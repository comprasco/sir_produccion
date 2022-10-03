package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class AnularFolioException extends EventoException {

	/**
	 * 
	 */
	public AnularFolioException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AnularFolioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public AnularFolioException(String str) {
		super(str);
	}

}
