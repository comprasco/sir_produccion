<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarTestamento"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%> 
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWTestamentos"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion"%>
<jsp:directive.page import="java.util.List"/>
 
<%	//INICIALIZACIÓN DE VARIABLES
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    SolicitudCorreccion solicitudAct =(SolicitudCorreccion)turno.getSolicitud();
    SolicitudRegistro solicitud = (SolicitudRegistro) session.getAttribute(CTestamentos.SOLICITUD_MODIFICADA);
    Turno turnoAnterior = solicitudAct.getTurnoAnterior();
    //Testamento testamento = ((SolicitudRegistro)turnoAnterior.getSolicitud()).getTestamento();
    //request.setAttribute(WebKeys.TESTAMENTO_SESION,((SolicitudRegistro)turnoAnterior.getSolicitud()).getTestamento());
    MostrarFechaHelper fecha = new MostrarFechaHelper();
    boolean editable = Boolean.parseBoolean(session.getAttribute("editable").toString()); 
    
    String nomOficina= "";
	
    boolean isInternacional=false;
	boolean isComentarioAnotacion=false;
    if(solicitud !=null){
    	if(solicitud.getDocumento()!=null){
    		if(solicitud.getDocumento().getComentario()!=null){
    			isComentarioAnotacion=true;
    			nomOficina= solicitud.getDocumento().getComentario();
    		}    	
    		if(solicitud.getDocumento().getOficinaOrigen()!=null){
    			
    			if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
	    			if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
		    			nomOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
	    			}
    			}
    			if(solicitud.getDocumento().getOficinaOrigen().getNombre()!=null){
    				nomOficina= nomOficina + " " +solicitud.getDocumento().getOficinaOrigen().getNombre();
    			}
    		}
    		if(solicitud.getDocumento().getOficinaInternacional()!=null){
    			isInternacional=true;
    			nomOficina= solicitud.getDocumento().getOficinaInternacional();
    		}
    	}
    }
    
    request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,solicitud.getDocumento().getTipoDocumento().getNombre());
    request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,solicitud.getDocumento().getNumero());
    request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,solicitud.getDocumento().getFecha());
    if(isComentarioAnotacion){
    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    }else if(isInternacional){
    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    }else{
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,solicitud.getDocumento().getOficinaOrigen().getNumero());
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
	    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
	}
	
	//Sí el turno tiene testamento es porque va a ser editado y se actualizan los datos en el formulario
	Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
        
	if(testamento!=null){
		request.getSession().setAttribute(CTestamentos.TOMO,testamento.getTomo());	
		request.getSession().setAttribute(CTestamentos.NUMERO_ANOTACIONES,testamento.getNumeroAnotaciones());	
		request.getSession().setAttribute(CTestamentos.NUMERO_COPIAS,testamento.getNumeroCopias());	
		request.getSession().setAttribute(CTestamentos.REVOCA_ESCRITURA,testamento.getRevocaEscritura());	
		request.getSession().setAttribute(CTestamentos.OBSERVACION,testamento.getObservacion());	
		
	}
%>

<SCRIPT>
function cambiarAccion(text) {
    document.DETALLE_TESTAMENTO.<%=WebKeys.ACCION%>.value = text;
    document.DETALLE_TESTAMENTO.submit();
}
function cambiarAccion(text,idturno) {
    document.DETALLE_TESTAMENTO.<%=WebKeys.ACCION%>.value = text;
    document.DETALLE_TESTAMENTO.<%=CTurno.ID_TURNO%>.value = idturno;
    document.DETALLE_TESTAMENTO.submit();
}
</SCRIPT>

