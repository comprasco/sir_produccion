
package gov.sir.fenrir.dao.impl.jdogenie;

import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.FenrirDAO;
import gov.sir.fenrir.dao.FenrirFactory;
import gov.sir.fenrir.dao.impl.oracle.OracleFenrirLDAP;

/**
 * 
 * @author jfrias
 *
 */
public class OracleJDOFenrirFactory extends FenrirFactory {

	/**
	 * Devuelve un <code>FenrirDAO</code> implementado con JDO
	 * @return un objeto que implementa la interfaz <code>FenrirDAO</code>
	 * @throws ErrorConexionException cuando hay un error con la conexión
	 */
	public FenrirDAO getFenrirDAO() throws ErrorConexionException {
		return new OracleFenrirJDO();
	}

	/**
	 * Devuelve un <code>OracleFenrirLDAP</code>
	 * @return un objeto que implementa la interfaz <code>OracleFenrirLDAP</code>
	 */
	public OracleFenrirLDAP getFenrirLDAP() throws ConfiguracionPropiedadesException{
		return new OracleFenrirLDAP();
	}

}
