package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
  * @author mriveros
 */
public class TomarFoliosException extends EventoException {

	/**
	 * 
	 */
	public TomarFoliosException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TomarFoliosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TomarFoliosException(String arg0) {
		super(arg0);
	}

}
