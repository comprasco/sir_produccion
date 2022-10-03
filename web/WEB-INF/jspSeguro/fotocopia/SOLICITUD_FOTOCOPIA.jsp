<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDocumento"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudFotocopia"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCiudadano"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants" %>
<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*" %>
<!--FAVOR ORDENAR ESTOS IMPORTS DE LA MANERA LEGIBLE-->
<%@page import="java.util.*,gov.sir.core.web.WebKeys"%>

<%@page import="gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item" %>
<%@page import="gov.sir.core.web.helpers.fotocopia.DocumentosAsociadosTblDrawable_HelperWrapper" %>

<%
	
	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	vistaAnterior = vistaAnterior !=null 
		? "javascript:window.location.href='"+vistaAnterior+"';" 
		: "javascript:history.back();";


   // list-model

   List documentosAsociados=(List)session.getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );

   // temporal wrapper para acciones de tabla
   TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

	ListaElementoHelper tiposDocumento = new ListaElementoHelper();
	TextAreaHelper descripcion = new TextAreaHelper();
	descripcion.setCols("90");
	descripcion.setRows("2");
	ListaElementoHelper tiposFotocopia = new ListaElementoHelper();
	TextHelper numHojas = new TextHelper();
	TextHelper numCopias = new TextHelper();
	ListaElementoHelper docHelper = new ListaElementoHelper();
	TextHelper textHelper = new TextHelper();
        TextHelper txtHelper = new TextHelper();

        List SolicitudItems = new Vector();

   // para acciones de tabla
   DocumentosAsociadosTblDrawable_HelperWrapper tblDrawableHelper
    = new DocumentosAsociadosTblDrawable_HelperWrapper();

%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>

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

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + "elementValue" );
   }

</script>
<script type="text/javascript">
  var actionField = "<%=WebKeys.ACCION%>";
  var PAGE_REGION__DOCUMENTOSASOCIADOSADD_ACTION = "<%=AW_FotocopiasConstants.DOCUMENTOSASOCIADOSADD_ACTION%>";
  var PAGE_REGION__DOCUMENTOSASOCIADOSDEL_ACTION= "<%=AW_FotocopiasConstants.DOCUMENTOSASOCIADOSDEL_ACTION%>";
</script>

