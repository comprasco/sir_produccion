package gov.sir.core.web.navegacion.registro;

import java.util.List;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.eventos.registro.EvnRespCalificacionReproduccionSellos;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author gvillal
 * @author ddiaz
 * @author dsalas
 * @author jfrias
*/
public class ControlNavegacionCalificacion implements ControlNavegacion {
    
	public static final String CALIFICAR_FOLIO = "CALIFICAR_FOLIO";
        public static final String REVISOR_CONFRONTACION = "REVISOR_CONFRONTACION";
	public static final String PRESERVAR_INFO= "PRESERVAR_INFO";
	public static final String PRESERVAR_INFO_CANCELACION= "PRESERVAR_INFO_CANCELACION";
	public static final String PRESERVAR_INFO_ANOTACION= "PRESERVAR_INFO_ANOTACION";
	public static final String AGREGAR_CIUDADANO= "AGREGAR_CIUDADANO";
	public static final String AGREGAR_ANOTACION="AGREGAR_ANOTACION";
	public static final String EDITAR_ANOTACION="EDITAR_ANOTACION";
	public static final String EDITAR_CANCELACION="EDITAR_CANCELACION";
	public static final String CANCELAR_EDICION="CANCELAR_EDICION";
	public static final String ELIMINAR_ANOTACION_TEMPORAL="ELIMINAR_ANOTACION_TEMPORAL";
	public static final String CANCELAR_CANCELACION="CANCELAR_CANCELACION";
        public static final String CANCELAR_CANCELACION_CORRECCION="CANCELAR_CANCELACION_CORRECCION";
	public static final String CANCELAR_EDICION_CANCELACION="CANCELAR_EDICION_CANCELACION";
	public static final String GRABAR_EDICION="GRABAR_EDICION";
	public static final String GRABAR_EDICION_CANCELACION="GRABAR_EDICION_CANCELACION";
	public static final String ELIMINAR_ANOTACION="ELIMINAR_ANOTACION";
	public final static String CREAR_ANOTACION="CREAR_ANOTACION";
	public static final String AGREGAR_ANOTACION_CREACION_FOLIO="AGREGAR_ANOTACION_CREACION_FOLIO";
	public static final String ELIMINAR_ANOTACION_CREACION_FOLIO="ELIMINAR_ANOTACION_CREACION_FOLIO";
	public static final String ACEPTAR_ANOTACIONES_FOLIO="ACEPTAR_ANOTACIONES_FOLIO";
	public static final String ENGLOBAR_MATRICULAS="ENGLOBAR_MATRICULAS";
	public static final String CARGAR_ANOTACIONES="CARGAR_ANOTACIONES";
	public static final String ENGLOBAR="ENGLOBAR";
	public static final String AGREGAR_COMPLEMENTACION="AGREGAR_COMPLEMENTACION";
	public static final String GRABACION_TEMPORAL_ANOTACION="GRABACION_TEMPORAL_ANOTACION";
	public static final String ELIMINAR_COMPLEMENTACION="ELIMINAR_COMPLEMENTACION";
	public static final String TERMINAR_ANOTACIONES_ENGLOBE="TERMINAR_ANOTACIONES_ENGLOBE";
	public static final String SEGREGACION_ANOTACION="SEGREGACION_ANOTACION";
	public static final String SEGREGACION_HERENCIA="SEGREGACION_HERENCIA";
	public static final String CANCELAR_ANOTACION="CANCELAR_ANOTACION";
	public static final String CANCELAR_ANOTACION_CORRECCION="CANCELAR_ANOTACION_CORRECCION";
	public static final String CANCELAR_ANOTACION_CORRECCION_ESPECIALIZADO="CANCELAR_ANOTACION_CORRECCION_ESPECIALIZADO";
	public static final String CANCELAR ="CANCELAR";
	public final static String ENVIAR_CORRECCION="ENVIAR_CORRECCION";
	public final static String ENVIAR_CALIFICACION="ENVIAR_CALIFICACION";
	public final static String CONFIRMACION="CONFIRMACION";
	public final static String CONFIRMACION_ERROR_IMPRESION="CONFIRMACION_ERROR_IMPRESION";
	public final static String AVANZAR="AVANZAR";
	public static final String GRABAR_ANOTACIONES_TEMPORAL_FOLIO="GRABAR_ANOTACIONES_TEMPORAL_FOLIO";
	public final static String TOMAR_TURNO="TOMAR_TURNO";
	public final static String DESASOCIAR_FOLIOS="DESASOCIAR_FOLIOS";
	public final static String CREAR_FOLIO_ENGLOBE="CREAR_FOLIO_ENGLOBE";
	public static final String ELIMINAR_CIUDADANO="ELIMINAR_CIUDADANO";
	public final static String AGREGAR_FOLIO_DERIVADO = "AGREGAR_FOLIO_DERIVADO";
	public final static String ELIMINAR_FOLIO_DERIVADO = "ELIMINAR_FOLIO_DERIVADO";
	public static final String SEGREGACION_MASIVA = "SEGREGACION_MASIVA";
	public static final String ACEPTAR_CORRECCION="ACEPTAR_CORRECCION";
	public static final String NEGAR_CORRECCION="NEGAR_CORRECCION";
	public final static String REFRESCAR_ANOTACION="REFRESCAR_ANOTACION";
	public final static String REFRESCAR_CANCELACION="REFRESCAR_CANCELACION";
	public final static String REFRESCAR_EDICION_CANCELACION="REFRESCAR_EDICION_CANCELACION";
	public final static String REFRESCAR_ANOTACION_SEGREGACION="REFRESCAR_ANOTACION_SEGREGACION";
	public final static String REFRESCAR_EDICION="REFRESCAR_EDICION";
	public final static String VALIDAR_CIUDADANO="VALIDAR_CIUDADANO";
	public final static String MOSTRAR="MOSTRAR";
	public final static String REGRESAR_CALIFICACION="REGRESAR_CALIFICACION";
	public final static String CREAR_ANOTACION_CANCELACION = "CREAR_ANOTACION_CANCELACION";

