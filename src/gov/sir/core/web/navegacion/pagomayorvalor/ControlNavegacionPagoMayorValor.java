package gov.sir.core.web.navegacion.pagomayorvalor;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.pagomayorvalor.AWPagoMayorValor;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWPagoMayorValor.
 * @author ppabon
 */
public class ControlNavegacionPagoMayorValor implements ControlNavegacion {
	/** Constante que indica que se navegar� en la pantalla de Custodia en pago mayor valor**/
	public static final String PMY_CUSTODIA = "PMY_CUSTODIA";

	/** Constante que indica que se navegar� en la pantalla de notificar al ciudadano en pago mayor valor**/
	public static final String PMY_NOTIFICAR_CIUDADANO = "PMY_NOTIFICAR_CIUDADANO";

	/** Constante que indica que se navegar� en la pantalla de registrar pago en pago mayor valor**/
	public static final String PMY_REGISTRAR = "PMY_REGISTRAR";

	/** Constante que indica que se navegar� en la pantalla de notificar al funcionario en pago mayor valor**/
	public static final String PMY_NOTIFICAR_FUNCIONARIO = "PMY_NOTIFICAR_FUNCIONARIO";

	/** Constante que indica que se navegar� en la pantalla de Custodia exitosa en pago mayor valor**/
	public static final String CUSTODIA_OK = "CUSTODIA_OK";

	/** Constante que indica que se navegar� en la pantalla de notificar al ciudadano exitosa en pago mayor valor**/
	public static final String NOTIFICAR_CIUDADANO_OK = "NOTIFICAR_CIUDADANO_OK";

	/** Constante que indica que se navegar� en la pantalla de realizar el pago, en pago mayor valor**/
	public static final String CARGAR_PAGO_OK = "CARGAR_PAGO_OK";

	/** Constante que indica que se navegar� en la pantalla de registrar pago exitosa en pago mayor valor**/
	public static final String REGISTRAR_PAGO_OK = "REGISTRAR_PAGO_OK";

	/** Constante que indica que se navegar� en la pantalla de notificar al funcionario en pago mayor valor**/
	public static final String NOTIFICAR_FUNCIONARIO_OK = "NOTIFICAR_FUNCIONARIO_OK";

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

		if (accion.equals(AWPagoMayorValor.CUSTODIA_EXITO) || accion.equals(AWPagoMayorValor.CUSTODIA_FRACASO)) {
			return CUSTODIA_OK;
		} else if (accion.equals(AWPagoMayorValor.NOTIFICAR_CIUDADANO_EXITO) || accion.equals(AWPagoMayorValor.NOTIFICAR_CIUDADANO_FRACASO)) {
			return NOTIFICAR_CIUDADANO_OK;
		} else if (accion.equals(AWPagoMayorValor.CARGAR_PAGO)) {
			return CARGAR_PAGO_OK;
		} else if (accion.equals(AWPagoMayorValor.REGISTRAR_PAGO_EXITO) || accion.equals(AWPagoMayorValor.REGISTRAR_PAGO_FRACASO)) {
			return REGISTRAR_PAGO_OK;
		} else if (accion.equals(AWPagoMayorValor.NOTIFICAR_FUNCIONARIO_EXITO) || accion.equals(AWPagoMayorValor.NOTIFICAR_FUNCIONARIO_FRACASO)) {
			return NOTIFICAR_FUNCIONARIO_OK;
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
