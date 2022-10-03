package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class CreacionBancoException extends EventoException {

	/**
	 * 
	 */
	public CreacionBancoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreacionBancoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public CreacionBancoException(String arg0) {
		super(arg0);
	}

}
