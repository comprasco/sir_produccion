package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci�n ocurrida cuando se excede el n�mero
 * m�ximo de intentos para una consulta simple
 *
 * @author jmendez
 */
public class MaximoNumeroIntentosException extends AccionWebException {
	/**
	 *
	 */
	public MaximoNumeroIntentosException() {
		super("Se alcanz� el n�mero m�ximo de intentos para la consulta.");
	}

	/**
	 * @param arg0
	 */
	public MaximoNumeroIntentosException(String arg0) {
		super(arg0);
	}
}
