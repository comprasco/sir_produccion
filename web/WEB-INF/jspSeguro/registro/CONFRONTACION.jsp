<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCheckedItem"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.negocio.modelo.Vereda"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.helpers.comun.AntiguoSistemaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.acciones.registro.AWConfrontacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase"%>

<%	 
	//OBTENER TURNO
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
	List sfolios=solicitud.getSolicitudFolios();
	
	//HELPER TABLA MATRICULAS
	TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
	
	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);		

	
	//TURNO ANTERIOR
	String turnoAnterior="&nbsp;";
    if(solicitud.getTurnoAnterior()!=null)
       	turnoAnterior=turno.getSolicitud().getTurnoAnterior().getIdWorkflow();

	//HELPER COMBO SUBTIPO DE SOLICITUD
	List subTiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
	if(subTiposObj==null){
		subTiposObj = new Vector();
	}

	Iterator itSubTiposObj = subTiposObj.iterator();
	List subTipos = new Vector();
	int c=0;
	while(itSubTiposObj.hasNext() && c<4){
		SubtipoSolicitud sub=(SubtipoSolicitud)itSubTiposObj.next();
		subTipos.add(new ElementoLista(sub.getIdSubtipoSol(),sub.getNombre()));
    c++;
	}

	ListaElementoHelper subTipoSolicitud = new ListaElementoHelper();
    subTipoSolicitud.setTipos(subTipos);
   	subTipoSolicitud.setNombre(CSolicitudRegistro.SUBTIPO_SOLICITUD);
    subTipoSolicitud.setCssClase("camposformtext");
    subTipoSolicitud.setId(CSolicitudRegistro.SUBTIPO_SOLICITUD);
    subTipoSolicitud.setSelected(solicitud.getSubtipoSolicitud().getIdSubtipoSol());
	
	
	//COMENTARIO
	String comentario="&nbsp;";
	if(solicitud.getComentario()!=null)
		comentario=solicitud.getComentario();
		
	//TIPO DOCUMENTO
	String tipoDocumento="&nbsp;";
	if(solicitud.getDocumento().getTipoDocumento().getNombre()!=null)
		tipoDocumento=solicitud.getDocumento().getTipoDocumento().getNombre();
	
	//NUM DOCUMENTO
	String numDocumento="&nbsp;";
	if(solicitud.getDocumento().getNumero()!=null)
		numDocumento=solicitud.getDocumento().getNumero();
		
	//FECHA DOCUMENTO
	String fechaDocumento="&nbsp;";
	if(solicitud.getDocumento().getFecha()!=null){
		fechaDocumento = DateFormatUtil.format(solicitud.getDocumento().getFecha());
	}
	
	//DEPARTAMENTO, MUNICIPIO, VEREDA
	String departamento="&nbsp;";
	String municipio="&nbsp;";
	String vereda="&nbsp;";
	String internacional="";
	boolean isInternacional=false;
	String tipoOficina="&nbsp;";
	String numOficina="&nbsp;";
	if(solicitud!=null&&solicitud.getDocumento()!=null) {
		if(solicitud.getDocumento().getOficinaInternacional()!=null) {
			internacional=solicitud.getDocumento().getOficinaInternacional();
			isInternacional=true;
		}else if(solicitud.getDocumento().getOficinaOrigen()!=null) {
			if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null) {
				Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
				departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
				municipio=auxVereda.getMunicipio().getNombre();
				vereda=auxVereda.getNombre();
				//TIPO OFICINA
				if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
					tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
				//NUM OFICINA
				if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
					numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
			}
		}
	}
	
	//TIPO IDENTIFICACION
	String tipoIdentificacion="&nbsp;";
	if(solicitud.getCiudadano().getTipoDoc()!=null)
		tipoIdentificacion=solicitud.getCiudadano().getTipoDoc();
	
	//NUM IDENTIFICACION
	String numIdentificacion="&nbsp;";
	if(solicitud.getCiudadano().getDocumento()!=null)
		numIdentificacion=solicitud.getCiudadano().getDocumento();
		
	//APELLIDO1
	String apellido1="&nbsp;";
	if(solicitud.getCiudadano().getApellido1()!=null)
		apellido1=solicitud.getCiudadano().getApellido1();
		
	//APELLIDO2
	String apellido2="&nbsp;";
	if(solicitud.getCiudadano().getApellido2()!=null)
		apellido2=solicitud.getCiudadano().getApellido2();
		
	//NOMBRE
	String nombre="&nbsp;";
	if(solicitud.getCiudadano().getNombre()!=null)
		nombre=solicitud.getCiudadano().getNombre();
  
  //IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}
	
  //OCULTAR DATOS DE RADICACION
  String ocultarRadicacion =(String)session.getAttribute("VER_RADICACION");
  if (ocultarRadicacion ==null){
   	ocultarRadicacion="TRUE";
  }else{
   	session.setAttribute("VER_RADICACION",ocultarRadicacion);
  }
  
	//HABILITAR CAMPOS OCULTOS DATOS ANTIGUO SISTEMA
	String ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR_ANTIGUO_SISTEMA);
	if( ocultarAntSist == null ){
		ocultarAntSist = "TRUE";
		session.setAttribute(WebKeys.OCULTAR_ANTIGUO_SISTEMA,ocultarAntSist);		
	}	

        //MOSTRAR U OCULTAR CAMPO NUMERO MATRICULAS SOLICITUD
        List liquidaciones = solicitud.getLiquidaciones();
        Iterator itLiq = liquidaciones.iterator();
        String valMatAgr = null;
        int numMatAgr = 0;
        if(itLiq.hasNext())
        {
          Liquidacion li = (Liquidacion) itLiq.next();
          valMatAgr = li.getNumMatAgr();
          if(valMatAgr!=null)
          numMatAgr = Integer.parseInt(valMatAgr);
          
        }
        

