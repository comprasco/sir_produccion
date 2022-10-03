<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultaFolio" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_MesaControl" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_CopiaSalvedadOptions"  %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_VerAlertasOptions" %>

<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"  %>


<%@page import="gov.sir.core.web.WebKeys"%>


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

   var CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION  = "<%= AwCorr_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION %>";

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEvent2( formName, actionValue, processorId ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setFormAction( processorId );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEventConfirm( formName, actionValue, msg ) {

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

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"></link>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<!-- activate grid: border="5" -->

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

  <%--

  <tr>
    <td>&nbsp;</td>
    <td colspan="3">
      &nbsp;
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td> Anotaci&oacute;n Tipo Canceladora </td>
<td width="15">&nbsp;
</td>
</tr>
</table>

    </td>
  </tr>
</table>

      &nbsp;
    </td>
    <td>&nbsp;</td>
  </tr>

  --%>




  <tr>
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral" style="font-weight:bold;">Correcciones: Alertas </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table>
    </td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>

<!-- sof:line -->

  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
      &nbsp;
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
   </tr>
<!-- eof:line -->

  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02" style="padding-bottom:0px;margin-bottom:0px" >

<div style="margin-left:6px" style="font-size:xx-small;background-color:#ffffff;"><a id="demoPanelLink" href="javascript:toggleSlide();" >Ocultar panel de ayuda</a></div>

<div id="demosMenu" ondblclick="toggleSlide()" style="margin-left:0px;margin-top:2px;padding-left:6px;background-color:#ffffff;width:100%" >

  <%--SOF:REGION --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:4px;padding-right:20px;padding-bottom:0px;" >

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td>
Se muestra a continuacion el conjunto de folios del turno y
un panel deslizable con el conjunto de folios sobre los cuales no se han registrado cambios.

</td>
<td width="15">&nbsp;
</td>
</tr>
</table>

    </td>
  </tr>
</table>
  <%--EOF:REGION --%>



</div>




<!--


-->

    </td>

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
   </tr>


<!-- -->



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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">: Detalles :</td>
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
            </table>
          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>

        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              <br>

<!--
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >
-->


            <form
              action="corr-corrsimplemain-veralertasoptions.do"
              method="post"
              id="CORRECCION1"
              name="CORRECCION1"
            >
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="" />



<!--

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td>

-->

<%-- :: REGION --%>

<%

// transfer-objects 2 plain objects

        // ::

        final String PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS;

        final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES      = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES;
        final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES;
        final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS   = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS;
        final String PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS    = "PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS" ;

        // Generate ---------------------------------------------------------------------------------------

        jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper(
            session
          , PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS
          , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES
          , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES
          , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS
        );


        // ::

        final String PARAM__OBJS_STEP1FOLIOSINTURNO = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__OBJS_STEP1FOLIOSINTURNO;

        final String PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES      = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES;
        final String PARAM__LOCAL_STEP1FOLIOSINTURNO_DISPLAYVALUES = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNO_DISPLAYVALUES;
        final String PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS   = AwCorr_CorrSimpleMain_VerAlertasOptions.PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS;
        final String PARAM__ITEM_STEP1FOLIOSINTURNO_SELECTEDIDS    = "PARAM__ITEM_STEP1FOLIOSINTURNO_SELECTEDIDS" ;

        // Generate ---------------------------------------------------------------------------------------

        jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper(
            session
          , PARAM__OBJS_STEP1FOLIOSINTURNO
          , PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES
          , PARAM__LOCAL_STEP1FOLIOSINTURNO_DISPLAYVALUES
          , PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS
        );


/*

        // List< Folio >
        List local_Result_FoliosInTurnoConSalvedades;
        Iterator local_Result_FoliosInTurnoConSalvedadesIterator;
        Folio local_Result_FoliosInTurnoConSalvedadesElement;

        if( null == ( local_Result_FoliosInTurnoConSalvedades = (List)session.getAttribute( PARAM__OBJS_FOLIOSINTURNOCONSALVEDADES ) ) ) {
          local_Result_FoliosInTurnoConSalvedades = new ArrayList();
        }

        // Transform { List< Folio > } 2 { List< String > ids + List< String > display }
        List local_View_FoliosInTurnoConSalvedadesIdValues;
        List local_View_FoliosInTurnoConSalvedadesDisplayValues;
        List local_View_FoliosInTurnoConSalvedadesSelectedIds;

        local_View_FoliosInTurnoConSalvedadesIdValues = new ArrayList();
        local_View_FoliosInTurnoConSalvedadesDisplayValues = new ArrayList();
        local_View_FoliosInTurnoConSalvedadesSelectedIds = new ArrayList();


        local_Result_FoliosInTurnoConSalvedadesIterator = local_Result_FoliosInTurnoConSalvedades.iterator();

        for( ;local_Result_FoliosInTurnoConSalvedadesIterator.hasNext(); ) {
          local_Result_FoliosInTurnoConSalvedadesElement = (Folio)local_Result_FoliosInTurnoConSalvedadesIterator.next();
          local_View_FoliosInTurnoConSalvedadesIdValues.add( local_Result_FoliosInTurnoConSalvedadesElement.getIdMatricula() );
          local_View_FoliosInTurnoConSalvedadesDisplayValues.add( local_Result_FoliosInTurnoConSalvedadesElement.getIdMatricula() );
        } // for

        // selialize 2 session

        session.setAttribute( PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_IDVALUES, local_View_FoliosInTurnoConSalvedadesIdValues );
        session.setAttribute( PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_DISPLAYVALUES, local_View_FoliosInTurnoConSalvedadesDisplayValues );
        session.setAttribute( PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS, local_View_FoliosInTurnoConSalvedadesSelectedIds );
*/



%>



<%-- :: REGION --%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- :: SOF:REGION (ACCORDION) --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<style
  type="text/css"
>

.accordionTabTitleBar {
   font-size           : 12px;
   padding             : 4px 6px 4px 6px;
   border-style        : solid none solid none;
   border-top-color    : #BDC7E7; /* #5D687D;*/
   border-bottom-color : #182052;
   border-width        : 1px 0px 1px 0px;
}

.accordionTabTitleBarHover {
   font-size        : 11px;
   background-color : #5D687D;
}

.accordionTabContentBox {
   font-size        : 11px;
   border           : 1px solid #1f669b;
   border-top-width : 0px;
   padding          : 0px 8px 0px 8px;
   /*background-color            : #ffffff;*/
   background-color: #ECF0F9;

}

</style>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js"
>
</script>

<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/rico.js"
>
</script>

<script
  type="text/javascript"
>


   var saveHeight;
   var showing = true;

   function toggleSlide() {
      if ( showing )
         { slideMenuUp(); showing = false; }
      else
         { slideMenuDown(); showing = true; }
   }

   function slideMenuUp() {
      var menu = $('demosMenu');
      saveHeight = menu.offsetHeight;

      menu.style.overflow = "hidden";
      new Rico.Effect.Size( menu, null, 1, 120, 8 );

      $('demoPanelLink').innerHTML = "Mostrar panel de ayuda";
   }

   function slideMenuDown() {
      var menu = $('demosMenu');
      new Rico.Effect.Size( menu, null, saveHeight, 120, 8, {complete:function() { $(menu).style.overflow = "visible"; }} );
      $('demoPanelLink').innerHTML = "Ocultar panel de ayuda";
   }

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>






<br />
<%-- SOF: ACCORDION STRUCTURE --%>

<div id="accordionDiv" >

  <%-- SOF:Panel0 --%>
 <%
  List local_Result_FoliosInTurno;
  List local_Result_FoliosInTurnoSinCambios;

  if( null == ( local_Result_FoliosInTurno = (List)session.getAttribute( PARAM__OBJS_STEP1FOLIOSINTURNO ) ) ) {
    local_Result_FoliosInTurno = new ArrayList();
  }
  if( null == ( local_Result_FoliosInTurnoSinCambios = (List)session.getAttribute( PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS ) ) ) {
    local_Result_FoliosInTurnoSinCambios = new ArrayList();
  }


 %>


   <div id="local_AccordionPanel0" style="margin:0px;">
     <div id="local_AccordionPanel0_Header" class="accordionTabTitleBar">

       <table width="100%" class="titulotbgral" style="font-weight:normal;">
         <tr>
           <td width="150"><span style="font-weight:normal;">Folios en el turno</span></td>
           <td>&nbsp;</td>
           <td width="150"><span style="font-weight:normal;">(<%=local_Result_FoliosInTurno.size() %> Items)</span></td>
           <td width="50">&nbsp;</td>
         </tr>
       </table>

      </div>
      <div id="local_AccordionPanel0_Content" class="accordionTabContentBox">
<%


              try {

                gov.sir.core.web.helpers.comun.TablaMatriculaHelper local_View_TableHelper
                  = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

                local_View_TableHelper.setInputName( PARAM__ITEM_STEP1FOLIOSINTURNO_SELECTEDIDS   ); //  WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION
                local_View_TableHelper.setListName(  PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES );

                // permite colocar los valores previamente seleccionados
                local_View_TableHelper.setSelectedValues_Enabled( true );
                local_View_TableHelper.setSelectedValues_Ids_Key( PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS );

                local_View_TableHelper.setContenidoCelda( gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_PLAIN );
                local_View_TableHelper.setAlineacionTodasCeldas( TablaMatriculaHelper.ALINEACION_IZQUIERDA );
                local_View_TableHelper.setColCount( 1 );

                local_View_TableHelper.render(request, out);
              }
              catch( HelperException e ){
                out.println("ERROR " + e.getMessage() );
              }




 %>

      </div>
   </div>

  <%-- EOF:Panel0 --%>

  <%-- SOF:Panel1 --%>

   <div id="local_AccordionPanel1" >
     <div id="local_AccordionPanel1_Header" class="accordionTabTitleBar" >

       <table width="100%" class="titulotbgral" style="font-weight:normal;">
         <tr>
           <td width="150"><span style="font-weight:normal;">Folios sin cambios</span></td>
           <td>&nbsp;</td>
           <td width="150"><span style="font-weight:normal;">(<%=local_Result_FoliosInTurnoSinCambios.size() %> Items)</span></td>
           <td width="50">

             <%
                if( local_Result_FoliosInTurnoSinCambios.size() > 0 ) {
             %>
              <img alt="[]" src="<%=request.getContextPath()%>/jsp/images/privileged/4/stock-warning-16.gif"/>
             <%
             }
             %>
             &nbsp;
            </td>
         </tr>
       </table>


      </div>
      <div id="local_AccordionPanel1_Content"  class="accordionTabContentBox">





<%


              try {

                gov.sir.core.web.helpers.comun.TablaMatriculaHelper local_View_TableHelper
                  = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

                local_View_TableHelper.setInputName( PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS   ); //  WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION
                local_View_TableHelper.setListName(  PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES );

                // permite colocar los valores previamente seleccionados
                local_View_TableHelper.setSelectedValues_Enabled( true );
                local_View_TableHelper.setSelectedValues_Ids_Key( PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS );

                local_View_TableHelper.setContenidoCelda( gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_PLAIN );
                local_View_TableHelper.setAlineacionTodasCeldas( TablaMatriculaHelper.ALINEACION_IZQUIERDA );
                local_View_TableHelper.setColCount( 1 );

                local_View_TableHelper.render(request, out);
              }
              catch( HelperException e ){
                out.println("ERROR " + e.getMessage() );
              }




 %>






      </div>
   </div>

  <%-- EOF:Panel1 --%>

</div>

<%-- EOF: ACCORDION STRUCTURE --%>

























<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">

	new Rico.Accordion( $('accordionDiv'), {panelHeight:300,collapsedBg:'#79849B',expandedBg:'#79849B',color:'#ffffff',hoverBg:'#5D687D'} );

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- :: EOF:REGION (ACCORDION) --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>



































<!--
</td>
<td width="15">&nbsp;
</td>
</tr>
</table>
-->
<!--

    </td>
  </tr>
</table>
-->
              <br />
            </form>

<%-- SOF:REGION --%>

    <span class="bgnsub" style="background:" >Acciones</span>


        <%-- ACCIONES --%>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" alt="" /></td>

                  <%-- SOF:BUTTON --%>
                  <%-- EOF:BUTTON --%>
                  <%-- SOF:BUTTON --%>
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION );">
                     <img alt="[back]"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  <%-- EOF:BUTTON --%>


                  <%-- SOF:BUTTON --%>
                  <%--
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  --%>
                  <%-- EOF:BUTTON --%>

                  <%-- SOF:BUTTON --%>
                  <%--
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  --%>
                  <%-- EOF:BUTTON --%>

                  <%-- SOF:BUTTON --%>
                  <%--
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  --%>
                  <%-- EOF:BUTTON --%>

                  <td>&nbsp;</td>
                </tr>
              </table>
<%--        --%>
<%-- EOF:REGION --%>


          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <%-- --%>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
        <%-- --%>
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


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- class methods --%>
<%!


public static void
jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper(
    HttpSession session
  , String sessionSourceKey_FoliosList
  , String sessionTargetKey_IdValues
  , String sessionTargetKey_DisplayValues
  , String sessionTargetKey_SelectedIdValues
) {



        // List< Folio >
        List local_Result_FoliosInTurnoConSalvedades;
        Iterator local_Result_FoliosInTurnoConSalvedadesIterator;
        Folio local_Result_FoliosInTurnoConSalvedadesElement;

        if( null == ( local_Result_FoliosInTurnoConSalvedades = (List)session.getAttribute( sessionSourceKey_FoliosList ) ) ) {
          local_Result_FoliosInTurnoConSalvedades = new ArrayList();
        }

        // Transform ---------------------------------------------------------------------------------------
        // { List< Folio > } 2 { List< String > ids + List< String > display }
        List local_View_FoliosInTurnoConSalvedadesIdValues;
        List local_View_FoliosInTurnoConSalvedadesDisplayValues;
        List local_View_FoliosInTurnoConSalvedadesSelectedIds;

        local_View_FoliosInTurnoConSalvedadesIdValues = new ArrayList();
        local_View_FoliosInTurnoConSalvedadesDisplayValues = new ArrayList();

        if( null == ( local_View_FoliosInTurnoConSalvedadesSelectedIds = (List)session.getAttribute( sessionTargetKey_SelectedIdValues ) ) ) {
          local_View_FoliosInTurnoConSalvedadesSelectedIds = new ArrayList();
        }

        local_Result_FoliosInTurnoConSalvedadesIterator = local_Result_FoliosInTurnoConSalvedades.iterator();

        for( ;local_Result_FoliosInTurnoConSalvedadesIterator.hasNext(); ) {
          local_Result_FoliosInTurnoConSalvedadesElement = (Folio)local_Result_FoliosInTurnoConSalvedadesIterator.next();
          local_View_FoliosInTurnoConSalvedadesIdValues.add( local_Result_FoliosInTurnoConSalvedadesElement.getIdMatricula() );
          local_View_FoliosInTurnoConSalvedadesDisplayValues.add( local_Result_FoliosInTurnoConSalvedadesElement.getIdMatricula() );
        } // for

        // Serialize ---------------------------------------------------------------------------------------
        // serialize 2 session

        session.setAttribute( sessionTargetKey_IdValues, local_View_FoliosInTurnoConSalvedadesIdValues );
        session.setAttribute( sessionTargetKey_DisplayValues, local_View_FoliosInTurnoConSalvedadesDisplayValues );
        session.setAttribute( sessionTargetKey_SelectedIdValues, local_View_FoliosInTurnoConSalvedadesSelectedIds );
        // -------------------------------------------------------------------------------------------------

} // end-method: jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper






%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
