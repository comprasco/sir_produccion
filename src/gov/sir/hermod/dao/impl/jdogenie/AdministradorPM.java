/*
 * Esta clase administra la interfaz de JDO Genie a través de su
 * Persistence Manager Factory el cuál provee objetos PersistenceManager.
 */
package gov.sir.hermod.dao.impl.jdogenie;

import java.util.Properties;

import javax.jdo.JDOException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.DAOException;
import java.io.IOException;

/**
 * Esta clase administra la interfaz de JDO Genie a través de su
 * Persistence Manager Factory el cuál provee objetos PersistenceManager.
 * @author fceballos
 */
public class AdministradorPM {
    private static PersistenceManagerFactory pmf = null;

    /**
     * Inicializa el Persistence Manager Factory
     * @throws <code>DAOException</code>
     */
    private synchronized static void init() throws DAOException 
    {
        ClassLoader loader = AdministradorPM.class.getClassLoader();
        HermodProperties hp = HermodProperties.getInstancia();
        String fileConfig = hp.getProperty(JDOGenieKeys.FILE_JDO_PROPERTIES);

        Properties props = new Properties();

        try {
        	Log.getInstance().debug(AdministradorPM.class,"Se está iniciando el servidor Hermod ..........");
        	String propJca = hp.getProperty(HermodProperties.JDO_JCA);
        	if(propJca == null || !propJca.equals("true")) {
	            //props.load(loader.getResourceAsStream(fileConfig));
	            CargarPropertiesCifrado cpc = new CargarPropertiesCifrado(fileConfig, loader);
	            
	            props = cpc.desencriptar();
        		
	            pmf = JDOHelper.getPersistenceManagerFactory(props);
        	} else {
        		String jndiName = hp.getProperty(HermodProperties.P_JNDI_NAME);
        		Context initialcontext = new InitialContext();
                //connectionFactory = (ConnectionFactory)initialcontext.lookup(jndiName);
                pmf = (PersistenceManagerFactory)initialcontext.lookup(jndiName);
        	}

            if (pmf == null) {
               Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Hermod");
               throw new DAOException("No fue posible iniciar el PersistenceManagerFactory de Hermod");
            }
            
            Log.getInstance().debug(AdministradorPM.class,"Servidor Hermod iniciado en puerto 2388");
        } catch(NamingException e) {
        	Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Fenrir");
        	throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Fenrir",e);
        } catch (IOException e) {
            Log.getInstance().fatal(AdministradorPM.class,"Error de lectura de archivo de propiedades Hermod");
            throw new DAOException("No se puede leer archivo de propiedades jdo: " + fileConfig, e);
        } catch (JDOException e) {
            Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Hermod");
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Hermod",e);
        }

    }



    /**
     * Obtiene un Persistence Manager a partir del Persistence Manager Factory.
     * <p>
     * Si el Persistence Manager Factory no está activo lo inicializa.
     * @return Instancia del <code>PersistenceManager</code>
     * @throws <code>DAOException</code>
     */
    public static PersistenceManager getPM() throws DAOException {
        if (pmf == null) 
        {
            init();
        }

        Log.getInstance().debug(AdministradorPM.class,"Utilizando el Persistence Manager Factory " + pmf);

        return pmf.getPersistenceManager();
    }

   
    /**
    * Apaga el Persistence Manager factory
    */
    public static void shutdown() 
    {
        if (pmf != null) 
        {
            Log.getInstance().debug(AdministradorPM.class,"Apagando servidor Hermod ..........");
            pmf.close();
            pmf = null;
            Log.getInstance().debug(AdministradorPM.class,"Servidor Hermod cerrado ..........");
        } 
        else 
        {
            Log.getInstance().debug(AdministradorPM.class,"El Servidor Hermod se encuentra inactivo");
        }
    }
}
