<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="org.auriga.util.FechaConFormato"%>

<%
Folio folio = (Folio)session.getAttribute(AWAdministracionForseti.FOLIO_TRASLADADO);
ZonaRegistral zonaRegistral = folio.getZonaRegistral();
Circulo circulo = zonaRegistral.getCirculo();
Vereda vereda = zonaRegistral.getVereda();

String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
vistaAnterior = vistaAnterior !=null 
	? "javascript:window.location.href='"+vistaAnterior+"';" 
	: "javascript:history.back();";


%>

<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- sof:block: "alt behavior" -->

<script type="text/javascript">
var ol_fgcolor="#ffffc0";
var ol_border="1";
var ol_bgcolor="#FFFFC0";
var ol_textcolor="#000000";
var ol_capcolor="#aaaaaa";
//var ol_css="forms-help";

</script>
<style media="screen">
.forms-help {
    border-style: dotted;
    border-width: 1px;
    padding: 5px;
    background-color:#FFFFC0; /* light yellow */
    width: 200px; /* otherwise IE does a weird layout */
    z-index:1000; /* must be higher than forms-tabContent */
}

</style>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/privileged/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<!-- eof:block -->
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
		<tr> 
			<td width="12">&nbsp;</td>
			<td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
			<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
				<table border="0" cellpadding="0" cellspacing="0">
					<tr> 
					  <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado de Folio</td>
					  <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
					</tr>
			  </table>
			  
			  
		  </td>
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
			  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
				  <tr>
					<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio Trasladado</td>
					<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
					<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
			  </table>
			  </td>
			  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
			  <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
			  <td height="10" valign="top" bgcolor="#79849B" class="tdtablacentral">                
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			  <tr>
				<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
				<td class="bgnsub"><p>Oficina de registro de instrumentos p&uacute;blicos</p></td>
				<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
				<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			  </tr>
			</table>
	<br>
				  
	  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
		  <tr>
		  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td >N&uacute;mero de Matr&iacute;cula</td>
		  <td class="campositem"> <%=   (folio.getIdMatricula()==null)?"&nbsp;":folio.getIdMatricula()  %> </td>
		  </tr>
		  </table>			
				   
	  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
		  <tr>
		  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td>Circulo Registral:</td>
		  <td class="campositem">  <%= (circulo==null)?"&nbsp;":circulo.getNombre()%>  </td>
		  <td>Depto:</td>
		  <td class="campositem"> <%= (vereda==null)?"&nbsp;":vereda.getMunicipio().getDepartamento().getNombre()%> </td>
		  <td>Municipio:</td>
		  <td class="campositem"> <%= (vereda==null)?"&nbsp;":vereda.getMunicipio().getNombre()%> </td>
		  <td>Vereda:</td>
		  <td class="campositem">  <%= (vereda==null)?"&nbsp;":vereda.getNombre() %>  </td>  
		  </tr>
		  <tr>
		  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td>Fecha Apertura: </td>
		  <td class="campositem">
		  <%= ((folio.getFechaApertura()==null)?"":FechaConFormato.formatear(folio.getFechaApertura())) %>
		  </td>
		  <td>Radicacion:</td>
		  <td class="campositem">
		  <%  
		  List anotaciones = folio.getAnotaciones();   
		  Anotacion primeraAnotacion = null;
		  Documento docPrimeraAnotacion = null;
		  TipoDocumento tipoDocPrimeraAnotacion = null;
		  if(!anotaciones.isEmpty()){
		   	primeraAnotacion = (Anotacion)anotaciones.get(0);
		   	docPrimeraAnotacion = primeraAnotacion.getDocumento();
		   	if(docPrimeraAnotacion != null){
		   		tipoDocPrimeraAnotacion = docPrimeraAnotacion.getTipoDocumento();
		   		}
		   	}
		  %>
		  <%= (primeraAnotacion==null)?"&nbsp;":primeraAnotacion.getNumRadicacion()%>
		  </td>
		  <td>Con:</td>
		  <td class="campositem">
		  <%= (tipoDocPrimeraAnotacion==null)?"&nbsp;":tipoDocPrimeraAnotacion.getNombre()%> de: <%=(docPrimeraAnotacion==null)?"&nbsp;":""+docPrimeraAnotacion.getNumero()%>
		  </td>
		  <td>Cod Catastral : </td>
		  <td class="campositem">  <%= (folio.getCodCatastral()==null)?"&nbsp;":folio.getCodCatastral()   %>     </td>
		  </tr>
		
		  
	  </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
	  <%
		  Documento documentoFolio = folio.getDocumento();
		  TipoDocumento tipoDoc =(documentoFolio!=null)? documentoFolio.getTipoDocumento():null;
		  %>
		  <tr>
		  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td >Datos de Apertura de Folio:</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  </tr>
		  <tr>
		  <td>&nbsp;</td>
		  <td >Fecha de Apertura</td>
		  <td class="campositem"> <%= ((folio.getFechaApertura()==null)?"":FechaConFormato.formatear(folio.getFechaApertura())) %> </td>
		  <td>N&uacutemero de Radicaci&oacuten:</td>
		  <td class="campositem"> <%=  (folio.getRadicacion()==null)?"&nbsp;":folio.getRadicacion()  %> </td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  </tr>
		  <tr>
		  <td>&nbsp;</td>
		  <td >Datos del Documento:</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  </tr>
		  <tr>
		  <td>&nbsp;</td>
		  <td>Tipo:</td>
		  <td class="campositem"> <%= (tipoDoc==null)?"&nbsp;":tipoDoc.getNombre() %> </td>
		  <td>N&uacutemero:</td>
		  <td class="campositem"> <%= (documentoFolio==null)?"&nbsp;":documentoFolio.getNumero() %> </td>
		  <td>Fecha:</td>
		  <td class="campositem"> <%= (documentoFolio==null)?"&nbsp;":FechaConFormato.formatear(documentoFolio.getFecha())  %> </td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  </tr>
		  </table>
	  
	  
	  
	  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
		  <tr>
		  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td>ESTADO DEL FOLIO </td>
		  <td class="campositem"><strong>
		  <%= folio.getEstado().getNombre()%>
		  </strong></td>
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
			  <td class="campositem"><p><%= (folio.getComplementacion()==null)?"":folio.getComplementacion().getComplementacion()%></p></td>
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
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		<tr>
		  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"><p>Matricula abierta con base en la(s) siguientes(s) matriculas(s) (En caso de integraci&oacute;n y otros)</p></td>
		  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
		  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		</tr>
	  </table>
				  <br>
				  
	  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
	  <%
		if((!anotaciones.isEmpty()) && !((Anotacion)anotaciones.get(0)).getAnotacionesPadre().isEmpty()){
			Iterator itPadres = ((Anotacion)folio.getAnotaciones().get(0)).getAnotacionesPadre().iterator();
			while(itPadres.hasNext()){
			FolioDerivado folioD = (FolioDerivado)itPadres.next();
			Anotacion padreFolioD = folioD.getPadre();
			String numRadPadreFolioD = (padreFolioD==null)?"&nbsp;":padreFolioD.getNumRadicacion();
			%>
			  <tr>
				  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				  <td class="campositem"><%=  numRadPadreFolioD  %></td>
			  </tr>
			<%}
		%>
		<%} 
		else if(!anotaciones.isEmpty()){
			Anotacion ultimaAnotacion =  (Anotacion)anotaciones.get(folio.getAnotaciones().size()-1);
			if(ultimaAnotacion != null && (!ultimaAnotacion.getAnotacionesHijos().isEmpty()) ){
				int cantidadAnotaciones = folio.getAnotaciones().size();
				//ultimaAnotacion.getAnotacionesHijos()
				//Iterator itHijos = ((Anotacion)folio.getAnotaciones().get(cantidadAnotaciones)).getAnotacionesHijos().iterator();
				Iterator itHijos = ultimaAnotacion.getAnotacionesHijos().iterator();
				while(itHijos.hasNext()){
					FolioDerivado folioD = (FolioDerivado)itHijos.next();%>
		  <tr>
			  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			  <td class="campositem"><%= (folioD.getHijo()==null)?"&nbsp;":folioD.getHijo().getNumRadicacion()%></td>
		  </tr>
			<%
				}
			}
		} else {%>
		  <tr>
		  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		  <td class="campositem">No tiene otras matriculas asociadas a este folio</td>
		  </tr>
	  <%}%>
	  </table>
	<hr class="linehorizontal">
		  
		  
		  
	  <%Iterator itAnotacion = folio.getAnotaciones().iterator();
			while(itAnotacion.hasNext()){
				Anotacion anotacion = (Anotacion)itAnotacion.next();
				%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				<tr>
					<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
					<td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Anotaci&oacute;n: N&ordm;  <%=anotacion.getOrden()!=null?anotacion.getOrden():""%> </td>
					<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
					<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
				</tr>
		  </table>
		   <br>
		  <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
				  <tr>
					  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					  <td width="179">Fecha</td>
					  <td width="211" class="campositem"><%= anotacion.getFechaRadicacion()!=null?FechaConFormato.formatear(anotacion.getFechaRadicacion()):"" %></td>
					  <td width="146">Radicaci&oacute;n</td>
					  <td width="212" class="campositem"><%=anotacion.getNumRadicacion()!=null?anotacion.getNumRadicacion():""%></td>
				  </tr>
				  <%
				  Documento documento = anotacion.getDocumento();
				  
				  TipoDocumento tipoDocumento = (documento==null)?null: documento.getTipoDocumento();
				  OficinaOrigen oficinaOrigen = (documento==null)?null: documento.getOficinaOrigen();
				  String nombreOficinaOrigen = (oficinaOrigen==null)?"&nbsp;":oficinaOrigen.getNombre();
				  String numeroOficinaOrigen = (oficinaOrigen==null)?"&nbsp;":oficinaOrigen.getNumero();
						  
				  Vereda veredaAnotacion = (oficinaOrigen==null)?null:oficinaOrigen.getVereda();
				  String nombreVeredaAnotacion = (veredaAnotacion==null)?"&nbsp;":veredaAnotacion.getNombre();
				  
				  if(documento.getOficinaOrigen() == null){
				  	
				  	if(documento.getOficinaInternacional()!=null){
						nombreOficinaOrigen = documento.getOficinaInternacional();
				  	}else if(documento.getComentario()!=null){
						String comentario = documento.getComentario();
						if(comentario.indexOf("-")!=-1){
							java.util.StringTokenizer token = new java.util.StringTokenizer(comentario, "-");
							nombreVeredaAnotacion = token.nextToken();
							nombreOficinaOrigen = token.nextToken();
						}else{
							nombreOficinaOrigen = comentario;
						}					  	
				  	}
				  	
				  }
				  
				  String nombreNaturalezaJuridica = null;
				  NaturalezaJuridica naturalezaJuridica = anotacion.getNaturalezaJuridica();				  
				  if(anotacion.getEspecificacion()!=null){
				  	  nombreNaturalezaJuridica = anotacion.getEspecificacion();
				  }else{
					  nombreNaturalezaJuridica = (naturalezaJuridica==null)?"&nbsp;":naturalezaJuridica.getNombre();
				  }
				  %>
				  <tr>
					  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					  <td>Doc</td>
					  <td class="campositem"><%= (tipoDocumento==null)?"&nbsp;":tipoDocumento.getNombre()%>&nbsp;<%= (documento==null)?"&nbsp;":documento.getNumero()%></td>
					  <td>Del</td>
					  <td class="campositem"><%= (documento!=null && documento.getFecha()!=null)?FechaConFormato.formatear(documento.getFecha())+", dd/MM/yyyy":""%></td>
				  </tr>
				  <tr>
					  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					  <td>Ciudad de Origen</td>
					  <td class="campositem"> <%= nombreOficinaOrigen %>  de <%= nombreVeredaAnotacion  %></td>
					  <td>N&uacutemero Oficina Origen</td>
					  <td  class="campositem"><%= numeroOficinaOrigen %></td>
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
					  <td class="campositem"><%= nombreNaturalezaJuridica %></td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
				  </tr>
				  <tr>
					  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					  <td>Comentario</td>
					  <td class="campositem"><%= (anotacion.getComentario()==null)?"&nbsp;":anotacion.getComentario() %></td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
				  </tr>
				  
		  </table>
		  
			<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
				  <tr>
					  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					  <td class="campostitle">
					  <p>Personas que intervienen en el acto (La X indica a la persona que figura como titular de derechos reales de dominio, I-Titular de dominio incompleto)</p>
					  </td>
					  <td >&nbsp;</td>
				  </tr>
			</table>	  
			<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">	  
			  <%if(anotacion.getAnotacionesCiudadanos().isEmpty()){%>
				  <tr>               
				  		<td>No tiene ciudadanos asociados a esta anotaci&oacute;n</td>          
				  </tr>
			  <%} else {
				Iterator itCiudadanos = anotacion.getAnotacionesCiudadanos().iterator();
					while(itCiudadanos.hasNext()){
					  AnotacionCiudadano anCiudadano = (AnotacionCiudadano) itCiudadanos.next();
					  Ciudadano ciudadanoAnotacion = anCiudadano.getCiudadano();
					  String part="&nbsp;";
					  String participacion = "";
			
					  if(anCiudadano.getParticipacion()!=null){
			   			participacion=anCiudadano.getParticipacion();
			    		if(participacion.length()>3){
			    			part= participacion.substring(0,3);
			    		}else{
			    			part= participacion;
			    		}
			    		participacion=anCiudadano.getParticipacion();
					  }
					  %>
					  <tr>
					  <td><%= (anCiudadano.getRolPersona()==null)?"&nbsp;":anCiudadano.getRolPersona()   %></td>
					  <td class="campositem">
					  		<%= (ciudadanoAnotacion.getInfoCiudadano()==null)?"&nbsp;":ciudadanoAnotacion.getInfoCiudadano() %>  
					  </td>
					  <td>Participaci&oacute;n</td>
					  <td class="campositem"  onclick='return overlib("<%= participacion %> " , STICKY, MOUSEOFF );' onmouseout='nd();' ><%= part   %></td>

    				  <td class="campositem"><span class="titresaltados"><%=anCiudadano.getStringMarcaPropietario()%></span>&nbsp;</td>
    				 </tr>
					<%}%>
			  <%}%>
			 </table>
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
					<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
					<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
					<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>  
							<td><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
						  </tr>
						</table>
					</td>
					<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
				</tr>
		 </table>
		 

		 
			  </td>
			  <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
			</tr>
				<tr>
				  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				  <td valign="top" bgcolor="#79849B" class="tdtablacentral"><hr class="linehorizontal"></td>
				  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			  </tr>
				<tr> 
				  <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				  <td valign="top" bgcolor="#79849B" class="tdtablacentral">
					<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
					  <tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="campositem">NRO TOTAL DE ANOTACIONES: *<%= folio.getAnotaciones().size() %>* </td>
					  </tr>
					</table>
				  </td>
				  <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
				</tr>
				<tr>
				  <td  background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				  <td valign="top" bgcolor="#79849B" class="tdtablacentral">
					<hr class="linehorizontal">
					<table class="camposform">
						<tr>
						  <td ><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                    <form action="admin.traslado.view" method="post" name="CONSULTAS" target="_self" id="CONSULTAS">
							<td  align="center">
                                                            <input name="back" type="image" onClick="<%=vistaAnterior%>" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                                                        </td>
                                                    </form>
                                                    <td width="686">&nbsp;</td>
						</tr>
				    </table>
				  </td>
				  <td height="10" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			  </tr>

			<tr> 
			  <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"></td>
			  <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" alt=""></td>
			</tr>
		  </table></td>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		</tr>
		  
		   <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
	</table>
    </td>
  </tr>
</table>
      
      
      
   



