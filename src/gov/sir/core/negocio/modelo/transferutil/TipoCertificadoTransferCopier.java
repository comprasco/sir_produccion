package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoCertificadoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoCertificado transferObject = new gov.sir.core.negocio.modelo.TipoCertificado();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoCertificado
  	    try {
	  	transferObject.setIdTipoCertificado(enhancedObject.getIdTipoCertificado());
	  	} catch (Throwable th ) {}
	  			
		  // FlagIndividual
    try {
  	transferObject.setFlagIndividual(enhancedObject.getFlagIndividual());
  } catch (Throwable th ) {}
  			
		  // Validacion
    	List validacion = null;
  	try
  	{
  	validacion = enhancedObject.getValidaciones();
  	} catch (Throwable th) {}
	  	if (validacion != null)
	  	{
		  	for(Iterator itera = validacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced validacionEnh = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TipoCertificadoValidacion objTo = (gov.sir.core.negocio.modelo.TipoCertificadoValidacion)cache.get(validacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TipoCertificadoValidacion)TipoCertificadoValidacionTransferCopier.deepCopy(validacionEnh, cache);
		  		}
		  		transferObject.addValidacion(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoCertificado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoCertificado
  	    try {
	  	enhancedObject.setIdTipoCertificado(transferObject.getIdTipoCertificado());
	  	} catch (Throwable th ) {}
	  			
		  // FlagIndividual
      try {
  	enhancedObject.setFlagIndividual(transferObject.getFlagIndividual());
  	} catch (Throwable th ) {}
  			
		  // Validacion
    	List validacion = null;
  	try
  	{
  	validacion = transferObject.getValidaciones();
  	} catch (Throwable th) { }
	  	if (validacion != null)
	  	{
		  	for(Iterator itera = validacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TipoCertificadoValidacion validacionto = (gov.sir.core.negocio.modelo.TipoCertificadoValidacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced)cache.get(validacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced)TipoCertificadoValidacionTransferCopier.deepCopy(validacionto, cache);
		  		}
		  		enhancedObject.addValidacion(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}