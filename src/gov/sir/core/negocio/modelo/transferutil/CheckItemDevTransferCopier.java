package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CheckItemDevTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CheckItemDev transferObject = new gov.sir.core.negocio.modelo.CheckItemDev();
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
	  			
		  // Obligatorio
    try {
  	transferObject.setObligatorio(enhancedObject.isObligatorio());
  } catch (Throwable th ) {}
  			
		  // IdCheckItemDev
  	    try {
	  	transferObject.setIdCheckItemDev(enhancedObject.getIdCheckItemDev());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CheckItemDev transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced();
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
	  			
		  // Obligatorio
      try {
  	enhancedObject.setObligatorio(transferObject.isObligatorio());
  	} catch (Throwable th ) {}
  			
		  // IdCheckItemDev
  	    try {
	  	enhancedObject.setIdCheckItemDev(transferObject.getIdCheckItemDev());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}