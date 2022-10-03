<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado" %>
<% 
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
	Calendar calendar = Calendar.getInstance();
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
         <table border="0" cellpadding="0" cellspacing="0">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
            <form action="microfilmado.view" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
					<%}else{%>
            <form action="microfilmado.view" method="post" type="submit">
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
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

    </table>
        <%if(ocultarFolio.equals("FALSE")){%>
          
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
        <td class="campositem"><%= folio.getZonaRegistral().getCirculo().getNombre()%></td>
        <td>Depto:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre()%></td>
        <td>Municipio:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getNombre()%></td>
        <td>Vereda:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getNombre()%></td>
        </tr>
        <tr>
        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Fecha Apertura: </td>
                  <%calendar.setTime(folio.getFechaApertura());%>
        <td class="campositem"><%=calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)%></td>
        <td>Radicacion:</td>
	    <%/*if(((TurnoFolio)folio.getTurnosFolios().get(0)).getTurno().getIdWorkflow()!=null){*/%>
	    <td class="campositem"><%/*= ((TurnoFolio)folio.getTurnosFolios().get(0)).getTurno().getIdWorkflow()*/%></td>
	    <%/*} else {*/%>
	    <td class="campositem"><%/*=folio.getRadicacion()*/%></td>
	    <%/*}*/%>
        <td>Con:</td>
        <%if(folio.getDocumento() != null){%>
        <td class="campositem"><%= folio.getDocumento().getTipoDocumento().getNombre()%> de: <%=folio.getDocumento().getNumero()%></td>
        <%} else {%>
        <td class="campositem">No hay documento asociado</td>
        <%}%>
        <td>Cod Catastral : </td>
        <td class="campositem"><%= folio.getCodCatastral()%></td>
        </tr>
    </table>
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
            <td class="campositem"><%=folio.getLindero()%></td>
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
            <td class="campositem"><p><%=folio.getComplementacion().getComplementacion()%></p></td>
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
            <td class="campositem"><%= folio.getTipoPredio().getNombre()%></td>
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
        <td class="campositem"><b><%=""+indexDireccion%>&nbsp;&nbsp;-</b>&nbsp;&nbsp;<%=direccion.toString()%></td>
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
              <td class="campositem"><%=folioD.getPadre().getNumRadicacion()%></td>
          </tr>
          	<%}
      	%>
      	<%} else if(!((Anotacion)folio.getAnotaciones().get(folio.getAnotaciones().size())).getAnotacionesHijos().isEmpty()){
          	Iterator itHijos = ((Anotacion)folio.getAnotaciones().get(folio.getAnotaciones().size())).getAnotacionesHijos().iterator();
          	while(itHijos.hasNext()){
	          	FolioDerivado folioD = (FolioDerivado)itHijos.next();%>
          <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td class="campositem"><%=folioD.getHijo().getNumRadicacion()%></td>
          </tr>
          	<%}
      	} else {%>
          <tr>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
          <td class="campositem">No tiene otras matriculas asociadas a este folio</td>
          </tr>
      <%}%>
      </table>
      <%}%>
    <br>
    
    <table border="0" width="100%" cellpadding="0" cellspacing="2">
			<%if(ocultarAnotaciones.equals("FALSE")){%>
        <tr>
            <td><hr class="linehorizontal"></td>
        </tr>
        <tr>
            <form action="microfilmado.view" method="post" type="submit">
                <td></td>
            <input type="hidden" name="<%=WebKeys.OCULTAR_ANOTACIONES%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
        </tr>
			<%}else{%>
        <tr>
            <form action="microfilmado.view" method="post" type="submit">
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
            <%calendar.setTime(anotacion.getFechaRadicacion());%>
            <td width="211" class="campositem"><%= calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)%></td>
            <td width="146">Radicaci&oacute;n</td>
            <td width="212" class="campositem"><%=anotacion.getNumRadicacion()%></td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Doc</td>
            <td class="campositem"><%=anotacion.getDocumento().getTipoDocumento().getNombre()%> <%=anotacion.getDocumento().getNumero()%></td>
            <td>Del</td>
            <%calendar.setTime(anotacion.getDocumento().getFecha());%>
            <%
            if(anotacion.getDocumento()!=null && anotacion.getDocumento().getOficinaOrigen()!=null){            
            %>
            <td class="campositem"><%= calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)%> <%= anotacion.getDocumento().getOficinaOrigen().getNombre()%> <%= anotacion.getDocumento().getOficinaOrigen().getNumero()%> de <%= anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre()%></td>
            <%
            }else if(anotacion.getDocumento()!=null && anotacion.getDocumento().getOficinaInternacional()!=null){            
            %>
            <td class="campositem"><%= calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)%> <%= anotacion.getDocumento().getOficinaInternacional()%> </td>            
            <%}else if(anotacion.getDocumento().getComentario()!=null){            
            		Documento documento = anotacion.getDocumento();	
					String comentario = documento.getComentario();
					String comentarioOficina = "&nbsp;";
					String comentarioVereda ="&nbsp;"; 					
					if(comentario.indexOf("-")!=-1){
						java.util.StringTokenizer token = new java.util.StringTokenizer(comentario, "-");
						comentarioVereda = token.nextToken();
						comentarioOficina = token.nextToken();
						
						comentario = comentarioOficina + " de " + comentarioVereda;
					}
            %>            
            <td class="campositem"><%= calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.YEAR)%> <%= comentario%> </td>
            <%
            }
            %>                         
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Valor</td>
            <td class="campositem"><%=(anotacion.getValor()!=0?java.text.NumberFormat.getInstance().format(anotacion.getValor()):"&nbsp;")%></td>
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
              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	          <form name="ENTREGA" method="post" action="certificado.do">
	          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ANT_SISTEMA_MICROFILMADO_CONFIRMAR%>">
	          <td width="150" align="center"><p>
	          <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" width="139" height="21" border="0">
	          </p></td>
	          </form>
	          <form name="ENTREGA" method="post" action="certificado.do">
	          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCertificado.ANT_SISTEMA_MICROFILMADO_NEGAR%>">            
              <td width="150" align="center"><p>
              <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" width="139" height="21" border="0">
              </p></td>
              </form>
              <form name="form1" method="post" action="turnos.view">
              <td width="150" align="center"><input name="imageField3" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></td>
              </form>
              <td>&nbsp;</td>
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