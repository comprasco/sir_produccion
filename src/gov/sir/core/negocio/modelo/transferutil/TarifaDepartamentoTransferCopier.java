package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TarifaDepartamentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TarifaDepartamento transferObject = new gov.sir.core.negocio.modelo.TarifaDepartamento();
		cache.put(enhancedObject, transferObject );
		
		  // IdDepartamento
  	    try {
	  	transferObject.setIdDepartamento(enhancedObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // Departamento
  	  gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced departamentoEnh = null;
	  try {
	  	departamentoEnh = enhancedObject.getDepartamento();
	  	} catch (Throwable th) {}
	  	if (departamentoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Departamento objTo =  (gov.sir.core.negocio.modelo.Departamento)cache.get(departamentoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Departamento)DepartamentoTransferCopier.deepCopy(departamentoEnh, cache);
	  		}
	  		transferObject.setDepartamento(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdTipoActo
  	    try {
	  	transferObject.setIdTipoActo(enhancedObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
		  // Perven
  	    try {
	  	transferObject.setPerven(enhancedObject.getPerven());
	  	} catch (Throwable th ) {}
	  			
		  // Valimp
  	    try {
	  	transferObject.setValimp(enhancedObject.getValimp());
	  	} catch (Throwable th ) {}
	  			
		  // Tipoacto
  	  gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced tipoactoEnh = null;
	  try {
	  	tipoactoEnh = enhancedObject.getTipoacto();
	  	} catch (Throwable th) {}
	  	if (tipoactoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoActo objTo =  (gov.sir.core.negocio.modelo.TipoActo)cache.get(tipoactoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoActo)TipoActoTransferCopier.deepCopy(tipoactoEnh, cache);
	  		}
	  		transferObject.setTipoacto(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TarifaDepartamento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TarifaDepartamentoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdDepartamento
  	    try {
	  	enhancedObject.setIdDepartamento(transferObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // Departamento
  	    gov.sir.core.negocio.modelo.Departamento departamento = null;
	  	try {
	  	departamento = (gov.sir.core.negocio.modelo.Departamento)transferObject.getDepartamento();
	  	} catch (Throwable th ) {}
	  	if (departamento != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced)cache.get(departamento);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced)DepartamentoTransferCopier.deepCopy(departamento, cache);
	  		}
	  		enhancedObject.setDepartamento(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdTipoActo
  	    try {
	  	enhancedObject.setIdTipoActo(transferObject.getIdTipoActo());
	  	} catch (Throwable th ) {}
	  			
		  // Perven
  	    try {
	  	enhancedObject.setPerven(transferObject.getPerven());
	  	} catch (Throwable th ) {}
	  			
		  // Valimp
  	    try {
	  	enhancedObject.setValimp(transferObject.getValimp());
	  	} catch (Throwable th ) {}
	  			
		  // Tipoacto
  	    gov.sir.core.negocio.modelo.TipoActo tipoacto = null;
	  	try {
	  	tipoacto = (gov.sir.core.negocio.modelo.TipoActo)transferObject.getTipoacto();
	  	} catch (Throwable th ) {}
	  	if (tipoacto != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)cache.get(tipoacto);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)TipoActoTransferCopier.deepCopy(tipoacto, cache);
	  		}
	  		enhancedObject.setTipoacto(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}