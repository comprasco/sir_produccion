<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>

<%@page import="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants" %>

<%@page import="gov.sir.core.web.acciones.correccion.AWRevision" %>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();
	
    ListaElementoHelper redirHelper = new ListaElementoHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    TextHelper txtHelper = new TextHelper(); 
	NotasInformativasHelper helper = new NotasInformativasHelper();
	List solicitudesPadre = solicitud.getSolicitudesPadres();
	
	Vector imagenes = new Vector();
	for(int i=0;i<solicitud.getSolicitudFolios().size();i++){
		Imagen img = new Imagen();
		img.setNombre("btn_mini_verdetalles.gif");
		img.setHeight("13");
		img.setWidth("35");
		img.setFuncion("verAnotacion('consultar.folio.do?POSICION="+i+"','Folio','width=900,height=450,scrollbars=yes','"+i+"')");
		imagenes.add(img);
	}
	
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);
	session.setAttribute(WebKeys.VISTA_ANTERIOR_AUXILIAR,session.getAttribute(SMARTKeys.VISTA_ACTUAL));
	        /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
        boolean activo = true;
	Turno turnoAnterior = solicitud.getTurnoAnterior();
	if(turnoAnterior==null){
		turnoAnterior = new Turno();
	}else
        {
            if(turnoAnterior.getIdProceso()==6)
            {
                activo = false;
            }
        }
	Ciudadano ciudadano = solicitud.getCiudadano();
	if(ciudadano==null){
		ciudadano = new Ciudadano();
	}

	List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
   	if(docs == null){
		docs = new Vector();
    }

    Iterator it = docs.iterator();
    String tipdoc = "DOCUMENTO IDENTIDAD";
    while (it.hasNext()) {
       ElementoLista el = (ElementoLista) it.next();

       if(el.getId().equals(ciudadano.getTipoDoc())){
       		tipdoc = el.getValor();
       }
    }

	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();

	Iterator is = matriculas.iterator();
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);

  //IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
		idCirculo = idCirculo + "-";
	}

%>

<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- cargar el conjunto de permisos cuando se van a modificar --%>

<%

  java.util.List modelPermisosList = null;

  if( turno.getSolicitud() instanceof SolicitudCorreccion ){
      SolicitudCorreccion solicitudCorreccion
	= (SolicitudCorreccion) turno.getSolicitud();
      modelPermisosList = solicitudCorreccion.getPermisos();
  }

  if (turno.getSolicitud() instanceof SolicitudRegistro){
    SolicitudRegistro solicitudCorreccion
      = (SolicitudRegistro) turno.getSolicitud();
    modelPermisosList = solicitudCorreccion.getPermisos();
  }

%>

<%-- ++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
<%-- cargar el conjunto de datos antiguo sistema cuando se van a modificar --%>

<%

  gov.sir.core.negocio.modelo.DatosAntiguoSistema local_DatosAntiguoSistema;

  local_DatosAntiguoSistema = null;

  if( turno.getSolicitud() instanceof SolicitudCorreccion ){
      SolicitudCorreccion solicitudCorreccion
	= (SolicitudCorreccion) turno.getSolicitud();
      local_DatosAntiguoSistema = solicitudCorreccion.getDatosAntiguoSistema();
  } // if


%>


<%

  if( null == session.getAttribute( "LOAD_EVENT_PARAM" ) ){

    // load permisos
    session.setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, modelPermisosList );
    AWRevision.loadState_Permisos( request );

    // load datos antiguo sistema
    // load permisos
    session.setAttribute( "PARAM__MODEL_DATOSANTIGUOSISTEMA" , local_DatosAntiguoSistema );
    AWRevision.loadState_DatosAntoguoSistema( request );


    // disable attr
    session.setAttribute( "LOAD_EVENT_PARAM", Boolean.TRUE );
  }



%>








