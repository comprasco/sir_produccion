package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class TurnoNoEncontradoException extends EventoException {

	/**
	 * 
	 */
	public TurnoNoEncontradoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TurnoNoEncontradoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TurnoNoEncontradoException(String arg0) {
		super(arg0);
	}

}
