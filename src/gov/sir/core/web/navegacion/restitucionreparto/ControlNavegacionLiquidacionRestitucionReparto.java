package gov.sir.core.web.navegacion.restitucionreparto;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.restitucionreparto.AWLiquidacionRestitucionReparto;
import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegación para la Acción Web, AWLiquidacionRestitucionReparto.
 * @author ppabon
 */
public class ControlNavegacionLiquidacionRestitucionReparto implements ControlNavegacion {

	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";
	
	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String CONSULTAR_MINUTA_OK = "CONSULTAR_MINUTA_OK";

	/**
	 * Método de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *        Este método lo que hace es la verificación de los diferentes objectos que se encuentran en la sesión,
	 *        y de acuerdo a esa verificación manda una respuesta para que sea procesada y asi poder tener una
	 *        navegación acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acción enviada por la acción web no es válida");
		}

		if (accion.equals(AWLiquidacionRestitucionReparto.LIQUIDAR)) {
			return LIQUIDACION_OK;
		} 
		if (accion.equals(AWLiquidacionRestitucionReparto.CONSULTAR_MINUTA)) {
			return CONSULTAR_MINUTA_OK;
		}
		else {
			return null;
		}
	}

	/**
	 * Método para terminar el control de navegación.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
