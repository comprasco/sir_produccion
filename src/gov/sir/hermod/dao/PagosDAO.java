/*
 * JDOPagosDAO
 * Interfaz para el manejo de los Pagos asociados con los diferentes procesos
 * de la aplicación. 
 */
package gov.sir.hermod.dao;

import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import java.util.Hashtable;
import java.util.List;

import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CirculoTipoPagoPk;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocumentoPagoCorreccion;
import gov.sir.core.negocio.modelo.DocumentoPagoPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced;

import org.auriga.core.modelo.transferObjects.Rol;

/**
 * Interfaz para el manejo de los Pagos asociados con los diferentes procesos de
 * la aplicación.
 *
 * @author mrios, mortiz, dlopez
 */
public interface PagosDAO {
    
    /**
     * Crea un <code>Turno y un <code>Pago</code> en el sistema, y crea una
     * instacia de Workflow de acuerdo con el <code>Proceso</code> determinado.
     *
     * @param p Pago con sus atributos, exceptuando el identificador
     * @param estacion Estación asociada con la fase del proceso.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param usuario Usuario iniciador del proceso.
     * @param delegarUsuario indica si el turno debe ser creado o no en la
     * estación recibida como parámetro.
     * @return Turno Generado luego de procesar el pago.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    Turno procesarPago(Pago p, String estacion, String impresora, Usuario user, Rol rol, boolean delegarUsuario) throws DAOException;

    /**
     * Crea un <code>Turno y un <code>Pago</code> en el sistema, y crea una
     * instacia de Workflow de acuerdo con el <code>Proceso</code> determinado.
     *
     * @param p Pago con sus atributos, exceptuando el identificador
     * @param parametros Hashtable parametros para crear el turno.
     * @return Turno Generado luego de procesar el pago.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Pago
     */
    Turno procesarPago(Pago p, Hashtable parametros) throws DAOException;

    /**
     * Obtiene la informacion existente en la base de datos, de los documentos
     * de pago asociados a un <code>Pago</code> por crearse
     *
     * @param p <code>Pago</code> con sus atributos, exceptuando el
     * identificador
     * @return <code>Pago</code> con toda la informacion de sus documentos de
     * pago
     * @throws DAOException
     * @gov.sir.core.negocio.modelo.Pago
     * @gov.sir.core.negocio.modelo.DocumentoPago
     */
    Pago validarPago(Pago pago) throws DAOException;

    /**
     * Obtiene el numero de recibo de un pago dado su identificador //Caso de
     * certificados asociados
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>String</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    String getNumReciboPagoByID(PagoPk pID) throws DAOException;

    /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador,
     * <p>
     * método utilizado para transacciones
     *
     * @param dpID identificador del documento de pago
     * @param pm <code>PersistenceManager</code> de la transacción
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocumentoPago getDocumentosPagoById(DocumentoPagoPk dpID) throws DAOException;

    /**
     * Obtiene una lista de <code>DocumentoPago</code> dado su identificador,
     * <p>
     * método utilizado para transacciones
     *
     * @param solicitud identificador de la solicitud del documento de pago
     * @return Lista de <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    List getDocumentoPagoBySolicitud(String solicitud) throws DAOException;
     /**
     * Actualiza el Documento Pago 
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>DAOException</code>
     */
    boolean actualizarEstadoDocumento(DocumentoPago Documento) throws DAOException;
    /**
     * Guarda el Documento Pago al cual se le va a hacer la correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>DAOException</code>
     */
    boolean guardardocumentopagoantesdecorreccion(String idDocumento, String iduser) throws DAOException;
    
    /**
     * Extrae una lista con el Documento Pago antes de correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>List </code>
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccion
     * @throws <code>DAOException</code>
     */
    List getDocumentoCorregido(String idDocumentoPago) throws DAOException;
       /**
     * Extrae una string con la cuenta destino del documento
     *
     * @param idDocumento Id de documento Pago
     * @return <code>String </code>
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccion
     * @throws <code>DAOException</code>
     */
    String getNoCuentabyDocumentoPago(String idDocumentoPago) throws DAOException;
    /**
     * Obtiene un <code>DocumentoPago</code> dado el identificador
     * <p>
     * m?todo utilizado para transacciones
     *
     * @param identificador <code>String</code> del documento de pago
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocumentoPago getDocumentobyIdDocPago(String idDocPago) throws DAOException;
    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado el identificador de la
     * consignacion,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador de la consignacion
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocPagoConsignacion getDocPagoConsignacion(String noConsignacion) throws DAOException;

    /**
     * Obtiene un <code>DocPagoCheque</code> dado el identificador del cheque,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador del cheque
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocPagoCheque getDocPagoCheque(String noCheque) throws DAOException;

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado el identificador de
     * <code>DocumentoPago</code>,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador <code>DocumentoPago</code> del documento de pago
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocPagoConsignacion getDocPagoConsignacion(DocumentoPago docPago) throws DAOException;

    /**
     * Obtiene un <code>DocPagoCheque</code> dado el identificador de
     * <code>DocumentoPago</code>,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador <code>DocumentoPago</code> del documento de pago
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    DocPagoCheque getDocPagoCheque(DocumentoPago docPago) throws DAOException;
 /**
     * retorna verdadero si encontro algun dato de este documento pago en correccion forma pago
     * @param idDocumento
     * @return
     * @throws HermodException
     */
    boolean validacionCorreccion(String idDocumento)  throws DAOException;
    /**
     * Obtiene un <code>List</code> de los documentos pago disponibles en el
     * sistema.
     *
     * @return <code>List</code> con los DocumentoPago disponibles en el sistema
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    List getTiposPago() throws DAOException;

    /**
     * Obtiene un <code>List</code> de los tipos de pago disponibles para un
     * círculo específico.
     *
     * @return <code>List</code> con los DocumentoPago disponibles en el sistema
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    List getCirculoTiposPago(CirculoPk cirID) throws DAOException;

    /**
     * Crea un <code>DocumentoPago</code> en el sistema
     *
     * @param dp <code>DocumentoPago</code> con sus atributos, exceptuando el
     * identificador
     * @return <code>DocumentoPago</code> generado, con su identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @see gov.sir.core.negocio.modelo.DocumentoPagoEnhanced
     */
    DocumentoPago crearDocumentoPago(DocumentoPago dp) throws DAOException;

