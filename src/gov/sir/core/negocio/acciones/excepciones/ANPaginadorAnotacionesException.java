package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepcion es lanzada en la acción de negocio <code>ANPaginadorAnotaciones</code> cuando
 * no se ha podido consultar las anotaciones de un folio.
 * @author ppabon
 */
public class ANPaginadorAnotacionesException extends EventoException {

	/**
	 * 
	 */
	public ANPaginadorAnotacionesException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ANPaginadorAnotacionesException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANPaginadorAnotacionesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
