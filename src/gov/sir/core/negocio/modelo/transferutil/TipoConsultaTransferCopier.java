package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoConsultaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoConsulta transferObject = new gov.sir.core.negocio.modelo.TipoConsulta();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoConsulta
  	    try {
	  	transferObject.setIdTipoConsulta(enhancedObject.getIdTipoConsulta());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoConsulta transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoConsulta
  	    try {
	  	enhancedObject.setIdTipoConsulta(transferObject.getIdTipoConsulta());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}