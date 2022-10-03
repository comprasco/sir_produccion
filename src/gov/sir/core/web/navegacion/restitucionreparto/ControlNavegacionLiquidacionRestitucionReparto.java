package gov.sir.core.web.navegacion.restitucionreparto;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.restitucionreparto.AWLiquidacionRestitucionReparto;
import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWLiquidacionRestitucionReparto.
 * @author ppabon
 */
public class ControlNavegacionLiquidacionRestitucionReparto implements ControlNavegacion {

	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";
	
	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String CONSULTAR_MINUTA_OK = "CONSULTAR_MINUTA_OK";

	/**
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *        Este m�todo lo que hace es la verificaci�n de los diferentes objectos que se encuentran en la sesi�n,
	 *        y de acuerdo a esa verificaci�n manda una respuesta para que sea procesada y asi poder tener una
	 *        navegaci�n acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acci�n enviada por la acci�n web no es v�lida");
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
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
