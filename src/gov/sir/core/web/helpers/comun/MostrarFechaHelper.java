package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * Este helper puede mostar el string de una fecha dado el date.
 * La manera que la puede mostrar son dos, puede mostrarlo en 
 * un campo inmodificable o en un cuadro de texto. Para saber
 * en cual de las dos maneras se va a mostrar es necesario 
 * darle valor a output.
 * Si es de tipo cuadro de texto es necesario dar valor a todos
 * los atributos que comienzan por setTextxxx 
 * @author mmunoz
 */
public class MostrarFechaHelper extends Helper {

	public static final String CUADRO_DE_TEXTO = "CUADRO_DE_TEXTO";

	public static final String CAMPO_INMODIFICABLE = "CAMPO_INMODIFICABLE";

	public static final String NO_SELECCIONADO = "NO_SELECCIONADO";

	public String output = MostrarFechaHelper.NO_SELECCIONADO;

	public Date date;

	private String fecha = "";

	private String size = "";

	private String textCss;
	private String textFuncion;
	private String textID;
	private String textNombre;
	private String textTipo;
	private boolean fechaEncabezado;

	/**
	 * 
	 *
	 */
	public MostrarFechaHelper() {
		fechaEncabezado = false;
	}

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		Calendar calendar = Calendar.getInstance();

		if (date != null) {
			calendar.setTime(date);
			int mes = calendar.get(Calendar.MONTH) + 1;
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			if (dia < 10) {
				fecha = "0" + dia;
			} else {
				fecha = String.valueOf(dia);
			}
			
			if (fechaEncabezado) {
				String url = request.getRequestURL().toString();
				if (!url.endsWith(".view")) {
					url = "";
				} else {
					int lastIndex = url.lastIndexOf('/');
					url = "<br>Pantalla: " + url.substring(lastIndex + 1);
				}

				fecha += "/" + mes + "/" + calendar.get(Calendar.YEAR) + url;

			} else {
				fecha += "/" + mes + "/" + calendar.get(Calendar.YEAR);
			}
		} else {
			fecha = "&nbsp;";
		}

	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		if (output.equals(MostrarFechaHelper.CUADRO_DE_TEXTO)) {
			TextHelper helper = new TextHelper();
			helper.setCssClase(textCss);
			helper.setFuncion(textFuncion);
			helper.setId(textID);
			helper.setNombre(textNombre);
			helper.setTipo(textTipo);
			helper.setSize(size);
			request.getSession().setAttribute(textNombre, fecha);
			helper.render(request, out);
		} else if (output.equals(MostrarFechaHelper.CAMPO_INMODIFICABLE)) {
			out.write(fecha);
		} else if (output.equals(MostrarFechaHelper.NO_SELECCIONADO)) {
			out.write("NO ESPECIFICO EL TIPO DE SALIDA");
		}
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param string
	 */
	public void setOutput(String string) {
		output = string;
	}

	/**
	 * @param string
	 */
	public void setTextCss(String string) {
		textCss = string;
	}

	/**
	 * @param string
	 */
	public void setTextFuncion(String string) {
		textFuncion = string;
	}

	/**
	 * @param string
	 */
	public void setTextID(String string) {
		textID = string;
	}

	/**
	 * @param string
	 */
	public void setTextNombre(String string) {
		textNombre = string;
	}

	/**
	 * @param string
	 */
	public void setTextTipo(String string) {
		textTipo = string;
	}

	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return
	 */
	public boolean isFechaEncabezado() {
		return fechaEncabezado;
	}

	/**
	 * @param b
	 */
	public void setFechaEncabezado(boolean b) {
		fechaEncabezado = b;
	}

}