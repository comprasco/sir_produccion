package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class RevisionCalificacionException extends EventoException {

	/**
	 * 
	 */
	public RevisionCalificacionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RevisionCalificacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param string
	 */
	public RevisionCalificacionException(String string) {
	}

}