<script type="text/javascript">
function cambiarAccion(text) {
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.submit();
}
function eliminarMatriculas() {
    document.ELIMINAR_MAT.ACCION.value = 'ELIMINAR';
    document.ELIMINAR_MAT.submit();
}
function cambiarSeleccion() {
    if(document.ELIMINAR_MAT.seleccionado.checked == true){
	    setAllCheckBoxes('ELIMINAR_MAT', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.ELIMINAR_MAT.seleccionado.checked == false){
    	setAllCheckBoxes('ELIMINAR_MAT', 'ELIMINAR_CHECKBOX', false);
    }
}

function verAnotacion(nombre,valor,dimensiones,pos){
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}


</script>

<!--  + + + + + + + + + + + + + + +  declaracion de helpers + + + + + + + + +  -->
<%


      // helper para configurar los permisos que el analista puede configurar (moved to region)
      // TablaMatriculaHelper editPermisosHelper
      // = new TablaMatriculaHelper();

      java.util.List vPermisosList = (java.util.List)session.getServletContext().getAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION);

      session.setAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION, vPermisosList );

%>

<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->

<!--  + + + + + + + + + + + + + + +  declaracion de helpers + + + + + + + + +  -->
<%
/*

    // helper para insertar datos relacionados con antiguo sistema

	// helper para insertar datos relacionados con antiguo sistema

	AntiguoSistemaHelper ASHelper = new AntiguoSistemaHelper();

	OficinaHelper ofHelper=new OficinaHelper();
	ofHelper.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS);
	ofHelper.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
	ofHelper.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS);
	ofHelper.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);

    ASHelper.setOficinaHelper(ofHelper);
	// helper para listar los datos de antiguo sistema en sesion
	// no necesario

	String ocultarAntSist = request.getParameter( WebKeys.OCULTAR );

	// descomentar si se quiere que la region se oculte
	if( null == ocultarAntSist ){

		ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);

		if( null == ocultarAntSist ){
			ocultarAntSist = "TRUE";
		}
	}
	else {
	    ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		session.setAttribute(WebKeys.OCULTAR,ocultarAntSist);
	}
     */


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

                        ash.setLocal_OficinasHelperCambioNombreFormularioEnabled( true );
                        ash.setLocal_OficinasHelperCambioNombreFormularioValue( "CORRECCION" );


%>

<%

	// helper para listar los datos de antiguo sistema en sesion
	// no necesario

	String ocultarAntSist = request.getParameter( WebKeys.OCULTAR );

	// descomentar si se quiere que la region se oculte
	if( null == ocultarAntSist ){

		ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);

		if( null == ocultarAntSist ){
			ocultarAntSist = "TRUE";
		}
	}
	else {
	    ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		session.setAttribute(WebKeys.OCULTAR,ocultarAntSist);
	}

%>


























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
	var PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION = "<%=AwCorrecciones_Constants.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION%>";
</script>

<script type="text/javascript">

   function js_OnRepeaterAction( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     // alert( findObj('FORMNAME').ACCION.value );
     htmlForm.submitForm();

   }

   // TODO: cambiar por browser compatibility

	function js_OnRegionAntiguoSistemaAction_ShowHide( formName, actionValue, param_ShowHide ){

		var htmlForm;
		htmlForm = new LocalForm( formName );
		// htmlForm.setFormAction( 'correccion.confrontacion.view' ); //correccion.confrontacion.view
		htmlForm.setValue( actionField, actionValue );
		 // alert( findObj('FORMNAME').ACCION.value );
		htmlForm.setValue( 'OCULTAR', param_ShowHide );
		htmlForm.submitForm();

	}

	function oficinas(nombre,valor,dimensiones)
	{
		document.CORRECCION.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
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
	    popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
	}

	function setTipoOficina(){
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

	}
	
	function check_all(myElement){				
		if(document.getElementsByName('<%=WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION%>')){
			var fmobj = document.getElementsByName('<%=WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION%>');			
			for (var i=0;i<fmobj.length;i++) {				
			    var e = fmobj[i];
			    if ( (e.name != 'allbox') && (e.type=='checkbox') && (!e.disabled) ) {
			      	e.checked = myElement.checked;
			    }
	  		}
	  	}
	}
