<%@page import="gov.sir.core.negocio.modelo.Anotacion,
java.util.*,
gov.sir.core.util.DateFormatUtil,
java.util.Iterator,
java.util.List,
java.util.Vector,
gov.sir.core.web.helpers.comun.MostrarFechaHelper,
gov.sir.core.web.helpers.comun.TextAreaHelper,
gov.sir.core.web.helpers.comun.TextHelper,
gov.sir.core.web.helpers.comun.AntiguoSistemaHelper,
org.auriga.core.web.*,
gov.sir.core.negocio.modelo.*,
gov.sir.core.negocio.modelo.constantes.*,
gov.sir.core.negocio.modelo.constantes.CAnotacion,
gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano,
gov.sir.core.web.WebKeys,
gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper,
gov.sir.core.negocio.modelo.constantes.CFolio,
gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema,
gov.sir.core.web.acciones.administracion.AWCrearFolio,
gov.sir.core.web.acciones.administracion.AWCrearFolioOficio,
gov.sir.core.web.acciones.correccion.*,
gov.sir.core.web.acciones.registro.AWCalificacion,
gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro,
gov.sir.core.web.helpers.comun.ListaElementoHelper,
gov.sir.core.web.acciones.comun.*"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaOficioHelper"%>

<%
	
	MostrarAntiguoSistemaOficioHelper MASHelper= new MostrarAntiguoSistemaOficioHelper();
	
    //BASICOS

    String nomOficina = "";
    boolean isInternacional = false;
    boolean isComentarioAnotacion = false;
	boolean isAnotacionAntiguoSistema = false;

	if(session.getAttribute(WebKeys.DATOS_ANTIGUO_SISTEMA) != null) {
		isAnotacionAntiguoSistema = true;
	}

    List anotaciones = (List)session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
	if(anotaciones == null){
		anotaciones = new Vector();
	}

    List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
	session.setAttribute("listanat",grupoNaturalezas);
    session.removeAttribute("cancelar");

	//LISTA DE ESTADO DE LA ANOTACION
	ListaElementoHelper estadoAnotacionHelper = new ListaElementoHelper();
	List estadosAnotacion = (List)session.getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA);
	if(estadosAnotacion == null){
		estadosAnotacion = new Vector();
	}
	estadoAnotacionHelper.setOrdenar(false);
	estadoAnotacionHelper.setTipos(estadosAnotacion);
	estadoAnotacionHelper.setCssClase("camposformtext");

	//Número de Radición
	TextHelper numRadicaAnota = new TextHelper();


    //PREPARACION HELPERS
    TextHelper tiposDocHelper = new TextHelper();
	TextHelper textHelper = new TextHelper();
	TextHelper hiddenHelper = new TextHelper();
	hiddenHelper.setTipo("hidden");
	TextAreaHelper textAreaHelper = new TextAreaHelper();
	MostrarFechaHelper fecha = new MostrarFechaHelper();

	//HELPER VARIOS CIUDADANOS
	VariosCiudadanosAnotacionHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
	Integer auxNumFilas = (Integer)session.getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
	List lciudadanos= (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
	if(lciudadanos==null){
		lciudadanos= new Vector();
	}
	int numFilas;
	if(auxNumFilas == null)
		numFilas=AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
	else
		numFilas=auxNumFilas.intValue();
	List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
        List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL); 
        List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
        List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
        List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
	variosCiudadanosHelper.setPropertiesHandly(numFilas,tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica,gov.sir.core.web.acciones.administracion.AWCrearFolio.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
																AWCrearFolio.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
																AWCrearFolio.AGREGAR_VARIOS_CIUDADANOS_EDICION,
																AWCrearFolio.ELIMINAR_CIUDADANO_EDICION,
																lciudadanos, AWCrearFolio.VALIDAR_CIUDADANO_EDICION, "ANOTACION");

	//NUMERO DE LA SIGUIENTE ANOTACION
	String pos = (String) session.getAttribute(AWCrearFolio.NUM_ANOTACION_TEMPORAL);
	if(pos == null)
		pos = "1";

	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	if(exception!=null){
		 variosCiudadanosHelper.setCentrar(false);
	}
	session.removeAttribute(WebKeys.HAY_EXCEPCION);

	// Datos de antiguo sistema
	String ocultarAS=(String)session.getAttribute("VER_ANTIGUO_SISTEMA_ANOTACION");
   	if (ocultarAS==null){
   		ocultarAS="TRUE";
   	}
   	else{
   		session.setAttribute("VER_ANTIGUO_SISTEMA_ANOTACION",ocultarAS);
   	}

	//SALVEDADES ANOTACION
	TextHelper fechaRadicacionSalvedadAnotacion = new TextHelper();
	TextHelper numRadicacionSalvedadAnotacion = new TextHelper();
	TextAreaHelper descripcionSalvedadAnotacion = new TextAreaHelper();

	//Número de la siguiente salvedad
	List salvedadesAnotacion = (List)session.getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
	if(salvedadesAnotacion == null){
		salvedadesAnotacion = new ArrayList();
	}

	//Consecutivo de Salvedades
	String posSalvedadAnotacion = (String) session.getAttribute(CAnotacion.NEXT_ORDEN_SALVEDAD_ANOTACION);
	if(posSalvedadAnotacion == null)
		posSalvedadAnotacion = "1";

    %>
    <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
    <script type="text/javascript">

