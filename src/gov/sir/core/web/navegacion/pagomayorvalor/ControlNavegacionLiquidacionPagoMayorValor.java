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
 * Clase que define la navegaci�n para la Acci�n Web, AWLiquidacionPagoMayorValor.
 * @author ppabon
*/
public class ControlNavegacionLiquidacionPagoMayorValor implements ControlNavegacion {

	/** Constante que indica que se cargo exitosamente la liquidaci�n	.*/
	public static final String CARGAR_OK = "CARGAR_OK";

	/** Constante que indica que no se cargo exitosamente la liquidaci�n*/
	public static final String CARGAR_FAILED = "CARGAR_FAILED";

	/** Constante que indica que el proceso de liquidaci�n fu� exitoso */
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que el proceso de liquidaci�n para el proceso de registro fue exitoso */
	public static final String LIQUIDACION_REGISTRO_OK = "LIQUIDACION_REGISTRO_OK";

	/** Constante que indica que el proceso de liquidaci�n no fu� exitoso */
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

	/** Constante que indica que el proceso de inserci�n de una matr�cula a la solicitud no fue exitoso */
	public static final String AGREGAR_FAILED = "AGREGAR_FAILED";

	/** Constante que indica que el proceso de guardar la informaci�n a la sesi�n ha sido exitoso */
	public static final String PRESERVAR_OK = "PRESERVAR_OK";

	/** Constante que indica que el proceso de agregar un nuevo acto ha sido exitoso  */
	public static final String AGREGAR_ACTO_OK = "AGREGAR_ACTO_OK";

	/** Constante que indica que el proceso de eliminar un acto ha sido exitoso  */
	public static final String ELIMINAR_ACTO_OK = "ELIMINAR_ACTO_OK";
	
	/** Constante que indica que el proceso de solicitud fu� exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";

	/**
	 * Constructor de la clase.
	 */
	public ControlNavegacionLiquidacionPagoMayorValor() {
		super();
	}

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
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
