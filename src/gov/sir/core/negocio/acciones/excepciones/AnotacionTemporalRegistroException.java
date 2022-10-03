package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class AnotacionTemporalRegistroException extends EventoException {

	/**
	 * 
	 */
	public AnotacionTemporalRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AnotacionTemporalRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public AnotacionTemporalRegistroException(String str) {
		super(str);
	}

}
