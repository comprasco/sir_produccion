<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.Hashtable" %>
<%@page import="gov.sir.core.web.helpers.consulta.*" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta" %>
<%
session.setAttribute(AWConsulta.ACCION_SIGUIENTE, AWConsulta.IMPRESION_CONSULTAS);
Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
Ciudadano ciudadano = solicitud.getCiudadano();
List busquedas = solicitud.getBusquedas();
int lastIndex = busquedas.size()-1;
Busqueda ultimaBusqueda = (Busqueda)busquedas.get(lastIndex);

if(session.getAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA)==null  ){
	List foliosSeleccionados = new Vector();
	Hashtable foliosSeleccionadosTable = new Hashtable();
	List solicitudesFolio = solicitud.getSolicitudFolios();
	for(Iterator iter = solicitudesFolio.iterator(); iter.hasNext();){
		SolicitudFolio solicitudFolio = (SolicitudFolio)iter.next();
		ResultadoFolio resultadoFolio = new ResultadoFolio();
		Folio folio = solicitudFolio.getFolio();
		resultadoFolio.setFolio(folio);
		foliosSeleccionados.add(resultadoFolio );
		foliosSeleccionadosTable.put(folio.getIdMatricula(),resultadoFolio);
	}
	session.setAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA, foliosSeleccionadosTable);
	session.setAttribute(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS, new Paginador(foliosSeleccionados));
}

TextHelper textHelper = new TextHelper();
ResultadoHelper resultadosHelper = new ResultadoHelper();
PaginadorHelper paginadorHelper  = new PaginadorHelper();
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
%>

