package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AlcanceGeograficoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.AlcanceGeografico transferObject = new gov.sir.core.negocio.modelo.AlcanceGeografico();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdAlcanceGeografico
  	    try {
	  	transferObject.setIdAlcanceGeografico(enhancedObject.getIdAlcanceGeografico());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.AlcanceGeografico transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdAlcanceGeografico
  	    try {
	  	enhancedObject.setIdAlcanceGeografico(transferObject.getIdAlcanceGeografico());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}