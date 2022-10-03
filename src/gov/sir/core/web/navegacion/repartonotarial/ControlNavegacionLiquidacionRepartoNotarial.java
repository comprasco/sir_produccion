package gov.sir.core.web.navegacion.repartonotarial;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial;
import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWLiquidacionRepartoNotarial.
 * @author ppabon
 */
public class ControlNavegacionLiquidacionRepartoNotarial implements ControlNavegacion {

	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";
	
	/** Constante que indica que se desea obtener la categoria*/
	public static final String OBTENER_CATEGORIA_OK = "OBTENER_CATEGORIA_OK";
	
	
	/** Constante que indica que se desea crear una solicitud de Reparto Notarial*/
	public static final String RECARGAR_LIQUIDACION = "RECARGAR_LIQUIDACION";	

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

		if ((accion.equals(AWLiquidacionRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES))||
				(accion.equals(AWLiquidacionRepartoNotarial.VALIDAR_INFORMACION))) {
			return RECARGAR_LIQUIDACION;
		}else if (accion.equals(AWLiquidacionRepartoNotarial.LIQUIDAR)) {
			return LIQUIDACION_OK;
		}else if (accion.equals(AWLiquidacionRepartoNotarial.OBTENER_CATEGORIA)) {
			return OBTENER_CATEGORIA_OK;
		}else if (accion.equals(AWLiquidacionRepartoNotarial.AGREGAR_ENTIDAD_PUBLICA)  ||
				  accion.equals(AWLiquidacionRepartoNotarial.ELIMINAR_ENTIDAD_PUBLICA)  ||
				  accion.equals(AWLiquidacionRepartoNotarial.AGREGAR_OTORGANTE_NATURAL)  ||
				  accion.equals(AWLiquidacionRepartoNotarial.ELIMINAR_OTORGANTE_NATURAL) ||
				  accion.equals(AWLiquidacionRepartoNotarial.PRESERVAR_INFO) ||
				  accion.equals(AWLiquidacionRepartoNotarial.AGREGAR_ACTO) ||
				  accion.equals(AWLiquidacionRepartoNotarial.ELIMINAR_ACCION_NOTARIAL)
				  ) {
			return RECARGAR_LIQUIDACION;
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
