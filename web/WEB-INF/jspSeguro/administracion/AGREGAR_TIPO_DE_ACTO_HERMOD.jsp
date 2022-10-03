<%@page import="java.util.List" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoActo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoCalculo" %>
<%@page import="gov.sir.core.negocio.modelo.TipoDerechoReg" %>
<%@page import="gov.sir.core.negocio.modelo.TipoActoDerechoRegistral" %>
<%@page import="gov.sir.core.negocio.modelo.TipoActo" %>
<%@page import="gov.sir.core.negocio.modelo.TipoCalculo" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
TextHelper textHelper = new TextHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS);
session.setAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS, tipos);

List tiposCalculo = (List)application.getAttribute(WebKeys.LISTA_TIPOS_CALCULO);
ListaElementoHelper tiposCalculoHelper = new ListaElementoHelper();


List tiposDerechoRegistral = (List)application.getAttribute(WebKeys.LISTA_TIPOS_DERECHO_REGISTRAL);

String flag = (String)session.getAttribute(CTipoActo.IMPUESTO_CUANTIA_EDICION);
boolean edicion=false;
if(flag!=null){	
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Actos HERMOD </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Tipo de Acto </td>
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
                    <td class="bgnsub">Tipo de Acto </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
       <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_TIPO_ACTO %>" value="<%= AWAdministracionHermod.ADICIONA_TIPO_ACTO %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&oacute;digo del Tipo de Acto </td>
                    <td>
                    	<% try {
					if(edicion){
						textHelper.setEditable(false);
					}
                    textHelper.setNombre(CTipoActo.ID_TIPO_ACTO );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTipoActo.ID_TIPO_ACTO);
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
                    <td width="120">Nombre de Acto </td>
                    <td>
                    <% try {
					textHelper.setEditable(true);
                    textHelper.setNombre(CTipoActo.NOMBRE_TIPO_ACTO );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTipoActo.NOMBRE_TIPO_ACTO);
					
					textHelper.render(request,out);
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
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                    <td>Impuesto por Cuant&iacute;a? </td>
                  </tr>
				  <% String imp = (String)session.getAttribute(CTipoActo.IMPUESTO_CUANTIA_EDICION); 
				  	 if(imp==null){
						imp="";
					 }
				  %>
                  <tr>
                    <td height="15">
 					<% if(imp.equals(CTipoActo.IMPUESTO_CUANTIA_SI)){ %>
                    	<input name="<%= CTipoActo.IMPUESTO_CUANTIA %>" type="radio" value="<%= CTipoActo.IMPUESTO_CUANTIA_SI %>" checked="true">
                    <%}else{%>
                    	<input name="<%= CTipoActo.IMPUESTO_CUANTIA %>" type="radio" value="<%= CTipoActo.IMPUESTO_CUANTIA_SI %>">
                    <%}%>
                    </td>
                    <td>SI</td>
                  </tr>
                  <tr>
                    <td height="15">
				    <% if(imp.equals(CTipoActo.IMPUESTO_CUANTIA_NO)){ %>
                    	<input name="<%= CTipoActo.IMPUESTO_CUANTIA %>" type="radio" value="<%= CTipoActo.IMPUESTO_CUANTIA_NO %>" checked="true">
                    <%}else{%>
					    <input name="<%= CTipoActo.IMPUESTO_CUANTIA %>" type="radio" value="<%= CTipoActo.IMPUESTO_CUANTIA_NO %>">
					<%}%>
                    </td>
                    <td>NO</td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="100">Tipos de C&aacute;lculo</td>
                    <td>
                     <% try {
                    List listadoElementosCalculo = new Vector();
                    for(Iterator iter =  tiposCalculo.iterator(); iter.hasNext();){
                    	TipoCalculo tipoCalculo = (TipoCalculo)iter.next();
                    	listadoElementosCalculo.add(new ElementoLista(tipoCalculo.getIdTipoCalculo(), tipoCalculo.getNombre()));
                    	}
                    tiposCalculoHelper.setTipos(listadoElementosCalculo);
			        tiposCalculoHelper.setNombre(CTipoCalculo.ID_TIPO_CALCULO);
			      	tiposCalculoHelper.setCssClase("camposformtext");
			      	tiposCalculoHelper.setId(CTipoCalculo.ID_TIPO_CALCULO);
					tiposCalculoHelper.render(request,out);
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
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Asociar Tipos de Derecho Registral</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td class="campostip04">Oprima SHIFT para selecci&oacute;n M&uacute;ltiple.</td>
                  </tr>
                  <tr>
                    <td height="15">&nbsp;</td>
                    <td>
                 <select  multiple  name="<%= CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO %>" size="8" class="camposformtext">
                    <% 
					List derechosRegistralesSeleccionados = (List)session.getAttribute(CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO_EDICION);
                    for(Iterator iter = tiposDerechoRegistral.iterator(); iter.hasNext(); ){
 		            	TipoDerechoReg tipo = (TipoDerechoReg)iter.next(); 
                   		boolean preSeleccionado=false;
						if(derechosRegistralesSeleccionados!=null){
							for(Iterator iterSelect = derechosRegistralesSeleccionados.iterator(); iterSelect .hasNext();){
								TipoActoDerechoRegistral tipoSelect = (TipoActoDerechoRegistral)iterSelect.next();
								if(tipo.getNombre().equals(tipoSelect.getTipoDerechoRegistral().getNombre())){
									preSeleccionado=true;
									break;
								}
							}
						}
						if(preSeleccionado){
						%>
                   			<option value="<%= tipo.getIdTipoDerechoReg()%>" selected="selected"><%= tipo.getNombre() %></option>
						<%
						}else{%>
							<option value="<%= tipo.getIdTipoDerechoReg()%>"><%= tipo.getNombre() %></option>
						<%
						}
					}%>
					 </select>	   
                    </td>
                  </tr>
                </table>
                <!-- @autor         : JATENCIA
                   * @mantis        : 0015082 
                   * @Requerimiento : 027_589_Acto_liquidación_copias 
                   * @descripcion   : Se establecen los parametros para visualizar la variable. -->   
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                    <td>Activo</td>
                  </tr>
				  <% String imp2 = (String)session.getAttribute(CTipoActo.ACTO_ACTIVO_EDICION); 
				  	 if(imp2==null){
						imp2="";
					 }
				  %>
                  <tr>
                    <td height="15">
 					<% if(imp2.equals(CTipoActo.ACTO_ACTIVO)){ %>
                    	<input name="<%= CTipoActo.ESTADO_ACTO %>" type="radio" value="<%= CTipoActo.ACTO_ACTIVO %>" checked="true">
                    <%}else{%>
                    	<input name="<%= CTipoActo.ESTADO_ACTO %>" type="radio" value="<%= CTipoActo.ACTO_ACTIVO %>">
                    <%}%>
                    </td>
                    <td>SI</td>
                  </tr>
                  <tr>
                    <td height="15">
				    <% if(imp2.equals(CTipoActo.ACTO_NO_ACTIVO)){ %>
                    	<input name="<%= CTipoActo.ESTADO_ACTO %>" type="radio" value="<%= CTipoActo.ACTO_NO_ACTIVO %>" checked="true">
                    <%}else{%>
					    <input name="<%= CTipoActo.ESTADO_ACTO %>" type="radio" value="<%= CTipoActo.ACTO_NO_ACTIVO %>">
					<%}%>
                    </td>
                    <td>NO</td>
                  </tr>
                  <%-- Fin del bloque --%>
                </table>     
                    
               <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
					<%if(!edicion){%>
						<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_TIPO_ACTO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
					<%}else{%>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.EDITA_TIPO_ACTO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    <%}%>
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
                  <td width="40">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">ID</td>
                  <td class="titulotbcentral">Tipo de Acto </td>
                  <td class="titulotbcentral">Impuesto por Cuant&iacute;a</td>
                  <td class="titulotbcentral">Tipo de C&aacute;lculo</td>
                  <td class="titulotbcentral">Tipo de Tarifa de Derecho Registral</td>
                  <td class="titulotbcentral">Estado</td>
                  <td width="50" align="center">Eliminar</td>
                  <td width="50" align="center">Editar</td>
                </tr>
                
              <%  
              
               int idTipoActo =0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	TipoActo dato = (TipoActo)iter.next();
                %>   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato.getIdTipoActo()  %></td>
                  <td class="campositem"><%= dato.getNombre()  %></td>
                  <td class="campositem"><%= (dato.isImpPorCuantia()==true)?"SI":"NO"  %></td>
                  <td class="campositem"><%= dato.getTipoCalculo().getNombre()  %></td>
                  <td class="campositem">
                  		<%
                  		List derechos =  dato.getTiposDerechosRegistrales() ;
                  		for(Iterator iterDerechos = derechos.iterator(); iterDerechos.hasNext();){
                  			TipoActoDerechoRegistral derechoReg = (TipoActoDerechoRegistral)iterDerechos.next(); 
							out.print(derechoReg.getTipoDerechoRegistral().getNombre()+"<br>");                  		
                  		} 
                  		%>
                  </td>
                  <!-- @autor         : JATENCIA
                   * @mantis        : 0015082 
                   * @Requerimiento : 027_589_Acto_liquidación_copias 
                   * @descripcion   : Se establecen los parametros para visualizar la variable. -->
                  <td class="campositem"><%= (dato.isActivo()==true)?"SI":"NO"%></td>
                  <%-- Fin del bloque --%>
                  <form action="administracionHermod.do" method="post" name="ELIMINARTIPOACTO<%=idTipoActo%>" id="ELIMINARTIPOACTO<%=idTipoActo%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_TIPO_ACTO %>" value="<%= AWAdministracionHermod.ELIMINA_TIPO_ACTO %>">
	                	<input  type="hidden" name="<%= CTipoActo.ID_TIPO_ACTO %>" id="<%= dato.getIdTipoActo() %>" value="<%= dato.getIdTipoActo() %>">
                  		<a href="javascript:validarEliminacion('<%=idTipoActo%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                  </td>
                  </form>
				  <form action="administracionHermod.do" method="post" name="EDITAR_TIPO_ACTO" id="EDITAR_TIPO_ACTO">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.EDITAR_TIPO_ACTO %>" value="<%= AWAdministracionHermod.EDITAR_TIPO_ACTO %>">
	                	<input  type="hidden" name="<%= CTipoActo.ID_TIPO_ACTO %>" id="<%= dato.getIdTipoActo() %>" value="<%= dato.getIdTipoActo() %>">
                  		<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0">
                  </td>
                  </form>
                </tr>
                <% 
                
                idTipoActo ++;
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

    alert ('Va a eliminar un Tipo de Acto');
	if (confirm('Esta seguro que desea eliminar el Tipo de Acto'))
	{
     
      eval('document.ELIMINARTIPOACTO' +nombre + '.submit()');
	}
}
</script>
