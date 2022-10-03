package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ConsignacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Consignacion transferObject = new gov.sir.core.negocio.modelo.Consignacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdIdDocPago
	  	try {
		  	transferObject.setIdDocPago(enhancedObject.getIdDocPago());
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
	  	
	  	 // DocPago
	  	  gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced documentoPagoEnh = null;
		  try {
		  	documentoPagoEnh = enhancedObject.getDocPago();
		  	} catch (Throwable th) {}
		  	if (documentoPagoEnh != null)
		  	{
		  		boolean assigned = false;
		  			  		
		  		if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago)cache.get(documentoPagoEnh);
		  		if (objTo == null)
		  		{
		  			objTo = (gov.sir.core.negocio.modelo.DocumentoPago)DocPagoChequeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced)documentoPagoEnh, cache);
		  		}
		  		transferObject.setDocPago(objTo);
		  		assigned = true;
		  		}
		  			  		
		  		if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago)cache.get(documentoPagoEnh);
		  		if (objTo == null)
		  		{
		  			objTo = (gov.sir.core.negocio.modelo.DocumentoPago)DocPagoConsignacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced)documentoPagoEnh, cache);
		  		}
		  		transferObject.setDocPago(objTo);
		  		assigned = true;
		  		}
		  	}
		  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Consignacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  		
	  	 // IdDocPago
	  	try {
	  		enhancedObject.setIdDocPago(transferObject.getIdDocPago());
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
	  	
	 // DocPago
  	    gov.sir.core.negocio.modelo.DocumentoPago documentoPago = null;
	  	try {
	  	documentoPago = (gov.sir.core.negocio.modelo.DocumentoPago)transferObject.getDocPago();
	  	} catch (Throwable th ) {}
	  	if (documentoPago != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoCheque)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced)cache.get(documentoPago);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced)DocPagoChequeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoCheque)documentoPago, cache);
	  		}
	  		enhancedObject.setDocPago(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoConsignacion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced)cache.get(documentoPago);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced)DocPagoConsignacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoConsignacion)documentoPago, cache);
	  		}
	  		enhancedObject.setDocPago(objEn);
	  		assigned = true;
	  		}
	  			  			  		
	  	}
		
		
		return enhancedObject;
	}
}