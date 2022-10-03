package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TarifaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Tarifa transferObject = new gov.sir.core.negocio.modelo.Tarifa();
		cache.put(enhancedObject, transferObject );
		
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FechaInicial
  		try {
		if (enhancedObject.getFechaInicial() != null)
		{
	  	 transferObject.setFechaInicial(new Date(enhancedObject.getFechaInicial().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // TipoTarifa
  	  gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced tipoTarifaEnh = null;
	  try {
	  	tipoTarifaEnh = enhancedObject.getTipoTarifa();
	  	} catch (Throwable th) {}
	  	if (tipoTarifaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoTarifa objTo =  (gov.sir.core.negocio.modelo.TipoTarifa)cache.get(tipoTarifaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoTarifa)TipoTarifaTransferCopier.deepCopy(tipoTarifaEnh, cache);
	  		}
	  		transferObject.setTipoTarifa(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdCodigo
  	    try {
	  	transferObject.setIdCodigo(enhancedObject.getIdCodigo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipo
  	    try {
	  	transferObject.setIdTipo(enhancedObject.getIdTipo());
	  	} catch (Throwable th ) {}
	  			
		  // IdVersion
  	    try {
	  	transferObject.setIdVersion(enhancedObject.getIdVersion());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Tarifa transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FechaInicial
  	    try {
		if (transferObject.getFechaInicial() != null)
		{
		  	enhancedObject.setFechaInicial(new Date(transferObject.getFechaInicial().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // TipoTarifa
  	    gov.sir.core.negocio.modelo.TipoTarifa tipoTarifa = null;
	  	try {
	  	tipoTarifa = (gov.sir.core.negocio.modelo.TipoTarifa)transferObject.getTipoTarifa();
	  	} catch (Throwable th ) {}
	  	if (tipoTarifa != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced)cache.get(tipoTarifa);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced)TipoTarifaTransferCopier.deepCopy(tipoTarifa, cache);
	  		}
	  		enhancedObject.setTipoTarifa(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdCodigo
  	    try {
	  	enhancedObject.setIdCodigo(transferObject.getIdCodigo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipo
  	    try {
	  	enhancedObject.setIdTipo(transferObject.getIdTipo());
	  	} catch (Throwable th ) {}
	  			
		  // IdVersion
  	    try {
	  	enhancedObject.setIdVersion(transferObject.getIdVersion());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}