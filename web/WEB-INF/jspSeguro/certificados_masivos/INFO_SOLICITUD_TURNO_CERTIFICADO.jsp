<%--
* @author      :   Julio Alcázar Rivas
* @change      :   Nuevo Import Calendar
* Caso Mantis  :   000941
--%>
<%@page import="java.util.Calendar"%>
<%@page import="gov.sir.core.web.helpers.comun.DatosPagoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page import="org.auriga.core.web.*, org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>


<%//
            ServletContext context = session.getServletContext();
            String numliquidacion = (String) session
                    .getAttribute(WebKeys.VALOR_LIQUIDACION);
            List consignaciones = (List) session
                    .getAttribute(WebKeys.LISTA_CONSIGNACIONES);
            List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
            AplicacionPago appEfectivo = (AplicacionPago) session
                    .getAttribute(WebKeys.APLICACION_EFECTIVO);
            AplicacionPago appTimbre = (AplicacionPago) session
                    .getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
            double valorLiquidacion = 0;
            List marcasCheques = (List)session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
            List marcasConsignacion = (List)session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
            
            if (numliquidacion != null) {
                valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
            } else {
                valorLiquidacion = 0;
            }

            DatosPagoHelper datosPagoHelper = new DatosPagoHelper(request,
                    valorLiquidacion, consignaciones, cheques, appEfectivo,
                    appTimbre,marcasConsignacion,marcasCheques);
            TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
            String ocultar = request.getParameter(WebKeys.OCULTAR);
            if (ocultar == null) {
                ocultar = (String) session.getAttribute(WebKeys.OCULTAR);
                if (ocultar == null) {
                    ocultar = "TRUE";
                }
            } else {
                session.setAttribute(WebKeys.OCULTAR, ocultar);
            }

            LiquidacionTurnoCertificadoMasivo liquidacion = (LiquidacionTurnoCertificadoMasivo) session
                    .getAttribute(WebKeys.LIQUIDACION);
            SolicitudCertificadoMasivo solicitud = (SolicitudCertificadoMasivo) liquidacion
                    .getSolicitud();
            Ciudadano ciudadano = solicitud.getCiudadano();

            /*
             * @author      :   Julio Alcázar Rivas
             * @change      :   Se agrega el documento a la información de la solicitud
             * Caso Mantis  :   000941
             */

            String rol = "";
            if ( session.getAttribute(WebKeys.ROL) != null ) {
                Rol rolClass = (Rol) session.getAttribute(WebKeys.ROL);
                rol = rolClass.getRolId();

                if ( rol != null ) {
                  rol = rol.trim();
                }
              }
            Documento documento = new Documento();
            String fechaHoy = null;
            if (rol.equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
                documento = solicitud.getDocumento();
                
                if(documento != null){
                    Calendar hoy = Calendar.getInstance();
                    hoy.setTime(documento.getFecha());
                    fechaHoy=hoy.get(Calendar.DAY_OF_MONTH)+"/"+(hoy.get(Calendar.MONTH)+1)+"/"+hoy.get(Calendar.YEAR);
                }
            }
            %>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif"
			width="7" height="10"></td>
		<td
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
			src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10"
			height="10"></td>
		<td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif"
			width="10" height="10"></td>
	</tr>
	<tr>
		<td><img name="tabla_central_r1_c1"
			src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
			width="7" height="29" border="0" alt=""></td>
		<td
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
					class="titulotbcentral">Certificado</td>
				<td width="9"><img name="tabla_central_r1_c3"
					src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
					width="9" height="29" border="0" alt=""></td>
				<td width="20" align="center" valign="top"
					background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img
							src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif"
							width="16" height="21"></td>
					</tr>
				</table>
				</td>
				<td width="12"><img name="tabla_central_r1_c5"
					src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
					width="12" height="29" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td><img name="tabla_central_pint_r1_c7"
			src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
			width="11" height="29" border="0" alt=""></td>
	</tr>


	<tr>
		<td
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		<td align="right" class="tdtablacentral">
		<table border="0" cellpadding="0" cellspacing="2">
			<tr>
				<%if (ocultar.equals("FALSE")) {%>
				<form
					action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>"
					method="post" type="submit"><input type="hidden"
					name="<%=WebKeys.OCULTAR%>" value="TRUE">
				<td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR"
					src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
					width="16" height="16" border="0"></td></form>

				<%} else {%>
				<form
					action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>"
					method="post" type="submit"><input type="hidden"
					name="<%=WebKeys.OCULTAR%>" value="FALSE">
				<td width="170" class="contenido">Haga click para maximizar</td>
				<td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR"
					src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
					width="16" height="16" border="0"></td></form>
				<%}%>
			</tr>
		</table>
		</td>
		<td
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>


	<tr>
		<td width="7"
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		<td valign="top" bgcolor="#79849B" class="tdtablacentral"><!--aca--> <%if (ocultar.equals("FALSE")) {%>
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="12"><img name="sub_r1_c1"
					src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
					width="12" height="22" border="0" alt=""></td>
				<td class="bgnsub">Datos Solicitud Certificado Masivo</td>
				<td width="16" class="bgnsub"><img
					src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif"
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
				<td>Tipo de Certificado</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="campositem" width="25%"><%=(liquidacion.getTipoCertificado()).getNombre()%></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br />
		<table width="100%" class="camposform">
			<tr>
				<td><img
					src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
					width="20" height="15" /></td>
				<td>Matr&iacute;culas Inmobiliarias</td>
			</tr>
			<tr>
				<td width="20">&nbsp;</td>
				<td><%try {
                    tablaHelper.setColCount(5);
                    tablaHelper.setListName(WebKeys.LISTA_CERTIFICADOS_MASIVOS);
                    tablaHelper.render(request, out);
                } catch (Exception re) {
                    out.println("ERROR " + re.getMessage());
                }

                %></td>
			</tr>
		</table>
		<P>&nbsp;</P>


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
                        .getTipoDoc() : "&nbsp;"%></td>
				<td width="146">N&uacute;mero</td>
				<td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano
                        .getDocumento() : "&nbsp;"%></td>
			</tr>
			<tr>
				<td>Primer Apellido</td>
				<td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano
                        .getApellido1() : "&nbsp;"%></td>
				<td>Segundo Apellido</td>
				<td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano
                        .getApellido2() : "&nbsp;"%></td>
			</tr>
			<tr>
				<td>Nombre</td>
				<td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano
                        .getNombre() : "&nbsp;"%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br>
             <%--
              * @author      :   Julio Alcázar Rivas
              * @change      :   Se muestra el documento en la información de la solicitud
              * Caso Mantis  :   000941
            --%>
            <%
            if (rol.equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
            %>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="12"><img name="sub_r1_c1"
			src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
			width="12" height="22" border="0" alt="">
                    </td>
		    <td
			background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
			class="bgnsub">Encabezado del Documento
                    </td>
		    <td width="16" class="bgnsub"><img
			src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif"
			width="16" height="21">
                    </td>
		    <td width="16" class="bgnsub"><img
			src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
			width="16" height="21">
                    </td>
		    <td width="15"><img name="sub_r1_c4"
			src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
			width="15" height="22" border="0" alt="" >
                    </td>
                </tr>
	    </table>
            <br>
		<table width="100%" border="0" cellpadding="0" cellspacing="2"
			class="camposform">
			<tr>
				<td width="20"><img
                         src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
                         width="20" height="15"></td>
                       <td  align="left">Datos B&aacute;sicos</td>
                    </tr>

                    <tr>
                     <td width="10%" align="right">Tipo</td>
                     <td width="23%" class="campositem"><%= documento.getTipoDocumento().getNombre() != null ? documento.getTipoDocumento().getNombre() : "&nbsp;"%></td>
                     <td width="10%" align="right">Numero</td>
                     <td width="23%" class="campositem"><%= documento.getNumero() != null ? documento.getNumero() : "&nbsp;"%></td>
                     <td width="10%" align="right">Fecha</td>
                     <td width="23%" class="campositem"><%= fechaHoy != null ? fechaHoy : "&nbsp;"%></td>
                     
                    </tr>

		</table>
                     <table width="100%" border="0" cellpadding="0" cellspacing="2"
                            class="camposform">
                         <tr>
                             <td width="20"><img
                                     src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
                                     width="20" height="15"></td>
                             <td  align="left">Oficina de Procedencia</td>
                         </tr>
                     <%
                if (documento.getOficinaInternacional() == null){
                     %>
                         <tr>
                             <td width="10%" align="right">Departamento</td>
                             <td width="23%" class="campositem"><%= documento.getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre() != null ?
                                 documento.getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre() : "&nbsp;"%></td>
                             <td width="10%" align="right">Municipio</td>
                             <td width="23%" class="campositem"><%= documento.getOficinaOrigen().getVereda().getMunicipio().getNombre() != null ?
                                 documento.getOficinaOrigen().getVereda().getMunicipio().getNombre() : "&nbsp;"%></td>
                         </tr>
                         <tr>
                             <td width="10%" align="right">Tipo de Oficina Origen</td>
                             <td width="23%" class="campositem"><%= documento.getOficinaOrigen().getTipoOficina().getNombre() != null ?
                                 documento.getOficinaOrigen().getTipoOficina().getNombre() : "&nbsp;"%></td>
                             <td width="10%" align="right">Nombre</td>
                             <td width="23%" class="campositem"><%= documento.getOficinaOrigen().getNombre() != null ?
                                 documento.getOficinaOrigen().getNombre() : "&nbsp;"%></td>
                         </tr>
   
                    <%} else{%>
                         <tr>
                             <td width="10%" align="right">Oficina ubicaci&oacute;n Internacional</td>
                             <td width="23%" class="campositem"><%= documento.getOficinaInternacional() != null ?
                                 documento.getOficinaInternacional() : "&nbsp;"%></td>
                         </tr>
                    <%}%>
                    </table>

                    <%}%>
		</td>
                <%}%>
            	<td width="11"
			background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>
	<tr>
		<td><img name="tabla_central_r3_c1"
			src="<%= request.getContextPath() %>/jsp/images/tabla_central_r3_c1.gif"
			width="7" height="6" border="0" alt=""></td>
		<td
			background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn006.gif"><img
			src="<%= request.getContextPath() %>/jsp/images/spacer.gif"
			width="15" height="6"></td>
		<td><img name="tabla_central_pint_r3_c7"
			src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r3_c7.gif"
			width="11" height="6" border="0" alt=""></td>
	</tr>
</table>
