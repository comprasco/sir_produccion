package gov.sir.fenrir.dao;

import gov.sir.fenrir.FenrirException;

/**
 * @author jmendez
 *
 */
public class DAOException extends FenrirException {

	/**
    * Crea una nueva instancia de <code>DAOException</code> sin ningun mensaje detallado
    */
	public DAOException() {
		super();
	}

	/**
     * Crea una nueva instancia de <code>DAOxception</code> con el mensaje detallado
     * @param msg el mensaje detallado
     */
	public DAOException(String arg0) {
		super(arg0);
	}

	 /**
     * Crea una nueva instancia de <code>DAOException</code> con el throwable
     * correspondiente a la excepcion
     * @param tr el throwable de la excepcion
     */
	public DAOException(Throwable arg0) {
		super(arg0);
	}

	 /**
     * Crea una nueva instancia de <code>DAOException</code> con el mensaje detallado, y throwable
     * correspondiente a la excepcion
     * @param msg el mensaje detallado
     * @param tr el throwable de la excepcion
     */
	public DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
