<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCheckItem" %>
<%@page import="gov.sir.core.negocio.modelo.CheckItem" %> 
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud" %> 
<%@page import="org.auriga.core.web.HelperException" %>

<%
TextHelper textHelper = new TextHelper();

List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
if(tipos == null){
	tipos = new ArrayList();
}

List subtipos = (List)application.getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
List elementos = new ArrayList();
for (Iterator iter = subtipos.iterator(); iter.hasNext();) {
		SubtipoSolicitud dato = (SubtipoSolicitud) iter.next();
		elementos.add(new ElementoLista(dato.getIdSubtipoSol(), dato.getNombre()));
	}
ListaElementoHelper subtipoSolicitudHelper = new ListaElementoHelper();
subtipoSolicitudHelper.setTipos(elementos);	

boolean edicion=false;
Boolean obligatoriedad=(Boolean)session.getAttribute(CCheckItem.CHECK_ITEM_OBLIGATORIEDAD_EDICION);
if(obligatoriedad!=null){
	edicion=true;
}

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionAndSend(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n Items de Chequeo</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Items de Chequeo</td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Items de Chequeo</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD %>" value="<%=  AWAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Subtipo de Solicitud </td>
                    <td>
			  <% try {
                    subtipoSolicitudHelper.setNombre(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
                  	subtipoSolicitudHelper.setCssClase("camposformtext");
                  	subtipoSolicitudHelper.setId(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
                  	subtipoSolicitudHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD+"')\"");
					subtipoSolicitudHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="220">Identificador</td>
                    <td>
               	<% try {
                    textHelper.setNombre(CCheckItem.CHECK_ITEM_ID);
                  	textHelper.setCssClase("camposformtext");
					if(edicion){
						textHelper.setEditable(false);	
					}
                  	textHelper.setId(CCheckItem.CHECK_ITEM_ID);
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
                    textHelper.setNombre(CCheckItem.CHECK_ITEM_NOMBRE);
                  	textHelper.setCssClase("camposformtext");
					textHelper.setEditable(true);
                  	textHelper.setId(CCheckItem.CHECK_ITEM_NOMBRE);
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
                    <td>Obligatoriedad</td>
						<%if(!edicion){%>
                    		<td class="camposformtext">
                    		<input name="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>" value="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>"  type="checkbox" id="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>"  >
                    		</td>
						<%}else{
							if(obligatoriedad.booleanValue()){%>
								<td class="camposformtext">
                    			<input name="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>" value="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>"  type="checkbox" id="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>" checked="<%= obligatoriedad.toString()%>"  >
                    			</td>
							<%}else{%>
								<td class="camposformtext">
                    			<input name="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>" value="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>"  type="checkbox" id="<%= CCheckItem.CHECK_ITEM_OBLIGATORIEDAD %>"  >
                    			</td>
							<%}%>
						<%}%>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
					<%if(edicion){%>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.EDITA_CHECK_ITEM %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    	</td>
                    <td>
					<%}else{%>
					<td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.ADICIONA_CHECK_ITEM %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    	</td>
                    <td>
					<%}%>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td width="50">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">ID</td>
                  <td class="titulotbcentral">Nombre</td>
				  <td class="titulotbcentral">Obligatoriedad</td>
                  <td width="50" align="center">Eliminar</td>
				  <td width="50" align="center">Editar</td>
                </tr>
                
                 <%
                 int idItem =0; 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	CheckItem dato = (CheckItem)iter.next(); 
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=dato.getIdCheckItem()%></td>
                  <td class="camposformtext_noCase"><%= dato.getNombre()%></td>
				  <% if(dato.isObligatorio()){%>
				  	<td class="camposformtext_noCase">SI</td>
				  <% }else{ %>
					<td class="camposformtext_noCase">NO</td>
				  <% } %>
                  <form action="administracionHermod.do" method="post" name="ELIMINARITEM<%=idItem%>" id="ELIMINARITEM<%=idItem%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_CHECK_ITEM %>" value="<%= AWAdministracionHermod.ELIMINA_CHECK_ITEM %>">
	                	<input  type="hidden" name="<%= CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD%>" id="<%= dato.getSubtipoSolicitud().getIdSubtipoSol() %>" value="<%= dato.getSubtipoSolicitud().getIdSubtipoSol()   %>">
	                	<input  type="hidden" name="<%= CCheckItem.CHECK_ITEM_ID%>" id="<%= dato.getIdCheckItem()%>" value="<%= dato.getIdCheckItem()  %>">
                  		<a href="javascript:validarEliminacion('<%=idItem%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                  		
                  </td>
                  </form>
				  <form action="administracionHermod.do" method="post" name="EDITARITEM<%=idItem%>" id="EDITARITEM<%=idItem%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.EDITAR_CHECK_ITEM %>" value="<%= AWAdministracionHermod.EDITAR_CHECK_ITEM %>">
	                	<input  type="hidden" name="<%= CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD%>" id="<%= dato.getSubtipoSolicitud().getIdSubtipoSol() %>" value="<%= dato.getSubtipoSolicitud().getIdSubtipoSol()   %>">
	                	<input  type="hidden" name="<%= CCheckItem.CHECK_ITEM_ID%>" id="<%= dato.getIdCheckItem()%>" value="<%= dato.getIdCheckItem()  %>">
                  		<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0">
                  		
                  </td>
                  </form>
                </tr>
                <% 
                idItem ++;
                 }
                 %>
            </table></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
        </table></td>
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

<script>
function validarEliminacion(nombre) {

    alert ('Va a eliminar un Item de Chequeo');
	if (confirm('Esta seguro que desea eliminar el Item de Chequeo'))
	{
     
      eval('document.ELIMINARITEM' +nombre + '.submit()');
	}
}
</script>