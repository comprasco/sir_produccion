<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionesEnglobeHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacion"%>
<%@page import="gov.sir.core.web.helpers.comun.RadioHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWSegregacion"%>
<%@page import="gov.sir.core.negocio.modelo.WebSegregacion"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.WebFolioHeredado"%>
<%@page import="gov.sir.core.negocio.modelo.WebAnotaHereda"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionCancelacionHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>

<%
	final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMID        = "FORMA_CANCELACION";
	final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET0_FORMID       = "FORMA_CANCELACION";
	final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMID       = "PAGINADOR_ADENTRO";
	final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMFIELDID   = "ESCOGER_ANOTACION_CANCELACION";
	final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID   = "TARGET_SELECTIONCOLLECTOR";

%>

<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/forms-lib.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/AnchorPosition.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/PopupWindow.js" ></script>
  <style type="text/css">

	  .forms-help {
		 border-style: dotted;
		 border-width: 1px;
		 padding: 5px;
		 background-color:#FFFFC0; /* light yellow */
		 width: 200px; /* otherwise IE does a weird layout */
		 z-index:1000; /* must be higher than forms-tabContent */
	   }


  </style>


<%
	
	WebSegregacion webSegregacion = (WebSegregacion)session.getAttribute(WebKeys.WEB_SEGREGACION);
	List anotacionesSeleccionadas = new ArrayList();
	
	if(webSegregacion!=null){
		if(webSegregacion.getFoliosHeredados().size()>0){
			WebFolioHeredado webFolio = (WebFolioHeredado)webSegregacion.getFoliosHeredados().get(0);
			List anotacionesheredadas = webFolio.getAnotacionesHeredadas();
			if(anotacionesheredadas!=null && anotacionesheredadas.size()>0){
				Iterator it = anotacionesheredadas.iterator();
				while(it.hasNext()){
					WebAnotaHereda anotacion = (WebAnotaHereda)it.next();
					anotacionesSeleccionadas.add(anotacion.getIdAnotacion());
				}
			}
		}
	}
	
	
	session.removeAttribute(gov.sir.core.web.WebKeys.LISTA_FOLIOS_DERIVADOS);
	
	String vistaEdicionAnotacion = (String)session.getAttribute("org.auriga.smart.vistas.VistaAnterior");
	if(vistaEdicionAnotacion!=null && vistaEdicionAnotacion.equals(AWModificarFolio.VISTA_EDICION_ANOTACION)) {
		session.setAttribute(AWModificarFolio.VISTA_SEGREGACION_ANOTACION_DEFINITIVA,vistaEdicionAnotacion);
	}	
   %>
   
<% 
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	String vistaActual;
	String nomFormaCancelacion="FORMA_CANCELACION";
	String nomOrdenCancelada="NOM_ORDEN_CANCELADA";
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	
	String posicion="";
	String idAnotacionCancelacion="";
	String temp;
	temp=(String)session.getAttribute("ESCOGER_ANOTACION_CANCELACION");
	if(temp!=null){
		idAnotacionCancelacion=temp;
	}
	
	
	//se mira si ya esta seteado llavesPaginador
	LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
	LLaveMostrarFolioHelper lla=null;
	if(llaves==null){
		//se crea el objeto llavesPaginador y settear sus valores;
		llaves= new LLavesMostrarFolioHelper();
		lla= new LLaveMostrarFolioHelper();
		lla.setNombrePaginador("NOMBRE_PAGINADOR_SEGREGACION");
		lla.setNombreResultado("NOMBRE_RESULTADO_SEGREGACION");
		lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_SEGREGACION");
		llaves.addLLave(lla);
		request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
	}
	if(lla==null){
		lla=llaves.getLLave("NOMBRE_RESULTADO_SEGREGACION", "NOMBRE_PAGINADOR_SEGREGACION");
	}
	
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;

   %>

  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
   <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>   
   <script type="text/javascript">
   function verAnotacion(nombre,valor,dimensiones,pos)
{ 
	document.SEGREGAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
	}
	function cambiarAccion(text) {
       document.SEGREGAR.ACCION.value = text;
       document.SEGREGAR.submit();
     }
	
	 function cargarAnotaciones(text) {
       document.SEGREGAR.MATRICULA.value = text;
       cambiarAccion('CARGAR_ANOTACIONES');
       }
     
     function grabarTemp(text) {
       document.SEGREGAR.MATRICULA.value = text;
       cambiarAccion('GRABACION_TEMPORAL_ANOTACION_CANCELACION');
       }
       
       function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.CANCELAR.ACCION.value='<%=AWCalificacion.PRESERVAR_INFO_CANCELACION%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	<%session.setAttribute("cancelar", "true"); %>
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.SEGREGAR.POSICION.value=pos;
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

