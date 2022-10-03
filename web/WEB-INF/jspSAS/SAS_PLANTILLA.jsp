<%@ taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>AURIGA - SAS - <smartTag:mostrar parametro="titulo"/></title>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="65"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="11"><img src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_sas_r1_c1.gif" width="11" height="65"></td>
        <td width="91"><img src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_sas_r1_c2.gif" width="91" height="65"></td>
        <td valign="bottom" background="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_bgn_sas.gif"><table width="100%" border="0">
            <tr>
              <td class="titulo"><smartTag:mostrar parametro="titulo" /></td>
            </tr>
        </table></td>
        <td width="39"><img src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_sas_r1_c4.gif" width="39" height="65"></td>
      </tr>
    </table></td>
  </tr>
  <tr> 
    <td valign="top">
	<table width="90%" align="center" ><tr><td><smartTag:insertar parametro="body" /></td></tr></table>
	</td>
  </tr>
</table></td>
</tr> </table> 
</body>
</html>
