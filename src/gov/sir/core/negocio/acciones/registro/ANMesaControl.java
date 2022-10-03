package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnCalificacion;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.negocio.acciones.excepciones.CambiarMatriculaException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificadoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoActo;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.eventos.registro.EvnMesaControl;
import gov.sir.core.eventos.registro.EvnRespMesaControl;
import gov.sir.core.is21.Encriptador;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTurnoExceptionException;
/**
 * @author : Carlos Torres
 * @Caso Mantis : 11604: Acta - Requerimiento No 030_453_Funcionario_Fase_
 * Entregado
 */
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.util.TLSHttpClientComponent;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsProperties;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jfrias,mmunoz
 */
public class ANMesaControl extends SoporteAccionNegocio {

    //SIR 57 R
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

    /**
     * Instancia de la intefaz de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

    private static final String REVISION_CALIFICACION = "REVISION_CALIFICACION";

    /**
     * Constante para enviar el turno a la fase en la que se aprueba la
     * correcci�n de la calificaci�n
     */
    private static final String AJUSTAR_MESA_CONTROL = "AJUSTAR_MESA_CONTROL";

    /**
     * Constante para enviar el turno al proceso de pagos por mayor valor.
     */
    private static final String MAYOR_VALOR = "MAYOR_VALOR";

    /**
     * Constante para enviar el turno a firma del registrador
     */
    private static final String CONFIRMAR = "CONFIRMAR";

    /**
     * Constante para identificar la fase de calificacion
     */
    private static final String FASE_CALIFICACION = "CAL_CALIFICACION";

    /**
     * Constante que se utiliza para delegar el turno a entrega PERSONAL
     */
    public final static String PERSONAL = "PERSONAL";

    /**
     * Constante que se utiliza para para delegar el turno a entrega por
     * CORRESPONDENCIA
     */
    public final static String CORRESPONDENCIA = "CORRESPONDENCIA";

    public static final String DEVUELTO = "DEVUELTO";

    /**
     * Metodo constructo de la accion de negocio
     *
     * @throws EventoException
     */
    public ANMesaControl() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
            if (hermod == null) {
                throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
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
            throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
        }
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;