<script>
<!--Modifica Pablo Quintana Junio 17 2008 - Enviar turno testamento a correcion encabezado-->
function enviarConfrontacion(text){
		if(confirm('¿Esta seguro que desea enviar el turno a correccion de encabezado?')){
		cambiarAccion(text);
	}
}
</script>
<!-- Modirfica Pablo Quintana - SIR-62- jUNIO 23 2008 -->
<script>
function funcionQuitarTestador(pos,text){
	if(confirm('¿Está seguro eliminar testador?')){
		document.DETALLE_TESTAMENTO.POSICION.value=pos;
		cambiarAccion(text);
	}
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
    <td class="tdtablaanexa02">
    
    

	  <form name="DETALLE_TESTAMENTO"  id="DETALLE_TESTAMENTO" method="post" action="trasladoTurno.do">
	  <input type="hidden" name="<%=WebKeys.ACCION%>" value="">
          <input type="hidden" name="TIPO_CONSULTA" value="<%=CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
          <input type="hidden" name="<%=CTurno.ID_TURNO%>" value="<%=turno.getIdWorkflow()%>"> 
	<!-- Pablo Quintana - SIR-62 - Junio 23 2008 -->
	  <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" >

	  <!--PARTE 1-->
	  <!--OPCIONES PARA LA INSCRIPCIÓN DE TESTAMENTOS-->
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">TESTAMENTO</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">


			<!--DATOS DEL DOCUMENTO-->

			<table width="100%" class="camposform">
                <tr>
                  
                  <td>Datos del Documento</td>
                </tr>

                <tr>
                  
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td width="3%" align="right">Tipo</td>
                        <td class="campositem" align="left">
                        <%
                        try {
							    TextHelper tiposDocHelper = new TextHelper();
		 		                tiposDocHelper.setId(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setCssClase("camposformtext");
								tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setEditable(false);
								tiposDocHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>
                        </td>
                        <td width="20%">&nbsp;</td>
                        <td width="5%" align="right">N&uacute;mero</td>
                        <td class="campositem" align="left">
                        <%
						TextHelper textHelperRead = new TextHelper();                        
                        try {

		 		                textHelperRead.setTipo("text");
								textHelperRead.setNombre(CFolio.ANOTACION_NUM_DOCUMENTO);
								textHelperRead.setCssClase("camposformtext");
								textHelperRead.setId(CFolio.ANOTACION_NUM_DOCUMENTO);
								textHelperRead.setEditable(false);
								textHelperRead.render(request,out);
						}catch(HelperException re){
						 	 out.println("ERROR " + re.getMessage());
						}
					 %>
                        </td>
                        <td width="20%">&nbsp;</td>
                        <td width="3%" align="right">Fecha</td>
                        <td class="campositem">
                        <%try {
		 		                java.util.Date dateDoc=(java.util.Date)request.getSession().getAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
								fecha.setDate(dateDoc);
								fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
								fecha.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>
					 	
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                   
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                 
                  <td>
				 <%if(isComentarioAnotacion){%>
	                  <table width="100%" class="camposform">
                        <td width="17%">Oficina Origen</td>
                        <td class="campositem">
                        <% try {
 		                        textHelperRead.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
                  			    textHelperRead.setCssClase("camposformtext");
                  			    textHelperRead.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
								textHelperRead.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                        </td>
                        <td width="50%">&nbsp;</td>
                      </table>						
					  <%
					  }else if(!isInternacional){%>
	                  <table width="100%" class="camposform">
	                      <tr>
	                        <td width="30">Codigo</td>
	                        <td class="campositem">
	                       <%try {
			 		                textHelperRead.setTipo("text");
									textHelperRead.setCssClase("camposformtext");				
									textHelperRead.setNombre("ANOTACION_OFICINA_DOCUMENTO_COD");
									textHelperRead.setId("ANOTACION_OFICINA_DOCUMENTO_COD");
									textHelperRead.setEditable(false);
									textHelperRead.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                        </td>
	                        <td width="50">Nombre</td>
	                        <td class="campositem">
	                         <%try {
									textHelperRead.setTipo("text");
									textHelperRead.setCssClase("camposformtext");	
									textHelperRead.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM");
									textHelperRead.setId("ANOTACION_OFICINA_DOCUMENTO_NOM");
									textHelperRead.setEditable(false);
									textHelperRead.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                        </td>
	                     </tr>
	                     <tr>
	                     	<td width="80">Departamento</td>
	                            <td class="campositem">
	                            <%try {
			 		                textHelperRead.setTipo("text");
									textHelperRead.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
									textHelperRead.setCssClase("camposformtext");
									textHelperRead.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
									textHelperRead.setEditable(false);
									textHelperRead.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                       <td width="60">Municipio</td>
	                            <td class="campositem">
	                            <%try {
			 		                textHelperRead.setTipo("text");
									textHelperRead.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
									textHelperRead.setCssClase("camposformtext");
									textHelperRead.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
									textHelperRead.setEditable(false);
									textHelperRead.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                            </td>
	                        
	                     </tr>
	                  </table>
	                 <%}else{%>
	                  <table width="100%" class="camposform">
                        <td width="17%">Oficina internacional</td>
                        <td class="campositem">
                        <% try {
 		                        textHelperRead.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
                  			    textHelperRead.setCssClase("camposformtext");
                  			    textHelperRead.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
								textHelperRead.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                        </td>
                        <td width="50%">&nbsp;</td>
                      </table>
	                 <%}%>
                  </td>
                </tr>
              </table>

			  <!--DATOS DEL TESTADOR-->
			  <br>
			  
			  
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <!-- Modifica Pablo Quintana Junio 20 2008
                  	Se pueden tener más testadores en un testamento -->
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Testadores</td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
 
 
              <!--  Pablo Quintana-->
              <table width="100%" border="0" class="camposform">
              <tr>
 				<%
 				if(testamento!=null && testamento.getTestadores()!=null && testamento.getTestadores().size()>0){
 					Ciudadano ciudadano;
 					String ciudadanoMostrar=null;
 					String docIdentidad;
 					for(int i=0;i<testamento.getTestadores().size();i++){
 						ciudadanoMostrar="";
 						docIdentidad="";
 						ciudadano=(Ciudadano)testamento.getTestadores().get(i);
 						
                                                if(ciudadano!=null){
 							if(ciudadano.getApellido1()!=null)
 								ciudadanoMostrar=ciudadanoMostrar+ciudadano.getApellido1()+" ";
 							if(ciudadano.getApellido2()!=null)
 								ciudadanoMostrar=ciudadanoMostrar+ciudadano.getApellido2()+" ";
 							if(ciudadano.getNombre()!=null)
 								ciudadanoMostrar=ciudadanoMostrar+ciudadano.getNombre()+"";
	 						if(ciudadano.getTipoDoc()!=null && !ciudadano.getTipoDoc().equals("")
	 								&& !ciudadano.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD))
	 							docIdentidad=ciudadano.getDocumento();
 						}
 					%>
 					<tr>
	 					<td width="20\"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
 						<td class="titulotbcentral"><%=ciudadanoMostrar%></td>
 						<td class="titulotbcentral"><%=docIdentidad%></td>
                                                <%if( editable ){%>
                                                    <td width="40"><a href="javascript:funcionQuitarTestador('<%=i%>','<%=AWTestamentos.ELIMINAR_TESTADOR%>')"><img src="<%= request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" name="Folio" width="35" height="25" border="0"></a></td>
                                                <%}%>
 					</tr>
 				<%	}
 				}
 				%>
               </tr> 
               </table>
              <!-- Fin -->
 
 
  
<%
      TextHelper textHelper = new TextHelper();
      ListaElementoHelper docHelper = new ListaElementoHelper();
      java.util.List docs = (java.util.List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
      if(editable){
  %>
             	<table width="100%" class="camposform">
                 	<tr>
                 	
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211">
                     	<%
						 
                     	 try {

                      		if(docs == null){
                      			docs = new java.util.Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		               	        docHelper.setNombre(CCiudadano.TIPODOC);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CCiudadano.TIPODOC);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						  %>
                 	</td>
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212">
                 		<% try {
 		                        textHelper.setNombre(CCiudadano.IDCIUDADANO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.IDCIUDADANO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	</tr>
                 	<tr>
                 	
                 	<td>Primer Apellido</td>
                 	<td>
                 		<% try {
 		           	            textHelper.setNombre(CCiudadano.APELLIDO1);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO1);
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
 		           	            textHelper.setNombre(CCiudadano.APELLIDO2);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO2);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	</tr>
                 	<tr>
                 	
                 	<td>Nombre</td>
                 	<td>
                 		<% try {
 		           	            textHelper.setNombre(CCiudadano.NOMBRE);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.NOMBRE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                 	</td>
                 	<td>&nbsp;<!--Teléfono--></td>
                 	<td>&nbsp;
                 		<% /*try {
 		           	            textHelper.setNombre(CCiudadano.TELEFONO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.TELEFONO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}*/
						%>
                 	</td>
                 	</tr>
             	</table>


				<table  class="camposform">
					<tr>
						<td>
							Agregar testador
						</td>
					</tr>
					<tr>
	        	        <td>
	        	        	<center>
	        	          	<a href="javascript:cambiarAccion('<%=AWTestamentos.AGREGAR_TESTADOR%>')">
    	        	        	<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" name="Folio" width="32" height="25" border="0" id="Folio" alt="Agregar testador"/>
        	        	  	</a>
        	        	  </center>
        	        	 </td>
        	        </tr>
                  </table>
             	
<%}%>
			 
		 <!--DATOS DEL REGISTRO DE TESTAMENTOS-->
              <br>


              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Registro de Testamentos</td>
                  
                  
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr>
                 
                  <td width="18%">Tomo:</td>
                  <td align='left' class="<%=(!editable)?"campositem":""%>">
				<% 	
					try {
	                   	textHelper.setNombre(CTestamentos.TOMO);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("30");
		               	textHelper.setId(CTestamentos.TOMO);
                                textHelper.setEditable(editable);
                               
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>
                
                <tr>
                  
                  <td width="18%">Número Anotaciones:</td>
                  <td align='left' class="<%=(!editable)?"campositem":""%>">
				<% 	
					try {
	                   	textHelper.setNombre(CTestamentos.NUMERO_ANOTACIONES);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("30");
		               	textHelper.setId(CTestamentos.NUMERO_ANOTACIONES);
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>     
                
                <tr>
                  
                  <td width="18%">Número copias:</td>
                  <td align='left' class="<%=(!editable)?"campositem":""%>">
				<% 	
					try {
	                   	textHelper.setNombre(CTestamentos.NUMERO_COPIAS);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("30");
		               	textHelper.setId(CTestamentos.NUMERO_COPIAS);
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>  
                
                <tr>
               
                  <td width="18%">Revoca Escritura:</td>
                  <td align='left' class="<%=(!editable)?"campositem":""%>">
				<% 	
					try {
	                   	textHelper.setNombre(CTestamentos.REVOCA_ESCRITURA);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("60");
		               	textHelper.setId(CTestamentos.REVOCA_ESCRITURA);
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>   
                
                <tr>
                  
                  <td width="18%" >Observación:</td>
                  <td align='left' class="<%=(!editable)?"campositem":""%>">
                 	<% if(editable){
                            try {                 	
             			
                                    TextAreaHelper area = new TextAreaHelper();
							area.setCols("90");
							area.setRows("8");
             				area.setNombre(CTestamentos.OBSERVACION);
              			   	area.setCssClase("camposformtext");
              			   	area.setId(CTestamentos.OBSERVACION);
              			   	area.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
                           }else
                           {
                                try{
                                       textHelper.setNombre(CTestamentos.OBSERVACION);
                                       textHelper.setId(CTestamentos.OBSERVACION);
                                       textHelper.render(request, out);
                                }catch(HelperException re ){
				    out.println("ERROR " + re.getMessage());
                                }

          
                           }       
					%>
				</td>
                </tr>
                <%if(editable){%>
                <tr>
                	<td width="18%">Copias a imprimir</td>
                	<td align="left">
                		<% 	
							try {
				                   	textHelper.setNombre(CTestamentos.COPIAS_IMPRIMIR);
					               	textHelper.setCssClase("camposformtext");
		        				    textHelper.setSize("30");
				    	           	textHelper.setId(CTestamentos.COPIAS_IMPRIMIR);
									textHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
						%>
                	</td>
                </tr>
                <%}%>
              </table>              
    <span class="bgnsub" style="background:" >Salvedad: Testamento </span>

    <table width="100%" class="camposform">
         <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Salvedad</td>
          </tr>
              <tr>
                 <td width="20">&nbsp;</td>
                 <td class="<%=(!editable)?"campositem":""%>">

                
                 <% // text
                          try {
                               if(editable){
                                     TextAreaHelper textAreaHelper = new TextAreaHelper();                              
                                  textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");

 		                  textAreaHelper.setNombre(  AWModificarTestamento.SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID );
                  		  textAreaHelper.setId(      AWModificarTestamento.SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID );
 		                  textAreaHelper.setCols( "130" );
 		                  textAreaHelper.setRows( "2" );
                  	          textAreaHelper.setCssClase("camposformtext");
                                  textAreaHelper.setReadOnly(false );
				  textAreaHelper.render(request,out);
                               }else
                               {
                                       textHelper.setNombre(AWModificarTestamento.SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID );
                                       textHelper.setId(AWModificarTestamento.SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID );
                                       textHelper.render(request, out);
                               }
			  }
                          catch( HelperException re ){
				  out.println("ERROR " + re.getMessage());
                          }
                 %>

                </td>
           </tr>
     </table>
			  <!--BOTONES-->			
			  <br>
                           <hr class="linehorizontal" />

        <table width="100%" class="camposform">
	<tr>
	<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
        
          <td width="140"><a href="javascript:cambiarAccion('<%=AWTrasladoTurno.CONSULTAR_TURNO%>','<%=turno.getIdWorkflow()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0"></a></td>
        <td colspan='2'>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	</table>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	  
     </form>            
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
