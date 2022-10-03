package gov.sir.core.web.helpers.registro;

import java.util.List;
import java.util.Vector;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;

import gov.sir.core.web.WebKeys;

import gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema;
import gov.sir.core.web.acciones.comun.AWLocacion;
import gov.sir.core.web.acciones.registro.AWCalificacion;

import gov.sir.core.web.helpers.comun.AntiguoSistemaBasicoHelper;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.TextAreaHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import org.auriga.smart.SMARTKeys;

/**
 * @author mmunoz
 * Esta clase muestras las anotaciones estandar ya creadas y los campos para crear
 * nuevas. Cada anotacion ya existente tiene la posibilidad de ser eliminada y
 * mirar sus detalles en un pop-up
 */
public class AnotacionesFolioHelper extends Helper {
    /**Helper que muestra las areas de texto*/
    private TextAreaHelper textAreaHelper;

    /**Helper que muestra los campos de texto*/
    private TextHelper textHelper;

    /**Helper que muestra los campos de texto*/
    private TextHelper textBlurHelper;

    /**Helper para los campos de texto ocultos*/
    private TextHelper hiddenHelper;

    /**Helper que muestra los combobox de objetos ElementoLista*/
    private ListaElementoHelper tiposDocHelper;

    /**Helper que muestra los ciudadanos asociados a la anotacion
     * se ve la informacion basica, y se da la posibilidad de borrarlos
     */
    private CiudadanosAnotacionHelper ciudadanosHelper;
    
    private boolean mostrarLista=true;

    /** Helper de ciudadanos */
    private Helper variosCiudadanosHelper = null;

    /**Lista donde se guardan las anotaciones ya creadas*/
    private List anotaciones;
    private OficinaHelper oficinaHelper;
    private String action="calificacion.do";

    /**
     * Nombre de la forma asociada
     */
    private String formName;

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        HttpSession session = request.getSession();
        tiposDocHelper = new ListaElementoHelper();

