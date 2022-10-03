<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWImpresionFolio" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>


<%
TextHelper textHelper = new TextHelper();
/*Turno ultimoTurno = (Turno)session.getAttribute(AWImpresionFolio.ULTIMO_TURNO_IMPRESO);
String idTurno = "";
if(ultimoTurno != null) {
    idTurno = ultimoTurno.getIdWorkflow();
    session.removeAttribute(AWImpresionFolio.ULTIMO_TURNO_IMPRESO);
}
Boolean recarga = (Boolean)session.getAttribute(WebKeys.RECARGA);
session.removeAttribute(WebKeys.RECARGA);*/
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cargarUltimoTurno() {
	cambiarAccion('<%= AWImpresionFolio.OBTENER_ULTIMO_TURNO_IMPRESO %>');
	document.BUSCAR.submit();
}
</script>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
<script language='javascript'>
	function cargarApplet(){
        var app =  getCookie("appletImpresionCargado");
        if (app == null){
       		var x = eval (window.screen.availWidth - 310);
			var y = eval (window.screen.availHeight - 450);
			var w = window.open('<%= request.getContextPath()%>/impresion.view','applet_impresion','width=300,height=400,resizable=no,scrollbars=no,location=no,status=yes,menubar=no,copyhistory=no,left='+x+',top='+y);
			//w.resizeTo(300,150);
			this.window.focus();
            setCookie("appletImpresionCargado",true);
        }
}
</script>
<body onload='cargarApplet()'>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reimpresi&oacute;n de Recibos</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Seleccione el consecutivo de recibos por el cuál desea reimprimir</td>
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
          
       <form action="impresionFolio.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWImpresionFolio.REIMPRIMIR_RECIBO %>" value="<%=  AWImpresionFolio.REIMPRIMIR_RECIBO %>">
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos del Turno</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="20%">N&uacute;mero del turno</td>
                    <td class="campositem">
                    <%
					String idTurno = (String)session.getAttribute(CTurno.ID_TURNO);
					%><%=idTurno!=null?idTurno:""%>&nbsp;
					<input type='hidden' id='<%=CTurno.ID_TURNO%>' name='<%=CTurno.ID_TURNO%>' value='<%=idTurno!=null?idTurno:""%>'>
                    </td>
                    <td> &nbsp; </td>
                  </tr>
                </table>
                
                
			<table width="100%" class="camposform">
			<tr>
			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
			<td height="17" colspan='5' class="titulotbcentral">Seleccione una Estacion</td>
			</tr>
			<%
			List estaciones = (List)session.getAttribute(AWImpresionFolio.LISTA_ESTACIONES);
			int i = 0;
            java.util.Iterator itEstaciones = estaciones.iterator();
            while (itEstaciones.hasNext()) {
                ElementoLista estacion = (ElementoLista) itEstaciones.next();
                String checked = new String(); 
                if( i==0){
                	checked = "checked";
                }
           %>
                <tr>
                <td><label>
                <input type="radio"  <%=checked%>  name="ID_ESTACION" value="<%=estacion.getId()%>">
                </label></td>
                <td width="10%"><b>Estación -</b></td>
                <td width="40%" class="campositem"><%=estacion.getId()%></td>
                <td width="20%"><b>Número recibo actual -</b></td>                
                <td width="30%" class="campositem"><%=estacion.getValor()%></td>                
                </tr>
                <%
                i++;
            }
            %>
			</table>
			
			<hr class="linehorizontal">           
                
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWImpresionFolio.REIMPRIMIR_RECIBO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_reimprimir.gif" width="150" height="21" border="0">
                    	</td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=  AWImpresionFolio.REGRESAR_REIMPRIMIR_RECIBO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
<%-- if(recarga == null) { %>
<SCRIPT>
	cargarUltimoTurno();
</SCRIPT>
<% } --%>
