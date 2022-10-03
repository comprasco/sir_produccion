<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.administracion.reportes.LegendHelper" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes" %>
<%@page import="gov.sir.core.negocio.modelo.TipoPantalla" %>
<%@page import="java.util.List" %>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWListaReportes" %>
<%@page import="gov.sir.core.negocio.modelo.PantallaAdministrativa" %>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />

<body>



<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%-- legend resources --%>

  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/forms-lib.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/AnchorPosition.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/PopupWindow.js" ></script>
  <style type="text/css">

	  .forms-help {
		 border-style: dotted;
		 border-width: 1px;
		 padding: 5px;
		 background-color:#FFFFC0; /* light yellow */
		 width: 200px; /* otherwise IE does a weird layout */
		 z-index:1000; /* must be higher than forms-tabContent */
	   }


  </style>

<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>


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

   var REPORTE_00__BACK_ACTION       = "<%= AWReportes.REPORTE_00__BACK_ACTION %>";

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">

   function js_OnEventPrint( formName, actionValue, param1 ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     var msg = new String( "Confirma envio de turno: " + param1 + " a impresion" );

     if( confirm( msg ) ) {

       htmlForm.setValue( actionField, actionValue );
       htmlForm.setValue( printField , param1 );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

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

     if( confirm( msg) ) {

       htmlForm.setValue( actionField, actionValue );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

   }


</script>



<%

LegendHelper legendHelper = new LegendHelper();

java.util.HashMap legendHelper_parameters = new java.util.HashMap();

legendHelper_parameters.put( legendHelper.CONTEXT_PATH, request.getContextPath() );

legendHelper.configure( legendHelper_parameters );



%>


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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n </td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Lista de Reportes </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table>
    </td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10" /></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10" /></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10" /></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Administraci&oacute;n</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_llave.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>


          <!--  img -->
          <%--
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>

            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          --%>


          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Men&uacute; de Acceso a Reportes </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                <table width="90%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="360">Reportes</td>
                    <td width="20">&nbsp;</td>
                    <td width="100">&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                  </tr>

<%

// List< PantallaAdministrativa >
java.util.List local_PantallaAdministrativaList;
local_PantallaAdministrativaList = (java.util.List) session.getAttribute( AWListaReportes.PAGEITEMID__REPORTE00_PANTALLASACTIVAS );

if( null == local_PantallaAdministrativaList ) {
  local_PantallaAdministrativaList = new java.util.ArrayList();
} // if




%>

<%-- Load Data --%>
<%
  String local_MenuItem_PageLinkUri;
  String local_MenuItem_PageName   ;
  String local_MenuItem_Id         ;
  String local_MenuItem_HelpId     ;
  String local_MenuItem_HelpTitle  ;


  PantallaAdministrativa local_PantallaAdministrativaListElement;
%>


<%-- sof:block type="loop" --%>
<%-- head --%>
<%

Iterator iterator = local_PantallaAdministrativaList.iterator();
for( ; iterator.hasNext(); ){

  local_PantallaAdministrativaListElement = (PantallaAdministrativa)iterator.next();

%>
<%-- head --%>




<%

  local_MenuItem_PageLinkUri = local_PantallaAdministrativaListElement.getVista();//"admin.reportes.report-37.param-form.view";
  local_MenuItem_PageName    = local_PantallaAdministrativaListElement.getNombre();//"Reporte 37: Relacion de documentos de antiguo sistema";
  local_MenuItem_Id          = Long.toString( local_PantallaAdministrativaListElement.getIdPantallaAdministrativa() );
  local_MenuItem_HelpId      = "region" +  local_MenuItem_Id;
  local_MenuItem_HelpTitle   = ": " + local_PantallaAdministrativaListElement.getNombre() ;//"Esta es la relacion de documentos de antiguo sistema";
%>
                  <!-- sof:block display item -->
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="<%= local_MenuItem_PageLinkUri %>" class="links"><%= local_MenuItem_PageName     %></a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( local_MenuItem_HelpId );
					legendHelper.setRegionName( local_MenuItem_HelpTitle );

					try {

	  					 legendHelper.render( out );
					}
					catch( java.io.IOException e ){
						out.write( e.toString() );
					}

                    %>
                    </td>

                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                  </tr>
                  <!-- eof:block -->















<%-- eof:block type="loop" --%>
<%-- foot --%>
<%
}
%>
<%-- foot --%>

















                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                  </tr>

                </table>
                <hr class="linehorizontal" />
               	<form name="TEMPORAL_FORM" id="TEMPORAL_FORM" action="reportes.do" method="POST" >
               		<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" />
               	</form>

                <div style="width:90%" align="right">


	                <a href="javascript:js_OnEvent( 'TEMPORAL_FORM', REPORTE_00__BACK_ACTION );">
	                 <img alt="regresar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
	                </a>

                </div>

            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- class methods --%>
<%!

    // copiado de PANTALLA_PRINCIPAL_ADMINISTRACION
    // TODO: revisar.

  public List eliminarPantallasDuplicadas ( List listaPantallas )
  {
     List listaDefinitiva = new ArrayList();

     for( int i=0; i<listaPantallas.size(); i++ )
     {
          PantallaAdministrativa pantalla = (PantallaAdministrativa)listaPantallas.get(i);
          if ( isPantallaInList(pantalla, listaDefinitiva)==true)
          {
          }
          else
          {
             listaDefinitiva.add(pantalla);
          }
      }
      return listaDefinitiva;
  }

  public boolean isPantallaInList (PantallaAdministrativa pantalla, List lista)
  {
      for (int w= 0; w< lista.size(); w++)
      {
         PantallaAdministrativa pantAdmin = (PantallaAdministrativa) lista.get(w);
         if (pantAdmin.getIdPantallaAdministrativa() == pantalla.getIdPantallaAdministrativa())
         {
            return true;
         }

      }
      return false;
  }





%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
