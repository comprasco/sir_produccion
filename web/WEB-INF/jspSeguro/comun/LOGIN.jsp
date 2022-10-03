<%@page import="gov.sir.core.web.acciones.comun.AWSeguridad" %>  
<%@page import="gov.sir.core.negocio.modelo.constantes.COPLookupTypes" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.comun.AWContrasena"%>
<%@page import="java.net.URL"%> 
<%@page import="de.nava.informa.core.ChannelIF"%>
<%@page import="de.nava.informa.parsers.FeedParser"%> 
<%@page import="de.nava.informa.impl.basic.ChannelBuilder"%>
<%@page import="de.nava.informa.impl.basic.Item"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="java.io.IOException"%>
<%@page import="de.nava.informa.core.ParseException"%>
<%@page import="gov.sir.fenrir.FenrirProperties"%>

<script language="JavaScript" type="text/JavaScript">
<%--
function cargarValidarNombreVentana(){
		try{
			validarNombreVentana();
		}catch(e){}
}
--%>
</script>
<script language='javascript'>
function borrarCookie(){
	try{
	    setCookie("appletImpresionCargado","",new Date(0));
	}catch(e){
	}
}
function setCookie(c_name,value,exp){
    c_string=c_name+"=";
    document.cookie=c_string + escape(value)+"; expires=" + exp.toGMTString();
}
<%--
function mostrarPassword(nombre,valor,dimensiones){
		popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
}
--%>
function validarLogin(){
	if ( document.getElementById('<%=AWSeguridad.ID_USUARIO%>').value == '' ) {
	    alert('Ingrese el usuario.');
	    document.getElementById('<%=AWSeguridad.ID_USUARIO%>').focus();
	    return;
  	}
	if ( document.getElementById('<%=AWSeguridad.CLAVE_USUARIO%>').value == '' ) {
	    alert('Ingrese el password.');
	    document.getElementById('<%=AWSeguridad.CLAVE_USUARIO%>').focus();
	    return;
  	}
  	document.Login.submit();
}

function validarEnter(){
	 if ((window.event && window.event.keyCode == 13) ){
		validarLogin();
	 }
}
</script>
<script>
	borrarCookie();
</script>

<%
if(session.getAttribute("PANTALLA_CARGADA")==null){
	double rand = Math.random()*100000;
	String ventanaID = ""+Math.round(rand);
	session.setAttribute("PANTALLA_CARGADA", ventanaID);
	
	

%>
<script language="JavaScript" type="text/JavaScript">
<%--
function validarNombreVentana(){
		var url =  '<%=request.getContextPath()%>/inicio.view';
		var winName = 'SIR_<%=ventanaID%>'; 
  		var features='location=no,status=yes,scrollbars=yes,resizable=yes,fullscreen=no,width='+(screen.availWidth-10)+',height='+(screen.availHeight-60)+',left=0,top=0';
  		window.open(url,winName,features);
		//location.href = 'bienvenido.cargado.view';
		//window.close();
		
		var v = window.self;
		v.opener=window.self;
		v.close();
}
validarNombreVentana(); 
--%>
</script>
<%
}
%>


