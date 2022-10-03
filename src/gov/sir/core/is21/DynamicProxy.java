/*
 * Created on Apr 27, 2005
 *
 * @author <a href="mailto:gvillalba@i-siglo21.com">Germán Villalba F </a>
 */
package gov.sir.core.is21;

import gov.sir.core.negocio.modelo.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Clase que se encarga de servir de Proxy Dinámico de una Clase de la interface
 * Home Remota o Local de un EJB.
 * 
 * @author gardila
 * @author <a href="mailto:gvillalba@i-siglo21.com">Germán Villalba F </a>
 */
public class DynamicProxy {


    /**
     * Cache con las instancias de los objetos que ya han sido construidos. La
     * llave es el nombre JNDI del objeto.
     */
    //private Hashtable cache;

    /**
     * Instancia unica de la clase.
     */
    private static DynamicProxy instance = null;

    /**
     * Constructor privado de la clase, implementa el patron Singleton.
     *  
     */
    private DynamicProxy() {
        //this.cache = new Hashtable();
    }

    /**
     * Método que retorna la unica instancia de la clase, implementa el patron
     * Singleton.
     * 
     * @return la intancia de la clase.
     */
    public static DynamicProxy getInstance() {
        if (instance == null) {
            instance = new DynamicProxy();
        }

        return instance;
    }

    /**
     * Método que retorna un objeto EJB local o remoto cargado dinámicamente a
     * partir de los parámetros de la función.
     * 
     * @param classLoader
     *            Cargador de Clases que tiene acceso a las clases representadas
     *            por sus nombres en los otros parámetros.
     * @param props
     *            tabla de Hashing con propiedades opcionales para establecer el
     *            contexto de los EJB's, si la tabla no tiene parametros el
     *            contexto seria el InitialContext sin parametros.
     * @param strHome
     *            Nombre completo de la interface Home (local o remota) del EJB.
     * @param jndiName
     *            Nombre JNDI del EJB.
     * @param strInterface
     *            Nombre completo de la interface de Negocio implementada por el
     *            Bean del EJB.
     * @param strEjbInterface
     *            Nombre completo de la interface Remota/Local del EJB.
     * @return una instancia del Objeto EJB que implementa la interface de
     *         negocio dada, para tener acceso a los métodos de este objeto hay
     *         que hacerle un Casting a la interface de negocio que implementa e
     *         invocar los metódos desde la misma.
     * @throws DynamicProxyException
     */
    public Object buildService(ClassLoader classLoader, Hashtable props,
            String strHome, String jndiName, String strInterface,
            String strEjbInterface) throws DynamicProxyException {

        Log.getInstance().debug(DynamicProxy.class,"=================================");
        Log.getInstance().debug(DynamicProxy.class,"Entrando al buildService.........");
        Log.getInstance().debug(DynamicProxy.class,"=================================");

        Object service = null, homeObj, dynamicProxy = null;
        Class home, serviceInterface, ejbInterface;
        Class[] c = new Class[2];
        IProxyObject proxyObject = null;
        String methodCreate = "create";

        Context context;
        try {
            context = getInitialContext(props);
        } catch (NamingException e4) {
            e4.printStackTrace();
            throw new DynamicProxyException(" [ERROR CARGANDO EL CONTEXT]", e4);
        }

        try {
            home = Class.forName(strHome, true, classLoader);
            Log.getInstance().debug(DynamicProxy.class,"home = "+home);
            serviceInterface = Class.forName(strInterface, true, classLoader);
            Log.getInstance().debug(DynamicProxy.class,"strEjbInterface = "+strEjbInterface);
            ejbInterface = Class.forName(strEjbInterface, true, classLoader);
            Log.getInstance().debug(DynamicProxy.class,"ejbInterface = "+ejbInterface);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            throw new DynamicProxyException(" [ERROR CARGANDO LA CLASE]", e1);
        }

        Object objRef = null;
        try {
        	Log.getInstance().debug(DynamicProxy.class,"CONTEXT = "+context);
        	Log.getInstance().debug(DynamicProxy.class,"context.getNameInNamespace = "+context.getNameInNamespace());
        	Log.getInstance().debug(DynamicProxy.class,"context.environment = "+context.getEnvironment());
        	
            objRef = context.lookup(jndiName);
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"OK JNDI =" + jndiName);
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        } catch (Exception e) {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"ERROR cargando JNDI=" + jndiName);
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            e.printStackTrace();
            throw new DynamicProxyException("ERROR cargando JNDI=" + jndiName,
                    e);
        }

        if (home.isInstance(java.rmi.Remote.class)) {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"INTENTANDO CARGAR CLASE REMORA objRef = "
                    + objRef.getClass());
            Log.getInstance().debug(DynamicProxy.class,"INTENTANDO CARGAR CLASE REMORA HOME = "
                    + home.getClass());
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            homeObj = PortableRemoteObject.narrow(objRef, home);
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"OK CLASE REMOTA HOME = " + home.getClass());
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        } else {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"LA CLASE LOCAL HOME = " + home.getClass());
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");

            homeObj = objRef;
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"homeObj = " + homeObj.getClass());
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        }

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"INTENTANDO EJECUTAR EL METODO = " + "["
                + homeObj.getClass() + "],[" + home.getClass() + "]," + "["
                + methodCreate + "]");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        try {
            service = Introspector.executeMethod(homeObj, home, methodCreate,
                    null);
        } catch (IntrospectorException e5) {
            e5.printStackTrace();
            throw new DynamicProxyException(
                    " [ERROR EJECUTANDO EL METODO DINAMICO]");
        }

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"METODO EJECUTADO = " + "[" + homeObj.getClass() + "],["
                + home.getClass() + "]," + "[" + methodCreate + "]");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        proxyObject = new ProxyDirectMethodExecution();

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"PROXY CREADO");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        c[0] = ejbInterface;
        c[1] = serviceInterface;

        boolean ok = false;

        try {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"INTENTAR INSTANCIAR PROXY");
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            dynamicProxy = Proxy.newProxyInstance(classLoader, c,
                    (InvocationHandler) proxyObject);
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"PROXY INSTANCIADO");
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");

            ok = true;
        } catch (Exception e) {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"ERROR INSTANCIANDO PROXY:");
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            e.printStackTrace();
        }

        if (!ok) {
        	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
            Log.getInstance().debug(DynamicProxy.class,"INTENTAR INSTANCIAR PROXY 2");
            Log.getInstance().debug(DynamicProxy.class,"========================================================================");

            dynamicProxy = Proxy.newProxyInstance(this.getClass()
                    .getClassLoader(), c, (InvocationHandler) proxyObject);
        }

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"proxyObject OK");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        proxyObject.setConcreteObject(service);

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"proxyObject CON SERVICE OK");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class,"retornando dynamicProxy = " + dynamicProxy);
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        return dynamicProxy;
    }
