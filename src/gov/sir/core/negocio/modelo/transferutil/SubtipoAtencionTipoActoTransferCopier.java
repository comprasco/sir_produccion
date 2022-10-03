package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SubtipoAtencionTipoActoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo transferObject = new gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo();
		cache.put(enhancedObject, transferObject );
		
		  // TipoActo
  	  gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced tipoActoEnh = null;
	  try {
	  	tipoActoEnh = enhancedObject.getTipoActo();
	  	} catch (Throwable th) {}
	  	if (tipoActoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoActo objTo =  (gov.sir.core.negocio.modelo.TipoActo)cache.get(tipoActoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoActo)TipoActoTransferCopier.deepCopy(tipoActoEnh, cache);
	  		}
	  		transferObject.setTipoActo(objTo);
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
	  			
		  // IdTipoActo
  	    try {
	  	transferObject.setIdTipoActo(enhancedObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // TipoActo
  	    gov.sir.core.negocio.modelo.TipoActo tipoActo = null;
	  	try {
	  	tipoActo = (gov.sir.core.negocio.modelo.TipoActo)transferObject.getTipoActo();
	  	} catch (Throwable th ) {}
	  	if (tipoActo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)cache.get(tipoActo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)TipoActoTransferCopier.deepCopy(tipoActo, cache);
	  		}
	  		enhancedObject.setTipoActo(objEn);
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
	  			
		  // IdTipoActo
  	    try {
	  	enhancedObject.setIdTipoActo(transferObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}