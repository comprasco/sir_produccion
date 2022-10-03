<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.*"%>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="java.util.Date"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
Date today;
    String fechaAct;

    today = new Date();
    fechaAct = DateFormatUtil.format(today);
    
    
String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	vistaAnterior = vistaAnterior !=null 
		? "javascript:window.location.href='fases.view';" 
		: "javascript:history.back();";
		
Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
session.setAttribute(WebKeys.TURNO_DEVOLUCION, turno);
SolicitudDevolucion solicitud = (SolicitudDevolucion)turno.getSolicitud();
//Ciudadano ciudadano = solicitud.getCiudadano();
TextAreaHelper area = new TextAreaHelper();
session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/solicitud.getDescripcion());
area.setCols("90");
area.setRows("10");
NotasInformativasHelper helper = new NotasInformativasHelper();

TextAreaHelper descripcion = new TextAreaHelper();
String estadoAprobacion = solicitud.isAprobada() ? "APROBADA" : "NO APROBADA";
NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");
List consignaciones = solicitud.getConsignaciones();
List solicitantes = solicitud.getSolicitantes();

List resoluciones = (List)session.getAttribute(AWDevolucion.LISTA_RESOLUCIONES);

if(resoluciones == null){
	resoluciones = new ArrayList();
}

//DETERMINAR VALOR A PAGAR.
Turno turnoAnterior = solicitud.getTurnoAnterior();
long absValorDevolver = 0;
double valorDevolucion = 0;
if (turnoAnterior != null) {
	List liquidaciones = solicitud.getLiquidaciones();
	double valorDevolver = 0;

	if (liquidaciones != null && liquidaciones.size() > 0) {
		Iterator itLiquidaciones = liquidaciones.iterator();
		while (itLiquidaciones.hasNext()) {
			Liquidacion liq = (Liquidacion) itLiquidaciones.next();
			if (liq.getValor() < 0 ) {
				valorDevolver = liq.getValor();
			}
		}
	} 

	valorDevolucion = Math.abs(valorDevolver);
} else {
	List liquidaciones = solicitud.getLiquidaciones();
	double valorDevolver = 0;

	if (liquidaciones != null && liquidaciones.size() > 0) {
		Iterator itLiquidaciones = liquidaciones.iterator();
		while (itLiquidaciones.hasNext()) {
			Liquidacion liq = (Liquidacion) itLiquidaciones.next();
			if (liq.getPago()==null && liq.getValor() < 0 ) {
				valorDevolver = liq.getValor();
			}
		}
	} 

	valorDevolucion = Math.abs(valorDevolver);
}
absValorDevolver = (long) valorDevolucion;

// turno y solicitud asociados
 Turno     local_TurnoAnterior;
 Solicitud local_SolicitudAnterior = null;;

 local_TurnoAnterior = turno.getSolicitud().getTurnoAnterior();
 if (local_TurnoAnterior != null){
 	local_SolicitudAnterior = local_TurnoAnterior.getSolicitud();
 }
 
%>
<script>
    
    
	function mostrarFormaPago(valor){
		if(valor=='<%=CAplicacionPago.CHEQUE%>'){
			document.all.cheque1.style.display = 'block'
			document.all.cheque2.style.display = 'block'
            document.all.notaDebito1.style.display = 'none'
			document.all.notaDebito2.style.display = 'none'
			document.all.transferencia1.style.display = 'none'
			document.all.transferencia2.style.display = 'none'
			document.all.efectivo1.style.display = 'none'
			document.all.efectivo2.style.display = 'none'
		}else if(valor=='<%=CAplicacionPago.NOTA_DEBITO%>'){
			document.all.cheque1.style.display = 'none'
			document.all.cheque2.style.display = 'none'
            document.all.notaDebito1.style.display = 'block'
			document.all.notaDebito2.style.display = 'block'
			document.all.transferencia1.style.display = 'none'
			document.all.transferencia2.style.display = 'none'
			document.all.efectivo1.style.display = 'none'
			document.all.efectivo2.style.display = 'none'
		}else if(valor=='<%=CAplicacionPago.TRANSFERENCIA%>'){
			document.all.cheque1.style.display = 'none'
			document.all.cheque2.style.display = 'none'
            document.all.notaDebito1.style.display = 'none'
			document.all.notaDebito2.style.display = 'none'
			document.all.transferencia1.style.display = 'block'
			document.all.transferencia2.style.display = 'block'
			document.all.efectivo1.style.display = 'none'
			document.all.efectivo2.style.display = 'none'
		}else if(valor=='<%=CAplicacionPago.EFECTIVO%>'){
			document.all.cheque1.style.display = 'none'
			document.all.cheque2.style.display = 'none'
            document.all.notaDebito1.style.display = 'none'
			document.all.notaDebito2.style.display = 'none'
			document.all.transferencia1.style.display = 'none'
			document.all.transferencia2.style.display = 'none'
			document.all.efectivo1.style.display = 'block'
			document.all.efectivo2.style.display = 'block'
		}else if(valor=='<%=WebKeys.SIN_SELECCIONAR%>'){
			document.all.cheque1.style.display = 'none'
			document.all.cheque2.style.display = 'none'
            document.all.notaDebito1.style.display = 'none'
			document.all.notaDebito2.style.display = 'none'
			document.all.transferencia1.style.display = 'none'
			document.all.transferencia2.style.display = 'none'
			document.all.efectivo1.style.display = 'none'
			document.all.efectivo2.style.display = 'none'
		}
	}

