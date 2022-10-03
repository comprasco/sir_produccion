package gov.sir.core.web.navegacion.devolucion;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.web.WebKeys;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.sir.core.web.acciones.devolucion.AWLiquidacionDevolucion;


/**
 * @author dsalas,mmunoz, jvelez
 */
public class ControlNavegacionLiquidacionDevolucion implements ControlNavegacion {
    /** Constante que indica que el proceso de liquidación fué exitoso */
    public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

    /** Constante que indica que el proceso de liquidación no fué exitoso */
    public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

    /** Constante que indica que el proceso de solicitud fué exitoso */
    public static final String SOLICITUD_OK = "SOLICITUD_OK";
    private static final String AGREGAR_OK = "AGREGAR_OK";
    
	private static final String CONFIRMACION_ERROR_IMPRESION = "CONFIRMACION_ERROR_IMPRESION";

    /**
     *
     */
    public ControlNavegacionLiquidacionDevolucion() {
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
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                "La accion enviada por la accion web no es válida");
        }

        if (accion.equals(AWLiquidacionDevolucion.AGREGAR_TURNO_ANT)){
            return AGREGAR_OK;
        }
        
        if (accion.equals(AWLiquidacionDevolucion.AGREGAR_CONSIGNACION_CHEQUE)){
            return AGREGAR_OK;
        }
        
        if (accion.equals(AWLiquidacionDevolucion.AGREGAR_SOLICITANTE)){
            return AGREGAR_OK;
        }
        
        if (accion.equals(AWLiquidacionDevolucion.ELIMINAR_SOLICITANTE)){
            return AGREGAR_OK;
        }
        
        if (accion.equals(AWLiquidacionDevolucion.ELIMINAR_CONSIGNACION_CHEQUE)){
            return AGREGAR_OK;
        }


		if (accion.equals(AWLiquidacionDevolucion.ELIMINAR_TURNO_ANTERIOR)){
			
			HttpSession sesion = request.getSession();
			sesion.removeAttribute("LISTA_ASOCIADOS");
			sesion.removeAttribute("TURNO_ANTERIOR");
			
			return AGREGAR_OK;
		}
		
		if (accion.equals(AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO)){
			
			
			
					return AGREGAR_OK;
				}


        //        if (accion.equals(AWLiquidacionDevolucion.LIQUIDAR)) {
        if (request.getSession().getAttribute(WebKeys.TURNO) == null) {
            Liquidacion liquidacion = (Liquidacion) request.getSession()
                                                           .getAttribute(WebKeys.LIQUIDACION);
                                                           
            if (liquidacion != null) {
                return LIQUIDACION_OK;
            }

            return LIQUIDACION_FAILED;
        }
        
		Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
		if(impreso!=null && impreso.intValue() != 0){
			return CONFIRMACION_ERROR_IMPRESION;	
		}	        

        return SOLICITUD_OK;
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest request) {
    }
}
