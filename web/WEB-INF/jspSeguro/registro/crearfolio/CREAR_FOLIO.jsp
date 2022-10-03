<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCrearFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.DireccionesHelper"%>


<%
    MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
    
    ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
    List determInm = (List) session.getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
    if(determInm == null){
        determInm = new ArrayList();
    }
    tipoDetermInmHelper.setTipos(determInm);
    tipoDetermInmHelper.setCssClase("camposformtext");
    
    ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
    List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
    if (tiposPredio == null) {
        tiposPredio = new ArrayList();
    }
    
	tipoPredioHelper.setOrdenar(false);
    tipoPredioHelper.setTipos(tiposPredio);
    tipoPredioHelper.setCssClase("camposformtext");

    ListaElementoHelper ejesHelper = new ListaElementoHelper();
    List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
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

    TextHelper textHelper = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
    //hiddenHelper.setTipo("text");

    TextAreaHelper textAreaHelper = new TextAreaHelper();
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
    } else{
        tipoDoc=solicitud.getProceso().getNombre();
    }

    TextAreaHelper textAreaComplementacionHelper = new TextAreaHelper();
    Complementacion complementacion = (Complementacion) session
            .getAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
    if (complementacion != null) {
        textAreaComplementacionHelper.setReadOnly(true);
        textAreaComplementacionHelper.setCssClase("campositem");
    } else {
        textAreaComplementacionHelper.setCssClase("camposformtext");
    }

    //Helper para las direcciones		
    gov.sir.core.web.helpers.correccion.DireccionesHelper dirHelper = new DireccionesHelper(
    AWCrearFolio.AGREGAR_DIRECCION,
    AWCrearFolio.ELIMINAR_DIRECCION);
    dirHelper.setFuncionCambiarAccion("cambiarAccionSubmit");
    dirHelper.setFuncionQuitar("quitarDireccion");
    dirHelper.setNombreFormaEdicionDireccion("CREAR");
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function valideKey(evt){
    var code = (evt.which) ? evt.which : evt.keyCode;

    if(code==8) { 
      return true;
    } else if(code>=48 && code<=57) {
      return true;
    } else{
      return false;
    }
}
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
	document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO%>').value ="";

}

