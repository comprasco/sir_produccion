<%@page import="java.util.*"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoOficina" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.COficinaOrigen" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Categoria" %>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad" %>
<%@page import="gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen" %>

<%
    Date today;
    String fechaAct;

    today = new Date();
    fechaAct = DateFormatUtil.format(today);


    // Bug YYYYY --------------------------------------

    Boolean isPageLoad;

    if( null == ( isPageLoad = (Boolean)session.getAttribute( "PAGE_LOAD" ) ) ) {
      // carga inicial de pagina
      isPageLoad = Boolean.TRUE;
      session.setAttribute( "PAGE_LOAD", isPageLoad );

    }
    else {
      isPageLoad = Boolean.FALSE;

    }
    // if

    boolean filterByUsuario;
    filterByUsuario = true;

    List elementos;

    if( filterByUsuario ) {
      elementos = (List)session.getAttribute( AWReportes.REPORTE_XX__ITEM_CIRCULOSBYUSUARIO );
    }
    else {
      elementos = new ArrayList();
      List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
      for( Iterator iter = circulos.iterator(); iter.hasNext(); ) {
	Circulo circulo = (Circulo) iter.next();
	elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));
      } // for

    } // if



    if( null == elementos ) {
      elementos = new ArrayList();
    }
   // -----------------------------------------------

	ListaElementoHelper circuloHelper = new ListaElementoHelper();
	circuloHelper.setTipos(elementos);

	session.removeAttribute(AWReportes.LISTA_DEPARTAMENTOS);
	if(session.getAttribute(AWReportes.LISTA_DEPARTAMENTOS)==null){
		Map map = (Map) application.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);

		List listaDeptos = new ArrayList();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String key = (String)iter.next();
			Departamento depto= (Departamento) map.get(key);
			listaDeptos.add(new ElementoLista(key, depto.getNombre()));
		}
		session.setAttribute(AWReportes.LISTA_DEPARTAMENTOS, listaDeptos);
	}

	if(session.getAttribute(CTipoOficina.ID_TIPO_OFICINA) ==null){
		session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, CTipoOficina.TIPO_OFICINA_NOTARIA);
	}

	List listaDeptos = (List)session.getAttribute(AWReportes.LISTA_DEPARTAMENTOS);
	ListaElementoHelper deptoHelper = new ListaElementoHelper();
	deptoHelper.setTipos(listaDeptos);

	ListaElementoHelper municipioHelper = new ListaElementoHelper();
	if(session.getAttribute(AWReportes.LISTA_MUNICIPIOS)==null ){
		municipioHelper.setTipos(new ArrayList());
	}
	else{
		municipioHelper.setTipos((List)session.getAttribute(AWReportes.LISTA_MUNICIPIOS));
	}

	ListaElementoHelper veredaHelper = new ListaElementoHelper();
	if(session.getAttribute(AWReportes.LISTA_VEREDAS)==null ){
		veredaHelper.setTipos(new ArrayList());
	}
	else{
		veredaHelper.setTipos((List)session.getAttribute(AWReportes.LISTA_VEREDAS));
	}
        //------------------------------------------
        //Grupos Naturalezas Juridicas
            List listaGrupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
            GrupoNaturalezaJuridica gruNatJuridicas = null;
            NaturalezaJuridica natJuridicas = null;
            List naturalezaJur = new ArrayList();
            List listaNatJur = new ArrayList();
            for(Iterator iter = listaGrupoNaturalezas.iterator(); iter.hasNext();){
                gruNatJuridicas = (GrupoNaturalezaJuridica) iter.next();
                naturalezaJur = gruNatJuridicas.getNaturalezaJuridicas();
            //Naturalezas Juridicas
                for(Iterator iterNat = naturalezaJur.iterator(); iterNat.hasNext(); ){
                    natJuridicas = (NaturalezaJuridica) iterNat.next();
                    listaNatJur.add(new ElementoLista(natJuridicas.getIdNaturalezaJuridica(), gruNatJuridicas.getNombre()+"-"+ natJuridicas.getIdNaturalezaJuridica()+"-"+ natJuridicas.getNombre()));
                }
            }
                       
            ListaElementoHelper natHelper = new ListaElementoHelper();
            natHelper.setTipoMultiple(true);
            natHelper.setShowInstruccion(false);
            natHelper.setTipos(listaNatJur);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=ISO-8859-1" />
