package gov.sir.core.web.navegacion.comun;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWNotas;

/**
 * @author mmunoz, ppabon
 */
public class ControlNavegacionAdminNotas implements ControlNavegacion {

	public static final String RECARGAR = "RECARGAR";
	public static final String CARGAR_INFO_REQUEST = "CARGAR_INFO_REQUEST";
	public static final String IMPRIMIR_NOTA_INFORMATIVA = "IMPRIMIR_NOTA_INFORMATIVA";
	public static final String IMPRIMIR_NOTA_INFORMATIVA_DETALLE = "IMPRIMIR_NOTA_INFORMATIVA_DETALLE";	
	
	public static final String RECARGAR_DEVOLUTIVA = "RECARGAR_DEVOLUTIVA";	

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
		String ruta = "";
		String rutaEspecial = (String) request.getSession().getAttribute(AWNotas.RUTA_ESPECIAL);
		if(rutaEspecial!=null){
			ruta = rutaEspecial;
		}
		if (accion.equals(AWNotas.COLOCAR_DESCRIPCION)) {
			return RECARGAR+ruta;
		} else if (accion.equals(AWNotas.AGREGAR_NOTA)) {
			return RECARGAR+ruta;
		}else if (accion.equals(AWNotas.COLOCAR_DESCRIPCION_DEVOLUTIVA)) {
			return RECARGAR_DEVOLUTIVA+ruta;
		}else if (accion.equals(AWNotas.BUSCAR_SUBTIPO_DEVOLUTIVA)) {
			return RECARGAR_DEVOLUTIVA+ruta;
		} else if (accion.equals(AWNotas.CARGAR_INFO_REQUEST)) {
			return CARGAR_INFO_REQUEST+ruta;
		} else if (accion.equals(AWNotas.IMPRIMIR_NOTA_INFORMATIVA)) {
			return IMPRIMIR_NOTA_INFORMATIVA+ruta;
		} else if (accion.equals(AWNotas.IMPRIMIR_NOTA_INFORMATIVA_DETALLE)) {
			return IMPRIMIR_NOTA_INFORMATIVA_DETALLE+ruta;
		} else if (accion.equals(AWNotas.AGREGAR_NOTA_DEVOLUTIVA)) {
			return RECARGAR_DEVOLUTIVA+ruta;
		} else if (accion.equals(AWNotas.GUARDAR_NOTAS_DEVOLUTIVAS)) {
			return RECARGAR_DEVOLUTIVA+ruta;
		}else if (accion.equals(AWNotas.ELIMINAR_NOTA_DEVOLUTIVA)) {
			return RECARGAR_DEVOLUTIVA+ruta; 
		}else if(accion.equals(AWNotas.AGREGAR_NOTA_DEVOLUTIVA_EDITADA)){
			return RECARGAR_DEVOLUTIVA+ruta;
		}else if(accion.equals(AWNotas.REGISTRAR_CALIFICACION_PARCIAL)){
			return RECARGAR_DEVOLUTIVA+ruta;
		}
		else {
			return (String) request.getSession().getAttribute(AWNotas.VISTA_ORIGINADORA);
		}

	} /* (non-Javadoc)
		 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
		 */
	public void doEnd(HttpServletRequest request) {

	}
	
	

}
