<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%

	boolean esFolioMayorExtension=false;
	String vistaActual;



	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(AWConsulta.FOLIO_CALIFICACION);
	
    List foliosDerivadoHijos = (List) request.getSession().getAttribute(AWConsulta.FOLIOS_DERIVADO_HIJO);
    
    List foliosDerivadoPadre = (List) request.getSession().getAttribute(AWConsulta.FOLIOS_DERIVADO_PADRE);
    
    List historialAreas = (List) request.getSession().getAttribute(WebKeys.HISTORIAL_AREAS);
		
	//limpieza de valores del session
	request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

	Boolean temp = null;
        Object param_MayorExtensionFolio = session.getAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);

        if( (  null != param_MayorExtensionFolio )
         &&( param_MayorExtensionFolio instanceof java.lang.Boolean ) ) {

           // se hace esta conversion porque en
           // correcciones, la misma llave
           // se estaba usando para indicar un tipo
           // de folio especifico editado.

           temp = (Boolean)param_MayorExtensionFolio;
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

	//se mira si ya esta seteado llavesPaginador
	LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
	LLaveMostrarFolioHelper lla=null;
	if(llaves==null){
		//se crea el objeto llavesPaginador y settear sus valores;
		llaves= new LLavesMostrarFolioHelper();
		lla= new LLaveMostrarFolioHelper();
		lla.setNombrePaginador("NOMBRE_PAGINADOR_DETALLES");
		lla.setNombreResultado("NOMBRE_RESULTADO_DETALLES");
		lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_DETALLES");
		llaves.addLLave(lla);
		request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
	}
	if(lla==null){
		lla=llaves.getLLave("NOMBRE_RESULTADO_DETALLES", "NOMBRE_PAGINADOR_DETALLES");
	}


        // compatibilidad con correcciones
        // para que no aparezcan errores en funciones y clases js:

        String nombreFormaEdicionDatosEncabezado   = null;
        String nombreAcccionEdicionCodigoCatastral = null;
        String nombreFormaEdicionCodCatastral      = null;
        String nombreFormaEdicionLinderos          = null;
        String nombreFormaEdicionDireccion         = null;


        if( null != session.getAttribute( "CORRECCIONES:PARAMETERS" ) ) {
          nombreFormaEdicionDatosEncabezado   = (String)session.getAttribute( "NOMBREFORMAEDICIONDATOSENCABEZADO"   );
          nombreAcccionEdicionCodigoCatastral = (String)session.getAttribute( "NOMBREACCCIONEDICIONCODIGOCATASTRAL" );
          nombreFormaEdicionCodCatastral      = (String)session.getAttribute( "NOMBREFORMAEDICIONCODCATASTRAL"   );
          nombreFormaEdicionLinderos          = (String)session.getAttribute( "NOMBREFORMAEDICIONLINDEROS"   );
          nombreFormaEdicionDireccion         = (String)session.getAttribute( "NOMBREFORMAEDICIONDIRECCION"   );

        }



%>
<script>



</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Detalles del Folio</td>
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

    <%try{
   			mFolio.setFolio(f);
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


   			mFolio.setTitulo("Ver folio consulta");
   			mFolio.setMayorExtension(esFolioMayorExtension);
   			mFolio.setNombreAccionFolio("calificacion.do");
   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
   			mFolio.setNombreAncla("anclaConsulta");
   			mFolio.setNombreForma("PAGINADOR_ADENTRO_CONSULTA");
   			mFolio.setNombreFormaFolio("FORMA_FOLIO_CONSULTA");
   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO_CONSULTA");
   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CONSULTA");
   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES_CONSULTA");
   			mFolio.setNombreOcultarFolio("O_FOLIO_CONSULTA");
   			mFolio.setNombrePaginador(lla.getNombrePaginador());
   			mFolio.setNombreResultado(lla.getNombreResultado());
   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
   			mFolio.setPaginaInicial(0);
   			mFolio.setVistaActual(vistaActual);
   			mFolio.setTotalAnotaciones(totalAnotaciones);
   			mFolio.setGravamenes(gravamenes);
   			mFolio.setMedCautelares(medCautelares);
   			mFolio.setFalsaTradicion(falsaTradicion);
                        mFolio.setHistorialAreas(historialAreas);
   			mFolio.setAnotacionesInvalidas(anotacionesInvalidas);
   			//datos a mostrar encabezado
   			mFolio.MostrarAperturaFolio();
   			mFolio.MostrarCabidaLinderos();
   			mFolio.MostrarGravamenes();
   			mFolio.MostrarDireccionInmueble();
   			mFolio.MostrarComplementacion();
   			mFolio.MostrarDatosDocumentos();
   			mFolio.MostrarRelaciones();
   			mFolio.setConsultarAnotacionesDefinitivas(true);   
   			mFolio.setFoliosDerivadoPadre(foliosDerivadoPadre);			
		    mFolio.setFoliosDerivadoHijo(foliosDerivadoHijos);

                        // para que no aparezcan errores js.

                        if( null != session.getAttribute( "CORRECCIONES:PARAMETERS" ) ) {
                           mFolio.setNombreFormaEdicionDatosEncabezado( nombreFormaEdicionDatosEncabezado );
                           mFolio.setNombreAcccionEdicionCodigoCatastral( nombreAcccionEdicionCodigoCatastral );
                           mFolio.setNombreFormaEdicionCodCatastral( nombreFormaEdicionCodCatastral );
                           mFolio.setNombreFormaEdicionLinderos( nombreFormaEdicionLinderos );
                           mFolio.setNombreFormaEdicionDireccion( nombreFormaEdicionDireccion );
                        }

   			mFolio.render(request, out);
   }catch(HelperException re){
	 	out.println("ERROR " + re.getMessage());
	}%>

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>



	<!--Fila para colocar la opción de regresar-->
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
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">

	    <%Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
	    String titulo="Regresar a calificar folios";
		if(gov.sir.core.negocio.modelo.constantes.CFase.COR_CORRECCION_SIMPLE.equals(fase.getID())){
			titulo="Regresar a corregir folios";
		}%>

        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"><%=titulo%> </td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
	    <table width="100%" class="camposform">
	      <tr>
	        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	        <form action="consultas.do" method="POST" name="BUSCAR" id="BUSCAR">
			<%

				String consultaMatriz = (String)session.getAttribute( gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE);
				if(consultaMatriz!=null && consultaMatriz.equals(gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE)){
			%>
	        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%= AWConsulta.ACEPTAR_CONSULTA_CALIFICACION_FOLIO %>">
			<%
				}else{
			%>
			<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="<%= AWConsulta.ACEPTAR_CONSULTA_CALIFICACION %>">
			<%
				}
			%>

	        <td>
	        <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0">
	        </td>
	        </FORM>
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


    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>


    </tr>






  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
