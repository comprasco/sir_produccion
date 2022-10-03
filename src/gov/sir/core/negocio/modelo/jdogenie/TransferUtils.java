/*
 * TransferUtils.java
 *
 * Created on 9 de octubre de 2004, 14:47
 */
package gov.sir.core.negocio.modelo.jdogenie;


//TEST
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.transferutil.MakeTransferEnhaced;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.forseti.dao.FolioDAO;
import gov.sir.forseti.dao.ForsetiFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * Esta calse realiza copias profundas de las clases modelo a modelo.jdogenie y de modelo.jdogenie
 * a modelo.
 * La copia profunda se hace para obtener objetos que no tengan la dependencia de JDO Genie.
 *
 * @author  Ing. Diego Hernando Cantor
 */
public class TransferUtils {
    private static final String transferPackage = "gov.sir.core.negocio.modelo";
    private static final String enhancedPackage = "gov.sir.core.negocio.modelo.jdogenie";
    
    /**
     * Obtiene un objeto transfer a partir de un objeto con terminacion Enhanced
     */
    public static Object makeTransfer(Object source) {
    	return MakeTransferEnhaced.makeTransfer(source);
    }
    
    /**
     * Obtiene un objeto enhanced a partir de un objeto transfer
     */
    public static Object makeEnhanced(Object source) {
        return MakeTransferEnhaced.makeEnhanced(source);
    }
    
    public static List makeTransferAll(List transfers) {
        Iterator i = transfers.iterator();
        List newList = new ArrayList();
        while (i.hasNext()) {
            newList.add(makeTransfer(i.next()));
        }
        return newList;
    }
    
    public static List makeEnhancedAll(List enhanceds) {
        Iterator i = enhanceds.iterator();
        List newList = new ArrayList();
        while (i.hasNext()) {
            newList.add(makeEnhanced(i.next()));
        }
        return newList;
    }
    
