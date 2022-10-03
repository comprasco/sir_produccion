<%@page import = "org.auriga.core.web.*,gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.web.helpers.comun.*"%>
<%@page import = "java.util.*"%>
<%@page import = "gov.sir.core.web.WebKeys"%>
<%@page import = "gov.sir.core.web.acciones.registro.AWDigitacionMasiva"%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function aceptarDigitacion(text) {
	document.CORRECCION.action = 'digitacionMasiva.do';	
	document.CORRECCION.ACCION.value = text;
	document.CORRECCION.submit();	
}
function cambiarSeleccion() {
    if(document.CORRECCION.seleccionado.checked == true){
	    setAllCheckBoxes('CORRECCION', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.CORRECCION.seleccionado.checked == false){
    	setAllCheckBoxes('CORRECCION', 'ELIMINAR_CHECKBOX', false);
    }
}
function salirConstruccion(text) {
	document.CORRECCION.action = 'digitacionMasiva.do';	
	document.CORRECCION.ACCION.value = '<%=AWDigitacionMasiva.SALIR_CONSTRUCCION_COMPLEMENTACION%>';
	document.CORRECCION.submit();	
}
</script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">DIGITACIÓN MASIVA - CONSTRUIR COMPLEMENTACIÓN - SELECCIONAR FOLIOS</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  
  <form action="digitacionMasiva.do" method="post" name="CORRECCION" id="CORRECCION">
  <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">            
  <input type="hidden" name="<%=WebKeys.PARAMETRO%>" id="<%=WebKeys.PARAMETRO%>">              
  

<%
	//SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
    Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
    if(turno==null){
    	turno = new Turno();
    }
    
	Solicitud solicitud = (Solicitud) turno.getSolicitud();	
	List solFolios = solicitud.getSolicitudFolios();
	List idMatriculas = new ArrayList();


	Iterator is = solFolios.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();

		Folio folioTemp = sf.getFolio();
		String labelMatricula= folioTemp.getIdMatricula();
		String idMatricula= folioTemp.getIdMatricula();

		gov.sir.core.web.helpers.comun.ElementoLista elista = new gov.sir.core.web.helpers.comun.ElementoLista();
		elista.setId(idMatricula);
		elista.setValor(labelMatricula!=null?labelMatricula:"");
		
		idMatriculas.add(elista);		

	}
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idMatriculas);	
%>


 <!--ELEGIR FOLIOS-->
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Construir Complementaci&oacute;n </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
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
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Seleccionar folios para construir complementación</span></td>
                  <td align='right' width='20' class="bgnsub"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>&nbsp;</td>                                                      
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>              
              <table width="100%">
                <tr>
                  <td>

				    	<% try {
				    		  //HELPER TABLA MATRICULAS
							  gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
							  
		                      tablaHelper.setColCount(5);
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
		               		  tablaHelper.render(request, out);
		                    }
		                    catch(HelperException re){
		                      out.println("ERROR " + re.getMessage());
		                    }
		                 %>

                  </td>
                </tr>
              </table>  


              <hr class="linehorizontal">


            
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
  


  <!--BOTONES DE PÁGINA-->  
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
      
      
	<!--TABLA DE BOTONES DE ANOTACIONES-->					
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
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
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES DIGITACI&Oacute;N</td>
	<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	<td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
	</tr>
	</table>
	</td>
	<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	</tr>
	</table>
	</td>
	<td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
	</tr>
	<tr>
	<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
	<td valign="top" bgcolor="#79849B" class="tdtablacentral">
	<table width="100%" >
	<tr>
	<td>
	
                <tr> 

                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
				  <td width="150"><a href="javascript:aceptarDigitacion('<%=AWDigitacionMasiva.ELEGIR_FOLIOS_DIGITACION_MASIVA%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif"  alt="Aceptar" width="150" height="21" border="0"></a></td>                  
				  <td width="150"><a href="javascript:salirConstruccion();"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"  alt="Regresar" width="139" height="21" border="0"></a></td>     
				  <td>&nbsp;</td>     				  
                </tr>
	
	</td>
	</tr>
	</table>
					
	</td>
	<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>

	<tr>
	<td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
	<td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
	</tr>
	</table>      
	</form>  

 	</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>  
  
  
  

  <!--PIE DE PÁGINA-->  
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>