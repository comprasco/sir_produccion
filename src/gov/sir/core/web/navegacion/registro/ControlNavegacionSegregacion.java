/*
 * Created on 28-FEB-2005
*/
package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWSegregacion;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import javax.servlet.http.HttpSession;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CProceso;

/**
 * Clase que define la navegación para la Acción Web, AWSegregacion del proceso de segregación en registro de documentos.
 * @author ppabon
*/
public class ControlNavegacionSegregacion implements ControlNavegacion {

	/** Constante que indica que se debe dirigir la navegación a la pantalla de creación de la anotación */
	public static final String SEGREGACION_ANOTACION = "SEGREGACION_ANOTACION";

	/** Constante se utiliza para que cuándo en una segregación el usuario no ingreso una anotación explicitamente en el primer paso
	  el pueda seleccionar una anotación de las que están en temporal.*/
	public static final String SEGREGACION_ESCOGER_ANOTACION_TEMPORAL = "SEGREGACION_ESCOGER_ANOTACION_TEMPORAL";

	/** Constante que indica que se debe dirigir la navegación a la pantalla de la carga de las anotaciones que deben ser heredadas.*/
	public static final String SEGREGACION_HERENCIA = "SEGREGACION_HERENCIA";

	/** Constante que indica que se debe dirigir la navegación a la pantalla de la tabla de segregación.*/
	public static final String SEGREGACION_TABLA = "SEGREGACION_TABLA";

	//MANEJO DE LA ADICIÓN Y ELIMINACIÓN DE CIUDADANOS EN LA CREACIÓN INICIAL DE LA ANOTACIÓN
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_SEGREGACION = "AGREGAR_VARIOS_CIUDADANOS_SEGREGACION";

	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";

	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";

	/** Constante que indica que se va eliminar un ciudadano de la anotación */
	public static final String ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION = "ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION";

	/** Constante que indica que se va a la pantalla de confirmación de la segregación */
	public static final String SEGREGACION_CONFIRMACION = "SEGREGACION_CONFIRMACION";

	/** Constante que indica que se va a editar los nuevos folios */
	public static final String CONSULTAR_NUEVO_FOLIO = "CONSULTAR_NUEVO_FOLIO";

	/** Constante que indica que se debe regresar a los resultados de la segregación */
	public static final String REGRESAR_RESULTADO_SEGREGACION = "REGRESAR_RESULTADO_SEGREGACION";

	/** wz-pasox: [cancelar] = redireccion a correcciones */
	public static final String OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_CORRECCIONES = "OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_CORRECCIONES";

	/** wz-pasox: [cancelar] = redireccion a registro */
	public static final String OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_REGISTRO = "OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_REGISTRO";
	
	/** Constante que indica que se van a agregar al la lista de ciudadanos los propietarios de una folio**/
	public static final String GUARDAR_PROPIETARIOS_ANOTACION_SEGREGACION_REGISTRO = "GUARDAR_PROPIETARIOS_ANOTACION_SEGREGACION_REGISTRO";
	/** Constante que indica que se va a consultar los ultimos propietarios de un folio*/
	public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";
	

