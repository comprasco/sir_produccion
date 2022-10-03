package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OtorganteNaturalTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.OtorganteNatural transferObject = new gov.sir.core.negocio.modelo.OtorganteNatural();
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
	  			
		  // IdOtorgante
  	    try {
	  	transferObject.setIdOtorgante(enhancedObject.getIdOtorgante());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.OtorganteNatural transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced();
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
	  			
		  // IdOtorgante
  	    try {
	  	enhancedObject.setIdOtorgante(transferObject.getIdOtorgante());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}