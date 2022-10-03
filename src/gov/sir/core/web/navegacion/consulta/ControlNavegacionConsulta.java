package gov.sir.core.web.navegacion.consulta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.sir.core.eventos.consulta.EvnRespConsulta;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudConsulta;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.consulta.AWConsulta;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para AWConsulta
 *
 * @author jmendez
 *
 */
public class ControlNavegacionConsulta implements ControlNavegacion {

	/**Esta constante se utiliza  para identificar una consulta simple */
	public static final String SIMPLE = "SIMPLE";

	/**Esta constante se utiliza  para identificar una consulta compleja */
	public static final String COMPLEJA = "COMPLEJA";

	/**Esta constante se utiliza  para identificar una consulta de observación de folio */
	public static final String FOLIO = "FOLIO";

	/**Esta constante se utiliza  para identificar el evento validación  satisfactoria de un folio */
	public static final String VALIDA_FOLIO_OK = "VALIDA_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento observación satisfactoria de un folio */
	public static final String OBSERVA_FOLIO_OK = "OBSERVA_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento observación errónea de un folio */
	public static final String OBSERVA_FOLIO_FAILED = "OBSERVA_FOLIO_FAILED";

	/**Esta constante se utiliza  para identificar el evento consulta satisfactoria de un folio */
	public static final String VER_FOLIO_OK = "VER_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento consulta satisfactoria de un folio
	 * para el proceso de calificación */
	public static final String CONSULTA_CALIFICACION_FOLIO_OK = "CONSULTA_CALIFICACION_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento de visualización satisfactoria
	 * de un folio en el proceso de calificación */
	public static final String VER_FOLIO_CALIFICACION_OK = "VER_FOLIO_CALIFICACION_OK";

	public static final String VER_FOLIO_CORRECCION_OK = "VER_FOLIO_CORRECCION_OK";


        // eventos de correcciones (wrappers de registro::calificacion )

        public static final String CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_OK
          = "CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_OK";

        public static final String CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_FAILED
          = "CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_FAILED";


	/**Esta constante se utiliza  para identificar el evento consulta errónea de un folio */
	public static final String VER_FOLIO_FAILED = "VER_FOLIO_FAILED";

	/**Esta constante se utiliza  para identificar el evento de visualización errónea
		 * de un folio en el proceso de calificación */
	public static final String VER_FOLIO_CALIFICACION_FAILED = "VER_FOLIO_CALIFICACION_FAILED";

	public static final String VER_FOLIO_CORRECCION_FAILED = "VER_FOLIO_CORRECCION_FAILED";


	/**Esta constante se utiliza  para identificar el evento de ocurrencia de resultados en una consulta compleja */
	public static final String RESULTADO_CONSULTA_COMPLEJA = "RESULTADO_CONSULTA_COMPLEJA";

        /**Esta constante se utiliza para identificar el evento de ocurrencia de resultados en una consulta simple */
        public static final String RESULTADO_CONSULTA_SIMPLE = "RESULTADO_CONSULTA_SIMPLE";

	/**Esta constante se utiliza  para identificar el evento satisfactorio de una consulta
	 * simple en el proceso de calificación */
	public static final String SIMPLE_CALIFICACION = "SIMPLE_CALIFICACION";

	/**Esta constante se utiliza  para identificar el evento de una consulta de folio
	* en el proceso de calificación */
	public static final String FOLIO_CALIFICACION = "FOLIO_CALIFICACION";

	/**Esta constante se utiliza  para identificar el evento de visualización satisfactoria
	 *  de un folio durante el proceso de traslado */
	public static final String VER_FOLIO_TRASLADO_OK = "VER_FOLIO_TRASLADO_OK";

	/**Esta constante se utiliza  para identificar el evento de visualización errónea
		 *  de un folio durante el proceso de traslado */
	public static final String VER_FOLIO_TRASLADO_FAILED = "VER_FOLIO_TRASLADO_FAILED";

	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	/** Constante que identifica la acción de cancelar la consultar los detalles de un folio.*/
	public static final String CANCELAR_CONSULTA = "CANCELAR_CONSULTA";

