package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnMigracionIncremental;
import gov.sir.core.eventos.administracion.EvnRespMigracionIncremental;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CComplementacion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar todo lo relacionado con la migración incremental.
 * @author ppabon
 */
public class AWMigracionIncremental extends SoporteAccionWeb {

	//LISTA DE ACCIONES.
	
	/** Constante que identifica la acción de bloquear un folio porque hay un turno en 
	 * trámite en el sistema FOLIO que lo esta utilizando*/
	public static final String BLOQUEAR_TURNO_FOLIO = "BLOQUEAR_TURNO_FOLIO";
	
	/** Constante que identifica la acción de desbloquear un folio */
	public static final String DESBLOQUEAR_TURNO_FOLIO = "DESBLOQUEAR_TURNO_FOLIO";
	
	/** Constante que identifica la acción de regresar a la pantalla de 
	 * Bloqueo Manual de Folios por Turnos en Trámite*/
	public static final String REGRESAR_BLOQUEAR_TURNO_FOLIO = "REGRESAR_BLOQUEAR_TURNO_FOLIO";

	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS = "CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS";

	/** Consultar folio para actualizar complementaciones conflictivas.	 */
	public static final String ACTUALIZAR_COMPLEMENTACION_FOLIO = "ACTUALIZAR_COMPLEMENTACION_FOLIO";
	
