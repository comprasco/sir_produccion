package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoPantallaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoPantalla transferObject = new gov.sir.core.negocio.modelo.TipoPantalla();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoPantalla
  	    try {
	  	transferObject.setIdTipoPantalla(enhancedObject.getIdTipoPantalla());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoPantalla transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoPantalla
  	    try {
	  	enhancedObject.setIdTipoPantalla(transferObject.getIdTipoPantalla());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}