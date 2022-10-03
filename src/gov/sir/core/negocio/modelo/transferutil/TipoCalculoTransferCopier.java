package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoCalculoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoCalculo transferObject = new gov.sir.core.negocio.modelo.TipoCalculo();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoCalculo
  	    try {
	  	transferObject.setIdTipoCalculo(enhancedObject.getIdTipoCalculo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoCalculo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoCalculo
  	    try {
	  	enhancedObject.setIdTipoCalculo(transferObject.getIdTipoCalculo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}