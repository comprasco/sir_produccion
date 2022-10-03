<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano" %>
<%@page import="gov.sir.core.negocio.modelo.Prohibicion" %>
<%@page import="gov.sir.core.negocio.modelo.CiudadanoProhibicion" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionCiudadano" %>
<%@page import="org.auriga.util.FechaConFormato"%>

<%
TextHelper textHelper = new TextHelper();
if(session.getAttribute(AWAdministracionCiudadano.LISTA_ELEMENTO_PROHIBICIONES ) == null){ 
	List tipos = (List)application.getAttribute(WebKeys.LISTA_PROHIBICIONES);
	List elementos = new ArrayList();
	for(Iterator iter = tipos.iterator(); iter.hasNext();){
		Prohibicion prohibicion = (Prohibicion)iter.next();
		elementos.add(new ElementoLista(prohibicion.getIdProhibicion(), prohibicion.getNombre()));
	}
	session.setAttribute(AWAdministracionCiudadano.LISTA_ELEMENTO_PROHIBICIONES,elementos );
}


ListaElementoHelper tiposHelper = new ListaElementoHelper();
List tiposProhibicion  = (List)session.getAttribute(AWAdministracionCiudadano.LISTA_ELEMENTO_PROHIBICIONES) ;
tiposHelper.setTipos(tiposProhibicion );

Ciudadano ciudadano = (Ciudadano)session.getAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO);
String nombre =( ( ciudadano.getNombre()==null)?"": ciudadano.getNombre()) +" "+ ((ciudadano.getApellido1()==null)?"":ciudadano.getApellido1())+" "+ ( (ciudadano.getApellido2()==null)?"":ciudadano.getApellido2());
List prohibiciones =  ciudadano.getProhibiciones();
List prohibicionesActivas = new ArrayList();
if(prohibiciones != null && !prohibiciones.isEmpty()){
	for(Iterator iter = prohibiciones.iterator(); iter.hasNext();){
		CiudadanoProhibicion ciudProhib = (CiudadanoProhibicion)iter.next();
		if(ciudProhib.getFechaFinal()==null){
			prohibicionesActivas.add(ciudProhib); 
			}
}	}
%>

<script type="text/javascript">

function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function eliminarRestriccion(indice){
    var formulario=document.getElementById("DATOS"+indice);
	var valor=formulario.COMENTARIOANULACION.value;
	var formularioEliminar=document.getElementById("ELIMINAR"+indice);
	formularioEliminar.COMENTARIO_ANULACION.value=valor;
}

