package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class EstadoHistoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.EstadoHistoria transferObject = new gov.sir.core.negocio.modelo.EstadoHistoria();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioEnh = null;
	  try {
	  	folioEnh = enhancedObject.getFolio();
	  	} catch (Throwable th) {}
	  	if (folioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioEnh, cache);
	  		}
	  		transferObject.setFolio(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdEstadoHistoria
  	    try {
	  	transferObject.setIdEstadoHistoria(enhancedObject.getIdEstadoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // FechaRegistro
  		try {
		if (enhancedObject.getFechaRegistro() != null)
		{
	  	 transferObject.setFechaRegistro(new Date(enhancedObject.getFechaRegistro().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // EstadoDestino
  	  gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced estadoDestinoEnh = null;
	  try {
	  	estadoDestinoEnh = enhancedObject.getEstadoDestino();
	  	} catch (Throwable th) {}
	  	if (estadoDestinoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.EstadoFolio objTo =  (gov.sir.core.negocio.modelo.EstadoFolio)cache.get(estadoDestinoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.EstadoFolio)EstadoFolioTransferCopier.deepCopy(estadoDestinoEnh, cache);
	  		}
	  		transferObject.setEstadoDestino(objTo);
	  		}
	  			  		
	  	}
	  			
		  // EstadoOrigen
  	  gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced estadoOrigenEnh = null;
	  try {
	  	estadoOrigenEnh = enhancedObject.getEstadoOrigen();
	  	} catch (Throwable th) {}
	  	if (estadoOrigenEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.EstadoFolio objTo =  (gov.sir.core.negocio.modelo.EstadoFolio)cache.get(estadoOrigenEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.EstadoFolio)EstadoFolioTransferCopier.deepCopy(estadoOrigenEnh, cache);
	  		}
	  		transferObject.setEstadoOrigen(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ComentarioAnterior
  	    try {
	  	transferObject.setComentarioAnterior(enhancedObject.getComentarioAnterior());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.EstadoHistoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    gov.sir.core.negocio.modelo.Folio folio = null;
	  	try {
	  	folio = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolio();
	  	} catch (Throwable th ) {}
	  	if (folio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folio, cache);
	  		}
	  		enhancedObject.setFolio(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdEstadoHistoria
  	    try {
	  	enhancedObject.setIdEstadoHistoria(transferObject.getIdEstadoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // FechaRegistro
  	    try {
		if (transferObject.getFechaRegistro() != null)
		{
		  	enhancedObject.setFechaRegistro(new Date(transferObject.getFechaRegistro().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // EstadoDestino
  	    gov.sir.core.negocio.modelo.EstadoFolio estadoDestino = null;
	  	try {
	  	estadoDestino = (gov.sir.core.negocio.modelo.EstadoFolio)transferObject.getEstadoDestino();
	  	} catch (Throwable th ) {}
	  	if (estadoDestino != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)cache.get(estadoDestino);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)EstadoFolioTransferCopier.deepCopy(estadoDestino, cache);
	  		}
	  		enhancedObject.setEstadoDestino(objEn);
	  		}
	  			  		
	  	}
	  			
		  // EstadoOrigen
  	    gov.sir.core.negocio.modelo.EstadoFolio estadoOrigen = null;
	  	try {
	  	estadoOrigen = (gov.sir.core.negocio.modelo.EstadoFolio)transferObject.getEstadoOrigen();
	  	} catch (Throwable th ) {}
	  	if (estadoOrigen != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)cache.get(estadoOrigen);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)EstadoFolioTransferCopier.deepCopy(estadoOrigen, cache);
	  		}
	  		enhancedObject.setEstadoOrigen(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ComentarioAnterior
  	    try {
	  	enhancedObject.setComentarioAnterior(transferObject.getComentarioAnterior());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}