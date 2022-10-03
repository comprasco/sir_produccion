<%@page import="org.auriga.core.web.* "%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%> 
<%@page import="gov.sir.core.web.helpers.registro.ActosHelper"%>
<%@page import="gov.sir.core.web.helpers.registro.AgregarActosHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.List,java.util.Iterator"%>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado" %>
<%@page import="java.util.ArrayList" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CCiudadano" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %> 
<%@page import="gov.sir.core.negocio.modelo.Proceso" %> 

<% 
	Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
	
	ListaElementoHelper tipoActoHelper = new ListaElementoHelper();
	ListaElementoHelper tipoDerechoHelper = new ListaElementoHelper();
	ListaElementoHelper tipoTarifaHelper = new ListaElementoHelper();
	ListaElementoHelper tipoIdHelper= new ListaElementoHelper();
	ActosHelper actoHelper = new ActosHelper();
	AgregarActosHelper nactoHelper = new AgregarActosHelper();
	TextHelper textHelper = new TextHelper();
	
	//settear si se muestra o no impuestos dependiendo del circulo
	actoHelper.setMostrarImpuesto(circulo.isCobroImpuesto());
	nactoHelper.setMostrarImpuesto(circulo.isCobroImpuesto());
	nactoHelper.setDesHabilitarDerechos(true);
	nactoHelper.setMostrarDerechos(true);
	
	Proceso proceso = new Proceso();
	proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_REGISTRO));
	proceso.setNombre("REGISTRO");
	session.setAttribute(WebKeys.PROCESO, proceso);
			
	session.setAttribute(WebKeys.ES_TURNO_MANUAL, new Boolean(true));
	
	session.removeAttribute(WebKeys.POP_CERTIFICADOS_ASOCIADOS);
   %>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>

	
   function cambiarAccion(text) {
	   document.REGISTRO.ACCION.value = text;
	   document.REGISTRO.submit();
   }
   
   function cambioTipo(text){
	   	document.getElementById('ID_ACTO').value = document.getElementById('TIPO_ACTO').value
	   	buscar(text);
   }
   
   function cambioID(text){
	   	document.getElementById('tIPO_ACTO').value = document.getElementById('ID_ACTO').value
	   	buscar(text);
   }
   
   function buscar(text) {
	   document.REGISTRO.ACCION.value = text;
	   document.REGISTRO.submit();
   }
   
   function agregarActo() {
	   document.REGISTRO.ACCION.value = 'AGREGAR_ACTO';
   }
	
   function quitar(text) {
	   document.REGISTRO.ITEM.value = text;
	   cambiarAccion('ELIMINAR_ACTO');
   }
   
   function openwindow(nombre,valor,dimensiones)
   {
	  document.getElementById('Depto').value=valor;
	  document.getElementById('Mpio').value=valor+"_municipio";
	  document.getElementById('Ver').value=valor+"_vereda";
	  popup=window.open(nombre,valor,dimensiones);
	  popup.focus();
   }

   function juridica(nombre,valor,dimensiones)
   {
     document.getElementById('natjuridica').value=valor;
     popup=window.open(nombre,valor,dimensiones);
     popup.focus();
   }

   function oficinas(nombre,valor,dimensiones)
   {
     document.getElementById('tipo_oficina').value=valor+"_tipo";
     document.getElementById('numero_oficina').value=valor+"_numero";
     popup=window.open(nombre,valor,dimensiones);
     popup.focus();
   }

   function MM_openBrWindow(theURL,winName,features) {
  		window.open(theURL,winName,features);
   }
  
  	function postCertificadoAsociado(){
     <%if (request.getSession().getAttribute("post") != null){ 
   	 %>
   		document.REGISTRO.VALOR_OTRO_IMPUESTO.focus();
   	<%}%>	
  	}
  	
    function campoactual(id){
		if (document.getElementById("ultimo_campo_editado")!=null){
			document.getElementById("ultimo_campo_editado").value=id;
		}
	}
  	
    function openCertificadosAsociados(nombre,valor,dimensiones){
		document.REGISTRO.ACCION.value = '<%=AWLiquidacionRegistro.PRESERVAR_INFO_LIQUIDAR%>';
		document.REGISTRO.submit();
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
    }

