<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.smart.SMARTKeys,gov.sir.core.web.helpers.comun.ExceptionHelper" %>
<% Exception exception = (Exception)session.getAttribute(SMARTKeys.EXCEPCION);
	ExceptionHelper exceptionHelper = new ExceptionHelper();
%>

<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"> 
<script language="JavaScript" type="text/JavaScript">
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
</script>

<!--<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/plugins.js">-->
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
    <td width="12"><img name="tabla_error_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_error.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Error 
            del sistema</td>
          <td width="14"><img name="tabla_error_r1_c4" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"> 
    
    
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas"><tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tablas">
			<tr> 
			<td><%
			  session.removeAttribute(SMARTKeys.EXCEPCION);
			  session.setAttribute(ExceptionHelper.EXCEPCION,exception);
			
			  if(exception.getMessage()==null){
                                        /**
                                         * @Autor: Edgar Lora
                                         * @Mantis: 003_589
                                         */
                                        if(session.getAttribute(WebKeys.IDENTIFICADOR_DE_SESSION) != null){
                                            out.write("Sesion cerrada por logueo con otro usuario.");
                                        }else{
                                            out.write("Ha ocurrido un error con descripcion no disponible. Para ver mas detalles haga click en \"Ver la Excepcion\"");
                                        }
			  }else{
				  Throwable e = exception.getCause();
				  while(e !=null && e.getCause()!=null){
				  	 e = e.getCause();
				  }
				  out.write(exception.getMessage() +  (e!=null&&e.getMessage()!=null?" : "+e.getMessage():"")); 
			  }%></td>

			</tr>
			<br>
			<tr>
			<td height="20"> 
			<input name="Submit" type="submit" class="botontextual" onClick="MM_openBrWindow('excepcion.view','EXCEPCION','resizable=yes,scrollbars=yes,location=no,status=yes,menubar=no,copyhistory=no, width=640,height=400')" value="Ver la Excepci&oacute;n">
			</td>
			</tr>
			</table></td>
			<td>
			<image src="<%=request.getContextPath()%>/jsp/images/error_animated.gif" />
			<!--
			<script language="javascript" type="text/javascript">
				  var Imagen="<%=request.getContextPath()%>/jsp/images/error_animated.gif"
				  var pelicula="<%=request.getContextPath()%>/jsp/images/error_animated.swf"
			      var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
				  var ancho="70"
				  var alto="70"
				  //plugindetectado();
				</script>
				-->
				</td>
			</tr>
			</table>
			
      </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>