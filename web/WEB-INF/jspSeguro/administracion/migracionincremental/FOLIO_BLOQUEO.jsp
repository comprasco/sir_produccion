<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="java.util.List"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%
TextHelper textHelper = new TextHelper();
Circulo circuloActual = (Circulo) session.getAttribute(WebKeys.CIRCULO);

//SE DETERMINA SI EL USUARIO ES ADMIN NACIONAL PARA MOSTRAR UNO O TODOS LOS CIRCULOS.
List rolesUsuario = (List)session.getAttribute(WebKeys.LISTA_ROLES);
if( null == rolesUsuario ) {
  rolesUsuario = new java.util.ArrayList();
}
boolean isAdministradorNacional = false;


java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;

for( ;local_RolesListIterator.hasNext(); ) {
	local_RolesListElement =(org.auriga.core.modelo.transferObjects.Rol)local_RolesListIterator.next();
	String rol = local_RolesListElement.getRolId();
	if( rol.equals(CRoles.ADMINISTRADOR_NACIONAL) ) {
		isAdministradorNacional = true;
		break;
	}
}
/**
* AHERRENO 10/04/2013
* Requerimiento 064_453_Duplicidad_Nombre_Circulo 
* descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
* de departamentos por circulo.
*/
        List listaCirculoDepartamento = new Vector();
        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

        int idCirculoInt = 0;
        String nombreCirculoDepartamento = "";
        String idCirculoString = "";
//SI ES ADMINISTRADOR NACIONAL SE MOSTRARÁN TODOS LOS CIRCULOS PARA ESCOGER EN DÓNDE CREAR EL USUARIO
//SINO ES ADMINISTRADOR NACIONAL SE MOSTRARÁ ÚNICAMENTE EL CIRCULO AL QUE PERTENECE EL USUARIO
List elementos = new java.util.ArrayList();
if(isAdministradorNacional){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	for( java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
		gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
		/*elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));*/
                idCirculoString = circulo.getIdCirculo();
                if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                    idCirculoInt = Integer.parseInt(idCirculoString);
                    nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                    if(nombreCirculoDepartamento != ""){
                        elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                    }
                }
	}
}else{
	gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
	List circulosUsuario = usuario.getUsuarioCirculos();
	java.util.Iterator itCirculosCiudadano = circulosUsuario.iterator();
	gov.sir.core.negocio.modelo.UsuarioCirculo usuarioCirculoTemp = null;
	gov.sir.core.negocio.modelo.Circulo circuloTemp = null;	
	for(;itCirculosCiudadano.hasNext();) {
	  usuarioCirculoTemp = (gov.sir.core.negocio.modelo.UsuarioCirculo)itCirculosCiudadano.next();
	  circuloTemp = usuarioCirculoTemp.getCirculo();
	  /*elementos.add(new ElementoLista(circuloTemp.getIdCirculo(), circuloTemp.getNombre()));*/
            idCirculoString = circuloTemp.getIdCirculo();
            if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                idCirculoInt = Integer.parseInt(idCirculoString);
                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                if(nombreCirculoDepartamento != ""){
                    elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                }
            }
	} 
}

ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(elementos);

//SE OBTIENEN LOS PROCESOS DEL SISTEMA
List procesos = (List)session.getServletContext().getAttribute(WebKeys.LISTA_PROCESOS_RECIBO_SISTEMA);
ListaElementoHelper procesoHelper = new ListaElementoHelper();
procesoHelper.setTipos(procesos);

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.MIGRACIONINCREMENTAL.<%= WebKeys.ACCION %>.value = text;
	document.MIGRACIONINCREMENTAL.submit();
}
function mostrarSir() {
	if(document.all.<%=gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO%>.value != '<%=WebKeys.SIN_SELECCIONAR%>'){
		document.getElementById('cir').innerHTML = '' + document.all.<%=gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO%>.value + ' -';
	}else{
		document.getElementById('cir').innerHTML = ''
	}
}
</script>
<body onload='mostrarSir()'>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Bloqueo de Folios por Turnos en trámite en el sistema FOLIO</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Bloqueo de Folios</td>
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

        <form action="adminmigracionincremental.do" method="POST" name="MIGRACIONINCREMENTAL" id="MIGRACIONINCREMENTAL">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  gov.sir.core.web.acciones.administracion.AWMigracionIncremental.BLOQUEAR_TURNO_FOLIO %>">


          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos para el Bloqueo</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                

                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="38%">N&uacute;mero de radicación (Año-Número)</td>
                    <td width="20%">
					&nbsp;
                    <% 
						try {
	                  		textHelper.setEditable(true);
 		              		textHelper.setNombre(CTurno.ID_TURNO);
                  	  		textHelper.setCssClase("camposformtext");
                  	  		textHelper.setId(CTurno.ID_TURNO);
					  		textHelper.render(request,out);
						} catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%> 
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="38%">Círculo</td>
                    <td width="20%">
					&nbsp;
                    <% 
                    try {
	                  	circuloHelper.setId( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
	                    circuloHelper.setNombre( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
                  		circuloHelper.setFuncion("onChange=\"mostrarSir()\"");
	                  	circuloHelper.setCssClase("camposformtext");
	                  	circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>
                    </td>
                    <td>&nbsp;</td>
                  </tr>

                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="38%">Proceso</td>
                    <td width="20%">
					&nbsp;
                    <% 
						try {
                    		procesoHelper.setNombre(CProceso.PROCESO_ID);
                  			procesoHelper.setCssClase("camposformtext");
                  			procesoHelper.setId(CProceso.PROCESO_ID);
							procesoHelper.render(request,out);
						}
						catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}

					%>
                    </td>
                    <td>&nbsp;</td>
                  </tr>
      
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="38%" valign='middle' >N&uacute;mero del folio</td>
                    <td width="20%"><table><tr class="camposform"><td id='cir'></td><td>
                    <% 
						try {
	                  		textHelper.setEditable(true);
 		              		textHelper.setNombre(CFolio.ID_MATRICULA);
                  	  		textHelper.setCssClase("camposformtext");
                  	  		textHelper.setId(CFolio.ID_MATRICULA);
					  		textHelper.render(request,out);
						} catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td></tr></table>
                    
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  gov.sir.core.web.acciones.administracion.AWMigracionIncremental.BLOQUEAR_TURNO_FOLIO%>');">
                    	<img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"> 
                    	</a>
                    	</td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  gov.sir.core.web.acciones.administracion.AWMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO%>');">
                    	<img src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" width="139" height="21" border="0"> 
                    	</a>
                    </td>
                    <td width="150" aling="left">
                    <a href="admin.view"><img  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" style=""></a>
                    </td>
                    <td>&nbsp;</td>
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
</body>
