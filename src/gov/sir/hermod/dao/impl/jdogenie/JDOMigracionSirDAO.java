package gov.sir.hermod.dao.impl.jdogenie;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;


import com.versant.core.jdo.VersantPersistenceManager;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolioMig;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolioMig;
import gov.sir.core.negocio.modelo.TurnoFolioTramiteMig;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioMigEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoFolioMigEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoFolioTramiteMigEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.MigracionSirDAO;


/**
 * @author ppabon
 * Clase para manejar aspectos particulares de la migración incremental. Dentro de estos aspectos se encuentran 
 * la consulta a las tablas  SIR_MIG_REL_TURNO_FOLIO y SIR_MIG_TRAMITE_TURNO_FOLIO.
 */
public class JDOMigracionSirDAO implements MigracionSirDAO{
	
	/** 
	* Crea una nueva instancia de <code>JDOTurnosDAO</code>
	* <p>
	* Método Constructor.  
	*/
	public JDOMigracionSirDAO() {
	}
	

	/**
	 * Retorna una lista de objetos<code>TurnoFolio</code> a partir de una turno.
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * @return List de objetos<code>TurnoFolio</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioByTurno(Turno turno) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select a.ID_TURNO , a.ID_FOLIO , a.ID_PROCESO , a.ID_CIRCULO, a.ANULADO , a.CREADO_SIR");
			sqlStatement.append(" from SIR_MIG_REL_TURNO_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_PROCESO = :1 and");
			sqlStatement.append(" upper(a.ID_TURNO) = :2 and");
			sqlStatement.append(" a.ANULADO = 0 and");
			sqlStatement.append(" a.ID_CIRCULO = :3 ");
			sqlStatement.append(" and a.ID_FOLIO not in ");
			sqlStatement.append("			  (			   ");
			sqlStatement.append("			  select sf.ID_MATRICULA ");
			sqlStatement.append("			  from sir_op_turno t, sir_op_solicitud_folio sf ");
			sqlStatement.append("			  where");
			sqlStatement.append("			  t.ANIO = :4 and ");
			sqlStatement.append("			  t.ID_CIRCULO = :5 and ");
			sqlStatement.append("			  t.ID_PROCESO = :6 and ");
			sqlStatement.append("			  t.ID_TURNO = :7 and");
			sqlStatement.append("			  t.ID_SOLICITUD = sf.ID_SOLICITUD");
			sqlStatement.append("			  ) ");

			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idTurnoFolio = "";
			if(turno.getIdProceso() == new Long(CProceso.PROCESO_CORRECCION).longValue()){
				idTurnoFolio = "C" + turno.getAnio() + "-" + turno.getIdTurno();
			}else{
				idTurnoFolio = turno.getAnio() + "-" + turno.getIdTurno();
			}
			
			ps.setLong(1, turno.getIdProceso());
			ps.setString(2, idTurnoFolio);
			ps.setString(3, turno.getIdCirculo());
			ps.setString(4, turno.getAnio());
			ps.setString(5, turno.getIdCirculo());
			ps.setLong(6, turno.getIdProceso());
			ps.setString(7, turno.getIdTurno());

			rs = ps.executeQuery();

			while (rs.next()) {
				TurnoFolioMig turnoFolioMig = new TurnoFolioMig();
				turnoFolioMig.setIdTurno(rs.getString(1));
				turnoFolioMig.setIdFolio(rs.getString(2));
				turnoFolioMig.setIdProceso(rs.getLong(3));
				turnoFolioMig.setIdCirculo(rs.getString(4));
				turnoFolioMig.setAnulado(rs.getBoolean(5));
				turnoFolioMig.setCreadoSir(rs.getBoolean(6));
				lista.add(turnoFolioMig);
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}
	
	/**
	 * Retorna una lista de objetos<code>TurnoFolio</code> a partir de un
	 * turno, sólo se retornan si no existen registros en SIR_OP_SOLICITUD_FOLIO
	 * 
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * 
	 * @return List de objetos<code>TurnoFolio</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioNoMigrados(Turno turno) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select a.ID_TURNO , a.ID_FOLIO , a.ID_PROCESO , a.ID_CIRCULO , a.ANULADO , a.CREADO_SIR ");
			sqlStatement.append(" from SIR_MIG_REL_TURNO_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_PROCESO = :1 and ");
			sqlStatement.append(" upper(a.ID_TURNO) = :2 and ");
			sqlStatement.append(" a.ANULADO = 0 and ");
			sqlStatement.append(" a.ID_CIRCULO = :3 ");
			sqlStatement.append(" and a.ID_FOLIO not in ");
			sqlStatement.append("			  ( ");
			sqlStatement.append("			  select sf.ID_MATRICULA ");
			sqlStatement.append("			  from sir_op_turno t, sir_op_solicitud_folio sf ");
			sqlStatement.append("			  where ");
			sqlStatement.append("			  t.ANIO = :4 and ");
			sqlStatement.append("			  t.ID_CIRCULO = :5 and ");
			sqlStatement.append("			  t.ID_PROCESO = :6 and ");
			sqlStatement.append("			  t.ID_TURNO = :7 and ");
			sqlStatement.append("			  t.ID_SOLICITUD = sf.ID_SOLICITUD ");
			sqlStatement.append("			  ) ");

			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idTurnoFolio = "";
			if(turno.getIdProceso() == new Long(CProceso.PROCESO_CORRECCION).longValue()){
				idTurnoFolio = "C" + turno.getAnio() + "-" + turno.getIdTurno();
			}else{
				idTurnoFolio = turno.getAnio() + "-" + turno.getIdTurno();
			}
			
			ps.setLong(1, turno.getIdProceso());
			ps.setString(2, idTurnoFolio);
			ps.setString(3, turno.getIdCirculo());
			ps.setString(4, turno.getAnio());
			ps.setString(5, turno.getIdCirculo());
			ps.setLong(6, turno.getIdProceso());
			ps.setString(7, turno.getIdTurno());

			rs = ps.executeQuery();

			while (rs.next()) {
				TurnoFolioMig turnoFolioMig = new TurnoFolioMig();
				turnoFolioMig.setIdTurno(rs.getString(1));
				turnoFolioMig.setIdFolio(rs.getString(2));
				turnoFolioMig.setIdProceso(rs.getLong(3));
				turnoFolioMig.setIdCirculo(rs.getString(4));
				turnoFolioMig.setAnulado(rs.getBoolean(5));
				turnoFolioMig.setCreadoSir(rs.getBoolean(6));
				lista.add(turnoFolioMig);
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

                if(connection != null){
                    connection.close();
                }

				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}	
	
	
	/**
	 * Retorna una lista de objetos<code>SolicitudFolioMig</code> a partir de una
	 * solicitud, sólo se retornan si no existen registros en SIR_OP_SOLICITUD_FOLIO
	 * 
	 * @param solicitud Solicitud a partir del cuál se quiere consultar.
	 * 
	 * @return List de objetos<code>SolicitudFolioMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitd
	 */
	public List getSolicitudFolioMigBySolicitud(Solicitud solicitud) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select a.ID_SOLICITUD , a.ID_FOLIO , a.ID_PROCESO , a.ID_CIRCULO , a.ANULADO , a.CREADO_SIR ");
			sqlStatement.append(" from SIR_MIG_REL_SOLICITUD_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_PROCESO = :1 and ");
			sqlStatement.append(" upper(a.ID_SOLICITUD) = :2 and ");
			sqlStatement.append(" a.ANULADO = 0 and ");
			sqlStatement.append(" a.ID_CIRCULO = :3 ");
			sqlStatement.append(" and a.ID_FOLIO not in ");
			sqlStatement.append("			  ( ");
			sqlStatement.append("			  select sf.ID_MATRICULA ");
			sqlStatement.append("			  from  sir_op_solicitud_folio sf ");
			sqlStatement.append("			  where ");
			sqlStatement.append("			  sf.ID_SOLICITUD = :4  ");
			sqlStatement.append("			  ) ");

			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			ps.setLong(1, solicitud.getProceso().getIdProceso());
			ps.setString(2, solicitud.getIdSolicitud());
			ps.setString(3, solicitud.getCirculo().getIdCirculo());
			ps.setString(4, solicitud.getIdSolicitud());

