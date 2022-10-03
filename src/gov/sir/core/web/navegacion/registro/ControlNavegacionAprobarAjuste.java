/*
 * Created on 25-oct-2004
*/
package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWRevision;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author jfrias
*/
public class ControlNavegacionAprobarAjuste implements ControlNavegacion {
	public static final String REVISAR_FOLIO = "REVISAR_FOLIO";
	public static final String VER_DATOS="VER_DATOS";
	public static final String VER_DATOS_BASICOS="VER_DATOS_BASICOS";
	public static final String APROBAR_REVISION="APROBAR_REVISION";
	public static final String DEVOLVER="DEVOLVER";
	public static final String REVISAR_FOLIO_DEVOLUCION="REVISAR_FOLIO_DEVOLUCION";
	public static final String VER_DATOS_DEVOLUCION="VER_DATOS_DEVOLUCION";
	public static final String VER_DATOS_BASICOS_DEVOLUCION="VER_DATOS_BASICOS_DEVOLUCION";
	public static final String APROBAR_REVISION_DEVOLUCION="APROBAR_REVISION_DEVOLUCION";
	public static final String CONFIRMAR_DEV="CONFIRMAR_DEV";
	public static final String REGRESAR_REVISION="REGRESAR_REVISION";
	

	/**
	 * 
	 */
	public ControlNavegacionAprobarAjuste() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest request) {

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest request)
		throws ControlNavegacionException {
			String accion = (String) request.getParameter(WebKeys.ACCION);
			if (accion.equals(AWRevision.REVISAR_FOLIO)) {
				return REVISAR_FOLIO;
			}else if(accion.equals(AWRevision.REVISAR_FOLIO_DEVOLUCION)){
				return REVISAR_FOLIO_DEVOLUCION;
			}else if(accion.equals(AWRevision.DEVOLVER)){
				return DEVOLVER;
			}else if(accion.equals(AWRevision.VER_DATOS)){
				return VER_DATOS;
			}else if(accion.equals(AWRevision.VER_DATOS_BASICOS)){
				return VER_DATOS_BASICOS;
			}else if(accion.equals(AWRevision.VER_DATOS_DEVOLUCION)){
				return VER_DATOS_DEVOLUCION;
			}else if(accion.equals(AWRevision.VER_DATOS_BASICOS_DEVOLUCION)){
				return VER_DATOS_BASICOS_DEVOLUCION;
			}else if(accion.equals(AWRevision.CONFIRMAR_DEV)){
				return CONFIRMAR_DEV;
			}else if(accion.equals(AWRevision.APROBAR_REVISION)){
				return APROBAR_REVISION;
			}else if(accion.equals(AWRevision.REGRESAR_REVISION)){
				return REGRESAR_REVISION;
			}
			
			return null;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}

}
