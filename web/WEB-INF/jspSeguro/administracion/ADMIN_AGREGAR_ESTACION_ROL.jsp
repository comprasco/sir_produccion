<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Usuario" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>
<%@page import="org.auriga.core.modelo.transferObjects.RelUsuRol" %>
<%@page import="org.auriga.core.modelo.transferObjects.RelUsuRolEst" %>
<%
List rolesSistema = (List)application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);
if (rolesSistema==null){
	rolesSistema=new ArrayList();
}

List estacionesSistema = (List)session.getAttribute(AWAdministracionFenrir.ESTACIONES_CIRCULO);

if (estacionesSistema==null)
	{
		estacionesSistema = (List)application.getAttribute(WebKeys.LISTA_ESTACIONES_SISTEMA);
		if (estacionesSistema==null)
		{
			estacionesSistema=new ArrayList();
		}
}

ListaElementoHelper rolesHelper = new ListaElementoHelper();
ListaElementoHelper estacionesHelper = new ListaElementoHelper();
%>


<script type="text/javascript">
function cambiarAccionAndSend(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}


function eliminarRolEstacion(estacion) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%= AWAdministracionFenrir.ELIMINAR_ESTACION_ROL %>';
	document.BUSCAR.<%= WebKeys.ESTACION %>.value = estacion;
	document.BUSCAR.submit();
}
</script>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Agregar Estaci&oacute;n a un Rol </td>
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
		<form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="<%= WebKeys.ACCION%>" id="NINGUNO" value="NINGUNO">
        <input type="hidden" name="<%= WebKeys.ROL%>" id="NINGUNO" value="NINGUNO">
        <input type="hidden" name="<%= WebKeys.ESTACION%>" id="NINGUNO" value="NINGUNO">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Informaci&oacute;n de Roles y Estaciones</td>
	                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
	                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>	
	                      </tr>
	                  </table>
	                 </td>
	                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	                </tr>
	          </table>
          </td>
          <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
        <table width="100%" class="camposform">
                <tr>
                	<td width="300" class="titulotbcentral">Roles del Sistema</td>
                	<td width="250" class="titulotbcentral">Estaciones del Sistema</td>
                	<td width="139">&nbsp;</td>
                	<td>&nbsp;</td>
                </tr>
                <tr>
                	<td>
                		<% 
                			
                			try {
		                    rolesHelper.setNombre(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
		                  	rolesHelper.setCssClase("camposformtext");
		                  	rolesHelper.setId(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
		                  	rolesHelper.setTipos(rolesSistema);
		                  	rolesHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_AGREGAR+"')\"");
							rolesHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
			 			 %> 
                	</td>
                	<td>
                		<% try {
		                    estacionesHelper.setNombre(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL_AGREGAR);
		                  	estacionesHelper.setCssClase("camposformtext");
		                  	estacionesHelper.setId(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL_AGREGAR);
		                  	estacionesHelper.setTipos(estacionesSistema);
							estacionesHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
			 			 %>
			 		</td>
			 		<td><img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.AGREGAR_ESTACION_ROL%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0" style="cursor:hand"></td>
			 		<td>&nbsp;</td>
                </tr>
          	</table>
            <table width="100%" class="camposform">
                <tr>
                  <td class="titulotbcentral" align="center" width="500">Estaciones</td>
				  <td class="titulotbcentral">Eliminar</td>
                 </tr> 
              	<%  
	               List estRols=(List)session.getAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
	               if (estRols!=null){
	                for(Iterator iter = estRols.iterator(); iter.hasNext();){
	                	ElementoLista estacion = (ElementoLista)iter.next();
                %>   
                <tr>
                  <td class="campositem" width="500"><%= estacion.getValor() %></td>
                  <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" onClick="eliminarRolEstacion('<%=estacion.getId()%>')" style="cursor:hand"></td>
                </tr>
                <% 
                	}
                 }else{%>
                 <tr>
                  <td class="campositem" width="150">Seleccione un rol</td>
                  <td width="35">&nbsp;</td>
                </tr>
                 <%}
                 %>
          	</table>
          	
          	<table width="100%" class="camposform">
                <tr>
                <img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.REGRESAR_AGREGAR_ESTACION_ROL%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0" style="cursor:hand">
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
      </form>
      
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