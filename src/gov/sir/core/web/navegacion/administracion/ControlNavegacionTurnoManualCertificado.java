/*
 * Created on 20-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWTurnoManualCertificado;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ControlNavegacionTurnoManualCertificado implements ControlNavegacion {

	/** Constante que indica que el proceso de liquidación fué exitoso */
	public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

	/** Constante que indica que el proceso de liquidación no fué exitoso */
	public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

	/**
	 * Constante que indica que el proceso de inserción de una matrícula 
	 * o de un turno anterior a la solicitud fue exitoso
	 */
	public static final String AGREGAR_OK = "AGREGAR_OK";

	/**
	 * Constante que indica que el proceso de eliminación de una matrícula
	 * de la solicitud fue exitoso
	 */
	public static final String ELIMINAR_OK = "ELIMINAR_OK";

	/**
	 * Constante que indica que el proceso de eliminación de una matrícula
	 * no fue exitoso
	 */
	public static final String ELIMINAR_FAILED = "ELIMINAR_FAILED";

	/**
	 * Constante que indica que el proceso de inserción de una matrícula a la
	 * solicitud no fue exitoso
	 */
	public static final String AGREGAR_FAILED = "AGREGAR_FAILED";
    
	/**
	 * Constante que indica que se cancelo la el proceso de liquidacion
	 */
	public static final String CANCELADO = "CANCELADO";
	
	/** Constante que indica que el proceso de solicitud fué exitoso */
	public static final String SOLICITUD_OK = "SOLICITUD_OK";

	/** Constante que indica que el proceso de solicitud no fué exitoso */
	public static final String SOLICITUD_FAILED = "SOLICITUD_FAILED";

	/**
	 *
	 */
	public ControlNavegacionTurnoManualCertificado() {
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
	public String procesarNavegacion(HttpServletRequest request)
		throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}

		if (accion.equals(AWTurnoManualCertificado.LIQUIDAR)) {
			if(request.getSession().getAttribute(WebKeys.TURNO) == null){
				Liquidacion liquidacion = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);
	
				if (liquidacion != null) { 
					return LIQUIDACION_OK;
				} else {
					return LIQUIDACION_FAILED;
				}
			} else if (request.getSession().getAttribute(WebKeys.TURNO)!=null){
					return SOLICITUD_OK; 
			}
		} else if (accion.equals(AWTurnoManualCertificado.RECARGAR)) {
			return AGREGAR_OK;
		} else if (accion.equals(AWTurnoManualCertificado.AGREGAR)) {
			return AGREGAR_OK;
		} else if (accion.equals(AWTurnoManualCertificado.AGREGAR_TURNO)) {
			return AGREGAR_OK;
		} else if (accion.equals(AWTurnoManualCertificado.ELIMINAR)) {
			return ELIMINAR_OK;
		} else if (accion.equals(AWTurnoManualCertificado.REMOVER_INFO)) {
			return CANCELADO;    
		} else if (accion.equals(AWTurnoManualCertificado.ASOCIAR_TURNO)) {
			return AGREGAR_OK;
		}
         
		return null;

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {
	}

}
