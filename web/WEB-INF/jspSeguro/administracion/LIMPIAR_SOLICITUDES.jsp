<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Date" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.SolicitudesPaginadasHelper" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro" %>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano" %>
<%@page import="gov.sir.core.negocio.modelo.Documento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CPaginacion" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%
	TextHelper textHelper = new TextHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	SolicitudesPaginadasHelper solicitudesHelper = new SolicitudesPaginadasHelper();
	
	if(session.getAttribute(CPaginacion.PAGINA) == null) {
	    session.setAttribute(CPaginacion.PAGINA, new Long(0));
	}

	if(session.getAttribute(CPaginacion.RESULTADOS_POR_PAGINA) == null) {
    	session.setAttribute(CPaginacion.RESULTADOS_POR_PAGINA, new Long(20));
	}
	
	boolean carga=false;
	List tipos = null;
	tipos = (List) request.getSession().getAttribute(AWAdministracionHermod.LISTA_SOLICITUDES_NO_PAGADAS);
	if(tipos == null) {
		carga = true;
	} else {
    	solicitudesHelper.setSolicitudes(tipos);
	}
	
	Date dtemp = new Date();
	Date DatefechaDesde = null;
	Date DatefechaHasta = null;
	String fechaDesde = (String) request.getSession().getAttribute("CALENDARDESDE");
	if(fechaDesde == null) {
		 DatefechaDesde = dtemp;
	}
	
	String fechaHasta = (String) request.getSession().getAttribute("CALENDARHASTA");
	if(fechaHasta == null) {
		DatefechaHasta = dtemp;
	}
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

	function cambiarPagina(numeroPagina) {
		document.ELIMINAR.<%= CPaginacion.PAGINA %>.value = numeroPagina;
		document.ELIMINAR.<%= WebKeys.ACCION %>.value = '<%= AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS %>';
		document.ELIMINAR.submit();
	}
	
	function cambiarAccionEliminar(text) {
		document.ELIMINAR.<%= WebKeys.ACCION %>.value = text;
		document.ELIMINAR.submit();
	}

	function cambiarAccion(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	}
	
	function cambiarAccionAndSend(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
		document.BUSCAR.submit();
	}
	
	function cambiarSeleccion() {
	    if(document.ELIMINAR.seleccionado.checked == true){
		    setAllCheckBoxes('ELIMINAR', 'ELIMINAR_SOL', true);
	    }
	    if(document.ELIMINAR.seleccionado.checked == false){
	    	setAllCheckBoxes('ELIMINAR', 'ELIMINAR_SOL', false);
	    }
	}
	
	function cargaInicial() {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%= AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS%>';
		document.BUSCAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n liquidaciones no pagadas</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consultar liquidaciones no pagadas </td>
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
                    <td class="bgnsub">Rangos de consulta </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION   %>" value="<%=  AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS %>">
                <table width="100%" class="camposform">

                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    </td>
                    <td width="220">Desde </td>
                    <td>
                    	<table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                              <% try {
                              	
 		                        textHelper.setNombre("CALENDARDESDE");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("CALENDARDESDE");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                              
                         		<td><a href="javascript:NewCal('calendardesde','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>          
                  			</tr>
                  		</table>
                  	</td>
                  </tr>
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    </td>
                    <td width="220">Hasta </td>
                    <td>
                    	<table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                              <% try {
                              	
 		                        textHelper.setNombre("CALENDARHASTA");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("CALENDARHASTA");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                              
                         		<td><a href="javascript:NewCal('calendarhasta','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>          
                  			</tr>
                  		</table>
                  	</td>
                  </tr>

                  
                  
  
                  
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS  %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado
                    liquidaciones
                    </td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <%-- <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Listado</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">C&oacute;digo Solicitud</td>
                  <td class="titulotbcentral">Fecha solicitud</td>
                  <td class="titulotbcentral">Documento registro</td>
                  <td class="titulotbcentral">Documento solicitante</td>
                  <td class="titulotbcentral">Eliminar</td>
                </tr>--%>
                 <form action="administracionHermod.do" method="POST" name="ELIMINAR" id="ELIMINAR">
        		 <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS %>">
        		 <%-- %><input type="hidden" name="<%= CPaginacion.PAGINA %>" id="<%= CPaginacion.PAGINA %>" value="<%= "" + numeroPagina %>">
                
                 <% 
                int contador=0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	SolicitudRegistro solicitud = (SolicitudRegistro)iter.next();
                	Ciudadano c=solicitud.getCiudadano();
                	Documento d=solicitud.getDocumento();
                	String codSolicitud="";
                	if(solicitud.getIdSolicitud()!=null){
                		codSolicitud=solicitud.getIdSolicitud();
                	}
                	String idDocumento="";
                	String tipoDocumento="";
                	if(d!=null){
	                	if(d.getTipoDocumento()!=null){
		                	if(d.getTipoDocumento().getNombre()!=null){
		                		idDocumento= d.getTipoDocumento().getNombre();
		                	}
		                }
		                if(d.getIdDocumento()!=null){
		                	tipoDocumento=d.getIdDocumento();
		                }
	                }
                	
                	String idSolicitante="";
                	String tipoSolicitante="";
                	if(c!=null){
                		if(c.getTipoDoc()!=null){
                			tipoSolicitante=c.getTipoDoc();
                		}
                		if(c.getDocumento()!=null){
                			idSolicitante=c.getDocumento();
                		}
                	}
                	
                	
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=   codSolicitud%></td>
                  <td class="campositem"><%  
                  	try {
						if (solicitud.getFecha()!=null){
							fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							fechaHelper.setDate(solicitud.getFecha());
							fechaHelper.render(request,out);
						}
						else{
							out.write("No disponible");
						}

					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
                  %></td>
                  <td class="campositem"><%= tipoDocumento+"-"+idDocumento%></td>
                  <td class="campositem"><%= tipoSolicitante+"-"+idSolicitante %></td>
                  
                  <td align="center">
        				<input name="ELIMINAR_SOL" type="checkbox" value="<%= Integer.toString(contador)%>">
                  </td>
                  
                </tr>
                <% contador++;
                 }
                 %>
               <%if(tipos!=null && tipos.size()!=0){
               %>
               <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="right">Seleccionar todas:</td>
                  <td align="center"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="center"><input name="imageField" type="image" onClick="cambiarAccionEliminar('<%=AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" width="139" height="21" border="0"></td>
                </tr>
                <%}%>--%>
                
				<% 
					try {
    					solicitudesHelper.render(request, out);
					} catch(HelperException re) {
                        out.println("ERROR " + re.getMessage());
					}
                %>
            </form>
             
            <%--</table>--%></td>
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
<%--
<%if(carga){%>
	<script>cargaInicial();</script>
<%}%>
--%>