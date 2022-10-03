package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ResultadoAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ResultadoAnotacion transferObject = new gov.sir.core.negocio.modelo.ResultadoAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced anotacionEnh = null;
	  try {
	  	anotacionEnh = enhancedObject.getAnotacion();
	  	} catch (Throwable th) {}
	  	if (anotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(anotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(anotacionEnh, cache);
	  		}
	  		transferObject.setAnotacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdBusqueda
  	    try {
	  	transferObject.setIdBusqueda(enhancedObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // ResultadoFolio
  	  gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced resultadoFolioEnh = null;
	  try {
	  	resultadoFolioEnh = enhancedObject.getResultadoFolio();
	  	} catch (Throwable th) {}
	  	if (resultadoFolioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.ResultadoFolio objTo =  (gov.sir.core.negocio.modelo.ResultadoFolio)cache.get(resultadoFolioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.ResultadoFolio)ResultadoFolioTransferCopier.deepCopy(resultadoFolioEnh, cache);
	  		}
	  		transferObject.setResultadoFolio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Propietario
    try {
  	transferObject.setPropietario(enhancedObject.isPropietario());
  } catch (Throwable th ) {}
  			
		  // CiudadanoPropietario
  	  gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced ciudadanoPropietarioEnh = null;
	  try {
	  	ciudadanoPropietarioEnh = enhancedObject.getCiudadanoPropietario();
	  	} catch (Throwable th) {}
	  	if (ciudadanoPropietarioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Ciudadano objTo =  (gov.sir.core.negocio.modelo.Ciudadano)cache.get(ciudadanoPropietarioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Ciudadano)CiudadanoTransferCopier.deepCopy(ciudadanoPropietarioEnh, cache);
	  		}
	  		transferObject.setCiudadanoPropietario(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ResultadoAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	    gov.sir.core.negocio.modelo.Anotacion anotacion = null;
	  	try {
	  	anotacion = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getAnotacion();
	  	} catch (Throwable th ) {}
	  	if (anotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(anotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(anotacion, cache);
	  		}
	  		enhancedObject.setAnotacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdBusqueda
  	    try {
	  	enhancedObject.setIdBusqueda(transferObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // ResultadoFolio
  	    gov.sir.core.negocio.modelo.ResultadoFolio resultadoFolio = null;
	  	try {
	  	resultadoFolio = (gov.sir.core.negocio.modelo.ResultadoFolio)transferObject.getResultadoFolio();
	  	} catch (Throwable th ) {}
	  	if (resultadoFolio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced)cache.get(resultadoFolio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced)ResultadoFolioTransferCopier.deepCopy(resultadoFolio, cache);
	  		}
	  		enhancedObject.setResultadoFolio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Propietario
      try {
  	enhancedObject.setPropietario(transferObject.isPropietario());
  	} catch (Throwable th ) {}
  			
		  // CiudadanoPropietario
  	    gov.sir.core.negocio.modelo.Ciudadano ciudadanoPropietario = null;
	  	try {
	  	ciudadanoPropietario = (gov.sir.core.negocio.modelo.Ciudadano)transferObject.getCiudadanoPropietario();
	  	} catch (Throwable th ) {}
	  	if (ciudadanoPropietario != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)cache.get(ciudadanoPropietario);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)CiudadanoTransferCopier.deepCopy(ciudadanoPropietario, cache);
	  		}
	  		enhancedObject.setCiudadanoPropietario(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}