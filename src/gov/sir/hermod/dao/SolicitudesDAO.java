package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
                                                                                                       /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              :Se abilita el uso de la clase
    */                  
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;

import java.util.List;

/**
 *
 * @author  mrios, mortiz, dlopez, dsalas
 */
public interface SolicitudesDAO {
    
	SolicitudPk crearSolicitudCertificado(SolicitudCertificado s) throws DAOException;
	SolicitudFotocopia crearSolicitudFotocopia(Solicitud s) throws DAOException;
	SolicitudPk crearSolicitudDevolucion(SolicitudDevolucion s) throws DAOException;
	SolicitudConsulta crearSolicitudConsulta(Solicitud s) throws DAOException;
	SolicitudPk crearSolicitudRegistro(SolicitudRegistro s) throws DAOException;
    Solicitud getSolicitudByID(SolicitudPk sID) throws DAOException;
	Ciudadano crearCiudadano(Ciudadano c) throws DAOException;
	Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo) throws DAOException;
	Ciudadano getCiudadanoByID(CiudadanoPk cID) throws DAOException;
	Circulo getCirculoByID(CirculoPk cID) throws DAOException;
	Folio getFolioByID (FolioPk fID) throws DAOException;
    Usuario getUsuarioByID(UsuarioPk uID) throws DAOException;
	SolicitudConsulta agregarBusqueda (SolicitudConsulta solConsulta, Busqueda busc) throws DAOException;
	SolicitudConsulta agregarCiudadanoConsulta (SolicitudConsulta solConsulta, Ciudadano ciud) throws DAOException;
	SolicitudConsulta modificarBusquedaConsulta (SolicitudConsulta solConsulta, Busqueda busc) throws DAOException;
	/**
	 * @author Cesar Ramírez
	 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
	 * Se recibe el usuario como parametro.
	 **/
	SolicitudRegistro modificarEncabezadoRegistro (SolicitudRegistro solReg, Usuario usuario) throws DAOException;
	SolicitudCertificado modificarSolicitudCertificado (SolicitudCertificado solCer) throws DAOException;
	/**
	* Actualiza una solicitud de Certificado y Consultas.
	* @param solicitud La <code>Solicitud</code> que se va a modificar.
	* @return la <code>Solicitud</code> modificada.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @throws <code>DAOException</code>
	*/
	public Solicitud actualizarSolicitud(Solicitud solicitud) throws DAOException;
	SolicitudCorreccion crearSolicitudCorreccion(Solicitud  s) throws DAOException;
	SolicitudConsulta agregarSolicitudFolio (SolicitudConsulta solConsulta, SolicitudFolio sf) throws DAOException;
	SolicitudPk crearSolicitudCertificadoMasivo(SolicitudCertificadoMasivo  solicitud) throws DAOException;
	public boolean setAnotacionestoSolicitud(long numAnotaciones, Solicitud  s) throws DAOException;
	
	//Reparto Notarial Minutas
	public SolicitudRepartoNotarial crearSolicitudRepartoNotarial(Solicitud s) throws DAOException;	
	
	//Reparto Notarial Restitución
	public SolicitudRestitucionReparto crearSolicitudRestitucionReparto(Solicitud s) throws DAOException;
	

    public SolicitudFolioPk addFolioToTurno(Folio folio,TurnoPk tID, Usuario user) throws DAOException;
	
    public boolean removeFolioFromTurno(Folio folio,TurnoPk tID) throws DAOException;
    
    /**
	 * Registra la matrícula que fue desasociada en los datos básicos del turno
	 * @param folio
	 * @param tID
	 * @return
	 * @throws DAOException
	 */
    public boolean registrarMatriculaEliminadaTurno(Folio folio, TurnoPk tID) throws DAOException;
	
	public boolean addCambioMatriculaAuditoria(String folioViejo, String folionuevo, TurnoPk tID,  Usuario user ) throws DAOException;

        /**
         * @author      :   Julio Alcázar Rivas
         * Caso Mantis  :   02359
	 * verifica que a el turno se le realizo un cambio de matricula.
	 * @param tID
	 * @return <code>true</code> (cambio) o <code>false</code> (no cambio)
	 * @throws DAOException
	 */
        public boolean getCambioMatriculaAuditoria(TurnoPk tID) throws DAOException;
	
	
	/**
	* Método que permite asignar a una <code>SolicitudConsulta</code> persistente,
	* el valor para el atributo numeroMaximoBusquedas recibido como parámetro.
	* @param numMaximo El número máximo de búsquedas permitido para la solicitud.
	* @param solicitud La <code>SolicitudConsulta</code> a la que se va  a
	* asignar el número máximo de búsquedas.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso), 
	* dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	*/
	public boolean setNumMaxBusquedasToSolConsulta (SolicitudConsulta solicitud, int numMaximo)
		throws DAOException; 



	/**
	* Método que permite asociar un rango de matrículas y las respectivas
	* solicitudes folio a una <code>SolicitudRegistro</code>
	* El servicio realiza la validación de los rangos y verifica la existencia
	* de los folios que va a asociar. En caso de que no exista alguno 
	* de los folios, debe partir los  rangos. 
	* @param matInicial Valor de la matrícula inicial del rango.
	* @param matFinal Valor de  la matrícula final del rango.
	* @param solicitud <code>SolicitudRegistro</code> a la que se asociará el 
	* rango de folios. 
	* @param user El <code>Usuario</code> que generó la <code>SolicitudFolio</code>
	* @return Lista con objetos de tipo <code>RangoFolio</code> creados 
	* a partir de los valores de matrículas inicial y final recibidos como
	* parámetros.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	* @see gov.sir.core.negocio.modelo.RangoFolio
	* @see gov.sir.core.negocio.modelo.SolicitudFolio
	*/
	public List addRangoFoliosToSolicitud (String matInicial, String matFinal, Solicitud solicitud, Usuario user, boolean validarAsociar)
	throws DAOException;  
	
	
	/**
	 * Obtiene una solicitud dado su identificador.
	 * <p>
	 * La solicitud debe incluir el listado de Liquidaciones asociadas. 
	 * @param solicitud_id el identificador de la solicitud  que se desea obtener. 
	 * @return la solicitud con el id recibido como parámetro y su listado de liquidaciones
	 * asociadas. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */ 
	 public Solicitud getSolicitudById(String solicitud_id) throws DAOException;
	 
	 

	/**
	* Valida que la solicitud de certificado sea de tipo "ASOCIADO" y que tenga un 
	* padre solicitud de registro que tenga como folio asociado el folio que se pasa por
	* parámetros
	* @param solCer La <code>SolicitudCertificado</code> que se va a validar
	* @param folioID La <code>Folio.ID</code> Identificador del folio a validar
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	* @throws <code>DAOException</code>
	*/
	void validarMatriculaMesaControl(SolicitudCertificado solCer, FolioPk folioID) throws DAOException;
        
	/**
	 * Elimina los actos por solicitud
	 * @param idSolicitud
	 * @throws Throwable
	 */
        public void deleteActosBySolicitud(String idSolicitud) throws DAOException;
        
	/**
	 * Hace que la solicitud quede con UN SOLO folio, el envíado como parámetro.
	 * @param solicitud
	 * @param folio
	 * @throws Throwable
	 */
	public void forceUnFolio(Solicitud solicitud, Folio folio) 
	throws DAOException; 
	
	/**
	 * Agrega una solicitud hija a una solicitud padre.
	 * @param solPadre
	 * @param solHija
	 * @throws Throwable
	 */
	public Solicitud addSolicitudHija(Solicitud solPadre, Solicitud solHija) 
	throws DAOException; 
	
	/**
	* Obtiene el estado de pago de una Solicitud.
	* <p> Si la Solicitud tiene un <code>Pago </code> asociado, se retorna <code> true </code>,
	* en el caso contrario se retorna <code>false </code>
	* @param solicitud La <code>Solicitud</code> en la cual se va a consultar el estado de 
	* los pagos.
	* @return <code>true </code> si la solicitud ya tiene un <code>Pago</code> o <code>false </code> 
	* en el caso contrario. 
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Pago
	* @throws <code>DAOException</code>
	*/
	public boolean getEstadoPagoSolicitud (Solicitud solicitud) throws DAOException;
	


	/**
	 * Actualiza los documentos fotocopia asociados a la solicitud de fotocopia
	 * @param  sol <code>SolicitudFotocopia</code> con identificador y sus documentos fotocopia, 
	 * cada uno con su nuevo tipo de fotocopia y su nuevo número de hojas
	 * @return La solicitud completa de fotocopia con sus documentos actualizados
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudCertificado
	 */
	public SolicitudFotocopia updateDocumentosFotocopia(SolicitudFotocopia sol)
		throws DAOException;
		


	/**
	 * Cambia el flag de aprobación de una solicitud de corrección o devolución
	 * @param solID Id de la solicitud
	 * @param aprobada Nuevo estado del flag de aprobación
	 * @return
	 * @throws DAOException
	 */
	public boolean setAprobacionSolicitud(SolicitudPk solID, boolean aprobada) throws DAOException;
	
	
	/**
	 * Setea los datos de antiguo sistema a una solicitud de corrección. Si los datos de antiguo sistema ya
	 * está seteados para la solicitud se reescriben
	 * @param sol Solicitud de corrección con el ID seteado
	 * @param datosAntiguoSistema
	 * @return
	 * @throws DAOException
	 */
	public boolean setDatosAntiguoSistemaToSolicitud(Solicitud sol, DatosAntiguoSistema datosAntiguoSistema) throws DAOException;
	
	
	/**
	 * Valida si la solicitud se puede asociar como solicitud vinculada
	 * @param solicitudID
	 * @return
	 * @throws DAOException
	 */
	public boolean validarSolicitudVinculada(SolicitudPk solicitudID) throws DAOException ;
	
	/**
	 * Crea una nueva solicitud para el proceso de antiguo sistema
	 * @param sol La solicitud que se almacenará en la base de datos
	 * @return
	 */
	public SolicitudPk crearSolicitudAntiguoSistema(SolicitudAntiguoSistema sol) throws DAOException;
	
	/**
	 * Se incrementan los intentos de reimpresión de una solicitud específica
	 * @param idSolicitud
	 * @throws Throwable
	 */
	public void incrementarIntentoReimpresionRecibo(SolicitudPk idSolicitud) throws DAOException;
	
	/**
	* Elimina la <code>Solicitud</code> recibida como parámetro.
	* @param solicitud La <code>Solicitud</code> que va a ser eliminada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @throws <code>Throwable</code>
	*/
	public boolean deleteTurnoAnterior(SolicitudPk idSolicitud) throws DAOException;
	
	/**
	 * Consulta las solicitudes que tengan el <code>Turno</code> como turno anterior
	 * @param turnoID
	 * @return
	 * @throws DAOEXception
	 */
	public List getSolicitudesByTurnoAnterior (Turno turno) throws DAOException;
	
	/** Pablo Quintana Junio 19 2008
		Consulta un testamento asociado a un turno*/
	public Testamento getTestamentoByID(String idSolicitud) throws DAOException;
	
	public void removeTestadorFromTestamento(TestamentoCiudadanoPk testamentoCiudadanoID)throws DAOException;
	
	public void actualizaSolicitud(Solicitud solicitud,List consignaciones)throws DAOException;
	
	public void updateSubtipoSolicitud(String idSubtipoSolicitud, String idSolicitud)throws DAOException;
        /**
	 * Obtiene una solicitud dado su identificador.
	 * <p>
	 * La solicitud debe incluir el listado de Liquidaciones asociadas. 
	 * @param solicitud_id el identificador de la solicitud  que se desea obtener. 
	 * @return la solicitud con el id recibido como parámetro y su listado de liquidaciones
	 * asociadas. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */ 
	 public SolicitudFolioEnhanced getSolicitudFolio(SolicitudFolioPk sfid) throws DAOException;
	
}
