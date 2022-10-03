<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionesCancelarHelper"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Cancelacion"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionCancelacionHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.LLaveMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="java.util.*"%>

<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="java.text.*"%>

<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%@taglib prefix="xRegionTemplate" uri="/xRegionTemplateTags" %>
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- Edicion anotacion-canceladora modulo:correcciones -->
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
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    TextHelper textHelper = new TextHelper();

    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
    
    Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);


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
/*    variosCiudadanosHelper.setPropertiesHandly(
        numFilas
      , tiposID
      , AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION
      , AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION
      , AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_CANCELACION
      , AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_CANCELACION
      , lciudadanos
      , AWCalificacion.VALIDAR_CIUDADANO_CANCELACION
      , "ANOTACION"
    );*/
    variosCiudadanosHelper.setPropertiesHandly(
        numFilas
      , tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica
      , AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION
      , AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION
      , AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION
      , AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION
      , lciudadanos
      , AWCalificacion.VALIDAR_CIUDADANO_EDICION_CANCELACION
      , "ANOTACION"
    );
    variosCiudadanosHelper.setAccionUltimosPropietarios(AWCalificacion.GET_ULTIMOS_PROPIETARIOS);
    variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
	request.getSession().setAttribute("paramVistaAnterior","EDITAR_CANCELACION_CORRECCIONES");



    // variosCiudadanosHelper.setMostrarListaCiudadanos( false );
    variosCiudadanosHelper.setEdicionCiudadanoSobreTodosFolios_FlagEnabled( true );
    variosCiudadanosHelper.setEdicionCiudadanoSobreTodosFolios_JsHandler( "javascript:jsHandler_DoEdicionCiudadanoSobreTodosFolios" );

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

    // recuperar valores de regionManager de la sesion
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


