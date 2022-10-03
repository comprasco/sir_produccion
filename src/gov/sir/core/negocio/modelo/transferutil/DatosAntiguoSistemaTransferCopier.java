package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DatosAntiguoSistemaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DatosAntiguoSistema transferObject = new gov.sir.core.negocio.modelo.DatosAntiguoSistema();
		cache.put(enhancedObject, transferObject );
		
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
	  			
		  // Documento
  	  gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced documentoEnh = null;
	  try {
	  	documentoEnh = enhancedObject.getDocumento();
	  	} catch (Throwable th) {}
	  	if (documentoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Documento objTo =  (gov.sir.core.negocio.modelo.Documento)cache.get(documentoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Documento)DocumentoTransferCopier.deepCopy(documentoEnh, cache);
	  		}
	  		transferObject.setDocumento(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdDatosAntiguoSistema
  	    try {
	  	transferObject.setIdDatosAntiguoSistema(enhancedObject.getIdDatosAntiguoSistema());
	  	} catch (Throwable th ) {}
	  			
		  // LibroAnio
  	    try {
	  	transferObject.setLibroAnio(enhancedObject.getLibroAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LibroNumero
  	    try {
	  	transferObject.setLibroNumero(enhancedObject.getLibroNumero());
	  	} catch (Throwable th ) {}
	  			
		  // LibroPagina
  	    try {
	  	transferObject.setLibroPagina(enhancedObject.getLibroPagina());
	  	} catch (Throwable th ) {}
	  			
		  // LibroTipo
  	    try {
	  	transferObject.setLibroTipo(enhancedObject.getLibroTipo());
	  	} catch (Throwable th ) {}
	  			
		  // TomoAnio
  	    try {
	  	transferObject.setTomoAnio(enhancedObject.getTomoAnio());
	  	} catch (Throwable th ) {}
	  			
		  // TomoMunicipio
  	    try {
	  	transferObject.setTomoMunicipio(enhancedObject.getTomoMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // TomoNumero
  	    try {
	  	transferObject.setTomoNumero(enhancedObject.getTomoNumero());
	  	} catch (Throwable th ) {}
	  			
		  // TomoPagina
  	    try {
	  	transferObject.setTomoPagina(enhancedObject.getTomoPagina());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DatosAntiguoSistema transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced();
		cache.put(transferObject, enhancedObject);
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
	  			
		  // Documento
  	    gov.sir.core.negocio.modelo.Documento documento = null;
	  	try {
	  	documento = (gov.sir.core.negocio.modelo.Documento)transferObject.getDocumento();
	  	} catch (Throwable th ) {}
	  	if (documento != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced)cache.get(documento);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced)DocumentoTransferCopier.deepCopy(documento, cache);
	  		}
	  		enhancedObject.setDocumento(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdDatosAntiguoSistema
  	    try {
	  	enhancedObject.setIdDatosAntiguoSistema(transferObject.getIdDatosAntiguoSistema());
	  	} catch (Throwable th ) {}
	  			
		  // LibroAnio
  	    try {
	  	enhancedObject.setLibroAnio(transferObject.getLibroAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LibroNumero
  	    try {
	  	enhancedObject.setLibroNumero(transferObject.getLibroNumero());
	  	} catch (Throwable th ) {}
	  			
		  // LibroPagina
  	    try {
	  	enhancedObject.setLibroPagina(transferObject.getLibroPagina());
	  	} catch (Throwable th ) {}
	  			
		  // LibroTipo
  	    try {
	  	enhancedObject.setLibroTipo(transferObject.getLibroTipo());
	  	} catch (Throwable th ) {}
	  			
		  // TomoAnio
  	    try {
	  	enhancedObject.setTomoAnio(transferObject.getTomoAnio());
	  	} catch (Throwable th ) {}
	  			
		  // TomoMunicipio
  	    try {
	  	enhancedObject.setTomoMunicipio(transferObject.getTomoMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // TomoNumero
  	    try {
	  	enhancedObject.setTomoNumero(transferObject.getTomoNumero());
	  	} catch (Throwable th ) {}
	  			
		  // TomoPagina
  	    try {
	  	enhancedObject.setTomoPagina(transferObject.getTomoPagina());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}