package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TextoImprimibleTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TextoImprimible transferObject = new gov.sir.core.negocio.modelo.TextoImprimible();
		cache.put(enhancedObject, transferObject );
		
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTexto
  	    try {
	  	transferObject.setIdTexto(enhancedObject.getIdTexto());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TextoImprimible transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTexto
  	    try {
	  	enhancedObject.setIdTexto(transferObject.getIdTexto());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}