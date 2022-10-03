<%@ taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
   <title>Demo - <smartTag:mostrar parametro="titulo" /></title>
   <meta http-equiv="Pragma" content="no-cache" />
   <meta http-equiv="Cache-Control" content="no-cache" />
   <link rel="stylesheet" href="<%=request.getContextPath() %>/jsp/estilos.css" type="text/css" />
</head>
<body topmargin="0" leftmargin="0" bgcolor="#ffffff">
<table border="0" cellpadding="0" cellspacing="0" summary="0" width="100%">
<tr><td>

<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
<tr>
          <td height="29" colspan="3" bgcolor="#999999"><font color="#FFFFFF" size="5"><strong>&nbsp;&nbsp;Demo</strong></font></td>
        </tr>
<tr>
          <td align="right" colspan="3" height="20"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="81%">
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr> 
                      <td width="27" bgcolor="#eeeeee">&nbsp;</td>
                      <td width="27" bgcolor="#eeeeee">&nbsp;</td>
                      <td width="27" bgcolor="#eeeeee" >&nbsp;</td>
                      <td width="27" bgcolor="#eeeeee" >&nbsp;</td>
                      <td width="8" bgcolor="#eeeeee" >&nbsp;</td>
                      <td width="661" bgcolor="#eeeeee" >&nbsp;</td>
                      <td width="65" align="center" bgcolor="#eeeeee">&nbsp;</td>
                      <td width="142" bgcolor="#eeeeee">&nbsp;</td>
                    </tr>
                  </table>
                </td>
             </tr>
            </table>
            
          </td>
        </tr>
</table>

</td></tr>
<tr><td>

      <table border="0" cellpadding="0" cellspacing="5" summary="" width="90%" align="center">
      <tr><td>
	  <font class="titulosverdes">Test</font> 
      <font class="titulosazules"><smartTag:mostrar parametro="titulo" /></font><br/>
	  </td></tr>
      <tr><td><smartTag:insertar parametro="body" /></td></tr>
	  </table>
</td></tr>
<tr><td>

      <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
        <tr> 
    <td colspan="3"><img src="<%=request.getContextPath() %>/jsp/imagenes/pixel_gris.gif" width="1" height="1" alt="COLFONDOS"></td>
</tr>
        <tr> 
          <td bgcolor="#FFFFFF">&nbsp;</td>
          <td valign="bottom"> </td>
          <td align="right" class="textoscopy" valign="bottom"> </td>
</tr>
</table>

</td></tr>
</table>
</body>
</html>