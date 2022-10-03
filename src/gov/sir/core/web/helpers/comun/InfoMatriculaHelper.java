package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.SolicitudFolio;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class InfoMatriculaHelper extends Helper {
	List solicitudFolios;
	/**
	 * 
	 */
	public InfoMatriculaHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			if (!solicitudFolios.isEmpty()){
			   out.write("<table width=\"100%\" class=\"camposform\">");
			   out.write("<tr>");
			   out.write("<td>N&uacute;mero de Matr&iacute;cula</td>");
			   out.write("</tr>");
			   for (int i = 0; i < solicitudFolios.size(); i++) {
				   String matr=((SolicitudFolio)solicitudFolios.get(i)).getFolio().getIdMatricula();
					out.write("<tr>");
					out.write("<td class=\"campositem\">");
					out.write(matr);
					out.write("</td>");
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
	}
	/**
	 * @param list
	 */
	public void setSolicitudFolios(List list) {
		solicitudFolios = list;
	}

}
