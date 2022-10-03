<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.MatriculaHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTurnoManualCertificado"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>


<% 
	// Si el objeto de Proceso no existe, lo creo para proceso de certificados
	Proceso proceso = (Proceso)request.getAttribute(WebKeys.PROCESO);
	if(proceso == null) {
		proceso = new Proceso();
		proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_CERTIFICADOS));
		proceso.setNombre("CERTIFICADOS");
		request.getSession().setAttribute(WebKeys.PROCESO, proceso);
		
		//session.removeAttribute(AWTurnoManualCertificado.TURNO_ANIO);
		//session.removeAttribute(AWTurnoManualCertificado.TURNO_OFICINA);
		//session.removeAttribute(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
		//session.removeAttribute(AWTurnoManualCertificado.VALOR_LIQUIDACION);
		//session.removeAttribute(AWTurnoManualCertificado.FECHA_INICIO);
		//session.removeAttribute(AWTurnoManualCertificado.NUMERO_RECIBO);
	}
	
	ListaElementoHelper certHelper = new ListaElementoHelper();
	ListaElementoHelper tarifasHelper = new ListaElementoHelper();
	ListaElementoHelper docHelper = new ListaElementoHelper();
	ListaElementoHelper impresorasHelper = new ListaElementoHelper();
   
	MatriculaHelper matHelper = new MatriculaHelper(); 
	TextHelper textHelper = new TextHelper();
	TextHelper textHelperCopias = new TextHelper();

   	NotasInformativasHelper helper = new NotasInformativasHelper();

   	AntiguoSistemaHelper ASHelper= new AntiguoSistemaHelper();
   
   	String ocultarAntSist = request.getParameter(WebKeys.OCULTAR);
   	
   	/*Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
   	if(session.getAttribute(AWTurnoManualCertificado.TURNO_OFICINA) == null) {
		String sCirculo = circ.getIdCirculo();
		session.setAttribute(AWTurnoManualCertificado.TURNO_OFICINA, sCirculo);
	}*/
	
	/*if(session.getAttribute(AWTurnoManualCertificado.TURNO_ANIO) == null) {
		Calendar cCalendar = Calendar.getInstance();
		String sAnio = "" + cCalendar.get(Calendar.YEAR);
		session.setAttribute(AWTurnoManualCertificado.TURNO_ANIO, sAnio);
	}*/
	if(ocultarAntSist == null||ocultarAntSist.equals("")){
	
		ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		if(ocultarAntSist==null) {
			ocultarAntSist = "TRUE";
		}
	} else {
	    ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		session.setAttribute(WebKeys.OCULTAR,ocultarAntSist);
	}
	
	//SolicitudCertificado solicitud = null;
	LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)session.getAttribute(WebKeys.LIQUIDACION);
	if (liquidacion!= null) {
		TipoCertificado tipoCertificado = liquidacion.getTipoCertificado();
	   	session.setAttribute("TIPO_CERTIFICADO", tipoCertificado.getIdTipoCertificado());
	   	/*solicitud = (SolicitudCertificado) liquidacion.getSolicitud();
	   	if (solicitud!= null) {
		  	session.setAttribute("COPIAS",String.valueOf(solicitud.getNumeroCertificados()));
	   	  	Ciudadano ciudadano = solicitud.getCiudadano(); 
	   	  	if (ciudadano!= null) {
				session.setAttribute(WebKeys.CIUDADANO,ciudadano);
		   	  
		   	  	session.setAttribute(CCiudadano.IDCIUDADANO,ciudadano.getDocumento());
		   	  	session.setAttribute(CCiudadano.TIPODOC,ciudadano.getTipoDoc());
		  	  	session.setAttribute(CCiudadano.APELLIDO1,ciudadano.getApellido1());
		  	  	session.setAttribute(CCiudadano.APELLIDO2,ciudadano.getApellido2());
		  	  	session.setAttribute(CCiudadano.NOMBRE,ciudadano.getNombre());
		  	  	session.setAttribute(CCiudadano.TELEFONO,ciudadano.getTelefono());
	   	  	}
		  	DatosAntiguoSistema antSis = solicitud.getDatosAntiguoSistema();
		  	if (antSis!= null) {
	 	  	  	session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, antSis.getLibroAnio());
	 	  	  	session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, antSis.getLibroNumero());
	 	  	  	session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, antSis.getLibroPagina());
	 	  	  	session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, antSis.getLibroTipo());
		   	  
		   	  	session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, antSis.getTomoAnio());
		   	  	session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, antSis.getTomoMunicipio());
		   	  	session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, antSis.getTomoNumero());
		   	  	session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, antSis.getTomoPagina());
		   	  	session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, antSis.getComentario());
		  	}
	   	}*/
	}
	
	String nombreVistaSolicitud = (String)session.getAttribute(gov.sir.core.web.WebKeys.VISTA_SOLICITUD);

	if(nombreVistaSolicitud==null) {
		nombreVistaSolicitud = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL);
		session.setAttribute(gov.sir.core.web.WebKeys.VISTA_SOLICITUD,nombreVistaSolicitud);
	}	
	
	//IDCIRCULO
	/*String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}*/

	%>
	
   	<SCRIPT>

	function cambiarAccion(text) {
       document.CERTIFICADO.ACCION.value = text;
       document.CERTIFICADO.submit();
	}
		
	function quitar(text) {
       document.CERTIFICADO.ITEM.value = text;
       cambiarAccion('ELIMINAR');
	}
	
	function datosAntSistema(ocultar){
		document.CERTIFICADO.OCULTAR.value = ocultar;
		cambiarAccion('RECARGAR');
	}
	
	
	function incrementarSecuencial(text){
		document.CERTIFICADO.action = 'certificado.do'
		document.CERTIFICADO.ACCION.value = text;
		document.CERTIFICADO.submit();
	}
	
	
	function centrar(){
	    document.location.href="#verAnotaciones";
	}
	
	function oficinas(nombre,valor,dimensiones)
	{
		document.CERTIFICADO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
		popup.focus();
	}
	
	function locacion(nombre,valor,dimensiones){
		document.getElementById('id_depto').value=valor+"_ID_DEPTO";
		document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
		document.getElementById('id_munic').value=valor+"_ID_MUNIC";
		document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
		document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
		document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	    popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
	}
	
	function setTipoOficina(){
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
	
	}	

   </SCRIPT>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
   
   
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">


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
        <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
            <tr> 
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Certificados</td>
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
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno 
        certificado</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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
    <form action="turnoManualCertificado.do" method="post" name="CERTIFICADO" id="CERTIFICADO">
    <input type="hidden" name="ACCION" value="AGREGAR">
    <input type="hidden" name="<%=AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS%>" value="NORMAL">
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    	<tr> 
        	<td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        	<td class="bgnsub">Turno</td>
        	<td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        	<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
    	</tr>
	</table>
    <br>
   	<table width="100%" class="camposform">
		<tr> 
			<td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			<%--<td>A&ntilde;o</td>
			<td>
				<% 
					try {
						textHelper.setNombre(AWTurnoManualCertificado.TURNO_ANIO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualCertificado.TURNO_ANIO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>--%>
			<td>C&iacute;rculo</td>
			<td>
				<% 
					try {
					
						//TextHelper textHelper = new TextHelper();
						textHelper.setNombre(AWTurnoManualCertificado.TURNO_OFICINA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualCertificado.TURNO_OFICINA);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>
			<td>Consecutivo</td>
			<td>
				<% 
                	try {
						textHelper.setNombre(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
				%>
			</td>
			<td>Fecha de Radicaci&oacute;n</td>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<% 
								try {
 		                        	textHelper.setNombre(AWTurnoManualCertificado.FECHA_INICIO);
                  			    	textHelper.setCssClase("camposformtext");
                  			    	textHelper.setId(AWTurnoManualCertificado.FECHA_INICIO);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
							%>
						</td>
						<td>&nbsp;
							<a href="javascript:NewCal('FECHA_INICIO','ddmmmyyyy',true,24)">
								<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									alt="Fecha" width="16" height="21" border="0"
									onClick="javascript:Valores('<%=request.getContextPath()%>')">
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    	<tr> 
        	<td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        	<td class="bgnsub">Datos Solicitud Certificado</td>
        	<td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        	<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
    	</tr>
	</table>
    <br>
    	<table width="100%" class="camposform">
        	<tr> 
            	<td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                <td class="titulotbcentral">Tipo de Certificado</td>
			</tr>
			<tr> 
				<td>&nbsp; </td>
				<td>      
                     <% 
                     	try {
							List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS) ;
							if(session.getAttribute("TIPO_CERTIFICADO") == null) {
								session.setAttribute("TIPO_CERTIFICADO",CTipoCertificado.TIPO_INMEDIATO_ID);
							}

	 		                certHelper.setOrdenar(false);
							certHelper.setTipos(tipos);
							certHelper.setNombre("TIPO_CERTIFICADO");
							certHelper.setCssClase("camposformtext");
							certHelper.setId("TIPO_CERTIFICADO");
							certHelper.render(request,out);
						} catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>  
				</td>
			</tr>
		</table>

              <%--<table width="100%" class="camposform">
                  <tr> 
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="titulotbcentral">Tipo de Tarifa</td>
                  </tr>
                  <tr> 
                  <td>&nbsp; </td>
                  <td>
                       
                      <% try   
                         { 
	 		                   List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS) ;
	 		                 
	 		                   if(session.getAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS) == null){
		 		                    session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS,"NORMAL");
	 		                    }
	 		                   
	 		                   tarifasHelper.setOrdenar(false);
                  			   tarifasHelper.setTipos(tipos);
                  			   tarifasHelper.setNombre(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
                  			   tarifasHelper.setCssClase("camposformtext");
                  			   tarifasHelper.setId(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
							   tarifasHelper.render(request,out);
							}
						    catch(HelperException re)
						    {
								re.printStackTrace();
								out.println("ERROR " + re.getMessage());
							}
						    catch(Exception e)
						    {
								e.printStackTrace();
								out.println("ERROR " + e.getMessage());
							}
							
						%>
                      
                  </td>
                  </tr>
              </table>--%>


              <br>
              <%--<table width="100%" class="camposform">
                  <tr> 
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Cantidad</td>
                  <td> 
     				
     				<% try {
     							if(session.getAttribute("COPIAS") == null ) 
     							{
     								session.setAttribute("COPIAS","1");
     							}
     							
 		                        textHelperCopias.setNombre("COPIAS");
 		                        
                  			    textHelperCopias.setCssClase("camposformtext");
                  			    //textHelperCopias.setCssClase("campositem");
                  			   
                  			    
                  			    textHelperCopias.setId("COPIAS");
								textHelperCopias.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    
                  </td>
                  </tr>
              </table>
              <br>--%>
              <%--<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Certificado Anterior</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                  <tr> 
                      <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
                      <td>&nbsp;</td>
                  </tr>
                  <tr> 
                  <td>&nbsp;</td>
                  <td>N&uacute;mero del Turno Anterior</td>
                  <td>
                  
                  <% try {
                        textHelper.setNombre("TURNO_ANTERIOR");
           			    textHelper.setCssClase("camposformtext");
					    textHelper.setId("TURNO_ANTERIOR");
						textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
						%>
						
                  </td>
                  </tr>
              </table>
              <br>
               <table width="100%">
				<tr>
				 <td align="right">
	              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					<tr>
		 			  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 		  <td>Agregar Turno</td>
		 			  <td><a href="javascript:cambiarAccion('AGREGAR_TURNO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
		 		    </tr>
	  			  </table>  
	  			 </td>
	  			</tr>
	  		  </table>
              
              <br>--%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              
			<%
				try {	
					matHelper.setLink(true); 
					matHelper.render(request,out);
				} catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
									
			%>	

                      

              <br>
				<!-- Turno de registro al que se asociará el certificado -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="camposform">
                	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                	<tr> 
                  		<td>Turno de Registro al que est&aacute; asociado</td>
						<td>
							<%
								try {
 		            				textHelper.setNombre(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO);
                  					textHelper.setCssClase("camposformtext");
                  					textHelper.setId(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO);
									if(session.getAttribute(AWTurnoManualCertificado.TURNO_REGISTRO_ASOCIADO) != null)
										textHelper.setEditable(false);
									textHelper.render(request,out);
									textHelper.setEditable(true);
								} catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}				
							%>
						</td>
						<td>
							<%
								if(session.getAttribute(AWTurnoManualCertificado.TURNO_REGISTRO_ASOCIADO) == null) {
							%>
							<a href="javascript:cambiarAccion('ASOCIAR_TURNO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a>
							<%
								}
							%>
						</td>
                	</tr>
              	</table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para Antiguo Sistema</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	                <input type="hidden" name="<%=WebKeys.OCULTAR%>" id="<%=WebKeys.OCULTAR%>">
					<%if(ocultarAntSist.equals("FALSE")){%>
		                <td width="16"><a href="#"><img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="datosAntSistema('TRUE')" width="16" height="16" border="0"></a></td>
					<%}else{%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16"><a href="#"><img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="datosAntSistema('FALSE')" width="16" height="16" border="0"></a></td>
					<%}%>
                </tr>
              </table>
              <br>
    <input name="Depto" type="hidden" id="id_depto" value="">
    <input name="Depto" type="hidden" id="nom_Depto" value="">
    <input name="Mpio" type="hidden" id="id_munic" value="">
    <input name="Mpio" type="hidden" id="nom_munic" value="">    
    <input name="Ver" type="hidden" id="id_vereda" value="">
    <input name="Ver" type="hidden" id="nom_vereda" value="">              
			  <%if(ocultarAntSist.equals("FALSE")){
			  
			  try{
			  		ASHelper.render(request, out);
			  }
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
			  }
			  
			  
              
			  }%>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                  <tr> 
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
                      <% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		
               				String selected = (String)session.getAttribute(CCiudadano.TIPODOC);
               				if(selected==null){
               					selected = CCiudadano.TIPO_DOC_ID_SECUENCIA;
               				}                      		
                      		
                      		if(docs == null){
                      			docs = new Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		                        docHelper.setNombre(CCiudadano.TIPODOC);
                   			    docHelper.setId(CCiudadano.TIPODOC);		                        
                  			    docHelper.setSelected(selected);
                   			    docHelper.setCssClase("camposformtext");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212">
                  	<% 
                  		try {
							textHelper.setNombre(CCiudadano.IDCIUDADANO);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CCiudadano.IDCIUDADANO);
							textHelper.render(request,out);
						} catch(HelperException re) {
							out.println("ERROR " + re.getMessage());
						}
					%>
                  </td>
				</tr>
                <tr> 
                  <td>&nbsp;</td>
                  <td>Primer Apellido / Raz&oacute;n Social</td>
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
                  
                  <td>Tel&eacute;fono</td>
                  <td>
                  <% 
                  try 
                  {
 		          	textHelper.setNombre(CCiudadano.TELEFONO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadano.TELEFONO);
					textHelper.render(request,out);
				  }
				  catch(HelperException re)
				  {
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
					<td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
					<td class="bgnsub">Recibo</td>
					<td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
					<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
				</tr>
			</table>
			<br>
            <table width="100%" class="camposform">
				<tr> 
					<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td>N&uacute;mero de Recibo</td>
                  	<td> 
     					<% 
     						try {
 		                        textHelperCopias.setNombre(AWTurnoManualCertificado.NUMERO_RECIBO);
                  			    textHelperCopias.setCssClase("camposformtext");
                  			    textHelperCopias.setId(AWTurnoManualCertificado.NUMERO_RECIBO);
								textHelperCopias.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    
                  	</td>
				</tr>
				<tr> 
					<td width="20">&nbsp;</td>
					<td>N&uacute;mero de Copias</td>
                  	<td> 
     					<% 
     						try {
 		                        textHelperCopias.setNombre(AWTurnoManualCertificado.COPIAS);
                  			    textHelperCopias.setCssClase("camposformtext");
                  			    textHelperCopias.setId(AWTurnoManualCertificado.COPIAS);
								textHelperCopias.render(request,out);
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
				<tr>
					<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
          			<td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Liquidaci&oacute;n</td>
          			<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
          			<td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
          		</tr>
          	</table>
          	<br>
          	<table width="100%" class="camposform">
				<tr>
					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
					<td>Valor</td>
					<td>$
						<% 
                  			try {
 		          				textHelper.setNombre(WebKeys.VALOR_LIQUIDACION);
                  				textHelper.setCssClase("camposformtext");
                  				textHelper.setId(WebKeys.VALOR_LIQUIDACION);
								textHelper.render(request,out);
				  			} catch(HelperException re) {
				  				out.println("ERROR " + re.getMessage());
				  			}
				  		%>
				  	</td>
				</tr>
			</table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                  <tr> 
                 <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                <td width="150" align="center"><a href="javascript:cambiarAccion('LIQUIDAR')"><img  src="<%= request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a></td>
    			<td ><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado.REMOVER_INFO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a></td>
                  </tr>
              </table>

			</form>
          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      <%--
		<% 
		try
		{ 
		  //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		  //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		  //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		  helper.setNombreFormulario("CERTIFICADO");
		  helper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}	
		%>       
      <% 
%>--%>
      
</td>
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
