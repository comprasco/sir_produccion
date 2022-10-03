package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import java.io.IOException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author mmunoz
 */
public class VerAnotacionHelper extends Helper {
	private Anotacion anotacion;
	boolean consultarPosicion = true;

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		
		if(consultarPosicion){
				
			String valPos = request.getParameter(CAnotacion.POSICION);
	
			if (valPos == null) {
				throw new RuntimeException("La posicion de la anotacion es invalida");
			}
	
			int pos = Integer.valueOf(valPos).intValue();
	
			if (pos < 0) {
				throw new RuntimeException("La posicion de la anotacion es invalida");
			}
	
			List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
	
			if (anotaciones == null) {
				anotaciones = new Vector();
			}
			
			if(anotaciones.size()==0){
				anotaciones =  (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES);
				
				if (anotaciones == null) {
					anotaciones = new Vector();
				}				
			}
			
			if(anotaciones.size()==0){
				String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
				if(nombreResultado!=null){
					DatosRespuestaPaginador RPag = (DatosRespuestaPaginador)request.getSession().getAttribute(nombreResultado);
					if (RPag!=null && RPag.getAnotacionesActual()!=null){
						anotaciones = RPag.getAnotacionesActual();		
					}
				}
				
				if (anotaciones == null) {
					anotaciones = new Vector();
				}				
			}
			
			if (pos >= anotaciones.size()) {
				throw new RuntimeException("La anotacion que desea ver esta fuera del rango");
			}
	
			anotacion = (Anotacion) anotaciones.get(pos);		
		}


