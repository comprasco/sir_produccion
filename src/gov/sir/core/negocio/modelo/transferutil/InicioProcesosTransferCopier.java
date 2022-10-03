package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class InicioProcesosTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.InicioProcesos transferObject = new gov.sir.core.negocio.modelo.InicioProcesos();
		cache.put(enhancedObject, transferObject );
		
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	transferObject.setIdRol(enhancedObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.InicioProcesos transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	enhancedObject.setIdRol(transferObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}