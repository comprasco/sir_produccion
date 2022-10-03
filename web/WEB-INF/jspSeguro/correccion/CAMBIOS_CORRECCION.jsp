<%@page import ="java.util.*"%>
<%@page import ="gov.sir.core.web.WebKeys"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AWCorreccion"%>


<%

	List diferencias = (List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS);

	if (diferencias==null){
		diferencias = new Vector();
	}

	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);

%>
<SCRIPT>
function enviar(text) {
    document.CORRECCION.submit();
}
</SCRIPT>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"></link>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Vista previa de cambios realizados.</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cambios realizados en la corrección</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_notasagregar.gif" width="16" height="21"></td>
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

            <form action="correccion.do" method="post" name="CORRECCION" id="CORRECCION">
			  <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCorreccion.APROBAR%>">

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Cambios realizados.</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>

              <%
              if(diferencias.size()>0){
              Iterator it = diferencias.iterator();
              %>
              <table width="100%"  class="camposform">
                <tr>
                  <td width="24%"><b>ITEM</b></td>
                  <td width="38%"><b>ANTES</b></td>
                  <td width="38%"><b>DESPUÉS</b></td>
                </tr>
                </table>
                <table width="100%"  class="camposform">
                <%
				while(it.hasNext()){
				gov.sir.core.negocio.modelo.Diferencias diferencia = (gov.sir.core.negocio.modelo.Diferencias)it.next();
                %>
                <tr>
                  <td width="24%" ><%=diferencia!=null&&diferencia.getId()!=null?diferencia.getId():""%>&nbsp;</td>
                  <td width="38%" class="campostip04"><%=diferencia!=null&&diferencia.getAntes()!=null&&!diferencia.getAntes().equals("")&&!diferencia.getAntes().equals(" ")?diferencia.getAntes():" - "%>&nbsp;</td>
                  <td width="38%" class="campositem"><%=diferencia!=null&&diferencia.getDespues()!=null?diferencia.getDespues():""%>&nbsp;</td>
                </tr>
                <%
                }
                %>
              </table>

              <%
              }else{
              %>
              <table width="100%" class="camposform">
                <tr>
                  <td colspan='3'><b>No hay cambios en la corrección.</b></td>
                </tr>
              </table>

              <%
              }
              %>

              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15" />
                  </td>
                  <td>
                    <a href="<%=vistaAnterior!=null?vistaAnterior:"javascript:history.back();"%>">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"  alt="Regresar" width="150" height="21" border="0"/>
                    </a>
                  </td>
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
