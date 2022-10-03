package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWTestamentos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWTestamentos</CODE>
 * que es la navegación de todo lo relacionado con inscripción de testamentos.
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionTestamentos implements ControlNavegacion {

	/** Constante que identifica la acción de incribir el testamento*/
	public static final String REGISTRAR_OK = "REGISTRAR_OK";
	
	/** Constante que identifica la acción de devolver el  testamento*/
	public static final String DEVOLVER_OK = "DEVOLVER_OK";
	
	/** Constante que identifica la acción de registrar o devolver el testamento pero con errores en la impresión*/
	public static final String CONFIRMACION_ERROR_IMPRESION = "CONFIRMACION_ERROR_IMPRESION";

	/** Constante que identifica la acción de colocar la descripción en la nota al seleccionaruna de ellas*/
	public static final String COLOCAR_DESCRIPCION_NOTA = "COLOCAR_DESCRIPCION_NOTA";

	/** Constante para identificar que agregaron testador*/
	public static final String REG_TESTAMENTO_AGREGA_ELIMINA_TESTADOR="REG_TESTAMENTO_AGREGA_ELIMINA_TESTADOR";
                              /*
        * @author : CTORRES
        * @change : se definen nuevas constantes
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        public static final String REFRESCAR_EDICION = "REFRESCAR_EDICION";
        public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";

	/**
	 *  Constructor por Default de <CODE>Testamentos</CODE>
	 */
	public ControlNavegacionTestamentos() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de registro de testamentos
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		}
		if (accion.equals(AWTestamentos.REGISTRAR)) {
			Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
			if(impreso!=null && impreso.intValue() != 0){
				return CONFIRMACION_ERROR_IMPRESION;	
			}  			
			return REGISTRAR_OK;
		} else if (accion.equals(AWTestamentos.DEVOLVER)) {
			Integer impreso = (Integer)request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
			if(impreso!=null && impreso.intValue() != 0){
				return CONFIRMACION_ERROR_IMPRESION;	
			}  			
			return DEVOLVER_OK;
		}else if (accion.equals(AWTestamentos.COLOCAR_DESCRIPCION)) {
			return COLOCAR_DESCRIPCION_NOTA;
		}
		/** Modifica Pablo Quintana Junio 16 2008
			 Si la accion es confrontacion retorna a devolver_ok*/
		else if(accion.equals(AWTestamentos.CORRECCION_ENCABEZADO)){
			return DEVOLVER_OK;
		}
		else if ( (accion.equals(AWTestamentos.AGREGAR_TESTADOR)) || (accion.equals(AWTestamentos.ELIMINAR_TESTADOR)) ){
			return REG_TESTAMENTO_AGREGA_ELIMINA_TESTADOR;
		}else if(accion.equals(AWTestamentos.DEVOLVER_A_CONFRONTACION)){
			return DEVOLVER_OK;
		}
                                      /*
        * @author : CTORRES
        * @change : Condiciones para las acciones de VALIDAR_CIUDADANO, REFRECAR_EDICION
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                else if(accion.equals(AWTestamentos.VALIDAR_CIUDADANO)){
			return VALIDAR_CIUDADANO;
		}else if(accion.equals(AWTestamentos.REFRESCAR_EDICION)){
			return REFRESCAR_EDICION;
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
