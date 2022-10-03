<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWActuacionAdministrativa"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWRevision"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultaFolio" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>
<%@page import="org.auriga.util.FechaConFormato"%>

<%@page import="gov.sir.core.web.WebKeys"%>

<%	//INICIALIZACIÓN DE VARIABLES
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();
	session.setAttribute(WebKeys.VISTA_INICIAL,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
    ListaElementoHelper redirHelper = new ListaElementoHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	if(turnoAnterior==null){
		turnoAnterior = new Turno();
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
	

	//LISTA DE NOTAS DE ACTUACIONES ADMINISTRATIVAS
   SolicitudRegistro solReg = null;
   SolicitudCorreccion solCor = null;              
   List notasActuaciones = null;

   if(solicitud instanceof SolicitudRegistro){
  	 solReg = (SolicitudRegistro)solicitud;
   }
   if(solicitud instanceof SolicitudCorreccion){
  	 solCor = (SolicitudCorreccion)solicitud;
   }			  

   if(solReg!=null){
  	 notasActuaciones = solReg.getNotasActuaciones();
   }
   if(solCor!=null){
  	 notasActuaciones = solCor.getNotasActuaciones();
   }


%>

<SCRIPT>
function cambiarAccionActuacionAdministrativa(text) {
    document.CORRECCION_ACTUACION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION_ACTUACION.submit();
}

function cambiarAccion1(text) {
    document.CORRECCION_ACTUACION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION_ACTUACION.submit();
}

function editarActuacionAdministrativa(item) {
    document.CORRECCION_ACTUACION.<%=WebKeys.ACCION%>.value = '<%=AWActuacionAdministrativa.EDITAR_NOTA%>';
    document.CORRECCION_ACTUACION.<%=WebKeys.ITEM%>.value = item;    
    document.CORRECCION_ACTUACION.submit();
}

</SCRIPT>

<%
	//INICIALIZACIÓN DE HELPERS (ANTIGUO SISTEMA)
	
	AntiguoSistemaHelper ASHelper = new AntiguoSistemaHelper();

	OficinaHelper ofHelper=new OficinaHelper();
	ofHelper.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS);
	ofHelper.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
	ofHelper.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS);
	ofHelper.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);

    ASHelper.setOficinaHelper(ofHelper);
	// helper para listar los datos de antiguo sistema en sesion


	//HABILITAR CAMPOS OCULTOS
	String ocultarAntSist = (String)session.getAttribute(CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA);
	if( ocultarAntSist == null ){
		ocultarAntSist = "TRUE";
		session.setAttribute(CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA,ocultarAntSist);		
	}	
	
	String ocultarMayorValor = (String)session.getAttribute(CActuacionAdministrativa.OCULTAR_MAYOR_VALOR);
	if( ocultarMayorValor == null ){
		ocultarMayorValor = "TRUE";
		session.setAttribute(CActuacionAdministrativa.OCULTAR_MAYOR_VALOR,ocultarMayorValor);		
	}
	
	String ocultarSolicitud = (String)session.getAttribute(CActuacionAdministrativa.OCULTAR_SOLICITUD);
	if( ocultarSolicitud == null ){
		ocultarSolicitud = "TRUE";
		session.setAttribute(CActuacionAdministrativa.OCULTAR_SOLICITUD,ocultarSolicitud);		
	}

%>

<script type="text/javascript">

   function LocalForm( formName ) {
     this.formName = formName;
   }
  
   LocalForm.prototype = new LocalForm();
   LocalForm.prototype.constructor = LocalForm;

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
	var actionField = "<%=WebKeys.ACCION%>";
	var PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION = "<%=AwCorrecciones_Constants.ACT_PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION%>";
</script>

