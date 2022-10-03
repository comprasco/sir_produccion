package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.TarifaRegistral;
import gov.sir.core.negocio.modelo.TarifaRegistralUVT;
import gov.sir.hermod.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

/**
 * @Autor: Santiago Vásquez 
 * @Change: 2062.TARIFAS.REGISTRALES.2017
 */
public class JDOTarifaRegistralDAO {

    public JDOTarifaRegistralDAO() {
    }

    public ArrayList getTarifasRegistrales() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        ArrayList tarifas = new ArrayList();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            String consulta = "SELECT INICIO_CUANTIA, INICIO_INCLUSIVE, "
                    + "FINAL_CUANTIA, FINAL_INCLUSIVE, "
                    + "TIPO_APLICACION_TARIFA, VALOR_TARIFA "
                    + "FROM SIR_OP_TARIFA_REGISTRAL";
            jdoPM = (VersantPersistenceManager) pm;
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            TarifaRegistral tarifa;
            while (rs.next()) {
                tarifa = new TarifaRegistral();
                if (rs.getInt(1) != 0) {
                    tarifa.setInicioCuantia(new Integer(rs.getInt(1)));
                } else {
                    tarifa.setInicioCuantia(null);
                }
                tarifa.setInicioInclusive(rs.getBoolean(2));
                if (rs.getInt(3) != 0) {
                    tarifa.setFinalCuantia(new Integer(rs.getInt(3)));
                } else {
                    tarifa.setFinalCuantia(null);
                }
                tarifa.setFinalInclusive(rs.getBoolean(4));
                tarifa.setTipoAplicacionTarifa(rs.getString(5));
                tarifa.setValorTarifa(rs.getDouble(6));
                tarifas.add(tarifa);
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
        return tarifas;
    }
    
     public ArrayList getTarifasRegistralesUVT() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        ArrayList tarifas = new ArrayList();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            String consulta = "SELECT INICIO_CUANTIA, INICIO_INCLUSIVE, "
                    + "FINAL_CUANTIA, FINAL_INCLUSIVE, "
                    + "TIPO_APLICACION_TARIFA, VALOR_TARIFA "
                    + "FROM SIR_OP_TARIFA_REGISTRAL_UVT_V";
            jdoPM = (VersantPersistenceManager) pm;
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            TarifaRegistralUVT tarifa;
            while (rs.next()) {
                tarifa = new TarifaRegistralUVT();
                if (rs.getFloat(1) != 0) {
                    tarifa.setInicioCuantia(rs.getFloat(1));
                } else {
                    tarifa.setInicioCuantia(null);
                }
                tarifa.setInicioInclusive(rs.getBoolean(2));
                if (rs.getInt(3) != 0) {
                    tarifa.setFinalCuantia((rs.getFloat(3)));
                } else {
                    tarifa.setFinalCuantia(null);
                }
                tarifa.setFinalInclusive(rs.getBoolean(4));
                tarifa.setTipoAplicacionTarifa(rs.getString(5));
                tarifa.setValorTarifa(rs.getDouble(6));
                tarifas.add(tarifa);
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
        return tarifas;
    }
}
