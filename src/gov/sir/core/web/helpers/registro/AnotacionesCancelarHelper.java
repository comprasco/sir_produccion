/*
 * Created on 30-oct-2004
*/
package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWFolio;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class AnotacionesCancelarHelper extends Helper {
	private List anotaciones;
	int edicion=-1;
	int sel=-1;
	/**
	 * 
	 */
	public AnotacionesCancelarHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			out.write("<input type=\"hidden\" name=\""+AWFolio.POSICION+"\" id=\""+AWFolio.POSICION+"\">");
				if(!anotaciones.isEmpty()){
					Iterator itAnotaciones = anotaciones.iterator();
					int i = 0;
					while(itAnotaciones.hasNext()){
						Anotacion anotacion = (Anotacion) itAnotaciones.next();
						if(i!=edicion){
							out.write("<tr>");
							if(i==sel){
								out.write("<td><input type=\"radio\" name=\"ESCOGER_ANOTACION_CANCELACION\" checked=\"true\" value=\""+anotacion.getOrden()+"\"></td>");
							}else{
								out.write("<td><input type=\"radio\" name=\"ESCOGER_ANOTACION_CANCELACION\" value=\""+anotacion.getOrden()+"\"></td>");
							}
							out.write("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
							out.write("<td class=\"campositem\">" + ((anotacion.getNumRadicacion() != null) ? anotacion.getNumRadicacion() : "&nbsp;") + " </td>");
							out.write("<td class=\"campositem\">" + ((anotacion.getEstado() != null) ? anotacion.getEstado().getNombre() : "&nbsp;") + " </td>");
							out.write("<td class=\"campositem\">" + ((anotacion.isTemporal())?"TEMPORAL":"DEFINITIVA")  + " </td>");
							out.write("<td class=\"campositem\">" + ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "&nbsp;") + " </td>");
							
							
							String naturaleza = null;							

							if(anotacion.getEspecificacion()!=null){
								naturaleza = anotacion.getEspecificacion();
								if(naturaleza!=null && naturaleza.length()>45){
									naturaleza = naturaleza.substring(0,45);
								}
								out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
							}else{
								if(anotacion.getNaturalezaJuridica() != null){
									naturaleza = anotacion.getNaturalezaJuridica().getNombre();
									if(naturaleza!=null && naturaleza.length()>45){
										naturaleza = naturaleza.substring(0,45);
									}
									out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");									
								}
							}
							
							
							out.write("<td class=\"campositem\" align=\"right\">" + (anotacion.getValor()!=0?(NumberFormat.getInstance().format(anotacion.getValor())):"&nbsp;") + " </td>");							
							out.write("<td width=\"40\"><img onClick=\"verAnotacion('ver.anotacion.view?" + AWFolio.POSICION + "="+i+"','Anotacion','width=900,height=450,scrollbars=yes','"+i+"')\" src=\""+ request.getContextPath()+ "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" alt=\"Ver Anotación\" style=\"cursor:hand\" height=\"13\"></td>");
							out.write("</tr>");
						}
						i++;			
					}
				}else{
					out.write("<tr>");
					out.write("<td class=\"campositem\">No hay anotaciones</td>");
					out.write("</tr>");
				}
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
			anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA); 
			if(anotaciones == null){
				anotaciones = new ArrayList();
			}
			request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
	}
	
	public void setEdicion(int b){
		edicion=b;
	}

	public void setSel(int b){
		sel=b;
		sel--;
	}
}
