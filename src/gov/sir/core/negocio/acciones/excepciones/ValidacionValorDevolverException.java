package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Clase para controlar que el valor a devolver al ciudadano no sea mayor al valor pagado por el ciudadano. 
 *
 */
public class ValidacionValorDevolverException extends EventoException {

	/**
	 * 
	 */
	public ValidacionValorDevolverException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionValorDevolverException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ValidacionValorDevolverException(String arg0) {
		super(arg0);
	}

}
