package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWConsultasReparto;
import gov.sir.core.web.acciones.administracion.AWReportes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author mmunoz
 */
public class ControlNavegacionConsultasReparto implements ControlNavegacion{

	public static final String LISTAR_POR_FECHA = "LISTAR_POR_FECHA";
	
	public static final String MINUTA_RADICACION = "MINUTA_RADICACION"; 

	public static final String TERMINAR = "TERMINAR";
	
	public static final String LISTAR_POR_OTORGANTE = "LISTAR_POR_OTORGANTE";
	
	public static final String LISTAR_PENDIENTES = "LISTAR_PENDIENTES";
	
	public static final String MOSTRAR_REPORTE_PENDIENTES = "MOSTRAR_REPORTE_PENDIENTES";
	
	public static final String EDICION_MINUTA = "EDICION_MINUTA";
	
	public static final String MINUTA_ANULADA = "MINUTA_ANULADA";
	
	public static final String CONFIRMACION = "CONFIRMACION";
	
	public static final String VER_DATALLES_EDICION = "VER_DATALLES_EDICION";
	
	public static final String TIPO_BUSQUEDA_SELECCIONADA = "TIPO_BUSQUEDA_SELECCIONADA";
	
	
	
	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {
		
	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = request.getParameter(WebKeys.ACCION).trim();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		} else if(accion.equals(AWConsultasReparto.LISTAR_POR_FECHA)){
			return LISTAR_POR_FECHA;
		} else if(accion.equals(AWConsultasReparto.MINUTA_RADICACION)){
			return MINUTA_RADICACION;			
		} else if(accion.equals(AWConsultasReparto.TERMINA)||accion.equals(AWConsultasReparto.TERMINA_EDICION)){
			return TERMINAR;
		} else if(accion.equals(AWConsultasReparto.LISTAR_POR_OTORGANTE)){
			return LISTAR_POR_OTORGANTE;			
		}  else if(accion.equals(AWConsultasReparto.LISTAR_PENDIENTES)){
			return LISTAR_PENDIENTES;			
		}else if(accion.equals(AWConsultasReparto.MOSTRAR_REPORTE_PENDIENTES)){
			
			String param_Page = (String) request.getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
			HttpSession session  = request.getSession();
			session.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, param_Page);			
			
			return MOSTRAR_REPORTE_PENDIENTES;			
			
		} else if (accion.equals(AWConsultasReparto.AGREGAR_ENTIDAD_PUBLICA)  ||
					accion.equals(AWConsultasReparto.ELIMINAR_ENTIDAD_PUBLICA)  ||
					accion.equals(AWConsultasReparto.AGREGAR_OTORGANTE_NATURAL)  ||
					accion.equals(AWConsultasReparto.ELIMINAR_OTORGANTE_NATURAL) ||
					accion.equals(AWConsultasReparto.CONSULTAR_MINUTA_MODIFICACION) ||
					accion.equals(AWConsultasReparto.AGREGAR_ACCION_NOTARIAL) ||
					accion.equals(AWConsultasReparto.ELIMINAR_ACCION_NOTARIAL)||
					accion.equals(AWConsultasReparto.PRESERVAR_INFO)
					) {
  			return EDICION_MINUTA;
		}  else if(accion.equals(AWConsultasReparto.ANULAR_MINUTA)){
			return CONFIRMACION;
		}  else if(accion.equals(AWConsultasReparto.ENVIAR_EDICION)){
					return VER_DATALLES_EDICION;
		}  else if(accion.equals(AWConsultasReparto.SELECCIONAR_TIPO_BUSQUEDA)){
					return TIPO_BUSQUEDA_SELECCIONADA;			
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