function cambiarAccion(text) {
	document.ANOTACION.ACCION.value = text;
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
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.getElementById('version').value=valor+"_OFICINA_VERSION";
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
         /**
         * Fecha: 18-08-2011
         * Autor: Guillermo Cabrera
         * Propósito: Al editar una anotación canceladora, no estaba cargando el popup para seleccionar
         * la naturaleza juridica.
         * Se modifica la función ya que se estaba generando mal la url al adicinarle la fecha del documento
         * que para este caso no se necesita.
         * Se setea en la sesion el atributo cancelar en true "session.setAttribute("cancelar", "true")" para que solo
         * se carguen las naturalezas juridicas de cancelación.
         * Mantis: 0008593: Acta - Requerimiento No 029_151 - Error al agregar naturaleza al folio.
         ***/
	<%session.setAttribute("cancelar", "true"); %>
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

%>

      <form action="modificacion.do" name="RELOADDATAFORM" id="RELOADDATAFORM" method="post">
        <input type="hidden" name="RELOAD_DATA" id="RELOAD_DATA" value="" />
        <input type="hidden" name="SOURCE_PAGE" id="SOURCE_PAGE" value="<%=param_SourcePage%>" />
        <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIOEDIT_RELOADBYUPDATECIUDADANO_EDITANOTACIONNORMAL_PAGERENDERING_PROCESS_ACTION %>" />

      </form>




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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Corregir Anotaci&oacute;n</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <%--
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    --%>
    <td width="12" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral tdtablaanexa02">

<%-- sof:block:: choose anotation --%>

<br />

<%

	List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
	session.setAttribute("listanat",grupoNaturalezas);

	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	String vistaActual;
	//AnotacionesCancelarHelper anot=new AnotacionesCancelarHelper();
	//AnotacionCancelacionHelper anotacionesModificacionHelper = new AnotacionCancelacionHelper();
	//System.out.println("*************************************");
		//System.out.println("************************CORRECC_FOLIO_EDIT*************");
		//System.out.println("*************************************");


	String num= (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL);

	request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL_CANCELACION, num);


	Boolean refresco = (Boolean)request.getSession().getAttribute(AWCalificacion.HAY_REFRESCO);
	String idAnotacionCancelada="";
	String posicion="";
	String t=(String)session.getAttribute("ESCOGER_ANOTACION_CANCELACION");

	if(t!=null){
		//System.out.println("ordenAnotacion recuperado de la session valor = "+ t);
		idAnotacionCancelada=t;
	}
	if(refresco==null){
		List anotacionesTemporales = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
		if(anotacionesTemporales==null){

			//System.out.println("OJO ENTRO EN EL IF DE ANOTACIONES TEMPORALES.");
			anotacionesTemporales = new ArrayList();
		}

		Iterator iat= anotacionesTemporales.iterator();
		Anotacion a=null;
		for(;iat.hasNext();){
			Anotacion temp= (Anotacion) iat.next();
			if(temp.getOrden().equals(num)){
				a=temp;
			}
		}

		String ultimavista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		request.getSession().setAttribute("ULITIMA_VISTA_TEMPORAL", ultimavista);

		if(a!=null){

			Cancelacion c ;

			//iniciando en el session los valores de la anotacion correspondiente
			List anotacionesCanceladas= (List)a.getAnotacionesCancelacions();
			if(anotacionesCanceladas!= null && anotacionesCanceladas.size()>0){
				c = (Cancelacion) anotacionesCanceladas.get(0);
			}else{
				c= new Cancelacion();
			}

			posicion = c.getCancelada().getOrden();
			idAnotacionCancelada= c.getCancelada().getIdAnotacion();
			String idNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
			String nomNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                        /**
                        * @Autor: Carlos Torres
                        * @Mantis: 0012705
                        * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                        * @Descripcion: Se asigna valores a campos en el formulario
                        */
                        String version = (String)request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
			if(idNat==null){
				request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,a.getNaturalezaJuridica().getIdNaturalezaJuridica());
				request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,a.getNaturalezaJuridica().getNombre());
                                /**
                                * @Autor: Carlos Torres
                                * @Mantis: 0012705
                                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                * @Descripcion: Se asigna valores a campos en el formulario
                                */                        
                                request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER,String.valueOf(a.getNaturalezaJuridica().getVersion()));
			}
			if( request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION) ==null ){
				request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,a.getAnotacionesCiudadanos());
			}
			request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, Double.toString(a.getValor()));
			request.getSession().setAttribute(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION,a.getComentario());
			request.getSession().setAttribute(AWCalificacion.ANOTACION_A_EDITAR, a);
		}

	}else{
		Anotacion a = null;
		a= (Anotacion)request.getSession().getAttribute(AWCalificacion.ANOTACION_A_EDITAR);
		Cancelacion c ;

		if(a!=null){
			List anotacionesCanceladas= (List)a.getAnotacionesCancelacions();

			if(anotacionesCanceladas!= null && anotacionesCanceladas.size()>0){
				c = (Cancelacion) anotacionesCanceladas.get(0);
			}else{
				c= new Cancelacion();
			}

			posicion = c.getCancelada().getOrden();
			idAnotacionCancelada= c.getCancelada().getIdAnotacion();
		}
	}
	request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
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
	}

	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;
	String nomFormaCancelacion="ANOTACION2"; //"FORMA_CANCELACION"; // ="ANOTACION";//;
	String nomOrdenCancelada="ESCOGER_ANOTACION_CANCELACION"; //"NOM_ORDEN_CANCELADA";//"ESCOGER_ANOTACION_CANCELACION";//

        // los campos que recibiran los valores
        // al efectuar el llamado
        // a notify
	String targetFormId       ="ANOTACION"; //"FORMA_CANCELACION"; // ="ANOTACION";//;
	String targetFormFieldId ="ESCOGER_ANOTACION_CANCELACION"; //"NOM_ORDEN_CANCELADA";//"ESCOGER_ANOTACION_CANCELACION";//
%>


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

        var ANOTACIONCANCELACIONEDIT_SELECTANOTACIONCANCELADAID_JS_FORMID      = "<%=targetFormId%>";
        var ANOTACIONCANCELACIONEDIT_SELECTANOTACIONCANCELADAID_JS_FORMFIELDID = "<%=targetFormFieldId%>";

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">


   function js_OnChangeValue( formName, fieldName, fieldValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( fieldName, fieldValue );

   }

   function notify( value ){
     js_OnChangeValue(
         ANOTACIONCANCELACIONEDIT_SELECTANOTACIONCANCELADAID_JS_FORMID
       , ANOTACIONCANCELACIONEDIT_SELECTANOTACIONCANCELADAID_JS_FORMFIELDID
       , value
     );
     return void(0);
   }

</script>




<%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
                    Escoja la anotaci&oacute;n que desea cancelar </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

--%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >

<table width="100%" class="camposform" style="border:0px;">
<tr>
<td> Anotaci&oacute;n Tipo Canceladora </td>
<td width="15">&nbsp;
</td>
</tr>
</table>

    </td>
  </tr>
