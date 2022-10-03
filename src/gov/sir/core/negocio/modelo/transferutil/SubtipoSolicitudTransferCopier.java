package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SubtipoSolicitudTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SubtipoSolicitud transferObject = new gov.sir.core.negocio.modelo.SubtipoSolicitud();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoSol
  	    try {
	  	transferObject.setIdSubtipoSol(enhancedObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
    	List checkItem = null;
  	try
  	{
  	checkItem = enhancedObject.getCheckItems();
  	} catch (Throwable th) {}
	  	if (checkItem != null)
	  	{
		  	for(Iterator itera = checkItem.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced checkItemEnh = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.CheckItem objTo = (gov.sir.core.negocio.modelo.CheckItem)cache.get(checkItemEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.CheckItem)CheckItemTransferCopier.deepCopy(checkItemEnh, cache);
		  		}
		  		transferObject.addCheckItem(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SubtipoSolicitud transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoSol
  	    try {
	  	enhancedObject.setIdSubtipoSol(transferObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
    	List checkItem = null;
  	try
  	{
  	checkItem = transferObject.getCheckItems();
  	} catch (Throwable th) { }
	  	if (checkItem != null)
	  	{
		  	for(Iterator itera = checkItem.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.CheckItem checkItemto = (gov.sir.core.negocio.modelo.CheckItem)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced)cache.get(checkItemto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced)CheckItemTransferCopier.deepCopy(checkItemto, cache);
		  		}
		  		enhancedObject.addCheckItem(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}