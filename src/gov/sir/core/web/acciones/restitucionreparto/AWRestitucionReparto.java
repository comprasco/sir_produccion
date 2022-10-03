package gov.sir.core.web.acciones.restitucionreparto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gov.sir.core.eventos.restitucionreparto.EvnRespRestitucionReparto;
import gov.sir.core.eventos.restitucionreparto.EvnRestitucionReparto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRestitucionReparto;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAnalisisRestitucionException;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la restitución
 * de reparto notarial de minutas. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
 */
public class AWRestitucionReparto extends SoporteAccionWeb {

	/**
	 * Constante que identifica que el anális de restitución es aprobado.
	 */
	public final static String ANALISIS_CONFIRMAR = "ANALISIS_CONFIRMAR";

	/**
	 * Constante que identifica que el análisis de restitución es negado.
	 */
	public final static String ANALISIS_NEGAR = "ANALISIS_NEGAR";

	/**
	 * Constante que identifica que la notificación al ciudadano ha sido exitosa
	 */
	public final static String NOTIFICAR_CIUDADANO_EXITO = "NOTIFICAR_CIUDADANO_CONFIRMAR";

	/**
	 * Constante que identifica que la notificación al ciudadano ha fracasado
	 */
	public final static String NOTIFICAR_CIUDADANO_FRACASO = "NOTIFICAR_CIUDADANO_NEGAR";
	
	/** Constante que identifica la acción de consultar los turnos para analisis de restitucion*/
	public static final String CONSULTAR_TURNOS_ANALISIS_RESTITUCION = "CONSULTAR_TURNOS_ANALISIS_RESTITUCION";
	
	/** Constante que identifica la acción de consultar los turnos para analisis de restitucion*/
	public static final String AGRUPACION_RESTITUCION = "AGRUPACION_RESTITUCION";

	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Constructor de la clase AWRestitucionReparto
	 */
	public AWRestitucionReparto() {
		super();
	}

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		HttpSession session = request.getSession();
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

		if (accion.equals(ANALISIS_CONFIRMAR)) {
			return confirmarAnalisis(request);
		} else if (accion.equals(ANALISIS_NEGAR)) {
			return negarAnalisis(request);
		} else if (accion.equals(NOTIFICAR_CIUDADANO_EXITO)) {
			return notificarCiudadanoExito(request);
		} else if (accion.equals(NOTIFICAR_CIUDADANO_FRACASO)) {
			return notificarCiudadanoFracaso(request);
		} else if (accion.equals(CONSULTAR_TURNOS_ANALISIS_RESTITUCION)) {
			return consultarTurnosAnalisisRestitucion(request);
		} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * Método que aprueba el análisis que se hace para permitir a una notaria recibir un nuevo
	 * turno de reparto notarial, si las razones son válidas
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto confirmarAnalisis(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);		
		String UID = request.getSession().getId();		

		ValidacionParametrosAnalisisRestitucionException exception = new ValidacionParametrosAnalisisRestitucionException();
		
		String resolucion = request.getParameter(CSolicitudRestitucionReparto.RESOLUCION);
		String fechaResolucion = request.getParameter(CSolicitudRestitucionReparto.FECHA_RESOLUCION);
		String observacionesResolucion = request.getParameter(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION);
		
		String[] idsTurnos = request.getParameterValues("TurnosRestitucion");
		List turnoRestitucion = new ArrayList();
		
		for (int i = 0 ; i < idsTurnos.length; i++ ){
			turnoRestitucion.add((String)idsTurnos[i]);
		}
		
		if (idsTurnos == null) {
			exception.addError("Se debe selecionar al menos un turno.");
		}
		/*if(true){
			exception.addError("Se debe ingresar la resolución del análisis de restitución de reparto notarial.");
		}*/
		
		request.getSession().setAttribute(CSolicitudRestitucionReparto.RESOLUCION,resolucion);
		request.getSession().setAttribute(CSolicitudRestitucionReparto.FECHA_RESOLUCION,fechaResolucion);
		request.getSession().setAttribute(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION,observacionesResolucion);
		
		if(resolucion==null||resolucion.length()==0){
			exception.addError("Se debe ingresar la resolución del análisis de restitución de reparto notarial.");
		}
		
		
		Date fechaRes = null;
		if ((fechaResolucion == null) || (fechaResolucion.length() == 0)) {
			exception.addError(
				"Se debe ingresar la fecha de la resolución del análisis de restitución de reparto notarial.");
		} else {
			fechaRes = this.darFecha(fechaResolucion);

			if (fechaRes == null) {
				exception.addError("Ingrese una fecha válida de la resolución del análisis de reparto notarial.");
			}
		}		
		
		if(fechaResolucion==null||fechaResolucion.length()==0){
			exception.addError("Se debe ingresar la fecha de la resolución del análisis de restitución de reparto notarial.");
		}		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		SolicitudRestitucionReparto sol = new SolicitudRestitucionReparto();
		sol.setResolucion(resolucion);
		sol.setObservaciones(observacionesResolucion); 
		sol.setFechaResolucion(fechaRes);

