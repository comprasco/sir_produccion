
package gov.sir.fenrir.dao;

import gov.sir.fenrir.FenrirException;

/**
 * Excepción utilizada para cuando ocurre un error en la creación del Factory de Fenrir
 * @author jfrias
 *
 */
public class FenrirFactoryException extends FenrirException{

	/**
	 * 
	 */
	public FenrirFactoryException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public FenrirFactoryException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */	
	public FenrirFactoryException(String arg0, Throwable arg1) {
		   super(arg0, arg1);
	   }
}
