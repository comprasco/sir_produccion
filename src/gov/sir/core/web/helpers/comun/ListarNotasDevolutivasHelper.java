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
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;

/**
 * @author dlopez
 */
public class ListarNotasDevolutivasHelper extends Helper{
	
	private int max = 70;
	private List notas;
	private int temporales = 0;
	private Turno turno;
	private boolean mostrarImpresion = false;
	private boolean devolutivaCalificacion = false;
	
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
		    notas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		}
		
		    //2.1 Si la lista no es vacía se obtienen las notas informativas.
		    if (notas != null)
		    {
		   	  //Se filtra el listado para que solo tome las notas informativas
		   	  List listaAuxNotas = new ArrayList();
		   	  //Se filtra el listado para notas en estado temporal por no haber salido de la fase de firma
		   	  List listaAuxNotasTemporal = new ArrayList();
		   	  
		   	  for  (int i=0; i< notas.size(); i++)
		   	  {
		   	  	Nota nota = (Nota) notas.get(i);
		   	  	if (nota != null)
		   	  	{
		   	  		TipoNota tipoNota = nota.getTiponota();
		   	  		if (tipoNota != null)
		   	  		{
		   	  			if (tipoNota.isDevolutiva() && nota.getIdFase().equals(CFase.CAL_CALIFICACION)){
		   	  				devolutivaCalificacion = true;
		   	  			}
		   	  			
		   	  			if (tipoNota.isDevolutiva() && String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_REGISTRO)){
		   	  				Usuario usuarioEnConsulta = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		   	  				/*JAlcazar caso Mantis 03992 Requerimiento No 188 - No aparece la nota devolutiva
                                                         * se modifico la cadena REG_ENTREGA_EXTERNOS la cual no correspondia a la fase en la BD
                                                         * por REG_ENTREGA_EXTERNO y no permitia visualizar las notas devolutivas de los turnos en esta fase.
                                                         */
                                                        if((!turno.getIdFase().equals("NOT_NOTA_DEVOLUTIVA") && !turno.getIdFase().equals("NOT_NOTA_NOTIFICADA")
                                                                && !turno.getIdFase().equals("NOT_RECURSOS_NOTA")
                                                                && !turno.getIdFase().equals("REG_CERTIFICADOS_ASOCIADOS") && !turno.getIdFase().equals("REG_ENTREGA")
		   	  					&& !turno.getIdFase().equals("REG_ENTREGA_EXTERNO") && !turno.getIdFase().equals("FINALIZADO")
		   	  					&& !turno.getIdFase().equals("FINALIZADO-DEVOL")) 
									&& nota.getUsuario().getIdUsuario() == usuarioEnConsulta.getIdUsuario()){
		   	  					listaAuxNotasTemporal.add(nota);//notas de turnos que aun no han salido de la fase de firma
		   	  					temporales = 1;
		   	  				}
                                                        /*JAlcazar caso Mantis 03992 Requerimiento No 188 - No aparece la nota devolutiva
                                                         * se modifico la cadena REG_ENTREGA_EXTERNOS la cual no correspondia a la fase en la BD
                                                         * por REG_ENTREGA_EXTERNO y no permitia visualizar las notas devolutivas de los turnos en esta fase.
                                                         */
		   	  				if(turno.getIdFase().equals("NOT_NOTA_DEVOLUTIVA") || turno.getIdFase().equals("NOT_NOTA_NOTIFICADA")
                                                                || turno.getIdFase().equals("NOT_RECURSOS_NOTA")
                                                                || turno.getIdFase().equals("REG_CERTIFICADOS_ASOCIADOS") || turno.getIdFase().equals("REG_ENTREGA") 
		   	  					|| turno.getIdFase().equals("REG_ENTREGA_EXTERNO") || turno.getIdFase().equals("FINALIZADO")
		   	  					|| turno.getIdFase().equals("FINALIZADO-DEVOL")){
		   	  					temporales = 0;
		   	  					listaAuxNotas.add(nota);
		   	  				}
		   	  			} else {
		   	  				if (tipoNota.isDevolutiva()){
		   	  					listaAuxNotas.add(nota);
		   	  					temporales = 0;
		   	  				}
		   	  			}
		   	  					   	  			
		   	  		}
		   	  	}
		   	  }
		   	  
			  notas = new ArrayList();
			  if(temporales == 1)//vista unicamente por el usuario en modo temporal
				  notas = listaAuxNotasTemporal;
			  else{
				  notas = listaAuxNotas;
				  temporales = 0;
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
						
			if(temporales == 1)//Estas notas solo pueden ser visualizadas por el usuario que las creo antes de salir de la fase de firma
				out.write("<td width=\"40\"><a href=\"javascript:verNota('detalles.nota.devolutiva.view?" + CNota.POSICION  + "=" + i + "','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0');\"><img src=\"" + request.getContextPath() + "/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\" border=\"0\" alt=\"Ver detalles nota informativa\"></a></td>");
			else
				out.write("<td width=\"40\"><a href=\"javascript:verNota('detalles.nota.devolutiva.view?" + CNota.POSICION  + "=" + i + "','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0');\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Ver detalles nota informativa\"></a></td>");
			out.write("</tr>");
			i++;
		}
		out.write("</table>");
		if(notas.size()>0 && turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && devolutivaCalificacion){
			out.write("<form name=\"NOTAS_INF\" action=\"trasladoTurno.do\" method=\"post\" id=\"NOTAS_INF\">");
			out.write("<input type=\"hidden\" name=\""+WebKeys.ACCION+"\" value=\"" + gov.sir.core.web.acciones.comun.AWNotas.IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE + "\">");		
			out.write("<input type=\"hidden\" name=\""+WebKeys.NOTA_DEVOLUTIVA+"\" value=\""+WebKeys.NOTA_DEVOLUTIVA+"\">\n");
			//out.write("<input type=\"hidden\" name=\""+WebKeys.NUMERO_COPIAS_IMPRESION+"\" value=\"1\">\n");
			out.write("<table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr><td><b>Reimpresion de Nota Devolutiva de Calificación</b></td></tr>");
			out.write("<tr>");
			
			TextHelper textHelper = new TextHelper();
			out.write("<td>Número de Copias:</td>");
			out.write("<td>");
			textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
			textHelper.setSize("5");
			textHelper.render(request,out);
			out.write("</td>");
			out.write("<td><a href=\"javascript:document.NOTAS_INF.submit();\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_imprimir.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</form>");
		}
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
