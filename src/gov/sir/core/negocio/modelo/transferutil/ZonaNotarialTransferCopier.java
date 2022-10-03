package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ZonaNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ZonaNotarial transferObject = new gov.sir.core.negocio.modelo.ZonaNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	transferObject.setIdDepartamento(enhancedObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // CirculoNotarial
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced circuloNotarialEnh = null;
	  try {
	  	circuloNotarialEnh = enhancedObject.getCirculoNotarial();
	  	} catch (Throwable th) {}
	  	if (circuloNotarialEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CirculoNotarial objTo =  (gov.sir.core.negocio.modelo.CirculoNotarial)cache.get(circuloNotarialEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CirculoNotarial)CirculoNotarialTransferCopier.deepCopy(circuloNotarialEnh, cache);
	  		}
	  		transferObject.setCirculoNotarial(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdMunicipio
  	    try {
	  	transferObject.setIdMunicipio(enhancedObject.getIdMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // Vereda
  	  gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced veredaEnh = null;
	  try {
	  	veredaEnh = enhancedObject.getVereda();
	  	} catch (Throwable th) {}
	  	if (veredaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Vereda objTo =  (gov.sir.core.negocio.modelo.Vereda)cache.get(veredaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Vereda)VeredaTransferCopier.deepCopy(veredaEnh, cache);
	  		}
	  		transferObject.setVereda(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdVereda
  	    try {
	  	transferObject.setIdVereda(enhancedObject.getIdVereda());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoNotarial
  	    try {
	  	transferObject.setIdCirculoNotarial(enhancedObject.getIdCirculoNotarial());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ZonaNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	enhancedObject.setIdDepartamento(transferObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // CirculoNotarial
  	    gov.sir.core.negocio.modelo.CirculoNotarial circuloNotarial = null;
	  	try {
	  	circuloNotarial = (gov.sir.core.negocio.modelo.CirculoNotarial)transferObject.getCirculoNotarial();
	  	} catch (Throwable th ) {}
	  	if (circuloNotarial != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced)cache.get(circuloNotarial);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced)CirculoNotarialTransferCopier.deepCopy(circuloNotarial, cache);
	  		}
	  		enhancedObject.setCirculoNotarial(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdMunicipio
  	    try {
	  	enhancedObject.setIdMunicipio(transferObject.getIdMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // Vereda
  	    gov.sir.core.negocio.modelo.Vereda vereda = null;
	  	try {
	  	vereda = (gov.sir.core.negocio.modelo.Vereda)transferObject.getVereda();
	  	} catch (Throwable th ) {}
	  	if (vereda != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)cache.get(vereda);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)VeredaTransferCopier.deepCopy(vereda, cache);
	  		}
	  		enhancedObject.setVereda(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdVereda
  	    try {
	  	enhancedObject.setIdVereda(transferObject.getIdVereda());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoNotarial
  	    try {
	  	enhancedObject.setIdCirculoNotarial(transferObject.getIdCirculoNotarial());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}