package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.DocPagoHeredado;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolioMig;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CSolicitudCheckedItem;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaPago_SolicitudData;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaInformativa;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoPk;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiProperties;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.OrdenImpresion;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;
import gov.sir.core.is21.Encriptador;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.core.util.SSLSOAPWebServiceClientComponent;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.print.server.PrintJobsProperties;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * Esta accion de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnPago</CODE> y generar eventos de respuesta del tipo
 * <CODE>EvnPago</CODE> Esta accion de negocio se encarga de atender todas las
 * solicitudes relacionadas con efectuar lquidaciones de solicitudes y registrar
 * pagos, efectuando las validaciones pertinenetes.
 *
 * @author eacosta, mmunoz, jfrias, dsalas
 */
public class ANPago extends SoporteAccionNegocio {

    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    private PrintFactory impresion;

    private static FileOutputStream archivoLog;
    
    private boolean isCertificadoEspecial = false;
    
    private boolean isCertificadoTramite = false;
    
    private boolean isCertificadoActuacion = false;
    
    public static final String CONSIGNACION_VUR = "15";
    
    public static final String PSE_VUR = "14";
    
    public static final String VUR_TOKEN = "$SaUYuP5nhc52UGsa5uIIxOUV4YcvttFHwp9TQM2o";
    
    public static final String VUR_ORIGEN = "1";
    
    
    private List turnoTramite = null;
    static {
        String rutaLog = "printLogs";
        try {
            File file = new File(rutaLog);
            if (!file.canRead()) {
                file.mkdirs();
            }
            archivoLog = new FileOutputStream(rutaLog + "/printing.log");
        } catch (FileNotFoundException e) {
            Log.getInstance().error(ANPago.class, "error abriendo archivo de log", e);
        }
    }

