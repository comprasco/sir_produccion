package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class GrupoNaturalezaJuridicaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica transferObject = new gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdGrupoNatJuridica
  	    try {
	  	transferObject.setIdGrupoNatJuridica(enhancedObject.getIdGrupoNatJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NaturalezaJuridica
    	List naturalezaJuridica = null;
  	try
  	{
  	naturalezaJuridica = enhancedObject.getNaturalezaJuridicas();
  	} catch (Throwable th) {}
	  	if (naturalezaJuridica != null)
	  	{
		  	for(Iterator itera = naturalezaJuridica.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced naturalezaJuridicaEnh = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.NaturalezaJuridica objTo = (gov.sir.core.negocio.modelo.NaturalezaJuridica)cache.get(naturalezaJuridicaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.NaturalezaJuridica)NaturalezaJuridicaTransferCopier.deepCopy(naturalezaJuridicaEnh, cache);
		  		}
		  		transferObject.addNaturalezaJuridica(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdGrupoNatJuridica
  	    try {
	  	enhancedObject.setIdGrupoNatJuridica(transferObject.getIdGrupoNatJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NaturalezaJuridica
    	List naturalezaJuridica = null;
  	try
  	{
  	naturalezaJuridica = transferObject.getNaturalezaJuridicas();
  	} catch (Throwable th) { }
	  	if (naturalezaJuridica != null)
	  	{
		  	for(Iterator itera = naturalezaJuridica.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.NaturalezaJuridica naturalezaJuridicato = (gov.sir.core.negocio.modelo.NaturalezaJuridica)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced)cache.get(naturalezaJuridicato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced)NaturalezaJuridicaTransferCopier.deepCopy(naturalezaJuridicato, cache);
		  		}
		  		enhancedObject.addNaturalezaJuridica(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}