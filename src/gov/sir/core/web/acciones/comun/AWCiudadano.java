/*
 * Created on 21-ene-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.acciones.comun;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author eacosta, ppabon
 * Esta accion web se encarga de generar eventos de negocio relacionados con la consulta a la tabla de 
 * ciudadanos para determinar si se debe o no ingresar un nuevo ciudadano.
 * Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 */
public class AWCiudadano extends SoporteAccionWeb {
	/** Constante que identifica que se desea obtener los detalles del ciudadano*/
	public static final String CONSULTAR = "CONSULTAR";

	/** Constante que identifica que se desea eliminar de la sesión la información que se 
	 * ha consultado de un ciudadano*/
	public static final String ELIMINAR = "ELIMINAR";
		
	/**Accion que selecciono el usuario */
	private String accion;

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.length() == 0)) {
			throw new AccionWebException("Debe indicar una accion");
		}

		if (accion.equals(CONSULTAR)) {
			return consultar(request);
		} else if (accion.equals(ELIMINAR)) {			
			return null;
		} else {
			throw new AccionWebException(
				"Debe indicar una accion valida. La accion " + accion +
				" no es valida");
		}
	}
	
	
	/**
	 * Este método es llamado cuando se quiere consultar un ciudadano para determinar si se
	 * debe o no ingresar a la base de datos.
	 * @param request HttpServletRequest
	 * @return EvnTurno 
	 */
	private EvnCiudadano consultar(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuario=(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionParametrosCiudadanoException errores = new ValidacionParametrosCiudadanoException();
		
		String tipoDocumento = request.getParameter(CCiudadano.TIPODOC);
		String idCiudadano = request.getParameter(CCiudadano.IDCIUDADANO);
		String nombre = request.getParameter(CCiudadano.NOMBRE);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String telefono = request.getParameter(CCiudadano.TELEFONO);
		
		request.getSession().setAttribute(CCiudadano.TIPODOC, tipoDocumento);
		request.getSession().setAttribute(CCiudadano.IDCIUDADANO, idCiudadano);
		request.getSession().setAttribute(CCiudadano.NOMBRE, nombre);
		request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
		request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
		request.getSession().setAttribute(CCiudadano.TELEFONO, telefono);
		
		if(tipoDocumento == null || tipoDocumento.trim().equals("SIN_SELECCIONAR")){
			errores.addError("Debe seleccionar un tipo de Documento");
		}
		
		if ((idCiudadano == null) || idCiudadano.trim().equals("")) {
			errores.addError("El usuario no es valido");
		}
		
		if (errores.getErrores().size()>0){
			throw errores;
		}

		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setTipoDoc(tipoDocumento);
		ciudadano.setDocumento(idCiudadano);
		
       	//Se setea el circulo del ciudadano
        Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		return new EvnCiudadano(usuario,EvnCiudadano.CONSULTAR,ciudadano);
	}
	
	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        
		HttpSession session = request.getSession();
		
		for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			String value = (String) request.getParameter(key);
			session.setAttribute(key,value);
		}
		
        
		if(request.getParameter(WebKeys.ACCION).equals(AWCiudadano.CONSULTAR)){
			EvnRespCiudadano respuesta = (EvnRespCiudadano) evento;
			if(respuesta != null){
				Ciudadano ciudadano = respuesta.getCiudadano();
				request.getSession().setAttribute(WebKeys.VALIDAR_CIUDADANO,ciudadano);
			} else{
				request.getSession().removeAttribute(WebKeys.VALIDAR_CIUDADANO);
			}
			
			request.getSession().setAttribute(WebKeys.CIUDADANO_VERIFICADO, new Boolean(respuesta.isCiudadanoEncontrado()));
		} else if (request.getParameter(WebKeys.ACCION).equals(AWCiudadano.ELIMINAR)){
			session.removeAttribute(WebKeys.VALIDAR_CIUDADANO);
			session.removeAttribute(WebKeys.CIUDADANO_VERIFICADO);

			session.removeAttribute(CCiudadano.TIPODOC);
			session.removeAttribute(CCiudadano.IDCIUDADANO);
			session.removeAttribute(CCiudadano.NOMBRE);
			session.removeAttribute(CCiudadano.APELLIDO1);
			session.removeAttribute(CCiudadano.APELLIDO2);
			session.removeAttribute(CCiudadano.TELEFONO);			
		}
	}
	
}
