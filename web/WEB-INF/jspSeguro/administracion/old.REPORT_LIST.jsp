<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.administracion.reportes.LegendHelper" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes" %>



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
                  <tr>
                    <td>&nbsp</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-01.param-form.view" class="links">Reporte 01: Acta de reparto notarial</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "Despliega un formato de acta predefinido; Usar el enlace para ir a la forma de parametros" );
					legendHelper.setRegionId( "report01" );
					legendHelper.setRegionName( ": Acta de reparto notarial" );

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
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-04.param-form.view" class="links">Reporte 04: Diario desanotador de documentos del d&iacute;a</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "Despliega para cualquier hora de un dia determinado, los turnos para la solicitud de registro que han sido desanotados para la fecha (que los cambios se han hecho definitivos). Estos resultados se muestran para el circulo seleccionado" );
					legendHelper.setRegionId( "report04" );
					legendHelper.setRegionName( ": Diario desanotador de documentos del d&iacute;a" );

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
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-05.param-form.view" class="links">Reporte 05: Diario mayores valores y devoluciones del d&iacute;a</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report05" );
					legendHelper.setRegionName( ": Diario mayores valores y devoluciones del d&iacute;a" );

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
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-06.param-form.view" class="links">Reporte 06: Diario radicador de certificados del d&iacute;a</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report06" );
					legendHelper.setRegionName( ": Diario radicador de certificados del d&iacute;a" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-6B.param-form.view" class="links">Reporte 6B: Diario radicador de certificados masivos del d&iacute;a</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report6B" );
					legendHelper.setRegionName( ": Diario radicador de certificados masivos del d&iacute;a" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-07.param-form.view" class="links">Reporte 07: Diario radicador de certificados - Superdocumento</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report07" );
					legendHelper.setRegionName( ": Diario radicador de certificados - Superdocumento" );

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
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-08.param-form.view" class="links">Reporte 08: Diario radicador de consultas</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report08" );
					legendHelper.setRegionName( ": Diario radicador de consultas" );

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
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-09.param-form.view" class="links">Reporte 09: Diario radicador de documentos del d&iacute;a</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report09" );
					legendHelper.setRegionName( ": Diario radicador de documentos del d&iacute;a" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-10.param-form.view" class="links">Reporte 10: Estad&iacute;sticas de certificados - Superdocumento (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report10" );
					legendHelper.setRegionName( ": Estad&iacute;sticas de certificados - Superdocumento (d&iacute;a xx al d&iacute;a yy)" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-11.param-form.view" class="links">Reporte 11: Estad&iacute;sticas por actos de registro (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report11" );
					legendHelper.setRegionName( ": Estad&iacute;sticas por actos de registro (d&iacute;a xx al d&iacute;a yy)" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-15.param-form.view" class="links">Reporte 15: Informe cuentas por pagar devoluciones (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report15" );
					legendHelper.setRegionName( ": Informe cuentas por pagar devoluciones (d&iacute;a xx al d&iacute;a yy)" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-16.param-form.view" class="links">Reporte 16: Informe cuentas por pagar mayores valores (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report16" );
					legendHelper.setRegionName( ": Informe cuentas por pagar mayores valores (d&iacute;a xx al d&iacute;a yy)" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-17.param-form.view" class="links">Reporte 17: Informe de consignaciones bancarias</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report17" );
					legendHelper.setRegionName( ": Informe de consignaciones bancarias" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-18.param-form.view" class="links">Reporte 18: Informe de devoluciones pagadas en cheque (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report18" );
					legendHelper.setRegionName( ": Informe de devoluciones pagadas en cheque (d&iacute;a xx al d&iacute;a yy)" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-19.param-form.view" class="links">Reporte 19: Informe de mayores valores pagados (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report19" );
					legendHelper.setRegionName( ": Informe de mayores valores pagados (d&iacute;a xx al d&iacute;a yy)" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-20.param-form.view" class="links">Reporte 20: Informe de otros recaudos (d&iacute;a xx al d&iacute;a yy)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report20" );
					legendHelper.setRegionName( ": Informe de otros recaudos (d&iacute;a xx al d&iacute;a yy)" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-21.param-form.view" class="links">Reporte 21: Informe de devoluciones ingresadas del d&iacute;a xx al d&iacute;a yy</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report21" );
					legendHelper.setRegionName( ": Informe de devoluciones ingresadas del d&iacute;a xx d&iacute;a al yy" );

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




                 <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-22.param-form.view" class="links">Reporte 22: Informe general de mayores valores registrados y pagados del d&iacute;a xx</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report22" );
					legendHelper.setRegionName( ": Informe general de mayores valores registrados y pagados del d&iacute;a xx" );

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




                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-24.param-form.view" class="links">Reporte 24: Informe mayores valores ingresados del d&iacute;a xx al d&iacute;a yy</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report24" );
					legendHelper.setRegionName( ": Informe mayores valores ingresados del d&iacute;a xx al d&iacute;a yy" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-25.param-form.view" class="links">Reporte 25: Informe recaudos del d&iacute;a (tiracaja)</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report25" );
					legendHelper.setRegionName( ": Informe recaudos del d&iacute;a (tiracaja)" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-26.param-form.view" class="links">Reporte 26: Minutas entregadas para municipio xx</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report26" );
					legendHelper.setRegionName( ": Minutas entregadas para municipio xx" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-27.param-form.view" class="links">Reporte 27: Minutas pendientes de entrega</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report27" );
					legendHelper.setRegionName( ": Minutas pendientes de entrega" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-30.param-form.view" class="links">Reporte 30: Notarias que no han recibido reparto notarial</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report30" );
					legendHelper.setRegionName( ": Notarias que no han recibido reparto notarial" );

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







                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-31.param-form.view" class="links">Reporte 31: Radicador de minutas por fecha, notaria y categoria</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report31" );
					legendHelper.setRegionName( ": Radicador de minutas por fecha, notaria y categoria" );

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







                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-32.param-form.view" class="links">Reporte 32: Radicador de minutas por n&uacute;mero de reparto</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report32" );
					legendHelper.setRegionName( ": Radicador de minutas por n&uacute;mero de reparto" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-37.param-form.view" class="links">Reporte 37: Relaci&oacute;n de documentos de antiguo sistema</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report37" );
					legendHelper.setRegionName( ": Relaci&oacute;n de documentos de antiguo sistema" );

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

