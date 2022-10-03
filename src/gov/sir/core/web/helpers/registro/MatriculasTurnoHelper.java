/*
 * Created on 28-sep-2004
*/
package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Folio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class MatriculasTurnoHelper extends Helper {
	
	private List asociadas;
	/**
	 * 
	 */
	public MatriculasTurnoHelper() {
		super();
	}

	/**
	 *
	 */

	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
		if (!asociadas.isEmpty()){
			out.write("Matriculas Asociadas:\n");
			for (int i=0; i<asociadas.size(); i++){
				Folio folio=(Folio)asociadas.get(i);
				out.write(folio.getIdMatricula()+"\n");
			}
		}
		
	}

	/**
	 *
	 */

	public void setProperties(HttpServletRequest request) throws HelperException {
		asociadas = (List)request.getSession().getAttribute("LISTA_MATRICULAS_ASOCIADAS");
		if (asociadas == null){
			asociadas = new ArrayList();
		}
	}

}
