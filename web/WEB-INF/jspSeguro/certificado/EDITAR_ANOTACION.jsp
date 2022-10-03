<%@page import="java.util.Date"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page
	import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper"%>
<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gov.sir.core.web.acciones.comun.*"%>

<%//BASICOS
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            if (turno == null) {
            	//System.out.println("Turno nulo en editar anotacion");
			} 
            String nomOficina = "";
            /*SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
             solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicitud.getDocumento().getOficinaOrigen().getNombre();
             Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
             if(folio==null){
             } else {
             }
             List anotaciones=folio.getAnotaciones();*/
            List anotaciones = (List) session
                    .getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            if (anotaciones == null) {
                anotaciones = new Vector();
            }

            //NUMERO DE LA ANOTACION A EDITAR.

            ////System.out.println("\n\n # de anotaciones --> "+folio.getAnotaciones().size());

            int pos;
            String num = (String) request.getSession().getAttribute(
                    AWCalificacion.NUM_ANOTACION_TEMPORAL);
            int n = Integer.parseInt(num);
            n--;


            List anotacionesTemporales = (List) request.getSession()
                    .getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            Anotacion a = (Anotacion) anotacionesTemporales.get(n);
            pos = Integer.parseInt(a.getOrden());

            request.getSession().setAttribute(
                    AWCalificacion.NUM_ANOTACION_TEMPORAL, a.getOrden());

            List grupoNaturalezas = (List) session.getServletContext()
                    .getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
            session.setAttribute("listanat", grupoNaturalezas);
            session.removeAttribute("cancelar");

            //iniciando en el session los valores de la anotacion correspondiente
            request.getSession().setAttribute(
                    CFolio.ANOTACION_VALOR_ESPECIFICACION,
                    Double.toString(a.getValor()));
            request.getSession().setAttribute(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
                    a.getNaturalezaJuridica().getIdNaturalezaJuridica());
            request.getSession().setAttribute(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,
                    a.getNaturalezaJuridica().getNombre());
            request.getSession().setAttribute(
                    CFolio.ANOTACION_COMENTARIO_ESPECIFICACION,
                    a.getComentario());
            if (request.getSession().getAttribute(
                    WebKeys.LISTA_CIUDADANOS_ANOTACION) == null) {
                request.getSession().setAttribute(
                        WebKeys.LISTA_CIUDADANOS_ANOTACION,
                        a.getAnotacionesCiudadanos());
            }


            //PREPARACION HELPERS
            TextHelper tiposDocHelper = new TextHelper();
            TextHelper textHelper = new TextHelper();
            TextHelper hiddenHelper = new TextHelper();
            hiddenHelper.setTipo("hidden");
            TextAreaHelper textAreaHelper = new TextAreaHelper();
            MostrarFechaHelper fecha = new MostrarFechaHelper();

            //HELPER VARIOS CIUDADANOS
            VariosCiudadanosAnotacionHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
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
            List tiposID = (List) session.getServletContext().getAttribute(
                    WebKeys.LISTA_TIPOS_ID);
            List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
            List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
            List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
            List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
            //Se debe mostrar el boton de agregar
            variosCiudadanosHelper.setMostrarBotonAgregar(true);
            variosCiudadanosHelper.setPropertiesHandly(numFilas, tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica,
                    AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
                    AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
                    AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION,
                    AWCalificacion.ELIMINAR_CIUDADANO_EDICION, lciudadanos,
                    AWCalificacion.VALIDAR_CIUDADANO_EDICION, "ANOTACION");
            //Se mira si existen excepciones.
            Boolean exception;
            exception = (Boolean) session.getAttribute(WebKeys.HAY_EXCEPCION);
            if (exception != null) {
                variosCiudadanosHelper.setCentrar(false);
            }
            session.removeAttribute(WebKeys.HAY_EXCEPCION);

            %>
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
    
    function ocultarSalvedad(text) {
	   document.ANOTACION.VER_SALVEDAD.value = text;
	   cambiarAccion('VER_SALVEDAD');
  	}
  	
