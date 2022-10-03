<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWCrearFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda"%>
<%@page import="gov.sir.core.web.helpers.correccion.DireccionesHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>

<%
    MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
        
        ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
        List determInm = (List) session.getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
            if(determInm == null){
                determInm = new ArrayList();
            }
        tipoDetermInmHelper.setTipos(determInm);
        tipoDetermInmHelper.setCssClase("camposformtext");
    
	//LISTA DE ESTADO DEL FOLIO
	ListaElementoHelper estadoFolioHelper = new ListaElementoHelper();
	List estadosFolio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA);
	if(estadosFolio == null){
		estadosFolio = new Vector();
	}
	estadoFolioHelper.setOrdenar(false);
	estadoFolioHelper.setTipos(estadosFolio);
	estadoFolioHelper.setCssClase("camposformtext");

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
    TextHelper fechaApertura = new TextHelper();
    TextHelper numRadicacion = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
    //hiddenHelper.setTipo("text");

    TextAreaHelper textAreaHelper = new TextAreaHelper();
    TextAreaHelper comentarioTextAreaHelper = new TextAreaHelper();
    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
    Documento documento = null;
    //Tipo de documento
    String tipoDoc = "PONER ALGO";

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

    dirHelper.setFolioSesion( WebKeys.FOLIO_EDITADO );


	// Datos de antiguo sistema
	String ocultarAS=(String)session.getAttribute("VER_ANTIGUO_SISTEMA");
   	if (ocultarAS==null){
   		ocultarAS="TRUE";
   	}
   	else{
   		session.setAttribute("VER_ANTIGUO_SISTEMA",ocultarAS);
   	}

	boolean esFolioAntiguoSistema = false;
	if(session.getAttribute("FOLIO_ANTIGUO_SISTEMA") != null)
		esFolioAntiguoSistema = true;

	// SALVEDADES FOLIO
	TextHelper fechaRadicacionSalvedad = new TextHelper();
	TextHelper numRadicacionSalvedad = new TextHelper();
	TextAreaHelper descripcionSalvedad = new TextAreaHelper();

	//Número de la siguiente salvedad
	List salvedadesFolio = (List)session.getAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);
	if(salvedadesFolio == null){
		salvedadesFolio = new ArrayList();
	}

	//Consecutivo de Salvedades
	String posSalvedad = (String) session.getAttribute(CFolio.NEXT_ORDEN_SALVEDAD);
	if(posSalvedad == null)
		posSalvedad = "1";
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- para manejo de formularios a traves de js                   --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

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
function disableAntiguoSistema(checkbox) {
	if(!checkbox.checked) {
		document.getElementById('OCULTAR').style.display = 'none';
		document.getElementById('TITULO_OCULTAR').style.display = 'none';
		document.getElementById('<%=CFolio.FOLIO_ID_MATRICULA%>').disabled = false;
	} else {
		document.getElementById('OCULTAR').style.display = '';
		document.getElementById('TITULO_OCULTAR').style.display = '';
		document.getElementById('<%=CFolio.FOLIO_ID_MATRICULA%>').disabled = true;
	}
}

function ocultarAntiguoSistema(text) {
	document.CREAR.VER_ANTIGUO_SISTEMA.value = text;
	cambiarAccionSubmit('VER_ANTIGUO_SISTEMA');
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


	function cambiarValorTipoDocumento(text){
	    try{
	    	document.getElementById('TIPO_ENCABEZADO').options[text].selected=true;
	    }catch(e){
	    	document.getElementById('TIPO_ENCABEZADO').value=' <%=WebKeys.SIN_SELECCIONAR%>';
	    	document.getElementById('ID_TIPO_ENCABEZADO').value='';
	    }
    }

	function oficinas(nombre,valor,dimensiones){
		//document.REGISTRO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
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

	function validarFecha(){
		if (document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
			var index=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.length;
				var texto=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha incorrecta');
					document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value='';
					document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.focus();
				}
			}
		}
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

<%--
function eliminarSalvedadFolio(text) {
	document.CREAR.<%= CSalvedadFolio.ID_SALVEDAD_FOLIO %>.value=text;
        alert( document.CREAR.<%= CSalvedadFolio.ID_SALVEDAD_FOLIO %>.value  );
	document.CREAR.<%= WebKeys.ACCION %>.value ='<%=AWCrearFolio.ELIMINAR_SALVEDAD_FOLIO%>';
	document.CREAR.submit();
}

--%>

</script>




<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- para manejo de formularios a traves de js                   --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">
// Libraries: FindObject BrowserCompatible

// Example: obj = findObj("image1");
function findObj(theObj, theDoc)
{
  var p, i, foundObj;

  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++)
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

  return foundObj;
}

