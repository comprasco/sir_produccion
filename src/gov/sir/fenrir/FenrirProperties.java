
package gov.sir.fenrir;

import gov.sir.core.is21.CargarPropertiesCifrado;

import org.auriga.core.properties.BasicProperties;


/**
 * Esta clase tiene las constantes para identificar los atributos del servicio 
 * que deben ser cargados desde el archivo .properties
 * @author jfrias, jmendez
 *
 */
public class FenrirProperties extends BasicProperties implements FenrirPropertiesMBean{
    
    private static FenrirProperties instance = null;
    
    /**
     * Factory del servicio
     */
    public static final String FENRIR_FACTORY = "gov.sir.fenrir.factory";
	
	/**
	 * Nombre del archivo de properties para jdo
	 */
	public static final String FILE_JDO_PROPERTIES = "gov.sir.fenrir.impl.jdogenie.jdofile";
	
	/**
	 * Atributos para el manejo de las secuencias del sistema 
	 */
	public static final String TABLA_SECUENCIA = "gov.sir.fenrir.impl.jdogenie.seq_table";
	public static final String NOMBRE_COLUMNA_TABLA = "gov.sir.fenrir.impl.jdogenie.keyColumnName";
	public static final String NOMBRE_COLUMNA_ULTIMO_ID = "gov.sir.fenrir.impl.jdogenie.valueColumnName";
	public static final String GRAB_SIZE = "gov.sir.fenrir.impl.jdogenie.seq_grabSize";
	
	/**
	 * Atributo para determinar si se debe realizar autenticación de usuarios con el LDAP
	 * al momento del inicio de una sesión
	 */
	public static final String AUTENTICACION_LDAP = "gov.sir.fenrir.autenticacion";
	
	/**
	 * Atributo para determinar si se debe lanzar la excepción de impresión
	 * al momento del inicio de una sesión
	 */
	public static final String LANZAR_EXCEPCION_IMPRESION = "gov.sir.fenrir.lanzar.excepcion.registro";	
	
	/**
	 * Atributo para determinar los posibles roles que tienen acceso a las pantallas administrativas 
	 * del sistema
	 */
	public static final String ADMIN_ID_ROL = "gov.sir.fenrir.admin.idrol";

	public static final String JDO_JCA = "gov.sir.fenrir.jca";

	public static final String P_JNDI_NAME = "gov.sir.fenrir.pmf.jndi";
        /*AHERRENO -  Se configura ruta del manual SIR en gov.sir.fenrir.properties*/
        public static final String R_MANUAL = "gov.sir.fenrir.manual";
     
 /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              : Nueva constante para manejar listado municipio origen.
    */

        public static final String RUTA_DESTINO_ARCHIVO ="gov.sir.fenrir.ruta.destino"; //"";
        
        public static final String RUTA_DESTINO_ARCHIVO_JUSTIFICACION ="gov.sir.fenrir.ruta.archivos.justificaciones";
        
        public static final String SO = "gov.sir.fenrir.so";//"\\";
        
        public static final String R_NOTICIAS = "gov.sir.fenrir.noticias";        
        public static final String R_VERSIONAMIENTO = "gov.sir.fenrir.versionamiento";

    /**
     * Constructor por defecto de la clase
     */
    private FenrirProperties() {
        super("gov.sir.fenrir.properties");
        
        try{
            ClassLoader loader = FenrirProperties.class.getClassLoader();
        	CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.fenrir.properties", loader);
        	prop = cpc.desencriptar();
        }catch(Exception e){        	
        }     

    }
    
    /**
     * Obtiene una instancia
     * @return una instancia
     */
    public static synchronized FenrirProperties getInstancia(){
    	if (instance==null){
    		instance=new FenrirProperties();
    	}
    	return instance;
    }
}
