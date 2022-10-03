package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoImpuestoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoImpuesto transferObject = new gov.sir.core.negocio.modelo.TipoImpuesto();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImpuesto
  	    try {
	  	transferObject.setIdTipoImpuesto(enhancedObject.getIdTipoImpuesto());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoImpuesto transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImpuesto
  	    try {
	  	enhancedObject.setIdTipoImpuesto(transferObject.getIdTipoImpuesto());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}