			rs = ps.executeQuery();

			while (rs.next()) {
				SolicitudFolioMig solicitudFolioMig = new SolicitudFolioMig();
				solicitudFolioMig.setIdSolicitud(rs.getString(1));
				solicitudFolioMig.setIdFolio(rs.getString(2));
				solicitudFolioMig.setIdProceso(rs.getLong(3));
				solicitudFolioMig.setIdCirculo(rs.getString(4));
				solicitudFolioMig.setAnulado(rs.getBoolean(5));
				solicitudFolioMig.setCreadoSir(rs.getBoolean(6));
				lista.add(solicitudFolioMig);
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}	
	
	
	/**
	 * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de un turno,
	 * Estos registros representan los folios que estan en tramite en un turno determinado en el 
	 * sistema folio.
	 * 
	 * @param turno Turno a partir del cuál se quiere consultar.
	 * 
	 * @return List de objetos<code>TurnoFolioTramiteMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getTurnosFolioTramite(Turno turno) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select a.ID_TURNO , a.ID_FOLIO , a.ID_PROCESO , a.ID_CIRCULO , a.SEC_PROCESO , a.ANULADO ");  
			sqlStatement.append(" from  SIR_MIG_TRAMITE_TURNO_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_CIRCULO = :1 and ");
			sqlStatement.append(" a.ID_PROCESO = :2 and "); 
			sqlStatement.append(" upper(a.ID_TURNO) = :3 and "); 
			sqlStatement.append(" a.ANULADO = 0 and ");
			sqlStatement.append(" a.SEC_PROCESO = ");
			sqlStatement.append(" 		 ( ");			
			sqlStatement.append(" 			select max(b.sec_proceso) from  SIR_MIG_TRAMITE_TURNO_FOLIO b ");
			sqlStatement.append(" 			where  ");
			sqlStatement.append(" 			b.ID_CIRCULO = :4 and ");
			sqlStatement.append(" 			b.ID_PROCESO = :5 and ");
			sqlStatement.append(" 			upper(b.ID_TURNO) = :6 and ");
			sqlStatement.append(" 			b.ANULADO = 0 ");
			sqlStatement.append("		 ) ");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idTurnoFolio = "";
			if(turno.getIdProceso() == new Long(CProceso.PROCESO_CORRECCION).longValue()){
				idTurnoFolio = "C" + turno.getAnio() + "-" + turno.getIdTurno();
			}else{
				idTurnoFolio = turno.getAnio() + "-" + turno.getIdTurno();
			}
			
			ps.setString(1, turno.getIdCirculo());
			ps.setLong(2, turno.getIdProceso());
			ps.setString(3, idTurnoFolio);
			ps.setString(4, turno.getIdCirculo());
			ps.setLong(5, turno.getIdProceso());
			ps.setString(6, idTurnoFolio);

			rs = ps.executeQuery();

			while (rs.next()) {
				TurnoFolioTramiteMig turnoFolioMig = new TurnoFolioTramiteMig();
				turnoFolioMig.setIdTurno(rs.getString(1));
				turnoFolioMig.setIdFolio(rs.getLong(2));
				turnoFolioMig.setIdProceso(rs.getLong(3));
				turnoFolioMig.setIdCirculo(rs.getString(4));
				turnoFolioMig.setSecProceso(rs.getLong(5));
				turnoFolioMig.setAnulado(rs.getBoolean(6));
				lista.add(turnoFolioMig);
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}	

	/**
	 * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de un folio,
	 * Estos registros representan los folios que estan en tramite en un turno determinado en el 
	 * sistema folio.
	 * 
	 * @param folio Folio a partir del cuál se quiere consultar.
	 * 
	 * @return List de objetos<code>TurnoFolioTramiteMig</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public List getTurnosFolioTramite(Folio folio) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select a.ID_TURNO , a.ID_FOLIO , a.ID_PROCESO , a.ID_CIRCULO , a.SEC_PROCESO , a.ANULADO  ");   
			sqlStatement.append(" from  SIR_MIG_TRAMITE_TURNO_FOLIO a  ");
			sqlStatement.append(" where  ");
			sqlStatement.append(" a.ID_FOLIO =:1 and ");
			sqlStatement.append(" a.ID_CIRCULO =:2 and ");
			sqlStatement.append(" a.ANULADO = 0 and ");
			sqlStatement.append(" a.SEC_PROCESO = ");			
			sqlStatement.append(" 		 (  ");
			sqlStatement.append("			  select max(b.sec_proceso) from  SIR_MIG_TRAMITE_TURNO_FOLIO  b ");   
			sqlStatement.append("			  where ");
			sqlStatement.append("			  b.ID_FOLIO=:3 and ");
			sqlStatement.append("			  b.ID_CIRCULO = :4	and");
			sqlStatement.append("			  b.ANULADO = 0 ");
			sqlStatement.append("			  )	");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idCirculo = "";
			String idFolio ="";
			
			idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			ps.setLong(1, new Long(idFolio).longValue());
			ps.setString(2, idCirculo);
			ps.setLong(3, new Long(idFolio).longValue());
			ps.setString(4, idCirculo);

			rs = ps.executeQuery();

			while (rs.next()) {
				TurnoFolioTramiteMig turnoFolioMig = new TurnoFolioTramiteMig();
				turnoFolioMig.setIdTurno(rs.getString(1));
				turnoFolioMig.setIdFolio(rs.getLong(2));
				turnoFolioMig.setIdProceso(rs.getLong(3));
				turnoFolioMig.setIdCirculo(rs.getString(4));
				turnoFolioMig.setSecProceso(rs.getLong(5));
				turnoFolioMig.setAnulado(rs.getBoolean(6));
				lista.add(turnoFolioMig);
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}	

	/**
	 * Retorna un boolean con la información de si la creación de TurnosFolioMig fue creada satisfactoriamente.
	 * @param turno Turno a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @param turnosFolioMig registros que se quieren insertar en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 */	
	public boolean addTurnosFolioMigToTurno(Turno turno, List turnosFolioMig) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			pm.currentTransaction().begin();

