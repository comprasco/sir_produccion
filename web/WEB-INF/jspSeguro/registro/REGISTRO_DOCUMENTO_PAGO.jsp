<%@page import="gov.sir.core.web.*"%>
<%if (session.getAttribute(WebKeys.SOLICITUD) != null) {
	session.setAttribute("PAGO_REGISTRO_LIQUIDACION", new Boolean(true));
%>
	<jsp:include page="../comun/REGISTRO_PAGO.jsp" flush="true" />
<%}

%>
