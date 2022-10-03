<%@taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
     <%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
    <title>Superintendencia de Notariado y Registro</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#CDD8EA" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tablagral">
<tr><td><smartTag:insertar parametro="body" /></td></tr>
</table>
</body>
</html>