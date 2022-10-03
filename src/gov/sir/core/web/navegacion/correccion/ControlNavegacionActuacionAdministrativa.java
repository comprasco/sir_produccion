package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWActuacionAdministrativa;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegaci�n para la Acci�n Web,
 * @author ppabon
 */
public class ControlNavegacionActuacionAdministrativa implements ControlNavegacion {

	public static final String CONFIRMAR_OK = "CONFIRMAR_OK";
	
	public static final String OCULTAR_CAMPOS_ACTUACION = "OCULTAR_CAMPOS_ACTUACION";
	
	public static final String AGREGAR_NOTA = "AGREGAR_NOTA";

	public ControlNavegacionActuacionAdministrativa() {
		super();
	}

	/**
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest request) {
	}

	/**
	 *   Este m�todo lo que hace es la verificaci�n de los diferentes objectos que se encuentran en la sesion,
	 *   y de acuerdo a esa verificaci�n manda una respuesta para que sea procesada y asi poder tener una
	 *   navegaci�n acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}

		if (accion.equals(AWActuacionAdministrativa.APROBAR_ACTUACION) 
				 || accion.equals(AWActuacionAdministrativa.DEVOLVER_A_CORRECCION)
				 || accion.equals(AWActuacionAdministrativa.ENVIAR_TURNO)) {
			return CONFIRMAR_OK;
		}else if (accion.equals(AWActuacionAdministrativa.OCULTAR_MAYOR_VALOR) 
				 ||  accion.equals(AWActuacionAdministrativa.OCULTAR_SOLICITUD)
				 ||  accion.equals(AWActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA)) {
			return OCULTAR_CAMPOS_ACTUACION;
		}else if (accion.equals(AWActuacionAdministrativa.AGREGAR_NOTA)) {
			return AGREGAR_NOTA;
		}else if (accion.equals(AWActuacionAdministrativa.EDITAR_NOTA)) {
			return AGREGAR_NOTA;
		}

		return CONFIRMAR_OK;

	}

	/**
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
