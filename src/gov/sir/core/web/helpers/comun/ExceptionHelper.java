package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;

/**
 *@author I.Siglo21
 * Esta clase es la encagada de mostrar la traza de algun error que haya sucedido.
 */
public class ExceptionHelper extends Helper {

    public static final String EXCEPCION = "EXCEPCION_PARA_HELPER";

    /**
     * Constante donde se almacena la informacion acerca de la traza
     * del error
     */
    private StackTraceElement stack[];

    /**
     * Clase donde ocurrio el error
     */
    private String clase;

    /**Exception que es lanzada por el negocio**/
    private Exception exception;

    /** Constructor por defecto de la clase ErrorHelper */
    public ExceptionHelper() {}

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request) throws HelperException {

        HttpSession session = request.getSession();
        exception = (Exception)session.getAttribute(EXCEPCION);
        stack = exception.getStackTrace();
        clase = (String)session.getAttribute(SMARTKeys.CLASE_EXCEPCION);
        session.removeAttribute(SMARTKeys.EXCEPCION);
        session.removeAttribute(EXCEPCION);
    }

    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
        out.println("<tr>");
        out.println("<td valign=\"top\" class =\"tituloTbCentral\">" + clase +":"+exception.getMessage());
        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"tablas\">");
        out.println("<tr>");
        out.println("<td>");
        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"tablas\">");

        if (stack != null) {
            printStackTrace(exception, out);
        }
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table></td>");
        out.println("</tr>");
        out.println("</table>");

    }

    public void printStackTraceAsCause(Throwable t, StackTraceElement[] causedTrace, JspWriter out) throws IOException {
        // Compute number of frames in common between this and caused
        StackTraceElement[] trace = t.getStackTrace();
        int m = trace.length - 1, n = causedTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
            m--;
            n--;
        }
        int framesInCommon = trace.length - 1 - m;
        out.println("<tr><td colspan=4  class =\"tituloTbCentral\">Causado por: " + t.toString() + "</td></tr>");
        for (int i = 0; i <= m; i++) {
            printStackTraceElement(i + 1, trace[i], out);
        }

        if (framesInCommon != 0)
            out.println("<tr><td colspan=4>hay " + framesInCommon + " mas...</td></tr>");

        Throwable ourCause = t.getCause();
        if (ourCause != null)
            printStackTraceAsCause(ourCause, trace, out);
    }

    public void printStackTrace(Throwable t, JspWriter out) throws IOException {
        out.println("<tr>");
        out.println("<td>");
        out.println(" ");
        out.println("</td>");
        out.println("<td class =\"tituloTbCentral\">");
        out.println("Clase:");
        out.println("</td>");
        out.println("<td class =\"tituloTbCentral\">");
        out.println("Metodo:");
        out.println("</td>");
        out.println("<td class =\"tituloTbCentral\">");
        out.println("Linea:");
        out.println("</td>");
        out.println("</tr>");
        StackTraceElement[] trace = t.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            printStackTraceElement(i + 1, trace[i], out);
        }

        Throwable ourCause = t.getCause();
        if (ourCause != null)
            printStackTraceAsCause(ourCause, trace, out);

    }

    public void printStackTraceElement(int index, StackTraceElement e, JspWriter out) throws IOException {
        out.println("<tr>");
        out.println("<td>" + index + "</td>");
        out.println("<td>" + e.getClassName() + "</td>");
        out.println("<td>" + e.getMethodName() + "</td>");
        out.println("<td>" + e.getLineNumber() + "</td>");
        out.println("</tr>");
    }

}
