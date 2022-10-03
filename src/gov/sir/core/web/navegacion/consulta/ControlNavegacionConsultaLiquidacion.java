package gov.sir.core.web.navegacion.consulta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.constantes.CFase;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.consulta.AWLiquidacionConsulta;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para AWConsultaLiquidacion
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionConsultaLiquidacion implements ControlNavegacion {

	public static final String LIQUIDA_CONSULTA_SIMPLE_OK = "LIQUIDA_CONSULTA_SIMPLE_OK";
	public static final String LIQUIDA_CONSULTA_SIMPLE_SIMPLIFICADA_OK = "LIQUIDA_CONSULTA_SIMPLE_SIMPLIFICADA_OK";
	public static final String LIQUIDA_CONSULTA_COMPLEJA_OK = "LIQUIDA_CONSULTA_COMPLEJA_OK";
	public static final String LIQUIDA_CONSULTA_FOLIO_OK = "LIQUIDA_CONSULTA_FOLIO_OK";
	public static final String LIQUIDA_CONSULTA_EXENTA_OK = "LIQUIDA_CONSULTA_EXENTA_OK";
	public static final String LIQUIDA_CONSULTA_CONSTANCIA_OK = "LIQUIDA_CONSULTA_CONSTANCIA_OK";
	public static final String LIQUIDA_CONSULTA_FAILED = "LIQUIDA_CONSULTA_FAILED";


	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";
	
	
	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *   Este método lo que hace es la verificacion de los diferentes objetos que se encuentran el 
	 *   la sesion, y deacuerdo a esa verificacion manda una respuesta para que sea procesada 
	 *   y asi poder tener una navegacion acertada.
	 * 
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}

		if(sesion.getAttribute(WebKeys.TURNO) == null){
			if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE)) {
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_SIMPLE_OK;
				}
			}else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_CONSTANCIA)) {
				//sir-113
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_CONSTANCIA_OK;
				}
			}else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_COMPLEJA)) {
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_COMPLEJA_OK;
				}
			} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_EXENTA)) {
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_EXENTA_OK;
				}
			} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_OBSERVACION_FOLIO)) {
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FOLIO_OK;
				}
			}
		} else {
			if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA)) {
				if (sesion.getAttribute(WebKeys.LIQUIDACION) == null) {
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_FAILED;
				} else {
					Fase fase = new Fase();
    				fase.setID(CFase.CON_CONSULTA_SIMPLE);
    				request.getSession().setAttribute(WebKeys.FASE,fase);
					return ControlNavegacionConsultaLiquidacion.LIQUIDA_CONSULTA_SIMPLE_SIMPLIFICADA_OK;
				}
			}
			return SOLICITUD_OK;
		}
		return null;

	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}
}