    /**
     * Constructor de la clase ANPago. Es el encargado de invocar al Service
     * Locator y pedirle una instancia del servicio que necesita
     *
     * @throws EventoException
     */
    public ANPago() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            try {
                impresion = PrintFactory.getFactory();
            } catch (FactoryException e) {
                PrintJobsException fe = new PrintJobsException("No fue posible obtener la fábrica concreta", e);
                Log.getInstance().error(ANPago.class, fe.getMessage(), e);
            }

        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio.", e);
        }

    }

    /**
     * Recibe un evento de seguridad y devuelve un evento de respuesta
     *
     * @param e el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnSeguridad</CODE>
     * @throws EventoException cuando ocurre un problema que no se pueda manejar
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespSeguridad</CODE>
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnPago evento = (EvnPago) e;

        if ((evento == null) || (evento.getTipoEvento() == null)) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnPago.VALIDAR)) {
            return validarPago(evento);
        } else if (evento.getTipoEvento().equals(EvnPago.PROCESAR)) {
            System.out.println("VOY A PAGAR AN");
            long tiempoIni = System.currentTimeMillis();
            EventoRespuesta evnResp = procesarPago(evento);
            EvnRespPago ev = (EvnRespPago) evnResp;
//            Pago payment = evento.getPago();
//            List applyPayment = payment.getAplicacionPagos();
//            Iterator itApp = applyPayment.iterator();
//            while(itApp.hasNext()){
//                AplicacionPago aP = (AplicacionPago) itApp.next();
//                DocPagoGeneral dP = (DocPagoGeneral) aP.getDocumentoPago();
//                if(dP.getIdTipoDocPago() == 15 || dP.getIdTipoDocPago() == 14 ){
//                    SSLSOAPWebServiceClientComponent vur = new SSLSOAPWebServiceClientComponent();
//                    String pin = (dP.getNoPin()==null?dP.getNoAprobacion():dP.getNoPin());
//                    try{
//                    vur.callSOAPWebServiceAsignaTurno(VUR_TOKEN,pin,ev.getTurno().getIdWorkflow(),VUR_ORIGEN);
//                    } catch(Exception ex){
//                        System.out.println("ERROR: NO SE PUDO ASIGNAR EL TURNO A VUR");
//                    }
//                }
//            }
            this.escribirPrintLog("ANPago.perform", tiempoIni);
            return evnResp;

        } else if (evento.getTipoEvento().equals(EvnPago.VERIFICAR_APLICACION)) {
            return verificarAplicacion(evento);
        } else if (evento.getTipoEvento().equals(EvnPago.BUSCAR_APLICACION)) {
            return verificarAplicacion(evento);
        }

        return null;
    }

    public static void escribirPrintLog(String metodo, long tiempoIni) {
        if (archivoLog != null) {
            long tiempoFin = System.currentTimeMillis();
            String salida = metodo + ": " + Long.toString(tiempoFin - tiempoIni) + "\n";
            try {
                archivoLog.write(salida.getBytes());
            } catch (IOException e) {
            }
        }
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta verificarAplicacion(EvnPago evento) throws EventoException {
        AplicacionPago aplicacionPago = evento.getAplicacionPago();
        DocumentoPago docPago = null;
        try {
//            if(evento.getCanal() != null && (evento.getCanal().equals(CONSIGNACION_VUR)
//                    || evento.getCanal().equals(PSE_VUR))){
//                pagoVUR = true;
//                SSLSOAPWebServiceClientComponent vur = new SSLSOAPWebServiceClientComponent();
//                String respuesta = vur.callSOAPWebServiceConsultaPIN(VUR_TOKEN, evento.getNoCanal());
//                if(!respuesta.equals("000")){
//                    String turnoAsignado = vur.getTurnoAsignado();
//                    if(respuesta.equals("002") && turnoAsignado != null ){
//                       throw new Exception("El número de PIN ya fue asignado al turno "+ turnoAsignado + " en el sistema preliquidador de derechos VUR.");
//                    }
//                throw new Exception("El número de PIN no existe en el sistema preliquidador de derechos VUR o ya fue utilizado en otro turno.");
//                }
//            }
            docPago = hermod.getDocumentosPagoExistente(aplicacionPago.getDocumentoPago());         
            double saldoFavor  = (double) (docPago != null? docPago.getSaldoDocumento() : 0.0);
            if(saldoFavor == 0d){
            if(evento.getBanco() != null){
            String idBanco = hermod.getIdBanco(evento.getBanco());
            String canal = evento.getCanal();
            String campoCaptura = hermod.getCanalPago(canal);
            String noCanal = evento.getNoCanal();
            if(hermod.restringirAddPago(idBanco,campoCaptura,noCanal)){
//                if(pagoVUR){
//                   throw new Exception("El número de PIN no existe en el sistema o ya fue utilizado en otro turno.");
//                }
                throw new Exception("El documento de pago ya existe en el sistema. Si desea ingresar uno ya registrado por favor haga click en esa opción");
            }
                } else{
                String noCanal = evento.getNoCanal();
                if(noCanal != null){
                String campoCaptura = hermod.getCanalPago(evento.getCanal());
                if(hermod.restringirAddPagoByFP(evento.getCanal(),campoCaptura,noCanal)){
//                if(pagoVUR){
//                   throw new Exception("El número de PIN no existe en el sistema o ya fue utilizado en otro turno.");
//                }
                throw new Exception("El documento de pago ya existe en el sistema. Si desea ingresar uno ya registrado por favor haga click en esa opción");
                }
                }
            }}
            //si es una nueva consignacion no puede existir el documento.
            if (evento.isNuevaAplicacion()) {
                if (docPago != null) {
                    throw new Exception("El documento de pago ya existe en el sistema. Si desea ingresar uno ya registrado por favor haga click en esa opción");
                }
            }
            //si es una consignacion ya registrada debe existir el documento.
            if (!evento.isNuevaAplicacion()) {
                if (docPago == null) {
                    throw new Exception("La aplicación no existe en el sistema o ya fue utilizada en otro turno");
                } else {
                    boolean existeDevolucion = hermod.existeConsignacionDevolucion(docPago.getIdDocumentoPago());
                    if (existeDevolucion) {
                        throw new Exception("El documento existe para devolucion");
                    }
                }
                if (docPago.getSaldoDocumento() == 0.0) {
                    throw new Exception("El saldo del documento es cero");
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANPago.class, e.getMessage(), e);
            throw new PagoInvalidoException("Error registrando el pago", e);
        } catch (Throwable e) {
            Log.getInstance().error(ANPago.class, e.getMessage(), e);
            throw new PagoInvalidoException("Error registrando el pago", e);
        }

        if (docPago == null) {
            return new EvnRespPago(evento.getAplicacionPago(), true);
        }
        if(evento.getAplicacionPago().getDocumentoPago().getSaldoDocumento() != 0d ){
            DocumentoPago documentoPago = evento.getAplicacionPago().getDocumentoPago();
            documentoPago.setIdDocumentoPago(docPago.getIdDocumentoPago());
            aplicacionPago.setDocumentoPago(documentoPago);
        } else {
            aplicacionPago.setDocumentoPago(docPago);
        }

        /*Si el evento que lo llamo no es buscarAplicacion debe decir que es una nuevaconsignacion para poder asignar
		  las marcas*/
        if (evento.getTipoEvento().equals(EvnPago.BUSCAR_APLICACION)) {
            return new EvnRespPago(aplicacionPago, false);
        } else {
            return new EvnRespPago(aplicacionPago, true);
        }
    }

    /**
     * Este metodo hace el llamado al negocio para que se procese el pago de una
     * liquidacion
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnOficinas</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespOficinas</CODE>
     * @throws EventoException
     */
    private EventoRespuesta procesarPago(EvnPago evento) throws EventoException {
        isCertificadoEspecial = evento.isCertificadoEspecial();
        isCertificadoTramite = (evento.isCertificadoTramite());
        isCertificadoActuacion = evento.isCertificadoActuacion();
        turnoTramite = (evento.getTurnoTramite()!=null?evento.getTurnoTramite():null);
        
        
        Pago pago = evento.getPago();
        Liquidacion liquidacionTemp = null;
        Solicitud solicitudTemp = null;
        int idImprimible = 0;

        System.out.println("VOY A PAGAR procesarPago");

        //SE VALIDA QUE SI SE QUIERE PAGAR UN PAGO DE SOLICITUDES MASIVAS DE CERTIFICADOS o una solicitud
        //de registro con certificados asociados.
        //SE ENTRE AL MÉTODO procesarPagoMasivos PARA QUE GENERE UN RECIBO DE CERTIFICADO INDIVIDUAL,
        //YA QUE SI NO SÓLO IMPRIMIRÍA EL RECIBO DE UN TURNO DE CERTIFICADOS MASIVOS
        if (pago != null) {
            System.out.println("PROCESAR PAGO AN PAGO NO NULO");
            liquidacionTemp = pago.getLiquidacion();
        }

        if (liquidacionTemp.getSolicitud() != null) {
            System.out.println("PROCESAR PAGO AN SOLICITUD NO NULA");
            solicitudTemp = liquidacionTemp.getSolicitud();
        }

        if (solicitudTemp instanceof SolicitudCertificadoMasivo) {
            System.out.println("PROCESAR PAGO AN CERTIFICADO MASIVO");
            return (procesarPagoMasivos(evento));
        }

        //SE MIRA EL CASO ESPECIAL DE PAGO DE CERTIFICADOS ASOCIADOS A TURNOS YA RADICADOS
        if (evento.isPagoCertificadosAsociadosTurno()) {
            System.out.println("PROCESAR PAGO AN isPagoCertificadosAsociadosTurno");
            return procesarPagoCertificadosAsociadosTurno(evento);
        }

        //SI ES UN PAGO DIFERENTE DE UN TURNO DE SOLICITUD MASIVA DE CERTIFICADOS CONTINUA EL SIGUIENTE PROCESO.
        pago = ((EvnRespPago) validarPago(evento)).getPago();
        System.out.println("PROCESAR PAGO AN PASO VALIDACION PAGO " + pago.getIdSolicitud());
        
        Estacion estacion = evento.getEstacion();
        Turno turno = null;

        Usuario user = evento.getUsuarioSIR();
        pago.setUsuario(user);

        String impresora = evento.getImpresora();

        EvnRespPago evRespuesta = null;

        long tiempoInicial = System.currentTimeMillis();

        Log.getInstance().debug(ANPago.class, "\n*******************************************************");
        Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO )" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
        Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        try {
            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
            Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO 2)" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

            //CASOS ESPECIALES: PROCESOS DE REGISTRO Y DE FOTOCOPIAS.
            if (evento.isEsPagoRegistro() || evento.isEsPagoFotocopias() || evento.isEsPagoCorreccion() || evento.isEsPagoCertificadoMayorValor()) {
                Pago pagoEvento = null;
                double valorLiquidacion = 0;
                double valorPago = 0;
                List aplicativos = null;

                long valorSecuencial = 0;
                SolicitudRegistro solTemp = null;
                Boolean esCajeroRegistro = new Boolean(false);

                pagoEvento = evento.getPago();
                valorLiquidacion = pagoEvento.getLiquidacion().getValor();
                aplicativos = pagoEvento.getAplicacionPagos();

                if (evento.getValorCertificadosAsociados() != 0) {
                    valorLiquidacion += evento.getValorCertificadosAsociados();
                }
                for (Iterator iter = aplicativos.iterator(); iter.hasNext();) {
                    AplicacionPago element = (AplicacionPago) iter.next();
                    valorPago += element.getValorAplicado();
                }

                if (valorLiquidacion != valorPago) {
                    throw new PagoInvalidoException("El valor del " + "pago debe ser exacto al valor de la liquidación");
                }

                if (solicitudTemp != null && solicitudTemp instanceof SolicitudRegistro) {
                    SolicitudRegistro solRegistro = (SolicitudRegistro) solicitudTemp;
                    if (solRegistro.getClasificacionRegistro() != null && solRegistro.getClasificacionRegistro().equals("MAYOR VALOR")) {
                        Calendar cal2Meses = Calendar.getInstance();
                        cal2Meses.set(Calendar.HOUR, 23);
                        cal2Meses.set(Calendar.MINUTE, 59);
                        cal2Meses.set(Calendar.SECOND, 59);
                        cal2Meses.add(Calendar.MONTH, -2);

                        Turno turnoReg = evento.getPago().getLiquidacion().getSolicitud().getTurno();

                        TurnoPk tID = new TurnoPk();
                        tID.anio = turnoReg.getAnio();
                        tID.idCirculo = turnoReg.getIdCirculo();
                        tID.idProceso = turnoReg.getIdProceso();
                        tID.idTurno = turnoReg.getIdTurno();

                        turnoReg = hermod.getTurno(tID);

                        TurnoHistoria tHist = (TurnoHistoria) turnoReg.getHistorials().get(turnoReg.getHistorials().size() - 1);

                        if (tHist.getFecha().before(cal2Meses.getTime())) {
                            throw new PagoInvalidoException("El término para pago de mayor valor venció");
                        }
                    }
                }

                Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO 3 )" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

                //CASOS ESPECIFICOS FOTOCOPIAS.
                /**
                 * @author : HGOMEZ
                 *** @change : Se elimina una pregunta para que solamente
                 * cuando ** el valor de la liquidación sea diferente de cero se
                 * asigne valor ** a la variable pago. ** Caso Mantis : 12288
                 */
                boolean flag = false;
                if (flag && valorLiquidacion == 0d) {
                    // null; pasa directamente
                } else {
                    //TODO EL PAGO QUE DEVUELVE NO TIENE LAS APLICACIONES DE PAGO
                    pago = hermod.registrarPago(pago, estacion.getEstacionId());
                }

                Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO 4 )" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

                //SI ES UN TURNO DE REGISTRO Y ES UN PAGO POR MAYOR VALOR, ES NECESARIO
                //IMPRIMIR UN RECIBO Y AVANZAR EL TURNO.
                if (evento.isEsPagoRegistro() || evento.isEsPagoCorreccion() || evento.isEsPagoCertificadoMayorValor()) {
                    turno = evento.getTurno();

                    if (turno == null) {
                        if (evento.isEsPagoRegistro()) {
                            EstacionReciboPk estacionRecibo = new EstacionReciboPk();
                            estacionRecibo.idEstacion = estacion.getEstacionId();

                            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                            Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO 5 )" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                            Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

                            valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo, user, Long.valueOf(CProceso.PROCESO_REGISTRO).longValue());
                            solTemp = (SolicitudRegistro) hermod.getSolicitudById(pago.getIdSolicitud());

                            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                            Log.getInstance().debug(ANPago.class, "(METODO PROCESAR PAGO 6)" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                            Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

                            //obtener checkitmes y agregarlos a la solicitud.
                            SubtipoSolicitud subsol = solTemp.getSubtipoSolicitud();

                            List checkitems = hermod.getCheckItemsBySubtipoSolicitud(subsol);
                            Iterator ic = checkitems.iterator();

                            for (; ic.hasNext();) {
                                CheckItem check = (CheckItem) ic.next();
                                /*Validacion para que no salga el check de Traer documento de identidad del solicitante */
                                if (!(check.getIdCheckItem().equals(CSolicitudCheckedItem.DOCUMENTO_IDENTIDAD)
                                        && check.getIdSubtipoSol().equals(CSolicitudCheckedItem.SUBTIPO_NORMAL))) {
                                    subsol.addCheckItem(check);
                                }
                            }
                            solTemp.setSubtipoSolicitud(subsol);

                            //SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
                            try {
                                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                                Iterator it = roles.iterator();
                                while (it.hasNext()) {
                                    Rol rol = (Rol) it.next();
                                    if (rol.getRolId().equals(CRoles.CAJERO_REGISTRO)) {
                                        esCajeroRegistro = new Boolean(true);
                                    }

                                }

                            } catch (Exception fe) {
                                ExceptionPrinter printer = new ExceptionPrinter(fe);
                                Log.getInstance().error(ANPago.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
                            }

                        }

                    } else {

                        TurnoPk idTurno = new TurnoPk();
                        idTurno.anio = turno.getAnio();
                        idTurno.idCirculo = turno.getIdCirculo();
                        idTurno.idTurno = turno.getIdTurno();
                        idTurno.idProceso = turno.getIdProceso();
                        Turno turnoPersistente = hermod.getTurno(idTurno);

                        boolean condicion = true;
                        
                        Pago pagoPaso = new Pago();
                        
                        //Si hay mas de una liquidacion con pago se debe imprimir recibo y avanzar.
                        if (turnoPersistente != null) {
                            if (turnoPersistente.getSolicitud() != null) {
                                if (turnoPersistente.getSolicitud().getLiquidaciones() != null) {
                                    List listaLiquidaciones = turnoPersistente.getSolicitud().getLiquidaciones();
                                    int liquidacionesPagadas = 0;
                                    for (int j = 0; j < listaLiquidaciones.size(); j++) {
                                        Liquidacion liqRegistro = (Liquidacion) listaLiquidaciones.get(j);
                                        if(liquidacionesPagadas == 1){
                                            pagoPaso = liqRegistro.getPago();
                                        }
                                        Pago pagoPersistente = liqRegistro.getPago();
                                        if (pagoPersistente != null) {
                                            liquidacionesPagadas++;
                                        }                                                                                                                                                            
                                    }
                                    if (liquidacionesPagadas > 1) {
                                        boolean isMayorValorCorreciones = false;

                                        if (turnoPersistente.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                                            isMayorValorCorreciones = true;
                                        }
                                        //AVANZAR EL TURNO.
                                        turno = evento.getPago().getLiquidacion().getSolicitud().getTurno();
                                        Fase fase = evento.getFase();

                                        Hashtable parameters = new Hashtable();
                                        parameters.put(Processor.RESULT, evento.getRespuestaWF());

                                        // call hermod service
                                        try {
                                            Pago pagoEnviar = pagoPaso;
                                            pagoEnviar.setIdLiquidacion(pago.getIdLiquidacion());
                                            pagoEnviar.setIdSolicitud(pago.getIdSolicitud());
                                            //TODO PARA PAGO MAYOR VALOR, AVANZAR LOS TURNOS CON EL OTRO METODO
                                            if (evento.isEsPagoCertificadoMayorValor() || evento.isEsPagoCorreccion() || evento.isEsPagoRegistro()) {
                                                {
                                                    long tiempoIni = System.currentTimeMillis();
                                                    hermod.avanzarTurnoNuevoNormal(turno, fase, parameters, evento.getUsuarioSIR());
                                                    this.escribirPrintLog("ANPago.procesarPago.avanzarTurnoNuevoNormal", tiempoIni);
                                                }
                                            }

                                            // SE PUEDE VALIDAR SI ES MAYOR VALOR
                                            {
                                                long tiempoIni = System.currentTimeMillis();
                                                idImprimible = imprimirRecibo(turnoPersistente, evento, pagoEnviar, estacion, user, impresora, isMayorValorCorreciones, null, 1);
                                                this.escribirPrintLog("ANPago.procesarPago.imprimirRecibo", tiempoIni);
                                            }
                                        } catch (HermodException e) {
                                            Log.getInstance().error(ANPago.class, e.getMessage(), e);
                                            throw new EventoException(e.getMessage(), e);
                                        } catch (Throwable e) {
                                            Log.getInstance().error(ANPago.class, e.getMessage(), e);
                                            throw new EventoException(e.getMessage(), e);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                /**
                 * ****************************************************************************
                 */
                /*                       CAMBIO ELIMINAR SAS                                   */
                /**
                 * ****************************************************************************
                 */
                if (evento.isEsPagoFotocopias()) {

                    // valor para colocar en el hash: usuario;
                    String local_UsuarioIniciador = (null != evento.getUsuarioSIR()) ? ("" + evento.getUsuarioSIR().getUsername()) : (getNullableString(true));
                    // Bug 05223
                    String local_UsuarioGeneraRecibo;
                    local_UsuarioGeneraRecibo = print_FootUtils_BuildUserName(evento.getUsuarioSIR());

                    // Se realiza impresion con valor 0 ? creo que no
                    /**
                     * @author : HGOMEZ
                     *** @change : Se comenta el siguiente if. ** Caso Mantis :
                     * 12288
                     */
                    //if (valorLiquidacion > 0d) {
                    // realizar impresion ------------------------------------------
                    String sessionId = evento.getSessionId();
                    gov.sir.core.negocio.modelo.Circulo circulo = evento.getCirculo();
                    SolicitudFotocopia solicitud = (SolicitudFotocopia) evento.getSolicitud();
                    gov.sir.core.negocio.modelo.Liquidacion liquidacion = liquidacionTemp;
                    if (null == turno) {
                        Turno turnoTemporal = evento.getTurno();
                        turno = turnoTemporal;
                    }

                    EstacionReciboPk estacionReciboID = new EstacionReciboPk();
                    estacionReciboID.idEstacion = estacion.getEstacionId();

                    String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionReciboID, user, Long.valueOf(CProceso.PROCESO_FOTOCOPIAS).longValue()));
                    pago.setNumRecibo(numRecibo);

                    PagoPk pagoID = new PagoPk();
                    pagoID.idLiquidacion = pago.getIdLiquidacion();
                    pagoID.idSolicitud = pago.getIdSolicitud();
                    hermod.setNumeroReciboPago(pagoID, numRecibo);

//                    DocumentoPago docPago;
//                    List aplicaciones = pago.getAplicacionPagos();
//                    if (aplicaciones != null) {
//                        Iterator it = aplicaciones.iterator();
//                        Banco banco = null;
//                        while (it.hasNext()) {
//                            AplicacionPago ap = (AplicacionPago) it.next();
//                            docPago = ap.getDocumentoPago();
//                            if (docPago instanceof DocPagoGeneral) {
//                                try {
//                                    CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
//                                    ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                                    ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                                    if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                                        banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                                        ((DocPagoGeneral) docPago).setBanco(banco);
//                                    } else {
//                                        ((DocPagoGeneral) docPago).setBanco(banco);
//                                    }
//
//                                } catch (Throwable ex) {
//                                    Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                                }
//
//                            }
//                        }
//
//                    }

                    pagoFocotopia_imprimibleRecibo(circulo, pago, turno, liquidacion, solicitud, pagoEvento.getAplicacionPagos(), sessionId, local_UsuarioGeneraRecibo);

                    //}
                    // avanzar el turno ------------------------------------------
                    // valor para colocar en el hash: usuario;
                    String USUARIO_INICIADOR = local_UsuarioIniciador;

                    turno = evento.getPago().getLiquidacion().getSolicitud().getTurno();
                    Fase fase = evento.getFase();
                    // Estacion estacion = evento.getEstacion();
                    Hashtable parameters = new Hashtable();
                    parameters.put(Processor.RESULT, evento.getRespuestaWF());
                    parameters.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
                    // parameters.put(Processor.USUARIO_INICIADOR, USUARIO_INICIADOR);

                    // call hermod service
                    try {
                        Hashtable parametros = new Hashtable();
                        Turno turnoAvance = evento.getTurno();
                        Fase faseAvance = evento.getFase();
                        String respuestaWf = evento.getRespuestaWF();
                        parametros.put(Processor.RESULT, respuestaWf);

                        hermod.avanzarTurnoNuevoNormal(turnoAvance, faseAvance, parametros, evento.getUsuarioSIR());

                    } catch (HermodException e) {
                        Log.getInstance().error(ANPago.class, e.getMessage(), e);
                        throw new EventoException(e.getMessage(), e);
                    } catch (Throwable e) {
                        Log.getInstance().error(ANPago.class, e.getMessage(), e);
                        throw new EventoException(e.getMessage(), e);
                    }

                }

                evRespuesta = new EvnRespPago(pago);
                evRespuesta.setIdImprimible(idImprimible);
                if (solTemp != null) {
                    evRespuesta.setSolicitud(solTemp);
                    evRespuesta.setValorSecuencial(valorSecuencial);
                    evRespuesta.setEsCajeroRegistro(esCajeroRegistro);
                }
            } else {

                long tiempoIni = System.currentTimeMillis();
                HashMap hm = generarPagoConTurno(evento, pago, estacion, user, impresora);
                this.escribirPrintLog("ANPago.procesarPago.generarPagoConTurno", tiempoIni);

                List listaNotasInformativas = evento.getListaNotasSinTurno();
                if (listaNotasInformativas != null) {
                    turno = pago.getLiquidacion().getSolicitud().getTurno();
                    TurnoPk idTurno = new TurnoPk();
                    idTurno.anio = turno.getAnio();
                    idTurno.idCirculo = turno.getIdCirculo();
                    idTurno.idTurno = turno.getIdTurno();
                    idTurno.idProceso = turno.getIdProceso();
                    for (int i = 0; i < listaNotasInformativas.size(); i++) {
                        Nota notaTurno = (Nota) listaNotasInformativas.get(i);
                        hermod.addNotaToTurno(notaTurno, idTurno);
                    }
                }

                Turno t = (Turno) hm.get(WebKeys.TURNO);
                Integer temp = (Integer) hm.get(WebKeys.ID_IMPRIMIBLE);
                evRespuesta = new EvnRespPago(t);
                evRespuesta.setIdImprimible(temp.intValue());
                evRespuesta.setImprimiblePdf((ImprimiblePdf) hm.get(WebKeys.IMPRIMIBLE_PDF));
                if (t != null && t.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                    evRespuesta.setValorSecuencial(Double.parseDouble((String) hm.get(WebKeys.SECUENCIAL_RECIBO_ESTACION)));
                }
            }
        } catch (PagoInvalidoException e) {
            throw e;
        } catch (HermodException e) {
            throw new PagoNoProcesadoException("Ocurrio un error procesando el pago ", e);
        } catch (ForsetiException e) {
            throw new PagoNoProcesadoException("Ocurrio un error procesando el pago ", e);
        } catch (PrintJobsException e) {
            if (turno != null) {
                throw new ErrorImpresionException("No se pudo imprimir el recibo:" + e.getMessage(), turno.getIdWorkflow());
            }
        } catch (Throwable e) {
            Log.getInstance().error(ANPago.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException("Excepcion inesperada", e);
        }

        return evRespuesta;
    }

    // -----------------------------------------------------------------------------
    private String
            print_FootUtils_BuildUserName(long userId) {
        return "" + userId;
    } // end method

    private String
            print_FootUtils_BuildUserName(Long userId) {
        if (null == userId) {
            return getNullableString(true);
        }
        return print_FootUtils_BuildUserName(userId.longValue());
    } // end method

    private String
            print_FootUtils_BuildUserName(gov.sir.core.negocio.modelo.Usuario user) {
        if (null == user) {
            return getNullableString(true);
        }
        return print_FootUtils_BuildUserName(user.getIdUsuario());
    } // end method

    public static String
            Nvl(String string, String replaceIfNull) {
        return (null == string) ? (replaceIfNull) : (string);
    } // end-method: Nvl

    public static String
            getNullableString(boolean treatBlankAsNull) {
        return ((treatBlankAsNull) ? ("") : (null));
    } // end-method: Nvl

// -----------------------------------------------------------------------------
    private int imprimirRecibo(Turno turno, EvnPago evento, Pago pago, Estacion estacion, Usuario user, String impresora, boolean isMayorValorCorreciones, OrdenImpresion ordenImpresion, int ordenActual) throws EventoException {
        Solicitud solicitud = turno.getSolicitud();
        List liquidaciones = solicitud.getLiquidaciones();
        //String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();

        EstacionReciboPk estacionReciboID = new EstacionReciboPk();
        estacionReciboID.idEstacion = estacion.getEstacionId();

        if (evento.isEsPagoRegistro() || !turno.getSolicitud().getSolicitudesPadres().isEmpty() || isMayorValorCorreciones
                || evento.isEsPagoCertificadoMayorValor()) {
            try {
                String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionReciboID, user, turno.getIdProceso()));
                pago.setNumRecibo(numRecibo);

                PagoPk pagoID = new PagoPk();
                pagoID.idLiquidacion = pago.getIdLiquidacion();
                pagoID.idSolicitud = pago.getIdSolicitud();
                hermod.setNumeroReciboPago(pagoID, numRecibo);
            } catch (Throwable e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        String idLiq = pago.getIdLiquidacion();
        String justificacionMayorValor = "";
        Liquidacion liqTemp = null;

        Iterator itLiq = turno.getSolicitud().getLiquidaciones().iterator();
        while (itLiq.hasNext()) {
            liqTemp = (Liquidacion) itLiq.next();

            if (liqTemp.getIdLiquidacion().equals(idLiq)) {
                pago.setLiquidacion(liqTemp);

            }
        }

        //Se captura la justificacion del mayor valor
        if (turno.getSolicitud().getLiquidaciones() != null && turno.getSolicitud().getLiquidaciones().size() > 0) {
            if (turno.getSolicitud().getLiquidaciones().get((turno.getSolicitud().getLiquidaciones().size()) - 1) instanceof LiquidacionTurnoRegistro) {
                LiquidacionTurnoRegistro liqTrnoRegistro = new LiquidacionTurnoRegistro();
                Iterator itLiqReg = turno.getSolicitud().getLiquidaciones().iterator();
                while (itLiqReg.hasNext()) {
                    liqTrnoRegistro = (LiquidacionTurnoRegistro) itLiqReg.next();
                    if (liqTrnoRegistro.getIdLiquidacion().equals(idLiq)) {
                        justificacionMayorValor = liqTrnoRegistro.getJustificacionMayorValor();
                    }
                }
            }
        }

        Liquidacion liqPago = pago.getLiquidacion();
        liqPago.setSolicitud(solicitud);
        solicitud.setTurno(turno);
        Circulo circulo;
        circulo = evento.getCirculo();

        // obtener los datos completos del circulo; ------------------------------
        // para el recibo de certificado el dato en sesion no tiene el nit
        if ((null != circulo)) {

            if (null != circulo.getIdCirculo()) {
                CirculoPk circuloId = new CirculoPk(circulo.getIdCirculo());

                try {
                    circulo = forseti.getCirculo(circuloId);
                } catch (Throwable e) {
                    throw new EventoException(e.getMessage(), e);
                }
            }
        } else {
            circulo = new Circulo();
        }
        // -----------------------------------------------------------------------
        Calendar cFecha = Calendar.getInstance();
        boolean hasMultipleLiquid = false;

        if (turno.getSolicitud().getLiquidaciones() != null && turno.getSolicitud().getLiquidaciones().size() > 0) {
            hasMultipleLiquid = true;
        }

        if (pago != null && pago.getFecha() != null && hasMultipleLiquid) {
            cFecha.setTime(pago.getFecha());
        } else {
            if (turno != null && turno.getFechaInicio() != null) {
                cFecha.setTime(turno.getFechaInicio());
            }
        }

        String fechaImpresion = this.getFechaImpresion(cFecha);

        Pago pagoMasivo = evento.getPago();

//        DocumentoPago docPago;
//        List aplicaciones = pago.getAplicacionPagos();
//        if (aplicaciones != null) {
//            Iterator it = aplicaciones.iterator();
//            Banco banco = null;
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                    }
//
//                }
//            }
//
//        }
//                String idCr = null;
//        String canalRecaudo = null;
//        //        CARLOS
//        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
//        if (aplicaciones != null) {
//            Iterator it = aplicaciones.iterator();
//            Banco banco = null;
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());                        
//                        idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(((DocPagoGeneral) docPago).getIdCtp()));
//                        canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
//                        System.out.print("El canal recaudo en AN metodo gere es: " + circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        System.out.print("El canal recaudo en AN Pago es la: " + canalRecaudo );
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());                        
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                    }
//
//                }
////                ap.setDocumentoPago(docPago);
//            }
//
//        }
        

        ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);

        //TFS 3851: Si es un turno de registro de mayor valor, se debe ingresar la razón del cobro
        if (hasMultipleLiquid && evento.isEsPagoRegistro()) {
            impRec.setJustificacionMayorValor(justificacionMayorValor);
            //TFS 3853: SE DEBE CAMBIAR EL TITULO DEL RECIBO SI ES PAGO DE MAYOR VALOR
            impRec.setEsReciboPagoMayorValorRegistro(true);
        }

        //TFS 3833: Hay que diferenciar el recibo de certificados al de mayor valor
        if (evento.isEsPagoCertificadoMayorValor()) {
            impRec.setEsReciboCertificadoMayorValor(true);
        }
        impRec.setPagoMasivo(pagoMasivo);
        impRec.setEsMayorValorCorrecciones(isMayorValorCorreciones);
        if (!isMayorValorCorreciones) {
            //TFS 3854: EN EL RECIBO SE DEBE IMPRIMIR EL ID DEL USUARIO QUE REALIZO EL PAGO (CAJERO)
            if (hasMultipleLiquid && evento.isEsPagoRegistro()) {
                impRec.setUsuarioGeneraRecibo(String.valueOf(pago.getUsuario().getIdUsuario()));
            } else {
                impRec.setUsuarioGeneraRecibo(String.valueOf(user.getIdUsuario()));
            }

        }

        String uid = evento.getUID();

        //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
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

        Bundle bundle = new Bundle(impRec);
        int idImprimible = 0;

        try {
            if(hermod.getCopiaImp(impRec.getCirculo().getIdCirculo()).equals(CHermod.ACTIVE)){
                bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
            }
            // se manda a imprimir el recibo por el identificador unico de usuario
            if (ordenImpresion != null) {
                printJobs.enviar(uid, bundle, maxIntentos, espera, ordenImpresion, ordenActual);
            } else {
                printJobs.enviar(uid, bundle, maxIntentos, espera);
            }
        } catch (PrintJobsException t) {
            idImprimible = t.getIdImpresion();
            if (idImprimible == 0) {
                throw new EventoException("Se genero una excepción al imprimir el recibo. " + t.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            t.printStackTrace();
        }
        Log.getInstance().debug(ANPago.class, "TERMINO DE IMPRIMIR EL RECIBO");

        return idImprimible;
    }

    /**
     * Procesa el pago generando un turno y el imprimible
     *
     * @param evento
     * @param pago
     * @param estacion
     * @param user
     * @param impresora
     * @return Turno generado del procesamiento del pago
     * @throws Throwable
     */
    private HashMap generarPagoConTurno(EvnPago evento, Pago pago, Estacion estacion, Usuario user, String impresora) throws Throwable {
        Turno turno;
        HashMap hm = new HashMap();

        int idImprimible = 0;

        // Si el turno es de internet, se asignar el ciudadano a la solicitud
        if (evento.isTurnoCertificadosInternet()) {
            Solicitud sol = (Solicitud) evento.getSolicitud();
            hermod.updateSolicitud(sol);

        }
        if (evento.isAsignarEstacion()) {
            long tiempoIni = System.currentTimeMillis();
            turno = hermod.procesarPago(pago, estacion.getEstacionId(), impresora, user, null, true);
            this.escribirPrintLog("ANPago.generarPagoConTurno.procesarPago1", tiempoIni);
        } else {
            long tiempoIni = System.currentTimeMillis();
            turno = hermod.procesarPago(pago, estacion.getEstacionId(), impresora, user, null);
            this.escribirPrintLog("ANPago.generarPagoConTurno.procesarPago2", tiempoIni);
        }

        if (turno.isNacional() != evento.isTurnoNacional()) {
            turno.setNacional(evento.isTurnoNacional());
            hermod.actualizarMarcaTurnoNacional(turno);
        }

        Solicitud solicitud = turno.getSolicitud();
        List liquidaciones = solicitud.getLiquidaciones();
        //String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
        //pago.setNumRecibo(numRecibo);
        pago.getLiquidacion().getSolicitud().setTurno(turno);
        if (!(solicitud instanceof SolicitudCorreccion) && !(solicitud instanceof SolicitudDevolucion)) {
            /*
			 * se guarda la secuencia siguiente para mostar en la pantalla de
			 * cajero certificados
             */

            // Se envia el parametro del proceso del turno para hacer la logica de independencia de recibos.
            EstacionReciboPk estacionReciboID = new EstacionReciboPk();
            estacionReciboID.idEstacion = estacion.getEstacionId();
            long secuencia = hermod.getNextNumeroRecibo(estacionReciboID, user, turno.getIdProceso());
            String numRecibo = String.valueOf(secuencia);
            pago.setNumRecibo(numRecibo);
            if (turno != null && turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                hm.put(WebKeys.SECUENCIAL_RECIBO_ESTACION, Long.toString(secuencia + 1));
            }

            PagoPk pagoID = new PagoPk();
            pagoID.idLiquidacion = pago.getIdLiquidacion();
            pagoID.idSolicitud = pago.getIdSolicitud();
            hermod.setNumeroReciboPago(pagoID, numRecibo);
        }

        Circulo circulo;
        circulo = evento.getCirculo();

        // obtener los datos completos del circulo; ------------------------------
        // para el recibo de certificado el dato en sesion no tiene el nit
        if ((null != circulo)) {

            if (null != circulo.getIdCirculo()) {
                CirculoPk circuloId = new CirculoPk(circulo.getIdCirculo());

                circulo = forseti.getCirculo(circuloId);
            }
        } else {
            circulo = new Circulo();
        }
        // -----------------------------------------------------------------------
        Calendar cFecha = Calendar.getInstance();

        if (turno != null && turno.getFechaInicio() != null) {
            cFecha.setTime(turno.getFechaInicio());
        }

        String fechaImpresion = this.getFechaImpresion(cFecha);
        /**
         * @author : HGOMEZ ** @change : Se mueve la declaración de la variable
         * TipoCertificado ** a esta posición.. ** Caso Mantis : 11598
         */
        TipoCertificado tipoCertificado = null;
        
                        //        CARLOS

//        String idCr = null;
//        String canalRecaudo = null;
//        //        CARLOS
//        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
//        DocumentoPago docPago;
//        List aplicaciones = pago.getAplicacionPagos();
//        if (aplicaciones != null) {
//            Iterator it = aplicaciones.iterator();
//            Banco banco = null;
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());                        
//                        idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(((DocPagoGeneral) docPago).getIdCtp()));
//                        canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
//                        System.out.print("El canal recaudo en AN metodo gere es: " + circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        System.out.print("El canal recaudo en AN Pago es la: " + canalRecaudo );
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());                        
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                    }
//
//                }
////                ap.setDocumentoPago(docPago);
//            }
//
//        }

        ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
        Liquidacion liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
        /**
         * @author Fernando Padilla Velez
         * @change Acta - Requerimiento No 178 - Integración Gestión Documental
         * y SIR, Se agrega esta fragmento de codigo para la publicacion del
         * mensaje del turno creado.
         */
        if (liquidacion instanceof LiquidacionTurnoCertificado) {
            LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
            String idTipoCertificado = liquidaCert.getTipoCertificado().getIdTipoCertificado();
            impRec.setTipoCertificadoId(idTipoCertificado);

            /**
             * @author Fernando Padilla Velez
             * @change 6760: Acta - Requerimiento No 191 - Pantalla
             * Administrativa SGD, Se comentan estan lineas, ya que se realizó
             * refactoring al proceso y ya no son necesarias.
             */
            if (("PERTENENCIA").equals(liquidaCert.getTipoCertificado().getNombre())
                    || ("AMPLIACION_TRADICION").equals(liquidaCert.getTipoCertificado().getNombre())
                    || ("ANTIGUO_SISTEMA").equals(liquidaCert.getTipoCertificado().getNombre())) {
                SGD sgd = new SGD(turno, user.getUsername());
                sgd.enviarTurnoCertificado();
            }

        }/**
         * @author Fernando Padilla Velez
         * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa
         * SGD, Se borran estan lineas, ya que se realizó refactoring al proceso
         * y ya no son necesarias.
         */
        else if (liquidacion instanceof LiquidacionTurnoConsulta) {
            SGD sgd = new SGD(turno, user.getUsername());
            sgd.enviarTurnoConsulta();
        }
        // Bug 3479:
        // :: ProcId: Certificado/Consulta/Devolucion
        // (usuario que genera recibo pago)
        // jxpath-search:.
        if ((solicitud instanceof SolicitudCertificado)
                || (solicitud instanceof SolicitudConsulta)
                || (solicitud instanceof SolicitudDevolucion)) {

            // realizar busqueda de usuario
            String local_UsuarioGeneraRecibo = "";

            local_SearchImpl_jx:
            {
                // Cambio, no es el usuario de solicitud sino el usuario
                // que se envia para afectar el wf
                //
                gov.sir.core.negocio.modelo.Usuario usuario = user;

                local_UsuarioGeneraRecibo = print_FootUtils_BuildUserName(usuario);

            } // :searchImpl_jx

            ImprimibleRecibo imprimible;
            imprimible = impRec;
            imprimible.setUsuarioGeneraRecibo(local_UsuarioGeneraRecibo);

            if (solicitud instanceof SolicitudConsulta) {
                //imprimible.setTamanoCarta(true);
                SolicitudConsulta sconsulta = (SolicitudConsulta) solicitud;
                String idTipoConsulta = sconsulta.getTipoConsulta().getIdTipoConsulta();
                if (idTipoConsulta.equals(TipoConsulta.TIPO_EXENTO)) {
                    imprimible.setConsultaExenta(true);
                    imprimible.setDocumento(sconsulta.getDocumento());
                    impRec.setConsultaExenta(true);
                    impRec.setDocumento(sconsulta.getDocumento());
                }
            }

            if (solicitud instanceof SolicitudDevolucion) {
                imprimible.setTamanoCarta(true);

                if (solicitud.getTurnoAnterior() == null) {
                    ((SolicitudDevolucion) solicitud).setConsignaciones(((SolicitudDevolucion) evento.getSolicitud()).getConsignaciones());
                }

                //Se agregan las notas al turno
                //Agregar las notas informativas
                if (turno != null) {
                    List listaNotas = evento.getNotasInformativas();
                    if (listaNotas != null) {
                        TurnoPk idTurno = new TurnoPk();
                        idTurno.anio = turno.getAnio();
                        idTurno.idCirculo = turno.getIdCirculo();
                        idTurno.idProceso = turno.getIdProceso();
                        idTurno.idTurno = turno.getIdTurno();

                        for (int j = 0; j < listaNotas.size(); j++) {
                            Nota notaInformativa = (Nota) listaNotas.get(j);
                            hermod.addNotaToTurno(notaInformativa, idTurno);
                        }
                    }
                }
            }
        } // :if

        if (!evento.isOmitirRecibo()) {

            String uid = evento.getUID();

            //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
            //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
            int maxIntentos;
            int espera;

            //INGRESO DE INTENTOS DE IMPRESION
            try {
                long tiempoIni = System.currentTimeMillis();
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
                this.escribirPrintLog("ANPago.generarPagoConTurno.obtenerVariablesImpresion", tiempoIni);
            } catch (Throwable t) {
                Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                maxIntentos = intentosDefault.intValue();

                Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                espera = esperaDefault.intValue();
            }
            if (evento.isTurnoCertificadosInternet()) {
                impRec.setTurnoInternet(true);

            }

            /**
             * @author : HGOMEZ ** @change : Se valida que la variable
             * tipoCertificado sea diferente de null ** y se le asigna a la
             * variable tipoCertificadoNombre de la clase ImprimibleRecibo.java.
             * ** Caso Mantis : 11598
             */
            if (turno.getIdProceso() == 1 && tipoCertificado != null && tipoCertificado.getNombre() != null
                    && tipoCertificado.getIdTipoCertificado() != null && (tipoCertificado.getIdTipoCertificado().equals("1")
                    || tipoCertificado.getIdTipoCertificado().equals("2") || tipoCertificado.getIdTipoCertificado().equals("3")
                    || tipoCertificado.getIdTipoCertificado().equals("11") || tipoCertificado.getIdTipoCertificado().equals("13"))) {
                impRec.setTipoCertificadoNombre(tipoCertificado.getNombre());
            }
            Bundle bundle = new Bundle(impRec);
            String copyActive = hermod.getCopiaImp(liquidacion.getCirculo());
            if(copyActive.equals(CHermod.ACTIVE)){
                bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
            }
            try {
                long tiempoIni = System.currentTimeMillis();
                //se manda a imprimir el recibo por el identificador unico de usuario
                int numVeces = 1;
                if ((solicitud instanceof SolicitudDevolucion) && !bundle.getImprimible().getTipoImprimible().equals(CTipoImprimible.RECIBO)) {
                    numVeces = 2;
                }
                for (int i = 0; i < numVeces; i++) {
                    printJobs.enviar(uid, bundle, maxIntentos, espera);
                    this.escribirPrintLog("ANPago.generarPagoConTurno.printJobs.enviar", tiempoIni);
                }
            } catch (PrintJobsException t) {
                idImprimible = t.getIdImpresion();
                t.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }

            if (evento.isTurnoCertificadosInternet()) {

//                DocumentoPago docPago;
//                aplicaciones = pago.getAplicacionPagos();
//                if (aplicaciones != null) {
//                    Iterator it = aplicaciones.iterator();
//                    Banco banco = null;
//                    while (it.hasNext()) {
//                        AplicacionPago ap = (AplicacionPago) it.next();
//                        docPago = ap.getDocumentoPago();
//                        if (docPago instanceof DocPagoGeneral) {
//                            try {
//                                CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
//                                ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                                ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                                if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                                    banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                } else {
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                }
//
//                            } catch (Throwable ex) {
//                                Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                            }
//
//                        }
//                    }
//
//                }
////                        String idCr = null;
////        String canalRecaudo = null;
////        //        CARLOS
////        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
////        DocumentoPago docPago;
////        List aplicaciones = pago.getAplicacionPagos();
//        if (aplicaciones != null) {
//            Iterator it = aplicaciones.iterator();
//            Banco banco = null;
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());                        
//                        idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(((DocPagoGeneral) docPago).getIdCtp()));
//                        canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
//                        System.out.print("El canal recaudo en AN metodo gere es: " + circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        System.out.print("El canal recaudo en AN Pago es la: " + canalRecaudo );
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());                        
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                    }
//
//                }
////                ap.setDocumentoPago(docPago);
//            }
//
//        }

                ImprimibleRecibo impRecPdf = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
                impRecPdf.setTipoCertificadoId(impRec.getTipoCertificadoId());
                impRecPdf.setTienematriculasSinMigrar(impRec.isTienematriculasSinMigrar());

                impRecPdf.setMatriculaSinMigrar(impRec.getMatriculaSinMigrar());
                impRecPdf.setMayorExtension(impRec.isMayorExtencion());
                impRecPdf.setUsuarioGeneraRecibo(impRec.getUsuarioGeneraRecibo());

                impRecPdf.setTurnoInternet(true);

                impRecPdf.setPrintWatermarkEnabled(true);
                impRecPdf.setPdf(true);
                impRecPdf.setTamanoCarta(true);

                Bundle bundle2 = new Bundle(impRecPdf);
                //bundle2.setNumeroCopias(evento.getNumeroCopias());
                String copyActive2 = hermod.getCopiaImp(impRecPdf.getCirculo().getIdCirculo());
                if(copyActive.equals(CHermod.ACTIVE)){
                     bundle2.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                }
                printJobs.agregarImprimiblePDF(bundle2, true);

                ImprimiblePdf imprimiblePdf = printJobs.getImprimiblePdf(turno.getIdWorkflow());
                hm.put(WebKeys.IMPRIMIBLE_PDF, imprimiblePdf);
            }

        }

        //verifica si el certificado es de mayor extension.
        if (solicitud instanceof SolicitudCertificado) {
            boolean esMayorExtension = false;
            long numAnota = -1;
            FolioPk fid = this.getFolio_ID(solicitud);

            if (fid != null) {
                numAnota = forseti.getCountAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null);
                esMayorExtension = forseti.mayorExtensionFolio(fid.idMatricula);
                impRec.setTienematriculasSinMigrar(false);
            } else {
                Log.getInstance().error(ANPago.class, "NO FUE POSIBLE DETERMINAR SI EL FOLIO ES DE MAYOR EXTENSION");

                //se asume false, pero no se sabe.
                esMayorExtension = false;

                //Se debe preguntar si tiene folio migrado
                boolean isMigradoFolio = evento.isTieneMatriculasSinMigrar();
                if (isMigradoFolio) {
                    //Se recorre la lista
                    List foliosMigrados = evento.getMatriculasSinMigrar();
                    List turnosFoliosMig = new ArrayList();
                    String matSinMigrar = "";
                    for (int i = 0; i < foliosMigrados.size(); i++) {
                        String matri = (String) foliosMigrados.get(i);
                        TurnoFolioMig turnoFolMig = new TurnoFolioMig();
                        turnoFolMig.setIdFolio(matri);
                        matSinMigrar = matri;
                        turnoFolMig.setIdTurno(turno.getAnio() + "-" + turno.getIdTurno());
                        turnoFolMig.setIdProceso(turno.getIdProceso());
                        turnoFolMig.setIdCirculo(turno.getIdCirculo());
                        turnoFolMig.setAnulado(false);
                        turnoFolMig.setCreadoSir(true);
                        turnosFoliosMig.add(turnoFolMig);
                    }
                    hermod.addTurnosFolioMigToTurno(turno, turnosFoliosMig);

                    impRec.setTienematriculasSinMigrar(true);
                    impRec.setMatriculaSinMigrar(matSinMigrar);
                }

            }

            impRec.setMayorExtension(esMayorExtension);

            long tiempoInicial = System.currentTimeMillis();

            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
            Log.getInstance().debug(ANPago.class, "(ANTES AN PAGO CERTIFICADO INMEDIATO)" + turno.getIdWorkflow() + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

            SolicitudCertificado solCert = (SolicitudCertificado) solicitud;
            if (solCert.getLiquidaciones().size() == 1) {

                LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                tipoCertificado = liqCert.getTipoCertificado();
                if ((tipoCertificado.getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID)
                        || tipoCertificado.getIdTipoCertificado().equals(CTipoCertificado.TIPO_NACIONAL_ID)
                        || tipoCertificado.getIdTipoCertificado().equals(CTipoCertificado.TIPO_INTERNET_ID)) && !esMayorExtension) {
                    long tiempoIni = System.currentTimeMillis();
                    this.imprimirCertificado(turno, evento.getUsuarioSIR(), circulo.getIdCirculo(), evento.getImpresora());
                    this.escribirPrintLog("ANPago.generarPagoConTurno.imprimirCertificado", tiempoIni);
                }

            }

            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
            Log.getInstance().debug(ANPago.class, "(DESPUES AN PAGO CERTIFICADO INMEDIATO)" + turno.getIdWorkflow() + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        }

     
        hm.put(WebKeys.TURNO, turno);
        hm.put(WebKeys.ID_IMPRIMIBLE, new Integer(idImprimible));
        return hm;
    }

    private void pagoFocotopia_imprimibleRecibo(gov.sir.core.negocio.modelo.Circulo circulo, gov.sir.core.negocio.modelo.Pago pago, gov.sir.core.negocio.modelo.Turno turno, gov.sir.core.negocio.modelo.Liquidacion liquidacion, gov.sir.core.negocio.modelo.SolicitudFotocopia solicitud, List pagoAplicaciones, String sessionId, String temp_UsuarioGeneraRecibo) throws Throwable {
        // TODO: TEST: pago.getAplicacionPagos()

        String fechaImpresion = this.getFechaImpresion();
        /*

		Pago pago = new gov.sir.core.negocio.modelo.imprimibles.util.DefaultPago( liquidacion, turno );

		ImprimibleRecibo impRec;
		impRec = new ImprimibleFotocopiaComprobanteSolicitud( pago, circulo, fechaImpresion );
		// impRec = new ImprimibleRecibo( evento.getTurno(), circulo, fechaImpresion );
         */

        // ImprimibleRecibo impRec;
        // impRec = new ImprimibleRecibo( turno, liquidacion, pago, solicitud, circulo, fechaImpresion );
        Vector tempListOfAplicaciones = new java.util.Vector();
        Iterator iterator = pagoAplicaciones.iterator();

        // solo wrapping para enviarlos al cliente
        for (; iterator.hasNext();) {
            tempListOfAplicaciones.add(iterator.next());
        }

        ImprimibleFotocopiaPago_SolicitudData impRec;
        impRec = new ImprimibleFotocopiaPago_SolicitudData(turno, solicitud, circulo, liquidacion, pago, tempListOfAplicaciones, CTipoImprimible.RECIBO);
        impRec.setFechaImpresionServidorFormatted(fechaImpresion);

        // Bug 3479
        // :: ProcId: Correcciones
        // (usuario que genera solicitud)
        // jxpath-search:./solicitud/usuario/nombre
        ImprimibleFotocopiaPago_SolicitudData imprimible;
        imprimible = impRec;

        // realizar busqueda de usuario
        String local_UsuarioGeneraRecibo = "";

        local_SearchImpl_jx:
        {

            local_UsuarioGeneraRecibo = temp_UsuarioGeneraRecibo;

        } // :searchImpl_jx

        imprimible.setUsuarioGeneraRecibo(local_UsuarioGeneraRecibo);

        String uid = sessionId;

        //IMPRIMIR EL RECIBO
        String[] legals = null;
        impRec.setFootLegendText(legals);

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

        Bundle bundle = new Bundle(impRec);

        try {
            String copyActive = hermod.getCopiaImp(circulo.getIdCirculo());
            if(copyActive.equals(CHermod.ACTIVE)){
                 bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
            }
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle, maxIntentos, espera);

            //si ok-->termina el ciclo.
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    // block:eof

    /**
     * Este metodo hace el llamado al negocio para que se procese el pago de una
     * liquidacion
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnOficinas</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespOficinas</CODE>
     * @throws EventoException
     */
    private EventoRespuesta procesarPagoMasivos(EvnPago evento) throws EventoException {
        validarPago(evento);

        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: EN PROCESAR PAGO MASIVOS");

        Turno turno = null;
        Turno turnoIndividuales = null;
        Estacion estacion = evento.getEstacion();
        Usuario user = evento.getUsuarioSIR();
        Rol rol = evento.getRol();
        String impresora = evento.getImpresora();
        Pago pagoCertificado = null;
        String id_matricula = "";
        List matriculas = new ArrayList();
        Hashtable validaciones = evento.getValidacionesMasivos();
        boolean crearNota = false;
        boolean imprimirRecibo = true;

        if (validaciones == null) {
            validaciones = new Hashtable();
        }

        //EL PAGO ES UTILIZADO PARA LIQUIDAR EL PROCESO DE CERTIFICADOS MASIVOS.
        Pago pago = evento.getPago();
        pago.setUsuario(user);

        Liquidacion liquidacion = pago.getLiquidacion();

        Solicitud sol = liquidacion.getSolicitud();

        pago.setLiquidacion(liquidacion);
        pago.setIdSolicitud(sol.getIdSolicitud());

        Hashtable ht = new Hashtable();

        try {
            validarPago(evento);
            pago.setUsuario(user);

            turno = hermod.procesarPago(pago, estacion.getEstacionId(), impresora, user, rol);

            Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Se va a realizar el procesamiento del Turno, " + turno.getIdWorkflow());

            try {
                //Asociar al turno las notas informativas definidas antes de la creación del turno. (Si existen).
                List listaNotasInformativas = evento.getListaNotasSinTurno();

                if (listaNotasInformativas != null) {
                    TurnoPk idTurno = new TurnoPk();
                    idTurno.anio = turno.getAnio();
                    idTurno.idCirculo = turno.getIdCirculo();
                    idTurno.idTurno = turno.getIdTurno();
                    idTurno.idProceso = turno.getIdProceso();

                    for (int i = 0; i < listaNotasInformativas.size(); i++) {
                        Nota notaTurno = (Nota) listaNotasInformativas.get(i);
                        hermod.addNotaToTurno(notaTurno, idTurno);
                    }
                }

                Solicitud solicitud = turno.getSolicitud();
                List liquidaciones = solicitud.getLiquidaciones();
                //String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();

                /*EstacionRecibo.ID estacionReciboID = new EstacionRecibo.ID();
                estacionReciboID.idEstacion = estacion.getEstacionId();
                String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionReciboID));
				pago.setNumRecibo(numRecibo);

                Pago.ID pagoID = new Pago.ID();
                pagoID.idLiquidacion = pago.getIdLiquidacion();
                pagoID.idSolicitud = pago.getIdSolicitud();
                hermod.setNumeroReciboPago(pagoID, numRecibo);*/
                pago.getLiquidacion().getSolicitud().setTurno(turno);

                Circulo circulo = evento.getCirculo();
                String fechaImpresion = this.getFechaImpresion();

                if (liquidacion instanceof LiquidacionTurnoCertificadoMasivo) {
                    LiquidacionTurnoCertificadoMasivo liqMas = (LiquidacionTurnoCertificadoMasivo) liquidacion;
                    /**
                     * @author : Julio Alcázar Rivas
                     * @change : cambio de acceso long numRecibo y String year
                     * al if externo Caso Mantis : 000941
                     */
                    long numRecibo;
                    String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                    if (liqMas.getTipoTarifa().equals(CTipoTarifa.EXENTO)) {
                        imprimirRecibo = false;
                        /**
                         * @author : Julio Alcázar Rivas
                         * @change : Se agrega un secuencial a los recibos
                         * Masivos Exentos Caso Mantis : 000941
                         */
                        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: El Turno es de Tipo EXENTO se va a imprimir masivos");
                        numRecibo = hermod.getSecuencialMasivosExento(circulo.getIdCirculo(), year);

                    } else {
                        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: El Turno es de Tipo NORMAL se va a imprimir todo");
                        numRecibo = hermod.getSecuencialMasivos(circulo.getIdCirculo(), year);
                    }
                    pago.setNumRecibo(Long.toString(numRecibo));
                    Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Numero de Recibo del masivo: " + numRecibo);

                    PagoPk pagoID = new PagoPk();
                    pagoID.idLiquidacion = pago.getIdLiquidacion();
                    pagoID.idSolicitud = pago.getIdSolicitud();
                    hermod.setNumeroReciboPago(pagoID, Long.toString(numRecibo));
                }
                /**
                 * @author : Julio Alcázar Rivas
                 * @change : Se permite la impresion del recibo Masivos Exentos
                 * Caso Mantis : 000941
                 */
                //if(imprimirRecibo){
//
//                DocumentoPago docPago;
//                List aplicaciones = pago.getAplicacionPagos();
//                if (aplicaciones != null) {
//                    Iterator it = aplicaciones.iterator();
//                    Banco banco = null;
//                    while (it.hasNext()) {
//                        AplicacionPago ap = (AplicacionPago) it.next();
//                        docPago = ap.getDocumentoPago();
//                        if (docPago instanceof DocPagoGeneral) {
//                            try {
//                                CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
//                                ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                                ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                                if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                                    banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                } else {
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                }
//
//                            } catch (Throwable ex) {
//                                Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                            }
//
//                        }
//                    }
//
//                }
//                
//                        String idCr = null;
//        String canalRecaudo = null;
//        //        CARLOS
//        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
////        DocumentoPago docPago;
////        List aplicaciones = pago.getAplicacionPagos();
//        if (aplicaciones != null) {
//            Iterator it = aplicaciones.iterator();
//            Banco banco = null;
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());                        
//                        idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(((DocPagoGeneral) docPago).getIdCtp()));
//                        canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
//                        System.out.print("El canal recaudo en AN metodo gere es: " + circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        System.out.print("El canal recaudo en AN Pago es la: " + canalRecaudo );
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());                        
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                    }
//
//                }
////                ap.setDocumentoPago(docPago);
//            }
//
//        }

                ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
                impRec.setValidaciones(evento.getValidacionesMasivos());
                impRec.setUsuarioGeneraRecibo("" + evento.getUsuarioSIR().getIdUsuario());

                // Bug 5324
                if (solicitud instanceof SolicitudCertificadoMasivo) {
                    impRec.setTamanoCarta(true);
                } // if 	

                //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
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

                Bundle bundle = new Bundle(impRec);
                String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                if(copyActive.equals(CHermod.ACTIVE)){
                    bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                }
                String cid = circulo.getIdCirculo();

                try {
                    //se manda a imprimir el recibo por el identificador unico de usuario
                    printJobs.enviar(cid, bundle, maxIntentos, espera);

                } catch (Throwable t) {
                    t.printStackTrace();
                }
                //}

            } catch (Throwable t) {
                Log.getInstance().error(ANPago.class, t.getMessage());
            }

            int solicitudesTotales = 0;
            int solicitudesProcesadas = 0;
            int solicitudesNOProcesadas = 0;
            if ((liquidacion != null) && liquidacion instanceof LiquidacionTurnoCertificadoMasivo) {

                OrdenImpresion ordenImpresion = new OrdenImpresion();
                int ordenActual = 1;

                SolicitudCertificadoMasivo solicitudCertificadoMasivo = (SolicitudCertificadoMasivo) liquidacion.getSolicitud();

                Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Va a iniciar procesamiento de las Solicitudes Individuales");
                if ((solicitudCertificadoMasivo != null) && !solicitudCertificadoMasivo.getSolicitudesHijas().isEmpty()) {
                    solicitudesTotales = solicitudCertificadoMasivo.getSolicitudesHijas().size();
                    Iterator it = solicitudCertificadoMasivo.getSolicitudesHijas().iterator();
                    //Inicio de toma de tiempos
                    long start_time = System.nanoTime();
                    while (it.hasNext()) {
                        id_matricula = "";
                        ht = new Hashtable();
                        ht.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioSIR());
                        ht.put(CEstacion.ESTACION_ID, evento.getEstacion().getEstacionId());

                        if (rol != null) {
                            ht.put(CRol.ID_ROL, rol.getRolId());
                        }

                        SolicitudAsociada solicitudAsociada = (SolicitudAsociada) it.next();
                        SolicitudCertificado solicitudCertificado = (SolicitudCertificado) solicitudAsociada.getSolicitudHija();
                        if (solicitudCertificado.getMatriculaNoExistente() != null) {
                            matriculas.add(solicitudCertificado.getMatriculaNoExistente());
                        } else {
                            matriculas.add(((SolicitudFolio) solicitudCertificado.getSolicitudFolios().get(0)).getIdMatricula());
                        }

                        try {
                            Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud());

                            id_matricula = solicitudCertificado.getMatriculaNoExistente();

                            if (((id_matricula == null) || id_matricula.equals("")) && (solicitudCertificado != null) && !solicitudCertificado.getSolicitudFolios().isEmpty()) {
                                SolicitudFolio solFolio = (SolicitudFolio) solicitudCertificado.getSolicitudFolios().get(0);
                                id_matricula = solFolio.getIdMatricula();
                                Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud() + " Matricula " + id_matricula);
                            }

                            if ((solicitudCertificado != null) && !solicitudCertificado.getLiquidaciones().isEmpty()) {
                                LiquidacionTurnoCertificado liquidacionCertificado = (LiquidacionTurnoCertificado) solicitudCertificado.getLiquidaciones().get(0);

                                Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud() + " Se esta iniciando el proceso de Pago");
                                //SE SACA LA LIQUIDACION DEL CERTIFICADO INDIVIDUAL Y SE LLAMA AL MÉTODO PROCESAR PAGO

                                // ----------------------------------------------------------
                                // Bug 5404:;
                                // Esta colocando efectivo; deberica colocar pago asociado.
                                DocumentoPago docPago;
                                double tmpDocPagoValor;

                                // tmpDocPagoValor = liquidacionCertificado.getValor();
                                // TODO: se debe dejar con valor 0 el pago heredado?							
                                tmpDocPagoValor = 0d;

                                docPago = new DocPagoHeredado(tmpDocPagoValor);

                                // ----------------------------------------------------------
                                //DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacionCertificado.getValor());
                                AplicacionPago appEfectivo = new AplicacionPago();
                                appEfectivo.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
                                appEfectivo.setIdSolicitud(solicitudCertificado.getIdSolicitud());
                                appEfectivo.setValorAplicado(liquidacionCertificado.getValor());
                                appEfectivo.setDocumentoPago(docPago);

                                pagoCertificado = new Pago(liquidacionCertificado, null);
                                pagoCertificado.addAplicacionPago(appEfectivo);
                                pagoCertificado.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
                                pagoCertificado.setIdSolicitud(solicitudCertificado.getIdSolicitud());
                                pagoCertificado.setLiquidacion(liquidacionCertificado);
                                pagoCertificado.setUsuario(evento.getUsuarioSIR());

                                if ((id_matricula != null) && !id_matricula.equals("")) {
                                    List valid = (List) validaciones.get(id_matricula);

                                    if ((valid != null) && (valid.size() > 0)) {
                                        //ht.put(id_matricula, valid);
                                        ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
                                    }

                                    if (valid == null) {
                                        valid = new ArrayList();
                                        ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
                                    }
                                }

                                Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud() + " Se esta Procesando Pago");
                                boolean creoTurno = false;
                                try {
                                    Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud() + " Se va a crear el Turno Individual");
                                    turnoIndividuales = hermod.procesarPagoMasivos(pagoCertificado, ht);
                                    if (imprimirRecibo) {
                                        imprimirRecibo(turnoIndividuales, evento, pagoCertificado, estacion, user, null, false, ordenImpresion, ordenActual);
                                        ordenActual++;
                                        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Turno Individual " + turnoIndividuales.getIdWorkflow() + " Se esta Imprimiendo el REcibo");
                                    } else {
                                        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Turno Individual " + turnoIndividuales.getIdWorkflow() + " No se va a imprimir recibo");
                                    }

                                    if (validaciones.get(id_matricula) == null) {

                                        imprimirCertificado(turnoIndividuales, user, evento.getCirculo().getIdCirculo(), evento.getImpresora());
                                        Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Procesando Turno: " + turno.getIdWorkflow() + " Turno Individual " + turnoIndividuales.getIdWorkflow() + " Se esta Imprimiendo el Certificado");
                                    } else {
                                        crearNota = true;
                                        Log.getInstance().debug(ANPago.class, "*********ERROR DURANTE VALIDACION:" + validaciones.get(id_matricula));
                                    }
                                    creoTurno = true;
                                } catch (Throwable t) {
                                    Log.getInstance().debug(ANPago.class, "ERROR AL CREAR EL TURNO" + t.getMessage());
                                }
                                if (creoTurno) {
                                    solicitudesProcesadas = solicitudesProcesadas + 1;
                                } else {
                                    solicitudesNOProcesadas = solicitudesNOProcesadas + 1;
                                }
                            }
                        } catch (Exception ee) {
                            solicitudesNOProcesadas = solicitudesNOProcesadas + 1;
                            Log.getInstance().error(ANPago.class, "ERROR Al Procesar la Solicitud" + ee.getMessage());
                            if (solicitudCertificado != null && solicitudCertificado.getIdSolicitud() != null) {
                                Log.getInstance().error(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: ERROR al Procesar la Solicitud Individual: " + turno.getIdWorkflow() + " Solicitud Certificado: " + solicitudCertificado.getIdSolicitud());
                            }
                        }
                    }
                    //fin toma de tiempos
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time) / 1e6;
                    System.out.println("El tiempo de la generación pdfs en milisegundos: " + difference);
                }
            }

            /**
             * @author: Cesar Ramírez
             * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF Creación del
             * archivo ZIP de los PDF generados.
             *
             */
            {
                FileInputStream fis = null;
                FileOutputStream fos = null;

                try {
                    String rutaTempPDF = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
                    String ruta = rutaTempPDF + "/" + turno.getIdWorkflow();
                    File dir = new File(ruta);

                    File[] matchingFiles = dir.listFiles(new FilenameFilter() {

                        public boolean accept(File dir, String fileName) {
                            return fileName.endsWith(".pdf");
                        }
                    });

                    if (matchingFiles.length > 0) {
                        String nombreArchivoZip = turno.getIdWorkflow() + ".zip";
                        fos = new FileOutputStream(new File(rutaTempPDF + "/" + nombreArchivoZip));
                        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(fos));

                        for (File input : matchingFiles) {
                            fis = new FileInputStream(input);
                            ZipEntry ze = new ZipEntry(input.getName());
                            zipOut.putNextEntry(ze);
                            byte[] tmp = new byte[4 * 1024];
                            int size = 0;
                            while ((size = fis.read(tmp)) != -1) {
                                zipOut.write(tmp, 0, size);
                            }
                            zipOut.flush();
                            fis.close();
                            input.delete();
                        }
                        zipOut.close();
                        if (dir.exists()) {
                            dir.delete();
                        }
                    }
                } catch (FileNotFoundException eff) {
                    Log.getInstance().error(ANPago.class, "ERROR GENERANDO ZIP: " + eff.getMessage());
                } catch (ZipException ez) {
                    Log.getInstance().error(ANPago.class, "ERROR GENERANDO ZIP: " + ez.getMessage());
                } catch (IOException eio) {
                    Log.getInstance().error(ANPago.class, "ERROR GENERANDO ZIP (IO): " + eio.getMessage());
                } catch (Exception e) {
                    Log.getInstance().error(ANPago.class, "ERROR al generar el archivo ZIP: " + e.getMessage());
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception ex) {
                        Log.getInstance().error(ANPago.class, "ERROR al generar el archivo ZIP: " + ex.getMessage());
                    }
                }
            }

            Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Se proceso el Turno: " + turno.getIdWorkflow() + " Solicitud Totales: " + solicitudesTotales);
            Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Se proceso el Turno: " + turno.getIdWorkflow() + " Solicitud Procesadas: " + solicitudesProcesadas);
            Log.getInstance().debug(ANPago.class, "@@lOGS_MASIVOS.ANPago.procesarPagoMasivos: Se proceso el Turno: " + turno.getIdWorkflow() + " Solicitud No Procesadas: " + solicitudesNOProcesadas);

            if (crearNota) {
                ProcesoPk id = new ProcesoPk();
                id.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS_MASIVOS);
                TipoNota tNota = (TipoNota) hermod.getTiposNotaProceso(id, CFase.CER_CERTIFICADOS_MASIVOS_2).get(1);
                Nota nota = new Nota();
                nota.setTiponota(tNota);
                nota.setUsuario(user);
                nota.setDescripcion(obtererErroresNotaInformativa(validaciones, matriculas));
                TurnoPk idTurno = new TurnoPk();
                idTurno.anio = turno.getAnio();
                idTurno.idCirculo = turno.getIdCirculo();
                idTurno.idTurno = turno.getIdTurno();
                idTurno.idProceso = turno.getIdProceso();
                hermod.addNotaToTurno(nota, idTurno);
                if (evento.isImprimirNotaCertMasivoExento()) {
                    Turno turnoAux = hermod.getTurnobyWF(turno.getIdWorkflow());

                    Hashtable htImp = new Hashtable();

                    String automatica = hermod.getTipoFase(turnoAux.getIdFase());
                    htImp.put(turnoAux.getIdFase(), automatica);

                    Nota notaImprimible = (Nota) turnoAux.getNotas().get(turnoAux.getNotas().size() - 1);
                    Vector notas = new Vector();
                    notas.add(notaImprimible);
                    String fechaImpresion = this.getFechaImpresion();
                    ImprimibleNotaInformativa impNota = new ImprimibleNotaInformativa(notas, turnoAux.getSolicitud().getCirculo().getNombre(), turnoAux.getIdWorkflow(), null, htImp, evento.getUsuarioSIR(), fechaImpresion, CTipoImprimible.NOTA_INFORMATIVA);
                    this.imprimirNotaInformativa(impNota, evento.getCirculo().getIdCirculo());
                }
            }
        } /*catch (HermodException e) {
		        throw new PagoNoProcesadoException("Ocurrio un error procesando el pago (hermod)", e);
		}
		catch (ForsetiException e) {
		        throw new PagoNoProcesadoException("Ocurrio un error procesando el pago (forseti)", e);
		}
		catch (PrintJobsException e) {
		        if (turno != null) {
		                throw new ErrorImpresionException("No se pudo imprimir el recibo:" + e.getMessage(), turno.getIdWorkflow());
		        }
		}*/ catch (Throwable e) {
            Log.getInstance().error(ANPago.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException("Excepcion inesperada", e);
        }

        EvnRespPago evRespuesta = new EvnRespPago(turno);

        return evRespuesta;
    }

    /**
     * Este metodo hace el llamado al negocio para que se procese el pago de una
     * liquidacion
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnOficinas</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespOficinas</CODE>
     * @throws EventoException
     */
    private EventoRespuesta procesarPagoCertificadosAsociadosTurno(EvnPago evento) throws EventoException {
        validarPago(evento);

        Log.getInstance().debug(ANPago.class, "ENTRO EN PROCESAR PAGO CERTIFICADOS ASOCIADOS A UN TURNO YA RADICADO");

        Turno turno = evento.getTurno();
        Turno turnoCert = null;
        Turno turnoIndividuales = null;
        Estacion estacion = evento.getEstacion();
        Usuario user = evento.getUsuarioSIR();
        Rol rol = evento.getRol();
        String impresora = evento.getImpresora();
        Pago pagoCertificado = null;
        String id_matricula = "";
        Hashtable validaciones = evento.getValidacionesMasivos();
        List liquidacionesCertificadosAsociados = evento.getLiquidacionesCertificadosAsociados();

        if (validaciones == null) {
            validaciones = new Hashtable();
        }

        Pago pagoCert = evento.getPago();
        pagoCert.setUsuario(user);
        AplicacionPago ap = (AplicacionPago) pagoCert.getAplicacionPagos().get(0);/**/

        //SE RECORRE EL LISTADO DE LIQUIDACION DE CERTIFICADOS ASOCIADOS
        Iterator it = liquidacionesCertificadosAsociados.iterator();
        while (it.hasNext()) {
            LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) it.next();
            SolicitudCertificado solCert = (SolicitudCertificado) liqCert.getSolicitud();

            try {

                AplicacionPago appEfectivo = new AplicacionPago();
                appEfectivo.setIdLiquidacion(liqCert.getIdLiquidacion());
                appEfectivo.setIdSolicitud(solCert.getIdSolicitud());
                appEfectivo.setValorAplicado(liqCert.getValor());
                appEfectivo.setDocumentoPago(ap.getDocumentoPago());

                pagoCertificado = new Pago(liqCert, null);
                pagoCertificado.addAplicacionPago(appEfectivo);
                pagoCertificado.setIdLiquidacion(liqCert.getIdLiquidacion());
                pagoCertificado.setIdSolicitud(solCert.getIdSolicitud());
                pagoCertificado.setLiquidacion(liqCert);
                pagoCertificado.setUsuario(evento.getUsuarioSIR());

                validarPago(pagoCertificado, 0);
                turnoCert = hermod.procesarPago(pagoCertificado, estacion.getEstacionId(), null, user, rol);

                Circulo circulo = evento.getCirculo();
                String fechaImpresion = this.getFechaImpresion();
                
//                        String idCr = null;
//        String canalRecaudo = null;
//                //        CARLOS
//                JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
//                DocumentoPago docPago;
//                List aplicaciones = pagoCertificado.getAplicacionPagos();
//                if (aplicaciones != null) {
//                    Iterator et = aplicaciones.iterator();
//                    Banco banco = null;
//                    while (it.hasNext()) {
//                        AplicacionPago ep = (AplicacionPago) et.next();
//                        docPago = ep.getDocumentoPago();
//                        if (docPago instanceof DocPagoGeneral) {
//                            try {
//                                CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());                        
//                                idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(((DocPagoGeneral) docPago).getIdCtp()));
//                                canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
//                                System.out.print("El canal recaudo en AN metodo gere es: " + circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                                System.out.print("El canal recaudo en AN Pago es la: " + canalRecaudo );
//                                ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());                        
//                                ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                                if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                                    banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                } else {
//                                    ((DocPagoGeneral) docPago).setBanco(banco);
//                                }
//
//                            } catch (Throwable ex) {
//                                Log.getInstance().debug(ANPago.class, "ERROR" + ex.getMessage());
//                            }
//
//                        }
//        //                ap.setDocumentoPago(docPago);
//                    }
//
//                }
                
                ImprimibleRecibo impRec = new ImprimibleRecibo(pagoCertificado, circulo, fechaImpresion, CTipoImprimible.RECIBO);
                impRec.setValidaciones(evento.getValidacionesMasivos());
                impRec.setUsuarioGeneraRecibo("" + evento.getUsuarioSIR().getIdUsuario());

                //SE ASOCIA LA SOLICITUD CERTIFICADO A LA SOLICITUD DE REGISTRO
                hermod.addSolicitudHija(turno.getSolicitud(), liqCert.getSolicitud());

                //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
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

                    Bundle bundle = new Bundle(impRec);
                    String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                    if(copyActive.equals(CHermod.ACTIVE)){
                        bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                    }
                    String cid = circulo.getIdCirculo();

                    try {
                        //se manda a imprimir el recibo por el identificador unico de usuario
                        printJobs.enviar(cid, bundle, maxIntentos, espera);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                } catch (Throwable t) {
                    Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                    maxIntentos = intentosDefault.intValue();

                    Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                    espera = esperaDefault.intValue();
                }
            } catch (Throwable e) {
                Log.getInstance().error(ANPago.class, "Ha ocurrido una excepcion inesperada ", e);
                throw new EventoException("Excepcion inesperada", e);
            }

        }

        /*
		//EL PAGO ES UTILIZADO PARA LIQUIDAR EL PROCESO DE CERTIFICADOS MASIVOS.
		Pago pago = evento.getPago();
		pago.setUsuario(user);

		Liquidacion liquidacion = pago.getLiquidacion();

		Solicitud sol = liquidacion.getSolicitud();

		pago.setLiquidacion(liquidacion);
		pago.setIdSolicitud(sol.getIdSolicitud());

		Hashtable ht = new Hashtable();

		try {
			validarPago(evento);
			pago.setUsuario(user);

			turno = hermod.procesarPago(pago, estacion.getEstacionId(), impresora, user, rol);

			try {
				//Asociar al turno las notas informativas definidas antes de la creación del turno. (Si existen).
				List listaNotasInformativas = evento.getListaNotasSinTurno();

				if (listaNotasInformativas != null) {
					Turno.ID idTurno = new Turno.ID();
					idTurno.anio = turno.getAnio();
					idTurno.idCirculo = turno.getIdCirculo();
					idTurno.idTurno = turno.getIdTurno();
					idTurno.idProceso = turno.getIdProceso();

					for (int i = 0; i < listaNotasInformativas.size(); i++) {
						Nota notaTurno = (Nota) listaNotasInformativas.get(i);
						hermod.addNotaToTurno(notaTurno, idTurno);
					}
				}

				Solicitud solicitud = turno.getSolicitud();
				List liquidaciones = solicitud.getLiquidaciones();
				//String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();


                pago.getLiquidacion().getSolicitud().setTurno(turno);

				Circulo circulo = evento.getCirculo();
				String fechaImpresion = this.getFechaImpresion();
				if (liquidacion instanceof LiquidacionTurnoCertificadoMasivo){
					LiquidacionTurnoCertificadoMasivo liqMas=(LiquidacionTurnoCertificadoMasivo)liquidacion;
					//if (liqMas.getTipoTarifa().equals(CTipoTarifa.EXENTO)){
						String year=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
						long numRecibo=hermod.getSecuencialMasivos(circulo.getIdCirculo(),year);
						pago.setNumRecibo(Long.toString(numRecibo));
						
						Pago.ID pagoID = new Pago.ID();
						pagoID.idLiquidacion = pago.getIdLiquidacion();
						pagoID.idSolicitud = pago.getIdSolicitud();
						hermod.setNumeroReciboPago(pagoID, Long.toString(numRecibo));						
						
					//}
				}
				
				
				
				ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion,CTipoImprimible.RECIBO);
				impRec.setValidaciones(evento.getValidacionesMasivos());
				impRec.setUsuarioGeneraRecibo(""+evento.getUsuarioSIR().getIdUsuario());
				
				// Bug 5324
				if( solicitud instanceof SolicitudCertificadoMasivo ){
					impRec.setTamanoCarta( true );
				} // if 


				//IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
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

				Bundle bundle = new Bundle(impRec);
				String cid= circulo.getIdCirculo();

				try {
					//se manda a imprimir el recibo por el identificador unico de usuario
					printJobs.enviar(cid, bundle, maxIntentos, espera);

				} catch (Throwable t) {
					t.printStackTrace();
				}
			} catch (Throwable t) {
				Log.getInstance().debug(ANPago.class,t.getMessage());
			}

			if ((liquidacion != null) && liquidacion instanceof LiquidacionTurnoCertificadoMasivo) {
				SolicitudCertificadoMasivo solicitudCertificadoMasivo = (SolicitudCertificadoMasivo) liquidacion.getSolicitud();

				if ((solicitudCertificadoMasivo != null) && !solicitudCertificadoMasivo.getSolicitudesHijas().isEmpty()) {
					Iterator it = solicitudCertificadoMasivo.getSolicitudesHijas().iterator();

					while (it.hasNext()) {
						id_matricula = "";
						ht = new Hashtable();
						ht.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioSIR());
						ht.put(CEstacion.ESTACION_ID, evento.getEstacion().getEstacionId());

						if (rol != null) {
							ht.put(CRol.ID_ROL, rol.getRolId());
						}

						SolicitudAsociada solicitudAsociada = (SolicitudAsociada) it.next();
						SolicitudCertificado solicitudCertificado = (SolicitudCertificado) solicitudAsociada.getSolicitudHija();

						id_matricula = solicitudCertificado.getMatriculaNoExistente();

						if (((id_matricula == null) || id_matricula.equals("")) && (solicitudCertificado != null) && !solicitudCertificado.getSolicitudFolios().isEmpty()) {
							SolicitudFolio solFolio = (SolicitudFolio) solicitudCertificado.getSolicitudFolios().get(0);
							id_matricula = solFolio.getIdMatricula();
						}

						if ((solicitudCertificado != null) && !solicitudCertificado.getLiquidaciones().isEmpty()) {
							LiquidacionTurnoCertificado liquidacionCertificado = (LiquidacionTurnoCertificado) solicitudCertificado.getLiquidaciones().get(0);

							//SE SACA LA LIQUIDACION DEL CERTIFICADO INDIVIDUAL Y SE LLAMA AL MÉTODO PROCESAR PAGO
							
							// ----------------------------------------------------------
							// Bug 5404:;
							// Esta colocando efectivo; deberica colocar pago asociado.
							DocumentoPago docPago;
							double tmpDocPagoValor;
							
							// tmpDocPagoValor = liquidacionCertificado.getValor();
							// TODO: se debe dejar con valor 0 el pago heredado?							
							tmpDocPagoValor = 0d;
							
							docPago = new DocPagoHeredado( tmpDocPagoValor );
							
							// ----------------------------------------------------------
							
							//DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacionCertificado.getValor());

							AplicacionPago appEfectivo = new AplicacionPago();
							appEfectivo.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
							appEfectivo.setIdSolicitud(solicitudCertificado.getIdSolicitud());
							appEfectivo.setValorAplicado(liquidacionCertificado.getValor());
							appEfectivo.setDocumentoPago(docPago);

							pagoCertificado = new Pago(liquidacionCertificado, null);
							pagoCertificado.addAplicacionPago(appEfectivo);
							pagoCertificado.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
							pagoCertificado.setIdSolicitud(solicitudCertificado.getIdSolicitud());
							pagoCertificado.setLiquidacion(liquidacionCertificado);
							pagoCertificado.setUsuario(evento.getUsuarioSIR());

							if ((id_matricula != null) && !id_matricula.equals("")) {
								List valid = (List) validaciones.get(id_matricula);

								if ((valid != null) && (valid.size() > 0)) {
									//ht.put(id_matricula, valid);
									ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
								}

								if (valid == null) {
									valid = new ArrayList();
									ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
								}
							}

							try {
								turnoIndividuales = hermod.procesarPagoMasivos(pagoCertificado, ht);
							} catch (Throwable t) {
								Log.getInstance().debug(ANPago.class,"ERROR AL CREAR EL TURNO" + t.getMessage());
							}
						}
					}
				}
			}
		}
	
		catch (Throwable e) {
			Log.getInstance().error(ANPago.class,"Ha ocurrido una excepcion inesperada ", e);
			throw new EventoException("Excepcion inesperada", e);
		}*/
        EvnRespPago evRespuesta = new EvnRespPago(turno);

        return evRespuesta;
    }

    /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno
     * @param impRecibo
     * @param uid
     * @return Indica si la impresion fue exitosa o no
     */
    private boolean imprimirRecibo(ImprimibleRecibo impRecibo, Turno turno, String uid) {
        //String circulo = turno.getIdCirculo();
        Bundle b = new Bundle(impRecibo);
        
        //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
        //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;

        //INGRESO DE INTENTOS DE IMPRESION
        try {
            String copyActive = hermod.getCopiaImp(impRecibo.getCirculo().getIdCirculo());
            if(copyActive.equals(CHermod.ACTIVE)){
                 b.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
            }
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

        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos
        boolean ok = false;

        try {
            printJobs.enviar(uid, b, maxIntentos, espera);
            ok = true;
        } catch (Throwable t) {
            t.printStackTrace();
            ok = false;
        }

        return ok;
    }

    /**
     * Este metodo hace el llamado al negocio para que se validen las
     * aplicaciones de pagoS
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnOficinas</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespOficinas</CODE>
     * @throws EventoException
     */
    private EventoRespuesta validarPago(EvnPago evento) throws EventoException {
        EvnRespPago evRespuesta = null;
        Pago pago = evento.getPago();
        String tipo = evento.getProceso().getNombre();
        tipo = (tipo != null) ? tipo.replaceAll(" ", "_") : null;

        System.out.println("LLEGO A VALIDAR");

        try {
            if (evento.getValorCertificadosAsociados() != 0) {
                System.out.println("LLEGO A VALIDAR VALOR CERTIFICADOS MASIVO > 0");
                if (validarPagoConCertificadosAsociados(pago, evento.getValorCertificadosAsociados(), hermod.getRangoAceptacionPago(tipo))) {
                    pago = hermod.validarPago(evento.getPago());
                    evRespuesta = new EvnRespPago(pago, hermod.getRangoAceptacionPago(tipo));
                }
            } else {
                System.out.println("LLEGO A VALIDAR VALOR CERTIFICADOS MASIVO < 0");
                if (validarPago(pago, hermod.getRangoAceptacionPago(tipo))) {
                    pago = hermod.validarPago(evento.getPago());
                    System.out.println("PASO VALIDACION CERTIFICADO MASIVO");
                    evRespuesta = new EvnRespPago(pago, hermod.getRangoAceptacionPago(tipo));
                }
            }
        } catch (PagoInvalidoException e) {
            throw e;
        } catch (HermodException e) {
            throw new PagoInvalidoException("Pago Invalido", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANPago.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("Excepcion inesperada", e);
        }

        return evRespuesta;
    }

    /**
     * Este metodo valida que todas las aplicaciones de pago tengan saldo valido
     *
     * @param pago EL pago que se va a validar
     * @param precision El rango de tolerancia
     * @return boolean true si el pago es valido
     * @throws PagoInvalidoException si el pago no es valido
     */
    private boolean validarPagoConCertificadosAsociados(Pago pago, double valorCertificadosAsociados, double precision) throws PagoInvalidoException {
        List pagos = pago.getAplicacionPagos();
        Liquidacion liquidacion = pago.getLiquidacion();
        double valorLiquidado = liquidacion.getValor();
        valorLiquidado += valorCertificadosAsociados;
        double valorPagado = 0;

        Iterator it = pagos.iterator();

        while (it.hasNext()) {
            AplicacionPago apl = (AplicacionPago) it.next();
            valorPagado += apl.getValorAplicado();
        }

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + valorLiquidado);
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + valorPagado);

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + NumberFormat.getInstance().format(valorPagado));

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + NumberFormat.getInstance().format(valorPagado));

        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.00");

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + df.format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + df.format(valorPagado));

        //SI ESTAN HACIENDO UNA EVALUACION CONTRA UN NIVEL DE PRECISION TIENEN QUE
        //EVALUAR UN RANGO !
        /*
		if ((valorPagado + precision) != valorLiquidado) {
		    throw new PagoInvalidoException(
		        "El valor a pagar no coincide con el valor Liquidado");
		}*/
        if ((valorPagado >= (valorLiquidado - precision)) && (valorPagado <= (valorLiquidado + precision))) {
            Log.getInstance().debug(ANPago.class, "PAGO OK");
            //liquidacion.setValor(valorLiquidado);
        } else {
            throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");
        }

        return true;
    }

    /**
     * Este metodo valida que todas las aplicaciones de pago tengan saldo valido
     *
     * @param pago EL pago que se va a validar
     * @param precision El rango de tolerancia
     * @return boolean true si el pago es valido
     * @throws PagoInvalidoException si el pago no es valido
     */
    private boolean validarPago(Pago pago, double precision) throws PagoInvalidoException {
        List pagos = pago.getAplicacionPagos();
        Liquidacion liquidacion = pago.getLiquidacion();
        double valorLiquidado = liquidacion.getValor();
        double valorPagado = 0;

        Iterator it = pagos.iterator();

        while (it.hasNext()) {
            System.out.println("VALIDANDO ITERANDO PAGOS ");
            AplicacionPago apl = (AplicacionPago) it.next();
            valorPagado += apl.getValorAplicado();
        }

        System.out.println("VALIDANDO PRECISION " + precision);
        System.out.println("VALIDANDO VALOR LIQUIDADO " + valorLiquidado);
        System.out.println("VALIDANDO VALOR PAGADO " + valorPagado);


        /*Log.getInstance().debug(ANPago.class, "Valor Liquidado" + valorLiquidado);
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + valorPagado);

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + NumberFormat.getInstance().format(valorPagado));

        Log.getInstance().debug(ANPago.class, "Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + NumberFormat.getInstance().format(valorPagado));*/
        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.00");

        /*Log.getInstance().debug(ANPago.class, "Valor Liquidado" + df.format(valorLiquidado));
        Log.getInstance().debug(ANPago.class, "Valor Pagado" + df.format(valorPagado));*/
        //SI ESTAN HACIENDO UNA EVALUACION CONTRA UN NIVEL DE PRECISION TIENEN QUE
        //EVALUAR UN RANGO !
        /*
		if ((valorPagado + precision) != valorLiquidado) {
		    throw new PagoInvalidoException(
		        "El valor a pagar no coincide con el valor Liquidado");
		}*/
        if ((valorPagado >= (valorLiquidado - precision)) && (valorPagado <= (valorLiquidado + precision))) {
            //Log.getInstance().debug(ANPago.class, "PAGO OK");
            System.out.println("VALIDANDO PAGO OK");
        } else {
            System.out.println("VALIDANDO PAGO NO OK");
            throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");
        }

        return true;
    }

    /**
     * Retorna el ID de la primera matrícula asociada a la solicitud. Si es un
     * certificado solo hay una matrícula.
     *
     * @param solicitud
     * @return Matricula asociada a la solicitud
     */
    private String getMatricula(Solicitud solicitud) {
        List solFolio = solicitud.getSolicitudFolios();
        List matriculas = new Vector();
        Iterator itSolFolio = solFolio.iterator();

        while (itSolFolio.hasNext()) {
            SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
            matriculas.add(sol.getFolio().getIdMatricula());
        }

        String idMatricula = (String) matriculas.get(0);

        return idMatricula;
    }

    /**
     * Retorna el ID del folio asociado a la solicitud
     *
     * @param solicitud
     * @return Llave primaria del Folio
     */
    private FolioPk getFolio_ID(Solicitud solicitud) {
        List solFolio = solicitud.getSolicitudFolios();

        Iterator itSolFolio = solFolio.iterator();
        List matriculas = new Vector();

        FolioPk fid;

        while (itSolFolio.hasNext()) {
            SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
            fid = new FolioPk();
            fid.idMatricula = sol.getFolio().getIdMatricula();
            matriculas.add(fid);
        }

        //String idMatricula = (String)matriculas.get(0);
        try {
            fid = (FolioPk) matriculas.get(0);
        } catch (Throwable e) {
            Log.getInstance().error(ANPago.class, e);
            fid = null;
        }

        return fid;
    }

    /**
     * Metodo que retorna un numero con un "0" antes en caso de ser menor que
     * 10.
     *
     * @param i el numero.
     * @return Cadena con el número en dos digitos
     */
    protected String formato(int i) {
        if (i < 10) {
            return "0" + (new Integer(i)).toString();
        }

        return (new Integer(i)).toString();
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     *
     * @return Cadena con la fecha de impresión
     */
    protected String getFechaImpresion() {
        Calendar c = Calendar.getInstance();
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

    //IMPRIMIR EL CERTIFICADO EN EL MOMENTO DE LA CREACIÓN DEL TURNO.
    /**
     * Imprimir el certificado asociado al turno.
     *
     * @param turno el turno
     * @param parametros tabla de Hashing con los parametros de impresion
     * (además de los del WorkFlow)
     * @return la tabla de hashing de parametros adicionando un registro
     * dependiendo de si la impresion fue o no exitosa.
     * @throws Throwable
     */
    private Hashtable imprimirCertificado(Turno turno, Usuario usuarioSIR, String UID, String impresora) throws Throwable {
        boolean usePrefabricado = false;
        boolean storePrefabricado = false;

        long initial_time = 0, final_time;

        String sNombre = "";
        String archivo = "";
        String cargoToPrint = "";
        String rutaFisica = null;

        String intentosImpresion;
        String esperaImpresion;

        ImprimibleCertificado imprimibleCertificado = null;
        FirmaRegistrador firmaRegistrador = null;

        SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
        List listaFolios = solCerti.getSolicitudFolios();
        int numCopias = solCerti.getNumeroCertificados();
        Hashtable parametros = new Hashtable();

        parametros.put("USUARIO_SIR", usuarioSIR.getUsername());
        parametros.put("CIRCULO_O_UID", UID);

        Log.getInstance().debug(ANPago.class, "\n*******************************************************");
        Log.getInstance().debug(ANPago.class, "(ANTES METODO IMPRESION CERTIFICADO)");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        //Obtener los parámetros.
        //String notificationId = (String) parametros.get(Processor.NOT_ID);
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

        List liquidaciones = solCerti.getLiquidaciones();
        LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
        String tipoTarifa = liquidacion.getTipoTarifa();

        String usuario = (String) parametros.get("USUARIO_SIR");
        Usuario usuarioNeg = new Usuario();
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

        if (forseti.mayorExtensionFolio(solFolio.getIdMatricula())) {
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

        CirculoPk cid = new CirculoPk();
        cid.idCirculo = turno.getIdCirculo();

        /*
		 * obtenemos los valores configurados a nivel del sistema para la impresion de certificados prefabricados
         */
        try {
            ForsetiProperties prop = ForsetiProperties.getInstancia();

            /* verificamos si la aplicacion esta configurada para el uso de prefabricados */
            usePrefabricado = Boolean.valueOf(prop.getProperty(ForsetiProperties.USAR_PREFABRICADO)).booleanValue();

            if (usePrefabricado) {
                /* verificamos si el circulo asociado a la solicitud del certificado, se encuentra habilitado
				 * para el uso del proceso de prefabricados */
                final String circulos_activos = prop.getProperty(ForsetiProperties.CIRCULOS_ACTIVOS);

                if (circulos_activos != null) {
                    usePrefabricado = circulos_activos == "ALL" || circulos_activos.indexOf(solFolio.getIdMatricula().substring(0, 3)) != -1 ? true : false;
                }

                /* verificamos si esta activa la opcion de almacenar el certificado prefabricado, cuando este
				 * no se encuentre almacenado en la base de datos o se encuentre obsoleto */
                if (usePrefabricado) {
                    storePrefabricado = Boolean.valueOf(prop.getProperty(ForsetiProperties.ALMACER_ALGENERAR)).booleanValue();
                }
            }
        } catch (Exception ex) {
            usePrefabricado = false;
        }

        if (usePrefabricado) {
            try {
                Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                initial_time = new Date().getTime();

                PrefabricadoPk id = new PrefabricadoPk(solFolio.getCirculo(), solFolio.getIdMatricula());
                Imprimible imprimible = impresion.getImpresionDAO().getPrefabricadoById(id);

                ByteArrayInputStream bais = new ByteArrayInputStream(imprimible.getDatosImprimible());
                ObjectInputStream ois = new ObjectInputStream(bais);

                Bundle bundle = (Bundle) ois.readObject();

                ois.close();
                bais.close();

                if (bundle.getImprimible() instanceof ImprimibleCertificado) {
                    imprimibleCertificado = (ImprimibleCertificado) bundle.getImprimible();
                } else {
                    usePrefabricado = false;
                }
            } catch (Exception ex) {
                Log.getInstance().fatal(ANPago.class, "Error al tratar de obtener el objeto prefabricado, " + ex.getMessage());
                usePrefabricado = false;
            }
        }

        /* en caso de no estar configurado el circulo para el certificado solicitado, o en caso de haberse
         * generado cualquier tipo de excepcion, procedemos a realizar el proceso normal de generacion del
         * imprimible */
        if (!usePrefabricado) {
            Folio folio = null;

            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
            initial_time = new Date().getTime();
            folio = forseti.getFolioByMatricula(solFolio.getIdMatricula());
            List anotacionesFolio = folio.getAnotaciones();

            if (anotacionesFolio == null || anotacionesFolio.isEmpty()) {
                FolioPk fpk = new FolioPk();
                fpk.idMatricula = solFolio.getIdMatricula();
                anotacionesFolio = forseti.getAnotacionesFolio(fpk);
                folio.setAnotaciones(anotacionesFolio);
            }

            FolioPk fid = new FolioPk();
            fid.idMatricula = folio.getIdMatricula();

            // obtenemos los folios padres
            List padres = forseti.getFoliosPadre(fid);
            // obtenemos los folios hijos
            List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
            /**
             * @author: David Panesso
             * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION Nuevo
             * listado de folios derivados
             *
             */
            List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

            imprimibleCertificado = new ImprimibleCertificado(folio, null, padres, hijos, foliosDerivadoHijos, null, null, null, null);
            
            // imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, fechaImpresion, tipoImprimible, tbase1, tbase2);  

            // almacenamos el imprimible como prefabricado
            if (storePrefabricado) {
                impresion.getImpresionDAO().guardarPrefabricado(imprimibleCertificado);
            }
        }

        imprimibleCertificado.setTurno(turno);
        imprimibleCertificado.setFechaImpresion(fechaImpresion);
        imprimibleCertificado.setTipoImprimible(tipoImprimible);
        imprimibleCertificado.setTextoBase1(tbase1);
        imprimibleCertificado.setTextoBase2(tbase2);
        imprimibleCertificado.setIsCertificadoEspecial(isCertificadoEspecial);
        imprimibleCertificado.setIsCertificadoTramite(isCertificadoTramite);
        imprimibleCertificado.setCertificadoActuacion(isCertificadoActuacion);
        imprimibleCertificado.setTurnoTramite(turnoTramite);
        imprimibleCertificado.setTurno(turno);
        /**
         * @author : Carlos Torres Caso Mantis : 14985: Acta - Requerimiento
         * 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */
        Traslado traslado = hermod.getFolioDestinoTraslado(solFolio.getIdMatricula());
        imprimibleCertificado.setInfoTraslado(traslado);

        /**
         * @author : Julio Alcazar @change : Set propiedad NIS en el imprimible.
         * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas
         * Impresión certificados
         */
        String text = turno.getIdWorkflow() + "/" + turno.getSolicitud().getIdSolicitud();
        byte[] key = new byte[8];
        key[0] = 5;
        imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
        imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
        if (turno.isNacional()) {
            cid.idCirculo = imprimibleCertificado.getFolio().getCirculo();
        }

        final_time = new Date().getTime();
        Log.getInstance().debug(ANPago.class, "\n " + String.valueOf(final_time - initial_time) + " Milliseconds *****");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************");

        imprimibleCertificado.setPrintWatermarkEnabled(true);
        imprimibleCertificado.setUsuario(usuarioSIR);

        //PageFormat pageFormat = new PageFormat();
        //imprimibleCertificado.generate(pageFormat);
        String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

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

            if (turno.isNacional()) {
                if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                        || archivo == null || archivo.equals("")) {
                    throw new Exception("No se permiten certificados de orden nacional sin firma");
                }
            }

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
        } catch (Exception e) {
            throw e;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (sNombre == null) {
            sNombre = "";
        }

        //+++
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
            //+++
            imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
        }

        //+++
        Log.getInstance().debug(ANPago.class, "Tipo de Tarifa = " + tipoTarifa);

        if (tipoTarifa.equals(CHermod.EXENTO) && (solCerti.getSolicitudesPadres() != null
                && solCerti.getSolicitudesPadres().size() > 0)) {
            imprimibleCertificado.setExento(true);
            String textoExento = hermod.getTextoExento();
            imprimibleCertificado.setTextoExento(textoExento);

            ByteArrayOutputStream pdfFormulario = null;

            /**
             * @author: Daniel Forero
             * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF Método que
             * genera el archivo PDF del objeto imprimibleCertificado
             *
             */
            try {
                String rutaTempPDF = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
                PdfCreator pdf = new PdfCreator();
                imprimibleCertificado.setPdf(true);
                pdfFormulario = pdf.generar(imprimibleCertificado);

                SolicitudAsociada turnoMasivo = (SolicitudAsociada) solCerti.getSolicitudesPadres().get(0);

                String ruta = rutaTempPDF + turnoMasivo.getSolicitudPadre().getTurno().getIdWorkflow();

                File dir = new File(ruta);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File file = new File(ruta + FenrirProperties.getInstancia().getProperty(FenrirProperties.SO) + imprimibleCertificado.getFolio().getIdMatricula() + ".pdf");

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfFormulario.toByteArray());
                fos.close();

                parametros.put(Processor.RESULT, "CONFIRMAR");
            } catch (IOException eio) {
                Log.getInstance().error(ANPago.class, "ERROR GENERANDO PDF (IO): " + eio.getMessage());
            } catch (Exception e) {
                parametros.put(Processor.RESULT, "ERROR");
                Log.getInstance().debug(ANPago.class, "ERROR GENERANDO PDF: " + e.getMessage());
            }
        } else {
            if (tipoTarifa.equals(CHermod.EXENTO)) {
                imprimibleCertificado.setTextoExento(hermod.getTextoExento());
                imprimibleCertificado.setExento(true);
            } else {
                imprimibleCertificado.setExento(false);
            }
            imprimibleCertificado.setFolio(forseti.getFolioByID(imprimibleCertificado.getFolio().getIdMatricula()));
            
             if (imprimibleCertificado.getFolio().getAnotaciones() == null || imprimibleCertificado.getFolio().getAnotaciones().isEmpty()) {
                List anots = null;
                Folio currentFolio = imprimibleCertificado.getFolio();
                FolioPk fpk = new FolioPk();
                fpk.idMatricula = imprimibleCertificado.getFolio().getIdMatricula();
                anots = forseti.getAnotacionesFolio(fpk);
                if(anots != null && !anots.isEmpty()){
                    currentFolio.setAnotaciones(anots);
                    imprimibleCertificado.setFolio(currentFolio);
                }
            }
            parametros = this.imprimir(turno, imprimibleCertificado, parametros, numCopias, impresora);
        }

        String resultado = (String) parametros.get(Processor.RESULT);
        boolean okImpresion = true;
        if (resultado != null) {
            if (resultado.equals("ERROR")) {
                okImpresion = false;
            }
        }

        if (okImpresion) {
            //actualizar el número de impresiones en el turno
            int numImpresiones = solCerti.getNumImpresiones();
            solCerti.setNumImpresiones(numImpresiones + 1);
            Solicitud resultadoSolicitud = null;
            resultadoSolicitud = hermod.updateSolicitudCertificado(solCerti);

        }

        Log.getInstance().debug(ANPago.class, "\n*******************************************************");
        Log.getInstance().debug(ANPago.class, "(DESPUES METODO IMPRESION CERTIFICADO)");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        return parametros;
    }

    public static BufferedImage getImage(String path, String nombreArchivo) {
        String nombreCompleto = getNombreCompleto(path, nombreArchivo);
        BufferedImage buf = null;

        try {
            File file = new File(nombreCompleto);
            buf = ImageIO.read(file);
        } catch (IOException e) {
            Log.getInstance().error(ANPago.class, e);
            Log.getInstance().error(ANPago.class, "Error imprimiendo el gráfico en la ruta: " + nombreCompleto);
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
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno es el Turno con el que se creo la solicitud.
     * @param imprimible es la representacion del documento que se desea
     * imprimir (Certificado, Oficio de Pertenencia, Nota Devolutiva, Formulario
     * de Calificacion, Formulario de Correccion, etc).
     * @param parametros tabla de Hashing con los parametros de impresión
     * (además de los parametros asociados al WorkFlow).
     * @param numCopias es el número de copias que se desea imprimir.
     * @return
     */
    private Hashtable imprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias, String impresora) {
        String circulo = turno.getIdCirculo();
        //String impresora = (String)parametros.get(IMPRESORA);
        String imp = (String) parametros.get("CIRCULO_O_UID");

        //Opción para imprimir en local o en el applet administrativo de impresión
        if (imp != null) {
            circulo = imp;
        }

        //Bundle b = new Bundle(imprimible,impresora);
        Bundle b = null;
        if (impresora != null) {
            b = new Bundle(imprimible, impresora);
        } else {
            b = new Bundle(imprimible);
        }

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

    private String obtererErroresNotaInformativa(Hashtable errores, List matriculas) {
        String descripcionNota = "";
        Iterator iter = matriculas.iterator();
        while (iter.hasNext()) {
            String idMatricula = (String) iter.next();
            if (errores.get(idMatricula) != null) {
                List listaErrores = (List) errores.get(idMatricula);
                if (listaErrores != null && listaErrores.size() > 0) {
                    descripcionNota += "PROBLEMA(S) CON LA MATRICULA " + idMatricula + ": \n";
                    for (int i = 0; i < listaErrores.size(); i++) {
                        String mensajeError = (String) listaErrores.get(i);
                        descripcionNota += mensajeError.toUpperCase();
                        if (i < listaErrores.size() - 1) {
                            descripcionNota += ", ";
                        }
                    }
                    descripcionNota += ". \n\n";
                }
            }
        }
        //validaciones.get(id_matricula)==null
        return descripcionNota;
    }

    /**
     * Accion que imprime una nota informativa cuando se solicita.
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private void imprimirNotaInformativa(ImprimibleNotaInformativa impNota, String circulo) throws EventoException {
        Hashtable tabla = new Hashtable();

        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";

        //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 
        //INGRESO DE INTENTOS DE IMPRESION
        try {

            String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
            if (intentosImpresion != null) {
                tabla.put(INTENTOS, intentosImpresion);
            } else {
                tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                tabla.put(ESPERA, esperaImpresion);
            } else {
                tabla.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
            }
        } catch (Throwable t) {

            tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
            tabla.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);

        }

        int idImprimible = this.imprimir(impNota, tabla, circulo, 1, true);
    }

    private int imprimir(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {

        boolean impresion_ok = false;
        String mensaje_error = "";

        //CONSTANTES PARA LA IMPRESIÓN.
        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";

        Bundle b = new Bundle(imprimible);
        b.setNumeroCopias(numCopias);

        String numIntentos = (String) parametros.get(INTENTOS);
        String espera = (String) parametros.get(ESPERA);

        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();
        int idImprimible = 0;

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
            printJobs.enviar(ID, b, intentos, esperado);
            impresion_ok = true;
        } catch (PrintJobsException t) {
            idImprimible = t.getIdImpresion();
            if (idImprimible == 0) {
                throw new EventoException("Se genero una excepción al imprimir la Nota. " + t.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            mensaje_error = t.getMessage();

            if (lanzarExcepcion && !impresion_ok) {
                throw new EventoException(mensaje_error);
            }

        }

        return idImprimible;
    }

}
