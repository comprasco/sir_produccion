package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CheckItemTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CheckItem transferObject = new gov.sir.core.negocio.modelo.CheckItem();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCheckItem
  	    try {
	  	transferObject.setIdCheckItem(enhancedObject.getIdCheckItem());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Obligatorio
    try {
  	transferObject.setObligatorio(enhancedObject.isObligatorio());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CheckItem transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCheckItem
  	    try {
	  	enhancedObject.setIdCheckItem(transferObject.getIdCheckItem());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Obligatorio
      try {
  	enhancedObject.setObligatorio(transferObject.isObligatorio());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}