<%@page import="gov.sir.core.negocio.modelo.constantes.CCuentasBancarias"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculoFestivo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPago"%>
<%@page import="gov.sir.core.negocio.modelo.Banco"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CBanco"%>
<jsp:directive.page import="gov.sir.core.negocio.modelo.BancosXCirculo"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.CuentasBancarias"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CCirculo"/>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>

<%
    TextHelper textHelper = new TextHelper();

    List listaCirculoDepartamento = new Vector();
    DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
    listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

    int idCirculoInt = 0;
    String nombreCirculoDepartamento = "";
    String idCirculoString = "";

    if (session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO) == null) {
        List circulos = (List) application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        List elementos = new ArrayList();

        for (java.util.Iterator iter = circulos.iterator(); iter.hasNext();) {
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
    
    /*ServletContext context = session.getServletContext();
    List cuentasBancarias = null;
    if (context != null) {
        System.out.println("CONTEXT LLENO CUENTAS");
        if(context.getAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS)!=null){
            System.out.println("CONTEXT LLENO LISTA_CUENTAS_BANCARIAS");
        }
        //cuentasBancarias = (List) context.getAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS);
        //System.out.println("cuentasBancarias TAMAÑO "+cuentasBancarias.size());
    }
    if (cuentasBancarias == null) {
        System.out.println("CONTEXT NULO CUENTAS");
        cuentasBancarias = new Vector();
    }*/
    
    /*if (session.getAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO) != null) {
        */
               
        List listadoBancosCirculo = (List) session.getAttribute(WebKeys.LISTADO_BANCOS_CIRCULO);
        
        if (listadoBancosCirculo == null || listadoBancosCirculo.isEmpty()) {
            
            List bancosXCirculo = (List) session.getAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO);            
            
            if(bancosXCirculo == null){
                bancosXCirculo = new Vector();
                session.setAttribute(WebKeys.LISTADO_BANCOS_CIRCULO, bancosXCirculo);
            } else {
                List elementos = new ArrayList();
                String nomBanco;
                for (Iterator iter = bancosXCirculo.iterator(); iter.hasNext();) {
                    nomBanco = "";
                    BancosXCirculo dato = (BancosXCirculo) iter.next();
                    if (dato != null && dato.getBanco() != null && dato.getBanco().getNombre() != null) {
                        nomBanco = dato.getBanco().getIdBanco() + " " + dato.getBanco().getNombre();
                        elementos.add(new ElementoLista(dato.getBanco().getIdBanco(), nomBanco));
                    }
                }
                session.setAttribute(WebKeys.LISTADO_BANCOS_CIRCULO, elementos);
            }
        
        }
    
    List bancos = (List) session.getAttribute(WebKeys.LISTADO_BANCOS_CIRCULO);
    ListaElementoHelper listaBancosHelper = new ListaElementoHelper();
    listaBancosHelper.setTipos(bancos);
    

    List cuentasBancarias=(List)session.getAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS);

    if(cuentasBancarias==null){
        cuentasBancarias=new Vector();        
    }
            
    
    
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>

