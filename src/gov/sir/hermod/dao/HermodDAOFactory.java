package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.impl.jdogenie.AbstractJDOListadosPorPaginasDAO;

/**
 * Esta clase implementa el patrón de fábrica abstracta. Una fábrica concreta
 * permite obtener objetos del tipo Processor para procesar los mensajes que
 * deben ser enviados y recibidos a un workflow.
 *
 * Dentro de la arquitectura de SIR, este servicio permite conocer a que
 * estación se debe enviar un Turno cuando una actividad se complete.
 *
 * @author Ing. Diego Hernando Cantor
 */
public abstract class HermodDAOFactory {

    /**
     * Obtiene la implementación de la fábrica
     *
     * @return la fábrica concreta
     * @throws </code>DAOException</code>
     */
    public static HermodDAOFactory getFactory() throws DAOException {
        HermodProperties prop = HermodProperties.getInstancia();
        String clase = prop.getProperty(HermodProperties.HERMOD_DAO_FACTORY);

        try {
            return (HermodDAOFactory) Class.forName(clase).newInstance();
        } catch (Exception e) {
            Log.getInstance().debug(HermodDAOFactory.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Retorna una instancia de <code>ProcesosDAO</code>
     *
     * @return Instancia de <code>ProcesosDAO</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.hermod.dao.ProcesosDAO
     */
    public abstract ProcesosDAO getProcesosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>FasesDAO</code>
     *
     * @return Instancia de <code>FasesDAO</code>
     * @throws <code>FasesDAO</code>
     * @see gov.sir.hermod.dao.FasesDAO
     */
    public abstract FasesDAO getFasesDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>JDOTurnosDAO</code>
     *
     * @return Instancia de <code>TurnosDAO</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.hermod.dao.TurnosDAO
     */
    public abstract TurnosDAO getTurnosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>LookupDAO</code>
     *
     * @return Instancia de <code>LookupDAO</code>
     * @throws <code>JDOLookupDAO</code>
     * @see gov.sir.hermod.dao.LookupDAO
     */
    public abstract LookupDAO getLookupDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>BancosDAO</code>
     *
     * @return Instancia de <code>BancosDAO</code>
     * @throws <code>BancosDAO</code>
     * @see gov.sir.hermod.dao.BancosDAO
     */
    public abstract BancosDAO getBancosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>PagosDAO</code>
     *
     * @return Instancia de <code>PagosDAO</code>
     * @throws <code>PagosDAO</code>
     * @see gov.sir.hermod.dao.PagosDAO
     */
    public abstract PagosDAO getPagosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>SolicitudesDAO</code>
     *
     * @return Instancia de <code>SolicitudesDAO</code>
     * @throws <code>SolicitudesDAO</code>
     * @see gov.sir.hermod.dao.SolicitudesDAO
     */
    public abstract SolicitudesDAO getSolicitudesDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>TarifasDAO</code>
     *
     * @return Instancia de <code>TarifasDAO</code>
     * @throws <code>TarifasDAO</code>
     * @see gov.sir.hermod.dao.TarifasDAO
     */
    public abstract TarifasDAO getTarifasDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>RepartosDAO</code>
     *
     * @return Instancia de <code>RepartosDAO</code>
     * @throws <code>RepartosDAO</code>
     * @see gov.sir.hermod.dao.RepartosDAO
     */
    public abstract RepartosDAO getRepartosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>VariablesOperativasDAO</code>
     *
     * @return Instancia de <code>VariablesOperativasDAO</code>
     * @throws <code>VariablesOperativasDAO</code>
     * @see gov.sir.hermod.dao.VariablesOperativasDAO
     */
    public abstract VariablesOperativasDAO getVariablesOperativasDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>LiquidacionesDAO</code>
     *
     * @return Instancia de <code>LiquidacionesDAO</code>
     * @throws <code>LiquidacionesDAO</code>
     * @see gov.sir.hermod.dao.LiquidacionesDAO
     */
    public abstract LiquidacionesDAO getLiquidacionesDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>RecibosDAO</code>
     *
     * @return Instancia de <code>RecibosDAO</code>
     * @throws <code>RecibosDAO</code>
     * @see gov.sir.hermod.dao.RecibosDAO
     */
    public abstract RecibosDAO getRecibosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>RepartoTurnosDAO</code>
     *
     * @return Instancia de <code>RepartoTurnosDAO</code>
     * @throws <code>RepartoTurnosDAO</code>
     * @see gov.sir.hermod.dao.RepartoTurnosDAO
     */
    public abstract RepartoTurnosDAO getRepartoTurnosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>TrasladoTurnosDAO</code>
     *
     * @return Instancia de <code>RepartoTurnosDAO</code>
     * @throws <code>RepartoTurnosDAO</code>
     * @see gov.sir.hermod.dao.RepartoTurnosDAO
     */
    public abstract TrasladoTurnosDAO getTrasladoTurnosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>AlertasDAO</code>
     *
     * @return Instancia de <code>AlertasDAO</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.hermod.dao.RepartoTurnosDAO
     */
    public abstract AlertasDAO getAlertasDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>RelacionesDAO</code>
     *
     * @return Instancia de <code>RelacionesDAO</code>
     * @throws DAOException
     * @see gov.sir.hermod.dao.RelacionesDAO
     */
    public abstract RelacionesDAO getRelacionesDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>WebSegEngDAO</code>
     *
     * @return Instancia de <code>WebSegEngDAO</code>
     * @throws DAOException
     * @see gov.sir.hermod.dao.WebSegEngDAO
     */
    public abstract WebSegEngDAO getWebSegEngDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>TurnosNuevosDAO</code>
     *
     * @return Instancia de <code>TurnosNuevosDAO</code>
     * @throws DAOException
     */
    public abstract TurnosNuevosDAO getTurnosNuevosDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>MigracionSirDAO</code>
     *
     * @return Instancia de <code>MigracionSirDAO</code>
     * @throws DAOException
     */
    public abstract MigracionSirDAO getMigracionSirDAO() throws DAOException;

    /**
     * Retorna una instancia de <code>JDOListadosPorPaginasDAO</code>
     *
     * @return Instancia de <code>JDOListadosPorPaginasDAO</code>
     * @throws DAOException
     */
    public abstract AbstractJDOListadosPorPaginasDAO getListadosPorPaginasDAO(String idListado) throws DAOException;

    public abstract ReportesJasperDAO getReportesJasperDAO();

    /*Adiciona Funcionalidad Boton de Pago
    * Author: Ingeniero Diego Hernandez
    * Modificacion en: 2010/02/23 by jvenegas
    */
    public abstract TurnosPortalDAO getTurnosPortalDAO();

}
