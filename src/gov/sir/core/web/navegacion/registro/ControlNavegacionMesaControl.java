/*
 * Created on 25-oct-2004
*/
package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWMesa;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author jfrias
*/
public class ControlNavegacionMesaControl implements ControlNavegacion {
	public static final String CONSULTAR_TURNOS = "CONSULTAR_TURNOS";
	
	

	/**
	 * 
	 */
	public ControlNavegacionMesaControl() {
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
			if (accion.equals(AWMesa.CONSULTAR_TURNOS)) {
				return AWMesa.CONSULTAR_TURNOS;
			} else if (accion.equals(AWMesa.ENVIAR_RESPUESTA)){
				return AWMesa.ENVIAR_RESPUESTA;
			} else if (accion.equals(AWMesa.DEVOLVER_REVISION)){
				return AWMesa.DEVOLVER_REVISION;
			} else if (accion.equals(AWMesa.VALIDAR_SOLICITUDES)){
				return AWMesa.VALIDAR_SOLICITUDES;
			} else if (accion.equals(AWMesa.IMPRIMIR_CERTIFICADO)){
				return AWMesa.IMPRIMIR_CERTIFICADO;
			} else if (accion.equals(AWMesa.IMPRIMIR_CERTIFICADO_RELACION)){
				return AWMesa.IMPRIMIR_CERTIFICADO_RELACION;
			} else if (accion.equals(AWMesa.CONSULTAR_RELACION)){
				return AWMesa.IMPRIMIR_CERTIFICADO_RELACION;
			} else if (accion.equals(AWMesa.CAMBIAR_MATRICULA)){
				return AWMesa.CAMBIAR_MATRICULA;
			} else if (accion.equals(AWMesa.AVANZAR_MESA_II)){
				return AWMesa.AVANZAR_MESA_II;
			} else if (accion.equals(AWMesa.CARGAR_CAMBIAR_MATRICULA) || 
					   accion.equals(AWMesa.CAMBIAR_MATRICULA_RELACION)){
				return AWMesa.CAMBIAR_MATRICULA_RELACION;
			}/**Devolver testamento. Pablo QuintanaJunio 19 2008*/
			else if(accion.equals(AWMesa.DEVOLVER_TESTAMENTO)){
				return AWMesa.DEVOLVER_REVISION;
			}
			
			return null;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest request) {

	}

}
