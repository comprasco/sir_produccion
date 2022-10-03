<%@page import="gov.sir.core.web.helpers.comun.DatosPagoHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.fotocopia.AWLiquidacionFotocopia"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item" %>
<%@page import="gov.sir.core.web.helpers.fotocopia.DocumentosAsociadosTblDrawable2_HelperWrapper" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants" %>
<%
            javax.servlet.ServletContext context = session.getServletContext();
            String numliquidacion = (String) session
                    .getAttribute(WebKeys.VALOR_LIQUIDACION);
            List consignaciones = (List) session
                    .getAttribute(WebKeys.LISTA_CONSIGNACIONES);
            List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
            AplicacionPago appEfectivo = (AplicacionPago) session
                    .getAttribute(WebKeys.APLICACION_EFECTIVO);
            AplicacionPago appTimbre = (AplicacionPago) session
                    .getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
            double valorLiquidacion = 0;
            List marcasCheques = (List)session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
            List marcasConsignacion = (List)session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
            if (numliquidacion != null) {
                valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
            } else {
                valorLiquidacion = 0;
            }

            DatosPagoHelper datosPagoHelper = new DatosPagoHelper(request,
                    valorLiquidacion, consignaciones, cheques, appEfectivo,
                    appTimbre,marcasConsignacion,marcasCheques, marcasCheques);

            ListaElementoHelper tiposFotocopia = new ListaElementoHelper();

            TextAreaHelper descripcion = new TextAreaHelper();
            descripcion.setCols("90");
            descripcion.setRows("2");

            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            NotasInformativasHelper helper = new NotasInformativasHelper();
            SolicitudFotocopia solicitud = (SolicitudFotocopia) turno
                    .getSolicitud();
            // LiquidacionTurnoFotocopia liquidacion = (LiquidacionTurnoFotocopia) (solicitud
            //        .getLiquidaciones().get(0));
            Ciudadano ciudadano = solicitud.getCiudadano();

            List tipos = null;

%>

<%
// Cast para la solicitud especifica : @see solicitud

List documentosAsociados_Model = solicitud.getDocumentoFotocopia();

Iterator iterator = null;


List documentosAsociados;

process_Load: {

  List tempList = null;
  if( null != session.getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS )) {
    tempList = (List)session.getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );
  }
  else {
    tempList = new java.util.ArrayList();
    int index = 0;
    iterator= documentosAsociados_Model.iterator();
    for( ;iterator.hasNext();index++){

      gov.sir.core.negocio.modelo.DocumentoFotocopia documentoFotocopia ;
      documentoFotocopia = (gov.sir.core.negocio.modelo.DocumentoFotocopia)iterator.next();//new gov.sir.core.negocio.modelo.DocumentoFotocopia();
      DocumentoAsociado_Item item = new DocumentoAsociado_Item();
      item.setDescripcion( documentoFotocopia.getDescripcion() );



      // get the default values
      String defaultNumHojasValue   = AW_FotocopiasConstants.DEFAULT_NUMHOJAS_VALUE;//AW_FotocopiasConstants.DEFAULT_NUMCOPIAS_VALUE;
      String defaultTipoFotocopiaId = AW_FotocopiasConstants.DEFAULT_TIPOFOTOCOPIA_ID; // documentoFotocopia.getTipoDocumento().getIdTipoDocumento()

      item.setNumCopias( String.valueOf( documentoFotocopia.getNumCopias() ) );
      item.setTipoDocumento_Id( documentoFotocopia.getTipoDocumento().getIdTipoDocumento() );

      item.setNumHojas( defaultNumHojasValue );
      item.setTipoFotocopia_Id( defaultTipoFotocopiaId );

      tempList.add( index, item );
    }

    documentosAsociados = tempList;
    session.setAttribute(AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS, documentosAsociados);
                tipos  = (List) session.getServletContext().getAttribute(
                        WebKeys.LISTA_TIPOS_FOTOCOPIA);



  }



}

// colocar los valores en la tabla de despliegue

   // list-model

   documentosAsociados=(List)session.getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );

   // para acciones de tabla
   DocumentosAsociadosTblDrawable2_HelperWrapper tblDrawableHelper
    = new DocumentosAsociadosTblDrawable2_HelperWrapper();

%>


<%
	ListaElementoHelper tiposDocumento = new ListaElementoHelper();

	TextHelper numHojas = new TextHelper();
	TextHelper numCopias = new TextHelper();
	ListaElementoHelper docHelper = new ListaElementoHelper();
	TextHelper textHelper = new TextHelper();

        List SolicitudItems = new Vector();

%>



<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css"
	rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords"
	content="inicio, sesion, login, password, clave, usuario, user">
<script
	language="javascript" type="text/javascript"
	src="<%= request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>

<script type="text/javascript" >
	function cambiarAccion( text ) {
		document.reliquidar.ACCION.value = text;
		document.reliquidar.submit();
	}
</script>



<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->
<!-- sof:block: "alt behavior" -->

<script type="text/javascript">
var ol_fgcolor="#ffffc0";
var ol_border="1";
var ol_bgcolor="#FFFFC0";
var ol_textcolor="#000000";
var ol_capcolor="#aaaaaa";
//var ol_css="forms-help";

