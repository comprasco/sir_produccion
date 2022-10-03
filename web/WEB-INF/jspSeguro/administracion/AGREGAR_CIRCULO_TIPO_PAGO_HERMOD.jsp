<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.util.CirculoCanalTipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.Banco"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCuentasBancarias"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCanalesRecaudo"%>
<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoPago" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoTipoPago" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%/**
     * @autor HGOMEZ
     * @mantis 13407
     * @Requerimiento 064_453_Duplicidad_Nombre_Circulo
     * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos
     * y departamentos asociados a los mismos.
     */%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>

<%

    TextHelper textHelper = new TextHelper();

    List tipos = (List) session.getAttribute(AWAdministracionHermod.LISTA_CIRCULO_TIPOS_PAGO);
    if (tipos == null) {
        tipos = new ArrayList();
    }

    /**
     * @autor HGOMEZ
     * @mantis 13407
     * @Requerimiento 064_453_Duplicidad_Nombre_Circulo
     * @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para
     * obtener el listado de departamentos por circulo.
     */
    List listaCirculoDepartamento = new Vector();
    DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
    listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

    int idCirculoInt = 0;
    String nombreCirculoDepartamento = "";
    String idCirculoString = "";

    if (session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO) == null) {
        List circulos = (List) application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        List elementos = new ArrayList();
        for (Iterator iter = circulos.iterator(); iter.hasNext();) {
            gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
            idCirculoString = circulo.getIdCirculo();
            if (departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)) {
                idCirculoInt = Integer.parseInt(idCirculoString);
                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                if (nombreCirculoDepartamento != "") {
                    elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                }
            }
        }
        session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO, elementos);
    }

    List circulos = (List) session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO);
    ListaElementoHelper circuloHelper = new ListaElementoHelper();
    circuloHelper.setTipos(circulos);

    List tiposPago = (List) application.getAttribute(WebKeys.LISTA_TIPOS_PAGO);
    ListaElementoHelper tiposPagoHelper = new ListaElementoHelper();
    tiposPagoHelper.setTipos(tiposPago);

    List canalesRecaudo = (List) session.getAttribute(WebKeys.LISTA_CANALES_RECAUDO_ACTIVOS);

    if (canalesRecaudo == null) {
        canalesRecaudo = new Vector();
    }

    List elementosCanales = new ArrayList();
    for (Iterator iter = canalesRecaudo.iterator(); iter.hasNext();) {
        gov.sir.core.negocio.modelo.CanalesRecaudo canalRecaudo = (gov.sir.core.negocio.modelo.CanalesRecaudo) iter.next();
        int idCanal = canalRecaudo.getIdCanal();
        String nombreCanal = canalRecaudo.getNombreCanal();
        elementosCanales.add(new ElementoLista(idCanal + "", nombreCanal));
    }

    ListaElementoHelper canalesHelper = new ListaElementoHelper();
    canalesHelper.setTipos(elementosCanales);

    List cuentasBancarias = (List) session.getAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO);

    if (cuentasBancarias == null) {
        cuentasBancarias = new Vector();
    }

    List elementosCuentasBancarias = new ArrayList();
    for (Iterator iter = cuentasBancarias.iterator(); iter.hasNext();) {
        gov.sir.core.negocio.modelo.CuentasBancarias ctasBancarias = (gov.sir.core.negocio.modelo.CuentasBancarias) iter.next();
        int idCuentaBancaria = ctasBancarias.getId();
        String nombreCuenta = ctasBancarias.getBanco().getNombre() + " - " + ctasBancarias.getNroCuenta();
        elementosCuentasBancarias.add(new ElementoLista(idCuentaBancaria + "", nombreCuenta));
    }

    ListaElementoHelper cuentasBancariasHelper = new ListaElementoHelper();
    cuentasBancariasHelper.setTipos(elementosCuentasBancarias);

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">

    var arrCtpEstados = [];

    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }
    function cambiarAccionAndSend(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
    }

    function validarEliminacion(idCanalTipoPago, idCirculo, consec) {
        alert('Va a eliminar un Canal - Tipo de Pago para un círculo');
        if (confirm('Esta seguro que desea eliminar el Canal - Tipo de Pago para el Círculo')) {
            eval('document.ELIMINARTIPOPAGO' + consec + '.CIRCULO_CANAL_TIPO_PAGO.value = ' + idCanalTipoPago);
            eval('document.ELIMINARTIPOPAGO' + consec + '.ID_CIRCULO.value = ' + idCirculo);
            eval('document.ELIMINARTIPOPAGO' + consec + '.submit()');
        }
    }

    function cargarFormActivarInactivar() {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionHermod.ACT_INACT_CTP%>';
        document.BUSCAR.<%= WebKeys.CIRCULO_TIPO_PAGO_ESTADOS%>.value = arrCtpEstados;
        document.BUSCAR.submit();
    }

    function removeFromArray(arr) {
        var what, a = arguments, L = a.length, ax;
        while (L > 1 && arr.length) {
            what = a[--L];
            while ((ax = arr.indexOf(what)) !== -1) {
                arr.splice(ax, 1);
            }
        }
        return arr;
    }

    function addToArrayEstateChange(field, idCtp, estado) {
        if (field.checked) {
            arrCtpEstados.push(idCtp + "&" + estado);
        } else {
            removeFromArray(arrCtpEstados);
        }

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
        <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Canales y Formas de Pago Por C&iacute;rculo </td>
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
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Canal y Forma de Pago a C&iacute;rculo</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>

                <tr>
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">C&iacute;rculo / Canal Recaudo / Forma de Pago / Entidad Bancaria </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>


                        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
                            <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=  AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO%>" value="<%=  AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO%>">
                            <input type="hidden" name="<%= WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO%>" id="<%= WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO%>" value="<%=cuentasBancarias.size()%>" >
                            <input type="hidden" name="<%= WebKeys.CIRCULO_TIPO_PAGO_ESTADOS%>" id="NINGUNO" value="NINGUNO">
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="200">C&iacute;rculo Registral </td>
                                    <td>
                                        <% try {
                                                circuloHelper.setNombre(CCirculo.ID_CIRCULO);
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.setId(CCirculo.ID_CIRCULO);
                                                circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO + "')\"");
                                                circuloHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="200">Canal de Recaudo </td>
                                    <td>
                                        <% try {
                                                canalesHelper.setNombre(CCanalesRecaudo.ID_CANAL_RECAUDO);
                                                canalesHelper.setCssClase("camposformtext");
                                                canalesHelper.setId(CCanalesRecaudo.ID_CANAL_RECAUDO);
                                                //canalesHelper.setFuncion("onChange=\"cambiarAccionAndSend()\"");
                                                canalesHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="220">Forma de Pago </td>
                                    <td>
                                        <% try {
                                                tiposPagoHelper.setNombre(CTipoPago.ID_TIPO_PAGO);
                                                tiposPagoHelper.setCssClase("camposformtext");
                                                tiposPagoHelper.setId(CTipoPago.ID_TIPO_PAGO);
                                                tiposPagoHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionHermod.SELECCIONA_CUENTA_BANCARIA + "')\"");
                                                tiposPagoHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="220">Entidad Bancaria </td>
                                    <td>
                                        <% try {
                                                cuentasBancariasHelper.setNombre(CCuentasBancarias.ID_CUENTA);
                                                cuentasBancariasHelper.setCssClase("camposformtext");
                                                cuentasBancariasHelper.setId(CCuentasBancarias.ID_CUENTA);
                                                cuentasBancariasHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="220">Canal SIR</td>
                                    <td>
                                        <%
                                            try {
                                                textHelper.setNombre(CTipoPago.CANAL_SIR);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTipoPago.CANAL_SIR);
                                                textHelper.setTipo("checkbox");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                                    </td>
                                    <td width="155">
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                                    </td>
                                    <td>
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                                    </td>
                                </tr>
                            </table>
                        </FORM>    



                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                <td>Listado</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td class="titulotbcentral">Círculo</td>
                                <td class="titulotbcentral">Canal Recaudo</td>
                                <td class="titulotbcentral">Forma de Pago</td>
                                <td class="titulotbcentral">Entidad Bancaria</td>
                                <td class="titulotbcentral">Canal Sir</td>
                                <td class="titulotbcentral">Estado</td>
                                <td width="35" class="titulotbcentral" style="text-align: center;"> 
                                    <a href="javascript:cargarFormActivarInactivar()">
                                        <img src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_estado.gif" width="139" height="21" border="0">
                                    </a>
                                </td>
                                <!--td width="50" align="center">Eliminar</td-->
                            </tr>

                            <%
                                int idTipoPago = 0;
                                for (Iterator iter = tipos.iterator(); iter.hasNext();) {
                                    CirculoCanalTipoPago dato = (CirculoCanalTipoPago) iter.next();

                            %>
                            <tr>
                                <td>&nbsp;</td>                  
                                <td class="camposformtext_noCase"><%=  dato.getNombreCirculo()%></td>
                                <td class="campositem"><%=  dato.getNombreCanal()%></td>
                                <td class="campositem"><%=  dato.getNombreFormaPago()%></td>
                                <td class="campositem"><%=  dato.getNombreBanco() + " - " + dato.getNroCuenta()%></td>
                                <td class="campositem"><%= (dato.isCanalSir()) ? "SI" : "NO"%></td>
                                <td class="campositem"><%= (dato.isCtpActivo()) ? "ACTIVO" : "INACTIVO"%></td>
                                <td style="text-align: center;">
                                    <% try {
                                            textHelper.setNombre(AWAdministracionHermod.ACTUALIZAR_ESTADO_CTP);
                                            textHelper.setCssClase("camposformtext_noCase");
                                            textHelper.setId(AWAdministracionHermod.ACTUALIZAR_ESTADO_CTP);
                                            textHelper.setTipo("checkbox");
                                            textHelper.setFuncion("onClick=\"addToArrayEstateChange(this, '" + dato.getIdCtp() + "','" + !dato.isCtpActivo() + "')\"");
                                            textHelper.render(request, out);
                                        } catch (HelperException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                            <%
                                    idTipoPago++;
                                }
                            %>


                        </table></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table></td>
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

<script>
    /*function validarEliminacion(idCanalTipoPago) {
     alert ('Va a eliminar un Canal - Tipo de Pago para un círculo');
     if (confirm('Esta seguro que desea eliminar el Canal - Tipo de Pago para el Círculo')){
     eliminarCanalTipoPago(idCanalTipoPago);
     //eval('document.ELIMINARTIPOPAGO' +nombre + '.submit()');
     }
     }*/
</script>
