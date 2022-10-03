package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class UsuarioSubtipoAtencionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion transferObject = new gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion();
		cache.put(enhancedObject, transferObject );
		
		  // Orden
  	    try {
	  	transferObject.setOrden(enhancedObject.getOrden());
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
	  			
		  // IdUsuario
  	    try {
	  	transferObject.setIdUsuario(enhancedObject.getIdUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	transferObject.setIdSubtipoAtencion(enhancedObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
		  // Subtipoatencion
  	  gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced subtipoatencionEnh = null;
	  try {
	  	subtipoatencionEnh = enhancedObject.getSubtipoatencion();
	  	} catch (Throwable th) {}
	  	if (subtipoatencionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SubtipoAtencion objTo =  (gov.sir.core.negocio.modelo.SubtipoAtencion)cache.get(subtipoatencionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SubtipoAtencion)SubtipoAtencionTransferCopier.deepCopy(subtipoatencionEnh, cache);
	  		}
	  		transferObject.setSubtipoatencion(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Orden
  	    try {
	  	enhancedObject.setOrden(transferObject.getOrden());
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
	  			
		  // IdUsuario
  	    try {
	  	enhancedObject.setIdUsuario(transferObject.getIdUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // IdSubtipoAtencion
  	    try {
	  	enhancedObject.setIdSubtipoAtencion(transferObject.getIdSubtipoAtencion());
	  	} catch (Throwable th ) {}
	  			
		  // Subtipoatencion
  	    gov.sir.core.negocio.modelo.SubtipoAtencion subtipoatencion = null;
	  	try {
	  	subtipoatencion = (gov.sir.core.negocio.modelo.SubtipoAtencion)transferObject.getSubtipoatencion();
	  	} catch (Throwable th ) {}
	  	if (subtipoatencion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced)cache.get(subtipoatencion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced)SubtipoAtencionTransferCopier.deepCopy(subtipoatencion, cache);
	  		}
	  		enhancedObject.setSubtipoatencion(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}