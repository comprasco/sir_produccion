<%@page import="java.util.Map" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="org.auriga.core.web.HelperException" %>


<%
TextHelper textHelper = new TextHelper();
boolean cargarDatos=true;
List departamentos=(List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
if(departamentos==null){
	departamentos=new ArrayList();
}else{
	cargarDatos=false;
}
java.lang.Boolean esNacional = (java.lang.Boolean)request.getSession().getAttribute(AWAdministracionForseti.ADMINISTRADOR_NACIONAL);
if (esNacional==null){
	esNacional=new java.lang.Boolean(false);
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
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit()
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n de Departamentos, Municipios y Veredas</td>
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
    
    
        <form action="administracionForseti.do" method="post" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionForseti.ADICIONA_DEPARTAMENTO %>" value="<%= AWAdministracionForseti.ADICIONA_DEPARTAMENTO %>">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Departamento </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        <img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12">
                  <img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
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
                    <td class="bgnsub">Departamento</td>
                    <td width="16" class="bgnsub">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                <%
                	if (esNacional.booleanValue()){%>
                		<table width="100%" class="camposform">
		                  <tr>
		                    <td width="20">
		                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">
		                    </td>
		                    <td width="200">C&oacute;digo del departamento </td>
		                    <td>
		                    	<% try {
		                    textHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
		                  	textHelper.setCssClase("camposformtext");
		                  	textHelper.setId(CDepartamento.ID_DEPARTAMENTO);
							textHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					  	%>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td width="20">
		                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">
		                    </td>
		                    <td width="200">Nombre del departamento </td>
		                    <td>
		                    	<% try {
		                    textHelper.setNombre(CDepartamento.NOMBRE_DEPARTAMENTO);
		                  	textHelper.setCssClase("camposformtext");
		                  	textHelper.setId(CDepartamento.NOMBRE_DEPARTAMENTO);
							textHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					  		%>
		                    </td>
		                  </tr>
		                </table>
                	<%}
                %>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                	
                    </td>
                    <td>
	                    <%if (esNacional.booleanValue()){%>
	                    	<input name="imageField" type="image"  onClick="cambiarAccion('<%=AWAdministracionForseti.ADICIONA_DEPARTAMENTO%>');" src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
	                    <%}else{%>
	                		&nbsp;
	                	<%}%>
                    </td>
                     
                    <td>&nbsp;</td>
                  </tr>
                </table>
                
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td>
            	<img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt="">
            </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td>
            	<img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt="">
            </td>
        </tr>
		</table>
		</form>
		
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado - Asociar </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
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
            <table width="100%" class="camposform">
              <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                <td width="40">ID</td>
                <td>Nombre</td>
                <td width="50" align="center">Municipio</td>
                <%if (esNacional.booleanValue()){%>
	                <td width="50" align="center">Eliminar</td>
	            <%}else{%>
	            	<td width="50" align="center">&nbsp;</td>
	            <%}%>
              </tr>
        <%
        int idDepto =0;
		for(Iterator iter = departamentos.iterator(); iter.hasNext();){
			
			Departamento depto = (Departamento)iter.next();
        %>      
              <tr>
                <td>&nbsp;</td>
                <td class="campositem"><%= depto.getIdDepartamento() %>
                <td class="campositem"><%= depto.getNombre() %>
                </td>
                <form action="administracionForseti.do" method="post" name="SELECCIONA<%=idDepto%>" id="SELECCIONA<%=idDepto%>">
                <td align="center">
	        		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionForseti.SELECCIONA_DEPARTAMENTO %>" value="<%= AWAdministracionForseti.SELECCIONA_DEPARTAMENTO %>">
                	<input  type="hidden" name="<%= CDepartamento.ID_DEPARTAMENTO %>" id="<%= depto.getIdDepartamento() %>" value="<%= depto.getIdDepartamento() %>">
                	<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_asociar_geografica.gif" width="35" height="13" border="0">
                </td>
                </form>
                <%if (esNacional.booleanValue()){%>
	                <form action="administracionForseti.do" method="post" name="ELIMINA<%=idDepto%>" id="ELIMINA<%=idDepto%>">
	                <td align="center">
		        		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionForseti.ELIMINA_DEPARTAMENTO %>" value="<%= AWAdministracionForseti.ELIMINA_DEPARTAMENTO %>">
	                	<input  type="hidden" name="<%= CDepartamento.ID_DEPARTAMENTO %>" id="<%= depto.getNombre() %>-<%= depto.getIdDepartamento() %>" value="<%= depto.getNombre() %>-<%= depto.getIdDepartamento() %>">
	                	<a href="javascript:validarEliminacion('<%=idDepto%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
	                </td>
	                </form>
                <%}else{%>
	            	<td width="50" align="center">&nbsp;</td>
	            <%}%>
              </tr>
		<%idDepto++;
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
<%if(cargarDatos){%>
	<script>cambiarAccionSubmit('<%=AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO%>');</script>
<%}%>


<script>
function validarEliminacion(nombre) {

	if (confirm('Esta seguro que desea eliminar el Departamento'))
	{
     
      eval('document.ELIMINA' +nombre + '.submit()');
	}
}
</script>