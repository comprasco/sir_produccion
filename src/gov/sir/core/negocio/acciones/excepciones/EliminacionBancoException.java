package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class EliminacionBancoException extends EventoException {

	/**
	 * 
	 */
	public EliminacionBancoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EliminacionBancoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public EliminacionBancoException(String arg0) {
		super(arg0);
	}

}
