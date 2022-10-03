package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CausalRestitucionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CausalRestitucion transferObject = new gov.sir.core.negocio.modelo.CausalRestitucion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
		  // IdCausalRestitucion
  	    try {
	  	transferObject.setIdCausalRestitucion(enhancedObject.getIdCausalRestitucion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CausalRestitucion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		  // IdCausalRestitucion
  	    try {
	  	enhancedObject.setIdCausalRestitucion(transferObject.getIdCausalRestitucion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}