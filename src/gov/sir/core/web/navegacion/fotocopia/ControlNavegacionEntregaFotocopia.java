package gov.sir.core.web.navegacion.fotocopia;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.fotocopia.AWEntregaFotocopia;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWEntregaFotocopia.
 * @author dlopez
 */
public class ControlNavegacionEntregaFotocopia implements ControlNavegacion {


	/**
	* Constante que indica que se debe fotocopiar el documento
	*/
	public static final String FOTOCOPIAR_DOCUMENTO = "FOTOCOPIAR_DOCUMENTO";

	
	/**
	* Constante que indica que se debe fotocopiar el documento
	*/
	public static final String ENTREGAR_FOTOCOPIAS = "ENTREGAR_FOTOCOPIAS";
	


	/**
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}



	/**
	* Este m�todo verifica el estado de los diferentes objetos que se encuentran
	* en la sesi�n, y de acuerdo a esto env�a una respuesta para que sea procesada
	* y asi poder tener una navegaci�n acertada.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acci�n enviada por la acci�n web no es v�lida");
		}

		if (accion.equals(AWEntregaFotocopia.ENTREGAR_FOTOCOPIAS)) {
			return ENTREGAR_FOTOCOPIAS;
		}
		
		else if (accion.equals(AWEntregaFotocopia.FOTOCOPIAR_DOCUMENTO)) {
			return FOTOCOPIAR_DOCUMENTO;
		}
		else {
		    return null;
		}
	}

	/**
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
