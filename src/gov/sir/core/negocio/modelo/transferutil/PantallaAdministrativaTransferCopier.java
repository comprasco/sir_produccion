package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class PantallaAdministrativaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.PantallaAdministrativa transferObject = new gov.sir.core.negocio.modelo.PantallaAdministrativa();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdPantallaAdministrativa
  	    try {
	  	transferObject.setIdPantallaAdministrativa(enhancedObject.getIdPantallaAdministrativa());
	  	} catch (Throwable th ) {}
	  			
		  // Vista
  	    try {
	  	transferObject.setVista(enhancedObject.getVista());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPantalla
  	  gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced tipoPantallaEnh = null;
	  try {
	  	tipoPantallaEnh = enhancedObject.getTipoPantalla();
	  	} catch (Throwable th) {}
	  	if (tipoPantallaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoPantalla objTo =  (gov.sir.core.negocio.modelo.TipoPantalla)cache.get(tipoPantallaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoPantalla)TipoPantallaTransferCopier.deepCopy(tipoPantallaEnh, cache);
	  		}
	  		transferObject.setTipoPantalla(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Pagina
  	    try {
	  	transferObject.setPagina(enhancedObject.getPagina());
	  	} catch (Throwable th ) {}
	  			
		  // Role
    	List role = null;
  	try
  	{
  	role = enhancedObject.getRoles();
  	} catch (Throwable th) {}
	  	if (role != null)
	  	{
		  	for(Iterator itera = role.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced roleEnh = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.RolPantalla objTo = (gov.sir.core.negocio.modelo.RolPantalla)cache.get(roleEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.RolPantalla)RolPantallaTransferCopier.deepCopy(roleEnh, cache);
		  		}
		  		transferObject.addRole(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.PantallaAdministrativa transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdPantallaAdministrativa
  	    try {
	  	enhancedObject.setIdPantallaAdministrativa(transferObject.getIdPantallaAdministrativa());
	  	} catch (Throwable th ) {}
	  			
		  // Vista
  	    try {
	  	enhancedObject.setVista(transferObject.getVista());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPantalla
  	    gov.sir.core.negocio.modelo.TipoPantalla tipoPantalla = null;
	  	try {
	  	tipoPantalla = (gov.sir.core.negocio.modelo.TipoPantalla)transferObject.getTipoPantalla();
	  	} catch (Throwable th ) {}
	  	if (tipoPantalla != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced)cache.get(tipoPantalla);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced)TipoPantallaTransferCopier.deepCopy(tipoPantalla, cache);
	  		}
	  		enhancedObject.setTipoPantalla(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Pagina
  	    try {
	  	enhancedObject.setPagina(transferObject.getPagina());
	  	} catch (Throwable th ) {}
	  			
		  // Role
    	List role = null;
  	try
  	{
  	role = transferObject.getRoles();
  	} catch (Throwable th) { }
	  	if (role != null)
	  	{
		  	for(Iterator itera = role.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.RolPantalla roleto = (gov.sir.core.negocio.modelo.RolPantalla)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)cache.get(roleto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)RolPantallaTransferCopier.deepCopy(roleto, cache);
		  		}
		  		enhancedObject.addRole(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}