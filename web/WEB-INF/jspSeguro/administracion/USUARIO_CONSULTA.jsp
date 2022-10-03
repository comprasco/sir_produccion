<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioCirculo" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles" %>


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
    List usuarios = (List) session.getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);

    if (usuarios == null) {
        usuarios = new ArrayList();
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

//CIRCULOS DE USUARIO PARA USARLOS EN EL COMBOBOX	
    try {
        if (session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO) == null) {
            Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
            List circulos = usuario.getUsuarioCirculos();
            List elementos = new ArrayList();
            for (Iterator iter = circulos.iterator(); iter.hasNext();) {
                UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
                idCirculoString = circulo.getIdCirculo();
                if (idCirculoString.equals("SNR")) {
                    elementos.add(new ElementoLista("SNR", "SNR"));
                } else {
                    if (departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)) {
                        idCirculoInt = Integer.parseInt(idCirculoString);
                        nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                        if (nombreCirculoDepartamento != "") {
                            elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                        }
                    }
                }
                //UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
                //elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getCirculo().getNombre()+"-"+circulo.getIdCirculo()));
            }
            session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementos);
        }
    } catch (Exception e) {
        System.out.print("Error : " + e.getMessage());
    }

//mirar los circulos obtenidos del servicio
    List circulosAdministradorNacional = (List) session.getAttribute(AWAdministracionFenrir.CIRCULOS_ADMINISTRADOR_NACIONAL);
    if (circulosAdministradorNacional != null) {
        if (circulosAdministradorNacional.size() != 0) {
            List elementos = new ArrayList();
            for (Iterator iter = circulosAdministradorNacional.iterator(); iter.hasNext();) {
                gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                idCirculoString = circulo.getIdCirculo();
                if (idCirculoString.equals("SNR")) {
                    elementos.add(new ElementoLista("SNR", "SNR"));
                } else {
                    if (departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)) {
                        idCirculoInt = Integer.parseInt(idCirculoString);
                        nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                        if (nombreCirculoDepartamento != "") {
                            elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                        }
                    }
                }

            }
            session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementos);
        }
    }

    List circulos = (List) session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
    ListaElementoHelper circuloHelper = new ListaElementoHelper();
    circuloHelper.setTipos(circulos);
    
    
  /**
     * DNilson226 VALIDAR SI EL USUARIO ES ADMINISTRADOR NACIONAL
     */
    List rolesUsuario = (List)session.getAttribute(WebKeys.LISTA_ROLES);
    if( null == rolesUsuario ) {
      rolesUsuario = new java.util.ArrayList();
    }
    boolean isAdministradorNacional = false;


    java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
    org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;

    for( ;local_RolesListIterator.hasNext(); ) {
            local_RolesListElement =(org.auriga.core.modelo.transferObjects.Rol)local_RolesListIterator.next();
            String rol = local_RolesListElement.getRolId();
            if( rol.equals(CRoles.ADMINISTRADOR_NACIONAL) ) {
                    isAdministradorNacional = true;
                    break;
            }
    }
    
   System.out.println("<<>>>>DNilson226 Vlr isAdministradorNacional: " +isAdministradorNacional + " <<>>>>>");  

//RECARGAR PAGINA
    boolean recarga = true;
    Boolean recarga_temp = (Boolean) session.getAttribute(WebKeys.RECARGA);
    if (recarga_temp != null) {
        recarga = recarga_temp.booleanValue();
    }


