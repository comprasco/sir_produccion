package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepción ocurrida cuando la naturaleza jurídica que se quiere cargar es inválida.
 * @author ppabon
 */
public class NaturalezaInvalidaException extends AccionWebException {

	/**
	 * 
	 */
	public NaturalezaInvalidaException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public NaturalezaInvalidaException(String arg0) {
		super(arg0);
	}
}
