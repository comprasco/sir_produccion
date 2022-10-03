<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.Minuta"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.OtorganteNatural"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaEntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaAccionNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.AccionNotarial"%>
<%@page import="java.text.DecimalFormat"%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%


	DecimalFormat df = new DecimalFormat("################.00");
	//Obtener la minuta que se quiere mostrar
    Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
	//Helpers
    TextHelper textHelper = new TextHelper();    
	textHelper.setReadonly(true);
    TextAreaHelper textAreaHelper = new TextAreaHelper();        
	//Codigo para mostrar la info de la minuta	
    String tipo = new String(); 
    String categoria = new String();
	if(minuta != null){	
		if(minuta.isNormal()){
			tipo = CMinuta.ORDINARIO;
		}else{
			tipo = CMinuta.EXTRAORDINARIO;
		}
		/*if(minuta.getAccionNotarial() != null){
			String accionNotarial = minuta.getAccionNotarial().getNombre();
			session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL,accionNotarial);
		} else {
			session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL," ");
		}*/
		if(minuta.getCategoria() != null){
			categoria = minuta.getCategoria().getNombre();
			session.setAttribute(CMinuta.CATEGORIA_NOM,categoria);
		} else {
			session.setAttribute(CMinuta.CATEGORIA_NOM," ");		
		}
		String numFolios = String.valueOf(minuta.getNumeroFolios());
		session.setAttribute(CMinuta.NRO_FOLIOS_MINUTA,numFolios);
		if(minuta.getCirculoNotarial() != null){
			String circuloNotarial = minuta.getCirculoNotarial().getNombre();
			session.setAttribute(CMinuta.CIRCULO_NOTARIAL_ID,circuloNotarial);			
		} else {
			session.setAttribute(CMinuta.CIRCULO_NOTARIAL_ID," ");
		}
		String cuantia = df.format(minuta.getValor());
		session.setAttribute(CMinuta.CUANTIA,cuantia);
		String unidades = String.valueOf(minuta.getUnidades());
		session.setAttribute(CMinuta.UNIDADES,unidades);
		String obsReparto = minuta.getComentario();
		session.setAttribute(CMinuta.OBSERVACIONES_REPARTO,obsReparto);	
	}
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.REPARTONOTARIAL.<%=WebKeys.ACCION%>.value = text;
	document.REPARTONOTARIAL.submit();	
}
</script>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
<script language='javascript'>
	function cargarApplet(){
        var app =  getCookie("appletImpresionCargado");
        if (app == null){
       		var x = eval (window.screen.availWidth - 310);
			var y = eval (window.screen.availHeight - 450);
			var w = window.open('<%= request.getContextPath()%>/impresion.view','applet_impresion','width=300,height=400,resizable=no,scrollbars=no,location=no,status=yes,menubar=no,copyhistory=no,left='+x+',top='+y);
			//w.resizeTo(300,150);
			this.window.focus();
            setCookie("appletImpresionCargado",true);
        }
}
</script>

