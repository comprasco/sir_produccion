/*
 * Created on 02-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
public class ListaRadioHelper extends Helper {
    private List tipos;
    private String nombre;
    private String cssClase;
    private String id;
    private String funcion = "";
    
    private RadioHelper radioHelper;

    /**
     *
     */
    public ListaRadioHelper() {
        super();
        radioHelper =  new RadioHelper();


    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
     public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
         
        if (tipos == null || tipos.isEmpty()) { 
            out.write("No hay datos");
        } else {
            radioHelper.setId(getId());
            radioHelper.setNombre(getId());
        	
            Iterator it = tipos.iterator();
            while (it.hasNext()) { 
                out.write("<tr><td>");
                ElementoLista el = (ElementoLista) it.next();
                radioHelper.setValordefecto(el.getId());
			    radioHelper.render(request,out);
			    out.write("</td><td>"+el.getValor()+"</td></tr>");
            }
        }
    }

    /**
     * Se usa para convertir la lista de objetos del modelo en una lista de
     * <code>ElementoLista</code> que se va a imprimir en una lista desplegable
     * @param lista La lista de objetos del negocio
     */
    //public abstract void setListaTipos(List lista);

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
