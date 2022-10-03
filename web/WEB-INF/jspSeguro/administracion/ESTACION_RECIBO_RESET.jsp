<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacionRecibo" %>
<%@page import="gov.sir.core.negocio.modelo.EstacionRecibo" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
TextHelper textHelper = new TextHelper();

EstacionRecibo estacion = (EstacionRecibo)session.getAttribute(AWAdministracionHermod.ESTACION_RECIBO_SELECCIONADA);

boolean editoOK = (session.getAttribute(AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK)==null)?false:true;
session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK);

String numInicial = "&nbsp;";
String numFinal = "&nbsp;";
if(estacion != null){
	numInicial = ""+estacion.getNumeroInicial();
	numFinal = ""+estacion.getNumeroFinal();
	session.setAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO, ""+estacion.getUltimoNumero());
	}
	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionAndSend(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Estaciones Recibo HERMOD </td>
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
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Modificar N&uacute;mero Actual del Recibo en la Estaci&oacute;n</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
                    <td class="bgnsub">Estaciones / Recibos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION  %>" id="<%=   (estacion==null )? AWAdministracionHermod.CONSULTA_ESTACION_RECIBO : AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO %>" value="<%=  (estacion==null )?AWAdministracionHermod.CONSULTA_ESTACION_RECIBO : AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO %>">
				<%
				if(estacion != null){
				%>	             
	                <table width="100%" class="camposform">
	                  <tr>
	                    <td>&nbsp;</td>
	                    <td>N&uacute;mero Inicial</td>
	                   	<td class="campositem"><%= numInicial%></td>
	                  </tr>
	                  <tr>
	                    <td>&nbsp;</td>
	                    <td>N&uacute;mero Final</td>
	                    <td class="campositem"><%= numFinal%></td>
	                  </tr>
	                  <tr>
	                    <td>&nbsp;</td>
	                    <td>N&uacute;mero Actual del Recibo </td>
	                    <td>
	                    	<% try {
	                    textHelper.setNombre(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
	                  	textHelper.setCssClase("camposformtext");
	                  	textHelper.setId(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
						textHelper.render(request,out);
						}
						catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
				  %>
	                    </td>
	                  </tr>
	                </table>
	                <table width="100%" class="camposform">
	                  <tr>
	                    <td width="20">&nbsp;</td>
	                    <td class="campostip04">Para editar el N&uacute;mero Actual del Recibo en la Estaci&oacute;n haga click en editar. <br>Para finalizar haga click en terminar. </td>
	                  </tr>
	                </table>
	            <%
	            }
				else{
				%>	
					<%
					if(editoOK){
					%>
					<table width="100%" class="camposform">
	                  <tr>
	                    <td width="20">&nbsp;</td>
	                    <td class="campositem">Se realiz&oacute; satisfactoriamente la modificaci&oacute;n del N&uacute;mero Actual del Recibo en la Estaci&oacute;n. </td>
	                  </tr>
	                </table>
					<%
					}
					%>
					
				<table width="100%" class="camposform">
	                  <tr>
	                    <td width="20">&nbsp;</td>
	                    <td class="campostip04">Para ver el N&uacute;mero Actual del Recibo en la Estaci&oacute;n haga click en Consultar.</td>
	                  </tr>
	                  <tr>
	                    <td width="20">&nbsp;</td>
	                    <td class="campostip04">Para terminar haga click en aceptar. </td>
	                  </tr>
	                </table>
				<%
	            }
				%>	 
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
               <%
				if(estacion == null){
				%>	
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.CONSULTA_ESTACION_RECIBO %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    	</td>
                 <%
	            } 
				else{
				%>	
                    	<td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=   AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                    	</td>
                <%
	            }
				%>	
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
            </FORM>    
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