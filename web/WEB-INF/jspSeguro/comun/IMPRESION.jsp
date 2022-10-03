<%@page import="java.net.InetAddress" %>
<%@page import="gov.sir.print.server.PrintJobsProperties" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>
<%@page import="org.auriga.core.modelo.transferObjects.Usuario"%>
<%@page import="org.auriga.smart.SMARTKeys"%>

<%
	PrintJobsProperties p = PrintJobsProperties.getInstancia();
	String host = InetAddress.getLocalHost().getHostAddress();
	int port = Integer.parseInt(p.getProperty(PrintJobsProperties.P_SERVER_PORT));
	String contexto;
	
	contexto = request.getContextPath();
	
	//System.out.println("HOST ES:" + host);
	//System.out.println("PORT ES:" + port);
	
	String context = request.getContextPath();
	String UID = request.getSession().getId();
	
	String usarBalanceador = (String)session.getServletContext().getAttribute(CImpresion.USAR_BALANCEADOR);

	String ipBalanceador = (String)session.getServletContext().getAttribute(CImpresion.IP_BALANCEADOR);
	Usuario uAuriga = (Usuario) request.getSession().getAttribute(
			SMARTKeys.USUARIO_EN_SESION);
	String idUsuario = uAuriga.getUsuarioId();
%>

<form id="formIniciarAdministrador" 
	action="<%=contexto%>/impresion/print_client.jnlp" method="get" >
<input name="UID" id="UID" type="hidden" value="<%=UID %>" /> 
<input name="REMOTE_HOST" id="REMOTE_HOST" type="hidden" value="<%=host %>" />
<input name="REMOTE_PORT" id="REMOTE_PORT" type="hidden" value="<%=port %>" /> 
<input name="ID_USUARIO" id="ID_USUARIO" type="hidden" value="<%=idUsuario %>" /> 
<input name="btnSubmit"
	id="btnSubmit" type="submit" value="Iniciar Impresion Cliente" />
</form>
<script language="javascript">
function iniciar(){
	document.getElementById("formIniciarAdministrador").submit();
	//window.close();
}

function cerrar() { 
	self.setTimeout("window.close();", 7000); 
}
</script>
<script>
iniciar();
cerrar();
</script>

<BR/><b>&nbsp;&nbsp;HOST :</b><%= host%>:<%=port%>
<BR/><b>&nbsp;&nbsp;UID&nbsp;&nbsp; :</b><%= UID %>
