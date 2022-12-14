<%@page import="org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>
<%@page import="gov.sir.core.negocio.modelo.Departamento"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes"%>
<!--
/**
 * JSP para realizar la solicitud en el proceso traslado de folio
 * @version 2.0, 18/03/2011
 * @author Julio Alcazar
 */
-->
<%@page import="gov.sir.forseti.dao.impl.jdogenie.JDOGenieZonaRegistralDAO"%>
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
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR" %>


<%
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    List matriculasAsociadas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */

    String numeroFundamento = "";
    String numeroFundamentoDestino = "";
    List tiposFundamento = new java.util.ArrayList();
    List tiposFundamentoD = new java.util.ArrayList();
    List RolesList = (List) session.getAttribute(WebKeys.LISTA_ROLES);
    List foliosConf = (List)session.getAttribute("FOLIOS_CONFIRMACION");
    foliosConf = (foliosConf==null)?new ArrayList():foliosConf;
    List fundamentosAsociados = (List)session.getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
    List fundamentosAsociadosDestino = (List)session.getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO");
    String valor = "";
    String tipo_destino =request.getParameter("CIRCULO_OFICINA");
    if(tipo_destino!=null)
    {
       if(tipo_destino.equals("S")){
         valor = "CIRCULOS_FOLIO";
       }
       else if(tipo_destino.equals("F"))
       {
          valor = "CIRCULOS_EN_SIR";
       }
    
    }else
    {
        valor = "CIRCULOS_FOLIO";
    }
    List elementos = new java.util.ArrayList();
    List elementos2 = new java.util.ArrayList();

    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
    ElementoLista circulo_actual = new ElementoLista(circulo.getIdCirculo(), circulo.getNombre());
    JDOGenieZonaRegistralDAO zr = new JDOGenieZonaRegistralDAO();
     /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
    List listaCirculoDepartamento = new Vector();
    DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
    listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();
    int idCirculoInt = 0;
    String nombreCirculoDepartamento = "";
    String idCirculoString = "";          
    if (request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR) == null) {
            List circulosSIR = zr.getCirculosFechaProd();
            for (java.util.Iterator iter = circulosSIR.iterator(); iter.hasNext();) {
                Circulo circ = (Circulo) iter.next();
                idCirculoString = circ.getIdCirculo();
                if (departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)) {
                    idCirculoInt = Integer.parseInt(idCirculoString);
                    nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                    if (nombreCirculoDepartamento != "") {
                        elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                    }
                }
            }
        elementos.remove(circulo_actual);
        request.getSession().setAttribute(WebKeys.LISTADO_CIRCULOS_SIR, elementos);
    } else {
        elementos = (List) request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR);
        elementos.remove(circulo_actual);
    }
    


    ListaElementoHelper municipioHelper = new ListaElementoHelper();
    if (session.getAttribute(AWReportes.LISTA_MUNICIPIOS + "_ORIGEN") == null) {
        municipioHelper.setTipos(new ArrayList());
    } else {
        municipioHelper.setTipos((List) session.getAttribute(AWReportes.LISTA_MUNICIPIOS + "_ORIGEN"));
    }

    ListaElementoHelper municipioHelperDestino = new ListaElementoHelper();
    ListaElementoHelper municipioHelperDestinoNoSir = new ListaElementoHelper();
    if (session.getAttribute(AWReportes.LISTA_MUNICIPIOS + "_DESTINO") == null) {
        municipioHelperDestino.setTipos(new ArrayList());
        municipioHelperDestinoNoSir.setTipos(new ArrayList());
    } else {
        if(tipo_destino!=null){
                if(tipo_destino.equals("S")){
                    municipioHelperDestino.setTipos((List) session.getAttribute(AWReportes.LISTA_MUNICIPIOS + "_DESTINO"));
                    municipioHelperDestinoNoSir.setTipos(new ArrayList());
                }else if(tipo_destino.equals("F"))
                {
                    municipioHelperDestinoNoSir.setTipos((List) session.getAttribute(AWReportes.LISTA_MUNICIPIOS + "_DESTINO"));
                    municipioHelperDestino.setTipos(new ArrayList());
                }
          }
        }

    if (request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR) == null) {

        List circulosNOSIR = zr.getCirculos();
        for (java.util.Iterator iter = circulosNOSIR.iterator(); iter.hasNext();) {
            Circulo circ = (Circulo) iter.next();
            elementos2.add(new ElementoLista(circ.getIdCirculo(), circ.getNombre()));
        }
        elementos2.remove(circulo_actual);
        Iterator iter = elementos.iterator();
        while (iter.hasNext()) {
            ElementoLista circulo_sir = (ElementoLista) iter.next();
            elementos2.remove(circulo_sir);
        }
        request.getSession().setAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR, elementos2);
    } else {
        elementos2 = (List) request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR);
        elementos2.remove(circulo_actual);
    }

    TrasladoSIR traslado = new TrasladoSIR();
    List folios = traslado.FoliosBloqueadosTraslado(circulo.getIdCirculo());
    TextHelper textHelper2 = new TextHelper();
    
        /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
    List  tiposFundamentoSIR = null;
    if(request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR) == null) {
        tiposFundamentoSIR = traslado.TiposFundamento();
        for (java.util.Iterator iter = tiposFundamentoSIR.iterator(); iter.hasNext();) {
            String[] tf = iter.next().toString().split(";");
            tiposFundamento.add(new ElementoLista(tf[0], tf[1]));
            if(tf[0].equals("1")){
                boolean esAdminNac = false;
                for(java.util.Iterator row = RolesList.iterator();row.hasNext();){
                    Rol rol = (Rol)row.next();
                    if(rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
                        esAdminNac = true;
                    }
                }
                if(esAdminNac){
                    tiposFundamentoD.add(new ElementoLista(tf[0], tf[1]));
                }
            }else{
              tiposFundamentoD.add(new ElementoLista(tf[0], tf[1]));  
            }
        }
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR, tiposFundamento);
    } else {
        tiposFundamento = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR);
    }
    
    if(request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO") == null) {
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO", tiposFundamentoD);
    } else {
        tiposFundamentoD = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO");
    }
    
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
    .sobmbraUnload{
			display:none;
			top:0px;
			left:0px;
			width:0px;
			height:0px;
		}
  .sombraLoad{
   display: block;
   position: absolute;
   top: 0%;
   left: 0%;
   width: 100%;
   height: 100%;
   background-color: #cdd8ed;
   z-index:1000;
   opacity:0.8;
   filter:alpha(opacity=60);
   overflow: hidden;
  }

		.windowUnload{
			display:none;
			top:0px;
			left:0px;
			width:0px;
			height:0px;
		}

  .windowLoad{
			display:block;
   position:absolute;
   background-color:#FFFFFF;

   border:#000000 0.5px solid;
   top:25%;
   left:25%;
   width:750px;
   height:162px;
   z-index:1001;
   
   /*-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	behavior:url(border-radius.htc);
	*/
        text-align: center;

  }
		.texto{
			margin:20px 20px 20px 20px;
			text-align:center;
		}



