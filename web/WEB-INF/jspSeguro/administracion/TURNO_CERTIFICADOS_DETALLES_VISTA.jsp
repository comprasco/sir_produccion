<%--/*
   * @author:Carlos Torres 
   * @change:Caso Mantis 00011623
   * se habilita el uso del de la clase  NotaActuacionComparador
   * columnas existentes para agregar la nueva columna.
*/--%>
<%@page import="gov.sir.core.negocio.modelo.util.NotaActuacionComparador"%>
<%@page import="java.util.*" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="java.lang.Integer" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.*" %> 
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado" %>
<%--
* @author : HGOMEZ
 *** @change : Se incluye el paquete SolicitudFotocopia.
 *** Caso Mantis : 12288
--%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFotocopia" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudRepartoNotarial" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudRestitucionReparto" %> 
<%@page import="gov.sir.core.negocio.modelo.Turno" %> 
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria" %> 
<%@page import="gov.sir.core.negocio.modelo.TurnoFolioMig" %> 
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %> 
<%@page import="gov.sir.core.negocio.modelo.Liquidacion" %> 
<%@page import="gov.sir.core.negocio.modelo.Pago" %> 
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %> 
<%@page import="gov.sir.core.negocio.modelo.AplicacionPago" %> 
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago" %> 
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.web.acciones.comun.AWTurno" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasInformativasHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListarNotasDevolutivasHelper"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CFase"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CRespuesta"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.Oficio"%>
<%--
* @author      :   Julio Alcázar Rivas
* @change      :   Nuevos Import gov.sir.core.util.DateFormatUtil
*                                gov.sir.core.negocio.modelo.constantes.CTipoTarifa
* Caso Mantis  :   000941
--%>
<%@page import="gov.sir.core.util.DateFormatUtil" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoTarifa" %>
<jsp:directive.page import="gov.sir.core.negocio.modelo.SolicitudCorreccion"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.NotaActuacion"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CActuacionAdministrativa"/>
<jsp:directive.page import="gov.sir.core.web.helpers.comun.TextHelper"/>
<jsp:directive.page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"/>
<jsp:directive.page import="gov.sir.core.web.helpers.comun.TextAreaHelper"/>


<%
    Turno turno = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
    Turno turnoDerivado = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_HIJO);
    String cirNacional = null;
    if (turno.isNacional() && turno.getSolicitud() != null && turno.getSolicitud().getCirculo() != null) {
        cirNacional = turno.getSolicitud().getCirculo().getIdCirculo();
        if (turno.getSolicitud().getCirculo().getNombre() != null) {
            cirNacional += " - " + turno.getSolicitud().getCirculo().getNombre();
        }
    }
    Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
    String fase = turno.getIdFase();
    Turno turnoPadreDerivado = (Turno) session.getAttribute(AWTrasladoTurno.TURNO_PADRE);
    List turnosAsociados = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);
    String categoria = (String) session.getAttribute(AWTrasladoTurno.CATEGORIA_MINUTA);
    String obsPertenencia = (String) session.getAttribute(AWTrasladoTurno.OBSERVACIONES_OFICIO_PERTENENCIA);

//Estacion del turno asociado (Certificados Asociados)
    String idAdministradorSAS = (String) session.getAttribute(AWTrasladoTurno.ID_AMINISTRADOR_SAS_TURNO_ASOCIADO);
    if (turnosAsociados == null) {
        turnosAsociados = new ArrayList();
    }

    List turnosHistoria = turno.getHistorials();

    String respuesta = null;
    TurnoHistoria thUltimo = null;
    if (turnosHistoria != null && turnosHistoria.size() > 0) {
        for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
            TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
            if (thUltimo == null) {
                thUltimo = turnoAux;
            } else {
                int idUltimo = Integer.parseInt(thUltimo.getIdTurnoHistoria());
                int idActual = Integer.parseInt(turnoAux.getIdTurnoHistoria());
                if (idActual > idUltimo) {
                    thUltimo = turnoAux;
                }
            }
            if (turnoAux.getFase() != null && ((turnoAux.getFase()).equals(CFase.DEV_ANALISIS) || (turnoAux.getFase()).equals(CFase.DEV_ANALISIS_REPOSICION) || (turnoAux.getFase()).equals(CFase.DEV_ANALISIS_APELACION))) {
                respuesta = turnoAux.getRespuesta();
            }

            if (turnoAux.getFase() != null && ((turnoAux.getFase()).equals(CFase.COR_REVISION_ANALISIS) || (turnoAux.getFase()).equals(CFase.COR_ACT_ADMIN))) {
                respuesta = turnoAux.getRespuesta();
            }

            if (turnoAux.getFase() != null && turnoAux.getFase().equals(CFase.RES_ANALISIS)) {
                respuesta = turnoAux.getRespuesta();
                respuesta = respuesta == null ? "" : respuesta.equals("CONFIRMAR") ? "RESTITUIDO" : "NO RESTITUIDO";
            }
        }

    }

//System.out.println("respuesta= " + respuesta);
    Solicitud solicitud = turno.getSolicitud();
    List solicitantes = null;
//SE VERIFICA LA RESPUESTA PARA CADA PROCESO, SI APLICA.
    if (solicitud != null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion) {
        solicitantes = ((SolicitudDevolucion) solicitud).getSolicitantes();
        if (respuesta != null) {
            if (respuesta.equals(CRespuesta.CONFIRMAR)) {
                respuesta = "APROBADA";
            } else if (respuesta != null && respuesta.equals(CRespuesta.NEGAR)) {
                respuesta = "NEGADA";
            }
        }
    }

    if (solicitud != null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCorreccion) {
        if (respuesta != null) {
            if (respuesta.equals(CRespuesta.CONFIRMAR) || respuesta.equals(CRespuesta.CONFIRMADO) || respuesta.equals(CRespuesta.CORRECCION_SIMPLE)) {
                respuesta = "APROBADA";
            } else if (respuesta.equals(CRespuesta.NEGAR)) {
                respuesta = "NEGADA";
            } else {
                respuesta = "";
            }
        }
    }

    if (solicitud != null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificado) {
        SolicitudCertificado solCert = (SolicitudCertificado) solicitud;

        int a = solCert.getNumImpresiones();
        if (a > 0) {
            respuesta = "EXPEDIDO";
        } else {
            respuesta = "NO EXPEDIDO";
        }
    }

    /*
if (solicitud!=null && solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRestitucionReparto){
        SolicitudRestitucionReparto solRes = (SolicitudRestitucionReparto)solicitud;
	
        if ("EXITO".equals(turno.getUltimaRespuesta()))
        {
                respuesta = "APROBADA";
        } else
        {
                respuesta = "NEGADA";
        }
}
     */
    Ciudadano ciudadano = solicitud.getCiudadano();

    String tipoCertificado = "&nbsp;";
    String tipoTarifa = "&nbsp;";
    String matricula = "&nbsp;";
    String turnoAnterior = "&nbsp;";
    String numeroRecibo = "&nbsp;";

    DocumentoPago docPagoDevolucion = null;
    Pago pagoDevolucion = null;

    MostrarAntiguoSistemaHelper MASHelper = new MostrarAntiguoSistemaHelper();

//////////////////////////
    Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO);
