<%@page import = "org.auriga.core.web.*,gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import = "gov.sir.core.web.helpers.comun.*"%>
<%@page import = "java.util.*"%>
<%@page import = "gov.sir.core.web.WebKeys"%>
<%@page import = "gov.sir.core.web.acciones.registro.AWDigitacionMasiva"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.helpers.registro.MatriculaCalifHelper"%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%@taglib prefix="xRegionTemplate" uri="/xRegionTemplateTags" %>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%-- privileges resources --%>

  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/forms-lib.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/AnchorPosition.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/PopupWindow.js" ></script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- Cargar parametros para configurar los permisos --%>
<%

        // obtener los estilos de despliegue
        gov.sir.core.web.helpers.correccion.region.model.RegionManager regionManager = null;

        localBlockAuth: {


          gov.sir.core.web.helpers.correccion.region.model.RegionManager localRegionManager
          = new gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager();

          // mapa de permisos
          java.util.HashMap permisosMap;

          // test1 -----------------------------------------------------

          // un mapa de permisos de prueba
          java.util.HashMap testPermisosMap = new java.util.HashMap();

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

          regionManager = localRegionManager;

        } // :localBlockAuth

        // ..... se debe propagar el conjunto de permisos a las paginas hijas
        session.setAttribute( "param_RegionManager", regionManager );

%>
<%
  // Manejo de permisos de modificacion sobre la forma
  gov.sir.core.web.helpers.correccion.region.model.RegionManager pageLocalRegionManager = null;

  // recuperar valores de regionManager de la sesion
  pageLocalRegionManager = (gov.sir.core.web.helpers.correccion.region.model.RegionManager)session.getAttribute( "param_RegionManager" );

%>

<%
    TextHelper textHelper = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");    

    TextAreaHelper textAreaHelper = new TextAreaHelper();
    ListaElementoHelper docHelper = new ListaElementoHelper();    
    ListaElementoHelper accionHelper = new ListaElementoHelper();        
    String mensaje = (String)request.getSession().getAttribute(WebKeys.MENSAJE);
    
    
    Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
    if(turno==null){
    	turno = new Turno();
    }
    
	Solicitud solicitud = (Solicitud) turno.getSolicitud();

	if (solicitud == null) {
		throw new Exception("No existe solicitud asociada");
	}

	List solFolio = solicitud.getSolicitudFolios();
	if(solFolio==null){
		solFolio = new Vector();		
	}
	
	List temp = new ArrayList();
	temp.addAll(solFolio);

    Collections.sort(temp,new Comparator(){
		public int compare(Object arg0, Object arg1) {
		SolicitudFolio solFolio1=(SolicitudFolio)arg0;
		SolicitudFolio solFolio2=(SolicitudFolio)arg1;
		return solFolio1.getIdMatricula().compareTo(solFolio2.getIdMatricula());
		}
   	});	
   	
	List matriculas = new Vector();	
	Iterator itTemp = temp.iterator();
	int i = 0;
	while (itTemp.hasNext()) {
		ElementoLista elemento = new ElementoLista(""+i, ((SolicitudFolio) itTemp.next()).getIdMatricula());
		matriculas.add(elemento);
		i++;
	}    
	
	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}
	
	if(session.getAttribute(WebKeys.ALERTA_FOLIOS_FORANEOS) != null && ((Boolean)session.getAttribute(WebKeys.ALERTA_FOLIOS_FORANEOS)).booleanValue()){
		mensaje = "Ha traido información que no pertenece a los folios relacionados con el turno";
		session.removeAttribute(WebKeys.ALERTA_FOLIOS_FORANEOS);
	}

		//HELPER PARA MOSTRAR
	MatriculaCalifHelper matHelper = new MatriculaCalifHelper();
	matHelper.setFolios(solicitud.getSolicitudFolios());

	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));
	java.util.Hashtable ht = (java.util.Hashtable) session.getAttribute(WebKeys.VALIDACION_APROBAR_CALIFICACION);
	matHelper.setValidacionAnotacionesTemporales(ht);
	matHelper.setModoVisualizacion(MatriculaCalifHelper.MODO_VISUALIZACION_REVISAR);
	
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function revisar(text) {
   document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = text;
   document.CORRECCION.action = 'digitacionMasiva.do';
   document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.VER_DETALLES_FOLIO%>';
   document.CORRECCION.submit();	
}

