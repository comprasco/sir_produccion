package gov.sir.forseti.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiMigracionIncrementalProperties;
import gov.sir.forseti.ForsetiProperties;
import gov.sir.forseti.dao.DAOException;

import java.io.IOException;
import java.util.Properties;

import javax.jdo.JDOException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * Esta clase administra la interfaz de JDO Genie a través de su
 * Persistence Manager Factory el cuál provee objetos PersistenceManager.
 * Permite definir un nuevo PM con el fin de usarlo para la carga
 * de objetos desde la base de datos temporal a partir de la cual 
 * se van migrar Folios y Turnos al SIR.
 * @author gvillal
 * Ing. Germán Villalba F.
 * gvillalba@i-siglo21.com
 */
public class AdministradorPMMigracionIncremental extends AdministradorPM {
	
    private static PersistenceManagerFactory pmfMigInc = null;
	
	/**
     * Inicializa el Persistence Manager Factory
     * @throws DAOException
     */
    private synchronized static void init() throws DAOException {
        ClassLoader loader = AdministradorPM.class.getClassLoader();
        ForsetiMigracionIncrementalProperties fp = ForsetiMigracionIncrementalProperties.getInstancia();
        String fileConfig = fp.getProperty(JDOGenieKeys.FILE_JDO_PROPERTIES);

        Properties props = new Properties();

        try {
        	Log.getInstance().info(AdministradorPMMigracionIncremental.class, "Iniciando servidor Forseti (migracion incremental)...");
        	String propJca = fp.getProperty(ForsetiProperties.JDO_JCA);
        	if(propJca == null || !propJca.equals("true")) {
        		props.load(loader.getResourceAsStream(fileConfig));
        		pmfMigInc = JDOHelper.getPersistenceManagerFactory(props);
        	} else {
        		String jndiName = fp.getProperty(ForsetiProperties.P_JNDI_NAME);
        		Context initialcontext = new InitialContext();
                //connectionFactory = (ConnectionFactory)initialcontext.lookup(jndiName);
        		pmfMigInc = (PersistenceManagerFactory)initialcontext.lookup(jndiName);
        	}
			
            if (pmfMigInc == null) {
            	Log.getInstance().fatal(AdministradorPMMigracionIncremental.class, "Error de inicialización del PersistenceManagerFactory Forseti (migracion incremental)");
                throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Forseti (migracion incremental)");
            }

            Log.getInstance().info(AdministradorPMMigracionIncremental.class, "Servidor Forseti (migracion incremental) iniciado en puerto 2388");
        } catch(NamingException e) {
        	Log.getInstance().fatal(AdministradorPMMigracionIncremental.class, "Error de inicialización del PersistenceManagerFactory Forseti (migracion incremental)");
        	throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Forseti (migracion incremental)",e);
        } catch (IOException e) {
        	Log.getInstance().fatal(AdministradorPMMigracionIncremental.class, "Error de lectura de archivo de propiedades Forseti (migracion incremental)");
            throw new DAOException("No se puede leer archivo de propiedades jdo: " + fileConfig, e);
        } catch (JDOException e) {
        	Log.getInstance().fatal(AdministradorPMMigracionIncremental.class, "Error de inicialización del PersistenceManagerFactory Forseti (migracion incremental)");
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Forseti (migracion incremental)",e);
        }
    }
    
    /**
     * Obtiene un Persistence Manager a partir del Persistence Manager Factory.
     * Si el Persistence Manager Factory no está activo lo inicializa
     * @return
     * @throws DAOException
     */
    public static PersistenceManager getPM() throws DAOException {
        if (pmfMigInc == null) {
            init();
        }

        Log.getInstance().debug(AdministradorPMMigracionIncremental.class, "Utilizando el Persistence Manager Factory " + pmfMigInc);

        return pmfMigInc.getPersistenceManager();
    }

}
