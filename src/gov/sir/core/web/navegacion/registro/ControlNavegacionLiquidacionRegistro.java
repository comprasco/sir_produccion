package gov.sir.core.web.navegacion.registro;

import gov.sir.core.negocio.modelo.constantes.CLiquidacionTurnoRegistro;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.eventos.registro.EvnRespLiquidacionRegistro;

import javax.servlet.http.HttpServletRequest;


/**
 * @author jfrias
*/
public class ControlNavegacionLiquidacionRegistro implements ControlNavegacion {


    // edicion turno vinculado ----------------------------------------------------------
    public static final String REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK
    = EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK;

    public static final String REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK
    = EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK;
    // ----------------------------------------------------------------------------------


    /** Constante para mostrar vista de carga exitosa */
    public static final String CARGAR_OK = "CARGAR_OK";

    /** Constante para mostrar vista de asociado exitosa */
    public static final String ASOCIADO_OK = "ASOCIADO_OK";
    
    /** Constante para mostrar vista de asociado exitosa */
    public static final String ASOCIADO_EDICION_OK = "ASOCIADO_EDICION_OK";
    
    /** Constante para mostrar vista de carga de preliquidacion exitosa */
    public static final String CARGAR_PRELIQUIDACION_OK = "CARGAR_PRELIQUIDACION_OK";

    /** Constante para mostrar vista de carga de documentos exitosa */
    public static final String CARGAR_DOCUMENTOS_OK = "CARGAR_DOCUMENTOS_OK";

    /** Constante para mostrar vista de carga fallida */
    public static final String CARGAR_FAILED = "CARGAR_FAILED";

    /** Constante que indica que el proceso de liquidación fué exitoso */
    public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que se estaba editando una liquidación*/
	public static final String EDITAR_LIQUIDACION_OK = "EDITAR_LIQUIDACION_OK";
	
	/** Constante que indica que se estaba editando una liquidación*/
	public static final String EDITAR_LIQUIDACION_A_CAJERO_OK = "EDITAR_LIQUIDACION_A_CAJERO_OK";

	/** Constante que indica que el proceso de liquidación fué exitoso, y se desea continuar con el pago*/
	public static final String LIQUIDACION_CONTINUAR_OK = "LIQUIDACION_CONTINUAR_OK";

    /** Constante que indica que el proceso de liquidación no fué exitoso */
    public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

    /**
     * Constante que indica que el proceso de inserción de una matrícula a la
     * solicitud fue exitoso
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

    /** Constante para mostrar vista de preveservar existosa */
    public static final String PRESERVAR_OK = "PRESERVAR_OK";

	/** Constante para mostrar vista turno.registro.liquidacion.view cuando se recargó la vista */
	public static final String PRESERVAR_INFO_LIQUIDACION_OK = "PRESERVAR_INFO_LIQUIDACION_OK";

    /** Constante para mostrar vista de liquidación existosa */
    public static final String LIQUIDAR_REGISTRO_OK = "LIQUIDAR_REGISTRO_OK";

    /** Constante para mostrar vista de agregar acto existosa */
    public static final String AGREGAR_ACTO_OK = "AGREGAR_ACTO_OK";
    
    /** Constante para mostrar vista de carga exitosa del valor de los derechos de un acto */
    public static final String CARGAR_VALOR_DERECHOS_OK = "CARGAR_VALOR_DERECHOS_OK";
    
    /** Constante para mostrar vista de agregar acto existosa */
    public static final String AGREGAR_ACTO_PRELIQUIDACION_OK = "AGREGAR_ACTO_PRELIQUIDACION_OK";
    
    /** Constante para mostrar vista de eliminar acto existosa */
    public static final String ELIMINAR_ACTO_PRELIQUIDACION_OK = "ELIMINAR_ACTO_PRELIQUIDACION_OK";

    /** Constante para mostrar vista de eliminar acto existosa */
    public static final String ELIMINAR_ACTO_OK = "ELIMINAR_ACTO_OK";

    /** Constante para mostrar vista de antiguo sistema */
    public static final String VER_ANTIGUO_SISTEMA = "VER_ANTIGUO_SISTEMA";

    /** Constante para mostrar vista de turno anterior válido */
    public static final String TURNO_ANTERIOR_VALIDADO = "TURNO_ANTERIOR_VALIDADO";

    /** Constante para mostrar vista de eliminar turno anterior */
    public static final String ELIMINAR_TURNO_ANTERIOR = "ELIMINAR_TURNO_ANTERIOR";

    /** Constante que indica que el proceso de solicitud fué exitoso */
    public static final String SOLICITUD_OK = "SOLICITUD_OK";
    
	/** Constante que indica que se creo el turno,pero fallo en la impresión. */
	public static final String CONFIRMACION_ERROR_IMPRESION = "CONFIRMACION_ERROR_IMPRESION";    
    
