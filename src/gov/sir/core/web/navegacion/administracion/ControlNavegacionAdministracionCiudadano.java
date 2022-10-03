/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWAdministracionCiudadano;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWAdministracionCiudadano</CODE>
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionAdministracionCiudadano implements ControlNavegacion {

	/**
	 *  Constructor por Default de <CODE>ControlNavegacionAdministracionForseti</CODE>
	 */
	public ControlNavegacionAdministracionCiudadano() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
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
		if (accion.equals(AWAdministracionCiudadano.ADICIONA_PROHIBICION)
			|| accion.equals(AWAdministracionCiudadano.ELIMINA_PROHIBICION)
		    || accion.equals(AWAdministracionCiudadano.EDITA_DETALLES_PROHIBICION)) {
			return AWAdministracionCiudadano.ADICIONA_PROHIBICION;
		} else if (
			accion.equals(AWAdministracionCiudadano.CIUDADANO_CONSULTAR)
				|| accion.equals(AWAdministracionCiudadano.CIUDADANO_CANCELAR_EDICION)) {
			return AWAdministracionCiudadano.CIUDADANO_CONSULTAR;
		}
		
		//Edición de prohibiciones.
		else if (accion.equals(AWAdministracionCiudadano.EDITA_PROHIBICION)) {
			
			return AWAdministracionCiudadano.EDITA_PROHIBICION;
		}
		
		//Terminar Edición de Prohibiciones
		else if (accion.equals(AWAdministracionCiudadano.TERMINA_EDICION_PROHIBICION)) {
			
			return AWAdministracionCiudadano.TERMINA_EDICION_PROHIBICION;
		}
		
		 else if (
			accion.equals(AWAdministracionCiudadano.CIUDADANO_ADICIONAR_PROHIBICION)
				|| accion.equals(AWAdministracionCiudadano.CIUDADANO_ELIMINAR_PROHIBICION)
				|| accion.equals(AWAdministracionCiudadano.CIUDADANO_SELECCIONAR)) {
			return AWAdministracionCiudadano.CIUDADANO_ADICIONAR_PROHIBICION;
		} else if (accion.equals(AWAdministracionCiudadano.TERMINA)) {
			return AWAdministracionCiudadano.TERMINA;
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_CREAR)) {
			return AWAdministracionCiudadano.CIUDADANO_CREAR;
		}

		return null;
	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
