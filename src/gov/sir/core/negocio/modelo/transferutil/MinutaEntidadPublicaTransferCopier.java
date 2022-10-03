package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class MinutaEntidadPublicaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.MinutaEntidadPublica transferObject = new gov.sir.core.negocio.modelo.MinutaEntidadPublica();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdEntidadPublica
  	    try {
	  	transferObject.setIdEntidadPublica(enhancedObject.getIdEntidadPublica());
	  	} catch (Throwable th ) {}
	  			
		  // Exento
    try {
  	transferObject.setExento(enhancedObject.isExento());
  } catch (Throwable th ) {}
  			
		  // IdMinuta
  	    try {
	  	transferObject.setIdMinuta(enhancedObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // Minuta
  	  gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced minutaEnh = null;
	  try {
	  	minutaEnh = enhancedObject.getMinuta();
	  	} catch (Throwable th) {}
	  	if (minutaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Minuta objTo =  (gov.sir.core.negocio.modelo.Minuta)cache.get(minutaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Minuta)MinutaTransferCopier.deepCopy(minutaEnh, cache);
	  		}
	  		transferObject.setMinuta(objTo);
	  		}
	  			  		
	  	}
	  			
		  // EntidadPublica
  	  gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced entidadPublicaEnh = null;
	  try {
	  	entidadPublicaEnh = enhancedObject.getEntidadPublica();
	  	} catch (Throwable th) {}
	  	if (entidadPublicaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.EntidadPublica objTo =  (gov.sir.core.negocio.modelo.EntidadPublica)cache.get(entidadPublicaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.EntidadPublica)EntidadPublicaTransferCopier.deepCopy(entidadPublicaEnh, cache);
	  		}
	  		transferObject.setEntidadPublica(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.MinutaEntidadPublica transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdEntidadPublica
  	    try {
	  	enhancedObject.setIdEntidadPublica(transferObject.getIdEntidadPublica());
	  	} catch (Throwable th ) {}
	  			
		  // Exento
      try {
  	enhancedObject.setExento(transferObject.isExento());
  	} catch (Throwable th ) {}
  			
		  // IdMinuta
  	    try {
	  	enhancedObject.setIdMinuta(transferObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // Minuta
  	    gov.sir.core.negocio.modelo.Minuta minuta = null;
	  	try {
	  	minuta = (gov.sir.core.negocio.modelo.Minuta)transferObject.getMinuta();
	  	} catch (Throwable th ) {}
	  	if (minuta != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)cache.get(minuta);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)MinutaTransferCopier.deepCopy(minuta, cache);
	  		}
	  		enhancedObject.setMinuta(objEn);
	  		}
	  			  		
	  	}
	  			
		  // EntidadPublica
  	    gov.sir.core.negocio.modelo.EntidadPublica entidadPublica = null;
	  	try {
	  	entidadPublica = (gov.sir.core.negocio.modelo.EntidadPublica)transferObject.getEntidadPublica();
	  	} catch (Throwable th ) {}
	  	if (entidadPublica != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced)cache.get(entidadPublica);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced)EntidadPublicaTransferCopier.deepCopy(entidadPublica, cache);
	  		}
	  		enhancedObject.setEntidadPublica(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}