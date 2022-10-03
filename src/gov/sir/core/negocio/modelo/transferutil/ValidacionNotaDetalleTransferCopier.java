package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ValidacionNotaDetalleTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ValidacionNotaDetalle transferObject = new gov.sir.core.negocio.modelo.ValidacionNotaDetalle();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoNota
  	    try {
	  	transferObject.setIdTipoNota(enhancedObject.getIdTipoNota());
	  	} catch (Throwable th ) {}
	  			
		  // IdRespuesta
  	    try {
	  	transferObject.setIdRespuesta(enhancedObject.getIdRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // TipoNota
  	  gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced tipoNotaEnh = null;
	  try {
	  	tipoNotaEnh = enhancedObject.getTipoNota();
	  	} catch (Throwable th) {}
	  	if (tipoNotaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoNota objTo =  (gov.sir.core.negocio.modelo.TipoNota)cache.get(tipoNotaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoNota)TipoNotaTransferCopier.deepCopy(tipoNotaEnh, cache);
	  		}
	  		transferObject.setTipoNota(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ValidacionNotaInf
  	  gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced validacionNotaInfEnh = null;
	  try {
	  	validacionNotaInfEnh = enhancedObject.getValidacionNotaInf();
	  	} catch (Throwable th) {}
	  	if (validacionNotaInfEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.ValidacionNota objTo =  (gov.sir.core.negocio.modelo.ValidacionNota)cache.get(validacionNotaInfEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.ValidacionNota)ValidacionNotaTransferCopier.deepCopy(validacionNotaInfEnh, cache);
	  		}
	  		transferObject.setValidacionNotaInf(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ValidacionNotaDetalle transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoNota
  	    try {
	  	enhancedObject.setIdTipoNota(transferObject.getIdTipoNota());
	  	} catch (Throwable th ) {}
	  			
		  // IdRespuesta
  	    try {
	  	enhancedObject.setIdRespuesta(transferObject.getIdRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // TipoNota
  	    gov.sir.core.negocio.modelo.TipoNota tipoNota = null;
	  	try {
	  	tipoNota = (gov.sir.core.negocio.modelo.TipoNota)transferObject.getTipoNota();
	  	} catch (Throwable th ) {}
	  	if (tipoNota != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced)cache.get(tipoNota);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced)TipoNotaTransferCopier.deepCopy(tipoNota, cache);
	  		}
	  		enhancedObject.setTipoNota(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ValidacionNotaInf
  	    gov.sir.core.negocio.modelo.ValidacionNota validacionNotaInf = null;
	  	try {
	  	validacionNotaInf = (gov.sir.core.negocio.modelo.ValidacionNota)transferObject.getValidacionNotaInf();
	  	} catch (Throwable th ) {}
	  	if (validacionNotaInf != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced)cache.get(validacionNotaInf);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced)ValidacionNotaTransferCopier.deepCopy(validacionNotaInf, cache);
	  		}
	  		enhancedObject.setValidacionNotaInf(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}