/////////////////////////
    session.setAttribute(WebKeys.TURNO, turno);
    /**
     * @author Cesar Ramírez
     * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA Se envía el turno
     * padre en sesión.
     *
     */
    session.setAttribute(WebKeys.TURNO_PADRE, turnoPadreDerivado);
    List liquidaciones = new ArrayList();

    /**
     * SI ES UN TURNOS DE DEVOLUCIONES SE DEBE PONER LAS LIQUIDACIONES DEL TURNO
     * ASOCIADO CON VALOR NEGATIVO
     */
    Long proceso = new Long(turno.getIdProceso());
    double valorPagado = 0.0;
    double valorPorPagar = 0.0;
    double valorDevuelto = 0.0;
    double valorPorDevolver = 0.0;
    if (proceso.toString().equals(CProceso.PROCESO_DEVOLUCIONES)) {
        Turno turnoAnt = solicitud.getTurnoAnterior();
        if (turnoAnt != null) {
            Solicitud solicitudAnt = turnoAnt.getSolicitud();
            List liquidacionesAux = solicitudAnt.getLiquidaciones();
            Iterator it = liquidacionesAux.iterator();
            while (it.hasNext()) {
                Liquidacion liq = (Liquidacion) it.next();
                if (liq.getPago() != null && liq.getValor() >= 0) {
                    valorPagado += liq.getValor();
                    List aPagos = liq.getPago().getAplicacionPagos();
                    Iterator it2 = aPagos.iterator();
                    while (it2.hasNext()) {
                        DocumentoPago docPago = ((AplicacionPago) it2.next()).getDocumentoPago();
                        if (docPago instanceof DocPagoConsignacion) {
                            valorPagado += ((DocPagoConsignacion) docPago).getSaldoDocumento();
                        } else if (docPago instanceof DocPagoCheque) {
                            valorPagado += ((DocPagoCheque) docPago).getSaldoDocumento();
                        }
                    }
                }
            }
        } else {
            List consignaciones = ((SolicitudDevolucion) solicitud).getConsignaciones();
            if (consignaciones != null && consignaciones.size() > 0) {
                Iterator it = consignaciones.iterator();
                while (it.hasNext()) {
                    Consignacion consig = (Consignacion) it.next();
                    DocumentoPago docPago = consig.getDocPago();
                    if (docPago instanceof DocPagoConsignacion) {
                        valorPagado += ((DocPagoConsignacion) docPago).getSaldoDocumento()
                                + ((DocPagoConsignacion) docPago).getValorDocumento();
                    } else if (docPago instanceof DocPagoCheque) {
                        valorPagado += ((DocPagoCheque) docPago).getSaldoDocumento()
                                + ((DocPagoCheque) docPago).getValorDocumento();
                    }
                }
            }
        }
        if (solicitud.getLiquidaciones() != null && solicitud.getLiquidaciones().size() > 0) {
            List liquiacionesDevolucion = ((SolicitudDevolucion) solicitud).getLiquidaciones();
            LiquidacionTurnoDevolucion liqDev = (LiquidacionTurnoDevolucion) liquiacionesDevolucion.get(liquiacionesDevolucion.size() - 1);
            if (turno.getIdFase().equals(CFase.FINALIZADO) && ((SolicitudDevolucion) solicitud).isAprobada()) {
                valorDevuelto = Math.abs(liqDev.getValor());
                valorPagado = valorDevuelto;
                pagoDevolucion = liqDev.getPago();
                numeroRecibo = pagoDevolucion.getNumRecibo() != null ? pagoDevolucion.getNumRecibo() : "&nbsp;";
                docPagoDevolucion = ((AplicacionPago) pagoDevolucion.getAplicacionPagos().get(pagoDevolucion.getAplicacionPagos().size() - 1)).getDocumentoPago();
            } else {
                valorPorDevolver = Math.abs(liqDev.getValor());
                valorPagado = valorPorDevolver;
            }
        }

    } else {
        /**
         * Author: Santiago Vásquez Change:
         * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
         */
        if (session.getAttribute(AWTrasladoTurno.LIQUIDACIONES) != null) {
            liquidaciones = (List) session.getAttribute(AWTrasladoTurno.LIQUIDACIONES);

        } else {
            liquidaciones = solicitud.getLiquidaciones();
        }
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
            int lastIndex = liquidaciones.size() - 1;
            if (lastIndex >= 0) {
                Liquidacion ultimaLiq = (Liquidacion) liquidaciones.get(lastIndex);
                Pago pago = ultimaLiq.getPago();
                if (pago != null) {
                    numeroRecibo = pago.getNumRecibo();
                }
            }
        }

    }

    if (solicitud.getTurnoAnterior() != null) {
        turnoAnterior = solicitud.getTurnoAnterior().getIdWorkflow();
    }

    boolean isSolicitudCertificado = false;
    if (solicitud instanceof SolicitudCertificado) {
        SolicitudCertificado solCert = (SolicitudCertificado) solicitud;
        int indexUltimaLiquidacion = solCert.getLiquidaciones().size() - 1;
        if (indexUltimaLiquidacion >= 0) {
            Liquidacion ultimaLiquidacion = (Liquidacion) solicitud.getLiquidaciones().get(indexUltimaLiquidacion);
            if (ultimaLiquidacion instanceof LiquidacionTurnoCertificado) {
                LiquidacionTurnoCertificado liqTurCer = (LiquidacionTurnoCertificado) ultimaLiquidacion;
                tipoCertificado = ((liqTurCer.getTipoCertificado() == null) ? "&nbsp;" : liqTurCer.getTipoCertificado().getNombre());
                tipoTarifa = ((liqTurCer.getTipoTarifa() == null) ? "&nbsp;" : liqTurCer.getTipoTarifa());

                //Si la liquidacion dos no tiene valores
                if (liqTurCer.getTipoCertificado() == null) {
                    int indexPrimeraLiquidacion = 0;
                    Liquidacion primeraLiquidacion = (Liquidacion) solicitud.getLiquidaciones().get(indexPrimeraLiquidacion);
                    if (primeraLiquidacion != null && primeraLiquidacion instanceof LiquidacionTurnoCertificado) {
                        LiquidacionTurnoCertificado liqTurCer1 = (LiquidacionTurnoCertificado) primeraLiquidacion;
                        tipoCertificado = ((liqTurCer1.getTipoCertificado() == null) ? "&nbsp;" : liqTurCer1.getTipoCertificado().getNombre());
                        tipoTarifa = ((liqTurCer1.getTipoTarifa() == null) ? "&nbsp;" : liqTurCer1.getTipoTarifa());
                    }
                }
            }
        }
    } /**
     * @author : HGOMEZ
     *** @change : Se verifica que la solicitud es instancia de solicitud
     * fotocopia ** para presentar los valores adecuados correspondientes al
     * flujo del proceso ** 5 Fotocopia. ** Caso Mantis : 12288
     */
    else {
        if (solicitud instanceof SolicitudFotocopia) {
            SolicitudFotocopia solCert = (SolicitudFotocopia) solicitud;
            int indexUltimaLiquidacion = solCert.getLiquidaciones().size() - 1;
            if (indexUltimaLiquidacion >= 0) {
                Liquidacion ultimaLiquidacion = (Liquidacion) solicitud.getLiquidaciones().get(indexUltimaLiquidacion);
                if (ultimaLiquidacion instanceof LiquidacionTurnoFotocopia) {
                    LiquidacionTurnoFotocopia liqTurFot = (LiquidacionTurnoFotocopia) ultimaLiquidacion;
                    tipoTarifa = ((liqTurFot.getTarifa() == null) ? "&nbsp;" : liqTurFot.getTarifa());

                    int indexPrimeraLiquidacion = 0;
                    Liquidacion primeraLiquidacion = (Liquidacion) solicitud.getLiquidaciones().get(indexPrimeraLiquidacion);
                    if (primeraLiquidacion != null && primeraLiquidacion instanceof LiquidacionTurnoFotocopia) {
                        LiquidacionTurnoFotocopia liqTurFot1 = (LiquidacionTurnoFotocopia) primeraLiquidacion;
                        //tipoCertificado = ((liqTurFot1.getTipoCertificado()==null)?"&nbsp;":liqTurFot1.getTipoCertificado().getNombre());
                        tipoTarifa = ((liqTurFot1.getTarifa() == null) ? "&nbsp;" : liqTurFot1.getTarifa());
                    }
                }
            }
        }
    }
    List solicitudesFolio = solicitud.getSolicitudFolios();
    if (!solicitudesFolio.isEmpty()) {
        SolicitudFolio solFolio = (SolicitudFolio) solicitudesFolio.get(0);
        matricula = solFolio.getIdMatricula();
    }

    /* info turnos anteriores */
    boolean hayResoluciones = false;
    List turnosResoluciones = (List) session.getAttribute(AWTrasladoTurno.RESOLUCIONES);
    if (turnosResoluciones != null && turnosResoluciones.size() > 0
            && turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
        hayResoluciones = true;
    }

    /* info turnos anteriores */
    boolean hayTurnosAnteriores = true;
    List turnosAnteriores = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_ANTERIORES);
    if (turnosAnteriores == null || turnosAnteriores.size() == 0) {
        hayTurnosAnteriores = false;
    }

    /* info turnos siguientes */
    boolean hayTurnosSiguientes = true;
    List turnosSiguientes = (List) session.getAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES);
    if (turnosSiguientes == null || turnosSiguientes.size() == 0) {
        hayTurnosSiguientes = false;
    }

    //Helper para notas informativas
    ListarNotasInformativasHelper notasInformativasHelper = new ListarNotasInformativasHelper();
    //Helper para notas devolutivas
    ListarNotasDevolutivasHelper notasDevolutivasHelper = new ListarNotasDevolutivasHelper();

    /**
     * @author : Julio Alcázar Rivas
     * @change : Se agrega la información del documento de
     * SolicitudCertificadoMasivo a los detalles del turno Caso Mantis : 000941
     */
    String tipoDocumento = "&nbsp;";
    String numDocumento = "&nbsp;";
    String fechaDocumento = "&nbsp;";
    String comentarioDocumento = "&nbsp;";
    String oficinaInternacional = "&nbsp;";
    String departamento = "&nbsp;";
    String municipio = "&nbsp;";
    String vereda = "&nbsp;";
    String comentario = "&nbsp;";
    String tipoOficina = "&nbsp;";
    String nombreOficina = "&nbsp;";
    String numOficina = "&nbsp;";
    boolean isInternacional = false;
    SolicitudCertificadoMasivo solCertMasi = null;
    if (solicitud instanceof SolicitudCertificadoMasivo) {
        solCertMasi = (SolicitudCertificadoMasivo) turno.getSolicitud();
        //TIPO DOCUMENTO

        if (solCertMasi != null
                && solCertMasi.getDocumento() != null
                && solCertMasi.getDocumento().getTipoDocumento() != null
                && solCertMasi.getDocumento().getTipoDocumento().getNombre() != null) {
            tipoDocumento = solCertMasi.getDocumento().getTipoDocumento().getNombre();
        }
        //NUM DOCUMENTO

        if (solCertMasi != null
                && solCertMasi.getDocumento() != null
                && solCertMasi.getDocumento().getNumero() != null) {
            numDocumento = solCertMasi.getDocumento().getNumero();
        }
        //FECHA DOCUMENTO

        if (solCertMasi != null
                && solCertMasi.getDocumento() != null
                && solCertMasi.getDocumento().getFecha() != null) {

            fechaDocumento = DateFormatUtil.format(solCertMasi.getDocumento().getFecha());
        }

        if (solCertMasi != null
                && solCertMasi.getDocumento() != null && solCertMasi.getDocumento().getComentario() != null) {
            comentarioDocumento = solCertMasi.getDocumento().getComentario();
        }
        //OFICINA

        //OFICINA INTERNACIONAL
        if (solCertMasi != null
                && solCertMasi.getDocumento() != null
                && solCertMasi.getDocumento().getOficinaInternacional() != null
                && !solCertMasi.getDocumento().getOficinaInternacional().equals("")) {
            isInternacional = true;
            oficinaInternacional = solCertMasi.getDocumento().getOficinaInternacional();
        }

        //OFICINA NACIONAL
        //DEPARTAMENTO, MUNICIPIO, VEREDA
        if (!isInternacional) {
            if (solCertMasi != null
                    && solCertMasi.getDocumento() != null
                    && solCertMasi.getDocumento().getOficinaOrigen() != null
                    && solCertMasi.getDocumento().getOficinaOrigen().getVereda() != null) {
                gov.sir.core.negocio.modelo.Vereda auxVereda = solCertMasi.getDocumento().getOficinaOrigen().getVereda();
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
            if (solCertMasi.getDocumento().getComentario() != null) {
                comentario = solCertMasi.getDocumento().getComentario();
            }
        }

        //TIPO OFICINA
        if (!isInternacional) {
            if (solCertMasi != null
                    && solCertMasi.getDocumento() != null
                    && solCertMasi.getDocumento().getOficinaOrigen() != null
                    && solCertMasi.getDocumento().getOficinaOrigen().getTipoOficina() != null
                    && solCertMasi.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() != null) {
                tipoOficina = solCertMasi.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
                nombreOficina = solCertMasi.getDocumento().getOficinaOrigen().getNombre();
            }

        }
        //NUM OFICINA

        if (!isInternacional) {
            if ((solCertMasi != null
                    && solCertMasi.getDocumento() != null
                    && solCertMasi.getDocumento().getOficinaOrigen() != null
                    && solCertMasi.getDocumento().getOficinaOrigen().getNumero() != null)) {
                numOficina = solCertMasi.getDocumento().getOficinaOrigen().getNumero();
                nombreOficina = solCertMasi.getDocumento().getOficinaOrigen().getNombre();
            }
        }
    }

    List listaTurnos = (List) session.getAttribute("TURNOS_DERIVADOS");
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">

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



    function cambiarAccion(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    }
    function cambiarAccionSubmit(text) {
        document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
        document.BUSCAR.submit();
    }

    function verDetalleTurno(text) {
        document.VER_DETALLE_TURNO.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO.submit();
    }

    function verDetalleTurnoAnt(text) {
        document.VER_DETALLE_TURNO_ANT.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO_ANT.submit();
    }

    function verDetalleTurnoSig(text) {
        document.VER_DETALLE_TURNO_SIG.<%= CTurno.ID_TURNO%>.value = text;
        document.VER_DETALLE_TURNO_SIG.submit();
    }

    function verDetalleTurnoRegresar() {
        //alert(text);
        //document.VER_DETALLE_TURNO_REGRESAR.<%--= CTurno.ID_TURNO--%>.value = text;
        document.VER_DETALLE_TURNO_REGRESAR.submit();
    }

    function consultarMatricula(text) {
        document.MATRICULAS.<%= CFolio.ID_MATRICULA%>.value = text;
        document.MATRICULAS.submit();
    }

    function verNota(nombre, valor, dimensiones)
    {
        popup = window.open(nombre, valor, dimensiones);
        popup.focus();
    }

    function consultarTurno(text) {
        document.DERIVADOS.<%= CTurno.ID_TURNO%>.value = text;
        document.DERIVADOS.submit();
    }
    <%--
    /*
    * @author : CTORRES
    * @change : se agrega funcion javascript cargarTestamento
    * Caso Mantis : 12291
    * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
    */
    --%>
    function cargarTestamento(text, folio, editable) {
        document.MATRICULAS.<%=WebKeys.ACCION%>.value = text;
        document.MATRICULAS.<%=CFolio.ID_MATRICULA%>.value = folio;
        document.MATRICULAS.submit();
    }
    
     //@author : David A Rubio J
    //@change : Se agrega función para mostrar los detalles de un folio en una ventana emergente con pestañas
    function verAnotacionPersonalizada(matricula,nombre,valor,dimensiones,pos){
        nombre=nombre+"&ID_MATRICULA="+matricula;
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
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
                                            <td></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                            <% 	if (turno.getIdProceso() == 7 && (categoria != null)) {
                            %>
                            <tr>
                                <td width="220">Categor&iacute;a de la minuta </td>
                                <td class="campositem"> <%= categoria != null ? categoria : "&nbsp;"%> </td>
                            </tr>
                            <%}%>
                            <tr>

                                <td width="220">Tipo de Certificado </td>
                                <td class="campositem"> <%=  tipoCertificado != null ? tipoCertificado : "&nbsp;"%> </td>
                            </tr>
                            <%if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS) && turno.isNacional()) {%>
                            <tr>

                                <td width="220">C&uacute;rculo de expedici&oacute;n del Certificado</td>
                                <td class="campositem"> <%=  cirNacional != null ? cirNacional : "&nbsp;"%> </td>
                            </tr>
                            <%}%>
                            <tr>

                                <td width="220">Tipo de Tarifa </td>
                                <td class="campositem"> <%=  tipoTarifa != null ? tipoTarifa : "&nbsp;"%> </td>
                            </tr>
                            <tr>

                                <td width="220">N&uacute;mero de Recibo </td>
                                <td class="campositem"> <%=  numeroRecibo != null ? numeroRecibo : "&nbsp;"%> </td>
                            </tr>
                            <!--<tr>

                                <td width="220">Valor Pagado </td>
                                <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(valorPagado)%> </td>
                            </tr>-->
                            <tr>

                                <td width="220">Valor por Pagar </td>
                                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(valorPorPagar)%> </td>

                            </tr>
                            <tr>

                                <td width="220">Valor Devuelto </td>
                                <td class="campositem"> <%=  java.text.NumberFormat.getInstance().format(java.lang.Math.abs(valorDevuelto))%> </td>
                            </tr>
                            <tr>

                                <td width="220">Valor por Devolver </td>
                                <td class="campositem">  <%=  java.text.NumberFormat.getInstance().format(valorPorDevolver)%>  </td>
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
                                <% if (idAdministradorSAS != null) {%>
                                <td class="campositem"> <%= idAdministradorSAS%> </td>
                                <%} else {%>
                                <td class="campositem"> <%=  thUltimo != null && thUltimo.getIdAdministradorSAS() != null ? thUltimo.getIdAdministradorSAS() : "&nbsp;"%> </td>
                                <%}%>

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
                            <%if (turno.getAnulado() == null || !turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {%>
                            <tr>

                                <td width="220"><B>Resolución</B></td>
                                <td class="campositem"> <%=  respuesta != null ? respuesta : "&nbsp;"%> &nbsp;  </td>
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
                                        idRelacionActual = "NO ESTA RELACIONADO";
                                    }%>
                                <td class="campositem"> <%=idRelacionActual%> </td>
                            </tr>

                            <tr>

                                <td width="220">Fase Relacion Actual </td>
                                <% String idFaseRelActual = turno.getIdFaseRelacionActual();
                                    if (idFaseRelActual == null) {
                                        idFaseRelActual = "NO ESTA RELACIONADO";
                                    }%>
                                <td class="campositem"> <%=idFaseRelActual%> </td>
                            </tr>

                            <%
                                /**
                                 * @author:Carlos Torres
                                 * @change:Caso Mantis 00011623 Se agrega
                                 * descripción al proceso Proceso Correcciones
                                 *
                                 */
                                if (turno.getIdProceso() == 3) {
                                    gov.sir.core.negocio.modelo.SolicitudCorreccion sol = (gov.sir.core.negocio.modelo.SolicitudCorreccion) turno.getSolicitud();
                                    String descripcion = (sol.getDescripcion() != null && !sol.getDescripcion().equals("")) ? sol.getDescripcion() : "&nbsp; &nbsp;";
                            %>
                            <tr>

                                <td width="220">Motivo de la Solicitud - Descripcion </td>

                                <td class="campositem"> <%=descripcion%> </td>
                            </tr>
                            <%}%>

                            <% Boolean esTurnoInternet = (Boolean) session.getAttribute(AWTrasladoTurno.ES_TURNO_INTERNET);
                                if (esTurnoInternet != null && esTurnoInternet.booleanValue()) {
                            %>
                            <tr>

                                <td width="220">Fecha Validaci&oacute;n matr&iacute;cua </td>
                                <td class="campositem"> <%=FechaConFormato.formatear(turno.getSolicitud().getFecha(), "dd/MM/yyyy HH:mm:ss")%> </td>
                            </tr>
                            <% }%>

                        </table>
                        <br>
                        <form action="trasladoTurno.do" method="post" name="VER_DETALLE_TURNO" id="VER_DETALLE_TURNO">
                            <input type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_TURNO%>" value="<%=  AWTrasladoTurno.CONSULTAR_TURNO%>">
                            <input type="hidden" name="TIPO_CONSULTA" id="TIPO_CONSULTA" value="<%= CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                            <input type="hidden" name="<%= CTurno.ID_TURNO%>" id="<%= CTurno.ID_TURNO%>"  value="">
                            <table width="100%" class="camposform">

                                <% float filas = ((float) turnosAsociados.size() / (float) 4);
                                    if (!turnosAsociados.isEmpty()) {
                                        int indexLista = 0;
                                        for (int row = 0; row < java.lang.Math.ceil(filas); row++) {
                                %>
                                <tr>
                                    <%      for (int col = 0; col < 5; col++) {
                                            if (col == 0) {%>
                                    <td width="220">Turnos Asociados</td>
                                    <%} else if (indexLista < turnosAsociados.size()) {
                                        Turno turnoAsociado = (Turno) turnosAsociados.get(indexLista);
                                    %>
                                    <td width="15%" class="campositem"><%=turnoAsociado.getIdWorkflow()%></td>
                                    <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurno('<%= turnoAsociado.getIdWorkflow()%>')" style="cursor:hand"></td>
                                        <%
                                            indexLista = indexLista + 1;
                                        } else {%>
                                    <td width="15%" ></td>
                                    <%}
                                        } %>
                                </tr>
                                <% }

                                    }%>

                            </table>
                        </form>
                        <br>


                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <%                            boolean region_ImprimiblesPendientesEnabled;
                            region_ImprimiblesPendientesEnabled = false;

                            Boolean region_ImprimiblesPendientesEnabledObj;

                            if (null != (region_ImprimiblesPendientesEnabledObj = (Boolean) session.getAttribute(AWTrasladoTurno.PARAM_OBJS_IMPRIMIBLESPENDIENTESENABLED))) {
                                region_ImprimiblesPendientesEnabled = region_ImprimiblesPendientesEnabledObj.booleanValue();
                            } // if

                        %>



                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <%-- SOF:CONDITION-START + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%    if (region_ImprimiblesPendientesEnabled) {

                        %>
                        <%-- EOF:CONDITION-START + + + + + + + + + + + + + + + + + + + + + + --%>


                        <div style="margin-left:6px;font-size:xx-small;" >
                            <span class="bgnsub" style="background:" >
                                <a id="demoPanelLink" href="javascript:toggleSlide();" >Panel de Impresiones Pendientes [Ocultar Ayuda]</a>
                            </span>	
                        </div>

                        <div id="demosMenu" ondblclick="toggleSlide()" style="margin-left:0px;margin-top:2px;padding-left:6px;width:100%" >

                            <%--SOF:REGION --%>

                            <table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-left:2px;margin-right:2px;">
                                <tr>
                                    <td class="camposform" colspan="5" style="margin-top:0px;margin-bottom:0px; padding-left:0px;padding-top:4px;padding-right:20px;padding-bottom:0px;" >

                                        <table width="100%" class="camposform" style="border:0px;">
                                            <tr>
                                                <td>
                                                    Se muestra a continuacion el conjunto de impresiones pendientes del turno y
                                                    un panel deslizable con el conjunto de impresiones pendientes para sus turnos asociados.

                                                </td>
                                                <td width="15">&nbsp;
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <%--EOF:REGION --%>



                        </div>


                        <%-- :: REGION --%>

                        <%// transfer-objects 2 plain objects
                            // ::
                            final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE = AWTrasladoTurno.PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE;

                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_IDVALUES = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_IDVALUES";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES1 = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES1";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES2 = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES2";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS";
                            final String PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS = "PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS";

                            // Generate ---------------------------------------------------------------------------------------
                            // jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno(
                            //    session
                            //  , PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE
                            //  , PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_IDVALUES
                            //  , PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES
                            //  , PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS
                            // );
                            jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno(
                                    session,
                                    PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE,
                                    PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_IDVALUES,
                                    PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_DISPLAYVALUES1,
                                    PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS
                            );

                            // ::
                            final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS = AWTrasladoTurno.PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS;

                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_IDVALUES = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_IDVALUES";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES1 = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES1";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES2 = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES2";
                            final String PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS = "PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS";
                            final String PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS = "PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS";

                            // Generate ---------------------------------------------------------------------------------------
                            jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno(
                                    session,
                                    PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS,
                                    PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_IDVALUES,
                                    PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES1,
                                    PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS
                            );


                        %>



                        <%-- :: REGION --%>

                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%-- :: SOF:REGION (ACCORDION) --%>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

                        <style
                            type="text/css"
                            >

                            .accordionTabTitleBar {
                                font-size           : 12px;
                                padding             : 4px 6px 4px 6px;
                                border-style        : solid none solid none;
                                border-top-color    : #BDC7E7; /* #5D687D;*/
                                border-bottom-color : #182052;
                                border-width        : 1px 0px 1px 0px;
                            }

                            .accordionTabTitleBarHover {
                                font-size        : 11px;
                                background-color : #5D687D;
                            }

                            .accordionTabContentBox {
                                font-size        : 11px;
                                border           : 1px solid #1f669b;
                                border-top-width : 0px;
                                padding          : 0px 8px 0px 8px;
                                /*background-color            : #ffffff;*/
                                background-color: #ECF0F9;

                            }

                        </style>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <script
                            type="text/javascript"
                            src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js"
                            >
                        </script>

                        <script
                            type="text/javascript"
                            src="<%=request.getContextPath()%>/jsp/plantillas/panels/rico.js"
                            >
                        </script>

                        <script
                            type="text/javascript"
                            >


                                var saveHeight;
                                var showing = true;

                                function toggleSlide() {
                                    if (showing)
                                    {
                                        slideMenuUp();
                                        showing = false;
                                    } else
                                    {
                                        slideMenuDown();
                                        showing = true;
                                    }
                                }

                                function slideMenuUp() {
                                    var menu = $('demosMenu');
                                    saveHeight = menu.offsetHeight;

                                    menu.style.overflow = "hidden";
                                    new Rico.Effect.Size(menu, null, 1, 120, 8);

                                    $('demoPanelLink').innerHTML = "Panel de Impresiones Pendientes [Mostrar Ayuda]";
                                }

                                function slideMenuDown() {
                                    var menu = $('demosMenu');
                                    new Rico.Effect.Size(menu, null, saveHeight, 120, 8, {complete: function () {
                                            $(menu).style.overflow = "visible";
                                        }});
                                    $('demoPanelLink').innerHTML = "Panel de Impresiones Pendientes [Ocultar Ayuda]";
                                }

                        </script>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>






                        <br />
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%-- SOF: ACCORDION STRUCTURE --%>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

                        <div id="accordionDiv" >

                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                            <%-- SOF:Panel0 --%>
                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                            <%

                                Integer local_ImprimiblesPendientesTurnoPadreCount;
                                Integer local_ImprimiblesPendientesTurnoHijosCount;

                                if (null == (local_ImprimiblesPendientesTurnoPadreCount = (Integer) session.getAttribute(AWTrasladoTurno.PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRECOUNT))) {
                                    local_ImprimiblesPendientesTurnoPadreCount = new Integer(0);
                                }
                                if (null == (local_ImprimiblesPendientesTurnoHijosCount = (Integer) session.getAttribute(AWTrasladoTurno.PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOSCOUNT))) {
                                    local_ImprimiblesPendientesTurnoHijosCount = new Integer(0);
                                }

                            %>

                            <%-- + + + + + + + + + + + + --%> 
                            <%// -----------------------------------------------------------------------------
                                gov.sir.core.web.helpers.comun.TablaMatriculaHelper local_View_TableHelper
                                        = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

                                //----------------------------------------------------------------------------- 
                            %>
                            <%-- + + + + + + + + + + + + --%> 


                            <div id="local_AccordionPanel0" style="margin:0px;">
                                <div id="local_AccordionPanel0_Header" class="accordionTabTitleBar">

                                    <table width="100%" class="titulotbgral" style="font-weight:normal;">
                                        <tr>
                                            <td width="300"><span style="font-weight:normal;">Imprimibles Pendientes / Turno Actual </span></td>
                                            <td>&nbsp;</td>
                                            <td width="100"><span style="font-weight:normal;">(<%=local_ImprimiblesPendientesTurnoPadreCount.intValue()%> Items)</span></td>
                                            <td width="50">

                                                <%
                                                    if (local_ImprimiblesPendientesTurnoPadreCount.intValue() > 0) {

                                                %>
                                                <img alt="[]" src="<%=request.getContextPath()%>/jsp/images/privileged/4/stock-warning-16.gif"/>
                                                <%
                                                    }
                                                %>
                                                &nbsp;
                                            </td>           
                                        </tr>
                                    </table>

                                </div>
                                <div id="local_AccordionPanel0_Content" class="accordionTabContentBox">




                                    <%-- print-data ----------------------------------------------------------------------------------- --%>
                                    <%-- version 1 --%>
                                    <%--
                                    <%
                                            // ---------------------------------------------------------
                                            local_PrintData : {
                                                    
                                                    List    local_Column1;
                                                    List    local_Column2;		
                                                    String  local_Column1_SessionKey;
                                                    String  local_Column2_SessionKey;	
                                                    
                                                    local_Column1_SessionKey = PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES1;
                                                    local_Column2_SessionKey = PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_DISPLAYVALUES2;
                                                    
                                                    if( null == ( local_Column1 = (java.util.List)session.getAttribute(  local_Column1_SessionKey ) ) ) {
                                                            local_Column1 = new java.util.ArrayList();
                                                    }
                                                    if( null == ( local_Column2 = (java.util.List)session.getAttribute(  local_Column2_SessionKey ) ) ) {
                                                            local_Column2 = new java.util.ArrayList();
                                                    }
                                                            
                                                    


%>


        <table>
        
        <%
        Iterator local_Column1Iterator;
        Iterator local_Column2Iterator;
        String local_Column1Element;
        String local_Column2Element;
        local_Column1Iterator = local_Column1.iterator();
        local_Column2Iterator = local_Column2.iterator();
        for(;local_Column1Iterator.hasNext();) {
                local_Column1Element = (String)local_Column1Iterator.next();
                local_Column2Element = (String)local_Column2Iterator.next();
        %>
                <tr>
                  <td><%= local_Column1Element %></td>
                  <td><%= local_Column2Element%></td>
                </tr>

        <%
        } // for
        %>		
                
        </table>

<%

   } // :local_PrintData
   // ---------------------------------------------------------
%>	    
                                    --%>  

                                    <%-- print-data ----------------------------------------------------------------------------------- --%>
                                    <%-- version 2 --%>

                                    <%
                                        try {

                                            local_View_TableHelper.setInputName(PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS); //  WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION
                                            local_View_TableHelper.setListName(PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_IDVALUES);

                                            // permite colocar los valores previamente seleccionados
                                            local_View_TableHelper.setSelectedValues_Enabled(true);
                                            local_View_TableHelper.setSelectedValues_Ids_Key(PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOPADRE_SELECTEDIDS);

                                            local_View_TableHelper.setContenidoCelda(gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_PLAIN);
                                            local_View_TableHelper.setAlineacionTodasCeldas(TablaMatriculaHelper.ALINEACION_IZQUIERDA);
                                            local_View_TableHelper.setColCount(1);

                                            local_View_TableHelper.render(request, out);
                                        } catch (HelperException e) {
                                            out.println("ERROR " + e.getMessage());
                                        }


                                    %>

                                </div>
                            </div>

                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                            <%-- EOF:Panel0 --%>
                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                            <%-- SOF:Panel1 --%>
                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

                            <div id="local_AccordionPanel1" >
                                <div id="local_AccordionPanel1_Header" class="accordionTabTitleBar" >

                                    <table width="100%" class="titulotbgral" style="font-weight:normal;">
                                        <tr>
                                            <td width="300"><span style="font-weight:normal;">Imprimibles Pendientes / Turnos Asociados</span></td>
                                            <td>&nbsp;</td>
                                            <td width="100"><span style="font-weight:normal;">(<%=local_ImprimiblesPendientesTurnoHijosCount.intValue()%> Items)</span></td>
                                            <td width="50">

                                                <%
                                                    if (local_ImprimiblesPendientesTurnoHijosCount.intValue() > 0) {
                                                %>
                                                <img alt="[]" src="<%=request.getContextPath()%>/jsp/images/privileged/4/stock-warning-16.gif"/>
                                                <%
                                                    }
                                                %>
                                                &nbsp;
                                            </td>
                                        </tr>
                                    </table>


                                </div>
                                <div id="local_AccordionPanel1_Content"  class="accordionTabContentBox">





                                    <%
                                        try {

                                            local_View_TableHelper.setInputName(PARAM_ITEM_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS); //  WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION
                                            local_View_TableHelper.setListName(PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_IDVALUES);

                                            // permite colocar los valores previamente seleccionados
                                            local_View_TableHelper.setSelectedValues_Enabled(true);
                                            local_View_TableHelper.setSelectedValues_Ids_Key(PARAM_LOCAL_IMPRIMIBLESPENDIENTESTURNOHIJOS_SELECTEDIDS);

                                            local_View_TableHelper.setContenidoCelda(gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_PLAIN);
                                            local_View_TableHelper.setAlineacionTodasCeldas(TablaMatriculaHelper.ALINEACION_IZQUIERDA);
                                            local_View_TableHelper.setColCount(1);

                                            local_View_TableHelper.render(request, out);
                                        } catch (HelperException e) {
                                            out.println("ERROR " + e.getMessage());
                                        }


                                    %>






                                </div>
                            </div>

                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                            <%-- EOF:Panel1 --%>
                            <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

                        </div>

                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%-- EOF: ACCORDION STRUCTURE --%>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>






















                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <script type="text/javascript">

                            new Rico.Accordion($('accordionDiv'), {panelHeight: 100, collapsedBg: '#79849B', expandedBg: '#79849B', color: '#ffffff', hoverBg: '#5D687D'});

                        </script>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%-- :: EOF:REGION (ACCORDION) --%>
                        <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>








                        <%-- SOF:CONDITION-END   + + + + + + + + + + + + + + + + + + + + + + --%>
                        <%    } // if

                        %>
                        <%-- EOF:CONDITION-END   + + + + + + + + + + + + + + + + + + + + + + --%>

                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 
                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --> 













                        <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->                



                        <% if (hayTurnosAnteriores) { %>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>
                                <%--
/*
* @author : CTORRES
* @change : se agrega condicion para mostrar turnos de testamento
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                                --%>
                                <td class="bgnsub">
                                    <%
                                        Turno tA = (Turno) turnosAnteriores.get(0);
                                        if (turno.getIdProceso() == 3 && tA.getIdProceso() == 6) {%>
                                    <p>Turno testamento</p>
                                    <%} else {%>
                                    <p>Turnos anteriores</p>
                                    <%}%>
                                </td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <!--gere-->
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

                                    <%
                                        String mensajeTurnoAnterior = "";
                                        if (solicitud != null && solicitud instanceof SolicitudRestitucionReparto) {
                                            mensajeTurnoAnterior = "Turno de reparto asociado : ";
                                            /*
                        * @author : CTORRES
                        * @change : se agrega condicion para verficar si es un turno de correccion de testamento
                        * Caso Mantis : 12291
                        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                                             */
                                        } else if (turno.getIdProceso() == 3 && temp.getIdProceso() == 6) {
                                            mensajeTurnoAnterior = "Turno testamento : ";
                                        } else {
                                            mensajeTurnoAnterior = "Turno Anterior : ";
                                        }%>
                                    <td width="220"><%=mensajeTurnoAnterior%></td>
                                    <td width="150" class="campositem"> <%=  nTurnoAnterior != null ? nTurnoAnterior : "&nbsp;"%> </td>
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurnoAnt('<%= nTurnoAnterior%>')" style="cursor:hand"></td>
                                        <%--
                                            /*
                                            * @author : CTORRES
                                            * @change : se agrega condicion para verficar si es un turno de correccion de testamento
                                            * Caso Mantis : 12291
                                            * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                                            */
                                        --%>
                                        <%if (turno.getIdProceso() == 3 && temp.getIdProceso() == 6) {%>
                                    <td class="campositem" width="30"><input name="imageField" type="image" onclick="javascript:cargarTestamento('<%=gov.sir.core.web.acciones.administracion.AWTrasladoTurno.VER_DETALLES_TURNO_TESTAMENTO%>', '<%=nTurnoAnterior%>', 'NO');" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0" /></td>
                                        <%}%>
                                </tr>
                                <% 	} %>
                            </table>
                        </form>
                        <%}%>


                        <%if (hayResoluciones) {%>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Resoluciones</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <% Iterator itaResoluciones = turnosResoluciones.iterator();
                            for (int i = 1; itaResoluciones.hasNext(); i++) {
                                Oficio temp = (Oficio) itaResoluciones.next();
                        %>


                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">N&uacute;mero</td>
                                <td width="211" class="campositem"><%=temp.getNumero() == null ? "&nbsp;" : temp.getNumero()%></td>

                                <td width="146">Fecha</td>
                                <td width="200" class="campositem"><%

                                    Date fechaOficio = temp.getFechaCreacion();
                                    String fechaDisplay = FechaConFormato.formatear(fechaOficio, "dd/MM/yyyy");
                                    %><%=fechaDisplay%></td>

                                <td width="146">Fecha Ejecutor&iacute;a</td>
                                <td width="200" class="campositem"><%
                                    if (temp.getFechaFirma() != null) {
                                        Date fechaEjecutoria = temp.getFechaFirma();
                                        String fechaEjecutoriaDisplay = FechaConFormato.formatear(fechaEjecutoria, "dd/MM/yyyy");
                                    %><%=fechaEjecutoriaDisplay%>
                                    <%} else {%>
                                    &nbsp;
                                    <%} %>
                                </td>


                            </tr>
                        </table>


                        <% 	} %>

                        <%}%>

                        <%if (docPagoDevolucion != null) {%>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Pago de la Devolucion</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Orden de Pago</td>
                                <td width="200" class="campositem"><%=pagoDevolucion.getConcepto()%></td>
                                <td width="146">&nbsp;</td>
                                <td width="200">&nbsp;</td>

                            </tr>
                        </table>

                        <%if (docPagoDevolucion instanceof DocPagoCheque) {%>
                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Tipo de Pago</td>
                                <td width="211" class="campositem">CHEQUE</td>

                                <td width="146">N&uacute;mero</td>
                                <td width="200" class="campositem"><%

                                    String numCheque = ((DocPagoCheque) docPagoDevolucion).getNoCheque();
                                    %><%=numCheque%></td>

                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="179">Banco</td>
                                <td width="211" class="campositem"><%

                                    String banco = ((DocPagoCheque) docPagoDevolucion).getBanco().getNombre();
                                    %><%=banco%></td>

                                <td width="146">Valor</td>
                                <td width="200" class="campositem"><%=valorDevuelto%></td>

                            </tr>
                        </table>
                        <%} else if (docPagoDevolucion instanceof DocPagoConsignacion) {%>
                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Tipo de Pago</td>
                                <td width="211" class="campositem">CONSIGNACION</td>

                                <td width="146">N&uacute;mero</td>
                                <td width="200" class="campositem"><%
                                    String numCheque = ((DocPagoConsignacion) docPagoDevolucion).getNoConsignacion();
                                    %><%=numCheque%></td>

                            </tr>
                            <tr>
                                <td width="20">&nbsp;</td>
                                <td width="179">Banco</td>
                                <td width="211" class="campositem"><%

                                    String banco = ((DocPagoConsignacion) docPagoDevolucion).getBanco().getNombre();
                                    %><%=banco%></td>

                                <td width="146">Valor</td>
                                <td width="200" class="campositem"><%=valorDevuelto%></td>

                            </tr>
                        </table>
                        <%} else {%>
                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Tipo de Pago</td>
                                <td width="211" class="campositem">EFECTIVO</td>

                                <td width="146">Valor</td>
                                <td width="200" class="campositem"><%=valorDevuelto%></td>

                            </tr>
                        </table>
                        <%} %>  

                        <%}%>





                        <%if (hayTurnosSiguientes) {%>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Turnos Siguientes</p></td>
                                <td width="16" class="bgnsub"></td>
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
                                    <%
                                        String mensajeTurnoSiguiente = "";
                                        if (solicitud != null && solicitud instanceof SolicitudRepartoNotarial) {
                                            mensajeTurnoSiguiente = "Turno de restitución : ";
                                        } else {
                                            mensajeTurnoSiguiente = "Turno Siguiente : ";
                                        }%>
                                    <td width="220"><%=mensajeTurnoSiguiente%></td>
                                    <td width="150" class="campositem"> <%=  nTurnoSiguiente != null ? nTurnoSiguiente : "&nbsp;"%> </td>
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verDetalleTurnoSig('<%= nTurnoSiguiente%>')" style="cursor:hand"></td>
                                </tr>
                                <% 	} %>
                            </table>
                        </form>
                        <%} %>
                        <% if (turnoDerivado != null || turnoPadreDerivado != null) {%>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Turnos Derivados</p></td>
                                <td width="16" class="bgnsub"></td>
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
                        <% }%>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Matr&iacute;culas Asociadas</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <form action="trasladoTurno.do" method="POST" name="MATRICULAS" id="MATRICULAS">
                            <input  type="hidden" name="<%= WebKeys.ACCION%>" id="<%=AWTrasladoTurno.CONSULTAR_MATRICULA%>" value="<%=  AWTrasladoTurno.CONSULTAR_MATRICULA%>">
                            <input  type="hidden" name="<%= CFolio.ID_MATRICULA%>" id="" value="">
                            <table width="100%" class="camposform">
                                <%
                                    if (turno.getSolicitud().getSolicitudFolios() != null) {
                                        int i=0;
                                        for (Iterator itMatriculas = turno.getSolicitud().getSolicitudFolios().iterator(); itMatriculas.hasNext();) {
                                            SolicitudFolio solFol = (SolicitudFolio) itMatriculas.next();
                                %>
                                <tr>
                                    <td width="20%" class="campositem"><%=solFol.getIdMatricula()%></td>
                                    <td width="35"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" onClick="verAnotacionPersonalizada('<%=solFol.getIdMatricula()%>', 'consultar.folio.do?POSICION=<%= i %>','Folio','width=900,height=450,scrollbars=yes','0')" style="cursor:hand"></td>
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
                                    <td width="35">&nbsp;</td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </table>
                        </form>
                        <br>
                        <%--
                        /*
                        * @author : YefersonMartinez
                        * @change : se agrega campo para mostrar informacion de Folio
                        * Caso : 317097
                        */
                        --%>

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">

                            <tr>
                                <td class="bgnsub"><p>Oficios De Pertenencia</p><img src="<%=request.getContextPath()%>/jsp/images/btn_verdetalles.gif" width="139" height="21" onClick="cambiarDisplay('campoOficio')" style="cursor:hand"> </td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>   
                            <tr id="campoOficio" style="display: none">
                                <%if (obsPertenencia != null && obsPertenencia != "") {%>

                                <td class="campositem"> 
                                    <textarea id="COMENTARIO_FOLIO" readonly="readonly" name="COMENTARIO_FOLIO" cols="145" rows="6" class=""><%=obsPertenencia%>
                                    </textarea>
                                </td> 

                                <% } else {%>
                                <td class="campositem"> 
                                    <textarea id="COMENTARIO_FOLIO" readonly="readonly" name="COMENTARIO_FOLIO" cols="145" rows="6" class="">SIN OFICIO DE PERTENECIA

                                    </textarea>
                                </td> 

                                <%  }%>

                            </tr>

                        </table>
                        <br>


                        <%--FIN--%>  

                        <%--
                          * @author      :   Julio Alcázar Rivas
                          * @change      :   Se imprimen los datos del Documento de la SolicitudCertificadoMasivo Exento
                          * Caso Mantis  :   000941
                        --%>
                        <%                            if (solCertMasi != null && solCertMasi.getDocumento() != null
                                    && ((LiquidacionTurnoCertificadoMasivo) solCertMasi.getLiquidaciones().get(liquidaciones.size() - 1)).getTipoTarifa().equals(CTipoTarifa.EXENTO)) {
                        %>
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
                        <%
                            }
                        %>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Historial de fases del turno</p></td>
                                <td width="16" class="bgnsub"></td>
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
                                        <%if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {%>
                                    <th width="31%">ACCIONES REALIZADAS</th>
                                        <%} else { %>
                                    <th width="15%">RELACION FASE</th>
                                    <th width="1%"></th>
                                    <th width="15%">RELACION SIGUIENTE</th>
                                        <%} %>
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
                                             * @change: 11604: Acta -
                                             * Requerimiento No
                                             * 004_589_Funcionario_Fase_
                                             * Entregado
                                             */
                                            turnoAux.setNombreFase("FINALIZADO");
                                        }
                                        /**
                                         * @author: Julio Alcazar
                                         * @change: 5680: Acta - Requerimiento
                                         * No 212 - Pantalla de administración -
                                         * Ver detalles de turno, Se modifica
                                         * para que sea tomado el nombre de
                                         * usuario de quien atendio en la fase
                                         * correspondiente.
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

                                    <%if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                                            String accionRealizada = "&nbsp";
                                            if (turnoAux.getAccion() != null) {
                                                accionRealizada = turnoAux.getAccion();
                                            }
                                    %>
                                    <td width="30%" class="campositem"><%=accionRealizada%> </td>
                                    <%} else {
                                        String idRelacion = null;
                                        idRelacion = turnoAux.getIdRelacion();
                                        if (idRelacion == null) {
                                            idRelacion = "&nbsp";
                                        }%>
                                    <td width="15%" class="campositem"><%=idRelacion%> </td>

                                    <td width="1%"></td>
                                    <%String idRelacionSig = null;
                                        String faseRelacionSig = null;
                                        idRelacionSig = turnoAux.getIdRelacionSiguiente();
                                        if (idRelacionSig == null) {
                                            idRelacionSig = "&nbsp";
                                        }%>
                                    <td width="15%" class="campositem"><%=idRelacionSig%> </td>
                                    <%} %>


                                </tr>
                            </tbody>

                            <%
                                    }
                                }
                            %>
                        </table>
                        <!-- Notas Actuaciones -->                
                        <%
                            List notasActuaciones = null;
                            MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
                            if (turno != null && turno.getSolicitud() != null) {
                                if (turno.getSolicitud() instanceof SolicitudCorreccion) {
                                    SolicitudCorreccion solCorr = (SolicitudCorreccion) turno.getSolicitud();
                                    /*
                                    * @author:Carlos Torres 
                                    * @change:Caso Mantis 00011623
                                    * se agrega condicion para ordenamiento de las notatasActuacion 
                                    * columnas existentes para agregar la nueva columna.
                                     */
                                    if (solCorr != null && solCorr.getNotasActuaciones() != null) {
                                        List lista = new ArrayList();
                                        Iterator unModlist = solCorr.getNotasActuaciones().iterator();
                                        while (unModlist.hasNext()) {
                                            Object o = unModlist.next();
                                            lista.add(o);
                                        }
                                        notasActuaciones = lista;
                                        Collections.sort(notasActuaciones, new NotaActuacionComparador());
                                    }
                                }
                            }
                        %>

                        <%if (notasActuaciones != null && turno.getIdProceso() == 3) {%>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="bgnsub"><p>Historial actuaciones administrativas</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>  

                        <table width="100%"> 
                            <tr>
                                <%-- 
                                * @author:Carlos Torres 
                                * @change:Caso Mantis 00011623
                                * Se agrega la columna DESCRIPCIÓN con el campo nota del objeto notaActuacion. Se modifica el acho de las
                                * columnas existentes para agregar la nueva columna.
                                --%>
                                <td width="1%"></td>
                                <td width="24%" class="contenido"><B>ESTADO</B></td>
                                <td width="1%"></td>
                                <td width="24%" class="contenido"><B>FECHA</B></td>
                                <td width="1%"></td>
                                <td width="24%"class="contenido"><B>DESCRIPCIÓN</B></td>
                                <td width="1%"></td>
                                <td width="24%"class="contenido"><B>NOTA</B></td>
                            </tr>			   	
                        </table>			   	

                        <table width="100%" class="camposform">
                            <%
                                Iterator itNotas = notasActuaciones.iterator();
                                while (itNotas.hasNext()) {
                                    NotaActuacion notaActuacion = (NotaActuacion) itNotas.next();
                            %>
                            <tr>

                                <%-- 
                                           * @author:Carlos Torres 
                                           * @change:Caso Mantis 00011623
                                           * Se agrega la columna DESCRIPCIÓN con el campo nota del objeto notaActuacion. Se modifica el acho de las
                                           * columnas existentes para agregar la nueva columna.
                                
                                           * @author:AHERRENO
                                           * @change:Caso Mantis 00011623
                                           * Se modifica formato del campo notaActuacion.getFechaCreacion() para que despliegle horas, minutos y segundos.
                                --%>
                                <td width="1%"></td>
                                <td width="24%" class="campositem"><%=  notaActuacion.getEstado() != null ? notaActuacion.getEstado() : "&nbsp;"%></td>
                                <td width="1%"></td>
                                <td width="24%" class="campositem"><%=  notaActuacion.getFechaCreacion() != null ? FechaConFormato.formatear(notaActuacion.getFechaCreacion(), "dd/MM/yyyy hh:mm:ss a") : "&nbsp;"%> </td>
                                <td width="1%"></td>
                                <td width="24%" class="campositem"><%=  notaActuacion.getNota() != null ? notaActuacion.getNota() : "&nbsp;"%></td>
                                <td width="1%"></td>
                                <td width="24%" class="campositem"><%=  notaActuacion.getIdNotaActuacion() != null ? notaActuacion.getIdNotaActuacion() : "&nbsp;"%></td>
                            </tr>
                            <%
                                }
                            %>
                        </table>                         
                        <%
                            }
                        %>                
                        <!--Fin notas actuacion-->      
                        <%if (solicitantes != null && solicitantes.size() > 0) {%>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Datos de los solicitantes</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <%for (int i = 0; i < solicitantes.size(); i++) {
                                Solicitante solicitante = (Solicitante) solicitantes.get(i);
                                ciudadano = solicitante.getCiudadano();
                        %>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="220">Nombre del Solicitante: </td>
                                <% esTurnoInternet = (Boolean) session.getAttribute(AWTrasladoTurno.ES_TURNO_INTERNET);
                                    if (esTurnoInternet != null && esTurnoInternet.booleanValue()) {
                                %>
                                <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getApellido1() + "&nbsp;"%> </td>
                                <% } else {%>
                                <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getNombreCompletoCiudadano() + "&nbsp;"%> </td>
                                <% }%>
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
                        <%} %>
                        <%} else {%>
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Datos del solicitante</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <table width="100%" class="camposform">
                            <tr>
                                <!-- 
                                @author Cesar Ramirez
                                @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                                -->
                                <%
                                    String nombre_solicitante = null;
                                    String tipo_doc_solicitante = null;
                                    long num_doc_solicitante = 0L;
                                    boolean isDerivadoRegistro = false;
                                    if (turnoPadreDerivado != null) {
                                        if (turno.getIdProceso() == 3 && turnoPadreDerivado.getIdProceso() == 6) {
                                            if (turnosHistoria != null && turnosHistoria.size() > 0) {
                                                for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();) {
                                                    TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
                                                    if (turnoAux.getFase().equals("SOLICITUD")) {
                                                        nombre_solicitante = turnoAux.getUsuario().getNombreCompletoUsuario();
                                                        tipo_doc_solicitante = turnoAux.getUsuario().getTipoIdentificacion();
                                                        num_doc_solicitante = turnoAux.getUsuario().getNumIdentificacion();
                                                        isDerivadoRegistro = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                %>
                                <td width="220">Nombre del Solicitante: </td>
                                <% esTurnoInternet = (Boolean) session.getAttribute(AWTrasladoTurno.ES_TURNO_INTERNET);
                                    /**
                                     * @author Cesar Ramirez
                                     * @change:
                                     * 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                                     *
                                     */
                                    if (!isDerivadoRegistro) {
                                        if (esTurnoInternet != null && esTurnoInternet.booleanValue()) {
                                %>
                                <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getApellido1() + "&nbsp;"%> </td>
                                <% } else {%>
                                <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getNombreCompletoCiudadano() + "&nbsp;"%> </td>
                                <% }
                                } else {
                                %>
                                <td class="campositem"> <%=(nombre_solicitante == null) ? "&nbsp;" : nombre_solicitante + "&nbsp;"%> </td>
                                <%
                                    }
                                %>
                                <td width="220">Telefono: </td>
                                <%
                                    /**
                                     * @author Cesar Ramirez
                                     * @change:
                                     * 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                                     *
                                     */
                                    if (!isDerivadoRegistro) {
                                %>
                                <td class="campositem"> <%=  (ciudadano != null && ciudadano.getTelefono() != null) ? ciudadano.getTelefono() : "&nbsp;"%> </td>
                                <%
                                } else {
                                %>
                                <td class="campositem"><%="&nbsp;"%> </td>
                                <%
                                    }
                                %>
                            </tr>
                            <tr>

                                <td width="220">Tipo de Documento del Solicitante: </td>
                                <%
                                    /**
                                     * @author Cesar Ramirez
                                     * @change:
                                     * 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                                     *
                                     */
                                    if (!isDerivadoRegistro) {
                                %>
                                <td width="220" class="campositem"> <%= (ciudadano == null) ? "&nbsp;" : ciudadano.getTipoDoc() + "&nbsp;"%> </td>
                                <%
                                } else {
                                %>
                                <td width="220" class="campositem"><%=(tipo_doc_solicitante == null) ? "&nbsp;" : tipo_doc_solicitante + "&nbsp;"%> </td>
                                <%
                                    }
                                %>
                                <td width="220">N&uacute;mero de Documento del Solicitante: </td>
                                <%
                                    /**
                                     * @author Cesar Ramirez
                                     * @change:
                                     * 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA
                                     *
                                     */
                                    if (!isDerivadoRegistro) {
                                %>
                                <td class="campositem"> <%=  (ciudadano == null) ? "&nbsp;" : ciudadano.getDocumento() + "&nbsp;"%> </td>
                                <%
                                } else {
                                %>
                                <td class="campositem"> <%=(num_doc_solicitante == 0) ? "&nbsp;" : num_doc_solicitante + "&nbsp;"%> </td>
                                <%
                                    }
                                %>
                            </tr>
                        </table> 
                        <%}%>






                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Detalles Antiguo Sistema</p></td>
                                <td width="16" class="bgnsub"></td>
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



                        <%             //Obtener información básica de los pagos.
                            Iterator itPagos = liquidaciones.iterator();
                            /**
                             * Author: Santiago Vásquez Change:
                             * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                             */
                            Boolean pagos = Boolean.FALSE;
                            Boolean mayorValor = Boolean.FALSE;
                        %>

                        <!--DATOS DE LA LIQUIDACIÓN.-->
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr>

                                <td class="bgnsub"><p>Pagos asociados</p></td>
                                <td width="16" class="bgnsub"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>

                        <table width="100%" class="camposform">
                            <%
                                while (itPagos.hasNext()) {
                                    Liquidacion liqAux = (Liquidacion) itPagos.next();
                                    if (pagos.booleanValue()) {
                                        mayorValor = Boolean.TRUE;
                            %>
                            <tr>
                                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                            </tr>
                            <td width="220"><b>Pago Mayor Valor</b></td>
                            <%
                                }
                            %>
                            

                            <!--<td width="220">Valor pagado </td>
                            <td width="220" class="campositem"> <%= java.text.NumberFormat.getInstance().format(liqAux.getValor())%> </td>
                            <!--<td width="220"> </td>
                            <td class="campositem">  </td>
                            </tr>
                            -->   
                            <% //Mostrar las aplicaciones de pago correspondientes
                                /*Pago pagoAux = liqAux.getPago();
                            if (pagoAux != null)
                            {
                                 List listaAplicaciones = pagoAux.getAplicacionPagos();
                     
                                 if (listaAplicaciones != null)
                                 {
                                     for (int k= 0; k<listaAplicaciones.size(); k++)
                                     {
                                        AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
                                        if (apPago != null)
                                        {
                                            DocumentoPago doc = apPago.getDocumentoPago();
                                            if (doc != null)
                                            {*/
                                /**
                                 * Author: Santiago Vásquez Change:
                                 * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                 */
                                if (!pagos.booleanValue()) {
                                    List efectivo = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO);
                                    if (efectivo != null && !efectivo.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < efectivo.size(); i= i+3) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_EFECTIVO%> </td>

                                <td class="campositem"> <%= efectivo.get(i)%> </td>
                            </tr>
                            <%
                                    }
                                }

                                List convenio = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO);
                                if (convenio != null && !convenio.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < convenio.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_CONVENIO%> </td>

                                <td class="campositem"> <%= convenio.get(i)%> </td>
                            </tr>
                            <tr>
                                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                            </tr>
                            <%
                                    }
                                }

                                List pse = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE);
                                if (pse != null && !pse.isEmpty()) {
                            %>
                            <tr>                             
                                <td width="220">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">Número Aprobación: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <%
                                for (int i = 0; i < pse.size(); i++) {
                            %>
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
                                    <%if(((List) debito.get(i)).get(9) != null){%>    
                                        <td width="220">Cuenta Destino: </td>
                                        <td class="campositem"> <%= ((List) debito.get(i)).get(10)+"-"+((List) debito.get(i)).get(9)%> </td> 
                                    <%}%>
                                </tr>
                                <tr>
                                    <%if(((List) debito.get(i)).get(5) != null){%>
                                        <td align="left">Canal de Recaudo: </td>
                                    <%}%>
                                    <%if(((List) debito.get(i)).get(6) != null){%>
                                        <td width="220">Forma de Pago: </td>
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
                                </tr>
                                <tr>
                                    <%if(((List) debito.get(i)).get(5) != null){%>
                                    <td class="campositem"> <%= ((List) debito.get(i)).get(5) != null ? ((List) debito.get(i)).get(5) : "&nbsp;"%> </td> 
                                    <%}%>
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
                                </tr>
                                <tr>
                                    <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                                </tr>
                        <%
                                }
                            }

                                List credito = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO);
                                if (credito != null && !credito.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">No. Aprobación: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < credito.size(); i++) {
                                %>
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
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">Valor Documento: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                                <%
                                    /**
                                     * @author Santiago Vasquez
                                     * @change:
                                     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                     *
                                     */
                                    if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                                %>
                                <td width="220">Pago Anulado: </td>
                                <% } %>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < cheque.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_CHEQUE%> </td>

                                <td class="campositem"> <%= ((List) cheque.get(i)).get(0)%> </td>

                                <td class="campositem"> <%= ((List) cheque.get(i)).get(1)%> </td>

                                <td class="campositem"> <%= ((List) cheque.get(i)).get(2)%> </td>

                                <td class="campositem"> <%= ((List) cheque.get(i)).get(3)%> </td>

                                <td class="campositem"> <%= ((List) cheque.get(i)).get(4)%> </td>
                                <%
                                    /**
                                     * @author Santiago Vasquez
                                     * @change:
                                     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                     *
                                     */
                                    if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                                %>
                                <td class="campositem"> <%= ((List) cheque.get(i)).get(5)%> </td>
                                <% } %>
                            </tr>
                            <tr>
                                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                            </tr>
                            <%
                                    }
                                }

                                List consignacion = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION);
                                if (consignacion != null && !consignacion.isEmpty()) {
                            %>
                            <tr>
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">Valor Documento: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                                <%
                                    /**
                                     * @author Santiago Vasquez
                                     * @change:
                                     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                     *
                                     */
                                    if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                                %>
                                <td width="220">Pago Anulado: </td>
                                <% } %>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < consignacion.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_CONSIGNACION%> </td>

                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(0)%> </td>

                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(1)%> </td>

                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(2)%> </td>

                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(3)%> </td>

                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(4)%> </td>
                                <%
                                    /**
                                     * @author Santiago Vasquez
                                     * @change:
                                     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                     *
                                     */
                                    if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                                %>
                                <td class="campositem"> <%= ((List) consignacion.get(i)).get(5)%> </td>
                                <% } %>
                            </tr>
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
                                <%if(((List) general.get(i)).get(7) != null){%>
                                    <td width="220">Banco: </td>
                                <%}%>
                                <%if(((List) general.get(i)).get(8) != null){%>
                                    <td width="220">No. Consignación: </td>
                                <%}%>
                                <%if(((List) general.get(i)).get(11) != null){%>
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
                                    pagos = Boolean.TRUE;
                                }
                                /**
                                 * @author Santiago Vasquez
                                 * @change:
                                 * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                                 *
                                 */
                                //if (liqAux instanceof LiquidacionTurnoCorreccion) {
                                //   LiquidacionTurnoCorreccion liqCor = (LiquidacionTurnoCorreccion) liqAux;
                                //   if(liqCor.getJustificacionMayorValor() != null && liqCor.getJustificacionMayorValor().length() > 0) {
                                //Mostrar las aplicaciones de pago correspondientes
                                if (mayorValor.booleanValue()) {
                                    Boolean mayorValorList = (Boolean) session.getAttribute(AWTrasladoTurno.MAYOR_VALOR);
                                    if (mayorValorList.booleanValue()) {
                                        List efectivoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM);
                                        if (efectivoMV != null && !efectivoMV.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < efectivoMV.size(); i= i+3) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_EFECTIVO%> </td>

                                <td class="campositem"> <%= efectivoMV.get(i)%> </td>
                            </tr>
                            <%
                                    }
                                }

                                List convenioMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM);
                                if (convenioMV != null && !convenioMV.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < convenioMV.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_CONVENIO%> </td>

                                <td class="campositem"> <%= convenioMV.get(i)%> </td>
                            </tr>
                            <%
                                    }
                                }

                                List pseMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM);
                                if (pseMV != null && !pseMV.isEmpty()) {
                            %>
                            <tr>                             
                                <td width="220">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">Número Aprobación: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <%
                                for (int i = 0; i < pseMV.size(); i++) {
                            %>
                            <tr>
                                <td class="campositem"> <%=DocumentoPago.PAGO_ELECTRONICO_PSE%> </td>

                                <td class="campositem"> <%= ((List) pseMV.get(i)).get(0)%> </td>

                                <td class="campositem"> <%= ((List) pseMV.get(i)).get(1)%> </td>

                                <td class="campositem"> <%= ((List) pseMV.get(i)).get(2)%> </td>

                                <td class="campositem"> <%= ((List) pseMV.get(i)).get(3)%> </td>
                            </tr>
                            <%
                                    }
                                }

                                List debitoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM);
                                if (debitoMV != null && !debitoMV.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">No. Aprobación: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < debitoMV.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_TARJETA_DEBITO%> </td>

                                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(0)%> </td>

                                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(1)%> </td>

                                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(2)%> </td>

                                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(3)%> </td>

                                <td class="campositem"> <%= ((List) debitoMV.get(i)).get(4)%> </td>
                            </tr>
                            <tr>
                                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
                            </tr>
                            <%
                                    }
                                }

                                List creditoMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM);
                                if (creditoMV != null && !creditoMV.isEmpty()) {
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">No. Aprobación: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < creditoMV.size(); i++) {
                                %>
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
                            %>
                            <tr>                             
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">Valor Documento: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < chequeMV.size(); i++) {
                                %>
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
                            %>
                            <tr>
                                <td align="left">Forma de Pago: </td>
                                <td width="220">Banco/Franquicia: </td>
                                <td width="220">No. Documento Pago: </td>
                                <td width="220">Valor Documento: </td>
                                <td width="220">Fecha Documento: </td>
                                <td width="220">Valor Pagado: </td>
                            </tr>
                            <tr>
                                <%
                                    for (int i = 0; i < consignacionMV.size(); i++) {
                                %>
                                <td class="campositem"> <%=DocumentoPago.PAGO_CONSIGNACION%> </td>

                                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(0)%> </td>

                                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(1)%> </td>

                                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(2)%> </td>

                                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(3)%> </td>

                                <td class="campositem"> <%= ((List) consignacionMV.get(i)).get(4)%> </td>
                            </tr>
                            <tr>
                                <td colspan='5'><hr class="linehorizontal" width='95%'></td>
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
                            </tr>
                            <tr>        
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
                                <%if(((List) generalMV.get(i)).get(7) != null){%>
                                    <td width="220">Banco: </td>
                                <%}%>
                                <%if(((List) generalMV.get(i)).get(8) != null){%>
                                    <td width="220">No. Consignación: </td>
                                <%}%>
                                <%if(((List) generalMV.get(i)).get(11) != null){%>
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
                                    }
                                }

                                Boolean devolucion = (Boolean) session.getAttribute(AWTrasladoTurno.DEVOLUCION);
                                if (devolucion.booleanValue()) {
                                    Pago pagoAux = liqAux.getPago();
                                    if (pagoAux != null) {
                                        List listaAplicaciones = pagoAux.getAplicacionPagos();

                                        if (listaAplicaciones != null) {
                                            for (int k = 0; k < listaAplicaciones.size(); k++) {
                                                AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
                                                if (apPago != null) {
                                                    DocumentoPago doc = apPago.getDocumentoPago();
                                                    if (doc != null) {
                            %>
                            <tr>

                                <td width="220">Forma de Pago: </td>
                                <td width="220" class="campositem"> <%=doc.getTipoPago()%> </td>
                                <td width="220">Valor Pagado: </td>
                                <td class="campositem"> <%= java.text.NumberFormat.getInstance().format(doc.getValorDocumento())%> </td>
                            </tr>
                            <% }
                                                }
                                            }
                                        }
                                    }
                                }
                            %>  

                </tr>  
                <tr>
                    <td><br></td>
                </tr>                               

                <%}%>
            </table> 









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

            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>

                    <td class="bgnsub"><p>Notas Devolutivas del turno</p></td>
                    <td width="16" class="bgnsub"></td>
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
            <table width="100%" class="camposform">
                <tr>

                    <%if (null != session.getAttribute(WebKeys.TURNO_DEVOLUCION) && rol.getRolId().equals(CRol.SIR_ROL_USUARIO_ADMINISTRATIVO)) {%>
                <form action="turno.do" method="POST" name="BUSCAR" id="BUSCAR">
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
                            <td width="150">
                                <a href="javascript:cambiarAccionSubmit('<%=AWTrasladoTurno.CONSULTAR_TURNO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></a>
                                <input type='hidden' name='r1' value="<%= gov.sir.core.negocio.modelo.constantes.CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR%>">
                                <input type='hidden' name='<%=gov.sir.core.negocio.modelo.constantes.CTurno.ID_TURNO%>' value="<%= turno.getIdWorkflow() != null ? turno.getIdWorkflow().trim() : ""%>">
                            </td>                
                            <%
                                }
                            %>
                            <%}%>

                            <td>&nbsp;</td>

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
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        </tr>

                        </table>

                        <%
                            /////////////////////
                            session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO, turno.getNotas());
                            session.setAttribute(WebKeys.TURNO, turnoPadre);
                            /////////////////////
