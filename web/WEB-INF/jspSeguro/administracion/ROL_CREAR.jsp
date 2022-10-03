<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>

<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CResponsabilidad" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacion" %>

<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>

<%
	// Obtengo los roles del sistema
	request.getSession().removeAttribute(CRol.ID_ROL);
	request.getSession().removeAttribute(CRol.ROL_NOMBRE);
	request.getSession().removeAttribute(CRol.ROL_DESCRIPCION);
	List tipos = (List)application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);

	TextHelper textHelper = new TextHelper();


	// Creo la lista de estaciones disponibles. Como la lista
	// que se guarda al inicio de la aplicación corresponde
	// a todas las estaciones disponibles en la base de datos,
	// se deben filtrar para que únicamente se muestren las
	// estaciones para el círculo registral actual.
	ListaElementoHelper estacionesHelper = new ListaElementoHelper();
	estacionesHelper.setTipoMultiple(true); 
	List estaciones = (List)session.getAttribute(WebKeys.LISTA_ESTACIONES_CIRCULO);

	List liEstaciones = new ArrayList();

	if(estaciones != null) {
		Iterator iEstaciones = estaciones.iterator();
		Estacion eEstacion = null;
		ElementoLista elElemento = null;
	
		while(iEstaciones.hasNext()) {
			eEstacion = (Estacion)iEstaciones.next();
			elElemento = new ElementoLista(eEstacion.getEstacionId(), eEstacion.getNombre());
			liEstaciones.add(elElemento);
		}
	}
	
	estacionesHelper.setTipos(liEstaciones);
%>


<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function editarRol(text) {
	document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionFenrir.CONSULTAR_ROL_EDITAR%>';
	document.BUSCAR.<%=AWAdministracionFenrir.CONSULTAR_ROL_EDITAR%>.value = text;
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Crear Roles </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar una Responsabilidad </td>
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
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos B&aacute;sicos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
            <form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionFenrir.ROL_CREAR  %>" value="<%= AWAdministracionFenrir.ROL_CREAR   %>">
        <input  type="hidden" name="<%=AWAdministracionFenrir.CONSULTAR_ROL_EDITAR%>" id="<%=AWAdministracionFenrir.CONSULTAR_ROL_EDITAR%>" value="NINGUNO">
        
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Identificador</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CRol.ID_ROL );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CRol.ID_ROL);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Nombre</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CRol.ROL_NOMBRE );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CRol.ROL_NOMBRE);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Descripci&oacute;n</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CRol.ROL_DESCRIPCION );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CRol.ROL_DESCRIPCION);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>  
                </table>
                
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Estaciones para el Rol</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td class="campostip04">Oprima CTRL para hacer una selecci&oacute;n multiple. </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>
                    <% try {
                    estacionesHelper.setNombre(CEstacion.ESTACION_ID);
                  	estacionesHelper.setCssClase("camposformtext");
                  	estacionesHelper.setId(CEstacion.ESTACION_ID);
					estacionesHelper.render(request,out);
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
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de crear Roles haga click en aceptar. </td>
                  </tr>
                </table>
                
               <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionFenrir.ROL_CREAR%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionFenrir.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
             </FORM>   
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
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
	             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
	              <td>Listado</td>
	              <td>&nbsp;</td>
	              <td>&nbsp;</td>
	            </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">ID</td>
                  <td class="titulotbcentral">Nombre del Rol </td>
                  <td class="titulotbcentral">Editar</td>
                 </tr> 
              	<%  
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	ElementoLista dato = (ElementoLista)iter.next();
                %>   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato.getId() %></td>
                  <td class="campositem"><%= dato.getValor() %></td>
                  <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" onClick="editarRol('<%=dato.getId()%>')" style="cursor:hand"></td>
                </tr>
                <% 
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