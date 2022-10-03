package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OPLookupCodesTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.OPLookupCodes transferObject = new gov.sir.core.negocio.modelo.OPLookupCodes();
		cache.put(enhancedObject, transferObject );
		
		  // Tipo
  	    try {
	  	transferObject.setTipo(enhancedObject.getTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Codigo
  	    try {
	  	transferObject.setCodigo(enhancedObject.getCodigo());
	  	} catch (Throwable th ) {}
	  			
		  // OPLookupType
  	  gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced oPLookupTypeEnh = null;
	  try {
	  	oPLookupTypeEnh = enhancedObject.getOPLookupType();
	  	} catch (Throwable th) {}
	  	if (oPLookupTypeEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.OPLookupTypes objTo =  (gov.sir.core.negocio.modelo.OPLookupTypes)cache.get(oPLookupTypeEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.OPLookupTypes)OPLookupTypesTransferCopier.deepCopy(oPLookupTypeEnh, cache);
	  		}
	  		transferObject.setOPLookupType(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.OPLookupCodes transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Tipo
  	    try {
	  	enhancedObject.setTipo(transferObject.getTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Codigo
  	    try {
	  	enhancedObject.setCodigo(transferObject.getCodigo());
	  	} catch (Throwable th ) {}
	  			
		  // OPLookupType
  	    gov.sir.core.negocio.modelo.OPLookupTypes oPLookupType = null;
	  	try {
	  	oPLookupType = (gov.sir.core.negocio.modelo.OPLookupTypes)transferObject.getOPLookupType();
	  	} catch (Throwable th ) {}
	  	if (oPLookupType != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced)cache.get(oPLookupType);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced)OPLookupTypesTransferCopier.deepCopy(oPLookupType, cache);
	  		}
	  		enhancedObject.setOPLookupType(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}