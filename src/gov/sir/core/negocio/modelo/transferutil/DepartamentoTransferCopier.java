package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DepartamentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Departamento transferObject = new gov.sir.core.negocio.modelo.Departamento();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	transferObject.setIdDepartamento(enhancedObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // Municipio
    	List municipio = null;
  	try
  	{
  	municipio = enhancedObject.getMunicipios();
  	} catch (Throwable th) {}
	  	if (municipio != null)
	  	{
		  	for(Iterator itera = municipio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced municipioEnh = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Municipio objTo = (gov.sir.core.negocio.modelo.Municipio)cache.get(municipioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Municipio)MunicipioTransferCopier.deepCopy(municipioEnh, cache);
		  		}
		  		transferObject.addMunicipio(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Departamento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDepartamento
  	    try {
	  	enhancedObject.setIdDepartamento(transferObject.getIdDepartamento());
	  	} catch (Throwable th ) {}
	  			
		  // Municipio
    	List municipio = null;
  	try
  	{
  	municipio = transferObject.getMunicipios();
  	} catch (Throwable th) { }
	  	if (municipio != null)
	  	{
		  	for(Iterator itera = municipio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Municipio municipioto = (gov.sir.core.negocio.modelo.Municipio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced)cache.get(municipioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced)MunicipioTransferCopier.deepCopy(municipioto, cache);
		  		}
		  		enhancedObject.addMunicipio(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}