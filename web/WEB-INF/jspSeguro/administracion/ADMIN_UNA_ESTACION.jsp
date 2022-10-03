<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacion" %>
<%@page import="gov.sir.core.negocio.modelo.EstacionRecibo" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="org.auriga.core.modelo.transferObjects.RelRolEstacion" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacionRecibo" %>

<%
//LEER VALORES DE SESSION
String idCirculo=(String)session.getAttribute(CCirculo.ID_CIRCULO);
String idEstacion=(String)session.getAttribute(CEstacion.ESTACION_ID);
Estacion estacion=(Estacion)session.getAttribute(WebKeys.ESTACION);
EstacionRecibo eRecibo=(EstacionRecibo)session.getAttribute(WebKeys.ESTACION_RECIBO);
List potRoles=(List)session.getAttribute(WebKeys.ESTACION_ROLES_POTENCIALES);
List usuarios=(List)session.getAttribute(WebKeys.ESTACION_USUARIOS);
List potUsuarios=(List)session.getAttribute(WebKeys.ESTACION_USUARIOS_POTENCIALES);

//LISTA DE ROLES
List rolesEL=new Vector();
Iterator it=estacion.getRelRolEstacions().iterator();
while(it.hasNext()) {
	RelRolEstacion rre=(RelRolEstacion)it.next();
	Rol rol=rre.getRol();
	ElementoLista el=new ElementoLista();
	el.setId(rol.getRolId());
	el.setValor(rol.getNombre());
	rolesEL.add(el);
}

//LISTA DE ROLES POTENCIALES
List potRolesEL=new Vector();
it=potRoles.iterator();
while(it.hasNext()) {
	Rol rol=(Rol)it.next();
	ElementoLista el=new ElementoLista();
	el.setId(rol.getRolId());
	el.setValor(rol.getNombre());
	potRolesEL.add(el);
}

//LISTA DE USUARIOS
List usuariosEL=new Vector();
it=usuarios.iterator();
while(it.hasNext()) {
	Usuario usuario=(Usuario)it.next();
	ElementoLista el=new ElementoLista();
	el.setId(String.valueOf(usuario.getIdUsuario()));
	el.setValor(usuario.getNombreCompletoUsuario());
	usuariosEL.add(el);
}

//LISTA DE USUARIOS POTENCIALES
List potUsuariosEL=new Vector();
it=potUsuarios.iterator();
while(it.hasNext()) {
	Usuario usuario=(Usuario)it.next();
	ElementoLista el=new ElementoLista();
	el.setId(String.valueOf(usuario.getIdUsuario()));
	el.setValor(usuario.getNombreCompletoUsuario());
	potUsuariosEL.add(el);
}
//LISTA HELPERS
//ROLES
ListaElementoHelper lehRoles=new ListaElementoHelper();
lehRoles.setTamanoLista(8);
lehRoles.setTipoMultiple(true);
lehRoles.setShowInstruccion(false);
lehRoles.setTipos(rolesEL);
lehRoles.setNombre(WebKeys.EX_ROLES_ESTACION);
lehRoles.setCssClase("camposformListas");
lehRoles.setId(WebKeys.EX_ROLES_ESTACION);
lehRoles.setWidth("150");
//POTROLES
ListaElementoHelper lehPotRoles=new ListaElementoHelper();
lehPotRoles.setTamanoLista(8);
lehPotRoles.setTipoMultiple(true);
lehPotRoles.setShowInstruccion(false);
lehPotRoles.setTipos(potRolesEL);
lehPotRoles.setNombre(WebKeys.NEW_ROLES_ESTACION);
lehPotRoles.setCssClase("camposformListas");
lehPotRoles.setId(WebKeys.NEW_ROLES_ESTACION);
lehPotRoles.setWidth("150");
//USUARIOS
ListaElementoHelper lehUsuarios=new ListaElementoHelper();
lehUsuarios.setTamanoLista(8);
lehUsuarios.setTipoMultiple(true);
lehUsuarios.setShowInstruccion(false);
lehUsuarios.setTipos(usuariosEL);
lehUsuarios.setNombre(WebKeys.EX_USUARIOS_ESTACION);
lehUsuarios.setCssClase("camposformListas");
lehUsuarios.setId(WebKeys.EX_USUARIOS_ESTACION);
lehUsuarios.setWidth("150");
//USUARIOS POTENCIALES
ListaElementoHelper lehPotUsuarios=new ListaElementoHelper();
lehPotUsuarios.setTamanoLista(8);
lehPotUsuarios.setTipoMultiple(true);
lehPotUsuarios.setShowInstruccion(false);
lehPotUsuarios.setTipos(potUsuariosEL);
lehPotUsuarios.setNombre(WebKeys.NEW_USUARIOS_ESTACION);
lehPotUsuarios.setCssClase("camposformListas");
lehPotUsuarios.setId(WebKeys.NEW_USUARIOS_ESTACION);
lehPotUsuarios.setWidth("150");

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>

<script type="text/javascript">
function cambiarAccion(text) {
	document.RECIBO.<%= WebKeys.ACCION %>.value = text;
	document.RECIBO.submit();
}

