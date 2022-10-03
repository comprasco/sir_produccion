/*
 * Created on 30/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

//import gov.sir.core.negocio.modelo.AnotacionesFolio;
import gov.sir.core.web.WebKeys;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.HelperException;


/**
 * @author jmendez
 *
 */
public class PaginadorAvanzadoHelper extends PaginadorHelper {


	// .DO DE LA ACCION WEB A LLAMAR
	private String urlAccionWeb;

	// NOMBRE DEL IDENTIFICADOR DE ACCIÓN (GENERALMENTE WEBKEYS.ACCION
	private String nombreAccion;

	// NOMBRE DEL IDENTIFICADOR DEL FORMULARIO
	private String nombreForm = "PAGINAR";

	//TIPO DE ACCION SOLICITADA A LA AW
	private String tipoAccion;

	//NOMBRE DEL PARÁMETRO DE PÁGINA ENVIADO A LA AW
	private String variablePagina;

	//CONSTANTE PARA DETERNINAR SI SE CREA O NO UN NUEVO FORMULARIO
	private boolean nuevoFormulario = true;



	/**
	 *
	 */
	public PaginadorAvanzadoHelper() {
		super();
	}

	/**
		 * Este método pinta en la página las tablas de los datos de resultado
		 * de una consulta
		 * @param request Trae toda la informacion que ha sido guardada del usuario
		 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
		 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		setProperties(request);

		out.write("   <SCRIPT>\r\n");
		out.write("       function cambiarPagina(text) {\r\n");
		out.write("       document."+nombreForm+"." + nombreAccion + ".value = '" + tipoAccion+ "';\r\n");
		out.write("       document."+nombreForm+"." + variablePagina + ".value = text;\r\n");
		out.write("       document."+nombreForm+".action = '"+ urlAccionWeb +"';\n");
		out.write("       document."+nombreForm+".submit();\r\n");
		out.write("       }\r\n");
		out.write("       function cambiarPaginaActual() {\r\n");
		out.write("       var a=0;\r\n");
		out.write("       eval(a=document."+nombreForm+".PAGINA_ACTUAL.value-1);\r\n");

		//out.write("       alert(a)\r\n");

		out.write("       if(a<="+ Integer.toString(getUltimaPagina())+" && a > -1){");
		out.write("       document."+nombreForm+"." + variablePagina + ".value = a;\r\n");
		out.write("       document."+nombreForm+".action = '"+ urlAccionWeb +"';\n");
		out.write("       document."+nombreForm+"." + nombreAccion + ".value = '" + tipoAccion+ "';\r\n");
		out.write("       document."+nombreForm+".submit();\r\n");
		out.write("       }else{\r\n");
		out.write("       alert(\"Pagina fuera del rango.\")\r\n");
		out.write("       document."+nombreForm+".PAGINA_ACTUAL.value=\"\";\r\n");
		out.write("       }\r\n");
		out.write("       }\r\n");
		out.write("       function cambiarAnotacionActual() {\r\n");
        //TODO
        // EL siguiente if es para validar si el usuario digitó la anotación desde la cual desea visualizar,
        // de ser asi si el usuario digita, en el paginador inferior, un numero menor que 1 se lanza un alert con el mensaje "Anotacion fuera del rango."
        // de lo contrario se hace como estaba antes (en el else).
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
        // Se cambió la variable estática por atributos en sesion y en el evento EvnPaginadorAnotaciones.
        Integer numAnotacionesTotalV = (Integer)request.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL);
        Integer numAnotacionesDesdeV = (Integer)request.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE);
        //if(AnotacionesFolio.getNumAnotacionesTotalV()>0){
        if(numAnotacionesTotalV != null && numAnotacionesTotalV>0){
            //out.write("       var vTempAnotacion = document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value - " + AnotacionesFolio.getNumAnotacionesFolio() + ";\r\n");
            out.write("       var vTempAnotacion = document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value - " + numAnotacionesDesdeV + ";\r\n");
            out.write("       if(vTempAnotacion<1){\r\n");
            out.write("         alert(\"Anotacion fuera del rango.\")\r\n");
            out.write("         document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value=\"\";\r\n");
            out.write("       }else{\r\n");
            out.write("         document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value = vTempAnotacion;\r\n");
        }
		out.write("       var a=0;\r\n");
		out.write("       var ordenAnotacion=0;\r\n");
		out.write("       eval(a=Math.floor((document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value-1)/"+this.getNumAnotacionesPagina()+"));\r\n");
		out.write("       eval(ordenAnotacion=Math.floor(document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value));\r\n");

		//out.write("       alert(a);\r\n");

		out.write("       if(ordenAnotacion<="+ Integer.toString(getNumAnotacionesTotal())+" && a > -1){");
		out.write("       if(a<="+ Integer.toString(getUltimaPagina())+" && a > -1){");
		out.write("       document."+nombreForm+"." + variablePagina + ".value = a;\r\n");
		out.write("       document."+nombreForm+".action = '"+ urlAccionWeb +"';\n");
		out.write("       document."+nombreForm+"." + nombreAccion + ".value = '" + tipoAccion+ "';\r\n");
		out.write("       document."+nombreForm+".submit();\r\n");
		out.write("       }else{\r\n");
		out.write("       alert(\"Anotacion fuera del rango.\")\r\n");
		out.write("       document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value=\"\";\r\n");
		out.write("       }\r\n");
		out.write("       }else{\r\n");
		out.write("       alert(\"Anotacion fuera del rango.\")\r\n");
		out.write("       document."+nombreForm+".ORDEN_ANOTACION_ACTUAL.value=\"\";\r\n");
		out.write("       }\r\n");
        //TODO
        // Cierre del else.
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
        // Se cambió la variable estática por atributos en sesion y en el evento EvnPaginadorAnotaciones.
        //if(AnotacionesFolio.getNumAnotacionesTotalV()>0){
        if(numAnotacionesTotalV != null && numAnotacionesTotalV>0){
            out.write("       }\r\n");
        }
		out.write("       }\r\n");
		out.write("   </SCRIPT>\r\n");

                // Bug 05042 ---------------------------------------------------
                StringBuffer htmBuffer;
                htmBuffer = new StringBuffer();

                if( isCheckboxController_MultiSelectEnabled() ) {

                   // remover este key en la accion web
                   final String PREFIX = "PAGINADOR:";
                   final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
                   final String CHECKBOXCONTROLLER_TARGETFORMFIELDID  = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";

                   HttpSession session;
                   session = request.getSession();

                   session.setAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED, Boolean.TRUE );
                   session.setAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID , getCheckboxController_TargetFormFieldId() );



                } // if

                if( isCheckboxController_MultiSelectEnabled() ) {
                   htmBuffer.append( "\r\n" );
                   htmBuffer.append( "<input           " + "\r\n" );

                   if( isCheckboxController_MultiSelectDebugEnabled() ) {
                      htmBuffer.append( "  type='text'    " + "\r\n" );
                   }
                   else {
                      htmBuffer.append( "  type='hidden'    " + "\r\n" );
                   } // if

                   htmBuffer.append( "  id='"      + getCheckboxController_TargetFormFieldId() + "'    " + "\r\n" );
                   htmBuffer.append( "  name='"    + getCheckboxController_TargetFormFieldId() + "'    " + "\r\n" );
                   htmBuffer.append( "  value='"   + ""  + "'"  + "\r\n" );
                   htmBuffer.append( "/>        "  + ""  + "\r\n" );
                   htmBuffer.append( "\r\n" );
                }

                // -------------------------------------------------------------



		if(nuevoFormulario){
			out.write(
				"<form action=\""
					+ urlAccionWeb
					+ "\" method=\"post\" type=\"submit\"  name=\""+nombreForm+"\" id=\""+nombreForm+"\">\r\n");

			out.write("<input  type=\"hidden\" name=\"");
			out.print(nombreAccion);
			out.write("\" id=\"");
			out.print(nombreAccion);
			out.write("\" value=\"");
			out.print(tipoAccion);
			out.write("\">\r\n");

                        out.println( htmBuffer.toString() );




		}




		out.write("<input  type=\"hidden\" name=\"");
		out.print(variablePagina);
		out.write("\" id=\"");
		out.print(variablePagina);
		out.write("\" value=\"");
		out.print("0");
		out.write("\">\r\n");

		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
		out.write(
			"<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"");
		out.print(request.getContextPath());
		out.write(
			"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>\r\n");
		out.write("<td class=\"bgnsub\">Paginador</td>\r\n");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/ico_paginador.gif\" width=\"16\" height=\"21\"></td>\r\n");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"");
		out.print(request.getContextPath());
		out.write(
			"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>\r\n");
		out.write("</tr>\r\n");
		out.write("</table>\r\n");
		out.write("<br>\r\n");
		out.write("<table width=\"100%\" class=\"camposform\">\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"20\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/ind_paginas.gif\" width=\"20\" height=\"15\"></td>\r\n");
		out.write("<td align=\"center\"><table >\r\n");
		out.write("<tr>\r\n");

		/*
				<a href="javascript:cambiarPagina()" ><img src="eraserhead.jpg" width="78" height="44" border="0"></a>
				<input name="textfield" type="text" value="fasdfasdf">

				 */

