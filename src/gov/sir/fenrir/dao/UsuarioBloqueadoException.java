
package gov.sir.fenrir.dao;

import gov.sir.fenrir.FenrirException;

/**
 * Excepción arrojada cuando un usuario que se encuentra bloqueado en el sistema intenta 
 * registrarse en el mismo
 * @author jfrias
 *
 */
public class UsuarioBloqueadoException extends FenrirException {

	/**
	 * 
	 */
	public UsuarioBloqueadoException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public UsuarioBloqueadoException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UsuarioBloqueadoException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UsuarioBloqueadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
