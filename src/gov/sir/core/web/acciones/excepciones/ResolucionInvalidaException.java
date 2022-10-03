package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author mmunoz
 */
public class ResolucionInvalidaException extends AccionWebException {

	/**
	 * @param string
	 */
	public ResolucionInvalidaException(String string) {
		super(string);
	}

}
