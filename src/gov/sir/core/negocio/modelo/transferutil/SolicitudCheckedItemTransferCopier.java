package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolicitudCheckedItemTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SolicitudCheckedItem transferObject = new gov.sir.core.negocio.modelo.SolicitudCheckedItem();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SolicitudRegistro objTo =  (gov.sir.core.negocio.modelo.SolicitudRegistro)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SolicitudRegistro)SolicitudRegistroTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdCheckItem
  	    try {
	  	transferObject.setIdCheckItem(enhancedObject.getIdCheckItem());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoSol
  	    try {
	  	transferObject.setIdSubtipoSol(enhancedObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
  	  gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced checkItemEnh = null;
	  try {
	  	checkItemEnh = enhancedObject.getCheckItem();
	  	} catch (Throwable th) {}
	  	if (checkItemEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CheckItem objTo =  (gov.sir.core.negocio.modelo.CheckItem)cache.get(checkItemEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CheckItem)CheckItemTransferCopier.deepCopy(checkItemEnh, cache);
	  		}
	  		transferObject.setCheckItem(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SolicitudCheckedItem transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.SolicitudRegistro solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.SolicitudRegistro)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)SolicitudRegistroTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdCheckItem
  	    try {
	  	enhancedObject.setIdCheckItem(transferObject.getIdCheckItem());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoSol
  	    try {
	  	enhancedObject.setIdSubtipoSol(transferObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
  	    gov.sir.core.negocio.modelo.CheckItem checkItem = null;
	  	try {
	  	checkItem = (gov.sir.core.negocio.modelo.CheckItem)transferObject.getCheckItem();
	  	} catch (Throwable th ) {}
	  	if (checkItem != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced)cache.get(checkItem);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced)CheckItemTransferCopier.deepCopy(checkItem, cache);
	  		}
	  		enhancedObject.setCheckItem(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}