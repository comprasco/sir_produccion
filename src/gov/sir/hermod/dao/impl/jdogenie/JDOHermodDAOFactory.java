/* Generated by Together */

package gov.sir.hermod.dao.impl.jdogenie;

import java.util.HashMap;

import gov.sir.hermod.dao.AlertasDAO;
import gov.sir.hermod.dao.BancosDAO;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.FasesDAO;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.dao.LiquidacionesDAO;
import gov.sir.hermod.dao.LookupDAO;
import gov.sir.hermod.dao.MigracionSirDAO;
import gov.sir.hermod.dao.PagosDAO;
import gov.sir.hermod.dao.ProcesosDAO;
import gov.sir.hermod.dao.RecibosDAO;
import gov.sir.hermod.dao.RelacionesDAO;
import gov.sir.hermod.dao.RepartoTurnosDAO;
import gov.sir.hermod.dao.RepartosDAO;
import gov.sir.hermod.dao.ReportesJasperDAO;
import gov.sir.hermod.dao.SolicitudesDAO;
import gov.sir.hermod.dao.TarifasDAO;
import gov.sir.hermod.dao.TrasladoTurnosDAO;
import gov.sir.hermod.dao.TurnosDAO;
import gov.sir.hermod.dao.TurnosNuevosDAO;
import gov.sir.hermod.dao.TurnosPortalDAO;
import gov.sir.hermod.dao.VariablesOperativasDAO;
import gov.sir.hermod.dao.WebSegEngDAO;


/**
 * Clase que regresa instancias de los diferentes DAOs que 
 * proveen funcionalidades para los servicios de Hermod.   
 * @author mortiz, dlopez
 */
public class JDOHermodDAOFactory extends HermodDAOFactory {

	private static HashMap daosListados = new HashMap();

