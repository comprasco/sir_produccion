package gov.sir.core.web.acciones.fotocopia;


import gov.sir.core.eventos.fotocopia.EvnEntregaFotocopia;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import javax.servlet.http.HttpServletRequest;

/**
 * @author dlopez
 * Acción Web asociada con el fotocopiado de documentos y con la
 * entrega de fotocopias al usuario final.
 */
public class AWEntregaFotocopia extends SoporteAccionWeb {


	/**
	 * Constante que identifica que se desea confirmar que el documento
	 * fue fotocopiado.
	 */
	public final static String FOTOCOPIAR_DOCUMENTO = "FOTOCOPIAR_DOCUMENTO";


	/**
	* Constante que identifica que se desea entregar el documento fotocopiado
	* al usuario.
	*/
	public final static String ENTREGAR_FOTOCOPIAS = "ENTREGAR_FOTOCOPIAS";


	/**
	* Constante que identifica que se desea cancelar la entrega o fotocopiado
	* de documentos.
	*/
	public final static String CANCELAR = "CANCELAR";


	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;


	/**
	 * Constructor de la clase AWEntregaFotocopia
	 */
	public AWEntregaFotocopia() {
		super();
	}


	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en
	* cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(FOTOCOPIAR_DOCUMENTO)) {
			return fotocopiarDocumento(request);
		}
		else if (accion.equals(ENTREGAR_FOTOCOPIAS)) {
			return entregarFotocopias (request);
		}
		else if (accion.equals(CANCELAR)) {
			return cancelarFotocopias(request);
		}
		else {
			throw new AccionInvalidaException("La accion " + accion + " no es válida.");
		}
	}



	/**
	 * @param request
	 * @return
	 * Se genera un evento de tipo EvnEntregaFotocopia, este método se utiliza para cuando se requiere
	 * avanzar el workflow por la opción de cancelar, o forzar la terminación de un turno.
	 */
	private EvnEntregaFotocopia cancelarFotocopias (HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		EvnEntregaFotocopia evn = new EvnEntregaFotocopia(usuarioAuriga,usuarioSIR , turno, fase, EvnEntregaFotocopia.NEGAR, EvnEntregaFotocopia.NEGAR );

		return evn;

		}

	/**
	 * @param request
	 * @return
	 * Se genera un evento de tipo EvnEntregaFotocopia, este método se utiliza para cuando se requiere
	 * avanzar el workflow por la opción de entregar fotocopias al usuario.
	 */
	private EvnEntregaFotocopia entregarFotocopias (HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		EvnEntregaFotocopia evn = new EvnEntregaFotocopia(usuarioAuriga, usuarioSIR, turno, fase, EvnEntregaFotocopia.ENTREGAR_FOTOCOPIAS, EvnEntregaFotocopia.CONFIRMAR );

		return evn;

		}


	/**
	 * @param request
	 * @return
	 * Se genera un evento de tipo EvnEntregaFotocopia, este método se utiliza para cuando se requiere
	 * avanzar el workflow por la opción de fotocopiar un documento.
	 */
	private EvnEntregaFotocopia fotocopiarDocumento (HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

		EvnEntregaFotocopia evn = new EvnEntregaFotocopia(usuarioAuriga, usuarioSIR, turno, fase, EvnEntregaFotocopia.FOTOCOPIAR_DOCUMENTO, EvnEntregaFotocopia.CONFIRMAR );

		return evn;

		}


	/*
	*
	*/
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {


		if (evento != null)
		{

			//Se obtuvo como respuesta un evento de fotocopias.
			if (evento instanceof EvnRespFotocopia)
			{

				EvnRespFotocopia auxEvento = (EvnRespFotocopia)evento;
				if (auxEvento.getTurno() != null) {
					request.getSession().setAttribute(WebKeys.TURNO, auxEvento.getTurno());
				}
			}




		}
	}

}

