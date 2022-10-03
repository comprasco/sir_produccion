package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;

/**
 * @author ppabon
 * Helper donde se ingresan los datos de antiguo sistema, basicamente coloca el libro y el tomo.
 */
public class AntiguoSistemaBasicoHelper extends Helper {

	TextHelper textHelper = null;

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {

		textHelper = new TextHelper();
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		out.write("<script>\n");
		out.write("function cambiarValorTipoDocumento(text) {\n");
		out.write("		try{\n");
		out.write("			document.getElementById('TIPO_ENCABEZADO').options[text].selected=true;\n");
		out.write("		}catch(e){\n");
		out.write("			document.getElementById('TIPO_ENCABEZADO').value='" + WebKeys.SIN_SELECCIONAR + "';\n");
		out.write("			document.getElementById('" + CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "').value='';\n");
		out.write("		}\n");
		out.write("}\n");
		out.write("</script>\n");

		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("		<tr>");
		out.write("			<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("			<td width=\"25%\" class=\"titulotbcentral\" align=\"left\">Libro</td>");
		out.write("			<td width=\"28%\">&nbsp;</td>");
		out.write("			<td width=\"22%\">&nbsp;</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>    ");
		out.write("			<td>Tipo</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("			<td>N&uacute;mero-Letra</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>Pagina</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("			<td>A&ntilde;o</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("		</tr>");
		out.write("	</table>");

		out.write("	<table width=\"100%\" class=\"camposform\">");
		out.write("		<tr> ");
		out.write("			<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("			<td width=\"25%\" class=\"titulotbcentral\" align=\"left\">Tomo</td>");
		out.write("			<td width=\"28%\">&nbsp;</td>");
		out.write("			<td width=\"22%\">&nbsp;</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>N&uacute;mero</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("			<td>Pagina</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>Municipio</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("			<td>A&ntilde;o</td>");
		out.write("			<td>");

		try {
			textHelper.setNombre(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
			textHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}

		out.write("			</td>");
		out.write("		</tr>");
		out.write("	</table>");

	}

}
