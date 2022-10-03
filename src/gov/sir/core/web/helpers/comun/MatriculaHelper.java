package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import java.io.IOException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado;
import gov.sir.hermod.dao.impl.jdogenie.JDOLookupDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y 
 * departamentos asociados a los mismos.
 */
import java.util.Vector;
import gov.sir.core.util.DepartamentosPorCirculoSingletonUtil;

/**
 * @author jfrias,mmunoz
 */
public class MatriculaHelper extends Helper {
	int num;

	private String accion = "AGREGAR";
	private boolean isLink = false;
	
	/** Variable que indica si se muestran el boton agregar de una matricula
	 * Caso: Ventana Certificados Simplificada*/
	private boolean mostrarBoton = true;  
	
	private String nomFuncion = "cambiarAccion";  

	private boolean mostrarAgregar = true;
	
	private boolean nacional = false;

        /**
	 *
	 */
	public MatriculaHelper() {
		super();
       	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		Integer nume = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);

		if (nume == null || !mostrarBoton) {
			request.getSession().setAttribute(CFolio.NUM_MATRICULAS, new Integer(0));
			num = 0;
		} else {
			num = nume.intValue();
		}

		if (num >= 1) {
			if (((Proceso) request.getSession().getAttribute(WebKeys.PROCESO)).getIdProceso() == Long.valueOf(CProceso.PROCESO_CERTIFICADOS).longValue() || ((Proceso) request.getSession().getAttribute(WebKeys.PROCESO)).getIdProceso() == Long.valueOf(CProceso.PROCESO_ANTIGUO_SISTEMA).longValue()) {
				if(mostrarBoton){
					mostrarAgregar = false;
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		out.write("<table width=\"100%\">");
		out.write("<tr>");
		out.write("<td>");
		out.write("<input type=\"hidden\" name=\"ITEM\" value=\"NINGUNO\">");
		if(mostrarBoton){
		if (num > 0) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td></td><td>N&uacute;mero de Matr&iacute;cula</td><td>N&uacute;mero de Anotaciones</td><td>Folio extenso</td><td>&nbsp;</td><td width=\"35\">Eliminar</td>");
			//out.write("</tr>");
			//out.write("</table>");			
			//out.write("<table width=\"100%\" class=\"camposform\">");
			for (int i = 0; i < num; i++) {
				Integer camb = new Integer(i);
				HttpSession session = request.getSession();

				String matr = (String) session.getAttribute(CFolio.ID_MATRICULA + camb.toString());
				String numAnota = (String) session.getAttribute(CFolio.NUMERO_ANOTACIONES);
				String mayorExtension = (String) session.getAttribute(CFolio.MAYOR_EXTENSION);
				String opcionImagen="&nbsp;";
				String mensajeMayorExtension;
	
				if(mayorExtension==null || mayorExtension.equals("")){
					mensajeMayorExtension = "&nbsp;";
				}
				else if(!mayorExtension.equals(CFolio.NO_MAYOR_EXTENSION)){
					opcionImagen = "<img name=\"ico_advertencia\" src=\"" + request.getContextPath() + "/jsp/images/ico_alerta.gif\">";
					mensajeMayorExtension = "ES UN FOLIO EXTENSO";
				}
				else{
					mensajeMayorExtension = "NO";
				}
					
				
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td class=\"campositem\">" + matr + "</td>");
				out.write("<td class=\"campositem\">" + numAnota + "</td>");
				out.write("<td class=\"campositem\">" + mensajeMayorExtension +"</td>");
				out.write("<td>" + opcionImagen+"</td>");
				
				out.write("<td width=\"35\"><a href=\"javascript:quitar('" + CFolio.ID_MATRICULA + camb.toString() + "')\"><img name=\"btn_mini_eliminar\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></a></td>");

				out.write("</tr>");
			}
			out.write("</table>");
			out.write("<br>");
		}
		}
		if (mostrarAgregar) {
			if (isLink) {
				String idCirculo = "";
				ListaElementoHelper circuloHelper = null;
				if (request.getSession().getAttribute(WebKeys.CIRCULO) != null) {
					idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
					idCirculo = idCirculo + "-";
				}

				out.write("<table width=\"100%\" class=\"camposform\">");
				out.write("<tr></tr>");
				Integer otro = new Integer(num);
				out.write("<tr>");
				if	 (!nacional)
				{
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td width=\"600\">N&uacute;mero de Matr&iacute;cula</td>");
					out.write("<td width=\"30\">");
					out.write(idCirculo);
					out.write("</td>");
				} else
				{
                    try {
                        out.write("<tr>");
                        out.write("<td class=\"titulotbcentral\" width=\"20%\">C&iacute;rculo</td>");
                        out.write("<td class=\"titulotbcentral\" width=\"20%\">N&uacute;mero de Matr&iacute;cula</td>");
                        out.write("<td class=\"titulotbcentral\" width=\"60%\">&nbsp;</td>");
                        out.write("</tr>");
                        out.write("<tr>");
                        out.write("<td align=\"rigth\">");
                        List elementos = new java.util.ArrayList();
                        List circulos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA);
                        Circulo circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                        /**
                        * @author Fernando Padilla Velez
                        * @change 5453: Acta - Requerimiento No 207 - Exclusion de circulos registrales
                        *         Se consulta el valor del lookupcode para el tipo CERTIFICADOS y codigo CIRCULOS_NO_EXPIDEN_CERTIFICADOS.
                        */
                        JDOLookupDAO lookDAO = new JDOLookupDAO();
                        String circulosNoNacionales = lookDAO.getValor(COPLookupTypes.CERTIFICADOS, COPLookupCodes.CIRCULOS_NO_EXPIDEN_CERTIFICADOS);

                        if(circulosNoNacionales == null){
                            circulosNoNacionales = "";
                        }
                        
                        /**
                        * @autor HGOMEZ 
                        * @mantis 13407 
                        * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
                        * @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
                        * de departamentos por circulo.
                        */
                        List listaCirculoDepartamento = new Vector();
                        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
                        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

                        int idCirculoInt = 0;
                        String nombreCirculoDepartamento = "";

                        for (java.util.Iterator iter = circulos.iterator(); iter.hasNext();) {
                            gov.sir.core.negocio.modelo.Circulo circuloAux = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                            /**
                            * @author Fernando Padilla Velez
                            * @change 5453: Acta - Requerimiento No 207 - Exclusion de circulos registrales
                            *         Se incluye la validacion para que no sean cargados en la lista de circulos.
                            */
                            if (!circuloAux.getIdCirculo().equals(circ.getIdCirculo()) && !(circulosNoNacionales.indexOf(circuloAux.getIdCirculo()) != -1)) {
                                idCirculoInt = Integer.parseInt(circuloAux.getIdCirculo());
                                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                                if(nombreCirculoDepartamento != ""){
                                    elementos.add(new ElementoLista(circuloAux.getIdCirculo(), nombreCirculoDepartamento));
                                }
                            }
                        }
                        
                        circuloHelper = new ListaElementoHelper();
                        circuloHelper.setOrdenar(false);
                        circuloHelper.setNombre(AWLiquidacionCertificado.CIRCULO_CERTIFICADO_NACIONAL);
                        circuloHelper.setId(AWLiquidacionCertificado.CIRCULO_CERTIFICADO_NACIONAL);
                        circuloHelper.setCssClase("camposformtext");
                        circuloHelper.setTipos(elementos);
                        circuloHelper.render(request, out);
                        out.write("</td>");
                    } catch (Throwable ex) {
                        Logger.getLogger(MatriculaHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
				}
				
				String matriculaNoValida;
				if (request.getSession().getAttribute(CFolio.ID_MATRICULA + otro.toString()) != null) {
					matriculaNoValida = request.getSession().getAttribute(CFolio.ID_MATRICULA + otro.toString()).toString();
				} else {
					matriculaNoValida = "";
				}
				request.getSession().removeAttribute(CFolio.ID_MATRICULA + otro.toString());
				
				out.write("<td><input name=\"" + CFolio.ID_MATRICULA + otro.toString() + "\" id=\"" + CFolio.ID_MATRICULA + otro.toString() + "\" type=\"text\" value=\"" + matriculaNoValida + "\" onFocus=\"campoactual('" + CFolio.ID_MATRICULA + "');\" class=\"camposformtext\">");
				out.write("<img id=\"" + CFolio.ID_MATRICULA + "_img\" src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" class=\"imagen_campo\">");
				out.write("</td>");
				out.write("</tr>"); 
				out.write("</table>");

				out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
				out.write("<td align=\"right\">");
				if(mostrarBoton){
					out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
					out.write("<td>Agregar Matr&iacute;cula</td>");
					out.write("<td><a href=\"javascript:" + nomFuncion + "('" + accion + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\"></a>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");
				}
			} else {
				String idCirculo = "";
				if (request.getSession().getAttribute(WebKeys.CIRCULO) != null) {
					idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
					idCirculo = idCirculo + "-";
				}

				out.write("<table width=\"100%\" class=\"camposform\">");
				Integer otro = new Integer(num);
				out.write("<tr>");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td>N&uacute;mero de Matr&iacute;cula Inmobiliaria</td>");
				out.write("<td>"+idCirculo+"<input name=\"" + CFolio.ID_MATRICULA + otro.toString() + "\" id=\"" + CFolio.ID_MATRICULA + otro.toString() + "\" type=\"text\" value=\"\"  onFocus=\"campoactual('" + CFolio.ID_MATRICULA + "');\" class=\"camposformtext\">");
				out.write("<img id=\"" + CFolio.ID_MATRICULA + "_img\" src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" class=\"imagen_campo\">");
				out.write("</td>");
				
				//out.write("</tr>");
				//out.write("</table>");

				out.write("</td>");
				//out.write("</tr>");
				//out.write("<tr>");
				out.write("<td align=\"right\">");
				//out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
				//out.write("<tr>");
				out.write("<img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<td width=\"100\">Agregar Matr&iacute;cula</td>");
				out.write("<td> <input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\" onClick=\"" + nomFuncion + "('" + accion + "')\">");
				out.write("</td>");
				out.write("</tr>");
				out.write("</table>");

			}
		}
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");

	}
	/**
	 * @param string
	 */
	public void setAccion(String string) {
		accion = string;
	}

	/**
	 * @param b
	 */
	public void setLink(boolean b) {
		isLink = b;
	}

	/**
	 * @param string
	 */
	public void setNomFuncion(String string) {
		nomFuncion = string;
	}

	public boolean isMostrarBoton() {
		return mostrarBoton;
	}

	public void setMostrarBoton(boolean mostrarBoton) {
		this.mostrarBoton = mostrarBoton;
	}

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

}
