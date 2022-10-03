<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
List usuarios = (List)session.getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);
List elemUsuarios=new ArrayList();

boolean cargarDatos=false;
if (usuarios==null){
	cargarDatos=true;
}else{
	Iterator itUsuarios=usuarios.iterator();
	while(itUsuarios.hasNext()){
		Usuario usuario=(Usuario)itUsuarios.next();
		if (usuario.isActivo()) {
			ElementoLista elem=new ElementoLista();
			elem.setId(usuario.getUsername());
			elem.setValor(usuario.getNombreCompletoUsuario());
			elemUsuarios.add(elem);
		} else {
			//System.out.println("Usuario " + usuario.getUsername() + " Inactivo");
		}
		
	}
}

ListaElementoHelper usuariosHelper = new ListaElementoHelper();
usuariosHelper.setTipos(elemUsuarios);	
Circulo circuloPerfiles=(Circulo)session.getAttribute(WebKeys.CIRCULO);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
}

function cambiarAccionAndSend(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
}

function cargarUsuarios(text) {
        document.BUSCAR.<%= CCirculo.ID_CIRCULO%>.value=text
        document.BUSCAR.<%= WebKeys.ACCION%>.value ='<%=AWAdministracionFenrir.USUARIOS_CIRCULO%>';
        document.BUSCAR.submit();
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
      <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n de Relaciones de Usuarios</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Usuarios</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Escoger usuario a modificar</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
            </table>
                
        <form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="<%= WebKeys.ACCION%>" id="NINGUNO" value="NINGUNO">
        <input type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%=CCirculo.ID_CIRCULO%>" value="">
        <%
		try {
			usuariosHelper.setNombre(CUsuario.LOGIN_USUARIO);
			usuariosHelper.setCssClase("camposformtext");
			usuariosHelper.setId(CUsuario.LOGIN_USUARIO);
			usuariosHelper.render(request,out);
		}
		catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
        %>
        <%/**
             * @autor Geremias Ortiz
             * @requerimiento 317090 Telebucaramanga
             * @descripcion se crea un nuevo input con name TIPO_JUSTIFICACION,
             * para indicarle al formulario de justificación, que opciones
             * cargar en el SELECT de tipo de justificación.
             */
        %>
        <input  type="hidden" name="<%= CUsuario.TIPO_JUSTIFICACION  %>" value="<%= CUsuario.ROL_AGREGAR %>">
        <table width="100%" class="camposform">
          <tr>
            <td width="20">
            	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
           	</td>
            <td width="140">
            <input name="imageField" type="image" onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.REGRESAR_ESCOGER_CIRCULO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0">
            </td>
            <td>
            <img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0" style="cursor:hand">
            </td>
          </tr>
        </table>
        </form>  
            </td>
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
<%if (cargarDatos) {%>
<script>cargarUsuarios('<%=circuloPerfiles.getIdCirculo()%>');</script>
<%}%>