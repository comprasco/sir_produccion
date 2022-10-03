package gov.sir.core.negocio.acciones.comun;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.comun.EvnRespTurno;
import gov.sir.core.eventos.comun.EvnTurno;
import gov.sir.core.negocio.acciones.excepciones.ANTurnoException;
import gov.sir.core.negocio.acciones.excepciones.ListarNoEfectuadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.LiquidacionConservacion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CListadosPorRangos;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.util.IDidTurnoComparator;
import gov.sir.core.negocio.modelo.util.ListadoPorRangosUtil;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;

import gov.sir.fenrir.interfaz.FenrirServiceInterface;

import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.interfaz.PrintJobsInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import org.auriga.util.ExceptionPrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Esta accion de negocio es responsable de ejecutar la lógica necesaria para
 * dar respuesta a las peticiones genericas que se realizan sobre un turno
 *
 * @author Ing. Diego Hernando Cantor Rivera, mmunoz
 */
public class ANTurno extends SoporteAccionNegocio {

    /**
     * Instancia de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de printJobs
     */
    private PrintJobsInterface printJobs;

    /**
     * Constructor de la clase ANTurno Es el encargado de invocar al Service
     * Locator y pedirle una instancia del servicio que necesita
     */
    public ANTurno() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

            if (hermod == null) {
                throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio fenrir", e);
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti", e);
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
        }

        try {
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

            if (printJobs == null) {
                throw new ServicioNoEncontradoException("Servicio printJobs no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio printJobs", e);
        }

        if (printJobs == null) {
            throw new ServicioNoEncontradoException("Servicio printJobs no encontrado");
        }
    }

