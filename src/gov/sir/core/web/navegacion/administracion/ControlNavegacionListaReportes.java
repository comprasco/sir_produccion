package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWListaReportes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

public class ControlNavegacionListaReportes implements ControlNavegacion {

  public static final String PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK
      = "PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK";

	public ControlNavegacionListaReportes() {
		super();
	}
	public void doStart(HttpServletRequest request) {

	}
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);
		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		}

                if( AWListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION.equals( accion ) ) {
                  return PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK;
                }


		return null;

	}

	public void doEnd(HttpServletRequest arg0) {

	}

}
