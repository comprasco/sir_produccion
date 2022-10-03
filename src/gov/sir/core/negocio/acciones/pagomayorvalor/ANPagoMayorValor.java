package gov.sir.core.negocio.acciones.pagomayorvalor;

import gov.sir.core.eventos.pagomayorvalor.EvnPagoMayorValor;
import gov.sir.core.eventos.pagomayorvalor.EvnRespPagoMayorValor;
import gov.sir.core.negocio.acciones.excepciones.ANPagoMayorValorException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import org.auriga.util.ExceptionPrinter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnPagoMayorValor</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespPagoMayorValor</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow del proceso de restitución de reparto notarial y llamar a los servicios 
 * que se requieren en cada fase del proceso.
 * @author ppabon
 */
public class ANPagoMayorValor extends SoporteAccionNegocio {

	/**
	  * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 *Constructor de la clase ANPagoMayorValor.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANPagoMayorValor() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod",e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio forseti",e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
	}

	/**
	* Recibe un evento de seguridad y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnPagoMayorValor</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespPagoMayorValor</CODE>
	* @see gov.sir.core.eventos.comun.EvnPagoMayorValor
	* @see gov.sir.core.eventos.comun.EvnPagoMayorValor
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnPagoMayorValor evento = (EvnPagoMayorValor) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnPagoMayorValor.CUSTODIA)) {
			return aprobarCustodia(evento);
		} else if (evento.getTipoEvento().equals(EvnPagoMayorValor.NOTIFICAR_CIUDADANO)) {
			return notificarCiudadano(evento);
		} else if (evento.getTipoEvento().equals(EvnPagoMayorValor.REGISTRAR_PAGO)) {
			return registrarPago(evento);
		} else if (evento.getTipoEvento().equals(EvnPagoMayorValor.NOTIFICAR_FUNCIONARIO)) {
			return notificarFuncionario(evento);
		}

		return null;
	}

	/**
	 * Permite avanzar el workflow de la fase de custodia en el proceso de pago por mayor valor
	 * Ocurre cuando el despacho registrador coloca en custodia los documentos que
	 * hacen parte del turno, al que se debió cobrar un valor adicional.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta aprobarCustodia(EvnPagoMayorValor evento) throws EventoException {
		EvnRespPagoMayorValor evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", determinarRespuesta(evento, true));
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		try {
			if ((turno.getSolicitud() instanceof SolicitudCertificado)||(turno.getSolicitud() instanceof SolicitudCorreccion)||(turno.getSolicitud() instanceof SolicitudRegistro)){
				if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_CUALQUIERA){
					hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla, evento.getUsuarioSIR());	
				}else if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_ELIMINANDO_PUSH){
					hermod.avanzarTurnoNuevoEliminandoPush(turno, fase, tabla, evento.getUsuarioSIR());
				}	
			}
			
		} catch (HermodException e) {
			throw new ANPagoMayorValorException("No se pudo avanzar Custodia Pago Mayor",e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANPagoMayorValor.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		evRespuesta = new EvnRespPagoMayorValor(evento.getUsuario(), turno, EvnRespPagoMayorValor.CUSTODIA);

		return evRespuesta;
	}

	/**
	 *  Permite avanzar el workflow de la fase de notificar al ciudadano en el proceso de pago por mayor valor.
	 * Ocurre cuando se le notifica al ciudadano que debe cancelar una suma adicional.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta notificarCiudadano(EvnPagoMayorValor evento) throws EventoException {
		EvnRespPagoMayorValor evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", determinarRespuesta(evento, true));
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		try {
			
		if ((turno.getSolicitud() instanceof SolicitudCertificado)||(turno.getSolicitud() instanceof SolicitudCorreccion)||(turno.getSolicitud() instanceof SolicitudRegistro)){
			if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_CUALQUIERA){
				hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla, evento.getUsuarioSIR());	
			}else if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_POP){
				hermod.avanzarTurnoNuevoPop(turno, fase, tabla, evento.getUsuarioSIR());
			}
			
		}
			
			
		} catch (HermodException e) {
			throw new ANPagoMayorValorException("No se pudo Notificar al Ciudadano",e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANPagoMayorValor.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		evRespuesta = new EvnRespPagoMayorValor(evento.getUsuario(), turno, EvnRespPagoMayorValor.CUSTODIA);

		return evRespuesta;
	}

	/**
	 *  Permite avanzar el workflow de la fase de registrar pago en el proceso de pago por mayor valor.
	 * Ocurre cuando el ciudadano esta pagando la cuantía adicional.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta registrarPago(EvnPagoMayorValor evento) throws EventoException {
		EvnRespPagoMayorValor evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", determinarRespuesta(evento, true));
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		try {
			if ((turno.getSolicitud() instanceof SolicitudCertificado)||(turno.getSolicitud() instanceof SolicitudCorreccion)||(turno.getSolicitud() instanceof SolicitudRegistro)){
				if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_CUALQUIERA){
					hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla, evento.getUsuarioSIR());	
				}else if (evento.getTipoAvance()==CAvanzarTurno.AVANZAR_POP){
					hermod.avanzarTurnoNuevoPop(turno, fase, tabla, evento.getUsuarioSIR());
				}
			}
		} catch (HermodException e) {
			throw new ANPagoMayorValorException("No se pudo registrar Pago Mayor Valor",e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANPagoMayorValor.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		evRespuesta = new EvnRespPagoMayorValor(evento.getUsuario(), turno, EvnRespPagoMayorValor.CUSTODIA);

		return evRespuesta;
	}

	/**
	 *  Permite avanzar el workflow de la fase de notificar el funcionario del proceso de pago por mayor valor.
	 * Ocurre cuando el ciudadano ya ha pagado la suma adicional que se le exigió.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta notificarFuncionario(EvnPagoMayorValor evento) throws EventoException {
		EvnRespPagoMayorValor evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", determinarRespuesta(evento, false));
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		try {
			if ((turno.getSolicitud() instanceof SolicitudCertificado)||(turno.getSolicitud() instanceof SolicitudCorreccion)||(turno.getSolicitud() instanceof SolicitudRegistro)){
				hermod.avanzarTurnoNuevoPop(turno, fase, tabla, evento.getUsuarioSIR());
				
			}
		} catch (HermodException e) {
			throw new ANPagoMayorValorException("No se pudo notificar al funcionario",e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANPagoMayorValor.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		evRespuesta = new EvnRespPagoMayorValor(evento.getUsuario(), turno, EvnRespPagoMayorValor.CUSTODIA);

		return evRespuesta;
	}
	
	/**
	 *  Permite determinar la respuesta para el avance, si el proceso de pago mayor valor fue llamado desde actuaciones administrativas.
	 * Si es así la respuesta siempre será ACTUACION_ADMINISTRATIVA, sino devuelve la respuesta requerida y que viene en el evento.
	 * @param evento
	 * @return
	 */
	private String determinarRespuesta(EvnPagoMayorValor evento, boolean verificarRespuesta) throws EventoException {
		
		//PARA LAS TRES PRIMERAS FASES SE VALIDA LA RESPUESTA, PARA VERIFICAR QUE SI LA RESPUESTA FUE
		//FRACASO O ELIMINACIÓN ÚNICAMENTE CAMBIE LA RESPUESTA A ACTUACION_ADMINISTRATIVA CUANDO EL TURNO 
		//PROVENIA DE ACTUACIONES ADMINISTRATIVAS
		//SI LA FASE ES LA ÚLTIMA NO VALIDA EL TIPO DE RESPUESTA Y CON CUALQUIERA
		//RETORNA LA RESPUESTA  ACTUACION_ADMINISTRATIVA PARA
		//QUE EL TURNO PROCIGA CON LA ACTUACIÓN.
		
		
		String WF_ACTUACION_ADMINISTRATIVA = "ACTUACION_ADMINISTRATIVA";
		String WF_FRACASO = "FRACASO";
		String WF_ELIMINACION = "ELIMINACION";
		 
		String rta = null;
		boolean esActuacion = false;

		Turno turno = evento.getTurno();		
		List turnosHistoria = turno.getHistorials();

		TurnoHistoria thUltimo = null;
		if(turnosHistoria!=null && turnosHistoria.size()>0){
			Iterator it = turnosHistoria.iterator();
			
			while(it.hasNext()){
				thUltimo = (TurnoHistoria)it.next();
	
				if(thUltimo.getFase()!=null&&thUltimo.getFase().equals(CFase.PMY_CUSTODIA)){
					if(thUltimo.getFaseAnterior()!=null && thUltimo.getFaseAnterior().equals(CFase.COR_ACT_ADMIN)){
						
						if(verificarRespuesta){
							if(evento.getRespuestaWF().equals(WF_FRACASO) || evento.getRespuestaWF().equals(WF_ELIMINACION)){
								esActuacion = true;
							}else{
								esActuacion = false;
							}
						}else{
							esActuacion = true;
						}
					}
					else{
						esActuacion = false;
					}
				
				}
			}
	
		}
		
		if(esActuacion){
			rta = WF_ACTUACION_ADMINISTRATIVA;
		}else{
			rta = evento.getRespuestaWF();
		}
		
		return rta;
	}	
}
