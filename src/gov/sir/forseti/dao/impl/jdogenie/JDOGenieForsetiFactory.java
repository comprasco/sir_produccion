/*
 * Created on 15-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao.impl.jdogenie;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.forseti.dao.CatastroDAO;
import gov.sir.forseti.dao.CiudadanoDAO;
import gov.sir.forseti.dao.FolioDAO;
import gov.sir.forseti.dao.FolioDerivadoDAO;
import gov.sir.forseti.dao.ForsetiFactory;
import gov.sir.forseti.dao.MigracionIncrementalDAO;
import gov.sir.forseti.dao.TrasladoDAO;
import gov.sir.forseti.dao.ZonaRegistralDAO;
/**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
import gov.sir.forseti.dao.FactoryException;
import gov.sir.forseti.dao.DAOException;


/**
 * Implementación de JDOGenie para ForsetiFactory
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JDOGenieForsetiFactory extends ForsetiFactory {
    /**
       * Devuelve un <code>FolioDAO</code>
       * @return un objeto que implementa la interfaz <code>FolioDAO</code>
       */
    public FolioDAO getFolioDAO() {
        return new JDOGenieFolioDAO();
    }

    /**
    * Devuelve un <code>ZonaRegistralDAO</code>
    * @return un objeto que implementa la interfaz <code>ZonaRegistralDAO</code>
    */
    public ZonaRegistralDAO getZonaRegistralDAO() {
        return new JDOGenieZonaRegistralDAO();
    }
    
    /**
    * Devuelve un <code>TrasladoDAO</code>
    * @return un objeto que implementa la interfaz <code>TrasladoDAO</code>
    */
    public TrasladoDAO getTrasladoDAO() {
        return new JDOGenieTrasladoDAO();
    }
    
    /**
    * Devuelve un <code>CiudadanoDAO</code>
    * @return un objeto que implementa la interfaz <code>CiudadanoDAO</code>
    */
    public CiudadanoDAO getCiudadanoDAO() {
            return new JDOGenieCiudadanoDAO();
    }

    /**
    * Devuelve un <code>CatastroDAO</code>
    * @return un objeto que implementa la interfaz <code>CatastroDAO</code>
    */
    public CatastroDAO getCatastroDAO() {
        return new JDOGenieCatastroDAO();
    }   
    

	/**
	* Devuelve un <code>FolioDerivadoDAO</code>
	* @return un objeto que implementa la interfaz <code>FolioDerivadoDAO</code>
	*/
	public FolioDerivadoDAO getFolioDerivadoDAO() {
		return new JDOGenieFolioDerivadoDAO();
	}

	/**
	* Devuelve un <code>MigracionIncrementalDAO</code>
	* @return un objeto que implementa la interfaz <code>MigracionIncrementalDAO</code>
	*/	
	public MigracionIncrementalDAO getMigracionIncrementalDAO() {
		return JDOGenieMigracionIncrementalDAO.getInstance();
	}
        
        /**
        * @Autor: Edgar Lora
        * @Mantis: 13176
        */
        public NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws FactoryException {
            try {
                return new JDOGenieFolioDAO().getNaturalezaJuridicaById(pk);
            } catch (DAOException ex) {
                throw new FactoryException(ex.getMessage());
            }
        } 
}
