<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CZonaRegistral" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="gov.sir.core.negocio.modelo.ZonaRegistral" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper();

session.removeAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
if(session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS)==null){
	Map map = (Map) application.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);

	List listaDeptos = new ArrayList();
	for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
		String key = (String)iter.next();
		Departamento depto= (Departamento) map.get(key);
		listaDeptos.add(new ElementoLista(key, depto.getNombre()));
			}
	session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS, listaDeptos)		;	
}

List listaDeptos = (List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
ListaElementoHelper deptoHelper = new ListaElementoHelper();
deptoHelper.setTipos(listaDeptos);

ListaElementoHelper municipioHelper = new ListaElementoHelper();
if(session.getAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS)==null ){
	municipioHelper.setTipos(new ArrayList());
}
else{
	municipioHelper.setTipos((List)session.getAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS));
	}

ListaElementoHelper veredaHelper = new ListaElementoHelper();
if(session.getAttribute(AWAdministracionForseti.LISTA_VEREDAS)==null ){
	veredaHelper.setTipos(new ArrayList());
}
else{
	veredaHelper.setTipos((List)session.getAttribute(AWAdministracionForseti.LISTA_VEREDAS));
	}

List tipos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
if(tipos == null){
	tipos = new ArrayList();
	session.setAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES,tipos);
}

List tiposInhab=(List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_CIRC_INHAB);
if (tiposInhab==null){
	tiposInhab=new ArrayList();
	session.setAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_CIRC_INHAB,tiposInhab);
}
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionSubmit(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function validarEliminacion(nombre) {
	if (confirm('Esta seguro que desea eliminar la Zona Registral'))
	{    
      eval('document.ELIMINARZONA' +nombre + '.submit()');
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n de Zonas Registrales</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Configurar Zona Registral </td>
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
                    <td class="bgnsub">Circulo</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
            
                
	<form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
     <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL %>" value="<%= AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Agregar una Zona Registral </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Departamento</td>
                    <td>
                    <% try {
                    deptoHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
                  	deptoHelper.setCssClase("camposformtext");
                  	deptoHelper.setId(CDepartamento.ID_DEPARTAMENTO);
                  	deptoHelper.setFuncion("onChange=\"cambiarAccionSubmit('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_DEPTO_INHAB+"')\"");
					deptoHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  		%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Municipio </td>
                    <td>
                      <% try {
                    municipioHelper.setNombre(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setCssClase("camposformtext");
                  	municipioHelper.setId(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setFuncion("onChange=\"cambiarAccionSubmit('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_MUNICIPIO_INHAB+"')\"");
					municipioHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Vereda</td>
                    <td>
                    <% try {
                    veredaHelper.setNombre(CVereda.ID_VEREDA);
                  	veredaHelper.setCssClase("camposformtext");
                  	veredaHelper.setId(CVereda.ID_VEREDA);
					veredaHelper.render(request,out);
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
                    <td width="139">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.VOLVER_INHAB_CIRCULO_ZONAS  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                    <td width="139">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL_CIRC_INHAB  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_asociar.gif" width="139" height="21" border="0">
                    </td>
					<td width="139">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA_ZONA_CIRC_INHAB  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0">
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
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Zonas Actuales del Círculo Destino</td>
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
                <td width="40">&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
              	<td>&nbsp;</td>
              	<td class="titulotbcentral">ID</td>
              	<td class="titulotbcentral">Departamento (Id-Nombre)</td>
              	<td class="titulotbcentral">Municipio (Id-Nombre)</td>
              	<td class="titulotbcentral">Vereda (Id-Nombre)</td>
                <td width="50" align="center">Eliminar</td>
              </tr>
               <% 
               int idZona=0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	ZonaRegistral dato = (ZonaRegistral)iter.next();
                %>    
              <tr>
                <td>&nbsp;</td>
                <td class="campositem"><%= (dato.getIdZonaRegistral()!=null)?dato.getIdZonaRegistral():""%></td>
                <td class="campositem"><%= dato.getVereda().getIdDepartamento() %>-<%= dato.getVereda().getMunicipio().getDepartamento().getNombre() %></td>
                <td class="campositem"><%= dato.getVereda().getIdMunicipio() %>-<%= dato.getVereda().getMunicipio().getNombre() %></td>
                <td class="campositem"><%= dato.getVereda().getIdVereda() %>-<%= dato.getVereda().getNombre() %></td>
				<%
					if (dato.getIdZonaRegistral()==null){
						%>
					<form action="administracionForseti.do" method="post" name="ELIMINARZONA<%=idZona%>" id="ELIMINARZONA<%=idZona%>">
                		<td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL_INHAB %>" value="<%= AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL_INHAB %>">
	                	<input  type="hidden" name="<%= CDepartamento.ID_DEPARTAMENTO%>" id="<%= CDepartamento.ID_DEPARTAMENTO %>" value="<%= dato.getVereda().getIdDepartamento() %>">
						<input  type="hidden" name="<%=CMunicipio.ID_MUNICIPIO%>" id="<%= CMunicipio.ID_MUNICIPIO %>" value="<%= dato.getVereda().getIdMunicipio() %>">
						<input  type="hidden" name="<%=CVereda.ID_VEREDA%>" id="<%= CVereda.ID_VEREDA %>" value="<%= dato.getVereda().getIdVereda() %>">
	                	<input  type="hidden" name="<%=CCirculo.ID_CIRCULO%>" id="<%= session.getAttribute(CCirculo.ID_CIRCULO) %>" value="<%=session.getAttribute(CCirculo.ID_CIRCULO) %>">
                  		<a href="javascript:validarEliminacion('<%=idZona%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                  		</td>
                  	</form>
					<%}else{
						%>
						<td>&nbsp;</td>
					<%
					}
				%>
                
              </tr>
             	<%
             	idZona ++;  
                 }
               %>  
            </table>
            </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Zonas a Trasladar del Círculo a Inhabilitar</td>
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
                <td width="40">&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
              	<td>&nbsp;</td>
              	<td class="titulotbcentral">ID</td>
              	<td class="titulotbcentral">Departamento (Id-Nombre)</td>
              	<td class="titulotbcentral">Municipio (Id-Nombre)</td>
              	<td class="titulotbcentral">Vereda (Id-Nombre)</td>
              </tr>
               <% 
                for(Iterator iter = tiposInhab.iterator(); iter.hasNext();){
                	ZonaRegistral dato = (ZonaRegistral)iter.next();
                %>    
              		<tr>
                <td>&nbsp;</td>
                <td class="campositem"><%= (dato.getIdZonaRegistral()!=null)?dato.getIdZonaRegistral():""%></td>
                <td class="campositem"><%= dato.getVereda().getIdDepartamento() %>-<%= dato.getVereda().getMunicipio().getDepartamento().getNombre() %></td>
                <td class="campositem"><%= dato.getVereda().getIdMunicipio() %>-<%= dato.getVereda().getMunicipio().getNombre() %></td>
                <td class="campositem"><%= dato.getVereda().getIdVereda() %>-<%= dato.getVereda().getNombre() %></td>
				<%
				}
               %>  
            </table>
            </td>
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