function ocultarAntiguoSistema(text) {
	document.ANOTACION.VER_ANTIGUO_SISTEMA_ANOTACION.value = text;
	cambiarAccion('VER_ANTIGUO_SISTEMA_ANOTACION');
}

    function ocultarSalvedad(text) {
	   document.ANOTACION.VER_SALVEDAD.value = text;
	   cambiarAccion('VER_SALVEDAD');
  	}

function cambiarAccion(text) {
	document.ANOTACION.ACCION.value = text;
	document.ANOTACION.submit();
}
function quitar(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function cargarAnotacion(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}

function editarAnotacion(text){
	document.getElementById("NUM_A_TEMPORAL").value = text;
	cambiarAccion('EDITAR_ANOTACION');
}

function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}

function cambiarAccionAndSendTipoTarifa(text) {
	if(document.ANOTACION.<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID %>.value!=""){
		document.ANOTACION.ACCION.value = text;
		document.ANOTACION.submit();
	}
}

	function cambiarValorTipoDocumento(text){
	    try{
	    	document.getElementById('TIPO_ENCABEZADO').options[text].selected=true;
	    }catch(e){
	    	document.getElementById('TIPO_ENCABEZADO').value=' <%=WebKeys.SIN_SELECCIONAR%>';
	    	document.getElementById('ID_TIPO_ENCABEZADO').value='';
	    }
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

function validarFechaRadicacion(){
		if (document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.value.length>0){
			var index=document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.value.length;
				var texto=document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha incorrecta');
					document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.value='';
					document.CREAR.<%=CSolicitudRegistro.CALENDAR2%>.focus();
				}
			}
		}
	}


function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
        /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se asigna valores a campos en el formulario
                */
        document.getElementById('natjuridica_ver').value = valor+"_VER";
	var fechaRad = document.getElementById('<%= CSolicitudRegistro.CALENDAR %>').value;
	var url = nombre + '&<%= AWModificarFolio.ANOTACION_FECHA_RADICACION %>='+ fechaRad;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.ANOTACION.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function Secuencia(text){
	document.ANOTACION.<%=CAnotacionCiudadano.SECUENCIA %>.value = text;
	cambiarAccion('<%=AWCalificacion.REFRESCAR_ANOTACION%>');
}

