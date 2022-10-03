package gov.sir.core.web.navegacion.restitucionreparto;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.restitucionreparto.AWRestitucionReparto;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;

/**
 * Clase que define la navegación para la Acción Web, AWRestitucionReparto.
 * @author ppabon
 */
public class ControlNavegacionRestitucionReparto implements ControlNavegacion {

	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String CREACION_OK = "CREACION_OK";

	/** Constante que indica que se desea acepto el análisis de restitución*/
	public static final String ANALISIS_OK = "ANALISIS_OK";

	/** Constante que indica que se desea acepto el análisis de restitución*/
	public static final String ANALISIS_FAILED = "ANALISIS_FAILED";

	/** Constante que indica que se entrego exitosamente el resultado del reparto de minutas*/
	public static final String ENTREGA_OK = "ENTREGA_OK";

	/** Constante que indica que se entrego exitosamente el resultado del reparto de minutas. Pero el workflow que siguió fue el de fracaso.*/
	public static final String ENTREGA_FAILED = "ENTREGA_FAILED";
	
	/** Constante que indica que se consulto correctamenter el turno de analisis de restitucion.*/
	public static final String CONSULTAR_TURNOS_ANALISIS_OK = "CONSULTAR_TURNOS_ANALISIS_OK";
	
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

		if (accion.equals(AWRestitucionReparto.ANALISIS_CONFIRMAR)) {
			return ANALISIS_OK;
		} else if (accion.equals(AWRestitucionReparto.ANALISIS_NEGAR)) {
			return ANALISIS_FAILED;
		} else if (accion.equals(AWRestitucionReparto.NOTIFICAR_CIUDADANO_EXITO)) {
			return ENTREGA_OK;
		} else if (accion.equals(AWRestitucionReparto.NOTIFICAR_CIUDADANO_FRACASO)) {
			return ENTREGA_FAILED;
		} else if (accion.equals(AWRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION)) {
			return CONSULTAR_TURNOS_ANALISIS_OK;
		} else {
			return null;
		}

	}

	/**
	 * Método para terminar el control de navegación.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
