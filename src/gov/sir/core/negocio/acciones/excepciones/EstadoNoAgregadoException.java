package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Excepción lanzada cuando se intenta crear un estado y no es posible crearlo.
  * @author ppabon
 */
public class EstadoNoAgregadoException extends EventoException {

	/**
	 * 
	 */
	public EstadoNoAgregadoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EstadoNoAgregadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public EstadoNoAgregadoException(String arg0) {
		super(arg0);
	}

}