/*
    public static void main(String args[]) {
        System.out.println("Hola:");
        String clase[] = {
                "com.is21.core.fenrir.factory.FenrirFactory",
                "com.is21.core.hermod.factory.HermodFactory",
                "com.is21.core.stationmanager.factory.StationManagerFactory",
                "com.is21.core.dispatcher.factory.DispatcherFactory",
                "com.is21.core.workqueue.factory.WorkQueueFactory"
                };

        DynamicProxy proxy = new DynamicProxy();
        try {
            ClassLoader classLoader = DynamicProxy.class.getClassLoader();
            for (int i=0; i<clase.length; i++) {
                System.out.println("clase[i]=" +  clase[i]);
                IService service = proxy.buildService(classLoader, clase[i]);
                System.out.println("service[i]=" + service);
                //IFenrirService fenrir = (IFenrirService)service;// (IFenrirService)obj;
            }
            

            
           
        } catch (DynamicProxyException e) {
            e.printStackTrace();
        } 
        System.out.println("FIN");

    }*/
/*
    public IService buildService(ClassLoader classLoader,
            String factoryClassName) throws DynamicProxyException {

        Class clase = null;
        Object obj = null;
        IFactory factory = null;
        IService service = null;
        try {
            clase = Class.forName(factoryClassName, true, classLoader);
            obj = clase.newInstance();
            factory = (IFactory) obj;
            service = factory.getService(classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DynamicProxyException(e);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
            throw new DynamicProxyException(e1);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
            throw new DynamicProxyException(e1);
        } catch (ServiceException se) {
            se.printStackTrace();
            throw new DynamicProxyException(se);
        }

        return service;
    }*/

    protected Context getInitialContext(Hashtable props) throws NamingException {

    	Log.getInstance().debug(DynamicProxy.class,"========================================================================");
        Log.getInstance().debug(DynamicProxy.class," [Hashtable props = " + props + "]");
        Log.getInstance().debug(DynamicProxy.class,"========================================================================");

        Context context = null;

        if (props != null) {
            context = new InitialContext(props);
        } else {
            context = new InitialContext();
        }

        if (context == null) {
            context = getInitialContext(null);
        }

        return context;

    }

}