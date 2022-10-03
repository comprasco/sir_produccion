<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWListaReportes" %>
<%@page import="gov.sir.core.negocio.modelo.TipoPantalla" %>
<%@page import="gov.sir.core.negocio.modelo.PantallaAdministrativa" %>
<%@page import="java.util.List" %>
<%@page import="java.util.*"%>

<%
	String ruta = "inicio.view";
	Boolean regreso = (Boolean)session.getAttribute(gov.sir.core.web.acciones.comun.AWSeguridad.INICIAR_COMO_ADMINISTRADOR);

	if(regreso != null && regreso.booleanValue()){
		ruta = "roles.view";
	}
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" ><!-- --></link>


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

   var PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION       = "<%= AWListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION %>";


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


<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
      
    
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Administraci&oacute;n</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_llave.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  
                </table>
                <table width="98%" class="camposform">

             <%  List listaTiposPantalla = (List) session.getAttribute( WebKeys.LISTADO_TIPOS_PANTALLAS);
                 List listaPantallasVisibles = (List) session.getAttribute( WebKeys.LISTADO_PANTALLAS_VISIBLES);
                 
                 /**
                 * @Autor: Edgar Lora
                 * @Mantis: 003_589
                 */
                if((listaTiposPantalla == null) && (listaPantallasVisibles == null)){                    
                    listaTiposPantalla = new ArrayList();
                    listaPantallasVisibles = new ArrayList();
                }

             List listadoFinalPantallas = eliminarPantallasDuplicadas (listaPantallasVisibles);

             for (int i=0; i<listaTiposPantalla.size(); i ++)
             {
             %>
             <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
             <td><B>


              <%
              TipoPantalla tipoPant = (TipoPantalla) listaTiposPantalla.get(i);
              %>
                  <%=tipoPant.getNombre()%></td></tr></B>


                      <%for (int k= 0; k<listadoFinalPantallas.size(); k++)
                      {
                           PantallaAdministrativa pantalla = (PantallaAdministrativa) listadoFinalPantallas.get(k);
                           if (pantalla.getTipoPantalla().getIdTipoPantalla() == tipoPant.getIdTipoPantalla()){
                               /**
                                * @author David Panesso
                                * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA SITEMINDER
                                * Se desactiva opción de cambio de calve en pantallas administrativas 
                                */
                               if(pantalla.getIdPantallaAdministrativa() != 22)
                               {
                                    %>
                                    <tr>
                                    <td>&nbsp</td>
                                    <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">
                                    <a href="<%= pantalla.getVista()%>" class="links">
                                    <%=pantalla.getNombre()%></a> </td></tr>

                                    <%
                               }
                           }
                      }%>
                     <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
              <%
              }
              %>

              <tr>

                <form
                  id="report-list"
                  name="report-list"
                  action="report-list.do"
                >
                  <input
                    type="hidden"
                    id="<%= WebKeys.ACCION %>"
                    name="<%= WebKeys.ACCION %>"
                    value="default"
                  />

                </form>





              </tr>


<%--

       <tr>

         <td width="20">
          <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" />
         </td>
         <td><b>REPORTES</b>
         </td>
       </tr>

       <tr>
         <td>&nbsp;</td>
           <td onMouseOver="this.style.backgroundColor='#F7F9FD'" onMouseOut="this.style.backgroundColor=''">
             <a class="links" href="#" onClick="javascript:js_OnEvent( 'report-list', PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION );" >Listado Reportes</a>
           </td>
       </tr>
--%>

       <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
       </tr>





                </table>



             <hr class="linehorizontal">
             <table width="100%" class="camposform">

	        <tr>
                <!--
                 * @Autor: Edgar Lora
                 * @Mantis: 003_589
                 -->
                <% if(listaTiposPantalla.isEmpty() && listaPantallasVisibles.isEmpty() && session.getAttribute(WebKeys.IDENTIFICADOR_DE_SESSION) != null){ 
                    session.removeAttribute(SMARTKeys.EXCEPCION);
                    %>
                    <td><a href="javascript:window.location.href='roles.view';"><img src='<%=request.getContextPath()%>/jsp/images/btn_regresar.gif'  width="150" height="21" border="0"> </a></td>
                <% }else{ %>
                    <td><a href='<%=ruta%>'><img src='<%=request.getContextPath()%>/jsp/images/btn_regresar.gif'  width="150" height="21" border="0"> </a></td>
                <% } %>
	        <td>&nbsp;</td>
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
	</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
  </tr>

</table>

<%!

  public List eliminarPantallasDuplicadas (List listaPantallas)
  {
     List listaDefinitiva = new ArrayList();

     for (int i=0; i<listaPantallas.size(); i++)
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
