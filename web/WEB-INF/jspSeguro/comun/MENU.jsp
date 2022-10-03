<%@ page import ="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*" %>
<% 

   MenuHelper menuHelper = new MenuHelper();
	try {
		menuHelper.setSeQuiereLimpiarSesion(true);
		menuHelper.render(request,out);
	}
		catch(HelperException re){
		out.println("ERROR " + re.getMessage());
	}
%>  