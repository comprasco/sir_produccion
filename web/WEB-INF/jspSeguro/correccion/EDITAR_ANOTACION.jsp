<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="java.text.*"%>
<%@page import="gov.sir.core.util.ListasContextoUtil"%>

<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%@taglib prefix="xRegionTemplate" uri="/xRegionTemplateTags" %>
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- Edicion anotacion-normal modulo:correcciones -->
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
<%-- "panel de acciones / resources" --%>

  <!-- block:sof add effects lib -->
  <!-- @see: http://script.aculo.us/ -->

  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js" type="text/javascript"  ></script>
  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/effects.js"   type="text/javascript"  ></script>
  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/dragdrop.js"  type="text/javascript"  ></script>

  <!-- block:eof -->
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- Bug 03541 --%>
<%

  // Usado para observar si cada campo tiene o no variacion
  // :: en el caso del folio
  java.util.Map local_FwdDiffComparisonResultsMap = null;

  local_FwdDiffComparisonResultsMap = (java.util.Map)session.getAttribute( AWModificarFolio.PARAM__ITEM_CORRSIMPLE_FOLIOANOTACIONXEDIT_FORWARDDIFFCOMPARISONRESULTS );

%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>





<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"></link>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<%
    ListaElementoHelper tipoModalidadHelper = new ListaElementoHelper();
    List modalidad = ListasContextoUtil.getModalidades();      
            if(modalidad == null){
                modalidad = new ArrayList();
            }
    tipoModalidadHelper.setTipos(modalidad);
    tipoModalidadHelper.setCssClase("camposformtext");
    
    String modalidadOld = (String) request.getSession().getAttribute(AWModificarFolio.ANOTACION_MODALIDAD);
    
    Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    TextHelper textHelper = new TextHelper();

    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
	
	Boolean aux = (Boolean)session.getAttribute(AWModificarFolio.MODIFICA_DEFINITIVA);
	boolean modificaDefinitiva = false;
	if(aux!=null){
		modificaDefinitiva = aux.booleanValue();
	}
	
	String idNatJuridica = (String)session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
	if(idNatJuridica==null){
		idNatJuridica="";
	}
	
	session.removeAttribute(AWModificarFolio.VISTA_SEGREGACION_ANOTACION_DEFINITIVA);

    gov.sir.core.web.helpers.registro.OficinaHelper oficinaHelper
    = new  gov.sir.core.web.helpers.registro.OficinaHelper();

    ListaElementoHelper tiposDocHelper =  new ListaElementoHelper();


    // helper para ciudadanos
    CiudadanosModificarAnotacionHelper ciudadanosHelper
    = new CiudadanosModificarAnotacionHelper();

    // helper para ciudadanos v2

    VariosCiudadanosAnotacionHelper variosCiudadanosHelper
    = new VariosCiudadanosAnotacionHelper();
    List lciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
    Integer auxNumFilas = (Integer)session.getAttribute(AWModificarFolio.NUM_REGISTROS_TABLA_CIUDADANOS);
    if( null == lciudadanos ){
       lciudadanos= new Vector();
    }

    int numFilas;
    if( auxNumFilas == null ) {
      numFilas=AWModificarFolio.DEFAULT_NUM_CIUDADANOS_TABLA;
    }
    else{
      numFilas=auxNumFilas.intValue();
    }

    List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
    List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
    List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
    List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
    List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
    variosCiudadanosHelper.setPropertiesHandly(
        numFilas
      , tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica
      , AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION
      , AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION
      , AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION
      , AWCalificacion.ELIMINAR_CIUDADANO_EDICION
      , lciudadanos
      , AWCalificacion.VALIDAR_CIUDADANO_EDICION
      , "ANOTACION"
    );
    // variosCiudadanosHelper.setMostrarListaCiudadanos( false );
    variosCiudadanosHelper.setEdicionCiudadanoSobreTodosFolios_FlagEnabled( true );
    variosCiudadanosHelper.setEdicionCiudadanoSobreTodosFolios_JsHandler( "javascript:jsHandler_DoEdicionCiudadanoSobreTodosFolios" );
    variosCiudadanosHelper.setAccionUltimosPropietarios(AWCalificacion.GET_ULTIMOS_PROPIETARIOS);
	variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
	variosCiudadanosHelper.setAccionEditar(AWModificarFolio.EDITAR_ANOTACION_CIUDADANO_EDICION);
	if(turno!=null){
		variosCiudadanosHelper.setProceso(Long.toString(turno.getIdProceso()));
	}
	request.getSession().setAttribute("paramVistaAnterior","EDITAR_ANOTACION_CORRECCIONES");


    // Cambiar a Id-Anotacion
    String idAnotacion = (String)request.getSession().getAttribute( AWModificarFolio.ANOTACION_ID_ANOTACION );
    String idDocumento = (String)request.getSession().getAttribute( AWModificarFolio.ANOTACION_NUM_DOCUMENTO );
    String orden = (String)request.getSession().getAttribute( AWModificarFolio.ANOTACION_ORDEN );
    String idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
    String posicionAnotacion = (String)request.getSession().getAttribute(AWModificarFolio.POSICIONANOTACION);
    String agregarAnotacion = (String)request.getSession().getAttribute(AWModificarFolio.AGREGAR_ANOTACION);

	//LISTA DE ESTADO DEL FOLIO
	ListaElementoHelper estadoFolioHelper = new ListaElementoHelper();
	List estadosAnotacion = (List)session.getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA);
	if(estadosAnotacion == null){
		estadosAnotacion = new Vector();
	}
	estadoFolioHelper.setOrdenar(false);
	estadoFolioHelper.setTipos(estadosAnotacion);
	estadoFolioHelper.setCssClase("camposformtext");
