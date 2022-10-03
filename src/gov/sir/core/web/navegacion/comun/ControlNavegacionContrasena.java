package gov.sir.core.web.navegacion.comun;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWContrasena;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

public class ControlNavegacionContrasena implements ControlNavegacion{

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {

	}
	
	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		//HttpSession sesion = request.getSession();
		String accion = (String) request.getParameter(WebKeys.ACCION);
		if ((accion == null) || accion.equals("")){
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}else if (accion.equals(AWContrasena.CONSULTAR_MESA_AYUDA)){
			return AWContrasena.CONSULTAR_MESA_AYUDA;
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
