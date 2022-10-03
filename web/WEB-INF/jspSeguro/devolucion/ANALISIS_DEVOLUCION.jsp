<%@page import="gov.sir.core.web.acciones.comun.AWTurno"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%

String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	vistaAnterior = vistaAnterior !=null 
		? "javascript:window.location.href='"+vistaAnterior+"';" 
		: "javascript:history.back();";
		
		
Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
session.setAttribute(WebKeys.TURNO_DEVOLUCION, turno);
SolicitudDevolucion solicitud = (SolicitudDevolucion)turno.getSolicitud();


Turno turnoAntDev=(Turno)session.getAttribute(WebKeys.TURNO_ANT_DEV);
if(turnoAntDev!=null)
	solicitud.setTurnoAnterior(turnoAntDev);

boolean precaucionTurnoRegistroInscrito = false;
Solicitud local_SolicitudAnterior=null;

List pagosTurnoAnterior = null;	
List turnoPagoAsociado = null;
if(solicitud.getTurnoAnterior()!=null){
	Turno tAnt = solicitud.getTurnoAnterior();
	local_SolicitudAnterior = tAnt.getSolicitud();
	Solicitud solAnt;
	List historials = tAnt.getHistorials();
	if(tAnt.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && historials!=null && historials.size()>0){
		for(int i=historials.size()-1;i>=0;i--){
			TurnoHistoria historia = (TurnoHistoria)historials.get(i);
			if(historia!=null && historia.getRespuesta()!=null && historia.getRespuesta().equals(CRespuesta.OK)){
				precaucionTurnoRegistroInscrito = true;
				break;
			}else if(historia!=null && historia.getRespuesta()!=null && historia.getRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)){
				precaucionTurnoRegistroInscrito = false;
				break;
			}else if(historia!=null && historia.getRespuesta()!=null && historia.getRespuesta().equals(CRespuesta.DEVOLUCION)){
				precaucionTurnoRegistroInscrito = false;
				break;
			}
		}
	}
	pagosTurnoAnterior = new ArrayList();
	turnoPagoAsociado = new ArrayList();
	while(tAnt!=null){
		if(tAnt.getSolicitud()!=null && tAnt.getSolicitud().getLiquidaciones()!=null && tAnt.getSolicitud().getLiquidaciones().size()>0){
			solAnt = tAnt.getSolicitud();
			List liquidaciones = tAnt.getSolicitud().getLiquidaciones();
			for(int i=0; i<liquidaciones.size(); i++){
				Liquidacion liq = (Liquidacion)liquidaciones.get(i);
				List aPagos = null;
				if(liq!=null && liq.getPago()!=null && liq.getPago().getAplicacionPagos()!=null){
					aPagos = liq.getPago().getAplicacionPagos();
				}
				if (aPagos==null) aPagos = new ArrayList();
				for(int j=0;j<aPagos.size();j++){
					DocumentoPago pago = ((AplicacionPago)aPagos.get(j)).getDocumentoPago();
					pagosTurnoAnterior.add(pago);
					turnoPagoAsociado.add(tAnt.getIdWorkflow());
				}
			}
			tAnt = solAnt.getTurnoAnterior();
		}
	}
	
	
}


List consignaciones = solicitud.getConsignaciones();
List solicitantes = solicitud.getSolicitantes();
TextAreaHelper area = new TextAreaHelper();
TextHelper turnoAnt = new TextHelper();
session.setAttribute(CTurno.DESCRIPCION,solicitud.getDescripcion());
area.setCols("90");
area.setRows("10");
if(solicitud.getTurnoAnterior()!=null && solicitud.getTurnoAnterior().getIdWorkflow()!=null)
	session.setAttribute(CTurno.TURNO_ANTERIOR,((Turno)solicitud.getTurnoAnterior()).getIdWorkflow());
NotasInformativasHelper helper = new NotasInformativasHelper();
NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");
Date today = new Date();
String fechaAct= DateFormatUtil.format(today);
%>

<SCRIPT>
function verDetallesTurno(solicitud) {
    document.CONSULTA.<%=CTurno.ID_TURNO%>.value=solicitud;
    document.CONSULTA.submit();
}

function cambiarAccion(text,solicitud) {
	 document.ENTREGA.<%=WebKeys.ACCION%>.value=text;
	 document.ENTREGA.submit();
}

