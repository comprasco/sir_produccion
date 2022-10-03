<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper" %>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%@taglib prefix="xRegionTemplate" uri="/xRegionTemplateTags" %>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%-- privileges resources --%>

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

<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%
    String idPermisoArea = (String)request.getSession().getAttribute(AWModificarFolio.PERMISO_AREA);
    
    //LISTA TIPO DETERMINACION DE INMUEBLE
ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
        List determInm = (List) session.getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
            if(determInm == null){
                determInm = new ArrayList();
            }
        tipoDetermInmHelper.setTipos(determInm);
        tipoDetermInmHelper.setCssClase("camposformtext");
        
	//LISTA TIPOS DE PREDIO
ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
List tiposPredio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
if(tiposPredio == null){
tiposPredio = new Vector();
}
long numSegregacionesVacias = 0;
if(session.getAttribute(AWModificarFolio.NUMERO_SEGREGACIONES_VACIAS)!=null){
numSegregacionesVacias = ((Long)session.getAttribute(AWModificarFolio.NUMERO_SEGREGACIONES_VACIAS)).longValue();
}
tipoPredioHelper.setOrdenar(false);
tipoPredioHelper.setTipos(tiposPredio);
tipoPredioHelper.setCssClase("camposformtext");

	//LISTA DE ESTADO DEL FOLIO
ListaElementoHelper estadoFolioHelper = new ListaElementoHelper();
List estadosFolio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA);
        /*
        * Remueve el estado Trasladado
        * Author: Santiago V?squez
        * Change: 1156.111.USUARIOS.ROLES.INACTIVOS
        * Validaci?n de roles no configurados
        */
        List estados = new ArrayList();
        if(estadosFolio == null){
        estadosFolio = new Vector();
    } else {
    for (int i = 0 ; i < estadosFolio.size() ; i++) {
    ElementoLista estado = (ElementoLista) estadosFolio.get(i);
    if (!estado.getId().equals(CEstadoFolio.TRASLADADO)) {
    estados.add(new ElementoLista(estado.getId(), estado.getValor()));
}
}
}
estadoFolioHelper.setOrdenar(false);
estadoFolioHelper.setTipos(estados);
estadoFolioHelper.setCssClase("camposformtext");


	//LISTA DE EJES PARA LAS DIRECCIONES
ListaElementoHelper ejesHelper = new ListaElementoHelper();
List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
if(ejes == null){
ejes = new Vector();
}
ejesHelper.setCssClase("camposformtext");
ejesHelper.setOrdenar(false);
ejesHelper.setTipos(ejes);

MostrarFechaHelper fecha = new MostrarFechaHelper();
DireccionesHelper dirHelper = new DireccionesHelper(AWModificarFolio.AGREGAR_DIRECCION_CORRECCION,AWModificarFolio.ELIMINAR_DIRECCION_CORRECCION,AWModificarFolio.EDITAR_DIRECCION_CORRECCION_TEMP,"");

dirHelper.setFolioSesion(WebKeys.FOLIO);
ListaElementoHelper ejes2Helper = new ListaElementoHelper();
ejes2Helper.setCssClase("camposformtext");
ejes2Helper.setOrdenar(false);
ejes2Helper.setTipos(ejes);
TextHelper textHelper = new TextHelper();
TextHelper areasHelper = new TextHelper();
TDHelper tdHelper = new TDHelper();
TextHelper hiddenHelper = new TextHelper();
hiddenHelper.setTipo("hidden");
 	//hiddenHelper.setTipo("text");
TextAreaHelper textAreaHelper = new TextAreaHelper();
AnotacionesModificarFolioHelper anotacionesModificacionHelper = new AnotacionesModificarFolioHelper();
Calendar calendar = Calendar.getInstance();
calendar.setTime(new Date());
//    gov.sir.core.negocio.modelo.Folio folio = (gov.sir.core.negocio.modelo.Folio) request.getSession().getAttribute(WebKeys.FOLIO);

	//RECUPERAR INFORMACI?N DE LA SESI?N
String idMatricula = (String)request.getSession().getAttribute(CFolio.ID_MATRICULA);
String documento     = (String)request.getSession().getAttribute(gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIO_COD_DOCUMENTO);
Circulo circulo   = (Circulo)session.getAttribute(WebKeys.CIRCULO);






    // Bug: 3580
String local_Folio_FechaApertura = (String)session.getAttribute( AWModificarFolio.FOLIO_FECHA_APERTURA );
String local_Folio_Radicacion    = (String)session.getAttribute( AWModificarFolio.FOLIO_NUM_RADICACION );

    // Bug: 3552 ------------------------------------------------------------------------------------------------------
String t0Complementacion;
    String t2Complementacion; // delta

    t0Complementacion = (String)session.getAttribute( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0 );
    t2Complementacion = (String)session.getAttribute( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2 );

    // -----------------------------------------------------------------------------------------------------------------------


    // Colocar informacion en la sesion sobre la
    // ubicacion geografica, para que sea colectada
    // por los helpers // TODO: pasar a accion web

//-------------------------------------------INICIALIZANDO LAS VARIABLES PAGINADOR-----------------------
    Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);



    /*inicializacion del helper del paginador
    */
    /*
	//inicializacion Vista generadora
    request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, "editar.folio.correccion.view");

    PaginadorAvanzadoHelper pag= new PaginadorAvanzadoHelper();
    //iniciando sus properties
    pag.setId("PAGINADOR_AVANZADO_CORRECCION");
    pag.setNombreAccion(WebKeys.ACCION);
    pag.setTipoAccion(AWPaginadorAnotaciones.REFRESCAR_PAGINADOR);
    pag.setUrlAccionWeb("paginadorAnotaciones.do");
    pag.setVariablePagina(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL);
    pag.setNombreForm("CORRECCION_PAGINADOR");
    Boolean OConsulta= new Boolean(false);
	request.getSession().setAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA, OConsulta);
	request.getSession().setAttribute(AWPaginadorAnotaciones.NOMBRE_NUM_PAGINA_ACTUAL, "NUM_PAG_ACTUAL_CORRECCIONES");
    //settear el nombre del paginador para q vaya al paginador indicado por el folio
	request.getSession().setAttribute(WebKeys.NOMBRE_PAGINADOR, "PAGINADOR_AVANZADO_CORRECCION");
    */

	boolean carga=true;

	if( null != session.getAttribute( "LOAD_LISTS" ) )
		carga = false;

/*
    List anotacionesDefinitivas=new LinkedList();
    List anotacionesTemporales=new LinkedList();

//paginador anterior

    DatosRespuestaPaginador RPag=(DatosRespuestaPaginador) request.getSession().getAttribute("RESULTADOS");
	if(RPag!=null){
		anotacionesDefinitivas=RPag.getAnotacionesActual();
//		anotacionesTemporales=RPag.getTemporales();
		carga=false;
	}
*/
	%>

	<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
	<%
  // Manejo de permisos de modificacion sobre la forma
	gov.sir.core.web.helpers.correccion.region.model.RegionManager pageLocalRegionManager = null;

  // recuperar valores de regionManager de la sesion
	pageLocalRegionManager = (gov.sir.core.web.helpers.correccion.region.model.RegionManager)session.getAttribute( "param_RegionManager" );

	%>
	<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
	<%

  // Manejo de flags de despliegue
	String PARAM_NAME__ANOTACIONES_WND_ICON_STATE = "PARAM_NAME__ANOTACIONES_WND_ICON_STATE";
	String param_AnotacionesWndIconState = request.getParameter( "PARAM_NAME__ANOTACIONES_WND_ICON_STATE" );

	if( null == param_AnotacionesWndIconState ) {
    // load defalut state
    param_AnotacionesWndIconState = "SHOW_MINIMIZED";
}

  // no se verifica el valor real de "param_AnotacionesWndIconState"
  // se asume que se maneja sobre la vista
session.setAttribute( PARAM_NAME__ANOTACIONES_WND_ICON_STATE, param_AnotacionesWndIconState );

%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%
  // usado solamente para mostrar las anotaciones
gov.sir.core.web.helpers.comun.MostrarFolioHelper mFolio
= new gov.sir.core.web.helpers.comun.MostrarFolioHelper();


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- Bug 03541 --%>
<%

  // Usado para observar si cada campo tiene o no variacion
  // :: en el caso del folio
java.util.Map local_FwdDiffComparisonResultsMap = null;

local_FwdDiffComparisonResultsMap = (java.util.Map)session.getAttribute( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_FORWARDDIFFCOMPARISONRESULTS );

%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>








