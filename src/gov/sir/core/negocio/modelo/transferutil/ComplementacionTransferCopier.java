package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ComplementacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Complementacion transferObject = new gov.sir.core.negocio.modelo.Complementacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdComplementacion
  	    try {
	  	transferObject.setIdComplementacion(enhancedObject.getIdComplementacion());
	  	} catch (Throwable th ) {}
	  			
		  // Complementacion
  	    try {
	  	transferObject.setComplementacion(enhancedObject.getComplementacion());
	  	} catch (Throwable th ) {}
	  			
		  // ComplementacionConflictiva
  	    try {
	  	transferObject.setComplementacionConflictiva(enhancedObject.getComplementacionConflictiva());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Complementacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdComplementacion
  	    try {
	  	enhancedObject.setIdComplementacion(transferObject.getIdComplementacion());
	  	} catch (Throwable th ) {}
	  			
		  // Complementacion
  	    try {
	  	enhancedObject.setComplementacion(transferObject.getComplementacion());
	  	} catch (Throwable th ) {}
	  			
		  // ComplementacionConflictiva
  	    try {
	  	enhancedObject.setComplementacionConflictiva(transferObject.getComplementacionConflictiva());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}