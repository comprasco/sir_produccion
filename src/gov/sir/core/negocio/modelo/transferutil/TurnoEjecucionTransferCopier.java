package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoEjecucionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoEjecucion transferObject = new gov.sir.core.negocio.modelo.TurnoEjecucion();
		cache.put(enhancedObject, transferObject );
		
		  // IdWorkflow
  	    try {
	  	transferObject.setIdWorkflow(enhancedObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	    try {
	  	transferObject.setEstado(enhancedObject.getEstado());
	  	} catch (Throwable th ) {}
	  			
		  // Estacion
  	    try {
	  	transferObject.setEstacion(enhancedObject.getEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // Fase
  	    try {
	  	transferObject.setFase(enhancedObject.getFase());
	  	} catch (Throwable th ) {}
	  			
		  // HasWF
    try {
  	transferObject.setHasWF(enhancedObject.isHasWF());
  } catch (Throwable th ) {}
  			
		  // NotificacionWF
  	    try {
	  	transferObject.setNotificacionWF(enhancedObject.getNotificacionWF());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoEjecucion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdWorkflow
  	    try {
	  	enhancedObject.setIdWorkflow(transferObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	    try {
	  	enhancedObject.setEstado(transferObject.getEstado());
	  	} catch (Throwable th ) {}
	  			
		  // Estacion
  	    try {
	  	enhancedObject.setEstacion(transferObject.getEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // Fase
  	    try {
	  	enhancedObject.setFase(transferObject.getFase());
	  	} catch (Throwable th ) {}
	  			
		  // HasWF
      try {
  	enhancedObject.setHasWF(transferObject.isHasWF());
  	} catch (Throwable th ) {}
  			
		  // NotificacionWF
  	    try {
	  	enhancedObject.setNotificacionWF(transferObject.getNotificacionWF());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}