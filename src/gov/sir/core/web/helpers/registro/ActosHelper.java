package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.comun.TextHelper;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;


/**
 * @author jfrias
 * @author ddiaz
 * @author dsalas
*/
public class ActosHelper extends Helper {
	private int num;
	private boolean mostrarEliminar = true;
	private List actos = null;
	private boolean mostrarImpuesto = true;
	TextHelper textHelper = new TextHelper();

	private NumberFormat formato = NumberFormat.getInstance();
	/**
	 * 
	 */
	public ActosHelper() {
		super();
	}

	/**
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     * Modificado:
     * OSBERT LINERO - Iridium Telecomuinicaciones e Informática Ltda.
     * Incidencia 02295 - Requerimiento 097
     *
     * Se cambiaron las condiciones:
     *  if(this.mostrarEliminar)
     * Por:
     *  if(this.mostrarEliminar || (request.getSession().getAttribute(WebKeys.TURNO_ANTERIOR_OK) != null && acto.getFechaCreacion() == null))
     *
     * Este cambio es para validar cuando sea un turno anterior pero el acto fue agregado,
     * para que sí muestre el boton eliminar en la página en este caso.
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)throws HelperException, IOException {
			
			if(this.actos==null){
				if (num>0){
					if(this.mostrarImpuesto){
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<input type=\"hidden\" name=\"" + CActo.POSICION +
					            "\" id=\"" + CActo.POSICION + "\">");
						out.write("<input type=\"hidden\" name=\"" + CActo.CUANTIA_EDICION +
					            "\" id=\"" + CActo.CUANTIA_EDICION + "\">");
						out.write("<tr>");
						out.write("<td width=\"3%\"><img src=\""+request.getContextPath()+"/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td width=\"3%\">#</td>");
						out.write("<td width=\"5%\" align=\"center\">Id</td>");
						out.write("<td width=\"25%\">Tipo</td>");
						out.write("<td width=\"10%\">Cuant&iacute;a,<br>cantidad o<br>autoavaluo</td>");
						out.write("<td width=\"10%\">Derechos registro</td>");
						out.write("<td width=\"10%\">Impuesto</td>");
						out.write("<td width=\"4%\" align=\"center\">&nbsp;</td>");
						out.write("</tr>");
					}else{
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<input type=\"hidden\" name=\"" + CActo.POSICION +
					            "\" id=\"" + CActo.POSICION + "\">");
						out.write("<input type=\"hidden\" name=\"" + CActo.CUANTIA_EDICION +
					            "\" id=\"" + CActo.CUANTIA_EDICION + "\">");
						out.write("<tr>");
						out.write("<td width=\"3%\"><img src=\""+request.getContextPath()+"/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td width=\"3%\">#</td>");
						out.write("<td width=\"5%\" align=\"center\">Id</td>");
						out.write("<td width=\"25%\">Tipo</td>");
						out.write("<td width=\"15%\">Cuant&iacute;a,<br>cantidad o<br>autoavaluo</td>");
						out.write("<td width=\"15%\">Derechos registro</td>");
						out.write("<td width=\"4%\" align=\"center\">&nbsp;</td>");
						out.write("</tr>");
					}
					for (int i = 0; i < num; i++) {
						Integer camb = new Integer(i);
						Acto acto = (Acto) request.getSession().getAttribute(CActo.ACTO+
												camb.toString());
						out.write("<tr>");
						out.write("<td><img src=\""+request.getContextPath()+"/jsp/images/ico_acto.gif\" width=\"16\" height=\"21\"></td>");
						out.write("<td class=\"campositem\">"+(i+1)+"</td>");
						out.write("<td class=\"campositem\">"+acto.getTipoActo().getIdTipoActo()+"</td>");
						out.write("<td class=\"campositem\">"+acto.getTipoActo().getNombre()+"</td>");
						
						/*Si existe un turno asociado, se debe dejar editar la cuantia*/
						Boolean boolea=(Boolean)request.getSession().getAttribute(WebKeys.TURNO_ANTERIOR_OK);
						if(boolea==null||!boolea.booleanValue())
							out.write("<td class=\"campositem\">"+formato.format(acto.getCuantia())+"</td>");
						else{
							request.getSession().setAttribute("VALOR_ACTO"+i,String.valueOf(formato.format(acto.getCuantia())));
							out.write("					<td>");
							textHelper.setFuncion("");
							textHelper.setNombre("VALOR_ACTO"+i);
							textHelper.setCssClase("camposformtext");
							textHelper.setId("VALOR_ACTO"+i);
							String posicion = camb.toString();
							textHelper.setFuncion("onBlur=\"cargarValorDerechos('CARGAR_VALOR_DERECHOS',"+posicion+",this.value)\"");
							textHelper.setSize("20");
							textHelper.setMaxlength("40");
							textHelper.render(request,out);
							out.write("					</td>");
						}
							out.write("<td class=\"campositem\">"+formato.format(acto.getValor())+"</td>");
						
