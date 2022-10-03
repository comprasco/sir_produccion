
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.InfoMatriculaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCheckedItem"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudAsociada"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<% 
	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	NumberFormat formateador = new DecimalFormat("######,###");
	
	String ocultar = request.getParameter(WebKeys.OCULTAR);	
	if(ocultar == null){
		ocultar = (String)session.getAttribute(WebKeys.OCULTAR);
		if(ocultar==null){
			ocultar = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR,ocultar);
	}
	InfoMatriculaHelper matHelper = new InfoMatriculaHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	TextHelper textHelper = new TextHelper();
	SolicitudRegistro solicitud =(SolicitudRegistro)session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
    
	String turnoAnterior="";   
	if (solicitud.getTurnoAnterior()!=null){
		turnoAnterior=solicitud.getTurnoAnterior().getIdWorkflow();
	}
	matHelper.setSolicitudFolios(solicitud.getSolicitudFolios());
	String comentario = "";
	if (solicitud.getComentario()!=null){
		comentario =solicitud.getComentario();
	}
	Calendar calendar = Calendar.getInstance();
   %>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
        <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Registro de Documentos</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                        </tr>
                    </table>
                </td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table>
        </td>
        <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
		
		
        <tr>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td align="right" class="tdtablacentral">
        <table border="0" cellpadding="0" cellspacing="2">
            <tr>
		<%if(ocultar.equals("FALSE")){%>
            <form action="turno.registro.registrar.pago.view" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
		<%}else{%>
            <form action="turno.registro.registrar.pago.view" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
		<%}%>
            </tr>
        </table>
        </td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
		
	
        <tr> 
        <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
        
        <%
      	if(!solicitud.getSolicitudesHijas().isEmpty()){
      		List solicitudesHijas = solicitud.getSolicitudesHijas();
		%>
				
		  <br>
	      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
	        <tr>
	          <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
	          <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Liquidaciones
	            de Certificados Asociados</td>
	          <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
	          <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	        </tr>
	      </table>
	      <br>
	      <table width="100%" class="camposform">
	      <%
	      for(Iterator iter = solicitudesHijas.iterator(); iter.hasNext();){
	      	SolicitudAsociada solAsoc = (SolicitudAsociada)iter.next();
	      	SolicitudCertificado solCert = (SolicitudCertificado)solAsoc.getSolicitudHija();
	      	List liquidaciones = solCert.getLiquidaciones();
	      	String valorLiquidacion = "";
	      	String idSolicitud = "";
	      	if(!liquidaciones.isEmpty()){
	      		LiquidacionTurnoCertificado liqTurCert = (LiquidacionTurnoCertificado)liquidaciones.get(0);
	      		valorLiquidacion = formateador.format(liqTurCert.getValor());
	      		idSolicitud = liqTurCert.getIdSolicitud();
	      	}
	      %>
	        <tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
				<td width="293">Solicitud</td>
				<td width="445" class="campositem"><%= idSolicitud %></td>
				<td width="293">Valor</td>
				<td width="11">$</td>
				<td width="445" class="campositem"><%= valorLiquidacion %> </td>
			</tr>
		  <%
		  }
	      %>		
	      </table>
	      <br>
		<% 
		}
		%>
			
				
        <!--aca-->
	<%if(ocultar.equals("FALSE")){%>
        <br>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos B&aacute;sicos </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
			
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
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;culas Asociadas </td>
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
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Subtipo de Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Subtipo </td>
                  <td class="campositem">
                  <%=solicitud.getSubtipoSolicitud().getNombre()%>
                  </td>
                </tr>
              </table>
              <br>
              <%
		       		try{
		      			MASHelper.setMostrarDocumento(true);
		      			MASHelper.render(request,out);
		      		}catch(HelperException re)
				    {
						re.printStackTrace();
						out.println("ERROR " + re.getMessage());
					}
		       %>
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
                  					<td class="campositem"><%=ch.getIdCheckItem()%></td>
                				 </tr><%
                			}
 		                        
						%>
						
                
              </table>
              <hr class="linehorizontal">
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
                         
                        <%=solicitud.getDocumento().getTipoDocumento().getNombre()%>
                        
                        </td>
                        <td>N&uacute;mero</td>
                        <td class="campositem">
                        <%=solicitud.getDocumento().getNumero()%>
                         </td>
                        <td>Fecha</td>
                        <td class="campositem">
						<% try {
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
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                    <%
						//DEPARTAMENTO, MUNICIPIO, VEREDA
						String departamento="";
						String municipio="";
						String vereda="&nbsp;";
						if(solicitud.getDocumento()!=null&&solicitud.getDocumento().getOficinaOrigen()!=null&&solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
							gov.sir.core.negocio.modelo.Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
							if(auxVereda!=null){
								gov.sir.core.negocio.modelo.Municipio municipioM = auxVereda.getMunicipio();
								if(municipioM!=null){
									if(municipioM.getNombre()!=null){																			
										municipio = municipioM.getNombre();
									} 
									gov.sir.core.negocio.modelo.Departamento depto = municipioM.getDepartamento();
									if(depto!=null){
										if(depto.getNombre()!=null){																				
											departamento = depto.getNombre();
										}
									}
								}
							}

							if(municipio!=null&&departamento!=null&&!municipio.equals("")&&!departamento.equals("")){
							

					%>
						
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
					
					<%	
						}else{
						
					%>
                  	<tr>
                        <td>Vereda</td>
                        <td class="campositem">
                        <%=solicitud.getDocumento().getOficinaOrigen().getVereda().getNombre()!=null?solicitud.getDocumento().getOficinaOrigen().getVereda().getNombre():solicitud.getDocumento().getOficinaOrigen().getVereda().getIdVereda()%>
                        </td>
                      </tr>					
					<%	
						}
						}else{
                    %>        
                      <tr>
                        <td>Municipio</td>
                        <td class="campositem">
                        <%=(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null&&solicitud.getDocumento().getOficinaOrigen().getVereda().getNombre()!=null)?solicitud.getDocumento().getOficinaOrigen().getVereda().getNombre():solicitud.getDocumento().getOficinaOrigen().getVereda().getIdVereda()%>
                        </td>
                      </tr>                    
					<%	
						}
                    %>                                      

                    </table>
                      <table width="100%" class="camposform">
                      <%if (solicitud.getDocumento().getOficinaOrigen().getNombre()!=null && solicitud.getDocumento().getOficinaOrigen().getNumero()!=null){%>
                         <tr>
                          <td>Tipo</td>
                          <td class="campositem">
                          <%=solicitud.getDocumento().getOficinaOrigen().getNombre()%>
                          </td>
                          <td>N&uacute;mero</td>
                          <td class="campositem">
                          <%=solicitud.getDocumento().getOficinaOrigen().getNumero()%>
                          </td>
                        </tr>
                      <%}
                      else{%>
                      	<tr>
                          <td>ID Oficina</td>
                          <td class="campositem">
                          <%=solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen()%>
                          </td>
                        </tr>
                      <%}%>

                    </table></td>
                </tr>
              </table>
              <br>

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem">
                  <%=solicitud.getCiudadano().getTipoDoc()%>
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem">
                  <%=solicitud.getCiudadano().getDocumento()%>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Primer Apellido</td>
                  <td class="campositem">
                  <%=solicitud.getCiudadano().getApellido1()%>
                  </td>
                  <td>Segundo Apellido</td>
                  <td class="campositem">
                  <%=solicitud.getCiudadano().getApellido2()%>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombre</td>
                  <td class="campositem">
                  <%=solicitud.getCiudadano().getNombre()%>
                  </td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
        <br>
		<%}%>

        </td>
        <td width="11" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
        <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
        <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="15" height="6"></td>
        <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
    </table>