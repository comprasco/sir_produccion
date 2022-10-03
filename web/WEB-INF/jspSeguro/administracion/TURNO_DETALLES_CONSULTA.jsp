<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %> 
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.smart.SMARTKeys" %>
<%  //System.out.println("MENSAJE 1.1......");
    TextHelper textHelper = new TextHelper();
    List tipos = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
    if (tipos == null) {
        tipos = new ArrayList();
    }
    List listaProcesos = (List) application.getAttribute(WebKeys.LISTA_PROCESOS_SISTEMA);
    ElementoLista elementoProceso = null;
    List procesos = new ArrayList();

    elementoProceso = new ElementoLista();
    elementoProceso.setId("CUALQUIERA");
    elementoProceso.setValor("CUALQUIERA");
    //System.out.println("MENSAJE 1.2......");
    if (!listaProcesos.contains(elementoProceso)) {
        procesos.add(elementoProceso);
    }
    //System.out.println("MENSAJE 1.3......");
    for (Iterator iteradorProcesos = listaProcesos.iterator(); iteradorProcesos.hasNext();) {
        elementoProceso = (ElementoLista) iteradorProcesos.next();
        //elementoProceso = new ElementoLista();
        //elementoProceso.setId(String.valueOf(proceso.getIdProceso()));
        //elementoProceso.setValor(proceso.getNombre());
        procesos.add(elementoProceso);
    }//System.out.println("MENSAJE 1.4......");
    ListaElementoHelper procesosHelper = new ListaElementoHelper();
    procesosHelper.setTipos(procesos);
    //procesosHelper.setSelected(WebKeys.SIN_SELECCIONAR);

    String idMatricula = (String) session.getAttribute(CTurno.MATRICULA_TURNO);
    //session.removeAttribute(CTurno.MATRICULA_TURNO);

    String idProceso = (String) session.getAttribute(CProceso.PROCESO_ID);
    if (idProceso == null) {
        idProceso = "CUALQUIERA";
    }
    procesosHelper.setSelected(idProceso);
    //System.out.println("MENSAJE 1.5......");
    String vistaActual = (String) session.getAttribute(SMARTKeys.VISTA_ACTUAL);
    session.setAttribute(WebKeys.VISTA_VOLVER, vistaActual);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.ERROR_FIND.value = document.getElementById("tablaError");
    }

    function cambiarAccionFiltro(text) {
        document.FILTRAR.<%= WebKeys.ACCION%>.value = text;
        document.FILTRAR.submit();
    }

</script>

