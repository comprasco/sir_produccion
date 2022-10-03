package gov.sir.core.web.helpers.correccion;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.ListaTipoString;
import gov.sir.core.web.helpers.comun.TextAreaHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * Esta clase es la clase encargada de mostrar en la jsp, el listado de ciudadanos que intervienen
 * en una anotación con campos editables. Además incorpora el botón de editar para guardar los cambios
 * realizados al ciudadano y el botón para eliminarlo de la anotación.
 * @author ppabon
 */
public class CiudadanosModificarAnotacionHelper extends Helper {
	private List ciudadanos;
	private ListaElementoHelper tipoIDHelper;
	private ListaTipoString tipoInterHelper;
	private TextHelper textHelper;
	private TextAreaHelper textAreaHelper;
	private String check;

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase.
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
		check = (String) session.getAttribute(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION);

		if (check != null) {
			check = "checked";
		} else {
			check = "";
		}

		if ((ciudadanos == null) || (ciudadanos.size() == 0)) {
			ciudadanos = new Vector();
		}

		textHelper = new TextHelper();
		textAreaHelper = new TextAreaHelper();

		tipoIDHelper = new ListaElementoHelper();

		List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
		
 		List tiposIDNew = new ArrayList();
		
		for (Iterator iter = tiposID.iterator(); iter.hasNext();) {
			ElementoLista element = (ElementoLista) iter.next();
			tiposIDNew .add(element);
		}


