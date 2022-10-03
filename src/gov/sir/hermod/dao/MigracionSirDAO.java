package gov.sir.hermod.dao;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * @author ppabon
 * Clase para manejar aspectos particulares de la migración incremental. Dentro de estos aspectos se encuentran 
 * la consulta a las tablas  SIR_MIG_REL_TURNO_FOLIO y SIR_MIG_TRAMITE_TURNO_FOLIO.
 */
public interface MigracionSirDAO {

	/**
	 * Retorna una lista de objetos<code>TurnoFolioMig</code> a partir de uns turno.	 
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * @return List de objetos<code>TurnoFolioMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioByTurno(Turno turno) throws DAOException;
	

	/**
	 * Retorna una lista de objetos<code>SolicitudFolioMig</code> a partir de una solicitud.	 
	 * @param solicitud Solicitud a partir de la cuál se quiere consultar.
	 * @return List de objetos<code>solicitudFolioMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	public List getSolicitudFolioMigBySolicitud(Solicitud solicitud) throws DAOException;	
	
	
	/**
	 * Retorna una lista de objetos<code>TurnoFolioMig</code> a partir de un turno,
	 * sólo se retornan si no existen registros en SIR_OP_SOLICITUD_FOLIO
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * @return List de objetos<code>TurnoFolioMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioNoMigrados(Turno turno) throws DAOException;	
	
	/**
	 * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de un turno,
	 * Estos registros representan los folios que estan en tramite en un turno determinado en el 
	 * sistema folio.
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * @return List de objetos<code>TurnoFolioTramiteMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioTramite(Turno turno) throws DAOException;
	
	/**
	 * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de un folio,
	 * Estos registros representan los folios que estan en tramite en un turno determinado en el 
	 * sistema folio.
	 * @param folio Folio a partir del cuál se quiere consultar.
	 * @return List de objetos<code>TurnoFolioTramiteMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public List getTurnosFolioTramite(Folio folio) throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si la creación de TurnosFolioMig fue creada satisfactoriamente.
	 * @param turno Turno a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @param turnosFolioMig registros que se quieren insertar en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public boolean addTurnosFolioMigToTurno(Turno turno, List turnosFolioMig) throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si la creación de TramiteTurnosFolioMig fue creada satisfactoriamente.
	 * @param tramiteTurnosFolioMig registros que se quieren insertar en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 */
	public boolean addTramiteTurnosFolioMigToTurno(List turnosFolioMig) throws DAOException;	
	
	/**
	 * Retorna un boolean con la información de si la creación de SolicitudFolioMig fue creada satisfactoriamente.
	 * @param solicitud Solicitud a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @param solicitudFolioMig registros que se quieren insertar en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	public boolean addSolicitudFolioMigToTurno(Solicitud solicitud, List solicitudFolioMig) throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si la eliminación de SolicitudFolioMig fue exitosa.
	 * @param solicitud Solicitud a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	public boolean removeSolicitudFolioMig(Solicitud solicitud) throws DAOException;
	
	
	/**
	 * Obtiene la lista de turnos asociados a una estacion, un rol,
	 * un <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code>
	 * si estos tienen datos sin migrar como por ejemplo algún folio.
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
	 * @throws <code>HermodException</code>
	 */
	public List getTurnosSirMig(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_REL_TURNO_FOLIO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioSirMigTurnoFolio(Folio folio) throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_FOLIO_VALIDO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_FOLIO_VALIDO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioValidoSirMig(Folio folio) throws DAOException;
	
	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioSirMigTurnoFolioTramite(Folio folio) throws DAOException;			
	
}