						if(this.mostrarImpuesto){
							if (acto.isCobroImpuestos()){
								out.write("<td class=\"campositem\">"+formato.format(acto.getValorImpuestos())+"</td>");
							} else{
								out.write("<td class=\"campositem\">NO APLICA</td>");
							}
						}

						if(this.mostrarEliminar || (request.getSession().getAttribute(WebKeys.TURNO_ANTERIOR_OK) != null && acto.getFechaCreacion() == null)){
							out.write("<td align=\"center\"><a href=\"javascript:quitar('"+CActo.ACTO+camb.toString() + "')\"><img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" alt=\"Quitar\" border=\"0\"></a></td>");	
						}
						out.write("</tr>");
					}
					out.write("</table>");
				}				
			}else{
				if(this.mostrarImpuesto){
					out.write("<table width=\"100%\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"3%\"><img src=\""+request.getContextPath()+"/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td width=\"3%\">#</td>");
					out.write("<td width=\"5%\" align=\"center\">Id</td>");
					out.write("<td width=\"25%\">Tipo</td>");
					out.write("<td width=\"10%\">Cuant&iacute;a,<br>cantidad o<br>autoavaluo</td>");
					out.write("<td width=\"10%\">Derechos registro</td>");
					out.write("<td width=\"10%\">Impuesto</td>");
					out.write("<td width=\"4%\" align=\"center\">&nbsp;</td>");
					out.write("</tr>");
				}else{
					out.write("<table width=\"100%\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"3%\"><img src=\""+request.getContextPath()+"/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td width=\"3%\">#</td>");
					out.write("<td width=\"5%\" align=\"center\">Id</td>");
					out.write("<td width=\"25%\">Tipo</td>");
					out.write("<td width=\"15%\">Cuant&iacute;a,<br>cantidad o<br>autoavaluo</td>");
					out.write("<td width=\"15%\">Derechos registro</td>");
					out.write("<td width=\"4%\" align=\"center\">&nbsp;</td>");
					out.write("</tr>");
				}
				
				Iterator it = actos.iterator();
				int i = 0;
				while(it.hasNext()){
					Acto acto = (Acto)it.next(); 

					out.write("<tr>");
					out.write("<td><img src=\""+request.getContextPath()+"/jsp/images/ico_acto.gif\" width=\"16\" height=\"21\"></td>");
					out.write("<td class=\"campositem\">"+(i+1)+"</td>");
					out.write("<td class=\"campositem\">"+acto.getTipoActo().getIdTipoActo()+"</td>");
					out.write("<td class=\"campositem\">"+acto.getTipoActo().getNombre()+"</td>");
					out.write("<td class=\"campositem\">"+formato.format(acto.getCuantia())+"</td>");
					out.write("<td class=\"campositem\">"+formato.format(acto.getValor())+"</td>");
					if(this.mostrarImpuesto){
						if (acto.isCobroImpuestos()){
							out.write("<td class=\"campositem\">"+acto.getValorImpuestos()+"</td>");
						}
						else{
							out.write("<td class=\"campositem\">NO APLICA</td>");
						}
					}
					if(this.mostrarEliminar || (request.getSession().getAttribute(WebKeys.TURNO_ANTERIOR_OK) != null && acto.getFechaCreacion() == null)){
						out.write("<td align=\"center\"><a href=\"javascript:quitar('"+CActo.ACTO+ i + "')\"><img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" alt=\"Quitar\" border=\"0\"></a></td>");	
					}					
					out.write("</tr>");
					i++;					
				}

				out.write("</table>");
				
			}
			
			//request.getSession().setAttribute(CActo.VALOR_ACTO, null);
	}

	/**
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		
		Integer nume = (Integer) request.getSession().getAttribute(CActo.NUM_ACTOS);
		
		if (nume == null) {
			request.getSession().setAttribute(CActo.NUM_ACTOS, new Integer(0));
			num = 0;
		} else {
			num = nume.intValue();
		}
	}

	/**
	 * @param b
	 */
	public void setMostrarEliminar(boolean b) {
		mostrarEliminar = b;
	}

	/**
	 * @param list
	 */
	public void setActos(List list) {
		actos = list;
	}

	/**
	 * @return Returns the mostrarImpuesto.
	 */
	public boolean isMostrarImpuesto() {
		return mostrarImpuesto;
	}
	
	/**
	 * @param mostrarImpuesto The mostrarImpuesto to set.
	 */
	public void setMostrarImpuesto(boolean mostrarImpuesto) {
		this.mostrarImpuesto = mostrarImpuesto;
	}
}
