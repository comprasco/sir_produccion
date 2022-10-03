package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepción ocurrida cuando se excede el número
 * máximo de intentos para una consulta simple
 *
 * @author jmendez
 */
public class MaximoNumeroIntentosException extends AccionWebException {
	/**
	 *
	 */
	public MaximoNumeroIntentosException() {
		super("Se alcanzó el número máximo de intentos para la consulta.");
	}

	/**
	 * @param arg0
	 */
	public MaximoNumeroIntentosException(String arg0) {
		super(arg0);
	}
}
