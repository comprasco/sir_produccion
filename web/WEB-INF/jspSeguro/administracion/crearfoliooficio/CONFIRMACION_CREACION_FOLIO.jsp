<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWCrearFolio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstadoFolio"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWCrearFolioOficio"%>

<%

	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
	session.removeAttribute(WebKeys.FOLIO);


	TextAreaHelper textAreaHelper = new TextAreaHelper();
	//limpieza de valores del session
	request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

	boolean esFolioMayorExtension=false;
	String vistaActual;

	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	mFolio.setMostrarHistorialEstados(true);
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	Boolean temp=(Boolean)session.getAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);

	if(temp!=null){
		esFolioMayorExtension = temp.booleanValue();
	}

	//inicializacion Vista generadora
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;

	java.math.BigDecimal totalAnotaciones = (java.math.BigDecimal) request.getSession().getAttribute(WebKeys.TOTAL_ANOTACIONES);
	List gravamenes = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);
	List medCautelares = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES);

	//inicializacion nombre de paginadores y resultados
	String nombrePaginador="NOMBRE_PAGINADOR_DETALLES";
    String nombreResultado="NOMBRE_RESULTADO_DETALLES";


	//si va a ver datosRelevantes folio
	Turno turnoTramite=null;
	turnoTramite= (Turno)session.getAttribute(WebKeys.TURNO_TRAMITE);

	Usuario usuarioBloqueo= null;
	usuarioBloqueo= (Usuario) session.getAttribute(WebKeys.USUARIO_BLOQUEO);

	Turno turnoDeuda=null;
	turnoDeuda= (Turno)session.getAttribute(WebKeys.TURNO_DEUDA);

	gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
    boolean activarAceptar = true;
    if((usuarioBloqueo !=null)&&(usuarioSIR!=null)){
    	if(usuarioBloqueo.getIdUsuario()!=usuarioSIR.getIdUsuario()){
    		activarAceptar = false;
    	}
    }

%>
<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js">-->
</script>
<script>
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
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

    // added -------------
   LocalForm.prototype.getFormObject = function() {
     var local_FormObject;
     local_FormObject = findObj( this.formName );
     return local_FormObject;
   }

   LocalForm.prototype.getValue = function( elementName ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     var elementValue;

     eval( "elementValue" + "=" + "formObject."+ elementName + ".value" );
     // alert( "valor de " + elementName + " es " + elementValue )
     return elementValue;
   }

   LocalForm.prototype.formElementFocus = function( elementName ) {
     var local_FormObject;
     local_FormObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".focus()" );

   }

   // -------------------

</script>



<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

   var ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION  = "<%= AWCrearFolio.ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION %>";

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reabrir Folio</td>
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


    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>

	<%if(true){
  	%>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Confirmaci&oacute;n</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
	    <form action="crearfolioadministracionoficio.do" method="POST" name="BUSCAR" id="BUSCAR">
	    <table width="100%" class="camposform">

	       <tr>
                <td width="20">&nbsp;</td>
                <td width="30">&nbsp;</td>
                <td >&nbsp;</td>
                <%if(session.getAttribute(AWCrearFolioOficio.GUARDAR_FOLIO)!=null) {
                		session.removeAttribute(AWCrearFolioOficio.GUARDAR_FOLIO);%>
                		<td class="titulotbcentral">EL FOLIO <%=folio!=null?folio.getIdMatricula():"NO"%> HA SIDO GUARDADO TEMPORALMENTE </td>
                <%}else{ %>
                	<td class="titulotbcentral">EL FOLIO <%=folio!=null?folio.getIdMatricula():"NO"%> HA SIDO CREADO </td>
                <%} %>
                
		         <td>
		         <image src="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif" />
		         <!--
		         <script language="javascript" type="text/javascript">
				  var Imagen="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif"
				  var pelicula="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.swf"
				  var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
				  var ancho="70"
				  var alto="70"
				  plugindetectado();
				</script>
				-->
				</td>

           </tr>

	      <tr>
	        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	           <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  gov.sir.core.web.acciones.administracion.AWAdministracionForseti.REABRIR_FOLIO %>">
	        <td>



                  <%-- SOF:BUTTON --%>

                  <td width="50">
                    <a href="javascript:js_OnEvent( 'BUSCAR', ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  <%-- EOF:BUTTON --%>

<%--

            <a href="admin.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" alt="Editar" width="139" height="21" border="0" ></a>
--%>
<td colspan="2">&nbsp;</td>
	        </FORM>
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

    <%}
   	 %>

   </table>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>



    <tr>





    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">



    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>



   </table>


    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>


    </tr>



  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