function Secuencia(text){
	document.SEGREGAR.<%=CAnotacionCiudadano.SECUENCIA %>.value = text;  
	cambiarAccion('<%=AWCalificacion.REFRESCAR_CANCELACION%>');
}
function quitar(pos,accion) {
	document.SEGREGAR.POSICION.value = pos;
	cambiarAccion(accion);
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

<%-- Bug 3562 --%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- SOF:REGION  JavascriptClasses                               --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">
	// local form manipulation

// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// sof:JsClazz
//		LocalForm
//
//   	Usada para abstraer funcionalidad
// 	de formulario, compatible con
//	multiples browsers
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------

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

     eval( "formObject."+ elementName + ".value" + "=" + "elementValue" );
   }

// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// eof:JsClazz
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------

</script>

<script type="text/javascript">

// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// sof:JsClazz
//		JsLocalUtils
//
//  Utilidades de conversion
// 	- Array2Cvs
//	- Csv2Array
//	- FieldIsMultiple: verificar si un checkbox es unico o es un arreglo
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------

function JsLocalUtils(){
}

JsLocalUtils.fieldIsMultiple = function ( local_Object ) {
	if( null == local_Object )
		return false;

	if( ( typeof local_Object.type != "string" )
	  &&( local_Object.length > 0 )
	  &&( local_Object[0] != null )
	  &&( local_Object[0].type.toLowerCase() == "checkbox" ) ) {
	  	return true;
	}

	return false;
}

JsLocalUtils.debugArray = function ( local_Array, local_ArrayName ) {
	if( null == local_Array )
		return;

	var msg = new String();
	for( var i=0; i< local_Array.length; i++ ) {
		msg += ( i + ": " + local_Array[i] + "\n");
	}
	alert( local_ArrayName + "\n" + msg );
}

JsLocalUtils.csvStringToStringArray = function ( local_Object, local_Separator, ignoreNulls ) {

	if( null == local_Object )
		return null;

	// Array< String >
	var local_Result;

	local_Result = local_Object.split( local_Separator );

	if( ignoreNulls ) {
		var tmp_Result;
		tmp_Result = new Array();
		for( var i=0; i < local_Result.length; i++ ){
			if( null == local_Result[i] ) {
				continue;
			}
			if( "" == local_Result[i] ) {
				continue;
			}
			tmp_Result.push( local_Result[i] );
		}

		local_Result = tmp_Result;
	}

	return local_Result;

} // end-method: utils_CsvStringToStringArray

JsLocalUtils.stringArrayToCsvString = function ( local_Object, local_Separator, ignoreNulls ) {

	if( null == local_Object )
		return null;

	if( 0 == local_Object.length )
		return "";


	// String
	var local_Result;
	local_Result = new String();

        var first;

        first= 0;

	for( var i=0; i < local_Object.length; i++ ) {

		if( ignoreNulls && ( null == local_Object[i] ) ) {
			continue;
		}

		if( 0 != first ) {
			local_Result += local_Separator;
		}
		local_Result += local_Object[i];
                first ++;
	}

        // alert( "csv:" + local_Result );

	return local_Result;

} // end-method: utils_StringArrayToCsvString

// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// eof:JsClazz
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------

</script>


<script type="text/javascript">

// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// sof:JsClazz
//		CheckBoxMultiselectController
//
//   	Usada para enviar los valores seleccionados
// 		de un multiselect-checkbox
//		hacia otro formulario
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------

