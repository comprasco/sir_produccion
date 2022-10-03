<%@page import="gov.sir.core.web.helpers.comun.NotasInformativasHelper"%>
<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReasignacionTurnos" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="org.auriga.core.modelo.transferObjects.Estacion" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacion" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>

<%
	TextHelper textHelper = new TextHelper();
        NotasInformativasHelper notasHelper = new NotasInformativasHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.action = 'reasignacionTurnos.do';
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}
function cambiarAccionExtraordinaria(text) {
	if(confirm('Esta opción es únicamente para la reasignación de turnos conservando los datos temporales. ¿Desea continuar la operación?')){
		document.BUSCAR.action = 'reasignacionTurnos.do';
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
		document.BUSCAR.submit();		
	}
}
function cambiarExtraordinariaEliminar(text) {
	if(confirm('Esta opción es únicamente para la reasignación de turnos eliminando los datos temporales. ¿Desea continuar la operación?')){
		document.BUSCAR.action = 'reasignacionTurnos.do';
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
		document.BUSCAR.submit();		
	}
}
function send(){
   document.forma.submit();
}

function cambiarSeleccion() {
    if(document.BUSCAR.seleccionado.checked == true){
	    setAllCheckBoxes('BUSCAR', '<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>', true);
    }
    if(document.BUSCAR.seleccionado.checked == false){
    	setAllCheckBoxes('BUSCAR', '<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>', false);
    }
}
</script>
<%
List usuariosCirculo =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_USUARIOS);
List rolesUsuario =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_ROLES);
List listaTurnos =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS);
List listaTurnosSeleccionados =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_SELECCIONADOS);
List listaPosiblesUsuarios =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
List listaPosiblesEstaciones =(List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);

if(listaTurnosSeleccionados==null){
	listaTurnosSeleccionados = new ArrayList();
}
if(listaPosiblesEstaciones==null){
	listaPosiblesEstaciones = new ArrayList();
}


