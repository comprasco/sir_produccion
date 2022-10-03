package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CategoriaNotariaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CategoriaNotaria transferObject = new gov.sir.core.negocio.modelo.CategoriaNotaria();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdCategoria
  	    try {
	  	transferObject.setIdCategoria(enhancedObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CategoriaNotaria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdCategoria
  	    try {
	  	enhancedObject.setIdCategoria(transferObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}