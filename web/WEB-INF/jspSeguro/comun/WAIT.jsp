<%@page import="java.util.*,
                gov.sir.core.web.WebKeys"
%>
<%

String toDo = request.getParameter(WebKeys.REDIRECCION_DO);
String queryString ="";

//Es posible que se desee enviar, para el mismo parámetro, 
//MAS DE DOS VALORES, de allí la inyección de la siguiente
//porción de código
Enumeration enu= request.getParameterNames();

while(enu.hasMoreElements())
{
	String parameterName = (String)enu.nextElement();
	String [] parameterValues = request.getParameterValues(parameterName);
	
	if(parameterValues != null)
	{
		for (int i = 0; i < parameterValues.length; i++) 
		{
			queryString += parameterName+"="+parameterValues[i]+"&";
		}
	}
}	
if (queryString.length()>0)
{
	queryString = queryString.substring(0,queryString.length()-1);
}
%>
<script>
redirTime = "24000";
redirURL = "<%= toDo%>?<%=queryString%>";
function redirTimer() { window.setTimeout("self.location.href = redirURL;",redirTime); }
</script>
<body onload='redirTimer()'>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Procesando</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="12">&nbsp;</td>
        <td width="12"><img name="tabla_error_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Ejecutando 
                  Proceso</td>
              <td width="14"><img name="tabla_error_r1_c4" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_error_r1_c6" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
            <tr>
                <td align="center" valign="middle"> 
                <image src="<%=request.getContextPath()%>/jsp/images/reloj_animated.gif" />
                <!--
                  <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" name="error" height="70" width="70" align="top" class="fndtablagral" id="error">
                    <param name=movie value="<%= request.getContextPath()%>/jsp/images/reloj_animated.swf">
                    <param name=quality value=autohigh>
                    <param name=wmode value=transparent>
                    <param name="menu" value="false">
                    <embed src="<%= request.getContextPath()%>/jsp/images/reloj_animated.swf" width="70" height="70" align="top" quality=autohigh wmode=transparent name="error"
 type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" menu="false"></embed> 
                  </object> 
                  -->
                  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tablas">
                    <tr>
                      <td align="center">Un momento por favor. El sistema esta 
                        ejecutando la operaci&oacute;n seleccionada.</td>
                    </tr>
                </table></td>
                <td width="70">&nbsp;</td>
            </tr>
        </table></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
        <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
<% out.flush(); %>
</body>