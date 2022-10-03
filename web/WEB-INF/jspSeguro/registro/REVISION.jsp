<%@page import="org.auriga.core.web.*,
gov.sir.core.web.helpers.registro.MatriculaCalifHelper,
gov.sir.core.web.helpers.comun.MostrarFechaHelper,
gov.sir.core.negocio.modelo.SolicitudCheckedItem,
gov.sir.core.negocio.modelo.Turno,
gov.sir.core.web.helpers.comun.TextHelper,
gov.sir.core.web.helpers.comun.ListaElementoHelper,
gov.sir.core.web.helpers.comun.NotasInformativasHelper,
gov.sir.core.negocio.modelo.SolicitudRegistro,
gov.sir.core.negocio.modelo.Vereda,
gov.sir.core.web.WebKeys,
java.util.*;"%>
<% 
	
	MatriculaCalifHelper matHelper = new MatriculaCalifHelper();

	MostrarFechaHelper fechaHelper = new MostrarFechaHelper(); 
	TextHelper textHelper = new TextHelper();
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	
    SolicitudRegistro solicitud=null;
   
    if(turno!=null){
   		solicitud =(SolicitudRegistro)turno.getSolicitud();
    }
 
    String ocultarAS=(String)session.getAttribute("VER_DATOS");
   	if (ocultarAS==null){
   	  		ocultarAS="TRUE";
   	}
   	
    else{
   	
   		session.setAttribute("VER_DATOS",ocultarAS);
    }
   
    String ocultarDB=(String)session.getAttribute("VER_DATOS_BASICOS");
   	if (ocultarDB==null){
   		ocultarDB="FALSE";
   	}
    else{
   		
   		session.setAttribute("VER_DATOS_BASICOS",ocultarDB);
    }
	String turnoAnterior="";   
	if(solicitud!=null){
	
		if (solicitud.getTurnoAnterior()!=null){
			turnoAnterior=solicitud.getTurnoAnterior().getIdWorkflow();
			
		}
	}
	
	
	
	turnoAnterior = (turnoAnterior.equals(""))?"&nbsp;":turnoAnterior;
	if(solicitud!=null){
	    if (solicitud.getSolicitudFolios() != null)
	    {

	     	matHelper.setFolios(solicitud.getSolicitudFolios());
	     	
	    }
	}else{
		
		matHelper.setFolios(new Vector());
	
		
	}

	matHelper.setRevisar();

	String comentario = "&nbsp;";
	if(solicitud!=null){
		if (solicitud.getComentario()!=null){
			comentario =solicitud.getComentario();

		}
	}
	Calendar calendar = Calendar.getInstance();
	List avanceWF = new Vector();
	NotasInformativasHelper notHelper = new NotasInformativasHelper();
	ListaElementoHelper helper = new ListaElementoHelper();
	helper.setTipos(avanceWF);

	
	
	// se ponen en el session los atributos q se necesutan de la solicitud
	if(solicitud!=null){
		if(solicitud.getUsuario()!=null){
			if(solicitud.getUsuario().getNombre()!=null){
				session.setAttribute("RADICADO_POR",solicitud.getUsuario().getNombre());
			}
		}
		
		
		if(solicitud.getDocumento()!=null){
			if(solicitud.getDocumento().getTipoDocumento()!=null){
				if(solicitud.getDocumento().getTipoDocumento().getNombre()!=null){
					session.setAttribute("TIPO_ENCABEZADO",solicitud.getDocumento().getTipoDocumento().getNombre());
				}
			}
			
			
			if(solicitud.getDocumento().getNumero()!=null){
				session.setAttribute("ID_ENCABEZADO",solicitud.getDocumento().getNumero());
			}
			
					if(solicitud.getDocumento().getFecha()!=null){
				session.setAttribute("CALENDAR",solicitud.getDocumento().getFecha().toString());
			}
		}
	}
	
	//Oficina
	String nomOficina= "";    
    boolean isInternacional=false;
    if(solicitud !=null){
    	if(solicitud.getDocumento()!=null){
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
	
	

	//DEPARTAMENTO, MUNICIPIO, VEREDA
	String departamento="&nbsp;";
	String municipio="&nbsp;";
	String vereda="&nbsp;";
	
	//TIPO OFICINA
	String tipoOficina="&nbsp;";
	
	//NUM OFICINA
	String numOficina="&nbsp;";
	
	if(!isInternacional){
		
		
		if(solicitud !=null){
    		if(solicitud.getDocumento()!=null){
    			if(solicitud.getDocumento().getOficinaOrigen()!=null){
					if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
						//DEPARTAMENTO, MUNICIPIO, VEREDA
						
						Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
						if(auxVereda.getMunicipio()!=null){
							if(auxVereda.getMunicipio().getDepartamento()!=null){
								if(auxVereda.getMunicipio().getDepartamento().getNombre()!=null){
									departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
								}
							}
							
							
							
							if(auxVereda.getMunicipio().getNombre()!=null){
								municipio=auxVereda.getMunicipio().getNombre();
							}
						}
						vereda=auxVereda.getNombre();
					}
		
					//TIPO OFICINA
					
					if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null) {
						tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
				  	}
					
					//NUM OFICINA
					if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null) {
						numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
				  }
				  
				}
			}
		}
	}

   

	if(!isInternacional){
		
		session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO",departamento);
	  	session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC",municipio);
		session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM",nomOficina);
				
		try {
			session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_COD",solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
		} catch (Exception e) {
			String nombreMunicipio = "&nbsp;";
			String nombreOficina = "&nbsp;";
			if (solicitud.getDocumento().getComentario() != null) {
				
				if(solicitud.getDocumento().getComentario().indexOf("-")!=-1){
					StringTokenizer token = new StringTokenizer(solicitud.getDocumento().getComentario(), "-");
					int numToken = token.countTokens();
					if (numToken <= 1){
						nombreOficina = solicitud.getDocumento().getComentario();
						session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC",nombreMunicipio);
						session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM",nombreOficina);
					} else {
						nombreMunicipio = token.nextToken();
						nombreOficina = token.nextToken();	
						session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC",nombreMunicipio);
						session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM",nombreOficina);
					}
				} else{
					nombreOficina = solicitud.getDocumento().getComentario();
					session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC",nombreMunicipio);
						session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM",nombreOficina);
				}
				
			}
		}
		
	}else{
		session.setAttribute("ANOTACION_OFICINA_DOCUMENTO_NOM",nomOficina);
	}
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));			
	
	
   %>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.CALIFICACION.ACCION.value = text;
       document.CALIFICACION.submit();
       }

