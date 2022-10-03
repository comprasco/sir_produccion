package gov.sir.core.web.navegacion.comun;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones;

/**
 * Esta clase determina la navegabilidad de las paginas cuando se ha consultado una lista
 * de anotaciones para un folio, su principal funcionalidad es dejar la misma vista
 * desde donde se llamó la consulta de anotaciones.
 * @author mmunoz
 */
public class ControlNavegacionPaginadorAnotaciones implements ControlNavegacion {
	
	public static final String RECARGAR = "RECARGAR";
	public static final String REFRESCAR_PAGINADOR = "REFRESCAR_PAGINADOR";

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest request) {

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = request.getParameter(WebKeys.ACCION);
		if(accion.equals(AWPaginadorAnotaciones.CONSULTAR_ANOTACIONES)){
			return (String) request.getSession().getAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA);
		} else if(accion.equals(AWPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO)){
			return (String) request.getSession().getAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA);
		} else if(accion.equals(AWPaginadorAnotaciones.REFRESCAR_PAGINADOR)){
			return (String) request.getSession().getAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA);
		} else if(accion.equals(AWPaginadorAnotaciones.TERMINAR_CONSULTA)){
			String v=(String) request.getSession().getAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA);
			if(v.equals("admin.folio.detalles.view")){
				return "admin.folio.consultar.view";
			}else if(v.equals("consultas.calificacion.folio.view")){
				return "calificacion.view"; 
			}
			else{
				return "bienvenido.view";
			}
		} else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}

}