	public JDOHermodDAOFactory() {
		/*HermodProperties hp = HermodProperties.getInstancia();
		String driver = hp.getProperty("gov.sir.hermod.impl.oracle.driver.type").trim();
		String locale = hp.getProperty("gov.sir.hermod.impl.oracle.locale").trim();
		if (driver.equals("THIN")) {
			Locale.setDefault(new Locale(locale));
			logger.info("Fabrica Instanciada. Driver:" + driver + " Locale:" + Locale.getDefault());
		}*/
	}
	/**
	 * Retorna una instancia de <code>JDOProcesosDAO</code>
	 * @return Instancia de <code>JDOProcesosDAO</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOProcesosDAO
	 */
	public ProcesosDAO getProcesosDAO() throws DAOException {
		return new JDOProcesosDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOTurnosDAO</code>
	 * @return Instancia de <code>JDOTurnosDAO</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO
	 */
	public TurnosDAO getTurnosDAO() throws DAOException {
		return new JDOTurnosDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOLookupDAO</code>
	 * @return Instancia de <code>JDOLookupDAO</code>
	 * @throws <code>JDOLookupDAO</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOLookupDAO
	 */
	public LookupDAO getLookupDAO() throws DAOException {
		return new JDOLookupDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOBancosDAO</code>
	 * @return Instancia de <code>JDOBancosDAO</code>
	 * @throws <code>JDOBancosDAO</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOBancosDAO
	 */
	public BancosDAO getBancosDAO() throws DAOException {
		return new JDOBancosDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOFasesDAO</code>
	 * @return Instancia de <code>JDOFasesDAO</code>
	 * @throws <code>JDOFasesDAO</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOFasesDAO
	 */
	public FasesDAO getFasesDAO() throws DAOException {
		return new JDOFasesDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOPagosDAO</code>
	 * @return Instancia de <code>JDOPagosDAO</code>
	 * @throws <code>JDOPagosDAO</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOPagosDAO
	 */
	public PagosDAO getPagosDAO() throws DAOException {
		return new JDOPagosDAO();
	}

	/**
	 * Retorna una instancia de <code>JDOSolicitudesDAO</code>
	 * @return Instancia de <code>JDOSolicitudesDAO</code>
	 * @throws <code>JDOSolicitudesDAO</code>
	 * @see gov.sir.hermod.dao.impl.jdogenie.JDOSolicitudesDAO
	 */
	public SolicitudesDAO getSolicitudesDAO() throws DAOException {
		return new JDOSolicitudesDAO();
	}

	/**
	* Retorna una instancia de <code>JDOTarifasDAO</code>
	* @return Instancia de <code>JDOTarifasDAO</code>
	* @throws <code>JDOTarifasDAO</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDOTarifasDAO
	*/
	public TarifasDAO getTarifasDAO() throws DAOException {
		return new JDOTarifasDAO();
	}

	/**
	* Retorna una instancia de <code>JDORepartosDAO</code>
	* @return Instancia de <code>JDORepartosDAO</code>
	* @throws <code>JDORepartosDAO</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDORepartosDAO
	*/
	public RepartosDAO getRepartosDAO() throws DAOException {
		return new JDORepartosDAO();
	}

	/**
	* Retorna una instancia de <code>JDOVariablesOperativasDAO</code>
	* @return Instancia de <code>JDOVariablesOperativasDAO</code>
	* @throws <code>JDOVariablesOperativasDAO</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDOVariablesOperativasDAO
	*/
	public VariablesOperativasDAO getVariablesOperativasDAO() throws DAOException {
		return new JDOVariablesOperativasDAO();
	}

	/**
	* Retorna una instancia de <code>JDOLiquidacionesDAO</code>
	* @return Instancia de <code>JDOLiquidacionesDAO</code>
	* @throws <code>JDOLiquidacionesDAO</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO
	*/
	public LiquidacionesDAO getLiquidacionesDAO() throws DAOException {
		return new JDOLiquidacionesDAO();
	}

	/**
	* Retorna una instancia de <code>JDORecibosDAO</code>
	* @return Instancia de <code>JDORecibosDAO</code>
	* @throws <code>JDORecibosDAO</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDORecibosDAO
	*/
	public RecibosDAO getRecibosDAO() throws DAOException {
		return new JDORecibosDAO();
	}

	/**
	* Retorna una instancia de <code>JDOTrasladoTurnosDAO</code>
	* @return Instancia de <code>JDOTrasladoTurnosDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.impl.jdogenie.JDOTrasladoTurnosDAO
	*/
	public TrasladoTurnosDAO getTrasladoTurnosDAO() throws DAOException {
		return new JDOTrasladoTurnosDAO();
	}
	
	/**
	 * 
	 * @param args
	 */	
	public static void main(String[] args) {
		JDOHermodDAOFactory f = new JDOHermodDAOFactory();
	}
	
	/**
	 * 
	 */
	public RepartoTurnosDAO getRepartoTurnosDAO() throws DAOException {
		return new JDORepartoTurnosDAO();
	}
	

	/**
	* Retorna una instancia de <code>AlertasDAO</code>
	* @return Instancia de <code>AlertasDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.RepartoTurnosDAO
	*/
	public AlertasDAO getAlertasDAO() throws DAOException{
		return new JDOAlertasDAO();
	}
	
	/**
	 * 
	 */
	public RelacionesDAO getRelacionesDAO() throws DAOException {
		return new JDORelacionesDAO();
	}
	
	

	/**
	* Retorna una instancia de <code>WebSegEngDAO</code>
	* @return Instancia de <code>WebSegEngDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.WebSegEngDAO
	*/
	public WebSegEngDAO getWebSegEngDAO() throws DAOException{
		return new JDOWebSegEngDAO();
	}
	
	
	/**
	* Retorna una instancia de <code>TurnosNuevosDAO</code>
	* @return Instancia de <code>TurnosNuevosDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.TurnosNuevosDAO
	*/
	public TurnosNuevosDAO getTurnosNuevosDAO() throws DAOException{
		return new JDOTurnosNuevosDAO();
	}

	/**
	* Retorna una instancia de <code>MigracionSirDAO</code>
	* @return Instancia de <code>MigracionSirDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.MigracionSirDAO
	*/
	public MigracionSirDAO getMigracionSirDAO() throws DAOException {
		return new JDOMigracionSirDAO();
	}
	
	/**
	* Retorna una instancia de <code>MigracionSirDAO</code>
	* @return Instancia de <code>MigracionSirDAO</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.hermod.dao.MigracionSirDAO
	*/
	public AbstractJDOListadosPorPaginasDAO getListadosPorPaginasDAO(String idListado) throws DAOException {
		AbstractJDOListadosPorPaginasDAO listadosDao;
		if (daosListados.containsKey(idListado))
		{
			listadosDao = (AbstractJDOListadosPorPaginasDAO)daosListados.get(idListado);
		}else
		{
			listadosDao = new JDOListadosPorPaginasDAO();
			daosListados.put(idListado, listadosDao);
		}
		return listadosDao;
	}
	public ReportesJasperDAO getReportesJasperDAO() {
		return new JDOReportesJasperDAO();
	}

	public TurnosPortalDAO getTurnosPortalDAO() {
		return new JDOTurnosPortalDAO();
	}
}