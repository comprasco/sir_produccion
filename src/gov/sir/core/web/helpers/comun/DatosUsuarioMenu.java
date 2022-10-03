package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.web.WebKeys;

/**
 * @author I.Siglo21
 * Esta clase fue diseñada para poder pintar de manera clara un menu 
 * de navagacion para que el usuario pueda cambiar entre procesos y fases sin 
 * cambiar de pantalla
 */
public class DatosUsuarioMenu extends Helper{
	
	/**
	 * Esta constante guarda la estacion que escogio el usuario para trabajar 
	 */
	private Estacion estacion;
	
	/**
	 * Esta constante guarda el rol que escogio el usuario para trabajar 
	 */	
	private Rol rol;
	
	/**Esta constante guarda el usuario que inicio sesion*/
	private Usuario usuario;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		
		estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		rol = (Rol) session.getAttribute(WebKeys.ROL);
		usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);

		
		if(usuario==null){
			usuario =  new Usuario();	
		}
		
		
		if(estacion == null){
			estacion = new Estacion();	
		}
		
		if(rol == null){
			rol = new Rol();
		}
	}


	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td width=\"12\"><img name=\"barratitle_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c1.gif\" width=\"12\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"16\" background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_iniciosesion.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\" class=\"titresaltados\">" + usuario.getUsername() + "</td>");
		out.write("<td width=\"11\"><img name=\"barratitle_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c4.gif\" width=\"11\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"12\"><img name=\"barratitle_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c1.gif\" width=\"12\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"16\" background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_rol.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"><span class=\"titresaltados\">" + rol.getNombre() + "</span></td>");
		out.write("<td width=\"11\"><img name=\"barratitle_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c4.gif\" width=\"11\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"12\"><img name=\"barratitle_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c1.gif\" width=\"12\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"16\" background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_estacion.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"><span class=\"titresaltados\">" + estacion.getNombre() + "</span></td>");
		out.write("<td width=\"11\"><img name=\"barratitle_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/barratitle_r1_c4.gif\" width=\"11\" height=\"26\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"11\">&nbsp;</td>");
		out.write("</tr>");
		out.write("</table>");
	}
	
}
