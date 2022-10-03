package gov.sir.core.web.navegacion.administracion;


import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWMigracionIncremental;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegaci�n para <CODE>AWMigracionIncremental</CODE>
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionMigracionIncremental implements ControlNavegacion {

	/** Constante que identifica la acci�n bloquear un folio exitosamente 
	 * cuando hay un turno en tr�mite en el sistema FOLIO.*/
	public static final String BLOQUEAR_TURNO_FOLIO_OK = "BLOQUEAR_TURNO_FOLIO_OK";

	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS_OK = "CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS_OK";

	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String ACTUALIZAR_COMPLEMENTACION_FOLIO_OK = "ACTUALIZAR_COMPLEMENTACION_FOLIO_OK";

	/**
	 *  Constructor por Default de <CODE>MigracionIncremental</CODE>
	 */
	public ControlNavegacionMigracionIncremental() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegaci�n.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * M�todo que procesa la siguiente acci�n de navegaci�n dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acci�n siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = ((String) request.getParameter(WebKeys.ACCION)).trim();

		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es v�lida");
		}
		
		if (accion.equals(AWMigracionIncremental.BLOQUEAR_TURNO_FOLIO)) {
			return ControlNavegacionMigracionIncremental.BLOQUEAR_TURNO_FOLIO_OK;
		} else if (accion.equals(AWMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO)) {
				return ControlNavegacionMigracionIncremental.BLOQUEAR_TURNO_FOLIO_OK;
		} else if (accion.equals(AWMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS)) {
			return ControlNavegacionMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS_OK;
		} else if (accion.equals(AWMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO)) {
			return ControlNavegacionMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO_OK;
		} else if (accion.equals(AWMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FROM_FOLIO)) {
			return ControlNavegacionMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO_OK;
		} 
		return null;
	}

	/**
	 * Finalizaci�n de la navegaci�n
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
