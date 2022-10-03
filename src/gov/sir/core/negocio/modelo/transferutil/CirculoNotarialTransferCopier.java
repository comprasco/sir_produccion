package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoNotarial transferObject = new gov.sir.core.negocio.modelo.CirculoNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IncluirEnReparto
    try {
  	transferObject.setIncluirEnReparto(enhancedObject.isIncluirEnReparto());
  } catch (Throwable th ) {}
  			
		  // CirculoRegistral
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloRegistralEnh = null;
	  try {
	  	circuloRegistralEnh = enhancedObject.getCirculoRegistral();
	  	} catch (Throwable th) {}
	  	if (circuloRegistralEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloRegistralEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloRegistralEnh, cache);
	  		}
	  		transferObject.setCirculoRegistral(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ZonasNotariale
    	List zonasNotariale = null;
  	try
  	{
  	zonasNotariale = enhancedObject.getZonasNotariales();
  	} catch (Throwable th) {}
	  	if (zonasNotariale != null)
	  	{
		  	for(Iterator itera = zonasNotariale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced zonasNotarialeEnh = (gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.ZonaNotarial objTo = (gov.sir.core.negocio.modelo.ZonaNotarial)cache.get(zonasNotarialeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.ZonaNotarial)ZonaNotarialTransferCopier.deepCopy(zonasNotarialeEnh, cache);
		  		}
		  		transferObject.addZonasNotariale(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IncluirEnReparto
      try {
  	enhancedObject.setIncluirEnReparto(transferObject.isIncluirEnReparto());
  	} catch (Throwable th ) {}
  			
		  // CirculoRegistral
  	    gov.sir.core.negocio.modelo.Circulo circuloRegistral = null;
	  	try {
	  	circuloRegistral = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculoRegistral();
	  	} catch (Throwable th ) {}
	  	if (circuloRegistral != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circuloRegistral);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circuloRegistral, cache);
	  		}
	  		enhancedObject.setCirculoRegistral(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ZonasNotariale
    	List zonasNotariale = null;
  	try
  	{
  	zonasNotariale = transferObject.getZonasNotariales();
  	} catch (Throwable th) { }
	  	if (zonasNotariale != null)
	  	{
		  	for(Iterator itera = zonasNotariale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.ZonaNotarial zonasNotarialeto = (gov.sir.core.negocio.modelo.ZonaNotarial)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced)cache.get(zonasNotarialeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced)ZonaNotarialTransferCopier.deepCopy(zonasNotarialeto, cache);
		  		}
		  		enhancedObject.addZonasNotariale(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}