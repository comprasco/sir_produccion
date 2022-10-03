<%@page import="gov.sir.core.web.WebKeys"%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));	

%>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

		<form action="notas.informativas.view" method="post" name="NOTAS" target="_self" id="NOTAS">
		<%
		session.setAttribute(gov.sir.core.web.acciones.comun.AWNotas.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));
		session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CTipoNota.DESCRIPCION);
		session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CNota.DESCRIPCION);
		session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CTipoNota.ID_TIPO_NOTA);
		session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CTipoNota.VISIBILIDAD_TIPO_NOTA);
		%>
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr> 
		<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
		<td class="bgnsub">Notas Informativas</td>
		<td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
		<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
		</tr>
		</table>
		<br>
		<table width="100%" class="camposform">
		<tr> 
		<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
		<td><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_notas_informativas.gif" onClick="openWindow();" width="180" height="21" border="0"></td>
		</tr>
		</table>
		</form>