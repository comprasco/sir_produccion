package gov.sir.core.web.navegacion.migracionincremental;

import gov.sir.core.eventos.migracionincremental.EvnMigracionIncremental;
import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

public class ControlNavegacionMigracionIncremental  implements ControlNavegacion {

	public void doStart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		
		String accion = (String) request.getParameter(WebKeys.ACCION);
		
       if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
        }
       
       if (accion.equals(EvnMigracionIncremental.TIPO_MIGRAR_FOLIO)) {
       		return "OK_FOLIO";      
       	}
       else if (accion.equals(EvnMigracionIncremental.TIPO_MIGRAR_TURNO)) {
      		return "OK_TURNO";      
      	}
		
		
		return null;
	}

	public void doEnd(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
