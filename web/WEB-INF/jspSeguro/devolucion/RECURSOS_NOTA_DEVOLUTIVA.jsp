<%@page import="gov.sir.core.util.ContainerUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="java.util.Date"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="gov.sir.core.util.DateFormatUtil;"%>

<%
        boolean isTestamento = false;
        boolean isparcial = false;
        boolean isreproduccion = false;
        boolean recursoFavorable = false; 
        
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
    
    //ver is la oficina es internacional o naciol
				String nomOficinaIntr="";
				String departamento="&nbsp;";
				String municipio="&nbsp;";
				String vereda="&nbsp;";
				String tipoOficina="&nbsp;";
				String numOficina="&nbsp;";
				boolean esInternacional=false;
				boolean esComentarioDocumento=false;				

				if(solicitud.getDocumento().getComentario()!=null){
					nomOficinaIntr=solicitud.getDocumento().getComentario();
					esComentarioDocumento=true;
				}else if(solicitud.getDocumento().getOficinaInternacional()!=null){
					nomOficinaIntr=solicitud.getDocumento().getOficinaInternacional();
					esInternacional=true;
				}
				else{

					//DEPARTAMENTO, MUNICIPIO, VEREDA
					if (solicitud.getDocumento().getOficinaOrigen()!=null){
						if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
							Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
							departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
							municipio=auxVereda.getMunicipio().getNombre();
							vereda=auxVereda.getNombre();
						}
	
						//TIPO OFICINA
	
						if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
							tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicitud.getDocumento().getOficinaOrigen().getNombre();

						//NUM OFICINA

						if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
							numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
					}
				}

	/** 
	 * @author Cesar Ramirez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Se trae la informacion de testamento para visualizarlo en el rol registrador
	 **/
	Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
	if(testamento!= null) {
		request.getSession().setAttribute(CTestamentos.TOMO,testamento.getTomo());	
		request.getSession().setAttribute(CTestamentos.NUMERO_ANOTACIONES,testamento.getNumeroAnotaciones());
		request.getSession().setAttribute(CTestamentos.NUMERO_COPIAS,testamento.getNumeroCopias());
		request.getSession().setAttribute(CTestamentos.REVOCA_ESCRITURA,testamento.getRevocaEscritura());
		request.getSession().setAttribute(CTestamentos.OBSERVACION,testamento.getObservacion());
	}

	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper(); 
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);	
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}
	
	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);	
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}
	
		String ocultarAnotaciones = request.getParameter(WebKeys.OCULTAR_ANOTACIONES);	
	if(ocultarAnotaciones == null){
		ocultarAnotaciones = (String)session.getAttribute(WebKeys.OCULTAR_ANOTACIONES);
		if(ocultarAnotaciones==null){
			ocultarAnotaciones = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,ocultarAnotaciones);
	}
        
        List historia= new Vector();
	historia=turno.getHistorials();
	
	if(historia != null){
	} else {
		historia = new Vector();
	}
	
	Iterator ih= historia.iterator();
	for(;ih.hasNext();){
		TurnoHistoria temp= (TurnoHistoria)ih.next();
		if(temp.getFase() != null && temp.getFase().equals(gov.sir.core.web.acciones.registro.AWMesa.REG_TESTAMENTO)){
			isTestamento = true;
		}
                /**
                 * @author: Fernando Padilla
                 * @change: Mantis 5643: Acta - Requerimiento No 211 - Opcion boton devolver calificacion
                 *          Se verifica si existe en la historia la fase CAL_CALIFICACION para poner en false
                 *          la variable isTestamento.       
                */
                else if(temp.getFase() != null && temp.getFase().equals(gov.sir.core.web.acciones.registro.AWMesa.CAL_CALIFICACION)){
			isTestamento = false;
		}
	}		
	
	
	LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
	List liquidaciones = solicitud.getLiquidaciones();
	for(int i=0;i<liquidaciones.size();i++){
		double id = new Double(((LiquidacionTurnoRegistro)liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
		if(id==solicitud.getLastIdLiquidacion()){
			liquidacion = (LiquidacionTurnoRegistro)liquidaciones.get(i);
		}
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();
	
	if(ciudadano.getTipoDoc()==null)
		ciudadano.setTipoDoc("");
	if(ciudadano.getDocumento()==null)
		ciudadano.setDocumento("");
	if(ciudadano.getApellido1()==null)
		ciudadano.setApellido1("");
	if(ciudadano.getApellido2()==null)
		ciudadano.setApellido2("");
	if(ciudadano.getNombre()==null)
		ciudadano.setNombre("");
        
    Date today;
    String fechaAct;
    today = new Date();
    fechaAct = DateFormatUtil.format(today);
    
    String vistaAnterior = (String) session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
    vistaAnterior = vistaAnterior != null
            ? "javascript:window.location.href='fases.view';"
            : "javascript:history.back();";

    
    String tCheck = (String) session.getAttribute(AWDevolucion.CHECK_RECURSO_FAVORABLE);
    session.setAttribute(WebKeys.TURNO_DEVOLUCION, turno);
// SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
//Ciudadano ciudadano = solicitud.getCiudadano();
//    List consignaciones = solicitud.getConsignaciones();
//    List solicitantes = solicitud.getSolicitantes();

    TextAreaHelper area = new TextAreaHelper();
    TextAreaHelper descripcion = new TextAreaHelper();
//   session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/ solicitud.getDescripcion());
    area.setCols("90");
    area.setRows("10");
    NotasInformativasHelper helper = new NotasInformativasHelper();

// turno y solicitud asociados
    Turno local_TurnoAnterior;
    Solicitud local_SolicitudAnterior = null;
    String certAsociado = "0";
    String proceso = "0";
    
    List listaTodosRecursos = new ArrayList();

        if (turno != null) { 

            List listaHistorials = turno.getHistorials();
            if (listaHistorials != null) {

                for (int k = 0; k < listaHistorials.size(); k++) {
                    TurnoHistoria turnoH = (TurnoHistoria) listaHistorials.get(k);

                    if (turnoH != null) {
                        List listaRecTurno = turnoH.getRecursos();

                        if (listaRecTurno != null) {
                            for (int t = 0; t < listaRecTurno.size(); t++) {
                                Recurso rec = (Recurso) listaRecTurno.get(t);
                                if (rec != null) {
                                    listaTodosRecursos.add(rec);
                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
        
    boolean dontExpires = false;
   try{
       HermodService hs = HermodService.getInstance();
       Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
    if(!listaTodosRecursos.isEmpty()){
       Iterator itAlert = listaTodosRecursos.iterator();
       while(itAlert.hasNext()){
           Recurso resource = (Recurso) itAlert.next();
           if(resource != null && 
                   resource.getIdTurnoHistoria().equals(String.valueOf(turn.getLastIdHistoria())) &&
                   (resource.getTitulo().equals(CDevoluciones.REC_APEL) || resource.getTitulo().equals("RECURSO DE REPOSICION DE SUBSIDIO DE APELACION"))){ 
               dontExpires = true;
           } 
       }
    
    }
   } catch(HermodException he){
       System.out.println("ERROR: " + he.getMessage());
   }
    
    String editar = (String) session.getAttribute(CDevoluciones.EDITAR_RECURSO);
    boolean edit = false;
    if(editar != null && editar.equals("TRUE")){
        edit = true;
    } else{
        edit = false;
    }
    
    List tipoRecurso = null;
    List resolucionesA = null; 
        List tiposRec = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            TurnoPk tid = new TurnoPk();
            tid.anio = turno.getAnio();
            tid.idCirculo = turno.getIdCirculo();
            tid.idProceso = turno.getIdProceso();
            tid.idTurno = turno.getIdTurno(); 
            resolucionesA = hs.getOficiosTurno(tid);
            
            tipoRecurso = hs.getValorLookupCodesByTipo(AWDevolucion.TIPO_RECURSOS);

            if(tipoRecurso == null){
                tipoRecurso = new ArrayList();
            }
        Iterator itTipo = tipoRecurso.iterator(); 
        while(itTipo.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itTipo.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            tiposRec.add(el);
        }

        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        }
        
    ListaElementoHelper tiposRecHelper = new ListaElementoHelper(); 
    if(tiposRec == null){
            tiposRec = new ArrayList();
    }
    tiposRecHelper.setOrdenar(false);
    tiposRecHelper.setTipos(tiposRec);
    tiposRecHelper.setCssClase("camposformtext");

    local_TurnoAnterior = turno.getSolicitud().getTurnoAnterior();
    if (local_TurnoAnterior != null) {
        local_SolicitudAnterior = local_TurnoAnterior.getSolicitud();
        proceso = Long.toString(local_TurnoAnterior.getIdProceso());
        if (local_TurnoAnterior.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO) && local_SolicitudAnterior.getSolicitudesHijas().size() > 0) {
            certAsociado = "1";
        }
    }
    
   //ver si se necesita carga
	Boolean oCarga= (Boolean)session.getAttribute("CARGAR_RECURSOS_NOTA");
	boolean carga=true;
	if(oCarga!=null){
		carga=oCarga.booleanValue();
	}
        
	Boolean oIsparcial= (Boolean)session.getAttribute("ISPARCIAL");
	if(oIsparcial!=null){
		isparcial=oIsparcial.booleanValue();
	}
        Boolean oReproduccion= (Boolean)session.getAttribute("ISREPRODUCCION");
	if(oReproduccion!=null){
		isreproduccion=oReproduccion.booleanValue();
	}

%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">

    function cambiarAccion(accion) {
        document.RESOLUCION.<%=WebKeys.ACCION%>.value = accion;
        document.RESOLUCION.submit();
    }

    function cambiarAccion1(accion) {
        document.AGREGAR.<%=WebKeys.ACCION%>.value = accion;
        document.AGREGAR.submit();
    }
    
    function procedeRecurso(accion,secuencia){
        document.AGREGAR.<%=WebKeys.ACCION%>.value = accion;
        document.getElementById('<%=AWDevolucion.SECUENCIA%>').value = secuencia;
        document.AGREGAR.submit();
    }
    
    function alertaVencimiento(text){
        alert(text);
    }

    function ampliarRecurso(accion, pos) {
        document.RECURSOS.POSICION.value = pos;
        document.RECURSOS.ACCION.value = accion;
        document.RECURSOS.submit();
    }
    function verAnotacion(nombre,valor,dimensiones,pos)
    {
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
    }
    
    function eliminarRecurso(accion, pos){
        if (confirm("El recurso se eliminara, ¿Desea continuar?")) {
            ampliarRecurso(accion, pos);
          }
    }


</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    
    <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
  
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    
    
    
    <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table border="0" align="right" cellpadding="0" cellspacing="2">
        <tr>
					<%if(ocultarTurno.equals("FALSE")){%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
            <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
        </form>
					
					<%}else{%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
            <td width="170" class="contenido">Haga click para maximizar</td>
            <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
        </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
         
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          

    <table width="100%" class="camposform">
        <tr>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=turno.getIdWorkflow()%></td>
        </tr>
    </table>
          
           <%if(ocultarTurno.equals("FALSE")){%>
           
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnsub">Datos Turno</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1"  src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Certificado Anterior</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>N&uacute;mero del Turno Anterior</td>
            <td class="campositem">
            <%if (turnoAnterior!=null){
            %><%=turnoAnterior.getIdWorkflow()%>
            <%}
            else {
            out.write("No hay turnos anteriores");
            }%></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Matr&iacute;cula Inmobiliaria de la Propiedad</td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
					
					<%List solFolios = solicitud.getSolicitudFolios();
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()){
						while(itSolFolios.hasNext()){
							SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=((Folio)solFolio.getFolio()).getIdMatricula()%></td>
        </tr>
							
						<% }%>
					<% }else{%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem">No hay elementos que mostrar</td>
        </tr>
					<%}%>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>	
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Adicionales </td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
            <%
       		try{
      			MASHelper.setMostrarDocumento(true);
      			MASHelper.render(request,out);
      		}catch(HelperException re)
		    {
				re.printStackTrace();
				out.println("ERROR " + re.getMessage());
			}
        %>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
    	<%
    		String tipoDoc="&nbsp;";
			String numDoc="&nbsp;";
			String apellido1="&nbsp;";
			String apellido2="&nbsp;";
			String nombre="&nbsp;";
    		if(ciudadano!=null){
    			
    			if(ciudadano.getTipoDoc()!=null && !ciudadano.getTipoDoc().equals("")){
    				tipoDoc=ciudadano.getTipoDoc();
    			}
    			if(ciudadano.getDocumento()!=null && !ciudadano.getDocumento().equals("")){
    				numDoc=ciudadano.getDocumento();
    			}
    			if(ciudadano.getApellido1()!=null && !ciudadano.getApellido1().equals("")){
    				apellido1=ciudadano.getApellido1();
    			}
    			if(ciudadano.getApellido2()!=null && !ciudadano.getApellido2().equals("")){
    				apellido2=ciudadano.getApellido2();
    			}
    			if(ciudadano.getNombre()!=null && !ciudadano.getNombre().equals("")){
    				nombre=ciudadano.getNombre();
    			}
    		}
    	%>
    
        <tr> 
            <td width="179">Tipo de Identificaci&oacute;n</td>
            <td width="211" class="campositem"><%=tipoDoc%></td>
            <td width="146">N&uacute;mero</td>
            <td width="212" class="campositem"><%=numDoc%></td>
        </tr>
        <tr> 
            <td>Primer Apellido</td>
            <td class="campositem"><%=apellido1%></td>
            <td>Segundo Apellido</td>
            <td class="campositem"><%=apellido2%></td>
        </tr>
        <tr> 
            <td>Nombre</td>
            <td class="campositem"><%=nombre%></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
			
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Liquidaci&oacute;n</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="293">Valor</td>
            <td width="11">$</td>
            <td width="445" class="campositem"><%=liquidacion.getValor()%></td>
        </tr>
    </table>
    <br>
    <hr class="linehorizontal">
       <%}%>
    </td>
		
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>
</table>


    <table border="0" cellpadding="0" cellspacing="0" width="100%">
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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folios</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td>
          <img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table border="0" align="right" cellpadding="0" cellspacing="2">
             <tr>
					<%if(ocultarFolio.equals("FALSE")){%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
					<%}else{%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

    </table>
        <%if(ocultarFolio.equals("FALSE")){%>
          
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub"><p>Listado de Folios del Turno</p></td>
   <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
	<form name="CALIFICACION" method="post" action="calificacion.do">
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
    <%
		Iterator itSolFolios = (solicitud.getSolicitudFolios()).iterator();
		int i = 0;
    	while(itSolFolios.hasNext()){%>
	    <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=((Folio)((SolicitudFolio)itSolFolios.next()).getFolio()).getIdMatricula()%></td>
		<td width="40" align="center"><a href="javascript:verAnotacion('consultar.folio.do?<%="POSICION" + "=" + i%>','Folio','width=900,height=450,scrollbars=yes','<%=i%>')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif border="0" width="35" height="13"></a></td>
		<input type="hidden" name="POSICION" value="<%=i%>">
        </tr>
  	 <%i++;
    	}%>
    </table>

		<!--
			@author Cesar Ramirez
			@change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
		-->
		<!-- Inicio: Datos del Testeador -->
		<%
		if(isTestamento) {
		%>
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Testadores</td>
				<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>
		<table width="100%" border="0" class="camposform">
			<tr>
			<%
			if (testamento != null && testamento.getTestadores() != null && testamento.getTestadores().size() > 0) {
				Ciudadano ciud;
				TestamentoCiudadano testamentoCiudadano;
				String ciudadanoMostrar = null;
				String docIdentidad;
				for (int ic = 0; ic < testamento.getTestadores().size(); ic++) {
					ciudadanoMostrar = "";
					docIdentidad = "";
					testamentoCiudadano = (TestamentoCiudadano) testamento.getTestadores().get(ic);
					ciud = testamentoCiudadano.getCiudadano();
					if(ciud != null) {
						if(ciud.getApellido1() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getApellido1() + " ";
						if(ciud.getApellido2() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getApellido2() + " ";
						if(ciud.getNombre() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getNombre();
						if (ciud.getTipoDoc() != null && !ciud.getTipoDoc().equals("") && !ciud.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
							docIdentidad = ciud.getTipoDoc();
							docIdentidad += "   " + ciud.getDocumento();
						}
					}
			%>
			<tr>
				<td width="20\"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"width="20" height="15"></td>
				<td class="titulotbcentral"><%=ciudadanoMostrar%></td>
				<td class="titulotbcentral"><%=docIdentidad%></td>
			</tr>
			<%
				}
			}
			%>
			</tr>
		</table>
		<!-- Fin: Datos del Testeador -->
		<!-- Inicio: Datos del Registro de Testamentos -->
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Registro de Testamentos</td>
				<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>
		<table width="100%" class="camposform">
			<tr>
				<td width="18%">Tomo:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {   
                                                TextHelper textHelper = new TextHelper(); 
						textHelper.setNombre(CTestamentos.TOMO);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.TOMO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%">N&uacute;mero Anotaciones:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {   
                                            TextHelper textHelper = new TextHelper();
						textHelper.setNombre(CTestamentos.NUMERO_ANOTACIONES);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.NUMERO_ANOTACIONES);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
                            <td width="18%">N&uacute;mero copias:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
                                            TextHelper textHelper = new TextHelper();
						textHelper.setNombre(CTestamentos.NUMERO_COPIAS);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.NUMERO_COPIAS);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%">Revoca Escritura:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
                                            TextHelper textHelper = new TextHelper();
						textHelper.setNombre(CTestamentos.REVOCA_ESCRITURA);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.REVOCA_ESCRITURA);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
                            <td width="18%" >Observaci&oacute;n:</td>
				<td align='left'>
				<%
				try {
					TextAreaHelper areaT = new TextAreaHelper();
					areaT.setCols("90");
					areaT.setRows("8");
					areaT.setNombre(CTestamentos.OBSERVACION);
					areaT.setCssClase("campositem");
					areaT.setId(CTestamentos.OBSERVACION);
					areaT.setReadOnly(true);
					areaT.render(request,out);
				} catch(HelperException re) {
					out.println("ERROR " + re.getMessage());
				}
				%>
				</td>
			</tr>
		</table>
		<%
		}
		%>
    </form>
    
    
    <hr class="linehorizontal">
		<%}%>
   <%if(isreproduccion){
    List repro =  (List) session.getAttribute("LISTISREPRO");
   %>  
        <br>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
         <tr>
         <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
         <td class="bgnsub"><p>Listado de Turnos en la Reproduccion de Sellos</p></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
         </tr>
         </table>
         <br>
            <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
         <%
                     Iterator Sellos = repro.iterator();
                     int i = 0;
             while(Sellos.hasNext()){
                 ReproduccionSellos r = (ReproduccionSellos) Sellos.next(); 
                 if(r.getOpcion() == 1){%>
                   <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
             <td>N&uacu&uacute;mero de Turno Asociado</td>
             <td class="campositem"><%=r.getCode()%></td>
             <td width="40" align="center"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RST=<%=r.getCode()%>&&ID=<%=r.getUsuariosir()%>','name','width=800,height=600')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif border="0" width="120" height="20"></a></td>
             <input type="hidden" name="POSICION" value="<%=i%>">
             </tr>  
              <%i++; }
             }%>
         </table>
         <br>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
         <tr>
         <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
         <td class="bgnsub"><p>Listado de Matriculas en la Reproduccion de Sellos</p></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
         </tr>
         </table>
         <br>
            <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
         <%
                     Iterator Sellos1 = repro.iterator();
                     int i1 = 0;
             while(Sellos1.hasNext()){
                 ReproduccionSellos r = (ReproduccionSellos) Sellos1.next();
                 if(r.getOpcion() == 2){%>
                   <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
             <td>N&uac&uacute;mero de Matricula Asociada</td>
             <td class="campositem"><%=r.getCode()%></td>
             <td width="40" align="center"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RSM=<%=r.getCode()%>&&ID=<%=r.getUsuariosir()%>&&TR=<%=r.getIdTurnoRaiz()%>&&desde=<%=r.getDesde()%>&&hasta=<%=r.getHasta()%>','name','width=800,height=600')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif border="0" width="120" height="20"></a></td>
             <input type="hidden" name="POSICION" value="<%=i1%>">
             </tr>  
              <%i1++; }
             }%>
         </table>
    <%}%>
    <hr class="linehorizontal">
    
    <form name="NOTIFICACION" method="post" action="devolucion.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>">    
    <%
        List notifications = null; 
        try{
            HermodService hs = HermodService.getInstance();
            notifications = hs.getNotaDevNotificada(turno.getIdWorkflow());
        } catch(HermodException he){
            System.out.println("ERROR: " + he);
        }
        
        if(notifications != null && !notifications.isEmpty()){
            %>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de Notificaciones</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
        </table>
    <%
            Iterator itNot = notifications.iterator();
            while(itNot.hasNext()){
                NotificacionNota notify = (NotificacionNota) itNot.next();
                if(notify != null){
                    boolean isNotifyUser = false;
                    boolean isNotifyOffice = false;
        
                String destiny = notify.getDestino();
                    if(destiny != null && !destiny.isEmpty()){
                        if(destiny.equals(CDevoluciones.USUARIO)){
                            isNotifyUser = true;
                            isNotifyOffice = false;
                        } else if (destiny.equals(CDevoluciones.OFICINA_ORIGEN)){ 
                            isNotifyUser = false;
                            isNotifyOffice = true; 
                        }
                    }
                
        
                boolean isUser = false;
                boolean isOffice = false;

                    if(isNotifyUser){
                        isUser = true;
                        isOffice = false; 
                    } else if(isNotifyOffice){
                        isUser = false;
                        isOffice = true; 
                    } else{
                        isUser = false;
                        isOffice = false;
                    }
            
                    String fechaNot = "&nbsp;";
                    destiny = (notify.getDestino()!=null?notify.getDestino():"&nbsp;"); 
                    String tipo = (notify.getTipo()!=null?notify.getTipo():"&nbsp;");
                    String correo = (notify.getCorreo()!=null?notify.getCorreo():"&nbsp;"); 
                    String apoderado = String.valueOf(notify.getApoderado()); 
                    String nombres = (notify.getNombres()!=null?notify.getNombres():"&nbsp;");
                    String apellidos = (notify.getApellidos()!=null?notify.getApellidos():"&nbsp;");
                    String tipoDocumento = (notify.getTipoDocumento()!=null?notify.getTipoDocumento():"&nbsp;");
                    String numDocumento = (notify.getDocumento()!=null?notify.getDocumento():"&nbsp;");
                    String direccion = (notify.getDireccion()!=null?notify.getDireccion():"&nbsp;");
                    String telefono = (notify.getTelefono()!=null?notify.getTelefono():"&nbsp;");


                    if(notify.getFechaNotificacion() != null){
                        Date date = notify.getFechaNotificacion();
                        String dateFormat = "dd/MM/yyyy";
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        fechaNot = formatter.format(notify.getFechaNotificacion());
                    }

                    boolean isEmail = false;
                    if(notify.getCorreo() != null && !notify.getCorreo().isEmpty()){
                            isEmail = true; 
                    }
        
                    %>

    <table width="100%" class="camposform">
        <tr>
            <td>
                <table class="camposformnoborder">
            <tr>
                <td width="200" nowrap>Fecha de Notificaci&oacute;n: </td>
                <td>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                        <td width="150" class="campositem">
                    <%= fechaNot %>
                    </td>
                    </tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td>Destinatario: </td>
                <td width="150" class="campositem">
                    <%= destiny %>
                </td>             
            </tr>

            <tr>
                <td>Tipo de Notificaci&oacute;n: </td>
                <td width="150" class="campositem">
                    <%= tipo %>
                </td>
              <%if(isEmail){%>
                                    <td width="150">Correo Electr&oacute;nico: </td>
                                    <td width="150" class="campositem">
                                        <%= correo %>
                                    </td>                   
              <%}%>
                                    
                 </tr>
                 
                 <% if(isUser){%>
                 <tr>
                     <td>Apoderado: </td>
                     <td width="150" class="campositem">
                         <%if(apoderado.equals("0")){%>
                         No
                         <%} else{%>
                         Si
                         <%}%>
                    </td> 
                 </tr>
                 
                 <tr>
                     <td>Nombre: </td>
                     <td width="150" class="campositem"><%= nombres
                         %>
                     </td>
                 </tr>
                 
                 <tr>
                     <td>Apellidos: </td>
                     <td width="150" class="campositem"><%= apellidos%>
                     </td>
                 </tr>
                 
                 <tr>
                     <td>Tipo de Documento: </td>
                     <td width="150" class="campositem"><%=tipoDocumento%></td>    

                     <td width="150">Numero de Documento: </td>
                     <td width="150"class="campositem"><%= numDocumento%></td>     
                 </tr>
                 
                 <%} else if(isOffice){%> 
                   
                 <tr>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  
                  <td colspan="2">
                  <table width="100%" class="camposform">
					<%if(esComentarioDocumento){%>
					 <tr>
                        <td>Oficina Origen</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%>
                        </td>
                      </tr>
                     <%}else if(!esInternacional){%>
                      <tr>
	                  <td width="12%">
		                   <DIV align="right">Codigo</DIV>
		              </td>
		              <td class="campositem" width="32%">
						<%=numOficina%>
	                  </td>
	                  <td width="15%">
	                    <DIV align="right">Nombre</DIV>
	                  </td>
	                  <td class="campositem" width="36%">
					    <%=tipoOficina%>
	                  </td>
		            </tr>

	                 <tr>
	                    <td width="12%">
	                      <DIV align="right">Departamento</DIV>
	                    </td>
	                    <td class="campositem" width="32%">
						<%=departamento%>
	                    </td>
	                    <td width="15%">
	                      <DIV align="right">Municipio</DIV>
	                    </td>
	                    <td class="campositem" width="36%">
							<%=municipio%>
	                    </td>
	                 </tr>
					<%}else{%>
					 <tr>
                        <td>Oficina internacional</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%>
                        </td>
                      </tr>
                     <%}%>
                    </table></td>
                </tr>

                    
                 <%}%>
                 
                 <tr>
                     <td>Direcci&oacute;n: </td>
                     <td width="150" class="campositem"><%= direccion%></td>    
                 </tr>
                 
                 <tr>
                     <td>Telefono: </td>
                     <td width="150" class="campositem"><%= telefono
                     %></td>    
                 </tr>
                
            </table>
            </td>
        </tr>
    </table>
    <%
                }
            }
        }
    %>
    </form>           
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>       
        
        <!-- hasta aqui-->
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
        <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Interposici&oacute;n de Recursos PROCESO DE DEVOLUCIONES</td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                </tr>
            </table></td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Procede Recursos Devoluci&oacute;n</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <br>
                        <br>

<form name="AGREGAR" method="post" action="devolucion.do">
    <input type="hidden" id="<%=AWDevolucion.SECUENCIA%>" name="<%=AWDevolucion.SECUENCIA%>">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->    
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Agregar Informaci&oacute;n Recursos</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>

            <td>Tipo de Recurso</td>
            <td>
                <%
                       try{
                        tiposRecHelper.setCssClase("camposformtext");
                        tiposRecHelper.setNombre(AWDevolucion.TIPO_RECURSO);
                        tiposRecHelper.setId(AWDevolucion.TIPO_RECURSO);
                        tiposRecHelper.render(request,out);  
                 } catch(HelperException he){
                     out.println("ERROR " + he.getMessage());
                 }   
            %>
            </td>

        </tr>
        <tr>
            <td>Usuario Recurso</td>
            <td>
                <% try {
                        TextHelper textHelper = new TextHelper();
                        textHelper.setSize("70");
                        textHelper.setNombre("USUARIO_RECURSO");
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId("USUARIO_RECURSO");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>

        </tr>

        <tr>

            <td>Observaci&oacute;n Recurso</td>
            <td>
                <% try {
                        descripcion.setNombre("DESCRIPCION_RECURSO");
                        descripcion.setCssClase("camposformtext");
                        descripcion.setId("DESCRIPCION_RECURSO");
                        descripcion.setCols("70");
                        descripcion.setRows("8");
                        descripcion.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>




            </td>

        </tr>
        <tr>
            <td width="146">Fecha de Recurso</td>
            <td width="200">
                <%
                    try {

                        session.setAttribute(AWDevolucion.FECHA_RECURSO, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_RECURSO);
                        textHelper.setNombre(AWDevolucion.FECHA_RECURSO);
                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+AWDevolucion.FECHA_RECURSO+"');\" "
                                                    + " onChange=\"fixDate('"+AWDevolucion.FECHA_RECURSO+"')\"   onBlur=\"javascript:validarFecha()\"" );
                            textHelper.setCssClase("camposformtext");
                            textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_RECURSO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
        </tr>

        <tr>

        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA%>">
        <td width="185"><a href="javascript:cambiarAccion1('AGREGAR_RECURSO_NOTA_DEVOLUTIVA')"> <img src="<%=request.getContextPath()%>/jsp/images/btn_agregar_recurso.gif" border="0"></a> </td>
        </tr> 
    </table>   
</FORM>




<br>    
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
        <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de Recursos del Turno</td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
        <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>
<br>

<% //Obtener los recursos del turno.
    %>
<form name="RECURSOS" method="post" action="devolucion.do">
    <input type="hidden" name="POSICION" id="POSICION" />
    <input type="hidden" name="ACCION" id="ACCION" />
    <% 
        if(!listaTodosRecursos.isEmpty()){
            Iterator itRec = listaTodosRecursos.iterator();
            int sec = 0;
            while(itRec.hasNext()){
                Recurso rec = (Recurso) itRec.next();
                String titulo = rec.getTitulo();
                Iterator itTip = tiposRec.iterator();
                while(itTip.hasNext()){
                    ElementoLista el = (ElementoLista) itTip.next();
                    if(el.getValor().equals(titulo)){
                        titulo = el.getId();
                        break;
                    }
                }
                request.getSession().setAttribute("TIPO_" + String.valueOf(sec), titulo);
                sec++;
            }
        }
        
        for (int s = 0; s < listaTodosRecursos.size(); s++) {

            Recurso auxRec = (Recurso) listaTodosRecursos.get(s);
            String idTitulo = "";
            String titulo = "";
            
            Iterator itRec = tiposRec.iterator();
            while(itRec.hasNext()){
                ElementoLista el = (ElementoLista) itRec.next();
                if(el.getId().equals(auxRec.getTitulo())){
                    idTitulo = auxRec.getTitulo(); 
                    auxRec.setTitulo(el.getValor());
                }
            }
            
            String alertRec = null;
            boolean hasAlert = false; 
            
            List tempRes = null;
            
            try{
                HermodService hs = HermodService.getInstance();
                TurnoPk tid = new TurnoPk();
                tid.anio = turno.getAnio();
                tid.idCirculo = turno.getIdCirculo();
                tid.idProceso = turno.getIdProceso();
                tid.idTurno = turno.getIdTurno();
                tempRes = hs.getOficiosTurno(tid);
            } catch (HermodException e) {
                System.out.println("ERROR: "+ e);
            }
                    
            List resourcesAlerts = (List) session.getAttribute(CDevoluciones.ALERTA_VENCIMIENTO);
                if(resourcesAlerts != null && !resourcesAlerts.isEmpty()){
                    Iterator itAlert = resourcesAlerts.iterator();
                    while(itAlert.hasNext()){
                        ContainerUtil notAlert = (ContainerUtil) itAlert.next();
                        if(notAlert != null){
                            String idNot = notAlert.getIdRecurso();
                            String alert = notAlert.getAlerta();

                            if(idNot != null && idNot.equals(auxRec.getIdRecurso())){ 
                                hasAlert = true;
                                alertRec = alert;
                            } 
                        }
                    }
                }
                
                    
            if (auxRec != null) {
              if((auxRec.getTitulo().equals("RECURSO DE REPOSICION DE SUBSIDIO DE APELACION") || auxRec.getTitulo().equals(CDevoluciones.REC_APEL)) && ((tempRes != null && !tempRes.isEmpty()))){
                   hasAlert = false; 
               }
        %>
    <br>
    <table width="100%" class="camposform">
        <tr>
            <td colspan="4">
                <%if(hasAlert){%>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td width="5">&nbsp;</td>
                        <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Alertas</td>
                              <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_alerta.gif" width="20" height="20"></td>
                            </tr>
                        </table></td>
                        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
                        <td class="tdtablaanexa02">
                            <br>
                            <p style="color:red;"><strong><%=alertRec%></strong></p>
                            </td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
                        <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
                    </tr>
            </table>
        <%}%>
            </td>
        </tr>
        <tr>
        <% 

            if((auxRec.getTitulo().equals("RECURSO DE REPOSICION DE SUBSIDIO DE APELACION") || auxRec.getTitulo().equals(CDevoluciones.REC_APEL)) && ((tempRes != null && !tempRes.isEmpty()))){
                session.setAttribute(AWDevolucion.RESOLUTION_ADDED, "TRUE");
           recursoFavorable = true;
        %>
            <td class="titresaltados" width="43%" colspan="3">&nbsp; El turno se encuentra en segunda instancia</td>
        <%} else{%>
            <td colspan="3">&nbsp;</td>
        <%}%>
        <td align="right">
                <a href="javascript:ampliarRecurso('<%=AWDevolucion.GUARDAR_RECURSO_NOTA_DEVOLUTIVA%>','<%=s%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_guardar.gif"  border="0"></a>
        </td>
        </tr>
        <tr>

            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td> 
            <td width="25%">Tipo de Recurso</td>
            <% titulo = (String) session.getAttribute("TIPO_" + s);%> 
            <%if(edit && !(titulo.equals(CDevoluciones.NO_PROCEDE_RECURSO) || titulo.equals("NO PROCEDE RECURSO"))){%> 
            <td>
                <% try {
                        tiposRecHelper.setCssClase("camposformtext");
                        tiposRecHelper.setNombre("TIPO_" + s);
                        tiposRecHelper.setId("TIPO_" + s);
                        tiposRecHelper.setSelected(titulo); 
                        tiposRecHelper.render(request,out);  
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <%} else{%>
            <td class="campositem" nowrap><%=auxRec.getTitulo()%></td>
            <%}%>
            
            <%if(recursoFavorable){%>
            <td width="40%">
                <table width="500" align="center" class="camposformdark">
                    <tr height="40%" align="center">
                        <td>Recurso Favorable: </td>
                        <td>
                            <%
                                recursoFavorable = false;
                                String favorableSi = "", favorableNo = "";
                                if(tCheck != null){
                                if (tCheck.equals(AWDevolucion.CHECKED_SI)) {
                                      favorableSi = "CHECKED";%>

                            <%} else {
                                    favorableNo = "CHECKED";%>

                            <%}
                                }
                            %>
                            <input type="radio" name="<%= AWDevolucion.CHECK_RECURSO_FAVORABLE%>" value="<%= AWDevolucion.CHECKED_SI%>" <%= favorableSi%> onClick="ampliarRecurso('<%=AWDevolucion.FAVORABLE%>','<%=s%>');"/> Favorable   
                            &nbsp;
                            <input type="radio" name="<%= AWDevolucion.CHECK_RECURSO_FAVORABLE%>" value="<%= AWDevolucion.CHECKED_NO%>" <%= favorableNo%> onClick="ampliarRecurso('<%=AWDevolucion.NO_FAVORABLE%>','<%=s%>');"/> No Favorable 
                        </td>
                    </tr>
                </table>
            </td>
            <%} else {%>
            <td width="40%">
                <table width="500" align="center" class="camposformdark">
                    <tr height="40%" align="center">
                        <td>Procede Recurso: </td>
                        <td>
                        <%
                            String tipoChecked = (String) session.getAttribute(AWDevolucion.CHECK_RECURSOS_NOT + s);
                            String checkedSi = "", checkedNo = "";
                            if(tipoChecked != null){
                            if (tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                                  checkedSi = "CHECKED";%>

                        <%} else {
                                checkedNo = "CHECKED";%>

                        <%}}
                        boolean noProc = false;
                        if(titulo.equals(CDevoluciones.NO_PROCEDE_RECURSO) || titulo.equals("NO PROCEDE RECURSO")){
                            noProc = true;
                            checkedSi = "";
                            checkedNo = "CHECKED";
                        }

                        if(!noProc){
                        %>
                        <input type="radio" name="<%= AWDevolucion.CHECK_RECURSOS_NOT + s%>" value="<%= AWDevolucion.CHECKED_SI%>" <%= checkedSi%> onClick="procedeRecurso('<%=AWDevolucion.RECURSOS_NOT%>','<%=s%>');"/> Si   
                        &nbsp;
                        <%}%>
                        <input type="radio" name="<%= AWDevolucion.CHECK_RECURSOS_NOT + s%>" value="<%= AWDevolucion.CHECKED_NO%>" <%= checkedNo%> onClick="procedeRecurso('<%=AWDevolucion.NO_RECURSOS_NOT%>',<%=s%>);"/> No
                        </td>
                    </tr>
                </table>
            </td>
            <%}%>
        </tr>

        <tr>

            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td> 
            <td width="25%">Fecha Recurso</td>
            <% String fechaFormateada = "";
                if (auxRec.getFecha() != null) {
                    fechaFormateada = FechaConFormato.formatear(auxRec.getFecha(), "dd/MM/yyyy");
                    }%>
            <%if(edit){%>
            <td>
                <% session.setAttribute("FECHA_" + s, fechaFormateada);%>
                <% try {
                        TextHelper txtHelper  = new TextHelper(); 
                        txtHelper.setNombre("FECHA_" + s);
                        txtHelper.setCssClase("camposformtext");
                        txtHelper.setId("FECHA_" + s);
                        String idH = "FECHA_" + s; 
                        txtHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+idH+"');\" "
                                                    + " onChange=\"fixDate('"+idH+"')\"   onBlur=\"javascript:validarFecha()\"" ); 
                        txtHelper.render(request, out); 
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
                <a href="javascript:NewCal('<%="FECHA_" + s%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
            <%} else{%>
            <td width="211" class="campositem"><%=fechaFormateada%></td>
            <%}%>
            <td width="40%">&nbsp;</td>
        </tr>

        <tr>

            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td> 
            <td width="25%">Usuario Recurso</td>
            <%if(edit){%>
            <td>
                <% session.setAttribute("USUARIO_" + s, auxRec.getTextoUsuario());%>
                <% try {
                        TextHelper txtHelper  = new TextHelper(); 
                        txtHelper.setNombre("USUARIO_" + s);
                        txtHelper.setCssClase("camposformtext");
                        txtHelper.setId("USUARIO_" + s);
                        txtHelper.render(request, out); 

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <%} else{%>
            <td width="211" class="campositem"><%=auxRec.getTextoUsuario()%></td>
            <%}%>
            <td width="40%">&nbsp;</td>
        </tr>

        <tr>

            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td> 
            <td width="25%">Descripci&oacute;n Recurso</td>
            <%if(edit){%>
            <td>
                <% session.setAttribute("DESCRIPCION_" + s, auxRec.getTextoRecurso());%>
                <% try {
                        TextAreaHelper descripcionRecurso = new TextAreaHelper();
                        descripcionRecurso.setNombre("DESCRIPCION_" + s);
                        descripcionRecurso.setCssClase("camposformtext");
                        descripcionRecurso.setId("DESCRIPCION_" + s);
                        descripcionRecurso.setCols("70");
                        descripcionRecurso.setRows("4");
                        descripcionRecurso.render(request, out);

                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <%} else{%>
                <td width="211" class="campositem"><%=auxRec.getTextoRecurso()%></td>
            <%}%>
            <td width="40%">&nbsp;</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
        <table class="camposformnoborder">
            <tr>
                <td width="40%">
                <a href="javascript:ampliarRecurso('<%=AWDevolucion.EDITAR_RECURSO_NOTA_DEVOLUTIVA%>','<%=s%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_editar_on.gif" width="35" height="25" border="0"></a>
                </td>
                 <td width="40%">
                <a href="javascript:eliminarRecurso('<%=AWDevolucion.ELIMINAR_RECURSO_NOTA_DEVOLUTIVA%>','<%=s%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"></a>
                </td>
            </tr>
        </table>
            </td>
            <td width="40%">&nbsp;</td>
        </tr>
    </table>   

    <%}
                 }%>
</form>   
<br>

<br>
<form name="RESOLUCION" method="post" action="devolucion.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>" value = "<%=AWDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT%>">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Resoluciones </td>
            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>

    <% List listaRes = null;
       if(resolucionesA != null){ 
           listaRes = resolucionesA;
       } else{
           listaRes = (List) session.getAttribute("LISTA_RESOLUCIONES");
       }     
                    if (listaRes != null) { %>
    <br>
    <table width="100%" class="camposform">
        <%for (int i = 0; i < listaRes.size(); i++) {
                        Oficio of = (Oficio) listaRes.get(i);%>
        <tr>
            <td>
                <table width="100%" class="camposform">

                    <tr>
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td><input type="checkbox" name="<%=WebKeys.TITULO_CHECKBOX_ELIMINAR%>" value="<%=i%>"></td>
                        <td width="179">N&uacute;mero</td>
                        <td width="211" class="campositem"><%=of.getNumero() == null ? "&nbsp;" : of.getNumero()%></td>

                        <td width="146">Fecha</td>
                        <td width="200" class="campositem"><%
                            Date fechaOficio = of.getFechaCreacion();
                            String fechaDisplay = DateFormatUtil.format(fechaOficio);
                            %><%=fechaDisplay%></td>

                        <%if (of.getFechaFirma() != null) { %>
                        <td width="146">Fecha Ejecutor&iacute;a</td>
                        <td width="200" class="campositem"><%
                            Date fechaEjecutoria = of.getFechaFirma();
                            String fechaEjecutoriaDisplay = DateFormatUtil.format(fechaEjecutoria);
                            %><%=fechaEjecutoriaDisplay%>
                        </td>
                        <%} else { %>
                        <td width="146">&nbsp;</td>
                        <td width="200">&nbsp;</td>
                        <%} %>


                    </tr>
                </table>
            </td>
        </tr>
        <%	} %>
        <tr>
            <td>
                <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                    <tr>
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                        <td>Eliminar Seleccionadas</td>
                        <td><a href="javascript:cambiarAccion('<%=AWDevolucion.ELIMINAR_RECURSOS_NOT%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <%}%>

    <br>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td width="179">N&uacute;mero</td>
            <td width="211">
                <% try {
                        TextHelper textHelper = new TextHelper();
                        textHelper.setNombre(AWDevolucion.RESOLUCION);
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(AWDevolucion.RESOLUCION);
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }
                %>
            </td>
            <td width="146">Fecha</td>
            <td width="200">
                <%
                    try {

                        session.setAttribute(AWDevolucion.FECHA_RESOLUCION, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_RESOLUCION);
                        textHelper.setNombre(AWDevolucion.FECHA_RESOLUCION);
                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+AWDevolucion.FECHA_RESOLUCION+"');\" "
                                                    + " onChange=\"fixDate('"+AWDevolucion.FECHA_RESOLUCION+"')\"   onBlur=\"javascript:validarFecha()\"" );
                        textHelper.setCssClase("camposformtext");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_RESOLUCION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
        <br>


        <td align="right">

            <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                    <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td>Agregar Resoluci&oacute;n</td>
                    <td><a href="javascript:cambiarAccion('<%=AWDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                </tr>
            </table>

        </td>

        </tr>
    </table>
    <br>
</form>


<script type="text/javascript">

    function reescribirValor(valor, id) {
        var my_str = valor;
        var miles = 1;
        if (my_str.indexOf(".") == -1) {
            if (my_str.indexOf(",") == -1) {
                var nStr = "";
                for (var i = 1; i <= my_str.length; i++) {
                    var desde = my_str.length - i * 3;
                    var hasta = my_str.length - (3 * (i - 1));
                    var temp = my_str.slice(desde, hasta);
                    var separador = "";
                    if (hasta > 3) {
                        if (miles == 1) {
                            miles = 0;
                            separador = ",";
                        } else {
                            miles = 1
                            separador = ",";
                        }
                        nStr = separador + temp + nStr;
                    } else {
                        if (hasta > 0) {
                            temp = my_str.slice(0, hasta);
                            nStr = temp + nStr;
                        }
                    }
                }
                nStr = nStr + ".00";
                document.getElementById(id).value = nStr;
            }
        } else {
            var largo = my_str.indexOf(".");
            var centavos = my_str.substr(largo, my_str.length);
            if (my_str.indexOf(",") == -1) {
                var nStr = "";
                for (var i = 1; i <= largo; i++) {
                    var desde = largo - i * 3;
                    var hasta = largo - (3 * (i - 1));
                    var temp = my_str.slice(desde, hasta);
                    var separador = "";
                    if (hasta > 3) {
                        if (miles == 1) {
                            miles = 0;
                            separador = ",";
                        } else {
                            miles = 1
                            separador = ",";
                        }
                        nStr = separador + temp + nStr;
                    } else {
                        if (hasta > 0) {
                            temp = my_str.slice(0, hasta);
                            nStr = temp + nStr;
                        }
                    }
                }
                nStr = nStr + centavos;
                document.getElementById(id).value = nStr;
            }
        }
    }

</script>
<form name="ENTREGA" method="post" action="devolucion.do">
    <table class="camposform">
        <tr>
            <td width="146">Fecha Ejecutoria</td>
            <td width="200">
                <%

                    try {

                        session.setAttribute(AWDevolucion.FECHA_EJECUTORIA, "");

                        TextHelper textHelper = new TextHelper();
                        textHelper.setId(AWDevolucion.FECHA_EJECUTORIA);
                        textHelper.setNombre(AWDevolucion.FECHA_EJECUTORIA);
                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+AWDevolucion.FECHA_EJECUTORIA+"');\" "
                                                    + " onChange=\"fixDate('"+AWDevolucion.FECHA_EJECUTORIA+"')\"   onBlur=\"javascript:validarFecha()\"" );
                        textHelper.setCssClase("camposformtext");
                        textHelper.render(request, out);
                    } catch (HelperException re) {
                        out.println("ERROR " + re.getMessage());
                    }

                %>
                <a href="javascript:NewCal('<%=AWDevolucion.FECHA_EJECUTORIA%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
            </td>
        </tr>
    </table>
    <br>

    <hr class="linehorizontal">
    <table width="100%" class="camposform">
        <tr>

        <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.ACEPTAR_INTERPOSICION_RECURSOS_NOT%>">
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
        <td width="140"><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="140" height="21" border="0"></td>
</form>
<form name="logOut2" action="seguridad.do" method="post">
    <td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cerrar.gif" width="150" height="21" border="0" >
        <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
        <% Proceso proc = ((Proceso) session.getAttribute(WebKeys.PROCESO));
            if (proc != null) {
        %>

        <input type="hidden" name="ID_PROCESO" value="<%= proc.getIdProceso()%>">

        <%
            }
        %>
    </td>
    <td>&nbsp;</td>
</form>
</tr>
</table>
</td>
<td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
</tr>
<tr>
    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
</tr>
</table>
<% try {
        helper.render(request, out);
    } catch (HelperException re) {
        out.println("ERROR " + re.getMessage());
    }
%>
</td>
<td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
</tr>
</table>
<%if(carga){%>
<script>cambiarAccion('<%=AWDevolucion.CARGAR_RECURSOS_NOTA%>')</script>
<%}%>