    /**
     * Recibe un evento sobre un turno y devuelve un evento de respuesta
     *
     * @param e el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnTurno</CODE>
     * @throws EventoException cuando ocurre un problema que no se pueda manejar
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespTurno</CODE>
     * @see gov.sir.core.eventos.comun.EvnTurno
     * @see gov.sir.core.eventos.comun.EvnRespTurno
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnTurno evento = (EvnTurno) e;

        if (evento.getTipoEvento().equals(EvnTurno.CONSULTAR)) {
            try {
                return consultar(evento);
            } catch (DAOException ex) {
                Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evento.getTipoEvento().equals(EvnTurno.LISTAR)) {
            // return listar( evento );
            return doProcess_Listar(evento);

        } else if (evento.getTipoEvento().equals(EvnTurno.FASE_INICIAL)) {
            return buscarNotasInicio(evento);
        } else if (evento.getTipoEvento().equals(EvnTurno.ENTREGA_MASIVA_REPARTO_NOTARIAL)) {
            return entregaMasivaRepartoNotarial(evento);
        } else if (evento.getTipoEvento().equals(EvnTurno.CONSULTAR_CONFIRMACION)) {
            return consultarConfirmacion(evento);
        } else if (evento.getTipoEvento().equals(EvnTurno.CONSULTAR_RELACION)) {
            return consultarRelacion(evento);
        } else if (evento.getTipoEvento().equals(EvnTurno.OBTENER_LISTADO_TURNOS_EJECUCION)) {
            return obtenerListadoPorRangosTurnosEjecucion(evento);
        }

        return null;
    }

    //	 -----------------------------------------------------------------------------------------
    private EventoRespuesta doProcess_Listar(EvnTurno evento) throws EventoException {
        // in ----------------------------------------------
        EventoRespuesta result;
        // -------------------------------------------------

        long local_EofTime;
        long local_SofTime;
        long local_Duration;

        local_SofTime = System.currentTimeMillis();

        // call --------------------------------------------
        result = listar(evento);
        // -------------------------------------------------

        local_EofTime = System.currentTimeMillis();
        local_Duration = local_EofTime - local_SofTime;
        String local_DurationAsString;
        local_DurationAsString = format_DeltaTime(local_Duration);

        String msg = "@@ DURACION ANTurno.listar: " + local_DurationAsString;
        Log.getInstance().debug(ANTurno.class, msg); 	// verbose

        // out ---------------------------------------------
        return result;
        // -------------------------------------------------

    }

    private String format_DeltaTime(long local_Duration) {
        // TODO Convertir a YYYY-MM-DD HH24:MI
        return "" + local_Duration + " milliseconds.";
    }

    // -----------------------------------------------------------------------------------------    
    /**
     * Se listan los turnos de acuerdo a la fase, estacion y proceso
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnTurno</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespTurno</CODE>
     * @throws ANTurnoException
     */
    private EvnRespTurno listar(EvnTurno evento) throws EventoException {
        Fase fase = evento.getFase();
        Estacion estacion = evento.getEstacion();
        Rol rol = evento.getRol();
        Proceso proceso = evento.getProceso();
        Usuario usuario = new Usuario();
        usuario.setUsername(evento.getUsuario().getUsuarioId());
        usuario.setNombre(evento.getUsuario().getNombre());
        Circulo circulo = evento.getCirculo();

        List tiposNota;
        List tiposNotaDevolutiva = new ArrayList();
        ProcesoPk id = new ProcesoPk();
        id.idProceso = proceso.getIdProceso();

        String faseId = null;
        if (fase != null) {
            faseId = fase.getID();
        }

        try {
            if (faseId != null) {

                tiposNota = hermod.getTiposNotaProceso(id, faseId, false);
                tiposNotaDevolutiva = hermod.getTiposNotaProceso(id, faseId, true);

            } else {
                tiposNota = hermod.getTiposNotasProceso(id);
            }
        } catch (HermodException e) {
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        }

        Hashtable tabla = new Hashtable();
        tabla.put(WebKeys.LISTA_TIPOS_NOTAS, tiposNota);
        tabla.put(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS, tiposNotaDevolutiva);

        List turnos = null;
        List turnosSirMig = null;

        try {
            if (proceso.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)
                    && fase.getID().equals(CFase.CER_ENTREGAR)) {
                turnos = hermod.getTurnos(estacion, rol, usuario, proceso, fase, null);
            } else {
                if(fase.getID().equals(CFase.PMY_NOTIFICAR_CIUDADANO) || fase.getID().equals(CFase.PMY_REGISTRAR)
                        || fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS) || fase.getID().equals(CFase.NOT_NOTA_DEVOLUTIVA)){
                        turnos = hermod.getTurnosPMY(estacion, rol, usuario, proceso, fase, circulo);
                } else{
                        turnos = hermod.getTurnos(estacion, rol, usuario, proceso, fase, circulo);
                }
            }
            if (fase.getID().equals(CFase.REG_REPARTO)) {
                turnosSirMig = hermod.getTurnosSirMig(estacion, rol, usuario, proceso, fase, circulo);
            }
        } catch (HermodException e) {
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

//      Se deben ordenar los turnos correctamente
        /**
         * @author : Edgar Lora
         * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
         * Reparto Notarial
         */
        EvnRespTurno eventoResp = new EvnRespTurno(turnos);
        try {
            Collections.sort(turnos, new IDidTurnoComparator());
        } catch (Exception e) {
            Log.getInstance().error(ANTurno.class, "No se pudieron ordenar los Turnos");
        }
        String tipoRepartoNotarial = "";
        if (fase.getID().equals(CFase.REP_REPARTO)) {
            tipoRepartoNotarial = "REP_REPARTO_NOTARIAL";
        }
        if ("REP_REPARTO_NOTARIAL".equals(tipoRepartoNotarial)) {
            ValidacionesSIR validacion = new ValidacionesSIR();
            try {
                if ("true".equals(validacion.HorarioNotarial(circulo.getIdCirculo(), tipoRepartoNotarial))) {
                    eventoResp.setEnHorario(false);
                    //throw new ListarNoEfectuadoException("No se puede hacer reparto notarial en este horario");
                } else {
                    eventoResp.setEnHorario(true);
                }

            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        eventoResp.setParametros(tabla);
        eventoResp.setTurnoSirMig(turnosSirMig);
        return eventoResp;
    }

    /**
     * Este metodo se encarga de obtener toda la informacion referente a un
     * folio
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnTurno</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespTurno</CODE>
     * @throws EventoException
     */
    private EvnRespTurno consultar(EvnTurno evento) throws EventoException, DAOException {
        Turno turno = evento.getTurno();
        int hasNoteCal = 0;
        int hasNoteCon = 0;
        int hasNoteConD = 0;
        int hasNoteConCo = 0;
        int hasNoteConCoD = 0;
        TurnoPk id = new TurnoPk();
        id.anio = turno.getAnio();
        id.idCirculo = turno.getIdCirculo();
        id.idProceso = turno.getIdProceso();
        id.idTurno = turno.getIdTurno();

        Turno nTurno = new Turno();
        Turno turnoHijo;
        Turno turnoPadre = null;
        Folio folio = null;
        List turnosFolioNoMigrados = null;
        List turnosFolioTramite = null;
        List oficios = null;

        try {
            boolean validarIndividual = evento.isValidarIndividual();
            if (validarIndividual) {
                Estacion estacion = evento.getEstacion();
                Fase fase = evento.getFase();
                boolean isValido = hermod.getTurnoEjecucionTurnoIndividual(estacion, fase, null, turno.getIdWorkflow());

                if (!isValido) {
                    throw new ANTurnoException("El turno no esta en el listado");
                }
            }

            // Se determina si el turno tiene un turno hijo no finalizado
            turnoHijo = hermod.getTurnoDependiente(id);

            if (String.valueOf(id.idProceso).equals(CProceso.PROCESO_CORRECCION)) {
                turnoPadre = hermod.getTurnoPadre(id);
            }

            nTurno = hermod.getTurno(id);
            
            List notasInf = nTurno.getNotas();
            
                if(notasInf != null && !notasInf.isEmpty()){
                    Iterator itNota = notasInf.iterator();
                    while(itNota.hasNext()){
                        Nota note = (Nota) itNota.next();
                        if(note != null && note.getTiponota().getIdTipoNota().equals(CNota.ELIMINAR_MATRICULAS_CALIFICACION)){
                            hasNoteCal++;
                        } else if (note != null && note.getTiponota().getIdTipoNota().equals(CNota.AGREGAR_MATRICULAS)){
                            hasNoteCon++;
                        } else if (note != null && note.getTiponota().getIdTipoNota().equals(CNota.ELIMINAR_MATRICULAS)){
                            hasNoteConD++;
                        } else if (note != null && note.getTiponota().getIdTipoNota().equals(CNota.AGREGAR_MATRICULAS_CORRECTIVA)){
                            hasNoteConCo++;
                        } else if (note != null && note.getTiponota().getIdTipoNota().equals(CNota.ELIMINAR_MATRICULAS_CORRECTIVA)){
                            hasNoteConCoD++;
                        }
                    }
                }
            
            turnosFolioNoMigrados = hermod.getTurnosFolioNoMigrados(turno);
            turnosFolioTramite = hermod.getTurnosFolioTramite(turno);

        } catch (HermodException e) {
            throw new ANTurnoException(
                    "No se pudo obtener la informacion del turno", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        //if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CERTIFICADOS)) {
        /*
        if(true){
            List solicitudes = new Vector();
            Solicitud solicitud = nTurno.getSolicitud();
            solicitudes = solicitud.getSolicitudFolios();

            SolicitudFolio solicitudF = new SolicitudFolio();

            Iterator itSolicitud = solicitudes.iterator();

            while (itSolicitud.hasNext()) {
                solicitudF = (SolicitudFolio) itSolicitud.next();
            }
			
            folio = solicitudF.getFolio();

            if (folio != null) {
                Folio.ID idF = new Folio.ID();
                idF.idMatricula = folio.getIdMatricula();
                idF.idZonaRegistral = folio.getIdZonaRegistral();

                try {
                    folio = new Folio();
                    folio = forseti.getFolioByID(idF);
                } catch (ForsetiException e1) {
					throw new ANTurnoException(
						"No se pudo obtener la informacion del folio",e1);
                } catch (Throwable e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(ANTurno.class,"Ha ocurrido una excepcion inesperada " +
                        printer.toString());
                    throw new EventoException(e.getMessage(),e);
                }
                

				if(folio==null){
					try {
						folio = new Folio();
						LlaveBloqueo llave = forseti.delegarBloqueoFolios(id, evento.getUsuarioNeg());
						folio = forseti.getFolioByID(idF, evento.getUsuarioNeg());
					} catch (ForsetiException e2) {
						throw new ANTurnoException(
							"No se pudo obtener la informacion del folio",e2);
					} catch (Throwable et) {
						ExceptionPrinter printer = new ExceptionPrinter(et);
						Log.getInstance().error(ANTurno.class,"Ha ocurrido una excepcion inesperada " +
							printer.toString());
						throw new EventoException(et.getMessage(),et);
					}
				}

                
            }
        }
         */
        if (turnosFolioNoMigrados.size() > 0) {
            throw new ANTurnoException("El turno no puede trabajarse, ya que tiene folios asociados que no fueron migrados.");
        }

        if (turnosFolioTramite.size() > 0) {
            throw new ANTurnoException("El turno no puede trabajarse, ya que tiene folios en tramite en el sistema FOLIO.");
        }
        //PQ
        /**yefer
         * 
         */
        Testamento testamento = null;
        double valorConservacionDoc = 0;
        double valorSinConservacion = 0; 
        double valorConservaMayorValor =0;
        double valorSinConservaMayorValor =0;
                
        if (nTurno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
            String idturnoo = "";
            if(nTurno.getSolicitud().getTurnoAnterior()==null){
                idturnoo = nTurno.getIdWorkflow();
            }else{
                idturnoo = nTurno.getSolicitud().getTurnoAnterior().getIdWorkflow();
            }
//            String idturnoo = nTurno.getSolicitud().getTurnoAnterior().getIdWorkflow();
            String turnoL = nTurno.getIdWorkflow();
            /**
             * Yeferson inicio
             */
            String fase1 = nTurno.getIdFase();
            String idSolicitud = "";
            if(nTurno.getSolicitud().getTurnoAnterior()==null){
                idSolicitud = nTurno.getSolicitud().getIdSolicitud();
            }else{
                idSolicitud =nTurno.getSolicitud().getTurnoAnterior().getSolicitud().getIdSolicitud();
            }
//            String idSolicitud =nTurno.getSolicitud().getTurnoAnterior().getSolicitud().getIdSolicitud();
            
             JDOTurnosDAO jdoTurnos = new JDOTurnosDAO();
            if (fase1.equalsIgnoreCase("DEV_ANALISIS") || fase1.equalsIgnoreCase("DEV_RESOLUCION")) {
                valorConservacionDoc = jdoTurnos.SumaConservacion(idturnoo);
                valorSinConservacion = jdoTurnos.valorsin(idturnoo,idSolicitud,0);
                valorConservaMayorValor = jdoTurnos.SumaConservacionMV(idturnoo,1);
                valorSinConservaMayorValor = jdoTurnos.valorsin(idturnoo,idSolicitud,1);
                
            } else {
                JDOLiquidacionesDAO jdoliquidaciones = new JDOLiquidacionesDAO();
                valorConservacionDoc = jdoliquidaciones.selectDevolucionConervaDoc(turnoL,0);
                valorSinConservacion = jdoliquidaciones.sinConserva(turnoL,0);
                valorConservaMayorValor =jdoliquidaciones.selectDevolucionConervaDoc(turnoL,1);
                valorSinConservaMayorValor =jdoliquidaciones.sinConserva(turnoL,1);
            }
            //fin  

            if (nTurno.getSolicitud().getTurnoAnterior() == null) {
                List neoConsignaciones = new ArrayList();
                List consignaciones = ((SolicitudDevolucion) nTurno.getSolicitud()).getConsignaciones();
                Iterator iter = consignaciones.iterator();
                while (iter.hasNext()) {
                    try {
                        Consignacion consignacion = ((Consignacion) iter.next());
                        DocumentoPago aux = null;
                        Banco banco = null;
                        DocumentoPago docPago = consignacion.getDocPago();
                        if (docPago instanceof DocPagoCheque) {
                            aux = hermod.getDocPagoCheque(docPago);
                            banco = hermod.getBancoByID(((DocPagoCheque) aux).getBanco().getIdBanco());
                            ((DocPagoCheque) docPago).setBanco(banco);
                        } else if (docPago instanceof DocPagoConsignacion) {
                            aux = hermod.getDocPagoConsignacion(docPago);
                            banco = hermod.getBancoByID(((DocPagoConsignacion) aux).getBanco().getIdBanco());
                            ((DocPagoConsignacion) docPago).setBanco(banco);
                        }
                        consignacion.setDocPago(docPago);
                        neoConsignaciones.add(consignacion);
                    } catch (HermodException e) {
                        throw new ANTurnoException(
                                "Error obteniendo consignaciones", e);
                    } catch (Throwable e) {
                        ExceptionPrinter printer = new ExceptionPrinter(e);
                        Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                                + printer.toString());
                        throw new EventoException(e.getMessage(), e);
                    }
                    ((SolicitudDevolucion) nTurno.getSolicitud()).setConsignaciones(neoConsignaciones);
                }
            } else {
                Turno turnoAnterior = nTurno.getSolicitud().getTurnoAnterior();
                Solicitud solAnt = turnoAnterior.getSolicitud();

                if (turnoAnterior.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                    try {
                        while (turnoAnterior != null) {
                            solAnt = turnoAnterior.getSolicitud();
                            List solAsociadas = solAnt.getSolicitudesHijas();
                            for (int i = 0; i < solAsociadas.size(); i++) {
                                Solicitud sol = hermod.getSolicitudById(((SolicitudAsociada) solAsociadas.get(i)).getSolicitudHija().getIdSolicitud());
                                ((SolicitudAsociada) turnoAnterior.getSolicitud().getSolicitudesHijas().get(i)).setSolicitudHija(sol);
                            }
                            turnoAnterior = solAnt.getTurnoAnterior();
                        }
                    } catch (Throwable t) {
                        new ANTurnoException("No existe solicitud de registro");
                    }
                }
            }
            try {
                oficios = hermod.getOficiosTurno(id);
                if (oficios == null) {
                    oficios = new ArrayList();
                }
            } catch (HermodException he) {
                throw new ANTurnoException(
                        "Error obteniendo oficios del tueno", he);
            } catch (Throwable t) {
                ExceptionPrinter printer = new ExceptionPrinter(t);
                Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                        + printer.toString());
                throw new EventoException(t.getMessage(), t);
            }

        }
        List turnosPosteriores = new ArrayList();//PQ SIR-57
        if (nTurno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
            try {
                String idturnoo = nTurno.getIdWorkflow();
                JDOTurnosDAO jdoTurnos = new JDOTurnosDAO();
                valorConservacionDoc = jdoTurnos.SumaConservacion(idturnoo);

                testamento = hermod.getTestamentoByID(nTurno.getSolicitud().getIdSolicitud());
            } catch (Throwable e) {
                testamento = null;
            }

            if (nTurno.getSolicitud().getTurnoAnterior() != null) {
                Turno turnoAnterior = nTurno.getSolicitud().getTurnoAnterior();
                Solicitud solAnt = turnoAnterior.getSolicitud();

                if (turnoAnterior.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                    try {
                        while (turnoAnterior != null) {
                            solAnt = turnoAnterior.getSolicitud();
                            List solAsociadas = solAnt.getSolicitudesHijas();
                            for (int i = 0; i < solAsociadas.size(); i++) {
                                Solicitud sol = hermod.getSolicitudById(((SolicitudAsociada) solAsociadas.get(i)).getSolicitudHija().getIdSolicitud());
                                ((SolicitudAsociada) solAsociadas.get(i)).setSolicitudHija(sol);
                            }
                            turnoAnterior = solAnt.getTurnoAnterior();
                        }
                    } catch (Throwable t) {
                        new ANTurnoException("No existe solicitud de registro");
                    }
                }
            }
            //SIR-57
            if (nTurno.getIdFase().equals(CFase.REG_CONFRONTAR)) {
                //Verificar si el turno pasó por testamento
                List listaHistorials = nTurno.getHistorials();
                boolean faseTestamento = false;
                if (listaHistorials != null) {
                    for (int hist = (listaHistorials.size() - 1); hist >= 0; hist--) {
                        TurnoHistoria turnoHistoriaTemp = (TurnoHistoria) listaHistorials.get(hist);
                        if (turnoHistoriaTemp != null) {
                            if (turnoHistoriaTemp.getFase().equals(CFase.REG_TESTAMENTO)) {
                                faseTestamento = true;
                                break;
                            }
                        }
                    }
                }
                if (faseTestamento) {
                    List turnosByMatricula = new ArrayList();
                    Turno turnoConf;
                    List solicitudesFolio = null;
                    SolicitudFolio solicitudFolio;
                    if (nTurno.getSolicitud() != null) {
                        solicitudesFolio = nTurno.getSolicitud().getSolicitudFolios();
                    }
                    if (solicitudesFolio != null && solicitudesFolio.size() > 0) {
                        for (int i = 0; i < solicitudesFolio.size(); i++) {
                            solicitudFolio = null;
                            turnosByMatricula = null;
                            solicitudFolio = (SolicitudFolio) solicitudesFolio.get(i);
                            try {
                                turnosByMatricula = hermod.getTurnosByMatricula(solicitudFolio.getIdMatricula());
                            } catch (Throwable e) {
                                turnosByMatricula = null;
                            }
                            if (turnosByMatricula != null) {
                                for (int j = 0; j < turnosByMatricula.size() - 1; j++) {
                                    turnoConf = (Turno) turnosByMatricula.get(j);
                                    if (turnoConf.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)
                                            && turnoConf.getIdCirculo().equals(nTurno.getIdCirculo())
                                            && ((Long.parseLong(turnoConf.getAnio()) == Long.parseLong(nTurno.getAnio())
                                            && Long.parseLong(turnoConf.getIdTurno()) > Long.parseLong(nTurno.getIdTurno()))
                                            || Long.parseLong(turnoConf.getAnio()) > Long.parseLong(nTurno.getAnio()))) {
                                        turnosPosteriores.add(turnoConf);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        EvnRespTurno respuesta = new EvnRespTurno(nTurno, folio);
        /**
         * yeferson
         */
        respuesta.setValorLiquidaConserva(valorConservacionDoc);
        respuesta.setValorSinConservacion(valorSinConservacion);
        respuesta.setValorConservaMayorValor(valorConservaMayorValor);
        respuesta.setNotasInformativasCal(hasNoteCal);
        respuesta.setNotasICon(hasNoteCon);
        respuesta.setNotasIConD(hasNoteConD);
        respuesta.setNotasIConCo(hasNoteConCo);
        respuesta.setNotasIConCoD(hasNoteConCoD);
        respuesta.setValorSinConservaMayorValor(valorSinConservaMayorValor);        
        //fin
        respuesta.setTurnoPadre(turnoPadre);
        respuesta.setTestamento(testamento);//PQ
        respuesta.setTurnosPosterioresConfTestamento(turnosPosteriores);//PQ
        if (turnoHijo != null) {
            respuesta.setTurnoDependiente(true);
            respuesta.setTurnoHijo(turnoHijo);

        }
        if (oficios != null) {
            respuesta.setOficiosDevoluciones(oficios);
        }
        //bloquar si es de certificados ampliacion tradicion SIR-101
        if (nTurno.getIdFase().equals(CFase.CER_AMPLIACION_TRADICION)) {
            TurnoPk turnoID = new TurnoPk();
            turnoID.idCirculo = turno.getIdCirculo();
            turnoID.idProceso = turno.getIdProceso();
            turnoID.idTurno = turno.getIdTurno();
            turnoID.anio = turno.getAnio();

            Solicitud solicitud = nTurno.getSolicitud();
            List solicitudes = solicitud.getSolicitudFolios();
            SolicitudFolio solicitudF = (SolicitudFolio) solicitudes.get(0);
            folio = solicitudF.getFolio();
            Folio folioDef = null;
            if (folio != null) {
                FolioPk idF = new FolioPk();
                idF.idMatricula = folio.getIdMatricula();
                try {
                    folio = forseti.getFolioByIDSinAnotaciones(idF, null);
                    folioDef = forseti.getFolioByIDSinAnotaciones(idF);
                    List anotaciones = forseti.getAnotacionesFolio(idF);
                    folio.setAnotaciones(anotaciones);
                } catch (ForsetiException e1) {
                    throw new ANTurnoException(
                            "No se pudo obtener la informacion del folio", e1);
                } catch (Throwable e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                            + printer.toString());
                    throw new EventoException(e.getMessage(), e);
                }
            }
            try {
                forseti.delegarBloqueoFolios(turnoID, evento.getUsuarioNeg());
            } catch (Throwable e) {
                throw new ANTurnoException("No se pudo bloquear el folio", e);
            }
            respuesta.setFolio(folio);
            respuesta.setFolioDefinitivo(folioDef);
        }

        //        CARLOS
        String idSolicitud = null;
        List idDocumentoPago = new ArrayList();
        String idCtp = null;
        String idCr = null;
        String canalRecaudo = null;
        String total = null;
        List info = new ArrayList();
        List error = new ArrayList();
        //        CARLOS
        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
        if(nTurno.getSolicitud().getTurnoAnterior() != null){
            try {
                idSolicitud = jdoTurnosDAO.encontrarIdSolicitudByTurno(nTurno.getSolicitud().getTurnoAnterior().getIdWorkflow());     
                idDocumentoPago = jdoTurnosDAO.encontrarIdDocumentoPagoByIdSolicitud (idSolicitud);
            } catch (DAOException ex) {
                    Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
            } 
            for (int i = 0; i < idDocumentoPago.size(); i++) {
            try {
                idCtp = jdoTurnosDAO.encontrarIdCtpByIdDocumentoPago(idDocumentoPago.get(i).toString());
                idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(idCtp);
                canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
                System.out.print("Los canalRecaudo en el for: " + canalRecaudo );
            } catch (DAOException ex) {
                    Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
            }            
                total = idDocumentoPago.get(i)+"-"+idCtp+"-"+idCr+"-"+canalRecaudo;
                info.add(total);
            }
            if(idCtp != null && canalRecaudo != null){
                respuesta.setCanalRecaudoYcuentas(info);
            }else{
                respuesta.setCanalRecaudoYcuentas(error);
            }       
        }
               
        
        return respuesta;

    }

    /**
     * Se obtienen las notas informativas de procesos cuya fase sea la de inicio
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnTurno</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespTurno</CODE>
     * @throws ANTurnoException
     */
    private EvnRespTurno buscarNotasInicio(EvnTurno evento) throws EventoException {
        Fase fase = null;
        Estacion estacion = evento.getEstacion();
        Rol rol = evento.getRol();
        String tipoRepartoNotarial = "";
        Circulo circulo = evento.getCirculo();
        try {
            fase = hermod.getFase(CFase.REP_REPARTO);
            if (fase.getID().equals("REP_REPARTO")) {
                tipoRepartoNotarial = "REP_REPARTO_MINUTAS";
            }
        } catch (Throwable ex) {
            Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
        Proceso proceso = evento.getProceso();
        Usuario usuario = new Usuario();
        usuario.setUsername(evento.getUsuario().getUsuarioId());
        usuario.setNombre(evento.getUsuario().getNombre());

        List tiposNota;
        ProcesoPk id = new ProcesoPk();
        id.idProceso = proceso.getIdProceso();

        try {

            tiposNota = hermod.getTiposNotaProceso(id, null);

        } catch (HermodException e) {
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        }

        Hashtable tabla = new Hashtable();
        tabla.put(WebKeys.LISTA_TIPOS_NOTAS, tiposNota);
        if ("REP_REPARTO_MINUTAS".equals(tipoRepartoNotarial)) {
            ValidacionesSIR validacion = new ValidacionesSIR();
            try {
                /**
                 * @author : Diana Lora
                 * @change : Mantis 0010028: Acta - Requerimiento No 058_151 -
                 * Certificados de otros circulos
                 */
                if ("true".equals(validacion.HorarioNotarial(circulo.getIdCirculo(), tipoRepartoNotarial))) {
//                            throw new ListarNoEfectuadoException("No se puede radicar minutas de reparto notarial en este horario");
                    tabla.put(WebKeys.REPARTO_MINUTAS_EN_HORARIO, "false");
                } else {
                    tabla.put(WebKeys.REPARTO_MINUTAS_EN_HORARIO, "true");
                }
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANTurno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        EvnRespTurno eventoResp = new EvnRespTurno();
        eventoResp.setParametros(tabla);
        return eventoResp;
    }

    /**
     * Este método es llamado cuando se quiere obtener la información del turno
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnRespTurno consultarConfirmacion(EvnTurno evento) throws EventoException {

        String idWorkflow = evento.getIdTurno();
        Turno turno;
        String estacion = "";
        List listaUsuarios = null;

        try {
            turno = hermod.getTurnobyWF(idWorkflow);
            
            if(turno.getIdFase() !=  null && turno.getIdFase().equals("PMY_NOTIFICAR_CIUDADANO")){
            
            gov.sir.core.util.TLSHttpClientComponent notifyREL = new gov.sir.core.util.TLSHttpClientComponent();
            boolean turnoREL = (hermod.isTurnoREL(turno.getIdWorkflow()));
            if(turnoREL){
                 notifyREL.setFase(CRespuesta.PAGO_MAYOR_VALOR);
                 notifyREL.sendMsgToREL(turno.getIdWorkflow());
            }
            }
            //Obtener la estacion:
            for (int j = 0; j < turno.getHistorials().size(); j++) {
                TurnoHistoria th = (TurnoHistoria) turno.getHistorials().get(j);
                estacion = th.getIdAdministradorSAS();
            }

            Estacion estacionSas = new Estacion();
            estacionSas.setEstacionId(estacion);
            estacionSas.setNombre(estacion);
            listaUsuarios = fenrir.getUsuariosEstacion(estacionSas);

        } catch (Throwable t) {
            throw new ANTurnoException("No se pudo obtener la informacion del turno", t);
        }
        String usuario = "";
        String fase = turno.getIdFase();
        

        EvnRespTurno eventoResp = new EvnRespTurno(turno, listaUsuarios, fase);
        return eventoResp;

    }

    /**
     * Se obtienen las notas informativas de procesos cuya fase sea la de inicio
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnTurno</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespTurno</CODE>
     * @throws ANTurnoException
     */
    private EvnRespTurno entregaMasivaRepartoNotarial(EvnTurno evento) throws EventoException {

        List turnos = evento.getTurnos();
        Map mapaTurnoHijo = new HashMap();
        Folio folio = null;

        for (Iterator iter = turnos.iterator(); iter.hasNext();) {
            Turno turno = (Turno) iter.next();

            TurnoPk id = new TurnoPk();
            id.anio = turno.getAnio();
            id.idCirculo = turno.getIdCirculo();
            id.idProceso = turno.getIdProceso();
            id.idTurno = turno.getIdTurno();

            Turno nTurno = new Turno();
            Turno turnoHijo;
            try {
                // Se determina si el turno tiene un turno hijo no finalizado
                turnoHijo = hermod.getTurnoDependiente(id);
                nTurno = hermod.getTurno(id);
            } catch (HermodException e) {
                throw new ANTurnoException("No se pudo obtener la informacion del turno", e);
            } catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                        + printer.toString());
                throw new EventoException(e.getMessage(), e);
            }

            mapaTurnoHijo.put(nTurno, turnoHijo);
        }

        EvnRespTurno respuesta = new EvnRespTurno(mapaTurnoHijo, folio);
        return respuesta;
    }

    /**
     * Este método es llamado cuando se quiere obtener lista de turnos que
     * pertenecen a una relación.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnRespTurno consultarRelacion(EvnTurno evento) throws EventoException {

        List listaTurnosRelacion = null;
        Hashtable datosTurnosCertificadosAsociados = null;

        long idProceso = evento.getProceso().getIdProceso();
        String idFase = evento.getFase().getID();
        String idRelacion = evento.getIdRelacion();
        //List certificadosValidos=new ArrayList();
        Hashtable certificadosValidos = new Hashtable();
        Relacion relacionFirma = null;

        Log.getInstance().debug(ANTurno.class, "ENTRO CONSULTA RELACION an");

        try {

            ProcesoPk procesoId = new ProcesoPk();
            procesoId.idProceso = idProceso;

            Proceso proceso = hermod.getProcesoById(procesoId);
            Fase fase = hermod.getFase(idFase);
            Circulo circulo = evento.getCirculo();

            if (fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)) {
                RelacionPk idrelacion = new RelacionPk();
                idrelacion.idFase = CFase.REG_FIRMAR;
                idrelacion.idRelacion = idRelacion;
                relacionFirma = hermod.getRelacion(idrelacion);
            }

            listaTurnosRelacion = hermod.getTurnosByRelacion(proceso, fase, circulo, idRelacion);
            
            Iterator itTurn = listaTurnosRelacion.iterator();
            while(itTurn.hasNext()){
                Turno turno = (Turno) itTurn.next();
                if(turno != null){
                   if(turno.getTurnoREL() != null && turno.getTurnoREL().equals(WebKeys.TURNO_REL)){
                       throw new ANTurnoException(" La relación es de origen REL ");
                   }
                }
            }
            datosTurnosCertificadosAsociados = this.consultarCertificadosAsociados(listaTurnosRelacion, certificadosValidos);
        } catch (ValidacionParametrosException vpe) {
            throw vpe;
        } catch (Throwable th) {
            th.printStackTrace();
            ValidacionParametrosException exception = new ValidacionParametrosException("Error al consultar los turnos para la relación : " + idRelacion + th.getMessage());
            exception.addError("Error al consultar los turnos para la relación : " + idRelacion + th.getMessage());
            throw exception;
        }

        //EvnRespTurno eventoResp =new EvnRespTurno(listaTurnosRelacion, EvnRespTurno.CONSULTAR_RELACION);
        EvnRespTurno eventoResp = new EvnRespTurno(datosTurnosCertificadosAsociados, EvnRespTurno.CONSULTAR_RELACION);
        eventoResp.setTurnosCertificadosValidos(certificadosValidos);
        if (relacionFirma != null) {
            eventoResp.setRelacion(relacionFirma);
        }
        return eventoResp;

    }

    /**
     * Este método es llamado cuando se quiere obtener lista de turnos que
     * pertenecen a una relación.
     *
     * @param List turnosRegistro
     * @return EvnTurno
     */
    private Hashtable consultarCertificadosAsociados(List turnosRegistro, Hashtable certificadosValidos) throws EventoException {

        //List listaTurnosRelacion=new ArrayList();
        Hashtable ht = new Hashtable();

        String idFase = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;

        if (turnosRegistro != null && !turnosRegistro.isEmpty()) {
            try {

                //Se recorre cada turno de registro y por cada uno se 
                //inserta en la lista un hashmap con el idWorkflow y una
                //lista con los certificados asociados
                Iterator it = turnosRegistro.iterator();
                while (it.hasNext()) {

                    //Para cada turno de registro se crea
                    //una lista para agregar sus certificados asociados
                    Turno turnoRegistro = (Turno) it.next();
                    List certificadosAsociados = new ArrayList();

                    turnoRegistro = hermod.getTurnobyWF(turnoRegistro.getIdWorkflow());

                    //Por cada solicitud hija se busca el turno asociado y su información
                    SolicitudRegistro solR = (SolicitudRegistro) turnoRegistro.getSolicitud();
                    List solHijas = solR.getSolicitudesHijas();

                    Iterator isolh = solHijas.iterator();
                    for (; isolh.hasNext();) {

                        SolicitudCertificado solTemp = new SolicitudCertificado();
                        Turno turnoTemp = new Turno();

                        SolicitudAsociada solA = (SolicitudAsociada) isolh.next();
                        SolicitudCertificado solC = (SolicitudCertificado) solA.getSolicitudHija();

                        SolicitudPk sid = new SolicitudPk();
                        sid.idSolicitud = solC.getIdSolicitud();
                        Turno temp = hermod.getTurnoBySolicitud(sid);

                        solC = (SolicitudCertificado) hermod.getSolicitudById(solC.getIdSolicitud());

                        if ((!temp.getIdFase().equals(idFase)) || (temp.getIdProceso() != idProceso)) {
                            certificadosValidos.put(temp.getIdWorkflow(), new Boolean(false));
                        } else {
                            certificadosValidos.put(temp.getIdWorkflow(), new Boolean(true));
                        }

                        Log.getInstance().debug(ANTurno.class, solC.getTurno() != null ? solC.getTurno().getIdWorkflow() : "ANTURNO IDWORKFLOW");

                        solTemp.setSolicitudFolios(solC.getSolicitudFolios());
                        solTemp.setNumeroCertificados(solC.getNumeroCertificados());

                        turnoTemp.setIdWorkflow(temp.getIdWorkflow());
                        solTemp.setTurno(turnoTemp);

                        certificadosAsociados.add(solTemp);
                    }

                    //Se agrega a la lista el hashmap con el idworkflow y 
                    //la lista de certificados asociados
                    ht.put(turnoRegistro.getIdWorkflow(), certificadosAsociados);
                    //listaTurnosRelacion.add(hm);					

                }

            } catch (Throwable th) {
                th.printStackTrace();
                ValidacionParametrosException exception = new ValidacionParametrosException();
                exception.addError("Error al determinar los certificados asociados de los turnos");
                throw exception;
            }
        }

        /*Enumeration enum = ht.keys();
		while(enum.hasMoreElements()){
			String key = (String)enum.nextElement();
			
			
		}*/
        return ht;

    }

    private EvnRespTurno obtenerListadoPorRangosTurnosEjecucion(EvnTurno evento) throws EventoException {
        Fase fase = evento.getFase();
        Estacion estacion = evento.getEstacion();
        Proceso proceso = evento.getProceso();
        Circulo circulo = evento.getCirculo();
        Rol rol = evento.getRol();
        Usuario usuario = new Usuario();
        usuario.setUsername(evento.getUsuario().getUsuarioId());
        usuario.setNombre(evento.getUsuario().getNombre());

        List tiposNota;
        List tiposNotaDevolutiva = new ArrayList();
        ProcesoPk id = new ProcesoPk();
        id.idProceso = proceso.getIdProceso();

        String faseId = null;
        if (fase != null) {
            faseId = fase.getID();
        }

        try {
            if (faseId != null) {
                tiposNota = hermod.getTiposNotaProceso(id, faseId, false);
                tiposNotaDevolutiva = hermod.getTiposNotaProceso(id, faseId, true);
            } else {
                tiposNota = hermod.getTiposNotasProceso(id);
            }
        } catch (HermodException e) {
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        }

        Hashtable tabla = new Hashtable();
        tabla.put(WebKeys.LISTA_TIPOS_NOTAS, tiposNota);
        tabla.put(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS, tiposNotaDevolutiva);

        ListadoPorRangosUtil listadoTurnos = null;

        try {
            Object[] parametros = new Object[]{circulo.getIdCirculo(), fase.getID(), estacion.getEstacionId()};
            listadoTurnos = new ListadoPorRangosUtil();
            listadoTurnos.setHermod(hermod);
            listadoTurnos.setIdListado(CListadosPorRangos.LISTADO_TURNOS_EJECUCION);
            listadoTurnos.setParametros(parametros);

        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        List turnos = null;
        try {
            turnos = hermod.getTurnos(estacion, rol, usuario, proceso, fase, circulo);
            if (turnos != null) {
                Collections.sort(turnos, new IDidTurnoComparator());
            }
        } catch (HermodException e) {
            throw new ListarNoEfectuadoException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANTurno.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespTurno eventoResp = new EvnRespTurno(turnos, EvnRespTurno.LISTAR_TURNOS_ENTREGA_DOCUMENTOS);
        eventoResp.setTipoEvento(EvnRespTurno.LISTAR_TURNOS_ENTREGA_DOCUMENTOS);
        eventoResp.setParametros(tabla);
        eventoResp.setListadoPorRangos(listadoTurnos);

        return eventoResp;
    }

}
