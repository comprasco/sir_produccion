package gov.sir.core.web.helpers.comun;

import com.versant.core.dd;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWNaturalezaJuridica;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;


/**
 * @author mmunoz
 */
public class SeleccionarNaturalezaJuridicaHelper extends Helper {
    private static final String GRUPO_NATURALEZA_JURIDICA_ID = "GRUPO_NATURALEZA_JURIDICA_ID";
    public static final String NATURALEZA_JURIDICA_ID = "NATURALEZA_JURIDICA_ID";
    private static final String NATURALEZA_JURIDICA_NOM = "NATURALEZA_JURIDICA_NOM";
            /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */

    private static final String VERSION = "VERSION";
    private ListaElementoHelper gruposHelper;
    private ListaElementoHelper naturalezaHelper;
    private String idGrupoNat;
    private String idNat;
    private String nomGrupo;
    private String nomNaturaleza;
    private boolean mostrarBoton = false;
    private boolean mostrarCancelacion = true;
    private boolean mostrarSoloCancelacion = true;
    private boolean mostrarAntiguas = true; //bandera que indica si se muestran las naturalezas juridicas antiguas (3 dígitos)
    private boolean mostrarNuevas = true; //bandera que indica si se muestran las naturalezas juridicas actuales (4 dígitos)
    private List listaNaturalezaJuridicas = null;
	private Date fechaRadAnotacion;
        private Date fechaRadFolio;
	private Anotacion anotacion;

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     * @param request Trae toda la informacion que se a guardado sobre el usuario
     */

    @Override
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        HttpSession session = request.getSession();

        List elementosGrupo = new Vector();
        List elementosNat = new Vector();

        List grupoNaturalezas = null;
        Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
        String cancelar = (String) session.getAttribute("cancelar");
        String soloCancelar = (String) session.getAttribute("soloCancelar");
        
