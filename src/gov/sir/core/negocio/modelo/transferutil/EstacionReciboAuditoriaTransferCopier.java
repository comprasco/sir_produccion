package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EstacionReciboAuditoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.EstacionReciboAuditoria transferObject = new gov.sir.core.negocio.modelo.EstacionReciboAuditoria();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdEstacion
  	    try {
	  	transferObject.setIdEstacion(enhancedObject.getIdEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdAuditoria
  	    try {
	  	transferObject.setIdAuditoria(enhancedObject.getIdAuditoria());
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
	  			
		  // NumeroProceso
  	    try {
	  	transferObject.setNumeroProceso(enhancedObject.getNumeroProceso());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroNuevo
  	    try {
	  	transferObject.setNumeroNuevo(enhancedObject.getNumeroNuevo());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroAnterior
  	    try {
	  	transferObject.setNumeroAnterior(enhancedObject.getNumeroAnterior());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.EstacionReciboAuditoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdEstacion
  	    try {
	  	enhancedObject.setIdEstacion(transferObject.getIdEstacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdAuditoria
  	    try {
	  	enhancedObject.setIdAuditoria(transferObject.getIdAuditoria());
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
	  			
		  // NumeroProceso
  	    try {
	  	enhancedObject.setNumeroProceso(transferObject.getNumeroProceso());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroNuevo
  	    try {
	  	enhancedObject.setNumeroNuevo(transferObject.getNumeroNuevo());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroAnterior
  	    try {
	  	enhancedObject.setNumeroAnterior(transferObject.getNumeroAnterior());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}