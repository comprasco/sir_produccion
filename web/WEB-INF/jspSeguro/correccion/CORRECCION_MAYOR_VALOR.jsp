<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccion" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.NotasInformativasHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper();
TextAreaHelper helper = new TextAreaHelper();
NotasInformativasHelper notahelper = new NotasInformativasHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function validarCaracter(evt) {
	if (!evt) {
		// grab IE event object
		evt = window.event
	} else if (!evt.keyCode) {
		// grab NN4 event info
		evt.keyCode = evt.which
	}
	
	// Si es backspace o suprimir, ac�ptelo
	if(evt.keyCode == 8 || evt.keyCode == 46 || evt.keyCode == 9)
		return true;
	
	// Los n�meros del pad num�rico tienen
	// c�digos distintos a los normales
	if(evt.keyCode >= 95 && evt.keyCode <= 105)
		return true;
	
	// Obtenga el caracter que representa al keyCode
	numString = String.fromCharCode(evt.keyCode);
	num = parseInt(numString);
	
	// Si no es un n�mero, se retorna false, de lo contario,
	// se retorna true.
	return !isNaN(num);
}

function reescribirValor(valor,id){
	
	/* JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Redondeo del valor
         */
        var round = valor / 100;
        round = Math.round(round);
        round = round * 100;
        var my_str = new String(round); 
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcci�n Mayor Valor
            </td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">
					Registrar mayor valor
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
                
                

        <form action="correccion.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%=  gov.sir.core.web.acciones.administracion.AWConsultaFolio.CONSULTAR_FOLIO %>">

                <%--
                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Valor a pagar</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    <% 
                    	try {
							textHelper.setNombre(AWCorreccion.PAGO_MAYOR_VALOR);
	                  	  	textHelper.setCssClase("camposformtext");
	                  	  	textHelper.setId(CFolio.ID_MATRICULA);
						  	textHelper.render(request,out);
						} catch(HelperException re) {
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td>
                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                    <td> &nbsp; </td>
                  </tr>
                </table>
                --%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Valor por Derechos</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    	<% 
                    		try {
 		              			textHelper.setNombre(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS);
								textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\" onKeyDown=\"return validarCaracter(event);\"");
			                  	textHelper.setCssClase("camposformtext");
			                  	textHelper.setId(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS);
								textHelper.render(request,out);
							} catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    </td>
                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                    <td> &nbsp; </td>
                  </tr>
				  <tr>
                    <td> &nbsp; </td>
                    <td width="15%">Valor por Certificados</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    	<% 
                    		try {
 		              			textHelper.setNombre(AWCorreccion.PAGO_MAYOR_VALOR_CERTIFICADOS);
		 		              	textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\" onKeyDown=\"return validarCaracter(event);\"");
			                  	textHelper.setCssClase("camposformtext");
			                  	textHelper.setId(AWCorreccion.PAGO_MAYOR_VALOR_CERTIFICADOS);
								textHelper.render(request,out);
							} catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    </td>
                    <td width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="20" height="15"></td>
                    <td> &nbsp; </td>
                  </tr>
                  <tr>
                    <td> &nbsp; </td>
                    <td width="15%">Valor impuestos</td>
                    <td width="1%">$</td>
                    <td width="15%">
                    	<% 
                    		try {
 		              			textHelper.setNombre(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS);
								textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\" onKeyDown=\"return validarCaracter(event);\"");
			                  	textHelper.setCssClase("camposformtext");
			                  	textHelper.setId(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS);
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
                    <td width="15%">Raz&oacute;n del pago</td>
                    <td>
                    <% 
					try{
						helper.setCols("100");
						helper.setReadOnly(false);
						helper.setCssClase("camposformtext");
						helper.setId(AWCorreccion.RAZON_MAYOR_VALOR);
						helper.setNombre(AWCorreccion.RAZON_MAYOR_VALOR);
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
                    	<a href="javascript: cambiarAccion('<%=  AWCorreccion.PAGO_MAYOR_VALOR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a>
                    </td>
                    <td>
                    	<a href="correccion.confrontacion.view"><img  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" style=""></a>
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
        
       <%
		try{
 		    //SE USA LA SIGUIENTE L�NEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CU�L SE LE DESEA GUARDAR LA INFORMACI�N QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOC� SE PERDER� LA INFORMACI�N. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    notahelper.setNombreFormulario("BUSCAR");
			notahelper.render(request,out);
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

