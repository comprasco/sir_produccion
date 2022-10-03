<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*,java.util.*,gov.sir.core.negocio.modelo.*,gov.sir.core.negocio.modelo.constantes.*,gov.sir.core.web.WebKeys"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_CopiaSalvedadOptions" %>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_VerAlertasOptions" %>
<%@page import="org.auriga.util.FechaConFormato"%>
<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

    MatriculaEditHelper matEditHelper = new MatriculaEditHelper(gov.sir.core.web.acciones.correccion.AWModificarFolio.CARGAR_FOLIO_CORRECCION);
    MatriculaViewHelper matViewHelper = new MatriculaViewHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();

	if(solicitud==null){
		solicitud = new Solicitud();
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
        /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
        boolean activo = true;
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
	List solicitudFolios = solicitud.getSolicitudFolios();
	Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

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
    Fase fase = (Fase)session.getAttribute(WebKeys.FASE);


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

	boolean isActuacionAdministrativa = false;
	 if(fase.getID().equals(CFase.COR_ACT_EJECUTAR)){
	 	isActuacionAdministrativa = true;
	 }
	 
	 boolean tieneAlerta = false;
	 if(session.getAttribute("GENERAR_ALERTA_SIN_CAMBIOS")!=null){
	 	tieneAlerta = true;
	 }

%>

<script type="text/javascript">

function aprobar(text) {
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.action = 'correccion.diferencias.view';
    document.CORRECCION.submit();
}
function cambiarAccion(text) {
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.submit();
}
function cargarFolio(text,folio) {
    document.CORRECCION.action = 'modificacion.do';
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = folio;
    document.CORRECCION.submit();
}
/*
* @author : CTORRES
* @change : se agrega funcion javascript
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*
*/
function cargarTestamento(text,folio,editable) {
    document.CORRECCION.action = 'modificar.testamento.do';
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = folio;
    document.CORRECCION.TS_EDITABLE.value=editable;
    document.CORRECCION.submit();
}

//@author : David A Rubio J
//@change : Se agrega función para mostrar los detalles de un folio en una ventana emergente con pestañas
    function verAnotacion(nombre,valor,dimensiones,pos){
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
    }

</SCRIPT>




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
        // [correccion.do]
	var ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION = "<%=AwCorrecciones_Constants.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION %>";
        var ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION   = "<%=AwCorrecciones_Constants.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION   %>";
        var PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION = "<%= AWCorreccion.PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION %>"
        var TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA = "<%=AWCorreccion.TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA%>";
        // [salvedadfolio.do]
        var CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION = "<%= AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION %>";
        var CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION      = "<%= AwCorr_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION%>";
        var CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION              = "<%= AWCorreccion.CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION %>";
</script>
<script type="text/javascript">
// local form proxy

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


   function js_OnProxyAction( formName, actionValue ) {

     return js_OnEvent( formName, actionValue );

   }

   function js_ConfirmMessage( message ) {
     // OPTIONAL: cambiar la apariencia de este tipo de ventanas
     // usando chromeless u otro estilo de despliegue incluyendo transparencias.

     //var message = new String( "Esta seguro que desea enviar los cambios al revisor?" );
     var agree = confirm( message );
     if( agree )
        return true;
     return false;
   }

   function js_OnProxyAction2( formName, actionValue ) {
       <% 
       /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
       String mensaje = "";
       String mensajeAlerta = "";
       ValidacionesSIR validacionesSIR = new ValidacionesSIR();
       List folios = solicitud.getSolicitudFolios();
       for(int i = 0; i < folios.size(); i = i + 1){
            SolicitudFolio sf = (SolicitudFolio) folios.get(i);
            Folio f = sf.getFolio();
            String matricula = f.getIdMatricula();
            String lindero = null;
            if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
                lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                /**
                 * @author: Cesar Ramirez
                 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                 * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada.
                 **/
                if(validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)){
                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                    if(lindero.indexOf(articulo) != -1){
                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                        if(lindero.length() - tamArticulo < 100){
                            if(mensaje.length() > 0){
                                mensaje = mensaje + "\\n";
                            }
                            mensaje = mensaje + "No se ha incluido la información de linderos para la matricula " + matricula;
                        }
                    }else{
                        if(mensajeAlerta.length() > 0){
                            mensajeAlerta = mensajeAlerta + "\\n";
                        }
                        mensajeAlerta = "El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto 'Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012', Porfavor no lo borre";
                    }
                }
            }
       }
       if(mensaje.length() > 0){
           mensaje = mensaje + "\\n esta seguro que desea enviar los cambios al revisor?";
       }
       if(mensajeAlerta.length() <= 0 && mensaje.length() > 0){
       %>
            var message = new String( "<%=mensaje %>" );

            if( !js_ConfirmMessage( message ) )
                return void(0);

            js_OnProxyAction( formName, actionValue );
        <%
        }else if(mensajeAlerta.length() > 0){
        %>
          alert("<%=mensajeAlerta %>");  
        <% 
        }else {
           %>
            var message = new String( "Esta seguro que desea enviar los cambios al revisor?" );

     if( !js_ConfirmMessage( message ) )
        return void(0);

            js_OnProxyAction( formName, actionValue );
            <%
        }
        %>
   }

   function js_OnProxyAction3( formName, actionValue ) {
     var message = new String( "Se enviaran los datos de vuelta al responsable de correcciones para otras asignaciones de permisos. Está seguro ?" );

     if( !js_ConfirmMessage( message ) )
        return void(0);

    js_OnProxyAction( formName, actionValue );

   }

   function js_OnProxyActionActuacion( formName, actionValue ) {
     var message = new String( "¿Esta seguro que desea terminar de ejecutar la actuación administrativa?" );

     if( !js_ConfirmMessage( message ) )
        return void(0);

    js_OnProxyAction( formName, actionValue );

   }


