package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * Helper utilizado para mostrar en pantalla los detalles de una estacion
 * @author dsalas
 */
public class EstacionHelper extends Helper {
	
	private Estacion estacion;

	/**
	 * No se cargan parametros de la sesión
	 * @param request 
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	}
	
	/**
	 * Este método pinta en la pantalla la información de la nota informativa seleccionada. 
	 * @param request Trae toda la informacion asociada con la nota informativa. 
	 * @param out Se utiliza para poder escribir el codigo HTML de manera dinámica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		out.println("<table>");
		out.println("<td>");
		out.println("</table>");

	}

	/**
	 * @return
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @param estacion
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

}
