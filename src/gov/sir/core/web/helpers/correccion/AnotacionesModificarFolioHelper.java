package gov.sir.core.web.helpers.correccion;

import java.io.IOException;

import java.text.NumberFormat;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.helpers.comun.TextAreaHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author I.Siglo21
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AnotacionesModificarFolioHelper extends Helper {
	private List anotaciones;
	private TextAreaHelper textAreaHelper;

	public AnotacionesModificarFolioHelper() {
		super();
	}

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();

		if (anotaciones == null) {
			anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
		}

		if (anotaciones == null) {
			anotaciones = new Vector();
		}
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		//out.write("<input type=\"hidden\" name=\""+AWModificarFolio.POSICION+"\" id=\""+AWModificarFolio.POSICION+"\">");
		if (!anotaciones.isEmpty()) {
			out.write("<table width=\"100%\" class=\"camposform\">");

			Iterator itAnotaciones = anotaciones.iterator();

			int i = 0;

			while (itAnotaciones.hasNext()) {
				Anotacion anotacion = (Anotacion) itAnotaciones.next();
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"16\"></td>");
				out.write("<td width=\"20\" class=\"campositem\">N&ordm;</td>");
				// out.write("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
                                out.write("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
				out.write("<td class=\"campositem\">" + ((anotacion.getNumRadicacion() != null) ? anotacion.getNumRadicacion() : "&nbsp;") + " </td>");
				out.write("<td class=\"campositem\">" + ((anotacion.getEstado() != null) ? anotacion.getEstado().getNombre() : "&nbsp;") + " </td>");
				out.write("<td class=\"campositem\">" + ((anotacion.isTemporal())?"TEMPORAL":"DEFINITIVA")  + " </td>");
				out.write("<td class=\"campositem\">" + ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "&nbsp;") + " </td>");
				
				String naturaleza = null;							

				if(anotacion.getEspecificacion()!=null){
					naturaleza = anotacion.getEspecificacion();
					if(naturaleza!=null && naturaleza.length()>33){
						naturaleza = naturaleza.substring(0,33);
					}
					out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
				}else{
					if(anotacion.getNaturalezaJuridica() != null){
						naturaleza = anotacion.getNaturalezaJuridica().getNombre();
						if(naturaleza!=null && naturaleza.length()>33){
							naturaleza = naturaleza.substring(0,33);
						}
						out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");									
					}
				}					
				
				out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
				out.write("<td class=\"campositem\">" + (NumberFormat.getInstance().format(anotacion.getValor())) + " </td>");


				if(anotacion.isTemporal()){
					out.write("<td width=\"40\" align=\"center\"> <a href=\"javascript:quitar('" + /*i*/ anotacion.getIdAnotacion() + "','" + AWModificarFolio.ELIMINAR_ANOTACION + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" alt=\"Eliminar Anotación\"  height=\"13\" border=\"0\"></a></td>");
				}else{
					out.write("<td width=\"40\" align=\"center\">&nbsp;</td>");
				}

				out.write("<td width=\"40\" align=\"center\"><a href=\"javascript:cargarAnotacion('" + /*i*/ anotacion.getIdAnotacion() + "','" + AWModificarFolio.CARGAR_ANOTACION + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" height=\"13\"  alt=\"Editar Anotación\"  border=\"0\"></a></td>");
				out.write("<td width=\"40\" align=\"center\"><img style=\"cursor:hand\" onClick=\"verAnotacion('ver.anotacion.view?" + anotacion.getIdAnotacion() /*WebKeys.POSICION*/ + "=" + i + "','Anotacion','width=900,height=450,scrollbars=yes','" + i + "')\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" border=\"0\"  alt=\"Ver Anotación\"  width=\"35\" height=\"13\"></td>");
				out.write("</tr>");
				i++;
			}

			out.write("</table>");
			out.write("<hr class=\"linehorizontal\">");
		} else {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td>No hay Anotaciones para el Folio</td>");
			out.write("</tr>");
			out.write("</table>");
		}
	}

	public void setListaAnotaciones(List anotaciones){
		this.anotaciones=anotaciones;
	}
}
