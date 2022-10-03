package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EntidadPublicaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.EntidadPublica transferObject = new gov.sir.core.negocio.modelo.EntidadPublica();
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
	  			
		  // NaturalezaJuridica
  	  gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced naturalezaJuridicaEnh = null;
	  try {
	  	naturalezaJuridicaEnh = enhancedObject.getNaturalezaJuridica();
	  	} catch (Throwable th) {}
	  	if (naturalezaJuridicaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad objTo =  (gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)cache.get(naturalezaJuridicaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)NaturalezaJuridicaEntidadTransferCopier.deepCopy(naturalezaJuridicaEnh, cache);
	  		}
	  		transferObject.setNaturalezaJuridica(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
		  // Nit
  	    try {
	  	transferObject.setNit(enhancedObject.getNit());
	  	} catch (Throwable th ) {}
	  			
		  // IdEntidadPublica
  	    try {
	  	transferObject.setIdEntidadPublica(enhancedObject.getIdEntidadPublica());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.EntidadPublica transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced();
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
	  			
		  // NaturalezaJuridica
  	    gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad naturalezaJuridica = null;
	  	try {
	  	naturalezaJuridica = (gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)transferObject.getNaturalezaJuridica();
	  	} catch (Throwable th ) {}
	  	if (naturalezaJuridica != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced)cache.get(naturalezaJuridica);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced)NaturalezaJuridicaEntidadTransferCopier.deepCopy(naturalezaJuridica, cache);
	  		}
	  		enhancedObject.setNaturalezaJuridica(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		  // Nit
  	    try {
	  	enhancedObject.setNit(transferObject.getNit());
	  	} catch (Throwable th ) {}
	  			
		  // IdEntidadPublica
  	    try {
	  	enhancedObject.setIdEntidadPublica(transferObject.getIdEntidadPublica());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}