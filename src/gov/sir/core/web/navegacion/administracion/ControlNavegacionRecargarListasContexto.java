/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWRecargarListasContexto;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegaci�n para <CODE>AWAdministracionCiudadano</CODE>
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionRecargarListasContexto implements ControlNavegacion {

	/**
	 *  Constructor por Default de <CODE>ControlNavegacionAdministracionForseti</CODE>
	 */
	public ControlNavegacionRecargarListasContexto() {
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
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO)) {
			return AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO;
		} else if (accion.equals(AWRecargarListasContexto.RECARGAR_LISTAS_CONTEXTO))
		{
			return AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO_OK;
		} else if (accion.equals(AWRecargarListasContexto.TERMINA)) {
			
			return AWRecargarListasContexto.TERMINA;
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
