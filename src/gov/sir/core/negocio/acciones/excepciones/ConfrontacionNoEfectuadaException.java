package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class ConfrontacionNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public ConfrontacionNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConfrontacionNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConfrontacionNoEfectuadaException(String arg0) {
		super(arg0);
	}

}
