package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AnotacionCiudadanoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.AnotacionCiudadano transferObject = new gov.sir.core.negocio.modelo.AnotacionCiudadano();
		cache.put(enhancedObject, transferObject );
		
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // ToDelete
	  	try {
	  		transferObject.setToDelete(enhancedObject.isToDelete());
	  	} catch (Throwable th ) {}
  
  		  //ToUpdate
  		try {
  			transferObject.setToUpdate(enhancedObject.isToUpdate());
  		} catch (Throwable th ) {}
  			
		  // IdCiudadano
  	    try {
	  	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // RolPersona
  	    try {
	  	transferObject.setRolPersona(enhancedObject.getRolPersona());
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
	  			
		  // MarcaPropietario
  	    try {
	  	transferObject.setMarcaPropietario(enhancedObject.getMarcaPropietario());
	  	} catch (Throwable th ) {}
	  			
		  // Participacion
  	    try {
	  	transferObject.setParticipacion(enhancedObject.getParticipacion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.AnotacionCiudadano transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // ToDelete
      try {
  	enhancedObject.setToDelete(transferObject.isToDelete());
  	} catch (Throwable th ) {}
  			
  	
  		  //ToUpdate
		try {
			enhancedObject.setToUpdate(transferObject.isToUpdate());
		} catch (Throwable th ) {}
		
		  // IdCiudadano
  	    try {
	  	enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // RolPersona
  	    try {
	  	enhancedObject.setRolPersona(transferObject.getRolPersona());
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
	  			
		  // MarcaPropietario
  	    try {
	  	enhancedObject.setMarcaPropietario(transferObject.getMarcaPropietario());
	  	} catch (Throwable th ) {}
	  			
		  // Participacion
  	    try {
	  	enhancedObject.setParticipacion(transferObject.getParticipacion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}