	//Delegar hacia confrontacion
	public final static String DELEGAR_CONFRONTACION="DELEGAR_CONFRONTACION";

	//	Varios ciudadanos Edicion
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION= "AGREGAR_VARIOS_CIUDADANOS_EDICION";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";
	/** Constante que indica que se va validar un ciudadano de la anotación */
	public static final String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";
	/** Constante que indica que se va a consultar los ultimos propietarios de un folio*/
	public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_ANOTACION_REGISTRO = "GUARDAR_PROPIETARIOS_ANOTACION_REGISTRO";
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_EDITAR_ANOTACION_REGISTRO = "GUARDAR_PROPIETARIOS_EDITAR_ANOTACION_REGISTRO";
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_ANOTACION_CANCELACION_REGISTRO = "GUARDAR_PROPIETARIOS_ANOTACION_CANCELACION_REGISTRO";
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_EDITAR_CANCELACION_REGISTRO = "GUARDAR_PROPIETARIOS_EDITAR_CANCELACION_REGISTRO";
	
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_CORRECION = "GUARDAR_PROPIETARIOS_CORRECION";
	/** Constante que indica que se cancela el proceso de consultar los ultimos propietarios**/
	public static final String CANCELAR_PROPIETARIOS_CORRECCION = "CANCELAR_PROPIETARIOS_CORRECCION";
        public static final String VISUALIZAR_PDF="VISUALIZAR_PDF";
	//End: Varios ciudadanos Edicion
	//Varios ciudadanos
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS= "AGREGAR_VARIOS_CIUDADANOS";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS";
	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";
	//End: Varios ciudadanos
	//Varios ciudadanos segregacion
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_SEGREGACION= "AGREGAR_VARIOS_CIUDADANOS_SEGREGACION";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";
	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION = "ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION";
	//End: Varios ciudadanos segregacion
	//Varios ciudadanos cancelacion
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_CANCELACION= "AGREGAR_VARIOS_CIUDADANOS_CANCELACION";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_ANOTACION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_CANCELACION";
	/** Constante que indica que se va validar un ciudadano de la anotación */
	public static final String VALIDAR_CIUDADANO_CANCELACION = "VALIDAR_CIUDADANO_CANCELACION";
	//End: Varios ciudadanos CANCELACION
	//Varios ciudadanos cancelacion
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION= "AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION";
	/** Constante que indica que se va validar un ciudadano de la anotación */
	public static final String VALIDAR_CIUDADANO_EDICION_CANCELACION = "VALIDAR_CIUDADANO_EDICION_CANCELACION";
	//End: Varios ciudadanos CANCELACION
	
	
	public static final String PROCESO_FAILED = "PROCESO_FAILED";
	public static final String PROCESO_OK = "PROCESO_OK";
	public static final String FASES_FAILED = "FASES_FAILED";


