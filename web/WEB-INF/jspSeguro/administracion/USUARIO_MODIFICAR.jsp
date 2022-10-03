<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes"%>
<%@page import="gov.sir.core.negocio.modelo.JustificaAdm"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CJustificaAdm" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CArchivosJustifica" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles" %>
<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/js/paging.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<style type="text/css">    
    .pg-normal {
        color: black;
        font-weight: normal;
        text-decoration: none;    
        cursor: pointer;
        font-size: 15;
    }
    .pg-selected {
        color: black;
        font-weight: bold;        
        text-decoration: underline;
        cursor: pointer;
        font-size: 15;
    }
    #btn_paginator {
        font-size: 15;
        cursor: pointer;
    }

</style>
<%
    TextHelper textHelper = new TextHelper();
    TextAreaHelper textAreaHelper = new TextAreaHelper();

    ListaElementoHelper rolesHelper = new ListaElementoHelper();
    rolesHelper.setTipoMultiple(true);
    List roles = (List) application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);

    List temp = new java.util.ArrayList();
    java.util.Iterator itTemp = roles.iterator();
    while (itTemp.hasNext()) {
        ElementoLista rol = (ElementoLista) itTemp.next();

        if (!(rol.getId().equals(CRoles.SIR_ROL_DECISOR)
                || rol.getId().equals(CRoles.SIR_ROL_IMPRESION))) {
            temp.add(rol);
        }

    }

    rolesHelper.setTipos(temp);

    List rolesUsuario = (List) session.getAttribute(WebKeys.LISTA_ROLES);
    if (null == rolesUsuario) {
        rolesUsuario = new java.util.ArrayList();
    }
    boolean isAdministradorNacional = false;

    java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
    org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;

    for (; local_RolesListIterator.hasNext();) {
        local_RolesListElement = (org.auriga.core.modelo.transferObjects.Rol) local_RolesListIterator.next();
        String rol = local_RolesListElement.getRolId();
        if (rol.equals(CRoles.ADMINISTRADOR_NACIONAL)) {
            isAdministradorNacional = true;
            break;
        }
    }

//SI ES ADMINISTRADOR NACIONAL SE MOSTRARÁN TODOS LOS CIRCULOS PARA ESCOGER EN DÓNDE CREAR EL USUARIO
//SINO ES ADMINISTRADOR NACIONAL MOSTRARÁ ÚNICAMENTE EL CIRCULO AL QUE PERTENECE EL USUARIO
    List elementos = new java.util.ArrayList();
    if (isAdministradorNacional) {
        List circulos = (List) application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        for (java.util.Iterator iter = circulos.iterator(); iter.hasNext();) {
            gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
            elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));
        }
    } else {
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        List circulosUsuario = usuario.getUsuarioCirculos();
        java.util.Iterator itCirculosCiudadano = circulosUsuario.iterator();
        gov.sir.core.negocio.modelo.UsuarioCirculo usuarioCirculoTemp = null;
        gov.sir.core.negocio.modelo.Circulo circuloTemp = null;
        for (; itCirculosCiudadano.hasNext();) {
            usuarioCirculoTemp = (gov.sir.core.negocio.modelo.UsuarioCirculo) itCirculosCiudadano.next();
            circuloTemp = usuarioCirculoTemp.getCirculo();
            elementos.add(new ElementoLista(circuloTemp.getIdCirculo(), circuloTemp.getNombre()));
        }
    }

    ListaElementoHelper circuloHelper = new ListaElementoHelper();
    circuloHelper.setTipos(elementos);

    /**
     * @autor Geremias Ortiz
     * @requerimiento 317090 Telebucaramanga
     * @descripcion se crea y se llena la lista listaTipoNovedad, para cargar
     * las opciones del SELECT de tipo de justificación. Por otro lado se carga
     * la lista justificaciones_usuario, para traer las novedades de un usuario
     * en especifico.
     */
    ListaElementoHelper tipoNovedadHelper = new ListaElementoHelper();
    try {
        List listaTipoNovedad = null;
        List listaTipoJustificaciones = (List) session.getAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);
        if (listaTipoJustificaciones != null) {
            if (listaTipoJustificaciones.size() != 0) {
                System.out.println("Consulta de tipos de justificacion Lista en session");
                listaTipoNovedad = new java.util.ArrayList();
                for (Iterator iter = listaTipoJustificaciones.iterator(); iter.hasNext();) {
                    gov.sir.core.negocio.modelo.JustificaTipos justificaTipos = (gov.sir.core.negocio.modelo.JustificaTipos) iter.next();
                    System.out.println("Consulta de tipos de justificacion Iter " + justificaTipos.getTipIdTipo() + " y " + justificaTipos.getTipDescripcion());
                    listaTipoNovedad.add(new ElementoLista(justificaTipos.getTipIdTipo() + "", justificaTipos.getTipDescripcion()));
                }
            } else {
                listaTipoNovedad = new java.util.ArrayList();
                listaTipoNovedad.add(new ElementoLista("0", "Sin Valores"));
            }
        }

        tipoNovedadHelper.setTipos(listaTipoNovedad);

    } catch (Exception e) {
        System.out.print("Error : " + e.getMessage());
    }

    List justificaciones_usuario = (List) session.getAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);

    if (justificaciones_usuario == null) {
        justificaciones_usuario = new ArrayList();
    }


