package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class RecursoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Recurso transferObject = new gov.sir.core.negocio.modelo.Recurso();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  		try {
		if (enhancedObject.getFecha() != null)
		{
	  	 transferObject.setFecha(new Date(enhancedObject.getFecha().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  	
		 // FechaEjecutoria
  		try {
		if (enhancedObject.getFechaEjecutoria() != null)
		{
	  	 transferObject.setFechaEjecutoria(new Date(enhancedObject.getFechaEjecutoria().getTime()));
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
	  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	transferObject.setIdTurno(enhancedObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHistoria
  	    try {
	  	transferObject.setIdTurnoHistoria(enhancedObject.getIdTurnoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHistoria
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced turnoHistoriaEnh = null;
	  try {
	  	turnoHistoriaEnh = enhancedObject.getTurnoHistoria();
	  	} catch (Throwable th) {}
	  	if (turnoHistoriaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TurnoHistoria objTo =  (gov.sir.core.negocio.modelo.TurnoHistoria)cache.get(turnoHistoriaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TurnoHistoria)TurnoHistoriaTransferCopier.deepCopy(turnoHistoriaEnh, cache);
	  		}
	  		transferObject.setTurnoHistoria(objTo);
	  		}
	  			  		
	  	}
	  			
		  // TextoUsuario
  	    try {
	  	transferObject.setTextoUsuario(enhancedObject.getTextoUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // Titulo
  	    try {
	  	transferObject.setTitulo(enhancedObject.getTitulo());
	  	} catch (Throwable th ) {}
	  			
		  // IdRecurso
  	    try {
	  	transferObject.setIdRecurso(enhancedObject.getIdRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // RespuestaRecurso
  	    try {
	  	transferObject.setRespuestaRecurso(enhancedObject.getRespuestaRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // TextoRecurso
  	    try {
	  	transferObject.setTextoRecurso(enhancedObject.getTextoRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // TipoRecurso
  	  gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced tipoRecursoEnh = null;
	  try {
	  	tipoRecursoEnh = enhancedObject.getTipoRecurso();
	  	} catch (Throwable th) {}
	  	if (tipoRecursoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoRecurso objTo =  (gov.sir.core.negocio.modelo.TipoRecurso)cache.get(tipoRecursoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoRecurso)TipoRecursoTransferCopier.deepCopy(tipoRecursoEnh, cache);
	  		}
	  		transferObject.setTipoRecurso(objTo);
	  		}
	  			  		
	  	}
	  			
		  // OficioRespuesta
  	  gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced oficioRespuestaEnh = null;
	  try {
	  	oficioRespuestaEnh = enhancedObject.getOficioRespuesta();
	  	} catch (Throwable th) {}
	  	if (oficioRespuestaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Oficio objTo =  (gov.sir.core.negocio.modelo.Oficio)cache.get(oficioRespuestaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Oficio)OficioTransferCopier.deepCopy(oficioRespuestaEnh, cache);
	  		}
	  		transferObject.setOficioRespuesta(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Recurso transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  	    try {
		if (transferObject.getFecha() != null)
		{
		  	enhancedObject.setFecha(new Date(transferObject.getFecha().getTime()));
		}
		} catch (Throwable th ) {}
		
		
		// FechaEjecutoria
  	    try {
		if (transferObject.getFechaEjecutoria() != null)
		{
		  	enhancedObject.setFechaEjecutoria(new Date(transferObject.getFechaEjecutoria().getTime()));
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
	  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	enhancedObject.setIdTurno(transferObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHistoria
  	    try {
	  	enhancedObject.setIdTurnoHistoria(transferObject.getIdTurnoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHistoria
  	    gov.sir.core.negocio.modelo.TurnoHistoria turnoHistoria = null;
	  	try {
	  	turnoHistoria = (gov.sir.core.negocio.modelo.TurnoHistoria)transferObject.getTurnoHistoria();
	  	} catch (Throwable th ) {}
	  	if (turnoHistoria != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)cache.get(turnoHistoria);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)TurnoHistoriaTransferCopier.deepCopy(turnoHistoria, cache);
	  		}
	  		enhancedObject.setTurnoHistoria(objEn);
	  		}
	  			  		
	  	}
	  			
		  // TextoUsuario
  	    try {
	  	enhancedObject.setTextoUsuario(transferObject.getTextoUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // Titulo
  	    try {
	  	enhancedObject.setTitulo(transferObject.getTitulo());
	  	} catch (Throwable th ) {}
	  			
		  // IdRecurso
  	    try {
	  	enhancedObject.setIdRecurso(transferObject.getIdRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // RespuestaRecurso
  	    try {
	  	enhancedObject.setRespuestaRecurso(transferObject.getRespuestaRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // TextoRecurso
  	    try {
	  	enhancedObject.setTextoRecurso(transferObject.getTextoRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // TipoRecurso
  	    gov.sir.core.negocio.modelo.TipoRecurso tipoRecurso = null;
	  	try {
	  	tipoRecurso = (gov.sir.core.negocio.modelo.TipoRecurso)transferObject.getTipoRecurso();
	  	} catch (Throwable th ) {}
	  	if (tipoRecurso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced)cache.get(tipoRecurso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced)TipoRecursoTransferCopier.deepCopy(tipoRecurso, cache);
	  		}
	  		enhancedObject.setTipoRecurso(objEn);
	  		}
	  			  		
	  	}
	  			
		  // OficioRespuesta
  	    gov.sir.core.negocio.modelo.Oficio oficioRespuesta = null;
	  	try {
	  	oficioRespuesta = (gov.sir.core.negocio.modelo.Oficio)transferObject.getOficioRespuesta();
	  	} catch (Throwable th ) {}
	  	if (oficioRespuesta != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced)cache.get(oficioRespuesta);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced)OficioTransferCopier.deepCopy(oficioRespuesta, cache);
	  		}
	  		enhancedObject.setOficioRespuesta(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}