package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoHistoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TurnoHistoria transferObject = new gov.sir.core.negocio.modelo.TurnoHistoria();
		cache.put(enhancedObject, transferObject );
		
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	  gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced procesoEnh = null;
	  try {
	  	procesoEnh = enhancedObject.getProceso();
	  	} catch (Throwable th) {}
	  	if (procesoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Proceso objTo =  (gov.sir.core.negocio.modelo.Proceso)cache.get(procesoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Proceso)ProcesoTransferCopier.deepCopy(procesoEnh, cache);
	  		}
	  		transferObject.setProceso(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // Activo
    try {
  	transferObject.setActivo(enhancedObject.isActivo());
  } catch (Throwable th ) {}
  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoEnh = null;
	  try {
	  	turnoEnh = enhancedObject.getTurno();
	  	} catch (Throwable th) {}
	  	if (turnoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoEnh, cache);
	  		}
	  		transferObject.setTurno(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdTurno
  	    try {
	  	transferObject.setIdTurno(enhancedObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHistoria
  	    try {
	  	transferObject.setIdTurnoHistoria(enhancedObject.getIdTurnoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	transferObject.setRespuesta(enhancedObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacion
  	    try {
	  	transferObject.setIdRelacion(enhancedObject.getIdRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // Fase
  	    try {
	  	transferObject.setFase(enhancedObject.getFase());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRecurso
  	    try {
	  	transferObject.setLastIdRecurso(enhancedObject.getLastIdRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacionSiguiente
  	    try {
	  	transferObject.setIdRelacionSiguiente(enhancedObject.getIdRelacionSiguiente());
	  	} catch (Throwable th ) {}
	  			
		  // Accion
  	    try {
	  	transferObject.setAccion(enhancedObject.getAccion());
	  	} catch (Throwable th ) {}
	  			
		  // StackPos
  	    try {
	  	transferObject.setStackPos(enhancedObject.getStackPos());
	  	} catch (Throwable th ) {}
               // Ncopias
  	    try {
	  	transferObject.setNcopias(enhancedObject.getNcopias());
	  	} catch (Throwable th ) {}
		  // FaseAnterior
  	    try {
	  	transferObject.setFaseAnterior(enhancedObject.getFaseAnterior());
	  	} catch (Throwable th ) {}
	  			
		  // NombreFase
  	    try {
	  	transferObject.setNombreFase(enhancedObject.getNombreFase());
	  	} catch (Throwable th ) {}
            
                // sentJuzgado
  	    try {
	  	transferObject.setSentJuzgado(enhancedObject.getSentJuzgado());
	  	} catch (Throwable th ) {}
            
            // fechaJuzgado
  	    try {
	  	transferObject.setFechaJuzgado(enhancedObject.getFechaJuzgado());
	  	} catch (Throwable th ) {}
	  			
		  // Recurso
    	List recurso = null;
  	try
  	{
  	recurso = enhancedObject.getRecursos();
  	} catch (Throwable th) {}
	  	if (recurso != null)
	  	{
		  	for(Iterator itera = recurso.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced recursoEnh = (gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Recurso objTo = (gov.sir.core.negocio.modelo.Recurso)cache.get(recursoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Recurso)RecursoTransferCopier.deepCopy(recursoEnh, cache);
		  		}
		  		transferObject.addRecurso(objTo);
		  		}
		  				  	}
		}
				
		  // IdAdministradorSAS
  	    try {
	  	transferObject.setIdAdministradorSAS(enhancedObject.getIdAdministradorSAS());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioAtiende
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioAtiendeEnh = null;
	  try {
	  	usuarioAtiendeEnh = enhancedObject.getUsuarioAtiende();
	  	} catch (Throwable th) {}
	  	if (usuarioAtiendeEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioAtiendeEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioAtiendeEnh, cache);
	  		}
	  		transferObject.setUsuarioAtiende(objTo);
	  		}
	  			  		
	  	}
	  			
		  // NumeroCopiasReimpresion
  	    try {
	  	transferObject.setNumeroCopiasReimpresion(enhancedObject.getNumeroCopiasReimpresion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatriculaImpresa
  	    try {
	  	transferObject.setIdMatriculaImpresa(enhancedObject.getIdMatriculaImpresa());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TurnoHistoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	    gov.sir.core.negocio.modelo.Proceso proceso = null;
	  	try {
	  	proceso = (gov.sir.core.negocio.modelo.Proceso)transferObject.getProceso();
	  	} catch (Throwable th ) {}
	  	if (proceso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)cache.get(proceso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)ProcesoTransferCopier.deepCopy(proceso, cache);
	  		}
	  		enhancedObject.setProceso(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // Activo
      try {
  	enhancedObject.setActivo(transferObject.isActivo());
  	} catch (Throwable th ) {}
  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	    gov.sir.core.negocio.modelo.Turno turno = null;
	  	try {
	  	turno = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurno();
	  	} catch (Throwable th ) {}
	  	if (turno != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turno);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turno, cache);
	  		}
	  		enhancedObject.setTurno(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdTurno
  	    try {
	  	enhancedObject.setIdTurno(transferObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurnoHistoria
  	    try {
	  	enhancedObject.setIdTurnoHistoria(transferObject.getIdTurnoHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // Respuesta
  	    try {
	  	enhancedObject.setRespuesta(transferObject.getRespuesta());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacion
  	    try {
	  	enhancedObject.setIdRelacion(transferObject.getIdRelacion());
	  	} catch (Throwable th ) {}
	  			
		  // Fase
  	    try {
	  	enhancedObject.setFase(transferObject.getFase());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdRecurso
  	    try {
	  	enhancedObject.setLastIdRecurso(transferObject.getLastIdRecurso());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacionSiguiente
  	    try {
	  	enhancedObject.setIdRelacionSiguiente(transferObject.getIdRelacionSiguiente());
	  	} catch (Throwable th ) {}
	  			
		  // Accion
  	    try {
	  	enhancedObject.setAccion(transferObject.getAccion());
	  	} catch (Throwable th ) {}
	  			
		  // StackPos
  	    try {
	  	enhancedObject.setStackPos(transferObject.getStackPos());
	  	} catch (Throwable th ) {}
              // Ncopias
  	    try {
	  	enhancedObject.setNcopias(transferObject.getNcopias());
	  	} catch (Throwable th ) {}
	  			
		  // FaseAnterior
  	    try {
	  	enhancedObject.setFaseAnterior(transferObject.getFaseAnterior());
	  	} catch (Throwable th ) {}
	  			
		  // NombreFase
  	    try {
	  	enhancedObject.setNombreFase(transferObject.getNombreFase());
	  	} catch (Throwable th ) {}
	  			
            	  // sentJuzgado
  	    try {
	  	enhancedObject.setSentJuzgado(transferObject.getSentJuzgado());
	  	} catch (Throwable th ) {}
            
             // fechaJuzgado
  	    try {
	  	enhancedObject.setFechaJuzgado(transferObject.getFechaJuzgado());
	  	} catch (Throwable th ) {}
		  // Recurso
    	List recurso = null;
  	try
  	{
  	recurso = transferObject.getRecursos();
  	} catch (Throwable th) { }
	  	if (recurso != null)
	  	{
		  	for(Iterator itera = recurso.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Recurso recursoto = (gov.sir.core.negocio.modelo.Recurso)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced)cache.get(recursoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced)RecursoTransferCopier.deepCopy(recursoto, cache);
		  		}
		  		enhancedObject.addRecurso(objEn);
		  				  		}
		  				  	}
		}
				
		  // IdAdministradorSAS
  	    try {
	  	enhancedObject.setIdAdministradorSAS(transferObject.getIdAdministradorSAS());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioAtiende
  	    gov.sir.core.negocio.modelo.Usuario usuarioAtiende = null;
	  	try {
	  	usuarioAtiende = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioAtiende();
	  	} catch (Throwable th ) {}
	  	if (usuarioAtiende != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioAtiende);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioAtiende, cache);
	  		}
	  		enhancedObject.setUsuarioAtiende(objEn);
	  		}
	  			  		
	  	}
	  			
		  // NumeroCopiasReimpresion
  	    try {
	  	enhancedObject.setNumeroCopiasReimpresion(transferObject.getNumeroCopiasReimpresion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatriculaImpresa
  	    try {
	  	enhancedObject.setIdMatriculaImpresa(transferObject.getIdMatriculaImpresa());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}