package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebFolioEnglobeTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebFolioEnglobe transferObject = new gov.sir.core.negocio.modelo.WebFolioEnglobe();
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
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
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
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebFolioEnglobe transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced();
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
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
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