%>
    
<%
	//INICIALIZACIÓN DE HELPERS (ANTIGUO SISTEMA)
	AntiguoSistemaHelper ASHelper = new AntiguoSistemaHelper();

	ASHelper.setObtenerJSP(true);
    ASHelper.setProperties(request);
    ASHelper.setNLibroTipoAS(CDatosAntiguoSistema.LIBRO_TIPO_AS);
    ASHelper.setNLibroNumeroAS(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
    ASHelper.setNLibroPaginaAS(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
    ASHelper.setNLibroAnoAS(CDatosAntiguoSistema.LIBRO_ANO_AS);

    ASHelper.setNTomoNumeroAS(CDatosAntiguoSistema.TOMO_NUMERO_AS);
    ASHelper.setNTomoPaginaAS(CDatosAntiguoSistema.TOMO_PAGINA_AS);
    ASHelper.setNTomoMunicipioAS(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
    ASHelper.setNTomoAnoAS(CDatosAntiguoSistema.TOMO_ANO_AS);

    ASHelper.setNDocumentoFechaAS(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
    ASHelper.setNDocumentoTipoAS(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
    ASHelper.setNDocumentoNumeroAS(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
    ASHelper.setNDocumentoComentarioAS(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

	ASHelper.setNPrefijoOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS);
    ASHelper.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
    ASHelper.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
    ASHelper.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
    ASHelper.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
    ASHelper.setNIdVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
    ASHelper.setNNomVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
    ASHelper.setNIdOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
    ASHelper.setNNumOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
    ASHelper.setNIdDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO);
    ASHelper.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
    ASHelper.setNNumOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN);
    ASHelper.setNTipoNomOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN);
    ASHelper.setNTipoOficinaHiddenn(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN);
    ASHelper.setNIdOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN);	
    ASHelper.setLocal_OficinasHelperCambioNombreFormularioEnabled(true);
    ASHelper.setLocal_OficinasHelperCambioNombreFormularioValue("SUBTIPO_SOLICITUD");    
%>    
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>
function deshabilitarBoton() {
		document.getElementById('imageField_id').width=0;
}
function ocultarInfoTurno(text) {
	document.CONFRONTACION.VER_RADICACION.value = text;
	cambiarAccion('VER_RADICACION');
}

function ocultarAntiguoSistema(text) {
    document.SUBTIPO_SOLICITUD.ACCION.value = '<%=AWConfrontacion.OCULTAR_DATOS_ANTIGUO_SISTEMA%>';
	document.SUBTIPO_SOLICITUD.<%=WebKeys.OCULTAR_ANTIGUO_SISTEMA%>.value = text;
    document.SUBTIPO_SOLICITUD.submit();
}
	   
function cambiarAccion(text) {
    document.CONFRONTACION.ACCION.value = text;
    document.CONFRONTACION.submit();
}

function cambiarAccionSubtipo(text) {
    document.SUBTIPO_SOLICITUD.ACCION.value = text;
    document.SUBTIPO_SOLICITUD.submit();
}

function cambiarAccionAceptar(text) {
    document.SUBTIPO_SOLICITUD.ACCION.value = text;
}

function oficinas(nombre,valor,dimensiones){
	var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=gov.sir.core.web.acciones.comun.AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
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
	document.getElementById('<%=CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO%>').value ="";
	document.getElementById('<%=CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO%>').value ="";	
	document.getElementById('<%=CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM%>').value ="";
	document.getElementById('<%=CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA%>').value ="";	
}	

function eliminarMatriculas() {
    document.CONFRONTACION.ACCION.value = 'ELIMINAR';
    document.CONFRONTACION.submit();
}   

function autoFocus(){
	document.ASOCIAR_MAT.<%=CFolio.ID_MATRICULA%>.focus();
}

function cambiarSeleccion() {
    if(document.CONFRONTACION.seleccionado.checked == true){
	    setAllCheckBoxes('CONFRONTACION', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.CONFRONTACION.seleccionado.checked == false){
    	setAllCheckBoxes('CONFRONTACION', 'ELIMINAR_CHECKBOX', false);
    }
}
function validarNumMatriculas(){
    var matriculas = document.getElementsByName("ELIMINAR_CHECKBOX");
    var paramMatr = "0";
       paramMatr  =  document.getElementById("numMatAgr").value;
    
        
    if(paramMatr !== "0" &&  matriculas.length > 0)
    {
        
        if(paramMatr > matriculas.length)
        {
           
            if(confirm("Esta seguro que desea avanzar el turno. Faltan matriculas."))
            {
                cambiarAccionAceptar('CONFIRMAR');
            }
            else
                return false;
                
        }

    }else
    {
        cambiarAccionAceptar('CONFIRMAR');
    }
    
}
</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Confrontaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td></td>
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
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  
                  <td class="bgnsub">Encabezado del Documento </td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td class="campositem">
                        <%=tipoDocumento%>
						
                        
                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>N&uacute;mero</td>
                        <td class="campositem">
                        
                        <%=numDocumento%>
                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>Fecha</td>
                        <td class="campositem">
                        <%=fechaDocumento%>
                        
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <table width="100%" class="camposform">
                    <%if(isInternacional) {%>
                      <tr>
                        <td>Oficina internacional</td>
                        <td class="campositem">
                        <%=internacional%>
                        </td>
                      </tr>
                    <%} else {%>
                      <tr>
                        <td>Departamento</td>
                        <td class="campositem">
                        <%=departamento%>
                        </td>
                      </tr>
                      <tr>
                        <td>Municipio</td>
                        <td class="campositem">
                        <%=municipio%>
                        </td>
                      </tr>
                    </table>
                      <table width="100%" class="camposform">
                        <tr>
                          <td>Tipo</td>
                          <td class="campositem">
                          
                          <%=tipoOficina%>
                          
                          </td>
                          <td>N&uacute;mero</td>
                          <td class="campositem">
                          
                          <%=numOficina%>
                          
                          </td>
                        </tr>
                        <%} %>
                    </table>
                     
					</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos de Radicaci&oacute;n </td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
               <table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTAR">
						<%if(ocultarRadicacion.equals("FALSE")){%>
			        <tr>
			            <td><hr class="linehorizontal"></td>
			        </tr>
			        <tr>
			                <td></td>
			                <td width="16"><a href="javascript:ocultarInfoTurno('TRUE');campoactual('OCULTAR');"><img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></a></td>
			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}else{%>
			        <tr>
			                <td align="right" class="campostip04">Haga click para maximizar los datos de Radicaci&oacute;n del documento</td>
			                <td width="16"><a href="javascript:ocultarInfoTurno('FALSE');campoactual('OCULTAR');"><img img id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></a></td>
   			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}%>
			    </table>
              
              <br>
              
               <%if(ocultarRadicacion.equals("FALSE")){%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>N&uacute;mero del Turno Anterior</td>
                  <td class="campositem">
                  
                  <%=turnoAnterior%>
                  
                  </td>
                </tr>
              </table>
               <br>
                 <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Comentario</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem">
                  
                  <%=comentario%>
                  
                  </td>
                </tr>
              </table>
              <br>
              
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Documentos Entregados </td>
                </tr>
                
                <% 
                			for (int i=0; i<solicitud.getCheckedItems().size(); i++){
                				SolicitudCheckedItem ch=(SolicitudCheckedItem)solicitud.getCheckedItems().get(i);
                				%><tr>
                  					<td>&nbsp;</td>
                  					<td class="campositem"><%=ch.getCheckItem().getNombre()%></td>
                				 </tr><%
                			}
 		                        
						%>
                
              </table>

              <%}%>
              
              <br>
              <form action="confrontacion.do" method="post" name="CONFRONTACION" id="CONFRONTACION">              
                  <input type="hidden" name="ITEM" value="NINGUNO">
		          <input type="hidden" name="ACCION" value="">
		          <input type="hidden" name ="VER_RADICACION" id="VER_RADICACION">
		                        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Asociar una Matr&iacute;cula </td>
                  <td align='right' width='20' class="bgnsub"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>&nbsp;</td>                  
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
             

              <table width="100%">

                <tr>
                  <td>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
		          
                  </td>
                </tr>


                <tr>
                    
                    <td>
            
                        <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                  
                <td>Numero de Matriculas de la Solicitud</td>
               <td class="campositem" width="10%" style="text-align: center">
                     <% if(valMatAgr!=null){ %>
            <input type="hidden" id="numMatAgr" name="numMatAgr" value="<%= valMatAgr %>">
                     
                    <%= numMatAgr %>
                <%} %>
                </td>
                        </tr>
            </table>
                    
                   
              <table border="0" align="left" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                  
                <td>Eliminar Seleccionadas</td>
                <td>
                  <a href="javascript:eliminarMatriculas()"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                </td>
              </tr>
            </table>    
                </form>            
            
            <P>&nbsp;</P>                                
<br>
              	<form action="confrontacion.do" method="post" name="ASOCIAR_MAT" id="ASOCIAR_MAT">
           		<input type="hidden" name="ACCION" value="ASOCIAR_UNA_MATRICULA">   
                <table class="camposform" width="100%"><tr>
					
					<td width="50%">N&uacute;mero de Matr&iacute;cula</td>
					<td width="25%">	
						<table class="camposform" width="95%">
							<tr>
								<td>
								<%=idCirculo%>-
								<input name="<%=CFolio.ID_MATRICULA%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
						</table>
					</td>
                    <td width="25%" align="left">
                      	<table border="0" cellpadding="0" cellspacing="2" class="camposform" width="90%">
                      		<tr> 
                      			
                      			<td>Agregar Matr&iacute;cula</td>
                      			<td><input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0""> 
                      		</tr>
                      	</table>
                    </td>
            </form>
                </tr>
                <tr>
            <form action="confrontacion.do" method="post" name="ASOCIAR_RAN" id="ASOCIAR_RAN">
            <input type="hidden" name="ACCION" value="ASOCIAR_UN_RANGO">
					
					<td width="50%">Rango de Matr&iacute;culas</td>

					<td width="25%">
						<table class="camposform" width="95%">
							<tr>
								<td>
									<%=idCirculo%>-
									<input name="<%=CFolio.ID_MATRICULA_RL%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
							<tr>
								<td>
									<%=idCirculo%>-
									<input name="<%=CFolio.ID_MATRICULA_RR%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
						</table>
					</td>
                   	<td width="25%" align="left">
                   	  	<table border="0" cellpadding="0" cellspacing="2" class="camposform" width="90%">
                      		<tr> 
                      			
                      			<td>Agregar Rango</td>
                      			<td><input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0""> 
                      		</tr>
                      	</table>
                   	</td>
            </form>
                </tr>
                </table></td></tr>
                
              </table>
              
              
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Subtipo de Solicitud</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
	        
	        
	            <form action="confrontacion.do" method="post" name="SUBTIPO_SOLICITUD" id="SUBTIPO_SOLICITUD">
	            <input type="hidden" name="ACCION" value="CONFIRMAR">
				<input type="hidden" name="<%=WebKeys.OCULTAR_ANTIGUO_SISTEMA%>" value="TRUE">	            
				<input name="Depto" type="hidden" id="id_depto" value="">
			    <input name="Depto" type="hidden" id="nom_Depto" value="">
			    <input name="Mpio" type="hidden" id="id_munic" value="">
			    <input name="Mpio" type="hidden" id="nom_munic" value="">    
			    <input name="Ver" type="hidden" id="id_vereda" value="">
			    <input name="Ver" type="hidden" id="nom_vereda" value="">  
            
                <tr>
                  
                  <td>Subtipo solicitud</td>
                  <td>
                  <%try {
                  			subTipoSolicitud.setCssClase("camposformtext");
                   			subTipoSolicitud.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						
					%>
                  
                  </td>
                  <%if(turno.getIdFase().equals(CFase.REG_CONFRONTAR)) {%>
                  	<td align="right"><a href="javascript:cambiarAccionSubtipo('<%=AWConfrontacion.GURDAR_CAMBIOS_CONFRONTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                  <%} %>
                </tr>
              </table>
              <br>
              
              <!-- Turnos posteriores -->
               <%List turnosPosteriores =(List)session.getAttribute(WebKeys.TURNOS_POSTERIORES_CONF_TESTAMENTO);
               		if(turnosPosteriores!=null && turnosPosteriores.size()>0){
               %>
               <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                  El turno ha pasado por la fase testamentos y tiene turnos posteriores:</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%" class="camposform">
               <tr>
               <%
               	Turno turnoPos;
               	String turnoMostrar;
               	int i=turnosPosteriores.size()-1;
               	List turnosAdd=new ArrayList();
               	while(i>=0){
	               	turnoPos=(Turno)turnosPosteriores.get(i);
	               	turnoMostrar=turnoPos.getIdWorkflow();
	               	if(!turnosAdd.contains(turnoMostrar)){
		               	turnosAdd.add(turnoMostrar);%>
						<tr>
		               		<td ><%=turnoMostrar%></td>
	               		</tr>
	               	<%}%>
			  <%
			  	i--;}%>
			  	</tr>
              </table>
               <%}%>
                
              
              
              
               <table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTAR">
				<%if(ocultarAntSist.equals("FALSE")){%>
			        <tr>
			            <td><hr class="linehorizontal"></td>
			        </tr>
			        <tr>
			                <td></td>
			                <td width="16"><a href="javascript:ocultarAntiguoSistema('TRUE');campoactual('OCULTAR');"><img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></a></td>
			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}else{%>
			        <tr>
			                <td align="right" class="campostip04">Haga click para maximizar los datos de Antiguo Sistema</td>
			                <td width="16"><a href="javascript:ocultarAntiguoSistema('FALSE');campoactual('OCULTAR');"><img img id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></a></td>
   			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}%>
			    </table>              
              
              <%if(ocultarAntSist.equals("FALSE")){%>
              
		       <table width="100%" border="0" cellpadding="0" cellspacing="0" id="idOpcionales"> 
                <tr>
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Modificar Datos de Antiguo sistema </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>              

              <%
					try {
						cargarDatosAntiguoSistema(request ,solicitud.getDatosAntiguoSistema());
						ASHelper.render( request, out );

					}
					catch( HelperException re ){
						out.println( "ERROR " + re.getMessage() );
					}              
              %>              
              
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:cambiarAccionSubtipo('<%=AWConfrontacion.ACTUALIZAR_DATOS_ANTIGUO_SISTEMA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>                  
                  <td>&nbsp;</td>
                </tr>
              </table>
              
              <%}%>
              
              <hr class="linehorizontal">
			<%if(turno.getIdFase().equals(CFase.REG_CONFRONTAR)) {%>              
              <table width="100%" class="camposform">
                <tr>
                 
                    <td width="140"><input name="imageField" id="imageField"   onclick="return validarNumMatriculas()" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  width="139" height="21" border="0"  ></td>
                  <td width="15%"><a href="admin.relacion.view?CONFRONTACION=TRUE"><img src="<%=request.getContextPath()%>/jsp/images/btn_crear_relaciones.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>
            </form>
            <form name="logOut2" action="seguridad.do" method="post">
				  	<td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cerrar.gif" width="150" height="21" border="0" >
                  	<input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                  	<% Proceso proceso = ((Proceso)session.getAttribute(WebKeys.PROCESO));
                		if(proceso != null){
                	%>
                	
                		<input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
                
                	<%
                		}
                	%>
                	</td>
                  	<td>&nbsp;</td>
                </tr>
                </table>
            </form>´
            <%}else{ %>
            	<table width="100%" class="camposform">
                	<tr>
                 
                  	<td width="140"><input name="imageField" id="imageField" onClick="return validarNumMatriculas()" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  width="139" height="21" border="0" ></td>
                  	<td>&nbsp;</td>
                	</tr>
              	</table>
               </form>
            <%} %>
            
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
  <tr> 
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
  </tr>
</table>
<script>
	autoFocus();
</script>
<%!
	public void cargarDatosAntiguoSistema(HttpServletRequest req, gov.sir.core.negocio.modelo.DatosAntiguoSistema das){
	
		HttpSession sesion = req.getSession();
		if (das !=null){
			
			//DATOS DEL LIBRO
			sesion.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, das.getLibroTipo());
			sesion.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, das.getLibroNumero());
			sesion.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, das.getLibroPagina());
			sesion.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, das.getLibroAnio());
			
			//DATOS DEL TOMO
			sesion.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, das.getTomoNumero());
			sesion.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, das.getTomoPagina());
			sesion.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, das.getTomoMunicipio());
			sesion.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, das.getTomoAnio());		
			
			// COMENTARIO DATOS DE ANTIGUO SISTEMA
			sesion.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, das.getComentario());
			
			
			//DATOS DEL DOCUMENTO
			if(das.getDocumento()!=null){

				//DATOS DEL DOCUMENTO
				gov.sir.core.negocio.modelo.Documento documento = das.getDocumento();
				sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, documento.getTipoDocumento().getIdTipoDocumento());
				sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS, documento.getTipoDocumento().getIdTipoDocumento());
				sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS, documento.getNumero());
			
				if(documento.getFecha()!=null)	
					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS, DateFormatUtil.format(documento.getFecha()));
				else
					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS, "");
				sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS, documento.getComentario());
			
				//DATOS DE LA OFICINA ORIGEN			
				gov.sir.core.negocio.modelo.OficinaOrigen oficina = documento.getOficinaOrigen();
				if(oficina!=null){

					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA, oficina.getIdOficinaOrigen());
					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM, oficina.getNombre());
					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO, oficina.getTipoOficina().getNombre());
					sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO, oficina.getTipoOficina().getIdTipoOficina());
					
					Vereda vereda = oficina.getVereda();
					if(vereda!=null){
					
						sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA, vereda.getIdVereda());
						sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA, vereda.getNombre());

						gov.sir.core.negocio.modelo.Municipio municipio = vereda.getMunicipio();
						if(municipio!=null){
							sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC, municipio.getIdMunicipio());
							sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC, municipio.getNombre());

							gov.sir.core.negocio.modelo.Departamento departamento = municipio.getDepartamento();
							if(departamento!=null){
								sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO, departamento.getIdDepartamento());
								sesion.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO, departamento.getNombre());
							}
						}
					}
				}

			}
			
		}
	}
%>