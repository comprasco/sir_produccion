<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="java.util.*"%>
<%@page
	import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%MostrarAntiguoSistemaHelper MASHelper = new MostrarAntiguoSistemaHelper();
            java.text.NumberFormat formato = java.text.NumberFormat
                    .getInstance();
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

            String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);
            if (ocultarFolio == null) {
                ocultarFolio = (String) session
                        .getAttribute(WebKeys.OCULTAR_FOLIO);
                if (ocultarFolio == null) {
                    ocultarFolio = "FALSE";
                }
            } else {
                session.setAttribute(WebKeys.OCULTAR_FOLIO, ocultarFolio);
            }

            String ocultarAnotaciones = request
                    .getParameter(WebKeys.OCULTAR_ANOTACIONES);
            if (ocultarAnotaciones == null) {
                ocultarAnotaciones = (String) session
                        .getAttribute(WebKeys.OCULTAR_ANOTACIONES);
                if (ocultarAnotaciones == null) {
                    ocultarAnotaciones = "TRUE";
                }
            } else {
                session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,
                        ocultarAnotaciones);
            }

            NotasInformativasHelper helper = new NotasInformativasHelper();

            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Solicitud solicitud = turno.getSolicitud();
            Liquidacion liquidacion = null;
            List liquidaciones = solicitud.getLiquidaciones();
            for (int i = 0; i < liquidaciones.size(); i++) {
                double id = new Double(((Liquidacion) liquidaciones.get(i))
                        .getIdLiquidacion()).doubleValue();
                if (id == solicitud.getLastIdLiquidacion()) {
                    liquidacion = (Liquidacion) liquidaciones.get(i);
                }
            }

            Turno turnoAnterior = solicitud.getTurnoAnterior();
            Ciudadano ciudadano = solicitud.getCiudadano();
            List solicitudFolios = solicitud.getSolicitudFolios();
            Iterator itSolicitudFolios = solicitudFolios.iterator();

            Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

            if (folio == null) {
                ocultarFolio = "TRUE";
            }

            %>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user" />
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
					class="titulotbgral">Revisi&oacute;n</td>
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
						<td class="campositem"><%=turno.getIdWorkflow()%>&nbsp;</td>
					</tr>
				</table>

				<%if (ocultarTurno.equals("FALSE")) {%> <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td class="bgnsub">Datos Turno</td>
						<td width="16" class="bgnsub"><img
							src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif"
							width="16" height="21"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>

				<br> <%if (solicitud instanceof SolicitudCertificado) {%>
				<table width="100%" border="0" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td>Tipo de Certificado</td>
						<td>Cantidad</td>
					</tr>
					<tr>
						<td class="campositem"><%=((TipoCertificado) ((LiquidacionTurnoCertificado) liquidacion)
                                            .getTipoCertificado()).getNombre()%>&nbsp;</td>
						<td class="campositem"><%=((SolicitudCertificado) solicitud)
                                    .getNumeroCertificados()%>&nbsp;</td>
					</tr>
				</table>
				<%}%> <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Turno Anterior</td>
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
					<tr>
						<td width="20"><img
							src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>N&uacute;mero del Turno Anterior</td>
						<td class="campositem"><%if (turnoAnterior != null) {

                    %><%=turnoAnterior.getIdWorkflow()%> <%} else {
                    out.write("No hay turnos anteriores");
                }%>&nbsp;</td>
					</tr>
				</table>

				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12"><img name="sub_r1_c1"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
							width="12" height="22" border="0" alt=""></td>
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Matr&iacute;cula Inmobiliaria de la Propiedad</td>
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
                                .next();%>
					<tr>
						<td width="20"><img
							src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula</td>
						<td class="campositem"><%=((Folio) solFolio.getFolio())
                                        .getIdMatricula()%>&nbsp;</td>
					</tr>

					<%}%>
					<%} else {%>
					<tr>
						<td width="20"><img
							src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula</td>
						<td class="campositem">No hay elementos que mostrar</td>
					</tr>
					<%}%>
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
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td width="7">&nbsp;</td>
				<td>
				<%MostrarFolioHelper mFolio = new MostrarFolioHelper();
            String vistaActual = "revision.view";
            try {
				List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
				List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);	
            
                mFolio.setFolio(folio);
                mFolio.setEditable(false);
                /*setters de estilo*/
                mFolio.setECampos("camposform");
                mFolio.setECampoTexto("campositem");
                mFolio.setETituloFolio("titulotbcentral");
                mFolio.setETitulos("titresaltados");
                mFolio.setETitulosSecciones("bgnsub");

                /*setters de imagenes */
                mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
                mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
                mFolio
                        .setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
                mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");

                mFolio.soloConsulta();
                mFolio.setTitulo("Ver folio consulta");
                //mFolio.setMayorExtension(esFolioMayorExtension);
                mFolio.setNombreAccionFolio("calificacion.do");
                mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
                mFolio.setNombreAncla("anclaConsulta");
                mFolio.setNombreForma("PAGINADOR_ADENTRO_CONSULTA");
                mFolio.setNombreFormaFolio("FORMA_FOLIO_CONSULTA");
                mFolio
                        .setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO_CONSULTA");
                mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CONSULTA");
                mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES_CONSULTA");
                mFolio.setNombreOcultarFolio("O_FOLIO_CONSULTA");
                mFolio.setnombreNumPaginaActual("NUM_PAGINA_ACTUAL_CONSULTA");
                mFolio.setNombrePaginador("NOMBRE_PAGINADOR_CONSULTA");
                mFolio.setNombreResultado("NOMBRE_RESULTADO_CONSULTA");
                mFolio.setPaginaInicial(0);
                mFolio.setVistaActual(vistaActual);
                //mFolio.setTotalAnotaciones(totalAnotaciones);
                //mFolio.setGravamenes(gravamenes);
                //mFolio.setMedCautelares(medCautelares);
                //mFolio.setFalsaTradicion(falsaTradicion);
   			    //mFolio.setAnotacionesInvalidas(anotacionesInvalidas);
                //datos a mostrar encabezado
                mFolio.MostrarAperturaFolio();
                mFolio.MostrarCabidaLinderos();
                mFolio.MostrarMayorExtension();

                //mFolio.setUsuarioBloqueo(usuarioBloqueo);
                //mFolio.setTurnoDeuda(turnoDeuda);
                //mFolio.setTurnoTramite(turnoTramite);
                mFolio.MostrarDatosRelevantes();

                mFolio.MostrarGravamenes();
                mFolio.MostrarDireccionInmueble();
                mFolio.MostrarComplementacion();
                mFolio.MostrarDatosDocumentos();
                mFolio.MostrarRelaciones();
                mFolio.render(request, out);

            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
				<td width="11">&nbsp;</td>
			</tr>
			<tr>
				<td width="7">&nbsp;</td>
				<td>
				<%try {
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
				<td width="11">&nbsp;</td>
			</tr>
		</table>

		</td>

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
