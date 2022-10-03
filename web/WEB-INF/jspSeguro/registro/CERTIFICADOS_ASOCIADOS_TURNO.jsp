<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCheckedItem"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
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
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado" %>
<%@page import="java.util.ArrayList" %> 

<%	 
	//OBTENER TURNO
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
	List sfolios=solicitud.getSolicitudFolios();
		
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
%>
    
   
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>
function ocultarInfoTurno(text) {
	document.REGISTRO.VER_RADICACION.value = text;
	cambiarAccion('VER_RADICACION');
}

	   
function cambiarAccion(text) {
    document.REGISTRO.ACCION.value = text;
    document.REGISTRO.submit();
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
    document.REGISTRO.ACCION.value = 'ELIMINAR';
    document.REGISTRO.submit();
}   

function autoFocus(){
	document.ASOCIAR_MAT.<%=CFolio.ID_MATRICULA%>.focus();
}

function cambiarSeleccion() {
    if(document.REGISTRO.seleccionado.checked == true){
	    setAllCheckBoxes('REGISTRO', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.REGISTRO.seleccionado.checked == false){
    	setAllCheckBoxes('REGISTRO', 'ELIMINAR_CHECKBOX', false);
    }
}

	function openCertificadosAsociados(nombre,valor,dimensiones){
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
    }

</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<form action="turnoLiquidacionRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
<input type="hidden" name="ACCION" value="<%=AWLiquidacionRegistro.REFRESCAR_AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO%>">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registro de Documentos </td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar certificados asociados a turnos ya radicados</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Encabezado del Documento </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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
              
                <!-- CERTIFICADOS ASOCIADOS  -->
<%              //Ya registrados 
			    List matriculasYaRegistrados = new ArrayList();
				List certificadosAsociadosYaRegistrados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO);    
			    if(certificadosAsociadosYaRegistrados ==null){
			    	certificadosAsociadosYaRegistrados = new ArrayList();
			    }	
		    	
			    
				//HAY QUE DETERMINAR EL NÚMERO DE REGISTROS QUE SON DE ANTIGUO SISTEMA Y DE SEGREGACIÓN.
				Iterator itCertificadosYaRegistrados = certificadosAsociadosYaRegistrados.iterator();
				int numDatosAntSistYaRegistrados = 0;
				int numDatosSegregacionYaRegistrados = 0;	
				int numCopiasAntSistYaRegistrados = 0;
				int numCopiasSegregacionYaRegistrados = 0;
				int numDatosMatriculaYaRegistrados = 0;	
				
				Iterator itmatriculasYaRegistrados = null;
				
				while(itCertificadosYaRegistrados.hasNext()){
					SolicitudCertificado solCert = (SolicitudCertificado)itCertificadosYaRegistrados.next();
					
					if(solCert.getComentario()!=null){			
						numDatosSegregacionYaRegistrados++;
						numCopiasSegregacionYaRegistrados+=solCert.getNumeroCertificados();
					}
					if(solCert.getDatosAntiguoSistema()!=null){
						numDatosAntSistYaRegistrados ++;
						numCopiasAntSistYaRegistrados+=solCert.getNumeroCertificados();
					}
					if(solCert.getSolicitudFolios().size()>0){
						numDatosMatriculaYaRegistrados++;
					}					
				}	
				
				if(numCopiasSegregacionYaRegistrados!= 0 && numDatosSegregacionYaRegistrados!=0){
					numCopiasSegregacionYaRegistrados = numCopiasSegregacionYaRegistrados / numDatosSegregacionYaRegistrados;
				}
				if(numCopiasAntSistYaRegistrados!= 0 && numDatosAntSistYaRegistrados!=0){
					numCopiasAntSistYaRegistrados = numCopiasAntSistYaRegistrados / numDatosAntSistYaRegistrados;
				}	
				

%>              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="titulotbcentral">
                  			Certificados Asociados Ya registrados
                  </td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              			

							
							<%
								if(certificadosAsociadosYaRegistrados!=null&&certificadosAsociadosYaRegistrados.size()>0){
							%>
							

	              			<%
	              				if(numDatosMatriculaYaRegistrados>0){
	              			%>							
							
        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  			                  
			                  <TD class="titulotbcentral" >Certificados asociados con matrícula</TD>
			                  </tr>
			                  <tr> 
			                  <td width="20">&nbsp;</td>			                  			                  
			                  <TD class="titulotbcentral" >
							    <%TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();                     			    
              			        try {
			                      tablaHelper.setColCount(5);
			                      tablaHelper.setListName(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_YA_REGISTRADOS_AGREGAR_TURNO);
			                      //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
			               		  tablaHelper.render(request, out);
			                    }
			                    catch(HelperException re){
			                      out.println("ERROR " + re.getMessage());
			                    }%>			                  
			                  </TD>
			                  </tr>
			                  
			                </table> 
			                <%
			                }
			                %>                    
              
              			<%
              				if(numDatosAntSistYaRegistrados>0){
              			%>              

        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  			                  
			                  <TD class="titulotbcentral" width="30%">Certificados asociados antiguo sistema</TD>
			                  <td width="20%" align='right'>N&uacute;mero de unidades</td>
			                  <td class='campositem' width="15%"> 
			                    <%=""+numDatosAntSistYaRegistrados%>
			                  </td>
			                  <td width="20%" align='right'>Cantidad</td>
			                  <td class='campositem' width="15%"> 			     				
			                    <%=""+numCopiasAntSistYaRegistrados%>
			                  </td>		
			                  </tr>
			                </table>  
			                
              			<%
              				}
              			%>	
              			<%
              				if(numDatosSegregacionYaRegistrados>0){
              			%>              					                     
			                
        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  
			                  <TD class="titulotbcentral" width="30%">Certificados asociados sin matrícula</TD>			                  
			                  <td width="20%" align='right'>N&uacute;mero de unidades</td>
			                  <td class='campositem' width="15%"> 
			                    <%=""+numDatosSegregacionYaRegistrados%>
			                  </td>
			                  <td width="20%" align='right'>Cantidad</td>
			                  <td class='campositem' width="15%">			     				
			                    <%=""+numCopiasSegregacionYaRegistrados%>
			                  </td>		
			                  </tr>
			                </table>   	
              			<%
              				}
              			%>			                		                
			                  
              			<%
              				}//FIN DE SI EXISTEN CERTIFICADOS ASOCIADOS
              			%>
			          
				<br>   

