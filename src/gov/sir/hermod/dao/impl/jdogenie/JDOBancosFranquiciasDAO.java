/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Banco Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.hermod.dao.impl.jdogenie;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;

import javax.jdo.PersistenceManager;
import gov.sir.hermod.dao.DAOException;

import gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhancedPk;


import gov.sir.core.negocio.modelo.util.Log;
/**
 *
 * @author Administrador
 */
public class JDOBancosFranquiciasDAO {
    
    public JDOBancosFranquiciasDAO() {
	}
    
    /**
	 * Obtiene un <code>Banco</code> dado su identificador, método utilizado
	 * para transacciones.
	 * <p>
	 * En caso de que el <code>Banco</code>con el identificador dado no se
	 * encuentre en la Base de Datos se retorna <code>null</code>
	 * @param bID identificador del <code>Banco</code>
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>Banco</code> con sus atributos. 
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Banco
	 */
	protected BancoFranquiciaEnhanced getBancoByID(
		BancoFranquiciaEnhancedPk bID,
		PersistenceManager pm)
		throws DAOException {
		BancoFranquiciaEnhanced rta = null;

		if (bID.idBanco != null && bID.idTipoFranquicia > 0) {
			try {
				rta = (BancoFranquiciaEnhanced) pm.getObjectById(bID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().debug(JDOBancosDAO.class,e);
				Log.getInstance().error(JDOBancosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rta;
	}
}
