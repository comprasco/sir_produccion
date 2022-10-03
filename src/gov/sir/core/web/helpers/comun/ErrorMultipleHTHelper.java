package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;

import gov.sir.core.negocio.acciones.excepciones.CopiaAnotacionHTException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.WebKeys;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author I.Siglo21
 * Esta clase es la encargada de mostrar la lista de los errores que se
 * han encontrado al momento de llenar una forma
 */
public class ErrorMultipleHTHelper extends Helper{
    
    /**
     * Lista donde se guadan los errores que se han encontrado en un formulario
     */
    private Throwable error;
    
	/**
	 * Nombre para colocar en el item cuando se hace referencia a matrículas.
	 */
	private static String OPCION_MATRICULA = "Para la matricula: ";  
	
	/**
	 * Nombre para colocar en el item cuando se hace referencia a matrículas.
	 */
	private static String OPCION_TURNO = "Para el turno: ";  
	
	/**
	 * Nombre para colocar en el item cuando se hace referencia a anotaciones.
	 */
	private static String OPCION_ANOTACION = "Para la anotación: ";
	
	/**
	 * Nombre para colocar el item que presento los errores.
	 */
	private String item = ErrorMultipleHTHelper.OPCION_MATRICULA;
	
	/**
	 * Mensaje encabezado.
	 */
	private String mensajeEncabezado = null;

    
    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request){
        error = (Throwable)request.getSession().getAttribute(SMARTKeys.EXCEPCION);
        
        if(error instanceof CopiaAnotacionHTException){
			request.getSession().setAttribute(WebKeys.EXCEPCION_COPIA_ANOTACION, error);
        }
        
        request.getSession().removeAttribute(SMARTKeys.EXCEPCION);
        request.getSession().setAttribute(WebKeys.HAY_EXCEPCION, new Boolean(true));
        
    }
    
    
    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
    throws IOException, HelperException {
        
        out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
        out.write("<tr>");
        out.write("<td valign=\"top\"> ");
        
		if (error instanceof ValidacionParametrosHTException ){
			ValidacionParametrosHTException vpe = (ValidacionParametrosHTException)error;
			if(vpe.getMensajeFormulario()!=null){
				this.mensajeEncabezado = vpe.getMensajeFormulario(); 
			}
		}
        
        out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
        out.write("<tr>");
        out.write("<td width=\"16\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_ok.gif\" width=\"12\" height=\"11\"></td>");
        if(mensajeEncabezado!=null){
			out.write("<td class=\"errortit\">"+mensajeEncabezado+"</td>");	
        }else{
			out.write("<td class=\"errortit\">Los Siguientes Errores han sido encontrados: </td>");        	
        }
        out.write("</tr>");
        out.write("</table>");
        
        if (error instanceof ValidacionParametrosHTException  && (((ValidacionParametrosHTException)error).getHashErrores().size()>0) ){
            Hashtable tabla = ((ValidacionParametrosHTException)error).getHashErrores();
            Enumeration llaves = tabla.keys();
            while(llaves.hasMoreElements()){
                
                String llave = (String) llaves.nextElement();
                Object obj = tabla.get(llave);
                
                List errores = new ArrayList();
                if(obj instanceof String){
                	errores.add(obj);
                }else{
                	errores = (List)tabla.get(llave);
                }
                
                //Seleccionar la opción si es matrícula o es turno:
                if(llave!=null){
                    StringTokenizer posiciones = new StringTokenizer(llave, "-");
                    if(posiciones.countTokens()==4){
                    	this.item = OPCION_TURNO;
                    }
                }
 
                
                
                Iterator itErrores = errores.iterator();
                
                out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
                out.write("<tr>");
                out.write("<td valign=\"top\">");
                
                out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
                out.write("<tr>");
                out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_ok.gif\" width=\"12\" height=\"11\"></td>");
                out.write("<td class=\"errortit\">"  + this.item + llave + "</td>");
                out.write("<td width=\"20\">&nbsp;</td>");
                out.write("</tr>");
                
                while(itErrores.hasNext()){ 
                    out.write("<tr>");
                    out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_mal.gif\" width=\"12\" height=\"11\" border=\"0\"></td>");
                    out.write("<td class=\"error\">"+itErrores.next()+"</td>");
                    out.write("<td width=\"20\">&nbsp;</td>");
                    out.write("</tr>");
                }
                out.write("</table>");
                
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");                       
            }
            
        }
        else if (error instanceof ValidacionParametrosException && (((ValidacionParametrosException)error).getErrores().size()>0) ){
            List errores = ((ValidacionParametrosException)error).getErrores();
            Iterator itErrores = errores.iterator();
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
            out.write("<tr>");
            out.write("<td valign=\"top\">");
            while(itErrores.hasNext()){
                    out.write("<tr>");
                    out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_mal.gif\" width=\"12\" height=\"11\" border=\"0\"></td>");
                    out.write("<td class=\"error\">"+itErrores.next()+"</td>");
                    out.write("<td width=\"20\">&nbsp;</td>");
                    out.write("</tr>");
                }
                out.write("</table>");
        }else if (error instanceof gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException && (((gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException)error).getErrores().size()>0) ){
            List errores = ((gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException)error).getErrores();
            Iterator itErrores = errores.iterator();
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
            out.write("<tr>");
            out.write("<td valign=\"top\">");
            while(itErrores.hasNext()){
                    out.write("<tr>");
                    out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_mal.gif\" width=\"12\" height=\"11\" border=\"0\"></td>");
                    out.write("<td class=\"error\">"+itErrores.next()+"</td>");
                    out.write("<td width=\"20\">&nbsp;</td>");
                    out.write("</tr>");
                }
                out.write("</table>");
        }
        
        else{
			Throwable e = error.getCause();
			while(e !=null && e.getCause()!=null){
				e = e.getCause();
			}
        	
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
            out.write("<tr>");
            out.write("<td valign=\"top\">");
            
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablas\">");
            out.write("<tr>");
            out.write("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/bullet_ok.gif\" width=\"12\" height=\"11\"></td>");
            
            if (e != null && e.getMessage()!=null && error.getMessage().equals(e.getMessage())){
            	out.write("<td class=\"errortit\">" + error.getMessage() + "</td>");
            } else {
            	out.write("<td class=\"errortit\">" + error.getMessage() + (e!=null&&e.getMessage()!=null?" : "+e.getMessage():"") + "</td>");
            }
            
            out.write("<td width=\"20\">&nbsp;</td>");
            out.write("</tr></table>");

			out.write("</td>");            
			out.write("</tr>");
			out.write("</table>");            
        }
        out.write("</td>");
        out.write("</tr>");
        out.write("<tr>");
        
        String vistaAnterior = (String)request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
    	vistaAnterior = vistaAnterior !=null 
    		? "javascript:window.location.href='"+vistaAnterior+"';" 
    		: "javascript:history.back();";
		String funcion = vistaAnterior;
        
        out.write("<td><input name=\"Submit2\" type=\"submit\" class=\"botontextual\" onClick=\""
        		+ vistaAnterior + "\" value=\"&laquo; Volver\"><td>");
        out.write("</tr>");
        out.write("</table>");
    }
	/**
	 * @return
	 */
	public String getMensajeEncabezado() {
		return mensajeEncabezado;
	}

	/**
	 * @param string
	 */
	public void setMensajeEncabezado(String string) {
		mensajeEncabezado = string;
	}

}
