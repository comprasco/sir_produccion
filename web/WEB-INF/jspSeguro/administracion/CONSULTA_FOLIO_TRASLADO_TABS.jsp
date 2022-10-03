<!--
/**
 * JSP que muestra la información de folios en el proceso traslado de folio
 * @version 1.0, 24/12/2010
 * @author Julio Alcazar
 */
-->
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%@page import="gov.sir.core.negocio.modelo.constantes.CTabs"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CExcepcion"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script>
	function cambiarTab(tabid){
		document.<%=CTabs.FICHAS%>.<%=CTabs.TAB%>.value=tabid
		document.<%=CTabs.FICHAS%>.submit();
	}
</script>

    <form  action='traslado.detalles.folio.view' name='<%=CTabs.FICHAS%>' method='post'>
    	<input type='hidden' name='<%=CTabs.TAB%>'>
		<%
			String tab = request.getParameter(CTabs.TAB);
			session.setAttribute(CTabs.TAB,tab);
		%>
	</form>
	<%	
        // Si existe alguna excepcion en matrices y segregados, debe seguir en la misma pantalla
        String excepcionRazon = (String)request.getSession().getAttribute(WebKeys.RAZON_EXCEPCION);
		if(excepcionRazon!=null&&excepcionRazon.equals(CExcepcion.MATRIZ_SEGREGADOS_EXCEPCION)){	
			tab=CTabs.TAB3;
			session.setAttribute(CTabs.TAB,tab);
		}
	%>
	<%if(tab == null || tab.equals(CTabs.TAB1)) {%>
    <table border="0" cellpadding="0" cellspacing="0" width="100%")">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>

        
        <tr>
          <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">         
          
          <table border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB1%>')">
                  <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Detalles Folio</td>
                    <td><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB3%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Matriz/Segregaciones</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB4%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Gravamenes</a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB6%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Salvedades </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_salvedad.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB7%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Cancelaciones </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_cancelar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

              </tr>
          </table></td>
          <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><%@ include file="CONSULTA_FOLIO_PRINCIPAL.jsp"%></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      



	<%}if(tab!=null&&tab.equals(CTabs.TAB2)){%>     
	<%}if(tab!=null&&tab.equals(CTabs.TAB3)){%>        
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_000.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB1%>')">
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Detalles Folio</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB3%>')">
                  <tr>
                    <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Matriz/Segregaciones </td>
                    <td><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB4%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Gravamenes</a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB6%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Salvedades </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_salvedad.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB7%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Cancelaciones </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_cancelar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>       
              </tr>
          </table></td>
          <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><%@ include file="CONSULTA_FOLIO_PADRES_HIJOS.jsp"%></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      
      
	<%}if(tab!=null&&tab.equals(CTabs.TAB4)){%>        
      <table border="0" cellpadding="0" cellspacing="0" width="100%">

        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_000.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB1%>')">
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Detalles Folio</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB3%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Matriz/Segregaciones</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB4%>')">
                  <tr>
                    <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Gravamenes</td>
                    <td><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB6%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Salvedades </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_salvedad.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB7%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Cancelaciones </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_cancelar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>                
              </tr>
          </table></td>
          <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><%@ include file="CONSULTA_FOLIO_GRAVAMENES.jsp"%></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
	<%}if(tab!=null&&tab.equals(CTabs.TAB5)){%>       
	<%}if(tab!=null&&tab.equals(CTabs.TAB6)){%>        
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_000.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB1%>')">
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Detalles Folio</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>

                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB3%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Matriz/Segregaciones</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB4%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Gravamenes</a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
               
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB6%>')">
                  <tr>
                    <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Salvedades</td>
                    <td><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_salvedad.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
                </table></td> 
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB7%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Cancelaciones </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_cancelar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>        
              </tr>
          </table></td>
          <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><%@ include file="CONSULTA_FOLIO_SALVEDADES.jsp"%></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      



	<%}if(tab!=null&&tab.equals(CTabs.TAB7)){%>        
      <table border="0" cellpadding="0" cellspacing="0" width="100%">

        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_000.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB1%>')">
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Detalles Folio</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB3%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Matriz/Segregaciones</a><a href="#"> </a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB4%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Gravamenes</a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB6%>')">
                    <tr>
                      <td valign="top"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_001.gif" width="7" height="29" border="0" alt=""></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif" class="titulotbcentral_tabs"><a href="#">Salvedades </a><a href="#"></a></td>
                      <td width="12" valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_003.gif" width="12" height="29" border="0" alt=""></td>
                      <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_salvedad.gif" width="16" height="21"></td>
                          </tr>
                      </table></td>
                      <td valign="top"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_inactiva_004.gif" width="12" height="29" border="0" alt=""></td>
                    </tr>
                </table></td>
                <td valign="top"><table border="0" cellpadding="0" cellspacing="0" onclick="cambiarTab('<%=CTabs.TAB7%>')">
                  <tr>
                    <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_tabs_activa_000.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cancelaciones</td>
                    <td><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                    <td valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="<%=request.getContextPath()%>/jsp/images/ico_cancelar.gif" width="16" height="21"></td>
                        </tr>
                    </table></td>
                    <td><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                  </tr>
                </table></td>             
              </tr>
          </table></td>
          <td valign="top"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><%@ include file="CONSULTA_FOLIO_CANCELACIONES.jsp"%></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	<%}%>     

