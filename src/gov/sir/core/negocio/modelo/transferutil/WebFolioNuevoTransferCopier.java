package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebFolioNuevoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebFolioNuevo transferObject = new gov.sir.core.negocio.modelo.WebFolioNuevo();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Direccione
    	List direccione = null;
  	try
  	{
  	direccione = enhancedObject.getDirecciones();
  	} catch (Throwable th) {}
	  	if (direccione != null)
	  	{
		  	for(Iterator itera = direccione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced direccioneEnh = (gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.WebDireccion objTo = (gov.sir.core.negocio.modelo.WebDireccion)cache.get(direccioneEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.WebDireccion)WebDireccionTransferCopier.deepCopy(direccioneEnh, cache);
		  		}
		  		transferObject.addDireccione(objTo);
		  		}
		  				  	}
		}
				
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
	  			
		  // Englobe
  	  gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced englobeEnh = null;
	  try {
	  	englobeEnh = enhancedObject.getEnglobe();
	  	} catch (Throwable th) {}
	  	if (englobeEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebEnglobe objTo =  (gov.sir.core.negocio.modelo.WebEnglobe)cache.get(englobeEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebEnglobe)WebEnglobeTransferCopier.deepCopy(englobeEnh, cache);
	  		}
	  		transferObject.setEnglobe(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebFolioNuevo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Direccione
    	List direccione = null;
  	try
  	{
  	direccione = transferObject.getDirecciones();
  	} catch (Throwable th) { }
	  	if (direccione != null)
	  	{
		  	for(Iterator itera = direccione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.WebDireccion direccioneto = (gov.sir.core.negocio.modelo.WebDireccion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced)cache.get(direccioneto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced)WebDireccionTransferCopier.deepCopy(direccioneto, cache);
		  		}
		  		enhancedObject.addDireccione(objEn);
		  				  		}
		  				  	}
		}
				
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
	  			
		  // Englobe
  	    gov.sir.core.negocio.modelo.WebEnglobe englobe = null;
	  	try {
	  	englobe = (gov.sir.core.negocio.modelo.WebEnglobe)transferObject.getEnglobe();
	  	} catch (Throwable th ) {}
	  	if (englobe != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)cache.get(englobe);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)WebEnglobeTransferCopier.deepCopy(englobe, cache);
	  		}
	  		enhancedObject.setEnglobe(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}