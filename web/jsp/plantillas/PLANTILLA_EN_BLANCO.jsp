<%@taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
     <%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
    <title><smartTag:mostrar parametro="titulo" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<body bgcolor="#CDD8EA" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div class="tablagral" title="encabezado" >
<smartTag:insertar parametro="pagina" />
</div>
</body>
</html>