</script>
<style media="screen">
.forms-help {
    border-style: dotted;
    border-width: 1px;
    padding: 5px;
    background-color:#FFFFC0; /* light yellow */
    width: 200px; /* otherwise IE does a weird layout */
    z-index:1000; /* must be higher than forms-tabContent */
}

</style>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/privileged/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<!-- eof:block -->
<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->


<table width="100%" border="0" cellpadding="0" cellspacing="0">
	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>
				<td
					background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%= request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
				<td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">Turno Fotocopias</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%= request.getContextPath()%>/jsp/images/ico_new.gif"
									width="16" height="21"></td>
								<td><img
									src="<%= request.getContextPath()%>/jsp/images/ico_fotocopias.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>
			<tr>
				<td width="7"
					background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">
				<form
					action="turnoLiquidacionFotocopia.do"
					method="post"
					name="reliquidar"
					id="reliquidar"
				>
					<input
						type="hidden"
					   name="<%=WebKeys.ACCION%>"
					   value="<%=AWLiquidacionFotocopia.RELIQUIDAR%>"
					/>
				   <br />
                            <!-- @author : HGOMEZ
                             *** @change : Se inserta el siguiente código para dar solución a la modificación
                             *** en la interfaz solicitada por este caso.
                             *** Caso Mantis : 12288 -->
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                                <tr> 
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos 
                                        Solicitud</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                    <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr> 
                                    <td width="20%"><strong>Tipo de solicitud</strong></td>
                                    <td width="20%">
                                        <strong>Exento: </strong> 
                                        <input type="checkbox" name="esExento" id="esExento" >  
                                    </td>
                                </tr>
                            </table>     
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Documentos</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br />

<!-- REGION:BLOCK (SOF) DESCRIPTION="mantiene el conjunto de documentos asociados" -->

				<table width="100%" class="camposform">
					<tr>
						
						<td>Documentos en esta solicitud</td>
					</tr>
					<tr>
						
						<td>

  <%

  // + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //

  try {

      tblDrawableHelper.setLov_TipoDocumentoContextAttName( WebKeys.LISTA_TIPOS_DOCUMENTO );
      tblDrawableHelper.setLov_TipoFotocopiaContextAttName( WebKeys.LISTA_TIPOS_FOTOCOPIA );
      tblDrawableHelper.setDocumentosAsociadosListName( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );
      tblDrawableHelper.render( request, out );
    }
    catch(HelperException re){
      out.println("ERROR " + re.getMessage());
    }

  // + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //

  %>

                                                  <table>
                                                    <tr>
                                                      <td>&nbsp;
                                                      </td>

                                                      <td>&nbsp;
                                                      </td>

                                                    </tr>

                                                  </table>

                                                </td>
					</tr>
				</table>


<!-- REGION:BLOCK (EOF) -->

				<br />
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						
						<td
							background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Datos Solicitante</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
<%
            String tipoDocumentoSolicitante = "";
            String nombreSolicitante = "";
            String apellido1Solicitante = "";
            String apellido2Solicitante = "";
            if (ciudadano.getApellido1() != null) {
                apellido1Solicitante = ciudadano.getApellido1();
            }
            if (ciudadano.getApellido2() != null) {
                apellido2Solicitante = ciudadano.getApellido2();
            }
            if (ciudadano.getNombre() != null) {
                nombreSolicitante = ciudadano.getNombre();
            }

%>


						<td width="20"><img
							src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td width="179">Tipo de Identificaci&oacute;n</td>
						<td width="211">
						<td class="campositem"><%=ciudadano.getTipoDoc()%></td>
						</td>
						<td width="146">N&uacute;mero</td>
						<td width="212">
						<td class="campositem"><%=ciudadano.getDocumento()%></td>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Primer Apellido</td>
						<td>
						<td class="campositem"><%=apellido1Solicitante%></td>
						</td>
						<td>Segundo Apellido</td>
						<td>
						<td class="campositem"><%=apellido2Solicitante%></td>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Nombre</td>
						<td>
						<td class="campositem"><%=nombreSolicitante%></td>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<hr class="linehorizontal">
				<table width="100%" class="camposform">
					<tr>
						
						<td width="150">
							<a href="javascript:cambiarAccion('<%=AW_FotocopiasConstants.RELIQUIDAR_ACTION%>');">
								<img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" />
							</a>
						</td>
				

				<td>&nbsp;</td>

				<td width="50">
					<a href="javascript:cambiarAccion('<%=AW_FotocopiasConstants.NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION%>');">
						<img src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" width="139" height="21" border="0" alt="NEGAR"/>
					</a>
				</td>

			</tr>
		</table>

	</form>

		</td>
		<td width="11"
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>
	<tr>
		<td><img name="tabla_central_r3_c1"
			src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
			width="7" height="6" border="0" alt=""></td>
		<td
			background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
			src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15"
			height="6"></td>
		<td><img name="tabla_central_pint_r3_c7"
			src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
			width="11" height="6" border="0" alt=""></td>
	</tr>
</table>


<%try {
                //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
                //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
                //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
                helper.setNombreFormulario("FOT_LIQUIDACION");
                helper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }

            %>
</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>

</table>