<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
	function cambiarCombo(text) {
		try{
			document.getElementById('<%=CDocumento.TIPO_DOCUMENTO%>').options[text].selected=true
		}catch(e){
			document.getElementById('<%=CDocumento.TIPO_DOCUMENTO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
			document.getElementById('<%=CDocumento.ID_TIPO_DOCUMENTO%>').value='';
		}
	}
	function cambiarAccionCorreccion(text) {
		document.CORRECCION.POSSCROLL.value =(document.body ? document.body.scrollTop :0);
		document.CORRECCION.ACCION.value = text;
		document.CORRECCION.submit();
	}

	function quitar(pos,accion){
		if(confirm("Esta seguro que desea eliminar este item ?")){
			document.CORRECCION.POSICION.value = pos;
			cambiarAccionCorreccion(accion);
		}
	}

	function editarDir(pos,accion){
		document.CORRECCION.POSICION.value = pos;
		cambiarAccionCorreccion(accion);
	}

// enviar el id de accion eliminacion de direccion definitiva
function jsFolioDireccionDelItemFromDefinitivas( pos,accion ){
	if( confirm("Esta seguro que desea eliminar este item ?") ){
		document.CORRECCION.POSICION.value = pos;
		cambiarAccionCorreccion( accion );
	}
}

function jsFolioDireccionEditItemFromDefinitivas( pos,accion ){
	document.CORRECCION.POSICION.value = pos;
	cambiarAccionCorreccion(accion);
}


function cargarAnotacion(pos,accion) {
	document.CORRECCION.POSICION.value = pos;
	cambiarAccionCorreccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.CORRECCION.ACCION.value='<%=AWModificarFolio.PRESERVAR_INFO%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_C?digo_Notaria_NC
         */
         document.getElementById('version').value=valor+"_OFICINA_VERSION";
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
     	document.CORRECCION.POSICION.value=pos;
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

     function cargarListas(){
     	document.PAGINADOR.ACCION.value="<%=AWPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO%>";
     	document.PAGINADOR.<%=WebKeys.INICIO%>.value="0";
     	document.PAGINADOR.<%=WebKeys.CANTIDAD%>.value="<%=AWPaginadorAnotaciones.TAMANO_PAGINA_CACHE%>";
     	document.PAGINADOR.<%=CFolio.ID_MATRICULA%>.value="<%=folio.getIdMatricula()%>";
     	document.PAGINADOR.<%=CFolio.ID_ZONA_REGISTRAL%>.value="<%=folio!=null&&folio.getZonaRegistral()!=null?folio.getZonaRegistral().getIdZonaRegistral():""%>";
     	document.PAGINADOR.<%=WebKeys.NOMBRE_RESULTADOS_PAGINADOR%>.value="RESULTADOS";
     	document.PAGINADOR.<%=WebKeys.NOMBRE_PAGINADOR%>.value="PAGINADOR_AVANZADO_CORRECCION";
     	document.PAGINADOR.<%=AWPaginadorAnotaciones.PAGINA_INICIAL %>.value="0";

     	document.PAGINADOR.submit();
     }

 </script>

 <script type="text/javascript">

 	var jsLocal_Folio_TipoPredio = "<%= folio.getTipoPredio().getIdPredio() %>";


 </script>


 <script type="text/javascript">
     
 	function verAnotacion(nombre,valor,dimensiones,pos)
 	{
 		document.CREAR.POSICION.value=pos;
 		popup=window.open(nombre,valor,dimensiones);
 		popup.focus();
 	}

 	function locacionTipoPredio( nombre, valor, dimensiones ) {
 		return locacionTipoPredio_Impl2( nombre, valor, dimensiones );
 	}

 	function locacionTipoPredio_Impl1(nombre,valor,dimensiones){
 		document.getElementById('id_depto').value=valor+"_ID_DEPTO";
 		document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
 		document.getElementById('id_munic').value=valor+"_ID_MUNIC";
 		document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
 		document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
 		document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";


 		if( jsLocal_Folio_TipoPredio == '<%=gov.sir.core.negocio.modelo.constantes.CTipoPredio.TIPO_URBANO%>' ) {
 			popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.NO_MOSTRAR_VEREDA%>',valor,dimensiones);
 		}
 		else {
 			popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
 		}
        /*
        <%--
	if(document.CORRECCION.<%=CFolio.FOLIO_TIPO_PREDIO%>.value == '<%=gov.sir.core.negocio.modelo.constantes.CTipoPredio.TIPO_URBANO%>'){
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.NO_MOSTRAR_VEREDA%>',valor,dimensiones);
	} else {
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
	}
        --%>
        */




        popup.focus();
    }

    function locacionTipoPredio_Impl2(nombre,valor,dimensiones){

    	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
    	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
    	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
    	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
    	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
    	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    	popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
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
</script>




<%-- Bug 3563 --%>


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

   	eval( "formObject."+ elementName + ".value" + "=" + "elementValue" );
   }

</script>
<script type="text/javascript">
	// local form dependant resources

	var actionField = "<%=WebKeys.ACCION%>";

        // ----------------------------------------------------------------
        var PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION = "<%= AWModificarFolio.PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION %>";
        var PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION     = "<%= AWModificarFolio.PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION     %>";
        var PAGE_REGION__STARTANOTACIONENGLOBE_ACTION         = "<%= AWModificarFolio.PAGE_REGION__STARTANOTACIONENGLOBE_ACTION         %>";

        // ----------------------------------------------------------------
        // bug 3562
        var PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION = "<%= AWModificarFolio.PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION %>";

    </script>

    <script type="text/javascript">

    	function js_OnEvent( formName, actionValue ) {

    		var htmlForm;
    		htmlForm = new LocalForm( formName );
    		htmlForm.setValue( actionField, actionValue );
    		htmlForm.setValue( 'POSSCROLL', (document.body ? document.body.scrollTop :0));
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





    <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
    <%

    Boolean local_ExceptionEnabled;
    String  local_ExceptionMessage;

    %>

    <%

    if( null != ( local_ExceptionMessage = (String)session.getAttribute( "LOCAL_EXCEPTION_ENABLED" ) ) ) {

    %>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
    	<tr>
    		<td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:4px;padding-right:20px;padding-bottom:0px;" >

    			<%--SOF:REGION --%>

    			<table width="100%" class="camposform" style="border:0px;">
    				<tr>
    					<td>

    						<%-- SOF:MENU--%>


    						<table>
    							<tr>
    								<td width="16">
        <%--
        <img src="<%=request.getContextPath()%>/jsp/images/bullet_ok.gif" width="12" height="11" alt="[err]"/>
        --%>
        <image src="<%=request.getContextPath()%>/jsp/images/error_animated.gif" />
        	<!--<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/plugins.js"></script>-->
          <!--<script
            language="javascript"
            type="text/javascript"
          >
            var Imagen="<%=request.getContextPath()%>/jsp/images/error_animated.gif"
            var pelicula="<%=request.getContextPath()%>/jsp/images/error_animated.swf"
            var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
            var ancho="70"
            var alto="70"
            plugindetectado();
        </script>-->
    </td>
    <td>
    	<span
    	class="camposform"
    	style="border:0px"
    	>
    	<%= local_ExceptionMessage %>
    </span>
</td>
</tr>
</table>


<%-- EOF:MENU--%>


</td>
<td width="15">&nbsp;
</td>
</tr>
</table>

<%--EOF:REGION --%>

</td>
</tr>
</table>

<br />

<%
session.removeAttribute( "LOCAL_EXCEPTION_ENABLED" );

} // if
%>




<%

if( null != session.getAttribute( WebKeys.HAY_EXCEPCION ) ) {
Boolean local_RecargaEnabled;
  //if( ( null != ( local_RecargaEnabled = (Boolean)session.getAttribute( WebKeys.RECARGA ) ) )
  //  &&( Boolean.TRUE.equals( local_RecargaEnabled ) )
  //)
  {

    // "org.auriga.smart.OrigenExcepcion"
    // "org.auriga.smart.MensajeExcepcion"
    Object local_Exception;
    if( null != ( local_Exception = session.getAttribute( "org.auriga.smart.MensajeExcepcion" ) ) ) {
    local_ExceptionMessage = "Error:" + local_Exception.toString();
}

session.setAttribute( "LOCAL_EXCEPTION_ENABLED", local_ExceptionMessage );
session.removeAttribute(  WebKeys.HAY_EXCEPCION  );
}

} // if
%>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>








<table width="100%" border="0" cellpadding="0" cellspacing="0">

	<form action="paginadorAnotaciones.do" method="post" type="submit" name="PAGINADOR" id="PAGINADOR" >
		<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="">
		<input type="hidden" name="<%=WebKeys.INICIO%>" id="<%=WebKeys.INICIO%>" value="">
		<input type="hidden" name="<%=WebKeys.CANTIDAD%>" id="<%=WebKeys.CANTIDAD%>" value="">
		<input type="hidden" name="<%=AWPaginadorAnotaciones.PAGINA_INICIAL%>" id="<%=AWPaginadorAnotaciones.PAGINA_INICIAL%>" value="">
		<input type="hidden" name="<%=CFolio.ID_MATRICULA%>" id="<%=CFolio.ID_MATRICULA%>" value="">
		<input type="hidden" name="<%=CFolio.ID_ZONA_REGISTRAL%>" id="<%=CFolio.ID_ZONA_REGISTRAL%>" value="">
		<input type="hidden" name="<%=WebKeys.NOMBRE_PAGINADOR%>" id="<%=WebKeys.NOMBRE_PAGINADOR%>" value="">
		<input type="hidden" name="<%=WebKeys.NOMBRE_RESULTADOS_PAGINADOR%>" id="<%=WebKeys.NOMBRE_RESULTADOS_PAGINADOR%>" value="">
	</form>


	<%
	if( carga ) {
	%>

	<script type="text/javascript">
		cargarListas();
	</script>

	<%

	session.setAttribute( "LOAD_LISTS", new Boolean( true ) );

}
%>

<tr>
	<td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_esquina001.gif" width="12" height="23" border="0" alt=""></td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">

	</td>
	<td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>

</tr>





<tr>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
	<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">


			<form action="modificacion.do" name="CORRECCION" id="CORRECCION" method="post">
				<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="SIN_SELECCIONAR">
				<input type="hidden" name="<%=AWModificarFolio.AGREGAR_CIUDADANO%>" value="<%=AWModificarFolio.AGREGAR_CIUDADANO_CORRECCION%>">
				<input type="hidden" name="<%=AWModificarFolio.ELIMINAR_CIUDADANO%>" value="<%=AWModificarFolio.ELIMINAR_CIUDADANO_CORRECCION%>">
				<input type="hidden" name="<%=AWModificarFolio.AGREGAR_ANOTACION%>" value="<%=AWModificarFolio.AGREGAR_ANOTACION_CORRECCION%>">
				<input type="hidden" name="<%=AWModificarFolio.ELIMINAR_ANOTACION%>" value="<%=AWModificarFolio.ELIMINAR_ANOTACION_CORRECCION%>">
				<input type="hidden" name="test" id="test" value="test">
				<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="">
				<input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" >

				<input name="Depto" type="hidden" id="id_depto" value="">
				<input name="Depto" type="hidden" id="nom_Depto" value="">
				<input name="Mpio" type="hidden" id="id_munic" value="">
				<input name="Mpio" type="hidden" id="nom_munic" value="">
				<input name="Ver" type="hidden" id="id_vereda" value="">
				<input name="Ver" type="hidden" id="nom_vereda" value="">

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
							<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio</td>
							<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
							<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
										<td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
							<table width="100%" class="camposform">
								<tr>
									<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15"></td>
									<td width="20" class="campositem">N&ordm;</td>
									<td class="campositem"><%=idMatricula!=null?idMatricula:""%>&nbsp;</td>
								</tr>
							</table>



							<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
								<tr>
									<td width="7%">&nbsp;</td>
									<%
									java.math.BigDecimal totalAnotaciones = (java.math.BigDecimal) request.getSession().getAttribute(WebKeys.TOTAL_ANOTACIONES);

									List gravamenes = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);
									List medCautelares = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES);
									List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
									List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);

									List anotacionesPatrimonioFamiliar = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR);	
									List anotacionesAfectacionVivienda = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA);

									boolean gravamen = false;
									boolean medidas = false;
									boolean patrimonioFamiliar = false;
									boolean afectacionVivienda = false;
									if(gravamenes!=null && gravamenes.size()>0){
									gravamen = true;
								}

								if(medCautelares!=null && medCautelares.size()>0){
								medidas = true;
							}

							if(anotacionesPatrimonioFamiliar!=null && anotacionesPatrimonioFamiliar.size()>0){
							patrimonioFamiliar = true;
						}

						if(anotacionesAfectacionVivienda!=null && anotacionesAfectacionVivienda.size()>0){
						afectacionVivienda = true;
					}
					%>
					<td class="titresaltados" width="43%">&nbsp;El folio tiene&nbsp;<%=(totalAnotaciones!=null?totalAnotaciones.toString():"")%>&nbsp;<%=(totalAnotaciones!=null&&(totalAnotaciones.longValue()!=1)?"Anotaciones":"Anotaci&oacute;n")%></td>
					<td class="titresaltados" width="43%"><%if(gravamen || medidas || patrimonioFamiliar || afectacionVivienda){%><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/ani_folios.gif" width="24" height="16" border="0" alt=""><%}%>&nbsp;&nbsp;&nbsp;&nbsp;<%=(gravamen || medidas || patrimonioFamiliar || afectacionVivienda)?"El folio tiene : "+(gravamen?" -Grav&aacute;menes":"") + (medidas?" -Medidas Cautelares":"") + (patrimonioFamiliar?" -Patrimonio Familiar":"") + (afectacionVivienda?" -Afectaci?n de Vivienda Familiar":""):""%></td>
					<td width="7%">&nbsp;</td>
				</tr>


				<%
				String textoFalsaTradicion = "  ";
				if(falsaTradicion!=null){
				Iterator it = falsaTradicion.iterator();
				while(it.hasNext()){
				String ordenAnotacion = (String)it.next();
				textoFalsaTradicion = textoFalsaTradicion + ordenAnotacion + ", ";
			}
		}
		textoFalsaTradicion = textoFalsaTradicion.substring(0,(textoFalsaTradicion.length()-2));

		String textoInvalidas = "  ";
		if(anotacionesInvalidas!=null){
		Iterator it = anotacionesInvalidas.iterator();
		while(it.hasNext()){
		String ordenAnotacion = (String)it.next();
		textoInvalidas = textoInvalidas + ordenAnotacion + ", ";
	}
}
textoInvalidas = textoInvalidas.substring(0,(textoInvalidas.length()-2));
%>
<%
if(textoFalsaTradicion.length()>0 || textoInvalidas.length()>0){
%>
<tr>
	<td width="7%">&nbsp;</td>
	<%
	if(textoFalsaTradicion.length()>0 && textoInvalidas.length()>0){
	%>
	<td class="titresaltados" width="43%">&nbsp;Las siguientes anotaciones son de falsa tradici?n:&nbsp;<%=textoFalsaTradicion%>.</td>
	<td class="titresaltados" width="43%">&nbsp;Las siguientes anotaciones son inv?lidas:&nbsp;<%=textoInvalidas%>.</td>
	<%
}else{
if(textoFalsaTradicion.length()>0){
%>
<td class="titresaltados" width="43%">&nbsp;Las siguientes anotaciones son de falsa tradici?n:&nbsp;<%=textoFalsaTradicion%>.</td>
<%	}
if(textoInvalidas.length()>0){
%>
<td class="titresaltados" width="43%">&nbsp;Las siguientes anotaciones son inv?lidas:&nbsp;<%=textoInvalidas%>.</td>
<%	}
}%>
<td width="7%">&nbsp;</td>
</tr>
<%
}
%>
<%if(numSegregacionesVacias>0) {%>
<tr>
	<td class="titresaltados" width="43%">&nbsp;El Folio puede tener segregaciones pendientes:&nbsp;.</td>
</tr>
<%} %>







</table>

<br />

<span class="bgnsub" style="background:" >Datos B&aacute;sicos</span>
<%--
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Datos B&aacute;sicos </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>
--%>
<%--
              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                      <td>Ubicaci&oacute;n</td>
                  </tr>
                  <tr>
                      <td>&nbsp;</td>
                      <td>
                      	--%>
                      	<!-- TAG: BEGIN-REGION -->
                      	<!-- + + + + + + + + + + + + + + + + + + + + -->

                      	<xRegionTemplate:DisplayPoint
                      	regionId="htRgn_UbicacionGeografica_Main"
                      	regionName="ubicacion geografica"
                      	regionDescription="Contiene los datos de ubicacion geofrafica de la radicacion del folio."
                      	debugEnabled="false"
                      	displayExtraMessage="null,true,false"
                      	regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                      	>	<!--   -->


                      	<!-- sof:block "edicion de ubiacion geografica" -->

                      	<%

    // -----------------------------------
    // por el momento, independientemente de las variaciones presentadas
    // en la zona registral, no se estan enviando estos valores a negocio
    // ni a servicios porque no esta el servicio realizado.
    // Hay que decidir si el id-zona-registral (dentro de zona registral)
    // se maneja en la capa de servicios, porque no hay forma de obtenerlo
    // aqui, con los datos de municipio, depto y circulo.
    // -----------------------------------

                      	%>


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
                      							<td class="campositem"><%=( ( null!=circulo ) && ( null != circulo.getNombre() )?( circulo.getNombre() ):( "" ) )%>&nbsp;</td>
                      						</tr>
                      					</table>
                      					<table width="100%" class="camposform">
                      						<tr>
                      							<td>Departamento</td>
                      							<td><!--<table border="0" align="center" cellpadding="0" cellspacing="0">-->
                      								<!--<tr>-->
                      									<td>ID:

                      										<%

                                  // departamento.id

                      										try {
                      										textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_DEPTO);
                      										textHelper.setId(CFolio.FOLIO_LOCACION_ID_DEPTO);

                      										textHelper.setSize("3");
                      										textHelper.setCssClase("camposformtext");

                                    // TODO: comment when servicelayer is enabled for this change
                                    //textHelper.setCssClase( "campositem" );

                                    textHelper.render(request, out);
                                }
                                catch (HelperException re) {
                                out.println("ERROR " + re.getMessage());
                            }


                                  // departamento nombre

                            try {

                            textHelper.setSize("");
                            textHelper.setId(CFolio.FOLIO_LOCACION_NOM_DEPTO);
                            textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_DEPTO);

                            textHelper.setCssClase("camposformtext");

                                      // TODO: comment when servicelayer is enabled for this change
                                    //textHelper.setCssClase( "campositem" );

                                    textHelper.render(request, out);
                                }
                                catch (HelperException re) {
                                out.println("ERROR " + re.getMessage());
                            }

                            %>



                            <%-- [bug-03541]: sof@changes-mark --%>
                            <xRegionTemplate:DeltaPoint
                            fieldId="field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento"
                            fieldName="Folio - ZonaRegistral - Depto "
                            fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                            showChangesEnabled="disabled"
                            />
                            <%-- eof@changes-mark --%>




                                  <%--

                                    // TODO: uncomment when servicelayer is enabled for this change

                                    --%>

                                    <td width="16">
                                    	<a
                                    	href="javascript:locacionTipoPredio('seleccionar.locacion.do?<%=AWLocacion.LOCACIONES_CIRCULO%>=<%=AWLocacion.LOCACIONES_CIRCULO%>','FOLIO_LOCACION','width=790,height=320,menubar=no');"><img
                                    	src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif"
                                    	alt="Permite seleccionar departamento, municipio, vereda"
                                    	width="21" height="26" border="0" />
                                    </a>
                                </td>
                                
                                
                            </tr>





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

                // TODO: comment when servicelayer is enabled for this change
                //textHelper.setCssClase( "campositem" );

                textHelper.render(request, out);
            } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }
        try {
        textHelper.setSize("");
        textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        textHelper.setCssClase("camposformtext");
        textHelper.setId(CFolio.FOLIO_LOCACION_NOM_MUNIC);

                // TODO: comment when servicelayer is enabled for this change
                //textHelper.setCssClase( "campositem" );


                textHelper.render(request, out);
            } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        %>


        <%-- [bug-03541]: sof@changes-mark --%>
        <xRegionTemplate:DeltaPoint
        fieldId="field:folio:zonaRegistral/vereda/municipio/idMunicipio"
        fieldName="Folio - ZonaRegistral - Mcpio "
        fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
        showChangesEnabled="disabled"
        />
        <%-- eof@changes-mark --%>







    </td>
    <td>Vereda</td>
    <td>ID: <%try {
    	textHelper.setSize("3");
    	textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_VEREDA);
    	textHelper.setCssClase("camposformtext");
    	textHelper.setId(CFolio.FOLIO_LOCACION_ID_VEREDA);

                // TODO: comment when servicelayer is enabled for this change
                //textHelper.setCssClase( "campositem" );

                textHelper.render(request, out);
            } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }
        try {
        textHelper.setSize("");
        textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        textHelper.setCssClase("camposformtext");
        textHelper.setId(CFolio.FOLIO_LOCACION_NOM_VEREDA);

                // TODO: comment when servicelayer is enabled for this change
                //textHelper.setCssClase( "campositem" );

                textHelper.render(request, out);
            } catch (HelperException re) {
            out.println("ERROR " + re.getMessage());
        }

        %>

        <%-- [bug-03541]: sof@changes-mark --%>
        <xRegionTemplate:DeltaPoint
        fieldId="field:folio:zonaRegistral/vereda/idVereda"
        fieldName="Folio - ZonaRegistral - McpioVereda "
        fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
        showChangesEnabled="disabled"
        />
        <%-- eof@changes-mark --%>




    </td>
