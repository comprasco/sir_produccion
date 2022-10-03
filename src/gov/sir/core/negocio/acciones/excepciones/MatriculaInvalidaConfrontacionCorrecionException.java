package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * Excepción para validar que las matrículas ingresadas existan en la base de datos en el proceso de correcciones en la fase de confrontación.
 * @author ppabon
*/
public class MatriculaInvalidaConfrontacionCorrecionException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public MatriculaInvalidaConfrontacionCorrecionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MatriculaInvalidaConfrontacionCorrecionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public MatriculaInvalidaConfrontacionCorrecionException(List e) {
		super(e);
	}

	/**
	 * Constructor de la clase.
	 * @param str
	 */
	public MatriculaInvalidaConfrontacionCorrecionException(String str) {
		super(str);
	}

}
