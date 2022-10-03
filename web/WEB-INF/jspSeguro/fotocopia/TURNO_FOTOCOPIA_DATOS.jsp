<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.fotocopia.DocumentosAsociadosTblDrawable3_HelperWrapper" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants" %>
<%@page import="gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item" %>
<%@page import="org.auriga.core.web.HelperException" %>


<%
    //Recuperar el Turno desde la sesión
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudFotocopia solicitud = (SolicitudFotocopia)turno.getSolicitud();
	if(solicitud==null){
		solicitud = new SolicitudFotocopia();
	}

        /*

	//Se obtiene el Documento.
	Documento documentoFotocopiado = new Documento();
	TipoDocumento tipoDoc = new TipoDocumento();
	String tipoDocumento="";
	String descripcion="";
	if (solicitud != null)
	{
	  descripcion = solicitud.getDescripcion();

	  if (solicitud.getDocumento() != null)
	  {
	     documentoFotocopiado = solicitud.getDocumento();
	     tipoDoc = documentoFotocopiado.getTipoDocumento();

	     if (tipoDoc != null)
	     {
	        tipoDocumento = tipoDoc.getNombre();
	     }
	   }
	}

        */

	//Se obtienen los datos del Solicitante
    String nombreCiudadano    ="";
    String apellido1Ciudadano ="";
    String apellido2Ciudadano ="";
    String numeroCiudadano    ="";
    String tipoDocCiudadano   ="";
    Ciudadano ciudadano = new Ciudadano ();
    if (solicitud != null)
    {
       if (solicitud.getCiudadano()!= null)
       {
          ciudadano = solicitud.getCiudadano();
          if (ciudadano.getApellido1()!=null)
          {
             apellido1Ciudadano = ciudadano.getApellido1();
          }
          if (ciudadano.getApellido2()!=null)
          {
             apellido2Ciudadano = ciudadano.getApellido2();
          }
          if (ciudadano.getNombre()!=null)
          {
             nombreCiudadano = ciudadano.getNombre();
          }
          if (ciudadano.getDocumento()!=null)
          {
             numeroCiudadano = ciudadano.getDocumento();
          }
          if (ciudadano.getTipoDoc()!=null)
          {
             tipoDocCiudadano = ciudadano.getTipoDoc();
          }
        }
     }

    // Recuperar atributos de la liquidación.
    // TODO: despliegue de detalles multi-item ?

    // devuelve una lista pero
    // solo se coge el ultimo registro.
    LiquidacionTurnoFotocopia liquidacion
     = (LiquidacionTurnoFotocopia) solicitud.getLiquidaciones().get( solicitud.getLiquidaciones().size() - 1 ) ;

    double valorLiq = 0;

    valorLiq = liquidacion.getValor();
    long numHojas  = 0;

    /*
    int numHojas  = 0;
    int numCopias = 0;
    String tipoFotoc = "";
    if (solicitud != null)
    {
        if (solicitud.getLiquidaciones() != null)
        {
           long lastIdLiq = solicitud.getLastIdLiquidacion();
           Long lastLong = new Long (lastIdLiq);
           liquidacion = (LiquidacionTurnoFotocopia) solicitud.getLiquidaciones().get(lastLong.intValue()-1);
           if (liquidacion != null)
           {
               numCopias = liquidacion.getNumCopias();
               numHojas  = liquidacion.getNumHojas();
               valorLiq  = liquidacion.getValor();
               TipoFotocopia tipoFot = liquidacion.getTipoFotocopia();
               if (tipoFot !=null)
               {
                  tipoFotoc = tipoFot.getNombre();
               }

            }
         }

     }
      */


%>
<%

   Liquidacion test1Liquidacion = (Liquidacion) request.getSession()
                                                       .getAttribute(WebKeys.LIQUIDACION);


%>


<%
// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //
// Cast para la solicitud especifica : @see solicitud

List documentosAsociados_Model = solicitud.getDocumentoFotocopia();
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

<%
// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //
// probar para ver si no es necesario editar el helper de datos pago
request.setAttribute( WebKeys.PAGO_FOTOCOPIAS_ACTIVE, Boolean.TRUE );

// + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + //
%>

<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/calendario.js"><!-- --> </script>
<script>
       function cambiarAccion(text) {
       document.PAGOFOTOCOPIA.ACCION.value = text;
       document.PAGOFOTOCOPIA.submit();
       }

</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">



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
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">



           <table border="0" cellpadding="0" cellspacing="0">

              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Valor Liquidado</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table>


          </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <form action="pagoFotocopia.do" method="post" name="PAGOFOTOCOPIA" id="PAGOFOTOCOPIA">
            <input type="hidden" name="ACCION">

              <br />
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Documentos Asociados</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br />



<!-- REGION:BLOCK (SOF) DESCRIPTION="mantiene el conjunto de documentos asociados" -->

				<table width="100%" class="camposform">
					<tr>
						<td></td>
						<td>Documentos en esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
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
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem"><%=tipoDocCiudadano%>&nbsp;</td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=numeroCiudadano%>&nbsp;</td>

                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=apellido1Ciudadano%>&nbsp;</td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=apellido2Ciudadano%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <td class="campositem"><%=nombreCiudadano%>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>


              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="bgnsub">Liquidaci&oacute;n</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <!--
                     JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                     Cambio del texto "Valor Liquidado" por "Valor Total Liquidado Ajustado a la Centena por Actos"
                  -->
                  <td width="179">Valor Total Liquidado Ajustado a la Centena por Actos</td>
                  <td width="211" class="campositem"><%=valorLiq%>&nbsp;</td>
                  <td width="146"></td>
                  <td width="212">&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 
</table>