function CheckBoxMultiselectController() {

	// :: attributes

	this._ControlledFieldCheckbox 		= null;	// form-checkbox-item // document.form1.form1_field1
	this._ControlledFieldSelectedValuesArray = new Array();
	// this._ControlledFieldSelectedValues = null; // form-text-item	//document.form1_listener1.form1_listener1_field1

	this._T0_ModelList = new Array(); // los que no estan representadops visualmente pero existen
	this._T1_ModelList = new Array(); // los que estan representadops visualmente



	this._CheckBoxController_Csv_Separator  = ",";			//separator


	// :: methods (public)

	this.setControlledFieldCheckbox       	= CBMC_setControlledFieldCheckbox;
	this.addControlledFieldSelectedValues 	= CBMC_addControlledFieldSelectedValues;
	this.delControlledFieldSelectedValues 	= CBMC_delControlledFieldSelectedValues;
	this.setCsvSeparator 					= CBMC_setCsvSeparator;

	this.onLoad   = CBMC_onLoad;
	this.onChange = CBMC_onChange;

	// :: methods (private)

	this.onChange_WriteValues 	= CBMC_onChange_WriteValues;
	this.findformField 			= CBMC_findformField;
	this.formField_setValue		= CBMC_formField_setValue;

	this.debugArray				= CBMC_debugArray;

}

function CBMC_findformField( formName, fieldName ) {
	var local_FormObject;
	local_FormObject = findObj( formName );

	if( null == local_FormObject ) {
		alert("ERROR:" + formName + " no encontrado" );
		return null;
	}

	var local_FieldObject;

	eval( "local_FieldObject" + "= " + "local_FormObject" +"." + fieldName );

	//alert( local_FieldObject );

	return local_FieldObject;

	local_FieldObject = findObj( local_FormObject, fieldName );

	if( null == local_FieldObject ) {
		alert("ERROR:" + fieldName + " no encontrado" );
		return null ;
	}
	//alert( "object:" + local_FieldObject + "," + "local_FieldObject.value" + local_FieldObject.value );
	return local_FieldObject;
}

function CBMC_formField_setValue( elementName , elementValue ) {
	var local_FormObject;

	local_FormObject = findObj( this.formName );

    if( local_FormObject == null )
	   return;

    eval( "local_FormObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")) );
}

function CBMC_setControlledFieldCheckbox( formName, fieldName ) {
	this._ControlledFieldCheckbox = this.findformField( formName, fieldName );
}

function CBMC_addControlledFieldSelectedValues( formName, fieldName ) {
	var local_TextField;
	local_TextField = this.findformField( formName, fieldName );
        if( null == local_TextField ) {
          alert( "ERROR:" + "no encontrado campo " + formName + "." + fieldName );
        }
	this._ControlledFieldSelectedValuesArray.push( local_TextField );
}
function CBMC_delControlledFieldSelectedValues( formName, fieldName ) {
	var local_TextField;
	local_TextField = this.findformField( formName, fieldName );
	for( var i=0; i < this._ControlledFieldSelectedValuesArray.length; i++ ) {
		if( null == this._ControlledFieldSelectedValuesArray[i]) {
			continue;
		}
		if( local_TextField == this._ControlledFieldSelectedValuesArray[i] ) {
			this._ControlledFieldSelectedValuesArray[i] = null;
		}
	}
}

function CBMC_setCsvSeparator( separator ) {
	this._CheckBoxController_Csv_Separator = separator;
}

