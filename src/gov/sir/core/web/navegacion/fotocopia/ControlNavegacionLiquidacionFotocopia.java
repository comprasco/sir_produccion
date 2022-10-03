package gov.sir.core.web.navegacion.fotocopia;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.fotocopia.AWLiquidacionFotocopia;
import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;

/**
 * Clase que define la navegación para la Acción Web, AWLiquidacionFotocopia.
 * @author dlopez
 */
public class ControlNavegacionLiquidacionFotocopia implements ControlNavegacion {

	/**
	* Constante que indica que se pudo crear la  solicitud de Fotocopias.
	*/
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";


	/**
	* Constante que indica que no se pudo crear la  solicitud de Fotocopias.
	*/
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";


	/**
	* Constante que indica que se debe procesar el pago
	*/
	public static final String PROCESAR_PAGO = "PROCESAR_PAGO";



	/**
	 * Método de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}



	/**
    * Este método verifica el estado de los diferentes objetos que se encuentran
    * en la sesión, y de acuerdo a esto envía una respuesta para que sea procesada
    * y asi poder tener una navegación acertada.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {

		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acción enviada por la acción web no es válida");
		}

		if (accion.equals(AWLiquidacionFotocopia.LIQUIDAR)) {
			return LIQUIDACION_OK;
		}
		else if (accion.equals(AWLiquidacionFotocopia.RELIQUIDAR)) {
			return AW_FotocopiasConstants.VERIFICAR_DOCUMENTOS_ASOCIADOS_OK;
			//      return PROCESAR_PAGO;
		}
		else if (accion.equals(AWLiquidacionFotocopia.RELIQUIDAR)) {
			return PROCESAR_PAGO;
		}
		else if( AW_FotocopiasConstants.DOCUMENTOSASOCIADOSADD_ACTION.equals( accion ) ) {
			// inicialmente no se verifica
			return AW_FotocopiasConstants.DOCUMENTOSASOCIADOSADD_PROCESSDONE;
		}
		else if( AW_FotocopiasConstants.DOCUMENTOSASOCIADOSDEL_ACTION.equals( accion ) ) {
			return AW_FotocopiasConstants.DOCUMENTOSASOCIADOSDEL_PROCESSDONE;
		}
		else if( AW_FotocopiasConstants.NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION.equals( accion ) ) {
			return EvnRespFotocopia.NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENTRESP_OK;
		}
		else {
			return LIQUIDACION_FAILED;
		}
		// NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENTRESP_OK
	}

	/**
	 * Método para terminar el control de navegación.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
