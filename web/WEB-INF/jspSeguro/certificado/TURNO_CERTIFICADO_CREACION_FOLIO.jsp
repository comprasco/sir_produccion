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
			ocultarAnotaciones = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,ocultarAnotaciones);
	}
	
	NotasInformativasHelper helper = new NotasInformativasHelper();
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();
	Liquidacion liquidacion = null;
	List liquidaciones = solicitud.getLiquidaciones();
	for(int i=0;i<liquidaciones.size();i++){
		double id = new Double(((Liquidacion)liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
		if(id==solicitud.getLastIdLiquidacion()){
			liquidacion = (Liquidacion)liquidaciones.get(i);
		}
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();
	List solicitudFolios  = solicitud.getSolicitudFolios();
	Iterator itSolicitudFolios = solicitudFolios.iterator();
	
	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
	
	if(folio==null){
		ocultarFolio = "TRUE";
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

  <!--Descomentarear las siguientes líneas para el caso que se necesite dejar el tipo de certificado  en Antiguo sistema de Certificados-->
  <%if(solicitud instanceof SolicitudCertificado){
	  SolicitudCertificado solCert = (SolicitudCertificado)solicitud;
	  LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado)liquidacion;
  %>
  	
   <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr> 
            <td>Tipo de Certificado</td>
            <td>Cantidad</td	>
        </tr>
        <tr> 
            <td class="campositem"><%=(liqCert!=null&&liqCert.getTipoCertificado()!=null)?liqCert.getTipoCertificado().getNombre():""%>&nbsp;</td>
            <td class="campositem"><%=""+solCert.getNumeroCertificados()%>&nbsp;</td>
        </tr>
    </table>
	<%}%>

			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1"  src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Turno Anterior</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>N&uacute;mero del Turno Anterior</td>
            <td class="campositem">&nbsp;
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
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos antiguo sistema </td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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
    </tr>
    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

    </table>
    
        <%if(ocultarFolio.equals("FALSE")){%>
  
 	<!--Se muestra la información del folio-->         
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

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Circulo Registral:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getCirculo().getNombre()%>&nbsp;</td>
        <td>Depto:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre()%>&nbsp;</td>
        <td>Municipio:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getNombre()%>&nbsp;</td>
        <td>Vereda:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getNombre()%>&nbsp;</td>
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
        <%
        String idWF = null;
		//	String idWF =((TurnoFolio)folio.getTurnosFolios().get(0)).getTurno().getIdWorkflow();
        %>
	    <td class="campositem"><%=idWF!=null?idWF:folio.getRadicacion()%>&nbsp;</td>
        <td>Con:</td>
        <%if(folio.getDocumento() != null){%>
<!--        <td class="campositem"><%= folio.getDocumento().getTipoDocumento().getNombre()%> de: <%=folio.getDocumento().getNumero()%>&nbsp;</td>        -->
        <td class="campositem"><%= folio.getDocumento().getTipoDocumento().getNombre()%>&nbsp;</td>
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
        <td class="campositem">&nbsp;<strong><% 
                  if(folio.getEstado() != null){
                  		out.write(folio.getEstado().getNombre());
                  } else {
                  		out.write("El folio no tiene estado asociado");
                  }
                  %></strong></td>
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
            <%if (folio.getComplementacion()!=null){
                %><td class="campositem"><p><%=folio.getComplementacion().getComplementacion()%></p></td>
            <%} else{
                %><td class="campositem"><p>NO HAY COMPLEMENTACION</p></td>
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
            <td class="campositem"><%= folio.getTipoPredio().getNombre()%>&nbsp;</td>
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
              <td class="campositem"><%=folioD.getHijo().getNumRadicacion()%></td>
          </tr>
          	<%	}
          	}
      	} %><%else {
      	
      	
      	%>
























      <%}%>
      </table>
      <%}%>
    <br>


 	<!--Se muestra información de la anotación-->         
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
            
            <%
            String idwf = turno.getIdWorkflow();
            if(anotacion.getNumRadicacion()!=null){
            %>            
            <td width="212" class="campositem"><%=anotacion.getNumRadicacion()!=null?anotacion.getNumRadicacion():""%>&nbsp;</td>
            <%}else{%>
            <td width="212" class="campositem">&nbsp;</td>
            <%
            }%>            
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Doc</td>
            <td class="campositem"><%=anotacion.getDocumento().getTipoDocumento().getNombre()%> <%=anotacion.getDocumento().getNumero()%>&nbsp;</td>
            <td>Del</td>
            <td class="campositem">
	        <%
	        String nombreVereda="";
	        if(anotacion.getDocumento()!=null){
	        	Documento documento = anotacion.getDocumento();	        	        
	        	if(anotacion.getDocumento().getOficinaOrigen()!=null){
	        		if(anotacion.getDocumento().getOficinaOrigen().getVereda()!=null){
	        			if(anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre()!=null){
	        				nombreVereda=anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre();
	        			}
	        		}
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
            <%= nombreVereda%>
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
    
    





	<%
	if(anotacion.getDatosAntiguoSistema()!=null){
		DatosAntiguoSistema das = anotacion.getDatosAntiguoSistema();
	%>


<table width="100%" class="camposform">
	<tr>
		<td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td width="25%" class="titulotbcentral" align="left">Libro</td>
		<td width="28%">&nbsp;</td>
		<td width="22%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
		<tr>
	<td>&nbsp;</td>    
	<td>Tipo</td>
	<td class="campositem">
		<%=das.getLibroTipo()!=null?das.getLibroTipo():""%>&nbsp;
		</td>
	<td>N&uacute;mero-Letra</td>
	<td class="campositem">
		<%=das.getLibroNumero()!=null?das.getLibroNumero():""%>&nbsp;
		</td>
	</tr>
		<tr>
	<td>&nbsp;</td>
	<td>Pagina</td>
	<td class="campositem">
		<%=das.getLibroPagina()!=null?das.getLibroPagina():""%>&nbsp;
	</td>
		<td>A&ntilde;o</td>
	<td class="campositem">
		<%=das.getLibroAnio()!=null?das.getLibroAnio():""%>&nbsp;
	</td>
	</tr>
	</table>

	<table width="100%" class="camposform">
	<tr> 
	<td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
	<td width="25%" class="titulotbcentral" align="left">Tomo</td>
	<td width="28%">&nbsp;</td>
	<td width="22%">&nbsp;</td>
	<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	<td>N&uacute;mero</td>
	<td class="campositem">
		<%=das.getTomoNumero()!=null?das.getTomoNumero():""%>&nbsp;
	</td>
	<td>Pagina</td>
	<td class="campositem">
		<%=das.getTomoPagina()!=null?das.getTomoPagina():""%>&nbsp;
	</td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td>Municipio</td>
	<td class="campositem">
		<%=das.getTomoMunicipio()!=null?das.getTomoMunicipio():""%>&nbsp;
	</td>
		<td>A&ntilde;o</td>
	<td class="campositem">
		<%=das.getTomoAnio()!=null?das.getTomoAnio():""%>&nbsp;
	</td>
	</tr>
	</table>


	<%
	}
	%>



    
    
    
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
    <td class="campositem"><%=anCiudadano.getCiudadano().getInfoCiudadano()%> </td>
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
				<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ANT_SISTEMA_CREACION_HACER_DEFINITIVO_FOLIO%>">				
				<td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aprobar_guardar.gif" width="180" height="21" border="0"></td>
              	</form>

				<form name="form1" method="post" action="certificado.do">
				<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ANT_SISTEMA_CREACION_REANALIZAR%>">				
				<td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_regresar_hoja_ruta.gif" width="180" height="21" border="0"></td>
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