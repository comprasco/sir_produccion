package gov.sir.core.negocio.acciones.correccion;

import gov.sir.core.eventos.correccion.EvnActuacionAdministrativa;
import gov.sir.core.eventos.correccion.EvnRespActuacionAdministrativa;
import gov.sir.core.negocio.acciones.excepciones.BloqueoFoliosCorreccionHTException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import java.util.Hashtable;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author ppabon
 */
public class ANActuacionAdministrativa extends SoporteAccionNegocio {

	private ServiceLocator service = null;
	private PrintJobsInterface printJobs;
	private FenrirServiceInterface fenrir;
	private HermodServiceInterface hermod = null;
	private ForsetiServiceInterface forseti = null;


	/**
	 *
	 */
	public ANActuacionAdministrativa() throws EventoException {
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
		
		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
		}
		
		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio printJobs no encontrado");
		}
		
	}

	/**
	 *
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnActuacionAdministrativa evento = (EvnActuacionAdministrativa) e;

		if (evento.getTipoEvento().equals(EvnActuacionAdministrativa.DEVOLVER_A_CORRECCION)) {
			avanzarTurno(evento, CAvanzarTurno.AVANZAR_POP);
			return new EvnRespActuacionAdministrativa(evento.getTurno(),EvnRespActuacionAdministrativa.DEVOLVER_A_CORRECCION);
		} else if (evento.getTipoEvento().equals(EvnActuacionAdministrativa.APROBAR_ACTUACION)) {
			this.asignarPermisosTurno(evento);
			this.eliminarAvance(evento);			
			avanzarTurno(evento, CAvanzarTurno.AVANZAR_CUALQUIERA);			
			return new EvnRespActuacionAdministrativa(evento.getTurno(),EvnRespActuacionAdministrativa.APROBAR_ACTUACION);
		}else if (evento.getTipoEvento().equals(EvnActuacionAdministrativa.AGREGAR_NOTA)) {
			return agregarNotaActuacion(evento);
		}else if (evento.getTipoEvento().equals(EvnActuacionAdministrativa.EDITAR_NOTA)) {
			return editarNotaActuacion(evento);		
		}else if (evento.getTipoEvento().equals(EvnActuacionAdministrativa.ENVIAR_TURNO)) {
			return enviarTurnoOtrasDependencias(evento);			
		}
		
		return null;
	}


			
	
	/**
	 * Método que le asigna todos los permisos al turno de correcciones
	 * @param evento
	 */
	private void asignarPermisosTurno(EvnActuacionAdministrativa evento) throws EventoException {

		try {
			Turno turno = evento.getTurno();
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			
			//TFS 4340: SE DEBE VALIDAR QUE HAYA UNA NOTA INFORMATIVA
			try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			}
			catch(Throwable t)
			{
				  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			
			List permisos = hermod.getPermisosCorreccion();
			hermod.asignarPermisosCorreccion(tid, permisos);
			
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo asignar los permisos al turno", e);
		}catch (TurnoNoAvanzadoException e) {
			throw e;
		}catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
	}
	
	
	
	/**
	 * Método que elimina de la pila de usuarios que pueden modificar el turno, el último
	 * ya que cuando se avanza el turno de actuación administrativa a ejecución de la misma
	 * ya no se necesita guardar quién realizó el análisis del turno de correcciones.
	 * @param evento
	 */
	private void eliminarAvance(EvnActuacionAdministrativa evento) throws EventoException {

		try {
			Turno turno = evento.getTurno();
			hermod.deshacerAvancesPush(turno, 1);
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

	}


	/**
	 * Este metodo llama el servicio Hermod para poder hacer el avance del turno.
	 * @param evento EvnDevolucion
	 * @return EventoRespuesta
	 * @throws EventoException
	 */
	public void avanzarTurno(EvnActuacionAdministrativa evento, int tipoAvance) throws EventoException {
		Hashtable tabla = new Hashtable();

		//Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try {
			Hashtable tablaAvance = new Hashtable(2);
			tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
		} catch (Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioSIR().getUsername());

		try {
			if (tipoAvance==CAvanzarTurno.AVANZAR_CUALQUIERA){
				hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla, evento.getUsuarioSIR());	
			}else if (tipoAvance==CAvanzarTurno.AVANZAR_POP){
				hermod.avanzarTurnoNuevoPop(turno, fase, tabla, evento.getUsuarioSIR());
			}else if (tipoAvance==CAvanzarTurno.AVANZAR_PUSH){
				hermod.avanzarTurnoNuevoPush(turno, fase, tabla, evento.getUsuarioSIR());
			}
			
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	}


	/**
	 * Método que agrega una nota y estado al historial de la actuación administrativa
	 * @param evento
	 */
	private EventoRespuesta agregarNotaActuacion(EvnActuacionAdministrativa evento) throws EventoException {

		Turno turno = evento.getTurno(); 
		Turno turnoNuevo = null;
		
		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
				
			hermod.addNotaActuacionToTurno(tid, evento.getNotaActuacion());
			turnoNuevo = hermod.getTurno(tid);

		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		EvnRespActuacionAdministrativa eventoRta = new EvnRespActuacionAdministrativa(turno,EvnRespActuacionAdministrativa.AGREGAR_NOTAS);
		eventoRta.setTurno(turnoNuevo);
		return eventoRta;

	}
	
	
	/**
	 * Método que edita una nota y estado al historial de la actuación administrativa
	 * @param evento
	 */
	private EventoRespuesta editarNotaActuacion(EvnActuacionAdministrativa evento) throws EventoException {

		Turno turno = evento.getTurno(); 
		Turno turnoNuevo = null;
		
		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
				
			hermod.updateNotaActuacion(tid, evento.getNotaActuacion());
			turnoNuevo = hermod.getTurno(tid);

		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		EvnRespActuacionAdministrativa eventoRta = new EvnRespActuacionAdministrativa(turno,EvnRespActuacionAdministrativa.EDITAR_NOTAS);
		eventoRta.setTurno(turnoNuevo);
		return eventoRta;

	}	
	
	
	/**
	 * Método que permite delegar el turno a otros roles.
	 * Entre los cuálesse encuentran Antiguo sistema y mayor valor 
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta enviarTurnoOtrasDependencias(EvnActuacionAdministrativa evento) throws EventoException {
		EvnRespActuacionAdministrativa evRespuesta = new EvnRespActuacionAdministrativa(evento.getTurno(), EvnRespActuacionAdministrativa.ENVIAR_TURNO);

		Turno turno = evento.getTurno();
		
		//TFS 4340: SE DEBE VALIDAR QUE HAYA UNA NOTA INFORMATIVA
		try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
		
		bloquearFolios(evento);

		if (evento.getRespuestaWF().equals(EvnActuacionAdministrativa.WF_ANTIGUO_SISTEMA)) {
			
			try {
				Solicitud solicitud = evento.getTurno().getSolicitud();
				DatosAntiguoSistema datosAntiguoSsistema = evento.getDatosAntiguoSistema();
				hermod.setDatosAntiguoSistemaToSolicitud(solicitud, datosAntiguoSsistema);
			} catch (Throwable t) {
				throw new EventoException("Error agregando los datos de Antiguo Sistema", t);
			}
			
			avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);			
			
		}

		else if (evento.getRespuestaWF().equals(EvnActuacionAdministrativa.WF_MAYOR_VALOR)) {
			
			try {
				Solicitud solicitud = evento.getTurno().getSolicitud();
				Liquidacion liquidacion= evento.getLiquidacion();
				hermod.addLiquidacionToSolicitud(solicitud, liquidacion);
			} catch (Throwable t) {
				throw new EventoException("Error agregando la nueva Liquidación", t);
			}

			avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);

		}

		return evRespuesta;
	}


	/**
	 * @param evento
	 * @throws EventoException
	 */
	private void bloquearFolios(EvnActuacionAdministrativa evento) throws EventoException{
		Turno turno=evento.getTurno();
		Usuario usr=evento.getUsuarioSIR();
		LlaveBloqueo llaveBloqueo = new LlaveBloqueo();

		TurnoPk turnoID = new TurnoPk();
		turnoID.idCirculo = turno.getIdCirculo();
		turnoID.idProceso = turno.getIdProceso();
		turnoID.idTurno = turno.getIdTurno();
		turnoID.anio = turno.getAnio();

		try {
			forseti.validarPrincipioPrioridadCorreccion(turnoID);
			llaveBloqueo = forseti.delegarBloqueoFolios(turnoID, usr);
		} catch (ForsetiException e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
			throw new BloqueoFoliosCorreccionHTException(e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	}




	/**
	 * @param imprimible
	 * @param uid
	 */
	private void imprimir(ImprimibleBase imprimible, String uid) {

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();

		}

		Bundle bundle = new Bundle(imprimible);

		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * @param evento
	 */
	private void establecerPermisos(EvnActuacionAdministrativa evento) throws EventoException {
		List permisos = evento.getPermisos();
		Turno turno = evento.getTurno();
		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		try {
			hermod.asignarPermisosCorreccion(oid, permisos);
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANActuacionAdministrativa.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

	}	

}
