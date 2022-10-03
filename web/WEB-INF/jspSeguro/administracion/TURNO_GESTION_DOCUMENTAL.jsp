<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWGestionDocumental" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CZonaRegistral" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="gov.sir.core.negocio.modelo.ZonaRegistral" %>
<%@page import="co.com.iridium.generalSIR.comun.modelo.TurnoSGD" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
if(session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_ELEMENTO)==null){
    List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
    List listaCirculos = new ArrayList();
    for (Iterator iter = circulos.iterator(); iter.hasNext();) {
        Circulo circulo = (Circulo) iter.next();
        listaCirculos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()+" - "+circulo.getIdCirculo()));
    }
    session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_ELEMENTO, listaCirculos);
}

List listaCirculos = (List) session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_ELEMENTO);
ListaElementoHelper circulosHelper = new ListaElementoHelper();
circulosHelper.setTipos(listaCirculos);

List tipos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
if(tipos == null){
    tipos = new ArrayList();
}
/* Lista de turnos SIN Encolar */
List turnosListaSinEncolar = (List)session.getAttribute(AWGestionDocumental.LISTA_TURNOS_GD_SL);
if(turnosListaSinEncolar == null) {
    turnosListaSinEncolar = new ArrayList();
}

/* Lista de turnos ENCOLADOS */
List turnosListaEncolados = (List)session.getAttribute(AWGestionDocumental.LISTA_TURNOS_GD_L);
if(turnosListaEncolados == null) {
    turnosListaEncolados = new ArrayList();
}

/* Update Exitoso Reencolar */
String turnosReencolado = (String)session.getAttribute(AWGestionDocumental.TURNO_REENCOLAR);

/* Numero de turnos seleccionados por Rango de Fechas */
List turnosPorFecha = (List)session.getAttribute(AWGestionDocumental.TURNOS_POR_FECHA);
String numeroTurnosSeleccionados = null;
String fechaInicioTurnosSeleccionados = null;
String fechaFinTurnosSeleccionados = null;
if(turnosPorFecha == null) {
    turnosPorFecha = new ArrayList();
}else{
    numeroTurnosSeleccionados = turnosPorFecha.get(2).toString();
    fechaInicioTurnosSeleccionados = turnosPorFecha.get(0).toString();
    fechaFinTurnosSeleccionados = turnosPorFecha.get(1).toString();
}

/* Verificar los turnos depurados */
String turnosDepurados = (String)session.getAttribute(AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA_OK);
if(turnosDepurados == null) {
    turnosDepurados = "0";
}

TextHelper textHelper = new TextHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
    function cambiarAccionTurnoSGD(text) {
        document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
    }

    function cambiarAccionTurnoPorFechaSGD(text) {
        document.turnosPorFecha.<%= WebKeys.ACCION %>.value = text;
    }

    function cambiarAccionDepurarTurnoPorFechaSGD(text) {
        document.depurarTurnosPorFecha.<%= WebKeys.ACCION %>.value = text;
    }

    function cambiarAccionSubmit(text) {
        document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
    }

    function reencolarItem(anio,circulo,proceso,turno){
        document.REENCOLAR.<%=CTurno.ANIO%>.value = document.getElementById(anio).value;
        document.REENCOLAR.<%=CCirculo.ID_CIRCULO%>.value = document.getElementById(circulo).value;
        document.REENCOLAR.<%=CProceso.PROCESO_ID%>.value = document.getElementById(proceso).value;
        document.REENCOLAR.<%=CTurno.ID_TURNO%>.value = document.getElementById(turno).value;
        document.REENCOLAR.submit();
    }
