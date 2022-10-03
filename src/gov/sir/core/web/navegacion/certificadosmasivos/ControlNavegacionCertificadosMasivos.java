package gov.sir.core.web.navegacion.certificadosmasivos;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificadosmasivos.AWCertificadoMasivo;
import gov.sir.core.web.acciones.certificadosmasivos.AWLiquidacionCertificadosMasivos;

/**
 *
 * @author  dcantor
 */
public class ControlNavegacionCertificadosMasivos implements ControlNavegacion{
    
    public static final String AGREGAR_OK = "AGREGAR_OK";
	public static final String TIPO_OK = "TIPO_OK";
    public static final String ELIMINAR_OK = "ELIMINAR_OK";
	public static final String ELIMINAR_VERIFICADO_OK = "ELIMINAR_VERIFICADO_OK";
	public static final String AGREGAR_VERIFICADO_OK = "AGREGAR_VERIFICADO_OK";
	public static final String UPLOAD_OK = "SUBIR_ARCHIVO_OK";
	public static final String SUBIR_ARCHIVO_VERIFICADO_OK = "SUBIR_ARCHIVO_VERIFICADO_OK";
	/** Constante que indica que el proceso de liquidación fué exitoso */
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que el proceso de liquidación no fué exitoso */
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";
	
	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";

	/** Constante que indica que el proceso de solicitud no fué exitoso */
	public static final String SOLICITUD_FAILED = "SOLICITUD_FAILED";
	



	/** Constante que indica que la fase de entrega de certificados masivos se realizó correctamente */
	public static final String ENTREGA_CONFIRMAR = "ENTREGA_CONFIRMAR";

	/** Constante que indica que la fase de entrega de certificados masivos no se realizó correctamente */
	public static final String ENTREGA_NEGAR = "ENTREGA_NEGAR";
	
	
    
    /** Creates a new instance of ControlNavegacionCertificadosMasivos */
    public ControlNavegacionCertificadosMasivos() { }
    public void doStart(HttpServletRequest request) { }
    public void doEnd(HttpServletRequest request) { }
    
    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);
        
		boolean isMultipart = FileUpload.isMultipartContent(request);
		
		if ( !isMultipart ) {		
	        if ((accion == null) || accion.equals("")) {
	            throw new ControlNavegacionException(
	                "La accion enviada por la accion web no es valida");
	        }
			if (accion.equals(AWLiquidacionCertificadosMasivos.AGREGAR)){
				return AGREGAR_OK;
			}
			else if (accion.equals(AWLiquidacionCertificadosMasivos.AGREGAR_VERIFICADO)){
				return AGREGAR_VERIFICADO_OK;
			}
			else if (accion.equals(AWLiquidacionCertificadosMasivos.ELIMINAR)){
				return ELIMINAR_OK;
			}
			else if (accion.equals(AWLiquidacionCertificadosMasivos.ELIMINAR_VERIFICADO)){
				return ELIMINAR_VERIFICADO_OK;
			}
			else if (accion.equals(AWLiquidacionCertificadosMasivos.TIPO)){
				return TIPO_OK;
			}
			else if (accion.equals(AWLiquidacionCertificadosMasivos.LIQUIDAR)){
				if(request.getSession().getAttribute(WebKeys.TURNO) == null){
					Liquidacion liquidacion = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);

					if (liquidacion != null) {
						return LIQUIDACION_OK;
					} 
					else {
						return LIQUIDACION_FAILED;
					}					
				} else { 
					if(request.getSession().getAttribute(WebKeys.TURNO) != null){
						return SOLICITUD_OK; 
					}
				}

			}
			else if (accion.equals(AWCertificadoMasivo.ENTREGAR_CONFIRMAR)){
				return ENTREGA_CONFIRMAR;
			}
			else if (accion.equals(AWCertificadoMasivo.ENTREGAR_NEGAR)){
				return ENTREGA_NEGAR;
			}
		}
        else {
			String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			if(ultimaVista.equals("turno.certificados.masivos.validado.view")){
			   return SUBIR_ARCHIVO_VERIFICADO_OK;
			}
			return UPLOAD_OK;
        }
        
        return null;
    }
}