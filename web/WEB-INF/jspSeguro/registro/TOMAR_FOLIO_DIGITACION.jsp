<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*,java.util.*,gov.sir.core.negocio.modelo.*,gov.sir.core.web.WebKeys"%>
<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	

	if(solicitud==null){
		solicitud = new Solicitud();
	}
	
	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);		
%>
<SCRIPT>
function cambiarAccion(text) {
    document.CORRECCION.ACCION.value = text;
    document.CORRECCION.submit();
}
</SCRIPT>
		
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<BR>
<BR>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Tomar folios para la digitación</td>
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
            <form action="digitacionMasiva.do" method="post" name="CORRECCION" id="CORRECCION">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="TOMAR_FOLIO">		                
              <br>            
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;culas Asociadas a la digitación </td>
                  </tr>
              </table>
              <br>
                  <% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
               		  tablaHelper.render(request, out);
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  
				  <td><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.TOMAR_FOLIO_DIGITACION%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_tomar_folios.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>                  
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 
</table>