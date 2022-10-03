<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.NotasInformativasHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno"%>
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago"%>
<%@page import="gov.sir.core.negocio.modelo.DocPagoCheque"%>
<%@page import="gov.sir.core.negocio.modelo.DocPagoConsignacion"%>
<%@page import="gov.sir.core.negocio.modelo.DocPagoEfectivo"%>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano"%>
<%@page import="gov.sir.core.negocio.modelo.Banco"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.CheckItemDev"%>
<%@page import="gov.sir.core.web.acciones.devolucion.AWLiquidacionDevolucion"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%

	Date today;
    String fechaAct;
    today = new Date();
    fechaAct = DateFormatUtil.format(today);
    NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");
    
    
	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	vistaAnterior = vistaAnterior !=null 
		? "javascript:window.location.href='fases.view';" 
		: "javascript:history.back();";
	
	
	TextAreaHelper descripcion = new TextAreaHelper();
	descripcion.setCols("90");
	descripcion.setRows("10");
	ListaElementoHelper docHelper = new ListaElementoHelper();
	TextHelper textHelper = new TextHelper();
    NotasInformativasHelper helper = new NotasInformativasHelper();
        
   ListaCheckboxHelper boxHelper = new ListaCheckboxHelper();
   TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
   
   String documentoAnexo = (String) session.getAttribute(AWLiquidacionDevolucion.DESCRIPCION_DOCUMENTO);
   if(documentoAnexo==null){
   		documentoAnexo = "";
   }
   
   Turno tAnt = (Turno)session.getAttribute(AWLiquidacionDevolucion.TURNO_VALIDADO);
   
   List pagosTurnoAnterior = null;	
if(tAnt!=null){
	Solicitud solAnt;
	pagosTurnoAnterior = new ArrayList();
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
				}
			}
			tAnt = solAnt.getTurnoAnterior();
		}
	}
}
%>
   <SCRIPT>

	function cambiarAccion(text) {
       document.DEVOLUCION.ACCION.value = text;
       document.DEVOLUCION.submit();
	}
	
	function quitar(pos,accion){
		document.DEVOLUCION.POSICION.value = pos-1;
		document.DEVOLUCION.ACCION.value = accion;
		document.DEVOLUCION.submit();
	}
	
	function solicitantesFocus(){
		<%
		if(session.getAttribute(AWLiquidacionDevolucion.SOLICITANTE_AGREGADO)!=null){%>
			document.DEVOLUCION.NUMERO_FOLIOS.focus();
		<%}%>
	}
	
    </SCRIPT>
 

<%
    List itemsChequeo = (List)session.getAttribute(WebKeys.ITEMS_CHEQUEO_DEVOLUCIONES);
	if(itemsChequeo ==null){
%>
<script>
function sendItems() {
	document.CARGAR_ITEMS_CHEQUEO.submit();
} 
</script>
 
<body bgcolor="#CDD8EA"  onLoad="sendItems()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="turnoLiquidacionDevolucion.do" method="POST" name="CARGAR_ITEMS_CHEQUEO"  id="CARGAR_ITEMS_CHEQUEO">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=WebKeys.ACCION%>" value="<%= AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO%>">
</form>
</body>
<% 
   }
   
