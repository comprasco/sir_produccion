package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.administracion.AWRelacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWTurno;
import gov.sir.core.web.acciones.registro.AWReparto;
import gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial;
import gov.sir.core.web.acciones.restitucionreparto.AWRestitucionReparto;

/**
 * @author I.Siglo21
 * Esta clase fue diseñada para poder pintar de manera clara los procesos que un usuario tiene disponibles, con
 * la ventaja de tener la minima cantidad de codigo Java en los jsp's.
 */
public class TurnosHelper extends Helper {
	
	/**
	 * En esta estructura se guadaran todas las estaciones que tenga asociadas el usuario
	 */
	private List turnos = new Vector();
	
	private List turnosSirMig = new Vector();

	private boolean esReparto = false;

	private boolean esRepartoNotarial = false;
	
	private boolean esRestitucionRepartoNotarial = false;
	
	private String matricula="";
	
	private String anio = "";
	
	private String idCirculo = "";
	
	private String idProceso = "";
	
	private boolean esEntregaRepartoNotarial = false;
	
	private boolean esCertificadosAsociados = false;
        
        /**
         * @author : Edgar Lora
         * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         */
        private boolean enHorarioRepartoNotarial = true;

	/**
	 * Método constructor por defecto de la clase FasesHelper
	 */
	public TurnosHelper() {
		turnos.clear();
	}

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();

		this.turnosSirMig = (List) session.getAttribute(WebKeys.LISTA_TURNOS_SIR_MIG);
		if(this.turnosSirMig==null ||this.turnosSirMig.size()==0){
			this.turnosSirMig = new ArrayList();
		}
		
		List turnos1 = (List) session.getAttribute(WebKeys.LISTA_TURNOS);
		if (turnos1 != null) {
			this.turnos = turnos1;
		} else {
			throw new HelperException("La lista de turnos es invalida");
		}
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		
		if(fase.getID().equals(CFase.REP_ENTREGA))
			this.esEntregaRepartoNotarial = true;
		
