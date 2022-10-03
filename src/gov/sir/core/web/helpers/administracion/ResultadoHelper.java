package gov.sir.core.web.helpers.administracion;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.ResultadoAnotacion;
import gov.sir.core.negocio.modelo.ResultadoFolio;
import gov.sir.core.web.acciones.consulta.AWConsulta;
import gov.sir.core.web.helpers.comun.Paginador;
import gov.sir.core.web.helpers.comun.PaginadorHelper;
import gov.sir.core.web.WebKeys;
import gov.sir.core.negocio.modelo.constantes.CFolio;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;


/**
 * Esta clase fue diseñada para poder pintar de manera clara los resultados de una consulta
 * de folios
 * @author jmendez
 */
public class ResultadoHelper extends Helper {
	private String tipo;

	/**
	 * Método constructor por defecto de la clase FasesHelper
	 */
	public ResultadoHelper() {
		this.tipo = AWConsulta.PAGINADOR_RESULTADOS;
	}

	/**
	 * @param tipo  Tipo de paginador a ser desplegado. Valores posibles:
	 * AWConsulta.PAGINADOR_RESULTADOS (Valor por omisión), AWConsultas.PAGINADOR_RESULTADOS_SELECCIONADOS
	 *
	 */
	public void setTipo(String tipo) {
		if (tipo.equals(AWConsulta.PAGINADOR_RESULTADOS)
			|| tipo.equals(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS)) {
			this.tipo = tipo;
		}
	}

	/**
	 * Este método pinta en la página las tablas de los datos de resultado
	 * de una consulta
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		HttpSession session = request.getSession();

		Hashtable foliosSeleccionados =
			(Hashtable) session.getAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA);

		Paginador paginador = null;

		if (tipo.equals(AWConsulta.PAGINADOR_RESULTADOS)) {
			paginador = (Paginador) session.getAttribute(AWConsulta.PAGINADOR_RESULTADOS);
		} else {
			paginador = (Paginador) session.getAttribute(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
		}

		if (paginador == null) {
			throw new HelperException("No es un paginador válido");
		}

		List resultados = null;
		int counter = paginador.getIndex();

		try {
			int pagina = Integer.parseInt(request.getParameter(PaginadorHelper.PAGINA));
			counter = paginador.getIndexPagina(pagina);
			resultados = paginador.getPagina(pagina);
		} catch (java.lang.NumberFormatException e) {
			int pagina_actual = paginador.getUltimaPaginaGenerada();
			counter = paginador.getIndexPagina(pagina_actual);
			resultados = paginador.getPagina(pagina_actual);
		}

		if (resultados == null || resultados.isEmpty()) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("La consulta no arrojó ningún resultado");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
		} else {
			for (Iterator iter = resultados.iterator(); iter.hasNext();) {
				counter++;

				ResultadoFolio resultadofolio = (ResultadoFolio) iter.next();

				List resultadosAnotaciones = resultadofolio.getResultadosAnotacions();
				String codigoAnotacion = "&nbsp;";
				String naturalezaJuridica = "&nbsp;";
				String especificacion = "&nbsp;";
				String ultimoPropietario = "&nbsp;";
				String mayorExtensión = (resultadofolio.isMayorExtension()) ? "SI" : "NO";
				String tipoDocumento = "&nbsp;";
				String estadoFolio = "&nbsp;";
				String direccionInmueble =
					(resultadofolio.getLastDireccion() == null)
						? "&nbsp;"
						: resultadofolio.getLastDireccion();
				String nombrePropietario = "&nbsp;";

				//NOTA: SE ASUME QUE LAS COLECCIONES LLEGAN ORGANIZADAS ASCENDIENTEMENTE POR SU LLAVE.
				//      EL ÚLTIMO REGISTRO DE LA COLECCIÓN ES EL VALOR ACTUAL PARA EL FOLIO
				if (!resultadosAnotaciones.isEmpty()) {
					
                    ResultadoAnotacion resultadoAnotacion = seleccionarResultadoAnotacion(resultadosAnotaciones);
						//(ResultadoAnotacion) resultadosAnotaciones.get(resultadosAnotaciones.size() - 1);
					Anotacion anotacion = resultadoAnotacion.getAnotacion();
                                        System.out.println("ANOTACION GERE: "+anotacion.getIdAnotacion());
					codigoAnotacion = anotacion.getOrden();
					ultimoPropietario = (resultadoAnotacion.isPropietario()) ? "SI" : "NO";
					

					if(anotacion.getEspecificacion()!=null){
						especificacion = anotacion.getEspecificacion();
						if(especificacion != null && especificacion.length() > 45){
							especificacion = especificacion.substring(0,45);
						}						
					}else{
						if (anotacion.getNaturalezaJuridica() != null) {
							especificacion = anotacion.getNaturalezaJuridica().getNombre();
						}
					}
					
					if(anotacion.getNaturalezaJuridica() != null) {
						naturalezaJuridica = anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica();
					}

					Ciudadano ciudadanoPropietario = resultadoAnotacion.getCiudadanoPropietario();
					if (ciudadanoPropietario != null) {
						nombrePropietario =
							((ciudadanoPropietario.getNombre() == null)
								? ""
								: ciudadanoPropietario.getNombre())
								+ " "
								+ ((ciudadanoPropietario.getApellido1() == null)
									? ""
									: ciudadanoPropietario.getApellido1())
								+ " "
								+ ((ciudadanoPropietario.getApellido2() == null)
									? ""
									: ciudadanoPropietario.getApellido2());
						tipoDocumento = ciudadanoPropietario.getTipoDoc() + " " + ciudadanoPropietario.getDocumento();
					}
				}
				if(resultadofolio.getEstadoFolio()!=null){
					estadoFolio = resultadofolio.getEstadoFolio();
				}
				out.write("<table width=\"100%\" class=\"camposform\">\r\n");
				out.write("<tr>\r\n");
				out.write("<td class=\"titulotbcentral\"><table width=\"100%\" >\r\n");
				out.write("<tr>\r\n");

				/*if (tipo.equals(AWConsulta.PAGINADOR_RESULTADOS)) {
					if (foliosSeleccionados != null) {
						out.write(
							"<td width=\"20\"><input type=\"checkbox\" name=\""
								+ AWConsulta.CHECKBOX_MATRICULA
								+ "_"
								+ counter
								+ "\" value=\""
								+ resultadofolio.getIdMatricula()
								+ "\"  "
								+ ((foliosSeleccionados.get(resultadofolio.getIdMatricula()) == null)
									? ""
									: "checked")
								+ "  ></td>\r\n");
					} else {
						out.write(
							"<td width=\"20\"><input type=\"checkbox\" name=\""
								+ AWConsulta.CHECKBOX_MATRICULA
								+ "_"
								+ counter
								+ "\" value=\""
								+ resultadofolio.getIdMatricula()
								+ "\"></td>\r\n");
					}
				}*/

