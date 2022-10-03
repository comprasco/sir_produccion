package gov.sir.fenrir.dao.impl.jdogenie;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.DAOException;

import org.auriga.util.ExceptionPrinter;

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
 * Esta clase administra la interfaz de JDO Genie a través de su Persistence
 * Manager Factory el cuál provee objetos PersistenceManager.
 *
 * @author fceballos
 *
 */
public class AdministradorPM {

    private static PersistenceManagerFactory pmf = null;

    /**
     * Inicializa el Persistence Manager Factory
     *
     * @throws DAOException
     */
    private synchronized static void init()
            throws DAOException {

        ClassLoader loader = AdministradorPM.class.getClassLoader();
        FenrirProperties prop = FenrirProperties.getInstancia();
        String archivojdo = prop.getProperty(FenrirProperties.FILE_JDO_PROPERTIES);
        Properties props = new Properties();

        try {
            String propJca = prop.getProperty(FenrirProperties.JDO_JCA);

            if (propJca == null || !propJca.equals("true")) {
                //props.load(loader.getResourceAsStream(archivojdo));

                CargarPropertiesCifrado cpc = new CargarPropertiesCifrado(archivojdo, loader);

                props = cpc.desencriptar();
                pmf = JDOHelper.getPersistenceManagerFactory(props);
            } else {
                String jndiName = prop.getProperty(FenrirProperties.P_JNDI_NAME);
                Context initialcontext = new InitialContext();
                //connectionFactory = (ConnectionFactory)initialcontext.lookup(jndiName);
                pmf = (PersistenceManagerFactory) initialcontext.lookup(jndiName);
            }

            if (pmf == null) {
                Log.getInstance().fatal(AdministradorPM.class, "Error de inicialización del PersistenceManagerFactory Fenrir");
                throw new ConfiguracionPropiedadesException(
                        "No fue posible iniciar el PersistenceManagerFactory Fenrir");
            }
        } catch (NamingException e) {
            Log.getInstance().fatal(AdministradorPM.class, "Error de inicialización del PersistenceManagerFactory Fenrir");
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Fenrir", e);
        } catch (ConfiguracionPropiedadesException e) {
            Log.getInstance().fatal(AdministradorPM.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (IOException e) {
            Log.getInstance().fatal(AdministradorPM.class, "Error de lectura de archivo de propiedades");
            throw new DAOException(
                    "No se puede leer archivo de propiedades jdo:" + archivojdo, e);
        } catch (JDOException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().fatal(AdministradorPM.class,
                    "Error de inicialización del PersistenceManagerFactory Fenrir:" + ep.toString());
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Fenrir", e);
        }
    }

    /**
     * Obtiene un Persistence Manager a partir del Persistence Manager Factory.
     * Si el Persistence Manager Factory no está activo lo inicializa
     *
     * @return
     * @throws DAOException
     */
    public static PersistenceManager getPM()
            throws DAOException {
        if (pmf == null) {
            init();
        }
        Log.getInstance().debug(AdministradorPM.class, "Utilizando el Persistence Manager Factory " + pmf);
        return pmf.getPersistenceManager();
    }

    /**
     * Apaga el Persistence Manager factory
     *
     */
    public static void shutdown() {
        Log.getInstance().info(AdministradorPM.class, "Apagando servidor Fenrir...");
        if (pmf != null) {
            pmf.close();
            Log.getInstance().info(AdministradorPM.class, "Servidor Fenrir cerrado");
            pmf = null;
        }
    }

}
