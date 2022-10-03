<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.COPLookupTypes" %>
<%@page import="gov.sir.core.negocio.modelo.FirmaRegistrador" %>
<%@page import="gov.sir.core.negocio.modelo.OPLookupCodes" %>
<%@page import="gov.sir.core.negocio.modelo.OPLookupTypes" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioCirculo" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y 
 * departamentos asociados a los mismos.
 */%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>

<%
TextHelper textHelper = new TextHelper();

Circulo circuloSeleccionado = (Circulo)session.getAttribute(AWAdministracionHermod.CIRCULO_SELECCIONADO);

List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_CIRCULO_FIRMAS_NOTARIALES);
if(tipos == null){
	tipos = new ArrayList();
}

List elementosLista = new ArrayList();
List cirs = null;
if(session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO)==null){
	Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
	cirs = usuario.getUsuarioCirculos();
	
	for (Iterator iter = cirs.iterator(); iter.hasNext();) {
		UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
		elementosLista.add(new ElementoLista(circulo.getIdCirculo(), circulo.getCirculo().getNombre()+"-"+circulo.getIdCirculo()));
	}

        /**
        * @autor HGOMEZ 
        * @mantis 13407 
        * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
        * @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
        * de departamentos por circulo.
        */
        List listaCirculoDepartamento = new Vector();
        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

        int idCirculoInt = 0;
        String nombreCirculoDepartamento = "";
        String idCirculoString = "";
        
	//VER SI ES ADMINISTRADOR NACIONAL
	List roles = (List)request.getSession().getAttribute(WebKeys.LISTA_ROLES);
	Iterator itRoles=roles.iterator();
	while(itRoles.hasNext()){
		Rol rol=(Rol)itRoles.next();
		if (rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
			elementosLista= new ArrayList();
			cirs = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
			for (Iterator iter = cirs.iterator(); iter.hasNext();) {
                            gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                            idCirculoString = circulo.getIdCirculo();
                            if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                                idCirculoInt = Integer.parseInt(idCirculoString);
                                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                                if(nombreCirculoDepartamento != ""){
                                    elementosLista.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                                }
                            }
			}
			break;
		}
		
	}

	session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementosLista)	;
}
	

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);	


String cargoPrincipal="";
String cargoDelegado="";
String cargoPrincipalEncargado = "";
String cargoDelegadoEncargado = "";

String cargoSeccional = "";
String cargoSeccionalEncargado = "";
String cargoSeccionalDelegado = "";
String cargoSeccionalDelegadoEncargado = "";

List lookupTypes = (List)application.getAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS);
List elementos = new ArrayList();
for (Iterator iter = lookupTypes.iterator(); iter.hasNext();) {
		OPLookupTypes dato = (OPLookupTypes) iter.next();
		if(dato.getTipo().equals(COPLookupTypes.CARGOS_REGISTRADOR)){
			List codes = dato.getOPLookupCodes();
			for(Iterator iter2 = codes.iterator(); iter2.hasNext();){
				OPLookupCodes code = (OPLookupCodes)iter2.next();
				if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL)){
					cargoPrincipal = code.getValor();
				}
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO)){
					cargoDelegado = code.getValor();
				}
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO)){
					cargoDelegadoEncargado = code.getValor();
				}
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO)){
					cargoPrincipalEncargado = code.getValor();
				} 
				
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL)){
					cargoSeccional = code.getValor();
				} 
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO)){
					cargoSeccionalEncargado = code.getValor();
				} 
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO)){
					cargoSeccionalDelegado = code.getValor();
				} 
				else if(code.getCodigo().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO)){
					cargoSeccionalDelegadoEncargado = code.getValor();
				} 																
				
				elementos.add(new ElementoLista(code.getCodigo(), code.getValor()));
			}
			break;
		}
	}

ListaElementoHelper cargosRegistradroHelper = new ListaElementoHelper();
cargosRegistradroHelper.setTipos(elementos);	

//borrar variables usadas en la session del popup
session.removeAttribute(AWAdministracionHermod.FIRMA_PREVIEW);
session.removeAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR);
session.removeAttribute(AWAdministracionHermod.LISTA_ELEMENTOS_FIRMAS_REGISTRADOR);
session.removeAttribute("CERRAR_VENTANA");