<body onload='cargarApplet()'>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Datos básicos de la minuta</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"> Minuta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
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
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
            <form action="consultasReparto.do" method="post" name="REPARTONOTARIAL" id="REPARTONOTARIAL">        

				<input name="id_depto" type="hidden" id="id_depto" value="">
				<input name="nom_depto" type="hidden" id="nom_depto" value="">
				<input name="id_munic" type="hidden" id="id_munic" value="">
				<input name="nom_munic" type="hidden" id="nom_munic" value="">
				<input name="id_vereda" type="hidden" id="id_vereda" value="">
				<input name="nom_vereda" type="hidden" id="nom_vereda" value="">            
				<input name="mostrar_vereda" type="hidden" id="mostrar_vereda" value="">        				
				
              <br>
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos 
                    Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align='left'><strong>Tipo de radicación: <%=tipo%></strong></td>
                  <td align='left'><strong>Categor&iacute;a: <%=categoria%></strong></td>                  
                  <td align='left'>
                  </td>
                </tr>
              </table>              

              <br>
              <hr class="linehorizontal">
              <br>              
                  
        
				<!--Aca se válida el ingreso de departamento, municipio y ciudad para llenar la Vereda de origen. Encabezado-->
				<table width="100%" class="camposform">
				<tr>
				<td align="left" width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="15%" align='right'>No folios.</td>
                  <td width="35%">
                    <%
					try {
						textHelper.setNombre(CMinuta.NRO_FOLIOS_MINUTA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CMinuta.NRO_FOLIOS_MINUTA);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>                  
                </td>
				<td align="left" width="15%">Circulo Notarial: </td>
                <td align="left">
                <%
				try {
					textHelper.setNombre(CMinuta.CIRCULO_NOTARIAL_NOM);
					textHelper.setCssClase("camposformtext");
					textHelper.setId(CMinuta.CIRCULO_NOTARIAL_NOM);
					textHelper.render(request,out);
				}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}                      
				%>                       
                </td>
                </tr> 
				</table>

			
				<!--Tabla de unidades-->
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Cuant&iacute;a</td>
                  <td  width="35%" align='left'>
                    <%
					try {
						textHelper.setNombre(CMinuta.CUANTIA);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(CMinuta.CUANTIA);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>  
                  </td>
                  
                  <td width="15%" align='right'>Unidades</td>
                  <td width="35%">
                    <%
						try {
							textHelper.setNombre(CMinuta.UNIDADES);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CMinuta.UNIDADES);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}                      
					%>                  
                  </td>                  

                </tr>
                
                
                
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Observaciones</td>
                  <td  width="85%" align='left' colspan='3'>
                        <%
							try {
								textAreaHelper.setNombre(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setId(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setCols("95");							
								textAreaHelper.setReadOnly(true);
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("camposformtext");
								textAreaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>

                </tr>                
                
                
                
              </table>



			  <!--ACCIONES NOTARIALES.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Acci&oacute;n notarial relacionada</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

				<%
			  List actosCargados = (List)session.getAttribute(WebKeys.LISTA_ACTOS_MINUTA);
				long unidadesTotal = 0;
				double valortotal = 0;
				if(actosCargados!=null && actosCargados.size()>0){
				%>
					<table width="100%" class="camposform">
					<tr>
								<td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
								<td class="titulotbcentral"> # </td>
								<td class="titulotbcentral"> Tipo Accion Notarial </td>
								<td class="titulotbcentral"> Cuant&iacute;a </td>
								<td class="titulotbcentral"> Unidades </td>
								<td class="titulotbcentral"> Con Cuant&iacute;a </td>
							</tr>
				<%
					int a = 0;
					Iterator itActos = actosCargados.iterator();				
					while(itActos.hasNext()){
						//AccionNotarial accionNotarialLista = (AccionNotarial) itActos.next();
						MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) itActos.next();
						AccionNotarial accionNotarialLista = actoMinutaTmp.getAccionNotarial();
						unidadesTotal = unidadesTotal + actoMinutaTmp.getUnidades();
						valortotal = valortotal + actoMinutaTmp.getValor();
						String valor = String.valueOf(actoMinutaTmp.getValor());
						String unidades = String.valueOf(actoMinutaTmp.getUnidades());
						String conCuantia = String.valueOf(actoMinutaTmp.getConCuantia());
						String labelConCuantia = "SI";
						if (!conCuantia.equals("1")) {
							labelConCuantia = "NO";
						}
				%>		
					<tr>
						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
						<td class="campositem"><%=(a+1)%></td>
						<td class="campositem"><%=(accionNotarialLista!=null && accionNotarialLista.getNombre()!=null ? accionNotarialLista.getNombre(): "&nbsp;")%></td>
						<td class="campositem"><%=(valor!=null && valor!=null ? valor: "&nbsp;")%></td>
						<td class="campositem"><%=(unidades!=null && unidades!=null ? unidades: "&nbsp;")%></td>
						<td class="campositem"><%=labelConCuantia%></td>			
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				%>


              <!--ENTIDADES PUBLICAS.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Entidad p&uacute;blica relacionada</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List entidadesCargadas = (List)session.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
				
				if(entidadesCargadas!=null && entidadesCargadas.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itEntidades = entidadesCargadas.iterator();				
					while(itEntidades.hasNext()){
						MinutaEntidadPublica entidadPublica = (MinutaEntidadPublica) itEntidades.next();
						boolean entidadexento = entidadPublica.isExento();
						String tentidadexento = "&nbsp;";
						if(entidadexento){
							tentidadexento = "EXENTO";
						}
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(entidadPublica!=null && entidadPublica.getEntidadPublica().getNombre()!=null ? entidadPublica.getEntidadPublica().getNombre(): "&nbsp;")%></td>
						<td width="50" align='left'><%=tentidadexento%></td>
						</tr>
				<% }
				%>
					</table>
				<%
				} 
				%>


              


     		  <!--OTORGANTES-->
              <br>
              <br>
        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Persona natural o jurídica</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List otorgantes = (List)session.getAttribute(WebKeys.LISTA_OTORGANTES);
				
				if(otorgantes!=null && otorgantes.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itOtorgantes = otorgantes.iterator();				
					while(itOtorgantes.hasNext()){
						OtorganteNatural otorgante = (OtorganteNatural) itOtorgantes.next();
						boolean otorganteexento = otorgante.isExento();
						String totorganteexento = "&nbsp;";
						if(otorganteexento){
							totorganteexento = "EXENTO";
						}
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(otorgante!=null && otorgante.getNombre()!=null ? otorgante.getNombre(): "&nbsp;")%></td>
						<td width="50" align='left'><%=totorganteexento%></td>
						</tr>
				<%}
				%>
					</table>
				<%
				} 
				%>

			  <br>	
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td> 
					<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.administracion.AWConsultasReparto.TERMINA_EDICION%>">
					<input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" width="139" height="21" border="0">
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