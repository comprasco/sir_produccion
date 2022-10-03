package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoFolioMigTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoFolioMig transferObject = new gov.sir.core.negocio.modelo.TurnoFolioMig();
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
	  			
		  // CreadoSir
    try {
  	transferObject.setCreadoSir(enhancedObject.isCreadoSir());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoFolioMig transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced();
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
	  			
		  // CreadoSir
      try {
  	enhancedObject.setCreadoSir(transferObject.isCreadoSir());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}