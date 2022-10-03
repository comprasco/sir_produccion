package gov.sir.core.web.navegacion.antiguosistema;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author jfrias
 *
 */
public class ControlNavegacionAntiguoSistema implements ControlNavegacion {

    public static final String CONFIRMACION="CONFIRMACION";
    public static final String HOJA_RUTA="HOJA_RUTA";
    public static final String CREAR_FOLIO="CREAR_FOLIO";
    public static final String CREAR_ANOTACION="CREAR_ANOTACION";
    public static final String EDITAR_FOLIO="EDITAR_FOLIO";
    public static final String EDITAR_ANOTACIONES="EDITAR_ANOTACIONES";
    public static final String EDITAR_ANOTACION="EDITAR_ANOTACION";
    public static final String CANCELACION_ANOTACION = "CANCELACION_ANOTACION";
    public static final String ANOTACION_CANCELACION = "ANOTACION_CANCELACION";
    public static final String REFRESCAR_EDICION = "REFRESCAR_EDICION";
    public static final String REGRESAR_FOLIO = "REGRESAR_FOLIO";
    
   
    /**  */
    public ControlNavegacionAntiguoSistema() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
     */
    public void doStart(HttpServletRequest request) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
     */
    public String procesarNavegacion(HttpServletRequest request)
            throws ControlNavegacionException {
        String accion=request.getParameter(WebKeys.ACCION);
        if (accion.equals(AWAntiguoSistema.AVANZAR_APROBAR_REVISION_INICIAL)){
            return CONFIRMACION;
        }else if(accion.equals(AWAntiguoSistema.AVANZAR_NEGAR_REVISION_INICIAL)){
            return CONFIRMACION;
        }else if(accion.equals(AWAntiguoSistema.AVANZAR_EXISTENTE_REVISION_INICIAL)){
            return CONFIRMACION;
        }else if(accion.equals(AWAntiguoSistema.ASOCIAR_FOLIO)){
            return HOJA_RUTA;
        }else if(accion.equals(AWAntiguoSistema.EDITAR_FOLIO)){
            return EDITAR_FOLIO;
        }else if(accion.equals(AWAntiguoSistema.ELIMINAR_FOLIO)){
            return HOJA_RUTA;
        }else if(accion.equals(AWAntiguoSistema.DESASOCIAR_FOLIO)){
            return HOJA_RUTA;
        }else if(accion.equals(AWAntiguoSistema.IMPRIMIR_FOLIO)){
            return HOJA_RUTA;
        }else if (accion.equals(AWAntiguoSistema.DIGITAR_HOJA_RUTA)){
            return CREAR_FOLIO;
        }else if (accion.equals(AWAntiguoSistema.REGRESAR_FOLIO)){
            return CREAR_FOLIO;
        }else if (accion.equals(AWAntiguoSistema.CONSULTA_FOLIO_COMPLEMENTACION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_DIRECCION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWAntiguoSistema.ELIMINAR_DIRECCION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWAntiguoSistema.REFRESCAR_ANOTACION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWAntiguoSistema.CREAR_FOLIO)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.REMOVER_1_REGISTRO_TABLA_CIUDADANOS)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.VALIDAR_CIUDADANO)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_ANOTACION)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_MATRICULA_ASO)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.BORRAR_MATRICULA_ASO)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_MATRICULA_ASO_EDIT)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.BORRAR_MATRICULA_ASO_EDIT)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.TERMINAR_FOLIO)){
            return HOJA_RUTA;
        }else if (accion.equals(AWAntiguoSistema.TERMINAR_EDITAR_FOLIO)){
            return HOJA_RUTA;
        }else if (accion.equals(AWAntiguoSistema.EDITAR_ANOTACIONES_FOLIO)){
            return EDITAR_ANOTACIONES;
        }else if (accion.equals(AWAntiguoSistema.EDITAR_ANOTACIONES)){
            return EDITAR_ANOTACIONES;
        }else if (accion.equals(AWAntiguoSistema.EDITAR_ANOTACION)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.VALIDAR_CIUDADANO_EDICION)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
            return EDITAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.GRABAR_EDICION_ANOTACION)){
            return EDITAR_ANOTACIONES;
        }else if (accion.equals(AWAntiguoSistema.CANCELAR_EDICION_ANOTACION)){
            return EDITAR_ANOTACIONES;
        }else if (accion.equals(AWAntiguoSistema.ELIMINAR_ANOTACION)){
            return CREAR_ANOTACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_APROBAR_HOJA_RUTA)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_DEVOLVER_REVISION_INICIAL)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_FOLIO_MAS_DOCS)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_FOLIO_RECHAZADO)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_APROBAR_CREACION_FOLIO)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.AVANZAR_NEGAR_CREACION_FOLIO)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.REVISION_FINAL_CREADO)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.REVISION_FINAL_EXISTE)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.REVISION_FINAL_MAS_DOCS)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.REVISION_FINAL_REANALIZAR)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.REVISION_FINAL_RECHAZADO)){
            return CONFIRMACION;
        }else if (accion.equals(AWAntiguoSistema.COMENZAR_CANCELAR_ANOTACION)){
			return CANCELACION_ANOTACION;
		}else if (accion.equals(AWAntiguoSistema.GUARDAR_ANOTACIONES_TEMPORALES)){
			return ANOTACION_CANCELACION;
		}else if (accion.equals(AWAntiguoSistema.CANCELAR_ANOTACION)){
			return HOJA_RUTA;
		}else if (accion.equals(AWAntiguoSistema.VALIDAR_CIUDADANO_CANCELACION)){
			return ANOTACION_CANCELACION;
		}else if (accion.equals(AWAntiguoSistema.AGREGAR_VARIOS_CIUDADANOS_CANCELACION)){
			return ANOTACION_CANCELACION;
		}else if (accion.equals(AWAntiguoSistema.CANCELAR_CANCELACION)){
			return HOJA_RUTA;
		} else if (accion.equals(AWAntiguoSistema.REFRESCAR_EDICION)){
			return REFRESCAR_EDICION;
		} else if (accion.equals(AWAntiguoSistema.AVANZAR_CORRECCIONES)) {
			return CONFIRMACION;
		} else if (accion.equals(AWAntiguoSistema.AVANZAR_MAYOR_VALOR)) {
			return CONFIRMACION;
		}
        
        // sigue igual control de navegacion luego de guardar en temporal
        
        
        
        return null;
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest request) {
        // TODO Auto-generated method stub

    }

}
