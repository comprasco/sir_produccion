package gov.sir.core.web.navegacion.certificado;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWCertificado;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author mmunoz
 */
public class ControlNavegacionCertificado implements ControlNavegacion {

	private static final String ENTREGADO = "ENTREGADO";

	/**
	 * Constante que indica que el proceso de inserción de una matrícula 
	 * o de un turno anterior a la solicitud fue exitoso
	 */
	public static final String AGREGAR_OK = "AGREGAR_OK";

	/**
	 * Constante que indica que el proceso de CAMBIO de una matrícula 
	 * asociada a un turno fue exitoso
	 */
	public static final String CAMBIAR_MATRICULA_OK = "CAMBIAR_MATRICULA_OK";

	/**
	 * Constante que indica que el proceso esta en la impresión de la hoja de ruta. 
	 */
	public static final String IMPRIMIR_HOJA_RUTA = "IMPRIMIR_HOJA_RUTA";

	/**
	 * Constante que indica que el proceso esta en la consulta para de un folio para copiar o asociar su complementación. 
	 */
	public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";
	
	/**
	 * Constante que indica que se desea mostrar cómo quedo el folio luego de hacerlo definitivo. 
	 */
	public static final String PREVIEW_CREACION_FOLIO = "PREVIEW_CREACION_FOLIO";	

	/**
	* Constante que indica que el proceso de administracion de secuenciales de recibo. 
	*/
	public static final String ADMINISTRACION_RECIBOS = "ADMINISTRACION_RECIBOS";
	
	/**
	* Constante que indica que el proceso de administracion de secuenciales de recibo. 
	*/
	public static final String ADMINISTRACION_RECIBOS_SIMPLIFICADO = "ADMINISTRACION_RECIBOS_SIMPLIFICADO";
	
	/**
	* Constante que indica que se desea consultar la plantilla predeterminada para el oficio de pertenencia. 
	*/
	public static final String CONSULTAR_PLANTILLA_PERTENENCIA = "CONSULTAR_PLANTILLA_PERTENENCIA";
	
	

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest arg0) throws ControlNavegacionException {
		String accion = arg0.getParameter(WebKeys.ACCION);
		if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_AGREGAR)) {
			return AGREGAR_OK;
		}
		if (accion.equals(AWCertificado.CAMBIAR_FOLIO_MATRICULA_ID)) {
			return CAMBIAR_MATRICULA_OK;
		}
		if (accion.equals(AWCertificado.CONSULTAR_SECUENCIAL_RECIBO_SIMPLIFICADO)) {
			return ADMINISTRACION_RECIBOS_SIMPLIFICADO;
		}
		
		if (accion.equals(AWCertificado.INCREMENTAR_SECUENCIAL_RECIBO)) {
			return ADMINISTRACION_RECIBOS;
		}

		if (accion.equals(AWCertificado.CONSULTAR_SECUENCIAL_RECIBO)) {
			return ADMINISTRACION_RECIBOS;
		}
		if (accion.equals(AWCertificado.INCREMENTAR_SECUENCIAL_RECIBO_SIMPLIFICADO)) {
			return ADMINISTRACION_RECIBOS_SIMPLIFICADO;
		}
		if (accion.equals(AWCertificado.CONSULTAR_SECUENCIAL_RECIBO_SIMPLIFICADO)) {
			return ADMINISTRACION_RECIBOS_SIMPLIFICADO;
		}

		if (accion.equals(AWCertificado.HOJA_RUTA_ELIMINAR_MATRICULA)) {
			return AGREGAR_OK;
		}

		if (accion.equals(AWCertificado.IMPRIMIR_HOJA_RUTA)) {
			return IMPRIMIR_HOJA_RUTA;
		}

		if (accion.equals(AWCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
			return CONSULTA_FOLIO_COMPLEMENTACION;
		} 
		
		if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_HACER_DEFINITIVO_FOLIO)) {
			return PREVIEW_CREACION_FOLIO;
		} 	
		
		if (accion.equals(AWCertificado.CONSULTAR_PLANTILLA_PERTENENCIA)) {
			return CONSULTAR_PLANTILLA_PERTENENCIA;
		}
		
		if (accion.equals(AWCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL)) {
			return AWCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL;
		}
		if(accion.equals(AWCertificado.CER_AMPLIACION_TRADICION) || accion.equals(AWCertificado.AMPLIACION_TRADICION_GUARDAR)){
			return AWCertificado.CER_AMPLIACION_TRADICION;
		}
        return ENTREGADO;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {
	}

}
