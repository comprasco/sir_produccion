<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*, javax.management.*, org.auriga.core.mbeans.Manager"%>
<%
ObjectName on = new ObjectName(request.getParameter("objectName"));
Manager m = Manager.getInstance();
MBeanInfo info = m.getMBeanInfo(on);

MBeanOperationInfo[] ops = info.getOperations();
%>
<html>
<head>
<title>MBean View</title>
<link href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
<link href="/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" cellspacing="0" cellpadding="5">
  <tr> 
    <td width="81%"><h2 class="titulo">MBean <%= on%></h2></td>
    <td width="19%"> <ul class="Boton">
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li> 
          <input type="button" name="btnMBeansManagerView" value="Ir al Administrador" onClick="location.href='mbeans.manager.view'">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_enviar.gif" width="27" height="22"></li>
      </ul></td>
  </tr>
  <tr> 
    <td colspan="2"><h2 class="subtitulo">Informacion</h2>
	<p>
      Tipo (Clase) :<%= info.getClassName()%><br>
      Descripcion : <%= info.getDescription()%><br> </td>
	  </p>
  </tr>
</table>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabla_general">
  <tr class="titulotablas"> 
    <td width="24" class="imagen_inicio_tabla">&nbsp;</td>
    <td width="187">Operaci&oacute;n</td>
    <td width="268">Descripci&oacute;n</td>
    <td width="630">Par&aacute;metros</td>
    <td width="10" class="imagen_fin_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_tabla.gif" width="10" height="17"></td>
  </tr>
  <% for (int i=0; i < ops.length; i++){
          MBeanOperationInfo op = ops[i];
      %>
  <tr> 
    <td>&nbsp;</td>
    <td><%= op.getName() %></td>
    <td><%= op.getDescription() %></td>
    <td> <form action="MBEANS_INVOKE.do">
	    <input type="hidden" name="objectName" value="<%= on%>"/>
        <input type="hidden" name="operationName" value="<%= op.getName()%>"/>

        <table width="100%" cellpadding="5" cellspacing="5" >
          <% 
		  String signature ="";
          MBeanParameterInfo[] params = op.getSignature();
          for(int j=0;j<params.length;j++){
              MBeanParameterInfo param = params[j];
              signature = signature + param.getType() + ",";
          %>
          <tr> 
            <td width="9%" class="texto_importante"><%= j+1%></td>
            <td width="8%"><%= param.getName() %></td>
            <td width="10%"><%= param.getType() %></td>
            <td width="30%"><input name="param<%= j%>" type="text" class="completar2" id="param<%= j%>"></td>
          </tr>
          <%}%>
          <tr> 
            <td colspan="4"> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="77%">&nbsp;</td>
                  <td width="23%"><ul class="Boton">
                      <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
                      <li> 
                        <input type="submit" name="Submit" value="Ejecutar">
                      </li>
                      <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_enviar.gif" width="27" height="22"></li>
                    </ul></td>
                </tr>
              </table> 
              
            </td>
          </tr>
        </table>
      <input type="hidden" name="signature" value="<%= signature.lastIndexOf(",")!=-1?signature.substring(0,signature.lastIndexOf(",")):""%>"/>
      </form></td>
  </tr>
  <%}%>
  <tr class="footer_tablas"> 
    <td class="imagen_inicio_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td class="imagen_fin_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_abajo_tabla.gif" width="10" height="7"></td>
  </tr>
</table>
</body>
</html>
