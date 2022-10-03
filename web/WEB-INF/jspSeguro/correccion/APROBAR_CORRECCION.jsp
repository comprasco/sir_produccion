<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultaFolio" %>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_VerAlertasOptions"%>

<%

	session.setAttribute(WebKeys.VISTA_INICIAL,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		

	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();

	if(solicitud==null){
		solicitud = new Solicitud();
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
        
/*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/

        boolean activo = true;
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
	
	boolean tieneAlerta = false;
	 if(session.getAttribute("GENERAR_ALERTA_SIN_CAMBIOS")!=null){
	 	tieneAlerta = true;
	 }
	 
	boolean aprueba = false;

%>

<%

    // bug 3536: pront result message
    String local_ResultMessage;


    local_ResultMessage = (String)session.getAttribute( "LOCAL_RESULT:MESSAGE" );

    if( null != local_ResultMessage ) {
      session.removeAttribute( "LOCAL_RESULT:MESSAGE" );
    } // if

%>


<script type="text/javascript">

var aprobado = 0;

function cambiarAccion1(text) {
	if(aprobado<1){
		document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    	document.CORRECCION1.submit();
	}
}

function cambiarAccion2(text,target1) {
    if(aprobado<1){
    	document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    	document.CORRECCION1.target = target1;
    	document.CORRECCION1.submit();
    }
}

function cambiarAccion5( text ) {
	if(aprobado<1){
		var message = new String( "Dando click en aceptar se har\u00e1n definitivos los cambios en los folios; est\u00e1 seguro?" );

     	if( !js_ConfirmMessage( message ) )
        	return void(0);
    	aprobado++;
    	document.getElementById("Deshacer_Cambios").disabled="disabled";
    	document.getElementById("Ver_Alertas").disabled="disabled";
    	document.getElementById("<%=AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS%>").disabled="disabled";
    	document.getElementById("<%=AWCorreccion.APROBAR%>").disabled="disabled";
    	document.getElementById("<%=AWCorreccion.NEGAR%>").disabled="disabled";
    	document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    	document.CORRECCION1.submit();
	}
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

	function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }


   function js_OnEventConfirm( formName, actionValue, msg ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     if(aprobado<1){
     	if( confirm( msg) ) {
       		htmlForm.setValue( actionField, actionValue );
       		return true;
     	}
     }
     return void(0);

   }


   function js_OnEvent2( formName, actionValue, processorId ) {
   	 <%session.setAttribute("REVISAR_APROBAR","true");%>
   	 if(aprobado<1){
     	var htmlForm;
     	htmlForm = new LocalForm( formName );
     	htmlForm.setFormAction( processorId );
     	htmlForm.setValue( actionField, actionValue );
     	htmlForm.submitForm();
     }
   }
   
	function getAprobado(){
   		return aprobado;
	}
             <%--
/*
* @author : CTORRES
* @change : se agrega funcion javascript cargarTestamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
--%>
 
    function cargarTestamento(text,folio,editable) {
        document.CORRECCION1.action = 'modificar.testamento.do';
        document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
        document.CORRECCION1.<%=CFolio.ID_MATRICULA%>.value = folio;
        document.CORRECCION1.TS_EDITABLE.value=editable;
        document.CORRECCION1.submit();
    }
</script>











<%-- Bug 3536 --%>


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

     eval( "formObject."+ elementName + ".value" + "=" + "elementValue" );
   }

</script>
<script type="text/javascript">
	// local form dependant resources

	var actionField     = "<%= WebKeys.ACCION    %>";
      var PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION = "<%= AWCorreccion.PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION %>"; // the same
    var CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION      = "<%= AwCorr_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION%>";
</script>



















<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%if(tieneAlerta){ %>
	<jsp:include page="../comun/HEADER_MENSAJE_ALERTA.jsp?idTipoMensaje=1" flush="true" />
<%} %>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Aprobar Correcci&oacute;n</td>
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

<%-- + + + + + + + + + + + + +  + + + + + + + + + + + + + + + + + + + +   --%>
<%-- SOF:REGION (conditional) Bug 3536: Informative Messages              --%>
<%-- + + + + + + + + + + + + +  + + + + + + + + + + + + + + + + + + + +   --%>

<%-- (condition) --%>
<%

  if( null != local_ResultMessage ) {

%>

<%-- (condition) --%>



<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >

<table width="100%" class="camposform" style="border:0px;">
<tr> <!-- message -->
  <td width="30">  <!-- icon -->
    <img alt="ok" src="<%=request.getContextPath()%>/jsp/images/ico_satisfactorio.gif" />
  </td>
  <td > <!-- text -->
    <span align="left">
    <%= local_ResultMessage %>
    </span>
  </td>
</tr>
</table>

    </td>
  </tr>
</table>



<%-- (condition) --%>
<%
  } // if
%>

<%-- (condition) --%>
<%-- + + + + + + + + + + + + +  + + + + + + + + + + + + + + + + + + + +   --%>
<%-- EOF:REGION (conditional)                                             --%>
<%-- + + + + + + + + + + + + +  + + + + + + + + + + + + + + + + + + + +   --%>






<%-- --%>


            <form action="consultaFolio.do" method="post" name="CONSULTAR_MAT" id="CONSULTAR_MAT">
            <input type="hidden" name="ACCION" value="<%=AWConsultaFolio.CONSULTAR_FOLIO%>">

              <span class="bgnsub" style="background:" >Matr&iacute;cula Inmobiliaria de la Propiedad</span>
              <%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> </td>
                  <td width="36" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              --%>

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


              </form>
            <P>&nbsp;</P>



            <form action="correccion.do" method="post" name="CORRECCION1" id="CORRECCION1">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="CONFIRMAR">
		    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">

              <br>
              <%--
/*
* @author : CTORRES
* @change : se agrega condicion de activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
--%>
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
              <span class="bgnsub" style="background:" >Derecho de Petici&oacute;n</span>
              <%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Derecho de Petici&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              --%>
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
              <!-- 
                  @author Cesar Ramirez
                  @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
              -->
              <%
              String solicitante_nombre = null;
              String solicitante_primer_apellido = null;
              String solicitante_segundo_apellido = null;
              String tipo_doc_solicitante = null;
              long num_doc_solicitante = 0L;
              boolean isDerivadoRegistro = false;
              if(turnoPadre != null) {
                if(turno.getIdProceso() == 3 && turnoPadre.getIdProceso() == 6) {
                    List turnosHistoria = turno.getHistorials();
                    if (turnosHistoria != null && turnosHistoria.size() > 0) {
                        for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
                            TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
                            if(turnoAux.getFase().equals("SOLICITUD")) {
                                solicitante_nombre = turnoAux.getUsuario().getNombre();
                                solicitante_primer_apellido = turnoAux.getUsuario().getApellido1();
                                solicitante_segundo_apellido = turnoAux.getUsuario().getApellido2();
                                tipo_doc_solicitante = turnoAux.getUsuario().getTipoIdentificacion();
                                num_doc_solicitante = turnoAux.getUsuario().getNumIdentificacion();
                                isDerivadoRegistro = true;
                                break;
                            }
                        }
                    }
                }
              }
              %>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td width="211" class="campositem"><%=tipdoc%>&nbsp;</td>
                  <% } else { %>
                  <td width="211" class="campositem"><%=(tipo_doc_solicitante==null)?"&nbsp;":tipo_doc_solicitante + "&nbsp;"%></td>
                  <%}%>
                  <td width="146">N&uacute;mero</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                  <% } else { %>
                  <td width="212" class="campositem"><%=(num_doc_solicitante == 0)?"&nbsp;":num_doc_solicitante + "&nbsp;"%></td>
                  <%}%>
                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_primer_apellido!=null?solicitante_primer_apellido:""%>&nbsp;</td>
                  <%}%>
                  <td>Segundo Apellido</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_segundo_apellido!=null?solicitante_segundo_apellido:""%>&nbsp;</td>
                  <%}%>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_nombre!=null?solicitante_nombre:""%>&nbsp;</td>
                  <%}%>
                  <td>Teléfono</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getTelefono()!=null?ciudadano.getTelefono():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem">&nbsp;</td>
                  <%}%>
                </tr>
              </table>

              <br />
              <span class="bgnsub" style="background:" >Ver Cambios</span>
              <%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Ver Cambios</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              --%>
              <br />
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" alt="[]"></td>
                  <td width="10">&nbsp;</td>
                  <td width="150"><%--
/*
* @author : CTORRES
* @change : se agrega condiciode activo inactivo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
--%>
                      <%if(activo){%>
                      <input id="Ver_Alertas"  type="image" onClick="js_OnEvent2( 'CORRECCION1', CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION, 'corr-corrsimplemain-veralertasoptions.do' );"  src="<%=request.getContextPath()%>/jsp/images/btn_veralertas.gif" width="150" height="21" border="0" />
                        <%}else{%>
                        <input id="Ver_Alertas" disabled="true" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_veralertas.gif" width="150" height="21" border="0" />
                        <%}%>
                  </td>
				  <td width="2">&nbsp;</td>
				  <td width="140">
                  	<input id="<%=AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS%>" type="image" onClick="cambiarAccion1('<%=AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="140" height="21" border="0">
                  </td>
                  <td>&nbsp;</td>
                  <td width="8">&nbsp;</td>
                </tr>
              </table>

<!-- BEGIN REGION: Print -->
              <hr />
              <%--

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Imprimir Formulario</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              --%>

              <br>
             
<!-- END REGION -->

              <span class="bgnsub" style="background:" >Hacer Cambios Definitivos</span>

<%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Hacer Cambios Definitivos</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
--%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>

                          <td><%-- Número de copias a imprimir: --%> </td>
		<td>
		<%--
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

                  <td width="250">
                    <div align="right">
                       <input id="Deshacer_Cambios" type="image" onClick="js_OnEventConfirm( 'CORRECCION1', PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION, 'Esto elimiara los cambios registrados como temporales, ¿desea continuar? ' );"  src="<%=request.getContextPath()%>/jsp/images/btn_deshacercambios.gif" width="180" height="21" border="0">
                    </div>
                  </td>
                  <td width="2">&nbsp;</td>
                  <td width="2"><sup>::</sup></td>
                  <td width="2">&nbsp;</td>
                  <td>
				  	<input id="<%=AWCorreccion.APROBAR%>" type="image" onClick="cambiarAccion5('<%=AWCorreccion.APROBAR%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_aprobar_guardar.gif" width="180" height="21" border="0">
                  </td>
               <td>
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?ServC=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
             </a>
                  </td>   
              
                  <td>
                  	<input id="<%=AWCorreccion.NEGAR%>" type="image" onClick="cambiarAccion1('<%=AWCorreccion.NEGAR%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_devolver_auxiliar.gif" width="150" height="21" border="0">
                  </td>
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
		    helper.setEsRevisarAprobar(true);
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
