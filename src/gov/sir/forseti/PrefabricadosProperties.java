package gov.sir.forseti;

import gov.sir.core.is21.CargarPropertiesCifrado;

import org.auriga.core.properties.BasicProperties;

public class PrefabricadosProperties extends BasicProperties{
	
	private static PrefabricadosProperties INSTANCIA = null;

	private PrefabricadosProperties() {
		super("gov.sir.prefabricado.properties");
		try{
			ClassLoader loader = PrefabricadosProperties.class.getClassLoader();
			CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.prefabricado.properties", loader);
			prop = cpc.desencriptar();
		}catch(Exception e){        	
		}   
	}

	public static synchronized PrefabricadosProperties getInstancia() {
		if (INSTANCIA == null) {
			INSTANCIA = new PrefabricadosProperties();
		}
		return INSTANCIA;
	}
}

