/*
 * Clase para el manejo de los procesos existentes en la aplicación.
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
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodProperties;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.ProcesosDAO;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CProceso;

import com.versant.core.jdo.VersantPersistenceManager;

import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FaseProceso;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;

/**
 * Clase para el manejo de los procesos existentes en la aplicación. 
 * @author  mrios, mortiz, dlopez
 */
public class JDOProcesosDAO implements ProcesosDAO {
    
    /**
    * Crea una nueva instancia de <code>JDOProcesosDAO</code>
    */  
    public JDOProcesosDAO() { 
    }



     /**
     * Obtiene la lista de procesos en los que participa un
     * determinado rol.
     * @return una lista de objetos <code>Proceso</code> que están asociados
     * con el rol recibido como parámetro.
     * @param id_rol el identificador del rol del cual se desean obtener los
     * procesos asociados.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getProcesos(String id_rol) throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = null;
        
        try {
            pm = AdministradorPM.getPM();
            
            
            //Se intentan obtener los procesos cuyo rol o rol de arranque es
            //el rol recibido como parámetro. 
            Query q = pm.newQuery(Procesos_V.class);
            q.setFilter("(rol=='"+id_rol+"' || rol_arranque=='"+id_rol+"') && visibilidad =='1'");
                        
            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();
        
            
            //Se hacen transientes cada uno de los objetos de la lista. 
            while ( it.hasNext() ) {
                Procesos_V obj = (Procesos_V) it.next();
                pm.makeTransient(obj);
                
                ProcesoEnhanced proceso = new ProcesoEnhanced(Long.parseLong(obj.getId_proceso()), obj.getNombre_proceso(), obj.getDesc_proceso());
                Iterator itaux = lista.iterator();
                boolean esta = false;
                
                while ( itaux.hasNext() ) {
                    ProcesoEnhanced paux = (ProcesoEnhanced) itaux.next();
                    
                    if ( String.valueOf(paux.getIdProceso()).equals(String.valueOf(proceso.getIdProceso())) ) {
                        esta = true;
                        break;
                    }
                }
                
                if ( !esta ) {
                    lista.add(proceso);
                }
            }
        
            q.close(results);
        } catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
            Log.getInstance().error(JDOProcesosDAO.class,e);
            Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
        } finally {
            pm.close();
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }
    
    
    /**
    * Obtiene la lista de los procesos que inicia un determinado rol.
    * @param id_rol Identificador del rol del cual se desean obtener los
    * procesos de los cuales es iniciador.
    * @return Lista con los procesos que inicia el rol.
    * @throws <code>DAOException</code>
    * @see gov.sir.core.negocio.modelo.Proceso
    */
    public List getProcesosQueInicia(String id_rol) throws DAOException {
        ArrayList lista = new ArrayList();
        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();

            //Se intentan obtener los procesos cuyo rol o rol de arranque es
            //el rol recibido como parámetro. 
            Query q = pm.newQuery(Procesos_V.class);
            q.setFilter("rol_arranque=='" + id_rol + "'");

            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            //Se hacen transientes cada uno de los objetos de la lista. 
            while (it.hasNext()) {
                Procesos_V obj = (Procesos_V) it.next();
                pm.makeTransient(obj);
                lista.add(new Proceso(Long.parseLong(obj.getId_proceso()), obj.getNombre_proceso(), obj.getDesc_proceso()));
            }

            q.close(results);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOProcesosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOProcesosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return lista;
    }

    
	/** 
	 * Obtiene un objeto de tipo <code>Proceso</code>, dado su identificador.
	 * @param pID Identificador del <code>Proceso</code> que se quiere recuperar.
	 * @return El <code>Proceso</code> con identificador pasado como parámetro.  
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Proceso
	 */
	public Proceso getProcesoByID(ProcesoPk pID) throws DAOException {
		ProcesoEnhanced pr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pr = this.getProcesoByID(new ProcesoEnhancedPk(pID), pm);
			
			//Se hace transiente el proceso.
			pm.makeTransient(pr);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}

