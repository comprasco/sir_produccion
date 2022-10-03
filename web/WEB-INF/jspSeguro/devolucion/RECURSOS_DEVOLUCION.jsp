<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="java.util.Date"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gov.sir.core.util.DateFormatUtil;"%>

<%

    Date today;
    String fechaAct;
    today = new Date();
    fechaAct = DateFormatUtil.format(today);

    String vistaAnterior = (String) session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
    vistaAnterior = vistaAnterior != null
            ? "javascript:window.location.href='fases.view';"
            : "javascript:history.back();";

    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    String tipoChecked = (String) session.getAttribute(AWDevolucion.CHECK_RECURSOS);
    session.setAttribute(WebKeys.TURNO_DEVOLUCION, turno);
    SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
//Ciudadano ciudadano = solicitud.getCiudadano();
    List consignaciones = solicitud.getConsignaciones();
    List solicitantes = solicitud.getSolicitantes();

    TextAreaHelper area = new TextAreaHelper();
    TextAreaHelper descripcion = new TextAreaHelper();
    session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/ solicitud.getDescripcion());
    area.setCols("90");
    area.setRows("10");
    NotasInformativasHelper helper = new NotasInformativasHelper();

    NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");

    String estadoAprobacion = solicitud.isAprobada() ? "APROBADA" : "NO APROBADA";

// turno y solicitud asociados
    Turno local_TurnoAnterior;
    Solicitud local_SolicitudAnterior = null;
    String certAsociado = "0";
    String proceso = "0";
    
    List resolucionesA = null;
        try{
            HermodService hs = HermodService.getInstance();
            TurnoPk tid = new TurnoPk();
            tid.anio = turno.getAnio();
            tid.idCirculo = turno.getIdCirculo();
            tid.idProceso = turno.getIdProceso();
            tid.idTurno = turno.getIdTurno(); 
            resolucionesA = hs.getOficiosTurno(tid); 
            
        } catch(HermodException he){
            System.out.println("ERROR: " + he);
        }

    local_TurnoAnterior = turno.getSolicitud().getTurnoAnterior();
    if (local_TurnoAnterior != null) {
        local_SolicitudAnterior = local_TurnoAnterior.getSolicitud();
        proceso = Long.toString(local_TurnoAnterior.getIdProceso());
        if (local_TurnoAnterior.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO) && local_SolicitudAnterior.getSolicitudesHijas().size() > 0) {
            certAsociado = "1";
        }
    }

%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">

    function cambiarAccion(accion) {
        document.RESOLUCION.<%=WebKeys.ACCION%>.value = accion;
        document.RESOLUCION.submit();
    }

    function cambiarAccion1(accion) {
        document.AGREGAR.<%=WebKeys.ACCION%>.value = accion;
        document.AGREGAR.submit();
    }

    function ampliarRecurso(accion, pos) {
        document.RECURSOS.POSICION.value = pos;
        document.RECURSOS.<%=WebKeys.ACCION%>.value = accion;
        document.RECURSOS.submit()
    }