%>



<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- class methods --%>
<%!
    public static void
            jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno(
                    HttpSession session,
                    String sessionSourceKey_TurnosMap,
                    String sessionTargetKey_IdValues,
                    String sessionTargetKey_DisplayValues,
                    String sessionTargetKey_SelectedIdValues
            ) {

        java.util.Map local_Map;

        if (null == (local_Map = (java.util.Map) session.getAttribute(sessionSourceKey_TurnosMap))) {
            local_Map = new java.util.HashMap();
        }

        Iterator local_MapEntryIterator;
        Map.Entry local_MapEntryElement;
        String local_MapEntryElementKey;

        java.util.List local_DisplayedIds;
        java.util.List local_DisplayedValues;
        java.util.List local_SelectedIds;

        if (null == (local_SelectedIds = (List) session.getAttribute(sessionTargetKey_SelectedIdValues))) {
            local_SelectedIds = new ArrayList();
        }

        // Transform ---------------------------------------------------------------------------------------
        // { Map< String:IdTurno, Imprimible > } 2 { List< String:idTurno - [ IdImprimible ] > + List< String > idImprimible }
        local_DisplayedIds = new java.util.ArrayList();
        local_DisplayedValues = new java.util.ArrayList();
        String local_DisplayedValuesElement;

        java.util.List local_MapEntryElementValue;
        gov.sir.core.negocio.modelo.Imprimible local_MapEntryElementValueItem;

        local_MapEntryIterator = local_Map.entrySet().iterator();

        for (; local_MapEntryIterator.hasNext();) {
            local_MapEntryElement = (Map.Entry) local_MapEntryIterator.next();
            local_MapEntryElementKey = (java.lang.String) local_MapEntryElement.getKey();
            local_MapEntryElementValue = (java.util.List) local_MapEntryElement.getValue();
            if ((null != (local_MapEntryElementValue))
                    && (local_MapEntryElementValue.size() > 0)) {

                local_MapEntryElementValueItem = null;

                for (Iterator iterator = local_MapEntryElementValue.iterator(); iterator.hasNext();) {
                    local_MapEntryElementValueItem = (gov.sir.core.negocio.modelo.Imprimible) iterator.next();
                    break;
                } // for

                if (null != local_MapEntryElementValueItem) {

                    // TODO: distinguir id de presentacion
                    local_DisplayedValuesElement = local_MapEntryElementKey + ": " + local_MapEntryElementValueItem.getIdImprimible();
                    local_DisplayedValues.add(local_DisplayedValuesElement);
                    local_DisplayedIds.add(local_DisplayedValuesElement);
                } // if

            } // if

        } // for

        // Serialize ---------------------------------------------------------------------------------------
        // serialize 2 session
        session.setAttribute(sessionTargetKey_IdValues, local_DisplayedIds);
        session.setAttribute(sessionTargetKey_DisplayValues, local_DisplayedValues);
        session.setAttribute(sessionTargetKey_SelectedIdValues, local_SelectedIds);
        // -------------------------------------------------------------------------------------------------

    } // end-method: jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno

    public static void
            jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno2(
                    HttpSession session,
                    String sessionSourceKey_TurnosMap,
                    String sessionTargetKey_IdValues,
                    String sessionTargetKey_DisplayValues1,
                    String sessionTargetKey_DisplayValues2
            ) {

        java.util.Map local_Map;

        if (null == (local_Map = (java.util.Map) session.getAttribute(sessionSourceKey_TurnosMap))) {
            local_Map = new java.util.HashMap();
        }

        Iterator local_MapEntryIterator;
        Map.Entry local_MapEntryElement;
        String local_MapEntryElementKey;

        java.util.List local_DisplayedIds;
        java.util.List local_DisplayedValues1;
        java.util.List local_DisplayedValues2;
        java.util.List local_SelectedIds;

        // Transform ---------------------------------------------------------------------------------------
        // { Map< String:IdTurno, Imprimible > } 2 { List< String:idTurno - [ IdImprimible ] > + List< String > idImprimible }
        local_DisplayedIds = new java.util.ArrayList();
        local_DisplayedValues1 = new java.util.ArrayList();
        local_DisplayedValues2 = new java.util.ArrayList();
        String local_DisplayedValuesElement;

        java.util.List local_MapEntryElementValue;
        gov.sir.core.negocio.modelo.Imprimible local_MapEntryElementValueItem;

        local_MapEntryIterator = local_Map.entrySet().iterator();

        for (; local_MapEntryIterator.hasNext();) {
            local_MapEntryElement = (Map.Entry) local_MapEntryIterator.next();
            local_MapEntryElementKey = (java.lang.String) local_MapEntryElement.getKey();
            local_MapEntryElementValue = (java.util.List) local_MapEntryElement.getValue();
            if ((null != (local_MapEntryElementValue))
                    && (local_MapEntryElementValue.size() > 0)) {

                local_MapEntryElementValueItem = null;

                for (Iterator iterator = local_MapEntryElementValue.iterator(); iterator.hasNext();) {
                    local_MapEntryElementValueItem = (gov.sir.core.negocio.modelo.Imprimible) iterator.next();
                    break;
                } // for

                if (null != local_MapEntryElementValueItem) {
                    local_DisplayedValuesElement = local_MapEntryElementKey + ": " + local_MapEntryElementValueItem.getIdImprimible();
                    local_DisplayedValues1.add(local_MapEntryElementKey);
                    local_DisplayedValues2.add("" + local_MapEntryElementValueItem.getIdImprimible());
                    local_DisplayedIds.add("" + local_MapEntryElementValueItem.getIdImprimible());
                } // if

            } // if

        } // for

        // Serialize ---------------------------------------------------------------------------------------
        // serialize 2 session
        session.setAttribute(sessionTargetKey_IdValues, local_DisplayedIds);
        session.setAttribute(sessionTargetKey_DisplayValues1, local_DisplayedValues1);
        session.setAttribute(sessionTargetKey_DisplayValues2, local_DisplayedValues2);
        // -------------------------------------------------------------------------------------------------

    } // end-method: jspMethod_ValueObjectsProcessor_LoadLocalViewTableHelper_ImprimiblesPendientesPorTurno


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>