		EvnRestitucionReparto evn = new EvnRestitucionReparto(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, sol, EvnRestitucionReparto.ANALISIS_RESTITUCION, EvnRestitucionReparto.CONFIRMAR);
		evn.setCirculo(circulo);		
		evn.setUID(UID);
		evn.setTurnoRestitucion(turnoRestitucion);
		return evn;
	}

	/**
	 * Método que niega la solicicitud de una notaria para recibir un nuevo
	 * turno de reparto notarial.
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto negarAnalisis(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String UID = request.getSession().getId();
		
		ValidacionParametrosAnalisisRestitucionException exception = new ValidacionParametrosAnalisisRestitucionException();
		
		String resolucion = request.getParameter(CSolicitudRestitucionReparto.RESOLUCION);
		String fechaResolucion = request.getParameter(CSolicitudRestitucionReparto.FECHA_RESOLUCION);
		String observacionesResolucion = request.getParameter(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION);
		
		String[] idsTurnos = request.getParameterValues("TurnosRestitucion");
		List turnoRestitucion = new ArrayList();
		
		for (int i = 0 ; i < idsTurnos.length; i++ ){
			 turnoRestitucion.add((String)idsTurnos[i]);
		}
		
		if (idsTurnos == null) {
			exception.addError("Se debe selecionar al menos un turno.");
		}
		/*if(true){
			exception.addError("Se debe ingresar la resolución del análisis de restitución de reparto notarial.");
		}*/
		
		request.getSession().setAttribute(CSolicitudRestitucionReparto.RESOLUCION,resolucion);
		request.getSession().setAttribute(CSolicitudRestitucionReparto.FECHA_RESOLUCION,fechaResolucion);
		request.getSession().setAttribute(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION,observacionesResolucion);
				
		if(resolucion==null||resolucion.length()==0){
			exception.addError("Se debe ingresar la resolución del análisis de restitución de reparto notarial.");
		}	
		Date fechaRes = null;

		if ((fechaResolucion == null) || (fechaResolucion.length() == 0)) {
			exception.addError(
				"Se debe ingresar la fecha de la resolución del análisis de restitución de reparto notarial.");
		} else {
			fechaRes = this.darFecha(fechaResolucion);

			if (fechaRes == null) {
				exception.addError("Ingrese una fecha válida de la resolución del análisis de reparto notarial.");
			}
		}
			
		if (exception.getErrores().size() > 0) {
			throw exception;
		}		
		Usuario usu = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		SolicitudRestitucionReparto sol = new SolicitudRestitucionReparto();
		sol.setResolucion(resolucion); 
		sol.setObservaciones(observacionesResolucion); 
		sol.setFechaResolucion(fechaRes);
				
		EvnRestitucionReparto evn = new EvnRestitucionReparto(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, sol, EvnRestitucionReparto.ANALISIS_RESTITUCION, EvnRestitucionReparto.NEGAR);
		evn.setCirculo(circulo);
		evn.setUID(UID);		
		evn.setUsuarioSIR(usu);
		evn.setTurnoRestitucion(turnoRestitucion);
		return evn;
	}
	
	/**
	 * @param fechaInterfaz
	 * @return
	 */
	private Date darFecha(String fechaInterfaz) {
		java.util.Date date = null;

		try {
			date = DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
				return null;
			}
		} catch (ParseException e) {
			return null;
		} catch (Throwable t) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");

		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) &&
					(calendar.get(Calendar.MONTH) == mes) &&
					(calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
				return calendar.getTime();
			}
		}

		return null;
	}

	/**
	 * Método que permite avanzar el workflow por éxito en la fase de notificar al ciudadano,
	 * en éste caso la notaria.
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto notificarCiudadanoExito(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Usuario usu=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		EvnRestitucionReparto evn = new EvnRestitucionReparto(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnRestitucionReparto.NOTIFICAR_CIUDADANO, EvnRestitucionReparto.CONFIRMAR);
		evn.setUsuarioSIR(usu);
		return evn;
	}

	/**
	 * Método que permite avanzar el workflow por fracaso en la fase de notificar al ciudadano,
	 * en éste caso la notaria.
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto notificarCiudadanoFracaso(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		EvnRestitucionReparto evn = new EvnRestitucionReparto(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnRestitucionReparto.NOTIFICAR_CIUDADANO, EvnRestitucionReparto.NEGAR);
		Usuario usu=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		evn.setUsuarioSIR(usu);
		return evn;
	}
	
	/**
	 * Este método se encarga de enviar un evento para consultar los turnos que estan para analisis de restitucion notarial 
	 * @param request
	 * @return evento <code>EvnRestitucionReparto</code> 
	 * @throws AccionWebException
	 */
	private EvnRestitucionReparto consultarTurnosAnalisisRestitucion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		List turnosARestituir =(List)session.getAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL);
		
		EvnRestitucionReparto evento = new EvnRestitucionReparto(usuarioAuriga, EvnRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION);
		evento.setCirculo(circulo);
		evento.setUsuarioSIR(usuarioSIR);
		evento.setListaturnos(turnosARestituir);
		return evento;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		
		HttpSession session = request.getSession();
		context = session.getServletContext();
		
		EvnRespRestitucionReparto respuesta = (EvnRespRestitucionReparto) evento;
		if (respuesta.getTipoEvento().equals(CONSULTAR_TURNOS_ANALISIS_RESTITUCION )){
			session.setAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL, respuesta.getPayload());
			session.setAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL_INFO, respuesta.getListaTurnos() );
			return;
		}

	}
}
