<%@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %> 

<%if (session.getAttribute(WebKeys.SOLICITUD) != null) {
	session.setAttribute("PAGO_REGISTRO_LIQUIDACION", new Boolean(true));
	
%>
	<jsp:include page="TURNO_MANUAL_PAGO.jsp" flush="true" />
<%}

%>
