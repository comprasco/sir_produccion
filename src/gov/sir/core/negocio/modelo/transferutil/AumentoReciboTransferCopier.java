package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AumentoReciboTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.AumentoRecibo transferObject = new gov.sir.core.negocio.modelo.AumentoRecibo();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
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
	  			
		  // IdAumentoRecibo
  	    try {
	  	transferObject.setIdAumentoRecibo(enhancedObject.getIdAumentoRecibo());
	  	} catch (Throwable th ) {}
	  			
		  // Motivo
  	    try {
	  	transferObject.setMotivo(enhancedObject.getMotivo());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRecibo
  	    try {
	  	transferObject.setNumeroRecibo(enhancedObject.getNumeroRecibo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.AumentoRecibo transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
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
	  			
		  // IdAumentoRecibo
  	    try {
	  	enhancedObject.setIdAumentoRecibo(transferObject.getIdAumentoRecibo());
	  	} catch (Throwable th ) {}
	  			
		  // Motivo
  	    try {
	  	enhancedObject.setMotivo(transferObject.getMotivo());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRecibo
  	    try {
	  	enhancedObject.setNumeroRecibo(transferObject.getNumeroRecibo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}