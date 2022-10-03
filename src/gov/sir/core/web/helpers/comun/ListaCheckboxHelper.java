/*
 * Created on 11-oct-2004
*/
package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class ListaCheckboxHelper extends Helper {    
	
	private List tipos;
	private String cssClase;
	private String id;

	/**
	 * 
	 */
	public ListaCheckboxHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
		if (tipos == null || tipos.isEmpty()) { 
			out.write("No hay datos");
		}
		else{
			for (int i=0;i<tipos.size();i++){
				ElementoLista e=(ElementoLista)tipos.get(i);
				String[] preservar=(String[])request.getSession().getAttribute(id);
				out.write("<tr>");
				if (preservar!=null && existe(e.getId(),preservar)){
					out.write("<td><input type=\"checkbox\" name=\""+id+"\" value=\""+e.getId()+"\" checked></td>");	
				}
				else{
					out.write("<td><input type=\"checkbox\" name=\""+id+"\" value=\""+e.getId()+"\"></td>");
				}
				
				out.write("<td class=\""+cssClase+"\">"+e.getValor()+"</td>");
				out.write("</tr>");
			}
		}
	}

	/**
	 * @param string
	 * @param preservar
	 * @return
	 */
	private boolean existe(String id1, String[] preservar) {
		for (int i=0; i<preservar.length; i++){
			String item=preservar[i];
			if (item.equals(id1)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
	}

	/**
	 * @param string
	 */
	public void setCssClase(String string) {
		cssClase = string;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param list
	 */
	public void setTipos(List list) {
		tipos = list;
	}

}
