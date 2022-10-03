package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebAnotacion transferObject = new gov.sir.core.negocio.modelo.WebAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
            
            // Modalidad
  	    try {
	  	transferObject.setModalidad(enhancedObject.getModalidad());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	  gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced documentoEnh = null;
	  try {
	  	documentoEnh = enhancedObject.getDocumento();
	  	} catch (Throwable th) {}
	  	if (documentoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebDocumento objTo =  (gov.sir.core.negocio.modelo.WebDocumento)cache.get(documentoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebDocumento)WebDocumentoTransferCopier.deepCopy(documentoEnh, cache);
	  		}
	  		transferObject.setDocumento(objTo);
	  		}
	  			  		
	  	}
	  			
		  // FechaRadicacion
  		try {
		if (enhancedObject.getFechaRadicacion() != null)
		{
	  	 transferObject.setFechaRadicacion(new Date(enhancedObject.getFechaRadicacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // CopiaComentario
  	    try {
	  	transferObject.setCopiaComentario(enhancedObject.getCopiaComentario());
	  	} catch (Throwable th ) {}
	  			
		  // IdDocumento
  	    try {
	  	transferObject.setIdDocumento(enhancedObject.getIdDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdNaturalezaJuridica
  	    try {
	  	transferObject.setIdNaturalezaJuridica(enhancedObject.getIdNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRadicacion
  	    try {
	  	transferObject.setNumeroRadicacion(enhancedObject.getNumeroRadicacion());
	  	} catch (Throwable th ) {}
	  	
	  	// IdAnotacionDefinitiva
  	    try {
	  	transferObject.setIdAnotacionDefinitiva(enhancedObject.getIdAnotacionDefinitiva());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
       /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
            //version
             try {
	  	transferObject.setVersion(enhancedObject.getVersion());
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
	  			
		  // Ciudadano
    	List ciudadano = null;
  	try
  	{
  	ciudadano = enhancedObject.getCiudadanos();
  	} catch (Throwable th) {}
	  	if (ciudadano != null)
	  	{
		  	for(Iterator itera = ciudadano.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced ciudadanoEnh = (gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.WebCiudadano objTo = (gov.sir.core.negocio.modelo.WebCiudadano)cache.get(ciudadanoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.WebCiudadano)WebCiudadanoTransferCopier.deepCopy(ciudadanoEnh, cache);
		  		}
		  		transferObject.addCiudadano(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
            
            // Modalidad
  	    try {
	  	enhancedObject.setModalidad(transferObject.getModalidad());
	  	} catch (Throwable th ) {}
            
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	    gov.sir.core.negocio.modelo.WebDocumento documento = null;
	  	try {
	  	documento = (gov.sir.core.negocio.modelo.WebDocumento)transferObject.getDocumento();
	  	} catch (Throwable th ) {}
	  	if (documento != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced)cache.get(documento);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced)WebDocumentoTransferCopier.deepCopy(documento, cache);
	  		}
	  		enhancedObject.setDocumento(objEn);
	  		}
	  			  		
	  	}
	  			
		  // FechaRadicacion
  	    try {
		if (transferObject.getFechaRadicacion() != null)
		{
		  	enhancedObject.setFechaRadicacion(new Date(transferObject.getFechaRadicacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // CopiaComentario
  	    try {
	  	enhancedObject.setCopiaComentario(transferObject.getCopiaComentario());
	  	} catch (Throwable th ) {}
	  			
		  // IdDocumento
  	    try {
	  	enhancedObject.setIdDocumento(transferObject.getIdDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdNaturalezaJuridica
  	    try {
	  	enhancedObject.setIdNaturalezaJuridica(transferObject.getIdNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRadicacion
  	    try {
	  	enhancedObject.setNumeroRadicacion(transferObject.getNumeroRadicacion());
	  	} catch (Throwable th ) {}
	  	
	  	// IdAnotacionDefinitiva
  	    try {
	  	enhancedObject.setIdAnotacionDefinitiva(transferObject.getIdAnotacionDefinitiva());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
            // version
  	    try {
	  	enhancedObject.setVersion(transferObject.getVersion());
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
	  			
		  // Ciudadano
    	List ciudadano = null;
  	try
  	{
  	ciudadano = transferObject.getCiudadanos();
  	} catch (Throwable th) { }
	  	if (ciudadano != null)
	  	{
		  	for(Iterator itera = ciudadano.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.WebCiudadano ciudadanoto = (gov.sir.core.negocio.modelo.WebCiudadano)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced)cache.get(ciudadanoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced)WebCiudadanoTransferCopier.deepCopy(ciudadanoto, cache);
		  		}
		  		enhancedObject.addCiudadano(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}
