package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class ReproduccionSellosException extends EventoException {

	/** 
	 * 
	 */
	public ReproduccionSellosException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ReproduccionSellosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public ReproduccionSellosException(String str) {
		super(str);
	}

}
