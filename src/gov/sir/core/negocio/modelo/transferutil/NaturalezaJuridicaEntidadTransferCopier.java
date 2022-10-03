package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class NaturalezaJuridicaEntidadTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad transferObject = new gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad();
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
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
		  // Exento
    try {
  	transferObject.setExento(enhancedObject.isExento());
  } catch (Throwable th ) {}
  			
		  // IdNatJuridicaEntidad
  	    try {
	  	transferObject.setIdNatJuridicaEntidad(enhancedObject.getIdNatJuridicaEntidad());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced();
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
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		  // Exento
      try {
  	enhancedObject.setExento(transferObject.isExento());
  	} catch (Throwable th ) {}
  			
		  // IdNatJuridicaEntidad
  	    try {
	  	enhancedObject.setIdNatJuridicaEntidad(transferObject.getIdNatJuridicaEntidad());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}