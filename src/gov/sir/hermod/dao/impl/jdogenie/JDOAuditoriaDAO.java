/*
 * Created on 28-abr-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.jdogenie.AuditoriaNegocioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.Enhanced;
import gov.sir.core.negocio.modelo.jdogenie.LlaveMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.RelAtributoValorMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ValorMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.handlers.JDOHandler;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiProperties;
import gov.sir.forseti.dao.DAOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.auriga.util.ExceptionPrinter;

import com.versant.core.jdo.VersantPersistenceManager;

/**
 * @author jfrias
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JDOAuditoriaDAO {
    /**
     * Constante que identifica que la accion que se registra en la auditoria es un INSERT en la base de datos
     */
    public static String INSERT = "INSERT";

    /**
     * Constante que identifica que la accion que se registra en la auditoria es un UPDATE en la base de datos
     */
    public static String UPDATE = "UPDATE";

    /**
     * Constante que identifica que la accion que se registra en la auditoria es un DELETE en la base de datos
     */
    public static String DELETE = "DELETE";
    

    /**  */
    public JDOAuditoriaDAO() {
        super();
    }

    /**
     * Este metodo crea un nuevo registro de auditoria, dependiendo de los objetos <code>Enhanced</code>
     * que llegan.
     *
     * Si ninguno de los objetos es nulo, se miran las diferencias de sus atributos básicos y éstos quedarán
     * en la auditoría.
     *
     * Si el objeto nuevo es nulo y el viejo no, quiere decir que el objeto viejo fue borrado.  Todos sus
     * atributos básicos quedarán registrados en la auditoría.
     *
     * Si el objeto viejo es nulo y el nuevo no, quiere decir que se creó un nuevo objeto.  Todos los atributos
     * del nuevo objeto quedarán registrados en la auditoría.
     *
     * @param nuevo El objeto con los valores actuales (modificados)
     * @param viejo El objeto con los valores antigus
     * @param usuario Usuario que realiza la modificacion
     * @param pm Persistence Manager para mantener la transaccionalidad
     */
    protected void addAuditoria(Enhanced nuevo, Enhanced viejo,
        UsuarioEnhanced usuario, PersistenceManager pm)
        throws DAOException {
        String audita = ForsetiProperties.getInstancia().getProperty(ForsetiProperties.AUDITORIA);
        boolean hayAuditoria = Boolean.valueOf(audita).booleanValue();
        if (hayAuditoria) {
            List deltas = new ArrayList(); //Lista de strings con los atributos que cambiaron de la clase vieja
            List atributos = new ArrayList(); //Lista con todos los atributos de la clase
            Map mapaValores = new HashMap(); //Mapa con {nombreAtributo-Object(Valor del atributo)} de los atributos que cambiaron
            Map mapa = JDOHandler.getInstance().getJdoMap(); //Mapa con {LlaveMapaJDO-ValorMapaJDO} que tiene todo el mapa JDO

            Enhanced guardar = (viejo == null) ? nuevo : viejo;
            Log.getInstance().info(JDOAuditoriaDAO.class,"Iniciando auditoria de " +
                guardar.getClass().getName());

            String accion = obtenerDatos(atributos, mapaValores, deltas, nuevo,
                    viejo);

            if (!accion.equals("")) {
                Map llavesValores = new HashMap();
                List valoresDeltas = new ArrayList();
                Map mapaValoresAtributos = new HashMap();
                LlaveMapaJDO key = new LlaveMapaJDO();
                key.setClase(guardar.getClass().getName());

                Iterator itAtributos = atributos.iterator();

                while (itAtributos.hasNext()) {
                    String atributo = (String) itAtributos.next();
                    key.setAtributo(atributo);

                    ValorMapaJDO valor = (ValorMapaJDO) mapa.get(key);

                    //si el valor es nulo, el valor se obtiene de la clase de la que hereda
                    if (valor == null) {
                        Class superClass = guardar.getClass().getSuperclass();
                        LlaveMapaJDO keySuper = new LlaveMapaJDO();
                        keySuper.setClase(superClass.getName());
                        keySuper.setAtributo(atributo);
                        valor = (ValorMapaJDO) mapa.get(keySuper);
                    }

                    //Si el valor sigue siendo null, es porque las relaciones por default no aparecen en el mapa JDO
                    if (valor != null) {
                        if (valor.isPk()) {
                            Object result = obtenerValor(guardar, atributo);
                            llavesValores.put(valor, result);
                        }

                        //Si el atributo actual se encuentra entre las diferencias encontradas entre los 2 objetos
                        if (deltas.contains(atributo)) {
                            valoresDeltas.add(valor);

                            Object valorAtributo = mapaValores.get(atributo);

                            //Si el valor del atributo al hacer get(Atributo) es diferente de null
                            if (valorAtributo != null) {
                                
                                /*Si el valor del atributo es otro objeto del modelo, es necesario obtener el objeto
                                 * RelAtributoMapaValorJDO de su(s) llave(s) primaria(s)
                                 */
                                if (valorAtributo.getClass().getSuperclass()
                                                     .getName().equals(Enhanced.class.getName())) {
                                    
                                    Class nueva = valorAtributo.getClass();

                                    Method[] metodosNuevo = nueva.getMethods();
                                    
                                    //Se obtienen los valores de los atributos y se relacionan con el valor que tienen actualmente
                                    for (int i = 0; i < metodosNuevo.length;
                                            i++) {
                                        try {
                                            Method metodo = metodosNuevo[i];
                                            if ((metodo.getName().startsWith("get") ||
                                                    metodo.getName().startsWith("is")) &&
                                                    !metodo.getName().equals("getClass") &&
                                                    !(metodo.getReturnType() == List.class)) {
                                                Object resultNuevo = metodo.invoke(valorAtributo,
                                                        null);

                                                String field = null;

                                                if (metodo.getName().startsWith("get")) {
                                                    field = metodo.getName()
                                                                  .substring(3);
                                                } else if (metodo.getName()
                                                                     .startsWith("is")) {
                                                    field = metodo.getName()
                                                                  .substring(2);
                                                }

                                                String first = field.substring(0,
                                                        1).toLowerCase();
                                                String all = first +
                                                    field.substring(1);
                                                LlaveMapaJDO llaveObjeto = new LlaveMapaJDO();
                                                llaveObjeto.setClase(nueva.getName());
                                                llaveObjeto.setAtributo(all);

                                                ValorMapaJDO valorObjeto = (ValorMapaJDO) mapa.get(llaveObjeto);

                                                if ((valorObjeto != null) &&
                                                        valorObjeto.isPk()) {
                                                    List valoresObjetos = valorObjeto.getColumnas();
                                                    Iterator itValoresObjetos = valoresObjetos.iterator();

                                                    while (itValoresObjetos.hasNext()) {
                                                        RelAtributoValorMapaJDO relac =
                                                            (RelAtributoValorMapaJDO) itValoresObjetos.next();

                                                        if (((RelAtributoValorMapaJDO) valor.getColumnas()
                                                                                                .get(0)).getAtributo() != null) {
                                                            if (all.equals(
                                                                        relac.getAtributo())) {
                                                                //mirar si era referencia o no
                                                                mapaValoresAtributos.put(relac.getColumna(),
                                                                    resultNuevo);
                                                                i = metodosNuevo.length;
                                                                Log.getInstance().debug(JDOAuditoriaDAO.class,
                                                                    "Insertando " +
                                                                    relac.getColumna() +
                                                                    " con valor " +
                                                                    resultNuevo);
                                                            }
                                                        } else {
                                                            mapaValoresAtributos.put(((RelAtributoValorMapaJDO) valor.getColumnas()
                                                                                                                     .get(0)).getColumna(),
                                                                resultNuevo);
                                                            i = metodosNuevo.length;
                                                            Log.getInstance().debug(JDOAuditoriaDAO.class,
                                                                "Insertando " +
                                                                ((RelAtributoValorMapaJDO) valor.getColumnas()
                                                                                                .get(0)).getColumna() +
                                                                " con valor " +
                                                                resultNuevo);
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (IllegalArgumentException e) {
                                            ExceptionPrinter printer = new ExceptionPrinter(e);
                                            Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                "Error en el método " +
                                                metodosNuevo[i].getName() +
                                                " de la clase " +
                                                nueva.getName() + ":" +
                                                printer.toString());
                                        } catch (IllegalAccessException e) {
                                            ExceptionPrinter printer = new ExceptionPrinter(e);
                                            Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                "Error en el método " +
                                                metodosNuevo[i].getName() +
                                                " de la clase " +
                                                nueva.getName() + ":" +
                                                printer.toString());
                                        } catch (InvocationTargetException e) {
                                            ExceptionPrinter printer = new ExceptionPrinter(e);
                                            Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                "Error en el método " +
                                                metodosNuevo[i].getName() +
                                                " de la clase " +
                                                nueva.getName() + ":" +
                                                printer.toString());
                                        }
                                    }
                                } else {
                                    /* Como el valor del atributo no es otro objeto del modelo, se guarda directamente
                                     * en el mapa
                                     */
                                    mapaValoresAtributos.put(((RelAtributoValorMapaJDO) valor.getColumnas()
                                                                                             .get(0)).getColumna(),
                                        valorAtributo);
                                    Log.getInstance().debug(JDOAuditoriaDAO.class,"Insertando " +
                                        ((RelAtributoValorMapaJDO) valor.getColumnas()
                                                                        .get(0)).getColumna() +
                                        " con valor " + valorAtributo);
                                }
                            }
                        }
                    } else {
                        /*Cuando el valor no esta en el mapa JDO se tiene que mirar el que tipo de objeto devuelve el
                         * metodo y mirar el valor de éste en el mapa JDO*/
                        String mayuscula = atributo.substring(0, 1).toUpperCase();
                        String getter = "get" + mayuscula +
                            atributo.substring(1);

                        Method[] metodos = guardar.getClass().getMethods();

                        for (int i = 0; i < metodos.length; i++) {
                            Method metodo = metodos[i];

                            if (metodo.getName().equals(getter)) {
                                try {
                                    Object objGetter = metodo.invoke(guardar,
                                            null);

                                    if (objGetter != null) {
                                        Class nueva = objGetter.getClass();

                                        Method[] metodosNuevo = nueva.getMethods();

                                        for (int j = 0;
                                                j < metodosNuevo.length; j++) {
                                            try {
                                                Method metodoObj = metodosNuevo[j];
                                                if ((metodoObj.getName()
                                                                  .startsWith("get") ||
                                                        metodo.getName()
                                                                  .startsWith("is")) &&
                                                        !metodoObj.getName()
                                                                      .equals("getClass") &&
                                                        !(metodo.getReturnType() == List.class)) {
                                                    String field = null;

                                                    if (metodo.getName()
                                                                  .startsWith("get")) {
                                                        field = metodo.getName()
                                                                      .substring(3);
                                                    } else if (metodo.getName()
                                                                         .startsWith("is")) {
                                                        field = metodo.getName()
                                                                      .substring(2);
                                                    }

                                                    String first = field.substring(0,
                                                            1).toLowerCase();
                                                    String all = first +
                                                        field.substring(1);
                                                    LlaveMapaJDO llaveObjeto = new LlaveMapaJDO();
                                                    llaveObjeto.setClase(nueva.getName());
                                                    llaveObjeto.setAtributo(all);

                                                    ValorMapaJDO valorObjeto = (ValorMapaJDO) mapa.get(llaveObjeto);

                                                    if ((valorObjeto != null) &&
                                                            valorObjeto.isPk()) {
                                                        if (deltas.contains(
                                                                    atributo)) {
                                                            ValorMapaJDO nuevoValor =
                                                                new ValorMapaJDO();
                                                            String tablaPadre = null;

                                                            for (int k = 0;
                                                                    k < atributos.size();
                                                                    k++) {
                                                                String atrCmp = (String) atributos.get(k);
                                                                LlaveMapaJDO llavePadre =
                                                                    new LlaveMapaJDO();
                                                                llavePadre.setAtributo(atrCmp);
                                                                llavePadre.setClase(guardar.getClass()
                                                                                           .getName());

                                                                ValorMapaJDO valorPadre =
                                                                    (ValorMapaJDO) mapa.get(llavePadre);

                                                                if (valorPadre != null) {
                                                                    tablaPadre = valorPadre.getTabla();
                                                                    k = atributos.size();
                                                                }
                                                            }

                                                            nuevoValor.setTabla(tablaPadre);
                                                            nuevoValor.setPk(valorObjeto.isPk());
                                                            nuevoValor.setColumnas(valorObjeto.getColumnas());
                                                            valoresDeltas.add(nuevoValor);

                                                            Object resultNuevo = metodoObj.invoke(objGetter,
                                                                    null);
                                                            List valoresObjetos = valorObjeto.getColumnas();
                                                            Iterator itValoresObjetos =
                                                                valoresObjetos.iterator();

                                                            while (itValoresObjetos.hasNext()) {
                                                                RelAtributoValorMapaJDO relac =
                                                                    (RelAtributoValorMapaJDO) itValoresObjetos.next();

                                                                if (((RelAtributoValorMapaJDO) valorObjeto.getColumnas()
                                                                                                              .get(0)).getAtributo() != null) {
                                                                    if (all.equals(
                                                                                relac.getAtributo())) {
                                                                        mapaValoresAtributos.put(relac.getColumna(),
                                                                            resultNuevo);
                                                                        Log.getInstance().debug(JDOAuditoriaDAO.class,
                                                                            "Insertando " +
                                                                            relac.getColumna() +
                                                                            " con valor " +
                                                                            resultNuevo);
                                                                    }
                                                                } else {
                                                                    mapaValoresAtributos.put(((RelAtributoValorMapaJDO) valorObjeto.getColumnas()
                                                                                                                                   .get(0)).getColumna(),
                                                                        resultNuevo);
                                                                    Log.getInstance().debug(JDOAuditoriaDAO.class,
                                                                        "Insertando " +
                                                                        ((RelAtributoValorMapaJDO) valorObjeto.getColumnas()
                                                                                                              .get(0)).getColumna() +
                                                                        " con valor " +
                                                                        resultNuevo);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (IllegalArgumentException e) {
                                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                                Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                    "Error en el método " +
                                                    metodosNuevo[j].getName() +
                                                    " de la clase " +
                                                    nueva.getName() + ":" +
                                                    printer.toString());
                                            } catch (IllegalAccessException e) {
                                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                                Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                    "Error en el método " +
                                                    metodosNuevo[j].getName() +
                                                    " de la clase " +
                                                    nueva.getName() + ":" +
                                                    printer.toString());
                                            } catch (InvocationTargetException e) {
                                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                                Log.getInstance().warn(JDOAuditoriaDAO.class,
                                                    "Error en el método " +
                                                    metodosNuevo[j].getName() +
                                                    " de la clase " +
                                                    nueva.getName() + ":" +
                                                    printer.toString());
                                            }
                                        }
                                    }
                                } catch (IllegalArgumentException e) {
                                    ExceptionPrinter printer = new ExceptionPrinter(e);
                                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                                        metodo.getName() + " de la clase " +
                                        guardar.getClass().getName() + ":" +
                                        printer.toString());
                                } catch (IllegalAccessException e) {
                                    ExceptionPrinter printer = new ExceptionPrinter(e);
                                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                                        metodo.getName() + " de la clase " +
                                        guardar.getClass().getName() + ":" +
                                        printer.toString());
                                } catch (InvocationTargetException e) {
                                    ExceptionPrinter printer = new ExceptionPrinter(e);
                                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                                        metodo.getName() + " de la clase " +
                                        guardar.getClass().getName() + ":" +
                                        printer.toString());
                                }
                            }
                        }
                    }
                }
                
                Map llaves = new HashMap();
                Iterator itValores = llavesValores.keySet().iterator();
                
                /*Se obtienen los valores de las llaves primarias para saber el nombre de la columna
                 * y el valor actual*/
                while (itValores.hasNext()) {
                    ValorMapaJDO valor = (ValorMapaJDO) itValores.next();

                    if (valor.isPk()) {
                        List cols = valor.getColumnas();
                        Iterator itCols = cols.iterator();
                        Object resultado = llavesValores.get(valor);

                        while (itCols.hasNext()) {
                            RelAtributoValorMapaJDO col = (RelAtributoValorMapaJDO) itCols.next();
                            llaves.put(col.getColumna(), resultado);
                        }
                    }
                }

                Iterator itDeltas = valoresDeltas.iterator();

                while (itDeltas.hasNext()) {
                    ValorMapaJDO val = (ValorMapaJDO) itDeltas.next();

                    String tabla = val.getTabla();

                    //Si la tabla es null, quiere decir que se tiene que usar la tabla de la superclase del objeto
                    if (tabla == null) {
                        Class superClass = guardar.getClass().getSuperclass();

                        
                        Method[] metodosNuevo = superClass.getMethods();
                        ValorMapaJDO valSuper = null;

                        for (int i = 0; i < metodosNuevo.length; i++) {
                            try {
                                Method metodo = metodosNuevo[i];
                                if ((metodo.getName().startsWith("get") ||
                                        metodo.getName().startsWith("is")) &&
                                        !metodo.getName().equals("getClass") &&
                                        !(metodo.getReturnType() == List.class)) {
                                    String field = null;

                                    if (metodo.getName().startsWith("get")) {
                                        field = metodo.getName().substring(3);
                                    } else if (metodo.getName().startsWith("is")) {
                                        field = metodo.getName().substring(2);
                                    }

                                    String first = field.substring(0, 1)
                                                        .toLowerCase();
                                    String all = first + field.substring(1);
                                    LlaveMapaJDO llaveSuper = new LlaveMapaJDO();
                                    llaveSuper.setClase(superClass.getName());
                                    llaveSuper.setAtributo(all);
                                    valSuper = (ValorMapaJDO) JDOHandler.getInstance()
                                                                        .getJdoMap()
                                                                        .get(llaveSuper);

                                    if (valSuper != null) {
                                        i = metodosNuevo.length;
                                    }
                                }
                            } catch (IllegalArgumentException e) {
                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                                    metodosNuevo[i].getName() +
                                    " de la clase " + superClass.getName() +
                                    ":" + printer.toString());
                            }
                        }

                        if ((valSuper != null) &&
                                (valSuper.getTabla() != null)) {
                            tabla = valSuper.getTabla();
                        }
                    }

                    List columns = val.getColumnas();
                    Iterator itColumns = columns.iterator();

                    while (itColumns.hasNext()) {
                        AuditoriaNegocioEnhanced auditoria = new AuditoriaNegocioEnhanced();
                        auditoria.setNombreTabla(tabla);

                        RelAtributoValorMapaJDO column = (RelAtributoValorMapaJDO) itColumns.next();
                        auditoria.setNombreColumna(column.getColumna());

                        Iterator itLlavesCols = llaves.keySet().iterator();

                        int i = 0;

                        while (itLlavesCols.hasNext()) {
                            String pk = (String) itLlavesCols.next();
                            Object pkVal = llaves.get(pk);

                            if (i == 0) {
                                auditoria.setLlave1(pk);
                                auditoria.setValorLlave1(pkVal.toString());
                            } else if (i == 1) {
                                auditoria.setLlave2(pk);
                                auditoria.setValorLlave2(pkVal.toString());
                            } else if (i == 2) {
                                auditoria.setLlave3(pk);
                                auditoria.setValorLlave3(pkVal.toString());
                            } else if (i == 3) {
                                auditoria.setLlave4(pk);
                                auditoria.setValorLlave4(pkVal.toString());
                            } else if (i == 4) {
                                auditoria.setLlave5(pk);
                                auditoria.setValorLlave5(pkVal.toString());
                            }

                            i++;
                        }

                        auditoria.setUsuario(usuario);
                        auditoria.setOperacion(accion);
                        auditoria.setFecha(new Date(System.currentTimeMillis()));

                        Object valorAtr = mapaValoresAtributos.get(column.getColumna());

                        if (valorAtr != null) {
                            auditoria.setValorAnterior(valorAtr.toString());
                        }

                        long id = getSecuencial(CSecuencias.SECUENCIA_AUDITORIA, pm);
                        auditoria.setIdAuditoria(id);

                        pm.makePersistent(auditoria);
                    }
                }
            }

            Log.getInstance().info(JDOAuditoriaDAO.class,"Auditoria de " + guardar.getClass().getName() +
                " terminada");
        }
    }

    /**
     * Se obtiene el valor actual del atributo en el objeto dado.  Para obtenerlo se ejecuta el getter
     * del atributo
     * @param viejo Instancia de la cual se quiere obtener el valor del atributo
     * @param atributo Nombre del atributo del cual se quiere obtener su valor.  Debe haber un método get(Atributo)
     * @return El objeto que representa el valor actual del atributo
     */
    private Object obtenerValor(Enhanced viejo, String atributo) {
        Method[] metodosNuevo = viejo.getClass().getMethods();

        for (int i = 0; i < metodosNuevo.length; i++) {
            try {
                Method metodo = metodosNuevo[i];

                if ((metodo.getName().startsWith("get") ||
                        metodo.getName().startsWith("is")) &&
                        !metodo.getName().equals("getClass") &&
                        !(metodo.getReturnType() == List.class)) {
                    String field = null;

                    if (metodo.getName().startsWith("get")) {
                        field = metodo.getName().substring(3);
                    } else if (metodo.getName().startsWith("is")) {
                        field = metodo.getName().substring(2);
                    }

                    String first = field.substring(0, 1).toLowerCase();
                    String all = first + field.substring(1);

                    if (all.equals(atributo)) {
                        Object result = metodo.invoke(viejo, null);

                        return result;
                    }
                }
            } catch (IllegalArgumentException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                    metodosNuevo[i].getName() + " de la clase " +
                    viejo.getClass().getName() + ":" + printer.toString());
            } catch (IllegalAccessException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                    metodosNuevo[i].getName() + " de la clase " +
                    viejo.getClass().getName() + ":" + printer.toString());
            } catch (InvocationTargetException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                    metodosNuevo[i].getName() + " de la clase " +
                    viejo.getClass().getName() + ":" + printer.toString());
            }
        }

        return null;
    }

    /**
     * Dependiendo de los valores de los objetos nuevo y viejo, se identifica el tipo de accion realizada y
     * se guarda en las listas y mapas que tiene en los parámetros, valores para saber las diferencias entre
     * los objetos y sus valores.
     *
     * @param atributos
     * @param mapaValores
     * @param deltas
     * @param nuevo
     * @param viejo
     * @return La accion que se realizó dependiendo de los valores de los objetos nuevo y viejo
     */
    private String obtenerDatos(List atributos, Map mapaValores, List deltas,
        Enhanced nuevo, Enhanced viejo) {
        String accion = "";

        if ((nuevo != null) && (viejo != null)) {
            accion = UPDATE;

            Class nueva = nuevo.getClass();
            Class vieja = viejo.getClass();

            Method[] metodosNuevo = nueva.getMethods();
            Method[] metodosViejo = vieja.getMethods();

            /*En este ciclo se obtienen todos los atributos de la clase y se mira las diferencias entre
             * el objeto viejo y el nuevo*/
            for (int i = 0; i < metodosNuevo.length; i++) {
                try {
                    Method metodo = metodosNuevo[i];
                    if ((metodo.getName().startsWith("get") ||
                            metodo.getName().startsWith("is")) &&
                            !metodo.getName().equals("getClass") &&
                            !(metodo.getReturnType() == List.class)) {
                        Object resultNuevo = metodo.invoke(nuevo, null);
                        Object resultViejo = metodo.invoke(viejo, null);
						String field=null;
						
						if (metodo.getName().startsWith("is")) {
							field = metodo.getName().substring(2);
						} else if (metodo.getName().startsWith("get")) {
							field = metodo.getName().substring(3);
						}

                        String first = field.substring(0, 1).toLowerCase();
                        String all = first + field.substring(1);
                        atributos.add(all);

                        if ((resultNuevo != null) && (resultViejo != null)) {
                            if (!resultNuevo.equals(resultViejo)) {
                                deltas.add(all);
                                mapaValores.put(all, resultViejo);
                            }
                        } else if (!((resultNuevo == null) &&
                                (resultViejo == null))) {
                            deltas.add(all);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNuevo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                } catch (IllegalAccessException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNuevo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                } catch (InvocationTargetException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNuevo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                }
            }
        } else if (nuevo == null) {
            accion = DELETE;

            Class vieja = viejo.getClass();

            Method[] metodosViejo = vieja.getMethods();

            /*Se obtienen todos los atributos de la clase y sus valores*/
            for (int i = 0; i < metodosViejo.length; i++) {
                try {
                    Method metodo = metodosViejo[i];

                    if ((metodo.getName().startsWith("get") ||
                            metodo.getName().startsWith("is")) &&
                            !metodo.getName().equals("getClass") &&
                            !(metodo.getReturnType() == List.class)) {
                        Object resultViejo = metodo.invoke(viejo, null);
                        
						String atributo = null;
						if (metodo.getName().startsWith("is")) {
							atributo = metodo.getName().substring(2);
						} else if (metodo.getName().startsWith("get")) {
							atributo = metodo.getName().substring(3);
						}
                        
                        String primera = atributo.substring(0, 1).toLowerCase();
                        String todo = primera + atributo.substring(1);
                        atributos.add(todo);
                        deltas.add(todo);

                        if (resultViejo != null) {
                            mapaValores.put(todo, resultViejo);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosViejo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                } catch (IllegalAccessException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosViejo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                } catch (InvocationTargetException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosViejo[i].getName() + " de la clase " +
                        viejo.getClass().getName() + ":" + printer.toString());
                }
            }
        } else if (viejo == null) {
            accion = INSERT;

            Class nueva = nuevo.getClass();

            Method[] metodosNueva = nueva.getMethods();

            /*Se obtienen todos los atributos de la clase y sus valores*/
            for (int i = 0; i < metodosNueva.length; i++) {
                try {
                    Method metodo = metodosNueva[i];

                    if ((metodo.getName().startsWith("get") ||
                            metodo.getName().startsWith("is")) &&
                            !metodo.getName().equals("getClass") &&
                            !(metodo.getReturnType() == List.class)) {
                        Object resultNuevo = metodo.invoke(nuevo, null);
                        String atributo = null;

                        if (metodo.getName().startsWith("is")) {
                            atributo = metodo.getName().substring(2);
                        } else if (metodo.getName().startsWith("get")) {
                            atributo = metodo.getName().substring(3);
                        }

                        String primera = atributo.substring(0, 1).toLowerCase();
                        String todo = primera + atributo.substring(1);
                        atributos.add(todo);
                        deltas.add(todo);

                        if (resultNuevo != null) {
                            mapaValores.put(todo, resultNuevo);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNueva[i].getName() + " de la clase " +
                        nuevo.getClass().getName() + ":" + printer.toString());
                } catch (IllegalAccessException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNueva[i].getName() + " de la clase " +
                        nuevo.getClass().getName() + ":" + printer.toString());
                } catch (InvocationTargetException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                        metodosNueva[i].getName() + " de la clase " +
                        nuevo.getClass().getName() + ":" + printer.toString());
                }
            }
        }

        return accion;
    }

	/**
	 * Este metodo consulta y avanza el valor de la secuencia que tiene como parámetro
	 * @param nomSecuencia Nombre de la secuencia como se encuentra en la base de datos
	 * @param pm Persestence Manager del cual se obtendrá la conexión a la base de datos
	 * @return El long que representa el secuencial
	 * @throws DAOException
	 */
    protected long getSecuencial(String nomSecuencia, PersistenceManager pm)throws DAOException{
        Connection con=null;
        long secuencia=0;
        ResultSet rs=null;
        PreparedStatement ps=null;
        VersantPersistenceManager jdoPM = null;
        String consulta="SELECT "+nomSecuencia+".NEXTVAL FROM DUAL";
        try {
            jdoPM = (VersantPersistenceManager)pm;
            con=jdoPM.getJdbcConnection(null);
            
            ps=con.prepareStatement(consulta);
            rs=ps.executeQuery();
            while(rs.next()){
                secuencia=rs.getLong(1);
                return secuencia;
            }
        }  catch (SQLException e) {
            ExceptionPrinter ep=new ExceptionPrinter(e);
            Log.getInstance().error(JDOAuditoriaDAO.class,"No se pudo obtener la secuencia:"+ep.toString());
            throw new DAOException(e);
        } finally{
            try {
                if (rs!=null){
                    rs.close();
                }
                if (ps!=null){
                    ps.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                ExceptionPrinter ep=new ExceptionPrinter(e);
                Log.getInstance().error(JDOAuditoriaDAO.class,ep.toString());
                throw new DAOException(e);
            }
        }
        return secuencia;
        
    }
    
    /**
     * Obtiene y avanza la secuencia de la tabla especificada
     * TOMADO de FORSETTI
     * @param tableName Nombre de la tabla de la cual se quiere obtener
     * el secuencial.
     * @param pm Persistence Manager de la transacción.
     * @return El secuencial requerido.
     * @throws DAOException
     */
/*    protected long getSecuencial(String tableName, PersistenceManager pm)
        throws DAOException {
        long rta = -1;

        if (tableName != null) {
            try {
                //Se hace el cambio de tipo de bloqueo pesimista para
                //que se bloquee la tabla la cual  nos
                //provee el secuencial
                VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
                pm = pm2;

                SecuenciasEnhanced.ID sid = new SecuenciasEnhanced.ID();
                sid.tableName = tableName;

                SecuenciasEnhanced s = this.getSecuenciaByID(sid, pm);
                s.setLastUsedId(s.getLastUsedId() + 1);

                //Volvemos a setear el tipo de bloqueo pesimista
                //para que no nos bloquee los siquientes registros
                //consultados
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

                rta = s.getLastUsedId(); // + 1;
            } catch (JDOObjectNotFoundException e) {
                throw new DAOException("No se encontró el registro de la secuencia",
                    e);
            } catch (JDOException e) {
                Log.getInstance().error(JDOAuditoriaDAO.class,e.getMessage());
            }
        }

        return rta;
    }*/

    /**
    * Obtiene una secuencia dado su identificador, método utilizado para transacciones
    * TOMADO de FORSETTI
    * @param sID identificador de la secuencia
    * @param pm PersistenceManager de la transaccion
    * @return Secuencia con sus atributos
    * @throws DAOException
    */
    /*protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhanced.ID sID,
        PersistenceManager pm) throws DAOException {
        SecuenciasEnhanced rta = null;

        if (sID.tableName != null) {
            try {
                rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOAuditoriaDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }*/

    /**
     * Clona los atributos básicos de un objeto dado
     * @param objeto Objeto a clonar
     * @return Un nuevo objeto con los atributos básicos iguales al original
     * @throws DAOException Cuando hay errores durante la clonación del objeto
     */
    protected Enhanced clonarEnhanced(Enhanced objeto)
        throws DAOException {
		String audita = ForsetiProperties.getInstancia().getProperty(ForsetiProperties.AUDITORIA);
		boolean hayAuditoria = Boolean.valueOf(audita).booleanValue();
        if (hayAuditoria){
            if (objeto != null) {
                Class claseObjeto = objeto.getClass();

                try {
                    Enhanced nuevo = (Enhanced) claseObjeto.newInstance();
                    Method[] metodos = claseObjeto.getMethods();

                    for (int i = 0; i < metodos.length; i++) {
                        try {
                            Method metodo = metodos[i];

                            if ((metodo.getName().startsWith("get") ||
                                    metodo.getName().startsWith("is")) &&
                                    !metodo.getName().equals("getClass") &&
                                    !(metodo.getReturnType() == List.class)) {
                                String field = null;

                                if (metodo.getName().startsWith("get")) {
                                    field = metodo.getName().substring(3);
                                } else if (metodo.getName().startsWith("is")) {
                                    field = metodo.getName().substring(2);
                                }

                                Object valor = metodo.invoke(objeto, null);
                                String setter = "set" + field;

                                for (int j = 0; j < metodos.length; j++) {
                                    Method metodoNuevo = metodos[j];

                                    if (metodoNuevo.getName().equals(setter)) {
                                        Object[] atributos = new Object[1];
                                        atributos[0] = valor;
                                        metodoNuevo.invoke(nuevo, atributos);
                                        j = metodos.length;
                                    }
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            ExceptionPrinter printer = new ExceptionPrinter(e);
                            Log.getInstance().error(JDOAuditoriaDAO.class,"Error en el método " +
                                metodos[i].getName() + " de la clase " +
                                claseObjeto.getName() + ":" + printer.toString());
                            throw new DAOException(e);
                        } catch (InvocationTargetException e) {
                            if (e.getCause() instanceof JDOObjectNotFoundException) {
                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                Log.getInstance().warn(JDOAuditoriaDAO.class,"Error en el método " +
                                    metodos[i].getName() + " de la clase " +
                                    claseObjeto.getName() + ":" +
                                    printer.toString());
                            } else {
                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                Log.getInstance().error(JDOAuditoriaDAO.class,"Error en el método " +
                                    metodos[i].getName() + " de la clase " +
                                    claseObjeto.getName() + ":" +
                                    printer.toString());
                                throw new DAOException(e);
                            }
                        }
                    }

                    return nuevo;
                } catch (InstantiationException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(JDOAuditoriaDAO.class,"Error clonando la clase " +
                        claseObjeto.getName() + ":" + printer.toString());
                    throw new DAOException(e);
                } catch (IllegalAccessException e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(JDOAuditoriaDAO.class,"Error clonando la clase " +
                        claseObjeto.getName() + ":" + printer.toString());
                    throw new DAOException(e);
                }
            }

        }else{
            try {
                if (objeto!=null){
                    Enhanced objetoNuevo=(Enhanced)objeto.getClass().newInstance();
                    return objetoNuevo;    
                }
            } catch (InstantiationException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(JDOAuditoriaDAO.class,"Error clonando la clase " +
                    objeto.getClass().getName() + ":" + printer.toString());
                throw new DAOException(e);
            } catch (IllegalAccessException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(JDOAuditoriaDAO.class,"Error clonando la clase " +
                        objeto.getClass().getName() + ":" + printer.toString());
                throw new DAOException(e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        /*JDOGenieAuditoriaDAO aud=new JDOGenieAuditoriaDAO();
        AlcanceGeograficoEnhanced fol=new AlcanceGeograficoEnhanced();
        fol.setIdAlcanceGeografico("1");
        fol.setNombre("nombre alcance");
        AlcanceGeograficoEnhanced fol2=new AlcanceGeograficoEnhanced();
        fol2.setIdAlcanceGeografico("1");
        fol2.setNombre("nombre alcance 2");
        UsuarioEnhanced usu=new UsuarioEnhanced();
        PersistenceManager pm=null;

        try {
            AlcanceGeograficoEnhanced clonado = (AlcanceGeograficoEnhanced)aud.clonarEnhanced(fol);
            logger.debug();
        } catch (DAOException e) {
            logger.error(e);
        }*/
    }
}