	/**
	 * Constante que indica que se cancelo la el proceso de liquidacion
	 */
	public static final String CANCELADO = "CANCELADO";
	
	/**
	 * Constante que diferencia creacion de turno de liquiador registro de cajero registro*/
	public static final String CREAR_TURNO_LIQUIDADOR = "CREAR_TURNO_LIQUIDADOR";
	

    /**
     *
     */
    public ControlNavegacionLiquidacionRegistro() {
        super();
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
     */
    public void doStart(HttpServletRequest arg0) {
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
     */
    public String procesarNavegacion(HttpServletRequest request)
        throws ControlNavegacionException {
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                "La accion enviada por la accion web no es valida");
        }

        Boolean esEdicion = (Boolean)request.getSession().getAttribute(AWLiquidacionRegistro.ES_EDICION_LIQUIDACION);

        if( AWLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM_ACTION.equals( accion ) ) {
            return REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK;
        }
        else if( AWLiquidacionRegistro.REGISTRO_VINCULARTURNO_DELITEM_ACTION.equals( accion )  ) {
            return REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK;
        }
        else if (accion.equals(AWLiquidacionRegistro.LIQUIDAR_REGISTRO)) {
            if (request.getSession().getAttribute(WebKeys.TURNO) == null) {
                return LIQUIDAR_REGISTRO_OK;
            }

            return SOLICITUD_OK;
        } else if (accion.equals(AWLiquidacionRegistro.AGREGAR) || accion.equals(AWLiquidacionRegistro.AGREGAR_MIG_INC)) {
            return AGREGAR_OK;
        } else if (accion.equals(AWLiquidacionRegistro.PRELIQUIDAR)) {
            return AWLiquidacionRegistro.PRELIQUIDAR;    
        } else if (accion.equals(AWLiquidacionRegistro.REGRESAR_LIQUIDACION)) {
            return AWLiquidacionRegistro.REGRESAR_LIQUIDACION;      
        } else if (accion.equals(AWLiquidacionRegistro.REGRESAR_CALIFICACION)) {
            return AWLiquidacionRegistro.REGRESAR_CALIFICACION;  
		} else if (accion.equals(AWLiquidacionRegistro.COMENZAR_PRELIQUIDACION)) {
			return AWLiquidacionRegistro.COMENZAR_PRELIQUIDACION; 
        } else if (accion.equals(AWLiquidacionRegistro.ELIMINAR) || accion.equals(AWLiquidacionRegistro.ELIMINAR_MIG_INC)) {
            return ELIMINAR_OK;
        } else if (accion.equals(AWLiquidacionRegistro.PRESERVAR_INFO)) {
            return PRESERVAR_OK;
        } else if (accion.equals(AWLiquidacionRegistro.PRESERVAR_INFO_LIQUIDAR)) {
        	if(esEdicion!=null && esEdicion.booleanValue()){
				return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
        	}
			return PRESERVAR_INFO_LIQUIDACION_OK;
		} else if (accion.equals(AWLiquidacionRegistro.AGREGAR_ACTO)) {
			if(esEdicion!=null && esEdicion.booleanValue()){
				return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
			}
            return AGREGAR_ACTO_OK;
        } else if (accion.equals(AWLiquidacionRegistro.CARGAR_VALOR_DERECHOS)) {
			if(esEdicion!=null && esEdicion.booleanValue()){
				return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
			}
            return CARGAR_VALOR_DERECHOS_OK;
        }else if (accion.equals(AWLiquidacionRegistro.AGREGAR_ACTO_PRELIQUIDACION)) {
            return AGREGAR_ACTO_PRELIQUIDACION_OK;
        }else if (accion.equals(AWLiquidacionRegistro.ELIMINAR_ACTO_PRELIQUIDACION)) {
            return ELIMINAR_ACTO_PRELIQUIDACION_OK;
        }else if (accion.equals(AWLiquidacionRegistro.ELIMINAR_ACTO)) {
			if(esEdicion!=null && esEdicion.booleanValue()){
				return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
			}
            return ELIMINAR_ACTO_OK;
        } else if (accion.equals(AWLiquidacionRegistro.LIQUIDAR)) {
        	
			Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
			if(impreso!=null && impreso.intValue() != 0){
				return CONFIRMACION_ERROR_IMPRESION;
			}                 	
        	
            return LIQUIDACION_OK;
        } else if (accion.equals(AWLiquidacionRegistro.LIQUIDAR_CONTINUAR)) {
        	Boolean esCajero = (Boolean)request.getSession().getAttribute(WebKeys.ES_CAJERO);
        	if(esCajero!=null && esCajero.booleanValue()){
				return LIQUIDACION_CONTINUAR_OK;
        	}else{
				return LIQUIDACION_OK;
        	}
		} else if (accion.equals(AWLiquidacionRegistro.EDITAR_LIQUIDACION)) {
			
			Boolean vieneDeCajero = (Boolean)request.getSession().getAttribute(AWLiquidacionRegistro.VIENE_DE_CAJERO);
			if(vieneDeCajero!=null && vieneDeCajero.booleanValue()){
				return EDITAR_LIQUIDACION_A_CAJERO_OK;
			}else{
				return EDITAR_LIQUIDACION_OK;
			}
			
		} else if (accion.equals(AWLiquidacionRegistro.CARGAR_DERECHOS)) {
			if(esEdicion!=null && esEdicion.booleanValue()){
				return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
			}
            return CARGAR_OK;
		} else if (accion.equals(AWLiquidacionRegistro.CARGAR_DERECHOS_PRELIQUIDACION)) {
            return CARGAR_PRELIQUIDACION_OK;
		} else if (accion.equals(AWLiquidacionRegistro.CARGAR_DOCUMENTOS)) {
            return CARGAR_DOCUMENTOS_OK;
        } else if (accion.equals(AWLiquidacionRegistro.VER_ANTIGUO_SISTEMA)) {
            return VER_ANTIGUO_SISTEMA;
        } else if (accion.equals(AWLiquidacionRegistro.VALIDAR_TURNO_ANTERIOR)) {
            return TURNO_ANTERIOR_VALIDADO;
        } else if (accion.equals(AWLiquidacionRegistro.ELIMINAR_TURNO_ANTERIOR)) {
            return ELIMINAR_TURNO_ANTERIOR;
        } else if (
                accion.equals(
                    AWLiquidacionRegistro.ADICIONAR_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                    AWLiquidacionRegistro.ELIMINA_CERTIFICADO_ASOCIADO)) {
            return ASOCIADO_OK;
        } else if (accion.equals(
                    AWLiquidacionRegistro.NUEVO_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                    AWLiquidacionRegistro.AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                    AWLiquidacionRegistro.ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO) ||
			accion.equals(
					AWLiquidacionRegistro.AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA) ||
			accion.equals(
					AWLiquidacionRegistro.AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION) ||
			accion.equals(
					AWLiquidacionRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA) ||
			accion.equals(
					AWLiquidacionRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION)
                    ) {
            //	return AWLiquidacionRegistro.NUEVO_CERTIFICADO_ASOCIADO;
            return ASOCIADO_OK;
        } else if (accion.equals(AWLiquidacionRegistro.AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION) ||
                accion.equals(AWLiquidacionRegistro.ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION) ||
                accion.equals(AWLiquidacionRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION) ||
                accion.equals(AWLiquidacionRegistro.AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION)) {
            return ASOCIADO_EDICION_OK;
        } else if (accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD)) {
            return AWLiquidacionRegistro.BUSCAR_SOLICITUD;
        } else if (accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION)) {
			return AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION;
		} else if (accion.equals(AWLiquidacionRegistro.VALIDAR_SOLICITUD)) {
            return AWLiquidacionRegistro.VALIDAR_SOLICITUD;
        } else if (accion.equals(
                    AWLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            return AWLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO;
        } else if (accion.equals(AWLiquidacionRegistro.CREAR_TURNO)) {
            
			Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
			if(impreso!=null && impreso.intValue() != 0){
				return CONFIRMACION_ERROR_IMPRESION;
			}
			//SIR-86
			if(request.getSession().getAttribute(WebKeys.LIQUIDADOR) != null
					&& request.getSession().getAttribute(WebKeys.LIQUIDADOR).equals(CLiquidacionTurnoRegistro.LIQUIDADOR_REGISTRO)){
            	request.getSession().setAttribute(WebKeys.LIQUIDADOR, null);
            	return CREAR_TURNO_LIQUIDADOR;
            }
            
            return AWLiquidacionRegistro.CREAR_TURNO;
            
        } else if (accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD_PAGO)) {
            return AWLiquidacionRegistro.BUSCAR_SOLICITUD_PAGO;
        } else if (accion.equals(AWLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return AWLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS;
        } else if (accion.equals(AWLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return AWLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS;
        } else if (accion.equals(AWLiquidacionRegistro.REFRESCAR_AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO)) {
            return AWLiquidacionRegistro.REFRESCAR_AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO;
        } else if (accion.equals(AWLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO)) {
            return AWLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO;
        } else if (accion.equals(AWLiquidacionRegistro.REFRESH)) {
            return LIQUIDAR_REGISTRO_OK;
        } else if (accion.equals(AWLiquidacionRegistro.REMOVER_INFO)) {
        	return CANCELADO;
        } else {
            return null;
        }
    }



    /**
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {
    }
}
