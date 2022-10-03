<%@page import="java.net.InetAddress" %>
<%@page import="gov.sir.print.server.PrintJobsProperties" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>
<%@page import="gov.sir.core.web.WebKeys"%>


<% 
	PrintJobsProperties p = PrintJobsProperties.getInstancia();
	String host = InetAddress.getLocalHost().getHostAddress();
	int port = Integer.parseInt(p.getProperty(PrintJobsProperties.P_SERVER_PORT));
	String context = request.getContextPath();
	Circulo c = (Circulo)session.getAttribute(WebKeys.CIRCULO);
	String ipAdmin = c.getPrintHost();
	String UID = c.getIdCirculo();

	//System.out.println("HOST ADMIN ES:" + host);	
	//System.out.println("PORT ADMIN ES:" + port);
	//System.out.println("IPADMIN ES:" + ipAdmin);	

	String usarBalanceador = (String)session.getServletContext().getAttribute(CImpresion.USAR_BALANCEADOR);

	String ipBalanceador = (String)session.getServletContext().getAttribute(CImpresion.IP_BALANCEADOR);
	
	//System.out.println("usarBalanceador:" + usarBalanceador);	
	//System.out.println("ipBalanceador:" + ipBalanceador);

	
%>

<body topmargin='0' leftmargin='0' marginwidth='0' marginheight='0'">
<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0' class='camposform'>
<tr><td align='center'>
<% if (false){
   //if (!ipAdmin.equals(host)){
out.println("Este equipo no tiene privilegios de administrador del circulo registral "+UID);
}
else{%>
<APPLET
CODE = 'gov.sir.print.client.plus.PrintJobsPlusApplet.class'
ARCHIVE = '<%= context%>/publicJars/signed.gov.sir.print.client.jar,
           <%= context%>/publicJars/gov.sir.core.negocio.modelo.jar,
           <%= context%>/publicJars/org.auriga.core.modelo.jar'
CODEBASE = '<%= context%>/publicJars/'
WIDTH = '580' HEIGHT = '520'>
<PARAM NAME = 'REMOTE_HOST' VALUE='<%= host %>'>
<PARAM NAME = 'REMOTE_PORT' VALUE='<%= port %>'>
<PARAM NAME = 'UID' VALUE = '<%=UID%>'>
<PARAM NAME = 'BUSY' VALUE = 'busy.gif'>
<PARAM NAME = 'READY' VALUE = 'ready.gif'>
<PARAM NAME = 'WAIT' VALUE = 'wait.gif'>
<PARAM NAME = 'ICONO' VALUE = 'icono.gif'>
<PARAM NAME = 'LOCAL_PORT_LOW' VALUE = '3330'>
<PARAM NAME = 'LOCAL_PORT_HIGH' VALUE = '3630'>
</APPLET>
<%}%>
<td>
</tr>
<tr>
<td align="center">Impresion SIR <%= host%>:<%=port%>:<%=UID%></td>
</tr>
</table>
</body>

<!-- 
<PARAM NAME = 'REMOTE_HOST' VALUE='<%= (usarBalanceador!=null && usarBalanceador.equals(CImpresion.SI_USAR_BALANCEADOR) ? ipBalanceador : host ) %>'>
-->
