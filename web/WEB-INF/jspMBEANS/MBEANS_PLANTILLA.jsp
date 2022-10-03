<%@ taglib uri="/smartTags" prefix="smartTag"  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>AURIGA - SAS - <smartTag:mostrar parametro="titulo"/></title>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="10" align="left"> <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="cabezote.png" fwbase="cabezote.gif" fwstyle="Dreamweaver" fwdocid = "847837668" fwnested="1" -->
        <tr> 
          <td width="11"><img name="cabezote_r1_c1" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c1.gif" width="11" height="65" border="0" alt=""></td>
          <td width="77"><img name="cabezote_r1_c2" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c2.gif" width="77" height="65" border="0" alt=""></td>
          <td width="156"><img name="cabezote_r1_c3" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c3.gif" width="156" height="65" border="0" alt=""></td>
          <td background="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_bgn.gif"><img name="cabezote_r1_c4" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c4.gif" width="467" height="65" border="0" alt=""></td>
          <td width="49"><img name="cabezote_r1_c5" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c5.gif" width="49" height="65" border="0" alt=""></td>
        </tr>
      </table></td>
  </tr>
  <tr class="linea_hotizontal"> 
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/barra_horizontal.gif" width="7" height="3"></td>
  </tr>
  <tr> 
    <td class="titulo"><smartTag:mostrar parametro="titulo" /></td>
  </tr>
  <tr class="linea_hotizontal"> 
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/barra_horizontal.gif" width="7" height="3"></td>
  </tr>
  <tr> 
    <td colspan="2"><smartTag:insertar parametro="body" /></td>
  </tr>
</table></td>
</tr> </table> 
</body>
</html>
