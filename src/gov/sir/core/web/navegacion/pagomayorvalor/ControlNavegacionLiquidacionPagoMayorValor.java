package gov.sir.core.web.navegacion.pagomayorvalor;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;

/**
 * Clase que define la navegación para la Acción Web, AWLiquidacionPagoMayorValor.
 * @author ppabon
*/
public class ControlNavegacionLiquidacionPagoMayorValor implements ControlNavegacion {

	/** Constante que indica que se cargo exitosamente la liquidación	.*/
	public static final String CARGAR_OK = "CARGAR_OK";

	/** Constante que indica que no se cargo exitosamente la liquidación*/
	public static final String CARGAR_FAILED = "CARGAR_FAILED";

	/** Constante que indica que el proceso de liquidación fué exitoso */
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que el proceso de liquidación para el proceso de registro fue exitoso */
	public static final String LIQUIDACION_REGISTRO_OK = "LIQUIDACION_REGISTRO_OK";

	/** Constante que indica que el proceso de liquidación no fué exitoso */
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

	/** Constante que indica que el proceso de inserción de una matrícula a la solicitud no fue exitoso */
	public static final String AGREGAR_FAILED = "AGREGAR_FAILED";

	/** Constante que indica que el proceso de guardar la información a la sesión ha sido exitoso */
	public static final String PRESERVAR_OK = "PRESERVAR_OK";

	/** Constante que indica que el proceso de agregar un nuevo acto ha sido exitoso  */
	public static final String AGREGAR_ACTO_OK = "AGREGAR_ACTO_OK";

	/** Constante que indica que el proceso de eliminar un acto ha sido exitoso  */
	public static final String ELIMINAR_ACTO_OK = "ELIMINAR_ACTO_OK";
	
	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";

	/**
	 * Constructor de la clase.
	 */
	public ControlNavegacionLiquidacionPagoMayorValor() {
		super();
	}

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

		if (accion.equals(AWLiquidacionRegistro.PRESERVAR_INFO)) {
			return (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		} else if (accion.equals(AWLiquidacionRegistro.AGREGAR_ACTO)) {
			return (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		} else if (accion.equals(AWLiquidacionRegistro.ELIMINAR_ACTO)) {
			return (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		} else if (accion.equals(AWLiquidacionRegistro.LIQUIDAR)) {
			Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
			if (turno.getSolicitud() instanceof SolicitudCorreccion) {
				return LIQUIDACION_OK;
			} else {
				return LIQUIDACION_REGISTRO_OK;
			}
		} else if (accion.equals(AWLiquidacionRegistro.CARGAR_DERECHOS)) {
			return (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
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
