<%@page import="org.auriga.core.web.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import="gov.sir.core.negocio.modelo.CheckItem"%>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTurnoManualCertificado"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTurnoManualRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso"%>

<%
	//Si el objeto de Proceso no existe, lo creo para proceso de certificados
	Proceso proceso = (Proceso)request.getAttribute(WebKeys.PROCESO);
	if(proceso == null) {
		proceso = new Proceso();
		proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_REGISTRO));
		proceso.setNombre("REGISTRO");
		request.getSession().setAttribute(WebKeys.PROCESO, proceso);
	}
	
   	TextAreaHelper textArea = new TextAreaHelper();
   	ListaElementoHelper subtipo = new ListaElementoHelper();
	ListaElementoHelper respuestaCalificacion = new ListaElementoHelper();
   	ListaElementoHelper tipoEncabezado= new ListaElementoHelper();
   	OficinaHelper oficinaHelper = new OficinaHelper();
   	ListaCheckboxHelper docHelper = new ListaCheckboxHelper();
   	TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
   	//MatriculaHelper matHelper = new MatriculaHelper();
   	List matriculasAsociadas=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

   	String ocultarAS=(String)session.getAttribute("VER_ANTIGUO_SISTEMA");
   	if (ocultarAS==null){
   		ocultarAS="TRUE";
   	}
   	else{
   		session.setAttribute("VER_ANTIGUO_SISTEMA",ocultarAS);
   	}

	/*if(session.getAttribute(AWTurnoManualCertificado.TURNO_ANIO) == null) {
		Calendar cCalendar = Calendar.getInstance();
		String sAnio = "" + cCalendar.get(Calendar.YEAR);
		session.setAttribute(AWTurnoManualCertificado.TURNO_ANIO, sAnio);
	}*/

   	String ultimo= null;
   	List subtipos = new ArrayList();
   	List subtiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);

   	if (subtiposObj!= null && subtiposObj.size()>0){
	    Iterator itActo =subtiposObj.iterator();
		ultimo=(String)session.getAttribute("ultimo_campo_editado");
		session.removeAttribute("ultimo_campo_editado");
		while(itActo.hasNext()){
		    SubtipoSolicitud sub=(SubtipoSolicitud)itActo.next();
		    if (sub!= null){
		        String idTipo = sub.getIdSubtipoSol();
		        String nombre = sub.getNombre();
		        if (idTipo!= null && nombre!= null){
		        	ElementoLista elemLista = new ElementoLista(idTipo,nombre);
 				    subtipos.add(elemLista);
				}
			}
		}
   	}


    Object ss = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
    Object lsc = request.getSession().getAttribute("LISTA_SUBTIPO_CHEQUEO");
    if ( ss == null || lsc == null) {
        String sel = "1";
        request.getSession().setAttribute("SUBTIPO_SOLICITUD", sel);
		List lsubtipos = (List) request.getSession().getServletContext().getAttribute(
				WebKeys.LISTA_SUBTIPOS_SOLICITUD);
		if (lsubtipos != null && lsubtipos.size()>0){
			Iterator it = lsubtipos.iterator();
			List chequeo = new ArrayList();
			while (it.hasNext()) {
				SubtipoSolicitud sub = (SubtipoSolicitud) it.next();
				if (sel.equals(sub.getIdSubtipoSol())) {
					List checked = sub.getCheckItems();
					Iterator it2 = checked.iterator();
					while (it2.hasNext()) {
						CheckItem ch = (CheckItem) it2.next();
						if(ch!= null){
							chequeo.add(new ElementoLista(ch.getIdCheckItem(), ch.getNombre()));
						}
					}
					request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO", chequeo);
				}
			}
		}
   	}

	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}

// Definicion Respuesta Calificacion
ListaRadioHelper radioHelper = new ListaRadioHelper();
ElementoLista a1 = new ElementoLista(CRespuestaCalificacion.RESPUESTA_INSCRITO,CRespuestaCalificacion.INSCRITO);
ElementoLista a2 = new ElementoLista(CRespuestaCalificacion.RESPUESTA_DEVUELTO,CRespuestaCalificacion.DEVUELTO);
List respuestas = new ArrayList();
respuestas.add(a1);
respuestas.add(a2);
radioHelper.setTipos(respuestas);