</table>


<%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <!--
                  <td width="12">&nbsp;</td>
                  -->
                  <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:20px;padding-top:10px;padding-right:20px;padding-bottom:10px;" >

                    <table width="100%" class="camposform" style="border:0px;">
                      <tr>

                        <td> Anotaci&oacute;n a cancelar </td>
                  <td width="15">

                  <%=idAnotacionCancelada%>

                  </td>

                      </tr>
                    </table>

                  </td>
                  <!--
                  <td width="16" >&nbsp;</td>
                  <td width="16" >&nbsp;</td>
                  -->
                </tr>
              </table>
--%>
<%-- --%>
<%-- Bug 3562 part2 --%>
<%--



              <table width="100%" class="camposform" >
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td style="font-weight:bold;">Anotaciones</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;

			   <%
                           try{
			   			//setear atributos del folio.
			   			mFolio.setFolio(f);
			   			mFolio.setTipoMostrarFolio(CTipoMostrarFolioHelper.TIPO_COMPRIMIDO);
			   			mFolio.setNomOrdenCancelada(nomOrdenCancelada);
			   			mFolio.setIdAnotacionCancelada(idAnotacionCancelada);
			   			mFolio.setOrdenCancelada(posicion);

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

			   			mFolio.setNombreAccionFolio("modificacion.do");
			   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
			   			mFolio.setNombreAncla("ancla");
			   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
			   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
			   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
			   			mFolio.setNombreAccionCancelacion("modificacion.do");
			   			mFolio.setNombreFormaCancelacion(nomFormaCancelacion);
			   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CALIFICACION");
			   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
			   			mFolio.setNombreOcultarFolio("O_FOLIO");
			   			mFolio.setNombrePaginador(lla.getNombrePaginador());
			   			mFolio.setNombreResultado(lla.getNombreResultado());
			   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
			   			mFolio.setPaginaInicial(0);
			   			mFolio.setVistaActual(vistaActual);
			   			//datos a mostrar encabezado
			   			mFolio.NoMostrarEncabezado();
			   			mFolio.render(request, out);
				   }
                                   catch(HelperException re){
					 	out.println("ERROR " + re.getMessage());
                                   }
                                   catch(Exception e){
					 	out.println("ERROR " + e.getMessage());
					 	e.printStackTrace();
                                   }

                                   %>


              </td>
              <td>&nbsp;</td>
            </tr>
           </table>



--%>

<%-- eof:block:: --%>
    <td width="12" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td width="12">&nbsp;</td>
</tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
      <form action="modificacion.do" name="ANOTACION" id="ANOTACION" method="post">

        <!-- valores de seleccion de anotacion canceladora transferidos a esta forma -->		
        <input type="hidden" name="<%=nomOrdenCancelada%>" id="<%=nomOrdenCancelada%>"

          <%

          out.println( "<input type='hidden' name='" + nomOrdenCancelada + "' " + "id='" + nomOrdenCancelada + "' " );
          String param_IdAnotacionCanceladora = (String) session.getAttribute( nomOrdenCancelada );
          if( null != param_IdAnotacionCanceladora ) {
            out.println( "value='" + param_IdAnotacionCanceladora + "'" );
          }
          out.println( " " );
          out.println( "/>" );

          %>




        <!-- -->







		<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">
	    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">
   	    <input type="hidden" name="<%=AWModificarFolio.AGREGAR_ANOTACION%>" value="<%=agregarAnotacion!=null?agregarAnotacion:""%>">
	    <input type="hidden" name="<%=AWModificarFolio.POSICIONANOTACION%>" value="<%=posicionAnotacion!=null?posicionAnotacion:""%>">
            <input type="hidden" id="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" name="<%=AWModificarFolio.ANOTACION_FECHA_RADICACION %>_hidden" value="<%=session.getAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION) %>" />
            <input type="hidden" id="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" name="<%=AWModificarFolio.FOLIO_FECHA_APERTURA %>" value="<%=session.getAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA) %>"/>

           <input type="hidden" name="POSICION" value="" />
           <input type="hidden" name="VER_SALVEDAD" value="" />
           <input type="hidden" name="SECUENCIA" value="-1" />


		<input name="id_depto" type="hidden" id="id_depto" value="">
		<input name="nom_depto" type="hidden" id="nom_depto" value="">
		<input name="id_munic" type="hidden" id="id_munic" value="">
		<input name="nom_munic" type="hidden" id="nom_munic" value="">
		<input name="id_vereda" type="hidden" id="id_vereda" value="">
		<input name="nom_vereda" type="hidden" id="nom_vereda" value="">
		<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):""%>">

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
                  <td colspan="5">Anotaci&oacute;n: Datos B&aacute;sicos </td>
                </tr>
                <tr>
                	<td >&nbsp;</td>
                	<td colspan="5">



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
                  <td colspan="5">Anotacion: Radicacion manual </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>


                  <td>
                    <table border="0"  width="100%" class="camposform">
                      <tr>

