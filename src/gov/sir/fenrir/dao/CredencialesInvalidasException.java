
package gov.sir.fenrir.dao;

import gov.sir.fenrir.FenrirException;

/**
 * Excepción lanzada cuando ocurren errores de validación de la clave del usuario en el sistema
 * @author jfrias
 *
 */
public class CredencialesInvalidasException extends FenrirException {

	/**
	 * 
	 */
	public CredencialesInvalidasException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CredencialesInvalidasException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public CredencialesInvalidasException(String arg0, Throwable arg1) {
		   super(arg0, arg1);
	   }
}
