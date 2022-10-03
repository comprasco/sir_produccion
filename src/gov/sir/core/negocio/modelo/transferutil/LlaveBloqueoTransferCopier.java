package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class LlaveBloqueoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.LlaveBloqueo transferObject = new gov.sir.core.negocio.modelo.LlaveBloqueo();
		cache.put(enhancedObject, transferObject );
		
		  // IdLlave
  	    try {
	  	transferObject.setIdLlave(enhancedObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // BloqueoFolio
    	List bloqueoFolio = null;
  	try
  	{
  	bloqueoFolio = enhancedObject.getBloqueoFolios();
  	} catch (Throwable th) {}
	  	if (bloqueoFolio != null)
	  	{
		  	for(Iterator itera = bloqueoFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced bloqueoFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.BloqueoFolio objTo = (gov.sir.core.negocio.modelo.BloqueoFolio)cache.get(bloqueoFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.BloqueoFolio)BloqueoFolioTransferCopier.deepCopy(bloqueoFolioEnh, cache);
		  		}
		  		transferObject.addBloqueoFolio(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.LlaveBloqueo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdLlave
  	    try {
	  	enhancedObject.setIdLlave(transferObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // BloqueoFolio
    	List bloqueoFolio = null;
  	try
  	{
  	bloqueoFolio = transferObject.getBloqueoFolios();
  	} catch (Throwable th) { }
	  	if (bloqueoFolio != null)
	  	{
		  	for(Iterator itera = bloqueoFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.BloqueoFolio bloqueoFolioto = (gov.sir.core.negocio.modelo.BloqueoFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)cache.get(bloqueoFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)BloqueoFolioTransferCopier.deepCopy(bloqueoFolioto, cache);
		  		}
		  		enhancedObject.addBloqueoFolio(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}