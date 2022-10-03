package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class RolSIRTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.RolSIR transferObject = new gov.sir.core.negocio.modelo.RolSIR();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	transferObject.setIdRol(enhancedObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Pantalla
    	List pantalla = null;
  	try
  	{
  	pantalla = enhancedObject.getPantallas();
  	} catch (Throwable th) {}
	  	if (pantalla != null)
	  	{
		  	for(Iterator itera = pantalla.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced pantallaEnh = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.RolPantalla objTo = (gov.sir.core.negocio.modelo.RolPantalla)cache.get(pantallaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.RolPantalla)RolPantallaTransferCopier.deepCopy(pantallaEnh, cache);
		  		}
		  		transferObject.addPantalla(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.RolSIR transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	enhancedObject.setIdRol(transferObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Pantalla
    	List pantalla = null;
  	try
  	{
  	pantalla = transferObject.getPantallas();
  	} catch (Throwable th) { }
	  	if (pantalla != null)
	  	{
		  	for(Iterator itera = pantalla.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.RolPantalla pantallato = (gov.sir.core.negocio.modelo.RolPantalla)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)cache.get(pantallato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced)RolPantallaTransferCopier.deepCopy(pantallato, cache);
		  		}
		  		enhancedObject.addPantalla(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}