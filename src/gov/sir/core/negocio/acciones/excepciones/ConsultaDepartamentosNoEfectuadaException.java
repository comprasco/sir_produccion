package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ConsultaDepartamentosNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public ConsultaDepartamentosNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaDepartamentosNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaDepartamentosNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
