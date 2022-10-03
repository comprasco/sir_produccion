package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebFolioHeredadoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebFolioHeredado transferObject = new gov.sir.core.negocio.modelo.WebFolioHeredado();
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
	  			
		  // CopiaComentario
    try {
  	transferObject.setCopiaComentario(enhancedObject.isCopiaComentario());
  } catch (Throwable th ) {}
  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // WebSegEng
  	  gov.sir.core.negocio.modelo.jdogenie.WebSegEngEnhanced webSegEngEnh = null;
	  try {
	  	webSegEngEnh = enhancedObject.getWebSegEng();
	  	} catch (Throwable th) {}
	  	if (webSegEngEnh != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (webSegEngEnh instanceof gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.WebSegEng objTo = (gov.sir.core.negocio.modelo.WebSegEng)cache.get(webSegEngEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebSegEng)WebEnglobeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)webSegEngEnh, cache);
	  		}
	  		transferObject.setWebSegEng(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (webSegEngEnh instanceof gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.WebSegEng objTo = (gov.sir.core.negocio.modelo.WebSegEng)cache.get(webSegEngEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebSegEng)WebSegregacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)webSegEngEnh, cache);
	  		}
	  		transferObject.setWebSegEng(objTo);
	  		assigned = true;
	  		}
	  			  			  		
	  	}
	  			
		  // AnotacionesHeredada
    	List anotacionesHeredada = null;
  	try
  	{
  	anotacionesHeredada = enhancedObject.getAnotacionesHeredadas();
  	} catch (Throwable th) {}
	  	if (anotacionesHeredada != null)
	  	{
		  	for(Iterator itera = anotacionesHeredada.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced anotacionesHeredadaEnh = (gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.WebAnotaHereda objTo = (gov.sir.core.negocio.modelo.WebAnotaHereda)cache.get(anotacionesHeredadaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.WebAnotaHereda)WebAnotaHeredaTransferCopier.deepCopy(anotacionesHeredadaEnh, cache);
		  		}
		  		transferObject.addAnotacionesHeredada(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebFolioHeredado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced();
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
	  			
		  // CopiaComentario
      try {
  	enhancedObject.setCopiaComentario(transferObject.isCopiaComentario());
  	} catch (Throwable th ) {}
  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // WebSegEng
  	    gov.sir.core.negocio.modelo.WebSegEng webSegEng = null;
	  	try {
	  	webSegEng = (gov.sir.core.negocio.modelo.WebSegEng)transferObject.getWebSegEng();
	  	} catch (Throwable th ) {}
	  	if (webSegEng != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (webSegEng instanceof gov.sir.core.negocio.modelo.WebEnglobe)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)cache.get(webSegEng);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced)WebEnglobeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.WebEnglobe)webSegEng, cache);
	  		}
	  		enhancedObject.setWebSegEng(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (webSegEng instanceof gov.sir.core.negocio.modelo.WebSegregacion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)cache.get(webSegEng);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced)WebSegregacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.WebSegregacion)webSegEng, cache);
	  		}
	  		enhancedObject.setWebSegEng(objEn);
	  		assigned = true;
	  		}
	  			  			  		
	  	}
	  			
		  // AnotacionesHeredada
    	List anotacionesHeredada = null;
  	try
  	{
  	anotacionesHeredada = transferObject.getAnotacionesHeredadas();
  	} catch (Throwable th) { }
	  	if (anotacionesHeredada != null)
	  	{
		  	for(Iterator itera = anotacionesHeredada.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.WebAnotaHereda anotacionesHeredadato = (gov.sir.core.negocio.modelo.WebAnotaHereda)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced)cache.get(anotacionesHeredadato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced)WebAnotaHeredaTransferCopier.deepCopy(anotacionesHeredadato, cache);
		  		}
		  		enhancedObject.addAnotacionesHeredada(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}