
package gov.sir.fenrir.dao.impl.oracle;

import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.FenrirDAO;
import gov.sir.fenrir.dao.FenrirFactory;

/**
 * Es la implementación de Oracle para el <code>FenrirFactory</code>
 * @author jfrias
 *
 */
public class OracleFenrirFactory extends FenrirFactory {

	/**
	 * Se obtinene el <code>OracleFenrirDAO</code>
	 */
	public FenrirDAO getFenrirDAO() throws ErrorConexionException{
		return new OracleFenrirDAO();
	}

	/**
	 * Se obtiene el <code>OracleFenrirLDAP</code>
	 */
	public OracleFenrirLDAP getFenrirLDAP() throws ConfiguracionPropiedadesException {
		return new OracleFenrirLDAP(); 
	}
}