		if (fase.getID().equals(CFase.REG_REPARTO)) {
			esReparto = true;
			
			Calendar cal= Calendar.getInstance();
			Date da= new Date();
			cal.setTime(da);
			int valano=cal.get(Calendar.YEAR);
			
			String ano=Integer.toString(valano);
			String circulo="";
			
			Circulo oCirculo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
			circulo=oCirculo.getIdCirculo();
			
			String proceso=CProceso.PROCESO_REGISTRO;
			
			matricula=ano+"-"+circulo+"-"+proceso+"-";
			
			anio = ano;
			idCirculo = circulo;
			idProceso = proceso;
			
		}
		if (fase.getID().equals(CFase.REP_REPARTO)) {
			esRepartoNotarial = true;
		}
		if (fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)) {
			esCertificadosAsociados = true;
		}
		
		
		//Crear la lista de turnos quitando los turnos que estan anulados
		List turnosDepurados = new ArrayList();
		Iterator it = turnos.iterator();
		while(it.hasNext()){
			Turno t = (Turno) it.next();
			if(t.getAnulado()== null || !t.getAnulado().equals(CTurno.TURNO_ANULADO)){
				turnosDepurados.add(t);
			}
		}
		this.turnos = turnosDepurados;
		
		if (fase.getID().equals(CFase.RES_ANALISIS)) {
			esRestitucionRepartoNotarial = true;
			request.getSession().setAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL,turnos);
		}
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		
		String formName = "Turnos";

		if (esReparto || esRepartoNotarial || esRestitucionRepartoNotarial) {
			
			
			
			List ordinarias = new ArrayList();
			List extraordinarias = new ArrayList();
			
			
			if(esReparto){
                                out.write("<form name=\""+formName+"\" method=\"post\" action=\"reparto.registro.do\">");
				out.write("<input type=\"hidden\" id=\"" + WebKeys.REDIRECCION_DO + "\" name=\"" + WebKeys.REDIRECCION_DO + "\" value=\"reparto.registro.do\" >");
				out.write("<input type=\"hidden\" id=\"" + WebKeys.ACCION + "\" name=\"" + WebKeys.ACCION + "\" value=\"" + AWReparto.CREAR + "\">");
			}else{
                                out.write("<form id=\""+formName+"\" name=\""+formName+"\" method=\"post\" action=\"wait.view\">");
                            }
			if(esRepartoNotarial){			
				out.write("<input type=\"hidden\" id=\"" + WebKeys.REDIRECCION_DO + "\" name=\"" + WebKeys.REDIRECCION_DO + "\" value=\"repartoNotarial.do\" >");
				out.write("<input type=\"hidden\" id=\"ACCION_REPARTO\" name=\"" + WebKeys.ACCION + "\" value=\"" + AWRepartoNotarial.EJECUTAR_REPARTO + "\">");
			}
			if(esRestitucionRepartoNotarial){			
				out.write("<input type=\"hidden\" id=\"" + WebKeys.REDIRECCION_DO + "\" name=\"" + WebKeys.REDIRECCION_DO + "\" value=\"restitucionReparto.do\" >");
				out.write("<input type=\"hidden\" id=\"" + WebKeys.ACCION + "\" name=\"" + WebKeys.ACCION + "\" value=\"" + AWRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION+ "\">");
			}
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("	<tr>");
			out.write("	<td>");
			out.write("		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("			<tr>");
			out.write("				<td>&nbsp;</td>");
			out.write("				<td>&nbsp;</td>");
			out.write("				<td width=\"10\">&nbsp;</td>");
			out.write("			</tr>");
			out.write(		"</table>");
			out.write("	</td>");
			out.write("	</tr>");
			out.write("	<tr>");
			out.write("	<td>");
			if (turnos.isEmpty() && !esRepartoNotarial) {
				out.write("		<table width=\"100%\" class=\"camposform\">");
				out.write("			<tr>");
				out.write("				<td>");
				out.write("En este momento no tiene turnos disponibles");
				out.write("				</td>");
				out.write("			</tr>");
				out.write("		</table>");
				out.write("		</td>");
				out.write("		</tr>");
				out.write("		<tr>");
				out.write("			<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("		</tr>");
				out.write("		<tr>");
				out.write("		<td>&nbsp;</td>");
				out.write("	</tr>");
				out.write("</table>");
			} else {
				
				//SI ES DE REPARTO DE ABOGADOS SE PUEDE ELEGIR UN RANGO PARA REPARTIR.
				if(esReparto){
					
					//Turno turno = (Turno)turnos.get(0);
					
					out.write("<table width=\"100%\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td height=\"17\" class=\"titulotbcentral\" colspan=\"4\">Repartir por rango hasta el turno:</td>");
					out.write("</tr>");
					out.write("</tr>");	
					out.write("<tr>");
					out.write("<td></td>");
					out.write("<td>");	
					try {
						TextHelper textHelper= new TextHelper();	
						textHelper.setNombre(CTurno.TURNO_HASTA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CTurno.TURNO_HASTA);
						textHelper.setSize("5%");
						textHelper.render(request,out);
					}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
					}
					out.write("</td>");
					out.write("<td>-"+idCirculo+"-"+idProceso+"-</td>");
					out.write("<td>");	
					try {
						TextHelper textHelper= new TextHelper();	
						textHelper.setNombre(CTurno.TURNO_HASTA1);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CTurno.TURNO_HASTA1);
						textHelper.setSize("5%");
						textHelper.render(request,out);
					}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
					}
					out.write("</td>");
					out.write("<td></td>");
					out.write("</tr>");	
					out.write("<tr>");
					out.write("<td align=\"center\" colspan=\"5\"><hr class=\"camposform\"></td>");
					out.write("</tr>");
					out.write("<tr>");
					out.write("<td width=\"5%\" align=\"center\"></td>");
					out.write("<td align=\"center\" colspan=\"5\"><input name=\"imageField\" id=\"imageField_id_rango\" onClick = \"javascript:deshabilitarBotonRango()\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_repartir.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");
					out.write("</tr>");
					out.write("</table>");
					
					out.write("<br>");	
				}
				
				//SI ES DE REPARTO DE NOTARIAL DE MINUTAS SE PUEDE DETERMINAR EL TURNO A REPARTIR.
				//ADEMÁS SE PUEDE DETERMINAR EL NÚMERO DE IMPRESIONES DEL REPARTO.				
				if(esRepartoNotarial){
					
					out.write("<script language=\"javascript\" type=\"text/javascript\" src=\""+ request.getContextPath()+"/jsp/plantillas/common.js\"></script>\n");

					out.write("<script>\n");
					out.write("function cambiarTipoRepartoNotarial(text){\n");
					out.write("		document.getElementById('"+ CMinuta.TIPO_REPARTO +"').value = text\n");
					out.write("		document.getElementById('imageField_id').width=0;\n");
					out.write("}\n");	
					out.write("</script>\n");
					
					out.write("<script>\n");
					out.write("function validarTurnosExt(f) \n ");
					out.write("{ \n ");
					out.write("var chks \n ");
					out.write("var checked = false, e, i = 0 \n ");
					out.write("chks = document.getElementsByTagName('input') \n ");
					out.write("for(i=0;i<chks.length;i++) \n ");
					out.write("{ \n ");
					out.write("if (chks[i].type == 'checkbox' && chks[i].checked) \n ");
					out.write("checked = true \n ");
					out.write("} \n ");
					out.write("if (!checked) \n ");
					out.write("{ \n ");
					out.write("alert ('Debe elegir, al menos, un turno extraordinario!') \n ");
					out.write("} \n ");
					out.write("else \n ");
					out.write("{ \n ");
					out.write("document.getElementById('"+ CMinuta.TIPO_REPARTO +"').value = '" + CMinuta.EXTRAORDINARIO + "' \n");
                                        out.write("document.getElementById('"+ WebKeys.REDIRECCION_DO +"').value = 'reparto.notarial.nota.informativa.do' \n");
                                        out.write("document.getElementById('ACCION_REPARTO').value = '" + AWRepartoNotarial.AGREGAR_NOTA_INFORMATIVA + "' \n");
                                        out.write("document."+formName+".action = 'reparto.notarial.nota.informativa.do' \n");
                                        out.write("document."+formName+".submit() \n");
					out.write("} \n ");
					out.write("} \n ");
					out.write("</script> \n ");
					
					/*

					out.write("<script>\n");
					out.write("function validarTurnosExt(f){ \n ");
					out.write(" var checked = false, e, i = 0 \n ");
					out.write(" while (e = f.elements[i++]) { \n");
					out.write(" if (e.type == 'checkbox' && e.checked) \n ");
					out.write(" checked = true \n ");
					out.write("}\n");
					out.write(" if (!checked)\n ");
					out.write(" {alert ('Debe elegir, al menos, un turno extraordinario!')} ");
					out.write("}\n");
					out.write("</script>\n");
*/
					out.write("<input type=\"hidden\" name=\""+CMinuta.TIPO_REPARTO+"\" id=\""+CMinuta.TIPO_REPARTO+"\" value=\""+CMinuta.ORDINARIO+"\" >\n");
					
					
					//SE DETERMINAN EL NÚMERO DE IMPRESIONES QUE DEBE GENERARSE.
					out.write("<table width=\"100%\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td height=\"17\" class=\"titulotbcentral\" colspan=\"3\">Número impresiones</td>");
					out.write("</tr>");
					
					out.write("<tr>");
					out.write("<td><label>");
					out.write("</label></td>");
					out.write("<td>");
					out.write("Resumen");					
					out.write("</td>");
					out.write("<td>");
					out.write("<input type=\"text\" size=\"2\" name=\""+CMinuta.NUMERO_RESUMENES+"\" id=\""+CMinuta.NUMERO_RESUMENES+"\" value=\"4\" onblur=\"javascript:validarNumericoRequeridoValorDefecto('"+CMinuta.NUMERO_RESUMENES+"','4')\" >");
					out.write("</td>");					
					out.write("</tr>");

					out.write("<tr>");
					out.write("<td><label>");
					out.write("</label></td>");
					out.write("<td>");
					out.write("Acta");					
					out.write("</td>");
					out.write("<td>");
					out.write("<input type=\"text\" size=\"2\" name=\""+CMinuta.NUMERO_ACTAS+"\" id=\""+CMinuta.NUMERO_ACTAS+"\" value=\"2\" onblur=\"javascript:validarNumericoRequeridoValorDefecto('"+CMinuta.NUMERO_ACTAS+"','2')\" >");
					out.write("</td>");					
					out.write("</tr>");
					
					out.write("</table>");
					
					out.write("<br>");	
				
					
					
					
					
					

					Iterator itEstaciones = turnos.iterator();
					while (itEstaciones.hasNext()) {
						Turno turno = (Turno) itEstaciones.next();
					
						SolicitudRepartoNotarial sol = (SolicitudRepartoNotarial)turno.getSolicitud();
						if(sol!=null){
							Minuta minuta = sol.getMinuta();
							if(minuta.isNormal()){
								ordinarias.add(turno);
							}else{
								extraordinarias.add(turno);
							}
						}
					}
				
					//SI HAY TURNOS EXTRAORDINARIOS PARA REPARTIR SE MUESTRAN SINO NO.
					//if(false){
					if(extraordinarias.size()>0){
						
											
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td height=\"17\" class=\"titulotbcentral\" colspan=\"3\">Reparto extraordinario</td>");
						out.write("</tr>");
						
						int a = 0;
						Iterator it = extraordinarias.iterator();
						while (it.hasNext()) {
							Turno turno = (Turno) it.next();
							out.write("<tr>");
							out.write("<td><label>");
							out.write("</label></td>");
							out.write("<td>");
							
							if(a==0){
								out.write("<input type=\"checkbox\" id=\""+CTurno.ID_TURNO+"\" name=\""+CTurno.ID_TURNO+"\" value=\""+turno.getIdWorkflow()+"\">");
								a++;	
							}else{
								out.write("<input type=\"checkbox\" id=\""+CTurno.ID_TURNO+"\" name=\""+CTurno.ID_TURNO+"\" value=\""+turno.getIdWorkflow()+"\">");	
							}
																				
							out.write(turno.getIdWorkflow());
							out.write("</input>");
							out.write("</td>");
							out.write("</tr>");
						}
						
						out.write("<tr>");
						out.write("<td align=\"center\" colspan=\"4\"><a href='javascript:validarTurnosExt(this.form);'><img src=\"" + request.getContextPath() + "/jsp/images/btn_repartir.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");
						out.write("</tr>");					
						
						out.write("</table>");
						
						out.write("<br>");	
					}
				}
				
				//Los turnos enlistados como de Reparto Extraordinario NO se deben
				//listar en la lista de turnos a repartir (pues se enlistaron antes)
				
				

								
				
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
				
				int size =  turnos.size() - extraordinarias.size();
				
				out.write("<td height=\"17\" class=\"titulotbcentral\">Turnos a Repartir: "+ size +"</td>");
				out.write("</tr>");

				Iterator itEstaciones = turnos.iterator();
				while (itEstaciones.hasNext()) {
					Turno turno = (Turno) itEstaciones.next();
					if(turno.getAnulado()== null 
							|| !turno.getAnulado().equals(CTurno.TURNO_ANULADO)){
						if(!extraordinarias.contains(turno))
						{
							out.write("<tr>");
							out.write("<td><label>");
							out.write("</label></td>");
							out.write("<td>");
							if(turnosSirMig.contains(turno.getIdWorkflow())){
								out.write(turno.getIdWorkflow() + " - <b>Folio no migrado</b>");								
							}else{
								out.write(turno.getIdWorkflow());	
							}							
							out.write("</td>");
							out.write("</tr>");
							
						}
					}
				}
				
				out.write("<tr>");
				out.write("<td colspan=\"2\" align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				
				if(esReparto){
					out.write("<td align=\"center\" colspan=\"2\"><input name=\"imageField\" id=\"imageField_id\" onClick = \"javascript:deshabilitarBoton()\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_repartir.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");	
				}else if(esRepartoNotarial){
                                        if(enHorarioRepartoNotarial){
                                            out.write("<td align=\"center\" colspan=\"2\"><input name=\"imageField\" id=\"imageField_id\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_repartir.gif\" width=\"139\" onClick=\"javascript:cambiarTipoRepartoNotarial('"+CMinuta.ORDINARIO+"')\" height=\"21\" border=\"0\"></td>");					
                                        }else{
                                            out.write("<td align=\"center\" colspan=\"2\"><a href=\"javascript:alert('No es horario para reparto de las minutas de tipo ordinario.')\"><image src=\"" + request.getContextPath() + "/jsp/images/btn_repartir.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");
                                        }
				} else if(esRestitucionRepartoNotarial){
					out.write("<td align=\"center\" colspan=\"2\"><input name=\"imageField\" id=\"imageField_id\" onClick = \"javascript:deshabilitarBoton()\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_restitucion.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");	
				}
				
				
				out.write("</tr>");
				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");
				out.write("</form>");
			}
		}else if(this.esEntregaRepartoNotarial)
		{
			out.write("<script>\n");
			out.write("function checkUncheckAll(theElement) \n ");
			out.write("{ \n ");
			out.write("var theForm = document."+ formName +", z = 0; \n ");
			out.write("for(z=0; z<theForm.length;z++) \n ");
			out.write("{ \n ");
			out.write("if(theForm[z].type == 'checkbox' && theForm[z].name != 'checkall')\n ");
			out.write("{ \n ");
			out.write(" theForm[z].checked = theElement.checked; \n ");
			out.write("} \n ");
			out.write("} \n ");
			out.write("} \n  ");
			out.write("</script> \n ");
			
			
			out.write("<form name=\"Turnos\" method=\"post\" action=\"turno.do\">");
			out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"" + AWTurno.PROCESAR_TURNOS_MULTIPLES + "\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>&nbsp;</td>");
			out.write("<td width=\"10\">&nbsp;</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>");
			if (turnos.isEmpty()) {
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td>");
				out.write("En este momento no tiene turnos disponibles");
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");
				out.write("</table>");
			} else {

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td height=\"17\" class=\"titulotbcentral\">Seleccione un Turno</td>");
				out.write("</tr>");
				
				out.write("<tr>");
				out.write("<td colspan=\"2\"><label>");
				out.write("<input type=\"checkbox\" name=\"checkall\" onClick=\"checkUncheckAll(this)\"\"> Elegir Todos");
				out.write("</label></td>");
				out.write("</tr>");
				
				boolean primerRegistro = true;
				Iterator itEstaciones = turnos.iterator();
				while (itEstaciones.hasNext()) {
					Turno turno = (Turno) itEstaciones.next();
					if(turno.getAnulado()== null || !turno.getAnulado().equals(CTurno.TURNO_ANULADO)){
						out.write("<tr>");
						out.write("<td><label>");
						if (primerRegistro) {
							out.write("<input type=\"checkbox\" id=\""+AWTurno.ID_TURNO+"\" name=\"" + AWTurno.ID_TURNO + "\" value=\"" + turno.getIdTurno() + "\" checked>");
							primerRegistro = false;
						} else {
							out.write("<input type=\"checkbox\" id=\""+AWTurno.ID_TURNO+"\" name=\"" + AWTurno.ID_TURNO + "\" value=\"" + turno.getIdTurno() + "\">");
						}
						out.write("</label></td>");
						out.write("<td>");
						out.write(turno.getIdWorkflow());
						out.write("</td>");
						out.write("</tr>");
					}
				}

				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><input name=\"imageField\" id=\"imageField_id\" onClick = \"javascript:deshabilitarBoton()\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_aceptar.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");
				out.write("</tr>");
				out.write("</table>");
				out.write("</form>");
			}
		
		}
		else {
			out.write("<form name=\"Turnos\" method=\"post\" action=\"turno.do\">");
			out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"" + AWTurno.CONTINUAR_TURNO + "\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>&nbsp;</td>");
			out.write("<td width=\"10\">&nbsp;</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>");
			if (turnos.isEmpty()) {
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td>");
				out.write("En este momento no tiene turnos disponibles");
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");
				out.write("</table>");
			} else {

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td height=\"17\" class=\"titulotbcentral\">Seleccione un Turno</td>");
				out.write("</tr>");
				
				boolean primerRegistro = true;
				Iterator itEstaciones = turnos.iterator();
				while (itEstaciones.hasNext()) {
					Turno turno = (Turno) itEstaciones.next();
					if(turno.getAnulado()== null || !turno.getAnulado().equals(CTurno.TURNO_ANULADO)){
						out.write("<tr>");
						out.write("<td><label>");
						if (primerRegistro) {
							out.write("<input type=\"radio\" name=\"" + AWTurno.ID_TURNO + "\" value=\"" + turno.getIdWorkflow() + "\" checked>");
							primerRegistro = false;
						} else {
							out.write("<input type=\"radio\" name=\"" + AWTurno.ID_TURNO + "\" value=\"" + turno.getIdWorkflow() + "\">");
						}
						out.write("</label></td>");
						out.write("<td>");
						out.write(turno.getIdWorkflow());
						out.write("</td>");
						out.write("</tr>");
					}
				}

				out.write("</table>");
				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><input name=\"imageField\" id=\"imageField_id\" onClick = \"javascript:deshabilitarBoton()\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_aceptar.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
				out.write("</tr>");				
				out.write("</table>");
				
				
				if(esCertificadosAsociados){
					
					out.write("<script>\n");
					out.write("function consultarRelacion(){\n");
					out.write("		document.Turnos."+ WebKeys.ACCION +".value = '"+AWTurno.CONSULTAR_RELACION+"'\n");
					out.write("		document.Turnos.submit();");
					out.write("}\n");	
					out.write("</script>\n");				
					
					List turnosRelacion = (List)request.getSession().getAttribute(WebKeys.LISTA_TURNOS_RELACION);
					
					Log.getInstance().debug(TurnosHelper.class,"TURNOS RELACION ES :" +(turnosRelacion!=null?""+turnosRelacion.size():"NULL"));
					out.write("<table width=\"100%\" class=\"camposform\">");

					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");				
					out.write("<td  class=\"titulotbcentral\" ><B>Consultar relación</B></td>");
					out.write("</tr>");					
									
					out.write("<tr>");
					out.write("<td align=\"center\" colspan=\"2\">");
					try {
						TextHelper textHelper= new TextHelper();	
						textHelper.setNombre(AWRelacion.ID_RELACION);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWRelacion.ID_RELACION);
						textHelper.setSize("20");
						textHelper.render(request,out);
					}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
					}
					out.write("</td>");
					out.write("</tr>");
		
					out.write("<tr>");
					out.write("<td align=\"center\" colspan=\"2\" ><a href=\"javascript:consultarRelacion()\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_consultar.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");
					out.write("</tr>");				
					out.write("</table>");
					
					if(turnosRelacion!=null){
					
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td height=\"17\" class=\"titulotbcentral\">Seleccione un Turno</td>");
						out.write("</tr>");
						
						Iterator itTurnosCertificados = turnosRelacion.iterator();
						while (itTurnosCertificados.hasNext()) {
							Turno turno = (Turno) itTurnosCertificados.next();
							out.write("<tr>");
							out.write("<td colspan=\"2\">");
							out.write("<input type=\"checkbox\" name=\"" + AWTurno.ID_TURNO + "\" value=\"" + turno.getIdTurno() + "\">");								
							out.write(turno.getIdWorkflow());
							out.write("</td>");
							out.write("</tr>");
						}

						out.write("<tr>");
						out.write("<td colspan=\"2\" align=\"center\"><hr class=\"camposform\"></td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td colspan=\"2\" align=\"center\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_aceptar.gif\" width=\"139\" height=\"21\" border=\"0\"></td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td colspan=\"2\" align=\"center\"><hr class=\"camposform\"></td>");
						out.write("</tr>");				
						out.write("</table>");						
						
					}
					
					
				}

				
				out.write("</form>");
			}
		}

	}
        
        /**
         * @author : Edgar Lora
         * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         */
        public boolean isEnHorarioRepartoNotarial() {
            return enHorarioRepartoNotarial;
        }

        public void setEnHorarioRepartoNotarial(boolean enHorarioRepartoNotarial) {
            this.enHorarioRepartoNotarial = enHorarioRepartoNotarial;
        }
}
