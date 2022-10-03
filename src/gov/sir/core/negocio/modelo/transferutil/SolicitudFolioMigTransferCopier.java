package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolicitudFolioMigTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SolicitudFolioMig transferObject = new gov.sir.core.negocio.modelo.SolicitudFolioMig();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // CreadoSir
    try {
  	transferObject.setCreadoSir(enhancedObject.isCreadoSir());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SolicitudFolioMig transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // CreadoSir
      try {
  	enhancedObject.setCreadoSir(transferObject.isCreadoSir());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}