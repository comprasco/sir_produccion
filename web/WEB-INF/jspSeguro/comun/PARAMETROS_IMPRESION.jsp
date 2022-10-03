<%@ page import="java.io.*,gov.sir.core.web.WebKeys,java.util.*,gov.sir.core.negocio.modelo.*" %><%
java.util.Map configuracion = (java.util.Map)session.getAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CARGAR_PARAMETROS_CONFIGURACION);
OutputStream output = response.getOutputStream();
ObjectOutputStream oos = new ObjectOutputStream( output );
session.removeAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CARGAR_PARAMETROS_CONFIGURACION);
oos.writeObject(configuracion);
oos.flush();
oos.close();
%>