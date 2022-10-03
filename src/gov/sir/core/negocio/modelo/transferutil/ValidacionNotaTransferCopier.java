package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

//
/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ValidacionNotaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ValidacionNota transferObject = new gov.sir.core.negocio.modelo.ValidacionNota();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	  gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced procesoEnh = null;
	  try {
	  	procesoEnh = enhancedObject.getProceso();
	  	} catch (Throwable th) {}
	  	if (procesoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Proceso objTo =  (gov.sir.core.negocio.modelo.Proceso)cache.get(procesoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Proceso)ProcesoTransferCopier.deepCopy(procesoEnh, cache);
	  		}
	  		transferObject.setProceso(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdRespuesta
  	    try {
	  	transferObject.setIdRespuesta(enhancedObject.getIdRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // ValidaDetalle
    try {
  	transferObject.setValidaDetalle(enhancedObject.isValidaDetalle());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ValidacionNota transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	    gov.sir.core.negocio.modelo.Proceso proceso = null;
	  	try {
	  	proceso = (gov.sir.core.negocio.modelo.Proceso)transferObject.getProceso();
	  	} catch (Throwable th ) {}
	  	if (proceso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)cache.get(proceso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)ProcesoTransferCopier.deepCopy(proceso, cache);
	  		}
	  		enhancedObject.setProceso(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdRespuesta
  	    try {
	  	enhancedObject.setIdRespuesta(transferObject.getIdRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // ValidaDetalle
      try {
  	enhancedObject.setValidaDetalle(transferObject.isValidaDetalle());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}