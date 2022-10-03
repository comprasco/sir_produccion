<%@page import="org.auriga.core.web.*,
java.util.Iterator,
java.util.ArrayList,
java.util.List,
gov.sir.core.web.WebKeys,
gov.sir.core.web.helpers.comun.TextHelper,
gov.sir.core.web.helpers.comun.ListaElementoHelper,
gov.sir.core.web.helpers.comun.ElementoLista,
gov.sir.core.web.acciones.administracion.AWAdministracionForseti,
gov.sir.core.negocio.modelo.Circulo,
gov.sir.core.negocio.modelo.constantes.CCirculo;" 
%>

<% 
    List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);

    List listaCirculos = new ArrayList();
    for (Iterator iter = circulos.iterator(); iter.hasNext();) {
        Circulo c = (Circulo) iter.next();
        listaCirculos.add(new ElementoLista(c.getIdCirculo(), c.getNombre()));
    }
    ListaElementoHelper circulosHelper = new ListaElementoHelper();
    circulosHelper.setTipos(listaCirculos);        
%>
	
<!--entra-->	
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Proceso de Catastro </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">
          	
          	   
          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Generaci&oacute;n de archivos de Catastro</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
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
            
            <form action="administracionForseti.do" method="post" name="REGISTRO" id="REGISTRO">
              <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionForseti.PROCESO_CATASTRO %>" value="<%= AWAdministracionForseti.PROCESO_CATASTRO %>">
                                    
              <table width="100%" class="camposform">
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>&nbsp;</td>
                        <td>Fecha inicial</td>
                        <td>
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                              <% try {
                              	TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(AWAdministracionForseti.FECHINI);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(AWAdministracionForseti.FECHINI);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                              
                              <td><a href="javascript:NewCal('<%=AWAdministracionForseti.FECHINI%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha inicial" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>               
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <tr>
                        <td>&nbsp;</td>
                        <td>Fecha final</td>
                        <td>
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                              <% try {
                              	TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(AWAdministracionForseti.FECHFIN);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(AWAdministracionForseti.FECHFIN);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                              
                              <td><a href="javascript:NewCal('<%=AWAdministracionForseti.FECHFIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha final" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>               
                            </tr>
                          </table>
                        </td>
                      </tr>

                            <tr>
                                <td>&nbsp;</td>
                                <td>C&iacute;rculo</td>
                                <td>
                                    <% 
                                        try {
                                            circulosHelper.setNombre(CCirculo.ID_CIRCULO);
                                            circulosHelper.setCssClase("camposformtext");
                                            circulosHelper.setId(CCirculo.ID_CIRCULO);
                                            circulosHelper.render(request,out);
                                        }
                                        catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>                      
                  </table></td>
                </tr>
                
              <!--</table>-->
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_confirmar.gif" name="Siguiente" width="139" height="21" border="0" id="Siguiente"></input></td>
                  <td>&nbsp;</td>
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
