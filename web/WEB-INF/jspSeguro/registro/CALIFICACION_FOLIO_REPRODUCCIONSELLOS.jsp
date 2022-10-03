<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos"%>


<%
Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();


	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}

	//HELPER PARA MOSTRAR
	TextHelper textHelper = new TextHelper();

	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));
	session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE);
	session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION, "1");
%>


<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.submit();
}

function calificar(text) {
   document.CALIFICACION.ITEM.value = text;
   cambiarAccion('CALIFICAR_FOLIO');
}

function reenviarCalificacion(text) {
	if(confirm('¿Esta seguro que desea reenviar el turno para que sea revisado en otras dependencias?')){
	   document.CALIFICACION.ACCION.value = text;
	   document.CALIFICACION.action = 'calificacion.do';
	   document.CALIFICACION.submit();
	}
}

function finalizarCalificacion(text){
	if(confirm('¿Esta seguro que desea terminar la calificación?')){
		cambiarAccion(text);
	}
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

   var printField = "<%= gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_PRINT %>";

   var CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION    = "<%=AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION%>";
   var CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION     = "<%=AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION%>";
   var CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION = "<%=AWCalificacion.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION%>";

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reproducci&oacute;n constancia de Inscripci&oacute;n </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">



    <form action="calificacion.do" method="post" name="CALIFICACION" id="CALIFICACION" >
      <input type="hidden" name="<%= WebKeys.ACCION %>" value='<%=""%>' />




    <!--INICIO MATRICULAS ASOCIADAS, OPCIONES DE CONSULTA-->

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reproducción por turno</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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

              <table width="100%">
                <tr>
                  <td>

                  </td>
                </tr>
              </table>


              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" width="40%">N&uacute;mero de turno</td>
                  <td class="contenido">&nbsp;</td>
                  <td class="contenido" width="60%">
                    <input
                      id="<%= CAccionCalificacionReproduccionSellos.PARAM_ITEM_SEARCH %>"
                      name="<%= CAccionCalificacionReproduccionSellos.PARAM_ITEM_SEARCH %>"
                      type="text" class="camposformtext"
                    />
                  </td>

                </tr>
              </table>

			  <br />


              <table width="100%" class="camposform">
                <tr>

                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                    <a name="imageField" href="javascript:js_OnEvent( 'CALIFICACION', CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION );">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"/>
                    </a>
                  </td>
                  <td width="140">
                    <a name="imageField" href="javascript:js_OnEvent( 'CALIFICACION', CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION );">

                      <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"/>
                    </a>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>


          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>

    <!--FIN DE INICIO MATRICULAS ASOCIADAS, OPCIONES DE CONSULTA-->


    <!--OPCIONES DE REENVIO-->

    <%

    // enum;
    final int FOUNDED     = 1;
    final int NOT_FOUNDED = 0;

    final String PARAM_QUERYRESULTS = gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAMID_SEARCHRESULTS;

    // state;
    int queryResultState = NOT_FOUNDED;


    // result list; List< Turno >;
    java.util.List results = null;


    results = (java.util.List)( session.getAttribute( PARAM_QUERYRESULTS ) );

    if( ( null != results )
      &&( results.size() > 0 ) ) {

      queryResultState = FOUNDED;

    }

    %>


    <%
    if( FOUNDED == queryResultState ) {
    %>

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Resultados</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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

            <input type="hidden"
              name="<%= gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_PRINT %>"
              id="<%= gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_PRINT %>"
              value="" />


            <%
            if( FOUNDED == queryResultState ) {
            %>

            <!-- when-founded ============================================= -->

            <table width="100%" class="camposform">
              <!-- t-head -->

              <tr>
                <td width="20" height="24">&nbsp;</td>
                <td>N&uacute;mero de turno</td>
                <td width="240">&nbsp;</td>
				<td width="150">&nbsp;</td>
              </tr>

              <!-- t-body -->

              <%

              java.util.Iterator iterator;
              iterator = results.iterator();


              for(;iterator.hasNext();) {

                gov.sir.core.negocio.modelo.Turno element  = (gov.sir.core.negocio.modelo.Turno)iterator.next();


              %>

              <tr>
                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">&nbsp;</td>
                <td class="campositem"><%= element.getIdWorkflow() %></td>
				<td width="240">&nbsp;</td>
                <td width="150" align="right"><a href="javascript:js_OnEventPrint( 'CALIFICACION', CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION, '<%=element.getIdWorkflow()%>' );"><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0" alt="" /></a></td>  
                <td width="150" align="right"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RST=<%=element.getIdWorkflow()%>&&ID=<%=String.valueOf(solicitud.getUsuario().getIdUsuario())%>','name','width=800,height=600')" ><img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" width="139" height="21" border="0"></a></td>     
              </tr>

              <%

              }

              %>

			  



            </table>


            <%
            }
    
            %>







          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
		<tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		  <td valign="top" bgcolor="#79849B" class="tdtablacentral">
			<table width="100%" class="camposform">
				<td>&nbsp;</td>
                <td>N&uacute;mero de impresiones</td>
				<td>	
				<%try {			    							
 		                textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
 		                textHelper.setCssClase("camposformtext");
                  	    textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>
				</td>
				<td>&nbsp;</td>
			</table>
		  <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
         </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      <%
      } else {
      %>

      <!--OPCIONES DE REENVIO-->
   <!--OPCIONES DE REIMPRESIÓN POR MATRÍCULA-->
	<%
	textHelper.setEditable(true);	
	textHelper.setTipo("text");	
	textHelper.setCssClase("camposformtext");	
	%>



    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reproducción por matrícula</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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

              <table width="100%">
                <tr>
                  <td>

                  </td>
                </tr>
              </table>


              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" width="20%" align="right">N&uacute;mero de matrícula</td>
                  <td class="contenido" width="20%">
                  <%
					try {
						textHelper.setNombre(CFolio.ID_MATRICULA);
						textHelper.setId(CFolio.ID_MATRICULA);
						textHelper.render(request,out);
					}catch(HelperException re){
						 out.println("ERROR " + re.getMessage());
					}	
					%>
                  </td>
                  <td class="contenido" align="right" width="15%">Desde: </td>                  
                 <td class="contenido" align="left"  width="15%">
                  <%
					try {
						textHelper.setNombre(WebKeys.DESDE);
						textHelper.setId(WebKeys.DESDE);
						textHelper.setSize("10");												
						textHelper.render(request,out);
					}catch(HelperException re){
						 out.println("ERROR " + re.getMessage());
					}	
					%>                  
                  </td>
                  
                  <td class="contenido" align="right" width="15%">Hasta: </td>                  
                  <td class="contenido" align="left" width="15%">
                  <%
					try {
						textHelper.setNombre(WebKeys.HASTA);
						textHelper.setId(WebKeys.HASTA);
						textHelper.render(request,out);
					}catch(HelperException re){
						 out.println("ERROR " + re.getMessage());
					}	
					%>                  
                  </td>
                </tr> 
				<tr>
					<td>&nbsp;</td>
					<td class="contenido" width="20%" align="right">N&uacute;mero de impresiones</td>
					<td>	
					<%try {			    							
 		                textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
 		                textHelper.setCssClase("camposformtext");
                  	    textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
						textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>               
              </table>

			  <br />


              <table width="100%" class="camposform">
                <tr>

                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                    <a name="imageField" href="javascript:js_OnEvent( 'CALIFICACION', 'CALIFICACION_REIMPRESIONSELLOS_MATRICULA');">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0"/>
                    </a>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>


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
            }
					%>
    <!--FIN DE REIMPRESIÓN POR MATRÍCULA-->
<!-- INICIO TERMINAR CALIFICACION DESDE REPRODUCCION DE SELLOS -->

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Terminar calificación</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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

              <table width="100%">
                <tr>
                  <td>

                  </td>
                </tr>
              </table>


              

			  <br />


              <table width="100%" class="camposform">
                <tr>

                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                 		<br>
                  		
                    	<a href="javascript:finalizarCalificacion('<%=gov.sir.core.web.acciones.registro.AWCalificacion.CONFIRMAR%>')">                    
                      		<img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" name="Siguiente" border="0" id="Siguiente"/>
                    	</a>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>


          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>


        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>

<!--FIN TERMINAR CALIFICACION DESDE REPRODUCCION DE SELLOS -->







    </form>




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