	/** Consultar folio para actualizar complementaciones conflictivas.	 */
	public static final String ACTUALIZAR_COMPLEMENTACION_FROM_FOLIO = "ACTUALIZAR_COMPLEMENTACION_FROM_FOLIO";
	
	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una acción válida");
		} else if (accion.equals(AWMigracionIncremental.BLOQUEAR_TURNO_FOLIO)) {
			return bloquearTurnoFolio(request);
		} else if (accion.equals(AWMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO)) {
			return desbloquearTurnoFolio(request);
		}else if (accion.equals(AWMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS)) {
			return consultarComplementacionesConflictivas(request);
		}else if (accion.equals(AWMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO)) {
			return actualizarComplementacionesConflictivas(request);
		}else if (accion.equals(AWMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FROM_FOLIO)) {
			return actualizarComplementacionesConflictivasFromFolio(request);
		}
		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita bloquear un Folio porque hay un turno en el
	 * sistema FOLIO que lo esta utilizando.
	 * @param request
	 * @return evento <code>EvnMigracionIncremental</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnMigracionIncremental bloquearTurnoFolio(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 
		(org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);

		ValidacionParametrosException vpe = new ValidacionParametrosException();

		String turno = request.getParameter(CTurno.ID_TURNO);
		String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
		String idProceso = request.getParameter(CProceso.PROCESO_ID);
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);

		// SE VALIDA EL NÚMERO DE TURNO
		if (turno == null || turno.length() == 0) {
			vpe.addError("El turno ingresado es inválido ");
		} else {
			String[] partesId = turno.split("-");
			if (partesId == null || partesId.length != 2)
				vpe.addError("El turno ingresado es inválido: " + turno);
			else if (partesId[0].trim().equals("")
					|| partesId[1].trim().equals(""))
				vpe.addError("El turno ingresado es inválido: " + turno);
		}
		
		//SE VALIDA EL CIRCULO
		if ((idCirculo == null) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			vpe.addError("Seleccione un circulo.");
		}

		//SE VALIDA EL CIRCULO
		if ((idProceso == null) || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
			vpe.addError("Seleccione un proceso.");
		}
		
		// SE VALIDA LA MATRICULA INGRESADA
		try {
			int temp = Integer.parseInt(idMatricula);
			if (temp < 1) {
				vpe.addError("Número de folio inválido: " + idMatricula);
			}
		} catch (Exception nfe) {
			vpe.addError("Número de folio inválido: " + idMatricula);
		}
		
		if (vpe.getErrores().size()>0){
			session.setAttribute(CTurno.ID_TURNO,turno);
			session.setAttribute(CCirculo.ID_CIRCULO,idCirculo);
			session.setAttribute(CProceso.PROCESO_ID,idProceso);
			session.setAttribute(CFolio.ID_MATRICULA,idMatricula);			
			throw vpe;
		}

		String anio = "";
		String idTurno = "";

		anio = turno.substring(0, turno.indexOf("-"));
		idTurno = turno.substring((turno.indexOf("-") + 1), turno.length());

		Turno turnoTemp = new Turno();
		turnoTemp.setAnio(anio);
		turnoTemp.setIdTurno(idTurno);
		turnoTemp.setIdProceso(new Long(idProceso).longValue());
		turnoTemp.setIdCirculo(idCirculo);
		
		Circulo circulo = new Circulo();
		circulo.setIdCirculo(idCirculo);

		Proceso proceso = new Proceso();
		proceso.setIdProceso(new Long(idProceso).longValue());

		Folio folio = new Folio();
		folio.setIdMatricula(idCirculo + "-" + idMatricula);

		return new EvnMigracionIncremental(turnoTemp, circulo, proceso, folio,
				usuarioAuriga, usuarioSIR,
				EvnMigracionIncremental.BLOQUEAR_TURNO_FOLIO);
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita bloquear un Folio porque hay un turno en el
	 * sistema FOLIO que lo esta utilizando.
	 * @param request
	 * @return evento <code>EvnMigracionIncremental</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnMigracionIncremental desbloquearTurnoFolio(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 
		(org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);

		ValidacionParametrosException vpe = new ValidacionParametrosException();

		String turno = request.getParameter(CTurno.ID_TURNO);
		String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
		String idProceso = request.getParameter(CProceso.PROCESO_ID);
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);

		// SE VALIDA EL NÚMERO DE TURNO
		if (turno == null || turno.length() == 0) {
			vpe.addError("El turno ingresado es inválido ");
		} else {
			String[] partesId = turno.split("-");
			if (partesId == null || partesId.length != 2)
				vpe.addError("El turno ingresado es inválido: " + turno);
			else if (partesId[0].trim().equals("")
					|| partesId[1].trim().equals(""))
				vpe.addError("El turno ingresado es inválido: " + turno);
		}
		
		//SE VALIDA EL CIRCULO
		if ((idCirculo == null) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			vpe.addError("Seleccione un circulo.");
		}

		//SE VALIDA EL CIRCULO
		if ((idProceso == null) || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
			vpe.addError("Seleccione un proceso.");
		}
		
		// SE VALIDA LA MATRICULA INGRESADA
		try {
			int temp = Integer.parseInt(idMatricula);
			if (temp < 1) {
				vpe.addError("Número de folio inválido: " + idMatricula);
			}
		} catch (Exception nfe) {
			vpe.addError("Número de folio inválido: " + idMatricula);
		}
		
		if (vpe.getErrores().size()>0){
			session.setAttribute(CTurno.ID_TURNO,turno);
			session.setAttribute(CCirculo.ID_CIRCULO,idCirculo);
			session.setAttribute(CProceso.PROCESO_ID,idProceso);
			session.setAttribute(CFolio.ID_MATRICULA,idMatricula);			
			throw vpe;
		}

		String anio = "";
		String idTurno = "";

		anio = turno.substring(0, turno.indexOf("-"));
		idTurno = turno.substring((turno.indexOf("-") + 1), turno.length());

		Turno turnoTemp = new Turno();
		turnoTemp.setAnio(anio);
		turnoTemp.setIdTurno(idTurno);
		turnoTemp.setIdProceso(new Long(idProceso).longValue());
		turnoTemp.setIdCirculo(idCirculo);
		
		Circulo circulo = new Circulo();
		circulo.setIdCirculo(idCirculo);

		Proceso proceso = new Proceso();
		proceso.setIdProceso(new Long(idProceso).longValue());

		Folio folio = new Folio();
		folio.setIdMatricula(idCirculo + "-" + idMatricula);

		return new EvnMigracionIncremental(turnoTemp, circulo, proceso, folio,
				usuarioAuriga, usuarioSIR,
				EvnMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO);
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita consultar las complementaciones del folio en conflicto
	 * entre la del sistema FOLIO y el sistema SIR.
	 * @param request
	 * @return evento <code>EvnMigracionIncremental</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnMigracionIncremental consultarComplementacionesConflictivas(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 
		(org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Circulo circuloFolio = new Circulo();

		ValidacionParametrosException vpe = new ValidacionParametrosException();
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);

		//SE VALIDA EL CIRCULO
		if ((idCirculo == null) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			vpe.addError("Seleccione un circulo.");
		}
		//SE VALIDA LA MATRICULA
		try {
			int temp = Integer.parseInt(idMatricula);
			if (temp < 1) {
				vpe.addError("Número de folio inválido: " + idMatricula);
			}
		} catch (Exception nfe) {
			vpe.addError("Número de folio inválido: " + idMatricula);
		}

		if (vpe.getErrores().size()>0){
			session.setAttribute(CFolio.ID_MATRICULA,idMatricula);
			session.setAttribute(CCirculo.ID_CIRCULO,idCirculo);
			throw vpe;
		}

		Folio folio = new Folio();
		folio.setIdMatricula(idCirculo + "-" + idMatricula);
		
		circuloFolio.setIdCirculo(idCirculo);
		
		return new EvnMigracionIncremental(folio,usuarioAuriga, circuloFolio, usuarioSIR, 
				EvnMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS);
	}
	
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita actualizar la complementación del folio acorde a cómo se 
	 * modificó por el usuario.
	 * sistema FOLIO que lo esta utilizando.
	 * @param request
	 * @return evento <code>EvnMigracionIncremental</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnMigracionIncremental actualizarComplementacionesConflictivas(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 
		(org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);

		ValidacionParametrosException vpe = new ValidacionParametrosException();

		String idComplementacion = request.getParameter(CComplementacion.ID_COMPLEMENTACION);
		String complementacion = request.getParameter(CComplementacion.COMPLEMENTACION);
		String complementacionConflictiva = request.getParameter(CComplementacion.COMPLEMENTACION_CONFLICTIVA);

		// SE VALIDA EL NÚMERO DE TURNO
		if (idComplementacion == null || idComplementacion.length() == 0) {
			vpe.addError("El número de complementación ingresado es inválido.");
		}
		if (complementacion == null || complementacion.length() == 0) {
			vpe.addError("La complementación ingresada es inválida.");
		}
		
		if (vpe.getErrores().size()>0){
			session.setAttribute(CComplementacion.ID_COMPLEMENTACION,idComplementacion);
			session.setAttribute(CComplementacion.COMPLEMENTACION,complementacion);
			session.setAttribute(CComplementacion.COMPLEMENTACION_CONFLICTIVA,complementacionConflictiva);
			throw vpe;
		}

		Complementacion comp = new Complementacion();
		comp.setIdComplementacion(idComplementacion);
		comp.setComplementacion(complementacion);
		
		return new EvnMigracionIncremental(comp,usuarioAuriga, usuarioSIR,
				EvnMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO);
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita actualizar la complementación del folio a 
	 * a partir de la complementación del sistema FOLIO acorde a cómo se 
	 * modificó por el usuario.
	 * sistema FOLIO que lo esta utilizando.
	 * @param request
	 * @return evento <code>EvnMigracionIncremental</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnMigracionIncremental actualizarComplementacionesConflictivasFromFolio(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 
		(org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);

		ValidacionParametrosException vpe = new ValidacionParametrosException();

		String idComplementacion = request.getParameter(CComplementacion.ID_COMPLEMENTACION);
		String complementacion = request.getParameter(CComplementacion.COMPLEMENTACION);
		String complementacionConflictiva = request.getParameter(CComplementacion.COMPLEMENTACION_CONFLICTIVA);

		// SE VALIDA EL NÚMERO DE TURNO
		if (idComplementacion == null || idComplementacion.length() == 0) {
			vpe.addError("El número de complementación ingresado es inválido.");
		}
		if (complementacionConflictiva == null || complementacionConflictiva.length() == 0) {
			vpe.addError("La complementación ingresada es inválida.");
		}
		
		if (vpe.getErrores().size()>0){
			session.setAttribute(CComplementacion.ID_COMPLEMENTACION,idComplementacion);
			session.setAttribute(CComplementacion.COMPLEMENTACION,complementacion);
			session.setAttribute(CComplementacion.COMPLEMENTACION_CONFLICTIVA,complementacionConflictiva);
			throw vpe;
		}

		Complementacion comp = new Complementacion();
		comp.setIdComplementacion(idComplementacion);
		comp.setComplementacion(complementacionConflictiva);
		
		return new EvnMigracionIncremental(comp,usuarioAuriga, usuarioSIR,
				EvnMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO);
	}

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespMigracionIncremental respuesta = (EvnRespMigracionIncremental) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespMigracionIncremental.BLOQUEAR_TURNO_FOLIO)) {
				return;
			} else if (tipoEvento.equals(EvnRespMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO)) {
				return;
			} else if (tipoEvento.equals(EvnRespMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS)) {
				session.setAttribute(WebKeys.FOLIO, respuesta.getPayload());
			}
		}
	}

}
