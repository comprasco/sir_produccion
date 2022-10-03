<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano" %>
<%@page import="gov.sir.core.negocio.modelo.CiudadanoProhibicion" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionCiudadano" %>
<%@page import="org.auriga.util.FechaConFormato"%>

<%
TextHelper textHelper = new TextHelper();
ListaElementoHelper tiposDocHelper = new ListaElementoHelper();
List tiposDocs = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ID);
tiposDocHelper.setOrdenar(false);
tiposDocHelper.setTipos(tiposDocs);

Ciudadano ciudadano = (Ciudadano)session.getAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO  );
Ciudadano ciudadanoCreado = (Ciudadano)session.getAttribute(AWAdministracionCiudadano.CIUDADANO_CREADO);
session.removeAttribute(AWAdministracionCiudadano.CIUDADANO_CREADO);

ListaElementoHelper docHelper = new ListaElementoHelper();
%>

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Restricciones </td>
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
				<%
					if(ciudadanoCreado != null) {
				%>
				El ciudadano con el documento <%= ciudadanoCreado.getDocumento() %> fue creado satisfactoriamente
				<%
					}
				%>
         <form action="administracionCiudadano.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionCiudadano.CIUDADANO_CONSULTAR  %>" value="<%= AWAdministracionCiudadano.CIUDADANO_CONSULTAR  %>">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="150">Tipo de Documento</td>
                    <td width="150">                     
                 <% 
              try {
                    tiposDocHelper.setNombre(CCiudadano.TIPODOC);
                  	tiposDocHelper.setCssClase("camposformtext");
                  	tiposDocHelper.setId(CCiudadano.TIPODOC);
					tiposDocHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="120">N&uacute;mero de Documento</td>
                    <td>
             <% try {
                    textHelper.setNombre(CCiudadano.DOCUMENTO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadano.DOCUMENTO);
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
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de consular Ciudadanos haga click en aceptar. </td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionCiudadano.CIUDADANO_CONSULTAR %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    	</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                  <tr> 
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
                      <% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		
               				String selected = (String)session.getAttribute(CCiudadano.TIPODOC);
               				if(selected==null){
               					selected = CCiudadano.TIPO_DOC_ID_SECUENCIA;
               				}                      		
                      		
                      		if(docs == null){
                      			docs = new Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		                        docHelper.setNombre(CCiudadano.TIPODOC + "1");
                   			    docHelper.setId(CCiudadano.TIPODOC + "1");		                        
                  			    docHelper.setSelected(selected);
                   			    docHelper.setCssClase("camposformtext");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212">
                  	<% 
                  		try {
							textHelper.setNombre(CCiudadano.IDCIUDADANO);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CCiudadano.IDCIUDADANO);
							textHelper.render(request,out);
						} catch(HelperException re) {
							out.println("ERROR " + re.getMessage());
						}
					%>
                  </td>
				</tr>
                <tr> 
                  <td>&nbsp;</td>
                  <td>Primer Apellido / Raz&oacute;n Social</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.APELLIDO1);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO1);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td>Segundo Apellido</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.APELLIDO2);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO2);
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
                  <td>Nombre</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.NOMBRE);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.NOMBRE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  
                  <td>Tel&eacute;fono</td>
                  <td>
                  <% 
                  try 
                  {
 		          	textHelper.setNombre(CCiudadano.TELEFONO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadano.TELEFONO);
					textHelper.render(request,out);
				  }
				  catch(HelperException re)
				  {
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
	                   	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionCiudadano.CIUDADANO_CREAR %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_crear.gif" width="139" height="21" border="0">
                   	</td>
					<td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionCiudadano.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
		
		
		
	<% 
	if(ciudadano != null ){
		String nombre =( ( ciudadano.getNombre()==null)?"": ciudadano.getNombre()) +" "+ ((ciudadano.getApellido1()==null)?"":ciudadano.getApellido1())+" "+ ( (ciudadano.getApellido2()==null)?"":ciudadano.getApellido2());
		
		List prohibiciones =  ciudadano.getProhibiciones();
		List prohibicionesActivas = new ArrayList();
		if(prohibiciones != null && !prohibiciones.isEmpty()){
			for(Iterator iter = prohibiciones.iterator(); iter.hasNext();){
				CiudadanoProhibicion ciudProhib = (CiudadanoProhibicion)iter.next();
				if(ciudProhib.getFechaFinal()==null){
					prohibicionesActivas.add(ciudProhib);
					}
			}
		}
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
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
                  <td>Listado</td>
                </tr>
            </table>
              <table width="100%" class="camposform">
                <tr>
                  <td>Nombre Ciudadano </td>
                </tr>
                <tr>
                  <td class="campositem"><%=  nombre  %></td>
                </tr>


		<%
                if(!prohibicionesActivas.isEmpty()){
                %>

               <tr>
                  <td><br>&nbsp;&nbsp;Restricciones Activas </td>
                </tr>
                <tr>
                  <td >
				  <table width="100%" border="0">
                    <tr class="camposform">
                      <td >&nbsp;Restricci&oacute;n</td>
                      <td>Comentario</td>
                      <td>Fecha de Creaci&oacute;n </td>
                    </tr>
					<% 
					for(Iterator iter = prohibicionesActivas.iterator(); iter.hasNext();){
						CiudadanoProhibicion ciudProhib = (CiudadanoProhibicion)iter.next();
					%>
                    <tr>
                      <td class="campositem"><%=   ciudProhib.getProhibicion().getNombre()   %></td>
                      <td class="campositem"><%=  ((ciudProhib.getComentario()==null)?"&nbsp;": ciudProhib.getComentario())%></td>
                      <td class="campositem"><%= ((ciudProhib.getFechaInicial() ==null)?"&nbsp;":FechaConFormato.formatear(ciudProhib.getFechaInicial() , "dd/MM/yyyy  HH:mm:ss")) %></td>
                    </tr>
					<%  
					}
					%>
                  </table>
				  </td>
                </tr>
                <%
                }
                %>
          
                <tr>
                <form action="administracionCiudadano.do" method="POST" name="EDITAR" id="EDITAR">
        		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionCiudadano.CIUDADANO_SELECCIONAR  %>" value="<%= AWAdministracionCiudadano.CIUDADANO_SELECCIONAR  %>">
                  <td align="right">
                  <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                  </td>
                  </FORM>
                </tr>
              </table>
              <hr class="linehorizontal">
              <hr class="linehorizontal"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
        </table>
   <% 
		}
	%>
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