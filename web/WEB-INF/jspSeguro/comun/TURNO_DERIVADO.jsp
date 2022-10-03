<%@page import="java.util.*" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano" %> 
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado" %> 
<%@page import="gov.sir.core.negocio.modelo.Turno" %> 
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria" %> 
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %> 
<%@page import="gov.sir.core.negocio.modelo.Liquidacion" %> 
<%@page import="gov.sir.core.negocio.modelo.Pago" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.AplicacionPago" %> 
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago" %> 
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasInformativasHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasDevolutivasHelper"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CFase"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CRespuesta"%>

<%

String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
vistaAnterior = vistaAnterior !=null 
	? "javascript:window.location.href='"+vistaAnterior+"';" 
	: "javascript:history.back();";

	Turno turnoPadre = (Turno)session.getAttribute(WebKeys.TURNO);
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO_HIJO);

	List turnosHistoria = turno.getHistorials();

	String respuesta = null;
	TurnoHistoria thUltimo = null;
	if(turnosHistoria!=null && turnosHistoria.size() > 0) {
		thUltimo = (TurnoHistoria)turnosHistoria.get(turnosHistoria.size() - 1);

    	for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
        	TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
        	if(turnoAux.getFase() != null && 
					((turnoAux.getFase()).equals(CFase.DEV_RESOLUCION) || 
					(turnoAux.getFase()).equals(CFase.DEV_ANALISIS_REPOSICION) || 
					(turnoAux.getFase()).equals(CFase.DEV_ANALISIS_APELACION))) {
				respuesta = turnoAux.getRespuesta();
        	}
        	
        	if(turnoAux.getFase() != null && 
				((turnoAux.getFase()).equals(CFase.COR_REVISAR_APROBAR) || 
				(turnoAux.getFase()).equals(CFase.ACT_APROBAR_GUARDAR))) {
				respuesta = turnoAux.getRespuesta();
        	}
        }
	}

	Solicitud solicitud = turno.getSolicitud();

	//SE VERIFICA LA RESPUESTA PARA CADA PROCESO, SI APLICA.
	if(solicitud!=null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion) {
		if(respuesta!=null ) {
			if(respuesta.equals(CRespuesta.CONFIRMAR)) {
				respuesta = "APROBADA";
			} else if(respuesta!=null && respuesta.equals(CRespuesta.NEGAR)) {
				respuesta = "NEGADA";
			}
		}
	}

	if(solicitud!=null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCorreccion){
		if(respuesta!=null ) {
			if(respuesta.equals(CRespuesta.CONFIRMAR) || respuesta.equals(CRespuesta.CONFIRMADO)) {
				respuesta = "APROBADA";
			} else {
				respuesta = "NEGADA";	
			}
		}
	}

	if(solicitud!=null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificado){
		SolicitudCertificado solCert = (SolicitudCertificado)solicitud;
		int a = solCert.getNumImpresiones();
		if(a > 0) {
			respuesta = "IMPRESO";
		} else {
			respuesta = "NO IMPRESO";
		}
	}

	Ciudadano ciudadano = solicitud.getCiudadano();

	String tipoCertificado = "&nbsp;";
	String tipoTarifa = "&nbsp;";
	String matricula = "&nbsp;";
	String turnoAnterior = "&nbsp;";
	String numeroRecibo =  "&nbsp;";

	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();

	List liquidaciones = solicitud.getLiquidaciones();

	double valorPagado = 0.0;
	double valorPorPagar = 0.0;
	double valorDevuelto = 0.0;
	double valorPorDevolver = 0.0;

	for(Iterator iter = liquidaciones.iterator(); iter.hasNext();) {
		Liquidacion liq = (Liquidacion)iter.next();
		if(liq.getPago() != null && liq.getValor() >= 0) {
			valorPagado += liq.getValor();
			continue;
		} else if(liq.getPago() == null && liq.getValor() >= 0) {
			valorPorPagar += liq.getValor();
			continue;
		} else if(liq.getPago() != null && liq.getValor() < 0) {
			valorDevuelto += liq.getValor();
			continue;
		} else if(liq.getPago() == null && liq.getValor() < 0) {
			valorPorDevolver += liq.getValor();
			continue;
		}
	}

	if(!liquidaciones.isEmpty()) {
		int lastIndex = liquidaciones.size() - 1;
		if(lastIndex >= 0) {
			Liquidacion ultimaLiq = (Liquidacion)liquidaciones.get(lastIndex);
			Pago pago = ultimaLiq.getPago();
			if(pago != null) {
				numeroRecibo = pago.getNumRecibo();	
			}
		}
	}


	if(solicitud.getTurnoAnterior() != null ) {
		turnoAnterior = solicitud.getTurnoAnterior().getIdWorkflow();
	}

	boolean isSolicitudCertificado = false;
	if(solicitud instanceof SolicitudCertificado) {
		SolicitudCertificado  solCert = (SolicitudCertificado)solicitud;
		int indexUltimaLiquidacion = solCert.getLiquidaciones().size() - 1;
		if(indexUltimaLiquidacion >= 0) {
			Liquidacion ultimaLiquidacion = (Liquidacion)solicitud.getLiquidaciones().get(indexUltimaLiquidacion);
			if(ultimaLiquidacion instanceof LiquidacionTurnoCertificado) {
				LiquidacionTurnoCertificado liqTurCer = (LiquidacionTurnoCertificado)ultimaLiquidacion;
				tipoCertificado = liqTurCer.getTipoCertificado().getNombre();
				tipoTarifa = ((liqTurCer.getTipoTarifa()==null)?"&nbsp;":liqTurCer.getTipoTarifa());
			}
		}
	}

	List solicitudesFolio = solicitud.getSolicitudFolios();
	if(!solicitudesFolio.isEmpty()) {
		SolicitudFolio solFolio = (SolicitudFolio)solicitudesFolio.get(0);
		matricula = solFolio.getIdMatricula();
	}

    //Helper para notas informativas
	ListarNotasInformativasHelper notasInformativasHelper = new ListarNotasInformativasHelper();	
	//Helper para notas devolutivas
	ListarNotasDevolutivasHelper notasDevolutivasHelper = new ListarNotasDevolutivasHelper();	

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
	function cambiarAccion(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	}

	function cambiarAccionSubmit(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
		document.BUSCAR.submit();
	}

	function consultarMatricula(text) {
		document.MATRICULAS.<%= CFolio.ID_MATRICULA %>.value = text;
		document.MATRICULAS.submit();
	}

	function verNota(nombre,valor,dimensiones) {
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}
        
        //@author : David A Rubio J
        //@change : Se agrega función para mostrar los detalles de un folio en una ventana emergente con pestañas
        function verAnotacionPersonalizada(matricula,nombre,valor,dimensiones,pos){
            nombre=nombre+"&ID_MATRICULA="+matricula;
            popup=window.open(nombre,valor,dimensiones);
            popup.focus();
        }
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno Derivado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
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
				<table border="0" cellpadding="0" cellspacing="0" align = "center">
				  <tr align = "center">
					<td class="contenido" align = "center">
					<B>EL TURNO <%= turnoPadre.getIdWorkflow() %> DEPENDE DEL TURNO <%= turno.getIdWorkflow() %>.</B>
					</td>
	        		  </tr>
					  <tr align = "center">
					<td class="contenido" align = "center">
					<B>A CONTINUACION SE MUESTRA EL ESTADO DE LA EJECUCION DEL TURNO <%= turno.getIdWorkflow() %>.</B>
					</td>
					
				  </tr>
				</table>
				<br>
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    
                    <td class="bgnsub">Datos b&aacute;sicos de Turno</td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="220">N&uacute;mero del Turno</td>
                    <td class="campositem"> <%=  turno.getIdWorkflow() != null ? turno.getIdWorkflow() : "&nbsp;" %> </td>
                  </tr>
                  <tr>
                    <td width="220">Fecha de Radicaci&oacute;n </td>
                    <td class="campositem"> <%= FechaConFormato.formatear( turno.getFechaInicio(), "dd/MM/yyyy") %> </td>
                  </tr>
                  <tr>
                    <td width="220">Tipo de Certificado </td>
                    <td class="campositem"> <%=  tipoCertificado != null ? tipoCertificado : "&nbsp;" %> </td>
                  </tr>
                  <tr>
                    <td width="220">Tipo de Tarifa </td>
                    <td class="campositem"> <%=  tipoTarifa != null ? tipoTarifa : "&nbsp;" %> </td>
                  </tr>
                  <tr>
                   <td width="220">N&uacute;mero de Recibo </td>
                    <td class="campositem"> <%=  numeroRecibo != null ? numeroRecibo : "&nbsp;" %> </td>
                  </tr>
                  <tr>
                   <td width="220">Valor Pagado </td>
                    <td class="campositem"> <%= valorPagado %> </td>
                  </tr>
                  <tr>
                    <td width="220">Valor por Pagar </td>
                    <td class="campositem"> <%= valorPorPagar %> </td>
                  </tr>
                  <tr>
                    <td width="220">Valor Devuelto </td>
                    <td class="campositem"> <%= valorDevuelto %> </td>
                  </tr>
                  <tr>
                    <td width="220">Valor por Devolver </td>
                    <td class="campositem"> <%= valorPorDevolver %> </td>
                  </tr>
                  	<%
						if(turno != null) {
							if(turno.getFechaFin() == null) {
	              	%>
	              <tr>
	                <td width="220">Estado Actual </td>
	                <td class="campositem"> <%=  thUltimo!=null && thUltimo.getNombreFase()!=null ? thUltimo.getNombreFase() :"&nbsp;" %> </td>
	              </tr>
	              <tr>
	                <td width="220">Estación Asignada </td>
	                <td class="campositem"> <%=  thUltimo!=null && thUltimo.getIdAdministradorSAS()!=null ? thUltimo.getIdAdministradorSAS() :"&nbsp;" %> </td>
	              </tr>
	              	<%
	                		} else {
	                %>
	              <tr>
	                <td width="220">Estado Actual </td>
	                <td class="campositem"> El turno ha finalizado. </td>
	              </tr>                  
	                <%
	                 		}
                  		}
                  
                  		if(respuesta != null) {
	                %>
	              <tr>
	                <td width="220"><B>Resolución</B></td>
	                <td class="campositem"> <%=  respuesta%> &nbsp;  </td>
	              </tr>
	                <%
                  		}
                  	%>
                  <tr>
	                <td width="220">Relación Actual </td>
	                <% 
						String idRelacionActual = turno.getIdRelacionActual();
	                    if (idRelacionActual == null) {
	                       idRelacionActual = "NO ESTA RELACIONADO";
	                    }
					%>
	                <td class="campositem"> <%=idRelacionActual%> </td>
	              </tr>    
                  <tr>
	                <td width="220">Fase Relacion Actual </td>
	                <% 
						String idFaseRelActual = turno.getIdFaseRelacionActual();
	                    if (idFaseRelActual == null) {
	                       idFaseRelActual = "NO ESTA RELACIONADO";
	                    }
					%>
	                <td class="campositem"> <%= idFaseRelActual %> </td>
	              </tr>
                </table>
                <br>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Matr&iacute;culas Asociadas</p></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   	</table>
			   	<form action="trasladoTurno.do" method="POST" name="MATRICULAS" id="MATRICULAS">
                <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWTrasladoTurno.CONSULTAR_MATRICULA %>" value="<%=  AWTrasladoTurno.CONSULTAR_MATRICULA %>">
                <input  type="hidden" name="<%= CFolio.ID_MATRICULA %>" id="" value="">
			   	<table width="100%" class="camposform">
			   	<%
			   		if (turno.getSolicitud().getSolicitudFolios() != null) {
                                            int i=0;
				   		for (Iterator itMatriculas = turno.getSolicitud().getSolicitudFolios().iterator(); itMatriculas.hasNext();){
				   			SolicitudFolio solFol = (SolicitudFolio)itMatriculas.next();
				%>
				  <tr>
			        <td width="20%" class="campositem"><%=solFol.getIdMatricula()%></td>
                                <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verAnotacionPersonalizada('<%=solFol.getIdMatricula()%>', 'consultar.folio.do?POSICION=<%= i %>','Folio','width=900,height=450,scrollbars=yes','0')" style="cursor:hand"></td>
			        <!-- <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="consultarMatricula('<%=solFol.getIdMatricula()%>')" style="cursor:hand"></td> -->
		          </tr>
				<%
                                    i++;
				   		}
			   		}
			   	%>
                </table>
                </form>
        		<br>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Historial de fases del turno</p></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   	</table>
			   	
			   	
			   	 <table> 
                 <tr>
                 
                 <td width="1%"></td>
                 <td width="15%" class="contenido"><B>FASE</B></td>
                 <td width="1%"></td>
                 <td width="15%" class="contenido"><B>ATENDIDO POR</B></td>
                 <td width="1%"></td>
                 <td width="15%"class="contenido"><B>FECHA</B></td>
                 <td width="1%"></td>
                 <td width="15%"class="contenido"><B>RELACION FASE</B></td>
                 <td width="1%"></td>
                 <td width="15%"class="contenido"><B>RELACION SIGUIENTE</B></td>
                  </tr>			   	
			   	</table>
			   	
			   	
                <%
            	for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();){
	            	TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
	            	String nombreUsuario = null;
	            	String nombreFase = null;
	            	
	            	if(turnoAux.getFase() != null && (turnoAux.getFase()).equals("FINALIZADO")){
						turnoAux.setNombreFase("FINALIZADO");	            	
	            	}	            	
	            	if (turnoAux.getUsuarioAtiende()!=null){
	            	   nombreUsuario = turnoAux.getUsuarioAtiende().getNombre();
	            	}
	            	if (turnoAux.getFase()!=null){
	            	   nombreFase = turnoAux.getNombreFase();
	            	}
                %>
                <table width="100%" class="camposform">
                <tr>
                    <td width="1%"></td>
                    <td width="15%" class="campositem"><%=  nombreFase!=null?nombreFase:"&nbsp;" %></td>
                    <td width="1%"></td>
                    <td width="15%" class="campositem"><%=  nombreUsuario!=null?nombreUsuario:"&nbsp;" %></td>
                    <td width="1%"></td>
                    <td width="15%" class="campositem"><%=  turnoAux.getFecha()!=null? FechaConFormato.formatear( turnoAux.getFecha(), "dd/MM/yyyy HH:mm:ss") :"&nbsp;"   %> </td>
                  
                    <td width="1%"></td>
                    <%String idRelacion =null;
                    idRelacion = turnoAux.getIdRelacion();
                    if (idRelacion == null)
                    {
                       idRelacion ="&nbsp";
                    }%>
                    <td width="15%" class="campositem"><%=idRelacion%> </td>
                    
                    
                    <td width="1%"></td>
                    <%String idRelacionSig =null;
                    String faseRelacionSig = null;
                    idRelacionSig = turnoAux.getIdRelacionSiguiente();
                    if (idRelacionSig == null)
                    {
                       idRelacionSig ="&nbsp";
                    }%>
                    <td width="15%" class="campositem"><%=idRelacionSig%> </td>                 
                  
                  
                  
                  </tr>
                </table> 
                <%
                   	}
                %>
                
                <br>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Datos del solicitante</p></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   	</table>
                <table width="100%" class="camposform">
                <tr>
                    <td width="220">Nombre del Solicitante: </td>
                    <td class="campositem"> <%=  (ciudadano==null)?"&nbsp;":ciudadano.getNombreCompletoCiudadano() + "&nbsp;"%> </td>
                    <td width="220">Telefono: </td>
                    <td class="campositem"> <%=  (ciudadano!=null && ciudadano.getTelefono()!=null)?ciudadano.getTelefono():"&nbsp;"%> </td>
                  </tr>
                  <tr>
                   <td width="220">Tipo de Documento del Solicitante: </td>
                    <td width="220" class="campositem"> <%= (ciudadano==null)?"&nbsp;":ciudadano.getTipoDoc() + "&nbsp;"%> </td>
                    <td width="220">N&uacute;mero de Documento del Solicitante: </td>
                    <td class="campositem"> <%=  (ciudadano==null)?"&nbsp;":ciudadano.getDocumento() + "&nbsp;"%> </td>
                  </tr>
                </table> 
                  
                
                
                <br>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Detalles Antiguo Sistema</p></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   	</table>
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
              
          
              
         <% 
         //Obtener información básica de los pagos.
         Iterator itPagos = liquidaciones.iterator();
         %>
         
          <!--DATOS DE LA LIQUIDACIÓN.-->
          <br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		  <tr>
		  
		  <td class="bgnsub"><p>Pagos asociados</p></td>
		 <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		  </tr>
          </table>
              
          <table width="100%" class="camposform">
		  <%
			  while (itPagos.hasNext()){		
				Liquidacion liqAux = (Liquidacion) itPagos.next();      
		  %>
          <tr>
          <td width="220">Valor pagado </td>
          <td width="220" class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqAux.getValor()) %> </td>
          <td width="220"> </td>
          <td class="campositem">  </td>
          </tr>
                 
          <% //Mostrar las aplicaciones de pago correspondientes
          Pago pagoAux = liqAux.getPago();
          if (pagoAux != null)
          {
               List listaAplicaciones = pagoAux.getAplicacionPagos();
                     
               if (listaAplicaciones != null)
               {
                   for (int k= 0; k<listaAplicaciones.size(); k++)
                   {
                      AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
                      if (apPago != null)
                      {
                          DocumentoPago doc = apPago.getDocumentoPago();
                          if (doc != null)
                          {
                             %>
                             <tr>
                             <td width="220">Forma de Pago: </td>
                             <td width="220" class="campositem"> <%=doc.getTipoPago()%> </td>
                             <td width="220">Valor Pagado: </td>
                             <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(doc.getValorDocumento()) %> </td>
                             </tr>  
                        <%}
                      }
                    }
                 }
           }
           %>  
               
           </tr>  
           <tr>
           <td><br></td>
           </tr>                               
                  
           <%}%>
           </table>
           <br>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" onClick="<%=vistaAnterior%>">
				</td>
			</tr>
			</table>
           
           
              <%--
              <br>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Notas informativas del turno</p></td>
			   	 	<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   </table>
              
              <% 
				try{
					notasInformativasHelper.render(request,out);
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}	
				%>
				
				
              <br>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			    	<tr>
			    	
			    	<td class="bgnsub"><p>Notas Devolutivas del turno</p></td>
			   	 	<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
			   	 	<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			   		</tr>
			   </table>
              
              <% 
				try{
					notasDevolutivasHelper.render(request,out);
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}	
				%>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de ver los detalles del turno haga click en Volver. </td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    
                    <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
                    <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO %>" value="<%=  AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO %>">
                    <td width="150">
                    <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>

					<%
						if(turno !=null){
					%>
                    <td width="150">
	                    <a href="javascript:cambiarAccionSubmit('<%=AWTrasladoTurno.CONSULTAR_TURNO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></a>
	                    <input type='hidden' name='r1' value="<%= gov.sir.core.negocio.modelo.constantes.CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
	                    <input type='hidden' name='<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>' value="<%= turno.getIdWorkflow()!=null ?turno.getIdWorkflow().trim():""%>">
                    </td>                
                    <%
                    	}
                    %>    
                    <td>&nbsp;</td>
                    
                    </FORM>    
                  </tr>
                </table>
                --%>
                
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>