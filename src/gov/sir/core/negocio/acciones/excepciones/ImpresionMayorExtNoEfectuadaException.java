package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ImpresionMayorExtNoEfectuadaException extends EventoException {
	/**
	 * 
	 */
	public ImpresionMayorExtNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ImpresionMayorExtNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ImpresionMayorExtNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
