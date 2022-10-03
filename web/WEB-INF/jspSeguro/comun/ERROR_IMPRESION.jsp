<%@page import ="org.auriga.smart.SMARTKeys" %>
<%@page import ="gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException" %>
<%
	Exception exception = (Exception)session.getAttribute(SMARTKeys.EXCEPCION);
	session.removeAttribute(SMARTKeys.EXCEPCION);
	String mensaje = "La operacion fue realizada satisfactoriamente";
	if (exception instanceof ErrorImpresionException){
		ErrorImpresionException error = (ErrorImpresionException)exception;
		mensaje="La operacion sobre el turno " + error.getIdTurno() + " fue realizada satisfactoriamente";	
	}
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"> 
<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js">-->
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="tablaError">
  <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_error_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Error de Impresi�n</td>
          <td width="14"><img name="tabla_error_r1_c4" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
        <tr>
          <td align="center" valign="middle" class="titulotbcentral"><%=mensaje%><br>No se pudo realizar la impresi&oacute;n</td>
          <td>
			<image src="<%=request.getContextPath()%>/jsp/images/mensaje_animated.gif" />
			<!--
			<script language="javascript" type="text/javascript">
				  var Imagen="<%=request.getContextPath()%>/jsp/images/mensaje_animated.gif"
				  var pelicula="<%=request.getContextPath()%>/jsp/images/mensaje_animated.swf"
			      var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
				  var ancho="70"
				  var alto="70"
				  //plugindetectado();
				</script>
				-->
				</td>
        </tr>
		<tr>
		
	       	<form name="form1" method="post" action="procesos.view">
		    <td>
	        <input name="Submit2" type="submit" class="botontextual" value="Regresar al Menu principal">
			</td>
			</form>		
		</tr>
      </table></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>