</script>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Restricciones Ciudadano FORSETI</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar Restricciones Ciudadano </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
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
                    <td class="bgnsub">Datos</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                <table width="100%" class="camposform">
                  <tr>
                    <td>Nombre Ciudadano </td>
                  </tr>
                  <tr>
                    <td class="campositem"><%=  nombre  %></td>
                  </tr>
                </table>
          
          	<%
                if(!prohibicionesActivas.isEmpty()){
                %>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td>Restricciones Activas </td>
       
                  </tr>
                   <tr>
                  <td >
				  <table width="100%" border="0">
                    <tr class="camposform">
                       <td >&nbsp;Restricci&oacute;n</td>
                       <td>Comentario</td>
                       <td>Fecha de Creaci&oacute;n </td>

                       <td>Comentario Anulaci&oacute;n</td>

                       <td align="center">Eliminar</td>
                    </tr>
					<% int i=0;
					for(Iterator iter = prohibicionesActivas.iterator(); iter.hasNext();){
						CiudadanoProhibicion ciudProhib = (CiudadanoProhibicion)iter.next();
					%>
                    <tr>
                      
                    <form action="administracionCiudadano.do" method="post" name="DATOS<%=i%>" id="DATOS<%=i%>">
                      <td class="campositem"><%=  ciudProhib.getProhibicion().getNombre()  %></td>
                      <td class="campositem"><%=  ((ciudProhib.getComentario()==null)?"&nbsp;": ciudProhib.getComentario())%></td>
                      <td class="campositem"><%= ((ciudProhib.getFechaInicial() ==null)?"":FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy HH:mm:ss")) %></td>
						<td>
  					       <input id="COMENTARIOANULACION" name="COMENTARIOANULACION" class="camposformtext" 
  					              value=<%=ciudProhib.getComentarioAnulacion()!=null?ciudProhib.getComentarioAnulacion():""%>>
                        </td>
</form>
                    
				
                    <form action="administracionCiudadano.do" method="post" name="ELIMINAR<%=i%>" id="ELIMINAR<%=i%>">                     
                      <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionCiudadano.CIUDADANO_ELIMINAR_PROHIBICION %>" value="<%= AWAdministracionCiudadano.CIUDADANO_ELIMINAR_PROHIBICION %>">
	                	<input  type="hidden" name="<%= CProhibicion.PROHIBICION_ID %>" id="<%= ciudProhib.getIdProhibicion()%>" value="<%= ciudProhib.getIdProhibicion() %>">
	                	<input  type="hidden" name="<%= CCiudadano.IDCIUDADANO %>" id="<%= ciudProhib.getIdCiudadano() %>" value="<%= ciudProhib.getIdCiudadano() %>">
	                	<!--<input  type="hidden" name="<%= CCiudadanoProhibicion.CIUDADANO_PROHIBICION_FECHA_ID%>" id="<%= FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy hh:mm:ss")   %>" value="<%= FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy hh:mm:ss")  %>">-->
						<input  type="hidden" name="<%= CCiudadanoProhibicion.CIUDADANO_PROHIBICION_FECHA_ID%>" id="<%= FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy HH:mm:ss")   %>" value="<%= FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy HH:mm:ss")  %>">
	                	<!--<input  type="hidden" name="<%= CCiudadanoProhibicion.CIUDADANO_PROHIBICION_FECHA_ID%>" id="<%= ""+ciudProhib.getFechaInicial().getTime() %>" value="<%= ""+ciudProhib.getFechaInicial().getTime() %>">-->
                      
                      	<input  type="hidden" name="<%= CProhibicion.COMENTARIO_ANULACION %>" id="<%= CProhibicion.COMENTARIO_ANULACION %>" value=<%=ciudProhib.getComentarioAnulacion()!=null?ciudProhib.getComentarioAnulacion():""%>>

                      <td align="center">
                      <input name="imageField" type="image" onclick="eliminarRestriccion('<%=i%>');" src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0">
                      </td>
                      </form>
                    </tr>
					<%  i++;
					}
					%>
                  </table>
				  </td>
                </tr>
                </table>
                 <%
                }
                %>
                
                
                <br>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Agregar Restricciones </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
         <form action="administracionCiudadano.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionCiudadano.CIUDADANO_ADICIONAR_PROHIBICION   %>" value="<%= AWAdministracionCiudadano.CIUDADANO_ADICIONAR_PROHIBICION  %>">
                <table width="100%" class="camposform">
                  <tr valign="top">
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Agregar Restriciones</td>
                  </tr>
                  <tr valign="top">
                    <td>&nbsp;</td>
                    <td class="campostip04">Seleccione la Restricci&oacute;n  que desee adicionar al ciudadano. </td>
                  </tr>
                  <tr valign="top">
                    <td width="20">Restricci&oacute;n </td>
                    <td>
                     <% 
              try {
                    tiposHelper.setNombre(CProhibicion.PROHIBICION_ID);
                  	tiposHelper.setCssClase("camposformtext");
                  	tiposHelper.setId(CProhibicion.PROHIBICION_ID);
					tiposHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="120">Comentario </td>
                    <td>
             <% try {
                    textHelper.setNombre(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_COMENTARIO  );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_COMENTARIO);
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
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                    <td>&nbsp;</td>
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
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
				  <%--
                  <tr>
                    <td>&nbsp;</td>
                    <td class="campostip04">Si ha terminado de agregar y/o eliminar restricciones Haga click en aceptar. </td>
                  </tr>
                  --%>
                  <form action="administracionCiudadano.do" method="post" name="TERMINAR" id="TERMINAR">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td>
                      <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionCiudadano.CIUDADANO_CANCELAR_EDICION %>" value="<%= AWAdministracionCiudadano.CIUDADANO_CANCELAR_EDICION %>">
                    <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                  </form>
              </table></td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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