package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SubAtencionSubSolicitudTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SubAtencionSubSolicitud transferObject = new gov.sir.core.negocio.modelo.SubAtencionSubSolicitud();
		cache.put(enhancedObject, transferObject );
		
		  // IdSubtipoSol
  	    try {
	  	transferObject.setIdSubtipoSol(enhancedObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // SubtipoSolicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced subtipoSolicitudEnh = null;
	  try {
	  	subtipoSolicitudEnh = enhancedObject.getSubtipoSolicitud();
	  	} catch (Throwable th) {}
	  	if (subtipoSolicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SubtipoSolicitud objTo =  (gov.sir.core.negocio.modelo.SubtipoSolicitud)cache.get(subtipoSolicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SubtipoSolicitud)SubtipoSolicitudTransferCopier.deepCopy(subtipoSolicitudEnh, cache);
	  		}
	  		transferObject.setSubtipoSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // SubtipoAtencion
  	  gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced subtipoAtencionEnh = null;
	  try {
	  	subtipoAtencionEnh = enhancedObject.getSubtipoAtencion();
	  	} catch (Throwable th) {}
	  	if (subtipoAtencionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SubtipoAtencion objTo =  (gov.sir.core.negocio.modelo.SubtipoAtencion)cache.get(subtipoAtencionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SubtipoAtencion)SubtipoAtencionTransferCopier.deepCopy(subtipoAtencionEnh, cache);
	  		}
	  		transferObject.setSubtipoAtencion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	transferObject.setIdSubtipoAtencion(enhancedObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SubAtencionSubSolicitud transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSubtipoSol
  	    try {
	  	enhancedObject.setIdSubtipoSol(transferObject.getIdSubtipoSol());
	  	} catch (Throwable th ) {}
	  			
		  // SubtipoSolicitud
  	    gov.sir.core.negocio.modelo.SubtipoSolicitud subtipoSolicitud = null;
	  	try {
	  	subtipoSolicitud = (gov.sir.core.negocio.modelo.SubtipoSolicitud)transferObject.getSubtipoSolicitud();
	  	} catch (Throwable th ) {}
	  	if (subtipoSolicitud != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced)cache.get(subtipoSolicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced)SubtipoSolicitudTransferCopier.deepCopy(subtipoSolicitud, cache);
	  		}
	  		enhancedObject.setSubtipoSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // SubtipoAtencion
  	    gov.sir.core.negocio.modelo.SubtipoAtencion subtipoAtencion = null;
	  	try {
	  	subtipoAtencion = (gov.sir.core.negocio.modelo.SubtipoAtencion)transferObject.getSubtipoAtencion();
	  	} catch (Throwable th ) {}
	  	if (subtipoAtencion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced)cache.get(subtipoAtencion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced)SubtipoAtencionTransferCopier.deepCopy(subtipoAtencion, cache);
	  		}
	  		enhancedObject.setSubtipoAtencion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	enhancedObject.setIdSubtipoAtencion(transferObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}