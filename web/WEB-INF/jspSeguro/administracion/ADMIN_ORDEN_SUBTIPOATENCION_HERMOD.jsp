<%@page import="java.util.Vector" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Collections" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuarioSubtipoAtencion" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.SubtipoAtencion" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
	//Obtener listado de calificadores
	List calificadores = (List)session.getAttribute(AWAdministracionHermod.LISTA_CALIFICADORES);
	boolean recarga=false;
	if(calificadores==null){
		calificadores= new Vector();
		recarga=true;
	}
	//Inicializar calificadoresCirculoHelper
	List lCalificadores = new Vector();
	for (Iterator iterC = calificadores.iterator(); iterC.hasNext();) {
		Usuario usu  = (Usuario) iterC.next();
		lCalificadores.add(	new ElementoLista(Long.toString(usu.getIdUsuario()),usu.getApellido1()+ ", "+ usu.getNombre()));
	}	
	ListaElementoHelper calificadoresCirculoHelper = new ListaElementoHelper();
	calificadoresCirculoHelper.setTipos(lCalificadores);

	//Obtener listado de subtipos de atencion
	List subtiposAtencion = (List)session.getAttribute(AWAdministracionHermod.LISTA_SUBTIPOSATENCION);
	if(subtiposAtencion==null){
		subtiposAtencion= new Vector();
	}
	//Inicializar calificadoresCirculoHelper
	List lSubtiposAtencion = new Vector();
	for (Iterator iterS = subtiposAtencion.iterator(); iterS.hasNext();) {
		SubtipoAtencion subtipo  = (SubtipoAtencion) iterS.next();
		lSubtiposAtencion.add(	new ElementoLista(subtipo.getIdSubtipoAtencion(),subtipo.getNombre()));
	}	
	ListaElementoHelper subtipoHelper = new ListaElementoHelper();
	subtipoHelper.setTipos(lSubtiposAtencion);

	//Obtener listado de calificadores de subtipos de atencion
	List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_CALIFICADORES_SUBTIPOSATENCION);
	List lOrden = new Vector();
	if(tipos==null){
		tipos= new Vector();
	}else{
		//Inicializar ordenHelper
		String ultimo = null;
		for (Iterator iterO = tipos.iterator(); iterO.hasNext();) {
			UsuarioSubtipoAtencion usuSubtipo  = (UsuarioSubtipoAtencion) iterO.next();
			String sorden= Long.toString(usuSubtipo.getOrden());
			lOrden.add(	new ElementoLista(sorden, sorden));
			//Si es el ultimo de la lista, el ultimo numero del orden debe ser +1 de este.
			if(!iterO.hasNext()){
				ultimo = Long.toString(usuSubtipo.getOrden()+1);
			}
		}	
		if(ultimo==null){
			ultimo="0";
		}
		lOrden.add( new ElementoLista(ultimo, ultimo));
	}
	ListaElementoHelper ordenHelper = new ListaElementoHelper();
	ordenHelper.setTipos(lOrden);

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administracci&oacute;n Orden Subtipos De Atenci&oacute;n HERMOD</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Orden Subtipo De Atenci&oacute;n </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td>
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
                    
      		<form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
      		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION %>" value="<%= AWAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION %>">
      		      
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Usuario </td>
                    <td>                      
                      <% 
               try {
                    calificadoresCirculoHelper.setNombre(CUsuario.ID_USUARIO);
                  	calificadoresCirculoHelper.setCssClase("camposformtext");
                  	calificadoresCirculoHelper.setId(CUsuario.ID_USUARIO);
					calificadoresCirculoHelper.render(request,out);
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
                    <td width="180">Subtipo de atencion</td>
                    <td>
               <% 
               try {
                    subtipoHelper.setNombre(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
                  	subtipoHelper.setCssClase("camposformtext");
                  	subtipoHelper.setId(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
                  	subtipoHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CARGAR_LISTADO_CALIFICADORES_SUBTIPOATENCION+"')\"");
					subtipoHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  	%>
                   </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Orden</td>
                    <td>
                 <% 
                   try {
                    ordenHelper.setNombre(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ORDEN);
                  	ordenHelper.setCssClase("camposformtext");
                  	ordenHelper.setId(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ORDEN);
                  	ordenHelper.render(request,out);
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
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                   	</td> 
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                    <td>&nbsp;</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado Calificadores</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td>
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
                  <td>Nombre</td>
                  <td>Orden</td>
                  <td width="50" align="center">Eliminar</td>                                  
                </tr>
         
                 <% for(Iterator iter = tipos.iterator(); iter.hasNext();){
                		UsuarioSubtipoAtencion dato = (UsuarioSubtipoAtencion)iter.next();
						String nombre= dato.getUsuario().getApellido1() + " " + dato.getUsuario().getApellido2() + ", " + dato.getUsuario().getNombre();
				%> 
                	<tr>
                  		<td>&nbsp;</td>
                  		<td class="campositem"><%=  nombre %></td>
                  		<td class="campositem"><%=  dato.getOrden() %></td>
						<form action="administracionHermod.do" method="post" name="ELIMINARORDEN<%=dato.getIdUsuario()%>" id="ELIMINARORDEN<%=dato.getIdUsuario()%>">
                  		<td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_ORDEN_SUBTIPOATENCION %>" value="<%= AWAdministracionHermod.ELIMINA_ORDEN_SUBTIPOATENCION %>">
	                	<input  type="hidden" name="<%= CSubtipoAtencion.ID_SUBTIPO_ATENCION %>" id="<%= dato.getIdSubtipoAtencion() %>" value="<%= dato.getIdSubtipoAtencion() %>">
						<input  type="hidden" name="<%= CUsuario.ID_USUARIO %>" id="<%= dato.getIdUsuario() %>" value="<%= dato.getIdUsuario() %>">			
                  		<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0">
                  		</td>
                  		</form>
						               	
                	</tr>
                <%}%> 
            </table></td>
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
<%if(recarga){%>
<script>
	cambiarAccionAndSend('<%= AWAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION %>')
</script>
<%}%>