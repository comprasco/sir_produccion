package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ProcesoRepartoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ProcesoReparto transferObject = new gov.sir.core.negocio.modelo.ProcesoReparto();
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
	  			
		  // IdProcesoReparto
  	    try {
	  	transferObject.setIdProcesoReparto(enhancedObject.getIdProcesoReparto());
	  	} catch (Throwable th ) {}
	  			
		  // FechaReparto
  		try {
		if (enhancedObject.getFechaReparto() != null)
		{
	  	 transferObject.setFechaReparto(new Date(enhancedObject.getFechaReparto().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Reparto
    	List reparto = null;
  	try
  	{
  	reparto = enhancedObject.getRepartos();
  	} catch (Throwable th) {}
	  	if (reparto != null)
	  	{
		  	for(Iterator itera = reparto.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced repartoEnh = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Reparto objTo = (gov.sir.core.negocio.modelo.Reparto)cache.get(repartoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Reparto)RepartoTransferCopier.deepCopy(repartoEnh, cache);
		  		}
		  		transferObject.addReparto(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ProcesoReparto transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced();
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
	  			
		  // IdProcesoReparto
  	    try {
	  	enhancedObject.setIdProcesoReparto(transferObject.getIdProcesoReparto());
	  	} catch (Throwable th ) {}
	  			
		  // FechaReparto
  	    try {
		if (transferObject.getFechaReparto() != null)
		{
		  	enhancedObject.setFechaReparto(new Date(transferObject.getFechaReparto().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Reparto
    	List reparto = null;
  	try
  	{
  	reparto = transferObject.getRepartos();
  	} catch (Throwable th) { }
	  	if (reparto != null)
	  	{
		  	for(Iterator itera = reparto.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Reparto repartoto = (gov.sir.core.negocio.modelo.Reparto)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced)cache.get(repartoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced)RepartoTransferCopier.deepCopy(repartoto, cache);
		  		}
		  		enhancedObject.addReparto(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}