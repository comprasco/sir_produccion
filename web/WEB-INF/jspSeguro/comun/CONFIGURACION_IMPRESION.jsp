<%@ page import="java.io.*,gov.sir.core.web.WebKeys,java.util.*,gov.sir.core.negocio.modelo.*" %><%
java.util.Map configuracion = (java.util.Map)session.getAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CARGAR_CONFIGURACION_ACTUAL);
Iterator it = configuracion.keySet().iterator();
		while(it.hasNext()){
			TipoImprimible ti=(TipoImprimible)it.next();
			Iterator itImpr=((List)configuracion.get(ti)).iterator();
			while(itImpr.hasNext()){
				CirculoImpresora ci=(CirculoImpresora)itImpr.next();
			}
		}
OutputStream output = response.getOutputStream();
ObjectOutputStream oos = new ObjectOutputStream( output );
session.removeAttribute(gov.sir.core.web.acciones.comun.AWImpresion.CARGAR_CONFIGURACION_ACTUAL);
oos.writeObject(configuracion);
oos.flush();
oos.close();
%>