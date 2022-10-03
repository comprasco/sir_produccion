package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FaseTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Fase transferObject = new gov.sir.core.negocio.modelo.Fase();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // ID
  	    try {
	  	transferObject.setID(enhancedObject.getID());
	  	} catch (Throwable th ) {}
	  			
		  // Estacion
  	    try {
	  	transferObject.setEstacion(enhancedObject.getEstacion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Fase transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // ID
  	    try {
	  	enhancedObject.setID(transferObject.getID());
	  	} catch (Throwable th ) {}
	  			
		  // Estacion
  	    try {
	  	enhancedObject.setEstacion(transferObject.getEstacion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}