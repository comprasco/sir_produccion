<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.ErrorFormularioHelper" %>
<%
	ErrorFormularioHelper errorFormularioHelper = new ErrorFormularioHelper();
%>
<!--<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/plugins.js">-->
</script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"> <table width="100%" border="0" cellpadding="0" cellspacing="0" id="tablaError">
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
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_errorform.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_error.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Error en la Validaci&oacute;n de Datos del Formulario</td>
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
      		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
		<tr>
		<td valign="top"> <table width="100%">
		
		<tr>
		<td width="16"><img src="<%=request.getContextPath()%>/jsp/images/bullet_ok.gif" width="12" height="11"></td>
		<td class="errortit">Los Siguientes Errores han sido encontrados en el Formulario: </td>
		</tr>
		</table>
			<% try {
					/**
                                         * @Autor: Edgar Lora
                                         * @Mantis: 003_589
                                         */
                                        if(session.getAttribute(WebKeys.IDENTIFICADOR_DE_SESSION) != null){
                                            out.write("ERROR: Sesion cerrada por logueo con otro usuario.");
                                        }else{
                                            errorFormularioHelper.render(request,out);
                                        }                                        
				}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
			%>
			</td>
          <td>
		  <image src="<%=request.getContextPath()%>/jsp/images/errorform_animated.gif" />
		  <!--
          <script language="javascript" type="text/javascript">
		  var Imagen="<%=request.getContextPath()%>/jsp/images/errorform_animated.gif"
		  var pelicula="<%=request.getContextPath()%>/jsp/images/errorform_animated.swf"
		  var ancho="70"
			      var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
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