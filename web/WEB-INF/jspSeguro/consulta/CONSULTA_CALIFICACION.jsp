<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.List,org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta" %>
<%@page import="gov.sir.core.negocio.modelo.TipoConsulta" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico" %>
<%
ListaRadioHelper radioHelper = new ListaRadioHelper(); 
List tiposAlcance = (List)application.getAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO);

radioHelper.setTipos(tiposAlcance);

%>
<script>
function regresar(){
	document.BUSCAR.<%= WebKeys.ACCION %>.value='<%=AWConsulta.REGRESAR_CONSULTA_CALIFICACION%>';
	document.BUSCAR.submit();
}
</script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consultas</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Nueva Consulta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
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
           



          <form action="consultas.do" method="post" name="BUSCAR" id="BUSCAR"><br>
          <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.SELECCION_CONSULTA %>" value="<%= AWConsulta.SELECCION_CONSULTA %>">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Alcance Geogr&aacute;fico de la Consulta </td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
			  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table><br>
          <table width="100%" class="camposform">
            <tr>
              <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
              <td class="titulotbcentral">Alcance</td>
            </tr>
            <% try {
                    radioHelper.setNombre(CAlcanceGeografico.ALCANCE_CONSULTA);
                  	radioHelper.setCssClase("camposformtext");
                  	radioHelper.setId(CAlcanceGeografico.ALCANCE_CONSULTA);
					radioHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>  
          </table><br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr> 
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Realizar la consulta deacuerdo al Tipo</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table><br>
          <table width="100%" class="camposform">
            <tr> 
              <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
              <td class="titulotbcentral">Tipo de Consulta</td>
              <td width="16" class="titulotbcentral">&nbsp;</td>
              <td class="titulotbcentral">&nbsp;</td>
            </tr>
            <tr> 
              <td><input name="<%= AWConsulta.TIPO_CONSULTA %>" type="radio" value="<%= TipoConsulta.TIPO_FOLIOS_CALIFICACION %>" > </td>
              <td> B&uacute;squeda por n&uacute;mero de matr&iacute;cula </td>
              <td><span class="titulotbcentral"><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></span></td>
              <td class="campostip04">Determinar N&uacute;mero de Matr&iacute;cula</td>
            </tr>
            
            <tr>
<!--              <td><input name="<%= AWConsulta.TIPO_CONSULTA %>" type="radio" value="<%= TipoConsulta.TIPO_SIMPLE_CALIFICACION %>"></td>
              <td> B&uacute;squeda por criterios de selecci&oacute;n </td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>-->
          </table>
          <hr class="linehorizontal">
          <table width="100%" class="camposform">
            <tr> 
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
              <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></td>
              <td><a href="javascript:regresar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Cancelar" width="150" height="21" border="0" id="Cancelar"></a></td>
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
