package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.web.WebKeys;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author mmunoz
 */
public class SeleccionarLocacionHelper extends Helper{

	public static final String LOCACION_ID_DEPARTAMENTO = "LOCACION_ID_DEPARTAMENTO";
	public static final String LOCACION_NOM_DEPARTAMENTO = "LOCACION_NOM_DEPARTAMENTO";
	public static final String LOCACION_ID_MUNICIPIO = "LOCACION_ID_MUNICIPIO";
	public static final String LOCACION_NOM_MUNICIPIO = "LOCACION_NOM_MUNICIPIO";
	public static final String LOCACION_ID_VEREDA = "LOCACION_ID_VEREDA";
	public static final String LOCACION_NOM_VEREDA = "LOCACION_NOM_VEREDA";
	
	private List elementosDeptos;
	private List elementosMunicip;
	private List elementosVeredas;
	private String idDepto;
	private String nombreDepto;
	private String idMunicip;
	private String nombreMunicip;
	private String idVereda;
	private String nombreVereda; 
	private ListaElementoHelper deptosHelper;
	private ListaElementoHelper municHelper;
	private ListaElementoHelper veredasHelper;
	private boolean mostrarboton = false;
	private boolean buscarVereda = false;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		
		elementosDeptos = new Vector();
		elementosMunicip = new Vector();
		elementosVeredas = new Vector();
		
		List deptos = (List) session.getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
		
		Iterator itDeptos = deptos.iterator();
		idDepto = request.getParameter(LOCACION_ID_DEPARTAMENTO);
		idMunicip = request.getParameter(LOCACION_ID_MUNICIPIO);
		idVereda = request.getParameter(LOCACION_ID_VEREDA);
		while(itDeptos.hasNext()){
			Departamento depto = (Departamento) itDeptos.next();
			if(!depto.getIdDepartamento().equals(CDepartamento.DEPARTAMENTO_NO_VISIBLE)){
				elementosDeptos.add(new ElementoLista(depto.getIdDepartamento(),depto.getNombre()));
			}
			if(idDepto != null){
				if(idDepto.equals(depto.getIdDepartamento())){
					 List munics = depto.getMunicipios();
					 nombreDepto = depto.getNombre();
					 session.setAttribute(LOCACION_ID_DEPARTAMENTO,depto.getIdDepartamento());					 
					 Iterator itMunic = munics.iterator();
					 while(itMunic.hasNext()){
					 	Municipio munic = (Municipio) itMunic.next();
					 	elementosMunicip.add(new ElementoLista(munic.getIdMunicipio(),munic.getNombre()));
						if(idMunicip != null){
							if(idMunicip.equals(munic.getIdMunicipio())){
								if (buscarVereda){
									List veredas = munic.getVeredas();
									 nombreMunicip = munic.getNombre();
									 session.setAttribute(LOCACION_ID_MUNICIPIO,munic.getIdMunicipio());
									 Iterator itVeredas = veredas.iterator();
									 while(itVeredas.hasNext()){
										Vereda vereda = (Vereda) itVeredas.next();
										elementosVeredas.add(new ElementoLista(vereda.getIdVereda(),vereda.getNombre()));
										if(idVereda != null){
											if(idVereda.equals(vereda.getIdVereda())){
												nombreVereda = vereda.getNombre();
												session.setAttribute(LOCACION_ID_VEREDA,vereda.getIdVereda());
												mostrarboton = true;
											}
										}
									 }
								}
								else{
									List veredas = munic.getVeredas();
									 nombreMunicip = munic.getNombre();
									 session.setAttribute(LOCACION_ID_MUNICIPIO,munic.getIdMunicipio());
									 Iterator itVeredas = veredas.iterator();
									 Vereda cabecera=null;
									 while(itVeredas.hasNext()){
										Vereda vereda = (Vereda) itVeredas.next();
										if (vereda.isCabecera()){
											cabecera=vereda;
										}
									 }
										if (cabecera!=null){
											idVereda = cabecera.getIdVereda();
											nombreVereda = cabecera.getNombre();
											session.setAttribute(LOCACION_ID_VEREDA,cabecera.getIdVereda());
											mostrarboton = true;
										}else{
											Iterator itVeredas2 = veredas.iterator();
											 while(itVeredas2.hasNext()){
												Vereda vereda = (Vereda) itVeredas2.next();
												elementosVeredas.add(new ElementoLista(vereda.getIdVereda(),vereda.getNombre()));
												if(idVereda != null){
													if(idVereda.equals(vereda.getIdVereda())){
														nombreVereda = vereda.getNombre();
														session.setAttribute(LOCACION_ID_VEREDA,vereda.getIdVereda());
														mostrarboton = true;
													}
												}
											 }
										}
								}
								 
							}
						}
					 }
				}
			}
		}
		