if (usuariosCirculo==null){
%>
	<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	    <form action="reasignacionTurnos.do" method="POST" name="forma"  id="forma">
	  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWReasignacionTurnos.CARGAR_USUARIOS%>" value="<%=AWReasignacionTurnos.CARGAR_USUARIOS%>">
		</form>
<%
}
%>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reasignación de turnos</td>
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
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Ingrese la información solicitada...</td>
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
          
        <form action="reasignacionTurnos.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWReasignacionTurnos.REASIGNAR_TURNOS%>" value="<%=  AWReasignacionTurnos.REASIGNAR_TURNOS%>">
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">1. Seleccione el funcionario que tiene asignados los turnos.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(usuariosCirculo!=null && !usuariosCirculo.isEmpty()){
                %>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="40%" align='right'>                    	
                    	<b>Funcionario:</b>
                    </td>
                    <td >                   	
					<% 
					try {
						List usuarioElementoLista = new ArrayList();
						if(usuariosCirculo!=null){
							java.util.Iterator it = usuariosCirculo.iterator();
							while(it.hasNext()){
								Usuario usuario = (Usuario)it.next();
								ElementoLista elemento = new ElementoLista();
								elemento.setId(""+usuario.getIdUsuario());
								elemento.setValor(usuario.getUsername());
								usuarioElementoLista.add(elemento);
							}
						}
					
						ListaElementoHelper listaHelper= new ListaElementoHelper();
          			    listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
           				listaHelper.setTipos(usuarioElementoLista);
          			    listaHelper.setId(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
          			    listaHelper.setNombre(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
						listaHelper.setCssClase("camposformtext");						
          			    listaHelper.setFuncion("onchange=cambiarAccion('"+AWReasignacionTurnos.CARGAR_ROLES+"')");
						listaHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>                    	
                    </td>
                  </tr>
                </table>                                
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existen usuarios en el circulo.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>
                
                <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">2. Seleccione el rol del funcionario para seleccionar los turnos.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(rolesUsuario!=null && !rolesUsuario.isEmpty()){
                %>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="40%" align='right'>                    	
                    	<b>Roles:</b>
                    </td>
                    <td >                   	
					<% 
					try {
						List rolElementoLista = new ArrayList();
						if(rolesUsuario!=null){
							java.util.Iterator it = rolesUsuario.iterator();
							while(it.hasNext()){
								Rol rol = (Rol)it.next();
								ElementoLista elemento = new ElementoLista();
								elemento.setId(""+rol.getRolId());
								elemento.setValor(rol.getNombre());
								rolElementoLista.add(elemento);
							}
						}
					
						ListaElementoHelper listaHelper= new ListaElementoHelper();
          			    listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
           				listaHelper.setTipos(rolElementoLista);
          			    listaHelper.setId(CRol.ID_ROL);
          			    listaHelper.setNombre(CRol.ID_ROL);
						listaHelper.setCssClase("camposformtext");						
          			    listaHelper.setFuncion("onchange=cambiarAccion('"+AWReasignacionTurnos.CARGAR_TURNOS+"')");
						listaHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>                     	
                    </td>
                  </tr>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existen roles asignados al usuario.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>
                
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">3. Seleccione turnos que desea reasignar.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(listaTurnos!=null && !listaTurnos.isEmpty()){
                	java.util.Iterator it = listaTurnos.iterator();
                	
                %>
                <table width="100%" class="camposform">
                 <tr>
                 <td>&nbsp;</td>
                 <td align='left'><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>Todos</td> 
                  <td class="titulotbcentral">&nbsp;</td>
                  <td class="titulotbcentral">&nbsp;</td>
                  <td class="titulotbcentral">&nbsp;</td>
                  
                 </tr>
                <%
	                boolean seleccionado = false;
                	while (it.hasNext()){
                	gov.sir.core.negocio.modelo.Turno turno = (gov.sir.core.negocio.modelo.Turno)it.next();
                	gov.sir.core.negocio.modelo.TurnoHistoria th= null;
                	
                	if(turno.getHistorials()!=null && turno.getHistorials().size()>0){
                		th = (gov.sir.core.negocio.modelo.TurnoHistoria)turno.getHistorials().get(0);
                	}
                %>
                
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>          
                    	<%
                    	seleccionado = listaTurnosSeleccionados.contains(turno.getIdWorkflow());
                    	%>          	
                    	<input type='checkbox' name='<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>' id='<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>' value='<%=turno.getIdWorkflow()!=null?turno.getIdWorkflow():""%>' <%=seleccionado?"checked":""%> >
                    </td>
                    <td>                    	
                    	<b>Turno: </b><%=turno.getIdWorkflow()!=null?turno.getIdWorkflow():""%>
                    </td>
                    <td >                   	
                    	<b>Fase: </b><%=th!=null&&th.getNombreFase()!=null?th.getNombreFase():""%>
                    </td>
                    <td >                   	
                    	<b>Estación:</b> <%=th!=null&&th.getIdAdministradorSAS()!=null?th.getIdAdministradorSAS():""%>
                    </td>
                  </tr>
                  <%}%>
                 <tr>
                 <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  </tr>
                </table>
                
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>El usuario no tiene turnos asignados en este Rol.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>
                
                 
		        <BR><hr class="linehorizontal"><BR>		                                 
                 
                    
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">4. Seleccione el funcionario a quien desea reasignarle los turnos.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(listaPosiblesUsuarios!=null && !listaPosiblesUsuarios.isEmpty()){
                %>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="40%" align='right'>                    	
                    	<b>Funcionario:</b>
                    </td>
                    <td >                   	
					<% 
					try {
						List usuarioElementoLista = new ArrayList();
						if(listaPosiblesUsuarios!=null){
							java.util.Iterator it = listaPosiblesUsuarios.iterator();
							while(it.hasNext()){
								Usuario usuario = (Usuario)it.next();
								ElementoLista elemento = new ElementoLista();
								elemento.setId(""+usuario.getIdUsuario());
								elemento.setValor(usuario.getUsername());
								usuarioElementoLista.add(elemento);
							}
						}
					
						ListaElementoHelper listaHelper= new ListaElementoHelper();
          			    listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
           				listaHelper.setTipos(usuarioElementoLista);
          			    listaHelper.setId(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
          			    listaHelper.setNombre(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
						listaHelper.setCssClase("camposformtext");						
          			    listaHelper.setFuncion("onchange=cambiarAccion('"+AWReasignacionTurnos.CARGAR_ESTACIONES+"')");
						listaHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>                    	
                    </td>
                  </tr>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existe otro funcionario que tenga el mismo rol, se debe configurar primero otro funcionario que tenga el mismo rol.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>
            
            <div id="a" style='display:<%=(listaPosiblesEstaciones.size()==1?"none":"block")%>'>
			<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">5. Seleccione la estación del usuario a la cuál quedarán los turnos.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(listaPosiblesEstaciones!=null && !listaPosiblesEstaciones.isEmpty()){
                %>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="40%" align='right'>                    	
                    	Estaciones:
                    </td>
                    <td >                   	
					<% 
					try {
						List estacionElementoLista = new ArrayList();
						if(listaPosiblesEstaciones!=null){
							java.util.Iterator it = listaPosiblesEstaciones.iterator();
							while(it.hasNext()){
								Estacion estacion = (Estacion)it.next();
								ElementoLista elemento = new ElementoLista();
								elemento.setId(estacion.getEstacionId());
								elemento.setValor(estacion.getNombre());
								estacionElementoLista.add(elemento);
							}
						}
					
						ListaElementoHelper listaHelper= new ListaElementoHelper();
          			    listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
           				listaHelper.setTipos(estacionElementoLista);
          			    listaHelper.setId(CEstacion.ESTACION_ID);
          			    listaHelper.setNombre(CEstacion.ESTACION_ID);
          			    if(estacionElementoLista.size()==1){
          			    	listaHelper.setShowInstruccion(false);
          			    }
						listaHelper.setCssClase("camposformtext");
						listaHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>                          	
                    </td>
                  </tr>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>El usuario no tiene estaciones configuradas para el rol.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>                
                
                </div>
                    
				<BR><hr class="linehorizontal"><BR>		                                                  
                                
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  AWReasignacionTurnos.REASIGNAR_TURNOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_reasignar_turnos.gif" width="150" height="21" border="0"></a>
                    </td>
                    <td width="155">
                    	<a href="javascript:cambiarAccionExtraordinaria('<%=  AWReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_reasignacion_temporales.gif" width="220" height="21" border="0"></a>
                    </td>
                    <td width="155">
                    	<a href="javascript:cambiarExtraordinariaEliminar('<%=  AWReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_reasignacion_borrar_temporales.gif" width="232" height="21" border="0"></a>
                    </td>
                    <td>
                    	<a href="javascript:cambiarAccion('<%=  AWReasignacionTurnos.VOLVER%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
                    </td>
                  </tr>
                </table>
               <% 
                    try
		{
		  notasHelper.setNombreFormulario("BUSCAR");
		  notasHelper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}	%>
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
