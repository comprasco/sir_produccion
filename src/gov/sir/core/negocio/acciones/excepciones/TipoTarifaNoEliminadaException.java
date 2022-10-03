package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ddiaz
 */
public class TipoTarifaNoEliminadaException extends EventoException {
	/**
	 * 
	 */
	public TipoTarifaNoEliminadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TipoTarifaNoEliminadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TipoTarifaNoEliminadaException(String arg0) {
		super(arg0);
	}
}
