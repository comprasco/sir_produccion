<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.web.helpers.comun.PaginadorAvanzado"%>
<%@page import="gov.sir.core.web.helpers.comun.PaginadorAvanzadoHelper"%>
<%@page import="java.util.*,gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.helpers.registro.MostrarAnotacionHelper"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstadoFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase"%>

<% 
	 
	//limpieza de valores del session
	request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

	boolean esFolioMayorExtension=false;
	String vistaActual;

	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
	boolean folioTemporal = false;
	boolean esRevisarAprobar = false;
	Boolean temp=(Boolean)session.getAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);	
	
	List foliosDerivadoHijos = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO);
    
	List foliosDerivadoPadre = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE);
        
        List historialAreas = (List) request.getSession().getAttribute(WebKeys.HISTORIAL_AREAS);

	List turnosFolioTramite = (List) request.getSession().getAttribute(WebKeys.LISTA_TURNOS_FOLIO_TRAMITE);
	
	if(turno!=null){
		if(turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)){
			esRevisarAprobar = true;
		}
	}

	if(temp!=null){
		esFolioMayorExtension = temp.booleanValue();	
	}
	
	//inicializacion Vista generadora
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	//TFS 3787: CUANDO HAY ERROR DEL FOLIO TEMPORAL, NO SE SETEA BIEN LA DIRECCION DE REGRESO
	if(request.getSession().getAttribute("folioPosibleTemporal")!=null){
		Folio folio = (Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		if(!folio.isDefinitivo()){
			session.setAttribute(SMARTKeys.VISTA_ACTUAL,request.getSession().getAttribute("VISTA_VOLVER"));
			request.getSession().removeAttribute("folioPosibleTemporal");
			folioTemporal = true;
		}
	}
	
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;
	
	java.math.BigDecimal totalAnotaciones = (java.math.BigDecimal) request.getSession().getAttribute(WebKeys.TOTAL_ANOTACIONES);          	 	
	List gravamenes = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);          	 	
	List medCautelares = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES); 
	List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
	List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);	
	List anotacionesPatrimonioFamiliar = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR);	
	List anotacionesAfectacionVivienda = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA);	
	
	//inicializacion nombre de paginadores y resultados
	String nombrePaginador="NOMBRE_PAGINADOR_DETALLES";
    String nombreResultado="NOMBRE_RESULTADO_DETALLES";
    

	//si va a ver datosRelevantes folio
	Turno turnoTramite=null;
	turnoTramite= (Turno)session.getAttribute(WebKeys.TURNO_TRAMITE);
	
	Usuario usuarioBloqueo= null;
	usuarioBloqueo= (Usuario) session.getAttribute(WebKeys.USUARIO_BLOQUEO);
	
	Turno turnoDeuda=null;
	turnoDeuda= (Turno)session.getAttribute(WebKeys.TURNO_DEUDA);

        /*
         * @author      :  Julio Alcázar Rivas
         * @change      :  Variables para manejar la nueva informacion de Ver detalles 
         * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
        Traslado trasladoOrigen  = (Traslado) session.getAttribute(WebKeys.TRASLADO_ORIGEN);
        Traslado trasladoDestino = (Traslado) session.getAttribute(WebKeys.TRASLADO_DESTINO);
        Circulo circuloOrigen = (Circulo) session.getAttribute(WebKeys.CIRCULO_ORIGEN);
        Circulo circuloDestino = (Circulo) session.getAttribute(WebKeys.CIRCULO_DESTINO);    
    
%>
<script>
/*
function cambiarAccion(text) {
       document.CALIFICAR.ACCION.value = text;
       document.CALIFICAR.submit();
       }
*/
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
   			
   			
   			mFolio.soloConsulta();
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
   			mFolio.setnombreNumPaginaActual("NUM_PAGINA_ACTUAL_CONSULTA");
   			mFolio.setNombrePaginador("NOMBRE_PAGINADOR_CONSULTA");
   			mFolio.setNombreResultado("NOMBRE_RESULTADO_CONSULTA");
   			mFolio.setPaginaInicial(0);
   			mFolio.setVistaActual(vistaActual);
   			mFolio.setTotalAnotaciones(totalAnotaciones);
   			mFolio.setGravamenes(gravamenes);
   			mFolio.setMedCautelares(medCautelares);
   			mFolio.setFalsaTradicion(falsaTradicion);
   			mFolio.setAnotacionesInvalidas(anotacionesInvalidas);
   			mFolio.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
   			mFolio.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
   			//datos a mostrar encabezado
   			mFolio.MostrarAperturaFolio();
   			mFolio.MostrarCabidaLinderos();
   			mFolio.MostrarMayorExtension();
			mFolio.MostrarAlertaInconsistencia();
   			
  			mFolio.setUsuarioBloqueo(usuarioBloqueo);
   			mFolio.setTurnoDeuda(turnoDeuda);
   			mFolio.setTurnoTramite(turnoTramite);
   			mFolio.MostrarDatosRelevantes();
   			
   			mFolio.MostrarGravamenes();
   			/**
   			 * @author: Cesar Ramirez
   			 * @change: 1245.HABILITAR.TIPO.PREDIO
   			 * Dibuja y muestra el campo de tipo del predio en la sección de la dirección del inmueble.
   			 **/
   			mFolio.MostrarTipoPredioEnDireccionInmueble();
   			mFolio.MostrarDireccionInmueble();
   			mFolio.MostrarComplementacion();
   			mFolio.MostrarDatosDocumentos();
   			mFolio.MostrarRelaciones();
   			mFolio.setMostrarHistorialEstados(false);
   			if (session.getAttribute("OCULTAR_COMENTARIO_ESTADO") == null)
   			{
   				session.setAttribute("OCULTAR_COMENTARIO_ESTADO", "FALSE");
   			}   
			if(esRevisarAprobar && folioTemporal){
				mFolio.setConsultarAnotacionesDefinitivas(false);
			}else{
   				mFolio.setConsultarAnotacionesDefinitivas(true);
   			}
   			
   			mFolio.setFoliosDerivadoPadre(foliosDerivadoPadre);			
		    mFolio.setFoliosDerivadoHijo(foliosDerivadoHijos);
                    mFolio.setHistorialAreas(historialAreas);
		    mFolio.setTurnosTramiteFolio(turnosFolioTramite);

                    /*
                     * @author      :  Julio Alcázar Rivas
                     * @change      :  Variables para manejar la nueva informacion de Ver detalles 
                     * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                     */
                    mFolio.setTrasladoDestino(trasladoDestino);
                    mFolio.setTrasladoOrigen(trasladoOrigen);
                    mFolio.setCirculoDestino(circuloDestino);
                    mFolio.setCirculoOrigen(circuloOrigen);
		    
   			//mFolio.setMostrarTurnos(true);
   			mFolio.render(request, out);
   			
   			
   			
		   			
		   			   			
   }catch(HelperException re){
	 	out.println("ERROR " + re.getMessage());
	}%>
	
    
    
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
