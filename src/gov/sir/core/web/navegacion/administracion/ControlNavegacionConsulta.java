package gov.sir.core.web.navegacion.administracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.sir.core.negocio.modelo.TipoConsulta;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWConsulta;

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

	/**Esta constante se utiliza  para identificar una consulta de observación de folio */
	public static final String FOLIO = "FOLIO";

	/**Esta constante se utiliza  para identificar el evento validación  satisfactoria de un folio */
	public static final String VALIDA_FOLIO_OK = "VALIDA_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento observación errónea de un folio */
	public static final String OBSERVA_FOLIO_FAILED = "OBSERVA_FOLIO_FAILED";

	/**Esta constante se utiliza  para identificar el evento consulta satisfactoria de un folio */
	public static final String VER_FOLIO_OK = "VER_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento consulta errónea de un folio */
	public static final String VER_FOLIO_FAILED = "VER_FOLIO_FAILED";

   /**Esta constante se utiliza para identificar el evento de ocurrencia de resultados en una consulta simple */
   public static final String RESULTADO_CONSULTA_SIMPLE = "RESULTADO_CONSULTA_SIMPLE";

   /** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	/** Constante que identifica la acción avanzar el turno pero con excepción en la impresión.*/
	public static final String TERMINA = "TERMINA";

	public static final String RESULTADO_CONSULTA_COMPLEJA = "RESULTADO_CONSULTA_COMPLEJA";
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
            } else if (tipo_consulta.equals(TipoConsulta.TIPO_SIMPLE)) {
            	return ControlNavegacionConsulta.SIMPLE;
            } 
		} else if (
			accion.equals(AWConsulta.VER_FOLIO)) { // SI SE UTILIZO
			if (sesion.getAttribute(AWConsulta.FOLIO) == null) {
				return ControlNavegacionConsulta.VER_FOLIO_FAILED;
			}
            return ControlNavegacionConsulta.VER_FOLIO_OK;
		}    //Redirección para la ejecución de consultas simples
        else if (accion.equals(AWConsulta.EJECUTA_SIMPLE)) { // SI SE UTILIZA
            return ControlNavegacionConsulta.RESULTADO_CONSULTA_SIMPLE;
        } else if (accion.equals(AWConsulta.TERMINA)) { // SI SE UTILIZA
			return AWConsulta.TERMINA;
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