		return (Proceso) pr.toTransferObject();
	}

	
	
	/** 
	* Obtiene un objeto de tipo <code>ProcesoEnhanced</code>, dado su identificador.
	* <p>Retorna null, si no se encuentra el proceso con el identificador dado. 
	* @param pID Identificador del <code>Proceso</code> que se quiere recuperar.
	* @param pm <code>PersistenceManager</code> de la transacción. 
	* @return El <code>Proceso</code> con identificador pasado como parámetro.  
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	protected ProcesoEnhanced getProcesoByID(ProcesoEnhancedPk pID, PersistenceManager pm) throws DAOException {
		ProcesoEnhanced rta = null;

		if (String.valueOf(pID.idProceso) != null){
			try {
				rta = (ProcesoEnhanced) pm.getObjectById(pID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(),e);
				Log.getInstance().debug(JDOProcesosDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
 
 

	/**
	 * Obtiene un <code>CirculoProceso</code> dado su identificador.
	 * @param cpID identificador del <code>CirculoProceso</code> que se quiere
	 * recuperar. 
	 * @return <code>CirculoProceso</code> con sus atributos 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Proceso
	 */	
	public CirculoProceso getCirculoProcesoById(CirculoProcesoPk cpID) throws DAOException {

		CirculoProcesoEnhanced cpr = null;
		CirculoProcesoEnhancedPk cpeId = new CirculoProcesoEnhancedPk();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			cpeId.anio = cpID.anio;
			cpeId.idCirculo = cpID.idCirculo;
			cpeId.idProceso = cpID.idProceso;
			cpr = this.getCirculoProcesoById(cpeId, pm);
			
			//Se hace transiente el Círculo Proceso. 
			pm.makeTransient(cpr);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		return (CirculoProceso) cpr.toTransferObject();
	}
	

	/**
	* Obtiene un <code>CirculoProceso</code> dado su identificador.
	* <p> Método utilizado en transacciones. 
	* <p> Se obtiene una respuesta null, si no se encuentra el objeto en la BD.
	* @param cpID identificador del <code>CirculoProceso</code>
	* @return <code>CirculoProceso</code> con sus atributos 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/	
	protected CirculoProcesoEnhanced getCirculoProcesoById(CirculoProcesoEnhancedPk cpID, PersistenceManager pm) throws DAOException {

		CirculoProcesoEnhanced rta = null;

		if (cpID.idCirculo != null && String.valueOf(cpID.idProceso) != null){
			try {
				rta = (CirculoProcesoEnhanced) pm.getObjectById(cpID, true);
			} catch (JDOObjectNotFoundException e) {
				Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(),e);
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(),e);
				throw new DAOException(e.getMessage(), e);
			}
		}	
		return rta;
	}
	
	/**
	 * Obtiene la lista de Procesos Padre de un Subproceso 
	 * @param id_proceso el identificador del proceso del cual se desean obtener sus
	 * procesos padre.
	 * @return una lista de objetos <code>Proceso</code>
	 * @see gov.sir.core.negocio.modelo.Proceso
	 * @throws DAOException
	 */
	protected List getProcesosPadre(String id_proceso, VersantPersistenceManager pm) throws DAOException {
		ArrayList lista = new ArrayList();
		HermodProperties hp = HermodProperties.getInstancia();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String CONSULTA = JDOHermodProperties.getInstancia().getProperty(JDOGenieKeys.GET_PROCESOS_PADRE);

		try {
			connection = pm.getJdbcConnection(null);

			ps = connection.prepareStatement(CONSULTA);
			ps.setString(1, id_proceso);
			rs = ps.executeQuery();
				
			while (rs.next()) {
				Log.getInstance().debug(JDOProcesosDAO.class,"Padre Proceso ::" + rs.getString(1));
				ProcesoEnhancedPk pId = new ProcesoEnhancedPk();
				pId.idProceso = Long.parseLong(rs.getString(1));
				ProcesoEnhanced proceso = this.getProcesoByID(pId, pm);
				pm.makeTransient(proceso);
				lista.add(proceso);
				
				//Buscar padres de los procesos padres
				List padres = new ArrayList();
				padres = getProcesosPadre(String.valueOf(proceso.getIdProceso()), pm);
				Iterator itaux = padres.iterator();
                
				while ( itaux.hasNext() ) {
					Proceso paux = (Proceso) itaux.next();
					ProcesoEnhancedPk ppId = new ProcesoEnhancedPk();
					ppId.idProceso = paux.getIdProceso();
					ProcesoEnhanced procesoPadre = this.getProcesoByID(ppId, pm);
					pm.makeTransient(procesoPadre);
					lista.add(procesoPadre);
				}            
			}
		} catch (NullPointerException e) {
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (SQLException e) {
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
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
			} catch (SQLException e) {
				Log.getInstance().error(JDOProcesosDAO.class,e);
				Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return TransferUtils.makeTransferAll(lista);
	}


	/**
	* Obtiene la lista de procesos existentes en el sistema
	* @return una lista de objetos <code>Proceso</code> que están disponibles en el sistema
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	public List getListaProcesos() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(ProcesoEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				ProcesoEnhanced obj = (ProcesoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	public List getListaProcesosRelacion() throws DAOException {
		
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query query = pm.newQuery(ProcesoEnhanced.class);
			query.declareVariables("gov.sir.hermod.dao.impl.jdogenie.Procesos_V procesoV;TipoRelacionEnhanced tipoRelacion");
			query.setFilter("this.idProceso != " + CProceso.PROCESO_CORRECCION + " && " +
		        "this.idProceso != " + CProceso.PROCESO_CORRECCION_CALIFICACION + " && " +
		        "this.idProceso != " + CProceso.PROCESO_ANTIGUO_SISTEMA_INFO + " && " +
		        "this.idProceso != " + CProceso.PROCESO_ANTIGUO_SISTEMA_INFO_CALIFICACION + " && " +
		        "this.idProceso != " + CProceso.PROCESO_ANTIGUO_SISTEMA_INFO_REGISTRO + " && " +
		        "this.idProceso != " + CProceso.PROCESO_CERTIFICADOS + " && " +
		        "this.idProceso != 25 && " +
		        "tipoRelacion.idFase == procesoV.id_fase && " +
				"procesoV.id_proceso == procesoV.sql(\"TO_CHAR(a.ID_PROCESO)\")");
		        //"procesoV.sql(\"TO_NUMBER(ID_PROCESO)\") == this.idProceso");
			Collection col = (Collection)query.execute();
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				ProcesoEnhanced procesoenhanced = (ProcesoEnhanced)iter.next();
				pm.makeTransient(procesoenhanced);
				lista.add(procesoenhanced.toTransferObject());
			}
			
			pm.currentTransaction().commit();
			query.closeAll();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		
		return lista;
	}



	/* (non-Javadoc)
	 * @see gov.sir.hermod.dao.ProcesosDAO#getFasesDeProceso(gov.sir.core.negocio.modelo.Proceso)
	 */
	public List getFasesDeProceso(Proceso proc) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = null;
        
		try {
			pm = AdministradorPM.getPM();
			
			if(proc == null){
				throw new DAOException("Debe Proporcionar un Proceso");
			}
			
            long idProceso = proc.getIdProceso();
            
			//Se intentan obtener los procesos cuyo rol o rol de arranque es
			//el rol recibido como parámetro. 
			Query q = pm.newQuery(Procesos_V.class);
			q.setFilter("id_proceso=='"+idProceso+"'" );
			q.setOrdering("nombre_fase ascending");
                        
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
        
			//Se hacen transientes cada uno de los objetos de la lista. 
			while ( it.hasNext() ) {
				Procesos_V obj = (Procesos_V) it.next();
				pm.makeTransient(obj);
				
				FaseProceso fase = new FaseProceso();
				fase.setIdProceso(obj.getId_proceso());
				fase.setIdFase(obj.getId_fase());
				fase.setNombre(obj.getNombre_fase());
				fase.setDescripcion(obj.getDesc_fase());
				lista.add(fase);
			}
			q.close(results);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		return lista;
	}

	/**
	* Obtiene la lista de procesos existentes en el sistema
	* @return una lista de objetos <code>Proceso</code> que están disponibles en el sistema
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	public Fase getFaseInicialProceso(ProcesoPk procId) throws DAOException {
		PersistenceManager pm = null;
		Fase fase = new Fase();
				
		try {
			pm = AdministradorPM.getPM();
			
			if(procId == null){
				throw new DAOException("Debe Proporcionar un Proceso");
			}
			
			//Se intentan obtener los procesos cuyo rol o rol de arranque es
			//el rol recibido como parámetro. 
			Query q = pm.newQuery(Fases_Arranque_V.class);
			q.setFilter("id_proceso=='"+procId.idProceso+"'");
			            
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
        
			//Se hacen transientes cada uno de los objetos de la lista. 
			while ( it.hasNext() ) {
				Fases_Arranque_V obj = (Fases_Arranque_V) it.next();
				pm.makeTransient(obj);
				fase.setID(obj.getId_fase());
				fase.setNombre(obj.getNombre_fase());
				fase.setDescripcion(obj.getDesc_fase());
			}
			q.close(results);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		return fase;
	}



	/**
	* Obtiene y avanza la secuencia de las constancias de devoluciones, de acuerdo 
	* a los parametros recibidos. 
	* @param circuloId El identificador del <code>Circulo</code> en el que se va a 
	* expedir la constancia de devolución.
	* @param year El año en el que se va a expedir la constancia de devolución. 
	* @param pm Persistence Manager de la transacción.
	* @return El secuencial requerido. 
	* @throws DAOException
	*/
   public long getSecuencialDevolucion (String circuloId, String year)
	   throws DAOException {
	   long rta = -1;
	   PersistenceManager pm = null;
	   VersantPersistenceManager pm2 = null;
	   
	   if (circuloId == null) {
			
		   throw new DAOException("El identificador del círculo es inválido");
	   }
		
		   try {
		   	
			   
			   pm = AdministradorPM.getPM();
			   pm.currentTransaction().begin();
			   //Se hace el cambio de tipo de bloqueo pesimista para
			   //que se bloquee la tabla la cual  nos
			   //provee el secuencial
			   pm2 = (VersantPersistenceManager) pm;
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			   pm = pm2;

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_DEVOLUCIONES);
			   sid.idProceso = idProcesoString.longValue();

			   
			   CirculoProcesoEnhanced circuloProceso = this.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null)
			   {
				   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				   throw new DAOException ("No se pudo obtener el secuencial para la constancia de devolución");
			   }
			   
			  
			   circuloProceso.setSecuencialConstDevolucion(circuloProceso.getSecuencialConstDevolucion() + 1);
			

			   //Volvemos a setear el tipo de bloqueo pesimista
			   //para que no nos bloquee los siquientes registros
			   //consultados
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

			   rta = circuloProceso.getSecuencialConstDevolucion();
			   
		   } 
		   
		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
					pm.currentTransaction().rollback();
			 }
			throw new DAOException("No se pudo obtener el secuencial para la constancia de devolución",e);
		   } 
		   
		   //Excepción JDO
		   catch (JDOException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
			 	pm.currentTransaction().rollback();
			 }
			 throw new DAOException("No se pudo obtener el secuencial para la constancia de devolución",e);	   
		  }
		  catch (DAOException e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para la constancia de devolución",e); 
		  }
		  catch (Exception e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para la constancia de devolución",e); 
		  }
		  //Otra Excepción
		  catch (Throwable t) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		    if (pm.currentTransaction().isActive())
		    {
			  pm.currentTransaction().rollback();
		    }
		    throw new DAOException("No se pudo obtener el secuencial para la constancia de devolución",t);	   
		  }
		
	   
	   
	     //Cerrar la transacción
		finally {
                       pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		  if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().commit();
		  }
		  //pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		 pm.close();
	   }
	   
	   
	   return rta;
   }


   /**
   * Obtiene un Círculo Proceso dado su identificador.
   * <p>Método utilizado para transacciones
   * @param sID identificador del objeto <code>CirculoProceso</code> del cual se obtendrá
   * el secuencial.
   * @param pm PersistenceManager de la transacción
   * @return CirculoProceso con sus atributos. 
   * @throws DAOException
   */
   protected CirculoProcesoEnhanced getCirculoProcesoByID(CirculoProcesoEnhancedPk sID,
	   PersistenceManager pm) throws DAOException {
	   CirculoProcesoEnhanced rta = null;

	   if (sID  != null) {
		   try {
			   rta = (CirculoProcesoEnhanced) pm.getObjectById(sID, true);
		   } catch (JDOObjectNotFoundException e) {
			   rta = null;
		   } catch (JDOException e) {
			   Log.getInstance().error(JDOProcesosDAO.class,e.getMessage());
			   throw new DAOException(e.getMessage(), e);
		   }
	   }
 
	   return rta;
   }



   /**
	* Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo 
	* a los parametros recibidos. 
	* @param circuloId El identificador del <code>Circulo</code> en el que se va a 
	* expedir el recibo de certificados masivos.
	* @param year El año en el que se va a expedir el recibo de certificados masivos. 
	* @return El secuencial requerido. 
	* @throws HermodException
	*/
   public long getSecuencialMasivos(String circuloId, String year) throws DAOException {
	   long rta = -1;
	   PersistenceManager pm = null;
	   VersantPersistenceManager pm2 = null;

	   if (circuloId == null) {
			
		   throw new DAOException("El identificador del círculo es inválido");
	   }
		
		   try {
		   	
			   
			   pm = AdministradorPM.getPM();
			   pm.currentTransaction().begin();
			   //Se hace el cambio de tipo de bloqueo pesimista para
			   //que se bloquee la tabla la cual  nos
			   //provee el secuencial
			   pm2 = (VersantPersistenceManager) pm;
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			   pm = pm2;

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_CERTIFICADOS_MASIVOS);
			   sid.idProceso = idProcesoString.longValue();

			   
			   CirculoProcesoEnhanced circuloProceso = this.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null)
			   {
				   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				   throw new DAOException ("No se pudo obtener el secuencial para el recibo de certificados masivos");
			   }
			   
			  
			   circuloProceso.setSecuencialConstMasivos(circuloProceso.getSecuencialConstMasivos() + 1);
			

			   //Volvemos a setear el tipo de bloqueo pesimista
			   //para que no nos bloquee los siquientes registros
			   //consultados
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

			   rta = circuloProceso.getSecuencialConstMasivos();
			   
		   } 
		   
		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
					pm.currentTransaction().rollback();
			 }
			throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		   } 
		   
		   //Excepción JDO
		   catch (JDOException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
			 	pm.currentTransaction().rollback();
			 }
			 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);	   
		  }
		  catch (DAOException e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (Exception e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  //Otra Excepción
		  catch (Throwable t) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		    if (pm.currentTransaction().isActive())
		    {
			  pm.currentTransaction().rollback();
		    }
		    throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");	   
		  }
			   
	     //Cerrar la transacción
		finally {
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		  if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().commit();
		  }
		 pm.close();
	   }
	   
	   
	   return rta;
   }


 /**
    * @author      :   Julio Alcázar Rivas
    * @change      :   Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo
    * a los parametros recibidos.
    * @Caso Mantis :   000941
    * @param circuloId El identificador del <code>Circulo</code> en el que se va a
    * expedir el recibo de certificados masivos.
    * @param year El año en el que se va a expedir el recibo de certificados masivos.
    * @return El secuencial requerido.
    * @throws HermodException
    */
   public long getSecuencialMasivosExento(String circuloId, String year) throws DAOException {
	   long rta = -1;
	   PersistenceManager pm = null;
	   VersantPersistenceManager pm2 = null;

	   if (circuloId == null) {
		   throw new DAOException("El identificador del círculo es inválido");
	   }

		   try {
			   pm = AdministradorPM.getPM();
			   pm.currentTransaction().begin();
			   //Se hace el cambio de tipo de bloqueo pesimista para
			   //que se bloquee la tabla la cual  nos
			   //provee el secuencial
			   pm2 = (VersantPersistenceManager) pm;
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			   pm = pm2;

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_CERTIFICADOS_MASIVOS);
			   sid.idProceso = idProcesoString.longValue();


			   CirculoProcesoEnhanced circuloProceso = this.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null)
			   {
				   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				   throw new DAOException ("No se pudo obtener el secuencial para el recibo de certificados masivos");
			   }

                           circuloProceso.setSecuencialConstMasivos(circuloProceso.getSecuencialConstMasivos() + 1);
			   //Volvemos a setear el tipo de bloqueo pesimista
			   //para que no nos bloquee los siquientes registros
			   //consultados
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

			   rta = circuloProceso.getSecuencialConstMasivos();

		   }

		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
					pm.currentTransaction().rollback();
			 }
			throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		   }

		   //Excepción JDO
		   catch (JDOException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
			 	pm.currentTransaction().rollback();
			 }
			 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (DAOException e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (Exception e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  //Otra Excepción
		  catch (Throwable t) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		    if (pm.currentTransaction().isActive())
		    {
			  pm.currentTransaction().rollback();
		    }
		    throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		  }

	     //Cerrar la transacción
		finally {
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		  if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().commit();
		  }
		 pm.close();
	   }
	   return rta;
   }

   /**
    * @author      :   Diana Lora
    * @change      :   Obtiene la secuencia de los recibos de reparto notarial, de acuerdo
    * a los parametros recibidos.
    * @Caso Mantis :   0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
    * @param year El año en el que se va a expedir el recibo de reparto notarial.
    * @return El secuencial requerido.
    * @throws HermodException
    */
   public long getSecuencialRepartoNotarial(String circuloId, String year) throws DAOException {
	   long rta = -1;
	   PersistenceManager pm = null;
	   VersantPersistenceManager pm2 = null;

	   if (circuloId == null) {
		   throw new DAOException("El identificador del círculo es inválido");
	   }

		   try {
			   pm = AdministradorPM.getPM();
			   pm.currentTransaction().begin();
			   //Se hace el cambio de tipo de bloqueo pesimista para
			   //que se bloquee la tabla la cual  nos
			   //provee el secuencial
			   pm2 = (VersantPersistenceManager) pm;
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			   pm = pm2;

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
			   sid.idProceso = idProcesoString.longValue();


			   CirculoProcesoEnhanced circuloProceso = this.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null)
			   {
				   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				   throw new DAOException ("No se pudo obtener el secuencial para la solicitud de reparto notarial");
			   }

                           circuloProceso.setLastIdRepartoNotarial(circuloProceso.getLastIdRepartoNotarial() + 1);
			   //Volvemos a setear el tipo de bloqueo pesimista
			   //para que no nos bloquee los siquientes registros
			   //consultados
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

			   rta = circuloProceso.getLastIdRepartoNotarial();

		   }

		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
					pm.currentTransaction().rollback();
			 }
			throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		   }

		   //Excepción JDO
		   catch (JDOException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 if (pm.currentTransaction().isActive())
			 {
			 	pm.currentTransaction().rollback();
			 }
			 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (DAOException e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (Exception e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  //Otra Excepción
		  catch (Throwable t) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		    if (pm.currentTransaction().isActive())
		    {
			  pm.currentTransaction().rollback();
		    }
		    throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		  }

	     //Cerrar la transacción
		finally {
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		  if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().commit();
		  }
		 pm.close();
	   }
	   return rta;
   }
   
    
    @Override
   public long getSecuencialRepartoNotarialRecibo(String circuloId, String year) throws DAOException {
	   long rta = -1;
	   PersistenceManager pm = null;

	   if (circuloId == null) {
		   throw new DAOException("El identificador del círculo es inválido");
	   }

		   try {
			   pm = AdministradorPM.getPM();

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
			   sid.idProceso = idProcesoString.longValue();


			   CirculoProcesoEnhanced circuloProceso = this.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null){				   
				   throw new DAOException ("No se pudo obtener el secuencial para la solicitud de reparto notarial");
			   }

                           rta = circuloProceso.getLastIdTurno() + 1;
		   }

		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
			 if (pm.currentTransaction().isActive())
			 {
					pm.currentTransaction().rollback();
			 }
			throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		   }

		   //Excepción JDO
		   catch (JDOException e) {
			 if (pm.currentTransaction().isActive())
			 {
			 	pm.currentTransaction().rollback();
			 }
			 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (DAOException e) {
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (Exception e) {
			  if (pm.currentTransaction().isActive())
				 {
				 	pm.currentTransaction().rollback();
				 }
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  //Otra Excepción
		  catch (Throwable t) {
		    if (pm.currentTransaction().isActive())
		    {
			  pm.currentTransaction().rollback();
		    }
		    throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		  }

	     //Cerrar la transacción
		finally {
			//pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		  if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().commit();
		  }
		 pm.close();
	   }
	   return rta;
   }


}