				out.write("<td class=\"titulotbcentral\">Resultado " + counter + " </td>\r\n");
				out.write("<td width=\"150\" class=\"titulotbcentral\">\r\n");

				out.write("<img name=\"imageField\" onClick=\"MM_openBrWindow('consultasAdministracion.do?");
				out.print(WebKeys.ACCION + "=" + AWConsulta.VER_FOLIO + "&" + CFolio.NUMERO_MATRICULA_INMOBILIARIA + "=" + resultadofolio.getIdMatricula());
				out.write("','Observación','resizable=yes,scrollbars=yes,location=no,status=yes,menubar=no,copyhistory=no, width=800,height=600');\" src=\"");
				out.print(request.getContextPath());
				out.write("/jsp/images/btn_observar.gif\" width=\"139\" height=\"21\" border=\"0\">\r\n");

				out.write("</tr>\r\n");
				out.write("</table></td>\r\n");
				out.write("</tr>\r\n");
				out.write("<tr>\r\n");
				out.write("<td><table width=\"100%\" class=\"camposform\">\r\n");
				out.write("<tr>\r\n");
				out.write("<td class=\"contenido\">Matr&iacute;cula</td>\r\n");
                                if (!resultadosAnotaciones.isEmpty()) {
                                	out.write(
                        					"<td>Estado</td>\r\n");
                                    out.write(
                                            "<td>C&oacute;digo Anotaci&oacute;n </td>\r\n");
									out.write(
											"<td>Especificaci&oacute;n</td>\r\n");
                                    out.write(
                                            "<td class=\"contenido\">Naturaleza Jur&iacute;dica </td>\r\n");
                                    out.write(
                                            "<td>Ultimo Propietario </td>\r\n");
                                    out.write(
                            				"<td>Identificaci&oacute;n </td>\r\n");
                                    out.write(
                                            "<td>Nombre Ultimo Propietario </td>\r\n");
                                }
				out.write("<td class=\"contenido\">Folios Extensos</td>\r\n");
				out.write("</tr>\r\n");
				out.write("<tr>\r\n");
				out.write("<td class=\"campositem\">" + resultadofolio.getIdMatricula() + "</td>\r\n");
                                if (!resultadosAnotaciones.isEmpty()) {
                                	out.write("<td class=\"campositem\">" + 
				                			estadoFolio + "</td>\r\n");
                                    out.write("<td class=\"campositem\">" +
                                              codigoAnotacion + "</td>\r\n");
									out.write("<td class=\"campositem\">" +
											  especificacion + "</td>\r\n");
                                    out.write("<td class=\"campositem\">" +
                                              naturalezaJuridica + "</td>\r\n");
                                    out.write("<td class=\"campositem\">" +
                                              ultimoPropietario + "</td>\r\n");
                                    out.write("<td class=\"campositem\">" +
                                    		  tipoDocumento + "</td>\r\n");
                                    out.write("<td class=\"campositem\">" +
                                              nombrePropietario + "</td>\r\n");
                                }
				out.write("<td class=\"campositem\">" + mayorExtensión + "</td>\r\n");
				out.write("</tr>\r\n");
				out.write("</table>\r\n");
				out.write("<table width=\"100%\" class=\"camposform\">\r\n");
				out.write("<tr class=\"contenido\">\r\n");
				out.write("<td class=\"contenido\">Direcci&oacute;n del Inmueble </td>\r\n");
				out.write("<td class=\"campositem\">" + direccionInmueble + "</td>\r\n");
				out.write("</tr>\r\n");
				out.write("</table>\r\n");
				out.write("</td>\r\n");
				out.write("</tr>\r\n");
				out.write("</table>\r\n");
				out.write("<hr class=\"linehorizontal\">\r\n");
			}
		}
	}
    
    private ResultadoAnotacion seleccionarResultadoAnotacion(List resultados) {
        
        ResultadoAnotacion resultado = null;
        ResultadoAnotacion resultadoTemp = null;
        
        resultadoTemp = (ResultadoAnotacion)resultados.get(resultados.size() - 1);
        for(int i = resultados.size() - 1; i >= 0; i--) {
            
            resultado = (ResultadoAnotacion)resultados.get(i);
            System.out.println("ID ANOTACION "+resultado.getAnotacion().getIdAnotacion()+" ID ANOTACION MODIFICADA "+resultado.getAnotacion().getIdAnotacionModificada());
            if(resultado.isPropietario()) {
                return resultado;
            }
        }
        
        return resultadoTemp;
    }
}
