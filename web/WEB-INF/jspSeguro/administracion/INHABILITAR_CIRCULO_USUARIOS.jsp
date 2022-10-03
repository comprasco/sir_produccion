<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>


<%

List usuarios = (List)session.getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);

if (usuarios==null){
	usuarios=new ArrayList();
}

List usuariosTrasladar = (List)session.getAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);

if (usuariosTrasladar==null){
	usuariosTrasladar=new ArrayList();
}

List usuariosCreados = (List)session.getAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);

if (usuariosCreados==null){
	usuariosCreados=new ArrayList();
}

TextHelper textHelper = new TextHelper();

ListaElementoHelper rolesHelper = new ListaElementoHelper();
rolesHelper.setTipoMultiple(true); 
List roles = (List)application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);

List temp = new java.util.ArrayList();
java.util.Iterator itTemp = roles.iterator();
while(itTemp.hasNext()){
	ElementoLista rol = (ElementoLista)itTemp.next();
	
	if(!
		(
		rol.getId().equals(CRoles.SIR_ROL_DECISOR) ||
		rol.getId().equals(CRoles.SIR_ROL_IMPRESION)
		)
		){
			temp.add(rol);
	}

}

rolesHelper.setTipos(temp);

%>


<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function moverUsuario(text) {
	document.BUSCAR.<%= AWAdministracionForseti.USUARIOS_MOVER %>.value=text;
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=AWAdministracionForseti.USUARIOS_MOVER%>';
}

function eliminarUsuarioCreado(text) {
	document.BUSCAR.<%= AWAdministracionForseti.USUARIOS_MOVER %>.value=text;
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=AWAdministracionForseti.ELIMINAR_USUARIOS_CREADOS%>';
}

function eliminarUsuarioTrasladado(text) {
	document.BUSCAR.<%= AWAdministracionForseti.USUARIOS_MOVER %>.value=text;
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=AWAdministracionForseti.ELIMINAR_USUARIOS_TRASLADADOS%>';
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Crear Usuarios </td>
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar un Usuario </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
          
        <form action="administracionForseti.do" method="get" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionFenrir.USUARIOS_CREAR %>" value="<%= AWAdministracionFenrir.USUARIOS_CREAR %>">
        <input  type="hidden" name="<%= AWAdministracionForseti.USUARIOS_MOVER %>" id="<%= AWAdministracionForseti.USUARIOS_MOVER %>" value="NINGUNO">
		
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos B&aacute;sicos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
               </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Nombre del Usuario en el Sistema</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.LOGIN_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.LOGIN_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="105">Contrase&ntilde;a</td>
                    <td>
                    <input id="<%=  CUsuario.CLAVE_USUARIO  %>" size="" name="<%=  CUsuario.CLAVE_USUARIO  %>"  type="password" maxlength="" class="camposformtext_noCase"  > </td>
                  </tr>
                   <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Nombre</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.NOMBRE_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.NOMBRE_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="105">Apellido 1</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.APELLIDO1_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.APELLIDO1_USUARIO);
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
                    <td width="160">Apellido 2</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.APELLIDO2_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.APELLIDO2_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Roles para el Usuario </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td class="campostip04">Oprima CTRL para hacer una selecci&oacute;n multiple. </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>
                    <% 
               try {
                    rolesHelper.setNombre(CRol.ID_ROL);
                  	rolesHelper.setCssClase("camposformtext");
                  	rolesHelper.setId(CRol.ID_ROL);
                  	rolesHelper.setShowInstruccion(false);
					rolesHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %> 
                    </td>
                  </tr>

                </table>


				<table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.VOLVER_USUARIO_CIRCULO_INHAB %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></td>
                  <td width="140"><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.USUARIOS_CREAR_INHAB %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_asociar.gif" width="139" height="21" border="0"></td>
				  <td width="140"><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINAR_USUARIO_CIRCULO_INHAB %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0"></td>
				  <td>&nbsp;</td>
                </tr>
              </table>

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td class="bgnsub">Usuarios Creados</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>

			<table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Nombre</td>
                  <td>Nombre de usuario</td>
                  <td align="center">Eliminar</td>
                </tr>
			 <%
			 
			if (usuariosCreados != null){
				for(Iterator iter = usuariosCreados.iterator(); iter.hasNext();){
					Usuario usuario = (Usuario)iter.next();
			%>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= usuario.getNombre() + " " + ((usuario.getApellido1()==null)?"":usuario.getApellido1())+ " " + ((usuario.getApellido2()==null)?"":usuario.getApellido2())  %></td>
                  <td class="camposformtext_noCase"><%= usuario.getUsername()   %></td>
                  <td width="40" align="center">
                  		<input name="imageField" type="image" onClick="eliminarUsuarioCreado('<%= usuario.getUsername()%>');" src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0">
                  </td>
                </tr>
				<%
					}
				}
				%>
              </table>

	

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td class="bgnsub">Usuarios Trasladados</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>

			<table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Nombre</td>
                  <td>Nombre de usuario</td>
                  <td align="center">Eliminar</td>
                </tr>
			 <%
			 
			if (usuariosTrasladar != null){
				for(Iterator iter =  usuariosTrasladar.iterator(); iter.hasNext();){
					Usuario usuario = (Usuario)iter.next();
			%>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= usuario.getNombre() + " " + ((usuario.getApellido1()==null)?"":usuario.getApellido1())+ " " + ((usuario.getApellido2()==null)?"":usuario.getApellido2())  %></td>
                  <td class="camposformtext_noCase"><%= usuario.getUsername()   %></td>
                  <td width="40" align="center">
                  		<input name="imageField" type="image" onClick="eliminarUsuarioTrasladado('<%= usuario.getUsername()%>');" src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0">
                  </td>
                </tr>
				<%
					}
				}
				%>
              </table>

            </td>
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
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
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
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td class="bgnsub">Trasladar usuarios</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>

			<table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Nombre</td>
                  <td>Nombre de usuario</td>
                  <td align="center">Mover</td>
                </tr>
			 <%
			 
			if (usuarios != null){
				for(Iterator iter = usuarios.iterator(); iter.hasNext();){
					Usuario usuario = (Usuario)iter.next();
			%>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= usuario.getNombre() + " " + ((usuario.getApellido1()==null)?"":usuario.getApellido1())+ " " + ((usuario.getApellido2()==null)?"":usuario.getApellido2())  %></td>
                  <td class="camposformtext_noCase"><%= usuario.getUsername()   %></td>
                  <td width="40" align="center">
                  		<input name="imageField" type="image" onClick="moverUsuario('<%= usuario.getUsername()%>');" src="<%=request.getContextPath()%>/jsp/images/btn_mini_agregar.gif" width="35" height="13" border="0">
                  </td>
                </tr>
				<%
					}
				}
				%>
              </table>
              

              </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr></table>
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