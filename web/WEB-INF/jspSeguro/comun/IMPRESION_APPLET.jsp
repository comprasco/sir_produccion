<%@page import="java.net.InetAddress" %>
<%@page import="gov.sir.print.server.PrintJobsProperties" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>
<%@page import="java.util.Enumeration" %>  
<%@page import="java.net.NetworkInterface" %>  

<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
<script language='javascript'>

   function checkParentWindow(){
	    if (window.opener.closed){
		    window.close();
     	}
    }

    function reloadWindow(){
	    if (window.opener.closed){
	    	return;
	    }
	    else{
	        alert('Applet de Impresion de SIR:\n\nNo es posible cerrar esta ventana hasta que cierre el navegador de Internet donde está ejecutando SIR.');
       		var x = eval (window.screen.availWidth - 310);
			var y = eval (window.screen.availHeight - 450);
			
			var w = window.open('<%= request.getContextPath()%>/impresion.view','','width=300,height=400,resizable=no,scrollbars=no,location=no,status=yes,menubar=no,copyhistory=no,left='+x+',top='+y);
			//var w = window.open('<%= request.getContextPath()%>/impresion.view','','height=300,width=500,status=yes,toolbar=no,menubar=no,location=no');			
			
			//w.resizeTo(400,400);
			w.opener = window.opener;
		}
	}

</script>
<body topmargin='0' leftmargin='0' marginwidth='0' marginheight='0' onLoad="setInterval('checkParentWindow()',5000);" onUnload="reloadWindow();">
<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0' class='camposform'>
<tr>
<td colspan='3'>&nbsp;</td>
</tr>
<tr>
<td width='6'>&nbsp;</td>
<td>
<%
	PrintJobsProperties p = PrintJobsProperties.getInstancia();
	String host = InetAddress.getLocalHost().getHostAddress();
	int port = Integer.parseInt(p.getProperty(PrintJobsProperties.P_SERVER_PORT));

	//System.out.println("HOST ES:" + host);
	//System.out.println("PORT ES:" + port);
	
	String context = request.getContextPath();
	String UID = request.getSession().getId();
	
	String usarBalanceador = (String)session.getServletContext().getAttribute(CImpresion.USAR_BALANCEADOR);

	String ipBalanceador = (String)session.getServletContext().getAttribute(CImpresion.IP_BALANCEADOR);
%>
<APPLET
CODE = 'gov.sir.print.client.PrintJobsApplet.class'
ARCHIVE = '<%= context%>/publicJars/signed.gov.sir.print.client.jar,
           <%= context%>/publicJars/gov.sir.core.negocio.modelo.jar,
           <%= context%>/publicJars/org.auriga.core.modelo.jar'
CODEBASE = '<%= context%>/publicJars/'
WIDTH = '280' HEIGHT = '310'>
<PARAM NAME = 'REMOTE_HOST' VALUE='<%= (usarBalanceador!=null && usarBalanceador.equals(CImpresion.SI_USAR_BALANCEADOR) ? ipBalanceador : host ) %>'>
<PARAM NAME = 'REMOTE_PORT' VALUE='<%= port %>'>
<PARAM NAME = 'UID' VALUE='<%= UID %>'>
<PARAM NAME = 'BUSY' VALUE = 'busy.gif'>
<PARAM NAME = 'READY' VALUE = 'ready.gif'>
<PARAM NAME = 'WAIT' VALUE = 'wait.gif'>
<PARAM NAME = 'LOCAL_PORT_LOW' VALUE = '3330'>
<PARAM NAME = 'LOCAL_PORT_HIGH' VALUE = '3630'>
</APPLET>
</td>
<td width='6'>&nbsp;</td>
</tr>
<tr>
<td colspan='3' align="left">
<BR><b>&nbsp;&nbsp;HOST :</b><%= host%>:<%=port%>
<BR><b>&nbsp;&nbsp;UID&nbsp;&nbsp; :</b><%= UID %></td>
</tr>
<tr>
<td colspan='3'>&nbsp;</td>
</tr>

</table>
</body>
</html>