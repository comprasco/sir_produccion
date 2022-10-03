
package gov.sir.fenrir.dao;

import org.auriga.util.ExceptionPrinter;

import gov.sir.core.is21.CargarPropertiesCifrado;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.impl.oracle.OracleFenrirLDAP;

/**
 * Esta clase implementa el patrón de fábrica abstracta. Una
 * fábrica concreta permite obtener el DAO con el que se va a hacer la conexión
 * a la base de datos
 * @author jfrias
 *
 */
public abstract class FenrirFactory {
	
	/**
	 * Por medio de este metodo se obtiene una implementacion concreta de la fábrica
	 * @return la fábrica concreta
	 */
	public static FenrirFactory getInstance() {
		FenrirFactory instance = null;
		FenrirProperties prop = FenrirProperties.getInstancia();
		String clase = prop.getProperty(FenrirProperties.FENRIR_FACTORY);

		try {
			instance = (FenrirFactory) Class.forName(clase).newInstance();

			return instance;
		} catch (Exception e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().fatal(CargarPropertiesCifrado.class, "Error cargando la clase " + clase + ":" + printer.toString());
			return null;
		}
	}
	/**
	 * Devuelve un <code>FenrirDAO</code>
	 * @return un objeto que implementa la interfaz <code>FenrirDAO</code>
	 * @throws ErrorConexionException cuando hay un error con la conexión
	 */
	public abstract FenrirDAO getFenrirDAO() throws ErrorConexionException;

	/**
	 * Devuelve un <code>OracleFenrirLDAP</code>
	 * @return un objeto que implementa la interfaz <code>OracleFenrirLDAP</code>
	 */
	public abstract OracleFenrirLDAP getFenrirLDAP() throws ConfiguracionPropiedadesException;
}
