
package gov.sir.fenrir.dao;

import gov.sir.fenrir.FenrirException;

/**
 * Excepción arrojada cuando se intenta crear un usuario que ya existe en el sistema 
 * registrarse en el mismo
 * @author jfrias
 *
 */
public class UsuarioDuplicadoException extends FenrirException {

	/**
	 * 
	 */
	public UsuarioDuplicadoException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public UsuarioDuplicadoException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UsuarioDuplicadoException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UsuarioDuplicadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
