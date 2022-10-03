<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWRelacion"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Relacion"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuestaMesaControl"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoRelacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRelacion"%>
<%@page import="gov.sir.core.negocio.modelo.util.IDidworkflowComparator" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Vector"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>

<%	 
	//SE OBTIENE LA INFORMACIÓN DE LOS TURNOS ASOCIADOS Y
	//SI SE DEBEN IMPRIMIR O NO
	Hashtable turnosRegistro = (Hashtable) session.getAttribute(WebKeys.LISTA_TURNOS_RELACION);
	Hashtable turnosValidosImpresion = (Hashtable) session.getAttribute(WebKeys.LISTA_TURNOS_VALIDOS);

	Relacion relacion = (Relacion) session.getAttribute(CRelacion.RELACION_FIRMA);

	//SE COLOCA EN SESIÓN ESTA VARIABLE PARA QUE NO SE CIERRE LA PÁGINA DE CAMBIO DE MATRICULA
	session.setAttribute("CERRAR_VENTANA", new Boolean(false));

	boolean carga=false;
	//SI SE DESEA QUE SE RECARGUE LA INFORMACIÓN DE ESTA PÁGINA SE COLOCA LA VARIABLE CON VALOR TRUE
	//ESTO OCURRE LUEGO DE UN CAMBIO DE MATRÍCULA O LUEGO DE LA INPRESIÓN DE ALGÚN CERTIFICADO ASOCIADO
	Boolean oCarga= (Boolean)session.getAttribute("CARGAR_MESA_CONTROL");
	if(oCarga!=null){
		carga= oCarga.booleanValue();
	}
	
	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	boolean hayExcepcion = false;
	
	if(exception!=null){
		session.removeAttribute(WebKeys.HAY_EXCEPCION);		 
		hayExcepcion = true;
	}
	
	if(!hayExcepcion && carga){
		carga = true;
	}else{
		carga = false;
	}
%>
<%
String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
//System.out.println("1VISTA"+vistaAnterior);
vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL);
//System.out.println("2VISTA"+vistaAnterior);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>  
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.submit();
}		
function cambiarAccionRelacion(text) {       
	if(confirm('Esta seguro de haber impreso los certificados asociados a turnos de registro de esta relación?')){
		document.BUSCAR.action = 'relacion.do';
       	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
       	document.BUSCAR.submit();
	}

}
function cambiarAccion(accion, numcert) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.<%= AWMesa.NUM_CERT %>.value= numcert;
       document.BUSCAR.submit();
}	

function cambiarAccion(accion, numcert, nummat) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.submit();
}	

function cambiarSeleccion() {
    if(document.BUSCAR.<%=AWMesa.NUM_CERT%>1.checked == true){
	    setAllCheckBoxes('BUSCAR', '<%=AWMesa.NUM_CERT%>', true);
    }
    if(document.BUSCAR.<%=AWMesa.NUM_CERT%>1.checked == false){
    	setAllCheckBoxes('BUSCAR', '<%=AWMesa.NUM_CERT%>', false);
    }
}