	/** Constante que identifica la acción de aceptar la consulta los detalles de un folio.*/
	public static final String ACEPTAR_CONSULTA = "ACEPTAR_CONSULTA";
	

	/** Constante que identifica la acción avanzar el turno pero con excepción en la impresión.*/
	public static final String CONFIRMACION_ERROR_IMPRESION = "CONFIRMACION_ERROR_IMPRESION";
	

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 * Este método lo que hace es la verificacion de los diferentes objetos que
	 * se encuentran el la sesion, y deacuerdo a esa verificacion manda una
	 * respuesta para que sea procesada y asi poder tener una navegacion
	 * acertada.
	 *
	 * @param request
	 * @return nombre de la acción siguiente
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = request.getParameter(WebKeys.ACCION);

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}

		if (accion.equals(AWConsulta.SELECCION_CONSULTA)) {
			String tipo_consulta = (String) sesion.getAttribute(AWConsulta.TIPO_CONSULTA);
			if (tipo_consulta == null || tipo_consulta.equals("")) {
				return AWConsulta.SELECCION_CONSULTA;
			}
            if (tipo_consulta.equals(TipoConsulta.TIPO_FOLIOS)) {
            	return ControlNavegacionConsulta.FOLIO;
            } else if (tipo_consulta.equals(TipoConsulta.TIPO_COMPLEJO)) {
            	return ControlNavegacionConsulta.COMPLEJA;
            } else if (tipo_consulta.equals(TipoConsulta.TIPO_SIMPLE)) {
            	return ControlNavegacionConsulta.SIMPLE;
            } else if (tipo_consulta.equals(TipoConsulta.TIPO_FOLIOS_CALIFICACION)) {
            	return ControlNavegacionConsulta.FOLIO_CALIFICACION;
            } else if (tipo_consulta.equals(TipoConsulta.TIPO_SIMPLE_CALIFICACION)) {
            	return ControlNavegacionConsulta.SIMPLE_CALIFICACION;
            }
		}
		//Respuesta a la solicitud de una nueva consulta
		else if (accion.equals(AWConsulta.SIGUIENTE_CONSULTA)) {
			return AWConsulta.SIGUIENTE_CONSULTA;
		}
		//Respuesta a la solicitud de un intento de consulta simple
		else if (accion.equals(AWConsulta.INTENTO_SIMPLE)) {
			return AWConsulta.INTENTO_SIMPLE;
		} else if (accion.equals(AWConsulta.INTENTO_SIMPLE_CALIFICACION)) {
			return AWConsulta.INTENTO_SIMPLE_CALIFICACION;
		} else if (accion.equals(AWConsulta.SELECCIONAR_FOLIOS)) {
			return AWConsulta.INTENTO_SIMPLE;
		} else if (accion.equals(AWConsulta.VER_FOLIO_TRASLADO)) {
			if (sesion.getAttribute(AWConsulta.FOLIO) == null) {
				return ControlNavegacionConsulta.VER_FOLIO_TRASLADO_FAILED;
			}
            return ControlNavegacionConsulta.VER_FOLIO_TRASLADO_OK;
		}
		//Redirección de navegación para las consultas en el proceso de calificación
		else if (accion.equals(AWConsulta.VER_FOLIO_CALIFICACION_FOLIO)) {
			return ControlNavegacionConsulta.CONSULTA_CALIFICACION_FOLIO_OK;
		}
                else if (accion.equals(AWConsulta.VER_FOLIO_CALIFICACION)) {
                    if (sesion.getAttribute(AWConsulta.FOLIO_CALIFICACION) == null) {
                            return ControlNavegacionConsulta.VER_FOLIO_CALIFICACION_FAILED;
                    }
                    return ControlNavegacionConsulta.VER_FOLIO_CALIFICACION_OK;
		}
                else if(AWConsulta.CONSULTAS_FOLIOCORRECCION_DISPLAYBYID_ACTION.equals( accion ) ) {
                    // es un wrapper de evento calificacion
                    final String FOLIO_CORRECCION = AWConsulta.FOLIO_CALIFICACION;
                    if( null == sesion.getAttribute( FOLIO_CORRECCION ) ) {
                       return ControlNavegacionConsulta.CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_FAILED;

                    }
                    return ControlNavegacionConsulta.CONSULTAS_FOLIOCORRECCION_DISPLAYBYID__EVENTRESP_OK;
                }
                else if (accion.equals(AWConsulta.VER_FOLIO_CORRECCION)) {
			if (sesion.getAttribute(AWConsulta.FOLIO_CORRECCION) == null) {
				return ControlNavegacionConsulta.VER_FOLIO_CORRECCION_FAILED;
			}
			return ControlNavegacionConsulta.VER_FOLIO_CORRECCION_OK;

		} else if (accion.equals(AWConsulta.VOLVER)) {
				return (String) request.getSession().getAttribute(
			AWConsulta.VISTA_VOLVER);



		} else if (
			accion.equals(AWConsulta.VER_FOLIO)
				|| accion.equals(AWConsulta.CONSULTA_FOLIO_USUARIO_INTERNO)) {
			if (sesion.getAttribute(AWConsulta.FOLIO) == null) {
				return ControlNavegacionConsulta.VER_FOLIO_FAILED;
			}
            return ControlNavegacionConsulta.VER_FOLIO_OK;
		} else if (accion.equals(AWConsulta.OBSERVA_FOLIO)) {
			String numero_matricula_inmobiliaria =
				(String) sesion.getAttribute(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
			if (numero_matricula_inmobiliaria == null || numero_matricula_inmobiliaria.equals("")) {
				return ControlNavegacionConsulta.OBSERVA_FOLIO_FAILED;
			}
            return ControlNavegacionConsulta.OBSERVA_FOLIO_OK;
		}
		//Redirección para la ejecución de consultas complejas
		else if (
			accion.equals(AWConsulta.EJECUTA_COMPLEJA)
				|| accion.equals(AWConsulta.EJECUTAR_INTENTO_COMPLEJA)) {
			return ControlNavegacionConsulta.RESULTADO_CONSULTA_COMPLEJA;
		}
                //Redirección para la ejecución de consultas simples
                else if (accion.equals(AWConsulta.EJECUTA_SIMPLE) || accion.equals(AWConsulta.EJECUTAR_INTENTO_SIMPLE)) {
                        return ControlNavegacionConsulta.RESULTADO_CONSULTA_SIMPLE;

                }
		//Redirecciona la navegación de acuerdo al parámetro de sesión AWConsulta.ACCION_SIGUIENTE
		else if (accion.equals(AWConsulta.ACCION_SIGUIENTE)) {
			String accionAnterior = (String) sesion.getAttribute(AWConsulta.ACCION_SIGUIENTE);
			return (accionAnterior == null) ? AWConsulta.SELECCION_CONSULTA : accionAnterior;
		} else if (accion.equals(AWConsulta.SELECCIONAR_RESULTADOS_CONSULTA_COMPLEJA)) {
			return ControlNavegacionConsulta.RESULTADO_CONSULTA_COMPLEJA;
		} else if (accion.equals(AWConsulta.SIGUIENTE_CONSULTA_COMPLEJA)) {
			return AWConsulta.INICIA_CONSULTA_COMPLEJA;
		} else if (accion.equals(AWConsulta.INICIA_CONSULTA_COMPLEJA)) {
			return AWConsulta.INICIA_CONSULTA_COMPLEJA;
		}
		//Redirección en el caso de ocurrir una acción de configuración de la consulta compleja
		else if (accion.equals(AWConsulta.CONFIGURA_COMPLEJA)) {
			return AWConsulta.CONFIGURA_COMPLEJA;
		} else if (accion.equals(AWConsulta.VER_SELECCION_CONSULTA_COMPLEJA)) {
			return AWConsulta.VER_SELECCION_CONSULTA_COMPLEJA;
                } else if (accion.equals(AWConsulta.VER_SELECCION_CONSULTA_SIMPLE)) {
                        return AWConsulta.VER_SELECCION_CONSULTA_SIMPLE;
                } else if (accion.equals(AWConsulta.VALIDA_FOLIO)) {
			return ControlNavegacionConsulta.VALIDA_FOLIO_OK;
		}
		//Limpia la sesión en el caso de la confirmación o negación de un turno
		else if (accion.equals(AWConsulta.CONFIRMAR_TURNO) || accion.equals(AWConsulta.NEGAR_TURNO)) {
			AWConsulta accionWeb = new AWConsulta();
			accionWeb.limpiarSesion(sesion);
			return AWConsulta.CONFIRMAR_TURNO;
		}
		//Redireccióna la página de acuerdo al tipo de acción siguiente a ejecutar
		else if (accion.equals(AWConsulta.IMPRIMIR)||accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_SIMPLE)||
				accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_COMPLEJA)) {
			String resultadoImpresion = (String) sesion.getAttribute(AWConsulta.ACCION_SIGUIENTE);
			sesion.removeAttribute(AWConsulta.ACCION_SIGUIENTE);
			
			Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
			if(impreso!=null && impreso.intValue() != 0){
				return CONFIRMACION_ERROR_IMPRESION;	
			}			
			
			if (resultadoImpresion.equals(EvnRespConsulta.IMPRESION_OK)) {
				Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
				if (turno.getSolicitud() instanceof SolicitudConsulta){
					SolicitudConsulta sol=(SolicitudConsulta)turno.getSolicitud();
					if (sol.getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_SIMPLE)
							|| sol.getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_CONSTANCIA)){
						AWConsulta accionWeb = new AWConsulta();
						accionWeb.limpiarSesion(sesion);
						sesion.removeAttribute(WebKeys.TURNO);
						sesion.removeAttribute(CCiudadano.TIPODOC);
						sesion.removeAttribute(CCiudadano.IDCIUDADANO);
						sesion.removeAttribute(CCiudadano.APELLIDO1);
						sesion.removeAttribute(CCiudadano.APELLIDO2);
						sesion.removeAttribute(CCiudadano.NOMBRE);
						sesion.removeAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
						if(request.getSession().getAttribute(WebKeys.SIMPLIFICADO)!=null){
							sesion.removeAttribute(WebKeys.SIMPLIFICADO);
							return AWConsulta.CONSULTA_SIMPLIFICADA;
						}
						return AWConsulta.SELECCION_CONSULTA;
					}
				}
				return AWConsulta.CONFIRMAR_TURNO;
			}
            return AWConsulta.SOLICITAR_REIMPRESION;
		} else if (accion.equals(AWConsulta.REGRESAR_CONSULTA_CALIFICACION)) {
			return (String) request.getSession().getAttribute(WebKeys.VISTA_ORIGINADORA);
		} else if (accion.equals(AWConsulta.CONSULTAR_FOLIO)) {
			return AWConsulta.CONSULTAR_FOLIO;
		} else if (accion.equals(AWConsulta.CANCELAR_CONSULTA)) {
					return AWConsulta.CANCELAR_CONSULTA;
		} else if (accion.equals(AWConsulta.ACEPTAR_CONSULTA)) {
					return AWConsulta.ACEPTAR_CONSULTA;
		} else if (accion.equals(AWConsulta.ACEPTAR_CONSULTA_CALIFICACION)) {

			Fase fase = (Fase)sesion.getAttribute(WebKeys.FASE);
			if(CFase.CAL_ESPECIALIZADO.equals(fase.getID())){
				return AWConsulta.ACEPTAR_CONSULTA_ESPECIALIZADO;
			}else if(CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
                return AWConsulta.ACEPTAR_CONSULTA_CORRECCION;
            }
			return AWConsulta.ACEPTAR_CONSULTA_CALIFICACION;
		}else if (accion.equals(AWConsulta.ACEPTAR_CONSULTA_CALIFICACION_FOLIO)) {
			return AWConsulta.ACEPTAR_CONSULTA_CALIFICACION_FOLIO;
		}

		return null;
	}

	/**
	 * Finalización de la navegación
	 *
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {
	}

}
