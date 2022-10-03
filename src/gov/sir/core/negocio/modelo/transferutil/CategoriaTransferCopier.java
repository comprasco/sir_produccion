package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CategoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Categoria transferObject = new gov.sir.core.negocio.modelo.Categoria();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCategoria
  	    try {
	  	transferObject.setIdCategoria(enhancedObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadMax
  	    try {
	  	transferObject.setUnidadMax(enhancedObject.getUnidadMax());
	  	} catch (Throwable th ) {}
	  			
		  // ValorMax
  	    try {
	  	transferObject.setValorMax(enhancedObject.getValorMax());
	  	} catch (Throwable th ) {}
	  			
		  // OficinasOrigen
    	List oficinasOrigen = null;
  	try
  	{
  	oficinasOrigen = enhancedObject.getOficinasOrigens();
  	} catch (Throwable th) {}
	  	if (oficinasOrigen != null)
	  	{
		  	for(Iterator itera = oficinasOrigen.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced oficinasOrigenEnh = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.OficinaCategoria objTo = (gov.sir.core.negocio.modelo.OficinaCategoria)cache.get(oficinasOrigenEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.OficinaCategoria)OficinaCategoriaTransferCopier.deepCopy(oficinasOrigenEnh, cache);
		  		}
		  		transferObject.addOficinasOrigen(objTo);
		  		}
		  				  	}
		}
				
		  // ValorMin
  	    try {
	  	transferObject.setValorMin(enhancedObject.getValorMin());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadMin
  	    try {
	  	transferObject.setUnidadMin(enhancedObject.getUnidadMin());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Categoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCategoria
  	    try {
	  	enhancedObject.setIdCategoria(transferObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadMax
  	    try {
	  	enhancedObject.setUnidadMax(transferObject.getUnidadMax());
	  	} catch (Throwable th ) {}
	  			
		  // ValorMax
  	    try {
	  	enhancedObject.setValorMax(transferObject.getValorMax());
	  	} catch (Throwable th ) {}
	  			
		  // OficinasOrigen
    	List oficinasOrigen = null;
  	try
  	{
  	oficinasOrigen = transferObject.getOficinasOrigens();
  	} catch (Throwable th) { }
	  	if (oficinasOrigen != null)
	  	{
		  	for(Iterator itera = oficinasOrigen.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.OficinaCategoria oficinasOrigento = (gov.sir.core.negocio.modelo.OficinaCategoria)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)cache.get(oficinasOrigento);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)OficinaCategoriaTransferCopier.deepCopy(oficinasOrigento, cache);
		  		}
		  		enhancedObject.addOficinasOrigen(objEn);
		  				  		}
		  				  	}
		}
				
		  // ValorMin
  	    try {
	  	enhancedObject.setValorMin(transferObject.getValorMin());
	  	} catch (Throwable th ) {}
	  			
		  // UnidadMin
  	    try {
	  	enhancedObject.setUnidadMin(transferObject.getUnidadMin());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}