<%--
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-38.param-form.view" class="links">Reporte 38: Relacion de documentos desanotados</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report38" );
					legendHelper.setRegionName( ": Relacion de documentos desanotados" );

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
--%>

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-39.param-form.view" class="links">Reporte 39: Relaci&oacute;n de documentos en correcci&oacute;n</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report39" );
					legendHelper.setRegionName( ": Relaci&oacute;n de documentos en correcci&oacute;n" );

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

<%--
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-41.param-form.view" class="links">Reporte 41: Relacion de documentos para calificacion</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report41" );
					legendHelper.setRegionName( ": Relacion de documentos para calificacion" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-42.param-form.view" class="links">Reporte 42: Relacion de documentos para confrontacion</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report42" );
					legendHelper.setRegionName( ": Relacion de documentos para confrontacion" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-44.param-form.view" class="links">Reporte 44: Relación de solicitudes de certificados a ventanilla</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report44" );
					legendHelper.setRegionName( ": Relación de solicitudes de certificados a ventanilla" );

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

--%>


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-45.param-form.view" class="links">Reporte 45: Relaci&oacute;n de solicitudes de certificados para devolver al p&uacute;blico</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report45" );
					legendHelper.setRegionName( ": Relaci&oacute;n de solicitudes de certificados para devolver al p&uacute;blico" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-46.param-form.view" class="links">Reporte 46: Relaci&oacute;n de turnos con mayor valor vencido</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report46" );
					legendHelper.setRegionName( ": Relaci&oacute;n de turnos con mayor valor vencido" );

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

<%--
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-48.param-form.view" class="links">Reporte 48: Relación solicitud certificados, antiguo sistema </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report48" );
					legendHelper.setRegionName( ": solicitud certificados, antiguo sistema " );

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


