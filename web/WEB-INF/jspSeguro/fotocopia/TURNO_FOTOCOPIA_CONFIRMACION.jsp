<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.fotocopia.*"%>

<%@page import="gov.sir.core.web.helpers.fotocopia.DocumentosAsociadosTblDrawable3_HelperWrapper" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants" %>
<%@page import="gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item" %>
<%@page import="org.auriga.core.web.HelperException" %>



<%
// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +
   // mantener el valor de la lista de documentod fotocopia
   List documentosAsociados_Model = null;
// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +
%>

<%


	TextAreaHelper descripcion = new TextAreaHelper();
	descripcion.setCols("90");
	descripcion.setRows("10");

	NotasInformativasHelper helper = new NotasInformativasHelper();

	//Obtener el turno de la sesión
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudFotocopia solicitud = new SolicitudFotocopia();
	LiquidacionTurnoFotocopia liquidacion = null;


        // variables "bind" para los datos de Solicitante

	String nombreCiudadano="";
	String apellido1Ciudadano ="";
	String apellido2Ciudadano ="";
    String tipoDocCiudadano = "";
	String numeroCiudadano ="";
	String tipoDocumento ="";
	String descripcionDocumento ="";
	String tipoFotocopia ="";
	double valorLiquidado =0;
	int numHojas =0;
	int numCopias =0;


	if (turno != null)
	{
	   solicitud = (SolicitudFotocopia) turno.getSolicitud();

	   //Obtener la liquidacion
	   if (solicitud != null)
	   {
	       long lastIdLiquidacion = solicitud.getLastIdLiquidacion();
	       Long lastIdLong = new Long (lastIdLiquidacion);
	       if (lastIdLiquidacion > 0)
	       {
	           liquidacion = (LiquidacionTurnoFotocopia) solicitud.getLiquidaciones().get(lastIdLong.intValue()-1);

	       }
	   }

	   //Obtener datos del ciudadano solicitante
	   if (solicitud !=null)
	   {
	      Ciudadano ciudadano = solicitud.getCiudadano();

	      if (ciudadano != null)
	      {
	          if (ciudadano.getNombre()!=null)
	          {
	              nombreCiudadano = ciudadano.getNombre();
	          }

	          if (ciudadano.getApellido1()!=null)
	          {
	              apellido1Ciudadano = ciudadano.getApellido1();
	          }

	          if (ciudadano.getApellido2()!=null)
	          {
	              apellido2Ciudadano = ciudadano.getApellido2();
	          }

	          if (ciudadano.getTipoDoc()!=null)
	          {
	              tipoDocCiudadano = ciudadano.getTipoDoc();
	          }

	          if (ciudadano.getDocumento()!=null)
	          {
	              numeroCiudadano = ciudadano.getDocumento();
	          }
	       }
	    }



	    //Obtener datos documento fotocopiado
	    if (solicitud != null)
	    {
                List tempList = null;
                tempList = solicitud.getDocumentoFotocopia();
                documentosAsociados_Model = tempList;
	        //Tipo Documento.
	        //// Documento documentoSol = solicitud.getDocumento();
	        //// if (documentoSol != null)
	        ////{
	        ////    TipoDocumento tipoDocumentoSol = documentoSol.getTipoDocumento();
	        ////    tipoDocumento = tipoDocumentoSol.getNombre();
	        ////}

	        //Descripcion Documento.
	        ////if (solicitud.getDescripcion()!=null)
	        ////{
	        ////    descripcionDocumento = solicitud.getDescripcion();
	        ////}

	    }

	   //Obtener datos de la liquidacion
	   if (liquidacion != null)
	   {
	       valorLiquidado = liquidacion.getValor();
	       // numCopias = liquidacion.getNumCopias();
	       // numHojas = liquidacion.getNumCopias();
	       // TipoFotocopia tipoFot = liquidacion.getTipoFotocopia();
           // if (tipoFot != null)
           // {
           //    tipoFotocopia = tipoFot.getNombre();
           // }
	   }
	}

%>


<%
// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //
//

Iterator iterator = null;

List tipos = null;

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


      item.setNumCopias( String.valueOf( documentoFotocopia.getNumCopias() ) );
      item.setTipoDocumento_Id( documentoFotocopia.getTipoDocumento().getIdTipoDocumento() );

      item.setTipoFotocopia_Id( documentoFotocopia.getTipoFotocopia().getIdTipoFotocopia() );
      item.setNumHojas( String.valueOf( documentoFotocopia.getNumHojas() ) );

      numHojas += ( documentoFotocopia.getNumHojas() * documentoFotocopia.getNumCopias() );

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
   DocumentosAsociadosTblDrawable3_HelperWrapper tblDrawableHelper
    = new DocumentosAsociadosTblDrawable3_HelperWrapper();

// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //
%>




<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />




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



<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js">
</script>

  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno Fotocopias</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  
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
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Documento</td>
                
                
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
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




            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              
            </table>
            <br />
            <table width="100%" class="camposform">
              <tr>
                
                <td width="150">Valor Liquidado </td>
                <td class="campositem"><%=valorLiquidado%></td>
              </tr>
            </table>
            <br>

			<table width="100%" class="camposform">
                 	<tr>
                 	
                 	<td width="179">Tipo de Identificaci&oacute;n</td>
                 	<td width="211">
		          		<td class="campositem"><%=tipoDocCiudadano%></td>
                 	</td>
                 	<td width="146">N&uacute;mero</td>
                 	<td width="212">
		          		<td class="campositem"><%=numeroCiudadano%></td>
                 	</td>
                 	</tr>
                 	<tr>
                 	
                 	<td>Primer Apellido</td>
                 	<td>
		          		<td class="campositem"><%=apellido1Ciudadano%></td>
                 	</td>
                 	<td>Segundo Apellido</td>
                 	<td>
		          		<td class="campositem"><%=apellido2Ciudadano%></td>
                 	</td>
                 	</tr>
                 	<tr>
                 	
                 	<td>Nombre</td>
                 	<td>
		          		<td class="campositem"><%=nombreCiudadano%></td>
                 	</td>
                 	<td>&nbsp;</td>
                 	<td>&nbsp;</td>
                 	</tr>
             	</table>
              	<hr class="linehorizontal">
			<form name="" method="post" action="entregaFotocopia.do">
			<input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWEntregaFotocopia.FOTOCOPIAR_DOCUMENTO%>">
			 <table width="100%" class="camposform">
              <tr>
	              
				  <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></td>
                  </form>
                  <td width="150" align="center"></td>
        	      <td>&nbsp;</td>
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
		<%try{
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}%>
   </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
