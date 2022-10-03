<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEje" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.hermod.dao.impl.jdogenie.*" %>
<%@page import="gov.sir.core.negocio.modelo.jdogenie.*" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="javax.jdo.JDOException" %>
<%@page import="javax.jdo.PersistenceManager" %>
<%


JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
String mensaje = "Turno creado";

String idWorkflow = (String) request.getParameter("turno");
try{
	turnosDAO.crearWF(idWorkflow);
}
catch (Throwable t){
	t.printStackTrace();
	mensaje = t.getMessage();
}
%>
actual 1
RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:RESULTADO:
<%=mensaje%>