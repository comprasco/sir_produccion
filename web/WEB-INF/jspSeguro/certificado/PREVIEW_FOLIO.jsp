<%@page import="gov.sir.core.web.*" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="org.auriga.core.web.*"%>
<% 

String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
vistaAnterior = vistaAnterior !=null 
	? "javascript:window.location.href='"+vistaAnterior+"';" 
	: "javascript:history.back();";

	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
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
		if (ocultarAnotaciones.equals("FALSE")){
		   ocultarFolio = "FALSE";
		}
	}
	
	Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Documento sin t&iacute;tulo</title>
    
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .Estilo2 {font-size: 11px; color: #3A414E; text-decoration: none; background-color: #FFFFFF; border-top: 1px solid #5D687D; border-right: 1px solid #5D687D; border-bottom: 1px solid #5D687D; border-left: 5px solid #6A7891; font-family: Verdana, Arial, Helvetica, sans-serif;}
        -->
    </style>
</head>

<body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td width="12">&nbsp;</td>
        <td width="12"><img name="tabla_error_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif">
        <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_vprevia.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Información hoja de ruta</td>
        <td width="14"><img name="tabla_error_r1_c4" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
    </tr>
    </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
    </tr>

    <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td align="center" class="tdtablaanexa02">
    
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
			<%
			if(ocultarFolio.equals("FALSE"))
			{
			  //session.setAttribute(WebKeys.OCULTAR_FOLIO,"TRUE");
			%>		   
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>			
			<%
			}
			else
			{
			%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
		    <%
		     session.setAttribute(WebKeys.OCULTAR_FOLIO,"FALSE");
		    
		    }
		    %>
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
      
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub"><p>Información Hoja de ruta</p></td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        
        
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Circulo Registral:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getCirculo().getNombre() != null ? folio.getZonaRegistral().getCirculo().getNombre() : "&nbsp;"%>&nbsp;</td>
        <td>Depto:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre() != null ? folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre(): folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getIdDepartamento()%>&nbsp;</td>
        <td>Municipio:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getMunicipio().getNombre() != null ? folio.getZonaRegistral().getVereda().getMunicipio().getNombre() : folio.getZonaRegistral().getVereda().getMunicipio().getIdMunicipio()%>&nbsp;</td>
        <td>Vereda:</td>
        <td class="campositem"><%= folio.getZonaRegistral().getVereda().getNombre() != null ? folio.getZonaRegistral().getVereda().getNombre() : folio.getZonaRegistral().getVereda().getIdVereda()%>&nbsp;</td>
        </tr>
        <tr>
        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Fecha Apertura: </td>
        <td class="campositem">
                        <%try {
							fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							fechaHelper.setDate(folio.getFechaApertura());
							fechaHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}%></td>
        <td>Radicacion:</td>
        <td class="campositem"><%= turno.getIdWorkflow()%></td>
        <td>Con:</td>
        <%if(folio.getDocumento() == null){
        	Solicitud solic = turno.getSolicitud();
        	if(solic instanceof SolicitudCertificado){
        %>
	        <td class="campositem">CERTIFICADO</td>        	
        <%	
        	}else{
        %>
	        <td class="campositem">No hay documento asociado</td>        	
	    <%    
        	}        
        %>
	    <%} else{
	    	 if(folio.getDocumento().getTipoDocumento() == null || folio.getDocumento().getNumero() == null) {
	        	Solicitud solic = turno.getSolicitud();
	        	if(solic instanceof SolicitudCertificado){
	        %>
		        <td class="campositem">CERTIFICADO</td>        	
	        <%	
	        	}else{
	        %>
		        <td class="campositem">No hay documento asociado</td>        	
		    <%    
	        	}        
	        %>
	        <%} else {%>
			<td class="campositem"><%= folio.getDocumento().getTipoDocumento().getNombre()%> de: <%=folio.getDocumento().getNumero()%>&nbsp;</td>        	
     	      <%}%>
        <%} %>
        <td>Cod Catastral : </td>
        <td class="campositem"><%= folio.getCodCatastral()%></td>
        </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>ESTADO DEL FOLIO </td>
        <td class="campositem"><strong>El estado sera proporcionado por el sistema</strong></td>
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
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td class="campositem"><%=folio.getComplementacion().getComplementacion()%>&nbsp;</td>
        </tr>
        </table>
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
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Tipo del Predio </td>
            <td class="campositem"><%= folio.getTipoPredio().getNombre()%>&nbsp;</td>
        </tr>
    </table>
    <table width="100%" class="camposform">
    	<%
    	Direccion direccionTemp = (Direccion)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CDireccion.DIRECCION_TEMPORAL);
         if(folio.getDirecciones().isEmpty() && (direccionTemp==null)){%>
        <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td class="campositem">No tiene direcciones registradas para este folio</td>
        </tr>              	              
        <%} else {
			int indexDireccion = 1;        
            Iterator itDirecciones = folio.getDirecciones().iterator();
            boolean tieneDirecciones = false;
            while(itDirecciones.hasNext()){
                Direccion direccion = (Direccion) itDirecciones.next();
                tieneDirecciones = true;
         %>
                
                <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                <td class="campositem"><b><%=""+indexDireccion%>&nbsp;&nbsp;-</b>&nbsp;&nbsp;
<%=((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"") +  "&nbsp;&nbsp;" + ((direccion.getValorEje()!=null)?direccion.getValorEje():"") + "&nbsp;&nbsp;&nbsp;" + ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"") + "&nbsp;&nbsp;" + ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((direccion.getEspecificacion()!=null)?direccion.getEspecificacion():"")%>
</td>
                </tr>		
                
            <%indexDireccion++;
            }
            if(direccionTemp!=null &&( !tieneDirecciones) ){
            %>

                <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                <td class="campositem">
<%=((direccionTemp.getEje()!=null&&direccionTemp.getEje().getNombre()!=null&&!(direccionTemp.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccionTemp.getEje().getNombre():"") +  "&nbsp;&nbsp;" + ((direccionTemp.getValorEje()!=null)?direccionTemp.getValorEje():"") + "&nbsp;&nbsp;&nbsp;" + ((direccionTemp.getEje1()!=null&&direccionTemp.getEje1().getNombre()!=null&&!(direccionTemp.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccionTemp.getEje1().getNombre():"") + "&nbsp;&nbsp;" + ((direccionTemp.getValorEje1()!=null)?direccionTemp.getValorEje1():"") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ((direccionTemp.getEspecificacion()!=null)?direccionTemp.getEspecificacion():"")%>
</td>
                </tr>		
            <%}
        }%>
    </table>
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
			<%if(ocultarAnotaciones.equals("FALSE")){%>
              <%Iterator itAnotacion = folio.getAnotaciones().iterator();
              	while(itAnotacion.hasNext()){
              	Anotacion anotacion = (Anotacion)itAnotacion.next();%>
              	<%String ntipodoc= anotacion.getDocumento().getTipoDocumento().getNombre()!=null ? anotacion.getDocumento().getTipoDocumento().getNombre(): "&nbsp";
              	  String numdoc = anotacion.getDocumento().getNumero() != null ? anotacion.getDocumento().getNumero() : "&nbsp";
                  String valor =  anotacion.getValor() != 0 ? Double.toString(anotacion.getValor()) : "&nbsp";
           		  String radicacion = anotacion.getNumRadicacion() != null ? anotacion.getNumRadicacion() : "&nbsp";
           		 %>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaci&oacute;n: N&ordm; <%=anotacion.getOrden()%>&nbsp; </td>
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
						}%>&nbsp;</td>
            <td width="146">Radicaci&oacute;n</td>
            <td width="212" class="campositem"><%=radicacion%>&nbsp;</td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Doc</td>
            
            <td class="campositem"><%=ntipodoc%>&nbsp;<%=numdoc%>&nbsp;</td>
            <td>Del</td>
            <td class="campositem">
                       <%try {
							fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							fechaHelper.setDate(anotacion.getDocumento().getFecha());
							fechaHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}%></td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Valor</td>
            <td class="campositem"><%=valor%>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Especificaci&oacute;n</td>
            <%
            if(anotacion.getEspecificacion()!=null){                        
            %>
            <td class="campositem"><strong><%= anotacion.getEspecificacion()%></strong>&nbsp;</td>            
            <%}else{%>
            <td class="campositem"><strong><%= anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() != null ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "&nbsp;"%>&nbsp;<%= anotacion.getNaturalezaJuridica().getNombre() != null ? anotacion.getNaturalezaJuridica().getNombre() : "&nbsp;"%>&nbsp;<strong></td>
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
                <td><%=anCiudadano.getRolPersona() != null ? anCiudadano.getRolPersona() : "&nbsp;"%></td>
                <td class="campositem"><%=anCiudadano.getCiudadano().getInfoCiudadano()%> </td>
			    <td class="campositem"><span class="titresaltados"><%=anCiudadano.getStringMarcaPropietario()%></span>&nbsp;</td>
    	</tr>
            <%}%>
        </table>
        <%}%>
        

<%}%> 
<!--<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
<tr>
<td class="campositem">No hay anotaciones que mostrar</td>
</tr>
</table>
-->
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td align="center" class="tdtablaanexa02">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones de la hoja de ruta</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_vprevia.gif" width="16" height="21"></td>
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
    <table width="100%" class="camposform">
        <tr>
            <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <form name="CreacionFolio" method="post" action="certificado.do">
                <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="IMPRIMIR_HOJA_RUTA">
                <td width="150" align="center"><p>
                    <input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0">
                </td>
            </form>
            
            <%
              Solicitud solicitud=turno.getSolicitud();
              Folio folio1=null;
              String idZonaReg=null;
              if (solicitud!=null)
              {
                List solicitudesFolio=solicitud.getSolicitudFolios();
	            if (solicitudesFolio!=null)
	            {
	               if (solicitudesFolio.size()>0)
	               {
	                  int index=solicitudesFolio.size()-1;
	                  SolicitudFolio solFol =(SolicitudFolio)solicitudesFolio.get(index);
	                  folio1=solFol.getFolio();
	                  if (folio1!=null)
	                  { 
	                    if (folio1.getZonaRegistral().getIdZonaRegistral()!=null) {
	                    	idZonaReg = folio1.getZonaRegistral().getIdZonaRegistral();
						}
	                  }
	               }
	             }
              }
              String accionBotonCrear=     gov.sir.core.web.acciones.registro.AWFolio.REGISTRAR_CREACION_FOLIO;
              String accionBotonGrabarTmp= gov.sir.core.web.acciones.registro.AWFolio.GRABAR_TMP;
              String botonCrear= "btn_aprobar_hoja_ruta.gif";
              String botonGrabarTmp= "btn_grabartmp.gif";
              
              String boton="";
              String accionBoton;
              String visibleBtnFinalizar="display:none";
              
              String path=request.getContextPath()+"/jsp/images/";

              if (turno.getSolicitud() instanceof SolicitudCertificado){
                  if (idZonaReg==null)
                  { 
                    boton = path+botonCrear;
                    accionBoton = accionBotonCrear;
                  }
                  else
                  {
                    boton = path+botonGrabarTmp;
                    accionBoton = accionBotonGrabarTmp;
                    visibleBtnFinalizar="display:block";              
                  }
              }else{
                  boton=path+botonCrear;
                  accionBoton = accionBotonCrear;
                  visibleBtnFinalizar="display:block";  
              }
              
            %>
             
             <form name="CreacionFolio" method="post" action="radicacion.do">
                <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=accionBoton%>">
                <td width="175" align="center">
                   <input name="imageField" type="image" src="<%=boton%>" width="175" height="21" border="0">
                </td>
             </form> 
            
             
            <form name="CreacionFolio" method="post" action="certificado.do">
                <input type="hidden"  name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.certificado.AWCertificado.ANT_SISTEMA_CREACION_FOLIO_CREADO%>" >
                <td width="150" align="center">
                    <input name="imageField" style="<%=visibleBtnFinalizar%>" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_finalizar.gif" width="139" height="21" border="0">
                </td>
            </form>
            
            
            <td width="140">
            	<!--input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"  onClick="<%=vistaAnterior%>" width="139" height="21" border="0"-->
            	<a href="<%=request.getContextPath()%>/crear.folio.view"> <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"  width="139" height="21" border="0"></a>
            	</td>
            <td>&nbsp;</td>
        </tr>
    </table></td>
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
</body>
</html>
