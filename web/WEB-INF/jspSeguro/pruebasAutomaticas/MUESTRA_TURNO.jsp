<html>
<body>
<TABLE ID="tablaTurno">
<tr>
<% 
gov.sir.core.negocio.modelo.Turno turno = (gov.sir.core.negocio.modelo.Turno)session.getAttribute(gov.sir.core.web.WebKeys.TURNO);
String idWorkflow = turno.getIdWorkflow();
%>
<td><%=idWorkflow%></td>
</tr>
</TABLE>
</body>
</html>