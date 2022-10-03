package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class TurnoAnteriorInvalidoException extends EventoException {

	/**
	 * 
	 */
	public TurnoAnteriorInvalidoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TurnoAnteriorInvalidoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TurnoAnteriorInvalidoException(String arg0) {
		super(arg0);
	}

}
