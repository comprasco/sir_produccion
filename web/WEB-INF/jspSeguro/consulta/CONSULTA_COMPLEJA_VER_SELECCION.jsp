<%@page import=" gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="java.util.List" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta" %>
<%@page import="gov.sir.core.web.helpers.consulta.*" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>

<%
Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();

TextHelper idMatriculaHelper = new TextHelper();
ResultadoHelper resultadosHelper = new ResultadoHelper();
PaginadorHelper paginadorHelper  = new PaginadorHelper();
%>

<script language="JavaScript" type="text/JavaScript">

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
</script>

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<script type="text/javascript">
function cambiarMatricula(accion, matricula) {
	document.RESULTADO_CONSULTA.<%= WebKeys.ACCION %>.value = accion;
	document.RESULTADO_CONSULTA.<%= CFolio.NUMERO_MATRICULA_INMOBILIARIA %>.value = matricula;
}
</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
<!-- Bug 5797: se utiliza Consulta exenta o Consulta -->
<%if (solicitud.getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_EXENTO)) { %>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consulta Exenta</td>
<%} else { %>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consultas</td>
<%}%>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">

            <form action="consultas.do" method="post" name="RESULTADO_CONSULTA" id="RESULTADO_CONSULTA">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.EJECUTA_COMPLEJA %>" value="<%= AWConsulta.EJECUTA_COMPLEJA %>">
            <input type="hidden" name="<%= CFolio.NUMERO_MATRICULA_INMOBILIARIA %>" id="" value="" >

      <!-- Bug 5913: se elimina la solicitud de datos de consulta de la página de resultados -->

      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Resultados de la Consulta</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                   <td><img src="<%=request.getContextPath()%>/jsp/images/ico_resultados_busqueda.gif" width="16" height="21"></td>
	                </tr>
                </table>
                </td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table>
          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
		       <table width="100%" border="0" cellpadding="0" cellspacing="0">
		            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		            <tr>
		              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		              <td class="bgnsub">Datos del Resultado </td>
		              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resultados_busqueda.gif" width="16" height="21"></td>
		              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		            </tr>
		       </table>

<hr class="linehorizontal">
      <%
      try {
                resultadosHelper.setTipo(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
		resultadosHelper.drawGUI(request, out);
		paginadorHelper.setId(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
                // BUG 5913: redirección corregida
		paginadorHelper.setVista("consultas.compleja.seleccion.view");
		paginadorHelper.drawGUI(request, out);
		}
	  catch(HelperException re){
		 out.println("ERROR " + re.getMessage());
		}

      %>


        <%-- :: Bug 0003385 (conditional display of select item)--%>
        <%

                boolean enabled_SolicitudConsultasTipoConsultaExenta;
                enabled_SolicitudConsultasTipoConsultaExenta = false;

                // ---------------------------------------------------------------------------------
                consultaExenta_Enabled : {

                  gov.sir.core.negocio.modelo.Turno             local_Turno;
                  gov.sir.core.negocio.modelo.Solicitud         local_Solicitud;
                  gov.sir.core.negocio.modelo.SolicitudConsulta local_SolicitudConsulta;
                  final String LOCAL_ID_CONSULTATIPOEXENTO       = "5";

                  enabled_SolicitudConsultasTipoConsultaExenta = false;

                  local_Turno = (gov.sir.core.negocio.modelo.Turno)session.getAttribute( WebKeys.TURNO );
                  local_Solicitud = local_Turno.getSolicitud();

                  if( local_Solicitud instanceof gov.sir.core.negocio.modelo.SolicitudConsulta ) {
                    local_SolicitudConsulta = (gov.sir.core.negocio.modelo.SolicitudConsulta)( local_Solicitud );
                    gov.sir.core.negocio.modelo.TipoConsulta local_SolicitudConsultaTipoConsulta = local_SolicitudConsulta.getTipoConsulta();

                    if( null != local_SolicitudConsultaTipoConsulta ) {

                      if( LOCAL_ID_CONSULTATIPOEXENTO.equals( local_SolicitudConsultaTipoConsulta.getIdTipoConsulta() ) ) {

                        enabled_SolicitudConsultasTipoConsultaExenta = true;

                      } // if

                    } // if

                  } // if

                } // :consultaExenta_Enabled
                // ---------------------------------------------------------------------------------

        %>
        <%
           final String CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO = "CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO";
           final String CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED = "CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED";
        %>

        <%

        if( enabled_SolicitudConsultasTipoConsultaExenta ) {

        %>

        <%-- SOF:REGION  (CONDITIONAL)   --%>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>&nbsp;</td>

                  <td class="contenido" width="250"><div align="right">Habilitar Impresion Consulta Exenta</div> </td>
                  <td class="contenido" width="20">
                    <input
                      type="checkbox"
                      id="<%= CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO %>"
                      name="<%= CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO %>"
                      value="<%= CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED %>"
                      checked="checked"
                      />
                  </td>
                  <td class="contenido" width="20">&nbsp;</td>
                </tr>
              </table>



        <%-- EOF:REGION  (CONDITIONAL)   --%>





        <%
        } // if
        %>

        <%-- --%>

	<hr class="linehorizontal" />



	<table width="100%" border="0" cellpadding="0" cellspacing="0">
       <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
          <td class="bgnsub">Anexar m&aacute;s resultados || Aceptar</td>
          <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anexar.gif" width="16" height="21"></td>
          <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
      </table>

      <table width="100%" class="camposform">
        <tr>
          <td width="20">
          <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
          <td width="140">
            <!-- BUG 5913: redirección corregida -->
            <input name="imageField" type="image" onClick="cambiarMatricula('<%=AWConsulta.INICIA_CONSULTA_COMPLEJA%>','');" src="<%=request.getContextPath()%>/jsp/images/btn_mas_consultas.gif" width="139" height="21" border="0">
          </td>
          <td width="140">
          <input name="imageField" type="image" onClick="cambiarMatricula('<%=AWConsulta.IMPRIMIR%>','');" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0">
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </td>
    <br>
    </form>


          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
          <td>
          <img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td>
          <img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
     </table>



      </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
