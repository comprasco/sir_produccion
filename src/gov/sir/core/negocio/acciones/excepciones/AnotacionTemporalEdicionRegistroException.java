package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class AnotacionTemporalEdicionRegistroException extends EventoException {

	/**
	 * 
	 */
	public AnotacionTemporalEdicionRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AnotacionTemporalEdicionRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public AnotacionTemporalEdicionRegistroException(String str) {
		super(str);
	}

}
