<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.net.InetAddress"%>
<%@page import="gov.sir.print.server.PrintJobsProperties"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.modelo.transferObjects.Usuario"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%
	String maquina;
	String puerto;
	String contexto;
	String url;

	//maquina = request.getLocalAddr();
	//puerto = request.getLocalPort() + "";
	contexto = request.getContextPath();

	//url = maquina + ":" + puerto + "" + contexto;

	Usuario uAuriga = (Usuario) request.getSession().getAttribute(
			SMARTKeys.USUARIO_EN_SESION);
	String idUsuario = uAuriga.getUsuarioId();
%>

<%
	PrintJobsProperties p = PrintJobsProperties.getInstancia();
	String host = InetAddress.getLocalHost().getHostAddress();
	int port = Integer.parseInt(p
			.getProperty(PrintJobsProperties.P_SERVER_PORT));
	String context = request.getContextPath();
	Circulo c = (Circulo) session.getAttribute(WebKeys.CIRCULO);
	String ipAdmin = c.getPrintHost();
	String UID = c.getIdCirculo();

	//System.out.println("HOST ADMIN ES:" + host);
	//System.out.println("PORT ADMIN ES:" + port);
	//System.out.println("IPADMIN ES:" + ipAdmin);

	String usarBalanceador = (String) session.getServletContext()
			.getAttribute(CImpresion.USAR_BALANCEADOR);

	String ipBalanceador = (String) session.getServletContext()
			.getAttribute(CImpresion.IP_BALANCEADOR);
%>


<form id="formIniciarAdministrador" 
	action="<%=contexto%>/impresion/print_plus.jnlp" method="get" >
<input name="UID" id="UID" type="hidden" value="<%=UID %>" /> 
<input name="REMOTE_HOST" id="REMOTE_HOST" type="hidden" value="<%=host %>" />
<input name="REMOTE_PORT" id="REMOTE_PORT" type="hidden" value="<%=port %>" /> 
<input name="ID_USUARIO" id="ID_USUARIO" type="hidden" value="<%=idUsuario %>" /> 
<input name="btnSubmit"
	id="btnSubmit" type="submit" value="Iniciar Administrador de Impresion" />
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
