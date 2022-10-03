package gov.sir.core.web.filters;

import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWSeguridad;
import gov.sir.core.web.acciones.excepciones.UsuarioInvalidoException;
import gov.sir.hermod.HermodProperties;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;

/**
* @author David Panesso
* @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA SITEMINDER
* Filtro para manejo de logout con CA SiteMinder
*/
public class SeguridadFilter implements Filter {

    private static final boolean debug = false;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public SeguridadFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SeguridadFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SeguridadFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("SeguridadFilter:doFilter()");
        }
        
        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession(false);
            String accion = request.getParameter(WebKeys.ACCION);
            if (accion != null && accion.equals(AWSeguridad.LOGOUT)) {
                Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		if (usuario == null) {
			throw new UsuarioInvalidoException("La sesión ha finalizado. Por favor regístrese de nuevo en el sistema.");
		}
		session.removeAttribute(WebKeys.USUARIO);
                request.setAttribute(SMARTKeys.INVALIDAR_SESSION, SMARTKeys.INVALIDAR_SESSION_SI);
                Enumeration enumeration = session.getAttributeNames();

                Map sesiones = (Map) session.getServletContext()
                        .getAttribute(WebKeys.LISTA_SESIONES);
                sesiones.remove(session.getId());
                String nombre = new String();
                while(enumeration.hasMoreElements()){
                        nombre = (String) enumeration.nextElement();
                        if(!nombre.startsWith("org.auriga")) {
                                session.removeAttribute(nombre);	
                        }					
                }
                HermodProperties hp = HermodProperties.getInstancia();
		String url = hp.getProperty(HermodProperties.URL_CA_LOGOUT);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(url);
                chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("SeguridadFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SeguridadFilter()");
        }
        StringBuffer sb = new StringBuffer("SeguridadFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
