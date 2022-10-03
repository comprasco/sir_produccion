<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.List,org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWLiquidacionConsulta" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="gov.sir.core.negocio.modelo.TipoConsulta" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.AlcanceGeografico" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%

ListaRadioHelper radioHelper = new ListaRadioHelper();
List tiposAlcance = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO);

List roles = (List)session.getAttribute(WebKeys.LISTA_ROLES);
Iterator itRoles = roles.iterator();

boolean adminNal = false;
	
while (itRoles.hasNext())
{
	Rol rol = (Rol) itRoles.next();
	if (rol.getRolId().equals("SIR_ROL_ADMINISTRADOR_NACIONAL"))
	{
		adminNal = true;
	}
}

List alcances= new ArrayList();
//quitar el alcance nacional
for(Iterator iterat = tiposAlcance.iterator(); iterat.hasNext(); ){
	ElementoLista a = (ElementoLista) iterat.next();
	if(!a.getId().equals(AlcanceGeografico.TIPO_NACIONAL)){
		alcances.add(a);
	}
}

radioHelper.setTipos(alcances);

// Para datos de solicitante
TextHelper textHelper = new TextHelper();
ListaElementoHelper tiposDocHelper = new ListaElementoHelper();
List tiposDocs = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ID);
tiposDocHelper.setOrdenar(false);
tiposDocHelper.setTipos(tiposDocs);

String tipoConsulta = (String)request.getSession().getAttribute(AWConsulta.TIPO_CONSULTA);
if (tipoConsulta != null && !tipoConsulta.equals("")) {
    tipoConsulta = AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA;
}

if(session.getAttribute(AWConsulta.TIPO_CONSULTA)== null) {
	session.setAttribute(AWConsulta.TIPO_CONSULTA,"0");
	tipoConsulta = AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA;
}

if(session.getAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS)== null) {
	session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS,"1");
}

if(session.getAttribute(CCiudadano.TIPODOC)== null) {
	session.setAttribute(CCiudadano.TIPODOC,CCiudadano.TIPO_DOC_ID_SECUENCIA);
}

%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>

