package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author mmunoz
 */
public class AutoActuacionInvalidoException extends AccionWebException {

	/**
	 * @param string
	 */
	public AutoActuacionInvalidoException(String string) {
		super(string);
	}

}
