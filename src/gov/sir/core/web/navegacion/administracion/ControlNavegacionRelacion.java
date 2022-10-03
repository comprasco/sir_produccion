package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWRelacion;
import gov.sir.core.web.acciones.administracion.AWReportes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

public class ControlNavegacionRelacion implements ControlNavegacion {

	public void doStart(HttpServletRequest request) {

	}

	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = request.getParameter(WebKeys.ACCION);

		//HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWRelacion.SELECCIONAR_FASE)) {
			return AWRelacion.SELECCIONAR_FASE;
		} else if (accion.equals(AWRelacion.SELECCIONAR_FASE_IMPRESION)) {
			return AWRelacion.SELECCIONAR_FASE_IMPRESION;
		} else if (accion.equals(AWRelacion.TERMINA)) {
			return AWRelacion.TERMINA;
		} else if (accion.equals(AWRelacion.SELECCIONAR_PROCESO)) {
			return AWRelacion.SELECCIONAR_PROCESO;
		} else if (accion.equals(AWRelacion.SELECCIONAR_PROCESO_IMPRESION)) {
			return AWRelacion.SELECCIONAR_PROCESO_IMPRESION;
		} else if (accion.equals(AWRelacion.SELECCIONAR_RELACION)) {
			return AWRelacion.SELECCIONAR_RELACION;
		} else if (accion.equals(AWRelacion.INGRESAR_RELACION)) {
			return AWRelacion.INGRESAR_RELACION;
		} else if (accion.equals(AWRelacion.CARGAR_DATOS)) {
			return AWRelacion.CARGAR_DATOS;
		} else if (accion.equals(AWRelacion.CREAR_RELACION)) {
			/*String mesa = (String) request.getSession().getAttribute(AWMesa.MESA_CONTROL);
			boolean vieneDeMesa = false;
			if(mesa!=null){
				request.getSession().removeAttribute(AWMesa.MESA_CONTROL);
				vieneDeMesa = true;
			}
			if(!vieneDeMesa){
				return AWRelacion.CREAR_RELACION;
			}else{*/
				return AWRelacion.CREAR_RELACION;
			//}
        } else if (accion.equals(AWRelacion.CARGAR_DATOS_IMPRESION)) {
            return AWRelacion.CARGAR_DATOS_IMPRESION;
        } else if (accion.equals(AWRelacion.IMPRIMIR) || accion.equals(AWRelacion.IMPRIMIR_SIN_NOTA)) {

        	String param_Page = (String) request.getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
    		HttpSession session  = request.getSession();
    		session.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, param_Page);
        	//return AWRelacion.IMPRIMIR;
        	return AWRelacion.REPORTES_JASPER_SERVLET;
        }

      else if (accion.equals(AWRelacion.IMPRIMIR_REPARTO)) {

              String param_Page = (String) request.getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
              HttpSession session  = request.getSession();
              session.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, param_Page);

              //return AWRelacion.IMPRIMIR_REPARTO;
              return AWRelacion.REPORTES_JASPER_SERVLET;
      }
      else if (accion.equals(AWRelacion.VER_DETALLE_RELACION)) {
    	  return AWRelacion.VER_DETALLE_RELACION;
      }
		


		return null;
	}

	public void doEnd(HttpServletRequest request) {

	}

}
