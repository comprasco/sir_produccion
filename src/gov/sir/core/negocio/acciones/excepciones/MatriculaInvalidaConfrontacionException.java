package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * Excepción para validar que las matrículas ingresadas existan en la base de datos en el proceso de registro en la fase de confrontación.
 * @author ppabon
*/
public class MatriculaInvalidaConfrontacionException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public MatriculaInvalidaConfrontacionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MatriculaInvalidaConfrontacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public MatriculaInvalidaConfrontacionException(List e) {
		super(e);
	}

	/**
	 * Constructor de la clase.
	 * @param str
	 */
	public MatriculaInvalidaConfrontacionException(String str) {
		super(str);
	}

}
