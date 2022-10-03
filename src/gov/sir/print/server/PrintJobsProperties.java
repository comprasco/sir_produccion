package gov.sir.print.server;

import gov.sir.core.is21.CargarPropertiesCifrado;

import org.auriga.core.properties.BasicProperties;




/**
 *
 * @author  dcantor
 */
public class PrintJobsProperties extends BasicProperties {
    /** Única instancia de PrintJobsProperties */
    private static PrintJobsProperties INSTANCIA;

    /** Constante que define la llave de la propiedad del 
     * puerto minimo del servidor de impresión */
    public static final String P_SERVER_PORT_LOW = "gov.sir.print.server.localPortLow";
    
	/** Constante que define la llave de la propiedad del 
		 * puerto maximo del servidor de impresión */
    public static final String P_SERVER_PORT_HIGH = "gov.sir.print.server.localPortHigh";
    
    /**
     * Esta constante no aparece en el archivo de propiedades y sirve para
     * que las paginas IMPRESION.jsp e IMPRESION_AUTOMATICA.jsp obtengan
     * el puerto por el cual el servidor de impresion recibe nuevos clientes.
     */
    public static final String P_SERVER_PORT = "gov.sir.print.server.currentPort";
    
    
    public static final String P_SERVER_REINTENTOS = "gov.sir.print.server.reintentos";
    
     
	public static final String PRINT_FACTORY = "gov.sir.print.factory";
	
	/** Constante qie define el número de reintentos de envio de una impresión */
    public static final String P_REINTENTOS = "gov.sir.print.reintentos";

	public static final String JDO_JCA = "gov.sir.print.jca";
	
	public static final String P_JNDI_NAME = "gov.sir.print.pmf.jndi";
	
	public static final String VALIDAR_IMPRESION = "gov.sir.print.validar.impresion";

    public static final String REPORTES_EXCEL ="reportes.excel";

    /**
    * @author     : Julio Alcazar
    * @change     : Constante para obtener la IP en la cual se colocara el webservice de verificación.
    * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
    */
    public static final String VERIFICACION_CERTIFICADO_IP ="verificacion.certificado.ip";
    
    /**
    * @author     : Ellery David Robles Gómez
    * @change     : Constante para obtener la IP del servidor FTP.
    * Caso_Mantis : 09422: Acta - Requerimiento No 040_151 - Impresión de Folios - Nivel Central.
    */
    public static final String IMP_MASIVA_SIMPLE_FOLIO_FTP ="imp_masiva_simple_folio.ftp";

    /** Creates a new instance of SIMProperties */
    public PrintJobsProperties() {
        super("gov.sir.print.properties");
        try{
	        ClassLoader loader = PrintJobsProperties.class.getClassLoader();
	    	CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.print.properties", loader);
	    	prop = cpc.desencriptar();
    	}catch(Exception e){        	
    	}   
    }

    /**
     * Obtener una instancia de la clase PrintJobProperties
     * @return isntancia de PrintJobProperties
     */
    public static synchronized PrintJobsProperties getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new PrintJobsProperties();
        }

        return INSTANCIA;
    }
}