</style>
<script type="text/javascript" >
    function modalPopUp()
    {
        var ventana = document.getElementById('window');
        var sombra = document.getElementById('sombra');
        ventana.className='windowLoad';
        sombra.className='sombraLoad';
        sombra.style.height= document.body.scrollHeight;
    }
    function agregarMatricula(text) {
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function agregarMatriculaRango(text) {
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function eliminarMatriculas(text) {
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function cambiarAccion(text) {
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function cambiarTabla(text){
        if(text == 'CIRCULOS_EN_SIR'){
            document.getElementById(text).style.display = 'none';
            document.getElementById("CIRCULOS_FOLIO").style.display = '';
            document.getElementById("r1").checked = true;
        }else{
            document.getElementById(text).style.display = 'none';
            document.getElementById("CIRCULOS_EN_SIR").style.display = '';
            document.getElementById("r2").checked = true; 
        }
    }
    function cambiarAccionAndSend(text,formulario) {
        var form = document.getElementById(formulario);
        if(text == '<%=AWAdministracionForseti.ONSELECT_MUNICIPIO_ORIGEN%>' || 
           text == '<%=AWAdministracionForseti.ONSELECT_MUNICIPIO_DESTINO%>'||
           text == '<%=AWAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO%>')
       {
            modalPopUp();    
       }
        form.<%= WebKeys.ACCION%>.value = text;
        form.submit();
    }
    function enviarArchivo()
    {   
        modalPopUp();
        document.ARCHIVOS_FOLIOS.submit();
    }
    function Confirmation(text){
        
        if (document.getElementById('CIRCULOS_NOSIR').value != "SIN_SELECCIONAR" || document.getElementById('CIRCULOS_SIR').value != "SIN_SELECCIONAR")
        {
            var datos = " ";
            var datos2 = "";
            if (document.getElementById('r2').checked == true){
                
                datos2 = document.TRASLADO_DESTINO.CIRCULOS_SIR.options[document.TRASLADO_DESTINO.CIRCULOS_SIR.selectedIndex].text;
            }else {
                
                datos2 = document.TRASLADO_DESTINO.CIRCULOS_NOSIR.options[document.TRASLADO_DESTINO.CIRCULOS_NOSIR.selectedIndex].text;
            }
            var answer = confirm(" Esta seguro de trasladar la matricula al círculo: " + datos2 + "\nCLICK en ACEPTAR si esta seguro de la operacíon, de lo contrario CLICK en CANCELAR" );

            if(answer == true)
            {
                modalPopUp();
                document.TRASLADO_DESTINO.ACCION.value = text;
            }
            else
            {
                document.TRASLADO_DESTINO.action = "";
                alert("Por Favor: Verifique de nuevo el círculo seleccionado.");
                return false;
            }
        }
        else
        {
            document.TRASLADO_DESTINO.ACCION.value = text;           
        }
    }

    function validarFecha(){
        if (document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
            var index=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
            if (index!=null){
                var fin=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.length;
                var texto=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
                if (texto.length!=4){
                    alert('Fecha incorrecta');
                    document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value='';
                    document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.focus();
                }
            }
        }
    }

    function folio_confirmacion(val, text){
        document.TRASLADO.FOLIO_CONFIRMACION.value = val;    
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function verDetalle(nombre,valor,matricula){
        popup=window.open(nombre+'&ID_MATRICULA='+matricula,valor);
        popup.focus();
    }
    function limitText(limitField, limitCount, limitNum) {
        if (limitField.value.length > limitNum) {
            limitField.value = limitField.value.substring(0, limitNum);
        } else {
            limitCount.value = limitNum - limitField.value.length;
        }
    }
    function oficinas(nombre,valor,dimensiones){
        //document.BUSCAR.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
        var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
        var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
        var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
        document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
        document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
        document.getElementById('numero_oficina').value=valor+"_NUM";
        document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
        popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
        popup.focus();
    }

    function locacion(nombre,valor,dimensiones){
        document.getElementById('id_depto').value=valor+"_ID_DEPTO";
        document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
        document.getElementById('id_munic').value=valor+"_ID_MUNIC";
        document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
        document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
        document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
        popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
        popup.focus();
    }

    function locacion1(nombre,valor,dimensiones){
        document.getElementById('id_depto').value=valor+"_ID_DEPTO";
        document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
        document.getElementById('id_munic').value=valor+"_ID_MUNIC";
        document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
        document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
        document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
    }
    function checkAll(check)
    {
        if(!document.forms['TRASLADO_DESTINO'])
		return;
	var objCheckBoxes = document.getElementsByName('FOLIOS_CONFIRMACION');
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = check.checked;
	else
        for(var i = 0; i < countCheckBoxes; i++){
	    objCheckBoxes[i].checked = check.checked;
        }
    }
function cambiarAccionDestido(text) {
    document.TRASLADO_DESTINO.ACCION.value = text;
    document.TRASLADO_DESTINO.submit();
}
</script>
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado Masivo</td>
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Traslado</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
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
                        <form action="administracionForseti.do" method="post"  name="TRASLADO" id="TRASLADO" >
                            <input type="hidden" name="ACCION" value="CANCELAR_SOLICITUD">	   
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
                            <table width="100%" id="CIRCULOS_EN_SIR_ORIGEN" class="camposform">
                                <tr>
                                   
                                    
                                </tr>
                                <tr>
                                    <td class="titulotbcentral" width="15%">C&iacute;rculo</td>
                                    <td class="titulotbcentral" width="15%">Municipio</td>
                                    <td class="titulotbcentral" width="35%">&nbsp;</td>
                                    <td class="titulotbcentral" width="35%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="rigth">
                                        <% try {
                                                ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                                circuloHelper.setOrdenar(false);
                                                circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_SIR + "_ORIGEN");
                                                circuloHelper.setId(AWAdministracionForseti.CIRCULOS_SIR + "_ORIGEN");
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.setTipos(elementos);
                                                circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN + "','TRASLADO')\"");
                                                circuloHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>

                                    <td align="right">
                                        <% try {

                                                municipioHelper.setOrdenar(false);
                                                municipioHelper.setNombre(AWAdministracionForseti.MUNICIPIO_ID + "_ORIGEN");
                                                municipioHelper.setId(AWAdministracionForseti.MUNICIPIO_ID + "_ORIGEN");
                                                municipioHelper.setCssClase("camposformtext");
                                                municipioHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.ONSELECT_MUNICIPIO_ORIGEN + "','TRASLADO')\"");
                                                municipioHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                    <td align="right">
                                    </td>
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
                                        <div style="overflow:scroll;width:100%;height:350px;">
                                        <% try {
                                                tablaHelper.setColCount(5);
                                                tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                                tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_PLAIN);
                                                tablaHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <P>&nbsp;</P>
                            <%}%>

                        </form>

                        <form id="ARCHIVOS_FOLIOS" name="ARCHIVOS_FOLIOS" action="administracionForseti.do" method="post" enctype="multipart/form-data">

                            <table width="100%" class="camposform">
                                <tr> 
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td>Cargar matr&iacute;culas desde archivo plano</td>
                                </tr>
                                <tr> 
                                    <td width="20">&nbsp;</td>
                                    <td> <table width="100%">
                                            <tr> 
                                                <td width="81%"> <input name="filename" type="file" class="camposformtext"> 
                                                </td>
                                                <td width="19%"><table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                                        <tr> 
                                                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                            <td>Subir Archivo</td>
                                                            <td><input onclick="enviarArchivo()" name="imageField222" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"> 
                                                            </td>
                                                        </tr>
                                                    </table></td>
                                            </tr>
                                        </table></td>
                                </tr>
                            </table>
                        </form>
                    </td>
                    <td width="11" background="/SNR/jsp/images/tabla_central_bgn008.gif">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">

                        <!-- Opcion de asociar un rango de matriculas -->

                        <!-- hasta aca se ha metido de nuevo de acuerdo al ticket se cambia el boton agregar matricula-->

                        <br>

                        <br>
                        <form id="TRASLADO_DESTINO" name="TRASLADO_DESTINO" method="post" action="administracionForseti.do">
                            <input type="hidden" name="ACCION" value="CANCELAR_SOLICITUD">
                            <input name="Depto" type="hidden" id="id_depto" value="">
                            <input name="Depto" type="hidden" id="nom_Depto" value="">
                            <input name="Mpio" type="hidden" id="id_munic" value="">
                            <input name="Mpio" type="hidden" id="nom_munic" value="">
                            <input name="Ver" type="hidden" id="id_vereda" value="">
                            <input name="Ver" type="hidden" id="nom_vereda" value="">
                            <input name="countdown" type="hidden" id="countdown" value="" />
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Datos de la Solicitud</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <%--                      
                            /**
                            * @author     : Carlos Torres
                            * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                            */
                            --%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Fundamento</td>
                </tr>
              </table>
              
              <% if (fundamentosAsociados != null && !fundamentosAsociados.isEmpty()) { %>
                    <table width="100%" border="0" class="camposform">
                        <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                            <td colspan="4">Fundamentos de esta solicitud</td>
                        </tr>
                        
                        <tr>
                            <td width="20">&nbsp;</td>
                            <td width="5px">&nbsp;</td>
                            <td class="titulotbcentral">Tipo</td>
                            <td class="titulotbcentral">Número</td>
                            <td class="titulotbcentral">Fecha</td>
                        </tr>
                        <%  
                            String f = null;
                            String[] s = null;
                            Iterator item;
                            item = fundamentosAsociados.iterator();
                            while (item.hasNext()) {
                                f = item.next().toString();
                                s = f.split(";");
                        %>
                        <tr>
                            <td width="20px">&nbsp;</td>
                            <td width="5px" class="campositem">
                                <input type="checkbox" id="<%= f %>" name="<%= WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX %>" value="<%= f %>"/>
                            </td>
                            <td class="campositem"><%= s[1] %></td>
                            <td class="campositem"><%= s[2] %></td>
                            <td class="campositem"><%= s[3] %></td>
                        </tr>
                        <% } %>
                    </table>
                    
                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                        <tr>
                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                            <td>Eliminar Fundamento</td>
                            <td><a href="javascript:cambiarAccionDestido('ELIMINAR_FUNDAMENTO_MASIVO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a></td>
                        </tr>
                    </table>
                    <P>&nbsp;</P>
            <% } %>
                          <table width="100%" border="0" class="camposform">
                  <tbody>
                      <tr>
                          <td width="20" height="18">&nbsp;</td>
                          <td class="titulotbcentral">Tipo</td>
                          <td>
                              <% try {
                                    ListaElementoHelper tiposFundamentoHelper = new ListaElementoHelper();
                                    tiposFundamentoHelper.setOrdenar(false);
                                    tiposFundamentoHelper.setNombre(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setId(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setCssClase("camposformtext");
                                    tiposFundamentoHelper.setTipos(tiposFundamento);
                                    tiposFundamentoHelper.render(request, out);
                                } catch(HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                              %>
                          </td>
                          <td class="titulotbcentral">N&uacute;mero</td>
                          <td>
                              <input name="<%=CFolio.NUMERO_FUNDAMENTO%>" id="<%=CFolio.NUMERO_FUNDAMENTO%>" type="text" value="<%=numeroFundamento%>" class="camposformtext">
                          </td>
                          <td class="titulotbcentral">Fecha</td>
                          <td>
                              <table border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                      <tr>
                                          <td>
                                              <% try {
                                                        TextHelper textHelper = new TextHelper();
                                                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\"   onBlur=\"javascript:validarFecha()\"" );
                                                        textHelper.setReadonly(false);
                                                        textHelper.render(request,out);
                                                     } catch(HelperException  re) {
                                                         out.println("ERROR " + re.getMessage());
                                                     }
                                              %>
                                          </td>
                                          <td align="left">
                                              <a href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
                                                  <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" 
                                                       alt="Fecha" width="16" height="21" border="0" 
                                                       onClick="javascript:Valores('<%=request.getContextPath()%>')">
                                              </a>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </td>
                          <td align="right">
                              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                  <tbody>
                                      <td><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                      <td>Agregar Fundamento</td>
                                      <td align="right">
                                          <a href="javascript:cambiarAccionDestido('AGREGAR_FUNDAMENTO_MASIVO');" name="imageField">
                                              <img alt="[add fundamento]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                          </a>
                                      </td>
                                  </tbody>
                              </table>
                          </td>
                      </tr>
                  </tbody>
              </table>
              <br />
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Circulo</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td class="contenido">Circulo Destino</td>
                                </tr>
                                <tr>
                                    <td height="18">&nbsp;</td>
                                    <td class="contenido">
                                        <table width="100%">
                                            <tr>
                                                <td>
                                                    <input type="hidden" name="ITEM" value="NINGUNO">
                                                    <input type="radio" id="r1" onclick="cambiarTabla('CIRCULOS_EN_SIR')" name="CIRCULO_OFICINA" value="F" /> FOLIO &nbsp;&nbsp;&nbsp;
                                                    <input type="radio" id="r2" onclick="cambiarTabla('CIRCULOS_FOLIO')" name="CIRCULO_OFICINA" value="S" checked /> SIR
                                                    <table width="100%" id="CIRCULOS_EN_SIR" class="camposform">
                                                        <tr>

                                                        </tr>
                                                        <tr>
                                                        <tr>
                                                            <td class="titulotbcentral" width="15%">C&iacute;rculo</td>
                                                            <td class="titulotbcentral" width="15%">Municipio</td>
                                                            <td class="titulotbcentral" width="35%">&nbsp;</td>
                                                            <td class="titulotbcentral" width="35%">&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                            <td align="rigth">
                                                                <% try {
                                                                        ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                                                        circuloHelper.setOrdenar(false);
                                                                        circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_SIR);
                                                                        circuloHelper.setId(AWAdministracionForseti.CIRCULOS_SIR);
                                                                        circuloHelper.setCssClase("camposformtext");
                                                                        circuloHelper.setTipos(elementos);
                                                                        circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.ONSELECT_CIRCULO_DESTINO + "','TRASLADO_DESTINO')\"");
                                                                        circuloHelper.render(request, out);
                                                                    } catch (HelperException re) {
                                                                        out.println("ERROR " + re.getMessage());
                                                                    }
                                                                %>
                                                            </td>
                                                            <td align="right">
                                                                <% try {

                                                                        municipioHelperDestino.setOrdenar(false);
                                                                        municipioHelperDestino.setNombre(AWAdministracionForseti.MUNICIPIO_ID + "_DESTINO");
                                                                        municipioHelperDestino.setId(AWAdministracionForseti.MUNICIPIO_ID + "_DESTINO");
                                                                        municipioHelperDestino.setCssClase("camposformtext");
                                                                        municipioHelperDestino.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.ONSELECT_MUNICIPIO_DESTINO + "','TRASLADO_DESTINO')\"");
                                                                        municipioHelperDestino.render(request, out);
                                                                    } catch (HelperException re) {
                                                                        out.println("ERROR " + re.getMessage());
                                                                    }
                                                                %>
                                                            </td>
                                                            <td align="right">
                                                                <%
                                                                    TextHelper hidden = new TextHelper();
                                                                    try
                                                                    {
                                                                        hidden.setId(AWAdministracionForseti.DEPARTAMENTO_ID+"_DESTINO");
                                                                        hidden.setNombre(AWAdministracionForseti.DEPARTAMENTO_ID+"_DESTINO");
                                                                        hidden.setTipo("hidden");
                                                                        hidden.setMaxlength("");
                                                                        hidden.setCssClase("");
                                                                        hidden.render(request, out); 
                                                                   }catch(HelperException re)
                                                                    {
                                                                        out.println("ERROR " + re.getMessage());
                                                                    }
                                                                %>
                                                            </td>
                                                            <td align="right">
                                                            </td>
                                                        </tr>
                                                    </table>

                                                    <table width="100%" id="CIRCULOS_FOLIO" class="camposform" style="display: none">
                                                        <tr>

                                                        </tr>
                                                        <tr>
                                                        <tr>
                                                            <td class="titulotbcentral" width="20%">C&iacute;rculo</td>
                                                            <td class="titulotbcentral" width="20%">&nbsp;</td>
                                                            <td class="titulotbcentral" width="40%">&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                            <td align="rigth">
                                                                <% try {
                                                                        ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                                                        circuloHelper.setOrdenar(false);
                                                                        circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_NOSIR);
                                                                        circuloHelper.setId(AWAdministracionForseti.CIRCULOS_NOSIR);
                                                                        circuloHelper.setCssClase("camposformtext");
                                                                        circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.ONSELECT_CIRCULO_DESTINO + "','TRASLADO_DESTINO')\"");
                                                                        circuloHelper.setTipos(elementos2);
                                                                        circuloHelper.render(request, out);
                                                                    } catch (HelperException re) {
                                                                        out.println("ERROR " + re.getMessage());
                                                                    }
                                                                %>
                                                            </td>
                                                            <td align="rigth">
                                                            </td>
                                                            
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td align="right">
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                           <script type="text/javascript">
                               cambiarTabla('<%=valor%>');
                           </script>                                
                            <br>


                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +   -->
                            <hr class="linehorizontal" />
                            <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->
                            
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td width="155">
                                        <input name="imageField" type="image" onClick="Confirmation('<%=AWAdministracionForseti.TRASLADAR_MASIVO%>')"  src="<%=request.getContextPath()%>/jsp/images/btn_trasladar.gif" width="150" height="21" border="0">
                                    </td>
                                    <td>
                                        <input name="Cancelar" type="image" onClick="cambiarAccionAndSend('<%=AWAdministracionForseti.TERMINA%>','TRASLADO_DESTINO')" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="150" height="21" border="0" id="Cancelar">
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                            <%
                                if (!folios.isEmpty()) {
                                    Iterator iter = folios.iterator();
                                    int i = 0;
                            %>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td class="bgnsub">Folios Traslados</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td class="contenido">Confirmacion</td>
                                </tr>
                                <tr>
                                    <td height="18">&nbsp;</td>
                                    <td class="contenido">
                                        <div style="overflow:scroll;width:100%;height:350px;">
                                            <table width="100%">
                                                <tr>
                                                    <td class="titulotbcentral"><input type="checkbox" name="ALL_FOLIO_CONFIRMACION"  onclick="checkAll(this)" />&nbsp;No.</td>
                                                    <td class="titulotbcentral">&nbsp;FOLIO</td>
                                                    <td class="titulotbcentral">&nbsp;VER</td>
                                                </tr>
                                                <%
                                                    while (iter.hasNext()) {
                                                        String folio = (String) iter.next();
                                                        i++;
                                                %>


                                                <tr>
                                                    <td class="campositem">
                                                        <input type="checkbox" name="FOLIOS_CONFIRMACION" value="<%=folio%>" <%if(foliosConf.contains(folio)){%>checked="checked"<%}%> /><%=i%>
                                                    </td>
                                                    <td class="campositem"><%=folio%> </td>
                                                    <td width="40"><img name="btn_mini_verdetalles.gif" src="/SNR/jsp/images/btn_mini_verdetalles.gif" width="35" height="13"  style="cursor:'hand'"  border="0" onClick="verDetalle('consultar.folio.traslado.do?ACCION=CONSULTAR_FOLIO&VER_FOLIO_TRASLADO=VER_FOLIO_TRASLADO','Folio','<%=folio%>')"></td>
                                                </tr>

                                                <%
                                                    }

                                                %>
                                            </table>

                                        </div>
                                            <div>
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                                                <tr>
                                                    <td width="12"><img name="sub_r1_c1"
                                                                        src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
                                                                        width="12" height="22" border="0" alt="">
                                                    </td>
                                                    <td
                                                        background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
                                                        class="bgnsub">Edicion Datos
                                                    </td>
                                                    <td width="16" class="bgnsub"><img
                                                            src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif"
                                                            width="16" height="21">
                                                    </td>
                                                    <td width="16" class="bgnsub"><img
                                                            src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
                                                            width="16" height="21">
                                                    </td>
                                                    <td width="15"><img name="sub_r1_c4"
                                                                        src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
                                                                        width="15" height="22" border="0" alt="" >
                                                    </td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellpadding="0" cellspacing="1" class="camposform">
                                                <tr>
                                                    <td  align="right">Departamento</td>
                                                    <td >
                                                        <%
                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
                                                            textHelper2.setSize("5");
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
                                                            textHelper2.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO');");
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);

                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
                                                            textHelper2.setSize("35");
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);
                                                        %></td>
                                                    <td align="right">Municipio</td>
                                                    <td >
                                                        <%
                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
                                                            textHelper2.setSize("6");
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
                                                            textHelper2.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);

                                                            textHelper2.setSize("35");
                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);
                                                        %></td>
                                                    <td align="right">Vereda</td>
                                                    <td>
                                                        <%
                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
                                                            textHelper2.setSize("6");
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
                                                            textHelper2.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);

                                                            textHelper2.setSize("35");
                                                            textHelper2.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                                                            textHelper2.setCssClase("camposformtext");
                                                            textHelper2.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                                                            textHelper2.setReadonly(false);
                                                            textHelper2.render(request, out);
                                                        %></td>
                                                    <td>
                                                        <a href="javascript:locacion('seleccionar.locacion.do?LOCACIONES_CIRCULO=<%=circulo.getIdCirculo()%>&<%=WebKeys.CIRCULO%>=<%=circulo.getIdCirculo()%>','ANOTACION_OFICINA_DOCUMENTO','width=790,height=175,menubar=no');"><img
                                                                src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif"
                                                                width="16"
                                                                height="21" border="0">
                                                        </a>
                                                    </td>
                                                </tr>
                                            </table>
                                            <br>
                                            <P>&nbsp;</P>
                           <%--                      
                            /**
                            * @author     : Carlos Torres
                            * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                            */
                            --%>                                     
       <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Fundamento</td>
                </tr>
              </table>
              
              <% if (fundamentosAsociadosDestino != null && !fundamentosAsociadosDestino.isEmpty()) { %>
                    <table width="100%" border="0" class="camposform">
                        <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                            <td colspan="4">Fundamentos de esta solicitud</td>
                        </tr>
                        
                        <tr>
                            <td width="20">&nbsp;</td>
                            <td width="5px">&nbsp;</td>
                            <td class="titulotbcentral">Tipo</td>
                            <td class="titulotbcentral">Número</td>
                            <td class="titulotbcentral">Fecha</td>
                        </tr>
                        <%  
                            String f = null;
                            String[] s = null;
                            Iterator item;
                            item = fundamentosAsociadosDestino.iterator();
                            while (item.hasNext()) {
                                f = item.next().toString();
                                s = f.split(";");
                        %>
                        <tr>
                            <td width="20px">&nbsp;</td>
                            <td width="5px" class="campositem">
                                <input type="checkbox" id="<%= f %>" name="<%= WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX+"_DESTINO" %>" value="<%= f %>"/>
                            </td>
                            <td class="campositem"><%= s[1] %></td>
                            <td class="campositem"><%= s[2] %></td>
                            <td class="campositem"><%= s[3] %></td>
                        </tr>
                        <% } %>
                    </table>
                    
                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                        <tr>
                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                            <td>Eliminar Fundamento</td>
                            <td><a href="javascript:cambiarAccionDestido('ELIMINAR_FUNDAMENTO_MASIVO_DESTINO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a></td>
                        </tr>
                    </table>
                    <P>&nbsp;</P>
            <% } %>
                          <table width="100%" border="0" class="camposform">
                  <tbody>
                      <tr>
                          <td width="20" height="18">&nbsp;</td>
                          <td class="titulotbcentral">Tipo</td>
                          <td>
                              <% try {
                                    ListaElementoHelper tiposFundamentoHelper = new ListaElementoHelper();
                                    tiposFundamentoHelper.setOrdenar(false);
                                    tiposFundamentoHelper.setNombre(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR+"_DESTINO");
                                    tiposFundamentoHelper.setId(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR+"_DESTINO");
                                    tiposFundamentoHelper.setCssClase("camposformtext");
                                    tiposFundamentoHelper.setTipos(tiposFundamentoD);
                                    tiposFundamentoHelper.render(request, out);
                                } catch(HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                              %>
                          </td>
                          <td class="titulotbcentral">N&uacute;mero</td>
                          <td>
                              <input name="<%=CFolio.NUMERO_FUNDAMENTO+"_DESTINO"%>" id="<%=CFolio.NUMERO_FUNDAMENTO+"_DESTINO"%>" type="text" value="<%=numeroFundamentoDestino%>" class="camposformtext">
                          </td>
                          <td class="titulotbcentral">Fecha</td>       
                          <td>
                              <table border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                      <tr>
                                          <td>
                                              <% try {
                                                        TextHelper textHelper = new TextHelper();
                                                        textHelper.setNombre(CSolicitudRegistro.CALENDAR+"_DESTINO");
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(CSolicitudRegistro.CALENDAR+"_DESTINO");
                                                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"_DESTINO"+"');\" "
                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"_DESTINO"+"')\"   onBlur=\"javascript:validarFecha()\"" );
                                                        textHelper.setReadonly(false);
                                                        textHelper.render(request,out);
                                                     } catch(HelperException  re) {
                                                         out.println("ERROR " + re.getMessage());
                                                     }
                                              %>
                                          </td>
                                          <td align="left">
                                              <a href="javascript:NewCal('calendar_destino','ddmmmyyyy',true,24)">
                                                  <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" 
                                                       alt="Fecha" width="16" height="21" border="0" 
                                                       onClick="javascript:Valores('<%=request.getContextPath()%>')">
                                              </a>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </td>
                          <td align="right">
                              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                  <tbody>
                                      <td><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                      <td>Agregar Fundamento</td>
                                      <td align="right">
                                          <a href="javascript:cambiarAccionDestido('AGREGAR_FUNDAMENTO_MASIVO_DESTINO');" name="imageField">
                                              <img alt="[add fundamento]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                          </a>
                                      </td>
                                  </tbody>
                              </table>
                          </td>
                      </tr>
                  </tbody>
              </table>
              <br />

                                            <P>&nbsp;</P>
                                            <hr class="linehorizontal" />
                                            <hr class="linehorizontal">
                                            <table width="100%" class="camposform">
                                                <tr>
                                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                    <td width="155">
                                                        <a href="javascript:cambiarAccionAndSend('TRASLADO_CONFIRMACION_MASIVO','TRASLADO_DESTINO')">
                                                            <image src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif"  width="150" height="21" border="0"  />
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="javascript:cambiarAccionAndSend('<%=AWAdministracionForseti.TERMINA%>','TRASLADO_DESTINO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0" ></a>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        </div>          
                                    </td>
                                </tr>
                            </table>
                            <%

                                }
                            %>

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
<div id="window" class="windowUnload">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
  
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Procesando</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    
  </tr>
  <tr> 
   
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="12">&nbsp;</td>
        <td width="12"><img name="tabla_error_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Ejecutando 
                  Proceso</td>
              <td width="14"><img name="tabla_error_r1_c4" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_error_r1_c6" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
            <tr>
                <td align="center" valign="middle"> 
                <image src="<%=request.getContextPath()%>/jsp/images/ico_enespera.gif" width="20px" />
                  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tablas">
                    <tr>
                      <td align="center">Un momento por favor. El sistema esta 
                        ejecutando la operaci&oacute;n seleccionada.</td>
                    </tr>
                </table></td>
                <td width="70">&nbsp;</td>
            </tr>
        </table></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
        <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>

  </tr>
  <tr> 
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
  </tr>
</table>
</div>
<div id="sombra" class="sombraUnload"></div>
