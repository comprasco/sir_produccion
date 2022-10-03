<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="java.util.Vector"%>
<!--
    @Autor: Santiago Vásquez
    @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
!-->
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    Date today;
    String fechaAct;
    today = new Date();
    fechaAct = DateFormatUtil.format(today);
    Boolean isPageLoad;

    if (null == (isPageLoad = (Boolean) session.getAttribute("PAGE_LOAD"))) {
        // Carga inicial de pagina
        isPageLoad = Boolean.TRUE;
        session.setAttribute("PAGE_LOAD", isPageLoad);
    } else {
        isPageLoad = Boolean.FALSE;
    }

    gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
    param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=ISO-8859-1" />
<meta name="Author" content="ETB - INTEK" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }

    function cambiarAccionAndSend(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
    }
</script>
<script type="text/javascript">
    function findObj(theObj, theDoc) {
        var p, i, foundObj;

        if(!theDoc) theDoc = document;
        if((p = theObj.indexOf("?")) > 0 && parent.frames.length) {
            theDoc = parent.frames[theObj.substring(p+1)].document;
            theObj = theObj.substring(0,p);
        }
        if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
        for (i=0; !foundObj && i < theDoc.forms.length; i++)
            foundObj = theDoc.forms[i][theObj];
        for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
            foundObj = findObj(theObj,theDoc.layers[i].document);
        if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

        return foundObj;
    }
</script>
<script type="text/javascript">
    // local form manipulation
    function LocalForm(formName) {
        this.formName = formName;
    }

    LocalForm.prototype = new LocalForm();
    LocalForm.prototype.constructor = LocalForm;
    // Form.prototype.superclass = Object;
    LocalForm.prototype.submitForm = function() {
        formObject = findObj(this.formName);
        formObject.submit();
    }

    LocalForm.prototype.setFormAction = function(formAction) {
        formObject = findObj(this.formName);
        formObject.action = formAction;
    }

    LocalForm.prototype.setValue = function(elementName, elementValue) {
        formObject = findObj(this.formName);
        if(formObject == null)
            return;

        eval("formObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")));
    }
</script>
<script type="text/javascript">
    // local form dependant resources
    var actionField = "<%=WebKeys.ACCION%>";
    var REPORTE_179__SEND_ACTION = "<%= AWReportes.REPORTE_179__SEND_ACTION%>";
    var REPORTE_179__BACK_ACTION = "<%= AWReportes.REPORTE_179__BACK_ACTION%>";
    var REPORTE_179_LOADCIRCULOS = "<%= AWReportes.REPORTE_179_LOADCIRCULOSBYUSUARIO%>";
</script>
<script type="text/javascript">
    function js_OnEventPrint(formName, actionValue, param1) {
        var htmlForm;
        htmlForm = new LocalForm(formName);
        var msg = new String("Confirma envio de turno: " + param1 + " a impresion");

        if(confirm(msg)) {
            htmlForm.setValue(actionField, actionValue);
            htmlForm.setValue(printField , param1);
            htmlForm.submitForm();
            return true;
        }
        return void(0);
    }

    function js_OnEvent(formName, actionValue) {
        var htmlForm;
        htmlForm = new LocalForm(formName);
        htmlForm.setValue(actionField, actionValue);
        htmlForm.submitForm();
    }

    function js_OnEventConfirm(formName, actionValue, msg) {
        var htmlForm;
        htmlForm = new LocalForm(formName);

        if(confirm(msg)) {
            htmlForm.setValue(actionField, actionValue);
            htmlForm.submitForm();
            return true;
        }
        return void(0);
    }
    
    function juridica(nombre,valor,dimensiones)
    {
        document.getElementById('natjuridica_id').value=valor+"_ID";
        document.getElementById('natjuridica_nom').value=valor+"_NOM";
        document.getElementById('natjuridica_ver').value = valor+"_VER";
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
    }
    
    function cambiarAccionAndSendTipoTarifa(text) {
        if(document.BUSCAR.<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID%>.value!=""){
            document.BUSCAR.ACCION.value = text;
            document.BUSCAR.submit();
        }
    }
