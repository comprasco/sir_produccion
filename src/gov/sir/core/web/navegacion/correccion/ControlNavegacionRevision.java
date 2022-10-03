package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWRevision;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants;

/**
 * Clase que define la navegación para la Acción Web, AWRevision del proceso de correcciones.
 * @author ppabon, jvelez
 */
public class ControlNavegacionRevision implements ControlNavegacion {
    public static final String RECARGAR = AwCorrecciones_Constants.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION;
    public static final String DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK = "DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK";

	public static final String ACT_RECARGAR = AwCorrecciones_Constants.ACT_PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION;
	public static final String ACT_DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK = "ACT_DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK";



    public static final String ASOCIAR_OK = "ASOCIAR_OK";
    public static final String ASOCIAR_FAILED = "ASOCIAR_FAILED";
    public static final String CONFIRMAR_OK = "CONFIRMAR_OK";
    public static final String ELIMINAR_OK = "ELIMINAR_OK";
    public static final String MAYOR_VALOR= "MAYOR_VALOR";
    

    /** Constante que indica que se negó satisfactoriamente una solicitud de corrección*/
    public static final String NEGAR_OK = "NEGAR_OK";

    /** Constante que indica que no se negó satisfactoriamente una solicitud de corrección*/
    public static final String NEGAR_FAILED = "NEGAR_FAILED";

    public ControlNavegacionRevision() {
        super();
    }

    /**
     * Método de inicio
     */
    public void doStart(HttpServletRequest request) {
    }

    /**
     *        Este método lo que hace es la verificación de los diferentes objectos que se encuentran en la sesion,
     *        y de acuerdo a esa verificación manda una respuesta para que sea procesada y asi poder tener una
     *        navegación acertada.
     */
    public String procesarNavegacion(HttpServletRequest request) throws
            ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                    "La accion enviada por la accion web no es valida");
        }
        if (accion.equals(AWRevision.ASOCIAR_UNA_MATRICULA)) {
            return ASOCIAR_OK;
        } else if (accion.equals(AWRevision.CONFIRMAR)) {
            return CONFIRMAR_OK;
        } else if (accion.equals(AWRevision.CORRECCION_SIMPLE)) {
            return CONFIRMAR_OK;
        } else if (accion.equals(AWRevision.REDIRECCIONAR_CASO)) {
            if (request.getParameter(AWRevision.REDIRECCIONAR_CASO).equals(AWRevision.MAYOR_VALOR)){
                return MAYOR_VALOR;
            }
            return CONFIRMAR_OK;
		} else if (accion.equals(AWRevision.REDIRECCIONAR_CASO_ACT)) {
			if (request.getParameter(AWRevision.REDIRECCIONAR_CASO_ACT).equals(AWRevision.MAYOR_VALOR)){
				return MAYOR_VALOR;
			}
			return CONFIRMAR_OK;
        } else if (accion.equals(AWRevision.REVISAR_APROBAR)) {
            return CONFIRMAR_OK;
        } else if (accion.equals(AWRevision.NEGAR)) {
            return NEGAR_OK;
        } else if (accion.equals(AWRevision.ELIMINAR)) {
            return ELIMINAR_OK;
        } else if (accion.equals(AWRevision.ASOCIAR_UN_RANGO)) {
            return ASOCIAR_OK;
        } else if (accion.equals(AwCorrecciones_Constants.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION )) {
                return DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK;

		} else if (accion.equals(AwCorrecciones_Constants.ACT_PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION )) {
				return ACT_DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK;

        }else if (accion.equals(AWRevision.OCULTAR_MAYOR_VALOR) ||  accion.equals(AWRevision.OCULTAR_SOLICITUD)) {
			return ACT_DATOSANTIGUOSISTEMA_RECARGAR_EVENTRESP_OK;
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
