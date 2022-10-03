<%@page import ="gov.sir.core.web.helpers.registro.*,org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.List"%>
<%@page import="java.io.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="java.util.Vector"%>
<%@page import="org.auriga.smart.SMARTKeys"%>

<%
	ListaElementoHelper nombresArchivos= new ListaElementoHelper();
	boolean cerrar=false;
	boolean cargarListaArchivos=false;	

	//obtener lista de nombresArchivos
	List narchivos = (List) session.getAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR);
	List elementoslista = (List) session.getAttribute(AWAdministracionHermod.LISTA_ELEMENTOS_FIRMAS_REGISTRADOR);


	if(elementoslista==null){
		elementoslista = new ArrayList();
		if(narchivos == null){
			//Si NO hay lista, cargar la lista de archivos
			cargarListaArchivos=true;
		}else{
			//Si hay lista colocarlas en listaElementoHelper
			Iterator it = narchivos.iterator();
			for(;it.hasNext();){
				File firma = (File) it.next();			
				elementoslista.add(new ElementoLista(firma.getName(), firma.getName()));
			}
			session.setAttribute(AWAdministracionHermod.LISTA_ELEMENTOS_FIRMAS_REGISTRADOR, elementoslista);
		}
	}

	//Si tengo en session el nombre del archivo de la firma debo hacer preview de esta.	
	boolean mostrarPreview = false;
	File firmaAMostrar = (File) session.getAttribute(AWAdministracionHermod.FIRMA_PREVIEW);
	if(firmaAMostrar!=null) {
		mostrarPreview = true;
	}


	//saber si tiene q cerrarse
	Boolean oCarga= (Boolean)session.getAttribute("CERRAR_VENTANA");
	if(oCarga!=null){
		cerrar= oCarga.booleanValue();
	}
	
	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	if(exception!=null){
		 cerrar=true;
		 request.getSession().setAttribute("CERRAR_VENTANA", new Boolean(true));
	}
	session.removeAttribute(WebKeys.HAY_EXCEPCION);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(accion) {
	   document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.submit();
}

function cerrarVentana(){
	window.opener.submitIt();
	window.close();
}
</script>
<form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cambiar matricula</td>
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">FIRMAS</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
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
	        <td>Nombre archivo :</td>
	        <td>
	       <%try {
                  nombresArchivos.setCssClase("camposformtext");
                  nombresArchivos.setId(AWAdministracionHermod.NOMBRE_FIRMA_REGISTRADOR);
                  nombresArchivos.setTipos(elementoslista);                  			
                  nombresArchivos.setNombre(AWAdministracionHermod.NOMBRE_FIRMA_REGISTRADOR);
				  nombresArchivos.setFuncion("onChange=\"cambiarAccion('"+AWAdministracionHermod.PREVIEW_FIRMA_REGISTRADOR+"')\"");
                  nombresArchivos.render(request,out);
			}	catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
						
			%>
	        </td>
			<td width="50">&nbsp;</td>
        </tr>
		<tr>
	        <td>Preview :</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<%
			File firma = (File) session.getAttribute("FILE_FIRMA");
			String pathDest = "";
			if(firma!=null){
				 pathDest = firma.getAbsolutePath();
				 pathDest= pathDest.replace('\\', '/');
			}

		%>
		<tr>
			<%if(firma!=null){%>
				<td colspan="3"> <img src="FIRMA.jsp?ARCHIVO=<%=pathDest%>">	</td>
            <%}else{%>
				<td colspan="3"> <img src="<%=request.getContextPath()%>/jsp/images/sin_firma.gif" >	</td>
			<%}%>	
		</tr>
    </table>
    </td>
    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <td width="150"><a href="javascript:cambiarAccion('<%= AWAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="150" height="21" border="0"></a></td>
            <td width="150"><a href="javascript:cerrarVentana()"><img src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="150" height="21" border="0"></a></td>
        	<td width="55%">&nbsp;</td>
        </tr>
    </table></td>
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
</form>
<% if(cargarListaArchivos){%>
		<script> cambiarAccion('<%= AWAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO %>'); </script>	
<% }%>

<% 	if(cerrar){ %>
		<script> cerrarVentana(); </script>
<%		session.setAttribute("CARGAR_MESA_CONTROL", new Boolean(true));
	}
	%>