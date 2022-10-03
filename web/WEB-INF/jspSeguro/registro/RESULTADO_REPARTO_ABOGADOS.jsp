<%@page import="java.util.*,
                 org.auriga.core.modelo.transferObjects.Estacion,
                 gov.sir.core.negocio.modelo.*,
                 gov.sir.core.web.WebKeys"
%>
<%@page import="gov.sir.core.web.acciones.administracion.AWRelacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRepartoAbogados"%>
<%
Map mapUsuarioEstacion = (Map)session.getAttribute(WebKeys.MAPA_ABOGADO_ESTACION);
Map mapUsuarioTurnos = (Map)session.getAttribute(WebKeys.MAPA_ABOGADO_TURNO);
Map mapRelacionesUsuario = (Map)session.getAttribute(WebKeys.MAPA_ABOGADO_RELACION);
List observaciones = (List)session.getAttribute(WebKeys.LISTA_OBSERVACIONES_REPARTO_ABOGADOS);
String idProcesoReparto = (String)session.getAttribute(WebKeys.ID_PROCESO_REPARTO);
Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);

//Calculamos  el número de turnos repartidos:
long turnosRepartidos = 0;
Iterator it5 = mapUsuarioTurnos.keySet().iterator();

while(it5.hasNext()){
	List turnos = (List)mapUsuarioTurnos.get(it5.next());
	turnosRepartidos = turnosRepartidos + turnos.size();
}
%>

<link href="<%= request.getContextPath() %>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/jsp/plantillas/calendario.js">
</script>

<script>
<!--

function openwindow(nombre,valor,dimensiones)
	{
		document.getElementById('Depto').value=valor;
		document.getElementById('Mpio').value=valor+"_municipio";
		document.getElementById('Ver').value=valor+"_vereda";
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}
function juridica(nombre,valor,dimensiones)
	{
		document.getElementById('natjuridica').value=valor;
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}
	function oficinas(nombre,valor,dimensiones)
	{
		document.getElementById('tipo_oficina').value=valor+"_tipo";
		document.getElementById('numero_oficina').value=valor+"_numero";
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}
//-->
</script>

<script type="text/javascript">
	function imprimirRelacion() {
//		document.IMPRIMIR.<%= AWRelacion.ID_RELACION %>.value = text;
		document.IMPRIMIR.<%= WebKeys.ACCION %>.value = '<%= AWRelacion.IMPRIMIR_REPARTO %>';
    	document.IMPRIMIR.submit();
	}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"> <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td width="7"><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Resultados
            del Proceso de Reparto # <%= idProcesoReparto %></td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td width="11"><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
              <tr>
                
                <td>Turnos repartidos: <%= turnosRepartidos %></td>
                <td>&nbsp;</td>
              </tr>
            </table>
            <table width="100%" class="camposform">
              <tr>
                
                <td class="titulotbcentral">Observaciones del Proceso de Reparto:</td>
              </tr>
            </table>
			<form action="relacion.do" method="POST" name="IMPRIMIR" id="IMPRIMIR">
				<input  type="hidden" name="<%= WebKeys.ACCION %>">
				<input type="hidden" name="<%= CCirculo.ID_CIRCULO %>" value="<%= circulo.getIdCirculo() %>">
				<input type="hidden" name="<%= CRepartoAbogados.ID_REPARTO %>" value="<%= idProcesoReparto %>">
			</form>
              <table width="100%" class="camposform">
               <%
                if(observaciones != null){
                Iterator ito = observaciones.iterator();
                while (ito.hasNext()){
                    String obs = (String)ito.next();%>
                <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td class="campositem"><%= obs %></td>
                </tr>
               <%}}%>
              </table>
            <!-- PONER AQUI EL BUCLE //-->
            <%
            Iterator it = mapUsuarioEstacion.keySet().iterator();
            while(it.hasNext()){
                Usuario abogado = (Usuario)it.next();
                Estacion estacion = (Estacion)mapUsuarioEstacion.get(abogado);
                List turnos = (List)mapUsuarioTurnos.get(abogado);
				Relacion relacion = (Relacion)mapRelacionesUsuario.get(abogado);
                if(turnos != null) {
            %>
            <table width="100%" class="camposform">
              <tr>
               
                <td class="titulotbcentral">Abogado: <%=abogado.getUsername()%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estacion:<%= estacion.getEstacionId()%> </td>
              </tr>
			  	<%
					if(relacion != null) {
			  	%>
			  	<tr>
					
					<td class="titulotbcentral" valign="middle">
						Relacion: <%= relacion.getIdRelacion()%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</form>
					</td>
				</tr>
				<%
					}
				%>
            </table>
            <table width="100%" class="camposform">


              <tr>
                <td class="titulotbcentral">Turnos</td>
              </tr>
              <%

                Iterator it2 = turnos.iterator();
                while (it2.hasNext()){
                    Turno turno = (Turno)it2.next();%>
                <tr>
                
                
                <td class="campositem"><%= turno.getIdWorkflow()%></td>
                </tr>
               <%}%>
            </table>
            <%}}%>
            <hr class="linehorizontal"> <table width="100%" class="camposform">
              <tr>
                

	        <td class="titulotbcentral" valign="middle"><a href="javascript:imprimirRelacion()"><img  src="<%= request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0"></a>
                <a href="<%= request.getContextPath() %>/fases.view"><img border="0" src="<%= request.getContextPath() %>/jsp/images/btn_aceptar.gif" width="139" height="21"></a></td>

                <td>&nbsp;</td>
              </tr>


            </table></td>
          <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
