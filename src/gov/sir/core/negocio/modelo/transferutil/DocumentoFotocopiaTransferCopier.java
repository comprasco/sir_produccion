package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocumentoFotocopiaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocumentoFotocopia transferObject = new gov.sir.core.negocio.modelo.DocumentoFotocopia();
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
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SolicitudFotocopia objTo =  (gov.sir.core.negocio.modelo.SolicitudFotocopia)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SolicitudFotocopia)SolicitudFotocopiaTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdDocFotocopia
  	    try {
	  	transferObject.setIdDocFotocopia(enhancedObject.getIdDocFotocopia());
	  	} catch (Throwable th ) {}
	  			
		  // NumCopias
  	    try {
	  	transferObject.setNumCopias(enhancedObject.getNumCopias());
	  	} catch (Throwable th ) {}
	  			
		  // NumHojas
  	    try {
	  	transferObject.setNumHojas(enhancedObject.getNumHojas());
	  	} catch (Throwable th ) {}
	  			
		  // TipoFotocopia
  	  gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced tipoFotocopiaEnh = null;
	  try {
	  	tipoFotocopiaEnh = enhancedObject.getTipoFotocopia();
	  	} catch (Throwable th) {}
	  	if (tipoFotocopiaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoFotocopia objTo =  (gov.sir.core.negocio.modelo.TipoFotocopia)cache.get(tipoFotocopiaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoFotocopia)TipoFotocopiaTransferCopier.deepCopy(tipoFotocopiaEnh, cache);
	  		}
	  		transferObject.setTipoFotocopia(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocumentoFotocopia transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced();
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
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.SolicitudFotocopia solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.SolicitudFotocopia)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)SolicitudFotocopiaTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdDocFotocopia
  	    try {
	  	enhancedObject.setIdDocFotocopia(transferObject.getIdDocFotocopia());
	  	} catch (Throwable th ) {}
	  			
		  // NumCopias
  	    try {
	  	enhancedObject.setNumCopias(transferObject.getNumCopias());
	  	} catch (Throwable th ) {}
	  			
		  // NumHojas
  	    try {
	  	enhancedObject.setNumHojas(transferObject.getNumHojas());
	  	} catch (Throwable th ) {}
	  			
		  // TipoFotocopia
  	    gov.sir.core.negocio.modelo.TipoFotocopia tipoFotocopia = null;
	  	try {
	  	tipoFotocopia = (gov.sir.core.negocio.modelo.TipoFotocopia)transferObject.getTipoFotocopia();
	  	} catch (Throwable th ) {}
	  	if (tipoFotocopia != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced)cache.get(tipoFotocopia);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced)TipoFotocopiaTransferCopier.deepCopy(tipoFotocopia, cache);
	  		}
	  		enhancedObject.setTipoFotocopia(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}