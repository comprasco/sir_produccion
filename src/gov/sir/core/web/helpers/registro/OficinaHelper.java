package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWLocacion;
import gov.sir.core.web.acciones.comun.AWOficinas;
import gov.sir.core.web.helpers.comun.TextHelper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;


/**
 * @author jfrias, mrios
*/
public class OficinaHelper extends Helper {

   private String local_NombreFormulario = "REGISTRO";




    private TextHelper textHelper;
    private TextHelper hiddenHelper;
    private String nIdDepartamento = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO;
    private String nNomDepartamento = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO;
    private String nIdVereda = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA;
    private String nNomVereda = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA;
    private String nIdMunicipio = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC;
    private String nNomMunicipio = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC;
    private String nIdDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO;
    private String nNomDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO;
    private String nIdOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA;
    /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    private String nVersionOfinaOrigen = CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION;
    private String nNumOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM;
    private String nPrefijoOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO;

    //Campos hidden
    private String nIdOficinaHidden = "id_oficina";
    private String nNumOficinaHidden = "numero_oficina";
    private String nTipoNomOficinaHidden = "tipo_nom_oficina";
    private String nTipoOficinaHidden = "tipo_oficina";
    /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    private String nVersionOficinaHidden="version";
    private String styleRead = "campositem";
    private boolean obtenerDelJSP = false;
	boolean readOnly = false;
	String style = "camposformtext";

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws HelperException, IOException {

    	if(obtenerDelJSP){
    		out.println("<script>");
    		out.println("function oficinasManual(nombre,manual, valor,dimensiones)");
            out.println("{");
    		out.println("document."+ getLocal_NombreFormulario() + ".ACCION.value='"+CSolicitudRegistro.PRESERVAR_INFO+"';");
    		out.println("	var idDepto = document.getElementById('"+nIdDepartamento+"').value;");
    		out.println("	var idMunic = document.getElementById('"+nIdMunicipio+"').value;");
    		out.println("	var idVereda = document.getElementById('"+nIdVereda +"').value;");
    		out.println("	document.getElementById('"+nTipoOficinaHidden+"').value=valor+\"_ID_TIPO\";");
    		out.println("	document.getElementById('"+nTipoNomOficinaHidden+"').value=valor+\"_TIPO\";");
    		out.println("	document.getElementById('"+nNumOficinaHidden+"').value=valor+\"_NUM\";");
    		out.println("	document.getElementById('"+nIdOficinaHidden+"').value=valor+\"_ID_OFICINA\";");
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                out.println("	document.getElementById('"+this.nVersionOficinaHidden+"').value=valor+\"_VERSION\";");
    		out.println("	popup=window.open(nombre+'?"+AWOficinas.ID_DEPTO+"='+idDepto+'&"+AWOficinas.ID_MUNIC+"='+idMunic+'&"+AWOficinas.ID_VEREDA+"='+idVereda+'&"+AWOficinas.OFICINA_HELPER_MANUAL+"='+manual,valor,dimensiones);");
    		out.println("	popup.focus();");
    		out.println("}");
    		out.println("</script>");
    	}


    	out.println("<tr>");
        out.println("<td>&nbsp;</td>");
        out.println("<td>");
        out.println("<table width=\"100%\" class=\"camposform\">");
        out.println("<tr>");
        out.println("<td width=\"17%\" align=\"right\">Departamento</td>");
        out.println("<td width=\"28%\">");

        textHelper.setNombre(this.nIdDepartamento);
        textHelper.setSize("5");
        textHelper.setCssClase(style);
        textHelper.setId(this.nIdDepartamento);
        textHelper.setFuncion("onChange=\"javascript:limpiarDatosOficina('" + nNomDepartamento + "' , '" + nIdOficina + "' , '" + nNumOficina + "' , '" + nNomDocumento + "');\"");
		if(readOnly){
			textHelper.setReadonly(readOnly);
		}else{
			textHelper.setReadonly(false);
		}
        textHelper.render(request, out);

        textHelper.setNombre(nNomDepartamento);
        textHelper.setSize("25");
        textHelper.setCssClase(style);
        textHelper.setId(nNomDepartamento);
        textHelper.setReadonly(true);
        textHelper.render(request, out);

        out.println("</td>");
        out.println("<td width=\"15%\" align=\"right\">Municipio</td>");
        out.println("<td width=\"30%\">");

        textHelper.setNombre(this.nIdMunicipio);
        textHelper.setSize("6");
        textHelper.setCssClase(style);
        textHelper.setId(this.nIdMunicipio);
        textHelper.setFuncion("onChange=\"javascript:limpiarDatosOficina('" + nNomDepartamento + "' , '" + nIdOficina + "' , '" + nNumOficina + "' , '" + nNomDocumento + "');\"");
		if(readOnly){
			textHelper.setReadonly(readOnly);
		}else{
			textHelper.setReadonly(false);
		}
        textHelper.render(request, out);

        textHelper.setSize("25");
        textHelper.setNombre(this.nNomMunicipio);
        textHelper.setCssClase(style);
        textHelper.setId(this.nNomMunicipio);
        textHelper.setReadonly(true);
        textHelper.render(request, out);

        out.println("</td>");
        if(!obtenerDelJSP){
        out.println("<td width=\"10%\">");

            if(!readOnly){
				out.println("<a href=\"javascript:locacion('seleccionar.locacion.do?" +
				AWLocacion.LOCACIONES_CIRCULO + "=','" + this.nPrefijoOficina +
				"','width=790,height=320,menubar=no');setTipoOficina();\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ico_mapcolombia.gif\" alt=\"Permite seleccionar los datos de ubicación\" width=\"16\" height=\"21\" border=\"0\"></a>");
            }

		out.println("&nbsp;</td>");

        }else{
        	out.println("<td width=\"10%\">");

			if(!readOnly){
				out.println("<a href=\"javascript:locacion('seleccionar.locacion.do?" +
	            AWLocacion.LOCACIONES_CIRCULO + "=','" + this.nPrefijoOficina +
	            "','width=790,height=320,menubar=no');setTipoOficina();\"><img src=\"" +
	                    request.getContextPath() +
	                    "/jsp/images/ico_mapcolombia.gif\" alt=\"Permite seleccionar los datos de ubicación\" width=\"16\" height=\"21\" border=\"0\"></a>");
			}

			out.println("&nbsp;</td>");
        }
        out.println("</tr>");
        out.println("</table>");

        hiddenHelper.setNombre(this.nIdVereda);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nIdVereda);
        hiddenHelper.render(request, out);

