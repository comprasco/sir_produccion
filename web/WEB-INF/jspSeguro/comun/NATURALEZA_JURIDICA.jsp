<%@page import="java.text.SimpleDateFormat"%>
<%@page import ="gov.sir.core.web.helpers.comun.*,org.auriga.core.web.*" %>
<%@page import ="gov.sir.core.web.WebKeys"%>
<%@page import ="java.util.List"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AWModificarFolio" %>
<%@page import ="java.util.Date,java.util.Calendar"%>
<%
  SeleccionarNaturalezaJuridicaHelper helper = new SeleccionarNaturalezaJuridicaHelper();
  boolean cancelacion = true;
  boolean calificacion = true;
  boolean soloCancelacion = false;
  boolean CancelacionCalificacion = false;
  String strCancelacion = "true";
  String strCalificacion = "false";
  String strSoloCancelacion = "true";
  String strCancelacionCalificacion = "false";
  
  if ( request.getParameter("cancelacion") != null ) {
    strCancelacion = (String) request.getParameter("cancelacion");
    
    if ( strCancelacion.equals("false") ) {
      cancelacion = false;
    }
  }

   if ( request.getParameter("calificacion") != null ) {
    strCalificacion = (String) request.getParameter("calificacion");
    
    if ( strCalificacion.equals("true") ) {
      List l =  (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION);
      helper.setListGruposNaturalezasJuridicas(l);
    }
  }

	if ( request.getParameter("soloCancelacion") != null ) 
	{
    	strSoloCancelacion = (String) request.getParameter("soloCancelacion");
    
	    if ( strSoloCancelacion.equals("true") ) 
		{
    	  soloCancelacion = true;
	    }
		
		if ( strSoloCancelacion.equals("false") ) 
		{
    	  soloCancelacion = false;
	    }
	}

	if ( request.getParameter("cancelacioncalificacion") != null ) {
    	strCancelacionCalificacion = (String) request.getParameter("cancelacioncalificacion");
		if ( strCancelacionCalificacion.equals("true") ) {
			CancelacionCalificacion = true;
		}
  	}	

	if (CancelacionCalificacion) {
		 List l =  (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION);
      	 helper.setListGruposNaturalezasJuridicas(l);
	}
	
	if (request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION) != null)
	{
		Date fechaRad = null;
		String fechaRadStr = (String)request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
		
		if (fechaRadStr != null && !fechaRadStr.equals(""))
		{
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");                   
                    fechaRad = formatoDelTexto.parse(fechaRadStr);
		}
		
		session.setAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION, fechaRad);
	} 

	helper.setMostrarCancelacion(cancelacion);
	helper.setMostrarSoloCancelacion(soloCancelacion);
	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);  
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<title>Documento sin t&iacute;tulo</title>
<script language="JavaScript" type="text/JavaScript">
function submitform(){
	document.formulario.submit();
}
function visibilidad(nombre,ver){
	document.nombre.style.visibility=ver
}
function carga(){
	document.formulario.nat_juridica_id_00.value=opener.document.getElementById('natjuridica_id').value;
		document.formulario.nat_juridica_nom_00.value=opener.document.getElementById('natjuridica_nom').value;
                 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */
                document.formulario.nat_juridica_ver_00.value = opener.document.getElementById('natjuridica_ver').value;
}
function value_formulario(idnatjuridica,nomnatjuridica,version){
	opener.document.getElementById(document.formulario.nat_juridica_id_00.value).value=idnatjuridica;
	opener.document.getElementById(document.formulario.nat_juridica_nom_00.value).value=nomnatjuridica;
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : 
	 */
        opener.document.getElementById(document.formulario.nat_juridica_ver_00.value).value=version;
	window.close();
}
</script>
</head>
<body onLoad="carga();">
<form action="seleccionar.naturaleza.juridica.view?cancelacion=<%=strCancelacion%>&calificacion=<%=strCalificacion%>&cancelacioncalificacion=<%=strCancelacionCalificacion%>" method="post" name="formulario" id="formulario">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Naturaleza Jur&iacute;dica </td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    <td width="7">&nbsp;</td>
  </tr>
  <tr>
    <td width="7">&nbsp;</td>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
		<%
		try{
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
      </td>
    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    <td width="7">&nbsp;</td>
  </tr>
  <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="6"></td>
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="6"></td>
  </tr>
</table>
    </form></body>
</html>
