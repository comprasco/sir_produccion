package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.hermod.HermodPropertiesMBean;
import org.auriga.core.properties.BasicProperties;

/**
 *
 * @author  mrios
 */

public class JDOHermodProperties extends BasicProperties implements HermodPropertiesMBean {
    
    private JDOHermodProperties() {
        super("gov.sir.hermod.properties");
        try{
        	ClassLoader loader = JDOHermodProperties.class.getClassLoader();
        	CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.hermod.properties", loader);
        	prop = cpc.desencriptar();
    	}catch(Exception e){        	
    	}     
    }
    
    public static synchronized JDOHermodProperties getInstancia() {
        if ( INSTANCIA == null ) {
            INSTANCIA = new JDOHermodProperties();
        }
        return INSTANCIA;
    }
    
    private static JDOHermodProperties INSTANCIA = null;
}