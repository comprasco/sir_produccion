package gov.sir.core.negocio.modelo.transferutil;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CancelacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Cancelacion transferObject = new gov.sir.core.negocio.modelo.Cancelacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // ToDelete
    try {
  	transferObject.setToDelete(enhancedObject.isToDelete());
  } catch (Throwable th ) {}
  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion1
  	    try {
	  	transferObject.setIdAnotacion1(enhancedObject.getIdAnotacion1());
	  	} catch (Throwable th ) {}
	  			
		  // Cancelada
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced canceladaEnh = null;
	  try {
	  	canceladaEnh = enhancedObject.getCancelada();
	  	} catch (Throwable th) {}
	  	if (canceladaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(canceladaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(canceladaEnh, cache);
	  		}
	  		transferObject.setCancelada(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Canceladora
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced canceladoraEnh = null;
	  try {
	  	canceladoraEnh = enhancedObject.getCanceladora();
	  	} catch (Throwable th) {}
	  	if (canceladoraEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(canceladoraEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(canceladoraEnh, cache);
	  		}
	  		transferObject.setCanceladora(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Cancelacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // ToDelete
      try {
  	enhancedObject.setToDelete(transferObject.isToDelete());
  	} catch (Throwable th ) {}
  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion1
  	    try {
	  	enhancedObject.setIdAnotacion1(transferObject.getIdAnotacion1());
	  	} catch (Throwable th ) {}
	  			
		  // Cancelada
  	    gov.sir.core.negocio.modelo.Anotacion cancelada = null;
	  	try {
	  	cancelada = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getCancelada();
	  	} catch (Throwable th ) {}
	  	if (cancelada != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(cancelada);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(cancelada, cache);
	  		}
	  		enhancedObject.setCancelada(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Canceladora
  	    gov.sir.core.negocio.modelo.Anotacion canceladora = null;
	  	try {
	  	canceladora = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getCanceladora();
	  	} catch (Throwable th ) {}
	  	if (canceladora != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(canceladora);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(canceladora, cache);
	  		}
	  		enhancedObject.setCanceladora(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}