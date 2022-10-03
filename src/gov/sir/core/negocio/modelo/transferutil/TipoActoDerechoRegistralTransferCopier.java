package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoActoDerechoRegistralTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoActoDerechoRegistral transferObject = new gov.sir.core.negocio.modelo.TipoActoDerechoRegistral();
		cache.put(enhancedObject, transferObject );
		
		  // TipoActo
  	  gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced tipoActoEnh = null;
	  try {
	  	tipoActoEnh = enhancedObject.getTipoActo();
	  	} catch (Throwable th) {}
	  	if (tipoActoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoActo objTo =  (gov.sir.core.negocio.modelo.TipoActo)cache.get(tipoActoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoActo)TipoActoTransferCopier.deepCopy(tipoActoEnh, cache);
	  		}
	  		transferObject.setTipoActo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoActo
  	    try {
	  	transferObject.setIdTipoActo(enhancedObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoDerechoReg
  	    try {
	  	transferObject.setIdTipoDerechoReg(enhancedObject.getIdTipoDerechoReg());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDerechoRegistral
  	  gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced tipoDerechoRegistralEnh = null;
	  try {
	  	tipoDerechoRegistralEnh = enhancedObject.getTipoDerechoRegistral();
	  	} catch (Throwable th) {}
	  	if (tipoDerechoRegistralEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoDerechoReg objTo =  (gov.sir.core.negocio.modelo.TipoDerechoReg)cache.get(tipoDerechoRegistralEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoDerechoReg)TipoDerechoRegTransferCopier.deepCopy(tipoDerechoRegistralEnh, cache);
	  		}
	  		transferObject.setTipoDerechoRegistral(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoActoDerechoRegistral transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced();
		cache.put(transferObject, enhancedObject);
		  // TipoActo
  	    gov.sir.core.negocio.modelo.TipoActo tipoActo = null;
	  	try {
	  	tipoActo = (gov.sir.core.negocio.modelo.TipoActo)transferObject.getTipoActo();
	  	} catch (Throwable th ) {}
	  	if (tipoActo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)cache.get(tipoActo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)TipoActoTransferCopier.deepCopy(tipoActo, cache);
	  		}
	  		enhancedObject.setTipoActo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdTipoActo
  	    try {
	  	enhancedObject.setIdTipoActo(transferObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoDerechoReg
  	    try {
	  	enhancedObject.setIdTipoDerechoReg(transferObject.getIdTipoDerechoReg());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDerechoRegistral
  	    gov.sir.core.negocio.modelo.TipoDerechoReg tipoDerechoRegistral = null;
	  	try {
	  	tipoDerechoRegistral = (gov.sir.core.negocio.modelo.TipoDerechoReg)transferObject.getTipoDerechoRegistral();
	  	} catch (Throwable th ) {}
	  	if (tipoDerechoRegistral != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced)cache.get(tipoDerechoRegistral);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced)TipoDerechoRegTransferCopier.deepCopy(tipoDerechoRegistral, cache);
	  		}
	  		enhancedObject.setTipoDerechoRegistral(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}