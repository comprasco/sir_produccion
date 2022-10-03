/*
 * Clase que contiene constantes de la implementacion jdogenie.
 */
package gov.sir.hermod.dao.impl.jdogenie;


/**
 * Clase que contiene constantes de la implementacion jdogenie.
 * @author fceballos, mrios
 */
public abstract class JDOGenieKeys {
    public static final String FILE_JDO_PROPERTIES = "gov.sir.hermod.impl.jdogenie.jdofile";
    public static final String ORACLE_DRIVER = "gov.sir.hermod.impl.oracle.driver";
    public static final String ORACLE_JDBC = "gov.sir.hermod.impl.oracle.jdbc";
    public static final String ORACLE_USER = "gov.sir.hermod.impl.oracle.user";
    public static final String ORACLE_PASS = "gov.sir.hermod.impl.oracle.pass";
    public static final String ORACLE_POOL_INI = "gov.sir.hermod.impl.oracle.pool.ini";
    public static final String ORACLE_POOL_MAX = "gov.sir.hermod.impl.oracle.pool.max";
    public static final String ORACLE_WORKFLOW = "gov.sir.hermod.impl.oracle.wf";

    public static String GET_LISTA_PROCESOS = "GET_PROCESOS";
    public static String GET_LISTA_PROCESOS_QUE_INICIA = "GET_PROCESOS_QUE_INICIA";
    public static String GET_PROCESOS_PADRE = "GET_PROCESOS_PADRE";
    public static String GET_LISTA_FASES = "GET_FASES";
    public static String GET_LISTA_FASES_SIGUIENTES = "GET_FASES_SIGUIENTES";
    public static String GET_LISTA_RTAS_SIGUIENTES = "GET_RTAS_SIGUIENTES";
    public static String GET_LISTA_TURNOS = "GET_TURNOS";
}