%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- Configuracion de permisos --%>
<%-- Cargar parametros del padre  --%>
<%

    gov.sir.core.web.helpers.correccion.region.model.RegionManager pageLocalRegionManager = null;

   
     localBlockAuth: {


          gov.sir.core.web.helpers.correccion.region.model.RegionManager localRegionManager
          = new gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager();

          // mapa de permisos
          java.util.HashMap permisosMap;

          // test1 -----------------------------------------------------

          // un mapa de permisos de prueba
          java.util.HashMap testPermisosMap = new java.util.HashMap();

/*
          // populate the test
          String testPermisosMap_Key = null;
          gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel testPermisosMap_Value = null;

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_CIUDADANOS_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_ESPECIFICACION_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_FECHA_ID;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );


          //TODO: quitar key para anoraciones-id
          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_ID;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );
*/
          // ----------------------------------------------------------

          // cargar permisos de prueba
          permisosMap = testPermisosMap;


          // loadPermisos1 -----------------------------------------------------

          java.util.List modelPermisosList = null;
           modelPermisosList = (java.util.List)session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST );

          if( ( null == modelPermisosList ) ) {
              // cargar defaultpermisos - - testPermisos

              // cargar permisos de prueba
              permisosMap = testPermisosMap;
              // permisosMap = new java.util.HashMap();
          }
          else {


              // mapa de permisos model
              java.util.HashMap modelPermisosMap = new java.util.HashMap();

              // populate the model
              String modelPermisosMap_Key = null;
              gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel modelPermisosMap_Value = null;

              // cargar model permisos
              java.util.Iterator iterator
              = modelPermisosList.iterator();

              for(; iterator.hasNext(); ) {
                gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion permiso
                = (gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion)iterator.next();


                modelPermisosMap_Key   = permiso.getIdPermiso();//gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;
                modelPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( modelPermisosMap_Key );
                modelPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega

                modelPermisosMap.put( modelPermisosMap_Key, modelPermisosMap_Value );

              }
    
              // ----------------------------------------------------------
              permisosMap = modelPermisosMap;

          } // end if


          // cargar para probar
          // TODO: remover despues
          // permisosMap = testPermisosMap;


          // filter expression:
          // aplicar los permisos;
          // con la lista de permisos, relaciona los items que se deben desplegar o no
          localRegionManager.filter( permisosMap );

          pageLocalRegionManager = localRegionManager;

        } // :localBlockAuth

        // ..... se debe propagar el conjunto de permisos a las paginas hijas
        session.setAttribute( "param_RegionManager", pageLocalRegionManager );
    
    
    
     pageLocalRegionManager = (gov.sir.core.web.helpers.correccion.region.model.RegionManager)session.getAttribute( "param_RegionManager" );
      
                	  
%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

function ocultarSalvedad(text) {
   document.ANOTACION.VER_SALVEDAD.value = text;
   cambiarAccion('VER_SALVEDAD');
}

