package gov.sir.core.web.acciones.correccion;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.eventos.correccion.EvnConfrontacion;
import gov.sir.core.eventos.correccion.EvnRespConfrontacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la fase de confrontación
 * en el proceso de correcciones. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
 */
public class AWConfrontacion extends SoporteAccionWeb {

	/**Constante que identifica que se quiere asociar una matricula*/
	public final static String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA";

	/**Constante que identifica que se quiere asociar un rango de matriculas*/
	public final static String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

	/**Constante que identifica que se quiere pasar el caso a reparto*/
	public final static String CONFIRMAR = "CONFIRMAR";
	
	/**Constante que identifica que se quiere terminar el caso a reparto*/
	public final static String NEGAR = "NEGAR";	

	/**Constante que identifica que se quiere eliminar una matrícula de la solicitud de correcciones*/
	public final static String ELIMINAR = "ELIMINAR";
	private String accion;

	public AWConfrontacion() {
		super();
	}

	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(request);
		} else if (accion.equals(ELIMINAR)) {
			return eliminarMatricula(request);
		} else if (accion.equals(CONFIRMAR)) {
			return confirmar(request);
		}else if (accion.equals(NEGAR)) {
			return negar(request);
		} else if (accion.equals(ASOCIAR_UN_RANGO)) {
			return asociarUnRango(request);
		} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * Puede asociar un rango de matriculas
	 * Garantiza que las matriculas quedan asociadas
	 * @param request con ID_MATRICULA_RL y ID_MATRICULA_RR
	 * @return Un evento de tipo ASOCIAR_UN_RANGO
	 */
	private Evento asociarUnRango(HttpServletRequest request) throws AccionWebException {

		String idMatRL = request.getParameter(CFolio.ID_MATRICULA_RL);
		String idMatRR = request.getParameter(CFolio.ID_MATRICULA_RR);

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged ;
                */
		EvnConfrontacion evento = new EvnConfrontacion(usuario, EvnConfrontacion.ASOCIAR_UN_RANGO, turno, idMatRL, idMatRR, usuarioNeg);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);

		return evento;
	}

	/**
	 * Puede asociar una matrícula
	 * Garantiza que la matrícula que quede asociada, puede ser afectada por el documento bajo radicación
	 * @param request La información del formulario
	 * @return Un evento confrontación de tipo ASOCIAR_UNA_MATRICULA
	 * @throws AccionWebException
	 */
	private EvnConfrontacion asociarUnaMatricula(HttpServletRequest request) throws AccionWebException {

		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		Folio folio = new Folio();
		folio.setIdMatricula(idMatricula);

		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                 /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged ;
                */
		EvnConfrontacion evento = new EvnConfrontacion(usuario, EvnConfrontacion.ASOCIAR_UNA_MATRICULA, turno, folio,usuarioNeg);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
		return evento;

	}

	/**
	* Puede asociar matrículas como un rango de matrículas
	* Garantiza que las matrículas que quedan asociadas son las que pueden
	* ser afectadas por el documento bajo radicación
	* @param request La información del formulario
	* @return Un evento confrontación de tipo ASOCIAR_RANGO_MATRICULAS
	* @throws AccionWebException
	*/
	private EvnConfrontacion eliminarMatricula(HttpServletRequest request) throws AccionWebException {

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		List folios = new ArrayList();
		String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if(matriculasImp !=null ){
			for (int i = 0; i < matriculasImp.length; i++) {
				Folio folio = new Folio();
				folio.setIdMatricula(matriculasImp[i]);
				folios.add(folio);
			}
		}
		if(folios.size()>0){
                               /**
                        * @Author Carlos Torres
                        * @Mantis 13176
                        * @Chaged ;
                        */
                        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                        EvnConfrontacion evento = new EvnConfrontacion(usuario, EvnConfrontacion.ELIMINAR_UNA_MATRICULA, turno, folios);
                        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSir.getUsername(),String.valueOf(usuarioSir.getIdUsuario()));
                        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                        evento.setInfoUsuario(infoUsuario);
			return evento;
		}else{
			return null;
		}

	
	}

	/**
	 * Confirma que todos los datos sean correctos, principalmente 
	 * que los folios asociados a la corrección esten asociados a la solicitud.
	 * @param request La información del formulario
	 * @return Un evento confrontación de tipo CONFIRMAR
	 * @throws AccionWebException
	 */
	private EvnConfrontacion confirmar(HttpServletRequest request) throws AccionWebException {

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		return new EvnConfrontacion(usuario, EvnConfrontacion.CONFIRMAR, turno, fase, EvnConfrontacion.EXITO);

	}

	/**
	 * Con éste método se termina la vida del turno en la fase de confrontación.
	 * @param request La información del formulario
	 * @return Un evento confrontación de tipo CONFIRMAR
	 * @throws AccionWebException
	 */
	private EvnConfrontacion negar(HttpServletRequest request) throws AccionWebException {

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		return new EvnConfrontacion(usuario, EvnConfrontacion.CONFIRMAR, turno, fase, EvnConfrontacion.FRACASO);

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespConfrontacion respuesta = (EvnRespConfrontacion) evento;

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespConfrontacion.TURNO)) {
				Turno turno = (Turno) respuesta.getPayload();
				request.getSession().setAttribute(WebKeys.TURNO, turno);
			}
		}
	}
}