%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>

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

  	function matriculaFocus(){/*
  		<%
	  	if (ultimo!=null && ultimo.equals("ID_MATRICULA")){
	  		Integer nume = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
	  		String campo=CFolio.ID_MATRICULA+nume.toString();
	  		%>
	  		document.REGISTRO.<%=campo%>.focus();
  		<%}
	  	%>*/
  	}

	function validacion (){
		ultimo_campo_editado="<%=ultimo%>";
		document.getElementById("ultimo_campo_editado").value=ultimo_campo_editado;
		vinculo="#"+ultimo_campo_editado;
		document.location.href=vinculo
		elemento=document.getElementById("ultimo_campo_editado").value
		if(document.getElementById(ultimo_campo_editado+"_img")!=null){document.getElementById(ultimo_campo_editado+"_img").className="imagen_campo_editar"}
	}

	function campoactual(id){
		if (document.getElementById("ultimo_campo_editado")!=null){
			document.getElementById("ultimo_campo_editado").value=id;
		}
	}

	function asociarTurno() {
	    document.REGISTRO.ACCION.value = 'VALIDAR_TURNO_ANTERIOR';
	    document.REGISTRO.submit();
	}

	function liquidarRegistro(){
		document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
		cambiarAccion('LIQUIDAR_REGISTRO');
	}

	function terminar() {
		cambiarAccion('TERMINA');
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

	function cargarSubTipo(){
		   <%if (request.getSession().getAttribute("SUBTIPO_SOLICITUD") != null){
		   		Object b = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
		   		System.out.print(b.getClass().getName());
		   		if(b!=null){
			   		try {
			   		  String ssol = (String) b;
			   		  subtipo.setSelected(ssol);
			   		} catch (Exception e){
			   		  subtipo.setSelected(null);
			   		}
		   		}
		   	}%>
	}

	function cargarRespuestaCalificacion(){
		   <%if (request.getSession().getAttribute("SUBTIPO_SOLICITUD") != null){
		   		Object b = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
		   		System.out.print(b.getClass().getName());
				System.out.print("CARGANDO REPUESTA CALIFICACION");
		   		if(b!=null){
			   		try {
			   		  String ssol = (String) b;
			   		  subtipo.setSelected(ssol);
			   		} catch (Exception e){
			   		  subtipo.setSelected(null);
			   		}
		   		}
		   	}%>
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

	function quitar(text) {
		document.REGISTRO.ITEM.value = text;
		cambiarAccion('ELIMINAR');
	}

	function setTipoOficina(){
	/*	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
		document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
		document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
	*/
	}

	function oficinas(nombre,valor,dimensiones){
		document.REGISTRO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
                 /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                document.getElementById('version').value=valor+"_OFICINA_VERSION";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
		popup.focus();
	}

	function juridica(nombre,valor,dimensiones){
		document.getElementById('natjuridica_id').value=valor+"_ID";
		document.getElementById('natjuridica_nom').value=valor+"_NOM";
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}

	function verAnotacion(nombre,valor,dimensiones,pos){
		document.CREAR.POSICION.value=pos;
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}

	function locacion(nombre,valor,dimensiones){
		document.getElementById('id_depto').value=valor+"_ID_DEPTO";
		document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
		document.getElementById('id_munic').value=valor+"_ID_MUNIC";
		document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
		document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
		document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	    popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
	}

	function cambiarValorTipoDocumento(text){
        try{
        	document.getElementById('TIPO_ENCABEZADO').options[text].selected=true;
        }catch(e){
        	document.getElementById('TIPO_ENCABEZADO').value=' <%=WebKeys.SIN_SELECCIONAR%>';
        	document.getElementById('ID_TIPO_ENCABEZADO').value='';
        }
   }

	function habilitarModificacion(){
		document.all.display_modificacion_off.style.display = 'none';
		document.all.display_modificacion_on.style.display = 'block';
		document.all.display_modificacion_1.style.display = 'block';
		document.all.display_modificacion_2.style.display = 'block';
		document.all.display_modificacion_3.style.display = 'block';
	}
	function desHabilitarModificacion(){
		document.all.display_modificacion_off.style.display = 'block';
		document.all.display_modificacion_on.style.display = 'none';
		document.all.display_modificacion_1.style.display = 'none';
		document.all.display_modificacion_2.style.display = 'none';
		document.all.display_modificacion_3.style.display = 'none';
	}

	function modificarLiquidacion(text,anio,circulo,proceso,sec) {
		if(document.REGISTRO.AUX.value!=""){
			document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+document.REGISTRO.AUX.value;
		    document.REGISTRO.ACCION.value = text;
		    document.REGISTRO.submit();
	    }
	}

	cargarSubTipo();
	cargarRespuestaCalificacion();
</script>
<script type="text/javascript">

function jsEvent_VincularTurno_AddItem() {
    // send action-event: VALIDAR_TURNO_VINCULADO
    var actionId = 'REGISTRO_VINCULARTURNO_ADDITEM_ACTION' ;//'VALIDAR_TURNO_VINCULADO';
    cambiarAccion( actionId );
}
function jsEvent_VincularTurno_DelItem() {
    var actionId = 'REGISTRO_VINCULARTURNO_DELITEM_ACTION' ;//'ELIMINAR_TURNO_VINCULADO';
    cambiarAccion( actionId );
}


</script>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"	rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<input name="Depto" type="hidden" id="id_depto" value="">
	<input name="Depto" type="hidden" id="nom_Depto" value="">
	<input name="Mpio" type="hidden" id="id_munic" value="">
	<input name="Mpio" type="hidden" id="nom_munic" value="">
	<input name="Ver" type="hidden" id="id_vereda" value="">
	<input name="Ver" type="hidden" id="nom_vereda" value="">

	<tr>
		<td width="12">&nbsp;</td>
		<td width="12"><img name="tabla_gral_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif"
			width="12" height="23" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif"
					class="titulotbgral">Registro de Documentos</td>
				<td width="28"><img name="tabla_gral_r1_c3"
					src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif"
					width="28" height="23" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td width="12"><img name="tabla_gral_r1_c5"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif"
			width="12" height="23" border="0" alt=""></td>
		<td width="12">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>









				<form action="turnoManualRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
					<input type="hidden" name="ACCION" id="ACCION" value="LIQUIDAR_REGISTRO">
					<input name="ultimo_campo_editado" type="hidden" id="ultimo_campo_editado">
					<input type="hidden" name="VER_ANTIGUO_SISTEMA"	id="VER_ANTIGUO_SISTEMA">
					<input type='image' src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="00" height="00">

				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"> </td>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>



			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">Solicitud de Registro</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_new.gif"
									width="16" height="21"></td>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>


			<tr>
				<td width="7"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">


    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    	<tr> 
        	<td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        	<td class="bgnsub">Turno</td>
        	<td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        	<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
    	</tr>
	</table>
    <br>
   	<table width="100%" class="camposform">
		<tr> 
			<td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			<%--<td>A&ntilde;o</td>
			<td>
				<% 
					try {
					
						TextHelper textHelper = new TextHelper();
						textHelper.setNombre(AWTurnoManualCertificado.TURNO_ANIO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualCertificado.TURNO_ANIO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>--%>
			<td>C&iacute;rculo</td>
			<td>
				<% 
					try {
					
						TextHelper textHelper = new TextHelper();
						textHelper.setNombre(AWTurnoManualRegistro.TURNO_CIRCULO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualRegistro.TURNO_CIRCULO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>
			<td>Consecutivo</td>
			<td>
				<% 
                	try {
						TextHelper textHelper = new TextHelper();
						textHelper.setNombre(AWTurnoManualRegistro.TURNO_CONSECUTIVO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualRegistro.TURNO_CONSECUTIVO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>
			<td>Fecha de Radicaci&oacute;n</td>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<% 
								try {
 		                        	TextHelper textHelper = new TextHelper();
									textHelper.setNombre(AWTurnoManualRegistro.FECHA_INICIO);                  			    	
                  			    	textHelper.setCssClase("camposformtext");
                  			    	textHelper.setId(AWTurnoManualRegistro.FECHA_INICIO);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
							%>
						</td>
						<td>&nbsp;
							<a href="javascript:NewCal('FECHA_INICIO','ddmmmyyyy',true,24)">
								<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									alt="Fecha" width="16" height="21" border="0"
									onClick="javascript:Valores('<%=request.getContextPath()%>')">
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				<tr> 
					<td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
					<td class="bgnsub">Recibo</td>
					<td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
					<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
				</tr>
			</table>
			<br>
            <table width="100%" class="camposform">
				<tr> 
					<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td>N&uacute;mero de Recibo</td>
                  	<td> 
     					<% 
     						try {
 		                        TextHelper textHelper = new TextHelper();
								textHelper.setNombre(AWTurnoManualRegistro.NUMERO_RECIBO);
                  			    //textHelper.setNombre(AWTurnoManualCertificado.NUMERO_RECIBO);
                  			    textHelper.setCssClase("camposformtext");
                  			    //textHelper.setId(AWTurnoManualCertificado.NUMERO_RECIBO);
								textHelper.setId(AWTurnoManualRegistro.NUMERO_RECIBO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    
                  	</td>
				</tr>
			</table>





				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Subtipo de Solicitud</td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Subtipo</td>
						<td><% try {
               				subtipo.setTipos(subtipos);
               				subtipo.setNombre(WebKeys.SUBTIPO_SOLICITUD);
              			    subtipo.setCssClase("camposformtext");
              			    subtipo.setId(WebKeys.SUBTIPO_SOLICITUD);
              			    //subtipo.setFuncion("onChange=\"agregarSubtipo('CARGAR_DOCUMENTOS')\"");
              			    //subtipo.setFuncion("onChange=\"guardarSubTipo(this.value)\"");
							subtipo.render(request,out);
						} catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%></td>
					</tr>
				</table>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Resultado Calificación</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
			  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table><br>
          <table width="100%" class="camposform">
            <tr>
              <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
              <td class="titulotbcentral">Respuesta</td>
            </tr>
            <% try {
					if(session.getAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION)== null) {
						session.setAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION,CRespuestaCalificacion.INSCRITO);
					}
					
					radioHelper.setNombre(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION);
                  	radioHelper.setCssClase("camposformtext");
                  	radioHelper.setId(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION);
					radioHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
          </table><br>

				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Asociar una Matr&iacute;cula</td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<%if (matriculasAsociadas!=null && !matriculasAsociadas.isEmpty()){%>
				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15"></td>
						<td>Folios de esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td>
							<% 
								try {
			                      	tablaHelper.setColCount(5);
			                      	tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
			                      	tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
			
			               		  	tablaHelper.render(request, out);
                    			} catch(HelperException re) {
                      				out.println("ERROR " + re.getMessage());
                    			}
                  			%>
                  		</td>
					</tr>
				</table>
				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15" /></td>
						<td>Eliminar Seleccionadas</td>
						<td><a href="javascript:eliminarMatriculas()"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0" /></a></td>
					</tr>
				</table>
				<P>&nbsp;</P>
				<%}%>
				<table width="100%" class="camposform">
					<tr></tr>
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula</td>
						<td><%=idCirculo%>-<input name="AGREGAR_MATRICULA_REGISTRO"
							id="AGREGAR_MATRICULA_REGISTRO" type="text"
							value=""
							onFocus="campoactual('AGREGAR_MATRICULA_REGISTRO'); cambiarAccionForm('AGREGAR');"
							class="camposformtext"> <img id="AGREGAR_MATRICULA_REGISTRO_img"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo"></td>
					</tr>
				</table>
				
				<table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
						<td width="20"><img
							src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15"></td>
						<td>Agregar Matr&iacute;cula</td>
						<td align="right">
							<a href="javascript:agregarMatricula('AGREGAR')">
							<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
								width="35" height="25" border="0">
							</a>
						</td>
					</tr>
				</table>
				<br>

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
				<br>

				<table border="0" width="100%" cellpadding="0" cellspacing="2"
					id="OCULTAR">
					<%if(ocultarAS.equals("FALSE")){%>
					<tr>
						<td>
						<hr class="linehorizontal">
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

                    	ash.render(request, out);
					%> <!-- Fin Mostrar helper de antiguo sistema --> <%} else {%> <%}%>
				<br>
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
                  			    textArea.setCssClase("camposformtext");
                  			    textArea.setId("COMENTARIO");
								textArea.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
					</tr>
				</table>
				<!--
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				<!--
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de Documentos Entregados </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Documentos Entregados </td>
                </tr>


                <% try {
	 		                    List tipos = (List) session.getAttribute("LISTA_SUBTIPO_CHEQUEO") ;
                   				if (tipos==null){
                   					tipos = new Vector();
                   				}
                   				docHelper.setTipos(tipos);
                  			    docHelper.setCssClase("campositem");
                  			    docHelper.setId("DOCUMENTOS_ENTREGADOS");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>


              </table>
              -->
				<hr class="linehorizontal">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td class="bgnsub">Encabezado del Documento</td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Datos B&aacute;sicos</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
						<table width="100%" class="camposform">
							<tr>
								<td>Tipo</td>
								<td><% try {
                        		TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value); cambiarAccionForm('LIQUIDAR_REGISTRO');\"");
                  			    textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setEditable(true);
								textHelper.render(request,out);
								textHelper.setFuncion("");
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<td><% try {
	 		                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                  			    tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
								tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
                   				tipoEncabezado.setOrdenar(false);
								tipoEncabezado.setTipos(tiposDoc);
                  			    tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
                  			    tipoEncabezado.setCssClase("camposformtext");
                  			    tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);
								tipoEncabezado.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<script>
                        	document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                        	if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                        		document.getElementById('ID_TIPO_ENCABEZADO').value='';
                        	}
                        </script>
								<td>N&uacute;mero</td>
								<td><% try {
                        		TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<td>Fecha</td>
								<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><% try {
                              	TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CSolicitudRegistro.CALENDAR);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
										<td><a
											href="javascript:NewCal('calendar','ddmmmyyyy',true,24)"><img
											src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
											alt="Fecha" width="16" height="21" border="0"
											onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Oficina de Procedencia</td>
					</tr>
					<!-- EL HELPER DE OFICINA EMPIEZA ACA -->
					<jsp:include page="../registro/HELPER_OFICINAS.jsp" flush="true" />
					<!-- EL HELPER DE OFICINA TERMINA ACA -->
					<!--</table>-->
					<hr class="linehorizontal">
					<table width="100%" class="camposform">
						<tr>
							<td width="20"><img
								src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
								width="20" height="15"></td>
							<td width="140"><a href="javascript:liquidarRegistro()"><img
								src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif"
								name="Siguiente" width="139" height="21" border="0"
								id="Siguiente"></a></td>
							<td width="139"><a href="javascript:terminar()"><img 
								src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" 
								width="139" height="21" border="0"></a></td>
							<td>&nbsp;</td>
						</tr>
					</table>



					<td width="11"
						background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>

					<tr>
						<td><img name="tabla_central_r3_c1"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
							width="7" height="6" border="0" alt=""></td>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							width="15" height="6"></td>
						<td><img name="tabla_central_pint_r3_c7"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
							width="11" height="6" border="0" alt=""></td>
					</tr>
				</table>
					</form>
				</td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><img name="tabla_gral_r3_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif"
					width="12" height="20" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
				<td><img name="tabla_gral_r3_c5"
					src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif"
					width="12" height="20" border="0" alt=""></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<script>
	matriculaFocus();
	validacion();
</script>