function CBMC_onLoad( local_ValuesCsv, synchronizeWidgets ) {


	// modificacion;

	// en el modelo se mantendra:
	// un array de la siguiente forma:
	// Array< id, value >
	// donde id es un entero que marca la posicion del item
	// value marca el valor del item; si no es null, esta seleccionado
	//

	var tmp_SelectedValues;
	tmp_SelectedValues = JsLocalUtils.csvStringToStringArray( local_ValuesCsv, this._CheckBoxController_Csv_Separator, true );

        // alert( 'tmp_SelectedValues' + tmp_SelectedValues );

	// obtener la referencia del field;

	var local_CheckboxField;
	var local_TextField;

	local_CheckboxField = this._ControlledFieldCheckbox;
	local_TextField     = this._ControlledFieldSelectedValues;



	var t0_ModelList;
	var t1_ModelList;

	t0_ModelList = this._T0_ModelList;
	t1_ModelList = this._T1_ModelList;

	var local_Value;
	var founded;


	// put the values in the Model
	// if needed, verify the input-checks-selection

	if( JsLocalUtils.fieldIsMultiple( local_CheckboxField ) ) {

		// marcar los que estan incluidos en
		// los items que se visualizan

		for( var i=0; i < local_CheckboxField.length; i++ ) {

			// --------------------------------------------------
			local_Value = local_CheckboxField[i].value;
			// select (1) item ---------------------------------

			founded = false;
			for( var j=0; j < tmp_SelectedValues.length; j++ ) {

				if( tmp_SelectedValues[j] == local_Value ) {
					founded = true;
					break;
				}


			} // for

			if( !founded ) {
				//alert("no encontrado " + local_Value );
				// t0_ModelList.push( local_Value );
				t1_ModelList[i] = null;


			}
			else {
				//alert("si encontrado " + local_Value );
				t1_ModelList[i] = local_Value;

				if( synchronizeWidgets ) {
					local_CheckboxField[i].checked = true;
				}

			} // if

			// --------------------------------------------------


		} // for

	}
	else {

		// --------------------------------------------------
		local_Value = local_CheckboxField.value;
		var i = 0;

		// select (1) item ---------------------------------
		founded = false;
		for( var j=0; j < tmp_SelectedValues.length; j++ ) {

			if( tmp_SelectedValues[j] == local_Value ) {
				founded = true;
				break;
			}


		} // for

		if( !founded ) {
			//alert("no encontrado " + local_Value );
			// t0_ModelList.push( local_Value );
			t1_ModelList[i] = null;


		}
		else {
			//alert("si encontrado " + local_Value );

			t1_ModelList[i] = local_Value;

			if( synchronizeWidgets ) {
				local_CheckboxField.checked = true;
			}

		} // if

	} // end-if



	// --------------------------------------------------


	// incluir los que faltaron por ser incluidos
	// de la lista temporal

	// son los que estan en tmp_SelectedValues
	// que no se han incluido a t0_ModelList, t1_ModelList

	for( var j=0; j < tmp_SelectedValues.length; j++ ) {

		if( null == tmp_SelectedValues[j] ) {
			continue;
		}

		// alert("this:" + tmp_SelectedValues[j] );

		founded = false;

		if( !founded ) {
			for( var i=0; i < t1_ModelList.length; i++ ) {

				if( tmp_SelectedValues[j] == t1_ModelList[i] ) {
					founded = true;
					break;
				} // if

			} // for

		} // if

		if( !founded ) {
			for( var i=0; i < t0_ModelList.length; i++ ) {

				if( tmp_SelectedValues[j] == t0_ModelList[i] ) {
					founded = true;
					break;
				} // if

			} // for

		} // if

		if( !founded ) {
			t0_ModelList.push( tmp_SelectedValues[j] );
		}


	} // for

	// this.debugArray( t0_ModelList, "t0_ModelList" );
	// this.debugArray( t1_ModelList, "t1_ModelList" );
	// ----------------------------------------------------------------------
	// escribir textfield



	// put the values in the field
	this.onChange_WriteValues();
	//local_TextField.value = local_ValuesCsv;


} // end-method: checkboxController_OnLoad


function CBMC_debugArray( local_Array, local_ArrayName ) {
  JsLocalUtils.debugArray( local_Array, local_ArrayName );
} // end-method:CBMC_debugArray


