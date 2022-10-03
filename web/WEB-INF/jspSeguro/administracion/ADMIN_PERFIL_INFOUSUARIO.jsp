<%@page import="java.util.StringTokenizer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CArchivosJustifica"%>
<%@page import="gov.sir.core.negocio.modelo.JustificaAdm"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CJustificaAdm"%>
<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Usuario" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>
<%@page import="org.auriga.core.modelo.transferObjects.RelUsuRol" %>
<%@page import="org.auriga.core.modelo.transferObjects.RelUsuRolEst" %>
<%@page import="gov.sir.core.negocio.modelo.OPLookupCodes" %>
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
    Usuario usuario = (Usuario) session.getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);
    List rolesSistemaLista = (List) application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);
    List rolesAdministrador = (List) session.getAttribute(AWAdministracionFenrir.LISTA_ADMINISTRADOR_REGIONAL_ROLES);

    List rolesString = new ArrayList();			//Lista que almacena el id de los roles del sistema
    Iterator itRoles = rolesSistemaLista.iterator();
    List rolesSistema = new ArrayList();		//Lista resultante de los roles a asignar
    Vector rolesAuxModificable = new Vector(); //Vector que se le va eliminado los roles que no son permitidos
    Vector rolesAuxFijo = new Vector();		//Vector con los roles del sistema

    TextAreaHelper textAreaHelper = new TextAreaHelper();
    
    
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
    
    
    
    
    /**
     * FILTRAR LOS ROLES QUE SON PERMITIDOS ASIGNAR EN PERFILES
     */
    if (rolesAdministrador != null) {
        //System.out.println("<<>>>>>DNilson226 Step1A vlr rolesAdministrador: "+ rolesAdministrador +"<<>>>>>");
        while (itRoles.hasNext()) { //DNilson226 itera sobre todos los Roles del Sistema
            ElementoLista el = (ElementoLista) itRoles.next();
            rolesString.add((String) el.getId());
            rolesAuxModificable.add(el);
            rolesAuxFijo.add(el);
        }

        for (int i = 0; i < rolesAdministrador.size(); i++) {
            for (int j = 0; j < rolesSistemaLista.size(); j++) {
                //System.out.println("<<>>>>>DNilson Step2BV vlr rolesSistemaLista.get(j): "+ rolesSistemaLista.get(j) +"<<>>>>>");
                //System.out.println("<<>>>>>DNilson Step2BV vlr rolesString.get(j): "+ rolesString.get(j) +"<<>>>>>");
                //((OPLookupCodes) rolesAdministrador.get(i)).getCodigo();
                //System.out.println("<<>>>>>DNilson Step2BV vlr ((OPLookupCodes) rolesAdministrador.get(i)).getCodigo() : "+ ((OPLookupCodes) rolesAdministrador.get(i)).getCodigo() +"<<>>>>>");
                if (rolesString.get(j).equals(((OPLookupCodes) rolesAdministrador.get(i)).getCodigo())) {
                    rolesAuxModificable.remove((ElementoLista) rolesAuxFijo.get(j));
                }
            }
        }
        rolesSistema = rolesAuxModificable;
    } else {
        rolesSistema = rolesSistemaLista;
    }

    if (rolesSistema == null) {
        rolesSistema = new ArrayList();
    }else{
        
        /**
         * @author DNilson Olaya
         * DNilson 226 Se Agrega Validacion de Regla de Negocio
         * validación al momento de realizar la asignación de roles,
         * por medio de la pantalla administrativa 'Administración de perfiles',
         * donde solo se le permita realizar la asignación del Rol CONSULTA NACIONAL,
         * al usuario Administrador Nacional
         */
        if(!isAdministradorNacional){            
            //System.out.println(">>>>>>>DNilson226 Ingreso a Validacion Usuario de Ingreso no es Administrador Nacional");
            for(int i=0; i<rolesSistema.size(); i++){
             ElementoLista elementLista = (ElementoLista) rolesSistema.get(i);
             //System.out.println("DNilson226 vlrs 1A.elementLista.id:["+elementLista.getId()+"]  2B.elementLista.valor:[ "+elementLista.getValor()+"]");
                if(elementLista.getId().trim().equals("SIR_ROL_CONSULTA_NACIONAL")){
                    //System.out.println(">>>>>>>DNilson226 1AV. antes de eliminar Consulta Nacional de Listado de Roles");
                    rolesSistema.remove(elementLista);
                    //System.out.println(">>>>>>>DNilson226 2BV. despues de eliminar Consulta Nacional de Listado de Roles");
                }
             }            
        }
    }

    List estacionesRol = (List) session.getAttribute(AWAdministracionFenrir.ESTACIONES_ROL);

    if (estacionesRol == null) {
        estacionesRol = new ArrayList();
    }
    ListaElementoHelper rolesHelper = new ListaElementoHelper();
    ListaElementoHelper estacionesHelper = new ListaElementoHelper();

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

    TextHelper textHelper = new TextHelper();

    List justificaciones_usuario = (List) session.getAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);

    if (justificaciones_usuario == null) {
        justificaciones_usuario = new ArrayList();
    }