        if (evento.getTipoEvento().equals(EvnMesaControl.MESA_CONTROL)) {
            return mesa_control(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.CONSULTAR_TURNOS)) {
            return consultarTurnos(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.ENVIAR_RESPUESTA)) {
            return enviarRespuestaNuevo(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.DEVOLVER_REVISION)) {
            return devolverRevisionNuevo(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.IMPRIMIR_CERTIFICADO)) {
            return imprimirCertificadoNuevo(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.IMPRIMIR_CERTIFICADO_RELACION)) {
            return imprimirCertificadoRelacion(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.AVANZAR_MESA_II)) {
            return avanzarMesaIINuevo(ev, CRespuesta.CONFIRMAR);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.NEGAR_MESA_II)) {
            return avanzarMesaII(ev, CRespuesta.NEGAR);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.CAMBIAR_MATRICULA)) {
            return cambiarMatricula(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.CARGAR_MATRICULAS_VALIDAS_CAMBIO)) {
            return determinarMatriculasCertificadosAsociados(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.CAMBIAR_MATRICULA_RELACION)) {
            return cambiarMatriculaRelacion(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.VALIDAR_CERTIFICADOS)) {
            return validarCertificados(ev);
        } else if (evento.getTipoEvento().equals(EvnMesaControl.CONSULTAR_RELACION)) {
            return consultarRelacion(ev);
        }/**
         * Devolver testamento. Pablo Quintana Junio 19 2008
         */
        else if (evento.getTipoEvento().equals(EvnMesaControl.DEVOLVER_TESTAMENTO)) {
            return devolverTestamento(ev);
        }
        return null;
    }

    /**
     * @param ev
     * @return evnRespMesaControl
     */
    private EventoRespuesta mesa_control(Evento ev) {
        EvnMesaControl evento = (EvnMesaControl) ev;
        return new EvnRespMesaControl(evento.getListaFolios());

    }

    /**
     * consulta los turnos dados por el rango contenido en ev.
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta consultarTurnos(Evento ev) throws EventoException {

        EvnMesaControl evento = (EvnMesaControl) ev;

        String turnoDesde = evento.getTurnoDesde();
        String turnoHasta = evento.getTurnoHasta();
        Fase fase = evento.getFase();
        List resultados = new Vector();
        String idFase = fase.getID();

        try {

            resultados = hermod.getRangoTurnosByFase(idFase, turnoDesde, turnoHasta);

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(resultados, EvnRespMesaControl.CONSULTAR_TURNOS);
        return respuesta;

    }

    // DEPURADO ENERO 20 DE 2006
    /**
     * envia la respuesta a hermod para avanzar el turno segun el valor de
     * respuesta en el evento
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta enviarRespuesta(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        String resp = evento.getRespuesta();
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        Hashtable parametros = new Hashtable();

        //inicializar hashtable
        parametros.put("RESULT", CRespuesta.CONFIRMAR);

        try {

            //colocar calificacion
            String USUARIO_INICIADOR = (null != evento.getUsuarioNeg()) ? ("" + evento.getUsuarioNeg().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new TurnoNoAvanzadoException("El usuario no se ha regostrado en la capa web." + this.getClass().getName());
            }

            // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
            //del turno desde esta fase.
            try {
                Hashtable tablaAvance = new Hashtable(2);
                tablaAvance.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
            }

            hermod.updateClasificacionSolicitudRegistro(resp, turno);

            parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
            //hermod.avanzarTurno(turno,fase,parametros,CAvanzarTurno.AVANZAR_CUALQUIERA);

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;

    }

    //DEPURADO ENERO 20 2006
    /**
     * envia la respuesta a hermod para avanzar el turno segun el valor de
     * respuesta en el evento
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta devolverRevision(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        Hashtable parametros = new Hashtable();

        //inicializar tabla hashing
        parametros.put("RESULT", ANMesaControl.AJUSTAR_MESA_CONTROL);

        try {

            String USUARIO_INICIADOR = (null != evento.getUsuarioNeg()) ? ("" + evento.getUsuarioNeg().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new TurnoNoAvanzadoException("El usuario no se ha regostrado en la capa web." + this.getClass().getName());
            }

            parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);

            // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
            //del turno desde esta fase.
            try {
                Hashtable tablaAvance = new Hashtable(2);
                tablaAvance.put(Processor.RESULT, ANMesaControl.REVISION_CALIFICACION);
                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
            }

            //hermod.avanzarTurno(turno,fase,parametros,CAvanzarTurno.AVANZAR_NORMAL);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;
    }

    /**
     * imprime los certificdos y avanza los turnos
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta imprimirCertificado(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;

        Hashtable tabla = new Hashtable();
        List solCerts = evento.getSolicitudesCertificado();
        Fase fase = null;
        Estacion estacion = evento.getEstacion();
        Turno turno = null;
        String path = evento.getPathFirmas();
        Usuario usuario = evento.getUsuario();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        String idUsuario = usuarioNeg.getUsername();
        SolicitudFolio solF = null;

        /*valores de idFase y idProceso para la validacion*/
        String idFaseCertificados = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;

        boolean fallo = false;

        // bug: 3464 ------------------------------------------------------
        // 1. revisar la respuesta de la fase calificacion;
        // condicion para lanzar excepcion:
        // fue-devuelto & no es naturaleza juridica de embargo
        boolean local_Condition_FueDevuelto = false;
        boolean local_Condition_NoEsNaturalezaJuridicaEmbargo = true;

        local_ConditionFueDevuelto_SearchImpl_jx:
        {

            // :: local variables ----------------------------------------------
            Turno local_Turno;
            Solicitud local_TurnoSolicitud;
            Liquidacion local_TurnoSolicitudLiquidacion;

            org.apache.commons.jxpath.JXPathContext local_TurnoSearchContext;

            // // List< TurnoHistoria >
            List local_TurnoHistoria;
            Iterator local_TurnoSearchContextIterator;

            Boolean foundedElelementsCount; // elementos encontrados
            String local_TipoCorreccion;

            // initialize context & variables
            local_Turno = evento.getTurno();
            local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
            local_TurnoSearchContext = org.apache.commons.jxpath.JXPathContext.newContext(local_TurnoHistoria);
            local_TurnoSearchContext.setLenient(true);

            // declare jxpath variables
            //  null
            query:
            {

                String searchRule;
                //searchRule = "count( ( .[ ( @fase = 'CAL_CALIFICACION' ) ] )[last()][ @respuesta = 'DEVOLUCION' )] ) > 0";
                searchRule = ".[ @fase = 'CAL_CALIFICACION' ] ";

                Iterator tmp_Iterator;
                tmp_Iterator = local_TurnoSearchContext.iterate(searchRule);
                Object foundedElement = null;

                // funcion last() en jxpath no funciona muy bien para contextos locales.
                // reemplazo temporal:
                // --------------------------------------------------------------
                foundedElelementsCount = Boolean.FALSE;
                if (null != tmp_Iterator) {
                    for (; tmp_Iterator.hasNext();) {
                        foundedElement = tmp_Iterator.next();
                    } // for
                } // if

                String tmp_LocalRespuesta;
                if (null != foundedElement) {

                    TurnoHistoria tmp_Element = (TurnoHistoria) foundedElement;
                    if (((tmp_LocalRespuesta = tmp_Element.getRespuesta()) != null)
                            && ("DEVOLUCION".equals(tmp_LocalRespuesta))) {

                        local_Condition_FueDevuelto = true;

                    } // if

                } // if
                // --------------------------------------------------------------

            } // :query

        } // :local_ConditionFueDevuelto_SearchImpl_jx
        local_ConditionNoEsNaturalezaJuridicaEmbargo_SearchImpl_jx:
        {
            // :: local variables ----------------------------------------------
            Turno local_Turno;
            Solicitud local_TurnoSolicitud;
            LiquidacionTurnoRegistro local_TurnoSolicitudLiquidacion;

            org.apache.commons.jxpath.JXPathContext local_TurnoSearchContext;
            Boolean foundedElelementsCount; // elementos encontrados

            // :: local variables ----------------------------------------------
            local_Turno = evento.getTurno();

            local_TurnoSearchContext = org.apache.commons.jxpath.JXPathContext.newContext(local_Turno);
            local_TurnoSearchContext.setLenient(true);

            query:
            {

                String searchRule;
                searchRule = "count( ./solicitud/liquidaciones/actos/tipoActo[@idTipoActo='10'] ) > 0";

                Object foundedElement;
                foundedElement = local_TurnoSearchContext.getValue(searchRule);

                foundedElelementsCount = (Boolean) foundedElement;
                local_Condition_NoEsNaturalezaJuridicaEmbargo = !(foundedElelementsCount.booleanValue());

            }

        }

        if (local_Condition_FueDevuelto && local_Condition_NoEsNaturalezaJuridicaEmbargo) {
            ValidacionParametrosException local_Exception;
            local_Exception = new ValidacionParametrosException();
            local_Exception.addError("El turno fue devuelto en la ultima calificacion y no es naturaleza juridica de embargo: no se permite impresion");
            throw local_Exception;
        }

        // ----------------------------------------------------------------
        try {

            /*imprimir certificados*/
            Iterator isol = solCerts.iterator();
            for (; isol.hasNext();) {
                //logica para imprimir un certificado y avanzar su turno respectivo
                //obtener solicitud Certificado
                SolicitudCertificado solCerti = (SolicitudCertificado) isol.next();
                SolicitudPk sid = new SolicitudPk();
                sid.idSolicitud = solCerti.getIdSolicitud();

                //obtener turno , circulo y fase
                turno = hermod.getTurnoBySolicitud(sid);
                String idFase = turno.getIdFase();
                fase = hermod.getFase(idFase);
                String idCirculo = turno.getIdCirculo();

                //validar que el turno no haya sido ya impreso
                if (!idFase.equals(idFaseCertificados) || (turno.getIdProceso() != idProceso)) {
                    throw new ImpresionNoEfectuadaException("El certificado ya fue impreso se encuentra en la fase de entrega.");
                }

                //obtener solicitud Folio
                List listaFolios = solCerti.getSolicitudFolios();
                SolicitudFolio solFolio = (SolicitudFolio) listaFolios.get(listaFolios.size() - 1);

                //obtener liquidaciones y su tipo de tarifa
                List liquidaciones = solCerti.getLiquidaciones();
                LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
                String tipoTarifa = liquidacion.getTipoTarifa();

                //validacion exento
                /*if (tipoTarifa.equals("EXENTO"))
				{
					throw new ImpresionNoEfectuadaException("No es posible imprimir porque el certificado es de tipo EXENTO.");
				}*/
                //colocar valores en la tabla
                //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
                //INGRESO DE INTENTOS DE IMPRESION
                try {

                    String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                    if (intentosImpresion != null) {
                        tabla.put(Processor.INTENTOS, intentosImpresion);
                    } else {
                        tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);

                    }

                    //INGRESO TIEMPO DE ESPERA IMPRESION
                    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                    if (intentosImpresion != null) {
                        tabla.put(Processor.ESPERA, esperaImpresion);
                    } else {
                        tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
                    }
                } catch (Throwable t) {

                    tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
                    tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
                }

                tabla.put(Processor.ESTACION, estacion.getEstacionId());
                tabla.put(Processor.CIRCULO, idCirculo);
                tabla.put(Processor.FIRMA_PATH, path);
                tabla.put(Processor.RESULT, EvnMesaControl.IMPRESION_CONFIRMAR);
                tabla.put(CInfoUsuario.USUARIO_SIR, idUsuario);

            }

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.IMPRIMIR_CERTIFICADO);
        return respuesta;

    }

    /**
     * @param ev
     * @param respuestaWF
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta avanzarMesaII(Evento ev, String respuestaWF) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        Usuario usuario = evento.getUsuario();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        String idUsuario = usuarioNeg.getUsername();

        Hashtable parametros = new Hashtable();

        //inicializar tabla hashing
        parametros.put(CInfoUsuario.USUARIO_SIR, idUsuario);
        parametros.put("RESULT", respuestaWF);

        // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
        //del turno desde esta fase.
        try {
            Hashtable tablaAvance = new Hashtable(2);
            tablaAvance.put(Processor.RESULT, respuestaWF);
            tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
        } catch (Throwable t) {
            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;

    }

    /**
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta cambiarMatricula(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();
        TurnoPk oid = new TurnoPk();
        gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNeg();
        oid.anio = turno.getAnio();
        oid.idCirculo = turno.getIdCirculo();
        oid.idProceso = turno.getIdProceso();
        oid.idTurno = turno.getIdTurno();
        SolicitudCertificado solC = evento.getSolicitudCertificado();
        SolicitudFolio solF = evento.getSolFolio();
        String numMat = "";
        String zonaReg = "";
        if (solF != null) {
            numMat = solF.getIdMatricula();
        }
        List validaciones = null;
        TipoCertificadoPk tipo = new TipoCertificadoPk();
        tipo.idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_ID;

        FolioPk fid = new FolioPk();
        fid.idMatricula = numMat;

        /*valores de idFase y idProceso para la validacion*/
        String idFaseCertificados = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;

        SolicitudPk sid = new SolicitudPk();
        sid.idSolicitud = solC.getIdSolicitud();

        /*obtener lista de validaciones */
        try {
            validaciones = hermod.getValidacionesCertificado(tipo);
        } catch (Throwable t) {
            throw new EventoException("Error recuperando la lista de validaciones para " + "tipo de certificado " + CTipoCertificado.TIPO_ASOCIADO_ID, t);
        }
        try {
            //validar que el turno este en la fase y proceso debidos

            //obtener turno , circulo y fase
            turno = hermod.getTurnoBySolicitud(sid);
            String idFase = turno.getIdFase();

            //validar que el turno no haya sido ya impreso
            if (!idFase.equals(idFaseCertificados) || (turno.getIdProceso() != idProceso)) {
                throw new ValidacionParametrosException("El certificado ya fue impreso se encuentra en la fase de entrega.");
            }

            /*validar solicitud Certificado y del folio a asociar Folio.ID recibe solC y fid */
            hermod.validarMatriculaMesaControl(solC, fid);

            /*llamar a servicio de reemplzado de SolicitudFolio en hermod solC y solF*/
            Folio folio = forseti.getFolioByID(fid, null);
            hermod.forceUnFolio(solC, folio);

            //cargar de nuevo el turno
            turno = hermod.getTurno(oid);

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new CambiarMatriculaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(turno, EvnRespMesaControl.CAMBIAR_MATRICULA);
        return respuesta;

    }

    /**
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta determinarMatriculasCertificadosAsociados(Evento ev) throws EventoException {

        /*Se recupera la informaci�n necesaria para el cambio de matr�cula*/
        EvnMesaControl evento = (EvnMesaControl) ev;
        gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNeg();
        Turno turnoRegistro = evento.getTurnoRegistro();
        List matriculasValidasCambio = new ArrayList();

        try {
            //cargar de nuevo el turno
            turnoRegistro = hermod.getTurnobyWF(turnoRegistro.getIdWorkflow());
            Solicitud solicitud = turnoRegistro.getSolicitud();
            if (solicitud != null && solicitud.getSolicitudFolios() != null) {
                Iterator it = solicitud.getSolicitudFolios().iterator();
                while (it.hasNext()) {

                    SolicitudFolio solFolio = (SolicitudFolio) it.next();
                    matriculasValidasCambio.add(solFolio.getIdMatricula());

                }

            }

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new CambiarMatriculaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(matriculasValidasCambio, EvnRespMesaControl.CARGAR_MATRICULAS_VALIDAS_CAMBIO);
        return respuesta;

    }

    /**
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta cambiarMatriculaRelacion(Evento ev) throws EventoException {

        /*Se recupera la informaci�n necesaria para el cambio de matr�cula*/
        EvnMesaControl evento = (EvnMesaControl) ev;
        gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNeg();
        Turno turnoCertificado = evento.getTurnoCertificado();
        SolicitudFolio solF = evento.getSolFolio();
        String numMat = "";
        if (solF != null) {
            numMat = solF.getIdMatricula();
        }
        List validaciones = null;
        TipoCertificadoPk tipo = new TipoCertificadoPk();
        tipo.idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_ID;

        FolioPk fid = new FolioPk();
        fid.idMatricula = numMat;

        /*valores de idFase y idProceso para la validacion*/
        String idFaseCertificados = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;

        /*obtener lista de validaciones */
        try {
            validaciones = hermod.getValidacionesCertificado(tipo);
        } catch (Throwable t) {
            throw new EventoException("Error recuperando la lista de validaciones para " + "tipo de certificado " + CTipoCertificado.TIPO_ASOCIADO_ID, t);
        }
        try {
            //validar que el turno este en la fase y proceso debidos

            //obtener turno , circulo y fase
            turnoCertificado = hermod.getTurnobyWF(turnoCertificado.getIdWorkflow());
            String idFase = turnoCertificado.getIdFase();

            //validar que el turno no haya sido ya impreso
            if (!idFase.equals(idFaseCertificados) || (turnoCertificado.getIdProceso() != idProceso)) {
                throw new ValidacionParametrosException("El certificado ya fue impreso se encuentra en la fase de entrega.");
            }

            /*validar solicitud Certificado y del folio a asociar Folio.ID recibe solC y fid */
            hermod.validarMatriculaMesaControl((SolicitudCertificado) turnoCertificado.getSolicitud(), fid);

            /*llamar a servicio de reemplzado de SolicitudFolio en hermod solC y solF*/
            Folio folio = forseti.getFolioByID(fid, null);
            hermod.forceUnFolio((SolicitudCertificado) turnoCertificado.getSolicitud(), folio);

            //cargar de nuevo el turno
            //  turno=hermod.getTurno(oid);
        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new CambiarMatriculaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(new Turno(), EvnRespMesaControl.CAMBIAR_MATRICULA);
        return respuesta;

    }

    /**
     * Este m�todo es llamado cuando se quiere obtener lista de turnos que
     * pertenecen a una relaci�n.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EventoRespuesta consultarRelacion(Evento evento) throws EventoException {

        EvnMesaControl eventoMesa = (EvnMesaControl) evento;
        List listaTurnosRelacion = null;
        Hashtable datosTurnosCertificadosAsociados = null;

        long idProceso = eventoMesa.getProceso().getIdProceso();
        String idFase = eventoMesa.getFase().getID();
        String idRelacion = eventoMesa.getIdRelacion();
        //List certificadosValidos=new ArrayList();
        Hashtable certificadosValidos = new Hashtable();

        Log.getInstance().debug(ANMesaControl.class, "ENTRO CONSULTA RELACION an");

        try {

            ProcesoPk procesoId = new ProcesoPk();
            procesoId.idProceso = idProceso;

            Proceso proceso = hermod.getProcesoById(procesoId);
            Fase fase = hermod.getFase(idFase);
            Circulo circulo = eventoMesa.getCirculo();

            listaTurnosRelacion = hermod.getTurnosByRelacion(proceso, fase, circulo, idRelacion);
            datosTurnosCertificadosAsociados = this.consultarCertificadosAsociados(listaTurnosRelacion, certificadosValidos);
        } catch (ValidacionParametrosException vpe) {
            throw vpe;
        } catch (Throwable th) {
            th.printStackTrace();
            ValidacionParametrosException exception = new ValidacionParametrosException("Error al consultar los turnos para la relaci�n : " + idRelacion + th.getMessage());
            exception.addError("Error al consultar los turnos para la relaci�n : " + idRelacion + th.getMessage());
            throw exception;
        }

        //EvnRespTurno eventoResp =new EvnRespTurno(listaTurnosRelacion, EvnRespTurno.CONSULTAR_RELACION);
        EvnRespMesaControl eventoResp = new EvnRespMesaControl(datosTurnosCertificadosAsociados, EvnRespMesaControl.CONSULTAR_RELACION);
        eventoResp.setTurnosCertificadosValidos(certificadosValidos);
        return eventoResp;

    }

    /**
     * Este m�todo es llamado cuando se quiere obtener lista de turnos que
     * pertenecen a una relaci�n.
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

                    //Por cada solicitud hija se busca el turno asociado y su informaci�n
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

                        Log.getInstance().debug(ANMesaControl.class, solC.getTurno() != null ? solC.getTurno().getIdWorkflow() : "ANTURNO IDWORKFLOW");

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

    /**
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarCertificados(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        List solCerts = evento.getSolCerts();
        /*valores de idFase y idProceso para la validacion*/
        String idFase = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;
        List solDef = new Vector();
        List Valido = new Vector();
        Turno turno = evento.getTurno();
        TurnoPk tid = new TurnoPk();
        tid.anio = turno.getAnio();
        tid.idCirculo = turno.getIdCirculo();
        tid.idProceso = turno.getIdProceso();
        tid.idTurno = turno.getIdTurno();

        /**
         * Daniel Mosquera SIR-88
         */
        try {
            Turno nTurno = null;
            SolicitudRegistro solicitud = null;
            nTurno = hermod.getTurno(tid);
            //se busca en el historial si el turno fue devuelto, inscrito, o inscrito parcialmente
            List historials = nTurno.getHistorials();
            String respHist = "";
            boolean permitido = false;
            for (int i = historials.size() - 1; i > 0; i--) {
                TurnoHistoria hist = (TurnoHistoria) historials.get(i);
                if (hist.getFase().equals(CFase.CAL_CALIFICACION)) {
                    respHist = hist.getRespuesta();
                    break;
                }
            }
            solicitud = (SolicitudRegistro) hermod.getSolicitudById(nTurno.getSolicitud().getIdSolicitud());

            String actoEmbargo = "";
            try {
                JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO();
                actoEmbargo = jdoTurnosDAO.getActoSolicitud(turno.getSolicitud().getIdSolicitud());
                System.out.println("ACTO EMBARGO VALIDAR CERTIFICADOS " + actoEmbargo);
            } catch (Exception e) {
                System.out.println("El turno no tiene acto de embargo " + e);
            }

            if (solicitud == null) {
                throw new EventoException("No se encontro la solicitud del turno " + nTurno.getIdWorkflow());
            }

            //si hay devolucion, revisar si existe el acto de embargo
            if (respHist.equals(CRespuesta.DEVOLUCION)) {
                LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) solicitud.getLiquidaciones().get(0);
                List actos = liq.getActos();
                for (int i = 0; i < actos.size(); i++) {
                    Acto acto = (Acto) actos.get(i);
                    if (acto.getTipoActo().getNombre().equals(CTipoActo.ACTO_EMBARGO)) {
                        permitido = true;
                        break;
                    }
                }
            }

            //obtener el bloqueo de los folios
            //forseti.delegarBloqueoFolios(tid,usuarioNeg);
            Iterator isol = solCerts.iterator();
            while (isol.hasNext()) {
                SolicitudCertificado solC = (SolicitudCertificado) isol.next();
                SolicitudCertificado solT;
                solT = (SolicitudCertificado) hermod.getSolicitudById(solC.getIdSolicitud());
                //se comprueba que la solicitud este en la fase y proceso correspondiente pa poder ser impresa
                SolicitudPk sid = new SolicitudPk();
                sid.idSolicitud = solT.getIdSolicitud();
                Turno temp = hermod.getTurnoBySolicitud(sid);
                if (temp.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
                    if ((!temp.getIdFase().equals(idFase)) || (temp.getIdProceso() != idProceso)) {
                        //el turno no esta habilitado para impresion.
                        System.out.println("fase incorrecta");
                        Valido.add(new Boolean(false));
                    } else {
                        //Si hay devolucion con el acto de embargo o inscripci�n
                        if (permitido || respHist.equals(CRespuesta.OK)) {
                            Valido.add(new Boolean(true));
                        } else if (respHist.equals(CRespuesta.INSCRIPCION_PARCIAL)) {
                            //Si hay inscripcion parcial se revisan qu� matriculas se inscribieron
                            boolean encontrado = false;
                            /**
                             * @author: Fernando Padilla
                             * @change: Mantis 7595: Error en la impresion, se
                             * generaba error al tratar de de acceder a
                             * solT.getSolicitudFolios().get(0).
                             */
                            if (solT.getSolicitudFolios() != null && solT.getSolicitudFolios().size() > 0) {
                                SolicitudFolio solFolCert = (SolicitudFolio) solT.getSolicitudFolios().get(0);

                                for (int i = 0; i < solicitud.getSolicitudFolios().size(); i++) {
                                    SolicitudFolio solFolio = (SolicitudFolio) solicitud.getSolicitudFolios().get(i);
                                    if (solFolio.getIdMatricula().equals(solFolCert.getIdMatricula())) {
//                                        if (actoEmbargo.equals(CSolicitudFolio.ACTO_EMBARGO)) {
//                                            Valido.add(new Boolean(true));
//                                        } else {
                                        System.out.println("IP antes de ESTADO_DEVUELTO " + solFolio.getEstado());
                                        if (solFolio.getEstado() != CSolicitudFolio.ESTADO_DEVUELTO || (actoEmbargo!=null && !actoEmbargo.equals("") && actoEmbargo.equals(CSolicitudFolio.ACTO_EMBARGO))) {
                                            Valido.add(new Boolean(true));
                                        } else {
                                            System.out.println("ESTADO_DEVUELTO");
                                            Valido.add(new Boolean(false));
                                        }
                                        encontrado = true;;
                                        break;
//                                        }
                                    }
                                }
                            }
                            if (!encontrado) {
                                Valido.add(new Boolean(true));
                            }
                        } else {
                            System.out.println("no deber�a pasar por aqui");
                            Valido.add(new Boolean(false));
                        }
                    }

                    solT.setTurno(temp);
                    solDef.add(solT);
                }
            }

            /*
			    //obtener el bloqueo de los folios
				//forseti.delegarBloqueoFolios(tid,usuarioNeg);
				Iterator isol= solCerts.iterator();
				while(isol.hasNext()){
					SolicitudCertificado solC= (SolicitudCertificado) isol.next();
					SolicitudCertificado solT;
					solT= (SolicitudCertificado) hermod.getSolicitudById(solC.getIdSolicitud());
					//se comprueba que la solicitud este en la fase y proceso correspondiente pa poder ser impresa
					SolicitudPk sid = new SolicitudPk();
			        sid.idSolicitud = solT.getIdSolicitud();
				    Turno temp= hermod.getTurnoBySolicitud(sid);
				    if( (!temp.getIdFase().equals(idFase)) || (temp.getIdProceso()!=idProceso)){
				        //el turno no esta habilitado para impresion.
				        Valido.add(new Boolean(false));
				    }else{
				        Valido.add(new Boolean(true));
				    }

	                solT.setTurno(temp);
					solDef.add(solT);
				}
             */
        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (ForsetiException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(solDef, EvnRespMesaControl.VALIDAR_CERTIFICADOS);
        respuesta.setValidos(Valido);
        return respuesta;

    }

    /**
     * envia la respuesta a hermod para avanzar el turno segun el valor de
     * respuesta en el evento
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta enviarRespuestaNuevo(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        String resp = evento.getRespuesta();
        Turno turno = evento.getTurno();
        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
        List folios = solicitud.getSolicitudFolios();
        String strEstaEnCorreccion = metodoGeneralTurnosCorreecion(folios, turno);
        if (strEstaEnCorreccion != null && !strEstaEnCorreccion.equals("") && !resp.equals(DEVUELTO)) {
			throw new TurnoNoAvanzadoException(strEstaEnCorreccion);
		}
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
        Fase fase = evento.getFase();
        Hashtable parametros = new Hashtable();
                
                try {
                   
            //colocar calificacion
            String USUARIO_INICIADOR = (null != evento.getUsuarioNeg()) ? ("" + evento.getUsuarioNeg().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new TurnoNoAvanzadoException("El usuario no se ha regostrado en la capa web." + this.getClass().getName());
            }

            hermod.updateClasificacionSolicitudRegistro(resp, turno);

            /**
             * ****************************************************
             */
            /*      Obtener la respuesta adecuada, de acuerdo con  */
 /*      la l�gica anterior de decisores                */
            /**
             * ****************************************************
             */
            //1. Decidir Si es de MAYOR VALOR.
            String itemKey = turno.getIdWorkflow();
            String respuesta = "";

            //Obtener informaci�n del turno a trav�s de los servicios.
            try {
                /**
                 * @author: Fernando Padilla
                 * @change: (Refactoring - proceso avanzar turno mesa control
                 * muy lento, no existe acta, mantis 2945). Este linea se
                 * comenta, ya que, la variable local turnoMayorValor n�nca es
                 * usada en el m�todo.
                 */
                //Turno turnoMayorValor =  hermod.getTurnobyWF(itemKey);

                //Si no se recibi� un turno se genera una excepci�n.
                if (turno == null) {
                    throw new TurnoNoAvanzadoException("El turno recibido es nulo.  No puede generarse decisi�n.");
                }

                //Ubicar si existe un turno historia, asociado con el turno que haya pasado por la
                //fase: FASE_BUSCADA y con la respuesta RESPUESTA_ESPERADA
                List listaHistorials = turno.getHistorials();

                //Si no se recibi� una lista de historiales se genera una excepci�n.
                if (listaHistorials == null) {
                    throw new EventoException("El historial del turno es vac�o.  No puede generarse una decisi�n.");
                }

                int size = listaHistorials.size();

                //Obtener el �ltimo turno historia que pas� por la fase de calificaci�n. 
                //Esto funciona porque se garantiza que el listado de turno historia est� organizado por fecha
                TurnoHistoria ultimoTurnoHistoria = null;
                for (int i = 0; i < size; i++) {
                    TurnoHistoria turnoHistoria = (TurnoHistoria) listaHistorials.get(i);
                    if (turnoHistoria.getFase().equals(FASE_CALIFICACION)) {
                        ultimoTurnoHistoria = turnoHistoria;
                    }

                }

                //No se ha pasado por la fase.
                if (ultimoTurnoHistoria == null) {
                    respuesta = CONFIRMAR;
                } else if (ultimoTurnoHistoria.getRespuesta().equals(MAYOR_VALOR)) {
                    respuesta = MAYOR_VALOR;
                } else {
                    respuesta = CONFIRMAR;
                }

                //2. DECIDIR SI EXISTE SOLICITUD AJUSTE. ESTA RESPUESTA DEBE PRIMAR SOBRE LA DECISION
                //DE MAYOR VALOR. (MIRAR FLAG QUE MARCA TURNOS AJUSTE).
                SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();
                if (solReg == null) {
                    throw new EventoException("El turno no tiene una solicitud asociada.");
                }

                //Si la solicitud requiere ajuste, se genera la respuesta de AJUSTAR_MESA_CONTROL
                if (solReg.getAjuste()) {
                    respuesta = AJUSTAR_MESA_CONTROL;
                }

                //SIR-57
                List turnosByMatricula;
                Turno turnoTes;
                List historials;
                List solicitudFolios = null;
                SolicitudFolio solicitudFolio;
                if (turno.getSolicitud() != null) {
                    solicitudFolios = turno.getSolicitud().getSolicitudFolios();
                }
                boolean faseTestamento = false;
                for (int j = 0; j < solicitudFolios.size(); j++) {
                    solicitudFolio = (SolicitudFolio) solicitudFolios.get(j);
                    try {
                        turnosByMatricula = hermod.getTurnosByMatricula(solicitudFolio.getIdMatricula());
                    } catch (Throwable e) {
                        throw new EventoException("Error obteniendo turnos asociados a la solicitud", e);
                    }
                    for (int i = 0; i < turnosByMatricula.size(); i++) {
                        faseTestamento = false;
                        /**
                         * @author: Fernando Padilla
                         * @change: (Refactoring - proceso avanzar turno mesa
                         * control muy lento, no existe acta, mantis 2945). Este
                         * linea se comenta, ya que, no es necesario volver a la
                         * base de datos a buscar los turnos.
                         */
                        //turnoTes=hermod.getTurno( new TurnoPk(((Turno)turnosByMatricula.get(i)).getIdWorkflow()) );
                        turnoTes = (Turno) turnosByMatricula.get(i);
                        if (turnoTes.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                            historials = turnoTes.getHistorials();
                            for (int hist = (historials.size() - 1); hist >= 0; hist--) {
                                TurnoHistoria turnoHistoriaTemp = (TurnoHistoria) historials.get(hist);
                                if (turnoHistoriaTemp != null) {
                                    if (turnoHistoriaTemp.getFase().equals(CFase.REG_TESTAMENTO)) {
                                        faseTestamento = true;
                                        break;
                                    }
                                }
                            }
                            if (faseTestamento) {
                                if (turnoTes.getIdFase().equals(CFase.REG_CONFRONTAR)
                                        || turnoTes.getIdFase().equals(CFase.REG_REPARTO)
                                        || turnoTes.getIdFase().equals(CFase.CAL_CALIFICACION)) {
                                    throw new EventoException(" El turno tiene asociada la matr�cula " + solicitudFolio.getIdMatricula() + " que est� asociada a turnos de testamento que "
                                            + "no se han finalizado ");
                                }
                            }
                        }
                    }
                }
                Turno turno1 = evento.getTurno();
                TurnoPk oid=new TurnoPk();
                oid.anio=turno1.getAnio();
                oid.idCirculo=turno1.getIdCirculo();
                oid.idProceso=turno1.getIdProceso();
                oid.idTurno=turno1.getIdTurno();
                try{
                        turno1 = hermod.getTurno(oid);
                }catch(Throwable t)
                {

                }
                boolean escalificacion = true;
                try {
                SolicitudRegistro solReg1 = (SolicitudRegistro) turno1.getSolicitud();
                SolicitudRegistro finalS;
            
                finalS = (SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
          
                String clasificacion="";
                if(finalS.getClasificacionRegistro()!=null){
                    clasificacion=finalS.getClasificacionRegistro();
                 }
                Testamento testamento = hermod.getTestamentoByID(evento.getTurno().getSolicitud().getIdSolicitud());
                if(testamento != null){
                    escalificacion = false;
                }    
                
                if(escalificacion){
                     if(clasificacion==null || clasificacion.trim().equals("")){
                                if(turno1.getUltimaRespuesta().equals(CRespuesta.OK) || turno1.getUltimaRespuesta().equals(CRespuesta.CONFIRMAR)){
                                        escalificacion = true;
                                }else if(turno1.getUltimaRespuesta().equals(CRespuesta.MAYOR_VALOR)){
                                        escalificacion = false;
                                }else if(turno1.getUltimaRespuesta().equals(CRespuesta.DEVOLUCION)){
                                        escalificacion = false;
                                }else if (turno1.getUltimaRespuesta().equals(CRespuesta.INSCRITO)){
                                    escalificacion = true;
                                }else if(turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCIONPARCIAL) || turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)|| turno1.getUltimaRespuesta().equals("REGISTRO PARCIAL")){
                                        escalificacion = true;
                                }else{
                                        escalificacion = false;
                                }
                        }else{
                            if(clasificacion.equals(CRespuesta.OK) || clasificacion.equals(CRespuesta.CONFIRMAR)){
                                        escalificacion = true;
                                }else if(clasificacion.equals(CRespuesta.MAYORVALOR)){
                                        escalificacion = false;
                                }else if (clasificacion.equals(CRespuesta.INSCRITO)){
                                    escalificacion = true;
                                }else if(clasificacion.equals(CRespuesta.DEVUELTO)){
                                        escalificacion = false;
                                }else if(clasificacion.equals(CRespuesta.INSCRIPCIONPARCIAL) || clasificacion.equals(CRespuesta.INSCRIPCION_PARCIAL) || clasificacion.equals("REGISTRO PARCIAL")){
                                        escalificacion = true;
                                }else{
                                        escalificacion = false;
                                }
                        }
                        LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg1.getLiquidaciones().get(0);
                        List actos = liquidacion.getActos();
                        for(int e = 0 ; e < actos.size() ; e++){
                            Acto act =(Acto)actos.get(e);
                            if(act.getTipoActo().getIdTipoActo().equals(CActo.TIPO_ACTO_REPRODUCCION_SELLOS)){
                                 escalificacion = false;
                            }
                        }  
                    }
                  } catch (Throwable ex) {
                escalificacion = false;
                }
                
                
                if(escalificacion){
                    TurnoPk oid1=new TurnoPk();
                    oid1.anio=turno1.getAnio();
                    oid1.idCirculo=turno1.getIdCirculo();
                    oid1.idProceso=turno1.getIdProceso();
                    oid1.idTurno=turno1.getIdTurno();
                    try{
                            turno = hermod.getTurno(oid1);
                    }catch(Throwable t)
                    {
                           
                    }
                    boolean  noavanzaradicacion = true;
                    List folioss = evento.getTurno().getSolicitud().getSolicitudFolios();
                    int count = 0;
                    for(int i = 0 ; i < folioss.size() ; i++){
                        SolicitudFolio solFolio =(SolicitudFolio)folioss.get(i);
                        Folio folio = solFolio.getFolio();
                        FolioPk key = new FolioPk();
                        key.idMatricula = folio.getIdMatricula();
                        List anotaciones = forseti.getAnotacionesFolioTMP(key);
                        for(int e = 0 ; e < anotaciones.size() ; e++){
                            Anotacion anota =(Anotacion)anotaciones.get(e);
                            if(anota.isTemporal()){
                                count = count +1;
                                if(anota.getNumRadicacion() != null){
                                    if(anota.getNumRadicacion().equals(turno.getIdWorkflow())){
                                        noavanzaradicacion = false;
                                    } 
                                }
                               
                            }
                            
                        }   
                    }
                    if(count == 0){
                        throw new EventoException(" Ninguno de los Folios tiene anotaciones temporales");
                    }
                    if(noavanzaradicacion){
                        throw new EventoException(" No se encontro una anotacion temporal radicada con el turno en ejecucion");
                    }
                    
                 }
                /**
                 * **************************************
                 */
                /*        ELIMINAR SASS                  */
                /**
                 * **************************************
                 */
                Fase faseAvance = evento.getFase();
                Hashtable parametrosAvance = new Hashtable();
                parametrosAvance.put(Processor.RESULT, respuesta);

                try {
                    Turno turnoAvanzar = hermod.getTurnobyWF(turno.getIdWorkflow());
                    if (!fase.getID().equals(turnoAvanzar.getIdFase()) && fase.getID().equals(CFase.REG_MESA_CONTROL)) {
                        throw new EventoException("El turno ya avanzo a la siguiente fase ya sea por relacion o por otro usuario");
                    }
                    hermod.avanzarTurnoNuevoCualquiera(turno, faseAvance, parametrosAvance, u);

                } catch (Throwable exception) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
                }
            } catch (Throwable exception) {
                if (exception != null && exception.getMessage() != null && exception.getMessage().startsWith(" El turno tiene asociada la matr�cula ")) {
                    throw exception;
                } else if(exception != null && exception.getMessage() != null && exception.getMessage().startsWith(" Ninguno de los Folios")) {
                    throw exception;
                } else if(exception != null && exception.getMessage() != null && exception.getMessage().startsWith(" No se encontro una anotacion")) {
                    throw exception;
                }else{
                    throw new EventoException("Error decidiendo navegaci�n del turno. No se pudo obtener el historial adecuadamente.", exception);                        
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;
    }
        public List ObtenerReproduccionSellos(EvnMesaControl evento) throws EventoException {
            List Reproduccion  = null ;
            try{
                Turno turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                Reproduccion = hermod.getListReproduccionSellos(turno.getIdWorkflow(),turno.getIdCirculo(),0);
            }catch(Throwable ex){
                Reproduccion = new ArrayList();
            }
            if(Reproduccion == null){
                Reproduccion = new ArrayList();
            }
            return Reproduccion;
        }
    /**
     * envia la respuesta a hermod para avanzar el turno segun el valor de
     * respuesta en el evento
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta devolverRevisionNuevo(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();
        gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
        Fase fase = evento.getFase();
        Hashtable parametros = new Hashtable();

//		Se valida que los turnos posteriores que comparten la misma matricula que tengan anotacionesTemporal 
        //tienen que ser devueltos sino estan en calificacion
        try {
            List turnos = new ArrayList();
            TurnoPk oid = new TurnoPk();
            oid.anio = turno.getAnio();
            oid.idCirculo = turno.getIdCirculo();
            oid.idProceso = turno.getIdProceso();
            oid.idTurno = turno.getIdTurno();
            turnos.add(oid);
            forseti.validarPrincipioPrioridadDevolucion(turnos);
        } catch (ForsetiException e1) {
            ExceptionPrinter ep = new ExceptionPrinter(e1);
            throw new ValidacionParametrosHTException(e1);
        } catch (Throwable e2) {
            ExceptionPrinter ep = new ExceptionPrinter(e2);
            throw new EventoException("No se pudieron obtener validacion de Devoluci�n:" + e2.getMessage(), e2);
        }

        try {

            String USUARIO_INICIADOR = (null != evento.getUsuarioNeg()) ? ("" + evento.getUsuarioNeg().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new TurnoNoAvanzadoException("El usuario no se ha regostrado en la capa web." + this.getClass().getName());
            }

            parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);

            // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
            //del turno desde esta fase.
            try {
                Hashtable tablaAvance = new Hashtable(2);
                tablaAvance.put(Processor.RESULT, ANMesaControl.REVISION_CALIFICACION);
                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
            }

            /**
             * **************************************
             */
            /*        ELIMINAR SASS                  */
            /**
             * **************************************
             */
            Fase faseAvance = evento.getFase();
            Hashtable parametrosAvance = new Hashtable();
            parametrosAvance.put(Processor.RESULT, ANMesaControl.AJUSTAR_MESA_CONTROL);

            try {
                //TFS 3582: SI UN TURNO SE VA A DEVOLVER, SE ELIMINAN LAS NOTAS DEVOLUTIVAS
                TurnoPk oid = new TurnoPk();
                oid.anio = turno.getAnio();
                oid.idCirculo = turno.getIdCirculo();
                oid.idProceso = turno.getIdProceso();
                oid.idTurno = turno.getIdTurno();

                hermod.removeDevolutivasFromTurno(oid);
                hermod.avanzarTurnoNuevoNormal(turno, faseAvance, parametrosAvance, u);

            } catch (Throwable exception) {
                throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
            }

        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;
    }

    /**
     * @param ev
     * @param respuestaWF
     * @return respuesta
     * @throws EventoException
     */
     private EventoRespuesta avanzarMesaIINuevo(Evento ev, String respuestaWF) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        String idUsuario = usuarioNeg.getUsername();

        Hashtable parametros = new Hashtable();

        //inicializar tabla hashing
        parametros.put(CInfoUsuario.USUARIO_SIR, idUsuario);
        parametros.put("RESULT", respuestaWF);
        Turno turno1 = evento.getTurno();
        if(evento.getFase().getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
            
                TurnoPk oid=new TurnoPk();
                oid.anio=turno1.getAnio();
                oid.idCirculo=turno1.getIdCirculo();
                oid.idProceso=turno1.getIdProceso();
                oid.idTurno=turno1.getIdTurno();
                try{
                        turno1 = hermod.getTurno(oid);
                }catch(Throwable t)
                {

                }
                boolean escalificacion = true;
                try {
                SolicitudRegistro solReg1 = (SolicitudRegistro) turno1.getSolicitud();
                SolicitudRegistro finalS;
            
                finalS = (SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
          
                String clasificacion="";
                if(finalS.getClasificacionRegistro()!=null){
                    clasificacion=finalS.getClasificacionRegistro();
                 }
                Testamento testamento = hermod.getTestamentoByID(evento.getTurno().getSolicitud().getIdSolicitud());
                if(testamento != null){
                    escalificacion = false;
                }  
                if(escalificacion){
                     if(clasificacion==null || clasificacion.trim().equals("")){
                        if(turno1.getUltimaRespuesta().equals(CRespuesta.OK) || turno1.getUltimaRespuesta().equals(CRespuesta.CONFIRMAR)){
                                escalificacion = true;
                        }else if(turno1.getUltimaRespuesta().equals(CRespuesta.MAYOR_VALOR)){
                                escalificacion = false;
                        }else if(turno1.getUltimaRespuesta().equals(CRespuesta.DEVOLUCION)){
                                escalificacion = false;
                        }else if (turno1.getUltimaRespuesta().equals(CRespuesta.INSCRITO)){
                            escalificacion = true;
                                 }else if(turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCIONPARCIAL) || turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)|| turno1.getUltimaRespuesta().equals("REGISTRO PARCIAL")){
                      escalificacion = true;
                        }else{
                                escalificacion = false;
                        }
                    }else{
                        if(clasificacion.equals(CRespuesta.OK) || clasificacion.equals(CRespuesta.CONFIRMAR)){
                                    escalificacion = true;
                            }else if(clasificacion.equals(CRespuesta.MAYORVALOR)){
                                    escalificacion = false;
                            }else if (clasificacion.equals(CRespuesta.INSCRITO)){
                                escalificacion = true;
                            }else if(clasificacion.equals(CRespuesta.DEVUELTO)){
                                    escalificacion = false;
                            }else if(clasificacion.equals(CRespuesta.INSCRIPCIONPARCIAL) || clasificacion.equals(CRespuesta.INSCRIPCION_PARCIAL) || clasificacion.equals("REGISTRO PARCIAL")){
                                          escalificacion = true;
                            }else{
                                    escalificacion = false;
                            }
                    }
                    LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg1.getLiquidaciones().get(0);
                    List actos = liquidacion.getActos();
                    for(int e = 0 ; e < actos.size() ; e++){
                        Acto act =(Acto)actos.get(e);
                        if(act.getTipoActo().getIdTipoActo().equals(CActo.TIPO_ACTO_REPRODUCCION_SELLOS)){
                             escalificacion = false;
                        }
                    }  
                }
               
                  } catch (Throwable ex) {
                escalificacion = false;
                }
                if(escalificacion){
                    ValidacionParametrosException local_Exception;
                    local_Exception = new ValidacionParametrosException();
                   
                    boolean primererror = false;
                    boolean segundoerror = false;
                    boolean tercererror = false;
                    List<Folio> folioList = new ArrayList<Folio>();
                    List<String> turnowk = new ArrayList<String>();
                    List folioss = turno1.getSolicitud().getSolicitudFolios();
                    for(int i = 0 ; i < folioss.size() ; i++){
                       
                        try {
                            SolicitudFolio solFolio =(SolicitudFolio)folioss.get(i);
                            Folio folio = solFolio.getFolio();
                            FolioPk key = new FolioPk();
                            key.idMatricula = folio.getIdMatricula();
                            List anotaciones = forseti.getAnotacionesFolioTMP(key);
                            for(int e = 0 ; e < anotaciones.size() ; e++){
                                Anotacion anota =(Anotacion)anotaciones.get(e);
                                if(anota.isTemporal()){
                                    folioList.add(folio);
                                    primererror= true;
                                    if(anota.getNumRadicacion() != null){
                                        if(anota.getNumRadicacion().equals(turno1.getIdWorkflow())){
                                            segundoerror = true; 
                                        }else{
                                            turnowk.add("La Matricula " +folio.getIdMatricula() +" tiene anotaciones temporales radicadas con el turno: "+ anota.getNumRadicacion());
                                            tercererror = true;
                                        }
                                    }

                                }

                            }
                            
                        } catch (Throwable ex) {
                            Logger.getLogger(ANMesaControl.class.getName()).log(Level.SEVERE, null, ex);   
                        }
                    }
                    
                    if(segundoerror){
                        local_Exception.addError("Existen anotaciones temporales radicadas con el turno "+evento.getTurno().getIdWorkflow());
                    }
                    
                    
                    if(!local_Exception.getErrores().isEmpty()){
                       throw local_Exception;
                    }
                 }
        }
        // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
        //del turno desde esta fase.
        try {
            Hashtable tablaAvance = new Hashtable(2);
            tablaAvance.put(Processor.RESULT, respuestaWF);
            tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
        } catch (Throwable t) {
            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
        }

        /**
         * **************************************
         */
        /*        ELIMINAR SASS                  */
        /**
         * **************************************
         */
        Fase faseAvance = evento.getFase();
        Hashtable parametrosAvance = new Hashtable();

        //Determinar si el turno se avanza hacia entrega por correspondencia o hacia entrega personal
        //	
        String itemKey = turno.getIdWorkflow();
        String respuesta = "PERSONAL";

        try {
            ServiceLocator service = null;
            service = ServiceLocator.getInstancia();
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
        } catch (Throwable t) {
            if (hermod == null) {
                throw new TurnoNoAvanzadoException("Servicio Hermod no encontrado");
            }
        }

        //Obtener informaci?n del turno a trav?s de los servicios.
        try {
            turno = hermod.getTurnobyWF(itemKey);

            //Si no se recibi? un turno se genera una excepci?n.
            if (turno == null) {
                throw new TurnoNoAvanzadoException("El turno recibido es nulo.  No puede generarse decisi?n.");
            }

        } catch (Throwable tr) {
            throw new TurnoNoAvanzadoException("Error recuperando informa?n del turno de registro.  No puede generarse decisi?n.");
        }

        //Determinar si el turno de registro tiene asociado un acto cuyo tipo de acto es
        //EMBARGO, caso en el cual la respuesta es por correspondencia.
        //En los dem?s casos la respuesta es PERSONALMENTE.
        SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();
        List listaLiquidaciones = solReg.getLiquidaciones();

        if (listaLiquidaciones == null || listaLiquidaciones.size() == 0) {
            respuesta = PERSONAL;
        } else {
            for (int i = 0; i < listaLiquidaciones.size(); i++) {
                LiquidacionTurnoRegistro liqRegistro = (LiquidacionTurnoRegistro) listaLiquidaciones.get(i);
                List listaActos = liqRegistro.getActos();
                if (listaActos != null && listaActos.size() > 0) {
                    for (int j = 0; j < listaActos.size(); j++) {
                        Acto acto = (Acto) listaActos.get(j);
                        if (acto.getTipoActo() != null) {
                            if (acto.getTipoActo().getIdTipoActo().equals(CActo.EMBARGO)) {
                                respuesta = CORRESPONDENCIA;
                                j = listaActos.size() + 1;
                                i = listaLiquidaciones.size() + 1;
                            }
                        }
                    }

                }

            }
        }

        try {
            parametrosAvance.put(Processor.RESULT, respuesta);
            /**
             * @author : Carlos Torres
             * @Caso Mantis : 11604: Acta - Requerimiento No
             * 030_453_Funcionario_Fase_ Entregado
             */
            List turnos = new ArrayList();
            turnos.add(turno);
            Circulo circulo = new Circulo();
            circulo.setIdCirculo(turno.getIdCirculo());
            TipoRelacion tr = new TipoRelacion();
            tr.setIdTipoRelacion("25");
            tr.setIdFase(CFase.REG_CERTIFICADOS_ASOCIADOS);

            boolean avanzo = hermod.avanzarTurnoNuevoNormal(turno, faseAvance, parametrosAvance, usuarioNeg);
            if (avanzo) {
                hermod.crearRelacionNuevo(tr, usuarioNeg, circulo, turnos, "");
            }
        } catch (Throwable exception) {
            throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
        }

        //Aca se avanzan los turnos de Certificados SIN IMPRIMIRLOS 
        try {
            String idFaseCertificados = "CER_ESPERA_IMPRIMIR";
            List solCerts = evento.getAllSolicitudCertificados();
            /*imprimir certificados*/
            if (solCerts != null) {
                Iterator isol = solCerts.iterator();
                for (; isol.hasNext();) {
                    //logica para imprimir avanzar su turno respectivo
                    //obtener solicitud Certificado
                    SolicitudCertificado solCerti = (SolicitudCertificado) isol.next();
                    SolicitudPk sid = new SolicitudPk();
                    sid.idSolicitud = solCerti.getIdSolicitud();

                    //obtener turno , circulo y fase
                    turno = hermod.getTurnoBySolicitud(sid);
                    String idFase = turno.getIdFase();
                    String idCirculo = turno.getIdCirculo();

                    //validar que el turno no haya sido ya impreso
                    if (idFase.equals(idFaseCertificados)) {
                        String idWorkflowCertificado = turno.getIdWorkflow();
                        turno = hermod.getTurnobyWF(idWorkflowCertificado);
                        /**
                         * **************************************
                         */
                        /*        ELIMINAR SASS                  */
                        /**
                         * **************************************
                         */
                        Fase faseAvanceCertificado = evento.getFase();
                        Hashtable parametrosAvanceCertificado = new Hashtable();
                        parametrosAvanceCertificado.put(Processor.RESULT, CRespuesta.CONFIRMAR);

                        try {
                            //hermod.avanzarTurnoNuevoNormal(turno,faseAvanceCertificado,parametrosAvanceCertificado,usuarioNeg);
                            hermod.actualizarTurnoCertificadoAsociado(turno, parametrosAvanceCertificado, usuarioNeg);
                        } catch (Throwable exception) {
                            throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
                        }
                    }
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }
      
        EvnRespMesaControl eventoRespuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return eventoRespuesta;

    }

    /**
     * imprime los certificdos y avanza los turnos
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta imprimirCertificadoNuevo(Evento ev) throws EventoException {
       	 EvnMesaControl evento = (EvnMesaControl) ev;

        Hashtable tabla = new Hashtable();
        List solCerts = evento.getSolicitudesCertificado();
        Fase fase = null;
        Estacion estacion = evento.getEstacion();
        Turno turno = null;
        String path = evento.getPathFirmas();

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();

        SolicitudFolio solF = null;

        /*valores de idFase y idProceso para la validacion*/
        String idFaseCertificados = "CER_ESPERA_IMPRIMIR";
        long idProceso = 1;
        Turno turno1 = evento.getTurno();
        boolean fallo = false;
        if(evento.getFase().getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
            
                TurnoPk oid=new TurnoPk();
                oid.anio=turno1.getAnio();
                oid.idCirculo=turno1.getIdCirculo();
                oid.idProceso=turno1.getIdProceso();
                oid.idTurno=turno1.getIdTurno();
                try{
                        turno1 = hermod.getTurno(oid);
                }catch(Throwable t)
                {

                }
                 boolean escalificacion = true;
                try {
                SolicitudRegistro solReg1 = (SolicitudRegistro) turno1.getSolicitud();
                SolicitudRegistro finalS;
            
                finalS = (SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
          
                String clasificacion="";
                if(finalS.getClasificacionRegistro()!=null){
                    clasificacion=finalS.getClasificacionRegistro();
                 }
                Testamento testamento = hermod.getTestamentoByID(solReg1.getIdSolicitud());
                if(testamento != null){
                    escalificacion = false;
                }
                if(escalificacion){
                    if(clasificacion==null || clasificacion.trim().equals("")){
                            if(turno1.getUltimaRespuesta().equals(CRespuesta.OK) || turno1.getUltimaRespuesta().equals(CRespuesta.CONFIRMAR)){
                                    escalificacion = true;
                            }else if(turno1.getUltimaRespuesta().equals(CRespuesta.MAYOR_VALOR)){
                                    escalificacion = false;
                            }else if(turno1.getUltimaRespuesta().equals(CRespuesta.DEVOLUCION)){
                                    escalificacion = false;
                            }else if (turno1.getUltimaRespuesta().equals(CRespuesta.INSCRITO)){
                                escalificacion = true;
                            }else if(turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCIONPARCIAL) || turno1.getUltimaRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)|| turno1.getUltimaRespuesta().equals("REGISTRO PARCIAL")){
                            escalificacion = true;
                            }else{
                                    escalificacion = false;
                            }
                    }else{
                       if(clasificacion.equals(CRespuesta.OK) || clasificacion.equals(CRespuesta.CONFIRMAR)){
                                    escalificacion = true;
                            }else if(clasificacion.equals(CRespuesta.MAYORVALOR)){
                                    escalificacion = false;
                            }else if (clasificacion.equals(CRespuesta.INSCRITO)){
                                escalificacion = true;
                            }else if(clasificacion.equals(CRespuesta.DEVUELTO)){
                                    escalificacion = false;
                           }else if(clasificacion.equals(CRespuesta.INSCRIPCIONPARCIAL) || clasificacion.equals(CRespuesta.INSCRIPCION_PARCIAL) || clasificacion.equals("REGISTRO PARCIAL")){
                               escalificacion = true;
                            }else{
                                    escalificacion = false;
                            }
                    }
                    LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg1.getLiquidaciones().get(0);
                    List actos = liquidacion.getActos();
                    for(int e = 0 ; e < actos.size() ; e++){
                        Acto act =(Acto)actos.get(e);
                        if(act.getTipoActo().getIdTipoActo().equals(CActo.TIPO_ACTO_REPRODUCCION_SELLOS)){
                             escalificacion = false;
                        }
                    }  
                 }
                  } catch (Throwable ex) {
                escalificacion = false;
                }
                
                if(escalificacion){
                    ValidacionParametrosException local_Exception;
                    local_Exception = new ValidacionParametrosException();
                   
                    boolean primererror = false;
                    boolean segundoerror = false;
                    boolean tercererror = false;
                    List<Folio> folioList = new ArrayList<Folio>();
                    List<String> turnowk = new ArrayList<String>();
                    List folioss = turno1.getSolicitud().getSolicitudFolios();
                    for(int i = 0 ; i < folioss.size() ; i++){
                       
                        try {
                            SolicitudFolio solFolio =(SolicitudFolio)folioss.get(i);
                            Folio folio = solFolio.getFolio();
                            boolean isactivo = false;
                            for (int f = 0 ; f < solCerts.size() ; f++) {
                                SolicitudCertificado solCerti = (SolicitudCertificado) solCerts.get(f);
                                List foliomat = solCerti.getSolicitudFolios();
                                for (int c = 0 ; c < foliomat.size() ; c++) {
                                    SolicitudFolio solici = (SolicitudFolio) foliomat.get(c);
                                    if(solici.getFolio().getIdMatricula().equals(folio.getIdMatricula())){
                                        isactivo = true;
                                    }
                                }
                            }
                            if(isactivo){
                                FolioPk key = new FolioPk();
                                key.idMatricula = folio.getIdMatricula();
                                List anotaciones = forseti.getAnotacionesFolioTMP(key);
                                for(int e = 0 ; e < anotaciones.size() ; e++){
                                    Anotacion anota =(Anotacion)anotaciones.get(e);
                                    if(anota.isTemporal()){
                                        folioList.add(folio);
                                        primererror= true;
                                        if(anota.getNumRadicacion() != null){
                                            if(anota.getNumRadicacion().equals(turno1.getIdWorkflow())){
                                                segundoerror = true; 
                                            }else{
                                                turnowk.add("La Matricula " +folio.getIdMatricula() +" tiene anotaciones temporales radicadas con el turno: "+ anota.getNumRadicacion());
                                                tercererror = true;
                                            }
                                        }

                                    }

                                }
                            }
                        } catch (Throwable ex) {
                            Logger.getLogger(ANMesaControl.class.getName()).log(Level.SEVERE, null, ex);   
                        }
                    }
                    
                    if(segundoerror){
                        local_Exception.addError("Existen anotaciones temporales radicadas con el turno "+evento.getTurno().getIdWorkflow());
                    }
                   
                    
                    if(!local_Exception.getErrores().isEmpty()){
                       throw local_Exception;
                    }
                 }
        }
        // bug: 3464 ------------------------------------------------------
        // 1. revisar la respuesta de la fase calificacion;
        // condicion para lanzar excepcion:
        // fue-devuelto & no es naturaleza juridica de embargo
        boolean local_Condition_FueDevuelto = false;
        boolean local_Condition_NoEsNaturalezaJuridicaEmbargo = true;

        local_ConditionFueDevuelto_SearchImpl_jx:
        {

            // :: local variables ----------------------------------------------
            Turno local_Turno;
            Solicitud local_TurnoSolicitud;
            Liquidacion local_TurnoSolicitudLiquidacion;

            org.apache.commons.jxpath.JXPathContext local_TurnoSearchContext;

            // // List< TurnoHistoria >
            List local_TurnoHistoria;
            Iterator local_TurnoSearchContextIterator;

            Boolean foundedElelementsCount; // elementos encontrados
            String local_TipoCorreccion;

            // initialize context & variables
            local_Turno = evento.getTurno();
            local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
            local_TurnoSearchContext = org.apache.commons.jxpath.JXPathContext.newContext(local_TurnoHistoria);
            local_TurnoSearchContext.setLenient(true);

            // declare jxpath variables
            //  null
            query:
            {

                String searchRule;
                //searchRule = "count( ( .[ ( @fase = 'CAL_CALIFICACION' ) ] )[last()][ @respuesta = 'DEVOLUCION' )] ) > 0";
                searchRule = ".[ @fase = 'CAL_CALIFICACION' ] ";

                Iterator tmp_Iterator;
                tmp_Iterator = local_TurnoSearchContext.iterate(searchRule);
                Object foundedElement = null;

                // funcion last() en jxpath no funciona muy bien para contextos locales.
                // reemplazo temporal:
                // --------------------------------------------------------------
                foundedElelementsCount = Boolean.FALSE;
                if (null != tmp_Iterator) {
                    for (; tmp_Iterator.hasNext();) {
                        foundedElement = tmp_Iterator.next();
                    } // for
                } // if

                String tmp_LocalRespuesta;
                if (null != foundedElement) {

                    TurnoHistoria tmp_Element = (TurnoHistoria) foundedElement;
                    if (((tmp_LocalRespuesta = tmp_Element.getRespuesta()) != null)
                            && ("DEVOLUCION".equals(tmp_LocalRespuesta))) {

                        local_Condition_FueDevuelto = true;

                    } // if

                } // if
                // --------------------------------------------------------------

            } // :query

        } // :local_ConditionFueDevuelto_SearchImpl_jx
        local_ConditionNoEsNaturalezaJuridicaEmbargo_SearchImpl_jx:
        {
            // :: local variables ----------------------------------------------
            Turno local_Turno;
            Solicitud local_TurnoSolicitud;
            LiquidacionTurnoRegistro local_TurnoSolicitudLiquidacion;

            org.apache.commons.jxpath.JXPathContext local_TurnoSearchContext;
            Boolean foundedElelementsCount; // elementos encontrados

            // :: local variables ----------------------------------------------
            local_Turno = evento.getTurno();

            local_TurnoSearchContext = org.apache.commons.jxpath.JXPathContext.newContext(local_Turno);
            local_TurnoSearchContext.setLenient(true);

            query:
            {

                String searchRule;
                searchRule = "count( ./solicitud/liquidaciones/actos/tipoActo[@idTipoActo='10'] ) > 0";

                Object foundedElement;
                foundedElement = local_TurnoSearchContext.getValue(searchRule);

                foundedElelementsCount = (Boolean) foundedElement;
                local_Condition_NoEsNaturalezaJuridicaEmbargo = !(foundedElelementsCount.booleanValue());

            }

        }

        if (local_Condition_FueDevuelto && local_Condition_NoEsNaturalezaJuridicaEmbargo) {
            ValidacionParametrosException local_Exception;
            local_Exception = new ValidacionParametrosException();
            local_Exception.addError("El turno fue devuelto en la ultima calificacion y no es naturaleza juridica de embargo: no se permite impresion");
            throw local_Exception;
        }

        // ----------------------------------------------------------------
        try {

            /*imprimir certificados*/
            Iterator isol = solCerts.iterator();
            for (; isol.hasNext();) {
                //logica para imprimir un certificado y avanzar su turno respectivo
                //obtener solicitud Certificado
                SolicitudCertificado solCerti = (SolicitudCertificado) isol.next();
                SolicitudPk sid = new SolicitudPk();
                sid.idSolicitud = solCerti.getIdSolicitud();

                //obtener turno , circulo y fase
                turno = hermod.getTurnoBySolicitud(sid);
                String idFase = turno.getIdFase();
                fase = hermod.getFase(idFase);
                String idCirculo = turno.getIdCirculo();

                //validar que el turno no haya sido ya impreso
                if (!idFase.equals(idFaseCertificados) || (turno.getIdProceso() != idProceso)) {
                    throw new ImpresionNoEfectuadaException("El certificado ya fue impreso se encuentra en la fase de entrega.");
                }

                String idWorkflowCertificado = turno.getIdWorkflow();
                turno = hermod.getTurnobyWF(idWorkflowCertificado);

                //IMPRIMIR EL CERTIFICADO
                imprimirCertificado(turno, usuarioNeg, idCirculo);

                /**
                 * **************************************
                 */
                /*        ELIMINAR SASS                  */
                /**
                 * **************************************
                 */
                Fase faseAvance = evento.getFase();
                Hashtable parametrosAvance = new Hashtable();
                parametrosAvance.put(Processor.RESULT, CRespuesta.CONFIRMAR);

                try {
                    //hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,usuarioNeg);
                    hermod.actualizarTurnoCertificadoAsociado(turno, parametrosAvance, usuarioNeg);

                } catch (Throwable exception) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
                }

            }

        } catch (HermodException e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.IMPRIMIR_CERTIFICADO);
        return respuesta;

    }
    /**
     * imprime los certificdos y avanza los turnos
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta imprimirCertificadoRelacion(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;

        //VARIABLES DE ENTRADA
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        List solCerts = evento.getSolicitudesCertificado();
        String numeroRelacion = evento.getNumeroRelacion();
        Circulo circulo = evento.getCirculo();
        Fase fase = evento.getFase();
        Turno turno = null;
        Hashtable htErrores = new Hashtable();

        //VALORES DE IDFASE Y  IDPROCESO PARA LA VALIDACI�N
        //String idFaseCertificados="CER_ESPERA_IMPRIMIR";
        //String idFaseFirmaRegistro="REG_FIRMAR";
        long idProceso = 1;

        RelacionPk idRelacion = new RelacionPk();
        idRelacion.idFase = CFase.REG_FIRMAR;
        idRelacion.idRelacion = numeroRelacion;
        Relacion relacion = null;
        try {
            relacion = hermod.getRelacion(idRelacion);
        } catch (Throwable exception) {
            throw new ImpresionNoEfectuadaException("Error consultando el n�mero de relaci�n" + numeroRelacion + ", debe ingresar una relaci�n de firma de registro.", exception);
        }

        if (relacion != null && relacion.getTipoRelacion() != null && relacion.getTipoRelacion().getIdTipoRelacion()
                .equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS)) {

            Iterator isol = solCerts.iterator();
            for (; isol.hasNext();) {
                String turnoAsociado = (String) isol.next();

                try {
                    //OBTENER TURNO, FASE Y CIRCULO
                    turno = hermod.getTurnobyWF(turnoAsociado);
                    String idFase = turno.getIdFase();
                    Fase faseAvance = hermod.getFase(idFase);
                    String idCirculo = turno.getIdCirculo();

                    SolicitudFolio solFolio = (SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0);

                    if (!permitidoImprimir(turno)) {
                        throw new ImpresionNoEfectuadaException("La matricula " + solFolio.getIdMatricula() + ", asociada al certificado no tuvo cambios"
                                + ". Por lo tanto, no se permite su impresion");
                    }

                    //VALIDAR QUE EL TURNO NO HAYA SIDO IMPRESO
                    if (!idFase.equals(CFase.CER_ESPERA_IMPRIMIR) || (turno.getIdProceso() != idProceso)) {
                        throw new ImpresionNoEfectuadaException("El certificado ya fue impreso y se encuentra en la fase de entrega.");
                    }

                    //IMPRIMIR EL CERTIFICADO
                    try {
                        imprimirCertificado(turno, usuarioNeg, idCirculo);
                    } catch (Exception e) {
                        throw new ImpresionNoEfectuadaException("No fue posible imprimir el certificado.");
                    }

                    //AVANZAR EL TURNO
                    try {
                        Hashtable parametrosAvance = new Hashtable();
                        parametrosAvance.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                        hermod.actualizarTurnoCertificadoAsociado(turno, parametrosAvance, usuarioNeg);
                    } catch (Exception e) {
                        throw new ImpresionNoEfectuadaException("Error en el avance del turno de certificados.");
                    }

                } catch (ImpresionNoEfectuadaException exception) {
                    htErrores.put(turno.getIdWorkflow(), exception.getMessage());
                } catch (Throwable exception) {
                    htErrores.put(turno.getIdWorkflow(), "No fue posible imprimir el certificado. " + exception.getMessage());
                }

            }
        } else if (relacion != null && relacion.getTipoRelacion() != null && relacion.getTipoRelacion().getIdTipoRelacion()
                .equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)) {

            List listaTurnosRelacion = null;
            List turnosRegistroValidosImpresion = new ArrayList();
            boolean validoImpresion = false;
            SolicitudAsociada solAsociada = null;

            ProcesoPk procesoId = new ProcesoPk();
            procesoId.idProceso = new Long(CProceso.PROCESO_REGISTRO).longValue();
            Proceso proceso = null;

            try {
                proceso = hermod.getProcesoById(procesoId);
                listaTurnosRelacion = hermod.getTurnosByRelacion(proceso, fase, circulo, numeroRelacion);
            } catch (Throwable e) {
                throw new ImpresionNoEfectuadaException("No fue posible consultar la relaci�n " + numeroRelacion + " " + e.getMessage());
            }

            if (listaTurnosRelacion != null) {

                Iterator it = listaTurnosRelacion.iterator();
                Turno turnoTemp = null;
                Solicitud solicitud = null;
                LiquidacionTurnoRegistro liqRegistro = null;
                Acto acto = null;

                while (it.hasNext()) {

                    turnoTemp = (Turno) it.next();
                    try {
                        turnoTemp = hermod.getTurnobyWF(turnoTemp.getIdWorkflow());
                    } catch (Throwable t) {
                        throw new ImpresionNoEfectuadaException("No fue posible consultar el turno " + turnoTemp.getIdWorkflow() + " " + t.getMessage());
                    }

                    solicitud = turnoTemp.getSolicitud();
                    validoImpresion = false;

                    if (solicitud != null && solicitud.getLiquidaciones() != null && solicitud.getLiquidaciones().size() > 0) {

                        liqRegistro = (LiquidacionTurnoRegistro) solicitud.getLiquidaciones().get(0);
                        Iterator itActos = liqRegistro.getActos().iterator();
                        while (itActos.hasNext()) {
                            acto = (Acto) itActos.next();
                            if (acto != null && acto.getTipoActo() != null && acto.getTipoActo().getIdTipoActo().equals(CActo.EMBARGO)) {
                                validoImpresion = true;
                            }
                        }

                    }
                    if (validoImpresion) {
                        turnosRegistroValidosImpresion.add(turnoTemp.getIdWorkflow());
                    }

                }

            }

            Iterator isol = solCerts.iterator();
            for (; isol.hasNext();) {
                String turnoAsociado = (String) isol.next();

                try {
                    //OBTENER TURNO, FASE Y CIRCULO
                    turno = hermod.getTurnobyWF(turnoAsociado);
                    Solicitud solicitud = turno.getSolicitud();
                    Turno turnoRegistro = null;
                    if (solicitud != null) {
                        List solicitudPadre = solicitud.getSolicitudesPadres();
                        solAsociada = (SolicitudAsociada) solicitudPadre.get(0);
                        Solicitud solPadre = solAsociada.getSolicitudPadre();
                        SolicitudPk idSolicitud = new SolicitudPk();
                        idSolicitud.idSolicitud = solPadre.getIdSolicitud();

                        turnoRegistro = hermod.getTurnoBySolicitud(idSolicitud);
                    }

                    //CUANDO LA RELACI�N ES DE DEVOLUCI�N
                    //S�LO SE PERMITE LA IMPRESI�N SI EL ACTO DEL TURNO ERA EMBARGO
                    if (turnoRegistro == null || !turnosRegistroValidosImpresion.contains(turnoRegistro.getIdWorkflow())) {
                        throw new ImpresionNoEfectuadaException("El turno fue devuelto en la �ltima calificaci�n y no es naturaleza jur�dica de embargo.");
                    } else {

                        String idFase = turno.getIdFase();
                        Fase faseAvance = hermod.getFase(idFase);
                        String idCirculo = turno.getIdCirculo();

                        //VALIDAR QUE EL TURNO NO HAYA SIDO IMPRESO
                        if (!idFase.equals(CFase.CER_ESPERA_IMPRIMIR) || (turno.getIdProceso() != idProceso)) {
                            throw new ImpresionNoEfectuadaException("El certificado ya fue impreso y se encuentra en la fase de entrega.");
                        }

                        //IMPRIMIR EL CERTIFICADO
                        try {
                            imprimirCertificado(turno, usuarioNeg, idCirculo);
                        } catch (Exception e) {
                            throw new ImpresionNoEfectuadaException("No fue posible imprimir el certificado.");
                        }

                        //AVANZAR EL TURNO
                        try {
                            Hashtable parametrosAvance = new Hashtable();
                            parametrosAvance.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                            hermod.actualizarTurnoCertificadoAsociado(turno, parametrosAvance, usuarioNeg);
                        } catch (Exception e) {
                            throw new ImpresionNoEfectuadaException("Error en el avance del turno de certificados.");
                        }

                    }

                } catch (ImpresionNoEfectuadaException exception) {
                    htErrores.put(turno.getIdWorkflow(), exception.getMessage());
                } catch (Throwable exception) {
                    htErrores.put(turno.getIdWorkflow(), "No fue posible imprimir el certificado. " + exception.getMessage());
                }
            }

        } else {
            throw new ImpresionNoEfectuadaException("El n�mero de relaci�n ingresada no es v�lida para la fase de certificados asociados.");
        }

        if (htErrores.size() > 0) {
            ValidacionParametrosHTException vpHT = new ValidacionParametrosHTException(htErrores);
            throw vpHT;
        }

        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.IMPRIMIR_CERTIFICADO);
        return respuesta;

    }

    /**
     * Imprimir el certificado asociado al turno.
     *
     * @param turno el turno
     * @param parametros tabla de Hashing con los parametros de impresion
     * (adem�s de los del WorkFlow)
     * @return la tabla de hashing de parametros adicionando un registro
     * dependiendo de si la impresion fue o no exitosa.
     * @throws Throwable
     */
    private Hashtable imprimirCertificado(Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR, String UID) throws Throwable {
        SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
        List listaFolios = solCerti.getSolicitudFolios();
        int numCopias = solCerti.getNumeroCertificados();
        Hashtable parametros = new Hashtable();

        parametros.put("USUARIO_SIR", usuarioSIR.getUsername());
        parametros.put("CIRCULO_O_UID", UID);

        //Obtener los par�metros.
        //String notificationId = (String) parametros.get(Processor.NOT_ID);
        String intentosImpresion;
        String esperaImpresion;

        //INGRESO DE INTENTOS DE IMPRESION
        try {

            intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.INTENTOS, intentosImpresion);
            } else {
                intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                parametros.put(Processor.INTENTOS, intentosImpresion);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (esperaImpresion != null) {
                parametros.put(Processor.ESPERA, esperaImpresion);
            } else {
                esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
                parametros.put(Processor.ESPERA, esperaImpresion);
            }
        } catch (Throwable t) {
            intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
            parametros.put(Processor.INTENTOS, intentosImpresion);
            esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
            parametros.put(Processor.ESPERA, esperaImpresion);
        }

        /*
		 * No hay un folio asociado y no hay nada que imprimir
		 * (Por ejemplo certificado de pertenencia sin matricula asociada).
         */
        if (listaFolios.size() < 1) {
            return parametros;
        }

        SolicitudFolio solFolio = (SolicitudFolio) listaFolios.get(listaFolios.size() - 1);
        //List foliosDerivados = new Vector();
        Folio folio = null;

        List liquidaciones = solCerti.getLiquidaciones();
        LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
        String tipoTarifa = liquidacion.getTipoTarifa();

        folio = forseti.getFolioByMatricula(solFolio.getIdMatricula());
