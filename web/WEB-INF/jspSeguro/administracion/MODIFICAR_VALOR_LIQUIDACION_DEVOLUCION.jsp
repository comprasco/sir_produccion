<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.List"%>

<%
Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
SolicitudDevolucion solicitud = (SolicitudDevolucion)turno.getSolicitud();
Ciudadano ciudadano = solicitud.getCiudadano();
TextAreaHelper area = new TextAreaHelper();
session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/solicitud.getDescripcion());
area.setCols("90");
area.setRows("10");
TextHelper textHelperDerechos = new TextHelper();
TextHelper textHelperImpuestos = new TextHelper();
TextHelper textHelperCertificados = new TextHelper();
NotasInformativasHelper helper = new NotasInformativasHelper();
String liquidacionNegativa = null;
%>


<script type="text/javascript">
function cambiarAccion(text) {
	document.LIQUIDACION_TURNO_DEVOLUCION.<%= WebKeys.ACCION %>.value = text;
}
</script>

<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">MODIFICAR VALOR DEVOLUCION</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Modificar Valor Turno de Devolución</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno asociado</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Turno asociado</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>

                  <% if (solicitud.getTurnoAnterior() != null &&
                    solicitud.getTurnoAnterior().getIdWorkflow() != null) {
                    session.setAttribute(WebKeys.VISTA_VOLVER,request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));

                    %>

                    <form action="trasladoTurno.do" method="post" name="CONSULTA" id="CONSULTA">
                      <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWTrasladoTurno.CONSULTAR_TURNO %>" value="<%= AWTrasladoTurno.CONSULTAR_TURNO %>">
                      <input  type="hidden" name="<%= CTurno.ID_TURNO %>" id="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow()%>" value="<%= ((Turno)solicitud.getTurnoAnterior()).getIdWorkflow() %>">
                    	<input  type="hidden" name="r1" id="r1" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>" >

                          <tr>
                            <td>&nbsp;</td>
                            <td>Turno con el que ingresó el documento o certificado a registro:</td>
                            <td class="campositem"><%=((Turno)solicitud.getTurnoAnterior()).getIdWorkflow()%></td>
                            <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0">
                            </td>
                          </tr>
                        </form>

                  <%
                  } else {
                  %>
                  <td>&nbsp;</td>
                  <td>Turno con el que ingresó el documento o certificado a registro:</tr>
                  <td class="campositem">&nbsp;</td>
                   <%
                  }
                  %>


                  </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {
                 				area.setNombre(CTurno.DESCRIPCION);
                  			   	area.setCssClase("campositem");
                  			   	area.setId(CTurno.DESCRIPCION);
                  			   	area.setReadOnly(true);
								area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
					</td>
                </tr>
              </table>
	          <br>
              <hr class="linehorizontal">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem"><%=ciudadano.getTipoDoc() != null ? ciudadano.getTipoDoc() : "&nbsp;" %></td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento() != null ? ciudadano.getDocumento() : "&nbsp;" %></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido1() != null ? ciudadano.getApellido1() : "&nbsp;" %></td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido2() != null ? ciudadano.getApellido2() : "&nbsp;" %></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombre</td>
                  <td class="campositem"><%=ciudadano.getNombre() != null ? ciudadano.getNombre() : "&nbsp;" %></td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              <hr class="linehorizontal">
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Valores a Devolver</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
               <br>
              <table width="100%" class="camposform">
              
                 <% 
                 Turno turnoAnterior = null;
				 String valorTotal="";
				 String valorImpuestos="";
				 String valorDerechos ="";
				 LiquidacionTurnoRegistro liqAnterior = null;
				 LiquidacionTurnoCertificado liqCertAnt = null;
				 Solicitud solAnterior= null;
                 if (turno != null)
				 {
				 
				    
				    Solicitud solTurno = turno.getSolicitud();
				    if (solTurno != null)
				    {
				       turnoAnterior = (Turno)solicitud.getTurnoAnterior();
				    }
				    if (turnoAnterior != null)
				    {
				       solAnterior = turnoAnterior.getSolicitud();
				       if (solAnterior != null)
				       {
				          
				       } 
				    }
				 }
				 %>
				 
				 <% if (solAnterior != null)
				 {
				   if (solAnterior instanceof SolicitudRegistro)
				    {
				       List listaLiqsAnt = solAnterior.getLiquidaciones();
				       if (listaLiqsAnt != null)
				       {
				          for (int w=0; w<listaLiqsAnt.size(); w++)
				          {
				             liqAnterior = (LiquidacionTurnoRegistro) listaLiqsAnt.get(w);
				          }
				          if (liqAnterior != null)
				          {
				               double valDerDev = liqAnterior.getValorDerechos();
				               double valImpDev = liqAnterior.getValorImpuestos();
				               double valTotDev = liqAnterior.getValor();
				               
				                //Si estos valores son positivos es porque aun no se ha creado la liquidacion
				                //con la cual se va a realizar la devolución.  En este caso se setean en 0
				                
				                //Derechos
				                if (valDerDev >=0)
				                {
				                    valorDerechos = 0+"";
				                }
				                else
				                {
				                    valorDerechos = valDerDev+"";
				                    //Se marca el flag que indica que si hay liquidación negativa
				                    liquidacionNegativa = AWAdministracionHermod.LIQUIDACION_NEGATIVA;
				                }
				                
				                
				                //Impuestos
				                 if (valImpDev >=0)
				                {
				                    valorImpuestos = 0+"";
				                }
				                else
				                {
				                    valorImpuestos = valImpDev+"";
				                    //Se marca el flag que indica que si hay liquidación negativa
				                    liquidacionNegativa = AWAdministracionHermod.LIQUIDACION_NEGATIVA;
				                }
				                
				                
				                // Valor Total
				                if (valTotDev >=0)
				                {
				                    valorTotal = 0+"";
				                }
				                else
				                {
				                    valorTotal = valTotDev+"";
				                    //Se marca el flag que indica que si hay liquidación negativa
				                    liquidacionNegativa = AWAdministracionHermod.LIQUIDACION_NEGATIVA;
				                }
				                
				                
				                
				          }
				       }
				       
				    

				    %>
                      <tr>
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td width="179">Derechos de Registro</td>
                        <td width="211" class="campositem"><%=valorDerechos%></td>
                     </tr>
                     <tr>
                        <td>&nbsp;</td>
                        <td>Impuestos</td>
                        <td class="campositem"><%=valorImpuestos%></td>
                  
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Total</td>
                  <td class="campositem"><%=valorTotal%></td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <%}%>
                
                
				<%if (solAnterior instanceof SolicitudCertificado)
				    {
				       List listaLiqsAnt = solAnterior.getLiquidaciones();
				       if (listaLiqsAnt != null)
				       {
				          for (int w=0; w<listaLiqsAnt.size(); w++)
				          {
				             liqCertAnt = (LiquidacionTurnoCertificado) listaLiqsAnt.get(w);
				          }
				          if (liqAnterior != null)
				          {
				               double valTotDevCert = liqAnterior.getValor();
				               
				                //Si estos valores son positivos es porque aun no se ha creado la liquidacion
				                //con la cual se va a realizar la devolución.  En este caso se setean en 0

				                //Certificados
				                if (valTotDevCert >=0)
				                {
				                    valorTotal = 0+"";
				                }
				                else
				                {
				                    //Se marca el flag que indica que si hay liquidación negativa
				                    liquidacionNegativa = AWAdministracionHermod.LIQUIDACION_NEGATIVA;
				                    valorTotal = valTotDevCert+"";
				                }
				               
				               
				               
				          }
				       }
				    %>
                  <tr>
                  <td>&nbsp;</td>
                  <td>Total</td>
                  <td class="campositem"><%=valorTotal%></td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <%}
                }%>         
         
              </table>
              
              <br>
              <hr class="linehorizontal">
              
              <form action="administracionHermod.do" method="post" name="LIQUIDACION_TURNO_DEVOLUCION" id="LIQUIDACION_TURNO_DEVOLUCION">
               <% String idTurno = turno.getIdWorkflow();%>
               <input type="hidden" name="<%=WebKeys.TURNO%>" value="<%=idTurno%>">
               <input type="hidden" name="<%=AWAdministracionHermod.LIQUIDACION_NEGATIVA%>" value="<%=liquidacionNegativa==null?"":AWAdministracionHermod.LIQUIDACION_NEGATIVA%>">
               <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA%>">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Modificación de los valores a Devolver</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <br>
              <table width="100%" class="camposform">
              <% if (solAnterior != null)
              {
                 if (solAnterior instanceof SolicitudRegistro)
				 {%>
                    <tr>
                      <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td width="179">Derechos de Registro</td>
                      <td> 
                         <% try 
                         {
                             textHelperDerechos.setNombre(AWAdministracionHermod.VALOR_DERECHOS);
                  	         textHelperDerechos.setCssClase("camposformtext"); 
                     	     textHelperDerechos.setId(AWAdministracionHermod.VALOR_DERECHOS);
					         textHelperDerechos.render(request,out); 
					     }
					     catch(HelperException re)
					     { 
						     out.println("ERROR " + re.getMessage());
					     }
			     %>
			       </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>Impuestos</td>
                      <td> 
                      <% try 
                      {
                         textHelperImpuestos.setNombre(AWAdministracionHermod.VALOR_IMPUESTOS);
                  	     textHelperImpuestos.setCssClase("camposformtext"); 
                     	 textHelperImpuestos.setId(AWAdministracionHermod.VALOR_IMPUESTOS);
					     textHelperImpuestos.render(request,out); 
					  }
					  catch(HelperException re)
					  { 
						 out.println("ERROR " + re.getMessage());
					  }
			     %>
			     </td>
                 </tr>
                <%}
                if (solAnterior instanceof SolicitudCertificado)
				{%>
                    <tr>
                      <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td width="179">Valor Total</td>
                      <td> 
                      <% try 
                      {
                          textHelperCertificados.setNombre(AWAdministracionHermod.VALOR_CERTIFICADOS);
                  	      textHelperCertificados.setCssClase("camposformtext"); 
                     	  textHelperCertificados.setId(AWAdministracionHermod.VALOR_CERTIFICADOS);
 				          textHelperCertificados.render(request,out); 
					  }
					  catch(HelperException re)
					  { 
					     out.println("ERROR " + re.getMessage());
					  }
			     %>
			       </td>
                   </tr>
                <%}
              }%>
              
              </table>
                   
              
               <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
				
				
				  
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                  <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                  </td>
                  
                  <td width="140">
                  <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                  </td>
                  
				  </form>
				  <td>&nbsp;</td>
                </tr>
              </table>
          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
 	<% try {
				helper.render(request,out);
			}
				catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
	%>
     </td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
