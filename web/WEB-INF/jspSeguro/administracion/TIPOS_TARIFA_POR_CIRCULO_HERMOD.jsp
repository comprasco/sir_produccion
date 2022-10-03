<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoTarifa" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTarifa" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Tarifa" %>
<%@page import="gov.sir.core.negocio.modelo.TipoTarifa" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%

boolean toAgregar = true;
if(request.getParameter("CANCEL")!= null){
	//session.removeAttribute(CCirculo.ID_CIRCULO);
	session.removeAttribute(CTipoTarifa.ID_TIPO_TARIFA);
	session.removeAttribute(CTarifa.VALOR_TARIFA);
	//session.removeAttribute(WebKeys.LISTA_TIPOS_TARIFA_POR_CIRCULO);
	}

TextHelper textHelper = new TextHelper();

List tipos = (List)session.getAttribute(WebKeys.LISTA_TIPOS_TARIFA_POR_CIRCULO);
if(tipos == null){
	tipos = new ArrayList();
}


String tipoTarifa = (String)request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
if(tipoTarifa!=null){
	session.setAttribute(CTipoTarifa.ID_TIPO_TARIFA, tipoTarifa);
	session.removeAttribute(CTarifa.VALOR_TARIFA);
	for(Iterator iter = tipos.iterator(); iter.hasNext();){
        Tarifa dato = (Tarifa)iter.next();
        if(dato.getIdTipo().equals(tipoTarifa)){
        	session.setAttribute(CTarifa.VALOR_TARIFA, dato.getValor()+"");
        	toAgregar = false;
        }
		
	}
}
else{
	tipoTarifa = (String)session.getAttribute(CTipoTarifa.ID_TIPO_TARIFA);
	if(tipoTarifa!=null){
		toAgregar = false;
	}
}




	
if(session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO)==null){
	Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
	List circulos = usuario.getUsuarioCirculos();
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getCirculo().getNombre()));
		}
	session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementos)	;
	}

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);	

List tiposTarifa = (List)session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO);
if(tiposTarifa== null){
	tiposTarifa = new ArrayList();
	}
	
ListaElementoHelper tiposTarifaHelper = new ListaElementoHelper();
List elementosTarifa = new ArrayList();
	for (Iterator iter = tiposTarifa.iterator(); iter.hasNext();) {
		TipoTarifa tipoTar = (TipoTarifa) iter.next();
		elementosTarifa.add(new ElementoLista(tipoTar.getIdTipo(), tipoTar.getIdTipo()));
		}
tiposTarifaHelper.setTipos(elementosTarifa);	
	
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

function cambiarAccionAndSendTipoTarifa() {
	
	if(document.BUSCAR.<%= CCirculo.ID_CIRCULO %>.value!='<%= WebKeys.SIN_SELECCIONAR%>'){
		document.BUSCAR.action = 'admin.tipotarifaporcirculo.view';
		document.BUSCAR.submit();
	}
	
	
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Tipos de Tarifa por C&iacute;rculo HERMOD </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Tipos de Tarifa por C&iacute;rculo </td>
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
                    <td class="bgnsub">Tipos de Tarifa / C&iacute;rculo</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   (tipoTarifa==null || tipoTarifa.trim().equals(""))?"ADICIONA_ESTACION_RECIBO" : "RESET_ESTACION_RECIBO" %>" value="<%=  (tipoTarifa==null || tipoTarifa.trim().equals(""))?"ADICIONA_ESTACION_RECIBO" : "RESET_ESTACION_RECIBO" %>">
                <table width="100%" class="camposform">

                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculos Registrales Asociados al Usuario </td>
                    <td>
			  <% try {
                    circuloHelper.setNombre(CCirculo.ID_CIRCULO);
                  	circuloHelper.setCssClase("camposformtext");
                  	circuloHelper.setId(CCirculo.ID_CIRCULO);
                  	circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO+"')\"");
					circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>

                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="220">Tipos de Tarifa </td>
                    <td>
                    	<% 
                    try {
                    tiposTarifaHelper.setNombre(CTipoTarifa.ID_TIPO_TARIFA);
                  	tiposTarifaHelper.setCssClase("camposformtext");
                  	tiposTarifaHelper.setId(CTipoTarifa.ID_TIPO_TARIFA);
                  	tiposTarifaHelper.setFuncion("onChange=\"cambiarAccionAndSendTipoTarifa()\"");
					tiposTarifaHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Valor</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CTarifa.VALOR_TARIFA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTarifa.VALOR_TARIFA);
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
                    <td class="campostip04">Cuando termine de agregar o actualizar los tipos de tarifa haga click en aceptar. </td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    <%
                    if(toAgregar){
                     %>
                    	 <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    <%	
                    }
                    else{
                    %>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                     <%	 
                    } 
                    %>
                    </td>
                    <td align="center">
                  		<a href="admin.tipotarifaporcirculo.view?CANCEL=CANCEL"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" alt="Editar" width="139" height="21" border="0" ></a>               
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
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
                  <td>Listado</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">Tipo Tarifa</td>
                  <td class="titulotbcentral">Valor</td>
                  <td class="titulotbcentral">Versión Actual</td>
                  <td width="50" align="center">Actualizar</td>
                </tr>
                
                 <% 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	Tarifa dato = (Tarifa)iter.next();
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=  dato.getIdTipo()%></td>
                  <td class="campositem"><%=  dato.getValor()%></td>
                  <td class="campositem"><%=  dato.getIdVersion()%></td>
                  <td align="center">
                  		<a href="admin.tipotarifaporcirculo.view?<%=CTipoTarifa.ID_TIPO_TARIFA%>=<%=  dato.getIdTipo()%>"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" alt="Editar" width="35" height="13" border="0" ></a>               
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