%>


<script type="text/javascript">
    
    /**
     * @autor Geremias Ortiz, Yeferson Martinez
     * @requerimiento 317090 Telebucaramanga
     * @descripcion se modifican los funciones cambiarAccionAndSend, 
     * editarRolEstacion, eliminarRolEstacion y cambiarEstadoRelRolEstacion, 
     * para agrear el campo de tipo de justificación y una confirmación 
     * de la acción, tambien se agregan nuevan funciones para validaciones, 
     * imprimir y descargar como son: agregarEstacion, cargarFormEliminar,
     * cargarFormActivarInactivar, textCounter, removeFromArray, verifyChecks,
     * addToArrayEstateChange, addToArrayDelete, limpiarForm, imprimir, 
     * fnExcelReport y validarFecha
     */
    
    var arrRolEst = [];
    var arrRolEstEstado = [];

    function cambiarAccionAndSend(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_AGREGAR%>';
        document.BUSCAR.submit();
    }

    function agregarEstacion(text) {
        if (confirm('Esta seguro de agregar la novedad y editar el Rol: '))
        {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_AGREGAR%>';
            document.BUSCAR.submit();
        }
    }

    function editarRolEstacion(rol, estacion) {
        if (confirm('Esta seguro de agregar la novedad y editar el Rol: '))
        {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionFenrir.EDITAR_ROL_ESTACION%>';
            document.BUSCAR.<%= WebKeys.ROL%>.value = rol;
            document.BUSCAR.<%= WebKeys.ESTACION%>.value = estacion;
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_AGREGAR%>';
            document.BUSCAR.submit();
        }
    }

    function eliminarRolEstacion() {
        if (confirm('Esta seguro de agregar la novedad y editar el Rol: '))
        {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO%>';
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_AGREGAR%>';
            document.BUSCAR.submit();
        }
    }

    function cargarFormEliminar() {
        if (arrRolEst.length > 0) {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionFenrir.FORM_JUST_ELIMINAR_ACT_INACT_PERFIL%>';
            document.BUSCAR.<%= WebKeys.ROL_ESTACION%>.value = arrRolEst;
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_ELIMINAR%>';
            document.BUSCAR.submit();

        } else {
            alert("Seleccione por lo menos un Rol a eliminar")
        }
    }

    function cargarFormActivarInactivar() {
        if (arrRolEstEstado.length > 0) {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionFenrir.FORM_JUST_ELIMINAR_ACT_INACT_PERFIL%>';
            document.BUSCAR.<%= WebKeys.ROL_ESTACION_ESTADO%>.value = arrRolEstEstado;
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_ACTIVAR_INACTIVAR%>';
            document.BUSCAR.submit();
        }
    }

    function cambiarEstadoRelRolEstacion() {
        if (confirm('Esta seguro de agregar la novedad y editar el Rol: '))
        {
            document.BUSCAR.<%= WebKeys.ACCION%>.value = '<%= AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION%>';
            document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = '<%= CUsuario.ROL_AGREGAR%>';
            document.BUSCAR.submit();
        }

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

    function verifyChecks(length, idToDisable) {
        var inputs = document.getElementsByTagName("input");
        if (length === 0) {
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].type === "checkbox" && inputs[i].getAttribute("id") === idToDisable) {
                    inputs[i].disabled = false;
                }
            }
        } else {
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].type === "checkbox" && inputs[i].getAttribute("id") === idToDisable) {
                    inputs[i].disabled = true;
                }
            }
        }
    }

    function addToArrayEstateChange(field, rolId, estacionId, estado) {
        if (field.checked) {
            arrRolEstEstado.push(rolId + "&" + estacionId + "&" + estado);
            verifyChecks(arrRolEstEstado.length, "<%= AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO%>");
        } else {
            removeFromArray(arrRolEstEstado, rolId + "&" + estacionId + "&" + estado);
            verifyChecks(arrRolEstEstado.length, "<%= AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO%>");
        }

    }

    function addToArrayDelete(field, rolId, estacionId) {
        if (field.checked) {
            arrRolEst.push(rolId + "&" + estacionId);
            verifyChecks(arrRolEst.length, "<%= AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION%>");
        } else {
            removeFromArray(arrRolEst, rolId + "&" + estacionId);
            verifyChecks(arrRolEst.length, "<%= AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION%>");
        }
    }

    function limpiarForm() {
        document.getElementById("<%=CJustificaAdm.ADM_DESCRIPCION%>").value = "";
        document.getElementById("<%=CJustificaAdm.TIP_ID_TIPO%>").selectedIndex = 0;
    }

    function imprimir(i) {

        var Descripcion = document.getElementById('AdmDescripcion' + i).innerHTML;
        var Fecha = document.getElementById('AdmFecha' + i).innerHTML;
        var Usuario = document.getElementById('AdmUsuarioModifica' + i).innerHTML;
        var TipoNovedad = document.getElementById('AdmJustificaTipos' + i).innerHTML;
        var ventana = window.open('imprimir', '', '');
        ventana.document.write('<head><title></title></head>');
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

    function fnExcelReport(){
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
            sa = txtArea1.document.execCommand("SaveAs", true, "Reporte Notas Informativas Roles.xls");

        } else                 //other browser not tested on IE 11
            sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));
        return (sa);
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

    function buscarXRango(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.<%=WebKeys.RANGO_FECHA_INI%>.value = document.getElementById('<%=WebKeys.RANGO_FECHA_INI%>');
        document.BUSCAR.<%=WebKeys.RANGO_FECHA_FIN%>.value = document.getElementById('<%=WebKeys.RANGO_FECHA_FIN%>');
        document.BUSCAR.<%= CUsuario.TIPO_JUSTIFICACION%>.value = document.getElementById('<%= CUsuario.FORM_TIPO_JUSTIFICACION%>').value;
        document.BUSCAR.submit();
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Informaci&oacute;n del Usuario </td>
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
                 * usuario y los campos de fecha para realizar el filtro. En el 
                 * formulario se agregaron nuevos inputs como TIPO_JUSTIFICACION, 
                 * ROL_ESTACION, ROL_ESTACION_ESTADO, RANGO_FECHA_INI, 
                 * RANGO_FECHA_FIN, para el manejo de las nuevas funciones
                 */
            %>
            <form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR" enctype="multipart/form-data" >
                <!--                <input type="hidden" name=" " id="NINGUNO" value="NINGUNO">-->
                <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%= AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO%>" value="<%= AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO%>">
                <input type="hidden" name="<%= WebKeys.ROL%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.ESTACION%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.ESTADO_REL_ROL_ESTACION%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= CUsuario.TIPO_JUSTIFICACION%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.ROL_ESTACION%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.ROL_ESTACION_ESTADO%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.RANGO_FECHA_INI%>" id="NINGUNO" value="NINGUNO">
                <input type="hidden" name="<%= WebKeys.RANGO_FECHA_FIN%>" id="NINGUNO" value="NINGUNO">

                <% if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.ROL_AGREGAR)) {%>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    </tr>
                    <tr>
                        <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Perfil del Usuario <%=usuario.getUsuarioId()%></td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>	
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
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" class="camposform">
                                <tr>
                                    <td class="titulotbcentral" align="center" width="250">Roles</td>
                                    <td class="titulotbcentral" align="center" width="150">Estaciones</td>
                                    <td class="titulotbcentral" align="center" width="30">Estado</td>
                                    <td width="35" class="titulotbcentral">Dar Acceso</td>
                                    <td width="35" class="titulotbcentral" style="text-align: center;"> 
                                        <a href="javascript:cargarFormEliminar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" width="139" height="21" border="0"></a>
                                    </td>
                                    <td width="35" class="titulotbcentral" style="text-align: center;"> 
                                        <a href="javascript:cargarFormActivarInactivar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_estado.gif" width="139" height="21" border="0"></a>
                                    </td>	
                                </tr> 
                                <%
                                    List usuRols = usuario.getRelUsuRols();
                                    for (Iterator iter = usuRols.iterator(); iter.hasNext();) {
                                        RelUsuRol usuRol = (RelUsuRol) iter.next();
                                        List usuRolEsts = usuRol.getRelUsuRolEsts();
                                        for (Iterator iterEst = usuRolEsts.iterator(); iterEst.hasNext();) {
                                            RelUsuRolEst usuRolEst = (RelUsuRolEst) iterEst.next();
                                            Rol rolT = usuRolEst.getRelRolEstacion().getRol();
                                            Estacion estacionT = usuRolEst.getRelRolEstacion().getEstacion();
                                %>   
                                <tr>
                                    <td class="campositem" width="250"><%= rolT.getNombre()%></td>
                                    <td class="campositem" width="150"><%= estacionT.getNombre()%></td>
                                    <td class="campositem" width="30"><%= usuRolEst.getRelRolEstacion().isActiva() ? "Activa" : "Inactiva"%></td>
                                    <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" onClick="editarRolEstacion('<%=usuRolEst.getRolId()%>', '<%=usuRolEst.getEstacionId()%>')" style="cursor:hand"></td>
                                    <td style="text-align: center;">                                    
                                        <% try {
                                                textHelper.setNombre(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO);
                                                textHelper.setTipo("checkbox");
                                                textHelper.setFuncion("onClick=\"addToArrayDelete(this,'" + usuRolEst.getRolId() + "','" + usuRolEst.getEstacionId() + "')\"");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td style="text-align: center;">
                                        <!--<img src="<%--=request.getContextPath()--%>/jsp/images/btn_mini_activar.gif" width="35" height="13" onClick="/*cambiarEstadoRelRolEstacion('<%--=usuRolEst.getRolId()--%>', '<%--=usuRolEst.getEstacionId()--%>', '<%--=!usuRolEst.getRelRolEstacion().isActiva()--%>')*/" style="cursor:hand">-->
                                        <% try {
                                                textHelper.setNombre(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION);
                                                textHelper.setCssClase("camposformtext_noCase");
                                                textHelper.setId(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION);
                                                textHelper.setTipo("checkbox");
                                                textHelper.setFuncion("onClick=\"addToArrayEstateChange(this, '" + usuRolEst.getRolId() + "','" + usuRolEst.getEstacionId() + "','" + !usuRolEst.getRelRolEstacion().isActiva() + "')\"");
                                                textHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
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
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    </tr>
                    <tr>
                        <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>	
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
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">                         
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Agregar Nueva Estación a un Rol para Usuario <%=usuario.getUsuarioId()%></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="300" class="titulotbcentral">Roles</td>
                                    <td width="250" class="titulotbcentral">Estaciones del Rol</td>
                                    <td width="139">&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>
                                        <%

                                            try {
                                                rolesHelper.setNombre(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
                                                rolesHelper.setCssClase("camposformtext");
                                                rolesHelper.setId(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
                                                rolesHelper.setTipos(rolesSistema);
                                                rolesHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO + "')\"");
                                                rolesHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %> 
                                    </td>
                                    <td>
                                        <% try {
                                                estacionesHelper.setNombre(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
                                                estacionesHelper.setCssClase("camposformtext");
                                                estacionesHelper.setId(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
                                                estacionesHelper.setTipos(estacionesRol);
                                                estacionesHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>                                    
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
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
                                        <input type="hidden" name="<%= CJustificaAdm.ADM_MAX_LENGTH%>" id="<%=CJustificaAdm.ADM_MAX_LENGTH%>" value="20">
                                    </td>
                                </tr>
                                <tr>
                                    <td><a href="javascript:agregarEstacion('<%=AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0" ></a>

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
                <hr class="linehorizontal">
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
                                            <input type="hidden" name="<%= CUsuario.FORM_TIPO_JUSTIFICACION%>" id="<%= CUsuario.FORM_TIPO_JUSTIFICACION%>" value="<%= session.getAttribute(CUsuario.TIPO_JUSTIFICACION)%>">
                                            <a href="javascript:buscarXRango('<%=AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA_PERFIL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0"></a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <hr class="linehorizontal"> 
                            <p style="text-align:right; text-align: end; "><label width="0" style="cursor:hand;" class="titulotbcentral" onclick="fnExcelReport();"><u>Descargar En Excel</u></label></p>
                            <table width="100%" class="camposform" name='tablanotificaciones' id="<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                <tr>  
                                    <th>Nombre de Novedad</th>
                                    <th>Rol Novedad</th>  <!--DNilson item7-->
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
                                            String sRolNovedad = "";
                                            String sAdmDesc = "";
                                            if( justificaAdm.getAdmDescripcion() != null && justificaAdm.getAdmDescripcion().trim().length()>0 ){
                                                if( justificaAdm.getAdmDescripcion().indexOf("&&$%")!= -1 ){
                                                    //StringTokenizer token = new StringTokenizer(justificaAdm.getAdmDescripcion(),"&&");
                                                    int liIndice = justificaAdm.getAdmDescripcion().indexOf("&&$%");
                                                    sRolNovedad = justificaAdm.getAdmDescripcion().substring(liIndice+4,justificaAdm.getAdmDescripcion().length());                                                    
                                                    //sAdmDesc = justificaAdm.getAdmDescripcion().substring(0,liIndice-4);                                                    
                                                    sAdmDesc = justificaAdm.getAdmDescripcion().substring(0,liIndice);                                                    
                                                }else{
                                                    sAdmDesc = justificaAdm.getAdmDescripcion();
                                                }                                                                                        
                                            }
                                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                            
                                            
                                %>
                                <tr>
                                    <td class="camposformtext_noCase"><%= justificaAdm.getJustificaTipos().getTipNombreNovedad()%></td>
                                    <!-- DNILSON <td class="camposformtext_noCase">%= justificaAdm.getJustificaTipos().getTipIdTipo()%></td> -->
                                    <td class="camposformtext_noCase"><%= sRolNovedad %></td>
                                    <td class="camposformtext_noCase"id="AdmFecha<%=i%>"><%= df.format(justificaAdm.getAdmFecha())%></td>
                                    <td class="camposformtext_noCase"id="AdmUsuarioModifica<%=i%>"><%= justificaAdm.getAdmUsuarioModifica()%></td>
                                    <td class="camposformtext_noCase"id="AdmJustificaTipos<%=i%>"><%= justificaAdm.getJustificaTipos().getTipDescripcion()%></td>
                                    <!-- <td class="camposformtext_noCase"id="AdmDescripcion%=i%>">%= justificaAdm.getAdmDescripcion()%></td> -->                                   
                                    <td class="camposformtext_noCase"id="AdmDescripcion<%=i%>"><%= sAdmDesc %></td>
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
                                        <img id="AdmImprimir<%=i%>" onClick="imprimir(<%=i%>);"  src="<%=request.getContextPath()%>/jsp/images/btn_mini_imprimir.gif">

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
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                    <tr>
                        <td class="titulotbcentral">Agregar Nueva Estaci&oacute;n a un Rol</td>
                    </tr>
                    <tr>
                        <td><img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.IR_AGREGAR_ESTACION_ROL%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0" style="cursor:hand"></td>
                    </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                    <tr>
                        <td><img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.REGRESAR_ESCOGER_USUARIO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" style="cursor:hand">
                            <a href="admin.view"> <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a></td>
                    </tr>
                </table>
                <script type="text/javascript"> limpiarForm();</script>
                <% }%>
                <% if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.ROL_ELIMINAR) || session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.ROL_ACTIVAR_INACTIVAR)) {%>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    </tr>
                    <tr>
                        <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>	
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
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
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
                                        <input type="hidden" name="<%= CJustificaAdm.ADM_MAX_LENGTH%>" id="<%=CJustificaAdm.ADM_MAX_LENGTH%>" value="20">
                                    </td>
                                </tr>

                                <% if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.ROL_ELIMINAR)) {%>
                                <tr>
                                    <td>                                        
                                        <img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" onClick="eliminarRolEstacion()" style="cursor:hand">
                                    </td>                       
                                </tr>
                                <% }%>
                                <% if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.ROL_ACTIVAR_INACTIVAR)) {%>
                                <tr>
                                    <td>                                        
                                        <img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" onClick="cambiarEstadoRelRolEstacion()" style="cursor:hand">
                                    </td>                       
                                </tr>
                                <% }%>
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
                <hr class="linehorizontal">
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
                                            <input type="hidden" name="<%= CUsuario.FORM_TIPO_JUSTIFICACION%>" id="<%= CUsuario.FORM_TIPO_JUSTIFICACION%>" value="<%= session.getAttribute(CUsuario.TIPO_JUSTIFICACION)%>">
                                            <a href="javascript:buscarXRango('<%=AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA_PERFIL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0"></a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <hr class="linehorizontal"> 
                            <p style=" text-align: end; "><label width="0" style="cursor:hand;" class="titulotbcentral" onclick="fnExcelReport();"><u>Descargar En Excel</u></label></p>
                            <table width="100%" class="camposform" name='tablanotificaciones' id="<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                <tr>  
                                    <th>Nombre de Novedad</th>
                                    <th>Rol Novedad</th>  <!--DNilson item7-->
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
                                            String sRolNovedad = "";
                                            String sAdmDesc = "";
                                            if( justificaAdm.getAdmDescripcion() != null && justificaAdm.getAdmDescripcion().trim().length()>0 ){
                                                if( justificaAdm.getAdmDescripcion().indexOf("&&$%")!= -1 ){
                                                    //StringTokenizer token = new StringTokenizer(justificaAdm.getAdmDescripcion(),"&&");
                                                    int liIndice = justificaAdm.getAdmDescripcion().indexOf("&&$%");
                                                    sRolNovedad = justificaAdm.getAdmDescripcion().substring(liIndice+4,justificaAdm.getAdmDescripcion().length());                                                    
                                                    sAdmDesc = justificaAdm.getAdmDescripcion().substring(0,liIndice);                                                    
                                                }else{
                                                    sAdmDesc = justificaAdm.getAdmDescripcion();
                                                }                                                                                        
                                            }
                                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                %>
                                <tr>
                                    <td class="camposformtext_noCase"><%= justificaAdm.getJustificaTipos().getTipNombreNovedad()%></td>
                                    <!-- <td class="camposformtext_noCase">%= justificaAdm.getJustificaTipos().getTipIdTipo()%></td> DNilson -->
                                    <td class="camposformtext_noCase"><%= sRolNovedad %></td>
                                    <td class="camposformtext_noCase"id="AdmFecha<%=i%>"><%= df.format(justificaAdm.getAdmFecha())%></td>
                                    <td class="camposformtext_noCase"id="AdmUsuarioModifica<%=i%>"><%= justificaAdm.getAdmUsuarioModifica()%></td>
                                    <td class="camposformtext_noCase"id="AdmJustificaTipos<%=i%>"><%= justificaAdm.getJustificaTipos().getTipDescripcion()%></td>
                                    <!-- <td class="camposformtext_noCase"id="AdmDescripcion%=i%>">%= justificaAdm.getAdmDescripcion()%></td>  -->    
                                    <td class="camposformtext_noCase"id="AdmDescripcion<%=i%>"><%= sAdmDesc %></td>
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
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                    <tr>
                        <td>
                            <img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.REGRESAR_PERFIL_INFOUSUARIO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" style="cursor:hand">
                            <a href="admin.view"> <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
                        </td>                        
                    </tr>

                </table>
                <script type="text/javascript"> limpiarForm();</script>
                <% }%>
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

