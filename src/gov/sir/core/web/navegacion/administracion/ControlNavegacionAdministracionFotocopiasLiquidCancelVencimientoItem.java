package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimientoItem;

/**
 * Control de Navegaci�n XX
 *
 *
 */
public class
ControlNavegacionAdministracionFotocopiasLiquidCancelVencimientoItem
implements ControlNavegacion {

    public static final String SOURCE_ACTION_VOLVER = AWAdministracionFotocopiasLiquidCancelVencimientoItem.ACCION_VOLVER;
    public static final String TARGET_ACTION_VOLVER = AWAdministracionFotocopiasLiquidCancelVencimientoItem.RESULTADO_VOLVER;

    public static final String SOURCE_ACTION_NEGAR = AWAdministracionFotocopiasLiquidCancelVencimientoItem.ACCION_NEGAR;
    public static final String TARGET_ACTION_NEGAR = AWAdministracionFotocopiasLiquidCancelVencimientoItem.RESULTADO_VOLVER;

	/**
	 * Prepara el procesamiento de la navegaci�n.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {

	}

	/**
	 * M�todo que procesa la siguiente acci�n de navegaci�n dentro del flujo de pantallas
	 *
	 * @param request
	 * @return nombre de la acci�n siguiente
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
 		String accion = (String) request.getParameter(WebKeys.ACCION);
		HttpSession session = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}

		if( SOURCE_ACTION_NEGAR.equals( accion )){
			return TARGET_ACTION_NEGAR;
		}
                if( SOURCE_ACTION_VOLVER.equals( accion )){
                        return TARGET_ACTION_VOLVER;
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
