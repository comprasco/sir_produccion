<%@page import="org.auriga.core.web.*, java.util.List,
gov.sir.core.web.helpers.registro.SegregacionEscogerAnotacionHelper, gov.sir.core.web.WebKeys"%>
<% 
	session.removeAttribute(gov.sir.core.web.WebKeys.LISTA_FOLIOS_DERIVADOS);
	SegregacionEscogerAnotacionHelper anot=new SegregacionEscogerAnotacionHelper();
	
	List anotacionesTemporales = (List)session.getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
	session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,anotacionesTemporales);
%>
   
   <script type="text/javascript">
function verAnotacion(nombre,valor,dimensiones,pos)
{
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}

function cambiarAccion(text) {
       document.SEGREGAR.ACCION.value = text;
       document.SEGREGAR.submit();
}
	
function cargarAnotaciones(text) {
       document.SEGREGAR.MATRICULA.value = text;
       cambiarAccion('CARGAR_ANOTACIONES');
}
     
function grabarTemp(text) {
       document.SEGREGAR.MATRICULA.value = text;
       cambiarAccion('GRABACION_TEMPORAL_ANOTACION');
}
   </script>
   
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Escoger Anotación - Segregación</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Anotaciones temporales</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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
            <form action="segregacion.do" method="post" name="SEGREGAR" id="SEGREGAR">
            <input type="hidden" name="ACCION" value="">
              <br>
              
              <br>
              
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
          <tr>
            <td width="15%">&nbsp;</td>
             <td class="titresaltados" width="30%" align="center">&nbsp;Recuerde que debe seleccionar alguna anotación que este en temporal, sino debe regresar al paso anterior e ingresar una nueva.&nbsp;</td>             
            <td width="15%">&nbsp;</td>              
            </tr>
    </table>                 
              
            <br><br>  
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Anotaciones temporales del Folio </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%" class="camposform">
                <%try {
							   anot.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}
%>
                
                
              </table>

              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <%
                  if(anotacionesTemporales!=null && !anotacionesTemporales.isEmpty()){
                  %>
	                  <td width="150"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWSegregacion.SEGREGACION_ESCOGER_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" name="Folio" width="139" height="21" border="0" id="Folio"></td>
	                  <td width="150"><a href="registro.segregar.anotacion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></td>                  	                  
                  <%
                  }else{
                  %>
	                  <td width="150"><a href="registro.segregar.anotacion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></td>                  
                  <%
                  }
                  %>
	              <!--ELIMINAR ENGLOBE EN CURSO-->
	              <td width="150">
	                <a href="javascript:eliminarEnglobe();">
	                 <img alt="Eliminar englobe en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_englobe.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
	                </a>
              	  </td>                                 
                  <td width="150"><a href="calificar.folio.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>
    			  <td>&nbsp;</td>
    			  <td>&nbsp;</td>
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