/*
 * Created on 24-ene-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.navegacion.comun;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ControlNavegacionCiudadano implements ControlNavegacion{

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest arg0) {

		
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		return (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {
		
	}
}
