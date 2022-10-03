<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
if(session.getAttribute("PANTALLA_CARGADA")==null){
	double rand = Math.random()*100000;
	String ventanaID = ""+Math.round(rand);
	session.setAttribute("PANTALLA_CARGADA", ventanaID);
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function openWindow(){
		var url =  '<%=request.getContextPath()%>/inicio.view';
		var winName = 'SIR_<%=ventanaID%>'; 
  		var features='location=no,status=yes,scrollbars=yes,resizable=yes,fullscreen=no,width='+(screen.availWidth-10)+',height='+(screen.availHeight-60)+',left=0,top=0';
  		window.open(url,winName,features);
		//location.href = 'bienvenido.cargado.view';
		//window.close();
		
		var v = window.self;
		v.opener=window.self;
		v.close();		
}
//-->
</script>
<%
}
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/disablerightclick.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"> 
    <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js">
    </script>
    <title>Documento sin t&iacute;tulo</title>
</head>
<body  >
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
    <td width="12"><img name="tabla_error_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_animation.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Intro:: 
        Aplicativo </td>
        <td width="14"><img name="tabla_error_r1_c4" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
        </tr>
    </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
    </tr>
    <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><script language="javascript" type="text/javascript">
    var Imagen="<%=request.getContextPath()%>/jsp/images/intro_aplicativo.jpg"
    var pelicula="<%=request.getContextPath()%>/jsp/images/intro_aplicativo.swf"
    var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
    var ancho="100%"
    var alto="340"
    plugindetectado();
    </script>
    <br>
    <center><a href="#" class="biglinks"  onClick="openWindow()"   >Saltar Introducci&oacute;n :: Entrar</a></center>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td align="center" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif"></td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
    </tr>
    </table>
</body>
</html>