<%session.removeAttribute(gov.sir.core.web.WebKeys.ROL);%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/disablerightclick.js"></script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Inicio de Sesión
                :: Inicio del Aplicativo :: SIR</td>
                <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa"><table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="9" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Inicio 
        de Sesi&oacute;n</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_llave.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif">&nbsp;</td>
    <td><img name="tabla_central_r1_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c9.gif" width="9" height="29" border="0" alt=""></td>
    </tr>
    <tr> 
    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" class="tdtablacentral">
    <form name="Login" method="post" action="seguridad.do">
    <input type="hidden" name="ACCION" value="LOGIN" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tdtablacentral">
        <tr> 
            <td>Ingrese su usuario y password asignados y haga click en ingresar</td>
        </tr>
        <tr> 
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tdtablacentral">
            <tr> 
                <td width="30%">Usuario</td>
                <td width="70%"><input name="<%=AWSeguridad.ID_USUARIO%>" type="text" class="CAMPOSFORM" id="<%=AWSeguridad.ID_USUARIO%>" value="" onKeyPress="validarEnter()"></td>
            </tr>
            <tr> 
                <td>Password</td>
                <td><input name="<%=AWSeguridad.CLAVE_USUARIO%>" type="password" class="CAMPOSFORM" id="<%=AWSeguridad.CLAVE_USUARIO%>" value="" onKeyPress="validarEnter()" ></td>
            </tr>
            <!--tr> 
                <td>Iniciar Sesi&oacute;n<br>como Administrador</td>
                <td><input name="<!--%=AWSeguridad.INICIAR_COMO_ADMINISTRADOR %>" type="checkbox"  id="<!--%= AWSeguridad.INICIAR_COMO_ADMINISTRADOR%>" value=""  onKeyPress="validarEnter()"></td>
            </tr-->
        </table></td>
        </tr>
        <tr> 
            <td><a href="javascript:mostrarPassword('consultaContrasena.do?<%= WebKeys.ACCION %>=<%=AWContrasena.CONSULTAR_MESA_AYUDA%>&<%= COPLookupTypes.NOMBRE_OPLOOKUP_TYPE %>=DATOS_HELP_DESK','Contrasena', 'width=400,height=380')">.:: <span class="importante">Olvido 
            su clave ?</span> Haga Click Aqu&iacute; ::.</a></td>
        </tr>
        <tr> 
            <td align="center">Al ingresar a este aplicativo usted esta 
            aceptando todas las condiciones legales que rigen este aplicativo 
            y la informaci&oacute;n contenida en el.</td>
        </tr>
        <tr> 
            <td align="center"><hr class="camposform"></td>
        </tr>
        <tr>
            <td align="center"><a href='javascript:validarLogin()'><img src="<%= request.getContextPath()%>/jsp/images/btn_iniciarsesion.gif" width="139" height="21" border="0"></a></td>
        </tr>
    </table>
    </form></td>
    <td width="11"  background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
            	<tr>
                    <td valign="middle" class="titulotbcentral">NOTICIAS</td>
                </tr>
                <tr>
                    <td><hr></td>
                </tr>
                <tr>
                	<td>
                		<div style='overflow:auto;width:450;height:150px;'>
                		<table width="420" border="0" cellpadding="0" cellspacing="0" class="tablas">
		                	<% 
                                        /*AHERRENO -  Se configura ruta en gov.sir.fenrir.properties*/
                                        FenrirProperties p = FenrirProperties.getInstancia();
                                        String R_MANUAL = p.getProperty(FenrirProperties.R_MANUAL);
                                        String R_NOTICIAS = p.getProperty(FenrirProperties.R_NOTICIAS);
								try{
                                                                    /**
                                                                     *@author: Fernando Padilla Velez
                                                                     *@change: Se modifica la pagina donde obtiene el xml para el resumen de las noticias mostradas.
                                                                    **/
			                		final URL url = new URL(R_NOTICIAS);
			                		final ChannelIF channel = FeedParser.parse(new ChannelBuilder(), url); 
			                		Object[] items = channel.getItems().toArray();
									Item item;
									
									final int itera = items.length>5?5:items.length; 
									final String contextPath = request.getContextPath();
									
									for(int i=0; i<itera;i++){
										item = (Item)items[i];
										out.write("<tr><td class='titulotbcentral'><img src='"+contextPath+"/jsp/images/ind_flecha.gif' width='20' height='15'><a href='"+item.getLink()+"' target='_blank' class='links'>&nbsp;"+item.getTitle()+" | " + DateFormatUtil.format("dd/MM/yyyy hh:mm", item.getDate())+"</a></td></tr>");
										out.write("<tr><td><hr width='415' align='center'></tr></td>");
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							%>
						</table>
					 	<div>
                	</td>
                 </tr>
                 <tr>
                    <td><hr></td>
                </tr>
                 <tr>
                  	<td valign="middle">
                  			<img src="<%= request.getContextPath()%>/jsp/images/ind_flecha.gif" width="20" height="15">
                                        <!--
                                            @author Fernando Padilla Velez
                                            @change Se  modifica este link para colocar el acceso al manual de usuarios de la aplicación SIR.
                                        -->
                   	 		<a href="<%=R_MANUAL%>" target='_blank' class="links">Manual en línea para usuarios SIR</a>
                    </td>
                </tr>
            </table></td>
        </tr>
    </table>
            
    </td>
    <td width="9" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn007.gif">&nbsp;</td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn005.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_r3_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c9.gif" width="9" height="6" border="0" alt=""></td>
    </tr>
</table></td>
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

<script language="JavaScript" type="text/JavaScript">
//cargarValidarNombreVentana();
</script>
