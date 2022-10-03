package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWCrearFolioOficio;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author jfrias
 *
 */
public class ControlNavegacionCrearFolioOficio implements ControlNavegacion {

    public static final String CREAR_FOLIO="CREAR_FOLIO";
    public static final String CREAR_ANOTACION="CREAR_ANOTACION";
    public static final String CREAR_CANCELACION = "CREAR_CANCELACION";
    public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";    
	public static final String TERMINAR_CREACION_FOLIO="TERMINAR_CREACION_FOLIO";
	public static final String CANCELAR_CREACION="CANCELAR_CREACION";

	public static final String ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESPOK
	= "ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESPOK";
	public static final String ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESPOK
	= "ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESPOK";
    public static final String ADMINCREARFOLIO_SAVEINFO_STEP3_BACK__EVENTRESPOK
    = "ADMINCREARFOLIO_SAVEINFO_STEP3_BACK__EVENTRESPOK";
    
    public static final String ELIMINAR_CIUDADANO_EDICION__EVENTRESP_OK
    = "ELIMINAR_CIUDADANO_EDICION__EVENTRESP_OK";
    


  // bug 05165

  public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK__EVENTRESP_OK
      ="ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK__EVENTRESP_OK";
  public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT__EVENTRESP_OK
      = "ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT__EVENTRESP_OK";
  public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT__EVENTRESP_OK
      = "ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT__EVENTRESP_OK";


	public static final String CANCELAR_ANOTACIONES = "CANCELAR_ANOTACIONES";
	
	public static final String CREAR_SEGREGACION_ENGLOBE = "CREAR_SEGREGACION_ENGLOBE";