    /**
     * Obtiene un objeto <code>Pago</code> dado su identificador.
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>Pago</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    Pago getPagoByID(PagoPk pID) throws DAOException;

    /**
     * Adiciona un <code>CirculoTipoPago<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>CirculoTipoPago</code> con el identificador pasado dentro
     * del parámetro.
     *
     * @param datos objeto <code>CirculoTipoPago</code> con sus atributos
     * @return identificador del <code>CirculoTipoPago</code> generado.
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    CirculoTipoPagoPk addCirculoTipoPago(CirculoTipoPago dato) throws DAOException;

    /**
     * Elimina un <code>CirculoTipoPago<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que existan objetos relacionados
     * con el  <code>CirculoTipoPago</code> que se pretende eliminar
     *
     * @param datos objeto <code>CirculoTipoPago</code> con sus atributos
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    void removeCirculoTipoPago(CirculoTipoPago dato) throws DAOException;

    /**
     * Hace persistente la información de un Pago, y lo asocia a una solicitud.
     * <p>
     * Método desarrollado para cumplir con los requerimientos específicos del
     * proceso de registro de documentos.
     *
     * @param pago El <code>Pago</code> que se va a hacer persistente.
     * @param estacion El identificador de la estación desde la cual se va a
     * asociar el <code>Pago</code>
     * @return <code>Pago </code> que se ha hecho persistente.
     * @throws <code>DAOException </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago registrarPago(Pago pago, String estacion) throws DAOException;

    /**
     * Retorna un documento de pago registrado en la base de datos. El documento
     * de pago debe ser un cheque o una consignación. Si no encuentra un
     * documento de pago correspondiente retorna null
     *
     * @param doc
     * @return
     * @throws DAOException
     */
    public DocumentoPago getDocumentosPagoExistente(DocumentoPago doc) throws DAOException;

    /**
     * @param pagoID
     * @param numRecibo
     */
    public void setNumeroReciboPago(PagoPk pagoID, String numRecibo) throws DAOException;

    /**
     * @param numRecibo
     */
    public Pago getUltimoPagoByNumeroRecibo(String numRecibo) throws DAOException;

    public boolean existeConsignacionDevolucion(String idDocumentoPago) throws DAOException;

    /**
     * Valida si un documento pago (consignacion o cheque) es valido en
     * devolución.
     *
     * @param doc
     * @return
     * @throws DAOException
     */
    public boolean getDocsPagosDevolucion(DocumentoPago doc) throws DAOException;

    /**
     * Obtiene un <code>List</code> de los campos de captura para la forma de
     * pago recibida
     *
     * @return <code>List</code> con los CamposCaptura disponibles en el sistema
     * @param formaPagoId
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CamposCaptura
     */
    List camposCapturaXFormaPago(String formaPagoId) throws DAOException;
    
    public CanalesRecaudo getCanalRecaudoByID(int canalRecaudoId)throws DAOException;
    
    public TipoPago getTipoPagoByID(int tipoTipoId)throws DAOException;
    
    public CuentasBancarias getCuentasBancariasByID(int cuentaBancariaId)throws DAOException;
    
    public String getIdBanco(String banco) throws DAOException;
    
    public String getCanalPago (String canal) throws DAOException;
    
    public boolean restringirAddPago(String idBanco, String canal, String numero) throws DAOException; 
    
    public boolean restringirAddPagoByFP (String formaPago, String canal, String numero) throws DAOException;
    
//.................................................CARLOS TEST.......................................    
    public String getIdCtpByParamenters(String formaPagoId, String idCirculo, int idCanal, String idCb) throws DAOException;
//.................................................CARLOS TEST....................................... 
    
    public CirculoTipoPago getCirculoTipoPagoByID(int idCtp)throws DAOException;
    
    /**
     * retorna verdadero si se evalua/valida que el Turno fue radicado 
     *  desde el Sistema Externo REL-Radicación Electrónica
     * @param idTurnoWorkFlow
     * @return
     * @throws DAOException
     * @author: DNilson226 - Nilson Olaya Gómez - desarrollo3@tsg.net.co
     */
    public boolean validarSiTurnoFueRadicadoXREL(String idTurnoWorkFlow) throws DAOException;
    
    public boolean numeroDeConsignacionEnUso(String idConsignacion, String idDocumentoPago) throws DAOException;
    
    public boolean datosTarjetaEnUso(String noTarjeta, String noAprobacion, String idDocumentoPago) throws DAOException;
    
}