%>


<script type="text/javascript">
    /**
     * @autor Geremias Ortiz, Yeferson Martinez
     * @requerimiento 317090 Telebucaramanga
     * @descripcion se agrega una confirmación a la función cambiarAccion, 
     * y se agregan las funciones buscarXRango, textCounter, limpiarForm,
     * validarFecha, imprimir y fnExcelReport, para diferentes funcionalidades
     * como validación, impresión y exportación
     */

    function cambiarAccion(text) {
        if (confirm('Esta seguro de agregar la novedad y editar el usuario: '))
        {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
            document.BUSCAR.submit();
        }
    }

    function buscarXRango(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
    }

    function textCounter(field, cnt, maxlimit) {
        if (field.value.length >= 20) {
            document.getElementById("<%=CJustificaAdm.ADM_MAX_LENGTH%>").value = 0;
            document.getElementById(cnt).textContent = 0;
        }
        if (field.value.length < 20) {
            document.getElementById("<%=CJustificaAdm.ADM_MAX_LENGTH%>").value = maxlimit - field.value.length;
            document.getElementById(cnt).textContent = maxlimit - field.value.length;
        }
    }

    function limpiarForm() {
        document.getElementById("<%=CJustificaAdm.ADM_DESCRIPCION%>").value = "";
        document.getElementById("<%=CJustificaAdm.TIP_ID_TIPO%>").selectedIndex = 0;
    }

    function validarFecha(id) {
        if (document.getElementById(id).value.length > 0) {
            var index = document.getElementById(id).value.lastIndexOf('/') + 1;
            if (index != null) {
                var fin = document.getElementById(id).value.length;
                var texto = document.getElementById(id).value.substring(index, fin);
                if (texto.length != 4) {
                    alert('Fecha incorrecta');
                    document.getElementById(id).value = '';
                    document.getElementById(id).focus();
                }
            }
        }
    }

    function imprimir(i) {

        var Descripcion = document.getElementById('AdmDescripcion' + i).innerHTML;
        var Fecha = document.getElementById('AdmFecha' + i).innerHTML;
        var Usuario = document.getElementById('AdmUsuarioModifica' + i).innerHTML;
        var TipoNovedad = document.getElementById('AdmJustificaTipos' + i).innerHTML;
        var ventana = window.open('imprimir', '', '');
        ventana.document.write('<head>  <meta http-equiv="X-UA-Compatible" content="IE=7;IE=8;IE=9;IE=10;IE=11" />');
        ventana.document.write('<title></title></head>');
        ventana.document.write('<style>');
        ventana.document.write('table.gridtable {');
        ventana.document.write('font-family: verdana,arial,sans-serif;');
        ventana.document.write('font-size:11px;');
        ventana.document.write('color:#333333;');
        ventana.document.write('border-width: 1px;');
        ventana.document.write('border-color: #666666;');
        ventana.document.write('border-collapse: collapse;');
        ventana.document.write('margin: 0 auto;');
        ventana.document.write('}');
        ventana.document.write('table.gridtable th {');
        ventana.document.write('border-width: 1px;');
        ventana.document.write('padding: 8px;');
        ventana.document.write('border-style: solid;');
        ventana.document.write('border-color: #666666;');
        ventana.document.write('background-color: #dedede;');
        ventana.document.write('}');
        ventana.document.write('table.gridtable td {');
        ventana.document.write('border-width: 1px;');
        ventana.document.write('padding: 8px;');
        ventana.document.write('border-style: solid;');
        ventana.document.write('border-color: #666666;');
        ventana.document.write('background-color: #ffffff;');
        ventana.document.write('}');
        ventana.document.write('.nameSection, .tableSection {');
        ventana.document.write('text-align : center;');
        ventana.document.write('padding-bottom: 30px;');
        ventana.document.write('}');
        ventana.document.write('</style>');
        ventana.document.write('<body>');
        ventana.document.write('<img name="header_r1_c2" src="/SNR/jsp/images/header_r1_c2.gif" width="331" height="48" border="0" alt="">');
        ventana.document.write('<section class="nameSection">');
        ventana.document.write('<h1>Reporte de Justificaciones</h1>');
        ventana.document.write('</section>');
        ventana.document.write('<section class="tableSection">');
        ventana.document.write('<p>A continuación la información de la novedad seleccionada.</p>');
        ventana.document.write('<table class="gridtable">');
        ventana.document.write('<thead>');
        ventana.document.write('<th>Descripcion</th>');
        ventana.document.write('<th>Fecha</th>');
        ventana.document.write('<th>Usuario</th>');
        ventana.document.write('<th>Tipo Novedad</th>');
        ventana.document.write('</thead>');
        ventana.document.write('<tbody>');
        ventana.document.write('<tr>');
        ventana.document.write('<td>' + Descripcion + '</td>');
        ventana.document.write('<td>' + Fecha + '</td>');
        ventana.document.write('<td>' + Usuario + '</td>');
        ventana.document.write('<td>' + TipoNovedad + '</td>');
        ventana.document.write('</tr>');
        ventana.document.write('</tbody>');
        ventana.document.write('</table>');
        ventana.document.write('</section>');
        ventana.document.write('</div>');
        ventana.document.write('</body>');
        ventana.document.close();  //cerramos el documento
        ventana.print();  //imprimimos la ventana
        ventana.close();  //cerramos la ventana
    }

    function fnExcelReport() {
        var tab_text = "<table border='2px'><tr>";
        var textRange;
        var j = 0;
        tab = document.getElementById('ARCHIVOS_JUSTIFICA'); // id of table


        for (j = 0; j < tab.rows.length; j++)
        {
            tab_text = tab_text + tab.rows[j].innerHTML + "</tr>";
            //tab_text=tab_text+"</tr>";
        }

        tab_text = tab_text + "</table>";
        tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
        tab_text = tab_text.replace(/<img[^>]*>/gi, ""); // remove if u want images in your table
        tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

        var ua = window.navigator.userAgent;
        var msie = ua.indexOf("MSIE ");

        if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
        {
            txtArea1.document.open("txt/html", "replace");
            txtArea1.document.write(tab_text);
            txtArea1.document.close();
            txtArea1.focus();
            sa = txtArea1.document.execCommand("SaveAs", true, "Reporte Notas Informativas Usuarios.xls");

        } else                 //other browser not tested on IE 11
            sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));
        return (sa);
    }