--%>


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-49.param-form.view" class="links">Reporte 49: Reporte de certificados reimpresos </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report49" );
					legendHelper.setRegionName( ": Reporte de certificados reimpresos" );

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

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-51.param-form.view" class="links">Reporte 51: Resumen diario de ingresos y egresos</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report51" );
					legendHelper.setRegionName( ": Resumen diario de ingresos y egresos" );

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






                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-57.param-form.view" class="links">Reporte 57: Diario radicador de matriculas</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "Despliega el conjunto de matriculas que se han creado para un dia determinado" );
					legendHelper.setRegionId( "report57" );
					legendHelper.setRegionName( ": Diario radicador de matriculas" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-69.param-form.view" class="links">Reporte 69: Diario desanotador de certificados</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report69" );
					legendHelper.setRegionName( ": Diario desanotador de certificados" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-74.param-form.view" class="links">Reporte 74: Relaci&oacute;n de ingresos por certificados</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report74" );
					legendHelper.setRegionName( ": Relaci&oacute;n de ingresos por certificados" );

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


                 <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-79.param-form.view" class="links">Reporte 79: Informe de devoluciones pagadas del d&iacute;a xx al d&iacute;a yy </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report79" );
					legendHelper.setRegionName( ": Informe de devoluciones pagadas del d&iacute;a xx al d&iacute;a yy" );

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





<%
/*					-----------------------------------------------------------------------------------
 					PENDIENTES PARA SUBIR CUANDO ESTEN LOS REPORTES
					-----------------------------------------------------------------------------------

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-79.param-form.view" class="links">Reporte 79: Informe devoluciones pagadas</a> </td>
                    <td>
*/
%>
                    <%
/*
					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report79" );
					legendHelper.setRegionName( ": Informe devoluciones pagadas." );

					try {

	  					 legendHelper.render( out );
					}
					catch( java.io.IOException e ){
						out.write( e.toString() );
					}
*/
                    %>
<%
/*
                    </td>

                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">&nbsp;</td>
                  </tr>
*/
%>
<%--                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-81.param-form.view" class="links">Reporte 81: Reporte Generico de relacion</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report81" );
					legendHelper.setRegionName( ": Reporte Generico de relacion para un rango de fechas." );

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
--%>

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-87.param-form.view" class="links">Reporte 87: Diario de documentos a operativa por correcci&oacute;n (correcciones enviadas a otras dependencias)</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report87" );
					legendHelper.setRegionName( ": Diario de documentos a operativa por correcci&oacute;n (correcciones enviadas a otras dependencias)" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-88.param-form.view" class="links">Reporte 88: Certificados entregados en el d&iacute;a</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report88" );
					legendHelper.setRegionName( ": Certificados entregados en el d&iacute;a" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-89.param-form.view" class="links">Reporte 89: Listado de documentos entregados el d&iacute;a xx</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report89" );
					legendHelper.setRegionName( ": Listado de documentos entregados el d&iacute;a xx" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-90.param-form.view" class="links">Reporte 90: Certificados desanotados en el d&iacute;a</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report90" );
					legendHelper.setRegionName( ": Certificados desanotados en el d&iacute;a" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-91.param-form.view" class="links">Reporte 91: Relaci&oacute;n de ingresos del impuesto de registro</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report91" );
					legendHelper.setRegionName( ": Relaci&oacute;n de ingresos del impuesto de registro" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-92.param-form.view" class="links">Reporte 92: Estad&iacute;sticas de impuesto (por acto) </a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report92" );
					legendHelper.setRegionName( ": Estad&iacute;sticas de impuesto (por acto)" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-93.param-form.view" class="links">Reporte 93: Relaci&oacute;n de documentos pendientes (por usuario)</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report93" );
					legendHelper.setRegionName( ": Relaci&oacute;n de documentos pendientes (por usuario)" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-94.param-form.view" class="links">Reporte 94: Informe recaudos de consultas del d&iacute;a xx realizados por yy</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report94" );
					legendHelper.setRegionName( ": Informe recaudos de consultas del d&iacute;a xx realizados por yy" );

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

<%--
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-97.param-form.view" class="links">Reporte 97: Relacion de documentos a desanotacion</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report97" );
					legendHelper.setRegionName( ": Relacion de documentos a desanotacion" );

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

