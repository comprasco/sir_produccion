<%@ taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
   <title>Fonopago - <smartTag:mostrar parametro="titulo" /></title>
   <meta http-equiv="Pragma" content="no-cache" />
   <meta http-equiv="Cache-Control" content="no-cache" />
   <link rel="stylesheet" href="/jsp/estilos.css" type="text/css" />
   <link rel="stylesheet" href="<%=request.getContextPath() %>/jsp/estilos.css" type="text/css" />
</head>
<body topmargin="0" leftmargin="0" bgcolor="#ffffff" marginwidth="0" marginheight="0">
<table border="0" cellpadding="0" cellspacing="0" summary="0" width="100%">
<tr><td>

<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
<tr>
          <td height="29" colspan="3" bgcolor="#666666">&nbsp;</td>
        </tr>
<tr>
          <td align="right" colspan="3" height="20">&nbsp;</td>
        </tr>
</table>

</td></tr>
<tr><td>
      <table border="0" cellpadding="0" cellspacing="5" summary="" width="90%" align="center">
      <tr><td>
      <font class="titulosazules"><smartTag:mostrar parametro="titulo" /></font><br/>
	  </td></tr>
      <tr>
          <td height="19"><smartTag:insertar parametro="body" /></td>
        </tr>
	  </table>
</td></tr>
<tr><td>

      <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
        <tr> 
    <td colspan="3" background="<%=request.getContextPath() %>/jsp/imagenes/pixel_gris.gif"><img src="<%=request.getContextPath() %>/jsp/imagenes/pixel_gris.gif" width="1" height="1" alt="COLFONDOS"></td>
</tr>
        <tr> 
          <td bgcolor="#FFFFFF">&nbsp;</td>
          <td valign="bottom"> 	</td>
          <td align="right" class="textoscopy" valign="bottom">      </td>
</tr>
</table>

</td></tr>
</table>
</body>
</html>