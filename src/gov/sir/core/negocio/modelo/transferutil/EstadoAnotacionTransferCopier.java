package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EstadoAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.EstadoAnotacion transferObject = new gov.sir.core.negocio.modelo.EstadoAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdEstadoAn
  	    try {
	  	transferObject.setIdEstadoAn(enhancedObject.getIdEstadoAn());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.EstadoAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdEstadoAn
  	    try {
	  	enhancedObject.setIdEstadoAn(transferObject.getIdEstadoAn());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}