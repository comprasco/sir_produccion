<%@page import="org.auriga.core.web.*" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="org.auriga.core.web.Helper" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.negocio.modelo.CheckItem" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.registro.ActosHelper" %>
<%@page import="gov.sir.core.web.helpers.registro.AgregarActosHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro" %>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudAsociada" %>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.text.NumberFormat" %>

<%
    TextAreaHelper textArea = new TextAreaHelper();
    ListaElementoHelper subtipo = new ListaElementoHelper();
    ListaElementoHelper tipoEncabezado = new ListaElementoHelper();
    OficinaHelper oficinaHelper = new OficinaHelper();
    ListaCheckboxHelper docHelper = new ListaCheckboxHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    List matriculasAsociadas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
    String style = "camposformtext";

    Boolean readOnly = (Boolean) session.getAttribute(WebKeys.TURNO_ANTERIOR_OK);
    boolean read = false;
    if(readOnly!=null && readOnly.booleanValue()) {
        read = true;
        style = "campositem";
    }

    String ocultarAS = (String) session.getAttribute("VER_ANTIGUO_SISTEMA");
    if(ocultarAS == null){
        ocultarAS = "TRUE";
    } else {
        session.setAttribute("VER_ANTIGUO_SISTEMA", ocultarAS);
    }

    String ultimo = null;
    List subtipos = new ArrayList();
    List subtiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);

    if(subtiposObj!= null && subtiposObj.size()>0){
        Iterator itActo = subtiposObj.iterator();
        ultimo = (String) session.getAttribute("ultimo_campo_editado");
        session.removeAttribute("ultimo_campo_editado");

        while(itActo.hasNext()) {
            SubtipoSolicitud sub = (SubtipoSolicitud)itActo.next();
            if(sub != null){
                String idTipo = sub.getIdSubtipoSol();
                String nombre = sub.getNombre();
                if(idTipo!= null && nombre!=null){
                    ElementoLista elemLista = new ElementoLista(idTipo, nombre);
                    subtipos.add(elemLista);
                }
            }
        }
    }

    Object ss = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
    Object lsc = request.getSession().getAttribute("LISTA_SUBTIPO_CHEQUEO");

    if(ss == null || lsc == null) {
        String sel = "1";
        request.getSession().setAttribute("SUBTIPO_SOLICITUD", sel);
        List lsubtipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);

        if(lsubtipos!=null && lsubtipos.size()>0) {
            Iterator it = lsubtipos.iterator();
            List chequeo = new ArrayList();

            while(it.hasNext()) {
                SubtipoSolicitud sub = (SubtipoSolicitud) it.next();

                if(sel.equals(sub.getIdSubtipoSol())) {
                    List checked = sub.getCheckItems();
                    Iterator it2 = checked.iterator();
                    
                    while(it2.hasNext()) {
                        CheckItem ch = (CheckItem) it2.next();

                        if(ch != null) {
                            chequeo.add(new ElementoLista(ch.getIdCheckItem(), ch.getNombre()));
                        }
                    }
                    request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO", chequeo);
                }
            }
        }
    }

    // Inicializando el # del turno anterior
    String turnoAnterior = (String) session.getAttribute("TURNO_ANTERIOR");
    if(turnoAnterior == null){
        turnoAnterior = "";
    }

    // -----------------------------------------------------------------------------------------
    // Inicializando la solicitud vinculada
    String solicitudVinculada = (String) session.getAttribute("SOLICITUD_VINCULADA");

    if(null == solicitudVinculada){
        solicitudVinculada = "";
    }
    // -----------------------------------------------------------------------------------------

    // IDCIRCULO
    String idCirculo = "";
    if(request.getSession().getAttribute(WebKeys.CIRCULO) != null) {
        idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
    }
    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

    ListaElementoHelper tipoActoHelper = new ListaElementoHelper();
    ListaElementoHelper tipoDerechoHelper = new ListaElementoHelper();
    ListaElementoHelper tipoTarifaHelper = new ListaElementoHelper();
    ListaElementoHelper tipoIdHelper= new ListaElementoHelper();
    ActosHelper actoHelper = new ActosHelper();
    AgregarActosHelper nactoHelper = new AgregarActosHelper();

    // Settear si se muestra o no impuestos dependiendo del circulo
    actoHelper.setMostrarImpuesto(circulo.isCobroImpuesto());
    nactoHelper.setMostrarImpuesto(circulo.isCobroImpuesto());

    session.removeAttribute(WebKeys.POP_CERTIFICADOS_ASOCIADOS);

    // Obtener los datos respuesta
    double valorDerechos = 0;
    double valorImpuestos = 0;
    double valorMora = 0;
    double valorOtroImp = 0;
    double valorAsociados = 0;
    double valorTotal = 0;
    double valorConservacion = 0;
    boolean valores = false;
    
    /** Tipo de formato numérico utilizado por el helper **/
    NumberFormat formateador = new DecimalFormat("###,###,###,###,###,###,###,###.00");
    LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) request.getSession().getAttribute(AWLiquidacionRegistro.PRELIQUIDACION);
    
    if(liq != null) {
        valores = true;
        valorDerechos = liq.getValorDerechos();
        valorImpuestos = liq.getValorImpuestos();
        valorMora = liq.getValorMora();
        valorOtroImp = liq.getValorOtroImp();
        valorConservacion = liq.getValorConservacionDoc();
        
        //Obtener valor de certificados asociados.
        valorAsociados = 0.0;
        Solicitud sol = (Solicitud) liq.getSolicitud();
        List solhijas = sol.getSolicitudesHijas();
        
        for(Iterator i=solhijas.iterator(); i.hasNext() ; ) {
            SolicitudAsociada solasoc = (SolicitudAsociada) i.next();
            Solicitud solhija = solasoc.getSolicitudHija();
            Liquidacion liqhija = (Liquidacion) solhija.getLiquidaciones().get(0);
            valorAsociados += liqhija.getValor();
        }
        valorTotal = valorDerechos + valorImpuestos + valorMora + valorOtroImp + valorAsociados + valorConservacion;
    }
    
    boolean calificacion = false;
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    if(turno != null){
        calificacion = true;
    }
