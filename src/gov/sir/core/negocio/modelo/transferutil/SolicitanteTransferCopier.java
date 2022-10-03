package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolicitanteTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Solicitante transferObject = new gov.sir.core.negocio.modelo.Solicitante();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdCiudadano
	  	try {
		  	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
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
	  	
	 // Ciudadano
	  	  gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced ciudadanoEnh = null;
		  try {
			  ciudadanoEnh = enhancedObject.getCiudadano();
		  	} catch (Throwable th) {}
		  	if (ciudadanoEnh != null)
		  	{
		  		boolean assigned = false;
		  			  			  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Ciudadano objTo =  (gov.sir.core.negocio.modelo.Ciudadano)cache.get(ciudadanoEnh);
		  		if (objTo == null)
		  		{
		  			objTo = (gov.sir.core.negocio.modelo.Ciudadano)CiudadanoTransferCopier.deepCopy(ciudadanoEnh, cache);
		  		}
		  		transferObject.setCiudadano(objTo);
		  		}
		  			  		
		  	}
	  			
		  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Solicitante transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  		
	  	 // IdCiudadano
	  	try {
	  		enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
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
	  	
	 // Ciudadano
  	    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
	  	try {
	  		ciudadano = (gov.sir.core.negocio.modelo.Ciudadano)transferObject.getCiudadano();
	  	} catch (Throwable th ) {}
	  	if (ciudadano != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)cache.get(ciudadano);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)CiudadanoTransferCopier.deepCopy(ciudadano, cache);
	  		}
	  		enhancedObject.setCiudadano(objEn);
	  		}
	  			  		
	  	}
	  			
		
		
		return enhancedObject;
	}
}