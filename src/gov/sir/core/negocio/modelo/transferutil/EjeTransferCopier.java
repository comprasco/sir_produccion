package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EjeTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Eje transferObject = new gov.sir.core.negocio.modelo.Eje();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje
  	    try {
	  	transferObject.setIdEje(enhancedObject.getIdEje());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Eje transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje
  	    try {
	  	enhancedObject.setIdEje(transferObject.getIdEje());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}