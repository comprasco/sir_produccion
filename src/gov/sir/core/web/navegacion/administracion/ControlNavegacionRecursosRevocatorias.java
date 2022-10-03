package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWRecursosRevocatorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegaci�n para <CODE>AWRecursosRevocatorias</CODE>
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionRecursosRevocatorias implements ControlNavegacion {

	/** Constante que identifica la acci�n de consultar el turno que desea reanotarse*/
	public static final String SELECCIONAR_TURNO_OK = "SELECCIONAR_TURNO_OK";	

	/** Constante que identifica la acci�n de bloquear recursos*/
	public static final String BLOQUEAR_RECURSOS_OK = "BLOQUEAR_RECURSOS_OK";	
	
	/** Constante que identifica la acci�n de consultar los turnos para reanotaci�n*/
	public static final String CONSULTAR_TURNOS_REANOTAR_OK = "CONSULTAR_TURNOS_REANOTAR_OK";

	/** Constante que identifica la acci�n de reanotar los recursos y revocatorias directas*/
	public static final String REANOTAR_RECURSOS_OK = "REANOTAR_RECURSOS_OK";

	/** Constante que identifica la acci�n de rechazar los recursos y revocatorias directas*/
	public static final String RECHAZAR_RECURSOS_OK = "RECHAZAR_RECURSOS_OK";
	
	/** Constante que identifica la acci�n de regresar al men� de pantallas administrativas*/
	public static final String VOLVER = "VOLVER";	


	/**
	 *  Constructor por Default de <CODE>RecursosRevocatorias</CODE>
	 */
	public ControlNavegacionRecursosRevocatorias() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegaci�n.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * M�todo que procesa la siguiente acci�n de navegaci�n dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acci�n siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = ((String) request.getParameter(WebKeys.ACCION)).trim();

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es v�lida");
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
	 * Finalizaci�n de la navegaci�n
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
