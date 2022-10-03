package gov.sir.core.web.acciones.comun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnLocacion;
import gov.sir.core.eventos.comun.EvnRespLocacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;

/**
 * @author mmunoz
 */
public class AWLocacion extends SoporteAccionWeb {

	/**Esta constante identifica cuando se quiere la lista para ciertas locaciones*/
	public static final String LOCACIONES_CIRCULO = "LOCACIONES_CIRCULO";
	
	/**Esta constante identifica cuando no se quiere la lista para ciertas locaciones*/
	private static final String LOCACION_SIN_CIRCULO = "LOCACION_SIN_CIRCULO"; 
	
	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		
		Circulo circulo = null;
		HttpSession session = request.getSession();
		
		String mostrarVereda = request.getParameter(CVereda.MOSTRAR_VEREDA);
		request.getSession().setAttribute(CVereda.MOSTRAR_VEREDA,mostrarVereda);
		
		String locaciones = request.getParameter(AWLocacion.LOCACIONES_CIRCULO);
		if(locaciones != null && locaciones.length()>0){
			circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO); 
		} else {
			session.setAttribute(WebKeys.ACCION,LOCACION_SIN_CIRCULO);
			return null;
		}
		
		String nomLista =  CCirculo.CIRCULO + "-" + circulo.getIdCirculo();
		List listaLocaciones = (List)session.getServletContext().getAttribute(nomLista);
					
		if(listaLocaciones == null){
			org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			return new EvnLocacion(usuario,circulo);
		} 
		return null;
	}
	
	/**
	 * Método que retorna la lista de departamentos.
	 * Es llamado por AJAX
	 * @param locacionesCirculo indica si se debe cargar la lista por circulo o general
	 * @return List
	 */
	public List cargarDepartamentos(HttpServletRequest request, String locacionesCirculo){
		List departamentos = new ArrayList();
		HttpSession session = request.getSession();
		Circulo circulo = null;
		ArrayList listaLivianaDepartamentos = new ArrayList();
		
		if(locacionesCirculo.equals(AWLocacion.LOCACIONES_CIRCULO)){
			circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		}
		
		List listaLocaciones = new ArrayList();
		if(locacionesCirculo.equals(AWLocacion.LOCACIONES_CIRCULO)){
			String nomLista =  CCirculo.CIRCULO + "-" + circulo.getIdCirculo();
			listaLocaciones = (List)session.getServletContext().getAttribute(nomLista);
		}else{
			listaLocaciones = (List)session.getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
		}
		
		if(listaLocaciones==null){
			try {
				org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
				EvnLocacion evnLocacion = new EvnLocacion(usuario,circulo);
				ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
				
				EvnRespLocacion evnRespLocacion = (EvnRespLocacion)proxy.manejarEvento(evnLocacion);
				departamentos = evnRespLocacion.getDepartamentos();
				if(locacionesCirculo.equals(AWLocacion.LOCACIONES_CIRCULO)){
					session.getServletContext().setAttribute(CCirculo.CIRCULO + "-" + circulo.getIdCirculo(),departamentos);
				}else{
					session.getServletContext().setAttribute(WebKeys.LISTA_DEPARTAMENTOS,departamentos);
				}
				session.setAttribute(WebKeys.LISTA_DEPARTAMENTOS,departamentos);
			} catch (EventoException e) {
				// TODO Auto-generated catch block
				Log.getInstance().error(AWLocacion.class, e);
			}
		}else{
			departamentos = listaLocaciones;
		}
		
		//Se va a retornar una lista mas 'liviana' para mejorar el desempeño
		if(departamentos!=null){
			Iterator itDepartamentos = departamentos.iterator();
			while(itDepartamentos.hasNext()){
				Departamento departamento = new Departamento();
				Departamento departamentoNuevo = new Departamento();
				departamento = (Departamento)itDepartamentos.next();
				
				//Se insertan los datos filtrados al departamento nuevo
				departamentoNuevo.setIdDepartamento(departamento.getIdDepartamento());
				departamentoNuevo.setNombre(departamento.getNombre());
				
				//Se itera sobre los municipios asociados al departamento
				if(departamento.getMunicipios()!=null){
					Iterator itMunicipios = departamento.getMunicipios().iterator();
					while(itMunicipios.hasNext()){
						Municipio municipio = (Municipio)itMunicipios.next();
						Municipio municipioNuevo = new Municipio();
						municipioNuevo.setIdMunicipio(municipio.getIdMunicipio());
						municipioNuevo.setNombre(municipio.getNombre());
						departamentoNuevo.addMunicipio(municipioNuevo);
					}
				}
				
				//Se inserta el nuevo departamento en la lista
				listaLivianaDepartamentos.add(departamentoNuevo);
			}
		}
		
		return listaLivianaDepartamentos;
	}
	
	
	/**
	 * Metodo que agrega un oficio de pertenencia al turno.
	 * @param request HttpServletRequest
	 * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	 * @throws AccionWebException
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespLocacion respuesta = (EvnRespLocacion) evento;
		HttpSession session = request.getSession();
		
		if(respuesta != null){
			if(respuesta.getTipoEvento().equals(EvnRespLocacion.LISTA_DEPTOS)){
				Circulo circulo = respuesta.getCirculo();
				String nomLista = WebKeys.LISTA_DEPARTAMENTOS;
				if(circulo != null){
					nomLista = CCirculo.CIRCULO + "-"  + circulo.getIdCirculo();	
				}
				
				session.getServletContext().setAttribute(nomLista, respuesta.getDepartamentos());
				session.setAttribute(WebKeys.LISTA_DEPARTAMENTOS,respuesta.getDepartamentos());
			}
		} else {
			Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
			String accion = "";
			accion =(String)session.getAttribute(WebKeys.ACCION); 
			if(accion != null){
				session.setAttribute(WebKeys.LISTA_DEPARTAMENTOS,session.getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS));
				session.removeAttribute(WebKeys.ACCION);
			} else {
				session.setAttribute(WebKeys.LISTA_DEPARTAMENTOS,session.getServletContext().getAttribute(CCirculo.CIRCULO + "-" + circulo.getIdCirculo()));	
			}
		}
   	}
}
