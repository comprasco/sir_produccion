<%@page import="gov.sir.core.negocio.modelo.Proceso"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.core.web.helpers.correccion.PagoCanalesRecaudoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.AplicacionPago"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCorreccionCanalRecaudo"%>
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="gov.sir.core.web.helpers.comun.NotasInformativasHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%
    NotasInformativasHelper notHelper = new NotasInformativasHelper();
    session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
    String conservacionmayor = (String) session.getAttribute(AWTrasladoTurno.VALOR_CONSERVACION_MAYOR_VALOR);
    HermodService hs;
    TextHelper textHelper = new TextHelper(); 

    String datoVacio = "N/A";
    String razonPagoGral = "MAYOR VALOR";
    
    javax.servlet.ServletContext context = session.getServletContext();
    String numliquidacion = (String) session.getAttribute(WebKeys.VALOR_LIQUIDACION);
    List consignaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);
    List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
    AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);
    AplicacionPago appTimbre = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
    double valorLiquidacion = 0;
    List marcasCheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
    List marcasConsignacion = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
        if (numliquidacion != null) {
            valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
        } else {
            valorLiquidacion = 0;
        }

    PagoCanalesRecaudoHelper crHelper = new PagoCanalesRecaudoHelper(request,
                    valorLiquidacion, consignaciones, cheques, appEfectivo,
                    appTimbre,marcasConsignacion,marcasCheques, marcasCheques);
