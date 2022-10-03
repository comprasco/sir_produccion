<%@page import="org.auriga.core.web.*,org.auriga.sas.web.helpers.*" %>
<% ListaAdministradoresHelper listaAdmin = new ListaAdministradoresHelper(); %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="jsp/auriga/auriga.css" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td height="10" align="left"><table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="cabezote.png" fwbase="cabezote.gif" fwstyle="Dreamweaver" fwdocid = "847837668" fwnested="1" -->
    <tr> 
        <td width="11"><img name="cabezote_r1_c1" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c1.gif" width="11" height="65" border="0" alt=""></td>
        <td width="77"><img name="cabezote_r1_c2" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c2.gif" width="77" height="65" border="0" alt=""></td>
        <td width="156"><img name="cabezote_r1_c3" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c3.gif" width="156" height="65" border="0" alt=""></td>
        <td background="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_bgn.gif"><img name="cabezote_r1_c4" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c4.gif" width="467" height="65" border="0" alt=""></td>
        <td width="49"><img name="cabezote_r1_c5" src="<%= request.getContextPath() %>/jsp/auriga/images/cabezote_r1_c5.gif" width="49" height="65" border="0" alt=""></td>
    </tr>
</table></td>
    </tr>
  <tr class="linea_hotizontal"> 
      <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/barra_horizontal.gif" width="7" height="3"></td>
  </tr>
  <tr> 
    <td class="titulo">Administrador de Solicitudes Concurrentes</td>
  </tr>
  <tr class="linea_hotizontal"> 
      <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/barra_horizontal.gif" width="7" height="3"></td>
  </tr>
  <tr> 
      <td align="left" height="10" width="100%"><table width="100%" >
      <tr> 
      <td class="Boton">
      <ul>
      
                <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
          <li> 
              <form action="sas.manager.view" method="get">
              
                  <input type="hidden" name="SAS_RECARGAR_LISTA_REGISTRO_EJECUCION" value="SI">
                  <%String numeroRegistros="10";%>
                  <input type="hidden" name="SAS_RECARGAR_LISTA_REGISTRO_EJECUCION_NUM" value="<%=numeroRegistros%>">
                  <input type="submit" name="Submit" value="Listar Ultimos <%=numeroRegistros%>">
              </form>
          </li>
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_fin_gral.gif" width="27" height="22"></li>
      
      
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
          <li> 
              <form action="sas.manager.view" method="get">
                  <input type="hidden" name="SAS_RECARGAR_LISTA_REGISTRO_EJECUCION" value="SI">
                  <input type="submit" name="Submit" value="Recarga">
              </form>
          </li>
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_regarga.gif" width="27" height="22"></li>
          
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
          <li> 
          <input type="submit" name="Submit" value="Buscar Solicitud">
          </li>
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_buscar.gif" width="27" height="22"></li>
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
          <li> 
          <input type="submit" name="Submit" value="Nueva Solicitud" onClick="nuevaSolicitud()">
          </li>
          <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_nuevo.gif" width="27" height="22"></li>
      </ul></td>
      <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/ico_titulos.gif" width="20" height="21"></td>
      <td align="right"><% try {
          listaAdmin.render(request,out);
       }
       catch(HelperException re){
           out.println("ERROR " + re.getMessage());
       }
    %></td>
  </tr>
      </table></td>
  </tr>
  <tr> 
      <td align="left" height="10">&nbsp;</td>
  </tr>
</table>
