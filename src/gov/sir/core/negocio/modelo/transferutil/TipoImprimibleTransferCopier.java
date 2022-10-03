package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoImprimibleTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoImprimible transferObject = new gov.sir.core.negocio.modelo.TipoImprimible();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImprimible
  	    try {
	  	transferObject.setIdTipoImprimible(enhancedObject.getIdTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoImprimible transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdTipoImprimible
  	    try {
	  	enhancedObject.setIdTipoImprimible(transferObject.getIdTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}