function devolver() {
       document.CALIFICACION.ACCION.value = "DEVOLVER";
       document.CALIFICACION.submit();
       }
       
function revisar(text) {
	   document.CALIFICACION.ITEM.value = text;
	   cambiarAccion('REVISAR_FOLIO');
       }
       
function campoactual(id){
	if (document.getElementById("ultimo_campo_editado")!=null){
		document.getElementById("ultimo_campo_editado").value=id;
	}}
	
	function ocultarDatos(text) {
	   document.CALIFICACION.VER_DATOS.value = text;
	   cambiarAccion('VER_DATOS');
  	}
  	
  	function ocultarDatosBasicos(text){
  	   document.CALIFICACION.VER_DATOS_BASICOS.value = text;
	   cambiarAccion('VER_DATOS_BASICOS');
  	}
  	
	function send(){
      document.forma.submit()
    }

  	
</script>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Revisi&oacute;n calificaci&oacute;n </td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Registro</td>
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
            <form action="revision.do" method="post" name="CALIFICACION" id="CALIFICACION">
            <input type="hidden" name="ACCION" value="CALIFICAR">
            <input type="hidden" name="VER_DATOS" value="">
            <input type="hidden" name="VER_DATOS_BASICOS" value="">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos B&aacute;sicos </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>        
              </table>
               <table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTARB">
						<%if(ocultarDB.equals("FALSE")){%>
			        <tr>
			            <td><hr class="linehorizontal"></td>
			        </tr>
			        <tr>
			                <td></td>
			                <td width="16"><a href="javascript:ocultarDatosBasicos('TRUE');campoactual('OCULTARB');"><img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></a></td>
			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}else{%>
			        <tr>
			                <td align="right" class="campostip04">Haga click para ver datos del documento</td>
			                <td width="16"><a href="javascript:ocultarDatosBasicos('FALSE');campoactual('OCULTARB');"><img img id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></a></td>
   			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}%>
			    </table>
			    <%if(ocultarDB.equals("FALSE")){%>
			    	<table width="100%" class="camposform">
	                <tr>
	                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
	                  <td width="3%" align="right">
	                      Tipo
	                  </td>
	                  <td class="campositem" width="16%">
	                        <% try {
	 		                        textHelper.setNombre("TIPO_ENCABEZADO");
	                  			    textHelper.setCssClase("campositem");
	                  			    textHelper.setId("TIPO_ENCABEZADO");
	                  			    textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
								
							%>
							
	                        
	                   </td>
	                   <td width="10%">
	                      <DIV align="right">N&uacute;mero</DIV>
	                   </td>
	                   <td class="campositem" width="6%">
	                        
	                        <% try {
	 		                        textHelper.setNombre("ID_ENCABEZADO");
	                  			    textHelper.setCssClase("campositem");
	                  			    textHelper.setId("ID_ENCABEZADO");
	                            	textHelper.setSize("10");
	                  			    textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
								
							%>
	                        
	                   </td>
	                   <td width="15%">
	                      <DIV align="right">Fecha</DIV>
	                   </td>
	                   <td class="campositem" width="9%">
	                        <% try {
	                        //fechaHelper.setSize("20");
									fechaHelper.setDate(solicitud.getDocumento().getFecha());
									fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
	                  			    fechaHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
									
								}
							%>
	                        
	                   </td>
	                </tr>
	               </table>
			   		    
			    
			    	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		                <tr>
		                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos oficina de procedencia</td>
		                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
		                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		                </tr>
		              </table>    
				     <table width="100%" class="camposform">
                      
	                  <%if(isInternacional){%>
 					 <tr>
 					  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
 					  <td width="15%">
		                   <DIV align="right">Oficina internacional</DIV>
		              </td>
		              <td class="campositem" width="35%">
		                  
		                  <% try {
		 		                        textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM");
		                  			    textHelper.setCssClase("campositem");
		                  			    textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_NOM");
		                  			    textHelper.setEditable(false);
										textHelper.render(request,out);
									}
										catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
									
								%>
		                  
	                  </td>
	                  <td width="50%">&nbsp;</td>
		            </tr>

					  <%}else{%>
					<tr>
	                  <td width="12%">
		                   <DIV align="right">Codigo</DIV>
		              </td>
		              <td class="campositem" width="32%">
		                  
		                  <% try {
		 		                        textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_COD");
		                  			    textHelper.setCssClase("campositem");
		                  			    textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_COD");
		                  			    textHelper.setEditable(false);
										textHelper.render(request,out);
									}
										catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
									
								%>
		                  
	                  </td>
	                  <td width="15%">
	                    <DIV align="right">Nombre</DIV>
	                  </td>
	                  <td class="campositem" width="36%">
		                  
		                  <% try {
		 		                        textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM");
		                  			    textHelper.setCssClase("campositem");
		                  			    textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_NOM");
		                  			    textHelper.setEditable(false);
										textHelper.render(request,out);
										
									}
										catch(HelperException re){
										out.println("ERROR " + re.getMessage());
										
									}
								%>
		                  
	                  </td>
		            </tr>
                      
                      
	                  <tr>
	                    <td width="12%">
	                      <DIV align="right">Departamento</DIV>
	                    </td>
	                    <td class="campositem" width="32%">
	                      <%  try {
	 		                        textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO");
	                  			    textHelper.setCssClase("campositem");
	                  			    textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO");
	                  			    textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
								
							%>
	                    </td>
	                    <td width="15%">
	                      <DIV align="right">Municipio</DIV>
	                    </td>
	                    <td class="campositem" width="36%">
	                      <%  try {
	 		                        textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC");
	                  			    textHelper.setCssClase("campositem");
	                  			    textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC");
	                  			    textHelper.setEditable(false);
	                            textHelper.render(request,out);
	                            
	                          }
	                            catch(HelperException re){
	                            out.println("ERROR " + re.getMessage());
	                           
	                          }
							%>
	                    
	                    
	                    </td>
	                 </tr>
                         
			    <%	}
			    }%>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Anterior </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_turno_02.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>N&uacute;mero del Turno Anterior</td>
                  <td class="campositem"><%=turnoAnterior%></td>
                </tr>
              </table>
              
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Asociar una Matr&iacute;cula </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
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
              <br>
              
			      <table border="0" width="100%" cellpadding="0" cellspacing="2" id="OCULTAR">
						<%if(ocultarAS.equals("FALSE")){%>
			        
			        <tr>
			                <td></td>
			                <td width="16"><a href="javascript:ocultarDatos('TRUE');campoactual('OCULTAR');"><img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></a></td>
			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}else{%>
			        <tr>
			                <td align="right" class="campostip04">Haga click para ver m&aacute;s datos</td>
			                <td width="16"><a href="javascript:ocultarDatos('FALSE');campoactual('OCULTAR');"><img img id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></a></td>
   			                <img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
			        </tr>
						<%}%>
			    </table>
			    
			    <%if(ocultarAS.equals("FALSE")){%>
           	  
           	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos adicionales folio</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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
                  <td class="campositem"><%=comentario%>
                  
                  </td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de Documentos Entregados </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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
              <hr class="linehorizontal">
              <br>
             
               <%} else {%>
			<%}%>
              <hr class="linehorizontal">
	          <table width="100%" class="camposform">
                <tr>
                  <td width="150" align="center">
	              <a href="javascript:cambiarAccion('APROBAR_REVISION')">
	                 <img src="<%=request.getContextPath()%>/jsp/images/btn_mesa_control.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
	              </a></td>
                  <td width="150">
                  <a href="javascript:devolver()">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_calificacion.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
                  </a>
                  <input type="hidden" name="DEVOLUCION" value=""/>
                  </td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
           </form>
            	<%
					try{
					    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
					    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
					    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
					    notHelper.setNombreFormulario("CALIFICACION");		
						notHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
						
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