</script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Efectuar Pago</td>
                <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
            </tr>
        </table>
    </td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
                <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            </tr>
            <tr>
                <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Devoluci&oacute;n</td>
                            <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                            <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                        </tr>
                    </table>
                </td>
                <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
            </tr>
            <tr>
                <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno asociado</td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <br>

                    <table width="100%" class="camposform">
                        <tr>
                            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                            <td>Turno asociado</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>

                        <%
                            if (solicitud.getTurnoAnterior() != null && solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                                session.setAttribute(WebKeys.VISTA_VOLVER,request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));

                        %>

                        <form action="trasladoTurno.do" method="post" name="CONSULTA" id="CONSULTA">
                            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWTrasladoTurno.CONSULTAR_TURNO %>" value="<%= AWTrasladoTurno.CONSULTAR_TURNO %>">
                            <input  type="hidden" name="<%= CTurno.ID_TURNO %>" id="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow()%>" value="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow() %>">
                            <input  type="hidden" name="r1" id="r1" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" >

                          <!--<tr>-->
                            <td>&nbsp;</td>
                            <td>Turno con el que ingresó el documento o certificado a registro:</td>
                            <td class="campositem"><%=((Turno)solicitud.getTurnoAnterior()).getIdWorkflow()%></td>
                            <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0"></td>
                          <!--</tr>-->
                        </form>

                      <%
                      } else {
                      %>
                            <td>&nbsp;</td>
                            <td>Turno con el que ingresó el documento o certificado a registro:</td>
                            <td class="campositem">&nbsp;</td>
                       <%
                      }
                      %>


                        </tr>
                    </table>

                    <br>
              
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Consignaciones / Cheques</td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <br>
                    <table width="100%" class="camposform">
                        <tr>
                            <td>Consignaci&oacute;n / Cheque</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2">Consignaciones o cheques sobre los cuales se solicit&oacute; la devoluci&oacute;n:</td>
                            <td>&nbsp;</td>
                        </tr>
                        <%
                            for (int i=0; i< consignaciones.size(); i++){
                                Consignacion cons = (Consignacion)consignaciones.get(i);
                                DocumentoPago docPago = cons.getDocPago();
                        %>
                        <tr>
                            <td colspan="3">
                            <br>
                                <table width="100%" class="camposform">
                                    <tr>
                                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="179">Forma de Pago</td>
                                        <td width="211" class="campositem"><%=docPago.getTipoPago() == null ? "&nbsp;" : docPago.getTipoPago()%></td>

                                        <td width="146">Valor</td>
                                        <td width="212" class="campositem"><%=format.format(docPago.getValorDocumento())%></td>

                                        <td>N&uacute;mero</td>
                                        <td class="campositem">
                                            <%if (docPago instanceof DocPagoCheque){%>
                                                <%=((DocPagoCheque)docPago).getNoCheque() == null ? "&nbsp;" : ((DocPagoCheque)docPago).getNoCheque()%>
                                            <%} else if (docPago instanceof DocPagoConsignacion){ %>
                                                    <%=((DocPagoConsignacion)docPago).getNoConsignacion()  == null ? "&nbsp;" : ((DocPagoConsignacion)docPago).getNoConsignacion()%>
                                            <%}%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>Saldo a favor</td>
                                        <td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
                                                    <%=format.format(((DocPagoCheque)docPago).getSaldoDocumento())%>
                                        <%} else if (docPago instanceof DocPagoConsignacion){ %>
                                                    <%=format.format(((DocPagoConsignacion)docPago).getSaldoDocumento())%>
                                        <%}%>
                                        </td>
                                        <td>Banco</td>
                                        <td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
                                                    <%=((DocPagoCheque)docPago).getBanco().getNombre()%>
                                        <%} else if (docPago instanceof DocPagoConsignacion){ %>
                                                    <%=((DocPagoConsignacion)docPago).getBanco().getNombre()%>
                                        <%}%>
                                        </td>

                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%}%>
                    </table>

                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td class="bgnsub">Datos de la Solicitud</td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <br>
                    <table width="100%" class="camposform">
                        <tr>
                            <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                            <td class="contenido">Descripci&oacute;n</td>
                        </tr>
                        <tr>
                            <td height="18">&nbsp;</td>
                            <td>
                            <%  try {
                                    area.setNombre(CTurno.DESCRIPCION);
                                    area.setCssClase("campositem");
                                    area.setId(CTurno.DESCRIPCION);
                                    area.setReadOnly(true);
                                    area.render(request,out);
                                }
								catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }
                            %>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <hr class="linehorizontal">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitantes</td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <br>
                    <% for (int i=0; i< solicitantes.size(); i++){
                        Ciudadano ciudadano = ((Solicitante)solicitantes.get(i)).getCiudadano();
                     %>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Tipo de Identificaci&oacute;n</td>
                                <td width="211" class="campositem"><%=ciudadano.getTipoDoc() != null ? ciudadano.getTipoDoc() : "&nbsp;" %></td>
                                <td width="146">N&uacute;mero</td>
                                <td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano.getDocumento() : "&nbsp;" %></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Primer Apellido</td>
                                <td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano.getApellido1() : "&nbsp;" %></td>
                                <td>Segundo Apellido</td>
                                <td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano.getApellido2() : "&nbsp;" %></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Nombre</td>
                                <td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano.getNombre() : "&nbsp;" %></td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    <%} %>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Aprobación </td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>

                    <table width="100%" class="camposform">
                        <tr>
                            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                            <td>&iquest; Est&aacute; aprobada la solicitud ?</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>La solicitud de devoluci&oacute;n es</td>
                            <td class="campositem"><%= estadoAprobacion %></td>
                        </tr>
                    <br>
                    </table>
              
                    <%if(solicitud.isAprobada()){%>

                        <%-- + + + + + + + + + + + + + + + + + + + + + +  + + + + + + + + + +  + + + + + + --%>
                        <!-- sof:Region description="Items para crear liquidacion negativa" -->

                        <%
                            // Declarations :: -----------------------------------------------------
                            TextHelper textHelper = new TextHelper();
                        %>
                        <%--
                            region needed resources/scripts
                        --%>
                        <br>

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Valor de la devolucion</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>

                        <%
                            List liquidaciones = solicitud.getLiquidaciones();
                            LiquidacionTurnoDevolucion liq = (LiquidacionTurnoDevolucion)liquidaciones.get(liquidaciones.size()-1);

                         // ----------------------------------------------------------------------------

                        %>

                        <table width="100%" class="camposform">
                            <tr>
                                <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="15%">Valor total para devoliuci&oacute;n<br/></td>
                                <td width="1%">$</td>
                                <td class="campositem"><%=format.format(Math.abs(liq.getValor()))%></td>
                                <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                <td> &nbsp; </td>
                            </tr>
                        </table>

                        <br>

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Valores a devolver</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>

                        <%
                         boolean displayCondition;
                        %>
                        <%

                         // Solo cuando es de Registro

                         displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                         if( displayCondition )  {

                         // ----------------------------------------------------------------------------

                        %>
                        <!---yeferson 
                                    -->
                                

                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="15%">Valor a devolver por derechos</td>
                                    <td width="1%">$</td>
                                    <td width="15%">
                                      <%
                                          try {

                                            textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS );
                                            textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS );
                                            textHelper.setCssClase("campositem");
                                            textHelper.setReadonly(true);

                                            textHelper.render(request,out);
                                          }
                                          catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                          }

                                      %>
                                    </td>
                                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                    <td> &nbsp; </td>
                                </tr>
                            </table>
                            <%
                             String aux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
                             if(aux!=null && !aux.equals("") && !aux.equals("0")){
                            %>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="15%">Valor a devolver por derechos Mayor Valor</td>
                                    <td width="1%">$</td>
                                    <td width="15%">
                                      <%
                                          try {

                                            textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR );
                                            textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR );

                                            textHelper.setCssClase("campositem");
                                            textHelper.setReadonly(true);

                                            textHelper.render(request,out);
                                          }
                                          catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                          }

                                      %>
                                    </td>
                                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                    <td> &nbsp; </td>
                                </tr>
                            </table>
                            <%} %>

                        <%

                         } // :if

                        %>

                        <%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
                        <% if (local_SolicitudAnterior != null && local_SolicitudAnterior instanceof SolicitudRegistro){
                            String valorAux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);
                                if(valorAux!=null && !valorAux.trim().equals("") && !valorAux.trim().equals("0")){
                        %>
                                    <table width="100%" class="camposform">
                                    <tr>
                                        <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="15%">Valor a devolver por certificados asociados</td>
                                        <td width="1%">$</td>
                                        <td width="15%">
                                          <%

                                          try {

                                            textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS );
                                            textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS );

                                            textHelper.setCssClase("campositem");
                                            textHelper.setReadonly(true);
                                            textHelper.render(request,out);

                                          }
                                          catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                          }

                                          %>
                                        </td>
                                        <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                        <td> &nbsp; </td>
                                    </tr>
                                </table>
                        <%      } %>
                            <%
                                String aux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
                                if(aux!=null && !aux.equals("") && !aux.equals("0")){
                            %>
              
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                            <td width="15%">Valor a devolver por certificados Mayor Valor</td>
                                            <td width="1%">$</td>
                                            <td width="15%">
                                              <%

                                              try {

                                                textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR );
                                                textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR );

                                                textHelper.setCssClase("campositem");
                                                textHelper.setReadonly(true);

                                                textHelper.render(request,out);

                                              }
                                              catch(HelperException re){
                                                out.println("ERROR " + re.getMessage());
                                              }

                                              %>
                                            </td>
                                            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                            <td> &nbsp; </td>
                                        </tr>
                                    </table>
                            <%  }  %>

                        <% } %>

                        <%-- SOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>
                        <%

                     // Solo cuando es de Registro

                     displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                     if( displayCondition )  {

                     // ----------------------------------------------------------------------------

                    %>

                        <table width="100%" class="camposform">
                            <tr>
                                <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="15%">Valor a devolver por impuestos</td>
                                <td width="1%">$</td>
                                <td width="15%">
                                  <%

                                  try {

                                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS );
                                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS );

                                    textHelper.setCssClase("campositem");
                                    textHelper.setReadonly(true);
                                    textHelper.render(request,out);

                                  }
                                  catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                  }

                                  %>
                                </td>
                                <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                <td> &nbsp; </td>
                            </tr>
                        </table>
                        

                        <%

                      } // :if

                    %>
                        <%-- EOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>




                        <%-- SOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>
                        <%

                         // Solo cuando es de Certificados

                         displayCondition = local_SolicitudAnterior instanceof SolicitudConsulta || local_SolicitudAnterior instanceof SolicitudCertificado ||
                                                local_SolicitudAnterior instanceof SolicitudFotocopia;

                         if( displayCondition )  {

                         // ----------------------------------------------------------------------------



                        %>



                          <table width="100%" class="camposform">
                            <tr>
                              <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                              <td width="15%">Valor total</td>
                              <td width="1%">$</td>
                              <td width="15%">
                              <%
                                  try {

                                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );
                                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );

                                    textHelper.setCssClase("campositem");
                                    textHelper.setReadonly(true);
                                    textHelper.render(request,out);
                                  }
                                  catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                  }
                              %>
                              </td>
                              <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                              <td> &nbsp; </td>
                            </tr>
                          </table>

                          <table width="100%" class="camposform">
                            <tr>
                              <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                              <td width="15%">Saldo a Favor</td>
                              <td width="1%">$</td>
                              <td width="15%">
                              <%

                              try {

                                textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                                textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                                textHelper.setCssClase("campositem");
                                textHelper.setReadonly(true);

                                textHelper.render(request,out);

                              }
                              catch(HelperException re){
                                out.println("ERROR " + re.getMessage());
                              }

                              %>
                              </td>
                              <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                              <td> &nbsp; </td>
                            </tr>
                          </table>


                        <%

                         } // :if

                        %>
                        <%-- EOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>

                        <%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
                        <%if (local_SolicitudAnterior == null){
                            if(solicitud.getConsignaciones().size() > 0){
                        %>
                                <table width="100%" class="camposform">
                                    <tr>
                                        <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="15%">Valor a devolver por consignaciones o cheques</td>
                                        <td width="1%">$</td>
                                        <td width="15%">
                                          <%

                                          try {

                                            textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES );
                                            textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES );

                                            textHelper.setCssClase("campositem");
                                            textHelper.setReadonly(true);
                                            textHelper.render(request,out);

                                          }
                                          catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                          }

                                          %>
                                        </td>
                                        <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                                        <td> &nbsp; </td>
                                    </tr>
                                </table>
                        <%
                            }
                          }
                         %>
 
                    <%
                    }
                    %>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                          <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                          <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Resoluciones </td>
                          <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                          <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                          <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>

                    </table>
                    <%if (resoluciones != null){ %>
                        <br>
                        <table width="100%" class="camposform">
                        <%for (int i=0; i< resoluciones.size(); i++){
                            Oficio of = (Oficio)resoluciones.get(i);%>
                            <tr>
                                <td>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                            <td width="179">N&uacute;mero</td>
                                            <td width="211" class="campositem"><%=of.getNumero() == null ? "&nbsp;" : of.getNumero()%></td>
                                            <td width="146">Fecha</td>
                                            <td width="200" class="campositem">
                        <%
                            Date fechaOficio = of.getFechaCreacion();
                            String fechaDisplay = DateFormatUtil.format(fechaOficio);
                        %>
                                                <%=fechaDisplay%>
                                            </td>
    				
                            <%if(of.getFechaFirma()!=null){ %>
                                            <td width="146">Fecha Ejecutor&iacute;a</td>
                                            <td width="200" class="campositem">
                            <%
                                    Date fechaEjecutoria = of.getFechaFirma();
                                    String fechaEjecutoriaDisplay = DateFormatUtil.format(fechaEjecutoria);
                            %>
                                                <%=fechaEjecutoriaDisplay%>
                                            </td>
                            <%}else{ %>
                                            <td width="146">&nbsp;</td>
                                            <td width="200">&nbsp;</td>
                            <%} %>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        <% } %>
                            <tr>
                                <td>
                                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                    </table>
                                </td>
                            </tr>
                        </table>
                    <%}%>

                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>
                            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de Recursos del Turno</td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <br>
            
                    <% //Obtener los recursos del turno.
                        List listaTodosRecursos = new ArrayList();
                        if (turno != null)
                        {
                           List listaHistorials = turno.getHistorials();
                           if (listaHistorials != null)
                           {
                               for (int k=0; k<listaHistorials.size(); k++)
                               {
                                   TurnoHistoria turnoH = (TurnoHistoria)listaHistorials.get(k);

                                   if (turnoH != null)
                                   {
                                      List listaRecTurno = turnoH.getRecursos();

                                      if (listaRecTurno != null)
                                      {
                                        for (int t=0; t<listaRecTurno.size(); t++)
                                        {
                                            Recurso rec = (Recurso)listaRecTurno.get(t);
                                            if (rec != null)
                                            {
                                               listaTodosRecursos.add(rec);
                                            }
                                        }
                                      }
                                   }
                                }
                           }
                        }
                     %>
            
                    <%
                        for (int s= 0; s<listaTodosRecursos.size();s++)
                        {
               
                            Recurso auxRec = (Recurso)listaTodosRecursos.get(s);
                            if (auxRec != null)
                            {
                    %>

                                <br>
                                <table width="100%" class="camposform">
                                    <tr>
                
                                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="25%">Tipo de Recurso</td>
                                        <td class="campositem"><%=auxRec.getTitulo()%></td>
                                        <td width="40%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="25%">Fecha Recurso</td>
                    <%
                                String fechaFormateada ="";
                                if (auxRec.getFecha() != null)
                                {
                                    fechaFormateada = FechaConFormato.formatear( auxRec.getFecha(), "dd/MM/yyyy");
                                }
                    %>
                                        <td width="200" class="campositem"><%=fechaFormateada%></td>
                                        <td width="40%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="25%">Usuario Recurso</td>
                                        <td width="200" class="campositem"><%=auxRec.getTextoUsuario()%></td>
                                        <td width="40%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                        <td width="25%">Descripción Recurso</td>
                                        <td>
                    <%
                                session.setAttribute("DESCRIPCION_"+s,auxRec.getTextoRecurso());
                                try {
                                    descripcion.setNombre("DESCRIPCION_"+s);
                                    descripcion.setCssClase("campositem");
                                    descripcion.setId("DESCRIPCION_"+s);
                                    descripcion.setCols("70");
                                    descripcion.setRows("4");
                                    descripcion.setReadOnly(true);
                                    descripcion.render(request,out);
								
                                }catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }
                    %>
                                        </td>
                                        <td width="40%">&nbsp;</td>
                                    </tr>
                                </table>
              
                    <%
                            }
                        }
                    %>

                    <hr class="linehorizontal" />

                </td>
                <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
            </tr>
            <tr>
              <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
              <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
              <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
            </tr>
        </table>
	</td>
	</tr>
    <tr>
        <td>&nbsp;</td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02">
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Forma de pago</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
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
                    <form name="ENTREGA" method="post" action="devolucion.do">
                        <br>
                        <table width="100%" class="camposform">
                            <tr>
                                <td>&nbsp;</td>
                                <td width="200">Seleccione una opción de pago: </td>
                                <td>
                                    <%
                                        try {
                                            ListaElementoHelper formaPagoHelper = new ListaElementoHelper();
                                            List elementosLista = new ArrayList();

                                            elementosLista.add(new ElementoLista( CAplicacionPago.CHEQUE, CAplicacionPago.CHEQUE ) );
                                            elementosLista.add(new ElementoLista( CAplicacionPago.EFECTIVO, CAplicacionPago.EFECTIVO ) );
                                            elementosLista.add(new ElementoLista( CAplicacionPago.NOTA_DEBITO, CAplicacionPago.NOTA_DEBITO ) );
                                            elementosLista.add(new ElementoLista( CAplicacionPago.TRANSFERENCIA, CAplicacionPago.TRANSFERENCIA ) );

                                            formaPagoHelper.setTipos(elementosLista);
                                            formaPagoHelper.setNombre(AWDevolucion.FORMA_PAGO);
                                            formaPagoHelper.setId(AWDevolucion.FORMA_PAGO);
                                            formaPagoHelper.setCssClase("camposformtext");
                                            formaPagoHelper.setShowInstruccion(true);
                                            formaPagoHelper.setFuncion("onchange='javascript:mostrarFormaPago(this.value);'");
                                            formaPagoHelper.render(request,out);
                                        }catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                                <!--</td>-->
                            </tr>
                        </table>
                        <hr class="linehorizontal">

                        <!-- Inicio tabla Nota Debito -->
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="notaDebito1" style='display:none'>
                            <tr>
                              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                              <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Nota débito </td>
                              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <table width="100%" class="camposform" id="notaDebito2" style='display:none'>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Introducir datos nota débito</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>N&uacute;mero:</td>
                                <td>
                                    <%
                                        try {
                                            TextHelper textHelper = new TextHelper();
                                            textHelper.setNombre(AWDevolucion.NOTA_DEBITO_NUMERO);
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setSize("25");
                                            textHelper.setId(AWDevolucion.NOTA_DEBITO_NUMERO);
                                            textHelper.render(request,out);
                                        }catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                                <td width="146">Fecha</td>
                                <td width="200">
                                <%
                                    try {
                                        session.setAttribute(AWDevolucion.FECHA_NOTA_DEBITO, "" );

                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setId(AWDevolucion.FECHA_NOTA_DEBITO);
                                        textHelper.setNombre(AWDevolucion.FECHA_NOTA_DEBITO);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.render(request,out);
                                    }catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                    }

                                %>
                                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_NOTA_DEBITO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onclick="javascript:Valores('<%=request.getContextPath()%>/')"></a>

                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Código Banco:</td>
                                <td width="211">
                                <%
                                    try {
                                        List bancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
                                        ListaElementoHelper bancosHelper = new ListaElementoHelper();

                                        if(bancos == null){
                                            bancos = new ArrayList();
                                        }

                                        List elemList = new ArrayList();
                                        ListIterator iter  = bancos.listIterator();

                                        while (iter.hasNext()){
                                          Banco banco = ((Banco)iter.next());
                                          //elemList.add(new ElementoLista( banco.getNombre(), banco.getNombre() ) ); //provisional
                                          elemList.add(new ElementoLista( banco.getIdBanco(), banco.getNombre() ) );
                                        }


                                        bancosHelper.setTipos(elemList);
                                        bancosHelper.setNombre(AWDevolucion.NOTA_DEBITO_BANCO);
                                        bancosHelper.setCssClase("camposformtext");
                                        bancosHelper.setId(AWDevolucion.NOTA_DEBITO_BANCO);
                                        bancosHelper.render(request,out);
                                    }
                                        catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td>Valor:</td>
                                <td>
                                <%
                                    String valorNotaDebito = (String)session.getAttribute(AWDevolucion.NOTA_DEBITO_VALOR);
                                    if(valorNotaDebito == null){
                                        session.setAttribute(AWDevolucion.NOTA_DEBITO_VALOR,""+format.format(absValorDevolver));
                                    }
                                    try {
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre(AWDevolucion.NOTA_DEBITO_VALOR);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setSize("25");
                                        textHelper.setId(AWDevolucion.NOTA_DEBITO_VALOR);
                                        textHelper.setReadonly(true);
                                        textHelper.render(request,out);
                                    }catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                            </tr>
                        </table>
                        <!-- Fin tabla Nota Debito -->
                             
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="cheque1" style='display:none'>
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Cheque </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <table width="100%" class="camposform" id="cheque2" style='display:none'>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Introducir datos cheque</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>N&uacute;mero del cheque:</td>
                                <td>
                                <%
                                    try {
                                      TextHelper textHelper = new TextHelper();
                                      textHelper.setNombre(AWDevolucion.CHEQUE_NUMERO);
                                      textHelper.setCssClase("camposformtext");
                                      textHelper.setSize("25");
                                      textHelper.setId(AWDevolucion.CHEQUE_NUMERO);
                                      textHelper.render(request,out);
                                    }catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td width="146">Fecha</td>
                                <td width="200">
                                <%
                                    try {
                                        session.setAttribute(AWDevolucion.FECHA_CHEQUE, "" );

                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setId(AWDevolucion.FECHA_CHEQUE);
                                        textHelper.setNombre(AWDevolucion.FECHA_CHEQUE);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.render(request,out);
                                    }catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }

                                %>

                                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_CHEQUE%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>

                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Código Banco:</td>
                                <td width="211">
                                <%
                                    try {
                                        List bancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
                                        ListaElementoHelper bancosHelper = new ListaElementoHelper();

                                        if(bancos == null){
                                            bancos = new ArrayList();
                                        }

                                        List elemList = new ArrayList();
                                        ListIterator iter  = bancos.listIterator();

                                        while (iter.hasNext()){
                                          Banco banco = ((Banco)iter.next());
                                          //elemList.add(new ElementoLista( banco.getNombre(), banco.getNombre() ) ); //provisional
                                          elemList.add(new ElementoLista( banco.getIdBanco(), banco.getNombre() ) );
                                        }


                                        bancosHelper.setTipos(elemList);
                                        bancosHelper.setNombre(AWDevolucion.CHEQUE_BANCO);
                                        bancosHelper.setCssClase("camposformtext");
                                        bancosHelper.setId(AWDevolucion.CHEQUE_BANCO);
                                        bancosHelper.render(request,out);
                                    }
                                        catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td>Valor del cheque:</td>
                                <td>
                                <%
                                String valorCheque = (String)session.getAttribute(AWDevolucion.CHEQUE_VALOR);
                                if(valorCheque == null){
                                    session.setAttribute(AWDevolucion.CHEQUE_VALOR,""+format.format(absValorDevolver));
                                }
                                try {

                                      TextHelper textHelper = new TextHelper();
                                      textHelper.setNombre(AWDevolucion.CHEQUE_VALOR);
                                      textHelper.setCssClase("camposformtext");
                                      textHelper.setSize("25");
                                      textHelper.setId(AWDevolucion.CHEQUE_VALOR);
                                      textHelper.setReadonly(true);
                                      textHelper.render(request,out);
                                }catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }
                                %>
                                </td>
                            </tr>
                        </table>
              
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="transferencia1" style='display:none'>
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                            <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Abono a Cuenta </td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                       </table>
                        <table width="100%" class="camposform" id="transferencia2" style='display:none'>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Introducir datos Abono a Cuenta</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Código Banco:</td>
                                <td width="211">
                                <% try {
                                    List bancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
                                    ListaElementoHelper bancosHelper = new ListaElementoHelper();

                                    if(bancos == null){
                                        bancos = new ArrayList();
                                    }

                                        List elemList = new ArrayList();
                                        ListIterator iter  = bancos.listIterator();

                                        while (iter.hasNext()){
                                          Banco banco = ((Banco)iter.next());
                                          //elemList.add(new ElementoLista( banco.getNombre(), banco.getNombre() ) ); //provisional
                                          elemList.add(new ElementoLista( banco.getIdBanco(), banco.getNombre() ) );
                                        }


                                        bancosHelper.setTipos(elemList);
                                        bancosHelper.setNombre(AWDevolucion.TRANSFERENCIA_BANCO);
                                        bancosHelper.setCssClase("camposformtext");
                                        bancosHelper.setId(AWDevolucion.TRANSFERENCIA_BANCO);
                                        bancosHelper.render(request,out);
                                    }
                                        catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td width="146">Titular</td>
                                <td width="200">
                                <%

                                try {

                                    TextHelper textHelper = new TextHelper();
                                    textHelper.setId(AWDevolucion.TITULAR_TRANSFERENCIA);
                                    textHelper.setNombre(AWDevolucion.TITULAR_TRANSFERENCIA);
                                    textHelper.setCssClase("camposformtext");
                                    textHelper.render(request,out);
                                }
                                catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }

                                %>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>N&uacute;mero de cuenta:</td>
                                <td>
                                <% try {

                                      TextHelper textHelper = new TextHelper();
                                            textHelper.setNombre(AWDevolucion.CUENTA_NUMERO);
                                            textHelper.setCssClase("camposformtext");
                                                            textHelper.setSize("25");
                                            textHelper.setId(AWDevolucion.CUENTA_NUMERO);
                                            textHelper.render(request,out);
                                        }
                                            catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                %>
                                </td>
                                <td>Valor:</td>
                                <td>
                                <%
                                String valorTrans = (String)session.getAttribute(AWDevolucion.TRANSFERENCIA_VALOR);
                                if(valorTrans == null){
                                    session.setAttribute(AWDevolucion.TRANSFERENCIA_VALOR,""+format.format(absValorDevolver));
                                }
                                try {

                                      TextHelper textHelper = new TextHelper();
                                            textHelper.setNombre(AWDevolucion.TRANSFERENCIA_VALOR);
                                            textHelper.setCssClase("camposformtext");
                                                            textHelper.setSize("25");
                                            textHelper.setId(AWDevolucion.TRANSFERENCIA_VALOR);
                                            textHelper.setReadonly(true);
                                            textHelper.render(request,out);
                                        }
                                            catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                %>
                                </td>
                            </tr>
                        </table>
              
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="efectivo1" style='display:none'>
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Efectivo </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
              
                        <table width="100%" class="camposform" id="efectivo2" style='display:none'>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Introducir datos Efectivo</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td width="120">Valor Devoluci&oacute;n: </td>
                                <!--<td>-->
                                <td>
                                <%
                                String valorefectivo = (String)session.getAttribute(AWDevolucion.EFECTIVO_REGRESADO);
                                if(valorefectivo == null){
                                    session.setAttribute(AWDevolucion.EFECTIVO_REGRESADO,""+format.format(absValorDevolver));
                                }

                                try {

                                      TextHelper textHelper = new TextHelper();
                                      textHelper.setNombre(AWDevolucion.EFECTIVO_REGRESADO);
                                      textHelper.setCssClase("camposformtext");
                                      textHelper.setSize("25");
                                      textHelper.setId(AWDevolucion.EFECTIVO_REGRESADO);
                                      textHelper.setReadonly(true);
                                      textHelper.render(request,out);
                                }
                                catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                }
                                %>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                                <!--</td>-->
                            </tr>
                        </table>
                          <script>
                              if(document.all.<%=AWDevolucion.FORMA_PAGO%>){
                                var tipoFormaPago = document.all.<%=AWDevolucion.FORMA_PAGO%>.options[document.all.<%=AWDevolucion.FORMA_PAGO%>.selectedIndex].value;
                                if(tipoFormaPago != "SIN_SELECCIONAR"){
                                    mostrarFormaPago(tipoFormaPago);
                                }
                            }
                          </script>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Orden de Pago</td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                       </table>
              
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">N&uacute;mero</td>
                                <td width="211">
                                  <%
                                    try {
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre(AWDevolucion.NUMERO_ORDEN);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setId(AWDevolucion.NUMERO_ORDEN);
                                        textHelper.render(request,out);
                                     }catch(HelperException re){
                                         out.println("ERROR " + re.getMessage());
                                     }
                                    %>
                                </td>
                                <td width="146">Fecha</td>
                                <td width="200">
                                <%
                                    try {
                                        session.setAttribute(AWDevolucion.FECHA_ORDEN, "" );
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setId(AWDevolucion.FECHA_ORDEN);
                                        textHelper.setNombre(AWDevolucion.FECHA_ORDEN);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.render(request,out);
                                    }catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }

                                %>

                                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_ORDEN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                </td>
                            </tr>
                        </table>

              
                        <hr class="linehorizontal">
              
                        <table width="100%" class="camposform">
                            <tr>
                            <form name="ENTREGA" method="post" action="devolucion.do">
                                <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.PAGO_CONFIRMAR%>">
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                <td width="140"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_regpago.gif" width="139" height="21" border="0"></td>
                            </form>
                            <form name="ENTREGA" method="post" action="devolucion.do">
                                <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.DEVOLVER_RESOLUCION%>">
                                <td width="170"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_devolver_resolucion.gif" width="170" height="21" border="0"></td>
                            </form>
                            <form name="ENTREGA" method="post" action="devolucion.do">
                                <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.DEVOLVER_RECURSOS%>">
                                <td width="170"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_devolver_recursos.gif" width="170" height="21" border="0"></td>
                            </form>
                            <form name="logOut2" action="seguridad.do" method="post">
                                <td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cerrar.gif" width="150" height="21" border="0" >
                                    <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO"">
                                <% Proceso proceso = ((Proceso)session.getAttribute(WebKeys.PROCESO));
                                    if(proceso != null){
                                %>
                                        <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
                                <%
                                    }
                                %>
                                </td>
                                <td>&nbsp;</td>
                            </form>
                        </tr>
                    </table>
                    </td>
                    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table>

            <% try {
                        helper.render(request,out);
                    }
                        catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                    }
            %>
        </td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
        <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
        <td>&nbsp;</td>
    </tr>
</table>
