package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class MunicipioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Municipio transferObject = new gov.sir.core.negocio.modelo.Municipio();
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
	  			
		  // Vereda
    	List vereda = null;
  	try
  	{
  	vereda = enhancedObject.getVeredas();
  	} catch (Throwable th) {}
	  	if (vereda != null)
	  	{
		  	for(Iterator itera = vereda.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced veredaEnh = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Vereda objTo = (gov.sir.core.negocio.modelo.Vereda)cache.get(veredaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Vereda)VeredaTransferCopier.deepCopy(veredaEnh, cache);
		  		}
		  		transferObject.addVereda(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Municipio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced();
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
	  			
		  // Vereda
    	List vereda = null;
  	try
  	{
  	vereda = transferObject.getVeredas();
  	} catch (Throwable th) { }
	  	if (vereda != null)
	  	{
		  	for(Iterator itera = vereda.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Vereda veredato = (gov.sir.core.negocio.modelo.Vereda)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)cache.get(veredato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)VeredaTransferCopier.deepCopy(veredato, cache);
		  		}
		  		enhancedObject.addVereda(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}