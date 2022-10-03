package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.registro.OficinaHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;


/**
 * @author ddiaz
 * Helper donde se ingresan los datos de antiguo sistema.
 */
public class AntiguoSistemaHelper extends Helper {

   private boolean local_OficinasHelperCambioNombreFormularioEnabled = false;
   private String  local_OficinasHelperCambioNombreFormularioValue = "REGISTRO";

    TextHelper textHelper = null;
    OficinaHelper oficinaHelper = null;
    ListaElementoHelper tipoDocumento = new ListaElementoHelper();
    private String nLibroTipoAS = CDatosAntiguoSistema.LIBRO_TIPO_AS;
    private String nLibroNumeroAS = CDatosAntiguoSistema.LIBRO_NUMERO_AS;
    private String nLibroPaginaAS = CDatosAntiguoSistema.LIBRO_PAGINA_AS;
    private String nLibroAnoAS = CDatosAntiguoSistema.LIBRO_ANO_AS;
    private String nTomoMunicipioAS = CDatosAntiguoSistema.TOMO_MUNICIPIO_AS;
    private String nTomoNumeroAS = CDatosAntiguoSistema.TOMO_NUMERO_AS;
    private String nTomoPaginaAS = CDatosAntiguoSistema.TOMO_PAGINA_AS;
    private String nTomoAnoAS = CDatosAntiguoSistema.TOMO_ANO_AS;
    private String nDocumentoTipoAS = CDatosAntiguoSistema.DOCUMENTO_TIPO_AS;
    private String nListaTipoAS = WebKeys.LISTA_TIPOS_DOCUMENTO;
    private String nDocumentoNumeroAS = CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS;
    private String nDocumentoFechaAS = CDatosAntiguoSistema.DOCUMENTO_FECHA_AS;
    private String nDocumentoComentarioAS = CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS;
    private String nComentarioAS = CDatosAntiguoSistema.COMENTARIO_AS;
    private String nComboTiposDocumentoAS = CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS;
	private String styleRead = "campositem";
    boolean obtenerJSP;
    boolean readOnly = false;
	String style = "camposformtext";

