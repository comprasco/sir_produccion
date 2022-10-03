package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWTurno;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CMensajesAyuda;
import gov.sir.core.negocio.modelo.util.Log;

/**
 * @author I.Siglo21
 * Esta clase fue diseñada para poder pintar de manera clara un menu 
 * de navagacion para que el usuario pueda cambiar entre procesos y fases sin 
 * cambiar de pantalla
 */
public class MenuHelper extends Helper{
	
	/**
	 * Esta constante guarda la estacion que escogio el usuario para trabajar 
	 */
	private Estacion estacion;
	
	/**
	 * Esta constante guarda el rol que escogio el usuario para trabajar 
	 */	
	private Rol rol;
	
	/**
	 * Esta lista guarda los procesos para la estacion que selecciono el usuario
	 */
	private List procesos = new Vector();
	
	/**
	 * Esta lista guarda los procesos que pueden ser iniciados
	 * por el usuario dada la estacion que escogio
	 */
	private List procesosIniciables = new Vector();
	
	/**
	 * Se guarda el proceso actual que el usuario ha seleccionado
	 */
	private Proceso proceso;
	
	/**
	 * Esta lista se guardan las fases de acuerdo al proceso y a la 
	 * estacion que el usuario ha seleccionado
	 */
	private List fases = new Vector();
	
	/**
	 * Se guarda la fase actual que el usaurio ha seleccionado
	 */
	private Fase fase;
	
	/** Informacion del usuario que inicio sesion*/
	private Usuario usuario;
	
	/**Esta constante estando en true permite llamar al metodo que 
	 * limpia la sesion dejando solamente los datos necesarios hasta 
	 * el menu.
	 */
	private boolean seQuiereLimpiarSesion = true;
	

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		
		estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		rol = (Rol) session.getAttribute(WebKeys.ROL);
		procesos = (List) session.getAttribute(WebKeys.LISTA_PROCESOS);
		proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		fases = (List) session.getAttribute(WebKeys.LISTA_FASES);
		fase = (Fase) session.getAttribute(WebKeys.FASE);
		usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
		procesosIniciables = (List) session.getAttribute(WebKeys.LISTA_PROCESOS_INICIABLES);
		if(proceso==null){
			proceso =  new Proceso(0,"","");	
		}
		
		if(usuario==null){
			usuario =  new Usuario();	
		}		
		
		if(fase == null){
			fase = new Fase("","","","");	
		}
		
		if(estacion == null){
			estacion = new Estacion();	
		}
		
		if(procesos == null){
			procesos = new Vector();
		}
		
		if(fases == null){
			fases = new Vector();
		}
		
		if(procesosIniciables == null){
			procesosIniciables = new Vector();
		}
		
