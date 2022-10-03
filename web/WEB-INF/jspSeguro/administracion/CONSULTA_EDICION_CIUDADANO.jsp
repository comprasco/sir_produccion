<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano"%>
<%@page import="java.util.*"%>
<%@page import="org.auriga.smart.SMARTKeys" %>
<% 

   	ListaElementoHelper certHelper = new ListaElementoHelper();
   	ListaElementoHelper tarifasHelper = new ListaElementoHelper();
   	ListaElementoHelper docHelper = new ListaElementoHelper();
   	ListaElementoHelper impresorasHelper = new ListaElementoHelper();
   
   	TextHelper textHelper = new TextHelper();
   	TextHelper textHelperCopias = new TextHelper();
   	
   	String mensaje = (String) session.getAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_MENSAJE);
   
	String vistaActual = (String) session.getAttribute(SMARTKeys.VISTA_ACTUAL);
	session.setAttribute(WebKeys.VISTA_VOLVER, vistaActual);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function cambiarAccionFiltro(text) {
	document.FILTRAR.<%= WebKeys.ACCION %>.value = text;
	document.FILTRAR.submit();
}

function validarCiudadano(text){
	if (eval('document.BUSCAR.IDCIUDADANO.value !=""')){
 		cambiarAccion('VALIDAR_CIUDADANO_EDICION');
	}
}

</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta de Ciudadano Edición</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td></td>
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
                
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="12"><img name="sub_r1_c1"
					src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
					width="12" height="22" border="0" alt=""></td>
				<td
					background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
					class="bgnsub">Datos Ciudadano</td>
				<td width="16" class="bgnsub"><img
					src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif"
					width="16" height="21"></td>
				<td width="16" class="bgnsub"><img
					src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
					width="16" height="21"></td>
				<td width="15"><img name="sub_r1_c4"
					src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
					width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>
		
		<%if(mensaje!=null) {%>
			  <br>
			 <hr class="linehorizontal">    
			<table width="100%" class="camposform">
			<tr>
				<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/satisfactorio_animated.gif" width="30" height="20"></td>
				<td width="200%" align="center" valign="middle" class="titulotbcentral2"><%=mensaje%></td>
			</tr>
				</table>
   		<%}%>
        <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO %>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO %>">
                <table width="100%" class="camposform">
                  <tr> 
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
                      <% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		
                      		if(docs == null){
                      			docs = new Vector();
                      		}
                      		
                      		List docsDef = new ArrayList();
                      		
                      		//Se debe quitar Secuencia
                      		for (int i= 0 ; i<docs.size();i++){
	                      		ElementoLista  elem = (ElementoLista) docs.get(i);
	                      		//System.out.println("tipo" +  elem.getValor() + " : " + elem.getId());
	                      		if (!elem.getId().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
		                      		docsDef.add(elem);
	                      		}
	                      		
                      		}
               				String selected = (String)session.getAttribute(CCiudadano.TIPODOC);
               				if(selected==null){
               					selected = CCiudadano.TIPO_DOC_ID_CEDULA;
               				}                     		

							docHelper.setOrdenar(false);
                   			docHelper.setTipos(docsDef);
		                    docHelper.setNombre(CCiudadano.TIPODOC);
                   			docHelper.setId(CCiudadano.TIPODOC);		                        
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
                  <% try {
 		                        textHelper.setNombre(CCiudadano.IDCIUDADANO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.IDCIUDADANO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td>
                    <input name="imageField" type="image" onClick="validarCiudadano();"  src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                  </table>
              <hr class="linehorizontal">
              
              <%
				Ciudadano ciudadanoToEdit = (Ciudadano) session.getAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT);
				if (ciudadanoToEdit!=null){              
              %>
              	<table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Id Ciudadano:</div></td>
                    <td width="200">
                    <%
                    try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);
	                	textHelper.setCssClase("camposformtext");
                        textHelper.setEditable(false);
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
		 		  </td>
                  <td>&nbsp;</td>
               </tr>
               <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Num. Documento:</div></td>
                    <td width="200">
                    <%
                    try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
	                	textHelper.setCssClase("camposformtext");
                        textHelper.setEditable(true);
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
				  	</td>
                  	<td>&nbsp;</td>
	                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Tipo Documento:</div></td>
                    <td width="200">
                    <%
					try {
                      docHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
                      docHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
                      docHelper.setCssClase("camposformtext");
                      docHelper.render(request, out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
			    	</td>
                  	<td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Nombre:</div></td>
                    <td width="200">
                    <%
					try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
	                	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
		 	 		</td>
                  	<td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Apellido<sub>1</sub>:</div></td>
                    <td width="200">
                    <%
                    try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
	                	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
		  			</td>
                  	<td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Apellido<sub>2</sub>:</div></td>
                    <td width="200">
                    <%
                    try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
	                	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
		 	 		</td>
                  	<td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td align="left" width="150"><div align="right" >Telefono:</div></td>
                    <td width="200">
                    <%
					try {
                        textHelper.setId(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);
	                	textHelper.setNombre(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);
	                	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
                    } catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
                    }
                    %>
		  			</td>
                  	<td>&nbsp;</td>
                </tr>
                 <tr>
                 <td>&nbsp;</td>
                 <td>&nbsp;</td>
                 <td>&nbsp;</td>
                 <td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="20"></td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%= AWTrasladoTurno.REALIZAR_CIUDADANO_EDICION%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_corregir.gif" width="150" height="21" border="0">
                    	</td>
                  	<td>&nbsp;</td>
                </tr>
              </table>
			  <hr class="linehorizontal">              
              <%
				} 
              %>

                <br>
                <br>
               
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	
                    	</td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWTrasladoTurno.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>

