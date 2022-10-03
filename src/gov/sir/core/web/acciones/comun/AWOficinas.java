package gov.sir.core.web.acciones.comun;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnOficinas;
import gov.sir.core.eventos.comun.EvnRespOficinas;
import gov.sir.core.negocio.acciones.comun.ANOficinas;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.util.ContenedorTiposyNumerosOficinas;
import gov.sir.core.negocio.modelo.util.HashTableToListConverter;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;

/**
 * @author mmunoz
 */
public class AWOficinas extends SoporteAccionWeb {

	/**Constante que identifica la variable que se pide del HttpServletRequest que
	 * contiene el identificador del departamento
	 */
	public static final String ID_DEPTO = "SELECCIONAR_OFICINA_ID_DEPTO";
	
	/**Constante que identifica la variable que se pide del HttpServletRequest que
	 * contiene el identificador del municipio
	 */	
	public static final String ID_MUNIC = "SELECCIONAR_OFICINA_ID_MUNIC";
	
	/**Constante que identifica la variable que se pide del HttpServletRequest que
	 * contiene el identificador de la vereda
	 */
	public static final String ID_VEREDA = "SELECCIONAR_OFICINA_ID_VEREDA";

	public static final String OFICINA_HELPER_MANUAL = "OFICINA_HELPER_MANUAL";
	


	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		String idVereda = request.getParameter(AWOficinas.ID_VEREDA);
		String idDepartamento = request.getParameter(AWOficinas.ID_DEPTO);
		String idMunic = request.getParameter(AWOficinas.ID_MUNIC);
		String vManual= request.getParameter(AWOficinas.OFICINA_HELPER_MANUAL);
		
		if(vManual!=null){
			if(vManual.equals("true")){
				request.getSession().setAttribute(AWOficinas.OFICINA_HELPER_MANUAL, "true");
			}
			else{
				request.getSession().setAttribute(AWOficinas.OFICINA_HELPER_MANUAL, "false");
			}
		}else{
			request.getSession().setAttribute(AWOficinas.OFICINA_HELPER_MANUAL, "false");
		}
		
	
		//if ( (idVereda == null || idVereda.trim().equals(""))&&  idDepartamento!=null && idMunic!=null) {
			idVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), idDepartamento, idMunic);
		//}
					
		session.removeAttribute(WebKeys.TABLA_TIPOS_OFICINAS);
		if(idVereda != null && idDepartamento != null && idMunic != null){
			if(idVereda.length()>0 && idDepartamento.length()>0 && idMunic.length()>0){
				Vereda vereda = new Vereda();
				vereda.setIdDepartamento(idDepartamento);
				vereda.setIdMunicipio(idMunic);
				vereda.setIdVereda(idVereda);
				org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
				return new EvnOficinas(usuario,vereda);
			}
		}

		return null;
	}
	
	/**
	 * Carga el listado de tipos de oficinas origen, dado un departamento y municipio
	 * Método llamado via AJAX
	 * @param request
	 * @param idDepartamento
	 * @param idMunicipio
	 * @return List
	 */
	public ContenedorTiposyNumerosOficinas cargarTiposOficinas(HttpServletRequest request,
			String idDepartamento,
			String idMunicipio){
		List listaTiposOficinas = new ArrayList();
		String idVereda = "";
		ContenedorTiposyNumerosOficinas contenedor = new ContenedorTiposyNumerosOficinas();
		
		if ( (idVereda == null || idVereda.trim().equals(""))&&  idDepartamento!=null && idMunicipio!=null) {
			idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(
					(List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), 
					idDepartamento, idMunicipio);
		}
		
		if(idVereda != null && idDepartamento != null && idMunicipio != null){
				Vereda vereda = new Vereda();
				vereda.setIdDepartamento(idDepartamento);
				vereda.setIdMunicipio(idMunicipio);
				vereda.setIdVereda(idVereda);
				org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
				EvnOficinas evnOficinas = new EvnOficinas(usuario,vereda);
				ANOficinas anOficinas = null;
				EvnRespOficinas respuesta = null;
				try {
					anOficinas = new ANOficinas();
					respuesta = (EvnRespOficinas)anOficinas.darOficinas(evnOficinas);
				} catch (EventoException e) {
					Log.getInstance().error(AWOficinas.class, e);
				}
				
				HashTableToListConverter.convert(listaTiposOficinas, respuesta.getOficinas());
				request.getSession().setAttribute(WebKeys.TABLA_TIPOS_OFICINAS,respuesta.getOficinas());
				request.getSession().setAttribute(WebKeys.LISTA_TIPOS_OFICINA,listaTiposOficinas);
				
				contenedor.setListaTiposOficinas(listaTiposOficinas);
		}
		
		return contenedor;
	}
	
	/**
	 * Método que obtiene los numeros de oficina asociados a un tipo
	 * Método llamado via AJAX
	 * @param contenedor
	 * @param idTipoOficina
	 * @return ContenedorTiposyNumerosOficinas
	 */
	public ContenedorTiposyNumerosOficinas cargarOficinas(
			HttpServletRequest request,
			ContenedorTiposyNumerosOficinas contenedor,
			String idTipoOficina){
		Hashtable tabla = (Hashtable)request.getSession().getAttribute(WebKeys.TABLA_TIPOS_OFICINAS);
		List listaTipos = (List)request.getSession().getAttribute(WebKeys.LISTA_TIPOS_OFICINA);
		Iterator itListaTipos = listaTipos.iterator();
		while(itListaTipos.hasNext()){
			TipoOficina tipoOficina = (TipoOficina)itListaTipos.next();
			if(tipoOficina.getIdTipoOficina().equals(idTipoOficina)){
				contenedor.setListaNumerosOficinas((List)tabla.get(tipoOficina));
				break;
			}
		}
		return contenedor;
	}
        
        /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
	 * Método que obtiene los numeros de oficina asociados a un tipo
	 * Método llamado via AJAX
	 * @param contenedor
	 * @param idTipoOficina
	 * @return ContenedorTiposyNumerosOficinas
	 */
	public OficinaOrigen cargarOficina(HttpServletRequest request,String idOficina){
		OficinaOrigen of = null;
                if(idOficina!=null && !"".equals(idOficina))
                {
                                org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
				EvnOficinas evnOficinas = new EvnOficinas(usuario,idOficina);
				ANOficinas anOficinas = null;
				EvnRespOficinas respuesta = null;
				try {
					anOficinas = new ANOficinas();
					respuesta = (EvnRespOficinas)anOficinas.darOficina(evnOficinas);
                                        of = respuesta.getOficina();
				} catch (EventoException e) {
					Log.getInstance().error(AWOficinas.class, e);
				}
                }
		return of;
	}
	
	
	/**
	 * Metodo que agrega un oficio de pertenencia al turno.
	 * @param request HttpServletRequest
	 * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	 * @throws AccionWebException
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespOficinas respuesta = (EvnRespOficinas) evento;
		HttpSession session = request.getSession();
		if(respuesta != null){
			if(respuesta.getTipoEvento().equals(EvnRespOficinas.TABLA_OFICINAS)){
				session.setAttribute(WebKeys.TABLA_TIPOS_OFICINAS,respuesta.getOficinas());
			}
		}
   	
	}

}