function CBMC_onChange( local_CheckBoxItem ) {

	var t0_ModelList;
	var t1_ModelList;

	t0_ModelList = this._T0_ModelList;
	t1_ModelList = this._T1_ModelList;


	// obtener la referencia del field;

	var local_CheckboxField;
	var local_TextField;

	local_CheckboxField = this._ControlledFieldCheckbox;
	local_TextField     = this._ControlledFieldSelectedValues;

	var founded;
	var index;

	if( JsLocalUtils.fieldIsMultiple( local_CheckboxField ) ) {

		founded = false;
		for( var i=0; i < local_CheckboxField.length; i++ ) {


			if( local_CheckboxField[i] == local_CheckBoxItem ) {
				founded = true;
				index = i;
				break;
			} // if

			// --------------------------------------------------

			// --------------------------------------------------


		} // for

		if( founded ) {

			var local_Value = local_CheckBoxItem.value;

			if( local_CheckBoxItem.checked ) {
				t1_ModelList[index] = local_Value;
			}
			else {
				t1_ModelList[index] = null;
			} // if

		}
		else {
			alert( "DEBUG: valor no encontrado" );
		}

	}
	else {

		var i=0;
		index = i;

		if( local_CheckboxField == local_CheckBoxItem ) {
			founded = true;
			index = i;
		} // if

		if( founded ) {

			var local_Value = local_CheckBoxItem.value;

			if( local_CheckBoxItem.checked ) {
				t1_ModelList[index] = local_Value;
			}
			else {
				t1_ModelList[index] = null;
			} // if

		}
		else {
			alert( "DEBUG: valor no encontrado" );
		}




	} // end-if

	// write the values to the other form
	this.onChange_WriteValues();

	//this.debugArray( t0_ModelList, "t0_ModelList" );
	//this.debugArray( t1_ModelList, "t1_ModelList" );


} // end-method:checkboxController_OnChange


function CBMC_onChange_WriteValues() {


	var t0_ModelList;
	var t1_ModelList;

	t0_ModelList = this._T0_ModelList;
	t1_ModelList = this._T1_ModelList;


	var t2_ModelList;
	t2_ModelList = new Array();


	// unir los 2 arreglos;
	var i;
	for( i=0; i < t0_ModelList.length; i++ ) {
		t2_ModelList.push( t0_ModelList[i] );
	}
	for( i=0; i < t1_ModelList.length; i++ ) {
		t2_ModelList.push( t1_ModelList[i] );
	}

	// obtener la referencia del field;

	var local_CheckboxField;
	var local_TextField;

	local_CheckboxField = this._ControlledFieldCheckbox;


	var checkBoxController_Csv_Separator  = this._CheckBoxController_Csv_Separator;

	var local_ValuesCsv;
	local_ValuesCsv = "";

	local_ValuesCsv = JsLocalUtils.stringArrayToCsvString( t2_ModelList, checkBoxController_Csv_Separator, true );

	var local_TextFieldArray = this._ControlledFieldSelectedValuesArray;

	// notify "observers"

	if( null == local_TextFieldArray ) {
		local_TextFieldArray = new Array();
	}
	for( i=0; i < local_TextFieldArray.length; i++ ){
		local_TextField = 	local_TextFieldArray[i];
                if( null == local_TextField ) {
                  continue;
                }
		local_TextField.value = local_ValuesCsv;
	}

} // end-method: checkboxController_OnChange_WriteValues


// ---------------------------------------------------------------------
// ---------------------------------------------------------------------
// eof:JsClazz
// ---------------------------------------------------------------------
// ---------------------------------------------------------------------


</script>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- EOF:REGION  JavascriptClasses                               --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>


<script type="text/javascript">

// simple-listener -----------------------------------------------------
// ---------------------------------------------------------------------

// function notify( text ) {
//	// alert( "checkbox".toUpperCase() );
//}

// ---------------------------------------------------------------------

</script>

<script type="text/javascript">

// function cambiarSeleccionCancelacion(text){
//
// 	//	document.form1_listener1.form1_listener1_field1.value = text;
//
//
//      if( ( undefined != window.notify )
//        ||( null !=  window.notify ) ) {
//        notify( text );
//      }
//
//
// } // end-method: cambiarSeleccionCancelacion

</script>

