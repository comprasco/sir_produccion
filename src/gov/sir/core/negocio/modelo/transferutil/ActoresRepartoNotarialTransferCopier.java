package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ActoresRepartoNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ActoresRepartoNotarial transferObject = new gov.sir.core.negocio.modelo.ActoresRepartoNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // CoordinadorReparto
  	    try {
	  	transferObject.setCoordinadorReparto(enhancedObject.getCoordinadorReparto());
	  	} catch (Throwable th ) {}
	  			
		  // DirectorReparto
  	    try {
	  	transferObject.setDirectorReparto(enhancedObject.getDirectorReparto());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ActoresRepartoNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // CoordinadorReparto
  	    try {
	  	enhancedObject.setCoordinadorReparto(transferObject.getCoordinadorReparto());
	  	} catch (Throwable th ) {}
	  			
		  // DirectorReparto
  	    try {
	  	enhancedObject.setDirectorReparto(transferObject.getDirectorReparto());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}