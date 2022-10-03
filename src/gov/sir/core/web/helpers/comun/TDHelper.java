/*
 * Created on 24-sep-2004
 *
 * @author jmendez
 */
package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 *  Helper para generar elementos TD de HTML que despliegan un texto simple
 * 
 *  * @author jmendez
 *
 */
public final class TDHelper extends Helper {
    
    private String cssClase;
    private String id;
    private String valor;

	/**
	 * 
	 */
	public TDHelper() {
		super();
		}
    
    
    /* (non-Javadoc)
         * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
         */
        public void drawGUI(HttpServletRequest request, JspWriter out)
            throws IOException, HelperException {
        
            if (valor != null) {
                out.println("<td class=\"" + cssClase + "\">");
                out.println(valor);
                out.println("</td>");
            } else {
                out.println("<td class=\"" + cssClase + "\">&nbsp;&nbsp;&nbsp;&nbsp;</td>");
            }
        
        }
     
    /* (non-Javadoc)
         * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
         */   
    public void setProperties(HttpServletRequest request)
            throws HelperException {
            valor = (String) request.getSession().getAttribute(getId());
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
	public String getValor() {
		return valor;
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
	public void setValor(String string) {
		valor = string;
	}

}
