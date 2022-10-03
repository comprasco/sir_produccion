package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoPredioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoPredio transferObject = new gov.sir.core.negocio.modelo.TipoPredio();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdPredio
  	    try {
	  	transferObject.setIdPredio(enhancedObject.getIdPredio());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoPredio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdPredio
  	    try {
	  	enhancedObject.setIdPredio(transferObject.getIdPredio());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}