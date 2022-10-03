package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ImprimibleTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Imprimible transferObject = new gov.sir.core.negocio.modelo.Imprimible();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    try {
	  	transferObject.setFolio(enhancedObject.getFolio());
	  	} catch (Throwable th ) {}
	  			
		  // UID
  	    try {
	  	transferObject.setUID(enhancedObject.getUID());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroImpresiones
  	    try {
	  	transferObject.setNumeroImpresiones(enhancedObject.getNumeroImpresiones());
	  	} catch (Throwable th ) {}
	  			
		  // IP
  	    try {
	  	transferObject.setIP(enhancedObject.getIP());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroBytes
  	    try {
	  	transferObject.setNumeroBytes(enhancedObject.getNumeroBytes());
	  	} catch (Throwable th ) {}
	  			
		  // TipoImprimible
  	    try {
	  	transferObject.setTipoImprimible(enhancedObject.getTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	    try {
	  	transferObject.setTurno(enhancedObject.getTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdImprimible
  	    try {
	  	transferObject.setIdImprimible(enhancedObject.getIdImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // DatosImprimible
  	    try {
	  	transferObject.setDatosImprimible(enhancedObject.getDatosImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // ImprimibleExtenso
    try {
  	transferObject.setImprimibleExtenso(enhancedObject.isImprimibleExtenso());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Imprimible transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    try {
	  	enhancedObject.setFolio(transferObject.getFolio());
	  	} catch (Throwable th ) {}
	  			
		  // UID
  	    try {
	  	enhancedObject.setUID(transferObject.getUID());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroImpresiones
  	    try {
	  	enhancedObject.setNumeroImpresiones(transferObject.getNumeroImpresiones());
	  	} catch (Throwable th ) {}
	  			
		  // IP
  	    try {
	  	enhancedObject.setIP(transferObject.getIP());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroBytes
  	    try {
	  	enhancedObject.setNumeroBytes(transferObject.getNumeroBytes());
	  	} catch (Throwable th ) {}
	  			
		  // TipoImprimible
  	    try {
	  	enhancedObject.setTipoImprimible(transferObject.getTipoImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	    try {
	  	enhancedObject.setTurno(transferObject.getTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdImprimible
  	    try {
	  	enhancedObject.setIdImprimible(transferObject.getIdImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // DatosImprimible
  	    try {
	  	enhancedObject.setDatosImprimible(transferObject.getDatosImprimible());
	  	} catch (Throwable th ) {}
	  			
		  // ImprimibleExtenso
      try {
  	enhancedObject.setImprimibleExtenso(transferObject.isImprimibleExtenso());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}