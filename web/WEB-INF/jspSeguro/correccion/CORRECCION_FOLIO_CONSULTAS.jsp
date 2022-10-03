<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta"%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
%>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr> 
		<td>
            
            
            
            <form action="consultas.do" method="post" name="CERTIFICADO" id="CERTIFICADO">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>">
            <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_MATRICULA %>">            
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Observar Informaci&oacute;n del Folio</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_consulta.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">N&uacute;mero de matr&iacute;cula </td>
                  <td class="contenido"><input name="<%= gov.sir.core.negocio.modelo.constantes.CFolio.NUMERO_MATRICULA_INMOBILIARIA%>" type="text" class="camposformtext"></td>
                </tr>
              </table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </form>              
              
    </td>
  </tr>              
    </table>