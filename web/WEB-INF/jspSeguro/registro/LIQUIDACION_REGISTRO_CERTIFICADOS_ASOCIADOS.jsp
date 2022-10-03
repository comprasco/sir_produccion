<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado" %>

<%
    // DEFINICIÓN DE VARIABLES
    TextHelper textHelperCopias = new TextHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    ListaElementoHelper tarifasHelper = new ListaElementoHelper();
    TablaMatriculaHelper tablaHelperCertificados = new TablaMatriculaHelper();
    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
    //Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
    //String NUM_COPIAS_DEFAULT = "1";

    String idCirculo = "";
    if(circulo != null) {
        idCirculo = circulo.getIdCirculo();
        idCirculo = idCirculo + "-";
    }

    // SE CARGA LA LISTA DE MATRICULAS O CERTIFICADOS ASOCIADOS, POR DEFECTO SE COLOCAN LAS MATRÍCULAS ASOCIADAS A LA SOLICITUD DE REGISTRO.

    // MATRICULAS ASOCIADAS
    List matriculas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
    List matriculasAsociadas = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
    List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
    if(certificadosAsociados == null) {
        certificadosAsociados = new ArrayList();
    }

    // ESTE IF SIRVE PARA COLOCAR POR DEFECTO LA LISTA DE MATRICULAS QUE FUERON ASOCIADAS EN EL TURNO DE REGISTRO.
    /*
    if(matriculasAsociadas==null && (matriculas != null) && !matriculas.isEmpty()) {
        if(matriculasAsociadas==null) {
            matriculasAsociadas = new ArrayList();
        }

        Proceso proceso = new Proceso();
        proceso.setIdProceso(new java.math.BigDecimal(CProceso.PROCESO_CERTIFICADOS).longValue());

        Iterator iterator = matriculas.iterator();

        while(iterator.hasNext()) {
            String id_matricula = (String)iterator.next();
            matriculasAsociadas.add(id_matricula);

            SolicitudCertificado solCert = new SolicitudCertificado();
            SolicitudFolio solFolio = new SolicitudFolio();
            Folio folio = new Folio();
            folio.setIdMatricula(id_matricula);
            solFolio.setIdMatricula(id_matricula);
            solFolio.setFolio(folio);
            solCert.addSolicitudFolio(solFolio);

            // SE LE COLOCA LOS DEMAS ATRIBUTOS QUE SE REQUIEREN.
            solCert.setProceso(proceso);
            solCert.setCirculo(circulo);
            solCert.setUsuario(usuario);
            solCert.setNumeroCertificados(new java.math.BigDecimal(NUM_COPIAS_DEFAULT).intValue());
            LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
            liquidacion.setTipoTarifa(CTipoTarifa.NORMAL);
            solCert.addLiquidacion(liquidacion);
            certificadosAsociados.add(solCert);
        }
    }
    */

    // HAY QUE DETERMINAR EL NÚMERO DE REGISTROS QUE SON DE ANTIGUO SISTEMA Y DE SEGREGACIÓN.
    Iterator itCertificados = certificadosAsociados.iterator();
    int numDatosAntSist = 0;
    int numDatosSegregacion = 0;
    int numCopiasAntSist = 0;
    int numCopiasSegregacion = 0;

    while(itCertificados.hasNext()) {
        SolicitudCertificado solCert = (SolicitudCertificado)itCertificados.next();
        
        if(solCert.getComentario() != null) {
            numDatosSegregacion++;
            numCopiasSegregacion += solCert.getNumeroCertificados();
        }
        
        if(solCert.getDatosAntiguoSistema()!=null){
            numDatosAntSist++;
            numCopiasAntSist += solCert.getNumeroCertificados();
        }
    }

    if(numCopiasSegregacion!= 0 && numDatosSegregacion!=0) {
        numCopiasSegregacion = numCopiasSegregacion / numDatosSegregacion;
    }

    if(numCopiasAntSist!= 0 && numDatosAntSist!=0) {
        numCopiasAntSist = numCopiasAntSist / numDatosAntSist;
    }

    session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,certificadosAsociados);
    session.setAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO, matriculasAsociadas);
%>