function cambiarAccionAndSendTipoTarifa(text) {
	if(document.ANOTACION.<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID %>.value!=""){
		document.ANOTACION.ACCION.value = text;
		document.ANOTACION.submit();
	}
}
function cambiarAccion(text) {
	document.ANOTACION.ACCION.value = text;
	document.ANOTACION.POSSCROLL.value = <%=(request.getParameter("POSSCROLL")!= null?request.getParameter("POSSCROLL"):String.valueOf(0))%>;
	document.ANOTACION.submit();
}
function cambiarAccion2(text) {
	document.ANOTACION.ACCION.value = text;
}
function cargarFolio(text,folio) {
    document.ANOTACION.action = 'modificacion.do';
    document.ANOTACION.ACCION.value = text;
    document.ANOTACION.<%=CFolio.ID_MATRICULA%>.value = folio;
    document.ANOTACION.submit();
}
function quitar(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function guardarCambio(pos,accion){
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function editar(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function cargarAnotacion(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.ANOTACION.ACCION.value='<%=AWModificarFolio.PRESERVAR_INFO%>';
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
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.getElementById('version').value=valor+"_OFICINA_VERSION";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
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
	var fechaRad = null;
        if(document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>') != null){
            fechaRad = document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>').value;
        }else{
            fechaRad = document.getElementById('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>_hidden').value;
        }
	var url = nombre + '&<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>='+ fechaRad;
        url = url + '&<%=AWNaturalezaJuridica.FOLIO_FECHA_APERTURA_NJ %>=' + document.getElementById('<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>').value;
	popup=window.open(url,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.ANOTACION.POSICION.value=pos;
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
	document.ANOTACION.SECUENCIA.value = text;
	cambiarAccion('REFRESCAR_EDICION');
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







<%--
--%>
<script type="text/javascript">
   var popUpWin=0;
</script>
<script type="text/javascript">

function popUpWindow(URLStr, left, top, width, height)
{
  if(popUpWin)
  {
    if(!popUpWin.closed) popUpWin.close();
  }
  popUpWin = open(URLStr, 'popUpWin', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width='+width+',height='+height+',left='+left+', top='+top+',screenX='+left+',screenY='+top+'');
  return popUpWin;
}


  function jsHandler_DoEdicionCiudadanoSobreTodosFolios( ciudadanoId ) {
    // para cada uno de los ciudadanos debe confirmar
    // alert( "go:" + ciudadanoId );
    // var win = new PopupWindow();
    // win.setSize(800,200);
    // win.setUrl("./");
    // win.offsetX = 0;
    // win.offsetY = 0;
    // win.showPopup( '' );
    var uriString = "corr-edicionciudadanosobretodosfolios.do"
                  + "?" + "<%=WebKeys.ACCION %>" +"=" + "<%= AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONLOAD__PAGERENDERING_PROCESS_ACTION %>"
                  + "&" + "PAGEITEM__CIUDADANO_IDCIUDADANO" + "=" + ciudadanoId ;


    var popupWndHandler = popUpWindow( uriString, 100,100,800,500 );

    if( popupWndHandler.opener == null ){
      popupWndHandler.opener = self;
      popupWndHandler.focus();
    } // end if

  }

</script>
<%--
--%>
<script type="text/javascript">
</script>
<%--
--%>


<%

String param_SourcePage = (String)session.getAttribute( org.auriga.smart.SMARTKeys.VISTA_ACTUAL );
String consultaAnotacionesResp = (String)session.getAttribute(AWModificarFolio.CONSULTA_ANOTACIONES_RESP);
String existenAnotacionesAsociadas = null;
int existenAnotacionesAsociadasInt = 1;
%>
    <form action="modificacion.do" name="RELOADDATAFORM" id="RELOADDATAFORM" method="post">
        <input type="hidden" name="RELOAD_DATA" id="RELOAD_DATA" value="" />
        <input type="hidden" name="SOURCE_PAGE" id="SOURCE_PAGE" value="<%=param_SourcePage%>" />
        <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIOEDIT_RELOADBYUPDATECIUDADANO_EDITANOTACIONNORMAL_PAGERENDERING_PROCESS_ACTION %>" />
    </form>

            <form action="modificacion.do" name="ANOTACIONES_ASOCIADAS" id="ANOTACIONES_ASOCIADAS">
                <input type="hidden" name="<%=AWModificarFolio.ANOTACION_ID_ANOTACION%>" id="<%=AWModificarFolio.ANOTACION_ID_ANOTACION%>" value="<%=idAnotacion%>"/>
                <input type="hidden" name="<%=AWModificarFolio.FOLIO_NUM_FOLIO%>" id="<%=AWModificarFolio.FOLIO_NUM_FOLIO%>" value="<%=idMatricula%>"/>
                <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.correccion.AWModificarFolio.ANOTACIONES_ASOCIADAS%>" />
            </form>
        <%if(consultaAnotacionesResp == null){%>
            <script type="text/javascript">document.ANOTACIONES_ASOCIADAS.submit();</script>
        <%} else {
            existenAnotacionesAsociadas = (String)session.getAttribute(AWModificarFolio.EXISTEN_ANOTACIONES_ASOCIADAS);
            existenAnotacionesAsociadasInt = Integer.parseInt(existenAnotacionesAsociadas);
          }%>
<form action="modificacion.do" name="ANOTACION" id="ANOTACION" method="post">
    <input type="hidden" id="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" name="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" value="<%=session.getAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION) %>" />
    <input type="hidden" id="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" name="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" value="<%=session.getAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA) %>"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<%if(existenAnotacionesAsociadasInt > 1){%>
    <%--<xRegionTemplate:DisplayPoint
        regionId="htRgn_AnotacionesCambioDocumento"
        regionName="anotacion - tipo de cambio"
        regionDescription="tipo de modificacion en la anotacion"
        debugEnabled="false"
        displayExtraMessage="null,true,false"
        regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>">
      <tr>
        <td width="12">&nbsp;</td>
        <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Advertencia al momento de corregir anotaciones</td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_alerta.gif" width="20" height="20"></td>
                </tr>
            </table>
        </td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <td class="tdtablaanexa02">
                    <br><br>
                    <b>Individual&nbsp;:</b>&nbsp;&nbsp;para que las modificaciones del documento se reflejen solo en la actual anotaci&oacute;n.<br><br>
                    <b>Multiple&nbsp;&nbsp;&nbsp;&nbsp;:</b>&nbsp;&nbsp;para que las modificaciones del documento se reflejen en todas las anotaciones de los folios asociados a la anotaci&oacute;n actual.<br><br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="opcCambio" value ="INDIVIDUAL" onclick="document.getElementById('<%=AWModificarFolio.TIPO_CAMBIO_ANOTACION%>').value=this.value" checked>Individual
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="opcCambio" value ="MULTIPLE" onclick="document.getElementById('<%=AWModificarFolio.TIPO_CAMBIO_ANOTACION%>').value=this.value">Multiple
                            <input type="hidden" name="<%=AWModificarFolio.TIPO_CAMBIO_ANOTACION%>" value ="INDIVIDUAL"/>
                </td>
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
  </xRegionTemplate:DisplayPoint>--%>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td><input type="hidden" name="<%=AWModificarFolio.TIPO_CAMBIO_ANOTACION%>" value ="INDIVIDUAL"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
  <%}%>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
                      Corregir Anotaci&oacute;n
                  </td>
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
		<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">
	    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">
   	    <input type="hidden" name="<%=AWModificarFolio.AGREGAR_ANOTACION%>" value="<%=agregarAnotacion!=null?agregarAnotacion:""%>">
	    <input type="hidden" name="<%=AWModificarFolio.POSICIONANOTACION%>" value="<%=posicionAnotacion!=null?posicionAnotacion:""%>">

           <input type="hidden" name="POSICION" value="" />
           <input type="hidden" name="VER_SALVEDAD" value="" />
           <input type="hidden" name="SECUENCIA" value="-1" />


		<input name="id_depto" type="hidden" id="id_depto" value="">
		<input name="nom_depto" type="hidden" id="nom_depto" value="">
		<input name="id_munic" type="hidden" id="id_munic" value="">
		<input name="nom_munic" type="hidden" id="nom_munic" value="">
		<input name="id_vereda" type="hidden" id="id_vereda" value="">
		<input name="nom_vereda" type="hidden" id="nom_vereda" value="">

      <!--input name="Depto" type="hidden" id="Depto" value="">
      <input name="Mpio" type="hidden" id="Mpio" value="">
      <input name="Ver" type="hidden" id="Ver" value="">
      <input name="natjuridica" type="hidden" id="natjuridica" value="">
      <input name="tipo_oficina" type="hidden" id="tipo_oficina" value="">
      <input name="numero_oficina" type="hidden" id="numero_oficina" value="">
      <input name="Depto" type="hidden" id="id_depto" value="">
      <input name="Depto" type="hidden" id="nom_Depto" value="">
      <input name="Mpio" type="hidden" id="id_munic" value="">
      <input name="Mpio" type="hidden" id="nom_munic" value="">
      <input name="Ver" type="hidden" id="id_vereda" value="">
      <input name="Ver" type="hidden" id="nom_vereda" value=""-->


      <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
      <tr>
        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"/></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10" /></td>
        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"/></td>
      </tr>
      <tr>

        <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""/></td>
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
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""/></td>
            </tr>
        </table>
       </td>
        <!-- mark -->
        <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""/></td>
        <!-- mark -->
      </tr>
      <tr>
        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <!--
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              -->
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaci&oacute;n: N&ordm; <%=orden!=null?orden:""%> </td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>


<!-- block: "datos basicos" -->

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Anotaci&oacute;n: Datos B&aacute;sicos </td>
                </tr>
                <tr>
                	<td>&nbsp;</td>
                	<td>



                          <table width="100%" class="camposform">
                		<tr>
<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
                			<td width="150">N&uacute;mero anotaci&oacute;n <!-- orden --></td>
                			<td align="right" width="200">

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesNumero_Main"
  regionName="anotacion - numero"
  regionDescription="Contiene el orden de la anotacion, numero de anotacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

                                                <%
                                                // :: helper orden
						try {
							textHelper.setNombre(AWModificarFolio.ANOTACION_ORDEN);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(AWModificarFolio.ANOTACION_ORDEN);
							textHelper.render(request,out);
						}
                                                catch( HelperException re ) {
							out.println("ERROR " + re.getMessage());
						}

						%>

                                                <%--a.getOrden()--%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:orden"
                    fieldName="Anotacion - Orden "
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
                			<td>&nbsp;</td>
                			<td width="15">&nbsp;<%--[codigo-interno :<%=idAnotacion %>]--%></td>
                			<td align="right" width="15%">

                		</tr>
                		</table>
                	</td>
                	<td>&nbsp;</td>
                </tr>


                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                  <td>Anotacion: Radicacion manual </td>
                </tr>
                <tr>
                  <td width="20">&nbsp;</td>
                  <td>
                    <table border="0"  width="100%" class="camposform">
                      <tr>

<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>

                        <td>
                          <table border="0" cellpadding="0" cellspacing="0">

                            <tr class="camposform">
                          <td width="150" colspan="2">Núm. radicación</td>
                        <td>

        <table border="0" cellpadding="0" cellspacing="0">
        <tr class="camposform">
        <td>


<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesRadicacionManualMain"
  regionName="anotacion - radicacion (manual) / numero radicacion"
  regionDescription="numero con el cual se radica la anotacion."
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

          <%
          textHelper.setTipo("text");
          textHelper.setFuncion(" ");
          textHelper.setNombre(AWModificarFolio.ANOTACION_NUM_RADICACION);
          textHelper.setCssClase("camposformtext");
          textHelper.setId(AWModificarFolio.ANOTACION_NUM_RADICACION);
          textHelper.render(request, out);
         %>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:numRadicacion"
                    fieldName="Anotacion - NumRadicacion "
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
        </table>

</td>
                        <td width="100">&nbsp;
                        </td>

                        <td width="150">Fecha radicacion</td>
                        <td>
                        <table align="left">
                        <tr>
                        <td class="camposform" style="border:0px;">

<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesFechaMain"
  regionName="anotacion - fecha"
  regionDescription="Fecha de la radicacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
<!--
<table align="left">
<tr>
  -->
                      <%

                      // :: fecha

                      try {
                              textHelper.setNombre(AWModificarFolio.ANOTACION_FECHA_RADICACION);
                              textHelper.setCssClase("camposformtext");
                              textHelper.setId(AWModificarFolio.ANOTACION_FECHA_RADICACION);
                              textHelper.render(request,out);
                      }
                      catch(HelperException re){
                              out.println("ERROR " + re.getMessage());
                      }

                      %>


                      <span>
                        <a href="javascript:NewCal('<%=AWModificarFolio.ANOTACION_FECHA_RADICACION%>','ddmmmyyyy',true,24)">
                          <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')" />
                        </a>
		     </span>


                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:fechaRadicacion"
                    fieldName="Anotacion - Fecha Radicacion "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>


<!--
</tr>
</table>
-->
<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

                        </td>

                            </tr>
                          </table>


                        </td>





                      </tr>
                  </table></td>
                </tr>


                <tr><td colspan="6"><hr class="linehorizontal" /></td>
                </tr>
<!-- ojo: malformed xhtml segment -->
                <tr><!--<td>&nbsp;</td>--><td colspan="5" align="left">

<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesDocumentoMain"
  regionName="anotacion - documentos"
  regionDescription="datos de documento enlazados en la anotacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->


                </td>
                </tr>



                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td colspan="5">Documento: Datos B&aacute;sicos </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td colspan="4"><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td>



                            <%
                            // :: tipos de documento

                            List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                            tiposDocHelper.setOrdenar(false);
							tiposDocHelper.setTipos(tiposDocs);

                            try {
                                tiposDocHelper.setId(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
                                tiposDocHelper.setCssClase("camposformtext");
                                tiposDocHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
                                tiposDocHelper.render(request,out);
                            }
                            catch( HelperException re ){
                                    out.println("ERROR " + re.getMessage());
                            }

                            %>


                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento"
                    fieldName="Anotacion - Documento - TipoDocumento "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>




                        <%--try {
		 		                tiposDocHelper.setId(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setCssClase("camposformtext");
								tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setEditable(false);
								tiposDocHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 --%>
                        </td>
                        <td>N&uacute;mero : <%--= idDocumento--%>
                          <%
                          // :: numero documento / id-documento
                          
                          if (request.getSession().getAttribute(CFolio.ANOTACION_NUM_DOCUMENTO) == null ){
                          	request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,"");
                          } else {
                          	if ((request.getSession().getAttribute(CFolio.ANOTACION_NUM_DOCUMENTO)).equals("null")){
                          		request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,"");
	                        }
                          }
                          try {
                            textHelper.setTipo("text");
                            textHelper.setNombre(CFolio.ANOTACION_NUM_DOCUMENTO);
                            textHelper.setCssClase("camposformtext");
                            textHelper.setId(CFolio.ANOTACION_NUM_DOCUMENTO);
                            textHelper.setEditable(true);
                            // textHelper.setReadonly( true );
                            textHelper.render(request,out);
                          }
                          catch( HelperException re ){
                            out.println("ERROR " + re.getMessage());
                          }
                          %>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/numero"
                    fieldName="Anotacion - Documento - Numero "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                        </td>

                        <td>&nbsp;</td>

 <td>Fecha documento</td>
                        <td>
                        <table align="center">
                        <tr>
                        <td>


                      <%

                      // :: fecha / documento

                      try {
                              textHelper.setReadonly( false );
                              textHelper.setTipo("text");
                              textHelper.setNombre(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
                              textHelper.setCssClase("camposformtext");
                              textHelper.setId(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
                              textHelper.setEditable(true);
                              textHelper.render(request,out);
                      }
                      catch(HelperException re){
                              out.println("ERROR " + re.getMessage());
                      }

                      %>


                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/fecha"
                    fieldName="Anotacion - Documento - Fecha "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>


                      </td>
                      <td>
                        <a href="javascript:NewCal('<%=AWModificarFolio.ANOTACION_FECHA_DOCUMENTO%>','ddmmmyyyy',true,24)">
                          <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')" />
                        </a>
		     </td>

                      </tr>
                  </table></td>










                     </tr>
                    </table>


                  </td>



                </tr>






                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td colspan="5">Documento: Oficina de Procedencia

                    &nbsp;



                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento"
                    fieldName="Anotacion - Documento - OficinaOrigen - Departamento "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio"
                    fieldName="Anotacion - Documento - OficinaOrigen - Municipio "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda"
                    fieldName="Anotacion - Documento - OficinaOrigen - Vereda "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/numero"
                    fieldName="Anotacion - Documento - OficinaOrigen - Numero "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/numero"
                    fieldName="Anotacion - Documento - OficinaOrigen - Numero "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen"
                    fieldName="Anotacion - Documento - OficinaOrigen - Id "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>




                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td width="400" colspan="5">Comentario:


                          <%
                          // :: numero documento / id-documento
                          try {
                            textHelper.setTipo("text");
                            textHelper.setNombre("CFolio.ANOTACION_DOCUMENTO_COMENTARIO");
                            textHelper.setCssClase("camposform");
                            textHelper.setId("CFolio.ANOTACION_DOCUMENTO_COMENTARIO");
                            textHelper.setReadonly( true );
                            textHelper.render(request,out);
                          }
                          catch( HelperException re ){
                            out.println("ERROR " + re.getMessage());
                          }

                          textHelper.setReadonly( false );
                          %>



                  </td>
                </tr>
                <!-- EL HELPER DE OFICINA EMPIEZA ACA -->
                    <jsp:include page="../correccion/HELPER_OFICINAS.jsp" flush="true" />
                <!-- EL HELPER DE OFICINA TERMINA ACA -->
                <!--<tr>
                  <td >

                <%--

                try {
                    oficinaHelper.render(request,out);
		}
                catch( HelperException re ) {
                   out.println("ERROR " + re.getMessage());
		}
		--%>


                  </td>
                </tr>-->

<!-- ojo: malformed xhtml segment -->
                <tr><td colspan="5">

<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

                </td>
                </tr>


                <tr>
                  <td width="20" ><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td colspan="5" align="left">Estado Anotaci&oacute;n </td>
                </tr>

                <tr>
                  <td>&nbsp;</td>
                  <td>



<!-- TAG: BEGIN-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<%--
<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesEstadoAnotacionMain"
  regionName="anotacion - estado anotacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->
--%>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG (inner) -->

            <table width="100%" >
                <tr>
                <td>

                 <table width="100%" class="camposform" style="border:0px;" align="left">


                    <tr>
                    <td width="130">Estado </td>
                    <td>


<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionEstado"
  regionName="estado de anotacion"
  regionDescription="Estado de anotacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

					<% try {
							estadoFolioHelper.setNombre(AWModificarFolio.ANOTACION_ESTADO);
							estadoFolioHelper.setId(AWModificarFolio.ANOTACION_ESTADO);
							estadoFolioHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:estado/idEstadoAn"
                    fieldName="Anotacion - Estado "
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
                </table>


                </td>
                </tr>
            </table>

<!-- TAG: END-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<%--
</xRegionTemplate:DisplayPoint>
--%>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

                  </td>

                </tr>




              </table>


<!-- :block -->



<!-- -->
<!-- -->

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Especificaci&oacute;n</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
					<tr>
						<td>&nbsp; <!-- Fecha de Radicaci&oacute;n--></td>
                        <td><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                                <%--
                               	MostrarFechaHelper fecha = new MostrarFechaHelper();

                                gov.sir.core.negocio.modelo.Turno turno
                                 = (gov.sir.core.negocio.modelo.Turno) session.getAttribute(WebKeys.TURNO);

                                java.util.Date fechaRadicacion =  (null!=turno.getSolicitud())?(turno.getSolicitud().getFecha()):(null);
                                try {
                                  fecha.setDate(fechaRadicacion);
                                  fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                                  fecha.render(request,out);
                                }
                                catch( Exception e ){

                                }

                               --%>

                              <%%>&nbsp;

                              </td>
                            </tr>
                        </table></td>
					</tr>
				</table>
  				<table width="100%" class="camposform"">
				  <tr>
					<td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">&nbsp;</td>
                                        <td width="240">

<!-- TAG: BEGIN-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesValorActoMain"
  regionName="anotacion - valor acto"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG (inner) -->
                                        <table class="camposform" style="border:0px;" width="210" cellpadding="0" cellspacing="0" >
                                          <tr>
                                            <td colspan="2"><span style="font-size:xx-small;">&nbsp;</span></td>
                                          </tr>
                                          <tr>

					<td width="40" valign="top">Valor</td>
					<td width="170" valign="bottom">


						<%

                                                try {
							textHelper.setTipo("text");
							textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setEditable(true);
							textHelper.setSize("20");
							textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
							textHelper.render(request,out);
						}
                                                catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:valor"
                    fieldName="Anotacion - (Especificacion) - Valor "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>



					</td>

                                          </tr>
                                        </table>

<!-- TAG: END-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
                                        </td>

                                        <td width="100%" valign="top">

<!-- TAG: BEGIN-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesEspecificacionNaturalezaJuridicaMain"
  regionName="anotacion - especificacion / naturaleza juridica"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG (inner) -->
                                          <table class="camposform" style="border:0px;" width="250" cellpadding="0" cellspacing="0">
                                            <tbody>



                                            <tr>
                                                <td width="180" colspan="7"><span style="font-size:xx-small;">Naturaleza Jur&iacute;dica </span></td>
                                            </tr>
                                            <tr>
                                                <td width="25" align="justify" class="camposformnoborder">ID &nbsp;
                                                </td>
                                                <td width="100">
                                                    <input name="natjuridica_id" type="hidden" id="natjuridica_id" value="" />
                                                    <input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value=""/>
<%--
                                                        /**
                                                        * @Autor: Carlos Torres
                                                        * @Mantis: 0012705
                                                        * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                        * @Descripcion: Se agrega la variable versionNatJuridica para optener la version de la naturalesa juridica seleccionada
                                                        */
                                                        --%>
                                                    <input name="natjuridica_ver" type="hidden" id="natjuridica_ver" value="">
                                                    <input name="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" type="hidden" id="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" value="<%=session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)%>">
                                                        <%

                                                        try {
                                                                textHelper.setFuncion("");
                                                                textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                                textHelper.setCssClase("camposformtext");
                                                                textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                                textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('"+AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION+"')\"");
                                                                //textHelper.setFuncion("onchange=\"document.ANOTACION."+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM+".value=''\"");

                                                                textHelper.setSize("3");
                                                                textHelper.render(request,out);

                                                                textHelper.setFuncion("");

                                                        }
                                                        catch(HelperException re){
                                                                out.println("ERROR " + re.getMessage());
                                                        }

                                                        %>



                                                </td>


                                        <td width="80" align="right" class="camposformnoborder">Descripci&oacute;n &nbsp;&nbsp;</td>
					<td width="120">

						<table border="0" cellpadding="0" cellspacing="0" width="100%">
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

                                                                // :: descripcion-nat juridica
									String descripcionNaturalezaJuridica = 
										(String)session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									int tamanioTexto = descripcionNaturalezaJuridica!=null?descripcionNaturalezaJuridica.length() : 0;
									String numeroFilas = tamanioTexto>65?
																tamanioTexto>130?
																"3":"2"
															:"1";
									
                                                                try {
									textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									textAreaHelper.setCols("65");
									textAreaHelper.setRows(numeroFilas);
									textAreaHelper.setReadOnly(true);
									textAreaHelper.setCssClase("camposformtext");
									textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									textAreaHelper.render(request,out);
                                                                        textAreaHelper.setReadOnly(false);

								}
                                                                catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
                                                                %>
                                                              </td>
                                                              
                                                              
                                                              <td>
                                                                  <!--<a href="javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacion=false&calificacion=false','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>-->

									<a href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=false&calificacion=false&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>&nbsp;																						
                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica"
                    fieldName="Anotacion - NaturalezaJuridica "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>



                                                                </td>
                                                                
                                                               <td class="camposformnoborder">
                                                                                                <% 
                                                                                                    String validarModalidad = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                                                                    boolean activarModalidad = false;
                                                                                                    if(validarModalidad != null && (validarModalidad.equals("0125") || validarModalidad.equals("0126"))){
                                                                                                    out.println("Modalidad");
                                                                                                    activarModalidad = true; 
                                                                                                    }
                                                                                                %>
                                                                                            </td>
                                                                                      <td> 
                                                                                          <%
                                                                                              try { 
                                                                                                  if(activarModalidad){
                                                                                            tipoModalidadHelper.setNombre(AWModificarFolio.ANOTACION_MODALIDAD); 
                                                                                            tipoModalidadHelper.setId(AWModificarFolio.ANOTACION_MODALIDAD); 
                                                                                            if(modalidadOld != null){
                                                                                            tipoModalidadHelper.setSelected(modalidadOld);
                                                                                            }
                                                                                            tipoModalidadHelper.render(request, out);
                                                                                                  }
                                                                                              }catch(HelperException re){ 
                                                                                                                            out.println("ERROR " + re.getMessage()); 
                                                                                                                    }%>
                                                                                      </td>

                                                                <td>&nbsp;</td>
							</tr>
			</table>


					</td>

                                            </tr>
                                           </tbody>

                                          </table>

<!-- TAG: END-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

                                        </td>


                                  </tr>


                                  <!--
                                  -->

                                  <tr>

                                    <td>&nbsp;</td>














              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                  <td>Comentario</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>

<!-- TAG: BEGIN-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesEspecificacionComentarioMain"
  regionName="anotacion - especificacion / comentario"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG (inner) -->
                    <%
                    // :: comentario
                    try {
                            textAreaHelper.setNombre(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
                            textAreaHelper.setCols("125");
                            textAreaHelper.setRows("3");
                            textAreaHelper.setReadOnly(false);
                            textAreaHelper.setCssClase("camposformtext");
                            textAreaHelper.setId(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
                            textAreaHelper.render(request,out);
                    }
                    catch( HelperException re ){
                            out.println("ERROR " + re.getMessage());
                    }

                    %>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:comentario"
                    fieldName="Anotacion - Comentario "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>


                  <%--
                  try {
						textAreaHelper.setNombre(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
						textAreaHelper.setCols("60");
						textAreaHelper.setRows("3");
						textAreaHelper.setReadOnly(false);
						textAreaHelper.setCssClase("camposformtext");
						textAreaHelper.setId(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
						textAreaHelper.render(request,out);
						}catch(HelperException re){
					 		 	out.println("ERROR " + re.getMessage());
								}--%>


<!-- TAG: END-REGION (inner) -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
                  </td>
                </tr>
              </table>


              <hr class="linehorizontal" />
              <hr class="linehorizontal" />


              <table class="camposform">
                <tr>
                  <td >
                      <div>&nbsp;<!-- mal formedness: se lo debemos al helper -->
<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesCiudadanosMain"
  regionName="anotacion - ciudadanos"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
<%--
     NOTE: close this helper inside a table;
     maybe the layput is wrong.
--%>


<%--
El siguiente tag se configura para que no se despliegue, y
el resultado de la evaluacion de los permisos se envie como parametro
al helper para que el se encargue del despliegue condicional.
--%>
<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionesRolDeCiudadanosEnAnotacionMain"
  regionName="anotacion - rol ciudadano en anotacion"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
  renderEnabled="false"
/>	<!--   -->

<%

// se usa de esta forma para que no presente errores
Boolean param_RegionEnabled = (Boolean)pageContext.getAttribute( "isEnabledRegion" );
if( null == param_RegionEnabled  )
  param_RegionEnabled = Boolean.FALSE;

%>

</div>

	              <%

                      // :: personas asociadas
                      try {

                        // antes de llamar al draw del helper
                        // se configura el permiso de rol de ciudadanos en anotacion:

                        // deshabilitado por errores al editar y aplicar los permisos
                        // variosCiudadanosHelper.setDisabledTipoInter( !( param_RegionEnabled.booleanValue() ) );

			//TODO set ciudadanos
			variosCiudadanosHelper.render(request,out);
                      }
                      catch( HelperException re ) {
							re.printStackTrace();
                      }

                      %>

                      <div class="camposform" style="width:100%;">&nbsp;<!-- mal formedness: se lo debemos al helper -->

<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
                      </div>


                      </td>
                      </tr>
                  </table>
<br />
<span class="bgnsub" style="background:" >Salvedad: Anotacion
</span>
<!-- Salvedad Anotaci&oacute;n  -->
    <div style="width:50px;text-align:right;vertical-align:top;table-layout:inherit;" align="right">
    <a onclick="new Effect.SlideDown('demo_source'); return false;" href="#">[ayuda]</a>
  </div>

                  <div id="demo_source" style="display: none; width:40%;" onclick="new Effect.SlideUp(this);">
                   <span style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;font-size:xx-small;">
                    Al terminar de editar los datos de la anotacion,
                    guarde los cambios; Los datos de la salvedad los
                    puede adicionar a continuacion.
                   </span>
                  </div>
                  <br />
    <table width="100%" class="camposform"  >
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
                   local_TextHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID );
                   local_TextHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID );
                   local_TextHelper.setTipo( "hidden" ); // hidden
                   local_TextHelper.setCssClase("camposformtext");
                   local_TextHelper.render( request, out );

                 }
                 catch( HelperException re ) {
		  out.println( "ERROR " + re.getMessage() );
                 }



                 %>
                 <% // text
                          try {
 		                  textAreaHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION );
                  		  textAreaHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION );
 		                  textAreaHelper.setCols( "100" );
 		                  textAreaHelper.setRows( "7" );
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

<%--
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Salvedad Anotaci&oacute;n</td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
  </tr>
</table>
--%>

<%--
<table width="100%" class="camposform">
  <tr>
    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
    <td>Salvedad</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>

            <%
            try {
                    textAreaHelper.setNombre(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
                    textAreaHelper.setCols("125");
                    textAreaHelper.setRows("4");
                    textAreaHelper.setCssClase("camposformtext");
                    textAreaHelper.setId(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
                    textAreaHelper.render(request,out);
            }
            catch( HelperException re ){
                    out.println("ERROR " + re.getMessage());
            }
            %>

    </td>
  </tr>
</table>
--%>




<br />



        <table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.ACEPTAR_EDICION_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="150" height="21" border="0"/></a></td>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.CANCELAR_EDICION_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"/></a></td>
			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.CONVERTIR_ANOTACION_CANCELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_convertir_canceladora.gif" width="190" height="21" border="0"/></a></td>
            <%if(modificaDefinitiva){ %>
            	<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
				<td width="150"><a href="javascript:cambiarAccion('<%=AWModificarFolio.SEGREGAR%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_segregar.gif" width="150" height="21" border="0"/></a></td>
            <%} %>
            <td>&nbsp;</td>
          </tr>
        </table>

              </td>
          </tr>
        </table>
         </td>
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      </tr>
      <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">

      </td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      </tr>
      <tr>
        <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
        <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
      </tr>
    </table>

	<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="0">

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
</form>
