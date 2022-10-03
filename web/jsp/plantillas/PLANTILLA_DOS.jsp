<%@taglib uri="/smartTags" prefix="smartTag"  %>
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
<body bgcolor="#CDD8EA" background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tablagral">
    <tr>
    <td height="85"><smartTag:insertar parametro="head" /></td>
    </tr>
    <tr>
        <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td><smartTag:insertar parametro="body1" /></td>
    </tr>
    </table>

    </td>
    </tr>

    <tr>
    <td valign="bottom"><smartTag:insertar parametro="body2" /></td>
    </tr>

    <tr>
    <td valign="bottom"><smartTag:insertar parametro="bottom" /></td>
    </tr>
</table>
</body>
</html>
