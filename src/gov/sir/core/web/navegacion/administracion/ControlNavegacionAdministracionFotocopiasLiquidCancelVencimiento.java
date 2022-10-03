package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimiento;

/**
 * Control de Navegación XX
 *
 *
 */
public class
ControlNavegacionAdministracionFotocopiasLiquidCancelVencimiento
implements ControlNavegacion {

	public static final String SOURCE_ACTION_LISTAR = AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR;
        public static final String TARGET_ACTION_LISTAR = AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR;

        public static final String SOURCE_ACTION_VOLVER = AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VOLVER;
        public static final String TARGET_ACTION_VOLVER = AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VOLVER;

        public static final String SOURCE_ACTION_VERDETALLES = AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VERDETALLES;
        public static final String TARGET_ACTION_VERDETALLES = AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLES;


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
 		String accion = (String) request.getParameter(WebKeys.ACCION);
		HttpSession session = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}

		if( SOURCE_ACTION_LISTAR.equals( accion )){
			return TARGET_ACTION_LISTAR;
		}
                if( SOURCE_ACTION_VOLVER.equals( accion )){
                        return TARGET_ACTION_VOLVER;
                }
                if( SOURCE_ACTION_VERDETALLES.equals( accion )){
                        return TARGET_ACTION_VERDETALLES;
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
