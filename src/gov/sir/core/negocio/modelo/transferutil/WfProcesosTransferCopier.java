package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WfProcesosTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WfProcesos transferObject = new gov.sir.core.negocio.modelo.WfProcesos();
		cache.put(enhancedObject, transferObject );
		
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	transferObject.setIdRol(enhancedObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	transferObject.setRespuesta(enhancedObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseFinal
  	    try {
	  	transferObject.setIdFaseFinal(enhancedObject.getIdFaseFinal());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseInicial
  	    try {
	  	transferObject.setIdFaseInicial(enhancedObject.getIdFaseInicial());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WfProcesos transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WfProcesosEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	enhancedObject.setIdRol(transferObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	enhancedObject.setRespuesta(transferObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseFinal
  	    try {
	  	enhancedObject.setIdFaseFinal(transferObject.getIdFaseFinal());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseInicial
  	    try {
	  	enhancedObject.setIdFaseInicial(transferObject.getIdFaseInicial());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}