function sendRecibo(text) {
	document.RECIBO.<%= WebKeys.ACCION %>.value = text;
	document.RECIBO.submit();
}

function sendRoles(text) {
	document.ROLES.<%= WebKeys.ACCION %>.value = text;
	document.ROLES.submit();
}

function sendUsuarios(text) {
	document.USUARIOS.<%= WebKeys.ACCION %>.value = text;
	document.USUARIOS.submit();
}

</script>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Estaci&oacute;n </td>
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Configuraci&oacute;n</td>
                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estacion.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
              </table></td>
              <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
            </tr>
            <tr>
              <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
              <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
                <tr>
                  <td width="100" class="titulotbcentral">C&iacute;rculo</td>
                  <td class="campositem"><%=idCirculo%>&nbsp;</td>
                </tr>
                <tr>
                  <td class="titulotbcentral">Estaci&oacute;n</td>
                  <td class="campositem"><%=idEstacion%>&nbsp;</td>
                </tr>
              </table>
                <hr class="linehorizontal">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Recibos</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                  <form name="RECIBO" method="post" action="administracionHermod.do">
                    <input type="hidden" name="<%=WebKeys.ACCION%>" value="">
                    <table width="100%" class="camposform">
                      <tr>
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td width="50">Desde </td>
                        <td width="250">
                            <input name="<%=CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO%>" type="text" class="camposformtext" id="<%=CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO%>" size="40" value="<%=eRecibo.getNumeroInicial()%>"></td>
                        <td width="50">Hasta</td>
                        <td>
                            <input name="<%=CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO%>" type="text" class="camposformtext" id="<%=CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO%>" size="40" value="<%=eRecibo.getNumeroFinal()%>"></td>
                        <td>
                            <input type="image" name="imageGuardarRecibos" onClick="sendRecibo('<%=AWAdministracionHermod.SET_ESTACION_RECIBO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" width="139" height="21" border="0">
                        </td>
                      </tr>
                    </table>
                  </form>
                                    <hr class="linehorizontal">
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                      <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                      <tr>
                                        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                        <td class="bgnsub">Roles</td>
                                        <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                      </tr>
                                    </table>
                 <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Roles</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td class="campostip04">Seleccione de las listas para agregar o quitar roles. </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>        
                          <form name="ROLES" method="post" action="administracionHermod.do">
                            <input type="hidden" name="<%=WebKeys.ACCION%>" value="">
	                        <table width="100%"  border="0" cellspacing="1" cellpadding="0">
	                          <tr class="titulotbcentral">
	                            <td align="center" width="50%">Roles de la estaci&oacute;n</td>
	                            <td width="35">&nbsp;</td>
	                            <td align="center" width="50%">Otros roles</td>
	                          </tr>
	                          <tr>
	                            <td align="center">
	                              <%lehRoles.render(request,out);%>
	                            </td>
	                            <td width="35">
	                              <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_pasar_atras.gif" 
	                                onClick="sendRoles('<%=AWAdministracionHermod.ADD_ROLES_ESTACION%>')" width="35" height="13"><br>
	                              <br>
								  <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_pasar_adelante.gif" 
								    onClick="sendRoles('<%=AWAdministracionHermod.REMOVE_ROLES_ESTACION%>')" width="35" height="13">
								</td>
	                            <td align="center">
	                              <%lehPotRoles.render(request,out);%>
	                            </td>
	                          </tr>
	                        </table>
                          </form>
                      </td>
                    </tr>
                  </table>
                  <hr class="linehorizontal">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                    <tr>
                      <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                      <td class="bgnsub">Usuarios</td>
                      <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                      <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                    </tr>
                  </table>




                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Usuarios</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td class="campostip04">Seleccione de las listas para agregar o quitar usuarios </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>
                          <form name="USUARIOS" method="post" action="administracionHermod.do">
                            <input type="hidden" name="<%=WebKeys.ACCION%>" value="">
                      
                      <table width="100%"  border="0" cellspacing="1" cellpadding="0">
                          <tr class="titulotbcentral">
                            <td align="center" width="50%">Usuarios de la estaci&oacute;n</td>
                            <td width="35">&nbsp;</td>
                            <td align="center" width="50%">Otros usuarios de los roles permitidos y del mismo c&iacute;rculo</td>
                        </tr>
                          <tr>
                            <td align="center">
                              <%lehUsuarios.render(request,out);%>
                            </td>
                            <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_pasar_atras.gif" 
                            	onClick="sendUsuarios('<%=AWAdministracionHermod.ADD_USUARIOS_ESTACION%>')" width="35" height="13"><br>
                                <br>
                                <img src="<%=request.getContextPath()%>/jsp/images/btn_pasar_adelante.gif" 
                                onClick="sendUsuarios('<%=AWAdministracionHermod.REMOVE_USUARIOS_ESTACION%>')" width="35" height="13"></td>
                            <td align="center">
                              <%lehPotUsuarios.render(request,out);%>
                            </td>
                          </tr>
                      </table></td>
                      
                      </form>
                    </tr>
                  </table>
 


                  <table width="100%" class="camposform">
                    <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                      <td width="140"><input name="imageField" type="image"  onClick="cambiarAccion('<%=AWAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO%>');" src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0"></td>
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