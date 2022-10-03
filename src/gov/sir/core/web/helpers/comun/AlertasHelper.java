package gov.sir.core.web.helpers.comun;

import gov.sir.core.web.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author mmunoz
 */
public class AlertasHelper extends Helper {
	
	private List alertas;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		alertas = (List)request.getSession().getAttribute(WebKeys.ALERTAS);
		if(alertas == null){
			alertas = new ArrayList();
		}
	}
	
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		
		if(alertas.size()>0){
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.println("<tr>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=\"12\">&nbsp;</td>");
			out.println("<td width=\"12\"><img name=\"tabla_error_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_error_r1_c1.gif\" width=\"12\" height=\"30\" border=\"0\"></td>");
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_error_bgn002.gif\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.println("<tr>"); 
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_error_bgn001.gif\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			out.println("<tr>"); 
			out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ico_iniciosesion.gif\" width=\"16\" height=\"21\"></td>");
			out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ico_alerta.gif\" width=\"20\" height=\"20\"></td>");
			out.println("</tr>");
			out.println("</table></td>");
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_error_bgn001.gif\" class=\"titulotbcentral\">Alertas del Sistema</td>");
			out.println("<td width=\"14\"><img name=\"tabla_error_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/tabla_error_r1_c4.gif\" width=\"14\" height=\"30\" border=\"0\" alt=\"\"></td>");
			out.println("</tr>");
			out.println("</table></td>");
			out.println("<td width=\"12\"><img name=\"tabla_error_r1_c6\" src=\"" + request.getContextPath() + "/jsp/images/tabla_error_r1_c6.gif\" width=\"12\" height=\"30\" border=\"0\" alt=\"\"></td>");
			out.println("<td width=\"12\">&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>"); 
			out.println("<td>&nbsp;</td>");
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn003.gif\">&nbsp;</td>");
			out.println("<td class=\"tdtablaanexa02\">"); 
    
    
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\"><tr>");
			out.println("<td valign=\"top\">");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"tablas\">");
			
			Iterator itAlertas = alertas.iterator();
			while(itAlertas.hasNext()){
				String alerta = (String)itAlertas.next();
				out.println("<tr>");
				out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_animation.gif\" width=\"16\" height=\"21\"></td>");
				out.println("<td class=\"titresaltados\">");
				out.println(alerta);
				out.println("</td>");
				out.println("</tr>");
			}
			
			out.println("</table></td>");
			out.println("<td>");
			out.println("<img name=\"tabla_error_r1_c6\" src=\"" + request.getContextPath() + "/jsp/images/mensaje_animated.gif\" width=\"70\" height=\"70\" border=\"0\" alt=\"\">");
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
			
			out.println("</td>");
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\">&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>"); 
			out.println("<td>&nbsp;</td>");
			out.println("<td><img name=\"tabla_gral_r3_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_gral_r3_c1.gif\" width=\"12\" height=\"20\" border=\"0\" alt=\"\"></td>");
			out.println("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn005.gif\">&nbsp;</td>");
			out.println("<td><img name=\"tabla_gral_r3_c5\" src=\"" + request.getContextPath() + "/jsp/images/tabla_gral_r3_c5.gif\" width=\"12\" height=\"20\" border=\"0\" alt=\"\"></td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("</table>");
		}
	}
}
