/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao.impl.jdogenie;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.core.negocio.modelo.util.Log;
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
 * @author fceballos
 *
 */
class AdministradorPM {
    private static PersistenceManagerFactory pmf = null;

    /**
     * Inicializa el Persistence Manager Factory
     * @throws DAOException
     */
    private synchronized static void init() throws DAOException {
        ClassLoader loader = AdministradorPM.class.getClassLoader();
        ForsetiProperties fp = ForsetiProperties.getInstancia();
        String fileConfig = fp.getProperty(JDOGenieKeys.FILE_JDO_PROPERTIES);

        Properties props = new Properties();

        try {
        	Log.getInstance().info(AdministradorPM.class, "Iniciando servidor Forseti...");
        	String propJca = fp.getProperty(ForsetiProperties.JDO_JCA);
        	if(propJca == null || !propJca.equals("true")) {
        		//props.load(loader.getResourceAsStream(fileConfig));
        		
        		CargarPropertiesCifrado cpc = new CargarPropertiesCifrado(fileConfig, loader);
 	            
 	            props = cpc.desencriptar();
 	            
        		pmf = JDOHelper.getPersistenceManagerFactory(props);
        	} else {
        		String jndiName = fp.getProperty(ForsetiProperties.P_JNDI_NAME);
        		Context initialcontext = new InitialContext();
                //connectionFactory = (ConnectionFactory)initialcontext.lookup(jndiName);
                pmf = (PersistenceManagerFactory)initialcontext.lookup(jndiName);
        	}
			
            if (pmf == null) {
            	Log.getInstance().fatal(AdministradorPM.class, "Error de inicialización del PersistenceManagerFactory Forseti");
                throw new DAOException(
                    "No fue posible iniciar el PersistenceManagerFactory Forseti");
            }

            Log.getInstance().info(AdministradorPM.class, "Servidor Forseti iniciado en puerto 2388");
        } catch(NamingException e) {
        	Log.getInstance().fatal(AdministradorPM.class, "Error de inicialización del PersistenceManagerFactory Forseti");
        	throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Forseti",e);
        } catch (IOException e) {
        	Log.getInstance().fatal(AdministradorPM.class, "Error de lectura de archivo de propiedades Forseti");
            throw new DAOException(
                "No se puede leer archivo de propiedades jdo: " + fileConfig, e);
        } catch (JDOException e) {
        	Log.getInstance().fatal(AdministradorPM.class, 
                "Error de inicialización del PersistenceManagerFactory Forseti");
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Forseti",
                e);
        }
    }

    /**
     * Obtiene un Persistence Manager a partir del Persistence Manager Factory.
     * Si el Persistence Manager Factory no está activo lo inicializa
     * @return
     * @throws DAOException
     */
    public static PersistenceManager getPM() throws DAOException {
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
    public static void shutdown() throws DAOException{
        try {
			if (pmf != null) {
				Log.getInstance().info(AdministradorPM.class, "Apagando servidor Forseti...");
			    pmf.close();
			    pmf = null;
			    Log.getInstance().info(AdministradorPM.class, "Servidor Forseti cerrado");
			} else {
				Log.getInstance().info(AdministradorPM.class, "El Servidor Forseti se encuentra inactivo");
			}
		} catch (JDOException e) {
			Log.getInstance().fatal(AdministradorPM.class, "Error en el shutdown de forseti "+e.getMessage());
		   throw new DAOException("Error en el shutdown de forseti "+e.getMessage());
		}
    }
}
