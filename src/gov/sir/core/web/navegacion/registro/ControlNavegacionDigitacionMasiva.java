package gov.sir.core.web.navegacion.registro;

import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWDigitacionMasiva;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegación para la Acción Web, AWDigitacionMasiva del proceso de calificación.
 * @author dlopez
*/
public class ControlNavegacionDigitacionMasiva implements ControlNavegacion {
	
	//SIR-41(R1)

	/** Constante que indica que se bloquearon correctamente los folios antes de empezar el proceso de digitación en correcciones*/
	public static final String TOMAR_FOLIO_DIGITACION_OK = "TOMAR_FOLIO_DIGITACION_OK";
	
	/** Constante que indica que se debe regresar al proceso de correcciones*/	
	public static final String BTNBACK_PRCCORRECCIONES__EVENTRESP_OK = "BTNBACK_PRCCORRECCIONES__EVENTRESP_OK";
	
	/** 
	 * Constante para indicar que se desea realizar la consulta a un folio para cargar la información de un folio
	 * */
	public static final String CONSULTAR_FOLIO_DIGITACION_MASIVA = "CONSULTAR_FOLIO_DIGITACION_MASIVA";
	
	/** 
	 * Constante para indicar que se desea recargar la pagina
	 * */
	public static final String DIGITACION_MASIVA_RELOAD = "DIGITACION_MASIVA_RELOAD";
	
	/** 
	 * Constante para indicar que se desea realizar una actualización a los folios indicados
	 * */
	public static final String EDITAR_FOLIO_DIGITACION_MASIVA = "EDITAR_FOLIO_DIGITACION_MASIVA";
	

	/** 
	 * Constante para indicar que se desea entrar a construir la complentación a partir de las anotaciones de un folio
	 * */
	public static final String ENTRAR_CONSTRUCCION_COMPLEMENTACION	 = "ENTRAR_CONSTRUCCION_COMPLEMENTACION";	
	

	/** Constante para indicar que se desea seleccionar los folios para construir la complementación*/
	public static final String ELEGIR_FOLIOS_DIGITACION_MASIVA = "ELEGIR_FOLIOS_DIGITACION_MASIVA";

	/** Constante para indicar que se desea seleccionar las anotaciones de cada folio, para construir la complementación*/
	public static final String ELEGIR_ANOTACIONES_DIGITACION_MASIVA = "ELEGIR_ANOTACIONES_DIGITACION_MASIVA";

	/**
	 * Constante para indicar que se debe direccionar a la pantalla de detalles del folio
	 */
	public static final String VER_DETALLES_FOLIO_EVENTRESP_OK = "VER_DETALLES_FOLIO_EVENTRESP_OK";


	/**
	 * Crea una instancia del control de navegacion 
	 */
	public ControlNavegacionDigitacionMasiva() {
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
		HttpSession session = request.getSession();
		
		// PROCESAMIENTO DE LA NAVEGACION.
		if (accion.equals(AWDigitacionMasiva.TOMAR_FOLIO_DIGITACION)) {
			return TOMAR_FOLIO_DIGITACION_OK;
		}else if (accion.equals(AWDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA)) {
		   return CONSULTAR_FOLIO_DIGITACION_MASIVA;
		}else if (accion.equals(AWDigitacionMasiva.DIGITACION_MASIVA_RELOAD)) {
			   return DIGITACION_MASIVA_RELOAD;
		}else if (accion.equals(AWDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA)) {
		   return EDITAR_FOLIO_DIGITACION_MASIVA;
		}else if (accion.equals(AWDigitacionMasiva.ENTRAR_CONSTRUCCION_COMPLEMENTACION)) {
			return ENTRAR_CONSTRUCCION_COMPLEMENTACION;
		}else if (accion.equals(AWDigitacionMasiva.ELEGIR_FOLIOS_DIGITACION_MASIVA)) {
			return ELEGIR_FOLIOS_DIGITACION_MASIVA;
		}else if (accion.equals(AWDigitacionMasiva.QUITAR_FOLIO)) {
			return ELEGIR_FOLIOS_DIGITACION_MASIVA;
		}else if (accion.equals(AWDigitacionMasiva.ELEGIR_ANOTACIONES_DIGITACION_MASIVA)) {
			return ELEGIR_ANOTACIONES_DIGITACION_MASIVA;
		}else if (accion.equals(AWDigitacionMasiva.SALIR_CONSTRUCCION_COMPLEMENTACION)) {
			return ELEGIR_ANOTACIONES_DIGITACION_MASIVA;
		}else if( AWDigitacionMasiva.BTN_BACK_ACTION.equals( accion ) ) {
		   // BTNBACK__EVENTRESP_OK

		   final String PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS = "PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS";

		   String local_ProcessId;
		   local_ProcessId = CProceso.PROCESO_CORRECCIONES;

		   // Correcciones (Session Marks For SharedProcess)
		   if( null != session.getAttribute( PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS ) ){
			  local_ProcessId = (String)session.getAttribute( PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS );
		   }

		   if( CProceso.PROCESO_CORRECCIONES.equals( local_ProcessId ) ) {
			  String local_Result;
			  local_Result = BTNBACK_PRCCORRECCIONES__EVENTRESP_OK;
			  session.removeAttribute( PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS );
			  return local_Result;
		   }

		}else if(AWDigitacionMasiva.BTN_REGRESAR.equals( accion )){
			String local_Result;
			local_Result = BTNBACK_PRCCORRECCIONES__EVENTRESP_OK;
			return local_Result;
		}else if(AWDigitacionMasiva.VER_DETALLES_FOLIO.equals( accion )){
			String local_Result;
			local_Result = VER_DETALLES_FOLIO_EVENTRESP_OK;
			return local_Result;
		} else if (AWDigitacionMasiva.BTN_REGRESAR_DETALLES_FOLIO.equals(accion))
		{
			return TOMAR_FOLIO_DIGITACION_OK;
		}
		//PQ
		else if(AWDigitacionMasiva.ACTUALIZAR_FOLIO_CABIDA_LINDEROS.equals(accion)
				|| AWDigitacionMasiva.ACTUALIZAR_FOLIO_DIRECCIONES.equals(accion)
				|| AWDigitacionMasiva.CONSULTAR_ANOTACIONES_FOLIO.equals(accion)
				|| AWDigitacionMasiva.AGREGAR_DIRECCION_DIGITACION.equals(accion)
				|| AWDigitacionMasiva.ELIMINAR_DIRECCION_CALIFICACION.equals(accion)
				|| AWDigitacionMasiva.ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION.equals(accion)
				|| AWDigitacionMasiva.ACTUALIZAR_COMPLEMENTACION.equals(accion)
				|| AWDigitacionMasiva.REFRESCAR_PAGINADOR.equals(accion)){
			return VER_DETALLES_FOLIO_EVENTRESP_OK;
		}
		//Fin PQ
		return null;
	}

	/** 
	*/
	public void doEnd(HttpServletRequest request) {

	}

}
