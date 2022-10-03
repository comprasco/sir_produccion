package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Circulo transferObject = new gov.sir.core.negocio.modelo.Circulo();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
		  // ZonaRegistral
    	List zonaRegistral = null;
  	try
  	{
  	zonaRegistral = enhancedObject.getZonaRegistrales();
  	} catch (Throwable th) {}
	  	if (zonaRegistral != null)
	  	{
		  	for(Iterator itera = zonaRegistral.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced zonaRegistralEnh = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.ZonaRegistral objTo = (gov.sir.core.negocio.modelo.ZonaRegistral)cache.get(zonaRegistralEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.ZonaRegistral)ZonaRegistralTransferCopier.deepCopy(zonaRegistralEnh, cache);
		  		}
		  		transferObject.addZonaRegistral(objTo);
		  		}
		  				  	}
		}
				
		  // CirculoProceso
    	List circuloProceso = null;
  	try
  	{
  	circuloProceso = enhancedObject.getCirculoProcesos();
  	} catch (Throwable th) {}
	  	if (circuloProceso != null)
	  	{
		  	for(Iterator itera = circuloProceso.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced circuloProcesoEnh = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.CirculoProceso objTo = (gov.sir.core.negocio.modelo.CirculoProceso)cache.get(circuloProcesoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.CirculoProceso)CirculoProcesoTransferCopier.deepCopy(circuloProcesoEnh, cache);
		  		}
		  		transferObject.addCirculoProceso(objTo);
		  		}
		  				  	}
		}
				
		  // CirculoFestivo
    	List circuloFestivo = null;
  	try
  	{
  	circuloFestivo = enhancedObject.getCirculoFestivos();
  	} catch (Throwable th) {}
	  	if (circuloFestivo != null)
	  	{
		  	for(Iterator itera = circuloFestivo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced circuloFestivoEnh = (gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.CirculoFestivo objTo = (gov.sir.core.negocio.modelo.CirculoFestivo)cache.get(circuloFestivoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.CirculoFestivo)CirculoFestivoTransferCopier.deepCopy(circuloFestivoEnh, cache);
		  		}
		  		transferObject.addCirculoFestivo(objTo);
		  		}
		  				  	}
		}
				
		  // TiposPago
    	List tiposPago = null;
  	try
  	{
  	tiposPago = enhancedObject.getTiposPagos();
  	} catch (Throwable th) {}
	  	if (tiposPago != null)
	  	{
		  	for(Iterator itera = tiposPago.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced tiposPagoEnh = (gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.CirculoTipoPago objTo = (gov.sir.core.negocio.modelo.CirculoTipoPago)cache.get(tiposPagoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.CirculoTipoPago)CirculoTipoPagoTransferCopier.deepCopy(tiposPagoEnh, cache);
		  		}
		  		transferObject.addTiposPago(objTo);
		  		}
		  				  	}
		}
				
		  // Firma
    	List firma = null;
  	try
  	{
  	firma = enhancedObject.getFirmas();
  	} catch (Throwable th) {}
	  	if (firma != null)
	  	{
		  	for(Iterator itera = firma.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced firmaEnh = (gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.FirmaRegistrador objTo = (gov.sir.core.negocio.modelo.FirmaRegistrador)cache.get(firmaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.FirmaRegistrador)FirmaRegistradorTransferCopier.deepCopy(firmaEnh, cache);
		  		}
		  		transferObject.addFirma(objTo);
		  		}
		  				  	}
		}
				
		  // LastNoMatricula
  	    try {
	  	transferObject.setLastNoMatricula(enhancedObject.getLastNoMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // CobroImpuesto
    try {
  	transferObject.setCobroImpuesto(enhancedObject.isCobroImpuesto());
  } catch (Throwable th ) {}
  			
		  // Nit
  	    try {
	  	transferObject.setNit(enhancedObject.getNit());
	  	} catch (Throwable th ) {}
	  			
		  // PrintHost
  	    try {
	  	transferObject.setPrintHost(enhancedObject.getPrintHost());
	  	} catch (Throwable th ) {}
	  			
		  // LastNoMatriculaTMP
  	    try {
	  	transferObject.setLastNoMatriculaTMP(enhancedObject.getLastNoMatriculaTMP());
	  	} catch (Throwable th ) {}
            
                  // copiaImp
  	    try {
	  	transferObject.setCopiaImp(enhancedObject.getCopiaImp());
	  	} catch (Throwable th ) {}
	  			
		  // MayorExtension
  	    try {
	  	transferObject.setMayorExtension(enhancedObject.getMayorExtension());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaOrigen
  	  gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced oficinaOrigenEnh = null;
	  try {
	  	oficinaOrigenEnh = enhancedObject.getOficinaOrigen();
	  	} catch (Throwable th) {}
	  	if (oficinaOrigenEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.OficinaOrigen objTo =  (gov.sir.core.negocio.modelo.OficinaOrigen)cache.get(oficinaOrigenEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.OficinaOrigen)OficinaOrigenTransferCopier.deepCopy(oficinaOrigenEnh, cache);
	  		}
	  		transferObject.setOficinaOrigen(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ProcesoMigracion
    try {
  	transferObject.setProcesoMigracion(enhancedObject.isProcesoMigracion());
  } catch (Throwable th ) {}
  			
		  // TipoTextoEncabezado
  	    try {
	  	transferObject.setTipoTextoEncabezado(enhancedObject.getTipoTextoEncabezado());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroBytesExtenso
  	    try {
	  	transferObject.setNumeroBytesExtenso(enhancedObject.getNumeroBytesExtenso());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Circulo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		  // ZonaRegistral
    	List zonaRegistral = null;
  	try
  	{
  	zonaRegistral = transferObject.getZonaRegistrales();
  	} catch (Throwable th) { }
	  	if (zonaRegistral != null)
	  	{
		  	for(Iterator itera = zonaRegistral.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.ZonaRegistral zonaRegistralto = (gov.sir.core.negocio.modelo.ZonaRegistral)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced)cache.get(zonaRegistralto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced)ZonaRegistralTransferCopier.deepCopy(zonaRegistralto, cache);
		  		}
		  		enhancedObject.addZonaRegistral(objEn);
		  				  		}
		  				  	}
		}
				
		  // CirculoProceso
    	List circuloProceso = null;
  	try
  	{
  	circuloProceso = transferObject.getCirculoProcesos();
  	} catch (Throwable th) { }
	  	if (circuloProceso != null)
	  	{
		  	for(Iterator itera = circuloProceso.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.CirculoProceso circuloProcesoto = (gov.sir.core.negocio.modelo.CirculoProceso)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced)cache.get(circuloProcesoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced)CirculoProcesoTransferCopier.deepCopy(circuloProcesoto, cache);
		  		}
		  		enhancedObject.addCirculoProceso(objEn);
		  				  		}
		  				  	}
		}
				
		  // CirculoFestivo
    	List circuloFestivo = null;
  	try
  	{
  	circuloFestivo = transferObject.getCirculoFestivos();
  	} catch (Throwable th) { }
	  	if (circuloFestivo != null)
	  	{
		  	for(Iterator itera = circuloFestivo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.CirculoFestivo circuloFestivoto = (gov.sir.core.negocio.modelo.CirculoFestivo)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced)cache.get(circuloFestivoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced)CirculoFestivoTransferCopier.deepCopy(circuloFestivoto, cache);
		  		}
		  		enhancedObject.addCirculoFestivo(objEn);
		  				  		}
		  				  	}
		}
				
		  // TiposPago
    	List tiposPago = null;
  	try
  	{
  	tiposPago = transferObject.getTiposPagos();
  	} catch (Throwable th) { }
	  	if (tiposPago != null)
	  	{
		  	for(Iterator itera = tiposPago.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.CirculoTipoPago tiposPagoto = (gov.sir.core.negocio.modelo.CirculoTipoPago)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced)cache.get(tiposPagoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced)CirculoTipoPagoTransferCopier.deepCopy(tiposPagoto, cache);
		  		}
		  		enhancedObject.addTiposPago(objEn);
		  				  		}
		  				  	}
		}
				
		  // Firma
    	List firma = null;
  	try
  	{
  	firma = transferObject.getFirmas();
  	} catch (Throwable th) { }
	  	if (firma != null)
	  	{
		  	for(Iterator itera = firma.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.FirmaRegistrador firmato = (gov.sir.core.negocio.modelo.FirmaRegistrador)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced)cache.get(firmato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced)FirmaRegistradorTransferCopier.deepCopy(firmato, cache);
		  		}
		  		enhancedObject.addFirma(objEn);
		  				  		}
		  				  	}
		}
				
		  // LastNoMatricula
  	    try {
	  	enhancedObject.setLastNoMatricula(transferObject.getLastNoMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // CobroImpuesto
      try {
  	enhancedObject.setCobroImpuesto(transferObject.isCobroImpuesto());
  	} catch (Throwable th ) {}
  			
		  // Nit
  	    try {
	  	enhancedObject.setNit(transferObject.getNit());
	  	} catch (Throwable th ) {}
	  			
		  // PrintHost
  	    try {
	  	enhancedObject.setPrintHost(transferObject.getPrintHost());
	  	} catch (Throwable th ) {}
	  			
		  // LastNoMatriculaTMP
  	    try {
	  	enhancedObject.setLastNoMatriculaTMP(transferObject.getLastNoMatriculaTMP());
	  	} catch (Throwable th ) {}
            
                  // copiaImp
  	    try {
	  	enhancedObject.setCopiaImp(transferObject.getCopiaImp());
	  	} catch (Throwable th ) {}
	  			
		  // MayorExtension
  	    try {
	  	enhancedObject.setMayorExtension(transferObject.getMayorExtension());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaOrigen
  	    gov.sir.core.negocio.modelo.OficinaOrigen oficinaOrigen = null;
	  	try {
	  	oficinaOrigen = (gov.sir.core.negocio.modelo.OficinaOrigen)transferObject.getOficinaOrigen();
	  	} catch (Throwable th ) {}
	  	if (oficinaOrigen != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)cache.get(oficinaOrigen);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)OficinaOrigenTransferCopier.deepCopy(oficinaOrigen, cache);
	  		}
	  		enhancedObject.setOficinaOrigen(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ProcesoMigracion
      try {
  	enhancedObject.setProcesoMigracion(transferObject.isProcesoMigracion());
  	} catch (Throwable th ) {}
  			
		  // TipoTextoEncabezado
  	    try {
	  	enhancedObject.setTipoTextoEncabezado(transferObject.getTipoTextoEncabezado());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroBytesExtenso
  	    try {
	  	enhancedObject.setNumeroBytesExtenso(transferObject.getNumeroBytesExtenso());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}