function cambiarMatricula(nombre,valor, dimensiones) {
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function cargaInicial() {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%= AWMesa.CONSULTAR_RELACION %>';
	document.BUSCAR.submit();
}
</script>

<form action="mesa.do" method="POST" name="BUSCAR" id="BUSCAR">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">

<input  type="hidden" name="<%= AWRelacion.ID_PROCESO %>" id="<%= AWRelacion.ID_PROCESO %>" value="<%=gov.sir.core.negocio.modelo.constantes.CProceso.PROCESO_REGISTRO%>">
<input  type="hidden" name="<%= AWRelacion.ID_FASE %>" id="<%= AWRelacion.ID_FASE %>" value="<%=gov.sir.core.negocio.modelo.constantes.CFase.REG_CERTIFICADOS_ASOCIADOS%>">
<input  type="hidden" name="<%= AWRelacion.ID_TIPO_RELACION %>" id="<%= AWRelacion.ID_TIPO_RELACION %>" value="<%=CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA%>">
<input  type="hidden" name="<%= AWRelacion.ID_RELACION %>" id="<%= AWRelacion.ID_RELACION %>" value="<%=(String)session.getAttribute(AWRelacion.ID_RELACION)%>">
<input  type="hidden" name="<%= AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE %>" id="<%= AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE %>" value="true">
						
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Certificados Asociados
  
                  </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
			
			  <BR>

			  <%if(relacion!=null){
			  		String complementoDesc = "";
			  		if(relacion!=null&&relacion.getTipoRelacion()!=null){
						if(relacion.getTipoRelacion().getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS)){
							complementoDesc = " Turnos de documentos INSCRITOS.";
						}
						if(relacion.getTipoRelacion().getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)){
							complementoDesc = " Turnos de documentos DEVUELTOS.";
						}
					}
			 %>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turnos de certificados asociados para los documentos de la relación <%=relacion.getIdRelacion()%>. <%=complementoDesc%></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
			  <%}%>
              <table width="100%" class="camposform">
              	<tr>
                  <td width="18%" align="left"><b>Turno registro</b></td>
                  <td width="10%" align="center" valign="center"><b>Seleccionar</b><br><input type="checkbox" name="<%=AWMesa.NUM_CERT%>1" value="" onclick='cambiarSeleccion()'></td>
                  <td width="18%" align="left"><b>Turno asociado</b></td>
                  <td width="18%" align="left"><b>Matrícula</b></td>
                  <td width="18%" align="left"><b>Número copias</b></td>
                  <td width="18%" align="left"><b>Cambiar Matrícula</b></td>
                </tr>
				<%
				ArrayList keysTurnosRegistro = new ArrayList(turnosRegistro.keySet());
				Collections.sort(keysTurnosRegistro, new IDidworkflowComparator());
				Iterator iter = keysTurnosRegistro.iterator();
				
				String colspan = "1";
				Boolean turnoValidoImpresion = null;
				boolean turnoValidoImp = false;
				boolean tieneCertificados = false;
				SolicitudFolio solFolio = null;
				while(iter.hasNext()){
					String key = (String)iter.next();
					List turnosCertificados = (List)turnosRegistro.get(key);
					//System.out.println("--"+turnosCertificados.size());

					colspan = "" + turnosCertificados.size();
					if(turnosCertificados.size() > 0){
						tieneCertificados = true;
					}else{
						tieneCertificados = false;
					}
					
					Iterator it = turnosCertificados.iterator();
					
					if(tieneCertificados){
						while(it.hasNext()){
							SolicitudCertificado solicitudCertificado = (SolicitudCertificado)it.next();
							Turno turnoCertificado = solicitudCertificado.getTurno();

							turnoValidoImpresion = (Boolean)turnosValidosImpresion.get(turnoCertificado.getIdWorkflow());
							if(turnoValidoImpresion!=null){
								turnoValidoImp = turnoValidoImpresion.booleanValue();
							}else{
								turnoValidoImp = false;
							}

							if(solicitudCertificado.getSolicitudFolios().size()>0){
								solFolio = (SolicitudFolio)solicitudCertificado.getSolicitudFolios().get(0);
							}else{
								solFolio = null;
							}

				%>
              	<tr>
				  <%if((new Integer(colspan)).intValue()>0){ %>
	                  <td width="18%" align="left" class="campositem" rowspan = "<%=colspan%>"><%=key%></td>
				  <%}%>
                  <td width="10%" align="center">
				  <%if(turnoValidoImp && solFolio!=null){%>
					  <input type="checkbox" name="<%=AWMesa.NUM_CERT%>" value="<%=turnoCertificado!=null?turnoCertificado.getIdWorkflow():""%>">
                  <%
				  }else{
				  %>
					&nbsp;
				  <%
				  }
				  %>
				  </td>
                  <td width="18%" align="left" class="campositem"><%=turnoCertificado!=null?turnoCertificado.getIdWorkflow():""%></td>
                  <td width="18%" align="left" class="campositem"><%=solFolio!=null?solFolio.getIdMatricula():"&nbsp;"%></td>
                  <td width="18%" align="left" class="campositem"><%=solicitudCertificado!=null?""+solicitudCertificado.getNumeroCertificados():"&nbsp;" %></td>
                  <td width="18%" align="left" class="campositem">
                  <%if(turnoValidoImp){%>
                  <a href="javascript:cambiarMatricula('mesa.do?<%=WebKeys.ACCION%>=<%=AWMesa.CARGAR_CAMBIAR_MATRICULA%>&MATRICULA=<%=solFolio!=null?solFolio.getIdMatricula():"&nbsp;"%>&NUM_CERT=<%= turnoCertificado.getIdWorkflow()%>&NUM_MAT=<%= ""+solicitudCertificado.getNumeroCertificados()%>&NUM_REG=<%= ""+key%>','Cambio_matricula','width=900,height=450,scrollbars=yes')">
	                  <img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula">
                  <a>
				  <%
				  }else{
				  %>
					&nbsp;
				  <%
				  }
				  %>
				  </td>
                </tr>
				<%
						colspan = "0";
						}
					}else{
				%>






              	<tr>
                  <td width="18%" align="left" class="campositem" ><%=key%></td>
                  <td width="10%"  align="center">&nbsp;</td>
                  <td width="18%" colspan="4" align="left" class="campositem">&nbsp;</td>
<!--                  <td width="18%" align="left" class="campositem">&nbsp;</td>
                  <td width="18%" align="left" class="campositem">&nbsp;</td>
                  <td width="18%" align="left" class="campositem">&nbsp;</td>-->
                </tr>



				
				<% 
					}
				//System.out.println("5**");
				}%>
              </table>
           
           
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                 <td width="15%"><a href="javascript:cambiarAccionRelacion('<%= AWRelacion.CREAR_RELACION %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Aceptar"></a></td>
                  <td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.IMPRIMIR_CERTIFICADO_RELACION %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" name="Folio" width="139" height="21" border="0" id="Imprimir"></a></td>
                  <td width="55%">&nbsp;</td>
                </tr>
              </table>
            </form>
            
          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      

        <%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
		
        
      </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
</form>
<%if(carga){%>
	<script>cargaInicial();</script>
<%}%>