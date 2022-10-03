package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWFolio;
import org.auriga.util.FechaConFormato;

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
 * @author jfrias, ppabon
*/
public class AnotacionesEnglobeHelper extends Helper {
	private List anotaciones;
	private List anotacionesSeleccionadas;
	
	/**
	 * 
	 */
	public AnotacionesEnglobeHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			out.write("<input type=\"hidden\" name=\""+AWFolio.POSICION+"\" id=\""+AWFolio.POSICION+"\">");
			
			if(!anotaciones.isEmpty()){
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");
				out.write("<td colspan=\"4\">Anotaci&oacute;n</td>");
				out.write("<td colspan=\"3\">Documento</td>");
				out.write("<td colspan=\"3\">Naturaleza jur&iacute;dica</td>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");
				out.write("<td>Id</td>");
				out.write("<td># radicaci&oacute;n</td>");
				out.write("<td>&nbsp;</td>");
				out.write("<td>&nbsp;</td>");
				out.write("<td>Tipo</td>");
				out.write("<td>N&uacute;mero</td>");
				out.write("<td>Fecha</td>");
				out.write("<td>Id</td>");
				out.write("<td>Naturaleza</td>");
				out.write("<td>Valor</td>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");				
				Iterator itAnotaciones = anotaciones.iterator();
				int i = 0;
				String[] preservar=(String[])request.getSession().getAttribute("Anotaciones");
				while(itAnotaciones.hasNext()){
					Anotacion anotacion = (Anotacion) itAnotaciones.next();
					if (preservar!=null && existe(anotacion.getOrden(),preservar)){
					out.write("<tr>");
					out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" id=\"Anotaciones"+i+"\" value=\""+anotacion.getOrden()+"\" checked></td>");
					out.write("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
					out.write("<td class=\"campositem\">" + ((anotacion.getNumRadicacion() != null) ? anotacion.getNumRadicacion() : "&nbsp;") + " </td>");
					
					Documento documento = anotacion.getDocumento();					
					TipoDocumento tipoDoc = null;
					String tipoDocumento = null;
					String numDocumento = null;
					String fechaDocumento = null;  
					String idEstado="";
					
					if(documento!=null && documento.getFecha()!=null){
						fechaDocumento = FechaConFormato.formatear( documento.getFecha(), "dd/MM/yyyy");
					}
					
					if(documento!=null && documento.getNumero()!=null){
						numDocumento = documento.getNumero();
					}					
					
					if(documento!=null&&documento.getTipoDocumento()!=null){						
						 tipoDoc = documento.getTipoDocumento();
						 
						 if(tipoDoc!=null && tipoDoc.getIdTipoDocumento()!=null){
						 	tipoDocumento = tipoDoc.getIdTipoDocumento();
						 }
						 
						if(tipoDoc!=null && tipoDoc.getNombre()!=null){
						   tipoDocumento = tipoDoc.getNombre();
						}	
											 
					}
					if(anotacion.getEstado() != null){
						
						idEstado=anotacion.getEstado().getIdEstadoAn();
						
					}
					
					out.newLine();
					out.write("<td class=\"campositem\">" + idEstado + " </td>");
					out.newLine();
					out.write("<td class=\"campositem\">" + ((anotacion.isTemporal())?"T":"D")  + " </td>");					
					out.write("<td class=\"campositem\">" + ((tipoDocumento!= null) ? tipoDocumento : "&nbsp;") + " </td>");
					out.write("<td class=\"campositem\">" + ((numDocumento != null) ? numDocumento : "&nbsp;") + " </td>");
					out.write("<td class=\"campositem\">" + ((fechaDocumento != null) ? fechaDocumento : "&nbsp;") + " </td>");					
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
					
					
					out.write("<td class=\"campositem\" align=\"right\">" + (anotacion.getValor()!=0?(NumberFormat.getInstance().format(anotacion.getValor())):"&nbsp;")  + " </td>");							
					out.write("<td width=\"40\"><img onClick=\"verAnotacion('ver.anotacion.view?" + AWFolio.POSICION + "="+i+"','Anotacion','width=900,height=450,scrollbars=yes','"+i+"')\" src=\""+ request.getContextPath()+ "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" alt=\"Ver Anotación\" style=\"cursor:hand\" ></td>");
					out.write("</tr>");
					}
					else{
						String naturaleza = null;
						if(anotacion.getNaturalezaJuridica() != null){
							naturaleza = anotacion.getNaturalezaJuridica().getNombre();
							if(naturaleza!=null && naturaleza.length()>45){
								naturaleza = naturaleza.substring(0,45);
							}
						}
												
						out.write("<tr>");

						if(anotacionesSeleccionadas!=null){
							if(anotacionesSeleccionadas.contains(anotacion.getIdAnotacion())){	
								out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" id=\"Anotaciones"+i+"\" checked value=\""+anotacion.getOrden()+"\"></td>");
							}else{
								out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" id=\"Anotaciones"+i+"\"  value=\""+anotacion.getOrden()+"\"></td>");
							}
						}else{
							//DESCOMENTAREAR LAS SIGUIENTES LÍNEAS SI SE DESEA QUE POR DEFECTO QUEDEN SELECCIONADAS 
							//LAS ANOTACIONES QUE TIENEN MEDIDAS CAUTELARES O GRAVÁMENES.									
							/*if(anotacion!=null && 
							   anotacion.getNaturalezaJuridica() != null &&
							   anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica()!=null &&
							   anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica()!=null &&
							   (anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.GRAVAMEN) || anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR))){
								//ESTA LINEA OBLIGABA A QUE NO SE PUDIERAN DESSELECCIONAR LAS ANOTACIONES A HEREDAR
								//out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" value=\""+anotacion.getOrden()+"\" id=\"Anotaciones"+i+"\"  checked  onClick=\"javascript:document.getElementById(this.id).checked=true\"></td>");							
								out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" value=\""+anotacion.getOrden()+"\" id=\"Anotaciones"+i+"\"  checked  ></td>");	
							}else{	
							*/					
								out.write("<td><input type=\"checkbox\" name=\"Anotaciones\" id=\"Anotaciones"+i+"\"  value=\""+anotacion.getOrden()+"\"></td>");
							/*}*/								
						}

																		
						out.write("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
						out.write("<td class=\"campositem\">" + ((anotacion.getNumRadicacion() != null) ? anotacion.getNumRadicacion() : "&nbsp;") + " </td>");
						
						Documento documento = anotacion.getDocumento();					
						TipoDocumento tipoDoc = null;
						String tipoDocumento = null;
						String numDocumento = null;
						String fechaDocumento = null;  
						String idEstado = null;
					
						if(documento!=null && documento.getFecha()!=null){
							fechaDocumento = FechaConFormato.formatear( documento.getFecha(), "dd/MM/yyyy");
						}
					
						if(documento!=null && documento.getNumero()!=null){
							numDocumento = documento.getNumero();
						}					
					
						if(documento!=null&&documento.getTipoDocumento()!=null){						
							 tipoDoc = documento.getTipoDocumento();
						 
							 if(tipoDoc!=null && tipoDoc.getIdTipoDocumento()!=null){
								tipoDocumento = tipoDoc.getIdTipoDocumento();
							 }
						 
							if(tipoDoc!=null && tipoDoc.getNombre()!=null){
							   tipoDocumento = tipoDoc.getNombre();
							}	
											 
						}
						if(anotacion.getEstado() != null){
							
							idEstado=anotacion.getEstado().getIdEstadoAn();
							
						}
						
						out.newLine();
						out.write("<td class=\"campositem\">" + idEstado + " </td>");
						out.newLine();
						out.write("<td class=\"campositem\">" + ((anotacion.isTemporal())?"T":"D")  + " </td>");
						out.write("<td class=\"campositem\">" + ((tipoDocumento!= null) ? tipoDocumento : "&nbsp;") + " </td>");
						out.write("<td class=\"campositem\">" + ((numDocumento != null) ? numDocumento : "&nbsp;") + " </td>");
						out.write("<td class=\"campositem\">" + ((fechaDocumento != null) ? fechaDocumento : "&nbsp;") + " </td>");						
						
						out.write("<td class=\"campositem\">" + ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "&nbsp;") + " </td>");
						out.write("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
						out.write("<td class=\"campositem\" align=\"right\">" + (anotacion.getValor()!=0?(NumberFormat.getInstance().format(anotacion.getValor())):"&nbsp;") + " </td>");							
						out.write("<td width=\"40\"><img onClick=\"verAnotacion('ver.anotacion.view?" + AWFolio.POSICION + "="+i+"','Anotacion','width=900,height=450,scrollbars=yes','"+i+"')\" src=\""+ request.getContextPath()+ "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" alt=\"Ver Anotación\" style=\"cursor:hand\" ></td>");
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
	private boolean existe(String id, String[] preservar) {
		for (int i=0; i<preservar.length; i++){
			String item=preservar[i];
			if (item.equals(id)){
				return true;
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
			anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
			if(anotaciones==null){
				anotaciones = new ArrayList();
			}
			
	}

	/**
	 * Se llena una lista con las anotaciones quedeberían estar seleccionadas por defectos, esta lista debe contener 
	 * objetos tipo String con el orden de la anotación que debe estar seleccionada por defecto. 
	 * @param list
	 */
	public void setAnotacionesSeleccionadas(List list) {
		anotacionesSeleccionadas = list;
	}

}