</script>
<form id="INITIAL_TIME_LOAD" name="INITIAL_TIME_LOAD" action="reportes.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="" />
</form>
<%
    if (isPageLoad.booleanValue()) {
%>
<script type="text/javascript">
    js_OnEvent("INITIAL_TIME_LOAD", REPORTE_179_LOADCIRCULOS);
</script>
<%    }
%>

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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"></td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                </tr>
            </table>
        </td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02">
            <table border="0" cellpadding="0" cellspacing="0" width="101%">
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reporte 179</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
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
                                <td class="bgnsub">Reporte Estadistico por Naturaleza Juridica</td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <form action="reportes.do" method="POST" name="BUSCAR" id="BUSCAR">
                            <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="">
                            <input type="hidden" name="<%=AWReportes.REPORTE_179__PARAM_USUARIOLOG%>" id="<%=AWReportes.REPORTE_179__PARAM_USUARIOLOG%>" value="<%=String.valueOf(param_UsuarioSir.getUsername())%>">
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="right" >Fecha Inicial:</div></td>
                                    <td width="200">
                                        <%
                                            try {
                                                if (null == session.getAttribute(AWReportes.REPORTE_179__PARAM_PFECHAINI)) {
                                                    session.setAttribute(AWReportes.REPORTE_179__PARAM_PFECHAINI, fechaAct);
                                                }
                                                TextHelper textHelper = new TextHelper();
                                                textHelper.setId(AWReportes.REPORTE_179__PARAM_PFECHAINI);
                                                textHelper.setNombre(AWReportes.REPORTE_179__PARAM_PFECHAINI);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                        <a href="javascript:NewCal('<%=AWReportes.REPORTE_179__PARAM_PFECHAINI%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="right" >Fecha Final:</div></td>
                                    <td width="200">
                                        <%
                                            try {
                                                if (null == session.getAttribute(AWReportes.REPORTE_179__PARAM_PFECHAFIN)) {
                                                    session.setAttribute(AWReportes.REPORTE_179__PARAM_PFECHAFIN, fechaAct);
                                                }
                                                TextHelper textHelper = new TextHelper();
                                                textHelper.setId(AWReportes.REPORTE_179__PARAM_PFECHAFIN);
                                                textHelper.setNombre(AWReportes.REPORTE_179__PARAM_PFECHAFIN);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                        <a href="javascript:NewCal('<%=AWReportes.REPORTE_179__PARAM_PFECHAFIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="right" >C&iacute;rculo Registral:</div></td>
                                    <td width="200">
                                        <%
                                            try {
                                                List elementos;
                                                elementos = (List) session.getAttribute(AWReportes.REPORTE_XX__ITEM_CIRCULOSBYUSUARIO);
                                                if (null == elementos) {
                                                    elementos = new ArrayList();
                                                } else {
                                                    if (elementos.size() > 0 && !((ElementoLista) elementos.get(0)).getId().equals("TODOS")) {
                                                        elementos.add(0, new ElementoLista("TODOS", " TODOS"));
                                                    }
                                                }
                                                ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                                circuloHelper.setTipos(elementos);
                                                circuloHelper.setId(AWReportes.REPORTE_179__PARAM_IDCIRCULO);
                                                circuloHelper.setNombre(AWReportes.REPORTE_179__PARAM_IDCIRCULO);
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>

                                <!-- TODO: Naturaleza -->

                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="right" >Naturaleza Jur&iacute;dica:</div></td>
                                    <td>
                                        <table class="camposform" style="border: 0;">
                                            <tr>
                                                <td width="15" align="justify">C&oacute;digo</td>
                                                <td width="40">
                                                    <%try {
                                                            TextHelper textHelper = new TextHelper();
                                                            textHelper.setFuncion("");
                                                            textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                            textHelper.setCssClase("camposformtext");
                                                            textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                            textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('" + AWReportes.REPORTE_179_NATURALEZA_JURIDICA + "')\"");
                                                            textHelper.setSize("4");
                                                            textHelper.render(request, out);
                                                        } catch (HelperException re) {
                                                            out.println("ERROR " + re.getMessage());
                                            }%></td>
                                                <td width="80" align="right">Descripci&oacute;n</td>
                                                <td align="justify">
                                                    <input name="natjuridica_id" type="hidden" id="natjuridica_id" value="">
                                                    <input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value="">
                                                    <input name="natjuridica_ver" type="hidden" id="natjuridica_ver" value="">
                                                    <input name="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" type="hidden" id="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" value="<%=session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)%>">
                                                <td align="right">
                                                    <%try {
                                                            TextAreaHelper textAreaHelper = new TextAreaHelper();
                                                            textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                                                            textAreaHelper.setCols("50");
                                                            textAreaHelper.setRows("1");
                                                            textAreaHelper.setReadOnly(true);
                                                            textAreaHelper.setCssClase("camposformtext");
                                                            textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                                                            textAreaHelper.render(request, out);
                                                        } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }%>
                                                </td>
                                                <td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?<%= WebKeys.ACCION%>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <!-- TODO: Naturaleza -->
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="right" >Tipo predio:</div></td>
                                    <td width="200">
                                        <%
                                            try {
                                                ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
                                                List tiposPredio = (List) session.getAttribute(AWReportes.REPORTE_179__LISTA_PREDIOS);
                                                if (tiposPredio == null) {
                                                    tiposPredio = new Vector();
                                                }
                                                tipoPredioHelper.setOrdenar(false);
                                                tipoPredioHelper.setTipos(tiposPredio);
                                                tipoPredioHelper.setCssClase("camposformtext");
                                                tipoPredioHelper.setId(AWReportes.REPORTE_179__PARAM_IDTIPOPREDIO);
                                                tipoPredioHelper.setNombre(AWReportes.REPORTE_179__PARAM_IDTIPOPREDIO);
                                                tipoPredioHelper.setCssClase("camposformtext");
                                                tipoPredioHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="155">
                                    <a href="javascript:js_OnEvent( 'BUSCAR', REPORTE_179__BACK_ACTION );">
                                        <img alt="regresar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
                                    </a>
                                </td>
                                <td>
                                    <a href="javascript:js_OnEvent( 'BUSCAR', REPORTE_179__SEND_ACTION );">
                                        <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_observar.gif" width="139" height="21" border="0" />
                                    </a>
                                </td>
                            </tr>
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