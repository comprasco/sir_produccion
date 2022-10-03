package gov.sir.hermod.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoEjecucion;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.hermod.workflow.Message;


/**
 * @author dlopez
 * Clase para el nuevo manejo de creaci�n y avance de turnos a trav�s 
 * del aplicativo.
 * <p>
 * Provee servicios para la consulta, modificaci�n y adici�n de los objetos 
 * que intervienen en este proeceso. 
 */
public interface TurnosNuevosDAO {

	/**
	 * Crea un <code>Turno de reparto notarial en el sistema,
	 * y crea una instacia de Workflow de acuerdo con el <code>Proceso</code>
	 * determinado.
	 * @param estacion Estaci�n asociada con la fase del proceso.
	 * @param user Usuario Usuario iniciador del proceso.
	 * @return Turno Generado 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 * @see gov.sir.core.negocio.modelo.Proceso
	 */
	Turno crearTurnoRepartoNotarial(SolicitudRepartoNotarial solicitud, String estacion,Usuario user) throws DAOException;
	
	
	  /**
     * Crea una instancia de workflow en el sistema para el proceso de Reparto Notarial.
     * @param  turno <code>TurnoEnhanced</code> con sus atributos.
     * @param user <code>Usuario</code> iniciador del proceso.
     * @throws <code>DAOException</code>
     */
    public Message crearWFTurno(Turno turno) throws DAOException;
	
    
    
