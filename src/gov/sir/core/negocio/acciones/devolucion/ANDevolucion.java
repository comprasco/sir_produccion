package gov.sir.core.negocio.acciones.devolucion;

import gov.sir.core.eventos.devolucion.EvnDevolucion;
import gov.sir.core.eventos.devolucion.EvnRespDevolucion;
import gov.sir.core.negocio.acciones.excepciones.ANTurnoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.PagoDevolucionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoAnteriorInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionValorDevolverException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoFranquicia;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocPagoNotaDebito;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAplicacionPago;
import gov.sir.core.negocio.modelo.constantes.CDevoluciones;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleDevolucion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucion;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.acciones.devolucion.AWDevolucion;
import gov.sir.core.web.acciones.devolucion.AWLiquidacionDevolucion;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.TreeSet;
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
import org.auriga.util.FechaConFormato;

/**
 * @author mmunoz, jvelez
 */
public class ANDevolucion extends SoporteAccionNegocio {

    private FenrirServiceInterface fenrir;

    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;

    private PrintJobsInterface printJobs;

    /**
     * Constructor de la clase ANCertificado. Es el encargado de invocar al
     * Service Locator y pedirle una instancia del servicio que necesita
     */
    public ANDevolucion() throws EventoException {
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
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti", e);
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

        try {
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs", e);
        }

        //OBTENER FENRIR
        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
        } catch (ServiceLocatorException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "No se encontró el servicio fenrir:" + ep.toString());
            throw new ServicioNoEncontradoException("No se encontró el servicio fenrir:" + ep.toString(), e);
        }

    }

    /**
     * Recibe un evento de certificado y devuelve un evento de respuesta
     *
     * @param e el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnDevolucion</CODE>
     * @throws ANDevolucionException cuando ocurre un problema que no se pueda
     * manejar
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespDevolucion</CODE>
     * @see gov.sir.core.eventos.devolucion.EvnDevolucion
     * @see gov.sir.core.eventos.devolucion.EvnRespDevolucion
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnDevolucion evento = (EvnDevolucion) e;

        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnDevolucion.AVANZAR)) {
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.RESOLUCION_ACEPTAR)) {
            try {
                return procesar_ResolucionAceptar(evento);
            } catch (DAOException ex) {
                Logger.getLogger(ANDevolucion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //Devolver un turno de resolución a la fase de análisis
        else if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_RESOLUCION_ANALISIS)) {
            return avanzarTurno(evento);
        } else if(evento.getTipoEvento().equals(EvnDevolucion.ESPERANDO_JUZGADO)){
            return consultarJuzgado(evento);
        } else if(evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA)){
            Turno turnoRecargado = agregarRecursoNotaNotificada(evento);
            EvnRespDevolucion eventoRespuesta = new EvnRespDevolucion();
            eventoRespuesta.setTipoEvento(EvnDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA);
            eventoRespuesta.setTurnoRespuesta(turnoRecargado);
            return eventoRespuesta;
        //El usuario decide interponer recursos 
        }else if (evento.getTipoEvento().equals(EvnDevolucion.CONFIRMAR_RECURSOS)) {
            return avanzarTurno(evento);
        } //Ya se cuenta con los dineros para devolver al ciudadano
        else if (evento.getTipoEvento().equals(EvnDevolucion.CONFIRMAR_DINERO)) {
            return avanzarTurno(evento);
        } //El usuario decide no interponer recursos
        else if (evento.getTipoEvento().equals(EvnDevolucion.NEGAR_RECURSOS)) {
            return avanzarTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_CALIFICACION)){
            return devolverCalificacion(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.EDITAR_RECURSO)) {
            return editarRecurso(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.EDITAR_RECURSO_NOTA)){
            return editarRecursoNotasDevolutivas(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.AVANZAR_NOTA_DEV_NOTIFICADA)) {
                return avanzarNotaDevolutivaNotificada(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.NOTIFICAR_NOTA_DEVOLUTIVA)) {
                return notificarNotaDevolutiva(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT)){
                return agregarResolucionRecursosNot(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.ELIMINAR_RESOLUCIONES_NOT)){
                return eliminarResolucionesNot(evento);
        }
            //Los recursos interpuestos por el ciudadano han sido aceptados
        else if (evento.getTipoEvento().equals(EvnDevolucion.ACEPTAR_INTERPOSICION_RECURSOS)) {
            Turno turno = evento.getTurno();
            SolicitudDevolucion solDev = (SolicitudDevolucion) turno.getSolicitud();
            if (evento.getInterponeRecursos() != null && evento.getInterponeRecursos().equals(AWDevolucion.CHECKED_NO)) {
                if (!solDev.isAprobada()) {
                    evento.setRespuestaWF("*");
                }
                return avanzarTurno(evento);
            }

            List recursos = new ArrayList();
            try {
                turno = hermod.getTurnobyWF(turno.getIdWorkflow());
                Iterator iter = turno.getHistorials().iterator();
                while (iter.hasNext()) {
                    TurnoHistoria hist = (TurnoHistoria) iter.next();
                    if (hist.getRecursos() != null && hist.getRecursos().size() > 0) {
                        recursos.addAll(hist.getRecursos());
                    }
                }
            } catch (HermodException h) {
                throw new EventoException(h.getMessage());
            } catch (Throwable t) {
                throw new EventoException(t.getMessage());
            }

            if (recursos.size() == 0 && evento.getInterponeRecursos() != null && !evento.getInterponeRecursos().equals(AWDevolucion.CHECKED_NO)) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno si no se han agregado recursos");
            }
            try {
                return procesar_ResolucionAceptar(evento);

                //Los recursos interpuestos por el ciudadano han sido rechazados.
            } catch (DAOException ex) {
                Logger.getLogger(ANDevolucion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (evento.getTipoEvento().equals(EvnDevolucion.NEGAR_INTERPOSICION_RECURSOS)) {
            return avanzarTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.RESOLUCION_FIRMAR_ACEPTAR)) {
            resolucionFirmar(evento);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.ANALISIS_ACEPTAR)) {
            String mensaje = "";
            if (evento.getTurno() != null && evento.getTurno().getSolicitud() != null && evento.getTurno().getSolicitud().getTurnoAnterior() != null) {
                validarTurnoAnt(evento);//validar turno anterior
                getDocumentoPago(evento);//validar turno anterior
                mensaje = "TURNO ANTERIOR No.: " + evento.getIdWfTurnoAnterior();
            } else {//validar que las consignaciones y cheques sean válidos
                mensaje = "CONSIGNACIONES O CHEQUES No.: ";
                List docPagos = null;
                if (evento.getTurno().getSolicitud() != null) {
                    SolicitudDevolucion solDev = (SolicitudDevolucion) evento.getTurno().getSolicitud();
                    if (solDev.getConsignaciones() != null) {
                        docPagos = solDev.getConsignaciones();
                    }
                    if (docPagos != null && docPagos.size() > 0) {
                        DocumentoPago validaDocuPagoExiste = null;
                        boolean pagoInvalido = false;
                        for (int i = 0; i < docPagos.size(); i++) {
                            if (docPagos.get(i) instanceof Consignacion) {
                                validaDocuPagoExiste = ((Consignacion) docPagos.get(i)).getDocPago();
                            }
                            try {
                                DocumentoPago validaDocuPagoAux = null;
                                if (validaDocuPagoExiste instanceof DocPagoConsignacion) {
                                    validaDocuPagoAux = hermod.getDocPagoConsignacion(((Consignacion) docPagos.get(i)).getDocPago());
                                } else if (validaDocuPagoExiste instanceof DocPagoCheque) {
                                    validaDocuPagoAux = hermod.getDocPagoCheque(((Consignacion) docPagos.get(i)).getDocPago());
                                }
                                if (validaDocuPagoAux == null) {
                                    validaDocuPagoAux = validarConsignacionCheque(validaDocuPagoExiste);
                                }
                                pagoInvalido = hermod.getDocsPagosDevolucion(validaDocuPagoAux);
                                if (validaDocuPagoAux instanceof DocPagoConsignacion) {
                                    mensaje += ((DocPagoConsignacion) validaDocuPagoAux).getNoConsignacion() + " ";
                                } else if (validaDocuPagoAux instanceof DocPagoCheque) {
                                    mensaje += ((DocPagoCheque) validaDocuPagoAux).getNoCheque() + " ";
                                }
                            } catch (HermodException e1) {
                                throw new TurnoAnteriorInvalidoException("La consignacion o cheque  ingresado es inválido", e1);
                            } catch (Throwable e2) {
                                throw new TurnoAnteriorInvalidoException("Error de validacion: ", e2);
                            }
                            if (pagoInvalido) {
                                throw new ValidacionParametrosException("La consignacion o cheque esta asociada a un turno");
                            }
                        }
                    }
                }
            }
            ((TurnoHistoria) evento.getTurno().getHistorials().get(evento.getTurno().getHistorials().size() - 1)).setAccion(mensaje);
            analisisAceptar(evento);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.ANALISIS_NEGAR)) {
            String mensaje = "";
            SolicitudDevolucion solDev = (SolicitudDevolucion) evento.getTurno().getSolicitud();
            if (!solDev.isAprobada()) {
                List historials = evento.getTurno().getHistorials();
                for (int i = historials.size() - 1; i >= 0; i--) {
                    TurnoHistoria th = (TurnoHistoria) historials.get(i);
                    if (th.getFase().equals(evento.getTurno().getIdFase()) && th.getRespuesta() != null
                            && th.getRespuesta().equals(CRespuesta.NEGAR)) {
                        throw new TurnoNoAvanzadoException("El turno ya se encuentra negado");
                    }
                    /**
                     * @author: Fernando Padilla Velez
                     * @changue: Mantis 3050, Se modifico la validación para que
                     * solo preguntara sobre la última historia.
                     */
                    break;
                }
            }
            Hashtable tabla = new Hashtable();
            Turno turno = evento.getTurno();
            try {
                tabla.put(Processor.RESULT, evento.getRespuestaWF());
                tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNec().getUsername());
                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tabla, turno);
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
            }
            /*Se bloquea la posibilidad que se coloque nota, cuando se da NEGAR a un turno de devolucion de registro.
                        * @author: Julio Alcazar
                        * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
             */
            if (solDev.getTurnoAnterior() != null) {
                if (!CProceso.PROCESO_REGISTRO.equals(Long.toString(solDev.getTurnoAnterior().getIdProceso()))) {
                    mensaje = "SE DESASOCIA EL TURNO ANTERIOR " + solDev.getTurnoAnterior().getIdWorkflow();
                }
            } else if (solDev.getConsignaciones() != null) {
                mensaje = "SE NIEGAN: ";
                Iterator iter = solDev.getConsignaciones().iterator();
                while (iter.hasNext()) {
                    Consignacion consig = (Consignacion) iter.next();
                    if (consig.getDocPago() instanceof DocPagoConsignacion) {
                        mensaje += "CN. " + ((DocPagoConsignacion) consig.getDocPago()).getNoConsignacion() + " ";
                    } else if (consig.getDocPago() instanceof DocPagoCheque) {
                        mensaje += "CH. " + ((DocPagoCheque) consig.getDocPago()).getNoCheque() + " ";
                    }
                }
            }
            analisisNegar(evento);

            if (solDev.getTurnoAnterior() == null) {
                ((SolicitudDevolucion) evento.getTurno().getSolicitud()).setConsignaciones(new ArrayList());
            } else {
                evento.getTurno().getSolicitud().setTurnoAnterior(null);
            }
            ((TurnoHistoria) evento.getTurno().getHistorials().get(evento.getTurno().getHistorials().size() - 1)).setAccion(mensaje);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_SIN_RECURSOS)
                || evento.getTipoEvento().equals(EvnDevolucion.FINALIZAR_SIN_RECURSOS)
                || evento.getTipoEvento().equals(EvnDevolucion.ESPERAR_INTERPONER_RECURSOS)) {

            if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_SIN_RECURSOS)) {
                evento.setRespuestaWF(EvnDevolucion.SIN_RECURSO_DEVOLVER);
            } else if (evento.getTipoEvento().equals(EvnDevolucion.FINALIZAR_SIN_RECURSOS)) {
                evento.setRespuestaWF(EvnDevolucion.SIR_RECURSO_FINALIZAR);
            } else {
                evento.setRespuestaWF(EvnDevolucion.CON_RECURSO);
            }
            notificarAceptar(evento);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.PAGO_CONFIRMAR)) {
            SolicitudDevolucion solicitud = (SolicitudDevolucion) evento.getTurno().getSolicitud();
            if (solicitud.isAprobada()) {
                efectuarPago(evento);
            }
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.REPOSICION_ACEPTAR)) {
            actualizarAceptacionSolicitud(evento, true);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.APELACION_ACEPTAR)) {
            actualizarAceptacionSolicitud(evento, true);
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.REPOSICION_RECURSO)) {
            return avanzarTurno(evento);

        } else if (evento.getTipoEvento().equals(EvnDevolucion.APELACION_RECURSO)) {
            return avanzarTurno(evento);

        } //Regresar de la fase de pago a la fase de resolucion
        else if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_RESOLUCION)) {
            return avanzarTurno(evento);
        } //Regresar de la fase de pago a la fase de recursos
        else if (evento.getTipoEvento().equals(EvnDevolucion.DEVOLVER_RECURSOS)) {
            return avanzarTurno(evento);
        } //Agregar Recursos al turno de devoluciones
        else if (evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_RECURSO)) {
            Turno turnoRecargado = agregarRecurso(evento);
            EvnRespDevolucion eventoRespuesta = new EvnRespDevolucion();
            eventoRespuesta.setTipoEvento(EvnDevolucion.AGREGAR_RECURSO);
            eventoRespuesta.setTurnoRespuesta(turnoRecargado);
            return eventoRespuesta;

        } //Modificar el valor de la devolución luego de la interposición de recursos.
        else if (evento.getTipoEvento().equals(EvnDevolucion.MODIFICAR_VALOR_DEVOLUCION)) {
            modificarValorDevolucion(evento);
            return avanzarTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT) || evento.getTipoEvento().equals(EvnDevolucion.CAMBIAR_TURNO_DEVOLUCION)) {
            List listaAsociados = validarTurnoAnt(evento);
            Turno turno = evento.getTurno();
            List listaDocPago = getDocumentoPago(evento);
            EvnRespDevolucion eventoRespuesta = new EvnRespDevolucion();
            eventoRespuesta.setListaCertificadosAsociados(listaAsociados);
            eventoRespuesta.setDocumentoPago(listaDocPago);
//        //        CARLOS
            String idSolicitud = "";
            List idDocumentoPago = new ArrayList();
            List info = new ArrayList();
            List error = new ArrayList();
            String pagoAnterior = "";
            String idCtp = "";
            String idCb = "";
            String idCr = "";
            String idTipoDocPago = "";
            String cuentaBancaria = "";
            String canalRecaudo = "";
            String tipoDocPago = "";
            String total = "";
            String dcpgNoAprovacion = "";
            String dcpgNoConsignacion = "";
            String numeroReferencia = "";
            String bancoId = "";
            String bancoNombre = "";
            JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO();
        
            try {
                    turno = hermod.getTurnobyWF(evento.getIdWfTurnoAnterior());
                    idSolicitud = jdoTurnosDAO.encontrarIdSolicitudByTurno(turno.getIdWorkflow());     
                            idDocumentoPago = jdoTurnosDAO.encontrarIdDocumentoPagoByIdSolicitud(idSolicitud);
                    for (int i = 0; i < idDocumentoPago.size(); i++) {
                        idCtp = jdoTurnosDAO.encontrarIdCtpByIdDocumentoPago(idDocumentoPago.get(i).toString());
                        idCb = jdoTurnosDAO.encontrarIdCbByIdCtp(idCtp);
                                if (idCb != null) {
                            cuentaBancaria = jdoTurnosDAO.encontrarNumerosCuenta(idCb);
                                } else {
                            cuentaBancaria = " ";
                        }
                        idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(idCtp);
                        canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
                        dcpgNoAprovacion = jdoTurnosDAO.encontrarDcpgNoAprovacionByIdDocumentoPago(idDocumentoPago.get(i).toString());
                                if (dcpgNoAprovacion != null) {
                            numeroReferencia = dcpgNoAprovacion;
                                } else if ((dcpgNoConsignacion = jdoTurnosDAO.encontrarDcpgNoConsignacionByIdDocumentoPago(idDocumentoPago.get(i).toString())) != null) {
                            numeroReferencia = dcpgNoConsignacion;
                                } else {
                            numeroReferencia = null;
                        }
                        idTipoDocPago = jdoTurnosDAO.encontrarIdTipoDocPagoByIdCtp(idCtp);
                        tipoDocPago = jdoTurnosDAO.encontrarTipoDocPagoByIdTipoDocPago(idTipoDocPago);
                        bancoId = jdoTurnosDAO.encontrarIdBancoByCuentaBancaria(cuentaBancaria);
                        bancoNombre = jdoTurnosDAO.encontrarBancoByIdBanco(bancoId);
                        total = idCtp + "-" + idCb + "-" + cuentaBancaria + "-" + idCr + "-" + canalRecaudo + "-" + idTipoDocPago + "-" + tipoDocPago + "-" + numeroReferencia + "-" + bancoNombre;
                        pagoAnterior = idCtp;
                        info.add(total);
                    }
                    if(pagoAnterior != null && canalRecaudo != null){
                        eventoRespuesta.setCanalRecaudoYcuentas(info);
                    }else{
                        eventoRespuesta.setCanalRecaudoYcuentas(error);
                    }        
            } catch (Throwable e1) {
                ExceptionPrinter printer = new ExceptionPrinter(e1);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e1.getMessage(), e1);
            }
        
            //eventoRespuesta.setTipoEvento(EvnDevolucion.AGREGAR_TURNO_ANT);
            eventoRespuesta.setTipoEvento(evento.getTipoEvento());
            Turno tAnt = null;
            try {
                tAnt = hermod.getTurnobyWF(evento.getIdWfTurnoAnterior());

                Turno turnoAnterior = tAnt;
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

                if (evento.getTipoEvento().equals(EvnDevolucion.CAMBIAR_TURNO_DEVOLUCION)) {
                    turno.getSolicitud().setTurnoAnterior(tAnt);
                    List consignaciones = null;
                    if (turno.getSolicitud() instanceof SolicitudDevolucion) {
                        SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
                        if (solicitud != null && solicitud.getConsignaciones() != null) {
                            consignaciones = solicitud.getConsignaciones();
                        }
                    }
                    hermod.actualizaSolicitud(turno.getSolicitud(), consignaciones);
                }

            } catch (Throwable e1) {
                ExceptionPrinter printer = new ExceptionPrinter(e1);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e1.getMessage(), e1);
            }
            eventoRespuesta.setTurnoRespuestaAnterior(tAnt);
            return eventoRespuesta;

        } else if (evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_CONSIGNACION_CHEQUE)) {
            DocumentoPago docPago = evento.getDocPago();//validarConsignacionCheque(evento);
            Banco banco = null;
            try {
                if (docPago instanceof DocPagoCheque) {
                    banco = hermod.getBancoByID(((DocPagoCheque) docPago).getBanco().getIdBanco());
                    ((DocPagoCheque) docPago).setBanco(banco);
                } else if (docPago instanceof DocPagoConsignacion) {
                    banco = hermod.getBancoByID(((DocPagoConsignacion) docPago).getBanco().getIdBanco());
                    ((DocPagoConsignacion) docPago).setBanco(banco);
                }
            } catch (HermodException h) {
                throw new EventoException(h.getMessage());
            } catch (Throwable t) {
                ExceptionPrinter printer = new ExceptionPrinter(t);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(t.getMessage(), t);
            }

            EvnRespDevolucion eventoRespuesta = new EvnRespDevolucion();
            List listDocPago = new ArrayList();
            listDocPago.add(docPago);
            eventoRespuesta.setDocumentoPago(listDocPago);
            eventoRespuesta.setTipoEvento(EvnDevolucion.AGREGAR_CONSIGNACION_CHEQUE);
            return eventoRespuesta;

        } else if (evento.getTipoEvento().equals(EvnDevolucion.IMPRIMIR_BORRADOR)) {
            imprimirBorrador(evento);
            return new EvnRespDevolucion();

        } else if (evento.getTipoEvento().equals(AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO)) {
            List listaChequeo = consultarItemsChequeo(evento);
            EvnRespDevolucion respuestaDevolucion = new EvnRespDevolucion();
            respuestaDevolucion.setTipoEvento(AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO);
            respuestaDevolucion.setListaItemsChequeo(listaChequeo);
            return respuestaDevolucion;

        } else if (evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_RESOLUCION)) {
            return agregarResolucion(evento);
        } else if (evento.getTipoEvento().equals(EvnDevolucion.ELIMINAR_RESOLUCIONES)) {
            return eliminarResoluciones(evento);
        }

        return null;
    }

    /**
     * notificarAceptar
     *
     * @param evento EvnDevolucion
     */
    private void notificarAceptar(EvnDevolucion evento) throws EventoException {
        Oficio of = obtenerUltimaResolucion(evento);
        OficioPk id = new OficioPk(of.getIdOficio());

        try {
            hermod.actualizarNumeroOficio(id, evento.getNumeroResolucion());
        } catch (HermodException e) {
            throw new EventoException("No se pudo agregar el número de resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }

    /**
     * resolucionFirmar
     *
     * @param evento EvnDevolucion
     */
    private void resolucionFirmar(EvnDevolucion evento) throws EventoException {
        Oficio of = obtenerUltimaResolucion(evento);
        OficioPk id = new OficioPk(of.getIdOficio());

        try {
            hermod.actualizarFirmaOficio(id, true);
        } catch (HermodException e) {
            throw new EventoException("No se pudo firmar la resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }

    /**
     * imprimirBorrador
     *
     * @param evento EvnDevolucion
     */
    private void imprimirBorrador(EvnDevolucion evento) throws EventoException {
        String resolucion = evento.getResolucion();

        Oficio of = new Oficio();
        of.setTextoOficio(resolucion);
        of.setTurnoHistoria(obtenerTurnoHistoriaActual(evento));
        of.setFirmado(false);

        imprimir(new ImprimibleResolucion(of, evento.getTurno(), true, CTipoImprimible.RESOLUCION), evento.getUID());
    }

    /**
     * Realiza la consulta de los datos asociados al pago correspondientes al
     * turno que se consulto, si este existe. Retorna los datos asociados al
     * pago del turno consultado
     *
     * @param evento EvnDevolucion
     */
    private List getDocumentoPago(EvnDevolucion evento) throws EventoException {

        String idWf = evento.getIdWfTurnoAnterior();
        Turno tAnt = null;
        DocumentoPago docPago = null;
        List rta = new ArrayList();

        try {
            tAnt = hermod.getTurnobyWF(idWf);
            if (tAnt != null) {
                Solicitud sol = tAnt.getSolicitud();
                List listDocPago = hermod.getDocumentoPagoBySolicitud(sol.getIdSolicitud());
                /*for (int i=0; i<listDocPago.size();i++){
					docPago = (DocumentoPago)listDocPago.get(i);
					if (docPago != null){
						if (docPago instanceof DocPagoConsignacion) {
							DocPagoConsignacion docPagoConsignacion = hermod.getDocPagoConsignacion(docPago);
							rta.add(docPagoConsignacion);
						} else if (docPago instanceof DocPagoCheque){
							DocPagoCheque docPagoCheque = hermod.getDocPagoCheque(docPago);
							rta.add(docPagoCheque);
						} else if (docPago instanceof DocPagoEfectivo){
							DocPagoEfectivo docPagoEfectivo = hermod.getDocPago(docPago);
							rta.add(docPagoCheque);
						}
					}
				}*/
                rta = listDocPago;
            }
        } catch (HermodException e) {
            throw new TurnoAnteriorInvalidoException("El turno anterior ingresado es inválido", e);
        } catch (Throwable e) {
            throw new TurnoAnteriorInvalidoException("Imposible obtener la informacion de pago del turno", e);
        }
        return rta;
    }

    /**
     * Realiza la validación de información del turno anterior para una
     * solicitud de devolución de dineros. Retorna una lista con los ids de los
     * turnos de certificados asociados (si aplica)
     *
     * @param evento EvnDevolucion
     */
    private List validarTurnoAnt(EvnDevolucion evento) throws EventoException {

        String idWf = evento.getIdWfTurnoAnterior();
        String anio = null;
        String idTurnoDev = null;

        Turno tAnt = null;
        List sig = null;
        List notasInformativas = evento.getNotasInformativas();
        boolean tieneNotasInformativas = false;

        if (notasInformativas != null) {
            tieneNotasInformativas = true;
        }
        if (evento.getTurno() != null) {
            anio = evento.getTurno().getAnio();
            idTurnoDev = evento.getTurno().getIdTurno();
            List notas = evento.getTurno().getNotas();
            if (notas != null && notas.size() > 0 && ((Nota) notas.get(notas.size() - 1)).getIdFase().equals(CFase.DEV_ANALISIS)) {
                if (!tieneNotasInformativas) {
                    tieneNotasInformativas = true;
                }
            }
        }

        //1. Identificar si se obtuvo un turno válido. 
        try {
            tAnt = hermod.getTurnobyWF(idWf);
            if (tAnt == null) {

                throw new ValidacionParametrosException("No se pudo obtener la informacion del turno");
            }
            if (tAnt.getAnulado().equals(CTurno.TURNO_ANULADO) && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                throw new ValidacionParametrosException("El turno " + idWf + " esta anulado. Por lo tanto no se permite avanzar de fase");
            }
            //Se obtiene lista de los turnos de los cuales el turno es turno anterior. 
            sig = hermod.getTurnosSiguientesDevolucion(idWf);
            List sig2 = new ArrayList();

            //2. Si el turno anterior ya fue utilizado como un turno anterior, no es posible asociarlo.
            if (sig != null && sig.size() > 0) {
                Turno turnoAnterior = (Turno) sig.get(sig.size() - 1);

                Turno turnoPrimer = null;
                if (sig.size() > 2) {
                    turnoPrimer = (Turno) sig.get(sig.size() - 2);
                } else {
                    turnoPrimer = (Turno) sig.get(0);
                }
                TurnoPk idTurnotemp = new TurnoPk();
                idTurnotemp.anio = turnoPrimer.getAnio();
                idTurnotemp.idCirculo = turnoPrimer.getIdCirculo();
                idTurnotemp.idProceso = turnoPrimer.getIdProceso();
                idTurnotemp.idTurno = turnoPrimer.getIdTurno();
                turnoPrimer = hermod.getTurno(idTurnotemp);
                if (turnoPrimer.getSolicitud() != null && turnoPrimer.getSolicitud().getTurnoAnterior() != null) {
                    Solicitud solicitud = turnoPrimer.getSolicitud();
                    if (solicitud instanceof SolicitudRegistro) {
                        if (turnoPrimer.getAnulado().equals("N") && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                            throw new TurnoAnteriorInvalidoException("El turno " + tAnt.getIdWorkflow() + " ya tiene nuevas entradas en el turno: " + turnoPrimer.getIdWorkflow() + ".");
                        }
                    }
                }

                /*
                        * 
                        * @author: Julio Alcazar
                        * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                 */
                TreeMap listaTurnos = new TreeMap();
                List turnoAnteriores = new ArrayList();
                turnoAnteriores = hermod.getTurnosAnteriores(tAnt.getIdWorkflow());
                turnoAnteriores.addAll(hermod.getTurnosSiguientes(tAnt.getIdWorkflow()));
                turnoAnteriores.add(tAnt);
                Iterator itera = turnoAnteriores.iterator();
                while (itera.hasNext()) {
                    Turno turnoTemp = (Turno) itera.next();
                    if (Long.valueOf(CProceso.PROCESO_REGISTRO).equals(turnoTemp.getIdProceso()) && hermod.getTurnosDevolucion(turnoTemp) != null) {
                        List nuevos = hermod.getTurnosDevolucion(turnoTemp);
                        Iterator iteracion = nuevos.iterator();
                        while (iteracion.hasNext()) {
                            Turno turnoNuevo = (Turno) iteracion.next();
                            String turnoID = turnoNuevo.getAnio() + turnoNuevo.getIdCirculo() + turnoNuevo.getIdProceso() + turnoNuevo.getIdTurno();
                            listaTurnos.put(turnoID, turnoNuevo);
                        }
                    }
                }

                if (listaTurnos != null && listaTurnos.size() > 0 && (!evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT))) {
                    /*
                            * Se reorganizan las excepciones de los turnos de devolucion para tener en cuenta el listado total de turnos anteriores
                            * @author: Julio Alcazar
                            * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                     */
                    boolean pago_devolucion = false;
                    String turno_pago_devolucion = "";
                    Iterator it = listaTurnos.values().iterator();
                    label:
                    while (it.hasNext()) {
                        Turno turnoAnteriorTemp = (Turno) it.next();
                        TurnoPk idTurnoTemp = new TurnoPk();
                        idTurnoTemp.anio = turnoAnteriorTemp.getAnio();
                        idTurnoTemp.idCirculo = turnoAnteriorTemp.getIdCirculo();
                        idTurnoTemp.idProceso = turnoAnteriorTemp.getIdProceso();
                        idTurnoTemp.idTurno = turnoAnteriorTemp.getIdTurno();
                        turnoAnteriorTemp = hermod.getTurno(idTurnoTemp);
                        if (turnoAnteriorTemp.getIdWorkflow().equals(evento.getTurno().getIdWorkflow())) {
                            continue;
                        }
                        List historial = turnoAnteriorTemp.getHistorials();
                        if (historial != null) {
                            Iterator iterator = historial.iterator();
                            while (iterator.hasNext()) {
                                TurnoHistoria turnoHistoria = (TurnoHistoria) iterator.next();
                                if (turnoHistoria.getFase() != null && ((turnoHistoria.getFase()).equals(CFase.DEV_PAGO_DEVOLUCION)) && (CRespuesta.CONFIRMAR).equals(turnoHistoria.getRespuesta())) {
                                    pago_devolucion = true;
                                    turno_pago_devolucion = turnoAnteriorTemp.getIdWorkflow();
                                    break label;
                                }
                            }
                        }
                    }
                    if (pago_devolucion) {
                        throw new TurnoAnteriorInvalidoException("El turno: " + tAnt.getIdWorkflow() + " tiene devolución de dinero registrado en el turno: " + turno_pago_devolucion + ".");
                    }

                }

                if (turnoAnterior != null) {
                    TurnoPk idTurno = new TurnoPk();
                    idTurno.anio = turnoAnterior.getAnio();
                    idTurno.idCirculo = turnoAnterior.getIdCirculo();
                    idTurno.idProceso = turnoAnterior.getIdProceso();
                    idTurno.idTurno = turnoAnterior.getIdTurno();
                    turnoAnterior = hermod.getTurno(idTurno);

                    if (turnoAnterior.getSolicitud().getTurnoAnterior() != null) {
                        sig2 = hermod.getTurnosDevolucion(turnoAnterior.getSolicitud().getTurnoAnterior());
                    }
                    if (!evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                        boolean isturnoSiguiente = false;
                        String turnosSiguiente = "";
                        Iterator it = sig2.iterator();
                        String respuesta = "";
                        while (it.hasNext()) {
                            Turno turnoAnteriorTemp = (Turno) it.next();
                            TurnoPk idTurnoTemp = new TurnoPk();
                            idTurnoTemp.anio = turnoAnteriorTemp.getAnio();
                            idTurnoTemp.idCirculo = turnoAnteriorTemp.getIdCirculo();
                            idTurnoTemp.idProceso = turnoAnteriorTemp.getIdProceso();
                            idTurnoTemp.idTurno = turnoAnteriorTemp.getIdTurno();
                            turnoAnteriorTemp = hermod.getTurno(idTurnoTemp);
                            respuesta = "";
                            List historial = turnoAnteriorTemp.getHistorials();
                            if (historial != null) {
                                Iterator iterator = historial.iterator();
                                while (iterator.hasNext()) {
                                    TurnoHistoria turnoHistoria = (TurnoHistoria) iterator.next();
                                    if (turnoHistoria.getFase() != null && ((turnoHistoria.getFase()).equals(CFase.DEV_RECURSOS))) {
                                        respuesta = turnoHistoria.getRespuesta();
                                    }
                                }
                            }
                            if (evento.getTipoEvento().equals(EvnDevolucion.CAMBIAR_TURNO_DEVOLUCION) && !("*").equals(respuesta)) {
                                throw new TurnoAnteriorInvalidoException("El turno " + tAnt.getIdWorkflow() + " tiene devolución de dinero registrado en el turno " + turnoAnteriorTemp.getIdWorkflow() + ".");
                            }
                            if (turnoAnteriorTemp.getSolicitud().getTurnoAnterior() != null && turnoAnteriorTemp.getSolicitud().getTurnoAnterior().getIdWorkflow() != null
                                    && turnoAnteriorTemp.getSolicitud().getTurnoAnterior().getIdWorkflow().equals(tAnt.getIdWorkflow()) && evento.getTurno() != null
                                    && turnoAnteriorTemp.getFechaInicio().before(evento.getTurno().getFechaInicio()) && !("*").equals(respuesta)) {
                                turnosSiguiente = turnoAnteriorTemp.getIdWorkflow();
                                turnosSiguiente = " en el turno " + turnosSiguiente;
                                isturnoSiguiente = true;
                                break;
                            }
                        }
                        if (turnoAnterior.getAnulado().equals("N") && isturnoSiguiente) {
                            throw new TurnoAnteriorInvalidoException("El turno: " + tAnt.getIdWorkflow() + " ya fue utilizado como turno anterior" + turnosSiguiente + ".");
                        }
                    }
                }
            }

            //3. Si el turno no es de certificados o de registro se genera una excepción. 
            if (tAnt.getSolicitud() instanceof SolicitudCorreccion) {
                throw new ValidacionParametrosException("El turno asociado no es válido. El turno es de correcciones");
            }

            //CASO TURNOS DE REGISTRO. 
            if (tAnt.getSolicitud() instanceof SolicitudRegistro) {
                Date fechaFin = tAnt.getFechaFin();
                long meses4 = (long) 1000 * 60 * 60 * 24 * 120; // 4 meses
                Date fechaLimite = new Date(new Date().getTime() - meses4);

                //4. Si el turno anterior no tiene fecha de finalización, se genera una excepción. 
                if (fechaFin == null && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. No tiene fecha de finalización.");
                }

                //5. Si han pasado 4 meses luego de la finalización del turno se genera una excepción. 
                //Por peticion realizada en el bug#7441 no se realizara objecion de tiempo cumplido para el turno
                if (fechaFin != null && fechaFin.getTime() < fechaLimite.getTime()) {
                    if (!tieneNotasInformativas && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                        throw new ValidacionParametrosException("El turno asociado no es válido. Se ha cumplido el periodo de vencimiento, para poder ser ingresado debe ingresar una nota Informativa");
                    }

                }
            }

            //CASO TURNOS DE CERTIFICADOS.
            if (tAnt.getSolicitud() instanceof SolicitudCertificado) {
                SolicitudCertificado solicitudCer = (SolicitudCertificado) tAnt.getSolicitud();
                Date fechaFin = tAnt.getFechaFin();
                long meses1 = (long) 1000 * 60 * 60 * 24 * 30; // 1 mes
                Date fechaLimite = new Date(new Date().getTime() - meses1);

                long proceso = 0;

                //6. Si el turno anterior no tiene fecha de finalización, se genera una excepción. 
                if (fechaFin == null && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. No tiene fecha de finalización.");
                }

                //7. Si han pasado 4 meses luego de la finalización del turno se genera una excepción. 
                if (fechaFin != null && fechaFin.getTime() < fechaLimite.getTime()) {
                    if (!tieneNotasInformativas && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                        throw new ValidacionParametrosException("El turno asociado no es válido. Se ha cumplido el periodo de vencimiento, para poder ser ingresado debe ingresar una nota Informativa");
                    }
                }

                //8 Si ya se hizo devolución de un turno de registro del cual este certificado es asociado
                if (solicitudCer.getSolicitudesPadres() != null && solicitudCer.getSolicitudesPadres().size() > 0) {
                    Turno tPadre = ((SolicitudAsociada) solicitudCer.getSolicitudesPadres().get(0)).getSolicitudPadre().getTurno();
                    proceso = tPadre.getIdProceso();
                    List turnosSig = hermod.getTurnosSiguientesDevolucion(tPadre.getIdWorkflow());
                    if (turnosSig != null && turnosSig.size() > 0) {
                        Turno turnoAnterior = (Turno) turnosSig.get(turnosSig.size() - 1);

                        if (turnoAnterior != null) {
                            TurnoPk idTurno = new TurnoPk();
                            idTurno.anio = turnoAnterior.getAnio();
                            idTurno.idCirculo = turnoAnterior.getIdCirculo();
                            idTurno.idProceso = turnoAnterior.getIdProceso();
                            idTurno.idTurno = turnoAnterior.getIdTurno();
                            turnoAnterior = hermod.getTurno(idTurno);
                            if (turnoAnterior.getSolicitud() != null) {
                                Solicitud solicitud = turnoAnterior.getSolicitud();
                                if (solicitud instanceof SolicitudDevolucion) {
                                    if (turnoAnterior.getAnulado().equals("N") && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                                        throw new TurnoAnteriorInvalidoException("El turno " + tAnt.getIdWorkflow() + " Esta asociado al turno de Regsitro " + tPadre.getIdWorkflow()
                                                + " que tiene devolución de dinero registrado en el turno " + turnoAnterior.getIdWorkflow() + ".");
                                    }
                                } else if (solicitud instanceof SolicitudRegistro) {
                                    if (turnoAnterior.getAnulado().equals("N") && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                                        throw new TurnoAnteriorInvalidoException("El turno " + tAnt.getIdWorkflow() + " Esta asociado al turno de Regsitro " + tPadre.getIdWorkflow()
                                                + " que ya tiene nuevas entradas en el turno " + turnoAnterior.getIdWorkflow() + ".");
                                    }
                                }
                            }
                        }
                        String turnosSiguiente = "";
                        Iterator it = turnosSig.iterator();
                        while (it.hasNext()) {
                            Turno turnoAnteriorTemp = (Turno) it.next();

                            TurnoPk idTurnoTemp = new TurnoPk();
                            idTurnoTemp.anio = turnoAnteriorTemp.getAnio();
                            idTurnoTemp.idCirculo = turnoAnteriorTemp.getIdCirculo();
                            idTurnoTemp.idProceso = turnoAnteriorTemp.getIdProceso();
                            idTurnoTemp.idTurno = turnoAnteriorTemp.getIdTurno();
                            turnoAnteriorTemp = hermod.getTurno(idTurnoTemp);

                            if (turnoAnteriorTemp.getSolicitud().getTurnoAnterior() != null && turnoAnteriorTemp.getSolicitud().getTurnoAnterior().getIdWorkflow() != null && turnoAnteriorTemp.getSolicitud().getTurnoAnterior().getIdWorkflow().equals(tAnt.getIdWorkflow())) {
                                turnosSiguiente = turnoAnteriorTemp.getIdWorkflow();
                                turnosSiguiente = " en el turno " + turnosSiguiente;
                                break;
                            }

                        }
                        if (turnoAnterior.getAnulado().equals("N") && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                            throw new TurnoAnteriorInvalidoException("El turno: " + tAnt.getIdWorkflow() + " Esta asociado al turno de Regsitro " + tPadre.getIdWorkflow()
                                    + " el cual fue utilizado como turno anterior" + turnosSiguiente + ".");
                        }
                    }
                }

                TurnoHistoria ultimoHistorial = null;
                if (tAnt.getHistorials().size() > 0) {
                    ultimoHistorial = (TurnoHistoria) tAnt.getHistorials().get(tAnt.getHistorials().size() - 1);
                }

                if (ultimoHistorial != null && !ultimoHistorial.getFase().equals(CFase.CER_TMAN_CIERRE)) {
                    //8. Se valida que el turno haya sido devuelto (Que tenga una nota devolutiva)
                    List listaNotas = tAnt.getNotas();

                    if (listaNotas != null) {

                        int contadorDevolutivas = 0;
                        for (int i = 0; i < listaNotas.size(); i++) {
                            Nota nota = (Nota) listaNotas.get(i);
                            TipoNota tipoNota = nota.getTiponota();
                            if (tipoNota != null) {
                                if (tipoNota.isDevolutiva()) {
                                    contadorDevolutivas++;
                                }
                            }

                        }
                        /**
                         * La devolucion se puede realizar siempre y cuando haya
                         * notas devolutivas o el certificado no este expedido
                         */
                        if (contadorDevolutivas == 0 && proceso != Long.parseLong(CProceso.PROCESO_REGISTRO) && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                            throw new ValidacionParametrosException("El turno asociado no es válido. Para turnos de certificados es necesario que "
                                    + "hayan sido devueltos al público y debe contener notas devolutivas.");
                            /*if(solicitudCer.getNumImpresiones()>0){
							throw new ValidacionParametrosException("El turno asociado no es válido. Para turnos de certificados es necesario que " +
									"hayan sido devueltos al público.  Debe contener notas devolutivas o que el certificado no esté expedido.");
						}*/
                        }

                    } else {
                        /**
                         * La devolucion se puede realizar siempre y cuando haya
                         * notas devolutivas o el certificado no este expedido
                         */
                        if (proceso != Long.parseLong(CProceso.PROCESO_REGISTRO) && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                            throw new ValidacionParametrosException("El turno asociado no es válido. Para turnos de certificados es necesario que "
                                    + "hayan sido devueltos al público y debe contener notas devolutivas.");
                        }
                        /*if(solicitudCer.getNumImpresiones()>0){
						throw new ValidacionParametrosException("El turno asociado no es válido. Para turnos de certificados es necesario que " +
							"hayan sido devueltos al público.  Debe contener notas devolutivas o que el certificado no esté expedido.");
					}*/
                    }
                }
            }

            //CASO TURNO DE CONSULTA
            if (tAnt.getSolicitud() instanceof SolicitudConsulta) {
                List aPagos = ((Liquidacion) tAnt.getSolicitud().getLiquidaciones().get(0)).getPago().getAplicacionPagos();
                Iterator iter = aPagos.iterator();
                int efectivo = 0;
                int sinSaldoAFavor = 0;
                while (iter.hasNext()) {
                    DocumentoPago docPago = ((AplicacionPago) iter.next()).getDocumentoPago();
                    if (docPago instanceof DocPagoCheque) {
                        if (((DocPagoCheque) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else if (docPago instanceof DocPagoConsignacion) {
                        if (((DocPagoConsignacion) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else {
                        efectivo++;
                    }
                }
                if (efectivo > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de consultas con pagos que no sean en efectivo");
                }
                if (sinSaldoAFavor > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de consultas con pagos que poseen saldo a favor");
                }
            }

            //CASO TURNO DE FOTOCOPIA
            if (tAnt.getSolicitud() instanceof SolicitudFotocopia) {
                if (tAnt.getSolicitud().getLiquidaciones() == null || tAnt.getSolicitud().getLiquidaciones().size() == 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. No existen liquidaciones ni pagos registrados para este turno");
                }
                List aPagos = ((Liquidacion) tAnt.getSolicitud().getLiquidaciones().get(0)).getPago().getAplicacionPagos();
                Iterator iter = aPagos.iterator();
                int efectivo = 0;
                int sinSaldoAFavor = 0;
                while (iter.hasNext()) {
                    DocumentoPago docPago = ((AplicacionPago) iter.next()).getDocumentoPago();
                    if (docPago instanceof DocPagoCheque) {
                        if (((DocPagoCheque) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else if (docPago instanceof DocPagoConsignacion) {
                        if (((DocPagoConsignacion) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else {
                        efectivo++;
                    }
                }
                if (efectivo > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de fotocopias con pagos que no sean en efectivo");
                }
                if (sinSaldoAFavor > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de fotocopias con pagos que poseen saldo a favor");
                }
            }
            /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
            //CASO TURNO DE CERTIFICADOS MASIVOS
            if (tAnt.getSolicitud() instanceof SolicitudCertificadoMasivo) {
                if (tAnt.getSolicitud().getLiquidaciones() == null || tAnt.getSolicitud().getLiquidaciones().size() == 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. No existen liquidaciones ni pagos registrados para este turno");
                }
                List aPagos = ((Liquidacion) tAnt.getSolicitud().getLiquidaciones().get(0)).getPago().getAplicacionPagos();
                Iterator iter = aPagos.iterator();
                int efectivo = 0;
                int sinSaldoAFavor = 0;
                while (iter.hasNext()) {
                    DocumentoPago docPago = ((AplicacionPago) iter.next()).getDocumentoPago();
                    if (docPago instanceof DocPagoCheque) {
                        if (((DocPagoCheque) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else if (docPago instanceof DocPagoConsignacion) {
                        if (((DocPagoConsignacion) docPago).getSaldoDocumento() <= 0) {
                            sinSaldoAFavor++;
                        }
                    } else {
                        efectivo++;
                    }
                }
                if (efectivo > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de certificados masivos con pagos que no sean en efectivo");
                }
                if (sinSaldoAFavor > 0 && !evento.getTipoEvento().equals(EvnDevolucion.AGREGAR_TURNO_ANT)) {
                    throw new ValidacionParametrosException("El turno asociado no es válido. Solo se permiten devolver turnos de certificados masivos con pagos que poseen saldo a favor");
                }
            }
        } catch (HermodException e) {
            throw new TurnoAnteriorInvalidoException("El turno anterior ingresado es inválido", e);
        } catch (Throwable e) {
            throw new TurnoAnteriorInvalidoException("Imposible validar el turno", e);
        }
        Solicitud sol = tAnt.getSolicitud();
        List listaCertificadosAsociados = new ArrayList();
        if (sol != null) {
            List asociadas = sol.getSolicitudesHijas();
            if (asociadas != null) {
                for (int i = 0; i < asociadas.size(); i++) {
                    SolicitudAsociada solAsoc = (SolicitudAsociada) asociadas.get(i);
                    if (solAsoc != null) {
                        Solicitud solHija = solAsoc.getSolicitudHija();
                        if (solHija != null) {
                            Turno turnoAsoc = solHija.getTurno();
                            if (turnoAsoc != null) {
                                String idTurno = turnoAsoc.getIdWorkflow();
                                try {
                                    List turnosSig = hermod.getTurnosSiguientesDevolucion(idTurno);
                                    if (turnosSig == null || turnosSig.size() == 0) {
                                        listaCertificadosAsociados.add(idTurno);
                                    }
                                } catch (Throwable e) {
                                    throw new ValidacionParametrosException("El turno asociado no es válido. " + e.getMessage());
                                }
                            }

                        }

                    }

                }
            }
        }
        return listaCertificadosAsociados;
    }

    /**
     * Realiza la validación de información de la consignacion o cheque para una
     * solicitud de devolución de dineros.
     *
     * @param evento EvnDevolucion
     */
    private DocumentoPago validarConsignacionCheque(DocumentoPago pago) throws EventoException {

        DocumentoPago docPago = null;
        try {
            docPago = hermod.getDocumentosPagoExistente(pago);
            if (docPago != null) {
                //consultar el turno
                throw new ValidacionParametrosException("La consignacion o cheque esta asociada a un turno");
            }
            Banco banco = null;
            String idBanco = null;
            if (pago instanceof DocPagoConsignacion) {
                idBanco = ((DocPagoConsignacion) pago).getBanco().getIdBanco();
                banco = hermod.getBancoByID(idBanco);
                ((DocPagoConsignacion) pago).setBanco(banco);
            } else {
                idBanco = ((DocPagoCheque) pago).getBanco().getIdBanco();
                banco = hermod.getBancoByID(idBanco);
                ((DocPagoCheque) pago).setBanco(banco);
            }
        } catch (HermodException e) {
            throw new TurnoAnteriorInvalidoException("La consignacion o cheque ingresado es inválido", e);
        } catch (Throwable e) {
            throw new TurnoAnteriorInvalidoException("Error de validacion: ", e);
        }
        return pago;
    }
    
    private EventoRespuesta avanzarNotaDevolutivaNotificada(EvnDevolucion evento) throws EventoException {
      
        Turno turno = evento.getTurno();
        
        try{
                Fase faseAvance = evento.getFase();
                gov.sir.core.negocio.modelo.Usuario usuarioSIRAvance = evento.getUsuarioSir();
                Hashtable tablaAvanzar = new Hashtable();
                tablaAvanzar.put(Processor.RESULT, evento.getRespuestaWF());
                try {
                    hermod.avanzarTurnoNuevoNormal(turno, faseAvance, tablaAvanzar, usuarioSIRAvance);
                } catch (Throwable e) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", e);
                }
            
        } catch (Throwable e1) {
             throw new TurnoNoAvanzadoException("No es posible avanzar el turno");
        }
        return null;
    }
    
    private EventoRespuesta notificarNotaDevolutiva(EvnDevolucion evento) throws EventoException {
        return avanzarNotaDevolutivaNotificada(evento);
    }
    
    private EventoRespuesta devolverCalificacion(EvnDevolucion evento) throws EventoException {
      
        Turno turno = evento.getTurno();
        
        try{
                Fase faseAvance = evento.getFase();
                gov.sir.core.negocio.modelo.Usuario usuarioSIRAvance = evento.getUsuarioSir();
                String estacion = hermod.getEstacionFromRecursosNota(turno);
                if(!hermod.isEstacionActivaCalificador(estacion)){
                    estacion = null;
                }
                Hashtable tablaAvanzar = new Hashtable();
                tablaAvanzar.put(Processor.RESULT, evento.getRespuestaWF());
                if(estacion != null && !estacion.isEmpty()){
                    tablaAvanzar.put(Processor.ESTACION, estacion);
                }
                try {
                    hermod.avanzarTurnoNuevoNormal(turno, faseAvance, tablaAvanzar, usuarioSIRAvance);
                } catch (Throwable e) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", e);
                }
            
        } catch (Throwable e1) {
             throw new TurnoNoAvanzadoException("No es posible avanzar el turno");
        }
        return null;
    }
    
    

    public EventoRespuesta procesar_ResolucionAceptar(EvnDevolucion evento) throws EventoException, DAOException, DAOException {
        Turno turno = evento.getTurno();
        String idWork = evento.getTurno().getIdWorkflow();
        Solicitud solicitud = null;
        // si la solicitud fue aceptada se va por confirmar
        // si no , por negar
        /**
         * yeferson
         */
        String turnoo = "";
        if (turno.getSolicitud().getTurnoAnterior() == null) {
            turnoo = idWork;
        } else {
            turnoo = turno.getSolicitud().getTurnoAnterior().getIdWorkflow();
        }
        double valorDevConservaDoc = evento.getValorDevolucionConservaDoc();

        //fin
        try {
            TurnoPk oid = new TurnoPk();
            oid.anio = turno.getAnio();
            oid.idCirculo = turno.getIdCirculo();
            oid.idProceso = turno.getIdProceso();
            oid.idTurno = turno.getIdTurno();
            List resoluciones = hermod.getOficiosTurno(oid);

            if (resoluciones == null || resoluciones.size() == 0) {
                throw new ValidacionValorDevolverException("No se puede avanzar el turno debido a que no existen resoluciones");
            }

        } catch (HermodException h) {
            throw new ValidacionValorDevolverException(h.getMessage());
        } catch (Throwable t) {
            throw new ValidacionValorDevolverException(t.getMessage());
        }

        if (solicitudAceptada(evento)) {
            //SE NECESITA VALIDAR QUE NO SE QUIERA INGRESAR UN VALOR SUPERIOR AL QUE EL CIUDADANO HA PAGADO.

            solicitud = turno.getSolicitud();
            Turno turnoAnterior = solicitud.getTurnoAnterior();
            double totalPagado = 0;
            double totalSaldoFavor = 0;
            boolean isConsultaFotocopia = false;
            /**
             * yeferson
             */
            JDOTurnosDAO jdo = new JDOTurnosDAO();
            double totalConservacionDocPago = jdo.SumaConservacion(turnoo);
            if (turnoAnterior == null) {
                List consignaciones = ((SolicitudDevolucion) solicitud).getConsignaciones();
                if (consignaciones != null && consignaciones.size() > 0) {
                    Iterator iter = consignaciones.iterator();
                    while (iter.hasNext()) {
                        DocumentoPago docPago = ((Consignacion) iter.next()).getDocPago();
                        totalPagado += docPago.getValorDocumento();
                    }
                }
            } else {
                while (turnoAnterior != null) {
                    Solicitud solicitudAnterior = turnoAnterior.getSolicitud();

                    List liquidaciones = solicitudAnterior.getLiquidaciones();

                    if (liquidaciones != null && liquidaciones.size() > 0) {
                        Iterator itLiquidaciones = liquidaciones.iterator();
                        while (itLiquidaciones.hasNext()) {
                            Liquidacion liq = (Liquidacion) itLiquidaciones.next();
                            if (liq.getPago() != null) {
                                totalPagado = totalPagado + (liq.getValor() > 0 ? liq.getValor() : 0);
                            }
                            if (liq.getPago() != null && liq.getPago().getAplicacionPagos() != null) {
                                List aPagos = liq.getPago().getAplicacionPagos();
                                for (int j = 0; j < aPagos.size(); j++) {
                                    if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                        DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                        if (chPago.getSaldoDocumento() > 0) {
                                            totalSaldoFavor += chPago.getSaldoDocumento();
                                            totalPagado += chPago.getSaldoDocumento();
                                        }
                                    } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                        DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                        if (chPago.getSaldoDocumento() > 0) {
                                            totalSaldoFavor += chPago.getSaldoDocumento();
                                            totalPagado += chPago.getSaldoDocumento();
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        throw new ValidacionValorDevolverException("No se ha podido encontrar las liquidaciones anteriores.");
                    }
                    if (solicitudAnterior instanceof SolicitudRegistro) {
                        List solAsociadas = solicitudAnterior.getSolicitudesHijas();
                        Iterator iter = solAsociadas.iterator();
                        while (iter.hasNext()) {
                            SolicitudAsociada solAsociada = (SolicitudAsociada) iter.next();
                            Liquidacion liqCert = (Liquidacion) solAsociada.getSolicitudHija().getLiquidaciones().get(0);
                            totalPagado = totalPagado + (liqCert.getValor() > 0 ? liqCert.getValor() : 0);
                        }
                    }

                    turnoAnterior = solicitudAnterior.getTurnoAnterior();
                }
            }

            Liquidacion valorDevolver = evento.getLiquidacion();

            isConsultaFotocopia = (valorDevolver instanceof LiquidacionTurnoConsulta) || (valorDevolver instanceof LiquidacionTurnoFotocopia);

            double absValorDevolver = 0;
            if (evento.getLiquidacionMayorValor() != null) {
                absValorDevolver = Math.abs(valorDevolver.getValor()) + Math.abs(evento.getLiquidacionMayorValor().getValor()) + Math.abs(evento.getValorSaldoFavor());
            } else if (isConsultaFotocopia) {
                absValorDevolver = Math.abs(evento.getValorSaldoFavor());
            }/*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/ else if (valorDevolver instanceof LiquidacionTurnoCertificadoMasivo) {
                absValorDevolver = Math.abs(evento.getValorSaldoFavor());
            } else {
                absValorDevolver = Math.abs(valorDevolver.getValor()) + Math.abs(evento.getValorSaldoFavor());
            }


            if (!isConsultaFotocopia && (absValorDevolver > totalPagado || evento.getValorSaldoFavor() > totalPagado)) {
                throw new ValidacionValorDevolverException("No se puede devolver un valor superior al pagado.");

            } else if (isConsultaFotocopia && absValorDevolver > totalSaldoFavor) {
                throw new ValidacionValorDevolverException("No se puede devolver un valor superior al pagado.");
            }

            LiquidacionTurnoDevolucion liquidacionDevolucion
                    = (LiquidacionTurnoDevolucion) solicitud.getLiquidaciones().get(solicitud.getLiquidaciones().size() - 1);

            if (Math.abs(liquidacionDevolucion.getValor()) != absValorDevolver) {
                ((TurnoHistoria) evento.getTurno().getHistorials().get(evento.getTurno().getHistorials().size() - 1)).setAccion(CDevoluciones.MODIFICACION_VALOR_DEVOLVER
                        + (long) absValorDevolver);
            }

            if (solicitud.getTurnoAnterior() != null) {
                if (isConsultaFotocopia) {
                    liquidacionDevolucion.setValor(evento.getValorSaldoFavor());

                } else {
                    liquidacionDevolucion.setValor(valorDevolver.getValor() + evento.getValorSaldoFavor());
                }
                if (valorDevolver instanceof LiquidacionTurnoRegistro) {
                    liquidacionDevolucion.setValorDerechos(((LiquidacionTurnoRegistro) valorDevolver).getValorDerechos());
                    liquidacionDevolucion.setValorConservacionDoc(((LiquidacionTurnoRegistro) valorDevolver).getValorConservacionDoc());
                    liquidacionDevolucion.setValorImpuestos(((LiquidacionTurnoRegistro) valorDevolver).getValorImpuestos());
                    if (evento.getLiquidacionMayorValor() != null) {
                        liquidacionDevolucion.setValorDerechosMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValorDerechos());
                        liquidacionDevolucion.setValorImpuestosMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValorImpuestos());
                        liquidacionDevolucion.setValorMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValor());
                        liquidacionDevolucion.setValor(valorDevolver.getValor() + liquidacionDevolucion.getValorMayorValor() + evento.getValorSaldoFavor());
                    }
                } /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/ else if (valorDevolver instanceof LiquidacionTurnoCertificadoMasivo) {
                    liquidacionDevolucion.setValor(evento.getValorSaldoFavor());
                }
                liquidacionDevolucion.setValorSaldoFavor(evento.getValorSaldoFavor());
                actualizarValorLiquidacionDevolucion(liquidacionDevolucion);

            }

            procesar_ResolucionAceptar_CrearLiquidacionNegativa(evento, valorDevConservaDoc);

            if (turno.getIdFase().equals(CFase.DEV_RESOLUCION)) {
                evento.setRespuestaWF(EvnDevolucion.CONFIRMAR);
            }
        } else {
            if (turno.getIdFase().equals(CFase.DEV_RESOLUCION)) {
                evento.setRespuestaWF(EvnDevolucion.CONFIRMAR);
            }

        } // :if

        // se crea el oficio
        //procesar_ResolucionAceptar_CrearOficio(evento);
        /*if( true ){
		   throw new EventoException("debug app purposes");
		}*/
        // generar el avance
        EventoRespuesta result;
        result = avanzarTurno(evento);
        return result;

    } // :resolucion_Aceptar

    private void procesar_ResolucionAceptar_CrearLiquidacionNegativa(EvnDevolucion evento, double conservacion) throws ServicioNoEncontradoException, EventoException, DAOException {

        Liquidacion local_Liquidacion = evento.getLiquidacion();
        LiquidacionTurnoRegistro lReg = null;
        /*
        yeferson
         */
 //       String turnoAnterior = evento.getTurno().getSolicitud().getTurnoAnterior().getIdWorkflow();
        String turnoAnterior = "";
        if (evento.getTurno().getSolicitud().getTurnoAnterior() == null) {
            turnoAnterior = evento.getTurno().getIdWorkflow();
        } else {
            turnoAnterior = evento.getTurno().getSolicitud().getTurnoAnterior().getIdWorkflow();
        }
        String idSolicitud = evento.getTurno().getSolicitud().getIdSolicitud();
        double valorBase = evento.getValorSaldoFavor();
        double valorconserva = conservacion;
        String turnoDevolucion = evento.getTurno().getIdWorkflow();
        JDOLiquidacionesDAO jdol = new JDOLiquidacionesDAO();
        ///
        if (evento.getLiquidacionMayorValor() != null) {
            lReg = (LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor();
            ((LiquidacionTurnoRegistro) local_Liquidacion).setValorDerechos(((LiquidacionTurnoRegistro) local_Liquidacion).getValorDerechos() + lReg.getValorDerechos());
            ((LiquidacionTurnoRegistro) local_Liquidacion).setValorImpuestos(((LiquidacionTurnoRegistro) local_Liquidacion).getValorImpuestos() + lReg.getValorImpuestos());
            ((LiquidacionTurnoRegistro) local_Liquidacion).setValor(((LiquidacionTurnoRegistro) local_Liquidacion).getValor() + lReg.getValor() + evento.getValorSaldoFavor());

        }
        SolicitudDevolucion solicitudDevolucion = (SolicitudDevolucion) evento.getTurno().getSolicitud();
        Solicitud local_SolicitudAnterior = null;
        if (solicitudDevolucion.getConsignaciones().size() == 0) {
            local_SolicitudAnterior = evento.getTurno().getSolicitud().getTurnoAnterior().getSolicitud();
        }

        try {
            if (local_SolicitudAnterior != null) {
                int size = local_SolicitudAnterior.getLiquidaciones().size();
                Liquidacion liq = (Liquidacion) local_SolicitudAnterior.getLiquidaciones().get(size - 1);
                if (liq.getValor() >= 0) {
                    hermod.addLiquidacionToSolicitud(local_SolicitudAnterior, local_Liquidacion);
                    jdol.insertDevolucionConservaDoc(turnoDevolucion, turnoAnterior, idSolicitud, valorBase, valorconserva);
                }
            } else {
                if (solicitudDevolucion != null && (solicitudDevolucion.getLiquidaciones() == null || solicitudDevolucion.getLiquidaciones().size() <= 1)) {
                    hermod.addLiquidacionToSolicitud(solicitudDevolucion, local_Liquidacion);
                    jdol.insertDevolucionConservaDoc(turnoDevolucion, turnoAnterior, idSolicitud, valorBase, valorconserva);

                } else if (solicitudDevolucion != null && solicitudDevolucion.getLiquidaciones().size() > 1) {
                    local_Liquidacion.setIdLiquidacion(Integer.toString(solicitudDevolucion.getLiquidaciones().size()));
                    local_Liquidacion.setIdSolicitud(solicitudDevolucion.getIdSolicitud());
                    actualizarValorLiquidacionDevolucion(local_Liquidacion);
                    jdol.UpdateDevolucionConservaDoc(turnoDevolucion, valorBase, valorconserva);
                }
            }
        } catch (HermodException e) {
            throw new EventoException(e.getMessage());
        } catch (Throwable ex) {
            throw new EventoException(ex.getMessage());
        } // :try

    } // :procesar_ResolucionAceptar_CrearLiquidacionNegativa

    private void actualizarValorLiquidacionDevolucion(Liquidacion liquidacion) throws ServicioNoEncontradoException, EventoException {
        try {
            hermod.updateValorLiquidacionDevolucion(liquidacion);
        } catch (HermodException e) {
            throw new EventoException(e.getMessage());
        } catch (Throwable ex) {
            throw new EventoException(ex.getMessage());
        }
    }

    /**
     * notificarAceptar
     *
     * @param evento EvnDevolucion
     */
    private void efectuarPago(EvnDevolucion evento) throws EventoException {

        String tipoPago = "";
        Pago pago = new Pago();

        PagoDevolucionException pde = new PagoDevolucionException();

        //Determinar si el pago es en efectivo o cheque.
        tipoPago = evento.getFormaPago();
        Estacion estacionPago = evento.getEstacion();

        Turno tAnt = evento.getTurno().getSolicitud().getTurnoAnterior();
        if (tAnt != null) {
            try {
                tAnt = hermod.getTurnobyWF(tAnt.getIdWorkflow());
                if (tAnt == null) {
                    pde.addError("No se pudo obtener la informacion del turno");
                    throw pde;
                }
            } catch (HermodException e) {
                pde.addError("Hermod: El turno asociado no es válido" + e.getMessage());
                throw pde;
            } catch (Throwable e) {
                //ExceptionPrinter printer = new ExceptionPrinter(e);
                pde.addError("Hermod: Error al consultar el turno." + e.getMessage());
                throw pde;
            }
        }
        Solicitud solAnt = null;
        if (tAnt != null) {
            solAnt = tAnt.getSolicitud();
        }

        // Busca la primera liquidacion negativa sin pago.
        // compara los valores ingresados en la forma
        Liquidacion negativa = null;

        negativa = obtenerLiquidacionNegativa(evento.getTurno());

        double valorPago = 0;
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
        if (tipoPago.equals(CAplicacionPago.CHEQUE)) {
            valorPago = evento.getChequeValor();
        } else if (tipoPago.equals(CAplicacionPago.NOTA_DEBITO)) {
            valorPago = evento.getNotaDebitoValor();
        } else if (tipoPago.equals(CAplicacionPago.TRANSFERENCIA)) {
            valorPago = evento.getTransferenciaValor();
        } else {
            valorPago = evento.getEfectivoValor();
        }

        if (valorPago > 0) {
            valorPago = valorPago * (-1);
        }
        if (negativa != null && (valorPago > negativa.getValor())) {
            pde.addError("El valor del pago no puede ser mayor al valor a devolver");
            throw pde;
        }

        //}
        //TIPO DE PAGO EN CHEQUE:
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
        if (tipoPago.equals(CAplicacionPago.CHEQUE)) {
            Banco banco = null;
            try {
                banco = hermod.getBancoByID(evento.getChequeBanco());
            } catch (HermodException h) {
                throw new EventoException("No se Encontro el Banco");
            } catch (Throwable t) {
                ExceptionPrinter printer = new ExceptionPrinter(t);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(t.getMessage(), t);
            }

            DocPagoCheque cheque = new DocPagoCheque(banco, "", "", evento.getChequeNumero(), evento.getChequeValor());

            AplicacionPago ap = new AplicacionPago();
            ap.setDocumentoPago(cheque);
            ap.setValorAplicado(cheque.getValorDocumento());

            pago.addAplicacionPago(ap);
        } else if (tipoPago.equals(CAplicacionPago.NOTA_DEBITO)) {
            Banco banco = null;
            try {
                banco = hermod.getBancoByID(evento.getNotaDebitoBanco());
            } catch (HermodException h) {
                throw new EventoException("No se Encontro el Banco");
            } catch (Throwable t) {
                ExceptionPrinter printer = new ExceptionPrinter(t);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(t.getMessage(), t);
            }

            DocPagoNotaDebito notaDebito = new DocPagoNotaDebito(banco, "", "", evento.getNotaDebitoNumero(), evento.getNotaDebitoValor());
            notaDebito.setTipoPago(tipoPago);
            AplicacionPago ap = new AplicacionPago();
            ap.setDocumentoPago(notaDebito);
            ap.setValorAplicado(notaDebito.getValorDocumento());

            pago.addAplicacionPago(ap);
        } else if (tipoPago.equals("ABONO A CUENTA")) {//TIPO DE PAGO EN TRANSFERENCIA
            Banco banco = null;
            try {
                banco = hermod.getBancoByID(evento.getTransferenciaBanco());
            } catch (HermodException h) {
                throw new EventoException("No se Encontro el Banco");
            } catch (Throwable t) {
                ExceptionPrinter printer = new ExceptionPrinter(t);
                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(t.getMessage(), t);
            }

//            DocPagoConsignacion cons = new DocPagoConsignacion(banco, "", "", evento.getTransferenciaNumero(), evento.getTransferenciaValor());
            DocPagoGeneral cons = new DocPagoGeneral(2, String.valueOf(evento.getFechaRecurso()), evento.getTransferenciaNumero(),
            null, null, evento.getTransferenciaValor(), null,
            evento.getTransferenciaNumero(), evento.getTransferenciaValor(), 0, "ABONO A CUENTA", 
            "", null,
            null, banco, 0.0);
            AplicacionPago ap = new AplicacionPago();
            ap.setDocumentoPago(cons);
            ap.setValorAplicado(cons.getValorDocumento());

            pago.addAplicacionPago(ap);
        } else if (tipoPago.equals("EFECTIVO")) {//TIPO DE PAGO EN EFECTIVO:
            DocPagoEfectivo efectivo = new DocPagoEfectivo(evento.getEfectivoValor());

            AplicacionPago appEfectivo = new AplicacionPago();
            appEfectivo.setValorAplicado(evento.getEfectivoValor());
            appEfectivo.setDocumentoPago(efectivo);

            pago.addAplicacionPago(appEfectivo);
        }

        ///////////////////////////////
        /*
		if (solAnt instanceof SolicitudCertificado) {
		LiquidacionTurnoCertificado liq = new LiquidacionTurnoCertificado();
		if (tipoPago.equals("CHEQUE"))
		{
		liq.setValor(-evento.getChequeValor());
		}
		if (tipoPago.equals("EFECTIVO"))
		{
		liq.setValor(-evento.getEfectivoValor());
		}
		
		liq.setUsuario(evento.getUsuarioNec());
		
		try {
		hermod.addLiquidacionToSolicitud(solAnt, liq);
		} catch (HermodException e) {
		pde.addError("No se pudo agregar la liquidación negativa");
		throw pde;
		} catch (Throwable e) {
		ExceptionPrinter printer = new ExceptionPrinter(e);
		pde.addError("Se ha presentado una excepción an agregar la liquidación a la solicitud");
		throw pde;
		}
		}
         */
        ///////////////////////////////
        //Liquidacion negativa = obtenerLiquidacionNegativa(tAnt);
        if (negativa != null) {
            negativa.setSolicitud(evento.getTurno().getSolicitud());
            pago.setLiquidacion(negativa);
        }
        pago.setUsuario(evento.getUsuarioNec());

        //Determinar el número del recibo:
        try {
            Turno turnoAux = evento.getTurno();
            if (turnoAux != null) {
                EstacionReciboPk estacionReciboID = new EstacionReciboPk();
                estacionReciboID.idEstacion = estacionPago.getEstacionId();
                Usuario user = evento.getUsuarioNec();
                long numReciboPago = 0;
                if (tipoPago.equals("EFECTIVO")) {
                    numReciboPago = hermod.getNextNumeroRecibo(estacionReciboID, user, 0);
                } else {
                    numReciboPago = hermod.getNextNumeroRecibo(estacionReciboID, user, turnoAux.getIdProceso());
                }
                //long numReciboPago = hermod.getSecuencialDevolucion(turnoAux.getIdCirculo(),turnoAux.getAnio());
                //Asociar el consecutivo de la Devolución al pago
                String secString = numReciboPago + "";
                pago.setNumRecibo(secString);
                pago.setLastNumRecibo(secString);
            } else {
                throw new EventoException("Error obteniendo secuencial de la constancia. El turno es nulo");
            }
        } catch (Throwable t) {
            throw new EventoException("Error obteniendo secuencial de la constancia de devolución", t);
        }
        //////////////////////////////
        Rol rol = evento.getRol();
        Estacion estacion = null;
        try {
            estacion = fenrir.getEstacion(evento.getUsuarioNec(), rol);
        } catch (Throwable e1) {
            throw new EventoException(e1.getMessage(), e1);
        }
        pago.setConcepto("ORDEN DE PAGO No.: " + evento.getNumeroOrden() + " DEL "
                + FechaConFormato.formatear(evento.getFechaOrden(), "dd/MM/yyyy"));
        ///////////////////////////////
        try {
            Pago pago2 = hermod.registrarPago(pago, estacion.getEstacionId());
        } catch (HermodException e) {
            pde.addError("No se ha podido persistir el pago");
            throw pde;
        } catch (Throwable e) {
            //ExceptionPrinter printer = new ExceptionPrinter(e);
            pde.addError("No se ha podido registrar el pago");
            throw pde;
        }
        ///////////////////////////////
        Oficio of = obtenerUltimaResolucion(evento);
        /*if(tAnt!=null)
			pago = obtenerPagoCompleto(tAnt, pago);*/
        //TODO // El pago que devuelve Hermod no tiene las aplicaciones de pago
        // Garantizar que se ejecute la adición de la liquidación y el registro
        // del pago de manera transaccional
        String fechaImpresion = this.getFechaImpresion();

        DocumentoPago docPago;
        List aplicaciones = pago.getAplicacionPagos();
        if (aplicaciones != null) {
            Iterator it = aplicaciones.iterator();
            Banco banco = null;
            while (it.hasNext()) {
                AplicacionPago ap = (AplicacionPago) it.next();
                docPago = ap.getDocumentoPago();
                if (docPago instanceof DocPagoGeneral) {
                    try {
                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
                        if(circuloTipoPago != null){
                            ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
                            ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
                        }
                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
                            ((DocPagoGeneral) docPago).setBanco(banco);
                        } else {
                            ((DocPagoGeneral) docPago).setBanco(banco);
                        }

                    } catch (Throwable ex) {
                        Log.getInstance().debug(ANDevolucion.class, "ERROR" + ex.getMessage());
                    }
                }
            }

        }

        ImprimibleDevolucion impDev = new ImprimibleDevolucion(of, evento.getTurno(), pago, fechaImpresion, CTipoImprimible.DEVOLUCION);
        impDev.setFechaOficio(FechaConFormato.formatear(of.getFechaCreacion(), "dd/MM/yyyy"));
        imprimir(impDev, evento.getUID());
    }

    /**
     * @param turno
     * @param pago
     * @return
     */
    private Pago obtenerPagoCompleto(Turno turno, Pago pago) throws EventoException {
        try {
            turno = hermod.getTurnobyWF(turno.getIdWorkflow());
            if (turno == null) {
                throw new EventoException("No se pudo obtener la informacion del turno");
            }
        } catch (HermodException e) {
            throw new EventoException("Hermod: El turno no es válido" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        List liqs = turno.getSolicitud().getLiquidaciones();
        ListIterator iter = liqs.listIterator();

        Pago rtn = null;
        while (iter.hasNext()) {
            Liquidacion liq = (Liquidacion) iter.next();
            if (pago.getIdLiquidacion().equals(liq.getIdLiquidacion())) {
                rtn = liq.getPago();
                break;
            }
        }

        return rtn;
    }

    /**
     * @param solAnt
     * @return
     */
    private Liquidacion obtenerLiquidacionNegativa(Turno turno) throws EventoException {
        try {
            turno = hermod.getTurnobyWF(turno.getIdWorkflow());
            if (turno == null) {
                throw new EventoException("No se pudo obtener la informacion del turno");
            }
        } catch (HermodException e) {
            throw new EventoException("Hermod: El turno no es válido" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        List liqs = turno.getSolicitud().getLiquidaciones();

        Liquidacion negativa = (LiquidacionTurnoDevolucion) liqs.get(liqs.size() - 1);

        return negativa;
    }

    /**
     * obtenerUltimaResolucion
     *
     * @param evento EvnDevolucion
     * @return Oficio
     */
    private Oficio obtenerUltimaResolucion(EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();
        TurnoPk id = new TurnoPk(turno.getIdWorkflow());

        List oficios = null;

        try {
            oficios = hermod.getOficiosTurno(id);
        } catch (HermodException e) {
            throw new EventoException("No se pudo encontrar los oficios del turno" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        ListIterator iter = oficios.listIterator();

        //long max = 0;
        Date fecha_max = new Date();
        Oficio selected = null;
        boolean primero = true;
        while (iter.hasNext()) {
            Oficio of = (Oficio) iter.next();
            if (primero) {
                primero = false;
                fecha_max = of.getFechaCreacion();
                selected = of;
            }
            if (of.getFechaCreacion().after(fecha_max)) {
                fecha_max = of.getFechaCreacion();
                selected = of;
            }
        }

        if (selected == null) {
            throw new EventoException("No existe resolución");
        }

        return selected;
    }

    private TurnoHistoria obtenerTurnoHistoriaActual(EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();

        List tHistorias = turno.getHistorials();
        ListIterator iter = tHistorias.listIterator();

        String faseActual = turno.getIdFase();

        TurnoHistoria activo = null;
        while (iter.hasNext()) {
            TurnoHistoria tH = (TurnoHistoria) iter.next();
            if (tH.isActivo() && tH.getFase().equals(faseActual)) {
                activo = tH;
                break;
            }
        }
        if (activo == null) {
            throw new EventoException("El turno historia actual es nulo");
        }

        return activo;
    }

    private void actualizarAceptacionSolicitud(EvnDevolucion evento, boolean flag) throws EventoException {

        String idStr = evento.getTurno().getSolicitud().getIdSolicitud();
        SolicitudPk id = new SolicitudPk(idStr);

        try {
            hermod.setAprobacionSolicitud(id, flag);
        } catch (HermodException e) {
            throw new EventoException("No se pudo actualizar la solicitud" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }

    /**
     * solicitudAceptada
     *
     * @param evento EvnDevolucion
     * @return boolean
     */
    private boolean solicitudAceptada(EvnDevolucion evento) {
        SolicitudDevolucion sol = (SolicitudDevolucion) evento.getTurno().getSolicitud();
        return sol.isAprobada();
    }

    /**
     * analisisNegar
     *
     * @param evento EvnDevolucion
     * @return EventoRespuesta
     */
    private void analisisNegar(EvnDevolucion evento) throws RuntimeException, EventoException {
        actualizarAceptacionSolicitud(evento, false);
    }

    /**
     * analisisAceptar
     *
     * @param evento EvnDevolucion
     * @return EventoRespuesta
     */
    private void analisisAceptar(EvnDevolucion evento) throws RuntimeException, EventoException {
        actualizarAceptacionSolicitud(evento, true);
    }

    /**
     *
     *
     * @param evento EvnDevolucion
     * @return EventoRespuesta
     */
    private void procesar_ResolucionAceptar_CrearOficio(EvnDevolucion evento) throws ImpresionNoEfectuadaException, ServicioNoEncontradoException, EventoException {
        //String resolucion = evento.getResolucion();
        List resoluciones = evento.getResoluciones();
        if (resoluciones != null) {
            for (int i = 0; i < resoluciones.size(); i++) {
                Oficio of = (Oficio) resoluciones.get(i);
                //of.setTextoOficio(resolucion);
                TurnoHistoria th = obtenerTurnoHistoriaActual(evento);
                of.setTurnoHistoria(th);
                of.setFirmado(false);

                //imprimir(new ImprimibleResolucion(of, evento.getTurno(),CTipoImprimible.RESOLUCION), evento.getUID());
                try {
                    hermod.crearOficio(of);
                } catch (HermodException e) {
                    throw new EventoException("No se pudo persistir la resolución" + e.getMessage(), e);
                } catch (Throwable e) {
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                    throw new EventoException(e.getMessage(), e);
                }
            }
        }
    }
    
    private EventoRespuesta consultarJuzgado(EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();
        String idWorkflow = turno.getIdWorkflow();
        String currentState = null;
        try{
         currentState = hermod.currentStateNotaNotificada(idWorkflow);
        } catch(HermodException e) {
            throw new EventoException("No ha sido posible consultar el estado actual de Nota Devolutiva Notificada");
        } catch (Throwable ex) {
            ExceptionPrinter printer = new ExceptionPrinter(ex);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(ex.getMessage(), ex);
        }
        EvnRespDevolucion evn = new EvnRespDevolucion();
        evn.setCurrentStateNotaNotificada(currentState);
        evn.setTipoEvento(EvnDevolucion.ESPERANDO_JUZGADO);
        return evn;
    }
    
    private EventoRespuesta agregarResolucion(EvnDevolucion evento) throws EventoException {
        List listaActualizada = null;
        Oficio of = (Oficio) evento.getOficioResolucion();
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());
        TurnoHistoria th = obtenerTurnoHistoriaActual(evento);
        of.setTurnoHistoria(th);
        of.setFirmado(false);

        //imprimir(new ImprimibleResolucion(of, evento.getTurno(),CTipoImprimible.RESOLUCION), evento.getUID());
        try {
            hermod.crearOficio(of);
            TurnoPk tid = new TurnoPk();
            tid.anio = evento.getTurno().getAnio();
            tid.idCirculo = evento.getTurno().getIdCirculo();
            tid.idProceso = evento.getTurno().getIdProceso();
            tid.idTurno = evento.getTurno().getIdTurno();
            listaActualizada = hermod.getOficiosTurno(tid);
        } catch (HermodException e) {
            throw new EventoException("No se pudo persistir la resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        respuesta.setResoluciones(listaActualizada);
        return respuesta;
    }
    
    private EventoRespuesta agregarResolucionRecursosNot(EvnDevolucion evento) throws EventoException {
        List listaActualizada = null;
        Oficio of = (Oficio) evento.getOficioResolucion();
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());
        TurnoHistoria th = obtenerTurnoHistoriaActual(evento);
        of.setTurnoHistoria(th);
        of.setFirmado(false);

        //imprimir(new ImprimibleResolucion(of, evento.getTurno(),CTipoImprimible.RESOLUCION), evento.getUID());
        try {
            hermod.crearOficio(of);
            TurnoPk tid = new TurnoPk();
            tid.anio = evento.getTurno().getAnio();
            tid.idCirculo = evento.getTurno().getIdCirculo();
            tid.idProceso = evento.getTurno().getIdProceso();
            tid.idTurno = evento.getTurno().getIdTurno();
            listaActualizada = hermod.getOficiosTurno(tid);
        } catch (HermodException e) {
            throw new EventoException("No se pudo persistir la resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        respuesta.setResoluciones(listaActualizada);
        return respuesta;
    }

    private EventoRespuesta eliminarResoluciones(EvnDevolucion evento) throws EventoException {
        List resoluciones = evento.getResoluciones();
        List listaActualizada = null;
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());

        //imprimir(new ImprimibleResolucion(of, evento.getTurno(),CTipoImprimible.RESOLUCION), evento.getUID());
        try {
            hermod.eliminarOficios(resoluciones);
            TurnoPk tid = new TurnoPk();
            tid.anio = evento.getTurno().getAnio();
            tid.idCirculo = evento.getTurno().getIdCirculo();
            tid.idProceso = evento.getTurno().getIdProceso();
            tid.idTurno = evento.getTurno().getIdTurno();
            listaActualizada = hermod.getOficiosTurno(tid);
        } catch (HermodException e) {
            throw new EventoException("No se pudo persistir la resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        respuesta.setResoluciones(listaActualizada);
        return respuesta;
    }
    
    private EventoRespuesta eliminarResolucionesNot(EvnDevolucion evento) throws EventoException {
        List resoluciones = evento.getResoluciones();
        List listaActualizada = null;
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());

        //imprimir(new ImprimibleResolucion(of, evento.getTurno(),CTipoImprimible.RESOLUCION), evento.getUID());
        try {
            hermod.eliminarOficios(resoluciones);
            TurnoPk tid = new TurnoPk();
            tid.anio = evento.getTurno().getAnio();
            tid.idCirculo = evento.getTurno().getIdCirculo();
            tid.idProceso = evento.getTurno().getIdProceso();
            tid.idTurno = evento.getTurno().getIdTurno();
            listaActualizada = hermod.getOficiosTurno(tid);
        } catch (HermodException e) {
            throw new EventoException("No se pudo persistir la resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        respuesta.setResoluciones(listaActualizada);
        return respuesta;
    }

    /**
     * Este metodo llama el servicio Hermod para poder hacer el avance del
     * turno.
     *
     * @param evento EvnDevolucion
     * @return EventoRespuesta
     * @throws EventoException
     */
    private EventoRespuesta avanzarTurno(EvnDevolucion evento) throws EventoException {
        Hashtable tabla = new Hashtable();

        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        boolean avanzado;
        
//        if (evento.getTipoEvento().equals(EvnDevolucion.NEGAR_INTERPOSICION_RECURSOS)) {
//            Solicitud solicitud = turno.getSolicitud();
////            Liquidacion liquidacion  = evento.getLiquidacion();
//            Solicitud solicitudAnterior = turno.getSolicitud().getTurnoAnterior().getSolicitud();
//            LiquidacionTurnoDevolucion liquidacionDevolucion = (LiquidacionTurnoDevolucion) solicitud.getLiquidaciones().get(solicitud.getLiquidaciones().size() - 1);
//            liquidacionDevolucion.setValor(0d);
//            liquidacionDevolucion.setValorDerechos(0d);
//            liquidacionDevolucion.setValorConservacionDoc(0d);
//            liquidacionDevolucion.setValorImpuestos(0d);
////            liquidacionDevolucion.setValorDerechosMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValorDerechos());
////            liquidacionDevolucion.setValorImpuestosMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValorImpuestos());
////            liquidacionDevolucion.setValorMayorValor(((LiquidacionTurnoRegistro) evento.getLiquidacionMayorValor()).getValor());
//            liquidacionDevolucion.setValorSaldoFavor(0d);
//            actualizarValorLiquidacionDevolucion(liquidacionDevolucion);
//            LiquidacionTurnoRegistro liquidacionDevolucionAnterior = (LiquidacionTurnoRegistro) solicitudAnterior.getLiquidaciones().get(solicitudAnterior.getLiquidaciones().size() - 1);
//            LiquidacionTurnoDevolucion liquidacionDevolucionAnteriorEnv = new LiquidacionTurnoDevolucion();            
//            liquidacionDevolucionAnteriorEnv.setIdLiquidacion("2");
//            liquidacionDevolucionAnteriorEnv.setIdSolicitud(liquidacionDevolucionAnterior.getIdSolicitud());
////            borrarLiquidacionDevolucion(liquidacionDevolucionAnteriorEnv);        
//            String idLiquidacion, idSolicitud = null;
//            idLiquidacion = "2";
//            idSolicitud = liquidacionDevolucionAnterior.getIdSolicitud();
//            try {
//                hermod.deleteLiquidacionTurnoRegistro(idLiquidacion,idSolicitud);
//            } catch (Throwable e) {
//                ExceptionPrinter printer = new ExceptionPrinter(e);
//                Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
//                throw new EventoException(e.getMessage(), e);
//            }        
//        }
        try {
            tabla.put(Processor.RESULT, evento.getRespuestaWF());
            tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNec().getUsername());
            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tabla, turno);
        } catch (Throwable t) {
            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
        }
        // se crea el oficio
        //procesar_ResolucionAceptar_CrearOficio(evento);
        try {
            //actualizar solicitud y consignaciones/cheques asociada al turno
            if (fase != null && fase.getID() != null && fase.getID().equals(CFase.DEV_ANALISIS)) {
                List consignaciones = null;
                if (turno.getSolicitud() instanceof SolicitudDevolucion) {
                    SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
                    if (solicitud != null && solicitud.getConsignaciones() != null) {
                        consignaciones = solicitud.getConsignaciones();
                    }
                }
                hermod.actualizaSolicitud(turno.getSolicitud(), consignaciones);
            }
            if (fase != null && fase.getID() != null && fase.getID().equals(CFase.DEV_RECURSOS)
                    && evento.getTipoEvento().equals(EvnDevolucion.ACEPTAR_INTERPOSICION_RECURSOS)) {

                TurnoPk oid = new TurnoPk();
                oid.anio = turno.getAnio();
                oid.idCirculo = turno.getIdCirculo();
                oid.idProceso = turno.getIdProceso();
                oid.idTurno = turno.getIdTurno();

                List oficios = hermod.getOficiosTurno(oid);
                Oficio resolucion = (Oficio) oficios.get(oficios.size() - 1);
                Date fechaEjecutoria = evento.getFechaEjecutoria();
                if (fechaEjecutoria == null && resolucion.getFechaFirma() == null) {
                    throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere de la fecha de ejecutoria para avanzar desde esta fase");
                }
                hermod.agregarFechaFirmaOficio(new OficioPk(resolucion.getIdOficio()), fechaEjecutoria);

            }
            avanzado = hermod.avanzarTurnoNuevoNormal(turno, fase, tabla, evento.getUsuarioNec());
        } catch (HermodException e) {
            throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        //generar nuevo imprimible
        if (evento.isTurnoDevolucionModificado()) {
            Calendar cFecha = Calendar.getInstance();
            if (turno != null && turno.getFechaInicio() != null) {
                cFecha.setTime(turno.getFechaInicio());
            }
            String fechaImpresion = this.getFechaImpresion(cFecha);
            int maxIntentos;
            int espera;
            Pago pago = null;
            LiquidacionTurnoDevolucion liquidacion = (LiquidacionTurnoDevolucion) turno.getSolicitud().getLiquidaciones().get(0);
            if (liquidacion != null && liquidacion.getPago() != null) {
                pago = liquidacion.getPago();
            }
            if (turno.getSolicitud() != null) {
                liquidacion.setSolicitud(turno.getSolicitud());
            }
            pago.setLiquidacion(liquidacion);
            if (pago.getLiquidacion().getSolicitud() != null && pago.getLiquidacion().getSolicitud().getTurno() == null) {
                pago.getLiquidacion().getSolicitud().setTurno(turno);
            }
            ImprimibleRecibo impRec = new ImprimibleRecibo(pago, turno.getSolicitud().getCirculo(), fechaImpresion, CTipoImprimible.RECIBO);
            ImprimibleRecibo imprimible;
            imprimible = impRec;
            imprimible.setUsuarioGeneraRecibo(Long.toString(evento.getUsuarioNec().getIdUsuario()));
            imprimible.setTamanoCarta(true);
            imprimible.setAreaImprimibleDimension(null);
            impRec.setReimpreso(true);
            Bundle bundle = new Bundle(impRec);
            try{
            String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                    if(copyActive.equals(CHermod.ACTIVE)){
                         bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                    }
            } catch(Throwable e){
                System.out.println("ERROR: NO HA SIDO POSIBLE ESTABLECER LAS COPIAS DEL RECIBO");
            }
            String intentosImpresion;
            String esperaImpresion;
            try {
                intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
                esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
                if (intentosImpresion != null) {
                    Integer esperaInt = new Integer(esperaImpresion);
                    espera = esperaInt.intValue();
                } else {
                    Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                    espera = esperaDefault.intValue();
                }
                if (intentosImpresion != null) {
                    Integer intentos = new Integer(intentosImpresion);
                    maxIntentos = intentos.intValue();
                } else {
                    Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                    maxIntentos = intentosDefault.intValue();
                }
                printJobs.enviar(evento.getUID(), bundle, maxIntentos, espera);
            } catch (Throwable e) {
                throw new TurnoNoAvanzadoException("No se puede generar imprimible", e);
            }
        }
        //fin imprimible

        return new EvnRespDevolucion(avanzado);
    }

    private void imprimir(ImprimibleBase imprimible, String uid) {

        //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;

        //INGRESO DE INTENTOS DE IMPRESION
        try {

            String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
            if (intentosImpresion != null) {
                Integer intentos = new Integer(intentosImpresion);
                maxIntentos = intentos.intValue();
            } else {
                Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                maxIntentos = intentosDefault.intValue();
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
            if (intentosImpresion != null) {
                Integer esperaInt = new Integer(esperaImpresion);
                espera = esperaInt.intValue();
            } else {
                Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                espera = esperaDefault.intValue();
            }
        } catch (Throwable t) {
            Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
            maxIntentos = intentosDefault.intValue();

            Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
            espera = esperaDefault.intValue();

        }

        Bundle bundle = new Bundle(imprimible);
        
        if(imprimible.getTipoImprimible().equals(CTipoImprimible.RECIBO)){
                    try{
                        ImprimibleRecibo imprimibleR = (ImprimibleRecibo) imprimible;
                        String copyActive = hermod.getCopiaImp(imprimibleR.getCirculo().getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
                    } catch (Throwable ex) {
                        try{
                            ImprimibleConstanciaLiquidacion imprimibleR = (ImprimibleConstanciaLiquidacion) imprimible;
                            String copyActive = hermod.getCopiaImp(imprimibleR.getLiquidacion().getCirculo());
                            if(copyActive.equals(CHermod.ACTIVE)){
                                 bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                            }
                        } catch(Throwable th){
                            
                        }
                    }  
        }
        
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle, maxIntentos, espera);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     *
     * @return
     */
    protected String getFechaImpresion() {

        Calendar c = Calendar.getInstance();
        int dia, ano, hora;
        String min, seg, mes;

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
        ano = c.get(Calendar.YEAR);

        hora = c.get(Calendar.HOUR_OF_DAY);
        if (hora > 12) {
            hora -= 12;
        }

        min = formato(c.get(Calendar.MINUTE));
        seg = formato(c.get(Calendar.SECOND));

        String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano + " a las " + formato(hora) + ":" + min + ":" + seg + " " + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

        return fechaImp;
    }

    /**
     * Metodo que retorna un numero con un "0" antes en caso de ser menor que
     * 10.
     *
     * @param i el numero.
     * @return
     */
    protected String formato(int i) {
        if (i < 10) {
            return "0" + (new Integer(i)).toString();
        }
        return (new Integer(i)).toString();
    }

    /**
     * Obtiene la lista de Items de Chequeo para el proceso de Devoluciones.
     *
     * @param evento EvnDevolucion
     */
    private List consultarItemsChequeo(EvnDevolucion evento) throws EventoException {

        List listaRespuesta = new ArrayList();
        try {
            listaRespuesta = hermod.getItemsChequeoDevoluciones();
        } catch (HermodException e) {
            throw new EventoException("No se pudo obtener la lista de items de chequeo." + e.getMessage(), e);
        } catch (Throwable e) {

            throw new EventoException("Error obteniendo la lista de items de chequeo." + e.getMessage(), e);
        }

        return listaRespuesta;
    }

    /**
     * Agrega un recurso a un turno de devolución.
     *
     * @param evento EvnDevolucion
     */
    private Turno agregarRecurso(EvnDevolucion evento) throws EventoException {

        Turno turnoRecargado = null;

        try {

            //Crear el recurso
            Recurso recurso = new Recurso();

            //Se obtiene el turno:
            Turno turno = evento.getTurno();
            TurnoHistoria th = null;
            if (turno != null) {
                long idRecurso = turno.getLastIdHistoria();
                List listaHistorials = turno.getHistorials();

                if (listaHistorials != null) {
                    for (int i = 0; i < listaHistorials.size(); i++) {
                        th = (TurnoHistoria) listaHistorials.get(i);
                    }
                    if (th == null) {
                        throw new EventoException("No se pudo obtener el turno historia para asociar el recurso");
                    }
                    recurso.setTurnoHistoria(th);
                    recurso.setTextoRecurso(evento.getDescripcionRecurso());
                    recurso.setTextoUsuario(evento.getUsuarioRecurso());
                    recurso.setTitulo(evento.getTipoRecurso());
                    recurso.setFecha(evento.getFechaRecurso());

                } else {
                    throw new EventoException("No se pudo obtener el turno historia para asociar el recurso");
                }
            } else {
                throw new EventoException("No se pudo obtener el turno  para asociar el recurso");
            }

            hermod.addRecurso(recurso);

            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = recurso.getAnio();
            turnoId.idCirculo = recurso.getIdCirculo();
            turnoId.idProceso = recurso.getIdProceso();
            turnoId.idTurno = recurso.getIdTurno();

            turnoRecargado = hermod.getTurno(turnoId);

            if (turnoRecargado.getSolicitud().getTurnoAnterior() != null) {
                Turno turnoAnterior = turnoRecargado.getSolicitud().getTurnoAnterior();
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
            } else {
                List neoConsignaciones = new ArrayList();
                List consignaciones = ((SolicitudDevolucion) turnoRecargado.getSolicitud()).getConsignaciones();
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
                        Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada "
                                + printer.toString());
                        throw new EventoException(e.getMessage(), e);
                    }
                    ((SolicitudDevolucion) turnoRecargado.getSolicitud()).setConsignaciones(neoConsignaciones);
                }
            }

        } catch (HermodException e) {
            throw new EventoException("No se pudo agregar el número de resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        return turnoRecargado;
    }
    
    private Turno agregarRecursoNotaNotificada(EvnDevolucion evento) throws EventoException {

        Turno turnoRecargado = null;

        try {

            //Crear el recurso
            Recurso recurso = new Recurso();

            //Se obtiene el turno:
            Turno turno = evento.getTurno();
            TurnoHistoria th = null;
            if (turno != null) {
                long idRecurso = turno.getLastIdHistoria();
                List listaHistorials = turno.getHistorials();

                if (listaHistorials != null) {
                    for (int i = 0; i < listaHistorials.size(); i++) {
                        th = (TurnoHistoria) listaHistorials.get(i);
                    }
                    if (th == null) {
                        throw new EventoException("No se pudo obtener el turno historia para asociar el recurso");
                    }
                    recurso.setTurnoHistoria(th);
                    recurso.setTextoRecurso(evento.getDescripcionRecurso());
                    recurso.setTextoUsuario(evento.getUsuarioRecurso());
                    recurso.setTitulo(evento.getTipoRecurso());
                    recurso.setFecha(evento.getFechaRecurso());

                } else {
                    throw new EventoException("No se pudo obtener el turno historia para asociar el recurso");
                }
            } else {
                throw new EventoException("No se pudo obtener el turno  para asociar el recurso");
            }

            hermod.addRecurso(recurso);

            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = recurso.getAnio();
            turnoId.idCirculo = recurso.getIdCirculo();
            turnoId.idProceso = recurso.getIdProceso();
            turnoId.idTurno = recurso.getIdTurno();

            turnoRecargado = hermod.getTurno(turnoId);

        } catch (HermodException e) {
            throw new EventoException("No se pudo agregar el número de resolución" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        return turnoRecargado;
    }
    
    /**
     * @param evento
     * @return
     */
    private void modificarValorDevolucion(EvnDevolucion evento) throws EventoException {

        //Recuperar valores de la nueva liquidación.
        double derechos = evento.getDevolucionDerechos();
        double impuestos = evento.getDevolucionImpuestos();
        double certificados = evento.getDevolucionCertificados();
        Turno turnoDevolucion = evento.getTurno();
        boolean tieneNegativa = false;
        Solicitud solicitudAnterior = null;

        double absValorDevolver = 0d;
        LiquidacionTurnoCertificado liqCertificados = new LiquidacionTurnoCertificado();
        LiquidacionTurnoRegistro liqRegistro = new LiquidacionTurnoRegistro();
        TurnoPk idTurnoAnterior = new TurnoPk();

        try {

            TurnoPk idTurnoDevolucion = new TurnoPk();
            idTurnoDevolucion.anio = turnoDevolucion.getAnio();
            idTurnoDevolucion.idCirculo = turnoDevolucion.getIdCirculo();
            idTurnoDevolucion.idTurno = turnoDevolucion.getIdTurno();
            idTurnoDevolucion.idProceso = turnoDevolucion.getIdProceso();

            //	SE NECESITA VALIDAR QUE NO SE QUIERA INGRESAR UN VALOR SUPERIOR AL QUE EL CIUDADANO HA PAGADO.
            Turno turnoCompleto = hermod.getTurno(idTurnoDevolucion);

            if (turnoCompleto == null) {
                throw new EventoException("El turno sobre el que se intenta modificar la devolución es nulo");
            }

            Solicitud solicitud = turnoCompleto.getSolicitud();
            Turno turnoAnterior = solicitud.getTurnoAnterior();
            if (turnoAnterior != null) {

                idTurnoAnterior.idCirculo = turnoAnterior.getIdCirculo();
                idTurnoAnterior.idProceso = turnoAnterior.getIdProceso();
                idTurnoAnterior.idTurno = turnoAnterior.getIdTurno();
                idTurnoAnterior.anio = turnoAnterior.getAnio();

                solicitudAnterior = turnoAnterior.getSolicitud();
                List liquidaciones = solicitudAnterior.getLiquidaciones();
                double totalPagado = 0;

                if (liquidaciones != null && liquidaciones.size() > 0) {
                    Iterator itLiquidaciones = liquidaciones.iterator();
                    while (itLiquidaciones.hasNext()) {
                        Liquidacion liq = (Liquidacion) itLiquidaciones.next();
                        if (liq.getPago() != null) {
                            totalPagado = totalPagado + liq.getValor();
                        }
                        if (liq.getValor() < 0) {
                            tieneNegativa = true;
                        }
                    }
                } else {
                    throw new EventoException("No se han podido encontrar las liquidaciones del turno anterior");
                }

                //Turno de Certificados
                if (certificados > 0) {

                    liqCertificados.setValor(certificados * -1);
                    absValorDevolver = Math.abs(liqCertificados.getValor());

                } //Turno de Registro
                else if (derechos > 0 || impuestos > 0) {

                    liqRegistro.setValorDerechos(derechos * -1);
                    liqRegistro.setValorImpuestos(impuestos * -1);
                    liqRegistro.setValor(derechos + impuestos);
                    absValorDevolver = Math.abs(liqRegistro.getValor());
                }

                if (absValorDevolver > totalPagado) {

                    throw new EventoException("No se puede devolver un valor superior al pagado.");
                }

            } else {
                throw new EventoException("No se pudo obtener información del turno asociado a la devolución");
            }

        } catch (Throwable e) {
            throw new EventoException("Error modificando valor de la devolución: ", e);
        }

        //UNA VEZ REALIZADAS LAS VALIDACIONES CORRESPONDIENTES:
        //1. SI EL TURNO ASOCIADO TIENE LA ULTIMA LIQUIDACION NEGATIVA, ESTA SE MODIFICA.
        //2. SI EL TURNO NO TIENE LIQUIDACION NEGATIVA SE CREA UNA LIQUIDACION NEGATIVA.
        //1.1. Caso liquidación de certificados:
        try {

            if (certificados > 0) {
                if (tieneNegativa == true) {
                    liqCertificados.setFecha(new Date());
                    liqCertificados.setUsuario(evento.getUsuarioNec());
                    hermod.updateUltimaLiquidacion(idTurnoAnterior, liqCertificados);
                } else {
                    liqCertificados.setFecha(new Date());
                    liqCertificados.setUsuario(evento.getUsuarioNec());
                    hermod.addLiquidacionToSolicitud(solicitudAnterior, liqCertificados);

                }
            }

            //1.2. Caso liquidación de registro:
            if (derechos > 0 || impuestos > 0) {
                if (tieneNegativa == true) {
                    liqRegistro.setFecha(new Date());
                    liqRegistro.setUsuario(evento.getUsuarioNec());
                    hermod.updateUltimaLiquidacion(idTurnoAnterior, liqRegistro);
                } else {
                    liqRegistro.setFecha(new Date());
                    liqRegistro.setUsuario(evento.getUsuarioNec());
                    hermod.addLiquidacionToSolicitud(solicitudAnterior, liqRegistro);

                }
            }

        } catch (Throwable t) {

        }

    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     *
     * @return Cadena con la fecha de impresión
     */
    protected String getFechaImpresion(Calendar c) {
        int dia;
        int ano;
        int hora;
        String min;
        String seg;
        String mes;
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
        ano = c.get(Calendar.YEAR);
        hora = c.get(Calendar.HOUR_OF_DAY);
        if (hora > 12) {
            hora -= 12;
        }
        min = formato(c.get(Calendar.MINUTE));
        seg = formato(c.get(Calendar.SECOND));
        String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano + " a las " + formato(hora) + ":" + min + ":" + seg + " " + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));
        return fechaImp;
    }
    
    public EventoRespuesta editarRecursoNotasDevolutivas(EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();
        Turno turnoRecargado = null;
        List recursos = obtenerRecursos(turno);
        Recurso rec = (Recurso) recursos.get(evento.getPosicionRecurso());

        RecursoPk rid = new RecursoPk();

        rid.anio = rec.getAnio();
        rid.idCirculo = rec.getIdCirculo();
        rid.idProceso = rec.getIdProceso();
        rid.idRecurso = rec.getIdRecurso();
        rid.idTurno = rec.getIdTurno();
        rid.idTurnoHistoria = rec.getIdTurnoHistoria();

        try {
            hermod.updateRecurso(rid, evento.getDatoAmpliacionRecurso());

            turnoRecargado = hermod.getTurnobyWF(turno.getIdWorkflow());

        } catch (HermodException e) {
            throw new EventoException("No se pudo actualizar el Recurso" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());
        respuesta.setTurnoRespuesta(turnoRecargado);
        return respuesta;
    }
    
    public EventoRespuesta editarRecurso(EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();
        Turno turnoRecargado = null;
        List recursos = obtenerRecursos(turno);
        Recurso rec = (Recurso) recursos.get(evento.getPosicionRecurso());

        RecursoPk rid = new RecursoPk();

        rid.anio = rec.getAnio();
        rid.idCirculo = rec.getIdCirculo();
        rid.idProceso = rec.getIdProceso();
        rid.idRecurso = rec.getIdRecurso();
        rid.idTurno = rec.getIdTurno();
        rid.idTurnoHistoria = rec.getIdTurnoHistoria();

        try {
            hermod.updateRecurso(rid, evento.getDatoAmpliacionRecurso());

            turnoRecargado = hermod.getTurnobyWF(turno.getIdWorkflow());

            if (turnoRecargado.getSolicitud().getTurnoAnterior() != null) {
                Turno turnoAnterior = turnoRecargado.getSolicitud().getTurnoAnterior();
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
            } else {
                List neoConsignaciones = new ArrayList();
                List consignaciones = ((SolicitudDevolucion) turnoRecargado.getSolicitud()).getConsignaciones();
                Iterator iter = consignaciones.iterator();
                while (iter.hasNext()) {
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
                    ((SolicitudDevolucion) turnoRecargado.getSolicitud()).setConsignaciones(neoConsignaciones);
                }
            }

        } catch (HermodException e) {
            throw new EventoException("No se pudo actualizar el Recurso" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());
        respuesta.setTurnoRespuesta(turnoRecargado);
        return respuesta;
    }
    
    public EventoRespuesta editarRecursoNota (EvnDevolucion evento) throws EventoException {
        Turno turno = evento.getTurno();
        Turno turnoRecargado = null;
        List recursos = obtenerRecursos(turno);
        Recurso rec = (Recurso) recursos.get(evento.getPosicionRecurso());

        RecursoPk rid = new RecursoPk();

        rid.anio = rec.getAnio();
        rid.idCirculo = rec.getIdCirculo();
        rid.idProceso = rec.getIdProceso();
        rid.idRecurso = rec.getIdRecurso();
        rid.idTurno = rec.getIdTurno();
        rid.idTurnoHistoria = rec.getIdTurnoHistoria();

        try {
            hermod.updateRecurso(rid, evento.getDatoAmpliacionRecurso());

            turnoRecargado = hermod.getTurnobyWF(turno.getIdWorkflow());


        } catch (HermodException e) {
            throw new EventoException("No se pudo actualizar el Recurso" + e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANDevolucion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespDevolucion respuesta = new EvnRespDevolucion();
        respuesta.setTipoEvento(evento.getTipoEvento());
        respuesta.setTurnoRespuesta(turnoRecargado);
        return respuesta;
    }

    public List obtenerRecursos(Turno turno) {
        List listaTodosRecursos = new ArrayList();
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

        return listaTodosRecursos;
    }
    
//    public void borrarLiquidacionDevolucion(Liquidacion liquidacion) throws ServicioNoEncontradoException, EventoException {
//        try {
//            hermod.deleteLiquidacionTurnoRegistro(liquidacion);
//        } catch (HermodException e) {
//            throw new EventoException(e.getMessage());
//        } catch (Throwable ex) {
//            throw new EventoException(ex.getMessage());
//        }
//    }
}
