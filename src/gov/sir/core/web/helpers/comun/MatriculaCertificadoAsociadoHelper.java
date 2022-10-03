package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;


/**
 * @author jfrias,mmunoz
 */
public class MatriculaCertificadoAsociadoHelper extends Helper {
    int num;
    
    private String accion = "AGREGAR";
    private boolean isLink=false;
    
    private boolean mostrarAgregar = true;

    /**
     *
     */
    public MatriculaCertificadoAsociadoHelper() {
        super();
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        HttpSession session = request.getSession();	
        Integer nume = (Integer) session.getAttribute(AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_NUM_MATRICULAS);

        if (nume == null) {
            session.setAttribute(AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_NUM_MATRICULAS, new Integer(0));
            num = 0;
        } else {
            num = nume.intValue();
        }
        
        if(((Proceso)session.getAttribute(WebKeys.PROCESO)).getIdProceso() == Long.valueOf(CProceso.PROCESO_CERTIFICADOS).longValue() 
        	&& num  >= 1){
			mostrarAgregar = false;			        	
        }
        
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        
		HttpSession session = request.getSession();		
        	
		out.write("<table width=\"100%\">");
		out.write("<tr>");
		out.write("<td>");
		out.write("<input type=\"hidden\" name=\"ITEM\" value=\"NINGUNO\">");
					
        if (num > 0) {
            out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td></td><td>N&uacute;mero de Matr&iacute;cula</td><td>Eliminar</td>");
            for (int i = 0; i < num; i++) {
                Integer camb = new Integer(i);
                String matr = (String) session.getAttribute(AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_ID_MATRICULA + camb.toString());
                out.write("<tr>");
                out.write("<td width=\"20\"><img src=\"" +
                    request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                out.write("<td class=\"campositem\">" + matr + "</td>");
                out.write("<td width=\"36\"><a href=\"javascript:quitar('"+AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_ID_MATRICULA+camb.toString() + "')\"><img name=\"btn_short_eliminar\" src=\"" +
                    request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\"></a></td>");

                out.write("</tr>");
            }
            out.write("</table>");
			out.write("<br>");
        }

		if(mostrarAgregar){
			if (isLink){
				String idCirculo = "";
				if ( session.getAttribute(WebKeys.CIRCULO) != null ) {
					idCirculo = ((Circulo) session.getAttribute(WebKeys.CIRCULO)).getIdCirculo();
					idCirculo = idCirculo + "-";
				}

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr></tr>");
				Integer otro = new Integer(num);
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>N&uacute;mero de Matr&iacute;cula</td>");
				out.write("<td><input name=\""+AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_ID_MATRICULA+otro.toString() + "\" type=\"text\" value=\""+idCirculo+"\" class=\"camposformtext\"></td>");
				out.write("</tr>");
				out.write("</table>");

				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"right\">");
				out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
				out.write("<tr>"); 
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>Agregar Matr&iacute;cula</td>");
				out.write("<td><a href=\"javascript:cambiarAccion('" + accion + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\"></a>"); 
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");			
			}else{
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr></tr>");
				Integer otro = new Integer(num);
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>N&uacute;mero de Matr&iacute;cula</td>");
				out.write("<td><input name=\""+AWLiquidacionRegistro.CERTIFICADO_ASOCIADO_ID_MATRICULA+otro.toString() + "\" type=\"text\" class=\"camposformtext\"></td>");
				out.write("</tr>");
				out.write("</table>");

				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"right\">");
				out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
				out.write("<tr>"); 
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>Agregar Matr&iacute;cula</td>");
				out.write("<td> <input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"cambiarAccion('" + accion + "')\">"); 
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");
	
			}
					}
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		

    }
	/**
	 * @param string
	 */
	public void setAccion(String string) {
		accion = string;
	}

	/**
	 * @param b
	 */
	public void setLink(boolean b) {
		isLink = b;
	}

}
