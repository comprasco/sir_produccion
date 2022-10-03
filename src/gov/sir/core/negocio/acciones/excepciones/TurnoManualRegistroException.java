package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

public class TurnoManualRegistroException extends EventoException {

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TurnoManualRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TurnoManualRegistroException(String arg0) {
		super(arg0);
	}
}
