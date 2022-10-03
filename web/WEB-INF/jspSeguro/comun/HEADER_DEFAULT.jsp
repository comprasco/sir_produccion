<%@page import="gov.sir.fenrir.FenrirProperties"%>
<%@page import ="java.util.Calendar" %>
<%@page import="org.auriga.core.web.Helper" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>
<%
    Calendar                    fecha = null;
    MostrarFechaHelper    fechaHelper = null;
    String                 rutaFirmas = null;
    
    fecha = Calendar.getInstance();
    
    fechaHelper = new MostrarFechaHelper();
    fechaHelper.setFechaEncabezado(true);
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td width="780">
    <table border="0" cellpadding="0" cellspacing="0" width="780">
    <!-- fwtable fwsrc="SIR_header.png" fwbase="header.jpg" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
        <td><img name="header_r1_c1" src="<%= request.getContextPath()%>/jsp/images/header_r1_c1.jpg" width="70" height="85" border="0" alt=""></td>
        <td><table border="0" cellpadding="0" cellspacing="0" width="331">
            <tr> 
                <td><img name="header_r1_c2" src="<%= request.getContextPath()%>/jsp/images/header_r1_c2.gif" width="331" height="48" border="0" alt=""></td>
            </tr>
            <tr> 
                <td><img name="header_r2_c2" src="<%= request.getContextPath()%>/jsp/images/header_r2_c2.gif" width="331" height="37" border="0" alt=""></td>
            </tr>
        </table></td>
        <td><img name="header_r1_c3" src="<%= request.getContextPath()%>/jsp/images/header_r1_c3.jpg" width="379" height="85" border="0" alt=""></td>
    </tr>
</table>
      </td>
      <td background="<%= request.getContextPath()%>/jsp/images/header_bgn001.jpg">&nbsp;</td>
  </tr>
  <% 
        if ((session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO) != null)
                && session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO).equals(new Boolean(true)))
        {%>
            <tr>
               <td><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="<%=request.getContextPath()%>/jsp/images/mensaje_animated.gif" width="10" height="14"></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Alerta: El administrador de impresi&oacute;n no est&aacute; funcionando</td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
          </table></td>
            </tr>
        <% } %>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="12" class="contenido">&nbsp;</td>
    <td class="footer">
        <b>
            <!--
                @autor: Fernando Padilla
                @cambio:se agregó el numero de la versión de la salida a producción.
            -->
            <%
                FenrirProperties p = FenrirProperties.getInstancia();
                String R_VERSIONAMIENTO = p.getProperty(FenrirProperties.R_VERSIONAMIENTO);
            %>

            Sistema de Información Registral - <a href="<%=request.getContextPath()%>/version.pdf" target="_blank"><%=R_VERSIONAMIENTO%></a>
            
            <br/>Fecha Actual:
            <%try {
                fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                fechaHelper.setDate(fecha.getTime());
                fechaHelper.render(request,out);
            }
                catch(HelperException re){
                out.println("ERROR " + re.getMessage());
            }%>
        </b>
		</td>
    <td>
</td>
    </tr>
</table>
