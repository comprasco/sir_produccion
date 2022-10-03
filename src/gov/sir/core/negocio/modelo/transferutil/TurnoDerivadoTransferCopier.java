package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoDerivadoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoDerivado transferObject = new gov.sir.core.negocio.modelo.TurnoDerivado();
		cache.put(enhancedObject, transferObject );
		
		  // AnioHijo
  	    try {
	  	transferObject.setAnioHijo(enhancedObject.getAnioHijo());
	  	} catch (Throwable th ) {}
	  			
		  // AnioPadre
  	    try {
	  	transferObject.setAnioPadre(enhancedObject.getAnioPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoHijo
  	    try {
	  	transferObject.setIdCirculoHijo(enhancedObject.getIdCirculoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoPadre
  	    try {
	  	transferObject.setIdCirculoPadre(enhancedObject.getIdCirculoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdProcesoHijo
  	    try {
	  	transferObject.setIdProcesoHijo(enhancedObject.getIdProcesoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProcesoPadre
  	    try {
	  	transferObject.setIdProcesoPadre(enhancedObject.getIdProcesoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHijo
  	    try {
	  	transferObject.setIdTurnoHijo(enhancedObject.getIdTurnoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoPadre
  	    try {
	  	transferObject.setIdTurnoPadre(enhancedObject.getIdTurnoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHijo
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoHijoEnh = null;
	  try {
	  	turnoHijoEnh = enhancedObject.getTurnoHijo();
	  	} catch (Throwable th) {}
	  	if (turnoHijoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoHijoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoHijoEnh, cache);
	  		}
	  		transferObject.setTurnoHijo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // TurnoPadre
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoPadreEnh = null;
	  try {
	  	turnoPadreEnh = enhancedObject.getTurnoPadre();
	  	} catch (Throwable th) {}
	  	if (turnoPadreEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoPadreEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoPadreEnh, cache);
	  		}
	  		transferObject.setTurnoPadre(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoDerivado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // AnioHijo
  	    try {
	  	enhancedObject.setAnioHijo(transferObject.getAnioHijo());
	  	} catch (Throwable th ) {}
	  			
		  // AnioPadre
  	    try {
	  	enhancedObject.setAnioPadre(transferObject.getAnioPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoHijo
  	    try {
	  	enhancedObject.setIdCirculoHijo(transferObject.getIdCirculoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoPadre
  	    try {
	  	enhancedObject.setIdCirculoPadre(transferObject.getIdCirculoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdProcesoHijo
  	    try {
	  	enhancedObject.setIdProcesoHijo(transferObject.getIdProcesoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProcesoPadre
  	    try {
	  	enhancedObject.setIdProcesoPadre(transferObject.getIdProcesoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHijo
  	    try {
	  	enhancedObject.setIdTurnoHijo(transferObject.getIdTurnoHijo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoPadre
  	    try {
	  	enhancedObject.setIdTurnoPadre(transferObject.getIdTurnoPadre());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHijo
  	    gov.sir.core.negocio.modelo.Turno turnoHijo = null;
	  	try {
	  	turnoHijo = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurnoHijo();
	  	} catch (Throwable th ) {}
	  	if (turnoHijo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turnoHijo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turnoHijo, cache);
	  		}
	  		enhancedObject.setTurnoHijo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // TurnoPadre
  	    gov.sir.core.negocio.modelo.Turno turnoPadre = null;
	  	try {
	  	turnoPadre = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurnoPadre();
	  	} catch (Throwable th ) {}
	  	if (turnoPadre != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turnoPadre);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turnoPadre, cache);
	  		}
	  		enhancedObject.setTurnoPadre(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}