        hiddenHelper.setNombre(this.nNomVereda);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nNomVereda);
        hiddenHelper.render(request, out);

        out.println("<table width=\"100%\" class=\"camposform\">");
        out.println("<tr>");
        out.println(
            "<td width=\"17%\" align=\"right\">Tipo de Oficina Origen</td>");
        out.println("<td width=\"28%\">");

        hiddenHelper.setCssClase(style);
        hiddenHelper.setNombre(this.nIdDocumento);
        hiddenHelper.setId(this.nIdDocumento);
        hiddenHelper.render(request, out);

        textHelper.setCssClase(style);
        textHelper.setSize("30");
        textHelper.setNombre(this.nNomDocumento);
        textHelper.setId(this.nNomDocumento);
        textHelper.setReadonly(true);
        textHelper.render(request, out);
        out.println("</td>");

        out.println("<td width=\"15%\" align=\"right\">N&uacute;mero</td>");
        out.println("<td width=\"30%\">");

        textHelper.setCssClase(style);
        textHelper.setSize("6");
        textHelper.setNombre(this.nIdOficina);
        textHelper.setId(this.nIdOficina);
        textHelper.setFuncion("");
		if(readOnly){
			textHelper.setReadonly(readOnly);
		}else{
			textHelper.setReadonly(false);
		}
        textHelper.render(request, out);

        textHelper.setSize("25");
        textHelper.setCssClase(style);
        textHelper.setNombre(this.nNumOficina);
        textHelper.setId(this.nNumOficina);
        textHelper.setReadonly(true);
        textHelper.render(request, out);

        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        hiddenHelper.setCssClase(style);
        hiddenHelper.setNombre(this.nVersionOfinaOrigen);
        hiddenHelper.setId(this.nVersionOfinaOrigen);
        hiddenHelper.render(request, out);
        out.println("</td>");
        if(obtenerDelJSP){
        	out.println(
	            "<td width=\"10%\">");

				if(!readOnly){
					out.println("<a href=\"javascript:oficinasManual('seleccionar.oficina.do"+"','" + "true"+
		            "','" + this.nPrefijoOficina+
		            "','width=800,height=310,menubar=no')\" /><image src=\"" +
		            request.getContextPath() +
		            "/jsp/images/ico_tipo_oficina.gif\" alt=\"Permite seleccionar los datos de oficina\" width=\"16\" height=\"21\" border=\"0\"></a>");
				}

			out.println("&nbsp;</td>");
        }else{
	        out.println(
	            "<td width=\"10%\">");

				if(!readOnly){
					out.println("<a href=\"javascript:oficinas('seleccionar.oficina.do" +
		            "','" + this.nPrefijoOficina+
		            "','width=800,height=310,menubar=no')\" /> <image src=\"" +
		            request.getContextPath() +
		            "/jsp/images/ico_tipo_oficina.gif\" alt=\"Permite seleccionar los datos de oficina\" width=\"16\" height=\"21\" border=\"0\"></a>");
				}

			out.println("&nbsp;</td>");
        }
        //	  /*******************CAMPOS OCULTOS**************************************
        out.println("");
        hiddenHelper.setNombre(this.nTipoNomOficinaHidden);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nTipoNomOficinaHidden);
        hiddenHelper.render(request, out);

        out.println("");
        hiddenHelper.setNombre(this.nTipoOficinaHidden);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nTipoOficinaHidden);
        hiddenHelper.render(request, out);

        out.println("");
        hiddenHelper.setNombre(this.nNumOficinaHidden);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nNumOficinaHidden);
        hiddenHelper.render(request, out);

        out.println("");
        hiddenHelper.setNombre(this.nIdOficinaHidden);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nIdOficinaHidden);
        hiddenHelper.render(request, out);

        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        out.println("");
        hiddenHelper.setNombre(this.nVersionOficinaHidden);
        hiddenHelper.setCssClase(style);
        hiddenHelper.setId(this.nVersionOficinaHidden);
        hiddenHelper.render(request, out);

        //	  /*********************************************************
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        textHelper = new TextHelper();
        hiddenHelper = new TextHelper();
        hiddenHelper.setTipo("hidden");

        request.getSession().removeAttribute(WebKeys.OFICINA_HELPER_MANUAL);

        //hiddenHelper.setTipo("text");
        if (!obtenerDelJSP) {
        	request.getSession().setAttribute(WebKeys.OFICINA_HELPER_MANUAL, new Boolean(false));

            //inicializar valores
            this.nIdDepartamento = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO;
            this.nNomDepartamento = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO;
            this.nIdVereda = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA;
            this.nNomVereda = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA;
            this.nIdMunicipio = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC;
            this.nNomMunicipio = CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC;
            this.nIdDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO;
            this.nNomDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO;
            this.nIdOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA;
            this.nNumOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM;
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            this.nVersionOfinaOrigen = CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION;


            //inicializar los hidden
            this.nIdOficinaHidden = "id_oficina";
            this.nNumOficinaHidden = "numero_oficina";
            this.nTipoNomOficinaHidden = "tipo_nom_oficina";
            this.nTipoOficinaHidden = "tipo_oficina";
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            this.nVersionOficinaHidden = "version";
        }else{
        	//inicilizar y subir a session el flag del manual
        	request.getSession().setAttribute(WebKeys.OFICINA_HELPER_MANUAL, new Boolean(true));
        	request.getSession().setAttribute(WebKeys.ID_OFICINA, nIdOficinaHidden);
        	request.getSession().setAttribute(WebKeys.NUMERO_OFICINA, nNumOficinaHidden);
        	request.getSession().setAttribute(WebKeys.TIPO_OFICINA,nTipoOficinaHidden);
        	request.getSession().setAttribute(WebKeys.TIPO_NOM_OFICINA, nTipoNomOficinaHidden);
        }
    }

    /**
     * Define si el helper coloca los datos standar o los dados por el JSP,
     * <p> Si se selecciona esta opcion todos los otros setter de esta clase deben ser llamados.
     * @param obtenerDelJSP
     */
    public void obtenerDelJSP(boolean obtenerDelJSP1) {
        this.obtenerDelJSP = obtenerDelJSP1;
    }

    /**
     * Inicializador del nombre en session donde se guarda del TipoOficinaHidden (hidden)
     * @param nIdDepartamento
     */
    public void setNTipoOficinaHiddenn(String nTipoOficinaHidden) {
        this.nTipoOficinaHidden = nTipoOficinaHidden;
    }

    /**
     * Inicializador del nombre en session donde se guarda del TipoNomOficinaHidden (hidden)
     * @param nIdDepartamento
     */
    public void setNTipoNomOficinaHidden(String nTipoNomOficinaHidden) {
        this.nTipoNomOficinaHidden = nTipoNomOficinaHidden;
    }

    /**
     * Inicializador del nombre en session donde se guarda del NumOficinaHidden (hidden)
     * @param nIdDepartamento
     */
    public void setNNumOficinaHidden(String nNumOficinaHidden) {
        this.nNumOficinaHidden = nNumOficinaHidden;
    }

    /**
     * Inicializador del nombre en session donde se guarda del IdOficinaHidden (hidden)
     * @param nIdDepartamento
     */
    public void setNIdOficinaHidden(String nIdOficinaHidden) {
        this.nIdOficinaHidden = nIdOficinaHidden;
    }

    /**
     * Inicializador del nombre en session donde se guarda del ndOficina
     * @param nIdDepartamento
     */
    public void setNNumOficina(String nNumOficina) {
        this.nNumOficina = nNumOficina;
    }

    /**
     * Inicializador del nombre en session donde se guarda del ndOficina
     * @param nIdDepartamento
     */
    public void setNIdOficina(String nIdOficina) {
        this.nIdOficina = nIdOficina;
    }

    /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    public void setNVersionOfinaOrigen(String nVersionOfinaOrigen) {
        this.nVersionOfinaOrigen = nVersionOfinaOrigen;
    }
    /**
     * Inicializador del nombre en session donde se guarda del IdDocumento
     * @param nIdDepartamento
     */
    public void setNNomDocumento(String nNomDocumento) {
        this.nNomDocumento = nNomDocumento;
    }

    /**
     * Inicializador del nombre en session donde se guarda del IdDocumento
     * @param nIdDepartamento
     */
    public void setNIdDocumento(String nIdDocumento) {
        this.nIdDocumento = nIdDocumento;
    }

    /**
     * Inicializador del nombre en session donde se guarda del NomMunicipio
     * @param nIdDepartamento
     */
    public void setNNomMunicipio(String nNomMunicipio) {
        this.nNomMunicipio = nNomMunicipio;
    }

    /**
     * Inicializador del nombre en session donde se guarda del IdMunicipio
     * @param nIdDepartamento
     */
    public void setNIdMunicipio(String nIdMunicipio) {
        this.nIdMunicipio = nIdMunicipio;
    }

    /**
     * Inicializador del nombre en session donde se guarda del NomVereda
     * @param nIdDepartamento
     */
    public void setNNomVereda(String nNomVereda) {
        this.nNomVereda = nNomVereda;
    }

    /**
     * Inicializador del nombre en session donde se guarda del IdVereda
     * @param nIdDepartamento
     */
    public void setNIdVereda(String nIdVereda) {
        this.nIdVereda = nIdVereda;
    }

    /**
     * Inicializador del nombre en session donde se guarda del NomDepartamento
     * @param nIdDepartamento
     */
    public void setNNomDepartamento(String nNomDepartamento) {
        this.nNomDepartamento = nNomDepartamento;
    }

    /**
     * Inicializador del nombre en session donde se guarda del IdDepartamento
     * @param nIdDepartamento
     */
    public void setNIdDepartamento(String nIdDepartamento) {
        this.nIdDepartamento = nIdDepartamento;
    }
	/**
	 * @return Returns the nPrefijoOficina.
	 */
	public String getNPrefijoOficina() {
		return nPrefijoOficina;
	}
	/**
	 * @param prefijoOficina The nPrefijoOficina to set.
	 */
	public void setNPrefijoOficina(String prefijoOficina) {
		nPrefijoOficina = prefijoOficina;
	}
	/**
	 * @return Returns the nIdDocumento.
	 */
	public String getNIdDocumento() {
		return nIdDocumento;
	}

	public String getLocal_NombreFormulario() {
		return local_NombreFormulario;
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

	public void setLocal_NombreFormulario(String local_NombreFormulario) {
		this.local_NombreFormulario = local_NombreFormulario;
	}

}
