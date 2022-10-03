<%@page import="gov.sir.fenrir.FenrirProperties"%>
<%@page import ="java.util.*" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%Calendar fecha = Calendar.getInstance();
MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
fechaHelper.setFechaEncabezado(true);
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/disablerightclick.js"></script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<table border="0" cellpadding="0" cellspacing="0" width="310">
<!-- fwtable fwsrc="SIR_Cabezote_PEQU.png" fwbase="header_peq.jpg" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
      <td><img name="header_peq_r1_c1" src="<%= request.getContextPath()%>/jsp/images/header_peq_r1_c1.gif" width="81" height="90" border="0" alt=""></td>
      <td><img name="header_peq_r1_c2" src="<%= request.getContextPath()%>/jsp/images/header_peq_r1_c2.jpg" width="519" height="90" border="0" alt=""></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td width="12"> </td>
    <td class="contenido">
        <b>
            <!--
                @autor: Fernando Padilla
                @cambio:se agregó el numero de la versión de la salida a producción.
            -->
<%
                FenrirProperties p = FenrirProperties.getInstancia();
                String R_VERSIONAMIENTO = p.getProperty(FenrirProperties.R_VERSIONAMIENTO);%>

                Sistema de Información Registral - <a href="<%=request.getContextPath()%>/version.pdf" target="_blank"><%=R_VERSIONAMIENTO%></a>            <br/>Fecha Actual:
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
                <%Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
			if(turno!=null){%>
			<table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Turno: <%=turno.getIdWorkflow()%></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
          </table>
          <%}%></td>
    </tr>
</table>
