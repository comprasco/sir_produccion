<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page	import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page	import="java.util.Calendar"%>
<%@page	import="gov.sir.core.web.WebKeys"%>
<%@page	import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page	import="gov.sir.core.negocio.modelo.Proceso"%>

<%TextHelper textHelper = new TextHelper();
            %>
<script>
function cambiarAccion(text,anio,circulo,proceso,sec) {
	   document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+
	   		document.REGISTRO.AUX.value;
       document.REGISTRO.ACCION.value = text;
       document.REGISTRO.submit();
}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="29">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="7" height="29"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cargar Solicitud </td>
          <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
          <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
  </tr>
  <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif"></td>
    <!----aqui se coloca la form---->
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><form action="turnoLiquidacionRegistro.do" method="post"
			name="REGISTRO" id="REGISTRO">
        <input type="hidden" name="ACCION"
			id="ACCION" value="LIQUIDAR_REGISTRO" />
        <!----Inicio Seccion Nro. solicitud ---->
        <table width="100%" class="camposform">
          <tr>
            <td width="20"><img
					src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
					width="20" height="15"></td>
            <td width="12%">N&uacute;mero Solicitud</td>
            <td width="60%">
            <%Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            String sYear=String.valueOf(year);
            
            Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);
            Proceso proceso=(Proceso)session.getAttribute(WebKeys.PROCESO);
            
            String sCirculo=circulo.getIdCirculo();
            long lProceso=proceso.getIdProceso();
            String sProceso=String.valueOf(lProceso);
            %>

			L-<%=sYear%>-<%=sCirculo%>-<%=sProceso%>-
           
            <%try {
                textHelper.setNombre("AUX");
                textHelper.setCssClase("camposformtext");
                textHelper.setId("AUX");
                textHelper.setSize("15");
                textHelper.setTipo("text");
                textHelper.render(request, out);
                
                textHelper.setNombre(AWLiquidacionRegistro.ID_SOLICITUD);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(AWLiquidacionRegistro.ID_SOLICITUD);
                textHelper.setSize("15");
                textHelper.setTipo("hidden");
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
            <td width="50%">&nbsp;</td>
            <td width="15%"><a
					href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.BUSCAR_SOLICITUD_PAGO%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>')"><img
					src="<%=request.getContextPath()%>/jsp/images/btn_validar.gif"
					name="Siguiente" width="139" height="21" border="0" id="Siguiente"></a></td>
          </tr>
        </table>
      </form></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
  <tr>
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
  </tr>
</table>

