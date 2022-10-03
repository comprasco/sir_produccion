package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class CreacionBusquedaException extends EventoException {

	/**
		 * 
		 */
	public CreacionBusquedaException() {
		super("No se pudo adicionar la búsqueda a la solicitud de consulta ");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreacionBusquedaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public CreacionBusquedaException(String arg0) {
		super(arg0);
	}

}
