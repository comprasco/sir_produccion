<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="gov.sir.core.web.acciones.devolucion.AWDevolucion"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="gov.sir.hermod.HermodProperties"%>

<%@page import="org.auriga.smart.eventos.EventoException"%>
<%@page import="org.auriga.smart.web.ProcesadorEventosNegocioProxy"%>
<%@page import="org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="gov.sir.core.eventos.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWTurno"%>


<%
            org.auriga.core.modelo.transferObjects.Usuario usuario =  null;
   	 	    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            //JALCAZAR agrega solicita  WebKeys.LISTAS_ENTREGA_MULTIPLE de la session
            List turnos = (List) session.getAttribute(WebKeys.LISTAS_ENTREGA_MULTIPLE);
			Turno turnoDerivado = (Turno) session.getAttribute(WebKeys.TURNO_HIJO);
   	 	    			org.auriga.core.modelo.transferObjects.Rol rol = (Rol)session.getAttribute(WebKeys.ROL);
			session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
            session.removeAttribute(WebKeys.LISTA_CHEQUES);
            session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
            session.removeAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
            session.removeAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
            session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
            session.removeAttribute("TURNO_ANTERIOR");
            session.removeAttribute(CTurno.TURNO_ANTERIOR_OBJETO);
            session.removeAttribute(CTurno.SOLICITUD_VINCULADA);
            session.removeAttribute(WebKeys.SUBTIPO_SOLICITUD);
            session.removeAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
            session.removeAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS);
            session.removeAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
            session.removeAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
            session.removeAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS);
            session.removeAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS);

            session.removeAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS);
            session.removeAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS);
            session.removeAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
            session.removeAttribute(CDatosAntiguoSistema.TOMO_ANO_AS);

            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

			session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN);
            session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN);
            session.removeAttribute("COMENTARIO");
            session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
            session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
            session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
            session.removeAttribute(CSolicitudRegistro.CALENDAR);
            session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            session.removeAttribute(CSolicitudRegistro.NUMERO_OFICINA);
            session.removeAttribute(WebKeys.ID_OFICINA);

            //JALCAZAR proceso realizado para solo un turno
            
   	 	    if(turno!=null){
   	 	    	List listaUsuarios = (List)session.getAttribute(AWTurno.LISTA_USUARIOS_TURNO);
	   	 	    String nombreFase = (String) session.getAttribute(AWTurno.FASE_TURNO);
	   	 	    ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
	   	 	    EvnTurno evento = new EvnTurno(turno.getIdWorkflow(), AWTurno.CONSULTAR_CONFIRMACION);
	   	 	    try{
	            EvnRespTurno respuesta = (EvnRespTurno)proxy.manejarEvento(evento);
	            turno = respuesta.getTurno();
	            
	            session.setAttribute(WebKeys.TURNO,turno);
	            }
	            catch(Throwable t){
	               return;
	            }
   	 	    }
            
   
            Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
			/**Cuando es un turno de certificados vuelve al formulario de solicitud de certificados*/
			String certificado = (String)session.getAttribute(WebKeys.CERTIFICADO_TIPO);
			session.removeAttribute(WebKeys.CERTIFICADO_TIPO);
                        
           
            String currentState = null;
            
            try{
                HermodService hs = HermodService.getInstance();
                currentState = hs.currentStateNotaNotificada(turno.getIdWorkflow()); 
            } catch(HermodException he){
                System.out.println("ERROR: NO FUE POSIBLE OBTENER EL ESTADO ACTUAL DE LA NOTA DEVOLUTIVA NOTIFICADA");
            }
            
            boolean start = false;
            boolean juzgado = false;
            boolean recursos = false;

            
            if(currentState == null){
                start = true;
            } else if(currentState.equals(CDevoluciones.START_NOTA_NOTIFICADA)){
                start = true;
            } else if(currentState.equals(CDevoluciones.JUZGADO_NOTA_NOTIFICADA)){
                juzgado = true;
            } else if(currentState.equals(CDevoluciones.RECURSOS_NOTA_NOTIFICADA)){
                recursos = true;
            }
    
            String fecha = "";
            String mensaje = "";
            String mensaje2 ="";
            String mensaje3 ="";
            String estacion ="";
            Calendar calendar = Calendar.getInstance();
            Liquidacion liq = (Liquidacion) session
                    .getAttribute(WebKeys.LIQUIDACION);

            fecha = DateFormatUtil.format(calendar.getTime());
            session.removeAttribute(WebKeys.LIQUIDACION);

            //Mostrar el mensaje cuando se genera un turno
            if (turno != null && turno.getIdWorkflow() != null) {
                calendar.setTime(turno.getFechaInicio());

                fecha = DateFormatUtil.format(calendar.getTime());

                /*
                 int mes = calendar.get(Calendar.MONTH) + 1;
                 int dia = calendar.get(Calendar.DAY_OF_MONTH);
                 if (dia < 10) {
                 fecha = "0" + dia;
                 } else {
                 fecha = String.valueOf(dia);
                 }

                 fecha += "/" + mes + "/" + calendar.get(Calendar.YEAR);
                 */
                 
                //Determinar estaciï¿½n asociada al turno
                for (int j=0; j< turno.getHistorials().size(); j++)
                {
                TurnoHistoria th = (TurnoHistoria) turno.getHistorials().get(j);
                estacion = th.getIdAdministradorSAS();
                }


                mensaje = "La operaci&oacute;n sobre el turno "
                        + turno.getIdWorkflow()
                        + " fue realizada satisfactoriamente";
               
				if(turnoDerivado != null && turnoDerivado.getIdWorkflow() != null) {
					mensaje += ". Se encuentra un turno derivado " + turnoDerivado.getIdWorkflow() +
							". El turno " + turno.getIdWorkflow() + 
							" no podr&aacute; ser avanzado a ciertas fases hasta tanto no culmine la ejecuci&oacute;n del turno " + 
							turnoDerivado.getIdWorkflow();
				}
                       
                if(juzgado){
                    mensaje2 = "El turno ha sido enviado a Juzgado - Nota Devolutiva Notificada";
                } else if(recursos) {
                    mensaje2 = "El turno ha sido enviado a Recursos - Nota Devolutiva Notificada";
                } else{
                   mensaje2 = "";
                }
                
                
                                
            } else if (liq != null) {
                mensaje = "La creaci&oacute;n de la liquidaci&oacute;n n&uacute;mero: "
                        + liq.getIdSolicitud()
                        + " fue realizada satisfactoriamente";
            } else {
                //Mensaje normal
                mensaje = "La operacion fue realizada satisfactoriamente";
            }

            //JALCAZAR Mensaje de proceso depende de la utilizacion de turnos simple o multiples
            if(turnos != null)
                {
                    mensaje = "La operaci&oacute;n sobre los turnos: ";
                      Iterator i = turnos.iterator();
                    while (i.hasNext())
                        {
                        mensaje = mensaje + (String) i.next() + ", ";
                        }
                    mensaje = mensaje + " fue realizada satisfactoriamente";

                }
            String toDo = "";
            if (fase != null) {
                toDo = "turno.do?" + WebKeys.ACCION + "=" + AWTurno.LISTAR
                        + "&" + AWTurno.ID_FASE + "=" + fase.getID();
                if((rol!=null && rol.getRolId().equals(CRoles.SIR_ROL_CAJERO_CONSULTAS)) &&
					turno != null && turno.getIdFase().equals(CFase.FINALIZADO)){
					SolicitudConsulta solCon = (SolicitudConsulta) turno.getSolicitud(); 
					if(solCon.getTipoConsulta()!=null && solCon.getTipoConsulta().getNombre().equals(TipoConsulta.CONSTANCIA)){
						session.removeAttribute(WebKeys.TURNO);
						session.removeAttribute(WebKeys.SOLICITUD);
						toDo = "consultas.view";
					}
				}
            } else if(certificado!=null){
				session.removeAttribute(WebKeys.TURNO);    
				toDo = "turno.certificado.solicitud.view";
			}else if((rol!=null && rol.getRolId().equals(CRoles.SIR_ROL_CAJERO_CONSULTAS)) &&
				turno != null && turno.getIdFase().equals(CFase.FINALIZADO)){
				session.removeAttribute(WebKeys.TURNO);
				session.removeAttribute(WebKeys.SOLICITUD);
				toDo = "consultas.view";
			}else if((rol!=null && rol.getRolId().equals(CRoles.SIR_ROL_CAJERO_CONSULTAS)) &&
				turno != null && turno.getIdFase().equals(CFase.FINALIZADO)){
				session.removeAttribute(WebKeys.TURNO);
				session.removeAttribute(WebKeys.SOLICITUD);
				toDo = "consultas.view";
			} else if(liq != null && liq.getSolicitud().getProceso().getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)){
            	session.removeAttribute(WebKeys.LIQUIDACION);
                session.removeAttribute(WebKeys.SOLICITUD);
                session.removeAttribute(WebKeys.LISTA_CERT_NO_MAT);
                session.removeAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
                session.removeAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
                toDo = "turno.registro.liquidador.view";
            }else if(rol!=null && rol.getRolId().equals(CRoles.LIQUIDADOR_REGISTRO)){
            	session.removeAttribute(WebKeys.LIQUIDACION);
            	session.removeAttribute(WebKeys.TURNO);
                session.removeAttribute(WebKeys.SOLICITUD);
                session.removeAttribute(WebKeys.LISTA_CERT_NO_MAT);
                session.removeAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
                session.removeAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
                toDo = "turno.registro.liquidador.view";
			}else{
                toDo = "procesos.view";
            }
    /**
     * @author: Cesar Ramírez
     * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF
     * Realiza el llamado a ZipServlet si el certificado es de tipo certificado masivo y tipo de tarifa exento.
     **/
    // TODO: Controlar cuando descargar zip con CertificadosMasivos y Exentos. Validar casos null
    boolean downloadZip = false; 
    String tipoTarifa = (String) session.getAttribute(CTipoTarifa.EXENTO);
    if(tipoTarifa != null){
        String tipoProceso = String.valueOf(turno.getSolicitud().getProceso().getIdProceso());

        String filePath = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
        String file = filePath + "/" + turno.getIdWorkflow() + ".zip";
        File f = new File(file);

        if(tipoProceso.equals(CProceso.PROCESO_CERTIFICADOS_MASIVOS) && tipoTarifa.equals(CTipoTarifa.EXENTO) && f.exists()) {
            downloadZip = true;
        }
        session.removeAttribute(CTipoTarifa.EXENTO);
    }
	String turnoId = "";
    if(turno != null){
        turnoId = turno.getIdWorkflow();
    }
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js"></script> -->
<script>
redirTime = "3000";
redirURL = "<%= toDo%>"
function downloadZip(){ window.location.href = "<%=request.getContextPath()%>/servlet/ZipServlet?TURNO=<%=turnoId%>"}
function redirTimer() { self.setTimeout("self.location.href = redirURL;",redirTime); }
</script>