			if (turnosFolioMig == null) {
				throw new DAOException("No es posible agregar los folios relacionados del turno anterior.");
			}
			if (turno == null) {
				throw new DAOException("No es posible agregar los folios relacionados del turno anterior, el turno no es válido");
			}			
			String turnoMig = turno.getAnio()+ "-" + turno.getIdTurno();
			
			if(turno.getIdProceso()==new Long (CProceso.PROCESO_CORRECCIONES).longValue()){
				turnoMig  = "C"+turnoMig;				
			}

			Iterator it = turnosFolioMig.iterator();
			while (it.hasNext()) {
				TurnoFolioMig tfm = (TurnoFolioMig) it.next();

				TurnoFolioMigEnhanced tfmEnh = new TurnoFolioMigEnhanced();

				tfmEnh.setIdFolio(tfm.getIdFolio());
				tfmEnh.setIdProceso(tfm.getIdProceso());
				tfmEnh.setIdTurno(turnoMig);
				tfmEnh.setIdCirculo(turno.getIdCirculo());
				tfmEnh.setAnulado(false);
				tfmEnh.setCreadoSir(true);
				pm.makePersistent(tfmEnh);

			}

			pm.currentTransaction().commit();

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			Log.getInstance().error(JDOTurnosDAO.class,e);
			throw e;

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}	

	
	/**
	 * Retorna un boolean con la información de si la creación de TramiteTurnosFolioMig fue creada satisfactoriamente.
	 * @param tramiteTurnosFolioMig registros que se quieren insertar en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 */	
	public boolean addTramiteTurnosFolioMigToTurno(List turnosFolioMig) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			pm.currentTransaction().begin();

			if (turnosFolioMig == null) {
				throw new DAOException("No es posible agregar los folios relacionados del turno anterior.");
			}

			Iterator it = turnosFolioMig.iterator();
			while (it.hasNext()) {
				TurnoFolioTramiteMig tfm = (TurnoFolioTramiteMig) it.next();
				
				/**
				 * se consulta para ver si el bloqueo ya existe
				 */
				Query query = pm.newQuery(TurnoFolioTramiteMigEnhanced.class);
				
				String idCir = tfm.getIdCirculo();
				Long idFolio = new Long(tfm.getIdFolio());
				String idTurno = tfm.getIdTurno();
				Long idProceso = new Long(tfm.getIdProceso());
				
				
				query.declareParameters("String idCir, long idFolio, String idTurno, long idProceso");
				query.setFilter("this.idCirculo == idCir && \n"+
				        "this.idFolio == idFolio && \n"+
				        "this.idTurno == idTurno && \n"+
				        "this.idProceso == idProceso");
				Collection col = (Collection)query.executeWithArray(new Object[]{idCir, idFolio, idTurno, idProceso});
				
				/**
				 * El registro existe, se debe actualizar
				 * Si no existe, se debe insertar
				 */
				if (col != null && !col.isEmpty())
				{
					Iterator tfmItera = col.iterator();
					TurnoFolioTramiteMigEnhanced tfmEnh = (TurnoFolioTramiteMigEnhanced)tfmItera.next();
					tfmEnh.setAnulado(tfm.isAnulado());
					
				}
				else {

					TurnoFolioTramiteMigEnhanced tfmEnh = new TurnoFolioTramiteMigEnhanced();
	
					tfmEnh.setIdTurno(tfm.getIdTurno());
					tfmEnh.setIdCirculo(tfm.getIdCirculo());
					tfmEnh.setIdProceso(tfm.getIdProceso());
					tfmEnh.setIdFolio(tfm.getIdFolio());
					tfmEnh.setSecProceso(tfm.getSecProceso());
					tfmEnh.setAnulado(tfm.isAnulado());
					pm.makePersistent(tfmEnh);
				}

			}

			pm.currentTransaction().commit();

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			Log.getInstance().error(JDOTurnosDAO.class,e);
			throw e;

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}	
	
	
	/**
	 * Retorna un boolean con la información de si la creación de SolicitudFolioMig fue creada satisfactoriamente.
	 * @param solicitud Solicitud a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @param solicitudFolioMig registros que se quieren insertar en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */	
	public boolean addSolicitudFolioMigToTurno(Solicitud solicitud, List solicitudFolioMig) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			pm.currentTransaction().begin();

			if (solicitudFolioMig == null) {
				throw new DAOException("No es posible agregar los folios relacionados de la solicitud anterior.");
			}
			if (solicitud == null) {
				throw new DAOException("No es posible agregar los folios relacionados del turno anterior, la solicitud no es válida");
			}			

			Iterator it = solicitudFolioMig.iterator();
			while (it.hasNext()) {
				SolicitudFolioMig sfm = (SolicitudFolioMig) it.next();

				SolicitudFolioMigEnhanced sfmEnh = new SolicitudFolioMigEnhanced();

				sfmEnh = new SolicitudFolioMigEnhanced();
				sfmEnh.setIdFolio(sfm.getIdFolio());
				sfmEnh.setIdProceso(sfm.getIdProceso());
				sfmEnh.setIdSolicitud(sfm.getIdSolicitud());
				sfmEnh.setIdCirculo(solicitud.getCirculo().getIdCirculo());
				sfmEnh.setAnulado(false);
				sfmEnh.setCreadoSir(true);
				pm.makePersistent(sfmEnh);

			}

			pm.currentTransaction().commit();

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			Log.getInstance().error(JDOTurnosDAO.class,e);
			throw e;

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}	
	
	/**
	 * Retorna un boolean con la información de si la eliminación de SolicitudFolioMig fue exitosa.
	 * @param solicitud Solicitud a partir del cuál se quiere crear registros en la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
	 * @return boolean con información de si la operación fue exitosa
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */	
	public boolean removeSolicitudFolioMig(Solicitud solicitud) throws DAOException {
		
		
		if (solicitud == null) {
			throw new DAOException("No es posible agregar los folios relacionados del turno anterior, la solicitud no es válida");
		}
		
		List solicitudesFolioMig = this.getSolicitudFolioMigBySolicitud(solicitud);
		if(solicitudesFolioMig==null || solicitudesFolioMig.size()==0){
			return true;			
		}
		
		
		//SI LA SOLICITUD TIENE REGISTROS ASOCIADOS SE PROCEDE A ELIMINARLOS.
		
		PersistenceManager pm = AdministradorPM.getPM();
		try {
			
			pm.currentTransaction().begin();

			Iterator it = solicitudesFolioMig.iterator();
			while (it.hasNext()) {
				SolicitudFolioMig sfm = (SolicitudFolioMig) it.next();

				SolicitudFolioMigEnhancedPk idSFM = new SolicitudFolioMigEnhancedPk();
				idSFM.idCirculo = sfm.getIdCirculo();
				idSFM.idFolio = sfm.getIdFolio();
				idSFM.idProceso = sfm.getIdProceso();
				idSFM.idSolicitud = sfm.getIdSolicitud();
				
				SolicitudFolioMigEnhanced sfmEnh = (SolicitudFolioMigEnhanced)pm.getObjectById( idSFM , true);
				pm.deletePersistent(sfmEnh);

			}

			pm.currentTransaction().commit();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}	
	
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
    public List getTurnosSirMig(Estacion estacion, Rol rol, Usuario usuario,
			Proceso proceso, Fase fase, Circulo circulo) throws DAOException {

    	PersistenceManager pm = AdministradorPM.getPM();
		List lista = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append("   SELECT a.TRNO_ID_WORKFLOW ");
			sqlStatement.append("   FROM SIR_OP_TURNO a ");
			//sqlStatement.append("   WHERE a.ID_CIRCULO =:1 ");
			//sqlStatement.append("   AND EXISTS (SELECT 1 ");
			sqlStatement.append("   WHERE EXISTS (SELECT 1 ");
			sqlStatement.append("               FROM SIR_OP_TURNO_EJECUCION b ");
			sqlStatement.append("               WHERE b.TREJ_ITEM_KEY = a.TRNO_ID_WORKFLOW ");
			sqlStatement.append("               AND b.TREJ_FASE =:1  ");
			sqlStatement.append("               AND b.TREJ_ESTACION =:2) ");				  
			sqlStatement.append("   AND EXISTS (SELECT 1 ");
			sqlStatement.append("               FROM SIR_MIG_REL_TURNO_FOLIO c ");
			sqlStatement.append("               WHERE c.ID_PROCESO = a.ID_PROCESO ");
			sqlStatement.append("               AND c.ID_CIRCULO = a.ID_CIRCULO ");
			sqlStatement.append("               AND c.ID_TURNO =  DECODE(a.id_proceso, 3, 'C'||a.anio||'-'||a.id_turno , a.anio||'-'||a.id_turno) ");
			sqlStatement.append("               AND NOT (EXISTS (SELECT 1 ");
			sqlStatement.append(" 				                       FROM SIR_NE_FOLIO d ");
			sqlStatement.append("                  			         WHERE d.ID_MATRICULA = c.ID_FOLIO   ");
			sqlStatement.append(" 				 				               ) ");
			sqlStatement.append(" 				 		           )  ");
			sqlStatement.append(" 				       ) ");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			/*ps.setString(1, circulo.getIdCirculo());
			ps.setString(2, fase.getID());
			ps.setString(3, estacion.getEstacionId());*/
			
			ps.setString(1, fase.getID());
			ps.setString(2, estacion.getEstacionId());

			rs = ps.executeQuery();

			while (rs.next()) {
				lista.add(rs.getString(1));
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return lista;
	}
	

	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_REL_TURNO_FOLIO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioSirMigTurnoFolio(Folio folio) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = false;
		Connection connection = null;
            PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select count(a.ID_FOLIO) ");
			sqlStatement.append(" from SIR_MIG_REL_TURNO_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_FOLIO =:1 and ");
			sqlStatement.append(" a.ID_CIRCULO = :2 and ");
			sqlStatement.append(" a.ANULADO = 0 ");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idCirculo = "";
			String idFolio ="";
			
			idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			ps.setString(1, folio.getIdMatricula());
			ps.setString(2, idCirculo);

			rs = ps.executeQuery();

			if (rs.next()) {
				long cantidad = rs.getLong(1);
				if(cantidad>0){
					rta = true;
				}
			}
			jdoPM.currentTransaction().commit();


		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_REL_TURNO_FOLIO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_REL_TURNO_FOLIO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioValidoSirMig(Folio folio) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = false;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select count(a.ID_FOLIO) ");
			sqlStatement.append(" from SIR_MIG_FOLIO_VALIDO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_FOLIO =:1 and ");
			sqlStatement.append(" a.ID_CIRCULO = :2 and ");
			sqlStatement.append(" a.ANULADO = 0 ");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idCirculo = "";
			String idFolio ="";
			
			idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			ps.setString(1, folio.getIdMatricula());
			ps.setString(2, idCirculo);

			rs = ps.executeQuery();

			if (rs.next()) {
				long cantidad = rs.getLong(1);
				if(cantidad>0){
					rta = true;
				}
			}
			jdoPM.currentTransaction().commit();


		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	
	/**
	 * Retorna un boolean con la información de si existe o no registros en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO
	 * a partir de un Folio. 
	 * @param folio Folio a partir del cuál se quiere consultar registros en la tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
	 * @return boolean con información de si existe o no registros
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public boolean isFolioSirMigTurnoFolioTramite(Folio folio) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = false;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" select count(a.ID_FOLIO) ");   
			sqlStatement.append(" from SIR_MIG_TRAMITE_TURNO_FOLIO a ");
			sqlStatement.append(" where ");
			sqlStatement.append(" a.ID_FOLIO =:1 and ");
			sqlStatement.append(" a.ID_CIRCULO =:2 and ");
			sqlStatement.append(" a.ANULADO = 0  ");
			
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			String idCirculo = "";
			String idFolio ="";
			
			idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			ps.setLong(1, new Long(idFolio).longValue());
			ps.setString(2, idCirculo);

			rs = ps.executeQuery();

			if (rs.next()) {
				long cantidad = rs.getLong(1);
				if(cantidad>0){
					rta = true;
				}
			}
			jdoPM.currentTransaction().commit();


		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDOTurnosDAO.class,e);
				Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
    
    
}