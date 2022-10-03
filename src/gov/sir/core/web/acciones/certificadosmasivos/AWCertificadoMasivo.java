package gov.sir.core.web.acciones.certificadosmasivos;

import gov.sir.core.eventos.certificadosmasivos.EvnCertificadoMasivo;
import gov.sir.core.eventos.certificadosmasivos.EvnRespCertificadoMasivo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con
 * los certificados masivos
 * @author ppabon
 */
public class AWCertificadoMasivo extends SoporteAccionWeb {

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_CONFIRMAR = "ENTREGAR_CONFIRMAR";

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_NEGAR = "ENTREGAR_NEGAR";

	/**Esta constante define el tipo de respuesta CONFIRMAR*/
	public static final String WF_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define el tipo de respuesta NEGAR*/
	public static final String WF_NEGAR = "NEGAR";
	
	/**Si desea imprimir nota informativa en caso de matriculas no validas*/
	public static final String NOTA_INF_CERTIFICADOS_MASIVOS = "NOTA_INF_CERTIFICADOS_MASIVOS";

	/**Accion que llega de la interfaz*/
	private String accion;

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

		if (accion.equals(ENTREGAR_CONFIRMAR)) {
			return entregarConfirmar(request);
		} else if (accion.equals(ENTREGAR_NEGAR)) {
			return entregarNegar(request);
		} else {
			throw new AccionInvalidaException("La acción " + accion + " no es válida.");
		}
	}

	/**
	 * Método que sigue el workflow por la ruta de confirmación, luego de imprimir el certificado
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnCertificadoMasivo entregarConfirmar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String uid = request.getSession().getId();

		EvnCertificadoMasivo eventoCertificado = new EvnCertificadoMasivo(usuarioAuriga, EvnCertificadoMasivo.ENTREGAR_CONFIRMAR, turno, fase, EvnCertificadoMasivo.WF_CONFIRMAR, usuarioSIR);

		return eventoCertificado;
	}

	/**
	 * Método que sigue el workflow por la ruta de confirmación, luego de imprimir el certificado
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnCertificadoMasivo entregarNegar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String uid = request.getSession().getId();

		EvnCertificadoMasivo eventoCertificado = new EvnCertificadoMasivo(usuarioAuriga, EvnCertificadoMasivo.ENTREGAR_NEGAR, turno, fase, EvnCertificadoMasivo.WF_NEGAR, usuarioSIR);

		return eventoCertificado;
	}

	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		if (evento instanceof EvnRespCertificadoMasivo) {
			EvnRespCertificadoMasivo respuesta = (EvnRespCertificadoMasivo) evento;
		}

	}
}