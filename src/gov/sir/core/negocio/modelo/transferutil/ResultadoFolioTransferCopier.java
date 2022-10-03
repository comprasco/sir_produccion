package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ResultadoFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ResultadoFolio transferObject = new gov.sir.core.negocio.modelo.ResultadoFolio();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioEnh = null;
	  try {
	  	folioEnh = enhancedObject.getFolio();
	  	} catch (Throwable th) {}
	  	if (folioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioEnh, cache);
	  		}
	  		transferObject.setFolio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdBusqueda
  	    try {
	  	transferObject.setIdBusqueda(enhancedObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // MayorExtension
    try {
  	transferObject.setMayorExtension(enhancedObject.isMayorExtension());
  } catch (Throwable th ) {}
  			
		  // Busqueda
  	  gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced busquedaEnh = null;
	  try {
	  	busquedaEnh = enhancedObject.getBusqueda();
	  	} catch (Throwable th) {}
	  	if (busquedaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Busqueda objTo =  (gov.sir.core.negocio.modelo.Busqueda)cache.get(busquedaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Busqueda)BusquedaTransferCopier.deepCopy(busquedaEnh, cache);
	  		}
	  		transferObject.setBusqueda(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ResultadosAnotacion
    	List resultadosAnotacion = null;
  	try
  	{
  	resultadosAnotacion = enhancedObject.getResultadosAnotacions();
  	} catch (Throwable th) {}
	  	if (resultadosAnotacion != null)
	  	{
		  	for(Iterator itera = resultadosAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced resultadosAnotacionEnh = (gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.ResultadoAnotacion objTo = (gov.sir.core.negocio.modelo.ResultadoAnotacion)cache.get(resultadosAnotacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.ResultadoAnotacion)ResultadoAnotacionTransferCopier.deepCopy(resultadosAnotacionEnh, cache);
		  		}
		  		transferObject.addResultadosAnotacion(objTo);
		  		}
		  				  	}
		}
				
		  // LastDireccion
  	    try {
	  	transferObject.setLastDireccion(enhancedObject.getLastDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // EstadoFolio
  	    try {
	  	transferObject.setEstadoFolio(enhancedObject.getEstadoFolio());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ResultadoFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    gov.sir.core.negocio.modelo.Folio folio = null;
	  	try {
	  	folio = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolio();
	  	} catch (Throwable th ) {}
	  	if (folio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folio, cache);
	  		}
	  		enhancedObject.setFolio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdBusqueda
  	    try {
	  	enhancedObject.setIdBusqueda(transferObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // MayorExtension
      try {
  	enhancedObject.setMayorExtension(transferObject.isMayorExtension());
  	} catch (Throwable th ) {}
  			
		  // Busqueda
  	    gov.sir.core.negocio.modelo.Busqueda busqueda = null;
	  	try {
	  	busqueda = (gov.sir.core.negocio.modelo.Busqueda)transferObject.getBusqueda();
	  	} catch (Throwable th ) {}
	  	if (busqueda != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced)cache.get(busqueda);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced)BusquedaTransferCopier.deepCopy(busqueda, cache);
	  		}
	  		enhancedObject.setBusqueda(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ResultadosAnotacion
    	List resultadosAnotacion = null;
  	try
  	{
  	resultadosAnotacion = transferObject.getResultadosAnotacions();
  	} catch (Throwable th) { }
	  	if (resultadosAnotacion != null)
	  	{
		  	for(Iterator itera = resultadosAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.ResultadoAnotacion resultadosAnotacionto = (gov.sir.core.negocio.modelo.ResultadoAnotacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced)cache.get(resultadosAnotacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoAnotacionEnhanced)ResultadoAnotacionTransferCopier.deepCopy(resultadosAnotacionto, cache);
		  		}
		  		enhancedObject.addResultadosAnotacion(objEn);
		  				  		}
		  				  	}
		}
				
		  // LastDireccion
  	    try {
	  	enhancedObject.setLastDireccion(transferObject.getLastDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // EstadoFolio
  	    try {
	  	enhancedObject.setEstadoFolio(transferObject.getEstadoFolio());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}