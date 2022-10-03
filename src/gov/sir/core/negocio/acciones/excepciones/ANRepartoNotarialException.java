package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepci�n es lanzada cuando ha ocurrido alg�n error en la acci�n de negocio de ANRepartoNotarial.
 * @author ppabon
 */
public class ANRepartoNotarialException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public ANRepartoNotarialException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANRepartoNotarialException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANRepartoNotarialException(String arg0) {
		super(arg0);
	}

}