<script type="text/javascript">

   function js_OnRepeaterAction( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     // alert( findObj('FOTOCOPIA').ACCION.value );
     htmlForm.submitForm();

   }
   
   function ocultarButton(){ 
   	document.getElementById('Folio').width=0;
   	document.getElementById('Folio').height=0;
   	document.getElementById('Folio').disabled=true;
   	document.FOTOCOPIA.submit();
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
<style media="screen">
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
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno
                  Fotocopias</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_fotocopias.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <!--AHERRENO 13/06/2012
                    REQ 076_151 TRANSACCIONES-->
                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                        <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
                    </tr>
                </table></td>                  
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <form action="turnoLiquidacionFotocopia.do" method="post" name="FOTOCOPIA" id="FOTOCOPIA">
              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.fotocopia.AWLiquidacionFotocopia.LIQUIDAR%>" >
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Documento</td>
                  <td width="16" class="bgnsub">
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_fotocopias.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br />
                <!-- sof: block -->
                <%--

                  List modelRepeaterItems = new java.util.Vector();


                  // ------ populate 1 element
                  gov.sir.core.negocio.modelo.DocumentoFotocopia documentoFotocopia ;

                  TipoFotocopia tipoDocumentoFotocopia;

                  documentoFotocopia = new gov.sir.core.negocio.modelo.DocumentoFotocopia();
                  documentoFotocopia.setDescripcion("algo1");

                  tipoDocumentoFotocopia = new TipoFotocopia();
                  tipoDocumentoFotocopia.setIdTipoFotocopia( "1" );

                  documentoFotocopia.setTipoFotocopia( tipoDocumentoFotocopia );

                  modelRepeaterItems.add( documentoFotocopia );


                  documentoFotocopia = new gov.sir.core.negocio.modelo.DocumentoFotocopia();
                  documentoFotocopia.setDescripcion("algo2");

                  tipoDocumentoFotocopia = new TipoFotocopia();
                  tipoDocumentoFotocopia.setIdTipoFotocopia( "2" );

                  documentoFotocopia.setTipoFotocopia( tipoDocumentoFotocopia );

                  modelRepeaterItems.add( documentoFotocopia );


                  gov.sir.core.web.helpers.fotocopia.DocumentosFotocopiaRepeaterRegion_Helper repeater1
                   = new gov.sir.core.web.helpers.fotocopia.DocumentosFotocopiaRepeaterRegion_Helper();

                  repeater1.render( request, out );
--%>

<!-- temporal helper -->
<%
List l = new ArrayList();

if(documentosAsociados !=null) {
  int index = 0;
  Iterator it= documentosAsociados.iterator();
  for(;it.hasNext();index++) {
    DocumentoAsociado_Item d = (DocumentoAsociado_Item)it.next();
    String value;
    // value = d.getDescripcion() + d.getNumHojas() + d.getTipoDocumento();
    value = "" + index;
    l.add( value );
  }

}

// session.setAttribute("temporalList", l);
%>


                  <%-- try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName( "temporalList" );
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                      tablaHelper.render(request, out);
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  --%>
<%
// session.removeAttribute("temporalList");
%>



<!-- -->


                <!-- eof: block -->

<!--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
-->
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
<%--
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos Solicitud Fotocopias </td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_fotocopias.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
--%>
                <!-- sof: block -->


                <%--
				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15" /></td>
						<td>Eliminar Seleccionadas</td>
						<td><a href="javascript:onRepeaterDelete()"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0" /></a></td>
					</tr>
				</table>
                                --%>



				<table width="100%" class="camposform">
					<tr>
						
						<td>Documentos en esta solicitud</td>
					</tr>
					<tr>
						
						<td>
<%

List tipos = null;
try {
   tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO) ;
   if( tipos==null ){
      tipos = new Vector();
   }
}
catch( Exception e  ) {
  e.printStackTrace();
}

%>
                                                  <!--ACA -->

                  <% try {

                      tblDrawableHelper.setLov_TipoDocumentoContextAttName( WebKeys.LISTA_TIPOS_DOCUMENTO );
                      tblDrawableHelper.setDocumentosAsociadosListName( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );
                      tblDrawableHelper.render( request, out );
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %>


                                                  <table>
                                                    <tr>
                                                      <td>
<!--
      <table>
        <tr>
          <td width="5" align="center" class="campositem">
          </td>
        </tr>
      </table>
-->


                                                      </td>

                                                      <td>

      <!--
      <table>
        <tr>
          <td width="5" align="center" class="campositem">
          <input type="text" />
          </td>
        </tr>
      </table>

      -->

                                                      </td>

                                                    </tr>

                                                  </table>

                                                </td>
					</tr>
				</table>



                <!-- Buttons -->
                <table>
                  <tr>
                        <td>

                        <!-- Add Button -->
				<table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
						
						<td>Agregar Documento</td>
						<td align="right">
<a href="javascript:js_OnRepeaterAction( 'FOTOCOPIA', PAGE_REGION__DOCUMENTOSASOCIADOSADD_ACTION )">
  <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" name="imageField" width="35" height="25" border="0" id="imageField" />
</a>
                                                </td>
					</tr>
				</table>

                        </td>
                        <td>
                          <!-- Del Button -->
				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						
						<td>Eliminar Seleccionadas</td>
						<td><a href="javascript:js_OnRepeaterAction( 'FOTOCOPIA', PAGE_REGION__DOCUMENTOSASOCIADOSDEL_ACTION )"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0"
                                                        /></a></td>
					</tr>
				</table>
                        </td>
                  </tr>
                </table>




                <table width="100%" class="camposform">
                  <tr valign="top">
                    
                    </td>
                    <td>

                    <table class="camposform">
                      <tr>
                        <td width="120">Tipo de Documento </td>
                        <td><!-- TipoDocumento.Renderer -->
                      <%
                          try {
								 tiposDocumento.setOrdenar(false);
                                 tiposDocumento.setTipos(tipos);

                   		 tiposDocumento.setNombre(CDocumento.TIPO_DOCUMENTO);
                                 tiposDocumento.setId(CDocumento.TIPO_DOCUMENTO);

                   		 tiposDocumento.setCssClase("camposformtext");
                   		 tiposDocumento.render(request,out);
                          }
			  catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			  }
                          %>
                        </td>
                        <td width="50"># copias </td>
                        <td><!-- NumCopias.Renderer -->
                 	<% try {
                 				numCopias.setNombre(CSolicitudFotocopia.NUMCOPIAS);
                 				numCopias.setId(CSolicitudFotocopia.NUMCOPIAS);
                 				numCopias.setCssClase("camposformtext");
								numCopias.render(request,out);
                           }
			   catch(HelperException re){
			      out.println("ERROR " + re.getMessage());
                          }
			%>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="5">Descripci&oacute;n:<br />
