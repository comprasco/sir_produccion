package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnReasignacionTurnos;
import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.eventos.comun.EvnSistema;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CListaContexto;
import gov.sir.core.web.InitParametrosServlet;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * de administración de ciudadanos provenientes de solicitudes 
 * a través del protocolo HTTP
 * @author jmendez
 *
 */
public class AWRecargarListasContexto extends SoporteAccionWeb {

	/** Constante que identifica la lista de listas de contexto seleccionadas por el usuario en session */
	public static final String RT_LISTAS_CONTEXTO_SELECCIONADOS = "RT_LISTAS_CONTEXTO_SELECCIONADOS";
	
	/** Constante que identifica la lista de listas de contexto en session */
	public static final String RT_LISTAS_CONTEXTO = "RT_LISTAS_CONTEXTO";
	
	/** Constante que identifica la acción de cargar en sesión la lista listas del contexto */
    public static final String CARGAR_LISTAS_CONTEXTO = "CARGAR_LISTAS_CONTEXTO";
    
	/** Constante que identifica la acción de efectuar la recarga de las listas de contexto seleccionadas */
	public static final String RECARGAR_LISTAS_CONTEXTO = "RECARGAR_LISTAS_CONTEXTO";
	
	/** Constante que identifica la acción de efectuar la recarga de las listas de contexto seleccionadas */
	public static final String CARGAR_LISTAS_CONTEXTO_OK = "CARGAR_LISTAS_CONTEXTO_OK";

	/** Constante que identifica las acción de terminar la utilización de los servicios 
	 * de la acción WEB (Para limpiar la sesión y redirigir a la página principal de páginas
	 * administrativas */
	public static final String TERMINA = "TERMINA";


	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO)) {
			return cargarListas(request);
		} else if (accion.equals(AWRecargarListasContexto.RECARGAR_LISTAS_CONTEXTO)) {
			return recargarListas(request);
		} else if (accion.equals(AWRecargarListasContexto.TERMINA)) {
			return limpiarDatosSesion(request);
		} 
		return null;
	}


	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de subir a la sesión del usuario la lista
	 * de listas de contexto
	 * @param request
	 * @return evento <code>EvnRecargarListasContexto</code>
	 * @throws AccionWebException
	 */
	private EvnSistema cargarListas(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		EvnSistema evento =
			new EvnSistema(usuarioAuriga, EvnSistema.CARGAR_LISTAS);
		return evento;
	}

	/**
	 * Este método se encarga de subir a la sesión del usuario la lista
	 * de listas de contexto
	 * @param request
	 * @return evento <code>EvnRecargarListasContexto</code>
	 * @throws AccionWebException
	 */
	private EvnSistema recargarListas(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		List listasARecargar = new ArrayList();
		String[] listas = request.getParameterValues(CListaContexto.ID_LISTA_CONTEXTO);
		
		if(listas !=null && listas.length >0){
			for (int i = 0; i < listas.length; i++) {
				String nombreLista = listas[i];
				listasARecargar.add(nombreLista);
				//Obliga la recarga de las Zonas Registrales al recargar la lista de Departamentos
				if (nombreLista.equals(EvnRespSistema.LISTA_DEPARTAMENTOS)){
					Circulo circulo= (Circulo)session.getAttribute(WebKeys.CIRCULO);
					String nomLista;
					if (circulo != null){
						nomLista = CCirculo.CIRCULO + "-"  + circulo.getIdCirculo();
						session.getServletContext().setAttribute(nomLista, null);
					}
				}
			}
		}
		
		session.setAttribute(RT_LISTAS_CONTEXTO_SELECCIONADOS , listasARecargar);
		
		if(listasARecargar==null || listasARecargar.size()==0){
			exception.addError("Debe seleccionar por lo menos una lista a recargar.");
		}
		
		if (!exception.getErrores().isEmpty())
		{
			throw exception;
		}
		
		EvnSistema evento =
			new EvnSistema(usuarioAuriga, EvnSistema.RECARGAR_LISTAS, listasARecargar);
		return evento;
	}
	
	/**
	 * Este método se encarga de eliminar los objetos que se subieron a la sesión en la ejecución de la recarga de listas.
	 * @param request
	 * @return evento <code>EvnReasignacionTurnos</code>.
	 * @throws AccionWebException
	 */
	private EvnReasignacionTurnos limpiarDatosSesion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		session.removeAttribute(RT_LISTAS_CONTEXTO_SELECCIONADOS);
		session.removeAttribute(RT_LISTAS_CONTEXTO);
		
		return null;
	}
	
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespSistema respuesta = (EvnRespSistema) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnSistema.RECARGAR_LISTAS)) {
				Hashtable ht = respuesta.getParams();
				if (ht != null)
				{
					InitParametrosServlet.cargarListasEnContexto(ht, session.getServletContext());
				}
				return;
			} else if (tipoEvento.equals(EvnSistema.CARGAR_LISTAS)) {
				session.setAttribute(RT_LISTAS_CONTEXTO, respuesta.getListasContexto());;
			}
		}
				
	}
	
	

}
