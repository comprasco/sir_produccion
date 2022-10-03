package gov.sir.core.web.acciones.restitucionreparto;

import gov.sir.core.eventos.restitucionreparto.EvnRespRestitucionReparto;
import gov.sir.core.eventos.restitucionreparto.EvnRestitucionReparto;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCausalRestitucion;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionRestitucionRepartoException;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import javax.servlet.http.HttpServletRequest;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la liquidación de 
 * un proceso de restitución de reparto notarial de minutas. 
 * Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
 */
public class AWLiquidacionRestitucionReparto extends SoporteAccionWeb {
	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de una corrección
	 */
	public final static String LIQUIDAR = "LIQUIDAR";
	
	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de una corrección
	 */
	public final static String CONSULTAR_MINUTA = "CONSULTAR_MINUTA";


	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Constructor de la clase AWTurnoCorreccion
	 */
	public AWLiquidacionRestitucionReparto() {
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

		if (accion.equals(LIQUIDAR)) {
			return liquidar(request);
		} else if(accion.equals(CONSULTAR_MINUTA))
		{
			return consultarMinutas(request);
			
		}
		else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * Método que determina el valor que debe ser cancelado por la solicitud de restitución de 
	 * reparto.
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto liquidar(HttpServletRequest request) throws AccionWebException {
		//Se recibe la información que viene del formulario.
		String causal = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
		String turno = request.getParameter(CTurno.TURNO_ANTERIOR);
		String observacionesRestitucion = request.getParameter(CMinuta.OBSERVACIONES_RESTITUCION);
		String imprimible = request.getParameter(CMinuta.IMPRIMIBLE);
		

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRestitucionRepartoException exception = new ValidacionLiquidacionRestitucionRepartoException();

		if ((causal == null) || causal.equals("SIN_SELECCIONAR")) {
			exception.addError("Debe seleccionar el causal de restitución");
		}

		if ((turno.length() <= 0)) {
			exception.addError("Debe ingresar el turno anterior asociado");
		}
		
		if ((observacionesRestitucion == null) || observacionesRestitucion.equals("")) {
			exception.addError("Debe ingresar una observación");
		}

		//Se guardan los datos en la sesión y se lanzan los errores si existieron en la validación.
		if (causal != null) {
			request.getSession().setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, causal);
		}

		if (turno != null) {
			request.getSession().setAttribute(CTurno.TURNO_ANTERIOR, turno);
		}
		
		if (observacionesRestitucion != null) {
			request.getSession().setAttribute(CMinuta.OBSERVACIONES_RESTITUCION, observacionesRestitucion);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//Datos de la sesión.		
		Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);

		//Se llena la causal de restitución
		CausalRestitucion causalRestitucion = new CausalRestitucion();
		causalRestitucion.setIdCausalRestitucion(causal);

		//Se llena el turno anterior
		Turno turnoAnterior = new Turno();
		turnoAnterior.setIdWorkflow(turno.toUpperCase());

		//Se llena la Solicitud de Reparto Notarial
		SolicitudRestitucionReparto solicitudRestitucionReparto = new SolicitudRestitucionReparto();
		solicitudRestitucionReparto.setCausalRestitucion(causalRestitucion);
		solicitudRestitucionReparto.setTurnoAnterior(turnoAnterior);
		solicitudRestitucionReparto.setCirculo(circulo);
		solicitudRestitucionReparto.setProceso(proceso);
		solicitudRestitucionReparto.setTurno(null);
		solicitudRestitucionReparto.setUsuario(usuario);
		solicitudRestitucionReparto.setObservacionesRestitucion(observacionesRestitucion);

		String UID = request.getSession().getId();
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

		EvnRestitucionReparto evento = new EvnRestitucionReparto(usuarioAuriga, (Usuario)request.getSession().getAttribute(WebKeys.USUARIO), solicitudRestitucionReparto, causalRestitucion, EvnRestitucionReparto.CREAR_SOLICITUD, UID, rol, estacion, circulo);
		if (imprimible == null)
			evento.setImprimible(false);
		else
			evento.setImprimible(true);
		
		return evento;
	}
	
	/**
	 * Método que determina el valor que debe ser cancelado por la solicitud de restitución de 
	 * reparto.
	 * @param request
	 * @return
	 */
	private EvnRestitucionReparto consultarMinutas(HttpServletRequest request) throws AccionWebException {
		//Se recibe la información que viene del formulario.
		String causal = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
		String turno = request.getParameter(CTurno.TURNO_ANTERIOR);
		String observacionesRestitucion = request.getParameter(CMinuta.OBSERVACIONES_RESTITUCION);
		
		//Se guardan los datos en la sesión y se lanzan los errores si existieron en la validación.
		if (causal != null) {
			request.getSession().setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, causal);
		}

		if (turno != null) {
			request.getSession().setAttribute(CTurno.TURNO_ANTERIOR, turno);
		}
		
		if (observacionesRestitucion != null) {
			request.getSession().setAttribute(CMinuta.OBSERVACIONES_RESTITUCION, observacionesRestitucion);
		}
		

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRestitucionRepartoException exception = new ValidacionLiquidacionRestitucionRepartoException();

		if ((turno.length() <= 0)) {
			exception.addError("Debe ingresar el turno anterior asociado");
		}
		
		//Se guardan los datos en la sesión y se lanzan los errores si existieron en la validación.
		if (turno != null) 
		{
			request.getSession().setAttribute(CTurno.TURNO_ANTERIOR, turno);
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//Datos de la sesión.		
		Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);

		//Se llena el turno anterior
		Turno turnoAnterior = new Turno();
		turnoAnterior.setIdWorkflow(turno.toUpperCase());
		
		String UID = request.getSession().getId();
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
		
		//return new EvnRestitucionReparto(Usuario usuario, Turno turno, String tipoAccion)
		return new EvnRestitucionReparto(usuarioAuriga, turnoAnterior, EvnRestitucionReparto.CONSULTAR_MINUTA);
	}


	/*
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRestitucionReparto respuesta = (EvnRespRestitucionReparto) evento;

		if (respuesta != null) {
			if(respuesta.getTipoEvento().equals(EvnRespRestitucionReparto.TABLA_RESTITUCION_REPARTO))
			{
				request.getSession().setAttribute(WebKeys.TABLA_MINUTAS,respuesta.getPayload());
			} 
			if (respuesta.getTurno() != null) {
				request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
			}
		}
	}
}