function oficinas(nombre,valor,dimensiones)
{
	var idDepto = document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
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
					class="titulotbgral">CALIFICACIÓN - CREAR FOLIO</td>
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
			<form action="crearfolio.do" name="CREAR" id="CREAR" method="post"><input
				type="hidden" name="ACCION" id="ACCION" /> <input type="hidden"
				name="NUM_ANOTACION_TEMPORAL" id="NUM_ANOTACION_TEMPORAL" value="" />
				<input name="Depto" type="hidden" id="id_depto" value="" /> <input
				name="Depto" type="hidden" id="nom_Depto" value="" /> <input
				name="Mpio" type="hidden" id="id_munic" value="" /> <input
				name="Mpio" type="hidden" id="nom_munic" value="" /> <input
				name="Ver" type="hidden" id="id_vereda" value="" /> <input
				name="Ver" type="hidden" id="nom_vereda" value="" /> 
				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1" />
				<input type="hidden" name="POSICION" id="POSICION" />
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
				<td width="7"background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
	<!-- Mostrar el campo número de matrícula para agregar la primera vez.	No se muestra si el sistema la genera --> 
	<!-- Se quito el campo de matricula por peticion realizada en el bug numero 6088 -->			
				
				

<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
				<td class="campositem"><%
				try {
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
                    <table class="camposformnoborder">
			<tr>
                            <td colspan="2">Tipo de Predio</td>
				<input type="hidden" name="temp" id="temp" />
				<td>
				<%
				try {
	                tipoPredioHelper.setNombre(CFolio.FOLIO_TIPO_PREDIO);
	                tipoPredioHelper.setId(CFolio.FOLIO_TIPO_PREDIO);
	                tipoPredioHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            	%>
            </td>
			</tr>


			<tr>
				<td colspan="2">C&oacute;digo Catastral</td>
				<td><%
				try {
	                textHelper.setNombre(CFolio.FOLIO_COD_CATASTRAL);
	                textHelper.setCssClase("camposformtext");
	                textHelper.setId(CFolio.FOLIO_COD_CATASTRAL);
                        textHelper.setReadonly(false);
	                //textHelper.setFuncion("onblur=javascript:validarNumerico('"+AWFolio.FOLIO_COD_CATASTRAL+"');");
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
			</tr>
                      
                        <tr>
                            <td colspan="2">NUPRE</td>
				<td><%
				try {
	                textHelper.setNombre(CFolio.FOLIO_NUPRE);
	                textHelper.setCssClase("camposformtext");
	                textHelper.setId(CFolio.FOLIO_NUPRE);
                        textHelper.setReadonly(false);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
                    }
	%></td>
                        </tr>
                        
                        <tr>
                            <td colspan="2" nowrap>Determinaci&oacute;n del Inmueble</td>
				<td><%
				try {
                        
	                tipoDetermInmHelper.setNombre(CFolio.FOLIO_DETERMINACION_INMUEBLE);
	                tipoDetermInmHelper.setId(CFolio.FOLIO_DETERMINACION_INMUEBLE);
	                tipoDetermInmHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
                    }
	%></td>
                        </tr>
                        
                         <tr>
                            <td colspan="2">Avaluo</td>
				<td><%
				try {
	                textHelper.setNombre(CFolio.FOLIO_AVALUO);
	                textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);
	                textHelper.setId(CFolio.FOLIO_AVALUO);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            Fecha de Avaluo &nbsp;
				<%
				try {
	                textHelper.setNombre(CFolio.FOLIO_FECHA_AVALUO);
	                textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);
	                textHelper.setId(CFolio.FOLIO_FECHA_AVALUO);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
                        </tr>
                        
                        <tr>
                            <td colspan="2" nowrap>Destinaci&oacute;n Econ&oacute;mica</td>
				<td><%
				try {
	                textHelper.setNombre(CFolio.FOLIO_DESTINACION_ECONOMICA);
	                textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);
	                textHelper.setId(CFolio.FOLIO_DESTINACION_ECONOMICA);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
                        </tr>
                        
                        <tr>
				<td nowrap>Area Privada</td>
                                <td align="right">Metros<sup>2</sup></td>
                                <td nowrap>
				<%
                        TextHelper areasHelper = new TextHelper(); 
				try {
	                areasHelper.setNombre(CFolio.FOLIO_PRIVMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_PRIVMETROS);
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.FOLIO_PRIVMETROS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_PRIVMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.FOLIO_PRIVMETROS+"','"+CFolio.FOLIO_PRIVCENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %>
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                           Centimetros<sup>2</sup>
                             <%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_PRIVCENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_PRIVCENTIMETROS);
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
			</tr>
                        
                        <tr>
				<td nowrap>Area Construida</td>
                                <td align="right">Metros<sup>2</sup></td>
                                <td nowrap>
				<%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_CONSMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_CONSMETROS);
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.FOLIO_CONSMETROS+"');\" "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_CONSMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.FOLIO_CONSMETROS+"','"+CFolio.FOLIO_CONSCENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            Centimetros<sup>2</sup> &nbsp;<%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_CONSCENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_CONSCENTIMETROS);
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
			</tr>
                        
                        <tr>
                            <td colspan="2">Coeficiente</td>
				<td><%
				try {
	                textHelper.setNombre(CFolio.FOLIO_COEFICIENTE);
	                textHelper.setCssClase("camposformtext");
	                textHelper.setId(CFolio.FOLIO_COEFICIENTE);
                        textHelper.setReadonly(false);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
                        </tr>
                        
                        <tr>
				<td>Area</td>
                                <td align="right">Hectareas</td>
                                <td nowrap>
				<%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_HECTAREAS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_HECTAREAS);
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolio.FOLIO_HECTAREAS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_HECTAREAS+"')\"  "
                                        + "onBlur=\"hectareasFormatter('"+CFolio.FOLIO_HECTAREAS+"','"+CFolio.FOLIO_METROS+"','"+CFolio.FOLIO_CENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Metros<sup>2</sup> &nbsp;
				<%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_METROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_METROS);
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolio.FOLIO_METROS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_METROS+"')\"  "
                                        + "onBlur=\"metrosFormatter('"+CFolio.FOLIO_HECTAREAS+"','"+CFolio.FOLIO_METROS+"','"+CFolio.FOLIO_CENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             Centimetros<sup>2</sup> &nbsp;
                             <%
				try {
	                areasHelper.setNombre(CFolio.FOLIO_CENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolio.FOLIO_CENTIMETROS);
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
			</tr>
                        
			<tr>
                            <td colspan="2">Linderos</td>
				<td><%
				try {
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
                        
                        <tr>
                            <td colspan="2" nowrap>Linderos T&eacute;cnicamente Definidos</td>
				<td><%
				try {
	                TextAreaHelper LinderosDefHelper = new TextAreaHelper();            
	                LinderosDefHelper.setNombre(CFolio.FOLIO_LINDEROS_DEFINIDOS);
	                LinderosDefHelper.setCols("50");
	                LinderosDefHelper.setRows("5");
	                LinderosDefHelper.setCssClase("campositem");
	                LinderosDefHelper.setId(CFolio.FOLIO_LINDEROS_DEFINIDOS);
                        LinderosDefHelper.setReadOnly(true);
	                LinderosDefHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }

            %></td>
			</tr>
		</table>
                </table>
                </table>
		<table width="100%" class="camposform">

			<tr>
				<td width="20%">Complementaci&oacute;n</td>
				<td>


				<table width="100%" border="0">
					<tr>

						<%
						String tipoComp = (String) session.getAttribute(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);
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
				<td width="80%"><%
				try {
	                textAreaComplementacionHelper.setNombre(CFolio.FOLIO_COMPLEMENTACION);
	                textAreaComplementacionHelper.setCols("80");
	                textAreaComplementacionHelper.setRows("5");
	                textAreaComplementacionHelper.setId(CFolio.FOLIO_COMPLEMENTACION);
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
<%
			try {
                dirHelper.render(request, out);
            } catch (HelperException re) {
	           	re.printStackTrace();            
            }catch(Throwable t){
	           	t.printStackTrace();
            }

            %>


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

</form>

<table border="0" cellpadding="0" cellspacing="0" width="100%">

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
					class="titulotbcentral">OPCIONES</td>
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
		
		<br>

		<table width="100%" class="camposform">
			<tr>
				<td width="20"><img
					src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
					width="20" height="15"></td>
				<td width="140"><input name="imageField" type="image"
					src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif"
					onClick="cambiarAccionSubmit( '<%=gov.sir.core.web.acciones.registro.AWCrearFolio.CREAR_FOLIO%>')" width="139"
					height="21" border="0"></td>
				</form>
				<td width="140"><a href="calificacion.inscripcion.view"><img
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

<!--ESTA DESHABILITADA LA OPCION DE NOTAS INFORMATIVAS -->

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
