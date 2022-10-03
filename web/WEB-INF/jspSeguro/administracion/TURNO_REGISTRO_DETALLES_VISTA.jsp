<%@page import="gov.sir.core.negocio.modelo.Vereda"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDevoluciones"%>
<%@page import="gov.sir.core.negocio.modelo.NotificacionNota"%>
<%@page import="java.util.*" %> 
<%@page import="java.util.ArrayList" %>  
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.util.DateFormatUtil" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano" %> 
<%@page import="gov.sir.core.negocio.modelo.TestamentoCiudadano" %>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro" %> 
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado" %> 
<%@page import="gov.sir.core.negocio.modelo.Turno" %> 
<%@page import="gov.sir.core.negocio.modelo.AplicacionPago" %> 
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago" %> 
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria" %> 
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudAsociada" %> 
<%@page import="gov.sir.core.negocio.modelo.Liquidacion" %> 
<%@page import="gov.sir.core.negocio.modelo.Pago" %> 
<%@page import="gov.sir.core.negocio.modelo.ControlMatricula" %> 
<%@page import="gov.sir.core.negocio.modelo.TurnoFolioMig" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudFolio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuestaCalificacion" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuesta" %> 
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasInformativasHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasDevolutivasHelper"%>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudAsociada"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstadoFolio"%>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.web.acciones.comun.AWTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CFase"%>
<%@page import ="gov.sir.hermod.*"%>

<%
    Turno turno = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
    
    SolicitudRegistro solicituds = (SolicitudRegistro) turno.getSolicitud();
    
    //ver is la oficina es internacional o naciol
				String nomOficinaIntr="";
				String departament="&nbsp;";
				String municipality="&nbsp;";
				String pathway="&nbsp;";
				String typeOf="&nbsp;";
				String numberOf="&nbsp;";
				boolean esInternacional=false;
				boolean esComentarioDocumento=false;				

				if(solicituds.getDocumento().getComentario()!=null){
					nomOficinaIntr=solicituds.getDocumento().getComentario();
					esComentarioDocumento=true;
				}else if(solicituds.getDocumento().getOficinaInternacional()!=null){
					nomOficinaIntr=solicituds.getDocumento().getOficinaInternacional();
					esInternacional=true;
				}
				else{

					//DEPARTAMENTO, MUNICIPIO, VEREDA
					if (solicituds.getDocumento().getOficinaOrigen()!=null){
						if(solicituds.getDocumento().getOficinaOrigen().getVereda()!=null){
							Vereda auxVereda=solicituds.getDocumento().getOficinaOrigen().getVereda();
							departament=auxVereda.getMunicipio().getDepartamento().getNombre();
							municipality=auxVereda.getMunicipio().getNombre();
							pathway=auxVereda.getNombre();
						}
	
						//TIPO OFICINA
	
						if(solicituds.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
							typeOf=solicituds.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicituds.getDocumento().getOficinaOrigen().getNombre();

						//NUM OFICINA

						if(solicituds.getDocumento().getOficinaOrigen().getNumero()!=null)
							numberOf=solicituds.getDocumento().getOficinaOrigen().getNumero();
					}
				}
    
        List controlMatriculas = null;
        List addedLiquidador = new ArrayList();
        List addedConfrontador = new ArrayList();
        List addedCorrectivo = new ArrayList();
        List deletedConfrontador = new ArrayList();
        List deletedCorrectivo = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            controlMatriculas = hs.getControlMatriculaTurno(turno.getIdWorkflow());
        } catch(HermodException he){
            System.out.println("ERROR obteniendo control de matriculas");
        }
        
        if(controlMatriculas != null){
            Iterator itCtrl = controlMatriculas.iterator();
            while(itCtrl.hasNext()){
                ControlMatricula ctrl = (ControlMatricula) itCtrl.next();
                if(ctrl.getRol().equals("LIQUIDADOR")){
                    if(ctrl.getAccion().equals("1")){
                        addedLiquidador.add(ctrl.getIdMatricula());
                    } 
                } else if(ctrl.getRol().equals("CONFRONTADOR")){
                    if(ctrl.getAccion().equals("1")){
                        addedConfrontador.add(ctrl.getIdMatricula());
                    } else{
                        deletedConfrontador.add(ctrl.getIdMatricula());
                    }
                } else if(ctrl.getRol().equals("CORRECTIVO")){
                    if(ctrl.getAccion().equals("1")){
                        addedCorrectivo.add(ctrl.getIdMatricula());
                    } else{
                        deletedCorrectivo.add(ctrl.getIdMatricula());
                    }
                }
                
            }
        }
        
    Turno turnoDerivado = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_HIJO);
    Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
    String fase = turno.getIdFase();
    Turno turnoPadreDerivado = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_PADRE);
    String conservacion = (String) session.getAttribute(AWTrasladoTurno.SUMA_CONSERVACION);
    String conservacionmayor = (String) session.getAttribute(AWTrasladoTurno.VALOR_CONSERVACION_MAYOR_VALOR);
    //////////////////////////
    Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO);
    /////////////////////////
    session.setAttribute(WebKeys.TURNO, turno);
 
    List turnosAsociados = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);
    if (turnosAsociados == null) {
        turnosAsociados = new ArrayList();
    }
    List turnosHistoria = new ArrayList();
    if (turno.getHistorials() != null) {
        turnosHistoria = turno.getHistorials();
    }

    //@author David Andrés Rubio
    //@change Se declara variable para obtener las observaciones del testamento desde la sesión
    String obsTestamento = (String) session.getAttribute(AWTrasladoTurno.OBSERVACIONES_TESTAMENTO);

    //@author Desarrollo1
    //@change Se declara variable para obtener las caracteristicas del testamento desde la sesión
    String tomoTestamento = (String) session.getAttribute(AWTrasladoTurno.TOMO_TESTAMENTO);
    String numAnotacionesTestamento = (String) session.getAttribute(AWTrasladoTurno.NUMERO_ANOTACIONES);
    String numCopiasTestamento = (String) session.getAttribute(AWTrasladoTurno.NUMERO_COPIAS);
    String revocaEscrituraTestamento = (String) session.getAttribute(AWTrasladoTurno.REVOCA_ESCRITURA);
    String copiasImprimirTestamento = (String) session.getAttribute(AWTrasladoTurno.COPIAS_IMPRIMIR);
    Date fechaCreacionTestamento = (Date) session.getAttribute(AWTrasladoTurno.FECHA_CREACION);
    List list_testadores = (List) session.getAttribute(AWTrasladoTurno.LIST_TESTADORES);

    String respuesta = null;
    TurnoHistoria thUltimo = null;
    if (turnosHistoria != null && turnosHistoria.size() > 0) {
        thUltimo = (TurnoHistoria) turnosHistoria.get(turnosHistoria.size() - 1);

        for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
            TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();

            if (turnoAux.getFase() != null && (turnoAux.getFase()).equals(gov.sir.core.negocio.modelo.constantes.CFase.CAL_CALIFICACION)) {
                respuesta = turnoAux.getRespuesta();
            }

            if (turnoAux.getFase() != null && (turnoAux.getFase()).equals(gov.sir.core.negocio.modelo.constantes.CFase.REG_TESTAMENTO)) {
                respuesta = turnoAux.getRespuesta();
            }

        }
    }

    if (respuesta != null) {
        if (respuesta.equals(CRespuestaCalificacion.RESPUESTA_INSCRITO) || respuesta.equals(CRespuesta.CONFIRMAR)) {
            respuesta = "INSCRITO";
        } else if (respuesta.equals(CRespuestaCalificacion.RESPUESTA_INSCRIPCION_PARCIAL)) {
            respuesta = "INSCRITO PARCIALMENTE";
        } else if (respuesta.equals(CRespuestaCalificacion.RESPUESTA_DEVUELTO) || respuesta.equals(CRespuesta.NEGAR)) {
            respuesta = "DEVUELTO";
        } else {
            respuesta = "&nbsp;";
        }
    }

    List actos = new ArrayList();

    Solicitud solicitud = new Solicitud();
    Ciudadano ciudadano = new Ciudadano();
    if (turno.getSolicitud() != null) {
        solicitud = turno.getSolicitud();
        ciudadano = solicitud.getCiudadano();
    }

    String matricula = "&nbsp;";
    String turnoAnterior = "&nbsp;";
    String numeroRecibo = "&nbsp;";

    MostrarAntiguoSistemaHelper MASHelper = new MostrarAntiguoSistemaHelper();

    //Helper para notas informativas
    ListarNotasInformativasHelper notasInformativasHelper = new ListarNotasInformativasHelper();
    //Helper para notas devolutivas
    ListarNotasDevolutivasHelper notasDevolutivasHelper = new ListarNotasDevolutivasHelper();

    // Obtengo los certificados asociados
    List liSolicitudesHijas = solicitud.getSolicitudesHijas();
    List certificados = new ArrayList();
    Solicitud sCertificado = null;
    SolicitudAsociada saSolicitud = null;
    Turno tTurnoCertificado = null;

    if (liSolicitudesHijas != null) {
        Iterator iSolicitudesHijas = liSolicitudesHijas.iterator();

        while (iSolicitudesHijas.hasNext()) {
            saSolicitud = (SolicitudAsociada) iSolicitudesHijas.next();
            sCertificado = saSolicitud.getSolicitudHija();
            tTurnoCertificado = sCertificado.getTurno();
            certificados.add(tTurnoCertificado);
        }
    }

    //SE DETERMINA EL VALOR DEL PAGO
    List liquidaciones = solicitud.getLiquidaciones();
    if (liquidaciones == null) {
        liquidaciones = new ArrayList();
    }
     Iterator itLiq = liquidaciones.iterator();
        String valMatAgr = null;
        int numMatAgr = 0;
        if(itLiq.hasNext())
        {
          Liquidacion li = (Liquidacion) itLiq.next();
          valMatAgr = li.getNumMatAgr();
          if(valMatAgr!=null)
          numMatAgr = Integer.parseInt(valMatAgr);
          
        }
        
    double valorPagado = 0.0;
    double valorPorPagar = 0.0;
    double valorDevuelto = 0.0;
    double valorPorDevolver = 0.0;

    for (Iterator iter = liquidaciones.iterator(); iter.hasNext();) {
        Liquidacion liq = (Liquidacion) iter.next();
        if (liq.getPago() != null && liq.getValor() >= 0) {
            valorPagado += liq.getValor();
            continue;
        }
        if (liq.getPago() == null && liq.getValor() >= 0) {
            valorPorPagar += liq.getValor();
            continue;
        }
        if (liq.getPago() != null && liq.getValor() < 0) {
            valorDevuelto += liq.getValor();
            continue;
        }
        if (liq.getPago() == null && liq.getValor() < 0) {
            valorPorDevolver += liq.getValor();
            continue;
        }
    }

    if (!liquidaciones.isEmpty()) {
        int lastIndex = liquidaciones.size();
        for (int i = 0; i < lastIndex; i++) {
            Liquidacion ultimaLiq = (Liquidacion) liquidaciones.get(i);
            Pago pago = ultimaLiq.getPago();
            if (pago != null) {
                if (i == 0) {
                    numeroRecibo = pago.getNumRecibo();
                } else {
                    numeroRecibo = numeroRecibo + " | " + pago.getNumRecibo();
                }
            }
        }
    }

    //SE ENCUENTRAN LOS ACTOS DEL TURNO
    Iterator it = liquidaciones.iterator();
    while (it.hasNext()) {
        LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) it.next();
        actos.addAll(liqReg.getActos());
    }

    //SE CARGA EL TURNO ANTERIOR
    if (solicitud.getTurnoAnterior() != null) {
        turnoAnterior = solicitud.getTurnoAnterior().getIdWorkflow();
    }

    //CARGA LAS MATRÍCULAS ASOCIADAS
    List solicitudesFolio = solicitud.getSolicitudFolios();
    if (solicitudesFolio != null) {
        solicitudesFolio = new ArrayList();
    }
    int numSolicitudes = 0;
    String labelMatricula = "&nbsp;";
    if (!solicitudesFolio.isEmpty()) {
        Iterator itSolicitud = solicitudesFolio.iterator();
        while (itSolicitud.hasNext()) {
            SolicitudFolio solFolio = (SolicitudFolio) itSolicitud.next();
            if (solFolio != null && solFolio.getIdMatricula() != null) {

                gov.sir.core.negocio.modelo.Folio folioLabel = solFolio.getFolio();
                labelMatricula = "&nbsp;";

                if (folioLabel != null && !folioLabel.isDefinitivo()) {
                    if (folioLabel.getNombreLote() != null) {
                        labelMatricula = folioLabel.getNombreLote().trim();
                    }
                } else {
                    labelMatricula = solFolio.getIdMatricula().trim();
                }

                if (numSolicitudes == 0) {
                    matricula = matricula + labelMatricula + " ";
                } else {
                    matricula = matricula + ", " + labelMatricula + " ";
                }

            }
            numSolicitudes++;
        }
    }

    //INFO TURNOS ANTERIORES
    boolean hayTurnosAnteriores = true;
    List turnosAnteriores = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_ANTERIORES);
    if (turnosAnteriores == null || turnosAnteriores.size() == 0) {
        hayTurnosAnteriores = false;
    }

    //INFO TURNOS SIGUIENTES
    boolean hayTurnosSiguientes = true;
    List turnosSiguientes = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES);
    if (turnosSiguientes == null || turnosSiguientes.size() == 0) {
        hayTurnosSiguientes = false;
    }

    /*
         * Obtiene la lista de turnos de devolucion asociados de la session.
         * @author: Julio Alcazar
         * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
     */
    List turnosDevoluciones = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_DEVOLUCION);

    gov.sir.core.negocio.modelo.SolicitudRegistro solRegistro = (gov.sir.core.negocio.modelo.SolicitudRegistro) turno.getSolicitud();
    //TIPO DOCUMENTO
    String tipoDocumento = "&nbsp;";
    if (solRegistro != null
            && solRegistro.getDocumento() != null
            && solRegistro.getDocumento().getTipoDocumento() != null
            && solRegistro.getDocumento().getTipoDocumento().getNombre() != null) {
        tipoDocumento = solRegistro.getDocumento().getTipoDocumento().getNombre();
    }
    //NUM DOCUMENTO
    String numDocumento = "&nbsp;";
    if (solRegistro != null
            && solRegistro.getDocumento() != null
            && solRegistro.getDocumento().getNumero() != null) {
        numDocumento = solRegistro.getDocumento().getNumero();
    }
    //FECHA DOCUMENTO
    String fechaDocumento = "&nbsp;";
    if (solRegistro != null
            && solRegistro.getDocumento() != null
            && solRegistro.getDocumento().getFecha() != null) {

        fechaDocumento = DateFormatUtil.format(solRegistro.getDocumento().getFecha());
    }
    String comentarioDocumento = "&nbsp;";
    if (solRegistro != null
            && solRegistro.getDocumento() != null && solRegistro.getDocumento().getComentario() != null) {
        comentarioDocumento = solRegistro.getDocumento().getComentario();
    }
    //OFICINA
    boolean isInternacional = false;
    //OFICINA INTERNACIONAL
    String oficinaInternacional = "&nbsp;";
    if (solRegistro != null
            && solRegistro.getDocumento() != null
            && solRegistro.getDocumento().getOficinaInternacional() != null
            && !solRegistro.getDocumento().getOficinaInternacional().equals("")) {
        isInternacional = true;
        oficinaInternacional = solRegistro.getDocumento().getOficinaInternacional();
    }

    //OFICINA NACIONAL
    //DEPARTAMENTO, MUNICIPIO, VEREDA
    String departamento = "&nbsp;";
    String municipio = "&nbsp;";
    String vereda = "&nbsp;";
    String comentario = "&nbsp;";
    if (!isInternacional) {
        if (solRegistro != null
                && solRegistro.getDocumento() != null
                && solRegistro.getDocumento().getOficinaOrigen() != null
                && solRegistro.getDocumento().getOficinaOrigen().getVereda() != null) {
            gov.sir.core.negocio.modelo.Vereda auxVereda = solRegistro.getDocumento().getOficinaOrigen().getVereda();
            if (auxVereda.getMunicipio() != null
                    && auxVereda.getMunicipio().getDepartamento() != null
                    && auxVereda.getMunicipio().getDepartamento().getNombre() != null) {
                departamento = auxVereda.getMunicipio().getDepartamento().getNombre();
            }
            if (auxVereda.getMunicipio() != null
                    && auxVereda.getMunicipio().getNombre() != null) {
                municipio = auxVereda.getMunicipio().getNombre();
            }
            if (auxVereda.getNombre() != null) {
                vereda = auxVereda.getNombre();
            }
        }
    }

    boolean isMigrado = false;
    if (turno.getDescripcion() != null && turno.getDescripcion().equals(WebKeys.MIGRACION)) {
        isMigrado = true;
        if (solRegistro.getDocumento().getComentario() != null) {
            comentario = solRegistro.getDocumento().getComentario();
        }
    }

    //TIPO OFICINA
    String tipoOficina = "&nbsp;";
    String nombreOficina = "&nbsp;";
    if (!isInternacional) {
        if (solRegistro != null
                && solRegistro.getDocumento() != null
                && solRegistro.getDocumento().getOficinaOrigen() != null
                && solRegistro.getDocumento().getOficinaOrigen().getTipoOficina() != null
                && solRegistro.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() != null) {
            tipoOficina = solRegistro.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
            nombreOficina = solRegistro.getDocumento().getOficinaOrigen().getNombre();
        }

    }
    //NUM OFICINA
    String numOficina = "&nbsp;";
    if (!isInternacional) {
        if ((solRegistro != null
                && solRegistro.getDocumento() != null
                && solRegistro.getDocumento().getOficinaOrigen() != null
                && solRegistro.getDocumento().getOficinaOrigen().getNumero() != null)) {
            numOficina = solRegistro.getDocumento().getOficinaOrigen().getNumero();
            nombreOficina = solRegistro.getDocumento().getOficinaOrigen().getNombre();
        }
    }

    List listaTurnos = (List) session.getAttribute("TURNOS_DERIVADOS");
    
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
    //@author David Andrés Rubio J
