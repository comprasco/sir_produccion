package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ConsultaTiposOficinaNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public ConsultaTiposOficinaNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaTiposOficinaNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaTiposOficinaNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
