package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoOficinaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoOficina transferObject = new gov.sir.core.negocio.modelo.TipoOficina();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoOficina
  	    try {
	  	transferObject.setIdTipoOficina(enhancedObject.getIdTipoOficina());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoOficina transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoOficina
  	    try {
	  	enhancedObject.setIdTipoOficina(transferObject.getIdTipoOficina());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}