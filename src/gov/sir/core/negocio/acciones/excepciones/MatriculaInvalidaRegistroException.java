package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class MatriculaInvalidaRegistroException extends EventoException {

	/**
	 * 
	 */
	public MatriculaInvalidaRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MatriculaInvalidaRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public MatriculaInvalidaRegistroException(String str) {
		super(str);
	}

}
