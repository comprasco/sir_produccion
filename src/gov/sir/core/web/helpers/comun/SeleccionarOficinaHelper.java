package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.web.WebKeys;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * @author mmunoz
 */
public class SeleccionarOficinaHelper extends Helper{
	
	public static final String OFICINA_DOCUMENTO_ID_OFICINA = "OFICINA_DOCUMENTO_ID_OFICINA";

	public static final String OFICINA_DOCUMENTO_NUM = "OFICINA_DOCUMENTO_NUM";

	public static final String OFICINA_DOCUMENTO_TIPO = "OFICINA_DOCUMENTO_TIPO";

	public static final String OFICINA_DOCUMENTO_ID_TIPO = "OFICINA_DOCUMENTO_ID_TIPO";
	
	public static final String LISTA_ELEMENTOS_TIPOS_OFICINA = "LISTA_ELEMENTOS_TIPOS_OFICINA";
	
	public static final String LISTA_ELEMENTOS_NUMEROS_OFICINA = "LISTA_ELEMENTOS_NUMEROS_OFICINA";
	
	public String nombreTipoOficina;
	
	public String idTipoOficina;
	
	public String numOficina;
	
	public String idOficina;
	
	public List elementosNumerosOficina;
	
	public List elementosTiposOficina;
	
	private ListaElementoHelper tiposOficinaHelper;
	
	private ListaElementoHelper numOficinaHelper;
	
	private boolean mostrarBoton = false;
	
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		
		HttpSession session = request.getSession();
		Hashtable tablaTipos = (Hashtable)session.getAttribute(WebKeys.TABLA_TIPOS_OFICINAS);

		if(tablaTipos == null){
			throw new HelperException("No se pudo cargar la informacion de las oficinas. No se proporciono una vereda.");
		}
			
		elementosTiposOficina = (List)session.getAttribute(LISTA_ELEMENTOS_TIPOS_OFICINA);
		if(elementosTiposOficina == null){
			elementosTiposOficina = new Vector();
			Enumeration llaves = tablaTipos.keys();
			while(llaves.hasMoreElements()){
				TipoOficina tipo = (TipoOficina)llaves.nextElement();
				elementosTiposOficina.add(new ElementoLista(tipo.getIdTipoOficina(),tipo.getNombre()));
			}
			session.setAttribute(LISTA_ELEMENTOS_TIPOS_OFICINA,elementosTiposOficina);
		} 
			elementosNumerosOficina = (List) session.getAttribute(LISTA_ELEMENTOS_NUMEROS_OFICINA);


		idTipoOficina = request.getParameter(OFICINA_DOCUMENTO_ID_TIPO);
		elementosNumerosOficina = new Vector();
		if(idTipoOficina != null){
			session.setAttribute(OFICINA_DOCUMENTO_ID_TIPO,idTipoOficina);
			List elementos = (List) session.getAttribute(LISTA_ELEMENTOS_TIPOS_OFICINA);
			Iterator itElementos = elementos.iterator();
			List oficinasOrigen = new Vector();
			while(itElementos.hasNext()){
				ElementoLista elemento = (ElementoLista) itElementos.next();
				if(elemento.getId().equals(idTipoOficina)){
					TipoOficina tipo = new TipoOficina();
					tipo.setIdTipoOficina(elemento.getId());
					tipo.setNombre(elemento.getValor());
					nombreTipoOficina = tipo.getNombre();
					session.setAttribute(OFICINA_DOCUMENTO_TIPO,nombreTipoOficina);
					oficinasOrigen = (List) tablaTipos.get(tipo);
				}
			}
                        /*
                          *  @author Carlos Torres
                          *  @chage   se agrega validacion de version diferente
                          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                          */
                        Map objetos = new LinkedHashMap();
                        for(Object ofo:oficinasOrigen)
                        {
                            OficinaOrigen of = (OficinaOrigen)ofo;
                            if(objetos.containsKey(of.getIdOficinaOrigen()))
                            {
                                OficinaOrigen of1 = (OficinaOrigen)objetos.get(of.getIdOficinaOrigen());
                                if(of.getVersion()>of1.getVersion())
                                {
                                    objetos.put(of.getIdOficinaOrigen(), of);
                                }               
                            }else
                            {
                                objetos.put(of.getIdOficinaOrigen(), of);
                            }
                            
                        }
			Iterator itNumeros = objetos.values().iterator();
			while(itNumeros.hasNext()){
				OficinaOrigen oficina = (OficinaOrigen)itNumeros.next();
				String nomOficOrig = null; 
				if(oficina.getNombre()!=null){
					nomOficOrig = oficina.getNombre(); 
				}
				if(nomOficOrig == null && oficina.getNumero()!=null){
					nomOficOrig = oficina.getNumero(); 
				}
				if(nomOficOrig == null){
					nomOficOrig = oficina.getIdOficinaOrigen();
				}
                                  /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
				elementosNumerosOficina.add(new ElementoLista(oficina.getIdOficinaOrigen()+"-"+oficina.getVersion(),nomOficOrig));
			}
			session.setAttribute(LISTA_ELEMENTOS_NUMEROS_OFICINA,elementosNumerosOficina);
		}
		
