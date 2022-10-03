package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * Clase Helper que permite construir un radio buton en una JSP. 
 * Este se construye a partir de los valores del nombre, el id, el valor, el valor por defecto, y la función
 * en caso de que se desee colocar una función javascript al radio buton.
 * @author ppabon
 */
public class RadioHelper extends Helper {
	private String nombre;
	private String id;
	private String valor;
	private String valordefecto;
	private String funcion = "";

	/**
	 * Constructor
	 */
	public RadioHelper() {
		super();
	}

	/** 
	 * Si la variable con el nombre asignado se encuentra en la sesión se coloca en el value del input.
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		valor = (String) request.getSession().getAttribute(getId());
	}

	/** 
	 * Método que construye el radio buton en la jsp.
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		if ((valor == null && valordefecto != null) || (valor != null && valordefecto != null && !valor.equals(valordefecto))) {
			out.println("<input name=\"" + getNombre() + "\" value=\"" + valordefecto + "\"  " + funcion + " type=\"radio\" >");
		} else if (valor != null && valordefecto != null && valor.equals(valordefecto)) {
			out.println("<input name=\"" + getNombre() + "\" value=\"" + valordefecto + "\"  " + funcion + " checked type=\"radio\" >");
		} else {
			out.println("<input name=\"" + getNombre() + "\" type=\"radio\" >");
		}
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}
	/**
	 * @return
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * @param string
	 */
	public void setFuncion(String string) {
		funcion = string;
	}
	/**
	 * @return
	 */
	public String getValordefecto() {
		return valordefecto;
	}

	/**
	 * @param string
	 */
	public void setValordefecto(String string) {
		valordefecto = string;
	}

}
