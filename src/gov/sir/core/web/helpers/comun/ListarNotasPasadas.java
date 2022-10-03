package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.web.WebKeys;

/**
 * @author mmunoz, ppabon
 */
public class ListarNotasPasadas extends Helper{
	
	private int max = 70;
	private List notas;
	private boolean mostrarImpresion = false;
	private Turno turno;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		turno = (Turno) session.getAttribute(WebKeys.TURNO);
		//if(turno == null){
		//	throw new HelperException("El turno que se encuentra en la sesion es nulo");
		//} 
		
		
		//1. Si el turno existe, se saca la lista de notas informativas del turno.
		if (turno != null)
		{
			notas = turno.getNotas();
		}
		
		
		//2. Si el turno no existe, se saca la lista de notas informativas de la lista
		//declarada en sesión.
		else
		{ 
		    List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		
		    //2.1 Si la lista no es vacía se obtienen las notas informativas.
		    if (listaNotas != null)
		    {
		   	  notas = listaNotas; 
		    }
		}
				
		if(notas == null){
			notas = new Vector();
		}
	}
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		
		
		Iterator itNotas = notas.iterator();
		int i = 0;
		int j = 0;
				
		out.write("<input type=\"hidden\" name=\""+WebKeys.POSICION+"\" value=\""+gov.sir.core.web.acciones.comun.AWNotas.AGREGAR_NOTA+"\">\n");
		out.write("<table width=\"100%\" class=\"camposform\">");
		while(itNotas.hasNext()){
			out.write("<tr>");
			Nota nota  = (Nota) itNotas.next();
			out.write("<td width=\"20\" align=\"left\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td class=\"titulotbcentral\" align=\"left\">" + nota.getTiponota().getNombre() + "</td>");
			out.write("<td align=\"left\">" + nota.getUsuario().getUsername() + "</td>");
			max = 50;
			String descripcion = nota.getDescripcion();
			if(descripcion == null) {
				out.write("<td align=\"left\">");
				out.write("</td>");
			} else if(descripcion.length() > max){
				out.write("<td align=\"left\">"); 
				out.write(descripcion.subSequence(0,max) + "...");  
				out.write("</td>");
			} else {
				out.write("<td align=\"left\">");
				out.write(descripcion);
				out.write("</td>");
			}
			if(nota.getTiponota().isDevolutiva() && nota.getIdFase().equals(CFase.CAL_CALIFICACION)){
				out.write("<td width=\"40\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_imprimir_off.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Imprimir nota informativa\"></td>");
				out.write("<td width=\"40\"><a href=\"javascript:verNota('detalles.nota.devolutiva.view?" + CNota.POSICION  + "=" + j + "','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Ver detalles nota devolutiva Calificacion\"></a></td>");
				out.write("</tr>");
				j++;
			}else{
				if(mostrarImpresion){
					out.write("<input type=\"hidden\" name=\""+WebKeys.NUMERO_COPIAS_IMPRESION+"\" value=\"1\">\n");
					out.write("<td width=\"40\"><a href=\"javascript:imprimirNotaInformativa('"+i+"');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_imprimir.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Imprimir nota informativa\"></a></td>");
				}
							
				out.write("<td width=\"40\"><a href=\"javascript:verNota('detalles.nota.view?" + CNota.POSICION  + "=" + i + "','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Ver detalles nota informativa\"></a></td>");
				out.write("</tr>");
			}
			
			i++;
		}
		out.write("</table>");
	}



	/**
	 * @param i
	 */
	public void setMax(int i) {
		max = i;
	}

	/**
	 * @param b
	 */
	public void setMostrarImpresion(boolean b) {
		mostrarImpresion = b;
	}

}
