package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.web.WebKeys;

/**
 * Clase Helper que permite construir una etiqueta <select> a partir de una lista con objetos de tipo Acciones Notariales.
 * Este se construye a partir de los valores del nombre, el id, el cssClase, y la lista de Acciones Notariales. 
 * @author ppabon
 */
public class ListaAccionesNotarialesHelper extends Helper {
	private String idSelected = null;
	private List tipos;
	private String nombre;
	private String cssClase;
	private String id;
	private String funcion = "";

	/**
	 * Constructor de la clase.
	 */
	public ListaAccionesNotarialesHelper() {
		super();
	}

	/**
	 * M�todo que puede ser utilizado para inicializar variables. Es ejecutado antes de llamar al m�todo drawGUI().
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	}

	/**
	 * M�todo que construye en la jsp una lista tipo <select> con las acciones notariales.
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		if (tipos == null || tipos.isEmpty()) {
			out.write("No hay datos");
		} else {
			String cert = (String) request.getSession().getAttribute(getId());
			if (cert != null || idSelected != null) {
				out.write("<select name=\"" + getNombre() + "\" class=\"" + getCssClase() + "\" id=\"" + getId() + "\" onFocus=\"campoactual('" + getId() + "');\" " + funcion + ">");
				out.write("<option value=\"" + WebKeys.SIN_SELECCIONAR + "\" selected>-Seleccione una opcion-</option>");
				Iterator it = tipos.iterator();

				while (it.hasNext()) {
					AccionNotarial el = (AccionNotarial) it.next();

					if (idSelected != null && idSelected.equals(el.getIdAccionNotarial()))
						out.write("<option value=\"" + el.getIdAccionNotarial() + "\" selected>" + el.getNombre() + "</option>");

					else if (el.getIdAccionNotarial().equals(cert) && idSelected == null) {
						out.write("<option value=\"" + el.getIdAccionNotarial() + "\" selected>" + el.getNombre() + "</option>");

					} else if(el.isActivo()) {
						out.write("<option value=\"" + el.getIdAccionNotarial() + "\">" + el.getNombre() + "</option>");
					}
				}

				out.write("</select>");
				out.write("<img id=\"" + getId() + "_img\" src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" class=\"imagen_campo\">");
			} else {
				out.write("<select name=\"" + getNombre() + "\" class=\"" + getCssClase() + "\" id=\"" + getId() + "\"  onFocus=\"campoactual('" + getId() + "');\" " + funcion + ">");
				out.write("<option value=\"" + WebKeys.SIN_SELECCIONAR + "\" selected>-Seleccione una opcion-</option>");

				Iterator it = tipos.iterator();

				while (it.hasNext()) {
					AccionNotarial el = (AccionNotarial) it.next();
                                      if(el.isActivo())
					out.write("<option value=\"" + el.getIdAccionNotarial() + "\">" + el.getNombre() + "</option>");
				}

				out.write("</select>");
				out.write("<img id=\"" + getId() + "_img\" src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" class=\"imagen_campo\">");
			}
		}
	}

	/**
	 * @return
	 */
	public String getCssClase() {
		return cssClase;
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
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}

	public void setSelected(String idSelected) {
		this.idSelected = idSelected;
	}

	/**
	 * @return
	 */
	public List getTipos() {
		return tipos;
	}

	/**
	 * @param list
	 */
	public void setTipos(List list) {
		tipos = list;
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

}
