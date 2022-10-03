<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultaFolio" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_MesaControl" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_CopiaSalvedadOptions"  %>

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

   var CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION  = "<%= AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION %>";
   var CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION    = "<%= AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION   %>";
   var CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION    = "<%= AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION   %>";

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral" style="font-weight:bold;">Correcciones: Copiar Salvedad Folio </td>
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



<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:4px;padding-right:20px;padding-bottom:0px;" >

  <%--SOF:REGION --%>

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td>

  <%-- SOF:MENU--%>

  <ul>
    <li>Paso 1: Escoger Folio Origen</li>
    <li><b>Paso 2: Seleccionar Folios Destino / Edicion Texto</b></li>
    <li>Paso 3: Confirmar </li>
  </ul>

  <%-- EOF:MENU--%>


</td>
<td width="15">&nbsp;
</td>
</tr>
</table>

  <%--EOF:REGION --%>

    </td>
  </tr>
</table>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Paso 2: Seleccionar Folios Destino / Edicion Texto</td>
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

            <form
              action="corr-corrsimplemain-copiasalvedadoptions.do"
              method="post"
              id="CORRECCION1"
              name="CORRECCION1"
            >
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="" />

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td>
<%-- :: REGION --%>

<%

// transfer-objects 2 plain objects

        // Generate ---------------------------------------------------------------------------------------

        final String PARAM__OBJS_STEP2FOLIOSOURCE              = AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__OBJS_STEP2FOLIOSOURCE;

        final String PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST = AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST;

        final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES      = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES";
        final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES";
        final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS   = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS";
        final String PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS    = AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS;


        // load table data

        jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper(
            session
          , PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST
          , PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES
          , PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES
          , PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS
        );

%>

Texto:
                   	<%

                        try {


                          TextAreaHelper area = new TextAreaHelper();
                          area.setCols( "140" );
                          area.setRows( "5" );
                          area.setCssClase( "camposformtext" );
                          area.setId(     AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__ITEM_STEP2SALVEDADTXID );
                          area.setNombre( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__ITEM_STEP2SALVEDADTXID );
                          area.setReadOnly( false );
                          area.render( request, out );
                        }
                        catch( HelperException re ){
                          out.println("ERROR " + re.getMessage());
                        }

                        %>
<br />
Folios Destino:

<%


              try {

                gov.sir.core.web.helpers.comun.TablaMatriculaHelper local_View_TableHelper
                  = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

                local_View_TableHelper.setInputName( PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS   ); //  WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION
                local_View_TableHelper.setListName(  PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES );

                // permite colocar los valores previamente seleccionados
                local_View_TableHelper.setSelectedValues_Enabled( true );
                local_View_TableHelper.setSelectedValues_Ids_Key( PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS );

                local_View_TableHelper.setContenidoCelda( gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_CHECKBOX );
                local_View_TableHelper.setAlineacionTodasCeldas( TablaMatriculaHelper.ALINEACION_IZQUIERDA );
                local_View_TableHelper.setColCount( 1 );

                local_View_TableHelper.render(request, out);
              }
              catch( HelperException e ){
                out.println("ERROR " + e.getMessage() );
              }




 %>
<%-- :: REGION --%>





</td>
<td width="15">&nbsp;
</td>
</tr>
</table>

    </td>
  </tr>
</table>


              <br />
            </form>

<%-- SOF:REGION --%>

    <span class="bgnsub" style="background:" >Acciones</span>


        <%-- ACCIONES --%>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" alt="" /></td>

                  <%-- SOF:BUTTON --%>
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0" />
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
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  <%-- EOF:BUTTON --%>


                  <%-- SOF:BUTTON --%>
                  <td width="50">
                    <a href="javascript:js_OnEvent( 'CORRECCION1', CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
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
