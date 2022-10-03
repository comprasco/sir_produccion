 <%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Collections" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.text.Format" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.Categoria" %>
<%@page import="org.auriga.core.web.HelperException" %>


<%
TextHelper textHelper = new TextHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_CATEGORIAS);

boolean modoEdicion = false;
boolean activo = false;
String categoria = request.getParameter("CATEGORIA");
if(categoria!=null  &&  !categoria.trim().equals("")){
	modoEdicion = true;
	session.setAttribute(CCategoria.MODO_EDICION, new Boolean(true));
	for(Iterator iter = tipos.iterator(); iter.hasNext(); ){
		Categoria cat  = (Categoria)iter.next();
		if(cat.getIdCategoria().equals(categoria)){
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(false);
			format.setGroupingUsed(false);
			session.setAttribute(CCategoria.ID_CATEGORIA, cat.getIdCategoria());
			session.setAttribute(CCategoria.NOMBRE_CATEGORIA, cat.getNombre());
			session.setAttribute(CCategoria.VALOR_MAX_CATEGORIA,   format.format(new Double(cat.getValorMax())) );
			session.setAttribute(CCategoria.VALOR_MIN_CATEGORIA,   format.format(new Double(cat.getValorMin())) );
			session.setAttribute(CCategoria.UNIDAD_MAX_CATEGORIA,  format.format(new Double(cat.getUnidadMax()))  );
			session.setAttribute(CCategoria.UNIDAD_MIN_CATEGORIA,  format.format(new Double(cat.getUnidadMin()))  );
			activo = cat.isActivo();
			break;
		}
	}
}else{
	Boolean edicion = (Boolean)session.getAttribute(CCategoria.MODO_EDICION);
	if(edicion != null && edicion.booleanValue()){
		modoEdicion = true;
	}
}

/*if(!modoEdicion){
	session.removeAttribute(CCategoria.ID_CATEGORIA);
	session.removeAttribute(CCategoria.NOMBRE_CATEGORIA);
	session.removeAttribute(CCategoria.VALOR_MAX_CATEGORIA);
	session.removeAttribute(CCategoria.VALOR_MIN_CATEGORIA);
	session.removeAttribute(CCategoria.UNIDAD_MAX_CATEGORIA);
	session.removeAttribute(CCategoria.UNIDAD_MIN_CATEGORIA);
}*/

String estado = (String) session.getAttribute(CCategoria.CATEGORIA_ESTADO);
if(estado!=null){
	activo = true;
	session.removeAttribute(CCategoria.CATEGORIA_ESTADO);
}

