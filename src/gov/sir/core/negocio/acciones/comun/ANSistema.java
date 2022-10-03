package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.eventos.comun.EvnSistema;
import gov.sir.core.negocio.acciones.excepciones.ANInitParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ANSeguridadException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.ListaContexto;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Esta accion de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnInitParametros</CODE> y generar eventos de respuesta del tipo
 * <CODE>EvnRespInitparametros</CODE> Esta accion de negocio se encarga de
 * atender todas las solicitudes relacionadas a las peticiones de los parámetros
 * del sistema
 *
 * @author mmunoz, fceballos, dsalas, jfrias
 */
public class ANSistema extends SoporteAccionNegocio {

    /**
     * Instancia de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de PrintJob
     */
    private PrintJobsInterface printJobs;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Constructor de la clase ANSistema Es el encargado de invocar al Service
     * Locator y pedirle una instancia del servicio que necesita
     */
    public ANSistema() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando los servicios.", e);
        }

    }

    /**
     * Recibe un evento de seguridad y devuelve un evento de respuesta
     *
     * @param e el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnLiquidacion</CODE>
     * @throws EventoException cuando ocurre un problema que no se pueda manejar
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespLiquidacion</CODE>
     * @see gov.sir.core.eventos.comun.EvnLiquidacion
     * @see gov.sir.core.eventos.comun.EvnRespLiquidacion
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnSistema evento = (EvnSistema) e;

        if ((evento == null) || (evento.getTipoEvento() == null)) {
            throw new EventoException("El evento recibido es nulo");
        }

        if (evento.getTipoEvento().equals(EvnSistema.INIT_PARAMETROS)) {
            return initParametros(evento);
        } else if (evento.getTipoEvento().equals(EvnSistema.FINALIZAR_SERVICIOS)) {
            return finalizarServicios(evento);
        } else if (evento.getTipoEvento().equals(EvnSistema.RECARGAR_LISTAS)) {
            return recargarListas(evento);
        } else if (evento.getTipoEvento().equals(EvnSistema.CARGAR_LISTAS)) {
            return cargarListas(evento);
        }

        return null;
    }

    /**
     * Por medio de este método se obtiene una lista de fases a las que puede
     * acceder una estación determinada en un proceso determinado
     *
     * @param evento
     * @return el evento de tipo <CODE>EvnRespSeguridad</CODE>
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EventoRespuesta finalizarServicios(EvnSistema evento) {
        try {
            fenrir.finalizar();
        } catch (Throwable e) {
            Log.getInstance().error(ANSistema.class, e);
        }

        try {
            forseti.finalizar();
        } catch (Throwable e1) {
            e1.printStackTrace();
        }

        try {
            hermod.finalizar();
        } catch (Throwable e2) {
            e2.printStackTrace();
        }

        try {
            printJobs.stop();
        } catch (Throwable e2) {
            e2.printStackTrace();
        }

        return null;
    }

    /**
     * Por medio de este método se obtiene una lista de fases a las que puede
     * acceder una estación determinada en un proceso determinado
     *
     * @param evento
     * @return el evento de tipo <CODE>EvnRespSeguridad</CODE>
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EventoRespuesta initParametros(EvnSistema evento)
            throws EventoException {
        String ipBalanceador = "";
        String usarBalanceador = "";
        String pathFirmas = "";
        String contentTypeImages = "";
        String reportesServletUrl = "";
        String numeroImpresionesCertificados = "";

        Hashtable ht = new Hashtable();

        try {
            printJobs.start();
        } catch (PrintJobsException e) {
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion al iniciar el servicio de impresión ", e);
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion al iniciar el servicio de impresión ", e);
            throw new EventoException(e.getMessage(), e);
        }

        evento.setListas(ht);
        try {
            this.getTiposCertificados(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposID(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getTiposIDNatural(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getTiposIDJuridica(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getModalidad(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getDeterminacionInm(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getTipoPersona(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        try {
            this.getTipoSexo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getBancos(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposTarifaCertificados(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getEjes(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposPredio(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposDocumento(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getGruposNaturalezaJuridica(evento);
            Log.getInstance().info(ANSistema.class, "Se cargaron correctamente las naturalezas juridicas ultima version");
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         */
        try {
            this.getGruposNaturalezaJuridicaAll(evento);
            Log.getInstance().info(ANSistema.class, "Se cargaron correctamente las naturalezas juridicas todas");
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getInteresesJuridicos(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getNaturalezasJuridicasEntidades(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getEntidadesReparto(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getGruposNaturalezaJuridicaCalificacion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCausalesReimpresion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCategoriasNotarias(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposAlcanceGeografico(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposFotocopia(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getSubTiposAtencion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getSubTiposSolicitud(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposActo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        /*
         *@autor          : JATENCIA
         * @mantis        : 0015082 
         * @Requerimiento : 027_589_Acto_liquidación_copias 
         * @descripcion   : Se realiza el llamado al metodo que se 
         * creo para cargar la lista de Tipos de Actos, sin filtro.
         */
        try {
            this.getTiposActoDos(evento);
            Log.getInstance().info(ANSistema.class, "Se cargaron correctamente los Tipos de Actos, lista completa.");
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        /* - Fin del bloque - */

        try {
            this.getTiposImpuesto(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        /**
         * @author: Fernando Padilla
         * @change: Requerimiento externo No. 135, Se carga en el contexto de la
         * aplicacion la lista de tipos de impuesto por circulo.
         */
        try {
            this.getTiposImpuestoCirculo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion "
                    + "inesperada al tratar de cargar los tipos de impuestos por circulo"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCirculos(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCirculosFechaProd(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposTarifas(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposCalculo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getOPLookupTypes(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTipoDerechoRegistral(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getEstadosFolio(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposOficina(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getDominiosNaturalezaJuridica(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getDepartamentos(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getEstadosAnotacion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposAccionNotarial(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCategoriasReparto(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getCausalesRestitucion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.consultarRoles(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposPago(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getOPLookupCodes(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.consultarEstaciones(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.consultarNiveles(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getValidacionNotas(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getListaProcesos(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getValidaciones(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getProhibiciones(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposVisibilidad(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getTiposTarifasConfiguradasPorCirculo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getPermisosCorreccion(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ipBalanceador = hermod.getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.IP_BALANCEADOR);
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar la ip del balanceador "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            usarBalanceador = hermod.getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.USAR_BALANCEADOR);
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar si se debe usar el balanceador "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            pathFirmas = hermod.getPathFirmasRegistradores();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar la ruta física para cargar las firmas"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            contentTypeImages = hermod.getFirmasContentType();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar el tipo de contenido para las imagenes"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            reportesServletUrl = hermod.getUrlServletReportes();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar la url del servlet de reportes"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            numeroImpresionesCertificados = hermod.getNumeroMaximoImpresionesCertificados();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepción al consultar la ruta física para cargar las firmas"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        try {
            ht.put(EvnRespSistema.IP_BALANCEADOR, ipBalanceador);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.USAR_BALANCEADOR, usarBalanceador);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.FIRMAS_PATH, pathFirmas);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.FIRMAS_CONTENT_TYPE, contentTypeImages);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.REPORTES_SERVLET_URL, reportesServletUrl);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.MAXIMO_COPIAS_CERTIFICADO, numeroImpresionesCertificados);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            this.getRelacionBancosCirculo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        /*
        *Req Canales de recaudo -- Geremias Ortiz Lozano
         */
        try {
            this.getCanalesRecaudo(evento);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        
        return new EvnRespSistema(ht);
    }

    /**
     * Por medio de este método se consultan las listas indicadas en la lista
     * evento.getListas(), la cual contiene objetos String iguales a las
     * constantes definidas en <CODE>EvnRespSistema</CODE>.
     *
     * @param evento
     * @return el evento de tipo <CODE>EvnRespSistema</CODE> que en el atributo
     * listas contiene el mapa con las listas consultadas
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @see gov.sir.core.eventos.comun.EvnSistema
     * @see gov.sir.core.eventos.comun.EvnRespSistema
     */
    private EventoRespuesta recargarListas(EvnSistema evento)
            throws EventoException {
        List listasARecargar = evento.getListasARecargar();
        Hashtable ht = new Hashtable();
        if (listasARecargar != null) {
            evento.setListas(ht);
            for (Iterator i = listasARecargar.iterator(); i.hasNext();) {
                try {
                    String listaARecargar = (String) i.next();
                    if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_CERTIFICADOS)) {
                        this.getTiposCertificados(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ID)) {
                        this.getTiposID(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ID_JURIDICA)) {
                        this.getTiposIDJuridica(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ID_NATURAL)) {
                        this.getTiposIDNatural(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_MODALIDAD)) {
                        this.getModalidad(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_DETERMINACION_INM)) {
                        this.getDeterminacionInm(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_PERSONA)) {
                        this.getTipoPersona(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_SEXO)) {
                        this.getTipoSexo(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_BANCOS)) {
                        this.getBancos(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CANALES_RECAUDO)) {
                        this.getCanalesRecaudo(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_EJES)) {
                        this.getEjes(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_PREDIO)) {
                        this.getTiposPredio(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_DOCUMENTO)) {
                        this.getTiposDocumento(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS)) {
                        this.getGruposNaturalezaJuridica(evento);
                    } /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change : se agrega condición para tener encuentra la
                     * nueva lista de grupos de Naturalezas juridicas.
                     */
                    else if (listaARecargar.equals(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V)) {
                        this.getGruposNaturalezaJuridicaAll(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_INTERES_JURIDICO)) {
                        this.getInteresesJuridicos(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION)) {
                        this.getGruposNaturalezaJuridicaCalificacion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CAUSALES_REIMPRESION)) {
                        this.getCausalesReimpresion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ALCANCE_GEOGRAFICO)) {
                        this.getTiposAlcanceGeografico(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CATEGORIAS_NOTARIAS)) {
                        this.getCategoriasNotarias(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_FOTOCOPIA)) {
                        this.getTiposFotocopia(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_SUBTIPOS_ATENCION)) {
                        this.getSubTiposAtencion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_SUBTIPOS_SOLICITUD)) {
                        this.getSubTiposSolicitud(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ACTO)) {
                        this.getTiposActo(evento);
                        /*
                         *@autor          : JATENCIA
                         * @mantis        : 0015082 
                         * @Requerimiento : 027_589_Acto_liquidación_copias 
                         * @descripcion   : Se realiza el llamado al metodo que se 
                         * creo para cargar la lista de Tipos de Actos, sin filtro.
                         */
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_ACTO_DOS)) {
                        this.getTiposActoDos(evento);
                        /* - Fin del bloque - */
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_IMPUESTO)) {
                        this.getTiposImpuesto(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_DEPARTAMENTOS)) {
                        this.getDepartamentos(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_TARIFA)) {
                        this.getTiposTarifas(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_OPLOOKUP_TYPE)) {
                        this.getOPLookupTypes(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPO_CALCULO)) {
                        this.getTiposCalculo(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_DERECHOS_REGISTRALES)) {
                        this.getTipoDerechoRegistral(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ESTADOS_FOLIO)) {
                        this.getEstadosFolio(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_OFICINA)) {
                        this.getTiposOficina(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_DOMINIOS_NATURALEZA_JURIDICA)) {
                        this.getDominiosNaturalezaJuridica(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES)) {
                        this.getCirculos(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES_FECHA)) {
                        this.getCirculosFechaProd(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ESTADOS_ANOTACION)) {
                        this.getEstadosAnotacion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ACCIONES_NOTARIALES)) {
                        this.getTiposAccionNotarial(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CATEGORIAS)) {
                        this.getCategoriasReparto(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_CAUSALES_RESTITUCION)) {
                        this.getCausalesRestitucion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ROLES)) {
                        this.consultarRoles(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_PAGO)) {
                        this.getTiposPago(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_UNIDADES_MEDIDA)) {
                        this.getOPLookupCodes(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ESTACIONES)) {
                        this.consultarEstaciones(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_NIVELES)) {
                        this.consultarNiveles(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_VALIDACIONES_NOTA)) {
                        this.getValidacionNotas(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_PROCESOS)) {
                        this.getListaProcesos(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_VALIDACIONES)) {
                        this.getValidaciones(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_PROHIBICIONES)) {
                        this.getProhibiciones(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_VISIBILIDADES)) {
                        this.getTiposVisibilidad(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS)) {
                        this.getTiposTarifaCertificados(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO)) {
                        this.getTiposTarifasConfiguradasPorCirculo(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD)) {
                        this.getNaturalezasJuridicasEntidades(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS)) {
                        this.getNaturalezasJuridicasEntidades(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS)) {
                        this.getEntidadesReparto(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS_ACTIVAS)) {
                        this.getEntidadesReparto(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_PERMISOS_CORRECCION)) {
                        this.getPermisosCorreccion(evento);
                    } else if (listaARecargar.equals(EvnRespSistema.LISTA_RELACION_BANCOS_CIRCULO)) {
                        this.getRelacionBancosCirculo(evento);
                    } else {
                        throw new EventoException("La lista indicada no existe");
                    }
                } catch (Throwable th) {
                    ExceptionPrinter printer = new ExceptionPrinter(th);
                    Log.getInstance().error(ANSistema.class, "Ha ocurrido una excepcion inesperada "
                            + printer.toString());
                    throw new EventoException(th.getMessage(), th);
                }
            }
        }
        return new EvnRespSistema(ht, evento.getTipoEvento());
    }

    /**
     * Este construye la lista de listas de contexto que son suceptibles de
     * actualización.
     *
     * @param evento
     * @return <code>EvnRespSistema</code>
     * @throws EventoException
     */
    private EventoRespuesta cargarListas(EvnSistema evento)
            throws EventoException {
        List listasContexto = new ArrayList();
        ListaContexto listaContexto = null;

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ID);
        listaContexto.setNombre("Tipos Identificación");
        listaContexto.setDescripcion("Tipos de documento de identificación de personas y empresas");
        listasContexto.add(listaContexto);
        
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ID_NATURAL);
        listaContexto.setNombre("Tipos Identificación Persona Natural");
        listaContexto.setDescripcion("Tipos de documento de identificación de personas naturales");
        listasContexto.add(listaContexto);
        
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ID_JURIDICA);
        listaContexto.setNombre("Tipos Identificación Persona Juridica");
        listaContexto.setDescripcion("Tipos de documento de identificación de personas jurudicas");
        listasContexto.add(listaContexto);
       
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_MODALIDAD);
        listaContexto.setNombre("Modalidad");
        listaContexto.setDescripcion("Modalidades segun los codigos de naturaleza juridica 0125 y 0126");
        listasContexto.add(listaContexto);
        
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_DETERMINACION_INM);
        listaContexto.setNombre("Determinacion del Inmueble");    
        listaContexto.setDescripcion("Determinaciones del inmueble en el folio");
        listasContexto.add(listaContexto);
        
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_PERSONA);
        listaContexto.setNombre("Tipos Persona");
        listaContexto.setDescripcion("Tipos de persona para identificar las personas juridica y natural");
        listasContexto.add(listaContexto);
        
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_SEXO);
        listaContexto.setNombre("Tipos Sexo");
        listaContexto.setDescripcion("Tipos de sexo para identificar a las personas naturales");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_CERTIFICADOS);
        listaContexto.setNombre("Tipos de Certificados");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(
                EvnRespSistema.LISTA_BANCOS);
        listaContexto.setNombre("Bancos");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(
                EvnRespSistema.LISTA_TIPOS_EJES);
        listaContexto.setNombre("Tipos de Eje");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_PREDIO);
        listaContexto.setNombre("Tipos de Predio");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_DOCUMENTO);
        listaContexto.setNombre("Tipos de Documento");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
        listaContexto.setNombre("Grupos de Naturalezas Juridicas");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Retorna Anotacion temporar.
         */
        listaContexto.setId(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V);
        listaContexto.setNombre("Grupos de Naturalezas Juridicas Versionadas");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_INTERES_JURIDICO);
        listaContexto.setNombre("Intereses Juridicos");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION);
        listaContexto.setNombre("Grupos de Naturalezas Juridicas Calificación");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_CAUSALES_REIMPRESION);
        listaContexto.setNombre("Causales de Reimpresión");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ALCANCE_GEOGRAFICO);
        listaContexto.setNombre("Tipos de Alcance Geográfico");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_FOTOCOPIA);
        listaContexto.setNombre("Tipos de Fotocopias");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_SUBTIPOS_ATENCION);
        listaContexto.setNombre("Subtipos de Atención");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_SUBTIPOS_SOLICITUD);
        listaContexto.setNombre("Subtipos de Solicitud");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ACTO);
        listaContexto.setNombre("Tipos de Acto Filtrada");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        /*
          * @autor          : JATENCIA 
          * @mantis        : 0015082 
          * @Requerimiento : 027_589_Acto_liquidación_copias 
          * @descripcion   : Se establecen variables para manejar el estado del 
          * atributo establecido " activo "
         */
        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_ACTO_DOS);
        listaContexto.setNombre("Tipos de Acto Completa");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);
        /* - Fin del bloque - */

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_IMPUESTO);
        listaContexto.setNombre("Tipos de Impuesto");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_DEPARTAMENTOS);
        listaContexto.setNombre("Departamentos");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_TARIFA);
        listaContexto.setNombre("Tipos de Tarifa");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_OPLOOKUP_TYPE);
        listaContexto.setNombre("Tipos de Búsqueda");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPO_CALCULO);
        listaContexto.setNombre("Tipos de Calculo");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_DERECHOS_REGISTRALES);
        listaContexto.setNombre("Derechos Registrales");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ESTADOS_FOLIO);
        listaContexto.setNombre("Estados Folio");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_OFICINA);
        listaContexto.setNombre("Tipos Oficina");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_DOMINIOS_NATURALEZA_JURIDICA);
        listaContexto.setNombre("Dominios Naturaleza Juridica");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES);
        listaContexto.setNombre("Circulos Registrales");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES_FECHA);
        listaContexto.setNombre("Circulos Registrales Asociados al SIR");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ESTADOS_ANOTACION);
        listaContexto.setNombre("Estados Anotación");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ACCIONES_NOTARIALES);
        listaContexto.setNombre("Acciones NOtariales");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_CATEGORIAS);
        listaContexto.setNombre("Categorias");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_CAUSALES_RESTITUCION);
        listaContexto.setNombre("Causales Restitución");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ROLES);
        listaContexto.setNombre("Roles");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_PAGO);
        listaContexto.setNombre("Tipos de Pago");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_UNIDADES_MEDIDA);
        listaContexto.setNombre("Unidades de Medida");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ESTACIONES);
        listaContexto.setNombre("Estaciones");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_NIVELES);
        listaContexto.setNombre("Niveles");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_VALIDACIONES_NOTA);
        listaContexto.setNombre("Validaciones Nota");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_PROCESOS);
        listaContexto.setNombre("Procesos");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_VALIDACIONES);
        listaContexto.setNombre("Validaciones");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_PROHIBICIONES);
        listaContexto.setNombre("Prohibiciones");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_VISIBILIDADES);
        listaContexto.setNombre("Visibilidades");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS);
        listaContexto.setNombre("Tarifas de Certificados");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO);
        listaContexto.setNombre("Tipos de Tarifas (Configurables por Círculo)");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD);
        listaContexto.setNombre("Naturalezas Juridicas por Entidad");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS);
        listaContexto.setNombre("Naturalezas Juridicas por Entidades Activas");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS);
        listaContexto.setNombre("Entidades Públicas");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS_ACTIVAS);
        listaContexto.setNombre("Entidades Públicas Activas");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_PERMISOS_CORRECCION);
        listaContexto.setNombre("Permisos de Corrección");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(EvnRespSistema.LISTA_RELACION_BANCOS_CIRCULO);
        listaContexto.setNombre("Relación Bancos Círculo");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        listaContexto = new ListaContexto();
        listaContexto.setId(
                EvnRespSistema.LISTA_CANALES_RECAUDO);
        listaContexto.setNombre("Canales de Recaudo");
        listaContexto.setDescripcion("");
        listasContexto.add(listaContexto);

        EvnRespSistema eventoRta = new EvnRespSistema(listasContexto, EvnSistema.CARGAR_LISTAS);
        return eventoRta;
    }

    private EventoRespuesta getTiposCertificados(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposCertificados = hermod.getTiposCertificadosIndividuales();
            ht.put(EvnRespSistema.LISTA_TIPOS_CERTIFICADOS, tiposCertificados);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposID(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposID = hermod.getTiposDocumento();
            ht.put(EvnRespSistema.LISTA_TIPOS_ID, tiposID);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getTiposIDNatural(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposIDNatural = hermod.getTipoDocNatural();
            ht.put(EvnRespSistema.LISTA_TIPOS_ID_NATURAL, tiposIDNatural);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getTiposIDJuridica(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposIDJuridica = hermod.getTipoDocJuridico();
            ht.put(EvnRespSistema.LISTA_TIPOS_ID_JURIDICA, tiposIDJuridica);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getModalidad(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List modalidad = hermod.getModalidad();
            ht.put(EvnRespSistema.LISTA_MODALIDAD, modalidad);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getDeterminacionInm(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List determinacionInm = hermod.getDeterminacionInm();
            ht.put(EvnRespSistema.LISTA_DETERMINACION_INM, determinacionInm);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getTipoPersona(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tipoPersona = hermod.getTipoPersona();
            ht.put(EvnRespSistema.LISTA_TIPOS_PERSONA, tipoPersona);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }
    
    private EventoRespuesta getTipoSexo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tipoSexo = hermod.getTipoSexo();
            ht.put(EvnRespSistema.LISTA_TIPOS_SEXO, tipoSexo);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getBancos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List bancos = hermod.getBancos();
            ht.put(EvnRespSistema.LISTA_BANCOS, bancos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getEjes(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List ejes = forseti.getEjes();
            ht.put(EvnRespSistema.LISTA_TIPOS_EJES, ejes);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposPredio(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposPredio = forseti.getTiposPredio();
            ht.put(EvnRespSistema.LISTA_TIPOS_PREDIO, tiposPredio);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposDocumento(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposDocumento = forseti.getTiposDocumento();
            ht.put(EvnRespSistema.LISTA_TIPOS_DOCUMENTO, tiposDocumento);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getGruposNaturalezaJuridica(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List gruposNat = forseti.getGruposNaturalezaJuridica();
            ht.put(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, gruposNat);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    /**
     * @author : Carlos Mario Torres Urina
     * @casoMantis : 0012705.
     * @actaReq : Acta - Requerimiento No
     * 056_453_Modificiación_de_Naturaleza_Jurídica
     * @change : Retorna Anotacion temporar.
     * @throws : DAOException.
     */
    private EventoRespuesta getGruposNaturalezaJuridicaAll(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List gruposNat = forseti.getGruposNaturalezaJuridicaAll();
            ht.put(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V, gruposNat);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getInteresesJuridicos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List interesJuridico = hermod.getInteresesJuridicos();
            ht.put(EvnRespSistema.LISTA_INTERES_JURIDICO, interesJuridico);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getGruposNaturalezaJuridicaCalificacion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List gruposNatCalificacion = forseti.getGruposNaturalezaJuridicaCalificacion();
            ht.put(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION, gruposNatCalificacion);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCausalesReimpresion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List causalesReImp = hermod.getCausalesReimpresion();
            ht.put(EvnRespSistema.LISTA_CAUSALES_REIMPRESION, causalesReImp);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposAlcanceGeografico(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List alcanceGeografico = hermod.getTiposAlcanceGeografico();
            ht.put(EvnRespSistema.LISTA_TIPOS_ALCANCE_GEOGRAFICO, alcanceGeografico);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposFotocopia(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposFotocopia = hermod.getTiposFotocopia();
            ht.put(EvnRespSistema.LISTA_TIPOS_FOTOCOPIA, tiposFotocopia);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCategoriasNotarias(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List categoriasNotarias = hermod.getCategoriasNotarias();
            ht.put(EvnRespSistema.LISTA_CATEGORIAS_NOTARIAS, categoriasNotarias);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getSubTiposAtencion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List subtiposAtencion = hermod.getSubTiposAtencion();
            ht.put(EvnRespSistema.LISTA_SUBTIPOS_ATENCION, subtiposAtencion);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getSubTiposSolicitud(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List subtiposSolicitud = hermod.getSubTiposSolicitud();
            ht.put(EvnRespSistema.LISTA_SUBTIPOS_SOLICITUD, subtiposSolicitud);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposActo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposActo = hermod.getTiposActo();
            ht.put(EvnRespSistema.LISTA_TIPOS_ACTO, tiposActo);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    /*
     *@autor          : JATENCIA
     * @mantis        : 0015082 
     * @Requerimiento : 027_589_Acto_liquidación_copias 
     * @descripcion   : Se realiza el llamado al metodo que se 
     * creo para cargar la lista de Tipos de Actos, sin filtro.
     */
    private EventoRespuesta getTiposActoDos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposActoDos = hermod.getTiposActoDos();
            ht.put(EvnRespSistema.LISTA_TIPOS_ACTO_DOS, tiposActoDos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    /* - Fin del bloque - */
    private EventoRespuesta getTiposImpuesto(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposImpuesto = hermod.getTiposImpuesto();
            ht.put(EvnRespSistema.LISTA_TIPOS_IMPUESTO, tiposImpuesto);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    /**
     * @author Fernando Padilla Velez
     * @change metodo que genera un evento respuesta con el listado de los tipos
     * de impuestos de todos los circulos
     */
    private EventoRespuesta getTiposImpuestoCirculo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposImpuestoCirculo = hermod.getTiposImpuestoCirculo();
            ht.put(EvnRespSistema.LISTA_TIPOS_IMPUESTO_CIRCULO, tiposImpuestoCirculo);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getDepartamentos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List departamentos = forseti.getDepartamentos(null);
            ht.put(EvnRespSistema.LISTA_DEPARTAMENTOS, departamentos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposTarifas(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposTarifas = hermod.getTiposTarifas();
            ht.put(EvnRespSistema.LISTA_TIPOS_TARIFA, tiposTarifas);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getOPLookupTypes(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List lookUpType = hermod.getOPLookupTypes();
            ht.put(EvnRespSistema.LISTA_OPLOOKUP_TYPE, lookUpType);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposCalculo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposCalculo = hermod.getTiposCalculo();
            ht.put(EvnRespSistema.LISTA_TIPO_CALCULO, tiposCalculo);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTipoDerechoRegistral(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposDerechoRegistral = hermod.getTipoDerechoRegistral();
            ht.put(EvnRespSistema.LISTA_DERECHOS_REGISTRALES, tiposDerechoRegistral);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getEstadosFolio(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List estadosFolio = forseti.getEstadosFolio();
            ht.put(EvnRespSistema.LISTA_ESTADOS_FOLIO, estadosFolio);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposOficina(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposOficina = forseti.getTiposOficina();
            ht.put(EvnRespSistema.LISTA_TIPOS_OFICINA, tiposOficina);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getDominiosNaturalezaJuridica(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List dominiosNatJuridica = forseti.getDominiosNaturalezaJuridica();
            ht.put(EvnRespSistema.LISTA_DOMINIOS_NATURALEZA_JURIDICA, dominiosNatJuridica);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCirculos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List circulos = forseti.getCirculos();
            ht.put(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES, circulos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCirculosFechaProd(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List circulos = forseti.getCirculosFechaProd();
            ht.put(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES_FECHA, circulos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getEstadosAnotacion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List estadosAnotacion = forseti.getEstadosAnotacion();
            ht.put(EvnRespSistema.LISTA_ESTADOS_ANOTACION, estadosAnotacion);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposAccionNotarial(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List accionesNotariales = hermod.getTiposAccionNotarial();
            ht.put(EvnRespSistema.LISTA_ACCIONES_NOTARIALES, accionesNotariales);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCategoriasReparto(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List categorias = hermod.getCategoriasReparto("nombre");
            ht.put(EvnRespSistema.LISTA_CATEGORIAS, categorias);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCausalesRestitucion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List causalesRestitucion = hermod.getCausalesRestitucion();
            ht.put(EvnRespSistema.LISTA_CAUSALES_RESTITUCION, causalesRestitucion);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta consultarRoles(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List roles = fenrir.consultarRoles();
            ht.put(EvnRespSistema.LISTA_ROLES, roles);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposPago(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposPago = hermod.getTiposPago();
            ht.put(EvnRespSistema.LISTA_TIPOS_PAGO, tiposPago);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getOPLookupCodes(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List unidadesMedida = hermod.getOPLookupCodes(COPLookupTypes.MEDIDAS_AREA);
            ht.put(EvnRespSistema.LISTA_UNIDADES_MEDIDA, unidadesMedida);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta consultarEstaciones(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List estaciones = fenrir.consultarEstaciones();
            ht.put(EvnRespSistema.LISTA_ESTACIONES, estaciones);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta consultarNiveles(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List niveles = fenrir.consultarNiveles();
            ht.put(EvnRespSistema.LISTA_NIVELES, niveles);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getValidacionNotas(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List validacionesNota = hermod.getValidacionNotas();
            ht.put(EvnRespSistema.LISTA_VALIDACIONES_NOTA, validacionesNota);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getListaProcesos(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List procesos = hermod.getListaProcesos();
            ht.put(EvnRespSistema.LISTA_PROCESOS, procesos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getValidaciones(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List validaciones = hermod.getValidaciones();
            ht.put(EvnRespSistema.LISTA_VALIDACIONES, validaciones);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getProhibiciones(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List prohibiciones = forseti.getProhibiciones();
            ht.put(EvnRespSistema.LISTA_PROHIBICIONES, prohibiciones);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposVisibilidad(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List visibilidades = hermod.getTiposVisibilidad();
            ht.put(EvnRespSistema.LISTA_VISIBILIDADES, visibilidades);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposTarifaCertificados(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tarifasCertificados = hermod.getTiposTarifaCertificados();
            ht.put(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS, tarifasCertificados);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getTiposTarifasConfiguradasPorCirculo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List tiposTarifaConfigurablesPorCirculo = hermod.getTiposTarifasConfiguradasPorCirculo();
            ht.put(EvnRespSistema.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO, tiposTarifaConfigurablesPorCirculo);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getNaturalezasJuridicasEntidades(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        List natJuridicasEntidad = null;
        List natJuridicasEntidadActivas = null;
        try {
            natJuridicasEntidad = hermod.getNaturalezasJuridicasEntidades();

        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }

        if (natJuridicasEntidad == null) {
            natJuridicasEntidad = new ArrayList();
        }

        natJuridicasEntidadActivas = new ArrayList();
        Iterator it = natJuridicasEntidad.iterator();
        while (it.hasNext()) {
            NaturalezaJuridicaEntidad nje = (NaturalezaJuridicaEntidad) it.next();
            if (nje.isActivo()) {
                natJuridicasEntidadActivas.add(nje);
            }
        }

        ht.put(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD, natJuridicasEntidad);
        ht.put(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS, natJuridicasEntidadActivas);
        return respuesta;
    }

    private EventoRespuesta getEntidadesReparto(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        List entidadesPublicas = null;
        List entidadesPublicasActivas = new ArrayList();
        try {
            entidadesPublicas = hermod.getEntidadesReparto();
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }

        if (entidadesPublicas == null) {
            entidadesPublicas = new ArrayList();
        }
        Iterator itEntidades = entidadesPublicas.iterator();
        while (itEntidades.hasNext()) {
            EntidadPublica entidad = (EntidadPublica) itEntidades.next();
            if (entidad.isActivo()) {
                entidadesPublicasActivas.add(entidad);
            }
        }

        ht.put(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS, entidadesPublicas);
        ht.put(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS_ACTIVAS, entidadesPublicasActivas);
        return respuesta;
    }

    private EventoRespuesta getPermisosCorreccion(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List permisosCorreccion = hermod.getPermisosCorreccion();
            ht.put(EvnRespSistema.LISTA_PERMISOS_CORRECCION, permisosCorreccion);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getRelacionBancosCirculo(EvnSistema evento) throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List bancos = hermod.getRelacionBancosCirculo();
            ht.put(EvnRespSistema.LISTA_RELACION_BANCOS_CIRCULO, bancos);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

    private EventoRespuesta getCanalesRecaudo(EvnSistema evento)
            throws EventoException {
        Hashtable ht = evento.getListas();
        EvnRespSistema respuesta = new EvnRespSistema(ht);
        try {
            List canales = hermod.getCanalesRecaudo(false);
            ht.put(EvnRespSistema.LISTA_CANALES_RECAUDO, canales);
        } catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
        return respuesta;
    }

}
