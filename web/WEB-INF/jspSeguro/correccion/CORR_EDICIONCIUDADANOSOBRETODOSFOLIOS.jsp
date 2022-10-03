<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_EdicionCiudadanoSobreTodosFolios"%>

<%@page import="gov.sir.core.negocio.modelo.Folio" %>
<%@page import="gov.sir.core.negocio.modelo.Anotacion" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>

<%
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		Circulo circulo = (Circulo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));
		}

	ListaElementoHelper circuloHelper = new ListaElementoHelper();
	circuloHelper.setTipos(elementos);




%>

<%

  List docs = null;
  ListaElementoHelper docHelper = new ListaElementoHelper();

  docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
  if( docs == null ){
    docs = new Vector();
  }
  docHelper.setOrdenar(false);
  docHelper.setTipos( docs );

%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>


<body>



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

   function passBack( updateResult )
   {
     // alert( "executed" + opener.document.RELOADDATAFORM.ACCION );
     opener.document.getElementById( "RELOAD_DATA" ).value = updateResult;
     // opener.document.getElementById("P1_SAL").focus();

     if( null != updateResult ) {
       // <%-- opener.document.RELOADDATAFORM.<%=WebKeys.ACCION%>.value = "<%= gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIOEDIT_RELOADBYUPDATECIUDADANO_PAGERENDERING_PROCESS_ACTION %>";--%>
       // opener.document.RELOADDATAFORM.RELOAD_DATA = updateResult;
       opener.document.RELOADDATAFORM.submit();
     }
     close();
   }

</script>


<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

   var EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION   = "<%= AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION %>";
   var EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION  = "<%= AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION %>"; // the same
   var EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION  = "<%= AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION %>"; // the same

   var EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION   = "<%= AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION %>";

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

   function js_OnEvent_Apply( formName, actionValue ) {

     // js_OnEvent( formName, actionValue );
     js_OnEvent_Confirm( formName, actionValue, "Las modificaciones del ciudadano aplicaran para todos los folios donde se encuentre referenciado el ciudadano; \n¿Desea continuar?" );

   }

   function js_OnEvent_Cancel( formName, actionValue ) {

     js_OnEvent( formName, actionValue );
     close();

   }

  function js_OnEvent_Back( formName, actionValue ) {
    passBack( "RELOAD" );
    // js_OnEvent( formName, actionValue );
  }


   function js_OnEvent_Confirm( formName, actionValue, msg ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     if( confirm( msg) ) {

       htmlForm.setValue( actionField, actionValue );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

   }


</script>



<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- sof:block: "alt behavior" -->

<script type="text/javascript">
var ol_fgcolor="#ffffc0";
var ol_border="1";
var ol_bgcolor="#FFFFC0";
var ol_textcolor="#000000";
var ol_capcolor="#aaaaaa";
//var ol_css="forms-help";

</script>
<style media="screen" type="text/css">
.forms-help {
    border-style: dotted;
    border-width: 1px;
    padding: 5px;
    background-color:#FFFFC0; /* light yellow */
    width: 200px; /* otherwise IE does a weird layout */
    z-index:1000; /* must be higher than forms-tabContent */
}

</style>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/privileged/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<!-- eof:block -->
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->



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
    <td width="12" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
      <%--
      <img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt="">
      --%>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
      <%--  --%>
      <%--
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"></td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table>
      --%>
    </td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">



<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- BEGIN REGION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>

        <%

        // colocar marca de habilitacion de bolqueos
        Boolean flag_UpdateResults = (Boolean)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__REGIONEDIT_UPDATERESULTS );
        if( null != flag_UpdateResults ) {

        %>


        <table>

          <tr>
            <td><img alt="ok" src="<%=request.getContextPath()%>/jsp/images/ico_satisfactorio.gif" /></td>
            <td> 1 registro actualizado </td>
          </tr>
        </table>


          <%
          session.removeAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__REGIONEDIT_UPDATERESULTS );
          %>


        <%
        }
        %>


