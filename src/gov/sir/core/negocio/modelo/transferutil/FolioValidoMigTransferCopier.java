package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FolioValidoMigTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.FolioValidoMig transferObject = new gov.sir.core.negocio.modelo.FolioValidoMig();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
    try {
  	transferObject.setAnulado(enhancedObject.isAnulado());
  } catch (Throwable th ) {}
  			
		  // IdFolio
  	    try {
	  	transferObject.setIdFolio(enhancedObject.getIdFolio());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.FolioValidoMig transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FolioValidoMigEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
      try {
  	enhancedObject.setAnulado(transferObject.isAnulado());
  	} catch (Throwable th ) {}
  			
		  // IdFolio
  	    try {
	  	enhancedObject.setIdFolio(transferObject.getIdFolio());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}