</script>

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
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Interposición de Recursos PROCESO DE DEVOLUCIONES</td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                </tr>
            </table></td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Interposición Recursos Devolución</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <br>
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

                                <% if (solicitud.getTurnoAnterior() != null
                                            && solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                                        session.setAttribute(WebKeys.VISTA_VOLVER, request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));

                                %>

                            <form action="trasladoTurno.do" method="post" name="CONSULTA" id="CONSULTA">
                                <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%= AWTrasladoTurno.CONSULTAR_TURNO%>">
                                <input  type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= ((Turno) solicitud.getTurnoAnterior()).getIdWorkflow()%>" value="<%= ((Turno) solicitud.getTurnoAnterior()).getIdWorkflow()%>">
                                <input  type="hidden" name="r1" id="r1" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" >

                                <tr>
                                    <td>&nbsp;</td>
                                    <td>Turno con el que ingresó el documento o certificado a registro:</td>
                                    <td class="campositem"><%=((Turno) solicitud.getTurnoAnterior()).getIdWorkflow()%></td>
                                    <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0">
                                    </td>
                                </tr>
                            </form>

                            <%
                            } else {
                            %>
                            <td>&nbsp;</td>
                            <td>Turno con el que ingresó el documento o certificado a registro:</tr>
                            <td class="campositem">&nbsp;</td>
                            <td>&nbsp;</td>
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
                    for (int i = 0; i < consignaciones.size(); i++) {
                        Consignacion cons = (Consignacion) consignaciones.get(i);
                                        DocumentoPago docPago = cons.getDocPago();%>
                <br>
                <table width="100%" class="camposform">
                    <tr>
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td width="179">Forma de Pago</td>
                        <td width="211" class="campositem"><%=docPago.getTipoPago() == null ? "&nbsp;" : docPago.getTipoPago()%></td>

                        <td width="146">Valor</td>
                        <td width="212" class="campositem"><%=format.format(docPago.getValorDocumento())%></td>

                        <td>N&uacute;mero</td>
                        <td class="campositem"><%if (docPago instanceof DocPagoCheque) {%>
                            <%=((DocPagoCheque) docPago).getNoCheque() == null ? "&nbsp;" : ((DocPagoCheque) docPago).getNoCheque()%>
                            <%} else if (docPago instanceof DocPagoConsignacion) {%>
                            <%=((DocPagoConsignacion) docPago).getNoConsignacion() == null ? "&nbsp;" : ((DocPagoConsignacion) docPago).getNoConsignacion()%>
                            <%}%>
                        </td>		                 		
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>Saldo a favor</td>
                        <td class="campositem"><%if (docPago instanceof DocPagoCheque) {%>
                            <%=format.format(((DocPagoCheque) docPago).getSaldoDocumento())%>
                            <%} else if (docPago instanceof DocPagoConsignacion) {%>
                            <%=format.format(((DocPagoConsignacion) docPago).getSaldoDocumento())%>
                            <%}%>
                        </td>
                        <td>Banco</td>
                        <td class="campositem"><%if (docPago instanceof DocPagoCheque) {%>
                            <%=((DocPagoCheque) docPago).getBanco().getNombre()%>
                            <%} else if (docPago instanceof DocPagoConsignacion) {%>
                            <%=((DocPagoConsignacion) docPago).getBanco().getNombre()%>
                            <%}%>
                        </td>

                        <td>&nbsp;</td>
                        <td>&nbsp;</td>

                    </tr>
                </table>
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
                        <% try {
                                area.setNombre(CTurno.DESCRIPCION);
                                area.setCssClase("campositem");
                                area.setId(CTurno.DESCRIPCION);
                                area.setReadOnly(true);
                                area.render(request, out);
                            } catch (HelperException re) {
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
            <% for (int i = 0; i < solicitantes.size(); i++) {
                      Ciudadano ciudadano = ((Solicitante) solicitantes.get(i)).getCiudadano();%>
            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="179">Tipo de Identificaci&oacute;n</td>
                    <td width="211" class="campositem"><%=ciudadano.getTipoDoc() != null ? ciudadano.getTipoDoc() : "&nbsp;"%></td>
                    <td width="146">N&uacute;mero</td>
                    <td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano.getDocumento() : "&nbsp;"%></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Primer Apellido</td>
                    <td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano.getApellido1() : "&nbsp;"%></td>
                    <td>Segundo Apellido</td>
                    <td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano.getApellido2() : "&nbsp;"%></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Nombre</td>
                    <td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano.getNombre() : "&nbsp;"%></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            <%}%>
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
            <br>
            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>&iquest; Est&aacute; aprobada la solicitud ?</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>La solicitud de devoluci&oacute;n es</td>
                    <td class="campositem"><%= estadoAprobacion%></td>
                    </td>
                </tr>
    </tr>
    <br>
</table>




<hr class="linehorizontal" />


<form name="AGREGAR" method="post" action="devolucion.do">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Agregar Información Recursos</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>
            <td>Recursos</td>
            <td>
                <%
                    String checkedSi = "", checkedNo = "";
                    if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                          checkedSi = "CHECKED";%>

                <%} else {
                        checkedNo = "CHECKED";%>

                <%}
                %>
                <input type="radio" name="<%= AWDevolucion.CHECK_RECURSOS%>" value="<%= AWDevolucion.CHECKED_SI%>" <%= checkedSi%> onClick="cambiarAccion1('<%=AWDevolucion.RECURSO%>');"/> Si
                &nbsp;
                <input type="radio" name="<%= AWDevolucion.CHECK_RECURSOS%>" value="<%= AWDevolucion.CHECKED_NO%>" <%= checkedNo%> onClick="cambiarAccion1('<%=AWDevolucion.NO_RECURSO%>');"/> No
            </td>
        </tr>
        <tr>

            <td>Tipo de Recurso</td>
            <td>
                <% try {
                        TextHelper textHelper = new TextHelper();
                        textHelper.setSize("70");
                        textHelper.setNombre("TIPO_RECURSO");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.setId("TIPO_RECURSO");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>

        </tr>
        <tr>
            <td>Usuario Recurso</td>
            <td>
                <% try {
                        TextHelper textHelper = new TextHelper();
                        textHelper.setSize("70");
                        textHelper.setNombre("USUARIO_RECURSO");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }
                        textHelper.setId("USUARIO_RECURSO");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>

        </tr>

        <tr>

            <td>Observaci&oacute;n Recurso</td>
            <td>
                <% try {
                        descripcion.setNombre("DESCRIPCION_RECURSO");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            descripcion.setCssClase("camposformtext");
                        } else {
                            descripcion.setCssClase("campositem");
                            descripcion.setReadOnly(true);
                        }
                        descripcion.setCssClase("camposformtext");
                        descripcion.setId("DESCRIPCION_RECURSO");
                        descripcion.setCols("70");
                        descripcion.setRows("8");
                        descripcion.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>




            </td>

        </tr>
        <tr>
            <td width="146">Fecha de Recurso</td>
            <td width="200">
                <%
                    try {

                        session.setAttribute(AWDevolucion.FECHA_RECURSO, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_RECURSO);
                        textHelper.setNombre(AWDevolucion.FECHA_RECURSO);
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <%if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {%>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_RECURSO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                    <%}%>
            </td>
        </tr>

        <tr>

        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.AGREGAR_RECURSO%>">
        <%if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {%>
        <td width="185"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_agregar_recurso.gif" width="185" height="21" border="0"></td>
            <%}%> 
        </tr>


    </table>   
</FORM>




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

    if (turno != null) {

        List listaHistorials = turno.getHistorials();
        if (listaHistorials != null) {

            for (int k = 0; k < listaHistorials.size(); k++) {
                TurnoHistoria turnoH = (TurnoHistoria) listaHistorials.get(k);

                if (turnoH != null) {
                    List listaRecTurno = turnoH.getRecursos();

                    if (listaRecTurno != null) {
                        for (int t = 0; t < listaRecTurno.size(); t++) {
                            Recurso rec = (Recurso) listaRecTurno.get(t);
                            if (rec != null) {
                                listaTodosRecursos.add(rec);
                            }
                                    }
                                }
                            }
                        }
                    }
                }%>
<form name="RECURSOS" method="post" action="devolucion.do">
    <input type="hidden" name="POSICION" id="POSICION" />
    <% for (int s = 0; s < listaTodosRecursos.size(); s++) {

            Recurso auxRec = (Recurso) listaTodosRecursos.get(s);
            if (auxRec != null) {%>

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
            <% String fechaFormateada = "";
                if (auxRec.getFecha() != null) {
                    fechaFormateada = FechaConFormato.formatear(auxRec.getFecha(), "dd/MM/yyyy");
                    }%>
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
                <% session.setAttribute("DESCRIPCION_" + s, auxRec.getTextoRecurso());%>
                <% try {
                        TextAreaHelper descripcionRecurso = new TextAreaHelper();
                        descripcionRecurso.setNombre("DESCRIPCION_" + s);
                        descripcionRecurso.setCssClase("campositem");
                        descripcionRecurso.setId("DESCRIPCION_" + s);
                        descripcionRecurso.setCols("70");
                        descripcionRecurso.setRows("4");
                        descripcionRecurso.setReadOnly(true);
                        descripcionRecurso.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <td width="40%">&nbsp;</td>
        </tr>

        <tr>

            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td> 
            <td width="25%">Ampliación Recurso</td>
            <td>
                <% session.setAttribute("DESCRIPCION_" + s, auxRec.getTextoRecurso());%>
                <% try {
                        TextAreaHelper descripcionRecurso = new TextAreaHelper();
                        descripcionRecurso.setNombre("DESCRIPCION_AMPLIACION_" + s);
                        descripcionRecurso.setCssClase("camposformtext");
                        descripcionRecurso.setId("DESCRIPCION_AMPLIACION_" + s);
                        descripcionRecurso.setCols("70");
                        descripcionRecurso.setRows("4");
                        descripcionRecurso.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <td width="40%"><input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.EDITAR_RECURSO%>">
                <a href="javascript:ampliarRecurso('<%=AWDevolucion.EDITAR_RECURSO%>','<%=s%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_editar_on.gif" width="35" height="25" border="0"></a>
            </td>
        </tr>

    </table>   

    <%}
                 }%>
</form>   
<br>

<br>
<form name="RESOLUCION" method="post" action="devolucion.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>" value = "<%=AWDevolucion.AGREGAR_RESOLUCION_RECURSO%>">
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

    <% 
        List listaRes = null; 
       if(resolucionesA != null){ 
           listaRes = resolucionesA;
       } else{
           listaRes = (List) session.getAttribute("LISTA_RESOLUCIONES"); 
       }
        
                    if (listaRes != null) { %>
    <br>
    <table width="100%" class="camposform">
        <%for (int i = 0; i < listaRes.size(); i++) {
                        Oficio of = (Oficio) listaRes.get(i);%>
        <tr>
            <td>
                <table width="100%" class="camposform">

                    <tr>
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td><input type="checkbox" name="<%=WebKeys.TITULO_CHECKBOX_ELIMINAR%>" value="<%=i%>"></td>
                        <td width="179">N&uacute;mero</td>
                        <td width="211" class="campositem"><%=of.getNumero() == null ? "&nbsp;" : of.getNumero()%></td>

                        <td width="146">Fecha</td>
                        <td width="200" class="campositem"><%
                            Date fechaOficio = of.getFechaCreacion();
                            String fechaDisplay = DateFormatUtil.format(fechaOficio);
                            %><%=fechaDisplay%></td>

                        <%if (of.getFechaFirma() != null) { %>
                        <td width="146">Fecha Ejecutor&iacute;a</td>
                        <td width="200" class="campositem"><%
                            Date fechaEjecutoria = of.getFechaFirma();
                            String fechaEjecutoriaDisplay = DateFormatUtil.format(fechaEjecutoria);
                            %><%=fechaEjecutoriaDisplay%>
                        </td>
                        <%} else { %>
                        <td width="146">&nbsp;</td>
                        <td width="200">&nbsp;</td>
                        <%} %>


                    </tr>
                </table>
            </td>
        </tr>
        <%	} %>
        <tr>
            <td>
                <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                    <%if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {%>
                    <tr>
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                        <td>Eliminar Seleccionadas</td>
                        <td><a href="javascript:cambiarAccion('<%=AWDevolucion.ELIMINAR_EN_RECURSOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                    </tr>
                    <%} %>
                </table>
            </td>
        </tr>
    </table>
    <%}%>

    <br>
    <%if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {%>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="179">N&uacute;mero</td>
            <td width="211">
                <% try {
                        TextHelper textHelper = new TextHelper();
                        textHelper.setNombre(AWDevolucion.RESOLUCION);
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(AWDevolucion.RESOLUCION);
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <td width="146">Fecha</td>
            <td width="200">
                <%
                    try {

                        session.setAttribute(AWDevolucion.FECHA_RESOLUCION, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_RESOLUCION);
                        textHelper.setNombre(AWDevolucion.FECHA_RESOLUCION);
                        textHelper.setCssClase("camposformtext");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_RESOLUCION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
        <br>


        <td align="right">

            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                    <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td>Agregar Resoluci&oacute;n</td>
                    <td><a href="javascript:cambiarAccion('<%=AWDevolucion.AGREGAR_RESOLUCION_RECURSO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                </tr>
            </table>

        </td>

        </tr>
    </table>
    <%} %>
    <br>
</form>


<script type="text/javascript">

    function reescribirValor(valor, id) {
        var my_str = valor;
        var miles = 1;
        if (my_str.indexOf(".") == -1) {
            if (my_str.indexOf(",") == -1) {
                var nStr = "";
                for (var i = 1; i <= my_str.length; i++) {
                    var desde = my_str.length - i * 3;
                    var hasta = my_str.length - (3 * (i - 1));
                    var temp = my_str.slice(desde, hasta);
                    var separador = "";
                    if (hasta > 3) {
                        if (miles == 1) {
                            miles = 0;
                            separador = ",";
                        } else {
                            miles = 1
                            separador = ",";
                        }
                        nStr = separador + temp + nStr;
                    } else {
                        if (hasta > 0) {
                            temp = my_str.slice(0, hasta);
                            nStr = temp + nStr;
                        }
                    }
                }
                nStr = nStr + ".00";
                document.getElementById(id).value = nStr;
            }
        } else {
            var largo = my_str.indexOf(".");
            var centavos = my_str.substr(largo, my_str.length);
            if (my_str.indexOf(",") == -1) {
                var nStr = "";
                for (var i = 1; i <= largo; i++) {
                    var desde = largo - i * 3;
                    var hasta = largo - (3 * (i - 1));
                    var temp = my_str.slice(desde, hasta);
                    var separador = "";
                    if (hasta > 3) {
                        if (miles == 1) {
                            miles = 0;
                            separador = ",";
                        } else {
                            miles = 1
                            separador = ",";
                        }
                        nStr = separador + temp + nStr;
                    } else {
                        if (hasta > 0) {
                            temp = my_str.slice(0, hasta);
                            nStr = temp + nStr;
                        }
                    }
                }
                nStr = nStr + centavos;
                document.getElementById(id).value = nStr;
            }
        }
    }

</script>
<form name="ENTREGA" method="post" action="devolucion.do">
    <table class="camposform">
        <tr>
            <td width="146">Fecha Ejecutoria</td>
            <td width="200">
                <%

                    try {

                        session.setAttribute(AWDevolucion.FECHA_EJECUTORIA, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_EJECUTORIA);
                        textHelper.setNombre(AWDevolucion.FECHA_EJECUTORIA);
                        textHelper.setCssClase("camposformtext");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_EJECUTORIA%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
        </tr>
    </table>
    <%if (solicitud.isAprobada()) {%>


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
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Valor a devolver por Resoluci&oacute;n</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>

    <%

        List liquidaciones = solicitud.getLiquidaciones();
        LiquidacionTurnoDevolucion liq = (LiquidacionTurnoDevolucion) liquidaciones.get(liquidaciones.size() - 1);

        // ----------------------------------------------------------------------------

    %>

    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor total</td>
            <td width="1%">$</td>
            <td class="campositem">
                <%=format.format(Math.abs(liq.getValor()))%>
            </td>
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
    <%    // Solo cuando es de Registro
        displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

        if (displayCondition) {

            // ----------------------------------------------------------------------------

    %>

    <!---Yeferson--> 
    

    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor a devolver por derechos</td>
            <td width="1%">$</td>
            <td width="15%">
                <%
                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
            </td>
            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
            <td> &nbsp; </td>
        </tr>
    </table>
    <%String aux = (String) session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
        if (aux != null && !aux.equals("") && !aux.equals("0")) {
    %>
    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor a devolver por derechos Mayor Valor</td>
            <td width="1%">$</td>
            <td width="15%">
                <%
                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
            </td>
            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
            <td> &nbsp; </td>
        </tr>
    </table>

    <%}%>
    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Saldo a Favor</td>
            <td width="1%">$</td>
            <td width="15%">
                <%

                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
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

    <%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
    <%if (local_SolicitudAnterior != null && local_SolicitudAnterior instanceof SolicitudRegistro) {
            String valorAux = (String) session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);
            if (valorAux != null && !valorAux.trim().equals("") && !valorAux.trim().equals("0")) {%>
    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor a devolver por certificados asociados</td>
            <td width="1%">$</td>
            <td width="15%">
                <%

                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
            </td>
            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
            <td> &nbsp; </td>
        </tr>
    </table>
    <%}%>
    <%String aux = (String) session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
        if (aux != null && !aux.equals("") && !aux.equals("0")) {
    %>
    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor a devolver por certificados asociados Mayor Valor</td>
            <td width="1%">$</td>
            <td width="15%">
                <%

                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
            </td>
            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
            <td> &nbsp; </td>
        </tr>
    </table>
    <%} %>

    <% }%>

    <%-- SOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>
    <%

        // Solo cuando es de Registro
        displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

        if (displayCondition) {

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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
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
    <%    // Solo cuando es de Certificados
        displayCondition = local_SolicitudAnterior instanceof SolicitudCertificado;

        if (displayCondition) {

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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");

                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);
                    } catch (HelperException re) {
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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
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

    <%    // Solo cuando es de Fotocopia
        displayCondition = local_SolicitudAnterior instanceof SolicitudFotocopia;

        if (displayCondition) {

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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");

                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request, out);
                    } catch (HelperException re) {
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

                        TextHelper textHelperAux = new TextHelper();
                        textHelperAux.setId(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                        textHelperAux.setNombre(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);

                        textHelperAux.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelperAux.setCssClase("camposformtext");
                        } else {
                            textHelperAux.setCssClase("campositem");
                            textHelperAux.setReadonly(true);
                        }

                        textHelperAux.render(request, out);

                    } catch (HelperException re) {
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

    <%    // Solo cuando es de Cconsulta
        displayCondition = local_SolicitudAnterior instanceof SolicitudConsulta;

        if (displayCondition) {

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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");

                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request, out);
                    } catch (HelperException re) {
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

                        TextHelper textHelperAux = new TextHelper();
                        textHelperAux.setId(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                        textHelperAux.setNombre(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);

                        textHelperAux.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelperAux.setCssClase("camposformtext");
                        } else {
                            textHelperAux.setCssClase("campositem");
                            textHelperAux.setReadonly(true);
                        }

                        textHelperAux.render(request, out);

                    } catch (HelperException re) {
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

    <%-- JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno
         Certificado Masivos
    --%>
    <%    // Solo cuando es de Certificados
        displayCondition = local_SolicitudAnterior instanceof SolicitudCertificadoMasivo;

        if (displayCondition) {

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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");

                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);
                    } catch (HelperException re) {
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

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
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

    <%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
    <%if (local_SolicitudAnterior == null) {
        if (solicitud.getConsignaciones().size() > 0) {%>
    <table width="100%" class="camposform">
        <tr>
            <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="15%">Valor a devolver por consignaciones o cheques</td>
            <td width="1%">$</td>
            <td width="15%">
                <%

                    try {

                        textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES);
                        textHelper.setNombre(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES);
                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");

                        if (tipoChecked == null || tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                            textHelper.setCssClase("camposformtext");
                        } else {
                            textHelper.setCssClase("campositem");
                            textHelper.setReadonly(true);
                        }

                        textHelper.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
            </td>
            <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
            <td> &nbsp; </td>
        </tr>
    </table>
    <%}
    }%>


    <%
        }
    %>

    <hr class="linehorizontal">
    <table width="100%" class="camposform">
        <tr>

        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.ACEPTAR_INTERPOSICION_RECURSOS%>">
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
        <td width="140"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="140" height="21" border="0"></td>
</form>
<form name="ENTREGA" method="post" action="devolucion.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.NEGAR_INTERPOSICION_RECURSOS%>">
    <td width="170"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_devolver_resolucion.gif" width="170" height="21" border="0"></td>
</form>
<form name="logOut2" action="seguridad.do" method="post">
    <td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cerrar.gif" width="150" height="21" border="0" >
        <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
        <% Proceso proc = ((Proceso) session.getAttribute(WebKeys.PROCESO));
            if (proc != null) {
        %>

        <input type="hidden" name="ID_PROCESO" value="<%= proc.getIdProceso()%>">

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
        helper.render(request, out);
    } catch (HelperException re) {
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