	/**
	*  Obtiene la estaci�n a la cual va a ser asignado un turno.
	*  @param m Message que contiene informaci�n del turno al cual se va a asociar la estaci�n.
	*  @param idCirculo identificador del turno al cual se va a asociar la estaci�n.
	*  @return El identificador de la estaci�n a la cual debe ser asociado el turno. 
	*/
	public String obtenerEstacionTurno(Hashtable m, String turnoId) throws DAOException;

	
	/**
	*  Guarda la informaci�n de un turno en la tabla de Turno Ejecuci�n.
	*  @param m Message que contiene informaci�n del turno del cual se va a guardar la informaci�n.
	*  @param idTurno identificador del turno del cual se va a guardar la informaci�n.
	*  @param  usuarioSir usuario que realiza el avance o creaci�n del turno 
	*  @return El identificador de la estaci�n a la cual debe ser asociado el turno. 
	*/
	public void guardarInfoTurnoEjecucion (Hashtable m, String estacionAsignada, Turno turno, Usuario usuarioSir) throws DAOException;
	
	
	 /**
     * Avanza un turno a la siguiente fase de acuerdo con los par�metros recibidos.
     * Como el modo bloqueo queda en normal, cualquier usuario en cualquier turno,
     * puede coger el turno.
     * <p>Debe realizar adem�s las siguientes funcionalidades:
     * <p> 1. Setear en CMODOBLOQUEO.NORMAL el atributo modoBloqueo.
     * <p> 2. Avanzar el <code>Turno</code>
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar el avance.
     * @param usuario Usuario que est� realizando el avance.
     * @return true o false dependiendo del resultado de la operaci�n.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     */
     public boolean avanzarTurnoNuevoNormal(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException;


	/**
	 * Metodo que devuelve una lista de turnos dado una fecha y un circulo
	 * @param proceso
	 * @param fase
	 * @param fecha
	 * @param circulo
	 * @return
	 * @throws DAOException
	 */
	public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws DAOException;
         
	/**
     * Avanza un turno a la siguiente fase de acuerdo con los par�metros recibidos.
     * Como el modo bloqueo queda en normal, cualquier usuario en cualquier turno,
     * puede coger el turno.
     * <p>Debe realizar adem�s las siguientes funcionalidades:
     * <p> 1. Setear en CMODOBLOQUEO.NORMAL el atributo modoBloqueo.
     * <p> 2. Avanzar el <code>Turno</code>
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar el avance.
     * @param usuario Usuario que est� realizando el avance.
     * @return true o false dependiendo del resultado de la operaci�n.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     */
     public boolean avanzarTurnoNuevoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException;

     /**
      * Avanza un turno a la siguiente fase de acuerdo con los par�metros recibidos.
      * Como el modo bloqueo queda en normal, cualquier usuario en cualquier turno,
      * puede coger el turno.
      * <p>Debe realizar adem�s las siguientes funcionalidades:
      * <p> 1. Setear en CMODOBLOQUEO.NORMAL el atributo modoBloqueo.
      * <p> 2. Avanzar el <code>Turno</code>
      * @param turno El <code>Turno</code> que va a ser avanzado.
      * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
      * @param parametros Hashtable con los parametros necesarios para realizar el avance.
      * @param usuario Usuario que est� realizando el avance.
      * @return true o false dependiendo del resultado de la operaci�n.
      * @throws <code>DAOException</code>
      * @see gov.sir.core.negocio.modelo.Turno
      * @see gov.sir.core.negocio.modelo.TurnoHistoria
      */
      public boolean avanzarTurnoNuevoPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException;

  	 /**
  	 * <p>AVANZAR ELIMINANDO PUSH
  	 * <p> Realiza las siguientes funcionalidades:
  	 * <p> 1. Eliminar los atributos ingresados con el �ltimo avanzar push.
  	 * (Quitar de turno historia el mayor stack pos).
  	 * <p> 2. Setear en CMODOBLOQUEO.DELEGAR_CUALQUIERA el atributo modoBloqueo. 
  	 * <p> 3. Avanzar el <code>Turno</code>
  	 * @param turno El <code>Turno</code> que va a ser avanzado.
  	 * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
  	 * @param parametros Hashtable con los parametros necesarios para realizar el avance.
  	 * @return true o false dependiendo del resultado de la operaci�n.
  	 * @throws <code>DAOException</code>
  	 * @see gov.sir.core.negocio.modelo.Turno
  	 * @see gov.sir.core.negocio.modelo.TurnoHistoria
  	 */      
      public boolean avanzarTurnoNuevoEliminandoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException;
      
  	 /**
  	 * <p>AVANZAR TURNO CUALQUIERA
  	 * <p> Realiza las siguientes funcionalidades:
  	 * <p> 1. Quita la marca de usuario destino del <code>Turno</code>.
  	 * <p> 2. Setear en CMODOBLOQUEO.DELEGAR_CUALQUIERA el atributo modoBloqueo. 
  	 * <p> 3. Avanzar el <code>Turno</code> 
  	 * @param turno El <code>Turno</code> que va a ser avanzado.
  	 * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
  	 * @param parametros Hashtable con los parametros necesarios para realizar el avance.
  	 * @return true o false dependiendo del resultado de la operaci�n.
  	 * @throws <code>DAOException</code>
  	 * @see gov.sir.core.negocio.modelo.Turno
  	 * @see gov.sir.core.negocio.modelo.TurnoHistoria
  	 */      
      public boolean avanzarTurnoNuevoCualquiera(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException;
      
  	 /**
  	 * Permite actualizar un registro de tipo TurnoEjecucion a partir de la informacion dada.
  	 * @param turnoEjecucion
  	 * @return
  	 * @throws DAOException
  	 */
  	  public boolean actualizarTurnoEjecucion(TurnoEjecucion turnoEjecucion) throws DAOException;
  	  
  	  public boolean getTurnoEjecucionTurnoIndividual(Estacion estacion, Fase fase , Circulo circulo, String idworkflow) throws DAOException;
  	  
	 /**
	 * M�todo que permite actualizar la informaci�n en las tablas del modelo operativo cuando se requiere 
	 * ejecutar la reanotaci�n de un turno de registro de documentos.
	 * @param idTurno
	 * @param notificationId
	 * @param fase
	 * @param resultado
	 * @param estacionAsignada
	 * @param usuarioSir
	 * @return
	 * @throws Throwable
	 */
   	  public boolean reanotarTurnoModeloOperativo(String idTurno, String notificationId, String fase, String resultado, String estacionAsignada, Usuario usuarioSir) throws DAOException;  	  
          
    /**
     * @author: Daniel Forero
     * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS. 
     * 
     * Permite desbloquear todos los folios asociados a un turno. La liberaci�n
     * de folios no tiene en cuenta el estado del turno, su fase actual, o el
     * usuario al que le pertenencen los bloqueos.
     *
     * @param turno El <code>Turno</code> cuyos folios van a ser liberados.
     * @return El n�mero de folios del turno liberados.
     * @throws DAOException Si ocurre alg�n error durante la actualizaci�n de
     * los bloqueos.
     */
    public int desbloquearFolios(Turno turno) throws DAOException;          
}
