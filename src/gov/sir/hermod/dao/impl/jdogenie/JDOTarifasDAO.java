/*
 * Clase que permite el manejo de las tarifas asociadas con las
 * liquidaciones de los procesos de la aplicación. 
 */

package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TarifaPk;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.TipoTarifaPk;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;

import com.versant.core.jdo.VersantQuery;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.TarifasDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que permite el manejo de las tarifas asociadas con las
 * liquidaciones de los procesos de la aplicación. 
 * @author  mortiz, dlopez
 */
public class JDOTarifasDAO implements TarifasDAO {
    
    /** 
    * Crea una nueva instacia de <code>JDOLookupDAO</code>
    */ 
    public JDOTarifasDAO() {
    }

	/**
	 * Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @return La <code>Tarifa</code> correspondiente a la 
	 * asociación tipo - código 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */    
	public Tarifa getTarifa(String tipo, String codigo) throws DAOException {
		TarifaEnhanced valor = null;
		PersistenceManager pm = AdministradorPM.getPM();
        
		try {
			pm.currentTransaction().begin();
			valor = this.getTarifa(tipo, codigo, pm);
			pm.currentTransaction().commit();
		} catch (DAOException e) {
			valor = null;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return (Tarifa)valor.toTransferObject();
	}
	
	/**
	 * Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @param pm PersistenceManager
	 * @return La <code>Tarifa</code> correspondiente a la 
	 * asociación tipo - código 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */ 
	protected TarifaEnhanced getTarifa(String tipo, String codigo, PersistenceManager pm) throws DAOException {
		Tarifa tarifa = null;
		Long version = null;
		TarifaEnhanced tarifaObtenida = null;
        
		try {
			VersantQuery query = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query.declareParameters("String tipo, String codigo");
			query.setFilter("idTipo==tipo && idCodigo==codigo");
			query.setResult("max(idVersion)");
			version = (Long) query.execute(tipo, codigo);

			query.closeAll();

			VersantQuery query2 = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query2.declareParameters("String tipo, String codigo, long version");
			query2.setFilter("idTipo==tipo && idCodigo==codigo && idVersion==version");
			Collection col2 = (Collection)query2.execute(tipo, codigo, version);
			for (Iterator iter2 = col2.iterator(); iter2.hasNext();) {
				 tarifaObtenida = (TarifaEnhanced)iter2.next();
			}
			if(tarifaObtenida!=null){
				pm.makeTransient(tarifaObtenida);
			}
			query2.closeAll();

		} catch (JDOObjectNotFoundException e) {
					tarifaObtenida = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return tarifaObtenida;
	}
	
        public boolean isTarifaUVT() throws DAOException {
        String attempt = "";
        int limit = 0;
        boolean rta = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT LKCD_VALOR FROM SIR_OP_LOOKUP_CODES " +
                         " WHERE ID_TIPO = 'SWITCH_TARIFA'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
            }
            
            if(attempt != null && !attempt.isEmpty()){
                limit = Integer.parseInt(attempt);
            }
            
            if(limit != 0){
                rta = true;
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

	/**
	 * Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @return el valor de la <code>Tarifa</code> correspondiente a la 
	 * asociación tipo - código 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */    
	public Tarifa getObjetoTarifa(String tipo, String codigo) throws DAOException {
		Tarifa rta = null;
		TarifaEnhanced valor= null;
		PersistenceManager pm = AdministradorPM.getPM();
        
		try {
			pm.currentTransaction().begin();
			valor = this.getObjetoTarifa(tipo, codigo, pm);
			if(valor!=null){
				pm.makeTransient(valor.getTipoTarifa());
				pm.makeTransient(valor);
			}
			
			pm.currentTransaction().commit();
		}  catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		if(valor!=null){
			rta = (Tarifa)valor.toTransferObject();
		}
		return rta;
	}
	
	/**
	 * Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @param pm PersistenceManager
	 * @return el valor de la <code>Tarifa</code> correspondiente a la 
	 * asociación tipo - código 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */ 
	protected TarifaEnhanced getObjetoTarifa(String tipo, String codigo, PersistenceManager pm) throws DAOException {
		TarifaEnhanced valor = null;
		Long version = null;
        
		try {
			VersantQuery query = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query.declareParameters("String tipo, String codigo");
			query.setFilter("idTipo==tipo && idCodigo==codigo");
			query.setResult("max(idVersion)");
			version = (Long) query.execute(tipo, codigo);

			query.closeAll();

			VersantQuery query2 = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query2.declareParameters("String tipo, String codigo, long version");
			query2.setFilter("idTipo==tipo && idCodigo==codigo && idVersion==version");
			Collection col2 = (Collection)query2.execute(tipo, codigo, version);
			for (Iterator iter2 = col2.iterator(); iter2.hasNext();) {
				valor = (TarifaEnhanced)iter2.next();
			}
			query2.closeAll();

		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			pm.currentTransaction().rollback();
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return valor;
	}
	

	/**
	 * Obtiene el valor de una <code>Tarifa</code> anterior, de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @param fecha Fecha en que fue ingresada la Tarifa
	 * @return el valor de la <code>Tarifa</code> anterior, correspondiente a la 
	 * asociación tipo - código - fecha
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */    
	public double getTarifaAnterior(String tipo, String codigo, Date fecha) throws DAOException {
		double valor = 0;
		PersistenceManager pm = AdministradorPM.getPM();
        
		try {
			pm.currentTransaction().begin();
			valor = this.getTarifaAnterior(tipo, codigo, fecha, pm);
			pm.currentTransaction().commit();
		} catch (DAOException e) {
			valor = 0;
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return valor;
	}

	/**
	 * Obtiene el valor de una <code>Tarifa</code> anterior, de acuerdo al tipo y 
	 * código recibidos como parámetros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @param fecha Fecha en que fue ingresada la Tarifa
	 * @param pm PersistenceManager
	 * @return el valor de la <code>Tarifa</code> anterior, correspondiente a la 
	 * asociación tipo - código - fecha
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */    
	protected double getTarifaAnterior(String tipo, String codigo, Date fecha, PersistenceManager pm) throws DAOException {
		double valor = 0;
        
		try {
			VersantQuery query = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query.declareParameters("String tipo, String codigo, date fecha");
			query.setFilter("idTipo==tipo && idCodigo==codigo && fecha==fecha");
			Collection col = (Collection)query.execute(tipo, codigo, fecha);
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				TarifaEnhanced t = (TarifaEnhanced)iter.next();
				valor = t.getValor();
			}
			query.closeAll();

		} catch (JDOObjectNotFoundException e) {
					valor = 0;
		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			pm.currentTransaction().rollback();
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return valor;
	}

	/**
	 * Obtiene una lista de objetos de tipo <code>Tarifa</code> del tipo 
	 * ingresado como parámetro, de acuerdo con los datos existentes en la
	 * tabla de Tarifas
	 * @param tipoTarifa tipo de la tarifa del cual se quiere obtener el listado
	 * de tarifas existentes. 
	 * @return una lista de objetos <code>Tarifa</code> asociados con el tipo
	 * dado.
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @throws <code>DAOException</code>
	 */ 
	public List getTarifas(TipoTarifa tipoTarifa) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
        
		try {
			pm.currentTransaction().begin(); 
			Query q = pm.newQuery(TarifaEnhanced.class);
			q.setFilter("idTipo=='"+tipoTarifa.getIdTipo()+"'");
	
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
			while ( it.hasNext() ) {
				TarifaEnhanced obj = (TarifaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			pm.currentTransaction().commit();
			q.close(results);
		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			pm.currentTransaction().rollback();
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
			lista = TransferUtils.makeTransferAll(lista);
		}
		return lista;
	}

	
	
	
	/**
	 * Agrega un tipo de  <code>Tarifa</code> a la tabla de Tarifas.
	 * <p> La versión asignada a la tarifa es la 1.
	 * <p> El método lanza una excepción si ya existe un tipo de tarifa con
	 * el identificador del objeto pasado como parámetro. 
	 * @param tarifa Objeto <code>Tarifa</code> con todos atributos.
	 * @return identificador del <code>TipoTarifa</code> generado.
	 * @throws <code>DAOExeption</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 */ 
	public TipoTarifaPk addTipoTarifa(Tarifa tarifa) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TarifaEnhanced tar = TarifaEnhanced.enhance(tarifa);
		TipoTarifaEnhancedPk rta = null;

		try {
			TipoTarifaEnhancedPk tarId = new TipoTarifaEnhancedPk();
			tarId.idTipo = tar.getIdTipo();
			TipoTarifaEnhanced tarr = this.getTipoTarifaById(tarId, pm);
			if (tarr!= null){
				throw new DAOException(
				"Ya existe un Tipo de Tarifa con el identificador: " + tarr.getIdTipo());
			}

			Date fecha = new Date();
			TipoTarifaEnhanced tipoTar = new TipoTarifaEnhanced();
			tipoTar.setFechaCreacion(fecha);
			tipoTar.setIdTipo(tar.getIdTipo());
			TarifaEnhanced t = new TarifaEnhanced();
			t.setFechaCreacion(fecha);
			t.setFechaInicial(fecha);
			t.setIdCodigo(tarifa.getIdCodigo());
			t.setIdTipo(tarifa.getIdTipo());
			t.setIdVersion(1);
			t.setValor(tarifa.getValor());

			pm.currentTransaction().begin();
			pm.makePersistent(t);
			pm.makePersistent(tipoTar);
			pm.currentTransaction().commit();
			rta = (TipoTarifaEnhancedPk) pm.getObjectId(tipoTar);

		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			pm.currentTransaction().rollback();
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return rta.getTipoTarifaID();
	}



	/**
	 * Agrega una <code>Tarifa</code> a la configuración del sistema. 
	 * <p> La versión asignada a la tarifa es la 1.
	 * <p> El método lanza una excepción si no existe un tipo de tarifa con
	 * el identificador del objeto pasado como parámetro. 
	 * @param tarifa Objeto <code>Tarifa</code> con todos atributos.
	 * @return identificador de la <code>Tarifa</code> generada.
	 * @throws <code>DAOExeption</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */ 
	public TarifaPk addTarifa(Tarifa tarifa) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TarifaEnhanced tar = TarifaEnhanced.enhance(tarifa);
		TarifaEnhancedPk rta = null;
		Long version = null;

		try {
			pm.currentTransaction().begin();
			VersantQuery query = (VersantQuery)pm.newQuery(TarifaEnhanced.class);
			query.declareParameters("String tipo, String codigo");
			query.setFilter("idTipo==tipo && idCodigo==codigo");
			query.setResult("max(idVersion)");
			version = (Long) query.execute(tar.getIdTipo(), tar.getIdCodigo());
			/*Collection col = (Collection)query.execute(tar.getIdTipo(), tar.getIdCodigo());
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				TarifaEnhanced t = (TarifaEnhanced)iter.next();
				version = new Long(t.getIdVersion());
			}*/
			if (version == null){
				throw new DAOException(
				"No existe un Tipo de Tarifa con el identificador: " + tar.getIdTipo());
			}
			query.closeAll();
			Date fecha = new Date();
			TarifaEnhanced t = new TarifaEnhanced();
			t.setFechaCreacion(fecha);
			t.setFechaInicial(fecha);
			t.setIdCodigo(tar.getIdCodigo());
			t.setIdTipo(tar.getIdTipo());
			t.setIdVersion(version.longValue()+1);
			t.setValor(tar.getValor());
			pm.makePersistent(t);
			pm.currentTransaction().commit();
			rta = (TarifaEnhancedPk) pm.getObjectId(t);
			
		} catch (JDOException e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			pm.currentTransaction().rollback();
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOTarifasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}		
		return rta.getTarifaID();
	}
	
	/**
	 * Obtiene una tarifa dado su identificador, método utilizado para transacciones
	 * @param tarID identificador de la tarifa.
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>TarifaEnhanced</code> con sus atributos
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 */
	protected TarifaEnhanced getTarifaById(TarifaEnhancedPk tarID,
		PersistenceManager pm) throws DAOException {
		TarifaEnhanced rta = null;

		if (tarID.idCodigo != null && tarID.idTipo != null) {
			try {
				rta = (TarifaEnhanced) pm.getObjectById(tarID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOTarifasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	/**
	 * Obtiene un tipo de  tarifa dado su identificador, método utilizado
	 * para transacciones
	 * @param tarID identificador del tipo de tarifa.
	 * @param pm PersistenceManager de la transaccion
	 * @return Objeto <code>TipoTarifa</code> con sus atributos
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 * @throws <code>DAOException</code>
	 */
	protected TipoTarifaEnhanced getTipoTarifaById(TipoTarifaEnhancedPk tarID,
		PersistenceManager pm) throws DAOException {
		TipoTarifaEnhanced rta = null;

		if (tarID.idTipo != null) {
			try {
				rta = (TipoTarifaEnhanced) pm.getObjectById(tarID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOTarifasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOTarifasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	 * Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en
	 * la Base de Datos. 
	 * <p>
	 * Se establece como criterio de ordenamiento el id del tipo de tarifa. 
	 * @return una lista de objetos <code>TipoTarifa</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 */ 
	public List getTiposTarifas() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		         
		try{
			pm.currentTransaction().begin();	
			Query q = pm.newQuery(TipoTarifaEnhanced.class);
			q.setOrdering("idTipo ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
    
			while ( it.hasNext() ){
				TipoTarifaEnhanced obj = (TipoTarifaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
				
			pm.currentTransaction().commit();    
			q.close(results);

		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	
	

	/**
	 * Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en
	 * la Base de Datos que son configurables por círculo 
	 * <p>
	 * Se establece como criterio de ordenamiento el id del tipo de tarifa. 
	 * @return una lista de objetos <code>TipoTarifa</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 */ 
	public List getTiposTarifasConfiguradasPorCirculo() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		         
		try{
			pm.currentTransaction().begin();	
			Query q = pm.newQuery(TipoTarifaEnhanced.class);
			q.setFilter("configurablePorCirculo==true");
			q.setOrdering("idTipo ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
    
			while ( it.hasNext() ){
				TipoTarifaEnhanced obj = (TipoTarifaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
				
			pm.currentTransaction().commit();    
			q.close(results);

		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOTarifasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
}