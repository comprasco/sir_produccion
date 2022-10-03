/*
 * Created on 04-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;


/**
 * @author jfrias
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TextHelper extends Helper {
	
    private String nombre;
    private String cssClase;
    private String id;
    private String valor;
    private String funcion = "";
    private String tipo="text";
    private boolean editable=true;
    private String size="";
    private String maxlength = "";
    private String readonly = "";

    /**
     *
     */
    public TextHelper() {
        super();

    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        valor = (String) request.getSession().getAttribute(getId());
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {

        if ( editable ) {
			if ( valor != null ) {
				out.println("<input id=\"" + getId() + "\"  size=\"" + size + "\" name=\"" + getNombre() + "\" value=\"" + valor +"\" " + funcion + 
							" type=\"" + tipo + "\" maxlength=\""+maxlength+"\" class=\"" + cssClase + "\" "+this.readonly+" >");
			} 
			else {
				out.println("<input id=\"" + getId() + "\" size=\"" + size + "\" name=\"" + getNombre() + "\" " + funcion +
							" type=\"" + tipo + "\" maxlength=\""+maxlength+"\" class=\"" + cssClase + "\" "+this.readonly+" >");
			}
        }
        else{
        	if ( valor!=null ) {
				out.println(valor);	
        	}
        	else{
        		out.println("No hay datos");
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
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param string
	 */
	public void setTipo(String string) {
		tipo = string;
	}

	/**
	 * @param b
	 */
	public void setEditable(boolean b) {
		editable = b;
	}

	/**
	 * @param string
	 */
	public void setSize(String string) {
		size = string;
	}

	/**
	 * @param string
	 */
	public void setMaxlength(String string) {
		maxlength = string;
	}
	
	public void setReadonly(boolean readonly) {
		if ( readonly ) {
			this.readonly = "readonly = \"true\"";
		}
		else {
			this.readonly = "";
		}
	}
}
