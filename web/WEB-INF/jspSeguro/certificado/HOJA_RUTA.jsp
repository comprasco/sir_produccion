<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCorreccion" %>
<%@page import="java.util.*"%>
<%@page
	import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>

<%MostrarAntiguoSistemaHelper MASHelper = new MostrarAntiguoSistemaHelper();
            MatriculaHelper matHelper = new MatriculaHelper();

            MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
            String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);
            if (ocultarTurno == null) {
                ocultarTurno = (String) session
                        .getAttribute(WebKeys.OCULTAR_TURNO);
                if (ocultarTurno == null) {
                    ocultarTurno = "FALSE";
                }
            } else {
                session.setAttribute(WebKeys.OCULTAR_TURNO, ocultarTurno);
            }

            String habilitarBusqueda = "FALSE";

            NotasInformativasHelper helper = new NotasInformativasHelper();

            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Solicitud solicitud = turno.getSolicitud();

            Turno turnoAnterior = solicitud.getTurnoAnterior();
            Ciudadano ciudadano = solicitud.getCiudadano();

            Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
%>

<script>
function cambiarAccion(text) {
	   if(text=='<%=gov.sir.core.web.acciones.registro.AWFolio.MOSTRAR_FOLIO%>'){
	      document.formAvance.action = 'radicacion.do';
	   }
	   else{
	      document.formAvance.action = 'certificado.do';
	   }
	   document.formAvance.ACCION.value = text
       document.formAvance.submit();   
}
function quitar(text) {
       document.formAvance.action = 'certificado.do';
       document.formAvance.ITEM.value = text;
	   document.formAvance.ACCION.value = '<%=gov.sir.core.web.acciones.certificado.AWCertificado.HOJA_RUTA_ELIMINAR_MATRICULA%>'
       document.formAvance.submit();	
}
</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user">
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
		<td width="12"><img name="tabla_gral_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif"
			width="12" height="23" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif"
					class="titulotbgral">Digitar hoja de Ruta</td>
				<td width="28"><img name="tabla_gral_r1_c3"
					src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif"
					width="28" height="23" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td width="12"><img name="tabla_gral_r1_c5"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif"
			width="12" height="23" border="0" alt=""></td>
		<td width="12">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td width="7"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7"
					height="10"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
				<td width="11"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">Turno</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table border="0" align="right" cellpadding="0" cellspacing="2">
					<tr>
						<%if (ocultarTurno.equals("FALSE")) {%>
						<form
							action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>"
							method="post" type="submit"><input type="hidden"
							name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
						<td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR"
							src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
							width="16" height="16" border="0"></td></form>

						<%} else {%>
						<form
							action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>"
							method="post" type="submit"><input type="hidden"
							name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
						<td width="170" class="contenido">Haga click para maximizar</td>
						<td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR"
							src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
							width="16" height="16" border="0"></td></form>
						<%}%>
					</tr>
				</table>
				</td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>

				<td valign="top" bgcolor="#79849B" class="tdtablacentral">


				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif"
							width="20" height="15"></td>
						<td width="20" class="campositem">N&ordm;</td>
						<td class="campositem"><%=turno.getIdWorkflow()%></td>
					</tr>
				</table>

				<%if (ocultarTurno.equals("FALSE")) {%> <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Folios asociados al turno</td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">

					<%List solFolios = solicitud.getSolicitudFolios();
                Iterator itSolFolios = solFolios.iterator();
                if (!solFolios.isEmpty()) {
                    while (itSolFolios.hasNext()) {
                        SolicitudFolio solFolio = (SolicitudFolio) itSolFolios
                                .next();

                        %>
					<tr>
						<td width="20"><img
							src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula</td>
						<td class="campositem"><%=solFolio.getFolio().getIdMatricula()%>&nbsp;</td>
					</tr>

					<%}%>
					<%} else {%>
					<tr>
						<td width="20"><img
							src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>No hay elementos que mostrar</td>
						<td class="campositem"></td>
					</tr>
					<%}

                %>
				</table>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Datos Adicionales</td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br> <%try {
                    MASHelper.setMostrarDocumento(true);
                    MASHelper.render(request, out);
                } catch (HelperException re) {
                    re.printStackTrace();
                    out.println("ERROR " + re.getMessage());
                }

                %> <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Datos Solicitante</td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>

				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="179">Tipo de Identificaci&oacute;n</td>
						<td width="211" class="campositem"><%=ciudadano.getTipoDoc() != null ? ciudadano
                        .getTipoDoc() : ""%>&nbsp;</td>
						<td width="146">N&uacute;mero</td>
						<td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano
                        .getDocumento() : ""%>&nbsp;</td>
					</tr>
					<tr>
						<td>Primer Apellido</td>
						<td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano
                        .getApellido1() : ""%>&nbsp;</td>
						<td>Segundo Apellido</td>
						<td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano
                        .getApellido2() : ""%>&nbsp;</td>
					</tr>
					<tr>
						<td>Nombre</td>
						<td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano
                        .getNombre() : ""%>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>

				<br>
				<hr class="linehorizontal"><%}%>
				</td>

				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>

			<tr>
				<td><img name="tabla_central_r3_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
					width="7" height="6" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="15" height="6"></td>
				<td><img name="tabla_central_pint_r3_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
					width="11" height="6" border="0" alt=""></td>
			</tr>

		</table>




		<form name="formAvance" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">


			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">Folio</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td width="7"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">

					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Agregar Matr&iacute;cula Inmobiliaria de la
						Propiedad</td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif"
							width="16" height="21"></td>
						<td width="16" class="bgnsub"><img
							src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>

				<table width="100%" class="camposform">
					<tr>
						<td><%try {
                matHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%></td>

						<%if (habilitarBusqueda.equals("TRUE")) {%>
						<td width="40"><input name="imageField" type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_short_buscar.gif"
							width="35" height="25" border="0"></td>
						<%}%>
					</tr>
				</table>

				</td>
				<td width="11"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%" class="camposform">
					<tr>
						<!--form name="formAvance" method="post"-->
						<input type="hidden" name="<%=WebKeys.ACCION %>"
							id="<%=WebKeys.ACCION %>" value="">
						<td width="150"><select name="AVANCE" id="AVANCE"
							class="camposformtext">
							<option value="SIN_SELECCIONAR" selected>-Seleccione una opcion-</option>
							<option
								value="<%=gov.sir.core.web.acciones.certificado.AWCertificado.ANT_SISTEMA_CREACION_FOLIO_RECHAZADO%>">Sin
							Propietario</option>
							<option
								value="<%=gov.sir.core.web.acciones.certificado.AWCertificado.ANT_SISTEMA_CREACION_FOLIO_MAS_DOCS%>">Mas
							Documentos</option>
							<option
								value="<%=gov.sir.core.web.acciones.certificado.AWCertificado.ANT_SISTEMA_CREACION_FOLIO_REANALIZAR%>">Reanalizar</option>
						</select></td>
						<td width="140" align="left"><a
							href="javascript:cambiarAccion(document.getElementById('AVANCE').value)"><img
							name="imageField"
							src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif"
							width="139" height="21" border="0"></a></td>
						<td width="80">&nbsp;</td>
						
						<% 


						boolean showButtonGotoFolio = false;

						showButtonGotoFolio = 	( folio == null ) 
												|| ( !folio.isDefinitivo() )
												|| ( solicitud instanceof SolicitudCorreccion ) ;
						%>
						
						<%if ( showButtonGotoFolio ) {%>
						<td width="175"><a
							href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWFolio.MOSTRAR_FOLIO%>')"><img
							name="imageField" type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_digitar_hoja_ruta.gif"
							width="175" height="21" border="0"></a></td>
						<%}%>

						<td width="140"><a
							href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.certificado.AWCertificado.ANT_SISTEMA_CREACION_FOLIO_EXISTE%>')"><img
							name="imageField" type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_folio_existente.gif"
							width="139" height="21" border="0"></a></td>
						<td width="140"><a href="turnos.view"><img name="imageField"
							type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"
							width="139" height="21" border="0"></a></td>
						<td align="center">&nbsp;</td>
						<!--form-->
					</tr>
				</table>
				</td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td><img name="tabla_central_r3_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
					width="7" height="6" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="15" height="6"></td>
				<td><img name="tabla_central_pint_r3_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
					width="11" height="6" border="0" alt=""></td>
			</tr>

		</table>
		</form>





		<%try {
                //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
                //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
                //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
                helper.setNombreFormulario("formAvance");
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
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