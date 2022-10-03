<%@page import="gov.sir.core.util.ListasContextoUtil"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" ></link>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
  <%--
  - modificado: agregar helper de varios ciudadanos;
  - modificado: cambiar layout de pagina para que coincida con el de
                las regiones de califacacion.

  --%>

<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- Creacion anotacion-normal modulo:correcciones -->
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->

<%
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
        
        ListaElementoHelper tipoModalidadHelper = new ListaElementoHelper();
        List modalidad = ListasContextoUtil.getModalidades(); 
                if(modalidad == null){
                    modalidad = new ArrayList();
                }
        tipoModalidadHelper.setTipos(modalidad);
        tipoModalidadHelper.setCssClase("camposformtext");
    
        
	TextAreaHelper textAreaHelper = new TextAreaHelper();
    TextHelper textHelper = new TextHelper();
	TextHelper hiddenHelper = new TextHelper();
	hiddenHelper.setTipo("hidden");
    ListaElementoHelper tiposDocHelper =  new ListaElementoHelper();

    // helper para ciudadanos
    /*


    gov.sir.core.web.helpers.registro.CiudadanosAnotacionHelper ciudadanosHelper
    = new gov.sir.core.web.helpers.registro.CiudadanosAnotacionHelper();

    ciudadanosHelper.setLink(true);

    */

    // helper para ciudadanos v2

    VariosCiudadanosAnotacionHelper variosCiudadanosHelper
    = new VariosCiudadanosAnotacionHelper();
    List lciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
    Integer auxNumFilas = (Integer)session.getAttribute(AWModificarFolio.NUM_REGISTROS_TABLA_CIUDADANOS);
    if( null == lciudadanos ){
       lciudadanos= new Vector();
    }

    int numFilas;
    if( auxNumFilas == null ) {
      numFilas=AWModificarFolio.DEFAULT_NUM_CIUDADANOS_TABLA;
    }
    else{
      numFilas=auxNumFilas.intValue();
    }

    List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
    List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
    List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
    List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
    List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
    variosCiudadanosHelper.setPropertiesHandly(
        numFilas
      , tiposIDNatural
      , tiposPersona
      , tiposSexo
      , tiposIDJuridica
      , AWModificarFolio.CRTANOTACIONNORMAL_CIUDADANO_ADDREPEATER_1FIELD
      , AWModificarFolio.CRTANOTACIONNORMAL_CIUDADANO_DELREPEATER_1FIELD
      , AWModificarFolio.CRTANOTACIONNORMAL_CIUDADANO_ADDITEM
      , AWModificarFolio.CRTANOTACIONNORMAL_CIUDADANO_DELITEM
      , lciudadanos
      , AWModificarFolio.CRTANOTACIONNORMAL_CIUDADANO_VALIDATEITEM
      , "CORRECCION"
    );
    variosCiudadanosHelper.setAccionUltimosPropietarios(AWModificarFolio.GET_ULTIMOS_PROPIETARIOS);
	variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
	variosCiudadanosHelper.setAnotacionNueva(true);
	variosCiudadanosHelper.setAccionEditar(AWModificarFolio.EDITAR_ANOTACION_CIUDADANO);
	if(turno!=null){
		variosCiudadanosHelper.setProceso(Long.toString(turno.getIdProceso()));
	}
	request.getSession().setAttribute("paramVistaAnterior","AGREGAR_ANOTACION_CORRECIONES");



    // Cambiar a Id-Anotacion
    String orden = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_ORDEN);
    String idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
    String posicionAnotacion = (String)request.getSession().getAttribute(AWModificarFolio.POSICIONANOTACION);
    String agregarAnotacion = request.getParameter(AWModificarFolio.AGREGAR_ANOTACION);
    if( null == agregarAnotacion ){
       agregarAnotacion = (String)session.getAttribute(AWModificarFolio.AGREGAR_ANOTACION);
    }

