package gov.sir.core.web.navegacion.administracion;

import java.util.Hashtable;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWReasignacionTurnos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWReasignacionTurnos</CODE>
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionReasignacionTurnos implements ControlNavegacion {

	/** Constante que identifica la acción de cargar los usuarios del circulo*/
	public static final String CARGAR_USUARIOS_OK = "CARGAR_USUARIOS_OK";

	/** Constante que identifica la acción de cargar los roles que tiene un usuario*/
	public static final String CARGAR_ROLES_OK = "CARGAR_ROLES_OK";
	
	/** Constante que identifica la acción de cargar los turnos que tiene el usuario para un rol determinado*/
	public static final String CARGAR_TURNOS_OK = "CARGAR_TURNOS_OK";
	
	/** Constante que identifica la acción de cargar las estaciones que tiene un usuario en rol determinado*/
	public static final String CARGAR_ESTACIONES_OK = "CARGAR_ESTACIONES_OK";	
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario*/
	public static final String REASIGNAR_TURNOS_OK = "REASIGNAR_TURNOS_OK";
	
	/** Constante que identifica la acción de reasignar turnos, pero cuando se realizó no se reasignaron todos los turnos por problemas de bloqueos.*/
	public static final String REASIGNAR_TURNOS_NO_COMPLETOS = "REASIGNAR_TURNOS_NO_COMPLETOS";

	/** Constante que identifica la acción de regresar al menú de pantallas administrativas*/
	public static final String VOLVER = "VOLVER";	


	/**
	 *  Constructor por Default de <CODE>RecursosRevocatorias</CODE>
	 */
	public ControlNavegacionReasignacionTurnos() {
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
		
		if (accion.equals(AWReasignacionTurnos.VOLVER)) {
			return ControlNavegacionReasignacionTurnos.VOLVER;
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_USUARIOS)) {
			return ControlNavegacionReasignacionTurnos.CARGAR_USUARIOS_OK;
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_ROLES)) {
			return ControlNavegacionReasignacionTurnos.CARGAR_ROLES_OK;
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_TURNOS)) {
			return ControlNavegacionReasignacionTurnos.CARGAR_TURNOS_OK;
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_ESTACIONES)) {
			return ControlNavegacionReasignacionTurnos.CARGAR_ESTACIONES_OK;
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS)) {
			Hashtable turnosNoReasignados = (Hashtable)sesion.getAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS);
			if(turnosNoReasignados != null && !turnosNoReasignados.isEmpty()){
				return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_NO_COMPLETOS;	
			}
			return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_OK;
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO)) {
			Hashtable turnosNoReasignados = (Hashtable)sesion.getAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS);
			if(turnosNoReasignados != null && !turnosNoReasignados.isEmpty()){
				return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_NO_COMPLETOS;	
			}
			return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_OK;
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES)) {
			Hashtable turnosNoReasignados = (Hashtable)sesion.getAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS);
			if(turnosNoReasignados != null && !turnosNoReasignados.isEmpty()){
				return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_NO_COMPLETOS;	
			}
			return ControlNavegacionReasignacionTurnos.REASIGNAR_TURNOS_OK;
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
