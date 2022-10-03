/*
 * OracleKeys.java
 *
 * Created on 29 de junio de 2004, 15:57
 */

package gov.sir.hermod.workflow.impl.oracle;

/**
 *
 * @author  dcantor
 */
public interface OracleKeys {

    public static final String ORACLE_DRIVER = "gov.sir.hermod.impl.oracle.driver";
    public static final String ORACLE_JDBC = "gov.sir.hermod.impl.oracle.jdbc";
    public static final String ORACLE_USER = "gov.sir.hermod.impl.oracle.user";
    public static final String ORACLE_PASS = "gov.sir.hermod.impl.oracle.pass";
    public static final String ORACLE_POOL_INI = "gov.sir.hermod.impl.oracle.pool.ini";
    public static final String ORACLE_POOL_MAX = "gov.sir.hermod.impl.oracle.pool.max";
    public static final String ORACLE_WORKFLOW = "gov.sir.hermod.impl.oracle.wf";

    //claves operativas del workflow
    public static final String ITEM_KEY = "ITEM_KEY";
    public static final String PROCESS = "PROCESS";
    public final static String NOTIFICATION_ID = "NOTIFICATION_ID";
    public final static String RESULT = "RESULT";
    public final static String ACTIVITY = "ACTIVITY";
    public final static String ROL = "ROL";
    public final static String SIGUIENTE_ESTACION = "SIGUIENTE_ESTACION";

    //claves para consultas
    public static final String TIPO_CONSULTA = "TIPO_CONSULTA";
    public static final String CONSULTA_OK = "EXITO";
    public static final String CONSULTA_ERROR = "FRACASO";
}