<script type="text/javascript">
	// local form dependant resources

	var actionField     = "<%= WebKeys.ACCION    %>";
        var parameterField  = "<%= WebKeys.PARAMETRO %>";

        // (TODO: cambiar a nuevos items)
        // @see guardar() ???


</script>

<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
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


   function js_OnEvent2( formName, actionValue, paramaterValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.setValue( parameterField, parameterValue );
     htmlForm.submitForm();

   }


</script>



<script type="text/javascript">

  <%

    boolean local_AnotacionesToBeCanceledSelect_EnabledRegionFlag = true;

    //gov.sir.core.web.helpers.correccion.region.model.Region local_RegionObj_AnotacionCanceladoraEditar;
    //local_RegionObj_AnotacionCanceladoraEditar = pageLocalRegionManager.getRegionById( "htRgn_AnotacionCanceladoraEditar_SelectItems" );

    //if( null != local_RegionObj_AnotacionCanceladoraEditar ) {

    //  if( local_RegionObj_AnotacionCanceladoraEditar.getConditionalDisplay().getDisplayResult() == gov.sir.core.web.helpers.correccion.region.model.Region.ConditionalDisplay.SHOW ){
    //    local_AnotacionesToBeCanceledSelect_EnabledRegionFlag = true;
    //  }

    //} // if

  %>




  var js_Local_AnotacionesToBeCanceledSelect_EnabledRegionFlag = "<%=( local_AnotacionesToBeCanceledSelect_EnabledRegionFlag )?( "enabled" ):( "disabled" )%>";


</script>

<script type="text/javascript">
   function js_OnEvent_Proxy1( formName, actionValue ) {

      // ignore the lines of js_OnEvent;
      // call directly to the helper burned method;
      // bug 3547 only call when mostrar-folio-helper is enabled

      if( ( undefined == js_Local_AnotacionesToBeCanceledSelect_EnabledRegionFlag )
         || ( null == js_Local_AnotacionesToBeCanceledSelect_EnabledRegionFlag )
         || ( "disabled" == js_Local_AnotacionesToBeCanceledSelect_EnabledRegionFlag ) ) {


           // default send, no validate selection

           js_OnEvent( formName, actionValue );

           return;
      }

      // Mostrar cuando el usuario tiene permiso

      if( ( undefined == window.guardar )
         || ( null == window.guardar ) ) {
        alert( "[Guardar No esta Definido]" );
        return;
      }

      guardar();

   }



</script>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
          ASISTENTE SEGREGACI&OACUTE;N - PASO 2 - SELECCIONAR ANOTACIONES A HEREDAR
            </td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Anotaciones
                  del Folio
                  </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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
              <br>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Anotaciones</td>
                  <td>&nbsp;</td>
                </tr>

<script type="text/javascript">

// declare the controller
var local_CheckBoxControllerEnabled;
local_CheckBoxControllerEnabled = true;

