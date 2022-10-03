package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class EntregaNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public EntregaNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EntregaNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public EntregaNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