        // reproduccion de sellos ---------------------------------------------------
        public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK
        = EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK;

        public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_ERROR
        = EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_ERROR;

        public static final String CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK
        = EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK;

        public static final String CALIFICACION_REIMPRESIONSELLOS_RESET__EVENTRESP_OK
        = EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK;

        // -----------------------------------------------------------------------------


	/**
	 * Crea una instancia del control de navegacion
	 */
	public ControlNavegacionCalificacion() {
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
			if (accion.equals(AWCalificacion.PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION)) {
					Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
					List fases = (List) request.getSession().getAttribute(WebKeys.LISTA_FASES);

					if ((proceso == null) || proceso.getIdProceso() == 0) {
						return PROCESO_FAILED;
					} else if (fases != null) {
						return PROCESO_OK;
					} else {
						return FASES_FAILED;
					}
			}
			else if (accion.equals(AWCalificacion.CALIFICAR_FOLIO)) {
					return CALIFICAR_FOLIO;
			}
                        else if (accion.equals(AWCalificacion.VISUALIZAR_PDF)) {
					return VISUALIZAR_PDF;
			}
                        // reproduccion sellos -----------------------------------------------------
                        // tener en cuenta que los eventos de error se mapean como excepciones
                        else if( AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION.equals( accion ) ) {
                            return CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK;
                        }
                        else if( AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION.equals( accion ) ) {
                            return CALIFICACION_REIMPRESIONSELLOS_RESET__EVENTRESP_OK;
                        }
                        else if( AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION.equals( accion ) ) {
                            return CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK;
                        }else if( AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_MATRICULA.equals( accion ) ) {
							return CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK;
						}

                        // --------------------------------------------------------







                        else if (accion.equals(AWCalificacion.PRESERVAR_INFO)){
				return PRESERVAR_INFO;
			} else if (accion.equals(AWCalificacion.PRESERVAR_INFO_CANCELACION)){
				return PRESERVAR_INFO_CANCELACION;
			} else if (accion.equals(AWCalificacion.PRESERVAR_INFO_ANOTACION)){
				return PRESERVAR_INFO_ANOTACION;
			} else if (accion.equals(AWCalificacion.AGREGAR_CIUDADANO)){
				String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
				return ultimaVista;
			} else if (accion.equals(AWCalificacion.MOSTRAR)){
				
				WebEnglobe webEnglobe = (WebEnglobe)request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);				
				if(webEnglobe!=null){
					return ENGLOBAR;	
				}
				
				WebSegregacion webSegregacion = (WebSegregacion)request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
				if(webSegregacion!=null){
					return SEGREGACION_ANOTACION;	
				}
								
				return MOSTRAR;
				
		    } else if (accion.equals(AWCalificacion.ELIMINAR_ANOTACION_TEMPORAL)){
				return ELIMINAR_ANOTACION_TEMPORAL;
            } else if (accion.equals(AWCalificacion.VALIDAR_TURNO_PARA_CALIFICACION)){				
				return TOMAR_TURNO;
			} else if (accion.equals(AWCalificacion.CREAR_ANOTACION)){
				return CREAR_ANOTACION;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CANCELACION)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CANCELACION;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION;
			}else if (accion.equals(AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_ENGLOBE)){
				return AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_ENGLOBE;
			}else if (accion.equals(AWCalificacion.VALIDAR_CIUDADANO)){
				return VALIDAR_CIUDADANO;
			} else if (accion.equals(AWCalificacion.AGREGAR_ANOTACION)){
				return AGREGAR_ANOTACION;
			} else if (accion.equals(AWCalificacion.ELIMINAR_ANOTACION)){
				return ELIMINAR_ANOTACION;
			} else if (accion.equals(AWCalificacion.AGREGAR_ANOTACION_CREACION_FOLIO)){
				return AGREGAR_ANOTACION_CREACION_FOLIO;
			} else if (accion.equals(AWCalificacion.ELIMINAR_ANOTACION_CREACION_FOLIO)){
				return ELIMINAR_ANOTACION_CREACION_FOLIO;
			} else if (accion.equals(AWCalificacion.EDITAR_ANOTACION)){
				return EDITAR_ANOTACION;
			} else if (accion.equals(AWCalificacion.EDITAR_CANCELACION)){
				return EDITAR_CANCELACION;
			} else if (accion.equals(AWCalificacion.GRABAR_EDICION)){
				return GRABAR_EDICION;
			} else if (accion.equals(AWCalificacion.GRABAR_EDICION_CANCELACION)){
				return GRABAR_EDICION_CANCELACION;
			} else if (accion.equals(AWCalificacion.REFRESCAR_ANOTACION)){
				return REFRESCAR_ANOTACION;
			} else if (accion.equals(AWCalificacion.REFRESCAR_CANCELACION) ){
				return REFRESCAR_CANCELACION;
			} else if (accion.equals(AWCalificacion.GUARDAR_ANOTACIONES_TEMPORALES)){
				return CREAR_ANOTACION_CANCELACION;
			} else if (accion.equals(AWCalificacion.REFRESCAR_EDICION_CANCELACION)){
				return REFRESCAR_EDICION_CANCELACION;
			} else if (accion.equals(AWCalificacion.VALIDAR_CIUDADANO_EDICION)){
				return VALIDAR_CIUDADANO_EDICION;
			} else if (accion.equals(AWCalificacion.REFRESCAR_EDICION)){
				return REFRESCAR_EDICION;
			}
			else if (accion.equals(AWCalificacion.CANCELAR_EDICION)){
				return CANCELAR_EDICION;
			}else if (accion.equals(AWCalificacion.CANCELAR_EDICION_CANCELACION)){
				return CANCELAR_EDICION_CANCELACION;
			}else if (accion.equals(AWCalificacion.CANCELAR_CANCELACION)){
                Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
                if(CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
                    return CANCELAR_CANCELACION_CORRECCION;
                }
				return CANCELAR_CANCELACION;
			}else if (accion.equals(AWCalificacion.GRABAR_EDICION)){
				return GRABAR_EDICION;
			}else if (accion.equals(AWCalificacion.ACEPTAR_ANOTACIONES_FOLIO)){
				return ACEPTAR_ANOTACIONES_FOLIO;
			}else if (accion.equals(AWCalificacion.CARGAR_ANOTACIONES)){
				return CARGAR_ANOTACIONES;
			}else if (accion.equals(AWCalificacion.ENGLOBAR_MATRICULAS)){
				return ENGLOBAR_MATRICULAS;
			}else if (accion.equals(AWCalificacion.ENGLOBAR)){
				return ENGLOBAR;
			}else if (accion.equals(AWCalificacion.AGREGAR_COMPLEMENTACION)){
				return AGREGAR_COMPLEMENTACION;
			}else if (accion.equals(AWCalificacion.GRABACION_TEMPORAL_ANOTACION)){
				return GRABACION_TEMPORAL_ANOTACION;
			}else if (accion.equals(AWCalificacion.ELIMINAR_COMPLEMENTACION)){
				return ELIMINAR_COMPLEMENTACION;
			}else if (accion.equals(AWCalificacion.ELIMINAR_COMPLEMENTACION)){
				return TERMINAR_ANOTACIONES_ENGLOBE;
			}else if (accion.equals(AWCalificacion.TERMINAR_ANOTACIONES_ENGLOBE)){
				return TERMINAR_ANOTACIONES_ENGLOBE;
			}else if (accion.equals(AWCalificacion.SEGREGACION_ANOTACION)){
				return SEGREGACION_ANOTACION;
			}else if (accion.equals(AWCalificacion.SEGREGACION_HERENCIA)){
				return SEGREGACION_HERENCIA;
			}else if (accion.equals(AWCalificacion.CANCELAR_ANOTACION)){
				Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
				if(fase.getID().equals(CFase.COR_CORRECCION)){
					return CANCELAR_ANOTACION_CORRECCION;
				}
				if(fase.getID().equals(CFase.COR_USUARIO_ESPECIALIZADO)){
					return CANCELAR_ANOTACION_CORRECCION_ESPECIALIZADO;
				}
				return CANCELAR_ANOTACION;
			}else if (accion.equals(AWCalificacion.CANCELAR)){
				return CANCELAR;
			}else if (accion.equals(AWCalificacion.ENVIAR_CORRECCION_ENCABEZADO)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.AVANZAR)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.INSCRIPCION_PARCIAL)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.DEVOLVER)){
				
				Boolean impreso = (Boolean)request.getSession().getAttribute(WebKeys.NOTA_DEVOLUTIVA_IMPRESA);
				if(impreso!=null && !impreso.booleanValue()){
					return CONFIRMACION_ERROR_IMPRESION;	
				}
				return CONFIRMACION;				
				
			}else if (accion.equals(AWCalificacion.CONFIRMAR)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.AGREGAR_FOLIO_DERIVADO)){
				return AGREGAR_FOLIO_DERIVADO;
			}else if (accion.equals(AWCalificacion.ELIMINAR_FOLIO_DERIVADO)){
				return ELIMINAR_FOLIO_DERIVADO;
			}else if(accion.equals(AWCalificacion.SEGREGAR_MASIVO)){
				return SEGREGACION_MASIVA;

			//				Varios ciudadanos Edicion
		    } else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION)){
			    return AGREGAR_VARIOS_CIUDADANOS_EDICION;
		    } else if (accion.equals(AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
			    return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION;
		    } else if (accion.equals(AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
		   	    return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION;
		    }else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION)){
			    return ELIMINAR_CIUDADANO_ANOTACION;
		    //End: Varios ciudadanos Edicion


			//Varios ciudadanos
			} else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)){
				return AGREGAR_VARIOS_CIUDADANOS;
			} else if (accion.equals(AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)){
				return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS;
			} else if (accion.equals(AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS)){
				return REMOVER_1_REGISTRO_TABLA_CIUDADANOS;
			}else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_EDICION)){
				return ELIMINAR_CIUDADANO_EDICION;
			}else if (accion.equals(AWCalificacion.GET_ULTIMOS_PROPIETARIOS)){
				return GET_ULTIMOS_PROPIETARIOS;	
			}else if (accion.equals(AWCalificacion.CANCELAR_PROPIETARIOS)){
				String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
				
				if (paramVistaAnterior.equals("ANOTACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_ANOTACION_REGISTRO;	
				} else if (paramVistaAnterior.equals("EDITAR_ANOTACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_EDITAR_ANOTACION_REGISTRO;
				} else if (paramVistaAnterior.equals("ANOTACION_CANCELACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_ANOTACION_CANCELACION_REGISTRO;
				} else if (paramVistaAnterior.equals("EDITAR_CANCELACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_EDITAR_CANCELACION_REGISTRO;
				} 
				
				
				/*Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
                if(CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
                    return CANCELAR_PROPIETARIOS_CORRECCION;
                }
				return CANCELAR_PROPIETARIOS;*/
			}else if (accion.equals(AWCalificacion.GUARDAR_PROPIETARIOS)){
				String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
				
				if (paramVistaAnterior.equals("ANOTACION_REGISTRO")){
						return GUARDAR_PROPIETARIOS_ANOTACION_REGISTRO;	
				} else if (paramVistaAnterior.equals("EDITAR_ANOTACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_EDITAR_ANOTACION_REGISTRO;
				} else if (paramVistaAnterior.equals("ANOTACION_CANCELACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_ANOTACION_CANCELACION_REGISTRO;
				} else if (paramVistaAnterior.equals("EDITAR_CANCELACION_REGISTRO")){
					return GUARDAR_PROPIETARIOS_EDITAR_CANCELACION_REGISTRO;
				} 
				//Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
				
				/*if(CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
                    return GUARDAR_PROPIETARIOS_CORRECION;
                }
				return GUARDAR_PROPIETARIOS;	*/
			//End: Varios ciudadanos

            //Varios ciudadanos Cancelacion
		    } else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_CANCELACION)){
			    return AGREGAR_VARIOS_CIUDADANOS_CANCELACION;
		    } else if (accion.equals(AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)){
			    return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION;
		    } else if (accion.equals(AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)){
		   	    return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION;
		    }else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_CANCELACION)){
			    return ELIMINAR_CIUDADANO_ANOTACION_CANCELACION;
		    }else if (accion.equals(AWCalificacion.VALIDAR_CIUDADANO_CANCELACION)){
				return VALIDAR_CIUDADANO_CANCELACION;
		    //End: Varios ciudadanos CANCELACION

			//Varios ciudadanos Cancelacion
		    } else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION)){
			    return AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION;
		    } else if (accion.equals(AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION)){
			    return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION;
		    } else if (accion.equals(AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION)){
		   	    return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION;
		    }else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION)){
			    return ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION;
		    }else if (accion.equals(AWCalificacion.VALIDAR_CIUDADANO_EDICION_CANCELACION)){
				return VALIDAR_CIUDADANO_EDICION_CANCELACION;
		    //End: Varios ciudadanos CANCELACION

			//Varios ciudadanos segregacion
			} else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)){
				return AGREGAR_VARIOS_CIUDADANOS_SEGREGACION;
			} else if (accion.equals(AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)){
				return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION;
			} else if (accion.equals(AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)){
				return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION;
			}else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION)){
				return ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION;
			//End: Varios ciudadanos segregacion

			}else if (accion.equals(AWCalificacion.CREAR_FOLIO_ENGLOBE)){
				return CREAR_FOLIO_ENGLOBE;
			}else if (accion.equals(AWCalificacion.GRABAR_ANOTACIONES_TEMPORAL_FOLIO)){
				return GRABAR_ANOTACIONES_TEMPORAL_FOLIO;
			}else if (accion.equals(AWCalificacion.TOMAR_TURNO)){
				return TOMAR_TURNO;
			}else if (accion.equals(AWCalificacion.DESASOCIAR_FOLIOS)){
				return DESASOCIAR_FOLIOS;
			}else if (accion.equals(AWCalificacion.VER_SALVEDAD)) {
				String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
				return ultimaVista;
			}else if (accion.equals(AWCalificacion.NEGAR_CORRECCION)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.ACEPTAR_CORRECCION)){
				return CONFIRMACION;
				//daniel SIR -61
			}else if(accion.equals(AWCalificacion.DEVOLVER_FIRMA_TESTAMENTO)){
				return CONFIRMACION;
			}else if (accion.equals(AWCalificacion.REGRESAR_CALIFICACION)){
				return REGRESAR_CALIFICACION;
			}else if (accion.equals(AWCalificacion.APROBAR_MAYOR_VALOR)){
				return AWCalificacion.APROBAR_MAYOR_VALOR;
			}else if (accion.equals(AWCalificacion.REGISTRAR_PAGO_EXCESO)){
				return AWCalificacion.REGISTRAR_PAGO_EXCESO;
			}else if (accion.equals(AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION)){
				return AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION;
			}else if (accion.equals(AWCalificacion.IMPRIMIR_FOLIO_TEMPORAL)){
				return AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION;
			}else if (accion.equals(AWCalificacion.OBTENER_BLOQUEO_FOLIOS_FIRMA)){
				return AWCalificacion.OBTENER_BLOQUEO_FOLIOS_FIRMA;
                        }else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION)){
				return AWCalificacion.REVISOR_CONFRONTACION;
			}else if (accion.equals(AWCalificacion.CERRAR_FOLIO)){
				return AWCalificacion.CERRAR_FOLIO;
			}else if (accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS)
			|| accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_COD_CATASTRAL)
			|| accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_DIRECCIONES)
            /**
             * @author: Cesar Ramirez
             * @change: 1245.HABILITAR.TIPO.PREDIO
             * Se añade la constante para visualizar el JSP relacionado.
             **/
            || accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_TIPO_PREDIO)
			|| accion.equals(AWCalificacion.AGREGAR_DIRECCION_CALIFICACION) 
			|| accion.equals(AWCalificacion.ELIMINAR_DIRECCION_CALIFICACION) 
			|| accion.equals(AWCalificacion.ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION) 
			){
				return CALIFICAR_FOLIO;

			}

			//DELEGAR CONFRONTACION
			else if (accion.equals(AWCalificacion.CONFRONTACION)){
							return AWCalificacion.CONFRONTACION;
			}
			
			
             //	DELEGAR DIGITACION MASIVA
			else if (accion.equals(AWCalificacion.DELEGAR_DIGITACION_MASIVA)){
	             return CONFIRMACION;
			}
			
			
			//  AVANZAR DESDE FIRMA DEL REGISTRADOR
			else if (accion.equals(AWCalificacion.FIRMA_REGISTRO_CONFIRMAR)){
                            return CONFIRMACION;
			}
                        
                        //  AVANZAR DESDE REVISOR CONFRONTACION CORRECTIVA
			else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION_CONFIRMAR)){
                            return CONFIRMACION;
			}
                        
                        //  DEVOLVER DESDE REVISOR CONFRONTACION CORRECTIVA
			else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION_DEVOLVER)){
                            return CONFIRMACION;
			}
                        
                       
			//  AVANZAR DESDE ENTREGA DE REGISTRO.
			else if (accion.equals(AWCalificacion.ENTREGAR_REGISTRO)){
	             return CONFIRMACION;
			}

            //  AVANZAR DESDE ENTREGA DE REGISTRO MULTIPLE SELECCION.
            // JALCAZAR caso MANTIS 0002225
            else if (accion.equals(AWCalificacion.ENTREGAR_REGISTRO_MULT)){
	             return CONFIRMACION;
            } 
			return null;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}

}
