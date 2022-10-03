package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AuditoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Auditoria transferObject = new gov.sir.core.negocio.modelo.Auditoria();
		cache.put(enhancedObject, transferObject );
		
		  // IdAuditoria
  	    try {
	  	transferObject.setIdAuditoria(enhancedObject.getIdAuditoria());
	  	} catch (Throwable th ) {}
	  			
		  // Direccion
  	    try {
	  	transferObject.setDireccion(enhancedObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaLogin
  		try {
		if (enhancedObject.getFechaLogin() != null)
		{
	  	 transferObject.setFechaLogin(new Date(enhancedObject.getFechaLogin().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FechaLogout
  		try {
		if (enhancedObject.getFechaLogout() != null)
		{
	  	 transferObject.setFechaLogout(new Date(enhancedObject.getFechaLogout().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Host
  	    try {
	  	transferObject.setHost(enhancedObject.getHost());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioWeb
  	    try {
	  	transferObject.setUsuarioWeb(enhancedObject.getUsuarioWeb());
	  	} catch (Throwable th ) {}
	  			
		  // Usuario
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioEnh = null;
	  try {
	  	usuarioEnh = enhancedObject.getUsuario();
	  	} catch (Throwable th) {}
	  	if (usuarioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioEnh, cache);
	  		}
	  		transferObject.setUsuario(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Auditoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdAuditoria
  	    try {
	  	enhancedObject.setIdAuditoria(transferObject.getIdAuditoria());
	  	} catch (Throwable th ) {}
	  			
		  // Direccion
  	    try {
	  	enhancedObject.setDireccion(transferObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaLogin
  	    try {
		if (transferObject.getFechaLogin() != null)
		{
		  	enhancedObject.setFechaLogin(new Date(transferObject.getFechaLogin().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FechaLogout
  	    try {
		if (transferObject.getFechaLogout() != null)
		{
		  	enhancedObject.setFechaLogout(new Date(transferObject.getFechaLogout().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Host
  	    try {
	  	enhancedObject.setHost(transferObject.getHost());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioWeb
  	    try {
	  	enhancedObject.setUsuarioWeb(transferObject.getUsuarioWeb());
	  	} catch (Throwable th ) {}
	  			
		  // Usuario
  	    gov.sir.core.negocio.modelo.Usuario usuario = null;
	  	try {
	  	usuario = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuario();
	  	} catch (Throwable th ) {}
	  	if (usuario != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuario);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuario, cache);
	  		}
	  		enhancedObject.setUsuario(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}