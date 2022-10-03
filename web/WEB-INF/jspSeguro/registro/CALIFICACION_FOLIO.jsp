<script>
function cambiarAccion(text) {
       document.CALIFICAR.ACCION.value = text;
       document.CALIFICAR.submit();
       }

</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Documento sin t&iacute;tulo</title>
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .Estilo2 {font-size: 11px; color: #3A414E; text-decoration: none; background-color: #FFFFFF; border-top: 1px solid #5D687D; border-right: 1px solid #5D687D; border-bottom: 1px solid #5D687D; border-left: 5px solid #6A7891; font-family: Verdana, Arial, Helvetica, sans-serif;}
        -->
    </style>
</head>
<% 
	session.removeAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.FOLIO_CALIFICACION);
%>
<body>
    
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
    

    
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
		<%@ include file="CALIFICACION_FOLIO_TABS.jsp" %> 
		
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>



   
     



  
    
  
</table>		
	
    
</body>
</html>
