package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoRecepcionPeticionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoRecepcionPeticion transferObject = new gov.sir.core.negocio.modelo.TipoRecepcionPeticion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoRecepcion
  	    try {
	  	transferObject.setIdTipoRecepcion(enhancedObject.getIdTipoRecepcion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoRecepcionPeticion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoRecepcion
  	    try {
	  	enhancedObject.setIdTipoRecepcion(transferObject.getIdTipoRecepcion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}