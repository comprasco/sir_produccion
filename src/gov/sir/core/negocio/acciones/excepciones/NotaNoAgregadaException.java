package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class NotaNoAgregadaException extends EventoException {

	/**
	 * 
	 */
	public NotaNoAgregadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NotaNoAgregadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public NotaNoAgregadaException(String string) {
		super(string);
	}

}
