/* Generated by Together */

package gov.sir.core.web.navegacion.registro;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWFolio;

public class ControlNavegacionRegistro implements ControlNavegacion{


	private static final String AGREGAR_CIUDADANO = "AGREGAR_CIUDADANO";

	private static final String FOLIO_CREADO = "FOLIO_CREADO";

	private static final String CREACION_FOLIO_REGISTRADO = "CREACION_FOLIO_REGISTRADO";

	private static final String CREACION_FOLIO_ACTUALIZADO = "CREACION_FOLIO_ACTUALIZADO";

	private static final String CREACION_FOLIO_FINALIZADO = "CREACION_FOLIO_FINALIZADO";

	private static final String CALIFICACION = "CALIFICACION";

	/**Esta constante identifica que el usuario va modificar una anotacion*/
	public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";

	public static final String GRABAR_EDICION="GRABAR_EDICION";
	public static final String CANCELAR_EDICION="CANCELAR_EDICION";
	public final static String REFRESCAR_EDICION="REFRESCAR_EDICION";


	//	Varios ciudadanos Edicion
	/** Constante que indica que se van a agregar varios ciudadanos a la anotacion */
	public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION= "AGREGAR_VARIOS_CIUDADANOS_EDICION";
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
	/** Constante que indica que se va eliminar un ciudadano de la anotaci�n */
	public static final String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";

	/** Constante que indica que se va a recargar la p�gina de adici�n de direcciones */
	public static final String RECARGAR_CREACION_FOLIO = "RECARGAR_CREACION_FOLIO";


	//End: Varios ciudadanos Edicion

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
	 */
	public void doStart(HttpServletRequest arg0) {

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}

		if(accion.equals(AWFolio.CREAR) && request.getSession().getAttribute(WebKeys.FOLIO)!=null){
			return FOLIO_CREADO;
		}

		if(accion.equals(AWFolio.REGISTRAR_CREACION_FOLIO))
		{
			if(((Proceso)request.getSession().getAttribute(WebKeys.PROCESO)).getNombre().equals("REGISTRO")){
				return CALIFICACION;
			}
			return CREACION_FOLIO_REGISTRADO;
		}

		if(accion.equals(AWFolio.CREACION_FOLIO_FINALIZADO ))
		{
			return CREACION_FOLIO_FINALIZADO;
		}

		if(accion.equals(AWFolio.REGISTRAR_CREACION_FOLIO))
		{
			if(((Proceso)request.getSession().getAttribute(WebKeys.PROCESO)).getNombre().equals("REGISTRO")){
				return CALIFICACION;
			}
			return CREACION_FOLIO_REGISTRADO;
		}

		if(accion.equals(AWFolio.EDITAR_ANOTACION))
		{
			return EDITAR_ANOTACION;
		}

		if(accion.equals(AWFolio.GRABAR_EDICION))
		{
			return GRABAR_EDICION;
		}
		//		Varios ciudadanos Edicion
		if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_EDICION)){
			return AGREGAR_VARIOS_CIUDADANOS_EDICION;
		} else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
			return AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION;
		} else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
			return REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION;
		}else if (accion.equals(ELIMINAR_CIUDADANO_EDICION)){
			return ELIMINAR_CIUDADANO_EDICION;
		//End: Varios ciudadanos Edicion
		} else if (accion.equals(REFRESCAR_EDICION)){
			return REFRESCAR_EDICION;
		}else if (accion.equals(CANCELAR_EDICION)){
			return CANCELAR_EDICION;
		} else if (accion.equals(AWFolio.AGREGAR_DIRECCION)) {
			return RECARGAR_CREACION_FOLIO;
		} else if (accion.equals(AWFolio.ELIMINAR_DIRECCION)) {
			return RECARGAR_CREACION_FOLIO;
		} else if (accion.equals(AWFolio.VALIDAR_CIUDADANO_EDICION)) {
			return AWFolio.VALIDAR_CIUDADANO_EDICION;
		}


		return AGREGAR_CIUDADANO;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}
}