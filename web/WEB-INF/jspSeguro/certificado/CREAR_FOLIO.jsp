<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.registro.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.DireccionesHelper"%>


<%ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
            MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
            List tiposPredio = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_TIPOS_PREDIO);
            if (tiposPredio == null) {
                tiposPredio = new ArrayList();
            }

            tipoPredioHelper.setTipos(tiposPredio);
            tipoPredioHelper.setCssClase("camposformtext");

            ListaElementoHelper ejesHelper = new ListaElementoHelper();
            List ejes = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_EJES_DIRECCION);
            if (ejes == null) {
                ejes = new ArrayList();
            }
            ejesHelper.setCssClase("camposformtext");
            ejesHelper.setOrdenar(false);
            ejesHelper.setTipos(ejes);

            ListaElementoHelper ejes2Helper = new ListaElementoHelper();
            ejes2Helper.setCssClase("camposformtext");
            ejes2Helper.setOrdenar(false);
            ejes2Helper.setTipos(ejes);
            CiudadanosAnotacionHelper ciudadanosHelper = new CiudadanosAnotacionHelper();
            TextHelper textHelper = new TextHelper();
            TextHelper hiddenHelper = new TextHelper();
            hiddenHelper.setTipo("hidden");
            //hiddenHelper.setTipo("text");
            TextAreaHelper textAreaHelper = new TextAreaHelper();
            AnotacionesFolioHelper anotacionesHelper = new AnotacionesFolioHelper();
            AntiguoSistemaBasicoHelper antSistHelper = new AntiguoSistemaBasicoHelper();
            Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Solicitud solicitud = turno.getSolicitud();
            Documento documento = null;
            //Tipo de documento
            String tipoDoc = "";

            if (solicitud instanceof SolicitudRegistro) {
                SolicitudRegistro registro = (SolicitudRegistro) solicitud;
                documento = registro.getDocumento();
                if (documento != null) {
                    TipoDocumento tipoDocumento = documento.getTipoDocumento();
                    if (tipoDocumento != null) {
                        tipoDoc = tipoDocumento.getNombre();
                    }
                }
            } else if (solicitud instanceof SolicitudCorreccion) {
                // datos de la solicitud para obtener el tipo de documento ?
                // SolicitudCorreccion solicitudCorreccionInstance = (SolicitudCorreccion)  solicitud;
                tipoDoc = "CORRECCIONES";

            } else {
                tipoDoc = "CERTIFICADO";
            }
            NotasInformativasHelper helper = new NotasInformativasHelper();

            TextAreaHelper textAreaComplementacionHelper = new TextAreaHelper();
            Complementacion complementacion = (Complementacion) session
                    .getAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
            if (complementacion != null) {
                textAreaComplementacionHelper.setReadOnly(true);
                textAreaComplementacionHelper.setCssClase("campositem");
            } else {
                textAreaComplementacionHelper.setCssClase("camposformtext");
            }
            session.setAttribute(CVereda.MOSTRAR_VEREDA, "ASDFASDFASD");

            //Helper para las direcciones		
            gov.sir.core.web.helpers.correccion.DireccionesHelper dirHelper = new DireccionesHelper(
                    gov.sir.core.web.acciones.registro.AWFolio.AGREGAR_DIRECCION,
                    gov.sir.core.web.acciones.registro.AWFolio.ELIMINAR_DIRECCION);
            dirHelper.setFuncionCambiarAccion("cambiarAccionSubmit");
            dirHelper.setFuncionQuitar("quitarDireccion");
            dirHelper.setNombreFormaEdicionDireccion("CREAR");
            
            dirHelper.setFolioSesion(WebKeys.FOLIO_EDITADO);

            %>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

function cambiarAccion(text) {
	document.CREAR.ACCION.value = text;
}

function cambiarAccionSubmit(text) {
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}

function Secuencia(text){
	document.CREAR.<%=CAnotacionCiudadano.SECUENCIA%>.value = text;
	cambiarAccionSubmit('<%=AWCalificacion.REFRESCAR_ANOTACION%>');
}

function quitarDireccion(pos,accion){
	if(confirm("Esta seguro que desea eliminar la dirección ?")){
		document.CREAR.POSICION.value = pos;
		cambiarAccionSubmit(accion);		
	}
}

function quitar(pos,accion) {
	document.CREAR.POSICION.value = pos;
	cambiarAccionSubmit(accion);
}
function setTipoOficina(){
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}

