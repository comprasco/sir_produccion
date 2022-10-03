package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoDocumentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoDocumento transferObject = new gov.sir.core.negocio.modelo.TipoDocumento();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoDocumento
  	    try {
	  	transferObject.setIdTipoDocumento(enhancedObject.getIdTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoDocumento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoDocumento
  	    try {
	  	enhancedObject.setIdTipoDocumento(transferObject.getIdTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}