package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class RelacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Relacion transferObject = new gov.sir.core.negocio.modelo.Relacion();
		cache.put(enhancedObject, transferObject );
		
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  		try {
		if (enhancedObject.getFecha() != null)
		{
	  	 transferObject.setFecha(new Date(enhancedObject.getFecha().getTime()));
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
	  			
		  // Nota
  	    try {
	  	transferObject.setNota(enhancedObject.getNota());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	transferObject.setRespuesta(enhancedObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacion
  	    try {
	  	transferObject.setIdRelacion(enhancedObject.getIdRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // TipoRelacion
  	  gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced tipoRelacionEnh = null;
	  try {
	  	tipoRelacionEnh = enhancedObject.getTipoRelacion();
	  	} catch (Throwable th) {}
	  	if (tipoRelacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoRelacion objTo =  (gov.sir.core.negocio.modelo.TipoRelacion)cache.get(tipoRelacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoRelacion)TipoRelacionTransferCopier.deepCopy(tipoRelacionEnh, cache);
	  		}
	  		transferObject.setTipoRelacion(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Relacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  	    try {
		if (transferObject.getFecha() != null)
		{
		  	enhancedObject.setFecha(new Date(transferObject.getFecha().getTime()));
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
	  			
		  // Nota
  	    try {
	  	enhancedObject.setNota(transferObject.getNota());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	enhancedObject.setRespuesta(transferObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacion
  	    try {
	  	enhancedObject.setIdRelacion(transferObject.getIdRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // TipoRelacion
  	    gov.sir.core.negocio.modelo.TipoRelacion tipoRelacion = null;
	  	try {
	  	tipoRelacion = (gov.sir.core.negocio.modelo.TipoRelacion)transferObject.getTipoRelacion();
	  	} catch (Throwable th ) {}
	  	if (tipoRelacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced)cache.get(tipoRelacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced)TipoRelacionTransferCopier.deepCopy(tipoRelacion, cache);
	  		}
	  		enhancedObject.setTipoRelacion(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}