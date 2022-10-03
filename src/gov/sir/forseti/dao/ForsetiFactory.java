/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;

import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiProperties;



/**
 * Esta clase implementa el patrón de fábrica abstracta. Una
 * fábrica concreta permite obtener objetos que proveen los servicios
 * de registro
 *
 * Dentro de la arquitectura de SIR, este servicio permite administrar,
 * consultar y actualizar de manera segura folios de registro inmobiliario
 *
 * @author Ing. Fabián Ceballos Acosta
 */
public abstract class ForsetiFactory {
    /**
    * Obtiene la implementación de la fábrica
    * @return la fábrica concreta
    */
    public static ForsetiFactory getFactory() throws FactoryException {
        ForsetiFactory factory = null;
        ForsetiProperties prop = ForsetiProperties.getInstancia();
        String clase = prop.getProperty(ForsetiProperties.FORSETI_FACTORY);
        
        try {
            factory = (ForsetiFactory) Class.forName(clase).newInstance();
            Log.getInstance().info(ForsetiFactory.class, "Instanciando clase: "+clase);
            return factory;
            
        } catch (Exception e) {
        	Log.getInstance().fatal(ForsetiFactory.class, "Error cargando la clase " + clase + ":" +
                e.getMessage());
			throw new FactoryException("No se pudo cargar la fábrica "+clase);
        }
    }

    /**
    * Devuelve un <code>FolioDAO</code>
    * @return un objeto que implementa la interfaz <code>FolioDAO</code>
    */
    public abstract FolioDAO getFolioDAO();


    /**
    * Devuelve un <code>ZonaRegistralDAO</code>
    * @return un objeto que implementa la interfaz <code>ZonaRegistralDAO</code>
    */
    public abstract ZonaRegistralDAO getZonaRegistralDAO();
    
    /**
     * Devuelve un <code>TrasladoDAO</code>
     * @return un objeto que implementa la interfaz <code>TrasladoDAO</code>
     */
    public abstract TrasladoDAO getTrasladoDAO();

    /**
     * Devuelve un <code>CiudadanoDAO</code>
     * @return un objeto que implementa la interfaz <code>CiudadanoDAO</code>
     */
    public abstract CiudadanoDAO getCiudadanoDAO();
    
    /**
     * Devuelve un <code>CatastroDAO</code>
     * @return un objeto que implementa la interfaz <code>CatastroDAO</code>
     */
    public abstract CatastroDAO getCatastroDAO();
    
    
	/**
	 * Devuelve un <code>FolioDerivadoDAO</code>
	 * @return un objeto que implementa la interfaz <code>FolioDerivadoDAO</code>
	 */
	public abstract FolioDerivadoDAO getFolioDerivadoDAO();
	
	/**
	 * Devuelve un <code>MigracionIncrementalDAO</code>
	 * @return un objeto que implementa la interfaz <code>MigracionIncrementalDAO</code>
	 */
	public abstract MigracionIncrementalDAO getMigracionIncrementalDAO();
        
        /**
        * @Autor: Edgar Lora
        * @Mantis: 13176
        */
        public abstract NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws FactoryException ;
}
