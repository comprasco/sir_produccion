package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWCrearFolio;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author jfrias
 *
 */
public class ControlNavegacionCrearFolio implements ControlNavegacion {

    public static final String CREAR_FOLIO="CREAR_FOLIO";
    public static final String CREAR_ANOTACION="CREAR_ANOTACION";
	public static final String TERMINAR_CREACION_FOLIO="TERMINAR_CREACION_FOLIO";    
    

    /**
	 * 
	 */
	public ControlNavegacionCrearFolio() {
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
        String accion=request.getParameter(WebKeys.ACCION);
        
		if (accion.equals(AWCrearFolio.INICIO_CREAR_NUEVO_FOLIO)){
			return CREAR_FOLIO;
		}else if (accion.equals(AWCrearFolio.AGREGAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		}else if (accion.equals(AWCrearFolio.ELIMINAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		}else if (accion.equals(AWCrearFolio.CREAR_FOLIO)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.REMOVER_1_REGISTRO_TABLA_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.AGREGAR_VARIOS_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.ELIMINAR_CIUDADANO_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.VALIDAR_CIUDADANO)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.CARGAR_DESCRIPCION_NATURALEZA)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.AGREGAR_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolio.TERMINAR_CREACION_FOLIO)){
			return TERMINAR_CREACION_FOLIO;
		}
		
		else if (accion.equals(AWCrearFolio.ELIMINAR_FOLIO_TEMPORAL)){
			return TERMINAR_CREACION_FOLIO;
		}		
		
		else if (accion.equals(AWCrearFolio.CONSULTA_FOLIO_COMPLEMENTACION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWCrearFolio.REFRESCAR_ANOTACION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }	
        return null;
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest request) {

    }

}
