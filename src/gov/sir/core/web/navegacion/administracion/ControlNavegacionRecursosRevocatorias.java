package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWRecursosRevocatorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWRecursosRevocatorias</CODE>
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionRecursosRevocatorias implements ControlNavegacion {

	/** Constante que identifica la acción de consultar el turno que desea reanotarse*/
	public static final String SELECCIONAR_TURNO_OK = "SELECCIONAR_TURNO_OK";	

	/** Constante que identifica la acción de bloquear recursos*/
	public static final String BLOQUEAR_RECURSOS_OK = "BLOQUEAR_RECURSOS_OK";	
	
	/** Constante que identifica la acción de consultar los turnos para reanotación*/
	public static final String CONSULTAR_TURNOS_REANOTAR_OK = "CONSULTAR_TURNOS_REANOTAR_OK";

	/** Constante que identifica la acción de reanotar los recursos y revocatorias directas*/
	public static final String REANOTAR_RECURSOS_OK = "REANOTAR_RECURSOS_OK";

	/** Constante que identifica la acción de rechazar los recursos y revocatorias directas*/
	public static final String RECHAZAR_RECURSOS_OK = "RECHAZAR_RECURSOS_OK";
	
	/** Constante que identifica la acción de regresar al menú de pantallas administrativas*/
	public static final String VOLVER = "VOLVER";	


	/**
	 *  Constructor por Default de <CODE>RecursosRevocatorias</CODE>
	 */
	public ControlNavegacionRecursosRevocatorias() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = ((String) request.getParameter(WebKeys.ACCION)).trim();

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		}
		if (accion.equals(AWRecursosRevocatorias.VOLVER)) {
			return ControlNavegacionRecursosRevocatorias.VOLVER;
		} else if (accion.equals(AWRecursosRevocatorias.SELECCIONAR_TURNO_BLOQUEO)) {
			return ControlNavegacionRecursosRevocatorias.SELECCIONAR_TURNO_OK;
		} else if (accion.equals(AWRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO)) {
			return ControlNavegacionRecursosRevocatorias.SELECCIONAR_TURNO_OK;
		} else if (accion.equals(AWRecursosRevocatorias.ELIMINAR_MATRICULA_BLOQUEO)) {
			return ControlNavegacionRecursosRevocatorias.SELECCIONAR_TURNO_OK;
		} else if (accion.equals(AWRecursosRevocatorias.BLOQUEAR_RECURSOS)) {
			return ControlNavegacionRecursosRevocatorias.BLOQUEAR_RECURSOS_OK;
		} else if (accion.equals(AWRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS)) {
			return ControlNavegacionRecursosRevocatorias.CONSULTAR_TURNOS_REANOTAR_OK;
		} else if (accion.equals(AWRecursosRevocatorias.REANOTAR_RECURSOS)) {
			return ControlNavegacionRecursosRevocatorias.REANOTAR_RECURSOS_OK;
		} else if (accion.equals(AWRecursosRevocatorias.RECHAZAR_RECURSOS)) {
			return ControlNavegacionRecursosRevocatorias.RECHAZAR_RECURSOS_OK;
		}
		 
		return null;
	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
