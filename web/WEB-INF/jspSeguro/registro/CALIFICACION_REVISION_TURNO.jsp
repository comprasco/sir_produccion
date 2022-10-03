<%@page import="gov.sir.core.negocio.modelo.constantes.CArchivosJustifica"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.negocio.modelo.TramiteSuspension"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.Vereda"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.helpers.registro.ActosHelper"%>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>

<%
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    Turno turnoHijo = (Turno) session.getAttribute(WebKeys.TURNO_HIJO);
    SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();

    //geremias
    System.out.println("SOLICITUD NUM: " + solicitud.getIdSolicitud());
    //geremias
    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

    //AREA PARA INGRESAR NOTA INFORMATIVA CUANDO SE DELEGA A CONFRONTACION
    TextAreaHelper delegarConfrontacion = new TextAreaHelper();

    //AREA PARA INGRESAR NOTA INFORMATIVA CUANDO SE DELEGA A CORRECCION DE ENCABEZADO
    TextAreaHelper delegarEncabezado = new TextAreaHelper();

    //SE LLENA LA LISTA DE ACTOS PARA MOSTRAR LAS LIQUIDACIONES
    List actos = new ArrayList();
    List liquidaciones = solicitud.getLiquidaciones();
    Iterator it = liquidaciones.iterator();
    while (it.hasNext()) {
        LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) it.next();
        actos.addAll(liq.getActos());
    }

    //SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
    List matriculas = solicitud.getSolicitudFolios();
    List idmatriculas = new ArrayList();

    Iterator is = matriculas.iterator();
    while (is.hasNext()) {
        SolicitudFolio sf = (SolicitudFolio) is.next();

        Folio folio = sf.getFolio();
        String labelMatricula = "";
        String idMatricula = "";

        if (folio.isDefinitivo()) {
            labelMatricula = folio.getIdMatricula();
            idMatricula = folio.getIdMatricula();
        } else {
            //labelMatricula= folio.getNombreLote();
            labelMatricula = folio.getIdMatricula();
            idMatricula = folio.getIdMatricula();
        }

        gov.sir.core.web.helpers.comun.ElementoLista elista = new ElementoLista();
        elista.setId(idMatricula);
        elista.setValor(labelMatricula != null ? labelMatricula : "");

        idmatriculas.add(elista);
    }

    session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);
    
    List respuestas_usuario = (List) session.getAttribute(AWCalificacion.LISTA_RESPUESTAS_USUARIO);
    if (respuestas_usuario == null) {
        respuestas_usuario = new ArrayList();
    }

    //HELPER PARA MOSTRAR
    MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
    TextHelper textHelper = new TextHelper();

    session.setAttribute(WebKeys.VISTA_ORIGINADORA, request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));

%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>

function cambiarAccion2(actionForm, text) {
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.action = actionForm;
   document.CALIFICACION.submit();
}

function cambiarAccion(text) {
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.submit();
}

function calificar(text) {
   document.CALIFICACION.ITEM.value = text;
   cambiarAccion('CALIFICAR_FOLIO');
}

function enviarConfrontacion(text){
	if(confirm('¿Esta seguro que desea enviar el turno a confrontación?')){
		cambiarAccion(text);
	}
}

function enviarCorreccionEncabezado(text){
	if(confirm('¿Esta seguro que desea enviar el turno a corrección de encabezado?')){
		cambiarAccion(text);
	}
}



