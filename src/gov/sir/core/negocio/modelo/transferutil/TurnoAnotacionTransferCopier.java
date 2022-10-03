package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoAnotacion transferObject = new gov.sir.core.negocio.modelo.TurnoAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced anotacionEnh = null;
	  try {
	  	anotacionEnh = enhancedObject.getAnotacion();
	  	} catch (Throwable th) {}
	  	if (anotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(anotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(anotacionEnh, cache);
	  		}
	  		transferObject.setAnotacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoEnh = null;
	  try {
	  	turnoEnh = enhancedObject.getTurno();
	  	} catch (Throwable th) {}
	  	if (turnoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoEnh, cache);
	  		}
	  		transferObject.setTurno(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdTurno
  	    try {
	  	transferObject.setIdTurno(enhancedObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	    gov.sir.core.negocio.modelo.Anotacion anotacion = null;
	  	try {
	  	anotacion = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getAnotacion();
	  	} catch (Throwable th ) {}
	  	if (anotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(anotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(anotacion, cache);
	  		}
	  		enhancedObject.setAnotacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	    gov.sir.core.negocio.modelo.Turno turno = null;
	  	try {
	  	turno = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurno();
	  	} catch (Throwable th ) {}
	  	if (turno != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turno);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turno, cache);
	  		}
	  		enhancedObject.setTurno(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdTurno
  	    try {
	  	enhancedObject.setIdTurno(transferObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}