<%@page import="org.auriga.core.web.*,
gov.sir.core.negocio.modelo.Folio,
gov.sir.core.web.helpers.registro.AnotacionesEnglobeHelper,
gov.sir.core.web.helpers.registro.MatriculasEnglobeHelper,
gov.sir.core.negocio.modelo.Complementacion,
gov.sir.core.negocio.modelo.Anotacion,
gov.sir.core.web.WebKeys,
java.util.*;"%>
<% 
	/*List fol=(List)request.getSession().getAttribute("LISTA_FOLIOS_ENGLOBE");
	for (int i=0; i<fol.size(); i++){
		Folio f=(Folio)fol.get(i);
	}*/   
	AnotacionesEnglobeHelper anot=new AnotacionesEnglobeHelper();
	MatriculasEnglobeHelper mat=new MatriculasEnglobeHelper();
	Hashtable tabla=(Hashtable)request.getSession().getAttribute("TABLA_ANOTACIONES_ENGLOBE");
	if (tabla!=null){
		Enumeration enumn=tabla.keys();
		while (enumn.hasMoreElements()){
		String key=(String)enumn.nextElement();
		List l=(List)tabla.get(key);
		Iterator it=l.iterator();
		while (it.hasNext()){
			Anotacion a=(Anotacion)it.next();
		}
	}
	}

   %>
   
   <script type="text/javascript">
   function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.ENGLOBE.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
	}
	function cambiarAccion(text) {
       document.ENGLOBE.ACCION.value = text;
       document.ENGLOBE.submit();
       }
	
	 function cargarAnotaciones(text) {
       document.ENGLOBE.MATRICULA.value = text;
       cambiarAccion('CARGAR_ANOTACIONES');
       }
     
     function grabarTemp(text) {
       document.ENGLOBE.MATRICULA.value = text;
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Englobar</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Englobe</td>
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
            <form action="calificacion.do" method="post" name="ENGLOBE" id="ENGLOBE">
            <input type="hidden" name="ACCION" value="ENGLOBAR">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Folios a Englobar </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              
              <%try {
							   mat.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}
%>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Detalles del Folio </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Anotaciones</td>
                  <td>&nbsp;</td>
                </tr>
                
                <%try {
							   anot.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}
%>
                
                
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Complementaci&oacute;n</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%">
              
              
              
              <%
              				Complementacion comp=(Complementacion)request.getSession().getAttribute("COMPLEMENTACION_FOLIO_ENGLOBE");
              				Complementacion esc=(Complementacion)request.getSession().getAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
							if (comp!=null && esc==null){
							%>
				<tr> 
                  <td><table width="100%" class="camposform">
                      <tr> 
                        <td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td><textarea name="ENGLOBE_COMPLEMENTACION" readonly="readonly" cols="80" rows="10" class="campositem"><%= comp.getComplementacion()%></textarea></td>
                      </tr>
                    </table> 
                  </td>
                </tr>
                <tr>
                  <td align="right">
					<table border="0" cellpadding="0" cellspacing="2" class="camposform">
                      <tr> 
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                        <td>Asociar Complementaci&oacute;n </td>
                        <td><a href="javascript:cambiarAccion('AGREGAR_COMPLEMENTACION')"><img name="imageField2" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a> 
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr><%
							}%>
              
              </table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                <% Folio fo=(Folio)request.getSession().getAttribute("FOLIO_ENGLOBE");
                	if (fo!=null){
                	%>
                	<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150"><a href="javascript:grabarTemp('<%=fo.getIdMatricula()%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>
                  <td width="16"><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></td>
                  <td class="campostip04">Guardar Temporalmente las Selecciones del Folio Actual </td>
                	<%}else{%>
                	<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" name="Folio" width="150" height="21" border="0" id="Folio"></td>
                  <td width="16"><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></td>
                  <td class="campostip04">Guardar Temporalmente las Selecciones del Folio Actual </td>
                	<%}
                %>
                  
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td><a href="javascript:cambiarAccion('ELIMINAR_COMPLEMENTACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ico_flecha.gif" width="16" height="16"></td>
                  <td class="campostip04">Eliminar la Complementaci&oacute;n Asociada </td>
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
      
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
              <td width="140"><a href="javascript:cambiarAccion('TERMINAR_ANOTACIONES_ENGLOBE')"><img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
              <td><a href="registro.englobar.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
            </tr>
          </table></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
        <%
        //Helper de Notas Informativas
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
		    //SE USA LA SIGUIENTE L?NEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CU?L SE LE DESEA GUARDAR LA INFORMACI?N QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOC? SE PERDER? LA INFORMACI?N. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("ENGLOBE");						
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
      
      
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