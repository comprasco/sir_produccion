package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepci�n se lanza cuando la actualizaci�n de una b�squeda genera error.
 * @author jmendez
 *
 */
public class ActualizacionBusquedaException extends EventoException {

	/**
	 * 
	 */
	public ActualizacionBusquedaException() {
		super("No se pudo actualizar la b�squeda");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ActualizacionBusquedaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param mensaje Mensaje a ser desplegado en la excepci�n.
	 */
	public ActualizacionBusquedaException(String mensaje) {
		super(mensaje);
	}

}