//@author : David A Rubio J
//@change : Se agrega función para mostrar los detalles de un folio en una ventana emergente con pestañas
function verAnotacionPersonalizada(matricula,nombre,valor,dimensiones,pos){
    nombre=nombre+"&ID_MATRICULA="+matricula;
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
 }  	


/**
 * @author: Cesar Ramirez
 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
 * Se modifica el método aceptarDigitacion() con cambiarAccionAceptarDigitacion()
**/
function aceptarDigitacion(text) {
    <%
        /**
         * @Autor: Edgar Lora @Mantis: 0013038 @Requerimiento: 060_453
         */
        String mensajeLin = "";
        String mensajeAlerta = "";
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        List folios = solicitud.getSolicitudFolios();
        
        for(int j = 0; j < folios.size(); j = j + 1) {
            SolicitudFolio sf = (SolicitudFolio) folios.get(j);
            Folio f = sf.getFolio();
            String matricula = f.getIdMatricula();
            String lindero = null;
                
            if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                //if(validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS)) {
                /**
                 * @author: Cesar Ramirez
                 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                 * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada
                 */
                if(validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                    if(lindero.indexOf(articulo) != -1) {
                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                        if(lindero.length() - tamArticulo < 100) {
                            if(mensajeLin.length() > 0) {
                                mensajeLin = mensajeLin + "\\n";
                            }
                            mensajeLin = mensajeLin + "No se ha incluido la información de linderos para la matricula " + matricula;
                        }
                    } else {
                        if(mensajeAlerta.length() > 0) {
                            mensajeAlerta = mensajeAlerta + "\\n";
                        }
                        mensajeAlerta = "El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto 'Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012', Porfavor no lo borre";
                    }
                }
            }
        }
        if (mensajeLin.length() > 0) {
            mensajeLin = mensajeLin + "\\n ¿esta seguro que desea terminar la calificación?";
        }
        if (mensajeAlerta.length() <= 0 && mensajeLin.length() > 0) {
    %>
        var message = new String( "<%=mensajeLin%>" );      
        if(confirm(message)) {
            cambiarAccionAceptarDigitacion(text);
        }
    <%
        } else if (mensajeAlerta.length() > 0) {
    %>
        alert("<%=mensajeAlerta%>");  
    <%
        } else {
    %>
        var message = new String( "¿Esta seguro que desea terminar la calificación?" );

        if(confirm(message)) {
            cambiarAccionAceptarDigitacion(text);
        }
    <%
        }
    %>
}

function cambiarAccionAceptarDigitacion(text) {
	document.CORRECCION.action = 'correccion.do';	
	document.CORRECCION.ACCION.value = text;
	document.CORRECCION.submit();	
}

function aceptarDigitacionCorreccion() {
	document.CORRECCION.action = 'digitacionMasiva.do';	
 	document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.BTN_BACK_ACTION%>';
	document.CORRECCION.submit();	
}
function regresar() {
	document.CORRECCION.action = 'digitacionMasiva.do';	
 	document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.BTN_REGRESAR%>';
	document.CORRECCION.submit();	
}
function construirComplementacion() {
	document.CORRECCION.action = 'digitacionMasiva.do';	
	document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.ENTRAR_CONSTRUCCION_COMPLEMENTACION%>';
	document.CORRECCION.submit();	
}
function cambiarAccion(text,tipo) {
	document.CORRECCION.ACCION.value = text;

	if(tipo=='<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>'){
		document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = document.CORRECCION.<%=CDigitacionMasiva.COMPLEMENTACION_MATRICULA%>.value
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>'
	}
	if(tipo=='<%=CDigitacionMasiva.OPCION_LINDERO%>'){
		document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = document.CORRECCION.<%=CDigitacionMasiva.LINDERO_MATRICULA%>.value
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_LINDERO%>'
	}
	if(tipo=='<%=CDigitacionMasiva.OPCION_DIRECCION%>'){
		document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value = document.CORRECCION.<%=CDigitacionMasiva.DIRECCION_MATRICULA%>.value
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_DIRECCION%>'
	}

	if(document.CORRECCION.<%=CFolio.ID_MATRICULA%>.value!=''){	
		document.CORRECCION.submit();	
	}
}
function cambiarImagen() {
	if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + CDigitacionMasiva.OPCION_COMPLEMENTACION%>.value=='<%=CFolio.COPIAR%>'){
		document.all.copiarcomplementacion.style.display = ''
		document.all.asociarcomplementacion.style.display = 'none'
	}else{
		document.all.copiarcomplementacion.style.display = 'none'
		document.all.asociarcomplementacion.style.display = ''
	}
}