<script type="text/javascript">
    
    var arrCtaBancariaEstados = [];
    
    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }
    
    function onChangeCombo(text) {
        document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
        document.BUSCAR.submit();
    }

    function cargarFormActivarInactivar() {
        //if (arrCtaBancariaEstados.length > 0) {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionHermod.ACT_INACT_CTA_BANCARIA%>';
            document.BUSCAR.<%= WebKeys.CUENTA_BANCARIA_ESTADOS%>.value = arrCtaBancariaEstados;
            document.BUSCAR.submit();
        //}
        //alert("Seleccione por lo menos una cuenta para activar/inactivar");
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
    
    function addToArrayEstateChange(field, circulo, banco, cuenta, estado) {
        if (field.checked) {
            arrCtaBancariaEstados.push(circulo + "&" + banco + "&" + cuenta + "&" + estado);
        } else {
            removeFromArray(arrCtaBancariaEstados);
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cuentas Bancarias por C&iacute;rculo</td>
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Administrac&oacute;n de Cuentas Bancarias Por C&iacute;rculo</td>
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
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">C&iacute;rculo Registral / Banco / Nro Cuenta</td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
                            <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=  AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO%>" value="<%= AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO%>">
                            <input type="hidden" name="<%= WebKeys.CUENTA_BANCARIA_ESTADOS%>" id="NINGUNO" value="NINGUNO">
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="200">C&iacute;rculo Registral </td>
                                    <td>
                                        <% try {
                                                circuloHelper.setNombre(CCuentasBancarias.ID_CIRCULO);
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.setId(CCuentasBancarias.ID_CIRCULO);
                                                circuloHelper.setFuncion("onChange=\"onChangeCombo('" + AWAdministracionHermod.LISTADO_BANCOS_X_CIRCULO + "')\"");
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
                                    <td width="200">Banco</td>
                                    <td>
                                        <% try {
                                                listaBancosHelper.setNombre(CCuentasBancarias.ID_BANCO);
                                                listaBancosHelper.setCssClase("camposformtext");
                                                listaBancosHelper.setId(CCuentasBancarias.ID_BANCO);
                                                listaBancosHelper.setFuncion("onChange=\"onChangeCombo('" + AWAdministracionHermod.CUENTAS_X_CIRCULO_BANCO + "')\"");
                                                listaBancosHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="200">N&uacute;mero Cuenta </td>
                                    <td>
                                        <%
                                            try {
                                                textHelper.setNombre(CCuentasBancarias.ID_CUENTA);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCuentasBancarias.ID_CUENTA);
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
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                                    </td>
                                    <td>
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                                    </td>
                                </tr>
                            </table>
                        </form>
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
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                <!--td class="titulotbcentral">C&iacute;rculo</td>
                                <td class="titulotbcentral">Entidad Bancaria</td-->
                                <td class="titulotbcentral">Nro Cuenta</td>
                                <td class="titulotbcentral">Estado</td>
                                <td width="35" class="titulotbcentral" style="text-align: center;"> 
                                    <a href="javascript:cargarFormActivarInactivar()">
                                        <img src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_estado.gif" width="139" height="21" border="0">
                                    </a>
                                </td>
                            </tr>
                            <!--aca va lo eliminado geremias -->
                            <%
                                //int idFestivo =0;
                                //String nomBanco;
                                for(Iterator iter = cuentasBancarias.iterator(); iter.hasNext();){
                                CuentasBancarias dato=(CuentasBancarias)iter.next();
                                        /*nomBanco="";
                                        if(dato!=null && dato.getBanco()!=null && dato.getBanco().getNombre()!=null)
                                                nomBanco=dato.getBanco().getNombre();*/
                            %>
                            <tr>
                                <td>&nbsp;</td>
                                <td class="campositem"><%=dato.getNroCuenta()%></td>
                                <td class="campositem"><%= (dato.isActiva()) ? "ACTIVO" : "INACTIVO"%></td>
                                <td style="text-align: center;">
                                        <% try {
                                                textHelper.setNombre(AWAdministracionHermod.ACTUALIZAR_ESTADO_CTA_BANCARIA);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(AWAdministracionHermod.ACTUALIZAR_ESTADO_CTA_BANCARIA);
                                                textHelper.setTipo("checkbox");
                                                textHelper.setFuncion("onClick=\"addToArrayEstateChange(this, '" + dato.getIdCirculo() + "','" + dato.getIdBanco() + "','" + dato.getNroCuenta() + "','" + !dato.isActiva() + "')\"");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
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

<script>
    /*function validarEliminacion(nombre) {
     alert ('Va a eliminar un banco de un Círculo');
     if (confirm('Esta seguro que desea eliminar el banco del Círculo')){
     eval('document.ELIMINARBANCO' +nombre + '.submit()');
     }
     }
     
     function validarPrincipal(nombre) {
     var formulario=document.getElementById('ELIMINARBANCO'+nombre);
     formulario.ACCION.value='ACTIVAR_BANCO_PRINCIPAL';
     eval('document.ELIMINARBANCO' +nombre + '.submit()');
     }*/
</script>