		if(seQuiereLimpiarSesion){
			limpiarInfoSession(session);	
		}
	}

	/**
	 * Este metodo limpia de sesion todo lo que no este aca declarado.
	 * Solo deja los atributos con el siquientes nombres:
	    Lo que comienze por "org.auriga"
		WebKeys.LISTA_ROLES
		WebKeys.LISTA_ESTACIONES
		WebKeys.LISTA_PROCESOS
		WebKeys.LISTA_PROCESOS_INICIABLES
		WebKeys.LISTA_FASES
		WebKeys.LISTA_TURNOS
		WebKeys.ROL
		WebKeys.ESTACION
		WebKeys.USUARIO
		WebKeys.CIRCULO
		WebKeys.PROCESO
		WebKeys.FASE
		WebKeys.NUMERO_MAX_IMPRESIONES
		WebKeys.LISTA_TIPOS_NOTAS
		WebKeys.LISTENER
	 * @param session HttpSession
	 */
	private void limpiarInfoSession(HttpSession session) {
		Enumeration enumeration = session.getAttributeNames();
		String nombre = new String();
		while(enumeration.hasMoreElements()){
			nombre = (String) enumeration.nextElement();
			if(!nombre.startsWith("org.auriga") &&
					!nombre.equals(WebKeys.LISTA_ROLES) &&
					!nombre.equals(WebKeys.LISTA_ESTACIONES) &&
					!nombre.startsWith(WebKeys.LISTA_IMPRESORAS) &&
			
                                !nombre.equals(WebKeys.LISTA_PROCESOS) &&
					!nombre.equals(WebKeys.LISTA_PROCESOS_INICIABLES) &&
					!nombre.equals(WebKeys.LISTA_FASES) &&
					!nombre.equals(WebKeys.LISTA_TURNOS) &&
					!nombre.equals(WebKeys.LISTA_TURNOS_PARCIAL) &&
					!nombre.equals(WebKeys.ROL) &&
					!nombre.equals(WebKeys.ESTACION) &&
					!nombre.equals(WebKeys.USUARIO) &&
					!nombre.equals(WebKeys.CIRCULO) &&
					!nombre.equals(WebKeys.PROCESO) &&
					!nombre.equals(WebKeys.FASE) &&
					!nombre.equals(WebKeys.NUMERO_MAX_IMPRESIONES) &&
					!nombre.equals(WebKeys.LISTA_TIPOS_NOTAS) &&
					!nombre.equals(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS) &&
					!nombre.equals(WebKeys.LISTA_TURNOS_SIR_MIG) &&
					!nombre.equals(WebKeys.LISTENER) &&
					!nombre.equals(WebKeys.ALERTAS) &&
					!nombre.equals(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO) &&
                                        !nombre.equals(WebKeys.REPARTO_NOTARIAL_EN_HORARIO) &&
					!nombre.equals("SESSION_SIZE") &&
                                        !nombre.equals("GENERAR_ALERTA_SIN_CAMBIOS") &&
                                        !nombre.equals("TRAMS_TURNOS")) {
				Log.getInstance().warn(MenuHelper.class,"Se eliminara de session el siguiente atributo: " + nombre);
				session.removeAttribute(nombre);
			}
		}
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td></td>");
		out.write("<td></td>");
		out.write("<td></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td>&nbsp;</td>");
		out.write("<td>&nbsp;</td>");
		out.write("</tr>");
		
		out.write("<tr>"); 
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_esquina001.gif\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn002.gif\" class=\"tdtablaanexa02\">"); 
		out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>"); 
		out.write("<td width=\"16\" background=\"" + request.getContextPath() + "/jsp/images/barratitle_bgn001.gif\"></td>");
		out.write("<td width=\"11\"></td>");
		out.write("<td width=\"11\"></td>");
		out.write("<td width=\"11\"></td>");
		out.write("<td width=\"11\"></td>");
		out.write("<td width=\"11\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td valign=\"top\" background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\"><img name=\"tabla_gral_r1_c5\" src=\"" + request.getContextPath() + "/jsp/images/tabla_gral_r1_c5.gif" + "\" width=\"12\" height=\"23\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		
		out.write("<tr>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn003.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"5\" height=\"10\"></td>");
		out.write("<td class=\"tdtablaanexa02\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"5\" height=\"10\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"5\" height=\"10\"></td>");
		out.write("</tr>");
		
		out.write("<tr>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn003.gif\">&nbsp;</td>");
		out.write("<td class=\"tdtablaanexa02\"> <table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<form name=\"Roles\" method=\"post\" action=\"roles.view\">");
		out.write("<tr>"); 
		out.write("<td width=\"10\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_inactivo_ini.gif\" width=\"10\" height=\"18\"></td>");
		out.write("<td class=\"bgnmenuinactivo\"> <input name=\"Submit2\" type=\"submit\" class=\"botoninactivo\" value=\"CAMBIAR ROL\" title=\""+CMensajesAyuda.MSG_CAMBIAR_ROL+"\"></td>");
		out.write("<td width=\"16\" class=\"bgnmenuinactivo\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_cambiosesion.gif\" width=\"16\" height=\"18\"></td>");
		out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menuinactivo.gif\" width=\"11\" height=\"18\"></td>");
		out.write("</tr>");
		out.write("</form>");
		out.write("<tr>"); 
		out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
		out.write("</tr>");
		out.write("<form name=\"EstacionesV\" method=\"post\" action=\"estaciones.view\">");
		out.write("<tr>"); 
		out.write("<td width=\"10\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_inactivo_ini.gif\" width=\"10\" height=\"18\"></td>");
		out.write("<td class=\"bgnmenuinactivo\"> <input name=\"Submit2\" type=\"submit\" class=\"botoninactivo\" value=\"CAMBIAR ESTACION\" title=\""+CMensajesAyuda.MSG_CAMBIAR_ESTACION+"\"></td>");
		out.write("<td width=\"16\" class=\"bgnmenuinactivo\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_cambiar_estacion.gif\" width=\"16\" height=\"18\"></td>");
		out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menuinactivo.gif\" width=\"11\" height=\"18\"></td>");
		out.write("</tr>");
		out.write("</form>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
		out.write("</tr>");
		out.write("</table></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\">&nbsp;</td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn003.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"5\"></td>");
		out.write("<td class=\"tdtablaanexa02\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"5\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\"><img src=\"" + request.getContextPath()  + "/jsp/images/spacer.gif\" width=\"10\" height=\"5\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td width=\"12\" class=\"bgnmenuactivo\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
		out.write("<td class=\"tdtablaanexa02\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td class=\"bgnmenuactivo\"> Men&uacute;</td>");
		out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menu.gif\" width=\"11\" height=\"18\"></td>");
		out.write("</tr>");
		out.write("</table></td>");
		out.write("<td width=\"12\" background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\"><img src=\"" + request.getContextPath()+ "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td height=\"47\" background=\""+  request.getContextPath()+ "/jsp/images/tabla_gral_bgn003.gif\">&nbsp;</td>");
		out.write("<td valign=\"top\" class=\"tdtablaanexa02\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.write("<tr>"); 
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"30\" height=\"5\"></td>");
		out.write("</tr>");
		out.write("</table>");
	

		if(!procesos.isEmpty()){
			Iterator itProcesos = procesos.iterator();
			while(itProcesos.hasNext()){
				Proceso tempProceso = (Proceso) itProcesos.next();
				if(proceso.getIdProceso() == tempProceso.getIdProceso()){
					//mostrar proceso seleccionado
					out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					out.write("<tr>"); 
					out.write("<form name=\"Estaciones\" method=\"post\" action=\"seguridad.do\">");
					out.write("<input type=\"hidden\" name=\"ACCION\" value=\"CONSULTAR_PROCESO\">");
					out.write("<input type=\"hidden\" name=\"ID_PROCESO\" value=\"" + tempProceso.getIdProceso() + "\">");
					out.write("<td width=\"10\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_activo_ini.gif\" width=\"10\" height=\"18\"></td>");
					out.write("<td class=\"bgnmenuactivo\"> <input name=\"" + proceso.getNombre() + "\" type=\"submit\" class=\"botonactivo\" value=\"" + proceso.getNombre() + "\">");
					out.write("</td>");
					out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menu.gif\" width=\"11\" height=\"18\"></td>");
					out.write("</form>");
					out.write("</tr>");
					out.write("<tr>"); 
					out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() +"/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
					out.write("</tr>");
					out.write("</table>");
					
					if(procesosIniciables.contains(proceso)){
						out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
						out.write("<tr>");
						out.write("<form name=\"procesosIniciables\" method=\"post\" action=\"turno.do\">");
						out.write("<td width=\"42\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_marcador_menu.gif\" width=\"42\" height=\"18\"></td>");
						out.write("<td width=\"16\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_inicioproceso.gif\" width=\"16\" height=\"18\"></td>");
						out.write("<td class=\"bgnmenuiniproceso\"><input name=\"FASE\" type=\"submit\" class=\"botoniniproceso\" id=\"FASE\" value=\"INICIAR\">");
						out.write("</td>");
						out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_marcador02_menu.gif\" width=\"11\" height=\"18\"></td>");
						out.write("<input type=\"hidden\" name=\""+WebKeys.ACCION +"\" value=\""+AWTurno.INICIAR_PROCESO +"\">");
						out.write("<input type=\"hidden\" name=\""+AWTurno.ID_PROCESO +"\" id=\""+AWTurno.ID_PROCESO +"\" value=\""+proceso.getIdProceso()+"\">");
						out.write("</form>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
						out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"16\" height=\"2\"></td>");
						out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
						out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
						out.write("</tr>");
						out.write("</table>");
					}
					if(!fases.isEmpty()){
						Iterator itFases = fases.iterator();
						while(itFases.hasNext()){
							Fase tempFase = (Fase) itFases.next();
                                                        //System.out.println(tempFase.getNombre());
							if(fase.getID().equals(tempFase.getID())){
								//mostrar fase selecionada
								out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
								out.write("<tr>");
								out.write("<form action=\"turno.do\" method=\"post\" name=\"FASE\">");
								out.write("<input type=\"hidden\" name=\"ACCION\" value=\"LISTAR\">");
								out.write("<input type=\"hidden\" name=\"ID_FASE\" value=\"" + tempFase.getID() + "\">");
								out.write("<td align=\"right\" width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_activo_ini_fases.gif\" width=\"10\" height=\"18\"></td>");
								out.write("<td class=\"botonactivo_fases\"> <input name=\"" + tempFase.getNombre() + "\" type=\"submit\" class=\"botonactivo_fases\" id=\"" + tempFase.getNombre() + "\" value=\"" + tempFase.getNombre() + "\">"); 
								out.write("</td>");
								out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menu_fases.gif\" width=\"11\" height=\"18\"></td>");
								out.write("</form>");
								out.write("</tr>");                                                                
								out.write("<tr>"); 
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
								out.write("</tr>");
								out.write("</table>");
							} else {
								//Fase sin seleccionar
								out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
								out.write("<tr>");
								out.write("<form action=\"turno.do\" method=\"post\" name=\"FASE\">");
								out.write("<input type=\"hidden\" name=\"ACCION\" value=\"LISTAR\">");
								out.write("<input type=\"hidden\" name=\"ID_FASE\" value=\"" + tempFase.getID() + "\">");
								out.write("<td align=\"right\" width=\"20\"><img src=\""+ request.getContextPath() + "/jsp/images/menu_inactivo_ini_fases.gif\" width=\"10\" height=\"18\"></td>");
								out.write("<td class=\"bgnmenuactivo_fases\"> <input name=\"" + tempFase.getNombre() + "\" type=\"submit\" class=\"botoninactivo_fases\" id=\"" + tempFase.getNombre() + "\" value=\"" + tempFase.getNombre() + "\" title=\""+CMensajesAyuda.MSG_SELECCIONAR_FASE + tempFase.getNombre()+"\">"); 
								out.write("</td>");
								out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menuinactivo_fases.gif\" width=\"11\" height=\"18\"></td>");
								out.write("</form>");
								out.write("</tr>");
								out.write("<tr>"); 
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
								out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
								out.write("</tr>");
								out.write("</table>");
							}
						}
					} else {
						//Fases vacias
						out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");						
						out.write("<tr>");						
						out.write("<td width=\"20\" align=\"right\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_ini_sin_fases.gif\" width=\"10\" height=\"18\"></td>");
						out.write("<td class=\"bgnmenu_sin_fases\">Sin Fases</td>");
						out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menu_sin_fases.gif\" width=\"11\" height=\"18\"></td>");
						out.write("</tr>");
						out.write("</table>");						
					}
				} else {
					//mostrar el proceso sin seleccionar
					out.write("<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					out.write("<tr>"); 
					out.write("<form name=\"Estaciones\" method=\"post\" action=\"seguridad.do\">");
					out.write("<input type=\"hidden\" name=\"ACCION\" value=\"CONSULTAR_PROCESO\">");
					out.write("<input type=\"hidden\" name=\"ID_PROCESO\" value=\"" + tempProceso.getIdProceso() + "\">");
					out.write("<td width=\"10\"><img src=\"" + request.getContextPath() + "/jsp/images/menu_inactivo_ini.gif\" width=\"10\" height=\"18\"></td>");
					out.write("<td class=\"bgnmenuinactivo\"> <input name=\"" + tempProceso.getNombre() + "\" type=\"submit\" class=\"botoninactivo\" id=\"ID_PROCESO \" value=\"" + tempProceso.getNombre() + "\" title=\""+CMensajesAyuda.MSG_SELECCIONAR_PROCESO + tempProceso.getNombre()+"\">"); 
					out.write("</td>");
					out.write("<td width=\"11\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_menuinactivo.gif\" width=\"11\" height=\"18\"></td>");
					out.write("</form>");
					out.write("</tr>");
					out.write("<tr>"); 
					out.write("<td><img src=\""+ request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"2\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"20\" height=\"2\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"11\" height=\"2\"></td>");
					out.write("</tr>");
					out.write("</table>");
					
				}
			}
		} else {
			//procesos vacios
			out.write("procesos vacios");
		}
		
		out.write("</td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn004.gif\">&nbsp;</td>");
		out.write("</tr>");
		out.write("<tr>"); 
		out.write("<td><img name=\"tabla_gral_r3_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_gral_r3_c1.gif\" width=\"12\" height=\"20\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_gral_bgn005.gif\">&nbsp;</td>");
		out.write("<td><img name=\"tabla_gral_r3_c5\" src=\"" + request.getContextPath() + "/jsp/images/tabla_gral_r3_c5.gif\" width=\"12\" height=\"20\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");	
	}
	
	
	

	/**
	 * Cambia el valor de esta variable 
	 * @param b boolean
	 */
	public void setSeQuiereLimpiarSesion(boolean b) {
		seQuiereLimpiarSesion = b;
	}

}