</tr>
</table>
</td>
</tr>
</table>

<%

    // TODO: comment when servicelayer is enabled for this change
    // textHelper.setCssClase( "campositem" );


%>


<!-- eof:block -->


























<%--

// Comment the previous implementation of ubicacion geografica editor

                      	  <table width="100%" class="camposform">
                          <tr>
                              <td width="87">Circulo</td>
                              <td class="campositem"><%=(circulo!=null && circulo.getNombre()!=null)?circulo.getNombre():""%>&nbsp;</td>
                          </tr>
                      	  </table>


   	                      <table width="100%" class="camposform">

	                      <tr>
	                      <td width="85">Departamento</td>
	                      <td>
	                          <%
 						    try {
								hiddenHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
								hiddenHelper.setCssClase("camposformtext");
								hiddenHelper.setId(CDepartamento.ID_DEPARTAMENTO);
								hiddenHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}

							try {
								tdHelper.setCssClase("campositem");
								tdHelper.setId(CDepartamento.NOMBRE_DEPARTAMENTO);
								tdHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							 %>
						  </td>

	                      <td width="85">Municipio</td>
	                      <td>
	                        <%
							try {
								hiddenHelper.setNombre(CMunicipio.ID_MUNICIPIO);
								hiddenHelper.setCssClase("camposformtext");
								hiddenHelper.setId(CMunicipio.ID_MUNICIPIO);
								hiddenHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							try {
								tdHelper.setCssClase("campositem");
								tdHelper.setId(CMunicipio.NOMBRE_MUNICIPIO);
								tdHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
							</td>

	                        <td width="85">Vereda</td>
	                        <td>
	                        	<%
								try {
									hiddenHelper.setNombre(CVereda.ID_VEREDA);
									hiddenHelper.setCssClase("camposformtext");
									hiddenHelper.setId(CVereda.ID_VEREDA);
									hiddenHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}

								try {
									tdHelper.setCssClase("campositem");
									tdHelper.setId(CVereda.NOMBRE_VEREDA);
									tdHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
								%>
	                          </td>
	                      </tr>
	                  </table>


	                  --%>

	                  <!-- TAG: END-REGION -->
	                  <!-- + + + + + + + + + + + + + + + + + + + + -->
	              </xRegionTemplate:DisplayPoint>
	              <!-- + + + + + + + + + + + + + + + + + + + + -->
	              <!-- END-TAG -->


<%--

                  </td>
                  </tr>
              </table>

              --%>
              <hr />
              <!--Encabezado del documento-->
              <span class="bgnsub" style="background:" >Documento</span>
<%--
			    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
				    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
				    <td class="bgnsub">Documento</td>
				    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
				    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			    </tr>
				</table>
				--%>


				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td>Datos B&aacute;sicos </td>
					</tr>

					<!-- TAG: BEGIN-REGION -->
					<!-- + + + + + + + + + + + + + + + + + + + + -->

					<xRegionTemplate:DisplayPoint
					regionId="htRgn_DatosDeAperturaMain"
					regionName="datos apertura"
					regionDescription="Contiene el conjunto de datos en el documento que se inscribieron al realizar la apertura del folio."
					debugEnabled="false"
					displayExtraMessage="null,true,false"
					regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
					>	<!--   -->

					<!-- + + + + + + + + + + + + + + + + + + + + -->
					<!-- END-TAG -->

					<tr>
						<td>&nbsp;</td>
						<td>

							<table width="100%" class="camposform">
								<tr>




									<td>Tipo</td>
									<td>

										<%
										try {
										textHelper.setNombre(CDocumento.ID_TIPO_DOCUMENTO);
										textHelper.setId(CDocumento.ID_TIPO_DOCUMENTO);
										textHelper.setCssClase("camposformtext");
										textHelper.setFuncion("onchange='javascript:cambiarCombo(this.value)'");
										textHelper.render(request,out);
									}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
								textHelper.setFuncion("");
								%>

							</td>
							<td>
								<%
								ListaElementoHelper tiposDocHelper =  new ListaElementoHelper();
								List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
								tiposDocHelper.setOrdenar(false);		
								tiposDocHelper.setTipos(tiposDocs);

								try {
								tiposDocHelper.setId(CDocumento.TIPO_DOCUMENTO);
								tiposDocHelper.setCssClase("camposformtext");
								tiposDocHelper.setNombre(CDocumento.TIPO_DOCUMENTO);
								tiposDocHelper.setFuncion("onchange=getElementById('"+CDocumento.ID_TIPO_DOCUMENTO+"').value=this.value;");
								tiposDocHelper.render(request,out);
							}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						tiposDocHelper.setFuncion("");
						%>



						<%-- [bug-03541]: sof@changes-mark --%>
						<xRegionTemplate:DeltaPoint
						fieldId="field:folio:documento/tipoDocumento/idTipoDocumento"
						fieldName="Folio - Documento / Tipo "
						fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
						showChangesEnabled="disabled"
						/>
						<%-- eof@changes-mark --%>




					</td>
					<td>N&uacute;mero</td>
					<td>
						<%
						try {
						textHelper.setNombre(CDocumento.NUM_DOCUMENTO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CDocumento.NUM_DOCUMENTO);
						textHelper.render(request,out);
					}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
				%>


				<%-- [bug-03541]: sof@changes-mark --%>
				<xRegionTemplate:DeltaPoint
				fieldId="field:folio:documento/numero"
				fieldName="Folio - Documento / Num "
				fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
				showChangesEnabled="disabled"
				/>
				<%-- eof@changes-mark --%>




			</td>
			<td>Fecha</td>
			<td><table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<%
						try {
						textHelper.setNombre(CDocumento.FECHA_RADICACION);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CDocumento.FECHA_RADICACION);
						textHelper.render(request,out);
					}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
				%>
				<td><a href="javascript:NewCal('<%=CDocumento.FECHA_RADICACION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>





					<%-- [bug-03541]: sof@changes-mark --%>
					<xRegionTemplate:DeltaPoint
					fieldId="field:folio:documento/numero"
					fieldName="Folio - Documento / Fecha "
					fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
					showChangesEnabled="disabled"
					/>
					<%-- eof@changes-mark --%>







				</tr>
			</table>
		</td>


	</tr>
</table></td>
</tr>


<tr>

	<td>
		<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" alt="[]"/>
	</td>
	<td>Oficina de Procedencia &nbsp;&nbsp;


		<%-- [bug-03541]: sof@changes-mark --%>
		<xRegionTemplate:DeltaPoint
		fieldId="field:folio:documento/oficinaOrigen/idOficinaOrigen"
		fieldName="Oficina Origen - id "
		fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
		showChangesEnabled="disabled"
		/>


		<%-- [bug-03541]: sof@changes-mark --%>
		<xRegionTemplate:DeltaPoint
		fieldId="field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento"
		fieldName="Folio -OficinaOrigen - Depto "
		fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
		showChangesEnabled="disabled"
		/>
		<%-- eof@changes-mark --%>

		<%-- [bug-03541]: sof@changes-mark --%>
		<xRegionTemplate:DeltaPoint
		fieldId="field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio"
		fieldName="Folio - OficinaOrigen - Mcpio "
		fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
		showChangesEnabled="disabled"
		/>
		<%-- eof@changes-mark --%>

		<%-- [bug-03541]: sof@changes-mark --%>
		<xRegionTemplate:DeltaPoint
		fieldId="field:folio:documento/oficinaOrigen/vereda/idVereda"
		fieldName="Folio - OficinaOrigen - McpioVereda "
		fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
		showChangesEnabled="disabled"
		/>
		<%-- eof@changes-mark --%>



	</td>
</tr>




<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
<%

gov.sir.core.web.helpers.registro.OficinaHelper oficinaHelper
= new gov.sir.core.web.helpers.registro.OficinaHelper ();

try {
oficinaHelper.render(request,out);
}
catch(HelperException re){
out.println("ERROR " + re.getMessage());
}

%>




<%-- eof@changes-mark --%>


</td>
</tr>


<!-- # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # -->

<%-- bug:3580 --%>

<tr>
	<td>
		<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" />
	</td>
	<td>Radicacion y Fecha</td>
</tr>

<tr>
	<td>&nbsp;</td>
	<td>

		<table width="100%" class="camposform" border="0">
			<tr>

				<!-- sof-field:tx -->

				<td width="100"><div align="right"><span>Num Radicacion:</span></div></td>
				<td width="180">


					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td>
								<%

                                    // :: folio.radicacion

								try {
								textHelper.setId(     AWModificarFolio.FOLIO_NUM_RADICACION );
								textHelper.setNombre( AWModificarFolio.FOLIO_NUM_RADICACION );
								textHelper.setCssClase( "camposformtext" );
								textHelper.render( request, out );
							}
							catch( HelperException re ){
							out.println("ERROR " + re.getMessage());
						}

						%>

						<%-- [bug-03541]: sof@changes-mark --%>
						<xRegionTemplate:DeltaPoint
						fieldId="field:folio:radicacion"
						fieldName="Numero Radicacion"
						fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
						showChangesEnabled="enabled"
						/>
						<%-- eof@changes-mark --%>

					</td>

				</tr>
			</table>

		</td>
		<!-- eof-field:tx -->

		<!-- sof-field:date -->

		<td width="100"><div align="right"><span>Fecha Apertura:</span></div></td>
		<td width="180">


			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<%

                                    // :: folio.fechaApertura

						try {
						textHelper.setId(     AWModificarFolio.FOLIO_FECHA_APERTURA );
						textHelper.setNombre( AWModificarFolio.FOLIO_FECHA_APERTURA );
						textHelper.setCssClase( "camposformtext" );
						textHelper.render( request,out );
					}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}

				%>

				<%-- [bug-03541]: sof@changes-mark --%>
				<xRegionTemplate:DeltaPoint
				fieldId="field:folio:fechaApertura"
				fieldName="Fecha Apertura"
				fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
				showChangesEnabled="enabled"
				/>
				<%-- eof@changes-mark --%>



			</td>
			<td><a href="javascript:NewCal('<%= AWModificarFolio.FOLIO_FECHA_APERTURA %>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')" /></a>
			</tr>
		</table>

	</td>
	<!-- eof-field:date -->
	<td >&nbsp;</td>

</tr>
</table>


</td>
</tr>

<!-- # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # -->

</table>


<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


<br />
<span class="bgnsub" style="background:" >Datos Folio</span>

<%--
			    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
				    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
				    <td class="bgnsub">Datos Folio</td>
				    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
				    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			    </tr>
				</table>

				--%>


				<table width="100%" class="camposform" >
					<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td>Datos Folio</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>

							<table width="100%" class="camposform" bgcolor="yellow" border="0">
								<%-- border="2" --%>


								<%-- tipo de predo y estado de folio se mueven a la parte superior --%>
<%-- Ello debido a que no permiten mostrar la nota explicativa de la
     region, si existe un select, incluso si la propiedad z-index esta
     fija. --%>


     <tr>
     	<td>Tipo de Predio </td>
     	<td colspan="2">


     		<!-- TAG: BEGIN-REGION -->
     		<!-- + + + + + + + + + + + + + + + + + + + + -->

     		<xRegionTemplate:DisplayPoint
     		regionId="htRgn_FolioTipoPredio"
     		regionName="tipo de predio"
     		regionDescription="Contiene el tipo de predio que relaciona el folio."
     		debugEnabled="false"
     		displayExtraMessage="null,true,false"
     		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
     		>	<!--   -->

     		<!-- + + + + + + + + + + + + + + + + + + + + -->
     		<!-- END-TAG -->
     		<%

     		try {
     		tipoPredioHelper.setNombre(AWModificarFolio.FOLIO_TIPO_PREDIO);
     		tipoPredioHelper.setId(AWModificarFolio.FOLIO_TIPO_PREDIO);
     		tipoPredioHelper.render(request,out);
     	}
     	catch( HelperException re ){
     	out.println("ERROR " + re.getMessage());
     }

     %>


     <%-- [bug-03541]: sof@changes-mark --%>
     <xRegionTemplate:DeltaPoint
     fieldId="field:folio:tipoPredio/idPredio"
     fieldName="Folio - Tipo Predio"
     fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
     showChangesEnabled="enabled"
     />
     <%-- eof@changes-mark --%>


     <!-- TAG: END-REGION -->
     <!-- + + + + + + + + + + + + + + + + + + + + -->
 </xRegionTemplate:DisplayPoint>
 <!-- + + + + + + + + + + + + + + + + + + + + -->
 <!-- END-TAG -->

</td>
</tr>


<tr>
	<td >Estado folio</td>
	<td>

		<!-- TAG: BEGIN-REGION -->
		<!-- + + + + + + + + + + + + + + + + + + + + -->

		<xRegionTemplate:DisplayPoint
		regionId="htRgn_FolioEstado"
		regionName="estado de folio"
		regionDescription="Contiene el estado del folio"
		debugEnabled="false"
		displayExtraMessage="null,true,false"
		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
		>	<!--   -->

		<!-- + + + + + + + + + + + + + + + + + + + + -->
		<!-- END-TAG -->

		<%
                                        // folio.estado
		try {
		estadoFolioHelper.setNombre(AWModificarFolio.FOLIO_ESTADO_FOLIO);
		estadoFolioHelper.setId(AWModificarFolio.FOLIO_ESTADO_FOLIO);
		estadoFolioHelper.render(request,out);
	}
	catch(HelperException re){
	out.println("ERROR " + re.getMessage());
}

%>

<%-- [bug-03541]: sof@changes-mark --%>
<xRegionTemplate:DeltaPoint
fieldId="field:folio:estado/idEstado"
fieldName="Folio - Estado"
fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
showChangesEnabled="enabled"
/>
<%-- eof@changes-mark --%>


<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


</td>
<td width="200" nowrap="nowrap" rowspan="2" valign="top">Comentario (cambio de estado): <!-- 20 -->
                      <!--
                    </td>
                    <td width="150" colspan="2" align="left">
                    -->

                    <!-- TAG: BEGIN-REGION -->
                    <!-- + + + + + + + + + + + + + + + + + + + + -->

                    <xRegionTemplate:DisplayPoint
                    regionId="htRgn_FolioEstado_Comentario"
                    regionName="estado de folio: comentario"
                    regionDescription="Contiene el comentario realizado respecto al cambio en el estado del folio"
                    debugEnabled="false"
                    displayExtraMessage="null,true,false"
                    regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                    >	<!--   -->

                    <!-- + + + + + + + + + + + + + + + + + + + + -->
                    <!-- END-TAG -->

                    <%
                      // folio.estado.(cambio).comentario

                    try {

                    textAreaHelper.setNombre( AWModificarFolio.FOLIO_ESTADO_FOLIO_COMENTARIO );
                    textAreaHelper.setId(     AWModificarFolio.FOLIO_ESTADO_FOLIO_COMENTARIO );
                    textAreaHelper.setCols( "22" );
                    textAreaHelper.setRows( "2" );
                    textAreaHelper.setCssClase( "camposformtext" );
                    textAreaHelper.render( request, out );

                }
                catch( HelperException e ) {

                out.println( "ERROR:" + e.getMessage() );

            }

            %>


            <%-- [bug-03541]: sof@changes-mark --%>
            <xRegionTemplate:DeltaPoint
            fieldId="field:folio:estado/comentario"
            fieldName="Folio - Estado, Comentario "
            fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
            showChangesEnabled="enabled"
            />
            <%-- eof@changes-mark --%>


            <!-- TAG: END-REGION -->
            <!-- + + + + + + + + + + + + + + + + + + + + -->
        </xRegionTemplate:DisplayPoint>
        <!-- + + + + + + + + + + + + + + + + + + + + -->
        <!-- END-TAG -->

    </td>
    <td colspan="10">&nbsp;</td>
</tr>







<tr>
	<td width="80">C&oacute;digo Catastral</td>
	<td >

		<!-- TAG: BEGIN-REGION -->
		<!-- + + + + + + + + + + + + + + + + + + + + -->

		<xRegionTemplate:DisplayPoint
		regionId="htRgn_NumeroCatastralMain"
		regionName="numero catastral"
		regionDescription="Contiene el numero catastral relacionado con el folio."
		debugEnabled="false"
		displayExtraMessage="null,true,false"
		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
		>	<!--   -->

		<!-- + + + + + + + + + + + + + + + + + + + + -->
		<!-- END-TAG -->
		<% try {
		textHelper.setNombre(AWModificarFolio.FOLIO_COD_CATASTRAL);
		textHelper.setCssClase("camposformtext");
                                           /**
                                           * @author: David Panesso
                                           * @change: Caso 904.ACTUALIZACION.FICHAS.CATASTRALES, se agrega tama?o de 80 al campo para visualizar toda la informaci?n del c?digo catastral.
                                           **/
                                           textHelper.setSize("80");
                                           textHelper.setId(AWModificarFolio.FOLIO_COD_CATASTRAL);
                                           textHelper.render(request,out);
                                       }
                                       catch(HelperException re){
                                       out.println("ERROR " + re.getMessage());
                                   }
                                   %>

                                   <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:codCatastral"
                                   fieldName="Codigo Catastral"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>




                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->

                           </td>
                       </tr>




                       <tr>
                       	<td width="80">C&oacute;digo Catastral Anterior</td>
                       	<td >


                       		<!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_NumeroCatastralAnteriorMain"
                       		regionName="numero catastral"
                       		regionDescription="Contiene el numero catastral anterior relacionado con el folio."
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG -->
                       		<% try {
                       		textHelper.setNombre(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);
                       		textHelper.setCssClase("camposformtext");
                                           /**
                                           * @author: David Panesso
                                           * @change: Caso 904.ACTUALIZACION.FICHAS.CATASTRALES, se agrega tama?o de 80 al campo para visualizar toda la informaci?n del c?digo catastral.
                                           **/
                                           textHelper.setSize("80");
                                           textHelper.setId(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);
                                           textHelper.render(request,out);
                                       }
                                       catch(HelperException re){
                                       out.println("ERROR " + re.getMessage());
                                   }
                                   %>
                                   <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:codCatastralAnterior"
                                   fieldName="Codigo Catastral Anterior"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->

                           </td>
                       </tr>

<tr>
                            <td>NUPRE</td>
				<td>
                                 <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_NupreMain"
                       		regionName="NUPRE"
                       		regionDescription="Numero Predial Nacional"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG -->
                                
                                    <%
				try {
	                textHelper.setNombre(AWModificarFolio.FOLIO_NUPRE);
	                textHelper.setCssClase("camposformtext");
	                textHelper.setId(AWModificarFolio.FOLIO_NUPRE);
                        textHelper.setSize("30");
                        textHelper.setReadonly(false);
	                textHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
                    }
	%>
                    <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:nupre"
                                   fieldName="NUPRE"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->            
                                </td>
                        </tr>
                        
                        <tr>
                            <td nowrap>Determinaci&oacute;n del Inmueble</td>
				<td>
                        <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_DeterminaInmMain"
                       		regionName="Determinacion del Inmueble"
                       		regionDescription="Determinacion del Inmueble"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG -->            
                                    <%
				try {
                        
	                tipoDetermInmHelper.setNombre(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE);
	                tipoDetermInmHelper.setId(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE);
	                tipoDetermInmHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
                    }
	%>
                                  <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:determinaInm"
                                   fieldName="Determinacion de Inmueble"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->    
                                </td>
                        </tr>
                        
                        <tr>
                            <td nowrap>Area Privada</td>
                            <td colspan="4">
                                <table class="camposformnoborder">
                                <td align="right">Metros<sup>2</sup></td>
                                <td nowrap>
                                    <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_PrivMetrosMain"
                       		regionName="Area Privada: Metros"
                       		regionDescription="Area Privada"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG -->    
				<%
                         
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_PRIVMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_PRIVMETROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+AWModificarFolio.FOLIO_PRIVMETROS+"');\"  "
                                        + "onChange=\"valideDot('"+AWModificarFolio.FOLIO_PRIVMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+AWModificarFolio.FOLIO_PRIVMETROS+"','"+AWModificarFolio.FOLIO_PRIVCENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %>
            <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:privMetros"
                                   fieldName="Area Privada: Metros"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->    
                               
                                </td>
                           <td align="right">Centimetros<sup>2</sup></td>
                           <td>
                               <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_PrivCentimetrosMain"
                       		regionName="Area Privada: Centimetros"
                       		regionDescription="Area Privada"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG -->    
                             <%
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_PRIVCENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_PRIVCENTIMETROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
                                %>
                                <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:privCentimetros"
                                   fieldName="Area Privada: Centimetros"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->  
                                </td>
                                </table>
                                </td>
			</tr>
                        
                        <tr>
                            <td nowrap>Area Construida</td>
                            <td colspan="4">
                                <table class="camposformnoborder">
                                <td align="right">Metros<sup>2</sup></td>
                                <td nowrap>
                                    <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_ConsMetrosMain"
                       		regionName="Area Construida: Metros"
                       		regionDescription="Area Construida"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG --> 
				<%
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_CONSMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_CONSMETROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+AWModificarFolio.FOLIO_CONSMETROS+"');\" "
                                        + "onChange=\"valideDot('"+AWModificarFolio.FOLIO_CONSMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+AWModificarFolio.FOLIO_CONSMETROS+"','"+AWModificarFolio.FOLIO_CONSCENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
                                %>
                                 <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:consMetros"
                                   fieldName="Area Construida: Metros"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->
                                </td>
                    <td align="right">Centimetros<sup>2</sup></td>   
                    <td nowrap>
                        <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_ConsCentimetrosMain"
                       		regionName="Area Construida: Centimetros"
                       		regionDescription="Area Construida"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG --> 
             <%
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_CONSCENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_CONSCENTIMETROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
             %> 
             <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:consCentimetros"
                                   fieldName="Area Construida: Centimetros"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->
                                </td>
                            </table>
                                </td>
			</tr>
                        
                        <tr>
                            <td >Coeficiente</td>
				<td>
                                    <!-- TAG: BEGIN-REGION -->
                       		<!-- + + + + + + + + + + + + + + + + + + + + -->

                       		<xRegionTemplate:DisplayPoint
                       		regionId="htRgn_CoeficienteMain"
                       		regionName="Coeficiente"
                       		regionDescription="Coeficiente"
                       		debugEnabled="false"
                       		displayExtraMessage="null,true,false"
                       		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
                       		>	<!--   -->

                       		<!-- + + + + + + + + + + + + + + + + + + + + -->
                       		<!-- END-TAG --> 
                                    <%
				try {
                                    textHelper.setNombre(AWModificarFolio.FOLIO_COEFICIENTE);
                                    textHelper.setCssClase("camposformtext");
                                    textHelper.setId(AWModificarFolio.FOLIO_COEFICIENTE);
                                    textHelper.setSize("30");
                                    textHelper.setReadonly(false);
                                    textHelper.render(request, out);
                                } catch (HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                                    %>
                                <%-- [bug-03541]: sof@changes-mark --%>
                                   <xRegionTemplate:DeltaPoint
                                   fieldId="field:folio:coeficiente"
                                   fieldName="Coeficiente"
                                   fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                                   showChangesEnabled="enabled"
                                   />
                                   <%-- eof@changes-mark --%>


                                   <!-- TAG: END-REGION -->
                                   <!-- + + + + + + + + + + + + + + + + + + + + -->
                               </xRegionTemplate:DisplayPoint>
                               <!-- + + + + + + + + + + + + + + + + + + + + -->
                               <!-- END-TAG -->
                                                        </td>
                        </tr>
                        
                        <tr>
                            <td>Area</td>
                            <td colspan="3"> 
                                <table class="camposformnoborder"> 
                                <td align="right">Hectareas</td> 
                                <td nowrap> 
				<%
                                    if(idPermisoArea.equals("FOL_AREA")){
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_HECTAREAS); 
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_HECTAREAS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+AWModificarFolio.FOLIO_HECTAREAS+"');\"  "
                                        + "onChange=\"valideDot('"+AWModificarFolio.FOLIO_HECTAREAS+"')\"  "
                                        + "onBlur=\"hectareasFormatter('"+AWModificarFolio.FOLIO_HECTAREAS+"','"+AWModificarFolio.FOLIO_METROS+"','"+AWModificarFolio.FOLIO_CENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
                                    }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Metros<sup>2</sup> &nbsp;
				<%
                                    if(idPermisoArea.equals("FOL_AREA")){
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_METROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_METROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+AWModificarFolio.FOLIO_METROS+"');\"  "
                                        + "onChange=\"valideDot('"+AWModificarFolio.FOLIO_METROS+"')\"  "
                                        + "onBlur=\"metrosFormatter('"+AWModificarFolio.FOLIO_HECTAREAS+"','"+AWModificarFolio.FOLIO_METROS+"','"+AWModificarFolio.FOLIO_CENTIMETROS+"')\"  ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }}
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             Centimetros<sup>2</sup> &nbsp;
                             <%
                                 if(idPermisoArea.equals("FOL_AREA")){
				try {
	                areasHelper.setNombre(AWModificarFolio.FOLIO_CENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(AWModificarFolio.FOLIO_CENTIMETROS);
                        areasHelper.setSize("30");
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }}
                                %></td>
                                </table>
                                </td>
			</tr>
                        
                        <tr>
  	<!-- bug 3574: cambiar lindero por cabida y lindero -->
        <!-- Requerimiento Catastro Multiproposito: Cambiar Cabida y Lindero por Linderos -->
  	<td>Linderos </td>
  	<td>
  		<!-- TAG: BEGIN-REGION -->
  		<!-- + + + + + + + + + + + + + + + + + + + + -->

  		<xRegionTemplate:DisplayPoint
  		regionId="htRgn_LinderoMain"
  		regionName="cabida y lindero"
  		regionDescription="Contiene la descripcion de cabida y lindero del folio."
  		debugEnabled="false"
  		displayExtraMessage="null,true,false"
  		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
  		>	<!--   -->

  		<!-- + + + + + + + + + + + + + + + + + + + + -->
  		<!-- END-TAG -->

  		<%

  		try {
  		textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");
  		textAreaHelper.setNombre(AWModificarFolio.FOLIO_LINDERO);
  		textAreaHelper.setCols("80");
  		textAreaHelper.setRows("2");
  		textAreaHelper.setCssClase("camposformtext");
  		textAreaHelper.setId(AWModificarFolio.FOLIO_LINDERO);
  		textAreaHelper.render(request,out);
  	}
  	catch(HelperException re){
  	out.println("ERROR " + re.getMessage());
  }

  %>


  <%-- [bug-03541]: sof@changes-mark --%>
  <xRegionTemplate:DeltaPoint
  fieldId="field:folio:lindero"
  fieldName="Lindero"
  fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
  showChangesEnabled="disabled"
  />
  <%-- eof@changes-mark --%>


  <!-- TAG: END-REGION -->
  <!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

</td>
</tr>



<%--

Bug 3552



--%>

<%-- --%>
<%

Boolean ruleComplementacionAdicionaEnabledWrapper;
boolean ruleComplementacionAdicionaEnabled;

ruleComplementacionAdicionaEnabled = false;
if( null != ( ruleComplementacionAdicionaEnabledWrapper = ( (Boolean)session.getAttribute( AWModificarFolio.PARAM__OPTION_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONADICIONAENABLED ) ) ) ) {
ruleComplementacionAdicionaEnabled = ruleComplementacionAdicionaEnabledWrapper.booleanValue();

  } // if

  %>


  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- CONDITIONAL--%>
  <%

  if( ruleComplementacionAdicionaEnabled ) {

  %>
  <%-- CONDITIONAL--%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>






  <%-- [sof:row-mark] --%>

  <tr>
  	<td>Complementaci&oacute;n <sub>(Mayor a 20 a?os)</sub></td>
  	<td colspan="5" >
  		<!-- TAG: BEGIN-REGION -->
  		<!-- + + + + + + + + + + + + + + + + + + + + -->

  		<xRegionTemplate:DisplayPoint
  		regionId="htRgn_ComplementacionAdicionMain"
  		regionName="complementacion"
  		regionDescription="Contiene la complementacion del folio (definitiva mas las adiciones )."
  		debugEnabled="false"
  		displayExtraMessage="null,true,false"
  		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
  		>	<!--   -->

  		<!-- + + + + + + + + + + + + + + + + + + + + -->
  		<!-- END-TAG -->


  		<!-- t0 data -->

  		<%

  		try {
  		textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=1' ");
  		textAreaHelper.setReadOnly( true );
  		textAreaHelper.setId(     AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0 );
  		textAreaHelper.setNombre( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0 );

  		textAreaHelper.setCols( "80" );
  		textAreaHelper.setRows( "1"  );
  		textAreaHelper.setCssClase( "campositem" );
  		textAreaHelper.render( request, out );

  		textAreaHelper.setFuncion( "" );
  		textAreaHelper.setReadOnly( false );

  	}
  	catch(HelperException re){
  	out.println("ERROR " + re.getMessage());
  }

  %>


  <!-- t2 data -->

  <%

  try {
  textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=1' ");

  textAreaHelper.setId(     AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2 );
  textAreaHelper.setNombre( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2 );
  textAreaHelper.setCols( "80" );
  textAreaHelper.setRows( "1"  );
  textAreaHelper.setCssClase( "camposformtext" );
  textAreaHelper.render( request, out );

  textAreaHelper.setFuncion( "" );

}
catch(HelperException re){
out.println("ERROR " + re.getMessage());
}

%>


<%-- [bug-03541]: sof@changes-mark --%>
<xRegionTemplate:DeltaPoint
fieldId="field:folio:complementacion/complementacion+"
fieldName="Complementacion"
fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
showChangesEnabled="disabled"
/>
<%-- eof@changes-mark --%>



<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


</td>
</tr>


<%-- [eof:row-mark] --%>









<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
<%
}
else {


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>


<%-- [sof:row-mark] --%>

<tr>
	<td>Complementaci&oacute;n</td>
	<td colspan="5" >
		<!-- TAG: BEGIN-REGION -->
		<!-- + + + + + + + + + + + + + + + + + + + + -->

		<xRegionTemplate:DisplayPoint
		regionId="htRgn_ComplementacionMain"
		regionName="complementacion"
		regionDescription="Contiene la complementacion del folio."
		debugEnabled="false"
		displayExtraMessage="null,true,false"
		regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
		>	<!--   -->

		<!-- + + + + + + + + + + + + + + + + + + + + -->
		<!-- END-TAG -->

		<%

		try {

		textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=1' ");

		textAreaHelper.setId(     AWModificarFolio.FOLIO_COMPLEMENTACION );
		textAreaHelper.setNombre( AWModificarFolio.FOLIO_COMPLEMENTACION );

		textAreaHelper.setCols( "80" );
		textAreaHelper.setRows( "1"  );
		textAreaHelper.setCssClase( "camposformtext" );
		textAreaHelper.render( request, out );

		textAreaHelper.setFuncion( "" );

	}
	catch(HelperException re){
	out.println("ERROR " + re.getMessage());
}

%>

<%-- [bug-03541]: sof@changes-mark --%>
<xRegionTemplate:DeltaPoint
fieldId="field:folio:complementacion/complementacion"
fieldName="Complementacion"
fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
showChangesEnabled="disabled"
/>
<%-- eof@changes-mark --%>


<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


</td>
</tr>


<%-- [eof:row-mark] --%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
<%
  } // end if
  %>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>
  <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  +  --%>



</table>


</td>
</tr>
</table>



<table width="100%" class="camposform">
	<tr>
		<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td width="135">Comentario</td>
		<td class="campositem"><%= ((String)session.getAttribute(AWModificarFolio.FOLIO_COMENTARIO) !=null)?(String)session.getAttribute(AWModificarFolio.FOLIO_COMENTARIO):"SIN COMENTARIO"%></td>
	</tr>
</table>


<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
regionId="htRgn_DireccionMain"
regionName="direccion"
regionDescription="Contiene el conjunto de direcciones relacionadas dentro del folio"
debugEnabled="false"
displayExtraMessage="null,true,false"
regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

<hr class="linehorizontal" />
<br />

<%

try {
dirHelper.setFuncionCambiarAccion("cambiarAccionCorreccion");
dirHelper.setNombreFormaEdicionDireccion("CORRECCION");

dirHelper.setEnabledDeleteFromDefinitivas( true );
dirHelper.setJsFunctionNameDeleteFromDefinitivas( "jsFolioDireccionDelItemFromDefinitivas" );
dirHelper.setActionIdDeleteFromDefinitivas( AWModificarFolio.FOLIOEDIT_DIRECCIONDEFINITIVA_DELITEM_ACTION );

dirHelper.setEnabledEditFromDefinitivas( true );
dirHelper.setJsFunctionNameEditFromDefinitivas( "jsFolioDireccionEditItemFromDefinitivas" );
dirHelper.setActionIdEditFromDefinitivas( AWModificarFolio.FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION );

dirHelper.render(request,out);
}
catch(HelperException re){
out.println("ERROR " + re.getMessage());
}

%>

<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


<hr class="linehorizontal" />
<br />


<!--Tabla de Salvedad Folio-->
    <%--

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Salvedad Folio </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
	</table>
	--%>
	<span class="bgnsub" style="background:" >Salvedad: Folio </span>

	<table width="100%" class="camposform">
		<tr>
			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			<td>Salvedad</td>
		</tr>
		<tr>
			<td width="20">&nbsp;</td>
			<td>

                 <% // id

                 try {
                 TextHelper local_TextHelper;

                 local_TextHelper = new TextHelper();
                 local_TextHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID );
                 local_TextHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID );
                 local_TextHelper.setTipo( "hidden" );
                 local_TextHelper.setCssClase("camposformtext");
                 local_TextHelper.render( request, out );

             }
             catch( HelperException re ) {
             out.println( "ERROR " + re.getMessage() );
         }



         %>
                 <% // text
                 try {
                 textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");

                 textAreaHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION );
                 textAreaHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION );
                 textAreaHelper.setCols( "130" );
                 textAreaHelper.setRows( "2" );
                 textAreaHelper.setCssClase("camposformtext");
                 textAreaHelper.render(request,out);
             }
             catch( HelperException re ){
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














<!--TABLA DE BOTONES DE ANOTACIONES-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
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
					<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
</form>

<tr>
	<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
	<td valign="top" bgcolor="#79849B" class="tdtablacentral">


		<!-- TAG: BEGIN-REGION -->
		<!-- + + + + + + + + + + + + + + + + + + + + -->
		<!-- no region -->
		<!-- + + + + + + + + + + + + + + + + + + + + -->
		<!-- END-TAG -->

<%--
  // BtnItem: mostrar/ocultar panel de anotaciones
  // conditional: parametro de sesion "param_AnotacionesWndIconState"
  --%>
  <%

  if( ( null != param_AnotacionesWndIconState )
  	&&( "SHOW_MINIMIZED".equals( param_AnotacionesWndIconState ) ) ) {
      // dibujar icono minimized
//MINIMIZED
%>


<%
}
%>



<%

if( ( null != param_AnotacionesWndIconState )
	&&( "SHOW_MAXIMIZED".equals( param_AnotacionesWndIconState ) ) ) {
      // dibujar icono maximized
//MAXIMIZED
%>


<%
}
%>


<%-- + + + + + + + + + + + + + + + + + + + + --%>
<%
  // Mostrar las anotaciones con folioHelper
Folio folio_t0 = (Folio)session.getAttribute( WebKeys.FOLIO );

if( null == folio_t0 ){
    //log.warn( "Folio Null" );
}


String vistaActual;

  // inicializacion Vista generadora

String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
vistaActual= ultimaVista;


  //se mira si ya esta seteado llavesPaginador

LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
LLaveMostrarFolioHelper lla=null;
if(llaves==null){
          //se crea el objeto llavesPaginador y settear sus valores;
          llaves= new LLavesMostrarFolioHelper();
          lla= new LLaveMostrarFolioHelper();
          lla.setNombrePaginador("NOMBRE_PAGINADOR_CALIFICACION");
          lla.setNombreResultado("NOMBRE_RESULTADO_CALIFICACION");
          lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_CALIFICACION");
          llaves.addLLave(lla);
          request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
      }
      if(lla==null){
      lla=llaves.getLLave("NOMBRE_RESULTADO_CALIFICACION", "NOMBRE_PAGINADOR_CALIFICACION");
      if(lla==null){
      lla= new LLaveMostrarFolioHelper();
      lla.setNombrePaginador("NOMBRE_PAGINADOR_CALIFICACION");
      lla.setNombreResultado("NOMBRE_RESULTADO_CALIFICACION");
      lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_CALIFICACION");
      llaves.addLLave(lla);
      request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
  }
}



%>

<%
try {
  //setear atributos del folio.
  mFolio.setFolio( folio_t0 );
  mFolio.setTipoMostrarFolio( gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper.TIPO_SOLO_ANOTACIONES_NORMAL );
  mFolio.setEditarAnotacionesNoTemporales( true );

  // fijar que trabaje con id
  mFolio.setAnotacionEditLink( MostrarFolioHelper.ANOTACIONEDITLINK_EDITARSEGUNID );

  // fijar mensaje segun sean anotaciones definitivas con cambios temporales
  // o anotaciones temporales
  mFolio.setTemporalConContraparteDefinitivaEnabled( true );

  /*setters de estilo*/
  mFolio.setECampos("camposform");
  mFolio.setECampoTexto("campositem");
  mFolio.setETituloFolio("titulotbcentral");
  mFolio.setETitulos("titresaltados");
  mFolio.setETitulosSecciones("bgnsub");

  /*setters de imagenes */
  mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
  mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
  mFolio.setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
  mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");

//  mFolio.setMayorExtension(esFolioMayorExtension);
  mFolio.setNombreAccionFolio( "modificacion.do" );//"calificacion.do";
  mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
  mFolio.setNombreAncla("ancla");
  mFolio.setNombreForma("PAGINADOR_ADENTRO");
  mFolio.setNombreFormaFolio("FORMA_FOLIO");
  mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
  mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CALIFICACION");
  mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
  mFolio.setNombreOcultarFolio("O_FOLIO");
  mFolio.setNombrePaginador(lla.getNombrePaginador());
  mFolio.setNombreResultado(lla.getNombreResultado());
  mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
  mFolio.setPaginaInicial(0);
  mFolio.setVistaActual(vistaActual);
  mFolio.setTotalAnotaciones(totalAnotaciones);
  //mFolio.setGravamenes(gravamenes);
  //mFolio.setMedCautelares(medCautelares);
  //datos a mostrar encabezado
  mFolio.NoMostrarEncabezado();
  // mFolio.MostrarAperturaFolio();
  // mFolio.MostrarCabidaLinderos();
  // mFolio.MostrarGravamenes();
  // mFolio.MostrarDireccionInmueble();
  // mFolio.MostrarComplementacion();
  mFolio.render(request, out);
}
catch(HelperException re){
out.println( "ERROR " + re.getMessage() );
}
%>

<%-- + + + + + + + + + + + + + + + + + + + + --%>



















<table width="100%" >
	<tr>
		<td>
          <%--
             cambio: ahora se usa mostrarFolioHelper
                     para mostrar las anotaciones
                     --%>
          <%--


	<!--ANTES DE -->
		<!-- dibujar helper con las definitivas -->
     <% try {
		   anotacionesModificacionHelper.setListaAnotaciones(anotacionesDefinitivas);
		   anotacionesModificacionHelper.render(request,out);
	    }
		catch(HelperException re){
		 	out.println("ERROR " + re.getMessage());
		}
	%>
		<!-- dibujar paginador -->
		<%
			  try {
                                          // generaba una excepcion cuando se carga por primera vez
                                          // debido a que no habia nada en sesion;
                                          // por esto se usa junto al flag de carga
                                         if( !carga ) {
                                            pag.drawGUI( request,out );
                                         }
				}
			  catch(HelperException re){
				 out.println("ERROR " + re.getMessage());
				}
			  %>

		<!-- dibujar helper con las temporales -->
		<% try {
		   anotacionesModificacionHelper.setListaAnotaciones(anotacionesTemporales);
		   anotacionesModificacionHelper.render(request,out);
	    }
		catch(HelperException re){
		 	out.println("ERROR " + re.getMessage());
		}
	%>
	<!--DESPUES DE -->
	--%>
</td>
</tr>
<tr>
	<td>

		<table width="100%" class="camposform" border="0">
			<tr>

				<td width="20" height="24">
					<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" />
				</td>

				<!-- TAG: BEGIN-REGION -->
				<!-- + + + + + + + + + + + + + + + + + + + + -->


				<!-- aggregar -->
				<td width="161" border="0">
					<xRegionTemplate:DisplayPoint
					regionId="htRgn_AnotacionesCrearAnotacionNormal"
					regionName="anotacion - crear"
					regionDescription="Permite la creacion de nuevas anotaciones."
					debugEnabled="false"
					displayExtraMessage="null,true,false"
					regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
					>	<!--   -->

					<!-- + + + + + + + + + + + + + + + + + + + + -->
					<!-- END-TAG -->

					<a href="javascript:cambiarAccionCorreccion('<%=AWModificarFolio.CARGAR_ANOTACION_AGREGAR%>')">
						<img src="<%=request.getContextPath()%>/jsp/images/btn_crear.gif" name="Folio" width="139" height="21" border="0" />
					</a>

					<!-- TAG: END-REGION -->
					<!-- + + + + + + + + + + + + + + + + + + + + -->
				</xRegionTemplate:DisplayPoint>
				<!-- + + + + + + + + + + + + + + + + + + + + -->
				<!-- END-TAG -->

			</td>


			<!-- crear cancelacion -->
			<td width="188" >
				<xRegionTemplate:DisplayPoint
				regionId="htRgn_AnotacionesCrearAnotacionCanceladora"
				regionName="anotacion canceladora - crear "
				debugEnabled="false"
				regionDescription="Permite la creacion de nuevas anotaciones de tipo canceladora."
				displayExtraMessage="null,true,false"
				regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
				>	<!--   -->

				<!-- + + + + + + + + + + + + + + + + + + + + -->
				<!-- END-TAG -->    &nbsp;

				<!--a href="javascript:cambiarAccionCorreccion('CANCELAR')"-->
				<a href="javascript:js_OnEvent( 'CORRECCION', PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION );" >
					<img src="<%=request.getContextPath()%>/jsp/images/btn_crear_cancelacion.gif" name="Siguiente" width="159" height="21" border="0" id="Siguiente" alt="crear cancelacion (v2)"/>
				</a>
				<!-- TAG: END-REGION -->
				<!-- + + + + + + + + + + + + + + + + + + + + -->
			</xRegionTemplate:DisplayPoint>
			<!-- + + + + + + + + + + + + + + + + + + + + -->
			<!-- END-TAG -->


		</td>

		<!-- eliminado, bug  0002985 -->
		<!-- cancelar /cache -->
          <%--
          <a href="javascript:cambiarAccionCorreccion('<%=AWModificarFolio.CANCELAR_ANOTACION_SESSION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar_anotacion.gif" name="Siguiente" width="180" height="21" border="0"></a>
          --%>
          <%--
          <!-- copiar -->

                  <td>
	                  <a href="copia.anotacion.escoger.folios.view">
	                    <img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
	                  </a>
                  </td>

          <!-- segregar -->

                 <td width="15%" align="center">
                    <a href="javascript:cambiarAccion('<%=AWCalificacion.SEGREGACION_ANOTACION%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_segregar.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente">
                    </a>
                 </td>
                 --%>
                 <td>&nbsp;</td>

                 <%-- bug 3551 --%>

                 <td width="150" align="center" >
                 	<a href="javascript:js_OnEvent( 'CORRECCION', PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION );">
                 		<img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif"
                 		name="Siguiente" width="150" height="21" border="0"
                 		id="Siguiente"
                 		alt="copy-anot-evt"
                 		/>
                 	</a>
                 </td>

                 <%-- bug 3563 --%>
                 <td width="150" align="center" >
                 	<a href="javascript:js_OnEvent( 'CORRECCION', PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION );">
                 		<img src="<%=request.getContextPath()%>/jsp/images/btn_segregar.gif"
                 		name="Siguiente" width="150" height="21" border="0"
                 		id="Siguiente"
                 		alt="segregar-evt"
                 		/>
                 	</a>
                 </td>

                 <td width="150" align="center" >
                 	<a href="javascript:js_OnEvent( 'CORRECCION', PAGE_REGION__STARTANOTACIONENGLOBE_ACTION );">
                 		<img src="<%=request.getContextPath()%>/jsp/images/btn_englobar.gif"
                 		name="Siguiente" width="150" height="21" border="0"
                 		id="Siguiente"
                 		alt="englobar-evt"
                 		/>
                 	</a>
                 </td>


                 <td>&nbsp;</td>

             </tr>

         </table>

     </td>
 </tr>
</table>

<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- no region -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


</td>
<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
</tr>

<tr>
	<td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
	<td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
</tr>
</table>


<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->



<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->



<!-- Save -->
<hr class="linehorizontal" />

<table width="100%" class="camposform">
	<tr>
		<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
		<td width="140"><a href="javascript:cambiarAccionCorreccion('<%=AWModificarFolio.ACEPTAR_EDICION_CORRECCION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="150" height="21" border="0"></a></td>
		<td colspan='2'>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>






</td>
<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
</tr>
<tr>
	<td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
	<td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
</tr>
</table>