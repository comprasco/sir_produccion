package gov.sir.core.web.navegacion.certificado;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWCertificadoEspecial;


/**
 * @author jfrias
 */
public class ControlNavegacionCertificadoEspecial implements ControlNavegacion {
    /** Constante que indica que el proceso de liquidación fué exitoso */
    public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

    /** Constante que indica que el proceso de radicacion (Liquidacion y pago) fué exitoso */
    public static final String RADICAR_OK = "RADICAR_OK";
        
    /** Constante que indica que el proceso de liquidación no fué exitoso */
    public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

    /**
     * Constante que indica que el proceso de inserción de una matrícula 
     * o de un turno anterior a la solicitud fue exitoso
     */
    public static final String AGREGAR_OK = "AGREGAR_OK";

    /**
     * Constante que indica que el proceso de eliminación de una matrícula
     * de la solicitud fue exitoso
     */
    public static final String ELIMINAR_OK = "ELIMINAR_OK";
    
    /**
     * Constante que indica que el proceso de eliminación de una matrícula
     * no fue exitoso
     */
    public static final String ELIMINAR_FAILED = "ELIMINAR_FAILED";

    /**
     * Constante que indica que el proceso de inserción de una matrícula a la
     * solicitud no fue exitoso
     */
    public static final String AGREGAR_FAILED = "AGREGAR_FAILED";
    
	/**
	 * Constante que indica que se cancelo la el proceso de liquidacion
	 */
	public static final String CANCELADO = "CANCELADO";
	
	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";

	/** Constante que indica que el proceso de solicitud no fué exitoso */
	public static final String SOLICITUD_FAILED = "SOLICITUD_FAILED";

    /**
     *
     */
    public ControlNavegacionCertificadoEspecial() {
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

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                "La accion enviada por la accion web no es valida");
        }

        if (accion.equals(AWCertificadoEspecial.LIQUIDAR)) {
        	if(request.getSession().getAttribute(WebKeys.TURNO) == null){
	            Liquidacion liquidacion = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);
	
	            if (liquidacion != null) { 
	                return LIQUIDACION_OK;
	            } else {
	                return LIQUIDACION_FAILED;
	            }
        	} else if (request.getSession().getAttribute(WebKeys.TURNO)!=null){
					return SOLICITUD_OK; 
			}
		}else if (accion.equals(AWCertificadoEspecial.RADICAR)) {
			return RADICAR_OK;
        }else if (accion.equals(AWCertificadoEspecial.RECARGAR)) {
			return AGREGAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.CERTIFICADO_ESPECIAL)){
                        return AGREGAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.AGREGAR)) {
            return AGREGAR_OK;
		} else if (accion.equals(AWCertificadoEspecial.AGREGAR_MIG_INC)) {
			return AGREGAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.ELIMINAR_MIG_INC)) {
			return AGREGAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.AGREGAR_TURNO)) {
			return AGREGAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.ELIMINAR)) {
            return ELIMINAR_OK;
        } else if (accion.equals(AWCertificadoEspecial.REMOVER_INFO)) {
			return CANCELADO;    
        } else if (accion.equals(AWCertificadoEspecial.CAMBIAR_TIPO_OFICINA_ORIGEN)) {
			return AGREGAR_OK;    
        }
         
		return null;

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest request) {
    }
}
