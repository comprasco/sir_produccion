<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.util.Collections" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.COficinaOrigen" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoOficina" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.Categoria" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="gov.sir.core.negocio.modelo.TipoOficina" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>

<%

if(session.getAttribute(AWAdministracionForseti.LISTA_FOTOCOPIAS)==null){
	List tiposOficina = (List)application.getAttribute(WebKeys.LISTA_TIPOS_OFICINA);
	List oficinas = new ArrayList();
	for (Iterator iter = tiposOficina.iterator(); iter.hasNext();) {
		TipoOficina tipo = (TipoOficina) iter.next();
		if (!tipo.getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_NOTARIA)){
			oficinas.add(	new ElementoLista(tipo.getIdTipoOficina(),tipo.getNombre()));
		}
		
		}	
	session.setAttribute(AWAdministracionForseti.LISTA_FOTOCOPIAS, oficinas);
	}
	

session.removeAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
if(session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS)==null){
	Map map = (Map) application.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);

	List listaDeptos = new ArrayList();
	List listaDptosSession = new ArrayList();
	for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
		String key = (String)iter.next();
		Departamento depto= (Departamento) map.get(key);
		listaDeptos.add(new ElementoLista(key, depto.getNombre()));
		listaDptosSession.add(depto);
			}
	session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS, listaDeptos)		;
	session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO, listaDptosSession);
}

if(session.getAttribute(CTipoOficina.ID_TIPO_OFICINA) ==null){
	session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, CTipoOficina.TIPO_OFICINA_NOTARIA);
	}
	

TextHelper textHelper = new TextHelper(); 
TextAreaHelper textAreaHelper = new TextAreaHelper();

List listaTiposOficina = (List)session.getAttribute(AWAdministracionForseti.LISTA_FOTOCOPIAS);
ListaElementoHelper tiposOficinaHelper = new ListaElementoHelper();
tiposOficinaHelper.setTipos(listaTiposOficina);

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

List tipos = (List)session.getAttribute(AWAdministracionForseti.LISTA_OFICINAS_POR_VEREDA);
if(tipos == null){
	tipos = new ArrayList();
}

if(session.getAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR) != null){
	OficinaOrigen oficina = (OficinaOrigen) session.getAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR);
	session.setAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN, oficina.getIdOficinaOrigen());
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_VERSION, oficina.getVersion().toString());
	session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, oficina.getTipoOficina().getIdTipoOficina());
	session.setAttribute(CDepartamento.ID_DEPARTAMENTO, oficina.getVereda().getMunicipio().getDepartamento().getNombre() + "-" + oficina.getVereda().getIdDepartamento());
	session.setAttribute(CMunicipio.ID_MUNICIPIO, oficina.getVereda().getMunicipio().getNombre() + "-" + oficina.getVereda().getIdMunicipio());
	session.setAttribute(CVereda.ID_VEREDA, oficina.getVereda().getIdVereda());
	session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO, oficina.getNumero()); 
	session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE, oficina.getNombre());
	session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO, oficina.getTelefono());
	session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA, oficina.getNaturalezaJuridicaEntidad());
	if(oficina.isExentoDerechoNotarial()){
		session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL, "TRUE");
	} else {
		session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL, "FALSE");
	}
		
	session.removeAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR);
}
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
function oficinas(nombre,valor,version,dimensiones)
{
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
	popup=window.open(nombre+'?<%=COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN%>='+valor+'&<%=COficinaOrigen.OFICINA_ORIGEN_VERSION%>='+version,'Detalles',dimensiones);
	popup.focus();
}
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	//document.BUSCAR.submit();
}

function cambiarAccionAndSend(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}


function editar(id,version){
	document.EDITAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionForseti.EDITAR_OFICINA_ORIGEN%>';
	document.EDITAR.<%=CTipoOficina.ID_TIPO_OFICINA%>.value = id;
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.EDITAR.<%=CTipoOficina.OFICINA_ORIGEN_VERSION%>.value = version;
	document.EDITAR.submit();
}

