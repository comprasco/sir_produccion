package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;

import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWNotas;

/**
 * @author mmunoz
 */
public class NotasInformativasHelper extends Helper{
	
	
	/*public NotasInformativasHelper(String nombreFormulario){
		this.nombreFormulario = nombreFormulario ;
	}*/
	
	private String ocultar;
	private HttpSession session;
	private boolean mostrardevolutivas = false;
	private String nombreFormulario;
	private boolean esRevisarAprobar = false;
	

	



	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	
		session = request.getSession();
	
		ocultar = request.getParameter(WebKeys.OCULTAR);	
		if(ocultar == null){
			ocultar = "TRUE";
		}
		session.setAttribute(CTipoNota.DEVOLUTIVA,String.valueOf(mostrardevolutivas));
	}



	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
	
		out.write("\r\n");
		out.write("   <SCRIPT>\r\n");
		out.write("       function cargarDatos() {\r\n");
		if(nombreFormulario!=null && !nombreFormulario.equals("")){
			if(!esRevisarAprobar){
				out.write("       	try{\r\n");		
				out.write("       		document."+nombreFormulario+"." + WebKeys.ACCION + ".value = '" + AWNotas.CARGAR_INFO_REQUEST+ "';\r\n");		
				out.write("       		document."+nombreFormulario+".action = 'notas.do';\r\n");
				out.write("       		document."+nombreFormulario+".submit();\r\n");
				out.write("       	}catch(e){\r\n");
				out.write("       		document.NOTAS.action = 'notas.informativas.view';\r\n");		
				out.write("       		document.NOTAS.submit();\r\n");
				out.write("       	}\r\n");
			}else{
				out.write("			if(getAprobado()<1){");
				out.write("       		try{\r\n");		
				out.write("       			document."+nombreFormulario+"." + WebKeys.ACCION + ".value = '" + AWNotas.CARGAR_INFO_REQUEST+ "';\r\n");		
				out.write("       			document."+nombreFormulario+".action = 'notas.do';\r\n");
				out.write("       			document."+nombreFormulario+".submit();\r\n");
				out.write("       		}catch(e){\r\n");
				out.write("       			document.NOTAS.action = 'notas.informativas.view';\r\n");		
				out.write("       			document.NOTAS.submit();\r\n");
				out.write("       		}\r\n");
				out.write("			}");
			}
		}else{
			out.write("       	document.NOTAS.action = 'notas.informativas.view';\r\n");		
			out.write("       	document.NOTAS.submit();\r\n");
		}
		out.write("       }\r\n");		
		out.write("   </SCRIPT>\r\n");	
	
	
	
	
		out.print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
		out.write("<tr>"); 
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"7\" height=\"10\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn003.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td><img name=\"tabla_central_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn003.gif\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>"); 
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn001.gif\" class=\"titulotbcentral\">NOTAS</td>");
		out.write("<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("<td width=\"20\" align=\"center\" valign=\"top\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn002.gif\">"); 
		
		out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.write("<tr>"); 
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ico_notas.gif\" width=\"16\" height=\"21\"></td>");
		out.write("</tr>");
		out.write("</table></td>");
		
		out.write("<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table></td>");
		
		out.write("<td><img name=\"tabla_central_pint_r1_c7\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td width=\"7\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
		out.write("<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
		
		out.write("<form action=\"notas.informativas.view\" method=\"post\" name=\"NOTAS\" target=\"_self\" id=\"NOTAS\">");
		
		session.setAttribute(AWNotas.VISTA_ORIGINADORA,request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));
		session.removeAttribute(CTipoNota.DESCRIPCION);
		session.removeAttribute(CNota.DESCRIPCION);
		session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
		session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		
		out.write("<br>");
		
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>"); 
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("<td class=\"bgnsub\">Notas Informativas</td>");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_notas.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		
		out.write("<br>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>"); 
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_notas_informativas.gif\" width=\"180\" height=\"21\" border=\"0\" style=\"cursor:'hand'\"  onclick=\"cargarDatos()\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</form>");
		out.write("</td>");
		out.write("<td width=\"11\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td><img name=\"tabla_central_r3_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn006.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
		out.write("<td><img name=\"tabla_central_pint_r3_c7\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
				
	}
	/**
	 * @param b
	 */
	public void setMostrardevolutivas(boolean b) {
		mostrardevolutivas = b;
	}

	/**
	 * @return
	 */
	public String getNombreFormulario() {
		return nombreFormulario;
	}

	/**
	 * Método usado para colocarle el nombre del formulario que esta en el jsp, y al cuál desea guardarsele la
	 * información que el usuario ha ingresado, si este atributo tiene el nombre del formulario, automáticamente
	 * se le guarda la información que tiene el formulario, sino se le coloca nada se pierde la información que 
	 * el usuario ha ingresado.
	 * @param string
	 */
	public void setNombreFormulario(String string) {
		nombreFormulario = string;
	}
	
	public void setEsRevisarAprobar(boolean esRevisarAprobar) {
		this.esRevisarAprobar = esRevisarAprobar;
	}

}

