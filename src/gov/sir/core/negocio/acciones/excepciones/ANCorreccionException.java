package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta es la excepción lanzada en caso de que ocurra una excepción en la Accion de Negocio ANCorreccion
 * @author ppabon
 */
public class ANCorreccionException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public ANCorreccionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANCorreccionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANCorreccionException(String arg0) {
		super(arg0);
	}

}
