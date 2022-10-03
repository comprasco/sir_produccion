/*
 * AdministradorConexiones.java
 *
 * Created on 18 de junio de 2004, 13:39
 */

package gov.sir.hermod.workflow.impl.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import javax.jdo.PersistenceManager;

import com.versant.core.jdo.VersantPersistenceManager;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.AdministradorPM;


public class AdministradorConexiones {
    /*esta variable es buscada en el archivo gov.sir.hermod.properties para
    obtener el driver de oracle*/
    
    private static AdministradorConexiones INSTANCIA = null;    
    
    private AdministradorConexiones() {
		try{
			AdministradorPM.getPM();
		}
		catch(Throwable e){
			Log.getInstance().error(AdministradorConexiones.class,e);
		}
    }
    
  
	public PersistenceManager getPM() throws DAOException{
		if (INSTANCIA==null){
			 INSTANCIA = new AdministradorConexiones();
		 }
		
		PersistenceManager 	pm = AdministradorPM.getPM();
		//logger.debug("INICIANDO CONEXION (PM) : "+ pm); 	
		 		 		 
		return pm;
	}
    
    public static synchronized AdministradorConexiones getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new AdministradorConexiones();
        }
        return INSTANCIA;
    }
    
	public Connection obtenerConexion(PersistenceManager pm) throws SQLException{
		VersantPersistenceManager pm2 = (VersantPersistenceManager)pm;
		return pm2.getJdbcConnection(null);
	}
    
	public void liberarConexion(PersistenceManager pm){
		//logger.debug("TERMINA LIBERAR CONEXION (PM): "+ pm);
		if(pm!=null){
			pm.close();
		}
	}
    
	public void terminar() {
		AdministradorPM.shutdown();
	}
    
	protected void finalize() throws Throwable{
		Log.getInstance().error(AdministradorConexiones.class,"finalizando...");
		AdministradorPM.shutdown();
	}
}