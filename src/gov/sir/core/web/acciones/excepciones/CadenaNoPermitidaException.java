package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepción ocurrida cuando la cadena de búsqueda
 * difiere en los tres primeros caracteres con respecto a su versión anterior
 * (Validación en consulta simple)
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
