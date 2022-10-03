<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.registro.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.Folio" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCorreccion" %>
<%@page import="java.util.*" %>
<%
	TablaSegregacionesConfirmacionHelper tabla = new TablaSegregacionesConfirmacionHelper();
	tabla.setEsVerificacion(true);
	tabla.setMostrarMatriculasGeneradas(false);
	Folio folio = (Folio)session.getAttribute(gov.sir.core.web.WebKeys.FOLIO);
	List foliosSegregados = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);
	boolean segregacionExitosa = true;
    String mensaje = "Los folios se segregaron exitosamente";
    if(foliosSegregados==null){
    	segregacionExitosa = false;
    	mensaje = "Los folios no se pudieron segregar";
    }
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ASISTENTE SEGREGACIÓN - PASO 4 - FOLIOS SEGREGADOS</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Segregar</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_desenglobar.gif" width="16" height="21"></td>
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


		<%try{
			tabla.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}%>


          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        </tr>
        <tr>
        	<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
         	<td valign="middle" bgcolor="#79849B" class="tdtablacentral" align="center">
         		<image src="<%=request.getContextPath()%>/jsp/images/<%=segregacionExitosa?"satisfactorio_animated.gif":"error_animated.gif"%>"/>
         		&nbsp;
         		<b><%=mensaje%></b>
         	</td>
         	<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <hr class="linehorizontal" />





<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%

Turno local_Turno;

Solicitud local_Solicitud;


local_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
local_Solicitud = (Solicitud)local_Turno.getSolicitud();

%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>




<!-- static resources -->
<%-- --%>

<%-- Bug 3563 --%>


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

     if( formObject == null ) {
       alert( 'form-undefined' );
	   return;
     }
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

	var actionField = "<%=WebKeys.ACCION%>";
	var PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION = "<%= AWModificarFolio.PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION %>";
</script>

<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEvent2( formName, actionValue, handlerUri ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.setFormAction( handlerUri );
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


<%-- --%>




          <form method="post" action="calificacion.do" name="SEGREGACION" id="SEGREGACION">
          	<input type="hidden" name="<%=gov.sir.core.web.WebKeys.ITEM%>" value="<%=folio.getIdMatricula()%>">
          	<input type="hidden" name="<%=gov.sir.core.web.WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.registro.AWCalificacion.CALIFICAR_FOLIO%>" >
            <table width="100%" class="camposform">
              <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                <td>









<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- SOF:REGION TYPE="CONDITIONAL" --%>

<%-- (condition) --%>
<%
if( local_Solicitud instanceof SolicitudRegistro ) {
%>


          <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" name="Folio" width="139" height="21" border="0" id="Folio" />


<%
}
%>
<%-- EOF:REGION--%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>



<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- SOF:REGION TYPE="CONDITIONAL" --%>

<%-- (condition) --%>
<%
if( local_Solicitud instanceof SolicitudCorreccion ) {
%>


                    <a href="javascript:js_OnEvent2( 'SEGREGACION', PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION, 'modificacion.do' );">

                      <img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif"
                         name="Folio" width="139" height="21" border="0"
                         id="Folio"
                         alt="segregar-evt"
                      />
                    </a>

<%
}
%>
<%-- EOF:REGION--%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>




                </td>
              </tr>
            </table>
            </form>
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
