/*
 * JDOCatastroDAO.java
 *
 * Created on 14 de marzo de 2005, 12:20
 */

package gov.sir.forseti.dao.impl.jdogenie;

import java.util.Date;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.jdo.JDOException;

import gov.sir.forseti.dao.CatastroDAO;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.dao.DAOException;

import com.versant.core.jdo.VersantPersistenceManager;

/**
 *
 * @author  jalvarez
 */
public class JDOGenieCatastroDAO implements CatastroDAO {

    /** Creates a new instance of JDOCatastroDAO */
    public JDOGenieCatastroDAO() {
    }
    
    /**
     * Servicio que permite ejecutar el proceso de catastro para una 
     * oficina (círculo) específica
     * @param fechIni Fecha de inicio del proceso
     * @param fechFin Fecha de finalización del proceso
     * @param circulo Círculo registral (oficina) del proceso
     * @param us Usuario que solicita la ejecución del proceso
     * @return True: ejecución exitosa; False: ejecución no exitosa
     * @throws <code>DAOException</code>
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
        CallableStatement  cs = null;

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            cs = connection.prepareCall("{call SIR_CATASTRO.PROCESO_CATASTRO(?,?,?,?)}");
            java.sql.Date dFechIni = new java.sql.Date(fechIni.getTime());
            java.sql.Date dFechFin = new java.sql.Date(fechFin.getTime());
            cs.setDate(1, dFechIni);
            cs.setDate(2, dFechFin);
            cs.setString(3, "CATASTRO_DIR");
            cs.setString(4, circulo.getIdCirculo().substring(2,3));
            cs.execute();
            jdoPM.currentTransaction().commit();
            return true;
        } catch (JDOException jdoe) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieCatastroDAO.class, jdoe.getMessage(), jdoe);
            throw new DAOException(jdoe.getMessage(), jdoe);
        } catch (SQLException sqle) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieCatastroDAO.class, sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage(), sqle);
        } catch (Exception sqle) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieCatastroDAO.class, sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage(), sqle);
        }
         finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if(connection != null){
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
            	Log.getInstance().error(JDOGenieCatastroDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
}