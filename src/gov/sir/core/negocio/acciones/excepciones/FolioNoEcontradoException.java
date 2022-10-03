package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class FolioNoEcontradoException extends EventoException {

	/**
	 * 
	 */
	public FolioNoEcontradoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FolioNoEcontradoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public FolioNoEcontradoException(String string) {
		super(string);
	}

}
