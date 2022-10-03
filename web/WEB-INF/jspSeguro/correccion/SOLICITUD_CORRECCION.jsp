<%--
/*
* @author : CTORRES
* @change : se avilita el uso de la clase gov.sir.core.negocio.modelo.Turno
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
* 
*/
--%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWLiquidacionCorreccion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>

<%
    ListaElementoHelper docHelper = new ListaElementoHelper();
    TextHelper textHelper = new TextHelper();
    TextHelper txtHelper = new TextHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    List matriculasAsociadas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
    List matriculasAsociadasSirMig = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);
    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

    RadioHelper radioHelper = new RadioHelper();
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    String idCirculo = "";
    if (request.getSession().getAttribute(WebKeys.CIRCULO) != null) {
        idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
        idCirculo = idCirculo + "-";
    }
    /*
* @author : CTORRES
* @change : se variables para tratar con con la correccion de testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
* 
     */
    Turno turnoTestamentoObj = (Turno) session.getAttribute(CTurno.TURNO_TESTAMENTO_OBJ);
    String turnoTestamento = (String) session.getAttribute(CTurno.TURNO_TESTAMENTO);
    boolean read = false;
    boolean activo = true;
    if (turnoTestamento == null) {
        turnoTestamento = "";
    } else {
        activo = false;
    }
    String style = "camposformtext";
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" >
    function agregarMatricula(text) {
        document.CORRECCION.ACCION.value = text;
        document.CORRECCION.submit();
    }

    function agregarMatriculaRango(text) {
        document.CORRECCION.ACCION.value = text;
        document.CORRECCION.submit();
    }

    function agregarMatriculaSinMigrar(text) {
        if (confirm('¿Esta seguro que la matrícula aún no se ha migrado?. Si no es así utilice la opción de Matrícula inmobiliaria de la Propiedad.')) {
            document.CORRECCION.ACCION.value = text;
            document.CORRECCION.submit();
        }
    }

    function eliminarMatriculas(text) {
        document.CORRECCION.ACCION.value = text;
        document.CORRECCION.submit();
    }

    function cambiarAccion(text) {
        if (text == 'LIQUIDAR') {
            document.getElementById('btn_aceptar_liquidar').width = 0;
        }
        document.CORRECCION.ACCION.value = text;
        document.CORRECCION.submit();
    }

    function quitar(text) {
        document.CORRECCION.ITEM.value = text;
        cambiarAccion('ELIMINAR');
    }

    function sinCorreo() {
        document.getElementById('<%=CTurno.DIRECCION%>').setAttribute("disabled", "disabled", true);
    }
    function conCorreo() {
        document.getElementById('<%=CTurno.DIRECCION%>').removeAttribute("readonly", false);
        document.CORRECCION.<%=CTurno.DIRECCION%>.disabled = false;
    }

    function disableIt(obj, b)
    {
        obj.disabled = b;
    }
    /*
     * @author : CTORRES
     * @change : se agrega funcion javascript asociarTurno
     * Caso Mantis : 12291
     * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     * 
     */
    function asociarTurno() {
        document.CORRECCION.ACCION.value = 'VALIDAR_TURNO_TESTAMENTO';
        document.CORRECCION.submit();
    }
