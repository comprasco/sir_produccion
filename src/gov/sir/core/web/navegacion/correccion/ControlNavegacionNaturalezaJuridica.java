package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWNaturalezaJuridica;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

public class ControlNavegacionNaturalezaJuridica implements ControlNavegacion {
	
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION = "ANOTACION_NAT_JURIDICA_ESPECIFICACION";
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest arg0) {

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		else if(accion.equals(AWNaturalezaJuridica.ANOTACION_NAT_JURIDICA_ESPECIFICACION)){
			return ANOTACION_NAT_JURIDICA_ESPECIFICACION;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}


}
