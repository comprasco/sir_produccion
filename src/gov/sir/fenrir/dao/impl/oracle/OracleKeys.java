
package gov.sir.fenrir.dao.impl.oracle;


/**
 * Esta clase contiene constantes para identificar las propiedades de configuración
 * a cargar a través del archivo . properties de configuración del servicio. (Para la 
 * implementación Oracle)
 * @author jfrias
 *
 */
public class OracleKeys {
    public static final String ORACLE_DRIVER = "gov.sir.fenrir.impl.oracle.driver";
    public static final String ORACLE_JDBC = "gov.sir.fenrir.impl.oracle.jdbc";
    public static final String ORACLE_USER = "gov.sir.fenrir.impl.oracle.user";
    public static final String ORACLE_PASS = "gov.sir.fenrir.impl.oracle.pass";
    public static final String ORACLE_POOL_INI = "gov.sir.fenrir.impl.oracle.pool.ini";
    public static final String ORACLE_POOL_MAX = "gov.sir.fenrir.impl.oracle.pool.max";
    public static final String ORACLE_WORKFLOW = "gov.sir.fenrir.impl.oracle.wf";
    
	public static final String LDAP_FACTORY = "gov.sir.fenrir.impl.oracle.ldap.factory";
	public static final String LDAP_URL = "gov.sir.fenrir.impl.oracle.ldap.url";
	public static final String LDAP_AUTHENTICATION = "gov.sir.fenrir.impl.oracle.ldap.authentication";
	public static final String LDAP_PROTOCOL = "gov.sir.fenrir.impl.oracle.ldap.protocol";
	public static final String LDAP_DISTINGUISHED_NAME = "gov.sir.fenrir.impl.oracle.ldap.principal";
	public static final String LDAP_USER = "gov.sir.fenrir.impl.oracle.ldap.user";
	public static final String LDAP_PASSWORD = "gov.sir.fenrir.impl.oracle.ldap.password";
	public static final String LDAP_DEFAULT_USER_PASSWORD = "gov.sir.fenrir.impl.oracle.ldap.defaultpassword";
	
	/**
	 * Domain para la creación de las direcciones de correo de los usuarios en el LDAP
	 */
	public static final String LDAP_EMAIL_DOMAIN = "gov.sir.fenrir.impl.oracle.ldap.emaildomain";

    protected OracleKeys() {
    }
}
