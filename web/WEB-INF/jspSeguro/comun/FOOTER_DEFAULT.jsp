<%@page import = "gov.sir.core.web.helpers.comun.AlertasHelper"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "org.auriga.core.web.*"%>
<%@page pageEncoding="UTF-8"%>

<%AlertasHelper alertas = new AlertasHelper();
try {
	alertas.render(request,out);
}catch(HelperException re){
	out.println("ERROR " + re.getMessage());
}	
%>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <!-- fwtable fwsrc="SIR_footer.png" fwbase="footer.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
      <td valign="top"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
      <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
      <td valign="top"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
  </tr>

  
</table>
