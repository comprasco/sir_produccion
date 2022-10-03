package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocumentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Documento transferObject = new gov.sir.core.negocio.modelo.Documento();
		cache.put(enhancedObject, transferObject );
		
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
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
	  			
		  // OficinaOrigen
  	  gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced oficinaOrigenEnh = null;
	  try {
	  	oficinaOrigenEnh = enhancedObject.getOficinaOrigen();
	  	} catch (Throwable th) {}
	  	if (oficinaOrigenEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.OficinaOrigen objTo =  (gov.sir.core.negocio.modelo.OficinaOrigen)cache.get(oficinaOrigenEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.OficinaOrigen)OficinaOrigenTransferCopier.deepCopy(oficinaOrigenEnh, cache);
	  		}
	  		transferObject.setOficinaOrigen(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdDocumento
  	    try {
	  	transferObject.setIdDocumento(enhancedObject.getIdDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	transferObject.setNumero(enhancedObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaInternacional
  	    try {
	  	transferObject.setOficinaInternacional(enhancedObject.getOficinaInternacional());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocumento
  	  gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced tipoDocumentoEnh = null;
	  try {
	  	tipoDocumentoEnh = enhancedObject.getTipoDocumento();
	  	} catch (Throwable th) {}
	  	if (tipoDocumentoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoDocumento objTo =  (gov.sir.core.negocio.modelo.TipoDocumento)cache.get(tipoDocumentoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoDocumento)TipoDocumentoTransferCopier.deepCopy(tipoDocumentoEnh, cache);
	  		}
	  		transferObject.setTipoDocumento(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Documento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
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
	  			
		  // OficinaOrigen
  	    gov.sir.core.negocio.modelo.OficinaOrigen oficinaOrigen = null;
	  	try {
	  	oficinaOrigen = (gov.sir.core.negocio.modelo.OficinaOrigen)transferObject.getOficinaOrigen();
	  	} catch (Throwable th ) {}
	  	if (oficinaOrigen != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)cache.get(oficinaOrigen);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)OficinaOrigenTransferCopier.deepCopy(oficinaOrigen, cache);
	  		}
	  		enhancedObject.setOficinaOrigen(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdDocumento
  	    try {
	  	enhancedObject.setIdDocumento(transferObject.getIdDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	enhancedObject.setNumero(transferObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaInternacional
  	    try {
	  	enhancedObject.setOficinaInternacional(transferObject.getOficinaInternacional());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocumento
  	    gov.sir.core.negocio.modelo.TipoDocumento tipoDocumento = null;
	  	try {
	  	tipoDocumento = (gov.sir.core.negocio.modelo.TipoDocumento)transferObject.getTipoDocumento();
	  	} catch (Throwable th ) {}
	  	if (tipoDocumento != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced)cache.get(tipoDocumento);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced)TipoDocumentoTransferCopier.deepCopy(tipoDocumento, cache);
	  		}
	  		enhancedObject.setTipoDocumento(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}