<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- END REGION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
        <%-- + + + + +  + + + + + +  + + + + + + + + + + ++ + + + + ++ --%>














        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar ciudadano</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <%--
          <tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              &nbsp;
            </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          --%>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              <%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Par&aacute;metros</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
              --%>

        <%--
        REGION 1: Desplegar El conjunto de folios, y el conjunto de anotaciones que tiene el bloqueo del usuario.
        --%>

        <%

        java.util.List listFolios;
        java.util.List listBloqueos;

        listFolios    = (List)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS );
        listBloqueos  = (List)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_USUARIOSBLOQUEO );

        if( null == listFolios ) {
          listFolios = new java.util.ArrayList();
        }

        if( null == listBloqueos ) {
          listBloqueos = new java.util.ArrayList();
        }



        %>


				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15" alt="ind-lista"/></td>
						<td> Folios que incluyen este ciudadano .............................................................................................. <img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif"/></td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td>


                                                  <!-- ini:items -->




        <table style='border:0px;' class='camposform' >
          <!-- header -->
          <tr>
            <td width="100"><div align="center" style="font-weight:bold;">Folio</div></td>
            <td width="200"><div align="center" style="font-weight:bold;">Anots.</div></td>
            <td width="100"><div align="center" style="font-weight:bold;">Blq</div></td>
            <td width="20"><div align="center" style="font-weight:bold;">::</div></td>
          </tr>

          <!-- iterate -->
          <%

          Folio[]   arrayFolios   = new Folio[ listFolios.size() ];
          Usuario[] arrayBloqueos = new Usuario[ listBloqueos.size() ];

          arrayFolios   = (Folio[])listFolios.toArray( arrayFolios );
          arrayBloqueos = (Usuario[])listBloqueos.toArray( arrayBloqueos );

          %>

          <%
          Folio      local_arrayFoliosElement;
          List       local_arrayFoliosAnotacionesList;
          Anotacion  local_arrayFoliosAnotacionesListElement;
          Usuario    local_arrayBloqueosElement;

          final int LENGTH_LIMIT_ANOTACIONES_TX = 200;

          %>
          <%
          for( int i=0; i < arrayFolios.length; i++ ) {
          %>
          <tr>

            <%
            local_arrayFoliosElement   = arrayFolios[i];
            local_arrayBloqueosElement = arrayBloqueos[i];
            %>

            <!-- element.folio -->
            <td>
              <div align="center">
              <%= local_arrayFoliosElement.getIdMatricula() %>
              </div>
            </td>

           <!-- element.anotaciones -->
            <%

            StringBuffer localBuffer_anotacionesByFolio
              = new StringBuffer();

            local_arrayFoliosAnotacionesList = local_arrayFoliosElement.getAnotaciones();
            for( java.util.Iterator localAnotacionesListIterator = local_arrayFoliosAnotacionesList.iterator(); localAnotacionesListIterator.hasNext(); ) {
              local_arrayFoliosAnotacionesListElement = (Anotacion)localAnotacionesListIterator.next();

              localBuffer_anotacionesByFolio.append( local_arrayFoliosAnotacionesListElement.getOrden() + " " );
            }
            %>
            <td onclick="return overlib('<%=localBuffer_anotacionesByFolio.toString()%>',STICKY, MOUSEOFF );" onmouseout="nd();" >
              <%

              if( localBuffer_anotacionesByFolio.length() > LENGTH_LIMIT_ANOTACIONES_TX ) {

                out.println( localBuffer_anotacionesByFolio.substring( 0, LENGTH_LIMIT_ANOTACIONES_TX ) + "..." );
              }
              else {
                out.println( localBuffer_anotacionesByFolio.toString() );
              }
              %>
            </td>

            <!-- element.bloqueo-por -->
            <td>
              <div align="center">

              <%

              if( ( null != local_arrayBloqueosElement )
                  &&( !(  "".equals( local_arrayBloqueosElement.getUsername() ) ) )  ) {

              %>

                 <%= local_arrayBloqueosElement.getUsername() %>

              <%
              }
              else {
              %>
                 <%= "-" %>
              <%
              }
              %>

              </div>
            </td>

            <!-- element.pass-test -->

            <td>
              <div align="center">

              <%
              boolean hasSameUser = false;

              localTest :{
                gov.sir.core.negocio.modelo.Usuario t0_User
                  = (gov.sir.core.negocio.modelo.Usuario )session.getAttribute( WebKeys.USUARIO );
                String t0_UserId = t0_User.getUsername();
                String t1_UserId = local_arrayBloqueosElement.getUsername();

                if( ( null != t0_UserId  )
                  &&( null != t1_UserId )
                  &&( t0_UserId.equalsIgnoreCase( t1_UserId ) ) ) {

                  hasSameUser = true;

                } // end if

              } //: localTest

              out.print( "<input type='checkbox' disabled='disabled' " );
              if( hasSameUser ) {
                out.print(" checked='checked' ");
              }
              out.print( "/>" );
              %>
              </div>

            </td>


          </tr>

          <%
          }
          %>


        </table>







                                                  <!-- end:items -->
                                                </td>
                                        </tr>
                                </table>





        <form action="corr-edicionciudadanosobretodosfolios.do" method="post" name="LOCK" id="LOCK">

          <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="" />


                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">



	                    <a href="javascript:js_OnEvent( 'LOCK', EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION  );">
	                     <img alt="regresar"   src="<%=request.getContextPath()%>/jsp/images/btn_validar.gif" width="139" height="21" border="0" />
	                    </a>
                    </td>
                  </tr>

                </table>

        </form>

        <%-- + + + + +  + + + + + +  + + + + + + + + + + ++ + + + + ++ --%>

        <%-- + + + + +  + + + + + +  + + + + + + + + + + ++ + + + + ++ --%>

        <%

        // colocar marca de habilitacion de bolqueos
        Boolean flag_HabilitacionBoqueos = (Boolean)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO );
        if( null == flag_HabilitacionBoqueos) {
          flag_HabilitacionBoqueos = Boolean.FALSE;
        }

        %>


        <%

        if( flag_HabilitacionBoqueos.booleanValue() ) {

        %>




        <form action="corr-edicionciudadanosobretodosfolios.do" method="post" name="EDIT" id="EDIT">

        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWReportes.DIARIO_RADICADOR_MATRICULAS%>">
           <table width="100%" class="camposform">

                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Id Ciudadano:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_IDCIUDADANO );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_IDCIUDADANO );
	                textHelper.setCssClase( "camposformtext" );
                        textHelper.setEditable( false );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>

                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Num. Documento:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_DOCUMENTO );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_DOCUMENTO );
	                textHelper.setCssClase( "camposformtext" );
                        textHelper.setEditable( true );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>



                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Tipo Documento:</div></td>
                    <td width="200">
                    <%

                    try {

                      docHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_TIPODOC );
                      docHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_TIPODOC );
                      docHelper.setCssClase( "camposformtext" );
                      docHelper.render( request, out );

                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Nombre:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_NOMBRE );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_NOMBRE );
	                textHelper.setCssClase( "camposformtext" );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>







                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Apellido<sub>1</sub>:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_APELLIDO1 );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_APELLIDO1 );
	                textHelper.setCssClase( "camposformtext" );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>




                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Apellido<sub>2</sub>:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_APELLIDO2 );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_APELLIDO2 );
	                textHelper.setCssClase( "camposformtext" );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>






              

                <tr>
                  <td colspan="5">
                    <hr />
                  </td>
                </tr>

                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Salvedad <sub>[anotaciones]</sub>:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextAreaHelper textHelper = new TextAreaHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__SALVEDAD_TX );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__SALVEDAD_TX );
	                textHelper.setCssClase( "camposformtext" );
                        textHelper.setCols( "50" );
                        textHelper.setRows( "2" );
			textHelper.render( request,out );



                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>



            </table>


                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">



	                    <a href="javascript:js_OnEvent_Back( 'EDIT', EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION  );">
	                     <img alt="regresar a la lista de folios"   src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" />
	                    </a>
                    </td>
                    <td>&nbsp;</td>
                    <td width="155">

	                    <a href="javascript:js_OnEvent_Apply( 'EDIT', EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION );">
	                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" />
	                    </a>

                    </td>
                    <td width="155">
                    &nbsp;
<!-- 
	                    <a href="javascript:js_OnEvent_Cancel( 'EDIT', EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION );">
	                     <img alt="cierra la ventana actual; no realiza refresh"   src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="139" height="21" border="0" />
	                    </a>
 -->
                    </td>
                  </tr>





                </table>

            </FORM>

<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- END REGION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>


        <%

        }

        else {

        %>




           <table width="100%" class="camposform">

                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Id Ciudadano:</div></td>
                    <td width="200">
                    <%

                    try {

                    	TextHelper textHelper = new TextHelper();
                        textHelper.setId(     AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_IDCIUDADANO );
	                textHelper.setNombre( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__CIUDADANO_IDCIUDADANO );
	                textHelper.setCssClase( "camposformtext" );
                        textHelper.setEditable( false );
			textHelper.render( request,out );
                    }
                    catch(HelperException re){
			out.println("ERROR " + re.getMessage());
                    }

                    %>

		  </td>
                  <td>&nbsp;</td>

                </tr>


                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >NOTA:</div></td>
                    <td width="200">
                      Debe verificar los bloqueos antes de poder editar el ciudadano.
		  </td>
                  <td>&nbsp;</td>

            </table>



        <%

        }
        %>










            </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
        </table>

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
