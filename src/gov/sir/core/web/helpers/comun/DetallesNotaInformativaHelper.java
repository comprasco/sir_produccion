package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author mmunoz, dlopez, ppabon, jvelez
 */
public class DetallesNotaInformativaHelper extends Helper {
	
	private Nota nota;
	private int posicion = 0;

	/**
	 * Helper utilizado para mostrar en pantalla los detalles de una nota informativa.
	 * @param request Trae toda la informacion asociada con la nota informativa. 
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		List notas = null;
		/**
		 * @author Cesar Ram?rez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Variable que almacena las notas informativas del turno padre (si tiene).
		 **/
		List notasTurnoPadre = null;
		String valPos = request.getParameter(CNota.POSICION);
		if(valPos==null){
			valPos = (String)request.getSession().getAttribute(CNota.POSICION);
		}
		
		
		if(valPos == null){
			throw new HelperException("La posici?n de la nota informativa es inv?lida");
		}
		int pos = Integer.valueOf(valPos).intValue();
		if(pos<0){
			throw new HelperException("La posici?n de la nota informativa es inv?lida");
		}
		
        
        //1. Si el turno existe, se saca la lista de notas informativas del turno.
		HttpSession session = request.getSession();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		/**
		 * @author Cesar Ram?rez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Se obtiene la informaci?n del turno padre.
		 **/
		Turno turnoPadreDerivado = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);
		if (turno != null)
		{
		     notas = turno.getNotas();
		}
		
		
		//2. Si el turno no existe, se saca la lista de notas informativas de la lista
		//declarada en sesi?n.
		else
		{ 
			notas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		}
		
		//2.1 Si la lista no es vac?a se obtienen las notas informativas.
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
		 * @author Cesar Ram?rez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Trae todas las notas del turno padre.
		 **/
		if(turnoPadreDerivado != null) {
			notasTurnoPadre = turnoPadreDerivado.getNotas();
		}
		
		/**
		 * @author Cesar Ram?rez
		 * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
		 * Si turno actual es del proceso 3 (correcciones) y su turno padre sea de tipo 6 (registro) para a?adir las notas informativas al listado.
		 **/
		if (turno != null && turnoPadreDerivado != null) {
			if(turno.getIdProceso() == 3 && turnoPadreDerivado.getIdProceso() == 6) {
				for (int i=0; i< notasTurnoPadre.size(); i++) {
					Nota nota = (Nota) notasTurnoPadre.get(i);
					notas.add(nota);
				}
			}
		}
		
		if(pos >= notas.size()){
			throw new HelperException("La nota informativa que desea ver esta fuera del rango");
		}
		
		nota = (Nota) notas.get(pos);
		
		if(nota == null){
			throw new HelperException("La nota informativa es inv?lida");
		}
		
		this.posicion = pos;
		
		request.getSession().setAttribute(CNota.POSICION, "" + pos);
	}
	
	/**
	 * Este m?todo pinta en la pantalla la informaci?n de la nota informativa seleccionada. 
	 * @param request Trae toda la informacion asociada con la nota informativa. 
	 * @param out Se utiliza para poder escribir el codigo HTML de manera din?mica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		TextAreaHelper textArea = new TextAreaHelper();
		TextHelper textHelper = new TextHelper();
		
		out.write("<form name=\"NOTAS_INF\" action=\"notas.do\" method=\"post\" id=\"NOTAS_INF\">");
		out.write("<input type=\"hidden\" name=\""+WebKeys.ACCION+"\" value=\"" + gov.sir.core.web.acciones.comun.AWNotas.IMPRIMIR_NOTA_INFORMATIVA_DETALLE + "\">");		
		out.write("<input type=\"hidden\" name=\""+WebKeys.POSICION+"\" value=\""+new Integer(posicion).toString()+"\">\n");		
		out.write("<input type=\"hidden\" name=\""+WebKeys.NOTA_INFORMATIVA+"\" value=\""+WebKeys.NOTA_INFORMATIVA+"\">\n");
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>"); 
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("<td class=\"bgnsub\">Tipo Nota </td>");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_notas.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td width=\"100\">Nombre</td>");
		out.write("<td class=\"campositem\">" + nota.getTiponota().getNombre() + "</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td width=\"100\">Visibilidad</td>");
		
		if(nota.getTiponota().getVisibilidad() != null){
			out.write("<td class=\"campositem\">" + nota.getTiponota().getVisibilidad() + "</td>");	
		} else {
			out.write("<td class=\"campositem\">&nbsp;</td>");
		}
		
		out.write("<td width=\"100\">Devolutiva</td>");
		if(nota.getTiponota().isDevolutiva()){
			out.write("<td class=\"titresaltados\">X</td>");
		}else {
			out.write("<td class=\"campositem\">&nbsp;</td>");	
		}
		out.write("</tr>");
		out.write("</table>");
		out.write("<hr class=\"linehorizontal\">");
		if(nota.getTiponota().getDescripcion() != null && nota.getTiponota().getDescripcion().length() > 0){
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>"); 
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Descripci&oacute;n</td>");
			out.write("</tr>");
			out.write("<tr>"); 
			out.write("<td>&nbsp;</td>");
			out.write("<td>");
			textArea.setCols("100");
			textArea.setRows("5");
			textArea.setCssClase("campositem");
			textArea.setReadOnly(true);
			textArea.setNombre("DESC1");
			textArea.setId("DESC1");
			request.getSession().setAttribute("DESC1",nota.getTiponota().getDescripcion());
			textArea.render(request,out);
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
		}

		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("<td class=\"bgnsub\">Informacion de Creaci&oacute;n </td>");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_notas.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td width=\"100\">Usuario</td>");
		out.write("<td class=\"campositem\">" + nota.getUsuario().getUsername() + "</td>");
		out.write("<td width=\"130\">Fecha de Creaci&oacute;n:</td>");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nota.getTime());
		String fecha = "";
		//calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)
		String mes = "";
		if (calendar.get(Calendar.MONTH) < 10){
			mes = 0 + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		} else {
			mes = String.valueOf(calendar.get(Calendar.MONTH) + 1 );
		}
		
		fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + mes + "/" + calendar.get(Calendar.YEAR) + " " +calendar.get(Calendar.HOUR_OF_DAY) + ":" +calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
		
		out.write("<td class=\"campositem\">" + fecha + "</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Descripci&oacute;n</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td>");
		textArea.setCols("100");
		textArea.setRows("5");
		textArea.setCssClase("campositem");
		textArea.setReadOnly(true);
		textArea.setNombre("DESC2");
		textArea.setId("DESC2");
		request.getSession().setAttribute("DESC2",nota.getDescripcion());
		textArea.render(request,out);
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<hr class=\"linehorizontal\">");
		out.write("<table width=\"100%\" class=\"camposform\">");
		
		out.write("<tr>"); 
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>N?mero de Copias:</td>");		
		out.write("<td>");		
		textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setSize("5");
		textHelper.render(request,out);
		out.write("</td>");		

		out.write("<td><a href=\"javascript:document.NOTAS_INF.submit();\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_imprimir.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");		

		out.write("<td><a href=\"javascript:window.close()\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_cerrar_ventana.gif\" width=\"150\" height=\"21\" border=\"0\"></a></td>");
		out.write("<td width=\"400\">&nbsp;</td>");		
		out.write("</tr>");
		out.write("</table>");
		out.write("</form>");
	}

}