        /**
         * @Autor: Edgar Lora
         * @Mantis: 0010688
         * @Requerimiento: 066_151
         */
        if (listaNaturalezaJuridicas == null || (turno != null && turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)) && cancelar != null && cancelar.equals("true")) {
            grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
        } else {
            grupoNaturalezas = listaNaturalezaJuridicas;
        }

        if (grupoNaturalezas == null) {
            grupoNaturalezas = new Vector();

            GrupoNaturalezaJuridica gNat;

            for (int i = 0; i < 5; i++)
            {
                gNat = new GrupoNaturalezaJuridica();
                gNat.setIdGrupoNatJuridica(i + "id");
                gNat.setNombre(i + "Nom");

                NaturalezaJuridica nat;

                for (int j = 0; j < 7; j++) {
                    nat = new NaturalezaJuridica();
                    nat.setIdNaturalezaJuridica("id" + i + j);
                    nat.setNombre("Nom" + i + j);
                    nat.setGrupoNaturalezaJuridica(gNat);
                    gNat.addNaturalezaJuridica(nat);
                }
                grupoNaturalezas.add(gNat);
            }
        }

        if (cancelar == null)
        {
        	cancelar = "";
        }

        if (soloCancelar == null)
        {
        	soloCancelar = "";
        }

        anotacion = (Anotacion)session.getAttribute(AWNaturalezaJuridica.ANOTACION_MODICADA);
        // se está creando una nueva anotación
        fechaRadAnotacion = (Date)session.getAttribute(AWNaturalezaJuridica.ANOTACION_FECHA_RADICACION);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        String fechaAperturaFolio = request.getParameter(AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ);
        if(fechaAperturaFolio != null){
            try {
                fechaRadFolio = formatoDelTexto.parse(fechaAperturaFolio);
                session.setAttribute(AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ, fechaRadFolio);
            } catch (ParseException ex) {
                Logger.getLogger(SeleccionarNaturalezaJuridicaHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (anotacion != null){
        	Date fechaAnota = fechaRadAnotacion;
                Date fechaFolio = null;
                NaturalezaJuridica nat = anotacion.getNaturalezaJuridica();

                if(turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)){
                    fechaFolio = (Date)session.getAttribute(AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ);

                    if(fechaAnota != null && fechaAnota.before(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) && fechaAnota.compareTo(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) != 0){
                        if(fechaFolio != null && fechaFolio.before(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) && fechaFolio.compareTo(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) != 0){
                                this.mostrarNuevas = false;
                                this.mostrarAntiguas = true;
                        }else{
                                this.mostrarNuevas = true;
                                this.mostrarAntiguas = true;
                        }
                    }else{
                        this.mostrarNuevas = true;
                        this.mostrarAntiguas = false;
                    }
                }else{
                    // se verifica si la fecha de radicacion es anterior a FECHA_NUEVAS_NATURALEZAS
                    // y si el código actual no es 915 o 999.
                    if (fechaAnota != null && fechaAnota.before(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) && fechaAnota.compareTo(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) != 0){
                        this.mostrarNuevas = false;
                        this.mostrarAntiguas = true;
                    } else {
                        this.mostrarNuevas = true;
                        this.mostrarAntiguas = false;
                    }
                    if (nat != null && (nat.getIdNaturalezaJuridica().equals("915") || nat.getIdNaturalezaJuridica().equals("999"))){
                        this.mostrarNuevas = true;
                    }
                }
        } else if (fechaRadAnotacion != null && fechaRadAnotacion.before(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) && fechaRadAnotacion.compareTo(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) != 0){
            if(turno == null || turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)){
                Date fechaFolio = (Date)session.getAttribute(AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ);
                if(fechaFolio != null && fechaFolio.before(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) && fechaFolio.compareTo(AWNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS) != 0){
                        this.mostrarNuevas = false;
                        this.mostrarAntiguas = true;
                }else{
                        this.mostrarNuevas = true;
                        this.mostrarAntiguas = true;
                }
            }else{
                this.mostrarNuevas = false;
                this.mostrarAntiguas = true;
            }
        } else {
        	this.mostrarNuevas = true;
        	this.mostrarAntiguas = false;
        }


        if(turno==null || turno.getIdFase().equals(CFase.ANT_HOJA_RUTA)){
        	this.mostrarNuevas = true;
        	this.mostrarAntiguas = true;
        }
        if (!cancelar.equals("true"))
        {
            Iterator itGrupos = grupoNaturalezas.iterator();
            idGrupoNat = request.getParameter(GRUPO_NATURALEZA_JURIDICA_ID);
            idNat = request.getParameter(NATURALEZA_JURIDICA_ID);

            while (itGrupos.hasNext()) {
                GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) itGrupos.next();

                if (!this.mostrarCancelacion) {
                    if (!group.getIdGrupoNatJuridica().equalsIgnoreCase(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                        elementosGrupo.add(new ElementoLista(
                                group.getIdGrupoNatJuridica(), group.getNombre()));
                    }
                } else {
                	if (!this.mostrarSoloCancelacion)
                	{
                		elementosGrupo.add(new ElementoLista(
                                group.getIdGrupoNatJuridica(), group.getNombre()));
                	}
                	else
                	{
                		if (group.getIdGrupoNatJuridica().equalsIgnoreCase(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                            elementosGrupo.add(new ElementoLista(
                                    group.getIdGrupoNatJuridica(), group.getNombre()));
                        }
                	}

                }

                if (idGrupoNat != null) {
                    if (idGrupoNat.equals(group.getIdGrupoNatJuridica())) {
                        nomGrupo = group.getNombre();
                        session.setAttribute(GRUPO_NATURALEZA_JURIDICA_ID,
                            group.getIdGrupoNatJuridica());

                        List naturalezas = group.getNaturalezaJuridicas();
                        Iterator itNat = naturalezas.iterator();

                        while (itNat.hasNext()) {
                            NaturalezaJuridica nat = (NaturalezaJuridica) itNat.next();
                            if ((this.mostrarAntiguas && nat.getIdNaturalezaJuridica().length() == 3)
                            		|| (this.mostrarNuevas && nat.getIdNaturalezaJuridica().length() == 4))
                            {
                            	elementosNat.add(new ElementoLista(
	                                    nat.getIdNaturalezaJuridica(),
	                                    nat.getNombre() + " - " +
	                                    nat.getIdNaturalezaJuridica()));
                            }

                            if (idNat != null) {
                                if (idNat.equals(nat.getIdNaturalezaJuridica())) {
                                    session.setAttribute(NATURALEZA_JURIDICA_ID,
                                        nat.getIdNaturalezaJuridica());
                                            /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */
                                    session.setAttribute(VERSION,nat.getVersion());
                                    nomNaturaleza = nat.getNombre();
                                    mostrarBoton = true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Iterator itGrupos = grupoNaturalezas.iterator();
            idGrupoNat = request.getParameter(GRUPO_NATURALEZA_JURIDICA_ID);
            idNat = request.getParameter(NATURALEZA_JURIDICA_ID);

            while (itGrupos.hasNext()) {
                GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) itGrupos.next();

                if (group.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                    elementosGrupo.add(new ElementoLista(
                            group.getIdGrupoNatJuridica(), group.getNombre()));
                }

                if (idGrupoNat != null) {
                    if (idGrupoNat.equals(group.getIdGrupoNatJuridica())) {
                        nomGrupo = group.getNombre();
                        session.setAttribute(GRUPO_NATURALEZA_JURIDICA_ID,
                            group.getIdGrupoNatJuridica());

                        List naturalezas = group.getNaturalezaJuridicas();
                        Iterator itNat = naturalezas.iterator();

                        while (itNat.hasNext()) {
                            NaturalezaJuridica nat = (NaturalezaJuridica) itNat.next();
                            String idnat = nat.getIdNaturalezaJuridica()
                                              .substring(0, 1);

                            if ((this.mostrarAntiguas && nat.getIdNaturalezaJuridica().length() == 3)
                            		|| (this.mostrarNuevas && nat.getIdNaturalezaJuridica().length() == 4))
                            {
                                elementosNat.add(new ElementoLista(
                                        nat.getIdNaturalezaJuridica(),
                                        nat.getNombre() + " - " +
                                        nat.getIdNaturalezaJuridica()));
                            }

                            if (idNat != null) {
                                if (idNat.equals(nat.getIdNaturalezaJuridica())) {
                                    session.setAttribute(NATURALEZA_JURIDICA_ID,
                                        nat.getIdNaturalezaJuridica());
                                   /**
                                    * @author     : Carlos Mario Torres Urina
                                    * @casoMantis : 0012705.
                                    * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                                    * @change     : 
                                    */
                                    session.setAttribute(VERSION,nat.getVersion());
                                    nomNaturaleza = nat.getNombre();
                                    mostrarBoton = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        gruposHelper = new ListaElementoHelper();
        gruposHelper.setCssClase("campoformlista");
        gruposHelper.setId(GRUPO_NATURALEZA_JURIDICA_ID);
        gruposHelper.setNombre(GRUPO_NATURALEZA_JURIDICA_ID);
        gruposHelper.setTipos(elementosGrupo);
        gruposHelper.setFuncion("onChange=\"submitform();\"");

        naturalezaHelper = new ListaElementoHelper();
        naturalezaHelper.setCssClase("campoformlista");
        naturalezaHelper.setId(NATURALEZA_JURIDICA_ID);
        naturalezaHelper.setNombre(NATURALEZA_JURIDICA_ID);
        naturalezaHelper.setTipos(elementosNat);
        naturalezaHelper.setFuncion("onChange=\"submitform();\"");
    }


    /**
     * Este método pinta en la pantalla de manera agradable una descripcion del error
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
     */
    @Override
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {
        out.write(
            "<input name=\"nat_juridica_nom_00\" type=\"hidden\" id=\"nat_juridica_00\" value=\"\">");
        out.write(
            "<input name=\"nat_juridica_id_00\" type=\"hidden\" id=\"nat_juridica_00\" value=\"\">");
                                               /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */
        out.write(
            "<input name=\"nat_juridica_ver_00\" type=\"hidden\" id=\"nat_juridica_00\" value=\"\">");
         out.write(
            "<input name=\""+VERSION+"\" type=\"hidden\" id=\""+VERSION+"\" value=\""+request.getSession().getAttribute(VERSION) +"\">");
        out.write("<br>");
        out.write(
            "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write(
            "<td class=\"bgnsub\">Selecci&oacute;n Naturaleza Juridica </td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_nat_juridica.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("<table width=\"100%\" class=\"camposform\" >");
        out.write("<tr>");
        out.write(
            "<td width=\"50%\" valign=\"top\"><table width=\"100%\" class=\"contenido\">");
        out.write("<tr>");
        out.write("<td>Naturaleza Jur&iacute;dica Gen&eacute;rica </td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>");
        gruposHelper.render(request, out);
        out.write("</td>");
        out.write("</tr>");
        out.write("</table></td>");
        out.write("<td width=\"50%\" valign=\"top\">");
        out.write("<table width=\"100%\" class=\"contenido\">");
        out.write("<tr>");
        out.write("<td>&nbsp;</td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>");
        naturalezaHelper.render(request, out);
        out.write("<input type=\"hidden\" name=\"" + NATURALEZA_JURIDICA_NOM +
            "\" id=\"" + NATURALEZA_JURIDICA_NOM + "\" value=\"" +
            nomNaturaleza + "\">");
        out.write("</td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("</tr>");
        out.write("</table>");

        if (mostrarBoton) {
            out.write("<table width=\"100%\" class=\"camposform\" >");
            out.write("<tr>");
                                                   /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */
            out.write(
                "<td align=\"center\"><a href=\"javascript: value_formulario(document.formulario." +
                NATURALEZA_JURIDICA_ID + ".value,document.formulario." +
                NATURALEZA_JURIDICA_NOM + ".value,"+ 
                "document.formulario." +VERSION+".value);\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/btn_agregar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\"></a></td>");
            out.write("</tr>");
            out.write("</table>");
        }

        removerAtributos(request);
    }

    /**
     * @param request
     */
    private void removerAtributos(HttpServletRequest request) {
        request.getSession().removeAttribute(GRUPO_NATURALEZA_JURIDICA_ID);
        request.getSession().removeAttribute(NATURALEZA_JURIDICA_ID);
        request.getSession().removeAttribute(NATURALEZA_JURIDICA_NOM);
    }

    public void setMostrarCancelacion(boolean valor) {
        this.mostrarCancelacion = valor;
    }
    
    public void setMostrarSoloCancelacion(boolean valor) {
        this.mostrarSoloCancelacion = valor;
    }

    public void setListGruposNaturalezasJuridicas(List listaNaturalezaJuridicas) {
        this.listaNaturalezaJuridicas = listaNaturalezaJuridicas;
    }

	public Date getFechaRadAnotacion() {
		return fechaRadAnotacion;
	}

    public void setFechaRadAnotacion(Date fechaRadAnotacion) {
            this.fechaRadAnotacion = fechaRadAnotacion;
    }

    public Date getFechaRadFolio() {
        return fechaRadFolio;
    }

    public void setFechaRadFolio(Date fechaRadFolio) {
        this.fechaRadFolio = fechaRadFolio;
    }
}
