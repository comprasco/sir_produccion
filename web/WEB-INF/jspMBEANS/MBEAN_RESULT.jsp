<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*, javax.management.*, org.auriga.core.mbeans.Manager"%>
<%
Manager m = Manager.getInstance();
ObjectName on = new ObjectName((String)request.getSession().getAttribute("objectName"));
MBeanInfo info = m.getMBeanInfo(on);
String operacion = (String)request.getSession().getAttribute("operationName");
String resultado = (String)request.getSession().getAttribute("resultado");
%>
<html>
<head>
<title>MBean Result</title>
<link href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
<link href="/jsp/auriga/auriga.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" cellspacing="0" cellpadding="5">
  <tr> 
    <td width="63%"><h2 class="titulo">Operacion Ejecutada</h2></td>
    <td width="17%"> <ul class="Boton">
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li> 
          <input type="button" name="btnMBeansManagerView" value="Ir al MBean" onClick="location.href='mbeans.detail.view?objectName=<%=on%>'">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_enviar.gif" width="27" height="22"></li>
      </ul></td> <td width="20%"> <ul class="Boton">
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li> 
          <input type="button" name="btnMBeansManagerView" value="Ir al Administrador" onClick="location.href='mbeans.manager.view'">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_enviar.gif" width="27" height="22"></li>
      </ul></td>
  </tr>
  <tr> 
    <td colspan="3"><h2 class="subtitulo">Resultado de la Operaci&oacute;n</h2>
      <p class="subtitulo">MBean:</p>
      <p class="titulo"><%= on%></p>
      <p class="subtitulo">Operacion:</p>
      <p class="titulo"><%= operacion %></p>
	  <p class="subtitulo">Resultado:</p>
	  <p class="titulo"><%= resultado %>	  </p>
	</td>

  </tr>
</table>
</body>
</html>
