package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoFolioTramiteMigTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoFolioTramiteMig transferObject = new gov.sir.core.negocio.modelo.TurnoFolioTramiteMig();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
    try {
  	transferObject.setAnulado(enhancedObject.isAnulado());
  } catch (Throwable th ) {}
  			
		  // IdFolio
  	    try {
	  	transferObject.setIdFolio(enhancedObject.getIdFolio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	transferObject.setIdTurno(enhancedObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // SecProceso
  	    try {
	  	transferObject.setSecProceso(enhancedObject.getSecProceso());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoFolioTramiteMig transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
      try {
  	enhancedObject.setAnulado(transferObject.isAnulado());
  	} catch (Throwable th ) {}
  			
		  // IdFolio
  	    try {
	  	enhancedObject.setIdFolio(transferObject.getIdFolio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	enhancedObject.setIdTurno(transferObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // SecProceso
  	    try {
	  	enhancedObject.setSecProceso(transferObject.getSecProceso());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}