---%>

                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-99.param-form.view" class="links">Reporte 99: Ingresos diarios por documentos</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report99" );
					legendHelper.setRegionName( ": Reporte 99: Relaci&oacute;n de ingresos por documentos" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-109.param-form.view" class="links">Reporte 109: Informe de radicaci&oacute;n de minutas por rango de fechas </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report109" );
					legendHelper.setRegionName( ": Informe de radicaci&oacute;n de minutas por rango de fechas" );

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




                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-110.param-form.view" class="links">Reporte 110: Minutas pendientes de reparto</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report110" );
					legendHelper.setRegionName( ": Minutas pendientes de reparto" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-121.param-form.view" class="links">Reporte 121: Estad&iacute;stica por notarias h&aacute;biles para reparto notarial</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report121" );
					legendHelper.setRegionName( ": Estad&iacute;stica por notarias h&aacute;biles para reparto notarial" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-123.param-form.view" class="links">Reporte 123: Estad&iacute;sticas por contratos sujetos a reparto notarial desde xx hasta yy</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report123" );
					legendHelper.setRegionName( ": Estad&iacute;sticas por contratos sujetos a reparto notarial desde xx hasta yy" );

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





                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-124.param-form.view" class="links">Reporte 124: Estad&iacute;sticas por categorias vigentes de reparto notarial</a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report124" );
					legendHelper.setRegionName( ": Estad&iacute;sticas por categorias vigentes de reparto notarial" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-142.param-form.view" class="links">Reporte 142: Relaci&oacute;n de solicitud de folio para confrontaci&oacute;n </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report142" );
					legendHelper.setRegionName( ": Relaci&oacute;n de solicitud de folio para confrontaci&oacute;n" );

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


                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-145.param-form.view" class="links">Reporte 145: Informe consignaciones bancarias del d&iacute;a </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report145" );
					legendHelper.setRegionName( ": Informe consignaciones bancarias del d&iacute;a" );

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



                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-151.param-form.view" class="links">Reporte 151: Diario radicador de testamentos </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report151" );
					legendHelper.setRegionName( ":  Diario radicador de testamentos" );

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




                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-rel-01.param-form.view" class="links">Reporte Rel 01: Reporte gen&eacute;rico de relaciones </a> </td>

                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "reportrel01" );
					legendHelper.setRegionName( ": Reporte gen&eacute;rico de relaciones" );

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


                  <%-- + + + + + + + + + + --%>
                  <%-- AHERRENO 19/08/2009 --%>
                  <%-- report_FOLIOS_MAS   --%>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-FOLIOS-MAS.param-form.view" class="links">Reporte: Folios Masivos </a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "Despliega el conjunto de matriculas que se han creado masivamente en un determinado rango de fechas" );
					legendHelper.setRegionId( "report_FOLIOS_MAS" );
					legendHelper.setRegionName( ": Folios Masivos" );

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


                  <%-- + + + + + + + + + + --%>
                  <%-- AHERRENO 22/09/2009 --%>
                  <%-- report_E01   --%>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-E01.param-form.view" class="links">Reporte E01: Estad&iacute;sticas por Indicadores de Registro</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "reportE01" );
					legendHelper.setRegionName( ": Estad&iacute;sticas por Indicadores de Registro" );

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

                  <%-- + + + + + + + + + + --%>
                  <%-- AHERRENO 22/09/2009 --%>
                  <%-- report_161   --%>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-161.param-form.view" class="links">Reporte 161: Actos de Registro en un Rango de Fecha</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "report161" );
					legendHelper.setRegionName( ": Actos de Registro en un Rango de Fecha" );

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

                  <%-- + + + + + + + + + + --%>
                  <%-- AHERRENO 26/04/2010 --%>
                  <%-- report_E02   --%>
                  <tr>
                    <td>&nbsp;</td>
                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''"><a href="admin.reportes.report-E02.param-form.view" class="links">Reporte E02: Estad&iacute;sticas Generales</a> </td>
                    <td>
                    <%

					legendHelper.setMessage( "" );
					legendHelper.setRegionId( "reportE02" );
					legendHelper.setRegionName( ": Estad&iacute;sticas Generales" );

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
                <hr class="linehorizontal">
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
