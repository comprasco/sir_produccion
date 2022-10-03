package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class BancoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Banco transferObject = new gov.sir.core.negocio.modelo.Banco();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdBanco
  	    try {
	  	transferObject.setIdBanco(enhancedObject.getIdBanco());
	  	} catch (Throwable th ) {}
	  			
		  // Sucursal
    	List sucursal = null;
  	try
  	{
  	sucursal = enhancedObject.getSucursales();
  	} catch (Throwable th) {}
	  	if (sucursal != null)
	  	{
		  	for(Iterator itera = sucursal.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced sucursalEnh = (gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SucursalBanco objTo = (gov.sir.core.negocio.modelo.SucursalBanco)cache.get(sucursalEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SucursalBanco)SucursalBancoTransferCopier.deepCopy(sucursalEnh, cache);
		  		}
		  		transferObject.addSucursal(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Banco transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdBanco
  	    try {
	  	enhancedObject.setIdBanco(transferObject.getIdBanco());
	  	} catch (Throwable th ) {}
	  			
		  // Sucursal
    	List sucursal = null;
  	try
  	{
  	sucursal = transferObject.getSucursales();
  	} catch (Throwable th) { }
	  	if (sucursal != null)
	  	{
		  	for(Iterator itera = sucursal.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SucursalBanco sucursalto = (gov.sir.core.negocio.modelo.SucursalBanco)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced)cache.get(sucursalto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced)SucursalBancoTransferCopier.deepCopy(sucursalto, cache);
		  		}
		  		enhancedObject.addSucursal(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}