%>
<script type="text/javascript">
   
    function cambiarAccion(text) {
        document.BUSCAR.<%=WebKeys.ACCION%>.value = text;   
        document.BUSCAR.submit();
    }
    
    function verNota(nombre,valor,dimensiones,text)
    { 
        document.BUSCAR.<%=WebKeys.ACCION%>.value = text;   
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();       
    }
    function HablaConmigo(result) {
        if(result  == 'true'){
            cambiarAccion('<%= AWTrasladoTurno.CORRECCION_CANAL_RECAUDO%>');
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
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
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
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Correcci&oacute;n canal de recaudo</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_correccion_canal_recaudo.gif" width="16" height="21"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                                            <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
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
                        <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
                            <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CORRECCION_CANAL_RECAUDO%>" value="<%=AWTrasladoTurno.CORRECCION_CANAL_RECAUDO%>">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno asociado</td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_correccion_canal_recaudo.gif" width="16" height="21"></td>
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
                                    <td>&nbsp;</td>
                                    <td>Turno sobre el cual se realizará la corrección</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CTurno.ID_TURNO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTurno.ID_TURNO);
                                                textHelper.setFuncion(CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                <br>
                                <td align="right">
                                    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                        <tr>
                                            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                            <td>Agregar Turno</td>
                                            <td><a href="javascript:cambiarAccion('<%= AWTrasladoTurno.CORRECCION_CANAL_RECAUDO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                                        </tr>
                                    </table>
                                </td>
                                </tr>
                            </table>
                        </form>
                    </td>
                    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos Asociados al Pago</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_correccion_canal_recaudo.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <% 
                    List canales = (List) session.getAttribute(AWTrasladoTurno.LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS);
                    List efectivo = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO);
                    if (efectivo != null && !efectivo.isEmpty()) {
                        for (int i = 0; i < efectivo.size(); i = i+2) {
                            int p = 0;
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_efectivo" id="id_dato_lista_efectivo_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <% 
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[6].equalsIgnoreCase(DocumentoPago.PAGO_EFECTIVO) && p == 0) {
                                            p++; 

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %> 
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_EFECTIVO%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=efectivo.get(i)%></td>   

                                <td>Estado Actual</td>  
                                <%
                                if(efectivo.get(i+1).equals("0")){
                            
                                %>
                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posEfec=0','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                                 <%
                               } else if(efectivo.get(i+1).equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(efectivo.get(i+1).equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                            </tr>                            
                        </table>
                    </td>                    
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr> 
                <%
                        }
                    }
                    List convenio = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO);
                    if (convenio != null && !convenio.isEmpty()) {

                        for (int i = 0; i < convenio.size(); i++) {
                            int p = 0;
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_convenio" id="id_dato_lista_convenio_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <% 
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[6].equalsIgnoreCase(DocumentoPago.PAGO_CONVENIO) && p == 0) {
                                            p++; 

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %> 
                            <tr>
                                <td>&nbsp;</td>
                                <td>Forma de Pago</td>
                                <td class="campositem"><%=DocumentoPago.PAGO_CONVENIO%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=convenio.get(i)%></td>   

                                <td>Estado Actual</td>  
                                <%
                                if(convenio.get(i+1).equals("0")){
                            
                                %>
                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConv=0','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                               <%
                               } else if(convenio.get(i+1).equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(convenio.get(i+1).equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                             </tr> 
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }

                    List pse = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE);
                    if (pse != null && !pse.isEmpty()) {
                        String pasoPse = "";
                        for (int i = 0; i < pse.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_pagos_pse" id="id_dato_lista_pagos_pse_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoPse = String.valueOf(((List) pse.get(i)).get(1));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");

                                        if (split[7].equalsIgnoreCase(pasoPse)) {

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_ELECTRONICO_PSE%></td>
                                
                                <td width="146">Banco/Franquicia</td>
                                <td width="212" class="campositem"><%=((List) pse.get(i)).get(0)%></td>   
                            </tr> 
                            <tr>
                                <td>&nbsp;</td>
                                <td>Número Aprobación</td>
                                <td class="campositem"><%=((List) pse.get(i)).get(1)%></td>

                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) pse.get(i)).get(2)%></td> 
                                
                                  <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) pse.get(i)).get(4);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                  <td><a href="javascript:verNota('correccion.canal.recaudo.view?posPse=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                            
                                <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Liquidado</td>
                                <td class="campositem"><%=((List) pse.get(i)).get(3)%></td>   
                            </tr>
                        </table>                        
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }

                    List debito = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO);
                    if (debito != null && !debito.isEmpty()) {
                        for (int i = 0; i < debito.size(); i++) {
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_tarjeta_debito" id="id_dato_lista_tarjeta_debito_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%=((List) debito.get(i)).get(5)%></td>                                

                                <%if (((List) debito.get(i)).get(9) != null) {%> 
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=((List) debito.get(i)).get(10) + "-" + ((List) debito.get(i)).get(9)%></td>     
                                <%} else {%>
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=datoVacio%></td>
                                <%}%>
                            
                            <tr>
                                <td>&nbsp;</td>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <%if (((List) debito.get(i)).get(6) != null) {%>
                                <td width="179">Forma de Pago</td>                                
                                <td width="211" class="campositem"><%=((List) debito.get(i)).get(6) != null ? ((List) debito.get(i)).get(6) : "&nbsp;"%></td>
                                <%} else {%>
                                <td width="179">Forma de Pago</td> 
                                <td width="211" class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debito.get(i)).get(0) != null) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(0) != null ? ((List) debito.get(i)).get(0) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) debito.get(i)).get(1) != null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(1) != null ? ((List) debito.get(i)).get(1) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debito.get(i)).get(2) != null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(2) != null ? ((List) debito.get(i)).get(2) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) debito.get(i)).get(5);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTDebito=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                            
                                <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>                                
                            </tr><tr>
                                <td>&nbsp;</td>
                                <%if (((List) debito.get(i)).get(3) != null) {%>
                                <td>Fecha documento</td>                                
                                <td class="campositem"><%=((List) debito.get(i)).get(3) != null ? ((List) debito.get(i)).get(3) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Fecha documento</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debito.get(i)).get(4) != null) {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(4) != null ? ((List) debito.get(i)).get(4) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) debito.get(i)).get(7) != null) {%>
                                <td>Banco</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(7) != null ? ((List) debito.get(i)).get(7) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debito.get(i)).get(8) != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(8) != null ? ((List) debito.get(i)).get(8) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>   
                                <td>&nbsp;</td>
                                <%if (((List) debito.get(i)).get(11) != null) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=((List) debito.get(i)).get(11) != null ? ((List) debito.get(i)).get(11) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>   
                            </tr>                            
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>               
                <%
                        }
                    }
                    List credito = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO);
                    if (credito != null && !credito.isEmpty()) {

                        String pasoTc = "";
                        String pasosTc = "";
                        for (int i = 0; i < credito.size(); i++) {

                %>
                <tr>
                     <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                     <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                         <input type="hidden" name="id_dato_lista_tarjeta_credito" id="id_dato_lista_tarjeta_credito_<%=i%>" value="<%=i%>">
                         <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoTc = String.valueOf(((List) credito.get(i)).get(1));
                                pasosTc = String.valueOf(((List) credito.get(i)).get(2));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");

                                        if (split[7].equalsIgnoreCase(pasosTc)) {
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_TARJETA_CREDITO%></td>

                                <td width="146">Banco/Franquicia</td>
                                <td width="212" class="campositem"><%=((List) credito.get(i)).get(0)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) credito.get(i)).get(1)%></td>

                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) credito.get(i)).get(2)%></td>   

                                  <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) credito.get(i)).get(5);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTCredito=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                                <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>  
                             </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) credito.get(i)).get(3)%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=((List) credito.get(i)).get(4)%></td>   
                            </tr>
                         </table>
                     </td>
                     <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>                
                <%
                            
                        }
                    }
                    List cheque = (List) session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE);
                    if (cheque != null && !cheque.isEmpty()) {

                        String pasoCheque = "";
                        for (int i = 0; i < cheque.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_cheque" id="id_dato_lista_cheque_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoCheque = String.valueOf(((List) cheque.get(i)).get(1));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[7].equalsIgnoreCase(pasoCheque)) {
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_CHEQUE%></td>

                                <td width="146">Banco/Franquicia</td>
                                <td width="212"class="campositem"><%=((List) cheque.get(i)).get(0)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) cheque.get(i)).get(1)%></td>

                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) cheque.get(i)).get(2)%></td>   
                                
                                <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) cheque.get(i)).get(6);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                               <td><a href="javascript:verNota('correccion.canal.recaudo.view?posCheque=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                                 <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                              </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) cheque.get(i)).get(3)%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=((List) cheque.get(i)).get(4)%></td>   
                            </tr> 
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>                
                <%
                        }
                    }
                    List consignacion = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION);
                    if (consignacion != null && !consignacion.isEmpty()) {

                        String pasoConsig = "";
                        for (int i = 0; i < consignacion.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_consignacion" id="id_dato_lista_consignacion_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoConsig = String.valueOf(((List) consignacion.get(i)).get(1));
                                    if (!canales.isEmpty()) {
                                        for (int j = 0; j < canales.size(); j++) {
                                            String[] split = canales.get(j).toString().split("-");
                                            if (split[7].equalsIgnoreCase(pasoConsig)) {
                            %>
                             <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                            }                                                           
                                        }
                                    }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_CONSIGNACION%></td>

                                <td width="146">No. Documento Pago</td>
                                <td width="212" class="campositem"><%=((List) consignacion.get(i)).get(1)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Documento pago</td>
                                <td class="campositem"><%=((List) consignacion.get(i)).get(2)%></td>

                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) consignacion.get(i)).get(3)%></td>                                 
                                 <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) consignacion.get(i)).get(6);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                    <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConsig=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                             <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                            
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Liquidado</td>
                                <td class="campositem"><%=((List) consignacion.get(i)).get(4)%></td>   
                            </tr>
                        </table>                        
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%      }
                    }
                 
                    List general = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL);
                        if (general != null && !general.isEmpty()) {
                            for (int i = 0; i < general.size(); i++) {
                            Object docu = ((List) general.get(i)).get(13);
                            String DocumentoPago = docu.toString();
                            String cuentaDestino = "";
                        try {
                             hs = HermodService.getInstance();
                             cuentaDestino= hs.getNoCuentabyDocumentoPago(DocumentoPago);
                             
                        } catch (HermodException e) {
                            
                        }   catch (Throwable es){
                            
                        }
                            
                %>   
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_general" id="id_dato_lista_general_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">                           
                            <br>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%=((List) general.get(i)).get(5)%></td>                                

                                <%if (!cuentaDestino.equals("")) {%> 
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=cuentaDestino%></td>     
                                <%} else {%>
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) general.get(i)).get(6) != null) {%>
                                <td>Forma de Pago</td>                                
                                <td class="campositem"><%=((List) general.get(i)).get(6) != null ? ((List) general.get(i)).get(6) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Forma de Pago</td> 
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (((List) general.get(i)).get(0) != null) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=((List) general.get(i)).get(0) != null ? ((List) general.get(i)).get(0) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) general.get(i)).get(1) != null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) general.get(i)).get(1) != null ? ((List) general.get(i)).get(1) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (((List) general.get(i)).get(2) != null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) general.get(i)).get(2) != null ? ((List) general.get(i)).get(2) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                
                                
                                     <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) general.get(i)).get(12);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                               <td><a href="javascript:verNota('correccion.canal.recaudo.view?posGral=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0','LIQUIDAR')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                               <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                                                          
                                                                                         
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) general.get(i)).get(3) != null) {%>
                                <td>Fecha documento</td>                                
                                <td class="campositem"><%=((List) general.get(i)).get(3) != null ? ((List) general.get(i)).get(3) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Fecha documento</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) general.get(i)).get(4) != null) {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=((List) general.get(i)).get(4) != null ? ((List) general.get(i)).get(4) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) general.get(i)).get(7) != null) {%>
                                <td>Banco</td>
                                <td class="campositem"><%=((List) general.get(i)).get(7) != null ? ((List) general.get(i)).get(7) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) general.get(i)).get(8) != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=((List) general.get(i)).get(8) != null ? ((List) general.get(i)).get(8) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>   
                                <td>&nbsp;</td>
                                <%if (((List) general.get(i)).get(11) != null) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=((List) general.get(i)).get(11) != null ? ((List) general.get(i)).get(11) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>   
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        
                        }
                    }

                    List mayorValor = (List) session.getAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR);
                    if (mayorValor != null && !mayorValor.isEmpty()) {

                %>
                
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                     <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" class="camposform">
                          <td>Mayor Valor: </td>
                        </table>                        
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                    List efectivoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM);
                    if (efectivoMV != null && !efectivoMV.isEmpty()) {
                        for (int i = 0; i < efectivoMV.size(); i = i +2) {                      
 
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_efectivo_mv"  id="id_dato_lista_efectivo_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                int p = 0;
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[6].equalsIgnoreCase(DocumentoPago.PAGO_EFECTIVO) && p == 0) {
                                            p++;

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %> 
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Razón de Pago</td>
                                <td width="211" class="campositem"><%=razonPagoGral%></td>

                                <td width="146">Forma de pago</td>
                                <td width="212" class="campositem"><%=DocumentoPago.PAGO_EFECTIVO%></td>

                                <td>Estado Actual</td>  
                                <%
                                  Object obs = efectivoMV.get(i+1);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.view?posEfecMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                              <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                              </tr>     
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor liquidado</td>
                                <td class="campositem"><%=efectivoMV.get(i)%></td>
                            </tr>
                        </table>                        
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }

                    List convenioMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM);
                    if (convenioMV != null && !convenioMV.isEmpty()) {

                        for (int i = 0; i < convenioMV.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_convenio_mv"  id="id_dato_lista_convenio_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                int p = 0;
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[6].equalsIgnoreCase(DocumentoPago.PAGO_CONVENIO) && p == 0) {
                                            p++;

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>  
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Razón de Pago</td>
                                <td width="211" class="campositem"><%=convenioMV.get(0)%></td>

                                <td width="146">Forma de pago</td>
                                <td width="212" class="campositem"><%=DocumentoPago.PAGO_CONVENIO%></td>
                            <td>Estado Actual</td>  
                                <%
                                  Object obs = convenioMV.get(i+1);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConvMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                             <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                            </tr>     
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor liquidado</td>
                                <td class="campositem"><%=convenioMV.get(i)%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }

                    List pseMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM);
                    if (pseMV != null && !pseMV.isEmpty()) {

                        String pasoPseMv = "";
                        for (int i = 0; i < pseMV.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_pse_mv"  id="id_dato_lista_pse_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoPseMv = String.valueOf((((List) pseMV.get(i)).get(1)));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");

                                        if (split[7].equalsIgnoreCase(pasoPseMv)) {

                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_ELECTRONICO_PSE%></td>
                                
                                <td width="146">Banco/Franquicia</td>
                                <td width="212" class="campositem"><%=((List) pseMV.get(i)).get(0)%></td>   
                            </tr> 
                            <tr>
                                <td>&nbsp;</td>
                                <td>Número Aprobación</td>
                                <td class="campositem"><%=((List) pseMV.get(i)).get(1)%></td>

                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) pseMV.get(i)).get(2)%></td> 

                                 <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) pseMV.get(i)).get(4);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                   <td><a href="javascript:verNota('correccion.canal.recaudo.view?posPseMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                            <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %> 
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Liquidado</td>
                                <td class="campositem"><%=((List) pseMV.get(i)).get(3)%></td>   
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }
                    List debitoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM);
                    if (debitoMV != null && !debitoMV.isEmpty()) {
                        for (int i = 0; i < debitoMV.size(); i++) {
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_Tdebito_mv"  id="id_dato_lista_Tdebito_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">

                            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                            <td width="179">Canal de recaudo</td>
                            <td width="211" class="campositem"><%=((List) debitoMV.get(i)).get(5)%></td>                                

                            <%if (((List) debitoMV.get(i)).get(9) != null) {%> 
                            <td width="146">Cuenta Destino</td>
                            <td width="212" class="campositem"><%=((List) debitoMV.get(i)).get(10) + "-" + ((List) debitoMV.get(i)).get(9)%></td>     
                            <%} else {%>
                            <td width="146">Cuenta Destino</td>
                            <td width="212" class="campositem"><%=datoVacio%></td>
                            <%}%>

                            <tr>
                                <td>&nbsp;</td>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <%if (((List) debitoMV.get(i)).get(6) != null) {%>
                                <td width="179">Forma de Pago</td>                                
                                <td width="211" class="campositem"><%=((List) debitoMV.get(i)).get(6) != null ? ((List) debitoMV.get(i)).get(6) : "&nbsp;"%></td>
                                <%} else {%>
                                <td width="179">Forma de Pago</td> 
                                <td width="211" class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debitoMV.get(i)).get(0) != null) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(0) != null ? ((List) debitoMV.get(i)).get(0) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) debitoMV.get(i)).get(1) != null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(1) != null ? ((List) debitoMV.get(i)).get(1) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debitoMV.get(i)).get(2) != null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(2) != null ? ((List) debitoMV.get(i)).get(2) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                             <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) debitoMV.get(i)).get(5);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                             <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTDebitoMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                               <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                            </tr><tr>
                                <td>&nbsp;</td>
                                <%if (((List) debitoMV.get(i)).get(3) != null) {%>
                                <td>Fecha documento</td>                                
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(3) != null ? ((List) debitoMV.get(i)).get(3) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Fecha documento</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debitoMV.get(i)).get(4) != null) {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(4) != null ? ((List) debitoMV.get(i)).get(4) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) debitoMV.get(i)).get(7) != null) {%>
                                <td>Banco</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(7) != null ? ((List) debitoMV.get(i)).get(7) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) debitoMV.get(i)).get(8) != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(8) != null ? ((List) debitoMV.get(i)).get(8) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>   
                                <td>&nbsp;</td>
                                <%if (((List) debitoMV.get(i)).get(11) != null) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=((List) debitoMV.get(i)).get(11) != null ? ((List) debitoMV.get(i)).get(11) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>   
                            </tr>                            
                        </table>                         
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }

                    List creditoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM);
                    if (creditoMV != null && !creditoMV.isEmpty()) {

                        String pasoTcMv = "";
                        String pasosTcMv = "";
                        for (int i = 0; i < creditoMV.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_Tcredito_mv"  id="id_dato_lista_Tcredito_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoTcMv = String.valueOf((((List) creditoMV.get(i)).get(1)));
                                pasosTcMv = String.valueOf((((List) creditoMV.get(i)).get(2)));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");

                                        if (split[7].equalsIgnoreCase(pasosTcMv)) {
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_TARJETA_CREDITO%></td>

                                <td width="146">Banco/Franquicia</td>
                                <td width="212" class="campositem"><%=((List) creditoMV.get(i)).get(0)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) creditoMV.get(i)).get(1)%></td>

                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) creditoMV.get(i)).get(2)%></td>   

                                  <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) creditoMV.get(i)).get(5);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTCreditoMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                              <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                               
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) creditoMV.get(i)).get(3)%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=((List) creditoMV.get(i)).get(4)%></td>   
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }
                    List chequeMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM);
                    if (chequeMV != null && !chequeMV.isEmpty()) {

                        String pasoChequeMv = "";
                        for (int i = 0; i < chequeMV.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_cheque_mv"  id="id_dato_lista_cheque_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoChequeMv = String.valueOf((((List) chequeMV.get(i)).get(1)));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");

                                        if (split[7].equalsIgnoreCase(pasoChequeMv)) {
                            %>
                             <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_CHEQUE%></td>

                                <td width="146">Banco/Franquicia</td>
                                <td width="212"class="campositem"><%=((List) chequeMV.get(i)).get(0)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) chequeMV.get(i)).get(1)%></td>

                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) chequeMV.get(i)).get(2)%></td>   

                                  <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) chequeMV.get(i)).get(6);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posChequeMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                              <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) chequeMV.get(i)).get(3)%></td>

                                <td>Valor liquidado</td>
                                <td class="campositem"><%=((List) chequeMV.get(i)).get(4)%></td>   
                            </tr> 
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }
                    List consignacionMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM);
                    if (consignacionMV != null && !consignacionMV.isEmpty()) {

                        String pasoConsigMv = "";
                        for (int i = 0; i < consignacionMV.size(); i++) {

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_consignacion_mv"  id="id_dato_lista_consignacion_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <%
                                pasoConsigMv = String.valueOf((((List) consignacionMV.get(i)).get(1)));
                                if (!canales.isEmpty()) {
                                    for (int j = 0; j < canales.size(); j++) {
                                        String[] split = canales.get(j).toString().split("-");
                                        if (split[7].equalsIgnoreCase(pasoConsigMv)) {
                            %>
                             <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%= !split[4].equals(null) ? split[4] : "&nbsp;"%></td>                                

                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%= split[8] + "-" + split[2]%></td>
                            </tr>
                            <%
                                            }                                                           
                                        }
                                    }
                            %>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Forma de Pago</td>
                                <td width="211" class="campositem"><%=DocumentoPago.PAGO_CONSIGNACION%></td>

                                <td width="146">No. Documento Pago</td>
                                <td width="212" class="campositem"><%=((List) consignacionMV.get(i)).get(1)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Documento pago</td>
                                <td class="campositem"><%=((List) consignacionMV.get(i)).get(2)%></td>

                                <td>Fecha Documento</td>
                                <td class="campositem"><%=((List) consignacionMV.get(i)).get(3)%></td>                                 
                                   <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) consignacionMV.get(i)).get(6);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.view?=posConsigMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                              <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                           </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Liquidado</td>
                                <td class="campositem"><%=((List) consignacionMV.get(i)).get(4)%></td>   
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    }
                    List generalMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL_VM);
                    if (generalMV != null && !generalMV.isEmpty()) {
                        for (int i = 0; i < generalMV.size(); i++) {
                            Object docu = ((List) generalMV.get(i)).get(13);
                            String DocumentoPago = docu.toString();
                            String cuentaDestino = null;
                        try {
                             hs = HermodService.getInstance();
                             cuentaDestino= hs.getNoCuentabyDocumentoPago(DocumentoPago);
                             
                        } catch (HermodException e) {
                            
                        }   catch (Throwable es){
                            
                        }
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_general_mv"  id="id_dato_lista_general_mv_<%=i%>" value="<%=i%>">
                        <table width="100%" class="camposform">
                            <br>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%=((List) generalMV.get(i)).get(5)%></td>                                

                                 <%if (!cuentaDestino.equals("")) {%> 
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=cuentaDestino%></td>     
                                <%} else {%>
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) generalMV.get(i)).get(6) != null) {%>
                                <td>Forma de Pago</td>                                
                                <td class="campositem"><%=((List) generalMV.get(i)).get(6) != null ? ((List) generalMV.get(i)).get(6) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Forma de Pago</td> 
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (((List) generalMV.get(i)).get(0) != null) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(0) != null ? ((List) generalMV.get(i)).get(0) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) generalMV.get(i)).get(1) != null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(1) != null ? ((List) generalMV.get(i)).get(1) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (((List) generalMV.get(i)).get(2) != null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(2) != null ? ((List) generalMV.get(i)).get(2) : "&nbsp;"%></td>
                                <%}else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                    <td>Estado Actual</td>  
                                <%
                                  Object obs = ((List) generalMV.get(i)).get(12);
                                  String ob = obs.toString();
                                if(ob.equals("0")){
                            
                                %>
                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posGralMV=<%=i%>','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir_canal.gif" width="150" height="21" border="0"></a></td>
                              <%
                               } else if(ob.equals("1")){
                            
                                %>
                                <td class="campositem">Vencido para Correccion</td> 
                                <%
                               } else if(ob.equals("3")){
                            
                                %>
                                <td class="campositem">Corregido</td> 
                                 <%
                               }
                                %>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) generalMV.get(i)).get(3) != null) {%>
                                <td>Fecha documento</td>                                
                                <td class="campositem"><%=((List) generalMV.get(i)).get(3) != null ? ((List) generalMV.get(i)).get(3) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Fecha documento</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) generalMV.get(i)).get(4) != null) {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(4) != null ? ((List) generalMV.get(i)).get(4) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (((List) generalMV.get(i)).get(7) != null) {%>
                                <td>Banco</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(7) != null ? ((List) generalMV.get(i)).get(7) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Banco</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>

                                <%if (((List) generalMV.get(i)).get(8) != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(8) != null ? ((List) generalMV.get(i)).get(8) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                            <tr>   
                                <td>&nbsp;</td>
                                <%if (((List) generalMV.get(i)).get(11) != null) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=((List) generalMV.get(i)).get(11) != null ? ((List) generalMV.get(i)).get(11) : "&nbsp;"%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>   
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%                            
                            }
                        }
                    }
                %>
                
                <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" class="camposform">
                            <tr>
                                <form name="Volvermenu" action="seguridad.do" method="post" type="submit">
                                    <td><input name="Submit" type="submit" class="irfasesCorreccionCanales" value="."></td>
                                    <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                                    <% Proceso proceso = ((Proceso)session.getAttribute(WebKeys.PROCESO));
                                       if(proceso != null){
                                    %>
                                    <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
                                        <% 
                                       }
                                    %>

                                </form>
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>                   
                </tr>                                
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table>
            <%
                try {                  
                    notHelper.setNombreFormulario("COR_CANAL_RECAUDO");
                    notHelper.render(request, out);
                } catch (HelperException re) {
                    out.println("ERROR " + re.getMessage());
                }
            %>
        </td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>    
    <tr>
        <td>&nbsp;</td>
        <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
        <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
        <td>&nbsp;</td>
    </tr>
</table>