%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function Secuencia(text){
	document.CORRECCION.<%=CAnotacionCiudadano.SECUENCIA %>.value = text;  
	cambiarAccion('<%=AWModificarFolio.REFRESCAR_ANOTACION%>');
}
function cambiarAccion(text) {
	document.CORRECCION.ACCION.value = text;
	document.CORRECCION.submit();
}
function cargarFolio(text,folio) {
    document.CORRECCION.action = 'modificacion.do';
    document.CORRECCION.ACCION.value = text;
    document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = folio;
    document.CORRECCION.submit();
}
function quitar(pos,accion) {
	document.CORRECCION.<%=WebKeys.POSICION%>.value = pos;
	cambiarAccion(accion);
}
function guardarCambio(pos,accion){
	document.CORRECCION.<%=WebKeys.POSICION%>.value = pos;
	cambiarAccion(accion);
}
function editar(pos,accion) {
	document.CORRECCION.<%=WebKeys.POSICION%>.value = pos;
	cambiarAccion(accion);
}
function cargarAnotacion(pos,accion) {
	document.CORRECCION.<%=WebKeys.POSICION%>.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function cambiarAccionAndSendTipoTarifa(text) {
	if(document.getElementById('<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID %>') != null){
		document.CORRECCION.ACCION.value = text;
		document.CORRECCION.submit();
	}
}
function oficinas(nombre,valor,dimensiones)
{
	document.CORRECCION.ACCION.value='<%=AWModificarFolio.PRESERVAR_INFO%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.getElementById('version').value=valor+"_OFICINA_VERSION";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
           /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se asigna valores a campos en el formulario
                */
        document.getElementById('natjuridica_ver').value = valor+"_VER";
	var fechaRad = null;
        if(document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>') != null){
            fechaRad = document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>').value;
        }else{
            fechaRad = document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>_hidden').value;
        }
	var url = nombre + '&<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>='+ fechaRad;
        url = url + '&<%=AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ %>=' + document.getElementById('<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>').value;
	popup=window.open(url,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CORRECCION.<%=WebKeys.POSICION%>.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function locacion(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
}
</script>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Agregar Anotaci&oacute;n</td>
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
      <form action="modificacion.do" name="CORRECCION" id="CORRECCION" method="post">
		<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">
	    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">
		<input type="hidden" name="CAMBIO" value="">
   	    <input type="hidden" name="<%=AWModificarFolio.AGREGAR_ANOTACION%>" value="<%=agregarAnotacion!=null?agregarAnotacion:""%>">
	    <input type="hidden" name="<%=WebKeys.POSICION%>" value="<%=posicionAnotacion!=null?posicionAnotacion:""%>">
	    <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=request.getParameter("POSSCROLL")%>">
            <input type="hidden" id="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" name="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" value="<%=session.getAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION) %>" />
            <input type="hidden" id="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" name="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" value="<%=session.getAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA) %>"/>

<!--
           <input type="hidden" name="POSICION" value="" />
-->
           <input type="hidden" name="VER_SALVEDAD" value="" />
           <input type="hidden" name="SECUENCIA" value="-1" />


	    <!--input name="Depto" type="hidden" id="id_depto" value="">
	    <input name="Depto" type="hidden" id="nom_Depto" value="">
	    <input name="Mpio" type="hidden" id="id_munic" value="">
	    <input name="Mpio" type="hidden" id="nom_munic" value="">
	    <input name="Ver" type="hidden" id="id_vereda" value="">
	    <input name="Ver" type="hidden" id="nom_vereda" value=""-->

		<input name="id_depto" type="hidden" id="id_depto" value="">
		<input name="nom_depto" type="hidden" id="nom_depto" value="">
		<input name="id_munic" type="hidden" id="id_munic" value="">
		<input name="nom_munic" type="hidden" id="nom_munic" value="">
		<input name="id_vereda" type="hidden" id="id_vereda" value="">
		<input name="nom_vereda" type="hidden" id="nom_vereda" value="">

      <!--input name="Depto" type="hidden" id="Depto" value="">
      <input name="Mpio" type="hidden" id="Mpio" value="">
      <input name="Ver" type="hidden" id="Ver" value="">
      <input name="natjuridica" type="hidden" id="natjuridica" value="">
      <input name="tipo_oficina" type="hidden" id="tipo_oficina" value="">
      <input name="numero_oficina" type="hidden" id="numero_oficina" value=""-->

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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ANOTACIONES</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
            </tr>
        </table></td>
        <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
      </tr>
      <tr>
        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" >
          <tr>
            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaci&oacute;n: N&ordm; <%=orden!=null?orden:""%> </td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>
                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Fecha</td>
                      <td>
                        <table border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td>
                      <%
						try {
							textHelper.setNombre(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%>
							<td><a href="javascript:NewCal('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>
                          </tr>
                      </table>

                      </td>
                      <td>


                        <table border="0" cellpadding="0" cellspacing="0" >
                          <tr>
                            <td class="camposform" style="border:0px;" width="120">Num. Radicacion</td>

                            <td>
                              <%
				try {
				  textHelper.setNombre(AWModificarFolio.ANOTACION_NUM_RADICACION);
				  textHelper.setCssClase("camposformtext");
				  textHelper.setId(AWModificarFolio.ANOTACION_NUM_RADICACION);
				  textHelper.render(request,out);
				}
				catch( HelperException re ){
                                  out.println("ERROR " + re.getMessage());
				}
                              %>
                            <td>
                          </tr>
                      </table>



                      </td>



                    </tr>
                  </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                      <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                      <td class="bgnsub">Documento</td>
                      <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                      <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_editar.gif" width="16" height="21"></td>
                      <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                    </tr>
                  </table>
                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Datos B&aacute;sicos </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td><table width="100%" class="camposform">
                          <tr>
                            <td>Tipo</td>
                            <td>
                            <%
							List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
							tiposDocHelper.setOrdenar(false);
							tiposDocHelper.setTipos(tiposDocs);

							try {
							    tiposDocHelper.setId(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
							    tiposDocHelper.setCssClase("camposformtext");
							    tiposDocHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
							    tiposDocHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						    %>

							</td>
                            <td>N&uacute;mero</td>
                            <td>
	                        <%
							try {
								textHelper.setNombre(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
                            </td>
                            <td>Fecha</td>
                            <td><table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
			                        <%
									try {
										textHelper.setNombre(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
										textHelper.setCssClase("camposformtext");
										textHelper.setId(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
										textHelper.render(request,out);
									}
										catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
									%>
                                  <td><a href="javascript:NewCal('<%=AWModificarFolio.ANOTACION_FECHA_DOCUMENTO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>




                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Oficina de Procedencia </td>
                    </tr>
                    <!-- EL HELPER DE OFICINA EMPIEZA ACA -->
                        <jsp:include page="../correccion/HELPER_OFICINAS.jsp" flush="true" />
                    <!-- EL HELPER DE OFICINA TERMINA ACA -->
                <%--
                gov.sir.core.web.helpers.registro.OficinaHelper oficinaHelper = new gov.sir.core.web.helpers.registro.OficinaHelper ();
                try {
                    oficinaHelper.render(request,out);
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
				--%>




				</td>
				</tr>
				</table>







                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                      <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                      <td class="bgnsub">Especificaci&oacute;n</td>
                      <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                      <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_editar.gif" width="16" height="21"></td>
                      <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                    </tr>
                  </table>
                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Valor</td>
                      <td>
                   		<%
						try{
							textHelper.setTipo("text");
							textHelper.setNombre(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
						  	textHelper.setCssClase("camposformtext");
						  	textHelper.setId(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
						  	textHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
                        %>
                      </td>
                      <td>Naturaleza Jur&iacute;dica </td>
                      <td>
<input name="natjuridica_id" type="hidden" id="natjuridica_id" value="">
<input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value="">
 <%--/**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se agrega campos al formulario
                */
 --%>
<input name="natjuridica_ver" type="hidden" id="natjuridica_ver" value="">
<input name="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" type="hidden" id="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" value="<%=session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)%>">
                      <td> C&oacute;digo</td>
                      <td><%
                textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		textHelper.setCssClase("camposformtext");
		textHelper.setSize("3");
		textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		textHelper.setFuncion("onChange=\"cambiarAccionAndSendTipoTarifa('"+AWModificarFolio.CARGAR_DESCRIPCION_NATURALEZA+"')\"");
		textHelper.render(request,out);
		textHelper.setFuncion("");
                          %></td>
                      <td> Descripci&oa&oacute;n</td>
<td>
<%
		textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
		textAreaHelper.setCols("65");
		textAreaHelper.setReadOnly(true);
		textAreaHelper.setRows("1");
		textAreaHelper.setCssClase("camposformtext");
		textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
		textAreaHelper.render(request,out);
		textAreaHelper.setReadOnly(false);
%> </td>
<td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>                              
     <td>
                                            <% 
                                                String validarModalidad = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                boolean activarModalidad = false;
                                                if(validarModalidad != null && (validarModalidad.equals("0125") || validarModalidad.equals("0126"))){
                                                out.write("Modalidad");
                                                activarModalidad = true; 
                                                }
                                            %>
                                        </td>
                                  <td> 
                                      <%
                                          try { 
                                              if(activarModalidad){
                                        tipoModalidadHelper.setNombre(AWModificarFolio.ANOTACION_MODALIDAD); 
                                        tipoModalidadHelper.setId(AWModificarFolio.ANOTACION_MODALIDAD);
                                        tipoModalidadHelper.render(request, out);
                                              }
                                          }catch(HelperException re){ 
									out.println("ERROR " + re.getMessage()); 
								}%>
                                  </td>
                    </tr>

                  </table>
                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Comentario</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>
	                   		<%
							try{
								textAreaHelper.setNombre(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
								textAreaHelper.setCols("70");
								textAreaHelper.setRows("5");
								textAreaHelper.setCssClase("camposformtext");
								textAreaHelper.setId(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
								textAreaHelper.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
	                        %>
                      </td>
                    </tr>
                  </table>


  <%--
                  <br />

  							try{
						  		ciudadanosHelper.render(request,out);
						  	}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
  --%>

</tr>
</table>

<%--
     NOTE: close this helper inside a table;
     maybe the layput is wrong.
--%>

              <table>
                <tr>
                  <td>

	              <%

                      // :: personas asociadas

                      try {
			//TODO set ciudadanos
			variosCiudadanosHelper.render( request, out );
                      }
                      catch( HelperException re ) {
							re.printStackTrace();
                      }

                      %>
                    </td>
                  </tr>
              </table>


<table width='100%'>
<tr>
<td>
<!--TABLA SALVEDAD ANOTACION-->
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
<td class="bgnsub">Salvedad Anotaci&oacute;n</td>
<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
</tr>
</table>

<table width="100%" class="camposform">
<tr>
<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
<td>Salvedad</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>
<%
  	try{
		textAreaHelper.setNombre(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
		textAreaHelper.setCols("70");
		textAreaHelper.setRows("5");
		textAreaHelper.setCssClase("camposformtext");
		textAreaHelper.setId(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
		textAreaHelper.render(request,out);
	}catch(HelperException re){
		out.println("ERROR " + re.getMessage());
	}
%>
</td>
</tr>
</table>


              </td>
          </tr>
        </table>
         </td>
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      </tr>
      <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.AGREGAR_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="150" height="21" border="0"></a></td>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.CANCELAR_EDICION_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a></td>
            <td>&nbsp;</td>

          </tr>
        </table></td>
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
