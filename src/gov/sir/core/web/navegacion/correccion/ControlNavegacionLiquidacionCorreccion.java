package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWLiquidacionCorreccion;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;

/**
 * Clase que define la navegación para la Acción Web, AWLiquidacionCorreccion.
 * @author ppabon, jvelez
 */
public class ControlNavegacionLiquidacionCorreccion implements ControlNavegacion {

   public static String PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK
       = "PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK";



	/** Constante que indica que el proceso de liquidación fué exitoso */
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que el proceso de liquidación no fué exitoso */
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

	/** Constante que indica que el proceso de inserción de una matrícula a la solicitud fue exitoso */
	public static final String AGREGAR_OK = "AGREGAR_OK";

	/** Constante que indica que el proceso de inserción de una matrícula a la solicitud no fue exitoso */
	public static final String AGREGAR_FAILED = "AGREGAR_FAILED";

	/** Constante que indica que el proceso de eliminación de una matrícula de la solicitud fue exitoso */
	public static final String ELIMINAR_OK = "ELIMINAR_OK";

	/** Constante que indica que el proceso de eliminación de una matrícula no fue exitoso */
	public static final String ELIMINAR_FAILED = "ELIMINAR_FAILED";

	/**
	 * Constructor de la clase.
	 */
	public ControlNavegacionLiquidacionCorreccion() {
		super();
	}

	/**
	 * Método de inicio
	 */
	public void doStart(HttpServletRequest request) {
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


                if( AWLiquidacionCorreccion.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION.equals( accion )  ) {
                   return PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK;
                }
                else if (accion.equals(AWLiquidacionCorreccion.LIQUIDAR)) {
                    Liquidacion liquidacion = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);
                    return LIQUIDACION_OK;
		}
                else if (accion.equals(AWLiquidacionCorreccion.AGREGAR) || accion.equals(AWLiquidacionCorreccion.AGREGAR_MIG_INC) || accion.equals(AWLiquidacionCorreccion.AGREGAR_RANGO)) {
			return AGREGAR_OK;
		}
                else if (accion.equals(AWLiquidacionCorreccion.ELIMINAR) || accion.equals(AWLiquidacionCorreccion.ELIMINAR_MIG_INC)) {
			return ELIMINAR_OK;
		}
                      /*
        * @author : CTORRES
        * @change : Se agregan condiciones para las acciones de correccion de testamento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                else if(accion.equals(AWLiquidacionCorreccion.VALIDAR_TURNO_TESTAMENTO))
                {
                    	return AGREGAR_OK;
                }
                else if(accion.equals(AWLiquidacionCorreccion.ELIMINAR_TURNO_TESTAMENTO))
                {
                    	return ELIMINAR_OK;
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
