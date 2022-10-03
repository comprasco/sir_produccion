package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepción se lanza cuando la actualización de una búsqueda genera error.
 * @author jmendez
 *
 */
public class ActualizacionBusquedaException extends EventoException {

	/**
	 * 
	 */
	public ActualizacionBusquedaException() {
		super("No se pudo actualizar la búsqueda");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ActualizacionBusquedaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param mensaje Mensaje a ser desplegado en la excepción.
	 */
	public ActualizacionBusquedaException(String mensaje) {
		super(mensaje);
	}

}