		if (anotacion == null) {
			throw new RuntimeException("La anotacion es invalida");
		}
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		Calendar calendar = Calendar.getInstance();
		
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		if(consultarPosicion){
			out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Anotaci&oacute;n: N&ordm; " + anotacion.getOrden() + "</td>");	
		}else{
			out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Datos Anotaci&oacute;n</td>");	
		}
		
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td width=\"179\">Fecha</td>");
		if(anotacion.getFechaRadicacion()!=null){
			calendar.setTime(anotacion.getFechaRadicacion());	
		}		
		out.write("<td width=\"211\" class=\"campositem\">" + (calendar!=null?""+calendar.get(Calendar.DAY_OF_MONTH):"") + "/" + (calendar!=null?""+(calendar.get(Calendar.MONTH) + 1):"") + "/" + (calendar!=null?""+calendar.get(Calendar.YEAR):"") + "&nbsp;</td>");
		out.write("<td width=\"146\">Radicaci&oacute;n</td>");
		out.write("<td width=\"212\" class=\"campositem\">" + (anotacion.getNumRadicacion() != null ? anotacion.getNumRadicacion() : "&nbsp;") + "</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Doc</td>");
		out.write("<td class=\"campositem\">" + (anotacion.getDocumento()!=null&&anotacion.getDocumento().getTipoDocumento()!=null&&anotacion.getDocumento().getTipoDocumento().getNombre()!=null?anotacion.getDocumento().getTipoDocumento().getNombre():"") + " " + (anotacion.getDocumento()!=null&&anotacion.getDocumento().getNumero()!=null?anotacion.getDocumento().getNumero():"") + "&nbsp;</td>");
		out.write("<td>Del</td>");
		
		if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getFecha()!=null){
			calendar.setTime(anotacion.getDocumento().getFecha());	
		}		
		
		if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getOficinaOrigen()!=null){
			out.write("<td class=\"campositem\">" + (calendar!=null?""+calendar.get(Calendar.DAY_OF_MONTH):"")	+ "/" + (calendar!=null?""+(calendar.get(Calendar.MONTH) + 1):"") + "/"	+ (calendar!=null?""+calendar.get(Calendar.YEAR):"") + "  " + (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaOrigen() != null) && (anotacion.getDocumento().getOficinaOrigen().getNombre() != null)) ? anotacion.getDocumento().getOficinaOrigen().getNombre() : "")	+ " "	+ (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaOrigen() != null) && (anotacion.getDocumento().getOficinaOrigen().getNumero() != null)) ? anotacion.getDocumento().getOficinaOrigen().getNumero() : "")	+ " de "	+ (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaOrigen() != null) && (anotacion.getDocumento().getOficinaOrigen().getVereda() != null) && (anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre() != null)) ? anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre() : "")	+ "&nbsp;</td>");	
		}else if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getOficinaInternacional()!=null){
			out.write("<td class=\"campositem\">" + (calendar!=null?""+calendar.get(Calendar.DAY_OF_MONTH):"")	+ "/" + (calendar!=null?""+(calendar.get(Calendar.MONTH) + 1):"") + "/"	+ (calendar!=null?""+calendar.get(Calendar.YEAR):"") + "  " + (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaInternacional() != null) ) ? anotacion.getDocumento().getOficinaInternacional() : "") + "&nbsp;</td>");	
		}else if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getComentario()!=null){
			String comentario = anotacion.getDocumento().getComentario();
			String comentarioOficina = "&nbsp;";
			String comentarioVereda ="&nbsp;";			
			if(comentario.indexOf("-")!=-1){
				StringTokenizer token = new StringTokenizer(comentario, "-");
				comentarioVereda = token.nextToken();
				comentarioOficina = token.nextToken();
			}else{
				comentarioOficina = comentario;
			}				
			out.write("<td class=\"campositem\">" + (calendar!=null?""+calendar.get(Calendar.DAY_OF_MONTH):"")	+ "/" + (calendar!=null?""+(calendar.get(Calendar.MONTH) + 1):"") + "/"	+ (calendar!=null?""+calendar.get(Calendar.YEAR):"") + "  " + (comentarioOficina)	+ " de "	+ (comentarioVereda)	+ "&nbsp;</td>");	
		}
		
		
		
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Valor Acto </td>");
		out.write("<td class=\"campositem\">$" + (anotacion.getValor()!=0?NumberFormat.getInstance().format(anotacion.getValor()):"&nbsp;") + "&nbsp;</td>");
		out.write("<td>Estado Anotaci&oacute;n</td>");
		out.write("<td class=\"campositem\">"+((anotacion.getEstado() != null && anotacion.getEstado().getNombre()!=null) ? anotacion.getEstado().getNombre() : "&nbsp;")+"</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Especificaci&oacute;n</td>");
		if(anotacion.getEspecificacion()!=null){			
			out.write("<td class=\"campositem\">"+  anotacion.getEspecificacion() + "&nbsp;</td>");
		}else{
			out.write("<td class=\"campositem\">"+ ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "") + ((anotacion.getNaturalezaJuridica() != null) ? " - "+anotacion.getNaturalezaJuridica().getNombre() : "") + "&nbsp;</td>");			
		}
		out.write("<td>Naturaleza Jur&iacute;dica</td>");
		out.write("<td class=\"campositem\">"+ ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "") + "&nbsp;</td>");
		out.write("</tr>");
                out.write("<tr>");
                out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                out.write("<td> Modalidad </td>");
                out.write("<td class=\"campositem\">"+ ((anotacion.getModalidad() != null) ? anotacion.getModalidad() : "") + "&nbsp;</td>");
                out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Comentario</td>");
		out.write("<td colspan=\"4\" class=\"campositem\">"+((anotacion.getComentario() != null && !anotacion.getComentario().equals("")) ? anotacion.getComentario() : "&nbsp;")+"</td>");
		out.write("</tr>");			
		out.write("</table>");
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td colspan=\"4\" class=\"campostitle\"><p>Personas que intervienen en el acto (La X indica a la persona que figura como titular de derechos reales de dominio, I-Titular de dominio incompleto)</p></td>");
		out.write("</tr>");
		List ciudadanosTemp = anotacion.getAnotacionesCiudadanos();
		List ciudadanos = new ArrayList();

		if (ciudadanosTemp.isEmpty()) {
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td colspan=\"4\" >No hay ciudadanos participantes para esta anotacion</td>");
			out.write("</tr>");
		} else {
			ciudadanos = organizarCiudadanos(ciudadanosTemp);			
		}

		Iterator itCiudadanos = ciudadanos.iterator();

		while (itCiudadanos.hasNext()) {
			AnotacionCiudadano ciudadano = (AnotacionCiudadano) itCiudadanos.next();
			out.write("<tr>");
			out.write("<td>" + ciudadano.getRolPersona() + "</td>");
			out.write("<td class=\"campositem\">" + (((ciudadano.getCiudadano() != null) && (ciudadano.getCiudadano().getApellido1() != null)) ? " "+ ciudadano.getCiudadano().getApellido1() : "") + (((ciudadano.getCiudadano() != null) && (ciudadano.getCiudadano().getApellido2() != null)) ?  " "+ ciudadano.getCiudadano().getApellido2() : "")  + " " + (((ciudadano.getCiudadano() != null) && (ciudadano.getCiudadano().getNombre() != null)) ? ciudadano.getCiudadano().getNombre() : "") + "&nbsp;</td>");
			out.write("<td class=\"campositem\">" + (((ciudadano.getCiudadano() != null) && (ciudadano.getCiudadano().getTipoDoc() != null)) ? ciudadano.getCiudadano().getTipoDoc() : "") + "  " + (((ciudadano.getCiudadano() != null) && (ciudadano.getCiudadano().getDocumento() != null)) ? ciudadano.getCiudadano().getDocumento() : "") + "&nbsp;</td>");
			
			out.write("<td width=\"20\" align=\"center\" class=\"titresaltados\">"+ciudadano.getStringMarcaPropietario()+"&nbsp;</td>");
			out.write("<td class=\"campositem\">"+(ciudadano!=null && ciudadano.getParticipacion()!=null? ciudadano.getParticipacion():"")+"&nbsp;</td>");			

			out.write("</tr>");
		}
		out.write("</table>");
	}
	/**
	 * @param anotacion
	 */
	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
		this.consultarPosicion = false;
	}
	
	private List organizarCiudadanos(List ciudadanos) {
		Iterator itCiudadanos = ciudadanos.iterator();
		List losA = new ArrayList();
		List losDe = new ArrayList();
		while(itCiudadanos.hasNext()){
			AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
			String rol = anotacionCiudadano.getRolPersona();
			if(rol.equals("DE")){
				losDe.add(anotacionCiudadano);
			} else if(rol.equals("A")){
				losA.add(anotacionCiudadano);
			}

		}
		losDe.addAll(losA);
		return losDe;
	}	

}