<meta name="Author" content="ETB - INTEK" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionAndSend(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
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
	// local form dependant resources
    var actionField = "<%=WebKeys.ACCION%>";

    var REPORTE_161__SEND_ACTION       = "<%= AWReportes.REPORTE_161__SEND_ACTION %>";
    var REPORTE_161__BACK_ACTION       = "<%= AWReportes.REPORTE_161__BACK_ACTION %>"; // the same
    var REPORTE_161_LOADCIRCULOSBYUSUARIO = "<%= AWReportes.REPORTE_161_LOADCIRCULOSBYUSUARIO %>"; // the same

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


<form
  id="INITIAL_TIME_LOAD"
  name="INITIAL_TIME_LOAD"
  action="reportes.do"
>
  <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="" />
</form>


<%-- + + + + + + + --%>
<%--

 // Truco para hacer una peticion la primera vez que se carga la pagina

--%>
<%-- CONDITION--%>
<%

  if( isPageLoad.booleanValue() ) {

%>
<%-- CONDITION--%>
<script type="text/javascript">

  js_OnEvent( "INITIAL_TIME_LOAD", REPORTE_161_LOADCIRCULOSBYUSUARIO );

</script>

<%-- CONDITION--%>
<%
  } // if
%>
<%-- CONDITION--%>
<%-- + + + + + + + --%>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"></td>
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
        <table border="0" cellpadding="0" cellspacing="0" width="101%">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reporte 161</td>
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
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Actos de Registro en un Rango de Fecha</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>




        <form action="reportes.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="">

		<table width="100%" class="camposform">

           	<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Fecha Inicial Anotaci&oacute;n:</div></td>
                    <td width="200">
                    <%

                    try {

                      if( null == session.getAttribute( AWReportes.REPORTE_161__PARAM_PFECHAINI ) ) {
                        session.setAttribute( AWReportes.REPORTE_161__PARAM_PFECHAINI, fechaAct );
                      }

                    	TextHelper textHelper = new TextHelper();
                 	textHelper.setId( AWReportes.REPORTE_161__PARAM_PFECHAINI );
                        textHelper.setNombre( AWReportes.REPORTE_161__PARAM_PFECHAINI );
                  	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			  		%>

					<a href="javascript:NewCal('<%=AWReportes.REPORTE_161__PARAM_PFECHAINI%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>

				  </td>
                  <td>&nbsp;</td>

                </tr>

           	<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Fecha Final Anotaci&oacute;n:</div></td>
                    <td width="200">
                    <%

                    try {

                      if( null == session.getAttribute( AWReportes.REPORTE_161__PARAM_PFECHAFIN ) ) {
                        session.setAttribute( AWReportes.REPORTE_161__PARAM_PFECHAFIN, fechaAct );
                      }

                    	TextHelper textHelper = new TextHelper();
	                textHelper.setId( AWReportes.REPORTE_161__PARAM_PFECHAFIN );
	                textHelper.setNombre( AWReportes.REPORTE_161__PARAM_PFECHAFIN );
	                textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			  		%>

					<a href="javascript:NewCal('<%=AWReportes.REPORTE_161__PARAM_PFECHAFIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>

				  </td>
                  <td>&nbsp;</td>

                </tr>


           	<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >C&iacute;rculo Registral:</div></td>
                    <td width="200">
                    <%

                    try {
	                    circuloHelper.setId( AWReportes.REPORTE_161__PARAM_PCIRCULONOMBRE );
	                    circuloHelper.setNombre( AWReportes.REPORTE_161__PARAM_PCIRCULONOMBRE );
	                    circuloHelper.setCssClase("camposformtext");
                            circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWReportes.REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_ACTION+"')\"");
			    circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			 		%>
                    </td>
                </tr>

                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Municipio:</div></td>
                    <td>
                 <%
                   try {
                        municipioHelper.setNombre(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setCssClase("camposformtext");
                  	municipioHelper.setId(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWReportes.REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION+"')\"");
			municipioHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>


                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Vereda:</div></td>
                    <td>
                    <%
                try {
                        veredaHelper.setNombre(CVereda.ID_VEREDA);
                  	veredaHelper.setCssClase("camposformtext");
                  	veredaHelper.setId(CVereda.ID_VEREDA);
                  	veredaHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWReportes.REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION+"')\"");
			veredaHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right">Naturaleza Jur&iacute;dica:</div></td>
                    <td class="campostip04">Oprima CTRL para hacer una selecci&oacute;n multiple.</td>
                  </tr>
                  <tr>
                      <td></td>
                      <td></td>
                    <td>
                         <%
                           try {
                                    natHelper.setNombre(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
                                    natHelper.setCssClase("camposformtext");
                                    natHelper.setId(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
                                    natHelper.render(request,out);
                                }
                                    catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }
                           %>
                    </td>
                </tr>
                <%
                        Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                %>
                	<input  type="hidden" name="<%=AWReportes.REPORTE_161__PARAM_USUARIOLOG%>"
                	id="<%=AWReportes.REPORTE_161__PARAM_USUARIOLOG%>"
                	value="<%=usuario.getUsername()%>">
	</table>


                <table width="100%" class="camposform">
                  <tr>
                    <td width="155">


	                    <a href="javascript:js_OnEvent( 'BUSCAR', REPORTE_161__BACK_ACTION );">
	                     <img alt="regresar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
	                    </a>
                    </td>
                    <td>
	                    <a href="javascript:js_OnEvent( 'BUSCAR', REPORTE_161__SEND_ACTION );">
	                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_observar.gif" width="139" height="21" border="0" />
	                    </a>
                    </td>
                  </tr>
                </table>

            </FORM>


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
