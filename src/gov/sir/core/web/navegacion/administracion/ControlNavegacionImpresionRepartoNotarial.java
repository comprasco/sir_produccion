/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;


import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWImpresionFolio;
import gov.sir.core.web.acciones.administracion.AWImpresionRepartoNotarial;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegaci�n para <CODE>AWImpresionRepartoNotarial</CODE>
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionImpresionRepartoNotarial implements ControlNavegacion {

	/**Constante que reenvia la navegaci� a preguntar cu�les la estaci�n que se va a usar
	 * para sacar el consecutivo de recibos.*/
	public static final String DETERMINAR_ESTACION_REIMPRESION_RECIBOS = "DETERMINAR_ESTACION_REIMPRESION_RECIBOS";

	/**
	 *  Constructor por Default de <CODE>ControlNavegacionImpresionFolio</CODE>
	 */
	public ControlNavegacionImpresionRepartoNotarial() {
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
		String accion = request.getParameter(WebKeys.ACCION);
		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWImpresionRepartoNotarial.IMPRIMIR_CARATULA)) {
			return AWImpresionRepartoNotarial.IMPRIMIR_CARATULA;
		} else if (accion.equals(AWImpresionRepartoNotarial.IMPRIMIR_ACTA)) {
			return AWImpresionRepartoNotarial.IMPRIMIR_ACTA;
		} else if (accion.equals(AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO)) {
			return AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO;
		} else if (accion.equals(AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_ACTA_REPARTO)) {
			return AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_ACTA_REPARTO;
		} else if (accion.equals(AWImpresionRepartoNotarial.TERMINA)) {
			return AWImpresionFolio.TERMINA;
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
