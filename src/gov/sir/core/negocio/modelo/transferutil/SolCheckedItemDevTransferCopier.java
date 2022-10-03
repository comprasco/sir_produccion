package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolCheckedItemDevTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SolCheckedItemDev transferObject = new gov.sir.core.negocio.modelo.SolCheckedItemDev();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SolicitudDevolucion objTo =  (gov.sir.core.negocio.modelo.SolicitudDevolucion)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SolicitudDevolucion)SolicitudDevolucionTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdCheckItemDev
  	    try {
	  	transferObject.setIdCheckItemDev(enhancedObject.getIdCheckItemDev());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
  	  gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced checkItemEnh = null;
	  try {
	  	checkItemEnh = enhancedObject.getCheckItem();
	  	} catch (Throwable th) {}
	  	if (checkItemEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CheckItemDev objTo =  (gov.sir.core.negocio.modelo.CheckItemDev)cache.get(checkItemEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CheckItemDev)CheckItemDevTransferCopier.deepCopy(checkItemEnh, cache);
	  		}
	  		transferObject.setCheckItem(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SolCheckedItemDev transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.SolicitudDevolucion solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.SolicitudDevolucion)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)SolicitudDevolucionTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdCheckItemDev
  	    try {
	  	enhancedObject.setIdCheckItemDev(transferObject.getIdCheckItemDev());
	  	} catch (Throwable th ) {}
	  			
		  // CheckItem
  	    gov.sir.core.negocio.modelo.CheckItemDev checkItem = null;
	  	try {
	  	checkItem = (gov.sir.core.negocio.modelo.CheckItemDev)transferObject.getCheckItem();
	  	} catch (Throwable th ) {}
	  	if (checkItem != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced)cache.get(checkItem);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced)CheckItemDevTransferCopier.deepCopy(checkItem, cache);
	  		}
	  		enhancedObject.setCheckItem(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}