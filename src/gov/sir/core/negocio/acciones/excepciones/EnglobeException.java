package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

/**
 * @author ppabon
 */
public class EnglobeException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public EnglobeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public EnglobeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EnglobeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public EnglobeException(ForsetiException e) {
		super(e);
	}

}
