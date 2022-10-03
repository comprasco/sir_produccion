<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEntidadPublica" %>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad"%>
<%@page import="gov.sir.core.negocio.modelo.EntidadPublica"%>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad" %>

<%
	TextHelper textHelper = new TextHelper(); 
	List natsJuridicas = (List)application.getAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS);
	if (natsJuridicas == null){
		natsJuridicas = new ArrayList();
	}
	List tipos = (List) application.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
	if (tipos == null){
		tipos = new ArrayList();
	}
	List tiposNats = new ArrayList();
	Iterator itNats = natsJuridicas.iterator();
	while(itNats.hasNext()){
		NaturalezaJuridicaEntidad nat = (NaturalezaJuridicaEntidad) itNats.next();
		tiposNats.add(new ElementoLista(nat.getIdNatJuridicaEntidad(),nat.getNombre()));
	}
	ListaElementoHelper helper = new ListaElementoHelper();
	helper.setTipos(tiposNats);
	
	boolean esEdicion = false;
	Boolean edicion = (Boolean)session.getAttribute(WebKeys.OCULTAR);
	if(edicion!=null && edicion.booleanValue()){
		esEdicion = true;
	}

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">


<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function recargarEntidades() {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=AWAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA%>';
	document.BUSCAR.submit();	
}
function cancelarEdicion() {
	document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.DESHABILITA_EDICION_ENTIDAD_PUBLICA%>'			
	document.BUSCAR.submit();
}
function eliminar(text){
	if (confirm("VA A ELIMINAR LA ENTIDAD CON ID "+text)){
		document.BUSCAR.<%=CEntidadPublica.ID_ENTIDAD_PUBLICA%>.value = text
		document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA%>'			
		document.BUSCAR.submit()
	}
}
function editar(text){
	if (confirm("VA A EDITAR LA ENTIDAD CON ID "+text)){
		document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.HABILITA_EDICION_ENTIDAD_PUBLICA%>'	
		document.BUSCAR.<%=CEntidadPublica.ID_ENTIDAD_PUBLICA%>.value = text
		document.BUSCAR.submit();
	}
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n Entidades P&uacute;blicas</td>
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
  
  		<%
  		if(!esEdicion){
  		%>
  
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Entidad P&uacute;blica para Reparto</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
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
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Entidad P&uacute;blica Reparto</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA%>">
 	    <input  type="hidden" name="<%=CEntidadPublica.ID_ENTIDAD_PUBLICA%>" id="<%= CEntidadPublica.ID_ENTIDAD_PUBLICA%>">                
      
           <table width="100%" class="camposform">

                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="280">Nombre Entidad P&uacute;blica para Reparto </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setSize("45");                  	
                  	textHelper.setId(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
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
                    <td width="280">Naturaleza Jur&iacute;dica</td>
                    <td>
                   	<% try {
	                    helper.setNombre(gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
	                  	helper.setCssClase("camposformtext");
	                  	helper.setFuncion("onchange=\"recargarEntidades()\"");
	                  	helper.setId(gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
						helper.render(request,out);
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
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
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
	    <br>
	    <%
	    }else{
	    %>
	    
	    
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar Entidad P&uacute;blica</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
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
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Entidad P&uacute;blica Reparto</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>

        <%
        EntidadPublica entidad = (EntidadPublica)session.getAttribute(CEntidadPublica.ENTIDAD_PUBLICA);
        %>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWAdministracionHermod.EDITA_ENTIDAD_PUBLICA%>">
 	    <input  type="hidden" name="<%=CEntidadPublica.ID_ENTIDAD_PUBLICA%>" id="<%= CEntidadPublica.ID_ENTIDAD_PUBLICA%>" value="<%=entidad.getIdEntidadPublica()!=null?entidad.getIdEntidadPublica():""%>">                
                        
           <table width="100%" class="camposform">
           
				 <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="280">Id entidad</td>
					<td class="campositem"><%=entidad.getIdEntidadPublica()!=null?entidad.getIdEntidadPublica():""%>&nbsp;</td>
                  </tr>                

                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="280">Nombre Entidad P&uacute;blica para Reparto </td>
                    <td>
                    <% try {
                    session.setAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA, entidad.getNombre());
                    textHelper.setNombre(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setSize("45");                  	
                  	textHelper.setId(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
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
                    <td width="280">Naturaleza Jur&iacute;dica</td>
                    <td>
                   	<% try {
                   		
                 	
	                    if(session.getAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD)==null){
						   session.setAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD, entidad.getNaturalezaJuridica().getIdNatJuridicaEntidad());
	                    }                   	
                   	 
	                    helper.setNombre(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);  
	                  	helper.setCssClase("camposformtext");
	                  	helper.setFuncion("onchange=\"recargarEntidades()\"");
	                  	helper.setId(gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
						helper.render(request,out);
						}
						catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
				    %>
                   	</td>
                  </tr>
                  
				 <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="280">Activo ?</td>
                    <td>
                          <input type='checkbox' name="<%=CEntidadPublica.ACTIVA_ENTIDAD_PUBLICA%>" <%=entidad.isActivo()?"checked":""%> >
                   	</td>
                  </tr>                  
                  
                  

                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.EDITA_ENTIDAD_PUBLICA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                   	 <td>
                    	<a href="javascript:cancelarEdicion();"><img onClick=""  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
                   	</td>
                   	
                   	

                   	
                  </tr>
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
	    <br>
	    <%}%>
	    
	    
	    	    
	    
	    <%if(tipos.size()>0){%>
	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
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
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
               
               
               <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>C&oacute;digo</td>
                  <td>Nombre</td>
                  <td>Naturaleza J&uacute;ridica</td>                  
                  <td>Activo?</td>                                    
                  <td width="90" align="center">Editar Eliminar</td>
                </tr>

               <% 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	EntidadPublica dato = (EntidadPublica)iter.next();
                %>            
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%=dato.getIdEntidadPublica() %></td>
                  <td class="campositem"><%=dato.getNombre()%></td>                  
                  <td class="campositem"><%=dato.getNaturalezaJuridica().getNombre()%></td>                    
                  <td class="campositem"><%=dato.isActivo()?"ACTIVO":"INACTIVO"%></td>                    
                  <td align="center">
        			    <a href="javascript:editar('<%=dato.getIdEntidadPublica()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a>                 	                	
        			    <a href="javascript:eliminar('<%=dato.getIdEntidadPublica()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>        			    
                  </td>

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
        <%}%>
        
        
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