</script>


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
          <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
              <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
                      <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
                      <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Sistema Gesti&oacute;n Documental</td>
                      <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                  </tr>
              </table>
          </td>
          <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
          <td width="12">&nbsp;</td>
      </tr>
      <tr>
          <td>&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
          <td class="tdtablaanexa02">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                  <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                  </tr>
                  <tr>
                      <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                          <table border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consultar Turnos</td>
                                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                              <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                          </tr>
                                      </table>
                                  </td>
                                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                              </tr>
                          </table>
                      </td>
                      <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                  </tr>
                  <tr>
                      <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                      <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                          <form action="gestionDocumental.do" method="POST" name="BUSCAR" id="BUSCAR">
                              <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=AWGestionDocumental.SELECCIONA_CIRCULO%>" value="<%=AWGestionDocumental.SELECCIONA_CIRCULO%>">
                              <table width="100%" class="camposform">
                                  <tr>
                                      <td>&nbsp;</td>
                                      <td width="5%">C&iacute;rculo&nbsp;:</td>
                                      <td width="20%">
                                          <%try {
                                              circulosHelper.setNombre(CCirculo.ID_CIRCULO);
                                              circulosHelper.setCssClase("camposformtext");
                                              circulosHelper.setId(CCirculo.ID_CIRCULO);
                                              circulosHelper.setFuncion("onChange=\"cambiarAccionTurnoSGD('"+AWGestionDocumental.SELECCIONA_CIRCULO+"')\"");
                                              circulosHelper.render(request,out);
                                            } catch(HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                          %>
                                      </td>
                                      <td align="left">
                                          <input name="imageField" type="image" onClick="cambiarAccionSubmit('<%=AWGestionDocumental.TERMINA  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                                      </td>
                                  </tr>
                              </table>
                          </form>
                      </td>
                      <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                  </tr>
                  <!-- Fila de una TABLA para el 1er requerimiento caso mantis: 06760 Autor: Ellery Robles G. -->
                  <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                      <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                          <table width="100%" class="camposform">
                              <tr>
                                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                  <td><b>Listado de turnos generados en SIR, en los procesos definidos y que se encuentran en la cola de mensajeria sin que hayan sido leídos por el Sistema de Gestión Documental.</b></td>
                              </tr>
                              <tr>
                                  <td colspan="2">
                                      <div style="overflow:scroll;width:100%;height:150px;">
                                          <table width="100%">
                                              <tr>
                                                  <td class="titulotbcentral">&nbsp;No.</td>
                                                  <td class="titulotbcentral">&nbsp;ID TURNO</td>
                                                  <td class="titulotbcentral">&nbsp;ID PROCESO</td>
                                                  <td class="titulotbcentral">&nbsp;FECHA RADICACIÓN</td>
                                                  <td class="titulotbcentral">&nbsp;ENVIO</td>
                                                  <td width="100px" class="titulotbcentral">&nbsp;</td>
                                              </tr>
                                              <%
                                              Iterator iter = turnosListaSinEncolar.iterator();
                                              int i = 1;
                                              while (iter.hasNext()){
                                                  TurnoSGD turnoSinEncolar = (TurnoSGD)iter.next();%>
                                                  <tr>
                                                      <td class="campositem"><%=i%></td>
                                                      <td class="campositem"><%=turnoSinEncolar.getTurno()%></td>
                                                      <td class="campositem"><%=turnoSinEncolar.getProceso()%></td>
                                                      <td class="campositem"><%=turnoSinEncolar.getFechaRadicacion()%></td>
                                                      <td class="campositem"><%=turnoSinEncolar.getEnvio()%></td>
                                                      <td>&nbsp;</td>
                                                  </tr><%
                                                  i++;
                                              }%>
                                          </table>
                                      </div>
                                  </td>
                              </tr>
                          </table>
                      </td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                  </tr>
                  <!-- Fila de una TABLA para el 2do y 3er requerimiento caso mantis: 06760 Autor: Ellery Robles G. -->
                  <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                      <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                          <table width="100%" class="camposform">
                              <tr>
                                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" alt="icoLista" width="20" height="15"></td>
                                  <td><b>Listado turnos generados por SIR, en los procesos definidos y que fueron encolados y leídos por el Sistema de Gestión Documental.</b></td>
                              </tr>
                              <tr>
                                  <td colspan="2">
                                      <form action="gestionDocumental.do" method="post" name="REENCOLAR" id="REENCOLAR">
                                          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWGestionDocumental.TURNO_REENCOLAR%>" />
                                          <input type="hidden" name="<%=CTurno.ANIO%>" />
                                          <input type="hidden" name="<%=CCirculo.ID_CIRCULO%>" />
                                          <input type="hidden" name="<%=CProceso.PROCESO_ID%>" />
                                          <input type="hidden" name="<%=CTurno.ID_TURNO%>" />
                                      </form>
                                      <div style="overflow:scroll;width:100%;height:150px;">
                                          <table width="100%">
                                              <tr>
                                                  <td class="titulotbcentral">&nbsp;No.</td>
                                                  <td class="titulotbcentral">&nbsp;ID TURNO</td>
                                                  <td class="titulotbcentral">&nbsp;ID PROCESO</td>
                                                  <td class="titulotbcentral">&nbsp;FECHA RADICACIÓN</td>
                                                  <td class="titulotbcentral">&nbsp;ENVIO</td>
                                                  <td width="100px" class="titulotbcentral">&nbsp;REENCOLAR</td>
                                              </tr>
                                              <%
                                              iter = turnosListaEncolados.iterator();
                                              int j = 1;
                                              while (iter.hasNext()){
                                                  TurnoSGD turnoEncolado = (TurnoSGD)iter.next();%>
                                                  <tr>
                                                      <td class="campositem"><%=j%></td>
                                                      <td class="campositem"><%=turnoEncolado.getTurno()%></td>
                                                      <td class="campositem"><%=turnoEncolado.getProceso()%></td>
                                                      <td class="campositem"><%=turnoEncolado.getFechaRadicacion()%></td>
                                                      <td class="campositem"><%=turnoEncolado.getEnvio()%></td>
                                                      <td>
                                                          <input type="hidden" id="a<%=j%>" value="<%=turnoEncolado.getAnio()%>" />
                                                          <input type="hidden" id="c<%=j%>" value="<%=turnoEncolado.getCirculo()%>" />
                                                          <input type="hidden" id="p<%=j%>" value="<%=turnoEncolado.getProceso()%>" />
                                                          <input type="hidden" id="t<%=j%>" value="<%=turnoEncolado.getTurno()%>" />
                                                          <input name="imageField" type="image" onClick="reencolarItem('a<%=j%>','c<%=j%>','p<%=j%>','t<%=j%>');" src="<%= request.getContextPath()%>/jsp/images/btn_mini_activar.gif" width="35" height="13" border="0" />
                                                      </td>
                                                  </tr>
                                                  <%
                                                  j++;
                                              }%>
                                          </table>
                                      </div>
                                  </td>
                              </tr>
                          </table>
                      </td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                  </tr>
                  <!-- Fila de una TABLA para el 4to requerimiento caso mantis: 06760 Autor: Ellery Robles G. -->
                  <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                      <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                          <table width="100%" class="camposform">
                              <tr>
                                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                  <td><b>Selecci&oacute;n de turnos para la depuraci&oacute;n de la cola de mensajer&iacute;a del Sistema de Gesti&oacute;n Documental.</b></td>
                              </tr>
                              <tr>
                                  <td colspan="2">
                                      <form action="gestionDocumental.do" method="post" name="turnosPorFecha" id="turnosPorFecha">
                                          <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA%>">
                                          <table width="100%" class="camposform">
                                              <tr>
                                                  <td width="20px"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                                  <td align="left" width="150px">Fecha Inicio:</td>
                                                  <td width="40px">
                                                      <% try {
                                                          textHelper.setNombre(AWGestionDocumental.FECHA_INICIO);
                                                          textHelper.setCssClase("camposformtext");
                                                          textHelper.setId(AWGestionDocumental.FECHA_INICIO);
                                                          textHelper.render(request,out);
                                                         } catch(HelperException re) {
                                                             out.println("ERROR " + re.getMessage());
                                                         } %>
                                                  </td>
                                                  <td align="left">
                                                      <a href="javascript:NewCal('<%=AWGestionDocumental.FECHA_INICIO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                                  </td>
                                                  <td width="50px">&nbsp;</td>
                                                  <td width="20px"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" alt="icoTexto" width="20" height="15"></td>
                                                  <td align="left" width="150px">Fecha Fin:</td>
                                                  <td width="40px">
                                                      <% try {
                                                          textHelper.setNombre(AWGestionDocumental.FECHA_FIN);
                                                          textHelper.setCssClase("camposformtext");
                                                          textHelper.setId(AWGestionDocumental.FECHA_FIN);
                                                          textHelper.render(request,out);
                                                         } catch(HelperException re) {
                                                             out.println("ERROR " + re.getMessage());
                                                         } %>
                                                  </td>
                                                  <td align="left">
                                                      <a href="javascript:NewCal('<%=AWGestionDocumental.FECHA_FIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                                  </td>
                                                  <td width="50px">&nbsp;</td>
                                                  <td width="20px"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" alt="icoVinculo" width="20" height="15"></td>
                                                  <td width="155px">
                                                      <input name="imageField" type="image" onClick="cambiarAccionTurnoPorFechaSGD('<%=AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA%>');" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                                                  </td>
                                                  <td width="25%">&nbsp;</td>
                                              </tr>
                                          </table>
                                      </form>
                                  </td>
                              </tr>
                              <%if(turnosPorFecha.size() != 0){%>
                                  <tr>
                                      <td colspan="2">
                                          <table width="100%" class="campostip04">
                                              <tr>
                                                  <td align="center">Se han seleccionado <b><%=numeroTurnosSeleccionados%> Turno(s)</b> a partir de: <b><%=fechaInicioTurnosSeleccionados%></b> hasta el <b><%=fechaFinTurnosSeleccionados%></b> para su depuraci&oacute;n</td>
                                              </tr>
                                          </table>
                                          <%if(!turnosPorFecha.get(2).equals("0")){%>
                                              <form action="gestionDocumental.do" method="post" name="depurarTurnosPorFecha" id="depurarTurnosPorFecha">
                                                  <table width="100%" class="camposform">
                                                      <tr>
                                                          <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA%>">
                                                          <input  type="hidden" name="<%=AWGestionDocumental.FECHA_INICIO%>" id="<%=AWGestionDocumental.FECHA_INICIO%>" value="<%=fechaInicioTurnosSeleccionados%>">
                                                          <input  type="hidden" name="<%=AWGestionDocumental.FECHA_FIN%>" id="<%=AWGestionDocumental.FECHA_FIN%>" value="<%=fechaFinTurnosSeleccionados%>">
                                                          <td width="20px"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20px" height="15"></td>
                                                          <td width="155px"><input name="imageField" type="image" onClick="cambiarAccionDepurarTurnoPorFechaSGD('<%=AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA%>');" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" width="139" height="21" border="0"></td>
                                                          <td>&nbsp;</td>
                                                      </tr>
                                                  </table>
                                              </form>
                                            <%}%>
                                      </td>
                                  </tr>
                              <%}
                                if(!turnosDepurados.equals("0")){%>
                                    <tr>
                                          <td colspan="2">
                                              <table width="100%" class="campostip04">
                                                  <tr>
                                                      <td align="center">La depuraci&oacute;n de los turnos seleccionados se ha realizado <b>Exitosamente.</b></td>
                                                  </tr>
                                              </table>
                                          </td>
                                      </tr>
                                <%}%>
                          </table>
                      </td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                  </tr>
                  <tr>
                      <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                      <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                  </tr>
              </table>
          </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
          <td>&nbsp;</td>
      </tr>
      <tr>
          <td>&nbsp;</td>
          <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
          <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
          <td>&nbsp;</td>
      </tr>
  </table>