<script type="text/javascript">

	function ocultar( formName, actionValue, parametro , valorParametro ){
		var htmlForm;
		htmlForm = new LocalForm( formName );
		htmlForm.setValue( actionField, actionValue );
		htmlForm.setValue( parametro, valorParametro );
		htmlForm.submitForm();
	}
	

	function oficinas(nombre,valor,dimensiones)
	{
		document.CORRECCION_ACTUACION.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
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

</script>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Actuaci&oacute;n Administrativa</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    
    
	<!--PARTE 1-->    
	<!--ANÁLISIS DE LA ACTUACIÓN-->
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ESTUDIO Y ANÁLISIS</td>
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


			  <!--DATOS PARA MAXIMIZAR LA SOLICITUD-->
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>

                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos de la solicitud</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>

	                <%
					if( "FALSE".equals( ocultarSolicitud ) ) {
					%>
		                <td width="16">
		                	<a href="#">
		                		<img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWRevision.OCULTAR_SOLICITUD%>', '<%=CActuacionAdministrativa.OCULTAR_SOLICITUD%>' , 'TRUE')" width="16" height="16" border="0" />
		                	</a>
		                </td>
		            <%
					}
					else {
					%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16">
		                	<a href="#">
		                		<img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWRevision.OCULTAR_SOLICITUD%>', '<%=CActuacionAdministrativa.OCULTAR_SOLICITUD%>' , 'FALSE')" width="16" height="16" border="0" />
		                	</a>
		                </td>
					<%
					}
					%>
                </tr>
              </table>
      		<!--FIN DATOS MAYOR VALOR MAXIMIZAR-->

			<%
			if( "FALSE".equals( ocultarSolicitud ) ) {
			%>

            <form action="consultaFolio.do" method="post" name="CONSULTAR_MAT" id="CONSULTAR_MAT">
            <input type="hidden" name="ACCION" value="<%=AWConsultaFolio.CONSULTAR_FOLIO%>">

              <table width="100%" class="camposform">
                  <tr>
	                  <td>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_RADIO);
                          tablaHelper.setInputName(CFolio.ID_MATRICULA);
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
                <td>Consultar Seleccionada</td>
                <td><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0"></td>
				  
              </tr>
            </table>


              </form>
            <P>&nbsp;</P>


              <%
              	if(turnoAnterior!=null&&turnoAnterior.getIdTurno()!=null) {
			  %>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <%}
              	if(solicitud instanceof SolicitudCorreccion) {
              		SolicitudCorreccion solicitudCorreccion = (SolicitudCorreccion)solicitud;
			  %>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {                 	
                 				TextAreaHelper area = new TextAreaHelper();
								session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/solicitudCorreccion.getDescripcion());
								area.setCols("90");
								area.setRows("8");
                 				area.setNombre(CTurno.DESCRIPCION);
                  			   	area.setCssClase("campositem");
                  			   	area.setId(CTurno.DESCRIPCION);
                  			   	area.setReadOnly(true);
								area.render(request,out);
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
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos Solicitud </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correo.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="30%">Direcci&oacute;n de Correo</td>
                  <td align='left' class="campositem"><%=solicitudCorreccion.getDireccionEnvio()!=null?solicitudCorreccion.getDireccionEnvio():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="30%">Derecho de Petici&oacute;n </td>
                  <td align='left' class="campositem"><%=solicitudCorreccion.getDerechoPeticion()==true?"SI":"NO"%>&nbsp;</td>
                </tr>
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="30%">Correcci&oacute;n solicitada con anterioridad</td>
                  <td align='left' class="campositem"><%=solicitudCorreccion.isSolicitadaAnteriormente()?"SI":"NO"%>&nbsp;</td>
                </tr>
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="30%">Inter&eacute;s Jur&iacute;dico</td>
                  <td align='left' class="campositem"><%=solicitudCorreccion.getInteresJuridico()!=null?solicitudCorreccion.getInteresJuridico():""%>&nbsp;</td>
                </tr>
              </table>                  

              <%
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
                  <td width="211" class="campositem"><%=tipdoc%>&nbsp;</td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <td class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <td>Teléfono</td>
                  <td class="campositem"><%=ciudadano.getTelefono()!=null?ciudadano.getTelefono():""%>&nbsp;</td>                  
                </tr>
              </table>
				<%
				}
				%>

          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      <!--FIN ANÁLISIS DE LA ACTUACIÓN-->
      

	  <form name="CORRECCION_ACTUACION"  id="CORRECCION_ACTUACION" method="post" action="actosAdminCorreccion.do">
	  <input type="hidden" name="<%=WebKeys.ACCION%>" value="">	
      <input type="hidden" name="<%=WebKeys.ITEM%>" value="">     	  
      <input type="hidden" name="<%=CActuacionAdministrativa.OCULTAR_MAYOR_VALOR%>" id="<%=CActuacionAdministrativa.OCULTAR_MAYOR_VALOR%>">                    
      <input type="hidden" name="<%=CActuacionAdministrativa.OCULTAR_SOLICITUD%>" id="<%=CActuacionAdministrativa.OCULTAR_SOLICITUD%>">                    	                
      <input type="hidden" name="<%=CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>" id="<%=CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>">                    	                
      



	  <!--PARTE 2-->
	  <!--ACTUACIONES ADMINISTRATIVAS-->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ACTUACIÓN ADMINISTRATIVA</td>
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
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%">Estado de la actuación:</td>
                  <td align='left'>
				<% 	try {
						if(session.getAttribute(CActuacionAdministrativa.ESTADO_ACTUACION)==null){
							if(notasActuaciones!=null && notasActuaciones.size()>0){
								NotaActuacion notaUltima = (NotaActuacion)notasActuaciones.get(notasActuaciones.size()-1);
								session.setAttribute(CActuacionAdministrativa.ESTADO_ACTUACION , notaUltima.getEstado());
							}
						}
					   	TextHelper textHelper = new TextHelper();								
	                   	textHelper.setNombre(CActuacionAdministrativa.ESTADO_ACTUACION);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("50");
		               	textHelper.setId(CActuacionAdministrativa.ESTADO_ACTUACION);
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>
              </table>              
              
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {                 	
                 				TextAreaHelper area = new TextAreaHelper();
								area.setCols("120");
								area.setRows("10");
                 				area.setNombre(CActuacionAdministrativa.NOTA_ACTUACION);
                  			   	area.setCssClase("camposformtext");
                  			   	area.setId(CActuacionAdministrativa.NOTA_ACTUACION);
                  			   	area.setReadOnly(false);
								area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
					</td>
                </tr>
              </table>


              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccionActuacionAdministrativa('<%=AWActuacionAdministrativa.AGREGAR_NOTA%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" border="0"></a></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
		

			  <hr class="linehorizontal">
			  
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de la actuación administrativa</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>			  
			  
              <%if(notasActuaciones!=null){%>
              <table width="100%" class="camposform">
				
				<%
				Iterator itNotas = notasActuaciones.iterator();
				while(itNotas.hasNext()){
					NotaActuacion notaActuacion = (NotaActuacion)itNotas.next();								
				%>
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" width="20%">
                      <%
                          session.setAttribute(CActuacionAdministrativa.ESTADO_ACTUACION + notaActuacion.getIdNotaActuacion(), notaActuacion.getEstado());

                          try {
                              TextHelper textHelper = new TextHelper();
                              textHelper.setNombre(CActuacionAdministrativa.ESTADO_ACTUACION + notaActuacion.getIdNotaActuacion());
                              textHelper.setId(CActuacionAdministrativa.ESTADO_ACTUACION + notaActuacion.getIdNotaActuacion());
                              textHelper.setCssClase("campositem");
                              textHelper.setSize("25");
                              textHelper.render(request, out);
                          } catch (HelperException re) {
                              out.println("ERROR " + re.getMessage());
                          }
                      %>
				  <td class="contenido" width="10%">
				  <!--% * @author:AHERRENO
                                        * @change:Caso Mantis 00011623
                                        * Se modifica formato del campo notaActuacion.getFechaCreacion() para que despliegle horas, minutos y segundos.
                                        try {
				  	fechaHelper.setDate(notaActuacion.getFechaCreacion());
					fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
					fechaHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
				  %--><%=  notaActuacion.getFechaCreacion()!=null? FechaConFormato.formatear( notaActuacion.getFechaCreacion(), "dd/MM/yyyy hh:mm:ss a") :"&nbsp;"   %> </td>
                  <td class="contenido" width="70%">
                  <%
					session.setAttribute(CActuacionAdministrativa.NOTA_ACTUACION + notaActuacion.getIdNotaActuacion(),notaActuacion.getNota());
					try {
                                                TextAreaHelper textArea = new TextAreaHelper();
                                                textArea.setCols("100");
                                                textArea.setRows("1");
                                                textArea.setNombre(CActuacionAdministrativa.NOTA_ACTUACION + notaActuacion.getIdNotaActuacion());
                                                textArea.setId(CActuacionAdministrativa.NOTA_ACTUACION + notaActuacion.getIdNotaActuacion());
                                                textArea.setCssClase("campositem");
                                                textArea.setFuncion(" onmouseover=\"this.rows=10\" onmouseout=\"this.rows=1\" ");
                                                textArea.setReadOnly(false);
                                                textArea.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                  %>
                  </td>         
                  <td class="contenido" width="20">
					 <a href="javascript:editarActuacionAdministrativa('<%=notaActuacion.getIdNotaActuacion()%>')"><img src="<%= request.getContextPath()%>/jsp/images/btn_mini_editar.gif" border="0"></a>
                  </td>                                    
                </tr>

                <%
                }
                %>
              </table>                         
              <%
              }
              %>
     



          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	  <!--FIN ACTUACIONES ADMINISTRATIVAS-->      


	  <!--PARTE 3-->    
	  <!--OPCIONES ACTUACIONES ADMINISTRATIVAS-->      
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
              <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES PARA EL CASO</td>
                      <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                      <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                  <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                              </tr>
                          </table></td>
                      <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
              </table>
          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            
      


      		  <!--DATOS MAYOR VALOR MAXIMIZAR-->
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>

                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para mayor valor</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>


	                <%
					if( "FALSE".equals( ocultarMayorValor ) ) {
					%>
		                <td width="16">
		                	<a href="#">
		                		<img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWRevision.OCULTAR_MAYOR_VALOR%>', '<%=CActuacionAdministrativa.OCULTAR_MAYOR_VALOR%>' , 'TRUE')" width="16" height="16" border="0" />
		                	</a>
		                </td>
		            <%
					}
					else {
					%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16">
		                	<a href="#">
		                		<img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWRevision.OCULTAR_MAYOR_VALOR%>', '<%=CActuacionAdministrativa.OCULTAR_MAYOR_VALOR%>' , 'FALSE')" width="16" height="16" border="0" />
		                	</a>
		                </td>
					<%
					}
					%>
                </tr>
              </table>
              <br />
      		<!--FIN DATOS MAYOR VALOR MAXIMIZAR-->

			<!--DATOS MAYOR VALOR-->
			<%
			if( "FALSE".equals( ocultarMayorValor ) ) {
			%>

                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Valor por Derechos</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    	<% 
							TextHelper textHelper = new TextHelper();                    	
                    		try {
 		              			textHelper.setNombre(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS);
		 		              	textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\" onKeyDown=\"return validarCaracter(event);\"");
			                  	textHelper.setCssClase("camposformtext");
			                  	textHelper.setId(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS);
								textHelper.render(request,out);
							} catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    </td>
                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                    <td> &nbsp; </td>
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Valor impuestos</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    	<% 
                    		try {
 		              			textHelper.setNombre(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS);
		 		              	textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\" onKeyDown=\"return validarCaracter(event);\"");
			                  	textHelper.setCssClase("camposformtext");
			                  	textHelper.setId(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS);
								textHelper.render(request,out);
							} catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    </td>
                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                    <td> &nbsp; </td>
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Razon del pago</td>
                    <td>
                    <% 
					try{
						TextAreaHelper taHelper = new TextAreaHelper();					
						taHelper.setCols("100");
						taHelper.setReadOnly(false);
						taHelper.setCssClase("camposformtext");
						taHelper.setId(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION);
						taHelper.setNombre(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION);
						taHelper.setRows("10");
						taHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
                    </td>
                  </tr>
                </table>
				<br>

			<%
			}
			%>
			  <!--FIN DATOS MAYOR VALOR-->

			      
      		  <!--DATOS ANTIGUO SISTEMA MAXIMIZAR-->
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>

                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para Antiguo Sistema</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>

	                <%

					if( "FALSE".equals( ocultarAntSist ) ) {

					%>
		                <td width="16">
		                	<a href="#">
		                		<img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>', '<%=CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>' , 'TRUE' )" width="16" height="16" border="0" />
		                	</a>
		                </td>
		            <%
					}
					else {
					%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16">
		                	<a href="#">
		                		<img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="javascript:ocultar( 'CORRECCION_ACTUACION', '<%=AWActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>', '<%=CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA%>', 'FALSE' )" width="16" height="16" border="0" />
		                	</a>
		                </td>
					<%
					}
					%>
                </tr>
              </table>
              <br />

			<input name="Depto" type="hidden" id="id_depto" value="">
		    <input name="Depto" type="hidden" id="nom_Depto" value="">
		    <input name="Mpio" type="hidden" id="id_munic" value="">
		    <input name="Mpio" type="hidden" id="nom_munic" value="">    
		    <input name="Ver" type="hidden" id="id_vereda" value="">
		    <input name="Ver" type="hidden" id="nom_vereda" value="">  
				<% // (create/update)

				if( "FALSE".equals( ocultarAntSist ) ) {

					try {

						ASHelper.render( request, out );

					}
					catch( HelperException re ){
						out.println( "ERROR " + re.getMessage() );
					}

				}

				%>
              <br />
      		  <!--FIN DATOS ANTIGUO SISTEMA MAXIMIZAR-->

			
			  <!-- REDIRECCIONAR -->
              <table width="100%" class="camposform">
                 <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200" class="contenido">Redireccionar la correcci&oacute;n a: </td>
                  <td class="contenido">

                  <% try {
                  			List tipos = new ArrayList();

 		                    ElementoLista el1=new ElementoLista(AWActuacionAdministrativa.WF_MAYOR_VALOR, "MAYOR VALOR");
                            ElementoLista el3=new ElementoLista(AWActuacionAdministrativa.WF_ANTIGUO_SISTEMA, "ANTIGUO SISTEMA");

                            tipos.add(el1);
                            tipos.add(el3);

              				redirHelper.setTipos(tipos);
                            redirHelper.setNombre(AWActuacionAdministrativa.ENVIAR_TURNO);
                            redirHelper.setCssClase("camposformtext");
                            redirHelper.setId(AWActuacionAdministrativa.ENVIAR_TURNO);
                            redirHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>

                  </td>
                  <td width="140" class="contenido"><a href="javascript:cambiarAccion1('<%=AWActuacionAdministrativa.ENVIAR_TURNO%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_redireccionar.gif" width="139" height="21" border="0"></td>
                </tr>
              </table>
              <br>
              <!--FIN REDIRECCIONAR-->
              
              
              
            
            
              
			<hr class="linehorizontal">

              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccionActuacionAdministrativa('<%=AWActuacionAdministrativa.APROBAR_ACTUACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_ejecutor.gif" border="0"></a></td>
                  <td width="50"><a href="javascript:cambiarAccionActuacionAdministrativa('<%=AWActuacionAdministrativa.DEVOLVER_A_CORRECCION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_correccion_simple.gif" border="0"></a></td>                  
                  <td>&nbsp;</td>
                </tr>
              </table>
              
          </form>            



          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	  <!--FIN OPCIONES ACTUACIONES ADMINISTRATIVAS-->      

	  
	  <!--PARTE 4-->    
		<%
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION_ACTUACION");
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