function editarAnotacion(text){
	document.getElementById("NUM_ANOTACION_TEMPORAL").value = text
	cambiarAccionSubmit('EDITAR_ANOTACION');
}

function oficinas(nombre,valor,dimensiones)
{
	var idDepto = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CREAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function locacionTipoPredio(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	if(document.CREAR.<%=CFolio.FOLIO_TIPO_PREDIO%>.value == '<%=gov.sir.core.negocio.modelo.constantes.CTipoPredio.TIPO_URBANO%>'){	
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.NO_MOSTRAR_VEREDA%>',valor,dimensiones);
	} else {
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
	}
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
function cambiarImagen() {
	if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.COPIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.ASOCIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else{
		document.all.caja.style.display = 'none'
		document.all.boton.style.display = 'none'
	}
}
function consultarFolio(text) {
	document.CREAR.action = 'certificado.do';
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}

function reescribirfecha(valor,id){
	var my_str = valor;
	if(my_str.search("/")==-1){
		var separator = "/";
		uno= my_str.slice(0,2);
		dos = my_str.slice(2,4);
		tres = my_str.slice(4,my_str.length);
		document.getElementById(id).value = "";
		my_str = uno+separator+dos+separator+tres;
		document.getElementById(id).value = my_str;
	}
}

function reescribirValor(valor,id){
	var my_str = valor;
	var miles=1;
	if(my_str.indexOf(".")==-1){
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=my_str.length;i++){
				var desde = my_str.length-i*3;
				var hasta = my_str.length-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr=nStr+".00";
		document.getElementById(id).value = nStr;
		}
	} else {
		var largo = my_str.indexOf(".");
		var centavos = my_str.substr(largo,my_str.length);
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=largo;i++){
				var desde = largo-i*3;
				var hasta = largo-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr = nStr+centavos;
		document.getElementById(id).value = nStr;
		}
	}
}
</script>
<%//Cargar nombre del proceso para colocar título de la página
            long proc = turno.getIdProceso();
            String nombreProceso = "";

            if (proc == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                nombreProceso = " Certificados";
            } else if (proc == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                nombreProceso = " Registro";
            } else if ((proc == Long.parseLong(CProceso.PROCESO_CORRECCION))
                    || (proc == Long.parseLong(CProceso.PROCESO_CORRECCIONES))) {
                nombreProceso = " Correccion > Antiguo Sistema";
            } else {
                nombreProceso = " Antiguo Sistema";
            }
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
		<td width="12"><img name="tabla_gral_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif"
			width="12" height="23" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif"
					class="titulotbgral">Digitar hoja de Ruta - <%=nombreProceso%></td>
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
			<form action="radicacion.do" name="CREAR" id="CREAR" method="post"><input
				type="hidden" name="ACCION" id="ACCION" /> <input type="hidden"
				name="NUM_ANOTACION_TEMPORAL" id="NUM_ANOTACION_TEMPORAL" value="" />
			<input name="Depto" type="hidden" id="id_depto" value="" /> <input
				name="Depto" type="hidden" id="nom_Depto" value="" /> <input
				name="Mpio" type="hidden" id="id_munic" value="" /> <input
				name="Mpio" type="hidden" id="nom_munic" value="" /> <input
				name="Ver" type="hidden" id="id_vereda" value="" /> <input
				name="Ver" type="hidden" id="nom_vereda" value="" /> 
				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1" />
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
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
							class="titulotbcentral">FOLIOS</td>
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
									src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
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
				<td valign="top" bgcolor="#79849B" class="tdtablacentral"><!-- Mostrar el campo número de matrícula para agregar la primera vez
				No se muestra si el sistema la genera --> <%String idMatricula = (String) session
                    .getAttribute(CFolio.FOLIO_ID_MATRICULA);
            idMatricula = idMatricula == null ? "" : idMatricula;

            if (idMatricula.length() == 0) {

                %>
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif"
							width="20" height="15"></td>
						<td width="20" class="campositem">N&ordm;</td>
						<td><%try {
                    textHelper.setNombre(CFolio.FOLIO_ID_MATRICULA);
                    textHelper.setCssClase("camposformtext");
                    textHelper.setId(CFolio.FOLIO_ID_MATRICULA);
                    textHelper.render(request, out);
                } catch (HelperException re) {
                    out.println("ERROR " + re.getMessage());
                }%>&nbsp;</td>
					</tr>
				</table>
				<%}//if idMatricula %>
				<table width="100%" class="camposform" border="0">
					<tr>
						<td width="20"></td>
						<td width="100">Tipo de Turno:</td>
						<%Field[] campos = CProceso.class.getDeclaredFields();
            String nombre = "No se puedo obtener el proceso donde se creo el turno";
            for (int i = 0; i < campos.length; i++) {
                Field campo = campos[i];
                if (campo.get(campo.getName()).equals(
                        String.valueOf(turno.getIdProceso()))) {
                    nombre = campo.getName();
                }

            }%>
						<td class="campositem"><%=nombre.replaceAll("PROCESO_", "")%></td>
				</td>
				<td width="20"></td>
				<td width="100">Fecha de Turno:</td>
				<td class="campositem"><%=DateFormatUtil.format(turno.getFechaInicio())%>&nbsp;</td>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
	<tr>
		<td width="12"><img name="sub_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
			width="12" height="22" border="0" alt=""></td>
		<td class="bgnsub">Datos B&aacute;sicos</td>
		<td width="16" class="bgnsub"><img
			src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
			width="16" height="21"></td>
		<td width="15"><img name="sub_r1_c4"
			src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
			width="15" height="22" border="0" alt=""></td>
	</tr>
