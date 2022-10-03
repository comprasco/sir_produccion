<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.GrupoNaturalezaJuridicaHelper"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->


        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Gravamenes</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
		
			<%
			GrupoNaturalezaJuridicaHelper helper = new GrupoNaturalezaJuridicaHelper(WebKeys.LISTA_ANOTACIONES_GRAVAMEN_PREVIEW,"Gravamen");

			try {
				helper.render(request,out);
			}catch(org.auriga.core.web.HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			%>
	
          </td>
        </tr>
        
        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Medidas Cautelares</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          
          
			<%
			GrupoNaturalezaJuridicaHelper helpermc = new GrupoNaturalezaJuridicaHelper(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES_PREVIEW,"Medidas Cautelares");

			try {
				helpermc.render(request,out);
			}catch(org.auriga.core.web.HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			%>

      
          </td>
        </tr>        

      </table>