function cambioFocus(text){
	document.getElementById('<%=CFolio.ANOTACION_NUM_ID_PERSONA%>0').focus();
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

function eliminarSalvedadAnotacion(text) {
	document.ANOTACION.<%= CSalvedadAnotacion.ID_SALVEDAD_ANOTACION %>.value=text;
	document.ANOTACION.<%= WebKeys.ACCION %>.value ='<%=AWCrearFolio.ELIMINAR_SALVEDAD_ANOTACION_EDICION%>';
	document.ANOTACION.submit();
}

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

    // added -------------
   LocalForm.prototype.getFormObject = function() {
     var local_FormObject;
     local_FormObject = findObj( this.formName );
     return local_FormObject;
   }

   LocalForm.prototype.getValue = function( elementName ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     var elementValue;

     eval( "elementValue" + "=" + "formObject."+ elementName + ".value" );
     // alert( "valor de " + elementName + " es " + elementValue )
     return elementValue;
   }

   LocalForm.prototype.formElementFocus = function( elementName ) {
     var local_FormObject;
     local_FormObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".focus()" );

   }

   // -------------------

</script>



<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

   var ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION  = "<%= AWCrearFolio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION %>";
   var ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT  = "<%= AWCrearFolio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT %>";

</script>

<script type="text/javascript">

  // Para validaciones de widgets tipo fecha
  var ITEM__PG_CREARANOTACION_CSOLICITUDREGISTRO_CALENDAR1 = "<%= CSolicitudRegistro.CALENDAR  %>";
  var ITEM__PG_CREARANOTACION_CSOLICITUDREGISTRO_CALENDAR2 = "<%= CSolicitudRegistro.CALENDAR2 %>";
  var ITEM__PG_CREARANOTACION_SALVEDADANOTACION_CALENDARRADICACION = "<%= CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION %>";


</script>

<script type="text/javascript">

  var ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION = "<%= AWCrearFolio.ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION  %>";



</script>

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


  function js_DateFormatValidator( formName, fieldName ){


    var htmForm;
    htmForm = new LocalForm( formName );

    var formObject;
    formObject = htmForm.getFormObject();

    // alert("formObject "+formObject);


    if( ( null == formObject )
      || ( undefined == formObject ) ) {

        alert( "ERROR: No se encuentra la referencia al formulario (jsDateFormatValidator)" );
        return;
    }

    var fieldValue;
    fieldValue = htmForm.getValue( fieldName );

    // alert("fieldValue "+fieldValue);



    if( fieldValue.length > 0 ) {

      var index = fieldValue.lastIndexOf('/') + 1;

      if (index!=null){
        var fin = fieldValue.length;
        var texto=fieldValue.substring( index,fin );

        if( texto.length != 4 ){
          alert( 'Fecha incorrecta' );
          htmForm.setValue( fieldName, '' );
          htmForm.formElementFocus( fieldName );
        } // if

      } // if

    } // if

    /*

<%--

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

--%>
    */
  }


</script>







