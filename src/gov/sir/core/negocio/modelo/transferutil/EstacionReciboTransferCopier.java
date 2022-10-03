package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EstacionReciboTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.EstacionRecibo transferObject = new gov.sir.core.negocio.modelo.EstacionRecibo();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdEstacion
  	    try {
	  	transferObject.setIdEstacion(enhancedObject.getIdEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroFinal
  	    try {
	  	transferObject.setNumeroFinal(enhancedObject.getNumeroFinal());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroInicial
  	    try {
	  	transferObject.setNumeroInicial(enhancedObject.getNumeroInicial());
	  	} catch (Throwable th ) {}
	  			
		  // UltimoNumero
  	    try {
	  	transferObject.setUltimoNumero(enhancedObject.getUltimoNumero());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroProceso
  	    try {
	  	transferObject.setNumeroProceso(enhancedObject.getNumeroProceso());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.EstacionRecibo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdEstacion
  	    try {
	  	enhancedObject.setIdEstacion(transferObject.getIdEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroFinal
  	    try {
	  	enhancedObject.setNumeroFinal(transferObject.getNumeroFinal());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroInicial
  	    try {
	  	enhancedObject.setNumeroInicial(transferObject.getNumeroInicial());
	  	} catch (Throwable th ) {}
	  			
		  // UltimoNumero
  	    try {
	  	enhancedObject.setUltimoNumero(transferObject.getUltimoNumero());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroProceso
  	    try {
	  	enhancedObject.setNumeroProceso(transferObject.getNumeroProceso());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}