<%              
			    List matriculas = new ArrayList();
				List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);    
			    if(certificadosAsociados ==null){
			    	certificadosAsociados = new ArrayList();
			    }	
		    	
			    
				//HAY QUE DETERMINAR EL NÚMERO DE REGISTROS QUE SON DE ANTIGUO SISTEMA Y DE SEGREGACIÓN.
				Iterator itCertificados = certificadosAsociados.iterator();
				int numDatosAntSist = 0;
				int numDatosSegregacion = 0;	
				int numCopiasAntSist = 0;
				int numCopiasSegregacion = 0;
				int numDatosMatricula = 0;	
				
				Iterator itMatriculas = null;
				
				while(itCertificados.hasNext()){
					SolicitudCertificado solCert = (SolicitudCertificado)itCertificados.next();
					
					if(solCert.getComentario()!=null){			
						numDatosSegregacion++;
						numCopiasSegregacion+=solCert.getNumeroCertificados();
					}
					if(solCert.getDatosAntiguoSistema()!=null){
						numDatosAntSist ++;
						numCopiasAntSist+=solCert.getNumeroCertificados();
					}
					if(solCert.getSolicitudFolios().size()>0){
						numDatosMatricula++;
					}					
				}	
				
				if(numCopiasSegregacion!= 0 && numDatosSegregacion!=0){
					numCopiasSegregacion = numCopiasSegregacion / numDatosSegregacion;
				}
				if(numCopiasAntSist!= 0 && numDatosAntSist!=0){
					numCopiasAntSist = numCopiasAntSist / numDatosAntSist;
				}	
				

%>              
              
			    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="titulotbcentral">
                  			Certificados Asociados que se van a agregar
                  </td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
                </table>
              			

							
							<%
								if(certificadosAsociados!=null&&certificadosAsociados.size()>0){
							%>
							

	              			<%
	              				if(numDatosMatricula>0){
	              			%>							
							
        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  			                  
			                  <TD class="titulotbcentral" >Certificados asociados con matrícula</TD>
			                  </tr>
			                  <tr> 
			                  <td width="20">&nbsp;</td>			                  			                  
			                  <TD class="titulotbcentral" >
							    <%TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();                     			    
              			        try {
			                      tablaHelper.setColCount(5);
			                      tablaHelper.setListName(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
			                      //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
			               		  tablaHelper.render(request, out);
			                    }
			                    catch(HelperException re){
			                      out.println("ERROR " + re.getMessage());
			                    }%>			                  
			                  </TD>
			                  </tr>
			                  
			                </table> 
			                <%
			                }
			                %>                    
              
              			<%
              				if(numDatosAntSist>0){
              			%>              

        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  			                  
			                  <TD class="titulotbcentral" width="30%">Certificados asociados antiguo sistema</TD>
			                  <td width="20%" align='right'>N&uacute;mero de unidades</td>
			                  <td class='campositem' width="15%"> 
			                    <%=""+numDatosAntSist%>
			                  </td>
			                  <td width="20%" align='right'>Cantidad</td>
			                  <td class='campositem' width="15%"> 			     				
			                    <%=""+numCopiasAntSist%>
			                  </td>		
			                  </tr>
			                </table>  
			                
              			<%
              				}
              			%>	
              			<%
              				if(numDatosSegregacion>0){
              			%>              					                     
			                
        					<table width="100%" class="camposform">
			                  <tr> 
			                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>			                  
			                  <TD class="titulotbcentral" width="30%">Certificados asociados sin matrícula</TD>			                  
			                  <td width="20%" align='right'>N&uacute;mero de unidades</td>
			                  <td class='campositem' width="15%"> 
			                    <%=""+numDatosSegregacion%>
			                  </td>
			                  <td width="20%" align='right'>Cantidad</td>
			                  <td class='campositem' width="15%">			     				
			                    <%=""+numCopiasSegregacion%>
			                  </td>		
			                  </tr>
			                </table>   	
              			<%
              				}
              			%>			                		                
			                  
              			<%
              				}//FIN DE SI EXISTEN CERTIFICADOS ASOCIADOS
              			%>


              <table width="100%" class="camposform">
                <tr>
                  <td width="20">
                  		<img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td>
                  		<a href="javascript:openCertificadosAsociados('turno.registro.liquidacion.certificado.asociado.view','Asociado','width=900,height=650,scrollbars=yes,top=0,left=0');">
                  		<img src="<%=request.getContextPath()%>/jsp/images/btn_certificados_asociados.gif" width="190" height="21" border="0" alt="Modificar certificados masivos">                  		
                  		</a>
                  	</td>
                </tr>
              </table>
              <!--FIN DE CERTIFICADOS ASOCIADOS-->

              <br>

              
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" onclick="cambiarAccion('<%=AWLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO%>')" id="Folio"></td>
                  <td width="140"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="139" height="21" border="0" onclick="cambiarAccion('<%=AWLiquidacionRegistro.REGRESAR_LIQUIDACION%>')" id="Folio"></td>
                  <td>&nbsp;</td>
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