        List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        tiposDocHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
        tiposDocHelper.setFuncion(
            "onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
        tiposDocHelper.setOrdenar(false);
        tiposDocHelper.setTipos(tiposDocs);
        ciudadanosHelper = new CiudadanosAnotacionHelper();
        textHelper = new TextHelper();
        hiddenHelper = new TextHelper();
        hiddenHelper.setTipo("hidden");

        //hiddenHelper.setTipo("text");
        textAreaHelper = new TextAreaHelper();
        textBlurHelper = new TextHelper();

        anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones == null) {
            anotaciones = new Vector();
        }
    }

    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        out.write("<script type=\"text/javascript\">");
        out.write("	function cambiarAccionAndSendTipoTarifa(text) { ");
        out.write("	if(document." + formName + "." +
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID +
            ".value!=\"\"){ ");
        out.write("		document." + formName + ".action = '"+action+"'; ");
        out.write("		document." + formName + ".ACCION.value = text; ");
        out.write("		document." + formName + ".submit(); ");
        out.write("		}");
        out.write("	}");
        out.write("</script>");
        out.write(
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        out.write("<tr>");
        out.write("<td><img src=\"" + request.getContextPath() +
            "/jsp/images/spacer.gif\" width=\"7\" height=\"10\"></td>");
        out.write("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn003.gif\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
        out.write("<td><img src=\"" + request.getContextPath() +
            "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td><img name=\"tabla_central_r1_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn003.gif\">");
        out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn001.gif\" class=\"titulotbcentral\">ANOTACIONES</td>");
        out.write("<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write(
            "<td width=\"20\" align=\"center\" valign=\"top\" background=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_bgn002.gif\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.write("<tr>");
        out.write("<td><img src=\"" + request.getContextPath() +
            "/jsp/images/ico_new.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td><img src=\"" + request.getContextPath() +
            "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("<td><img name=\"tabla_central_pint_r1_c7\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td width=\"7\" background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
        out.write(
            "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
        out.write("<table width=\"100%\" >");
        out.write("<tr>");
        out.write("<td>");

        out.write("<hr class=\"linehorizontal\">");

        out.write("<input type=\"hidden\" name=\"" + CAnotacion.POSICION +
            "\" id=\"" + CAnotacion.POSICION + "\">");

        if (!anotaciones.isEmpty() && mostrarLista) {
            out.write("<table width=\"100%\" class=\"camposform\">");

            int numAnota = anotaciones.size();

            for (int i = 0; i < numAnota; i++) {
                Anotacion anotacion = (Anotacion) anotaciones.get(i);
                out.write("<tr>");
                out.write("<td width=\"20\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                out.write("<td width=\"35\" class=\"campositem\">N&ordm;</td>");
                out.write("<td class=\"campositem\">" + anotacion.getOrden() +
                    "</td>");
                out.write(
                    "<td width=\"40\" align=\"center\"><input type=\"image\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/btn_mini_eliminar.gif\" onClick=\"if(window.confirm('Desea eliminar esta anotación  ?')){quitar('" +
                    i + "','" + AWAntiguoSistema.ELIMINAR_ANOTACION +
                    "');}else{return false;}\" width=\"35\" height=\"13\"></td>");
				out.write(
					"<td width=\"40\" align=\"center\"><a href=\"javascript:editarAnotacion('" +
					anotacion.getOrden() + "')\"> <img src=\"" +
					request.getContextPath() +
					"/jsp/images/btn_mini_editar.gif\" border=\"0\" alt=\"Editar anotaci&oacute;n\"></a></td>");
                out.write("<td width=\"40\" align=\"center\"><a href=\"" +
                    (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL) +
                    "\"><img onClick=\"verAnotacion('ver.anotacion.view?" +
                    CAnotacion.POSICION + "=" + i +
                    "','Anotacion','width=900,height=450,scrollbars=yes','" +
                    i + "')\" src=\"" + request.getContextPath() +
                    "/jsp/images/btn_mini_verdetalles.gif\" border=\"0\" width=\"35\" height=\"13\"></a></td>");
                out.write("</tr>");
            }

            out.write("</table>");
            out.write("<hr class=\"linehorizontal\">");
        }

        /**************************************/
        out.write(
            "<input name=\"id_depto\" type=\"hidden\" id=\"id_depto\" value=\"\">");
        out.write(
            "<input name=\"nom_depto\" type=\"hidden\" id=\"nom_depto\" value=\"\">");
        out.write(
            "<input name=\"id_munic\" type=\"hidden\" id=\"id_munic\" value=\"\">");
        out.write(
            "<input name=\"nom_munic\" type=\"hidden\" id=\"nom_munic\" value=\"\">");
        out.write(
            "<input name=\"id_vereda\" type=\"hidden\" id=\"id_vereda\" value=\"\">");
        out.write(
            "<input name=\"nom_vereda\" type=\"hidden\" id=\"nom_vereda\" value=\"\">");

        /***************************************/
        out.write(
            "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("<td class=\"bgnsub\">Radicacion</td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");

        out.write("<table width=\"100%\" class=\"camposform\">");
        out.write("<tr>");
        out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
            "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"20\" class=\"campositem\">N&ordm;</td>");

        int pos = anotaciones.size() + 1;
        out.write("<td class=\"campositem\">" + pos + "</td>");
        out.write("<td>");
        out.write("</tr>");
        out.write("</table>");

        out.write("</td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>");

        out.write("<table width=\"100%\" class=\"camposform\">");
        out.write("<tr>");

        out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.write("<td>Fecha de Radicación</td>");
        out.write("<td>");
        out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td>");
        
        
            textHelper.setTipo("text");
            textHelper.setFuncion("onBlur=\"reescribirfecha(this.value,this.id)\"");
            textHelper.setNombre(CFolio.ANOTACION_FECHA_RADICACION);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_FECHA_RADICACION);
            textHelper.render(request, out);
            out.write("<td><a href=\"javascript:NewCal('" +
                CFolio.ANOTACION_FECHA_RADICACION +
                "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"21\" height=\"26\" border=\"0\" onClick=\"javascript:Valores('" +
                request.getContextPath() + "')\"></a></tr>");
            out.write("</table>");
            out.write("</td>");
    
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Número de Radicación</td>");
            out.write("<td>");
            out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");
            out.write("<td>");
    
            textHelper.setTipo("text");
            textHelper.setFuncion(" ");
            textHelper.setNombre(CFolio.ANOTACION_NUM_RADICACION);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_NUM_RADICACION);
            textHelper.render(request, out);
            out.write("</table>");
            out.write("</td>");
    
            out.write("</tr>");
            out.write("</table>");
    
            out.write(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");
            out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("<td class=\"bgnsub\">Documento</td>");
            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Datos Basicos </td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td>Tipo</td>");
            out.write("<td>");
    
            textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
            textHelper.setEditable(true);
            textHelper.setFuncion(" ");
            textHelper.render(request, out);
    
            out.write("</td>");
            out.write("<td>");
    
            tiposDocHelper.setId(CFolio.ANOTACION_TIPO_DOCUMENTO);
            tiposDocHelper.setCssClase("camposformtext");
            tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
            tiposDocHelper.render(request, out);
    
            out.write("</td>");
            out.write("<script>" + "document.getElementById('" +
                CSolicitudRegistro.ID_TIPO_ENCABEZADO +
                "').value=document.getElementById('" +
                CFolio.ANOTACION_TIPO_DOCUMENTO + "').value;" +
                "if(document.getElementById('" +
                CSolicitudRegistro.ID_TIPO_ENCABEZADO +
                "').value=='SIN_SELECCIONAR'){" + "	document.getElementById('" +
                CSolicitudRegistro.ID_TIPO_ENCABEZADO + "').value='';" + "}" +
                "</script>");
            out.write("<td>N&uacute;mero</td>");
            out.write("<td>");
    
            textHelper.setTipo("text");
            textHelper.setFuncion(" ");
            textHelper.setNombre(CFolio.ANOTACION_NUM_DOCUMENTO);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_NUM_DOCUMENTO);
            textHelper.render(request, out);
    
            out.write("</td>");
            out.write("<td>Fecha</td>");
            out.write("<td>");
            out.write("<table align=\"center\">");
            out.write("<tr>");
            out.write("<td>");
    
            textHelper.setTipo("text");
            textHelper.setFuncion("onBlur=\"reescribirfecha(this.value,this.id)\"");
            textHelper.setNombre(CFolio.ANOTACION_FECHA_DOCUMENTO);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_FECHA_DOCUMENTO);
            textHelper.render(request, out);
    
            out.write("<td><a href=\"javascript:NewCal('" +
                CFolio.ANOTACION_FECHA_DOCUMENTO +
                "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"21\" height=\"26\" border=\"0\" onClick=\"javascript:Valores('" +
                request.getContextPath() + "')\"> </a>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td><img src=\"" + request.getContextPath() +
                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Oficina de Procedencia </td>");
            out.write("</tr>");
    
            //oficinaHelper.render(request, out);
            out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20%\" align=\"right\">Departamento</td>");
            out.write("<td width=\"25%\">");
    
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setSize("5");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setFuncion(
                "onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO');");
            textHelper.render(request, out);
    
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.setSize("25");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
    
            out.write("</td>");
            out.write("<td width=\"20%\" align=\"right\">Municipio</td>");
            out.write("<td width=\"25%\">");
    
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.setSize("5");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.setFuncion(
                "onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
            textHelper.setReadonly(false);
            textHelper.render(request, out);
    
            textHelper.setSize("25");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
    
            out.write("</td>");
    
            out.write(
                "<td width=\"10%\"><a href=\"javascript:locacion('seleccionar.locacion.do?" +
                AWLocacion.LOCACIONES_CIRCULO + "=','" +
                AWCalificacion.ANOTACION_OFICINA_DOCUMENTO +
                "','width=790,height=320,menubar=no');setTipoOficina();\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_mapcolombia.gif\" alt=\"Permite seleccionar los datos de ubicación\" width=\"21\" height=\"26\" border=\"0\"></a></td>");
            out.write("</tr>");
            out.write("</table>");
    
            hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            hiddenHelper.render(request, out);
    
            hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            hiddenHelper.render(request, out);
    
                
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write(
                "<td width=\"20%\" align=\"right\">Tipo de Oficina Origen</td>");
            out.write("<td width=\"25%\">");
    
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.render(request, out);
    
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("30");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
            out.write("</td>");
    
            out.write("<td width=\"20%\" align=\"right\">Numero</td>");
            out.write("<td width=\"25%\">");
    
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("5");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.setFuncion(" ");
            textHelper.setReadonly(false);
            textHelper.render(request, out);
    
            textHelper.setSize("25");
            textHelper.setCssClase("camposformtext");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.setReadonly(true);
            textHelper.render(request, out);

            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            TextHelper versionHelper = new TextHelper();
            versionHelper.setTipo("hidden");
            versionHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.render(request, out);
    
            out.write("</td>");
    
            out.write(
                "<td width=\"10%\"><a href=\"javascript:oficinas('seleccionar.oficina.do" +
                "','" + AWCalificacion.ANOTACION_OFICINA_DOCUMENTO +
                "','width=800,height=310,menubar=no')\" /> <image src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_tipo_oficina.gif\" alt=\"Permite seleccionar los datos de oficina\" width=\"21\" height=\"26\" border=\"0\"></td>");
    
            //			  /*******************CAMPOS OCULTOS**************************************
            hiddenHelper.setNombre("tipo_nom_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("tipo_nom_oficina");
            hiddenHelper.render(request, out);
    
            hiddenHelper.setNombre("tipo_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("tipo_oficina");
            hiddenHelper.render(request, out);
    
            hiddenHelper.setNombre("numero_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("numero_oficina");
            hiddenHelper.render(request, out);
    
            hiddenHelper.setNombre("id_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("id_oficina");
            hiddenHelper.render(request, out);

            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            hiddenHelper.setNombre("version");
            hiddenHelper.setId("version");
            hiddenHelper.drawGUI(request, out);
    
            //			  /*********************************************************		
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            
            out.write("</td>");
            out.write("</tr>");
    
            /*out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td>");
    
    
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"150\">Departamento</td>");
            out.write("<td width=\"300\"> ID:");
    
    
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setSize("3");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.render(request,out);
    
    
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.setSize("");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.render(request,out);
    
            out.write("</td>");
            out.write("<td><a href=\"javascript:locacion('seleccionar.locacion.do?" + AWLocacion.LOCACIONES_CIRCULO + "=','" + AWFolio.ANOTACION_OFICINA_DOCUMENTO+ "','width=790,height=320,menubar=no');setTipoOficina();\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_mapcolombia.gif\" alt=\"Permite seleccionar departamento, municipio, vereda\" width=\"16\" height=\"21\" border=\"0\"></a></td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td>Municipio</td>");
            out.write("<td> ID:");
    
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.setSize("3");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.render(request,out);
    
            textHelper.setSize("");
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.render(request,out);
    
            out.write("</td>");
            out.write("<td>Vereda</td>");
            out.write("<td> ID:");
    
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            textHelper.setSize("3");
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            textHelper.render(request,out);
    
            textHelper.setSize("");
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            textHelper.render(request,out);
    
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
    
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td>Tipo</td>");
            out.write("<td>");
            out.write("<td>");
    
            ///*******************CAMPOS OCULTOS**************************************
    
            hiddenHelper.setNombre("tipo_nom_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("tipo_nom_oficina");
            hiddenHelper.render(request,out);
    
            hiddenHelper.setNombre("tipo_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("tipo_oficina");
            hiddenHelper.render(request,out);
    
            hiddenHelper.setNombre("numero_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("numero_oficina");
            hiddenHelper.render(request,out);
    
            hiddenHelper.setNombre("id_oficina");
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setId("id_oficina");
            hiddenHelper.render(request,out);
    
            ///*********************************************************
    
            hiddenHelper.setCssClase("camposformtext");
            hiddenHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.render(request,out);
    
    
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("");
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.render(request,out);
    
    
            out.write("<td><a href=\"javascript:oficinas('seleccionar.oficina.do"+ "','" + AWFolio.ANOTACION_OFICINA_DOCUMENTO + "','width=800,height=310,menubar=no')\" /> <image src=\"" + request.getContextPath() + "/jsp/images/ico_tipo_oficina.gif\" alt=\"Permite seleccionar los datos de oficina\" width=\"16\" height=\"21\" border=\"0\"></tr>");
            out.write("</td>");
            out.write("<td>Numero</td>");
            out.write("<td> ID:");
    
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("3");
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.render(request,out);
    
            textHelper.setSize("");
            textHelper.setCssClase("camposformtext");
            textHelper.setNombre(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.setId(AWFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.render(request,out);
    
            out.write("</td>");
            out.write("<td>&nbsp;</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("</td>");
            out.write("</tr>");*/
            out.write("</table>");
            
            out.write(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");
            out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("<td class=\"bgnsub\">Especificaci&oacute;n</td>");
            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("</tr>");
            out.write("</table>");
            
            
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Valor</td>");
            out.write("<td>");
            textHelper.setSize("");
            textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
            textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
            textHelper.setReadonly(false);
            textHelper.render(request, out);
            textHelper.setFuncion("");
    
            out.write("</td>");
    
            //out.write("</tr>");
            //out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td>Naturaleza Jur&iacute;dica </td>");
            out.write("<td>");
    
            /*out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("<tr>");*/
            out.write(
                "<input name=\"natjuridica_id\" type=\"hidden\" id=\"natjuridica_id\" value=\"\">");
            out.write(
                "<input name=\"natjuridica_nom\" type=\"hidden\" id=\"natjuridica_nom\" value=\"\">");
             
                /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se agrega la variable versionNatJuridica para optener la version de la naturalesa juridica seleccionada
                */

            out.write("<input name=\"natjuridica_ver\" type=\"hidden\" id=\"natjuridica_ver\" value=\"\">");
            out.write("<input name=\""+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER+"\" type=\"hidden\" id=\""+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER+"\" value=\""+request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)+"\">");

            out.write("<td> ID:");
    
            textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("3");
            textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('" +
                AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO + "')\"");
            textHelper.render(request, out);
            out.write("</td>");
    
            out.write("<td> Descripción:");
            textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            textHelper.setCssClase("camposformtext");
            textHelper.setSize("50");
            textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            textHelper.render(request, out);
    
            /*textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            textAreaHelper.setCols("50");
            textAreaHelper.setRows("5");
            textAreaHelper.setCssClase("camposformtext");
            textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            textAreaHelper.render(request,out); */
            out.write(
                "<td><a href=\"javascript:juridica('seleccionar-naturaleza-juridica.do?" +WebKeys.ACCION + "=" + CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION+ "','" +
                CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION +
                "','width=800,height=350,menubar=no')\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_nat_juridica.gif\" alt=\"Permite seleccionar la naturaleza jurídica\" width=\"21\" height=\"26\" border=\"0\" onClick=\"javascript:Valores('" +
                request.getContextPath() + "')\"></a>");
    
            /*out.write("</tr>");
            out.write("</table>");*/
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("<td>Comentario</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td>&nbsp;</td>");
            out.write("<td>");
    
            textAreaHelper.setNombre(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
            textAreaHelper.setCols("50");
            textAreaHelper.setRows("5");
            textAreaHelper.setCssClase("camposformtext");
            textAreaHelper.setId(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
            textAreaHelper.render(request, out);
    
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            
            
                AntiguoSistemaBasicoHelper antSistHelper = new AntiguoSistemaBasicoHelper();
    
                try {
                    antSistHelper.render(request, out);
                } catch (HelperException re) {
                    out.println("ERROR " + re.getMessage());
                }
    
                out.write("<br>");
    
                if (this.variosCiudadanosHelper != null) {
                    this.variosCiudadanosHelper.render(request, out);
                } else {
                    ciudadanosHelper.render(request, out);
                }
	        if(request != null && request.getSession() != null && request.getSession().getAttribute(WebKeys.ROL) != null){
	        	Rol rol =(Rol)request.getSession().getAttribute(WebKeys.ROL);
	        	if(rol != null && rol.getNombre() != null && rol.getNombre().equals(CRol.SIR_ROL_BUSCADOR_AS)){
	        		
	        	}
	        }
        

        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");        
        out.println("</td>");
        
       
        
        
        out.println("<td width=\"11\" background=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
        out.println("</tr>");
        
        
        ////////////////////////////////////////////
        
        if(request != null && request.getSession() != null && request.getSession().getAttribute(WebKeys.ROL) != null){
        	Rol rol =(Rol)request.getSession().getAttribute(WebKeys.ROL);
        	if(rol != null && rol.getNombre() != null && rol.getNombre().equals(CRol.SIR_ROL_BUSCADOR_AS)){
        		
        		String strMatriculasP = request.getParameter("textmatriculaPadre");
        		String strAnotacionesP = request.getParameter("textanotacionPadre");
        		        
        		
				///////para las matriculas Padre
        		out.println("<tr>");
                out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
                out.println(
                    "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
                out.println(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                out.println("<tr>");
                out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("<td class=\"bgnsub\">Asociar Matricula Padre</td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("</tr>");
                out.println("</table>");
                
                //request.getSession().getAttribute(arg0)
                out.println("<table width=\"100%\" class=\"camposform\">");                
               
                out.println("<tr>");
                out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
                    "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
                      
                out.write("<td>Matricula</td>");
                out.write("<td>");
//                textHelper.setSize("");
//                textHelper.setNombre("textmatriculaPadre");
//                textHelper.setCssClase("camposformtext");
//                textHelper.setId("textmatriculaPadre");
//                
//                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//                textHelper.setReadonly(false);                
//                textHelper.setFuncion("");
//                textHelper.render(request, out);
                
                String Nombre = "", cssClase ="", Id ="", valor = "";
                
                Nombre = "textmatriculaPadre";
                cssClase ="camposformtext";
                Id ="textmatriculaPadre";
                if(strMatriculasP!= null)
                	valor = strMatriculasP;
                
                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
                out.println("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
							" class=\"" + cssClase + "\" >");

                out.write("</td>");
                
                out.write("<td>Anotacion</td>");
                out.write("<td>");
                
                Nombre = "textanotacionPadre";
                cssClase ="camposformtext";
                Id ="textanotacionPadre";
              
                if(strAnotacionesP != null)
                	valor = strAnotacionesP;
                else
                	valor = "";
                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
                out.println("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
							" class=\"" + cssClase + "\" >");

                out.write("</td>");
                
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                               
	            out.println("<tr>");
	            out.println("<td>&nbsp;</td>");
	            out.println("</tr>");
	            out.println("</table>");
	            out.println("</td>");
	            
	            
	            
	            
	            out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
                out.println("</tr>");
                
        		
        		///////para las matriculas hija
        		out.println("<tr>");
                out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
                out.println(
                    "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
                out.println(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                out.println("<tr>");
                out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("<td class=\"bgnsub\">Asociar Matriculas Hijas</td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("</tr>");
                out.println("</table>");
                
                //request.getSession().getAttribute(arg0)
                Integer nuMatriculas = new Integer(1);
                if( request.getSession().getAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS) != null){
                	nuMatriculas = (Integer) request.getSession().getAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS);
                }else{
                	request.getSession().setAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS,nuMatriculas);
                }
                
                String[] strVectorMatriculas = request.getParameterValues("textmatriculahija");
                String[] strVectorAnotaciones = request.getParameterValues("textanotacionhija");
                
                out.println("<table width=\"100%\" class=\"camposform\">");                
                for(int i=0;i<nuMatriculas.intValue();i++){
	               
	                out.println("<tr>");
	                out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
	                    "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
	                      
	                out.write("<td>Matricula</td>");
	                out.write("<td>");
//	                textHelper.setSize("");
//	                textHelper.setNombre("textmatriculahija");
//	                textHelper.setCssClase("camposformtext");
//	                textHelper.setId("textmatriculahija");
//	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//	                textHelper.setReadonly(false);	                
//	                textHelper.setFuncion("");
//	                textHelper.render(request, out);
	                
	                
	                Nombre = "textmatriculahija";
	                cssClase ="camposformtext";
	                Id ="textmatriculahija";
	                if(strVectorMatriculas != null && i<strVectorMatriculas.length)
	                	valor = strVectorMatriculas[i];
	                else
	                	valor = "";
	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
	                out.write("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
								" class=\"" + cssClase + "\" >");
	                
	
	                out.write("</td>");
	                
	                out.write("<td>Anotacion</td>");
	                out.write("<td>");
//	                textHelper.setSize("");
//	                textHelper.setNombre("textanotacionhija");
//	                textHelper.setCssClase("camposformtext");
//	                textHelper.setId("textanotacionhija");
//	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//	                textHelper.setReadonly(false);	               
//	                textHelper.setFuncion("");
//	                textHelper.render(request, out);
	                Nombre = "textanotacionhija";
	                cssClase ="camposformtext";
	                Id ="textanotacionhija";
	                if(strVectorAnotaciones != null && i<strVectorAnotaciones.length)
	                	valor = strVectorAnotaciones[i];
	                else
	                	valor = "";
	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
	                out.write("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
								" class=\"" + cssClase + "\" >");
	
	                out.write("</td>");
	                
	                
	               /* out.println("<td width=\"150\"><input type =\"image\" src=\"" +
	                    request.getContextPath() +
	                    "/jsp/images/btn_agregar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\" onClick=\"cambiarAccion('" +
	                    AWAntiguoSistema.AGREGAR_MATRICULA_ASO + "')\"></td>");*/
	
	                out.println("<td>&nbsp;</td>");
	                out.println("</tr>");
	                        
	                
                }
                
                               
	            out.println("<tr>");
	
	            //aumentar un registro
	            out.println("<td><input type =\"image\" src=\"" + request.getContextPath() +
	                "/jsp/images/sub_btn_agregar.gif\" name=\"agregarMatricula\"  alt=\"Agregar un registro más\" width=\"18\" height=\"14\" border=\"0\"   onClick=\"cambiarAccion('" +
	                    AWAntiguoSistema.AGREGAR_MATRICULA_ASO + "')\"></td>");
	            //out.println("<td width=\"5\">&nbsp;</td>");
	
	            //disminuir un registro
	            out.println("<td><input type =\"image\" src=\"" + request.getContextPath() +
	                "/jsp/images/sub_btn_eliminar.gif\" name=\"removerMatricula\"  alt=\"Eliminar un Registro\" width=\"18\" height=\"14\" border=\"0\" onClick=\"cambiarAccion('" +
	                    AWAntiguoSistema.BORRAR_MATRICULA_ASO + "')\"></td>");
	            
	            out.println("<td>&nbsp;</td>");
	            
	            out.println("</tr>");
	            
	            
	            out.println("</table>");
	            out.println("</td>");
	            
	            
                
                
                
                
                
                out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
                out.println("</tr>");
                
                
                
                ////////////////////////////////////////////
        	}
        }
        
        
        
        
        
        
        out.println("<tr>");
        out.println("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
        out.println(
            "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
        out.println(
            "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.println("<tr>");
        out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.println("<td class=\"bgnsub\">Agregar Anotacion </td>");
        out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
        out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
        out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.println("</tr>");
        out.println("</table>");
        
        out.println("<table width=\"100%\" class=\"camposform\">");
        out.println("<tr>");
        out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
        out.println("<td width=\"150\"><input type =\"image\" src=\"" +
            request.getContextPath() +
            "/jsp/images/btn_agregar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\" onClick=\"cambiarAccion('" +
            AWAntiguoSistema.AGREGAR_ANOTACION + "')\"></td>");
        out.println("<td>&nbsp;</td>");
        out.println("</tr>");
        out.println("</table>");        
        out.println("</td>");
        
        out.println("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
        out.println("</tr>");
        out.println("<tr>");
        
        out.println("<td><img name=\"tabla_central_r3_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("<td background=\"" + request.getContextPath() +
            "/jsp/images/tabla_central_bgn006.gif\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
        out.println("<td><img name=\"tabla_central_pint_r3_c7\" src=\"" +
            request.getContextPath() +
            "/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("</tr>");
        
        out.println("</table>");
    }

    /**
     * @return
     */
    public String getFormName() {
        return formName;
    }

    /**
     * @param string
     */
    public void setFormName(String string) {
        formName = string;
    }

    /**
     * Obtener el atributo variosCiudadanosHelper
     *
     * @return Retorna el atributo variosCiudadanosHelper.
     */
    public Helper getVariosCiudadanosHelper() {
        return variosCiudadanosHelper;
    }

    /**
     * Actualizar el valor del atributo variosCiudadanosHelper
     * @param variosCiudadanosHelper El nuevo valor del atributo variosCiudadanosHelper.
     */
    public void setVariosCiudadanosHelper(Helper variosCiudadanosHelper) {
        this.variosCiudadanosHelper = variosCiudadanosHelper;
    }

    public void setMostrarLista(boolean mostrarLista) {
        this.mostrarLista = mostrarLista;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
