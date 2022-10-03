<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Collections" %>
<%@page import="java.util.Comparator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad" %>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper(); 
textHelper.setSize("45");
List tipos = (List)application.getAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD);
boolean cargarDatos=false;
if (tipos == null){
	cargarDatos=true;
	tipos = new ArrayList();
}
try{
Collections.sort(tipos,new Comparator(){
	public int compare(Object arg0, Object arg1) {
		NaturalezaJuridicaEntidad nat1=(NaturalezaJuridicaEntidad)arg0;
		NaturalezaJuridicaEntidad nat2=(NaturalezaJuridicaEntidad)arg1;
		Integer idNat1=new Integer(nat1.getIdNatJuridicaEntidad());
		Integer idNat2=new Integer(nat2.getIdNatJuridicaEntidad());
		if(nat1!=null && nat1.getIdNatJuridicaEntidad()!=null &&nat2 !=null && nat2.getIdNatJuridicaEntidad()!=null){
			return nat1.getNombre().compareTo(nat2.getNombre());
		}else{
			return nat1.getIdNatJuridicaEntidad().compareTo(nat2.getIdNatJuridicaEntidad());
			//return idNat1.compareTo(idNat2);		
		}
	}
});
}catch(Exception e){
	e.printStackTrace();
}


	boolean esEdicion = false;
	Boolean edicion = (Boolean)session.getAttribute(WebKeys.OCULTAR);
	if(edicion!=null && edicion.booleanValue()){
		esEdicion = true;
	}

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function cambiarAccionSubmit(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text
	document.BUSCAR.submit()
}
function cancelarEdicion() {
	document.ELIMINAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.DESHABILITA_EDICION_NAT_JURIDICA_ENTIDAD%>'			
	document.ELIMINAR.submit();
}
function eliminar(text){
	if (confirm("VA A ELIMINAR LA NATURALEZA CON ID "+text)){
		document.ELIMINAR.<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>.value = text
		document.ELIMINAR.submit()
	}
}
function editar(text){
	if (confirm("VA A EDITAR LA NATURALEZA CON ID "+text)){
		document.ELIMINAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.HABILITA_EDICION_NAT_JURIDICA_ENTIDAD%>'	
		document.ELIMINAR.<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>.value = text
		document.ELIMINAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administración de Tipos de Naturalezas Jurídicas de Entidades de Reparto</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Naturaleza Jur&iacute;dica para Reparto</td>
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
                    <td class="bgnsub">Naturaleza Jur&iacute;dica Reparto</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD%>">
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="150">C&oacute;digo naturaleza Jur&iacute;dica para Reparto</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
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
                    <td width="280">Nombre Naturaleza Jur&iacute;dica para Reparto </td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
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
                    <td width="280">Tipo de Cobro</td>
                    <td>
                    Exento?
                    <input type="checkbox" name="<%=CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD%>" id=<%=CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD%>>
                   	</td>
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar Naturaleza Jur&iacute;dica para Reparto</td>
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
                    <td class="bgnsub">Naturaleza Jur&iacute;dica Reparto</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
        <%
        NaturalezaJuridicaEntidad naturaleza = (NaturalezaJuridicaEntidad)session.getAttribute(CNaturalezaJuridicaEntidad.NATURALEZA_JURIDICA_ENTIDAD);
        %>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD%>">
	    <input  type="hidden" name="<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>" id="<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>" value="<%=naturaleza.getIdNatJuridicaEntidad()!=null?naturaleza.getIdNatJuridicaEntidad():""%>">                                            
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="250">C&oacute;digo naturaleza Jur&iacute;dica para Reparto</td>
                    <td class="campositem"><%=naturaleza.getIdNatJuridicaEntidad()!=null?naturaleza.getIdNatJuridicaEntidad():""%></td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="250">Nombre Naturaleza Jur&iacute;dica para Reparto </td>
                    <td>
                    <% try {
                    session.setAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD, naturaleza.getNombre());
                    textHelper.setNombre(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
                    textHelper.setSize("70");
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
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
                    <td width="250">Tipo de Cobro</td>
                    <td>
                    Exento?
                    <input type="checkbox" name="<%=CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD%>" id=<%=CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD%> <%=naturaleza.isExento()?"checked":""%>>
                   	</td>
                  </tr>
                  
				  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="250">Activo</td>
                    <td>
                    Activo?&nbsp;
                    <input type="checkbox" name="<%=CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD%>" id=<%=CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD%> <%=naturaleza.isActivo()?"checked":""%>>
                   	</td>
                  </tr>                  
                  
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                   	 <td>
                    	<a href="javascript:cancelarEdicion();"><img onClick=""  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
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
	    <br>	    
	    
	    <%}%>
	    
	    
	    <%if(tipos.size()>0){
	    %>
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
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></span></td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <form action="administracionHermod.do" method="post" name="ELIMINAR" id="ELIMINAR">
            <input  type="hidden" name="<%=WebKeys.ACCION %>" id="<%=AWAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD%>" value="<%= AWAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD %>">
	        <input  type="hidden" name="<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>" id="<%=CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD%>" value="">
	        <input  type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" value="">	        
                  		
            <table width="100%" class="camposform">
               
               
               <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>C&oacute;digo</td>
                  <td>Nombre</td>
                  <td>Exento?</td>
                  <td>Activo?</td>                  
                  <td width="90" align="center">Editar Eliminar</td>
                  
                </tr>
                <% 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	NaturalezaJuridicaEntidad dato = (NaturalezaJuridicaEntidad)iter.next();
                %>            
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%=  dato.getIdNatJuridicaEntidad() %></td>
                  <td class="campositem"><%=  dato.getNombre() %></td>
                  <%
                  String excento = "NO EXENTO";
                  if(dato.isExento()){
                  	excento = "EXENTO";
                  }
                  String activo = "INACTIVO";
                  if(dato.isActivo()){
                  	activo = "ACTIVO";
                  }                  
                  %>
					<td class="campositem"><%=  excento %></td>
					<td class="campositem"><%=  activo %></td>
                 
                 <td align="center">
        			<a href="javascript:editar('<%=dato.getIdNatJuridicaEntidad()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a>                 
        			<a href="javascript:eliminar('<%=dato.getIdNatJuridicaEntidad()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                 </td>
                  	
                </tr>
                <% 
                } 
                %> 
            </table>
            </form>
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
<%if(cargarDatos){ %>
	<script>cambiarAccionSubmit('CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO');</script>
<%}%>
