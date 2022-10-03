package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class RolPantallaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.RolPantalla transferObject = new gov.sir.core.negocio.modelo.RolPantalla();
		cache.put(enhancedObject, transferObject );
		
		  // IdPantallaAdministrativa
  	    try {
	  	transferObject.setIdPantallaAdministrativa(enhancedObject.getIdPantallaAdministrativa());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	transferObject.setIdRol(enhancedObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Pantalla
  	  gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced pantallaEnh = null;
	  try {
	  	pantallaEnh = enhancedObject.getPantalla();
	  	} catch (Throwable th) {}
	  	if (pantallaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.PantallaAdministrativa objTo =  (gov.sir.core.negocio.modelo.PantallaAdministrativa)cache.get(pantallaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.PantallaAdministrativa)PantallaAdministrativaTransferCopier.deepCopy(pantallaEnh, cache);
	  		}
	  		transferObject.setPantalla(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Rol
  	  gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced rolEnh = null;
	  try {
	  	rolEnh = enhancedObject.getRol();
	  	} catch (Throwable th) {}
	  	if (rolEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.RolSIR objTo =  (gov.sir.core.negocio.modelo.RolSIR)cache.get(rolEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.RolSIR)RolSIRTransferCopier.deepCopy(rolEnh, cache);
	  		}
	  		transferObject.setRol(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.RolPantalla transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdPantallaAdministrativa
  	    try {
	  	enhancedObject.setIdPantallaAdministrativa(transferObject.getIdPantallaAdministrativa());
	  	} catch (Throwable th ) {}
	  			
		  // IdRol
  	    try {
	  	enhancedObject.setIdRol(transferObject.getIdRol());
	  	} catch (Throwable th ) {}
	  			
		  // Pantalla
  	    gov.sir.core.negocio.modelo.PantallaAdministrativa pantalla = null;
	  	try {
	  	pantalla = (gov.sir.core.negocio.modelo.PantallaAdministrativa)transferObject.getPantalla();
	  	} catch (Throwable th ) {}
	  	if (pantalla != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced)cache.get(pantalla);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced)PantallaAdministrativaTransferCopier.deepCopy(pantalla, cache);
	  		}
	  		enhancedObject.setPantalla(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Rol
  	    gov.sir.core.negocio.modelo.RolSIR rol = null;
	  	try {
	  	rol = (gov.sir.core.negocio.modelo.RolSIR)transferObject.getRol();
	  	} catch (Throwable th ) {}
	  	if (rol != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced)cache.get(rol);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced)RolSIRTransferCopier.deepCopy(rol, cache);
	  		}
	  		enhancedObject.setRol(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}