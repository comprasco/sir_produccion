<%@page import ="gov.sir.core.web.helpers.registro.*,org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado"%>
<%@page import="org.auriga.smart.SMARTKeys"%>

<%
String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);

String vistaAnterior1 = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
vistaAnterior1 = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL);
%>

<%
	ListaElementoHelper tipoRespuesta= new ListaElementoHelper();
	boolean cerrar=false;
	
	//obtener y guardar los parametros
	String numMatricula= request.getParameter("MATRICULA");
	String numCert= request.getParameter(AWMesa.NUM_CERT);
	String numMat= request.getParameter(AWMesa.NUM_MAT);
	
	/*String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_LIQUIDACION);
	session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);	*/
	
	//lista de matricula
	List listaMat= new Vector();
	
	//obtener listas asociadas
	List listaMatAsociadas= (List) request.getSession().getAttribute(AWMesa.LIST_SOL_FOLIO_ASOCIADAS);
	if(listaMatAsociadas==null){
		listaMatAsociadas=new Vector();
	}
	Iterator it= listaMatAsociadas.iterator();
	for(;it.hasNext();){
		SolicitudFolio sol= (SolicitudFolio) it.next();
		listaMat.add(new ElementoLista(sol.getIdMatricula(),sol.getIdMatricula()));
	}
	
	//saber si tiene q cerrarse
	Boolean oCarga= (Boolean)session.getAttribute("CERRAR_VENTANA");
	if(oCarga!=null){
		cerrar= oCarga.booleanValue();
	}
	
	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	if(exception!=null){
		 cerrar=true;
		 request.getSession().setAttribute("CERRAR_VENTANA", new Boolean(true));
	}
	session.removeAttribute(WebKeys.HAY_EXCEPCION);

	if(listaMat == null || listaMat.isEmpty()){

		Hashtable turnosRegistro = (Hashtable) session.getAttribute(WebKeys.LISTA_TURNOS_RELACION);
		Enumeration enumn = null;
		if(turnosRegistro!=null){
			enumn = turnosRegistro.keys();
		}

		SolicitudFolio solFolio = null;
		while(enumn!=null && enumn.hasMoreElements()){
			String key = (String)enumn.nextElement();
			List turnosCertificados = (List)turnosRegistro.get(key);

			Iterator itCert = turnosCertificados.iterator();
			while(itCert.hasNext()){
				SolicitudCertificado solicitudCertificado = (SolicitudCertificado)itCert.next();
				if(solicitudCertificado.getSolicitudFolios().size()>0){
					solFolio = (SolicitudFolio)solicitudCertificado.getSolicitudFolios().get(0);
					listaMat.add(new ElementoLista(solFolio.getIdMatricula(),solFolio.getIdMatricula()));
				}
			}
		}
		
	}
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(accion, numcert, nummat) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.<%= AWMesa.NUM_CERT %>.value= numcert;
       document.BUSCAR.<%= AWMesa.NUM_MAT %>.value= nummat;
       document.BUSCAR.submit();
}

function cerrarVentana(){

	<%
	if(vistaAnterior!=null){
	%>
	window.opener.location.href = '<%=vistaAnterior%>';
	<%
	}else{
	%>
	window.opener.location.reload();
	<%
	}
	%>

	window.close();
}

</script>
<form action="mesa.do" method="POST" name="BUSCAR" id="BUSCAR">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">
<input  type="hidden" name="<%= AWMesa.NUM_MAT %>" id="<%= AWMesa.NUM_MAT %>" value="">
<input  type="hidden" name="<%= AWMesa.NUM_CERT %>" id="<%= AWMesa.NUM_CERT %>" value="">
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cambiar matricula</td>
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">MATRICULAS</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
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
    <table width="100%" class="camposform">
        <tr>
	        <td>Numero matricula </td>
	        <td class="campositem"><%= numMatricula%></td>
	        <td width="48%">&nbsp;</td>
        </tr>
        <tr>
	        <td>Nuevo numero matricula </td>
	        <td colspan="2">
	       <%try {
                  tipoRespuesta.setCssClase("camposformtext");
                  tipoRespuesta.setId(AWMesa.NUEVO_NUM_MAT);
                  tipoRespuesta.setTipos(listaMat);                  			
				  tipoRespuesta.setShowInstruccion(false);
                  tipoRespuesta.setNombre(AWMesa.NUEVO_NUM_MAT);
                  tipoRespuesta.render(request,out);
			}	catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
						
			%>
	        </td>
        </tr>
    </table>
    </td>
    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <td width="150"><a href="javascript:cambiarAccion('<%= AWMesa.CAMBIAR_MATRICULA%>','<%= numCert%>', '<%= numMat%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0"></a></td>
            <td width="150"><a href="javascript:cerrarVentana()"><img src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="150" height="21" border="0"></a></td>
        	<td width="55%">&nbsp;</td>
        </tr>
    </table></td>
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
</form>
<% 	if(cerrar){ %>
<script> cerrarVentana(); </script>
<%		session.setAttribute("CARGAR_MESA_CONTROL", new Boolean(true));
	}
	%>