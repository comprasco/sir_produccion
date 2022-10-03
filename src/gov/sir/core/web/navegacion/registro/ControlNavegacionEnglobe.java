package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWEnglobe;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.negocio.modelo.WebEnglobe;

import javax.servlet.http.HttpSession;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.navegacion.correccion.ControlNavegacionModificacionFolio;

/**
 * Clase que define la navegación para la Acción Web, AWEnglobe del proceso de Englobe, en registro de documentos.
 * @author dlopez
*/
public class ControlNavegacionEnglobe implements ControlNavegacion {

	/** Constante que indica que se desea regresar a la vista de creación de la anotación temporal, ya que se
	 * estaba modificando los CIUDADANOS que hacían parte de la anotación temporal */
	public static final String CIUDADANOS = "CIUDADANOS";

	/**
	* Acción que indica que se desean heredar anotaciones.
	*/
	public static final String HEREDAR_ANOTACION ="HEREDAR_ANOTACION";

	/**
	* Acción que indica que se desean seleccionar entre las anotaciones del folio aquellas que se desean heredar.
	*/
	public static final String CREAR_ANOTACION ="CREAR_ANOTACION";

	/**
	* Acción que indica que se desean seleccionar entre las anotaciones temporales, cuál es la anotación que
	* dará origen al englobe
	*/
	public static final String CREAR_ANOTACION_VACIA ="CREAR_ANOTACION_VACIA";

	
	/** wz-pasox: [cancelar] = redireccion a correcciones */
	public static final String OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_CORRECCIONES = "OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_CORRECCIONES";
	
	/** wz-pasox: [cancelar] = redireccion a registro */
	public static final String OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_REGISTRO = "OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_REGISTRO";

	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_ENGLOBE_ANOTACION_ENGLOBE_REGISTRO = "GUARDAR_PROPIETARIOS_ENGLOBE_ANOTACION_ENGLOBE_REGISTRO";
	/** Constante que indica que se va a consultar los ultimos propietarios de un folio*/
	public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";

	/**
	 * Crea una instancia del control de navegacion
	 */
	public ControlNavegacionEnglobe() {
		super();
	}

	/**
	*/
	public void doStart(HttpServletRequest request) {

	}

