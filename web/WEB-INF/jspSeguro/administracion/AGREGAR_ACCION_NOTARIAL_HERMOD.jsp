<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAccionNotarial" %>
<%@page import="gov.sir.core.negocio.modelo.AccionNotarial" %>
<%@page import="gov.sir.core.web.helpers.comun.RadioHelper" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
TextHelper textHelper = new TextHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
gov.sir.core.web.helpers.comun.RadioHelper radioHelper = new RadioHelper();   	

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
function cancelarEdicion() {
	document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.DESHABILITA_EDICION_ACCION_NOTARIAL%>'
	document.BUSCAR.submit();
}
function eliminar(text){
	if (confirm("VA A ELIMINAR EL NEGOCIO JURÍDICO CON ID "+text)){
		document.BUSCAR.<%=CAccionNotarial.ID_ACCION_NOTARIAL%>.value = text
		document.BUSCAR.submit()
	}
}
function editar(text){
	if (confirm("VA A EDITAR EL NEGOCIO JURÍDICO CON ID "+text)){
		document.BUSCAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionHermod.HABILITA_EDICION_ACCION_NOTARIAL%>'	
		document.BUSCAR.<%=CAccionNotarial.ID_ACCION_NOTARIAL%>.value = text
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Negocio Jurídico</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Negocio jurídico </td>
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
                    <td class="bgnsub">Negocio jurídico </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
              
                
       <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL %>" value="<%= AWAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&oacute;digo del negocio jurídico </td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CAccionNotarial.ID_ACCION_NOTARIAL);
                    textHelper.setReadonly(true);
                    textHelper.setSize("60");                    
                  	textHelper.setCssClase("campositem");
                  	textHelper.setId(CAccionNotarial.ID_ACCION_NOTARIAL);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td width="20">
                     
                  </tr>
                  <tr height="1"> 
                    <td> </td>
                    <td> </td>  
                    <td width="400" height="5"> El Sistema lo Asigna Automaticamente en Creacion </td>
                  </tr> 
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Negocio jurídico </td>
                    <td>
                    <% try {
                    textHelper.setReadonly(false);
                    textHelper.setNombre(CAccionNotarial.NOMBRE_ACCION_NOTARIAL );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Tipo de cuantía</td>
                    <td>
                    
                    
						<% try {
 		                        String defecto = (String)request.getSession().getAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
 		                        radioHelper.setNombre(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setId(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setValordefecto("TRUE");                  			    
							    radioHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>  Con cuantía&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 
						<% try {
 		                        String defecto = (String)request.getSession().getAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
 		                        if(defecto==null){
 		                        	request.getSession().setAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL,"FALSE");
 		                        }														
 		                        radioHelper.setNombre(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setId(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setValordefecto("FALSE");                  			    
							    radioHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>  Sin cuantía
                    
                    
                    <%/* try {
                    textHelper.setNombre(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}*/
			  %>
                    </td>
                  </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar Negocio jurídico </td>
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
                    <td class="bgnsub">Negocio jurídico </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
        <%
        AccionNotarial accion = (AccionNotarial)session.getAttribute(CAccionNotarial.ACCION_NOTARIAL);
        %>
                              
                
       <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL %>" value="<%= AWAdministracionHermod.EDITA_ACCION_NOTARIAL%>">
	    <input  type="hidden" name="<%=CAccionNotarial.ID_ACCION_NOTARIAL%>" id="<%=CAccionNotarial.ID_ACCION_NOTARIAL%>" value="<%=accion.getIdAccionNotarial()!=null?accion.getIdAccionNotarial():""%>">                                                    
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&oacute;digo del negocio jurídico </td>
                    <td class="campositem"><%=accion.getIdAccionNotarial()!=null?accion.getIdAccionNotarial():""%></td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Negocio jurídico </td>
                    <td>
                    <% 
                    try {
	                    session.setAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL, accion.getNombre());                    
	                    textHelper.setNombre(CAccionNotarial.NOMBRE_ACCION_NOTARIAL );
	                    textHelper.setSize("60");	                    
	                  	textHelper.setCssClase("camposformtext");
	                  	textHelper.setId(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
						textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Tipo de cuantía</td>
                    <td>
                    
                    
						<% try {
 		                        String defecto = (String)request.getSession().getAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
 		                        if(defecto==null){
 		                        	if(accion.isCuantia()){
	 		                        	request.getSession().setAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL,"TRUE");
 		                        	}else{
	 		                        	request.getSession().setAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL,"FALSE");
 		                        	}
 		                        }																				
 		                        radioHelper.setNombre(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setId(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setValordefecto("TRUE");                  			    
							    radioHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>  Con cuantía&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 
						<% try {

 		                        radioHelper.setNombre(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setId(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                  			    radioHelper.setValordefecto("FALSE");                  			    
							    radioHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>  Sin cuantía
                    

                    </td>
                  </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.EDITA_ACCION_NOTARIAL%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    	</td>
                   	 <td>
                    	<a href="javascript:cancelarEdicion();"><img onClick=""  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
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
		
		
	    
	    <%}%>
	    			
		
		
		
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
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></span></td>
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
                  <td class="titulotbcentral">Código Negocio </td>                  
                  <td class="titulotbcentral">Negocio jurídico </td>
                  <td class="titulotbcentral">Tipo de cuantía</td>
                  <td width="90" align="center">Editar Eliminar</td>
                </tr>
		       <% 
		       int idNegocio =0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	AccionNotarial dato = (AccionNotarial)iter.next(); 
                %>   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%=   dato.getIdAccionNotarial()    %></td>                  
                  <td class="campositem"><%=   dato.getNombre()    %></td>
                  <td class="campositem"><%=   dato.isCuantia()?"CON CUANTÍA":"SIN CUANTÍA"    %></td>
                  <form action="administracionHermod.do" method="post" name="ELIMINARNEGOCIO<%=idNegocio%>" id="ELIMINARNEGOCIO<%=idNegocio%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_ACCION_NOTARIAL %>" value="<%= AWAdministracionHermod.ELIMINA_ACCION_NOTARIAL %>">
	                	<input  type="hidden" name="<%= CAccionNotarial.ID_ACCION_NOTARIAL %>" id="<%= dato.getIdAccionNotarial() %>" value="<%= dato.getIdAccionNotarial() %>">
        			    <a href="javascript:editar('<%=dato.getIdAccionNotarial()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a>                 	                	
                  		<a href="javascript:validarEliminacion('<%=idNegocio%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0">
                  	</td>
                  	</form>
                </tr>
               <% 
               idNegocio++;
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

    alert ('Va a eliminar un Negocio Jurídico de Reparto Notarial');
	if (confirm('Esta seguro que desea eliminar el Negocio Jurídico de Reparto Notarial'))
	{
     
      eval('document.ELIMINARNEGOCIO' +nombre + '.submit()');
	}
}
</script>