<script type="text/javascript">
	function deshabilitarBoton() {
		document.getElementById('imageField_id').width=0;
	}

	function cambiarAccion(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	}

	function oficinas(nombre,valor,dimensiones){
		//document.BUSCAR.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
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

	function locacion1(nombre,valor,dimensiones){
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
    <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
  </tr>
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Nueva Consulta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <!--AHERRENO 24/05/2012
                    REQ 076_151 TRANSACCIONES-->
                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                        <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
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




          <form action="consultasLiquidacion.do" method="post" name="BUSCAR" id="BUSCAR"><br>
           <input type="hidden" name="<%= WebKeys.ACCION  %>" id="<%=tipoConsulta%>" value="<%=tipoConsulta%>">
<!--         <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.SELECCION_CONSULTA %>" value="<%= AWConsulta.SELECCION_CONSULTA %>"> -->

	<input name="Depto" type="hidden" id="id_depto" value="">
	<input name="Depto" type="hidden" id="nom_Depto" value="">
	<input name="Mpio" type="hidden" id="id_munic" value="">
	<input name="Mpio" type="hidden" id="nom_munic" value="">
	<input name="Ver" type="hidden" id="id_vereda" value="">
	<input name="Ver" type="hidden" id="nom_vereda" value="">

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		<td align="left">
			<table border="0" cellpadding="0" cellspacing="2" class="camposform">
			<tr>
				<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
				<td>Ir a la pantalla con todas las opciones</td>
				<td><a href="consultas.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
			</tr>
		</td>
			</table>
	</table>
<br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

	           <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Alcance Geogr&aacute;fico de la Consulta </td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
			  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table><br>
          <table width="100%" class="camposform">
            <tr>
              <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
              <td class="titulotbcentral">Alcance</td>
            </tr>
            <% try {
					if(session.getAttribute(CAlcanceGeografico.ALCANCE_CONSULTA)== null) {
						session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA,CAlcanceGeografico.ID_LOCAL);
					}
                    radioHelper.setNombre(CAlcanceGeografico.ALCANCE_CONSULTA);
                  	radioHelper.setCssClase("camposformtext");
                  	radioHelper.setId(CAlcanceGeografico.ALCANCE_CONSULTA);
					radioHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
          </table><br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Realizar la consulta de acuerdo al Tipo</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table><br>
          <table width="100%" class="camposform">
            <tr>
              <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
              <td class="titulotbcentral">Tipo de Consulta</td>
              <td width="16" class="titulotbcentral">&nbsp;</td>
              <td class="titulotbcentral">&nbsp;</td>
            </tr>
<!--            <tr>
              <td><input name="<%= AWConsulta.TIPO_CONSULTA %>" type="radio"
                onClick="cambiarAccion('<%=AWLiquidacionConsulta.LIQUIDAR_CONSULTA_OBSERVACION_FOLIO%>');document.BUSCAR.<%= CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS %>.disabled = true;"
                <%  Object tipo = request.getSession().getAttribute(AWConsulta.TIPO_CONSULTA);
                    if (tipo != null && ((String)tipo).equals("0")) {%>checked<%}%>
                    value="<%= TipoConsulta.TIPO_FOLIOS %>"></td>
              <td> Observar Informaci&oacute;n del Folio </td>
              <td><span class="titulotbcentral"><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></span></td>
              <td class="campostip04">Determinar N&uacute;mero de Matr&iacute;cula</td>
            </tr> -->

<%

Rol rol1 = (Rol) session.getAttribute(WebKeys.ROL);


if(! (rol1.getRolId().equals(AWLiquidacionConsulta.ROL_USUARIO_OPERATIVO) || rol1.getRolId().equals(AWLiquidacionConsulta.ROL_USUARIO_OPERATIVO_CONSULTAS))){


%>

            <tr>
              <td><input name="<%= AWConsulta.TIPO_CONSULTA %>" type="radio" 
                onClick="cambiarAccion('<%=AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA%>')"
                <%  tipo = request.getSession().getAttribute(AWConsulta.TIPO_CONSULTA);
                    if (tipo != null && ((String)tipo).equals("0")) {%>checked<%}%>
                    value="<%= TipoConsulta.TIPO_SIMPLE %>"></td>
              <td>Consulta simple (de 1 a 3)</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>

<%

} else {

%>

<script>

cambiarAccion('<%=AWLiquidacionConsulta.LIQUIDAR_CONSULTA_EXENTA%>');

</script>
            <tr>
              <td><input name="<%= AWConsulta.TIPO_CONSULTA %>" type="hidden"
                value="<%= TipoConsulta.TIPO_EXENTO %>"></td>
              <td>Consulta exenta</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
         
          </table>
 
            
            
<%

}

%>   


<%

Rol rol = (Rol) session.getAttribute(WebKeys.ROL);




%>
         

          <hr class="linehorizontal">

          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">N&uacute;mero de B&uacute;squedas</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>

          <table width="100%" class="camposform">
             <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="179">N&uacute;mero de B&uacute;squedas</td>
              <td width="211">
			 <% try {
                    textHelper.setNombre(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
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
			  <td>&nbsp;</td>
            </tr>
          </table>

          <hr class="linehorizontal">

          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          <br>

         <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Solicitante</td>
              <td>
              <% try {
                    textHelper.setNombre(CCiudadano.APELLIDO1);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadano.APELLIDO1);
					textHelper.setSize("38");
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
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
              <td width="140"><input name="imageField" id="imageField_id" onClick = "javascript:deshabilitarBoton()" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_liquidar.gif" width="139" height="21" border="0"></td>
              <td>&nbsp;</td>
            </tr>
          </table>



          </form>



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
</table>
