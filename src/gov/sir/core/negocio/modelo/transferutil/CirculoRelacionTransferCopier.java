package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoRelacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoRelacion transferObject = new gov.sir.core.negocio.modelo.CirculoRelacion();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRelacion
  	    try {
	  	transferObject.setLastIdRelacion(enhancedObject.getLastIdRelacion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoRelacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRelacion
  	    try {
	  	enhancedObject.setLastIdRelacion(transferObject.getLastIdRelacion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}