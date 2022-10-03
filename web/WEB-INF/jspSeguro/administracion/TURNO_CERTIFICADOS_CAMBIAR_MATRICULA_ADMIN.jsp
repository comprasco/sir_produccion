<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="java.util.List"%>
<%
	//System.out.println("CAMBIAR_MATRICULA_ADMIN******************");
	//System.out.println("CAMBIAR_MATRICULA_ADMIN******************");
	//System.out.println("CAMBIAR_MATRICULA_ADMIN******************");
	//System.out.println("CAMBIAR_MATRICULA_ADMIN******************");
	//System.out.println("CAMBIAR_MATRICULA_ADMIN******************");
 	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
 	/*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se verifica que el folio asociado al turno sea diferente a null
         * Caso Mantis  :   02359
         */
        String actualMatricula = null;
        if(folio != null){
            actualMatricula=folio.getIdMatricula();
        }
   //System.out.println("CAMBIAR_MATRICULA_ADMIN******************folioioasido  " + folio.getIdMatricula() );
  	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}

	NotasInformativasAdministrativaHelper helperNotasInformativas = new NotasInformativasAdministrativaHelper();
	
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);

%>
<script type="text/javascript">
function cambiarAccion(text) {
	document.form1.<%= WebKeys.ACCION %>.value = text;
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cambiar Matr&iacute;cula</td>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
		  <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cambiar Matr&iacute;cula</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
          </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">              <table width="100%" >
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                      <tr>
                        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                        <td class="bgnsub">Cambiar matricula inmobiliaria asociada al certificado </td>
                        <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                      </tr>
                    </table>
                      <table width="100%" class="camposform">
                          <%
                          /*
                           * @author      :   Julio Alcázar Rivas
                           * @change      :   if que verifica que actualMatricula sea diferente a null
                           * Caso Mantis  :   02359
                           */
                          if (actualMatricula!= null){
                          %>
                              <tr>
                              <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                              <td width="100">Matr&iacute;cula Actual </td>
                              <td class="campositem"><%=actualMatricula%>&nbsp;</td>
                              </tr>
                          <%
                          /*
                           * @author      :   Julio Alcázar Rivas
                           * @change      :   cierre if que verifica que actualMatricula sea diferente a null
                           * Caso Mantis  :   02359
                           */
                          }
                          %>
                        <tr>
 
                          <form name="form1" method="post" action="trasladoTurno.do">
                          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWTrasladoTurno.CAMBIAR_FOLIO_MATRICULA_ID%>">                          
                          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                          <!--
                           * @author      :   Julio Alcázar Rivas
                           * @change      :   Ajustes al diseño html
                           * Caso Mantis  :   02359
                          -->
                          <td width="100" align="left">Nueva Matr&iacute;cula </td>
                          <%if (!turno.isNacional())
                          {%>
                          <!--
                           * @author      :   Julio Alcázar Rivas
                           * @change      :   Ajustes al diseño html
                           * Caso Mantis  :   02359
                          -->
                          <td align="left"><%=idCirculo%>-<input type="text" name="<%=AWTrasladoTurno.FOLIO_MATRICULA_ID%>" class="camposformtext" size="25" maxlength="25"/></td>
                          <%} else
                          { %>
                          <td><%
                          
                          List elementos = new java.util.ArrayList();
		                List circulos = (List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA);
		                Circulo circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		                for( java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
		                        gov.sir.core.negocio.modelo.Circulo circuloAux = (gov.sir.core.negocio.modelo.Circulo) iter.next();
		                        if (!circuloAux.getIdCirculo().equals(circ.getIdCirculo()))
		                        {
		                        	elementos.add(new ElementoLista(circuloAux.getIdCirculo(), circuloAux.getNombre()));
		                        }
		                }
	                
                          ListaElementoHelper circuloHelper = new ListaElementoHelper();
				        circuloHelper.setOrdenar(false);
				        circuloHelper.setNombre(AWTrasladoTurno.CIRCULO_CERTIFICADO_NACIONAL);
				        circuloHelper.setId(AWTrasladoTurno.CIRCULO_CERTIFICADO_NACIONAL);
				        circuloHelper.setCssClase("camposformtext");
				        circuloHelper.setTipos(elementos);
				        circuloHelper.render(request, out);
				        out.write("</td>");
                          %>
                          <!--
                           * @author      :   Julio Alcázar Rivas
                           * @change      :   Ajustes al diseño html
                           * Caso Mantis  :   02359
                          -->
                          </td><td align="left"><input type="text" name="<%=AWTrasladoTurno.FOLIO_MATRICULA_ID%>" class="camposformtext" size="25" maxlength="25"/></td>
                          <% } %>
                        </tr>
                      </table></td>
                  </tr>
				 <tr>
                    <td><table width="100%" class="camposform">
                      <tr>
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                        <td width="150"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" name="Folio" width="180" height="21" border="0" id="Folio" ></td>
                        <td>
							<input name="imageField" type="image" onClick="cambiarAccion('<%=AWTrasladoTurno.CANCELAR_CAMBIAR_FOLIO_MATRICULA_ID%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0">
                        </td>
                     </form>
                      </tr>
                    </table></td></tr>
            </table></td><td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
      </table>  
      
      <%
		try{
			helperNotasInformativas.render(request,out);
		}catch(HelperException re){
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
