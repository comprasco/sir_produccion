package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class MatriculaAsociadaInvalidaRegistroException extends EventoException {

	/**
	 * 
	 */
	public MatriculaAsociadaInvalidaRegistroException() {
		super();
	}

	/**
	 * @param str
	 */
	public MatriculaAsociadaInvalidaRegistroException(String str) {
		super(str);
	}

	/**
	* @param arg0
	* @param arg1
	*/
	public MatriculaAsociadaInvalidaRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
