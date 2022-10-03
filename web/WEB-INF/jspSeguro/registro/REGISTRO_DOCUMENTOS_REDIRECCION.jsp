<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles"%>
<%@page import="org.auriga.core.modelo.transferObjects.Rol"%>

<%
	Rol rolObj = (Rol) session.getAttribute(WebKeys.ROL);
        String rol = "";
        String redireccion = "";

        rol = rolObj == null ? "" : rolObj.getRolId();

        if (rol.equalsIgnoreCase(CRoles.LIQUIDADOR_REGISTRO)) {
            redireccion = "turno.registro.liquidador.view";
        } else if (rol.equalsIgnoreCase(CRoles.CAJERO_REGISTRO)) {
            redireccion = "turno.registro.cajero.radicar.view";
        } else if (rol.equalsIgnoreCase(CRoles.CAJERO)) {
            redireccion = "turno.registro.cajero.view";
            session.setAttribute("PAGO_REGISTRO_LIQUIDACION", new Boolean(true));
        }

%>

<form id="redirect" method="post" action="<%=redireccion%>" name="redirect">
	<script type="text/javascript">
		document.redirect.submit();
	</script>
</form>


