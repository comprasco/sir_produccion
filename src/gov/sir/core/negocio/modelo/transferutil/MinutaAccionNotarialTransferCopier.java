package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class MinutaAccionNotarialTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.MinutaAccionNotarial transferObject = new gov.sir.core.negocio.modelo.MinutaAccionNotarial();
		cache.put(enhancedObject, transferObject );
		
		  // IdAccionNotarial
  	    try {
	  	transferObject.setIdAccionNotarial(enhancedObject.getIdAccionNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // IdMinuta
  	    try {
	  	transferObject.setIdMinuta(enhancedObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // Unidades
  	    try {
	  	transferObject.setUnidades(enhancedObject.getUnidades());
	  	} catch (Throwable th ) {}
	  			
		  // AccionNotarial
  	  gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced accionNotarialEnh = null;
	  try {
	  	accionNotarialEnh = enhancedObject.getAccionNotarial();
	  	} catch (Throwable th) {}
	  	if (accionNotarialEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.AccionNotarial objTo =  (gov.sir.core.negocio.modelo.AccionNotarial)cache.get(accionNotarialEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.AccionNotarial)AccionNotarialTransferCopier.deepCopy(accionNotarialEnh, cache);
	  		}
	  		transferObject.setAccionNotarial(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Minuta
  	  gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced minutaEnh = null;
	  try {
	  	minutaEnh = enhancedObject.getMinuta();
	  	} catch (Throwable th) {}
	  	if (minutaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Minuta objTo =  (gov.sir.core.negocio.modelo.Minuta)cache.get(minutaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Minuta)MinutaTransferCopier.deepCopy(minutaEnh, cache);
	  		}
	  		transferObject.setMinuta(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ConCuantia
  	    try {
	  	transferObject.setConCuantia(enhancedObject.getConCuantia());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.MinutaAccionNotarial transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdAccionNotarial
  	    try {
	  	enhancedObject.setIdAccionNotarial(transferObject.getIdAccionNotarial());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // IdMinuta
  	    try {
	  	enhancedObject.setIdMinuta(transferObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // Unidades
  	    try {
	  	enhancedObject.setUnidades(transferObject.getUnidades());
	  	} catch (Throwable th ) {}
	  			
		  // AccionNotarial
  	    gov.sir.core.negocio.modelo.AccionNotarial accionNotarial = null;
	  	try {
	  	accionNotarial = (gov.sir.core.negocio.modelo.AccionNotarial)transferObject.getAccionNotarial();
	  	} catch (Throwable th ) {}
	  	if (accionNotarial != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced)cache.get(accionNotarial);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced)AccionNotarialTransferCopier.deepCopy(accionNotarial, cache);
	  		}
	  		enhancedObject.setAccionNotarial(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Minuta
  	    gov.sir.core.negocio.modelo.Minuta minuta = null;
	  	try {
	  	minuta = (gov.sir.core.negocio.modelo.Minuta)transferObject.getMinuta();
	  	} catch (Throwable th ) {}
	  	if (minuta != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)cache.get(minuta);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced)MinutaTransferCopier.deepCopy(minuta, cache);
	  		}
	  		enhancedObject.setMinuta(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ConCuantia
  	    try {
	  	enhancedObject.setConCuantia(transferObject.getConCuantia());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}