//@change Función para mostrar el text area que contiene las observaciones del testamento
    function cambiarDisplay(id) {
        if (!document.getElementById)
            return false;
        fila = document.getElementById(id);
        if (fila.style.display != "none") {
            fila.style.display = "none"; //ocultar fila 
        } else {
            fila.style.display = ""; //mostrar fila 
        }
    }
    //

    //@author David Andrés Rubio J
//@change Función para mostrar el text area que contiene las observaciones del testamento
    function cambiarDisplay_test() {
        if (!document.getElementById)
            return false;
        fila_uno = document.getElementById('campoOficio_uno');
        fila_dos = document.getElementById('campoOficio_dos');
        fila_tres = document.getElementById('campoOficio_tres');
        fila_cuatro = document.getElementById('campoOficio_cuatro');
        fila_cinco = document.getElementById('campoOficio_cinco');
        fila_cero = document.getElementById('campoOficio_cero');
        fila_col = document.getElementById('campoOficio_col');
        fila_col_uno = document.getElementById('campoOficio_col_uno');
        fila_testadores = document.getElementById('table_testadores');
        fila = document.getElementById('campoOficio');

        if (fila.style.display != "none") {
            fila.style.display = "none"; //ocultar fila 
        } else {
            fila.style.display = ""; //mostrar fila 
        }

        if (fila_uno.style.display != "none") {
            fila_uno.style.display = "none"; //ocultar fila 
        } else {
            fila_uno.style.display = ""; //mostrar fila 
        }

        if (fila_dos.style.display != "none") {
            fila_dos.style.display = "none"; //ocultar fila 
        } else {
            fila_dos.style.display = ""; //mostrar fila 
        }

        if (fila_tres.style.display != "none") {
            fila_tres.style.display = "none"; //ocultar fila 
        } else {
            fila_tres.style.display = ""; //mostrar fila 
        }

        if (fila_cuatro.style.display != "none") {
            fila_cuatro.style.display = "none"; //ocultar fila 
        } else {
            fila_cuatro.style.display = ""; //mostrar fila 
        }
        
         if (fila_cinco.style.display != "none") {
            fila_cinco.style.display = "none"; //ocultar fila 
        } else {
            fila_cinco.style.display = ""; //mostrar fila 
        }
        
        if (fila_cero.style.display != "none") {
            fila_cero.style.display = "none"; //ocultar fila 
        } else {
            fila_cero.style.display = ""; //mostrar fila 
        }
        
        if (fila_col.style.display != "none") {
            fila_col.style.display = "none"; //ocultar fila 
        } else {
            fila_col.style.display = ""; //mostrar fila 
        }
        
        if (fila_col_uno.style.display != "none") {
            fila_col_uno.style.display = "none"; //ocultar fila 
        } else {
            fila_col_uno.style.display = ""; //mostrar fila 
        }
        
        if (fila_testadores.style.display != "none") {
            fila_testadores.style.display = "none"; //ocultar fila 
        } else {
            fila_testadores.style.display = ""; //mostrar fila 
        }
    }
    //
    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }
    function cambiarAccionSubmit(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
    }
    function verNota(nombre, valor, dimensiones)
    {
        popup = window.open(nombre, valor, dimensiones);
        popup.focus();
    }

    function consultarMatricula(text) {
        document.MATRICULAS.<%= CFolio.ID_MATRICULA%>.value = text;
        document.MATRICULAS.submit();
    }

    function consultarTurno(text) {
        document.DERIVADOS.<%= CTurno.ID_TURNO%>.value = text;
        document.DERIVADOS.submit();
    }

    function verDetalleTurnoSig(text) {
        document.VER_DETALLE_TURNO_SIG.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO_SIG.submit();
    }
    
    function verDetalleTurnoCertAso(text) {
        document.VER_DETALLE_TURNO_CERT_ASO.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO_CERT_ASO.submit();
    }

    function verDetalleTurnoRegresar() {
        //alert(text);
        //document.VER_DETALLE_TURNO_REGRESAR.<%--= CTurno.ID_TURNO--%>.value = text;
        document.VER_DETALLE_TURNO_REGRESAR.submit();
    }

    function verDetalleTurnoAnt(text) {
        document.VER_DETALLE_TURNO_ANT.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO_ANT.submit();
    }

    //@author : David A Rubio J
//@change : Se agrega función para mostrar los detalles de un folio en una ventana emergente con pestañas
    function verAnotacionPersonalizada(matricula, nombre, valor, dimensiones, pos) {
        nombre = nombre + "&ID_MATRICULA=" + matricula;
        popup = window.open(nombre, valor, dimensiones);
        popup.focus();
    }

    function precisionRound(number) {
        var n = number * 0.2;
        var factor = Math.pow(10, 1);
        return Math.round(n * factor) / factor;
    }

	


</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

</table></td>
<td width="12"></td>
<td width="12">&nbsp;</td>
</tr>

<tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Detalles de Turno</td>
                            <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                            <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>

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
                    <br>
                    <br>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub">Datos basicos de Turno</td>
                            <td width="16" class="bgnsub"></td>
                            <td width="16" class="bgnsub"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>

                    <table width="100%" class="camposform">
                        <tr>

                            <td width="220">N&uacute;mero del Turno</td>
                            <td class="campositem"> <%=  turno.getIdWorkflow() != null ? turno.getIdWorkflow() : "&nbsp;"%> </td>
                        </tr>
                        <tr>

                            <td width="220">Fecha de Radicaci&oacute;n </td>
                            <td class="campositem"> <%= FechaConFormato.formatear(turno.getFechaInicio(), "dd/MM/yyyy")%> </td>
                        </tr>
                        <tr>

                            <td width="220">N&uacute;mero de Recibo </td>
                            <td class="campositem"> <%=  numeroRecibo != null ? numeroRecibo : "&nbsp;"%> </td>
                        </tr>
                        <tr>

                            <td width="220">Valor Pagado </td>
                            <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(valorPagado)%> </td>
                        </tr>
                        <tr>

                            <td width="220">Valor por Pagar </td>
                            <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(valorPorPagar)%> </td>
                        </tr>
                        <tr>

                            <td width="220">Valor Devuelto </td>
                            <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(java.lang.Math.abs(valorDevuelto))%> </td>
                        </tr>
                        <tr>

                            <td width="220">Valor por Devolver </td>
                            <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(valorPorDevolver)%> </td>
                        </tr>
                        <%
                            if (turno != null) {
                                if (turno.getFechaFin() == null) {
                        %>
                        <tr>

                            <td width="220">Estado Actual </td>
                            <%if (turno.getAnulado() == null || !turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {%>
                            <td class="campositem"> <%=  thUltimo != null && thUltimo.getNombreFase() != null ? thUltimo.getNombreFase() : "&nbsp;"%> </td>
                            <%} else {%>
                            <td class="campositem"> ANULADO </td>
                            <%}%> 
                        </tr>
                        <tr>

                            <td width="220">Estación Asignada </td>
                            <td class="campositem"> <%=  thUltimo != null && thUltimo.getIdAdministradorSAS() != null ? thUltimo.getIdAdministradorSAS() : "&nbsp;"%> </td>
                        </tr>
                        <%
                        } else {
                        %>
                        <tr>

                            <td width="220">Estado Actual </td>
                            <td class="campositem"> Turno entregado. </td>
                        </tr>                  
                        <%
                                }
                            }
                        %>     


                        <%
                            if (respuesta != null || turno.getAnulado() != null) {
                        %>
                        <%if (turno.getAnulado() == null || !turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                        %>
                        <tr>

                            <td width="220"><B>Resolución</B></td>
                            <td class="campositem"> <%= respuesta != null ? respuesta : "&nbsp;"%> &nbsp;  </td>
                        </tr>
                        <%
                        } else {%>
                        <tr>

                            <td width="220"><B>Resolución</B></td>
                            <td class="campositem"> ANULADO &nbsp;  </td>
                        </tr>
                        <tr>

                            <td width="220">Observación de anulación</td>
                            <td class="campositem"> <%=turno.getObservacionesAnulacion()%> </td>
                        </tr>
                        <%}
                            }
                        %> 


                        <tr>

                            <td width="220">Relación Actual </td>
                            <% String idRelacionActual = turno.getIdRelacionActual();
                                if (idRelacionActual == null) {
                                    idRelacionActual = "&nbsp;";
                                }%>
                            <td class="campositem"> <%=idRelacionActual%> </td>
                        </tr>

                        <tr>

                            <td width="220">Fase Relacion Actual </td>
                            <% String idFaseRelActual = turno.getIdFaseRelacionActual();
                                if (idFaseRelActual == null) {
                                    idFaseRelActual = "&nbsp;";
                                }%>
                            <td class="campositem"> <%=idFaseRelActual%> </td>
                        </tr>
                        <tr>
                            <td width="220">Origen </td>
                            <% String turnoREL = (turno.getTurnoREL() != null? "REL":"SIR");%> 
                            <td class="campositem"> <%=turnoREL%> </td>
                        </tr>




                    </table>
                    <% if (turnoDerivado != null || turnoPadreDerivado != null) {%>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Turnos Derivados</p></td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <form action="trasladoTurno.do" method="POST" name="DERIVADOS" id="DERIVADOS">
                        <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                        <input name="TIPO_CONSULTA"  id="TIPO_CONSULTA" type="hidden" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                        <input name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>" type="hidden" value="">
                        <table width="100%" class="camposform">
                            <% if (turnoDerivado != null) {%>
                            <tr>

                                <td width="20%">Turno Hijo</td>
                                <td width="20%" class="campositem"><%= turnoDerivado.getIdWorkflow()%></td>
                                <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="consultarTurno('<%= turnoDerivado.getIdWorkflow()%>')" style="cursor:hand"></td>
                            </tr>
                            <% } %>
                            <% if (turnoPadreDerivado != null) {%>
                            <tr>

                                <td width="20%">Turno Padre</td>
                                <td width="20%" class="campositem"><%= turnoPadreDerivado.getIdWorkflow()%></td>
                                <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="consultarTurno('<%= turnoPadreDerivado.getIdWorkflow()%>')" style="cursor:hand"></td>
                            </tr>
                            <% } %>
                        </table>
                    </form>
                    <% } %>
                    <br>
                    <%
                        if (turnosAsociados != null && !turnosAsociados.isEmpty()) {
                            String idsTurnosAsociados = "&nbsp;";
                            for (Iterator iter = turnosAsociados.iterator(); iter.hasNext();) {
                                Turno turnoAsociado = (Turno) iter.next();
                                if (turnoAsociado != null && turnoAsociado.getIdWorkflow() != null) {
                                    idsTurnosAsociados += turnoAsociado.getIdWorkflow();
                                    if (iter.hasNext()) {
                                        idsTurnosAsociados += ", ";
                                    }
                                }
                            }
                    %>
                    <table width="100%" class="camposform">
                        <tr>

                            <td width="220">Turnos Asociados</td>
                            <td class="campositem"> <%=  idsTurnosAsociados != null ? idsTurnosAsociados : "&nbsp;"%> </td>
                        </tr>
                    </table>
                    <br>
                    <%
                        }
                    %>

                    <% if (hayTurnosAnteriores) {%>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Turnos anteriores</p></td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <form action="trasladoTurno.do" method="post" name="VER_DETALLE_TURNO_ANT" id="VER_DETALLE_TURNO_ANT">
                        <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                        <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                        <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>"  value="">
                        <table width="100%" class="camposform">
                            <% Iterator ita = turnosAnteriores.iterator();
                                for (; ita.hasNext();) {
                                    Turno temp = (Turno) ita.next();
                                    String nTurnoAnterior = temp.getIdWorkflow();
                            %>

                            <tr>

                                <td width="220">Turno Anterior : </td>
                                <td width="150" class="campositem"> <%=  nTurnoAnterior != null ? nTurnoAnterior : "&nbsp;"%> </td>
                                <td><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurnoAnt('<%= nTurnoAnterior%>')" style="cursor:hand"></td>
                            </tr>
                            <% 	} %>
                        </table>
                    </form>
                    <%}
                        if (hayTurnosSiguientes) {%>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Turnos Siguientes</p></td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <form action="trasladoTurno.do" method="post" name="VER_DETALLE_TURNO_SIG" id="VER_DETALLE_TURNO_SIG">
                        <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                        <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                        <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>"  value="">
                        <table width="100%" class="camposform">
                            <% Iterator its = turnosSiguientes.iterator();
                                for (; its.hasNext();) {
                                    Turno temp = (Turno) its.next();
                                    String nTurnoSiguiente = temp.getIdWorkflow();
                            %>

                            <tr>

                                <td width="220">Turno Siguiente : </td>
                                <td width="150" class="campositem"> <%=  nTurnoSiguiente != null ? nTurnoSiguiente : "&nbsp;"%> </td>
                                <td><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurnoSig('<%= nTurnoSiguiente%>')" style="cursor:hand"></td>
                            </tr>
                            <% 	} %>
                        </table>
                    </form>
                    <%} %>

                    <br>
                    <br>
                    <br>
                    <!--
                       * Tabla para mostrar los turnos de devolución asociado
                       * @author: Julio Alcazar
                       * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                    !-->
                    <%  if (turnosDevoluciones != null) {
                    %>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="bgnsub"><p>Turnos de Devoluciones Asociados</p></td>
                            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <table width="100%" class="camposform">
                        <%
                            Iterator itera = turnosDevoluciones.iterator();
                            while (itera.hasNext()) {
                                Turno temp2 = (Turno) itera.next();
                                List turnohistoria = temp2.getHistorials();
                                String respuestadev = "";
                                Iterator iter = turnohistoria.iterator();
                                while (iter.hasNext()) {
                                    TurnoHistoria turnoAux = (TurnoHistoria) iter.next();
                                    if (turnoAux.getFase() != null && ((turnoAux.getFase()).equals(CFase.DEV_ANALISIS))) {
                                        respuestadev = turnoAux.getRespuesta();
                                    }
                                }
                                if (respuestadev != null && respuestadev != "") {
                                    if (respuestadev.equals(CRespuesta.CONFIRMAR)) {
                                        respuestadev = "RESOLUCIÓN: APROBADA";
                                    } else if (respuestadev.equals(CRespuesta.NEGAR)) {
                                        respuestadev = "RESOLUCIÓN: NEGADA";
                                    }
                                }
                        %>
                        <tr>
                            <td width="220">Turno : </td>
                            <td class="campositem"> <%=  temp2.getIdWorkflow()%> </td>

                            <td class="campositem"> <%= respuestadev != null ? respuestadev : "&nbsp;"%> </td>
                        </tr>

                        <%}
                            }%>
                    </table>
                    <br>
                    <br>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Matr&iacute;culas Asociadas</p></td>
                            <td width="16" class="bgnsub"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                                <table width="100%" class="camposform"> 
                      <tr>
                          <td align="center"> <strong>Matriculas Agregadas Rol Liquidador</strong></td>
                          <td align="center"> <strong>Matriculas Agregadas Rol Confrontador</strong></td>
                          <td align="center"> <strong>Matriculas Agregadas Rol Confrontador - Correctiva</strong></td>	
                      </tr>
                      <tr>
                             <td align="center">
                                 <table class="camposformnoborder">
                                     <%
                                         if(addedLiquidador != null){
                                         Iterator itLiqui = addedLiquidador.iterator(); 
                                         while(itLiqui.hasNext()){
                                         String matLiq = (String) itLiqui.next(); 
                                     %>
                                     <tr>
                                      <td class="campositem" width="150">
                                         <%=matLiq%>
                                     </td>
                                     </tr>
                                     <%
                                            }  
                                        }
                                     %>
                                 </table>
                             </td>
                             <td align="center">
                                 <table class="camposformnoborder">
                                     <%
                                         if(addedConfrontador != null){ 
                                         Iterator itCon = addedConfrontador.iterator();
                                         while(itCon.hasNext()){
                                         String matCon = (String) itCon.next(); 
                                     %>
                                     <tr>
                                      <td class="campositem" width="150">
                                         <%=matCon%>
                                     </td>
                                     </tr>
                                     <%
                                            }  
                                        }
                                     %>
                                 </table>
                             </td>
                             <td align="center">
                                 <table class="camposformnoborder">
                                     <%
                                         if(addedCorrectivo != null){  
                                         Iterator itCon = addedCorrectivo.iterator();
                                         while(itCon.hasNext()){
                                         String matCon = (String) itCon.next(); 
                                     %>
                                     <tr>
                                      <td class="campositem" width="150">
                                         <%=matCon%>
                                     </td>
                                     </tr>
                                     <%
                                            }  
                                        }
                                     %>
                                 </table>
                             </td>
                       </tr>
                       <tr>
                              <td>&nbsp;</td>
                                <td align="center"> <strong>Matriculas Eliminadas Rol Confrontador</strong></td>
                                <td align="center"> <strong>Matriculas Eliminadas Rol Confrontador - Correctiva</strong></td>	
                       </tr>
                       <tr>
                              <td>&nbsp;</td>
                              <td align="center">
                                  <table class="camposformnoborder">
                                     <%
                                         if(deletedConfrontador != null){  
                                         Iterator itCon = deletedConfrontador.iterator(); 
                                         while(itCon.hasNext()){
                                         String matCon = (String) itCon.next(); 
                                     %>
                                     <tr>
                                      <td class="campositem" width="150">
                                         <%=matCon%>
                                     </td>
                                     </tr>
                                     <%
                                            }  
                                        }
                                     %>
                                 </table>
                              </td>
                              <td align="center">
                                  <table class="camposformnoborder">
                                     <%
                                         if(deletedCorrectivo != null){  
                                         Iterator itCon = deletedCorrectivo.iterator(); 
                                         while(itCon.hasNext()){
                                         String matCon = (String) itCon.next(); 
                                     %>
                                     <tr>
                                      <td class="campositem" width="150">
                                         <%=matCon%>
                                     </td>
                                     </tr>
                                     <%
                                            }  
                                        }
                                     %>
                                 </table>
                              </td>
                       </tr>
                      <tr>
                              <td>&nbsp;</td>
                       </tr>
                        <tr>
                              <td>&nbsp;</td>
                       </tr>
                  </table>
                                     
                    <table width="100%" class="camposform">
                         <tr>
                            <td width="300">Cantidad de Matriculas a Agregar: </td>

                            <td class="campositem">  <%= numMatAgr == 0 ? "&nbsp;" : numMatAgr+""  %> </td>
                        
                         </tr>
                        <tr>
                            <td width="300">Matriculas Agregadas Rol Liquidador: </td>
                             
                            <td class="campositem"> <%= addedLiquidador.size() == 0 &&  addedLiquidador != null ? "&nbsp;" : addedLiquidador.size()+"" %> </td>
                        </tr>
                        <tr>
                            <td width="300">Matriculas Agregadas Rol Confrontador: </td>
                             
                            <td class="campositem"> <%= addedConfrontador.size() == 0 &&  addedConfrontador != null ? "&nbsp;" : addedConfrontador.size()+"" %></td>
                        </tr>
                        <tr>
                            <td width="300">Matriculas Agregadas Rol Confrontador Correctivo: </td>
                             
                            <td class="campositem"> <%= addedCorrectivo.size() == 0 &&  addedCorrectivo != null ? "&nbsp;" : addedCorrectivo.size()+"" %> </td>
                        </tr>
                        <tr>
                            <td width="300">Matriculas Eliminadas Rol Confrontador: </td>
                             
                            <td class="campositem"> <%= deletedConfrontador.size() == 0 &&  deletedConfrontador != null ? "&nbsp;" : deletedConfrontador.size()+"" %> </td>
                        </tr>
                        <tr>
                            <td width="300">Matriculas Eliminadas Rol Confrontador Correctivo: </td>
                             
                            <td class="campositem"> <%= deletedCorrectivo.size() == 0 &&  deletedCorrectivo != null ? "&nbsp;" : deletedCorrectivo.size()+"" %></td>
                        </tr>
                    </table> 
                    <form action="trasladoTurno.do" method="POST" name="MATRICULAS" id="MATRICULAS">
                        <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_MATRICULA%>" value="<%=  AWTrasladoTurno.CONSULTAR_MATRICULA%>">
                        <input  type="hidden" name="<%= CFolio.ID_MATRICULA%>" id="" value="">
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20%" align="center"> Folio</td>
                                <td width="20%" align="center"> Estado</td>
                                <td width="35">&nbsp;</td>
                            </tr>
                            <%
                                if (turno.getSolicitud().getSolicitudFolios() != null) {
                                    int i = 0;
                                    for (Iterator itMatriculas = turno.getSolicitud().getSolicitudFolios().iterator(); itMatriculas.hasNext();) {
                                        SolicitudFolio solFol = (SolicitudFolio) itMatriculas.next();
                                        String estadoS = "&nbsp;";
                                        Folio folio = solFol.getFolio();

                                        if (folio != null && folio.getEstado() != null) {
                                            estadoS = folio.getEstado().getIdEstado();
                                        }

                                        if (estadoS.equals(CEstadoFolio.ACTIVO)) {
                                            estadoS = "ACTIVO";
                                        } else if (estadoS.equals(CEstadoFolio.ANULADO)) {
                                            estadoS = "ANULADO";
                                        } else if (estadoS.equals(CEstadoFolio.CERRADO)) {
                                            estadoS = "CERRADO";
                                        } else if (estadoS.equals(CEstadoFolio.TRASLADADO)) {
                                            estadoS = "TRASLADADO";
                                        } else if (estadoS.equals(CEstadoFolio.EN_CUSTODIA)) {
                                            estadoS = "EN CUSTODIA";
                                        } else if (estadoS.equals(CEstadoFolio.OBSOLETO)) {
                                            estadoS = "OBSOLETO";
                                        }
                            %>
                            <tr>
                                <td width="20%" class="campositem"><%=solFol.getIdMatricula()%></td>
                                <td width="20%" class="campositem"><%=estadoS%></td>
                                <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verAnotacionPersonalizada('<%=solFol.getIdMatricula()%>', 'consultar.folio.do?POSICION=<%= i%>', 'Folio', 'width=900,height=450,scrollbars=yes', '0')" style="cursor:hand"></td>
                                <!-- <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="consultarMatricula('<%=solFol.getIdMatricula()%>')" style="cursor:hand"></td> -->
                            </tr>
                            <%
                                        i++;
                                    }
                                }
                            %>



                            <%
                                List listaTurnosFolioMig = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_FOLIO_MIG);
                                if (listaTurnosFolioMig != null && !listaTurnosFolioMig.isEmpty()) {

                                    for (Iterator itSMTF = listaTurnosFolioMig.iterator(); itSMTF.hasNext();) {
                                        TurnoFolioMig smtf = (TurnoFolioMig) itSMTF.next();
                                        String estadoS = "&nbsp;";
                            %>
                            <tr>
                                <td width="20%" class="campositem"><%=smtf.getIdFolio()%></td>
                                <td width="20%" class="campositem"><%=estadoS%></td>
                                <td width="35">&nbsp;</td>
                            </tr>
                            <%
                                    }
                                }
                            %>


                        </table>
                    </form>
                    <br>
                    <br>
                    <br>
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

                            String idNotification = (notify.getIdNotificacion()!=null?notify.getIdNotificacion():"&nbsp;");
                            String fechaNot = "&nbsp;";
                            destiny = (notify.getDestino()!=null?notify.getDestino():"&nbsp;"); 
                            String tipo = (notify.getTipo()!=null?notify.getTipo():"&nbsp;");
                            String correo = (notify.getCorreo()!=null?notify.getCorreo():"&nbsp;"); 
                            String apoderado = String.valueOf(notify.getApoderado()); 
                            String nombres = (notify.getNombres()!=null?notify.getNombres():"&nbsp;");
                            String apellidos = (notify.getApellidos()!=null?notify.getApellidos():"&nbsp;");
                            String typeDoc= (notify.getTipoDocumento()!=null?notify.getTipoDocumento():"&nbsp;"); 
                            String numberDoc = (notify.getDocumento()!=null?notify.getDocumento():"&nbsp;");
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
                            <br>
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
                             <td width="150" class="campositem"><%=typeDoc%></td>    

                             <td width="150">Numero de Documento: </td>
                             <td width="150"class="campositem"><%= numberDoc%></td>     
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
                                                        <%=numberOf%>
                                  </td>
                                  <td width="15%">
                                    <DIV align="right">Nombre</DIV>
                                  </td>
                                  <td class="campositem" width="36%">
                                                    <%=typeOf%>
                                  </td>
                                    </tr>

                                 <tr>
                                    <td width="12%">
                                      <DIV align="right">Departamento</DIV>
                                    </td>
                                    <td class="campositem" width="32%">
                                                        <%=departament%>
                                    </td>
                                    <td width="15%">
                                      <DIV align="right">Municipio</DIV>
                                    </td>
                                    <td class="campositem" width="36%">
                                                                <%=municipality%>
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
    
                    <br>
                    <br>
                    <br>

                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>

                            <td class="bgnsub">Encabezado del Documento </td>
                            <td width="16" class="bgnsub"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <table width="100%" class="camposform">
                        <tr>

                            <td>Datos B&aacute;sicos </td>
                        </tr>
                        <tr>

                            <td><table width="100%" class="camposform">
                                    <tr>
                                        <td>Tipo</td>
                                        <td class="campositem"><%=tipoDocumento != null ? tipoDocumento : "&nbsp;"%></td>
                                        <td width="15%">&nbsp;</td>

                                        <td>N&uacute;mero</td>
                                        <td class="campositem"><%=numDocumento != null ? numDocumento : "&nbsp;"%></td>
                                        <td width="15%">&nbsp;</td>

                                        <td>Fecha</td>
                                        <td class="campositem"><%=fechaDocumento != null ? fechaDocumento : "&nbsp;"%></td>
                                        <td width="15%">&nbsp;</td>

                                        <td>Comentario</td>
                                        <td class="campositem"><%=comentarioDocumento != null ? comentarioDocumento : "&nbsp;"%></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>

                            <td>Oficina de Procedencia </td>
                        </tr>

                        <tr>

                            <td>
                                <%if (!isInternacional) {%>
                                <table width="100%" class="camposform">
                                    <tr>
                                        <td>Departamento</td>
                                        <td class="campositem">
                                            <%=departamento != null ? departamento : "&nbsp;"%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Municipio</td>
                                        <td class="campositem">
                                            <%=municipio != null ? municipio : "&nbsp;"%>
                                        </td>
                                    </tr>
                                    <%if (isMigrado) {%>
                                    <tr>
                                        <td>Comentario</td>
                                        <td class="campositem">
                                            <%=comentario != null ? comentario : "&nbsp;"%>
                                        </td>
                                    </tr>
                                    <%}%>
                                </table>
                                <table width="100%" class="camposform">
                                    <tr>
                                        <td>Tipo</td>
                                        <td class="campositem">

                                            <%=tipoOficina != null ? tipoOficina : "&nbsp;"%>

                                        </td>
                                        <td>N&uacute;mero</td>
                                        <td class="campositem">

                                            <%=numOficina != null ? numOficina : "&nbsp;"%>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Nombre</td>
                                        <td class="campositem"> <%=nombreOficina != null ? nombreOficina : "&nbsp;"%></td>
                                    </tr>
                                </table>
                                <%} else {%>
                                <table width="100%" class="camposform">
                                    <tr>
                                        <td>Oficina internacional</td>
                                        <td class="campositem">
                                            <%=oficinaInternacional%>
                                        </td>
                                    </tr>
                                </table>
                                <%}%>
                            </td>
                        </tr>
                    </table>
                    <br> 
                    <br> 
                    <br>        		


                    <!--REGISTRAR ACTOS-->
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>

                            <td class="bgnsub"><p>Actos Asociados</p></td>

                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <%if (actos == null || actos.isEmpty()) {%>
                    <table width="100%" class="camposform">
                        <tr>

                            <td colspan="5">El turno no tiene actos asociados</td>
                        </tr>
                    </table> 			   	
                    <%
                    } else {
                    %>
                    <table width="100%" class="camposform">			   	
                        <%
                            Iterator itActos = actos.iterator();
                            while (itActos.hasNext()) {
                                gov.sir.core.negocio.modelo.Acto acto = (gov.sir.core.negocio.modelo.Acto) itActos.next();
                                gov.sir.core.negocio.modelo.TipoActo tipoActo = acto.getTipoActo();
                        %>
                        <tr>

                            <td width="5%">Tipo Acto: </td>
                            <td width="15%" class="campositem"><%=  tipoActo != null ? tipoActo.getNombre() : "&nbsp;"%></td>
                            <td width="5%">Cuantia: </td>
                            <td width="15%" class="campositem"><%=  "" + java.text.NumberFormat.getInstance().format(acto.getCuantia())%></td>
                            <!---
                            yeferson inicio
                            -->
                            <td width="15%">Valor Conservaci&#242n Documental: </td>
                            <% 
                                double valorconservaciondocumental = 0.02;
                                double valorx = 0;
                                if (conservacion != null && !conservacion.equalsIgnoreCase("null")) {
                                    valorx = acto.getValor() * valorconservaciondocumental;
                                    double diferencia = valorx % 100;

                                     //Redondeo hacia la decena anterior.
                                    if (diferencia < 50) {
                                        valorx -= diferencia;
                                    } //Redondeo hacia la centena siguiente.
                                    else {
                                        valorx -= diferencia;
                                        valorx += 100;
                                    }
                               }%>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(valorx)%> </td>
                            <td width="5%">Valor: </td>
                            <td width="15%" class="campositem"><%=  java.text.NumberFormat.getInstance().format(acto.getValor())%> </td>

                            <!---fin -->

                        </tr>

                        <%
                            }//END WHILE
                        %>
                    </table>                 
                    <%
                        }//END ELSE
                    %>


                    <br>
                    <br>
                    <br>
                    <!--END ACTOS-->


                    <%--
                            /*
                            * @author : David A Rubio J
                            * @change : Se agrega botón y textarea para mostrar observaciones del testamento
                            * Caso : 
                            */
                    --%>
                    <% if ((list_testadores != null) && (!list_testadores.isEmpty())){ %>
                    <table width="100%" class="camposform">

                        <tr>
                            <td class="bgnsub"><p>Detalles testamento</p><img src="<%=request.getContextPath()%>/jsp/images/btn_verdetalles.gif" width="139" height="21" onClick="cambiarDisplay_test()" style="cursor:hand"> </td>
                            <td width="16" class="bgnsub"></td>
                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                        <tr id="campoOficio_col_uno" style="display: none">
                            <td colspan="2"> &nbsp; </td>
                        </tr>
                        <tr id="campoOficio_cero" style="display: none">
                            <td colspan="2">
                                <%=tipoDocumento != null ? tipoDocumento : "&nbsp;"%>
                                <%=numDocumento != null ? numDocumento : "&nbsp;"%> 
                                DEL&nbsp;<%=fechaDocumento != null ? fechaDocumento : "&nbsp;"%>
                                DE&nbsp;<%=municipio != null ? municipio : "&nbsp;"%> 
                                <%=departamento != null ? departamento : "&nbsp;"%> 
                            </td>
                        </tr>
                        <%
                            if (list_testadores != null && !list_testadores.isEmpty()) {
                                Ciudadano ciudadanoT;
 				TestamentoCiudadano testamentoCiudadano;
                        %>
                        <tr id="table_testadores" style="display: none">
                            <td colspan="2">
                        <table width="100%" class="camposform">
                            <tr>
                                <td colspan="2">
                                    <label> TESTADORES:</label>
                                </td>
                            </tr>
                            <%
                                for (int i = 0; i < list_testadores.size(); i++) {
                                    testamentoCiudadano=(TestamentoCiudadano)list_testadores.get(i);
 				    ciudadanoT=testamentoCiudadano.getCiudadano();
                            %>

                            <tr>
                                <td class="campositem" colspan="2">
                                    <% if(ciudadanoT.getApellido1()!=null){ %>
                                            <%= ciudadanoT.getApellido1() %>
                                    <%}if(ciudadanoT.getApellido2()!=null){ %>
                                            &nbsp; <%= ciudadanoT.getApellido2() %>
                                    <%}if(ciudadanoT.getNombre()!=null){ %>
                                            &nbsp; <%= ciudadanoT.getNombre() %> 
                                    <%}if(ciudadanoT.getTipoDoc()!=null){ %>
                                            &nbsp;-&nbsp; <%= ciudadanoT.getTipoDoc() %>
                                    <%}if(ciudadanoT.getDocumento()!=null){ %>
                                            &nbsp; <%= ciudadanoT.getDocumento() %>
                                    <%}%>
                                </td>
                            </tr>

                            <%
                                }
                            %>
                        </table>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        <tr id="campoOficio_col" style="display: none">
                            <td colspan="2"> &nbsp; </td>
                        </tr>
                        <tr id="campoOficio_cinco" style="display: none">
                            <td colspan="2">
                                El documento fue registrado en el libro de testamentos
                            </td>
                        </tr>
                        <tr id="campoOficio_uno" style="display: none">
                            <td width="220">
                                TOMO:
                            </td>
                            <td class="campositem">
                               <%=tomoTestamento%>
                            </td>
                        </tr>
                        <tr id="campoOficio_dos" style="display: none">
                            <td width="220">
                                N&Uacute;MERO ANOTACI&Oacute;N:
                            </td>
                            <td class="campositem">
                                <%=numAnotacionesTestamento%>
                            </td>
                        </tr>
                        <tr id="campoOficio_tres" style="display: none">
                            <td width="220">
                                FECHA:
                            </td>
                            <td class="campositem">
                                <%= FechaConFormato.formatear(turno.getFechaInicio(), "dd/MM/yyyy")%>
                            </td>
                        </tr>
                        <tr id="campoOficio_cuatro" style="display: none">
                            <td width="220">
                                REVOCA ESCRITURA:
                            </td>
                            <td class="campositem">
                                <%=revocaEscrituraTestamento%>
                            </td>
                        </tr>
                        <tr id="campoOficio" style="display: none">
                            <td width="220">
                                OBSERVACIONES:
                            </td>

                            </td>
                            <%if (obsTestamento != null && obsTestamento != "") {%>

                            <td class="campositem"> 
                                <%=obsTestamento%>
                            </td> 

                            <% } else {%>
                            <td class="campositem"> 
                                SIN OBSERVACIONES
                            </td> 

                            <%  }%>

                        </tr>

                    </table>
                    <% } %>
                    <br>

                    <!-- CERTIFICADOS ASOCIADOS -->
                    <%if (certificados != null || !certificados.isEmpty()) {%>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>

                            <td class="bgnsub"><p>Certificados Asociados</p></td>

                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <form action="trasladoTurno.do" method="post" name="VER_DETALLE_TURNO_CERT_ASO" id="VER_DETALLE_TURNO_CERT_ASO">
                        <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                        <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                        <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>"  value="">
                    <table width="100%" class="camposform">			

                        <tr>
                            <td width="1%"></td>
                            <td width="15%"class="contenido"><b>TURNO</b></td>
                            <td width="5%"></td>
                            <td width="1%"></td>
                            <td width="15%"class="contenido"><b>TIPO</b></td>
                            <td width="1%"></td>
                            <td width="15%"class="contenido"><b>VALOR</b></td>
                        </tr>   	
                        <%

                            Iterator iCertificados = liSolicitudesHijas.iterator();
                            int iColumna = 0;
                            final int iMaxColumnas = 5;
                            final int iAncho = 100 / iMaxColumnas;
                            String tipoCertificado = "&nbsp";
                            String tipoTarifa = "&nbsp";
                            String valor = "&nbsp";

                            while (iCertificados.hasNext()) {

                                SolicitudAsociada solAsoc = (SolicitudAsociada) iCertificados.next();
                                SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();

                                Turno tCertificado = solCert.getTurno();
                                int indexUltimaLiquidacion = solCert.getLiquidaciones().size() - 1;
                                if (indexUltimaLiquidacion >= 0) {
                                    Liquidacion ultimaLiquidacion = (Liquidacion) solCert.getLiquidaciones().get(indexUltimaLiquidacion);
                                    if (ultimaLiquidacion instanceof LiquidacionTurnoCertificado) {
                                        LiquidacionTurnoCertificado liqTurCer = (LiquidacionTurnoCertificado) ultimaLiquidacion;
                                        tipoCertificado = liqTurCer.getTipoTarifa();
                                        tipoTarifa = ((liqTurCer.getTipoTarifa() == null) ? "&nbsp;" : liqTurCer.getTipoTarifa());
                                        valor = java.text.NumberFormat.getInstance().format(liqTurCer.getValor());
                                    }
                                }
                        %>


                        <tr>
                            <td>&nbsp</td>
                            <td width="<%=iAncho%>%" class="campositem"><%=tCertificado.getIdWorkflow()%></td>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurnoCertAso('<%= tCertificado.getIdWorkflow()%>')" style="cursor:hand"></td>
                            <td>&nbsp</td>
                            <td width="<%=iAncho%>%" class="campositem"><%=tipoCertificado%></td>
                            <td>&nbsp</td>
                            <td width="<%=iAncho%>%" class="campositem"><%=valor%></td>					
                        </tr>
                        <%}//END WHILE
                        %>
                    </table>
                    </form>
                    <%
                        }
                    %>

                    <br>
                    <br>
                    <br>
                    <!-- END CERTIFICADOS ASOCIADOS -->

                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Historial de fases del turno</p></td>

                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>

                    <!--JALCAZAR Caso Mantis 0002866
                    * Se modifico la estructura de la tabla que tenia problemas de formato
                    -->
                    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="camposform">
                        <thead>
                            <tr>
                                <th width="1%"></th>
                                <th width="15%">FASE</th>
                                <th width="1%"></th>
                                <th width="15%">ATENDIDO POR</th>
                                <th width="1%"></th>
                                <th width="15%">FECHA</th>
                                <th width="1%"></th>
                                <th width="15%">RELACION FASE</th>
                                <th width="1%"></th>
                                <th width="15%">RELACION SIGUIENTE</th>
                            </tr>
                        </thead>
                        <%
                            for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
                                TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
                                String nombreUsuario = null;
                                String nombreFase = null;

                                /*JALCAZAR Caso Mantis 0002866
                                 * Se agrgo if(!turnoAux.getFase().equals("CER_SOLICITUD")) que evita agregar dos veces la fase SOLICITUD a la tabla de fases
                                 * esto debido a que cuando se crea un truno de certificacdo por antiguo sistemas o uno para certificados masivos la aplicación
                                 * automaticamente agrega a el historial del turno la fase CER_SOLICITUD que corresponde al nombre de fase SOLICITUD
                                 */
                                if (!turnoAux.getFase().equals("CER_SOLICITUD")) {
                                    if (turnoAux.getFase() != null && (turnoAux.getFase()).equals("FINALIZADO")) {
                                        /**
                                         * @author: Carlos Torres
                                         * @change: 11604: Acta - Requerimiento
                                         * No 004_589_Funcionario_Fase_
                                         * Entregado
                                         */
                                        turnoAux.setNombreFase("FINALIZADO");
                                    }
                                    /**
                                     * @author: Julio Alcazar
                                     * @change: 5680: Acta - Requerimiento No
                                     * 212 - Pantalla de administración - Ver
                                     * detalles de turno, Se modifica para que
                                     * sea tomado el nombre de usuario de quien
                                     * atendio en la fase correspondiente.
                                     */
                                    if (turnoAux.getUsuarioAtiende() != null) {
                                        nombreUsuario = turnoAux.getUsuarioAtiende().getUsername();
                                    }
                                    if (turnoAux.getFase() != null) {
                                        nombreFase = turnoAux.getNombreFase();
                                    }
                                    if (nombreFase == null) {
                                        nombreFase = turnoAux.getFase();
                                    }

                                    if (nombreFase.equals("DECIDIR MAYOR VALOR")) {
                                        nombreUsuario = "USUARIO AUTOMÁTICO";
                                    }
                        %>

                        <tbody>
                            <tr>

                                <td width="1%"></td>
                                <td width="15%" class="campositem"><%=  nombreFase != null ? nombreFase.replace('_', ' ') : "&nbsp;"%></td>
                                <td width="1%"></td>
                                <td width="15%" class="campositem"><%=  nombreUsuario != null ? nombreUsuario : "&nbsp;"%></td>
                                <td width="1%"></td>
                                <td width="15%" class="campositem"><%=  turnoAux.getFecha() != null ? FechaConFormato.formatear(turnoAux.getFecha(), "dd/MM/yyyy hh:mm:ss a") : "&nbsp;"%> </td>

                                <td width="1%"></td>
                                <%String idRelacion = null;
                                    idRelacion = turnoAux.getIdRelacion();
                                    if (idRelacion == null) {
                                        idRelacion = "&nbsp";
                                    }%>
                                <td width="15%" class="campositem"><%=idRelacion%> </td>


                                <td width="1%"></td>
                                <%String idRelacionSig = null;
                                    idRelacionSig = turnoAux.getIdRelacionSiguiente();
                                    if (idRelacionSig == null) {
                                        idRelacionSig = "&nbsp";
                                    }%>
                                <td width="15%" class="campositem"><%=idRelacionSig%> </td>

                            </tr>
                        </tbody>
                        <%
                                }
                            }
                        %>
                    </table>

                    <br>
                    <br>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Datos del solicitante</p></td>

                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>
                    <table width="100%" class="camposform">
                        <tr>

                            <td width="220">Nombre del Solicitante: </td>
                            <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getNombreCompletoCiudadano() + "&nbsp;"%> </td>
                            <td width="220">Telefono: </td>
                            <td class="campositem"> <%=  (ciudadano != null && ciudadano.getTelefono() != null) ? ciudadano.getTelefono() : "&nbsp;"%> </td>
                        </tr>
                        <tr>

                            <td width="220">Tipo de Documento del Solicitante: </td>
                            <td width="220" class="campositem"> <%= (ciudadano == null) ? "&nbsp;" : ciudadano.getTipoDoc() + "&nbsp;"%> </td>
                            <td width="220">N&uacute;mero de Documento del Solicitante: </td>
                            <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getDocumento() + "&nbsp;"%> </td>
                        </tr>
                    </table> 


                    <%

                        Iterator itPagos = liquidaciones.iterator();
                    %>


                    <!--DATOS DE LA LIQUIDACIÓN.-->
                    <br>
                    <br>
                    <br>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                        <tr>

                            <td class="bgnsub"><p>Pagos asociados</p></td>

                            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                        </tr>
                    </table>


                    <table width="100%" class="camposform">
                        <%
                            double valorCert = 0;
                            List certs = solicitud.getSolicitudesHijas();
                            Iterator ic = certs.iterator();
                            for (; ic.hasNext();) {
                                SolicitudAsociada solA = (SolicitudAsociada) ic.next();

                                SolicitudCertificado solC;
                                solC = (gov.sir.core.negocio.modelo.SolicitudCertificado) solA.getSolicitudHija();
                                if (solC != null && solC.getLiquidaciones().size() > 0) {
                                    LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado) solC.getLiquidaciones().get(0);
                                    valorCert += liq.getValor();
                                }

                            }
                            /**
                             * Author: Santiago Vásquez Change:
                             * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                             */
                            Boolean devolucion = (Boolean) session.getAttribute(AWTrasladoTurno.DEVOLUCION);
                            if (!devolucion.booleanValue()) {
                                if (itPagos.hasNext()) {
                                    LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) itPagos.next();
                        %>                
                        <tr>

                            <td width="15%">Valor derechos de registro: </td>
                            <td width="20%" class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorDerechos())%> </td>
                            <td width="15%">Valor certificados asociados: </td>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(valorCert)%> </td>
                        </tr>
                        <tr>

                            <td width="220">Valor de impuestos: </td>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorImpuestos())%> </td>
                            <td width="220">Valor de multa: </td>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorMora())%> </td>
                        </tr>
                        <tr>

                            <td width="220">Otro impuesto: </td>
                            <td class="campositem"> <%= liqReg.getOtroImpuesto() != null ? liqReg.getOtroImpuesto() : "&nbsp;"%> </td>
                            <td width="220">Valor otro impuesto: </td>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorOtroImp())%> </td>
                        </tr>
                        <!--     
                             * Author: Yeferson Martinez
                             * Change: agregado para conservacion documental
                             * inicio
                        -->

                        <tr> 
                            <td width="220">Valor Conservaci&#242n Documental </td>
                            <%
                                double v = 0.0;
                                if (conservacion != null && !conservacion.equalsIgnoreCase("null")) {
                                    v = Double.parseDouble(conservacion);
                                }%>
                            <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(v)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <% //Mostrar las aplicaciones de pago correspondientes
                            List canales = (List) session.getAttribute(AWTrasladoTurno.LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS);
                            List efectivo = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO);                            
                            if (efectivo != null && !efectivo.isEmpty()) {
                             
                                for (int i = 0; i < efectivo.size(); i=i+3) {
                                int p = 0;
                                if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    if(split[6].equalsIgnoreCase(DocumentoPago.PAGO_EFECTIVO) && p == 0 ){
                                        p++;

                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null) ? split[4] : "&nbsp;"%> </td>
                                              <%
                                             if(efectivo.get(i+1).equals("3")){

                                             %>
                                            <td width="220">Forma de Pago Corregida</td>      
                                                   <td><a href="javascript:verNota('correccion.canal.recaudo.transladoturno.view?posEfec=0&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                          
                                              <%
                                            }
                                             %>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                        
                        <tr>                             
                            <td colspan="2" align="left">Forma de Pago: </td>
                            <td colspan="2" width="220">Valor Liquidado: </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="campositem"> <%=DocumentoPago.PAGO_EFECTIVO%> </td>

                            <td colspan="2" class="campositem"> <%= efectivo.get(i)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <%
                                }
                            }

                            List convenio = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO);
                            if (convenio != null && !convenio.isEmpty()) {

                                for (int i = 0; i < convenio.size(); i++) {

                                int p = 0;
                                if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    if(split[6].equalsIgnoreCase(DocumentoPago.PAGO_CONVENIO) && p == 0 ){
                                        p++;

                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null) ? split[4] : "&nbsp;"%> </td>
                                            
                                                <%
                                                 if(convenio.get(i+1).equals("3")){

                                                %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                   <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConv=0&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                             
                                                 <%
                                               }
                                                %>
                            </tr>
                            <%
                                        }
                                   }
                                }
                            %>
                        
                        <tr>                             
                            <td colspan="2" align="left">Forma de Pago: </td>
                            <td colspan="2" width="220">Valor Liquidado: </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="campositem"> <%=DocumentoPago.PAGO_CONVENIO%> </td>

                            <td colspan="2" class="campositem"> <%= convenio.get(i)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <%
                                }
                            }

                            List pse = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE);
                            if (pse != null && !pse.isEmpty()) {

                            String pasoPse = "";
                            for (int i = 0; i < pse.size(); i++) {
                            
                             pasoPse = String.valueOf(((List) pse.get(i)).get(1));
                            Object obs = ((List) pse.get(i)).get(4);
                              String ob = obs.toString();

                             if(!canales.isEmpty()){
                                boolean es = true;
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoPse)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%=split[8]+"-"+split[2]%> </td>
                            
                            <%
                                    }                                                           
                                }
                                if(ob.equals("3")){
                                es = false;
                                %>
                                            <td width="220">Forma de Pago Corregida</td>      
                                               <td><a href="javascript:verNota('correccion.canal.recaudo.view?posPse=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                            
                               </tr>
                                 <%
                               }
                               if(es){
                            %>
                                    </tr>
                             <%
                                }
                            }
                            %>
                        
                        <tr>                             
                            <td width="220">Forma de Pago: </td>
                            <td width="220">Banco/Franquicia: </td>
                            <td width="220">Número Aprobación: </td>
                            <td width="220">Fecha Documento: </td>
                            <td width="220">Valor Liquidado: </td>
                        </tr>
                        <tr>
                            <td class="campositem"> <%=DocumentoPago.PAGO_ELECTRONICO_PSE%> </td>

                            <td class="campositem"> <%= ((List) pse.get(i)).get(0)%> </td>

                            <td class="campositem"> <%= ((List) pse.get(i)).get(1)%> </td>                            

                            <td class="campositem"> <%= ((List) pse.get(i)).get(2)%> </td>
                            
                            <td class="campositem"> <%= ((List) pse.get(i)).get(3)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <%
                                }
                            }
                            List debito = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO);
                            if (debito != null && !debito.isEmpty()) {                            

                                for (int i = 0; i < debito.size(); i++) {                                      
                            %>
                                <tr>
                                        <td width="220">Canal de Recaudo: </td>
                                        <td class="campositem"> <%=((List) debito.get(i)).get(5)%> </td>
                                    <%if(((List) debito.get(i)).get(9) != null){%>    
                                        <td width="220">Cuenta Destino: </td>
                                        <td class="campositem"> <%= ((List) debito.get(i)).get(10)+"-"+((List) debito.get(i)).get(9)%> </td> 
                                    <%}%>
                                    
                                        <%
                                          Object obs = ((List) debito.get(i)).get(5);
                                          String ob = obs.toString();
                                            if(ob.equals("3")){
                                            %>
                                             <td width="220">Estado Actual</td>  
                                               <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTDebito=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                             
                                             <%
                                           }
                                        %> 
                                </tr>

                                <tr>
                                    <%if(((List) debito.get(i)).get(6) != null){%>
                                        <td align="left">Forma de Pago: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(0) != null){%>
                                        <td width="220">Banco/Franquicia: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(1) != null){%>
                                        <td width="220">No. Documento Pago: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(2) != null){%>
                                        <td width="220">No. Aprobación: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(3) != null){%>
                                        <td width="220">Fecha Documento: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(4) != null){%>
                                        <td width="220">Valor Liquidado: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(7) != null){%>
                                        <td width="220">Banco: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(8) != null){%>
                                        <td width="220">No. Consignación: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(11) != null){%>
                                        <td width="220">Valor Documento Pago: </td>
                                    <%}%>
                                </tr>
                                <tr>
                                    <%if(((List) debito.get(i)).get(6) != null){%>
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(6) != null ? ((List) debito.get(i)).get(6) : "&nbsp;"%> </td> 
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(0) != null){%>                            
                                        <td class="campositem"> <%= ((List) debito.get(i)).get(0) != null ? ((List) debito.get(i)).get(0) : "&nbsp;"%> </td>  
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(1) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(1) != null ? ((List) debito.get(i)).get(1) : "&nbsp;"%> </td>                           
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(2) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(2) != null ? ((List) debito.get(i)).get(2) : "&nbsp;"%> </td>                            
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(3) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(3) != null ? ((List) debito.get(i)).get(3) : "&nbsp;"%> </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(4) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(4) != null ? ((List) debito.get(i)).get(4) : "&nbsp;"%> </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(7) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(7) != null ? ((List) debito.get(i)).get(7) : "&nbsp;"%> </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(8) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(8) != null ? ((List) debito.get(i)).get(8) : "&nbsp;"%> </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(11) != null){%> 
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(11) != null ? ((List) debito.get(i)).get(11) : "&nbsp;"%> </td>
                                    <%}%>
                                </tr>
                                <tr>
                                    <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                                </tr>
                        <%
                                }
                            }

                            List credito = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO);
                            if (credito != null && !credito.isEmpty()) {

                                String pasoTc = "";
                                String pasosTc = "";
                                for (int i = 0; i < credito.size(); i++) {

                                    pasoTc = String.valueOf(((List) credito.get(i)).get(1));

                                    pasosTc = String.valueOf(((List) credito.get(i)).get(2));
                                if(!canales.isEmpty()){
                                boolean es = true;
                                Object obs = ((List) credito.get(i)).get(5);
                                String ob = obs.toString();
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasosTc)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%=!split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                                            
                            <%
                                    }                                                           
                                }
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTCredito=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                        
                                   </tr>
                                     <%
                                   }
                                        if(es){
                                     %>
                                    </tr>
                                        <%
                                           }
                                }
                            %>
                        
                                                <tr>                             
                            <td align="left">Forma de Pago: </td>
                            <td width="220">Banco/Franquicia: </td>
                            <td width="220">No. Documento Pago: </td>
                            <td width="220">No. Aprobación: </td>
                            <td width="220">Fecha Documento: </td>
                            <td width="220">Valor Liquidado: </td>
                        </tr>
                        <tr>
                            <td class="campositem"> <%=DocumentoPago.PAGO_TARJETA_CREDITO%> </td>

                            <td class="campositem"> <%= ((List) credito.get(i)).get(0)%> </td>

                            <td class="campositem"> <%= ((List) credito.get(i)).get(1)%> </td>                            

                            <td class="campositem"> <%= ((List) credito.get(i)).get(2)%> </td>                           

                            <td class="campositem"> <%= ((List) credito.get(i)).get(3)%> </td>

                            <td class="campositem"> <%= ((List) credito.get(i)).get(4)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <%
                                }
                            }

                            List cheque = (List) session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE);
                            if (cheque != null && !cheque.isEmpty()) {

                                String pasoCheque = "";
                                for (int i = 0; i < cheque.size(); i++) {

                                    pasoCheque = String.valueOf(((List) cheque.get(i)).get(1));
                                if(!canales.isEmpty()){
                                boolean es = true;
                                Object obs = ((List) cheque.get(i)).get(6);
                                String ob = obs.toString();
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoCheque)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                           
                                           
                            <%
                                    }                                                           
                                }
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                       <td><a href="javascript:verNota('correccion.canal.recaudo.view?posCheque=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                        
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
                        <tr>                             
                            <td align="left">Forma de Pago: </td>
                            <td width="220">Banco/Franquicia: </td>
                            <td width="220">No. Documento Pago: </td>
                            <td width="220">Valor Documento Pago: </td>
                            <td width="220">Fecha Documento: </td>
                            <td width="220">Valor Liquidado: </td>
                        </tr>
                            <%
                                if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                            %>
                            <td width="220">Pago Anulado: </td>
                            <% } %>
                        
                        <tr>
                            <td class="campositem"> <%=DocumentoPago.PAGO_CHEQUE%> </td>

                            <td class="campositem"> <%= ((List) cheque.get(i)).get(0)%> </td>

                            <td class="campositem"> <%= ((List) cheque.get(i)).get(1)%> </td>                            

                            <td class="campositem"> <%= ((List) cheque.get(i)).get(2)%> </td>

                            <td class="campositem"> <%= ((List) cheque.get(i)).get(3)%> </td>

                            <td class="campositem"> <%= ((List) cheque.get(i)).get(4)%> </td>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <tr>
                            <%
                                if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                            %>
                            <td class="campositem"> <%= ((List) cheque.get(i)).get(5)%> </td>
                            <% } %>
                        </tr>
                        <%
                                }
                            }

                            List consignacion = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION);
                            if (consignacion != null && !consignacion.isEmpty()) {

                                String pasoConsig = "";
                                for (int i = 0; i < consignacion.size(); i++) {

                                    pasoConsig = String.valueOf(((List) consignacion.get(i)).get(1));
                            if(!canales.isEmpty()){
                            Object obs = ((List) consignacion.get(i)).get(6);
                            String ob = obs.toString();
                            boolean es = true;
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoConsig)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%=!split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                                          
                            <%
                                    }                                                           
                                }
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                               <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConsig=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                         
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
                        <tr>
                            <td align="left">Forma de Pago: </td>

                            <td width="220">No. Documento Pago: </td>
                            <td width="220">Valor Documento Pago: </td>
                            <td width="220">Fecha Documento: </td>
                            <td width="220">Valor Liquidado: </td>
                            <%
                                if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                            %>
                            <td width="220">Pago Anulado: </td>
                            <% } %>
                        </tr>
                        <tr>
                            <td class="campositem"> <%=DocumentoPago.PAGO_CONSIGNACION%> </td>



                            <td class="campositem"> <%= ((List) consignacion.get(i)).get(1)%> </td>

                            <td class="campositem"> <%= ((List) consignacion.get(i)).get(2)%> </td>

                            <td class="campositem"> <%= ((List) consignacion.get(i)).get(3)%> </td>

                            <td class="campositem"> <%= ((List) consignacion.get(i)).get(4)%> </td>
                         </tr>  
                            <%
                                if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                            %>
                            <td class="campositem"> <%= ((List) consignacion.get(i)).get(5)%> </td>
                            <% } %>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>                         
                            
                        <%
                                }
                            }
                            List general = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL);
                            if (general != null && !general.isEmpty()) {
                                for (int i = 0; i < general.size(); i++) {
                        %>
                        <tr>
                            <td width="220">Canal de Recaudo: </td>
                            <td class="campositem"> <%=((List) general.get(i)).get(5)%> </td>
                            <%if (((List) general.get(i)).get(9) != null) {%>    
                            <td width="220">Cuenta Destino: </td>
                            <td class="campositem"> <%= ((List) general.get(i)).get(10) + "-" + ((List) general.get(i)).get(9)%> </td> 
                            <%}%>
                              
                                <%
                                  Object obs = ((List) general.get(i)).get(12);
                                  String ob = obs.toString();
                               if(ob.equals("3")){
                                %>
                                <td width="220">Forma de Pago Corregida</td>  
                                   <td><a href="javascript:verNota('correccion.canal.recaudo.transladoturno.view?posGral=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0','LIQUIDAR')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                           
                                 <%
                               }
                                %> 
                        </tr>
                        <tr>                             
                            <%if (((List) general.get(i)).get(6) != null) {%>
                            <td align="left">Forma de Pago: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(0) != null) {%>
                            <td width="220">Banco/Franquicia: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(1) != null) {%>
                            <td width="220">No. Documento Pago: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(2) != null) {%>
                            <td width="220">No. Aprobación: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(3) != null) {%>
                            <td width="220">Fecha Documento: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(4) != null) {%>
                            <td width="220">Valor Pagado: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(7) != null) {%>
                            <td width="220">Banco: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(8) != null) {%>
                            <td width="220">No. Consignación: </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(11) != null) {%>
                            <td width="220">Valor Documento Pago: </td>
                            <%}%>
                        </tr>
                        <tr>                                
                            <%if (((List) general.get(i)).get(6) != null) {%>
                            <td class="campositem"> <%= ((List) general.get(i)).get(6) != null ? ((List) general.get(i)).get(6) : "&nbsp;"%> </td> 
                            <%}%>
                            <%if (((List) general.get(i)).get(0) != null) {%>                            
                            <td class="campositem"> <%= ((List) general.get(i)).get(0) != null ? ((List) general.get(i)).get(0) : "&nbsp;"%> </td>  
                            <%}%>
                            <%if (((List) general.get(i)).get(1) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(1) != null ? ((List) general.get(i)).get(1) : "&nbsp;"%> </td>                           
                            <%}%>
                            <%if (((List) general.get(i)).get(2) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(2) != null ? ((List) general.get(i)).get(2) : "&nbsp;"%> </td>                            
                            <%}%>
                            <%if (((List) general.get(i)).get(3) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(3) != null ? ((List) general.get(i)).get(3) : "&nbsp;"%> </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(4) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(4) != null ? ((List) general.get(i)).get(4) : "&nbsp;"%> </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(7) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(7) != null ? ((List) general.get(i)).get(7) : "&nbsp;"%> </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(8) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(8) != null ? ((List) general.get(i)).get(8) : "&nbsp;"%> </td>
                            <%}%>
                            <%if (((List) general.get(i)).get(11) != null) {%> 
                            <td class="campositem"> <%= ((List) general.get(i)).get(11) != null ? ((List) general.get(i)).get(11) : "&nbsp;"%> </td>
                            <%}%>
                        </tr>
                        <tr>
                            <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                        </tr>
                        <%
                                }
                            }
                            List mayorValor = (List) session.getAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR);
                            if (mayorValor != null && !mayorValor.isEmpty()) {
                                //if(liqReg.getJustificacionMayorValor()!=null && liqReg.getJustificacionMayorValor().length()>0){
                                //Double valorCerMV = new Double (liqReg.getValor() - (liqReg.getValorDerechos() + liqReg.getValorImpuestos()));
                        %>	

                        <td width="220"><b>Pago Mayor Valor</b></td>
            <tr>
                <td width="220">Razón del pago: </td>
                <td class="campositem"> <%= mayorValor.get(0)%> </td>
            </tr>
            <tr>

                <td width="220">Valor derechos de registro: </td>
                <td class="campositem"> <%= mayorValor.get(1)%> </td>
                <td width="220">Valor certificados asociados: </td>
                <td class="campositem"> <%= mayorValor.get(2)%> </td>
            </tr>
            <tr>

                <td width="220">Valor de impuestos: </td>
                <td class="campositem"> <%= mayorValor.get(3)%> </td>
                <td width="220">Valor Liquidado: </td>
                <td class="campositem"> <%= mayorValor.get(4)%> </td>
            </tr>
            <tr>

                <td width="220">Valor Conservaci&#242n Documental </td>
                <%
                    double vC = 0.0;
                    if (conservacionmayor != null && !conservacionmayor.equalsIgnoreCase("null")) {
                        vC = Double.parseDouble(conservacionmayor);
                    }%>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(vC)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>

            <% //Mostrar las aplicaciones de pago correspondientes
                List efectivoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM);
                if (efectivoMV != null && !efectivoMV.isEmpty()) {

                    for (int i = 0; i < efectivoMV.size(); i=i+3) {

                                int p = 0;
                                if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    if(split[6].equalsIgnoreCase(DocumentoPago.PAGO_EFECTIVO) && p == 0 ){
                                        p++;

                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                           
                              <%
                                    }                                                           
                                }
                                    Object obs = efectivoMV.get(i+1);
                                    String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                       <td><a href="javascript:verNota('correccion.canal.recaudo.view?posEfecMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                           
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        <tr>                             
                <td colspan="2" align="left">Forma de Pago: </td>
                <td colspan="2" width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td colspan="2" class="campositem"> <%=DocumentoPago.PAGO_EFECTIVO%> </td>

                <td colspan="2" class="campositem"> <%= efectivoMV.get(i)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List convenioMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM);
                if (convenioMV != null && !convenioMV.isEmpty()) {

                    for (int i = 0; i < convenioMV.size(); i++) {

                                int p = 0;
if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    if(split[6].equalsIgnoreCase(DocumentoPago.PAGO_CONVENIO) && p == 0 ){
                                        p++;

                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                               <%
                                    }                                                           
                                }
                                     Object obs = convenioMV.get(i+1);
                                  String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posConvMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
            <tr>                             
                <td colspan="2" align="left">Forma de Pago: </td>
                <td colspan="2" width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td colspan="2" class="campositem"> <%=DocumentoPago.PAGO_CONVENIO%> </td>

                <td colspan="2" class="campositem"> <%= convenioMV.get(i)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List pseMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM);
                if (pseMV != null && !pseMV.isEmpty()) {

                String pasoPseMv = "";
                for (int i = 0; i < pseMV.size(); i++) {
          
                    pasoPseMv = String.valueOf((((List) pseMV.get(i)).get(1)));
if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoPseMv)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null)? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                                <%
                                    }                                                           
                                }
                                    Object obs = ((List) pseMV.get(i)).get(4);
                                  String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                           <td><a href="javascript:verNota('correccion.canal.recaudo.view?posPseMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                        
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
            <tr>                             
                <td width="220">Forma de Pago: </td>
                <td width="220">Banco/Franquicia: </td>
                <td width="220">Número Aprobación: </td>
                <td width="220">Fecha Documento: </td>
                <td width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td class="campositem"> <%=DocumentoPago.PAGO_ELECTRONICO_PSE%> </td>

                <td class="campositem"> <%= ((List) pseMV.get(i)).get(0)%> </td>

                <td class="campositem"> <%= ((List) pseMV.get(i)).get(1)%> </td>              

                <td class="campositem"> <%= ((List) pseMV.get(i)).get(2)%> </td>

                <td class="campositem"> <%= ((List) pseMV.get(i)).get(3)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List debitoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM);
                if (debitoMV != null && !debitoMV.isEmpty()) {
                    for (int i = 0; i < debitoMV.size(); i++) {
            %>
            <tr>
                    <td width="220">Canal de Recaudo: </td>
                    <td class="campositem"> <%=((List) debitoMV.get(i)).get(5)%> </td>
                <%if(((List) debitoMV.get(i)).get(9) != null){%>    
                    <td width="220">Cuenta Destino: </td>
                    <td class="campositem"> <%= ((List) debitoMV.get(i)).get(10)+"-"+((List) debitoMV.get(i)).get(9)%> </td> 
                <%}%>
                  
                    <%
                      Object obs = ((List) debitoMV.get(i)).get(5);
                      String ob = obs.toString();
                   if(ob.equals("3")){

                    %>
                    <td width="220">Forma de Pago Corregida</td>    
                     <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTDebitoMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                   
                     <%
                   }
                    %>
            </tr>     
            <tr>                             
                <%if(((List) debitoMV.get(i)).get(6) != null){%>
                    <td align="left">Forma de Pago: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(0) != null){%>
                    <td width="220">Banco/Franquicia: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(1) != null){%>
                    <td width="220">No. Documento Pago: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(2) != null){%>
                    <td width="220">No. Aprobación: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(3) != null){%>
                    <td width="220">Fecha Documento: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(4) != null){%>
                    <td width="220">Valor Liquidado: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(7) != null){%>
                    <td width="220">Banco: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(8) != null){%>
                    <td width="220">No. Consignación: </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(11) != null){%>
                    <td width="220">Valor Documento Pago: </td>
                <%}%>
            </tr>
            <tr>
                <%if(((List) debitoMV.get(i)).get(6) != null){%>
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(6) != null ? ((List) debitoMV.get(i)).get(6) : "&nbsp;"%> </td> 
                <%}%>
                <%if(((List) debitoMV.get(i)).get(0) != null){%>                            
                    <td class="campositem"> <%= ((List) debitoMV.get(i)).get(0) != null ? ((List) debitoMV.get(i)).get(0) : "&nbsp;"%> </td>  
                <%}%>
                <%if(((List) debitoMV.get(i)).get(1) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(1) != null ? ((List) debitoMV.get(i)).get(1) : "&nbsp;"%> </td>                           
                <%}%>
                <%if(((List) debitoMV.get(i)).get(2) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(2) != null ? ((List) debitoMV.get(i)).get(2) : "&nbsp;"%> </td>                            
                <%}%>
                <%if(((List) debitoMV.get(i)).get(3) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(3) != null ? ((List) debitoMV.get(i)).get(3) : "&nbsp;"%> </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(4) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(4) != null ? ((List) debitoMV.get(i)).get(4) : "&nbsp;"%> </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(7) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(7) != null ? ((List) debitoMV.get(i)).get(7) : "&nbsp;"%> </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(8) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(8) != null ? ((List) debitoMV.get(i)).get(8) : "&nbsp;"%> </td>
                <%}%>
                <%if(((List) debitoMV.get(i)).get(11) != null){%> 
                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(11) != null ? ((List) debitoMV.get(i)).get(11) : "&nbsp;"%> </td>
                <%}%>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List creditoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM);
                if (creditoMV != null && !creditoMV.isEmpty()) {

                    String pasoTcMv = "";
                    String pasosTcMv = "";
                    for (int i = 0; i < creditoMV.size(); i++) {

                            pasoTcMv = String.valueOf((((List) creditoMV.get(i)).get(1)));

                            pasosTcMv = String.valueOf((((List) creditoMV.get(i)).get(2)));
                            if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasosTcMv)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null) ? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                          <%
                                    }                                                           
                                }
                                   Object obs = ((List) creditoMV.get(i)).get(5);
                                  String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                  <td><a href="javascript:verNota('correccion.canal.recaudo.view?posTCreditoMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                            
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
            <tr>                             
                <td align="left">Forma de Pago: </td>
                <td width="220">Banco/Franquicia: </td>
                <td width="220">No. Documento Pago: </td>
                <td width="220">No. Aprobación: </td>
                <td width="220">Fecha Documento: </td>
                <td width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td class="campositem"> <%=DocumentoPago.PAGO_TARJETA_CREDITO%> </td>

                <td class="campositem"> <%= ((List) creditoMV.get(i)).get(0)%> </td>

                <td class="campositem"> <%= ((List) creditoMV.get(i)).get(1)%> </td>

                <td class="campositem"> <%= ((List) creditoMV.get(i)).get(2)%> </td>

                <td class="campositem"> <%= ((List) creditoMV.get(i)).get(3)%> </td>

                <td class="campositem"> <%= ((List) creditoMV.get(i)).get(4)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List chequeMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM);
                if (chequeMV != null && !chequeMV.isEmpty()) {

                    String pasoChequeMv = "";
                    for (int i = 0; i < chequeMV.size(); i++) {

                        pasoChequeMv = String.valueOf((((List) chequeMV.get(i)).get(1)));
if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoChequeMv)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null) ? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                             <%
                                    }                                                           
                                }
                                  Object obs = ((List) chequeMV.get(i)).get(6);
                                  String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?posChequeMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                           
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
            <tr>                             
                <td align="left">Forma de Pago: </td>
                <td width="220">Banco/Franquicia: </td>
                <td width="220">No. Documento Pago: </td>
                <td width="220">Valor Documento Pago: </td>
                <td width="220">Fecha Documento: </td>
                <td width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td class="campositem"> <%=DocumentoPago.PAGO_CHEQUE%> </td>

                <td class="campositem"> <%= ((List) chequeMV.get(i)).get(0)%> </td>

                <td class="campositem"> <%= ((List) chequeMV.get(i)).get(1)%> </td>

                <td class="campositem"> <%= ((List) chequeMV.get(i)).get(2)%> </td>

                <td class="campositem"> <%= ((List) chequeMV.get(i)).get(3)%> </td>

                <td class="campositem"> <%= ((List) chequeMV.get(i)).get(4)%> </td>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                    }
                }

                List consignacionMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM);
                if (consignacionMV != null && !consignacionMV.isEmpty()) {

                    String pasoConsigMv = "";
                    for (int i = 0; i < consignacionMV.size(); i++) {

                        pasoConsigMv = String.valueOf((((List) consignacionMV.get(i)).get(1)));
if(!canales.isEmpty()){
                                for (int j = 0; j < canales.size(); j++) {
                                    String[] split = canales.get(j).toString().split("-");
                                    
                                    if(split[7].equalsIgnoreCase(pasoConsigMv)){
                                        
                            %>
                            <tr>
                                            <td width="220">Canal de Recaudo: </td>
                                            <td class="campositem"> <%= !split[4].equals(null) ? split[4] : "&nbsp;"%> </td>
                                            <td width="220">Cuenta Destino: </td>
                                            <td class="campositem"> <%= split[8]+"-"+split[2]%> </td>
                             <%
                                    }                                                           
                                }
                                    Object obs = ((List) consignacionMV.get(i)).get(6);
                                  String ob = obs.toString();
                                    boolean es = true;
                                    if(ob.equals("3")){
                                    es = false;
                                    %>
                                                <td width="220">Forma de Pago Corregida</td>      
                                                <td><a href="javascript:verNota('correccion.canal.recaudo.view?=posConsigMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                          
                                   </tr>
                                        <%
                                      }
                                        if(es){
                                     %>
                                    </tr>
                                    <%
                                       }
                                }
                            %>
                        
            <tr>
                <td align="left">Forma de Pago: </td>

                <td width="220">No. Documento Pago: </td>
                <td width="220">Valor Documento Pago: </td>
                <td width="220">Fecha Documento: </td>
                <td width="220">Valor Liquidado: </td>
            </tr>
            <tr>
                <td class="campositem"> <%=DocumentoPago.PAGO_CONSIGNACION%> </td>



                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(1)%> </td>

                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(2)%> </td>

                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(3)%> </td>

                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(4)%> </td>
            </tr>

            <%
                    }
                }

                List generalMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL_VM);
                    if (generalMV != null && !generalMV.isEmpty()) {
                        for (int i = 0; i < generalMV.size(); i++) {
            %>
            <tr>
                <td width="220">Canal de Recaudo: </td>
                <td class="campositem"> <%=((List) generalMV.get(i)).get(5)%> </td>
                <%if (((List) generalMV.get(i)).get(9) != null) {%>    
                <td width="220">Cuenta Destino: </td>
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(10) + "-" + ((List) generalMV.get(i)).get(9)%> </td> 
                <%}%>
                                <%
                                  Object obs = ((List) generalMV.get(i)).get(12);
                                  String ob = obs.toString();
                              if(ob.equals("3")){
                            
                                %>
                                    <td width="220">Forma de Pago Corregida</td>      
                                 <td><a href="javascript:verNota('correccion.canal.recaudo.transladoturno.view?posGralMV=<%=i%>&&justview=1','Detalles','width=900,height=650,scrollbars=yes,top=0,left=0')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
                    
                                 <%
                               }
                                %>
            </tr>
            <tr>                             
                <%if (((List) generalMV.get(i)).get(6) != null) {%>
                <td align="left">Forma de Pago: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(0) != null) {%>
                <td width="220">Banco/Franquicia: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(1) != null) {%>
                <td width="220">No. Documento Pago: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(2) != null) {%>
                <td width="220">No. Aprobación: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(3) != null) {%>
                <td width="220">Fecha Documento: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(4) != null) {%>
                <td width="220">Valor Pagado: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(7) != null) {%>
                <td width="220">Banco: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(8) != null) {%>
                <td width="220">No. Consignación: </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(11) != null) {%>
                <td width="220">Valor Documento Pago: </td>
                <%}%>
            </tr>
            <tr>                                
                <%if (((List) generalMV.get(i)).get(6) != null) {%>
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(6) != null ? ((List) generalMV.get(i)).get(6) : "&nbsp;"%> </td> 
                <%}%>
                <%if (((List) generalMV.get(i)).get(0) != null) {%>                            
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(0) != null ? ((List) generalMV.get(i)).get(0) : "&nbsp;"%> </td>  
                <%}%>
                <%if (((List) generalMV.get(i)).get(1) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(1) != null ? ((List) generalMV.get(i)).get(1) : "&nbsp;"%> </td>                           
                <%}%>
                <%if (((List) generalMV.get(i)).get(2) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(2) != null ? ((List) generalMV.get(i)).get(2) : "&nbsp;"%> </td>                            
                <%}%>
                <%if (((List) generalMV.get(i)).get(3) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(3) != null ? ((List) generalMV.get(i)).get(3) : "&nbsp;"%> </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(4) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(4) != null ? ((List) generalMV.get(i)).get(4) : "&nbsp;"%> </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(7) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(7) != null ? ((List) generalMV.get(i)).get(7) : "&nbsp;"%> </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(8) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(8) != null ? ((List) generalMV.get(i)).get(8) : "&nbsp;"%> </td>
                <%}%>
                <%if (((List) generalMV.get(i)).get(11) != null) {%> 
                <td class="campositem"> <%= ((List) generalMV.get(i)).get(11) != null ? ((List) generalMV.get(i)).get(11) : "&nbsp;"%> </td>
                <%}%>
            </tr>
            <tr>
                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
            </tr>
            <%
                        }
                    }
                }//else{
            %>      

            <%
                }
            } else {
                while (itPagos.hasNext()) {
                    LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) itPagos.next();
                    if (liqReg.getJustificacionMayorValor() != null && liqReg.getJustificacionMayorValor().length() > 0) {
                        Double valorCerMV = new Double(liqReg.getValor() - (liqReg.getValorDerechos() + liqReg.getValorImpuestos()));
            %>	
    
            <tr>
                <td width="220"><b>Pago Mayor Valor</b></td>
            </tr>
            <tr>
                <td width="220">Razón del pago: </td>
                <td class="campositem"> <%= liqReg.getJustificacionMayorValor()%> </td>
            </tr>
            <tr>

                <td width="220">Valor derechos de registro: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorDerechos())%> </td>
                <td width="220">Valor certificados asociados: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(valorCerMV)%> </td>
            </tr>
            <tr>

                <td width="220">Valor de impuestos: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorImpuestos())%> </td>
                <td width="220">Valor Liquidado: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValor())%> </td>
            </tr>
            <%
            } else {
            %>      
            <tr>

                <td width="220">Valor derechos de registro: </td>
                <td width="220" class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorDerechos())%> </td>
                <td width="220">Valor certificados asociados: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(valorCert)%> </td>
            </tr>
            <tr>

                <td width="220">Valor de impuestos: </td>
                <td width="220" class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorImpuestos())%> </td>
                <td width="220">Valor de multa: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorMora())%> </td>
            </tr>
            <tr>

                <td width="220">Otro impuesto: </td>
                <td width="220" class="campositem"> <%= liqReg.getOtroImpuesto() != null ? liqReg.getOtroImpuesto() : "&nbsp;"%> </td>
                <td width="220">Valor otro impuesto: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqReg.getValorOtroImp())%> </td>

                <% //Mostrar las aplicaciones de pago correspondientes
                    Pago pagoAux = liqReg.getPago();
                    if (pagoAux != null) {
                        List listaAplicaciones = pagoAux.getAplicacionPagos();

                        if (listaAplicaciones != null) {
                            for (int k = 0; k < listaAplicaciones.size(); k++) {
                                AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
                                if (apPago != null) {
                                    DocumentoPago doc = apPago.getDocumentoPago();
                                    if (doc != null) {
                %>
            </tr>  
            <tr>

                <td width="220">Forma de Pago: </td>
                <td width="220" class="campositem"> <%=doc.getTipoPago()%> </td>
                <td width="220">Valor Liquidado: </td>
                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(doc.getValorDocumento())%> </td>
            </tr>  

            <%                  }
                            }
                        }
                    }
                }
            %>  

