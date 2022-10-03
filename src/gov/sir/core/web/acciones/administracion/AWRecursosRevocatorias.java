package gov.sir.core.web.acciones.administracion;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnRecursosRevocatorias;
import gov.sir.core.eventos.administracion.EvnRespRecursosRevocatorias;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ConsultarTurnoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
/**
 * Acción Web encargada de manejar todo lo relacionado con recursos y reposiciones.
 * La reposición ocurre cuando un turno de registro debe "Revivirse" luego de haberse terminado.
 * Esto ocurre cuando dicho turno fue devuelto y por recursos de apelación se decide
 * que debe realizarse la inscripción del mismo. En estos casos el turno finalizado debe quedar en la 
 * fase de calificación para que se registre su inscripción.
 * Existen dos pantallas para este fin una donde se bloquean los folios asociados al turno y 
 * otra donde se desbloquean (Rechazo) o se reanota (Se revive el turno).
 * 
 * @author ppabon
 */
public class AWRecursosRevocatorias extends SoporteAccionWeb {

	/** Constante que identifica la acción de consultar el turno al que se le desea bloquear sus folios*/
	public static final String SELECCIONAR_TURNO_BLOQUEO = "SELECCIONAR_TURNO_BLOQUEO";

	/** Constante que identifica la acción de agregar matrículas para ser bloqueadas*/
	public static final String AGREGAR_MATRICULA_BLOQUEO = "AGREGAR_MATRICULA_BLOQUEO";
	
	/** Constante que identifica la acción de eliminar matrículas que querían ser bloqueadas*/
	public static final String ELIMINAR_MATRICULA_BLOQUEO = "ELIMINAR_MATRICULA_BLOQUEO";
	
	/** Constante que identifica la acción de bloquear recursos*/
	public static final String BLOQUEAR_RECURSOS = "BLOQUEAR_RECURSOS";

	/** Constante que identifica la acción de consultar los turnos bloqueados*/
	public static final String CONSULTAR_TURNOS_BLOQUEADOS = "CONSULTAR_TURNOS_BLOQUEADOS";

	/** Constante que identifica la acción de reanotar los recursos y revocatorias directas*/
	public static final String REANOTAR_RECURSOS = "REANOTAR_RECURSOS";

	/** Constante que identifica la acción de rechazar los recursos y revocatorias directas*/
	public static final String RECHAZAR_RECURSOS = "RECHAZAR_RECURSOS";

