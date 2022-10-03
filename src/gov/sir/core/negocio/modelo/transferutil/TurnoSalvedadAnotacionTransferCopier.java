package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoSalvedadAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion transferObject = new gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion();
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
	  			
		  // IdSalvedadAn
  	    try {
	  	transferObject.setIdSalvedadAn(enhancedObject.getIdSalvedadAn());
	  	} catch (Throwable th ) {}
	  			
		  // Salvedad
  	  gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced salvedadEnh = null;
	  try {
	  	salvedadEnh = enhancedObject.getSalvedad();
	  	} catch (Throwable th) {}
	  	if (salvedadEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SalvedadAnotacion objTo =  (gov.sir.core.negocio.modelo.SalvedadAnotacion)cache.get(salvedadEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SalvedadAnotacion)SalvedadAnotacionTransferCopier.deepCopy(salvedadEnh, cache);
	  		}
	  		transferObject.setSalvedad(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced();
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
	  			
		  // IdSalvedadAn
  	    try {
	  	enhancedObject.setIdSalvedadAn(transferObject.getIdSalvedadAn());
	  	} catch (Throwable th ) {}
	  			
		  // Salvedad
  	    gov.sir.core.negocio.modelo.SalvedadAnotacion salvedad = null;
	  	try {
	  	salvedad = (gov.sir.core.negocio.modelo.SalvedadAnotacion)transferObject.getSalvedad();
	  	} catch (Throwable th ) {}
	  	if (salvedad != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced)cache.get(salvedad);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced)SalvedadAnotacionTransferCopier.deepCopy(salvedad, cache);
	  		}
	  		enhancedObject.setSalvedad(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}