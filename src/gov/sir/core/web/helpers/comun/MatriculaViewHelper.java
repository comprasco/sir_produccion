package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudFolio;

/**
 * Clase Helper que permite construir el listado de matriculas en una jsp a partir de una lista de matriculas. 
 * Tiene como parámetros el número total de matriculas en la lista(num) y la lista de matriculas (folios).
 * @author ppabon
 */
public class MatriculaViewHelper extends Helper {
	int num;
	List folios;
	boolean resaltado =false;

	/**
	 * Constructor de la clase.
	 */
	public MatriculaViewHelper() {
		super();
	}

	/**
	 * Método que puede ser utilizado para inicializar variables. Es ejecutado antes de llamar al método drawGUI().
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	}

	/** 
	 * Método que construye en la jsp el listado de matriculas que recibe de la lista de Folios.
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		if (num > 0) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td></td><span=\"titulos_resaltado\">Matr&iacute;culas</span>");

			Iterator iterator = folios.iterator();
			while (iterator.hasNext()) {
				SolicitudFolio solfolio = (SolicitudFolio) iterator.next();
				Folio folio = solfolio.getFolio();
				String matr = folio.getIdMatricula();

				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				if(resaltado==false){
					out.write("<td class=\"campositem\" colspan=\"2\">" + matr + "</td>");
				}else{
					out.write("<td class=\"campositem\">"+ matr + "</span>");
				}
				out.write("</tr>");
			}

			out.write("</table>");
			out.write("<br>");
		}

	}
	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}

	/**
	 * Método que asigna el listado de folios al atributo folios de la clase.
	 * @param list
	 */
	public void setFolios(List list) {
		folios = list;
		if (folios != null) {
			num = folios.size();
		} else {
			num = 0;
		}
	}
	
	/**
	 * @param nr
	 */
	public void setResaltado(boolean nr){
		resaltado = nr;
	}
	
	/**
	 * @return
	 */
	public boolean getResaltado(){
		return resaltado;
	}
	

}