<!-- DescripcionHelper.Render -->
                 	<% try {
                 				descripcion.setNombre(CTurno.DESCRIPCION);
                  			   	descripcion.setId(CTurno.DESCRIPCION);
                  			   	descripcion.setCssClase("camposformtext");
								descripcion.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
                        </td>
                        <td>


          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/btn_anadir.gif" width="7" height="6" border="0" alt=""></td>



                    </td>
                    </tr>
                    </table>
                </table>

                <!-- eof:block -->



              <br />
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"></td>
                  
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
             	<table width="100%" class="camposform">
                      <tr>
                 	<td width="20"></td>
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211">
                     	<% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		if(docs == null){
                      			docs = new Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		               	        docHelper.setNombre(CCiudadano.TIPODOC);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CCiudadano.TIPODOC);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212">
                 	<% try {
 		                        textHelper.setNombre(CCiudadano.DOCUMENTO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.DOCUMENTO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                      </tr>
                      <tr>
                 	<td>&nbsp;</td>
                 	<td>Primer Apellido</td>
                 	<td>
                 	<% try {
 		           	            textHelper.setNombre(CCiudadano.APELLIDO1);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO1);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	<td>Segundo Apellido</td>
                 	<td>
                 	<% try {
 		           	            textHelper.setNombre(CCiudadano.APELLIDO2);
                  			    //textHelper.setCssClase("campositem");
                                            textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO2);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                      </tr>
                      <tr>
                 	<td>&nbsp;</td>
                 	<td>Nombre</td>
                 	<td>
                 	<% try {
 		           	            textHelper.setNombre(CCiudadano.NOMBRE);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.NOMBRE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	<td>Tel&eacute;fono</td>
                 	<td>
                 	<%
                           try {
                              textHelper.setNombre(CCiudadano.TELEFONO);
                              // textHelper.setCssClase("campositem");
                              textHelper.setCssClase("camposformtext");
                              textHelper.setId(CCiudadano.TELEFONO);
                              textHelper.render(request,out);
                           }
			   catch(HelperException re){
                              out.println("ERROR " + re.getMessage());
			   }
			%>
                        </td>
                      </tr>
             	</table>
              <hr class="linehorizontal">
				<table width="100%" class="camposform">
	              <tr>
		              
						<td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Folio" onclick="ocultarButton();"></td>
			            </form>
		              <td width="150" align="center"><input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="150" onClick="<%=vistaAnterior%>" height="21" border="0" ></td>
	        	      <td>&nbsp;</td>
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

    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
