<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudAsociada" %>
<%
	// Obtengo el turno suministrado por la acción de negocio
	Turno tTurno = (Turno)session.getAttribute(WebKeys.TURNO);
	Solicitud sSolicitud = tTurno.getSolicitud();

	// Variables de utilidad
	List liTurnosPadres = new ArrayList();
	String sMensajeTurno = "";
	String sMensajeCertificado = "";

	if(sSolicitud != null) {
		// Obtengo las solicitudes padres
		List liPadres = sSolicitud.getSolicitudesPadres();

		if(liPadres != null) {
			// Asigno las solicitudes padres a una lista
			Iterator iPadres = liPadres.iterator();

			SolicitudAsociada saAsociada = null;
			Solicitud sSolicitudPadre = null;
			Turno tTurnoPadre = null;

			if(iPadres != null) {
				while(iPadres.hasNext()) {
					saAsociada = (SolicitudAsociada)iPadres.next();
					sSolicitudPadre = saAsociada.getSolicitudPadre();
					tTurnoPadre = sSolicitudPadre.getTurno();
					liTurnosPadres.add(tTurnoPadre);
				}
			}
		}
	}

	// Ensamblo los mensajes a ser desplegados en la página
	sMensajeCertificado = tTurno.getIdWorkflow();
	
	if(liTurnosPadres.size() == 0) {
		sMensajeTurno = "ning&uacute;n turno";
	} else if(liTurnosPadres.size() == 1) {
		sMensajeTurno = "el turno ";
	} else {
		sMensajeTurno = "los turnos ";
	}
	
	Iterator iPadres = liTurnosPadres.iterator();
	Turno tPadre = null;
	
	while(iPadres.hasNext()) {
		tPadre = (Turno)iPadres.next();
		sMensajeTurno += tPadre.getIdWorkflow();
		if(iPadres.hasNext()) {
			sMensajeTurno += ", ";
		}
	}
	
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="12">&nbsp;</td>
		<td width="12"><img name="tabla_error_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif"
			width="12" height="30" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ico_iniciosesion.gif"
							width="16" height="21"></td>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif"
							width="16" height="21"></td>
					</tr>
				</table>
				</td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"
					class="titulotbcentral">Informaci&oacute;n</td>
				<td width="14"><img name="tabla_error_r1_c4"
					src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif"
					width="14" height="30" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td width="12"><img name="tabla_error_r1_c6"
			src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif"
			width="12" height="30" border="0" alt=""></td>
		<td width="12">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td class="tdtablaanexa02" align="center">
		<table width="90%" border="0" cellpadding="0" cellspacing="0" class="tablas">
			<tr>
				<td valign="middle" class="links">
					<br>
					<b>Certificado Asociado a un Registro de Documentos</b>
					<br>
					<br>
					El turno <%=sMensajeCertificado%> está directamente asociado con <%=sMensajeTurno%> del 
					proceso de registro de documentos. Para avanzar el turno en cuesti&oacute;n es necesario 
					completar la calificaci&oacute;n del folio en el proceso de registro, pasar la 
					revisi&oacute;n de la mesa de control, realizar la firma del registrador  e imprimirlos 
					desde la fase de certificados asociados.<br><br>
					Si un turno de certiifcados asociados figura listado en los turnos disponibles para esta 
					fase, quiere decir que este no ha completado el proceso descrito anteriormente.
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
			<tr>
				<td>
					<form name="form1" method="post" action="procesos.view	">
					<input name="Submit2" type="submit" class="botontextual" value="Regresar al Menu principal">
					</form>
				</td>
			</tr>
		</table>
		</td>
		<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><img name="tabla_gral_r3_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif"
			width="12" height="20" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
		<td><img name="tabla_gral_r3_c5"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif"
			width="12" height="20" border="0" alt=""></td>
		<td>&nbsp;</td>
	</tr>
</table>