%>

<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/jsp/plantillas/common.js"></script>

<script>
    var ultimo_campo_editado;
    var vinculo;
    
    function ocultarAntiguoSistema(text) {
        document.REGISTRO.VER_ANTIGUO_SISTEMA.value = text;
        cambiarAccion('VER_ANTIGUO_SISTEMA');
    }
    
    function cambiarAccionForm(text) {
        document.REGISTRO.ACCION.value = text;
    }

    function validarFecha() {
        if(document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.value.length>0){
            var index = document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.value.lastIndexOf('/') + 1;
            if(index != null) {
                var fin = document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.value.length;
                var texto = document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.value.substring(index, fin);
                if (texto.length != 4) {
                    alert('Fecha incorrecta');
                    document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.value='';
                    document.REGISTRO.<%= CSolicitudRegistro.CALENDAR %>.focus();
                }
            }
        }
    }

    function matriculaFocus() {
        /*
        <%
        if(ultimo!=null && ultimo.equals("ID_MATRICULA")) {
            Integer nume = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
            String campo = CFolio.ID_MATRICULA + nume.toString();
        %>
        document.REGISTRO.<%= campo %>.focus();
        <%
            }
        %>
        */
    }

    function validacion () {
        ultimo_campo_editado="<%= ultimo %>";
        document.getElementById("ultimo_campo_editado").value = ultimo_campo_editado;
        vinculo = "#" + ultimo_campo_editado;
        document.location.href = vinculo
        elemento = document.getElementById("ultimo_campo_editado").value
        if(document.getElementById(ultimo_campo_editado + "_img") != null) {
            document.getElementById(ultimo_campo_editado + "_img").className = "imagen_campo_editar"
        }
    }

    function campoactual(id) {
        if(document.getElementById("ultimo_campo_editado") != null) {
            document.getElementById("ultimo_campo_editado").value = id;
        }
    }

    function asociarTurno() {
        document.REGISTRO.ACCION.value = 'VALIDAR_TURNO_ANTERIOR';
        document.REGISTRO.submit();
    }

    function liquidarRegistro() {
        document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
        cambiarAccion('LIQUIDAR_REGISTRO');
    }

    function eliminarMatriculas() {
        document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
        document.REGISTRO.ACCION.value = 'ELIMINAR';
        document.REGISTRO.submit();
    }

    function agregarSubtipo(text) {
        document.REGISTRO.ACCION.value = text;
        document.REGISTRO.submit();
    }

    function cargarSubTipo() {
        <%
        if(request.getSession().getAttribute("SUBTIPO_SOLICITUD") != null) {
            Object b = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
            System.out.print(b.getClass().getName());
            if(b != null) {
                try {
                    String ssol = (String) b;
                    subtipo.setSelected(ssol);
                } catch(Exception e){
                    subtipo.setSelected(null);
                }
            }
        }
        %>
    }

    function cambiarAccion(text) {
        document.REGISTRO.ACCION.value = text;
        document.REGISTRO.submit();
    }

    function agregarMatricula(text) {
        //document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
        document.REGISTRO.ACCION.value = text;
        document.REGISTRO.submit();
    }

    function setTipoOficina(){
        /*
        document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO %>').value = "";
        document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM %>').value = "";
        document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO %>').value = "";
        */
    }

    function oficinas(nombre, valor, dimensiones) {
        document.REGISTRO.ACCION.value='<%= CSolicitudRegistro.PRESERVAR_INFO %>';
        var idDepto = document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO %>').value;
        var idMunic = document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC %>').value;
        var idVereda = document.getElementById('<%= CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA %>').value;
        document.getElementById('tipo_oficina').value = valor + "_ID_TIPO";
        document.getElementById('tipo_nom_oficina').value = valor + "_TIPO";
        document.getElementById('numero_oficina').value = valor + "_NUM";
        document.getElementById('id_oficina').value = valor + "_ID_OFICINA";
        popup = window.open(nombre + '?<%= AWOficinas.ID_DEPTO %>=' + idDepto + '&<%= AWOficinas.ID_MUNIC %>=' + idMunic + '&<%= AWOficinas.ID_VEREDA %>=' + idVereda, valor, dimensiones);
        popup.focus();
    }

    function juridica(nombre, valor, dimensiones) {
        document.getElementById('natjuridica_id').value = valor + "_ID";
        document.getElementById('natjuridica_nom').value = valor + "_NOM";
        popup = window.open(nombre, valor, dimensiones);
        popup.focus();
    }

    function verAnotacion(nombre, valor, dimensiones, pos){
        document.CREAR.POSICION.value = pos;
        popup = window.open(nombre, valor, dimensiones);
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

    function cambiarValorTipoDocumento(text) {
        try {
            document.getElementById('TIPO_ENCABEZADO').options[text].selected = true;
        } catch(e) {
            document.getElementById('TIPO_ENCABEZADO').value = ' <%= WebKeys.SIN_SELECCIONAR %>';
            document.getElementById('ID_TIPO_ENCABEZADO').value = '';
        }
    }

    function habilitarModificacion() {
        document.all.display_modificacion_off.style.display = 'none';
        document.all.display_modificacion_on.style.display = 'block';
        document.all.display_modificacion_1.style.display = 'block';
        document.all.display_modificacion_2.style.display = 'block';
        document.all.display_modificacion_3.style.display = 'block';
    }

    function desHabilitarModificacion() {
        document.all.display_modificacion_off.style.display = 'block';
        document.all.display_modificacion_on.style.display = 'none';
        document.all.display_modificacion_1.style.display = 'none';
        document.all.display_modificacion_2.style.display = 'none';
        document.all.display_modificacion_3.style.display = 'none';
    }

    function modificarLiquidacion(text, anio, circulo, proceso, sec) {
        if(document.REGISTRO.AUX.value != "") {
            document.REGISTRO.<%= AWLiquidacionRegistro.ID_SOLICITUD %>.value = anio + "-" + circulo + "-" + proceso + "-" + document.REGISTRO.AUX.value;
            document.REGISTRO.ACCION.value = text;
            document.REGISTRO.submit();
        }
    }

    function cambioTipo(text) {
        document.getElementById('ID_ACTO').value = document.getElementById('TIPO_ACTO').value
        buscar(text + '_PRELIQUIDACION');
    }

    function cambioID(text) {
        document.getElementById('tIPO_ACTO').value = document.getElementById('ID_ACTO').value
        buscar(text + '_PRELIQUIDACION');
    }
    
    function buscar(text) {
        document.REGISTRO.ACCION.value = text;
        document.REGISTRO.submit();
    }

    function agregarActo() {
        document.REGISTRO.ACCION.value = 'AGREGAR_ACTO_PRELIQUIDACION';
    }

    function quitar(text) {
        document.REGISTRO.ITEM.value = text;
        cambiarAccion('ELIMINAR_ACTO_PRELIQUIDACION');
    }

    cargarSubTipo();
</script>
<script type="text/javascript">
    function jsEvent_VincularTurno_AddItem() {
        // send action-event: VALIDAR_TURNO_VINCULADO
        var actionId = 'REGISTRO_VINCULARTURNO_ADDITEM_ACTION'; //'VALIDAR_TURNO_VINCULADO';
        cambiarAccion(actionId);
    }
    
    function jsEvent_VincularTurno_DelItem() {
        var actionId = 'REGISTRO_VINCULARTURNO_DELITEM_ACTION'; //'ELIMINAR_TURNO_VINCULADO';
        cambiarAccion(actionId);
    }
</script>

<link href="<%= request.getContextPath() %>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <form name="REGISTRO" id="REGISTRO" action="turnoLiquidacionRegistro.do" method="post">
        <input type="hidden" name="Depto" id="id_depto" value="">
        <input type="hidden" name="Depto" id="nom_Depto" value="">
        <input type="hidden" name="Mpio" id="id_munic" value="">
        <input type="hidden" name="Mpio" id="nom_munic" value="">
        <input type="hidden" name="Ver" id="id_vereda" value="">
        <input type="hidden" name="Ver" id="nom_vereda" value="">
    
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="tdtablaanexa02">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <input type="hidden" name="ACCION" id="ACCION" value="LIQUIDAR_REGISTRO">
                    <input type="hidden" name="ultimo_campo_editado" id="ultimo_campo_editado">
                    <input type="hidden" name="VER_ANTIGUO_SISTEMA"    id="VER_ANTIGUO_SISTEMA">
                    <input type='image' src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="00" height="00">
                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif">
                        <img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10">
                    </td>
                    <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Preliquidacion</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn002.gif" width="20" align="center" valign="top">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr><td><img src="<%= request.getContextPath() %>/jsp/images/ico_new.gif" width="16" height="21"></td></tr>
                                    </table>
                                </td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td width="7" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <td class="bgnsub">Encabezado del Documento</td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath() %>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Datos B&aacute;sicos</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td>Tipo</td>
                                            <td>
                                            <%
                                                try {
                                                    TextHelper textHelper = new TextHelper();
                                                    textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                                                    textHelper.setCssClase(style);
                                                    textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value); cambiarAccionForm('LIQUIDAR_REGISTRO');\"");
                                                    textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                                                    textHelper.setEditable(true);
                                                    textHelper.setReadonly(read);
                                                    textHelper.render(request,out);
                                                    textHelper.setFuncion("");
                                                } catch(HelperException re){
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td>
                                            <%
                                                try {
                                                    String stringTipoEncabezado = (String) session.getAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
                                                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                                                    tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
                                                    tipoEncabezado.setOrdenar(false);
                                                    tipoEncabezado.setTipos(tiposDoc);
                                                    tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
                                                    tipoEncabezado.setCssClase(style);
                                                    tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);

                                                    if(stringTipoEncabezado!=null && read) {
                                                        tipoEncabezado.setFuncion("onchange=\"this.value = '" + (stringTipoEncabezado) + "';\"");
                                                    } else {
                                                        tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
                                                    }
                                                    tipoEncabezado.render(request, out);
                                                }
                                                catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                            <script>
                                                document.getElementById('ID_TIPO_ENCABEZADO').value = document.getElementById('TIPO_ENCABEZADO').value;
                                                if(document.getElementById('ID_TIPO_ENCABEZADO').value == 'SIN_SELECCIONAR') {
                                                    document.getElementById('ID_TIPO_ENCABEZADO').value = '';
                                                }
                                            </script>
                                            <td>N&uacute;mero</td>
                                            <td>
                                            <%
                                                try {
                                                    TextHelper textHelper = new TextHelper();
                                                    textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
                                                    textHelper.setCssClase(style);
                                                    textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
                                                    textHelper.setReadonly(read);
                                                    textHelper.render(request, out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td>Fecha</td>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td>
                                                        <%
                                                            try {
                                                                Date dHoy = new Date();
                                                                Calendar hoy = Calendar.getInstance();
                                                                hoy.setTime(dHoy);
                                                                String fechaHoy = hoy.get(Calendar.DAY_OF_MONTH) + "/" + (hoy.get(Calendar.MONTH)+1) + "/" + hoy.get(Calendar.YEAR);
                                                                if(request.getSession().getAttribute(CSolicitudRegistro.CALENDAR) == null){
                                                                    request.getSession().setAttribute(CSolicitudRegistro.CALENDAR, fechaHoy);
                                                                }
                                                                TextHelper textHelper = new TextHelper();
                                                                textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                                                                textHelper.setCssClase(style);
                                                                textHelper.setId(CSolicitudRegistro.CALENDAR);
                                                                textHelper.setFuncion("onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                                        + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\" "
                                                                        + " onBlur=\"javascript:validarFecha()\"" );
                                                                textHelper.setReadonly(read);
                                                                textHelper.render(request, out);
                                                            } catch(HelperException re) {
                                                                out.println("ERROR " + re.getMessage());
                                                            }
                                                        %>
                                                        </td>
                                                        <td>
                                                        <%
                                                            if(!read) {
                                                        %>
                                                        <a href="javascript:NewCal('calendar', 'ddmmmyyyy', true, 24)">
                                                            <img src="<%= request.getContextPath() %>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%= request.getContextPath() %>')">
                                                        </a>
                                                        <%
                                                            }
                                                        %>&nbsp;
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath() %>/jsp/images/sub_bgn001.gif" class="bgnsub">Actos</td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath() %>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <br>
                        <input type="hidden" name="ITEM" value="NINGUNO">
                        <%
                            try {
                                actoHelper.render(request, out);
                            } catch(HelperException re) {
                                out.println("ERROR " + re.getMessage());
                            }
                        %>
                        <br>
                        <%
                            try {
                                nactoHelper.render(request, out);
                            } catch(HelperException re){
                                out.println("ERROR " + re.getMessage());
                            }
                        %>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath() %>/jsp/images/sub_bgn001.gif" class="bgnsub">Otro Impuesto</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath() %>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath() %>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath() %>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" class="camposform">
                            <tr>
                                <td>Valor</td>
                                <td>
                                <%
                                    try {
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre("VALOR_OTRO_IMPUESTO");
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setId("VALOR_OTRO_IMPUESTO");
                                        textHelper.render(request, out);
                                    } catch(HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td>Descripci&oacute;n</td>
                                <td>
                                <%
                                    try {
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre("DESCRIPCION");
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setId("DESCRIPCION");
                                    textHelper.render(request, out);
                                    } catch(HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath() %>/jsp/images/sub_bgn001.gif" class="bgnsub">Certificados asociados</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath() %>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath() %>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath() %>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="100"># de certificados</td>
                                <td width="60">Tipo tarifa</td>
                                <td>&nbsp;</td>
                            </tr>
                            <%
                                List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
                                List tipos = null;
                                tipos =  new ArrayList();
                                Iterator it = temp.iterator();
                                String nombreTarifa;
                                
                                while(it.hasNext()) {
                                    ElementoLista el = (ElementoLista) it.next();
                                    nombreTarifa = el.getValor();
                            %>
                            <tr>
                                <td width="100">
                                <%
                                    try {
                                        String valor = (String) session.getAttribute("NUMERO_" + nombreTarifa);
                                        if(valor == null || valor.equals("")) {
                                            session.setAttribute("NUMERO_"+nombreTarifa, "0");
                                        }
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre("NUMERO_" + nombreTarifa);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setId("NUMERO_" + nombreTarifa);
                                        textHelper.render(request, out);
                                    } catch(HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                    }
                                %>
                                </td>
                                <td class ="campositem" width="60"><%= nombreTarifa %></td>
                                <td>&nbsp;</td>
                            </tr>
                            <%
                                }
                            %>
							<!--
								@author: Cesar Ramírez
								@change: @change: Caso 1158.111.INACTIVAR.TARIFA.ESPECIAL.CERTIFICADOS. Se inactiva la opción de tarifa especial.
							-->
                            <!--
                            <tr>
                                <td width="100">
                                <%
                                    /*
                                    try {
                                        String valor = (String) session.getAttribute("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);
                                        if(valor == null || valor.equals("")) {
                                            session.setAttribute("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL, "0");
                                        }
                                        TextHelper textHelper = new TextHelper();
                                        textHelper.setNombre("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);
                                        textHelper.setCssClase("camposformtext");
                                        textHelper.setId("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);
                                        textHelper.render(request, out);
                                    } catch(HelperException re) {
                                        out.println("ERROR " + re.getMessage());
                                    }
                                    */
                                %>
                                </td>
                                <td class ="campositem" width="60">
                                    <%/*=CTipoTarifa.TARIFA_ESPECIAL*/%>
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            -->
                        </table>
                        <br>
                        <hr class="linehorizontal">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="150"><a href="javascript:cambiarAccion('PRELIQUIDAR')"><img src="<%= request.getContextPath() %>/jsp/images/btn_liquidar.gif" width="139" height="21" border="0" alt="Liquidar la solicitud"></a></td>
                                <%
                                    if(calificacion) {
                                %>
                                <td width="150"><a href="javascript:cambiarAccion('REGRESAR_CALIFICACION')"><img src="<%= request.getContextPath() %>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" alt="Liquidar la solicitud"></a></td>
                                <%
                                    } else {
                                %>
                                <td width="150"><a href="javascript:cambiarAccion('REGRESAR_LIQUIDACION')"><img src="<%= request.getContextPath() %>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" alt="Liquidar la solicitud"></a></td>
                                <%
                                    }
                                %>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                        <br>
                        <%
                            String mensajeVencimientoHipoteca = (String) session.getAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA);
                            String mensajeVencimientoPatrimonio = (String) session.getAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO);
                            if((mensajeVencimientoHipoteca!=null && !mensajeVencimientoHipoteca.equals("")) || (mensajeVencimientoPatrimonio!=null && !mensajeVencimientoPatrimonio.equals(""))) {
                        %>
                        <!-- MENSAJE DE VENCIMIENTO -->
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="camposform">
                            <tr>
                                <td width='80' align='center'><img src='<%= request.getContextPath() %>/jsp/images/mensaje_animated.gif' width='40' height='40'></td>
                                <td>
                                    <table>
                                        <tr><td colspan='3'>&nbsp;</td></tr>
                                        <%
                                            if(mensajeVencimientoHipoteca != null) {
                                        %>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td colspan='3' align='center'><span class='botontextualRojo'><font size='2'><b>&nbsp;&nbsp;<%= mensajeVencimientoHipoteca %> !!!&nbsp;&nbsp;</b></font></span></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <%
                                            }
                                            if(mensajeVencimientoPatrimonio != null) {
                                        %>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td colspan='3' align='center'><span class='botontextualRojo'><font size='2'><b>&nbsp;&nbsp;<%= mensajeVencimientoPatrimonio %> !!!&nbsp;&nbsp;</b></font></span></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                        <tr><td colspan='3'>&nbsp;</td></tr>
                                    </table>
                                </td>
                                <td width='60'>&nbsp;</td>
                            </tr>
                        </table>
                        <br>
                        <!-- FIN MENSAJE DE VENCIMIENTO -->
                        <%
                            }
                        %>
                        <%
                            if(valores) {
                         %>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="camposform">
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                                <td width="293">Valor derechos</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorDerechos) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">Valor conservación documental</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorConservacion) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">Valor certificados asociados</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorAsociados) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">Valor impuestos</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorImpuestos) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">Valor multa</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorMora) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">Valor otros impuestos</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorOtroImp) %></td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293">&nbsp;</td>
                                <td width="11">&nbsp;</td>
                                <td width="445">&nbsp;</td>
                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="293" class ="campostitle">Valor total</td>
                                <td width="11">$</td>
                                <td width="445"><%= formateador.format(valorTotal) %></td>
                            </tr>
                        </table>
                        <%
                            }
                        %>
                    <td width="11" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                        <tr>
                            <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                            <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="15" height="6"></td>
                            <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                        </tr>
            </table>
    </form>
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
    </tr>
</table>
<script>
    matriculaFocus();
    validacion();
</script>