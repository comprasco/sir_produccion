package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.web.WebKeys;

/**
 * Clase Helper que permite construir el listado de matriculas en una jsp a partir de una lista de matriculas. Con el fin de
 * colocar un botón de edición y de esta manera poder editar el respectivo folio. 
 * Tiene como parámetros el número total de matriculas en la lista(num), la lista de matriculas (folios) y la acción que 
 * debe ejecutarse al hacer clic en el boton de editar.
 * @author ppabon
 */
public class MatriculaEditHelper extends Helper {
	int num;
	List folios;
	String accion;

	/**
	 * Constructor de la clase.
	 */
	public MatriculaEditHelper() {
		super();
	}

	/**
	 * Constructor de la clase. Con un parámetro que indica la acción que debe ejecutarse al hacer clic en el boton de editar.
	 * @param accion 
	 */
	public MatriculaEditHelper(String accion) {
		super();
		this.accion = accion;
	}

	/** 
	 * Método que puede ser utilizado para inicializar variables. Es ejecutado antes de llamar al método drawGUI().
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	}

	/**
	 * Método que construye en la jsp el listado de matriculas que recibe de la lista de Folios. con el botón de editar.
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		if (num > 0) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td></td><td>Matr&iacute;culas</td><td></td><td align=\"center\">Editar</td>");
			
			gov.sir.core.negocio.modelo.Fase fase = (gov.sir.core.negocio.modelo.Fase)request.getSession().getAttribute(WebKeys.FASE);

			Iterator iterator = folios.iterator();
                        int i=0;
			while (iterator.hasNext()) {
				SolicitudFolio solfolio = (SolicitudFolio) iterator.next();
				Folio folio = solfolio.getFolio();
				
				String matricula = folio.getIdMatricula();							
				String labelMatricula = "";

				if (folio.isDefinitivo()){
					labelMatricula = folio.getIdMatricula();
				}else{
					if(fase==null){
						if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
							labelMatricula = folio.getNombreLote();
						}else{
							labelMatricula = folio.getIdMatricula();
						}   
					}else{
						if(fase.getID().startsWith("ANT_")){
							if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
								labelMatricula=folio.getNombreLote();	
							}										
						}else{
							if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
								labelMatricula=folio.getIdMatricula() + " - " +folio.getNombreLote();	
							}else{
								labelMatricula=folio.getIdMatricula();	
							}										
						}
					}
				}

				/*if(folio.isDefinitivo()){
					labelMatricula = folio.getIdMatricula();	
				}else{
					labelMatricula = folio.getNombreLote();
				}*/

				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td class=\"campositem\">" +  (labelMatricula!=null?labelMatricula:"")  + "</td>");				
				
                                 /*@author : David A Rubio J
                                   @change : Se agrega botón para consultar los detalles del folio mediante ventana emergente con pestañas */
                                   out.println("<td width=\"50\"><a href=\"javascript:verAnotacion('consultar.folio.do?POSICION=" + i + "','Folio','width=900,height=450,scrollbars=yes','0')\"><img name=\"btn_mini_verdetalles.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");  

                                
                                
                                out.write("<td width=\"139\"><a href=\"javascript:cargarFolio('" + (accion != null ? accion : "") + "','" + matricula + "');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_editar_folio.gif\" width=\"139\" height=\"21\" border=\"0\" ></a></td>");		
				
				out.write("</tr>");
                                i++;
			}

			out.write("</table>");
			out.write("<br>");
		}

	}
	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}

	/**
	 * @param list
	 */
	public void setFolios(List list) {
		folios = list;
		if (folios != null) {
			num = folios.size();
		} else {
			num = 0;
		}
	}

	/**
	 * @return
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param string
	 */
	public void setAccion(String string) {
		accion = string;
	}

}
