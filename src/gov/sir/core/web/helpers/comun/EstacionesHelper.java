package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWSeguridad;
import gov.sir.core.negocio.modelo.constantes.CMensajesAyuda;

/**
 * @author I.Siglo21
 * Esta clase fue diseñada para poder pintar de manera clara las estaciones a las cuales pertenece un usuario
 */
public class EstacionesHelper extends Helper {
    /**
     * En esta estructura se guadaran todas las estaciones que tenga asociadas el usuario
     */
    private List estaciones = new Vector();

	/** Esta es la estacion que se tiene en memoria que va a ser mostrada en caso
	 * que se desee cambiar de estacion
	 */
    private Estacion estacionSession;
    
     /**
     * Método constructor por defecto de la clase EstacionesHelper
     */
    public EstacionesHelper() {
        estaciones.clear();
    }

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        estaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ESTACIONES);
        
        if(estaciones == null){
        	estaciones = new Vector();
        }
		estacionSession = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        if(estacionSession == null){
			estacionSession = new Estacion();
        }
    }

    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        	
			out.write("<form name=\"Roles\" method=\"post\" action=\"roles.view\">");
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<input type=\"hidden\" name=\"ACCION\" value=\"CONSULTAR_ESTACION\">");
			out.write("<tr>");
			out.write("<td>");
			
			out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<tr>");
			out.write("<td width=\"10\" class=\"bgnmenuinactivo\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_inactivo_ini_02.gif\" width=\"10\" height=\"18\"></td>");
			out.write("<td class=\"bgnmenuinactivo\"><input name=\"Submit2\" type=\"submit\" class=\"botoninactivo\" title=\""+CMensajesAyuda.MSG_CAMBIAR_ROL+"\" value=\"Cambiar Rol\"></td>");
			out.write("<td width=\"16\" class=\"bgnmenuinactivo\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_cambiosesion.gif\" width=\"16\" height=\"18\"></td>");
			out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menuinactivo.gif\" width=\"11\" height=\"18\"></td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"8\"></td>");
			out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"8\"></td>");
			out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
			out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
			out.write("</tr>");
			out.write("</table>");
			
			out.write("</td>");
			out.write("</form>");
			out.write("<form name=\"Estaciones\" method=\"post\" action=\"seguridad.do\" type=\"submit\">");
			out.write("<input type=\"hidden\" name=" + WebKeys.ACCION + " value=\"" + AWSeguridad.CONSULTAR_ESTACION + "\">");
			out.write("</table></td>");
			out.write("</tr>");
			out.write("<tr>"); 
			out.write("<td>");
        if (estaciones.isEmpty()) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("No tiene estaciones disponibles");
			out.write("</td>");
			out.write("</tr>");
			
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
			out.write("</tr>");
			out.write("</form>");
			out.write("</table>");
        } else {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td height=\"17\" class=\"titulotbcentral\">Seleccione una Estacion</td>");
			out.write("</tr>");

			int i = 0;
            Iterator itEstaciones = estaciones.iterator();
            while (itEstaciones.hasNext()) {
                Estacion estacion = (Estacion) itEstaciones.next();
                String checked = new String(); 
                if(estacion.getEstacionId().equals(estacionSession.getEstacionId()) || i==0){
                	checked = "checked";
                }
                out.write("<tr>");
                out.write("<td><label>");
                out.write("<input type=\"radio\"  " + checked + " name=\"ID_ESTACION\" value=\"" + estacion.getEstacionId() + "\">");
                out.write("</label></td>");
                out.write("<td>");
                out.write(estacion.getNombre());
                out.write("</td>");
                out.write("</tr>");
                i++;
            }
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
			out.write("</tr>");
			out.write("<tr>"); 
			out.write("<td align=\"center\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_aceptar.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");
			out.write("</tr>");
			out.write("</form>");
			out.write("</table>");
        }
		
    }
}
