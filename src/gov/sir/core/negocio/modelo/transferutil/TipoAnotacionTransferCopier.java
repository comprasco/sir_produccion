package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoAnotacion transferObject = new gov.sir.core.negocio.modelo.TipoAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoAnotacion
  	    try {
	  	transferObject.setIdTipoAnotacion(enhancedObject.getIdTipoAnotacion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoAnotacion
  	    try {
	  	enhancedObject.setIdTipoAnotacion(transferObject.getIdTipoAnotacion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}