</script>

<script type="text/javascript">
	// local form manipulation

   function LocalForm( formName ) {
     this.formName = formName;
   }
   LocalForm.prototype = new LocalForm();
   LocalForm.prototype.constructor = LocalForm;
   // Form.prototype.superclass = Object;

   LocalForm.prototype.submitForm = function() {
	 formObject = findObj( this.formName );
     formObject.submit();
   }

   LocalForm.prototype.setFormAction = function( formAction ) {
	 formObject = findObj( this.formName );
     formObject.action = formAction;
   }

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")) );
   }

</script>



<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

   var ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION  = "<%= AWCrearFolio.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION %>";

</script>

<%-- --%>
<%-- --%>
<%

final String PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO = AWCrearFolio.PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO ;

%>

<%-- --%>
<%-- --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEvent2( formName, actionValue, processorId ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setFormAction( processorId );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEventConfirm( formName, actionValue, msg ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     if( confirm( msg) ) {

       htmlForm.setValue( actionField, actionValue );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

   }

</script>

<script type="text/javascript">

function eliminarSalvedadFolio(text) {

  var formName = 'CREAR';
  var fieldName = '<%= PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO %>';
  var actionValue = '<%= AWCrearFolio.ELIMINAR_SALVEDAD_FOLIO %>';

   var htmlForm;
   htmlForm = new LocalForm( formName );
   htmlForm.setValue( fieldName, text );
   htmlForm.setValue( actionField, actionValue );
   htmlForm.submitForm();
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
					class="titulotbgral">ADMINISTRACIÓN - GRABAR FOLIO</td>
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
			<form action="crearfolioadministracion.do" name="CREAR" id="CREAR" method="post"><input
				type="hidden" name="ACCION" id="ACCION" /> <input type="hidden"
				name="NUM_ANOTACION_TEMPORAL" id="NUM_ANOTACION_TEMPORAL" value="" />
				<input name="Depto" type="hidden" id="id_depto" value="" />
				<input name="Depto" type="hidden" id="nom_Depto" value="" />
				<input name="Mpio" type="hidden" id="id_munic" value="" />
				<input name="Mpio" type="hidden" id="nom_munic" value="" />
				<input name="Ver" type="hidden" id="id_vereda" value="" />
				<input name="Ver" type="hidden" id="nom_vereda" value="" />

				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1" />
				<input type="hidden" name="POSICION" id="POSICION" />

				<input type="hidden" name="VER_ANTIGUO_SISTEMA"	id="VER_ANTIGUO_SISTEMA">
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

<!-- 					XXXXX  -->


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
				<td colspan= "2" valign="top" bgcolor="#79849B" class="tdtablacentral">
	<!-- Mostrar el campo número de matrícula para agregar la primera vez.	No se muestra si el sistema la genera -->

				<%String idMatricula = (String) session.getAttribute(CFolio.FOLIO_ID_MATRICULA);
                idMatricula = idMatricula == null ? "" : idMatricula;

                %>

				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif"
							width="20" height="15"></td>
						<td width="20" class="campositem">N&ordm;</td>
						<td><%=circulo!=null?circulo.getIdCirculo():""%>-

							<%
							try {
			                    textHelper.setNombre(CFolio.FOLIO_ID_MATRICULA);
			                    textHelper.setCssClase("camposformtext");
			                    textHelper.setId(CFolio.FOLIO_ID_MATRICULA);
			                    textHelper.render(request, out);
			                }
							catch (HelperException re) {
			                    out.println("ERROR " + re.getMessage());
			                }
							%>

                &nbsp;


                  <%-- SOF:ITEM --%>
				  <%-- type="textfield", properties="disabled" --%>
					<%
							final String PARAM_LOCAL__LOADSTATE = "PARAM_LOCAL__LOADSTATE";
							String loadState;

							if( null != session.getAttribute( AWCrearFolio.PARAM__LOCAL_FOLIO_ID_MATRICULA ) ) {
								loadState = "CARGADO";
							}
							else {
								loadState = "NUEVO";
							} // if

							session.setAttribute( PARAM_LOCAL__LOADSTATE, loadState );

					%>
					<%

							// ::

							try {

								TextHelper local_TextHelper;

								local_TextHelper = new TextHelper();

			                    local_TextHelper.setId(     PARAM_LOCAL__LOADSTATE );
			                    local_TextHelper.setNombre( PARAM_LOCAL__LOADSTATE );
			                    local_TextHelper.setCssClase( "campositem" );
								local_TextHelper.setEditable( false );
								local_TextHelper.setReadonly( true  );

			                    local_TextHelper.render( request, out );

			                }
							catch( HelperException re ) {

			                    out.println( "ERROR " + re.getMessage() );

			                } // try

					%>
                  <%-- EOF:ITEM --%>

                  <%-- SOF:BUTTON --%>

                    <a href="javascript:js_OnEvent( 'CREAR', ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_cargar.gif" width="125" height="21" border="0" />
                    </a>
                  <%-- EOF:BUTTON --%>




                </td>

					<!--
					  <td align="right">¿Folio de Antiguo Sistema?</td>
					  <td>
						<%
							if(esFolioAntiguoSistema) {
						%>
					  	<input type="checkbox" name="FOLIO_ANTIGUO_SISTEMA" id="FOLIO_ANTIGUO_SISTEMA" checked onClick="disableAntiguoSistema(this)">
						<%
							} else {
						%>
						<input type="checkbox" name="FOLIO_ANTIGUO_SISTEMA" id="FOLIO_ANTIGUO_SISTEMA" onClick="disableAntiguoSistema(this)">
						<%
							}
						%>
					  </td>
					  -->
					</tr>
				</table>

				<table width="100%" class="camposform">

					<tr>
					<td>Fecha Apertura</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><% try {
										fechaApertura.setNombre(CSolicitudRegistro.CALENDAR_APERTURA);
										fechaApertura.setCssClase("camposformtext");
										fechaApertura.setId(CSolicitudRegistro.CALENDAR_APERTURA);
										fechaApertura.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR_APERTURA+"');\" "
                                                                                + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR_APERTURA+"')\"   onBlur=\"javascript:validarFecha()\"" );
										fechaApertura.render(request,out);
										}
											catch(HelperException  re){
											out.println("ERROR " + re.getMessage());
										}
									%>
								</td>
								<td>

									<a
									  href="javascript:NewCal('<%=CSolicitudRegistro.CALENDAR_APERTURA%>','ddmmmyyyy',true,24)"
									>
									  <img
									    src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									    alt="Fecha"
									    width="16"
									    height="21"
									    border="0"
									    onClick="javascript:Valores('<%=request.getContextPath()%>')" />
									</a>

								<%--

									<a	href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
										<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
										alt="Fecha" width="16" height="21" border="0"
										onClick="javascript:Valores('<%=request.getContextPath()%>')">
									</a>
								--%>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>

					<td>N&uacute;mero de Radicaci&oacute;n</td>
					<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
					<td>
					<%
					try
					{
						numRadicacion.setNombre(CSolicitudRegistro.NUMERO_RADICACION);
						numRadicacion.setCssClase("camposformtext");
						numRadicacion.setId(CSolicitudRegistro.NUMERO_RADICACION);
						numRadicacion.render(request,out);
					} catch(HelperException re)
					{
						out.println("ERROR " + re.getMessage());
					}
					%>
					</td>
					</tr>
					</table>
					</td>
					<td>Estado folio</td>
                    <td>
					<% try {
							estadoFolioHelper.setNombre(CFolio.FOLIO_ID_ESTADO);
							estadoFolioHelper.setId(CFolio.FOLIO_ID_ESTADO);
							estadoFolioHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td>
					</tr>
					<tr>

					</tr>


				</table>
				<table width="100%" class="camposform">

	<tr>
          <td>&nbsp;</td>
		<td width="280px"><div align="right">Comentario (cambio de estado)</div></td>
		<td width="220px"><%
		try {
			comentarioTextAreaHelper.setNombre(CFolio.FOLIO_COMENTARIO);
			comentarioTextAreaHelper.setCols("28");
			comentarioTextAreaHelper.setRows("2");
			comentarioTextAreaHelper.setCssClase("camposformtext");
			comentarioTextAreaHelper.setId(CFolio.FOLIO_COMENTARIO);
			comentarioTextAreaHelper.render(request, out);
		} catch (HelperException re) {
			out.println("ERROR " + re.getMessage());
		}
		%></td>
	</tr>


				</table>











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







<!-- DATOS DEL DOCUMENTOS-->
<table width="100%" class="camposform">
	<tr>
		<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td>Datos Documento</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<table width="100%" class="camposform">
				<tr>
					<td>Tipo</td>
					<td><% try {
 		                        textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value);\"");
                  			    textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
								textHelper.render(request,out);
								textHelper.setFuncion("");
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
					</td>
					<td><% try {
								String stringTipoEncabezado = (String) session.getAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
								ListaElementoHelper tipoEncabezado= new ListaElementoHelper();

	 		                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                  			    tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
								tipoEncabezado.setOrdenar(false);
                   				tipoEncabezado.setTipos(tiposDoc);
                  			    tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
								tipoEncabezado.setCssClase("camposformtext");
                  			    tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);
	              			    tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
								tipoEncabezado.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
					</td>

					<script type='text/javascript'>
                       	document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                       	if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                       		document.getElementById('ID_TIPO_ENCABEZADO').value='';
                       	}
                    </script>

					<td>N&uacute;mero</td>
					<td><% try {
 		                       textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
							textHelper.setCssClase("camposformtext");
                  		    textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
					</td>
					<td>Fecha</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><% try {
				 	                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
											textHelper.setCssClase("camposformtext");
				               			    textHelper.setId(CSolicitudRegistro.CALENDAR);
				               			    textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\"   onBlur=\"javascript:validarFecha()\"" );
											textHelper.render(request,out);
										}
											catch(HelperException  re){
											out.println("ERROR " + re.getMessage());
										}
									%>
								</td>
								<td>

									<a
									  href="javascript:NewCal('<%=CSolicitudRegistro.CALENDAR%>','ddmmmyyyy',true,24)"
									>
									  <img
									    src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									    alt="Fecha"
									    width="16"
									    height="21"
									    border="0"
									    onClick="javascript:Valores('<%=request.getContextPath()%>')" />
									</a>

								<%--


									<a	href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
										<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
										alt="Fecha" width="16" height="21" border="0"
										onClick="javascript:Valores('<%=request.getContextPath()%>')">
									</a>
								--%>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>

	</tr>

	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">
		</td>
		<td>Oficina de Procedencia</td>
	</tr>

	<!-- EL HELPER DE OFICINA EMPIEZA ACA -->
	<jsp:include page="../../registro/HELPER_OFICINAS.jsp" flush="true" />
	<!-- EL HELPER DE OFICINA TERMINA ACA -->

</table>
<!-- FIN DATOS DEL DOCUMENTOS-->






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

<!-- INICIO SALVEDADES -->

<table width="100%" class="camposform">
	<tr>
		<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td>Salvedades</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<table width="100%" class="camposform">
			<!--
			<tr>
			<td>N&uacute;mero Salvedad</td>
			<td class="campositem" align="right" width="15%"> <%=posSalvedad%> </td>
			</tr>
			-->

				<tr>
					<td>Fecha Radicaci&oacute;n</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><% try {
										fechaRadicacionSalvedad.setNombre(CSalvedadFolio.CALENDAR_RADICACION);
										fechaRadicacionSalvedad.setCssClase("camposformtext");
										fechaRadicacionSalvedad.setId(CSalvedadFolio.CALENDAR_RADICACION);
										fechaRadicacionSalvedad.setFuncion("  onkeypress=\"return valideDate(event,'"+CSalvedadFolio.CALENDAR_RADICACION+"');\" "
                                                                                    + " onChange=\"fixDate('"+CSalvedadFolio.CALENDAR_RADICACION+"')\"   onBlur=\"javascript:validarFecha()\"" );
										fechaRadicacionSalvedad.render(request,out);
										}
											catch(HelperException  re){
											out.println("ERROR " + re.getMessage());
										}
									%>
								</td>

								<td>

									<a
									  href="javascript:NewCal('<%=CSalvedadFolio.CALENDAR_RADICACION%>','ddmmmyyyy',true,24)"
									>
									  <img
									    src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									    alt="Fecha"
									    width="16"
									    height="21"
									    border="0"
									    onClick="javascript:Valores('<%=request.getContextPath()%>')" />
									</a>

								<%--
									<a	href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
										<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
										alt="Fecha" width="16" height="21" border="0"
										onClick="javascript:Valores('<%=request.getContextPath()%>')">
									</a>
								--%>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
					</tr>
					<tr>
					<td>N&uacute;mero de Radicaci&oacute;n</td>
					<td>
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
					<td>
					<%
					try
					{
						numRadicacionSalvedad.setNombre(CSalvedadFolio.NUMERO_RADICACION_SALVEDAD_FOLIO);
						numRadicacionSalvedad.setCssClase("camposformtext");
						numRadicacionSalvedad.setId(CSalvedadFolio.NUMERO_RADICACION_SALVEDAD_FOLIO);
						numRadicacionSalvedad.render(request,out);
					} catch(HelperException re)
					{
						out.println("ERROR " + re.getMessage());
					}
					%>
					</td>

					</tr>
					</table>
					</td>

				</tr>
				<tr>

				<td>Descripci&oacute;n Salvedad</td>
				<td colspan="4"><%
				try {
					descripcionSalvedad.setNombre(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
					descripcionSalvedad.setCols("80");
					descripcionSalvedad.setRows("5");
					descripcionSalvedad.setCssClase("camposformtext");
					descripcionSalvedad.setId(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
					descripcionSalvedad.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }

            %></td>
            <td>
            <table  border="0" class="camposform"><tr align="left"><td>Agregar</td></tr><tr align="right">
            <td>
            	<a href="javascript:cambiarAccionSubmit('AGREGAR_SALVEDAD_FOLIO')">
            		<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" name="Folio" width="35" height="25" border="0" />
            	</a>
            </td></tr></table>
            </td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	</tr>

<%--
Bug 05165: no esta eliminando salvedades
--%>


<input  type="hidden" name="<%= PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO %>" id="<%= PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO %>" value="-1">



	                 <%
                int idSalvedadFolio = 0;
                for(Iterator iter = salvedadesFolio.iterator(); iter.hasNext();){
                	SalvedadFolio salvedadFolio = (SalvedadFolio)iter.next();
				    String fechaSalvedadFolio = DateFormatUtil.format(salvedadFolio.getFechaCreacion());

                %>
<table border="0" align="center" width = "100%">

				<tr>
				 <td>&nbsp;</td>
                  <!-- <td class="camposform">Número de Salvedad</td> -->
                  <td class="camposform">Fecha Salvedad</td>
                  <td class="camposform">Radicación</td>
                  <td class="camposform">Descripción</td>
                  <td class="camposform">Eliminar</td>
				</tr>
                <tr>

                  <td>&nbsp;</td>
                  <!-- <td class="camposformtext_noCase"><%= salvedadFolio.getIdSalvedad() %></td> -->
                  <td class="camposformtext_noCase"><%= fechaSalvedadFolio %></td>
                  <td class="camposformtext_noCase"><%= salvedadFolio.getNumRadicacion() %></td>
                   <td class="camposformtext_noCase"><%= salvedadFolio.getDescripcion() %></td>


                  <td>
	                	<input  type="hidden" name="<%= CSalvedadFolio.ID_SALVEDAD_FOLIO%>" id="<%= CSalvedadFolio.ID_SALVEDAD_FOLIO %>" value="<%= salvedadFolio.getIdSalvedad() %>">
                                <%-- SOF:BUTTON --%>

                                  <a href="javascript:eliminarSalvedadFolio('<%= idSalvedadFolio %>');">
                                   <img alt="[execute]" src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" style="cursor:hand" border="0px" />
                                  </a>
                                <%-- EOF:BUTTON --%>
                  </td>


                </tr>
                <!-- </table> -->

                <%
                idSalvedadFolio ++;
                 }
                 %>
			</table>
		</td>

	</tr>


</table>



<!-- FIN SALVEDADES -->
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


<hr class="linehorizontal" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="TITULO_OCULTAR">
	<tr>
		<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
		<td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Antiguo Sistema</td>
		<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif"
										width="16" height="21"></td>
		<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
										width="16" height="21"></td>
		<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
	</tr>
</table>
<br>
<table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTAR">
	<%
		if(ocultarAS.equals("FALSE")) {
	%>
	<tr>
		<td>
			<hr class="linehorizontal">
		</td>
	</tr>
	<tr>
		<td></td>
		<td width="16"><a href="javascript:ocultarAntiguoSistema('TRUE');campoactual('OCULTAR');"><img id="MINIMIZAR"
												src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
												width="16" height="16" border="0"></a>
		</td>
		<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
	</tr>
	<%
		} else {
	%>
	<tr>
		<td align="right" class="campostip04">Haga click para maximizar los datos de Antiguo Sistema</td>
		<td width="16"><a href="javascript:ocultarAntiguoSistema('FALSE');campoactual('OCULTAR');"><img
							img id="MAXIMIZAR"
							src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
							width="16" height="16" border="0"></a>
		</td>
		<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
	</tr>
	<%
		}
	%>
</table>
<!-- Mostrar helper de antiguo sistema -->
<%
	if(ocultarAS.equals("FALSE")) {
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
				<td width="140"><a href="javascript:cambiarAccionSubmit( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolio.CREAR_FOLIO%>')">
				<img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif"  width="139" height="21" border="0"></a></td>
				</form>
				<td width="140"><a href="javascript:cambiarAccionSubmit( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolio.CANCELAR_CREACION%>')">
				<img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a></td>
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
<script>
<%
	if(esFolioAntiguoSistema) {
%>
document.getElementById('OCULTAR').style.display='';
document.getElementById('TITULO_OCULTAR').style.display = '';
document.getElementById('<%=CFolio.FOLIO_ID_MATRICULA%>').disabled = true;
<%
	} else {
%>
document.getElementById('OCULTAR').style.display='none';
document.getElementById('TITULO_OCULTAR').style.display = 'none';
document.getElementById('<%=CFolio.FOLIO_ID_MATRICULA%>').disabled = false;
<%
	}
%>
</script>