//Flag si ya hay una firma previewmente aceptada
FirmaRegistrador firmaR = (FirmaRegistrador)session.getAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR);


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

function buscarFirma(nombre,valor, dimensiones) {
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function submitIt()
   {
    document.forms['BUSCAR'].<%= WebKeys.ACCION %>.value = '<%=AWAdministracionHermod.RELOAD%>';
    document.forms['BUSCAR'].submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n de Archivos Gr&aacute;ficos de Firmas del Registrador </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Configuraci&oacute;n Firmas del Registrador</td>
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
                    <td class="bgnsub">Firmas del Registrador </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
	     <form name="BUSCAR" id="BUSCAR" action="administracionHermod.do"    enctype="multipart/form-data" method="POST" >
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR %>" value="<%=  AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculo Registral </td>
                    <td>
			 		<% try {
                    circuloHelper.setNombre(CCirculo.ID_CIRCULO);
                  	circuloHelper.setCssClase("camposformtext");
                  	circuloHelper.setId(CCirculo.ID_CIRCULO);
                  	circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR+"')\"");
					circuloHelper.render(request,out);			
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
		
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="220">Archivo Gr&aacute;fico de firma</td>
					<%String idCirculoFirma= (String) session.getAttribute(CCirculo.ID_CIRCULO);
					  if(idCirculoFirma!=null){%>
						<%if(firmaR!=null){%>
							<td><img src="<%=request.getContextPath()%>/jsp/images/ico_ok.gif" width="21" height="21"><a href="javascript:buscarFirma('buscar.firma.view','Buscar_firma','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="150" height="21" border="0" alt="buscar firma del registrador"><a></td></td>
					 	<%}else{%>
							<td><a href="javascript:buscarFirma('buscar.firma.view','Buscar_firma','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="150" height="21" border="0" alt="buscar firma del registrador"><a></td></td>
						<%} %>
					 	<td>&nbsp;</td> 
					 <%}else{%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
	 				  <%} %>
                  </tr>
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Activo </td>
                    <td>
			 			<input type="checkbox" name="<%= CFirmaRegistrador.FIRMA_REGISTRADOR_ESTADO %>"  value="checkbox"    >
                    </td>
                  </tr>
                <tr>
                  <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200">Nombre del Registrador </td>
                  <td>
                  <% try {
                    textHelper.setNombre(CFirmaRegistrador.FIRMA_REGISTRADOR_NOMBRE);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CFirmaRegistrador.FIRMA_REGISTRADOR_NOMBRE);
                  	textHelper.setSize("60");
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  	%>
                  </td>
                </tr>
                <tr>
                  <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200">Cargo del Registrador </td>
                  <td>
                  <% 
                  try {
                  	cargosRegistradroHelper.setNombre(CFirmaRegistrador.FIRMA_REGISTRADOR_CARGO);
                  	cargosRegistradroHelper.setCssClase("camposformtext");
                  	cargosRegistradroHelper.setId(CFirmaRegistrador.FIRMA_REGISTRADOR_CARGO);
					cargosRegistradroHelper.render(request,out);
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
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
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
                  <td class="titulotbcentral">Circulo</td>
				  <td class="titulotbcentral">Codigo del circulo</td>
                  <td class="titulotbcentral">Nombre del Archivo</td>
                  <td class="titulotbcentral">Estado</td>
                  <td class="titulotbcentral">Nombre del Registrador</td>
                  <td class="titulotbcentral">Cargo</td>
                  <td width="50" align="center">Activar Firma</td>
                  <td width="50" align="center">Desactivar Firma</td>
				  <td width="50" align="center">Desactivar Definitivamente</td>
                </tr>
                
                 <% 
                 int idRegistrador =0;
                String cargo = "";
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	FirmaRegistrador dato = (FirmaRegistrador)iter.next();
                	if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO)){
                		cargo = cargoDelegado;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL)){
                		cargo = cargoPrincipal;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO)){
                		cargo = cargoDelegadoEncargado;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO)){
                		cargo = cargoPrincipalEncargado;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL)){
                		cargo = cargoSeccional;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO)){
                		cargo = cargoSeccionalEncargado;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO)){
                		cargo = cargoSeccionalDelegado;
                	}
                	else if(dato.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO)){
                		cargo = cargoSeccionalDelegadoEncargado;
                	}                	                	                	                	
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=  (circuloSeleccionado==null)?"&nbsp;": circuloSeleccionado.getNombre() %></td>
				  <td class="camposformtext_noCase"><%=  (circuloSeleccionado==null)?"&nbsp;": circuloSeleccionado.getIdCirculo() %></td>
                  <td class="campositem"><%=  dato.getIdArchivo()%></td>
				  <%
					String acti="";
					if(dato.getActivo()== CFirmaRegistrador.INACTIVA_DEFINITIVA){
						acti= "Inactivo Definitivo";
					}else if (dato.getActivo()== CFirmaRegistrador.INACTIVA){
						acti= "Inactivo";
					}else{
						acti= "Activo";
					}
				  %>
                  <td class="campositem"><%=  acti  %> </td>
                  <td class="campositem"><%=  dato.getNombreRegistrador()%></td>
                  <td class="campositem"><%=  cargo %></td>
                  <form action="administracionHermod.do" method="post" name="ACTIVAR" id="ACTIVAR">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR %>" value="<%= AWAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR %>">
	                	<input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ARCHIVO %>" id="<%= dato.getIdArchivo()%>" value="<%= dato.getIdArchivo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ID_FIRMA_REGISTRADOR %>" id="<%= dato.getIdFirmaRegistrador()%>" value="<%= dato.getIdFirmaRegistrador()%>">
                  		<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_activar.gif" width="35" height="13" border="0">
                  </td>
                  </form>
                  <form action="administracionHermod.do" method="post" name="DESACTIVARREGISTRADOR<%=idRegistrador%>" id="DESACTIVARREGISTRADOR<%=idRegistrador%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR %>" value="<%= AWAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR %>">
	                	<input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ARCHIVO %>" id="<%= dato.getIdArchivo()%>" value="<%= dato.getIdArchivo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ID_FIRMA_REGISTRADOR %>" id="<%= dato.getIdFirmaRegistrador()%>" value="<%= dato.getIdFirmaRegistrador()%>">	                	
                  		<a href="javascript:validarEliminacion('<%=idRegistrador%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_desactivar.gif" width="35" height="13" border="0"></a>
                  </td>
                  </form>
				  <form action="administracionHermod.do" method="post" name="DESACTIVARREGISTRADORDEFINITIVAMENTE<%=idRegistrador%>" id="DESACTIVARREGISTRADORDEFINITIVAMENTE<%=idRegistrador%>">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR %>" value="<%= AWAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR %>">
	                	<input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ARCHIVO %>" id="<%= dato.getIdArchivo()%>" value="<%= dato.getIdArchivo()  %>">
	                	<input  type="hidden" name="<%= CFirmaRegistrador.ID_FIRMA_REGISTRADOR %>" id="<%= dato.getIdFirmaRegistrador()%>" value="<%= dato.getIdFirmaRegistrador()%>">	                	
                  		<a href="javascript:validarEliminacionDefinitiva('<%=idRegistrador%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_desactivar.gif" width="35" height="13" border="0"></a>
                  </td>
                  </form>
                </tr>
                <%
                idRegistrador++; 
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

<script>
function validarEliminacion(nombre) {

    alert ('Va a desactivar la firma de un Registrador');
	if (confirm('Esta seguro que desea desactivar la firma del Registador'))
	{
     
      eval('document.DESACTIVARREGISTRADOR' +nombre + '.submit()');
	}
}
function validarEliminacionDefinitiva(nombre) {

    alert ('Va a desactivar la firma de un Registrador DEFINITIVAMENTE');
	if (confirm('Esta seguro que desea desactivar la firma del Registador definitivamente'))
	{
     
      eval('document.DESACTIVARREGISTRADORDEFINITIVAMENTE' +nombre + '.submit()');
	}
}
</script>