	/** Constante que identifica la acción de regresar a la página principal de Pantallas Administrativas*/
	public static final String VOLVER = "VOLVER";

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWRecursosRevocatorias.SELECCIONAR_TURNO_BLOQUEO)) {
			return seleccionarTurno(request);
		} else if (accion.equals(AWRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO)) {
			return agregarMatricula(request);
		} else if (accion.equals(AWRecursosRevocatorias.ELIMINAR_MATRICULA_BLOQUEO)) {
			return desasociarMatricula(request);
		} else if (accion.equals(AWRecursosRevocatorias.BLOQUEAR_RECURSOS)) {
			return bloquearRecursos(request);
		} else if (accion.equals(AWRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS)) {
			return consultarTurnosBloqueados(request);
		} else if (accion.equals(AWRecursosRevocatorias.REANOTAR_RECURSOS)) {
			return reanotarRecursosRevocatorias(request);
		} else if (accion.equals(AWRecursosRevocatorias.RECHAZAR_RECURSOS)) {
			return rechazarRecursosRevocatorias(request);
		} else if (accion.equals(AWRecursosRevocatorias.VOLVER)) {
			return limpiarDatosSesion(request);
		}
		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita consultar el turno que desea reanotarse.
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code> con la información del turno.
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias seleccionarTurno(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		ConsultarTurnoException exception = new ConsultarTurnoException();

		String turnoWF = request.getParameter(CTurno.ID_TURNO);
		String justificacion = request.getParameter(CTurno.DESCRIPCION);

		if (turnoWF == null || turnoWF.equals("")) {
			exception.addError("Ingrese el turno");
		} else {
			if (turnoWF.indexOf("-") == -1) {
				exception.addError("El turnoes inválido");
			} else {
				String[] partes = turnoWF.split("-");
				if (partes.length != 4) {
					exception.addError("El turno es inválido");
				}
			}
		}

		request.getSession().setAttribute(CTurno.ID_TURNO,turnoWF);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Turno turno = new Turno();
		turno.setIdWorkflow(turnoWF);
		
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);		

		EvnRecursosRevocatorias evento = new EvnRecursosRevocatorias(usuarioAuriga, EvnRecursosRevocatorias.SELECCIONAR_TURNO_BLOQUEO, turno, usuarioSIR);
		evento.setCirculo(circulo);
		return evento;
	}
	
	/**
	 * Permite asociar una matrícula al turno.
	 * @param request La información del formulario
	 * @return Un evento EvnRecursosRevocatorias de tipo AGREGAR_MATRICULA_BLOQUEO
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias agregarMatricula(HttpServletRequest request)throws AccionWebException {

		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		String idCirculo =((Circulo)request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();

		Folio folio = new Folio();
		folio.setIdMatricula(idCirculo+"-"+idMatricula);
			
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);

                /**
                 * @autor AHERRENO
                 * REQUERIMIENTO 039_453
                 */
                ConsultarTurnoException exception = new ConsultarTurnoException();                
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(folio.getIdMatricula())){
                        exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
			throw exception;
                    }
                } catch (Exception ex) {
                    if(ex.getMessage() != null){
                        exception.addError(ex.getMessage());
                    }
                    throw exception;
                }
		return new EvnRecursosRevocatorias(usuario, EvnRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO, turno, folio, usuarioSir);
        
	}

	/**
	* Permite desasociar matrículas del turno.
	* @param request La información del formulario
	* @return Un evento EvnRecursosRevocatorias de tipo ELIMINAR_MATRICULA_BLOQUEO
	* @throws AccionWebException
	*/
	private EvnRecursosRevocatorias desasociarMatricula(HttpServletRequest request)throws AccionWebException {
        	
		List foliosAAsociar = (List)request.getSession().getAttribute(WebKeys.LISTA_FOLIOS);
		if(foliosAAsociar==null){
			foliosAAsociar = new ArrayList();
		}
				
		String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if(matriculasImp !=null ){
			for (int i = 0; i < matriculasImp.length; i++) {
				foliosAAsociar.remove(matriculasImp[i]);
			}
		}
		
		request.getSession().setAttribute(WebKeys.LISTA_FOLIOS, foliosAAsociar);		

		return null;
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para bloquear los folios de un turno (Bloquear recursos).
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code> con la información del turno y justificación del bloqueo.
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias bloquearRecursos(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		ConsultarTurnoException exception = new ConsultarTurnoException();

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
		List foliosAAdicionar = (List)session.getAttribute(WebKeys.LISTA_FOLIOS);
		String justificacion = request.getParameter(CTurno.DESCRIPCION);		

		if(turno ==null){
			exception.addError("No se encuentra el turno a restituir.");
		}
		if(justificacion==null || justificacion.equals("")){
			exception.addError("Debe ingresar la razón del bloqueo.");	
		}

		session.setAttribute(CTurno.DESCRIPCION, justificacion);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);		

		EvnRecursosRevocatorias evento = new EvnRecursosRevocatorias(usuarioAuriga, EvnRecursosRevocatorias.BLOQUEAR_RECURSOS, turno, foliosAAdicionar , justificacion, usuarioSIR);
		evento.setCirculo(circulo);
		return evento;
	}	

	/**
	 * Este método se encarga de enviar un evento para consultar los turnos que han sido bloqueados por la pantalla administrativa 
	 * de Bloquear Recursos y revocatorias directas
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code> 
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias consultarTurnosBloqueados(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

		EvnRecursosRevocatorias evento = new EvnRecursosRevocatorias(usuarioAuriga, EvnRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS);
		evento.setCirculo(circulo);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para bloquear los folios de un turno (Bloquear recursos).
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code> con la información del turno y justificación del bloqueo.
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias reanotarRecursosRevocatorias(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		ConsultarTurnoException exception = new ConsultarTurnoException();

		String turnoWF = request.getParameter(CTurno.ID_TURNO);
		String idEstacion = request.getParameter(CEstacion.ESTACION_ID);
		String justificacion = request.getParameter(CTurno.DESCRIPCION);
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : nueva variable para tener en cuenta estaciones de rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                String idEstacionTest = request.getParameter("EST_TESTAMENTO");

		if (turnoWF == null || turnoWF.equals("")) {
			exception.addError("Ingrese el turno");
		} else {
			if (turnoWF.indexOf("-") == -1) {
				exception.addError("El turnoes inválido");
			} else {
				String[] partes = turnoWF.split("-");
				if (partes.length != 4) {
					exception.addError("El turno es inválido");
				}
			}
		}
		
		/*
                * @author      : Julio Alcázar Rivas
                * @change      : Cambio en la validacion para tener en cuenta estaciones de rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                if((idEstacion==null || idEstacion.equals("")) && (idEstacionTest==null || idEstacionTest.equals(""))){
			exception.addError("Debe seleccionar el calificador (Turno de Calificacion o Testamento) a quién le desea asignar el turno.");
		}
		if(justificacion==null || justificacion.equals("")){
			exception.addError("Debe ingresar la razón de la reanotación del turno.");	
		}		

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Turno turno = new Turno();
		turno.setIdWorkflow(turnoWF);
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : Cambio en la validacion para tener en cuenta estaciones de rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
		Estacion estacion =  null;
                Estacion estacionTest = null;
                if(idEstacion==null || idEstacion.equals("")){
                   estacionTest =  new Estacion(idEstacionTest);
                }else{
                   estacion = new Estacion(idEstacion);
                }

		EvnRecursosRevocatorias evento = new EvnRecursosRevocatorias(usuarioAuriga, EvnRecursosRevocatorias.REANOTAR_RECURSOS, turno, estacion, justificacion, usuarioSIR);
                 /*
                * @author      : Julio Alcázar Rivas
                * @change      : se agrega al evento la estacion con rol testamento
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                evento.setEstacionTest(estacionTest);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para bloquear los folios de un turno (Bloquear recursos).
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code> con la información del turno y justificación del bloqueo.
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias rechazarRecursosRevocatorias(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		ConsultarTurnoException exception = new ConsultarTurnoException();

		String turnoWF = request.getParameter(CTurno.ID_TURNO);
		String justificacion = request.getParameter(CTurno.DESCRIPCION);		

		if (turnoWF == null || turnoWF.equals("")) {
			exception.addError("Ingrese el turno");
		} else {
			if (turnoWF.indexOf("-") == -1) {
				exception.addError("El turnoes inválido");
			} else {
				String[] partes = turnoWF.split("-");
				if (partes.length != 4) {
					exception.addError("El turno es inválido");
				}
			}
		}
		if(justificacion==null || justificacion.equals("")){
			exception.addError("Debe ingresar la razón de la reanotación del turno.");	
		}			

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Turno turno = new Turno();
		turno.setIdWorkflow(turnoWF);

		EvnRecursosRevocatorias evento = new EvnRecursosRevocatorias(usuarioAuriga, EvnRecursosRevocatorias.RECHAZAR_RECURSOS, turno, usuarioSIR);
		evento.setJustificacion(justificacion);
		return evento;
	}

	/**
	 * Este método se encarga de eliminar los objetos que se subieron a la sesión en la ejecución de bloquea o reanotación de recursos.
	 * @param request
	 * @return evento <code>EvnRecursosRevocatorias</code>.
	 * @throws AccionWebException
	 */
	private EvnRecursosRevocatorias limpiarDatosSesion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(WebKeys.LISTA_CALIFICADORES);
		session.removeAttribute(WebKeys.LISTA_TURNOS_REANOTAR);
		session.removeAttribute(CTurno.DESCRIPCION);
		
		return null;
	}

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRecursosRevocatorias respuesta = (EvnRespRecursosRevocatorias) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespRecursosRevocatorias.BLOQUEAR_RECURSOS)) {
				session.setAttribute(WebKeys.TURNO, respuesta.getPayload());
				return;
			} else if (tipoEvento.equals(EvnRespRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO)) {
				
				List foliosAAsociar = (List)session.getAttribute(WebKeys.LISTA_FOLIOS);
				if(foliosAAsociar==null){
					foliosAAsociar = new ArrayList();
				}
				
				Folio rta = (Folio)respuesta.getPayload();
				if(!foliosAAsociar.contains(rta.getIdMatricula())){
					foliosAAsociar.add(rta.getIdMatricula());
				}
				
				session.setAttribute(WebKeys.LISTA_FOLIOS, foliosAAsociar);
				return;
			}else if (tipoEvento.equals(EvnRespRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS)) {
				session.setAttribute(WebKeys.LISTA_TURNOS_REANOTAR, respuesta.getPayload());
				session.setAttribute(WebKeys.LISTA_CALIFICADORES, respuesta.getListaCalificadores());
                                /*
                                * @author      : Julio Alcázar Rivas
                                * @change      : Se agrega a la sesion los usuarios con rol testamentos
                                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                                */
                                session.setAttribute(WebKeys.LISTA_CAL_TESTAMENTOS, respuesta.getListaCalTestamentos());
				return;
			}else if (tipoEvento.equals(EvnRespRecursosRevocatorias.REANOTAR_RECURSOS) || tipoEvento.equals(EvnRespRecursosRevocatorias.RECHAZAR_RECURSOS)) {
				session.removeAttribute(WebKeys.LISTA_TURNOS_REANOTAR);
				session.removeAttribute(WebKeys.LISTA_CALIFICADORES);
				return;
			}
		}
	}

}
