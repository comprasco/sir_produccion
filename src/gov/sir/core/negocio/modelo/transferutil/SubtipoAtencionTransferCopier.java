package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SubtipoAtencionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SubtipoAtencion transferObject = new gov.sir.core.negocio.modelo.SubtipoAtencion();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Orden
  	    try {
	  	transferObject.setOrden(enhancedObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	transferObject.setIdSubtipoAtencion(enhancedObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
		  // MinAnotacione
  	    try {
	  	transferObject.setMinAnotacione(enhancedObject.getMinAnotacione());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadesTrabajo
  	    try {
	  	transferObject.setUnidadesTrabajo(enhancedObject.getUnidadesTrabajo());
	  	} catch (Throwable th ) {}
	  			
		  // TiposActo
    	List tiposActo = null;
  	try
  	{
  	tiposActo = enhancedObject.getTiposActos();
  	} catch (Throwable th) {}
	  	if (tiposActo != null)
	  	{
		  	for(Iterator itera = tiposActo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced tiposActoEnh = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo objTo = (gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo)cache.get(tiposActoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo)SubtipoAtencionTipoActoTransferCopier.deepCopy(tiposActoEnh, cache);
		  		}
		  		transferObject.addTiposActo(objTo);
		  		}
		  				  	}
		}
				
		  // SubtipoSolicitude
    	List subtipoSolicitude = null;
  	try
  	{
  	subtipoSolicitude = enhancedObject.getSubtipoSolicitudes();
  	} catch (Throwable th) {}
	  	if (subtipoSolicitude != null)
	  	{
		  	for(Iterator itera = subtipoSolicitude.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced subtipoSolicitudeEnh = (gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SubAtencionSubSolicitud objTo = (gov.sir.core.negocio.modelo.SubAtencionSubSolicitud)cache.get(subtipoSolicitudeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SubAtencionSubSolicitud)SubAtencionSubSolicitudTransferCopier.deepCopy(subtipoSolicitudeEnh, cache);
		  		}
		  		transferObject.addSubtipoSolicitude(objTo);
		  		}
		  				  	}
		}
				
		  // MinAnotaciones
  	    try {
	  	transferObject.setMinAnotaciones(enhancedObject.getMinAnotaciones());
	  	} catch (Throwable th ) {}
	  			
		  // MinFolios
  	    try {
	  	transferObject.setMinFolios(enhancedObject.getMinFolios());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SubtipoAtencion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Orden
  	    try {
	  	enhancedObject.setOrden(transferObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	enhancedObject.setIdSubtipoAtencion(transferObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
		  // MinAnotacione
  	    try {
	  	enhancedObject.setMinAnotacione(transferObject.getMinAnotacione());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadesTrabajo
  	    try {
	  	enhancedObject.setUnidadesTrabajo(transferObject.getUnidadesTrabajo());
	  	} catch (Throwable th ) {}
	  			
		  // TiposActo
    	List tiposActo = null;
  	try
  	{
  	tiposActo = transferObject.getTiposActos();
  	} catch (Throwable th) { }
	  	if (tiposActo != null)
	  	{
		  	for(Iterator itera = tiposActo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo tiposActoto = (gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced)cache.get(tiposActoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced)SubtipoAtencionTipoActoTransferCopier.deepCopy(tiposActoto, cache);
		  		}
		  		enhancedObject.addTiposActo(objEn);
		  				  		}
		  				  	}
		}
				
		  // SubtipoSolicitude
    	List subtipoSolicitude = null;
  	try
  	{
  	subtipoSolicitude = transferObject.getSubtipoSolicitudes();
  	} catch (Throwable th) { }
	  	if (subtipoSolicitude != null)
	  	{
		  	for(Iterator itera = subtipoSolicitude.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SubAtencionSubSolicitud subtipoSolicitudeto = (gov.sir.core.negocio.modelo.SubAtencionSubSolicitud)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced)cache.get(subtipoSolicitudeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced)SubAtencionSubSolicitudTransferCopier.deepCopy(subtipoSolicitudeto, cache);
		  		}
		  		enhancedObject.addSubtipoSolicitude(objEn);
		  				  		}
		  				  	}
		}
				
		  // MinAnotaciones
  	    try {
	  	enhancedObject.setMinAnotaciones(transferObject.getMinAnotaciones());
	  	} catch (Throwable th ) {}
	  			
		  // MinFolios
  	    try {
	  	enhancedObject.setMinFolios(transferObject.getMinFolios());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}