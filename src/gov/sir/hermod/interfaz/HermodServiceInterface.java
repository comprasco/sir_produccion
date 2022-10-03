package gov.sir.hermod.interfaz;

import gov.sir.core.eventos.comun.EvnPago;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoPk;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CategoriaPk;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.CheckItemPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CirculoTipoPagoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocumentoPagoCorreccion;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.NotaPk;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.ReproduccionSellos;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoPk;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacionPk;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoRelacionPk;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoEjecucion;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;
import gov.sir.core.negocio.modelo.ZonaNotarial;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced;
/**
 * @author Cesar Ramírez
 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
 *
 */
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;

import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.workflow.Message;
import org.portal.modelo.Transaccion;

/**
 * @author dlopez
 * @author mortiz Interfaz que se utiliza como fachada para el acceso a todos
 * los servicios ofrecidos por Hermod.
 * <p>
 * El servicio hermod se encarga de manejar el modelo operativo de la aplicación
 * SIR.
 * <p>
 * Se incluyen servicios para el manejo de turnos, procesos, fases y para la
 * consulta y actualización de variables de tipo operativo.
 */
public interface HermodServiceInterface {

    /**
     * Crea una solicitud persistente, dependiendo del tipo de
     * <code>Solicitud</code> recibida como parámetro.
     *
     * @param sol <code>Solicitud</code> que se va a hacer persistente.
     * @return <code>Solicitud</code> persistente.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.SolicitudCorreccion
     * @see gov.sir.core.negocio.modelo.SolicitudDevolucion
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     * @throws <code>Throwable</code>
     */
    Solicitud crearSolicitud(Solicitud sol) throws Throwable;

