package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class CancelarAnotacionNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public CancelarAnotacionNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CancelarAnotacionNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public CancelarAnotacionNoEfectuadaException(String str) {
		super(str);
	}

}