function eliminar(id){
	document.EDITAR.<%=WebKeys.ACCION%>.value = '<%=AWAdministracionForseti.ELIMINAR_OFICINA_ORIGEN%>';
	document.EDITAR.<%=CTipoOficina.ID_TIPO_OFICINA%>.value = id;
	document.EDITAR.submit();
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
          <td width="1*0" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n de Oficinas de Origen</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Oficina Origen </td>
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
            
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Oficina Origen </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>

                
      <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
     <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN %>" value="<%= AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN %>">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="230">Tipos de Oficina </td>
                    <td>                      
                      <% 
               try {
                    tiposOficinaHelper.setNombre(CTipoOficina.ID_TIPO_OFICINA);
                  	tiposOficinaHelper.setCssClase("camposformtext");
                  	tiposOficinaHelper.setId(CTipoOficina.ID_TIPO_OFICINA);
                 	//tiposOficinaHelper.setFuncion("onChange=\"visibilidadItemsNotaria()\"");
                 	tiposOficinaHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_TIPO_OFICINA+"')\"");
					tiposOficinaHelper.render(request,out);
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
                    <td width="230">Departamento</td>
                    <td>
               <% 
               try {
                    deptoHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
                  	deptoHelper.setCssClase("camposformtext");
                  	deptoHelper.setId(CDepartamento.ID_DEPARTAMENTO);
                  	deptoHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO+"')\"");
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
                    <td>Municipio</td>
                    <td>
                 <% 
                   try {
                    municipioHelper.setNombre(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setCssClase("camposformtext");
                  	municipioHelper.setId(CMunicipio.ID_MUNICIPIO);
                  	municipioHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA+"')\"");
                  	municipioHelper.render(request,out);
					} 
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					} 
			  %>
                    </td>
                  </tr>
                                
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">N&uacute;mero</td>
                    <td>
                     <% try {
		            textHelper.setNombre(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
		          	textHelper.setCssClase("camposformtext");
		          	textHelper.setId(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
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
                    <td width="180">Nombre Oficina </td>
                    <td>
                    <% try {
		            textHelper.setNombre(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
		          	textHelper.setCssClase("camposformtext");
		          	textHelper.setId(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
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
                    <td width="180">Teléfono Oficina </td>
                    <td>
                    <% try {
		            textHelper.setNombre(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
		          	textHelper.setCssClase("camposformtext");
		          	textHelper.setId(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                TextHelper versionHelper = new TextHelper();
                                versionHelper.setTipo("hidden");
                                versionHelper.setId(COficinaOrigen.OFICINA_ORIGEN_VERSION);
                                versionHelper.setNombre(COficinaOrigen.OFICINA_ORIGEN_VERSION);
                                versionHelper.render(request, out);
			  %>
                    </td>
                  </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <%if(session.getAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN) != null){%>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.ENVIAR_OFICINA_EDICION%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" width="139" height="21" border="0">
                    <%}else{%>	
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    <%}%>	
                   	</td> 
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
	            <table width="100%" class="camposform">
	                <tr>
	                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
	                  <td>ID</td>
	                  <td>Nombre</td>
	                  <td>N&uacute;mero</td>
	                  <td>Tel&eacute;fono</td>
                            <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                             --%>
                          <td>Version</td>
	                  <td width="50" align="center">Editar</td>
					  <td width="50" align="center">Ver Detalles</td>    
					  <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                                          <td width="50" align="center">Eliminar</td>                                 
                             --%>
	                </tr>
	                <form action="administracionForseti.do" method="POST" name="EDITAR" id="EDITAR">
	                <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>">
	                <input type="hidden" name="<%=CTipoOficina.ID_TIPO_OFICINA%>">
                        <input type="hidden" name="<%=CTipoOficina.OFICINA_ORIGEN_VERSION%>">
	                 <% 
	                 Collections.sort(tipos,new Comparator(){
						public int compare(Object arg0, Object arg1) {
							OficinaOrigen oo1=(OficinaOrigen)arg0;
							OficinaOrigen oo2=(OficinaOrigen)arg1;
							return (new Integer(oo1.getIdOficinaOrigen())).compareTo(new Integer(oo2.getIdOficinaOrigen()));
						}
			    	});
	                 
	                for(Iterator iter = tipos.iterator(); iter.hasNext();){
	                	OficinaOrigen dato = (OficinaOrigen)iter.next();
	                %> 
	                <tr>
	                  <td>&nbsp;</td>
	                  <td class="campositem"><%=  (dato.getIdOficinaOrigen()!=null)?dato.getIdOficinaOrigen():"" %>&nbsp;</td>
	                  <td class="campositem"><%=  (dato.getNombre()!=null)?dato.getNombre():"" %>&nbsp;</td>
	                  <td class="campositem"><%=(dato.getNumero()!=null)?dato.getNumero():"" %>&nbsp;</td>
	                  <td class="campositem"><%=(dato.getTelefono()!=null)?dato.getTelefono():"" %>&nbsp;</td>
                            <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                             --%>
                             <td class="campositem"><%=(dato.getVersion()!=null)?dato.getVersion().toString():"" %>&nbsp;</td>
						<td align="center">
                            <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                            --%>
	                  		<a href="javascript:editar(<%=dato.getIdOficinaOrigen()%>,<%=dato.getVersion().toString()%>)"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a> 
                            
	                  	</td>
						<td align="center">
	                  <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                             --%>
                                <a href="javascript:oficinas('admin.detalles.oficina.origen.view','<%=dato.getIdOficinaOrigen()%>','<%=dato.getVersion().toString()%>','scrollbars=yes,width=1000,height=600,menubar=no')"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a> 
	                  	</td>
                            <%--
                            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                            */
                           	<td align="center">
	                  		<a href="javascript:eliminar(<%=dato.getIdOficinaOrigen()%>)"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a> 
	                  	</td>
                             --%>
	                </tr>
	                <%
	                } 
	               %> 
	               </form>
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

