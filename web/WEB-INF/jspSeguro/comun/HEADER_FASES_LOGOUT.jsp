<%@page import="gov.sir.fenrir.FenrirProperties"%>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>

<%Calendar fecha = Calendar.getInstance();
MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
fechaHelper.setFechaEncabezado(true);
%>
<script language='javascript'>
function borrarCookie(){
	try{
	    setCookie("appletImpresionCargado","",new Date(0));
	}catch(e){
	}
}
function setCookie(c_name,value,exp){
    c_string=c_name+"=";
    document.cookie=c_string + escape(value)+"; expires=" + exp.toGMTString();
}
</script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/disablerightclick.js"></script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="780">
            <table border="0" cellpadding="0" cellspacing="0" width="780">
                <tr> 
                    <td><img name="header_r1_c1" src="<%= request.getContextPath()%>/jsp/images/header_r1_c1.jpg" width="70" height="85" border="0" alt=""></td>
                    <td>
                    <table border="0" cellpadding="0" cellspacing="0" width="331">
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
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
        <td width="1%" class="contenido">&nbsp;</td>
        <td valign="top" class="footer">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
	        <td width="150" class="footer">
            <b>
                <!--
                    @autor: Fernando Padilla
                    @cambio:se agregó el numero de la versión de la salida a producción.
                -->
                <%
                FenrirProperties p = FenrirProperties.getInstancia();
                String R_VERSIONAMIENTO = p.getProperty(FenrirProperties.R_VERSIONAMIENTO);%>

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
			<td class="">
			<%Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
			  Solicitud solicitud = (Solicitud)session.getAttribute(WebKeys.SOLICITUD);
			if(turno!=null && turno.getIdWorkflow()!=null){%>
			<table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Turno: <%=turno.getIdWorkflow()%></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
          </table>
          <%
          }else if(solicitud != null){
		  %>
			<table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Liquidaci&oacute;n: <%=solicitud.getIdSolicitud()%></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
          </table>
          <%}%>          
           <%         
        if ((session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO) != null)
                && session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO).equals(new Boolean(true)))        {%>
            <!--<tr>
               <td><table border="0" cellpadding="0" cellspacing="0" class="tablas">
                    <tr><td class="tit_turno">Alerta: El administrador de impresi&oacute;n no est&aacute; funcionando</td></tr>
                </table></td>
            </tr>-->
            <table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="<%=request.getContextPath()%>/jsp/images/mensaje_animated.gif" width="10" height="14"></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Alerta: El administrador de impresi&oacute;n no est&aacute; funcionando</td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
          </table>
        <% } %>
          </td>
    	</tr>
            <tr>
               <td><table border="0" cellpadding="0" cellspacing="0" class="tablas">
                    <tr></tr>
                </table></td>
            </tr>
        </table>
    </td>
	   <td width="10%"> 
        <table border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
            <!--
                OSBERT LINERO - IRIDIUM Telecomunicaciones e informática Ltda.
                Cambio realizado para resolver requerimiento 092 - Incidencia Mantis 02940
                Se cambió el nombre del formulario, para enviarlo en caso de que se haya deshecho cambios en calificación,
                ya que este formulario tenía el mismo nombre que el de cerrar sesión
            -->
            <form name="logOutM" action="seguridad.do" method="post" type="submit">
                <td><input name="Submit" type="submit" class="irFases" value="."></td>
                <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                <% Proceso proceso = ((Proceso)session.getAttribute(WebKeys.PROCESO));
                	if(proceso != null){
                %>
                	
                <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
                    <!--
                        OSBERT LINERO - IRIDIUM Telecomunicaciones e informática Ltda.
                        Cambio realizado para resolver requerimiento 092 - Incidencia Mantis 02940
                        - Se verifica si existe el atributo de sesión "DESHICIERON_CAMBIOS_CALIFICACION".
                        - Si el atributo exite se remueve y se envía el formulario "logOutM".

                    -->
                    <% 
                        if(request.getSession().getAttribute(WebKeys.DESHICIERON_CAMBIOS_CALIFICACION) != null){
                            request.getSession().removeAttribute(WebKeys.DESHICIERON_CAMBIOS_CALIFICACION);
                    %>
                            <script>
                                document.logOutM.submit();
                            </script>
               <%
                        }
                	}
                %>
          
            </form>
            <form name="logOut" action="seguridad.do" method="post" type="submit">
                <td><input name="Submit" type="submit" class="cerrarsesion" value="." onclick="borrarCookie();"></td>
                <input type="hidden" name="ACCION" value="LOGOUT">
                </tr>
            </form>
        </table>
    </td>
    <td width="2%">&nbsp;</td>
    </tr>
</table>

