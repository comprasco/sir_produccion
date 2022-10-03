<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema"%>
<%@page import="gov.sir.core.web.helpers.registro.MatriculaCalifHelper"%>
<%@page	import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%-- includes --%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="org.apache.commons.jxpath.JXPathContext"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%-- + + + +  --%>


<%
	MostrarAntiguoSistemaHelper MASHelper = new MostrarAntiguoSistemaHelper();
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

	TextAreaHelper nota=new TextAreaHelper();

	NotasInformativasHelper helper = new NotasInformativasHelper();

	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();

	Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

	String idCirculo = "";
	if (request.getSession().getAttribute(WebKeys.CIRCULO) != null) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
		idCirculo = idCirculo + "-";
	}
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);
%>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- mostrar si es correccion interna o externa  (bug:0003584) --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<%



  local_SearchImpl_jx: {

            // :: local variables ----------------------------------------------
            Turno local_Turno;
            JXPathContext local_TurnoSearchContext;
            // // List< TurnoHistoria >
            List local_TurnoHistoria;
            Iterator local_TurnoSearchContextIterator;

            Boolean foundedElelementsCount; // elementos encontrados
            boolean evaluateNextRule; // flag para evaluar las reglas
            String local_TipoCorreccion;

            final String TIPO_CORRECCION_INTERNA = "INTERNA";
            final String TIPO_CORRECCION_EXTERNA = "EXTERNA";


            // initialize context & variables
            local_Turno = turno;
            local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
            local_TurnoSearchContext = JXPathContext.newContext( local_TurnoHistoria );
            local_TurnoSearchContext.setLenient( true );

            evaluateNextRule = true;
            local_TipoCorreccion = null;

            // declare jxpath variables
            local_TurnoSearchContext.getVariables().declareVariable( "local_ProcesoId"         , new Long( 0L ) );
            local_TurnoSearchContext.getVariables().declareVariable( "local_FaseId"            , "" );
            local_TurnoSearchContext.getVariables().declareVariable( "local_RespuestaId"       , "" );

            // -----------------------------------------------------------------
            // simple queries
            // apply first



            if( evaluateNextRule  ) {

              // ruleA: search-rule:
              //  que se haya iniciado siendo proceso de correcciones
              //  y que haya pasado por solicitud correccion;
              //  en este caso es externa

              String searchRule = "count( .[ (@idProceso = $local_ProcesoId) and (@fase = $local_FaseId ) ] ) > 0";

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_CORRECCIONES ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "COS_SOLICITUD_CORRECCION"   );

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( searchRule );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_EXTERNA):(null);

            } // :if

            if( evaluateNextRule  ) {

              // ruleB: search-rule:
              // se escoge el ultimo elemento
              // del turno historia que sea la primera fase de antiguo sistema;
              // se observa el turno historia anterior
              // si la fase es de correcciones, sin importar el proceso, se coloca correccion interna.

              //String searchRule = "count( .[ (@fase = $local_FaseId ) ] ) > 0";
              // .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] ) [last()] devuelve el ultimo nodo con fase antiguo sistema
              // preceding-sibling::*[1] busca el nodo inmediatamente anterior del contexto
              // [starts-with(@fase,'CORR')] busca si el nombre de la fase de ese nodo es de correcciones
              // count cuenta el conjunto de nodos que cumplen con esa regla
              // jxpath:
              // count( ( .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] ) [last()] /preceding-sibling::*[1]/[ starts-with( @fase,'COR' ) ] ) > 0

              // jxpath no genera esta regla asi que se debe descomponer en 2:
              // 1. buscar la posicion del nodo de interes en el contexto
              // 2. escoger el anterior y hacer la consulta

              // forma 2: en el elemento se guarda la fase anterior:

              String searchRule = "count( ( .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] )[last()][starts-with( @faseAnterior, 'COR' )] ) > 0";

              Object foundedElement;
              foundedElement = local_TurnoSearchContext.getValue( searchRule );

              foundedElelementsCount = (Boolean)foundedElement;
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            } // :if



            // only informative display
            if( null != local_TipoCorreccion ) {

              request.setAttribute( "LOCAL_TIPO_CORRECCION", local_TipoCorreccion );

            } // if



  } // :searchImpl_jx


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">
function cambiarAccion(text) 
{
	if ( text == '<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_FOLIO_RECHAZADO%>' ||
	text == '<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_FOLIO_MAS_DOCS%>' )
	{
		if (<%=!turno.getSolicitud().getSolicitudFolios().isEmpty() %> )
		{
			var answer = confirm("Se desharán las hojas de ruta creadas; desea continuar?")
			
			if (answer)
			{
			   document.HOJA_RUTA.ACCION.value = text;
		       document.HOJA_RUTA.submit();
		    }
		}
		else
		{
			document.HOJA_RUTA.ACCION.value = text;
		    document.HOJA_RUTA.submit();		
		}
	} 
	else
	{
		document.HOJA_RUTA.ACCION.value = text;
		document.HOJA_RUTA.submit();
	}
}
function quitar(text) {
       document.HOJA_RUTA.ITEM.value = text;
	   document.HOJA_RUTA.ACCION.value = '<%=gov.sir.core.web.acciones.certificado.AWCertificado.HOJA_RUTA_ELIMINAR_MATRICULA%>'
       document.HOJA_RUTA.submit();
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
						
						<td width="20" class="campositem">N&ordm;</td>
						<td class="campositem"><%=turno.getIdWorkflow()%></td>
					</tr>
				</table>

				<%if (ocultarTurno.equals("FALSE")) {%> <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Folios asociados al turno</td>
						
						
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<form action="antiguosistema.do" method="post" name="MATRICULAS_HOJA_RUTA">
				<input type="hidden" name="ACCION" id="ACCION" value="">
				<%
				MatriculaCalifHelper matrHelper=new MatriculaCalifHelper();
				matrHelper.setModoVisualizacion(MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA);
				matrHelper.setFolios(turno.getSolicitud().getSolicitudFolios());
				matrHelper.setVistaActual("hoja.ruta.view");
				matrHelper.setForm("MATRICULAS_HOJA_RUTA");
				try{
					matrHelper.render(request,out);
				}catch(HelperException he){
				}
				 %>
				 </form>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Datos Adicionales</td>
						
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

                %>

				<br>
				<hr class="linehorizontal"><%}%>





    <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
    <%

	String tipoCorreccion="EXTERNA";
	if(turnoPadre != null){
		tipoCorreccion="INTERNA";
	}


    %>


    <%

    if( null != tipoCorreccion ) {

    %>

      <table width="100%" class="camposform">
        <tr>
          
            <td>Tipo de correcci&oacute;n </td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>La corrección es </td>

            <td class="campositem"><%=tipoCorreccion%>&nbsp;</td>

          </tr>
        </table>

    <hr class="linehorizontal" />


    <%
    } // :if
    %>

    <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>






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

		<form name="HOJA_RUTA" method="post" action="antiguosistema.do">
		<input type="hidden" name="<%=WebKeys.ACCION %>" id="<%=WebKeys.ACCION %>" value="">
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
							class="titulotbcentral">Opciones</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
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
				
				<%if (!(turno.getSolicitud() instanceof SolicitudCertificado) || !(turno.getSolicitud().getSolicitudFolios().size()>0)){
					%>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">

					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Agregar Matr&iacute;cula Inmobiliaria de la
						Propiedad</td>
						
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<table width="100%" class="camposform">
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td>
									<table width="100%" class="camposform">
										<tr></tr>
										<tr>
											<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
											<td>N&uacute;mero de Matr&iacute;cula</td>
											<td><input name="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>" id="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>" type="text" value="<%=idCirculo%>" class="camposformtext">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="right">
									<table border="0" cellpadding="0" cellspacing="2" class="camposform">
										<tr>
											<td>Agregar Matr&iacute;cula</td>
											<td><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.ASOCIAR_FOLIO %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
		            </td>
				</tr>
			</table>
					<%
				}
				%>
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
						
						<td width="140" align="left"><a
							href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_APROBAR_HOJA_RUTA%>')"><img
							name="imageField"
							src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif"
							width="139" height="21" border="0"></a>
						</td>
						<!--<td width="80">&nbsp;</td>-->
						<td width="175" align="left">
						<%
						if (turno.getSolicitud() instanceof SolicitudCertificado && turno.getSolicitud().getSolicitudFolios().size()>0){
							%>&nbsp;<%
						}else{%>
    						<a
							href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.DIGITAR_HOJA_RUTA%>')"><img
							name="imageField" type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_digitar_hoja_ruta.gif"
							width="175" height="21" border="0"></a>
					<%}%>
						</td>
						<td width="80">&nbsp;</td>
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
							class="titulotbcentral">Opciones Alternativas</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
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
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">&nbsp;</td>
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
					
						<td width="150">
							<select name="AVANCE" id="AVANCE"
								class="camposformtext">
								<option value="SIN_SELECCIONAR" selected>-Seleccione una opcion-</option>
								<option
									value="<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_FOLIO_RECHAZADO%>">Rechazar</option>
								<option
									value="<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_FOLIO_MAS_DOCS%>">M&aacute;s
								Documentos</option>
							</select>
						</td>

						<td width="140" align="left"><a
							href="javascript:cambiarAccion(document.getElementById('AVANCE').value)"><img
							name="imageField"
							src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif"
							width="139" height="21" border="0"></a>
						</td>
						<!--<td width="80">&nbsp;</td>-->
						<td width="180" align="left"><a
							href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema.AVANZAR_DEVOLVER_REVISION_INICIAL%>')"><img
							name="imageField" type="image"
							src="<%=request.getContextPath()%>/jsp/images/btn_revision_inicial.gif"
							width="180" height="21" border="0"></a>
						</td>

						<td align="center">&nbsp;</td>
						<!--form-->
					</tr>
					<!--<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td align="center">&nbsp;</td>
					</tr>-->

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
							
							<td>Nota Informativa</td>
						</tr>
						<tr>
							
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
										<%
											try{
    											nota.setReadOnly(false);
    											nota.setCssClase("camposformtext");
    											nota.setNombre(CDatosAntiguoSistema.RAZON_DEVOLUCION_HOJA_RUTA);
    											nota.setId(CDatosAntiguoSistema.RAZON_DEVOLUCION_HOJA_RUTA);
    											nota.setCols("80");
    											nota.setRows("10");
    											nota.render(request,out);
											}catch (HelperException he){
                                                out.println("ERROR " + he.getMessage());
											}
										 %>
										</td>
									</tr>
								</table>
							</td>
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
                helper.setNombreFormulario("HOJA_RUTA");
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
		<td>&nbsp;</td>
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
