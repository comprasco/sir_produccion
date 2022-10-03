package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci�n ocurrida cuando la cadena de b�squeda
 * difiere en los tres primeros caracteres con respecto a su versi�n anterior
 * (Validaci�n en consulta simple)
 * @author jmendez
 */
public class CadenaNoPermitidaException extends AccionWebException {
	/**
	 *
	 */
	public CadenaNoPermitidaException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CadenaNoPermitidaException(String arg0) {
		super(arg0);
	}
}
