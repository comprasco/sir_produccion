package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoImpresoraTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoImpresora transferObject = new gov.sir.core.negocio.modelo.CirculoImpresora();
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
	  			
		  // Predeterminada
    try {
  	transferObject.setPredeterminada(enhancedObject.isPredeterminada());
  } catch (Throwable th ) {}
  			
		  // IdImpresora
  	    try {
	  	transferObject.setIdImpresora(enhancedObject.getIdImpresora());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImprimible
  	    try {
	  	transferObject.setIdTipoImprimible(enhancedObject.getIdTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoImpresora transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced();
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
	  			
		  // Predeterminada
      try {
  	enhancedObject.setPredeterminada(transferObject.isPredeterminada());
  	} catch (Throwable th ) {}
  			
		  // IdImpresora
  	    try {
	  	enhancedObject.setIdImpresora(transferObject.getIdImpresora());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImprimible
  	    try {
	  	enhancedObject.setIdTipoImprimible(transferObject.getIdTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}