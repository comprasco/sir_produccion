package gov.sir.core.web.helpers.comun;


import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.HelperException;


/**
 * @author gvillal
 * Clase que extiende a ListaTipoString
 * y reescribre el metodo drawGUI para que
 * no incluya la opcion "-Seleccione-" y solo meta
 * los datos asiganados.
 */
public class ListaTipoPlano extends ListaTipoString {

    protected boolean disabled;

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }
    public boolean isDisabled() {
        return this.disabled;
    }

    public String getDisabledText() {
        StringBuffer buffer = new StringBuffer();

        if( isDisabled() ) {
            buffer.append(' ');
            buffer.append( "disabled='disabled'" );
            buffer.append(' ');
        }

        return buffer.toString();
    }


	public void drawGUI(HttpServletRequest request, JspWriter out)
	   throws IOException, HelperException {
	   if (tipos == null || tipos.isEmpty()) {
		   out.write("No hay datos");
	   } else {
		   String cert = (String) request.getSession().getAttribute(getId());
		   if (cert != null || idSelected != null) {
			   out.write("<select name=\"" + getNombre() + "\"  " + getDisabledText()  + ((tipoMultiple==true)?" multiple size=\""+tamanoLista+"\" ":"") +"   class=\"" + getCssClase() + "\" id=\"" + getId() + "\" onFocus=\"campoactual('"+getId()+"');\" " + funcion + ">");
			   Iterator it = tipos.iterator();

			   while (it.hasNext()) {
				   ElementoLista el = (ElementoLista) it.next();

				   if (idSelected != null && idSelected.equals(el.getId())) {
					   out.write("<option value=\"" + el.getId() +
						   "\" selected>" + el.getValor() + "</option>");
				   }
				   else if (el.getId().equals(cert) && idSelected == null) {
					   out.write("<option value=\"" + el.getId() +
						   "\" selected>" + el.getValor() + "</option>");

				   } else {
					   out.write("<option value=\"" + el.getId() + "\">" +
						   el.getValor() + "</option>");
				   }
			   }

			   out.write("</select>");
			   out.write("<img id=\""+getId()+"_img\" src=\""+request.getContextPath()+"/jsp/images/spacer.gif\" class=\"imagen_campo\">");
		   } else {
			   out.write("<select name=\"" + getNombre()  + "\"  "+  getDisabledText()  +((tipoMultiple==true)?" multiple size=\""+tamanoLista+"\" ":"") +"      class=\"" + getCssClase() + "\" id=\"" + getId() + "\"  onFocus=\"campoactual('"+getId()+"');\" " + funcion + ">");

			   Iterator it = tipos.iterator();

			   while (it.hasNext()) {
				   ElementoLista el = (ElementoLista) it.next();
				   out.write("<option value=\"" + el.getId() + "\">" +
					   el.getValor() + "</option>");
			   }

			   out.write("</select>");
			   out.write("<img id=\""+getId()+"_img\" src=\""+request.getContextPath()+"/jsp/images/spacer.gif\" class=\"imagen_campo\">");
		   }
	   }
   }

}