</table>
<table width="100%" class="camposform">
	<tr>
		<td width="20"><img
			src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
			width="20" height="15"></td>
		<td>Ubicaci&oacute;n</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
		<table width="100%" class="camposform">
			<tr>
				<td>Circulo</td>
				<td class="campositem"><%=circulo.getNombre()%>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" class="camposform">
			<tr>
				<td>Departamento</td>
				<td><!--<table border="0" align="center" cellpadding="0" cellspacing="0">-->
				<!--<tr>-->
				<td>ID: <%try {
                textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_DEPTO);
                textHelper.setSize("3");
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_ID_DEPTO);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }
            try {
                textHelper.setSize("");
                textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_DEPTO);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_NOM_DEPTO);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %>

				<td width="16"><a
					href="javascript:locacionTipoPredio('seleccionar.locacion.do?<%=AWLocacion.LOCACIONES_CIRCULO%>=<%=AWLocacion.LOCACIONES_CIRCULO%>','FOLIO_LOCACION','width=790,height=320,menubar=no');"><img
					src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif"
					alt="Permite seleccionar departamento, municipio, vereda"
					width="21" height="26" border="0"></a>
			</tr>
			<!--</table>-->
		</td>
	</tr>
</table>
<table width="100%" class="camposform">
	<tr>
		<td>Municipio</td>
		<td>ID: <%try {
                textHelper.setSize("3");
                textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_MUNIC);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_ID_MUNIC);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }
            try {
                textHelper.setSize("");
                textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_MUNIC);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_NOM_MUNIC);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
		<td>Vereda</td>
		<td>ID: <%try {
                textHelper.setSize("3");
                textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_VEREDA);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_ID_VEREDA);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }
            try {
                textHelper.setSize("");
                textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_VEREDA);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_LOCACION_NOM_VEREDA);
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
	</tr>
