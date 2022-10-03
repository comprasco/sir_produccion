package gov.sir.core.web.navegacion.devolucion;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.devolucion.AWDevolucion;

import gov.sir.core.web.WebKeys;
import javax.servlet.http.HttpSession;

/**
 * @author mmunoz, jvelez
 */
public class ControlNavegacionDevolucion implements ControlNavegacion {

    public static final String CONFIRMACION = "CONFIRMACION";
    public static final String IMPRIMIR_OK = "IMPRIMIR_OK";
    public static final String NOT_NOTA_DEVOLUTIVA = "NOT_NOTA_DEVOLUTIVA";
    public static final String NOT_NOTA_NOTIFICADA = "NOT_NOTA_NOTIFICADA";
    public static final String NOT_RECURSOS_NOTA = "NOT_RECURSOS_NOTA";
    public static final String REANOTAR_TURNO_INICIO = "REANOTAR_TURNO_INICIO";
    public static final String CONFIRMAR_NOTA = "CONFIRMAR_NOTA";


    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
     */
    public void doStart(HttpServletRequest arg0) {

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
     */
    public String procesarNavegacion(HttpServletRequest request) throws
            ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);

        HttpSession sesion = request.getSession();

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                    "La accion enviada por la accion web no es valida");
        }

        if (accion.equals(AWDevolucion.IMPRIMIR_BORRADOR)) {
            return IMPRIMIR_OK;

        }
        
        if (accion.equals(AWDevolucion.NOTIFICAR_NOTA_DEVOLUTIVA)){
            return NOT_NOTA_DEVOLUTIVA;
        }
        
        if (accion.equals(AWDevolucion.AGREGAR_NOTIFICACION)){
            return NOT_NOTA_DEVOLUTIVA;
        }
        
        if (accion.equals(AWDevolucion.CARGAR_CONTEXTO)){
            return NOT_NOTA_DEVOLUTIVA;
        }
        
        if (accion.equals(AWDevolucion.NOTA_DEVOLUTIVA_NOTIFICADA)){
            return NOT_NOTA_NOTIFICADA; 
        }
        
        if(accion.equals(AWDevolucion.CONTINUE_WF) || accion.equals(AWDevolucion.ENVIA_JUZGADO)){
            return NOT_NOTA_NOTIFICADA;
        }
        
        if(accion.equals(AWDevolucion.APODERADO_SI) || accion.equals(AWDevolucion.APODERADO_NO)){
            return NOT_NOTA_DEVOLUTIVA;
        }
        
        if (accion.equals(AWDevolucion.GUARDAR_NOTIFICACION) || accion.equals(AWDevolucion.EDITAR_NOTIFICACION) || accion.equals(AWDevolucion.ELIMINAR_NOTIFICACION)){
            return NOT_NOTA_DEVOLUTIVA;
        }
        
        if(accion.equals(AWDevolucion.CHANGE_STATE_NOTA_NOTIFICADA)){
            return CONFIRMAR_NOTA;
        }
        
        if(accion.equals(AWDevolucion.RECURSOS_NOT) || accion.equals(AWDevolucion.NO_RECURSOS_NOT)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.EDITAR_RECURSO_NOTA_DEVOLUTIVA) || accion.equals(AWDevolucion.ELIMINAR_RECURSO_NOTA_DEVOLUTIVA) 
                || accion.equals(AWDevolucion.GUARDAR_RECURSO_NOTA_DEVOLUTIVA)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.ELIMINAR_RECURSOS_NOT)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.CARGAR_RECURSOS_NOTA)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.FAVORABLE) || accion.equals(AWDevolucion.NO_FAVORABLE)){
            return NOT_RECURSOS_NOTA;
        }
        
        if(accion.equals(AWDevolucion.REANOTAR_TURNO)){
            return REANOTAR_TURNO_INICIO;
        }
        
        
        
        

		if (accion.equals(AWDevolucion.AGREGAR_RECURSO)) {
			sesion.removeAttribute(AWDevolucion.TIPO_RECURSO);
			sesion.removeAttribute(AWDevolucion.USUARIO_RECURSO);
			sesion.removeAttribute(AWDevolucion.DESCRIPCION_RECURSO);
			return AWDevolucion.AGREGAR_RECURSO;
		}
		if (accion.equals(AWDevolucion.AGREGAR_RESOLUCION)){
            return IMPRIMIR_OK;
        }
		if(accion.equals(AWDevolucion.EDITAR_RECURSO)){
			return AWDevolucion.AGREGAR_RECURSO;
		}
		if (accion.equals(AWDevolucion.ELIMINAR)){
            return IMPRIMIR_OK;
        }
		if (accion.equals(AWDevolucion.AGREGAR_RESOLUCION_RECURSO)){
            return AWDevolucion.AGREGAR_RECURSO;
        }
        if (accion.equals(AWDevolucion.NO_RECURSO) || accion.equals(AWDevolucion.RECURSO)){
			return AWDevolucion.AGREGAR_RECURSO;
		}
		if (accion.equals(AWDevolucion.ELIMINAR_EN_RECURSOS)){
            return AWDevolucion.AGREGAR_RECURSO;
        }
		if( accion.equals(AWDevolucion.CAMBIAR_TURNO_DEVOLUCION) || accion.equals(AWDevolucion.ELIMINAR_CONSIGNACION_CHEQUE)
				|| accion.equals(AWDevolucion.AGREGAR_CONSIGNACION_CHEQUE)){
			return AWDevolucion.CAMBIAR_TURNO_DEVOLUCION;
		}


        return CONFIRMACION;
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {

    }

}
