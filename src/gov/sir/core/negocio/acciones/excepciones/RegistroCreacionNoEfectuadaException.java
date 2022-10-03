package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class RegistroCreacionNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public RegistroCreacionNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RegistroCreacionNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public RegistroCreacionNoEfectuadaException(String string) {
	}

}
