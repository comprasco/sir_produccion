package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoHorarioNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoHorarioNotarial transferObject = new gov.sir.core.negocio.modelo.TipoHorarioNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoHorarioRepartoNotarial
  	    try {
	  	transferObject.setIdTipoHorarioRepartoNotarial(enhancedObject.getIdTipoHorarioRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoHorarioNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoHorarioNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoHorarioRepartoNotarial
  	    try {
	  	enhancedObject.setIdTipoHorarioRepartoNotarial(transferObject.getIdTipoHorarioRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}