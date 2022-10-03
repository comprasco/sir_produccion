package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class HorarioNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.HorarioNotarial transferObject = new gov.sir.core.negocio.modelo.HorarioNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdDia
  	    try {
	  	transferObject.setIdDia(enhancedObject.getIdDia());
	  	} catch (Throwable th ) {}
	  			
		  // HoraFin
  	    try {
	  	transferObject.setHoraFin(enhancedObject.getHoraFin());
	  	} catch (Throwable th ) {}
	  			
		  // HoraInicio
  	    try {
	  	transferObject.setHoraInicio(enhancedObject.getHoraInicio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoHorarioNotarial
  	    try {
	  	transferObject.setIdTipoHorarioNotarial(enhancedObject.getIdTipoHorarioNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // MinFin
  	    try {
	  	transferObject.setMinFin(enhancedObject.getMinFin());
	  	} catch (Throwable th ) {}
	  			
		  // MinInicio
  	    try {
	  	transferObject.setMinInicio(enhancedObject.getMinInicio());
	  	} catch (Throwable th ) {}
	  			
		  // IdConsecutivo
  	    try {
	  	transferObject.setIdConsecutivo(enhancedObject.getIdConsecutivo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.HorarioNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.HorarioNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdDia
  	    try {
	  	enhancedObject.setIdDia(transferObject.getIdDia());
	  	} catch (Throwable th ) {}
	  			
		  // HoraFin
  	    try {
	  	enhancedObject.setHoraFin(transferObject.getHoraFin());
	  	} catch (Throwable th ) {}
	  			
		  // HoraInicio
  	    try {
	  	enhancedObject.setHoraInicio(transferObject.getHoraInicio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoHorarioNotarial
  	    try {
	  	enhancedObject.setIdTipoHorarioNotarial(transferObject.getIdTipoHorarioNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // MinFin
  	    try {
	  	enhancedObject.setMinFin(transferObject.getMinFin());
	  	} catch (Throwable th ) {}
	  			
		  // MinInicio
  	    try {
	  	enhancedObject.setMinInicio(transferObject.getMinInicio());
	  	} catch (Throwable th ) {}
	  			
		  // IdConsecutivo
  	    try {
	  	enhancedObject.setIdConsecutivo(transferObject.getIdConsecutivo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}