package gov.sir.core.web.helpers.comun;

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

import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.web.WebKeys;

/**
 * @author dlopez
 */
public class ListarNotasInformativasHelper extends Helper{
	
	private int max = 70;
	private List notas;
	private List notasTurnoPadre;
	private boolean mostrarImpresion = false;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		/**
		 * @author Cesar Ramírez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Variable que almacena las notas informativas del turno padre (si tiene).
		 **/
		Turno turnoPadreDerivado = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);
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
		    notas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		}
		
		    //2.1 Si la lista no es vacía se obtienen las notas informativas.
		    if (notas != null)
		    {
		   	  //Se filtra el listado para que solo tome las notas informativas
		   	  List listaAuxNotas = new ArrayList();
		   	  
		   	  for  (int i=0; i< notas.size(); i++)
		   	  {
		   	  	Nota nota = (Nota) notas.get(i);
		   	  	if (nota != null)
		   	  	{
		   	  		TipoNota tipoNota = nota.getTiponota();
		   	  		if (tipoNota != null)
		   	  		{
		   	  			if (tipoNota.isDevolutiva())
		   	  			{
		   	  			}
		   	  			else
		   	  			{
		   	  				listaAuxNotas.add(nota);
		   	  			}
		   	  		}
		   	  	}
		   	  }
		   	  
		   	  notas = listaAuxNotas;		   	  
		    }
		
				
		if(notas == null){
			notas = new Vector();
		}
		
		/**
		 * @author Cesar Ramírez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Se obtiene las notas informativas del tuno padre.
		 **/
		if(turnoPadreDerivado != null) {
			notasTurnoPadre = turnoPadreDerivado.getNotas();
		}
		
		/**
		 * @author Cesar Ramírez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Si turno actual es del proceso 3 (correcciones) y su turno padre sea de tipo 6 (registro) para añadir las notas informativas al listado.
		 **/
		if (turno != null && turnoPadreDerivado != null) {
			if(turno.getIdProceso() == 3 && turnoPadreDerivado.getIdProceso() == 6) {
				for (int i=0; i< notasTurnoPadre.size(); i++) {
					Nota nota = (Nota) notasTurnoPadre.get(i);
					notas.add(nota);
				}
			}
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
			if(mostrarImpresion){
				out.write("<td width=\"40\"><a href=\"javascript:imprimirNotaInformativa('"+i+"');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_imprimir.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Imprimir nota informativa\"></a></td>");
			}
						
			out.write("<td width=\"40\"><a href=\"javascript:verNota('detalles.nota.informativa.view?" + CNota.POSICION  + "=" + i + "','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Ver detalles nota informativa\"></a></td>");
			out.write("</tr>");
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
