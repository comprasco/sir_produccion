/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import java.util.List;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWConsultaFolio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWConsultaFolio</CODE>
 * 
 * @author ppabon
 *
 */
public class ControlNavegacionConsultaFolio implements ControlNavegacion {

	/** Constante que identifica la acción de consultar un  folio */
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	/** Constante que identifica la acción de consultar un  folio */
	public static final String REGRESAR_INICIO = "REGRESAR_INICIO";	

	/** Constante que identifica la acción de desbloqueo del folio se realizo */
	public static final String DESBLOQUEO_FOLIO_OK = "DESBLOQUEO_FOLIO_OK";	
	
	/**
	 *  Constructor por Default de <CODE>ControlNavegacionConsultaFolio</CODE>
	 */
	public ControlNavegacionConsultaFolio() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		HttpSession sesion = request.getSession();
		
		String vista = "";

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		}
		if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO)) {
			return AWConsultaFolio.CONSULTAR_FOLIO;
		}else if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO_REABRIR)) {
			return AWConsultaFolio.CONSULTAR_FOLIO_REABRIR;
		}else if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION)) {
			return AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION;
		}else if (accion.equals(AWConsultaFolio.ANULAR_FOLIO)) {
			return AWConsultaFolio.ANULAR_FOLIO;
		}else if(accion.equals(AWConsultaFolio.REGRESAR_INICIO)) {			
			List listaConsultas = (List) sesion.getAttribute(WebKeys.LISTA_CONSULTAS);		 
			if(listaConsultas.size()>0){
				return AWConsultaFolio.CONSULTAR_FOLIO;	
			}else{
				//return AWConsultaFolio.REGRESAR_INICIO;
				vista = (String) request.getSession().getAttribute(
				AWConsultaFolio.VISTA_VOLVER);
				sesion.removeAttribute(AWConsultaFolio.VISTA_VOLVER);
				return vista;
			}

		} else if (accion.equals(AWConsultaFolio.DESBLOQUEAR_FOLIO_RESTITUCION)) {
			return DESBLOQUEO_FOLIO_OK;
		} 

		return null;
	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
