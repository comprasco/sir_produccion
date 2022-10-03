<%@page import="gov.sir.fenrir.FenrirProperties"%>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.negocio.modelo.Folio" %>
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
			<%
			Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
            Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
            String labelMatricula=null;
            if (folio!=null)
            	if(folio.isDefinitivo()){            	
	              	labelMatricula=folio.getIdMatricula();
	            }else{
	            	gov.sir.core.negocio.modelo.Fase fase = (gov.sir.core.negocio.modelo.Fase)session.getAttribute(WebKeys.FASE);

					//SI SE ESTA EN UNA FASE DE ANTIGUO SISTEMA NO SE MUESTRA EN Nï¿½MERO DEL NUEVO FOLIO, DE LO CONTRARIO SI.
					if(fase.getID().startsWith("ANT_")){
		            	labelMatricula=folio.getNombreLote();
					}else{
						labelMatricula=folio.getIdMatricula();
					}
	            }
            %>
          
	          <table border="0" cellpadding="0" cellspacing="0" align="center">
	        <%
          if(turno!=null) {
	        %> 
                <tr>
                <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Turno: <%=turno.getIdWorkflow()%></td>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
                  <td></td>
                  </tr>
          </table></td>
                </tr>
            <%
            if (labelMatricula!=null) {
            %>
	            <tr>
                    <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
	              <td width="10"><img src="<%=request.getContextPath()%>/jsp/images/tit_matriculas_r1_c1.gif" width="10" height="16" border="0" alt=""></td>
	              <!--<td width="30"><img src="<%=request.getContextPath()%>/jsp/images/tit_matriculas_r1_c2.gif" width="30" height="16" border="0" alt=""></td>-->
	              <td background="<%=request.getContextPath()%>/jsp/images/tit_matriculas_bgn.gif"class="tit_turno">Folio: <%=labelMatricula%></td>
	              <td width="10"><img src="<%=request.getContextPath()%>/jsp/images/tit_matriculas_r1_c4.gif" width="10" height="16" border="0" alt=""></td>
                    </tr>
          </table></td>
	            </tr>
            <%}%>     
        <%}%>
        <% 
        if ((session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO) != null)
                && session.getAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO).equals(new Boolean(true)))
        {%>
            <tr>
               <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
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
            <form name="logOut" action="seguridad.do" method="post" type="submit">
                <td><input name="Submit" type="submit" class="irFases" value="."></td>
                <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                <input type="hidden" name="ID_PROCESO" value="<%= ((Proceso)session.getAttribute(WebKeys.PROCESO)).getIdProceso()%>">
          
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