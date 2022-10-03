package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ImpresionNoEfectuadaException extends EventoException {
	/**
	 * 
	 */
	public ImpresionNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ImpresionNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ImpresionNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
