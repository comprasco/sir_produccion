<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWLiquidacionConsulta" %>

<html>
<head>
    <title>SNR :: Superintendencia de Notariado y Registro :: Ministerio del interior y de Justicia :: Rep&uacute;blica de Colombia</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!-- Fireworks MX Dreamweaver MX target.  Created Wed Jul 21 13:36:02 GMT-0500 (Hora est. del Pacífico de SA) 2004-->
    <link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
</head>

<SCRIPT LANGUAGE="javascript">
function send()
{document.forma.submit()}
</SCRIPT>

<body bgcolor="#CDD8EA" onLoad="send()" background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tablagral">
    <tr>
    <form action="consultasLiquidacion.do" method="POST" name="forma"  id="forma">
  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWLiquidacionConsulta.LIQUIDAR_CONSULTA_OBSERVACION_FOLIO %>" value="<%= AWLiquidacionConsulta.LIQUIDAR_CONSULTA_OBSERVACION_FOLIO %>">
	</form>	
    </tr>
</table>
</body>
</html>