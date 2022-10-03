package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ImpresionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Impresion transferObject = new gov.sir.core.negocio.modelo.Impresion();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdSesion
  	    try {
	  	transferObject.setIdSesion(enhancedObject.getIdSesion());
	  	} catch (Throwable th ) {}
	  			
		  // DireccionIP
  	    try {
	  	transferObject.setDireccionIP(enhancedObject.getDireccionIP());
	  	} catch (Throwable th ) {}
	  			
		  // Puerto
  	    try {
	  	transferObject.setPuerto(enhancedObject.getPuerto());
	  	} catch (Throwable th ) {}
	  			
		  // Administrador
    try {
  	transferObject.setAdministrador(enhancedObject.isAdministrador());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Impresion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdSesion
  	    try {
	  	enhancedObject.setIdSesion(transferObject.getIdSesion());
	  	} catch (Throwable th ) {}
	  			
		  // DireccionIP
  	    try {
	  	enhancedObject.setDireccionIP(transferObject.getDireccionIP());
	  	} catch (Throwable th ) {}
	  			
		  // Puerto
  	    try {
	  	enhancedObject.setPuerto(transferObject.getPuerto());
	  	} catch (Throwable th ) {}
	  			
		  // Administrador
      try {
  	enhancedObject.setAdministrador(transferObject.isAdministrador());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}