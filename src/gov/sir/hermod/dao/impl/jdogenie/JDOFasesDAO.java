/*
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * Created on 12 de julio de 2004, 9:10
 */

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
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.FasesDAO;

import org.auriga.core.modelo.transferObjects.Rol;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.InicioProcesosEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;

/**
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * @author  mrios, mortiz, dlopez
 */
public class JDOFasesDAO implements FasesDAO {
    
	/** Crea una nueva instacia de JDOFasesDAO 
	 */
    public JDOFasesDAO() {
    }
    
    /**
     * Obtiene la lista de fases (actividades) asociadas a un proceso y una estacion
     * @param rol objeto Rol del cual se desean obtener las fases asociadas.
     * @param proceso objeto <code>Proceso</code> del cual se desean obtener las fases asociadas.
     * @return la lista de fases asociadas al proceso y al rol.
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>DAOException</code>
     */  
    public List getFases(Rol rol, Proceso proceso) throws DAOException {
        ArrayList lista = new ArrayList();
        PersistenceManager pm = null;
        String id_proceso = String.valueOf(proceso.getIdProceso());

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Query q = pm.newQuery(Procesos_V.class);
            q.setFilter("rol=='" + rol.getRolId() + "' && id_proceso=='" + id_proceso + "'");

            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                Procesos_V obj = (Procesos_V) it.next();
                pm.makeTransient(obj);

                lista.add(new Fase(obj.getId_fase(), obj.getNombre_fase(), obj.getDesc_fase(), obj.getRol()));
            }
            pm.currentTransaction().commit();
            q.close(results);
        } catch (JDOException e) {
            Log.getInstance().error(JDOFasesDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOFasesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOFasesDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOFasesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return lista;
    }
    
	/**
	 * Obtiene la lista de fases (actividades) a las que puede avanzarse un turno
	 * recibido como parámetro  
	 * @param turno objeto <code>Turno</code> 
	 * @return la lista de fases sigiuientes a la fase actual del turno recibido.
	 * @see gov.sir.core.negocio.modelo.Fase
	 * @throws <code>DAOException</code>
	 */  
    public List getFasesSiguientes(Turno turno) throws DAOException {
        List lista = new ArrayList();
        HermodProperties hp = HermodProperties.getInstancia();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;
        
        String iType = hp.getProperty(JDOGenieKeys.ORACLE_WORKFLOW);
        String CONSULTA = JDOHermodProperties.getInstancia().getProperty(JDOGenieKeys.GET_LISTA_FASES_SIGUIENTES);

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(CONSULTA);
            ps.setString(1, turno.getIdTurno());
            ps.setString(2, String.valueOf(turno.getIdProceso()));
            
            rs = ps.executeQuery();
                
            while ( rs.next() ) {
                FaseEnhanced fase = new FaseEnhanced(rs.getString(1), rs.getString(2), "", "");
                lista.add(fase);
            }
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
            Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
            Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);                                  
        } finally {
            try {
                if ( rs != null ) {
                    rs.close();
                }
            
                if ( ps != null ) {
                    ps.close();
                }

                if(connection != null){
                    connection.close();
                }
            }
            catch (SQLException e) {
                Log.getInstance().error(JDOFasesDAO.class,e);
                Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } 
            
            if ( jdoPM != null )
                jdoPM.close();
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }
    
	/**
	 * Obtiene la lista de respuestas que puede utilizar un turno dado para avanzar. 
	 * @param turno objeto Turno 
	 * @return la lista de respuestas posibles en la fase actual del turno recibido.
	 * @see gov.sir.core.negocio.modelo.Fase
	 * @throws <code>DAOException</code>
	 */  
    public List getRespuestasSiguientes(Turno turno) throws DAOException {
        ArrayList lista = new ArrayList();
        HermodProperties hp = HermodProperties.getInstancia();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;
        
        String iType = hp.getProperty(JDOGenieKeys.ORACLE_WORKFLOW);
        String CONSULTA = JDOHermodProperties.getInstancia().getProperty(JDOGenieKeys.GET_LISTA_RTAS_SIGUIENTES);

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(CONSULTA);
            ps.setString(1, turno.getIdTurno());
            ps.setString(2, String.valueOf(turno.getIdProceso()));
            
            rs = ps.executeQuery();
                
            while ( rs.next() ) {
                lista.add(rs.getString(1));
            }
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
            Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
            Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOFasesDAO.class,e);
			if (jdoPM.currentTransaction().isActive()){
				jdoPM.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);            
        } finally {
            try {
                if ( rs != null ) {
                    rs.close();
                }
            
                if ( ps != null ) {
                    ps.close();
                }

                if(connection != null){
                    connection.close();
                }
            }
            catch (SQLException e) {
                Log.getInstance().error(JDOFasesDAO.class,e);
                Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } 
            
            if ( jdoPM != null )
                jdoPM.close();
        }
        return lista;
    }

	/**
	 * Obtiene la fase con que arranca el <code>Proceso</code> recibido. 
	 * @param proceso objeto <code>Proceso del cual se quiere conocer su fase de 
	 * arranque. 
	 * @return la <code>Fase</code> de arranque para el <code>Proceso</code> recibido
	 * como parámetro. 
	 * @throws <code>DAOException</code>
	 */
	public Fase gerFaseArranqueProceso(Proceso proceso) throws DAOException {

		FaseEnhanced fase = new FaseEnhanced();
		PersistenceManager pm = null;
		String id_proceso = String.valueOf(proceso.getIdProceso());
        
			try {
				pm = AdministradorPM.getPM();
				pm.currentTransaction().begin();
				Query q = pm.newQuery(Fases_Arranque_V.class);
				q.setFilter("id_proceso=='"+id_proceso+"'");
			            
				Collection results = (Collection) q.execute();
				Iterator it = results.iterator();
        
				while ( it.hasNext() ) {
					Fases_Arranque_V obj = (Fases_Arranque_V) it.next();
					Log.getInstance().debug(JDOFasesDAO.class,"ID : "+obj.getId_fase());
					fase.setDescripcion(obj.getDesc_fase());
					fase.setID(obj.getId_fase());
					fase.setNombre(obj.getNombre_fase());
				}
				q.close(results);
				pm.currentTransaction().commit();
			} catch (JDOException e) {
				Log.getInstance().error(JDOFasesDAO.class,e);
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} catch (Throwable e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}				
			} finally {
				pm.close();
			}

			return (Fase) fase.toTransferObject();
	}
	
	/**
	* Obtiene la <code>Fase</code> identificada  con el id recibido. 
	* @param id_fase objeto <code>Fase</code>.
	* @return la <code>Fase</code> del Id recibido
	* como parámetro. 
	* @throws <code>DAOException</code>
	*/
	public Fase getFaseById(String idFase) throws DAOException {

		FaseEnhanced fase = new FaseEnhanced();
		PersistenceManager pm = null;
		
			try {
				pm = AdministradorPM.getPM();
				pm.currentTransaction().begin();
				Query q = pm.newQuery(Procesos_V.class);
				q.setFilter("id_fase=='"+idFase+"'");
			            
				Collection results = (Collection) q.execute();
				Iterator it = results.iterator();
        
				while ( it.hasNext() ) {
					Procesos_V obj = (Procesos_V) it.next();
					fase.setDescripcion(obj.getDesc_fase());
					fase.setID(obj.getId_fase());
					fase.setNombre(obj.getNombre_fase());
				}
				q.close(results);
				pm.currentTransaction().commit();
			} catch (JDOException e) {
				Log.getInstance().error(JDOFasesDAO.class,e);
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} catch (Throwable e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}				
			} finally {
				pm.close();
			}

			return (Fase) fase.toTransferObject();
	}
	
	
	/**
	 * Obtiene el tipo de una fase.  
	 * <p>
	 * El tipo de una fase puede ser Automático o Manual.
	 * @param fase_id el identificador de la fase de la cual se desea obtener su tipo.
	 * @return el tipo de fase (Automático o Manual) asociado con la fase. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.constantes.CFase;
	 */ 
	 public String getTipoFase(String fase_id) throws DAOException
	 {
		String tipoFase = null;
		PersistenceManager pm = null;
		
		try 
		{
			
			pm = AdministradorPM.getPM();
			pm.currentTransaction().begin();
			Query q = pm.newQuery(Procesos_V.class);
			q.setFilter("id_fase=='"+fase_id+"'");
			            
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
        
			while ( it.hasNext() ) 
			{
				Procesos_V obj = (Procesos_V) it.next();
				tipoFase = obj.getTipo_fase();
			}
			q.close(results);
			pm.currentTransaction().commit();
			
		}
		
		catch (JDOException e) 
		{
			Log.getInstance().error(JDOFasesDAO.class,e);
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
		    	pm.currentTransaction().rollback();
			}				
		}
		
		finally 
		{
			pm.close();
		}

		return tipoFase;
	 }
	 
	 /**
	 * Obtiene el proceso dado el id de una fase.  
	 * @param fase_id el identificador de la fase de la cual se desea obtener su proceso.
	 * @return el proceso asociado a la fase dada. 
	 * @throws <code>DAOException</code>
	 */ 
	 public String getProcesoByIdFase(String fase_id) throws DAOException
	 {
		String proceso = null;
		PersistenceManager pm = null;
		
		try 
		{
			
			pm = AdministradorPM.getPM();
			pm.currentTransaction().begin();
			Query q = pm.newQuery(Procesos_V.class);
			q.setFilter("id_fase=='"+fase_id+"'");
			            
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
        
			while ( it.hasNext() ) 
			{
				Procesos_V obj = (Procesos_V) it.next();
				proceso = obj.getId_proceso();
			}
			q.close(results);
			pm.currentTransaction().commit();
			
		}
		
		catch (JDOException e) 
		{
			Log.getInstance().error(JDOFasesDAO.class,e);
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
		    	pm.currentTransaction().rollback();
			}				
		}
		
		finally 
		{
			pm.close();
		}

		return proceso;
	 }
	 
	 /**
     * Obiene el iniciador de un proceso
     * @param idProceso El id del proceso
     * @return
     */
    public InicioProcesos obtenerFaseInicial(String idProceso) throws DAOException{
    	InicioProcesos inicioProcesos = new InicioProcesos();
    	PersistenceManager pm = null;
    	Collection col = null;
    	try {
    		pm = AdministradorPM.getPM();
    		Query query = pm.newQuery(InicioProcesosEnhanced.class);
   		 	if(idProceso!=null && idProceso.length()>0){
	   		 	query.setFilter("this.idProceso=='"+idProceso+"'");
	   		 	
	   		 	col = (Collection) query.execute();
	   		 	Iterator iter = col.iterator();
	   		 	while(iter.hasNext()){
	   		 		InicioProcesosEnhanced inicioProcesosEnhanced = 
	   		 			(InicioProcesosEnhanced)iter.next();
	   		 		
	   		 		inicioProcesos = (InicioProcesos)TransferUtils.makeTransfer(inicioProcesosEnhanced);
	   		 	}
	 			query.closeAll();
   		 	}else{
   		 		throw new DAOException("El id del proceso es inválido");
   		 	}
		}catch (DAOException e) {
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
		}catch (Exception e) {
			Log.getInstance().error(JDOFasesDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return inicioProcesos;
    }
    
}