</SCRIPT>
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                </tr>
            </table></td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Correcci&oacute;n </td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <!--AHERRENO 28/05/2012
                                    REQ 076_151 TRANSACCIONES-->
                                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                                            <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
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
                        <form action="turnoLiquidacionCorreccion.do" method="post"  name="CORRECCION" id="CORRECCION" method="post">
                            <input type="hidden" name="ACCION" value="AGREGAR">
                            <!--
                                                    <input type="hidden" name="VER_ANTIGUO_SISTEMA"	id="VER_ANTIGUO_SISTEMA">
                            -->
                            <br />
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>


                            <%if (matriculasAsociadas != null && !matriculasAsociadas.isEmpty()) {%>

                            <table width="100%" class="camposform">
                                <tr>
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                    <td>Folios de esta solicitud</td>
                                </tr>
                                <tr>
                                    <td width="20">&nbsp;
                                    </td>
                                    <td>
                                        <% try {
                                                tablaHelper.setColCount(5);
                                                tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                                                tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                                                tablaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                                    </td>
                                    <td>Eliminar Seleccionadas</td>
                                    <td>
                                        <a href="javascript:eliminarMatriculas('ELIMINAR')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                                    </td>
                                </tr>
                            </table>
                            <P>&nbsp;</P>
                                <%}%>
                            <table width="100%" class="camposform">
                                <tr>
                                </tr>
                                <tr>

                                    <!-- cosas nuevas que se metieron por el cambio a rango de matriculas se modifica el boton agregarmatricula -->
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="60%">N&uacute;mero de Matr&iacute;cula</td>

                                    <td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA);
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td>
                                    
                                    <td align="right">
                                        <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                            <tr>
                                                <td width="20"><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                <td>Agregar Matr&iacute;cula</td>
                                                <td align="right">
                                                    <%
                                                        /*
* @author : CTORRES
* @change : se condiciona la muestra de datos segun el valor de la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
* 
                                                                       */
                                                                      if (activo) {%>
                                                    <a href="javascript:agregarMatricula( 'AGREGAR' );" name="imageField">
                                                        <img alt="[add matricula]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                                    </a>
                                                    <%} else {%>
                                                    <a name="imageField">
                                                        <img alt="[add matricula]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                                    </a>
                                                    <% }%>

                                                    <!--
                                                    <input name="" type="image" src=""  onClick="">
                                                    -->
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <!--hasta aqui lo que se metio nuevo -->


                                </tr>
                            </table>


                            <!-- Opcion de asociar un rango de matriculas -->

                            <!-- hasta aca se ha metido de nuevo de acuerdo al ticket se cambia el boton agregar matricula-->

                            <br>

                            <!-- debido a la solicitud de rangos de matriculas se introducen los campos necesarios y el boton agregar rango -->

                            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="65%">Rango de Matr&iacute;culas</td>
                                    <td>
                                        <table>
                                            <tr><td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA_RL);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA_RL);
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td></tr>
                                            <tr><td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA_RR);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA_RR); 
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td></tr>
                                        </table>
                                    </td>
                                    <td align="right">
                                        <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                            <tr>
                                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                <td>Agregar Rango</td>
                                                <td align="right">
                                                    <%/*
    * @author : CTORRES
    * @change : se condiciona la muestra de datos segun el valor de la variable activo
    * Caso Mantis : 12291
    * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
* 
                                                     */
                                                    if (activo) {%>
                                                    <a href="javascript:agregarMatriculaRango( 'AGREGAR_RANGO' );" name="imageField">
                                                        <img alt="[add rango]" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a> 
                                                        <%} else {%>
                                                    <a name="imageField">
                                                        <img alt="[add rango]" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a> 
                                                        <%}%>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>

                            <%--
                            /*
                           * @author : CTORRES
                           * @change :se agrega zona de turno de testamento
                           * Caso Mantis : 12291
                           * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                           * 
                           */  
                            --%>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno de Testamento</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>	                        
                            <table width="100%" class="camposform">

                                <tr>
                                    <td>Turno testamentos</td>
                                    <td width="165px">
                                        <%
                            turnoTestamento = (String) session.getAttribute(CTurno.TURNO_TESTAMENTO);

                            if (turnoTestamento == null || turnoTestamento.equals("")) {%>
                                    <td><% try {
                                            TextHelper textHelper1 = new TextHelper();
                                            textHelper1.setNombre("TURNO_TESTAMENTO");
                                            textHelper1.setCssClase("camposformtext");
                                            textHelper1.setId("TURNO_TESTAMENTO");
                                            textHelper1.setFuncion("onFocus=\"javascript:cambiarAccionForm('VALIDAR_TURNO_TESTAMENTO')\"");
                                            textHelper1.setReadonly(read);
                                            textHelper1.render(request, out);
                                            textHelper1.setFuncion("");
                                        } catch (HelperException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                        %></td>
                                    <td align="right">
                                        <table border="0" cellpadding="0" cellspacing="2"
                                               class="camposform">
                                            <tr>
                                                <td style="padding-left:20px;width:160px;">Asociar turno testamento</td>
                                                <td><a href='javascript:asociarTurno()'><img
                                                            src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
                                                            width="35" height="25" border="0"></a></td>
                                            </tr>
                                        </table>

                                        <%} else {%>
                                    <td>
                                        <% try {
                                                TextHelper textHelper2 = new TextHelper();
                                                textHelper2.setNombre("TURNO_TESTAMENTO");
                                                textHelper2.setCssClase(style);
                                                textHelper2.setId("TURNO_TESTAMENTO");
                                                textHelper2.setEditable(false);
                                                textHelper2.setReadonly(read);
                                                textHelper2.render(request, out);
                                                textHelper2.setEditable(true);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>

                                    </td>
                                    <td align="right">
                                        <table border="0" cellpadding="0" cellspacing="2"
                                               class="camposform">
                                            <tr>
                                                <td>Eliminar turno testamento</td>
                                                <td><a
                                                        href="javascript:cambiarAccion('ELIMINAR_TURNO_TESTAMENTO')"><img
                                                            src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
                                                            width="35" height="25" border="0"></a></td>
                                            </tr>
                                        </table>
                                        <%}
                                        %>
                                </tr>
                            </table>             
                            <!-- hasta aca llegan los cambios debido a la solicitud de rangos de matriculas se introducen los campos y el boton agregar rango -->


                            <!-- Opciones de matriculas sin migrar -->
                            <%
                                if (circulo != null && circulo.isProcesoMigracion()) {
                            %>

                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;culas Inmobiliarias <B>SIN MIGRAR</B></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <%if (matriculasAsociadasSirMig != null && !matriculasAsociadasSirMig.isEmpty()) {%>

                            <table width="100%" class="camposform">
                                <tr>
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                    <td>Folios no migrados de  esta solicitud</td>
                                </tr>
                                <tr>
                                    <td width="20">&nbsp;
                                    </td>
                                    <td>
                                        <% try {
                                                tablaHelper.setColCount(5);
                                                tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);
                                                tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                                                tablaHelper.setInputName(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);
                                                tablaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                <tr>
                                    <td width="20">
                                        <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                                    </td>
                                    <td>Eliminar Seleccionadas</td>
                                    <td>
                                        <a href="javascript:eliminarMatriculas('ELIMINAR_MIG_INC')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                                    </td>
                                </tr>
                            </table>
                            <P>&nbsp;</P>
                                <%}%>
                            <table width="100%" class="camposform">
                                <tr></tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>N&uacute;mero de Matr&iacute;cula  <B>SIN MIGRAR</B></td>
                                    <td><input name="<%=CFolio.ID_MATRICULA_SIR_MIG%>" id="<%=CFolio.ID_MATRICULA_SIR_MIG%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CFolio.ID_MATRICULA_SIR_MIG%>');" class="camposformtext">
                                        <img id="<%=CFolio.ID_MATRICULA_SIR_MIG%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
                                    </td>
                                </tr>
                            </table>

                            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                <tr>
                                    <td width="20"><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td>Agregar Matr&iacute;cula</td>
                                    <td align="right">
                                        <a href="javascript:agregarMatriculaSinMigrar( 'AGREGAR_MIG_INC' );" name="imageField">
                                            <img alt="[add matricula]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                        </a>

                                    </td>
                                </tr>
                            </table>
                            <br>


                            <!--End Opciones de matriculas sin migrar-->

                            <%
                                }
                            %>


                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Asociado</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>&iquest; Est&aacute; asociado a un turno ?</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>

                                        <!-- BUG: 3520 -->
                                        <!-- Turno de documento objeto de correcci&oacute;n -->
                                        Turno asociado :

                                    </td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CTurno.TURNO_ANTERIOR);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTurno.TURNO_ANTERIOR);
                                                /*
* @author : CTORRES
* @change : Se establece el valor editable del text helper
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
* 
                                                 */
                                                textHelper.setEditable(activo);
                                                textHelper.render(request, out);
                                                textHelper.setEditable(true);

                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Datos de la Solicitud</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td class="contenido">Descripci&oacute;n</td>
                                </tr>
                                <tr>
                                    <td height="18">&nbsp;</td>
                                    <td class="contenido">
                                        <% try {
                                                textAreaHelper.setNombre(CTurno.DESCRIPCION);
                                                textAreaHelper.setCssClase("camposformtext");
                                                textAreaHelper.setId(CTurno.DESCRIPCION);
                                                textAreaHelper.setRows("5");
                                                textAreaHelper.setCols("60");
                                                textAreaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Comentario / Documentos anexos</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>Comentario</td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>

                                        <% try {
                                                textAreaHelper.setNombre(CSolicitud.COMENTARIO);
                                                textAreaHelper.setCols("60");
                                                textAreaHelper.setRows("5");
                                                textAreaHelper.setCssClase("camposformtext");
                                                textAreaHelper.setId(CSolicitud.COMENTARIO);
                                                textAreaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>

                                    </td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Correo :: Opcional </td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correo.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Rec&iacute;bido por Correo</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                                    <td width="20">
                                        <%
                                            RadioHelper radio = new RadioHelper();

                                            try {
                                                radio.setNombre(AWLiquidacionCorreccion.RECIBIDO_CORREO);
                                                radio.setId(AWLiquidacionCorreccion.RECIBIDO_CORREO);
                                                radio.setValordefecto("SI");
                                                radio.setFuncion("onclick='javascript:disableIt(document.CORRECCION." + CTurno.DIRECCION + ", false)'");
                                                radio.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td> Si </td>
                                    <td width="16">
                                        <% try {
                                                String defecto = (String) request.getSession().getAttribute(AWLiquidacionCorreccion.RECIBIDO_CORREO);
                                                if (defecto == null) {
                                                    request.getSession().setAttribute(AWLiquidacionCorreccion.RECIBIDO_CORREO, "NO");
                                                }

                                                radio.setNombre(AWLiquidacionCorreccion.RECIBIDO_CORREO);
                                                radio.setId(AWLiquidacionCorreccion.RECIBIDO_CORREO);
                                                radio.setValordefecto("NO");
                                                radio.setFuncion("onclick='javascript:disableIt(document.CORRECCION." + CTurno.DIRECCION + ", true)'");
                                                radio.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td>No</td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>Direcci&oacute;n de Correo</td>
                                    <td>

                                        <% try {
                                                textHelper.setNombre(CTurno.DIRECCION);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CTurno.DIRECCION);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>

                                        <script>
                                            disableIt(document.CORRECCION.<%=CTurno.DIRECCION%>, true);
                                        </script>

                                    </td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Derecho de Petici&oacute;n </td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                                    <td width="20">
                                        <% try {
                                                radioHelper.setNombre(CTurno.PETICION);
                                                radioHelper.setId(CTurno.PETICION);
                                                radioHelper.setValordefecto("SI");
                                                radioHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td> Si </td>
                                    <td width="16">
                                        <% try {
                                                String defecto = (String) request.getSession().getAttribute(CTurno.PETICION);
                                                if (defecto == null) {
                                                    request.getSession().setAttribute(CTurno.PETICION, "NO");
                                                }

                                                radioHelper.setNombre(CTurno.PETICION);
                                                radioHelper.setId(CTurno.PETICION);
                                                radioHelper.setValordefecto("NO");
                                                radioHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td>No</td>
                                </tr>
                            </table>

                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Correcci&oacute;n solicitada con anterioridad</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                                    <td width="20">
                                        <%
                                            RadioHelper radio1 = new RadioHelper();

                                            try {
                                                radio1.setNombre(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES);
                                                radio1.setId(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES);
                                                radio1.setValordefecto("SI");
                                                radio1.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td> Si </td>
                                    <td width="16">
                                        <% try {
                                                String defecto1 = (String) request.getSession().getAttribute(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES);
                                                if (defecto1 == null) {
                                                    request.getSession().setAttribute(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES, "NO");
                                                }

                                                radio1.setNombre(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES);
                                                radio1.setId(AWLiquidacionCorreccion.RECIBIDO_SIMILAR_ANTES);
                                                radio1.setValordefecto("NO");
                                                radio1.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td>No</td>
                                </tr>
                            </table>

                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Inter&eacute;s Jur&iacute;dico</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>Inter&eacute;s Jur&iacute;dico</td>
                                    <td>
                                        <% try {


                                                /*							List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_INTERES_JURIDICO);
                if(tipos == null){
                        tipos = new Vector();
                }
                                ListaElementoHelper listaHelper = new ListaElementoHelper();
                                                listaHelper.setTipos(tipos);
                        listaHelper.setNombre(AWLiquidacionCorreccion.INTERES_JURIDICO);
                            listaHelper.setCssClase("camposformtext");
                            listaHelper.setId(AWLiquidacionCorreccion.INTERES_JURIDICO);
                                            listaHelper.render(request,out);
                                                 */
                                                textHelper.setNombre(AWLiquidacionCorreccion.INTERES_JURIDICO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(AWLiquidacionCorreccion.INTERES_JURIDICO);
                                                textHelper.render(request, out);

                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>




                            <%-- Sof:Radio para habilitar deshabilitar datos de antiguo sistema --%>

                            <%-- Sof:Radio --%>
                            <%--
                            <table width="100%" class="camposform">

<tr>
  <td width="20"> <img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15" />
  </td>
  <td width="20">

<%

  RadioHelper local_RadioHelper;
  local_RadioHelper = new RadioHelper();


%>

<%
   String local_RadioHelperDefaultValue;
   local_RadioHelperDefaultValue = (String)session.getAttribute( "ANTIGUOSISTEMA_ENABLED" );

   if( null == local_RadioHelperDefaultValue ) {
     // local_RadioHelper.setFuncion( "onClick='javasscript:Select'" );
     session.setAttribute( "ANTIGUOSISTEMA_ENABLED", "NO" );
   } // if

%>

<%


local_RadioHelper.setId(      "ANTIGUOSISTEMA_ENABLED" );
local_RadioHelper.setNombre(  "ANTIGUOSISTEMA_ENABLED" );
local_RadioHelper.setValordefecto(  "NO" );

try {

  local_RadioHelper.render( request, out );

}
catch(HelperException re){

  out.println( "ERROR " + re.getMessage() );

} // try

%>
  </td>
  <td>No </td>
  <td width="16">
<%

local_RadioHelper.setId(      "ANTIGUOSISTEMA_ENABLED" );
local_RadioHelper.setNombre(  "ANTIGUOSISTEMA_ENABLED" );
local_RadioHelper.setValordefecto(  "SI" );

try {

  local_RadioHelper.render( request, out );

}
catch(HelperException re){

  out.println( "ERROR " + re.getMessage() );

} // try

%>
  </td>
  <td>Si</td>
</tr>
</table>

                            --%>

                            <%-- eof:Radio --%>
























                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->

                            <!--  + + + + + + + + + + + + + + +  declaracion de helpers + + + + + + + + +  -->
                            <%
                                // helper para insertar datos relacionados con antiguo sistema
                                AntiguoSistemaHelper ash = new AntiguoSistemaHelper();
                                ash.setObtenerJSP(true);
                                ash.setProperties(request);
                                ash.setNLibroTipoAS(CDatosAntiguoSistema.LIBRO_TIPO_AS);

                                ash.setNLibroNumeroAS(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
                                ash.setNLibroPaginaAS(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
                                ash.setNLibroAnoAS(CDatosAntiguoSistema.LIBRO_ANO_AS);

                                ash.setNTomoNumeroAS(CDatosAntiguoSistema.TOMO_NUMERO_AS);
                                ash.setNTomoPaginaAS(CDatosAntiguoSistema.TOMO_PAGINA_AS);
                                ash.setNTomoMunicipioAS(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
                                ash.setNTomoAnoAS(CDatosAntiguoSistema.TOMO_ANO_AS);

                                ash.setNDocumentoFechaAS(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
                                ash.setNDocumentoTipoAS(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
                                ash.setNDocumentoNumeroAS(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
                                ash.setNDocumentoComentarioAS(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

                                ash.setNPrefijoOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS);
                                ash.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
                                ash.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
                                ash.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
                                ash.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
                                ash.setNIdVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
                                ash.setNNomVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
                                ash.setNIdOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
                                ash.setNNumOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
                                ash.setNIdDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO);
                                ash.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
                                ash.setNNumOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN);
                                ash.setNTipoNomOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN);
                                ash.setNTipoOficinaHiddenn(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN);
                                ash.setNIdOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN);

                                ash.setLocal_OficinasHelperCambioNombreFormularioEnabled(true);
                                ash.setLocal_OficinasHelperCambioNombreFormularioValue("CORRECCION");


                            %>

                            <%    // helper para listar los datos de antiguo sistema en sesion
                                // no necesario
                                String ocultarAntSist = request.getParameter(WebKeys.OCULTAR);

                                // descomentar si se quiere que la region se oculte
                                if (null == ocultarAntSist) {

                                    ocultarAntSist = (String) session.getAttribute(WebKeys.OCULTAR);

                                    if (null == ocultarAntSist) {
                                        ocultarAntSist = "TRUE";
                                    }
                                } else {
                                    ocultarAntSist = (String) session.getAttribute(WebKeys.OCULTAR);
                                    session.setAttribute(WebKeys.OCULTAR, ocultarAntSist);
                                }

                            %>

                            <!-- Fin Mostrar helper de antiguo sistema -->








                            <%--



















        OficinaHelper ofHelper=new OficinaHelper();
        ofHelper.obtenerDelJSP( true );

        ofHelper.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS);
        ofHelper.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
        ofHelper.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS);
        ofHelper.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);
        ofHelper.setNIdVereda( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA );
        ofHelper.setNNomVereda( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA );

        ofHelper.setNIdDocumento( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO );
        ofHelper.setNNomDocumento( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO );

        ofHelper.setNIdOficina( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA );
        /*
        ofHelper.setNNumOficina(  );

        ofHelper.setNIdOficina( CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA );
        ofHelper.setNIdOficinaHidden( CDatosAntiguoSistema.Docu );
        ofHelper.setNNumOficina();
        ofHelper.setNNumOficinaHidden();
        ofHelper.setNTipoNomOficinaHidden();
        ofHelper.setNTipoOficinaHiddenn();
        ofHelper.setN

    private String nIdDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO;
    private String nNomDocumento = CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO;
    private String nIdOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA;
    private String nNumOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM;
    private String nPrefijoOficina = CFolio.ANOTACION_OFICINA_DOCUMENTO;
        */
















    ASHelper.setOficinaHelper(ofHelper);
        // helper para listar los datos de antiguo sistema en sesion
        // no necesario

        String ocultarAntSist = request.getParameter( WebKeys.OCULTAR );

        // descomentar si se quiere que la region se oculte
        if( null == ocultarAntSist ){

                ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);

                if( null == ocultarAntSist ){
                        ocultarAntSist = "TRUE";
                }
        }
        else {
            ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
                session.setAttribute(WebKeys.OCULTAR,ocultarAntSist);
        }

                            --%>


                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +   -->

                            <script type="text/javascript">
                                // Libraries: FindObject BrowserCompatible

                                // Example: obj = findObj("image1");
                                function findObj(theObj, theDoc)
                                {
                                    var p, i, foundObj;

                                    if (!theDoc)
                                        theDoc = document;
                                    if ((p = theObj.indexOf("?")) > 0 && parent.frames.length)
                                    {
                                        theDoc = parent.frames[theObj.substring(p + 1)].document;
                                        theObj = theObj.substring(0, p);
                                    }
                                    if (!(foundObj = theDoc[theObj]) && theDoc.all)
                                        foundObj = theDoc.all[theObj];
                                    for (i = 0; !foundObj && i < theDoc.forms.length; i++)
                                        foundObj = theDoc.forms[i][theObj];
                                    for (i = 0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
                                        foundObj = findObj(theObj, theDoc.layers[i].document);
                                    if (!foundObj && document.getElementById)
                                        foundObj = document.getElementById(theObj);

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

                                LocalForm.prototype.submitForm = function () {
                                    formObject = findObj(this.formName);
                                    formObject.submit();
                                }

                                LocalForm.prototype.setFormAction = function (formAction) {
                                    formObject = findObj(this.formName);
                                    formObject.action = formAction;
                                }

                                LocalForm.prototype.setValue = function (elementName, elementValue) {
                                    formObject = findObj(this.formName);

                                    if (formObject == null)
                                        return;

                                    eval("formObject." + elementName + ".value" + "=" + "elementValue");
                                }

                            </script>
                            <script type="text/javascript">
                                // local form dependant resources

                                var actionField = "<%=WebKeys.ACCION%>";
                                var PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION = "<%=AWLiquidacionCorreccion.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION%>";
                            </script>

                            <script type="text/javascript">

                                function js_OnRepeaterAction(formName, actionValue) {

                                    var htmlForm;
                                    htmlForm = new LocalForm(formName);
                                    htmlForm.setValue(actionField, actionValue);
                                    // alert( findObj('FORMNAME').ACCION.value );
                                    htmlForm.submitForm();

                                }

                                // TODO: cambiar por browser compatibility

                                function js_OnRegionAntiguoSistemaAction_ShowHide(formName, actionValue, param_ShowHide) {

                                    var htmlForm;
                                    htmlForm = new LocalForm(formName);
                                    // htmlForm.setFormAction( 'correccion.confrontacion.view' ); //correccion.confrontacion.view
                                    htmlForm.setValue(actionField, actionValue);
                                    // alert( findObj('FORMNAME').ACCION.value );
                                    htmlForm.setValue('OCULTAR', param_ShowHide);
                                    htmlForm.submitForm();

                                }

                                function oficinas(nombre, valor, dimensiones)
                                {
                                    document.CORRECCION.ACCION.value = '<%=CSolicitudRegistro.PRESERVAR_INFO%>';
                                    var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
                                    var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
                                    var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
                                    document.getElementById('tipo_oficina').value = valor + "_ID_TIPO";
                                    document.getElementById('tipo_nom_oficina').value = valor + "_TIPO";
                                    document.getElementById('numero_oficina').value = valor + "_NUM";
                                    document.getElementById('id_oficina').value = valor + "_ID_OFICINA";
                                    popup = window.open(nombre + '?<%=AWOficinas.ID_DEPTO%>=' + idDepto + '&<%=AWOficinas.ID_MUNIC%>=' + idMunic + '&<%=AWOficinas.ID_VEREDA%>=' + idVereda, valor, dimensiones);
                                    popup.focus();
                                }

                                function locacion(nombre, valor, dimensiones) {
                                    document.getElementById('id_depto').value = valor + "_ID_DEPTO";
                                    document.getElementById('nom_Depto').value = valor + "_NOM_DEPTO";
                                    document.getElementById('id_munic').value = valor + "_ID_MUNIC";
                                    document.getElementById('nom_munic').value = valor + "_NOM_MUNIC";
                                    document.getElementById('id_vereda').value = valor + "_ID_VEREDA";
                                    document.getElementById('nom_vereda').value = valor + "_NOM_VEREDA";
                                    popup = window.open(nombre, valor, dimensiones);
                                    popup.focus();
                                }

                                function setTipoOficina() {
                                    document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value = "";
                                    document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value = "";
                                    document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value = "";

                                }

                            </script>




                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
































                            <%-- SOF: DATOS ANTIGUO SISTEMA --%>

                            <script type="text/javascript">

                                var ultimo_campo_editado;
                                var vinculo;

                                function ocultarAntiguoSistema(text) {
                                    document.CORRECCION.VER_ANTIGUO_SISTEMA.value = text;
                                    cambiarAccion('VER_ANTIGUO_SISTEMA');
                                }

                                function campoactual(id) {
                                    if (document.getElementById("ultimo_campo_editado") != null) {
                                        document.getElementById("ultimo_campo_editado").value = id;
                                    }
                                }

                                function locacion(nombre, valor, dimensiones) {
                                    document.getElementById('id_depto').value = valor + "_ID_DEPTO";
                                    document.getElementById('nom_Depto').value = valor + "_NOM_DEPTO";
                                    document.getElementById('id_munic').value = valor + "_ID_MUNIC";
                                    document.getElementById('nom_munic').value = valor + "_NOM_MUNIC";
                                    document.getElementById('id_vereda').value = valor + "_ID_VEREDA";
                                    document.getElementById('nom_vereda').value = valor + "_NOM_VEREDA";
                                    popup = window.open(nombre, valor, dimensiones);
                                    popup.focus();
                                }

                                function setTipoOficina() {
                                    /*	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
                                     document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
                                     document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
                                     */
                                }

                            </script>


                            <%
                                /*
                            * @author : CTORRES
                            * @change : se declara la variable al principio del codigo
                            * Caso Mantis : 12291
                            * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                            * 
                                 */
                                style = "camposformtext";

                                TextAreaHelper textArea = new TextAreaHelper();

                                String ocultarAS = (String) session.getAttribute("VER_ANTIGUO_SISTEMA");
                                if (ocultarAS == null) {
                                    ocultarAS = "TRUE";
                                } else {
                                    session.setAttribute("VER_ANTIGUO_SISTEMA", ocultarAS);
                                }

                                Boolean readOnly = (Boolean) session.getAttribute(WebKeys.TURNO_ANTERIOR_OK);
                                /*
                            * @author : CTORRES
                            * @change : se declara la variable al principio del codigo
                            * Caso Mantis : 12291
                            * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                            * 
                                 */
                                read = false;
                                if (readOnly != null && readOnly.booleanValue()) {
                                    read = true;
                                    style = "campositem";
                                }


                            %>


                            <%-- fields --%>
                            <%--
                                    <input name="Depto" type="hidden" id="id_depto" value="">
                                    <input name="Depto" type="hidden" id="nom_Depto" value="">
                                    <input name="Mpio" type="hidden" id="id_munic" value="">
                                    <input name="Mpio" type="hidden" id="nom_munic" value="">
                                    <input name="Ver" type="hidden" id="id_vereda" value="">
                                    <input name="Ver" type="hidden" id="nom_vereda" value="">
                            --%>
                            <%-- fields --%>


                            <hr class="linehorizontal" />
                            <%--
                                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                                                    <tr>
                                                                            <td width="12"><img name="sub_r1_c1"
                                                                                    src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
                                                                                    width="12" height="22" border="0" alt=""></td>
                                                                            <td
                                                                                    background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
                                                                                    class="bgnsub">Datos Opcionales para Antiguo Sistema</td>
                                                                            <td width="16" class="bgnsub"><img
                                                                                    src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif"
                                                                                    width="16" height="21"></td>
                                                                            <td width="16" class="bgnsub"><img
                                                                                    src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
                                                                                    width="16" height="21"></td>
                                                                            <td width="15"><img name="sub_r1_c4"
                                                                                    src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
                                                                                    width="15" height="22" border="0" alt=""></td>
                                                                    </tr>
                                                            </table>

                                <table border="0" width="100%" cellpadding="0" cellspacing="2"
                                        id="OCULTAR">
                                        <%if(ocultarAS.equals("FALSE")){%>
                                        <tr>
                                                <td>&nbsp;
                                                </td>
                                        </tr>
                                        <tr>
                                                <td></td>
                                                <td width="16"><a
                                                        href="javascript:ocultarAntiguoSistema('TRUE');campoactual('OCULTAR');"><img
                                                        id="MINIMIZAR"
                                                        src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
                                                        width="16" height="16" border="0"></a></td>
                                                <img id="OCULTAR_img"
                                                        src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
                                                        class="imagen_campo">
                                        </tr>
                                        <%}else{%>
                                        <tr>
                                                <td align="right" class="campostip04">Haga click para maximizar
                                                los datos de Antiguo Sistema</td>
                                                <td width="16"><a
                                                        href="javascript:ocultarAntiguoSistema('FALSE');campoactual('OCULTAR');"><img
                                                        img id="MAXIMIZAR"
                                                        src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
                                                        width="16" height="16" border="0"></a></td>
                                                <img id="OCULTAR_img"
                                                        src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
                                                        class="imagen_campo">
                                        </tr>
                                        <%}%>
                                </table>
                                <!-- Mostrar helper de antiguo sistema --> <%if(ocultarAS.equals("FALSE")){%>

                                <%
                            AntiguoSistemaHelper ash = new AntiguoSistemaHelper();
                                ash.setLocal_FormularioNombre( "CORRECCION" );
                                ash.setObtenerJSP(true);
                            ash.setProperties(request);
                            ash.setNLibroTipoAS(CDatosAntiguoSistema.LIBRO_TIPO_AS);
                        ash.setNLibroNumeroAS(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
                        ash.setNLibroPaginaAS(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
                        ash.setNLibroAnoAS(CDatosAntiguoSistema.LIBRO_ANO_AS);

                        ash.setNTomoNumeroAS(CDatosAntiguoSistema.TOMO_NUMERO_AS);
                        ash.setNTomoPaginaAS(CDatosAntiguoSistema.TOMO_PAGINA_AS);
                        ash.setNTomoMunicipioAS(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
                        ash.setNTomoAnoAS(CDatosAntiguoSistema.TOMO_ANO_AS);

                        ash.setNDocumentoFechaAS(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
                        ash.setNDocumentoTipoAS(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
                        ash.setNDocumentoNumeroAS(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
                        ash.setNDocumentoComentarioAS(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

                                                ash.setNPrefijoOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS);
                        ash.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
                        ash.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
                        ash.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
                        ash.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
                        ash.setNIdVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
                        ash.setNNomVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
                        ash.setNIdOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
                        ash.setNNumOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
                        ash.setNIdDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO);
                        ash.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
                        ash.setNNumOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN);
                        ash.setNTipoNomOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN);
                        ash.setNTipoOficinaHiddenn(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN);
                        ash.setNIdOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN);
                                                if(read){
                                                        ash.setReadOnly(read);
                                                }
                        ash.render(request, out);
                                        %> <!-- Fin Mostrar helper de antiguo sistema --> <%} else {%> <%}%>
                                <br />


                            <%--
                            <table width="100%" class="camposform">
                                    <tr>
                                            <td width="20"><img
                                                    src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
                                                    width="20" height="15"></td>
                                            <td>Comentario</td>
                                    </tr>
                                    <tr>
                                            <td>&nbsp;</td>
                                            <td><% try {
                                    textArea.setNombre("COMENTARIO");
                                    textArea.setCols("60");
                                    textArea.setRows("5");
                                        textArea.setCssClase(style);
                                        textArea.setId("COMENTARIO");
                                                            textArea.setReadOnly(read);
                                                            textArea.render(request,out);
                                                    }
                                                            catch(HelperException re){
                                                            out.println("ERROR " + re.getMessage());
                                                    }
                                            %></td>
                                    </tr>
                            </table>
                            --%>

                            <%-- EOF: DATOS ANTIGUO SISTEMA --%>































                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->



                            <br />
                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->
                            <!-- (v2) DATOS ANTIGUO SISTEMA  -->

                            <!-- + + + + + + + + + + + + + + +  -->
                            <!--  sub-region: type="title"  -->

                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>

                                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para Antiguo Sistema</td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                <input type="hidden" name="<%=WebKeys.OCULTAR%>" id="<%=WebKeys.OCULTAR%>">

                                <%

                                    if ("FALSE".equals(ocultarAntSist)) {

                                %>
                                <td width="16">
                                    <a href="#">
                                        <img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="javascript:js_OnRegionAntiguoSistemaAction_ShowHide('CORRECCION', PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION, 'TRUE')" width="16" height="16" border="0" />
                                    </a>
                                </td>
                                <%
                                } else {
                                %>
                                <td width="170" class="contenido">Haga click para maximizar</td>
                                <td width="16">
                                    <a href="#">
                                        <img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="javascript:js_OnRegionAntiguoSistemaAction_ShowHide('CORRECCION', PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION, 'FALSE')" width="16" height="16" border="0" />
                                    </a>
                                </td>
                                <%
                                    }
                                %>
                                </tr>
                            </table>
                            <br />
                            <!-- + + + + + + + + + + + + + + +  -->
                            <!--  sub-region: type="select"  -->
                            <!--  [no necesitado] -->

                            <!-- + + + + + + + + + + + + + + +  -->
                            <!--  sub-region: type="buttons"  -->
                            <!--  [no necesitado] -->

                            <!-- + + + + + + + + + + + + + + +  -->
                            <!--  sub-region: type="item-set"  -->

                            <input name="Depto" type="hidden" id="id_depto" value="">
                            <input name="Depto" type="hidden" id="nom_Depto" value="">
                            <input name="Mpio" type="hidden" id="id_munic" value="">
                            <input name="Mpio" type="hidden" id="nom_munic" value="">
                            <input name="Ver" type="hidden" id="id_vereda" value="">
                            <input name="Ver" type="hidden" id="nom_vereda" value="">
                            <% // (create/update)

                                if ("FALSE".equals(ocultarAntSist)) {

                                    try {

                                        ash.render(request, out);

                                    } catch (HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                    }

                                }

                            %>
                            <br />
                            <!-- (v2) + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->














                            <hr class="linehorizontal">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <br>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="179">Tipo de Identificaci&oacute;n</td>
                                    <td width="211">
                                        <% try {
                                                List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                                                if (docs == null) {
                                                    docs = new Vector();
                                                }
                                                docHelper.setOrdenar(false);
                                                docHelper.setTipos(docs);
                                                docHelper.setNombre(CCiudadano.TIPODOC);
                                                docHelper.setCssClase("camposformtext");
                                                docHelper.setId(CCiudadano.TIPODOC);
                                                docHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td width="146">N&uacute;mero</td>
                                    <td width="212">
                                        <% try {
                                                textHelper.setNombre(CCiudadano.IDCIUDADANO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCiudadano.DOCUMENTO);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>Primer Apellido</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CCiudadano.APELLIDO1);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCiudadano.APELLIDO1);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td>Segundo Apellido</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CCiudadano.APELLIDO2);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCiudadano.APELLIDO2);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>Nombre</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CCiudadano.NOMBRE);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCiudadano.NOMBRE);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td>Teléfono</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CCiudadano.TELEFONO);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(CCiudadano.TELEFONO);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                            <hr class="linehorizontal">


                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>N&uacute;mero de copias</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(AWLiquidacionCorreccion.NUMERO_COPIAS_CORRECCION);
                                                textHelper.setCssClase("camposformtext");
                                                textHelper.setId(AWLiquidacionCorreccion.NUMERO_COPIAS_CORRECCION);
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
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>

                                    <td  width="140">
                                        <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWLiquidacionCorreccion.LIQUIDAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" id="btn_aceptar_liquidar"></a>
                                    </td>

                                    <td>&nbsp;</td>
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
