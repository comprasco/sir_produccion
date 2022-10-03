package gov.sir.hermod.dao.impl.jdogenie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.Alerta;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.jdogenie.AlertaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AlertaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FaseAlertaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FaseAlertaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhancedPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.AlertasDAO;
import gov.sir.hermod.dao.DAOException;


/**
 * @author fceballos
 * Clase para el manejo de los objetos de tipo <code>Alerta</code> utilizados en la aplicacion.<p>
 * <p>
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * @see gov.sir.core.negocio.modelo.Alerta
 */
public class JDOAlertasDAO implements AlertasDAO{
	
	/** 
	* Crea una nueva instancia de <code>JDOAlertasDAO</code>
	* <p>
	* Método Constructor.  
	*/
	public JDOAlertasDAO() {
	}
	
	
	/**
	 * Indica si una alerta se encuentra configurada para una fase y un proceso determinado
	 * @param faseAlertaID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected boolean isAlertaConfigurada(FaseAlertaEnhancedPk faseAlertaID, PersistenceManager pm) throws DAOException{
		boolean rta = false;
		if(this.getFaseAlertaById(faseAlertaID, pm)!=null){
			rta = true;
		}
		return rta;
	}
	
	
	/**
	 * Obtiene un objeto FaseAlerta dado su ID
	 * @param cID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FaseAlertaEnhanced getFaseAlertaById(FaseAlertaEnhancedPk cID, PersistenceManager pm)
		throws DAOException {
		FaseAlertaEnhanced rta = null;

		if (cID.idFase != null) {
			try {
				rta = (FaseAlertaEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOAlertasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rta;
	}
	
	
	
	/**
	 * Añade una alerta si el proceso y la fase de ésta se encuentra configurada en la tabla
	 * de fases que requieren alerta
	 * @param alerta
	 * @param pm
	 * @return true: inserta la alarma, false: NO la inserta porque no está configurada la fase en
	 * las alertas
	 * @throws DAOException
	 */
	protected boolean addAlerta(AlertaEnhanced alerta, PersistenceManager pm)throws DAOException {
		boolean rta = false;
		JDOLookupDAO lookDAO = new JDOLookupDAO();
		try {
			//1. Se revisa si en el proceso y la fase determinada se debe registrar la alarma
			FaseAlertaEnhancedPk faseAlertaID = new FaseAlertaEnhancedPk();
			faseAlertaID.idFase = alerta.getIdFase();
			faseAlertaID.idProceso = alerta.getIdProceso();
			
			if(this.isAlertaConfigurada(faseAlertaID, pm)){
				//La fase y el proceso está configurado para que registre alertas:
				
				//Se lee el valor inicial del contador de alertas:
				OPLookupCodesEnhancedPk contadorID = new OPLookupCodesEnhancedPk();
				contadorID.tipo = COPLookupTypes.CONFIGURACION_ALERTAS;
				contadorID.codigo = COPLookupCodes.NUMERO_MAXIMO_ALERTAS;
				
				OPLookupCodesEnhanced look = lookDAO.getOPLookupCodesById(contadorID, pm);
				
				int numeroMaximoAlertas;
				if(look==null){
					//Si no encuentra se asigna una por defecto
					numeroMaximoAlertas = 20;
				}
				else{
					numeroMaximoAlertas = Integer.parseInt(look.getValor());
				}
				
				//Se revisas la alerta ya existe:
				AlertaEnhancedPk alertaID = new AlertaEnhancedPk();
				alertaID.idEstacion = alerta.getIdEstacion();
				alertaID.idFase = alerta.getIdFase();
				alertaID.idProceso = alerta.getIdProceso();
				alertaID.idWorkflow = alerta.getIdWorkflow();
				
				AlertaEnhanced alertaExistente = this.getAlertaById(alertaID, pm);
				
				if(alertaExistente!=null){
					alertaExistente.setContador(numeroMaximoAlertas);
				}
				else{
					alerta.setContador(numeroMaximoAlertas);
					pm.makePersistent(alerta);
				}
				rta = true;
			}
			
		} catch (JDOException e) {
			Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
			Log.getInstance().debug(JDOAlertasDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}
	
	
	/**
	 * Elimina una alerta con el proceso, fase, estación e idWorkflow correspondiente. Si no encuentra
	 * la alerta retorna false, si la encuentra la elimina y retorna true
	 * @param alerta
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected boolean deleteAlerta(AlertaEnhanced alerta, PersistenceManager pm)throws DAOException {
		boolean rta = false;
		JDOLookupDAO lookDAO = new JDOLookupDAO();
		try {
			//Se revisa si la alerta existe:
			AlertaEnhancedPk alertaID = new AlertaEnhancedPk();
			alertaID.idEstacion = alerta.getIdEstacion();
			alertaID.idFase = alerta.getIdFase();
			alertaID.idProceso = alerta.getIdProceso();
			alertaID.idWorkflow = alerta.getIdWorkflow();
			
			AlertaEnhanced alertaExistente = this.getAlertaById(alertaID, pm);
			
			if(alertaExistente!=null){
				pm.deletePersistent(alertaExistente);
				rta = true;
			}
	
		} catch (JDOException e) {
			Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
			Log.getInstance().debug(JDOAlertasDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}
	
	
	
	/**
	 * Retorna una alerta dado su ID
	 * @param cID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected AlertaEnhanced getAlertaById(AlertaEnhancedPk cID, PersistenceManager pm)
		throws DAOException {
		AlertaEnhanced rta = null;

		if ((cID.idEstacion != null)&&(cID.idFase!=null)&&(cID.idWorkflow!=null)) {
			try {
				rta = (AlertaEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOAlertasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rta;
	}
	
	/**
	 * Obtiene la lista de alertas para la estación determinada. Si no se tiene alertas
	 * se retorna una lista vacía. Si existen alertas se rebaja el contador de alertas, si este
	 * llega a 0 se elimina la alerta.
	 * @param idEstacion
	 * @return
	 * @throws DAOException
	 */
	public List getAlertas(String idEstacion)throws DAOException {
		List rta = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		try {
			pm.currentTransaction().begin();
			Query query = pm.newQuery(AlertaEnhanced.class);
			query.declareParameters("String idEst");
			query.setFilter("this.idEstacion==idEst");
			Collection col = (Collection)query.execute(idEstacion);
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				AlertaEnhanced alerta = (AlertaEnhanced)iter.next();
				rta.add((Alerta)alerta.toTransferObject());
    			if(alerta.getContador()==0){
    				pm.deletePersistent(alerta);
    			}
    			else{
					alerta.setContador(alerta.getContador()-1);
    			}
			}
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
			Log.getInstance().debug(JDOAlertasDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOAlertasDAO.class,e.getMessage(), e);
			Log.getInstance().debug(JDOAlertasDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		}
		  finally{
			pm.close();
		}
		
		return rta;
	}

}