<script language="JavaScript" type="text/JavaScript">

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
</script>

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<script type="text/javascript">
function cambiarMatricula(accion, matricula) {
	document.RESULTADO_CONSULTA.<%= WebKeys.ACCION %>.value = accion;
	document.RESULTADO_CONSULTA.<%= CFolio.NUMERO_MATRICULA_INMOBILIARIA %>.value = matricula;
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


            <form action="consultas.do" method="post" name="RESULTADO_CONSULTA" id="RESULTADO_CONSULTA">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.EJECUTA_COMPLEJA %>" value="<%= AWConsulta.EJECUTA_COMPLEJA %>">
            <input type="hidden" name="<%= CFolio.NUMERO_MATRICULA_INMOBILIARIA %>" id="" value="" >
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12">
                  <img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Consulta: <%= solicitud.getTipoConsulta().getNombre() %></td>
                  <td width="16" class="bgnsub">
                  <img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="16"><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></td>
                  <td class="campostip04">M&aacute;s de Tres B&uacute;squedas</td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">N&uacute;mero de Documento de Identidad </td>
                  <td >
                  	<input type="text" name="textfield" class="campositem" value="<%= (ultimaBusqueda.getNumeroDocCiudadano()==null)?"&nbsp;":ultimaBusqueda.getNumeroDocCiudadano()%>">
                  </td>
                  <td class="contenido">Nombre del Ciudadano en Anotaci&oacute;n </td>
                  <td >
                     <input type="text" name="textfield" class="campositem" value="<%= (ultimaBusqueda.getNombreCiudadano()==null)?"&nbsp;":ultimaBusqueda.getNombreCiudadano()%>">
                  </td>
                </tr>
                <tr>
                  <td width="20" height="18">&nbsp;</td>
                  <td class="contenido">Nombre Jur&iacute;dico en Anotaci&oacute;n </td>
                  <td >
                  <input type="text" name="textfield" class="campositem" value="<%= (ultimaBusqueda.getNombreNaturalezaJuridica()==null)?"&nbsp;":ultimaBusqueda.getNombreNaturalezaJuridica()%>">
                  </td>
                  <td class="contenido">&nbsp;</td>
                  <td class="contenido">&nbsp;</td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Direcci&oacute;n en Folio </td>
                  <td >
                  <input type="text" name="textfield" class="campositem" value="<%= (ultimaBusqueda.getDireccion()==null)?"&nbsp;":ultimaBusqueda.getDireccion()%>">
                  </td>
                  <td class="contenido">Tipo de Predio </td>
			   <td width="211">
			 <% try {
			 		session.setAttribute(AWConsulta.TIPO_DE_PREDIO, ultimaBusqueda.getIdTipoPredio());
			        tiposPrediosHelper.setNombre(AWConsulta.TIPO_DE_PREDIO);
			      	tiposPrediosHelper.setCssClase("campositem");
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
   			 		session.setAttribute(CFolio.CODIGO_CATASTRAL_FOLIO, ultimaBusqueda.getNumeroCatastral());
                    textHelper.setNombre(CFolio.CODIGO_CATASTRAL_FOLIO);
                  	textHelper.setCssClase("campositem");
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

                            <tr>
              <td height="18">&nbsp;</td>
              <td class="contenido">Eje</td>
               <td width="211">
			 <% try {
			 		session.setAttribute(AWConsulta.TIPO_DE_EJE, ultimaBusqueda.getIdEje());
			        tiposEjesHelper.setNombre(AWConsulta.TIPO_DE_EJE);
			      	tiposEjesHelper.setCssClase("campositem");
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
              		session.setAttribute(AWConsulta.VALOR_DEL_EJE, ultimaBusqueda.getValorEje());
                    textHelper.setNombre(AWConsulta.VALOR_DEL_EJE);
                  	textHelper.setCssClase("campositem");
                  	textHelper.setId(AWConsulta.VALOR_DEL_EJE);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
              </td>
            </tr>

              </table>
              <hr class="linehorizontal">


              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
			  	 <% try {
			  	 	String tipoDocumento = "&nbsp;";
			  	 	if(ciudadano != null){
			  	 		tipoDocumento =  ciudadano.getTipoDoc();
			  	 		}
                    session.setAttribute(CCiudadano.TIPODOC,tipoDocumento);
                    textHelper.setNombre(CCiudadano.TIPODOC);
                  	textHelper.setCssClase("campositem");
                  	textHelper.setId(CCiudadano.TIPODOC);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212">
              <% try {
              		String documento = "&nbsp;";
			  	 	if(ciudadano != null){
			  	 		documento =  ciudadano.getDocumento();
			  	 		}
       		        session.setAttribute(CCiudadano.IDCIUDADANO,documento);
                    textHelper.setNombre(CCiudadano.IDCIUDADANO);
                  	textHelper.setCssClase("campositem");
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
                   String apellido1 = "&nbsp;";
			  	 	if(ciudadano != null){
			  	 		apellido1 =  ciudadano.getApellido1();
			  	 		}
       		        session.setAttribute(CCiudadano.APELLIDO1, apellido1);
                    textHelper.setNombre(CCiudadano.APELLIDO1);
                  	textHelper.setCssClase("campositem");
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
					String apellido2 = "&nbsp;";
			  	 	if(ciudadano != null){
			  	 		apellido2 =  ciudadano.getApellido2();
			  	 		}
					session.setAttribute(CCiudadano.APELLIDO2, apellido2);
                    textHelper.setNombre(CCiudadano.APELLIDO2);
                  	textHelper.setCssClase("campositem");
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
             		String nombre = "&nbsp;";
			  	 	if(ciudadano != null){
			  	 		nombre =  ciudadano.getNombre();
			  	 		}
		            session.setAttribute(CCiudadano.NOMBRE, nombre);
                    textHelper.setNombre(CCiudadano.NOMBRE);
                  	textHelper.setCssClase("campositem");
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
              </table>
              <hr class="linehorizontal">


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Resultados de la Consulta</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                   <td><img src="<%=request.getContextPath()%>/jsp/images/ico_resultados_busqueda.gif" width="16" height="21"></td>
	                </tr>
                </table>
                </td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table>
          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
		       <table width="100%" border="0" cellpadding="0" cellspacing="0">
		            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
		            <tr>
		              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		              <td class="bgnsub">Datos del Resultado </td>
		              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resultados_busqueda.gif" width="16" height="21"></td>
		              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		            </tr>
		       </table>

<hr class="linehorizontal">
      <%
      try {
      	resultadosHelper.setTipo(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
		resultadosHelper.drawGUI(request, out);
		paginadorHelper.setId(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
		paginadorHelper.setVista("consultas.compleja.imprimir.view");
		paginadorHelper.drawGUI(request, out);
		}
	  catch(HelperException re){
		 out.println("ERROR " + re.getMessage());
		}

      %>

	<hr class="linehorizontal">

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
       <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
          <td class="bgnsub">Imprimir</td>
          <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anexar.gif" width="16" height="21"></td>
          <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
      </table>

      <table width="100%" class="camposform">
        <tr>
          <td width="20">
          <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
          <td width="140">
          <input name="imageField" type="image" onClick="cambiarMatricula('<%=AWConsulta.IMPRIMIR%>','');" src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0">
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </td>
    <br>
    </form>


          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
          <td>
          <img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td>
          <img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
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
