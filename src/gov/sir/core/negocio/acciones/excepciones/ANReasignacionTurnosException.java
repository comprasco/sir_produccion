package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Excepción lanzada en las validaciones de negocio cuando se reasignan turnos de un usuario a otro.
 * de documentos. 
 */
public class ANReasignacionTurnosException extends EventoException {

	/**
	 * 
	 */
	public ANReasignacionTurnosException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANReasignacionTurnosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANReasignacionTurnosException(String arg0) {
		super(arg0);
	}

}