<script>
    function check_radio(id) {
        if (id == '<%=CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>') {
            document.getElementById('<%=CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>').checked = true;
        } else {
            document.getElementById('<%=CTurno.CONSULTA_TURNO_POR_MATRICULA%>').checked = true;
        }
    }
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">


    <tr> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta de Detalles de Turnos</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td></td>
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

                                <td class="bgnsub">Datos del Turno</td>
                                <td width="16" class="bgnsub"></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>



                        <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
                            <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                            <input type="hidden" name="ERROR_FIND" id="ERROR_FIND" value="">                        		  
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"></td>
                                    <%

                                        String temp = null;
                                        try {
                                            temp = (String) session.getAttribute("TIPO_CONSULTA");
                                        } catch (Exception ee) {
                                            temp = null;
                                        }

                                        //System.out.println("MENSAJE 1.6......");
                                        boolean esTurno = false;
                                        if (temp == null) {
                                            esTurno = true;
                                        } else {
                                            if (temp.equals(CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR)) {
                                                esTurno = true;
                                            } else {
                                                esTurno = false;
                                            }

                                        }
                                    %>

                                    <td width="220">                    	
                                        <input name="TIPO_CONSULTA"  id="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" type="radio" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" <%=esTurno ? "checked" : ""%> > N&uacute;mero de Turno</td>
                                    <td>
                                        <% //System.out.println("MENSAJE 1.7......");
                                            try {
                                                textHelper.setNombre(CTurno.ID_TURNO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTurno.ID_TURNO);
                                                textHelper.setFuncion("onClick=\"check_radio('" + CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR + "')\";");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="20">
                                    </td>
                                    <td width="220"> 
                                        <input name="TIPO_CONSULTA"  id="<%= CTurno.CONSULTA_TURNO_POR_MATRICULA%>" type="radio" value="<%= CTurno.CONSULTA_TURNO_POR_MATRICULA%>" <%=!esTurno ? "checked" : ""%> >  N&uacute;mero de Matr&iacute;cula </td>
                                    <td>
                                        <% //System.out.println("MENSAJE 1.8......");
                                            try {
                                                textHelper.setNombre(CTurno.MATRICULA_TURNO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTurno.MATRICULA_TURNO);
                                                textHelper.setFuncion("onClick=\"check_radio('" + CTurno.CONSULTA_TURNO_POR_MATRICULA + "')\";");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <br>

                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20">

                                    </td>
                                    <td width="155">
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=  AWTrasladoTurno.CONSULTAR_TURNO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0">
                                    </td>
                                    <td>
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=AWTrasladoTurno.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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

            <%
                if (!tipos.isEmpty() || idMatricula != null) {
                    //System.out.println("MENSAJE 1.9......");
%>	
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
                            <%-- El selector de proceso --%>
                            <% if (idMatricula != null) {%>
                            <tr>
                                <td class="titulotbcentral">
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <form action="trasladoTurno.do" method="POST" name="FILTRAR" id="FILTRAR">
                                            <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%= AWTrasladoTurno.CONSULTAR_TURNO%>">
                                            <input type="hidden" name="<%= CTurno.MATRICULA_TURNO%>" id="<%= CTurno.MATRICULA_TURNO + idMatricula%>" value="<%= idMatricula%>">
                                            <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%=temp%>">                        		  
                                            <tr>
                                                <td class="titulotbcentral" width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                                <td class="titulotbcentral" align="left">Proceso</td>
                                                <td>
                                                    <%//System.out.println("MENSAJE 1.10......");
                                                        try {
                                                            procesosHelper.setNombre(CProceso.PROCESO_ID);
                                                            procesosHelper.setCssClase("camposformtext");
                                                            procesosHelper.setId(CProceso.PROCESO_ID);
                                                            procesosHelper.setFuncion("onChange=\"cambiarAccionFiltro('"
                                                                    + AWTrasladoTurno.CONSULTAR_TURNO + "');\"");
                                                            procesosHelper.render(request, out);
                                                        } catch (HelperException he) {
                                                            out.println("ERROR " + he.getMessage());
                                                        }
                                                    %>
                                                </td>
                                            </tr>
                                        </form>
                                    </table>

                                </td>
                            </tr>
                            <% }%>
                            <tr>
                                <td class="titulotbcentral" ><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"> Turno</td>
                                <td width="50" align="center">Ver Detalles</td>
                            </tr>
                            <%
                                for (Iterator iter = tipos.iterator(); iter.hasNext();) {
                                    Turno dato = (Turno) iter.next();
                            %>
                            <form action="trasladoTurno.do" method="post" name="ELIMINAR" id="ELIMINAR">
                                <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWTrasladoTurno.VER_DETALLES_TURNO%>" value="<%= AWTrasladoTurno.VER_DETALLES_TURNO%>">
                                <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= dato.getIdWorkflow()%>" value="<%= dato.getIdWorkflow()%>">
                                <input type="hidden" name="<%= CTurno.MATRICULA_TURNO%>" id="<%= CTurno.MATRICULA_TURNO + idMatricula%>" value="<%= idMatricula%>">                

                                <tr>
                                    <td class="camposformtext_noCase"><%= dato.getIdWorkflow()%></td>
                                    <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0">
                                    </td>
                                </tr>
                            </form>
                            <%
                                }
                            %>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
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
            <%
                }
            %>

        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

</table>

