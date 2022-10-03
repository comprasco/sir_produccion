<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.registro.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.DireccionesHelper"%>
<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionAntiguoSistemaHelper"%>


<%			ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
            MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
            List tiposPredio = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_TIPOS_PREDIO);
            if (tiposPredio == null) {
                tiposPredio = new ArrayList();
            }

            tipoPredioHelper.setTipos(tiposPredio);
            tipoPredioHelper.setCssClase("camposformtext");

            ListaElementoHelper ejesHelper = new ListaElementoHelper();
            List ejes = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_EJES_DIRECCION);
            if (ejes == null) {
                ejes = new ArrayList();
            }
            ejesHelper.setCssClase("camposformtext");
            ejesHelper.setOrdenar(false);
            ejesHelper.setTipos(ejes);

            ListaElementoHelper ejes2Helper = new ListaElementoHelper();
            ejes2Helper.setCssClase("camposformtext");
            ejes2Helper.setOrdenar(false);
            ejes2Helper.setTipos(ejes);
            CiudadanosAnotacionHelper ciudadanosHelper = new CiudadanosAnotacionHelper();
            TextHelper textHelper = new TextHelper();
            TextHelper hiddenHelper = new TextHelper();
            hiddenHelper.setTipo("hidden");
            //hiddenHelper.setTipo("text");
            TextAreaHelper textAreaHelper = new TextAreaHelper();
            AnotacionesFolioHelper anotacionesHelper = new AnotacionesFolioHelper();
            
            AntiguoSistemaBasicoHelper antSistHelper = new AntiguoSistemaBasicoHelper();
            Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Solicitud solicitud = turno.getSolicitud();
            Documento documento = null;
           
            NotasInformativasHelper helper = new NotasInformativasHelper();

            TextAreaHelper textAreaComplementacionHelper = new TextAreaHelper();
            Complementacion complementacion = (Complementacion) session
                    .getAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
            if (complementacion != null) {
                textAreaComplementacionHelper.setReadOnly(true);
                textAreaComplementacionHelper.setCssClase("campositem");
            } else {
                textAreaComplementacionHelper.setCssClase("camposformtext");
            }
            session.setAttribute(CVereda.MOSTRAR_VEREDA, "ASDFASDFASD");

            //Helper para las direcciones		
            gov.sir.core.web.helpers.correccion.DireccionesHelper dirHelper = new DireccionesHelper(
                    gov.sir.core.web.acciones.registro.AWFolio.AGREGAR_DIRECCION,
                    gov.sir.core.web.acciones.registro.AWFolio.ELIMINAR_DIRECCION);
            dirHelper.setFuncionCambiarAccion("cambiarAccionSubmit");
            dirHelper.setNombreFormaEdicionDireccion("CREAR");
            dirHelper.setFuncionQuitar("quitarDireccion");
            dirHelper.setFolioSesion(WebKeys.FOLIO_EDITADO);

            %>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user" />
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

function cambiarAccion(text) {
	document.CREAR.ACCION.value = text;
}

function cambiarAccionSubmit(text) {
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}

function Secuencia(text){
	document.CREAR.<%=CAnotacionCiudadano.SECUENCIA%>.value = text;
	cambiarAccionSubmit('<%=AWAntiguoSistema.REFRESCAR_ANOTACION%>');
}

function quitarDireccion(pos,accion){
	if(confirm("Esta seguro que desea eliminar la dirección ?")){
		document.CREAR.POSICION.value = pos;
		cambiarAccionSubmit(accion);		
	}
}

function quitar(pos,accion) {
	document.CREAR.POSICION.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}

function editarAnotacion(text){
	document.getElementById("NUM_ANOTACION_TEMPORAL").value = text
	cambiarAccionSubmit('EDITAR_ANOTACION');
}

