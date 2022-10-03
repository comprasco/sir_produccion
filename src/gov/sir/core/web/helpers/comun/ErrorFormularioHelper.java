package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.web.acciones.AccionWebException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;

/**
 * @author I.Siglo21
 * Esta clase es la encargada de mostrar la lista de los errores que se
 * han encontrado al momento de llenar una forma
 */
public class ErrorFormularioHelper extends Helper{

	/**
	 * Lista donde se guadan los errores que se han encontrado en un formulario
	 */
	private List errores;

	/**
	* Este método recoje los atributos necesarios del request para poder
	* inicializar los atributos propios de la clase
	* @param request Trae toda la informacion que se a guardado sobre el usuario
	*/
	public void setProperties(HttpServletRequest request){
		Exception exception = (Exception) request.getSession().getAttribute(SMARTKeys.EXCEPCION);
		request.getSession().removeAttribute(SMARTKeys.EXCEPCION);
		request.getSession().setAttribute(WebKeys.HAY_EXCEPCION, new Boolean(true));
		if(exception instanceof EventoException){
			if (exception instanceof gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException){
				errores = ((gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException)exception).getErrores();	
			}else{
				errores = new ArrayList();
				Throwable e = exception.getCause();
				while(e !=null && e.getCause()!=null){
					e = e.getCause();
				}
				errores.add(exception.getMessage() + (e!=null&&e.getMessage()!=null?" : "+e.getMessage():""));
			}
			
		} if(exception instanceof AccionWebException){
			if (exception instanceof ValidacionParametrosException){
				errores = ((ValidacionParametrosException)exception).getErrores();
			}else{
				errores = new ArrayList();
				Throwable e = exception.getCause();
				while(e !=null && e.getCause()!=null){
					e = e.getCause();
				}
				errores.add(exception.getMessage() + (e!=null&&e.getMessage()!=null?" : "+e.getMessage():""));
			}
			
		}
	}
	   
	   
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {

		out.write("<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">");
		Iterator itErrores = errores.iterator();
		while(itErrores.hasNext()){
			out.write("<tr>");
			out.write("<td width=\"20\">&nbsp;</td>"); 
			out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_mal.gif\" width=\"12\" height=\"11\" border=\"0\"></td>");
			out.write("<td class=\"error\">"+itErrores.next()+"</td>");
			out.write("</tr>");			
		}
		out.write("</table>");
	}

}
