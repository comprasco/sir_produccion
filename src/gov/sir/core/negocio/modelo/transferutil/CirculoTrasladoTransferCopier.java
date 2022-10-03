package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoTrasladoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoTraslado transferObject = new gov.sir.core.negocio.modelo.CirculoTraslado();
		cache.put(enhancedObject, transferObject );
		
		  // IdTraslado
  	    try {
	  	transferObject.setIdTraslado(enhancedObject.getIdTraslado());
	  	} catch (Throwable th ) {}
	  			
		  // CirculoDestino
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloDestinoEnh = null;
	  try {
	  	circuloDestinoEnh = enhancedObject.getCirculoDestino();
	  	} catch (Throwable th) {}
	  	if (circuloDestinoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloDestinoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloDestinoEnh, cache);
	  		}
	  		transferObject.setCirculoDestino(objTo);
	  		}
	  			  		
	  	}
	  			
		  // CirculoOrigen
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloOrigenEnh = null;
	  try {
	  	circuloOrigenEnh = enhancedObject.getCirculoOrigen();
	  	} catch (Throwable th) {}
	  	if (circuloOrigenEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloOrigenEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloOrigenEnh, cache);
	  		}
	  		transferObject.setCirculoOrigen(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoTraslado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdTraslado
  	    try {
	  	enhancedObject.setIdTraslado(transferObject.getIdTraslado());
	  	} catch (Throwable th ) {}
	  			
		  // CirculoDestino
  	    gov.sir.core.negocio.modelo.Circulo circuloDestino = null;
	  	try {
	  	circuloDestino = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculoDestino();
	  	} catch (Throwable th ) {}
	  	if (circuloDestino != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circuloDestino);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circuloDestino, cache);
	  		}
	  		enhancedObject.setCirculoDestino(objEn);
	  		}
	  			  		
	  	}
	  			
		  // CirculoOrigen
  	    gov.sir.core.negocio.modelo.Circulo circuloOrigen = null;
	  	try {
	  	circuloOrigen = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculoOrigen();
	  	} catch (Throwable th ) {}
	  	if (circuloOrigen != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circuloOrigen);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circuloOrigen, cache);
	  		}
	  		enhancedObject.setCirculoOrigen(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}