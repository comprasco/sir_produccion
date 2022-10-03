package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoCertificadoValidacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoCertificadoValidacion transferObject = new gov.sir.core.negocio.modelo.TipoCertificadoValidacion();
		cache.put(enhancedObject, transferObject );
		
		  // TipoCertificado
  	  gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced tipoCertificadoEnh = null;
	  try {
	  	tipoCertificadoEnh = enhancedObject.getTipoCertificado();
	  	} catch (Throwable th) {}
	  	if (tipoCertificadoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoCertificado objTo =  (gov.sir.core.negocio.modelo.TipoCertificado)cache.get(tipoCertificadoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoCertificado)TipoCertificadoTransferCopier.deepCopy(tipoCertificadoEnh, cache);
	  		}
	  		transferObject.setTipoCertificado(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdTipoCertificado
  	    try {
	  	transferObject.setIdTipoCertificado(enhancedObject.getIdTipoCertificado());
	  	} catch (Throwable th ) {}
	  			
		  // IdValidacion
  	    try {
	  	transferObject.setIdValidacion(enhancedObject.getIdValidacion());
	  	} catch (Throwable th ) {}
	  			
		  // Validacion
  	  gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced validacionEnh = null;
	  try {
	  	validacionEnh = enhancedObject.getValidacion();
	  	} catch (Throwable th) {}
	  	if (validacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Validacion objTo =  (gov.sir.core.negocio.modelo.Validacion)cache.get(validacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Validacion)ValidacionTransferCopier.deepCopy(validacionEnh, cache);
	  		}
	  		transferObject.setValidacion(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoCertificadoValidacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // TipoCertificado
  	    gov.sir.core.negocio.modelo.TipoCertificado tipoCertificado = null;
	  	try {
	  	tipoCertificado = (gov.sir.core.negocio.modelo.TipoCertificado)transferObject.getTipoCertificado();
	  	} catch (Throwable th ) {}
	  	if (tipoCertificado != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced)cache.get(tipoCertificado);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced)TipoCertificadoTransferCopier.deepCopy(tipoCertificado, cache);
	  		}
	  		enhancedObject.setTipoCertificado(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdTipoCertificado
  	    try {
	  	enhancedObject.setIdTipoCertificado(transferObject.getIdTipoCertificado());
	  	} catch (Throwable th ) {}
	  			
		  // IdValidacion
  	    try {
	  	enhancedObject.setIdValidacion(transferObject.getIdValidacion());
	  	} catch (Throwable th ) {}
	  			
		  // Validacion
  	    gov.sir.core.negocio.modelo.Validacion validacion = null;
	  	try {
	  	validacion = (gov.sir.core.negocio.modelo.Validacion)transferObject.getValidacion();
	  	} catch (Throwable th ) {}
	  	if (validacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced)cache.get(validacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced)ValidacionTransferCopier.deepCopy(validacion, cache);
	  		}
	  		enhancedObject.setValidacion(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}