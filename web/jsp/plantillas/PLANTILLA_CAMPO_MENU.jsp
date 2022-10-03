<%@ page import ="org.auriga.smart.SMARTKeys,gov.sir.core.web.helpers.comun.*" %>
<%@taglib uri="/smartTags" prefix="smartTag"  %>
<% Exception exception = (Exception)session.getAttribute(SMARTKeys.EXCEPCION);
   MenuHelper menuHelper = new MenuHelper();%>
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

<div class="tablagral" title="encabezado" >
<smartTag:insertar parametro="head" />
</div>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="150" valign="top"> 
        <!--Inicia Menu -->
		<%/* try {
				menuHelper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}*/
		%>  
     <smartTag:insertar parametro="menu" />
        <!--Fin Menu -->
    </td>
    <td valign="top">
    	<!-- Inicia Error -->
		<% if (exception != null) {%>  
		    <smartTag:insertar parametro="error" />
		<%}%>
        <!-- Fin Error -->
        <!-- Inicia Contenido -->
        <smartTag:insertar parametro="body" />
        <!-- Fin Contenido -->
    </td>
    </tr>
</table>
<div class="tablagral" title="pie">
<smartTag:insertar parametro="bottom" />
</div>
</body>
</html>