// se deben consultar todas las anotaciones; el folio se usa para imprimir certificado
        CirculoPk cid = new CirculoPk();
        cid.idCirculo = turno.getIdCirculo();

        FolioPk fid = new FolioPk();
        fid.idMatricula = folio.getIdMatricula();

        List anotaciones = folio.getAnotaciones();
        if (anotaciones == null || anotaciones.isEmpty()) {
            anotaciones = forseti.getAnotacionesFolio(fid);
            folio.setAnotaciones(anotaciones);
        }

        List padres = forseti.getFoliosPadre(fid);
        List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
        /**
         * @author: David Panesso
         * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION Nuevo
         * listado de folios derivados
         *
         */
        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

        String usuario = (String) parametros.get("USUARIO_SIR");
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = new gov.sir.core.negocio.modelo.Usuario();
        usuarioNeg.setUsername(usuario);
        String fechaImpresion = this.getFechaImpresion();
        String tipoImprimible = CTipoImprimible.CERTIFICADO_INMEDIATO;
        if (tipoTarifa.equals(CHermod.EXENTO)) {
            tipoImprimible = CTipoImprimible.CERTIFICADO_EXENTO;
        }

        if (!solCerti.getSolicitudesPadres().isEmpty()) {
            if (((SolicitudAsociada) solCerti.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivo) {
                tipoImprimible = CTipoImprimible.CERTIFICADOS_MASIVOS;
            } else {
                tipoImprimible = CTipoImprimible.CERTIFICADO_ASOCIADO;
            }

        }

        if (forseti.mayorExtensionFolio(folio.getIdMatricula())) {
            tipoImprimible = CTipoImprimible.CERTIFICADO_EXTENSO;
        }

        //obtener textos base de los separadores
        String tbase1 = "";
        String tbase2 = "";
        List variablesImprimibles = hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
        for (Iterator it = variablesImprimibles.iterator(); it.hasNext();) {
            OPLookupCodes op = (OPLookupCodes) it.next();
            if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)) {
                tbase1 = op.getValor();
            }
            if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)) {
                tbase2 = op.getValor();
            }
        }
        ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, foliosDerivadoHijos, fechaImpresion, tipoImprimible, tbase1, tbase2);
        /**
         * @author : Carlos Torres
         * @change : Set propiedad NIS en el imprimible. Caso Mantis : 0006493:
         * Acta - Requerimiento No 027 - Caracteristicas Impresi�n certificados
         */
        String text = turno.getIdWorkflow() + "/" + turno.getSolicitud().getIdSolicitud();
        byte[] key = new byte[8];
        key[0] = 5;
        imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
        imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
        imprimibleCertificado.setPrintWatermarkEnabled(true);
        imprimibleCertificado.setUsuario(usuarioSIR);
        /**
         * @author : Carlos Torres Caso Mantis : 14985: Acta - Requerimiento
         * 028_589_Formato_de_certificado_expedido_para_matr�culas_trasladadas
         */
        Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
        imprimibleCertificado.setInfoTraslado(traslado);

        String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

        FirmaRegistrador firmaRegistrador = null;

        String sNombre = "";
        String archivo = "";
        String cargoToPrint = "";
        String rutaFisica = null;

        try {
            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

            if (firmaRegistrador == null) {
                cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
            }
            if (firmaRegistrador == null) {
                cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
            }
            if (firmaRegistrador == null) {
                cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
            }

            rutaFisica = hermod.getPathFirmasRegistradores();

            sNombre = firmaRegistrador.getNombreRegistrador();
            archivo = firmaRegistrador.getIdArchivo();

            //Se recupera el verdadero cargo para definir si es ENCARGADO o
            //no lo es.
            cargo = firmaRegistrador.getCargoRegistrador();

            //Se saca el valor del cargo para imprimirlo en el certificado
            List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

            cargoToPrint = "";
            OPLookupCodes lookUp;
            for (Iterator it = cargos.iterator(); it.hasNext();) {
                lookUp = (OPLookupCodes) it.next();
                if (lookUp.getCodigo().equals(cargo)) {
                    cargoToPrint = lookUp.getValor();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (sNombre == null) {
            sNombre = "";
        }

        imprimibleCertificado.setCargoRegistrador(cargoToPrint);
        imprimibleCertificado.setNombreRegistrador(sNombre);

        if (rutaFisica != null) {
            BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);

            byte pixeles[] = null;
            try {
                pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
            } catch (Throwable e1) {
                e1.printStackTrace();
            }
            imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
        }

        if (tipoTarifa.equals(CHermod.EXENTO)) {
            imprimibleCertificado.setExento(true);
            String textoExento = hermod.getTextoExento();
            imprimibleCertificado.setTextoExento(textoExento);
        } else {
            imprimibleCertificado.setExento(false);
        }

        //TFS 3790: NO SE PUEDEN IMPRIMIR FOLIOS ANULADOS
        if (!(folio.getEstado().getIdEstado().equals(CEstadoFolio.ANULADO))) {
            parametros = this.imprimir(turno, imprimibleCertificado, parametros, numCopias);
        } else {
            parametros.put(Processor.RESULT, "ERROR");
        }

        String resultado = (String) parametros.get(Processor.RESULT);
        boolean okImpresion = true;
        if (resultado != null) {
            if (resultado.equals("ERROR")) {
                okImpresion = false;
            }
        }

        if (okImpresion) {
            //actualizar el n�mero de impresiones en el turno
            int numImpresiones = solCerti.getNumImpresiones();
            solCerti.setNumImpresiones(numImpresiones + 1);
            hermod.updateSolicitudCertificado(solCerti);
        }

        return parametros;
    }

    /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno es el Turno con el que se creo la solicitud.
     * @param imprimible es la representacion del documento que se desea
     * imprimir (Certificado, Oficio de Pertenencia, Nota Devolutiva, Formulario
     * de Calificacion, Formulario de Correccion, etc).
     * @param parametros tabla de Hashing con los parametros de impresi�n
     * (adem�s de los parametros asociados al WorkFlow).
     * @param numCopias es el n�mero de copias que se desea imprimir.
     * @return
     */
    private Hashtable imprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias) {

        String circulo = turno.getIdCirculo();
        //String impresora = (String)parametros.get(IMPRESORA);
        String imp = (String) parametros.get("CIRCULO_O_UID");

        //Opci�n para imprimir en local o en el applet administrativo de impresi�n
        if (imp != null) {
            circulo = imp;
        }

        //Bundle b = new Bundle(imprimible,impresora);
        Bundle b = new Bundle(imprimible);

        String numIntentos = (String) parametros.get(Processor.INTENTOS);
        String espera = (String) parametros.get(Processor.ESPERA);

        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();
        boolean resultadoImpresion = false;

        //settear el numero de impresiones
        b.setNumeroCopias(numCopias);

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
        try {
            printJobs.enviar(circulo, b, intentos, esperado);
            //si imprime exitosamente sale del ciclo.
            resultadoImpresion = true;
        } catch (Throwable t) {
            t.printStackTrace();
            resultadoImpresion = false;
        }

        if (resultadoImpresion) {
            parametros.put(Processor.RESULT, "CONFIRMAR");
        } else {
            parametros.put(Processor.RESULT, "ERROR");
        }
        return parametros;
    }

    public static BufferedImage getImage(String path, String nombreArchivo) {
        String nombreCompleto = getNombreCompleto(path, nombreArchivo);
        BufferedImage buf = null;

        try {
            File file = new File(nombreCompleto);
            buf = ImageIO.read(file);
        } catch (IOException e) {
            Log.getInstance().error(ANMesaControl.class, e);
            Log.getInstance().debug(ANMesaControl.class, "Error imprimiendo el gr�fico en la ruta: " + nombreCompleto);
        }

        return buf;
    }

    public static String getNombreCompleto(String path, String nombreArchivo) {
        String nombreCompleto = null;

        if (!path.trim().equals(CHermod.CADENA_VACIA)) {
            nombreCompleto = path + nombreArchivo;
        } else {
            nombreCompleto = nombreArchivo;
        }

        return nombreCompleto;
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresi�n.
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

        String fechaImp
                = "Impreso el "
                + dia
                + " de "
                + mes
                + " de "
                + ano
                + " a las "
                + formato(hora)
                + ":"
                + min
                + ":"
                + seg
                + " "
                + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

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
     * envia la respuesta a hermod para avanzar el turno segun el valor de
     * respuesta en el evento
     *
     * @param ev
     * @return respuesta
     * @throws EventoException
     */
    private EventoRespuesta devolverTestamento(Evento ev) throws EventoException {
        EvnMesaControl evento = (EvnMesaControl) ev;
        Turno turno = evento.getTurno();
        gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
        Hashtable parametros = new Hashtable();
        //Se valida que los turnos posteriores que comparten la misma matricula que tengan anotacionesTemporal 
        //tienen que ser devueltos sino estan en calificacion
        try {
            List turnos = new ArrayList();
            TurnoPk oid = new TurnoPk();
            oid.anio = turno.getAnio();
            oid.idCirculo = turno.getIdCirculo();
            oid.idProceso = turno.getIdProceso();
            oid.idTurno = turno.getIdTurno();
            turnos.add(oid);
            forseti.validarPrincipioPrioridadDevolucion(turnos);
        } catch (ForsetiException e1) {
            throw new ValidacionParametrosHTException(e1);
        } catch (Throwable e2) {
            throw new EventoException("No se pudieron obtener validacion de Devoluci�n:" + e2.getMessage(), e2);
        }
        try {

            /**
             * Modifica Pablo Quintana Junio 20 2008 S�lo se devuelve a
             * testamento si no ha avanzado a fase certificados asociados
             */
            List historia = turno.getHistorials();
            String estacionTestamentos = null;
            //daniel
            int i = historia.size() - 1;
            boolean avanzoFaseCertAsociados = false;
            while (i >= 0) {
                TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                if (historial.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)) {
                    if (i != historia.size() - 1) {
                        avanzoFaseCertAsociados = true;
                    }
                }
                //daniel
                if (historial.getFase().equals(CFase.REG_TESTAMENTO)) {
                    estacionTestamentos = historial.getIdAdministradorSAS();
                    Log.getInstance().debug(ANMesaControl.class, "Administrador SAS: " + estacionTestamentos);
                    break;
                }
                --i;
            }
            if (avanzoFaseCertAsociados) {
                throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser devuelto a testamentos porque ya avanz� a la fase  " + CFase.REG_CERTIFICADOS_ASOCIADOS);
            }

            String USUARIO_INICIADOR = (null != evento.getUsuarioNeg()) ? ("" + evento.getUsuarioNeg().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new TurnoNoAvanzadoException("El usuario no se ha regostrado en la capa web." + this.getClass().getName());
            }
            parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
            // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
            //del turno desde esta fase.
            try {
                Hashtable tablaAvance = new Hashtable(2);
                tablaAvance.put(Processor.RESULT, ANMesaControl.REVISION_CALIFICACION);
                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
            }
            /**
             * **************************************
             */
            /*        ELIMINAR SASS                  */
            /**
             * **************************************
             */
            Fase faseAvance = evento.getFase();
            Hashtable parametrosAvance = new Hashtable();
            parametrosAvance.put(Processor.RESULT, CRespuesta.DEVOLVER_MESA_TESTAMENTO);
            //daniel
            parametrosAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
            if (estacionTestamentos != null) {
                parametrosAvance.put(Processor.ESTACION, estacionTestamentos);
                /**
                 * @author Daniel Forero
                 * @change REQ 1156 - Error: Se cambia el valor de esta llave
                 * debido a que su valor debe corresponder a la fase a la cual
                 * esta asociada la estaci�n. Se cambia de
                 * CFase.COR_REVISION_ANALISIS a CFase.REG_TESTAMENTO
                 */
                parametrosAvance.put(Processor.CONDICION_AVANCE, CFase.REG_TESTAMENTO);
            }

            try {
                //TFS 3582: SI UN TURNO SE VA A DEVOLVER, SE ELIMINAN LAS NOTAS DEVOLUTIVAS
                TurnoPk oid = new TurnoPk();
                oid.anio = turno.getAnio();
                oid.idCirculo = turno.getIdCirculo();
                oid.idProceso = turno.getIdProceso();
                oid.idTurno = turno.getIdTurno();
                hermod.removeDevolutivasFromTurno(oid);
                //hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
                hermod.avanzarTurnoNuevoCualquiera(turno, faseAvance, parametrosAvance, u);
            } catch (Throwable exception) {
                throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
            }
        } catch (Throwable e) {
            Log.getInstance().error(ANMesaControl.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespMesaControl respuesta = new EvnRespMesaControl(null, EvnRespMesaControl.ENVIAR_RESPUESTA);
        return respuesta;
    }

    private boolean permitidoImprimir(Turno turno) throws EventoException {

        List solPadres = turno.getSolicitud().getSolicitudesPadres();

        if (solPadres == null || solPadres.size() == 0) {
            throw new EventoException("El turno de certificado no esta asociado a ningun turno de registro");
        }

        SolicitudAsociada solAsociada = (SolicitudAsociada) turno.getSolicitud().getSolicitudesPadres().get(0);
        String idSolPadre = ((SolicitudRegistro) solAsociada.getSolicitudPadre()).getIdSolicitud();
        SolicitudCertificado solCert = (SolicitudCertificado) turno.getSolicitud();
        SolicitudFolio solFolCert = (SolicitudFolio) solCert.getSolicitudFolios().get(0);

        try {
            SolicitudRegistro solReg = (SolicitudRegistro) hermod.getSolicitudById(idSolPadre);

            List solFolios = solReg.getSolicitudFolios();

            Iterator it = solFolios.iterator();

            while (it.hasNext()) {
                SolicitudFolio solFolio = (SolicitudFolio) it.next();
                if (solFolio.getIdMatricula().equals(solFolCert.getIdMatricula())) {
                    if (solFolio.getEstado() == CSolicitudFolio.ESTADO_INSCRITO || solFolio.getEstado() == CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE) {
                        return true;
                    }
                }
            }

        } catch (Throwable t) {
            throw new EventoException(t.getMessage());
        }
        //SolicitudRegistro solReg = (SolicitudRegistro)solAsociada.getSolicitudPadre();

        return false;
    }

    private String metodoGeneralTurnosCorreecion(List folios, Turno turno) throws EventoException {
        Iterator it = folios.iterator();
        List TurnosCorreccion = new ArrayList();
        String StrFolios = "";
        while (it.hasNext()) {
            SolicitudFolio solF = (SolicitudFolio) it.next();
            try {
                List TurnosCorreccionAux = forseti.getTurnosCorreccionActivosFolio(solF.getFolio(), turno);
                if (TurnosCorreccionAux != null && TurnosCorreccionAux.size() > 0) {
                    TurnosCorreccion.addAll(TurnosCorreccionAux);
                    StrFolios += solF.getFolio().getIdMatricula();
                }
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new TomarTurnoRegistroException(e.getMessage());
            }
        }

        TurnoPk turnoID = new TurnoPk();
        turnoID.idCirculo = turno.getIdCirculo();
        turnoID.idProceso = turno.getIdProceso();
        turnoID.idTurno = turno.getIdTurno();
        turnoID.anio = turno.getAnio();

        Hashtable tablaAnotacionesTemporales = new Hashtable();
        String msgAnotaciones = "";
        try {
            tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(turnoID);

            //Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
            Iterator it2 = folios.iterator();
            Boolean tieneAnotacionesTemporales = new Boolean(false);
            while (it2.hasNext() && !tieneAnotacionesTemporales.booleanValue()) {
                SolicitudFolio solFTieneAnotaciones = (SolicitudFolio) it2.next();
                tieneAnotacionesTemporales = (Boolean) tablaAnotacionesTemporales.get(solFTieneAnotaciones.getIdMatricula());
            }


            /*String msgTurnosCorreccion = "\n Turnos de correcciones relacionados con uno o mas folios de este turno";
							
			if(TurnosCorreccion != null){
				Iterator iterCorrecciones  = TurnosCorreccion.iterator();
				while(iterCorrecciones.hasNext()){
					Turno turnoCorrec = (Turno)iterCorrecciones.next();
					if(turnoCorrec.getIdTurno() != null){
						msgTurnosCorreccion +=turnoCorrec.getIdTurno()+"  "; 
					}
				}
			}*/
            if (tieneAnotacionesTemporales.booleanValue() && TurnosCorreccion != null && TurnosCorreccion.size() > 0) {
                msgAnotaciones = "\n El turno contiene folios  los cuales tienen anotaciones temporales y debe"
                        + "\n borrar los temporales para darle prioridad a el turno de correccion."
                        + "\n Folio(s) Asociado(s): ";

                msgAnotaciones += StrFolios;

                /*Throwable e1 = new Throwable(); 
				ExceptionPrinter ep=new ExceptionPrinter(e1);*/
                //logger.error("No fue posible tomar el turno:"+ep.toString());
                //throw new TomarTurnoRegistroException("el turno no puede seguir por que hay folio(s) relacionados con turno(s) de Correccion"+msgAnotaciones,e1);
                msgAnotaciones = "El turno no puede seguir por que hay folio(s) relacionados con turno(s) de Correccion.  " + msgAnotaciones;
            } else if (TurnosCorreccion != null && TurnosCorreccion.size() > 0) {

                /*Throwable e1 = new Throwable(); 
				ExceptionPrinter ep=new ExceptionPrinter(e1);*/
                //logger.error("No fue posible tomar el turno:"+ep.toString());
                //throw new TomarTurnoRegistroException("el turno no es valido por que esta en la fase correccion. "+msgTurnosCorreccion,e1);
                msgAnotaciones = " el turno no puede seguir por que hay folio(s) relacionados con turno(s) de Correccion. "
                        + "\n Folio(s) Asociado(s): " + StrFolios;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return msgAnotaciones;

    }

}
