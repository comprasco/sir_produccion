<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>

<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.RolPantalla" %>
<%@page import="gov.sir.core.negocio.modelo.PantallaAdministrativa" %>
<%@page import="gov.sir.core.negocio.modelo.RolSIR" %>

<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>

<%
	// Obtengo los roles del sistema
	List roles = (List)application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);
	List pantallas = (List)session.getAttribute(AWAdministracionHermod.LISTA_PANTALLAS_ADMINISTRATIVAS);
	
	List rolesPantallas = (List)session.getAttribute(AWAdministracionHermod.LISTA_ROLES_PANTALLAS);
	TextHelper textHelper = new TextHelper();
	ListaElementoHelper rolesHelper = new ListaElementoHelper();

%>


<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function eliminarRolPantalla(text) {
	document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.ELIMINAR_ROL_PANTALLA%>';
	document.BUSCAR.<%=AWAdministracionHermod.ROL_PANTALLA_A_ELIMINAR%>.value = text;
	document.BUSCAR.submit();
}

function send(){
   document.forma.submit();
}
</script>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<%
if (rolesPantallas==null){
%>
	<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	    <form action="administracionHermod.do" method="POST" name="forma"  id="forma">
	  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWAdministracionHermod.CARGAR_ROLES_PANTALLAS%>" value="<%=AWAdministracionHermod.CARGAR_ROLES_PANTALLAS%>">
		</form>
<%
}
%>


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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Asignar Roles a Reportes</td>
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

            <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.AGREGAR_ROL_PANTALLA  %>" value="<%= AWAdministracionHermod.AGREGAR_ROL_PANTALLA   %>">
        <input  type="hidden" name="<%= AWAdministracionHermod.ROL_PANTALLA_A_ELIMINAR %>" id="<%=  AWAdministracionHermod.ROL_PANTALLA_A_ELIMINAR  %>" value="">
        
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
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Datos b&aacute;sicos</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr> 
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">Seleccione un Rol y un Reporte a asignar </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        
                    <%
                    if(roles!=null && !roles.isEmpty()){
                    %>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="20%" align='right'>
                    	<b>Rol:</b>
                    </td>
                    <td >                   	
                    <% 
                        try {
                            rolesHelper.setNombre(AWAdministracionHermod.ROL_ASIGNAR);
                            rolesHelper.setCssClase("camposformtext");
                            rolesHelper.setId(AWAdministracionHermod.ROL_ASIGNAR);
                            rolesHelper.setTipos(roles);
                            rolesHelper.render(request,out);
                        }
                        catch(HelperException re){
                                out.println("ERROR " + re.getMessage());
                        }
					
					%>                    	
                    </td>
                     <%
                    } else {
                    %>
                     <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existen roles en el sistema.</b>
                    </td>
                    <%}%>
                    <%
                	if(pantallas!=null && !pantallas.isEmpty()){
                    %>
                    <td width="40%" align='right'>                    	
                    	<b>Reporte:</b>
                    </td>
                    <td >                   	
                    <% 
                    try {
                        List pantallaElementoLista = new ArrayList();
                        java.util.Iterator it = pantallas.iterator();
                        while(it.hasNext()){
                            PantallaAdministrativa pantalla = (PantallaAdministrativa)it.next();
                            ElementoLista elemento = new ElementoLista();
                            elemento.setId(""+pantalla.getIdPantallaAdministrativa());
                            elemento.setValor(pantalla.getNombre());
                            pantallaElementoLista.add(elemento);
                        }
                
                        ListaElementoHelper listaHelper= new ListaElementoHelper();
                        listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
                        listaHelper.setTipos(pantallaElementoLista);
                        listaHelper.setId(AWAdministracionHermod.PANTALLA_ASIGNAR);
                        listaHelper.setNombre(AWAdministracionHermod.PANTALLA_ASIGNAR);
                        listaHelper.setCssClase("camposformtext");
                        listaHelper.render(request,out);
                    }
                        catch(HelperException re){
                        out.println("ERROR " + re.getMessage());
                    }
                    %>                    	
                    </td>
                    <%
                    } else {
                    %>
                     <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existen reportes en el sistema.</b>
                    </td>
                <%}%>
                  </tr>
                </table>
                  

                <hr class="linehorizontal">
                
               <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.AGREGAR_ROL_PANTALLA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
                
                <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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
       <tr>
       <td>&nbsp;</td>
       <td>&nbsp;</td>              
	    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <table width="100%" class="camposform">
	      		<tr>
	             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
	              <td>Reportes asigandos a Roles</td>
	              <td>&nbsp;</td>
	              <td>&nbsp;</td>
	            </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">Rol</td>
                  <td class="titulotbcentral">Reportes</td>
                  <td class="titulotbcentral">Eliminar</td>
                 </tr> 
              	<%  
              	if (rolesPantallas != null)
              	{
                for(Iterator iter = rolesPantallas.iterator(); iter.hasNext();){
                	RolPantalla dato = (RolPantalla)iter.next();
                %>   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato.getRol().getNombre() %></td>
                  <td class="campositem"><%= dato.getPantalla().getNombre() %></td>
                  <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" onClick="javascript: eliminarRolPantalla('<%=dato.getPantalla().getIdPantallaAdministrativa() + "-" + dato.getRol().getIdRol()%>');" style="cursor:hand"></td>
                </tr>
                <% 
                 }
                 }
                 %>
          	</table>
          </td>
    </tr>
</table>