<%@ page import ="org.auriga.smart.SMARTKeys" %>
<%@taglib uri="/smartTags" prefix="smartTag"%>
<% Exception exception = (Exception)session.getAttribute(SMARTKeys.EXCEPCION);
%>
<html>
<head>
	<%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
	<!--<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">-->
    <title>SNR :: Superintendencia de Notariado y Registro :: Ministerio del interior y de Justicia :: Rep&uacute;blica de Colombia</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!-- Fireworks MX Dreamweaver MX target.  Created Wed Jul 21 13:36:02 GMT-0500 (Hora est. del Pacífico de SA) 2004-->
    <link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>
	function campoactual(id){
	if (document.getElementById("ultimo_campo_editado")!=null){
		document.getElementById("ultimo_campo_editado").value=id;
	}}
</script>
<body bgcolor="#CDD8EA" background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tablagral">
    <tr> 
    <td height="85"><smartTag:insertar parametro="head" /></td>
    </tr>
    <tr> 
        <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<%if (exception != null) {%>
        <tr> 
        <td><smartTag:insertar parametro="error" /></td>
    </tr>
	<%}%>
        <tr> 
        <td>
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
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr> 
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registrar Pago </td>
                <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
            </tr>
        </table>
        </td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    
    <%
	    gov.sir.core.negocio.modelo.Solicitud solicitud = (gov.sir.core.negocio.modelo.Solicitud)session.getAttribute(gov.sir.core.web.WebKeys.SOLICITUD);
	    if(solicitud==null){
    %>
    <tr> 
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02">
        
        <smartTag:insertar parametro="solicitud" />
        
        </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    
    <%
    	}
    %>

    <tr> 
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02">
        &nbsp;
        </td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
 
    <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    
    <smartTag:insertar parametro="pago" />
    
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
        </td>
        </tr>
    </table>
      
    </td>
    </tr>
    <tr> 
    <td valign="bottom"><smartTag:insertar parametro="bottom" /></td>
    </tr>
</table>
</body>
</html>