%>


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
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    	<table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Devoluciones</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table>
    </td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    	<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
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
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Devoluci&oacute;n de Dineros</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                    </tr>
                  </table>
                </td>
                <!--AHERRENO 28/05/2012
                    REQ 076_151 TRANSACCIONES-->
                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                        <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table>
          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <form action="turnoLiquidacionDevolucion.do" method="post" name="DEVOLUCION" id="DEVOLUCION">
			  <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWLiquidacionDevolucion.LIQUIDAR%>" >
			  <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" />
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
                  <td>&nbsp;</td>
                  <td>Turno sobre el cual se solicita la devolución</td>

                  	<td>
                 	<% try {
                 				textHelper.setNombre(CTurno.TURNO_ANTERIOR);
                  			   	textHelper.setCssClase("camposformtext");
                  			   	textHelper.setId(CTurno.TURNO_ANTERIOR);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
					</td>

             
              <br>

               
				 <td align="right">
	              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
		 			  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 		  <td>Agregar Turno</td>
		 			  <td><a href="javascript:cambiarAccion('<%=AWLiquidacionDevolucion.AGREGAR_TURNO_ANT%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
		 		    </tr>
	  			  </table>
	  			 </td>
	  			</tr>
	  			</table>
	  			
	  			<table border="0" cellpadding="0" cellspacing="2" class="camposform">
	  				  			
	  			<%
					String	turnoAnterior = (String)session.getAttribute("TURNO_ANTERIOR");%>
	  			
	  			<tr>
	  			  <td>
	  			  <% 
	  			  
	  			  try {
                  				TextHelper textHelper2 = new TextHelper();
 		                        textHelper2.setNombre("TURNO_ANTERIOR");
                  			    textHelper2.setCssClase("campositem");
                  			    textHelper2.setId("TURNO_ANTERIOR");
                  			    textHelper2.setEditable(false);
								textHelper2.setReadonly(true);
								textHelper2.render(request,out);
								textHelper2.setEditable(true);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
				   </td>
				   <td align="right">
						<table border="0" cellpadding="0" cellspacing="2" class="camposform">
						<tr>
							<td>Eliminar turno anterior</td>
							<td><a href="javascript:cambiarAccion('ELIMINAR_TURNO_ANTERIOR')"><img
									src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
									width="100%" height="25" border="0"></a>
							</td>
						</tr>
						</table>
					</td>
				  </tr>
				  </table>
				
				  <%
				  List listaAsociados = (List) session.getAttribute("LISTA_ASOCIADOS");
				  if (listaAsociados != null)
				  {
				     if (listaAsociados.size() > 0)
				     {
				         %>
				         Turnos de Certificados Asociados
	  			         <% try 
	  			           {
                                tablaHelper.setColCount(5);
                                tablaHelper.setListName("LISTA_ASOCIADOS");
               		            tablaHelper.render(request, out);
                           }
                           catch(HelperException re)
                           {
                              out.println("ERROR " + re.getMessage());
                           }
                       }
                    }%>
                          
              <br>
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
                        <% List lista = (List) session.getAttribute("LISTA_CANALES_RECAUDO");%>  
                            <%if(!lista.isEmpty() && lista != null){%> 
                                <%for (int j = 0; j < lista.size(); j++) {%>
                                    <%String[] split = lista.get(j).toString().split("-");
                                    int idCtpV = (int) (((DocPagoGeneral)docPago).getIdCtp());
                                    int idCtpVa = Integer.parseInt(split[0]);%>
                                            <%if(idCtpV == idCtpVa){
                                                    System.out.println("ENTRO AL IF BUGGEADO");%>
                                                    <td>Canal de Recaudo</td>
                                                    <td class="campositem"> <%=(split[4] != null? split[4] : lista.get(j).toString()) %> </td>     
                                            <% System.out.println("TERMINA EL IF BUGGEADO, ITERACIÓN = " + j);}%> 
                                        <%}%>
                                    <%}%>
                 	<td>&nbsp;</td>
                 	<td>&nbsp;</td>
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
                  <td colspan="3">
                  <%
                    String checkedConsignacion = "", checkedCheque = "";
                    String tipoChecked = (String)session.getAttribute(AWLiquidacionDevolucion.TIPO_BUSQUEDA);
                    if (tipoChecked == null || tipoChecked.equals(AWLiquidacionDevolucion.BUSQUEDA_CONSIGNACION))
                    {
                        checkedConsignacion = "CHECKED";
                    } else
                    {
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

                      if( null == session.getAttribute(AWLiquidacionDevolucion.FECHA_CONSIGNACION) ) {
                        session.setAttribute(AWLiquidacionDevolucion.FECHA_CONSIGNACION, fechaAct );
                      }

                    	TextHelper textHelper9 = new TextHelper();
	                  	textHelper9.setId(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
	                    textHelper9.setNombre(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
	                  	textHelper9.setCssClase("camposformtext");
						textHelper9.render(request,out);
					}
					catch(HelperException re){
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

                      		if(bancos == null){
                      			bancos = new ArrayList();
                      		}

                                List elemList = new ArrayList();
                                ListIterator iter  = bancos.listIterator();

                                while (iter.hasNext()){
                                  Banco banco = ((Banco)iter.next());
                                  //elemList.add(new ElementoLista( banco.getNombre(), banco.getNombre() ) ); //provisional
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
                  	

             
              <br>

               
				 <td align="right">
	              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
		 			  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 		  <td>Agregar Consignaci&oacute;n / Cheque</td>
		 			  <td><a href="javascript:cambiarAccion('<%=AWLiquidacionDevolucion.AGREGAR_CONSIGNACION_CHEQUE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
		 		    </tr>
	  			  </table>
	  			 </td>
	  			</tr>
	  			</table>
	  			
	  			<br>
	  			
             	
             	<% List listaCon = (List) session.getAttribute("LISTA_CONSIGNACIONES_DEVOLUCION"); 
            	if ((listaCon != null) && (listaCon.size() > 0)){ 
            	%>
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos Asociados al Pago</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%">
              
              <tr><td>
              
            	<%for (int i=0; i< listaCon.size(); i++){
            		DocumentoPago docPago = (DocumentoPago)listaCon.get(i);%>
            		
              <br>
              <table width="100%" class="camposform">
              
                 	<tr>
                 	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 	<td width="179">Forma de Pago</td>
                 	<td width="211" class="campositem"><%=docPago.getTipoPago() == null ? "&nbsp;" : docPago.getTipoPago()%></td>
                 	
                        <%
                            System.out.print("El id Documento pago es 1: "+docPago.getIdDocumentoPago());
                          %>
                        
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
                 	<%}%>
                 	</td>
                 	
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
                 	<td>&nbsp;</td>
                 	<td align="right"><a href="javascript:quitar('<%=i+1 %>','<%=AWLiquidacionDevolucion.ELIMINAR_CONSIGNACION_CHEQUE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"></a></td>
                 	</tr>
             	</table>
             	<%}%>
             	</td></tr>
             	</table>
             	<%}%>
             	
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
                 				descripcion.setNombre(CTurno.DESCRIPCION);
                  			   	descripcion.setCssClase("camposformtext");
                  			   	descripcion.setId(CTurno.DESCRIPCION);
								descripcion.render(request,out);
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
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                  Datos Solicitantes</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>
            
            <% List listaSol = (List) session.getAttribute("LISTA_SOLICITANTES_DEVOLUCION"); 
            if (listaSol != null){%>
            <table width="100%">
            	<!-- <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" /> -->
            	<tr><td> 
            	<%for (int i=0; i< listaSol.size(); i++){
            		Ciudadano solicitante = (Ciudadano)listaSol.get(i);%>
            <br>
            	<table width="100%" class="camposform">
            		
                 	<tr>
                 	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211" class="campositem"><%=solicitante.getTipoDoc() == null ? "&nbsp;" : solicitante.getTipoDoc()%></td>
                 	
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212" class="campositem"><%=solicitante.getDocumento() == null ? "&nbsp;" : solicitante.getDocumento()%></td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Primer Apellido</td>
                 	<td class="campositem"><%=solicitante.getApellido1() == null ? "&nbsp;" : solicitante.getApellido1()%></td>
                 	<td>Segundo Apellido</td>
                 	<td class="campositem"><%=solicitante.getApellido2() == null ? "&nbsp;" : solicitante.getApellido2()%></td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Nombre</td>
                 	<td class="campositem"><%=solicitante.getNombre() == null ? "&nbsp;" : solicitante.getNombre()%></td>
                 	<td>&nbsp;</td>
                 	<td>&nbsp;</td>
                 	<td align="right"><a href="javascript:quitar('<%=i+1 %>','<%=AWLiquidacionDevolucion.ELIMINAR_SOLICITANTE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a></td>
                 	</tr>
             	</table>
             	<%	} %>
             	</td></tr> 
             	</table>
             	<%}%>
             	<br>
             	
                <table width="100%" class="camposform">
                <tr>
                 	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211">
                     	<% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		if(docs == null){
                      			docs = new Vector();
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
 		                        textHelper.setNombre(CCiudadano.DOCUMENTO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.DOCUMENTO);
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
                 	<td>&nbsp;</td>
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
                 	<td>&nbsp;</td>
                 	<td width="19%"> <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                    	<tr> 
                    		<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    		<td>Agregar Solicitante</td>
                    		<td><a href="javascript:cambiarAccion('<%=AWLiquidacionDevolucion.AGREGAR_SOLICITANTE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                    		
                        </tr>
                         </table></td>
                 </tr>
                 </table>
              
                <br>
             <table width="100%" class="camposform">
             <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
             <td>Documentos Entregados </td>
              </tr>
                
                
              <%try 
               {
	 		        List tiposAux = (List) session.getAttribute(WebKeys.ITEMS_CHEQUEO_DEVOLUCIONES) ;
                    List tipos = new ArrayList();
                    List chequeados = (List) session.getAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED);
                    if (tiposAux==null)
                   	{
                     	tipos = new Vector();
                   	}
                   	
                   	else
                   	{
                   	   //Construir lista de elementos lista
                   	   
                   	   for (int j=0; j<tiposAux.size();j++)
                   	   {
                   	      ElementoLista element = new ElementoLista();
                   	      CheckItemDev itemCheq = (CheckItemDev)tiposAux.get(j);
                   	      element.setId(itemCheq.getIdCheckItemDev());
                   	      element.setValor(itemCheq.getNombre());
                   	      tipos.add(element);
                   	      
                   	    }
                   	    
                   	}
                   	
                  if (tipos == null || tipos.isEmpty()) {%> 
					<tr>
						<td>No hay datos</td>
					</tr>
					<%} else {
		
                   	for (int i=0;i<tipos.size();i++){
						ElementoLista e=(ElementoLista)tipos.get(i); 
						String checked = (String)chequeados.get(i);%>
						<tr>
						<%if (!checked.trim().equals("")) {%>
							<td><input type="checkbox" id="DOCUMENTOS_ENTREGADOS" checked="<%=checked%>" name="DOCUMENTOS_ENTREGADOS" value="<%=e.getId()%>"></td>
						<%}else{%>
							<td><input type="checkbox" id="DOCUMENTOS_ENTREGADOS" name="DOCUMENTOS_ENTREGADOS" value="<%=e.getId()%>"></td>
						<%} %>
						<td class="campositem"><%=e.getValor()%>
						<%if (e.getValor().equals("DOCUMENTO")){%>
							<input type="text" class="camposformtext" name="DESCRIPCION_DOCUMENTO" value="<%=documentoAnexo%>">
						<%} %>
						</td>
						</tr>
					<% }
					}
				}
				catch(Throwable t){
					out.println("ERROR " + t.getMessage());
				}
			%>
               <tr>
                  <td>&nbsp;</td>
                  </tr>
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>N&uacute;mero de Folios</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                 	<% try {
                 				textHelper.setNombre("NUMERO_FOLIOS");
                  			   	textHelper.setCssClase("camposformtext");
                  			   	textHelper.setId("NUMERO_FOLIOS");
								textHelper.render(request,out);
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
            
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></td>
                  </form>
                  <td width="150"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_cerrar.gif" onClick="<%=vistaAnterior%>" width="150" height="21" border="0"></td>
                  <td>&nbsp;</td>
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


        <%
        //Helper de Notas Informativas
		try{
			helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();
			//SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("DEVOLUCION");
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>


      </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
<script>
 solicitantesFocus()
</script>