		if(elementosNumerosOficina != null){
			idOficina = request.getParameter(OFICINA_DOCUMENTO_ID_OFICINA);
			if(idOficina != null){
				List numeros = (List)session.getAttribute(LISTA_ELEMENTOS_NUMEROS_OFICINA);
				Iterator itNumeros = numeros.iterator();
				while(itNumeros.hasNext()){
					ElementoLista elemento = (ElementoLista) itNumeros.next();
					if(elemento.getId().equals(idOficina)){
						numOficina=elemento.getValor();
						session.setAttribute(OFICINA_DOCUMENTO_ID_OFICINA,idOficina);
						mostrarBoton = true;
					}
				}
			}
		}
		
		
		tiposOficinaHelper = new ListaElementoHelper();
		numOficinaHelper = new ListaElementoHelper();
		
		tiposOficinaHelper.setCssClase("campoformlista");
		tiposOficinaHelper.setId(OFICINA_DOCUMENTO_ID_TIPO);
		tiposOficinaHelper.setNombre(OFICINA_DOCUMENTO_ID_TIPO);
		tiposOficinaHelper.setTipos(elementosTiposOficina);
		tiposOficinaHelper.setFuncion("onChange=\"submitform();\"");
		
		numOficinaHelper.setCssClase("campoformlista");
		numOficinaHelper.setId(OFICINA_DOCUMENTO_ID_OFICINA);
		numOficinaHelper.setNombre(OFICINA_DOCUMENTO_ID_OFICINA);
		numOficinaHelper.setFuncion("onChange=\"submitform();\"");
		numOficinaHelper.setTipos(elementosNumerosOficina);
	}
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		out.write("<input name=\"text\" type=\"hidden\" id=\"tipo_00\" value=\"\">");
		out.write("<input name=\"text\" type=\"hidden\" id=\"tipo_nom_00\" value=\"\">");
		out.write("<input name=\"text\" type=\"hidden\" id=\"numero_00\" value=\"\">");
		out.write("<input name=\"text\" type=\"hidden\" id=\"id_oficina_00\" value=\"\">");
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                out.write("<input name=\"text\" type=\"hidden\" id=\"version_00\" value=\"\">");
		out.write("<br>");
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("<td class=\"bgnsub\">Selecci&oacute;n Tipo Oficina - Numero Oficina </td>");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_tipo_oficina.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" class=\"camposform\" >");
		out.write("<tr>");
		out.write("<td width=\"50%\" valign=\"top\">");
		out.write("<table width=\"100%\" class=\"contenido\">");
		out.write("<tr>");
		out.write("<td>Tipo de Oficina </td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>");
		
		tiposOficinaHelper.render(request,out);
		
		out.write("<input type=\"hidden\" id=\"" + OFICINA_DOCUMENTO_TIPO+"\" value=\"" + (String)request.getSession().getAttribute(OFICINA_DOCUMENTO_TIPO) + "\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td width=\"50%\" valign=\"top\">");
		out.write("<table width=\"100%\" class=\"contenido\">");
		out.write("<tr>");
		out.write("<td>Numero de Oficina </td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>");
		
		numOficinaHelper.render(request,out);
		
		out.write("<input type=\"hidden\" id=\"" + OFICINA_DOCUMENTO_NUM + "\" value=\"" + numOficina + "\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		if(mostrarBoton){
			out.write("<table width=\"100%\" class=\"camposform\" >");
			out.write("<tr>");
			out.write("<td align=\"center\"><a href=\"javascript: value_formulario(document.formulario." + OFICINA_DOCUMENTO_ID_TIPO + ".value,document.formulario." + OFICINA_DOCUMENTO_TIPO + ".value,document.formulario." + OFICINA_DOCUMENTO_NUM + ".value,document.formulario." + OFICINA_DOCUMENTO_ID_OFICINA + ".value);\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_agregar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\"></a></td>");
			out.write("</tr>");
			out.write("</table>");
		}
		removerAtributos(request);
	}

	/**
	 * @param request
	 */
	private void removerAtributos(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(LISTA_ELEMENTOS_NUMEROS_OFICINA);
		session.removeAttribute(LISTA_ELEMENTOS_TIPOS_OFICINA);
		session.removeAttribute(OFICINA_DOCUMENTO_ID_OFICINA);
		session.removeAttribute(OFICINA_DOCUMENTO_ID_TIPO);
		session.removeAttribute(OFICINA_DOCUMENTO_NUM);
		session.removeAttribute(OFICINA_DOCUMENTO_TIPO);
	}
}
