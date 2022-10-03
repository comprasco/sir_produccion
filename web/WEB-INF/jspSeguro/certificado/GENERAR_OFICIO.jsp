<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<% 
	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	java.text.NumberFormat formato = java.text.NumberFormat.getInstance();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();  
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);	
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}
	
	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);	
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}
	
		String ocultarAnotaciones = request.getParameter(WebKeys.OCULTAR_ANOTACIONES);	
	if(ocultarAnotaciones == null){
		ocultarAnotaciones = (String)session.getAttribute(WebKeys.OCULTAR_ANOTACIONES);
		if(ocultarAnotaciones==null){
			ocultarAnotaciones = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,ocultarAnotaciones);
	}
	
	NotasInformativasHelper helper = new NotasInformativasHelper();
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudCertificado solicitud = (SolicitudCertificado) turno.getSolicitud();
	LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
	List liquidaciones = solicitud.getLiquidaciones();
	for(int i=0;i<liquidaciones.size();i++){
		double id = new Double(((LiquidacionTurnoCertificado)liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
		if(id==solicitud.getLastIdLiquidacion()){
			liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(i);
		}
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();

	List solicitudFolios  = solicitud.getSolicitudFolios();	
	Folio folioSolicitud = null;
	
	if(solicitudFolios!=null){		
		if(solicitudFolios.size()>0){
			SolicitudFolio solFolio = (SolicitudFolio)solicitudFolios.get(0);
			folioSolicitud = solFolio.getFolio();
		}
	}
	
	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
	
	if(folio==null){
		ocultarFolio = "TRUE";
	}
	


   String resp = "";
   
   java.util.Iterator it = null;

	if(turno!=null && turno.getHistorials()!=null){
		it = turno.getHistorials().iterator();
	}

   if(it!=null){
   
	   while(it.hasNext()){
	   		gov.sir.core.negocio.modelo.TurnoHistoria th = (gov.sir.core.negocio.modelo.TurnoHistoria)it.next();

	   		if(th!=null && th.getFase()!=null && (th.getFase().equals(gov.sir.core.negocio.modelo.constantes.CFase.ANT_REVISION)||th.getFase().equals(gov.sir.core.negocio.modelo.constantes.CFase.ANT_REVISION_INICIAL))){
	   			if(th.getRespuesta()!=null &&( th.getRespuesta().equals(gov.sir.core.negocio.modelo.constantes.CRespuesta.NEGAR) || th.getRespuesta().equals(gov.sir.core.negocio.modelo.constantes.CRespuesta.RECHAZADO))){
	   				resp = th.getRespuesta();
	   			}
	   		}
	   }
   }

%>
<script>
	function send(){
      document.forma.submit()
    }
	function cambiarAccion(text) 
	{
		document.CERTIFICADO.ACCION.value = text;
		document.CERTIFICADO.submit();
	}      
</script>

  <%
  String plantilla = request.getParameter(gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA);
  if(plantilla !=null){
  	  request.getSession().setAttribute(gov.sir.core.web.WebKeys.PLANTILLA,plantilla);
 }

  if(plantilla==null){ 
	  plantilla = (String)request.getSession().getAttribute(gov.sir.core.web.WebKeys.PLANTILLA);
  }
  if(plantilla==null){
  %>
  <body onLoad="send()" >  
    <form action="certificado.do" method="POST" name="forma"  id="forma">
  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.certificado.AWCertificado.CONSULTAR_PLANTILLA_PERTENENCIA%>">
  		<input  type="hidden" name="<%= WebKeys.PLANTILLA %>" id="<%=WebKeys.PLANTILLA%>" value="<%=resp!=null?resp:""%>">  		
  		<input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CFolio.ID_ZONA_REGISTRAL%>" id="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_ZONA_REGISTRAL%>" value="<%=folioSolicitud!=null&&folioSolicitud.getZonaRegistral()!=null?folioSolicitud.getZonaRegistral().getIdZonaRegistral():""%>">  		
   		<input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA %>" id="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>" value="<%=folioSolicitud!=null?folioSolicitud.getIdMatricula():""%>">  		 		  		
  		
	</form>	  
  <%
  }else{
  %>
  <body>  
  <%			
  }
  %>


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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Certificados</td>
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
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table border="0" align="right" cellpadding="0" cellspacing="2">
        <tr>
					<%if(ocultarTurno.equals("FALSE")){%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
            <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" id="OF1">            
            <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF1').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
        </form>
						
					<%}else{%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
            <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" id="OF2">                        
            <td width="170" class="contenido">Haga click para maximizar</td>
            <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF2').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
        </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
         
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          

    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15"></td>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=turno.getIdWorkflow()%>&nbsp;</td>
        </tr>
    </table>
          
           <%if(ocultarTurno.equals("FALSE")){%>
           
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnsub">Datos Turno</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>

  <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr> 
            <td>Tipo de Certificado</td>
            <td>Cantidad</td	>
        </tr>
        <tr> 
            <td class="campositem"><%= ((TipoCertificado)liquidacion.getTipoCertificado()).getNombre()%>&nbsp;</td>
            <td class="campositem"><%= solicitud.getNumeroCertificados()%>&nbsp;</td>
        </tr>
    </table>
						

			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1"  src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Certificado Anterior</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>N&uacute;mero del Turno Anterior</td>
            <td class="campositem">
            <%if (turnoAnterior!=null){
            %><%=turnoAnterior.getIdWorkflow()%>
            <%}
            else {
            out.write("No hay turnos anteriores");
            }%>&nbsp;</td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Matr&iacute;cula Inmobiliaria de la Propiedad</td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
					
					<%List solFolios = solicitud.getSolicitudFolios();
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()){
						while(itSolFolios.hasNext()){
							SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=((Folio)solFolio.getFolio()).getIdMatricula()%>&nbsp;</td>
        </tr>
							
						<% }%>
					<% }else{%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem">No hay elementos que mostrar</td>
        </tr>
					<%}%>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>	
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Adicionales </td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr> 
            <td width="179">Tipo de Identificaci&oacute;n</td>
            <td width="211" class="campositem"><%= ciudadano.getTipoDoc()!=null?ciudadano.getTipoDoc():""%>&nbsp;</td>
            <td width="146">N&uacute;mero</td>
            <td width="212" class="campositem"><%= ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
        </tr>
        <tr> 
            <td>Primer Apellido</td>
            <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
            <td>Segundo Apellido</td>
            <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
        </tr>
        <tr> 
            <td>Nombre</td>
            <td class="campositem"><%= ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
			
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Liquidaci&oacute;n</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="293">Valor</td>
            <td width="11">$</td>
            <td width="445" class="campositem"><%=liquidacion!=null?""+formato.format(liquidacion.getValor()):""%>&nbsp;</td>
        </tr>
    </table>
    <br>
    <hr class="linehorizontal">
       <%}%>
    </td>
		
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>

    <tr> 
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>
</table>











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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td>
          <img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table border="0" align="right" cellpadding="0" cellspacing="2">
              <tr>
					<%if(ocultarFolio.equals("FALSE")){%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
    	        <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" id="OF3">                            
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF3').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
            </form>
						
					<%}else{%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">
	            <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>"  id="OF4">                            
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF4').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
            </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

    </table>
        <%if(ocultarFolio.equals("FALSE")){%>
  
  
  
      
      
      
 	<!--Se muestra la informaci�n del folio-->         
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub"><p>Informacion Apertura de Folio</p></td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>


    <%
     Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
    
      ZonaRegistral zona=null;
      if(folio!=null){
      	zona = folio.getZonaRegistral(); 
      }
 
      Vereda vereda=null;
      Municipio municipio=null;
      Departamento depto=null;
      
      String nombreCirculo="&nbsp;";
      String nombreVereda="&nbsp;";
      String nombreMunicipio="&nbsp;";
      String nombreDepto="&nbsp;";
      
      if(zona!=null)
      {
        circulo=zona.getCirculo();
        nombreCirculo=circulo.getNombre();
        if(nombreCirculo==null)
          nombreCirculo="&nbsp;";  
          
        vereda=zona.getVereda();
        if(vereda!=null)
        {
          nombreVereda= vereda.getNombre();
          if(nombreVereda==null)
            nombreVereda="&nbsp;";
            
          municipio=vereda.getMunicipio();
          if(municipio!=null)
          {
            nombreMunicipio = municipio.getNombre();
            if (nombreMunicipio==null)
              nombreMunicipio="&nbsp;";
            
            depto= municipio.getDepartamento();
            if(depto!=null)
              nombreDepto=depto.getNombre();
          }  
        }
      }
  
   %>		



    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Circulo Registral:</td>
        <td class="campositem"><%= circulo != null ? circulo.getNombre() : "&nbsp;"%>&nbsp;</td>
        <td>Depto:</td>
        <td class="campositem"><%= nombreDepto%>&nbsp;</td>
        <td>Municipio:</td>
        <td class="campositem"><%= nombreMunicipio %>&nbsp;</td>
        <td>Vereda:</td>
        <td class="campositem"><%= nombreVereda%>&nbsp;</td>
        </tr>
        <tr>
        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Fecha Apertura: </td>
        <td class="campositem">
        <%
        if(folio!=null && folio.getFechaApertura()!=null){
	        try {
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(folio.getFechaApertura());
				fechaHelper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
		}
		%>&nbsp;
        </td>
        
        <td>Radicacion:</td>
	    <td class="campositem"><%=folio.getRadicacion()!=null?folio.getRadicacion():turno.getIdWorkflow()%></td>
        <td>Con:</td>
        <%if(folio.getDocumento() != null){%>
        <td class="campositem"><%= folio.getDocumento().getTipoDocumento().getNombre()%> de: <%=folio.getDocumento().getNumero()!=null?folio.getDocumento().getNumero():"&nbsp;"%>&nbsp;</td>
        <%} else {%>
        <td class="campositem">No hay documento asociado</td>
        <%}%>
        <td>Cod Catastral : </td>
        <td class="campositem"><%= folio.getCodCatastral()%>&nbsp;</td>
        </tr>
    </table>

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>ESTADO DEL FOLIO </td>
        <td class="campositem"><strong><% 
                  if(folio.getEstado() != null){
                  		out.write(folio.getEstado().getNombre());
                  } else {
                  		out.write("El folio no tiene estado asociado");
                  }
                  %></strong>&nbsp;</td>
        </tr>
    </table>
    
      
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"><p>Descripci&oacute;n: Cabida y Linderos</p></td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td class="campositem"><%=folio.getLindero()%>&nbsp;</td>
        </tr>
    </table>
    
    

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Complementaci&oacute;n</td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <%if(folio.getComplementacion()!=null){%>
            <td class="campositem"><p><%=folio.getComplementacion().getComplementacion()!=null ? folio.getComplementacion().getComplementacion() : "&nbsp;"%></p>&nbsp;</td>
        	<%}else{%>
        	<td class="campositem"><p>&nbsp;</p>&nbsp;</td>
        	<%}%>
        </tr>
        <br>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"><p>Direcci&oacute;n del inmueble</p></td>
        <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Tipo del Predio </td>
            <td class="campositem"><%= (folio.getTipoPredio()!=null && folio.getTipoPredio().getNombre()!=null)?folio.getTipoPredio().getNombre():"&nbsp;"%>&nbsp;</td>
        </tr>
    </table>
    

    <table width="100%" class="camposform">
              <% if(folio.getDirecciones().isEmpty()){%>
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td class="campositem">No tiene direcciones registradas para este folio</td>
        </tr>              	              
              	<%} else {
				 int indexDireccion = 1;              	
              	 Iterator itDirecciones = folio.getDirecciones().iterator();
	             while(itDirecciones.hasNext()){
	              	Direccion direccion = (Direccion) itDirecciones.next();%>
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td class="campositem"><b><%=""+indexDireccion%>&nbsp;&nbsp;-</b>&nbsp;&nbsp;<%=((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"") +  "&nbsp;&nbsp;" + ((direccion.getValorEje()!=null)?direccion.getValorEje():"") + "&nbsp;&nbsp;&nbsp;" + ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"") + "&nbsp;&nbsp;" + ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((direccion.getEspecificacion()!=null)?direccion.getEspecificacion():"")%></td>
        </tr>		
	             <%
	             indexDireccion++;
	             }
              	}%>
    </table>
    
    <br>
    


    <%if(folio.getAnotaciones().size()>0){%>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
      <%
      	if(!((Anotacion)folio.getAnotaciones().get(0)).getAnotacionesPadre().isEmpty()){
          	Iterator itPadres = ((Anotacion)folio.getAnotaciones().get(0)).getAnotacionesPadre().iterator();
          	while(itPadres.hasNext()){
          	FolioDerivado folioD = (FolioDerivado)itPadres.next();
          	
          	%>
          <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <%if (folioD!=null && folioD.getPadre()!=null){ %>
              <td class="campositem"><%=folioD.getPadre().getNumRadicacion()%></td>
              <%}%>
          </tr>
          	<%}
      	%>
      	<%} else if(!((Anotacion)folio.getAnotaciones().get(folio.getAnotaciones().size()-1)).getAnotacionesHijos().isEmpty()){
          	Iterator itHijos = ((Anotacion)folio.getAnotaciones().get(folio.getAnotaciones().size())).getAnotacionesHijos().iterator();
          	while(itHijos.hasNext()){
	          	FolioDerivado folioD = (FolioDerivado)itHijos.next();
	          	if(folioD!=null && folioD.getHijo()!=null){%>
          <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td class="campositem"><%=folioD.getHijo().getNumRadicacion()%>&nbsp;</td>
          </tr>
          	<%	}
          	}
      	} else {%>
          <tr>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
          <td class="campositem">No tiene otras matriculas asociadas a este folio</td>
          </tr>
      <%}%>
      </table>
      <%}%>
    <br>


 	<!--Se muestra informaci�n de la anotaci�n-->         
    <table border="0" width="100%" cellpadding="0" cellspacing="2">
			<%if(ocultarAnotaciones.equals("FALSE")){%>
        <tr>
            <td><hr class="linehorizontal"></td>
        </tr>
        <tr>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <td></td>
                <input type="hidden" name="<%=WebKeys.OCULTAR_ANOTACIONES%>" value="TRUE">
	            <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" id="OF5">                            
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF5').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
            </form>
        </tr>
			<%}else{%>
        <tr>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_ANOTACIONES%>" value="FALSE">
	            <input type="hidden" name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" id="OF6">                            
                <td align="right" class="contenido">Haga click para maximizar las anotaciones</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onclick="document.getElementById('OF6').value = document.CERTIFICADO.<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>.value"></td>
            </form>
        </tr>
			<%}%>
    </table>
    <br>


 			<%
			if(ocultarAnotaciones.equals("FALSE")){
			if(folio.getAnotaciones().isEmpty()){%>
		<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
          <tr>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
          <td class="campositem">No tiene anotaciones asociadas a este folio</td>
          </tr>
	      </table>
			<%}
			%>
              <%Iterator itAnotacion = folio.getAnotaciones().iterator();
              	while(itAnotacion.hasNext()){
              	Anotacion anotacion = (Anotacion)itAnotacion.next();%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaci&oacute;n: N&ordm; <%=anotacion.getOrden()%> </td>
        <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="179">Fecha</td>
            <td width="211" class="campositem">
	        <%try {
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(anotacion.getFechaRadicacion());
				fechaHelper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}%>
            </td>
            <td width="146">Radicaci&oacute;n</td>
            <td width="212" class="campositem"><%=anotacion.getNumRadicacion()%>&nbsp;</td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Doc</td>
            <td class="campositem"><%=anotacion.getDocumento().getTipoDocumento().getNombre()%> <%=anotacion.getDocumento().getNumero()%></td>
            <td>Del</td>
            <td class="campositem">
	        <%
	        nombreVereda="";
	        String oficinaDocumento="";	        
	        if(anotacion.getDocumento()!=null){
	        	Documento documento = anotacion.getDocumento();
	        	if(anotacion.getDocumento().getOficinaOrigen()!=null){
	        		oficinaDocumento = anotacion.getDocumento().getOficinaOrigen().getNombre();
	        		if(anotacion.getDocumento().getOficinaOrigen().getVereda()!=null){
	        			if(anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre()!=null){
	        				nombreVereda=anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre();
	        			}
	        		}
	        		nombreVereda = (oficinaDocumento!=null?oficinaDocumento:" ") + " de " + nombreVereda;
	        	}else if(documento.getOficinaInternacional()!=null){
					nombreVereda = documento.getOficinaInternacional();
			  	}else if(documento.getComentario()!=null){
					String comentario = documento.getComentario();
					String comentarioOficina = "&nbsp;";
					String comentarioVereda ="&nbsp;"; 					
					if(comentario.indexOf("-")!=-1){
						java.util.StringTokenizer token = new java.util.StringTokenizer(comentario, "-");
						comentarioVereda = token.nextToken();
						comentarioOficina = token.nextToken();
						
						nombreVereda = comentarioOficina + " de " + comentarioVereda;
					}else{
						nombreVereda = comentario;
					}
			  	}
	        	
	        }
	        
	        try {
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(anotacion.getDocumento().getFecha());
				fechaHelper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}%>
            de 
            <%= nombreVereda%>&nbsp;
            </td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Valor</td>
            <td class="campositem"><%=(anotacion.getValor()!=0?java.text.NumberFormat.getInstance().format(anotacion.getValor()):"&nbsp;")%>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Especificaci&oacute;n</td>
            <%
            if(anotacion.getEspecificacion()!=null){                        
            %>
            <td class="campositem"><%= anotacion.getEspecificacion()%>&nbsp;</td>            
            <%}else{%>
            <td class="campositem"><%= anotacion.getNaturalezaJuridica()!=null?anotacion.getNaturalezaJuridica().getNombre():""%>&nbsp;</td>
            <%
            }
            %>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td class="campostitle"><p>Personas que intervienen en el acto (La X indica a la persona que figura como titular de derechos reales de dominio, I-Titular de dominio incompleto)</p></td>
            <td>&nbsp;</td>
        </tr>
                <%if(anotacion.getAnotacionesCiudadanos().isEmpty()){%>
        <tr>
            <td>&nbsp;</td>
            <td>No tiene ciudadanos asociados a esta anotacion</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
                <%} else {
                Iterator itCiudadanos = anotacion.getAnotacionesCiudadanos().iterator();
                while(itCiudadanos.hasNext()){
                	AnotacionCiudadano anCiudadano = (AnotacionCiudadano) itCiudadanos.next();%>
    <tr>
    <td><%=anCiudadano.getRolPersona()%></td>
    <td class="campositem"><%=anCiudadano.getCiudadano().getInfoCiudadano()%> &nbsp;</td>
    <td class="campositem"><span class="titresaltados"><%=anCiudadano.getStringMarcaPropietario()%></span>&nbsp;</td>
    </tr>
				<%}%>
    </table>
				<%}%>
			<%}%> 
			<%} else {%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaciones</td>
            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
			<%}%>	
    <hr class="linehorizontal">
                           
    
    
		<%}%>
      
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        
        
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          

 			<table width="100%" class="camposform">
            <tr>
				<form name="form1" method="post" action="certificado.do">                
				<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ANT_SISTEMA_REVISION_REANALIZAR%>">
               <!-- <td width="150" align="center"><input name="imageField3" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_reanalizar.gif" width="150" height="21" border="0"></td>-->
                </form>
				<td width="140"><a href="turnos.view"><img name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"   width="139" height="21" border="0"></a></td>
              <td align="center">&nbsp;</td>
            </tr>
          </table>
          
          </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Generar Oficio de Pertenencia </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
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
            
        

            <form action="certificado.do" name="CERTIFICADO" id="CERTIFICADO" method="post">
            <input type="hidden" name="ACCION" value="AGREGAR">
            
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Certificaci&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align="left"><textarea name="<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA%>" cols="150" rows="20" class="camposformtext"><%=plantilla!=null?plantilla:""%></textarea> </td>
                </tr>
              </table>
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                  	<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.certificado.AWCertificado.OFICIO_PERTENENCIA_CONFIRMAR%>')"> 
                    <img
                    	src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" 
                    	width="139" 
                    	height="21" 
                    	border="0">
                    </a>
                  </td>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </form>
            
          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      <%
		try{
		    //SE USA LA SIGUIENTE L�NEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CU�L SE LE DESEA GUARDAR LA INFORMACI�N QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOC� SE PERDER� LA INFORMACI�N. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CERTIFICADO");
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
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>

      </body>