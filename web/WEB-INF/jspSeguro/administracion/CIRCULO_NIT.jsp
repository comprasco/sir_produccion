<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);

String oficina = request.getParameter("OFICINA");
if(oficina!=null  &&  !oficina.trim().equals("")){
	for(Iterator iter = tipos.iterator(); iter.hasNext(); ){
		Circulo circulo  = (Circulo)iter.next();
		if(circulo.getIdCirculo().equals(oficina)){
			session.setAttribute(CCirculo.ID_CIRCULO, circulo.getIdCirculo());
			session.setAttribute(CCirculo.NOMBRE_CIRCULO, circulo.getNombre());
			session.setAttribute(CCirculo.NIT_CIRCULO, circulo.getNit());
			}
		}
	}
	
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Oficinas</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Editar NIT de Oficina </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
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
                    <td class="bgnsub">Oficinas </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionForseti.EDITA_CIRCULO_NIT %>" value="<%= AWAdministracionForseti.EDITA_CIRCULO_NIT %>">
        <input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= session.getAttribute(CCirculo.ID_CIRCULO) %>" value="<%=  session.getAttribute(CCirculo.ID_CIRCULO) %>">
             <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200" class="titulotbcentral">Editar NIT de Oficina </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>C&oacute;digo de la oficina </td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CCirculo.ID_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.ID_CIRCULO);
					textHelper.setEditable(false);
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
                    <td>Nombre de la Oficina</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCirculo.NOMBRE_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.NOMBRE_CIRCULO);
                  	textHelper.setEditable(false);
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
                    <td>NIT de la Oficina </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCirculo.NIT_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.NIT_CIRCULO);
                  	textHelper.setEditable(true);
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
                    <td class="campostip04">Cuando termine de Configurar los NIT para las oficinas haga click en aceptar. </td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.EDITA_CIRCULO_NIT%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
               
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Listado de Oficinas </td>
                  <td width="50" align="center">Editar</td>
                </tr>
                
                
             <% 
            for(Iterator iter = tipos.iterator(); iter.hasNext();){
              	Circulo dato = (Circulo)iter.next();
            %>   
            <tr>
              <td>&nbsp;</td>
              <td class="campositem"><%=   dato.getNombre()  %></td>
              <td align="center">
             	<a href="admin.nitoficina.view?OFICINA=<%=  dato.getIdCirculo()%>"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" alt="Editar" width="35" height="13" border="0" ></a>               
              </td>
            </tr>
            <% 
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