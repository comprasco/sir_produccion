<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuarioSubtipoAtencion" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.SubtipoAtencion" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
Circulo circuloSes=(Circulo)session.getAttribute(WebKeys.CIRCULO);
List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_ABOGADOS_DE_CIRCULO);
boolean cargarDatos=false;
if(tipos == null){
	cargarDatos=true;
	tipos = new ArrayList();
}

if(session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO)==null){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		Circulo circulo = (Circulo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));
		}
	session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO, elementos)	;
	}

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);	

List subtipos = (List)application.getAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION);
	
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

function cargarSubtipos(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value ='<%=AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO%>';
	document.BUSCAR.<%= CCirculo.ID_CIRCULO %>.value ='<%=circuloSes.getIdCirculo()%>';
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n De Subtipos de Atenci&oacute;n para Calificadores</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Subtipos de Atenci&oacute;n para Calificadores </td>
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
                  
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO %>" value="<%=  AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO %>">
        <input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= CCirculo.ID_CIRCULO %>" value="<%=circuloSes.getIdCirculo()%>">
               <table width="100%" class="camposform"> 
	         <% 
	            for(Iterator iter = tipos.iterator(); iter.hasNext();){
	            	Usuario dato = (Usuario)iter.next();
	            	String usuarioId = ""+dato.getIdUsuario();
	            	List subtiposUsuarioAtencion = dato.getSubtiposAtencions();
	            	List subtiposList = new ArrayList();
	           		for(Iterator itSubUser = subtiposUsuarioAtencion.iterator(); itSubUser.hasNext();){
	            		UsuarioSubtipoAtencion u = (UsuarioSubtipoAtencion) itSubUser.next();
	            		SubtipoAtencion s = new SubtipoAtencion();
	            		s.setIdSubtipoAtencion(u.getIdSubtipoAtencion());
		           		subtiposList.add(s);
		            	}                      
	            %>
	            
	            <input  type="hidden" name="<%= CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ID %>" id="<%=  usuarioId %>" value="<%=  usuarioId %>">
	            <tr>
	              <td>&nbsp;</td>
                      <!--
                       * @author      :   Julio Alcázar Rivas
                       * @change      :   Se concatena al nombre del calificador los dos apellidos para mayor claridad
                       * Caso Mantis  :   05985
                      !-->
                      <td class="camposformtext_noCase"><%=  dato.getNombre() +" "+ ((dato.getApellido1()!= null)? dato.getApellido1():" " ) + " " + ((dato.getApellido2()!= null)? dato.getApellido2():" " )%></td>
	              <%
	              for(Iterator subtiposIter = subtipos.iterator(); subtiposIter.hasNext();){
	              		SubtipoAtencion subtipo = (SubtipoAtencion)subtiposIter.next();
						boolean contiene  =  subtiposList.contains(subtipo);
						
	              %>
	              <td class="campositem"><%=  subtipo.getNombre() %><input name="<%= CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_CHECK_+usuarioId %>" value="<%= subtipo.getIdSubtipoAtencion()%>"  type="checkbox" id="<%= CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_CHECK_ +usuarioId %>"  <%=  (contiene)?"checked":"" %>  ></td>
	              <%
	              		}
	              %>
	            </tr>
	            <% 
	             }
	             %>
	            </table> 
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"> 
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_ABOGADOS %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    	</td>
                    <td>
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
	<script>cargarSubtipos();</script>
<%}%>
