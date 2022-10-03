<%@page import = "java.util.*"%>
<%@page import = "gov.sir.core.web.helpers.comun.*"%>
<%@page import = "org.auriga.core.web.*"%>
<%@page import = "gov.sir.core.web.WebKeys"%>
<%@page import = "gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.web.acciones.comun.AWNotas"%>
<%
    ListaElementoHelper tiposNotaHelper = new ListaElementoHelper();
    ListarNotasPasadas notasHelper = new ListarNotasPasadas();
    notasHelper.setMostrarImpresion(true);

    List listaNotasInformativas = (List) session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
    List elementosTipoNota = new Vector();

    if (listaNotasInformativas != null) {
        Iterator itTipos = listaNotasInformativas.iterator();

        while (itTipos.hasNext()) {
            TipoNota tipoNota = (TipoNota) itTipos.next();
            elementosTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), tipoNota.getIdTipoNota() + " - " + tipoNota.getNombre()));
        }
    }

    tiposNotaHelper.setCssClase("camposformtext");
    tiposNotaHelper.setId(CTipoNota.ID_TIPO_NOTA);
    tiposNotaHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
    tiposNotaHelper.setTipos(elementosTipoNota);

    TextAreaHelper helper = new TextAreaHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script>
    function verNota(nombre,valor,dimensiones){
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
    }
    function submitform(){
        document.NOTAS_INF.ACCION.value='<%=AWNotas.COLOCAR_DESCRIPCION%>';
        document.NOTAS_INF.submit();
    }
    function regresar(){
        document.NOTAS_INF.ACCION.value='<%=AWNotas.REGRESAR%>';
        document.NOTAS_INF.submit();
    }
    function cancelar(){
        document.NOTAS_INF.ACCION.value='<%=AWNotas.CANCELAR_AGREGAR_NOTAS_INFORMATIVAS%>';
        document.NOTAS_INF.submit();
    }
    function imprimirNotaInformativa(posicion){
        document.NOTAS_INF.ACCION.value='<%=AWNotas.IMPRIMIR_NOTA_INFORMATIVA%>';
        document.NOTAS_INF.<%=WebKeys.POSICION%>.value=posicion;
        document.NOTAS_INF.submit();
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
            <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Agregar Nota Informativa </td>
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
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
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
                            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Notas Informativas </td>
                            <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                            <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_notasagregar.gif" width="16" height="21"></td>
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
                    <br>
                    <form action="notas.do" method="post" name="NOTAS_INF" id="NOTAS_INF">
                        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.comun.AWNotas.AGREGAR_NOTA%>">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr> 
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">Nota</td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>              
                        <br>
                        <table width="100%" class="camposform">
                            <tr> 
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Escoger Tipo de Nota</td>
                                <td>
                                    <%
                                        try {
                                            tiposNotaHelper.setFuncion("onChange=\"submitform();\"");
                                            tiposNotaHelper.render(request, out);
                                        } catch (HelperException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
                                        try {
                                            helper.setCols("60");
                                            helper.setCssClase("campositem");
                                            helper.setId(CTipoNota.DESCRIPCION);
                                            helper.setNombre(CTipoNota.DESCRIPCION);
                                            helper.setRows("3");
                                            helper.setReadOnly(true);
                                            helper.render(request, out);
                                        } catch (HelperException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" class="camposform">
                            <tr> 
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Descripci&oacute;n</td>
                            </tr>
                            <tr> 
                                <td>&nbsp;</td>
                                <td>
                                    <%
                                        try {
                                            helper.setCols("100");
                                            helper.setReadOnly(false);
                                            helper.setCssClase("camposformtext");
                                            helper.setId(CNota.DESCRIPCION);
                                            helper.setNombre(CNota.DESCRIPCION);
                                            helper.setRows("10");
                                            helper.render(request, out);
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
                                <td width="139"><input name="imageField2" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></td>
                                <td width="139"><a href='javascript:regresar();'><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"   width="139" height="21" border="0" ></a></td>
                                <td><a href='javascript:cancelar();'><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"   width="139" height="21" border="0" ></a></td>
                            </tr>
                        </table>
                        <hr class="linehorizontal">              
                        <%
                            try {
                                notasHelper.render(request, out);
                            } catch (HelperException re) {
                                out.println("ERROR " + re.getMessage());
                            }
                        %>
                        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
            </tr>
            <tr> 
                <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
            </tr>
        </table>
        </form>
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