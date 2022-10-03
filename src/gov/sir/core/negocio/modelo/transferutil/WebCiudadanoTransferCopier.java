package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebCiudadanoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebCiudadano transferObject = new gov.sir.core.negocio.modelo.WebCiudadano();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdCiudadano
  	    try {
	  	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	  gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced anotacionEnh = null;
	  try {
	  	anotacionEnh = enhancedObject.getAnotacion();
	  	} catch (Throwable th) {}
	  	if (anotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebAnotacion objTo =  (gov.sir.core.negocio.modelo.WebAnotacion)cache.get(anotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebAnotacion)WebAnotacionTransferCopier.deepCopy(anotacionEnh, cache);
	  		}
	  		transferObject.setAnotacion(objTo);
	  		}
	  			  		
	  	}
            
            // TipoPersona
  	    try {
	  	transferObject.setTipoPersona(enhancedObject.getTipoPersona());
	  	} catch (Throwable th ) {}
	  			
		  // Sexo
  	    try {
	  	transferObject.setSexo(enhancedObject.getSexo());
	  	} catch (Throwable th ) {}
            
		  // Apellido1
  	    try {
	  	transferObject.setApellido1(enhancedObject.getApellido1());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2
  	    try {
	  	transferObject.setApellido2(enhancedObject.getApellido2());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocumento
  	    try {
	  	transferObject.setTipoDocumento(enhancedObject.getTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	transferObject.setPorcentaje(enhancedObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Propietario
  	    try {
	  	transferObject.setPropietario(enhancedObject.getPropietario());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebCiudadano
  	    try {
	  	transferObject.setIdWebCiudadano(enhancedObject.getIdWebCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumDocumento
  	    try {
	  	transferObject.setNumDocumento(enhancedObject.getNumDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // TipoIntervencion
  	    try {
	  	transferObject.setTipoIntervencion(enhancedObject.getTipoIntervencion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebCiudadano transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdCiudadano
  	    try {
	  	enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	    gov.sir.core.negocio.modelo.WebAnotacion anotacion = null;
	  	try {
	  	anotacion = (gov.sir.core.negocio.modelo.WebAnotacion)transferObject.getAnotacion();
	  	} catch (Throwable th ) {}
	  	if (anotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced)cache.get(anotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced)WebAnotacionTransferCopier.deepCopy(anotacion, cache);
	  		}
	  		enhancedObject.setAnotacion(objEn);
	  		}
	  			  		
	  	}
	  			
            // TipoPersona
  	    try {
	  	enhancedObject.setTipoPersona(transferObject.getTipoPersona());
	  	} catch (Throwable th ) {}
	  			
		  // Sexo
  	    try {
	  	enhancedObject.setSexo(transferObject.getSexo());
	  	} catch (Throwable th ) {}
            
		  // Apellido1
  	    try {
	  	enhancedObject.setApellido1(transferObject.getApellido1());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2
  	    try {
	  	enhancedObject.setApellido2(transferObject.getApellido2());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocumento
  	    try {
	  	enhancedObject.setTipoDocumento(transferObject.getTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	enhancedObject.setPorcentaje(transferObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Propietario
  	    try {
	  	enhancedObject.setPropietario(transferObject.getPropietario());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebCiudadano
  	    try {
	  	enhancedObject.setIdWebCiudadano(transferObject.getIdWebCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumDocumento
  	    try {
	  	enhancedObject.setNumDocumento(transferObject.getNumDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // TipoIntervencion
  	    try {
	  	enhancedObject.setTipoIntervencion(transferObject.getTipoIntervencion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}