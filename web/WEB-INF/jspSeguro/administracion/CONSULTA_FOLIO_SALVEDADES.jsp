<%
/**
*@Autor: Edgar Lora
*@Mantis: 11599
*@Requerimiento: 085_151
*/%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorAnotaciones"%>
<%@page import="java.util.Collections"%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorSalvedad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.SalvedadFolio"%>
<%@page import="gov.sir.core.negocio.modelo.SalvedadAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoSalvedadFolio"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%

		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
		
		if(folio==null){
			folio = new Folio();
		}
		
		List salvAnotacion = (List)session.getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES);

		int local_CorreccionCounter;
		local_CorreccionCounter = 0;

		
%>

<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" class="camposform">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td width="12"><img name="ind_turno" src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15" border="0" alt=""></td>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=folio.getIdMatricula()%></td>
          </tr>
        </table>
        <br> 
        
<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->


        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Salvedades del Folio</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          <%
			if(folio.getSalvedades()!=null&&folio.getSalvedades().size()>0){     
		  %>	
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="12%">Radicación</td>				
				<td width="12%">Turno Corrección</td>				
				<td width="15%">Usuario</td>
				<td width="9%">Fecha</td>									
				<td width="80%">Salvedad</td>
				<td width="5"><div align="center">#.</div></td>
				<td width="16"><div align="center">::</div></td>
				</tr>
			<%
			MostrarFechaHelper fechaHelper = new MostrarFechaHelper();				
			fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);

                        		
			
                        /**
                        *@Autor: Edgar Lora
                        *@Mantis: 11599
                        *@Requerimiento: 085_151
                        */
                        ArrayList salvedades = new ArrayList();
                        for(int i = 0; i < folio.getSalvedades().size(); i = i + 1){
                            salvedades.add(folio.getSalvedades().get(i));
                        }
                        Collections.sort(salvedades, new ComparadorSalvedad());
			Iterator is = salvedades.iterator();
			while(is.hasNext()){
				SalvedadFolio salvedad = (SalvedadFolio)is.next();
				String usr = null;
				if( salvedad.getUsuarioCreacionTMP()!=null&&salvedad.getUsuarioCreacionTMP().getUsername()!=null){
					usr = salvedad.getUsuarioCreacionTMP().getUsername();					
				}
                                if(usr == null && salvedad.getUsuarioCreacion()!=null&&salvedad.getUsuarioCreacion().getUsername()!=null){
					usr = salvedad.getUsuarioCreacion().getUsername();
				}
			%>	
				<tr>
				<td width="20">&nbsp;</td>
				<td width="15%" class="campositem"><%=(folio.getRadicacion()!=null)?folio.getRadicacion():"&nbsp;"%></td>				
				<td width="15%" class="campositem">
				<%
				String turnoCorreccion = "&nbsp;";
				if(salvedad.getTurnoSalvedadFolios()!=null && !salvedad.getTurnoSalvedadFolios().isEmpty()){
                    // Bug 5353
					TurnoSalvedadFolio tsf = (TurnoSalvedadFolio)salvedad.getTurnoSalvedadFolios().get(0);
					if(tsf!=null){
						turnoCorreccion = tsf.getAnio() + "-";
						turnoCorreccion = turnoCorreccion + tsf.getIdCirculo() + "-";
						turnoCorreccion = turnoCorreccion + tsf.getIdProceso() + "-";
						turnoCorreccion = turnoCorreccion + tsf.getIdTurno();
					}
				}
				else {

						if( null != salvedad.getNumRadicacion() ) {
                           turnoCorreccion +=  salvedad.getNumRadicacion();
						} // if

				} // if

				%><%=(turnoCorreccion!=null)?turnoCorreccion:"&nbsp;"%>				
				</td>												
				<td width="15%" class="campositem"><%=(usr!=null)?usr:"&nbsp;"%></td>				
					<td width="9%" class="campositem">
						<%
						fechaHelper.setDate(salvedad.getFechaCreacion());
						fechaHelper.render(request,out);
						%>					
					</td>					
				<td width="80%" class="campositem"><%=(salvedad.getDescripcion()!=null)?salvedad.getDescripcion():"&nbsp;"%></td>
				
				

				<%-- Bug 05353 --%>
				<%-- Bug 05354 (contar solo las correcciones) --%>

				<%
					
					boolean local_SalvedadFolio_IsTemporal;
					local_SalvedadFolio_IsTemporal = false;
					local_SalvedadFolio_IsTemporal = salvedad.isTemporal();
				%>
				<%
					if( local_SalvedadFolio_IsTemporal ) {
						local_CorreccionCounter ++;
					} // if
					
				%>
				<td width="5" class="campositem" ><%= ( local_SalvedadFolio_IsTemporal )?( ""+local_CorreccionCounter ):( "&nbsp;" ) %></td>

				<td width="16">&nbsp;
				<%-- condition --%>
				<%
					if( local_SalvedadFolio_IsTemporal ) {
				%>
				<%-- condition --%>
				<img src="<%=request.getContextPath()%>/jsp/images/ani_temporal.gif" alt="[tmp]" border="0" />
				<%-- condition --%>
				<%

					} // if
				%>
				<%-- condition --%>

				</td>
				

				</tr>
			<%
			}
			%>
			</table>
		<%
		}else{
		%>
			<table width="100%" class="camposform">
			<tr>
			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			<td width="40">&nbsp;</td>
			<td>El folio No tiene Salvedades</td>
			</tr>
			</table>
		<%	
		}
		%>
	
          </td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Salvedades de las Anotaciones del Folio</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          
          <% 
			if(salvAnotacion!=null&&salvAnotacion.size()>0){     
		  %>	
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="6%">Anotaci&oacute;n</td>
				<td width="12%">Radicación</td>				
				<td width="12%">Turno Corrección</td>				
				<td width="15%">Usuario</td>				
				<td width="9%">Fecha</td>	
				<td width="40%">Salvedad</td>
				<td width="5"><div align="center">#.</div></td>
				<td width="16"><div align="center">::</div></td>
				</tr>
				<%			
				MostrarFechaHelper fechaHelper = new MostrarFechaHelper();				
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                                
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                ArrayList anotaciones = new ArrayList();
                                for(int i = 0; i < salvAnotacion.size(); i = i + 1){
                                    anotaciones.add(salvAnotacion.get(i));
                                }
                                Collections.sort(anotaciones, new ComparadorAnotaciones());
				Iterator ia = anotaciones.iterator();
				while(ia.hasNext()){
				    Anotacion anotacion = (Anotacion)ia.next();
						
                                        /**
                                        *@Autor: Edgar Lora
                                        *@Mantis: 11599
                                        *@Requerimiento: 085_151
                                        */
                                        List salvedades = new ArrayList();
                                        for(int i = 0; i < anotacion.getSalvedades().size(); i = i + 1){
                                            salvedades.add(anotacion.getSalvedades().get(i));
                                        }
                                        Collections.sort(salvedades, new ComparadorSalvedad());
					Iterator is = salvedades.iterator();
					while(is.hasNext()){
						SalvedadAnotacion salvedad = (SalvedadAnotacion)is.next();				
					String usr = null;
					if( salvedad.getUsuarioCreacionTMP()!=null&&salvedad.getUsuarioCreacionTMP().getUsername()!=null){
						usr = salvedad.getUsuarioCreacionTMP().getUsername();
					}
                                        if(usr == null && salvedad.getUsuarioCreacion()!=null&&salvedad.getUsuarioCreacion().getUsername()!=null){
						usr = salvedad.getUsuarioCreacion().getUsername();
					}
				%>	
					<tr>
					<td width="20">&nbsp;</td>
					<td width="6%" class="campositem"><%=(anotacion.getOrden()!=null)?anotacion.getOrden():"&nbsp;"%></td>					
					<td width="12%" class="campositem"><%=(anotacion.getNumRadicacion()!=null)?anotacion.getNumRadicacion():"&nbsp;"%></td>										
					<td width="12%" class="campositem">
						<%
						String turnoCorreccion = "&nbsp;";
						if(salvedad.getTurnoSalvedadAnotacions()!=null && !salvedad.getTurnoSalvedadAnotacions().isEmpty()){
							TurnoSalvedadAnotacion tsa = (TurnoSalvedadAnotacion)salvedad.getTurnoSalvedadAnotacions().get(0);
							if(tsa!=null){
								turnoCorreccion = tsa.getAnio() + "-";
								turnoCorreccion = turnoCorreccion + tsa.getIdCirculo() + "-";
								turnoCorreccion = turnoCorreccion + tsa.getIdProceso() + "-";
								turnoCorreccion = turnoCorreccion + tsa.getIdTurno();
							}

						}
						else {

								if( null != salvedad.getNumRadicacion() ) {
		                           turnoCorreccion +=  salvedad.getNumRadicacion();
								} // if
		
						} // if
						%><%=(turnoCorreccion!=null)?turnoCorreccion:"&nbsp;"%>
					</td>															
					<td width="15%" class="campositem"><%=(usr!=null)?usr:"&nbsp;"%></td>
		
					<td width="9%" class="campositem">
						<%
						fechaHelper.setDate(salvedad.getFechaCreacion());
						fechaHelper.render(request,out);
						%>					
					</td>					
					<td width="40%" class="campositem"><%=(salvedad.getDescripcion()!=null)?salvedad.getDescripcion():"&nbsp;"%></td>					



				<%-- Bug 05353 --%>
				<%
					
					boolean local_SalvedadAnotacion_IsTemporal;
					local_SalvedadAnotacion_IsTemporal = false;
					local_SalvedadAnotacion_IsTemporal = salvedad.isTemporal();
				%>


				<%
					if( local_SalvedadAnotacion_IsTemporal ) {
						local_CorreccionCounter ++;
					} // if
					
				%>
				<td width="5" class="campositem" ><%= ( local_SalvedadAnotacion_IsTemporal )?( ""+local_CorreccionCounter ):( "&nbsp;" ) %></td>



				<td width="16">&nbsp;
				<%-- condition --%>
				<%
					if( local_SalvedadAnotacion_IsTemporal ) {
				%>
				<%-- condition --%>
				<img src="<%=request.getContextPath()%>/jsp/images/ani_temporal.gif" alt="[tmp]" border="0" />
				<%-- condition --%>
				<%

					} // if
				%>
				<%-- condition --%>

				</td>

					




					</tr>
				<%
					} 
				}
				%>
				</table>
			<%
			}else{
			%>
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="40">&nbsp;</td>
				<td>El folio No tiene Salvedades</td>
				</tr>
				</table>
			<%	
			}
			%>          

      
          </td>
        </tr>        

      </table>
