package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class TomarTurnoRegistroException extends EventoException {

	/**
	 * 
	 */
	public TomarTurnoRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TomarTurnoRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public TomarTurnoRegistroException(String str) {
		super(str);
	}

}