<body onload="<% if(downloadZip){ out.println("downloadZip();"); } %> redirTimer();">
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
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ico_iniciosesion.gif"
							width="16" height="21"></td>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ico_satisfactorio.gif"
							width="16" height="21"></td>
					</tr>
				</table>
				</td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"
					class="titulotbcentral">Satisfactorio</td>
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
		<td class="tdtablaanexa02">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tablas" id="tablaTurno">
			<tr>
				<td align="center" valign="middle" class="titulotbcentral2"><%=mensaje%></td>
				<td>
				<image src="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif" />
				<!--
		  <script language="javascript" type="text/javascript">
		  var Imagen="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif"
		  var pelicula="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.swf"
		  var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
		  var ancho="70"
		  var alto="70"
		  //plugindetectado();
		</script>
		-->
		</td>
			
			
			</tr>
			
			<% if (estacion != null)
			{
			%>
			<tr>
			<td align="center" valign="middle" class="titulotbcentral2"><%=mensaje2%></td>
			</td>
			<tr>
			<% } %>
				<%if (fase != null) {%>
				<form name="form1" method="post" action="turno.do">
				<td><input name="Submit2" type="submit" class="botontextual"
					value="Seleccionar nuevo turno"> <input type="hidden"
					name="<%=WebKeys.ACCION%>" value="<%=AWTurno.LISTAR%>"> <input
					type="hidden" name="<%=AWTurno.ID_FASE%>"
					value="<%=fase.getID() %>"></td>
				</form>
				<%} else if(liq != null && liq.getSolicitud().getProceso().getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)){%>
				<form name="form1" method="post" action="turno.registro.liquidador.view">
				<td><input name="Submit2" type="submit" class="botontextual"
					value="Regresar a Liquidacion de Registro"></td>
				</form>
				<%} else {%>
				<form name="form1" method="post" action="procesos.view	">
				<td><input name="Submit2" type="submit" class="botontextual"
					value="Regresar al Menu principal"></td>
				</form>
				<%}%>
			</tr>
		</table>
		</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td align="right" class="tdtablaanexa02"><span class="contenido">Sera
		redireccionado en 3 segundos</span></td>
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
</body>