<script>
    function cambiarAccion(text) {
        document.CERTIFICADO.ACCION.value = text;
        document.CERTIFICADO.submit();
    }

    function cambiarTab(text) {
        if(text=='1') {
            document.all.TAB1.style.display = 'block';
            document.all.TAB3.style.display = 'none';
            document.all.ESQUINA1ACTIVO.style.display = 'block';
            document.all.ESQUINA1INACTIVO.style.display = 'none';
            document.all.TAB1ACTIVO.style.display = 'block';
            document.all.TAB1INACTIVO.style.display = 'none';
            document.all.TAB3ACTIVO.style.display = 'none';
            document.all.TAB3INACTIVO.style.display = 'block';
        }

        if(text=='3') {
            document.all.TAB1.style.display = 'none';
            document.all.TAB3.style.display = 'block';
            document.all.ESQUINA1ACTIVO.style.display = 'none';
            document.all.ESQUINA1INACTIVO.style.display = 'block';
            document.all.TAB1ACTIVO.style.display = 'none';
            document.all.TAB1INACTIVO.style.display = 'block';
            document.all.TAB3ACTIVO.style.display = 'block';
            document.all.TAB3INACTIVO.style.display = 'none';
        }
    }

    function eliminarMatriculas() {
        cambiarAccion('<%= AWLiquidacionRegistro.ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO %>')
    }

    function cerrarVentana() {
        //window.opener.location.reload();
        window.opener.location.href = 'turno.registro.liquidacion.view';
        window.close();
    }

    function recargarPagina() {
        //location.reload();
    }
    
    function cambiaestadoFoliosRegistro(campo){
        var elements = document.getElementsByName("TITULO_CHECKBOX_ELIMINAR_MATSOL_REGISTRO");
        var i = 0;
        for(i=0; i<elements.length; i++) {
            var checkbox = elements[i];
            checkbox.checked = campo.checked;
        }
    }
</script>

<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/jsp/plantillas/common.js"></script>

