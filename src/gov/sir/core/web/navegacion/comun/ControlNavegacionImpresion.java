package gov.sir.core.web.navegacion.comun;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWImpresion;

/**
 * @author ppabon
 */
public class ControlNavegacionImpresion implements ControlNavegacion {

	public static final String DEFAULT = "DEFAULT";
	public static final String DESCARGAR_IMPRIMIBLE = "DESCARGAR_IMPRIMIBLE";
	public final static String IMPRESIONES_FALLIDAS = "IMPRESIONES_FALLIDAS";	
	public final static String CONSULTA_REGLAS= "CONSULTA_REGLAS";
	public final static String CONFIGURACION_ACTUAL= "CONFIGURACION_ACTUAL";	
	public static final String VISTA_DESCARGAR_IMPRIMIBLE = "admin.descargar.imprimible.view";

	public static final String VERIFICAR_SESION = "VERIFICAR_SESION";
	
	public static final String PARAMETROS_CONFIGURACION = "PARAMETROS_CONFIGURACION";
	
	
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
		String id = request.getParameter(CImpresion.ID_IMPRIMIBLE);
		
		if (accion.equals(AWImpresion.DESCARGAR_IMPRIMIBLE)) {
			return VISTA_DESCARGAR_IMPRIMIBLE + "?" + CImpresion.ID_IMPRIMIBLE + "=" + id;
		}else if (accion.equals(AWImpresion.CONSULTAR_IMPRESIONES_FALLIDAS)) {
			return IMPRESIONES_FALLIDAS;
		}else if (accion.equals(AWImpresion.CONSULTAR_LISTA_REGLAS)){
			return CONSULTA_REGLAS;
		}else if (accion.equals(AWImpresion.CARGAR_CONFIGURACION_ACTUAL)){
			return CONFIGURACION_ACTUAL;
		} else if(accion.equals(AWImpresion.VERIFICAR_SESION)) {
			return VERIFICAR_SESION;
		} else if (accion.equals(AWImpresion.CARGAR_PARAMETROS_CONFIGURACION))
		{
			return PARAMETROS_CONFIGURACION;
		}
		
		return DEFAULT;

	} /* (non-Javadoc)
		 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
		 */
	public void doEnd(HttpServletRequest request) {

	}

}
