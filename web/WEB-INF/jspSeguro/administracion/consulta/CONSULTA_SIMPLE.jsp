<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="java.util.List"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsulta"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>

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
TextHelper idMatriculaHelper = new TextHelper();
ListaElementoHelper tiposDocHelper = new ListaElementoHelper();
ListaElementoHelper tiposPrediosHelper = new ListaElementoHelper();
ListaElementoHelper tiposEjesHelper = new ListaElementoHelper();

List tiposDocs = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ID);
tiposDocHelper.setOrdenar(false);
tiposDocHelper.setTipos(tiposDocs);

List tiposPredios = (List)application.getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
tiposPrediosHelper.setTipos(tiposPredios);

List tiposEjes = (List)application.getAttribute(WebKeys.LISTA_EJES_DIRECCION);
tiposEjesHelper.setTipos(tiposEjes);


List rolesUsuario = (List)session.getAttribute(WebKeys.LISTA_ROLES);
Circulo circuloUsuario = (Circulo)session.getAttribute(WebKeys.CIRCULO);
if( null == rolesUsuario ) {
  rolesUsuario = new java.util.ArrayList();
}
boolean isAdministradorNacional = false;


java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;

for( ;local_RolesListIterator.hasNext(); ) {
	local_RolesListElement =(org.auriga.core.modelo.transferObjects.Rol)local_RolesListIterator.next();
	String rol = local_RolesListElement.getRolId();
	if( rol.equals(CRoles.ADMINISTRADOR_NACIONAL) || rol.equals(CRoles.SIR_ROL_CONSULTA_INDICE_PROPIETARIO)) {
		isAdministradorNacional = true;
		break;
	}
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

//SI ES ADMINISTRADOR NACIONAL SE MOSTRAR?N TODOS LOS CIRCULOS PARA ESCOGER EN D?NDE CREAR EL USUARIO
//SINO ES ADMINISTRADOR NACIONAL SE MOSTRAR? ?NICAMENTE EL CIRCULO AL QUE PERTENECE EL USUARIO
List elementos = new java.util.ArrayList();
if(isAdministradorNacional){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA);
	for( java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
		gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
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
          idCirculoString = circuloTemp.getIdCirculo();
                if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                    idCirculoInt = Integer.parseInt(idCirculoString);
                    nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                    if(nombreCirculoDepartamento != ""){
                        elementos.add(new ElementoLista(circuloTemp.getIdCirculo(), circuloTemp.getNombre()));
                    }
                }
	} 
}

ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(elementos);

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
    <td>&nbsp;</td> <td>&nbsp;</td>  <td>&nbsp;</td>  <td>&nbsp;</td>  <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consultas</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Nueva Consulta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
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




        <form action="consultasAdministracion.do" method="post" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.EJECUTA_SIMPLE %>" value="<%= AWConsulta.EJECUTA_SIMPLE %>">
          <br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Consulta Indice de Propietario</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          <br>

          <!-- BUG 5893 -->

          <input id="NUMERO_MATRICULA_INMOBILIARIA" size="" name="NUMERO_MATRICULA_INMOBILIARIA"  type="hidden">

          <table width="100%" class="camposform">

            <tr>
              <td height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="179">Tipo de Identificaci&oacute;n</td>
              <td width="211">
			 <% try {
                    tiposDocHelper.setNombre(AWConsulta.TIPO_DOCUMENTO_CIUDADANO);
                  	tiposDocHelper.setCssClase("camposformtext");
                  	tiposDocHelper.setId(AWConsulta.TIPO_DOCUMENTO_CIUDADANO);
					tiposDocHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>

              <td class="contenido">N&uacute;mero de Documento de Identidad                  </td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(AWConsulta.DOCUMENTO_CIUDADANO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.DOCUMENTO_CIUDADANO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>

            </tr>
            <tr>
              <td width="20" height="18">&nbsp;</td>
              <td class="contenido">Nombre del Ciudadano en Anotaci&oacute;n </td>
              <td class="contenido">
               <% try {
                    textHelper.setNombre(AWConsulta.NOMBRE_CIUDADANO_ANOTACION);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.NOMBRE_CIUDADANO_ANOTACION);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
              <td class="contenido">Apellido 1 del Ciudadano en Anotaci&oacute;n (o raz&oacute;n social)</td>
              <td class="contenido">
                <% try {
                    textHelper.setNombre(AWConsulta.APELLIDO1_CIUDADANO_ANOTACION);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.APELLIDO1_CIUDADANO_ANOTACION);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
            </tr>

            <tr>
              <td width="20" height="18">&nbsp;</td>
              <td class="contenido">Apellido 2 del Ciudadano en Anotaci&oacute;n  </td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(AWConsulta.APELLIDO2_CIUDADANO_ANOTACION);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.APELLIDO2_CIUDADANO_ANOTACION);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
			  </td>
              <td class="contenido">Nombre Jur&iacute;dico en Anotaci&oacute;n </td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(AWConsulta.NOMBRE_JURIDICO_ANOTACION);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.NOMBRE_JURIDICO_ANOTACION);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %></td>
              <td class="contenido">&nbsp;</td>
              <td class="contenido">&nbsp;</td>
            </tr>
			<tr>
				     <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Circulo</td>
                    <td>
                    <%

                    try {
	                  	circuloHelper.setId( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
	                    circuloHelper.setNombre( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
						circuloHelper.setSelected(circuloUsuario.getIdCirculo());
	                  	circuloHelper.setCssClase("camposformtext");
	                  	circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			 		%>
                    </td>
                    <td colspan="4">&nbsp;</td>
			</tr>
          </table>

          <table width="100%" class="camposform">
			<tr>
			  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td class="contenido">Eje</td>
               <td width="211">
			 <% try {
			        tiposEjesHelper.setNombre(AWConsulta.TIPO_DE_EJE);
			      	tiposEjesHelper.setCssClase("camposformtext");
			      	tiposEjesHelper.setId(AWConsulta.TIPO_DE_EJE);
					tiposEjesHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				  %>
			  </td>
              <td class="contenido">Valor del Eje</td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(AWConsulta.VALOR_DEL_EJE);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.VALOR_DEL_EJE);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
            </tr>
            <tr>
              <td height="18">&nbsp;</td>
              <td class="contenido">Especificaci&oacute;n</td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(AWConsulta.DIRECCION_EN_FOLIO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsulta.DIRECCION_EN_FOLIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
              <td class="contenido">Tipo de Predio </td>
			   <td width="211">
			 <% try {
			        tiposPrediosHelper.setNombre(AWConsulta.TIPO_DE_PREDIO);
			      	tiposPrediosHelper.setCssClase("camposformtext");
			      	tiposPrediosHelper.setId(AWConsulta.TIPO_DE_PREDIO);
					tiposPrediosHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
			  </td>
            </tr>
            <tr>
              <td height="18">&nbsp;</td>
              <td class="contenido">N&uacute;mero Catastral en Folio </td>
              <td class="contenido">
              <% try {
                    textHelper.setNombre(CFolio.CODIGO_CATASTRAL_FOLIO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CFolio.CODIGO_CATASTRAL_FOLIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
              <td class="contenido">&nbsp;</td>
              <td class="contenido">&nbsp;</td>
            </tr>

          </table>
          <hr class="linehorizontal">

          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
              <td width="140">
              <input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsulta.EJECUTA_SIMPLE%>');" src="<%=request.getContextPath()%>/jsp/images/btn_buscar.gif" width="139" height="21" border="0"></td>
			  <td>
              	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsulta.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
              </td>
              <td>&nbsp;</td>
            </tr>
          </table>
<!--          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="179">Tipo de Identificaci&oacute;n</td>
              <td width="211">
			 <% try {
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
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>Primer Apellido</td>
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
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table> -->
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
