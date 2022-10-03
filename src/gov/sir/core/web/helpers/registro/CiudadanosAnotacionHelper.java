package gov.sir.core.web.helpers.registro;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWFolio;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.ListaTipoPlano;
import gov.sir.core.web.helpers.comun.ListaTipoString;
import gov.sir.core.web.helpers.comun.TextHelper;

/**
 * @author mmunoz
 */
public class CiudadanosAnotacionHelper extends Helper{
	
	private List ciudadanos;
	
	private ListaElementoHelper tipoIDHelper; 
	
	private ListaTipoString tipoInterHelper;
	
	private ListaTipoPlano tipoPropHelper;
	
	private boolean isLink=false;
	
	private TextHelper textHelper;
	
	private List tiposPropietario;
		
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
	
		HttpSession session = request.getSession();
		ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
		
		if(ciudadanos == null || ciudadanos.size()==0) {
			ciudadanos = new Vector();
		}
		
		tipoIDHelper = new ListaElementoHelper();
		List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
 		List tiposIDNew = new ArrayList();
		
		for (Iterator iter = tiposID.iterator(); iter.hasNext();) {
			ElementoLista element = (ElementoLista) iter.next();
			tiposIDNew.add(element);
		}

		
		
		if(tiposID != null){
			tipoIDHelper.setOrdenar(false);
			tipoIDHelper.setCssClase("camposformtext");
			tipoIDHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA);
			tipoIDHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA);
			tipoIDHelper.setTipos(tiposIDNew);
		}
		

		tipoInterHelper = new ListaTipoString();
		
		List tiposParticipacion = null;//(List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
		
		if(tiposParticipacion == null){
			tiposParticipacion = new Vector();
			tiposParticipacion.add("DE");
			tiposParticipacion.add("A");	 
		} 
			
	
		tipoInterHelper.setCssClase("camposformtext");
		tipoInterHelper.setId(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);
		tipoInterHelper.setListaTipos(tiposParticipacion);
		
		textHelper = new TextHelper();
		textHelper.setCssClase("camposformtext");
		
		tiposPropietario = new Vector();
		tiposPropietario.add("");
		tiposPropietario.add("X");
		tiposPropietario.add("I");
		
		tipoPropHelper = new ListaTipoPlano();
		tipoPropHelper.setCssClase("camposformtext");
		tipoPropHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION);
		tipoPropHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION);
		tipoPropHelper.setListaTipos(tiposPropietario);
		
	}
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {

		//		Libreria para mostrar el campo de participacion
		out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");
		out.println("<!-- sof:block: \"alt behavior\" -->");
		
		out.println("<script type=println>");
		out.println("var ol_fgcolor=\"#ffffc0\";");
		out.println("var ol_border=\"1\";");
		out.println("var ol_bgcolor=\"#FFFFC0\";");
		out.println("var ol_textcolor=\"#000000\";");
		out.println("var ol_capcolor=\"#aaaaaa\";");
		//		var ol_css="forms-help";
		
		out.println("</script>");
		out.println("<style media=\"screen\">");
		out.println(".forms-help {");
		out.println("   border-style: dotted;");
		out.println("   border-width: 1px;");
		out.println("   padding: 5px;");
		out.println("   background-color:#FFFFC0; /* light yellow */");
		out.println("    width: 200px; /* otherwise IE does a weird layout */");
		out.println("   z-index:1000; /* must be higher than forms-tabContent */");
		out.println("}");
		
		out.println("</style>");
		out.println("<script type=\"text/javascript\" src=\""+ request.getContextPath()+"/jsp/plantillas/privileged/overlib.js\"><!-- overLIB (c) Erik Bosrup --></script>");
		out.println("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>");
		
		out.println("<!-- eof:block -->");
		out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");	
		
		
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.println("<tr>");
			out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.println("<td class=\"bgnsub\">Personas que intervienen ya Adicionadas a la Anotación</td>");
			out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
			out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.println("</tr>");
			out.println("</table>");
			
			
		//Tablas donde se muestran los ciudadanos insertados
		if(!ciudadanos.isEmpty()){
			int i=0;
			Iterator itCiudadanos = ciudadanos.iterator();
			out.println("<table width=\"100%\" class=\"camposform\">");
			while(itCiudadanos.hasNext()){
				AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
				String part="&nbsp;";
				String participacion = "";
				
				if(anotacionCiudadano.getParticipacion()!=null){
				    participacion=anotacionCiudadano.getParticipacion();
				    if(participacion.length()>3){
				    	part= participacion.substring(0,3);
				    }else{
				    	part= participacion;
				    }
				    participacion=anotacionCiudadano.getParticipacion();
				}
				out.println("<tr>"); 
				out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.println("<td class=\"titulotbcentral\">" + anotacionCiudadano.getRolPersona() + "</td>");
				out.println("<td>" +  anotacionCiudadano.getCiudadano().getApellido1() + " " + anotacionCiudadano.getCiudadano().getApellido2() + " " + anotacionCiudadano.getCiudadano().getNombre() + " " + "</td>");
				out.println("<td>" + anotacionCiudadano.getCiudadano().getTipoDoc() + "</td>");
				out.println("<td>" + anotacionCiudadano.getCiudadano().getDocumento()+ "</td>");
				//mostrar version abreviada y mostrar completo al hacer click en el
				out.println("<td onclick='return overlib(\""+ participacion + "\" , STICKY, MOUSEOFF );' onmouseout='nd();' >&nbsp;&nbsp;"+ participacion   +"&nbsp;&nbsp;</td> ");
				//out.println("<td onclick='return overlib(\""+ participacion + "\" , STICKY, MOUSEOFF );' onmouseout='nd();' >&nbsp;&nbsp;"+ part   +"&nbsp;&nbsp;</td> ");
				out.println("<td width=\"20\" align=\"center\" class=\"titresaltados\">"+anotacionCiudadano.getStringMarcaPropietario()+"&nbsp;</td>");
				
				if (!isLink){
					out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"quitar('" + i + "','" + AWFolio.ELIMINAR_CIUDADANO+ "')\"></td>");
				}else{
					out.println("<td width=\"40\"><a href=\"javascript:quitar('" + i + "','" + AWFolio.ELIMINAR_CIUDADANO+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
				}
				
				out.println("</tr>");
				i++;
			}
			out.println("</table>");
				
		} else {
			out.println("<table width=\"100%\" class=\"camposform\">");
			out.println("<tr>");
			out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("<td class=\"titulotbcentral\">" + "No hay ciudadanos relacionados con la anotación" + "</td>");
			out.println("</tr>");
			out.println("</table>");
		}

		out.println("<br>");
		out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("<tr>");
		out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.println("<td class=\"bgnsub\">Adicionar Personas que intervienen en el Acto</td>");
		out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
		out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.println("</tr>");
		out.println("</table>");
		
		
		out.println("<table width=\"100%\" class=\"camposform\">");
		out.println("<tr>");
		out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.println("<td>Asociaci&oacute;n</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>&nbsp;</td>");
		out.println("<td><table width=\"100%\" class=\"camposform\">");
		out.println("<tr>");
		out.println("<td>Tipo Intervenci&oacute;n </td>");
		out.println("<td>");
		tipoInterHelper.render(request,out);
		out.println("</td>");
		out.println("<td>Propietario?</td>");
		out.println("<td>");
		//out.println("<input name=\"" + CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + "\" type=\"checkbox\" value=\"" + CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + "\">");
		tipoPropHelper.render(request,out);
		out.println("</td>");
		out.println("<td>Porcentaje</td>");
		out.println("<td>");
		textHelper.setId(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
		textHelper.setNombre(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
		textHelper.render(request,out);
		out.println("%</td>");
		out.println("</tr>"); 
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("<tr>");
		out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.println("<td>Identificaci&oacute;n</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>&nbsp;</td>");
		out.println("<td>");
		out.println("<table width=\"100%\" class=\"camposform\">");
		out.println("<tr>");
		out.println("<td>Tipo</td>");
		out.println("<td>");
		tipoIDHelper.render(request,out);
		out.println("</td>");
		out.println("<td>N&uacute;mero</td>");
		out.println("<td>");
		textHelper.setId(CFolio.ANOTACION_NUM_ID_PERSONA);
		textHelper.setNombre(CFolio.ANOTACION_NUM_ID_PERSONA);
		textHelper.render(request,out);
		out.println("</tr>");
		out.println("</table></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.println("<td>B&aacute;sicos</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>&nbsp;</td>");
		out.println("<td><table width=\"100%\" class=\"camposform\">");
		out.println("<tr>");
		out.println("<td>Primer Apellido / Raz&oacute;n Social</td>");
		out.println("<td>");
		textHelper.setSize("55");
		textHelper.setId(CFolio.ANOTACION_APELLIDO_1_PERSONA);
		textHelper.setNombre(CFolio.ANOTACION_APELLIDO_1_PERSONA);
		textHelper.render(request,out);
		out.println("</td>");
		out.println("<td>Segundo Apellido </td>");
		out.println("<td>");
		textHelper.setSize("30");
		textHelper.setId(CFolio.ANOTACION_APELLIDO_2_PERSONA);
		textHelper.setNombre(CFolio.ANOTACION_APELLIDO_2_PERSONA);
		textHelper.render(request,out);
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Nombres</td>");
		out.println("<td>");
		textHelper.setSize("30");
		textHelper.setId(CFolio.ANOTACION_NOMBRES_PERSONA);
		textHelper.setNombre(CFolio.ANOTACION_NOMBRES_PERSONA);
		textHelper.render(request,out);
		out.println("</td>");		
		out.println("<td>&nbsp;</td>");
		out.println("<td>&nbsp;</td>");
		out.println("</tr>");
		out.println("</table></td>");
		out.println("</tr>");

	
		out.println("<tr>");
		out.println("<td>&nbsp;</td>");
		out.println("<td align=\"right\">");
		out.println("<table  border=\"0\" class=\"camposform\">");
		out.println("<tr align=\"center\">");
		out.println("<td>Agregar</td>");
		out.println("</tr>");
		out.println("<tr align=\"center\">");
		if (!isLink){
			out.println("<td><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"cambiarAccion('" + AWFolio.AGREGAR_CIUDADANO + "')\"></td>");
		}else{
			out.println("<td><a href=\"javascript:cambiarAccion('" + AWFolio.AGREGAR_CIUDADANO + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
		}
		
		out.println("</tr>");
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<hr class=\"linehorizontal\"></td>");
	}

	/**
	 * @param b
	 */
	public void setLink(boolean b) {
		isLink = b;
	}

}
