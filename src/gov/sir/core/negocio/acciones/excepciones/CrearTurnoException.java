package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class CrearTurnoException extends EventoException {

	/**
	 * 
	 */
	public CrearTurnoException() {
		super("No se pudo crear el turno ");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CrearTurnoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public CrearTurnoException(String arg0) {
		super(arg0);
	}

}