var local_CheckBoxController;
local_CheckBoxController = new CheckBoxMultiselectController();
</script>

			   <%try{
						List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
						List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);	
			   
			   			//setear atributos del folio.
			   			mFolio.setFolio(f);
			   			mFolio.setTipoMostrarFolio(CTipoMostrarFolioHelper.TIPO_COMPRIMIDO);
			   			mFolio.setNomOrdenCancelada(nomOrdenCancelada);
			   			mFolio.setIdAnotacionCancelada(idAnotacionCancelacion);
			   			
			   			//setters de estilo
			   			mFolio.setECampos("camposform");
			   			mFolio.setECampoTexto("campositem");
			   			mFolio.setETituloFolio("titulotbcentral");
			   			mFolio.setETitulos("titresaltados");
			   			mFolio.setETitulosSecciones("bgnsub");
			   			
			   			//setters de imagenes 
			   			mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
			   			mFolio.setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");
			   			
			   			mFolio.setNombreAccionFolio("segregacion.do");
			   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
			   			mFolio.setNombreAncla("ancla");
			   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
			   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
			   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
			   			mFolio.setNombreAccionCancelacion("segregacion.do");
			   			mFolio.setNombreFormaCancelacion(nomFormaCancelacion);
			   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_SEGREGACION");
			   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
			   			mFolio.setNombreOcultarFolio("O_FOLIO");
			   			mFolio.setNombrePaginador(lla.getNombrePaginador());
			   			mFolio.setNombreResultado(lla.getNombreResultado());
			   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
			   			mFolio.setPaginaInicial(0);
			   			mFolio.setVistaActual(vistaActual);
			   			//datos a mostrar encabezado
			   			mFolio.NoMostrarEncabezado();
			   			mFolio.mostrarAnotacionesCancelacioMultiple(AWCalificacion.GUARDAR_ANOTACIONES_TEMPORALES);
			   			mFolio.setEditarAnotacionesNoTemporales(true);
                                                mFolio.setEnabledCheckAnotacionesTemporales(true);
						
						// para habilitar bug 5042 parte 2-------------------------------------------------------------------------------------------------
                                                mFolio.setCheckboxController_MultiSelectEnabled( true );
                                                mFolio.setCheckboxController_JsControllerName( "local_CheckBoxController" );

                                                mFolio.setCheckboxController_SourceFormFieldId( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMFIELDID  );
                                                mFolio.setCheckboxController_TargetFormFieldId( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID );
                                                mFolio.setCheckboxController_MultiSelectDebugEnabled( false );
			   			mFolio.render(request, out);
				   }catch(HelperException re){
					 	out.println("ERROR " + re.getMessage());
					}
				%>


              </table>
<%
// Extract SelectedIdValues

String[] local_CheckBoxControllerSelectedIds;
String   local_CheckBoxControllerSelectedIdsCsv;

local_CheckBoxControllerSelectedIds = (String[])session.getAttribute( gov.sir.core.web.helpers.comun.MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION );
if( null == local_CheckBoxControllerSelectedIds ) {
  local_CheckBoxControllerSelectedIds = new String[0];
} // if

// mismos procedimientosd pero en java

local_CheckBoxControllerSelectedIdsCsv = JvLocalUtils.stringArrayToCsvString( local_CheckBoxControllerSelectedIds, JvLocalUtils.DEFAULT_SEPARATOR, true );

%>


<script type="text/javascript">

  if( !( ( undefined == window.local_CheckBoxControllerEnabled )
       ||( null == window.local_CheckBoxControllerEnabled ) ) ) {


	// prepare for receive notifications + load defaults



        // campo donde estan los valores en sesion
        var LOCAL_CHECKBOXCONTROLLER_CUMULATEDVALUES = '<%= local_CheckBoxControllerSelectedIdsCsv %>';

        // campo/forma donde esta el multiselect-checkbox
        var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMID = '<%= "FORMA_CANCELACION" %>';
        var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET0_FORMID      = '<%= LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET0_FORMID      %>';
        var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMID      = '<%= LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMID      %>';


        // campo/forma donde esta el campo de texto con los valores seleccionados
        var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMFIELDID = '<%= "ESCOGER_ANOTACION_CANCELACION" %>';
        var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID  = '<%= LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID  %>';
        //var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMID = '<%= "PAGINADOR_ADENTRO" %>';
        //var LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMFIELDID = '<%= "SELECTION_COLLECTOR" %>';

        // configurar el componente
	local_CheckBoxController.setControlledFieldCheckbox(       LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMID , LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_SOURCE_FORMFIELDID  );
	local_CheckBoxController.addControlledFieldSelectedValues( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET0_FORMID, LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID );
	local_CheckBoxController.addControlledFieldSelectedValues( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET1_FORMID, LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID );
	local_CheckBoxController.onLoad( LOCAL_CHECKBOXCONTROLLER_CUMULATEDVALUES, true);

  } // if

</script>