function editarFolios(text,tipo) {
	document.CORRECCION.ACCION.value = text;

	if(tipo=='<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>'){
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>'
	}
	if(tipo=='<%=CDigitacionMasiva.OPCION_LINDERO%>'){
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_LINDERO%>'
	}
	if(tipo=='<%=CDigitacionMasiva.OPCION_DIRECCION%>'){
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_DIRECCION%>'
	}
	if(tipo=='<%=CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL%>'){
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL%>'
	}	
	if(document.CORRECCION.<%=WebKeys.PARAMETRO%>.value!=''){	
		document.CORRECCION.submit();	
	}
}

var submit = 0;
function editarFoliosUnClic(text,tipo) {
	if(submit < 1){
		submit++;
		document.CORRECCION.ACCION.value = text;
		document.CORRECCION.<%=WebKeys.PARAMETRO%>.value = '<%=CDigitacionMasiva.OPCION_DIRECCION%>'
		document.CORRECCION.submit();
	}
}

function recargar() {
	document.CORRECCION.action = 'digitacionMasiva.do';	
	document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.DIGITACION_MASIVA_RELOAD%>';
	document.CORRECCION.submit();	
}
</script>
<%
	String toDo = "";
   //toDo = "procesos.view";
   toDo = "correccion.digitacion.masiva.view";
%>
<script>
redirTime = "480000";
redirURL = "<%= toDo%>"
function redirTimer() { 
	self.setTimeout("recargar();",redirTime); 
}
</script>

<body onload='redirTimer()'>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<%
if(mensaje!=null && !mensaje.equals("")){
%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_error_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_error.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Resultado de la operaci&oacute;n</td>
          <td width="14"><img name="tabla_error_r1_c4" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%= request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"> 
    
    
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas"><tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tablas">
			<tr> 
			<td class='error'><%=mensaje%></td>
			</tr>
			<br>
			</table></td>
			<td>&nbsp;</td>
			</tr>
			</table>
			
      </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
<%
}
%>





<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  <form action="digitacionMasiva.do" method="post" name="CORRECCION" id="CORRECCION">
  <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">            
  <input type="hidden" name="<%=WebKeys.PARAMETRO%>" id="<%=WebKeys.PARAMETRO%>">              
  <input name="<%=CFolio.ID_MATRICULA%>" type="hidden" class="camposformtext">                   	
  