function validarTurno(text,solicitud) {
	 document.ENTREGA.<%=WebKeys.ACCION%>.value=text;
	 document.ENTREGA.<%=CTurno.TURNO_ANTERIOR%>.value=document.CONSULTA.<%=CTurno.TURNO_ANTERIOR%>.value;
	 document.ENTREGA.submit();
}

function quitar(pos,accion){
	document.ENTREGA.POSICION.value = pos-1;
	document.ENTREGA.ACCION.value = accion;
	document.ENTREGA.submit();
}

function addConsignacionCheque(text ){
	document.ENTREGA_ANALISIS.<%=WebKeys.ACCION%>.value=text;
	document.ENTREGA_ANALISIS.submit();
}

function analisisAceptar(text,solicitud){ 
	 document.ENTREGA.<%=WebKeys.ACCION%>.value=text;
	 if(document.CONSULTA.<%=CTurno.TURNO_ANTERIOR%>.value!=null)
		 document.ENTREGA.<%=CTurno.TURNO_ANTERIOR%>.value=document.CONSULTA.<%=CTurno.TURNO_ANTERIOR%>.value;
	 document.ENTREGA.submit();
}

</SCRIPT>

<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
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
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Análisis Solicitud Devolución</td>
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
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Devoluci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
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
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno asociado</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Turno asociado</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                
<form action="trasladoTurno.do" method="post" name="CONSULTA" id="CONSULTA">
                  
                  
						<% if (solicitud.getTurnoAnterior() != null &&
                    solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                    session.setAttribute(WebKeys.VISTA_VOLVER,request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));

                    %>
           
                      <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWTrasladoTurno.CONSULTAR_TURNO %>" value="<%= AWTrasladoTurno.CONSULTAR_TURNO %>">
                      <input  type="hidden" name="<%= CTurno.ID_TURNO %>" id="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow()%>" value="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow() %>">
                      <input  type="hidden" name="r1" id="r1" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" >
                      <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" />
					<%} %>
                          <tr>
                            <td>&nbsp;</td>
                            <td>Turno ingresado para la devoluci&oacute;n: </td>
                            <td>
		                 	<% try {
                 				turnoAnt.setNombre(CTurno.TURNO_ANTERIOR);
                  			   	turnoAnt.setCssClase("camposformtext");
                  			   	turnoAnt.setId(CTurno.TURNO_ANTERIOR);
								turnoAnt.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
							</td>
					<% if (solicitud.getTurnoAnterior() != null &&
                    solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                    session.setAttribute(WebKeys.VISTA_VOLVER,request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));

                    %>
                  
				<td align="right">
		            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
						<tr>
				 		  <td>Detalles Turno</td>
			 			  <td><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0"/></td>
			 		    </tr>
		  			  </table>
	  			 </td>                            
                   <%} %>         
					
				<td align="right">
		            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
						<tr>
				 		  <td>Cambiar Turno</td>
			 			  <td width="35" height="13"><a href="javascript:validarTurno('<%=AWDevolucion.CAMBIAR_TURNO_DEVOLUCION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" border="0"></a></td>
			 		    </tr>
		  			  </table>
	  			 </td>
	  			 
	  			 <% if (precaucionTurnoRegistroInscrito) {
                    %>
					 <tr> 
                          	<td>&nbsp;</td>
                          	<td class="titresaltados" colspan="2"> <img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21">El turno de registro fue inscrito, favor verificar las liquidaciones</td>						
                     </tr>
                  <%
                  } 
                  %>
	  			 
	  			 <% if (solicitud.getTurnoAnterior() != null &&
                    solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                    %>
					  
					 </tr>
                        <%if(turno!=null && turno.getSolicitud()!=null && turno.getSolicitud().getTurnoAnterior()!=null 
                             && turno.getSolicitud().getTurnoAnterior().getSolicitud()!=null
                             && turno.getSolicitud().getTurnoAnterior().getSolicitud().getSolicitudesHijas()!=null
                             && turno.getSolicitud().getTurnoAnterior().getSolicitud().getSolicitudesHijas().size()>0){
                             	SolicitudAsociada solAsociada=null;
                             		for(int i=0;i<turno.getSolicitud().getTurnoAnterior().getSolicitud().getSolicitudesHijas().size();i++){
                             		solAsociada=(SolicitudAsociada)turno.getSolicitud().getTurnoAnterior().getSolicitud().getSolicitudesHijas().get(i); 
                             		if(solAsociada!=null){
                             			if(solAsociada.getSolicitudHija()!=null && solAsociada.getSolicitudHija().getTurno()!=null){%>
                             	
                          <tr> 
                          	<td>&nbsp;</td>
                          	<td>Turno certificado asociado</td>
                          	<td class="campositem"><%=solAsociada.getSolicitudHija().getTurno().getIdWorkflow()%></td>
							
							<td align="right">
					            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
									<tr>
							 		  <td>Detalles Turno Asociado</td>
						 			  <td width="35" height="13"><a href="javascript:verDetallesTurno('<%=solAsociada.getSolicitudHija().getTurno().getIdWorkflow()%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" border="0"></a></td>
						 		    </tr>
					  			  </table>
				  			 </td>							
							
							<td>&nbsp;</td>
                          </tr>
                         <%				}
                         			}
                         		} 
                         }%>
                        
                  <%
                  } 
                  %>
                  
              </table>
              
              <% if (pagosTurnoAnterior != null){%>
              	
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos Asociados al Pago</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
                                        
              <%for (int i=0; i<pagosTurnoAnterior.size();i++){
              DocumentoPago docPago = (DocumentoPago)pagosTurnoAnterior.get(i);%>
              <br>
              <table width="100%" class="camposform">
              
                 	<tr>
                 	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 	<td width="179">Forma de Pago</td>
                 	<td width="211" class="campositem"><%=docPago.getTipoPago() == null ? "&nbsp;" : docPago.getTipoPago()%></td>
                 	                        
                 	<td width="146">Valor</td>            
                 	<td width="212" class="campositem"><%=format.format(docPago.getValorDocumento())%></td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Banco</td>
                 	<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
                 				<%=((DocPagoCheque)docPago).getBanco().getIdBanco() == null ? "&nbsp;" : ((DocPagoCheque)docPago).getBanco().getIdBanco()%>
                 	<%} else if (docPago instanceof DocPagoConsignacion){ %>
                 				<%=((DocPagoConsignacion)docPago).getBanco().getIdBanco() == null ? "&nbsp;" : ((DocPagoConsignacion)docPago).getBanco().getIdBanco()%>
                 	<%} else if (docPago instanceof DocPagoEfectivo){ %>
                 				&nbsp;
                 	<%} else if (docPago instanceof DocPagoGeneral){ %>
                 				<%=((DocPagoGeneral)docPago).getBanco() == null ? "&nbsp;" : ((DocPagoGeneral)docPago).getBanco().getNombre()%>
                 	<%} %>
                 	</td>
                        <% if (docPago instanceof DocPagoGeneral){ %>
                            <%if(((DocPagoGeneral)docPago).getNoAprobacion() != null){ %>
                                    <td>N&uacute;mero Aprobación</td>
                            <%}else if(((DocPagoGeneral)docPago).getNoConsignacion() != null){%>
                                    <td>N&uacute;mero Consignación</td>
                            <%}else{%>
                                    <td>N&uacute;mero</td>
                            <%}%>
                        <%}else{%>
                                    <td>N&uacute;mero</td>
                         <%}%>
                 	<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
                 				<%=((DocPagoCheque)docPago).getNoCheque() == null ? "&nbsp;" : ((DocPagoCheque)docPago).getNoCheque()%>
                 	<%} else if (docPago instanceof DocPagoConsignacion){ %>
                 				<%=((DocPagoConsignacion)docPago).getNoConsignacion()  == null ? "&nbsp;" : ((DocPagoConsignacion)docPago).getNoConsignacion()%>
                 	<%} else if (docPago instanceof DocPagoEfectivo){ %>
                 				&nbsp;
                        <%} else if (docPago instanceof DocPagoGeneral){ %>
                                <%if(((DocPagoGeneral)docPago).getNoAprobacion() != null){ %>
                                        <%=((DocPagoGeneral)docPago).getNoAprobacion() == null ? "&nbsp;" : ((DocPagoGeneral)docPago).getNoAprobacion()%>
                                <%}else if(((DocPagoGeneral)docPago).getNoConsignacion() != null){%>
                                        <%=((DocPagoGeneral)docPago).getNoConsignacion() == null ? "&nbsp;" : ((DocPagoGeneral)docPago).getNoConsignacion()%>
                                <%}else{%>
                                    &nbsp;
                                <%}%>
                 	<%}%>
                 	</td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Saldo a favor</td>
                 	<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
                 				<%=format.format(((DocPagoCheque)docPago).getSaldoDocumento())%>
                 	<%} else if (docPago instanceof DocPagoConsignacion){ %>
                 				<%=format.format(((DocPagoConsignacion)docPago).getSaldoDocumento())%>
                 	<%} else if (docPago instanceof DocPagoEfectivo){ %>
                 				<%=format.format(((DocPagoEfectivo)docPago).getSaldoDocumento())%>
                        <%} else if (docPago instanceof DocPagoGeneral){ %>
                 				<%=format.format(((DocPagoGeneral)docPago).getSaldoDocumento())%>
                 	<%}%>
                 	</td>
                 	<td>Asociado al turno</td>
                 	<td class="campositem"><%=(String)turnoPagoAsociado.get(i) %></td>
                 	</tr>
                        <tr>
                 	<td>&nbsp;</td>
                        <% List lista = (List) session.getAttribute("LISTA_CANALES_RECAUDO");%>   
                            <%if(!lista.isEmpty()){%>
                                <%for (int j = 0; j < lista.size(); j++) {%>
                                    <%String[] split = lista.get(j).toString().split("-");%>
                                    <%if(((DocPagoGeneral)docPago).getIdCtp() == Integer.parseInt(split[0])){%>
                                            <td>Canal de Recaudo</td>
                                            <td class="campositem"> <%= split[3] %> </td>                        
                                        <%}%>
                                    <%}%>
                                <%}%>
                        </tr>
             	</table>
             	<%}%>
             	<%}%>
              
              <br>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Consignaciones / Cheques</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Consignaci&oacute;n / Cheque</td>
                  <td>&nbsp;</td>
                </tr>
                
                <tr>
                  <td>&nbsp;</td>
                  <td>Consignaciones o cheques sobre los cuales se solicit&oacute; la devoluci&oacute;n:</td>
				  <td>&nbsp;</td>
                  <td>&nbsp;</td>
	  			</tr>
	  			<% 
	  				for (int i=0; i< consignaciones.size(); i++){
	  					Consignacion cons = (Consignacion)consignaciones.get(i);
	  					DocumentoPago docPago = cons.getDocPago();%>
						<br>
              			<table width="100%" class="camposform">
		                 	<tr>
		    	             	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		        	         	<td width="179">Forma de Pago</td>
		            	     	<td width="211" class="campositem"><%=docPago.getTipoPago() == null ? "&nbsp;" : docPago.getTipoPago()%></td>
		                 	
		                	 	<td width="146">Valor</td>
		                 		<td width="212" class="campositem"><%=format.format(docPago.getValorDocumento())%></td>
		                 		
	                 			<td>N&uacute;mero</td>
	                 			<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
	                 					<%=((DocPagoCheque)docPago).getNoCheque() == null ? "&nbsp;" : ((DocPagoCheque)docPago).getNoCheque()%>
	                 			<%} else if (docPago instanceof DocPagoConsignacion){ %>
	                 						<%=((DocPagoConsignacion)docPago).getNoConsignacion()  == null ? "&nbsp;" : ((DocPagoConsignacion)docPago).getNoConsignacion()%>
	                 			<%}%>
	                 			</td>		                 		
		                 	</tr>
	                   		<tr>
	                 			<td>&nbsp;</td>
		                 		<td>Saldo a favor</td>
	    	             		<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
	        	         					<%=format.format(((DocPagoCheque)docPago).getSaldoDocumento())%>
	            	     		<%} else if (docPago instanceof DocPagoConsignacion){ %>
	                	 					<%=format.format(((DocPagoConsignacion)docPago).getSaldoDocumento())%>
	                 			<%}%>
	                 			</td>
	                 			<td>Banco</td>
	                 			<td class="campositem"><%if (docPago instanceof DocPagoCheque){%>
	        	         					<%=((DocPagoCheque)docPago).getBanco().getNombre()%>
	            	     		<%} else if (docPago instanceof DocPagoConsignacion){ %>
	                	 					<%=((DocPagoConsignacion)docPago).getBanco().getNombre()%>
	                 			<%}%>
	                 			</td>
		                 		
		                 		<%if(turno.getSolicitud()!=null && turno.getSolicitud().getTurnoAnterior()==null){ %>
			                 		<td>&nbsp;</td>
		    	             		<td align="right"><a href="javascript:quitar('<%=i+1 %>','<%=AWLiquidacionDevolucion.ELIMINAR_CONSIGNACION_CHEQUE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"></a></td>
	    	             		<%}%>
	    	             		
	        	         	</tr>
             			</table>
             	<%}%>
	  		  </table>
             
             
             
             
             
   </form>          
  <form name="ENTREGA_ANALISIS" method="post" action="devolucion.do">            
   			<input type="hidden" name="<%=WebKeys.ACCION%>" value="">          
             <!-- Begin add consignacion -->
                
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Consignaci&oacute;n / Cheque</td>
                  <td>&nbsp;</td>
                </tr>
                <tr> 
                  <td>&nbsp;</td>
                  <td colspan="3"> 
	                  <%
	                    String checkedConsignacion = "", checkedCheque = "";
	                    String tipoChecked = (String)session.getAttribute(AWLiquidacionDevolucion.TIPO_BUSQUEDA);
                            List canales = (List) session.getAttribute(AWLiquidacionDevolucion.LISTA_CANALES_RECAUDO);
	                    if (tipoChecked == null || tipoChecked.equals(AWLiquidacionDevolucion.BUSQUEDA_CONSIGNACION)){
	                        checkedConsignacion = "CHECKED";
	                    }else{
	                        checkedCheque = "CHECKED";
	                    }
	                  %>
	                  <input type="radio" name="<%= AWLiquidacionDevolucion.TIPO_BUSQUEDA %>" value="<%= AWLiquidacionDevolucion.BUSQUEDA_CONSIGNACION %>" <%= checkedConsignacion %>/> Consignaci&oacute;n
	                  &nbsp;
	                  <input type="radio" name="<%= AWLiquidacionDevolucion.TIPO_BUSQUEDA %>" value="<%= AWLiquidacionDevolucion.BUSQUEDA_CHEQUE %>" <%= checkedCheque %> /> Cheque
                  </td>
                  <td>&nbsp;</td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
                  <td colspan="4">Consignaci&oacute;n o cheque sobre el cual se solicita la devolución</td>
				</tr>
				<tr>
					<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 	<td width="179">N&uacute;mero</td>
                 	<td width="211">
                 	<% try {
                 				TextHelper textHelper8 = new TextHelper();
                 				textHelper8.setNombre("NUMERO");
                  			   	textHelper8.setCssClase("camposformtext");
                  			   	textHelper8.setId("NUMERO");
								textHelper8.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
					</td>
                 	
                 	<td width="146">Fecha</td>
                 	<td width="212">
                 	<%

                    try {
                      if( null == session.getAttribute(AWLiquidacionDevolucion.FECHA_CONSIGNACION) ){
                        session.setAttribute(AWLiquidacionDevolucion.FECHA_CONSIGNACION, fechaAct );
                      }
                    	TextHelper textHelper9 = new TextHelper();
	                  	textHelper9.setId(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
	                    textHelper9.setNombre(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
	                  	textHelper9.setCssClase("camposformtext");
						textHelper9.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  		%>
					<a href="javascript:NewCal('<%=AWLiquidacionDevolucion.FECHA_CONSIGNACION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                 	</td>
				</tr>
				<tr>
				
				<td>&nbsp;</td>
                  <td>Banco:</td>

                 	<td width="211">
                     	<% try {
        	              		List bancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
            	          		ListaElementoHelper bancosHelper = new ListaElementoHelper();
                	      		if(bancos == null)
                    	  			bancos = new ArrayList();
                        	    List elemList = new ArrayList();
                            	ListIterator iter  = bancos.listIterator();
	                            while (iter.hasNext()){
    	                           Banco banco = ((Banco)iter.next());
        	                       elemList.add(new ElementoLista( banco.getIdBanco(), banco.getNombre() ) );
            	                 }
              					bancosHelper.setTipos(elemList);
	               		        bancosHelper.setNombre("BANCO");
              				    bancosHelper.setCssClase("camposformtext");
               			    	bancosHelper.setId("BANCO");
								bancosHelper.render(request,out);
								}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
 					</td>
                 	
                 	<td>Valor</td>
                 	<td>
                 	<% try {
                 				TextHelper textHelper11 = new TextHelper();
                 				textHelper11.setNombre("VALOR_DOCUMENTO");
                  			   	textHelper11.setCssClase("camposformtext");
                  			   	textHelper11.setId("VALOR_DOCUMENTO");
								textHelper11.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
                 	</td>
                </tr>
                <tr>
                 	<td>&nbsp;</td>
                 	<td>Saldo a favor</td>
                 	<td>
                 	<% try {
                 				TextHelper textHelper12 = new TextHelper();
                 				textHelper12.setNombre("SALDO_A_FAVOR");
                  			   	textHelper12.setCssClase("camposformtext");
                  			   	textHelper12.setId("SALDO_A_FAVOR");
								textHelper12.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
                 	</td>
                 	<td>&nbsp;</td>
                 	<td>&nbsp;</td>
                  	

             
              

               
				 <td align="right">
	              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
		 			  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 		  <td>Agregar Consignaci&oacute;n / Cheque</td>
		 			  <td><a href="javascript:addConsignacionCheque('<%=AWLiquidacionDevolucion.AGREGAR_CONSIGNACION_CHEQUE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
		 		    </tr>
	  			  </table>
	  			 </td>
	  			</tr>
	  			</table>
             <!-- End add consignacion -->
             
             
             
             
             
</form>
             
             
             <%

              // Declarations :: -----------------------------------------------------

              TextHelper textHelper = new TextHelper();


              %>
              <%--
                region needed resources/scripts
              --%>
<script type="text/javascript">

function reescribirValor(valor,id){
	var my_str = valor;
	var miles=1;
	if(my_str.indexOf(".")==-1){
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=my_str.length;i++){
				var desde = my_str.length-i*3;
				var hasta = my_str.length-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr=nStr+".00";
		document.getElementById(id).value = nStr;
		}
	} else {
		var largo = my_str.indexOf(".");
		var centavos = my_str.substr(largo,my_str.length);
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=largo;i++){
				var desde = largo-i*3;
				var hasta = largo-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr = nStr+centavos;
		document.getElementById(id).value = nStr;
		}
	}
}

</script>

	<br>

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Valores totales<br></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

<%

 boolean displayCondition;


%>
<%

 // Solo cuando es de Registro

 displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------

%>
<!---
                  yeferson
                  -->
              <!--<table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por Conservaci&#242n Documental</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table> -->  
                  
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por derechos</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              <%String aux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
              	if(aux!=null && !aux.equals("") && !aux.equals("0")){
              %>
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por derechos Mayor Valor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              <%} %>
<table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Saldo a Favor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>

<%

 } // :if

%>

<%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
<%if (local_SolicitudAnterior != null && local_SolicitudAnterior instanceof SolicitudRegistro){
String valorAux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);
if(valorAux!=null && !valorAux.trim().equals("") && !valorAux.trim().equals("0")){%>
<table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por certificados asociados</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              <%}%>
              
              <%String aux = (String)session.getAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
              	if(aux!=null && !aux.equals("") && !aux.equals("0")){
              %>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por certificados Mayor Valor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              <%} %>

 <%}%>
 

<%-- SOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>
<%

 // Solo cuando es de Registro

 displayCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------



%>



              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por impuestos</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              

<%

 } // :if

%>
<%-- EOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>




<%-- SOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>
<%

 // Solo cuando es de Certificados

 displayCondition = local_SolicitudAnterior instanceof SolicitudCertificado;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------



%>



              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor total</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }
                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Saldo a Favor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>