</table>
</td>
</tr>
</table>
<table width="100%" class="camposform">
	<tr>
		<td width="20"><img
			src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
			width="20" height="15"></td>
		<td>Apertura</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
		<table width="100%" class="camposform">
			<tr>
				<td width="190">Fecha</td>
				<td class="campositem"><%try {
                fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                fechaHelper.setDate(new Date());
                fechaHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" class="camposform">
			<tr>
				<td width="190">Tipo de Documento</td>
				<td class="campositem"><%=tipoDoc%></td>
			</tr>
		</table>

		<!--
                    <table width="100%"class="camposform">
                      <tr>
                        <td width="190">Documento</td>
                        <%if(documento==null){%>
                        <td class="campositem">&nbsp;</td>
                        <%} else {%>
                        <td class="campositem"><%=documento.getTipoDocumento().getNombre()%> <%= documento.getNumero()%></td>
                        <%}%>
                      </tr>
                    </table>
                    -->
		<table width="100%" class="camposform">
			<tr>
				<td width="190">N&uacute;mero de Radicaci&oacute;n</td>
				<td class="campositem"><%=turno.getIdWorkflow()%>&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<%if (("" + turno.getIdProceso())
                    .equals(CProceso.PROCESO_CERTIFICADOS)) {%>
	<%}%>
</table>
<table width="100%" class="camposform">
	<tr>
		<td width="20"><img
			src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
			width="20" height="15"></td>
		<td>B&aacute;sicos</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
		<table width="100%" class="camposform">
			<tr>
				<td width="20%">Tipo de Predio</td>
				<input type="hidden" name="temp" id="temp" />
				<td width="80%"><%try {
                tipoPredioHelper.setNombre(CFolio.FOLIO_TIPO_PREDIO);
                tipoPredioHelper.setId(CFolio.FOLIO_TIPO_PREDIO);
                tipoPredioHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
			</tr>


			<tr>
				<td>C&oacute;digo Catastral</td>
				<td><%try {
                textHelper.setNombre(CFolio.FOLIO_COD_CATASTRAL);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.FOLIO_COD_CATASTRAL);
                //textHelper.setFuncion("onblur=javascript:validarNumerico('"+AWFolio.FOLIO_COD_CATASTRAL+"');");
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
			</tr>


			<tr>
				<td>Descripci&oacute;n Cabida y Linderos</td>
				<td><%try {
                textAreaHelper.setNombre(CFolio.FOLIO_LINDERO);
                textAreaHelper.setCols("50");
                textAreaHelper.setRows("5");
                textAreaHelper.setCssClase("camposformtext");
                textAreaHelper.setId(CFolio.FOLIO_LINDERO);
                textAreaHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
			</tr>




		</table>





		<table width="100%" class="camposform">

			<tr>
				<td width="20%">Complementaci&oacute;n</td>
				<td>


				<table width="100%" border="0">
					<tr>

						<%String tipoComp = (String) session
                    .getAttribute(CFolio.SELECCIONAR_FOLIO
                            + AWModificarFolio.FOLIO_COMPLEMENTACION);

            %>

						<td width="20%"><select
							name="<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>"
							class="camposformtext" onchange="cambiarImagen()">
							<option value="<%=CFolio.NUEVA%>"
								<%=(tipoComp==null||tipoComp.equals(CFolio.NUEVA))?"selected":""%>>Nueva</option>
							<option value="<%=CFolio.COPIAR%>"
								<%=(tipoComp!=null&&tipoComp.equals(CFolio.COPIAR))?"selected":""%>>Copiar</option>
							<option value="<%=CFolio.ASOCIAR%>"
								<%=(tipoComp!=null&&tipoComp.equals(CFolio.ASOCIAR))?"selected":""%>>Asociar</option>
							<!--                    <option value="<%=CFolio.DESDE_ANOTACION%>">Desde Anotación....</option>-->
						</select></td>


						<%if (tipoComp == null || tipoComp.equals(CFolio.NUEVA)) {

                %>
						<td width="25%" id="caja" style="display:'none'"><input
							name="<%=CFolio.ID_MATRICULA%>" type="text"
							class="camposformtext"></td>
						<td width="25%" id="boton" style="display:'none'"><a
							href="javascript:consultarFolio('<%=gov.sir.core.web.acciones.certificado.AWCertificado.CONSULTA_FOLIO_COMPLEMENTACION%>');"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_cargar_complementacion.gif"
							name="Folio" width="190" height="21" border="0"></a></td>
						<%} else {

                %>
						<td width="25%" id="caja" style="display:''"><input
							name="<%=CFolio.ID_MATRICULA%>" type="text"
							class="camposformtext"></td>
						<td width="25%" id="boton" style="display:''"><a
							href="javascript:consultarFolio('<%=gov.sir.core.web.acciones.certificado.AWCertificado.CONSULTA_FOLIO_COMPLEMENTACION%>');"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_cargar_complementacion.gif"
							name="Folio" width="190" height="21" border="0"></a></td>
						<%}

            %>

						<td>&nbsp;</td>
					</tr>
				</table>

				</td>
			</tr>


			<tr>
				<td width="20%">&nbsp;</td>
				<td width="80%"><%try {
                textAreaComplementacionHelper
                        .setNombre(CFolio.FOLIO_COMPLEMENTACION);
                textAreaComplementacionHelper.setCols("80");
                textAreaComplementacionHelper.setRows("5");
                textAreaComplementacionHelper
                        .setId(CFolio.FOLIO_COMPLEMENTACION);
                textAreaComplementacionHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }
            try {
                hiddenHelper.setSize("");
                hiddenHelper.setNombre(CFolio.FOLIO_ID_COMPLEMENTACION);
                hiddenHelper.setCssClase("camposformtext");
                hiddenHelper.setId(CFolio.FOLIO_ID_COMPLEMENTACION);
                hiddenHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
			</tr>



		</table>
		</td>
	</tr>
</table>
<hr class="linehorizontal" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
	<tr>
		<td width="12"><img name="sub_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
			width="12" height="22" border="0" alt=""></td>
		<td class="bgnsub">Direcciones</td>
		<td width="16" class="bgnsub"><img
			src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
			width="16" height="21"></td>
		<td width="15"><img name="sub_r1_c4"
			src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
			width="15" height="22" border="0" alt=""></td>
	</tr>
</table>
<%try {
                dirHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %>


<hr class="linehorizontal" />
</td>
<td width="11"
	background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
</tr>
<tr>
	<td><img name="tabla_central_r3_c1"
		src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
		width="7" height="6" border="0" alt=""></td>
	<td
		background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
		src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15"
		height="6"></td>
	<td><img name="tabla_central_pint_r3_c7"
		src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
		width="11" height="6" border="0" alt=""></td>
</tr>
</table>
<!-- nuevo helper de ciudadanos -->

<%//HELPER VARIOS CIUDADANOS
            VariosCiudadanosAnotacionHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
            variosCiudadanosHelper
                    .setFuncionJavascriptAccion("cambiarAccionSubmit");
            variosCiudadanosHelper.setMostrarBotonAgregar(false);
            Integer auxNumFilas = (Integer) session
                    .getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
            List lciudadanos = (List) request.getSession().getAttribute(
                    WebKeys.LISTA_CIUDADANOS_ANOTACION);
            if (lciudadanos == null) {
                lciudadanos = new Vector();
            }
            int numFilas;
            if (auxNumFilas == null)
                numFilas = AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
            else
                numFilas = auxNumFilas.intValue();
            List tiposID = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_TIPOS_ID);
            List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
            List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
            List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
            List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
            variosCiudadanosHelper.setPropertiesHandly(numFilas, tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica,
                    AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS,
                    AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS,
                    AWCalificacion.AGREGAR_VARIOS_CIUDADANOS,
                    AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION, lciudadanos,
                    AWCalificacion.VALIDAR_CIUDADANO, "CREAR");

            //NUMERO DE LA SIGUIENTE ANOTACION
            String pos = (String) session
                    .getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
            if (pos == null)
                pos = "1";

            //Se mira si existen excepciones.
            Boolean exception;
            exception = (Boolean) session.getAttribute(WebKeys.HAY_EXCEPCION);
            if (exception != null) {
                variosCiudadanosHelper.setCentrar(false);
            }
            session.removeAttribute(WebKeys.HAY_EXCEPCION);
          %>
<!-- Fin nuevo helper -->
<!-- viejo helper -->
<%try {
                anotacionesHelper.setFormName("CREAR");
                anotacionesHelper
                        .setVariosCiudadanosHelper(variosCiudadanosHelper);
                anotacionesHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %>
<!-- fin viejo helper -->
</form>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
			width="7" height="10"></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
			src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10"
			height="10"></td>
		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
			width="10" height="10"></td>
	</tr>
	<tr>
		<td width="7"><img name="tabla_central_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
			width="7" height="29" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
					class="titulotbcentral">OPCIONES DEL FOLIO</td>
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
							src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
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
		<td width="11"><img name="tabla_central_pint_r1_c7"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
			width="11" height="29" border="0" alt=""></td>
	</tr>
	<tr>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		<td valign="top" bgcolor="#79849B" class="tdtablacentral">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="12"><img name="sub_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
					width="12" height="22" border="0" alt=""></td>
				<td class="bgnsub">Opciones para el Folio</td>
				<td width="16" class="bgnsub"><img
					src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif"
					width="16" height="21"></td>
				<td width="16" class="bgnsub"><img
					src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
					width="16" height="21"></td>
				<td width="15"><img name="sub_r1_c4"
					src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
					width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>

		<table width="100%" class="camposform">
			<tr>
				<td width="20"><img
					src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
					width="20" height="15"></td>
				<td width="140"><input name="imageField" type="image"
					src="<%=request.getContextPath()%>/jsp/images/btn_vistaprevia.gif"
					onClick="cambiarAccionSubmit( '<%=AWFolio.CREAR%>')" width="139"
					height="21" border="0"></td>
				</form>
				<td width="140"><a href="hoja.ruta.view"><img
					src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"
					width="139" height="21" border="0"></a></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>
	<tr>
		<td><img name="tabla_central_r3_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
			width="7" height="6" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
			src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15"
			height="6"></td>
		<td><img name="tabla_central_pint_r3_c7"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
			width="11" height="6" border="0" alt=""></td>
	</tr>
</table>

<%try {
                //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
                //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
                //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
                helper.setNombreFormulario("CREAR");
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%>

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
