package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class PlantillaPertenenciaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.PlantillaPertenencia transferObject = new gov.sir.core.negocio.modelo.PlantillaPertenencia();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdPlantillaPertenencia
  	    try {
	  	transferObject.setIdPlantillaPertenencia(enhancedObject.getIdPlantillaPertenencia());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	transferObject.setRespuesta(enhancedObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.PlantillaPertenencia transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdPlantillaPertenencia
  	    try {
	  	enhancedObject.setIdPlantillaPertenencia(transferObject.getIdPlantillaPertenencia());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	enhancedObject.setRespuesta(transferObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}