    protected static Object deepCopy(Object source, Map cache) {
        if (source == null) {
            return null;
        }
        
        //logger.debug("INICIO :" + source.getClass().getName());
        
        //1. Crear una instancia de la clase target y ponerla en el cache
        Object target = getTarget(source);
        cache.put(source, target);
        
        //2. Obtener metodos setters (set, add)
        Method[] methods = source.getClass().getMethods();
        List setters = new ArrayList();
        
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().startsWith("set") ||
            methods[i].getName().startsWith("add")) {
                setters.add(methods[i]);
            }
        }
        
        //3. Para cada metodo setter obtener el getter (get, is)
        for (Iterator i = setters.iterator(); i.hasNext();) {
            Method setter = (Method) i.next();
            //logger.debug("procesando-->" + setter.getName());
            
            Method getter = getGetterMethod(source, setter);
            
            if (getter == null) {
                continue;
            }
            
            //4. Invocar cada getter sobre el objeto source
            try {
                Object sourceProperty = getter.invoke(source, null);
                Object targetProperty = null;
                
                if (sourceProperty == null) {
                    continue; //se encontro null;
                }
                //4.1. Si el objeto obtenido es una lista entonces crear una nueva lista y
                //hacer la copia para cada elemento de la lista
                else if (sourceProperty instanceof List) {
                    Iterator it = ((List) sourceProperty).iterator();
                    
                    if (!it.hasNext()) {
                        continue; //la lista no tiene elementos;
                    }
                    
                    while (it.hasNext()) {
                        Object sp = it.next();
                        Object tp = cache.get(sp);
                        if (tp == null) {
                            tp = deepCopy(sp, cache);
                        }
                        doSet(target, setter, tp);
                    }
                }
                //4.2 Si el objeto obtenido es una fecha y el objeto
                //es del paquete enhanced entonces crear el objeto date debido
                //a que en jdo genie devuelve un objeto descendiente de date y en lo que queremos
                //pasar debemos asegurarnos que no sea ningun objeto particular, sino el Date.
                else if ((sourceProperty instanceof Date) &&
                (source.getClass().getPackage().getName().equals(enhancedPackage))) {
                    targetProperty = new Date(((Date) sourceProperty).getTime());
                    doSet(target, setter, targetProperty);
                }
                //4.3. Si el objeto obtenido se encuentra en el mismo paquete del source
                // y no existe una correspondencia en el cache entonces hacer la copia profunda
                else if (sourceProperty.getClass().getPackage().equals(source.getClass().getPackage())) {
                    targetProperty = cache.get(sourceProperty);
                    if (targetProperty == null) {
                        targetProperty = deepCopy(sourceProperty, cache);
                    }
                    
                    doSet(target, setter, targetProperty);
                } else {
                    targetProperty = sourceProperty;
                    doSet(target, setter, targetProperty);
                }
            }  catch (InvocationTargetException e) {
					//logger.error("Error de invocation target exception: "+e.getCause());
				}
            catch (Exception e) {
                //logger.error(e);
            }
        }
        //logger.debug("FIN :" + source.getClass().getName());
        return target;
    }
    
    /**
     * Este metodo permite obtener una instancia VACIA de la clase correspondiente.
     * La correspondencia es:
     * <ul>
     *   <li>si la clase de origen es transfer, se obtiene un objeto enhanced</li>
     *   <li>si la clase de origen es enhanced, se obtiene un objeto transfer</li>
     * </ul>
     */
    protected static Object getTarget(Object source) {
        String packageName = source.getClass().getPackage().getName();
        String className = source.getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        
        if (packageName.equals(transferPackage)) {
            //El paquete de origen es transfer, se debe obtener un objeto enhanced
            className = enhancedPackage + "." + className + "Enhanced";
        } else if (packageName.equals(enhancedPackage)) {
            //El paquete de origen es enhanced se debe obtener un objeto transfer
            className = className.substring(0, className.indexOf("Enhanced"));
            className = transferPackage + "." + className;
        } else {
            throw new IllegalArgumentException(
            "No se puede procesar una clase del paquete" + packageName);
        }
        
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            
            return null;
        }
    }
    
    protected static Method getGetterMethod(Object object, Method setter) {
        String sourceProperty = setter.getName().substring(3);
        String getterMethodName = "get" + sourceProperty;
        Method getter = null;
        
        try {
            getter = object.getClass().getMethod(getterMethodName, null);
        } catch (NoSuchMethodException nsme) {
            getterMethodName = "get" + sourceProperty + "s";
            
            try {
                getter = object.getClass().getMethod(getterMethodName, null);
            } catch (NoSuchMethodException nsme2) {
                getterMethodName = "get" + sourceProperty + "es";
                
                try {
                    getter = object.getClass().getMethod(getterMethodName, null);
                } catch (NoSuchMethodException nsme3) {
                    getterMethodName = "is" + sourceProperty;
                    
                    try {
                        getter = object.getClass().getMethod(getterMethodName,
                        null);
                    } catch (NoSuchMethodException nsme4) {
                    	Log.getInstance().debug(TransferUtils.class, "error obteniendo getter: clase=" +
                        object.getClass() + " error=" + nsme4);
                    }
                }
            }
        }
        
        return getter;
    }
    
    protected static void doSet(Object target, Method setter, Object value) {
        Class targetClass = target.getClass();
        String targetClassName = targetClass.getName();
        Class[] types = { value.getClass() };
        Object[] params = { value };
        Method setterTarget = null;
        String setterName = setter.getName();
        
        // recorrer los nombres de los metodos para evitar hacer llamados por introspeccion mas
        // adelante mas costosos si el metodo no se encuentra en el destino.
        try {
            Method[] metodos = targetClass.getMethods();
            boolean found = false;
            
            for (int i = 0; i < metodos.length; i++) {
                if (metodos[i].getName().equals(setterName)) {
                    found = true;
                }
            }
            
            if (!found) {
                //logger.debug("El setter: clase=" + targetClassName +", setter=" + setterName + " no existe");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        try {
            setterTarget = targetClass.getMethod(setterName, types);
        } catch (NoSuchMethodException nsme) {
            //logger.debug("fallo obteniendo setter: clase=" + targetClassName +
            //", setter=" + setterName + " intentando con primitivas...");
            Class primitive = getPrimitiveType(value.getClass());
            
            if (primitive != null) {
                try {
                    setterTarget = target.getClass().getMethod(setterName,
                    new Class[] { primitive });
                } catch (NoSuchMethodException nsme2) {
                	Log.getInstance().debug(TransferUtils.class, 
                    "error obteniendo setter con primitivas: clase=" +
                    targetClassName + ", setter=" + setterName + " error=" +
                    nsme2);
                }
            } else {
                /*logger.debug("fallo obteniendo setter: clase=" +
                targetClassName + ", setter=" + setterName +
                " intentando con superclases...");*/
                
                Class type = types[0];
                
                while (!type.equals(Object.class)) {
                    type = type.getSuperclass();
                    types[0] = type;
                    Log.getInstance().debug(TransferUtils.class, "probando con " + type.getClass());
                    
                    try {
                        setterTarget = target.getClass().getMethod(setterName,
                        types);
                    } catch (NoSuchMethodException nsme2) {
                    }
                }
            }
        } finally {
            if (setterTarget != null) {
                try {
                    setterTarget.invoke(target, params);
                } catch (Exception e) {
                    /*logger.debug("error haciendo set: clase=" +
                    targetClassName + ", setter=" + setterName + " error=" +
                    e);*/
                }
            } else {
                /*logger.debug("No se pudo procesar el setter: clase=" +
                targetClassName + ", setter=" + setterName);*/
            }
        }
    }
    
    protected static Class getPrimitiveType(Class c) {
        String className = c.getName();
        
        if (className.equals("java.lang.Boolean")) {
            return Boolean.TYPE;
        } else if (className.equals("java.lang.Character")) {
            return Character.TYPE;
        } else if (className.equals("java.lang.Byte")) {
            return Byte.TYPE;
        } else if (className.equals("java.lang.Short")) {
            return Short.TYPE;
        } else if (className.equals("java.lang.Integer")) {
            return Integer.TYPE;
        } else if (className.equals("java.lang.Long")) {
            return Long.TYPE;
        } else if (className.equals("java.lang.Float")) {
            return Float.TYPE;
        } else if (className.equals("java.lang.Double")) {
            return Double.TYPE;
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception {
        FolioPk id = new FolioPk();
        
        id.idMatricula = "67";
        
        FolioDAO dao = ForsetiFactory.getFactory().getFolioDAO();
        Folio original = dao.getFolioByID(id);
        FolioEnhanced fe = (FolioEnhanced) TransferUtils.makeEnhanced(original);
    }
}