<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>

<HTML>
<head>
	<script language="javascript">
	
		function cambiarAccion() {
			document.CONSULTAR.submit();
		}
	</script>
</head>
<body>
	<form name="CONSULTAR" id="CONSULTAR" action="consultasReparto.do" method="POST">
		<input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWConsultasReparto.LISTAR_PENDIENTES%>" value="<%=AWConsultasReparto.LISTAR_PENDIENTES%>">
	</form>
	<script language="javascript">
		cambiarAccion();
	</script>
</body>
</HTML>