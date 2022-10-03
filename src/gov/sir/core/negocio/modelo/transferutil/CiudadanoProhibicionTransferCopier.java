package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CiudadanoProhibicionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CiudadanoProhibicion transferObject = new gov.sir.core.negocio.modelo.CiudadanoProhibicion();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  	
		  // Comentario Anulacion
  	    try {
	  	transferObject.setComentarioAnulacion(enhancedObject.getComentarioAnulacion());
	  	} catch (Throwable th ) {}	  	
	  			
		  // UsuarioCreacion
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioCreacionEnh = null;
	  try {
	  	usuarioCreacionEnh = enhancedObject.getUsuarioCreacion();
	  	} catch (Throwable th) {}
	  	if (usuarioCreacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioCreacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioCreacionEnh, cache);
	  		}
	  		transferObject.setUsuarioCreacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdCiudadano
  	    try {
	  	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Ciudadano
  	  gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced ciudadanoEnh = null;
	  try {
	  	ciudadanoEnh = enhancedObject.getCiudadano();
	  	} catch (Throwable th) {}
	  	if (ciudadanoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Ciudadano objTo =  (gov.sir.core.negocio.modelo.Ciudadano)cache.get(ciudadanoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Ciudadano)CiudadanoTransferCopier.deepCopy(ciudadanoEnh, cache);
	  		}
	  		transferObject.setCiudadano(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdProhibicion
  	    try {
	  	transferObject.setIdProhibicion(enhancedObject.getIdProhibicion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFinal
  		try {
		if (enhancedObject.getFechaFinal() != null)
		{
	  	 transferObject.setFechaFinal(new Date(enhancedObject.getFechaFinal().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FechaInicial
  		try {
		if (enhancedObject.getFechaInicial() != null)
		{
	  	 transferObject.setFechaInicial(new Date(enhancedObject.getFechaInicial().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Prohibicion
  	  gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced prohibicionEnh = null;
	  try {
	  	prohibicionEnh = enhancedObject.getProhibicion();
	  	} catch (Throwable th) {}
	  	if (prohibicionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Prohibicion objTo =  (gov.sir.core.negocio.modelo.Prohibicion)cache.get(prohibicionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Prohibicion)ProhibicionTransferCopier.deepCopy(prohibicionEnh, cache);
	  		}
	  		transferObject.setProhibicion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // UsuarioEliminacion
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioEliminacionEnh = null;
	  try {
	  	usuarioEliminacionEnh = enhancedObject.getUsuarioEliminacion();
	  	} catch (Throwable th) {}
	  	if (usuarioEliminacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioEliminacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioEliminacionEnh, cache);
	  		}
	  		transferObject.setUsuarioEliminacion(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CiudadanoProhibicion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  	
		  // Comentario Anulacion
  	    try {
	  	enhancedObject.setComentarioAnulacion(transferObject.getComentarioAnulacion());
	  	} catch (Throwable th ) {}	  	
	  			
		  // UsuarioCreacion
  	    gov.sir.core.negocio.modelo.Usuario usuarioCreacion = null;
	  	try {
	  	usuarioCreacion = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioCreacion();
	  	} catch (Throwable th ) {}
	  	if (usuarioCreacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioCreacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioCreacion, cache);
	  		}
	  		enhancedObject.setUsuarioCreacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdCiudadano
  	    try {
	  	enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Ciudadano
  	    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
	  	try {
	  	ciudadano = (gov.sir.core.negocio.modelo.Ciudadano)transferObject.getCiudadano();
	  	} catch (Throwable th ) {}
	  	if (ciudadano != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)cache.get(ciudadano);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)CiudadanoTransferCopier.deepCopy(ciudadano, cache);
	  		}
	  		enhancedObject.setCiudadano(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdProhibicion
  	    try {
	  	enhancedObject.setIdProhibicion(transferObject.getIdProhibicion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFinal
  	    try {
		if (transferObject.getFechaFinal() != null)
		{
		  	enhancedObject.setFechaFinal(new Date(transferObject.getFechaFinal().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FechaInicial
  	    try {
		if (transferObject.getFechaInicial() != null)
		{
		  	enhancedObject.setFechaInicial(new Date(transferObject.getFechaInicial().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Prohibicion
  	    gov.sir.core.negocio.modelo.Prohibicion prohibicion = null;
	  	try {
	  	prohibicion = (gov.sir.core.negocio.modelo.Prohibicion)transferObject.getProhibicion();
	  	} catch (Throwable th ) {}
	  	if (prohibicion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced)cache.get(prohibicion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced)ProhibicionTransferCopier.deepCopy(prohibicion, cache);
	  		}
	  		enhancedObject.setProhibicion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // UsuarioEliminacion
  	    gov.sir.core.negocio.modelo.Usuario usuarioEliminacion = null;
	  	try {
	  	usuarioEliminacion = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioEliminacion();
	  	} catch (Throwable th ) {}
	  	if (usuarioEliminacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioEliminacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioEliminacion, cache);
	  		}
	  		enhancedObject.setUsuarioEliminacion(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}