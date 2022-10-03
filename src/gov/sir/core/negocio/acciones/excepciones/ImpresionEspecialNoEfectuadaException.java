package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ImpresionEspecialNoEfectuadaException extends EventoException {
	/**
	 * 
	 */
	public ImpresionEspecialNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ImpresionEspecialNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ImpresionEspecialNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
