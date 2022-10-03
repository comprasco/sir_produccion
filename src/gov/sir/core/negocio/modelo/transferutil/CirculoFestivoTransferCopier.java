package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoFestivoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoFestivo transferObject = new gov.sir.core.negocio.modelo.CirculoFestivo();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloEnh = null;
	  try {
	  	circuloEnh = enhancedObject.getCirculo();
	  	} catch (Throwable th) {}
	  	if (circuloEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloEnh, cache);
	  		}
	  		transferObject.setCirculo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFestivo
  		try {
		if (enhancedObject.getFechaFestivo() != null)
		{
	  	 transferObject.setFechaFestivo(new Date(enhancedObject.getFechaFestivo().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoFestivo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Circulo
  	    gov.sir.core.negocio.modelo.Circulo circulo = null;
	  	try {
	  	circulo = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculo();
	  	} catch (Throwable th ) {}
	  	if (circulo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circulo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circulo, cache);
	  		}
	  		enhancedObject.setCirculo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFestivo
  	    try {
		if (transferObject.getFechaFestivo() != null)
		{
		  	enhancedObject.setFechaFestivo(new Date(transferObject.getFechaFestivo().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}