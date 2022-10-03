package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoProcesoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CirculoProceso transferObject = new gov.sir.core.negocio.modelo.CirculoProceso();
		cache.put(enhancedObject, transferObject );
		
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
	  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdTurno
  	    try {
	  	transferObject.setLastIdTurno(enhancedObject.getLastIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdSolicitud
  	    try {
	  	transferObject.setLastIdSolicitud(enhancedObject.getLastIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRepartoNotarial
  	    try {
	  	transferObject.setLastIdRepartoNotarial(enhancedObject.getLastIdRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // SecuencialConstDevolucion
  	    try {
	  	transferObject.setSecuencialConstDevolucion(enhancedObject.getSecuencialConstDevolucion());
	  	} catch (Throwable th ) {}
	  			
		  // SecuencialConstMasivos
  	    try {
	  	transferObject.setSecuencialConstMasivos(enhancedObject.getSecuencialConstMasivos());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CirculoProceso transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced();
		cache.put(transferObject, enhancedObject);
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
	  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdTurno
  	    try {
	  	enhancedObject.setLastIdTurno(transferObject.getLastIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdSolicitud
  	    try {
	  	enhancedObject.setLastIdSolicitud(transferObject.getLastIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRepartoNotarial
  	    try {
	  	enhancedObject.setLastIdRepartoNotarial(transferObject.getLastIdRepartoNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // SecuencialConstDevolucion
  	    try {
	  	enhancedObject.setSecuencialConstDevolucion(transferObject.getSecuencialConstDevolucion());
	  	} catch (Throwable th ) {}
	  			
		  // SecuencialConstMasivos
  	    try {
	  	enhancedObject.setSecuencialConstMasivos(transferObject.getSecuencialConstMasivos());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}