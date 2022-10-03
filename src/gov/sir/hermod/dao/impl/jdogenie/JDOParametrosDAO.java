/**
* @Autor: Santiago Vásquez
* @Change: 2062.TARIFAS.REGISTRALES.2017
*/
package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

public class JDOParametrosDAO {
    public Date getFechaIncentivoRegistral() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        Date date = null;

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            String consulta = "SELECT VALOR_PARAMETRO "
                    + "FROM SIR_NE_PARAMETRO "
                    + "WHERE NOMBRE_PARAMETRO = 'FECHA_INCENTIVO_REGISTRO' ";
            jdoPM = (VersantPersistenceManager) pm;
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            
            while (rs.next()) {
                if (rs.getString(1) != null) {
                    String dateString = rs.getString(1);
                    date = formatter.parse(dateString);
                }
            }
            pm.currentTransaction().commit();
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
        return date;
    }
            }