/*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/       
     function cargarTestamento(text,folio,editable) {
        document.CORRECCION.action = 'modificar.testamento.do';
        document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
        document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = folio;
        document.CORRECCION.submit();
    }
</script>


<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisi&oacute;n y An&aacute;lisis</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
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
              <br>


            <form action="revisionCorreccion.do" method="post" name="ELIMINAR_MAT" id="ELIMINAR_MAT">
            <input type="hidden" name="ACCION" value="ELIMINAR">

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td align='right' width='20' class="bgnsub"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>&nbsp;</td>
                  <td width="36" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%" class="camposform">
                  <tr>
	                  <td>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                          tablaHelper.setImagenes(imagenes);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
					</td>
                </tr>
              </table>


            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Eliminar Seleccionadas</td>
                <td>
                  <a href="javascript:eliminarMatriculas()"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                </td>
              </tr>
            </table>


              </form>
            <P>&nbsp;</P>





        <table class="camposform" width="100%">

			  <script type="text/javascript">

			      // temporal javascript actions
					// for prevent double summit
			      function doSubmitForm_RevisionCorrecciones( actionId ) {
						document.ASOCIAR_MAT.ACCION.value = actionId; //'ASOCIAR_UNA_MATRICULA';
						document.ASOCIAR_MAT.submit();
					}

			  </script>



            <form action="revisionCorreccion.do" method="post" name="ASOCIAR_MAT" id="ASOCIAR_MAT">
            <input type="hidden" name="ACCION" value="ASOCIAR_UNA_MATRICULA">
		            <tr>
						<td width="20">
							<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td width="60%">N&uacute;mero de Matr&iacute;cula</td>

						<td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA);
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td>
	                    <td align="right">
	                      	<table border="0" cellpadding="0" cellspacing="2" class="camposform">
	                      		<tr>
	                      			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	                      			<td>Agregar Matr&iacute;cula</td>
	                      			<td><%--
                                                	        /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
                                                --%>
                                                    <%if(activo){%>
												<a href="javascript:doSubmitForm_RevisionCorrecciones( 'ASOCIAR_UNA_MATRICULA' );">
													<img alt="[go]" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" />
												</a>
                                                                                            <%}else{%>
                                                                                                <a>
													<img alt="[go]" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" />
												</a>
                                                                        
                                                                                            <%}%>
											</td>
	                      		</tr>
	                      	</table>
	                    </td>
                	</tr>
            </form>

            <form action="revisionCorreccion.do" method="post" name="ASOCIAR_RAN" id="ASOCIAR_RAN">
            <input type="hidden" name="ACCION" value="ASOCIAR_UN_RANGO">
	                <tr>
						<td width="20">
							<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td width="60%">Rango de Matr&iacute;culas</td>

						<td>
							<table>
								<tr><td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA_RR);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA_RR);
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td></tr>
								<tr><td>
                                        <table class="camposformnoborder">
                                            <td> <%=idCirculo%> </td>
                                            <td>
                                                 <%
                                                txtHelper.setNombre(CFolio.ID_MATRICULA_RL);
                                                txtHelper.setCssClase("camposformtext");
                                                txtHelper.setId(CFolio.ID_MATRICULA_RL);
                                                txtHelper.setMaxlength("20");
                                                txtHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                                                txtHelper.setReadonly(false);
                                                txtHelper.render(request, out);
                                                %>
                                            </td>
                                        </table>
                                    </td></tr>
							</table>
						</td>
	                   	<td align="right">
	                   	  	<table border="0" cellpadding="0" cellspacing="2" class="camposform">
	                      		<tr>
	                      			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	                      			<td>Agregar Rango</td>
                                                <td>
                                                    <%--
                                                	        /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
                                                --%>
                                                    <%if(activo){%>
                                                    <input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" />
                                                    <% }else{ %>
                                                    <input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" disabled="disabled"/>
                                                    <%}%>
                                                </td>
	                      		</tr>
	                      	</table>
	                   	</td>
                </tr>
            </form>
            </table>




            <form action="revisionCorreccion.do" method="post" name="CORRECCION" id="CORRECCION">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="CONFIRMAR">
		    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">



              <br>
              <%--
                      /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
              --%>
             <% if(!activo){ %> 
               <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno de Testamento</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
                
              <table width="100%" class="camposform">
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de testamento</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdWorkflow():""%>&nbsp;</td>
                  <%--
                           /*
                            * @author Carlos Torres Urina
                            * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                            * @changed: se agregan boton para consultar testamento.
                            */
                  --%>
                  <td><input name="imageField" type="image" onclick="javascript:cargarTestamento('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.CARGAR_TESTAMENTO%>','<%=turnoAnterior.getSolicitud().getIdSolicitud() %>','NO');" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0" /></td>
                </tr>
              </table>	                        
                                    <%}else{%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Asociado</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Est&aacute; asociado a un turno ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de documento objeto de correcci&oacute;n</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdTurno():""%>&nbsp;</td>
                </tr>
              </table>
            <% } %>
              <br>

              <%
              if (solicitud instanceof SolicitudCorreccion){
              	SolicitudCorreccion solicitudCorreccion = (SolicitudCorreccion) solicitud;
              %>


              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>


              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>
                  <td class="campositem"><%=solicitudCorreccion.getDescripcion()!=null?solicitudCorreccion.getDescripcion():""%>&nbsp;</td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Comentario</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>
                  <td class="campositem"><%=solicitudCorreccion.getComentario()!=null?solicitudCorreccion.getComentario():""%>&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Correo </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correo.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Direcci&oacute;n de Correo</td>
                  <td width="45%" align='left' class="campositem"><%=solicitudCorreccion.getDireccionEnvio()!=null?solicitudCorreccion.getDireccionEnvio():""%>&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Derecho de Petici&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getDerechoPeticion()==true?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Correcci&oacute;n solicitada con anterioridad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.isSolicitadaAnteriormente()?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Inter&eacute;s Jur&iacute;dico</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getInteresJuridico()!=null?solicitudCorreccion.getInteresJuridico():""%></td>
                </tr>
              </table>

              <%
              }
              %>

              <!-- 
                  @author Cesar Ramirez
                  @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
              -->
              <%
              String solicitante_nombre = null;
              String solicitante_primer_apellido = null;
              String solicitante_segundo_apellido = null;
              String tipo_doc_solicitante = null;
              long num_doc_solicitante = 0L;
              boolean isDerivadoRegistro = false;
              if(turnoPadre!=null) {
                if(turno.getIdProceso() == 3 && turnoPadre.getIdProceso() == 6) {
                    List turnosHistoria = turno.getHistorials();
                    if (turnosHistoria != null && turnosHistoria.size() > 0) {
                        for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
                            TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
                            if(turnoAux.getFase().equals("SOLICITUD")) {
                                solicitante_nombre = turnoAux.getUsuario().getNombre();
                                solicitante_primer_apellido = turnoAux.getUsuario().getApellido1();
                                solicitante_segundo_apellido = turnoAux.getUsuario().getApellido2();
                                tipo_doc_solicitante = turnoAux.getUsuario().getTipoIdentificacion();
                                num_doc_solicitante = turnoAux.getUsuario().getNumIdentificacion();
                                isDerivadoRegistro = true;
                                break;
                            }
                        }
                    }
                }
              }
              %>

              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td width="211" class="campositem"><%=tipdoc%>&nbsp;</td>
                  <% } else { %>
                  <td width="211" class="campositem"><%=(tipo_doc_solicitante==null)?"&nbsp;":tipo_doc_solicitante + "&nbsp;"%></td>
                  <%}%>
                  <td width="146">N&uacute;mero</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                  <% } else { %>
                  <td width="212" class="campositem"><%=(num_doc_solicitante == 0)?"&nbsp;":num_doc_solicitante + "&nbsp;"%></td>
                  <%}%>
                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_primer_apellido!=null?solicitante_primer_apellido:""%>&nbsp;</td>
                  <%}%>
                  <td>Segundo Apellido</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_segundo_apellido!=null?solicitante_segundo_apellido:""%>&nbsp;</td>
                  <%}%>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem"><%=solicitante_nombre!=null?solicitante_nombre:""%>&nbsp;</td>
                  <%}%>
                  <td>Teléfono</td>
                  <%
                  /**
                   * @author Cesar Ramirez
                   * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                   **/
                  if(!isDerivadoRegistro) {
                  %>
                  <td class="campositem"><%=ciudadano.getTelefono()!=null?ciudadano.getTelefono():""%>&nbsp;</td>
                  <% } else { %>
                  <td class="campositem">&nbsp;</td>
                  <%}%>
                </tr>
              </table>




              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Asignar permisos</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="50%" align="left" class="titulotbcentral"><input type="checkbox" name="CHECK_ALL" title="" value="CHECK_ALL" onclick="check_all(this)">Check Todos</td>
                </tr>
              </table>
              <br>

              <%

              try {

                gov.sir.core.web.helpers.comun.TablaMatriculaHelper editPermisosHelper
                  = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

				editPermisosHelper.setAlineacionTodasCeldas(TablaMatriculaHelper.ALINEACION_IZQUIERDA);
                editPermisosHelper.setColCount(2);
                editPermisosHelper.setInputName(WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION);
                editPermisosHelper.setListName(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION);

                editPermisosHelper.setSelectedValues_Enabled( true );
                editPermisosHelper.setSelectedValues_Ids_Key( "PARAM_CACHE__PERMISOS" );

                editPermisosHelper.setContenidoCelda(gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_CHECKBOX);
                        /*
* @author : CTORRES
* @change : se agrega condicion para eliminar permiso
 *  cuando el turno no sea de correccion de testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
                List permisos = (List) request.getSession().getAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION);
                 Object permisoTest = null;
                if(!activo){
                    List selectItems = (List)request.getSession().getAttribute("PARAM_CACHE__PERMISOS");
                    selectItems.add("TUR_TESTAMENTO");
                    request.getSession().setAttribute("PARAM_CACHE__PERMISOS",selectItems);
                    editPermisosHelper.render(request, out);
                }else
                {
                    if(permisos.size()==22){
                        permisoTest = permisos.get(21); 
                        permisos.remove(21);
                        request.getSession().setAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION,permisos); 
                        editPermisosHelper.render(request, out);
                        permisos.add(permisoTest);
                        request.getSession().setAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION,permisos);
                    }else{
                        List selectItems = (List)request.getSession().getAttribute("PARAM_CACHE__PERMISOS");
                        selectItems.add("TUR_TESTAMENTO");
                        request.getSession().setAttribute("PARAM_CACHE__PERMISOS",selectItems);
                        editPermisosHelper.render(request, out);
                    }
                }  
              }
              catch( HelperException e ){
                out.println("ERROR " + e.getMessage() );
              }

              %>


              <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->



              <br />
              <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->
              <!-- DATOS ANTIGUO SISTEMA  -->

              <!-- + + + + + + + + + + + + + + +  -->
              <!--  sub-region: type="title"  -->

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>

                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para Antiguo Sistema</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	                <input type="hidden" name="<%=WebKeys.OCULTAR%>" id="<%=WebKeys.OCULTAR%>">

	                <%

					if( "FALSE".equals( ocultarAntSist ) ) {

					%>
		                <td width="16">
		                	<a href="#">
		                		<img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="javascript:js_OnRegionAntiguoSistemaAction_ShowHide( 'CORRECCION', PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION, 'TRUE' )" width="16" height="16" border="0" />
		                	</a>
		                </td>
		            <%
					}
					else {
					%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16">
		                	<a href="#">
		                		<img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="javascript:js_OnRegionAntiguoSistemaAction_ShowHide( 'CORRECCION', PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION, 'FALSE' )" width="16" height="16" border="0" />
		                	</a>
		                </td>
					<%
					}
					%>
                </tr>
              </table>
              <br />
              <!-- + + + + + + + + + + + + + + +  -->
              <!--  sub-region: type="select"  -->
              <!--  [no necesitado] -->

              <!-- + + + + + + + + + + + + + + +  -->
              <!--  sub-region: type="buttons"  -->
              <!--  [no necesitado] -->

              <!-- + + + + + + + + + + + + + + +  -->
              <!--  sub-region: type="item-set"  -->
	<input name="Depto" type="hidden" id="id_depto" value="">
    <input name="Depto" type="hidden" id="nom_Depto" value="">
    <input name="Mpio" type="hidden" id="id_munic" value="">
    <input name="Mpio" type="hidden" id="nom_munic" value="">
    <input name="Ver" type="hidden" id="id_vereda" value="">
    <input name="Ver" type="hidden" id="nom_vereda" value="">
				<%-- // (create/update)

				if( "FALSE".equals( ocultarAntSist ) ) {

					try {

						ASHelper.render( request, out );

					}
					catch( HelperException re ){
						out.println( "ERROR " + re.getMessage() );
					}

				}

				--%>

				<% // (create/update)

				if( "FALSE".equals( ocultarAntSist ) ) {

					try {

						ash.render( request, out );

					}
					catch( HelperException re ){
						out.println( "ERROR " + re.getMessage() );
					}

				}

				%>



              <br />
              <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->







              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Redireccionar</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200" class="contenido">Redireccionar la correcci&oacute;n a: </td>
                  <td class="contenido">

                  <%
					try {
                  		List tipos = new ArrayList();

 		                ElementoLista el1=new ElementoLista(gov.sir.core.web.acciones.correccion.AWRevision.MAYOR_VALOR, "MAYOR_VALOR");
                        ElementoLista el2=new ElementoLista(gov.sir.core.web.acciones.correccion.AWRevision.ACTUACION_ADMINISTRATIVA, "ACTUACION_ADMINISTRATIVA");
                        ElementoLista el3=new ElementoLista(gov.sir.core.web.acciones.correccion.AWRevision.ANTIGUO_SISTEMA, "ANTIGUO_SISTEMA");

                        tipos.add(el1);
                        tipos.add(el2);
                        tipos.add(el3);

              			redirHelper.setTipos(tipos);
                        redirHelper.setNombre(gov.sir.core.web.acciones.correccion.AWRevision.REDIRECCIONAR_CASO);
                        redirHelper.setCssClase("camposformtext");
                        redirHelper.setId(gov.sir.core.web.acciones.correccion.AWRevision.REDIRECCIONAR_CASO);
						redirHelper.render(request,out);
					}
					catch( HelperException re ){
						out.println("ERROR " + re.getMessage());
					}
					%>

                  </td>
                  <%--
                          /*
* @author : CTORRES
* @change : se inclulle condicion para desacctivar boton en el caso de ser correccion de testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
                  --%>
                  <td class="contenido">
                      <%if(activo){%>
                            <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWRevision.REDIRECCIONAR_CASO%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif" width="139" height="21" border="0" /></a>
                      <%}else{%>
                            <a><img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif" width="139" height="21" border="0" /></a>
                      <%}%>
                      </td>
                </tr>
              </table>
              <br>

              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWRevision.CORRECCION_SIMPLE%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a></td>
                  <td width="30">&nbsp;</td>
                  <td colspan='2'>&nbsp;</td>

				  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWRevision.NEGAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" width="139" height="21" border="0"></a></td>

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

<%

String tipoCorreccion="EXTERNA";
if(turnoPadre != null){
	tipoCorreccion="INTERNA";
}

%>



      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Creación de la solicitud</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <table width="100%" class="camposform">
            <tr>


                              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Tipo de Corrección</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Qué Tipo de corrección es ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>La corrección es </td>

                  <td class="campositem"><%=tipoCorreccion%>&nbsp;</td>

                            </td>
                          </tr>
                  </tr>
              </table>




          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>



		<%
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION");
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>

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
