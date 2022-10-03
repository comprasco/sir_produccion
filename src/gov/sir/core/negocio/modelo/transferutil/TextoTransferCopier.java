package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TextoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Texto transferObject = new gov.sir.core.negocio.modelo.Texto();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdLlave
  	    try {
	  	transferObject.setIdLlave(enhancedObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // Texto
  	    try {
	  	transferObject.setTexto(enhancedObject.getTexto());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Texto transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdLlave
  	    try {
	  	enhancedObject.setIdLlave(transferObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // Texto
  	    try {
	  	enhancedObject.setTexto(transferObject.getTexto());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}