package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class FolioNoEncontradoException extends EventoException {

	/**
	 * 
	 */
	public FolioNoEncontradoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FolioNoEncontradoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public FolioNoEncontradoException(String arg0) {
		super(arg0);
	}

}
