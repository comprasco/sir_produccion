package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebFolioDerivadoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebFolioDerivado transferObject = new gov.sir.core.negocio.modelo.WebFolioDerivado();
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
	  			
		  // Orden
  	    try {
	  	transferObject.setOrden(enhancedObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	transferObject.setPorcentaje(enhancedObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Area
  	    try {
	  	transferObject.setArea(enhancedObject.getArea());
	  	} catch (Throwable th ) {}
            
            // Hectareas
  	    try {
	  	transferObject.setHectareas(enhancedObject.getHectareas());
	  	} catch (Throwable th ) {}
            
            // Metros
  	    try {
	  	transferObject.setMetros(enhancedObject.getMetros());
	  	} catch (Throwable th ) {}
            
            // Centimetros
  	    try {
	  	transferObject.setCentimetros(enhancedObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
            // PrivMetros
            try {
	  	transferObject.setPrivMetros(enhancedObject.getPrivMetros());
	  	} catch (Throwable th ) {}
            
                // PrivCentimetros
            try {
	  	transferObject.setPrivCentimetros(enhancedObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
                // ConsMetros
            try {
	  	transferObject.setConsMetros(enhancedObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
                // ConsCentimetros
            try {
	  	transferObject.setConsCentimetros(enhancedObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
                // DeterminaInm
            try {
	  	transferObject.setDeterminaInm(enhancedObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebFolioDerivado
  	    try {
	  	transferObject.setIdWebFolioDerivado(enhancedObject.getIdWebFolioDerivado());
	  	} catch (Throwable th ) {}
	  			
		  // Inmueble
  	    try {
	  	transferObject.setInmueble(enhancedObject.getInmueble());
	  	} catch (Throwable th ) {}
	  			
		  // Segregacion
  	  gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced segregacionEnh = null;
	  try {
	  	segregacionEnh = enhancedObject.getSegregacion();
	  	} catch (Throwable th) {}
	  	if (segregacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebSegregacion objTo =  (gov.sir.core.negocio.modelo.WebSegregacion)cache.get(segregacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebSegregacion)WebSegregacionTransferCopier.deepCopy(segregacionEnh, cache);
	  		}
	  		transferObject.setSegregacion(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebFolioDerivado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced();
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
	  			
		  // Orden
  	    try {
	  	enhancedObject.setOrden(transferObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	enhancedObject.setPorcentaje(transferObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Area
  	    try {
	  	enhancedObject.setArea(transferObject.getArea());
	  	} catch (Throwable th ) {}
            
             // Hectareas
  	    try {
	  	enhancedObject.setHectareas(transferObject.getHectareas());
	  	} catch (Throwable th ) {}
            
             // Metros
  	    try {
	  	enhancedObject.setMetros(transferObject.getMetros());
	  	} catch (Throwable th ) {}
            
             // Centimetros
  	    try {
	  	enhancedObject.setCentimetros(transferObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
            // PrivMetros
            try {
	  	enhancedObject.setPrivMetros(transferObject.getPrivMetros());
	  	} catch (Throwable th ) {}
                
                // PrivCentimetros
            try {
	  	enhancedObject.setPrivCentimetros(transferObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
                // ConsMetros
            try {
	  	enhancedObject.setConsMetros(transferObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
                // ConsCentimetros
            try {
	  	enhancedObject.setConsCentimetros(transferObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
                // DeterminaInm
            try {
	  	enhancedObject.setDeterminaInm(transferObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebFolioDerivado
  	    try {
	  	enhancedObject.setIdWebFolioDerivado(transferObject.getIdWebFolioDerivado());
	  	} catch (Throwable th ) {}
	  			
		  // Inmueble
  	    try {
	  	enhancedObject.setInmueble(transferObject.getInmueble());
	  	} catch (Throwable th ) {}
	  			
		  // Segregacion
  	    gov.sir.core.negocio.modelo.WebSegregacion segregacion = null;
	  	try {
	  	segregacion = (gov.sir.core.negocio.modelo.WebSegregacion)transferObject.getSegregacion();
	  	} catch (Throwable th ) {}
	  	if (segregacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)cache.get(segregacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)WebSegregacionTransferCopier.deepCopy(segregacion, cache);
	  		}
	  		enhancedObject.setSegregacion(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}