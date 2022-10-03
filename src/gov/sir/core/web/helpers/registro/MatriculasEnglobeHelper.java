/*
 * Created on 28-oct-2004
*/
package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Folio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class MatriculasEnglobeHelper extends Helper {
	private List fol;
	/**
	 * 
	 */
	public MatriculasEnglobeHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			if (!fol.isEmpty()){
				out.write("<table width=\"100%\" class=\"camposform\">");
				Iterator it=fol.iterator();
				out.write("<input type=\"hidden\" name=\"MATRICULA\" value=\"\">");
				while (it.hasNext()){
					Folio folio=(Folio)it.next();
					out.write("<tr>");
					out.write("<td width=\"20\" height=\"18\"><img src=\""+request.getContextPath()+"/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td class=\"campositem\">"+folio.getIdMatricula()+"</td>");
					out.write("<td width=\"40\"><a href=\"javascript:cargarAnotaciones('"+folio.getIdMatricula()+"')\"><img src=\""+request.getContextPath()+"/jsp/images/btn_mini_verdetalles.gif\" alt=\"Permite cargar las anotaciones\" width=\"35\" height=\"13\" border=\"0\"></a></td>");
					out.write("</tr>");
				}
				out.write("</table>");
				out.write("<br>");	
			}
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
		
		fol=(List)request.getSession().getAttribute("LISTA_FOLIOS_ENGLOBE");
		if (fol==null){
			fol=new ArrayList();
		}
	}
}
