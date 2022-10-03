package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class RepartoNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.RepartoNotarial transferObject = new gov.sir.core.negocio.modelo.RepartoNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Normal
    try {
  	transferObject.setNormal(enhancedObject.isNormal());
  } catch (Throwable th ) {}
  			
		  // IdRepartoNotarial
  	    try {
	  	transferObject.setIdRepartoNotarial(enhancedObject.getIdRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // Minuta
    	List minuta = null;
  	try
  	{
  	minuta = enhancedObject.getMinutas();
  	} catch (Throwable th) {}
	  	if (minuta != null)
	  	{
		  	for(Iterator itera = minuta.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced minutaEnh = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Minuta objTo = (gov.sir.core.negocio.modelo.Minuta)cache.get(minutaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Minuta)MinutaTransferCopier.deepCopy(minutaEnh, cache);
		  		}
		  		transferObject.addMinuta(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.RepartoNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Normal
      try {
  	enhancedObject.setNormal(transferObject.isNormal());
  	} catch (Throwable th ) {}
  			
		  // IdRepartoNotarial
  	    try {
	  	enhancedObject.setIdRepartoNotarial(transferObject.getIdRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // Minuta
    	List minuta = null;
  	try
  	{
  	minuta = transferObject.getMinutas();
  	} catch (Throwable th) { }
	  	if (minuta != null)
	  	{
		  	for(Iterator itera = minuta.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Minuta minutato = (gov.sir.core.negocio.modelo.Minuta)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)cache.get(minutato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)MinutaTransferCopier.deepCopy(minutato, cache);
		  		}
		  		enhancedObject.addMinuta(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}