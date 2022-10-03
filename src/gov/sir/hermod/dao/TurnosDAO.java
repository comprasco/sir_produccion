package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.NotaPk;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.ReproduccionSellos;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.UsuarioPk;

import gov.sir.hermod.HermodException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;


/**
 * Presta los servicios de acceso a datos para información de turnos
 * @author mrios
 * @author mortiz
 * @author dsalas 
 */
public interface TurnosDAO {
    
    void eliminarActos(String idSolicitud) throws DAOException;
    List getTurnos(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws DAOException;
    List getTurnosPMY(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws DAOException;
    List getControlMatriculaTurno(String turnoID) throws DAOException;
    Boolean isTurnoREL(String idWorkflow) throws DAOException;
    Boolean isTurnoDevuelto(String idWorkflow) throws DAOException;
    void setStatusREL(String status, String url, String idWorkflow) throws DAOException;
    void setStateNotaNotificada(String state, String idWorkflow) throws DAOException;
    String currentStateNotaNotificada(String idWorkflow) throws DAOException;
    public String getFechaTurnoJuzgado(String idWorkflow) throws DAOException;
    void addNotasInformativas(List notasInformativas) throws DAOException;
    void notificarNotaDevolutiva(NotificacionNota notify) throws DAOException;
    void addCtrlMatricula(String idMatricula, String accion, String rol, String idWorkflow) throws DAOException;
    int diasHabiles(String idCirculo, String fecha) throws DAOException;
    List getNotaDevNotificada(String turnoWF) throws DAOException;
    void addCtrlReasignacion(Turno turno, String usuarioOrigen, String usuarioDestino) throws DAOException;
    int getReasignacion() throws DAOException;
    String getStringByQuery(String sql) throws DAOException;
    String getCopiaImp(String idCirculo) throws DAOException;
    int getLimiteReasignacion(Turno turno) throws DAOException;
    void updateRecurso(Recurso recurso, String turnoWF) throws DAOException;
    void deleteRecurso(String idRecurso, String turnoWF) throws DAOException;
    void executeDMLFromSQL(String sql) throws DAOException;
    int getNumNotasInformativas(Turno turno, String tipoNota) throws DAOException;
    String getEstacionFromRevisor(Turno turno) throws DAOException;
    String getEstacionFromRecursosNota(Turno turno) throws DAOException;
    boolean isEstacionActivaCalificador(String estacionId) throws DAOException;
    List isMatriculaNotificacionDev(String idMatricula) throws DAOException;
    int getNotasNecesarias(Turno turno) throws DAOException;
	//void crearInstanciaWF(Turno turno) throws DAOException;
	boolean addTurnoHistoria(Turno turno, TurnoHistoria turnoHistoria, Integer numCopias) throws DAOException;
	void updateTurno(Turno turno) throws DAOException;
	void updateTurnoReimpresionCertificado(Turno turno, Usuario usuario, Folio folio) throws DAOException;
	void actualizarTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws DAOException;
	public void actualizarTurnoModoBloqueo (Turno turno) throws DAOException;
	boolean validarAnulacionTurno(TurnoPk idTurno) throws DAOException;
	void anularTurno(Turno turno) throws DAOException;
	void habilitarTurno(Turno turno) throws DAOException;
    Turno getTurnoByWFId(String wfId) throws DAOException;
    
	/**
	 * Retorna la lista de los identificadores de las estaciones SAS a donde se despacho un turno dado.
	 * @param fase Fase a partir de la cual se quiere saber las estaciones donde se despacho el turno.
	 * @param tID Identificador del turno que se quiere averiguar.
	 * @return Collection de objetos String
	 * @throws <code>DAOException</code>
	 */
	public List getEstacionesActuales(String fase, String idWF) throws DAOException;
    
	/**
	 * Obtiene un Turno dado su identificador.
	 * <p>Retorna las anotaciones dependiendo del atributo pasado como
	 * parámetro. 
	 * @param tID identificador del Turno
	 * @param anotaciones Indica si deben o no retornarse las anotaciones. 
	 * @return Turno con sus atributos y jerarquia: Circuloproceso, Solicitud, Liquidacion, Pagos e Histotia,
	 * y las anotaciones dependiendo del atributo pasado como parámetro. 
	 * @throws DAOException
	 */
    Turno getTurnoByID(TurnoPk tID) throws DAOException;
    
    
    NotaPk addNotaToTurno(Nota nota, TurnoPk tID) throws DAOException;
    
    
	/**
	* Crea un <code>Testamento</code> persistente y lo asocia a la solicitud de un turno de registro de documentos.
	* @param Turno El turno <code>Turno</code> al que se va a asociar el <code>Testamento</code>.
	* @param testamento El <code>Testamento</code> que va a asociarse a la solicitud de registro de documentos.
	* @return resultado<code>boolean</code> el resultado de el ingreso de el Testamento.
	* @see gov.sir.core.negocio.modelo.Testamento
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>Throwable</code>
	*/
	boolean addTestamentoToSolicitudRegistro(TurnoPk tid, Testamento testamento) throws DAOException;    
    	
		
	
	/**
	* Agrega un <code>Usuario</code> a un <code>Turno</code>
	* @param user  <code>Usuario</code> que será asignado al <code>Turnoz</code>
	* @param turno <code>Turno</code> al que será asignado el <code>Usuario</code>
	* @return true o false dependiendo del resultado de la operación. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.Turno
	*/
	public boolean setUsuarioToTurno(Usuario user, Turno turno) 
	throws DAOException;
   
	/**
	* Obtiene una lista de objetos <code>Turno</code> que estan en el <code>Proceso</code> 
	* la <code>Fase</code> y el <code>Circulo</code> ingresado
	* @param proceso <code>Proceso</code> 
	* @param fase <code>Fase</code>
	* @param circulo <code>Circulo</code> 
	* @return una lista de objetos <code>Turno</code>. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	* @see gov.sir.core.negocio.modelo.Fase
	* @see gov.sir.core.negocio.modelo.Circulo
	*/
	List getTurnosFase(Proceso proceso, Fase fase, Circulo circulo) throws DAOException;

	/**
	* Obtiene una lista de objetos <code>Turno</code> que estan sociados
	* con el <code>Folio</code> correspondiente al numero de matricula
	* ingresado.
	* @param matricula <code>String</code> 
	* @return una lista de objetos <code>Turno</code>. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Folio
	*/
	List getTurnosByMatricula(String matricula) throws DAOException;
        
        /**
	* @param reproduccion <code>String</code> 
	* @return bool si fue exitosamente guardado
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ReproduccionSellos
	*/
        boolean CreateReproduccionSellosReg(ReproduccionSellos reproduccion) throws DAOException;
        
        /**
     * Captura Datos de Calificador para registrador
     *
     * @param idTurno El turno <code>Turno</code> Workflow
     * @param Circulo El <code>Circulo</code> Su id
     * @param Activo El <code>Testamento</code> Si paso por registrador
     * @return List<code>ReproduccionSellos</code> 
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws <code>DAOException</code>
     */
        List getListReproduccionSellos(String idTurno, String Circulo, int Activo) throws DAOException;
	/**
	* Obtiene los turnos anteriores asociados con el turno con identificador ingresado
	* como parámetro. 
	* @param idTurno Identificador del turno del cual se consultarán los turnos anteriores.
	* @return lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	List getTurnosAnteriores(String idTurno) throws DAOException;
	
	
	/**
	* Obtiene los turnos siguientes asociados con el turno con identificador ingresado
	* como parámetro. 
	* @param idTurno Identificador del turno del cual se consultarán los turnos siguientes.
	* @return lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	List getTurnosSiguientes(String idTurno) throws DAOException;
         
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        **/
        /**
	* Obtiene los turnos siguientes asociados con el turno con identificador ingresado
	* como parámetro. 
	* @param idTurno Identificador del turno del cual se consultarán los turnos siguientes.
	* @return lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	List getTurnosSiguientesTestamento(String idTurno) throws DAOException;
	
	/**
	* Obtiene los turnos siguientes asociados con el turno con identificador ingresado
	* como parámetro. 
	* @param idTurno Identificador del turno del cual se consultarán los turnos siguientes.
	* @return lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	List getTurnosSiguientesDevolucion(String idTurno) throws DAOException;



	/**
	* Obtiene una lista de turnos dentro de un rango dado, que han pasado por una fase dada.
	* @param idFase identificador de la fase por la cual debe haber pasado el turno.
	* @param turnoInicial identificador del turno inicial dentro del rango en el que se va a 
	* realizar la consulta.
	* @param turnoFinal identificado del turno final dentro del ranqo en el que se va a
	* realizar la consulta
	* @return Lista de todos los turnos que han pasado por la fase dada y que están comprendidos
	* entre el rango de turnos dado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	*/
	public List getRangoTurnosByFase (String idFase, String turnoInicial, String turnoFinal) throws DAOException;
	
	
	
	/**
	* Modifica la categoría de clasificación de una solicitud de registro.
	* @param clasificacion Valor que va a ser asignado al atributo categoría de clasificación de la solicitud
	* de registro.
	* @param turnoActualizado
	* @return <code> true </code> o <code>false </code> dependiendo del resultado de la operación.
	* @throws <code>DAOException </code>
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	*/
	public boolean updateClasificacionSolicitudRegistro (String clasificacion, Turno turnoActualizado) throws DAOException;
	
	
	
	/**
	* Modifica el atributo ajuste de una <code>SolicitudRegistro</code> 
	* @param turno <code>Turno</code> que tiene asociada la Solicitud.
	* @param valor el boolenao que va a ser asignado al atributo ajuste de la <code>SolicitudRegistro</code> 
	 * @return <code>true </code> o <code>false </code> dependiendo del resultado de la operación. 
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	public boolean updateAjusteInTurnoRegistro (Turno turno, boolean ajuste) throws DAOException;
	
	
	/**
	 * Crear un turno de registro asociado a la solicitud recibida como parámetro. 
	 * @param solicitud La <code>Solicitud</code> que va a ser asociada al turno.
     * @param usuarioSir <code>Usuario</code> que crea el turno.
	 * @return el turno creado.
	 * @throws <code>DAOException </code>
	 */
	public Turno crearTurnoRegistro (SolicitudRegistro solicitud, Usuario usuarioSir) throws DAOException;
	
	/**
	 * Crea de manera persistente un recurso de apelación, reposición o el tipo que corresponda
	 * @param idTurnoHistoria
	 * @param idTipoRecurso
	 * @param textRecurso
	 * @return
	 * @throws DAOException
	 */
	public RecursoPk crearRecurso(Recurso recurso)	throws DAOException;
	
	public void updateRecurso(RecursoPk rid, String datoAmpliacion)throws DAOException;
	
	/**
	 * Colocar la respuesta a un recurso
	 * @param recurso
	 * @throws DAOException
	 */
	public void setRespuestaRecurso(Recurso recurso) throws DAOException;

	/**
	 * Obtiene la lista de turnos de fotocopias que se encuentren en la fase
	 * de 'FOT_PAGO' durante más de n días (n es pasado por parámetros)
	 * @param circulo Círculo del cual se desea buscar los turnos
	 * @param dias Número de dias 
	 * @return
	 * @throws HermodException
	 */
	public List getTurnosFotocopiasConPagoVencido(Circulo circulo, double dias) throws DAOException;
	
	
	
	/**
	* Crear un turno de fotocopias asociado a la solicitud recibida como parámetro.
	* @param solicitud La <code>Solicitud</code> que va a ser asociada al turno.
	* @return el turno creado.
	* @param usuarioSir <code>Usuario</code> que realiza el proceso. 
	* @throws <code>DAOException </code>
	*/
	public Turno crearTurnoFotocopias (SolicitudFotocopia solicitud, Usuario usuarioSir) throws DAOException;
	
  
	/**
	 * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
	 * asociado retorna null
	 * @param solID
	 * @return
	 * @throws DAOException
	 */
	public Turno getTurnoBySolicitud(SolicitudPk solID) throws DAOException;
	
	

	/**
	 * Crea un oficio, el oficio debe tener sus atributos básicos y una asociación
	 * con un turno historia existente.
	 * @param oficio
	 * @return
	 * @throws DAOException
	 */
	public OficioPk crearOficio(Oficio oficio) throws DAOException;
	
	/**
	 * Elimina oficios, el oficio debe tener sus atributos básicos y una asociación
	 * con un turno historia existente.
	 * @param oficios
	 * @return
	 * @throws DAOException
	 */
	public void eliminarOficios(List oficios) throws DAOException;
   
   
	/**
	 * Actualiza la firma del oficio con el ID especificado en el flag
	 * indicado
	 * @param oficioID
	 * @param firmado
	 * @return
	 * @throws DAOException
	 */
	public boolean actualizarFirmaOficio(OficioPk oficioID, boolean firmado)  throws DAOException;
   
   
	
	/**
	 * Asocia un oficio respuesta a un recurso. Ambos deben existir en la base de datos. El objeto
	 * queda en el atributo oficioRespuesta del recurso
	 * @param recursoID
	 * @param oficioID
	 * @return
	 * @throws DAOException
	 */	
	public boolean setOficioRespuestaToRecurso(RecursoPk recursoID, OficioPk oficioID) throws DAOException;
	

	/**
	 * Obtiene los oficios asociados al turno. Cada oficio tiene el turno historia en el 
	 * que fue creado
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getOficiosTurno(TurnoPk oid) throws DAOException;
	

	/**
	 * Actualiza el número del oficio con el ID especificado
	 * @param oficioID
	 * @param firmado
	 * @return
	 * @throws DAOException
	 */
	public boolean actualizarNumeroOficio(OficioPk oficioID, String nuevoNumero)  throws DAOException;
	
	/**
	 * Agrega la fecha de firma al oficio con el ID especificado
	 * @param oficioID
	 * @param fechaFirma
	 * @return
	 * @throws DAOException
	 */
	public boolean agregarFechaFirmaOficio(OficioPk oficioID, Date fechaFirma)  throws DAOException;
	

	/**
	* Obtiene el listado de turnos calificados por un <code>Usuario</code> y que no han pasado
	* por la fase de firma del registrador.
	* @param usuario El identificador del usuario del cual se están consultando sus turnos calificados.
	* @param Circulo circulo del calificador
	* @return Lista con los turnos calificados por el <code>Usuario</code> dado 
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	public List getTurnosByUsuarioCalificador (long usuario, Circulo circulo) throws DAOException;
	
	/**
    * Obtiene el listado de turnos radicados en este dia y que se encuentren actualmente en confrontación.
    * @return Lista con los turnos validos
    * @see gov.sir.core.negocio.modelo.Turno
    * @throws <code>DAOException</code>
    */
	public List listarTurnosRegistroParaAgregarCertificadosAsociados (Circulo cir) throws DAOException;

	/**
	 * Devuelve el último usuario asignado a un turno en calificación que tenga el folio con el ID
	 * folioID asociado
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */	
	public Usuario getUsuarioConTurnoEnCalificacionConFolioAsociado(FolioPk folioID) throws DAOException;
	
   
	/**
	 * Verifica si un turno es válido entre los registros de ejecución de SAS
	 * @param idWorkflow
	 * @return
	 * @throws DAOException
	 */
	public boolean isValidTurnoSAS(String idWorkflow) throws DAOException;
	
	/**
	 * Obtiene el administrador SAS del último registro de ejecucion de un turno
	 * @param idWorkflow
	 * @return
	 * @throws DAOException
	 */
	public String lastAdministradorTurnoSAS(String idWorkflow) throws DAOException;

	/**
	 * Setea el conjunto de permisos configurados de un turno
	 * @param turnoID
	 * @param permisos
	 * @return
	 * @throws DAOException
	 */
	public boolean asignarPermisosCorreccion(TurnoPk turnoID, List permisos) throws DAOException;
	
	

	/**
	 * Refresca el subtipo de atención del turno dependiendo de las nuevas características de éste
	 * @param turnoID
	 * @param permisos
	 * @return
	 * @throws DAOException
	 */
	public boolean refrescarSubtipoAtencionTurno(TurnoPk turnoID) throws DAOException;
	
	
	/**
	 * Asigna un estado a la solicitud folio del turno y folio determinado.
	 * @param turnoID
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean updateEstadoSolicitudFolio(SolicitudFolio solFolio) throws DAOException;
	
	/**
	 * Asigna una marca al folio dentro del turno especificado
	 * @param turnoID
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean marcarFolioInTurno(TurnoPk turnoID, FolioPk folioID) throws DAOException;
	
	/**
	 * Desmarca todos los folios asociados a un turno
	 * @param turnoID
	 * @return
	 * @throws DAOException
	 */
	public boolean desmarcarFoliosInTurno(TurnoPk turnoID) throws DAOException;
	
	

	/**
	 * Indica si el turno tiene por lo menos un acto del tipo indicado
	 * @param turnoID
	 * @param tipoActoID
	 * @return
	 * @throws DAOException
	 */
	public boolean hasActoTurnoRegistro(TurnoPk turnoID, TipoActoPk tipoActoID) throws DAOException;
	
	
	
	/**
	 * Elimina los modos de bloqueo y stack pos ingresados al avanzar push,  de acuerdo
	 * con el número de avances ingresado como parámetro. 
	 * @return <code> true </code> o <code> false </code> dependiendo del resultado de la operación. 
	 * @throws <code>DAOException </code>
	 * @param turno El <code>Turno</code> sobre el cual se va a realizar la actualización. 
	 * @param cantidad El número de operaciones avanzar push que debe deshacerse. 
	 */
	public boolean deshacerAvancesPush (Turno turno, int cantidad) throws DAOException;	
	
	

	/**
	 * Asigna una marca al folio recién creado en antiguo sistema dentro del turno especificado
	 * @param turnoID
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean marcarFolioRecienCreadoASInTurno(TurnoPk turnoID, FolioPk folioID) throws DAOException;
	
	/**
	 * Desmarca todos los folios recién creados en antiguo sistema asociados a un turno
	 * @param turnoID
	 * @return
	 * @throws DAOException
	 */
	public boolean desmarcarFoliosRecienCreadoASInTurno(TurnoPk turnoID) throws DAOException;
	

	/**
	 * Indica si el turno tiene una nota informativa en la última fase
	 * @param turnoID
	 * @return
	 * @throws DAOException
	 */
	public boolean hasNotaInLastFase(TurnoPk turnoID) throws DAOException;
	
	/**
	 * Retorna las notas del turno correspondientes a la última fase
	 * @param turnoID
	 * @return
	 * @throws DAOException
	 */
	public List getNotasInLastFase(TurnoPk turnoID) throws DAOException;
	
	public Turno crearTurno(Pago p, String sAnio, String sCirculo, 
		String sProceso, String idTurno, Date dFechaInicio, Usuario user) throws DAOException;
    /**
     * @param proceso
     * @param fase
     * @param fecha
     * @param circulo
     * @return
     */
    List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws DAOException;


    /**
     * @param proceso
     * @param fase
     * @param fecha
     * @param circulo
     * @return
     */
    List getTurnosByFechaAndCirculoMinusMasivos(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws DAOException;

    
  
	/**
	 * Obtiene la lista de turnos que se encuentren en la fase
	 * de 'PMY_REGISTRAR' durante más de 2 meses
	 * @param circulo Círculo del cual se desea buscar los turnos
	 * @return
	 * @throws HermodException
	 */
	List getTurnosConPagoMayorValorVencido(Circulo circulo) throws DAOException;
	
	/**
	 * Obtiene la lista de turnos que se encuentren en la fase
	 * de 'PMY_REGISTRAR', es decir, que están pendientes de pago
	 * @param circulo Círculo del cual se desea buscar los turnos
	 * @return
	 * @throws HermodException
	 */
	public List getTurnosConPagoMayorValorPendiente(Circulo circulo) throws DAOException;
    
	/**
     * @param idMatricula
     * @return
     */
    List getTurnos(String idMatricula) throws DAOException;
	
	/**
     * @param proceso
     * @param circulo
     */
    List getTurnosCirculo(Proceso proceso, Circulo circulo, String idMatricula) throws DAOException;
    
    /**
     * @param circulo
     * @return
     */
    List getTurnosCirculo(Circulo circulo, String idMatricula) throws DAOException;
   	
   	
	/**
	* Elimina del sistema las notas devolutivas asociadas con un turno.
	* @param turno identificador del turno al cual se le van a eliminar las notas devolutivas.
	* @throws <code>DAOException</code>
	*/
	public void removeDevolutivasFromTurno (TurnoPk turnoID) throws DAOException;

	/**
	 * 
	 * @param turnoRegistro
	 * @param turnoCertificado
	 * @throws DAOException
	 */
   	public void addCertificadoAsociado(Turno turnoRegistro, Turno turnoCertificado) throws DAOException;
   	
   	/**
   	 * 
   	 * @param turnoID
   	 * @param pagoID
   	 * @throws DAOException
   	 */
   	public void removeLiquidacionesSinPagoFromTurno(TurnoPk turnoID) throws DAOException;
   	
   	/**
   	 * Elimina la relación de un turno
   	 * @param turnoID
   	 * @throws DAOException
   	 */
   	public void eliminarRelacionTurno(TurnoPk turnoID) throws DAOException;
   	
   	/**
   	 * 
   	 * @param tid
   	 * @return
   	 * @throws DAOException
   	 */
   	public TipoNota getTipoNota(TipoNotaPk tid) throws DAOException;
   	
   	/**
   	 * Dado el identificador de un turno, obtiene el turno hijo de este.
   	 * @param id Identificador del turno padre
   	 * @return El turno derivado del turno con el identificador dado
   	 */
	public Turno getTurnoDependiente(TurnoPk id) throws DAOException;
	
	/**
	 * Crea un turno derivado de un turno padre.
	 * @param turnoPadre El turno del cual derivar el nuevo turno
	 * @param usuario El usuario que realiza la operación
	 * @param idProceso El identificador del proceso en el que se quiere iniciar el nuevo turno dependiente
	 * @return El turno derivado del turno padre
	 * @throws DAOException
	 */
	public Turno crearTurnoDependiente(Turno turnoPadre, Usuario usuario, long idProceso) throws DAOException;
	
	/**
	 * Obtiene el turno del que un turno fue derivado
	 * @param turnoId Identificador del turno para el que se quiere determinar su padre
	 * @return El turno del que el turno fue derivado
	 * @throws DAOException
	 */
	public Turno getTurnoPadre(TurnoPk turnoId) throws DAOException;
	
	/**
	* Agrega una Nota de actuaciones al turno dado.
	* @param Identificador del turno <code>Turno.ID</code> a la que se va a asociar la <code>NotaActuacion</code>
	* @param nota actuación la <code>NotaActuacion</code> que va a ser asociada al turno.
	* @return El resultado de la adición de la nota de actuaciones administrativas.
	* @see gov.sir.core.negocio.modelo.TurnoPk
	* @see gov.sir.core.negocio.modelo.NotaActuacion
	* @throws <code>Throwable</code>
	*/
	public boolean agregarNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws DAOException;
	
	/**
	* Actualiza una Nota de actuaciones al turno dado.
	* @param Identificador del turno <code>Turno.ID</code> al que se va a actualizar la <code>NotaActuacion</code>
	* @param nota actuación la <code>NotaActuacion</code> que va a ser actualizada al turno.
	* @return El resultado de la actualización de la nota de actuaciones administrativas.
	* @see gov.sir.core.negocio.modelo.TurnoPk
	* @see gov.sir.core.negocio.modelo.NotaActuacion
	* @throws <code>Throwable</code>
	*/
	public boolean actualizarNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion)throws DAOException;	
	   	
	/**
	 * Obtiene el último turno generado por un usuario en un proceso especifico
	 * @param idUsuario
	 * @param idProceso
	 * @return
	 * @throws Throwable
	 */
	public Turno getUltimoTurnoProcesoUsuario(UsuarioPk idUsuario, ProcesoPk idProceso, CirculoPk idCirculo) throws DAOException;
	
           
        //
        /**
	 * Obtiene el último turno generado por un usuario en un proceso especifico
	 * @param idUsuario
	 * @return
	 * @throws Throwable
	 */
	public String getUltimaSolicitudLiquidacion(UsuarioPk idUsuario, CirculoPk idCirculo) throws DAOException;
	
        
	/**
	 * Obtiene el ultimo turno por usuario que haya registrado el pago de mayor valor
	 * @param idUsuario
	 * @param idProceso
	 * @param idCirculo
	 * @return
	 * @throws DAOException
	 */
	public Turno getUltimoTurnoMayorValorUsuario(UsuarioPk idUsuario, ProcesoPk idProceso, CirculoPk idCirculo) throws DAOException;
	
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getImprimiblesPendientesByWfId( java.lang.String, java.lang.String )
     */
	public List getImprimiblesPendientesByWfId( String turno_WfId, String tipoImprimibleId ) throws DAOException;
	
	/**
	 * Permite marcar o desmarcar el turno para indicar que al turno se le interpuso un recurso o revocatoria directa.
	 * @param turno
	 * @return
	 * @throws Throwable
	 */
	public boolean actualizarMarcaRevocatoriaTurno(Turno turno) throws DAOException;
	
	/**
	 * Realiza la consulta de los turnos que fueron bloqueados y que están para ser reanotados.
	 * @param circulo
	 * @return
	 * @throws Throwable
	 */
	public List consultarTurnosAReanotar(Circulo circulo) throws DAOException;	
	
	/**
	 * Permite colocar en la fase calificación, un turno que ya se encuentra finalizado.
	 * @param turno
	 * @param parametros
	 * @return
	 * @throws Throwable
	 */
	public boolean reanotarTurno(Turno turno, Hashtable parametros) throws DAOException;

	/**
	* Obtiene la lista de turnos a partir de una estación y una fase.
	* Este método devuelve el turno con un turno historia en dónde se tiene
	* la información de la fase y la estación asignada.
	* @param estacion <code>Estacion</code> sobre la cual se buscan los turnos.
	* @param fase <code>Fase</code> sobre la cual se buscan los turnos.
	* @return una lista de objetos <code>Turno</code>
	* @return una lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Circulo
	* @see gov.sir.core.negocio.modelo.Usuario
	* @throws <code>Throwable</code>
	*/
	public List getTurnosAReasignar(Estacion estacion, Fase fase) throws DAOException;
	
	
	/**
	* Actualiza la estación que tiene un turno por otra nueva estación.
	* @param turno <code>Turno</code> sobre el cual se va a cambiar de estación que lo tiene.
	* @param estacionDestino <code>Estacion</code> a la que va a quedar asignado un turno.
	* @return una lista de objetos <code>Turno</code>
	* @throws <code>Throwable</code>
	*/
	public boolean reasignarTurno(Turno turno , Estacion estacionDestino)throws DAOException;
	
        /**
	* Actualiza el usuario que atendió el último turno historia de un proceso dado.
	* @param turno <code>Turno</code> sobre el cuál se quiere actualizar el turno historia.
	* @param nombreFase <code>String</code> sobre la cuál se quiere actualizar el turno historia.
	* @param usuarioAtiende <code>Usuario</code> que atendió la fase dada. 
	* @return <code>boolean</code> con la respuesta se se actualizó o nó el turno.
	* @throws <code>Throwable</code>
	*/
	public boolean actualizarUsuarioAtiendeTurnoHistoria(Turno turno, String nombreFase, Usuario usuarioAtiende)throws DAOException;
	
	/**
	 * Permite cambiar la respuesta en el historial de turnos del último turno
	 * que tenga la fase especificada.
	 * @param turno
	 * @fase fase del turno a cambiar
	 * @respueta nuevo valor de la respuesta del turno
	 * @return
	 * @throws Throwable
	 */
	public boolean modificarRespuestaUltimaFase(Turno turno, Fase fase, String respuesta) 
		throws DAOException;
   	
	/**
	 * Elimina las notas informativas asociadas al turno, 
	 * solo elimina la última nota en donde se encuentra el turno en estos momentos
	 * @param turnoID
	 * @throws DAOException
	 */
	public void eliminarNotasInformativasUltimaFaseTurno (TurnoPk turnoID) throws DAOException;
          
	/**
	 * Permite marcar o desmarcar el turno como turno de certificados nacional
	 * @param turno
	 * @return
	 * @throws Throwable
	 */
	 public boolean actualizarMarcaTurnoNacional(Turno turno) throws DAOException;
	 
	 /**
	  * Permite actualizar el Historial al reasignar el turno sin modificar su contenido
	  * @param turno
	  * @param turnoHistoria
	  * @return
	  * @throws DAOException
	  */
	 boolean addTurnoHistoriaReasignacion(Turno turno, TurnoHistoria turnoHistoria) throws DAOException;
	 
	 public boolean validarFolioTurnoReanotacion(String idMatricula, Turno turno) throws DAOException;
	 
	 public void reanotarTurno(Turno turno, Nota nota, Usuario calificador, Usuario usuario, Estacion estacion) throws DAOException;
	 
	 public List getTurnosCertificadoPosteriores(String idMatricula, Turno turno) throws DAOException;
	 
	 public void removeNotaDevolutivaFromTurno (TurnoPk turnoID,Nota nota) throws DAOException;
	 
	 public void actualizaNotaDevolutiva (TurnoPk turnoID,Nota nota,Nota notalOld) throws DAOException;
         
         
       /**
	    * Author: Ingeniero Diego Hernandez
        * Modificado en 2010/02/23 by jvenegas
	    */
         public Turno getTurnoBySolicitudPortal(SolicitudPk solID) throws DAOException;

         /**
	* Obtiene los turnos de devolucion asociados con el turno ingresado como parámetro.
        * @author: Julio Alcazar
        * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
	* @param Turno Identificador del turno del cual se consultarán los turnos siguientes.
	* @return lista de objetos <code>Turno</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
	List getTurnosDevolucion(Turno turno) throws DAOException;
        
         /**
	* Obtiene los turnos de devolucion asociados con el turno ingresado como parámetro.
        * @author: Carlos Torres
        * @change: 0014376
	* @param Turno Identificador del turno del cual se consultarán los turnos siguientes.
	* @return <code>Void</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @throws <code>DAOException</code>
	*/
        public void addFaseRestitucionTurno(Turno turno,Usuario usuario) throws DAOException;
        
        
	void updateStatusRel(Turno turno) throws DAOException;
}