</script>

<iframe id="txtArea1" style="display:none; visibility: hidden;"></iframe>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
                    <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Editar Usuario </td>
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
            <%/**
                 * @autor Geremias Ortiz
                 * @requerimiento 317090 Telebucaramanga
                 * @descripcion se crea el formulario nuevo con los campos de
                 * tipo de justificación, descripción y archivo adjunto. Por
                 * otro lado se crea la tabla con las justificaciones para el
                 * usuario y los campos de fecha para realizar el filtro.
                 */
            %>
            <form action="administracionFenrir.do" method="post" name="BUSCAR" id="BUSCAR" enctype="multipart/form-data">
                <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION%>" value="<%= AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION%>">
                <% if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_EDITAR)) {%>
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
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar un Usuario </td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                                <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                                            </tr>
                                        </table></td>
                                    <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                </tr>
                            </table></td>
                        <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                    </tr>

                    <!--pegar aca form e input-->

                    <tr>
                        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Datos B&aacute;sicos </td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="205">Nombre del usuario en el sistema</td>
                                    <td class="campositem">
                                        <%
                                            String nombreUsuario = (String) session.getAttribute(CUsuario.LOGIN_USUARIO);
                                        %>

                                        <%=  nombreUsuario != null ? nombreUsuario : "&nbsp;"%>

                                        <%
                                            try {
                                                textHelper.setNombre(CUsuario.ID_USUARIO);
                                                textHelper.setId(CUsuario.ID_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setTipo("hidden");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                            try {
                                                textHelper.setNombre(CUsuario.LOGIN_USUARIO);
                                                textHelper.setId(CUsuario.LOGIN_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setTipo("hidden");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>

                                    </td>
                                    <td colspan="4">&nbsp;</td>

                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="205">Nombre</td>
                                    <td>
                                        <% try {
                                                textHelper.setTipo("text");
                                                textHelper.setNombre(CUsuario.NOMBRE_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(CUsuario.NOMBRE_USUARIO);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td width="105">Apellido 1</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CUsuario.APELLIDO1_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(CUsuario.APELLIDO1_USUARIO);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>

                                    <td width="160">Apellido 2</td>
                                    <td>
                                        <% try {
                                                textHelper.setNombre(CUsuario.APELLIDO2_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(CUsuario.APELLIDO2_USUARIO);
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>                    

                                </tr>

                            </table>

                        </td>
                        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                    </tr>
                    <tr>
                        <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                        <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                    </tr>
                </table>
                <% }%>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>                    
                    </tr>
                    <tr>
                        <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Novedad</td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                                <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                </tr>
                            </table>
                        </td>
                        <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                    </tr>
                    <tr>
                        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Informaci&oacute;n de la Novedad</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="205">Tipo de novedad</td>
                                    <td>
                                        <% try {
                                                tipoNovedadHelper.setNombre(CJustificaAdm.TIP_ID_TIPO);
                                                tipoNovedadHelper.setCssClase("camposformtext");
                                                tipoNovedadHelper.setId(CJustificaAdm.TIP_ID_TIPO);
                                                tipoNovedadHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>

                                    </td>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="205">Documento justificaci&oacute;n</td>
                                    <td colspan="2"><input name="filename" type="file" class="camposformtext"></td>                                
                                </tr>
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="205">Descripci&oacute;n de novedad</td>
                                    <td colspan="5">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="7">                                        
                                        <% try {
                                                textAreaHelper.setNombre(CJustificaAdm.ADM_DESCRIPCION);
                                                textAreaHelper.setCols("60");
                                                textAreaHelper.setRows("6");
                                                textAreaHelper.setCssClase("camposformtext");
                                                textAreaHelper.setId(CJustificaAdm.ADM_DESCRIPCION);
                                                textAreaHelper.setFuncion("onKeyDown=\"textCounter(this,20)\"");
                                                textAreaHelper.setFuncion("onKeyUp=\"textCounter(this,'MAX_LENGTH',20)\"");
                                                textAreaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        Car&aacute;cteres restantes m&iacute;nimos en la descripci&oacute;n: 
                                        <span id="MAX_LENGTH">20</span>
                                        <input type="hidden" name="<%=CJustificaAdm.ADM_MAX_LENGTH%>" id="<%=CJustificaAdm.ADM_MAX_LENGTH%>" value="20">
                                    </td>
                                </tr>
                            </table>
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
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                                <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                </tr>
                            </table>
                        </td>
                        <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                    </tr>
                    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Opciones</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td width="140">
                                        <a href="javascript:cambiarAccion('<%=AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
                                    </td>

                                    <td><a href="admin.usuario.consulta.view"> <img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0"></a>
                                        <a href="admin.view"> <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
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
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Listado</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <hr class="linehorizontal">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td>Rango Fecha, desde:</td>
                                        <td>
                                            <input id="<%=WebKeys.RANGO_FECHA_INI%>" size="" name="<%=WebKeys.RANGO_FECHA_INI%>" onblur="javascript:validarFecha('<%=WebKeys.RANGO_FECHA_INI%>')" type="text" maxlength="" class="camposformtext">
                                        </td>
                                        <td align="left">
                                            <a href="javascript:NewCal('<%=WebKeys.RANGO_FECHA_INI%>','ddmmmyyyy',true,24)">
                                                <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onclick="javascript:Valores('<%=request.getContextPath()%>')">
                                            </a>
                                        </td>
                                        <td>Hasta:</td>

                                        <td>
                                            <input id="<%=WebKeys.RANGO_FECHA_FIN%>" size="" name="<%=WebKeys.RANGO_FECHA_FIN%>" onblur="javascript:validarFecha('<%=WebKeys.RANGO_FECHA_FIN%>')" type="text" maxlength="" class="camposformtext">
                                        </td>
                                        <td align="left">
                                            <a href="javascript:NewCal('<%=WebKeys.RANGO_FECHA_FIN%>','ddmmmyyyy',true,24)">
                                                <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onclick="javascript:Valores('<%=request.getContextPath()%>')">
                                            </a>
                                        </td> 
                                        <td width="140">
                                            <a href="javascript:buscarXRango('<%=AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0"></a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <hr class="linehorizontal"> 
                            <p style="text-align:right; text-align:end; "><label width="0" style="cursor:hand;" class="titulotbcentral" onclick="fnExcelReport();"><u>Descargar En Excel</u></label></p>
                            <table width="100%" class="camposform" name='tablanotificaciones' id="<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                <tr>  
                                    <th>Nombre de Novedad</th>
                                    <th>Fecha</th>
                                    <th>Usuario</th>
                                    <th>Tipo Nota</th>
                                    <th>Descripción</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <%

                                    if (justificaciones_usuario != null) {
                                        int i = 0;
                                        for (Iterator iter = justificaciones_usuario.iterator(); iter.hasNext();) {
                                            JustificaAdm justificaAdm = (JustificaAdm) iter.next();
                                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                %>
                                <tr>
                                    <td class="camposformtext_noCase"><%= justificaAdm.getJustificaTipos().getTipNombreNovedad()%></td>
                                    <td class="camposformtext_noCase"id="AdmFecha<%=i%>"><%= df.format(justificaAdm.getAdmFecha())%></td>
                                    <td class="camposformtext_noCase"id="AdmUsuarioModifica<%=i%>"><%= justificaAdm.getAdmUsuarioModifica()%></td>
                                    <td class="camposformtext_noCase"id="AdmJustificaTipos<%=i%>"><%= justificaAdm.getJustificaTipos().getTipDescripcion()%></td>
                                    <td class="camposformtext_noCase"id="AdmDescripcion<%=i%>"><%= justificaAdm.getAdmDescripcion()%></td>                                   
                                    <%if (justificaAdm.getArchivosJustifica() == null) {%>
                                    <td>-</td>                                    
                                    <%} else {%>
                                    <td>
                                        <a target="_blank" href="<%=request.getContextPath()%>/jsp/descargar.jsp?nArchivo=<%=justificaAdm.getArchivosJustifica().getJusIdArchivo()%>.<%=justificaAdm.getArchivosJustifica().getJusTipoArchivo()%>&rArchivo=<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                            <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif"> 
                                        </a>                                    
                                    </td>
                                    <% }%>
                                    <td width="40">   
                                        <img id="AdmImprimir<%=i%>" onClick="imprimir(<%=i%>);"  src="<%=request.getContextPath()%>/jsp/images/btn_mini_imprimir.gif" >

                                    </td>
                                </tr>
                                <%
                                            i++;
                                        }
                                    }
                                %>
                            </table>
                            
                            <div style="text-align: center;" id="pageNavPosition"></div>
                            <script type="text/javascript"> limpiarForm();
                                var pager = new Pager('<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>', 20);
                                pager.init();
                                pager.showPageNav('pager', 'pageNavPosition');
                                pager.showPage(1);
                            </script>
                        </td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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