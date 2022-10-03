package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CPaginacion;
import gov.sir.core.web.acciones.administracion.AWAdministracionHermod;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author mnewball
 *
 */
public class SolicitudesPaginadasHelper extends Helper {
    
    private long numeroPagina = 0;
    private long numeroResultados = 0;
    private long numeroPaginas = 0;
    private long resultadosPorPagina = 0;
    private List solicitudes = null;
    private String contextPath = null;
    
    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request) throws HelperException {
        
        HttpSession session = request.getSession();
        Long pagina = (Long)session.getAttribute(CPaginacion.PAGINA);
        Long resultados = (Long)session.getAttribute(CPaginacion.TOTAL_RESULTADOS);
        Long numeroPorPagina = (Long)session.getAttribute(CPaginacion.RESULTADOS_POR_PAGINA);
        contextPath = request.getContextPath();
        
        if(pagina == null)
            numeroPagina = 0;
        else
            numeroPagina = pagina.longValue();
        
        if(resultados == null)
            numeroResultados = 0;
        else
            numeroResultados = resultados.longValue();
        
        if(numeroPorPagina == null)
            resultadosPorPagina = 10;
        else
            resultadosPorPagina = numeroPorPagina.longValue();
        
        numeroPaginas = numeroResultados / resultadosPorPagina;
        if(numeroResultados % resultadosPorPagina != 0)
            numeroPaginas++;

        if(numeroPagina > 0 && solicitudes == null){
            throw new HelperException("No se pudo cargar el listado de solicitudes para la página actual.");
        }
    }
    
    public void setSolicitudes(List listaSolicitudes) {
        solicitudes = listaSolicitudes;
    }
    
    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

        out.write("<input name=\"" + CPaginacion.PAGINA + "\" type=\"hidden\" id=\"" + CPaginacion.PAGINA + "\" value=\"" + numeroPagina + "\">");
        out.write("<input name=\"" + CPaginacion.RESULTADOS_POR_PAGINA + "\" type=\"hidden\" id=\"" + CPaginacion.RESULTADOS_POR_PAGINA + "\" value=\"" + resultadosPorPagina + "\">");
        
        if(numeroPagina == 0 || solicitudes == null)
            return;
        
        MostrarFechaHelper fechaHelper = new MostrarFechaHelper();

        out.write("<br>");
        out.write("<table width=\"100%\" class=\"camposform\">");
        out.write("<tr>");
        out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\"></td>");
        out.write("<td>Listado</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("</tr>");
        /*out.write("<tr>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>Buscar:</td>");
        out.write("<td><input type=\"text\" name=\"" + CPaginacion.CRITERIO_BUSQUEDA + "\"></td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("</tr>");*/
        
        // Muestro la tabla de resultados para la página actual
        out.write("<tr>");
        out.write("<td class=\"titulotbcentral\">&nbsp;</td>");
        out.write("<td class=\"titulotbcentral\">C&oacute;digo Solicitud</td>");
        out.write("<td class=\"titulotbcentral\">Fecha Solicitud</td>");
        out.write("<td class=\"titulotbcentral\">Documento Registro</td>");
        out.write("<td class=\"titulotbcentral\">Documento Solicitante</td>");
        out.write("<td class=\"titulotbcentral\">Eliminar</td>");
        out.write("</tr>");
        
        int contador = 0;
        
        Iterator iteradorSolicitudes = solicitudes.iterator();
        while(iteradorSolicitudes.hasNext()) {
            SolicitudRegistro solicitud = (SolicitudRegistro)iteradorSolicitudes.next();
            
            Ciudadano ciudadano = solicitud.getCiudadano();
            Documento documento = solicitud.getDocumento();
            String codigoSolicitud = "";
            
            if(solicitud.getIdSolicitud() != null){
                codigoSolicitud = solicitud.getIdSolicitud();
            }
            
            String idDocumento = "";
            String tipoDocumento = "";
            
            if(documento != null) {
                if(documento.getTipoDocumento() != null) {
                    if(documento.getTipoDocumento().getNombre() != null) {
                        idDocumento = documento.getTipoDocumento().getNombre();
                    }
                }
                if(documento.getIdDocumento() != null){
                    tipoDocumento = documento.getIdDocumento();
                }
            }
            
            String idSolicitante = "";
            String tipoSolicitante = "";
            
            if(ciudadano != null){
                if(ciudadano.getTipoDoc() != null){
                    tipoSolicitante = ciudadano.getTipoDoc();
                }
                if(ciudadano.getDocumento() != null){
                    idSolicitante = ciudadano.getDocumento();
                }
            }
            
            out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td class=\"camposformtext_noCase\">" + codigoSolicitud + "</td>");
            out.write("<td class=\"campositem\">");
            
            if (solicitud.getFecha()!=null){
                fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                fechaHelper.setDate(solicitud.getFecha());
                fechaHelper.render(request,out);
            }
            else{
                out.write("No disponible");
            }
            
            out.write("</td>");
            out.write("<td class=\"campositem\">" + tipoDocumento + "-" + idDocumento + "</td>");
            out.write("<td class=\"campositem\">" + tipoSolicitante + "-" + idSolicitante + "</td>");
            out.write("<td><input name=\"ELIMINAR_SOL\" type=\"checkbox\" value=\"" + Integer.toString(contador) + "\"></td>");
            out.write("</tr>");
            
            contador++;
        }
        
        out.write("<tr>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td align=\"right\">Seleccionar todas:</td>");
        out.write("<td><input type=\"checkbox\" name=\"seleccionado\" onclick='cambiarSeleccion()'></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td>&nbsp;</td>");
        out.write("<td align=\"center\" colspan=\"2\"><a href=\"javascript:cambiarAccionEliminar('" + AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS + "');\"><image src=\"" + contextPath + "/jsp/images/btn_eliminar.gif\" width=\"139\" height=\"21\" border=\"0\"></a></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td colspan=\"6\" align=\"center\">");
        
        // Paginador
        out.write("<table width=\"100%\"  border=\"0\" class=\"camposform\">");
        out.write("<tr>");
        out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + 
                "/jsp/images/ind_paginas.gif\" width=\"20\" height=\"15\"></td>");
        out.write("<td align=\"center\">");
        out.write("<table>");
        out.write("<tr>");
        
        if(numeroPagina > 1) {
            out.write("<td width=\"20\" align=\"center\">" +
                    "<a href=\"javascript:cambiarPagina(" + 1 + ")\"><img src=\"" + 
                    request.getContextPath() + "/jsp/images/btn_inicio.gif\" " +
                    "width=\"25\" height=\"21\" border=\"0\"></a></td>");
            out.write("<td width=\"20\" align=\"center\">" +
                    "<a href=\"javascript:cambiarPagina("+ (numeroPagina - 1) +")\"><img src=\"" + 
                    request.getContextPath() + "/jsp/images/btn_anterior.gif\" " +
                    "width=\"20\" height=\"21\" border=\"0\"></a></td>");
        }
        
        int seccionPaginas = (int)((numeroPagina - 1) / 10);
        seccionPaginas *= 10;
        for(int i = seccionPaginas; i < numeroPaginas && i - seccionPaginas < 10; i++) {
            out.write("<td align=\"center\"><a href=\"javascript:cambiarPagina(" + (i + 1) + ")\" class=\"" +
                        ((numeroPagina == (i + 1)) ? "paginadoractivo" : "paginadorinactivo") + "\">" + 
                        (i + 1) + "</a></td>");
        }
        
        if(numeroPagina < numeroPaginas){
            out.write("<td><a href=\"javascript:cambiarPagina("+ (numeroPagina + 1) + ")\">" +
                    "<img src=\"" + request.getContextPath() + "/jsp/images/btn_siguiente.gif\" " +
                    "width=\"20\" height=\"21\" border=\"0\"></a></td>");
            out.write("<td><a href=\"javascript:cambiarPagina(" + numeroPaginas + ")\"><img src=\"" +
                    request.getContextPath() + "/jsp/images/btn_fin.gif\" width=\"25\" height=\"21\" " +
                    "border=\"0\"></a></td>");
        }
        
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("</tr>");
        out.write("</table>");
        
        removerAtributos(request);
    }

    /**
     * @param request
     */
    private void removerAtributos(HttpServletRequest request) {
    }
}
