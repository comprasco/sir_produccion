package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

/**
 * @author ppabon
 */
public class SegregacionException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public SegregacionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public SegregacionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SegregacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public SegregacionException(ForsetiException e) {
		super(e);
	}

}
