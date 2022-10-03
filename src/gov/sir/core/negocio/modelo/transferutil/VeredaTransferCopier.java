package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class VeredaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Vereda transferObject = new gov.sir.core.negocio.modelo.Vereda();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	transferObject.setIdDepartamento(enhancedObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // IdMunicipio
  	    try {
	  	transferObject.setIdMunicipio(enhancedObject.getIdMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // IdVereda
  	    try {
	  	transferObject.setIdVereda(enhancedObject.getIdVereda());
	  	} catch (Throwable th ) {}
	  			
		  // Municipio
  	  gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced municipioEnh = null;
	  try {
	  	municipioEnh = enhancedObject.getMunicipio();
	  	} catch (Throwable th) {}
	  	if (municipioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Municipio objTo =  (gov.sir.core.negocio.modelo.Municipio)cache.get(municipioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Municipio)MunicipioTransferCopier.deepCopy(municipioEnh, cache);
	  		}
	  		transferObject.setMunicipio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Cabecera
    try {
  	transferObject.setCabecera(enhancedObject.isCabecera());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Vereda transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	enhancedObject.setIdDepartamento(transferObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // IdMunicipio
  	    try {
	  	enhancedObject.setIdMunicipio(transferObject.getIdMunicipio());
	  	} catch (Throwable th ) {}
	  			
		  // IdVereda
  	    try {
	  	enhancedObject.setIdVereda(transferObject.getIdVereda());
	  	} catch (Throwable th ) {}
	  			
		  // Municipio
  	    gov.sir.core.negocio.modelo.Municipio municipio = null;
	  	try {
	  	municipio = (gov.sir.core.negocio.modelo.Municipio)transferObject.getMunicipio();
	  	} catch (Throwable th ) {}
	  	if (municipio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced)cache.get(municipio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced)MunicipioTransferCopier.deepCopy(municipio, cache);
	  		}
	  		enhancedObject.setMunicipio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Cabecera
      try {
  	enhancedObject.setCabecera(transferObject.isCabecera());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}