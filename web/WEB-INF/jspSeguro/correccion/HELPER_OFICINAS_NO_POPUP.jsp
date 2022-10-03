<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import ="gov.sir.core.web.helpers.comun.*,org.auriga.core.web.*" %>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import ="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import ="gov.sir.core.web.acciones.comun.AWLocacion" %>
<%@page import ="gov.sir.core.negocio.modelo.OficinaOrigen" %>
<%@page import ="gov.sir.core.negocio.modelo.Vereda" %>
<%@page import ="gov.sir.core.negocio.modelo.Municipio" %>
<%@page import ="gov.sir.core.negocio.modelo.Departamento" %>

<%
   SeleccionarLocacionHelper helper = new SeleccionarLocacionHelper();
   SeleccionarOficinaHelper helperOficina = new SeleccionarOficinaHelper();
   String mostrarVeredas = (String)request.getSession().getAttribute(CVereda.MOSTRAR_VEREDA);
   boolean soloLectura = false;
   
   Boolean readOnly =  (Boolean)session.getAttribute(WebKeys.TURNO_ANTERIOR_OK);
   String style = "camposformtext";   
   boolean read = false;
   if(readOnly!=null && readOnly.booleanValue()){
   	   read = true;
	   style = "campositem";   	   
   }	
   
   //Si ya hay seleccionado un departamento y un municipio, no va a tomar el circulo por defecto
   if(request.getSession().getAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO)==null &&
   		request.getSession().getAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC)==null)
   //Si hay oficina origen por defecto, se toma el municipio y el departamento y llenan	los campos correspondientes
   if(request.getSession().getAttribute(WebKeys.CIRCULO)!=null){
   		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
   		if(circulo.getOficinaOrigen()!=null){
   			OficinaOrigen oficinaOrigen = circulo.getOficinaOrigen();
   			if(oficinaOrigen.getVereda()!=null){
   				Vereda vereda = oficinaOrigen.getVereda();
   				if(vereda.getMunicipio()!=null){
   					Municipio municipio = vereda.getMunicipio();
   					if(municipio.getDepartamento()!=null){
   						Departamento departamento = municipio.getDepartamento();
   						if(departamento.getIdDepartamento()!=null && departamento.getNombre()!=null){
   							request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,departamento.getIdDepartamento());
   							request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,departamento.getNombre());
   							if(municipio.getIdMunicipio()!=null && municipio.getNombre()!=null){
   								request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,municipio.getIdMunicipio());
   								request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,municipio.getNombre());
   							}
   						}
   					}
   				}
   			}
   		}
   }

    TextHelper textHelper = new TextHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
            %>

<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js"
>
</script>

<script
  type="text/javascript"
  src="<%=request.getContextPath()%>/jsp/plantillas/panels/rico.js"
