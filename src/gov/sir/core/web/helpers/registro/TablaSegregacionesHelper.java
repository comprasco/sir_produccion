package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Folio;
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

import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.WebFolioDerivado;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.acciones.registro.AWSegregacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

/**
 * @author mmunoz
 */
public class TablaSegregacionesHelper extends Helper{
	
	
	private List foliosDerivados;
	
	
	private boolean esVerificacion = false;
	
	
	private ListaElementoHelper helperUnidad = new ListaElementoHelper();
	
	/**Lista donde se guardan las anotaciones ya creadas*/
	private List anotaciones;
	
	private boolean mostrarMatriculasGeneradas = false;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
			
		HttpSession session = request.getSession();

		WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
		if (webSegregacion == null) {
			webSegregacion = new WebSegregacion();
		}
		foliosDerivados = webSegregacion.getFoliosDerivados();

		if(foliosDerivados == null){
			foliosDerivados = new Vector();
		}
		List unidadesMedida = (List)session.getServletContext().getAttribute(WebKeys.LISTA_UNIDADES_MEDIDA);
		if(unidadesMedida == null){
			unidadesMedida = new Vector();
		}
		helperUnidad.setCssClase("camposformtext");
		helperUnidad.setId(CFolioDerivado.UNIDAD_MEDIDA);
		helperUnidad.setNombre(CFolioDerivado.UNIDAD_MEDIDA);
		Iterator itUnidadesM = unidadesMedida.iterator();
		List nUnidades = new Vector();
		while(itUnidadesM.hasNext()){
			OPLookupCodes code = (OPLookupCodes)itUnidadesM.next();
			nUnidades.add(new ElementoLista(code.getCodigo(),code.getValor()));
		}
		helperUnidad.setTipos(nUnidades);
		helperUnidad.setSelected("M2");
		
		anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_SEGREGACION);
		if(anotaciones == null){
			anotaciones = new Vector();
		}
	}
	
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {
            
                        TextHelper areasHelper = new TextHelper();
                        TextHelper textHelper = new TextHelper();
                        areasHelper.setCssClase("camposformtext");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);
                        /* 
                        * Autor: Cristian David Garcia
                        * Requerimiento: Catastro Nuevos Campos
                        * Adicionar Area Privada, Area Construida y Determinación del Inmueble al realizar la segregacion
                        */
                        ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
                        List determInm = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
                        if(determInm == null){
                            determInm = new ArrayList();
                        }
                        tipoDetermInmHelper.setTipos(determInm);
                        tipoDetermInmHelper.setCssClase("camposformtext");
                                                
                        
			if(!foliosDerivados.isEmpty()){
				out.write("<script>\n");
                                
                                out.write("	   function editarFolio(text){\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ WebKeys.ACCION +".value='"+AWSegregacion.CONSULTAR_NUEVO_FOLIO+"';\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ CFolio.ID_MATRICULA +".value = text;\n");
				out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");				
				out.write("	   }\n");

				out.write("	   function editarFolioDerivado(text){\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ WebKeys.ACCION +".value='"+AWSegregacion.EDITAR_FOLIO_DERIVADO+"';\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ WebKeys.POSICION +".value = text;\n");
				out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");
				out.write("	   }\n");

				out.write("	   function eliminarFolioDerivado(text){\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ WebKeys.ACCION +".value='"+AWSegregacion.ELIMINAR_FOLIO_DERIVADO+"';\n");
				out.write("	       document.all.EDITAR_SEGREGACION."+ WebKeys.POSICION +".value = text;\n");
				out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");
				out.write("	   }\n");
				out.write("</script>\n");
				
				out.write("<br>");				
				out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
				out.write("<tr>");
				out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
				
				out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Folios Agregados</td>");	
				
				out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_matriculas.gif\" width=\"16\" height=\"21\"></td>");
				out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_desenglobar.gif\" width=\"16\" height=\"21\"></td>");
				out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
				out.write("</tr>");
				out.write("</table>");
				
				out.write("<form method=\"post\" action=\"segregacion.do\" name=\"EDITAR_SEGREGACION\">");
				out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"\">");
				out.write("<input type=\"hidden\" name=\"" + WebKeys.POSICION + "\" id=\"" + WebKeys.POSICION + "\" value=\"\">");
				out.write("<input type=\"hidden\" name=\"" + CFolio.ID_MATRICULA + "\" id=\"" + CFolio.ID_MATRICULA + "\" value=\"\">");

				
				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>Nro.</td>");				
				out.write("<td>Inmueble</td>");
				out.write("<td colspan=\"6\" align=\"center\"> Area (Seleccione las unidades de medida del predio)</td>");
                                out.write("<td width=\"150\">Coeficiente %</td>");				
                                out.write("<td width=\"40\">&nbsp;</td>");
				out.write("</tr>");
				int i = 0;

				Iterator itFolio = foliosDerivados.iterator();
				while(itFolio.hasNext()){
					WebFolioDerivado folio = (WebFolioDerivado)itFolio.next();
					String area = "";
                                        String hectareas = "";
                                        String metros = "";
                                        String centimetros = "";
                                        String privMetros = "";
                                        String privCentimetros = "";
                                        String consMetros = "";
                                        String consCentimetros = "";
                                        String determinaInm = WebKeys.SIN_SELECCIONAR;
					String lote = "";
					String porcentaje = "";
					if(folio.getHectareas() != null || folio.getMetros() != null || folio.getCentimetros() != null){
					if(folio.getHectareas() != null){
						hectareas = folio.getHectareas();
					}
					if(folio.getMetros() != null){
						metros = folio.getMetros();
					}
					if(folio.getCentimetros() != null){
						centimetros = folio.getCentimetros();
					}
                                        }
                                        
                                        if(folio.getPrivMetros() != null){
                                                privMetros = folio.getPrivMetros();
                                        }
                                        
                                        if(folio.getPrivCentimetros() != null){
                                                privCentimetros = folio.getPrivCentimetros();
                                        }
                                        
                                        if(folio.getConsMetros() != null){
                                                consMetros = folio.getConsMetros();
                                        }
                                        
                                        if(folio.getConsCentimetros() != null){
                                                consCentimetros = folio.getConsCentimetros();
                                        }
                                        
                                        if(folio.getDeterminaInm() != null){
                                                determinaInm = folio.getDeterminaInm();
                                        }
                                        
					if(folio.getInmueble()!=null){
						lote = folio.getInmueble();	
					
					}
					if(folio.getPorcentaje() != null){
						porcentaje = folio.getPorcentaje();
					}
					out.write("<tr>");
					
					TextHelper helper = new TextHelper();
					helper.setCssClase("camposformtext");
					HttpSession sesion = request.getSession();						
					out.write("<td width=\"20\">&nbsp;</td>");
					out.write("<td class=\"campositem\">" + (i + 1) + "&nbsp;</td>");					
					out.write("<td>");
					
					sesion.setAttribute(CFolioDerivado.NOMBRE_LOTE + i ,lote);
					helper.setId(CFolioDerivado.NOMBRE_LOTE + i);
					helper.setNombre(CFolioDerivado.NOMBRE_LOTE + i);
					helper.setSize("20");
					helper.render(request,out);
											
					out.write("&nbsp;</td>");
					 out.write("<td>");
                                out.write("Hect&aacutereas");
                                out.write("</td>");
                                
				out.write("<td>");

				sesion.setAttribute(CFolioDerivado.HECTAREAS+ i , hectareas);
				areasHelper.setId(CFolioDerivado.HECTAREAS + i);
				areasHelper.setNombre(CFolioDerivado.HECTAREAS + i);
				areasHelper.setSize("20");
                                areasHelper.setMaxlength("20");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.HECTAREAS+ i+"');\"  "
                                        + "onChange=\"valideDot('"+CFolioDerivado.HECTAREAS+ i+"')\"  "
                                        + "onBlur=\"hectareasFormatter('"+CFolioDerivado.HECTAREAS+ i+"','"+CFolioDerivado.METROS+ i+"','"+CFolioDerivado.CENTIMETROS + i+"')\"  ");
				areasHelper.render(request,out);

				out.write("&nbsp;</td>");
                                
                                out.write("<td>");
                                out.write("Metros<sup>2</sup>");
                                out.write("</td>");
                        
				out.write("<td>");

				sesion.setAttribute(CFolioDerivado.METROS+ i , metros);
				areasHelper.setId(CFolioDerivado.METROS + i);
				areasHelper.setNombre(CFolioDerivado.METROS + i);
				areasHelper.setSize("20");
                                areasHelper.setMaxlength("20");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.METROS+ i+"');\"  "
                                        + "onChange=\"valideDot('"+CFolioDerivado.METROS+ i+"')\"  "
                                        + "onBlur=\"metrosFormatter('"+CFolioDerivado.HECTAREAS+ i+"','"+CFolioDerivado.METROS+ i+"','"+CFolioDerivado.CENTIMETROS + i+"')\"  ");
				areasHelper.render(request,out);

				out.write("&nbsp;</td>");
                                
                                out.write("<td>");
                                out.write("Centimetros<sup>2</sup>");
                                out.write("</td>");
                                
				out.write("<td>");

				sesion.setAttribute(CFolioDerivado.CENTIMETROS + i , centimetros);
				areasHelper.setId(CFolioDerivado.CENTIMETROS + i);
				areasHelper.setNombre(CFolioDerivado.CENTIMETROS + i);
				areasHelper.setSize("20");
                                areasHelper.setMaxlength("4");
                                areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
				areasHelper.render(request,out);

				out.write("&nbsp;</td>");
					out.write("<td>");

					sesion.setAttribute(CFolioDerivado.PORCENTAJE + i , porcentaje);
					helper.setId(CFolioDerivado.PORCENTAJE + i);
					helper.setNombre(CFolioDerivado.PORCENTAJE + i);
					helper.setSize("20");
					helper.render(request,out);

					out.write("&nbsp;</td>");
					


					out.write("<td><a href=\"#\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_guardar.gif\" width=\"35\" alt=\"Validar y Editar Folio Derivado\" height=\"13\"  border=\"0\" onclick=\"javascript:editarFolioDerivado('"+(""+i)+"');\"></a></td>");
					out.write("<td><a href=\"#\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" alt=\"Quitar Folio Derivado\" height=\"13\"  border=\"0\" onclick=\"javascript:eliminarFolioDerivado('"+(""+i)+"');\"></a></td>");
						
					out.write("</tr>");
                                        
                                        out.println("<tr>");
                                        out.println("<td colspan=\"10\">");
                                        out.println("<table width=\"100%\" class=\"camposformdark\">");
                                        out.println("<tr>");
                                        out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Area Privada</td>");
                                        out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Area Construida</td>");
                                        out.println("<td nowrap> Determinaci&oacute;n del Inmueble </td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                        out.println("<td>");
                                        out.println("<table class=\"camposformdnoborder\">");
                                        out.println("<tr>");
                                        out.println("<td> &nbsp;Metros<sup>2</sup></td>");
                                        out.println("<td>");
                                        sesion.setAttribute(CFolio.PRIVMETROS + i , privMetros);
                                        areasHelper.setId(CFolio.PRIVMETROS + i);
                                        areasHelper.setNombre(CFolio.PRIVMETROS + i);
                                        areasHelper.setSize("20");
                                        areasHelper.setMaxlength("20");
                                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.PRIVMETROS+i+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.PRIVMETROS+i+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.PRIVMETROS+i+"','"+CFolio.PRIVCENTIMETROS+i+"')\" ");
                                        areasHelper.render(request, out);
                                        out.println("</td>");
                                        out.println("<td> &nbsp;Centimetros<sup>2</sup></td>");
                                        out.println("<td>");
                                        sesion.setAttribute(CFolio.PRIVCENTIMETROS + i , privCentimetros);
                                        areasHelper.setId(CFolio.PRIVCENTIMETROS + i);
                                        areasHelper.setNombre(CFolio.PRIVCENTIMETROS + i);
                                        areasHelper.setSize("20");
                                        areasHelper.setMaxlength("4");
                                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                        areasHelper.render(request, out);
                                        out.println("</td>");
                                        out.println("</tr>");
                                        out.println("</table>");
                                        out.println("</td>");
                                        out.println("<td>");
                                        out.println("<table class=\"camposformdnoborder\">");
                                        out.println("<tr>");
                                        out.println("<td> Metros<sup>2</sup> </td>");
                                        out.println("<td>");
                                        sesion.setAttribute(CFolio.CONSMETROS + i , consMetros);
                                        areasHelper.setId(CFolio.CONSMETROS + i);
                                        areasHelper.setNombre(CFolio.CONSMETROS + i);
                                        areasHelper.setSize("20");
                                        areasHelper.setMaxlength("20");
                                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.CONSMETROS+ i+"');\" "
                                        + "onChange=\"valideDot('"+CFolio.CONSMETROS+ i+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.CONSMETROS+ i+"','"+CFolio.CONSCENTIMETROS+ i+"')\"  ");
                                        areasHelper.render(request, out);
                                        out.println("</td>");
                                        out.println("<td>&nbsp; Centimetros<sup>2</sup> </td>");
                                        out.println("<td>");
                                        sesion.setAttribute(CFolio.CONSCENTIMETROS + i , consCentimetros);
                                        areasHelper.setId(CFolio.CONSCENTIMETROS + i);
                                        areasHelper.setNombre(CFolio.CONSCENTIMETROS + i);
                                        areasHelper.setSize("20");
                                        areasHelper.setMaxlength("4");
                                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                        areasHelper.render(request, out);
                                        out.println("</td>");
                                        out.println("</tr>");
                                        out.println("</table>");
                                        out.println("</td>");
                                        out.println("<td>");
                                        try {
                                        sesion.setAttribute(CFolio.DETERMINACION_INMUEBLE + i , determinaInm);
                                        tipoDetermInmHelper.setNombre(CFolio.DETERMINACION_INMUEBLE + i);
                                        tipoDetermInmHelper.setId(CFolio.DETERMINACION_INMUEBLE + i);
                                        tipoDetermInmHelper.setSelected(determinaInm);
                                        tipoDetermInmHelper.render(request, out);
                                        } catch (HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                         }
                                        out.println("</td>");
                                        out.println("</tr>");
                                        out.println("</table>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                        
					i++;
				}
				out.write("</table>");
				out.write("</form>");					
			}
			if(!esVerificacion){
				TextHelper helper = new TextHelper();
			out.write("<form action=\"segregacion.do\" method=\"post\" name=\"SEGREGACION\" id=\"SEGREGACION\">");
			out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"" + AWCalificacion.AGREGAR_FOLIO_DERIVADO + "\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<tr>");
			out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Agregar Datos </td>");
			out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_matriculas.gif\" width=\"16\" height=\"21\"></td>");
			out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_desenglobar.gif\" width=\"16\" height=\"21\"></td>");
			out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Inmueble</td>");
			out.write("<td colspan=\"6\" align=\"center\"> Area (Seleccione las unidades de medida del predio)</td>");
			out.write("<td width=\"150\">Coeficiente %</td>");
			out.write("<td  align=\"center\">Agregar Segregación </td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td width=\"20\">&nbsp;</td>");
			// Nombre Lote
			out.write("<td>");
			helper.setCssClase("camposformtext");
			helper.setId(CFolioDerivado.NOMBRE_LOTE);
			helper.setNombre(CFolioDerivado.NOMBRE_LOTE);
			helper.render(request, out);
			out.write("</td>");
			// Hectareas
			out.write("<td>");
			out.write("Hect&aacutereas");
			out.write("</td>");
			out.write("<td>");
			areasHelper.setId(CFolioDerivado.HECTAREAS);
			areasHelper.setNombre(CFolioDerivado.HECTAREAS);
			areasHelper.setSize("20");
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.HECTAREAS+"');\"  "
                                + "onChange=\"valideDot('"+CFolioDerivado.HECTAREAS+"')\"  "
                                + "onBlur=\"hectareasFormatter('"+CFolioDerivado.HECTAREAS+"','"+CFolioDerivado.METROS+"','"+CFolioDerivado.CENTIMETROS+"')\"");
			areasHelper.render(request, out);
			out.write("</td>");
			// Metros
			out.write("<td>");
			out.write("Metros<sup>2</sup>");
			out.write("</td>");
			out.write("<td>");
			areasHelper.setId(CFolioDerivado.METROS);
			areasHelper.setNombre(CFolioDerivado.METROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.METROS+"');\"  "
                                + "onChange=\"valideDot('"+CFolioDerivado.METROS+"')\"  "
                                + "onBlur=\"metrosFormatter('"+CFolioDerivado.HECTAREAS+"','"+CFolioDerivado.METROS+"','"+CFolioDerivado.CENTIMETROS+"')\" ");
			areasHelper.render(request, out);
			out.write("</td>");
			// Centimetros
			out.write("<td>");
			out.write("Centimetros<sup>2</sup>");
			out.write("</td>");
			out.write("<td>");
			areasHelper.setId(CFolioDerivado.CENTIMETROS);
			areasHelper.setNombre(CFolioDerivado.CENTIMETROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\"");
			areasHelper.render(request, out);
			out.write("</td>");
			// Porcentaje
			out.write("<td>");
			helper.setId(CFolioDerivado.PORCENTAJE);
			helper.setNombre(CFolioDerivado.PORCENTAJE);
			helper.setSize("20");
                        helper.setMaxlength("20");
			helper.render(request, out);
			out.write("</td>");
			out.write("<td align=\"center\" ><input name=\"imageField2\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" alt=\"Agregar Folio Derivado\" border=\"0\"></td>");
			out.write("</tr>");
			out.write("</table>");
                        
                        out.println("<table width=\"100%\" class=\"camposform\">");
                        out.println("<tr>");
                        out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                        out.println("<td> Area Privada </td>");
                        out.println("<td nowrap> Area Construida</td>");
                        out.println("<td> Determinaci&oacute;n del Inmueble </td>");
                        out.println("</tr>");
                        
                        out.println("<tr>");
                        out.println("<td width=\"20\">&nbsp;</td>");
                        out.println("<td>");
                        out.println("<table class=\"camposformnoborder\">");
                        out.println("<tr>");
                        out.println("<td> Metros<sup>2</sup> </td>");
                        out.println("<td>");
                        areasHelper.setId(CFolio.PRIVMETROS);
			areasHelper.setNombre(CFolio.PRIVMETROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.PRIVMETROS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.PRIVMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.PRIVMETROS+"','"+CFolio.PRIVCENTIMETROS+"')\"  ");
			areasHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td> Centimetros<sup>2</sup> </td>");
                        out.println("<td>");
                        areasHelper.setId(CFolio.PRIVCENTIMETROS);
			areasHelper.setNombre(CFolio.PRIVCENTIMETROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
			areasHelper.render(request, out);
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<table class=\"camposformnoborder\">");
                        out.println("<tr>");
                        out.println("<td> Metros<sup>2</sup> </td>");
                        out.println("<td>");
                        areasHelper.setId(CFolio.CONSMETROS);
			areasHelper.setNombre(CFolio.CONSMETROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.CONSMETROS+"');\" "
                                        + "onChange=\"valideDot('"+CFolio.CONSMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.CONSMETROS+"','"+CFolio.CONSCENTIMETROS+"')\"  ");
			areasHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td> Centimetros<sup>2</sup> </td>");
                        out.println("<td>");
                        areasHelper.setId(CFolio.CONSCENTIMETROS);
			areasHelper.setNombre(CFolio.CONSCENTIMETROS);
			areasHelper.setSize("20");
			areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
			areasHelper.render(request, out);
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        out.println("<td>");
                        try{
                        tipoDetermInmHelper.setNombre(CFolio.DETERMINACION_INMUEBLE);
                        tipoDetermInmHelper.setId(CFolio.DETERMINACION_INMUEBLE);
                        tipoDetermInmHelper.render(request, out);
                        } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                         }
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        
                        out.println("<table class=\"camposformdnoborder\">");
                        out.println("<tr>");
                        out.println("<td>");
                        // Nota de Ayuda
			out.write("<table width=\"100%\" class=\"camposformdark\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("Nota de ayuda:");
			out.write("</td>");
			out.write("<td>");
			out.write("&nbsp&nbsp&nbsp&nbsp&nbspTabla de equivalencias");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>");
			out.write("Para el diligenciamiento de los campos de &aacuterea el usuario debe tener en cuenta lo siguiente:");
			out.write("<table class =\"camposformdnoborder\">");
			out.write("<tr>");
			out.write("<td>1. Para el campo hect&aacutereas, puede ingresar la cantidad de d&iacutegitos que requiera.</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>2. Para el campo metros cuadrados, solo permite ingresar cuatro d&iacutegitos.</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>3. Para el campo centimetros cuadrados, solo permite ingresar cuatro digitos d&iacutegitos.<br></td>");
			out.write("</tr>");
                        out.write("<tr>");
			out.write("<td>Para lo anterior se debe tener en cuenta la tabla de equivalencias.</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>Ejemplo: Convertir 50.000 m<sup>2</sup> a hect&aacutereas (Rta: 50.000/10.000 = 5 Hect&aacutereas)</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("<td>");
			out.write("<table class=\"camposformwhite\">");
			out.write("<tr align=\"center\">");
			out.write("<td>&nbsp<strong>Medida</strong>&nbsp</td>");
			out.write("<td>&nbsp<strong>Equivalencia</strong>&nbsp</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>&nbsp 1 hect&aacuterea &nbsp</td>");
			out.write("<td>&nbsp 10.000 m<sup>2</sup>&nbsp</td>");
			out.write("</tr>");
			out.write("<tr align= \"center\">");
			out.write("<td>&nbsp 1 m<sup>2</sup>&nbsp</td>");
			out.write("<td>&nbsp 10.000 cm<sup>2</sup>&nbsp</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
                        
                        out.println("</td>");
                        
                        
                        Folio folioM = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
                        String hectareasM = "0";
                        String metrosM = "0";
                        String centimetrosM = "0";

                        if(folioM.getHectareas() != null && !folioM.getHectareas().isEmpty()){
                           hectareasM = folioM.getHectareas();
                        }

                        if(folioM.getMetros() != null && !folioM.getMetros().isEmpty()){
                           metrosM = folioM.getMetros();
                        }

                        if(folioM.getCentimetros() != null && !folioM.getCentimetros().isEmpty()){
                           centimetrosM = folioM.getCentimetros();
                        }

                        int mHectareas = Integer.parseInt(hectareasM);
                        int mMetros = Integer.parseInt(metrosM);
                        int mCentimetros = Integer.parseInt(centimetrosM);

                        int TotalHectareas = 0;
                        int TotalMetros = 0;
                        int TotalCentimetros = 0;

                            Iterator itFoliosDe = foliosDerivados.iterator();
                            while (itFoliosDe.hasNext()) {
                                try {
                                    int hectareasPorFolio = 0;
                                    int metrosPorFolio = 0;
                                    int centimetrosPorFolio = 0;
                                    String auxh, auxm, auxc;
                                    try {
                                        WebFolioDerivado dev = (WebFolioDerivado) itFoliosDe.next();
                                        auxh = dev.getHectareas();
                                        auxm = dev.getMetros();
                                        auxc = dev.getCentimetros();
                                        hectareasPorFolio = Integer.parseInt((auxh != null && !auxh.isEmpty() ? auxh : "0"));
                                        metrosPorFolio = Integer.parseInt((auxm != null && !auxm.isEmpty() ? auxm : "0"));
                                        centimetrosPorFolio = Integer.parseInt((auxc != null && !auxc.isEmpty() ? auxc : "0"));
                                    } catch (Exception ee) {
                                        hectareasPorFolio = 0;
                                        metrosPorFolio = 0;
                                        centimetrosPorFolio = 0;
                                    }
                                    TotalHectareas += hectareasPorFolio;
                                    TotalMetros += metrosPorFolio;
                                    TotalCentimetros += centimetrosPorFolio;
                                } catch (NumberFormatException e) {
                                    TotalHectareas = 0;
                                    TotalMetros = 0;
                                    TotalCentimetros = 0;
                                }

                                if (TotalMetros >= 10000) {
                                TotalHectareas += 1;
                                TotalMetros -= 10000;
                            }

                            if (TotalCentimetros >= 10000) {
                                TotalMetros += 1;
                                TotalCentimetros -= 10000;
                            }
                            }
                            
                            if (TotalMetros >= 10000) {
                                TotalHectareas += 1;
                                TotalMetros -= 10000;
                            }
                            
                            String totalHectareas = String.valueOf(TotalHectareas);
                            String totalMetros = String.valueOf(TotalMetros);
                            String totalCentimetros = String.valueOf(TotalCentimetros);

                        
                        
                        // AREA MATRIZ Y AREA TOTAL SEGREGADA 
                        out.println("<td>");
                        out.println("<table class=\"camposformdnoborder\">");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<table class=\"camposformdark\">");
                        out.println("<tr>");
                        out.println("<td align=\"center\" colspan=\"3\"><strong>Area Matriz</strong></td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td> Hectareas </td>");
                        out.println("<td> Metros<sup>2</sup> </td>");
                        out.println("<td> Centimetros<sup>2</sup></td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.HECTAREAS_MATRIZ , hectareasM);
                        textHelper.setId(CFolioDerivado.HECTAREAS_MATRIZ);
			textHelper.setNombre(CFolioDerivado.HECTAREAS_MATRIZ);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.METROS_MATRIZ , metrosM);
                        textHelper.setId(CFolioDerivado.METROS_MATRIZ);
			textHelper.setNombre(CFolioDerivado.METROS_MATRIZ);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.CENTIMETROS_MATRIZ , centimetrosM);
                        textHelper.setId(CFolioDerivado.CENTIMETROS_MATRIZ);
			textHelper.setNombre(CFolioDerivado.CENTIMETROS_MATRIZ);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<table class=\"camposformdark\">");
                        out.println("<tr>");
                        out.println("<td align=\"center\" colspan=\"3\">");
                        out.println("<strong>Area Total Segregada</strong>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td> Hectareas </td>");
                        out.println("<td> Metros<sup>2</sup> </td>");
                        out.println("<td> Centimetros<sup>2</sup></td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.HECTAREAS_TOTAL , totalHectareas);
                        textHelper.setId(CFolioDerivado.HECTAREAS_TOTAL);
			textHelper.setNombre(CFolioDerivado.HECTAREAS_TOTAL);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.METROS_TOTAL , totalMetros);
                        textHelper.setId(CFolioDerivado.METROS_TOTAL);
			textHelper.setNombre(CFolioDerivado.METROS_TOTAL);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("<td>");
                        request.getSession().setAttribute(CFolioDerivado.CENTIMETROS_TOTAL , totalCentimetros);
                        textHelper.setId(CFolioDerivado.CENTIMETROS_TOTAL);
			textHelper.setNombre(CFolioDerivado.CENTIMETROS_TOTAL);
                        textHelper.setSize("15");
			textHelper.render(request, out);
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        
				out.write("</form>");
			}

	}
	/**
	 * @param b
	 */
	public void setEsVerificacion(boolean b) {
		esVerificacion = b;
	}

	/**
	 * @param b
	 */
	public void setMostrarMatriculasGeneradas(boolean b) {
		mostrarMatriculasGeneradas = b;
	}
	
	public boolean fueEditado(String idMatricula, HttpServletRequest request){
		List idsFolios = (List)request.getSession().getAttribute(WebKeys.LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS);
		if(idsFolios == null){
			idsFolios = new ArrayList();
		}
		Iterator itFolios = idsFolios.iterator();
		while(itFolios.hasNext()){
			String idFolio = (String) itFolios.next();
			if(idFolio.equals(idMatricula)){
				return true;
			}
		}
		return false;
	}


	/**
	 * @return
	 */
	private List ArrayList() {
		// TODO Auto-generated method stub
		return null;
	}

}