</script>

















<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%if(tieneAlerta){ %>
	<jsp:include page="../comun/HEADER_MENSAJE_ALERTA.jsp?idTipoMensaje=1" flush="true" />
<%} %>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Realizar Correcci&oacute;n </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
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
            <form action="correccion.do" method="post" name="CORRECCION" id="CORRECCION">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="APROBAR">
		    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">
<%--
                    /*
* @author : CTORRES
* @change : se agrega nuevo campo para el formulario
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*
*/
--%>

                    <input type="hidden" name="TS_EDITABLE" value="">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub">&nbsp;</td>
                  <td width="16" class="bgnsub">&nbsp;</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                  <tr>
	                  <td>
	                    <%try {
	                  		    matEditHelper.setFolios(solicitud.getSolicitudFolios());
	                  		    matEditHelper.render(request,out);
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
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de la actuación administrativa</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <!--PARA EL CASO DE EJECUCIÓN DE ACTUACIONES ADMINISTRATIVAS.-->
			  <%if(isActuacionAdministrativa && notasActuaciones!=null){%>
                <table width="100%" class="camposform">
				<%
				Iterator itNotas = notasActuaciones.iterator();
				while(itNotas.hasNext()){
					NotaActuacion notaActuacion = (NotaActuacion)itNotas.next();
				%>
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" width="20%"><%=(notaActuacion!=null && notaActuacion.getEstado()!=null) ?notaActuacion.getEstado():"" %>&nbsp;</td>
				  <td class="contenido" width="10%">
				  <!--/ * @author:AHERRENO
                                        * @change:Caso Mantis 00011623
                                        * Se modifica formato del campo notaActuacion.getFechaCreacion() para que despliegle horas, minutos y segundos.
                                        try {
					MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
				  	fechaHelper.setDate(notaActuacion.getFechaCreacion());
					fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
					fechaHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
				  %-->
                                  <%=  notaActuacion.getFechaCreacion()!=null? FechaConFormato.formatear( notaActuacion.getFechaCreacion(), "dd/MM/yyyy hh:mm:ss a") :"&nbsp;"   %> </td>
                  <td class="contenido" width="70%">
                  <%
                  TextAreaHelper textArea = new TextAreaHelper();
					session.setAttribute("MENSAJE_NOTA_ACTUACION",notaActuacion.getNota());
					try{
						textArea.setCols("100");
						textArea.setRows("1");
         				textArea.setNombre("MENSAJE_NOTA_ACTUACION");
          			   	textArea.setId("MENSAJE_NOTA_ACTUACION");
          			   	textArea.setCssClase("campositem");
          			   	textArea.setFuncion(" onmouseover=\"this.rows=10\" onmouseout=\"this.rows=1\" ");
          			   	textArea.setReadOnly(true);
						textArea.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
                  %>
                  </td>
                </tr>

                <%
                }
                %>
              </table>
              <%
              }
              %>

              <BR>
<%--
                    /*
* @author : CTORRES
* @change : se valida si es un turno de correccion testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*
*/
--%>
              <%if(!activo){%>
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
                  <td><a href="javascript:cargarTestamento('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.CARGAR_TESTAMENTO%>','<%=turnoAnterior.getSolicitud().getIdSolicitud() %>','SI');"><img src="<%=request.getContextPath()%>/jsp/images/btn_tomar_turno.gif" name="Folio" width="150" height="21" border="0" id="Folio"/></a></td>
                </tr>
              </table>
              <%}else{%>
              <span class="bgnsub" style="background:" >Turno Asociado</span>
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
                <%}%>
              <span class="bgnsub" style="background:" >Datos de la Solicitud</span>

              <%
              	if(solicitud instanceof SolicitudCorreccion) {
              		SolicitudCorreccion solicitudCorreccion = (SolicitudCorreccion)solicitud;
			  %>
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
              <span class="bgnsub" style="background:" >Correo</span>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Direcci&oacute;n de Correo</td>
                  <td width="45%" align='left' class="campositem"><%=solicitudCorreccion.getDireccionEnvio()!=null?solicitudCorreccion.getDireccionEnvio():""%>&nbsp;</td>
                </tr>
              </table>
              <span class="bgnsub" style="background:" >Derecho de Petici&oacute;n </span>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getDerechoPeticion()==true?"SI":"NO"%></td>
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
              if(turnoPadre != null) {
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
              <span class="bgnsub" style="background:" >Datos Solicitante</span>

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
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>

              <span class="bgnsub" style="background:" >Ver Cambios/Alertas</span>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" alt="[]"></td>
                  <td width="10">&nbsp;</td>
                  <td width="140">
                    <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS%>');">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="140" height="21" border="0" alt="[cambios-propuestos]"/>
                    </a>
                  </td>
		  <td width="2">&nbsp;</td>
		  <td width="180">
                      <%--
                    /*
* @author : CTORRES
* @change : se valida si es un turno de correccion testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*
*/
--%>
                      <%if(!activo){%>
                    <a>
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_veralertas.gif"  alt="[ver-alertas]" width="150" height="21" border="0" />
                    </a>
                    <%}else{%>
                      <a href="javascript:js_OnEvent2( 'CORRECCION', CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION, 'corr-corrsimplemain-veralertasoptions.do' )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_veralertas.gif"  alt="[ver-alertas]" width="150" height="21" border="0" />
                    </a>
                    <%}%>
                  </td>
                  <td>&nbsp;</td>
                  <td width="8">&nbsp;</td>
                </tr>
              </table>














              <span class="bgnsub" style="background:" >Opciones Digitacion Masiva / Copia Salvedades</span>

              <%-- Bug 3551, corrección relacionada con complementaciones, direcciones --%>
              <table width="100%" class="camposform" >
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="10">&nbsp;</td>
		  <td width="150">
                    <%if(!activo){%>
                      <a>
                        <img src="<%=request.getContextPath()%>/jsp/images/btn_digitacion.gif"  alt="[digitacion]" width="150" height="21" border="0" />
                        </a>
                    <%}else{%>
                       <a href="javascript:js_OnEvent( 'CORRECCION', PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_digitacion.gif"  alt="[digitacion]" width="150" height="21" border="0" />
                    </a>
                    <%}%>
                  </td>
		  <td width="2">&nbsp;</td>
		  <td width="180">
                      <%if(!activo){%>
                    <a>
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_salvedadfolio.gif"  alt="[copia-salvedad-folio]" width="180" height="21" border="0" />
                    </a>
                    <%}else{%>
                    <a href="javascript:js_OnEvent2( 'CORRECCION', CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION, 'corr-corrsimplemain-copiasalvedadoptions.do' )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_salvedadfolio.gif"  alt="[copia-salvedad-folio]" width="180" height="21" border="0" />
                    </a>
                    <%}%>
                  </td>

                  <td>&nbsp;</td>

                </tr>
              </table>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js"
>
</script>

<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/rico.js"
>
</script>

<script
  type="text/javascript"
>


   var saveHeight;
   var showing = true;

   function toggleSlide() {
      if ( showing )
         { slideMenuUp(); showing = false; }
      else
         { slideMenuDown(); showing = true; }
   }

   function slideMenuUp() {
      var menu = $('demosMenu');
      saveHeight = menu.offsetHeight;

      menu.style.overflow = "hidden";
      new Rico.Effect.Size( menu, null, 1, 120, 8 );

      $('demoPanelLink').innerHTML = "Opciones de impresion [Mostrar]";
   }

   function slideMenuDown() {
      var menu = $('demosMenu');
      new Rico.Effect.Size( menu, null, saveHeight, 120, 8, {complete:function() { $(menu).style.overflow = "visible"; }} );
      $('demoPanelLink').innerHTML = "Opciones de impresion [Ocultar]";
   }

</script>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<br>
      <div style="margin-left:6px;font-size:xx-small;">
        <span class="bgnsub" style="background:" >
          <a id="demoPanelLink" href="javascript:toggleSlide();" >Opciones de impresion [Mostrar]</a>
        </span>
      </div>

<div id="demosMenu" ondblclick="toggleSlide()" style="margin-left:0px;margin-top:2px;padding-left:6px;width:100%;" >
  <%--SOF:REGION --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:0px;margin-right:2px;"  >
  <tr>
    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:0px;padding-top:4px;padding-right:20px;padding-bottom:0px;" >



              <span class="bgnsub" style="background:" >Imprimir Formulario</span>


              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>

                <td><div align="right">Número de copias a imprimir:</div></td>
		<td width="50">

		<%

                try {

                    TextHelper textHelper = new TextHelper();
                    textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
                    textHelper.setCssClase("camposformtext");
                    textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
                    textHelper.setSize("5");
                    textHelper.render(request,out);
                }
                catch( HelperException re ){
		  out.println("ERROR " + re.getMessage());
		}

		%>

		</td>


                  <td width="50">

                    <a href="javascript:js_OnEvent2( 'CORRECCION', CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION, 'correccion.do' )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_mprmrformulario.gif"  alt="[print-printable-page]" width="180" height="21" border="0" />
                    </a>

                  </td>

                </tr>
              </table>



    </td>
  </tr>
</table>

  <%--EOF:REGION --%>

<script type="text/javascript">
   // toggleSlide();
</script>


</div>
				<br>
                  <span class="bgnsub" style="background:" >Finalizar (Avanzar) / Regresar a la fase anterior</span>
<%--
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
--%>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="10">&nbsp;</td>

				  <%if(!isActuacionAdministrativa){%>
				  <td width="200">
                    <a href="javascript:js_OnProxyAction2( 'CORRECCION', ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  alt="Aceptar" width="139" height="21" border="0" />
                    </a>
                  </td>
				  <%}else{%>
				  <td width="200">
                    <a href="javascript:js_OnProxyActionActuacion( 'CORRECCION', TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  alt="Aceptar" width="139" height="21" border="0" />
                    </a>
                  </td>
				  <%}%>

                  <td>&nbsp;</td>
				  <%if(!isActuacionAdministrativa){%>
                  <td width="250">
                    <a href="javascript:js_OnProxyAction3( 'CORRECCION',  ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION )">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_devolver_revision.gif"  alt="Aceptar" width="180" height="21" border="0" align="left"/>
                    </a>
                  </td>
				  <%}%>

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
