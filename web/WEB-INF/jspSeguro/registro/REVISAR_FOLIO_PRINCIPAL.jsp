<%@page import="gov.sir.core.web.helpers.comun.MostrarFolioHelper" %>
<%@page import="org.auriga.core.web.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="java.util.*,gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWRevision"%>
<% 
	boolean esFolioMayorExtension=false;
	String vistaActual;
	
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	
	//limpieza de valores del session
	request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
	
	Boolean temp=(Boolean)session.getAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);
	if(temp!=null){
		esFolioMayorExtension = temp.booleanValue();	
	}
	
	//inicializacion Vista generadora
			
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;
	
	java.math.BigDecimal totalAnotaciones = (java.math.BigDecimal) request.getSession().getAttribute(WebKeys.TOTAL_ANOTACIONES);          	 	
	List gravamenes = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);          	 	
	List medCautelares = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES); 
	List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
	List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);
        List historialAreas = (List) request.getSession().getAttribute(WebKeys.HISTORIAL_AREAS);
    
    
%>
<script>
function cambiarAccion(text) {
       document.REVISAR.ACCION.value = text;
       document.REVISAR.submit();
}

</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Documento sin t&iacute;tulo</title>
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .Estilo2 {font-size: 11px; color: #3A414E; text-decoration: none; background-color: #FFFFFF; border-top: 1px solid #5D687D; border-right: 1px solid #5D687D; border-bottom: 1px solid #5D687D; border-left: 5px solid #6A7891; font-family: Verdana, Arial, Helvetica, sans-serif;}
        -->
    </style>
</head>

<body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    


        <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_esquina001.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">

        </td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>

    </tr>

    <tr>

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td align="center" class="tdtablaanexa02">
    
    <%try{
   			//setear atributos del folio.
   			mFolio.setFolio(f);
   			mFolio.soloConsulta();
   			mFolio.setEditable(false);
   			
   			/*setters de estilo*/
   			mFolio.setECampos("camposform");
   			mFolio.setECampoTexto("campositem");
   			mFolio.setETituloFolio("titulotbcentral");
   			mFolio.setETitulos("titresaltados");
   			mFolio.setETitulosSecciones("bgnsub");
   			
   			/*setters de imagenes */
   			mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
   			mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
   			mFolio.setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
   			mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");
   			
   			mFolio.setMayorExtension(esFolioMayorExtension);
   			mFolio.setNombreAccionFolio("calificacion.do");
   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
   			mFolio.setNombreAncla("ancla");
   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_REVISION");
   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
   			mFolio.setNombreOcultarFolio("O_FOLIO");
   			mFolio.setNombrePaginador("NOMBRE_PAGINADOR_REVISION");
   			mFolio.setNombreResultado("NOMBRE_RESULTADO_REVISION");
   			mFolio.setnombreNumPaginaActual("NUM_PAGINA_ACTUAL_REVISION");
   			mFolio.setPaginaInicial(0);
   			mFolio.setVistaActual(vistaActual);
   			mFolio.setTotalAnotaciones(totalAnotaciones);
   			mFolio.setGravamenes(gravamenes);
   			mFolio.setMedCautelares(medCautelares);
   			mFolio.setFalsaTradicion(falsaTradicion);
   			mFolio.setAnotacionesInvalidas(anotacionesInvalidas);
                        mFolio.setHistorialAreas(historialAreas);
   			//datos a mostrar encabezado
   			mFolio.MostrarAperturaFolio();
   			mFolio.MostrarCabidaLinderos();
   			mFolio.MostrarGravamenes();
   			mFolio.MostrarDireccionInmueble();
   			mFolio.MostrarComplementacion();
   			mFolio.render(request, out);
   }catch(HelperException re){
	 	out.println("ERROR " + re.getMessage());
	}%>
    
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>

    </tr>
    <tr>

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td align="center" class="tdtablaanexa02">


     
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Nuevas Anotaciones </td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_agregar_items.gif" width="16" height="21"></td>
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
    <form action="revision.do" method="POST" name="REVISAR" id="REVISAR">
	<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">
    <table width="100%" class="camposform">
        <tr>
            <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <td width="150" align="center"><a href="javascript:cambiarAccion('<%=AWRevision.REGRESAR_REVISION%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente">
                    </a>
            </td>
			<td width="150" align="center">&nbsp;</td>
            <td width="150" align="center">&nbsp;</td>
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
    
    </tr>
    <tr>
        
        <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
        <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
        
    </tr>
    </table>
</body>
</html>