>
</script>
<script src='<%=request.getContextPath()%>/dwr/interface/AWLocacion.js'></script>
<script src='<%=request.getContextPath()%>/dwr/interface/AWOficinas.js'></script>
<script src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script>
var saveHeight;
var showing = true;
var showingOficina = true;
var listaDepartamentos = new Array();
var contenedorOficinas;
var cargaDesdeTexto = false;

	function showhide(){
		var valor;

		valor = document.getElementById("uno").checked;
		if (valor){
			document.getElementById("Nacional1").style.display = "block";
			document.getElementById("Nacional2").style.display = "block";
                        document.getElementById("departamentoMunicipio").style.display = "block";
                        document.getElementById("oficinaOrigen").style.display = "block";
			document.getElementById("Internacional").style.display = "none";
		}else{
			document.getElementById("Nacional1").style.display = "none";
			document.getElementById("Nacional2").style.display = "none";
                        document.getElementById("departamentoMunicipio").style.display = "none";
                        document.getElementById("oficinaOrigen").style.display = "none";
			document.getElementById("Internacional").style.display = "block";
		}
	}
	function setNacional(){
		document.getElementById("uno").checked=true;
	}

   function toggleSlide() {
      if ( showing )
         { slideMenuUp(); showing = false; }
      else
         { slideMenuDown(); showing = true; }
   }

   function slideMenuUp() {
      var menu = $('departamentoMunicipio');
      saveHeight = menu.offsetHeight;

      menu.style.overflow = "hidden";
      new Rico.Effect.Size( menu, null, 1, 120, 8 );
   }

   function slideMenuDown() {
      var menu = $('departamentoMunicipio');
      new Rico.Effect.Size( menu, null, saveHeight, 120, 8, {complete:function() { $(menu).style.overflow = "visible"; }} );
   }

   function toggleSlideOficina() {
      if ( showingOficina )
         { slideOficinaUp(); showingOficina = false; }
   }

   function toggleSlideOficina2() {
      if ( showingOficina )
         { slideOficinaUp(); showingOficina = false; }
      else
         { slideOficinaDown(); showingOficina = true; }
   }

   function slideOficinaUp() {
      var menu = $('oficinaOrigen');
      saveHeight = menu.offsetHeight;

      menu.style.overflow = "hidden";
      new Rico.Effect.Size( menu, null, 1, 120, 8 );
   }

   function slideOficinaDown() {
      var menu = $('oficinaOrigen');
      new Rico.Effect.Size( menu, null, saveHeight, 120, 8, {complete:function() { $(menu).style.overflow = "visible"; }} );
   }

   function cargarComboDepartamentos(){
   		//AJAX: Como el metodo en el servidor recibe el request, la funcion callback va de ultimas
   		AWLocacion.cargarDepartamentos('<%="SIN_CIRCULO"%>', llenarComboDepartamentos);
   }

   function llenarComboDepartamentos(data){
   	if(data!=null){
   		dwr.util.addOptions(document.getElementById("<%=helper.LOCACION_ID_DEPARTAMENTO%>"), ["-Seleccione una opcion-"]);
   		dwr.util.addOptions(document.getElementById("<%=helper.LOCACION_ID_DEPARTAMENTO%>"), data, "idDepartamento", "nombre");
   		listaDepartamentos = data;
   		verificarDepartamento();
   	}else{
   		alert("Ocurrio un error cargando los departamentos, intente de nuevo");
   	}
   }

   function cargarMunicipios(){
   		var comboDepartamentos = document.getElementById("<%=helper.LOCACION_ID_DEPARTAMENTO%>");
   		var idDepartamento = "";
   		var indice = "";
		var nombreDepartamento = "";
   		if(cargaDesdeTexto){
   			idDepartamento = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>").value;
   			nombreDepartamento = "";
   			for(var i=0;i<listaDepartamentos.length;++i){
   				var departamento = listaDepartamentos[i];
				if(idDepartamento==departamento.idDepartamento){
					nombreDepartamento=departamento.nombre;
					comboDepartamentos.value=idDepartamento;
					break;
				}
   			}
   		}else{
   			idDepartamento = comboDepartamentos.value;
   			indice = comboDepartamentos.selectedIndex;
			nombreDepartamento = comboDepartamentos.options[indice].text;
   		}
   		cargaDesdeTexto = false;
   		if(idDepartamento == "-Seleccione una opcion-")
   			alert("Debe selecionar un departamento válido");
   		else{
   			for(var i=0;i<listaDepartamentos.length;++i){
   				var departamento = listaDepartamentos[i];
   				if(departamento.idDepartamento == idDepartamento){
   					var listaMunicipios = departamento.municipios;
   					llenarComboMunicipios(listaMunicipios);
   					llenarCamposDepartamentos(idDepartamento,nombreDepartamento);
   					llenarCamposMunicipio("","");
   					llenarCamposOficinas("","");
   					llenarTipoOficina("");
   					removerOpciones("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>");
   					removerOpciones("<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA%>");
   					break;
   				}
   			}
   		}
   }

   function removerOpciones(id){
   		dwr.util.removeAllOptions(id);
   }

   function llenarCamposDepartamentos(idDepartamento,nombreDepartamento){
   		var campoIdDepartamento = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>");
   		campoIdDepartamento.value = idDepartamento;
   		var campoNombreDepartamento = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO%>");
   		campoNombreDepartamento.value = nombreDepartamento;
   }

   function llenarComboMunicipios(data){
   		dwr.util.removeAllOptions("<%=helper.LOCACION_ID_MUNICIPIO%>");
   		if(data!=null){
	   		dwr.util.addOptions(document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>"), ["-Seleccione una opcion-"]);
	   		dwr.util.addOptions(document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>"), data, "idMunicipio", "nombre");
	   	}else{
	   		alert("Ocurrio un error cargando los municipios, intente de nuevo");
	   	}
   }

   function cargarCamposMunicipios(){
   		var comboMunicipios = document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>");
   		var idMunicipio = comboMunicipios.value;
   		var nombreMunicipio = "";
   		var indice = comboMunicipios.selectedIndex;
   		if(document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value != ""
   			&& cargaDesdeTexto){
   			idMunicipio = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value;
   			cargaDesdeTexto = false;
   			for(var i=0;i<comboMunicipios.length;i++){
   				var municipio=comboMunicipios[i];
   				if(municipio.value==idMunicipio){
   					nombreMunicipio=comboMunicipios.options[i].text;
   					comboMunicipios.options.value=idMunicipio;
   					break;
   				}
   				indice=0;
   			}
   		}
   		if(indice!=0){
			nombreMunicipio = comboMunicipios.options[indice].text;
		}
   		if(idMunicipio == "-Seleccione una opcion-")
   			alert("Debe selecionar un municipio válido");
   		else{
   			llenarCamposMunicipio(idMunicipio,nombreMunicipio);
   			cargarTiposOficinas();
   			//toggleSlideOficina();
   		}
   }

   function llenarCamposMunicipio(idMunicipio,nombreMunicipio){
   		var campoIdMunicipio = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>");
   		campoIdMunicipio.value = idMunicipio;
   		var campoNombreMunicipio = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC%>");
   		campoNombreMunicipio.value = nombreMunicipio;
   }

   function cargarTiposOficinas(){
   		var idMunicipio;
   		if(document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value != "")
   			idMunicipio = document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value;
   		else if(document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>").value != "")
   			idMunicipio = document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>").value;

   		AWOficinas.cargarTiposOficinas(
   			document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>").value,
   			idMunicipio,
   			cargarComboTiposOficinas);
   }

   function cargarComboTiposOficinas(data){
   		dwr.util.removeAllOptions("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>");
   		//Si se está llamando al componente desde una pantalla que no es de liquidacion registro, no limpia
   		//los campos de las oficinas o si ya se seleccionó la oficina
   		<%if(request.getParameter("noLiquidacionRegistro")==null &&
   			request.getSession().getAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO)==null &&
   			request.getSession().getAttribute(helperOficina.OFICINA_DOCUMENTO_ID_OFICINA)==null){%>
	   		llenarCamposOficinas("","");
	   		llenarTipoOficina("");
   		<%}%>
   		if(data!=null){
   			//toggleSlideOficina();
   			dwr.util.addOptions(document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>"), ["-Seleccione una opcion-"]);
	   		dwr.util.addOptions(document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>"), data.listaTiposOficinas, "idTipoOficina", "nombre");
	   		contenedorOficinas = data;
   		}else{
   			alert("No hay tipos de oficinas origen asociadas al municipio y departamentos seleccionados");
   		}
   }

   function cargarOficinas(){
   		if(document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>").value == "")
   			alert("Debe seleccionar un departamento válido");
   		else if(document.getElementById("<%=helper.LOCACION_ID_MUNICIPIO%>").value == "" &&
   			document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value == "")
   			alert("Debe seleccionar un municipio válido");
   		else if (document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>").value=="-Seleccione una opcion-"){
   			alert("Debe seleccionar el tipo de oficina válido");
   		}else{
   			AWOficinas.cargarOficinas(contenedorOficinas,document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>").value,cargarComboOficinas);
   			var comboTiposOficinas = document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO%>");
   			var indice = comboTiposOficinas.selectedIndex;
			var nombreTipoOficina = comboTiposOficinas.options[indice].text;
   			llenarTipoOficina(nombreTipoOficina);
   			llenarCamposOficinas("","");
   		}
   }

   function llenarTipoOficina(nombreTipoOficina){
   		document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>").value = nombreTipoOficina;
   }

   function cargarComboOficinas(data){
   		dwr.util.removeAllOptions("<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA%>");
   		if(data!=null){
   			dwr.util.addOptions(document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA%>"), ["-Seleccione una opcion-"]);
	   		dwr.util.addOptions(document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA%>"), data.listaNumerosOficinas, "idOficinaOrigen", "nombre");
   		}else{
   			alert("No hay oficinas asociadas al tipo de oficina seleccionada");
   		}
   }

   function cargarCamposOficinas(){
   		var comboOficinas = document.getElementById("<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA%>");
	 	var indice = comboOficinas.selectedIndex;
		var nombreOficina = comboOficinas.options[indice].text;
		llenarCamposOficinas(comboOficinas.value,nombreOficina);
   }

   function llenarCamposOficinas(idNumeroOficina,nombreOficina){
       /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
                var i = idNumeroOficina.indexOf('-');
                var id = idNumeroOficina.substring(0,i);
                var version = idNumeroOficina.substring(i+1,idNumeroOficina.length);
                /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
   		document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA%>").value = id;
   		document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM%>").value = nombreOficina;
                document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION%>").value = version;
   }

   function verificarDepartamento(){
   		if(document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>").value != ""
   			&& document.getElementById("<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>").value != ""){
   			cargarTiposOficinas();
   		}
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

	<div id="departamentoMunicipio">
	<table width="100%">
		<!-- COMBOS DE DEPARTAMENTO Y MUNICIPIO -->
			<tr>
			    <td valign="top" bgcolor="#79849B" class="tdtablacentral" colspan="5">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
							<td class="bgnsub">Selecci&oacute;n Departamento - Municipio </td>
							<td width="16" class="bgnsub"><img src="<%=request.getContextPath() %>/jsp/images/ico_mapcolombia.gif" width="16" height="21"></td
							<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
						</tr>
					</table>

					<table width="100%" class="camposform">
						<tr>
							<td valign="top">
								<table width="100%" class="contenido">
									<tr>
										<td>Departamento</td>
									</tr>
									<tr>
										<td>
											<select id="<%=helper.LOCACION_ID_DEPARTAMENTO %>"
											name="<%=helper.LOCACION_ID_DEPARTAMENTO %>"
											onchange="cargarMunicipios();"
											class="campoformlista"
											<%if(request.getParameter("soloLectura")!=null){%>disabled="disabled"<%}%>>
											</select>
										</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<table width="100%" class="contenido">
									<tr>
										<td>Municipio</td>
									</tr>
									<tr>
										<td>
											<select id="<%=helper.LOCACION_ID_MUNICIPIO %>"
											name="<%=helper.LOCACION_ID_MUNICIPIO %>"
											onchange="cargarCamposMunicipios();"
											class="campoformlista"
											<%if(request.getParameter("soloLectura")!=null){%>disabled="disabled"><%}%>>
											</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
			</tr>
		<!-- FIN DE COMBOS DE DEPARTAMENTO Y MUNICIPIO -->
	</table>
	</div>
	<table width="100%" id="Nacional1" class="camposform"
		style="display:block">
		<tr>
			<td width="17%" align="right">Departamento</td>
			<td width="28%"><%
			textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setSize("5");
            textHelper.setCssClase(style);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO');cargaDesdeTexto=true;cargarMunicipios();");
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
            textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');cargaDesdeTexto=true;cargarCamposMunicipios();");
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
			<td width="10%"><%if(!read){%>
			<!-- <input type="button"
			style="background: url('<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif');width=22;height=26"
			onclick="toggleSlide();"> -->
				<%}%>
				</td>
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
    <div id="oficinaOrigen">
	<table width="100%">
		<!-- COMBOS DE OFICINA ORIGEN -->
			<tr>
			    <td valign="top" bgcolor="#79849B" class="tdtablacentral" colspan="5">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
							<td class="bgnsub">Selecci&oacute;n Oficina Origen </td>
							<td width="16" class="bgnsub"><img src="<%=request.getContextPath() %>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td
							<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
						</tr>
					</table>

					<table width="100%" class="camposform">
						<tr>
							<td valign="top">
								<table width="100%" class="contenido">
									<tr>
										<td>Tipo de Oficina</td>
									</tr>
									<tr>
										<td>
											<select id="<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO %>"
											name="<%=helperOficina.OFICINA_DOCUMENTO_ID_TIPO %>"
											onchange="cargarOficinas();"
											class="campoformlista"
											<%if(request.getParameter("soloLectura")!=null){%>disabled="disabled"><%}%>>
											</select>
										</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<table width="100%" class="contenido">
									<tr>
										<td>Número de oficina</td>
									</tr>
									<tr>
										<td>
											<select id="<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA %>"
											name="<%=helperOficina.OFICINA_DOCUMENTO_ID_OFICINA %>"
											onchange="cargarCamposOficinas();"
											class="campoformlista"
											<%if(request.getParameter("soloLectura")!=null){%>disabled="disabled"><%}%>>
											</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
			</tr>
		<!-- FIN DE COMBOS DE OFICINA ORIGEN -->
	</table>
	</div>

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
            textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
            textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
            textHelper.setReadonly(true);
            textHelper.render(request, out);
            %></td>
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
            TextHelper versionHelper = new TextHelper();
            versionHelper.setTipo("hidden");
            versionHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            versionHelper.render(request, out);
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


<SCRIPT>showhide();
//Si hay un parametro llamado solo lectura, no se cargan los combos
<%if(request.getParameter("soloLectura")==null){%>
	cargarComboDepartamentos();
<%}%>
</SCRIPT>