		if (getPaginaActual() > 0) {
			out.write("<td width=\"20\" align=\"center\">\r\n");
			out.write("<a href=\"javascript:cambiarPagina(" + 0 + ") \"><img src=\"");
			out.print(request.getContextPath());
			out.write(
				"/jsp/images/btn_inicio.gif\" width=\"25\" height=\"21\" border=\"0\"></a></td>\r\n");
		}
		int nPaginaAnt= getPaginaActual()-10;
		if (nPaginaAnt > -1) {

			out.write("<td width=\"20\" align=\"center\">\r\n");
			out.write("<a href=\"javascript:cambiarPagina("+ nPaginaAnt +") \"><img src=\"");
			out.print(request.getContextPath());
			out.write(
				"/jsp/images/btn_anterior.gif\" width=\"20\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		for (int i = getDesde(); i < getHasta(); i++) {
			out.write(
				"<td align=\"center\" ><a href=\"javascript:cambiarPagina("+i+")  "
					+ "\" class=\""
					+ ((getPaginaActual() == i) ? "paginadoractivo" : "paginadorinactivo")
					+ "\">"
					+ (i + 1)
					+ "</a></td>\r\n");
		}

		int nPaginaSig= getPaginaActual()+10;
		if(nPaginaSig < getUltimaPagina()){
			out.write("<td><a href=\"javascript:cambiarPagina("+ nPaginaSig +") \"><img src=\"");
			out.print(request.getContextPath());
			out.write("/jsp/images/btn_siguiente.gif\" width=\"20\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		if (getPaginaActual() < getUltimaPagina()) {
			out.write("<td><a href=\"javascript:cambiarPagina("+ getUltimaPagina() +") \"><img src=\"");
			out.print(request.getContextPath());
			out.write("/jsp/images/btn_fin.gif\" width=\"25\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		out.write("</tr>\r\n");
		out.write("</table></td>\r\n");
		out.write("</tr>\r\n");
		out.write("</table>\r\n");

		out.write("<table width=\"100%\" class=\"camposform\">\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"49%\" align=\"right\">buscar Pagina:</td>\r\n");
		out.write("<td width=\"2%\" align=\"right\">&nbsp;</td>\r\n");
		out.write("<td width=\"5%\" align=\"left\">   " );
		try {
			TextHelper textHelper = new TextHelper();
			textHelper.setNombre("PAGINA_ACTUAL");
			textHelper.setCssClase("camposformtext");
			textHelper.setId("PAGINA_ACTUAL");
			textHelper.setSize("3%");
			textHelper.render(request,out);
		}
			catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		out.write(" </td>\r\n");
		out.write("<td width=\"44%\" align=\"left\"><a href=\"javascript:cambiarPaginaActual()\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/btn_mini_buscar_pag.gif\" width=\"35\" height=\"21\" border=\"0\"></a></td>\r\n");

		out.write("</tr>\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"49%\" align=\"right\">buscar anotaci&oacute;n:</td>\r\n");
		out.write("<td width=\"2%\" align=\"right\">&nbsp;</td>\r\n");
		out.write("<td width=\"5%\" align=\"left\">   " );
		try {
			TextHelper textHelper = new TextHelper();
			textHelper.setNombre("ORDEN_ANOTACION_ACTUAL");
			textHelper.setCssClase("camposformtext");
			textHelper.setId("ORDEN_ANOTACION_ACTUAL");
			textHelper.setSize("3%");
			textHelper.render(request,out);
		}
			catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		out.write(" </td>\r\n");
		out.write("<td width=\"44%\" align=\"left\"><a href=\"javascript:cambiarAnotacionActual()\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/btn_mini_buscar_pag.gif\" width=\"35\" height=\"21\" border=\"0\"></a></td>\r\n");

		out.write("</tr>\r\n");
		out.write("</table>\r\n");
		if(nuevoFormulario){
			out.write("</form>\r\n");
		}

		out.write("<hr class=\"linehorizontal\">\r\n");

	}

	/**
	 f* @return
	 */
	public String getUrlAccionWeb() {
		return urlAccionWeb;
	}

	/**
	 * @param string
	 */
	public void setUrlAccionWeb(String string) {
		urlAccionWeb = string;
	}

	/**
	 * @return
	 */
	public String getNombreAccion() {
		return nombreAccion;
	}

	/**
	 * @param string
	 */
	public void setNombreAccion(String string) {
		nombreAccion = string;
	}

	/**
	 * @return
	 */
	public String getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * @param string
	 */
	public void setTipoAccion(String string) {
		tipoAccion = string;
	}

	/**
	 * @return
	 */
	public String getVariablePagina() {
		return variablePagina;
	}

	public boolean isNuevoFormulario() {
		return nuevoFormulario;
	}

	public String getCheckboxController_TargetFormFieldId() {
		return checkboxController_TargetFormFieldId;
	}

	public boolean isCheckboxController_MultiSelectEnabled() {
		return checkboxController_MultiSelectEnabled;
	}

	public boolean isCheckboxController_MultiSelectDebugEnabled() {
		return checkboxController_MultiSelectDebugEnabled;
	}

	/**
	 * @param string
	 */
	public void setVariablePagina(String string) {
		variablePagina = string;
	}

	/**
	 * @param string
	 */
	public void setNombreForm(String string) {
		nombreForm = string;
	}

	public void setNuevoFormulario(boolean nuevoFormulario) {
		this.nuevoFormulario = nuevoFormulario;
	}

        private boolean checkboxController_MultiSelectEnabled;
        private String  checkboxController_TargetFormFieldId        = ""; // text-field identifier
        private boolean checkboxController_MultiSelectDebugEnabled = false;


	/**
	 * setCheckboxController_MultiSelectEnabled
	 *
	 * @param b boolean
	 */
	public void setCheckboxController_MultiSelectEnabled(boolean b) {
          checkboxController_MultiSelectEnabled = b;
	}

	/**
	 * setCheckboxController_TargetFormFieldId
	 *
	 * @param string String
	 */
	public void setCheckboxController_TargetFormFieldId(String string) {
          checkboxController_TargetFormFieldId = string;
	}

	public void setCheckboxController_MultiSelectDebugEnabled(boolean
		 checkboxController_MultiSelectDebugEnabled) {
		this.checkboxController_MultiSelectDebugEnabled =
			 checkboxController_MultiSelectDebugEnabled;
	}


}