<%
  	Fase fase = (Fase)session.getAttribute(WebKeys.FASE) ;
  	if(fase.getID().equals(CFase.CAL_DIGITACION)){
  %>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">folios</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                       </tr>
	                  </table></td>
	                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	              </tr>
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

              <table width="100%">
                <tr>
                  <td>

                      <% try {
                  			    matHelper.render(request,out);
							}
								catch(HelperException re){
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
      
    <!--FIN INSCRIPCIÓN -->  
<%
	}
  %>

  <!--Complemantación-->
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Complementaci&oacute;n </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
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
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                 <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> DE</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
<xRegionTemplate:DisplayPoint
  regionId="htRgn_ComplementacionMain"
  regionName="complementacion"
  regionDescription="Contiene la complementacion del folio (definitiva mas las adiciones )."
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>
                <tr>
                  <td>N&uacute;mero de Folio </td>
                  <td width="20%"><%=idCirculo%>-
				    <%
					try {
						textHelper.setNombre(CDigitacionMasiva.COMPLEMENTACION_MATRICULA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CDigitacionMasiva.COMPLEMENTACION_MATRICULA);
						textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}%>
                  </td>
				  <td>
				  	<a href="javascript:cambiarAccion('<%=AWDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA%>','<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_cargar_complementacion.gif" name="Folio" width="190" height="21" border="0"></a>
				  </td>
                  <td width="40%">
	                  <a href="javascript:construirComplementacion();"><img src="<%=request.getContextPath()%>/jsp/images/btn_construir_complementacion.gif" name="Folio" width="220" height="21" border="0"></a>				  
                  </td>
                </tr>
              </table>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Complementaci&oacute;n </td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td>
                         <% try {
   		                       textAreaHelper.setNombre(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
 		                       textAreaHelper.setCols("130");
 		                       textAreaHelper.setRows("12");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
							   textAreaHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
						 %>                      
                  </td>
                </tr>
              </table>
             
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                 <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> A</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="120">Acci&oacute;n</td>
                  <td width="200">Rango de Folios </td>
                  <td width="200">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  
                  <td>
                      <% 
                      List acciones = new ArrayList();
                      ElementoLista elemento1 = new ElementoLista(CFolio.COPIAR, "Copiar A....");
                      ElementoLista elemento2 = new ElementoLista(CFolio.ASOCIAR, "Asociar A....");                      
                      acciones.add(elemento1);
                      acciones.add(elemento2);                      
                      
                      try {
               				accionHelper.setTipos(acciones);
	                        accionHelper.setNombre(CDigitacionMasiva.COMPLEMENTACION_ACCION);
               			    accionHelper.setCssClase("camposformtext");
               			    accionHelper.setId(CDigitacionMasiva.COMPLEMENTACION_ACCION);
							accionHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>                       
                    </td>
                  <td>Desde:
                      <% 
                      try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.COMPLEMENTACION_DESDE);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.COMPLEMENTACION_DESDE);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							
						try {
							hiddenHelper.setNombre(CComplementacion.ID_COMPLEMENTACION);
							hiddenHelper.setCssClase("camposformtext");
							hiddenHelper.setId(CComplementacion.ID_COMPLEMENTACION);
							hiddenHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
							
						%>      
                  </td>
                  <td>Hasta:
                      <% try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.COMPLEMENTACION_HASTA);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.COMPLEMENTACION_HASTA);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>  
                  </td>
                  <td>
                  <a href="javascript:editarFolios('<%=AWDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA%>','<%=CDigitacionMasiva.OPCION_COMPLEMENTACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_complementacion.gif" name="copiarcomplementacion" id="copiarcomplementacion" width="190" height="21" border="0"><img src="<%=request.getContextPath()%>/jsp/images/btn_asociar_complementacion.gif" name="asociarcomplementacion" id="asociarcomplementacion" width="190" height="21" style="display:none" border="0"></a>
                  </td>
                </tr>
<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
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
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
  
    <!-- 
        @author Cesar Ramírez
        @change: 1736.NO.DUPLICIDAD.PROCESO.DIGITAR.MASIVO.AL.REALIZAR.COPY
        Se comenta el código para que no se visualice la sección de "Agregar Linderos"
    -->
  
  <!--linderos-->
  <!--
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%//=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%//=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%//=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Linderos</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                       </tr>
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%//=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> DE</td>
                  <td width="15"><img name="sub_r1_c4" src="<%//=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
 -->
<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!--
<xRegionTemplate:DisplayPoint
  regionId="htRgn_LinderoMain"
  regionName="cabida y lindero"
  regionDescription="Contiene la descripcion de cabida y lindero del folio."
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	
-->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
<!--
                <tr>
                 <td>N&uacute;mero de Folio </td>
                  <td width="20%"><%//=idCirculo%>-

				    <%/*
					try {
						textHelper.setNombre(CDigitacionMasiva.LINDERO_MATRICULA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CDigitacionMasiva.LINDERO_MATRICULA);
						textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}*/%>                     

                  </td>
				  <td><a href="javascript:cambiarAccion('<%//=AWDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA%>','<%//=CDigitacionMasiva.OPCION_LINDERO%>');"><img src="<%//=request.getContextPath()%>/jsp/images/btn_cargar_lindero.gif" name="Folio" width="180" height="21" border="0"></a></td>                 
                  <td width="40%">&nbsp;</td>
                </tr>
              </table>
             
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%//=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Linderos </td>
                  <td width="15"><img name="sub_r1_c4" src="<%//=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                 <td>
                   <%/* try {
 		                       textAreaHelper.setNombre(CDigitacionMasiva.LINDERO_LINDERO);
 		                       textAreaHelper.setCols("130");
 		                       textAreaHelper.setRows("12");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CDigitacionMasiva.LINDERO_LINDERO);
							   textAreaHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }*/
					%>                  
                  </td>
                </tr>
              </table>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%//=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> A</td>
                  <td width="15"><img name="sub_r1_c4" src="<%//=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                 <td width="120">Acci&oacute;n</td>
                  <td width="200">Rango de Folios </td>
                  <td width="200">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  
                  <td>
                  <% /*
                  	  List accionesCopiar = new ArrayList();
                      accionesCopiar = new ArrayList();
                      ElementoLista elementoCopiar = new ElementoLista(CFolio.COPIAR, "Copiar A....");
                      accionesCopiar.add(elementoCopiar);
                      
                      try {
               				accionHelper.setTipos(accionesCopiar);
	                        accionHelper.setNombre(CDigitacionMasiva.LINDERO_ACCION);
               			    accionHelper.setCssClase("camposformtext");
               			    accionHelper.setId(CDigitacionMasiva.LINDERO_ACCION);
							accionHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
				*/	%>   
				  </td>
                  <td>Desde:
                  	  <%/*
                      try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.LINDERO_DESDE);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.LINDERO_DESDE);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						*/%>      
                  </td>
                  <td>Hasta:
                  	  <%/*
                      try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.LINDERO_HASTA);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.LINDERO_HASTA);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						*/%>                        
					</td>
                  <td>
                  <a href="javascript:editarFolios('<%//=AWDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA%>','<%//=CDigitacionMasiva.OPCION_LINDERO%>');"><img src="<%//=request.getContextPath()%>/jsp/images/btn_copiar_lindero.gif" name="Folio" width="180" height="21" border="0"></a>                  
                  </td>
                </tr>
<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!--
</xRegionTemplate:DisplayPoint>
-->
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
<!--
              </table>
          </td>
          <td width="11" background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%//=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%//=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%//=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
	</td>
    <td">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>  
-->
  
  
  
  
  <!--Direcciones-->
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Direcci&oacute;n </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
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
               <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="campostip04"><b>Tenga en cuenta las siguientes recomendaciones: </b><br>
                  La dirección que se cargue o se escriba en los siguientes campos, se concatenará con la información del lote que se ingresó en el momento de la segregación.<br>
                  La información del lote que se ingrese en el englobe, no se concatenará con la dirección que se cargue o se escriba, sólo se adiciona al nuevo folio.<br>
                  </td>
				  <td>
				  </td>
                </tr>
              </table>
			  <table>
				   <tr>&nbsp;</tr>
			  </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> DE</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
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
              <table width="100%" class="camposform">
                <tr>
                  <td>N&uacute;mero de Folio </td>
                  <td width="20%"><%=idCirculo%>-
				    <%
					try {
						textHelper.setNombre(CDigitacionMasiva.DIRECCION_MATRICULA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CDigitacionMasiva.DIRECCION_MATRICULA);
						textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}%>                     
                  </td>
				  <td ><a href="javascript:cambiarAccion('<%=AWDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA%>','<%=CDigitacionMasiva.OPCION_DIRECCION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_cargar_direccion.gif" name="Folio" width="180" height="21" border="0"></a></td>                       
                  <td width="40%">&nbsp;</td>
                </tr>
              </table>
             
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Direcci&oacute;n </td>
                   <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

                  <%
	
		ListaElementoHelper ejesHelper;
		ListaElementoHelper ejes2Helper;
		
		List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
		if(ejes == null){
			ejes = new Vector();
		}
		ejesHelper = new ListaElementoHelper();
		ejesHelper.setCssClase("camposformtext");
		ejesHelper.setOrdenar(false);
		ejesHelper.setTipos(ejes);
		
		ejes2Helper = new ListaElementoHelper();
		ejes2Helper.setCssClase("camposformtext");
		ejes2Helper.setOrdenar(false);
		ejes2Helper.setTipos(ejes);		
		
		textHelper = new TextHelper();
		textAreaHelper = new TextAreaHelper();                  
    %>
                  
                  
<br>

<table width="100%" class="camposform">
<tr>

<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>
<%
		try {
			ejesHelper.setId(CFolio.FOLIO_EJE1);
			ejesHelper.setNombre(CFolio.FOLIO_EJE1);
			ejesHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
%>		
</td>
<td>
<%		
		try {
			textHelper.setNombre(CFolio.FOLIO_VALOR1);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CFolio.FOLIO_VALOR1);
			textHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
%>			
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>
<%		
		try {
			ejes2Helper.setId(CFolio.FOLIO_EJE2);
			ejes2Helper.setNombre(CFolio.FOLIO_EJE2);
			ejes2Helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
%>		
</td>
<td>
<%		try {
			textHelper.setNombre(CFolio.FOLIO_VALOR2);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CFolio.FOLIO_VALOR2);
			textHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
%>		
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>Especificaci&oacute;n</td>
<td>
<%		try {
			textAreaHelper.setNombre(CFolio.FOLIO_COMPLEMENTO_DIR);
			textAreaHelper.setCols("50");
			textAreaHelper.setRows("5");
			textAreaHelper.setCssClase("camposformtext");
			textAreaHelper.setId(CFolio.FOLIO_COMPLEMENTO_DIR);
			textAreaHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
%>		
</td>
</tr>



</table>
                  

             
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> A</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="120">Acci&oacute;n</td>
                  <td width="200">Rango de Folios </td>
                  <td width="200">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="20">&nbsp;</td>
                  <td>
                      <% 
                      List accionesCopiar2 = new ArrayList();
                      ElementoLista elementoCopiar2 = new ElementoLista(CFolio.COPIAR, "Copiar A....");
                      accionesCopiar2.add(elementoCopiar2);
                      
                      try {
               				accionHelper.setTipos(accionesCopiar2);
	                        accionHelper.setNombre(CDigitacionMasiva.DIRECCION_ACCION);
               			    accionHelper.setCssClase("camposformtext");
               			    accionHelper.setId(CDigitacionMasiva.DIRECCION_ACCION);
							accionHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>                     
                  </td>
                  <td>Desde:
                  	  <%
                      try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.DIRECCION_DESDE);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.DIRECCION_DESDE);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>                            
                  </td>
                  <td>Hasta:
                  	  <%
                      try {
                   				docHelper.setTipos(matriculas);
		                        docHelper.setNombre(CDigitacionMasiva.DIRECCION_HASTA);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CDigitacionMasiva.DIRECCION_HASTA);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>      
                  </td>
                  <td>
                  <a href="javascript:editarFoliosUnClic('<%=AWDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA%>','<%=CDigitacionMasiva.OPCION_DIRECCION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_direccion.gif" name="Folio" width="180" height="21" border="0"></a>                  
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
<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->
        
        
      </table>
      
      
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>  




  <!--Despachos Judicial-->
  <!--Salvedades-->
  <%
  	if(fase.getID().equals(CFase.COR_CORRECCION) || fase.getID().equals(CFase.COR_CORRECCION_SIMPLE)){  
//  if(fase.getID().equals(CFase.COR_CORRECCION)){
  %>  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Salvedad Folio </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
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
              <td>Salvedad</td>
          </tr> 
              <tr>
                 <td width="20">&nbsp;</td>
                 <td>                    
                 <%try {
 		                 textAreaHelper.setNombre(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
 		                 textAreaHelper.setCols("60");
 		                 textAreaHelper.setRows("8");
                  	     textAreaHelper.setCssClase("camposformtext");
                  		 textAreaHelper.setId(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
						 textAreaHelper.render(request,out);
					}catch(HelperException re){
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
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>  

  <%
	}
  %>


  <!--anotaciones-->
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
      
      
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
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES DIGITACI&Oacute;N</td>
	<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
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

                  
<%
	boolean isRegistro = true;
	String PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS = "PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS";
	String local_ProcessId = CProceso.PROCESO_REGISTRO;
		   
   if( null != session.getAttribute( PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS ) ){
	  local_ProcessId = (String)session.getAttribute( PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS );
   }
   
   if( CProceso.PROCESO_CORRECCIONES.equals( local_ProcessId ) ) {
		isRegistro = false;
   }

%>                  
<%if(isRegistro){%>
          <td><a href="javascript:aceptarDigitacion('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.APROBAR_DIGITACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  alt="Aceptar" width="150" height="21" border="0"></a></td>                  
<%}else{%>
          <td><a href="javascript:aceptarDigitacionCorreccion();"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  alt="Aceptar" width="150" height="21" border="0"></a>&nbsp;&nbsp;                        
          <a href="javascript:regresar();"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"  alt="Regresar" width="150" height="21" border="0"></a></td>
<%}%>

                </tr>
	
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
	</form>  

    <%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%> 
      
 	</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>  
  
  
  

  
 



  
  
 
</table>