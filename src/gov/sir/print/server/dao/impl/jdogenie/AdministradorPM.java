/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.print.server.dao.impl.jdogenie;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.print.server.PrintJobsProperties;
import gov.sir.print.server.dao.DAOException;
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
		PrintJobsProperties fp = PrintJobsProperties.getInstancia();
        String fileConfig = fp.getProperty(JDOGenieKeys.FILE_JDO_PROPERTIES);

        Properties props = new Properties();

        try {
        	//logger.info("Iniciando servidor ImpresiónDAO...");
        	String propJca = fp.getProperty(PrintJobsProperties.JDO_JCA);
        	if(propJca == null || !propJca.equals("true")) {
	            //props.load(loader.getResourceAsStream(fileConfig));
	            
	            CargarPropertiesCifrado cpc = new CargarPropertiesCifrado(fileConfig, loader);
	            
	            props = cpc.desencriptar();
	            
	            pmf = JDOHelper.getPersistenceManagerFactory(props);
        	} else {
        		String jndiName = fp.getProperty(PrintJobsProperties.P_JNDI_NAME);
        		Context initialcontext = new InitialContext();
                //connectionFactory = (ConnectionFactory)initialcontext.lookup(jndiName);
                pmf = (PersistenceManagerFactory)initialcontext.lookup(jndiName);
        	}
			
            if (pmf == null) {
            	Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Impresión DAO");
                    
                throw new DAOException(
                    "No fue posible iniciar el PersistenceManagerFactory Impresión DAO");
            }

            //logger.info("Servidor Impresión DAO iniciado en puerto 2388");
        } catch(NamingException e) {
        	Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Impresión DAO");
        	throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Impresión DAO",e);
        } catch (IOException e) {
        	Log.getInstance().fatal(AdministradorPM.class,"Error de lectura de archivo de propiedades Impresión DAO");
            throw new DAOException(
                "No se puede leer archivo de propiedades jdo: " + fileConfig, e);
        } catch (Throwable e) {
        	Log.getInstance().fatal(AdministradorPM.class,"Error de inicialización del PersistenceManagerFactory Impresión DAO");
            throw new DAOException("No fue posible iniciar el PersistenceManagerFactory Impresión DAO",
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

        //logger.debug("Utilizando el Persistence Manager Factory " + pmf);

        return pmf.getPersistenceManager();
    }

    /**
     * Apaga el Persistence Manager factory
     *
     */
    public static void shutdown() throws DAOException{
        try {
			if (pmf != null) {
			    //logger.info("Apagando servidor Impresión DAO...");
			    pmf.close();
			    pmf = null;
			    //logger.info("Servidor Impresión DAO cerrado");
			} else {
			    //logger.info("El Servidor Impresión DAO se encuentra inactivo");
			}
		} catch (JDOException e) {
			Log.getInstance().fatal(AdministradorPM.class,"Error en el shutdown de Impresión DAO "+e.getMessage());
		   throw new DAOException("Error en el shutdown de Impresión DAO "+e.getMessage());
		}
    }
}