<tr>
    <td><br></td>
</tr>                               


<%}
        }
    }%>
</table> 

<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td class="bgnsub"><p>Detalles Antiguo Sistema</p></td>
        <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>
<%
    try {
        MASHelper.setMostrarDocumento(true);
        MASHelper.render(request, out);
    } catch (HelperException re) {
        re.printStackTrace();
        out.println("ERROR " + re.getMessage());
    }

%> 
<br>
<br>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>

        <td class="bgnsub"><p>Notas informativas del turno</p></td>
        <td width="16" class="bgnsub"></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>

<%
    try {
        notasInformativasHelper.render(request, out);
    } catch (HelperException re) {
        out.println("ERROR " + re.getMessage());
    }
%>

<br>
<br>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>

        <td class="bgnsub"><p>Notas Devolutivas del turno</p></td>

        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>

<%
    try {
        notasDevolutivasHelper.render(request, out);
    } catch (HelperException re) {
        out.println("ERROR " + re.getMessage());
    }
%>

<br>
<br>
<br>
<br>
<br>
<br>

<table width="100%" class="camposform">
    <tr>

        <%if (null != session.getAttribute(WebKeys.TURNO_DEVOLUCION) && rol.getRolId().equals(CRol.SIR_ROL_USUARIO_ADMINISTRATIVO)) {%>
    <form 
        method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden"
               name="<%=WebKeys.ACCION%>" value="<%=AWTurno.CONTINUAR_TURNO%>"> <input
               type="hidden" name="<%=AWTurno.ID_FASE%>"
               value="<%=fase%>">
        <td width="150">
            <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
        </td>
        <%} else {%>
        <% if (!listaTurnos.isEmpty() && listaTurnos.size() > 1) {

        %>
        <form action="trasladoTurno.do" method="post" name="VER_DETALLE_TURNO_REGRESAR" id="VER_DETALLE_TURNO_REGRESAR">
            <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
            <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
            <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>"  value="<%= listaTurnos.get(listaTurnos.size() - 2)%>">
            <input type="hidden" name="<%= WebKeys.REGRESAR_TURNO%>" id="<%= WebKeys.REGRESAR_TURNO%>"  value="true">
            <td width="150">
                <img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21"  border="0" onClick="verDetalleTurnoRegresar()" style="cursor:hand">                            
            </td>
            <%} else {
            %>
            <form action="trasladoTurno.do" method="POST" name="BUSCAR" id="BUSCAR">
                <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO%>" value="<%=  AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO%>">
                <td width="150">
                    <input name="imageField" type="image"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                </td>

                <%}
                    if (turno != null) {
                %>
                <!--
                <td width="150">
                        <a href="javascript:cambiarAccionSubmit('<%--=AWTrasladoTurno.CONSULTAR_TURNO--%>')"><img src="<%--=request.getContextPath()--%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></a>
                        <input type='hidden' name='r1' value="<%--= gov.sir.core.negocio.modelo.constantes.CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR--%>">
                        <input type='hidden' name='<%--=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO--%>' value="<%--= turno.getIdWorkflow() != null ? turno.getIdWorkflow().trim() : ""--%>">
                </td> 
                -->               
                <%
                    }
                %>
                <%}%>    
                <td>&nbsp;</td>                    
            </form>    
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
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
            <tr> 
                <td>&nbsp;</td>
                <td></td>
                <td>&nbsp;</td>
                <td></td>
                <td>&nbsp;</td>
            </tr>
           <!--</table>-->

            <%
                /////////////////////
                session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO, turno.getNotas());
                session.setAttribute(WebKeys.TURNO, turnoPadre);
                /////////////////////
%>
