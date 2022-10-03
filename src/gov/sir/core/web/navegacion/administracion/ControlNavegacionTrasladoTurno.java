/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWTrasladoTurno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWTrasladoTurno</CODE>
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionTrasladoTurno implements ControlNavegacion {

	/** Constante que identifica la acción de ver los detalles un turno */
	public static final String VER_DETALLES_TURNO_CERTIFICADOS = "VER_DETALLES_TURNO_CERTIFICADOS";
	
	/** Constante que identifica la acción de ver los detalles un turno */
	public static final String VER_DETALLES_TURNO_REGISTRO = "VER_DETALLES_TURNO_REGISTRO";	
        
        public static final String CORRECCION_CANAL_DE_RECAUDO = "CORRECCION_CANAL_DE_RECAUDO";
	/** Constante que identifica la acción de ver los detalles un turno */
	public static final String ANULAR_TURNO_POR_IDENTIFICADOR_OK = "ANULAR_TURNO_POR_IDENTIFICADOR_OK";	
	
	/** Constante que identifica la acción de ver los detalles un turno */
	public static final String HABILITAR_TURNO_POR_IDENTIFICADOR_OK = "HABILITAR_TURNO_POR_IDENTIFICADOR_OK";	
	
	/** Constante que identifica la acción de cambiar la matricula turno certificado */
	public static final String TURNO_CERTIFICADO_CAMBIO_MATRICULA = "TURNO_CERTIFICADO_CAMBIO_MATRICULA";	
	
	/** Constante que identifica la acción de cambiar la matricula turno certificado de respuesta */
	public static final String CAMBIAR_MATRICULA_OK = "CAMBIAR_MATRICULA_OK";
	
	/** Constante que identifica la acción de cambiar la matricula turno certificado de respuesta cancelar */
	public static final String CAMBIAR_MATRICULA_CANCELAR = "CAMBIAR_MATRICULA_CANCELAR";
	
	/** La respuesta de ciudadano para la edicion*/
	public static final String VALIDAR_CIUDADANO_EDICION_OK = "VALIDAR_CIUDADANO_EDICION_OK";
	
	public static final String REANOTAR_TURNO_ESPECIFICACION = "REANOTAR_TURNO_ESPECIFICACION";
	
	public static final String REANOTAR_TURNO_POR_IDENTIFICADOR_OK = "REANOTAR_TURNO_POR_IDENTIFICADOR_OK";
	
	public static final String REANOTAR_TURNO_INICIO = "REANOTAR_TURNO_INICIO";
        
        public static final String NOT_NOTA_DEVOLUTIVA = "NOT_NOTA_DEVOLUTIVA";
        
        public static final String CONFIRMACION = "CONFIRMACION";
        
                
             /*
        * @author : CTORRES
        * @change : Se agrega la constante CARGAR_TESTAMENTO_OK 
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        
        public static final String CARGAR_TESTAMENTO_OK = "CARGAR_TESTAMENTO_OK"; 
	
	/**
	 *  Constructor por Default de <CODE>ControlNavegacionTrasladoTurno</CODE>
	 */
	public ControlNavegacionTrasladoTurno() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
 		String accion = (String) request.getParameter(WebKeys.ACCION);

		HttpSession sesion = request.getSession();
                
		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO)
			|| accion.equals(AWTrasladoTurno.TRASLADAR_TURNO)) {
			return AWTrasladoTurno.TRASLADAR_TURNO;
		} else if (accion.equals(AWTrasladoTurno.TERMINA)) {
			return AWTrasladoTurno.TERMINA;
                } else if (accion.equals(AWTrasladoTurno.BACK_NOT_NOTA)){
                        return NOT_NOTA_DEVOLUTIVA;
		} else if (accion.equals(AWTrasladoTurno.VER_DETALLES_TURNO)) {
			
			Turno turno = (Turno) sesion.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
			Solicitud solicitud = turno.getSolicitud();
				
			if(solicitud instanceof SolicitudCertificado){
				return VER_DETALLES_TURNO_CERTIFICADOS;
			}else if (solicitud instanceof SolicitudRegistro){
				return VER_DETALLES_TURNO_REGISTRO;
			}
				
			return VER_DETALLES_TURNO_CERTIFICADOS;			
			
		} else if (accion.equals(AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO)) {
			String vistaVolver = (String) request.getSession().getAttribute(WebKeys.VISTA_VOLVER);
			Boolean excepcion = (Boolean) request.getSession().getAttribute(WebKeys.HAY_EXCEPCION);
			if(excepcion != null && excepcion.booleanValue())
				return AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO;
			if(vistaVolver!=null){
				return vistaVolver;	
			}			
			return AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO;
		} else if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNO) || accion.equals(AWTrasladoTurno.IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE)) {
			if (sesion.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO) != null) {
				Turno turno = (Turno) sesion.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
				Solicitud solicitud = turno.getSolicitud();
				
                                /** @author : HGOMEZ
                                *** @change : Se comenta el siguiente if dado 
                                *** que es redundante ya que se cumpla o no la condición
                                *** siempre se retorna VER_DETALLES_TURNO_CERTIFICADOS.
                                *** Caso Mantis : 12288
                                */
//				if(solicitud instanceof SolicitudCertificado){
//					return VER_DETALLES_TURNO_CERTIFICADOS;
//				}
//                                else 
                                if (solicitud instanceof SolicitudRegistro){
					return VER_DETALLES_TURNO_REGISTRO;
				}
				
				return VER_DETALLES_TURNO_CERTIFICADOS;
				
			} else {
				return AWTrasladoTurno.CONSULTAR_TURNO;
			}
		} else if (accion.equals(AWTrasladoTurno.CONSULTAR_MATRICULA)){
			return AWTrasladoTurno.CONSULTAR_MATRICULA;
		}  else if (accion.equals(AWTrasladoTurno.CORRECCION_CANAL_RECAUDO)) {
                    return CORRECCION_CANAL_DE_RECAUDO;
                }else if (accion.equals(AWTrasladoTurno.ANULAR_TURNO)){
			return this.ANULAR_TURNO_POR_IDENTIFICADOR_OK;
		} else if (accion.equals(AWTrasladoTurno.HABILITAR_TURNO)){
			return this.HABILITAR_TURNO_POR_IDENTIFICADOR_OK;
		} else if (accion.equals(AWTrasladoTurno.TERMINA_CONSULTA_MATRICULA)){
			Turno turno = (Turno) sesion.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
			Solicitud solicitud = turno.getSolicitud();
				
			if (solicitud instanceof SolicitudRegistro){
				return VER_DETALLES_TURNO_REGISTRO;
			}
			return VER_DETALLES_TURNO_CERTIFICADOS;
		}  else if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA)){
			Turno turno = (Turno) sesion.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
			
			return TURNO_CERTIFICADO_CAMBIO_MATRICULA;
		} else if (accion.equals(AWTrasladoTurno.CAMBIAR_FOLIO_MATRICULA_ID)){
			return CAMBIAR_MATRICULA_OK;
		} else if (accion.equals(AWTrasladoTurno.CANCELAR_CAMBIAR_FOLIO_MATRICULA_ID)){
			return CAMBIAR_MATRICULA_CANCELAR;
		} else if (accion.equals(AWTrasladoTurno.VALIDAR_CIUDADANO_EDICION)){
			return VALIDAR_CIUDADANO_EDICION_OK;
		} else if (accion.equals(AWTrasladoTurno.REALIZAR_CIUDADANO_EDICION)){
			return VALIDAR_CIUDADANO_EDICION_OK;
		} else if (accion.equals(AWTrasladoTurno.REANOTAR_TURNO_INICIAR)){
			return REANOTAR_TURNO_ESPECIFICACION;
		} else if (accion.equals(AWTrasladoTurno.REANOTAR_TURNO)){
                    String isNotify = (String) request.getSession().getAttribute(AWTrasladoTurno.IS_NOTIFY);
                    boolean isNot = false;
                    if(isNotify != null && isNotify.equals("TRUE")){
                        isNot = true;
                    } else {
                        isNot = false;
                    }
                    if(isNot){
                        return CONFIRMACION;
                    } else{
                        return REANOTAR_TURNO_POR_IDENTIFICADOR_OK;
                    }
		} else if (accion.equals(AWTrasladoTurno.CANCELAR_REANOTACION)){
			return REANOTAR_TURNO_INICIO;
                        
                        
               
                             /*
        * @author : CTORRES
        * @change : Se agrega condicion para cargar ver Detalles de Turno Testamento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
		}else if (accion.equals(AWTrasladoTurno.VER_DETALLES_TURNO_TESTAMENTO)){
			return CARGAR_TESTAMENTO_OK;
		}

		return null;
	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
