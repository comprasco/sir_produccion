<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>



<%			
   Boolean readOnly =  (Boolean)session.getAttribute(WebKeys.TURNO_ANTERIOR_OK);
   String style = "camposformtext";   
   boolean read = false;
   if(readOnly!=null && readOnly.booleanValue()){
   	   read = true;
	   style = "campositem";   	   
   }		

			
	TextHelper textHelper = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");

            %>
<script>
function showhide(){
	var valor;
	
	valor = document.getElementById("uno").checked;
	if (valor){
		document.getElementById("Nacional1").style.display = "block";
		document.getElementById("Nacional2").style.display = "block";
		document.getElementById("Internacional").style.display = "none";
	}else{
		document.getElementById("Nacional1").style.display = "none";
		document.getElementById("Nacional2").style.display = "none";
		document.getElementById("Internacional").style.display = "block";
	}
}
function setNacional(){
	document.getElementById("uno").checked=true;
}
</script>
<%//Cargar valores que ha escrito previamente el usuario

            String tipoOficina = (String) session
                    .getAttribute(WebKeys.TIPO_OFICINA_I_N);
            String checkedNacional = "";
            String checkedInterNacional = "";

            tipoOficina = tipoOficina == null || tipoOficina.length() == 0 ? WebKeys.TIPO_OFICINA_NACIONAL
                    : tipoOficina;

            checkedNacional = tipoOficina
                    .equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL) ? "checked"
                    : "";
            checkedInterNacional = tipoOficina
                    .equalsIgnoreCase(WebKeys.TIPO_OFICINA_INTERNACIONAL) ? "checked"
                    : "";

            %>

<tr>
	<td>&nbsp;</td>
	<td><!-- aqui -->
	<table width="100%" border="0" class="camposform">
		<tr>
			<td>
			<input name='<%=WebKeys.TIPO_OFICINA_I_N%>' id="uno" type="radio" 
				value='<%=WebKeys.TIPO_OFICINA_NACIONAL%>' <%=checkedNacional%>
				onClick="<%=(read?"setNacional();":"")%>showhide();"> Nacional <input
				name='<%=WebKeys.TIPO_OFICINA_I_N%>' id="dos" type="radio"  
				value='<%=WebKeys.TIPO_OFICINA_INTERNACIONAL%>'
				<%=checkedInterNacional%> onClick="<%=(read?"setNacional();":"")%>showhide();"> Internacional</td>
		</tr>
	</table>
	<table width="100%" id="Nacional1" class="camposform"
		style="display:block">
		<tr>
			<td width="17%" align="right">Departamento</td>
			<td width="28%"><%
			textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setSize("5");
            textHelper.setCssClase(style);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO');");
            if(read){
	            textHelper.setReadonly(read);
            }else{
	            textHelper.setReadonly(false);            
            }
            textHelper.render(request, out);

            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.setSize("25");
            textHelper.setCssClase(style);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
            %></td>
			<td width="15%" align="right">Municipio</td>
			<td width="30%"><%textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.setSize("6");
            textHelper.setCssClase(style);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
            if(read){
	            textHelper.setReadonly(read);
            }else{
	            textHelper.setReadonly(false);            
            }
            textHelper.render(request, out);
            
            textHelper.setSize("25");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.setCssClase(style);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
            %></td>
			<td width="10%"><%if(!read){%><a
				href="javascript:locacion('seleccionar.locacion.do?LOCACIONES_CIRCULO=','ANOTACION_OFICINA_DOCUMENTO','width=790,height=175,menubar=no');"><img
				src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif"
				width="16"
				height="21" border="0"></a><%}%>&nbsp;</td>
		</tr>
	</table>
	
	<%		hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
			hiddenHelper.setCssClase(style);
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            hiddenHelper.render(request, out);
            
            
            hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
			hiddenHelper.setCssClase(style);
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            hiddenHelper.render(request, out);
            %>
	<table width="100%" id="Nacional2" class="camposform"
		style="display:block">
		<tr>
			<td width="17%" align="right">Tipo de Oficina Origen</td>
			<td width="28%"><%			
			hiddenHelper.setCssClase(style);
            hiddenHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
            hiddenHelper.render(request, out);

            textHelper.setCssClase(style);
            textHelper.setSize("30");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            textHelper.setReadonly(true);
            textHelper.render(request, out);

            %></td>
			<td width="15%" align="right">N&uacute;mero</td>
			<td width="30%"><%textHelper.setCssClase(style);
            textHelper.setSize("6");
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            textHelper.setFuncion("");
            if(read){
	            textHelper.setReadonly(read);
            }else{
	            textHelper.setReadonly(false);            
            }
            textHelper.render(request, out);
			
            textHelper.setSize("25");
            textHelper.setCssClase(style);
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            TextHelper versionHelper = new TextHelper();
            versionHelper.setTipo("hidden");
            versionHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.render(request, out);
            %></td>
			<td width="10%"><%if(!read){%><a
				href="javascript:oficinas('seleccionar.oficina.do','ANOTACION_OFICINA_DOCUMENTO','width=800,height=175,menubar=no')" />
			<image src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif"
				 width="16"
				height="21" border="0"></a><%}%>&nbsp;</td>
			<%hiddenHelper.setNombre("tipo_nom_oficina");
			hiddenHelper.setCssClase(style);            
            hiddenHelper.setId("tipo_nom_oficina");
            hiddenHelper.render(request, out);

            hiddenHelper.setNombre("tipo_oficina");
			hiddenHelper.setCssClase(style);
            hiddenHelper.setId("tipo_oficina");
            hiddenHelper.render(request, out);
            hiddenHelper.setNombre(CSolicitudRegistro.NUMERO_OFICINA);
			hiddenHelper.setCssClase(style);
            hiddenHelper.setId(CSolicitudRegistro.NUMERO_OFICINA);
            hiddenHelper.render(request, out);

            hiddenHelper.setNombre(WebKeys.ID_OFICINA);
			hiddenHelper.setCssClase(style);
            hiddenHelper.setId(WebKeys.ID_OFICINA);
            hiddenHelper.render(request, out);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            hiddenHelper.setNombre(WebKeys.VERSION);
            hiddenHelper.setId(WebKeys.VERSION);
            hiddenHelper.drawGUI(request, out);
            %>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" id="Internacional"
		style="display:none" class="camposform">
		<tr>
			<td width="230">Oficina ubicaci&oacute;n Internacional</td>
			<td><%textHelper.setSize("35");
            textHelper.setCssClase(style);
            textHelper.setNombre(WebKeys.TIPO_OFICINA_INTERNACIONAL);
            textHelper.setId(WebKeys.TIPO_OFICINA_INTERNACIONAL);
            if(read){
	            textHelper.setReadonly(read);
            }else{
	            textHelper.setReadonly(false);            
            }
            textHelper.render(request, out);
        %></td>
		</tr>
	</table>
	<!-- hasta aqui --></td>
</tr>


<SCRIPT>showhide();</SCRIPT>
