<%@page 
import="org.auriga.sas.SASKeys,
org.auriga.core.web.*,
org.auriga.sas.web.helpers.*" %>
<link rel="stylesheet" href="<%= request.getContextPath()%>/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<%
   request.getSession().setAttribute(PaginadorHelper.PAGINA,request.getParameter(PaginadorHelper.PAGINA));
   TablaSolicitudesHelper registros = new TablaSolicitudesHelper();
   PaginadorHelper ph = new PaginadorHelper();
   ph.setVista("sas.manager.view");
   RecargarListaHelper rlh = new RecargarListaHelper();
  %>
<form name="frmSolicitudes" method="post">
    <table width="100%" border="0" align="center" cellpadding="1" cellspacing="0" class="tabla_general" >
        <tr class="titulotablas"> 
            <td width="22" class="imagen_inicio_tabla">&nbsp;</td>
            <td>Id Ejecucion</td>
            <td>Nombre Solicitud</td>
            <td>Fase</td>
            <td>Estado</td>
            <td>Estacion</td>
            <td width="10" class="imagen_fin_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_tabla.gif" width="10" height="17"></td>
        </tr>
    <% try {
           rlh.render(request,out);
           registros.render(request, out); 
       }
       catch(Exception re){
           re.printStackTrace();
           out.println("ERROR " + re.getMessage());
       }
    %>
        <tr class="footer_tablas"> 
            <td valign="bottom" class="imagen_inicio_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7" ></td>
            <td valign="bottom"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7" ></td>
            <td valign="bottom"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7" ></td>
            <td valign="bottom"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
            <td valign="bottom"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7" ></td>
            <td valign="bottom"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7" ></td>
            <td class="imagen_fin_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_abajo_tabla.gif" width="10" height="7"></td>
        </tr>
    </table>
</form>

<center>
<% try {
    ph.render(request, out);
    }
    catch(Exception re){
           re.printStackTrace();
           out.println("ERROR " + re.getMessage());
     }
%>
</center>
<br>
