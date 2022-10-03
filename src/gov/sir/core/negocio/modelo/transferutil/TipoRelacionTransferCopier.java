package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoRelacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoRelacion transferObject = new gov.sir.core.negocio.modelo.TipoRelacion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoRelacion
  	    try {
	  	transferObject.setIdTipoRelacion(enhancedObject.getIdTipoRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // Reporte
  	    try {
	  	transferObject.setReporte(enhancedObject.getReporte());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoRelacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoRelacion
  	    try {
	  	enhancedObject.setIdTipoRelacion(transferObject.getIdTipoRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // Reporte
  	    try {
	  	enhancedObject.setReporte(transferObject.getReporte());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}