<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                        <td>


                    <table width="100%" class="camposform">
                      <tr>

                        <td width="150">Núm. radicación</td>
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


                        <td>&nbsp;
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
                  <td>Documento: Datos B&aacute;sicos </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
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
                  <td>Documento: Oficina de Procedencia

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
                  <td width="400" >Comentario:

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
                <tr>
                  <td>

                <%

                try {
                    oficinaHelper.render(request,out);
		}
                catch( HelperException re ) {
                   out.println("ERROR " + re.getMessage());
		}
		%>


                  </td>
                </tr>











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
                                        <td width="210">

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
                                          <table class="camposform" style="border:0px;" width="200" cellpadding="0" cellspacing="0">
                                            <tbody>



                                            <tr>
                                                <td width="180" colspan="7"><span style="font-size:xx-small;">Naturaleza Jur&iacute;dica </span></td>
                                            </tr>
                                            <tr>
                                                <td width="25" align="justify">id
                                                </td>
                                                <td width="100">
                                                    <input name="natjuridica_id" type="hidden" id="natjuridica_id" value="" />
                                                    <input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value=""/>

                                                                 <%--/**
                                                                                * @Autor: Carlos Torres
                                                                                * @Mantis: 0012705
                                                                                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                                                * @Descripcion: Se agrega campos al formulario
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
                                                                // textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('"+AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION+"')\"");
                                                                textHelper.setFuncion("onchange=\"document.ANOTACION."+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM+".value=''\"");

                                                                textHelper.setSize("3");
                                                                textHelper.render(request,out);

                                                                textHelper.setFuncion("");

                                                        }
                                                        catch(HelperException re){
                                                                out.println("ERROR " + re.getMessage());
                                                        }

                                                        %>
                                                </td>


                                        <td width="80" align="right">Descripci&oacute;n</td>
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
								<td align="right">
								<%

                                                                // :: descripcion-nat juridica

                                                                try {
									textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									textAreaHelper.setCols("65");
									textAreaHelper.setRows("1");
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
								<!--<td><a href="javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacion=true&calificacion=false&soloCancelacion=true','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>-->
								<% 
								boolean cancelacion = session.getAttribute(AWModificarFolio.CONVERTIR_CANCELADORA_ESTANDAR) != null;
								%>	
								<td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=<%=cancelacion?"true":"false"%>&calificacion=false&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>

                  <%-- [bug-03541]: sof@changes-mark --%>
                  <xRegionTemplate:DeltaPoint
                    fieldId="field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica"
                    fieldName="Anotacion - NaturalezaJuridica "
                    fwdDiffComparisonResultsMap="<%=(java.util.Map)local_FwdDiffComparisonResultsMap %>"
                    showChangesEnabled="disabled"
                  />
                  <%-- eof@changes-mark --%>

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
			if(turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_CORRECCIONES)){
				variosCiudadanosHelper.setProceso(CProceso.PROCESO_CORRECCIONES);
			}
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

<%--
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Salvedad Anotaci&oacute;n</td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
  </tr>
</table>

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








        <table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.GRABAR_EDICION_CANCELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="150" height="21" border="0"/></a></td>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.CANCELAR_EDICION_CANCELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"/></a></td>
			<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
                			<td width="150">N&uacute;mero anotaci&oacute;n <!-- orden --></td>
                			<td align="right" width="200">

<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_AnotacionCanceladoraEditar_SelectItems"
  regionName="anotacion-canceladora - botón"
  regionDescription="Botón para convertir la anotacion a Estándar"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
			<td width="140"><a href="javascript:cambiarAccion('<%=AWModificarFolio.CONVERTIR_ANOTACION_ESTANDAR%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_convertir_estandar.gif" width="190" height="21" border="0"/></a></td>
<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

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
