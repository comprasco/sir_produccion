
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuesta"%>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoTarifa" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoCertificado" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%  
	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper(); 
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper(); 
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);	
	Circulo circuloSes = (Circulo)session.getAttribute(WebKeys.CIRCULO);
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}
	
	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);	
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}
	
		String ocultarAnotaciones = request.getParameter(WebKeys.OCULTAR_ANOTACIONES);	
	if(ocultarAnotaciones == null){
		ocultarAnotaciones = (String)session.getAttribute(WebKeys.OCULTAR_ANOTACIONES);
		if(ocultarAnotaciones==null){
			ocultarAnotaciones = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,ocultarAnotaciones);
	}
	
	
	String printspecial = request.getParameter(AWCertificado.PRINTESPECIAL);	
	if(printspecial == null){
		printspecial = (String)session.getAttribute(AWCertificado.PRINTESPECIAL);
		if(printspecial==null){
			printspecial = "FALSE";
		}
	} else {
		session.setAttribute(AWCertificado.PRINTESPECIAL,printspecial);
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
	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
	boolean exento=false;
	String tipoTarifa = liquidacion.getTipoTarifa();
	if(tipoTarifa!=null && tipoTarifa.equals(CTipoTarifa.EXENTO)){
		exento=true;
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
            <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
        </form>
						
					<%}else{%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
            <td width="170" class="contenido">Haga click para maximizar</td>
            <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
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
        <td class="campositem"><%=((Folio)solFolio.getFolio()).getIdMatricula()%></td>
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
    <%
      String tipoDoc = ciudadano.getTipoDoc();
      if(tipoDoc==null)
        tipoDoc="&nbsp;";
        
      String documento = ciudadano.getDocumento();
      if(documento==null)
        documento="&nbsp;";
        
      String apellido1 = ciudadano.getApellido1();
      if(apellido1==null)
        apellido1="&nbsp;";
 
      String apellido2 = ciudadano.getApellido2();
      if(apellido2==null)
        apellido2="&nbsp;";
 
      String nombre = ciudadano.getNombre();
      if(nombre==null)
        nombre="&nbsp;";
        
      String numero = ciudadano.getDocumento();
      if(numero==null)
        numero="&nbsp;";
    %>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr> 
            <td width="179">Tipo de Identificaci&oacute;n</td>
            <td width="211" class="campositem"><%=tipoDoc%>&nbsp;</td>
            <td width="146">N&uacute;mero</td>
            <td width="212" class="campositem"><%=numero%>&nbsp;</td>
        </tr>
        <tr> 
            <td>Primer Apellido</td>
            <td class="campositem"><%=apellido1%>&nbsp;</td>
            <td>Segundo Apellido</td>
            <td class="campositem"><%=apellido2%>&nbsp;</td>
        </tr>
        <tr> 
            <td>Nombre</td>
            <td class="campositem"><%=nombre%></td>
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
            <td width="445" class="campositem"><%=liquidacion.getValor()%>&nbsp;</td>
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

        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
<!--          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio</td>   -->
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones Entrega</td>              
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
<!--
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table border="0" align="right" cellpadding="0" cellspacing="2">
              <tr>
					<%if(ocultarFolio.equals("FALSE")){%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
					<%}else{%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>-->
    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
    

    </table>
        <%if(ocultarFolio.equals("FALSE")){%>
          
    <br>
    <!--
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub"><p>Informacion Apertura de Folio</p></td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
    <%
      ZonaRegistral zona=null;
      if(folio!=null){
      	zona = folio.getZonaRegistral(); 
      }
 
      Circulo circulo=null;
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
    
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
	        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
	        <td>Circulo Registral:</td>
	        <td class="campositem"><%=nombreCirculo%>&nbsp;</td>
	        <td>Depto:</td>
	        <td class="campositem"><%=nombreDepto%>&nbsp;</td>
	        <td>Municipio:</td>
	        <td class="campositem"><%=nombreMunicipio%>&nbsp;</td>
	        <td>Vereda:</td>
	        <td class="campositem"><%=nombreVereda%>&nbsp;</td>
        </tr>
        <tr>
        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
	        <td>Fecha Apertura: </td>
	        <td class="campositem">&nbsp;
		    <%/*
		        try {
				  fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				  fechaHelper.setDate(folio.getFechaApertura());
				  fechaHelper.render(request,out);
				}
				catch(HelperException re){
				  out.println("ERROR " + re.getMessage());
				
			}*/%>
	        </td>
	        <td>Radicacion:</td>
		    <%/*if(((TurnoFolio)folio.getTurnosFolios().get(0)).getTurno().getIdWorkflow()!=null){*/%>
		    <td class="campositem">&nbsp;<%/*= ((TurnoFolio)folio.getTurnosFolios().get(0)).getTurno().getIdWorkflow()*/%></td>
		    <%/*} else {*/%>
		    <td class="campositem">&nbsp;<%/*=folio.getRadicacion()*/%></td>
		    <%/*}*/%>
	        <td>Con:</td>
	        <% 
	          Documento folioDocumento=null;
	          if(folio!=null){
	          	folioDocumento = folio.getDocumento(); 
	          }
	          TipoDocumento tipoDocumento =null;
	          String numeroDocumento ="&nbsp;";
	          String nombreTipoDocumento ="&nbsp;";
	          String codigoCatastral =null;
	          
	          
	            	           
	          if(folioDocumento!= null)
	          {
	            tipoDocumento = folioDocumento.getTipoDocumento();
	            if(tipoDocumento!=null)
	            {
	              nombreTipoDocumento=tipoDocumento.getNombre();
	            } 
	            numeroDocumento=folioDocumento.getNumero();
	          }
	          
	          if(folio!=null){
	          	codigoCatastral=folio.getCodCatastral();
	          }
     		  if (codigoCatastral==null)
	            codigoCatastral="&nbsp;";
	               
	          %>
	          
	          <%
	            if (folioDocumento!=null)
	            {
	          %>
	              <td class="campositem"><%=nombreTipoDocumento%> de: <%=numeroDocumento%>&nbsp;</td>
	          <%} 
	            else 
	            {
	          %>
	             <td class="campositem">No hay documento asociado</td>
	         <%
	           }
	           %>
	        <td>Cod Catastral : </td>
	        <td class="campositem">&nbsp;<%=codigoCatastral%></td>
        </tr>
    </table>
    
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>ESTADO DEL FOLIO </td>
        <td class="campositem"><strong><% 
        		  EstadoFolio estado=null;
        		  if(folio!=null){
                  	estado=folio.getEstado();
                  }
                  String nombreEstado="El folio no tiene estado asociado";
             
                  if (estado!=null)
                    nombreEstado= estado.getNombre();
                    
                  if (nombreEstado==null)
                    nombreEstado="&nbsp;";
             
                  out.write("nombreEstado="+nombreEstado);
                  %></strong></td>
        </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    
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
            <%
              String lindero="&nbsp;";
              if(folio!=null){
              	if (folio.getLindero()!=null)
                	lindero=folio.getLindero();
              }
              
            %>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td class="campositem"><%=lindero%>&nbsp;</td>
        </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    
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
            <%
              String complementacion ="&nbsp;";
              Complementacion comp=null;
              if(folio!=null){
              	comp = folio.getComplementacion();
              }
              if (comp!=null)
              {
                complementacion=comp.getComplementacion();
                if(complementacion==null)
                  complementacion ="&nbsp;";
              }
            %>
            <td class="campositem"><p><%=complementacion%></p></td>
        </tr>
        <br>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
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
            <%
              TipoPredio tipoPred=null;
              if(folio!=null){
              	tipoPred=folio.getTipoPredio();
              }
              String tipoPredio="&nbsp;";
              if (tipoPred!=null)
              {
                tipoPredio= tipoPred.getNombre();
                if(tipoPredio==null)
                  tipoPredio="&nbsp;"; 
              }
            %>
            <td class="campositem"><%=tipoPredio%></td>
        </tr>
    </table>
    <table width="100%" class="camposform">
                  
              <% 
              List direcciones=null;
              if(folio!=null){
                 direcciones = folio.getDirecciones();
              }
              if (direcciones==null)
                direcciones= new Vector();
              if(folio!=null){
              	if(folio.getDirecciones().isEmpty()){%>
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td class="campositem">No tiene direcciones registradas para este folio</td>
        </tr>              	              
              	<%} else {
              	 	if(folio!=null){
              	 		if(folio.getDirecciones()!=null){
              	 			Iterator itDirecciones = folio.getDirecciones().iterator();
							 int indexDireccion = 1;              	 			
	             			while(itDirecciones.hasNext()){
	              				Direccion direccion = (Direccion) itDirecciones.next();%>
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td class="campositem"><b><%=""+indexDireccion%>&nbsp;&nbsp;-</b>&nbsp;&nbsp;<%=((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"") +  "&nbsp;&nbsp;" + ((direccion.getValorEje()!=null)?direccion.getValorEje():"") + "&nbsp;&nbsp;&nbsp;" + ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"") + "&nbsp;&nbsp;" + ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((direccion.getEspecificacion()!=null)?direccion.getEspecificacion():"")%></td>
        </tr>		
	             <%			indexDireccion++;
	             			}
              			}
              		}
              	}
              	 }%>
    </table>
    
    <br>
    
    
  <%
  	List anotaciones=null;
	if(folio!=null){
    	anotaciones = folio.getAnotaciones();
    }
    if (anotaciones==null){
    }
    else{
   
    int index=anotaciones.size()-1;
    if(anotaciones.size()>0){%>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
      <%
        Anotacion anota = (Anotacion)anotaciones.get(0);
        List anotacionesPadre = anota.getAnotacionesPadre();
        if (anotacionesPadre==null)
          anotacionesPadre = new Vector();
        
        Anotacion anota2 = (Anotacion)anotaciones.get(index);
        List anotacionesHijas = anota2.getAnotacionesHijos();
        if (anotacionesHijas==null)
          anotacionesHijas = new Vector();
    
        
      	if(!anotacionesPadre.isEmpty())
      	{
          	Iterator itPadres = anotacionesPadre.iterator();
          	while( itPadres.hasNext() )
          	{
          	  FolioDerivado folioD = (FolioDerivado)itPadres.next();
          	  Anotacion anotaPadre=null;
          	  if(folioD!=null){
          	  	anotaPadre=folioD.getPadre();
          	  }
          	  String numRad = "&nbsp;";
 
          	  if (anotaPadre!=null)
          	     numRad= anotaPadre.getNumRadicacion();
          	  

          	%>
          <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td class="campositem"><%=numRad%></td>
          </tr>
          	<%}
      	%>
      	<%
      	} 
      	else if(!anotacionesHijas.isEmpty())
      	{
          	Iterator itHijos = anotacionesHijas.iterator();
          	while(itHijos.hasNext())
          	{
	          	FolioDerivado folioD = (FolioDerivado)itHijos.next();
	          	Anotacion anotaHija=null;
	          	if(folioD!=null){
	            	anotaHija=folioD.getHijo();
	            }
          	    String numRad = "&nbsp;";
 
          	    if (anotaHija!=null)
          	     numRad= anotaHija.getNumRadicacion();     
	         %>
          <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td class="campositem"><%=numRad%></td>
          </tr>
          	<%}
      	} else {%>
          <tr>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
          <td class="campositem">No tiene otras matriculas asociadas a este folio</td>
          </tr>
      <%}
      }%>
      </table>
      <%}%>
    <br>
    
    <table border="0" width="100%" cellpadding="0" cellspacing="2">
			<%if(ocultarAnotaciones.equals("FALSE")){%>
        <tr>
            <td><hr class="linehorizontal"></td>
        </tr>
        <tr>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <td></td>
                <input type="hidden" name="<%=WebKeys.OCULTAR_ANOTACIONES%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
        </tr>
			<%}else{%>
        <tr>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_ANOTACIONES%>" value="FALSE">
                <td align="right" class="contenido">Haga click para maximizar las anotaciones</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
        </tr>
			<%}%>
    </table>
    <br>
			<%
			if(ocultarAnotaciones.equals("FALSE")){
			if(anotaciones!=null){
				if(anotaciones.isEmpty()){%>
		<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
          <tr>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
          <td class="campositem">No tiene anotaciones asociadas a este folio</td>
          </tr>
	      </table>
			<%	}
			%>
              <%Iterator itAnotacion = folio.getAnotaciones().iterator();
              	while(itAnotacion.hasNext()){
              		Anotacion anotacion = (Anotacion)itAnotacion.next();
              		if(anotacion!=null){%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    
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
	        <%try 
	          {
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(anotacion.getFechaRadicacion());
				fechaHelper.render(request,out);
			  }
			  catch(HelperException re)
			  {
				out.println("ERROR " + re.getMessage());
				re.printStackTrace();
			  }
			%>
            </td>
            <td width="146">Radicaci&oacute;n</td>
            <%
              		String numeRadAnota = "&nbsp;";
              		if (anotacion.getNumRadicacion()!=null)
                		numeRadAnota =anotacion.getNumRadicacion();
            %>
            <td width="212" class="campositem"><%=numeRadAnota%>&nbsp;</td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Doc</td>
			<%
	            String nombreTipoDocAnota="&nbsp;";
	            String numeroDocAnota="&nbsp;";
	          
	          
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
	             
	              Documento docAnota = anotacion.getDocumento();
	              OficinaOrigen ofiAnota = null;
	              Vereda vereAnota=null;
	              if (docAnota!=null)
	              {
	                TipoDocumento tipoDocAnota = docAnota.getTipoDocumento();
	                if (tipoDocAnota!=null)
	                {
	                  nombreTipoDocAnota=tipoDocAnota.getNombre();
	                  if (nombreTipoDocAnota==null)
	                    nombreTipoDocAnota="&nbsp;";
	                }
	                numeroDocAnota = docAnota.getNumero();
	                if (numeroDocAnota==null)
	                    numeroDocAnota="&nbsp;";
	              }
                
            %>             
            <td class="campositem"><%=nombreTipoDocAnota%> <%=numeroDocAnota%>&nbsp;</td>
            <td>Del</td>
            <td class="campositem">
	        <%
	          try {
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(anotacion.getDocumento().getFecha());
				 fechaHelper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}%>
            de
            <%= nombreVereda%>
            </td>
            </td>
        </tr>
        
        <%
          String natuJurAnota = "&nbsp;";
          NaturalezaJuridica natuJur= anotacion.getNaturalezaJuridica();
          if (natuJur!=null)
          {
            natuJurAnota=natuJur.getNombre();
            if (natuJurAnota==null)
              natuJurAnota="&nbsp;";
          }  
        %>
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
            <td class="campositem"><%=natuJurAnota%>&nbsp;</td>
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
                <%
                List anotacionesCiudadanos = anotacion.getAnotacionesCiudadanos();
                if (anotacionesCiudadanos==null)
                  anotacionesCiudadanos=new Vector();
                  
                if( anotacionesCiudadanos.isEmpty() )
                {%>
        <tr>
            <td>&nbsp;</td>
            <td>No tiene ciudadanos asociados a esta anotacion</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
                <%} else {
                Iterator itCiudadanos = anotacionesCiudadanos.iterator();
                while(itCiudadanos.hasNext()){
                	AnotacionCiudadano anCiudadano = (AnotacionCiudadano) itCiudadanos.next();%>
    <tr>
    <td><%=anCiudadano.getRolPersona()%></td>
    <td class="campositem"><%=anCiudadano.getCiudadano().getInfoCiudadano()%> </td>
    <td class="campositem"><span class="titresaltados"><%=anCiudadano.getStringMarcaPropietario()%></span>&nbsp;</td>
    </tr>
				<%}%>
    </table>
				<%}%>
			<%	}
				}
			  }%> 
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
			-->    
		<%}%>
      
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        
        
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          

		<%
		  	if(!printspecial.equals(AWCertificado.PRINTESPECIAL)){%>
          <table width="100%" class="camposform">
              <tr>
              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
              <form name="ENTREGA" method="post" action="certificado.do">
              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ENTREGAR%>">
              <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_marcaentregado.gif" width="139" height="21" border="0">
              </td>
              </form>
        
			  <%
			  if(!exento && !turno.getUltimaRespuesta().equals(CRespuesta.NEGAR)){
		/*
                 * @author      :   Julio Alc�zar Rivas
                 * @change      :   Se elimina la opcion que los certificados sean impresos en el proceso de Entrega de Certificados
                                    Comentariando este codigo.
                 * Caso Mantis  :   02359
                 */
                //	  int i = ((Integer)session.getAttribute(WebKeys.NUMERO_MAX_IMPRESIONES)).intValue();
		//	  	if(solicitud.getNumImpresiones() <  i )
                          //{%>
	        <!--      <form name="ENTREGA" method="post" action="certificado.do">
	              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.IMPRIMIR%>">
	              <td width="150" align="center"><p>
	              <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_reimpresion.gif" width="139" height="21" border="0">
	              </p></td>
	              </form>  !-->
              <%//} else {%>
	         <!--      <td width="150" align="center"><p>
	              <image name="imageField52" src="<%=request.getContextPath()%>/jsp/images/btn_imprimir_off.gif" width="139" height="21" border="0">
	              </p></td> !-->
              <%//}
              	boolean isCerMasivo = false;
              	
              	try {
              		if ( solicitud.getSolicitudesPadres()!=null &&  !solicitud.getSolicitudesPadres().isEmpty() 
              			&& ((SolicitudAsociada)solicitud.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivo) {
	              		isCerMasivo = true;
              		} 
              	} catch (Exception ee) {
              		ee.printStackTrace();
              	}
              	
              	if ((!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID) || 
				    (!solicitud.getSolicitudesPadres().isEmpty() && !isCerMasivo) ||
				    ((SolicitudFolio)solicitud.getSolicitudFolios().get(0)).getFolio().getLastIdAnotacion()>circuloSes.getMayorExtension())
				    && !turno.getUltimaRespuesta().equals(CRespuesta.NEGAR)){%>
              
	<!--    /*
                 * @author      :   Julio Alc�zar Rivas
                 * @change      :   Se elimina la opcion que los certificados sean reimpresos en el proceso de Entrega de Certificados
                                    Comentariando este codigo.
                 * Caso Mantis  :   02359
                 */
			<form name="ENTREGA" method="post" action="certificado.do">
           
                <input type="hidden" name="<%=AWCertificado.PRINTESPECIAL%>" value="<%=AWCertificado.PRINTESPECIAL%>">
                <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.IMPRIMIR_ESPECIAL%>">
                <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">                              
                <td width="150" align="center"><p>
                <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion_especial.gif" width="180" height="21" border="0">
                </p></td>
              </form>
				
         !-->
              <%}
			}%>
              
              <form name="form1" method="post" action="turnos.view">
              <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
	          <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">    
			  <input type="hidden" name="<%=AWCertificado.PRINTESPECIAL%>" value="">
              <td width="150" align="center"><input name="imageField3" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></td>
              </form>
              <td>&nbsp;</td>
              </tr>

          </table>
		<%	}
		  %>
          
          </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      <%if(printspecial.equals(AWCertificado.PRINTESPECIAL)){%>
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Enviar a Reimpresion </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                  <form name="ENTREGA" method="post" action="certificado.do">              
              
              <br>
          <table width="100%" class="camposform">
              <tr>
	              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			      <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.IMPRIMIR_ESPECIAL%>">
    	          <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif" width="139" height="21" border="0"></td>
    	          </form>
    	          <form name="ENTREGA" method="post" action="entrega.certificados.view">
                  <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
	              <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">    
				  <input type="hidden" name="<%=AWCertificado.PRINTESPECIAL%>" value="">
	              <td width="150" align="center"><input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="150" height="21" border="0"></td>
	              </form>
        	      <td>&nbsp;</td>
            	  </tr>
          </table>
          </form></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      <%}%>
      
             <%
		try{
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