		if (tiposID != null) {
			tipoIDHelper.setOrdenar(false);
			tipoIDHelper.setCssClase("camposformtext");
			tipoIDHelper.setId(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
			tipoIDHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
			tipoIDHelper.setTipos(tiposIDNew);
		}

		tipoInterHelper = new ListaTipoString();

		List tiposParticipacion = null;

		try {
			tiposParticipacion = null; //(List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);	
		} catch (Exception e) {
			//logger.debug("ERROR EN CiudadanosModificarAnotacionHelper:" + e.getMessage());
		}

		if (tiposParticipacion == null) {
			tiposParticipacion = new Vector();
			tiposParticipacion.add("DE");
			tiposParticipacion.add("A");
		}

		tipoInterHelper.setCssClase("camposformtext");
		tipoInterHelper.setId(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.setListaTipos(tiposParticipacion);
	}

	/**
	 * Este método pinta en el jsp el listado de ciudadanos con los campos editables
	 * y con los botones de editar y borrar.
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td class=\"campostitle\"><p>Personas que intervienen en el acto (La X indica a la persona que figura como titular de derechos reales de dominio, I-Titular de dominio incompleto)</p></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td colspan=\"4\">");
		out.write("<input type=\"hidden\" name=\"" + AWModificarFolio.POSICION + "\" id=\"" + AWModificarFolio.POSICION + "\">");

		//Tablas donde se muestran los ciudadanos insertados
		if (!ciudadanos.isEmpty()) {
			int i = 0;
			Iterator itCiudadanos = ciudadanos.iterator();
			out.write("<table width=\"100%\" class=\"camposform\">");

			while (itCiudadanos.hasNext()) {
				AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
				out.write("<tr>");
				out.write("<td height=\"28\">" + ((anotacionCiudadano.getRolPersona() != null) ? anotacionCiudadano.getRolPersona() : "") + "</td>");
				out.write("<td>");
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td>Tipo</td>");
				out.write("<td>");
				tipoIDHelper.setId(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA + i);
				tipoIDHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA + i);
				request.getSession().setAttribute(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA + i, anotacionCiudadano.getCiudadano().getTipoDoc());
				tipoIDHelper.render(request, out);
				out.write("</td>");
				out.write("<td>N&uacute;mero</td>");
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_NUM_ID_PERSONA + i + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWModificarFolio.ANOTACION_NUM_ID_PERSONA + "\" value=\"" + ((anotacionCiudadano.getCiudadano().getDocumento() != null) ? anotacionCiudadano.getCiudadano().getDocumento() : "") + "\"></td>");
				out.write("</tr>");
				out.write("</table>");

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td>Primer Apellido / Raz&oacute;n Social </td>");
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA + i + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA + "\" value=\"" + ((anotacionCiudadano.getCiudadano().getApellido1() != null) ? anotacionCiudadano.getCiudadano().getApellido1() : "") + "\"></td>");
				out.write("<td>Segundo Apellido </td>");
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA + i + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA + "\" value=\"" + ((anotacionCiudadano.getCiudadano().getApellido2() != null) ? anotacionCiudadano.getCiudadano().getApellido2() : "") + "\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td>Nombres</td>");
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_NOMBRES_PERSONA + i + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWModificarFolio.ANOTACION_NOMBRES_PERSONA + "\" value=\"" + ((anotacionCiudadano.getCiudadano().getNombre() != null) ? anotacionCiudadano.getCiudadano().getNombre() : "") + "\"></td>");
				out.write("<td>&nbsp;</td>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");
				out.write("</table>");

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td>Tipo Intervenci&oacute;n </td>");
				out.write("<td>");
				tipoInterHelper.setId(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
				tipoInterHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
				request.getSession().setAttribute(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION + i, anotacionCiudadano.getRolPersona());
				tipoInterHelper.render(request, out);
				out.write("</td>");
				out.write("<td>Propietario?</td>");
				//TODO CIUDADANO_TITULAR Cambiar el checkBox por un combo con las 3 opciones
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION + i + "\" type=\"checkbox\" value=\"\" " + (anotacionCiudadano.getMarcaPropietario()!=CAnotacionCiudadano.NO_PROPIETARIO ? "checked" : "") + "></td>");
				out.write("<td>Porcentaje</td>");
				out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION + i + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION + "\"  value=\"" + (anotacionCiudadano.getParticipacion()) + "\">%</td>");
				out.write("</tr>");
				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");

				out.write("<tr>");
				out.write("<td>&nbsp;</td>");
				out.write("<td align=\"right\">");
				out.write("<table  border=\"0\" class=\"camposform\">");

				out.write("<tr align=\"center\">");
				out.write("<td>Editar</td>");
				out.write("<td>Eliminar</td>");
				out.write("</tr>");
				out.write("<tr align=\"center\">");
				out.write("<td><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"editar('" + i + "','" + AWModificarFolio.EDITAR_CIUDADANO_ANOTACION + "')\"></td>");
				out.write("<td><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"quitar('" + i + "','" + AWModificarFolio.ELIMINAR_CIUDADANO_ANOTACION + "')\"></td>");
				out.write("</tr>");

				out.write("</table>");
				out.write("</td>");

				out.write("</tr>");

				if (i != (ciudadanos.size() - 1)) {
					out.write("<tr>");
					out.write("<td>&nbsp;</td>");
					out.write("<td><hr class=\"linehorizontal\"></td>");
					out.write("</tr>");
				}

				i++;
			}

			out.write("</table>");

			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
		} else {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td class=\"titulotbcentral\">" + "No hay ciudadanos relacionados con la anotaci&oacute;n" + "</td>");
			out.write("</tr>");
			out.write("</table>");

			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
		}

		out.write("<br>");
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td class=\"campostitle\" align=\"left\"><p align=\"left\">Adicionar Personas que intervienen en el Acto&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td colspan=\"4\">");

		out.write("<br>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Identificaci&oacute;n</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td>Tipo</td>");
		out.write("<td>");
		tipoIDHelper.setId(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
		tipoIDHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
		tipoIDHelper.render(request, out);
		out.write("</td>");
		out.write("<td>N&uacute;mero</td>");
		out.write("<td>");
		textHelper.setTipo("text");
		textHelper.setNombre(AWModificarFolio.ANOTACION_NUM_ID_PERSONA);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(AWModificarFolio.ANOTACION_NUM_ID_PERSONA);
		textHelper.render(request, out);
		out.write("</td>");
		out.write("</tr>");
		out.write("</table></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>B&aacute;sicos</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td><table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td>Primer Apellido </td>");
		out.write("<td>");
		textHelper.setTipo("text");
		textHelper.setNombre(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA);
		textHelper.render(request, out);
		out.write("</td>");
		out.write("<td>Segundo Apellido </td>");
		out.write("<td>");
		textHelper.setTipo("text");
		textHelper.setNombre(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA);
		textHelper.render(request, out);
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>Nombres</td>");
		out.write("<td>");
		textHelper.setTipo("text");
		textHelper.setNombre(AWModificarFolio.ANOTACION_NOMBRES_PERSONA);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(AWModificarFolio.ANOTACION_NOMBRES_PERSONA);
		textHelper.render(request, out);
		out.write("</td>");
		out.write("<td>&nbsp;</td>");
		out.write("<td>&nbsp;</td>");
		out.write("</tr>");
		out.write("</table></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Asociaci&oacute;n</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td><table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td>Tipo Intervenci&oacute;n </td>");
		out.write("<td>");
		tipoInterHelper.setId(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.render(request, out);
		out.write("</td>");
		out.write("<td>Propietario?</td>");
		out.write("<td><input name=\"" + AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION + "\" type=\"checkbox\" " + check + " value=\"" + AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION + "\"></td>");
		out.write("<td>Porcentaje</td>");
		out.write("<td>");
		textHelper.setTipo("text");
		textHelper.setNombre(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION);
		textHelper.render(request, out);
		out.write("</td>");
		out.write("</tr>");
		out.write("</table> </td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td align=\"right\">");
		out.write("<table  border=\"0\" class=\"camposform\">");
		out.write("<tr align=\"center\">");
		out.write("<td>Agregar</td>");
		out.write("</tr>");
		out.write("<tr align=\"center\">");
		out.write("<td><input name=\"imageField\" type=\"image\"  src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"cambiarAccion('" + AWModificarFolio.AGREGAR_CIUDADANO_ANOTACION + "')\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");

		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
	}
}