    /**
     * Obtiene el valor a liquidar de acuerdo al tipo de <code>Proceso</code>
     *
     * @param liquidacion representa la información de la
     * <code>Liquidacion</code> actual
     * @return un objeto <code>Liquidacion</code> con los detalles del valor
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @throws <code>Throwable</code>
     */
    Liquidacion liquidar(Liquidacion liquidacion) throws Throwable;

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    Turno procesarPago(Pago pago, String estacion) throws Throwable;

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>Throwable</code>
     */
    //Turno procesarPago(Pago pago, Hashtable parametros) throws Throwable;
    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param impresora Impresora en la que debe imprimirse el certificado
     * generado.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol) throws Throwable;

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param impresora Impresora en la que debe imprimirse el certificado
     * generado.
     * @param delegarUsuario indica si el turno debe ser creado o no en la
     * estación recibida como parámetro.
     * @param estacion Estacion desde la cual se está creando el turno.
     * @param user Usuario que está creando el turno.
     * @param rol Rol del usuario que está creando el turno.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol, boolean delegarUsuario) throws Throwable;

    /**
     * Valida el pago y completa la información correspondiente
     *
     * @param pago representa la información inicial del <code>Pago</code>
     * @return un objeto <code>Pago</code> con los detalles del mismo
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>Throwable</code>
     */
    Pago validarPago(Pago pago) throws Throwable;

    /**
     * Obtiene la lista de respuestas siguientes respecto a un
     * <code>Turno</code>
     *
     * @param turno el objeto <code>Turno</code> actual
     * @return la lista de respuestas (String) siguientes
     * @throws <code>Throwable</code>
     */
    List getRespuestasSiguientes(Turno turno) throws Throwable;

    /**
     * Obtiene la lista de fases (actividades) siguientes a partir de un
     * <code>Turno</code>
     *
     * @param turno el objeto <code>Turno</code> actual
     * @return la lista de fases siguientes respecto al <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getFasesSiguientes(Turno turno) throws Throwable;

    /**
     * Obtiene la lista de procesos que son iniciados por un rol.
     *
     * @param id_rol el identificador del rol del cual se buscan los procesos
     * que son iniciados.
     * @return una lista de objetos <code>Proceso</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>Throwable</code>
     */
    List getProcesosQueInicia(String id_rol) throws Throwable;
   
    /**
     * Crea registro de reproduccion de sellos
     *
     * @param Reproduccion el objeto reproduccion
     * @return boolean 
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws <code>Throwable</code>
     */
    boolean CreateReproduccionSellosReg(ReproduccionSellos Reproduccion) throws Throwable;
    
    
       /**
     * Captura Datos de Calificador para registrador
     *
     * @param idTurno El turno <code>Turno</code> Workflow
     * @param Circulo El <code>Circulo</code> Su id
     * @param Activo Si paso por registrador en numero
     * @return Lista de <code>ReproduccionSellos</code> 
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws <code>Throwable</code>
     */
    List getListReproduccionSellos(String idTurno, String Circulo, int Activo)  throws Throwable;
    /**
     * Obtiene la lista de turnos asociados a una estacion, un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code>.
     *
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>Throwable</code>
     */
    List getTurnos(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws Throwable;
    
    /**
     * Obtiene la lista de turnos asociados a un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code>.
     * Filtra los turnos radicados por REL
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>Throwable</code>
     */
    List getTurnosPMY(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws Throwable;
    
    /**
    * Devuelve true si el turno es rádicado por REL
    * @param idWorkflow 
    * @return Boolean 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    Boolean isTurnoREL(String idWorkflow) throws Throwable;
    
    /**
    * Devuelve el estado de Nota Devolutiva Notficada
    * @param idWorkflow 
    * @return String 
    * throws <code>Throwable</code>
    */
    String currentStateNotaNotificada(String idWorkflow) throws Throwable;
    
    /**
     * Devuelve la fecha en la que fue enviada al juzgado una nota devolutiva notificada
     * @param idWorkflow
     * @return
     * @throws Throwable 
     */
    String getFechaTurnoJuzgado(String idWorkflow) throws Throwable;
  
        /**
    * Retorna el limite de reasignacion 
    * @return int 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    int getReasignacion() throws Throwable;
    
    /**
     * Retorna el volor unico de un parametro en LookUpCodes
     * @param tipo
     * @param idCodigo
     * @return
     * @throws Throwable 
     */
    String getValorLookupCodes(String tipo, String idCodigo) throws Throwable;
    
    /**
     * Retorna todo el objeto de Lookup Codes segun el tipo
     * @param tipo
     * @return
     * @throws Throwable 
     */
    List getOPLookupCodesByTipo(String tipo) throws Throwable;
    
    /**
     * Retorna el parametro de copias de impresión a recibo
     * @param idCirculo
     * @return
     * @throws Throwable 
     */
    String getCopiaImp(String idCirculo) throws Throwable;
    
        /**
    * Devuelve true si el turno es devuelto
    * @param idWorkflow 
    * @return Boolean 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    Boolean isTurnoDevuelto(String idWorkflow) throws Throwable;
    
        /**
    * Retorna el numero de reasignaciones de un turno
    * @param turno 
    * @return int 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    int getLimiteReasignacion(Turno turno) throws Throwable;
    
        /**
    * Retorna el numero de notas informativas de un turno
    * @param turno 
    * @return int 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    int getNumNotasInformativas(Turno turno, String tipoNota) throws Throwable;
    
        /**
    * Retorna la estacion de calificador que envio a Confrontacion Correctiva
    * @param turno 
    * @return int 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    String getEstacionFromRevisor(Turno turno) throws Throwable;
    
    /**
     * Retorna la estacion de calificador que envió a Devolución
     * @param turno
     * @return
     * @throws Throwable 
     */
    String getEstacionFromRecursosNota (Turno turno) throws Throwable;
    
    /**
     * Retorna true si la estación se encuentra activa en calificacion
     * @param estacionId
     * @return
     * @throws Throwable 
     */
    boolean isEstacionActivaCalificador (String estacionId) throws Throwable;
    
    /**
     * Retorna una matricula bloqueada
     * @param idMatricula
     * @return
     * @throws Throwable 
     */
    List isMatriculaNotificacionDev(String idMatricula) throws Throwable;
    
       /**
    * Retorna el numero de notas necesarias para agregar o eliminar matriculas
    * @param turno 
    * @return int 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    int getNotasNecesarias(Turno turno) throws Throwable;
    
    /**
    * Retorna una lista con el control de matriculas
    * @param turnoID 
    * @return List 
    * @see gov.sir.core.negocio.modelo.Turno
    * throws <code>Throwable</code>
    */
    List getControlMatriculaTurno(String turnoID) throws Throwable;

    /**
     * Obtiene la lista de fases (actividades) asociadas con un
     * <code>Proceso</code> y un Rol.
     *
     * @param rol el rol del cual se quiere obtener la <code>Fase</code>.
     * @param proceso el <code>proceso</code> del cual se quiere obtener la
     * <code>Fase</code>
     * @return la lista de fases asociadas al <code>Proceso</code> y la
     * estacion.
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>Throwable</code>
     */
    List getFases(Rol rol, Proceso proceso) throws Throwable;

    /**
     * Obtiene la lista de procesos asociados con un Rol
     *
     * @return una lista de objetos </code>Proceso</code>
     * @param id_rol el identificador del rol del cual se buscan los procesos
     * asociados.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>Throwable</code>
     */
    List getProcesos(String id_rol) throws Throwable;

    /**
     * Dado un identificador de turno retorna el turno con todos sus atributos y
     * jerarquia.
     *
     * @return el <code>Turno</code> con todos sus atributos.
     * @param tId El Identificador del <code>Turno</code>que se quiere recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     */
    Turno getTurno(TurnoPk turnoId) throws Throwable;

    /**
     * Dado un identificador de <code>Turno</code> retorna el <code>Turno</code>
     * con todos sus atributos y jerarquia
     *
     * @return el <code>Turno</code> con todos sus atributos.
     * @param Identificador del <code>Turno</code> a recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Turno getTurnobyWF(String turnoIdWf) throws Throwable;

    /**
     * Dado un identificador de solicitud retorna el <code>DocumentoPago</code>
     * con todos sus atributos y jerarquia
     *
     * @return una lista <code>DocumentoPago</code> con todos sus atributos.
     * @param Identificador de la solicitud asociada al documetno de pago a
     * recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    List getDocumentoPagoBySolicitud(String solicitud) throws Throwable;

    /**
     * Dado un identificador de <code>DocumentoPago</code> retorna el
     * <code>DocPagoConsignacion</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoConsignacion</code> con todos sus atributos.
     * @param Identificador del <code>DocumentoPago</code> a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    DocPagoConsignacion getDocPagoConsignacion(DocumentoPago docPago) throws Throwable;

    /**
     * Dado un identificador de <code>DocumentoPago</code> retorna el
     * <code>DocPagoCheque</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoCheque</code> con todos sus atributos.
     * @param Identificador del <code>DocumentoPago</code> a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    DocPagoCheque getDocPagoCheque(DocumentoPago docPago) throws Throwable;

    /**
     * Dado un identificador de la consignacion retorna el
     * <code>DocPagoConsignacion</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoConsignacion</code> con todos sus atributos.
     * @param Identificador de la consignacion a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    DocPagoConsignacion getDocPagoConsignacion(String noConsignacion) throws Throwable;

    /**
     * Dado un identificador del cheque retorna el <code>DocPagoCheque</code>
     * con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoCheque</code> con todos sus atributos.
     * @param Identificador del cheque a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    DocPagoCheque getDocPagoCheque(String noCheque) throws Throwable;

    /**
     * Dado un <code>DocumentoPago</code> lo crea en la base de datos con todos
     * sus atributos
     *
     * @return el <code>DocumentoPago</code> con todos sus atributos.
     * @param <code>DocumentoPago</code> a crear
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    DocumentoPago crearDocumentoPago(DocumentoPago dp) throws Throwable;

    /**
     * Agrega una <code>Nota</code> a un <code>Turno</code>
     *
     * @return el Identificador de la <code>Nota</code> creada
     * @param nota <code>Nota</code>con todos los atributos excepto su
     * identificador
     * @param tId Identificador del <code>Turno</code> al que se le asocia la <code>
     * Nota</code>
     * @see gov.sir.core.negocio.modelo.Nota
     * @throws <code>Throwable</code>
     *
     */
    public NotaPk addNotaToTurno(Nota nota, TurnoPk tId) throws Throwable;

    /**
     * Crea un <code>Testamento</code> persistente y lo asocia a la solicitud de
     * un turno de registro de documentos.
     *
     * @param Turno El turno <code>Turno</code> al que se va a asociar el
     * <code>Testamento</code>.
     * @param testamento El <code>Testamento</code> que va a asociarse a la
     * solicitud de registro de documentos.
     * @return resultado<code>boolean</code> el resultado de el ingreso de el
     * Testamento.
     * @see gov.sir.core.negocio.modelo.Testamento
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    boolean addTestamentoToSolicitudRegistro(TurnoPk tid, Testamento testamento) throws Throwable;

    /**
     * Retorna el número máximo de impresiones autorizadas para un rol dentro de
     * un proceso dado.
     *
     * @param rol El rol sobre el que se hace la consulta.
     * @param p El proceso sobre el que se hace la consulta.
     * @return El número Máximo de impresiones autorizadas para un rol dentro de
     * un proceso dado.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>Throwable</code>
     */
    public int getNumeroMaximoImpresiones(Rol rol, Proceso p) throws Throwable;

    /**
     * *********************************************************************
     */
    /*                      PROCESO DE CONSULTAS                            */
    /**
     * *********************************************************************
     */
    /**
     * Modifica una búsqueda en una solicitud de consultas.
     *
     * @param solConsulta La <code>SolicitudConsulta</code> en la que se va a
     * modificar la <code>Busqueda</code>
     * @param b la <code>Busqueda</code> con los nuevos valores.
     * @return la <code>SolicitudConsulta</code> con la <code>Busqueda</code>
     * modificada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Busqueda
     * @throws <code>Throwable</code>
     */
    SolicitudConsulta updateBusquedaInSolicitudConsulta(SolicitudConsulta solConsulta, Busqueda b)
            throws Throwable;

    /**
     * Agrega una Solicitud de folios a una solicitud de Consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar la <code>SolicitudFolio</code>
     * @param solFolio la <code>SolicitudFolio</code> que va a ser asociada a la
     * <code>SolicitudConsulta</code>
     * @return la <code>SolicitudConsulta</code> con la
     * <code>SolicitudFolio</code> asociada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @throws <code>Throwable</code>
     */
    SolicitudConsulta addFolioToSolicitudConsulta(
            SolicitudConsulta solConsulta,
            SolicitudFolio solFolio)
            throws Throwable;

    /**
     * Agrega una búsqueda a una Solicitud de consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar la <code>Busqueda</code>
     * @param busc la <code>Busqueda</code> que va a ser asociada a la
     * <code>SolicitudConsulta</code>
     * @return la <code>Solicitud</code> con la <code>Busqueda</code> asociada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Busqueda
     * @throws <code>Throwable</code>
     */
    Solicitud addBusquedaToSolicitudConsulta(SolicitudConsulta solConsulta, Busqueda busc)
            throws Throwable;

    /**
     * Agrega un ciudadano a una Solicitud de consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar el <code>Ciudadano</code>
     * @param ciud El <code>Ciudadano</code> que va a ser asociado a la
     * <code>SolicitudConsulta</code>
     * @return la <code>Solicitud</code> con el <code>Ciudadano</code> asociado.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @throws <code>Throwable</code>
     */
    public Solicitud addCiudadanoToSolicitudConsulta(SolicitudConsulta solConsulta, Ciudadano ciud)
            throws Throwable;

    /**
     * *********************************************************************
     */
    /*                      REPARTO DE ABOGADOS                             */
    /**
     * *********************************************************************
     */
    /**
     * Retorna una lista con los subtipos de atención y los círculos de los
     * usuarios recibidos como parámetros.
     *
     * @param logins Lista que contiene los usuarios que van a ser buscados.
     * @param Circulo <code>Circulo</code> al que pertenecen los usuarios.
     * @return Lista que contiene los usuarios recibidos como parámetros con sus
     * respectivos subtipos de atencion y sus circulos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    public List getSubtiposAtencionByUsuarios(List logins, Circulo circulo) throws Throwable;

    /**
     * Retorna una lista con los subtipos de atención y los círculos de los
     * usuarios recibidos como parámetros.
     *
     * @param logins Lista que contiene los usuarios que van a ser buscados.
     * @param circuloId identificador del circulo al que pertenecen los
     * usuarios.
     * @return Lista que contiene los usuarios recibidos como parámetros con sus
     * respectivos subtipos de atencion y sus circulos.
     */
    //public List getSubtiposAtencionUsuarios(List logins, String circuloId) throws Throwable;
    /**
     * Agrega un <code>ProcesoReparto</code> al sistema.
     *
     * @return el <code>ProcesoReparto</code> persistente con su identificador.
     * @param prR <code>ProcesoReparto</code> con todos los atributos excepto el
     * identificador.
     * @see gov.sir.core.negocio.modelo.ProcesoReparto
     * @throws <code>Throwable</code>
     */
    ProcesoReparto addProcesoReparto(ProcesoReparto prR) throws Throwable;

    /**
     * Retorna el último reparto asociado a un <code>Turno</code>
     *
     * @param turnoId el identificador del <code>turno del cual se va a obtener
     * el último reparto.
     * @return el último <code>Reparto</code> asociado con el <code>Turno,
     * con id recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Reparto
     * @throws <code>Throwable</code>
     */
    Reparto getLastReparto(TurnoPk turnoId) throws Throwable;

    /**
     * Retorna un mapa con las listas de los usuarios clasificados por subtipo
     * de atencion
     *
     * @param List Lista con los usuarios que deben clasificar según su subtipo
     * de atención.
     * @return Map Mapa que contiene las asociaciones usuarios, subtipos de
     * atención.
     * @throws Throwable
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @see gov.sir.core.negocio.modelo.Turno
     */
    Map getUsuariosBySubtipoAtencion(List usuarios) throws Throwable;

    /**
     * *********************************************************************
     */
    /*                         REPARTO NOTARIAL                             */
    /**
     * *********************************************************************
     */
    /**
     * Retorna la categoría en la cual se debe clasificar una
     * <code>Minuta</code> de acuerdo con el valor o el número de unidades.
     *
     * @param minuta la <code>Minuta</code> que se va a clasificar.
     * @return la <code>Categoria</code> en la cual se debe clasificar la
     * <code>minuta</code>.
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @throws <code>Throwable</code>
     */
    Categoria getCategoriaClasificacionMinuta(Minuta minuta) throws Throwable;

    /**
     * Crea una <code>Minuta</code> persistente y la asocia a una solicitud de
     * reparto notarial de minutas.
     *
     * @param solicitud La <code>Solicitud</code> a la que se va a asociar la
     * <code>minuta</code>.
     * @param minuta La <code>Minuta</code> que va a asociarse a la solicitud de
     * reparto.
     * @return solicitud <code>SolicitudRepartoNotarial</code> con su
     * <code>Minuta</code> persistente asociada.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @throws <code>Throwable</code>
     */
    SolicitudRepartoNotarial addMinutaToSolicitudReparto(Solicitud solicitud, Minuta minuta)
            throws Throwable;

    /**
     * Anula la minuta recibida como parámetro.
     *
     * @param min La minuta que va a ser anulada
     * @param usuario El usuario que anula la minuta
     * @return true o false dependiendo del resultado de la anulación de la
     * minuta.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @throws <code>Throwable</code>
     */
    boolean anularMinuta(Minuta min, Usuario usuario) throws Throwable;

    /**
     * Permite la modificación de una minuta de reparto notarial.
     *
     * @param min la minuta que se va a modificar.
     * @param generarAuditoria flag que indica si se debe generar auditoría de
     * la modificación.
     * @param usuario Usuario que realiza la modificaicion.
     * @return la minuta con las modificaciones persistentes.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @throws <code>Throwable</code>
     */
    Minuta modificarMinuta(Minuta min, boolean generarAuditoria, Usuario usuario) throws Throwable;

    /**
     * Lista todas las minutas que no tienen asignado un reparto notarial y que
     * están asociadas con una <code>Vereda</code> dada.
     *
     * @param vereda identificador de una <code>Vereda</code>
     * @return lista con todas las minutas asociadas a una <code>Vereda</code>
     * que no han sido repartidas.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Vereda
     * @throws <code>Throwable</code>
     */
    List getMinutasNoAsignadasByVereda(VeredaPk vereda) throws Throwable;

    /**
     * Agrega una Oficina Origen a una <code>Categoria</code>
     *
     * @param cat El identificador de la <code>Categoria</code> en la que se va
     * a adicionar la <code> OficinaOrigen </code>
     * @param oficina La <code> OficinaOrigen </code> que se va a asociar a la
     * <code>Categoria</code>
     * @param true o false dependiendo del resultado de la operación.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     * @throws <code>Throwable</code>
     */
    boolean addOficinaOrigenToCategoria(CategoriaPk cat, OficinaOrigen oficina) throws Throwable;

    /**
     * Actualiza la información de una <code>Categoria</code> existente.
     *
     * @param cat El identificador de la <code>Categoria</code> que se va a
     * modificar.
     * @param nuevosDatos la información modificada de la <code>Categoria</code>
     * @return true o false dependiendo del resultado de la operación.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @throws <code>Throwable</code>
     */
    boolean updateCategoria(CategoriaPk cat, Categoria nuevosDatos) throws Throwable;

    /**
     * Obtiene una minuta persistente, dado el identificador del turno de
     * workflow en el cual fue creada.
     *
     * @param turnoIdWf Identificador del <code> Turno</code> asociado a la
     * creación de la  <code>Minuta</code>
     * @return <code>Minuta</code> persistente creada en el <code>Turno</code>
     * con id dado.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Minuta getMinutaByTurnoWF(String turnoIdWf) throws Throwable;

    /**
     * Obtiene una minuta persistente, dado el identificador del turno de
     * workflow en el cual fue creada.
     *
     * @param turnoIdWf Identificador del <code> Turno</code> asociado a la
     * creación de la  <code>Minuta</code>
     * @return <code>Minuta</code> persistente creada en el <code>Turno</code>
     * con id dado.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getMinutasByTurnoWF(String turnoIdWf) throws Throwable;

    /**
     * Realiza el Reparto de las Minutas que se encuentran disponibles dentro
     * del Círculo al que pertenece el <code>Usuario</code>.
     *
     * @param circ <code>Circulo</code> al que pertenece el <code>Usuario</code>
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String idExtraordinario) throws Throwable;

    /**
     * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes a
     * un Circulo Notarial.
     *
     * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a
     * realizar el Reparto
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoCirculoNotarialOrdinario(CirculoNotarial circuloNotarial, Usuario usuario)
            throws Throwable;

    /**
     * Realiza el consumo de secuencial sin no se reparte nada
     *
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public String consultarLastSecuencialCirculoNotarial(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo)
            throws Throwable;

    /**
     * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes a
     * un Circulo Notarial.
     *
     * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a
     * realizar el Reparto
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoCirculoNotarialExtraordinario(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo, String[] idsExtraordinaro)
            throws Throwable;

    /**
     * *********************************************************************
     */
    /*                    PANTALLAS ADMINISTRATIVAS                         */
    /**
     * *********************************************************************
     */
    /**
     * Obtiene la lista de tipos de alcances geograficos
     *
     * @return una lista de objetos de tipo <code>AlcanceGeografico</code>
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     * @throws <code>Throwable</code>
     */
    List getTiposAlcanceGeografico() throws Throwable;

    /**
     * Agrega un tipo de AlcanceGeografico en el sistema.
     *
     * @param alcance El tipo de alcance geografico que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     * @throws <code>Throwable</code>
     */
    boolean addTipoAlcanceGeografico(AlcanceGeografico ag) throws Throwable;

    /**
     * Obtiene la lista de tipos de Accion Notarial
     *
     * @return una lista de objetos <code>AccionNotarial</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>Throwable</code>
     */
    List getTiposAccionNotarial() throws Throwable;

    /**
     * Agrega una AccionNotarial en el sistema.
     *
     * @param an La <code>AccionNotarial</code> que se va a agregar.
     * @param usuario que va adicionar la accion notarial
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>Throwable</code>
     */
    boolean addAccionNotarial(AccionNotarial an, Usuario usuario) throws Throwable;

    /**
     * Editar una AccionNotarial en el sistema.
     *
     * @param an La <code>AccionNotarial</code> que se va a editar.
     * @param usuario que va a modificar la accion notarial
     * @return true o false dependiendo del resultado de la actualización.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>Throwable</code>
     */
    boolean editarAccionNotarial(AccionNotarial an, Usuario usuario) throws Throwable;

    /**
     * Obtiene la lista de objetos de tipo <code>Banco</code>
     *
     * @return una lista de objetos <code>Banco</code>
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>Throwable</code>
     */
    List getBancos() throws Throwable;

    /**
     * Obtiene el objeto <code>Banco</code> dado su ID
     *
     * @return <code>Banco</code>
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>Throwable</code>
     */
    Banco getBancoByID(String idBanco) throws Throwable;

    /**
     * Agrega un <code>Banco</code> en el sistema.
     *
     * @param banco El <code>Banco</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>Throwable</code>
     */
    boolean addBanco(Banco banco) throws Throwable;

    /**
     * Obtiene la lista con los Causales de Reimpresion existentes en el
     * sistema.
     *
     * @return Lista con objetos de tipo <code>OPLookupCodes</code> con los
     * causales de reimpresión existentes en el sistema
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    List getCausalesReimpresion() throws Throwable;

    /**
     * Obtiene la lista de causales de restitución.
     *
     * @return una lista de objetos <code>CausalRestitucion</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     * @throws <code>Throwable</code>
     */
    List getCausalesRestitucion() throws Throwable;

    /**
     * Agrega un Causal de Restitución en el sistema.
     *
     * @param causal El <code>CausalRestitucion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     * @throws <code>Throwable</code>
     */
    boolean addCausalRestitucion(CausalRestitucion cr) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>CausalRestitucion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>CausalRestitucion</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    boolean eliminarCausalRestitucion(CausalRestitucion valNota) throws Throwable;

    /**
     * Modifica un objeto de tipo <code>CausalRestitucion</code> dentro de la
     * configuración del sistema.
     * <p>
     * El método genera una excepción si ya existe un causal de restitución con
     * el nombre del objeto pasado como parámetro.
     *
     * @param causal objeto de tipo <code>CausalRestitucion</code> con todos sus
     * atributos.
     * @return identificador del objeto <code>CausalRestitucion</code>
     * modificado.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public boolean editCausalRestitucion(CausalRestitucion causal) throws Throwable;

    /**
     * Obtiene los <code>OPLookupCodes</code> asociados con el
     * <code>OPLookupType</code> pasado como parámetro.
     *
     * @param tipo LookupCode que representa el tipo de <code>Proceso</code>
     * @return Lista de objetos de tipo <code>OPLookupCodes</code>
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    List getOPLookupCodes(String tipo) throws Throwable;

    /**
     * Agrega un LookupCode en el sistema.
     *
     * @param luc El <code>OPLookupCodes</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    boolean addOPLookupCode(OPLookupCodes luc) throws Throwable;

    /**
     * Edita un LookupCode en el sistema.
     *
     * @param datoAEditar El <code>OPLookupCodes</code> que se va a editar.
     * @param dato El <code>OPLookupCodes</code> con los nuevos datos.
     * @return true o false dependiendo del resultado de la edicion.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    boolean updateOPLookupCode(OPLookupCodes datoAEditar, OPLookupCodes dato) throws Throwable;

    /**
     * Obtiene la lista con los LookupTypes existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>OPLookupTypes</code>
     * @see gov.sir.core.negocio.modelo.OPLookupTypes</code>
     * @throws <code>Throwable</code>
     */
    List getOPLookupTypes() throws Throwable;

    /**
     * Agrega un LookupType en el sistema.
     *
     * @param lut El <code>OPLookupTypes</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @throws <code>Throwable</code>
     */
    boolean addOPLookupType(OPLookupTypes lut) throws Throwable;

    /**
     * Edita un LookupType en el sistema.
     *
     * @param datoAEditar El <code>OPLookupTypes</code> que se va a editar.
     * @param dato El <code>OPLookupTypes</code> que tiene los nuevos datos.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @throws <code>Throwable</code>
     */
    boolean updateOPLookupType(OPLookupTypes datoAEditar, OPLookupTypes dato) throws Throwable;

    /**
     * Obtiene la lista de Tipos de Actos.
     *
     * @return una lista de objetos de tipo <code>TipoActo</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>Throwable</code>
     */
    List getTiposActo() throws Throwable;

    /**
     * Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo
     * Solicitado
     *
     * @return Objeto <code>TipoActo</code>
     * @param TipoActo.ID tid
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    TipoActo getTipoActo(TipoActoPk tid) throws Throwable;

    /*
        * @autor         : JATENCIA 
        * @mantis        : 0015082 
        * @Requerimiento : 027_589_Acto_liquidación_copias 
        * @descripcion   : Se declara el metodo en interfacez
     */
    /**
     * Obtiene la lista de Tipos de Actos.
     *
     * @return una lista de objetos de tipo <code>TipoActo</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>Throwable</code>
     */
    List getTiposActoDos() throws Throwable;

    /**
     * Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo
     * Solicitado
     *
     * @return Objeto <code>TipoActo</code>
     * @param TipoActo.ID tid
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    TipoActo getTipoActoDos(TipoActoPk tid) throws Throwable;

    /* - Fin del bloque - */
    /**
     * Agrega un tipo de acto en el sistema.
     *
     * @param tac El <code>TipoActo</code> que se va a agregar.
     * @param El usuario que va adicionar el tipo de acto
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>Throwable</code>
     */
    boolean addTipoActo(TipoActo tac, Usuario usuario) throws Throwable;

    /**
     * Modifica un tipo de acto en el sistema.
     *
     * @param tac El <code>TipoActo</code> que se va a agregar.
     * @param usuario que va a modificar el tipo de acto
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>Throwable</code>
     */
    boolean updateTipoActo(TipoActo tac, Usuario usuario) throws Throwable;

    /**
     * Obtiene la lista con los Tipos de Cálculos existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoCalculo</code>
     * @see gov.sir.core.negocio.modelo.TipoCalculo</code>
     * @throws <code>Throwable</code>
     */
    List getTiposCalculo() throws Throwable;

    /**
     * Agrega un tipo de cálculo en el sistema.
     *
     * @param tac El <code>TipoCalculo</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     * @throws <code>Throwable</code>
     */
    boolean addTipoCalculo(TipoCalculo tc) throws Throwable;

    /**
     * Obtiene la lista con los Tipos de Fotocopias existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoFotocopia</code>
     * @see gov.sir.core.negocio.modelo.TipoFotocopia</code>
     * @throws <code>Throwable</code>
     */
    List getTiposFotocopia() throws Throwable;

    /**
     * Agrega un tipo de fotocopia en el sistema.
     *
     * @param fot El <code>TipoFotocopia</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     * @throws <code>Throwable</code>
     */
    boolean addTipoFotocopia(TipoFotocopia fot) throws Throwable;

    /**
     * Obtiene la lista de tipos de Impuestos.
     *
     * @return una lista de objetos <code>TipoImpuesto</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     * @throws <code>Throwable</code>
     */
    List getTiposImpuesto() throws Throwable;

    /**
     * .
     * @author Fernando Padilla Velez
     * @change Modificado para el caso MANTIS 135_141_Impuesto Meta, Obtiene la
     * lista de tipos de Impuestos por Circulo.
     * @return una lista de objetos <code>TipoImpuestoCirculo</code> con todos
     * sus atributos.
     * @see gov.sir.core.negocio.modelo.TipoImpuestoCirculo
     * @throws <code>Throwable</code>
     */
    List getTiposImpuestoCirculo() throws Throwable;

    /**
     * Agrega un tipo de impuesto en el sistema.
     *
     * @param timp El <code>TipoImpuesto</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     * @throws <code>Throwable</code>
     */
    boolean addTipoImpuesto(TipoImpuesto timp) throws Throwable;

    /**
     * Obtiene la lista de tipos de Subtipos de Atencion
     *
     * @return una lista de objetos <code>SubtipoAtencion</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    List getSubTiposAtencion() throws Throwable;

    /**
     * Obtiene la lista de tipos de Subtipos de Atencion completa (listado de
     * subtipos de solicitud y tipos acto)
     *
     * @return una lista de objetos <code>SubtipoAtencion</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    List getSubTiposAtencionCompleto() throws Throwable;

    /**
     * Agrega un subtipo de atención en el sistema.
     *
     * @param sat El <code>SubtipoAtencion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    boolean addSubTipoAtencion(SubtipoAtencion sat) throws Throwable;

    /**
     * Edita un subtipo de atención en el sistema.
     *
     * @param sat El <code>SubtipoAtencion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    boolean updateSubTipoAtencion(SubtipoAtencion sat) throws Throwable;

    /**
     * Obtiene la lista de calificadores del circulo dado
     *
     * @return una lista de objetos <code>Usuario</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    List getCalificadoresSubtipoAtencion(Circulo cir, SubtipoAtencion sub) throws Throwable;

    /**
     * Obtiene la lista de tipos de Subtipos de Solicitud
     *
     * @return una lista de objetos <code>SubtipoSolicitud</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>Throwable</code>
     */
    List getSubTiposSolicitud() throws Throwable;

    /**
     * Agrega el orden de un usuario en el subtipo solicitud
     *
     * @return true o false dependiendo del resultado de la adicion.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    boolean addOrdenSubtipoAtencion(SubtipoAtencion sub, Usuario usu, String orden, Circulo cir) throws Throwable;

    /**
     * Remueve el orden de un usuario en el subtipo solicitud
     *
     * @return true o false dependiendo del resultado de la operacion.
     * @see gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion
     * @throws <code>Throwable</code>
     */
    boolean removeOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion, Circulo cir) throws Throwable;

    /**
     * Agrega un subtipo de solicitud en el sistema.
     *
     * @param sts El <code>SubtipoSolicitud</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la edicion.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>Throwable</code>
     */
    boolean addSubTipoSolicitud(SubtipoSolicitud sts) throws Throwable;

    /**
     * Edita un subtipo de solicitud en el sistema.
     *
     * @param sts El <code>SubtipoSolicitud</code> que se va a editar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>Throwable</code>
     */
    boolean updateSubTipoSolicitud(SubtipoSolicitud sts) throws Throwable;

    /**
     * Obtiene la lista de tipos de certificados
     *
     * @return una lista de objetos <code>TipoCertificado</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>Throwable</code>
     */
    List getTiposCertificado() throws Throwable;

    /**
     * Obtiene la lista de tipos de certificados individuales
     *
     * @return una lista de objetos <code>TipoCertificado</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>Throwable</code>
     */
    List getTiposCertificadosIndividuales() throws Throwable;

    /**
     * Agrega un tipo de certificado en el sistema.
     *
     * @param tcert El <code>TipoCertificado</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>Throwable</code>
     */
    boolean addTipoCertificado(TipoCertificado tcert) throws Throwable;

    /**
     * Obtiene la lista de tipos de consultas.
     *
     * @return una lista de objetos <code>TipoConsulta</code>
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     * @throws <code>Throwable</code>
     */
    List getTiposConsulta() throws Throwable;

    /**
     * Agrega un tipo de consulta en el sistema.
     *
     * @param tcons El <code>TipoConsulta</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     * @throws <code>Throwable</code>
     */
    boolean addTipoConsulta(TipoConsulta tcons) throws Throwable;

    /**
     * Obtiene la lista de tipos de recepción de petición
     *
     * @return una lista de objetos <code>TipoRececpcionPeticion</code>
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     * @throws <code>Throwable</code>
     */
    List getTiposRecepcionPeticion() throws Throwable;

    /**
     * Agrega un tipo de recepcion peticion en el sistema.
     *
     * @param trp El <code>TipoRecepcionPeticion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     * @throws <code>Throwable</code>
     */
    boolean addTipoRecepcionPeticion(TipoRecepcionPeticion trp) throws Throwable;

    /**
     * Obtiene la lista con los Tipos de Tarifas existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoTarifa</code>
     * @see gov.sir.core.negocio.modelo.TipoTarifa</code>
     * @throws <code>Throwable</code>
     */
    List getTiposTarifas() throws Throwable;

    /**
     * Agrega un Tipo de Tarifa en el sistema.
     *
     * @param tar El <code>TipoTarifa</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     * @throws <code>Throwable</code>
     */
    boolean addTipoTarifa(Tarifa ttar) throws Throwable;

    /**
     * Obtiene la lista con las Tarifas existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>Tarifa</code>
     * @see gov.sir.core.negocio.modelo.Tarifa</code>
     * @throws <code>Throwable</code>
     */
    List getTarifas(TipoTarifa tipoTar) throws Throwable;

    /**
     * Agrega una Tarifa en el sistema.
     *
     * @param tar La <code>Tarifa</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @throws <code>Throwable</code>
     */
    boolean addTarifa(Tarifa tar) throws Throwable;

    /**
     * Retorna la lista de validaciones que se deben hacer dependiendo del tipo
     * de solicitud
     *
     * @param solicitud Solicitud sobre la que se van a obtener las
     * validaciones.
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>Throwable</code>
     */
    List getValidacionesSolicitud(Solicitud solicitud) throws Throwable;

    /**
     * Obtiene el valor del rango de aceptación para un pago
     *
     * @param tipo String que representa el tipo de <code>Proceso</code>
     * @return un valor que representa el rango de aceptación.
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>Throwable</code>
     */
    double getRangoAceptacionPago(String tipo) throws Throwable;

    /**
     * Adiciona un <code>Folio a un <code>Turno</code>.
     *
     * @param matricula matricula asociada al folio.
     * @param tID identificador del <code>Turno</code>
     * @param user El <code>Usuario</code> que generó la
     * <code>SolicitudFolio</code>
     * @return el identificador de la solicitud folio que se adicionó al
     * <code>Turno</code>.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws Throwable
     */
    SolicitudFolioPk addFolioToTurno(String matricula, TurnoPk tID, Usuario user) throws Throwable;

    /**
     * Remueve la asociación de un <code>Folio a un <code>Turno</code>.
     *
     * @param matricula matricula asociada al <code>folio</code>.
     * @param tID Identificador del <code>Turno</code>de registro.
     * @return true o false dependiendo del resultado de la eliminacion.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws Throwable
     */
    boolean removeFolioFromTurno(String matricula, TurnoPk tID) throws Throwable;

    /**
     * Registra la matrícula que fue desasociada en los datos básicos del turno
     *
     * @param folio
     * @param tID
     * @return
     * @throws DAOException
     */
    public boolean registrarMatriculaEliminadaTurno(String matricula,
            TurnoPk tID) throws Throwable;

    /**
     * Remueve la asociación de un folio a un turno
     *
     * @param matricula matricula asociada al folio.
     * @param tID Identificador del turno de registro.
     * @return true o false dependiendo del resultado de la eliminacion.
     * @throws HermodException
     */
    public boolean addCambioMatriculaAuditoria(String folioViejo, String folionuevo, TurnoPk tID, Usuario user) throws Throwable;

    /**
     * @author : Julio Alcázar Rivas Caso Mantis : 02359 verifica la asociación
     * de un folio a un turno
     * @param tID Identificador del turno de registro.
     * @return true o false dependiendo de si se hizo cambio al turno.
     * @throws HermodException
     */
    public boolean getCambioMatriculaAuditoria(TurnoPk tID) throws Throwable;

    /**
     * Obtiene la lista de tipos de documentos.
     *
     * @return una lista de objetos <code>TipoDocumento</code>
     * @see gov.sir.core.negocio.modelo.TipoDocumento
     * @throws <code>Throwable</code>
     */
    public List getTiposDocumento() throws Throwable;
    
    /**
     * Obtiene la lista de tipos de documento para personas juridicas;
     *
     * @return  una lista con las tipos de documetos para personas juridicas
     * @throws HermodException
     */
    public List getTipoDocJuridico() throws Throwable;
    
    /**
     * Obtiene la lista de tipos de documento para personas naturales;
     *
     * @return  una lista con las tipos de documetos para personas naturales
     * @throws HermodException
     */
    public List getTipoDocNatural() throws Throwable;
    
    /**
     * Obtiene la lista de modalidades
     *
     * @return  una lista con las modalidades
     * @throws HermodException
     */
    public List getModalidad() throws Throwable;
    
    /**
     * Obtiene la lista de determinaciones del inmueble
     *
     * @return  una lista con las determinacion del inmueble
     * @throws HermodException
     */
    public List getDeterminacionInm() throws Throwable;
    
    /**
     * Obtiene la lista de tipos de persona
     *
     * @return  una lista con los tipos de persona
     * @throws HermodException
     */
    public List getTipoPersona() throws Throwable;
    
    /**
     * Obtiene la lista de tipos de sexo
     *
     * @return  una lista con los sexos
     * @throws HermodException
     */
    public List getTipoSexo() throws Throwable;

    /**
     * Retorna una lista con las sucursales asociadas a un <code>Banco</code>
     *
     * @param idBanco Identificador del <code>Banco</code> del cual se van a
     * buscar las <code>SucursalesBanco</code>
     * @return Lista que contiene las <code>SucursalesBanco</code> asociadas con
     * el <code>Banco</code> pasado como parámetro.
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>Throwable</code>
     */
    List getSucursalesBanco(BancoPk idBanco) throws Throwable;

    /**
     * Agrega una sucursal a un <code>Banco</code> existente en el sistema.
     *
     * @param suc <code>SucursalBanco</code> que va a ser agregada.
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @throws <code>Throwable</code>
     */
    boolean addSucursalBanco(SucursalBanco sucursal) throws Throwable;

    /**
     * Retorna una lista con los tipos de Derechos Registrales existentes en el
     * sistema.
     *
     * @return Lista que contiene objetos de tipo <code>TipoDerechoReg</code>
     * existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     * @throws <code>Throwable</code>
     */
    List getTipoDerechoRegistral() throws Throwable;

    /**
     * Agrega un tipo de Derecho Registral a la configuración del sistema.
     *
     * @param tipo <code>TipoDerechoReg</code> que va a ser agregado.
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     * @throws <code>Throwable</code>
     */
    boolean addTipoDerechoRegistral(TipoDerechoReg tipo) throws Throwable;

    /**
     * Retorna una lista con las Categorías de Reparto Notarial existentes en el
     * sistema.
     *
     * @return Lista que contiene objetos de tipo <code>Categoria</code>
     * existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>Throwable</code>
     */
    List getCategoriasReparto(String orden) throws Throwable;

    /**
     * Agrega una Categoría de Reparto Notarial a la configuración del sistema.
     *
     * @param categoria <code>Categoria</code> que va a ser agregada.
     * @param usuario que adiciona la categoria de reparto
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>Throwable</code>
     */
    boolean addCategoriaReparto(Categoria categoria, Usuario usuario) throws Throwable;

    /**
     * Edita una Categoría de Reparto Notarial a la configuración del sistema.
     *
     * @param categoria <code>Categoria</code> que va a ser editada.
     * @return true o false dependiendo del resultado de la edición.
     * @return El usuario que va a mopdificar la categoria
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>Throwable</code>
     */
    boolean updateCategoriaReparto(Categoria categoria, Usuario usuario) throws Throwable;

    /**
     * Lista todos las tipos de notas existentes en el sistema.
     *
     * @return Lista con objetos <code>TipoNota</code> existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @throws <code>Throwable</code>
     */
    List getTiposNotas() throws Throwable;

    /**
     * Lista las tipos de nota para el <code>Proceso</code> recibido
     *
     * @param proceso Proceso del que se deben obtener las notas.
     * @param proceso <code>Proceso</code> del cual se desean obtener los tipos
     * de notas.
     * @return Lista de objetos <code>TipoNota</code> que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @throws <code>Throwable</code>
     */
    List getTiposNotasProceso(ProcesoPk proceso) throws Throwable;

    /**
     * Obtiene el valor a liquidar para un acto de acuerdo al tipo de acto y la
     * cuantia
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @return un objeto <code>Acto</code> con los detalles del valor
     * @see gov.sir.core.negocio.modelo.Acto
     */
    Acto getLiquidacionActo(Acto acto) throws Throwable;

    /**
     * Valida la cuantia de un acto con respecto a lo que se edita.
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @param i <code>int</code>posicion del acto
     * @return true o false dependiendo de la validacion.
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public boolean validacionActo(Acto acto, int i) throws Throwable;

    /**
     * Obtiene una lista de las estaciones recibo del sistema.
     *
     * @return una lista de objetos <code>EstacionRecibo</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    List getEstacionesRecibo() throws Throwable;

    /**
     * Obtiene una estación recibo dado su identificador.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return <code>EstacionRecibo </code> con todos sus atributos.
     * @throws Throwable
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid) throws Throwable;
    
    /**
     * Elimina todos los actos segun una solicitud.
     * @param idSolicitud
     * @throws <code>Throwable </code>
     */
    void eliminarActos(String idSolicitud) throws Throwable;
    
    /**
     * Actualiza un recurso
     * @param recurso
     * @param turnoWF
     * @throws Throwable 
     */
    void updateRecurso(Recurso recurso, String turnoWF) throws Throwable;
    
    /**
     * Elimina un recurso
     * @param idRecurso
     * @param turnoWF
     * @throws Throwable 
     */
    void deleteRecurso(String idRecurso, String turnoWF) throws Throwable;
    
    /**
     * Ejecuta un Insert, Update o Delete
     * @param sql
     * @throws Throwable 
     */
    void executeDMLFromSQL(String sql) throws Throwable;
    
    /**
     * Obtiene un string segun una consulta SQL
     * @param sql
     * @return
     * @throws Throwable 
     */
    String getStringByQuery(String sql) throws Throwable;

    /**
     * Obtiene una estación recibo dado su identificador.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return <code>EstacionRecibo </code> con todos sus atributos.
     * @throws Throwable
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid, long numeroProceso) throws Throwable;

    /**
     * Setea una estación recibo a la configuración del sistema
     *
     * @param circuloID Identificador del círculo donde está la estación
     * @param eRecibo <code>EstacionRecibo</code> que va a ser agregada a la
     * configuración del sistema.
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    public void setEstacionRecibo(CirculoPk circuloID, EstacionRecibo sRecibo, Usuario user)
            throws Throwable;

    /**
     * Resetea el último número del recibo de la estacion. Recibe los nueno
     * último número del recibos.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code> cuyo
     * secuencial va a ser reseteado.
     * @param ultimoNumeroActualizado Nuevo valor para el último número de la
     * <code>EstacionRecibo</code>.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public boolean resetUltimoNumeroEstacionRecibo(EstacionReciboPk oid, long ultimoNumeroActualizado)
            throws Throwable;

    /**
     * Obtiene el siguiente número de recibo según la secuencia configurada
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return el siguiente número de la secuencia de recibos para la
     * <code>EstacionRecibo</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>Throwable</code>
     */
    public long getNextNumeroRecibo(EstacionReciboPk oid, Usuario user, long idProceso) throws Throwable;

    /**
     * Finaliza el DAO
     *
     * @throws <code>HermodException</code> cuando hay un error con la conexión
     */
    public void finalizar() throws Throwable;

    /**
     * Agrega un <code>numero de anotaciones de un folio<code> </code> a una
     * solicitud.
     *
     * @param numAnotaciones <code>long</code> que se va a adicionar.
     * @param solicitud <code>Solicitud<code> a la que se agregará
     * el <code>numAnotaciones</code>
     * @return <code>true</code> si se agrego el valor exitosamente
     * <code>false</code> en caso contrario.
     * @see gov.sir.core.negocio.modelo.SoliciutConsulta
     * @throws <code>Throwable</code>
     */
    public boolean setAnotacionestoSolicitud(long numAnotaciones, Solicitud solicitud) throws Throwable;

    /**
     * Retorna la lista de validaciones que se deben hacer dependiendo del tipo
     * de certificafo
     *
     * @param solicitud Solicitud sobre la que se van a obtener las
     * validaciones.
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>Throwable</code>
     */
    List getValidacionesCertificado(TipoCertificadoPk tipoId) throws Throwable;

    /**
     * Realiza las validaciones a las matriculas especificadas dada una lista
     *
     * @param matricula
     * @return
     * @throws ForsetiException hay una lista de excepciones por matrícula
     */
    public boolean validarMatriculas(List validaciones, List matriculas) throws Throwable;

    /**
     * Retorna la lista de validaciones para registro
     *
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    List getValidacionesRegistro() throws Throwable;

    /**
     * Retorna la lista de validaciones para registro de Turno Manual
     *
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    List getValidacionesRegistroTurnoManual() throws Throwable;

    /**
     * Dado un identificador de turno retorna el turno con todos sus atributos y
     * jerarquia exceptuando las anotaciones.
     *
     * @return el <code>Turno</code> con todos sus atributos exceptuando las
     * anotaciones.
     * @param tId El Identificador del <code>Turno</code>que se quiere recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public Turno getTurnoSinAnotaciones(TurnoPk turnoId) throws Throwable;

    /**
     * Obtiene una lista de los  <code>TipoPago</code> del sistema.
     *
     * @return una lista de objetos <code>TipoPago</code>
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>Throwable</code>
     */
    public List getTiposPago() throws Throwable;

    /**
     * Obtiene una lista de los  <code>CirculoTipoPago</code> del sistema.
     *
     * @return una lista de objetos <code>CirculoTipoPago</code>
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     * @throws <code>Throwable</code>
     */
    public List getCirculoTiposPago(CirculoPk cirID) throws Throwable;

    /**
     * Adiciona un <code>CirculoTipoPago</code> a la configuración del sistema
     *
     * @param datos <code>CirculoTipoPago</code> que va a ser agregado a la
     * configuración del sistema.
     * @return identificador de la <code>CirculoTipoPago</code>agregada a la
     * configuración del sistema.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     * @throws <code>Throwable</code>
     */
    public CirculoTipoPagoPk addCirculoTipoPago(CirculoTipoPago dato) throws Throwable;

    /**
     * Elimina un <code>CirculoTipoPago</code> de la configuración del sistema
     *
     * @param datos <code>CirculoTipoPago</code> que va a ser removido de la
     * configuración del sistema.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     * @throws <code>Throwable</code>
     */
    public void removeCirculoTipoPago(CirculoTipoPago dato) throws Throwable;

    /**
     * Obtiene un círculo de proceso dado su identificador
     *
     * @param cpID El identificador del círculo de proceso
     * @return El <code>CirculoProceso</code> correspondiente al identificador
     * suministrado
     * @throws <code>Throwable</code>
     */
    public CirculoProceso getCirculoProceso(CirculoProcesoPk cpID) throws Throwable;

    /**
     * Realiza la Restitución de Reparto Notarial para la Notaría que la
     * solicita. El proceso de Restitución realiza las siguientes acciones: 1.
     * Marca la <code>Solicitud</code> como aceptada. 2. Anula la
     * <code>Minuta</code> asociada con la <code>Solicitud</code> 3. Coloca la
     * Notaría que realizó la solicitud como primera, dentro de la cola de
     * Notarías para la categoría a la que pertenecía la <code>Minuta</code>
     *
     * @param idSolicitud Identificador de la solicitud de restitución.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRestitucionRepartoNotarial(String idSolicitud) throws Throwable;

    /**
     * Servicio que permite asignar a una <code>SolicitudConsulta</code>
     * persistente, el valor para el atributo numeroMaximoBusquedas recibido
     * como parámetro.
     *
     * @param numMaximo El número máximo de búsquedas permitido para la
     * solicitud.
     * @param solicitud La <code>SolicitudConsulta</code> a la que se va a
     * asignar el número máximo de búsquedas.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso),
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     */
    public boolean setNumMaxBusquedasToSolicitudConsulta(SolicitudConsulta solicitud, int numMaximo)
            throws Throwable;

    /**
     * Servicio que permite adicionar el texto correspondiente a una resolución
     * de restitución de reparto notarial a una
     * <code>SolicitudRestitucionReparto</code>
     *
     * @param resolucion El texto que va a ser asociado a la
     * <code>Solicitud</code>
     * @param solicitud La <code>SolicitudRestitucionReparto</code> a la que se
     * va a asignar la resolución.
     * @param observaciones Comentario que explica por qué fue aceptada o
     * rechazada una solicitud de restitución
     * @param fechaResolucion fecha en la que fue creada la resolución de
     * restitución.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     */
    public boolean addResolucionToSolicitudRestitucion(
            SolicitudRestitucionReparto solicitud,
            String resolucion, String observaciones, Date fechaResolucion)
            throws Throwable;

    /**
     * Servicio que permite marcar como rechazada una
     * <code>SolicitudRestitucionReparto</code>
     *
     * @param solicitud La <code>SolicitudRestitucionReparto</code> que va a ser
     * marcada como rechazada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     */
    public boolean rechazarSolicitudRestitucion(SolicitudRestitucionReparto solicitud) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>Categoría</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param categoria La <code>Categoria</code> que va a ser eliminada.
     * @param usuario que va a eliminar la categoria
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public boolean eliminarCategoria(Categoria categoria, Usuario usuario) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>AlcanceGeografico</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param alcance El <code>AlcanceGeografico</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public boolean eliminarAlcanceGeografico(AlcanceGeografico alcance) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>TipoFotocopia</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoFot El <code>TipoFotocopia</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public boolean eliminarTipoFotocopia(TipoFotocopia tipoFot) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>TipoCalculo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoCalc El <code>TipoCalculo</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public boolean eliminarTipoCalculo(TipoCalculo tipoCalc) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>TipoDerechoReg</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoDerecho El <code>TipoDerechoReg</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public boolean eliminarTipoDerechoRegistral(TipoDerechoReg tipoDerecho) throws Throwable;

    /**
     * Servicio que permite eliminar un Tipo de  <code>AccionNotarial</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param accion La <code>AccionNotarial</code> que va a ser eliminada.
     * @param usuario que va a eliminar la accion notarial
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public boolean eliminarAccionNotarial(AccionNotarial accion, Usuario usuario) throws Throwable;

    /**
     * Servicio que permite asociar un rango de matrículas y las respectivas
     * solicitudes folio a una <code>SolicitudRegistro</code> El servicio
     * realiza la validación de los rangos y verifica la existencia de los
     * folios que va a asociar. En caso de que no exista alguno de los folios,
     * debe partir los rangos.
     *
     * @param matIncial Valor de la matrícula inicial del rango.
     * @param matFinal Valor de la matrícula final del rango.
     * @param solicitud <code>Solicitud</code> a la que se asociará el rango de
     * folios.
     * @param user El <code>Usuario</code> que generó la
     * <code>SolicitudFolio</code>
     * @return Lista con objetos de tipo <code>RangoFolio</code> creados a
     * partir de los valores de matrículas inicial y final recibidos como
     * parámetros.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.RangoFolio
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     */
    public List addRangoFoliosToSolicitudRegistro(
            String matIncial,
            String matFinal,
            Solicitud solicitud,
            Usuario user,
            boolean validarAsociar)
            throws Throwable;

    /**
     * Modifica el encabezado del documento en una solicitud de Registro.
     *
     * @author Cesar Ramírez
     * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
     * @param solReg La <code>SolicitudRegistro</code> en la que se va a
     * modificar el <code>Documento</code>
     * @return la <code>Solicitudregistro</code> con el <code>Documento</code>
     * modificado.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Documento
     * @throws <code>Throwable</code>
     */
    public SolicitudRegistro updateEncabezadoInSolicitud(SolicitudRegistro solReg, Usuario usuario) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>Banco</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param banco El <code>Banco</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public boolean eliminarBanco(Banco banco) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>SucursalBanco</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param sucursal <code>SucursalBanco</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public boolean eliminarSucursalBanco(SucursalBanco sucursal) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>Tarifa</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tarifa <code>Tarifa</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public boolean eliminarTarifa(Tarifa tarifa) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>TipoTarifa</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoTarifa <code>TipoTarifa</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public boolean eliminarTipoTarifa(TipoTarifa tipoTarifa) throws Throwable;

    /**
     * Servicio que permite eliminar un <code>SubtipoSolicitud</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param subtipoSolicitud <code>SubtipoSolicitud</code> que va a ser
     * eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public boolean eliminarSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>OPLookupTypes</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param type Objeto <code>OPLookupTypes</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public boolean eliminarLookupType(OPLookupTypes type) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>OPLookupCodes</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param code Objeto <code>OPLookupCodes</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public boolean eliminarLookupCode(OPLookupCodes code) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>TipoImpuesto</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param impuesto Objeto <code>TipoImpuesto</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public boolean eliminarTipoImpuesto(TipoImpuesto impuesto) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>SubtipoAtencion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param atencion Objeto <code>SubtipoAtencion</code> que va a ser
     * eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public boolean eliminarSubtipoAtencion(SubtipoAtencion atencion) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>TipoActo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param acto Objeto <code>TipoActo</code> que va a ser eliminado.
     * @param usuario que va a eliminar el tipo de acto
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public boolean eliminarTipoActo(TipoActo acto, Usuario usuario) throws Throwable;

    /**
     * A partir de un identificador de relacion, busca el conjunto de
     * fasesProceso de las cuales tiene registros
     *
     * @return List< Fase >
     */
    public List buscarFasesRelacionadasPorRelacionId(String relacionId) throws Throwable;

    /**
     * Consulta las <code>EstacionRecibo</code> para un círculo específico
     *
     * @return la lista de los objetos <code>EstacionRecibo</code> solicitados
     * @throws Throwable
     */
    public List consultarEstacionesReciboPorCirculo(Circulo circulo) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>EstacionRecibo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param categoria La <code>EstacionRecibo</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public boolean eliminarEstacionRecibo(EstacionRecibo estacionRecibo) throws Throwable;

    /**
     * Agrega un <code>ValidacionNota</code> en el sistema.
     *
     * @param valVota La <code>ValidacionNota</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     * @throws <code>HermodException</code>
     */
    public boolean addValidacionNota(ValidacionNota valNota) throws Throwable;

    /**
     * Servicio que permite eliminar una <code>ValidacionNota</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota La <code>ValidacionNota</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public boolean eliminarValidacionNota(ValidacionNota valNota) throws Throwable;

    /**
     * Obtiene la lista de objetos de tipo <code>ValidacionNota</code>
     *
     * @return una lista de objetos <code>ValidacionNota</code>
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     * @throws <code>HermodException</code>
     */
    public List getValidacionNotas() throws Throwable;

    /**
     * Actualiza los subtipos de atención de un <code>Usuario</code>
     * <p>
     * Utilizado desde las pantallas administrativas.</p>
     *
     * @param usuario  <code>Usuario</code> al que se le adicionarán los subtipos
     * @param listaUsuarios  <code>List</code> con los ids de usuario que son
     * calificadores
     * @param circulo  <code>Circulo</code> circulo al que pertenece el usuario
     * que se le esta configurando el suubtipo de atención.
     * @return <code>boolean</code> si puede actualizar el usuario
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public boolean updateSubtiposAtencionEnUsuario(Usuario usuario, List listaUsuarios, Circulo circulo) throws Throwable;

    /**
     * Obtiene la lista de procesos existentes en el sistema
     *
     * @return una lista de objetos <code>Proceso</code> que están disponibles
     * en el sistema
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getListaProcesos() throws Throwable;

    /**
     * Obtiene la lista de objetos de tipo <code>FaseProceso</code> con las
     * fases relacionadas al <code>Proceso</code> pasado como parámetro
     *
     * @return una lista de objetos <code>Procesos_V</code> que con las fases
     * del proceso pasado como parámetro
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.FaseProceso
     */
    public List getFasesDeProceso(Proceso proceso) throws Throwable;

    /**
     * Adiciona una <code>TipoCertificadoValidacion<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, una <code>TipoCertificadoValidacion</code> con el identificador
     * pasado dentro del parámetro.
     *
     * @param valNota objeto <code>TipoCertificadoValidacion</code> con sus
     * atributos, incluido el identificador.
     * @return identificador del TipoCertificadoValidacion generado.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public TipoCertificadoValidacionPk addTipoCertificadoValidacion(TipoCertificadoValidacion valNota)
            throws Throwable;

    /**
     * Servicio que permite eliminar una <code>TipoCertificadoValidacion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>TipoCertificadoValidacion</code> que va a ser
     * eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public boolean eliminarTipoCertificadoValidacion(TipoCertificadoValidacion valNota)
            throws Throwable;

    /**
     * Obtiene una lista de objetos tipo <code>TipoCertificadoValidacion </code>
     * filtrada por <code>TipoCertificado</code>
     *
     * @param tipoCertificado  <code>TipoCertificado</code> utilizado para el
     * filtro
     * @return una lista de objetos <code>TipoCertificadoValidacion</code>
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public List getTiposCertificadosValidacionByTipoCertificado(TipoCertificado tipoCertificado)
            throws Throwable;

    /**
     * Servicio que permite consultar la lista de objetos
     * <code>Validacion</code> existentes en el sistema.
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @return <code>List</code> con la lista de validaciones disponibles en el
     * sistema
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     */
    public List getValidaciones() throws Throwable;

    /**
     * Adiciona un <code>TipoNota<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, una <code>TipoNota</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param tipoNota objeto <code>TipoNota</code> con sus atributos, incluido
     * el identificador.
     * @param usuario que va adicionar el tipo de nota
     * @return identificador del TipoNota generado.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public TipoNotaPk addTipoNota(TipoNota tipoNota, Usuario usuario) throws Throwable;

    /**
     * Actualiza un <code>TipoNota<code> en la configuración del sistema.<p>
     * <p>
     * @param tipoNota objeto <code>TipoNota</code> con sus atributos
     * @param usuario que modifica el tipo de nota
     * @return identificador del TipoNota generado.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public TipoNotaPk updateTipoNota(TipoNota tipoNota, Usuario usuario) throws Throwable;

    /**
     * Valida que el turno pueda ser anulado.
     * <p>
     * @param idTurno objeto <code>Turno.ID</code> con sus atributos
     * @return true = Si se puede anular, false = Si no se puede anular.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno.ID
     */
    public boolean validarAnulacionTurno(TurnoPk idTurno) throws Throwable;

    /**
     * Anular el turno
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public void anularTurno(Turno turno) throws Throwable;

    /**
     * Habilitar el turno
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public void habilitarTurno(Turno turno) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>TipoNota</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>TipoNota</code> que va a ser eliminada.
     * @param usuario que va a eliminar el tipo de nota
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public boolean eliminarTipoNota(TipoNota tipoNota, Usuario usuario) throws Throwable;

    /**
     * Adiciona un <code>CheckItem<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>CheckItem</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param item objeto <code>CheckItem</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del <code>CheckItem</code> generado.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public CheckItemPk addCheckItem(CheckItem item) throws Throwable;

    /**
     * Edita un <code>CheckItem<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en que no exista en la base de datos,
     * un <code>CheckItem</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param item objeto <code>CheckItem</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del <code>CheckItem</code> generado.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public CheckItemPk updateCheckItem(CheckItem item) throws Throwable;

    /**
     * Servicio que permite eliminar un objeto <code>CheckItem</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param item la <code>CheckItem</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public boolean eliminarCheckItem(CheckItem item) throws Throwable;

    /**
     * Este servicio permite modificar el valor de la última liquidación
     * asociada a un turno (Utilizado en el proceso de devoluciones).
     *
     * @param turno El identificador del turno sobre el cual se va a modificar
     * la última liquidación.
     * @param liquidacion La <code>Liquidacion</code> con los valores que van a
     * ser modificados.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public boolean updateUltimaLiquidacion(TurnoPk turnoId, Liquidacion liquidacion) throws HermodException;

    /**
     * Obtiene una lista de objetos tipo <code>CheckItem </code> filtrada por
     * <code>SubtipoSolicitud</code>
     *
     * @param subtipo  <code>SubtipoSolicitud</code> utilizado para el filtro
     * @return una lista de objetos <code>CheckItem</code>
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.CheckItem
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public List getCheckItemsBySubtipoSolicitud(SubtipoSolicitud subtipo) throws Throwable;

    /**
     * Agrega un <code>Usuario</code> a un <code>Turno</code>
     *
     * @param user  <code>Usuario</code> que será asignado al <code>Turnoz</code>
     * @param turno Objeto <code>Turno</code> al que será asignado el
     * <code>Usuario</code>
     * @return true o false dependiendo del resultado de la operación.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public boolean setUsuarioToTurno(Usuario user, Turno turno) throws Throwable;

    /**
     * Obtiene una lista con los tipos de visibilidad para las notas
     * informativas, existentes en el sistema.
     *
     * @return Lista con los nombres de los tipos de visibilidad existentes en
     * el sistema.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Nota
     */
    public List getTiposVisibilidad() throws Throwable;

    /**
     * Obtiene la lista de turnos asociados a una <code>Fase</code>, un
     * <code>Proceso</code> y un <code>Circulo</code>.
     *
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> sobre el cual se buscan los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>Throwable</code>
     */
    List getTurnosFase(Proceso proceso, Fase fase, Circulo circulo) throws Throwable;

    /**
     * Modifica una solicitud de Certificado.
     *
     * @param solCer La <code>SolicitudCertificado</code> que se va a modificar.
     * @return la <code>SolicitudCertificado</code> actualizada.
     * @see gov.sir.core.negocio.modelo.SolicitudCerificado
     * @throws <code>Throwable</code>
     */
    public SolicitudCertificado updateSolicitudCertificado(SolicitudCertificado solCer)
            throws Throwable;

    /**
     * Modifica una solicitud de Certificado o Consulta.
     *
     * @param solicitud La <code>Solicitud</code> que se va a modificar.
     * @return la <code>Solicitud</code> actualizada.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>HermodException</code>
     */
    public Solicitud updateSolicitud(Solicitud solicitud)
            throws Throwable;

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el <code>Proceso</code> pasado como
     * parámetro, la fase y si es devolutivo o informativa.
     * <p>
     * El método lanza una excepción si no se encuentra el <code>Proceso</code>
     * con el identificador pasado como parámetro.
     *
     * @return una lista de objetos <code>TipoNota</code> ordenados
     * alfabéticamente de acuerdo al nombre, y que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getTiposNotaProceso(ProcesoPk proceso, String fase, boolean devolutiva)
            throws Throwable;

    /**
     * Obtiene la lista de Tipos de Tarifas de Certificados
     *
     * @return una lista de objetos de tipo <code>OPLookupCodes</code> con todos
     * sus atributos.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    List getTiposTarifaCertificados() throws Throwable;

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el <code>Proceso</code> pasado como
     * parámetro y la fase
     * <p>
     * El método lanza una excepción si no se encuentra el <code>Proceso</code>
     * con el identificador pasado como parámetro.
     *
     * @return una lista de objetos <code>TipoNota</code> ordenados
     * alfabéticamente de acuerdo al nombre, y que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    List getTiposNotaProceso(ProcesoPk proceso, String fase) throws Throwable;

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el <code>Proceso</code> pasado como
     * parámetro y la fase
     * <p>
     * El método lanza una excepción si no se encuentra el <code>Proceso</code>
     * con el identificador pasado como parámetro.
     *
     * @return una lista de objetos <code>TipoNota</code> ordenados
     * alfabéticamente de acuerdo al nombre, y que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    List getTiposNotaProcesoByTpnaDevolutiva(ProcesoPk proceso, boolean devolutiva) throws Throwable;

    /**
     * Actualiza el atributo orden para todas las relaciones Usuario - SubTipo
     * de Atención definidas dentro del Circulo recibido como parámetro.
     * <p>
     * El usuario que tenía el orden 1 pasa al final de la lista, y en los demás
     * casos, el orden se reduce en una unidad.
     *
     * @throws <code>Throwable</code>
     * @param circulo El <code>Circulo </code> en el que se debe actualizar el
     * orden para las relaciones Usuario - SubtipoAtencion.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * del proceso.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public boolean actualizarRotacionReparto(Circulo circulo) throws Throwable;

    /**
     * Avanza los <code>Turnos</code> de la lista ingresada, correspondiente a
     * una Estación y la fase donde se encuentran.
     *
     * @return <code>true</code> o <code>false</code> dependiendo de si se pudo
     * realizar el avance.
     * @param turnos tiene la información de los <code>Turno</code> a avanzar.
     * @param fase tiene la información de la actividad en la que se encuentran
     * los <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Fase
     * @throws <code>HermodException</code>
     */
    boolean avanzarMasivoTurnosNuevo(List turnos, Fase fase, Hashtable parametros, Usuario usuario) throws Throwable;

    /**
     * Obtiene el siguiente número de recibo pero NO avanza la secuencia, si se
     * supera la secuencia devuelve -1
     *
     * @param oid
     * @return
     * @throws Throwable
     */
    long getNextNumeroReciboSinAvanzar(EstacionReciboPk oid, Usuario user, long idProceso) throws Throwable;

    /**
     * Avanza la secuencia de recibos en el avance configurado, si se supera la
     * secuencia definida en la estación se lanza una excepción
     *
     * @param oid
     * @param avance
     * @return
     * @throws Throwable
     */
    long avanzarNumeroRecibo(EstacionReciboPk oid, long avance, long idProceso) throws Throwable;

    /**
     * Traslada un turno a un usuario específico
     *
     * @param turno
     * @param usuario
     * @return
     * @throws Throwable
     */
    boolean trasladarTurnoSAS(Turno turno, Estacion estacion) throws Throwable;

    /**
     * Obtiene una lista de objetos <code>Turno</code> que estan sociados con el
     * <code>Folio</code> correspondiente al numero de matricula ingresado.
     *
     * @param matricula <code>String</code>
     * @return una lista de objetos <code>Turno</code>.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Folio
     */
    List getTurnosByMatricula(String matricula) throws Throwable;

    /**
     * Hace persistente el motivo por el cual se incrementó el secuencial de un
     * recibo.
     *
     * @param usuario Usuario que incrementa el secuencial.
     * @param secuencial Valor al cual se incrementa el secuencial de recibos
     * @param motivo Motivo por el cual se incrementó el secuencial del recibo.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws Throwable
     */
    public boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial, String motivo)
            throws Throwable;

    /**
     * Obtiene un objeto <code>Fase</code> dado su ID.
     *
     * @param id_fase <code>String</code> el identificador de la fase
     * @return un objeto <code>Fase</code>
     * @see gov.sir.core.negocio.modelo.Fase
     * @throws <code>Throwable</code>
     */
    Fase getFase(String idFase) throws Throwable;
 /**
     * Actualiza el Documento Pago al cual se le hizo la correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>HermodException</code>
     */
    public boolean actualizarEstadoDocumento(DocumentoPago Documento)throws Throwable;
    /**
     * Obtiene los turnos anteriores asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * anteriores.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getTurnosAnteriores(String idTurno) throws Throwable;

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getTurnosSiguientes(String idTurno) throws Throwable;

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getTurnosSiguientesTestamento(String idTurno) throws Throwable;

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getTurnosSiguientesDevolucion(String idTurno) throws Throwable;

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param parametros tabla con parametros para procesar una solicitud de
     * certificados masivos.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>Throwable</code>
     */
    Turno procesarPagoMasivos(Pago pago, Hashtable parametros) throws Throwable;

    /**
     * Obtiene el valor de la variable probabilidad revisión calificación.
     *
     * @return El valor de la variable probabilidad revisión calificación.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    String getProbabilidadRevisionCalificacion() throws Throwable;

    /**
     * Obtiene el valor de la cantidad de impresiones de certificados permitidas
     * en correcciones.
     *
     * @return El valor de la variable numero impresiones de certificados en
     * correcciones.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    String getNumeroImpresionesCertificadosEnCorrecciones() throws Throwable;

    /**
     * Modifica el valor de la variable probabilidad revisión calificación.
     *
     * @param nuevoValor el valor que va a ser asignado a la variable
     * probabilidad revisión calificación.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    boolean updateProbabilidadRevisionCalificacion(String nuevoValor) throws Throwable;

    /**
     * Obtiene el listado de las Solicitudes sin turnos asociados dentro de un
     * rango de fechas dado.
     *
     * @param fechaInicial Fecha de inicio del rango
     * @param fechaFinal Fecha de finalización del rango
     * @return Lista con todas las solicitudes sin turnos asociados, dentro del
     * rango de fechas recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    public Map getSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws HermodException;

    public long getNumeroSolicitudesSinTurno(Circulo circulo, Date fechaInicial,
            Date fechaFinal) throws HermodException;

    /**
     * Elimina la <code>Solicitud</code> recibida como parámetro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    boolean deleteSolicitud(Solicitud solicitud) throws Throwable;

    /**
     * Elimina las Solicitudes asociadas al turno.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    boolean deleteTurnoAnterior(SolicitudPk sid) throws Throwable;

    /**
     * Elimina todas las solicitudes
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    public boolean removeSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws HermodException;

    /**
     * Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en la
     * Base de Datos que son configurables por círculo
     * <p>
     * Se establece como criterio de ordenamiento el id del tipo de tarifa.
     *
     * @return una lista de objetos <code>TipoTarifa</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    List getTiposTarifasConfiguradasPorCirculo() throws Throwable;

    /**
     * Obtiene una lista de los objetos <code>Tarifa</code> existentes en la
     * Base de Datos del círculo indicado
     * <p>
     * @return una lista de objetos <code>Tarifa</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public List getTiposTarifasPorCirculo(Circulo c) throws Throwable;

    /**
     * Obtiene el tipo de una fase.
     * <p>
     * El tipo de una fase puede ser Automático o Manual.
     *
     * @param fase_id el identificador de la fase de la cual se desea obtener su
     * tipo.
     * @return el tipo de fase (Automático o Manual) asociado con la fase.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.constantes.CFase
     */
    public String getTipoFase(String fase_id) throws Throwable;

    /**
     * Obtiene una solicitud dado su identificador.
     * <p>
     * La solicitud debe incluir el listado de Liquidaciones asociadas.
     *
     * @param solicitud_id el identificador de la solicitud que se desea
     * obtener.
     * @return la solicitud con el id recibido como parámetro y su listado de
     * liquidaciones asociadas.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public Solicitud getSolicitudById(String solicitud_id) throws Throwable;

    /**
     * Obtiene una lista de turnos dentro de un rango dado, que han pasado por
     * una fase dada.
     *
     * @param idFase identificador de la fase por la cual debe haber pasado el
     * turno.
     * @param turnoInicial identificador del turno inicial dentro del rango en
     * el que se va a realizar la consulta.
     * @param turnoFinal identificado del turno final dentro del ranqo en el que
     * se va a realizar la consulta
     * @return Lista de todos los turnos que han pasado por la fase dada y que
     * están comprendidos entre el rango de turnos dado.
     * @throws <code>Throwable </code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getRangoTurnosByFase(String idFase, String turnoInicial, String turnoFinal) throws Throwable;

    /**
     * Modifica la categoría de clasificación de una solicitud de registro.
     *
     * @param clasificacion Valor que va a ser asignado al atributo categoría de
     * clasificación de la solicitud de registro.
     * @param turnoActualizado El turno que va a ser actualizado.
     * @return <code> true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>Throwable </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public boolean updateClasificacionSolicitudRegistro(String clasificacion, Turno turnoActualizado) throws Throwable;

    /**
     * Hace persistente la información de un Pago, y lo asocia a una solicitud.
     * <p>
     * Método desarrollado para cumplir con los requerimientos específicos del
     * proceso de registro de documentos.
     *
     * @param pago El <code>Pago</code> que se va a hacer persistente.
     * @param estacion El identificador de la estación desde la cual se va a
     * asociar el <code>Pago</code>
     * @return <code> El <code>Pago</code> que se ha hecho persistente.
     * @throws <code>Throwable </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago registrarPago(Pago pago, String estacion) throws Throwable;

    /**
     * Valida que la solicitud de certificado sea de tipo "ASOCIADO" y que tenga
     * un padre solicitud de registro que tenga como folio asociado el folio que
     * se pasa por parámetros
     *
     * @param solCer La <code>SolicitudCertificado</code> que se va a validar
     * @param folioID La <code>Folio.ID</code> Identificador del folio a validar
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @throws <code>Throwable</code>
     */
    void validarMatriculaMesaControl(SolicitudCertificado solCer, FolioPk folioID) throws Throwable;
   /**
     * retorna verdadero si encontro algun dato de este documento pago en correccion forma pago
     * @param idDocumento
     * @return
     * @throws HermodException
     */
    boolean validacionCorreccion(String idDocumento) throws Throwable;
    /**
     * Obtiene el número de intentos permitidos para impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la consulta.
     * @return El número de intentos permitidos para impresión.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>Throwable</code>
     */
    String getNumeroIntentosImpresion(String proceso) throws Throwable;

    /**
     * Modifica el número de intentos permitidos para impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la modificación.
     * @param valor El valor que va a ser asignado a la variable.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>Throwable</code>
     */
    boolean updateNumeroIntentosImpresion(String proceso, String valor) throws Throwable;
  /**
     * Guarda el Documento Pago al cual se le va a hacer la correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    boolean guardardocumentopagoantesdecorreccion(String idDocumento, String iduser) throws Throwable;

    /**
     * Obtiene el tiempo de espera configurado para el proceso de impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la consulta.
     * @return el tiempo de espera configurado para el proceso de impresión.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>Throwable</code>
     */
    String getTiempoEsperaImpresion(String proceso) throws Throwable;

    /**
     * Modifica el tiempo de espera configurado para el proceso de impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la modificación.
     * @param valor El valor que va a ser asignado a la variable.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>Throwable</code>
     */
    boolean updateTiempoEsperaImpresion(String proceso, String valor) throws Throwable;

    /**
     * Obtiene el estado de pago de una Solicitud.
     * <p>
     * Si la Solicitud tiene un <code>Pago </code> asociado, se retorna <code> true
     * </code>, en el caso contrario se retorna <code>false </code>
     *
     * @param solicitud La <code>Solicitud</code> en la cual se va a consultar
     * el estado de los pagos.
     * @return <code>true </code> si la solicitud ya tiene un <code>Pago</code>
     * o <code>false </code> en el caso contrario.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>Throwable</code>
     */
    boolean getEstadoPagoSolicitud(Solicitud solicitud) throws Throwable;

    /**
     * Crea un <code>Turno </code> de Registro y su respectiva instancia de
     * Workflow.
     * <p>
     * Si el turno tiene certificados asociados, también crea las instancias de
     * los turnos de certificados individuales.
     *
     * @param solicitud La <code>SolicitudRegistro</code> desde la cual se va a
     * generar el turno y su instancia de workflow.
     * @param usuarioSir <code>Usuario</code> que crea el turno.
     * @return El <code>Turno</code> que fue creado.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Turno crearTurnoRegistro(SolicitudRegistro solicitud, Usuario usuarioSir) throws Throwable;

    /**
     * Crea un <code>Turno</code> manual asociado a un pago. Este tipo de turnos
     * no genera instancia en Workflow.
     *
     * @param pPago El pago asociado al turno
     * @param sAnio El año del turno
     * @param sCirculo El círculo del turno
     * @param sProceso El proceso del turno
     * @param sIdTurno El identificador del turno
     * @param dFechaInicio La fecha de inicio para el turno
     * @param uUsuario El usuario que realiza el ingreso del turno
     * @return El <code>Turno</code> que fue creado.
     * @throws Throwable <code>Throwable</code>
     */
    public Turno crearTurnoManual(Pago pPago, String sAnio, String sCirculo, String sProceso,
            String sIdTurno, Date dFechaInicio, Usuario uUsuario) throws Throwable;

    /**
     * Obtiene el listado de turnos calificados por un <code>Usuario</code> y
     * que no han pasado por la fase de firma del registrador.
     *
     * @param username El identificador del usuario del cual se están
     * consultando sus turnos calificados.
     * @param Circulo circulo del calificador
     * @return Lista con los turnos calificados por el <code>Usuario</code> dado
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List getTurnosByUsuarioCalificador(long usuario, Circulo circulo) throws Throwable;

    /**
     * Obtiene el listado de turnos radicados en este dia y que se encuentren
     * actualmente en confrontación.
     *
     * @return Lista con los turnos validos
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    List listarTurnosRegistroParaAgregarCertificadosAsociados(Circulo cir) throws Throwable;

    /**
     * Agrega una <code>SolicitudFolio</code> a una SolicitudHija asociada a un
     * Turno.
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param solicitud <code>Solicitud</code> a la cual se va a asociar la
     * <code>SolicitudFolio</code>
     * @param solFolio <code> <code>SolicitudFolio</code> que va a ser asociada.
     * @return Turno actualizado.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Turno addSolicitudFolioToSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws Throwable;

    /**
     * Agrega una <code>Solicitud</code> como SolicitudHija a la SolicitudPadre.
     *
     * @param solPadre <code>Solicitud</code> a la cual se va a asociar la
     * <code>Solicitud</code>
     * @param solHija <code> <code>Solicitud</code> que va a ser asociada.
     * @return Solicitud actualizada.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    Solicitud addSolicitudHija(Solicitud solPadre, Solicitud solHija) throws Throwable;

    /**
     * Elimina una <code>SolicitudFolio</code> de una SolicitudHija asociada a
     * un Turno.
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param solicitud <code>Solicitud</code> de la cual se va a eliminar la
     * <code>SolicitudFolio</code>
     * @param solFolio <code> <code>SolicitudFolio</code> que va a ser eliminada
     * @return Turno actualizado.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Turno removeSolicitudFolioFromSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws Throwable;

    /**
     * Modifica el atributo ajuste de una <code>SolicitudRegistro</code>
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param valor el booleano que va a ser asignado al atributo ajuste de la
     * <code>SolicitudRegistro</code>
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    boolean updateAjusteInTurnoRegistro(Turno turno, boolean ajuste) throws Throwable;

    /**
     * Retorno un mapa en donde las llaves son los subtipos de atención y los
     * valores corresponden a la lista de los usuarios del círculo especificado
     * rotados
     *
     * @param circulo Identificador del círculo
     * @return Mapa [SubtipoAtencion, Lista usuarios en orden]
     * @throws Throwable
     */
    public Map getUsuariosPorSubtiposDeAtencionRotados(Circulo circulo) throws Throwable;

    /**
     * Coloca la respuesta a un recurso de apelación o reposición o del tipo que
     * corresponda
     *
     * @param recurso Debe tener seteados todos sus identificadores y el
     * textoRespuesta
     * @throws Throwable
     */
    void setRespuestaRecurso(Recurso recurso) throws Throwable;
    
    /**
     * Agrega una notificación de una nota devolutiva
     * @param notify
     * @throws Throwable 
     */
    void notificarNotaDevolutiva(NotificacionNota notify) throws Throwable;
    
    /**
     * Retorna la notificacion de una nota devolutiva
     * @param turnoWF
     * @return
     * @throws HermodException 
     */
    List getNotaDevNotificada (String turnoWF) throws Throwable;
    
    /**
     * Retorna los días habiles
     * @param idCirculo
     * @param fecha
     * @return
     * @throws Throwable 
     */
    int diasHabiles(String idCirculo, String fecha) throws Throwable;
    
        /**
     * Agrega una lista de notas informativas.
     *
     * @param notasInformativas 
     * @throws Throwable
     */
    void addNotasInformativas(List notasInformativas) throws Throwable;
    
            /**
     * Agrega el estado del turno REL.
     *
     * @param status 
     * @param url
     * @param idWorkflow
     * @throws Throwable
     */
    void setStatusREL(String status, String url, String idWorkflow) throws Throwable;
    
    /**
     * Actualiza el estado de la nota devolutiva notificada
     * @param state
     * @param idWorkflow
     * @throws Throwable 
     */
    void setStateNotaNotificada(String state, String idWorkflow) throws Throwable;
           /**
     * Agrega una regisro en el control de matriculas.
     *
     * @param idMatricula 
     * @param accion
     * @param rol
     * @param idWorkflow
     * @throws Throwable
     */
    void addCtrlMatricula(String idMatricula, String accion, String rol, String idWorkflow) throws Throwable;
    
    /**
     * Agrega un registro en el control de reasignación.
     * @param turno
     * @param usuarioOrigen
     * @param usuarioDestino
     * @throws Throwable 
     */
    void addCtrlReasignacion(Turno turno, String usuarioOrigen, String usuarioDestino) throws Throwable;

    /**
     * Registra un recurso de apelación o reposición o el tipo que corresponda
     *
     * @param recurso Debe tener seteado el TurnoHistoria, el TipoRecurso y el
     * textoRecurso
     * @return El identificador del objeto creado
     * @throws Throwable
     */
    RecursoPk addRecurso(Recurso recurso) throws Throwable;

    void updateRecurso(RecursoPk rid, String datoAmpliacion) throws Throwable;

    /**
     * Este servicio permite agregar una liquidación, cuyo valor ha sido
     * ingresado por el usuario a una solicitud.
     *
     * @param solicitud La <code>Solicitud</code> a la cual se va a agregar la
     * liquidación.
     * @param liquidacion La <code>Liquidacion</code> que va a ser agregada a la
     * solicitud.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    boolean addLiquidacionToSolicitud(Solicitud solicitud, Liquidacion liquidacion) throws Throwable;

    /**
     * Este servicio permite agregar una liquidación, cuyo valor ha sido
     * ingresado por el usuario a una solicitud de certificados.
     *
     * @param solicitud La <code>SolicitudCertificado</code> a la cual se va a
     * agregar la liquidación.
     * @param liquidacion La <code>LiquidacionTurnoRegistro</code> que va a ser
     * agregada a la solicitud.
     * @return <code>LiquidacionTurnoCertificado</code> creada para la
     * solicitud.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     */
    public LiquidacionTurnoCertificado addLiquidacionToSolicitudCertificado(SolicitudPk solicitudId, LiquidacionTurnoCertificado liquidacion) throws Throwable;

    /**
     * Obtiene la lista de turnos de fotocopias que se encuentren en la fase de
     * 'FOT_PAGO' durante más de n días (n es pasado por parámetros)
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @param dias Número de dias
     * @return
     * @throws Throwable
     */
    public List getTurnosFotocopiasConPagoVencido(Circulo circulo, double dias) throws Throwable;

    /**
     * Actualiza los documentos fotocopia asociados a la solicitud de fotocopia
     *
     * @param sol <code>SolicitudFotocopia</code> con identificador y sus
     * documentos fotocopia, cada uno con su nuevo tipo de fotocopia y su nuevo
     * número de hojas
     * @return La solicitud completa de fotocopia con sus documentos
     * actualizados
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     */
    public SolicitudFotocopia updateDocumentosFotocopia(SolicitudFotocopia sol) throws Throwable;
    
    
    /**
     * Elimina los actos por solicitud
     *
     * @param idSolicitud
     * @throws Throwable
     */
    public void deleteActosBySolicitud(String idSolicitud) throws Throwable;

    /**
     * Hace que la solicitud quede con UN SOLO folio, el envíado como parámetro.
     *
     * @param solicitud
     * @param folio
     * @throws Throwable
     */
    public void forceUnFolio(Solicitud solicitud, Folio folio)
            throws Throwable;

    /**
     * Obtiene el proceso asociado al id de la fase otorgado.
     *
     * @param idFase Id de la fase el cual se desea saber el proceso
     * @return proceso dado el id de la fase
     * @throws Throwable
     */
    public String getProcesoByIdFase(String idFase) throws Throwable;

    /**
     * Crea un <code>Turno </code> de Fotocopias y su respectiva instancia de
     * Workflow.
     *
     * @param solicitud La <code>SolicitudFotocopia</code> desde la cual se va a
     * generar el turno y su instancia de workflow.
     * @param usuarioSir <code>Usuario</code> que realiza el proceso.
     * @return El <code>Turno</code> que fue creado.
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    Turno crearTurnoFotocopias(SolicitudFotocopia solicitud, Usuario usuarioSir) throws Throwable;

    /**
     * Cambia el flag de aprobación de una solicitud de corrección o devolución
     *
     * @param solID Id de la solicitud
     * @param aprobada Nuevo estado del flag de aprobación
     * @return
     * @throws Throwable
     */
    public boolean setAprobacionSolicitud(SolicitudPk solID, boolean aprobada) throws Throwable;

    /**
     * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
     * asociado retorna null
     *
     * @param solID
     * @return
     * @throws Throwable
     */
    public Turno getTurnoBySolicitud(SolicitudPk solID) throws Throwable;

    /**
     * Obtiene el numero de recibo de un pago dado su identificador //Caso de
     * certificados asociados
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>String</code> con sus atributos
     * @throws <code>DAOException</code>
     */
    public String getNumReciboPagoByID(PagoPk pID) throws Throwable;

    /**
     * Crea un oficio, el oficio debe tener sus atributos básicos y una
     * asociación con un turno historia existente.
     *
     * @param oficio
     * @return
     * @throws Throwable
     */
    public OficioPk crearOficio(Oficio oficio) throws Throwable;

    /**
     * Elimina oficios, el oficio debe tener sus atributos básicos y una
     * asociación con un turno historia existente.
     *
     * @param oficios
     * @return
     * @throws Throwable
     */
    public void eliminarOficios(List oficios) throws Throwable;

    /**
     * Actualiza la firma del oficio con el ID especificado en el flag indicado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws Throwable
     */
    public boolean actualizarFirmaOficio(OficioPk oficioID, boolean firmado) throws Throwable;

    /**
     * Asocia un oficio respuesta a un recurso. Ambos deben existir en la base
     * de datos. El objeto queda en el atributo oficioRespuesta del recurso
     *
     * @param recursoID
     * @param oficioID
     * @return
     * @throws Throwable
     */
    public boolean setOficioRespuestaToRecurso(RecursoPk recursoID, OficioPk oficioID) throws Throwable;

    /**
     * Obtiene la lista de las estaciones a donde pasó un turno.
     *
     * @param fase Nombre de la fase a partir de donde se quiere saber.
     * @param idWF identificador del workflow del turno.
     * @return Lista de objetos de tipo String
     * @throws Throwable
     */
    public List getEstacionesActuales(String fase, String idWF) throws Throwable;

    /**
     * Obtiene el valor de una variable, dado su lookupType y su LookupCode
     *
     * @param tipo El LookupType de la variable.
     * @param codigo El LookupCode de la variable.
     * @return El valor de la variable.
     * @throws Throwable
     */
    public String getValor(String tipo, String codigo) throws Throwable;

    /**
     * Obtiene los oficios asociados al turno. Cada oficio tiene el turno
     * historia en el que fue creado
     *
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getOficiosTurno(TurnoPk oid) throws Throwable;

    /**
     * Actualiza el número del oficio con el ID especificado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws Throwable
     */
    public boolean actualizarNumeroOficio(OficioPk oficioID, String nuevoNumero) throws Throwable;

    /**
     * Agrega la fecha de firma al oficio con el ID especificado
     *
     * @param oficioID
     * @param fechaFirma
     * @return
     * @throws DAOException
     */
    public boolean agregarFechaFirmaOficio(OficioPk oficioID, Date fechaFirma) throws Throwable;

    /**
     * Devuelve el último usuario asignado a un turno en calificación que tenga
     * el folio con el ID folioID asociado
     *
     * @param folioID
     * @return
     * @throws Throwable
     */
    public Usuario getUsuarioConTurnoEnCalificacionConFolioAsociado(FolioPk folioID) throws Throwable;

    /**
     * Verifica si un turno es válido entre los registros de ejecución de SAS
     *
     * @param idWorkflow
     * @return
     * @throws Throwable
     */
    public boolean isValidTurnoSAS(String idWorkflow) throws Throwable;

    /**
     * Verifica si un turno es válido entre los registros de ejecución de SAS
     *
     * @param idWorkflow
     * @return
     * @throws Throwable
     */
    public String lastAdministradorTurnoSAS(String idWorkflow) throws Throwable;

    /**
     * Obtiene el listado de entidades públicas que intervienen como otorgantes,
     * en el proceso de reparto notarial
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial.
     * @throws Throwable
     */
    public List getEntidadesReparto() throws Throwable;

    /**
     * Obtiene el listado de entidades públicas que intervienen como otorgantes,
     * en el proceso de reparto con una naturaleza juridica determinada.
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial, que contienen la naturaleza jurídica
     * dada.
     * @throws Throwable
     */
    public List getEntidadesRepartoByNaturaleza(NaturalezaJuridicaEntidad naturalezaJuridicaReparto) throws Throwable;
  /**
     * Retorna uun objeto tipo Documento Pago
     * de atencion
     *
     * @param String codigo de documento pago
     * @return DocumentoPago 
     * @throws Throwable
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocumentoPago getDocumentobyIdDocPago(String idDocpago) throws Throwable;
    /**
     * Obtiene el listado de naturalezas jurídicas de las entidades públicas que
     * intervienen como otorgantes, en el proceso de reparto notarial
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial.
     * @throws Throwable
     */
    public List getNaturalezasJuridicasEntidades() throws Throwable;

    /**
     * Obtiene los permisos de cerrección confirurados en el sistema
     *
     * @return Lista con objetos de tipo PermisoCorreccion
     * @throws Throwable
     */
    public List getPermisosCorreccion() throws Throwable;

    /**
     * Setea el conjunto de permisos configurados de un turno
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws Throwable
     */
    public boolean asignarPermisosCorreccion(TurnoPk turnoID, List permisos) throws Throwable;

    /**
     * Refresca el subtipo de atención del turno dependiendo de las nuevas
     * características de éste
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws Throwable
     */
    public boolean refrescarSubtipoAtencionTurno(TurnoPk turnoID) throws Throwable;

    /**
     * Obtiene el listado de Minutas pendientes por repartir dentro de un
     * Círculo Registral.
     *
     * @param circuloRegistral el <code>Circulo</code> en el cual se van a
     * buscar las minutas pendientes de reparto.
     * @return Lista de minutas por repartir dentro del <code>Círculo</code>
     * recibido como parámetro.
     * @throws Throwable
     */
    public List getMinutasNoRepartidasByCirculoRegistral(Circulo circuloRegistral) throws Throwable;

    /**
     * Obtiene el listado de Minutas en las que aparece como otorgante una
     * persona natural.
     *
     * @param otorgante, nombre del otorgante que se quiere consultar dentro del
     * listado de minutas.
     * @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
     * @param circulo registral al cual pertenece el usuario en sesion
     * @return Lista de minutas en las que aparece el otorgante recibido como
     * parámetro.
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws Throwable
     */
    public List getMinutasByOtorganteNatural(String otorganteNatural, long estado, Circulo circuloRegistral) throws Throwable;

    /**
     * Obtiene el listado de Categorías a las que puede pertenecer una Notaría.
     *
     * @return Lista de Categorías a las que puede pertenecer una Notaría.
     * @see gov.sir.core.negocio.modelo.CategoriaNotaria
     * @throws Throwable
     */
    public List getCategoriasNotarias() throws Throwable;

    /**
     * Obtiene el listado de Minutas en las que aparece como otorgante una
     * entidad pública.
     *
     * @param otorgante, nombre del otorgante pública que se quiere consultar
     * dentro del listado de minutas.
     * @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
     * @param circulo registral al cual pertenece el usuario en sesion
     * @return Lista de minutas en las que aparece el otorgante recibido como
     * parámetro.
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws Throwable
     */
    public List getMinutasByOtorgantePublico(String otorganteNatural, long estado, Circulo circuloRegistral) throws Throwable;

    /**
     * Obtiene el listado de Minutas radicadas dentro de un rango de fechas
     * dado.
     *
     * @param fechaInicial fecha de inicio para el rango.
     * @param fechaFinal fecha de finalización para el rango.
     * @return Lista de minutas radicadas dentro del rango dado
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws Throwable
     */
    public List getMinutasRadicadasByRangoFecha(Date fechaInicial, Date fechaFinal) throws Throwable;

    /**
     * Obtiene el listado de Minutas repartidas dentro de un rango de fechas
     * dado.
     *
     * @param fechaInicial fecha de inicio para el rango.
     * @param fechaFinal fecha de finalización para el rango.
     * @param circulo registral del usuario
     * @return Lista de minutas repartidas dentro del rango dado
     * @throws Throwable
     */
    public List getMinutasRepartidasByRangoFecha(Date fechaInicial, Date fechaFinal, Circulo circuloRegistral) throws Throwable;

    /**
     * Obtiene el listado de Círculos Notariales, asociados con un Círculo
     * Registral.
     *
     * @param circulo <code>Circulo</code> del cual se van a obtener los
     * circulos notariales.
     * @return Lista de Círculos Notariales, asociados con un Círculo Registral.
     * @throws Throwable
     */
    public List getCirculosNotarialesByCirculoRegistral(Circulo circulo) throws Throwable;

    /**
     * Setea los datos de antiguo sistema a una solicitud de corrección. Si los
     * datos de antiguo sistema ya está seteados para la solicitud se reescriben
     *
     * @param sol Solicitud de corrección con el ID seteado
     * @param datosAntiguoSistema
     * @return
     * @throws Throwable
     */
    public boolean setDatosAntiguoSistemaToSolicitud(Solicitud sol, DatosAntiguoSistema datosAntiguoSistema) throws Throwable;

    /**
     * Obtiene una hashtable con el estado de la cola de las notarías por
     * categoría. La llave es la categoría y el valor es la lista de oficinas
     * origen.
     *
     * @return
     * @throws throws Throwable
     */
    public Hashtable getColasRepartoByCategoria() throws Throwable;

    /**
     * Permite agregar una Entidad Pública a la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser
     * adicionada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean agregarEntidadPublica(EntidadPublica entidadPublica) throws Throwable;

    /**
     * Permite actualizar una Entidad Pública a la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser
     * actualizado.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean editarEntidadPublica(EntidadPublica entidadPublica) throws Throwable;

    /**
     * Permite eliminar una Entidad Pública de la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser eliminada
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean eliminarEntidadPublica(EntidadPublica entidadPublica) throws Throwable;

    /**
     * Permite agregar una Naturaleza Jurídica de Entidad Pública a la
     * configuración del Sistema.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser adicionada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean agregarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws Throwable;

    /**
     * Permite editar una Naturaleza Jurídica de Entidad Pública para el reparto
     * notarial.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser editada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean editarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws Throwable;

    /**
     * Permite actualizar el estado de una Naturaleza Jurídica de Entidad
     * Pública para el reparto notarial.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser
     * actualizada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarEstadoNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws Throwable;

    /**
     * Permite eliminar una Naturaleza Jurídica de Entidad Pública de la
     * configuración del Sistema
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean eliminarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws Throwable;

    /**
     * Permite obtener el identificador de workflow de un turno asociado con una
     * minuta con id pasado como parámetro.
     *
     * @param idMinuta identificador de la minuta de la cual se desea obtener su
     * turno asociado.
     * @return identificador de workflow de un turno asociado con una minuta con
     * id pasado como parámetro.
     * @throws <code>Throwable </code>
     */
    public String getIdWorkflowByIdMinuta(String idMinuta) throws Throwable;

    /**
     * Obtiene la lista con los actos que tienen un plazo para ser registrados
     * existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>OPLookupCodes</code> con actos
     * que tienen un plazo de vencimiento para ser registrados
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    public List getActosQueVencen() throws Throwable;

    /**
     * Obtiene el plazo (dias) en el cual vencen los Actos para ser registrados
     *
     * @return String con la vigencua de los actos en dias.
     * @throws <code>Throwable</code>
     */
    public String getPlazoVencimientoRegistroActos() throws Throwable;

    /**
     * Asigna un estado a la solicitud folio del turno y folio determinado.
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws Throwable
     */
    public boolean updateEstadoSolicitudFolio(SolicitudFolio solFolio) throws Throwable;

    /**
     * Asigna una marca al folio dentro del turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws Throwable
     */
    public boolean marcarFolioInTurno(TurnoPk turnoID, FolioPk folioID) throws Throwable;

    /**
     * Desmarca todos los folios asociados a un turno
     *
     * @param turnoID
     * @return
     * @throws Throwable
     */
    public boolean desmarcarFoliosInTurno(TurnoPk turnoID) throws Throwable;

    /**
     * Obtiene la lista de alertas para la estación determinada. Si no se tiene
     * alertas se retorna una lista vacía. Si existen alertas se rebaja el
     * contador de alertas, si este llega a 0 se elimina la alerta.
     *
     * @param idEstacion
     * @return
     * @throws Throwable
     */
    public List getAlertas(String idEstacion) throws Throwable;

    /**
     * Permite modificar los datos de una liquidación de registro de documentos.
     *
     * @author dlopez
     * @param nuevaLiquidacion Nueva liquidación que va a ser asociada a la
     * solicitud.
     * <p>
     * La nueva liquidación tiene asociada su respectiva solicitud.
     */
    public Liquidacion modificarLiquidacionRegistro(Liquidacion nuevaLiquidacion) throws Throwable;

    /**
     * Retorna un documento de pago registrado en la base de datos. El documento
     * de pago debe ser un cheque o una consignación. Si no encuentra un
     * documento de pago correspondiente retorna null
     *
     * @param doc
     * @return
     * @throws Throwable
     */
    public DocumentoPago getDocumentosPagoExistente(DocumentoPago doc) throws Throwable;

    /**
     * Indica si el turno tiene por lo menos un acto del tipo indicado
     *
     * @param turnoID
     * @param tipoActoID
     * @return
     * @throws Throwable
     */
    public boolean hasActoTurnoRegistro(TurnoPk turnoID, TipoActoPk tipoActoID) throws Throwable;

    /**
     * Valida si la solicitud se puede asociar como solicitud vinculada
     *
     * @param solicitudID
     * @return
     * @throws Throwable
     */
    public boolean validarSolicitudVinculada(SolicitudPk solicitudID) throws Throwable;

    /**
     * Obtiene el listado de intereses jurídicos.
     *
     * @return Listado de intereses jurídicos.
     * @throws Throwable
     */
    public List getInteresesJuridicos() throws Throwable;

    /**
     * Obtiene la ruta física donde se encuentran las firmas de los
     * registradores.
     *
     * @return Ruta física donde se encuentra las firmas de los registradores.
     * @throws Throwable
     */
    public String getPathFirmasRegistradores() throws Throwable;

    /**
     * Obtiene la ruta física donde se encuentran las firmas de los
     * registradores a asociar.
     *
     * @return Ruta física donde se encuentra las firmas de los registradores.
     * @throws Throwable
     */
    public String getPathFirmasRegistradoresAAsociar() throws Throwable;

    /**
     * Elimina los modos de bloqueo y stack pos ingresados al avanzar push. De
     * acuerdo con el número de avances ingresado como parámetro.
     *
     * @return <code> true </code> o <code> false </code> dependiendo del
     * resultado de la operación.
     * @throws Throwable
     * @param turno El <code>Turno</code> sobre el cual se va a realizar la
     * actualización.
     * @param cantidad El número de operaciones avanzar push que debe
     * deshacerse.
     */
    public boolean deshacerAvancesPush(Turno turno, int cantidad) throws Throwable;

    /**
     * Obtiene el tipo de archivo que se usa para las impresiones
     *
     * @return Tipo de archivo para las impresiones
     * @throws Throwable
     */
    public String getFirmasContentType() throws Throwable;

    /**
     * Obtiene la url del servlet de reportes
     *
     * @return Url del servlet de reportes
     * @throws Throwable
     */
    public String getUrlServletReportes() throws Throwable;

    /**
     * Obtiene el número máximo de impresiones de certificados que se pueden
     * realizar
     *
     * @return Número máximo de impresiones de certificados
     * @throws Throwable
     */
    public String getNumeroMaximoImpresionesCertificados() throws Throwable;

    /**
     * Obtiene texto para un certificado exento
     *
     * @return texto para un certificado exento
     * @throws Throwable
     */
    public String getTextoExento() throws Throwable;

    /**
     * Asigna una marca al folio recién creado en antiguo sistema dentro del
     * turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws Throwable
     */
    public boolean marcarFolioRecienCreadoASInTurno(TurnoPk turnoID, FolioPk folioID) throws Throwable;

    /**
     * Desmarca todos los folios recién creados en antiguo sistema asociados a
     * un turno
     *
     * @param turnoID
     * @return
     * @throws Throwable
     */
    public boolean desmarcarFoliosRecienCreadoASInTurno(TurnoPk turnoID) throws Throwable;

    /**
     * Indica si la impresión de recibos asociados (Certificados) se imprimen en
     * la impresora local (Cajero) o se imprime en la impresora configurada para
     * los certificados por circulo
     *
     * @throws <code>Throwable</code>
     */
    public boolean isImpresionRecibosCertificadosImpresoraCajero(String nombreCirculo) throws Throwable;

    /**
     * Valida previamente de avanzar el turno, si el turno tiene notas
     * informativas en la fase respectiva si está configurada la validación para
     * dicha fase
     *
     * @param fase
     * @param parametros
     * @param turno
     * @throws HermodException
     */
    public void validarNotaInformativaAvanceTurno(Fase fase, Hashtable parametros, Turno turno) throws Throwable;

    /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerAdministrativasPorRol(List roles) throws Throwable;

    /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol y una página de despligue de la pantalla administrativa.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @param pagina pagina en la que se muestra la pantalla administrativa.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerRolPantallasAdministrativasPorRolPagina(List roles, String pagina) throws Throwable;

    /**
     * Obtiene el listado de reportes visibles para un respectivo rol.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerPantallasPaginaReportesPorRol(List roles) throws Throwable;

    /**
     * Obtiene el listado de tipos de pantallas administrativas existentes en la
     * aplicación.
     *
     * @return Listado de tipos de pantallas administrativas.
     * @throws HermodException
     */
    public List obtenerTiposPantallasAdministrativas() throws Throwable;

    /**
     * @param proceso
     * @param fase
     * @param fecha
     * @param circulo
     * @return
     */
    public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws Throwable;

    /**
     * @param proceso
     * @param fase
     * @param fecha
     * @param circulo
     * @return
     */
    public List getTurnosByFechaAndCirculoMinusMasivos(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws Throwable;

    /**
     * @param proceso
     * @param fase
     * @param fecha
     * @param circulo
     * @return
     */
    public List getTurnosNuevoByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws Throwable;

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR' durante más de 2 meses
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorVencido(Circulo circulo) throws Throwable;

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR', es decir, que están pendientes de pago
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorPendiente(Circulo circulo) throws Throwable;

    /**
     *
     * @param pagoID
     * @param numRecibo
     * @throws HermodException
     */
    public void setNumeroReciboPago(PagoPk pagoID, String numRecibo) throws Throwable;
    

    /**
     *
     * @param numRecibo
     * @return
     * @throws HermodException
     */
    public Pago getPagoByNumeroRecibo(String numRecibo) throws Throwable;

    /**
     * Obtiene un objeto <code>Pago</code> dado su identificador.
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>Pago</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago getPagoByID(PagoPk pID) throws Throwable;

    public void addTurnoHistoriaToTurno(Turno turno, TurnoHistoria turnoHistoria) throws Throwable;

    /**
     * Actualiza en el modelo operativo la información del turno, el cuál debe
     * quedar en la fase de entrega.
     *
     * @param t
     * @param solicitud
     * @param user
     * @throws Throwable
     */
    public void actualizarTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws Throwable;

    /**
     * Actualiza el modo de bloqueo del turno en el esquema de persistencia
     *
     * @param t
     */
    public void actualizarTurnoModoBloqueo(Turno turno) throws Throwable;

    public Proceso getProcesoById(ProcesoPk oid) throws Throwable;

    /**
     * Obtiene el número de modificaciones permitidas en la edición de minutas
     * de reparto notarial
     *
     * @return el número de modificaciones permitidas en la edición de minutas
     * de reparto notarial
     * @throws Throwable
     */
    public int getNumModificacionesMinutas() throws Throwable;

    /**
     * Modifica el atributo número de ediciones realizadas a una minuta
     *
     * @param minuta La minuta en la que se va a modificar el atributo.
     * @throws Throwable
     */
    public void updateNumModificacionesMinuta(Minuta minuta) throws Throwable;

    /**
     * @param idMatricula
     * @return
     */
    List getTurnos(String idMatricula) throws Throwable;

    /**
     * @param proceso
     * @param circulo
     * @return
     */
    List getTurnosCirculo(Proceso proceso, Circulo circulo, String idMatricula) throws Throwable;

    /**
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    List getTurnosCirculo(Circulo circulo, String idMatricula) throws Throwable;

    /**
     * @param turno identificador del turno al cual se le van a eliminar las
     * notas devolutivas.
     * @throws Throwable
     */
    public void removeDevolutivasFromTurno(TurnoPk turnoID) throws Throwable;

    /**
     * @param turno identificador del turno al cual se le van a encontrar el
     * valor pagado por otro impuesto en las liquidaciones del turno de registro
     * incluyendo los turnos anteriores.
     * @throws Throwable
     */
    public double getValorOtroImpuestoTurnosAnteriores(TurnoPk turnoID) throws Throwable;

    /**
     * Agrega un nuevo certificado asociado a un turno de registro existente
     *
     * @param turnoRegistro
     * @param turnoCertificado
     * @throws Throwable
     */
    public void addCertificadoAsociadoToTurno(Turno turnoRegistro, Turno turnoCertificado) throws Throwable;

    /**
     * Elimina una liquidación sin pago de un turno dado
     *
     * @param tid El identificador del turno para el cual se quieren eliminar
     * las liquidaciones sin pago
     * @throws Throwable
     */
    public void removeLiquidacionesSinPagoFromTurno(TurnoPk tid) throws Throwable;

    /**
     * Elimina una relación de un turno
     *
     * @param tid
     * @throws Throwable
     */
    public void eliminarRelacionTurno(TurnoPk tid) throws Throwable;

    /**
     * Obtiene un tipo de nota a partir de su identificador
     *
     * @param tid El identificador del tipo de nota
     * @return El tipo de nota
     * @throws Throwable
     */
    public TipoNota getTipoNota(gov.sir.core.negocio.modelo.TipoNotaPk tid) throws Throwable;

    /**
     * Obtiene los tipos de relaciones existentes para una fase dada de un
     * proceso
     *
     * @param idFase El identificador de la fase para la cual se quieren obtener
     * los tipos de relaciones
     * @return Todos los tipos de relaciones correspondientes a la fase indicada
     * @throws Throwable
     */
    public List getTiposRelacionesFase(String idFase) throws Throwable;

    /**
     * Obtiene el tipo de relación a partir de su identificador
     *
     * @param idTipoRelacion El identificador del tipo de relación
     * @return El tipo de relación con el identificador suministrado
     */
    public TipoRelacion getTipoRelacion(TipoRelacionPk idTipoRelacion) throws Throwable;

    /**
     * Obtiene todos los turnos disponibles para relacionar para un proceso, una
     * fase, un círculo y un tipo de relación dados.
     *
     * @param proceso El proceso al cual deben pertenecer los turnos
     * @param fase La fase actual de los turnos
     * @param circulo El círculo al que corresponden los turnos
     * @param tipoRelacion El tipo de relación para el que se quieren agregar
     * los turnos
     * @return Listado de todos los turnos que cumplen con las restricciones
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion) throws Throwable;

    /**
     * Obtiene todos los turnos que están en una fase determinada de un proceso
     * y con un número de relación determinado.
     *
     * @param proceso El proceso al cual deben pertenecer los turnos
     * @param fase La fase actual de los turnos
     * @param circulo El círculo al que corresponden los turnos
     * @param idRelacion El número de la relación.
     * @return Listado de todos los turnos que cumplen con las condiciones
     * dadas.
     */
    public List getTurnosByRelacion(Proceso proceso, Fase fase, Circulo circulo, String idRelacion) throws Throwable;

    /**
     *
     * @param tipoRelacion
     * @param usuario
     * @return
     * @throws Throwable
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo) throws Throwable;

    /**
     *
     * @param tipoRelacion
     * @param usuario
     * @param turnos
     * @return
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos) throws Throwable;

    /**
     *
     * @param tipoRelacion
     * @param usuario
     * @param turnos
     * @return
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String respuesta) throws Throwable;

    /**
     *
     * @param relacion
     * @param turno
     * @return
     * @throws Throwable
     */
    public Relacion addTurnoToRelacion(Relacion relacion, Turno turno) throws Throwable;

    /**
     *
     * @param idRelacion
     * @param nota
     * @throws Throwable
     */
    public void setNotaToRelacion(RelacionPk idRelacion, String nota) throws Throwable;

    /**
     *
     * @param idRelacion
     * @return
     * @throws Throwable
     */
    public Relacion getRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion) throws Throwable;

    /**
     *
     * @param tipoRelacion
     * @param usuario
     * @param circulo
     * @param turnos
     * @param idRelacion
     * @return
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion) throws Throwable;

    /**
     *
     * @param tipoRelacion
     * @param usuario
     * @param circulo
     * @param turnos
     * @param idRelacion
     * @return
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion, String respuesta) throws Throwable;

    /**
     *
     * @return @throws Throwable
     */
    public boolean isValidarSesionImpresion() throws Throwable;

    /**
     * @param proceso
     * @param fase
     * @param circulo
     * @param tipoRelacion
     * @param idRelacion
     * @return
     * @throws Throwable
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion, String idRelacion) throws Throwable;

    /**
     * Obtiene el turno derivado del turno con el identificador dado.
     *
     * @param id
     * @return
     * @throws Throwable
     */
    public Turno getTurnoDependiente(TurnoPk id) throws Throwable;

    /**
     * Crea un nuevo turno derivado del un turno padre dado.
     *
     * @param turno
     * @param usuarioNeg
     * @param idProceso
     * @return
     * @throws Throwable
     */
    public Turno crearTurnoDependiente(Turno turno, Usuario usuarioNeg, long idProceso) throws Throwable;

    /**
     * Obtiene el turno del que un turno fue derivado
     *
     * @param turnoId Identificador del turno para el que se quiere determinar
     * su padre
     * @return El turno del que el turno fue derivado
     * @throws DAOException
     */
    public Turno getTurnoPadre(TurnoPk turnoID) throws Throwable;

    /**
     * Obtiene y avanza la secuencia de las constancias de devoluciones, de
     * acuerdo a los parametros recibidos.
     *
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir la constancia de devolución.
     * @param year El año en el que se va a expedir la constancia de devolución.
     * @param pm Persistence Manager de la transacción.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialDevolucion(String circuloId, String year)
            throws Throwable;

    /**
     * Agrega una Nota de actuaciones al turno dado.
     *
     * @param Identificador del turno <code>Turno.ID</code> a la que se va a
     * asociar la <code>NotaActuacion</code>
     * @param nota actuación la <code>NotaActuacion</code> que va a ser asociada
     * al turno.
     * @return El resultado de la adición de la nota de actuaciones
     * administrativas.
     * @see gov.sir.core.negocio.modelo.TurnoPk
     * @see gov.sir.core.negocio.modelo.NotaActuacion
     * @throws <code>Throwable</code>
     */
    public boolean addNotaActuacionToTurno(TurnoPk turnoID, NotaActuacion notaActuacion) throws Throwable;

    /**
     * Actualiza una Nota de actuaciones al turno dado.
     *
     * @param Identificador del turno <code>Turno.ID</code> al que se va a
     * actualizar la <code>NotaActuacion</code>
     * @param nota actuación la <code>NotaActuacion</code> que va a ser
     * actualizada al turno.
     * @return El resultado de la actualización de la nota de actuaciones
     * administrativas.
     * @see gov.sir.core.negocio.modelo.TurnoPk
     * @see gov.sir.core.negocio.modelo.NotaActuacion
     * @throws <code>Throwable</code>
     */
    public boolean updateNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws Throwable;

    /**
     * Obtiene y avanza la secuencia de los recibos de certificados masivos, de
     * acuerdo a los parametros recibidos.
     *
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir el recibo de certificados masivos.
     * @param year El año en el que se va a expedir el recibo de certificados
     * masivos.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialMasivos(String circuloId, String year)
            throws Throwable;

    /**
     * @author : Julio Alcázar Rivas
     * @change : Obtiene y avanza la secuencia de los recibos de certificados
     * masivos, de acuerdo a los parametros recibidos.
     * @Caso Mantis : 000941
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir el recibo de certificados masivos.
     * @param year El año en el que se va a expedir el recibo de certificados
     * masivos.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialMasivosExento(String circuloId, String year)
            throws Throwable;

    /**
     * Obtiene el listado de items de chequeo definidos en el sistema para las
     * solicitudes de devoluciones.
     *
     * @return Listado de items de chequeo definidos en el sistema para las
     * solicitudes de devoluciones.
     * @throws HermodException
     */
    public List getItemsChequeoDevoluciones()
            throws Throwable;

    /**
     * Obtiene el listado de turnos de restitución asociados con una minuta.
     *
     * @return listado de turnos de restitución asociados con una minuta.
     * @param idCir Círculo Registral asociado con la minuta
     * @param idMin Identificador de la minuta
     * @throws <code>Throwable</code>
     */
    public List getListadoTurnosRestitucionMinutas(String idCir, String idMin) throws Throwable;

    /**
     * Obtiene el último turno generado por un usuario en un proceso especifico
     *
     * @param idUsuario
     * @param idProceso
     * @return
     * @throws Throwable
     */
    public Turno getUltimoTurnoProcesoUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws Throwable;

    /**
     * Obtiene el último turno generado por un usuario en un proceso especifico
     *
     * @param idUsuario
     * @return
     * @throws Throwable
     */
    public String getUltimaSolicitudLiquidacion(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, CirculoPk idCirculo) throws Throwable;

       
    /**
     * Obtiene el ultimo turno por usuario que haya registrado el pago de mayor
     * valor
     *
     * @param idUsuario
     * @param idProceso
     * @param idCirculo
     * @return
     * @throws Throwable
     */
    public Turno getUltimoTurnoMayorValorUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws Throwable;
    
       /**
     * Retorna un objeto de tipo OPLookupCodes
     * @param tipo
     * @return
     * @throws Throwable 
     */
    List getValorLookupCodesByTipo(String tipo) throws Throwable;

    /**
     * Se incrementan los intentos de reimpresión de una solicitud específica
     *
     * @param idSolicitud
     * @throws Throwable
     */
    public void incrementarIntentoReimpresionRecibo(SolicitudPk idSolicitud) throws Throwable;

    /**
     * Agrega un circulo notarial
     *
     * @param circuloNotarial circulo notarial a agregar
     * @throws Throwable
     */
    public void agregarCirculoNotarial(CirculoNotarial circuloNotarial) throws Throwable;

    /**
     * Elimina un circulo notarial
     *
     * @param circuloNotarial circulo notarial a eliminar
     * @throws Throwable
     */
    public void eliminarCirculoNotarial(CirculoNotarial circuloNotarial) throws Throwable;

    /**
     * Edita un circulo notarial
     *
     * @param circuloNotarial circulo notarial a editar
     * @throws Throwable
     */
    public void editarCirculoNotarial(CirculoNotarial circuloNotarial) throws Throwable;

    /**
     * Consulta un circulo notarial
     *
     * @param circuloNotarial circulo notarial a consultar
     * @throws Throwable
     */
    public CirculoNotarial consultarCirculoNotarial(CirculoNotarialPk idCirculo) throws Throwable;

    /**
     * Agrega una zona notarial
     *
     * @param zonaNotarial zona notarial a agregar
     * @throws Throwable
     */
    public void agregarZonaNotarial(ZonaNotarial zonaNotarial) throws Throwable;

    /**
     * Elimina una zona notarial
     *
     * @param zonaNotarial zona notarial a eliminar
     * @throws Throwable
     */
    public void eliminarZonaNotarial(ZonaNotarial zonaNotarial) throws Throwable;

    /**
     * Crea una operación de englobe o segregación en la solicitud indicada
     *
     * @param operacion
     * @param solID
     * @return
     * @throws DAOException
     */
    public WebSegEngPk crearWebSegEng(WebSegEng operacion, SolicitudPk solID) throws Throwable;

    /**
     * Elimina una operación de englobe o segregación dado su identificador
     *
     * @param operacionID
     * @return
     * @throws DAOException
     */
    public boolean eliminarWebSegEng(WebSegEngPk operacionID) throws Throwable;

    /**
     * Actualiza una operación de englobe o segregación dado su identificador y
     * nuevos datos
     *
     * @param operacionID
     * @param operacion
     * @return
     * @throws DAOException
     */
    public boolean actualizarWebSegEng(WebSegEngPk operacionID, WebSegEng operacion) throws Throwable;

    /**
     * Consulta una operación de englobe o segregación dado su identificador
     *
     * @param operacionID
     * @return
     * @throws DAOException
     */
    public WebSegEng consultarWebSegEng(WebSegEngPk operacionID) throws Throwable;

    /**
     * Consulta una operación de englobe o segregación dada su solicitud
     *
     * @param solicitudID
     * @return
     * @throws DAOException
     */
    public List getWebSegEngBySolicitud(SolicitudPk solicitudID) throws Throwable;

    /**
     * Agrega una notaria a la cola de reparto notarial
     *
     * @param notaria
     * @return
     * @throws Throwable
     */
    public void agregarNotariaReparto(OficinaOrigen notaria) throws Throwable;

    /**
     * Obiene el texto imprimible
     *
     * @param idTexto identificador del texto
     * @return
     * @throws DAOException
     */
    public TextoImprimible getTextoImprimible(TextoImprimiblePk idTexto) throws Throwable;

    /**
     * Regresa una lista de imprimibles (List< Imprimible >)
     *
     * @param oid el identificador de workflow para el turno
     */
    public List getImprimiblesPendientesByWfId(String turno_WfId, String tipoImprimibleId) throws Throwable;

    /**
     * Devuelve una lista con los procesos para los que se pueden generar
     * relaciones
     *
     * @return
     * @throws Throwable
     */
    public List getListaProcesosRelacion() throws Throwable;

    /**
     * Permite marcar o desmarcar el turno para indicar que al turno se le
     * interpuso un recurso o revocatoria directa.
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean actualizarMarcaRevocatoriaTurno(Turno turno) throws Throwable;

    /**
     * Realiza la consulta de los turnos que fueron bloqueados y que están para
     * ser reanotados.
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    public List consultarTurnosAReanotar(Circulo circulo) throws Throwable;

    /**
     * Permite colocar en la fase calificación, un turno que ya se encuentra
     * finalizado.
     *
     * @param turno
     * @param parametros
     * @return
     * @throws Throwable
     */
    public boolean reanotarTurno(Turno turno, Hashtable parametros) throws Throwable;

    /**
     * Método que permite actualizar el último turno historia de un turno para
     * su estado no sea activo..
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public void updateLastTurnoHistoria(Turno turno) throws Throwable;

    /**
     * Actualizar la informacion de un turno Historia Informacion Reimpresion
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public void updateTurnoReimpresionCertificado(Turno turno, Usuario usuario, Folio folio) throws Throwable;

    /**
     * Obtiene la lista de turnos a partir de una estación y una fase. Este
     * método devuelve el turno con un turno historia en dónde se tiene la
     * información de la fase y la estación asignada.
     *
     * @param estacion <code>Estacion</code> sobre la cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Circulo
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public List getTurnosAReasignar(Estacion estacion, Fase fase)
            throws Throwable;

    /**
     * Actualiza la estación que tiene un turno por otra nueva estación.
     *
     * @param turno <code>Turno</code> sobre el cual se va a cambiar de estación
     * que lo tiene.
     * @param estacionDestino <code>Estacion</code> a la que va a quedar
     * asignado un turno.
     * @return una lista de objetos <code>Turno</code>
     * @throws <code>Throwable</code>
     */
    public boolean reasignarTurno(Turno turno, Estacion estacionDestino)
            throws Throwable;

    /**
     * Actualiza la informacion asociada a un turno en la tabla
     * sir_op_turno_ejecucion.
     *
     * @param turno <code>TurnoEjecucion</code> sobre el cual se va a actualizar
     * la informacion.
     * @return Informacion de si se pudo o no actualizar la informacion.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarTurnoEjecucion(TurnoEjecucion turnoEjecucion)
            throws Throwable;

    public boolean getTurnoEjecucionTurnoIndividual(Estacion estacion, Fase fase, Circulo circulo, String idworkflow)
            throws Throwable;

    /**
     * Método que permite actualizar la información en las tablas del modelo
     * operativo cuando se requiere ejecutar la reanotación de un turno de
     * registro de documentos.
     *
     * @param idTurno
     * @param notificationId
     * @param fase
     * @param resultado
     * @param estacionAsignada
     * @param usuarioSir
     * @return
     * @throws Throwable
     */
    public boolean reanotarTurnoModeloOperativo(String idTurno, String notificationId, String fase, String resultado, String estacionAsignada, Usuario usuarioSir)
            throws Throwable;

    /**
     * Actualiza el usuario que atendió el último turno historia de un proceso
     * dado.
     *
     * @param turno <code>Turno</code> sobre el cuál se quiere actualizar el
     * turno historia.
     * @param nombreFase <code>String</code> sobre la cuál se quiere actualizar
     * el turno historia.
     * @param usuarioAtiende <code>Usuario</code> que atendió la fase dada.
     * @return <code>boolean</code> con la respuesta se se actualizó o nó el
     * turno.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarUsuarioAtiendeTurnoHistoria(Turno turno, String nombreFase, Usuario usuarioAtiende)
            throws Throwable;

    /**
     * Retorna un turno luego de haberlo creado en el modelo operativo.
     *
     * @param solicitud solicitud asociada al turno.
     * @param usuarioSir <code>Usuario</code> que está creando el turno.
     * @param idEstacion Identificador de la estación desde la cual se está
     * creando el turno.
     * @return El <code>Turno</code> creado.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    Turno crearTurnoRepartoNotarial(SolicitudRepartoNotarial solicitud, String idEstacion, Usuario usuarioSir)
            throws Throwable;

    /**
     * Retorna una instancia de wf de un turno de reparto notarial luego de
     * haberlo creado en el modelo operativo.
     *
     * @param turno Turno asociado al turno.
     * @return El mensaje enviado por el WF luego de realizar la creación del
     * turno.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    Message crearWFTurno(Turno turno)
            throws Throwable;

    /**
     * Obtiene la estación a la cual va a ser asignado un turno.
     *
     * @param m Message que contiene información del turno al cual se va a
     * asociar la estación.
     * @param idCirculo identificador del turno al cual se va a asociar la
     * estación.
     * @return El identificador de la estación a la cual debe ser asociado el
     * turno.
     */
    String obtenerEstacionTurno(Hashtable m, String idCirculo) throws Throwable;

    /**
     * Guarda la información de un turno en la tabla de Turno Ejecución.
     *
     * @param m Message que contiene información del turno del cual se va a
     * guardar la información.
     * @param idTurno identificador del turno del cual se va a guardar la
     * información.
     * @param usuarioSir el usuario responsable del avance o la creación del
     * turno.
     * @return El identificador de la estación a la cual debe ser asociado el
     * turno.
     */
    void guardarInfoTurnoEjecucion(Hashtable m, String estacionAsignada, Turno turno, Usuario UsuarioSir) throws Throwable;

    /**
     * Avanza un turno a la siguiente fase de acuerdo con los parámetros
     * recibidos.
     *
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el
     * <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar
     * el avance.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     * @see gov.sir.core.negocio.modelo.constantes.CAvanzarTurno
     */
    public boolean avanzarTurnoNuevoNormal(Turno turno, Fase fase, Hashtable parametros, Usuario usuario)
            throws Throwable;

    /**
     * Avanza un turno a la siguiente fase de acuerdo con los parámetros
     * recibidos.
     *
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el
     * <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar
     * el avance.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     * @see gov.sir.core.negocio.modelo.constantes.CAvanzarTurno
     */
    public boolean avanzarTurnoNuevoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario)
            throws Throwable;

    /**
     * Avanza un turno a la siguiente fase de acuerdo con los parámetros
     * recibidos.
     *
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el
     * <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar
     * el avance.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     * @see gov.sir.core.negocio.modelo.constantes.CAvanzarTurno
     */
    public boolean avanzarTurnoNuevoPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario)
            throws Throwable;

    public boolean avanzarTurnoNuevoEliminandoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario)
            throws Throwable;

    public boolean avanzarTurnoNuevoCualquiera(Turno turno, Fase fase, Hashtable parametros, Usuario usuario)
            throws Throwable;

    public Hashtable realizarRepartoExtraordinarioCirculo(Circulo circ, Usuario usuario, boolean tipo, String[] idsExtraordinarios)
            throws Throwable;

    public RepartoNotarial getRepartoNotarialById(RepartoNotarialPk repartoNotarial) throws Throwable;

    /**
     * Retorna una lista de objetos<code>TurnoFolioMig</code> a partir de una
     * turno.
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioByTurno(Turno turno) throws Throwable;

    /**
     * Retorna una lista de objetos<code>SolicitudFolioMig</code> a partir de
     * una solicitud.
     *
     * @param solicitud Solicitud a partir del cuál se quiere consultar.
     * @return List de objetos<code>SolicitudFolioMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public List getSolicitudFolioMigBySolicitud(Solicitud solicitud) throws Throwable;

    /**
     * Retorna una lista de objetos<code>TurnoFolioMig</code> a partir de un
     * turno, sólo se retornan si no existen registros en SIR_OP_SOLICITUD_FOLIO
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioNoMigrados(Turno turno) throws Throwable;

    /**
     * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de
     * un turno, Estos registros representan los folios que estan en tramite en
     * un turno determinado en el sistema folio.
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioTramiteMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioTramite(Turno turno) throws Throwable;

    /**
     * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de
     * un folio, Estos registros representan los folios que estan en tramite en
     * un turno determinado en el sistema folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioTramiteMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public List getTurnosFolioTramite(Folio folio) throws Throwable;

    /**
     * Retorna un boolean con la información de si la creación de TurnosFolioMig
     * fue creada satisfactoriamente.
     *
     * @param turno Turno a partir del cuál se quiere crear registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO.
     * @param turnosFolioMig registros que se quieren insertar en la tabla
     * SIR_MIG_REL_TURNO_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public boolean addTurnosFolioMigToTurno(Turno turno, List turnosFolioMig) throws Throwable;

    /**
     * Retorna un boolean con la información de si la creación de
     * TramiteTurnosFolioMig fue creada satisfactoriamente.
     *
     * @param turno Turno a partir del cuál se quiere crear registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
     * @param tramiteTurnosFolioMig registros que se quieren insertar en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     */
    public boolean addTramiteTurnosFolioMigToTurno(List tramiteTurnosFolioMig) throws Throwable;

    /**
     * Retorna un boolean con la información de si la creación de
     * SolicitudFolioMig fue creada satisfactoriamente.
     *
     * @param solicitud Solicitud a partir del cuál se quiere crear registros en
     * la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
     * @param solicitudFolioMig registros que se quieren insertar en la tabla
     * SIR_MIG_REL_SOLICITUD_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public boolean addSolicitudFolioMigToTurno(Solicitud solicitud, List solicitudFolioMig) throws Throwable;

    /**
     * Retorna un boolean con la información de si la eliminación de
     * SolicitudFolioMig fue exitosa.
     *
     * @param solicitud Solicitud a partir del cuál se quiere borrar registros
     * en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public boolean removeSolicitudFolioMig(Solicitud solicitud) throws Throwable;

    /**
     * Obtiene la lista de turnos asociados a una estacion, un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code> si
     * estos tienen datos sin migrar como por ejemplo algún folio.
     *
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>Throwable</code>
     */
    List getTurnosSirMig(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws Throwable;

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO.
     * @return boolean con información de si existe o no registros
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioSirMigTurnoFolio(Folio folio) throws Throwable;

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_FOLIO_VALIDO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_FOLIO_VALIDO.
     * @return boolean con información de si existe o no registros
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioValidoSirMig(Folio folio) throws Throwable;

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
     * @return boolean con información de si existe o no registros
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioSirMigTurnoFolioTramite(Folio folio) throws Throwable;

    /**
     * Consulta las solicitudes que tengan el <code>Turno</code> como turno
     * anterior
     *
     * @param turnoID
     * @return
     * @throws HermodException
     */
    public List getSolicitudesByTurnoAnterior(Turno turno) throws Throwable;

    /**
     * Obtiene el listado de <code>PantallaAdministrativa</code> cuyo atributo
     * pagina sea igual al parámetro pagina
     *
     * @param pagina
     * @return
     * @throws DAOException
     */
    public List getPantallasAdministrativasByPagina(String pagina)
            throws Throwable;

    /**
     * inserta <code>RolPantalla</code> en la base de datos
     *
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public RolPantalla addRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws Throwable;

    /**
     * elimina <code>RolPantalla</code> de la base de datos
     *
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public boolean deleteRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws Throwable;

    /**
     * inserta cada elemento <code>RolPantalla</code> de la lista rolesPantallas
     * en la base de datos
     *
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public void addRolesPantallasAdministrativas(List rolesPantallas)
            throws Throwable;

    /**
     * elimina cada elemento <code>RolPantalla</code> de la lista rolesPantalas
     * de la base de datos
     *
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public boolean deleteRolPantallasAdministrativa(List rolesPantallas)
            throws Throwable;

    /**
     * Permite cambiar la respuesta en el historial de turnos del último turno
     * que tenga la fase especificada.
     *
     * @param turno
     * @fase fase del turno a cambiar
     * @respueta nuevo valor de la respuesta del turno
     * @return
     * @throws Throwable
     */
    public boolean modificarRespuestaUltimaFase(Turno turno, Fase fase, String respuesta)
            throws Throwable;

    public int getTamanioLista(String idListado, Object[] parametros)
            throws Throwable;

    public List getListaPorRangos(String idListado, Object[] parametros, int rangoInf, int rangoSup)
            throws Throwable;

    /**
     * Actualiza el valor de la liquidacion del turno de devoluciones
     *
     * @param liquidacion
     * @return
     * @throws DAOException
     */
    public boolean updateValorLiquidacionDevolucion(Liquidacion liquidacion) throws Throwable;
    
    
    
    
    public void deleteLiquidacionTurnoRegistro(String idLiquidacion, String idSolicitud) throws Throwable;

    /**
     * Elimina las notas informativas asociadas al turno, solo elimina la última
     * nota de la fase en donde se encuentra el turno en estos momentos
     *
     * @param turnoID
     * @throws Throwable
     */
    public void eliminarNotasInformativasUltimaFaseTurno(TurnoPk turnoID) throws Throwable;
        
    /**
     * Permite marcar o desmarcar el turno como turno de certificados nacional
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    boolean actualizarMarcaTurnoNacional(Turno turno) throws Throwable;

    /**
     * Obtiene el circulo configurado como circulo WEB
     *
     * @return Intacia de tipo gov.sir.core.negocio.modelo.Circulo.
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public Circulo getCirculoPortal() throws Throwable;

    /**
     * Obiene el iniciador de un proceso
     *
     * @param idProceso El id del proceso
     * @return
     */
    public InicioProcesos obtenerFaseInicial(String idProceso) throws Throwable;

    public byte[] generarReporteJasper(String nombreReporte, String rutaReportes,
            HashMap parametrosJasper) throws Throwable;

    /**
     * Actualiza el Historial del Turno sin modificar el contenido anterior
     *
     * @param turno
     * @param turnoHistoria
     */
    public void addTurnoHistoriaToTurnoReasignacion(Turno turno, TurnoHistoria turnoHistoria) throws Throwable;

    /**
     * Pablo Quintana Junio 19 2008 Retorna un Testamento asociado a un turno
     * según el IdSolicitud
     */
    public Testamento getTestamentoByID(String idSolicitud) throws Throwable;

    public void removeTestadorFromTestamento(TestamentoCiudadanoPk testamentoCiudadanoID) throws Throwable;

    /**
     * Retorna true si un pago se una consignacion pertenece a devoluciones
     *
     * @param idDocumentoPago
     * @return
     * @throws Throwable
     */
    public boolean existeConsignacionDevolucion(String idDocumentoPago) throws Throwable;

    /**
     * Actualiza una solicitud
     */
    public void actualizaSolicitud(Solicitud solicitud, List consignaciones) throws Throwable;

    /**
     * Valida si un documento pago (consignacion o cheque) es valido en
     * devolución.
     *
     * @param doc
     * @return
     * @throws Throwable
     */
    public boolean getDocsPagosDevolucion(DocumentoPago doc) throws Throwable;

    /**
     * Adiciona relacion banco círculo
     *
     * @param bancoXCirculo
     * @return
     * @throws Throwable
     */
    public boolean addBancoCirculo(BancosXCirculo bancoXCirculo) throws Throwable;

    /**
     * Consulta los bancos configurados para un determinado círculo
     *
     * @return
     * @throws Throwable
     */
    List getBancosXCirculo(String idCirculo) throws Throwable;

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param bancoXCirculo
     * @return
     * @throws Throwable
     */
    public boolean eliminaBancoCirculo(BancosXCirculo bancoXCirculo) throws Throwable;

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     *
     * @param bancoXCirculo
     * @return
     * @throws Throwable
     */
    public boolean activarBancoPrincipal(BancosXCirculo bancoXCirculo) throws Throwable;

    /**
     * Consulta relación bancos círculo (SIR_OP_BANCOS_X_CIRCULO) para ser
     * cargada en contexto. Con ésta lista se cargan los bancos en las
     * diferentes modalidades de pago
     *
     * @return
     * @throws Throwable
     */
    List getRelacionBancosCirculo() throws Throwable;

    /**
     *
     * @param idMatricula
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean validarFolioTurnoReanotacion(String idMatricula, Turno turno) throws Throwable;

    /**
     *
     * @param turno
     * @param nota
     * @param calificador
     * @param usuario
     * @param estacion
     * @throws Throwable
     */
    public void reanotarTurno(Turno turno, Nota nota, Usuario calificador, Usuario usuario, Estacion estacion) throws Throwable;

    /**
     * Elimina una nota devolutiva de un turno
     *
     * @param turnoID
     * @param nota
     * @throws Throwable
     */
    public void removeNotaDevolutivaFromTurno(TurnoPk turnoID, Nota nota) throws Throwable;

    /**
     *
     * @param turnoID
     * @param nota
     * @param notaOld
     * @throws Throwable
     */
    public void actualizaNotaDevolutiva(TurnoPk turnoID, Nota nota, Nota notaOld) throws Throwable;

    /**
     *
     * @param idSubtipoSolicitud
     * @param idSolicitud
     * @throws Throwable
     */
    public void updateSubtipoSolicitud(String idSubtipoSolicitud, String idSolicitud) throws Throwable;

    /**
     *
     * @param idMatricula
     * @param turno
     * @return
     * @throws Throwable
     */
    public List getTurnosCertificadoPosteriores(String idMatricula, Turno turno) throws Throwable;

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    public ImprimibleCertificado getImprimible(Integer idImprimible) throws Throwable;

    public ImprimibleRecibo getRecibo(Integer idImprimible) throws Throwable;

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Metodo encargado de la creacion de turno de certificado masivo con
     * tertificados individuales asociados de orden nacional
     *
     * @param transaccion
     * @param solicitante
     * @return Tabla con los turnos, el recibo de pago, y los identificadores de
     * los certificados
     * @throws Throwable
     */
    public Hashtable crearTurnoTransaccion(Transaccion transaccion, Ciudadano solicitante, String idUsuario, String idBanco) throws Throwable;

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Metodo encargado de procesar el pago por ventanilla unica de registro
     *
     * @param transaccion
     * @param solicitante
     * @param idBanco
     * @return Tabla con el turno creado y el imprimible recibo
     * @throws DAOException
     */
    public Hashtable procesarPagoVUR(Transaccion transaccion, Ciudadano solicitante, String idBanco, String idUsuarioSIR, String cedula) throws Throwable;

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
     * asociado retorna null
     *
     * @param solID
     * @return
     * @throws Throwable
     */
    public Turno getTurnoBySolicitudPortal(SolicitudPk solID) throws Throwable;

    /**
     * Trae Datos de impresion retorna arreglo caracter
     *
     * @param solID
     * @return
     * @throws throws Throwable,java.rmi.RemoteException
     */
    public byte[] getDatosImprimible(Integer idImprimible) throws Throwable;

    /**
     * Obtiene los turnos de devolución asociados con el turno ingresado como
     * parámetro.
     *
     * @author: Julio Alcazar
     * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno -
     * Turnos Devolucion Negados
     * @param Turno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    public List getTurnosDevolucion(Turno turno) throws Throwable;

    /**
     * @author : Julio Alcazar
     * @change : Se consulta la informacion destino de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioDestinoTraslado(String matricula) throws Throwable;

    /**
     * @author : Julio Alcazar
     * @change : Se consulta la informacion origen de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioOrigenTraslado(String matricula) throws Throwable;

    /**
     * @author : Diana Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     * @param year El año en el que se va a expedir el recibo de reparto
     * notarial.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialRepartoNotarial(String circuloId, String year) throws HermodException;

    /**
     * @author : Edgar Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     * @param year El año en el que se va a expedir el recibo de reparto
     * notarial.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialRepartoNotarialRecibo(String circuloId, String year) throws HermodException;

    /**
     * @author : Carlos Torres
     * @change : Se agrega fase en el historial del turno
     * @Caso Mantis : 11309: Acta - Requerimiento No 023_453 -
     * Traslado_Masivo_Folios
     * @param tueno <code>Turno</code>
     * @return void
     * @throws HermodException
     */
    public SolicitudFolio getSolicitudFolio(SolicitudFolioPk sfid) throws HermodException;

    /**
     * @author : Carlos Torres
     * @change : Se agrega fase en el historial del turno
     * @Caso Mantis : 0014376
     * @param tueno <code>Turno</code>
     * @return void
     * @throws HermodException
     */
    public void addFaseRestitucionTurno(Turno turno, Usuario usuario) throws HermodException;

    /**
     * @author : Carlos Torres
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas * Obtiene
     * el listado de pantallas administrativas visibles para un respectivo rol.
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerAdministrativasPorRol(Usuario usuario) throws Throwable;

    /**
     * 
     * @param Sttring id documento Pago corregido.
     * @return List con datos de documento pago correccion 
     * recibido como parámetro.
     * @throws HermodException
     */
    public List getDocumentoCorregido(String idDocumentoPago) throws Throwable;
/**
     * 
     * @param idDocumentopago id documento Pago .
     * @return String con la cuenta destino 
     * recibido como parámetro.
     * @throws HermodException
     */
    public String getNoCuentabyDocumentoPago(String idDocumentopago) throws Throwable;
    
    /**
     * @param banco
     * @return idBanco
     * @throws Throwable 
     */
    public String getIdBanco(String banco) throws Throwable;
    
    /**
     * @param canal
     * @return
     * @throws Throwable 
     */
    public String getCanalPago (String canal) throws Throwable;
    /**
     * @param idBanco
     * @param canal
     * @param numero
     * @return
     * @throws Throwable 
     */
    public boolean restringirAddPago(String idBanco, String canal, String numero) throws Throwable;
    /**
     * @param canal
     * @param numero
     * @return
     * @throws Throwable
     */
    public boolean restringirAddPagoByFP(String formaPago, String canal, String numero) throws Throwable;
    
    /**
     * @author : Carlos Torres
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas Obtiene el
     * listado de reportes visibles para un respectivo rol.
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerPantallasPaginaReportesPorRol(Usuario usuario) throws Throwable;

    /**
     * @author: Daniel Forero
     * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS.
     *
     * Permite desbloquear todos los folios asociados a un turno. La liberación
     * de folios no tiene en cuenta el estado del turno, su fase actual, o el
     * usuario al que le pertenencen los bloqueos.
     *
     * @param turno El <code>Turno</code> cuyos folios van a ser liberados.
     * @return El número de folios del turno liberados.
     * @throws HermodException
     */
    public int desbloquearFolios(Turno turno) throws HermodException;

    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    public boolean isIncentivoRegistral(Date fechaDocumento) throws HermodException;

    /**
     * Adiciona relacion círculo banco cuenta
     *
     * @param cuentaBancaria
     * @return
     * @throws Throwable
     */
    public boolean addCuentaBancaria(CuentasBancarias cuentaBancaria) throws Throwable;

    /**
     * Consulta las cuentas bancarias para un círculo y banco específico
     *
     * @return
     * @throws Throwable
     */
    List getCuentasBancarias(String idCirculo, String idBanco) throws Throwable;

    /**
     * Consulta las cuentas bancarias para un círculo
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    List getCuentasBancariasXCirculo(Circulo circulo) throws Throwable;

    /**
     * actualiza el estado para una cuenta bancaria de un circulo y banco en
     * especifico
     *
     * @return
     * @throws Throwable
     */
    public void actualizarEstadoCtaBancaria(String idCirculo, String idBanco, String nroCuenta, boolean estado) throws Throwable;

    /**
     * Obtiene la lista de objetos de tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>Throwable</code>
     */
    List getCanalesRecaudo(boolean flag) throws Throwable;

    /**
     * Obtiene la lista de objetos de tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>Throwable</code>
     */
    List getCanalesRecaudoXCirculo(Circulo circulo) throws Throwable;

    /**
     * Adiciona nuevo canal de recaudo (SIR_OP_CANALES_RECAUDO)
     *
     * @return true o false dependiendo del resultado de la operacion
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>Throwable</code>
     */
    boolean addCanalRecaudo(CanalesRecaudo canalesRecaudo) throws Throwable;

    /**
     * actualiza el estado para una canal de recaudo en especifico
     *
     * @return
     * @throws Throwable
     */
    public void actualizarEstadoCanalRecaudo(int idCanal, boolean estado) throws Throwable;

    /**
     * Valida si para la forma de pago recibida esta activo o no el campo de
     * banco/franquicia
     *
     * @return true o false dependiendo del resultado de la operacion
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>Throwable</code>
     */
    boolean validaCampoBancoFranquicia(TipoPago tipoPago) throws Throwable;

    /**
     * Obtiene una lista de los  <code>CamposCaptura</code> del sistema.
     *
     * @return una lista de objetos <code>CamposCaptura</code>
     * @see gov.sir.core.negocio.modelo.CamposCaptura
     * @throws <code>Throwable</code>
     */
    public List camposCapturaXFormaPago(String formaPagoId) throws Throwable;

    /**
     * Obtiene un objeto <code>CanalesRecaudo</code> del sistema.
     *
     * @return un objeto <code>CanalesRecaudo</code>
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>Throwable</code>
     */
    public CanalesRecaudo getCanalRecaudoByID(int canalRecaudoId) throws Throwable;

    /**
     * Obtiene un objeto <code>TipoPago</code> del sistema.
     *
     * @return un objeto <code>TipoPago</code>
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>Throwable</code>
     */
    public TipoPago getTipoPagoByID(int tipoPagoId) throws Throwable;

    /**
     * Obtiene un objeto <code>CuentasBancarias</code> del sistema.
     *
     * @return un objeto <code>CuentasBancarias</code>
     * @see gov.sir.core.negocio.modelo.CuentasBancarias
     * @throws <code>Throwable</code>
     */
    public CuentasBancarias getCuentasBancariasByID(int cuentaBancariaId) throws Throwable;

    
    public String getIdCtpByParamenters(String formaPagoId, String idCirculo, int idCanal, String idCb) throws Throwable;

    public CirculoTipoPago getCirculoTipoPagoByID(int idCtp) throws Throwable;
    
    
    /**
     * Consulta las cuentas bancarias para un círculo, canal y forma pago
     *
     * @param circulo, idCanalRecaudo, idFormaPago
     * @return
     * @throws Throwable
     */
    List getCuentasBancariasXCirculoCanalForma(Circulo circulo, String idCanalRecaudo, String idFormaPago) throws Throwable;
    
    /**
     * actualiza el estado para un registro en la tabla SIR_NE_CIRCULO_TIPO_PAGO
     *
     * @return
     * @throws Throwable
     */
    public void actualizarEstadoCtp(String idCtp, boolean estado) throws Throwable;  
    
     /**
     * 
     * @author: DNilson226 - Nilson Olaya Gómez - desarrollo3@tsg.net.co
     * Valida si un TURNO en SIR fue Radicado por REL o a traves de REL
     * @param trnoIdWorkFlow
     * @return
     * @throws Throwable
     */
    public boolean validarSiTurnoFueRadicadoXREL(String trnoIdWorkFlow) throws Throwable;

     /**
     * 
     * @author: julian rojas - desarrollo5@tsg.net.co
     * cambia el estado y url en la tabla SIR_OP_TURNO
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public void updateStatusRel(Turno turno) throws Throwable;
}
