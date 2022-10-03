package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FirmaRegistradorTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.FirmaRegistrador transferObject = new gov.sir.core.negocio.modelo.FirmaRegistrador();
		cache.put(enhancedObject, transferObject );
		
		  // IdFirmaRegistrador
  	    try {
	  	transferObject.setIdFirmaRegistrador(enhancedObject.getIdFirmaRegistrador());
	  	} catch (Throwable th ) {} 
		
		
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
	  			
		  // Activo
  	    try {
	  	transferObject.setActivo(enhancedObject.getActivo());
	  	} catch (Throwable th ) {}
	  			
		  // IdArchivo
  	    try {
	  	transferObject.setIdArchivo(enhancedObject.getIdArchivo());
	  	} catch (Throwable th ) {}
	  			
		  // CargoRegistrador
  	    try {
	  	transferObject.setCargoRegistrador(enhancedObject.getCargoRegistrador());
	  	} catch (Throwable th ) {}
	  			
		  // NombreRegistrador
  	    try {
	  	transferObject.setNombreRegistrador(enhancedObject.getNombreRegistrador());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.FirmaRegistrador transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced();
		cache.put(transferObject, enhancedObject);
		
		  // IdFirmaRegistrador
  	    try {
	  	enhancedObject.setIdFirmaRegistrador(transferObject.getIdFirmaRegistrador());
	  	} catch (Throwable th ) {}
		
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
	  			
		  // Activo
  	    try {
	  	enhancedObject.setActivo(transferObject.getActivo());
	  	} catch (Throwable th ) {}
	  			
		  // IdArchivo
  	    try {
	  	enhancedObject.setIdArchivo(transferObject.getIdArchivo());
	  	} catch (Throwable th ) {}
	  			
		  // CargoRegistrador
  	    try {
	  	enhancedObject.setCargoRegistrador(transferObject.getCargoRegistrador());
	  	} catch (Throwable th ) {}
	  			
		  // NombreRegistrador
  	    try {
	  	enhancedObject.setNombreRegistrador(transferObject.getNombreRegistrador());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}