	/**
	 * Crea una instancia del control de navegacion
	 */
	public ControlNavegacionSegregacion() {
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
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if (accion.equals(AWSegregacion.REFRESCAR_ANOTACION_SEGREGACION)) {
			return SEGREGACION_ANOTACION;
		} else if (accion.equals(AWSegregacion.SEGREGACION_ANOTACION)) {
			return SEGREGACION_HERENCIA;
		} else if (accion.equals(AWSegregacion.VALIDAR_CIUDADANO_SEGREGACION)) {
			return SEGREGACION_ANOTACION;
		} else if (accion.equals(AWSegregacion.SEGREGACION_SIN_ANOTACION)) {
			return SEGREGACION_ESCOGER_ANOTACION_TEMPORAL;
		} else if (accion.equals(AWSegregacion.SEGREGACION_ESCOGER_ANOTACION)) {
			return SEGREGACION_HERENCIA;
		} else if (accion.equals(AWSegregacion.SEGREGACION_HERENCIA)) {
			return SEGREGACION_TABLA;
		} else if (accion.equals(AWSegregacion.AGREGAR_FOLIO_DERIVADO)) {
			return SEGREGACION_TABLA;
		} else if (accion.equals(AWSegregacion.ELIMINAR_FOLIO_DERIVADO)) {
			return SEGREGACION_TABLA;
		} else if (accion.equals(AWSegregacion.EDITAR_FOLIO_DERIVADO)) {
			return SEGREGACION_TABLA;
		} else if (accion.equals(AWSegregacion.GUARDAR_WEB_SEGREGACION)) {
			return SEGREGACION_TABLA;
		} else if (accion.equals(AWSegregacion.SEGREGAR_MASIVO)) {
			return SEGREGACION_CONFIRMACION;
		} else if (accion.equals(AWSegregacion.CONSULTAR_NUEVO_FOLIO)) {
			return CONSULTAR_NUEVO_FOLIO;
		} else if (accion.equals(AWSegregacion.GUARDAR_CAMBIOS_FOLIO)) {
			return REGRESAR_RESULTADO_SEGREGACION;
		}else if (accion.equals(AWSegregacion.AGREGAR_DIRECCION)) {
			return CONSULTAR_NUEVO_FOLIO;
		} else if (accion.equals(AWSegregacion.ELIMINAR_DIRECCION)) {
			return CONSULTAR_NUEVO_FOLIO;
		}

		else if (accion.equals(AWSegregacion.ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION)) {
			return ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION;
			//AGREGAR NUEVOS CIUDADANOS CUANDO SE ESTA CREANDO UNA ANOTACIÓN EN EL PROCESO DE SEGREGACIÓN.
		} else if (accion.equals(AWSegregacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
			return AGREGAR_VARIOS_CIUDADANOS_SEGREGACION;
		} else if (accion.equals(AWSegregacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
			return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION;
		} else if (accion.equals(AWSegregacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
			return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION;
		} else if (accion.equals(AWSegregacion.ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION)) {
			return ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION;
		} else if (accion.equals(AWSegregacion.GET_ULTIMOS_PROPIETARIOS)){
			return GET_ULTIMOS_PROPIETARIOS;	
		} else if (accion.equals(AWSegregacion.CANCELAR_PROPIETARIOS)){
			String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
			
			if (paramVistaAnterior.equals("ANOTACION_SEGREGACION_REGISTRO")){
				return GUARDAR_PROPIETARIOS_ANOTACION_SEGREGACION_REGISTRO;
			}
			
			
			/*Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
            if(CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
                return CANCELAR_PROPIETARIOS_CORRECCION;
            }
			return CANCELAR_PROPIETARIOS;*/
		} else if (accion.equals(AWSegregacion.GUARDAR_PROPIETARIOS)){
			String paramVistaAnterior = (String) request.getSession().getAttribute("paramVistaAnterior");
			
			if (paramVistaAnterior.equals("ANOTACION_SEGREGACION_REGISTRO")){
				return GUARDAR_PROPIETARIOS_ANOTACION_SEGREGACION_REGISTRO;
			}
		} 
		// BOTON DE CANCELACIÓN
		if (AWSegregacion.CANCELAR_SEGREGACION.equals(accion) || AWSegregacion.ELIMINAR_SEGREGACION.equals( accion )) {
			return doNav_OpcionSegregacionPasoXCancelar(request);
		}



		return null;
	}
	
	/**
	 * @param request
	 * @return
	 * @throws ControlNavegacionException
	 */
	public String doNav_OpcionSegregacionPasoXCancelar(HttpServletRequest request) throws ControlNavegacionException {
	
		// the session
		HttpSession session;
		session = request.getSession();
	
		WebSegregacion local_Segregacion;
		local_Segregacion = (WebSegregacion) session.getAttribute(WebKeys.WEB_SEGREGACION);
	
		// acciones segun el proceso
		// acciones segun atributos en sesion
	
		// caso 1: correcciones
	
		if (null != local_Segregacion) {
			int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
			if (local_Segregacion.getIdProceso()== procesoCorrecciones) {
	
				return OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_CORRECCIONES;
	
			} // if
	
		}	
		// caso 2: registro (default)
		String local_ControlNavegacionTarget;
	
		local_ControlNavegacionTarget = (session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA) != null) ? (String) session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA) : "procesos.view";
	
		// return local_ControlNavegacionTarget;
	
		return OPCIONSEGREGACION_PASOX_BTNCANCELAR_OK_REGISTRO;
	
		// -------------------------------------------------
	} // end-method: doNav_OpcionSegregacionPasoXCancelar

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}

}
