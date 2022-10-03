package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class CorreccionEncabezadoNoEfectuadoException extends EventoException {

	/**
	 * 
	 */
	public CorreccionEncabezadoNoEfectuadoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CorreccionEncabezadoNoEfectuadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public CorreccionEncabezadoNoEfectuadoException(String string) {
		super(string);
	}

}