%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">

    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }

    function cambiarAccionAndSend(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa </td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consulta de Usuarios </td>
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>

                <form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR">
                    <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.USUARIOS_CONSULTAR_POR_CIRCULO%>" value="<%= AWAdministracionFenrir.USUARIOS_CONSULTAR_POR_CIRCULO%>">

                    <tr>
                        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="camposform">
                                <br>
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Opciones</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>                
                            </table>

                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="180">C&iacute;rculo</td>
                                    <td>
                                        <% try {
                                                circuloHelper.setNombre(CCirculo.ID_CIRCULO);
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.setId(CCirculo.ID_CIRCULO);
                                                circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionFenrir.USUARIOS_CONSULTAR_POR_CIRCULO + "')\"");
                                                circuloHelper.render(request, out);
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
                                        <!-- DNilson226
                                        <input type="button" value="Click me">
                                        <td>&nbsp;</td>
                                        -->
                                        <% if(isAdministradorNacional){ %>
                                            <input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionFenrir.USUARIOS_NUEVO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_crear.gif" width="139" height="21" border="0">                                            
                                        <% } else { %>
                                            <!-- <input name="imageField" type="image" onClick="cambiarAccion('%= AWAdministracionFenrir.USUARIOS_NUEVO%');"   src="%=request.getContextPath()%/jsp/images/btn_crear.gif" width="139" height="21" border="0" disabled> -->
                                            <td>&nbsp;</td>
                                        <% }%>
                                    </td>
                                    <td>
                                        <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionFenrir.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Resultados de la Consulta </td>
                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></span></td>
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
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Listado</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
            </table>

            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                    <td>Nombre</td>
                    <td>Nombre de usuario</td>
                    <td>Estado</td>
                    <td align="center">Editar</td>
                    <td align="center">Activar</td>
                    <td align="center">Inactivar</td>
                </tr>
                <%

                    if (usuarios != null) {
                        for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
                            Usuario usuario = (Usuario) iter.next();
                %>
                <tr>
                    <td>&nbsp;</td>
                    <td class="camposformtext_noCase"><%= usuario.getNombre() + " " + ((usuario.getApellido1() == null) ? "" : usuario.getApellido1()) + " " + ((usuario.getApellido2() == null) ? "" : usuario.getApellido2())%></td>
                    <td class="camposformtext_noCase"><%= usuario.getUsername()%></td>
                    <td class="campositem"><%= (usuario.isActivo()) ? "ACTIVO" : "INACTIVO"%></td>

                    <%/**
                         * @autor Geremias Ortiz
                         * @requerimiento 317090 Telebucaramanga
                         * @descripcion se crea un nuevo input con name
                         * TIPO_JUSTIFICACION, para indicarle al formulario de
                         * justificación, que opciones cargar en el SELECT de
                         * tipo de justificación.
                         */
                    %>

                <form action="administracionFenrir.do" method="post" name="EDITAR" id="EDITAR">
                    <td width="40" align="center">
                        <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.USUARIOS_MODIFICAR%>" value="<%= AWAdministracionFenrir.USUARIOS_MODIFICAR%>">
                        <input  type="hidden" name="<%= CUsuario.ID_USUARIO%>" id="<%= usuario.getIdUsuario()%>" value="<%= usuario.getIdUsuario()%>">
                        <input  type="hidden" name="<%= CUsuario.LOGIN_USUARIO%>" id="<%= usuario.getUsername()%>" value="<%= usuario.getUsername()%>">
                        <input  type="hidden" name="<%= CUsuario.APELLIDO1_USUARIO%>" id="<%= usuario.getApellido1()%>" value="<%= usuario.getApellido1()%>">
                        <input  type="hidden" name="<%= CUsuario.APELLIDO2_USUARIO%>" id="<%= usuario.getApellido2()%>" value="<%= usuario.getApellido2()%>">
                        <input  type="hidden" name="<%= CUsuario.NOMBRE_USUARIO%>" id="<%= usuario.getNombre()%>" value="<%= usuario.getNombre()%>">
                        <input  type="hidden" name="<%= CUsuario.TIPO_JUSTIFICACION%>" value="<%= CUsuario.USUARIO_EDITAR%>">
                        <input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0">
                    </td>
                </form>

                <form action="administracionFenrir.do" method="post" name="HABILITAR" id="HABILITAR">
                    <td width="40" align="center">
                        <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.USUARIOS_HABILITAR%>" value="<%= AWAdministracionFenrir.USUARIOS_HABILITAR%>">
                        <input  type="hidden" name="<%= CUsuario.ID_USUARIO%>" id="<%= usuario.getIdUsuario()%>" value="<%= usuario.getIdUsuario()%>">
                        <input  type="hidden" name="<%= CUsuario.LOGIN_USUARIO%>" id="<%= usuario.getUsername()%>" value="<%= usuario.getUsername()%>">
                        <input  type="hidden" name="<%= CUsuario.TIPO_JUSTIFICACION%>" value="<%= CUsuario.USUARIO_ACTIVAR%>">
                        <input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_mini_activar.gif" width="35" height="13" border="0">
                    </td>
                </form>
                <form action="administracionFenrir.do" method="post" name="DESHABILITAR" id="DESHABILITAR">
                    <td width="40" align="center">
                        <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.USUARIOS_DESHABILITAR%>" value="<%= AWAdministracionFenrir.USUARIOS_DESHABILITAR%>">
                        <input  type="hidden" name="<%= CUsuario.ID_USUARIO%>" id="<%= usuario.getIdUsuario()%>" value="<%= usuario.getIdUsuario()%>">
                        <input  type="hidden" name="<%= CUsuario.LOGIN_USUARIO%>" id="<%= usuario.getUsername()%>" value="<%= usuario.getUsername()%>">
                        <input  type="hidden" name="<%= CUsuario.TIPO_JUSTIFICACION%>" value="<%= CUsuario.USUARIO_INACTIVAR%>">
                        <input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_desactivar.gif" width="35" height="13" border="0">
                    </td>
                </form>
    </tr>
    <%
            }
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
<%if (recarga) {%>
<script>cambiarAccionAndSend('<%= AWAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO%>');</script>
<%}%>
