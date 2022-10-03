package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWConfrontacion;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWConfrontacion del proceso de correcciones.
 * @author ppabon
*/
public class ControlNavegacionConfrontacion implements ControlNavegacion {

	public static final String ASOCIAR_OK = "ASOCIAR_OK";
	public static final String ASOCIAR_FAILED = "ASOCIAR_FAILED";
	public static final String CONFIRMAR_OK = "CONFIRMAR_OK";
	public static final String ELIMINAR_OK = "ELIMINAR_OK";
	public ControlNavegacionConfrontacion() {
		super();
	}

	/**
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest request) {
	}

	/**
	 *        Este m�todo lo que hace es la verificaci�n de los diferentes objectos que se encuentran en la sesion,
	 *        y de acuerdo a esa verificaci�n manda una respuesta para que sea procesada y asi poder tener una
	 *        navegaci�n acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWConfrontacion.ASOCIAR_UNA_MATRICULA)) {
			return ASOCIAR_OK;
		} else if (accion.equals(AWConfrontacion.CONFIRMAR)) {
			return CONFIRMAR_OK;
		} else if (accion.equals(AWConfrontacion.ELIMINAR)) {
			return ELIMINAR_OK;
		} else if (accion.equals(AWConfrontacion.ASOCIAR_UN_RANGO)) {
			return ASOCIAR_OK;
		} else {
			return null;
		}
	}

	/**
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