	/**
	* Método que genera una respuesta de acuerdo a la acción recibida en el request.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		// PROCESAMIENTO DE LA NAVEGACION.
		if (accion.equals(AWEnglobe.ESCOGER_FOLIOS)) {
			return AWEnglobe.ESCOGER_FOLIOS;
		}

		//AGREGAR NUEVOS CIUDADANOS CUANDO SE ESTA CREANDO UNA ANOTACIÓN EN EL PROCESO DE ENGLOBE.
		else if (accion.equals(AWEnglobe.AGREGAR_VARIOS_CIUDADANOS_ENGLOBE)) {
			return CIUDADANOS;
		} else if (accion.equals(AWEnglobe.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE)) {
			return CIUDADANOS;
		} else if (accion.equals(AWEnglobe.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE)) {
			return CIUDADANOS;
		} else if (accion.equals(AWEnglobe.ELIMINAR_CIUDADANO_ANOTACION_ENGLOBE)) {
			return CIUDADANOS;
		} else if (accion.equals(AWEnglobe.REFRESCAR_ANOTACION_ENGLOBE)) {
			return CIUDADANOS;
		} else if (accion.equals(AWEnglobe.VALIDAR_CIUDADANO_ENGLOBE)) {
			return CIUDADANOS;
		}

		//SE CREO UNA ANOTACIÓN TEMPORAL Y SE DESEA ENVIAR LA NAVEGACIÓN A SELECCIONAR LAS ANOTACIONES A HEREDAR
		else if (accion.equals(AWEnglobe.ENGLOBE_ANOTACION)) {
			return CREAR_ANOTACION;
		}

		//NO SE CREO UNA ANOTACIÓN TEMPORAL Y SE DESEA ENVIAR LA NAVEGACIÓN A SELECCIONAR
		//UNA ANOTACIÓN TEMPORAL PARA QUE SE LA ANOTACIÓN QUE ORIGINE EL ENGLOBE
		else if (accion.equals(AWEnglobe.ENGLOBE_SIN_ANOTACION)) {
			return CREAR_ANOTACION_VACIA;
		}
		else if (accion.equals(AWEnglobe.RECARGAR_ANOTACIONES_TEMPORALES)) {
			return CREAR_ANOTACION_VACIA;
		}

		//OPCIONES DE GUARDAR ANOTACIONES A HEREDAR
		if (accion.equals(AWEnglobe.ESCOGER_ANOTACION)) {
			return AWEnglobe.ESCOGER_ANOTACION;
		}
		else if (accion.equals(AWEnglobe.GUARDAR_ANOTACIONES_HEREDADAS)) {
			return AWEnglobe.ESCOGER_ANOTACION;
		}
		else if (accion.equals(AWEnglobe.RECARGAR_ANOTACIONES_DEFINITIVAS)) {
			return AWEnglobe.ESCOGER_ANOTACION;
		}

		if (accion.equals(AWEnglobe.HEREDAR_ANOTACION)) {
			return AWEnglobe.HEREDAR_ANOTACION;
		}

		if (accion.equals(AWEnglobe.UBICACION_GEOGRAFICA)) {
			return AWEnglobe.UBICACION_GEOGRAFICA;
		}

		if (accion.equals(AWEnglobe.INFORMACION_LOTE)) {
			return AWEnglobe.INFORMACION_LOTE;
		}

		else if (accion.equals(AWEnglobe.AGREGAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		}else if (accion.equals(AWEnglobe.ELIMINAR_DIRECCION)){
			String vista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
			return vista;
		} else if (accion.equals(AWEnglobe.GET_ULTIMOS_PROPIETARIOS)){
			return GET_ULTIMOS_PROPIETARIOS;	
		} else if (accion.equals(AWEnglobe.CANCELAR_PROPIETARIOS)){
			String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
			if (paramVistaAnterior.equals("ENGLOBE_ANOTACION_ENGLOBE")){
				return GUARDAR_PROPIETARIOS_ENGLOBE_ANOTACION_ENGLOBE_REGISTRO;
			}
		} else if (accion.equals(AWEnglobe.GUARDAR_PROPIETARIOS)){
			String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
			if (paramVistaAnterior.equals("ENGLOBE_ANOTACION_ENGLOBE")){
				return GUARDAR_PROPIETARIOS_ENGLOBE_ANOTACION_ENGLOBE_REGISTRO;
			}
		} 

		if (accion.equals(AWEnglobe.ENGLOBAR)) {

             HttpSession session = request.getSession();
             WebEnglobe englobe = (WebEnglobe) session.getAttribute( WebKeys.WEB_ENGLOBE );

             if( ( null != englobe ) ) {
                // opc: Proceso.CORRECCIONES
				int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
                if(englobe.getIdProceso()==procesoCorrecciones){
					request.getSession().removeAttribute(WebKeys.WEB_ENGLOBE);                	
                   return ControlNavegacionModificacionFolio.PAGE_REGION__FINISHANOTACIONENGLOBE__EVENTRESP_OK;
                } // if

             }
			request.getSession().removeAttribute(WebKeys.WEB_ENGLOBE);
             return AWEnglobe.ENGLOBAR;
		}

        //BOTONES DE CANCELAR
        if( AWEnglobe.CANCELAR_ENGLOBE.equals( accion ) || AWEnglobe.ELIMINAR_ENGLOBE.equals( accion )) {
          return doNav_OpcionEnglobePasoXCancelar( request );
        } // if


		return null;
	}


	/**
	 * @param request
	 * @return
	 * @throws ControlNavegacionException
	 */
	public String doNav_OpcionEnglobePasoXCancelar(HttpServletRequest request) throws ControlNavegacionException {
	
		// the session
		HttpSession session;
		session = request.getSession();
	
		//
		WebEnglobe local_Englobe;
		local_Englobe = (WebEnglobe) session.getAttribute(WebKeys.WEB_ENGLOBE);
	
		// acciones segun el proceso
		// acciones segun atributos en sesion
	
		// caso 1: correcciones	
		if (null != local_Englobe) {
			int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();	
			if (local_Englobe.getIdProceso()==procesoCorrecciones) {
	
				return OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_CORRECCIONES;
	
			} // if
	
		}
	
		// caso 2: registro (default)
		String local_ControlNavegacionTarget;
	
		local_ControlNavegacionTarget = (session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA) != null) ? (String) session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA) : "procesos.view";
	
		// return local_ControlNavegacionTarget;
	
		return OPCIONENGLOBE_PASOX_BTNCANCELAR_OK_REGISTRO;
	
		// -------------------------------------------------
	} // end-method: doNav_OpcionEnglobePasoXCancelar




	/**
	*/
	public void doEnd(HttpServletRequest request) {

	}

}
