<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWRelacion" %>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultaFolio" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_MesaControl" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible" %>
<%@page import="org.apache.commons.jxpath.JXPathContext" %>

<%@page import="gov.sir.core.web.WebKeys"%>

<%
	session.removeAttribute(AWMesa.MESA_CONTROL+"C");
	session.setAttribute(WebKeys.VISTA_INICIAL,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();

	if(solicitud==null){
		solicitud = new Solicitud();
	}
/*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
        boolean activo = true;
	Turno turnoAnterior = solicitud.getTurnoAnterior();
	if(turnoAnterior==null){
		turnoAnterior = new Turno();
	}else
        {
            if(turnoAnterior.getIdProceso()==6)
            {
                activo = false;
            }
        }
	Ciudadano ciudadano = solicitud.getCiudadano();
	if(ciudadano==null){
		ciudadano = new Ciudadano();
	}

	List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
   	if(docs == null){
		docs = new Vector();
    }

    Iterator it = docs.iterator();
    String tipdoc = "DOCUMENTO IDENTIDAD";
    while (it.hasNext()) {
       ElementoLista el = (ElementoLista) it.next();

       if(el.getId().equals(ciudadano.getTipoDoc())){
       		tipdoc = el.getValor();
       }
    }

	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();

	Iterator is = matriculas.iterator();
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);
	
	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}

%>
<script type="text/javascript">

function cambiarAccion(accion){
	document.CORRECCION.<%=WebKeys.ACCION%>.value = accion;
	document.CORRECCION.submit();
}

function cambiarAccion1(text) {
    document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION1.submit();
}

function cambiarAccion5( text ) {

     var message = new String( "(Antes de realizar el envío de la forma, asegurese de haber impreso el formulario de correccion). Dando click en aceptar se har\u00e1n definitivos los cambios en los folios; est\u00e1 seguro?" );

     if( !js_ConfirmMessage( message ) )
        return void(0);

    cambiarAccion1( text );
}



   function js_ConfirmMessage( message ) {
     // OPTIONAL: cambiar la apariencia de este tipo de ventanas
     // usando chromeless u otro estilo de despliegue incluyendo transparencias.

     //var message = new String( "Esta seguro que desea enviar los cambios al revisor?" );
     var agree = confirm( message );
     if( agree )
        return true;
     return false;
   }

   function js_OnProxyAction2( formName, actionValue ) {


    js_OnProxyAction( formName, actionValue );

   }



</script>



<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- para manejo de formularios a traves de js                   --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">
// Libraries: FindObject BrowserCompatible

// Example: obj = findObj("image1");
function findObj(theObj, theDoc)
{
  var p, i, foundObj;

  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++)
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

  return foundObj;
}

</script>

<script type="text/javascript">
	// local form manipulation

   function LocalForm( formName ) {
     this.formName = formName;
   }
   LocalForm.prototype = new LocalForm();
   LocalForm.prototype.constructor = LocalForm;
   // Form.prototype.superclass = Object;

   LocalForm.prototype.submitForm = function() {
	 formObject = findObj( this.formName );
     formObject.submit();
   }

   LocalForm.prototype.setFormAction = function( formAction ) {
	 formObject = findObj( this.formName );
     formObject.action = formAction;
   }

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")) );
   }

</script>



<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

   var MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION  = "<%= AwCorr_MesaControl.MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION %>";

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">

   // function js_OnEvent_Print( formName, actionValue, param1 ) {
   //
   //  var htmlForm;
   //  htmlForm = new LocalForm( formName );
   //
   //  var msg = new String( "Confirma envio de turno: " + param1 + " a impresion" );

   // if( confirm( msg ) ) {

   //    htmlForm.setValue( actionField, actionValue );
   //    htmlForm.setValue( printField , param1 );
   //    htmlForm.submitForm();
   //    return true;
   //  }
   //  return void(0);
   //
   // }

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }
  <%--  /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/--%>
function cargarTestamento(text,folio,editable) {
    document.CORRECCION1.action = 'modificar.testamento.do';
    document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION1.<%=CFolio.ID_MATRICULA%>.value = folio;
    document.CORRECCION1.submit();
}
</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Mesa Control</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              <br>


            <form action="consultaFolio.do" method="post" name="CONSULTAR_MAT" id="CONSULTAR_MAT">
            <input type="hidden" name="ACCION" value="<%=AWConsultaFolio.CONSULTAR_FOLIO%>">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="36" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%" class="camposform">
                  <tr>
	                  <td>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_RADIO);
	               		  tablaHelper.setInputName(CFolio.ID_MATRICULA);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }

	                  %>
					</td>
                </tr>
              </table>


            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Consultar Seleccionada</td>
                <td><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0"></td>

              </tr>
            </table>    
   <br>
<br>
<br>
<%--
/*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
--%>
    <% if(activo){ %>         
           <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Consultar Folios válidos para imprimir </td>
                <td>&nbsp;</td>
                <td width='150'><a href="javascript:javascript:js_OnEvent( 'CORRECCION1','<%=gov.sir.core.web.acciones.correccion.AwCorr_MesaControl.IMPRIMIR_MESA%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif"  alt="Imprimir" width="139" height="21" border="0"></a></td>

              </tr>
            </table>
            <P>&nbsp;</P>

<%}%>
  </form>
            <form action="correccion.mesa-control.do" method="post" name="CORRECCION1" id="CORRECCION1">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="CONFIRMAR" />
		    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="" />
 <%-- /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */--%>
                    <% if(!activo){ %> 
               <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno de Testamento</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
                
              <table width="100%" class="camposform">
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de testamento</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdWorkflow():""%>&nbsp;</td>
                  <td style="text-align: right;">Consultar</td>
                   <td><input name="imageField" type="image" onclick="javascript:cargarTestamento('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.CARGAR_TESTAMENTO%>','<%=turnoAnterior.getSolicitud().getIdSolicitud() %>','NO');" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0" /></td>
                </tr>
              </table>	                        
                                    <%}else{%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Asociado</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Est&aacute; asociado a un turno ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de documento objeto de correcci&oacute;n</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdTurno():""%>&nbsp;</td>
                </tr>
              </table>
                <%}%>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%
              	if(solicitud instanceof SolicitudCorreccion) {
              		SolicitudCorreccion solicitudCorreccion = (SolicitudCorreccion)solicitud;
			  %>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {
                 				TextAreaHelper area = new TextAreaHelper();
								session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/solicitudCorreccion.getDescripcion());
								area.setCols("90");
								area.setRows("10");
                 				area.setNombre(CTurno.DESCRIPCION);
                  			   	area.setCssClase("campositem");
                  			   	area.setId(CTurno.DESCRIPCION);
                  			   	area.setReadOnly(true);
								area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
					</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Correo </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correo.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Direcci&oacute;n de Correo</td>
                  <td width="45%" align='left' class="campositem"><%=solicitudCorreccion.getDireccionEnvio()!=null?solicitudCorreccion.getDireccionEnvio():""%>&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Derecho de Petici&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getDerechoPeticion()==true?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Correcci&oacute;n solicitada con anterioridad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.isSolicitadaAnteriormente()?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Inter&eacute;s Jur&iacute;dico</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getInteresJuridico()!=null?solicitudCorreccion.getInteresJuridico():""%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%
              }
              %>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem"><%=tipdoc%>&nbsp;</td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <td class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <td>Teléfono</td>
                  <td class="campositem"><%=ciudadano.getTelefono()!=null?ciudadano.getTelefono():""%>&nbsp;</td>
                </tr>
              </table>

              <br />
<%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Ver Cambios</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <br />

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccion1('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="140" height="21" border="0"></a></td>
                  <td width="8">&nbsp;</td>
                </tr>
              </table>
--%>
<%--
<!-- BEGIN REGION: Print -->
              <hr />

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Imprimir Formulario</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>


              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>

                <td><div align="right">Número de copias a imprimir:</div></td>
		<td width="50">

		<%

                try {

                    TextHelper textHelper = new TextHelper();
                    textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
                    textHelper.setCssClase("camposformtext");
                    textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
                    textHelper.setSize("5");
                    textHelper.render(request,out);
                }
                catch( HelperException re ){
		  out.println("ERROR " + re.getMessage());
		}

		%>

		</td>


                  <td width="50">
                    <a href="javascript:cambiarAccion1('<%= AWCorreccion.CORR_REVISIONAPROBACION__PRINTFORM %>');">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" border="0" alt="print"/>
                    </a>
                  </td>

                </tr>
              </table>
<!-- END REGION -->
--%>

              <br>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Revision Mesa Control</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
		<%--

                  <td> Número de copias a imprimir: </td>
		<td>
		<% try {
		TextHelper textHelper = new TextHelper();
		textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setSize("5");
		textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

		%>
                --%>

		</td>

                    <td width="50">

	                    <a href="javascript:js_OnEvent( 'CORRECCION1', MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION );">
	                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" />
	                    </a>

                    </td>
                    <%--
                    /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
                    --%>
                     <% if(activo){ %>
					<td width="15%"><a href="admin.relacion.view?MESA_CONTROLC=TRUE"><img src="<%=request.getContextPath()%>/jsp/images/btn_crear_relaciones.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>
                    <%}%> 
                    <td>&nbsp;</td>
                </tr>
              </table>

            </form>

          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>







<%

String tipoCorreccion="EXTERNA";
if(turnoPadre != null){
	tipoCorreccion="INTERNA";
}

%>


      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Creación de la solicitud</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <table width="100%" class="camposform">
            <tr>


                              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Tipo de Corrección</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Qué Tipo de corrección es ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>La corrección es </td>

                  <td class="campositem"><%=tipoCorreccion%>&nbsp;</td>

                            </td>
                          </tr>
                  </tr>
              </table>




          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>



 		<%
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION");
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>

  </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- class methods --%>

<%-- Bug 3570: Filtro temporal de impresoras --%>

<%!


// realiza una transformacion de un mapa
// en una lista

// estructura del mapa:
//   key: (TipoImpresion)
// value: (List< CirculoImpresora >)

public static List
jspMethod_ValueObjectsProcessor_FilterImpresorasByCirculoTipo(
    Map local_SourceObject
  , String local_TipoImprimibleId
  , String local_CirculoId
) {


    List local_Result;
    local_Result = new ArrayList();


      local_SearchImpl_jx: {

                 // :: local variables ----------------------------------------------
                 // ...... source variables

                 // ...... target variables
                 Map local_TmpMapTargetObject;


                 local_TmpMapTargetObject = new java.util.TreeMap(
                    new java.util.Comparator() {

                      public boolean equals(Object o2 ) {
                        return false;
                      } // end method

                      public int compare(Object o1, Object o2) {
                        if( !( ( o1 instanceof gov.sir.core.negocio.modelo.CirculoImpresora )
                            &&( o2 instanceof gov.sir.core.negocio.modelo.CirculoImpresora ) ) ){
                          return -1;
                        }

                        gov.sir.core.negocio.modelo.CirculoImpresora p1 = (gov.sir.core.negocio.modelo.CirculoImpresora)o1;
                        gov.sir.core.negocio.modelo.CirculoImpresora p2 = (gov.sir.core.negocio.modelo.CirculoImpresora)o2;


                        return( p1.getIdCirculo().compareTo( p2.getIdCirculo() )
                              + p1.getIdImpresora().compareTo( p2.getIdImpresora() ) );

                      } // end method

                    } // end class

                 );

                 JXPathContext 	                local_JxContext;
                 Object 			local_JxContextSource;
                 String 			local_JxSearchString;

                 // search-1:
                 // " .[ (@idTipoImprimible = $local_TipoImprimibleId ) ]";
                 // local_JxContext.setValue( "local_TipoImprimibleId", local_TipoImprimibleId );
                 //
                 Iterator local_TargetIterator = null;

                 local_Search_JxTipoImprimible: {

                   Iterator   local_SourceObjectEntryIterator = local_SourceObject.entrySet().iterator();
                   Map.Entry  local_SourceObjectEntry;
                   List       local_Search1Result;


                   gov.sir.core.negocio.modelo.TipoImprimible local_SourceObjectElementKey;
                   Object                                     local_SourceObjectElementValue;

                   local_Search1Result = new ArrayList();

                   for( ; local_SourceObjectEntryIterator.hasNext(); ) {
                     local_SourceObjectEntry = (Map.Entry)local_SourceObjectEntryIterator.next();
                     local_SourceObjectElementKey = (gov.sir.core.negocio.modelo.TipoImprimible)local_SourceObjectEntry.getKey();

                     if(!(  local_TipoImprimibleId.equals( local_SourceObjectElementKey.getIdTipoImprimible() ) ) ) {
                       continue;
                     } //

                     local_SourceObjectElementValue = local_SourceObjectEntry.getValue();

                     local_Search1Result.add( local_SourceObjectElementValue );

                     break;

                   } // for

                   local_TargetIterator = local_Search1Result.iterator();

                   // jxpath does not support map keys as objects, only strings

                   // // (declare variables)
                   // local_JxContext.getVariables().declareVariable( "local_TipoImprimibleId", "" );

                   // local_JxSearchString = " .[ (@idTipoImprimible = $local_TipoImprimibleId ) ]";
                   // local_JxContext.setValue( "local_TipoImprimibleId", local_TipoImprimibleId );

                   // // :: get the results
                   // // single object   : local_JxContext.getValue
                   // // multiple object : local_JxContext.iterate

                   // Iterator local_TargetIterator;
                   // local_TargetIterator = local_JxContext.iterate( local_JxSearchString );

                 } // :local_Search_JxTipoImprimible

                 // :: initialize



                 // se usa un mapa para eliminar elementos duplicados

                 for( ; local_TargetIterator.hasNext() ; )  {
                     // consume
                     ArrayList local_TargetElement = (ArrayList)local_TargetIterator.next();

                     for( Iterator local_TmpListIterator = local_TargetElement.iterator(); local_TmpListIterator.hasNext(); ) {
                       Object circuloImpresora = local_TmpListIterator.next();
                       local_TmpMapTargetObject.put( circuloImpresora, circuloImpresora );
                     } // for

                 } // for

                 local_TargetIterator = null;

                 // luego se colocan los resultados en la lista objetivo
          // transform the map & return
          Map.Entry mapEntry; // to iterate through the map


          for( Iterator iterator = local_TmpMapTargetObject.entrySet().iterator(); iterator.hasNext();) {
            mapEntry =  (Map.Entry) iterator.next();

            local_Result.add( mapEntry.getValue() );

          } // for

          local_TmpMapTargetObject = null;

          // fix the results
          return local_Result;
          // ----------------------------------------------------------
      } // :local_SearchImpl_jx




/*

  Map local_PrintersMap = local_SourceObject;
  Iterator local_PrintersMapKeyIterator;
  gov.sir.core.negocio.modelo.CirculoImpresora local_PrintersElement;

  Map.Entry           local_PrintersMapEntry;
  java.util.ArrayList local_PrintersMapEntryValue;

  local_PrintersMapKeyIterator = local_PrintersMap.entrySet().iterator();

  gov.sir.core.web.helpers.comun.ElementoLista local_TiposElement;
  for( ; local_PrintersMapKeyIterator.hasNext(); ) {

    // el key es un tipoimprimible
    // el value de cada entry es una lista

    local_PrintersMapEntry = (Map.Entry)local_PrintersMapKeyIterator.next();
    local_PrintersMapEntryValue = (java.util.ArrayList)local_PrintersMapEntry.getValue();

    // al parecer el query que se devuelve coloca
    // todos los circulos;
    // 1. se debe filtrar solo los tipos del circulo actual
    // 2. se debe colocar solo si no existe actualmente

    // 3. (TODO: temporal debido al cambio que se hizo de impresion)

    for( Iterator local_PrintersMapEntryValueIterator = local_PrintersMapEntryValue.iterator(); local_PrintersMapEntryValueIterator.hasNext(); ) {
      local_PrintersElement = (gov.sir.core.negocio.modelo.CirculoImpresora)local_PrintersMapEntryValueIterator.next();
      local_TiposElement = new gov.sir.core.web.helpers.comun.ElementoLista();
      local_TiposElement.setId( local_PrintersElement.getIdImpresora() );
      local_TiposElement.setValor( local_PrintersElement.getIdImpresora() );

      impresoras.add( local_TiposElement );

    } // for


  return null;
*/

} // end-method: jspMethod_ValueObjectsProcessor_FilterImpresorasByCirculoTipo


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