function oficinas(nombre,valor,dimensiones)
{
	var idDepto = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.getElementById('version').value=valor+"_OFICINA_VERSION";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
          /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se asigna valores a campos en el formulario
                */
        document.getElementById('natjuridica_ver').value = valor+"_VER";
	var fechaRad = document.getElementById('<%= CFolio.ANOTACION_FECHA_DOCUMENTO %>').value;
	var url = nombre + '&<%= AWModificarFolio.ANOTACION_FECHA_RADICACION %>='+ fechaRad;
	popup=window.open(url,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CREAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function locacionTipoPredio(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	if(document.CREAR.<%=CFolio.FOLIO_TIPO_PREDIO%>.value == '<%=gov.sir.core.negocio.modelo.constantes.CTipoPredio.TIPO_URBANO%>'){	
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.NO_MOSTRAR_VEREDA%>',valor,dimensiones);
	} else {
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
	}
    popup.focus();
}
function locacion(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
}
function cambiarImagen() {
	if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.COPIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.ASOCIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else{
		document.all.caja.style.display = 'none'
		document.all.boton.style.display = 'none'
	}
}
        
function consultarFolio(text) {
	document.CREAR.action = 'certificado.do';
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}

function reescribirfecha(valor,id){
	var my_str = valor;
	if(my_str.search("/")==-1){
		var separator = "/";
		uno= my_str.slice(0,2);
		dos = my_str.slice(2,4);
		tres = my_str.slice(4,my_str.length);
		document.getElementById(id).value = "";
		my_str = uno+separator+dos+separator+tres;
		document.getElementById(id).value = my_str;
	}
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
<%//Cargar nombre del proceso para colocar título de la página
            long proc = turno.getIdProceso();
            String nombreProceso = " Antiguo Sistema";
            
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td class="tdtablaanexa02">
		
		
        <%
        
        
        final String PARAM__LOCAL_FOLIO_ID_MATRICULA = "PARAM:LOCAL_FOLIO_IDMATRICULA";
        String local_Folio_IdMatricula;
        
        if( null != ( local_Folio_IdMatricula = (String)session.getAttribute( PARAM__LOCAL_FOLIO_ID_MATRICULA ) )
        	&& session.getAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA) == null) {
        %>
				<br />


                    
                    <div width="100%">
                    
					<table width="100%" class="camposform">
					<tr>
						<td colspan="3">Datos del Folio</td>
					</tr>
					<tr>
						
						<td width="20" class="campositem">N&ordm;</td>
						<td class="campositem">
						<%
						try {
							TextHelper local_TextHelper;
							local_TextHelper = new TextHelper();
							
							local_TextHelper.setId(     PARAM__LOCAL_FOLIO_ID_MATRICULA );
							local_TextHelper.setNombre( PARAM__LOCAL_FOLIO_ID_MATRICULA );
							
							local_TextHelper.setCssClase( "campositem" );
							local_TextHelper.setFuncion( "style='border:0px;'" );
							local_TextHelper.setReadonly(true);
							
							local_TextHelper.render( request, out );
	                	} 
						catch (HelperException re) {
	                    	out.println("ERROR " + re.getMessage());
	                	}	
	                	%>&nbsp;
	                	</td>
					</tr>
					</table>
                    	
				  	</div>
                    
                    <%
                    
                    } // end-if
                    
                    %>
                    
                    
                    

<!-- nuevo helper de ciudadanos -->
			<form action="antiguosistema.do" name="CREAR" id="CREAR" method="post"><input
				type="hidden" name="ACCION" id="ACCION" /> <input type="hidden"
				name="NUM_ANOTACION_TEMPORAL" id="NUM_ANOTACION_TEMPORAL" value="" />
				<input name="Depto" type="hidden" id="id_depto" value="" /> <input
				name="Depto" type="hidden" id="nom_Depto" value="" /> <input
				name="Mpio" type="hidden" id="id_munic" value="" /> <input
				name="Mpio" type="hidden" id="nom_munic" value="" /> <input
				name="Ver" type="hidden" id="id_vereda" value="" /> <input
				name="Ver" type="hidden" id="nom_vereda" value="" /> 
				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1" />				
<%//HELPER VARIOS CIUDADANOS
            VariosCiudadanosAnotacionAntiguoSistemaHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionAntiguoSistemaHelper();
            variosCiudadanosHelper
                    .setFuncionJavascriptAccion("cambiarAccionSubmit");
            variosCiudadanosHelper.setMostrarBotonAgregar(false);
			variosCiudadanosHelper.setMostrarListaCiudadanos(false);
            Integer auxNumFilas = (Integer) session
                    .getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
            List lciudadanos = (List) request.getSession().getAttribute(
                    WebKeys.LISTA_CIUDADANOS_ANOTACION);
            if (lciudadanos == null) {
                lciudadanos = new Vector();
            }
            int numFilas;
            if (auxNumFilas == null)
                numFilas = AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
            else
                numFilas = auxNumFilas.intValue();
            List tiposIDNatural = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_TIPOS_ID_NATURAL);
           variosCiudadanosHelper.setPropertiesHandly(numFilas, tiposIDNatural,
                    AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS,
                    AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS,
                    AWCalificacion.AGREGAR_VARIOS_CIUDADANOS,
                    AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION, lciudadanos,
                    AWCalificacion.VALIDAR_CIUDADANO, "CREAR");

            //NUMERO DE LA SIGUIENTE ANOTACION
            String pos = (String) session
                    .getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
            if (pos == null)
                pos = "1";

            //Se mira si existen excepciones.
            Boolean exception;
            exception = (Boolean) session.getAttribute(WebKeys.HAY_EXCEPCION);
            if (exception != null) {
                variosCiudadanosHelper.setCentrar(false);
            }
            session.removeAttribute(WebKeys.HAY_EXCEPCION);

            %>
<!-- Fin nuevo helper -->
<!-- viejo helper -->
<%try {
                anotacionesHelper.setFormName("CREAR");
                anotacionesHelper.setVariosCiudadanosHelper(variosCiudadanosHelper);
                anotacionesHelper.setAction("antiguosistema.do");
               anotacionesHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %>
<!-- fin viejo helper -->


<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>			
			<img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
			width="7" height="10"></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
			src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10"
			height="10"></td>
		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
			width="10" height="10"></td>
	</tr>
	<tr>
		<td width="7"><img name="tabla_central_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
			width="7" height="29" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
					class="titulotbcentral">OPCIONES DEL FOLIO</td>
				<td width="9"><img name="tabla_central_r1_c3"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
					width="9" height="29" border="0" alt=""></td>
				<td width="20" align="center" valign="top"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
				</table>
				</td>
				<td width="12"><img name="tabla_central_r1_c5"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
					width="12" height="29" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td width="11"><img name="tabla_central_pint_r1_c7"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
			width="11" height="29" border="0" alt=""></td>
	</tr>
	<tr>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
		<td valign="top" bgcolor="#79849B" class="tdtablacentral">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				
				<td class="bgnsub">Opciones para el Folio</td>
				
				<td width="15"><img name="sub_r1_c4"
					src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
					width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>

		<table width="100%" class="camposform">
			<tr>
				
				<%
					String accionSec=(String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA);
				if (accionSec!=null){
    				if (accionSec.equals(AWAntiguoSistema.EDICION)){
        				%>
        				<td width="140"><a href="editar.folio.antiguo.sistema.view"><img
    					src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"
    					width="139" height="21" border="0"></a></td>
    					
    					<td width="140">&nbsp;</td>
    				<%}else if (accionSec.equals(AWAntiguoSistema.CREACION)){
                        %>
    					<td width="140"><input name="imageField" type="image"
						src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"
    					onClick="cambiarAccionSubmit( '<%=AWAntiguoSistema.REGRESAR_FOLIO%>')"width="139"
    					height="21" border="0"></td>

    					<td width="140"><input name="imageField" type="image"
						src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif"
						onClick="cambiarAccionSubmit( '<%=AWAntiguoSistema.TERMINAR_FOLIO%>')" width="139"
						height="21" border="0"></td>
    				<%}else if (accionSec.equals(AWAntiguoSistema.EDICION_ANOTACIONES)){%>
						<td width="140"><a href="hoja.ruta.view"><img
    					src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif"
    					width="139" height="21" border="0"></a></td>
    					
    					<td width="140">&nbsp;</td>
					<%}%>
					<%}else{
    				%>
                    <td width="140">&nbsp;</td>
					<td width="140">&nbsp;</td>
				<%}
				 %>
				<td>&nbsp;</td>
			</tr>
		</table>
		</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>
	<tr>
		<td><img name="tabla_central_r3_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
			width="7" height="6" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
			src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15"
			height="6"></td>
		<td><img name="tabla_central_pint_r3_c7"
			src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
			width="11" height="6" border="0" alt=""></td>
	</tr>
</table>

<%try {
                //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
                //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
                //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
                helper.setNombreFormulario("CREAR");
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%>

</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>

</table>
