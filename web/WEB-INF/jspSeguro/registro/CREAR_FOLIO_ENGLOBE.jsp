<%@page import="org.auriga.core.web.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%> 
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.registro.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%
	
	ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	List tiposPredio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
	if(tiposPredio == null){
		tiposPredio = new Vector();
	} 
	tipoPredioHelper.setOrdenar(false);
	tipoPredioHelper.setTipos(tiposPredio);
	tipoPredioHelper.setCssClase("camposformtext");
	
	ListaElementoHelper ejesHelper = new ListaElementoHelper();
	List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
	if(ejes == null){
		ejes = new Vector();
	}
	ejesHelper.setCssClase("camposformtext");
	ejesHelper.setOrdenar(false);
	ejesHelper.setTipos(ejes);
	
	ListaElementoHelper ejes2Helper = new ListaElementoHelper();
	ejes2Helper.setCssClase("camposformtext");
	ejes2Helper.setOrdenar(false);
	ejes2Helper.setTipos(ejes);
	CiudadanosAnotacionHelper ciudadanosHelper = new CiudadanosAnotacionHelper();
    TextHelper textHelper = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
 	//hiddenHelper.setTipo("text");
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    AnotacionesFolioHelper anotacionesHelper = new AnotacionesFolioHelper();
    Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
    session.removeAttribute(WebKeys.FOLIO);
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud solicitud = turno.getSolicitud();
    Documento documento = null;
	if(solicitud instanceof SolicitudRegistro){
		SolicitudRegistro registro = (SolicitudRegistro) solicitud;
		documento = registro.getDocumento();
	}
	
	String ultimavista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute("ULITIMA_VISTA_TEMPORAL", ultimavista);
	
	NotasInformativasHelper helper = new NotasInformativasHelper();
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}
function quitar(pos,accion) {
	document.CREAR.POSICION.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	var idDepto = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CREAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function locacion(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Creaci&oacute;n 
                Folios </td>
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
    <form action="calificacion.do" name="CREAR" id="CREAR" method="post">
    <input type="hidden" name="ACCION" id="ACCION">

    <input name="Depto" type="hidden" id="id_depto" value="">
    <input name="Depto" type="hidden" id="nom_Depto" value="">
    <input name="Mpio" type="hidden" id="id_munic" value="">
    <input name="Mpio" type="hidden" id="nom_munic" value="">    
    <input name="Ver" type="hidden" id="id_vereda" value="">
    <input name="Ver" type="hidden" id="nom_vereda" value="">
        
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">FOLIOS</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
    <td class="bgnsub">Datos B&aacute;sicos </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>
              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Ubicaci&oacute;n</td>
                  </tr>
                  <tr> 
                      <td>&nbsp;</td>
                      <td><table width="100%" class="camposform">
                          <tr>
                              <td>Circulo</td>
                              <td class="campositem"><%=circulo.getNombre()%></td>
                          </tr>
                      </table>
                      <table width="100%" class="camposform">
                      <tr>
                      <td>Departamento</td>
                      <td>
                      <table class="camposform">
                          <tr>
                          <td>ID:
							<%
						try {
							textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_DEPTO);
							textHelper.setCssClase("camposformtext");
							textHelper.setSize("3");
							textHelper.setId(CFolio.FOLIO_LOCACION_ID_DEPTO);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						try {
							textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_DEPTO);
							textHelper.setCssClase("camposformtext");
							textHelper.setSize("");
							textHelper.setId(CFolio.FOLIO_LOCACION_NOM_DEPTO);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
												 	
												 	
						 %>
                          
                          <td width="16"><a href="javascript:locacion('seleccionar.locacion.do?<%=AWLocacion.LOCACIONES_CIRCULO%>=<%=AWLocacion.LOCACIONES_CIRCULO%>','FOLIO_LOCACION','width=790,height=320,menubar=no');"><img src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif" alt="Permite seleccionar departamento, municipio, vereda" width="16" height="21" border="0"></a></tr>
                      </table></td>
                  </tr>
                  </table>
                  <table width="100%" class="camposform">
                      <tr>
                      <td>Municipio</td>
                      <td>ID: 
                        	<%
						try {
							textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_MUNIC);
							textHelper.setCssClase("camposformtext");
							textHelper.setSize("3");
							textHelper.setId(CFolio.FOLIO_LOCACION_ID_MUNIC);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						try {
							textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_MUNIC);
							textHelper.setCssClase("camposformtext");
							textHelper.setSize("");
							textHelper.setId(CFolio.FOLIO_LOCACION_NOM_MUNIC);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%></td>
                      <td>Vereda</td>
                      <td>ID:
                        	<%
							try {
								textHelper.setNombre(CFolio.FOLIO_LOCACION_ID_VEREDA);
								textHelper.setSize("3");
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CFolio.FOLIO_LOCACION_ID_VEREDA);
								textHelper.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							try {
								textHelper.setNombre(CFolio.FOLIO_LOCACION_NOM_VEREDA);
								textHelper.setCssClase("camposformtext");
								textHelper.setSize("");
								textHelper.setId(CFolio.FOLIO_LOCACION_NOM_VEREDA);
								textHelper.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							
							%>
                      </td>
                      </tr>
                  </table></td>
                  </tr>
              </table>
              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Apertura </td>
                  </tr>
                  <tr>
                      <td>&nbsp;</td>
                      <td><table width="100%" class="camposform">
                          <tr>
                              <td>Fecha</td>
                              <td class="campositem">
                		<%try {
							fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							fechaHelper.setDate(new Date());
							fechaHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}%>

                              </td>
                          </tr>
                      </table></td>
                  </tr>
                  <tr>
                      <td>&nbsp;</td>
                      <td><table width="100%" class="camposform">
                          <tr>
                              <td>Documento</td>
                              <%if(documento==null){%>
                              <td class="campositem">No hay documento asociado</td>
                              <%} else {%>
                              <td class="campositem"><%=documento.getTipoDocumento().getNombre()%> <%= documento.getNumero()%></td>
                              <%}%>
                          </tr>
                      </table></td>
                  </tr>
              </table>
            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>B&aacute;sicos</td>
                </tr>
                <tr>
                <td>&nbsp;</td>
                <td>
                <table width="100%" class="camposform">
                    <tr>
                    <td>C&oacute;digo Catastral</td>
                    <td>
                          <% try {
 		                       textHelper.setNombre(CFolio.FOLIO_COD_CATASTRAL);
                  			   textHelper.setCssClase("camposformtext");
                  			   textHelper.setId(CFolio.FOLIO_COD_CATASTRAL);
							   textHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
						 %>
					</td>	
                    <td>Complementaci&oacute;n</td>
                    <td>  
                           <% try {
   		                       textAreaHelper.setNombre(CFolio.FOLIO_COMPLEMENTACION);
 		                       textAreaHelper.setCols("50");
 		                       textAreaHelper.setRows("5");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CFolio.FOLIO_COMPLEMENTACION);
							   textAreaHelper.render(request,out);
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
                    <td>Lindero</td>
                    <td>
                   <% try {
 		                       textAreaHelper.setNombre(CFolio.FOLIO_LINDERO);
 		                       textAreaHelper.setCols("50");
 		                       textAreaHelper.setRows("5");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CFolio.FOLIO_LINDERO);
							   textAreaHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
					%>
                    </td>
                    </tr>
                    <tr>
                    <td>Tipo de Predio </td>
                    <td>
					<% try {
							tipoPredioHelper.setNombre(CFolio.FOLIO_TIPO_PREDIO);
							tipoPredioHelper.setId(CFolio.FOLIO_TIPO_PREDIO);
							tipoPredioHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td>
                    </tr>
                </table></td>
                </tr>
            </table>
              <hr class="linehorizontal">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Direcciones</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                  </tr>
                  <tr>
                  <td>&nbsp;</td>
                  <td>
					<% try {
							ejesHelper.setId(CFolio.FOLIO_EJE1);
							ejesHelper.setNombre(CFolio.FOLIO_EJE1);
							ejesHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>				  
                  </td>
                  <td>
				
				  <% try {
 		                       textHelper.setNombre(CFolio.FOLIO_VALOR1);
                  			   textHelper.setCssClase("camposformtext");
                  			   textHelper.setId(CFolio.FOLIO_VALOR1);
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
                  <td>
					<% try {
							ejes2Helper.setId(CFolio.FOLIO_EJE2);
							ejes2Helper.setNombre(CFolio.FOLIO_EJE2);
							ejes2Helper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>	
                  </td>
                  <td>
				  <% try {
 		                       textHelper.setNombre(CFolio.FOLIO_VALOR2);
                  			   textHelper.setCssClase("camposformtext");
                  			   textHelper.setId(CFolio.FOLIO_VALOR2);
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
                  <td>Especificacion</td>
                  <td>
                  
                   <% try {
 		                       textAreaHelper.setNombre(CFolio.FOLIO_COMPLEMENTO_DIR);
 		                       textAreaHelper.setCols("50");
 		                       textAreaHelper.setRows("5");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CFolio.FOLIO_COMPLEMENTO_DIR);
							   textAreaHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
					%>
                  
                  </td>
                  </tr>
              </table>
          <hr class="linehorizontal"></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
		<tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
	</table>
      
                   <% try {
							   anotacionesHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}
					%>
      
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
		</tr>
		<tr>
		<td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES DEL FOLIO</td>
		<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
		<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
		<td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
		</tr>
		</table>
		</td>
		<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
		</tr>
		</table>
		</td>
		<td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
		</tr>
		<tr>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		<td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		<td class="bgnsub">Opciones para el Folio</td>
		<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
		<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
		<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		</tr>
		</table>
		
		<table width="100%" class="camposform">
		<tr>
		<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
		<td width="140"><a href="javascript:cambiarAccion('CREAR_FOLIO_ENGLOBE')"><img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" width="139" height="21" border="0"></a></td>
		</form>
		<td><a href="calificacion.inscripcion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a></td>
		</tr>
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
   
   
                <%
		try{
		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CREAR");		
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
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