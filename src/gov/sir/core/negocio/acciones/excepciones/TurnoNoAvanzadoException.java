package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class TurnoNoAvanzadoException extends EventoException {

	/**
	 * 
	 */
	public TurnoNoAvanzadoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TurnoNoAvanzadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TurnoNoAvanzadoException(String arg0) {
		super(arg0);
	}
}