    /**
	 *
	 */
	public ControlNavegacionCrearFolioOficio() {
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

		if (accion.equals(AWCrearFolioOficio.INICIO_CREAR_NUEVO_FOLIO)){
			return CREAR_FOLIO;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		}else if (accion.equals(AWCrearFolioOficio.ELIMINAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		}else if (accion.equals(AWCrearFolioOficio.CREAR_FOLIO)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.REMOVER_1_REGISTRO_TABLA_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_VARIOS_CIUDADANOS)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_VARIOS_CIUDADANOS_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.ELIMINAR_CIUDADANO_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.VALIDAR_CIUDADANO)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.VALIDAR_CIUDADANO_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.VALIDAR_CIUDADANO_CANCELACION)){
			return CANCELAR_ANOTACIONES;
		}else if (accion.equals(AWCrearFolioOficio.CARGAR_DESCRIPCION_NATURALEZA)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.GUARDAR_ANOTACIONES_TEMPORALES)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_SALVEDAD_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_ANOTACION)){
			return CREAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.AGREGAR_SALVEDAD_ANOTACION_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.ELIMINAR_SALVEDAD_ANOTACION_EDICION)){
			return EDITAR_ANOTACION;
		}else if (accion.equals(AWCrearFolioOficio.CREAR_CANCELACION)){
			return CREAR_CANCELACION;
		} else if (accion.equals(AWCrearFolioOficio.EDITAR_ANOTACION)){
			return EDITAR_ANOTACION;
		} else if (accion.equals(AWCrearFolioOficio.GRABAR_EDICION)){
			return CREAR_ANOTACION;
		} else if (accion.equals(AWCrearFolioOficio.ELIMINAR_ANOTACION_TEMPORAL)){
			return CREAR_ANOTACION;
		} else if (accion.equals(AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT)){
			return CREAR_ANOTACION;
		} else if( accion.equals(AWCrearFolioOficio.ELIMINAR_CIUDADANO_EDICION) ) {
            return ELIMINAR_CIUDADANO_EDICION__EVENTRESP_OK;
        } else if (accion.equals(AWCrearFolioOficio.AGREGAR_SALVEDAD_FOLIO)){
			return CREAR_FOLIO;
		}else if (accion.equals(AWCrearFolioOficio.ELIMINAR_SALVEDAD_FOLIO)){
			return CREAR_FOLIO;
		}else if (accion.equals(AWCrearFolioOficio.TERMINAR_CREACION_FOLIO)){
			return TERMINAR_CREACION_FOLIO;
		} else if (accion.equals(AWCrearFolioOficio.GUARDAR_FOLIO)){
			return TERMINAR_CREACION_FOLIO;
		}else if (AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION.equals( accion ) ) {
			return ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESPOK;
		} else if( AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION.equals( accion ) ) {
			return ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESPOK;
                } else if( AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION.equals( accion ) ) {
                              return ADMINCREARFOLIO_SAVEINFO_STEP3_BACK__EVENTRESPOK;
		} else if( AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK_ACTION.equals( accion ) ) {
                    return ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK__EVENTRESP_OK;
                } else if( AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT_ACTION.equals( accion ) ) {
                    return ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT__EVENTRESP_OK;
                } else if( AWCrearFolioOficio.ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION.equals( accion ) ) {
                    return ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT__EVENTRESP_OK;
                }


		else if (accion.equals(AWCrearFolioOficio.CANCELAR_CREACION)){
			return CANCELAR_CREACION;
		}else if (accion.equals(AWCrearFolioOficio.CONSULTA_FOLIO_COMPLEMENTACION)){
            String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            return vista;
        }else if (accion.equals(AWCrearFolioOficio.REFRESCAR_ANOTACION)){
        	return CREAR_ANOTACION;
        }else if (accion.equals(AWCrearFolioOficio.VER_ANTIGUO_SISTEMA)) {
        	return CREAR_FOLIO;
        }else if(accion.equals(AWCrearFolioOficio.VER_ANTIGUO_SISTEMA_ANOTACION)) {
        	return CREAR_ANOTACION;
        }else if(accion.equals(AWCrearFolioOficio.GUARDAR_ANOTACIONES_TEMPORALES)) {
        	return CANCELAR_ANOTACIONES;
        }else if(accion.equals(AWCrearFolioOficio.REFRESCAR_CANCELACION)) {
        	return CANCELAR_ANOTACIONES;
        }else if(accion.equals(AWCrearFolioOficio.AGREGAR_VARIOS_CIUDADANOS_CANCELACION)) {
        	return CANCELAR_ANOTACIONES;
        }else if(accion.equals(AWCrearFolioOficio.ELIMINAR_CIUDADANO_ANOTACION_CANCELACION)) {
        	return CANCELAR_ANOTACIONES;
        }else if(accion.equals(AWCrearFolioOficio.CANCELAR_ANOTACION)) {
        	return CREAR_ANOTACION;
        }else if(accion.equals(AWCrearFolioOficio.CANCELAR_CANCELACION)) {
        	return CREAR_ANOTACION;
        } else if (accion.equals(AWCrearFolioOficio.AGREGAR_SEG_ENG)){
			return CREAR_SEGREGACION_ENGLOBE;
		} else if (accion.equals(AWCrearFolioOficio.AGREGAR_DERIVADO)){
			return CREAR_SEGREGACION_ENGLOBE;
		} else if (accion.equals(AWCrearFolioOficio.ELIMINAR_DERIVADO)){
			return CREAR_SEGREGACION_ENGLOBE;
		} else if (accion.equals(AWCrearFolioOficio.AGREGAR_ENGLOBE)){
			return CREAR_SEGREGACION_ENGLOBE;
		} else if (accion.equals(AWCrearFolioOficio.ELIMINAR_ENGLOBE)){
			return CREAR_SEGREGACION_ENGLOBE;
		} else if (accion.equals(AWCrearFolioOficio.VOLVER_DERIVADO)){
			return CREAR_ANOTACION;
		} else if (accion.equals(AWCrearFolioOficio.IMPRIMIR_HOJA_DE_RUTA)){
			return CREAR_ANOTACION;
		}

        return null;
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest request) {

    }

}
