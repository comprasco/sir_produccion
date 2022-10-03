<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%
	TextAreaHelper descripcion = new TextAreaHelper();
	descripcion.setCols("90");
	descripcion.setRows("10");
	ListaElementoHelper docHelper = new ListaElementoHelper();
	TextHelper textHelper = new TextHelper();

	String ocultar = request.getParameter(WebKeys.OCULTAR);
	if(ocultar == null){
		ocultar = (String)session.getAttribute(WebKeys.OCULTAR);
		if(ocultar==null){
			ocultar = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR,ocultar);
	}

	LiquidacionTurnoDevolucion liquidacion = (LiquidacionTurnoDevolucion)session.getAttribute(WebKeys.LIQUIDACION);
	SolicitudDevolucion solicitud = (SolicitudDevolucion) liquidacion.getSolicitud();
	Ciudadano ciudadano = solicitud.getCiudadano();

%>

        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
        <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Devoluciones</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                        </tr>
                    </table>
                </td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table>
        </td>
        <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td align="right" class="tdtablacentral">
        <table border="0" cellpadding="0" cellspacing="2">
            <tr>

		<%if(ocultar.equals("FALSE")){%>
            <form action="turno.devolucion.registrar.pago.view" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>

		<%}else{%>
            <form action="turno.devolucion.registrar.pago.view" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
		<%}%>
            </tr>
        </table>
        </td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>


        <tr>
        <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">

        <!--aca-->
	<%if(ocultar.equals("FALSE")){%>

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
                  <td>&iquest; Est&aacute; asociado a un Turno asociado ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>N&uacute;mero del Turno asociado</td>

	          	    <td class="campositem">
	          	    	<%if(solicitud.getTurnoAnterior()!=null)
	          	    		out.println(solicitud.getTurnoAnterior().getIdWorkflow());
	          	    	  else
	          	    		out.println("NINGUNO");%>
					</td>

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
                 				session.setAttribute(CTurno.DESCRIPCION,
                 				solicitud.getDescripcion());
                 				descripcion.setNombre(CTurno.DESCRIPCION);
                  			   	descripcion.setCssClase("campositem");
                  			   	descripcion.setId(CTurno.DESCRIPCION);
                  			   	descripcion.setReadOnly(true);
								descripcion.render(request,out);
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
                 	<td width="211">
		          		<td class="campositem"><%=ciudadano.getTipoDoc()%></td>
                 	</td>
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212">
		          		<td class="campositem"><%=ciudadano.getDocumento()%></td>
                 	</td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Primer Apellido</td>
                 	<td>
		          		<td class="campositem"><%=ciudadano.getApellido1()%></td>
                 	</td>
                 	<td>Segundo Apellido</td>
                 	<td>
		          		<td class="campositem"><%=ciudadano.getApellido2()%></td>
                 	</td>
                 	</tr>
                 	<tr>
                 	<td>&nbsp;</td>
                 	<td>Nombre</td>
                 	<td>
		          		<td class="campositem"><%=ciudadano.getNombre()%></td>
                 	</td>
                 	<td>&nbsp;</td>
                 	<td>&nbsp;</td>
                 	</tr>
             	</table>
              <hr class="linehorizontal">

	<%}%>


        </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