    public AntiguoSistemaHelper() {
        super();
        oficinaHelper = new OficinaHelper();
        textHelper = new TextHelper();
    }

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        if (this.obtenerJSP) {
            oficinaHelper.obtenerDelJSP(true);
        } else {
            this.nLibroTipoAS = CDatosAntiguoSistema.LIBRO_TIPO_AS;
            this.nLibroNumeroAS = CDatosAntiguoSistema.LIBRO_NUMERO_AS;
            this.nLibroPaginaAS = CDatosAntiguoSistema.LIBRO_PAGINA_AS;
            this.nLibroAnoAS = CDatosAntiguoSistema.LIBRO_ANO_AS;
            this.nTomoMunicipioAS = CDatosAntiguoSistema.TOMO_MUNICIPIO_AS;
            this.nTomoNumeroAS = CDatosAntiguoSistema.TOMO_NUMERO_AS;
            this.nTomoPaginaAS = CDatosAntiguoSistema.TOMO_PAGINA_AS;
            this.nTomoAnoAS = CDatosAntiguoSistema.TOMO_ANO_AS;
            this.nDocumentoTipoAS = CDatosAntiguoSistema.DOCUMENTO_TIPO_AS;
            this.nListaTipoAS = WebKeys.LISTA_TIPOS_DOCUMENTO;
            this.nDocumentoNumeroAS = CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS;
            this.nDocumentoFechaAS = CDatosAntiguoSistema.DOCUMENTO_FECHA_AS;
            this.nDocumentoComentarioAS = CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS;
            this.nComentarioAS = CDatosAntiguoSistema.COMENTARIO_AS;
        }
    }

    /**
     * @param comentarioAS The nComentarioAS to set.
     */
    public void setNComentarioAS(String comentarioAS) {
        nComentarioAS = comentarioAS;
    }

    /**
     * @param documentoComentarioAS The nDocumentoComentarioAS to set.
     */
    public void setNDocumentoComentarioAS(String documentoComentarioAS) {
        nDocumentoComentarioAS = documentoComentarioAS;
    }

    /**
     * @param documentoFechaAS The nDocumentoFechaAS to set.
     */
    public void setNDocumentoFechaAS(String documentoFechaAS) {
        nDocumentoFechaAS = documentoFechaAS;
    }

    /**
     * @param documentoNumeroAS The nDocumentoNumeroAS to set.
     */
    public void setNDocumentoNumeroAS(String documentoNumeroAS) {
        nDocumentoNumeroAS = documentoNumeroAS;
    }

    /**
     * @param documentoTipoAS The nDocumentoTipoAS to set.
     */
    public void setNDocumentoTipoAS(String documentoTipoAS) {
        nDocumentoTipoAS = documentoTipoAS;
    }

    /**
     * @param libroAnoAS The nLibroAnoAS to set.
     */
    public void setNLibroAnoAS(String libroAnoAS) {
        nLibroAnoAS = libroAnoAS;
    }

    /**
     * @param libroNumeroAS The nLibroNumeroAS to set.
     */
    public void setNLibroNumeroAS(String libroNumeroAS) {
        nLibroNumeroAS = libroNumeroAS;
    }

    /**
     * @param libroPaginaAS The nLibroPaginaAS to set.
     */
    public void setNLibroPaginaAS(String libroPaginaAS) {
        nLibroPaginaAS = libroPaginaAS;
    }

    /**
     * @param libroTipoAS The nLibroTipoAS to set.
     */
    public void setNLibroTipoAS(String libroTipoAS) {
        nLibroTipoAS = libroTipoAS;
    }

    /**
     * @param listaTipoAS The nListaTipoAS to set.
     */
    public void setNListaTipoAS(String listaTipoAS) {
        nListaTipoAS = listaTipoAS;
    }

    /**
     * @param tomoAnoAS The nTomoAnoAS to set.
     */
    public void setNTomoAnoAS(String tomoAnoAS) {
        nTomoAnoAS = tomoAnoAS;
    }

    /**
     * @param tomoMunicipioAS The nTomoMunicipioAS to set.
     */
    public void setNTomoMunicipioAS(String tomoMunicipioAS) {
        nTomoMunicipioAS = tomoMunicipioAS;
    }

    /**
     * @param tomoNumeroAS The nTomoNumeroAS to set.
     */
    public void setNTomoNumeroAS(String tomoNumeroAS) {
        nTomoNumeroAS = tomoNumeroAS;
    }

    /**
     * @param tomoPaginaAS The nTomoPaginaAS to set.
     */
    public void setNTomoPaginaAS(String tomoPaginaAS) {
        nTomoPaginaAS = tomoPaginaAS;
    }

    /**
     * @param obtenerJSP The obtenerJSP to set.
     */
    public void setObtenerJSP(boolean obtenerJSP) {
        this.obtenerJSP = obtenerJSP;
    }

    /**
     * @param oficinaHelper The oficinaHelper to set.
     */
    public void setOficinaHelper(OficinaHelper oficinaHelper) {
        this.oficinaHelper = oficinaHelper;
    }

    /**
     * @param textHelper The textHelper to set.
     */
    public void setTextHelper(TextHelper textHelper) {
        this.textHelper = textHelper;
    }

    /**
     * @param tipoDocumento The tipoDocumento to set.
     */
    public void setTipoDocumento(ListaElementoHelper tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdDepartamento(java.lang.String)
     */
    public void setNIdDepartamento(String nIdDepartamento) {
        oficinaHelper.setNIdDepartamento(nIdDepartamento);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdDocumento(java.lang.String)
     */
    public void setNIdDocumento(String nIdDocumento) {
        oficinaHelper.setNIdDocumento(nIdDocumento);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdMunicipio(java.lang.String)
     */
    public void setNIdMunicipio(String nIdMunicipio) {
        oficinaHelper.setNIdMunicipio(nIdMunicipio);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdOficina(java.lang.String)
     */
    public void setNIdOficina(String nIdOficina) {
        oficinaHelper.setNIdOficina(nIdOficina);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdOficinaHidden(java.lang.String)
     */
    public void setNIdOficinaHidden(String nIdOficinaHidden) {
        oficinaHelper.setNIdOficinaHidden(nIdOficinaHidden);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNIdVereda(java.lang.String)
     */
    public void setNIdVereda(String nIdVereda) {
        oficinaHelper.setNIdVereda(nIdVereda);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNomDepartamento(java.lang.String)
     */
    public void setNNomDepartamento(String nNomDepartamento) {
        oficinaHelper.setNNomDepartamento(nNomDepartamento);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNomDocumento(java.lang.String)
     */
    public void setNNomDocumento(String nNomDocumento) {
        oficinaHelper.setNNomDocumento(nNomDocumento);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNomMunicipio(java.lang.String)
     */
    public void setNNomMunicipio(String nNomMunicipio) {
        oficinaHelper.setNNomMunicipio(nNomMunicipio);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNomVereda(java.lang.String)
     */
    public void setNNomVereda(String nNomVereda) {
        oficinaHelper.setNNomVereda(nNomVereda);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNumOficina(java.lang.String)
     */
    public void setNNumOficina(String nNumOficina) {
        oficinaHelper.setNNumOficina(nNumOficina);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNNumOficinaHidden(java.lang.String)
     */
    public void setNNumOficinaHidden(String nNumOficinaHidden) {
        oficinaHelper.setNNumOficinaHidden(nNumOficinaHidden);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNTipoNomOficinaHidden(java.lang.String)
     */
    public void setNTipoNomOficinaHidden(String nTipoNomOficinaHidden) {
        oficinaHelper.setNTipoNomOficinaHidden(nTipoNomOficinaHidden);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNTipoOficinaHiddenn(java.lang.String)
     */
    public void setNTipoOficinaHiddenn(String nTipoOficinaHidden) {
        oficinaHelper.setNTipoOficinaHiddenn(nTipoOficinaHidden);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.registro.OficinaHelper#setNPrefijoOficinaHiddenn(java.lang.String)
     */
    public void setNPrefijoOficina(String nPrefijoOficina) {
        oficinaHelper.setNPrefijoOficina(nPrefijoOficina);
    }

    /**
     * Este método pinta en la pantalla de manera agradable un fomulario para digitar los datos de Antiguo Sistema
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        out.println("<script>\n");
        out.println("function cambiarValorTipoDocumentoAS(text) {\n");
        out.println("		try{\n");
        out.println(
            "			document.getElementById('"+this.nComboTiposDocumentoAS+"').options[text].selected=true;\n");
        out.println("		}catch(e){\n");
        out.println("			document.getElementById('"+this.nComboTiposDocumentoAS+"').value='" +
            WebKeys.SIN_SELECCIONAR + "';\n");
        out.println("			document.getElementById('" + this.nDocumentoTipoAS +
            "').value='';\n");
        out.println("		}\n");
        out.println("}\n");
        out.println("</script>\n");

        out.println("<table width=\"100%\" class=\"camposform\">");
        out.println("		<tr>");
        out.println("			<td><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println(
            "			<td width=\"25%\" class=\"titulotbcentral\" align=\"left\">Libro</td>");
        out.println("			<td width=\"28%\">&nbsp;</td>");
        out.println("			<td width=\"22%\">&nbsp;</td>");
        out.println("			<td width=\"25%\">&nbsp;</td>");
        out.println("		</tr>");
        out.println("		<tr>");
        out.println("			<td>&nbsp;</td>    ");
        out.println("			<td>Tipo</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nLibroTipoAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nLibroTipoAS);
			textHelper.setReadonly(this.readOnly);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("			<td>N&uacute;mero-Letra</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nLibroNumeroAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nLibroNumeroAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("		</tr>");
        out.println("		<tr>");
        out.println("			<td>&nbsp;</td>");
        out.println("			<td>Pagina</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nLibroPaginaAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nLibroPaginaAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("			<td>A&ntilde;o</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nLibroAnoAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nLibroAnoAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("		</tr>");
        out.println("	</table>");

        out.println("	<table width=\"100%\" class=\"camposform\">");
        out.println("		<tr> ");
        out.println("			<td><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println(
            "			<td width=\"25%\" class=\"titulotbcentral\" align=\"left\">Tomo</td>");
        out.println("			<td width=\"28%\">&nbsp;</td>");
        out.println("			<td width=\"22%\">&nbsp;</td>");
        out.println("			<td width=\"25%\">&nbsp;</td>");
        out.println("		</tr>");
        out.println("		<tr>");
        out.println("			<td>&nbsp;</td>");
        out.println("			<td>N&uacute;mero</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nTomoNumeroAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nTomoNumeroAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("			<td>Pagina</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nTomoPaginaAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nTomoPaginaAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("		</tr>");
        out.println("		<tr>");
        out.println("			<td>&nbsp;</td>");
        out.println("			<td>Municipio</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nTomoMunicipioAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nTomoMunicipioAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("			<td>A&ntilde;o</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nTomoAnoAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nTomoAnoAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("		</tr>");
        out.println("	</table>");

        //TABLA DEL DOCUMENTO
        //TODO Eliminar el código que renderiza la tabla de "Datos del documento" si SNR está conforme con el cambio. Requerimiento 020_141
        out.println("	<table style=\"display:none\" width=\"100%\" class=\"camposform\"> ");
        out.println("		<tr>");
        out.println("			<td><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println(
            "			<td colspan=\"2\" class=\"titulotbcentral\" align=\"left\">Datos del documento</td>");
        out.println("			<td>&nbsp;</td>");
        out.println("			<td>&nbsp;</td>");
        out.println("		</tr>");

        out.println("						  <tr>");
        out.println("							<td>&nbsp;</td>");
        out.println("							<td>Tipo</td>");
        out.println("						<td>");

        try {
            textHelper.setNombre(this.nDocumentoTipoAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nDocumentoTipoAS);
            textHelper.setSize("5%");
            textHelper.setFuncion(
                "onchange='javascript:cambiarValorTipoDocumentoAS(this.value)'");
            textHelper.render(request, out);
            textHelper.setSize("");
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        textHelper.setFuncion("");

        out.println("								</td>");
        out.println("<td>");

        try {
            List tiposDoc = (List) request.getSession().getServletContext()
                                          .getAttribute(this.nListaTipoAS);
            tipoDocumento.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);

            if(this.readOnly){
				String stringTipoEncabezado = (String) request.getSession().getAttribute(this.nComboTiposDocumentoAS);
            	if(stringTipoEncabezado!=null){
					tipoDocumento.setFuncion("onchange=\"this.value = '"+(stringTipoEncabezado)+"';\"");
            	}
            }else{
				tipoDocumento.setFuncion("onchange=getElementById('" +
					this.nDocumentoTipoAS + "').value=this.value;");
            }

            tipoDocumento.setTipos(tiposDoc);
            tipoDocumento.setNombre(this.nComboTiposDocumentoAS);//"TIPO_ENCABEZADO"
            tipoDocumento.setCssClase(style);
            tipoDocumento.setId(this.nComboTiposDocumentoAS);
            tipoDocumento.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("</td>");
        out.println("			<td>N&uacute;mero</td>");
        out.println("			<td>");

        try {
            textHelper.setNombre(this.nDocumentoNumeroAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nDocumentoNumeroAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("									</td>");
        out.println("			<td>Fecha</td>");
        out.println(
            "			<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.println("				<tr>");
        out.println("				  <td>");

        try {
            textHelper.setNombre(this.nDocumentoFechaAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nDocumentoFechaAS);
            textHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("  <td><a href=\"javascript:NewCal('" +
            this.nDocumentoFechaAS + "','ddmmmyyyy',true,24)\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" +
            request.getContextPath() + "')\"></a>");
        out.println("				</tr>");
        out.println("			</table>	</td>");

        out.println("		</tr>");
        out.println("		<tr>");
        out.println("			<td>&nbsp;</td>");
        out.println("			<td>Comentario</td>");
        out.println("			<td colspan=\"3\">");

        try {
            textHelper.setNombre(this.nDocumentoComentarioAS);
            textHelper.setCssClase(style);
            textHelper.setId(this.nDocumentoComentarioAS);
            textHelper.setSize("44");
            textHelper.render(request, out);
            textHelper.setSize("30");
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td>");
        out.println("		 </tr>");
        out.println("	</table>");

        /*

        out.println("        <table width=\"100%\" class=\"camposform\"> ");
        out.println("                <tr>");
        out.println("                        <td><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println("                        <td colspan=\"2\" class=\"titulotbcentral\" align=\"left\">Datos del documento</td>");
        out.println("                        <td>&nbsp;</td>");
        out.println("                        <td>&nbsp;</td>");
        out.println("                </tr>");
        out.println("                <tr>");
        out.println("                        <td >&nbsp;</td>");
        out.println("                        <td width=\"18%\">Tipo</td>");
        out.println("                        <td width=\"35%\">");

                                                try {
                                                        textHelper.setNombre(this.nDocumentoTipoAS);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(this.nDocumentoTipoAS);
                                                        textHelper.setSize("5%");
                                                        textHelper.setFuncion("onchange='javascript:cambiarValorTipoDocumento(this.value)'");
                                                        textHelper.render(request,out);
                                                        textHelper.setSize("");
                                                }
                                                        catch(HelperException re){
                                                        out.println("ERROR " + re.getMessage());
                                                }
                                                textHelper.setFuncion("");

                                                try {
                                                        List tiposDoc = (List) request.getSession().getServletContext().getAttribute(this.nListaTipoAS);
                                                        tipoDocumento.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
                                                        tipoDocumento.setFuncion("onchange=getElementById('"+this.nDocumentoTipoAS+"').value=this.value;");
                                                        tipoDocumento.setTipos(tiposDoc);
                                                        tipoDocumento.setNombre("TIPO_ENCABEZADO");
                                                        tipoDocumento.setCssClase("camposformtext");
                                                        tipoDocumento.setId("TIPO_ENCABEZADO");
                                                        tipoDocumento.render(request,out);
                                                }
                                                        catch(HelperException re){
                                                        out.println("ERROR " + re.getMessage());
                                                }

                out.println("                        </td>");


        out.println("                        <td width=\"22%\">N&uacute;mero</td>");
        out.println("                        <td width=\"25%\">");

                                                try {
                                                        textHelper.setNombre(this.nDocumentoNumeroAS);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(this.nDocumentoNumeroAS);
                                                        textHelper.render(request,out);
                                                }
                                                        catch(HelperException re){
                                                        out.println("ERROR " + re.getMessage());
                                                }

        out.println("                        </td>");
        out.println("                </tr>");

        out.println("                <tr>");
        out.println("                        <td>&nbsp;</td>");
        out.println("                        <td>Fecha</td>");
        out.println("                        <td>");
                                                try {
                                                        textHelper.setNombre(this.nDocumentoFechaAS);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(this.nDocumentoFechaAS);
                                                        textHelper.render(request,out);
                                                }
                                                        catch(HelperException re){
                                                        out.println("ERROR " + re.getMessage());
                                                }
        out.println("                        <a href=\"javascript:NewCal('"+this.nDocumentoFechaAS+"','ddmmmyyyy',true,24)\"><img src=\""+request.getContextPath()+"/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('"+request.getContextPath()+"')\"></a>");
        out.println("                        </td>");
        out.println("                        <td>Comentario</td>");
        out.println("                        <td>");

                                                try {
                                                        textHelper.setNombre(this.nDocumentoComentarioAS);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(this.nDocumentoComentarioAS);
                                                        textHelper.setSize("43");
                                                        textHelper.render(request,out);
                                                        textHelper.setSize("30");
                                                }
                                                        catch(HelperException re){
                                                        out.println("ERROR " + re.getMessage());
                                                }

        out.println("                        </td>");
        out.println("                </tr>");
        out.println("        </table>");
        */
        //TODO Eliminar el código que renderiza la tabla de "Oficina de origen" si SNR está conforme con el cambio. Requerimiento 020_141
        out.println("	<table  style=\"display:none\" width=\"100%\" class=\"camposform\">");
        out.println("		<tr>");
        out.println("			<td width=\"3%\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println(
            "			<td class=\"titulotbcentral\">Oficina de origen (Documento)</td>");
        out.println("		</tr>");
        out.println("		<tr>");

        try {
        	if(this.readOnly){
        		oficinaHelper.setReadOnly(this.readOnly);
        	}


           if( local_OficinasHelperCambioNombreFormularioEnabled ) {
              oficinaHelper.setLocal_NombreFormulario( getLocal_OficinasHelperCambioNombreFormularioValue() );

           };



            oficinaHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("		</tr>");
        out.println("	</table> ");


        out.println("	<table width=\"100%\" class=\"camposform\">");
        out.println("		<tr>");
        out.println("			<td width=\"20\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>   ");
        out.println(
            "			<td class=\"titulotbcentral\"> Comentario (Datos Antiguo Sistema)</td>   ");
        out.println("		</tr> ");
        out.println("		<tr> ");
        out.println("			<td width=\"20\">&nbsp;</td>   ");
        out.println("			<td> ");

        try {
            TextAreaHelper areaHelper = new TextAreaHelper();
            areaHelper.setNombre(this.nComentarioAS);
            areaHelper.setCssClase(style);
            areaHelper.setId(this.nComentarioAS);
            areaHelper.setCols("120");
            areaHelper.setRows("4");
            areaHelper.setReadOnly(this.readOnly);
            areaHelper.render(request, out);
        } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        out.println("			</td> ");
        out.println("		</tr> ");
        out.println("</table> ");
    }

    /**
     * Actualizar el valor del atributo nComboTiposDocumentoAS
     * @param comboTiposDocumentoAS El nuevo valor del atributo nComboTiposDocumentoAS.
     */
    public void setNComboTiposDocumentoAS(String comboTiposDocumentoAS) {
        nComboTiposDocumentoAS = comboTiposDocumentoAS;
    }

	/**
	 * @param b
	 */
	public void setReadOnly(boolean b) {
		readOnly = b;
		if(readOnly){
			style = styleRead;
		}
	}

	public void setLocal_OficinasHelperCambioNombreFormularioValue(String
		 local_OficinasHelperCambioNombreFormularioValue) {
		this.local_OficinasHelperCambioNombreFormularioValue =
			 local_OficinasHelperCambioNombreFormularioValue;
	}

	public void setLocal_OficinasHelperCambioNombreFormularioEnabled(boolean
		 local_OficinasHelperCambioNombreFormularioEnabled) {
		this.local_OficinasHelperCambioNombreFormularioEnabled =
			 local_OficinasHelperCambioNombreFormularioEnabled;
	}

	public String getLocal_OficinasHelperCambioNombreFormularioValue() {
		return local_OficinasHelperCambioNombreFormularioValue;
	}

	public boolean isLocal_OficinasHelperCambioNombreFormularioEnabled() {
		return local_OficinasHelperCambioNombreFormularioEnabled;
	}

}