</script>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">



    <form action="calificacion.do" method="post" name="CALIFICACION" id="CALIFICACION">
    <input type="hidden" name="ACCION" value="CALIFICAR">



    <!--INICIO REVISIÓN ENCABEZADO-->

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">


          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión encabezado</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
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
				<br>

			    <%
				//TIPO DOCUMENTO
				String tipoDocumento="&nbsp;";
				if(solicitud.getDocumento().getTipoDocumento().getNombre()!=null)
					tipoDocumento= solicitud.getDocumento().getTipoDocumento().getIdTipoDocumento() + " - " + solicitud.getDocumento().getTipoDocumento().getNombre();

				//NUM DOCUMENTO
				String numDocumento="&nbsp;";
				if(solicitud.getDocumento().getNumero()!=null)
					numDocumento=solicitud.getDocumento().getNumero();

				//FECHA DOCUMENTO
				String fechaDocumento="&nbsp;";
				if(solicitud.getDocumento().getFecha()!=null){
					fechaDocumento = DateFormatUtil.format(solicitud.getDocumento().getFecha());
				}

				//ver is la oficina es internacional o naciol
				String nomOficinaIntr="";
				String departamento="&nbsp;";
				String municipio="&nbsp;";
				String vereda="&nbsp;";
				String tipoOficina="&nbsp;";
				String numOficina="&nbsp;";
				boolean esInternacional=false;
				boolean esComentarioDocumento=false;				

				if(solicitud.getDocumento().getComentario()!=null){
					nomOficinaIntr=solicitud.getDocumento().getComentario();
					esComentarioDocumento=true;
				}else if(solicitud.getDocumento().getOficinaInternacional()!=null){
					nomOficinaIntr=solicitud.getDocumento().getOficinaInternacional();
					esInternacional=true;
				}
				else{

					//DEPARTAMENTO, MUNICIPIO, VEREDA
					if (solicitud.getDocumento().getOficinaOrigen()!=null){
						if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
							Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
							departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
							municipio=auxVereda.getMunicipio().getNombre();
							vereda=auxVereda.getNombre();
						}
	
						//TIPO OFICINA
	
						if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
							tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicitud.getDocumento().getOficinaOrigen().getNombre();

						//NUM OFICINA

						if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
							numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
					}
				}
			    %>

              <table width="100%" class="camposform">
                <tr>
                  <td>Datos B&aacute;sicos </td>
                </tr>
                <%if(turnoHijo!=null){
                   		String mensaje = "Este turno depende del turno de Correcciones No: " + turnoHijo.getIdWorkflow();
                   %>
                   <tr>
                  
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td class="titresaltados" colspan="2"> <img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21"><%=mensaje %></td>	
                      </tr>
                  </table></td>
                </tr>
                   <%} %>
                <tr>
                  
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td class="campositem">
                        <%=tipoDocumento%>


                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>N&uacute;mero</td>
                        <td class="campositem">

                        <%=numDocumento%>
                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>Fecha</td>
                        <td class="campositem">
                        <%=fechaDocumento%>

                        </td>
                      </tr>
                  </table></td>
                </tr>

                <tr>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  
                  <td>
                  <table width="100%" class="camposform">
					<%if(esComentarioDocumento){%>
					 <tr>
                        <td>Oficina Origen</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%>
                        </td>
                      </tr>
                     <%}else if(!esInternacional){%>
                      <tr>
	                  <td width="12%">
		                   <DIV align="right">Codigo</DIV>
		              </td>
		              <td class="campositem" width="32%">
						<%=numOficina%>
	                  </td>
	                  <td width="15%">
	                    <DIV align="right">Nombre</DIV>
	                  </td>
	                  <td class="campositem" width="36%">
					    <%=tipoOficina%>
	                  </td>
		            </tr>

	                 <tr>
	                    <td width="12%">
	                      <DIV align="right">Departamento</DIV>
	                    </td>
	                    <td class="campositem" width="32%">
						<%=departamento%>
	                    </td>
	                    <td width="15%">
	                      <DIV align="right">Municipio</DIV>
	                    </td>
	                    <td class="campositem" width="36%">
							<%=municipio%>
	                    </td>
	                 </tr>
					<%}else{%>
					 <tr>
                        <td>Oficina internacional</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%>
                        </td>
                      </tr>
                     <%}%>
                    </table></td>
                </tr>
              </table>


              <hr class="linehorizontal">

              <table width="100%" class="camposform">
                <tr>

                <td>
                  <a href="javascript:enviarCorreccionEncabezado('<%="ERROR_ENCABEZADO"%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_correccion_encabezado.gif" name="Folio" width="190" height="21" border="0" id="Folio"/>
                  </a>
                  <td>
                    Motivo Envío
                    <%
                       delegarConfrontacion.setNombre(AWCalificacion.NOTA_INFORMATIVA_ENCABEZADO);
		               delegarConfrontacion.setCols("70");
		 	           delegarConfrontacion.setRows("3");
		               delegarConfrontacion.setCssClase("camposformtext");
		               delegarConfrontacion.setId(AWCalificacion.NOTA_INFORMATIVA_ENCABEZADO);
		               delegarConfrontacion.render(request,out);
		               delegarConfrontacion.setReadOnly(false);
		             %>
                  </td>
                  </td>
                </tr>
              </table>



          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
    <!--FIN REVISIÓN ENCABEZADO-->

    <!--INICIO REVISIÓN CONFRONTACIÓN-->
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión confrontación</td>
                        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            </table></td>
                        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table>
            </td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">

                <table width="100%">
                    <tr>
                        <td>
                            <% try {
                                    //HELPER TABLA MATRICULAS
                                    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
                                    tablaHelper.setColCount(5);
                                    tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                                    //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                                    tablaHelper.render(request, out);
                                } catch (HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                            %>
                        </td>
                    </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                    <tr>
                        <td>
                            <a href="javascript:enviarConfrontacion('<%=AWCalificacion.CONFRONTACION%>')">
                                <img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_confrontacion.gif" name="Folio" width="190" height="21" border="0" id="Folio"/>
                            </a>
                        </td>
                        <td>
                            Motivo Envío
                            <%
                                delegarEncabezado.setNombre(AWCalificacion.NOTA_INFORMATIVA_CONFRONTACION);
                                delegarEncabezado.setCols("70");
                                delegarEncabezado.setRows("3");
                                delegarEncabezado.setCssClase("camposformtext");
                                delegarEncabezado.setId(AWCalificacion.NOTA_INFORMATIVA_CONFRONTACION);
                                delegarEncabezado.render(request, out);
                                delegarEncabezado.setReadOnly(false);
                            %>
                        </td>
                    </tr>
                </table>               
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
    </table>
      <!--FIN DE REVISIÓN DE CONFRONTACIÓN-->
      
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
              <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
              <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
              <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                  <table border="0" cellpadding="0" cellspacing="0">
                      <tr>
                          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Respuesta Agregada</td>
                          <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                          <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                                  </tr>
                              </table></td>
                          <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                      </tr>
                  </table>
              </td>
              <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
              <td valign="top" bgcolor="#79849B" class="tdtablacentral">                  
                  <br>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                          <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                          <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Respuesta Tramite Suspensión</td>
                          <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                          <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                          <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                      </tr>                        
                  </table>                                
                  <table width="100%" class="camposform" name='tablanotificaciones' id="table">
                      <tr>
                          <th> Nombre de la fase </th>
                          <th> Fecha </th>
                          <th> Usuario </th>
                          <th> Descripción </th>
                          <th></th>
                      </tr>
                      <%
                          if (respuestas_usuario != null) {
                              int i = 0;
                              for (Iterator iter = respuestas_usuario.iterator(); iter.hasNext();) {
                                  TramiteSuspension respUsuario = (TramiteSuspension) iter.next();
                                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                      %>
                      <tr>
                          <td class="camposformtext_noCase"id="tramsNombreFase<%=i%>"><%= respUsuario.getTramsNombreFase()%></td>
                          <td class="camposformtext_noCase"id="tramsFecha<%=i%>"><%= df.format(respUsuario.getTramsFecha())%></td>
                          <td class="camposformtext_noCase"id="tramsIdUsuario<%=i%>"><%= respUsuario.getTramsUsuario()%></td>
                          <td class="camposformtext_noCase"id="tramsDescripcion<%=i%>"><%= respUsuario.getTramsDescripcion()%></td>                                   
                          <%if (respUsuario.getTramsIdArchivoJustifica() == null) {%>
                          <td>-</td>                                    
                          <%} else {%>
                          <td>
                              <a target="_blank" href="<%=request.getContextPath()%>/jsp/descargar.jsp?nArchivo=<%=respUsuario.getTramsIdArchivoJustifica().getJusIdArchivo()%>.<%=respUsuario.getTramsIdArchivoJustifica().getJusTipoArchivo()%>&rArchivo=<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                  <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif"> 
                              </a>                                    
                          </td>
                          <% }%>                         
                      </tr>
                      <%
                                  i++;
                              }
                          }
                      %>
                  </table>
              </td>
              <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>                   
          </tr>
          <tr>
              <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
              <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
      </table>
      

<%if(turnoHijo==null){ %>
    <!--INICIO REVISIÓN LIQUIDACIÓN-->

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión liquidación</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    
	                  </table></td>
	                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	              </tr>
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

              <table width="100%">
                <tr>
                  <td>

		              <% try {
							    ActosHelper actoHelper = new ActosHelper();
							    actoHelper.setActos(actos);
							    actoHelper.setMostrarEliminar(false);
							    //settear si se muestra o no impuestos dependiendo del circulo
								actoHelper.setMostrarImpuesto(circulo.isCobroImpuesto());
		          			    actoHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>

                  </td>
                </tr>
              </table>



              <table width="100%" class="camposform">
                <tr>
                  <td>Otros impuestos</td>
                </tr>
                <tr>
                 
                  <td><table width="100%" class="camposform">

					  <%
					  int paso = 0;
					  it = liquidaciones.iterator();

					  double totalLiquidaciones = 0;
                                          double totalConservacionDoc = 0;
					  while(it.hasNext()){
					  	LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro)it.next();
					  	if(liq.getPago()!=null){
					  		totalLiquidaciones = totalLiquidaciones + liq.getValorDerechos();
                                                        totalConservacionDoc = totalConservacionDoc + liq.getValorConservacionDoc();
					  	}
					  	if(liq.getOtroImpuesto()!=null){
					  %>

                      <tr>
                        <td width="25%" align="right">Descripción</td>
                        <td class="campositem" width="25%" align="left">
                        <%=liq.getOtroImpuesto()%>
                        </td>
                        <td width="25%" align="right">Valor</td>
                        <td class="campositem" width="25%" align="left">
                        <%="" + java.text.NumberFormat.getInstance().format(liq.getValorOtroImp())%>
                        </td>
                      </tr>
                      <%
                       paso++;
                       }
                      }
                      %>
                      <%
                      if(paso==0){
                      %>
                      <tr>
                        <td>No hay otros impuestos asociados</td>
                      </tr>
                      <%
                      }
                      %>

                  </table></td>
                </tr>
               </table>



              <table width="100%" class="camposform">
                  <tr>
                  <td colspan='2' width="50%" >Conservación Documental :</td>
                  <td colspan='2' width="50%" class="campositem" align="left">
                      <%=java.text.NumberFormat.getInstance().format(totalConservacionDoc)%>
                  </td>
                </tr>
                <tr>
                  <td colspan='2' width="50%" >Total liquidaciones :</td>
                  <td colspan='2' width="50%" class="campositem" align="left">
                  <%=java.text.NumberFormat.getInstance().format(totalLiquidaciones)%>
                  </td>
                </tr>
               </table>


              <hr class="linehorizontal">

              <table width="100%" class="camposform">
                <tr>

               <td >
				  <a href="javascript:document.CALIFICACION.action='calificacion.mayor.valor.view'; document.CALIFICACION.submit();"><img alt="[mayor-valor]" src="<%=request.getContextPath()%>/jsp/images/btn_mayor_valor.gif" name="Folio" width="150" height="21" border="0" id="Folio" /></a>
				  </td>

				  <td >
				   <a href="javascript:cambiarAccion2('turnoLiquidacionRegistro.do','<%=gov.sir.core.web.acciones.registro.AWLiquidacionRegistro.COMENZAR_PRELIQUIDACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_preliquidar.gif" width="139" height="21" border="0"></a>
				  </td>


                </tr>
              </table>



          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>

        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>

      <!--FIN DE REVISIÓN DE LIQUIDACIÓN-->

<%} %>



    </form>




  </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>





 
</table>