%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n de Categor&iacute;as</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Categor&iacute;a</td>
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
                    <td class="bgnsub">Categor&iacute;a</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_CATEGORIA %>" value="<%= AWAdministracionHermod.ADICIONA_CATEGORIA %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&oacute;digo de la Categor&iacute;a</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CCategoria.ID_CATEGORIA );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCategoria.ID_CATEGORIA);
					if(modoEdicion){
						textHelper.setReadonly(true);
					}	
					textHelper.render(request,out);
						
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td width="180">Categor&iacute;a</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCategoria.NOMBRE_CATEGORIA );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCategoria.NOMBRE_CATEGORIA);
					textHelper.setReadonly(false);
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
                    <td>Valor Minimo </td>
                    <td>
                     <% try {
	                textHelper.setNombre(CCategoria.VALOR_MIN_CATEGORIA );
	              	textHelper.setCssClase("camposformtext");
	              	textHelper.setId(CCategoria.VALOR_MIN_CATEGORIA);
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
                    <td>Valor M&aacute;ximo </td>
                    <td>
                     <% try {
	                textHelper.setNombre(CCategoria.VALOR_MAX_CATEGORIA );
	              	textHelper.setCssClase("camposformtext");
	              	textHelper.setId(CCategoria.VALOR_MAX_CATEGORIA);
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
                    <td>Unidad Minima </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCategoria.UNIDAD_MIN_CATEGORIA );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCategoria.UNIDAD_MIN_CATEGORIA);
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
                    <td>Unidad M&aacute;xima </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCategoria.UNIDAD_MAX_CATEGORIA );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCategoria.UNIDAD_MAX_CATEGORIA);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
				  <% 
					//if(modoEdicion){
					%>
				  <tr>
                    <td width="20">&nbsp;</td>
                    <td width="200">Activo </td>
					<%
						if(activo){
					%>
                    <td>
			 			<input type="checkbox" name="<%= CCategoria.CATEGORIA_ESTADO %>"  value="<%= CCategoria.CATEGORIA_ESTADO%>"  checked="true"  >
                    </td>
					<%}else{%>
					<td>
			 			<input type="checkbox" name="<%= CCategoria.CATEGORIA_ESTADO %>"  value="<%= CCategoria.CATEGORIA_ESTADO%>"   >
                    </td>
					<%
					//}
					%>
                  </tr>
				  <%}%>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <%if(!modoEdicion){%>
                    	<td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_CATEGORIA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    	</td>
					<%}else{%>
						<td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.EDITA_CATEGORIA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0">
                    	</td>
					<%}%>
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
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></span></td>
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
                  <td width="50">Listado</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td width="50" align="center">Editar</td>
                  <td width="50" align="center">Eliminar</td>      
               <!--   <td width="50" align="center">Editar</td>      -->
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">ID</td>
                  <td class="titulotbcentral">Categor&iacute;a</td>
                  <td class="titulotbcentral">Valor M&iacute;nimo </td>
                  <td class="titulotbcentral">Valor M&aacute;ximo </td>
                  <td class="titulotbcentral">Unidad M&iacute;nima </td>
                  <td class="titulotbcentral">Unidad M&aacute;xima</td>
                  <td class="titulotbcentral">Activa</td>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                </tr>
                
                <% 
                
                Collections.sort(tipos,new Comparator(){
					public int compare(Object arg0, Object arg1) {
						Categoria cat1=(Categoria)arg0;
						Categoria cat2=(Categoria)arg1;
						return cat1.getIdCategoria().compareTo(cat2.getIdCategoria());
					}
		    	});
		    	
		    	int idCategoria=0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	Categoria dato = (Categoria)iter.next(); 
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%=   dato.getIdCategoria() %></td>
                  <td class="campositem"><%=   dato.getNombre() %></td>
                  
                  <td class="campositem"><%=   new DecimalFormat().format(new Double(dato.getValorMin())) %></td>
                  <td class="campositem"><%=   new DecimalFormat().format(new Double(dato.getValorMax())) %></td>
                  <td class="campositem"><%=   dato.getUnidadMin() %></td>
                  <td class="campositem"><%=   dato.getUnidadMax() %></td>
                  <td class="campositem"><%=   dato.isActivo()? "ACTIVA":"INACTIVA" %></td>
				  <td align="center">
                  		<a href="admin.categoria.view?CATEGORIA=<%=  dato.getIdCategoria()%>"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" alt="Editar" width="35" height="13" border="0" ></a>               
                  </td> 
                  <form action="administracionHermod.do" method="post" name="ELIMINARCATEGORIA<%=idCategoria%>" id="ELIMINARCATEGORIA<%=idCategoria%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_CATEGORIA %>" value="<%= AWAdministracionHermod.ELIMINA_CATEGORIA %>">
	                	<input  type="hidden" name="<%= CCategoria.ID_CATEGORIA %>" id="<%= dato.getIdCategoria() %>" value="<%= dato.getIdCategoria() %>">
                  		<a href="javascript:validarEliminacion('<%=idCategoria%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                  </td>
                  </form>
	                 
                </tr>
                 <%
                 idCategoria++; 
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

    alert ('Va a eliminar una Categoría de Reparto Notarial');
	if (confirm('Esta seguro que desea eliminar la Categoría de Reparto Notarial'))
	{
     
      eval('document.ELIMINARCATEGORIA' +nombre + '.submit()');
	}
}
</script>