<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%> 
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWTestamentos"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<jsp:directive.page import="java.util.List"/>
 
<%	//INICIALIZACIÓN DE VARIABLES
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
    MostrarFechaHelper fecha = new MostrarFechaHelper();
       /*
        * @author : CTORRES
        * @change : definen variables tipoId,idCiudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
    String tipoId = request.getParameter(CCiudadano.TIPODOC);
    String idCiudadano = (String)request.getSession().getAttribute("ID_CIUDADANO"); 
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
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
	
	//Sí el turno tiene testamento es porque va a ser eitado y se actualizan los datos en el formulario
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
    document.CORRECCION_ACTUACION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION_ACTUACION.submit();
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
		document.CORRECCION_ACTUACION.POSICION.value=pos;
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
    
    

	  <form name="CORRECCION_ACTUACION"  id="CORRECCION_ACTUACION" method="post" action="testamentos.do">
	  <input type="hidden" name="<%=WebKeys.ACCION%>" value="">	
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">REGISTRO DE TESTAMENTOS</td>
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
 					TestamentoCiudadano testamentoCiudadano;
 					String ciudadanoMostrar=null;
 					String docIdentidad;
 					for(int i=0;i<testamento.getTestadores().size();i++){
 						ciudadanoMostrar="";
 						docIdentidad="";
 						testamentoCiudadano=(TestamentoCiudadano)testamento.getTestadores().get(i);
 						ciudadano=testamentoCiudadano.getCiudadano();
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
	 					<td width="20\"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"width="20" height="15"></td>
 						<td class="titulotbcentral"><%=ciudadanoMostrar%></td>
 						<td class="titulotbcentral"><%=docIdentidad%></td>
 						<td width="40"><a href="javascript:funcionQuitarTestador('<%=i%>','<%=AWTestamentos.ELIMINAR_TESTADOR%>')"><img src="<%= request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" name="Folio" width="35" height="25" border="0"></a></td>
 					</tr>
 				<%	}
 				}
 				%>
               </tr> 
               </table>
              <!-- Fin -->
 
 
 
             	<table width="100%" class="camposform">
                 	<tr>
                 	
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211">
                            <%--
                                                  /*
        * @author : CTORRES
        * @change : Nuevo campo ID_CIUDADANO
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                            --%>
                            <input id="ID_CIUDADANO" name="ID_CIUDADANO" type ="hidden" value="<%=(idCiudadano !=null)?idCiudadano:""%>" />
                                
                                
                     	<%
						 TextHelper textHelper = new TextHelper();
						 ListaElementoHelper docHelper = new ListaElementoHelper();
                     	 try {

                      		java.util.List docs = (java.util.List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		if(docs == null){
                      			docs = new java.util.Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		               	        docHelper.setNombre(CCiudadano.TIPODOC);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CCiudadano.TIPODOC);
    /*
                    *  @author Carlos Torres
                    *  @chage  metodo para cerrar conexion
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/ 
                                            
                                            docHelper.setFuncion("onchange=\"cambiarAccion('REFRESCAR_EDICION')\"");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						  %>
                 	</td>
                        
                        <%
                         /*
                    *  @author Carlos Torres
                    *  @chage  comprobar tipo de identificacion
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/ 
                        if(tipoId == null || (tipoId !=null && !tipoId.equals("SE"))){%>
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212">
                 		<% try {
 		                        textHelper.setNombre(CCiudadano.IDCIUDADANO);
                  			textHelper.setCssClase("camposformtext");
                  			textHelper.setId(CCiudadano.IDCIUDADANO);
                                                              /*
        * @author : CTORRES
        * @change : se asigna la propiedad funcion
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                                        textHelper.setFuncion("onBlur =\"cambiarAccion('VALIDAR_CIUDADANO')\"");
					textHelper.render(request,out);
					}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
					}
       /* @author : CTORRES
        * @change : se asigna la propiedad funcion
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                                textHelper.setFuncion("");
				%>
                 	</td>
                        <%}%>
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
                  <td align='left'>
				<% 	
					try {
	                   	textHelper.setNombre(CTestamentos.TOMO);
		               	textHelper.setCssClase("camposformtext");
        			    textHelper.setSize("30");
		               	textHelper.setId(CTestamentos.TOMO);
						textHelper.render(request,out);
					}	catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				%>
				</td>
                </tr>
                
                <tr>
                  
                  <td width="18%">Número Anotaciones:</td>
                  <td align='left'>
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
                  <td align='left'>
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
                  <td align='left'>
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
                  <td align='left'>
                 	<% try {                 	
             				TextAreaHelper area = new TextAreaHelper();
							area.setCols("90");
							area.setRows("8");
             				area.setNombre(CTestamentos.OBSERVACION);
              			   	area.setCssClase("camposformtext");
              			   	area.setId(CTestamentos.OBSERVACION);
              			   	area.setReadOnly(false);
							area.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
				</td>
                </tr>    
                
                <!-- Modifica Pablo Quintana Junio 12 2008
                     Opcion de número de copias a imprimir -->              
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
              </table>              
              


      <!--INICIO DE REVISIÓN DE CONFRONTACIÓN Modifica Pablo Quintana Junio 17 2008-->
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Enviar Correccion Encabezado</td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
				 <tr>
				  <td>
                  <a href="javascript:enviarConfrontacion('<%=AWTestamentos.CORRECCION_ENCABEZADO%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_correccion_encabezado.gif" name="Folio" width="190" height="21" border="0" id="Folio"/>
                  </a>
                  </td>              
                 </tr>
              </table>

      <!--FIN DE REVISIÓN DE CONFRONTACIÓN-->






			  <!--BOTONES-->			
			  <br>
			  <hr class="linehorizontal">
			  
              
              <table width="100%" class="camposform">
                <tr>
                  
                  <td width="50"><a href="javascript:cambiarAccion('<%=AWTestamentos.REGISTRAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_registrar.gif" border="0"></a></td>
                  <td width="50"><a href="devolucion.testamentos.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_devolver.gif" border="0"></a></td>                  
                  <td width="50"><a href="javascript:cambiarAccion('<%=AWTestamentos.DEVOLVER_A_CONFRONTACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_confrontacion.gif" border="0"></a></td>
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
	  <!--FIN ACTUACIONES ADMINISTRATIVAS-->      

	  
	  
     </form>            

	  

	  
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

  
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
