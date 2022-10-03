<%@page import="gov.sir.core.web.acciones.registro.AWConsultaFolio"%>
<script>
function cambiarAccion(text) {
       document.CALIFICAR.ACCION.value = text;
       document.CALIFICAR.submit();
       }

function cambiarAccionPreview(text) {
	document.BUSCAR.ACCION.value = text;
	document.BUSCAR.submit();
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
	String nombrePaginador2="NOMBRE_PAGINADOR_DETALLES";
	
	boolean termino=false;
	 Boolean term=(Boolean)session.getAttribute(AWConsultaFolio.TERMINO);
	 if(term!=null){
	 	termino=term.booleanValue();
	 }
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
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
    
    
    
     <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consulta - Folio</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>    
    

    
    <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
		<%@ include file="PREVIEW_CONSULTA_FOLIO_TABS.jsp" %> 
		
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>



   
     

    
    
    
    
    
    
    <!--Fila para colocar la opción de regresar-->
    <tr>

    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">



    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Regresar a consulta de folios </td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
	    <table width="100%" class="camposform">
	      <tr>
	        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	        <form action="consultar.folio.do" method="POST" name="BUSCAR" id="BUSCAR">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  AWConsultaFolio.TERMINAR_PREVIEW %>">	        
	        <input  type="hidden" name="<%= WebKeys.NOMBRE_PAGINADOR %>" id="<%= WebKeys.NOMBRE_PAGINADOR %>" value="<%= nombrePaginador2 %>">
	        <input  type="hidden" cambiarAccionPreview(<%=AWConsultaFolio.TERMINO%>)">
	        <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="150" height="21" border="0"></a>
	        </td>
	        </FORM>    
	      </tr>
	    </table>
    </td>
    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>
    </table>


    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
        
    </tr>    
    
        


  
    
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>		
	
    
</body>
</html>
<% if(termino){%>
	<script>
		window.close()
	</script>
<%}%>