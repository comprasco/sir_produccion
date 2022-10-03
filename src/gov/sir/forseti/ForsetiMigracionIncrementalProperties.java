package gov.sir.forseti;

import gov.sir.core.is21.CargarPropertiesCifrado;

import org.auriga.core.properties.BasicProperties;

/**
 * @author gvillal
 * Ing. Germán Villalba F.
 * gvillalba@i-siglo21.com
 * Propiedades de Forseti para el servicio de Persistencia
 * para la migracion incremental.
 */
public class ForsetiMigracionIncrementalProperties extends BasicProperties implements ForsetiPropertiesMBean {
	
	private static ForsetiMigracionIncrementalProperties INSTANCIA = null;
	
    public static final String FORSETI_FACTORY = "gov.sir.forseti.factory";
	public static final String MAYOR_EXTENSION = "gov.sir.forseti.mayor_extension";
	public static final String MAXIMO_REGISTROS_CONSULTA = "gov.sir.forseti.maximo_regitros_consulta";
	public static final String COPIAR_CANCELADORA_COPIA_ANOTACION = "gov.sir.forseti.copiar_canceladora_copia_anotacion";
    public static final String AUDITORIA = "gov.sir.forseti.auditoria";
    public static final String MAPA_JDO="versant.metadata.0";
	public static final String JDO_JCA = "gov.sir.forseti.jca";
	public static final String P_JNDI_NAME = "gov.sir.forseti.pmf.jndi";
	
    private ForsetiMigracionIncrementalProperties() {
        super("gov.sir.forseti.migracionincremental.properties");
        try{
	        ClassLoader loader = ForsetiMigracionIncrementalProperties.class.getClassLoader();
	    	CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.forseti.migracionincremental.properties", loader);
	    	prop = cpc.desencriptar();
    	}catch(Exception e){        	
    	}   
    }

    public static synchronized ForsetiMigracionIncrementalProperties getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ForsetiMigracionIncrementalProperties();
        }
        return INSTANCIA;
    }

}
