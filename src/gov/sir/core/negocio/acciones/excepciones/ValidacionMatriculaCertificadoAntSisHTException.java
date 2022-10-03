package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

/**
 * @author jfrias
*/
public class ValidacionMatriculaCertificadoAntSisHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public ValidacionMatriculaCertificadoAntSisHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ValidacionMatriculaCertificadoAntSisHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionMatriculaCertificadoAntSisHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public ValidacionMatriculaCertificadoAntSisHTException(ForsetiException e) {
		super(e);
	}

}
