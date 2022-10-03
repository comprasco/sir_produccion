<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*, javax.management.*, org.auriga.core.mbeans.Manager"%>
<%
Manager m = Manager.getInstance();
Set s = m.getRegisteredMBeans();
Iterator it = s.iterator();
%>
<html>
<head>
<title>MBean Server</title>
<link href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
<link href="/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2 class="titulo">MBeans Registrados en Auriga</h2>
<table width="99%" border="0" align="center" cellpadding="1" cellspacing="0" class="tabla_general" >
  <tr class="titulotablas"> 
    <td width="20" class="imagen_inicio_tabla">&nbsp;</td>
    <td width="265">Mbean</td>
    <td width="225">Descripcion</td>
    <td width="605">Operaciones</td>
    <td width="11" valign="top" class="imagen_fin_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_tabla.gif" width="10" height="17"></td>
  </tr>
  <% while (it.hasNext()){
    ObjectInstance oi = (ObjectInstance)it.next();
    MBeanInfo info = m.getMBeanInfo(oi.getObjectName());
    MBeanOperationInfo[] ops = info.getOperations();
    
%>
  <tr onMouseOver="this.style.backgroundColor='#F0F6FA'" onMouseOut="this.style.backgroundColor=''"> 
    <td align="center">&nbsp;</td>
    <td align="left" valign="top"><a href="<%= request.getContextPath() %>/mbeans.detail.view?objectName=<%=  oi.getObjectName() %>"><%= oi.getClassName() %></a></td>
    <td><%= info.getDescription() %></td>
    <td><% for(int i=0;i<ops.length;i++){
      MBeanOperationInfo op = ops[i];%>
        <%= op.getName() %>  <%}%>
    </td>
  </tr>
  <%}%>
  <tr class="footer_tablas"> 
    <td class="imagen_inicio_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td><img src="<%= request.getContextPath() %>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
    <td align="right" class="imagen_fin_abajo_tabla"><img src="<%= request.getContextPath() %>/jsp/auriga/images/fin_abajo_tabla.gif" width="10" height="7"></td>
  </tr>
</table>
</body>
</html>