<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<form action="crearfolioadministracionoficio.do" method="post" name="ANOTACION" id="ANOTACION">
 	<input type="hidden" name="ACCION" value="">
 	<input type="hidden" name="POSICION" value="">
 	<input type="hidden" name="VER_SALVEDAD" value="">
 	<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1">
 	<input type="hidden" name="CAMBIO" value="">
 	<input type="hidden" name="NUM_A_TEMPORAL" id="NUM_A_TEMPORAL" value="">

	<input name="Depto" type="hidden" id="id_depto" value="" />
	<input name="Depto" type="hidden" id="nom_Depto" value="" />
	<input name="Mpio" type="hidden" id="id_munic" value="" />
	<input name="Mpio" type="hidden" id="nom_munic" value="" />
	<input name="Ver" type="hidden" id="id_vereda" value="" />
	<input name="Ver" type="hidden" id="nom_vereda" value="" />
	<input name="VER_ANTIGUO_SISTEMA_ANOTACION" id="VER_ANTIGUO_SISTEMA_ANOTACION" type="hidden" />
	<input type="hidden" name="<%= CSalvedadAnotacion.ID_SALVEDAD_ANOTACION%>" id="<%= CSalvedadAnotacion.ID_SALVEDAD_ANOTACION %>"%>

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
          			<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ADMINISTRACIÓN - GRABACIÓN FOLIO - EDICIÓN ANOTACIONES</td>
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





        <%
		//System.out.println("***************************************");
		//System.out.println("*************se va a llegar a este lado**************************");
		

        final String PARAM__LOCAL_FOLIO_ID_MATRICULA     = "PARAM:LOCAL_FOLIO_IDMATRICULA";
        final String PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL = "PARAM:LOCAL_FOLIO_IDZONAREGISTRAL";

        String local_Folio_IdMatricula;
        String local_Folio_IdZonaRegistral;

        if( ( null != ( local_Folio_IdMatricula     = (String)session.getAttribute( PARAM__LOCAL_FOLIO_ID_MATRICULA     ) ) )
          &&( null != ( local_Folio_IdZonaRegistral = (String)session.getAttribute( PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL ) ) ) ) {


        %>
				<br />



                    <div width="100%">

					<table width="100%" class="camposform">
					<tr>
						<td colspan="3">Datos del Folio <sub>TMP</sub></td>
					</tr>
					<tr>
						<td width="20">
							<img
								src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif"
								width="20" height="15"
							/>
						</td>
						<td width="20" class="campositem">N&ordm;</td>
						<td class="campositem">
						<%
						try {
							TextHelper local_TextHelper;
							local_TextHelper = new TextHelper();

							local_TextHelper.setId(     PARAM__LOCAL_FOLIO_ID_MATRICULA );
							local_TextHelper.setNombre( PARAM__LOCAL_FOLIO_ID_MATRICULA );

							local_TextHelper.setCssClase( "campositem" );
							local_TextHelper.setFuncion( "style='border:0px;'" );
							local_TextHelper.setReadonly(true);

							local_TextHelper.render( request, out );
	                	}
						catch (HelperException re) {
	                    	out.println("ERROR " + re.getMessage());
	                	}
	                	%>&nbsp;
	                	</td>
					</tr>
					</table>

				  	</div>

                    <%

                    } // end-if
		//System.out.println("***************************************");
		//System.out.println("*************se va a llegar a este lado AAAAAAAAAAAAAAAAAAAAAAA**************************");
                    %>




















			<table border="0" cellpadding="0" cellspacing="0" width="100%">
      		<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
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
              					<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ANOTACIONES</td>
              					<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              					<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              						<table width="100%" border="0" cellspacing="0" cellpadding="0">
                  						<tr>
                    						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
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
        				<table width="100%" >
          					<tr>
            					<td>
									<table width="100%" class="camposform">
										<tr>
											<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
											<td>Datos B&aacute;sicos </td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>
												<table width="100%" class="camposform">
													<tr>
														<td width="15%">N&uacute;mero anotaci&oacute;n </td>
														<td class="campositem" align="right" width="15%"> <%=pos%> </td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
										<td>&nbsp;</td>
										<td>
										<table width="100%" class="camposform">
													<tr>
														<td width="15%" >Estado Anotación</td>
														<td  align="right" width="21%"> <%
														try {
															estadoAnotacionHelper.setNombre(CAnotacion.ESTADO_ANOTACION);
															estadoAnotacionHelper.setId(CAnotacion.ESTADO_ANOTACION);
															estadoAnotacionHelper.render(request,out);
															}
														catch(HelperException re){
														out.println("ERROR " + re.getMessage());
											}
										%> </td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>

															<tr>
										<td>&nbsp;</td>
										<td>
										<table width="100%" class="camposform">
													<tr>
														<td width="15%" >N&uacute;mero de Radicaci&oacute;n</td>
														<td  align="right" width="21%">
										<%
														try
														{
															numRadicaAnota.setNombre(CAnotacion.NUMERO_RADICACION_ANOTACION);
															numRadicaAnota.setCssClase("camposformtext");
															numRadicaAnota.setId(CAnotacion.NUMERO_RADICACION_ANOTACION);
															numRadicaAnota.render(request,out);
														} catch(HelperException re)
														{
															out.println("ERROR " + re.getMessage());
														}
										%> </td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>

									</table>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
											<td class="bgnsub">Documento</td>
											<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
											<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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
														<td>
														<%
														//System.out.println("***************************************");
									//System.out.println("*************ID_TIPO_ENCABEZADOA**************************");
															try {
 		                        								textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    								textHelper.setCssClase("camposformtext");
                  			    								textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value);\"");
                  			    								textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
																textHelper.render(request,out);
																textHelper.setFuncion("");
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<td>
														<%
															try {
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
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<script>
                       										document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                       										if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                       											document.getElementById('ID_TIPO_ENCABEZADO').value='';
                       										}
                       									</script>
														<td>N&uacute;mero</td>
														<td>
														<%
														//System.out.println("***************************************");
									//System.out.println("*************ID_ENCABEZADO**************************");
															try {
 		                       									textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
																textHelper.setCssClase("camposformtext");
                  		    									textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
																textHelper.render(request,out);
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
																		//System.out.println("***************************************");
									//System.out.println("*************CALENDAR**************************");
																			try {
				 	                        									textHelper.setNombre(CSolicitudRegistro.CALENDAR);
																				textHelper.setCssClase("camposformtext");
				               			    									textHelper.setId(CSolicitudRegistro.CALENDAR);
				               			    									//textHelper.setFuncion("onBlur=\"javascript:validarFecha()\"" );
                                                                                                                                        textHelper.setFuncion(" onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\"  "
                                                                                                                                                + "onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\" "
                                                                                                                                                + "onBlur=\"javascript:js_DateFormatValidator('ANOTACION',ITEM__PG_CREARANOTACION_CSOLICITUDREGISTRO_CALENDAR1 )\"" );
	//System.out.println("*************CALENDAR OKOKOKOKO**************************");
																				textHelper.render(request,out);
																				//System.out.println("*************CPAILANDER *************************");
																			} catch(HelperException re) {
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
									<!-- INICIO DATOS ANTIGUO SISTEMA -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
											<td class="bgnsub">Antiguo Sistema</td>
											<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
											<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
										</tr>
									</table>

									<%
									//System.out.println("***************************************");
									//System.out.println("*************MASHELPER**************************");
       								try{
						      			MASHelper.render(request,out);
						      		} catch(HelperException re){
						      			re.printStackTrace();
										out.println("ERROR " + re.getMessage());
									}
							        %>
        							<!-- FIN DATOS ANTIGUO SISTEMA -->
           							
									<!-- INICIO ESPECIFICACION -->
              						<table width="100%" border="0" cellpadding="0" cellspacing="0">
                						<tr>
                  							<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  							<td class="bgnsub">Especificaci&oacute;n</td>
                  							<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  							<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                						</tr>
              						</table>
              						<table width="100%" class="camposform">
										<tr>
											<td>Fecha de Radicaci&oacute;n</td>
                        					<td>
                        						<table border="0" cellpadding="0" cellspacing="0">
                            						<tr>
                              							<td>

														<%
														//System.out.println("***************************************");
									//System.out.println("*************CALENDAR2**************************");
															try {
				 	                        					textHelper.setNombre(CSolicitudRegistro.CALENDAR2);
																textHelper.setCssClase("camposformtext");
				               			    					textHelper.setId(CSolicitudRegistro.CALENDAR2);
				               			    					//textHelper.setFuncion("onBlur=\"javascript:validarFechaRadicacion()\"" );
                                                                                                        textHelper.setFuncion("onBlur=\"javascript:js_DateFormatValidator('ANOTACION',ITEM__PG_CREARANOTACION_CSOLICITUDREGISTRO_CALENDAR2 )\"" );

																textHelper.render(request,out);
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<td>



									<a
									  href="javascript:NewCal('<%=CSolicitudRegistro.CALENDAR2%>','ddmmmyyyy',true,24)"
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


															<a	href="javascript:NewCal('calendar2','ddmmmyyyy',true,24)">
																<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
																	alt="Fecha de Radicaci&oacute;n" width="16" height="21" border="0"
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
  									<table width="100%" class="camposform">
				  						<tr>
											<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
											<td width="150">Naturaleza Jur&iacute;dica </td>
											<td width="40" align="justify">Codigo</td>
											<td width="40">
											<%
												try {
													textHelper.setTipo("text");
													textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
													textHelper.setCssClase("camposformtext");
													textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
													textHelper.setSize("5");
													textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('"+AWCrearFolio.CARGAR_DESCRIPCION_NATURALEZA+"')\"");
													textHelper.setEditable(true);
													textHelper.render(request,out);
													textHelper.setFuncion("");
												} catch(HelperException re) {
													out.println("ERROR " + re.getMessage());
												}
											%>
											</td>
											<td width="100" align="right">Descripci&oacute;n</td>
											<td align="justify">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
													<input name="natjuridica_id" type="hidden" id="natjuridica_id" value="">
													<input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value="">
                                                                                                               <%--/**
                                                                                                                        * @Autor: Carlos Torres
                                                                                                                        * @Mantis: 0012705
                                                                                                                        * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                                                                                        * @Descripcion: Se agrega campos al formulario
                                                                                                                        */
                                                                                                        --%>
                                                                                                        <input name="natjuridica_ver" type="hidden" id="natjuridica_ver" value="">
													<input name="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" type="hidden" id="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" value="<%=session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)%>">
                                                                                                        <td align="right">
														<%
															try {
																textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
																textAreaHelper.setCols("50");
																textAreaHelper.setRows("1");
																textAreaHelper.setReadOnly(true);
																textAreaHelper.setCssClase("camposformtext");
																textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
																textAreaHelper.render(request,out);
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<!--<td><a href="javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacion=false&calificacion=true','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>-->
														<td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=false&calificacion=false&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>
													</tr>
												</table>
											</td>
											<td width="50">Valor</td>
											<td width="150">
											<%
												try {
													textHelper.setTipo("text");
													textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
													textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
													textHelper.setCssClase("camposformtext");
													textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
													textHelper.setEditable(true);
													textHelper.setSize("20");
													textHelper.render(request,out);
												} catch(HelperException re) {
													out.println("ERROR " + re.getMessage());
												}
											%>
											</td>
				  						</tr>
									</table>
              						<table width="100%" class="camposform">
                						<tr>
                  							<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  							<td>Comentario</td>
                						</tr>
                						<tr>
                  							<td>&nbsp;</td>
                  							<td>
                  							<%
												try {
													textAreaHelper.setNombre(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
													textAreaHelper.setCols("60");
													textAreaHelper.setRows("3");
													textAreaHelper.setReadOnly(false);
													textAreaHelper.setCssClase("camposformtext");
													textAreaHelper.setId(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
													textAreaHelper.render(request,out);
												} catch(HelperException re) {
					 		 						out.println("ERROR " + re.getMessage());
												}
											%>
                  							</td>
                						</tr>
              						</table>

              						<!-- INICIO SALVEDADES -->

									<table width="95%" border="0" cellpadding="0" cellspacing="0">
                						<tr>
                  							<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  							<td class="bgnsub">Salvedades</td>
                  							<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  							<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                						</tr>
              						</table>



              						<table width="100%" class="camposform">
              						<tr>
										<td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
										<td align>Datos B&aacute;sicos </td>
									</tr>
<tr>
					<td>Fecha Radicaci&oacute;n</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><% try {
										fechaRadicacionSalvedadAnotacion.setNombre(CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION);
										fechaRadicacionSalvedadAnotacion.setCssClase("camposformtext");
										fechaRadicacionSalvedadAnotacion.setId(CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION);
										// fechaRadicacionSalvedadAnotacion.setFuncion("onBlur=\"javascript:validarFecha()\"" );
                                                                                fechaRadicacionSalvedadAnotacion.setFuncion("onkeypress=\"return valideDate(event,'"+CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION+"');\" "
                                                                                        + "onChange=\"fixDate('"+CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION+"')\" "
                                                                                        + "onBlur=\"javascript:js_DateFormatValidator('ANOTACION',ITEM__PG_CREARANOTACION_SALVEDADANOTACION_CALENDARRADICACION )\"" );
										fechaRadicacionSalvedadAnotacion.render(request,out);
										}
											catch(HelperException  re){
											out.println("ERROR " + re.getMessage());
										}
									%>
								</td>
								<td>


									<a
									  href="javascript:NewCal('<%=CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION%>','ddmmmyyyy',true,24)"
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
						numRadicacionSalvedadAnotacion.setNombre(CSalvedadAnotacion.NUMERO_RADICACION_SALVEDAD_ANOTACION);
						numRadicacionSalvedadAnotacion.setCssClase("camposformtext");
						numRadicacionSalvedadAnotacion.setId(CSalvedadAnotacion.NUMERO_RADICACION_SALVEDAD_ANOTACION);
						numRadicacionSalvedadAnotacion.render(request,out);
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
					descripcionSalvedadAnotacion.setNombre(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
					descripcionSalvedadAnotacion.setCols("80");
					descripcionSalvedadAnotacion.setRows("5");
					descripcionSalvedadAnotacion.setCssClase("camposformtext");
					descripcionSalvedadAnotacion.setId(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
					descripcionSalvedadAnotacion.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }

            %></td>
            <td>
            <table  border="0" class="camposform"><tr align="left"><td>Agregar</td></tr><tr align="right"><td><a href="javascript:cambiarAccion('AGREGAR_SALVEDAD_ANOTACION_EDICION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" name="Folio" width="35" height="25" border="0"></a></td></tr></table>
            </td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	</tr>

				<table border="0" align="center" width = "100%">
				<tr>
				 <td>&nbsp;</td>
                  <!-- <td class="camposform">Número de Salvedad</td> -->
                  <td class="camposform">Fecha Salvedad</td>
                  <td class="camposform">Radicación</td>
                  <td class="camposform">Descripción</td>
                  <td class="camposform">Eliminar</td>
				</tr>
				
	                 <%
                int idSalvedadAnotacion = 0;
                for(Iterator iter = salvedadesAnotacion.iterator(); iter.hasNext();){
                	SalvedadAnotacion salvedadAnotacion = (SalvedadAnotacion)iter.next();
				    String fechaSalvedadAnotacion = DateFormatUtil.format(salvedadAnotacion.getFechaCreacion());
				    
				    //idSalvedadAnotacion = salvedadAnotacion.getIdSalvedad();
				    String comentario = salvedadAnotacion.getDescripcion();
				    if (!salvedadAnotacion.isToDelete()) {

                %>
					
                <tr>

                  <td>&nbsp;</td>
                  <!-- <td class="camposformtext_noCase"><%= salvedadAnotacion.getIdSalvedad() %></td> -->
                  <td class="camposformtext_noCase"><%= fechaSalvedadAnotacion %></td>
                  <td class="camposformtext_noCase"><%= salvedadAnotacion.getNumRadicacion() %></td>
                  <td class="camposformtext_noCase"><%= comentario %></td>


                  <td>
	                	<img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" onClick="eliminarSalvedadAnotacion('<%= idSalvedadAnotacion %>')" style="cursor:hand">
                  </td>


                </tr>
                <!-- </table> -->

                <%
                	}
                	idSalvedadAnotacion ++;
                 }
                 %>
			</table>

									<!-- FIN SALVEDADES -->




									<br>
									<%
										if(isAnotacionAntiguoSistema) {
									%>
									<table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTAR">
										<%
											if(ocultarAS.equals("FALSE")) {
										%>
										<tr>
											<td colspan="3">
												<hr class="linehorizontal">
											</td>
										</tr>
										<tr>
											<td></td>
											<td width="16"><a href="javascript:ocultarAntiguoSistema('TRUE');campoactual('OCULTAR');"><img id="MINIMIZAR"
												src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
												width="16" height="16" border="0"></a>
											</td>
											<td><img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
										</tr>
										<%
											} else {
										%>
										<tr>
											<td align="right" class="campostip04">Haga click para maximizar los datos de Antiguo Sistema</td>
											<td width="16"><a href="javascript:ocultarAntiguoSistema('FALSE');campoactual('OCULTAR');"><img
												img id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
												width="16" height="16" border="0"></a>
											</td>
											<td><img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
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
												ash.setNLibroTipoAS(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1");
												ash.setNLibroNumeroAS(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1");
												ash.setNLibroPaginaAS(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1");
												ash.setNLibroAnoAS(CDatosAntiguoSistema.LIBRO_ANO_AS + "1");

												ash.setNTomoNumeroAS(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1");
												ash.setNTomoPaginaAS(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1");
												ash.setNTomoMunicipioAS(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1");
												ash.setNTomoAnoAS(CDatosAntiguoSistema.TOMO_ANO_AS + "1");

												ash.setNDocumentoFechaAS(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1");
												ash.setNDocumentoTipoAS(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1");
												ash.setNDocumentoNumeroAS(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1");
												ash.setNDocumentoComentarioAS(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1");

												ash.setNPrefijoOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS + "1");
												ash.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO + "1");
												ash.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO + "1");
												ash.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC + "1");
												ash.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC + "1");
												ash.setNIdVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA + "1");
												ash.setNNomVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA + "1");
												ash.setNIdOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA + "1");
												ash.setNNumOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM + "1");
												ash.setNIdDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO + "1");
												ash.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO + "1");
												ash.setNNumOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN + "1");
												ash.setNTipoNomOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN + "1");
												ash.setNTipoOficinaHiddenn(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN + "1");
												ash.setNIdOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN + "1");
												ash.setNComentarioAS(CDatosAntiguoSistema.COMENTARIO_AS + "1");

												ash.render(request, out);
											}
										}
									%>
              						<br>
              						<hr class="linehorizontal">
          							<%
          								try {
											//TODO set ciudadanos
											variosCiudadanosHelper.render(request,out);
		  								} catch(HelperException re) {
											out.println("ERROR " + re.getMessage());
											re.printStackTrace();
										}
		  							%>
              					</td>
          					</tr>
        				</table>
         			</td>
        			<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      			</tr>
      			<tr>
        			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        			<td valign="top" bgcolor="#79849B" class="tdtablacentral">
        				<table width="100%" class="camposform">
          					<tr>
            					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            					<td width="150"><a href="javascript:cambiarAccion('GRABAR_EDICION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="160" height="21" border="0"></a></td>
            					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            					<td width="150"><a href="javascript:cambiarAccion('AGREGAR_SEG_ENG')"><img src="<%=request.getContextPath()%>/jsp/images/btn_englobar_v2.gif" width="200" height="21" border="0"></a></td>
            					<td width="150">
                                                </td>
            					<td>&nbsp;</td>
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
		</td>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		<td>&nbsp;</td>
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
        			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
        				<table border="0" cellpadding="0" cellspacing="0">
            				<tr>
              					<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES</td>
              					<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              					<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              						<table width="100%" border="0" cellspacing="0" cellpadding="0">
                  						<tr>
                    						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
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
        				<table width="100%" >
        				</table>
         			</td>
        			<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      			</tr>
      			<tr>
        			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        			<td valign="top" bgcolor="#79849B" class="tdtablacentral">
        				<table width="100%" class="camposform">
          					<tr>
            					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <%-- SOF:BUTTON --%>

                  <td width="50">
                    <a href="javascript:js_OnEvent( 'ANOTACION', ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT );">
                     <img alt="procesar"   src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" />
                    </a>
                  </td>
                  <%-- EOF:BUTTON --%>

            					<%--
            					 <a href="admin.crear.folio.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="139" height="21" border="0" id="Folio">
            					 </a>
            					  --%>
            					</td>
            					<td>&nbsp;</td>
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
  	</form>
</table>
