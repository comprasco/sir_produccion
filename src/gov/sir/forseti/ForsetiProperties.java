/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import gov.sir.core.is21.CargarPropertiesCifrado;

import org.auriga.core.properties.BasicProperties;

public class ForsetiProperties extends BasicProperties implements ForsetiPropertiesMBean{
    private static ForsetiProperties INSTANCIA = null;
    public static final String FORSETI_FACTORY = "gov.sir.forseti.factory";
	public static final String MAYOR_EXTENSION = "gov.sir.forseti.mayor_extension";
	public static final String MAXIMO_REGISTROS_CONSULTA = "gov.sir.forseti.maximo_regitros_consulta";
	public static final String COPIAR_CANCELADORA_COPIA_ANOTACION = "gov.sir.forseti.copiar_canceladora_copia_anotacion";
    public static final String AUDITORIA = "gov.sir.forseti.auditoria";
    public static final String MAPA_JDO="versant.metadata.0";
	public static final String JDO_JCA = "gov.sir.forseti.jca";
	public static final String P_JNDI_NAME = "gov.sir.forseti.pmf.jndi";

	public static final String CIRCULOS_ACTIVOS = "gov.sir.forseti.prfb.circulo";
	public static final String USAR_PREFABRICADO = "gov.sir.forseti.prfb.use";
	public static final String ALMACER_ALGENERAR = "gov.sir.forseti.prfb.storegenerate";
	
    private ForsetiProperties() {
        super("gov.sir.forseti.properties");
        try{
	        ClassLoader loader = ForsetiProperties.class.getClassLoader();
	    	CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.forseti.properties", loader);
	    	prop = cpc.desencriptar();
    	}catch(Exception e){        	
    	}   
    }

    public static synchronized ForsetiProperties getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ForsetiProperties();
        }
        return INSTANCIA;
    }
}
