package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.HermodException;

/**
 * @author jfrias
*/
public class ValidacionMatriculaCertificadoHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public ValidacionMatriculaCertificadoHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ValidacionMatriculaCertificadoHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionMatriculaCertificadoHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public ValidacionMatriculaCertificadoHTException(ForsetiException e) {
		super(e);
	}
	
	/**
	 * @param e
	 */
	public ValidacionMatriculaCertificadoHTException(HermodException e) {
		super(e);
	}

}
