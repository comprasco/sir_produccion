<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
    <%@page  import="gov.sir.core.web.WebKeys" %>
    <%
        Integer numEstaciones = (Integer)request.getAttribute(WebKeys.NUM_ESTACIONES);
        request.removeAttribute(WebKeys.NUM_ESTACIONES);
        Integer numProcesos = (Integer)request.getAttribute(WebKeys.NUM_PROCESOS);
        request.removeAttribute(WebKeys.NUM_PROCESOS);
    %>
   
    <script>

    <%
        /* JAlcaza 20/05/2009
         *No se hace necesario realizar validación en el if de las estaciones debido a que ente fase
         *La estación ya ha sido escogida por el usuario
         *if(numEstaciones!=null && numEstaciones.intValue()==1 && numProcesos!=null && numProcesos.intValue()==1){
        */
        if(numProcesos!=null && numProcesos.intValue()==1){
    %>
            document.Estaciones.submit();
    <%
        }
    %>
    </script>
    
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
        <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">SNR 
        :: Superintendencia de Notariado y Registro</td>
        <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
    </tr>
    </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
    </tr>
    <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"> 
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central_blank.png" fwbase="tabla_central_b.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn003.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    <tr> 
    <td width="7"><img name="tabla_central_b_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn001.gif" class="titulotbcentral">SNR</td>
        <td><img name="tabla_central_b_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_llave.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td><img name="tabla_central_b_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table> </td>
    <td width="11"><img name="tabla_central_b_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr> 
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn004.gif"><img src="<%= request.getContextPath()%>/jsp/images//spacer.gif" width="7" height="10" border="0" alt=""></td>
    <td> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tdAnexo02">
        <tr>
        <td width="450"><img src="<%= request.getContextPath()%>/jsp/images/img_proceso.jpg" height="150"></td>
        <td valign="top"> <table border="0" cellpadding="0" cellspacing="0" width="150">
        <!-- fwtable fwsrc="SIR_central_pantallasinternas_sub_blank.png" fwbase="sub_b.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
            <td width="12"><img name="sub_b_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_b_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnbsub">SNR</td>
            <td width="16" class="bgnbsub"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="16" height="22" border="0" alt=""></td>
            <td width="15"><img name="sub_b_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_b_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
            <td align="left" class="contenido">&nbsp;</td>
            <td align="left" class="contenido"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
                <tr> 
                    <td valign="middle"><img src="<%= request.getContextPath()%>/jsp/images/ind_flecha.gif" width="20" height="15"></td>
                    <td valign="middle"><a href="#" class="links">Condiciones 
                        Generales de Uso | Pol&iacute;tica de Privacidad</a> 
                    </td>
                </tr>
            </table></td>
        </tr>
        <tr>
            <td align="left" class="contenido">&nbsp;</td>
            <td align="left" class="contenido"><hr class="camposform"></td>
        </tr>
        <tr> 
        <td width="12" align="left" class="contenido">&nbsp;</td>
        <td align="center" class="contenido">Sistema de Informaci&oacute;n 
        y Registro :: <span class="titulotbcentral"> SIR</span></td>
        </tr>
        <tr> 
            <td align="left" class="contenido">&nbsp;</td>
            <td align="left" class="contenido"><hr class="camposform"> 
            </td>
        </tr>
        <tr> 
            <td align="left" class="contenido">&nbsp;</td>
            <td align="left" class="contenido">&nbsp;</td>
        </tr>
    </table></td>
    </tr>
</table></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn005.gif"><img src="<%= request.getContextPath()%>/jsp/images//spacer.gif" width="11" height="10" border="0" alt=""></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_b_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_b_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images//spacer.gif" width="10" height="6" border="0" alt=""></td>
          <td><img name="tabla_central_b_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_b_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
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