		deptosHelper = new ListaElementoHelper();
		deptosHelper.setCssClase("campoformlista");
		deptosHelper.setId(LOCACION_ID_DEPARTAMENTO);
		deptosHelper.setNombre(LOCACION_ID_DEPARTAMENTO);
		deptosHelper.setTipos(elementosDeptos);
		deptosHelper.setFuncion("onChange=\"submitform();\"");
		
		
		municHelper = new ListaElementoHelper();
		municHelper.setCssClase("campoformlista");
		municHelper.setId(LOCACION_ID_MUNICIPIO);
		municHelper.setNombre(LOCACION_ID_MUNICIPIO);
		municHelper.setFuncion("onChange=\"submitform();\"");
		municHelper.setTipos(elementosMunicip);
		
		
		veredasHelper = new ListaElementoHelper();
		veredasHelper.setCssClase("campoformlista");
		veredasHelper.setId(LOCACION_ID_VEREDA);
		veredasHelper.setNombre(LOCACION_ID_VEREDA);
		veredasHelper.setFuncion("onChange=\"submitform();\"");
		veredasHelper.setTipos(elementosVeredas);
		
		
					
	}
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

	out.write("<input name=\"id_depto_00\" type=\"hidden\" id=\"id_depto_00\" value=\"\">");
	out.write("<input name=\"nom_depto_00\" type=\"hidden\" id=\"nom_depto_00\" value=\"\">");
	out.write("<input name=\"id_munic_00\" type=\"hidden\" id=\"id_munic_00\" value=\"\">");
	out.write("<input name=\"nom_munic_00\" type=\"hidden\" id=\"nom_munic_00\" value=\"\">");
	out.write("<input name=\"id_vereda_00\" type=\"hidden\" id=\"id_vereda_00\" value=\"\">");
	out.write("<input name=\"nom_vereda_00\" type=\"hidden\" id=\"nom_vereda_00\" value=\"\">");
	out.write("<input name=\""+CVereda.MOSTRAR_VEREDA+"\" type=\"hidden\" id=\""+CVereda.MOSTRAR_VEREDA+"\" value=\"\">");	

	
	out.write("<br>");
	out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	out.write("<tr>");
	out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
	out.write("<td class=\"bgnsub\">Selecci&oacute;n Ciudad - Vereda </td>");
	out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_mapcolombia.gif\" width=\"16\" height=\"21\"></td>");
	out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
	out.write("</tr>");
	out.write("</table>");
		
	out.write("<table width=\"100%\" class=\"camposform\" >");
	out.write("<tr>");
	out.write("<td valign=\"top\">");
	out.write("<table width=\"100%\" class=\"contenido\">");
	out.write("<tr>");
	out.write("<td>Departamento</td>");
	out.write("</tr>");
	out.write("<tr>");
	out.write("<td>");
	deptosHelper.render(request,out);
	
	out.write("<input name=\"" + LOCACION_NOM_DEPARTAMENTO+ "\" type=\"hidden\" id=\"" + LOCACION_NOM_DEPARTAMENTO+ "\" value=\"" + nombreDepto + "\">");
	
	out.write("</td>");
	out.write("</tr>");
	out.write("</table></td>");
	out.write("<td valign=\"top\">");
	out.write("<table width=\"100%\" class=\"contenido\">");
	out.write("<tr>");
	out.write("<td>Municipio </td>");
	out.write("</tr>");
	out.write("<tr>");
	out.write("<td>" );
	municHelper.render(request,out);
	out.write("<input name=\"" + LOCACION_NOM_MUNICIPIO+ "\" type=\"hidden\" id=\"" + LOCACION_NOM_MUNICIPIO+ "\" value=\"" + nombreMunicip + "\">");
	out.write("</td>");
	out.write("</tr>");
	out.write("</table>");
	out.write("</td>");
	out.write("<td valign=\"top\">");
	
	if (buscarVereda){
		out.write("<table width=\"100%\" class=\"contenido\">");
		out.write("<tr>");
		out.write("<td>Vereda</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>");
		veredasHelper.render(request,out);
		out.write("<input name=\"" + LOCACION_NOM_VEREDA+ "\" type=\"hidden\" id=\"" + LOCACION_NOM_VEREDA+ "\" value=\"" + nombreVereda + "\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
	
	}else{
		out.write("<input name=\"" + LOCACION_ID_VEREDA+ "\" type=\"hidden\" id=\"" + LOCACION_ID_VEREDA+ "\" value=\"" + idVereda + "\">");
		out.write("<input name=\"" + LOCACION_NOM_VEREDA+ "\" type=\"hidden\" id=\"" + LOCACION_NOM_VEREDA+ "\" value=\"" + nombreVereda + "\">");
	}
	
	out.write("</td>");
	out.write("</tr>");
	out.write("</table>");
	if(mostrarboton){
		out.write("<table width=\"100%\" class=\"camposform\" >");
		out.write("<tr>");
		out.write("<td align=\"center\"><a href=\"javascript: value_formulario(document.formulario."+ LOCACION_ID_DEPARTAMENTO + ".value,'" + nombreDepto+ "',document.formulario." + LOCACION_ID_MUNICIPIO+ ".value,'" + nombreMunicip + "',document.formulario." + LOCACION_ID_VEREDA + ".value,'"+ nombreVereda+"');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_agregar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\"></a></td>");	out.write("</tr>");
		out.write("</table>");
	}
	removerInfo(request);

	}

	/**
	 * @param request
	 */
	private void removerInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(LOCACION_ID_DEPARTAMENTO);
		session.removeAttribute(LOCACION_ID_MUNICIPIO);
		session.removeAttribute(LOCACION_ID_VEREDA);
	}
	/**
	 * @param b
	 */
	public void setBuscarVereda(boolean b) {
		buscarVereda = b;
	}

}
