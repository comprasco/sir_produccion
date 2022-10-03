<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
	TextHelper textHelper = new TextHelper();
	TextAreaHelper helper = new TextAreaHelper();

	Boolean confirmacion = (Boolean)session.getAttribute(WebKeys.CONFIRMACION_PAGO_EXCESO);
	boolean mostrarConfirmacion = false;
	if(confirmacion!=null && confirmacion.booleanValue()){
		mostrarConfirmacion = true;
	}
	session.removeAttribute(WebKeys.CONFIRMACION_PAGO_EXCESO);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js"></script>-->
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}
function reescribirValor(valor,id){
	var my_str = valor;
	var miles=1;
	if(my_str.indexOf(".")==-1){
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=my_str.length;i++){
				var desde = my_str.length-i*3;
				var hasta = my_str.length-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr=nStr+".00";
		document.getElementById(id).value = nStr;		
		}
	} else {
		var largo = my_str.indexOf(".");
		var centavos = my_str.substr(largo,my_str.length);
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=largo;i++){
				var desde = largo-i*3;
				var hasta = largo-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr = nStr+centavos;
		document.getElementById(id).value = nStr;		
		}	
	}
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registro
            de documentos
            </td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
          Pago en exceso
            </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  
  
  
  
  				<!--OPCION PARA LA CONFIRMACIÓN-->
  				<%
  					if(mostrarConfirmacion){
  				%>

				  <tr> 
				    <td>&nbsp;</td>
				    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
				    <td class="tdtablaanexa02">
				    
				        <table border="0" cellpadding="0" cellspacing="0" width="100%">

				          <tr>
				            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
				            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
				            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
				          </tr>
				          
				          
				          
				    <tr>
				    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
				    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				    <table border="0" cellpadding="0" cellspacing="0">
				        <tr>
				        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Confirmaci&oacute;n</td>
				        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
				        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
				        <table width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr>
				                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
					    <table width="100%" class="camposform">
					        
					       <tr>
				                <td width="20">&nbsp;</td>
				                <td class="titulotbcentral">SE HA REGISTRADO EL PAGO EN EXCESO </td>
						         <td>
						         <image src="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif" />
						         <!--<script language="javascript" type="text/javascript">
								  var Imagen="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif"
								  var pelicula="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.swf"
								  var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
								  var ancho="70"
								  var alto="70"
								  //plugindetectado();
								</script>-->
								</td>
				           </tr>
					   		
					      <tr>
					        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>					        
					        <td>
				            	<a href="calificacion.revision.turno.view"><img  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" style=""></a>					        
					        <td>				
				            <td>
								&nbsp;
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

      			  <tr>
		            <td colspan='3'>&nbsp;</td>
		          </tr> 
          		
          			<!--TERMINA OPCION PARA LA CONFIRMACIÓN-->
          						         

          			<%
          			}else{
          			%>

					<!--Renglon Vacio-->				
				    <tr> 
				    <td>&nbsp;</td>
				    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
				    <td class="tdtablaanexa02">
						&nbsp;
				    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
				    <td>&nbsp;</td>
				    </tr>    			

          
				  <tr> 
				    <td>&nbsp;</td>
				    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
				    <td class="tdtablaanexa02">
				    
				        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          
          
			          <tr>
			            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
			            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
			                <tr>
			                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">
			                  Registrar pago en exceso
			                    </td>
			                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
			                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			                      <tr>
			                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
			                      </tr>
			                  </table></td>
			                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
			                </tr>
			            </table></td>
			            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
			          </tr>
			          
			          
			          <tr>
			            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
			            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
			                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			                  <tr>
			                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
			                    <td class="bgnsub">Datos del pago</td>
			                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
			                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
			                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			                  </tr>
			                </table>
			                
			                
			
			        <form action="calificacion.do" method="POST" name="BUSCAR" id="BUSCAR">
			        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  gov.sir.core.web.acciones.administracion.AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION %>">
			
			
			        
			        
			        
			                <%--<table width="100%" class="camposform">
			                  <tr>
			                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                    <td width="15%">Valor a devolver</td>
			                    <td width="1%">$</td>
			                    <td width="15%">
			                    <% 
			                    	try {
			 		              		textHelper.setNombre(AWCalificacion.PAGO_EXCESO);
			 		              		textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
			                  	  		textHelper.setCssClase("camposformtext");
			                  	  		textHelper.setId(AWCalificacion.PAGO_EXCESO);
								  		textHelper.render(request,out);
									} catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
								%>
			                    </td>
			                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
			                    <td> &nbsp; </td>
			                  </tr>
			                </table>--%>
			                <table width="100%" class="camposform">
			                  <tr>
			                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                    <td width="15%">Valor a devolver por derechos</td>
			                    <td width="1%">$</td>
			                    <td width="15%">
			                    <% 
			                    	try {
			 		              		textHelper.setNombre(AWCalificacion.PAGO_EXCESO_DERECHOS);
			 		              		textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
			                  	  		textHelper.setCssClase("camposformtext");
			                  	  		textHelper.setId(AWCalificacion.PAGO_EXCESO_DERECHOS);
								  		textHelper.render(request,out);
									} catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
								%>
			                    </td>
			                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
			                    <td> &nbsp; </td>
			                  </tr>
			                </table>
			                <table width="100%" class="camposform">
			                  <tr>
			                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                    <td width="15%">Valor a devolver por impuestos</td>
			                    <td width="1%">$</td>
			                    <td width="15%">
			                    <% 
			                    	try {
			 		              		textHelper.setNombre(AWCalificacion.PAGO_EXCESO_IMPUESTOS);
			 		              		textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
			                  	  		textHelper.setCssClase("camposformtext");
			                  	  		textHelper.setId(AWCalificacion.PAGO_EXCESO_IMPUESTOS);
								  		textHelper.render(request,out);
									} catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
								%>
			                    </td>
			                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
			                    <td> &nbsp; </td>
			                  </tr>
			                </table>
			                <table width="100%" class="camposform">
			                  <tr>
			                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                    <td width="15%">Razón de la devolución</td>
			                    <td>
			                    <% 
								try{
									helper.setCols("100");
									helper.setReadOnly(false);
									helper.setCssClase("camposformtext");
									helper.setId(AWCalificacion.RAZON_DEVOLUCION);
									helper.setNombre(AWCalificacion.RAZON_DEVOLUCION);
									helper.setRows("10");
									helper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}	
								%>
			                    </td>
			                  </tr>
			                </table>
			                <table width="100%" class="camposform">
			                  <tr>
			                    <td width="20">
			                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
			                    	</td>
			                    <td width="155">
			                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWCalificacion.REGISTRAR_PAGO_EXCESO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0">
			                    	</td>
			                    <td>
			                    <a href="calificacion.revision.turno.view"><img  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" style=""></a>
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
        
            <%}%>        
		</table>
		
        <%
        if(!mostrarConfirmacion){
	        //Helper de Notas Informativas        
			try{
				gov.sir.core.web.helpers.comun.NotasInformativasHelper helperNI = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
				helperNI.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}	
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

