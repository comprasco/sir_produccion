package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoSalvedadFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoSalvedadFolio transferObject = new gov.sir.core.negocio.modelo.TurnoSalvedadFolio();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
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
	  			
		  // Salvedad
  	  gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced salvedadEnh = null;
	  try {
	  	salvedadEnh = enhancedObject.getSalvedad();
	  	} catch (Throwable th) {}
	  	if (salvedadEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SalvedadFolio objTo =  (gov.sir.core.negocio.modelo.SalvedadFolio)cache.get(salvedadEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SalvedadFolio)SalvedadFolioTransferCopier.deepCopy(salvedadEnh, cache);
	  		}
	  		transferObject.setSalvedad(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdSalvedadFo
  	    try {
	  	transferObject.setIdSalvedadFo(enhancedObject.getIdSalvedadFo());
	  	} catch (Throwable th ) {}
	  			
		  // RrsfFechaCreacion
  		try {
		if (enhancedObject.getRrsfFechaCreacion() != null)
		{
	  	 transferObject.setRrsfFechaCreacion(new Date(enhancedObject.getRrsfFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoSalvedadFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
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
	  			
		  // Salvedad
  	    gov.sir.core.negocio.modelo.SalvedadFolio salvedad = null;
	  	try {
	  	salvedad = (gov.sir.core.negocio.modelo.SalvedadFolio)transferObject.getSalvedad();
	  	} catch (Throwable th ) {}
	  	if (salvedad != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced)cache.get(salvedad);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced)SalvedadFolioTransferCopier.deepCopy(salvedad, cache);
	  		}
	  		enhancedObject.setSalvedad(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdSalvedadFo
  	    try {
	  	enhancedObject.setIdSalvedadFo(transferObject.getIdSalvedadFo());
	  	} catch (Throwable th ) {}
	  			
		  // RrsfFechaCreacion
  	    try {
		if (transferObject.getRrsfFechaCreacion() != null)
		{
		  	enhancedObject.setRrsfFechaCreacion(new Date(transferObject.getRrsfFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}