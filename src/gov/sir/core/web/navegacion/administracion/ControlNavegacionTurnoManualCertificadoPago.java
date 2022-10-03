/*
 * Created on 23-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ControlNavegacionTurnoManualCertificadoPago implements ControlNavegacion {
	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";    

	/** Constante que indica que el proceso de solicitud no fué exitoso */
	public static final String SOLICITUD_FAILED = "SOLICITUD_FAILED";

	/** Constante que indica que el proceso pago del turno de registro fue exitoso
	 * y que desea continuarse con la radicación del turno. */
	public static final String PROCESAR_REGISTRO_CONTINUAR = "PROCESAR_REGISTRO_CONTINUAR";

	/** Constante que indica que se quiere mostrar de nuevo la lista de aplicaciones */
	public static final String RELISTAR_APLICACIONES_ = "RELISTAR_APLICACIONES_";

	/** Constante que indica que se va a hacer la validacion del pago */
	public static final String VALIDAR_PAGO = "VALIDAR_PAGO";

	/** Constante que indica que se va a hacer el registro del pago */
	public static final String REGISTRAR_PAGO = "REGISTRAR_PAGO";

	/**
	 *
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 * Este método lo que hace es la verificacion de los diferentes objectos que se encuentran el la sesion,
	 * y deacuerdo a esa verificacion manda una respuesta para que sea procesada y asi poder tener una
	 * navegacion acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request)
		throws ControlNavegacionException {
		String accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}
        
		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
		String nombreProceso = proceso.getNombre();
		nombreProceso = nombreProceso.replaceAll(" ", "_");

		if (accion.equals(AWPago.PROCESAR)) {
			if (request.getSession().getAttribute(WebKeys.TURNO) != null) {
				return SOLICITUD_OK;
			}

			if (request.getSession().getAttribute(WebKeys.PAGO) != null) {
				return SOLICITUD_OK;
			}

			return SOLICITUD_FAILED;
		} else if (accion.equals(AWPago.PROCESAR_REGISTRO_CONTINUAR)) {
			Boolean esCajeroResgistro = (Boolean)request.getSession().getAttribute(WebKeys.ES_CAJERO_REGISTRO);
			if(esCajeroResgistro!=null && esCajeroResgistro.booleanValue()){
				return PROCESAR_REGISTRO_CONTINUAR;	
			}else{
				return SOLICITUD_OK;
			}        	
		} else if (accion.equals(AWPago.ADICIONAR_APLICACION) ||
				accion.equals(AWPago.ELIMINAR_APLICACION)) {
			return RELISTAR_APLICACIONES_ + nombreProceso;
		} else if (accion.equals(AWPago.VALIDAR) ||
				accion.equals(AWPago.ELIMINAR_APLICACION_VALIDACION) ||
				accion.equals(AWPago.ADICIONAR_APLICACION_VALIDACION)) {
			if (((Pago) request.getSession().getAttribute(WebKeys.PAGO)).getAplicacionPagos()
					 .isEmpty()) {
				return RELISTAR_APLICACIONES_ + proceso.getNombre();
			}

			return VALIDAR_PAGO;
		}

		return null;
	}

	/**
	 *
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
