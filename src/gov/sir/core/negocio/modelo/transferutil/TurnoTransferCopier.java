package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TurnoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Turno transferObject = new gov.sir.core.negocio.modelo.Turno();
		cache.put(enhancedObject, transferObject );
		
		  // Error
    try {
  	transferObject.setError(enhancedObject.isError());
  } catch (Throwable th ) {}
  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	transferObject.setIdFase(enhancedObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	transferObject.setIdWorkflow(enhancedObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudAntiguoSistemaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo =  (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Anio
  	    try {
	  	transferObject.setAnio(enhancedObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
  	    try {
	  	transferObject.setAnulado(enhancedObject.getAnulado());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	transferObject.setIdTurno(enhancedObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatriculaUltima
  	    try {
	  	transferObject.setIdMatriculaUltima(enhancedObject.getIdMatriculaUltima());
	  	} catch (Throwable th ) {}
	  			
		  // Nota
    	List nota = null;
  	try
  	{
  	nota = enhancedObject.getNotas();
  	} catch (Throwable th) {}
	  	if (nota != null)
	  	{
		  	for(Iterator itera = nota.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced notaEnh = (gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Nota objTo = (gov.sir.core.negocio.modelo.Nota)cache.get(notaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Nota)NotaTransferCopier.deepCopy(notaEnh, cache);
		  		}
		  		transferObject.addNota(objTo);
		  		}
		  				  	}
		}
				
		  // Historial
    	List historial = null;
  	try
  	{
  	historial = enhancedObject.getHistorials();
  	} catch (Throwable th) {}
	  	if (historial != null)
	  	{
		  	for(Iterator itera = historial.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced historialEnh = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TurnoHistoria objTo = (gov.sir.core.negocio.modelo.TurnoHistoria)cache.get(historialEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TurnoHistoria)TurnoHistoriaTransferCopier.deepCopy(historialEnh, cache);
		  		}
		  		transferObject.addHistorial(objTo);
		  		}
		  				  	}
		}
				
		  // Circuloproceso
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced circuloprocesoEnh = null;
	  try {
	  	circuloprocesoEnh = enhancedObject.getCirculoproceso();
	  	} catch (Throwable th) {}
	  	if (circuloprocesoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CirculoProceso objTo =  (gov.sir.core.negocio.modelo.CirculoProceso)cache.get(circuloprocesoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CirculoProceso)CirculoProcesoTransferCopier.deepCopy(circuloprocesoEnh, cache);
	  		}
	  		transferObject.setCirculoproceso(objTo);
	  		}
	  			  		
	  	}
	  			
		  // UltimaRespuesta
  	    try {
	  	transferObject.setUltimaRespuesta(enhancedObject.getUltimaRespuesta());
	  	} catch (Throwable th ) {}
            
                  // turnoREL
  	    try {
	  	transferObject.setTurnoREL(enhancedObject.getTurnoREL());
	  	} catch (Throwable th ) {}
            
                // reasignacion
  	    try {
	  	transferObject.setReasignacion(enhancedObject.getReasignacion());
	  	} catch (Throwable th ) {}
            
                // relStat
  	    try {
	  	transferObject.setRelStat(enhancedObject.getRelStat());
	  	} catch (Throwable th ) {}
            
                // relEndpoint
  	    try {
	  	transferObject.setRelEndpoint(enhancedObject.getRelEndpoint());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdHistoria
  	    try {
	  	transferObject.setLastIdHistoria(enhancedObject.getLastIdHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFin
  		try {
		if (enhancedObject.getFechaFin() != null)
		{
	  	 transferObject.setFechaFin(new Date(enhancedObject.getFechaFin().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FechaInicio
  		try {
		if (enhancedObject.getFechaInicio() != null)
		{
	  	 transferObject.setFechaInicio(new Date(enhancedObject.getFechaInicio().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // LastIdNota
  	    try {
	  	transferObject.setLastIdNota(enhancedObject.getLastIdNota());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioDestino
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioDestinoEnh = null;
	  try {
	  	usuarioDestinoEnh = enhancedObject.getUsuarioDestino();
	  	} catch (Throwable th) {}
	  	if (usuarioDestinoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioDestinoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioDestinoEnh, cache);
	  		}
	  		transferObject.setUsuarioDestino(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ModoBloqueo
  	    try {
	  	transferObject.setModoBloqueo(enhancedObject.getModoBloqueo());
	  	} catch (Throwable th ) {}
	  			
		  // ConsistenciaWF
  	    try {
	  	transferObject.setConsistenciaWF(enhancedObject.getConsistenciaWF());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseRelacionActual
  	    try {
	  	transferObject.setIdFaseRelacionActual(enhancedObject.getIdFaseRelacionActual());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacionActual
  	    try {
	  	transferObject.setIdRelacionActual(enhancedObject.getIdRelacionActual());
	  	} catch (Throwable th ) {}
	  			
		  // ObservacionesAnulacion
  	    try {
	  	transferObject.setObservacionesAnulacion(enhancedObject.getObservacionesAnulacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioAnulacion
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioAnulacionEnh = null;
	  try {
	  	usuarioAnulacionEnh = enhancedObject.getUsuarioAnulacion();
	  	} catch (Throwable th) {}
	  	if (usuarioAnulacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioAnulacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioAnulacionEnh, cache);
	  		}
	  		transferObject.setUsuarioAnulacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Revocatoria
    try {
  	transferObject.setRevocatoria(enhancedObject.isRevocatoria());
  } catch (Throwable th ) {}
  			
		  // Nacional
    try {
  	transferObject.setNacional(enhancedObject.isNacional());
  } catch (Throwable th ) {}
  			
		  // ObservacionesHabilitar
  	    try {
	  	transferObject.setObservacionesHabilitar(enhancedObject.getObservacionesHabilitar());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Turno transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Error
      try {
  	enhancedObject.setError(transferObject.isError());
  	} catch (Throwable th ) {}
  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdFase
  	    try {
	  	enhancedObject.setIdFase(transferObject.getIdFase());
	  	} catch (Throwable th ) {}
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	enhancedObject.setIdWorkflow(transferObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.Solicitud solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.Solicitud)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudAntiguoSistema)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)SolicitudAntiguoSistemaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudAntiguoSistema)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificado)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)SolicitudCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCertificado)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)SolicitudCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudConsulta)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)SolicitudConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudConsulta)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCorreccion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)SolicitudCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCorreccion)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)SolicitudDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudDevolucion)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudFotocopia)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)SolicitudFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudFotocopia)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRegistro)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)SolicitudRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRegistro)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRepartoNotarial)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)SolicitudRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRepartoNotarial)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRestitucionReparto)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)SolicitudRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRestitucionReparto)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced)SolicitudTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Anio
  	    try {
	  	enhancedObject.setAnio(transferObject.getAnio());
	  	} catch (Throwable th ) {}
	  			
		  // Anulado
  	    try {
	  	enhancedObject.setAnulado(transferObject.getAnulado());
	  	} catch (Throwable th ) {}
	  			
		  // IdTurno
  	    try {
	  	enhancedObject.setIdTurno(transferObject.getIdTurno());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatriculaUltima
  	    try {
	  	enhancedObject.setIdMatriculaUltima(transferObject.getIdMatriculaUltima());
	  	} catch (Throwable th ) {}
	  			
		  // Nota
    	List nota = null;
  	try
  	{
  	nota = transferObject.getNotas();
  	} catch (Throwable th) { }
	  	if (nota != null)
	  	{
		  	for(Iterator itera = nota.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Nota notato = (gov.sir.core.negocio.modelo.Nota)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced)cache.get(notato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced)NotaTransferCopier.deepCopy(notato, cache);
		  		}
		  		enhancedObject.addNota(objEn);
		  				  		}
		  				  	}
		}
				
		  // Historial
    	List historial = null;
  	try
  	{
  	historial = transferObject.getHistorials();
  	} catch (Throwable th) { }
	  	if (historial != null)
	  	{
		  	for(Iterator itera = historial.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TurnoHistoria historialto = (gov.sir.core.negocio.modelo.TurnoHistoria)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)cache.get(historialto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)TurnoHistoriaTransferCopier.deepCopy(historialto, cache);
		  		}
		  		enhancedObject.addHistorial(objEn);
		  				  		}
		  				  	}
		}
				
		  // Circuloproceso
  	    gov.sir.core.negocio.modelo.CirculoProceso circuloproceso = null;
	  	try {
	  	circuloproceso = (gov.sir.core.negocio.modelo.CirculoProceso)transferObject.getCirculoproceso();
	  	} catch (Throwable th ) {}
	  	if (circuloproceso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced)cache.get(circuloproceso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced)CirculoProcesoTransferCopier.deepCopy(circuloproceso, cache);
	  		}
	  		enhancedObject.setCirculoproceso(objEn);
	  		}
	  			  		
	  	}
	  			
		  // UltimaRespuesta
  	    try {
	  	enhancedObject.setUltimaRespuesta(transferObject.getUltimaRespuesta());
	  	} catch (Throwable th ) {}
            
                  // turnoREL
  	    try {
	  	enhancedObject.setTurnoREL(transferObject.getTurnoREL());
	  	} catch (Throwable th ) {}
            
                 // reasignacion
  	    try {
	  	enhancedObject.setReasignacion(transferObject.getReasignacion());
	  	} catch (Throwable th ) {}
            
             // relStat
  	    try {
	  	enhancedObject.setRelStat(transferObject.getRelStat());
	  	} catch (Throwable th ) {}
            
             // relEndpoint
  	    try {
	  	enhancedObject.setRelEndpoint(transferObject.getRelEndpoint());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdHistoria
  	    try {
	  	enhancedObject.setLastIdHistoria(transferObject.getLastIdHistoria());
	  	} catch (Throwable th ) {}
	  			
		  // FechaFin
  	    try {
		if (transferObject.getFechaFin() != null)
		{
		  	enhancedObject.setFechaFin(new Date(transferObject.getFechaFin().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FechaInicio
  	    try {
		if (transferObject.getFechaInicio() != null)
		{
		  	enhancedObject.setFechaInicio(new Date(transferObject.getFechaInicio().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // LastIdNota
  	    try {
	  	enhancedObject.setLastIdNota(transferObject.getLastIdNota());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioDestino
  	    gov.sir.core.negocio.modelo.Usuario usuarioDestino = null;
	  	try {
	  	usuarioDestino = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioDestino();
	  	} catch (Throwable th ) {}
	  	if (usuarioDestino != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioDestino);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioDestino, cache);
	  		}
	  		enhancedObject.setUsuarioDestino(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ModoBloqueo
  	    try {
	  	enhancedObject.setModoBloqueo(transferObject.getModoBloqueo());
	  	} catch (Throwable th ) {}
	  			
		  // ConsistenciaWF
  	    try {
	  	enhancedObject.setConsistenciaWF(transferObject.getConsistenciaWF());
	  	} catch (Throwable th ) {}
	  			
		  // IdFaseRelacionActual
  	    try {
	  	enhancedObject.setIdFaseRelacionActual(transferObject.getIdFaseRelacionActual());
	  	} catch (Throwable th ) {}
	  			
		  // IdRelacionActual
  	    try {
	  	enhancedObject.setIdRelacionActual(transferObject.getIdRelacionActual());
	  	} catch (Throwable th ) {}
	  			
		  // ObservacionesAnulacion
  	    try {
	  	enhancedObject.setObservacionesAnulacion(transferObject.getObservacionesAnulacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioAnulacion
  	    gov.sir.core.negocio.modelo.Usuario usuarioAnulacion = null;
	  	try {
	  	usuarioAnulacion = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioAnulacion();
	  	} catch (Throwable th ) {}
	  	if (usuarioAnulacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioAnulacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioAnulacion, cache);
	  		}
	  		enhancedObject.setUsuarioAnulacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Revocatoria
      try {
  	enhancedObject.setRevocatoria(transferObject.isRevocatoria());
  	} catch (Throwable th ) {}
  			
		  // Nacional
      try {
  	enhancedObject.setNacional(transferObject.isNacional());
  	} catch (Throwable th ) {}
  			
		  // ObservacionesHabilitar
  	    try {
	  	enhancedObject.setObservacionesHabilitar(transferObject.getObservacionesHabilitar());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}