<script type="text/javascript">
function enviarDatos()
{
    document.SEGREGAR.TARGET_SELECTIONCOLLECTOR.value = document.<%= "FORMA_CANCELACION" %>.TARGET_SELECTIONCOLLECTOR.value;
    cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWSegregacion.SEGREGACION_HERENCIA%>')
}
</script>

            <form action="segregacion.do" method="post" name="SEGREGAR" id="SEGREGAR">
            <input type="hidden" name="ACCION" value="">
            <input type="hidden" name="POSICION" value="">
            <input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1">
            <input type="hidden" name="TARGET_SELECTIONCOLLECTOR" value="">
            <input type="hidden" name="CAMBIO" value="">
            <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
              <table width="100%" class="camposform">              
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td colspan='5'>Copiar comentario en las anotaciones heredadas ?</td>
                </tr>
                <tr>
                  <td width="20">&nbsp;</td>
                  <td width="20">
					<%
							gov.sir.core.web.helpers.comun.RadioHelper radio = new RadioHelper();
							if(webSegregacion!=null && webSegregacion.getFoliosHeredados()!=null && webSegregacion.getFoliosHeredados().size()>0){
								WebFolioHeredado webFolioHeredado = (WebFolioHeredado)webSegregacion.getFoliosHeredados().get(0);
								if(webFolioHeredado==null || webFolioHeredado.isCopiaComentario()){
									session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION , CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
								}else{
									session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION , CAnotacion.NO_GUARDAR_COMENTARIO_ANOTACION);
								}
							}
							
 		                    String defecto = (String)request.getSession().getAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
 		                    if(defecto==null){
 		                    	request.getSession().setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION,CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
 		                    }

							try {
 		                        radio.setNombre(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setId(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setValordefecto(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
							    radio.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
					%>
 				  </td>
                  <td width="60"> Si </td>
                  <td width="20">
							<% try {
 		                        radio.setNombre(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setId(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setValordefecto(CAnotacion.NO_GUARDAR_COMENTARIO_ANOTACION);
							    radio.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>
                  </td>
                  <td width="60">No</td>
                  <td width="70%">&nbsp;</td>
                </tr>
              </table>

              <hr class="linehorizontal">
              <br>
              <table width="100%" class="camposform">

                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150"><a href="javascript:enviarDatos();"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" name="Folio" width="139" height="21" border="0" id="Folio"></td>
                  
					<!--BOTON REGRESAR-->                 
                    <TD width="150">
                    <%if(session.getAttribute(AWModificarFolio.VISTA_SEGREGACION_ANOTACION_DEFINITIVA)!=null) {%>
                    	<a href=<%=(String)session.getAttribute(AWModificarFolio.VISTA_SEGREGACION_ANOTACION_DEFINITIVA) %>>
                    <%}else{ %>
                    	<a href="registro.segregar.anotacion.view?POSSCROLL=<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
                    <%} %>
	                 	<img alt="Regresar" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
	                </a>
					</TD>
					
					<!--BOTON CANCELAR-->   						                  
	                <TD width="150">
					<%-- SOF:SCRIPT-VARS --%>
					<script type="text/javascript">
					   var CANCELAR_SEGREGACION = "<%= AWSegregacion.CANCELAR_SEGREGACION %>";
					   var ELIMINAR_SEGREGACION = "<%= AWSegregacion.ELIMINAR_SEGREGACION %>";
					   function eliminarSegregacion(){
					      if(confirm('ï¿½Desea eliminar la segregaciï¿½n en curso?')){
					         cambiarAccion( ELIMINAR_SEGREGACION );
					      }
					   }						   
					</script>
					<%-- EOF:SCRIPT-VARS --%>
	
		              <%-- SOF:BUTTON --%>
		                <a href="javascript:cambiarAccion( CANCELAR_SEGREGACION );">
		                 <img alt="Salir de la segregaciï¿½n en curso" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
		                </a>
		              <%-- EOF:BUTTON --%>
                     </TD>

			          <!--ELIMINAR SEGREGACIÓN EN CURSO-->
			          <td width="150">
			            <a href="javascript:eliminarSegregacion();">
			             <img alt="Eliminar segregaciï¿½n en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_segregacion.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
			            </a>
			      	  </td>        


    			  <td>&nbsp;</td>
    			  <td>&nbsp;</td>
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
