package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author mmunoz
 */
public class MatriculaNoDadaException extends AccionWebException {

	/**
	 * @param string
	 */
	public MatriculaNoDadaException(String string) {
		super(string);
	}

}