</script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registro de Documentos </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">              </td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Liquidaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          
         
         
          <form action="turnoManualRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
				<input type="hidden" name="ACCION" value="AGREGAR_ACTO">
 			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Actos </td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <input type="hidden" name="ITEM" value="NINGUNO">
              <% try {
          			    actoHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
              <br>
              	<% try {
          			    nactoHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
        
              <hr class="linehorizontal">

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Otro Impuesto</td>
                  <td width="16" class="bgnsub">
                  <img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21">
                  </td>
                  <td width="16" class="bgnsub">
                  <img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21">
                  </td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Valor</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("VALOR_OTRO_IMPUESTO");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("VALOR_OTRO_IMPUESTO");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  
                  </td>
                  <td>Descripci&oacute;n</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("DESCRIPCION");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("DESCRIPCION");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                </tr>
              </table>

			  <hr class="linehorizontal">
			  
			  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Mora</td>
                  <td width="16" class="bgnsub">
                  <img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21">
                  </td>
                  <td width="16" class="bgnsub">
                  <img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21">
                  </td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Valor</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("VALOR_MORA");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("VALOR_MORA");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  
                  </td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
                  
                  <% try {
                  				List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID) ;
                   				
                   				String selected = (String)session.getAttribute(CCiudadano.TIPODOC);
                   				if(selected==null){
                   					selected = CCiudadano.TIPO_DOC_ID_SECUENCIA;
                   				}
                   				tipoIdHelper.setOrdenar(false);
                  				tipoIdHelper.setTipos(tiposDoc);
                  			    tipoIdHelper.setNombre("TIPODOC");
                  			    tipoIdHelper.setId("TIPODOC");                  			    
				                tipoIdHelper.setCssClase("camposformtext");
                  			    tipoIdHelper.setSelected(selected);
                  			    tipoIdHelper.setShowInstruccion(false);
                  			    tipoIdHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212">
                  
                  <% try {
 		                        textHelper.setNombre("DOCUMENTO");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("DOCUMENTO");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombres-Apellidos / Raz&oacute;n Social</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("APELLIDO1");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("APELLIDO1");
                  			    textHelper.setSize("45");
								textHelper.render(request,out);
                  			    textHelper.setSize("20");								
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
				 
				  <td>Tel&eacute;fono</td>
                  <td><% try {
 		                        textHelper.setNombre("TELEFONO");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("TELEFONO");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
					</td>         
						
                </tr>						
				  
				<%--
				
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombre</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("NOMBRE");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("NOMBRE");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                  <td>Segundo Apellido</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre("APELLIDO2");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("APELLIDO2");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                </tr>
                
                --%>
                
              </table>
              <hr class="linehorizontal">
              <%--
                <!-- CERTIFICADOS ASOCIADOS -->
<%                
			    List matriculas = new ArrayList();
				List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);    
			    if(certificadosAsociados==null){
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
                  			Certificados Asociados
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
			                  <TD class="titulotbcentral" width="30%">Certificados asociados segregación</TD>			                  
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
				--%>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Liquidar Valor</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>                                   
                  <td width="150"><a href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.LIQUIDAR_CONTINUAR%>')"><img src="<%= request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0" alt="Liquidar la solicitud y proceder a registrar el pago"></a></td>
                  <td><a href="admin.turno.manual.registro.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Cancelar" width="150" height="21" border="0" id="Cancelar" alt="Regresar a modificar los datos de la solicitud de registro"></a></td>
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


        <%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("REGISTRO");			
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>


    </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
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
<%--<script>
	postCertificadoAsociado();	
</script>--%>

