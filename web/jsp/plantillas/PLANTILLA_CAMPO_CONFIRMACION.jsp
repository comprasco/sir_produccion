<%@taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
	<%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
    <title>SNR :: Superintendencia de Notariado y Registro :: Ministerio del interior y de Justicia :: Rep&uacute;blica de Colombia</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tablagral">
    <tr> 
    <td height="85"><smartTag:insertar parametro="head" /></td>
    </tr>
    <tr> 
        <td valign="top"> 
	        <smartTag:insertar parametro="confirmacion" />
    	</td>
    </tr>
</table>
<div class="tablagral" title="pie">
<smartTag:insertar parametro="bottom" />
</div>
</body>
</html>
