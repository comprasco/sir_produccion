package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebDocumentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebDocumento transferObject = new gov.sir.core.negocio.modelo.WebDocumento();
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
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  		try {
		if (enhancedObject.getFecha() != null)
		{
	  	 transferObject.setFecha(new Date(enhancedObject.getFecha().getTime()));
	  	}
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
	  			
		  // Numero
  	    try {
	  	transferObject.setNumero(enhancedObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaInternacional
  	    try {
	  	transferObject.setOficinaInternacional(enhancedObject.getOficinaInternacional());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficinaOrigen
  	    try {
	  	transferObject.setIdOficinaOrigen(enhancedObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
	  /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
           //version
          try {
	  	transferObject.setVersion(enhancedObject.getVersion());
	  	} catch (Throwable th ) {}
		  // IdTipoDocumento
  	    try {
	  	transferObject.setIdTipoDocumento(enhancedObject.getIdTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebDocumento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced();
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
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  	    try {
		if (transferObject.getFecha() != null)
		{
		  	enhancedObject.setFecha(new Date(transferObject.getFecha().getTime()));
		}
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
	  			
		  // Numero
  	    try {
	  	enhancedObject.setNumero(transferObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaInternacional
  	    try {
	  	enhancedObject.setOficinaInternacional(transferObject.getOficinaInternacional());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficinaOrigen
  	    try {
	  	enhancedObject.setIdOficinaOrigen(transferObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
             /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            try {
	  	enhancedObject.setVersion(transferObject.getVersion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoDocumento
  	    try {
	  	enhancedObject.setIdTipoDocumento(transferObject.getIdTipoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