<%

 } // :if

%>

<%

 // Solo cuando es de Certificados

 displayCondition = local_SolicitudAnterior instanceof SolicitudConsulta;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------



%>



              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor total</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }
                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Saldo a Favor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>


<%

 } // :if

%>

<%

 // Solo cuando es de Certificados

 displayCondition = local_SolicitudAnterior instanceof SolicitudFotocopia;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------



%>



              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor total</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }
                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Saldo a Favor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>


<%

 } // :if

%>
<%-- JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno
     Certificado Masivos
--%>
<%

 // Solo cuando es de Certificados

 displayCondition = local_SolicitudAnterior instanceof SolicitudCertificadoMasivo;

 if( displayCondition )  {

 // ----------------------------------------------------------------------------



%>



              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor total</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%
                      try {

                        textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );
                        textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL );

                        textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        textHelper.setCssClase("campositem");
                        textHelper.setReadonly(true);

                        textHelper.render(request,out);
                      }
                      catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                      }
                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>

              <table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Saldo a Favor</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(     AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>


<%

 } // :if

%>

<%-- EOF:WIDGET + + + + + +  + + + + +  + + + + + + + + + + + + + + + + + + + + + --%>

<%-- SI EL TURNO DE REGISTRO POSEE ASOCIADOS TURNOS DE CERTIFICADOS, APARECERA EL CAMPO DE VALOR A DEVOLVER DE CERTIFICADOS --%>
<%if (local_SolicitudAnterior == null){
if(solicitud.getConsignaciones().size() > 0){%>
<table width="100%" class="camposform">
                <tr>
                  <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor a devolver por consignaciones o cheques</td>
                  <td width="1%">$</td>
                  <td width="15%">
                  <%

                  try {

                    textHelper.setId(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES );
                    textHelper.setNombre( AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES );

                    textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                    textHelper.setCssClase("campositem");
                    textHelper.setReadonly(true);

                    textHelper.render(request,out);

                  }
                  catch(HelperException re){
                    out.println("ERROR " + re.getMessage());
                  }

                  %>
                  </td>
                  <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                  <td> &nbsp; </td>
                </tr>
              </table>
<%}
 }%>
             
             
              <br>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {
                 				area.setNombre(CTurno.DESCRIPCION);
                  			   	area.setCssClase("campositem");
                  			   	area.setId(CTurno.DESCRIPCION);
                  			   	area.setReadOnly(true);
								area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%> 
					</td>
                </tr>
              </table>
	          <br>
              <hr class="linehorizontal">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitantes</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <% for (int i=0; i< solicitantes.size(); i++){
              		Ciudadano ciudadano = ((Solicitante)solicitantes.get(i)).getCiudadano();%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem"><%=ciudadano.getTipoDoc() != null ? ciudadano.getTipoDoc() : "&nbsp;" %></td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano.getDocumento() : "&nbsp;" %></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano.getApellido1() : "&nbsp;" %></td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano.getApellido2() : "&nbsp;" %></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombre</td>
                  <td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano.getNombre() : "&nbsp;" %></td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              <%} %>
              
              
                <table class="camposform">
                <tr>
                  <td><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>N&uacute;mero de folios</td>
                  <td class="campositem"><%=((SolicitudDevolucion)turno.getSolicitud()).getNumeroFolios() %></td>
                </tr>
               </table>
               
               <table class="camposform">
                <tr>
                  <td><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Documentos Entregados</td>
                  <%String documento="";
                  	List docEntregados= ((SolicitudDevolucion)turno.getSolicitud()).getCheckedItems();
                    SolCheckedItemDev check=null;
                    for(int i=0;i<docEntregados.size();i++){
                    check=(SolCheckedItemDev)docEntregados.get(i);
                    if(check!=null && check.getCheckItem()!=null)
                    	documento=check.getCheckItem().getNombre();%>
                  <td class="campositem"><%=documento%></td>
                  
                  <%} %>
                </tr>
               </table>

              
 
              
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                    <form name="ENTREGA" method="post" action="devolucion.do">
                        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.ANALISIS_ACEPTAR%>">
                        <input type="hidden" name="<%=CTurno.TURNO_ANTERIOR%>" value="">
                        <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" />				  
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                            <%if (turno.getSolicitud() != null && turno.getSolicitud().getTurnoAnterior() != null) {%>
                        <td class="campositem" width="140" height="21"><a href="javascript:analisisAceptar('<%=AWDevolucion.ANALISIS_ACEPTAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" border="0"></a></td>
                                <%} else {%>
                        <td class="campositem" width="140" height="21"><a href="javascript:cambiarAccion('<%=AWDevolucion.ANALISIS_ACEPTAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" border="0"></a></td>
                                <%}%>
                        <td class="campositem" width="140" height="21"><a href="javascript:cambiarAccion('<%=AWDevolucion.ANALISIS_NEGAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" border="0"></a></td>
                    </form>
                    <form name="logOut2" action="seguridad.do" method="post">
                        <td width="150" align="center">
                            <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cerrar.gif" width="150" height="21" border="0" >
                            <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                            <% Proceso proceso = ((Proceso) session.getAttribute(WebKeys.PROCESO));
                                if (proceso != null) {
                            %>

                            <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">

                            <%
                                }
                            %>
                        </td>
                        <td>&nbsp;</td>
                    </form>
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
 	<% try {
				helper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
	%>
    
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
