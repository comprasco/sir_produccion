package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Anotacion;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author ppabon
 *
 * Esta clase imprime en el jsp una lista con el orden y el nombre de la naturaleza jurídica.
 * Sirve especialmente para pintar las anotaciones que tienen gravamenes y medidas cautelares.
 */
public class GrupoNaturalezaJuridicaHelper extends Helper {


	/**
	 * Lista de anotaciones a pintar.
	*/
	private List listData;
	
	/**
	 * Nombre de la lista de anotaciones que se encuentra en la sesión.
	*/
	private String listName = "";	
	
	/**
	 * Nombre del grupo de la Naturaleza Jurídica que se esta mostrando.
	*/
	private String grupoNaturalesJuridica = "";		
	

	/**
	 * 
	 */
	public GrupoNaturalezaJuridicaHelper(String listName, String grupoNaturalesJuridica) {
		super();
		this.listName = listName;
		this.grupoNaturalesJuridica = grupoNaturalesJuridica;		
	}
	
	/**
	 * 
	 */
	public GrupoNaturalezaJuridicaHelper() {
		super();
	}	

	/**
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute(this.listName) != null) {
			this.listData = (List) session.getAttribute(this.listName);
			if(this.listData==null){
				this.listData = new ArrayList();
			}
		} else {
			this.listData = new ArrayList();
		}
	}

	/**
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		if(this.listData.size()>0){          
			out.write("<table width=\100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td width=\"30%\">Anotaci&oacute;n </td>");
			out.write("<td width=\"70%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+(grupoNaturalesJuridica!=null?grupoNaturalesJuridica:"")+"</td>");
			out.write("</tr>");
			
			Iterator ig = listData.iterator();	
			while(ig.hasNext()){
				Anotacion anotacion = (Anotacion)ig.next();
				out.write("<tr>");
				out.write("<td width=\"20\">&nbsp;</td>");
				out.write("<td width=\"30%\" class=\"campositem\">"+(anotacion.getOrden()!=null?anotacion.getOrden():"ID:"+anotacion.getIdAnotacion())+"</td>");
				out.write("<td width=\"70%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+(anotacion.getNaturalezaJuridica()!=null?anotacion.getNaturalezaJuridica().getNombre():"-")+"</td>");                          
				out.write("</tr>");
			}

			out.write("</table>");

		}else{
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td width=\"40\">&nbsp;</td>");
			out.write("<td>El folio No tiene "+(grupoNaturalesJuridica!=null?grupoNaturalesJuridica:"")+"</td>");
			out.write("</tr>");
			out.write("</table>");            
		}

	}

	/**
	 * @return
	 */
	public List getListData() {
		return listData;
	}

	/**
	 * @return
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * @param string
	 */
	public void setListName(String string) {
		listName = string;
	}

	/**
	 * @return
	 */
	public String getGrupoNaturalesJuridica() {
		return grupoNaturalesJuridica;
	}

	/**
	 * @param string
	 */
	public void setGrupoNaturalesJuridica(String string) {
		grupoNaturalesJuridica = string;
	}

}
