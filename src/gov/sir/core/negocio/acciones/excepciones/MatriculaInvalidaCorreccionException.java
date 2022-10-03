package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Excepción para validar que las matrículas ingresadas existan en la base de datos en el proceso de correcciones.
 * @author ppabon
*/
public class MatriculaInvalidaCorreccionException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public MatriculaInvalidaCorreccionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MatriculaInvalidaCorreccionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor de la clase.
	 * @param str
	 */
	public MatriculaInvalidaCorreccionException(String str) {
		super(str);
	}

}
