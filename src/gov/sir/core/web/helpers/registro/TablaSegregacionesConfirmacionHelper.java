package gov.sir.core.web.helpers.registro;

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

import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.acciones.registro.AWSegregacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

/**
 * @author mmunoz
 */
public class TablaSegregacionesConfirmacionHelper extends Helper {

    private List foliosDerivados;

    private boolean esVerificacion = false;

    private ListaElementoHelper helperUnidad = new ListaElementoHelper();

    /**
     * Lista donde se guardan las anotaciones ya creadas
     */
    private List anotaciones;

    private boolean mostrarMatriculasGeneradas = false;

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     *
     * @param request Trae toda la informacion que se a guardado sobre el
     * usuario
     */
    public void setProperties(HttpServletRequest request)
            throws HelperException {

        HttpSession session = request.getSession();

        foliosDerivados = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);

        if (foliosDerivados == null) {
            foliosDerivados = new Vector();
        }
        List unidadesMedida = (List) session.getServletContext().getAttribute(WebKeys.LISTA_UNIDADES_MEDIDA);
        if (unidadesMedida == null) {
            unidadesMedida = new Vector();
        }
        helperUnidad.setCssClase("camposformtext");
        helperUnidad.setId(CFolioDerivado.UNIDAD_MEDIDA);
        helperUnidad.setNombre(CFolioDerivado.UNIDAD_MEDIDA);
        Iterator itUnidadesM = unidadesMedida.iterator();
        List nUnidades = new Vector();
        while (itUnidadesM.hasNext()) {
            OPLookupCodes code = (OPLookupCodes) itUnidadesM.next();
            nUnidades.add(new ElementoLista(code.getCodigo(), code.getValor()));
        }
        helperUnidad.setTipos(nUnidades);
        helperUnidad.setSelected("M2");

        anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_SEGREGACION);
        if (anotaciones == null) {
            anotaciones = new Vector();
        }
    }

    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del
     * error
     *
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera
     * dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
            throws IOException, HelperException {
        
        TextHelper areasHelper = new TextHelper();
        areasHelper.setCssClase("camposformtext");
        
        /* 
        * Autor: Cristian David Garcia
        * Requerimiento: Catastro Nuevos Campos
        * Adicionar Area Privada, Area Construida y Determinación del Inmueble al realizar la segregacion
        */
        ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
        List determInm = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
        if(determInm == null){
            determInm = new ArrayList();
        }
        tipoDetermInmHelper.setTipos(determInm);
        tipoDetermInmHelper.setCssClase("camposformtext");
        
        if (!foliosDerivados.isEmpty()) {
            out.write("<script>\n");
            
            out.println("function valideKey(evt){");
            out.println("var code = (evt.which) ? evt.which : evt.keyCode;");
            out.println("if(code==8) { // backspace.");
            out.println("return true;");
            out.println("} else if(code>=48 && code<=57) {");
            out.println("return true;");
            out.println("} else{");
            out.println("return false;");
            out.println("}");
            out.println("}");
            
            out.write("	   function editarFolio(text){\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + WebKeys.ACCION + ".value='" + AWSegregacion.CONSULTAR_NUEVO_FOLIO + "';\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + CFolio.ID_MATRICULA + ".value = text;\n");
            out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");
            out.write("	   }\n");

            out.write("	   function editarFolioDerivado(text){\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + WebKeys.ACCION + ".value='" + AWSegregacion.EDITAR_FOLIO_DERIVADO + "';\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + WebKeys.POSICION + ".value = text;\n");
            out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");
            out.write("	   }\n");

            out.write("	   function eliminarFolioDerivado(text){\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + WebKeys.ACCION + ".value='" + AWSegregacion.ELIMINAR_FOLIO_DERIVADO + "';\n");
            out.write("	       document.all.EDITAR_SEGREGACION." + WebKeys.POSICION + ".value = text;\n");
            out.write("	       document.all.EDITAR_SEGREGACION.submit();\n");
            out.write("	   }\n");
            out.write("</script>\n");

            out.write("<br>");
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");
            out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");

            if (esVerificacion) {
                out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Folios Segregados</td>");
            } else {
                out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Folios Agregados</td>");
            }

            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_matriculas.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_desenglobar.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("</tr>");
            out.write("</table>");

            out.write("<form method=\"post\" action=\"segregacion.do\" name=\"EDITAR_SEGREGACION\">");
            out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"\">");
            out.write("<input type=\"hidden\" name=\"" + WebKeys.POSICION + "\" id=\"" + WebKeys.POSICION + "\" value=\"\">");
            out.write("<input type=\"hidden\" name=\"" + CFolio.ID_MATRICULA + "\" id=\"" + CFolio.ID_MATRICULA + "\" value=\"\">");
            //out.write("<input type=\"hidden\" name=\"" + CFolio.ID_MATRICULA + "\" id=\"" + CFolio.ID_MATRICULA + "\" value=\"" + "" + folio.getIdMatricula1() + "" + "\">");

            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Nro.</td>");
            out.write("<td>Inmueble</td>");
            out.write("<td colspan=\"6\" align=\"center\"> Area (Seleccione las unidades de medida del predio)</td>");
            out.write("<td width=\"150\">Coeficiente %</td>");
            out.write("<td width=\"40\">&nbsp;</td>");
            out.write("</tr>");
            int i = 0;

            Iterator itFolio = foliosDerivados.iterator();
            while (itFolio.hasNext()) {
                FolioDerivado folio = (FolioDerivado) itFolio.next();
                String hectareas = "";
                String metros = "";
                String centimetros = "";
                String lote = "";
                String porcentaje = "";
                if (folio.getHectareas() != null || folio.getMetros() != null || folio.getCentimetros() != null) {
                    if (folio.getHectareas() != null) {
                        hectareas = folio.getHectareas();
                    }
                    if (folio.getMetros() != null) {
                        metros = folio.getMetros();
                    }
                    if (folio.getCentimetros() != null) {
                        centimetros = folio.getCentimetros();
                    }
                }
                if (folio.getLote() != null) {
                    if (folio.getIdMatricula1() != null) {
                        lote = folio.getIdMatricula1() + " - " + folio.getLote();
                    } else {
                        lote = folio.getLote();
                    }
                }
                if (folio.getPorcentaje() != null) {
                    porcentaje = folio.getPorcentaje();
                }
                out.write("<tr>");

                if (esVerificacion) {
                    out.write("<td width=\"20\">&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + (i + 1) + "&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + lote + "&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + hectareas + "&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + metros + "&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + centimetros + "&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + porcentaje + "&nbsp;</td>");
                } else {
                    TextHelper helper = new TextHelper();
                    helper.setCssClase("camposformtext");
                    HttpSession sesion = request.getSession();
                    out.write("<td width=\"20\">&nbsp;</td>");
                    out.write("<td class=\"campositem\">" + (i + 1) + "&nbsp;</td>");
                    out.write("<td>");

                    sesion.setAttribute(CFolioDerivado.NOMBRE_LOTE + i, lote);
                    helper.setId(CFolioDerivado.NOMBRE_LOTE + i);
                    helper.setNombre(CFolioDerivado.NOMBRE_LOTE + i);
                    helper.setSize("20");
                    helper.render(request, out);

                    out.write("&nbsp;</td>");
                    out.write("<td>");
                    out.write("Hect&aacutereas");
                    out.write("</td>");

                    out.write("<td>");

                    sesion.setAttribute(CFolioDerivado.HECTAREAS + i, hectareas);
                    areasHelper.setId(CFolioDerivado.HECTAREAS + i);
                    areasHelper.setNombre(CFolioDerivado.HECTAREAS + i);
                    areasHelper.setSize("20");
                    areasHelper.setMaxlength("20");
                    areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                    areasHelper.render(request, out);

                    out.write("&nbsp;</td>");

                    out.write("<td>");
                    out.write("Metros");
                    out.write("</td>");

                    out.write("<td>");

                    sesion.setAttribute(CFolioDerivado.METROS + i, metros);
                    areasHelper.setId(CFolioDerivado.METROS + i);
                    areasHelper.setNombre(CFolioDerivado.METROS + i);
                    areasHelper.setSize("20");
                    areasHelper.setMaxlength("4");
                    areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                    areasHelper.render(request, out);

                    out.write("&nbsp;</td>");

                    out.write("<td>");
                    out.write("Centimetros");
                    out.write("</td>");

                    out.write("<td>");

                    sesion.setAttribute(CFolioDerivado.CENTIMETROS + i, centimetros);
                    areasHelper.setId(CFolioDerivado.CENTIMETROS + i);
                    areasHelper.setNombre(CFolioDerivado.CENTIMETROS + i);
                    areasHelper.setSize("20");
                    areasHelper.setMaxlength("2");
                    areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                    areasHelper.render(request, out);

                    out.write("&nbsp;</td>");
                    out.write("<td>");

                    sesion.setAttribute(CFolioDerivado.PORCENTAJE + i, porcentaje);
                    helper.setId(CFolioDerivado.PORCENTAJE + i);
                    helper.setNombre(CFolioDerivado.PORCENTAJE + i);
                    helper.setSize("20");
                    helper.render(request, out);

                    out.write("&nbsp;</td>");

                }

                if (esVerificacion) {
                    if (mostrarMatriculasGeneradas) {
                        out.write("<td width=\"100\">No Matricula:</td>");
                        out.write("<td class=\"campositem\">" + folio.getIdMatricula1() + "</td>");
                    }
                    out.write("<td><a href=\"#\"><img  src=\"" + request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" width=\"35\" alt=\"Editar Folio Derivado\" height=\"13\" border=\"0\" onclick=\"javascript:editarFolio('" + folio.getIdMatricula1() + "');\"></a></td>");
                    if (fueEditado(folio.getIdMatricula1(), request)) {
                        out.write("<td><image src=\"" + request.getContextPath() + "/jsp/images/ico_alerta.gif\" width=\"20\" width=\"20\" alt=\"Folio editado\" height=\"13\"></td>");
                    } else {
                        out.write("<td width=\"20\"></td>");
                    }
                } else {
                    out.write("<td><a href=\"#\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" alt=\"Editar Folio Derivado\" height=\"13\"  border=\"0\" onclick=\"javascript:editarFolioDerivado('" + ("" + i) + "');\"></a></td>");
                    out.write("<td><a href=\"#\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" alt=\"Quitar Folio Derivado\" height=\"13\"  border=\"0\" onclick=\"javascript:eliminarFolioDerivado('" + ("" + i) + "');\"></a></td>");
                }
                out.write("</tr>");
                i++;
            }
            out.write("</table>");
            out.write("</form>");
        }
        if (!esVerificacion) {
            TextHelper helper = new TextHelper();
            out.write("<form action=\"segregacion.do\" method=\"post\" name=\"SEGREGACION\" id=\"SEGREGACION\">");
            out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" id=\"" + WebKeys.ACCION + "\" value=\"" + AWCalificacion.AGREGAR_FOLIO_DERIVADO + "\">");
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");
            out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Agregar Datos </td>");
            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_matriculas.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_desenglobar.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Inmueble</td>");
            out.write("<td colspan=\"6\" align=\"center\"> Area (Seleccione las unidades de medida del predio)</td>");
            //out.write("<td>Unidad de Medida</td>");				
            out.write("<td width=\"150\">Porcentaje % </td>");
            out.write("<td  align=\"center\" >Agregar Segregación </td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td width=\"20\">&nbsp;</td>");
            out.write("<td>");
            helper.setCssClase("camposformtext");
            helper.setId(CFolioDerivado.NOMBRE_LOTE);
            helper.setNombre(CFolioDerivado.NOMBRE_LOTE);
            //helper.setFuncion("onblur=javascript:validarRequerido('"+CFolioDerivado.NOMBRE_LOTE+"');");
            // Hectareas
            out.write("<td>");
            out.write("Hect&aacutereas");
            out.write("</td>");
            out.write("<td>");
            areasHelper.setId(CFolioDerivado.HECTAREAS);
            areasHelper.setNombre(CFolioDerivado.HECTAREAS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("20");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.write("</td>");
            // Metros
            out.write("<td>");
            out.write("Metros");
            out.write("</td>");
            out.write("<td>");
            areasHelper.setId(CFolioDerivado.METROS);
            areasHelper.setNombre(CFolioDerivado.METROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("4");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.write("</td>");
            // Centimetros
            out.write("<td>");
            out.write("Centimetros");
            out.write("</td>");
            out.write("<td>");
            areasHelper.setId(CFolioDerivado.CENTIMETROS);
            areasHelper.setNombre(CFolioDerivado.CENTIMETROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("2");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.write("</td>");
            // Porcentaje	
            out.write("<td>");
            helper.setId(CFolioDerivado.PORCENTAJE);
            helper.setNombre(CFolioDerivado.PORCENTAJE);
            helper.setSize("20");
            helper.setFuncion("onblur=javascript:validarNumerico('" + CFolioDerivado.PORCENTAJE + "');");
            helper.render(request, out);
            out.write("</td>");
            out.write("<td align=\"center\" ><input name=\"imageField2\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" alt=\"Agregar Folio Derivado\" border=\"0\"></td>");
            out.write("</tr>");
            out.write("</table>");
            
            out.println("<table width=\"100%\" class=\"camposform\">");
            out.println("<tr>");
            out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.println("<td> Area Privada </td>");
            out.println("<td nowrap> Area Construida</td>");
            out.println("<td> Determinaci&oacute;n del Inmueble </td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td width=\"20\">&nbsp;</td>");
            out.println("<td>");
            out.println("<table class=\"camposformnoborder\">");
            out.println("<tr>");
            out.println("<td> Metros </td>");
            out.println("<td>");
            areasHelper.setId(CFolio.PRIVMETROS);
            areasHelper.setNombre(CFolio.PRIVMETROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("4");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.println("</td>");
            out.println("<td> Centimetros </td>");
            out.println("<td>");
            areasHelper.setId(CFolio.PRIVCENTIMETROS);
            areasHelper.setNombre(CFolio.PRIVCENTIMETROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("2");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("<td>");
            out.println("<table class=\"camposformnoborder\">");
            out.println("<tr>");
            out.println("<td> Metros </td>");
            out.println("<td>");
            areasHelper.setId(CFolio.CONSMETROS);
            areasHelper.setNombre(CFolio.CONSMETROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("4");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.println("</td>");
            out.println("<td> Centimetros </td>");
            out.println("<td>");
            areasHelper.setId(CFolio.CONSCENTIMETROS);
            areasHelper.setNombre(CFolio.CONSCENTIMETROS);
            areasHelper.setSize("20");
            areasHelper.setMaxlength("2");
            areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
            areasHelper.render(request, out);
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("<td>");
            try{
            tipoDetermInmHelper.setNombre(CFolio.DETERMINACION_INMUEBLE);
            tipoDetermInmHelper.setId(CFolio.DETERMINACION_INMUEBLE);
            tipoDetermInmHelper.render(request, out);
            } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
             }
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
                        
                        
                        
            out.write("<table width=\"65%\" class=\"camposformdark\">");
            out.write("<tr>");
            out.write("<td>");
            out.write("Nota de ayuda:");
            out.write("</td>");
            out.write("<td>");
            out.write("&nbsp&nbsp&nbsp&nbsp&nbspTabla de equivalencias");
            out.write("</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>");
            out.write("Para el diligenciamiento de los campos de &aacuterea el usuario debe tener en cuenta lo siguiente:");
            out.write("<table class =\"camposformdnoborder\">");
            out.write("<tr>");
            out.write("<td>1. Para el campo hect&aacutereas, puede ingresar la cantidad de d&iacutegitos que requiera.</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>2. Para el campo metros cuadrados, solo permite ingresar cuatro d&iacutegitos.</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>3. Para el campo centimetros cuadrados, solo permite ingresar dos d&iacutegitos.<br></td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>Para lo anterior se debe tener en cuenta la tabla de equivalencias.</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>Ejemplo: Convertir 50.000 m<sup>2</sup> a hect&aacutereas (Rta: 50.000/10.000 = 5 Hect&aacutereas)</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</td>");
            out.write("<td>");
            out.write("<table class=\"camposformwhite\">");
            out.write("<tr align=\"center\">");
            out.write("<td>&nbsp<strong>Medida</strong>&nbsp</td>");
            out.write("<td>&nbsp<strong>Equivalencia</strong>&nbsp</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>&nbsp 1 hect&aacuterea &nbsp</td>");
            out.write("<td>&nbsp 10.000 m<sup>2</sup>&nbsp</td>");
            out.write("</tr>");
            out.write("<tr align= \"center\">");
            out.write("<td>&nbsp 1 m<sup>2</sup>&nbsp</td>");
            out.write("<td>&nbsp 10.000 cm<sup>2</sup>&nbsp</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</form>");
        }

    }

    /**
     * @param b
     */
    public void setEsVerificacion(boolean b) {
        esVerificacion = b;
    }

    /**
     * @param b
     */
    public void setMostrarMatriculasGeneradas(boolean b) {
        mostrarMatriculasGeneradas = b;
    }

    public boolean fueEditado(String idMatricula, HttpServletRequest request) {
        List idsFolios = (List) request.getSession().getAttribute(WebKeys.LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS);
        if (idsFolios == null) {
            idsFolios = new ArrayList();
        }
        Iterator itFolios = idsFolios.iterator();
        while (itFolios.hasNext()) {
            String idFolio = (String) itFolios.next();
            if (idFolio.equals(idMatricula)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    private List ArrayList() {
        // TODO Auto-generated method stub
        return null;
    }

}
