<%@ page import="java.io.*"%><%@ page contentType="application/octet-stream"%><%
java.util.List reglas = (java.util.List)session.getAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CONSULTAR_LISTA_REGLAS);
OutputStream output = response.getOutputStream();
ObjectOutputStream oos = new ObjectOutputStream( output );
session.removeAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CONSULTAR_LISTA_REGLAS);
oos.writeObject(reglas);
oos.flush();
oos.close();
%>