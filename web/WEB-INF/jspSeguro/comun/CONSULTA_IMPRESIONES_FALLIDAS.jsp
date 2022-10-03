<%@ page import="java.io.*,gov.sir.core.web.WebKeys" %><%@ page contentType="application/octet-stream"%><%
java.util.List imp = (java.util.List)session.getAttribute(WebKeys.LISTA_IMPRESIONES_FALLIDAS);
OutputStream output = response.getOutputStream();
ObjectOutputStream oos = new ObjectOutputStream( output );
session.removeAttribute(WebKeys.LISTA_IMPRESIONES_FALLIDAS);
oos.writeObject(imp);
oos.flush();
oos.close();
%>