<%@page import="java.util.*" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.*" %> 
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.smart.SMARTKeys" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase"%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR" %>
<%@page import="co.com.iridium.generalSIR.comun.GeneralSIRException" %>
<%@page import="java.util.logging.Logger" %>
<%@page import="java.util.logging.Level" %>

<%
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
	TextHelper textHelper = new TextHelper();
	List calificadores = (List)session.getAttribute(AWTrasladoTurno.LISTA_CALIFICADORES);     
	List turnosSiguientes = (List)session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES); 

	List tipos = (List)session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
	if(tipos == null){
		tipos = new ArrayList();
	}
	
	List listaProcesos = (List)application.getAttribute(WebKeys.LISTA_PROCESOS_SISTEMA);
	ElementoLista elementoProceso = null;
	List procesos = new ArrayList();

	elementoProceso = new ElementoLista();
	elementoProceso.setId("CUALQUIERA");
    elementoProceso.setValor("CUALQUIERA");

    if(!listaProcesos.contains(elementoProceso))
        procesos.add(elementoProceso);
	
	for(Iterator iteradorProcesos = listaProcesos.iterator(); iteradorProcesos.hasNext();) {
	    elementoProceso = (ElementoLista)iteradorProcesos.next();
	    //elementoProceso = new ElementoLista();
	    //elementoProceso.setId(String.valueOf(proceso.getIdProceso()));
	    //elementoProceso.setValor(proceso.getNombre());
	    procesos.add(elementoProceso);
	}
	ListaElementoHelper procesosHelper = new ListaElementoHelper();
	procesosHelper.setTipos(procesos);
	//procesosHelper.setSelected(WebKeys.SIN_SELECCIONAR);

	String idProceso = (String)session.getAttribute(CProceso.PROCESO_ID);
	if(idProceso == null)
    	idProceso = "CUALQUIERA";
	procesosHelper.setSelected(idProceso);
	
	String vistaActual = (String) session.getAttribute(SMARTKeys.VISTA_ACTUAL);
	session.setAttribute(WebKeys.VISTA_VOLVER, vistaActual);
	
	//notas informativas
	ListaElementoHelper tiposNotaHelper = new ListaElementoHelper();
	ListarNotasPasadas notasHelper = new ListarNotasPasadas();
	notasHelper.setMostrarImpresion(true);
	
    List listaNotasInformativas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
    List elementosTipoNota = new Vector();
	
	if (listaNotasInformativas != null)
	{
	   Iterator itTipos = listaNotasInformativas.iterator();
	   
	   while (itTipos.hasNext()) {
	   TipoNota tipoNota = (TipoNota) itTipos.next();
	   elementosTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), tipoNota.getIdTipoNota() + " - " +tipoNota.getNombre()));
	   }
	}
	
		
	tiposNotaHelper.setCssClase("camposformtext");
	tiposNotaHelper.setId(CTipoNota.ID_TIPO_NOTA);
	tiposNotaHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
	tiposNotaHelper.setTipos(elementosTipoNota);
	tiposNotaHelper.setShowInstruccion(true);

	
	TextAreaHelper helper = new TextAreaHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionAndSubmit(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function cambiarAccionFiltro(text) {
	document.FILTRAR.<%= WebKeys.ACCION %>.value = text;
	document.FILTRAR.submit();
}

function confirmar(text) {
	if(confirm('¿Esta seguro que desea reanotar el turno?')){
		cambiarAccionAndSubmit(text);
	}
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reanotacion Turnos</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reanotacion</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos del Turno</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" value="<%=  AWTrasladoTurno.ANULAR_TURNO%>">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>

                    <td width="220">Turno</td>
                    <td class="campositem"><%=turno.getIdWorkflow() %></td>
                    <td width="220">Fase</td>
                    <td class="campositem"><%=turno.getIdFase() %></td>
                    </tr>

                    
                   
                </table>
                <%if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && turnosSiguientes!=null && turnosSiguientes.size()>0) {
                	for(int i=0;i<turnosSiguientes.size();i++){
                		String listaTurnos = (String)turnosSiguientes.get(i);%>
                		<table width="100%" class="camposform">
                  			<tr>
                    			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>

                    			<td class="titresaltados" colspan="2"> <img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21"><%=listaTurnos %></td>	
                    		</tr>
                		</table>
                	
                	<%}
                } %>
                <%if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && !turno.getIdFase().equals(CFase.FINALIZADO)){ %>
                <br>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="40%" align='right'>                    	
                    	<b>Funcionario Calificador:</b>
                    </td>
                    <td >                   	
					<% 
					try {
                                                /**
                                                * @author              : HGOMEZ
                                                * @casoMantis          : 13898.
                                                * @actaRequerimiento   : 075_453_Reanotación_Turnos_Registro
                                                * @change              : Ajustes necesarios para dar solución al requerimiento.
                                                */
                                                ValidacionesSIR validacion = new ValidacionesSIR();
                                                String codigoPrimerUsuarioCalificador = String.valueOf(validacion.getCodigoPrimerUsuarioCalificador(turno.getAnio(), turno.getIdCirculo(), Long.toString(turno.getIdProceso()), turno.getIdTurno()));                                
						List usuarioElementoLista = new ArrayList();
						if(calificadores!=null){
							java.util.Iterator it = calificadores.iterator();
							while(it.hasNext()){
								Usuario usuario = (Usuario)it.next();
								ElementoLista elemento = new ElementoLista();
								elemento.setId(""+usuario.getIdUsuario());
								elemento.setValor(usuario.getUsername());
								usuarioElementoLista.add(elemento);
                                                                if(codigoPrimerUsuarioCalificador.equals(Long.toString(usuario.getIdUsuario()))){
                                                                    session.setAttribute(AWTrasladoTurno.CALIFICADOR_SELECCIONADO, codigoPrimerUsuarioCalificador);
                                                                }
							}
						}
					
						ListaElementoHelper listaHelper= new ListaElementoHelper();
          			    listaHelper.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_VALOR);
           				listaHelper.setTipos(usuarioElementoLista);
          			    listaHelper.setId(AWTrasladoTurno.CALIFICADOR_SELECCIONADO);
          			    listaHelper.setNombre(AWTrasladoTurno.CALIFICADOR_SELECCIONADO);
          			    listaHelper.setShowInstruccion(true);
						listaHelper.setCssClase("camposformtext");						
						listaHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					%>                    	
                    </td>
                  </tr>
                </table>
                <%} %>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Nota</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Escoger Tipo de Nota</td>
                  <td>
				<% 
				try {
					tiposNotaHelper.render(request,out);
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}	
				%>
				</td>
                </tr>
              </table>
              
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Descripci&oacute;n</td>
                </tr>
                <tr> 
                  <td>&nbsp;</td>
                  <td>
					<% 
					try{
						helper.setCols("100");
						helper.setReadOnly(false);
						helper.setCssClase("camposformtext");
						helper.setId(CNota.DESCRIPCION);
						helper.setNombre(CNota.DESCRIPCION);
						helper.setRows("10");
						helper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
                  </td>
                </tr>
              </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
						<a href="javascript:confirmar('<%=  AWTrasladoTurno.REANOTAR_TURNO%>')">                    
                      		<img src="<%=request.getContextPath()%>/jsp/images/btn_reanotar_turno.gif" width="139" height="21" border="0" id="banular"/>
                    	</a>
                    	</td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWTrasladoTurno.CANCELAR_REANOTACION%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
            </FORM>    
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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