<link href="<%= request.getContextPath() %>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="tdtablaanexa02">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                  <td><img src="<%=request.getContextPath() %>/jsp/images/spacer.gif" width="7" height="10"></td>
                                  <td background="<%=request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
                                  <td><img src="<%=request.getContextPath() %>/jsp/images/spacer.gif" width="10" height="10"></td>
                            </tr>
                            <tr>
                              <td valign="top" id="ESQUINA1ACTIVO" style="display:'block'"><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                              <td valign="top" id="ESQUINA1INACTIVO" style="display:'none'"><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_000.gif" width="7" height="29" border="0" alt=""></td>
                              <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn003.gif">
                                  <table border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td id="TAB1ACTIVO" style="display:block" valign="top">
                                            <table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('1')">
                                                <tr>
                                                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Asociar matrículas</td>
                                                    <td><img name="tabla_central_r1_c3" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                                    <td valign="top" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn002.gif">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td><img src="<%= request.getContextPath() %>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td id="TAB1INACTIVO" style="display:none" valign="top">
                                            <table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('1')">
                                                <tr>
                                                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Asociar matrículas</a><a href="#"></a></td>
                                                    <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                                                    <td valign="top" background="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_002.gif">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td><img src="<%= request.getContextPath() %>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td valign="top"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td id="TAB3ACTIVO" style="display:none" valign="top">
                                            <table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('3')">
                                                <tr>
                                                    <td width="7"><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                                                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Sin matrícula</td>
                                                    <td><img name="tabla_central_r1_c3" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                                    <td valign="top" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn002.gif">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td><img src="<%= request.getContextPath() %>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td>
                                                        <img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td id="TAB3INACTIVO" style="display:block" valign="top">
                                            <table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('3')">
                                                <tr>
                                                    <td valign="top"><img name="tabla_central_r1_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                                                    <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Sin matrícula</a><a href="#"></a></td>
                                                    <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                                                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif">
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td valign="top"><img name="tabla_central_r1_c5" src="<%= request.getContextPath() %>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                                                </tr>
                                            </table></td>
                                      </tr>
                                  </table></td>
                                  <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                            </tr>

        <form action="turnoLiquidacionRegistro.do" method="post" name="CERTIFICADO" id="CERTIFICADO"> 
        <input type="hidden" name="ACCION" id="ACCION" >

                            <tr id="TAB1" style="display:block">
                                <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                                <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <br/>
                                        <br/>
                                        <br/> 
                                        <tr> 
                                            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Asociar nuevo certificado</td>
                                            <td width="16" class="bgnsub"></td>
                                            <td width="16" class="bgnsub"></td>
                                            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                        </tr>
                                    </table>
                                    <%
                                        if (matriculasAsociadas!=null && !matriculasAsociadas.isEmpty()) {
                                    %>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                            <td>Folios de esta solicitud</td>
                                        </tr>
                                        <tr>
                                            <td width="20">&nbsp;</td>
                                            <td>
                                            <%
                                                try {
                                                    tablaHelper.setColCount(5);
                                                    tablaHelper.setListName(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
                                                    tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                                                    tablaHelper.render(request, out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                        </tr>
                                    </table>
                                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                        <tr>
                                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                                            <td>Eliminar Seleccionadas</td>
                                            <td><a href="javascript:eliminarMatriculas()"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a></td>
                                        </tr>
                                    </table>
                                    <p>&nbsp;</p>
                                    <%
                                        }
                                    %>
                                    <%
                                        List certificadosAsociadosRegistro=(List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                                        List matriculasAdd=(List)session.getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_REMOVE);
                                        List certificadosRegistrosNoAdd=new ArrayList();

                                        if(certificadosAsociadosRegistro!=null && certificadosAsociadosRegistro.size()>0) {
                                            if(matriculasAdd!=null && matriculasAdd.size()>0) {
                                                for(int i=0;i<matriculasAdd.size();i++) {
                                                    for(int j=0;j<certificadosAsociadosRegistro.size();j++) {
                                                        if(!matriculasAdd.get(i).equals(certificadosAsociadosRegistro.get(j))) {
                                                            if(!certificadosRegistrosNoAdd.contains(certificadosAsociadosRegistro.get(j))) {
                                                                if(!matriculasAdd.contains(certificadosAsociadosRegistro.get(j)))
                                                                    certificadosRegistrosNoAdd.add(certificadosAsociadosRegistro.get(j));
                                                            }
                                                        } else {
                                                            if(certificadosRegistrosNoAdd.contains(certificadosAsociadosRegistro.get(j))) {
                                                                certificadosRegistrosNoAdd.remove(certificadosAsociadosRegistro.get(j));
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                certificadosRegistrosNoAdd=certificadosAsociadosRegistro;
                                            }
                                        }
                                        session.setAttribute(WebKeys.LISTA_CERT_NO_MAT,certificadosRegistrosNoAdd);
                                    %>
                                    <%
                                        if(certificadosRegistrosNoAdd!=null && certificadosRegistrosNoAdd.size()>0) {
                                    %>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"/></td>
                                            <td>Folios de registro</td>
                                        </tr>
                                        <tr>
                                            <td width="40">&nbsp;</td>
                                            <td>
                                            <%
                                                try {
                                                    tablaHelperCertificados.setColCount(5);
                                                    tablaHelperCertificados.setListName(WebKeys.LISTA_CERT_NO_MAT);
                                                    tablaHelperCertificados.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                                                    tablaHelperCertificados.setInputName(WebKeys.TITULO_CHECKBOX_ELIMINAR_MATSOL_REGISTRO);
                                                    tablaHelperCertificados.render(request, out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                        </tr>
                                    </table>
                                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                        <tr>
                                            <td>
                                                <input type="checkbox" onclick="cambiaestadoFoliosRegistro(this);" width="20" height="15"/>
                                            </td>
                                            <td>Seleccionar todos</td>
                                        </tr>
                                    </table>
                                    <p>&nbsp;</p>
                                    <%
                                        }
                                    %>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="20%">Tipo de Tarifa</td>
                                            <td width="30%">
                                            <%
                                                List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
                                                List tipos = null;
                                                tipos =  new ArrayList();
                                                Iterator it = temp.iterator();

                                                while(it.hasNext()) {
                                                    ElementoLista el = (ElementoLista)it.next();
                                                    ElementoLista nuevo = new ElementoLista(el.getId(),el.getValor());
                                                    tipos.add(nuevo);
                                                }
                                                    
                                                /**
												 * @author: Cesar Ramírez
												 * @change: Caso 1158.111.INACTIVAR.TARIFA.ESPECIAL.CERTIFICADOS. Se inactiva la opción de tarifa especial.
												 **/
												//tipos.add(new ElementoLista(CTipoTarifa.TARIFA_ESPECIAL,CTipoTarifa.TARIFA_ESPECIAL));

                                                try {
                                                    /*
                                                    if(session.getAttribute(gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA) == null){
                                                        session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA,"NORMAL");
                                                    }
                                                    */
                                                    tarifasHelper.setOrdenar(false);
                                                    tarifasHelper.setTipos(tipos);
                                                    tarifasHelper.setNombre(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA);
                                                    tarifasHelper.setId(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA);
                                                    tarifasHelper.setCssClase("camposformtext");
                                                    tarifasHelper.render(request,out);
                                                } catch(HelperException re) {
                                                    re.printStackTrace();
                                                    out.println("ERROR " + re.getMessage());
                                                } catch(Exception e) {
                                                    e.printStackTrace();
                                                    out.println("ERROR " + e.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td width="20">&nbsp;</td>
                                            <td colspan='2' width="50%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Cantidad</td>
                                            <td width="25px">
                                            <%
                                                try {
                                                    if(session.getAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA  ) == null ) {
                                                        session.setAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA,"1");
                                                    }
                                                    textHelperCopias.setNombre(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
                                                    textHelperCopias.setId(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
                                                    textHelperCopias.setCssClase("camposformtext");
                                                    textHelperCopias.render(request,out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                        </tr>
                                        <tr></tr>
                                        <tr>
                                            <td>N&uacute;mero de Matr&iacute;cula</td>
                                            <td>
                                                <%=idCirculo%>
                                                <input name="<%=CFolio.ID_MATRICULA%>" id="<%=CFolio.ID_MATRICULA%>" type="text" value="" onFocus="campoactual('<%=CFolio.ID_MATRICULA%>');" class="camposformtext">
                                                <img id="<%=CFolio.ID_MATRICULA%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
                                            </td>
                                            <td width="20">&nbsp;</td>
                                            <td  colspan='2' width="50%">&nbsp;</td>
                                        </tr>
                                        <br><br>
                                        <tr>
                                            <td>&nbsp;
                                        </tr>
                                        <tr>
                                            <td width="20%">Agregar Matr&iacute;cula</td>
                                            <td width="30%">
                                                <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWLiquidacionRegistro.AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO%>')">
                                                    <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0">
                                                </a>
                                            </td>
                                            <td width="20">&nbsp;</td>
                                            <td colspan='2' width="50%">&nbsp;</td>
                                        </tr>
                                    </table>
                                    <br>
                                </td>
                                <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                            </tr>
                            <tr id="TAB3" style="display:none">
                                <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                                <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="20%" align='right'>N&uacute;mero de unidades</td>
                                            <td class='campositem' width="15%"><%=""+numDatosSegregacion%></td>
                                            <td width="15%" align='right'>Cantidad</td>
                                            <td class='campositem' width="15%"><%=""+numCopiasSegregacion%></td>
                                            <td width='20%' align='right'>Eliminar certificados asociados sin matrícula</td>
                                            <td width="15%" valign='middle' align='left'>
                                                <a href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION%>')">
                                                    <img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" alt="Eliminar certificados asociados de segregación" border="0" />
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
                                    <br>
                                    <hr class="linehorizontal">
                                    <br>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="20%">Tipo de Tarifa</td>
                                            <td width="30%">
                                            <%
                                                try {
                                                    /*
                                                    if(session.getAttribute(gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_SEGREGACION) == null) {
                                                        session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_SEGREGACION,"NORMAL");
                                                    }
                                                    */
                                                    tarifasHelper.setTipos(tipos);
                                                    tarifasHelper.setNombre(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_SEGREGACION);
                                                    tarifasHelper.setId(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_SEGREGACION);
                                                    tarifasHelper.setCssClase("camposformtext");
                                                    tarifasHelper.render(request,out);
                                                } catch(HelperException re) {
                                                    re.printStackTrace();
                                                    out.println("ERROR " + re.getMessage());
                                                } catch(Exception e) {
                                                    e.printStackTrace();
                                                    out.println("ERROR " + e.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td width="20">&nbsp;</td>
                                            <td colspan='2' width="50%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td width="20%">N&uacute;mero de matrículas</td>
                                            <td width="30%">
                                            <%
                                                try {
                                                    if(session.getAttribute(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION) == null) {
                                                        session.setAttribute(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION,"1");
                                                    }
                                                    textHelperCopias.setNombre(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
                                                    textHelperCopias.setId(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
                                                    textHelperCopias.setCssClase("camposformtext");
                                                    textHelperCopias.render(request,out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td width="20%">Número de copias</td>
                                            <td width="30%">
                                            <%
                                                try {
                                                    if(session.getAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION) == null ) {
                                                        session.setAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION,"1");
                                                    }
                                                    textHelperCopias.setNombre(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
                                                    textHelperCopias.setId(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
                                                    textHelperCopias.setCssClase("camposformtext");
                                                    textHelperCopias.render(request,out);
                                                } catch(HelperException re) {
                                                    out.println("ERROR " + re.getMessage());
                                                }
                                            %>
                                            </td>
                                            <td width="20">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWLiquidacionRegistro.AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION%>')">
                                                    <img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  width="139" height="21" border="0">
                                                </a>
                                            </td>
                                            <td width="20">&nbsp;</td>
                                            <td colspan='2' width="50%">&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                            </tr>
                            <tr>
                                <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                                <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                                    <br>
                                    <hr class="linehorizontal">
                                    <br>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" onClick="cerrarVentana();" width="150" height="21" border="0"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                            </tr>
        </form>        
                            <tr>
                                <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                                <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>
                    </td>
                </tr>
            </table>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td> 
        <td></td>
        <td>&nbsp;</td>
        <td></td>
        <td>&nbsp;</td>
    </tr>
</table>

<script>
<%
    Boolean popCargado = (Boolean)session.getAttribute(WebKeys.POP_CERTIFICADOS_ASOCIADOS);
    if(popCargado==null || !popCargado.booleanValue()) {
%>
    //alert('recargado')
    //recargarPagina();
<%  
        session.setAttribute(WebKeys.POP_CERTIFICADOS_ASOCIADOS, new Boolean(true));
    }
%>
</script>