function cambiarAccion(text) {
	document.ANOTACION.ACCION.value = text;
	document.ANOTACION.submit();
}
function quitar(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function cargarAnotacion(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.ANOTACION.ACCION.value='<%=AWCalificacion.PRESERVAR_INFO_ANOTACION%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.ANOTACION.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
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

function Secuencia(text){
	document.ANOTACION.SECUENCIA.value = text; 
	cambiarAccion('REFRESCAR_EDICION');
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
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<form action="radicacion.do" method="post" name="ANOTACION"
		id="ANOTACION"><input type="hidden" name="ACCION" value=""> <input
		type="hidden" name="POSICION" value=""> <input type="hidden"
		name="VER_SALVEDAD" value=""> <input type="hidden" name="SECUENCIA"
		value="-1">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="12">&nbsp;</td>
		<td width="12"><img name="tabla_gral_r1_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif"
			width="12" height="23" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif"
					class="titulotbgral">Creaci&oacute;n Anotaciones</td>
				<td width="28"><img name="tabla_gral_r1_c3"
					src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif"
					width="28" height="23" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td width="12"><img name="tabla_gral_r1_c5"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif"
			width="12" height="23" border="0" alt=""></td>
		<td width="12">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">ANOTACIONES</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_new.gif"
									width="16" height="21"></td>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>

			<tr>
				<td width="7"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%">
					<tr>
						<td>





						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
							<tr>
								<td width="12"><img name="sub_r1_c1"
									src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
									width="12" height="22" border="0" alt=""></td>
								<td class="bgnsub">Especificaci&oacute;n</td>
								<td width="16" class="bgnsub"><img
									src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif"
									width="16" height="21"></td>
								<td width="15"><img name="sub_r1_c4"
									src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
									width="15" height="22" border="0" alt=""></td>
							</tr>
						</table>
						<table width="100%" class="camposform">
							<tr>
								<td>Fecha de Radicaci&oacute;n</td>
								<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="campositem"><%try {

                Liquidacion liq = (Liquidacion) turno.getSolicitud()
                        .getLiquidaciones().get(0);

                Date fechaPago = liq.getPago().getFecha();
                fecha.setDate(fechaPago);
                fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                fecha.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						<table width="100%" class="camposform">
							<tr>
								<td width="20"><img
									src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
									width="20" height="15"></td>
								<td width="50">Valor</td>
								<td width="150"><%try {
                String valor = (String) session
                        .getAttribute(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
                String nValor = NumberFormat.getInstance().format(
                        Double.parseDouble(valor));
                nValor = nValor + ".00";
                textHelper.setTipo("text");
                textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
                textHelper
                        .setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                textHelper.setEditable(true);
                textHelper.setSize("20");
                session
                        .setAttribute(
                                AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION,
                                nValor);
                textHelper.render(request, out);
                textHelper.setFuncion("");
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %></td>


								<td width="150">Naturaleza Jur&iacute;dica</td>
								<td width="40" align="justify">ID</td>
								<td width="40"><%try {
                textHelper
                        .setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                textHelper.setCssClase("camposformtext");
                textHelper
                        .setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                textHelper.setSize("5");
                textHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%></td>
								<td width="100" align="right">Descripci&oacute;n</td>
								<td align="justify">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<input name="natjuridica_id" type="hidden" id="natjuridica_id"
											value=""> <input name="natjuridica_nom" type="hidden"
											id="natjuridica_nom" value="">
										<td align="right"><%try {
                textAreaHelper
                        .setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                textAreaHelper.setCols("50");
                textAreaHelper.setRows("1");
                textAreaHelper.setReadOnly(true);
                textAreaHelper.setCssClase("camposformtext");
                textAreaHelper
                        .setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                textAreaHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%></td>
										<td><a
										    href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=false&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img
											src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif"
											alt="Permite seleccionar la naturaleza juridica" width="16"
											height="21" border="0"
											onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						<table width="100%" class="camposform">
							<tr>
								<td width="20"><img
									src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
									width="20" height="15"></td>
								<td>Comentario</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><%try {
                textAreaHelper
                        .setNombre(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
                textAreaHelper.setCols("60");
                textAreaHelper.setRows("3");
                textAreaHelper.setReadOnly(false);
                textAreaHelper.setCssClase("camposformtext");
                textAreaHelper
                        .setId(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
                textAreaHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }%></td>
							</tr>
						</table>
						<br>
						<hr class="linehorizontal"><%
            try {
                //TODO set ciudadanos
                variosCiudadanosHelper.render(request, out);
            } catch (HelperException re) {
                re.printStackTrace();
            }

            %>
						</td>
					</tr>
				</table>
				</td>
				<td width="11"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%" class="camposform">
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
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="15" height="6"></td>
				<td><img name="tabla_central_pint_r3_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
					width="11" height="6" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>



	<tr>
		<td>&nbsp;</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">GRABACION TEMPORAL</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_new.gif"
									width="16" height="21"></td>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>
			<tr>
				<td width="7"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%">

				</table>
				</td>
				<td width="11"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			</tr>
			<tr>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15"></td>
						<td width="150"><a
							href="javascript:cambiarAccion('GRABAR_EDICION')"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif"
							name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
						<td><a href="javascript:cambiarAccion('CANCELAR_EDICION')"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif"
							name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
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
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="15" height="6"></td>
				<td><img name="tabla_central_pint_r3_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
					width="11" height="6" border="0" alt=""></td>
			</tr>
		</table>
		</td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td><img name="tabla_gral_r3_c1"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif"
			width="12" height="20" border="0" alt=""></td>
		<td
			background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
		<td><img name="tabla_gral_r3_c5"
			src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif"
			width="12" height="20" border="0" alt=""></td>
		<td>&nbsp;</td>
	</tr></form>
</table>